# Protocol Code Generator

## Overview

Generates code for MITCH and Native protocol messages from XML definitions in multiple languages:
- **Java** - Classes with parse/encode methods
- **C#** - Classes with Parse/Encode methods using Span<byte>
- **C++** - Structs with parse/encode methods
- **Wireshark** - Lua dissector for protocol analysis

## Supported Protocols

### MITCH Protocol
Market data protocol with 30 message types for order book, trades, and market data.

### Native Protocol
Trading protocol with 24+ message types for order entry, execution reports, and quotes.

## Usage

### MITCH Protocol

```bash
# Generate Java
mvn exec:java -Dexec.mainClass="br.com.a5x.trd.codegen.A5XCodeGenApp" \
  -Dexec.args="/mitch-messages-complete.xml ./generated java"

# Generate C#
mvn exec:java -Dexec.mainClass="br.com.a5x.trd.codegen.A5XCodeGenApp" \
  -Dexec.args="/mitch-messages-complete.xml ./generated csharp"

# Generate C++
mvn exec:java -Dexec.mainClass="br.com.a5x.trd.codegen.A5XCodeGenApp" \
  -Dexec.args="/mitch-messages-complete.xml ./generated cpp"

# Generate Wireshark dissector
mvn exec:java -Dexec.mainClass="br.com.a5x.trd.codegen.A5XCodeGenApp" \
  -Dexec.args="/mitch-messages-complete.xml ./generated wireshark"

# Generate all languages
mvn exec:java -Dexec.mainClass="br.com.a5x.trd.codegen.A5XCodeGenApp" \
  -Dexec.args="/mitch-messages-complete.xml ./generated all"
```

### Native Protocol

```bash
# Generate Java
mvn exec:java -Dexec.mainClass="br.com.a5x.trd.codegen.A5XCodeGenApp" \
  -Dexec.args="/native-messages.xml ./generated java"

# Generate C#
mvn exec:java -Dexec.mainClass="br.com.a5x.trd.codegen.A5XCodeGenApp" \
  -Dexec.args="/native-messages.xml ./generated csharp"

# Generate C++
mvn exec:java -Dexec.mainClass="br.com.a5x.trd.codegen.A5XCodeGenApp" \
  -Dexec.args="/native-messages.xml ./generated cpp"

# Generate Wireshark dissector
mvn exec:java -Dexec.mainClass="br.com.a5x.trd.codegen.A5XCodeGenApp" \
  -Dexec.args="/native-messages.xml ./generated wireshark"

# Generate all languages
mvn exec:java -Dexec.mainClass="br.com.a5x.trd.codegen.A5XCodeGenApp" \
  -Dexec.args="/native-messages.xml ./generated all"
```

## Data Types

Both protocols share one type vocabulary. A given type name means the same thing on the wire
and surfaces as the same language type in both SDKs. The authoritative declaration lives in the
`<types>` block at the top of `native-messages.xml` and `mitch-messages-complete.xml`, including
`javaType`, `csharpType` and `cppType` for every type.

### Shared core (both protocols)

| XML Type | Bytes | Java | C# | C++ | Description |
|----------|-------|------|----|-----|-------------|
| alpha    | variable | String | string | std::string | ASCII string. Padding differs by protocol, see below |
| byte     | 1  | byte | byte   | uint8_t  | Single byte holding one ASCII character |
| bitfield | 1  | byte | byte   | uint8_t  | Single byte where individual bits carry meaning |
| int32    | 4  | int  | int    | int32_t  | Signed 32-bit little-endian integer |
| uint8    | 1  | int  | byte   | uint8_t  | Unsigned 8-bit integer |
| uint16   | 2  | int  | ushort | uint16_t | Unsigned 16-bit little-endian integer |
| uint32   | 4  | long | uint   | uint32_t | Unsigned 32-bit little-endian integer |
| uint64   | 8  | long | ulong  | uint64_t | Unsigned 64-bit little-endian integer |
| price4   | 4  | int  | int    | int32_t  | Signed 32-bit integer, 4 implied decimals ("Price") |
| price8   | 8  | long | long   | int64_t  | Signed 64-bit integer, 4 implied decimals ("Long Price") |

### MITCH only

Native carries every timestamp as a `uint64` epoch value and has no reserved fields, so it
declares none of these. Do not add them to the Native schema until the Native generators
support them.

| XML Type | Bytes | Java | C# | C++ | Description |
|----------|-------|------|----|-----|-------------|
| bytearray    | variable | byte[] | byte[] | uint8_t[]   | Raw bytes, used for reserved fields |
| date         | 8  | String | string | std::string | Date as ASCII, YYYYMMDD |
| time         | 8  | String | string | std::string | Time as ASCII, HH:MM:SS |
| extendedtime | 12 | String | string | std::string | Time as ASCII, HH:MM:SS.sss |
| datetime     | 27 | String | string | std::string | UTC ASCII, YYYY-MM-DDThh:mm:ss.ddddddZ |

### Things to know

**`alpha` padding is the one intentional difference between the protocols.** Native is
null-terminated, MITCH is right-padded with spaces. Each specification states this. The type
name is stable and the `padding` attribute in the `<types>` block carries the distinction; the
generated `parse`/`encode` code handles it, so callers work with a plain string either way.

