#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * Mass Cancel Request
 * Message Type: q
 */
struct NativeMassCancelRequest {
    /** Client specified identifier of the rejected message if available */
    std::string clientOrderId;

    /** 3 = Firm Interest for Instrument, 4 = Firm Interest for Segment, 7 = All Interest for Client, 8 = All Interest for Firm, 9 = Client Interest for Instrument, 14 = Client Interest for Underlying, 15 = Client Interest for Segment, 22 = Firm Interest for Underlying */
    uint8_t massCancelType;

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** Identifier of the segment */
    std::string segment;

    /** 0 = Order, 3 = Quote */
    uint8_t interestType;

    /** 1 = Regular */
    uint8_t orderBook;

    /** Sender Location assigned to the Trader ID/Firm */
    std::string senderLocation;

    /** Entering Trader ID */
    std::string enteringTrader;

    /** Desk ID through which the request is placed from */
    std::string deskId;

    static void parse(NativeMassCancelRequest& msg, const uint8_t* data) {
        msg.clientOrderId.assign(reinterpret_cast<const char*>(data + 0), strnlen(reinterpret_cast<const char*>(data + 0), 10));
        msg.massCancelType = data[10];
        std::memcpy(&msg.instrumentId, data + 11, 4);
        msg.segment.assign(reinterpret_cast<const char*>(data + 15), strnlen(reinterpret_cast<const char*>(data + 15), 10));
        msg.interestType = data[25];
        msg.orderBook = data[26];
        msg.senderLocation.assign(reinterpret_cast<const char*>(data + 27), strnlen(reinterpret_cast<const char*>(data + 27), 10));
        msg.enteringTrader.assign(reinterpret_cast<const char*>(data + 37), strnlen(reinterpret_cast<const char*>(data + 37), 15));
        msg.deskId.assign(reinterpret_cast<const char*>(data + 52), strnlen(reinterpret_cast<const char*>(data + 52), 15));
    }

    static void encode(const NativeMassCancelRequest& msg, uint8_t* data) {
        std::memset(data + 0, 0, 10); std::memcpy(data + 0, msg.clientOrderId.c_str(), std::min(msg.clientOrderId.size(), size_t(10)));
        data[10] = msg.massCancelType;
        std::memcpy(data + 11, &msg.instrumentId, 4);
        std::memset(data + 15, 0, 10); std::memcpy(data + 15, msg.segment.c_str(), std::min(msg.segment.size(), size_t(10)));
        data[25] = msg.interestType;
        data[26] = msg.orderBook;
        std::memset(data + 27, 0, 10); std::memcpy(data + 27, msg.senderLocation.c_str(), std::min(msg.senderLocation.size(), size_t(10)));
        std::memset(data + 37, 0, 15); std::memcpy(data + 37, msg.enteringTrader.c_str(), std::min(msg.enteringTrader.size(), size_t(15)));
        std::memset(data + 52, 0, 15); std::memcpy(data + 52, msg.deskId.c_str(), std::min(msg.deskId.size(), size_t(15)));
    }
};

} // namespace a5x::trading::native
