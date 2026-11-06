-- Native Protocol Dissector
-- Auto-generated Wireshark dissector

local native_proto = Proto("native", "Native Trading Protocol")

local f = native_proto.fields
f.start = ProtoField.uint8("native.start", "Start of Message", base.HEX)
f.length = ProtoField.uint16("native.length", "Message Length", base.DEC)
f.msgtype = ProtoField.string("native.msgtype", "Message Type")

f.nativelogon_compId = ProtoField.string("native.nativelogon.compId", "CompId")
f.nativelogon_password = ProtoField.string("native.nativelogon.password", "Password")
f.nativelogon_newPassword = ProtoField.string("native.nativelogon.newPassword", "NewPassword")
f.nativelogon_protocolVersion = ProtoField.int32("native.nativelogon.protocolVersion", "ProtocolVersion", base.DEC)
f.nativelogon_ipAddress = ProtoField.string("native.nativelogon.ipAddress", "IpAddress")
f.nativelogon_certifiedSolution = ProtoField.string("native.nativelogon.certifiedSolution", "CertifiedSolution")
f.nativelogonresponse_rejectCode = ProtoField.int32("native.nativelogonresponse.rejectCode", "RejectCode", base.DEC)
f.nativelogonresponse_passwordExpiry = ProtoField.int32("native.nativelogonresponse.passwordExpiry", "PasswordExpiry", base.DEC)
f.nativereject_rejectCode = ProtoField.int32("native.nativereject.rejectCode", "RejectCode", base.DEC)
f.nativereject_rejectReason = ProtoField.string("native.nativereject.rejectReason", "RejectReason")
f.nativereject_refMessageType = ProtoField.uint8("native.nativereject.refMessageType", "RefMessageType", base.DEC)
f.nativereject_clientOrderId = ProtoField.string("native.nativereject.clientOrderId", "ClientOrderId")
f.nativelogout_reason = ProtoField.string("native.nativelogout.reason", "Reason")
f.nativeexecutionreport_partitionId = ProtoField.uint8("native.nativeexecutionreport.partitionId", "PartitionId", base.DEC)
f.nativeexecutionreport_sequenceNumber = ProtoField.uint64("native.nativeexecutionreport.sequenceNumber", "SequenceNumber", base.DEC)
f.nativeexecutionreport_executionId = ProtoField.string("native.nativeexecutionreport.executionId", "ExecutionId")
f.nativeexecutionreport_clientOrderId = ProtoField.string("native.nativeexecutionreport.clientOrderId", "ClientOrderId")
f.nativeexecutionreport_orderId = ProtoField.string("native.nativeexecutionreport.orderId", "OrderId")
f.nativeexecutionreport_publicOrderId = ProtoField.string("native.nativeexecutionreport.publicOrderId", "PublicOrderId")
f.nativeexecutionreport_executedPrice = ProtoField.int64("native.nativeexecutionreport.executedPrice", "ExecutedPrice", base.DEC)
f.nativeexecutionreport_transactTime = ProtoField.uint64("native.nativeexecutionreport.transactTime", "TransactTime", base.DEC)
f.nativeexecutionreport_account = ProtoField.uint64("native.nativeexecutionreport.account", "Account", base.DEC)
f.nativeexecutionreport_senderLocation = ProtoField.string("native.nativeexecutionreport.senderLocation", "SenderLocation")
f.nativeexecutionreport_instrumentId = ProtoField.uint32("native.nativeexecutionreport.instrumentId", "InstrumentId", base.DEC)
f.nativeexecutionreport_crossId = ProtoField.string("native.nativeexecutionreport.crossId", "CrossId")
f.nativeexecutionreport_executedQuantity = ProtoField.uint32("native.nativeexecutionreport.executedQuantity", "ExecutedQuantity", base.DEC)
f.nativeexecutionreport_leavesQuantity = ProtoField.uint32("native.nativeexecutionreport.leavesQuantity", "LeavesQuantity", base.DEC)
f.nativeexecutionreport_executionType = ProtoField.string("native.nativeexecutionreport.executionType", "ExecutionType")
f.nativeexecutionreport_orderStatus = ProtoField.uint8("native.nativeexecutionreport.orderStatus", "OrderStatus", base.DEC)
f.nativeexecutionreport_rejectCode = ProtoField.uint32("native.nativeexecutionreport.rejectCode", "RejectCode", base.DEC)
f.nativeexecutionreport_workingIndicator = ProtoField.uint8("native.nativeexecutionreport.workingIndicator", "WorkingIndicator", base.DEC)
f.nativeexecutionreport_side = ProtoField.uint8("native.nativeexecutionreport.side", "Side", base.DEC)
f.nativeexecutionreport_restatementReason = ProtoField.uint8("native.nativeexecutionreport.restatementReason", "RestatementReason", base.DEC)
f.nativeexecutionreport_orderBook = ProtoField.uint8("native.nativeexecutionreport.orderBook", "OrderBook", base.DEC)
f.nativeexecutionreport_indicatorFlags = ProtoField.uint8("native.nativeexecutionreport.indicatorFlags", "IndicatorFlags", base.DEC)
f.nativeexecutionreport_crossType = ProtoField.uint8("native.nativeexecutionreport.crossType", "CrossType", base.DEC)
f.nativeexecutionreport_routingInstruction = ProtoField.uint8("native.nativeexecutionreport.routingInstruction", "RoutingInstruction", base.DEC)
f.nativeexecutionreport_tradingPhase = ProtoField.uint8("native.nativeexecutionreport.tradingPhase", "TradingPhase", base.DEC)
f.nativeexecutionreport_executionVenue = ProtoField.string("native.nativeexecutionreport.executionVenue", "ExecutionVenue")
f.nativeexecutionreport_enteringTrader = ProtoField.string("native.nativeexecutionreport.enteringTrader", "EnteringTrader")
f.nativeexecutionreport_memo = ProtoField.string("native.nativeexecutionreport.memo", "Memo")
f.nativeexecutionreport_deskId = ProtoField.string("native.nativeexecutionreport.deskId", "DeskId")
f.nativeexecutionreport_noTradeKey = ProtoField.string("native.nativeexecutionreport.noTradeKey", "NoTradeKey")
f.nativeexecutionreport_multiLegReportingType = ProtoField.uint8("native.nativeexecutionreport.multiLegReportingType", "MultiLegReportingType", base.DEC)
f.nativeexecutionreport_tradeDate = ProtoField.string("native.nativeexecutionreport.tradeDate", "TradeDate")
f.nativeordercancelreject_partitionId = ProtoField.uint8("native.nativeordercancelreject.partitionId", "PartitionId", base.DEC)
f.nativeordercancelreject_sequenceNumber = ProtoField.uint64("native.nativeordercancelreject.sequenceNumber", "SequenceNumber", base.DEC)
f.nativeordercancelreject_clientOrderId = ProtoField.string("native.nativeordercancelreject.clientOrderId", "ClientOrderId")
f.nativeordercancelreject_rejectCode = ProtoField.int32("native.nativeordercancelreject.rejectCode", "RejectCode", base.DEC)
f.nativeordercancelreject_orderBook = ProtoField.uint8("native.nativeordercancelreject.orderBook", "OrderBook", base.DEC)
f.nativeordercancelreject_transactTime = ProtoField.uint64("native.nativeordercancelreject.transactTime", "TransactTime", base.DEC)
f.nativeordercancelreject_senderLocation = ProtoField.string("native.nativeordercancelreject.senderLocation", "SenderLocation")
f.nativeordercancelreject_account = ProtoField.uint64("native.nativeordercancelreject.account", "Account", base.DEC)
f.nativeordercancelreject_memo = ProtoField.string("native.nativeordercancelreject.memo", "Memo")
f.nativenewordersingle_limitPrice = ProtoField.int64("native.nativenewordersingle.limitPrice", "LimitPrice", base.DEC)
f.nativenewordersingle_orderQuantity = ProtoField.uint32("native.nativenewordersingle.orderQuantity", "OrderQuantity", base.DEC)
f.nativenewordersingle_instrumentId = ProtoField.uint32("native.nativenewordersingle.instrumentId", "InstrumentId", base.DEC)
f.nativenewordersingle_side = ProtoField.uint8("native.nativenewordersingle.side", "Side", base.DEC)
f.nativenewordersingle_enteringTrader = ProtoField.string("native.nativenewordersingle.enteringTrader", "EnteringTrader")
f.nativenewordersingle_clientOrderId = ProtoField.string("native.nativenewordersingle.clientOrderId", "ClientOrderId")
f.nativenewordersingle_account = ProtoField.uint64("native.nativenewordersingle.account", "Account", base.DEC)
f.nativenewordersingle_senderLocation = ProtoField.string("native.nativenewordersingle.senderLocation", "SenderLocation")
f.nativenewordersingle_noTradeKey = ProtoField.string("native.nativenewordersingle.noTradeKey", "NoTradeKey")
f.nativenewordersingle_stopPrice = ProtoField.int64("native.nativenewordersingle.stopPrice", "StopPrice", base.DEC)
f.nativenewordersingle_displayQuantity = ProtoField.uint32("native.nativenewordersingle.displayQuantity", "DisplayQuantity", base.DEC)
f.nativenewordersingle_cancelOnDisconnect = ProtoField.uint8("native.nativenewordersingle.cancelOnDisconnect", "CancelOnDisconnect", base.DEC)
f.nativenewordersingle_anonymity = ProtoField.uint8("native.nativenewordersingle.anonymity", "Anonymity", base.DEC)
f.nativenewordersingle_orderBook = ProtoField.uint8("native.nativenewordersingle.orderBook", "OrderBook", base.DEC)
f.nativenewordersingle_executionInstruction = ProtoField.uint8("native.nativenewordersingle.executionInstruction", "ExecutionInstruction", base.DEC)
f.nativenewordersingle_routingInstruction = ProtoField.uint8("native.nativenewordersingle.routingInstruction", "RoutingInstruction", base.DEC)
f.nativenewordersingle_orderType = ProtoField.uint8("native.nativenewordersingle.orderType", "OrderType", base.DEC)
f.nativenewordersingle_timeInForce = ProtoField.uint8("native.nativenewordersingle.timeInForce", "TimeInForce", base.DEC)
f.nativenewordersingle_expireTime = ProtoField.uint64("native.nativenewordersingle.expireTime", "ExpireTime", base.DEC)
f.nativenewordersingle_memo = ProtoField.string("native.nativenewordersingle.memo", "Memo")
f.nativenewordersingle_deskId = ProtoField.string("native.nativenewordersingle.deskId", "DeskId")
f.nativeordercancelrequest_clientOrderId = ProtoField.string("native.nativeordercancelrequest.clientOrderId", "ClientOrderId")
f.nativeordercancelrequest_origClientOrderId = ProtoField.string("native.nativeordercancelrequest.origClientOrderId", "OrigClientOrderId")
f.nativeordercancelrequest_orderId = ProtoField.string("native.nativeordercancelrequest.orderId", "OrderId")
f.nativeordercancelrequest_instrumentId = ProtoField.uint32("native.nativeordercancelrequest.instrumentId", "InstrumentId", base.DEC)
f.nativeordercancelrequest_side = ProtoField.uint8("native.nativeordercancelrequest.side", "Side", base.DEC)
f.nativeordercancelrequest_orderBook = ProtoField.uint8("native.nativeordercancelrequest.orderBook", "OrderBook", base.DEC)
f.nativeordercancelrequest_senderLocation = ProtoField.string("native.nativeordercancelrequest.senderLocation", "SenderLocation")
f.nativeorderreplacerequest_limitPrice = ProtoField.int64("native.nativeorderreplacerequest.limitPrice", "LimitPrice", base.DEC)
f.nativeorderreplacerequest_orderQuantity = ProtoField.uint32("native.nativeorderreplacerequest.orderQuantity", "OrderQuantity", base.DEC)
f.nativeorderreplacerequest_instrumentId = ProtoField.uint32("native.nativeorderreplacerequest.instrumentId", "InstrumentId", base.DEC)
f.nativeorderreplacerequest_side = ProtoField.uint8("native.nativeorderreplacerequest.side", "Side", base.DEC)
f.nativeorderreplacerequest_clientOrderId = ProtoField.string("native.nativeorderreplacerequest.clientOrderId", "ClientOrderId")
f.nativeorderreplacerequest_origClientOrderId = ProtoField.string("native.nativeorderreplacerequest.origClientOrderId", "OrigClientOrderId")
f.nativeorderreplacerequest_account = ProtoField.uint64("native.nativeorderreplacerequest.account", "Account", base.DEC)
f.nativeorderreplacerequest_senderLocation = ProtoField.string("native.nativeorderreplacerequest.senderLocation", "SenderLocation")
f.nativeorderreplacerequest_stopPrice = ProtoField.int64("native.nativeorderreplacerequest.stopPrice", "StopPrice", base.DEC)
f.nativeorderreplacerequest_displayQuantity = ProtoField.uint32("native.nativeorderreplacerequest.displayQuantity", "DisplayQuantity", base.DEC)
f.nativeorderreplacerequest_orderBook = ProtoField.uint8("native.nativeorderreplacerequest.orderBook", "OrderBook", base.DEC)
f.nativeorderreplacerequest_orderType = ProtoField.uint8("native.nativeorderreplacerequest.orderType", "OrderType", base.DEC)
f.nativeorderreplacerequest_timeInForce = ProtoField.uint8("native.nativeorderreplacerequest.timeInForce", "TimeInForce", base.DEC)
f.nativeorderreplacerequest_expireTime = ProtoField.uint64("native.nativeorderreplacerequest.expireTime", "ExpireTime", base.DEC)
f.nativemissedmessagerequest_partitionId = ProtoField.uint8("native.nativemissedmessagerequest.partitionId", "PartitionId", base.DEC)
f.nativemissedmessagerequest_sequenceNumber = ProtoField.uint64("native.nativemissedmessagerequest.sequenceNumber", "SequenceNumber", base.DEC)
f.nativemissedmessagerequestack_status = ProtoField.uint8("native.nativemissedmessagerequestack.status", "Status", base.DEC)
f.nativetransmissioncomplete_status = ProtoField.uint8("native.nativetransmissioncomplete.status", "Status", base.DEC)
f.nativesystemstatus_partitionId = ProtoField.uint8("native.nativesystemstatus.partitionId", "PartitionId", base.DEC)
f.nativesystemstatus_status = ProtoField.uint8("native.nativesystemstatus.status", "Status", base.DEC)
f.nativenewordercross_crossId = ProtoField.string("native.nativenewordercross.crossId", "CrossId")
f.nativenewordercross_limitPrice = ProtoField.int64("native.nativenewordercross.limitPrice", "LimitPrice", base.DEC)
f.nativenewordercross_orderQuantity = ProtoField.uint32("native.nativenewordercross.orderQuantity", "OrderQuantity", base.DEC)
f.nativenewordercross_enteringTrader = ProtoField.string("native.nativenewordercross.enteringTrader", "EnteringTrader")
f.nativenewordercross_instrumentId = ProtoField.uint32("native.nativenewordercross.instrumentId", "InstrumentId", base.DEC)
f.nativenewordercross_senderLocation = ProtoField.string("native.nativenewordercross.senderLocation", "SenderLocation")
f.nativenewordercross_crossType = ProtoField.uint8("native.nativenewordercross.crossType", "CrossType", base.DEC)
f.nativenewordercross_buySideClientOrderId = ProtoField.string("native.nativenewordercross.buySideClientOrderId", "BuySideClientOrderId")
f.nativenewordercross_buySideAccountId = ProtoField.uint64("native.nativenewordercross.buySideAccountId", "BuySideAccountId", base.DEC)
f.nativenewordercross_sellSideClientOrderId = ProtoField.string("native.nativenewordercross.sellSideClientOrderId", "SellSideClientOrderId")
f.nativenewordercross_sellSideAccountId = ProtoField.uint64("native.nativenewordercross.sellSideAccountId", "SellSideAccountId", base.DEC)
f.nativenewordercross_orderType = ProtoField.uint8("native.nativenewordercross.orderType", "OrderType", base.DEC)
f.nativenewordercross_timeInForce = ProtoField.uint8("native.nativenewordercross.timeInForce", "TimeInForce", base.DEC)
f.nativenewordercross_deskId = ProtoField.string("native.nativenewordercross.deskId", "DeskId")
f.nativenewordercross_buySideMemo = ProtoField.string("native.nativenewordercross.buySideMemo", "BuySideMemo")
f.nativenewordercross_sellSideMemo = ProtoField.string("native.nativenewordercross.sellSideMemo", "SellSideMemo")
f.nativebusinessreject_partitionId = ProtoField.uint8("native.nativebusinessreject.partitionId", "PartitionId", base.DEC)
f.nativebusinessreject_sequenceNumber = ProtoField.uint64("native.nativebusinessreject.sequenceNumber", "SequenceNumber", base.DEC)
f.nativebusinessreject_rejectCode = ProtoField.uint32("native.nativebusinessreject.rejectCode", "RejectCode", base.DEC)
f.nativebusinessreject_clientOrderId = ProtoField.string("native.nativebusinessreject.clientOrderId", "ClientOrderId")
f.nativemasscancelrequest_clientOrderId = ProtoField.string("native.nativemasscancelrequest.clientOrderId", "ClientOrderId")
f.nativemasscancelrequest_massCancelType = ProtoField.uint8("native.nativemasscancelrequest.massCancelType", "MassCancelType", base.DEC)
f.nativemasscancelrequest_instrumentId = ProtoField.uint32("native.nativemasscancelrequest.instrumentId", "InstrumentId", base.DEC)
f.nativemasscancelrequest_segment = ProtoField.string("native.nativemasscancelrequest.segment", "Segment")
f.nativemasscancelrequest_interestType = ProtoField.uint8("native.nativemasscancelrequest.interestType", "InterestType", base.DEC)
f.nativemasscancelrequest_orderBook = ProtoField.uint8("native.nativemasscancelrequest.orderBook", "OrderBook", base.DEC)
f.nativemasscancelrequest_senderLocation = ProtoField.string("native.nativemasscancelrequest.senderLocation", "SenderLocation")
f.nativemasscancelrequest_enteringTrader = ProtoField.string("native.nativemasscancelrequest.enteringTrader", "EnteringTrader")
f.nativemasscancelrequest_deskId = ProtoField.string("native.nativemasscancelrequest.deskId", "DeskId")
f.nativemasscancelreport_partitionId = ProtoField.uint8("native.nativemasscancelreport.partitionId", "PartitionId", base.DEC)
f.nativemasscancelreport_sequenceNumber = ProtoField.uint64("native.nativemasscancelreport.sequenceNumber", "SequenceNumber", base.DEC)
f.nativemasscancelreport_clientOrderId = ProtoField.string("native.nativemasscancelreport.clientOrderId", "ClientOrderId")
f.nativemasscancelreport_status = ProtoField.uint8("native.nativemasscancelreport.status", "Status", base.DEC)
f.nativemasscancelreport_rejectCode = ProtoField.uint32("native.nativemasscancelreport.rejectCode", "RejectCode", base.DEC)
f.nativemasscancelreport_orderBook = ProtoField.uint8("native.nativemasscancelreport.orderBook", "OrderBook", base.DEC)
f.nativenews_partitionId = ProtoField.uint8("native.nativenews.partitionId", "PartitionId", base.DEC)
f.nativenews_sequenceNumber = ProtoField.uint64("native.nativenews.sequenceNumber", "SequenceNumber", base.DEC)
f.nativenews_origTime = ProtoField.uint64("native.nativenews.origTime", "OrigTime", base.DEC)
f.nativenews_urgency = ProtoField.uint8("native.nativenews.urgency", "Urgency", base.DEC)
f.nativenews_headline = ProtoField.string("native.nativenews.headline", "Headline")
f.nativenews_text = ProtoField.string("native.nativenews.text", "Text")
f.nativenews_instruments = ProtoField.string("native.nativenews.instruments", "Instruments")
f.nativenews_underlyings = ProtoField.string("native.nativenews.underlyings", "Underlyings")
f.nativenews_firmList = ProtoField.string("native.nativenews.firmList", "FirmList")
f.nativenews_userList = ProtoField.string("native.nativenews.userList", "UserList")
f.nativemassquote_massQuoteId = ProtoField.string("native.nativemassquote.massQuoteId", "MassQuoteId")
f.nativemassquote_partitionId = ProtoField.uint8("native.nativemassquote.partitionId", "PartitionId", base.DEC)
f.nativemassquote_cancelOnDisconnect = ProtoField.uint8("native.nativemassquote.cancelOnDisconnect", "CancelOnDisconnect", base.DEC)
f.nativemassquote_anonymity = ProtoField.uint8("native.nativemassquote.anonymity", "Anonymity", base.DEC)
f.nativemassquote_account = ProtoField.uint64("native.nativemassquote.account", "Account", base.DEC)
f.nativemassquote_senderLocation = ProtoField.string("native.nativemassquote.senderLocation", "SenderLocation")
f.nativemassquote_enteringTrader = ProtoField.string("native.nativemassquote.enteringTrader", "EnteringTrader")
f.nativemassquote_noTradeKey = ProtoField.string("native.nativemassquote.noTradeKey", "NoTradeKey")
f.nativemassquote_memo = ProtoField.string("native.nativemassquote.memo", "Memo")
f.nativemassquote_deskId = ProtoField.string("native.nativemassquote.deskId", "DeskId")
f.nativemassquote_totalQuoteEntries = ProtoField.uint16("native.nativemassquote.totalQuoteEntries", "TotalQuoteEntries", base.DEC)
f.nativemassquoteacknowledgement_massQuoteId = ProtoField.string("native.nativemassquoteacknowledgement.massQuoteId", "MassQuoteId")
f.nativemassquoteacknowledgement_partitionId = ProtoField.uint8("native.nativemassquoteacknowledgement.partitionId", "PartitionId", base.DEC)
f.nativemassquoteacknowledgement_status = ProtoField.uint8("native.nativemassquoteacknowledgement.status", "Status", base.DEC)
f.nativemassquoteacknowledgement_rejectCode = ProtoField.int32("native.nativemassquoteacknowledgement.rejectCode", "RejectCode", base.DEC)
f.nativemassquoteacknowledgement_transactTime = ProtoField.uint64("native.nativemassquoteacknowledgement.transactTime", "TransactTime", base.DEC)
f.nativemassquoteacknowledgement_anonymity = ProtoField.uint8("native.nativemassquoteacknowledgement.anonymity", "Anonymity", base.DEC)
f.nativemassquoteacknowledgement_account = ProtoField.uint64("native.nativemassquoteacknowledgement.account", "Account", base.DEC)
f.nativemassquoteacknowledgement_senderLocation = ProtoField.string("native.nativemassquoteacknowledgement.senderLocation", "SenderLocation")
f.nativemassquoteacknowledgement_orderBook = ProtoField.uint8("native.nativemassquoteacknowledgement.orderBook", "OrderBook", base.DEC)
f.nativemassquoteacknowledgement_enteringTrader = ProtoField.string("native.nativemassquoteacknowledgement.enteringTrader", "EnteringTrader")
f.nativemassquoteacknowledgement_noTradeKey = ProtoField.string("native.nativemassquoteacknowledgement.noTradeKey", "NoTradeKey")
f.nativemassquoteacknowledgement_memo = ProtoField.string("native.nativemassquoteacknowledgement.memo", "Memo")
f.nativemassquoteacknowledgement_deskId = ProtoField.string("native.nativemassquoteacknowledgement.deskId", "DeskId")
f.nativemassquoteacknowledgement_tradingPhase = ProtoField.uint8("native.nativemassquoteacknowledgement.tradingPhase", "TradingPhase", base.DEC)
f.nativemassquoteacknowledgement_routingSeq = ProtoField.uint64("native.nativemassquoteacknowledgement.routingSeq", "RoutingSeq", base.DEC)
f.nativemassquoteacknowledgement_totalQuoteEntries = ProtoField.uint16("native.nativemassquoteacknowledgement.totalQuoteEntries", "TotalQuoteEntries", base.DEC)
f.nativequotestatusreport_partitionId = ProtoField.uint8("native.nativequotestatusreport.partitionId", "PartitionId", base.DEC)
f.nativequotestatusreport_sequenceNumber = ProtoField.uint64("native.nativequotestatusreport.sequenceNumber", "SequenceNumber", base.DEC)
f.nativequotestatusreport_quoteMsgId = ProtoField.string("native.nativequotestatusreport.quoteMsgId", "QuoteMsgId")
f.nativequotestatusreport_quoteStatus = ProtoField.uint8("native.nativequotestatusreport.quoteStatus", "QuoteStatus", base.DEC)
f.nativequotestatusreport_rejectCode = ProtoField.int32("native.nativequotestatusreport.rejectCode", "RejectCode", base.DEC)
f.nativequotestatusreport_orderBook = ProtoField.uint8("native.nativequotestatusreport.orderBook", "OrderBook", base.DEC)
f.nativequotestatusreport_transactTime = ProtoField.uint64("native.nativequotestatusreport.transactTime", "TransactTime", base.DEC)
f.nativequotestatusreport_anonymity = ProtoField.uint8("native.nativequotestatusreport.anonymity", "Anonymity", base.DEC)
f.nativequotestatusreport_instrumentId = ProtoField.uint32("native.nativequotestatusreport.instrumentId", "InstrumentId", base.DEC)
f.nativequotestatusreport_bidId = ProtoField.string("native.nativequotestatusreport.bidId", "BidId")
f.nativequotestatusreport_offerId = ProtoField.string("native.nativequotestatusreport.offerId", "OfferId")
f.nativequotestatusreport_account = ProtoField.uint64("native.nativequotestatusreport.account", "Account", base.DEC)
f.nativequotestatusreport_senderLocation = ProtoField.string("native.nativequotestatusreport.senderLocation", "SenderLocation")
f.nativequotestatusreport_enteringTrader = ProtoField.string("native.nativequotestatusreport.enteringTrader", "EnteringTrader")
f.nativequotestatusreport_memo = ProtoField.string("native.nativequotestatusreport.memo", "Memo")
f.nativequotestatusreport_deskId = ProtoField.string("native.nativequotestatusreport.deskId", "DeskId")
f.nativemmpresetrequest_requestId = ProtoField.uint32("native.nativemmpresetrequest.requestId", "RequestId", base.DEC)
f.nativemmpresetrequest_segment = ProtoField.string("native.nativemmpresetrequest.segment", "Segment")
f.nativemmpresetrequest_partitionId = ProtoField.uint8("native.nativemmpresetrequest.partitionId", "PartitionId", base.DEC)
f.nativemmpresetrequest_enteringTrader = ProtoField.string("native.nativemmpresetrequest.enteringTrader", "EnteringTrader")
f.nativemmpresetrequest_deskId = ProtoField.string("native.nativemmpresetrequest.deskId", "DeskId")
f.nativemmpresetrequest_senderLocation = ProtoField.string("native.nativemmpresetrequest.senderLocation", "SenderLocation")
f.nativemmpresetrequest_memo = ProtoField.string("native.nativemmpresetrequest.memo", "Memo")
f.nativemmpresetacknowledgement_requestId = ProtoField.uint32("native.nativemmpresetacknowledgement.requestId", "RequestId", base.DEC)
f.nativemmpresetacknowledgement_segment = ProtoField.string("native.nativemmpresetacknowledgement.segment", "Segment")
f.nativemmpresetacknowledgement_partitionId = ProtoField.uint8("native.nativemmpresetacknowledgement.partitionId", "PartitionId", base.DEC)
f.nativemmpresetacknowledgement_enteringTrader = ProtoField.string("native.nativemmpresetacknowledgement.enteringTrader", "EnteringTrader")
f.nativemmpresetacknowledgement_deskId = ProtoField.string("native.nativemmpresetacknowledgement.deskId", "DeskId")
f.nativemmpresetacknowledgement_senderLocation = ProtoField.string("native.nativemmpresetacknowledgement.senderLocation", "SenderLocation")
f.nativemmpresetacknowledgement_memo = ProtoField.string("native.nativemmpresetacknowledgement.memo", "Memo")
f.nativemmpresetacknowledgement_status = ProtoField.uint8("native.nativemmpresetacknowledgement.status", "Status", base.DEC)
f.nativemmpresetacknowledgement_rejectCode = ProtoField.uint32("native.nativemmpresetacknowledgement.rejectCode", "RejectCode", base.DEC)
f.nativemassquoteflat_massQuoteId = ProtoField.string("native.nativemassquoteflat.massQuoteId", "MassQuoteId")
f.nativemassquoteflat_partitionId = ProtoField.uint8("native.nativemassquoteflat.partitionId", "PartitionId", base.DEC)
f.nativemassquoteflat_cancelOnDisconnect = ProtoField.uint8("native.nativemassquoteflat.cancelOnDisconnect", "CancelOnDisconnect", base.DEC)
f.nativemassquoteflat_anonymity = ProtoField.uint8("native.nativemassquoteflat.anonymity", "Anonymity", base.DEC)
f.nativemassquoteflat_account = ProtoField.uint64("native.nativemassquoteflat.account", "Account", base.DEC)
f.nativemassquoteflat_senderLocation = ProtoField.string("native.nativemassquoteflat.senderLocation", "SenderLocation")
f.nativemassquoteflat_enteringTrader = ProtoField.string("native.nativemassquoteflat.enteringTrader", "EnteringTrader")
f.nativemassquoteflat_noTradeKey = ProtoField.string("native.nativemassquoteflat.noTradeKey", "NoTradeKey")
f.nativemassquoteflat_memo = ProtoField.string("native.nativemassquoteflat.memo", "Memo")
f.nativemassquoteflat_deskId = ProtoField.string("native.nativemassquoteflat.deskId", "DeskId")
f.nativemassquoteflat_totalQuoteEntries = ProtoField.uint8("native.nativemassquoteflat.totalQuoteEntries", "TotalQuoteEntries", base.DEC)
f.nativemassquoteflat_quoteEntryId1 = ProtoField.string("native.nativemassquoteflat.quoteEntryId1", "QuoteEntryId1")
f.nativemassquoteflat_instrumentId1 = ProtoField.uint32("native.nativemassquoteflat.instrumentId1", "InstrumentId1", base.DEC)
f.nativemassquoteflat_bidPrice1 = ProtoField.int64("native.nativemassquoteflat.bidPrice1", "BidPrice1", base.DEC)
f.nativemassquoteflat_bidSize1 = ProtoField.uint32("native.nativemassquoteflat.bidSize1", "BidSize1", base.DEC)
f.nativemassquoteflat_offerPrice1 = ProtoField.int64("native.nativemassquoteflat.offerPrice1", "OfferPrice1", base.DEC)
f.nativemassquoteflat_offerSize1 = ProtoField.uint32("native.nativemassquoteflat.offerSize1", "OfferSize1", base.DEC)
f.nativemassquoteflat_quoteEntryId2 = ProtoField.string("native.nativemassquoteflat.quoteEntryId2", "QuoteEntryId2")
f.nativemassquoteflat_instrumentId2 = ProtoField.uint32("native.nativemassquoteflat.instrumentId2", "InstrumentId2", base.DEC)
f.nativemassquoteflat_bidPrice2 = ProtoField.int64("native.nativemassquoteflat.bidPrice2", "BidPrice2", base.DEC)
f.nativemassquoteflat_bidSize2 = ProtoField.uint32("native.nativemassquoteflat.bidSize2", "BidSize2", base.DEC)
f.nativemassquoteflat_offerPrice2 = ProtoField.int64("native.nativemassquoteflat.offerPrice2", "OfferPrice2", base.DEC)
f.nativemassquoteflat_offerSize2 = ProtoField.uint32("native.nativemassquoteflat.offerSize2", "OfferSize2", base.DEC)
f.nativemassquoteflat_quoteEntryId3 = ProtoField.string("native.nativemassquoteflat.quoteEntryId3", "QuoteEntryId3")
f.nativemassquoteflat_instrumentId3 = ProtoField.uint32("native.nativemassquoteflat.instrumentId3", "InstrumentId3", base.DEC)
f.nativemassquoteflat_bidPrice3 = ProtoField.int64("native.nativemassquoteflat.bidPrice3", "BidPrice3", base.DEC)
f.nativemassquoteflat_bidSize3 = ProtoField.uint32("native.nativemassquoteflat.bidSize3", "BidSize3", base.DEC)
f.nativemassquoteflat_offerPrice3 = ProtoField.int64("native.nativemassquoteflat.offerPrice3", "OfferPrice3", base.DEC)
f.nativemassquoteflat_offerSize3 = ProtoField.uint32("native.nativemassquoteflat.offerSize3", "OfferSize3", base.DEC)
f.nativemassquoteflat_quoteEntryId4 = ProtoField.string("native.nativemassquoteflat.quoteEntryId4", "QuoteEntryId4")
f.nativemassquoteflat_instrumentId4 = ProtoField.uint32("native.nativemassquoteflat.instrumentId4", "InstrumentId4", base.DEC)
f.nativemassquoteflat_bidPrice4 = ProtoField.int64("native.nativemassquoteflat.bidPrice4", "BidPrice4", base.DEC)
f.nativemassquoteflat_bidSize4 = ProtoField.uint32("native.nativemassquoteflat.bidSize4", "BidSize4", base.DEC)
f.nativemassquoteflat_offerPrice4 = ProtoField.int64("native.nativemassquoteflat.offerPrice4", "OfferPrice4", base.DEC)
f.nativemassquoteflat_offerSize4 = ProtoField.uint32("native.nativemassquoteflat.offerSize4", "OfferSize4", base.DEC)
f.nativemassquoteflat_quoteEntryId5 = ProtoField.string("native.nativemassquoteflat.quoteEntryId5", "QuoteEntryId5")
f.nativemassquoteflat_instrumentId5 = ProtoField.uint32("native.nativemassquoteflat.instrumentId5", "InstrumentId5", base.DEC)
f.nativemassquoteflat_bidPrice5 = ProtoField.int64("native.nativemassquoteflat.bidPrice5", "BidPrice5", base.DEC)
f.nativemassquoteflat_bidSize5 = ProtoField.uint32("native.nativemassquoteflat.bidSize5", "BidSize5", base.DEC)
f.nativemassquoteflat_offerPrice5 = ProtoField.int64("native.nativemassquoteflat.offerPrice5", "OfferPrice5", base.DEC)
f.nativemassquoteflat_offerSize5 = ProtoField.uint32("native.nativemassquoteflat.offerSize5", "OfferSize5", base.DEC)
f.nativemassquoteflat_quoteEntryId6 = ProtoField.string("native.nativemassquoteflat.quoteEntryId6", "QuoteEntryId6")
f.nativemassquoteflat_instrumentId6 = ProtoField.uint32("native.nativemassquoteflat.instrumentId6", "InstrumentId6", base.DEC)
f.nativemassquoteflat_bidPrice6 = ProtoField.int64("native.nativemassquoteflat.bidPrice6", "BidPrice6", base.DEC)
f.nativemassquoteflat_bidSize6 = ProtoField.uint32("native.nativemassquoteflat.bidSize6", "BidSize6", base.DEC)
f.nativemassquoteflat_offerPrice6 = ProtoField.int64("native.nativemassquoteflat.offerPrice6", "OfferPrice6", base.DEC)
f.nativemassquoteflat_offerSize6 = ProtoField.uint32("native.nativemassquoteflat.offerSize6", "OfferSize6", base.DEC)
f.nativemassquoteflat_quoteEntryId7 = ProtoField.string("native.nativemassquoteflat.quoteEntryId7", "QuoteEntryId7")
f.nativemassquoteflat_instrumentId7 = ProtoField.uint32("native.nativemassquoteflat.instrumentId7", "InstrumentId7", base.DEC)
f.nativemassquoteflat_bidPrice7 = ProtoField.int64("native.nativemassquoteflat.bidPrice7", "BidPrice7", base.DEC)
f.nativemassquoteflat_bidSize7 = ProtoField.uint32("native.nativemassquoteflat.bidSize7", "BidSize7", base.DEC)
f.nativemassquoteflat_offerPrice7 = ProtoField.int64("native.nativemassquoteflat.offerPrice7", "OfferPrice7", base.DEC)
f.nativemassquoteflat_offerSize7 = ProtoField.uint32("native.nativemassquoteflat.offerSize7", "OfferSize7", base.DEC)
f.nativemassquoteflat_quoteEntryId8 = ProtoField.string("native.nativemassquoteflat.quoteEntryId8", "QuoteEntryId8")
f.nativemassquoteflat_instrumentId8 = ProtoField.uint32("native.nativemassquoteflat.instrumentId8", "InstrumentId8", base.DEC)
f.nativemassquoteflat_bidPrice8 = ProtoField.int64("native.nativemassquoteflat.bidPrice8", "BidPrice8", base.DEC)
f.nativemassquoteflat_bidSize8 = ProtoField.uint32("native.nativemassquoteflat.bidSize8", "BidSize8", base.DEC)
f.nativemassquoteflat_offerPrice8 = ProtoField.int64("native.nativemassquoteflat.offerPrice8", "OfferPrice8", base.DEC)
f.nativemassquoteflat_offerSize8 = ProtoField.uint32("native.nativemassquoteflat.offerSize8", "OfferSize8", base.DEC)
f.nativemassquoteflat_quoteEntryId9 = ProtoField.string("native.nativemassquoteflat.quoteEntryId9", "QuoteEntryId9")
f.nativemassquoteflat_instrumentId9 = ProtoField.uint32("native.nativemassquoteflat.instrumentId9", "InstrumentId9", base.DEC)
f.nativemassquoteflat_bidPrice9 = ProtoField.int64("native.nativemassquoteflat.bidPrice9", "BidPrice9", base.DEC)
f.nativemassquoteflat_bidSize9 = ProtoField.uint32("native.nativemassquoteflat.bidSize9", "BidSize9", base.DEC)
f.nativemassquoteflat_offerPrice9 = ProtoField.int64("native.nativemassquoteflat.offerPrice9", "OfferPrice9", base.DEC)
f.nativemassquoteflat_offerSize9 = ProtoField.uint32("native.nativemassquoteflat.offerSize9", "OfferSize9", base.DEC)
f.nativemassquoteflat_quoteEntryId10 = ProtoField.string("native.nativemassquoteflat.quoteEntryId10", "QuoteEntryId10")
f.nativemassquoteflat_instrumentId10 = ProtoField.uint32("native.nativemassquoteflat.instrumentId10", "InstrumentId10", base.DEC)
f.nativemassquoteflat_bidPrice10 = ProtoField.int64("native.nativemassquoteflat.bidPrice10", "BidPrice10", base.DEC)
f.nativemassquoteflat_bidSize10 = ProtoField.uint32("native.nativemassquoteflat.bidSize10", "BidSize10", base.DEC)
f.nativemassquoteflat_offerPrice10 = ProtoField.int64("native.nativemassquoteflat.offerPrice10", "OfferPrice10", base.DEC)
f.nativemassquoteflat_offerSize10 = ProtoField.uint32("native.nativemassquoteflat.offerSize10", "OfferSize10", base.DEC)
f.nativemassquoteflat_quoteEntryId11 = ProtoField.string("native.nativemassquoteflat.quoteEntryId11", "QuoteEntryId11")
f.nativemassquoteflat_instrumentId11 = ProtoField.uint32("native.nativemassquoteflat.instrumentId11", "InstrumentId11", base.DEC)
f.nativemassquoteflat_bidPrice11 = ProtoField.int64("native.nativemassquoteflat.bidPrice11", "BidPrice11", base.DEC)
f.nativemassquoteflat_bidSize11 = ProtoField.uint32("native.nativemassquoteflat.bidSize11", "BidSize11", base.DEC)
f.nativemassquoteflat_offerPrice11 = ProtoField.int64("native.nativemassquoteflat.offerPrice11", "OfferPrice11", base.DEC)
f.nativemassquoteflat_offerSize11 = ProtoField.uint32("native.nativemassquoteflat.offerSize11", "OfferSize11", base.DEC)
f.nativemassquoteflat_quoteEntryId12 = ProtoField.string("native.nativemassquoteflat.quoteEntryId12", "QuoteEntryId12")
f.nativemassquoteflat_instrumentId12 = ProtoField.uint32("native.nativemassquoteflat.instrumentId12", "InstrumentId12", base.DEC)
f.nativemassquoteflat_bidPrice12 = ProtoField.int64("native.nativemassquoteflat.bidPrice12", "BidPrice12", base.DEC)
f.nativemassquoteflat_bidSize12 = ProtoField.uint32("native.nativemassquoteflat.bidSize12", "BidSize12", base.DEC)
f.nativemassquoteflat_offerPrice12 = ProtoField.int64("native.nativemassquoteflat.offerPrice12", "OfferPrice12", base.DEC)
f.nativemassquoteflat_offerSize12 = ProtoField.uint32("native.nativemassquoteflat.offerSize12", "OfferSize12", base.DEC)
f.nativemassquoteflat_quoteEntryId13 = ProtoField.string("native.nativemassquoteflat.quoteEntryId13", "QuoteEntryId13")
f.nativemassquoteflat_instrumentId13 = ProtoField.uint32("native.nativemassquoteflat.instrumentId13", "InstrumentId13", base.DEC)
f.nativemassquoteflat_bidPrice13 = ProtoField.int64("native.nativemassquoteflat.bidPrice13", "BidPrice13", base.DEC)
f.nativemassquoteflat_bidSize13 = ProtoField.uint32("native.nativemassquoteflat.bidSize13", "BidSize13", base.DEC)
f.nativemassquoteflat_offerPrice13 = ProtoField.int64("native.nativemassquoteflat.offerPrice13", "OfferPrice13", base.DEC)
f.nativemassquoteflat_offerSize13 = ProtoField.uint32("native.nativemassquoteflat.offerSize13", "OfferSize13", base.DEC)
f.nativemassquoteflat_quoteEntryId14 = ProtoField.string("native.nativemassquoteflat.quoteEntryId14", "QuoteEntryId14")
f.nativemassquoteflat_instrumentId14 = ProtoField.uint32("native.nativemassquoteflat.instrumentId14", "InstrumentId14", base.DEC)
f.nativemassquoteflat_bidPrice14 = ProtoField.int64("native.nativemassquoteflat.bidPrice14", "BidPrice14", base.DEC)
f.nativemassquoteflat_bidSize14 = ProtoField.uint32("native.nativemassquoteflat.bidSize14", "BidSize14", base.DEC)
f.nativemassquoteflat_offerPrice14 = ProtoField.int64("native.nativemassquoteflat.offerPrice14", "OfferPrice14", base.DEC)
f.nativemassquoteflat_offerSize14 = ProtoField.uint32("native.nativemassquoteflat.offerSize14", "OfferSize14", base.DEC)
f.nativemassquoteflat_quoteEntryId15 = ProtoField.string("native.nativemassquoteflat.quoteEntryId15", "QuoteEntryId15")
f.nativemassquoteflat_instrumentId15 = ProtoField.uint32("native.nativemassquoteflat.instrumentId15", "InstrumentId15", base.DEC)
f.nativemassquoteflat_bidPrice15 = ProtoField.int64("native.nativemassquoteflat.bidPrice15", "BidPrice15", base.DEC)
f.nativemassquoteflat_bidSize15 = ProtoField.uint32("native.nativemassquoteflat.bidSize15", "BidSize15", base.DEC)
f.nativemassquoteflat_offerPrice15 = ProtoField.int64("native.nativemassquoteflat.offerPrice15", "OfferPrice15", base.DEC)
f.nativemassquoteflat_offerSize15 = ProtoField.uint32("native.nativemassquoteflat.offerSize15", "OfferSize15", base.DEC)

