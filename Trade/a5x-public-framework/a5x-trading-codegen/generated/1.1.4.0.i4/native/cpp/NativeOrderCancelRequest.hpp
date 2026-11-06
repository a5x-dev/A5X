#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * Order cancel request
 * Message Type: F
 */
struct NativeOrderCancelRequest {
    /** Client specified identifier of the request */
    std::string clientOrderId;

    /** Client specified identifier of the order or quote being cancelled */
    std::string origClientOrderId;

    /** Server specified identifier of the order being cancelled */
    std::string orderId;

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** 1 = Buy, 2 = Sell */
    uint8_t side;

    /** 1 = Regular */
    uint8_t orderBook;

    /** Sender Location assigned to the Trader ID/Firm */
    std::string senderLocation;

    static void parse(NativeOrderCancelRequest& msg, const uint8_t* data) {
        msg.clientOrderId.assign(reinterpret_cast<const char*>(data + 0), strnlen(reinterpret_cast<const char*>(data + 0), 10));
        msg.origClientOrderId.assign(reinterpret_cast<const char*>(data + 10), strnlen(reinterpret_cast<const char*>(data + 10), 10));
        msg.orderId.assign(reinterpret_cast<const char*>(data + 20), strnlen(reinterpret_cast<const char*>(data + 20), 12));
        std::memcpy(&msg.instrumentId, data + 32, 4);
        msg.side = data[36];
        msg.orderBook = data[37];
        msg.senderLocation.assign(reinterpret_cast<const char*>(data + 38), strnlen(reinterpret_cast<const char*>(data + 38), 10));
    }

    static void encode(const NativeOrderCancelRequest& msg, uint8_t* data) {
        std::memset(data + 0, 0, 10); std::memcpy(data + 0, msg.clientOrderId.c_str(), std::min(msg.clientOrderId.size(), size_t(10)));
        std::memset(data + 10, 0, 10); std::memcpy(data + 10, msg.origClientOrderId.c_str(), std::min(msg.origClientOrderId.size(), size_t(10)));
        std::memset(data + 20, 0, 12); std::memcpy(data + 20, msg.orderId.c_str(), std::min(msg.orderId.size(), size_t(12)));
        std::memcpy(data + 32, &msg.instrumentId, 4);
        data[36] = msg.side;
        data[37] = msg.orderBook;
        std::memset(data + 38, 0, 10); std::memcpy(data + 38, msg.senderLocation.c_str(), std::min(msg.senderLocation.size(), size_t(10)));
    }
};

} // namespace a5x::trading::native