**`price8` carries 4 implied decimals, not the 8 stated in the portal's data type table.** The
`<types>` blocks document the evidence. Every price field in the current Native layouts is 8
bytes wide, so they all use `price8`; `price4` is declared for completeness because the
specification defines it.

**`uint64` in Java.** Java has no unsigned 64-bit primitive. All 64 bits are preserved in a
`long`, but a value above 2^63-1 reads as negative. Use `Long.toUnsignedString` and
`Long.compareUnsigned` where that is possible. C# (`ulong`) and C++ (`uint64_t`) expose a true
unsigned type.

### Breaking changes from the previous generated SDK

| Protocol | Type | Was | Now |
|----------|------|-----|-----|
| Native | `alpha` | Java `byte[]`, C# `byte[]`, C++ `uint8_t[N]` | `String` / `string` / `std::string` |
| MITCH  | `uint64` | Java `BigInteger`, C# `BigInteger` | `long` / `ulong` |

The wire format is unchanged; only the generated accessor types change. Client code that
touched these fields needs updating.

## Generated Class Structure

Each message generates:
- Public fields with JavaDoc/documentation
- Byte array fields with initialization
- Static parse() method (decode from ByteBuffer/Span/pointer)
- Static encode() method (encode to ByteBuffer/Span/pointer)
- toString() method (Java only)

### Additional Generated Classes

#### MITCH Protocol
- **MitchEventWrapper** - Message dispatcher (header: Length UInt16 + MessageType Byte)

#### Native Protocol
- **NativeEventWrapper** - Message dispatcher (header: Start 0x02 + Length UInt16 + MessageType Byte)

## MITCH Protocol Messages (30 messages)

### Market Data Messages
- **MitchTime** (0x54) - Time synchronization
- **MitchSystemEvent** (0x53) - System events
- **MitchSymbolDirectory** (0x52) - Instrument directory
- **MitchSymbolStatus** (0x48) - Trading status
- **MitchTopOfBook** (0x71) - Top of book
- **MitchStatistics** (0x77) - Statistics
- **MitchExtendedStatistics** (0x80) - Extended statistics
- **MitchNews** (0x75) - News

### Order Book Messages
- **MitchAddOrder** (0x41) - Add order
- **MitchAddAttributedOrder** (0x46) - Add attributed order
- **MitchOrderDeleted** (0x44) - Order deletion
- **MitchOrderModified** (0x55) - Order modification
- **MitchOrderBookClear** (0x79) - Clear order book

### Trade Messages
- **MitchOrderExecuted** (0x45) - Order execution
- **MitchOrderExecutedWithPriceSize** (0x43) - Order execution with size
- **MitchTrade** (0x50) - Trade
- **MitchAuctionTrade** (0x51) - Auction trade
- **MitchRecoveryTrade** (0x76) - Recovery trade
- **MitchAuctionInfo** (0x49) - Auction info

## Native Protocol Messages (24 messages)

### Session Messages
- **NativeLogon** (A) - Logon request
- **NativeLogonResponse** (B) - Logon response
- **NativeHeartbeat** (0) - Heartbeat
- **NativeReject** (3) - Reject
- **NativeLogout** (5) - Logout

### Order Entry Messages
- **NativeNewOrderSingle** (D) - New order
- **NativeOrderCancelRequest** (F) - Cancel order
- **NativeOrderReplaceRequest** (G) - Replace order
- **NativeNewOrderCross** (C) - Cross order

### Execution Messages
- **NativeExecutionReport** (8) - Execution report
- **NativeOrderCancelReject** (9) - Cancel reject
- **NativeBusinessReject** (j) - Business reject

### Mass Operations
- **NativeMassCancelRequest** (q) - Mass cancel request
- **NativeMassCancelReport** (r) - Mass cancel report
- **NativeMassQuote** (i) - Mass quote
- **NativeMassQuoteAcknowledgement** (J) - Mass quote ack
- **NativeQuoteStatusReport** (c) - Quote status

### Recovery Messages
- **NativeMissedMessageRequest** (M) - Missed message request
- **NativeMissedMessageRequestAck** (N) - Missed message ack
- **NativeTransmissionComplete** (P) - Transmission complete
- **NativeSystemStatus** (n) - System status
- **NativeNews** (Z) - News

## Language-Specific Features

### Java
- Static parse() and encode() methods
- ByteBuffer for data handling
- Event wrapper with handler interface
- Little-endian byte order

### C#
- Parse() and Encode() methods using Span<byte>
- Properties with get/set
- BitConverter for endianness
- Event wrapper with handler interface

### C++
- Header-only structs (.hpp files)
- Static parse() and encode() methods
- Uses std::memcpy for performance
- Event wrapper with virtual handler interface

### Wireshark
- Lua dissector script
- Automatic field registration
- Protocol header parsing
- TCP port binding (default: 9999)

## Wireshark Installation

1. Copy dissector to Wireshark plugins folder:
   - Windows: `%APPDATA%\Wireshark\plugins`
   - Linux: `~/.local/lib/wireshark/plugins`
   - macOS: `~/.wireshark/plugins`

2. Restart Wireshark

3. Configure port in dissector (default: 9999)