local message_types = {
    ["A"] = "NativeLogon",
    ["B"] = "NativeLogonResponse",
    ["0"] = "NativeHeartbeat",
    ["3"] = "NativeReject",
    ["5"] = "NativeLogout",
    ["8"] = "NativeExecutionReport",
    ["9"] = "NativeOrderCancelReject",
    ["D"] = "NativeNewOrderSingle",
    ["F"] = "NativeOrderCancelRequest",
    ["G"] = "NativeOrderReplaceRequest",
    ["M"] = "NativeMissedMessageRequest",
    ["N"] = "NativeMissedMessageRequestAck",
    ["P"] = "NativeTransmissionComplete",
    ["n"] = "NativeSystemStatus",
    ["C"] = "NativeNewOrderCross",
    ["j"] = "NativeBusinessReject",
    ["q"] = "NativeMassCancelRequest",
    ["r"] = "NativeMassCancelReport",
    ["Z"] = "NativeNews",
    ["i"] = "NativeMassQuote",
    ["J"] = "NativeMassQuoteAcknowledgement",
    ["c"] = "NativeQuoteStatusReport",
    ["}"] = "NativeMMPResetRequest",
    ["~"] = "NativeMMPResetAcknowledgement",
    ["m"] = "NativeMassQuoteFlat",
}

