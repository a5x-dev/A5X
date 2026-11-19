# Protocol Code Generator

## Overview

Generates code for MITCH and Native protocol messages from XML definitions in multiple languages:
- **Java** - Classes with parse/encode methods
- **C#** - Classes with Parse/Encode methods using Span<byte>
- **C++** - Structs with parse/encode methods
- **Wireshark** - Lua dissector for protocol analysis

## Specification Versions

The exchange runs one version of the specification in each environment, and the technical
portal documents each of them. Every version has its own schemas and its own generated SDK:

| Version | Environment | Schemas | Generated SDK |
|---------|-------------|---------|---------------|
| `1.1.4.0.i4` | Certification | [`spec/1.1.4.0.i4/`](spec/1.1.4.0.i4) | [`generated/1.1.4.0.i4/`](generated/1.1.4.0.i4) |
| `1.1.4.0.i6` | New Release | [`spec/1.1.4.0.i6/`](spec/1.1.4.0.i6) | [`generated/1.1.4.0.i6/`](generated/1.1.4.0.i6) |

Each `spec/<version>/` holds `mitch-messages.xml`, `native-messages.xml`, the two XSDs, and the
QuickFIX dictionaries `fix-oe-messages.xml` (Order Entry) and `fix-dc-messages.xml` (Drop Copy),
and `reject-codes.xml` with its XSD (the reject codes of the gateways).
Each `generated/<version>/` holds `mitch/` and `native/`, each with `java/`, `csharp/`, `cpp/`
and `wireshark/`.

`src/main/resources` is the version clients run today (Certification) and is what the
`a5x-trading-mitch` module and the examples below use; `SpecVersionsTest` checks that it is
identical to one of the `spec/` directories. Use the New Release SDK to prepare for the next
version: the classes have the same names and methods, and the fields of that version (new
fields, renamed ones and changed types are listed in [`spec/README.md`](spec/README.md)).

## Supported Protocols

### MITCH Protocol
Market data protocol with 30 message types for order book, trades, and market data.

### Native Protocol
Trading protocol with 24+ message types for order entry, execution reports, and quotes.

The protocol of a schema comes from the namespace of its root element
(`http://a5x.com.br/mitch` or `http://a5x.com.br/native`), so a schema file can have any name.
The QuickFIX dictionaries (`fix-oe-messages.xml`, `fix-dc-messages.xml`) are not generated:
the FIX engines load them as they are.

## Usage

```text
A5XCodeGenApp [--schema <schema>] [--lang <language>] [--out <outputDir>]

  --schema, -s  a schema file, a classpath resource (/mitch-messages.xml, /native-messages.xml:
                the current version) or the spec directory (every version)
                default: /mitch-messages.xml
  --lang, -l    java, csharp, cpp, wireshark or all          default: java
  --out, -o     output directory                             default: ./generated
```

The options go in any order, `--lang=cpp` also works, and any of them can be left out. The
positional form of the previous versions, `<schema> <outputDir> <language>`, is still accepted.

### Running the generator as a jar

The generator uses only the JDK (21 or later), so its jar runs on its own. Build it once, from
`a5x-public-framework`:

```bash
./mvnw -pl a5x-trading-codegen package -DskipTests
```

and run it from anywhere, with a schema file of the version you need:

```bash
java -jar a5x-trading-codegen/target/a5x-trading-codegen-2.0.0.jar \
  --schema a5x-trading-codegen/spec/1.1.4.0.i6/mitch-messages.xml --lang cpp --out ./sdk/mitch
```

The schemas of the current version are also inside the jar, as classpath resources:

```bash
java -jar a5x-trading-codegen-2.0.0.jar --schema /native-messages.xml --lang csharp
```

In Git Bash on Windows, run `export MSYS_NO_PATHCONV=1` first: otherwise the shell turns
`/native-messages.xml` into a path under the Git installation directory.

### Running the generator with Maven

From `a5x-trading-codegen`. To regenerate every version at once, pass the `spec` directory
instead of a schema:

