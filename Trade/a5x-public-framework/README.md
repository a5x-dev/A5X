# A5X Public Framework

Java SDK, code generator and examples for the A5X trading gateways. The protocols are
documented in the [A5X Technical Portal](https://portal-tecnico.a5x.com.br/en/trading); this
framework turns those specifications into code you can compile, test and run.

| Module | What it is | Tests |
| --- | --- | --- |
| [`a5x-trading-codegen`](a5x-trading-codegen) | The message schemas of each specification version (`spec/<version>/`) and the generator that turns them into Java, C#, C++ and Wireshark Lua dissectors (`generated/<version>/`). | 68 |
| [`a5x-trading-mitch`](a5x-trading-mitch) | Market Data (MITCH): generated message models, a multicast reader and a PCAP reader. | 26 |
| [`a5x-trading-native`](a5x-trading-native) | Order Entry Binary (Native): generated message models, TCP stream framing and an example client session (logon, heartbeat, order, logout). | 15 |

The two protocol modules keep no copy of the message models: every build generates them with
`a5x-trading-codegen`, from the schemas of the version clients run today (Certification,
`a5x-trading-codegen/src/main/resources`), into `target/generated-sources/a5x/java`, and
compiles them with the hand-written code. Change the schema and the next build follows it.
The SDK of every version, including the next one, is in
[`a5x-trading-codegen/generated`](a5x-trading-codegen/generated); see the
[versions table](a5x-trading-codegen/README.md#specification-versions).

## Requirements

A JDK 21. The Maven Wrapper (`mvnw` on Linux and macOS, `mvnw.cmd` on Windows) downloads
Maven 3.9.9 from Maven Central on first use; an installed Maven 3.8+ works the same way.

## Build and test

Run from this directory.

| | Linux / macOS | Windows (PowerShell or cmd) |
| --- | --- | --- |
| Everything | `./mvnw clean verify` | `.\mvnw.cmd clean verify` |
| One module | `./mvnw -pl a5x-trading-native -am verify` | `.\mvnw.cmd -pl a5x-trading-native -am verify` |
| Coverage report | `./mvnw clean verify jacoco:report` | `.\mvnw.cmd clean verify jacoco:report` |
| JavaDoc | `./mvnw javadoc:javadoc` | `.\mvnw.cmd javadoc:javadoc` |

Reports land in each module's `target/` directory (`site/jacoco/index.html`,
`site/apidocs/index.html`). The tests need no network access and no credentials: the Native
session tests run against a fake gateway on the loopback interface.

## Wire formats in short

| | MITCH (Market Data) | Native (Order Entry Binary) |
| --- | --- | --- |
| Transport | UDP multicast, plus TCP for snapshot and recovery | TCP |
| Byte order | Little-endian | Little-endian |
| Text fields | ASCII, space-padded | ASCII, NUL-padded |
| Negative prices | Sign-magnitude (most significant bit is the sign) | Two's complement |
| Framing | Unit header with message count | `0x02`, UInt16 length from the Message Type, Message Type |

The module READMEs and the Technical Portal have the details. Sample captures of the market
data feeds are published in the Technical Portal (PCAP column of each gateway page), not in
this repository.
