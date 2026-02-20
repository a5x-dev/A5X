#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Order Replace Request
 * Message Type: G
 */
struct NativeOrderReplaceRequest {
    /** Limit price. Ignored if Order Type is not Limit (2) or Stop Limit (4) */
    int32_t limitPrice;

    double limitPriceAsDecimal() const { return limitPrice / 10000.0; }

    /** Total order quantity */
    uint32_t orderQuantity;

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** 1 = Buy, 2 = Sell */
    uint8_t side;

    /** Client specified identifier of the order */
    uint8_t clientOrderId[10];

    /** Client specified identifier of the order being modified */
    uint8_t origClientOrderId[10];

    /** Server specified identifier of the order being cancelled */
    uint8_t orderId[12];

    /** Identifier of the Trader Account. Required field */
    uint64_t account;

    /** Sender Location assigned to the Trader ID/Firm */
    uint8_t senderLocation[10];

    /** Stop price/Trigger price. Ignored if Order Type is not Stop (3) or Stop Limit (4) */
    int32_t stopPrice;

    double stopPriceAsDecimal() const { return stopPrice / 10000.0; }

    /** Maximum quantity to be displayed. Negative value to ignore */
    uint32_t displayQuantity;

    /** 1 = Regular */
    uint8_t orderBook;

    /** Bit 0: Passive Only (0 = No, 1 = Yes) */
    uint8_t executionInstruction;

    /** 1 = Market, 2 = Limit, 3 = Stop, 4 = Stop Limit, 5 = Market to Limit, 51 = RLP */
    uint8_t orderType;

    /** 0 = Day, 3 = IOC, 4 = FOK, 5 = OPG, 8 = GTT, 9 = GFA, 10 = ATC */
    uint8_t timeInForce;

    /** Expire time in epoch (seconds precision). Ignored if Time in Force is not GTT (8) */
    uint64_t expireTime;

    static void parse(NativeOrderReplaceRequest& msg, const uint8_t* data) {
        std::memcpy(&msg.limitPrice, data + 0, 4);
        std::memcpy(&msg.orderQuantity, data + 4, 4);
        std::memcpy(&msg.instrumentId, data + 8, 4);
        msg.side = data[12];
        std::memcpy(msg.clientOrderId, data + 13, 10);
        std::memcpy(msg.origClientOrderId, data + 23, 10);
        std::memcpy(msg.orderId, data + 33, 12);
        std::memcpy(&msg.account, data + 45, 8);
        std::memcpy(msg.senderLocation, data + 53, 10);
        std::memcpy(&msg.stopPrice, data + 63, 4);
        std::memcpy(&msg.displayQuantity, data + 67, 4);
        msg.orderBook = data[71];
        msg.executionInstruction = data[72];
        msg.orderType = data[73];
        msg.timeInForce = data[74];
        std::memcpy(&msg.expireTime, data + 75, 8);
    }

    static void encode(const NativeOrderReplaceRequest& msg, uint8_t* data) {
        std::memcpy(data + 0, &msg.limitPrice, 4);
        std::memcpy(data + 4, &msg.orderQuantity, 4);
        std::memcpy(data + 8, &msg.instrumentId, 4);
        data[12] = msg.side;
        std::memcpy(data + 13, msg.clientOrderId, 10);
        std::memcpy(data + 23, msg.origClientOrderId, 10);
        std::memcpy(data + 33, msg.orderId, 12);
        std::memcpy(data + 45, &msg.account, 8);
        std::memcpy(data + 53, msg.senderLocation, 10);
        std::memcpy(data + 63, &msg.stopPrice, 4);
        std::memcpy(data + 67, &msg.displayQuantity, 4);
        data[71] = msg.orderBook;
        data[72] = msg.executionInstruction;
        data[73] = msg.orderType;
        data[74] = msg.timeInForce;
        std::memcpy(data + 75, &msg.expireTime, 8);
    }
};

} // namespace a5x::trading::native
