#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * Order Replace Request
 * Message Type: G
 */
struct NativeOrderReplaceRequest {
    /** Limit price. This field will be ignored if the Order Type is not Limit (2) or Stop Limit (4) */
    int64_t limitPrice;

    /** Total order quantity */
    uint32_t orderQuantity;

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** 1 = Buy, 2 = Sell */
    uint8_t side;

    /** Client specified identifier of the order */
    std::string clientOrderId;

    /** Client specified identifier of the order being modified */
    std::string origClientOrderId;

    /** Identifier of the Trader Account. This is a required field */
    uint64_t account;

    /** Sender Location assigned to the Trader ID/Firm */
    std::string senderLocation;

    /** Stop price/Trigger price. This field will be ignored if the Order Type is not Stop (3) or Stop Limit (4) */
    int64_t stopPrice;

    /** Maximum quantity to be displayed. A negative value should be used if this field is to be ignored */
    uint32_t displayQuantity;

    /** 1 = Regular */
    uint8_t orderBook;

    /** 1 = Market, 2 = Limit, 3 = Stop, 4 = Stop Limit, 5 = Market to Limit, 51 = RLP */
    uint8_t orderType;

    /** 0 = Day, 3 = IOC, 4 = FOK, 5 = OPG, 8 = GTT, 9 = GFA, 10 = ATC */
    uint8_t timeInForce;

    /** Expire time in epoch (seconds precision). Ignored if Time in Force is not GTT (8) */
    uint64_t expireTime;

    static void parse(NativeOrderReplaceRequest& msg, const uint8_t* data) {
        std::memcpy(&msg.limitPrice, data + 0, 8);
        std::memcpy(&msg.orderQuantity, data + 8, 4);
        std::memcpy(&msg.instrumentId, data + 12, 4);
        msg.side = data[16];
        msg.clientOrderId.assign(reinterpret_cast<const char*>(data + 17), strnlen(reinterpret_cast<const char*>(data + 17), 10));
        msg.origClientOrderId.assign(reinterpret_cast<const char*>(data + 27), strnlen(reinterpret_cast<const char*>(data + 27), 10));
        std::memcpy(&msg.account, data + 37, 8);
        msg.senderLocation.assign(reinterpret_cast<const char*>(data + 45), strnlen(reinterpret_cast<const char*>(data + 45), 10));
        std::memcpy(&msg.stopPrice, data + 55, 8);
        std::memcpy(&msg.displayQuantity, data + 63, 4);
        msg.orderBook = data[67];
        msg.orderType = data[68];
        msg.timeInForce = data[69];
        std::memcpy(&msg.expireTime, data + 70, 8);
    }

    static void encode(const NativeOrderReplaceRequest& msg, uint8_t* data) {
        std::memcpy(data + 0, &msg.limitPrice, 8);
        std::memcpy(data + 8, &msg.orderQuantity, 4);
        std::memcpy(data + 12, &msg.instrumentId, 4);
        data[16] = msg.side;
        std::memset(data + 17, 0, 10); std::memcpy(data + 17, msg.clientOrderId.c_str(), std::min(msg.clientOrderId.size(), size_t(10)));
        std::memset(data + 27, 0, 10); std::memcpy(data + 27, msg.origClientOrderId.c_str(), std::min(msg.origClientOrderId.size(), size_t(10)));
        std::memcpy(data + 37, &msg.account, 8);
        std::memset(data + 45, 0, 10); std::memcpy(data + 45, msg.senderLocation.c_str(), std::min(msg.senderLocation.size(), size_t(10)));
        std::memcpy(data + 55, &msg.stopPrice, 8);
        std::memcpy(data + 63, &msg.displayQuantity, 4);
        data[67] = msg.orderBook;
        data[68] = msg.orderType;
        data[69] = msg.timeInForce;
        std::memcpy(data + 70, &msg.expireTime, 8);
    }
};

} // namespace a5x::trading::native
