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
f.nativeexecutionreport_executedPrice = ProtoField.int32("native.nativeexecutionreport.executedPrice", "ExecutedPrice", base.DEC)
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
f.nativeordercancelreject_partitionId = ProtoField.uint8("native.nativeordercancelreject.partitionId", "PartitionId", base.DEC)
f.nativeordercancelreject_sequenceNumber = ProtoField.uint64("native.nativeordercancelreject.sequenceNumber", "SequenceNumber", base.DEC)
f.nativeordercancelreject_clientOrderId = ProtoField.string("native.nativeordercancelreject.clientOrderId", "ClientOrderId")
f.nativeordercancelreject_rejectCode = ProtoField.int32("native.nativeordercancelreject.rejectCode", "RejectCode", base.DEC)
f.nativeordercancelreject_orderBook = ProtoField.uint8("native.nativeordercancelreject.orderBook", "OrderBook", base.DEC)
f.nativeordercancelreject_transactTime = ProtoField.uint64("native.nativeordercancelreject.transactTime", "TransactTime", base.DEC)
f.nativeordercancelreject_senderLocation = ProtoField.string("native.nativeordercancelreject.senderLocation", "SenderLocation")
f.nativenewordersingle_limitPrice = ProtoField.int32("native.nativenewordersingle.limitPrice", "LimitPrice", base.DEC)
f.nativenewordersingle_orderQuantity = ProtoField.uint32("native.nativenewordersingle.orderQuantity", "OrderQuantity", base.DEC)
f.nativenewordersingle_instrumentId = ProtoField.uint32("native.nativenewordersingle.instrumentId", "InstrumentId", base.DEC)
f.nativenewordersingle_side = ProtoField.uint8("native.nativenewordersingle.side", "Side", base.DEC)
f.nativenewordersingle_enteringTrader = ProtoField.string("native.nativenewordersingle.enteringTrader", "EnteringTrader")
f.nativenewordersingle_clientOrderId = ProtoField.string("native.nativenewordersingle.clientOrderId", "ClientOrderId")
f.nativenewordersingle_account = ProtoField.uint64("native.nativenewordersingle.account", "Account", base.DEC)
f.nativenewordersingle_senderLocation = ProtoField.string("native.nativenewordersingle.senderLocation", "SenderLocation")
f.nativenewordersingle_noTradeKey = ProtoField.string("native.nativenewordersingle.noTradeKey", "NoTradeKey")
f.nativenewordersingle_stopPrice = ProtoField.int32("native.nativenewordersingle.stopPrice", "StopPrice", base.DEC)
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
f.nativeorderreplacerequest_limitPrice = ProtoField.int32("native.nativeorderreplacerequest.limitPrice", "LimitPrice", base.DEC)
f.nativeorderreplacerequest_orderQuantity = ProtoField.uint32("native.nativeorderreplacerequest.orderQuantity", "OrderQuantity", base.DEC)
f.nativeorderreplacerequest_instrumentId = ProtoField.uint32("native.nativeorderreplacerequest.instrumentId", "InstrumentId", base.DEC)
f.nativeorderreplacerequest_side = ProtoField.uint8("native.nativeorderreplacerequest.side", "Side", base.DEC)
f.nativeorderreplacerequest_clientOrderId = ProtoField.string("native.nativeorderreplacerequest.clientOrderId", "ClientOrderId")
f.nativeorderreplacerequest_origClientOrderId = ProtoField.string("native.nativeorderreplacerequest.origClientOrderId", "OrigClientOrderId")
f.nativeorderreplacerequest_orderId = ProtoField.string("native.nativeorderreplacerequest.orderId", "OrderId")
f.nativeorderreplacerequest_account = ProtoField.uint64("native.nativeorderreplacerequest.account", "Account", base.DEC)
f.nativeorderreplacerequest_senderLocation = ProtoField.string("native.nativeorderreplacerequest.senderLocation", "SenderLocation")
f.nativeorderreplacerequest_stopPrice = ProtoField.int32("native.nativeorderreplacerequest.stopPrice", "StopPrice", base.DEC)
f.nativeorderreplacerequest_displayQuantity = ProtoField.uint32("native.nativeorderreplacerequest.displayQuantity", "DisplayQuantity", base.DEC)
f.nativeorderreplacerequest_orderBook = ProtoField.uint8("native.nativeorderreplacerequest.orderBook", "OrderBook", base.DEC)
f.nativeorderreplacerequest_executionInstruction = ProtoField.uint8("native.nativeorderreplacerequest.executionInstruction", "ExecutionInstruction", base.DEC)
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
f.nativenewordercross_limitPrice = ProtoField.int32("native.nativenewordercross.limitPrice", "LimitPrice", base.DEC)
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
f.nativenewordercross_memo = ProtoField.string("native.nativenewordercross.memo", "Memo")
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
f.nativequotestatusreport_orderBook = ProtoField.uint32("native.nativequotestatusreport.orderBook", "OrderBook", base.DEC)
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
    ["u"] = "NativeNewOrderCross",
    ["j"] = "NativeBusinessReject",
    ["q"] = "NativeMassCancelRequest",
    ["r"] = "NativeMassCancelReport",
    ["CB"] = "NativeNews",
    ["i"] = "NativeMassQuote",
    ["b"] = "NativeMassQuoteAcknowledgement",
    ["AI"] = "NativeQuoteStatusReport",
}

