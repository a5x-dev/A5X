-- MITCH Protocol Dissector
-- Auto-generated from XML definition

local mitch_proto = Proto("MITCH", "A5X MITCH Protocol")

-- Protocol fields
-- Unit Header fields
local f_unit_length = ProtoField.uint16("mitch.unit.length", "Unit Length", base.DEC)
local f_unit_msg_count = ProtoField.uint8("mitch.unit.msgcount", "Message Count", base.DEC)
local f_unit_mdg = ProtoField.uint8("mitch.unit.mdg", "Market Data Group", base.HEX)
local f_unit_seqnum = ProtoField.uint64("mitch.unit.seqnum", "Sequence Number", base.DEC)
-- Message fields
local f_msg_type = ProtoField.uint8("mitch.msgtype", "Message Type", base.HEX)
local f_msg_length = ProtoField.uint16("mitch.length", "Message Length", base.DEC)

-- Message-specific fields
local fields = {}
fields.f_mitchtime_seconds = ProtoField.uint32("mitch.mitchtime.seconds", "seconds", base.DEC)
fields.f_mitchsystemevent_nanosecond = ProtoField.uint32("mitch.mitchsystemevent.nanosecond", "nanosecond", base.DEC)
fields.f_mitchsystemevent_eventcode = ProtoField.uint8("mitch.mitchsystemevent.eventcode", "eventCode", base.HEX)
fields.f_mitchaddorder_nanosecond = ProtoField.uint32("mitch.mitchaddorder.nanosecond", "nanosecond", base.DEC)
fields.f_mitchaddorder_orderid = ProtoField.uint64("mitch.mitchaddorder.orderid", "orderId", base.DEC)
fields.f_mitchaddorder_side = ProtoField.uint8("mitch.mitchaddorder.side", "side", base.HEX)
fields.f_mitchaddorder_quantity = ProtoField.uint32("mitch.mitchaddorder.quantity", "quantity", base.DEC)
fields.f_mitchaddorder_instrumentid = ProtoField.uint32("mitch.mitchaddorder.instrumentid", "instrumentId", base.DEC)
fields.f_mitchaddorder_price = ProtoField.int64("mitch.mitchaddorder.price", "price", base.DEC)
fields.f_mitchaddorder_flags = ProtoField.bytes("mitch.mitchaddorder.flags", "flags", base.NONE)
fields.f_mitchaddattributedorder_nanosecond = ProtoField.uint32("mitch.mitchaddattributedorder.nanosecond", "nanosecond", base.DEC)
fields.f_mitchaddattributedorder_orderid = ProtoField.uint64("mitch.mitchaddattributedorder.orderid", "orderId", base.DEC)
fields.f_mitchaddattributedorder_side = ProtoField.uint8("mitch.mitchaddattributedorder.side", "side", base.HEX)
fields.f_mitchaddattributedorder_quantity = ProtoField.uint32("mitch.mitchaddattributedorder.quantity", "quantity", base.DEC)
fields.f_mitchaddattributedorder_instrumentid = ProtoField.uint32("mitch.mitchaddattributedorder.instrumentid", "instrumentId", base.DEC)
fields.f_mitchaddattributedorder_price = ProtoField.int64("mitch.mitchaddattributedorder.price", "price", base.DEC)
fields.f_mitchaddattributedorder_firm = ProtoField.string("mitch.mitchaddattributedorder.firm", "firm", base.ASCII)
fields.f_mitchaddattributedorder_flags = ProtoField.bytes("mitch.mitchaddattributedorder.flags", "flags", base.NONE)
fields.f_mitchorderdeleted_nanosecond = ProtoField.uint32("mitch.mitchorderdeleted.nanosecond", "nanosecond", base.DEC)
fields.f_mitchorderdeleted_orderid = ProtoField.uint64("mitch.mitchorderdeleted.orderid", "orderId", base.DEC)
fields.f_mitchorderdeleted_oldquantity = ProtoField.uint32("mitch.mitchorderdeleted.oldquantity", "oldQuantity", base.DEC)
fields.f_mitchorderdeleted_oldprice = ProtoField.int64("mitch.mitchorderdeleted.oldprice", "oldPrice", base.DEC)
fields.f_mitchordermodified_nanosecond = ProtoField.uint32("mitch.mitchordermodified.nanosecond", "nanosecond", base.DEC)
fields.f_mitchordermodified_orderid = ProtoField.uint64("mitch.mitchordermodified.orderid", "orderId", base.DEC)
fields.f_mitchordermodified_newquantity = ProtoField.uint32("mitch.mitchordermodified.newquantity", "newQuantity", base.DEC)
fields.f_mitchordermodified_newprice = ProtoField.int64("mitch.mitchordermodified.newprice", "newPrice", base.DEC)
fields.f_mitchordermodified_oldquantity = ProtoField.uint32("mitch.mitchordermodified.oldquantity", "oldQuantity", base.DEC)
fields.f_mitchordermodified_oldprice = ProtoField.int64("mitch.mitchordermodified.oldprice", "oldPrice", base.DEC)
fields.f_mitchordermodified_flags = ProtoField.bytes("mitch.mitchordermodified.flags", "flags", base.NONE)
fields.f_mitchorderbookclear_nanosecond = ProtoField.uint32("mitch.mitchorderbookclear.nanosecond", "nanosecond", base.DEC)
fields.f_mitchorderbookclear_instrumentid = ProtoField.uint32("mitch.mitchorderbookclear.instrumentid", "instrumentId", base.DEC)
fields.f_mitchorderbookclear_subbook = ProtoField.uint8("mitch.mitchorderbookclear.subbook", "subBook", base.DEC)
fields.f_mitchorderbookclear_booktype = ProtoField.uint8("mitch.mitchorderbookclear.booktype", "bookType", base.HEX)
fields.f_mitchorderexecuted_nanosecond = ProtoField.uint32("mitch.mitchorderexecuted.nanosecond", "nanosecond", base.DEC)
fields.f_mitchorderexecuted_orderid = ProtoField.uint64("mitch.mitchorderexecuted.orderid", "orderId", base.DEC)
fields.f_mitchorderexecuted_executedquantity = ProtoField.uint32("mitch.mitchorderexecuted.executedquantity", "executedQuantity", base.DEC)
fields.f_mitchorderexecuted_price = ProtoField.int64("mitch.mitchorderexecuted.price", "price", base.DEC)
fields.f_mitchorderexecuted_instrumentid = ProtoField.uint32("mitch.mitchorderexecuted.instrumentid", "instrumentId", base.DEC)
fields.f_mitchorderexecuted_tradeid = ProtoField.uint64("mitch.mitchorderexecuted.tradeid", "tradeId", base.DEC)
fields.f_mitchorderexecuted_lastoptpx = ProtoField.int64("mitch.mitchorderexecuted.lastoptpx", "lastOptPx", base.DEC)
fields.f_mitchorderexecuted_volatility = ProtoField.int64("mitch.mitchorderexecuted.volatility", "volatility", base.DEC)
fields.f_mitchorderexecuted_underlyingreferenceprice = ProtoField.int64("mitch.mitchorderexecuted.underlyingreferenceprice", "underlyingReferencePrice", base.DEC)
fields.f_mitchorderexecuted_tradingdatetime = ProtoField.uint64("mitch.mitchorderexecuted.tradingdatetime", "tradingDateTime", base.DEC)
fields.f_mitchorderexecuted_instrumentidentificationcode = ProtoField.string("mitch.mitchorderexecuted.instrumentidentificationcode", "instrumentIdentificationCode", base.ASCII)
fields.f_mitchorderexecuted_instrumentidentificationcodetype = ProtoField.uint8("mitch.mitchorderexecuted.instrumentidentificationcodetype", "instrumentIdentificationCodeType", base.DEC)
fields.f_mitchorderexecuted_currency = ProtoField.string("mitch.mitchorderexecuted.currency", "currency", base.ASCII)
fields.f_mitchorderexecuted_venueofexecution = ProtoField.string("mitch.mitchorderexecuted.venueofexecution", "venueOfExecution", base.ASCII)
fields.f_mitchorderexecuted_pricenotation = ProtoField.uint8("mitch.mitchorderexecuted.pricenotation", "priceNotation", base.DEC)
fields.f_mitchorderexecuted_notionalamount = ProtoField.int64("mitch.mitchorderexecuted.notionalamount", "notionalAmount", base.DEC)
fields.f_mitchorderexecuted_notionalcurrency = ProtoField.string("mitch.mitchorderexecuted.notionalcurrency", "notionalCurrency", base.ASCII)
fields.f_mitchorderexecuted_publicationdatetime = ProtoField.uint64("mitch.mitchorderexecuted.publicationdatetime", "publicationDateTime", base.DEC)
fields.f_mitchorderexecuted_firm = ProtoField.string("mitch.mitchorderexecuted.firm", "firm", base.ASCII)
fields.f_mitchorderexecuted_contrafirm = ProtoField.string("mitch.mitchorderexecuted.contrafirm", "contraFirm", base.ASCII)
fields.f_mitchorderexecutedwithpricesize_nanosecond = ProtoField.uint32("mitch.mitchorderexecutedwithpricesize.nanosecond", "nanosecond", base.DEC)
fields.f_mitchorderexecutedwithpricesize_orderid = ProtoField.uint64("mitch.mitchorderexecutedwithpricesize.orderid", "orderId", base.DEC)
fields.f_mitchorderexecutedwithpricesize_executedquantity = ProtoField.uint32("mitch.mitchorderexecutedwithpricesize.executedquantity", "executedQuantity", base.DEC)
fields.f_mitchorderexecutedwithpricesize_price = ProtoField.int64("mitch.mitchorderexecutedwithpricesize.price", "price", base.DEC)
fields.f_mitchorderexecutedwithpricesize_instrumentid = ProtoField.uint32("mitch.mitchorderexecutedwithpricesize.instrumentid", "instrumentId", base.DEC)
fields.f_mitchorderexecutedwithpricesize_tradeid = ProtoField.uint64("mitch.mitchorderexecutedwithpricesize.tradeid", "tradeId", base.DEC)
fields.f_mitchorderexecutedwithpricesize_lastoptpx = ProtoField.int64("mitch.mitchorderexecutedwithpricesize.lastoptpx", "lastOptPx", base.DEC)
fields.f_mitchorderexecutedwithpricesize_volatility = ProtoField.int64("mitch.mitchorderexecutedwithpricesize.volatility", "volatility", base.DEC)
fields.f_mitchorderexecutedwithpricesize_underlyingreferenceprice = ProtoField.int64("mitch.mitchorderexecutedwithpricesize.underlyingreferenceprice", "underlyingReferencePrice", base.DEC)
fields.f_mitchorderexecutedwithpricesize_tradingdatetime = ProtoField.uint64("mitch.mitchorderexecutedwithpricesize.tradingdatetime", "tradingDateTime", base.DEC)
fields.f_mitchorderexecutedwithpricesize_instrumentidentificationcode = ProtoField.string("mitch.mitchorderexecutedwithpricesize.instrumentidentificationcode", "instrumentIdentificationCode", base.ASCII)
fields.f_mitchorderexecutedwithpricesize_instrumentidentificationcodetype = ProtoField.uint8("mitch.mitchorderexecutedwithpricesize.instrumentidentificationcodetype", "instrumentIdentificationCodeType", base.DEC)
fields.f_mitchorderexecutedwithpricesize_currency = ProtoField.string("mitch.mitchorderexecutedwithpricesize.currency", "currency", base.ASCII)
fields.f_mitchorderexecutedwithpricesize_venueofexecution = ProtoField.string("mitch.mitchorderexecutedwithpricesize.venueofexecution", "venueOfExecution", base.ASCII)
fields.f_mitchorderexecutedwithpricesize_pricenotation = ProtoField.uint8("mitch.mitchorderexecutedwithpricesize.pricenotation", "priceNotation", base.DEC)
fields.f_mitchorderexecutedwithpricesize_notionalamount = ProtoField.int64("mitch.mitchorderexecutedwithpricesize.notionalamount", "notionalAmount", base.DEC)
fields.f_mitchorderexecutedwithpricesize_notionalcurrency = ProtoField.string("mitch.mitchorderexecutedwithpricesize.notionalcurrency", "notionalCurrency", base.ASCII)
fields.f_mitchorderexecutedwithpricesize_publicationdatetime = ProtoField.uint64("mitch.mitchorderexecutedwithpricesize.publicationdatetime", "publicationDateTime", base.DEC)
fields.f_mitchorderexecutedwithpricesize_firm = ProtoField.string("mitch.mitchorderexecutedwithpricesize.firm", "firm", base.ASCII)
fields.f_mitchorderexecutedwithpricesize_contrafirm = ProtoField.string("mitch.mitchorderexecutedwithpricesize.contrafirm", "contraFirm", base.ASCII)
fields.f_mitchorderexecutedwithpricesize_displayquantity = ProtoField.uint32("mitch.mitchorderexecutedwithpricesize.displayquantity", "displayQuantity", base.DEC)
fields.f_mitchorderexecutedwithpricesize_printable = ProtoField.uint8("mitch.mitchorderexecutedwithpricesize.printable", "printable", base.HEX)
fields.f_mitchauctioninfo_nanosecond = ProtoField.uint32("mitch.mitchauctioninfo.nanosecond", "nanosecond", base.DEC)
fields.f_mitchauctioninfo_pairedquantity = ProtoField.uint32("mitch.mitchauctioninfo.pairedquantity", "pairedQuantity", base.DEC)
fields.f_mitchauctioninfo_imbalancequantity = ProtoField.uint32("mitch.mitchauctioninfo.imbalancequantity", "imbalanceQuantity", base.DEC)
fields.f_mitchauctioninfo_imbalancedirection = ProtoField.uint8("mitch.mitchauctioninfo.imbalancedirection", "imbalanceDirection", base.HEX)
fields.f_mitchauctioninfo_instrumentid = ProtoField.uint32("mitch.mitchauctioninfo.instrumentid", "instrumentId", base.DEC)
fields.f_mitchauctioninfo_price = ProtoField.int64("mitch.mitchauctioninfo.price", "price", base.DEC)
fields.f_mitchauctioninfo_auctiontype = ProtoField.uint8("mitch.mitchauctioninfo.auctiontype", "auctionType", base.HEX)
fields.f_mitchauctiontrade_nanosecond = ProtoField.uint32("mitch.mitchauctiontrade.nanosecond", "nanosecond", base.DEC)
fields.f_mitchauctiontrade_executedquantity = ProtoField.uint32("mitch.mitchauctiontrade.executedquantity", "executedQuantity", base.DEC)
fields.f_mitchauctiontrade_price = ProtoField.int64("mitch.mitchauctiontrade.price", "price", base.DEC)
fields.f_mitchauctiontrade_instrumentid = ProtoField.uint32("mitch.mitchauctiontrade.instrumentid", "instrumentId", base.DEC)
fields.f_mitchauctiontrade_tradeid = ProtoField.uint64("mitch.mitchauctiontrade.tradeid", "tradeId", base.DEC)
fields.f_mitchauctiontrade_lastoptpx = ProtoField.int64("mitch.mitchauctiontrade.lastoptpx", "lastOptPx", base.DEC)
fields.f_mitchauctiontrade_volatility = ProtoField.int64("mitch.mitchauctiontrade.volatility", "volatility", base.DEC)
fields.f_mitchauctiontrade_underlyingreferenceprice = ProtoField.int64("mitch.mitchauctiontrade.underlyingreferenceprice", "underlyingReferencePrice", base.DEC)
fields.f_mitchauctiontrade_tradingdatetime = ProtoField.uint64("mitch.mitchauctiontrade.tradingdatetime", "tradingDateTime", base.DEC)
fields.f_mitchauctiontrade_instrumentidentificationcode = ProtoField.string("mitch.mitchauctiontrade.instrumentidentificationcode", "instrumentIdentificationCode", base.ASCII)
fields.f_mitchauctiontrade_instrumentidentificationcodetype = ProtoField.uint8("mitch.mitchauctiontrade.instrumentidentificationcodetype", "instrumentIdentificationCodeType", base.DEC)
fields.f_mitchauctiontrade_currency = ProtoField.string("mitch.mitchauctiontrade.currency", "currency", base.ASCII)
fields.f_mitchauctiontrade_venueofexecution = ProtoField.string("mitch.mitchauctiontrade.venueofexecution", "venueOfExecution", base.ASCII)
fields.f_mitchauctiontrade_pricenotation = ProtoField.uint8("mitch.mitchauctiontrade.pricenotation", "priceNotation", base.DEC)
fields.f_mitchauctiontrade_notionalamount = ProtoField.int64("mitch.mitchauctiontrade.notionalamount", "notionalAmount", base.DEC)
fields.f_mitchauctiontrade_notionalcurrency = ProtoField.string("mitch.mitchauctiontrade.notionalcurrency", "notionalCurrency", base.ASCII)
fields.f_mitchauctiontrade_publicationdatetime = ProtoField.uint64("mitch.mitchauctiontrade.publicationdatetime", "publicationDateTime", base.DEC)
fields.f_mitchauctiontrade_cancellationflag = ProtoField.string("mitch.mitchauctiontrade.cancellationflag", "cancellationFlag", base.ASCII)
fields.f_mitchauctiontrade_amendmentflag = ProtoField.string("mitch.mitchauctiontrade.amendmentflag", "amendmentFlag", base.ASCII)
fields.f_mitchauctiontrade_auctiontype = ProtoField.uint8("mitch.mitchauctiontrade.auctiontype", "auctionType", base.HEX)
fields.f_mitchrecoverytrade_nanosecond = ProtoField.uint32("mitch.mitchrecoverytrade.nanosecond", "nanosecond", base.DEC)
fields.f_mitchrecoverytrade_orderid = ProtoField.uint64("mitch.mitchrecoverytrade.orderid", "orderId", base.DEC)
fields.f_mitchrecoverytrade_executedquantity = ProtoField.uint32("mitch.mitchrecoverytrade.executedquantity", "executedQuantity", base.DEC)
fields.f_mitchrecoverytrade_price = ProtoField.int64("mitch.mitchrecoverytrade.price", "price", base.DEC)
fields.f_mitchrecoverytrade_instrumentid = ProtoField.uint32("mitch.mitchrecoverytrade.instrumentid", "instrumentId", base.DEC)
fields.f_mitchrecoverytrade_tradeid = ProtoField.uint64("mitch.mitchrecoverytrade.tradeid", "tradeId", base.DEC)
fields.f_mitchrecoverytrade_lastoptpx = ProtoField.int64("mitch.mitchrecoverytrade.lastoptpx", "lastOptPx", base.DEC)
fields.f_mitchrecoverytrade_volatility = ProtoField.int64("mitch.mitchrecoverytrade.volatility", "volatility", base.DEC)
fields.f_mitchrecoverytrade_underlyingreferenceprice = ProtoField.int64("mitch.mitchrecoverytrade.underlyingreferenceprice", "underlyingReferencePrice", base.DEC)
fields.f_mitchrecoverytrade_tradingdatetime = ProtoField.uint64("mitch.mitchrecoverytrade.tradingdatetime", "tradingDateTime", base.DEC)
fields.f_mitchrecoverytrade_instrumentidentificationcode = ProtoField.string("mitch.mitchrecoverytrade.instrumentidentificationcode", "instrumentIdentificationCode", base.ASCII)
fields.f_mitchrecoverytrade_instrumentidentificationcodetype = ProtoField.uint8("mitch.mitchrecoverytrade.instrumentidentificationcodetype", "instrumentIdentificationCodeType", base.DEC)
fields.f_mitchrecoverytrade_currency = ProtoField.string("mitch.mitchrecoverytrade.currency", "currency", base.ASCII)
fields.f_mitchrecoverytrade_venueofexecution = ProtoField.string("mitch.mitchrecoverytrade.venueofexecution", "venueOfExecution", base.ASCII)
fields.f_mitchrecoverytrade_pricenotation = ProtoField.uint8("mitch.mitchrecoverytrade.pricenotation", "priceNotation", base.DEC)
fields.f_mitchrecoverytrade_notionalamount = ProtoField.int64("mitch.mitchrecoverytrade.notionalamount", "notionalAmount", base.DEC)
fields.f_mitchrecoverytrade_notionalcurrency = ProtoField.string("mitch.mitchrecoverytrade.notionalcurrency", "notionalCurrency", base.ASCII)
fields.f_mitchrecoverytrade_publicationdatetime = ProtoField.uint64("mitch.mitchrecoverytrade.publicationdatetime", "publicationDateTime", base.DEC)
fields.f_mitchrecoverytrade_firm = ProtoField.string("mitch.mitchrecoverytrade.firm", "firm", base.ASCII)
fields.f_mitchrecoverytrade_contrafirm = ProtoField.string("mitch.mitchrecoverytrade.contrafirm", "contraFirm", base.ASCII)
fields.f_mitchrecoverytrade_cancellationflag = ProtoField.string("mitch.mitchrecoverytrade.cancellationflag", "cancellationFlag", base.ASCII)
fields.f_mitchrecoverytrade_amendmentflag = ProtoField.string("mitch.mitchrecoverytrade.amendmentflag", "amendmentFlag", base.ASCII)
fields.f_mitchrecoverytrade_subbook = ProtoField.uint8("mitch.mitchrecoverytrade.subbook", "subBook", base.DEC)
fields.f_mitchrecoverytrade_flags = ProtoField.bytes("mitch.mitchrecoverytrade.flags", "flags", base.NONE)
fields.f_mitchrecoverytrade_auctiontype = ProtoField.uint8("mitch.mitchrecoverytrade.auctiontype", "auctionType", base.HEX)
fields.f_mitchstatistics_nanosecond = ProtoField.uint32("mitch.mitchstatistics.nanosecond", "nanosecond", base.DEC)
fields.f_mitchstatistics_instrumentid = ProtoField.uint32("mitch.mitchstatistics.instrumentid", "instrumentId", base.DEC)
fields.f_mitchstatistics_statistictype = ProtoField.uint8("mitch.mitchstatistics.statistictype", "statisticType", base.HEX)
fields.f_mitchstatistics_price = ProtoField.int64("mitch.mitchstatistics.price", "price", base.DEC)
fields.f_mitchstatistics_opencloseindicator = ProtoField.uint8("mitch.mitchstatistics.opencloseindicator", "openCloseIndicator", base.HEX)
fields.f_mitchstatistics_subbook = ProtoField.uint8("mitch.mitchstatistics.subbook", "subBook", base.DEC)
fields.f_mitchextendedstatistics_nanosecond = ProtoField.uint32("mitch.mitchextendedstatistics.nanosecond", "nanosecond", base.DEC)
fields.f_mitchextendedstatistics_instrumentid = ProtoField.uint32("mitch.mitchextendedstatistics.instrumentid", "instrumentId", base.DEC)
fields.f_mitchextendedstatistics_highprice = ProtoField.int64("mitch.mitchextendedstatistics.highprice", "highPrice", base.DEC)
fields.f_mitchextendedstatistics_lowprice = ProtoField.int64("mitch.mitchextendedstatistics.lowprice", "lowPrice", base.DEC)
fields.f_mitchextendedstatistics_vwap = ProtoField.int64("mitch.mitchextendedstatistics.vwap", "vwap", base.DEC)
fields.f_mitchextendedstatistics_volume = ProtoField.uint32("mitch.mitchextendedstatistics.volume", "volume", base.DEC)
fields.f_mitchextendedstatistics_turnover = ProtoField.int64("mitch.mitchextendedstatistics.turnover", "turnover", base.DEC)
fields.f_mitchextendedstatistics_numberoftrades = ProtoField.uint32("mitch.mitchextendedstatistics.numberoftrades", "numberOfTrades", base.DEC)
fields.f_mitchextendedstatistics_reservedfield = ProtoField.bytes("mitch.mitchextendedstatistics.reservedfield", "reservedField", base.SPACE)
fields.f_mitchextendedstatistics_subbook = ProtoField.uint8("mitch.mitchextendedstatistics.subbook", "subBook", base.DEC)
fields.f_mitchextendedstatistics_notionalexposure = ProtoField.int64("mitch.mitchextendedstatistics.notionalexposure", "notionalExposure", base.DEC)
fields.f_mitchextendedstatistics_notionaldeltaexposure = ProtoField.int64("mitch.mitchextendedstatistics.notionaldeltaexposure", "notionalDeltaExposure", base.DEC)
fields.f_mitchextendedstatistics_theoreticalprice = ProtoField.int64("mitch.mitchextendedstatistics.theoreticalprice", "theoreticalPrice", base.DEC)
fields.f_mitchextendedstatistics_volatility = ProtoField.int64("mitch.mitchextendedstatistics.volatility", "volatility", base.DEC)
fields.f_mitchextendedstatistics_upperdynamicpblimit = ProtoField.int64("mitch.mitchextendedstatistics.upperdynamicpblimit", "upperDynamicPBLimit", base.DEC)
fields.f_mitchextendedstatistics_lowerdynamicpblimit = ProtoField.int64("mitch.mitchextendedstatistics.lowerdynamicpblimit", "lowerDynamicPBLimit", base.DEC)
fields.f_mitchextendedstatistics_upperstaticpblimit = ProtoField.int64("mitch.mitchextendedstatistics.upperstaticpblimit", "upperStaticPBLimit", base.DEC)
fields.f_mitchextendedstatistics_lowerstaticpblimit = ProtoField.int64("mitch.mitchextendedstatistics.lowerstaticpblimit", "lowerStaticPBLimit", base.DEC)
fields.f_mitchextendedstatistics_upperdynamiccblimit = ProtoField.int64("mitch.mitchextendedstatistics.upperdynamiccblimit", "upperDynamicCBLimit", base.DEC)
fields.f_mitchextendedstatistics_lowerdynamiccblimit = ProtoField.int64("mitch.mitchextendedstatistics.lowerdynamiccblimit", "lowerDynamicCBLimit", base.DEC)
fields.f_mitchsymbolstatus_nanosecond = ProtoField.uint32("mitch.mitchsymbolstatus.nanosecond", "nanosecond", base.DEC)
fields.f_mitchsymbolstatus_instrumentid = ProtoField.uint32("mitch.mitchsymbolstatus.instrumentid", "instrumentId", base.DEC)
fields.f_mitchsymbolstatus_tradingstatus = ProtoField.uint8("mitch.mitchsymbolstatus.tradingstatus", "tradingStatus", base.HEX)
fields.f_mitchsymbolstatus_flags = ProtoField.bytes("mitch.mitchsymbolstatus.flags", "flags", base.NONE)
fields.f_mitchsymbolstatus_haltreason = ProtoField.string("mitch.mitchsymbolstatus.haltreason", "haltReason", base.ASCII)
fields.f_mitchsymbolstatus_sessionchangereason = ProtoField.uint8("mitch.mitchsymbolstatus.sessionchangereason", "sessionChangeReason", base.DEC)
fields.f_mitchsymbolstatus_newendtime = ProtoField.uint64("mitch.mitchsymbolstatus.newendtime", "newEndTime", base.DEC)
fields.f_mitchsymbolstatus_subbook = ProtoField.uint8("mitch.mitchsymbolstatus.subbook", "subBook", base.DEC)
fields.f_mitchtopofbook_nanosecond = ProtoField.uint32("mitch.mitchtopofbook.nanosecond", "nanosecond", base.DEC)
fields.f_mitchtopofbook_instrumentid = ProtoField.uint32("mitch.mitchtopofbook.instrumentid", "instrumentId", base.DEC)
fields.f_mitchtopofbook_subbook = ProtoField.bytes("mitch.mitchtopofbook.subbook", "subBook", base.NONE)
fields.f_mitchtopofbook_action = ProtoField.uint8("mitch.mitchtopofbook.action", "action", base.HEX)
fields.f_mitchtopofbook_side = ProtoField.uint8("mitch.mitchtopofbook.side", "side", base.HEX)
fields.f_mitchtopofbook_price = ProtoField.int64("mitch.mitchtopofbook.price", "price", base.DEC)
fields.f_mitchtopofbook_quantity = ProtoField.uint32("mitch.mitchtopofbook.quantity", "quantity", base.DEC)
fields.f_mitchtopofbook_marketorderquantity = ProtoField.uint32("mitch.mitchtopofbook.marketorderquantity", "marketOrderQuantity", base.DEC)
fields.f_mitchtopofbook_reservedfield = ProtoField.bytes("mitch.mitchtopofbook.reservedfield", "reservedField", base.SPACE)
fields.f_mitchtopofbook_splits = ProtoField.uint32("mitch.mitchtopofbook.splits", "splits", base.DEC)
fields.f_mitchtrade_nanosecond = ProtoField.uint32("mitch.mitchtrade.nanosecond", "nanosecond", base.DEC)
fields.f_mitchtrade_orderid = ProtoField.uint64("mitch.mitchtrade.orderid", "orderId", base.DEC)
fields.f_mitchtrade_executedquantity = ProtoField.uint32("mitch.mitchtrade.executedquantity", "executedQuantity", base.DEC)
fields.f_mitchtrade_price = ProtoField.int64("mitch.mitchtrade.price", "price", base.DEC)
fields.f_mitchtrade_instrumentid = ProtoField.uint32("mitch.mitchtrade.instrumentid", "instrumentId", base.DEC)
fields.f_mitchtrade_tradeid = ProtoField.uint64("mitch.mitchtrade.tradeid", "tradeId", base.DEC)
fields.f_mitchtrade_lastoptpx = ProtoField.int64("mitch.mitchtrade.lastoptpx", "lastOptPx", base.DEC)
fields.f_mitchtrade_volatility = ProtoField.int64("mitch.mitchtrade.volatility", "volatility", base.DEC)
fields.f_mitchtrade_underlyingreferenceprice = ProtoField.int64("mitch.mitchtrade.underlyingreferenceprice", "underlyingReferencePrice", base.DEC)
fields.f_mitchtrade_tradingdatetime = ProtoField.uint64("mitch.mitchtrade.tradingdatetime", "tradingDateTime", base.DEC)
fields.f_mitchtrade_instrumentidentificationcode = ProtoField.string("mitch.mitchtrade.instrumentidentificationcode", "instrumentIdentificationCode", base.ASCII)
fields.f_mitchtrade_instrumentidentificationcodetype = ProtoField.uint8("mitch.mitchtrade.instrumentidentificationcodetype", "instrumentIdentificationCodeType", base.DEC)
fields.f_mitchtrade_currency = ProtoField.string("mitch.mitchtrade.currency", "currency", base.ASCII)
fields.f_mitchtrade_venueofexecution = ProtoField.string("mitch.mitchtrade.venueofexecution", "venueOfExecution", base.ASCII)
fields.f_mitchtrade_pricenotation = ProtoField.uint8("mitch.mitchtrade.pricenotation", "priceNotation", base.DEC)
fields.f_mitchtrade_notionalamount = ProtoField.int64("mitch.mitchtrade.notionalamount", "notionalAmount", base.DEC)
fields.f_mitchtrade_notionalcurrency = ProtoField.string("mitch.mitchtrade.notionalcurrency", "notionalCurrency", base.ASCII)
fields.f_mitchtrade_publicationdatetime = ProtoField.uint64("mitch.mitchtrade.publicationdatetime", "publicationDateTime", base.DEC)
fields.f_mitchtrade_firm = ProtoField.string("mitch.mitchtrade.firm", "firm", base.ASCII)
fields.f_mitchtrade_contrafirm = ProtoField.string("mitch.mitchtrade.contrafirm", "contraFirm", base.ASCII)
fields.f_mitchtrade_cancellationflag = ProtoField.string("mitch.mitchtrade.cancellationflag", "cancellationFlag", base.ASCII)
fields.f_mitchtrade_amendmentflag = ProtoField.string("mitch.mitchtrade.amendmentflag", "amendmentFlag", base.ASCII)
fields.f_mitchtrade_subbook = ProtoField.uint8("mitch.mitchtrade.subbook", "subBook", base.DEC)
fields.f_mitchtrade_flags = ProtoField.bytes("mitch.mitchtrade.flags", "flags", base.NONE)
fields.f_mitchnews_nanosecond = ProtoField.uint32("mitch.mitchnews.nanosecond", "nanosecond", base.DEC)
fields.f_mitchnews_time = ProtoField.uint64("mitch.mitchnews.time", "time", base.DEC)
fields.f_mitchnews_urgency = ProtoField.uint8("mitch.mitchnews.urgency", "urgency", base.HEX)
fields.f_mitchnews_headline = ProtoField.string("mitch.mitchnews.headline", "headline", base.ASCII)
fields.f_mitchnews_text = ProtoField.string("mitch.mitchnews.text", "text", base.ASCII)
fields.f_mitchnews_instrumentids = ProtoField.string("mitch.mitchnews.instrumentids", "instrumentIds", base.ASCII)
fields.f_mitchnews_underlyinginstrumentids = ProtoField.string("mitch.mitchnews.underlyinginstrumentids", "underlyingInstrumentIds", base.ASCII)
fields.f_mitchloginrequest_username = ProtoField.string("mitch.mitchloginrequest.username", "username", base.ASCII)
fields.f_mitchloginrequest_password = ProtoField.string("mitch.mitchloginrequest.password", "password", base.ASCII)
fields.f_mitchloginrequest_ipaddress = ProtoField.string("mitch.mitchloginrequest.ipaddress", "ipAddress", base.ASCII)
fields.f_mitchloginrequest_certifiedsolution = ProtoField.string("mitch.mitchloginrequest.certifiedsolution", "certifiedSolution", base.ASCII)
fields.f_mitchloginresponse_status = ProtoField.uint8("mitch.mitchloginresponse.status", "status", base.HEX)
fields.f_mitchsymboldirectory_nanosecond = ProtoField.uint32("mitch.mitchsymboldirectory.nanosecond", "nanosecond", base.DEC)
fields.f_mitchsymboldirectory_symbol = ProtoField.string("mitch.mitchsymboldirectory.symbol", "symbol", base.ASCII)
fields.f_mitchsymboldirectory_instrumentid = ProtoField.uint32("mitch.mitchsymboldirectory.instrumentid", "instrumentId", base.DEC)
fields.f_mitchsymboldirectory_symbolstatus = ProtoField.string("mitch.mitchsymboldirectory.symbolstatus", "symbolStatus", base.ASCII)
fields.f_mitchsymboldirectory_identificationnumber = ProtoField.string("mitch.mitchsymboldirectory.identificationnumber", "identificationNumber", base.ASCII)
fields.f_mitchsymboldirectory_segment = ProtoField.string("mitch.mitchsymboldirectory.segment", "segment", base.ASCII)
fields.f_mitchsymboldirectory_expirationdate = ProtoField.bytes("mitch.mitchsymboldirectory.expirationdate", "expirationDate", base.NONE)
fields.f_mitchsymboldirectory_underlying = ProtoField.string("mitch.mitchsymboldirectory.underlying", "underlying", base.ASCII)
fields.f_mitchsymboldirectory_underlyinginstrumentid = ProtoField.uint32("mitch.mitchsymboldirectory.underlyinginstrumentid", "underlyingInstrumentId", base.DEC)
fields.f_mitchsymboldirectory_strikeprice = ProtoField.int64("mitch.mitchsymboldirectory.strikeprice", "strikePrice", base.DEC)
fields.f_mitchsymboldirectory_optiontype = ProtoField.string("mitch.mitchsymboldirectory.optiontype", "optionType", base.ASCII)
fields.f_mitchsymboldirectory_issuer = ProtoField.string("mitch.mitchsymboldirectory.issuer", "issuer", base.ASCII)
fields.f_mitchsymboldirectory_issuedate = ProtoField.bytes("mitch.mitchsymboldirectory.issuedate", "issueDate", base.NONE)
fields.f_mitchsymboldirectory_coupon = ProtoField.int32("mitch.mitchsymboldirectory.coupon", "coupon", base.DEC)
fields.f_mitchsymboldirectory_flags = ProtoField.bytes("mitch.mitchsymboldirectory.flags", "flags", base.NONE)
fields.f_mitchsymboldirectory_subbook = ProtoField.bytes("mitch.mitchsymboldirectory.subbook", "subBook", base.NONE)
fields.f_mitchsymboldirectory_corporateaction = ProtoField.string("mitch.mitchsymboldirectory.corporateaction", "corporateAction", base.ASCII)
fields.f_mitchsymboldirectory_partitionid = ProtoField.string("mitch.mitchsymboldirectory.partitionid", "partitionId", base.ASCII)
fields.f_mitchsymboldirectory_exercisestyle = ProtoField.string("mitch.mitchsymboldirectory.exercisestyle", "exerciseStyle", base.ASCII)
fields.f_mitchsymboldirectory_leg1symbol = ProtoField.string("mitch.mitchsymboldirectory.leg1symbol", "leg1Symbol", base.ASCII)
fields.f_mitchsymboldirectory_leg1instrumentid = ProtoField.uint32("mitch.mitchsymboldirectory.leg1instrumentid", "leg1InstrumentId", base.DEC)
fields.f_mitchsymboldirectory_leg2symbol = ProtoField.string("mitch.mitchsymboldirectory.leg2symbol", "leg2Symbol", base.ASCII)
fields.f_mitchsymboldirectory_leg2instrumentid = ProtoField.uint32("mitch.mitchsymboldirectory.leg2instrumentid", "leg2InstrumentId", base.DEC)
fields.f_mitchsymboldirectory_contractmultiplier = ProtoField.int32("mitch.mitchsymboldirectory.contractmultiplier", "contractMultiplier", base.DEC)
fields.f_mitchsymboldirectory_settlementmethod = ProtoField.string("mitch.mitchsymboldirectory.settlementmethod", "settlementMethod", base.ASCII)
fields.f_mitchsymboldirectory_testinstrument = ProtoField.uint8("mitch.mitchsymboldirectory.testinstrument", "testInstrument", base.DEC)
fields.f_mitchsymboldirectory_venueofexecution = ProtoField.string("mitch.mitchsymboldirectory.venueofexecution", "venueOfExecution", base.ASCII)
fields.f_mitchsymboldirectory_lotsize = ProtoField.uint32("mitch.mitchsymboldirectory.lotsize", "lotSize", base.DEC)
fields.f_mitchsymboldirectory_securitydescription = ProtoField.string("mitch.mitchsymboldirectory.securitydescription", "securityDescription", base.ASCII)
fields.f_mitchsymboldirectory_listmethod = ProtoField.uint8("mitch.mitchsymboldirectory.listmethod", "listMethod", base.HEX)
fields.f_mitchsymboldirectory_currency = ProtoField.string("mitch.mitchsymboldirectory.currency", "currency", base.ASCII)
fields.f_mitchsymboldirectory_ticksize = ProtoField.int64("mitch.mitchsymboldirectory.ticksize", "tickSize", base.DEC)
fields.f_mitchsymboldirectory_minimumcrossordersize = ProtoField.uint32("mitch.mitchsymboldirectory.minimumcrossordersize", "minimumCrossOrderSize", base.DEC)
fields.f_mitchsymboldirectory_minimumsizeqty = ProtoField.uint32("mitch.mitchsymboldirectory.minimumsizeqty", "minimumSizeQty", base.DEC)
fields.f_mitchsymboldirectory_maximumsizeqty = ProtoField.uint32("mitch.mitchsymboldirectory.maximumsizeqty", "maximumSizeQty", base.DEC)
fields.f_mitchsymboldirectory_securitytype = ProtoField.uint8("mitch.mitchsymboldirectory.securitytype", "securityType", base.DEC)
fields.f_mitchsymboldirectory_assetname = ProtoField.string("mitch.mitchsymboldirectory.assetname", "assetName", base.ASCII)
fields.f_mitchreplayrequest_marketdatagroup = ProtoField.uint8("mitch.mitchreplayrequest.marketdatagroup", "marketDataGroup", base.HEX)
fields.f_mitchreplayrequest_firstmessage = ProtoField.uint64("mitch.mitchreplayrequest.firstmessage", "firstMessage", base.DEC)
fields.f_mitchreplayrequest_count = ProtoField.bytes("mitch.mitchreplayrequest.count", "count", base.NONE)
fields.f_mitchreplayresponse_marketdatagroup = ProtoField.uint8("mitch.mitchreplayresponse.marketdatagroup", "marketDataGroup", base.HEX)
fields.f_mitchreplayresponse_firstmessage = ProtoField.uint64("mitch.mitchreplayresponse.firstmessage", "firstMessage", base.DEC)
fields.f_mitchreplayresponse_count = ProtoField.bytes("mitch.mitchreplayresponse.count", "count", base.NONE)
fields.f_mitchreplayresponse_status = ProtoField.uint8("mitch.mitchreplayresponse.status", "status", base.HEX)
fields.f_mitchsnapshotrequest_sequencenumber = ProtoField.uint64("mitch.mitchsnapshotrequest.sequencenumber", "sequenceNumber", base.DEC)
fields.f_mitchsnapshotrequest_segment = ProtoField.string("mitch.mitchsnapshotrequest.segment", "segment", base.ASCII)
fields.f_mitchsnapshotrequest_instrumentid = ProtoField.uint32("mitch.mitchsnapshotrequest.instrumentid", "instrumentId", base.DEC)
fields.f_mitchsnapshotrequest_subbook = ProtoField.bytes("mitch.mitchsnapshotrequest.subbook", "subBook", base.NONE)
fields.f_mitchsnapshotrequest_snapshottype = ProtoField.uint8("mitch.mitchsnapshotrequest.snapshottype", "snapshotType", base.DEC)
fields.f_mitchsnapshotrequest_recoverfromtime = ProtoField.uint64("mitch.mitchsnapshotrequest.recoverfromtime", "recoverFromTime", base.DEC)
fields.f_mitchsnapshotrequest_requestid = ProtoField.uint32("mitch.mitchsnapshotrequest.requestid", "requestId", base.DEC)
fields.f_mitchsnapshotresponse_sequencenumber = ProtoField.uint64("mitch.mitchsnapshotresponse.sequencenumber", "sequenceNumber", base.DEC)
fields.f_mitchsnapshotresponse_ordercount = ProtoField.uint32("mitch.mitchsnapshotresponse.ordercount", "orderCount", base.DEC)
fields.f_mitchsnapshotresponse_status = ProtoField.uint8("mitch.mitchsnapshotresponse.status", "status", base.HEX)
fields.f_mitchsnapshotresponse_snapshottype = ProtoField.uint8("mitch.mitchsnapshotresponse.snapshottype", "snapshotType", base.DEC)
fields.f_mitchsnapshotresponse_requestid = ProtoField.uint32("mitch.mitchsnapshotresponse.requestid", "requestId", base.DEC)
fields.f_mitchsnapshotcomplete_sequencenumber = ProtoField.uint64("mitch.mitchsnapshotcomplete.sequencenumber", "sequenceNumber", base.DEC)
fields.f_mitchsnapshotcomplete_segment = ProtoField.string("mitch.mitchsnapshotcomplete.segment", "segment", base.ASCII)
fields.f_mitchsnapshotcomplete_instrumentid = ProtoField.uint32("mitch.mitchsnapshotcomplete.instrumentid", "instrumentId", base.DEC)
fields.f_mitchsnapshotcomplete_subbook = ProtoField.bytes("mitch.mitchsnapshotcomplete.subbook", "subBook", base.NONE)
fields.f_mitchsnapshotcomplete_tradingstatus = ProtoField.uint8("mitch.mitchsnapshotcomplete.tradingstatus", "tradingStatus", base.HEX)
fields.f_mitchsnapshotcomplete_snapshottype = ProtoField.uint8("mitch.mitchsnapshotcomplete.snapshottype", "snapshotType", base.DEC)
fields.f_mitchsnapshotcomplete_requestid = ProtoField.uint32("mitch.mitchsnapshotcomplete.requestid", "requestId", base.DEC)

