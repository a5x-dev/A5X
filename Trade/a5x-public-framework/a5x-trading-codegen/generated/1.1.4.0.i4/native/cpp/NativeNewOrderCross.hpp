#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * New Order Cross
 * Message Type: C
 */
struct NativeNewOrderCross {
    /** Identifier of the cross order. Only alphanumeric values allowed */
    std::string crossId;

    /** Price of the cross order */
    int64_t limitPrice;

    /** Total order quantity of the cross order */
    uint32_t orderQuantity;

    /** Entering Trader ID */
    std::string enteringTrader;

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** Sender Location assigned to the Trader ID/Firm */
    std::string senderLocation;

    /** 5 = Internal Cross. Any other value rejected */
    uint8_t crossType;

    /** Client specified identifier of the buy side. Required */
    std::string buySideClientOrderId;

    /** Identifier of the buyer's trading account. Required */
    uint64_t buySideAccountId;

    /** Client specified identifier of the sell side. Required */
    std::string sellSideClientOrderId;

    /** Identifier of the seller's trading account. Required */
    uint64_t sellSideAccountId;

    /** 2 = Limit. Any other value rejected */
    uint8_t orderType;

    /** 0 = Day. Only DAY TIF allowed for cross orders */
    uint8_t timeInForce;

    /** Desk ID through which the order is placed from */
    std::string deskId;

    /** Free format text field. Echoed in reports */
    std::string memo;

    static void parse(NativeNewOrderCross& msg, const uint8_t* data) {
        msg.crossId.assign(reinterpret_cast<const char*>(data + 0), strnlen(reinterpret_cast<const char*>(data + 0), 10));
        std::memcpy(&msg.limitPrice, data + 10, 8);
        std::memcpy(&msg.orderQuantity, data + 18, 4);
        msg.enteringTrader.assign(reinterpret_cast<const char*>(data + 22), strnlen(reinterpret_cast<const char*>(data + 22), 15));
        std::memcpy(&msg.instrumentId, data + 37, 4);
        msg.senderLocation.assign(reinterpret_cast<const char*>(data + 41), strnlen(reinterpret_cast<const char*>(data + 41), 10));
        msg.crossType = data[51];
        msg.buySideClientOrderId.assign(reinterpret_cast<const char*>(data + 52), strnlen(reinterpret_cast<const char*>(data + 52), 10));
        std::memcpy(&msg.buySideAccountId, data + 62, 8);
        msg.sellSideClientOrderId.assign(reinterpret_cast<const char*>(data + 70), strnlen(reinterpret_cast<const char*>(data + 70), 10));
        std::memcpy(&msg.sellSideAccountId, data + 80, 8);
        msg.orderType = data[88];
        msg.timeInForce = data[89];
        msg.deskId.assign(reinterpret_cast<const char*>(data + 90), strnlen(reinterpret_cast<const char*>(data + 90), 15));
        msg.memo.assign(reinterpret_cast<const char*>(data + 105), strnlen(reinterpret_cast<const char*>(data + 105), 15));
    }

    static void encode(const NativeNewOrderCross& msg, uint8_t* data) {
        std::memset(data + 0, 0, 10); std::memcpy(data + 0, msg.crossId.c_str(), std::min(msg.crossId.size(), size_t(10)));
        std::memcpy(data + 10, &msg.limitPrice, 8);
        std::memcpy(data + 18, &msg.orderQuantity, 4);
        std::memset(data + 22, 0, 15); std::memcpy(data + 22, msg.enteringTrader.c_str(), std::min(msg.enteringTrader.size(), size_t(15)));
        std::memcpy(data + 37, &msg.instrumentId, 4);
        std::memset(data + 41, 0, 10); std::memcpy(data + 41, msg.senderLocation.c_str(), std::min(msg.senderLocation.size(), size_t(10)));
        data[51] = msg.crossType;
        std::memset(data + 52, 0, 10); std::memcpy(data + 52, msg.buySideClientOrderId.c_str(), std::min(msg.buySideClientOrderId.size(), size_t(10)));
        std::memcpy(data + 62, &msg.buySideAccountId, 8);
        std::memset(data + 70, 0, 10); std::memcpy(data + 70, msg.sellSideClientOrderId.c_str(), std::min(msg.sellSideClientOrderId.size(), size_t(10)));
        std::memcpy(data + 80, &msg.sellSideAccountId, 8);
        data[88] = msg.orderType;
        data[89] = msg.timeInForce;
        std::memset(data + 90, 0, 15); std::memcpy(data + 90, msg.deskId.c_str(), std::min(msg.deskId.size(), size_t(15)));
        std::memset(data + 105, 0, 15); std::memcpy(data + 105, msg.memo.c_str(), std::min(msg.memo.size(), size_t(15)));
    }
};

} // namespace a5x::trading::native