function native_proto.dissector(buffer, pinfo, tree)
    local length = buffer:len()
    if length == 0 then return end

    pinfo.cols.protocol = native_proto.name

    local subtree = tree:add(native_proto, buffer(), "Native Protocol")
    local offset = 0

    local start = buffer(offset, 1):uint()
    subtree:add(f.start, buffer(offset, 1))
    offset = offset + 1

    local msg_length = buffer(offset, 2):le_uint()
    subtree:add(f.length, buffer(offset, 2))
    offset = offset + 2

    local msgtype = buffer(offset, 1):string()
    subtree:add(f.msgtype, buffer(offset, 1))
    offset = offset + 1

    local msg_name = message_types[msgtype] or "Unknown"
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
        do local v = buffer(offset, 4):le_int() subtree:add(f.nativeexecutionreport_executedPrice, buffer(offset, 4)):set_text("ExecutedPrice: " .. string.format("%.4f", v / 10000000.0)) end
        offset = offset + 4
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
    end

    if msgtype == "D" then
        -- NativeNewOrderSingle
        do local v = buffer(offset, 4):le_int() subtree:add(f.nativenewordersingle_limitPrice, buffer(offset, 4)):set_text("LimitPrice: " .. string.format("%.4f", v / 10000000.0)) end
        offset = offset + 4
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
        do local v = buffer(offset, 4):le_int() subtree:add(f.nativenewordersingle_stopPrice, buffer(offset, 4)):set_text("StopPrice: " .. string.format("%.4f", v / 10000000.0)) end
        offset = offset + 4
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
        do local v = buffer(offset, 4):le_int() subtree:add(f.nativeorderreplacerequest_limitPrice, buffer(offset, 4)):set_text("LimitPrice: " .. string.format("%.4f", v / 10000000.0)) end
        offset = offset + 4
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
        subtree:add(f.nativeorderreplacerequest_orderId, buffer(offset, 12))
        offset = offset + 12
        subtree:add(f.nativeorderreplacerequest_account, buffer(offset, 8))
        offset = offset + 8
        subtree:add(f.nativeorderreplacerequest_senderLocation, buffer(offset, 10))
        offset = offset + 10
        do local v = buffer(offset, 4):le_int() subtree:add(f.nativeorderreplacerequest_stopPrice, buffer(offset, 4)):set_text("StopPrice: " .. string.format("%.4f", v / 10000000.0)) end
        offset = offset + 4
        subtree:add(f.nativeorderreplacerequest_displayQuantity, buffer(offset, 4))
        offset = offset + 4
        subtree:add(f.nativeorderreplacerequest_orderBook, buffer(offset, 1))
        offset = offset + 1
        subtree:add(f.nativeorderreplacerequest_executionInstruction, buffer(offset, 1))
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

    if msgtype == "u" then
        -- NativeNewOrderCross
        subtree:add(f.nativenewordercross_crossId, buffer(offset, 10))
        offset = offset + 10
        do local v = buffer(offset, 4):le_int() subtree:add(f.nativenewordercross_limitPrice, buffer(offset, 4)):set_text("LimitPrice: " .. string.format("%.4f", v / 10000000.0)) end
        offset = offset + 4
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
        subtree:add(f.nativenewordercross_memo, buffer(offset, 15))
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

    if msgtype == "CB" then
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

    if msgtype == "b" then
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

    if msgtype == "AI" then
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
        subtree:add(f.nativequotestatusreport_orderBook, buffer(offset, 4))
        offset = offset + 4
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

end

local tcp_port = DissectorTable.get("tcp.port")
tcp_port:add(9999, native_proto)