mitch_proto.fields = {
    f_unit_length, f_unit_msg_count, f_unit_mdg, f_unit_seqnum,
    f_msg_type, f_msg_length,
    fields.f_mitchtime_seconds,
    fields.f_mitchsystemevent_nanosecond,
    fields.f_mitchsystemevent_eventcode,
    fields.f_mitchaddorder_nanosecond,
    fields.f_mitchaddorder_orderid,
    fields.f_mitchaddorder_side,
    fields.f_mitchaddorder_quantity,
    fields.f_mitchaddorder_instrumentid,
    fields.f_mitchaddorder_price,
    fields.f_mitchaddorder_flags,
    fields.f_mitchaddattributedorder_nanosecond,
    fields.f_mitchaddattributedorder_orderid,
    fields.f_mitchaddattributedorder_side,
    fields.f_mitchaddattributedorder_quantity,
    fields.f_mitchaddattributedorder_instrumentid,
    fields.f_mitchaddattributedorder_price,
    fields.f_mitchaddattributedorder_firm,
    fields.f_mitchaddattributedorder_flags,
    fields.f_mitchorderdeleted_nanosecond,
    fields.f_mitchorderdeleted_orderid,
    fields.f_mitchorderdeleted_oldquantity,
    fields.f_mitchorderdeleted_oldprice,
    fields.f_mitchordermodified_nanosecond,
    fields.f_mitchordermodified_orderid,
    fields.f_mitchordermodified_newquantity,
    fields.f_mitchordermodified_newprice,
    fields.f_mitchordermodified_oldquantity,
    fields.f_mitchordermodified_oldprice,
    fields.f_mitchordermodified_flags,
    fields.f_mitchorderbookclear_nanosecond,
    fields.f_mitchorderbookclear_instrumentid,
    fields.f_mitchorderbookclear_subbook,
    fields.f_mitchorderbookclear_booktype,
    fields.f_mitchorderexecuted_nanosecond,
    fields.f_mitchorderexecuted_orderid,
    fields.f_mitchorderexecuted_executedquantity,
    fields.f_mitchorderexecuted_price,
    fields.f_mitchorderexecuted_instrumentid,
    fields.f_mitchorderexecuted_tradeid,
    fields.f_mitchorderexecuted_lastoptpx,
    fields.f_mitchorderexecuted_volatility,
    fields.f_mitchorderexecuted_underlyingreferenceprice,
    fields.f_mitchorderexecuted_tradingdatetime,
    fields.f_mitchorderexecuted_instrumentidentificationcode,
    fields.f_mitchorderexecuted_instrumentidentificationcodetype,
    fields.f_mitchorderexecuted_currency,
    fields.f_mitchorderexecuted_venueofexecution,
    fields.f_mitchorderexecuted_pricenotation,
    fields.f_mitchorderexecuted_notionalamount,
    fields.f_mitchorderexecuted_notionalcurrency,
    fields.f_mitchorderexecuted_publicationdatetime,
    fields.f_mitchorderexecuted_firm,
    fields.f_mitchorderexecuted_contrafirm,
    fields.f_mitchorderexecutedwithpricesize_nanosecond,
    fields.f_mitchorderexecutedwithpricesize_orderid,
    fields.f_mitchorderexecutedwithpricesize_executedquantity,
    fields.f_mitchorderexecutedwithpricesize_price,
    fields.f_mitchorderexecutedwithpricesize_instrumentid,
    fields.f_mitchorderexecutedwithpricesize_tradeid,
    fields.f_mitchorderexecutedwithpricesize_lastoptpx,
    fields.f_mitchorderexecutedwithpricesize_volatility,
    fields.f_mitchorderexecutedwithpricesize_underlyingreferenceprice,
    fields.f_mitchorderexecutedwithpricesize_tradingdatetime,
    fields.f_mitchorderexecutedwithpricesize_instrumentidentificationcode,
    fields.f_mitchorderexecutedwithpricesize_instrumentidentificationcodetype,
    fields.f_mitchorderexecutedwithpricesize_currency,
    fields.f_mitchorderexecutedwithpricesize_venueofexecution,
    fields.f_mitchorderexecutedwithpricesize_pricenotation,
    fields.f_mitchorderexecutedwithpricesize_notionalamount,
    fields.f_mitchorderexecutedwithpricesize_notionalcurrency,
    fields.f_mitchorderexecutedwithpricesize_publicationdatetime,
    fields.f_mitchorderexecutedwithpricesize_firm,
    fields.f_mitchorderexecutedwithpricesize_contrafirm,
    fields.f_mitchorderexecutedwithpricesize_displayquantity,
    fields.f_mitchorderexecutedwithpricesize_printable,
    fields.f_mitchauctioninfo_nanosecond,
    fields.f_mitchauctioninfo_pairedquantity,
    fields.f_mitchauctioninfo_imbalancequantity,
    fields.f_mitchauctioninfo_imbalancedirection,
    fields.f_mitchauctioninfo_instrumentid,
    fields.f_mitchauctioninfo_price,
    fields.f_mitchauctioninfo_auctiontype,
    fields.f_mitchauctiontrade_nanosecond,
    fields.f_mitchauctiontrade_executedquantity,
    fields.f_mitchauctiontrade_price,
    fields.f_mitchauctiontrade_instrumentid,
    fields.f_mitchauctiontrade_tradeid,
    fields.f_mitchauctiontrade_lastoptpx,
    fields.f_mitchauctiontrade_volatility,
    fields.f_mitchauctiontrade_underlyingreferenceprice,
    fields.f_mitchauctiontrade_tradingdatetime,
    fields.f_mitchauctiontrade_instrumentidentificationcode,
    fields.f_mitchauctiontrade_instrumentidentificationcodetype,
    fields.f_mitchauctiontrade_currency,
    fields.f_mitchauctiontrade_venueofexecution,
    fields.f_mitchauctiontrade_pricenotation,
    fields.f_mitchauctiontrade_notionalamount,
    fields.f_mitchauctiontrade_notionalcurrency,
    fields.f_mitchauctiontrade_publicationdatetime,
    fields.f_mitchauctiontrade_cancellationflag,
    fields.f_mitchauctiontrade_amendmentflag,
    fields.f_mitchauctiontrade_auctiontype,
    fields.f_mitchrecoverytrade_nanosecond,
    fields.f_mitchrecoverytrade_orderid,
    fields.f_mitchrecoverytrade_executedquantity,
    fields.f_mitchrecoverytrade_price,
    fields.f_mitchrecoverytrade_instrumentid,
    fields.f_mitchrecoverytrade_tradeid,
    fields.f_mitchrecoverytrade_lastoptpx,
    fields.f_mitchrecoverytrade_volatility,
    fields.f_mitchrecoverytrade_underlyingreferenceprice,
    fields.f_mitchrecoverytrade_tradingdatetime,
    fields.f_mitchrecoverytrade_instrumentidentificationcode,
    fields.f_mitchrecoverytrade_instrumentidentificationcodetype,
    fields.f_mitchrecoverytrade_currency,
    fields.f_mitchrecoverytrade_venueofexecution,
    fields.f_mitchrecoverytrade_pricenotation,
    fields.f_mitchrecoverytrade_notionalamount,
    fields.f_mitchrecoverytrade_notionalcurrency,
    fields.f_mitchrecoverytrade_publicationdatetime,
    fields.f_mitchrecoverytrade_firm,
    fields.f_mitchrecoverytrade_contrafirm,
    fields.f_mitchrecoverytrade_cancellationflag,
    fields.f_mitchrecoverytrade_amendmentflag,
    fields.f_mitchrecoverytrade_subbook,
    fields.f_mitchrecoverytrade_flags,
    fields.f_mitchrecoverytrade_auctiontype,
    fields.f_mitchstatistics_nanosecond,
    fields.f_mitchstatistics_instrumentid,
    fields.f_mitchstatistics_statistictype,
    fields.f_mitchstatistics_price,
    fields.f_mitchstatistics_opencloseindicator,
    fields.f_mitchstatistics_subbook,
    fields.f_mitchextendedstatistics_nanosecond,
    fields.f_mitchextendedstatistics_instrumentid,
    fields.f_mitchextendedstatistics_highprice,
    fields.f_mitchextendedstatistics_lowprice,
    fields.f_mitchextendedstatistics_vwap,
    fields.f_mitchextendedstatistics_volume,
    fields.f_mitchextendedstatistics_turnover,
    fields.f_mitchextendedstatistics_numberoftrades,
    fields.f_mitchextendedstatistics_reservedfield,
    fields.f_mitchextendedstatistics_subbook,
    fields.f_mitchextendedstatistics_notionalexposure,
    fields.f_mitchextendedstatistics_notionaldeltaexposure,
    fields.f_mitchextendedstatistics_theoreticalprice,
    fields.f_mitchextendedstatistics_volatility,
    fields.f_mitchextendedstatistics_upperdynamicpblimit,
    fields.f_mitchextendedstatistics_lowerdynamicpblimit,
    fields.f_mitchextendedstatistics_upperstaticpblimit,
    fields.f_mitchextendedstatistics_lowerstaticpblimit,
    fields.f_mitchextendedstatistics_upperdynamiccblimit,
    fields.f_mitchextendedstatistics_lowerdynamiccblimit,
    fields.f_mitchsymbolstatus_nanosecond,
    fields.f_mitchsymbolstatus_instrumentid,
    fields.f_mitchsymbolstatus_tradingstatus,
    fields.f_mitchsymbolstatus_flags,
    fields.f_mitchsymbolstatus_haltreason,
    fields.f_mitchsymbolstatus_sessionchangereason,
    fields.f_mitchsymbolstatus_newendtime,
    fields.f_mitchsymbolstatus_subbook,
    fields.f_mitchtopofbook_nanosecond,
    fields.f_mitchtopofbook_instrumentid,
    fields.f_mitchtopofbook_subbook,
    fields.f_mitchtopofbook_action,
    fields.f_mitchtopofbook_side,
    fields.f_mitchtopofbook_price,
    fields.f_mitchtopofbook_quantity,
    fields.f_mitchtopofbook_marketorderquantity,
    fields.f_mitchtopofbook_reservedfield,
    fields.f_mitchtopofbook_splits,
    fields.f_mitchtrade_nanosecond,
    fields.f_mitchtrade_orderid,
    fields.f_mitchtrade_executedquantity,
    fields.f_mitchtrade_price,
    fields.f_mitchtrade_instrumentid,
    fields.f_mitchtrade_tradeid,
    fields.f_mitchtrade_lastoptpx,
    fields.f_mitchtrade_volatility,
    fields.f_mitchtrade_underlyingreferenceprice,
    fields.f_mitchtrade_tradingdatetime,
    fields.f_mitchtrade_instrumentidentificationcode,
    fields.f_mitchtrade_instrumentidentificationcodetype,
    fields.f_mitchtrade_currency,
    fields.f_mitchtrade_venueofexecution,
    fields.f_mitchtrade_pricenotation,
    fields.f_mitchtrade_notionalamount,
    fields.f_mitchtrade_notionalcurrency,
    fields.f_mitchtrade_publicationdatetime,
    fields.f_mitchtrade_firm,
    fields.f_mitchtrade_contrafirm,
    fields.f_mitchtrade_cancellationflag,
    fields.f_mitchtrade_amendmentflag,
    fields.f_mitchtrade_subbook,
    fields.f_mitchtrade_flags,
    fields.f_mitchnews_nanosecond,
    fields.f_mitchnews_time,
    fields.f_mitchnews_urgency,
    fields.f_mitchnews_headline,
    fields.f_mitchnews_text,
    fields.f_mitchnews_instrumentids,
    fields.f_mitchnews_underlyinginstrumentids,
    fields.f_mitchloginrequest_username,
    fields.f_mitchloginrequest_password,
    fields.f_mitchloginrequest_ipaddress,
    fields.f_mitchloginrequest_certifiedsolution,
    fields.f_mitchloginresponse_status,
    fields.f_mitchsymboldirectory_nanosecond,
    fields.f_mitchsymboldirectory_symbol,
    fields.f_mitchsymboldirectory_instrumentid,
    fields.f_mitchsymboldirectory_symbolstatus,
    fields.f_mitchsymboldirectory_identificationnumber,
    fields.f_mitchsymboldirectory_segment,
    fields.f_mitchsymboldirectory_expirationdate,
    fields.f_mitchsymboldirectory_underlying,
    fields.f_mitchsymboldirectory_underlyinginstrumentid,
    fields.f_mitchsymboldirectory_strikeprice,
    fields.f_mitchsymboldirectory_optiontype,
    fields.f_mitchsymboldirectory_issuer,
    fields.f_mitchsymboldirectory_issuedate,
    fields.f_mitchsymboldirectory_coupon,
    fields.f_mitchsymboldirectory_flags,
    fields.f_mitchsymboldirectory_subbook,
    fields.f_mitchsymboldirectory_corporateaction,
    fields.f_mitchsymboldirectory_partitionid,
    fields.f_mitchsymboldirectory_exercisestyle,
    fields.f_mitchsymboldirectory_leg1symbol,
    fields.f_mitchsymboldirectory_leg1instrumentid,
    fields.f_mitchsymboldirectory_leg2symbol,
    fields.f_mitchsymboldirectory_leg2instrumentid,
    fields.f_mitchsymboldirectory_contractmultiplier,
    fields.f_mitchsymboldirectory_settlementmethod,
    fields.f_mitchsymboldirectory_testinstrument,
    fields.f_mitchsymboldirectory_venueofexecution,
    fields.f_mitchsymboldirectory_lotsize,
    fields.f_mitchsymboldirectory_securitydescription,
    fields.f_mitchsymboldirectory_listmethod,
    fields.f_mitchsymboldirectory_currency,
    fields.f_mitchsymboldirectory_ticksize,
    fields.f_mitchsymboldirectory_minimumcrossordersize,
    fields.f_mitchsymboldirectory_minimumsizeqty,
    fields.f_mitchsymboldirectory_maximumsizeqty,
    fields.f_mitchsymboldirectory_securitytype,
    fields.f_mitchsymboldirectory_assetname,
    fields.f_mitchreplayrequest_marketdatagroup,
    fields.f_mitchreplayrequest_firstmessage,
    fields.f_mitchreplayrequest_count,
    fields.f_mitchreplayresponse_marketdatagroup,
    fields.f_mitchreplayresponse_firstmessage,
    fields.f_mitchreplayresponse_count,
    fields.f_mitchreplayresponse_status,
    fields.f_mitchsnapshotrequest_sequencenumber,
    fields.f_mitchsnapshotrequest_segment,
    fields.f_mitchsnapshotrequest_instrumentid,
    fields.f_mitchsnapshotrequest_subbook,
    fields.f_mitchsnapshotrequest_snapshottype,
    fields.f_mitchsnapshotrequest_recoverfromtime,
    fields.f_mitchsnapshotrequest_requestid,
    fields.f_mitchsnapshotresponse_sequencenumber,
    fields.f_mitchsnapshotresponse_ordercount,
    fields.f_mitchsnapshotresponse_status,
    fields.f_mitchsnapshotresponse_snapshottype,
    fields.f_mitchsnapshotresponse_requestid,
    fields.f_mitchsnapshotcomplete_sequencenumber,
    fields.f_mitchsnapshotcomplete_segment,
    fields.f_mitchsnapshotcomplete_instrumentid,
    fields.f_mitchsnapshotcomplete_subbook,
    fields.f_mitchsnapshotcomplete_tradingstatus,
    fields.f_mitchsnapshotcomplete_snapshottype,
    fields.f_mitchsnapshotcomplete_requestid
}