-- Start-of-message byte and header size. The length field counts from the
-- message type onwards, so a message occupies 3 + length bytes on the wire.
local NATIVE_SOM = 0x02
local NATIVE_HEADER = 4

-- Dissects the one message that begins at 'start'.
local function dissect_message(buffer, start, pinfo, tree)
    local msg_length = buffer(start + 1, 2):le_uint()
    local total = 3 + msg_length

    local subtree = tree:add(native_proto, buffer(start, total), "Native Protocol")
    local offset = start

    subtree:add(f.start, buffer(offset, 1))
    offset = offset + 1

    subtree:add(f.length, buffer(offset, 2))
    offset = offset + 2

    local msgtype = buffer(offset, 1):string()
    subtree:add(f.msgtype, buffer(offset, 1))
    offset = offset + 1

    local msg_name = message_types[msgtype] or "Unknown"
    subtree:append_text(" - " .. msg_name)
    pinfo.cols.info = msg_name

    if msgtype == "A" then
        -- NativeLogon
        subtree:add(f.nativelogon_compId, buffer(offset, 11))
        offset = offset + 11
        subtree:add(f.nativelogon_password, buffer(offset, 25))
        offset = offset + 25
        subtree:add(f.nativelogon_newPassword, buffer(offset, 25))
        offset = offset + 25
        subtree:add(f.nativelogon_protocolVersion, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativelogon_ipAddress, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativelogon_certifiedSolution, buffer(offset, 44))
        offset = offset + 44
    end

    if msgtype == "B" then
        -- NativeLogonResponse
        subtree:add(f.nativelogonresponse_rejectCode, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativelogonresponse_passwordExpiry, buffer(offset, 4))
        offset = offset + 4
    end

    if msgtype == "0" then
        -- NativeHeartbeat
    end

    if msgtype == "3" then
        -- NativeReject
        subtree:add(f.nativereject_rejectCode, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativereject_rejectReason, buffer(offset, 30))
        offset = offset + 30
        subtree:add(f.nativereject_refMessageType, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativereject_clientOrderId, buffer(offset, 10))
        offset = offset + 10
    end

    if msgtype == "5" then
        -- NativeLogout
        subtree:add(f.nativelogout_reason, buffer(offset, 20))
        offset = offset + 20
    end

    if msgtype == "8" then
        -- NativeExecutionReport
        subtree:add(f.nativeexecutionreport_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeexecutionreport_sequenceNumber, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativeexecutionreport_executionId, buffer(offset, 21))
        offset = offset + 21
        subtree:add(f.nativeexecutionreport_clientOrderId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativeexecutionreport_orderId, buffer(offset, 12))
        offset = offset + 12
        subtree:add(f.nativeexecutionreport_publicOrderId, buffer(offset, 12))
        offset = offset + 12
        subtree:add(f.nativeexecutionreport_executedPrice, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativeexecutionreport_transactTime, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativeexecutionreport_account, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativeexecutionreport_senderLocation, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativeexecutionreport_instrumentId, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativeexecutionreport_crossId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativeexecutionreport_executedQuantity, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativeexecutionreport_leavesQuantity, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativeexecutionreport_executionType, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeexecutionreport_orderStatus, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeexecutionreport_rejectCode, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativeexecutionreport_workingIndicator, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeexecutionreport_side, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeexecutionreport_restatementReason, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeexecutionreport_orderBook, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeexecutionreport_indicatorFlags, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeexecutionreport_crossType, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeexecutionreport_routingInstruction, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeexecutionreport_tradingPhase, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeexecutionreport_executionVenue, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativeexecutionreport_enteringTrader, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativeexecutionreport_memo, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativeexecutionreport_deskId, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativeexecutionreport_noTradeKey, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativeexecutionreport_multiLegReportingType, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeexecutionreport_tradeDate, buffer(offset, 8))
        offset = offset + 8
    end

    if msgtype == "9" then
        -- NativeOrderCancelReject
        subtree:add(f.nativeordercancelreject_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeordercancelreject_sequenceNumber, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativeordercancelreject_clientOrderId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativeordercancelreject_rejectCode, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativeordercancelreject_orderBook, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeordercancelreject_transactTime, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativeordercancelreject_senderLocation, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativeordercancelreject_account, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativeordercancelreject_memo, buffer(offset, 15))
        offset = offset + 15
    end

    if msgtype == "D" then
        -- NativeNewOrderSingle
        subtree:add(f.nativenewordersingle_limitPrice, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativenewordersingle_orderQuantity, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativenewordersingle_instrumentId, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativenewordersingle_side, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenewordersingle_enteringTrader, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativenewordersingle_clientOrderId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativenewordersingle_account, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativenewordersingle_senderLocation, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativenewordersingle_noTradeKey, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativenewordersingle_stopPrice, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativenewordersingle_displayQuantity, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativenewordersingle_cancelOnDisconnect, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenewordersingle_anonymity, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenewordersingle_orderBook, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenewordersingle_executionInstruction, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenewordersingle_routingInstruction, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenewordersingle_orderType, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenewordersingle_timeInForce, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenewordersingle_expireTime, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativenewordersingle_memo, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativenewordersingle_deskId, buffer(offset, 15))
        offset = offset + 15
    end

    if msgtype == "F" then
        -- NativeOrderCancelRequest
        subtree:add(f.nativeordercancelrequest_clientOrderId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativeordercancelrequest_origClientOrderId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativeordercancelrequest_orderId, buffer(offset, 12))
        offset = offset + 12
        subtree:add(f.nativeordercancelrequest_instrumentId, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativeordercancelrequest_side, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeordercancelrequest_orderBook, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeordercancelrequest_senderLocation, buffer(offset, 10))
        offset = offset + 10
    end

    if msgtype == "G" then
        -- NativeOrderReplaceRequest
        subtree:add(f.nativeorderreplacerequest_limitPrice, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativeorderreplacerequest_orderQuantity, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativeorderreplacerequest_instrumentId, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativeorderreplacerequest_side, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeorderreplacerequest_clientOrderId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativeorderreplacerequest_origClientOrderId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativeorderreplacerequest_account, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativeorderreplacerequest_senderLocation, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativeorderreplacerequest_stopPrice, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativeorderreplacerequest_displayQuantity, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativeorderreplacerequest_orderBook, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeorderreplacerequest_orderType, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeorderreplacerequest_timeInForce, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeorderreplacerequest_expireTime, buffer(offset, 8))
        offset = offset + 8
    end

    if msgtype == "M" then
        -- NativeMissedMessageRequest
        subtree:add(f.nativemissedmessagerequest_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemissedmessagerequest_sequenceNumber, buffer(offset, 8))
        offset = offset + 8
    end

    if msgtype == "N" then
        -- NativeMissedMessageRequestAck
        subtree:add(f.nativemissedmessagerequestack_status, buffer(offset, 1))
        offset = offset + 1
    end

    if msgtype == "P" then
        -- NativeTransmissionComplete
        subtree:add(f.nativetransmissioncomplete_status, buffer(offset, 1))
        offset = offset + 1
    end

    if msgtype == "n" then
        -- NativeSystemStatus
        subtree:add(f.nativesystemstatus_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativesystemstatus_status, buffer(offset, 1))
        offset = offset + 1
    end

    if msgtype == "C" then
        -- NativeNewOrderCross
        subtree:add(f.nativenewordercross_crossId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativenewordercross_limitPrice, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativenewordercross_orderQuantity, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativenewordercross_enteringTrader, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativenewordercross_instrumentId, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativenewordercross_senderLocation, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativenewordercross_crossType, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenewordercross_buySideClientOrderId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativenewordercross_buySideAccountId, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativenewordercross_sellSideClientOrderId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativenewordercross_sellSideAccountId, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativenewordercross_orderType, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenewordercross_timeInForce, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenewordercross_deskId, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativenewordercross_buySideMemo, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativenewordercross_sellSideMemo, buffer(offset, 15))
        offset = offset + 15
    end

    if msgtype == "j" then
        -- NativeBusinessReject
        subtree:add(f.nativebusinessreject_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativebusinessreject_sequenceNumber, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativebusinessreject_rejectCode, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativebusinessreject_clientOrderId, buffer(offset, 10))
        offset = offset + 10
    end

    if msgtype == "q" then
        -- NativeMassCancelRequest
        subtree:add(f.nativemasscancelrequest_clientOrderId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemasscancelrequest_massCancelType, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemasscancelrequest_instrumentId, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemasscancelrequest_segment, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemasscancelrequest_interestType, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemasscancelrequest_orderBook, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemasscancelrequest_senderLocation, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemasscancelrequest_enteringTrader, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemasscancelrequest_deskId, buffer(offset, 15))
        offset = offset + 15
    end

    if msgtype == "r" then
        -- NativeMassCancelReport
        subtree:add(f.nativemasscancelreport_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemasscancelreport_sequenceNumber, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemasscancelreport_clientOrderId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemasscancelreport_status, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemasscancelreport_rejectCode, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemasscancelreport_orderBook, buffer(offset, 1))
        offset = offset + 1
    end

    if msgtype == "Z" then
        -- NativeNews
        subtree:add(f.nativenews_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenews_sequenceNumber, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativenews_origTime, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativenews_urgency, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativenews_headline, buffer(offset, 100))
        offset = offset + 100
        subtree:add(f.nativenews_text, buffer(offset, 750))
        offset = offset + 750
        subtree:add(f.nativenews_instruments, buffer(offset, 100))
        offset = offset + 100
        subtree:add(f.nativenews_underlyings, buffer(offset, 100))
        offset = offset + 100
        subtree:add(f.nativenews_firmList, buffer(offset, 54))
        offset = offset + 54
        subtree:add(f.nativenews_userList, buffer(offset, 54))
        offset = offset + 54
    end

    if msgtype == "i" then
        -- NativeMassQuote
        subtree:add(f.nativemassquote_massQuoteId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquote_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemassquote_cancelOnDisconnect, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemassquote_anonymity, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemassquote_account, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquote_senderLocation, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquote_enteringTrader, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemassquote_noTradeKey, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquote_memo, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemassquote_deskId, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemassquote_totalQuoteEntries, buffer(offset, 2))
        offset = offset + 2
    end

    if msgtype == "J" then
        -- NativeMassQuoteAcknowledgement
        subtree:add(f.nativemassquoteacknowledgement_massQuoteId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteacknowledgement_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemassquoteacknowledgement_status, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemassquoteacknowledgement_rejectCode, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteacknowledgement_transactTime, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteacknowledgement_anonymity, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemassquoteacknowledgement_account, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteacknowledgement_senderLocation, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteacknowledgement_orderBook, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemassquoteacknowledgement_enteringTrader, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemassquoteacknowledgement_noTradeKey, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteacknowledgement_memo, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemassquoteacknowledgement_deskId, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemassquoteacknowledgement_tradingPhase, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemassquoteacknowledgement_routingSeq, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteacknowledgement_totalQuoteEntries, buffer(offset, 2))
        offset = offset + 2
    end

    if msgtype == "c" then
        -- NativeQuoteStatusReport
        subtree:add(f.nativequotestatusreport_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativequotestatusreport_sequenceNumber, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativequotestatusreport_quoteMsgId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativequotestatusreport_quoteStatus, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativequotestatusreport_rejectCode, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativequotestatusreport_orderBook, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativequotestatusreport_transactTime, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativequotestatusreport_anonymity, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativequotestatusreport_instrumentId, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativequotestatusreport_bidId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativequotestatusreport_offerId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativequotestatusreport_account, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativequotestatusreport_senderLocation, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativequotestatusreport_enteringTrader, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativequotestatusreport_memo, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativequotestatusreport_deskId, buffer(offset, 15))
        offset = offset + 15
    end

    if msgtype == "}" then
        -- NativeMMPResetRequest
        subtree:add(f.nativemmpresetrequest_requestId, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemmpresetrequest_segment, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemmpresetrequest_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemmpresetrequest_enteringTrader, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemmpresetrequest_deskId, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemmpresetrequest_senderLocation, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemmpresetrequest_memo, buffer(offset, 15))
        offset = offset + 15
    end

    if msgtype == "~" then
        -- NativeMMPResetAcknowledgement
        subtree:add(f.nativemmpresetacknowledgement_requestId, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemmpresetacknowledgement_segment, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemmpresetacknowledgement_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemmpresetacknowledgement_enteringTrader, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemmpresetacknowledgement_deskId, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemmpresetacknowledgement_senderLocation, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemmpresetacknowledgement_memo, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemmpresetacknowledgement_status, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemmpresetacknowledgement_rejectCode, buffer(offset, 4))
        offset = offset + 4
    end

    if msgtype == "m" then
        -- NativeMassQuoteFlat
        subtree:add(f.nativemassquoteflat_massQuoteId, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_partitionId, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemassquoteflat_cancelOnDisconnect, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemassquoteflat_anonymity, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemassquoteflat_account, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_senderLocation, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_enteringTrader, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemassquoteflat_noTradeKey, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_memo, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemassquoteflat_deskId, buffer(offset, 15))
        offset = offset + 15
        subtree:add(f.nativemassquoteflat_totalQuoteEntries, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativemassquoteflat_quoteEntryId1, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId1, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice1, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize1, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice1, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize1, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId2, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId2, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice2, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize2, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice2, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize2, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId3, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId3, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice3, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize3, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice3, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize3, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId4, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId4, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice4, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize4, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice4, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize4, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId5, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId5, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice5, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize5, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice5, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize5, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId6, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId6, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice6, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize6, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice6, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize6, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId7, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId7, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice7, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize7, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice7, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize7, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId8, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId8, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice8, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize8, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice8, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize8, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId9, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId9, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice9, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize9, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice9, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize9, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId10, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId10, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice10, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize10, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice10, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize10, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId11, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId11, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice11, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize11, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice11, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize11, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId12, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId12, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice12, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize12, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice12, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize12, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId13, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId13, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice13, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize13, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice13, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize13, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId14, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId14, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice14, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize14, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice14, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize14, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_quoteEntryId15, buffer(offset, 10))
        offset = offset + 10
        subtree:add(f.nativemassquoteflat_instrumentId15, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_bidPrice15, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_bidSize15, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativemassquoteflat_offerPrice15, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativemassquoteflat_offerSize15, buffer(offset, 4))
        offset = offset + 4
    end

