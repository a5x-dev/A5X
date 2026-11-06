# Schemas by specification version

One directory per version of the A5X trading specification. The technical portal links each
environment's schemas from the "Message Specification" table of every gateway page.

| Version | Environment | Release |
|---------|-------------|---------|
| [`1.1.4.0.i4`](1.1.4.0.i4) | Certification | Exchange Release 4 Beta 2 |
| [`1.1.4.0.i6`](1.1.4.0.i6) | New Release | Exchange Release 4 Final |

| File | Gateway |
|------|---------|
| `mitch-messages.xml`, `mitch-messages.xsd` | Market Data (MITCH) |
| `native-messages.xml`, `native-messages.xsd` | Order Entry (Native binary) |
| `fix-oe-messages.xml` | Order Entry (FIX), QuickFIX dictionary |
| `fix-dc-messages.xml` | Drop Copy (FIX), QuickFIX dictionary |
| `reject-codes.xml`, `reject-codes.xsd` | Reject codes of the trading gateways, as on the [Technical Portal](https://portal-tecnico.a5x.com.br/en/trading/reject-code); the same in every version |

The SDK generated from each version is in [`../generated/<version>/`](../generated).

## From 1.1.4.0.i4 to 1.1.4.0.i6

### MITCH

| Message | Change |
|---------|--------|
| Add Order | `reserved1` (alpha 10) added |
| Order Deleted, Order Modified | `instrumentId` and `side` added |
| Order Executed, Order Executed With Price Size | `side` and `tradeDate` added |
| Trade, Auction Trade, Recovery Trade | `tradeDate` added |
| Extended Statistics | `upperStaticCBLimit`, `lowerStaticCBLimit` and `openInterest` added |
| Symbol Directory | `createdDate`, `uniqueInstrumentId`, `firstTradingDate`, `settlementDate`, `lastTradingDay`, `tradeMethod`, `numberOfDecimalPlaces`, `cfiCode`, `strategyType`, `strategyRatio` and `securitySubType` added |

### Native

| Message | Change |
|---------|--------|
| Execution Report | `multiLegReportingType` and `tradeDate` added |
| New Order Cross | `memo` replaced by `buySideMemo` and `sellSideMemo` |
| Order Cancel Reject | `account` and `memo` added; `rejectCode` is now `uint32` |
| Logon | `protocolVersion` is now `uint32` |
| Logon Response | `rejectCode` and `passwordExpiry` are now `uint32` |
| Mass Quote Acknowledgement, Quote Status Report | `rejectCode` is now `uint32` |

### FIX

New tags in both dictionaries: `SecondaryClOrdID (526)`, `TradeDate (75)` and
`PublishCrossStatistics (23000)`.

| Message | Order Entry | Drop Copy |
|---------|-------------|-----------|
| New Order Single | `Symbol`, `SecondaryClOrdID` | |
| New Order Cross | `Symbol`, `SecondaryClOrdID`; `Memo` moves within the message | |
| Order Cancel Request | `Account`, `Memo`, `Symbol`, `SecondaryClOrdID` | |
| Order Cancel Replace Request | `Symbol`, `SecondaryClOrdID` | |
| Order Cancel Reject | `Account`, `SecondaryClOrdID` | `Account`, `SecondaryClOrdID` |
| Order Mass Cancel Request | `Symbol`, `UnderlyingSymbol` | |
| Execution Report | `SecondaryClOrdID`, `TradeDate`, `PublishCrossStatistics` | `SecondaryClOrdID`, `TradeDate`, `PublishCrossStatistics` |
| Mass Quote | `Symbol` and `NoTradeKey` in the quote entries | |
| Mass Quote Acknowledgement | `Symbol` and `NoTradeKey` in the quote entries | `Symbol` and `NoTradeKey` in the quote entries |
| Quote Cancel | `Symbol` | |

All of them are optional (`required="N"`).
