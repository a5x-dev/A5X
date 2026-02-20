#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * New Order Cross
 * Message Type: u
 */
struct NativeNewOrderCross {
    /** Identifier of the cross order. Only alphanumeric values allowed */
    uint8_t crossId[10];

    /** Price of the cross order */
    int32_t limitPrice;

    double limitPriceAsDecimal() const { return limitPrice / 10000.0; }

    /** Total order quantity of the cross order */
    uint32_t orderQuantity;

    /** Entering Trader ID */
    uint8_t enteringTrader[15];

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** Sender Location assigned to the Trader ID/Firm */
    uint8_t senderLocation[10];

    /** 5 = Internal Cross. Any other value rejected */
    uint8_t crossType;

    /** Client specified identifier of the buy side. Required */
    uint8_t buySideClientOrderId[10];

    /** Identifier of the buyer's trading account. Required */
    uint64_t buySideAccountId;

    /** Client specified identifier of the sell side. Required */
    uint8_t sellSideClientOrderId[10];

    /** Identifier of the seller's trading account. Required */
    uint64_t sellSideAccountId;

    /** 2 = Limit. Any other value rejected */
    uint8_t orderType;

    /** 0 = Day. Only DAY TIF allowed for cross orders */
    uint8_t timeInForce;

    /** Desk ID through which the order is placed from */
    uint8_t deskId[15];

    /** Free format text field. Echoed in reports */
    uint8_t memo[15];

    static void parse(NativeNewOrderCross& msg, const uint8_t* data) {
        std::memcpy(msg.crossId, data + 0, 10);
        std::memcpy(&msg.limitPrice, data + 10, 4);
        std::memcpy(&msg.orderQuantity, data + 14, 4);
        std::memcpy(msg.enteringTrader, data + 18, 15);
        std::memcpy(&msg.instrumentId, data + 33, 4);
        std::memcpy(msg.senderLocation, data + 37, 10);
        msg.crossType = data[47];
        std::memcpy(msg.buySideClientOrderId, data + 48, 10);
        std::memcpy(&msg.buySideAccountId, data + 58, 8);
        std::memcpy(msg.sellSideClientOrderId, data + 66, 10);
        std::memcpy(&msg.sellSideAccountId, data + 76, 8);
        msg.orderType = data[84];
        msg.timeInForce = data[85];
        std::memcpy(msg.deskId, data + 86, 15);
        std::memcpy(msg.memo, data + 101, 15);
    }

    static void encode(const NativeNewOrderCross& msg, uint8_t* data) {
        std::memcpy(data + 0, msg.crossId, 10);
        std::memcpy(data + 10, &msg.limitPrice, 4);
        std::memcpy(data + 14, &msg.orderQuantity, 4);
        std::memcpy(data + 18, msg.enteringTrader, 15);
        std::memcpy(data + 33, &msg.instrumentId, 4);
        std::memcpy(data + 37, msg.senderLocation, 10);
        data[47] = msg.crossType;
        std::memcpy(data + 48, msg.buySideClientOrderId, 10);
        std::memcpy(data + 58, &msg.buySideAccountId, 8);
        std::memcpy(data + 66, msg.sellSideClientOrderId, 10);
        std::memcpy(data + 76, &msg.sellSideAccountId, 8);
        data[84] = msg.orderType;
        data[85] = msg.timeInForce;
        std::memcpy(data + 86, msg.deskId, 15);
        std::memcpy(data + 101, msg.memo, 15);
    }
};

} // namespace a5x::trading::native