end

function native_proto.dissector(buffer, pinfo, tree)
    if buffer:len() == 0 then return 0 end

    pinfo.cols.protocol = native_proto.name
    local offset = 0

    while offset < buffer:len() do
        local available = buffer:len() - offset

        -- Not even the header yet: ask for one more segment.
        if available < NATIVE_HEADER then
            pinfo.desegment_offset = offset
            pinfo.desegment_len = DESEGMENT_ONE_MORE_SEGMENT
            return buffer:len()
        end

        if buffer(offset, 1):uint() ~= NATIVE_SOM then return offset end

        local total = 3 + buffer(offset + 1, 2):le_uint()
        if total < NATIVE_HEADER then return offset end

        -- Header says more than this segment holds: ask for the rest.
        if available < total then
            pinfo.desegment_offset = offset
            pinfo.desegment_len = total - available
            return buffer:len()
        end

        dissect_message(buffer, offset, pinfo, tree)
        offset = offset + total
    end

    return offset
end

-- Known Native message types for heuristic detection
local known_native_types = {
    [0x41] = true, -- NativeLogon
    [0x42] = true, -- NativeLogonResponse
    [0x30] = true, -- NativeHeartbeat
    [0x33] = true, -- NativeReject
    [0x35] = true, -- NativeLogout
    [0x38] = true, -- NativeExecutionReport
    [0x39] = true, -- NativeOrderCancelReject
    [0x44] = true, -- NativeNewOrderSingle
    [0x46] = true, -- NativeOrderCancelRequest
    [0x47] = true, -- NativeOrderReplaceRequest
    [0x4d] = true, -- NativeMissedMessageRequest
    [0x4e] = true, -- NativeMissedMessageRequestAck
    [0x50] = true, -- NativeTransmissionComplete
    [0x6e] = true, -- NativeSystemStatus
    [0x43] = true, -- NativeNewOrderCross
    [0x6a] = true, -- NativeBusinessReject
    [0x71] = true, -- NativeMassCancelRequest
    [0x72] = true, -- NativeMassCancelReport
    [0x5a] = true, -- NativeNews
    [0x69] = true, -- NativeMassQuote
    [0x4a] = true, -- NativeMassQuoteAcknowledgement
    [0x63] = true, -- NativeQuoteStatusReport
    [0x7d] = true, -- NativeMMPResetRequest
    [0x7e] = true, -- NativeMMPResetAcknowledgement
    [0x6d] = true, -- NativeMassQuoteFlat
}

