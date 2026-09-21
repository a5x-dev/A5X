#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * Execution report
 * Message Type: 8
 */
struct NativeExecutionReport {
    /** Identity of the matching partition */
    uint8_t partitionId;

    /** Message sequence number of the matching partition */
    uint64_t sequenceNumber;

    /** Identifier of the Execution Report. Identifier of the trade being cancelled or corrected if Execution Type is Trade Cancel (H) */
    std::string executionId;

    /** Client specified identifier of the order, cancel request or modification requests */
    std::string clientOrderId;

    /** Server specified identifier of the order */
    std::string orderId;

    /** Server specified public order identifier of the order */
    std::string publicOrderId;

    /** Executed price. This field should be ignored if Execution Type is not Trade (F) */
    int64_t executedPrice;

    /** Time the transaction occurred in epoch format */
    uint64_t transactTime;

    /** Identifier of the Trader Account */
    uint64_t account;

    /** Sender Location assigned to the Trader ID/Firm */
    std::string senderLocation;

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** Identifier of the cross order. Only alphanumeric values allowed */
    std::string crossId;

    /** Executed quantity. Ignore if Execution Type is not Trade (F) */
    uint32_t executedQuantity;

    /** Quantity available for further execution */
    uint32_t leavesQuantity;

    /** 0 = New, 4 = Cancelled, 5 = Modified, 8 = Rejected, 9 = Suspended, A = Pending New, C = Expired, D = Restated, E = Pending Replace, F = Trade, H = Trade Cancel, L = Triggered */
    std::string executionType;

    /** 0 = New, 1 = Partially Filled, 2 = Filled, 4 = Cancelled, 6 = Expired, 8 = Rejected, 9 = Suspended, 12 = Pending New, 14 = Pending Replace */
    uint8_t orderStatus;

    /** Code specifying the reason for the reject. Ignore if Execution Type is not Rejected (8) */
    uint32_t rejectCode;

    /** 0 = Unset, 1 = Order is being worked, 2 = Order is not currently in a working state. Only applicable when Order Status is New (0) */
    uint8_t workingIndicator;

    /** 1 = Buy, 2 = Sell. When a quote is rejected, field will be set to Buy (1) */
    uint8_t side;

    /** 0 = GT Corporate Action, 1 = GT Renewal/Restatement, 3 = Order Re-Priced, 8 = Market Option, 100 = Order Replenishment. Only applicable if Exec Type is Restated (D) or unsolicited cancellation */
    uint8_t restatementReason;

    /** 1 = Regular */
    uint8_t orderBook;

    /** Bit 0: Aggressor Indicator (0 = Passive, 1 = Aggressor), Bit 5: RLP (0 = No, 1 = Yes). Applicable only when Exec Type is Trade */
    uint8_t indicatorFlags;

    /** 5 = Internal Cross. Any other value rejected */
    uint8_t crossType;

    /** 1 = Retail Liquidity Taker, 2 = Waived Priority, 3 = Broker Only */
    uint8_t routingInstruction;

    /** 1 = Regular, 2 = Non-Regular */
    uint8_t tradingPhase;

    /** MIC Code of the instrument as set up under Execution Venue in Reference Data */
    std::string executionVenue;

    /** Entering Trader ID */
    std::string enteringTrader;

    /** Free format text field. Echoed in reports */
    std::string memo;

    /** Desk ID through which the order is placed from */
    std::string deskId;

    /** Client specified identifier of order relevant to self-execution */
    std::string noTradeKey;

