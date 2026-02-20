#pragma once

#include <cstdint>
#include <cstring>

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
    uint8_t executionId[21];

    /** Client specified identifier of the order, cancel request or modification requests */
    uint8_t clientOrderId[10];

    /** Server specified identifier of the order */
    uint8_t orderId[12];

    /** Server specified public order identifier of the order */
    uint8_t publicOrderId[12];

    /** Executed price. Ignore if Execution Type is not Trade (F) */
    int32_t executedPrice;

    double executedPriceAsDecimal() const { return executedPrice / 10000.0; }

    /** Time the transaction occurred in epoch format */
    uint64_t transactTime;

    /** Identifier of the Trader Account */
    uint64_t account;

    /** Sender Location assigned to the Trader ID/Firm */
    uint8_t senderLocation[10];

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** Identifier of the cross order. Only alphanumeric values allowed */
    uint8_t crossId[10];

    /** Executed quantity. Ignore if Execution Type is not Trade (F) */
    uint32_t executedQuantity;

    /** Quantity available for further execution */
    uint32_t leavesQuantity;

    /** 0 = New, 4 = Cancelled, 5 = Modified, 8 = Rejected, 9 = Suspended, A = Pending New, C = Expired, D = Restated, E = Pending Replace, F = Trade, H = Trade Cancel, L = Triggered */
    uint8_t executionType[1];

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
    uint8_t executionVenue[4];

    /** Entering Trader ID */
    uint8_t enteringTrader[15];

    /** Free format text field. Echoed in reports */
    uint8_t memo[15];

    /** Desk ID through which the order is placed from */
    uint8_t deskId[15];

    /** Client specified identifier of order relevant to self-execution */
    uint8_t noTradeKey[10];

    static void parse(NativeExecutionReport& msg, const uint8_t* data) {
        msg.partitionId = data[0];
        std::memcpy(&msg.sequenceNumber, data + 1, 8);
        std::memcpy(msg.executionId, data + 9, 21);
        std::memcpy(msg.clientOrderId, data + 30, 10);
        std::memcpy(msg.orderId, data + 40, 12);
        std::memcpy(msg.publicOrderId, data + 52, 12);
        std::memcpy(&msg.executedPrice, data + 64, 4);
        std::memcpy(&msg.transactTime, data + 68, 8);
        std::memcpy(&msg.account, data + 76, 8);
        std::memcpy(msg.senderLocation, data + 84, 10);
        std::memcpy(&msg.instrumentId, data + 94, 4);
        std::memcpy(msg.crossId, data + 98, 10);
        std::memcpy(&msg.executedQuantity, data + 108, 4);
        std::memcpy(&msg.leavesQuantity, data + 112, 4);
        std::memcpy(msg.executionType, data + 116, 1);
        msg.orderStatus = data[117];
        std::memcpy(&msg.rejectCode, data + 118, 4);
        msg.workingIndicator = data[122];
        msg.side = data[123];
        msg.restatementReason = data[124];
        msg.orderBook = data[125];
        msg.indicatorFlags = data[126];
        msg.crossType = data[127];
        msg.routingInstruction = data[128];
        msg.tradingPhase = data[129];
        std::memcpy(msg.executionVenue, data + 130, 4);
        std::memcpy(msg.enteringTrader, data + 134, 15);
        std::memcpy(msg.memo, data + 149, 15);
        std::memcpy(msg.deskId, data + 164, 15);
        std::memcpy(msg.noTradeKey, data + 179, 10);
    }

    static void encode(const NativeExecutionReport& msg, uint8_t* data) {
        data[0] = msg.partitionId;
        std::memcpy(data + 1, &msg.sequenceNumber, 8);
        std::memcpy(data + 9, msg.executionId, 21);
        std::memcpy(data + 30, msg.clientOrderId, 10);
        std::memcpy(data + 40, msg.orderId, 12);
        std::memcpy(data + 52, msg.publicOrderId, 12);
        std::memcpy(data + 64, &msg.executedPrice, 4);
        std::memcpy(data + 68, &msg.transactTime, 8);
        std::memcpy(data + 76, &msg.account, 8);
        std::memcpy(data + 84, msg.senderLocation, 10);
        std::memcpy(data + 94, &msg.instrumentId, 4);
        std::memcpy(data + 98, msg.crossId, 10);
        std::memcpy(data + 108, &msg.executedQuantity, 4);
        std::memcpy(data + 112, &msg.leavesQuantity, 4);
        std::memcpy(data + 116, msg.executionType, 1);
        data[117] = msg.orderStatus;
        std::memcpy(data + 118, &msg.rejectCode, 4);
        data[122] = msg.workingIndicator;
        data[123] = msg.side;
        data[124] = msg.restatementReason;
        data[125] = msg.orderBook;
        data[126] = msg.indicatorFlags;
        data[127] = msg.crossType;
        data[128] = msg.routingInstruction;
        data[129] = msg.tradingPhase;
        std::memcpy(data + 130, msg.executionVenue, 4);
        std::memcpy(data + 134, msg.enteringTrader, 15);
        std::memcpy(data + 149, msg.memo, 15);
        std::memcpy(data + 164, msg.deskId, 15);
        std::memcpy(data + 179, msg.noTradeKey, 10);
    }
};

} // namespace a5x::trading::native