local function native_heuristic(buffer, pinfo, tree)
    if buffer:len() < NATIVE_HEADER then return false end

    if buffer(0, 1):uint() ~= NATIVE_SOM then return false end

    local msg_length = buffer(1, 2):le_uint()
    if msg_length < 1 or msg_length > 4096 then return false end
    if not known_native_types[buffer(3, 1):uint()] then return false end

    -- Require the messages in this segment to chain exactly: each one
    -- starts with the start-of-message byte and a known type, and the
    -- lengths add up. That is what separates Native from traffic that
    -- merely starts with plausible looking bytes.
    local offset = 0
    while offset + NATIVE_HEADER <= buffer:len() do
        if buffer(offset, 1):uint() ~= NATIVE_SOM then return false end
        local total = 3 + buffer(offset + 1, 2):le_uint()
        if total < NATIVE_HEADER then return false end
        if not known_native_types[buffer(offset + 3, 1):uint()] then return false end
        -- The last message may run into the next segment; that is fine.
        if offset + total > buffer:len() then break end
        offset = offset + total
    end

    native_proto.dissector(buffer, pinfo, tree)
    pinfo.conversation = native_proto
    return true
end

native_proto:register_heuristic("tcp", native_heuristic)

-- Fallback for a capture that begins mid-session: the heuristic is only
-- offered the first segment of a conversation, so a capture that missed the
-- start has nothing to match on. Set the port in Preferences > Protocols >
-- NATIVE then. 0, the default, means the heuristic above is doing the work.
native_proto.prefs.tcp_port = Pref.uint("Native TCP port (fallback)", 0,
    "Only needed when the capture misses the start of the connection")

local tcp_port = DissectorTable.get("tcp.port")
local registered_tcp_port = 0

function native_proto.prefs_changed()
    if registered_tcp_port ~= 0 then
        tcp_port:remove(registered_tcp_port, native_proto)
    end
    registered_tcp_port = native_proto.prefs.tcp_port
    if registered_tcp_port ~= 0 then
        tcp_port:add(registered_tcp_port, native_proto)
    end
end