```bash
../mvnw compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" \
  "-Dexec.args=--schema spec --lang all --out ./generated"
```

A schema file also works, for one version and one protocol:

```bash
../mvnw compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" \
  "-Dexec.args=--schema spec/1.1.4.0.i6/mitch-messages.xml --lang all --out ./out/mitch"
```

> **Running the commands on Windows.** The examples are written for bash (Linux, macOS, Git
> Bash). In PowerShell use `..\mvnw.cmd`, write the command on one line (or end lines with a
> backtick instead of `\`) and quote every `-D` argument as a whole, otherwise PowerShell splits
> it at the dot: `..\mvnw.cmd compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" "-Dexec.args=--schema spec --lang all"`.
> In cmd, use `..\mvnw.cmd` and `^` to continue a line. The `java -jar` commands work the same
> way in every shell.

### MITCH Protocol

```bash
# Generate Java
../mvnw compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" \
  "-Dexec.args=--schema /mitch-messages.xml --lang java"

# Generate C#
../mvnw compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" \
  "-Dexec.args=--schema /mitch-messages.xml --lang csharp"

# Generate C++
../mvnw compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" \
  "-Dexec.args=--schema /mitch-messages.xml --lang cpp"

# Generate Wireshark dissector
../mvnw compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" \
  "-Dexec.args=--schema /mitch-messages.xml --lang wireshark"

# Generate all languages
../mvnw compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" \
  "-Dexec.args=--schema /mitch-messages.xml --lang all"
```

### Native Protocol

```bash
# Generate Java
../mvnw compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" \
  "-Dexec.args=--schema /native-messages.xml --lang java"

# Generate C#
../mvnw compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" \
  "-Dexec.args=--schema /native-messages.xml --lang csharp"

# Generate C++
../mvnw compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" \
  "-Dexec.args=--schema /native-messages.xml --lang cpp"

# Generate Wireshark dissector
../mvnw compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" \
  "-Dexec.args=--schema /native-messages.xml --lang wireshark"

# Generate all languages
../mvnw compile exec:java "-Dexec.mainClass=br.com.a5x.trd.codegen.A5XCodeGenApp" \
  "-Dexec.args=--schema /native-messages.xml --lang all"
```

## Data Types

Both protocols share one type vocabulary. A given type name means the same thing on the wire
and surfaces as the same language type in both SDKs. The authoritative declaration lives in the
`<types>` block at the top of `native-messages.xml` and `mitch-messages.xml`, including
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
| price4   | 4  | int  | int    | int32_t  | Signed 32-bit integer, 4 implied decimals ("Price"). Sign-magnitude in MITCH, see below |
| price8   | 8  | long | long   | int64_t  | Signed 64-bit integer, 8 implied decimals ("Long Price"). Sign-magnitude in MITCH, see below |

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

**MITCH prices are sign-magnitude, Native prices are two's complement.** In MITCH the highest
bit of a price is the sign and the other bits are the absolute value, so -1.00 in a Long Price
is `00 E1 F5 05 00 00 00 80`; read as two's complement those bytes give
-9223372036754775808. The MITCH schema declares this with `negativeEncoding="sign-magnitude"`
on `price4` and `price8`. The generated code converts at the wire boundary (Java
`BitUtils.byteArrayToSignMagnitude64`, C++ `a5x_read_sm64`, C# inline, Lua `sign_magnitude`),
so the field always holds an ordinary signed value. The Native types declare nothing and stay
two's complement.

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

### Sample captures

Packet captures are not kept in this repository. Download them from the
[A5X Technical Portal](https://portal-tecnico.a5x.com.br/en/trading/market-data-mitch), in the
PCAP column of the Message Specification table, and open them with the dissector of the same
version (`generated/<version>/mitch/wireshark/mitch_dissector.lua`).

MITCH Price and Long Price fields carry negative values in **sign-magnitude**, not two's
complement: the most significant bit of the last byte is the sign. -1.00 as a Long Price is
`00 E1 F5 05 00 00 00 80`; a decoder that reads two's complement shows
`-9223372036754775808` instead.