local msg_types = {
    [0x54] = "MitchTime",
    [0x53] = "MitchSystemEvent",
    [0x41] = "MitchAddOrder",
    [0x46] = "MitchAddAttributedOrder",
    [0x44] = "MitchOrderDeleted",
    [0x55] = "MitchOrderModified",
    [0x79] = "MitchOrderBookClear",
    [0x45] = "MitchOrderExecuted",
    [0x43] = "MitchOrderExecutedWithPriceSize",
    [0x49] = "MitchAuctionInfo",
    [0x51] = "MitchAuctionTrade",
    [0x76] = "MitchRecoveryTrade",
    [0x77] = "MitchStatistics",
    [0x80] = "MitchExtendedStatistics",
    [0x48] = "MitchSymbolStatus",
    [0x71] = "MitchTopOfBook",
    [0x50] = "MitchTrade",
    [0x75] = "MitchNews",
    [0x01] = "MitchLoginRequest",
    [0x02] = "MitchLoginResponse",
    [0x05] = "MitchLogoutRequest",
    [0x52] = "MitchSymbolDirectory",
    [0x03] = "MitchReplayRequest",
    [0x04] = "MitchReplayResponse",
    [0x81] = "MitchSnapshotRequest",
    [0x82] = "MitchSnapshotResponse",
    [0x83] = "MitchSnapshotComplete",
}

