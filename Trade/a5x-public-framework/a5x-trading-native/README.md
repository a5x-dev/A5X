# a5x-trading-native

Java SDK and example client for the **A5X Native Gateway** (Order Entry Binary). The protocol
is described in the [Technical Portal](https://portal-tecnico.a5x.com.br/en/trading/order-entry-binary):
connection rules, message layouts and the gateway addresses of each environment.

| Package | What it is |
| --- | --- |
| `br.com.a5x.trd.pub.nativ.model` | One class per message, with `parse` and `encode`. Generated at build time from the schema of the current version (`../a5x-trading-codegen/src/main/resources/native-messages.xml`) into `target/generated-sources/a5x/java`; they are not in `src/`. |
| `br.com.a5x.trd.pub.nativ` | `NativeEventWrapper`: frames a message (`encode`) and dispatches a frame to a handler (`parse`). Generated at build time as well. |
| `br.com.a5x.trd.pub.nativ.client` | Hand-written example code built on the two above: stream framing, a session and two runnable examples. |

## Wire basics

Every message is `Start of Message (0x02)` + `Length` (UInt16, little-endian, counting the
bytes **from the Message Type onwards**) + `Message Type` (1 byte) + body. All integers are
little-endian. Prices are signed 64-bit integers with 8 implied decimals, in two's
complement: `5.25` travels as `525000000` (`NativePrices` converts both ways). Alpha fields
are ASCII, NUL-padded.

TCP does not preserve message boundaries: a single read can hold part of a message or
several messages. `NativeStreamDecoder` keeps the bytes of an incomplete frame until the rest
arrives and decodes each complete frame from its own slice, so a message the SDK does not
decode completely cannot shift the next one.

## The session (`NativeSession`)

It follows the rules of the portal's "Establishing a Connection" section:

1. **Logon:** send `Logon` and wait for `Logon Response` before sending anything else. Reject
   Code `0` is success; `3` is success with the new password refused. On an invalid CompID,
   password, gateway or privilege the gateway closes the connection without answering, and
   `logon` throws an `IOException` that says so.
2. **Heartbeat:** after the logon, the session sends a `Heartbeat` whenever nothing else was
   sent for one heartbeat interval, and disconnects if nothing arrives from the gateway for
   the inactivity timeout (two intervals by default).
3. **Logout:** `logout` sends `Logout`, waits for the gateway's `Logout` and closes.

Every message received is delivered to `Listener.onMessage` on the reader thread, already
decoded into its model class. The session decodes through a dynamic proxy to keep the
example short; a latency-sensitive client implements
`NativeEventWrapper.INativePackageHandler` directly (`NativeHandlerAdapter` is a no-op base
class for that).

The `Logon` message carries the password, and its `toString` prints every field: never log
it.

## Build and test

The commands run from `a5x-public-framework`. Only a JDK 21 is needed: the Maven Wrapper
downloads Maven on first use.

| | Linux / macOS | Windows (PowerShell or cmd) |
| --- | --- | --- |
| Build and test | `./mvnw -pl a5x-trading-native -am verify` | `.\mvnw.cmd -pl a5x-trading-native -am verify` |

The tests run against a fake gateway on the loopback interface: no network access and no
credentials.

## Run the examples

After the build, the classes are in `a5x-trading-native/target/classes`.

**Offline**, no network: encodes a New Order Single and an Execution Report, prints the
bytes and decodes them back from a stream cut in uneven pieces.

```
java -cp a5x-trading-native/target/classes br.com.a5x.trd.pub.nativ.client.NativeOfflineExample
```

**Against the gateway**: logs on, optionally sends one Day limit order, prints what arrives
and logs out. The host, port and CompID are the ones A5X assigned to you for the environment
(use the Certification environment to try it). The password is read from the
`A5X_NATIVE_PASSWORD` environment variable, so it never appears in the command line or in the
process list (`A5X_NATIVE_NEW_PASSWORD`, optional, changes it at logon).

Linux / macOS (bash or zsh):

```bash
read -rs A5X_NATIVE_PASSWORD && export A5X_NATIVE_PASSWORD
java -cp a5x-trading-native/target/classes br.com.a5x.trd.pub.nativ.client.NativeClientExample \
  --host <gateway-host> --port <port> --comp-id <CompID>
```

Windows PowerShell:

```powershell
$env:A5X_NATIVE_PASSWORD = Read-Host -MaskInput   # PowerShell 7; on 5.1 type it as a plain string
java -cp a5x-trading-native/target/classes br.com.a5x.trd.pub.nativ.client.NativeClientExample `
  --host <gateway-host> --port <port> --comp-id <CompID>
```

Windows cmd (the password is visible while you type it):

```bat
set /p A5X_NATIVE_PASSWORD=Password: 
java -cp a5x-trading-native/target/classes br.com.a5x.trd.pub.nativ.client.NativeClientExample --host <gateway-host> --port <port> --comp-id <CompID>
```

To send one order, add (all values are yours; the example has no defaults for them):

```
--order --instrument <instrument id> --side buy|sell --quantity <n> --price <decimal>
--account <account> --client-order-id <id> [--entering-trader <id>] [--sender-location <id>]
```

Other options: `--heartbeat-seconds` (the interval configured for your environment; 3 by
default), `--wait-seconds` (how long to print messages before logging out; 5 by default),
`--protocol-version`, `--ip-address` and `--certified-solution` (Logon fields).

The order is a Day limit order with Cancel on Disconnect set. Max Floor (`displayQuantity`) is
sent as `0xFFFFFFFF`: the specification asks for a negative value to ignore the field, and the
field is UInt32. Confirm the values your CompID needs (Entering Trader, Sender Location,
Account) with A5X before using it outside Certification.