    static void parse(NativeExecutionReport& msg, const uint8_t* data) {
        msg.partitionId = data[0];
        std::memcpy(&msg.sequenceNumber, data + 1, 8);
        msg.executionId.assign(reinterpret_cast<const char*>(data + 9), strnlen(reinterpret_cast<const char*>(data + 9), 21));
        msg.clientOrderId.assign(reinterpret_cast<const char*>(data + 30), strnlen(reinterpret_cast<const char*>(data + 30), 10));
        msg.orderId.assign(reinterpret_cast<const char*>(data + 40), strnlen(reinterpret_cast<const char*>(data + 40), 12));
        msg.publicOrderId.assign(reinterpret_cast<const char*>(data + 52), strnlen(reinterpret_cast<const char*>(data + 52), 12));
        std::memcpy(&msg.executedPrice, data + 64, 8);
        std::memcpy(&msg.transactTime, data + 72, 8);
        std::memcpy(&msg.account, data + 80, 8);
        msg.senderLocation.assign(reinterpret_cast<const char*>(data + 88), strnlen(reinterpret_cast<const char*>(data + 88), 10));
        std::memcpy(&msg.instrumentId, data + 98, 4);
        msg.crossId.assign(reinterpret_cast<const char*>(data + 102), strnlen(reinterpret_cast<const char*>(data + 102), 10));
        std::memcpy(&msg.executedQuantity, data + 112, 4);
        std::memcpy(&msg.leavesQuantity, data + 116, 4);
        msg.executionType.assign(reinterpret_cast<const char*>(data + 120), strnlen(reinterpret_cast<const char*>(data + 120), 1));
        msg.orderStatus = data[121];
        std::memcpy(&msg.rejectCode, data + 122, 4);
        msg.workingIndicator = data[126];
        msg.side = data[127];
        msg.restatementReason = data[128];
        msg.orderBook = data[129];
        msg.indicatorFlags = data[130];
        msg.crossType = data[131];
        msg.routingInstruction = data[132];
        msg.tradingPhase = data[133];
        msg.executionVenue.assign(reinterpret_cast<const char*>(data + 134), strnlen(reinterpret_cast<const char*>(data + 134), 4));
        msg.enteringTrader.assign(reinterpret_cast<const char*>(data + 138), strnlen(reinterpret_cast<const char*>(data + 138), 15));
        msg.memo.assign(reinterpret_cast<const char*>(data + 153), strnlen(reinterpret_cast<const char*>(data + 153), 15));
        msg.deskId.assign(reinterpret_cast<const char*>(data + 168), strnlen(reinterpret_cast<const char*>(data + 168), 15));
        msg.noTradeKey.assign(reinterpret_cast<const char*>(data + 183), strnlen(reinterpret_cast<const char*>(data + 183), 10));
    }

    static void encode(const NativeExecutionReport& msg, uint8_t* data) {
        data[0] = msg.partitionId;
        std::memcpy(data + 1, &msg.sequenceNumber, 8);
        std::memset(data + 9, 0, 21); std::memcpy(data + 9, msg.executionId.c_str(), std::min(msg.executionId.size(), size_t(21)));
        std::memset(data + 30, 0, 10); std::memcpy(data + 30, msg.clientOrderId.c_str(), std::min(msg.clientOrderId.size(), size_t(10)));
        std::memset(data + 40, 0, 12); std::memcpy(data + 40, msg.orderId.c_str(), std::min(msg.orderId.size(), size_t(12)));
        std::memset(data + 52, 0, 12); std::memcpy(data + 52, msg.publicOrderId.c_str(), std::min(msg.publicOrderId.size(), size_t(12)));
        std::memcpy(data + 64, &msg.executedPrice, 8);
        std::memcpy(data + 72, &msg.transactTime, 8);
        std::memcpy(data + 80, &msg.account, 8);
        std::memset(data + 88, 0, 10); std::memcpy(data + 88, msg.senderLocation.c_str(), std::min(msg.senderLocation.size(), size_t(10)));
        std::memcpy(data + 98, &msg.instrumentId, 4);
        std::memset(data + 102, 0, 10); std::memcpy(data + 102, msg.crossId.c_str(), std::min(msg.crossId.size(), size_t(10)));
        std::memcpy(data + 112, &msg.executedQuantity, 4);
        std::memcpy(data + 116, &msg.leavesQuantity, 4);
        std::memset(data + 120, 0, 1); std::memcpy(data + 120, msg.executionType.c_str(), std::min(msg.executionType.size(), size_t(1)));
        data[121] = msg.orderStatus;
        std::memcpy(data + 122, &msg.rejectCode, 4);
        data[126] = msg.workingIndicator;
        data[127] = msg.side;
        data[128] = msg.restatementReason;
        data[129] = msg.orderBook;
        data[130] = msg.indicatorFlags;
        data[131] = msg.crossType;
        data[132] = msg.routingInstruction;
        data[133] = msg.tradingPhase;
        std::memset(data + 134, 0, 4); std::memcpy(data + 134, msg.executionVenue.c_str(), std::min(msg.executionVenue.size(), size_t(4)));
        std::memset(data + 138, 0, 15); std::memcpy(data + 138, msg.enteringTrader.c_str(), std::min(msg.enteringTrader.size(), size_t(15)));
        std::memset(data + 153, 0, 15); std::memcpy(data + 153, msg.memo.c_str(), std::min(msg.memo.size(), size_t(15)));
        std::memset(data + 168, 0, 15); std::memcpy(data + 168, msg.deskId.c_str(), std::min(msg.deskId.size(), size_t(15)));
        std::memset(data + 183, 0, 10); std::memcpy(data + 183, msg.noTradeKey.c_str(), std::min(msg.noTradeKey.size(), size_t(10)));
    }
};

} // namespace a5x::trading::native