function mitch_proto.dissector(buffer, pinfo, tree)
    local length = buffer:len()
    if length == 0 then return end

    pinfo.cols.protocol = mitch_proto.name

    local subtree = tree:add(mitch_proto, buffer(), "MITCH Protocol Data")
    local offset = 0

    -- Unit Header (12 bytes)
    local unit_tree = subtree:add(mitch_proto, buffer(offset, 12), "Unit Header")
    local unit_length = buffer(offset, 2):le_uint()
    unit_tree:add_le(f_unit_length, buffer(offset, 2))
    offset = offset + 2

    local msg_count = buffer(offset, 1):uint()
    unit_tree:add(f_unit_msg_count, buffer(offset, 1))
    offset = offset + 1

    local mdg = buffer(offset, 1):uint()
    unit_tree:add(f_unit_mdg, buffer(offset, 1))
    offset = offset + 1

    local seq_num = buffer(offset, 8):le_uint64()
    unit_tree:add_le(f_unit_seqnum, buffer(offset, 8))
    offset = offset + 8

    pinfo.cols.info = string.format("MDG=%d SeqNum=%s MsgCount=%d", mdg, tostring(seq_num), msg_count)

    -- Parse payload messages
    for i = 1, msg_count do
        local msg_start = offset
        local msg_length = buffer(offset, 2):le_uint()
        local msg_tree = subtree:add(mitch_proto, buffer(offset, msg_length), "Message " .. i)
        msg_tree:add_le(f_msg_length, buffer(offset, 2))
        offset = offset + 2

        local msg_type = buffer(offset, 1):uint()
        msg_tree:add(f_msg_type, buffer(offset, 1))
        offset = offset + 1

        local msg_name = msg_types[msg_type] or "Unknown"
        msg_tree:append_text(" - " .. msg_name)

        if msg_type == 0x54 then
            -- MitchTime
            msg_tree:add_le(fields.f_mitchtime_seconds, buffer(offset, 4))
            offset = offset + 4
        elseif msg_type == 0x53 then
            -- MitchSystemEvent
            msg_tree:add_le(fields.f_mitchsystemevent_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchsystemevent_eventcode, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x41 then
            -- MitchAddOrder
            msg_tree:add_le(fields.f_mitchaddorder_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchaddorder_orderid, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchaddorder_side, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchaddorder_quantity, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchaddorder_instrumentid, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchaddorder_price, buffer(offset, 8)):set_text("price: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchaddorder_flags, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x46 then
            -- MitchAddAttributedOrder
            msg_tree:add_le(fields.f_mitchaddattributedorder_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchaddattributedorder_orderid, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchaddattributedorder_side, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchaddattributedorder_quantity, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchaddattributedorder_instrumentid, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchaddattributedorder_price, buffer(offset, 8)):set_text("price: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add(fields.f_mitchaddattributedorder_firm, buffer(offset, 6))
            offset = offset + 6
            msg_tree:add_le(fields.f_mitchaddattributedorder_flags, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x44 then
            -- MitchOrderDeleted
            msg_tree:add_le(fields.f_mitchorderdeleted_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchorderdeleted_orderid, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchorderdeleted_oldquantity, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchorderdeleted_oldprice, buffer(offset, 8)):set_text("oldPrice: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
        elseif msg_type == 0x55 then
            -- MitchOrderModified
            msg_tree:add_le(fields.f_mitchordermodified_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchordermodified_orderid, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchordermodified_newquantity, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchordermodified_newprice, buffer(offset, 8)):set_text("newPrice: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchordermodified_oldquantity, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchordermodified_oldprice, buffer(offset, 8)):set_text("oldPrice: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchordermodified_flags, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x79 then
            -- MitchOrderBookClear
            msg_tree:add_le(fields.f_mitchorderbookclear_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchorderbookclear_instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchorderbookclear_subbook, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchorderbookclear_booktype, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x45 then
            -- MitchOrderExecuted
            msg_tree:add_le(fields.f_mitchorderexecuted_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchorderexecuted_orderid, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchorderexecuted_executedquantity, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchorderexecuted_price, buffer(offset, 8)):set_text("price: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchorderexecuted_instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchorderexecuted_tradeid, buffer(offset, 8))
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchorderexecuted_lastoptpx, buffer(offset, 8)):set_text("lastOptPx: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchorderexecuted_volatility, buffer(offset, 8)):set_text("volatility: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchorderexecuted_underlyingreferenceprice, buffer(offset, 8)):set_text("underlyingReferencePrice: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchorderexecuted_tradingdatetime, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchorderexecuted_instrumentidentificationcode, buffer(offset, 12))
            offset = offset + 12
            msg_tree:add_le(fields.f_mitchorderexecuted_instrumentidentificationcodetype, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchorderexecuted_currency, buffer(offset, 3))
            offset = offset + 3
            msg_tree:add(fields.f_mitchorderexecuted_venueofexecution, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchorderexecuted_pricenotation, buffer(offset, 1))
            offset = offset + 1
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchorderexecuted_notionalamount, buffer(offset, 8)):set_text("notionalAmount: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add(fields.f_mitchorderexecuted_notionalcurrency, buffer(offset, 3))
            offset = offset + 3
            msg_tree:add_le(fields.f_mitchorderexecuted_publicationdatetime, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchorderexecuted_firm, buffer(offset, 6))
            offset = offset + 6
            msg_tree:add(fields.f_mitchorderexecuted_contrafirm, buffer(offset, 6))
            offset = offset + 6
        elseif msg_type == 0x43 then
            -- MitchOrderExecutedWithPriceSize
            msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_orderid, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_executedquantity, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_price, buffer(offset, 8)):set_text("price: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_tradeid, buffer(offset, 8))
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_lastoptpx, buffer(offset, 8)):set_text("lastOptPx: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_volatility, buffer(offset, 8)):set_text("volatility: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_underlyingreferenceprice, buffer(offset, 8)):set_text("underlyingReferencePrice: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_tradingdatetime, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchorderexecutedwithpricesize_instrumentidentificationcode, buffer(offset, 12))
            offset = offset + 12
            msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_instrumentidentificationcodetype, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchorderexecutedwithpricesize_currency, buffer(offset, 3))
            offset = offset + 3
            msg_tree:add(fields.f_mitchorderexecutedwithpricesize_venueofexecution, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_pricenotation, buffer(offset, 1))
            offset = offset + 1
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_notionalamount, buffer(offset, 8)):set_text("notionalAmount: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add(fields.f_mitchorderexecutedwithpricesize_notionalcurrency, buffer(offset, 3))
            offset = offset + 3
            msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_publicationdatetime, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchorderexecutedwithpricesize_firm, buffer(offset, 6))
            offset = offset + 6
            msg_tree:add(fields.f_mitchorderexecutedwithpricesize_contrafirm, buffer(offset, 6))
            offset = offset + 6
            msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_displayquantity, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchorderexecutedwithpricesize_printable, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x49 then
            -- MitchAuctionInfo
            msg_tree:add_le(fields.f_mitchauctioninfo_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchauctioninfo_pairedquantity, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchauctioninfo_imbalancequantity, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchauctioninfo_imbalancedirection, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchauctioninfo_instrumentid, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchauctioninfo_price, buffer(offset, 8)):set_text("price: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchauctioninfo_auctiontype, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x51 then
            -- MitchAuctionTrade
            msg_tree:add_le(fields.f_mitchauctiontrade_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchauctiontrade_executedquantity, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchauctiontrade_price, buffer(offset, 8)):set_text("price: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchauctiontrade_instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchauctiontrade_tradeid, buffer(offset, 8))
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchauctiontrade_lastoptpx, buffer(offset, 8)):set_text("lastOptPx: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchauctiontrade_volatility, buffer(offset, 8)):set_text("volatility: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchauctiontrade_underlyingreferenceprice, buffer(offset, 8)):set_text("underlyingReferencePrice: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchauctiontrade_tradingdatetime, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchauctiontrade_instrumentidentificationcode, buffer(offset, 12))
            offset = offset + 12
            msg_tree:add_le(fields.f_mitchauctiontrade_instrumentidentificationcodetype, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchauctiontrade_currency, buffer(offset, 3))
            offset = offset + 3
            msg_tree:add(fields.f_mitchauctiontrade_venueofexecution, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchauctiontrade_pricenotation, buffer(offset, 1))
            offset = offset + 1
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchauctiontrade_notionalamount, buffer(offset, 8)):set_text("notionalAmount: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add(fields.f_mitchauctiontrade_notionalcurrency, buffer(offset, 3))
            offset = offset + 3
            msg_tree:add_le(fields.f_mitchauctiontrade_publicationdatetime, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchauctiontrade_cancellationflag, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add(fields.f_mitchauctiontrade_amendmentflag, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchauctiontrade_auctiontype, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x76 then
            -- MitchRecoveryTrade
            msg_tree:add_le(fields.f_mitchrecoverytrade_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchrecoverytrade_orderid, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchrecoverytrade_executedquantity, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchrecoverytrade_price, buffer(offset, 8)):set_text("price: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchrecoverytrade_instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchrecoverytrade_tradeid, buffer(offset, 8))
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchrecoverytrade_lastoptpx, buffer(offset, 8)):set_text("lastOptPx: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchrecoverytrade_volatility, buffer(offset, 8)):set_text("volatility: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchrecoverytrade_underlyingreferenceprice, buffer(offset, 8)):set_text("underlyingReferencePrice: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchrecoverytrade_tradingdatetime, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchrecoverytrade_instrumentidentificationcode, buffer(offset, 12))
            offset = offset + 12
            msg_tree:add_le(fields.f_mitchrecoverytrade_instrumentidentificationcodetype, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchrecoverytrade_currency, buffer(offset, 3))
            offset = offset + 3
            msg_tree:add(fields.f_mitchrecoverytrade_venueofexecution, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchrecoverytrade_pricenotation, buffer(offset, 1))
            offset = offset + 1
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchrecoverytrade_notionalamount, buffer(offset, 8)):set_text("notionalAmount: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add(fields.f_mitchrecoverytrade_notionalcurrency, buffer(offset, 3))
            offset = offset + 3
            msg_tree:add_le(fields.f_mitchrecoverytrade_publicationdatetime, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchrecoverytrade_firm, buffer(offset, 6))
            offset = offset + 6
            msg_tree:add(fields.f_mitchrecoverytrade_contrafirm, buffer(offset, 6))
            offset = offset + 6
            msg_tree:add(fields.f_mitchrecoverytrade_cancellationflag, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add(fields.f_mitchrecoverytrade_amendmentflag, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchrecoverytrade_subbook, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchrecoverytrade_flags, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchrecoverytrade_auctiontype, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x77 then
            -- MitchStatistics
            msg_tree:add_le(fields.f_mitchstatistics_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchstatistics_instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchstatistics_statistictype, buffer(offset, 1))
            offset = offset + 1
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchstatistics_price, buffer(offset, 8)):set_text("price: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchstatistics_opencloseindicator, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchstatistics_subbook, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x80 then
            -- MitchExtendedStatistics
            msg_tree:add_le(fields.f_mitchextendedstatistics_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchextendedstatistics_instrumentid, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_highprice, buffer(offset, 8)):set_text("highPrice: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_lowprice, buffer(offset, 8)):set_text("lowPrice: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_vwap, buffer(offset, 8)):set_text("vwap: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchextendedstatistics_volume, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_turnover, buffer(offset, 8)):set_text("turnover: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchextendedstatistics_numberoftrades, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchextendedstatistics_reservedfield, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchextendedstatistics_subbook, buffer(offset, 1))
            offset = offset + 1
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_notionalexposure, buffer(offset, 8)):set_text("notionalExposure: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_notionaldeltaexposure, buffer(offset, 8)):set_text("notionalDeltaExposure: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_theoreticalprice, buffer(offset, 8)):set_text("theoreticalPrice: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_volatility, buffer(offset, 8)):set_text("volatility: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_upperdynamicpblimit, buffer(offset, 8)):set_text("upperDynamicPBLimit: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_lowerdynamicpblimit, buffer(offset, 8)):set_text("lowerDynamicPBLimit: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_upperstaticpblimit, buffer(offset, 8)):set_text("upperStaticPBLimit: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_lowerstaticpblimit, buffer(offset, 8)):set_text("lowerStaticPBLimit: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_upperdynamiccblimit, buffer(offset, 8)):set_text("upperDynamicCBLimit: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchextendedstatistics_lowerdynamiccblimit, buffer(offset, 8)):set_text("lowerDynamicCBLimit: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
        elseif msg_type == 0x48 then
            -- MitchSymbolStatus
            msg_tree:add_le(fields.f_mitchsymbolstatus_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchsymbolstatus_instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchsymbolstatus_tradingstatus, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchsymbolstatus_flags, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchsymbolstatus_haltreason, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchsymbolstatus_sessionchangereason, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchsymbolstatus_newendtime, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchsymbolstatus_subbook, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x71 then
            -- MitchTopOfBook
            msg_tree:add_le(fields.f_mitchtopofbook_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchtopofbook_instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchtopofbook_subbook, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchtopofbook_action, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchtopofbook_side, buffer(offset, 1))
            offset = offset + 1
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchtopofbook_price, buffer(offset, 8)):set_text("price: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchtopofbook_quantity, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchtopofbook_marketorderquantity, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchtopofbook_reservedfield, buffer(offset, 2))
            offset = offset + 2
            msg_tree:add_le(fields.f_mitchtopofbook_splits, buffer(offset, 4))
            offset = offset + 4
        elseif msg_type == 0x50 then
            -- MitchTrade
            msg_tree:add_le(fields.f_mitchtrade_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchtrade_orderid, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchtrade_executedquantity, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchtrade_price, buffer(offset, 8)):set_text("price: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchtrade_instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchtrade_tradeid, buffer(offset, 8))
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchtrade_lastoptpx, buffer(offset, 8)):set_text("lastOptPx: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchtrade_volatility, buffer(offset, 8)):set_text("volatility: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchtrade_underlyingreferenceprice, buffer(offset, 8)):set_text("underlyingReferencePrice: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchtrade_tradingdatetime, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchtrade_instrumentidentificationcode, buffer(offset, 12))
            offset = offset + 12
            msg_tree:add_le(fields.f_mitchtrade_instrumentidentificationcodetype, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchtrade_currency, buffer(offset, 3))
            offset = offset + 3
            msg_tree:add(fields.f_mitchtrade_venueofexecution, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchtrade_pricenotation, buffer(offset, 1))
            offset = offset + 1
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchtrade_notionalamount, buffer(offset, 8)):set_text("notionalAmount: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add(fields.f_mitchtrade_notionalcurrency, buffer(offset, 3))
            offset = offset + 3
            msg_tree:add_le(fields.f_mitchtrade_publicationdatetime, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchtrade_firm, buffer(offset, 6))
            offset = offset + 6
            msg_tree:add(fields.f_mitchtrade_contrafirm, buffer(offset, 6))
            offset = offset + 6
            msg_tree:add(fields.f_mitchtrade_cancellationflag, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add(fields.f_mitchtrade_amendmentflag, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchtrade_subbook, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchtrade_flags, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x75 then
            -- MitchNews
            msg_tree:add_le(fields.f_mitchnews_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchnews_time, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchnews_urgency, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchnews_headline, buffer(offset, 100))
            offset = offset + 100
            msg_tree:add(fields.f_mitchnews_text, buffer(offset, 750))
            offset = offset + 750
            msg_tree:add(fields.f_mitchnews_instrumentids, buffer(offset, 100))
            offset = offset + 100
            msg_tree:add(fields.f_mitchnews_underlyinginstrumentids, buffer(offset, 100))
            offset = offset + 100
        elseif msg_type == 0x01 then
            -- MitchLoginRequest
            msg_tree:add(fields.f_mitchloginrequest_username, buffer(offset, 6))
            offset = offset + 6
            msg_tree:add(fields.f_mitchloginrequest_password, buffer(offset, 10))
            offset = offset + 10
            msg_tree:add(fields.f_mitchloginrequest_ipaddress, buffer(offset, 15))
            offset = offset + 15
            msg_tree:add(fields.f_mitchloginrequest_certifiedsolution, buffer(offset, 44))
            offset = offset + 44
        elseif msg_type == 0x02 then
            -- MitchLoginResponse
            msg_tree:add_le(fields.f_mitchloginresponse_status, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x05 then
            -- MitchLogoutRequest
        elseif msg_type == 0x52 then
            -- MitchSymbolDirectory
            msg_tree:add_le(fields.f_mitchsymboldirectory_nanosecond, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add(fields.f_mitchsymboldirectory_symbol, buffer(offset, 25))
            offset = offset + 25
            msg_tree:add_le(fields.f_mitchsymboldirectory_instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add(fields.f_mitchsymboldirectory_symbolstatus, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchsymboldirectory_identificationnumber, buffer(offset, 12))
            offset = offset + 12
            msg_tree:add(fields.f_mitchsymboldirectory_segment, buffer(offset, 10))
            offset = offset + 10
            msg_tree:add_le(fields.f_mitchsymboldirectory_expirationdate, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchsymboldirectory_underlying, buffer(offset, 25))
            offset = offset + 25
            msg_tree:add_le(fields.f_mitchsymboldirectory_underlyinginstrumentid, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchsymboldirectory_strikeprice, buffer(offset, 8)):set_text("strikePrice: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add(fields.f_mitchsymboldirectory_optiontype, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchsymboldirectory_issuer, buffer(offset, 6))
            offset = offset + 6
            msg_tree:add_le(fields.f_mitchsymboldirectory_issuedate, buffer(offset, 8))
            offset = offset + 8
            do local v = buffer(offset, 4):le_int64() msg_tree:add_le(fields.f_mitchsymboldirectory_coupon, buffer(offset, 4)):set_text("coupon: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchsymboldirectory_flags, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchsymboldirectory_subbook, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchsymboldirectory_corporateaction, buffer(offset, 5))
            offset = offset + 5
            msg_tree:add(fields.f_mitchsymboldirectory_partitionid, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchsymboldirectory_exercisestyle, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchsymboldirectory_leg1symbol, buffer(offset, 25))
            offset = offset + 25
            msg_tree:add_le(fields.f_mitchsymboldirectory_leg1instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add(fields.f_mitchsymboldirectory_leg2symbol, buffer(offset, 25))
            offset = offset + 25
            msg_tree:add_le(fields.f_mitchsymboldirectory_leg2instrumentid, buffer(offset, 4))
            offset = offset + 4
            do local v = buffer(offset, 4):le_int64() msg_tree:add_le(fields.f_mitchsymboldirectory_contractmultiplier, buffer(offset, 4)):set_text("contractMultiplier: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 4
            msg_tree:add(fields.f_mitchsymboldirectory_settlementmethod, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchsymboldirectory_testinstrument, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchsymboldirectory_venueofexecution, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchsymboldirectory_lotsize, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add(fields.f_mitchsymboldirectory_securitydescription, buffer(offset, 110))
            offset = offset + 110
            msg_tree:add_le(fields.f_mitchsymboldirectory_listmethod, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchsymboldirectory_currency, buffer(offset, 3))
            offset = offset + 3
            do local v = buffer(offset, 8):le_int64() msg_tree:add_le(fields.f_mitchsymboldirectory_ticksize, buffer(offset, 8)):set_text("tickSize: " .. string.format("%.4f", tonumber(tostring(v)) / 10000)) end
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchsymboldirectory_minimumcrossordersize, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchsymboldirectory_minimumsizeqty, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchsymboldirectory_maximumsizeqty, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchsymboldirectory_securitytype, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add(fields.f_mitchsymboldirectory_assetname, buffer(offset, 6))
            offset = offset + 6
        elseif msg_type == 0x03 then
            -- MitchReplayRequest
            msg_tree:add_le(fields.f_mitchreplayrequest_marketdatagroup, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchreplayrequest_firstmessage, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchreplayrequest_count, buffer(offset, 2))
            offset = offset + 2
        elseif msg_type == 0x04 then
            -- MitchReplayResponse
            msg_tree:add_le(fields.f_mitchreplayresponse_marketdatagroup, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchreplayresponse_firstmessage, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchreplayresponse_count, buffer(offset, 2))
            offset = offset + 2
            msg_tree:add_le(fields.f_mitchreplayresponse_status, buffer(offset, 1))
            offset = offset + 1
        elseif msg_type == 0x81 then
            -- MitchSnapshotRequest
            msg_tree:add_le(fields.f_mitchsnapshotrequest_sequencenumber, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchsnapshotrequest_segment, buffer(offset, 10))
            offset = offset + 10
            msg_tree:add_le(fields.f_mitchsnapshotrequest_instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchsnapshotrequest_subbook, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchsnapshotrequest_snapshottype, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchsnapshotrequest_recoverfromtime, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchsnapshotrequest_requestid, buffer(offset, 4))
            offset = offset + 4
        elseif msg_type == 0x82 then
            -- MitchSnapshotResponse
            msg_tree:add_le(fields.f_mitchsnapshotresponse_sequencenumber, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add_le(fields.f_mitchsnapshotresponse_ordercount, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchsnapshotresponse_status, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchsnapshotresponse_snapshottype, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchsnapshotresponse_requestid, buffer(offset, 4))
            offset = offset + 4
        elseif msg_type == 0x83 then
            -- MitchSnapshotComplete
            msg_tree:add_le(fields.f_mitchsnapshotcomplete_sequencenumber, buffer(offset, 8))
            offset = offset + 8
            msg_tree:add(fields.f_mitchsnapshotcomplete_segment, buffer(offset, 10))
            offset = offset + 10
            msg_tree:add_le(fields.f_mitchsnapshotcomplete_instrumentid, buffer(offset, 4))
            offset = offset + 4
            msg_tree:add_le(fields.f_mitchsnapshotcomplete_subbook, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchsnapshotcomplete_tradingstatus, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchsnapshotcomplete_snapshottype, buffer(offset, 1))
            offset = offset + 1
            msg_tree:add_le(fields.f_mitchsnapshotcomplete_requestid, buffer(offset, 4))
            offset = offset + 4
        end
        offset = msg_start + msg_length
    end
end

-- Heuristic dissector for multicast IP range 224.4.85.0/24
local function heuristic_checker(buffer, pinfo, tree)
    local dst_ip = tostring(pinfo.dst)
    if dst_ip:match("^224%.4%.85%.") then
        mitch_proto.dissector(buffer, pinfo, tree)
        return true
    end
    return false
end

mitch_proto:register_heuristic("udp", heuristic_checker)
