#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Mass Cancel Request
 * Message Type: q
 */
struct NativeMassCancelRequest {
    /** Client specified identifier of the rejected message if available */
    uint8_t clientOrderId[10];

    /** 3 = Firm Interest for Instrument, 4 = Firm Interest for Segment, 7 = All Interest for Client, 8 = All Interest for Firm, 9 = Client Interest for Instrument, 14 = Client Interest for Underlying, 15 = Client Interest for Segment, 22 = Firm Interest for Underlying */
    uint8_t massCancelType;

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** Identifier of the segment */
    uint8_t segment[10];

    /** 0 = Order, 3 = Quote */
    uint8_t interestType;

    /** 1 = Regular */
    uint8_t orderBook;

    /** Sender Location assigned to the Trader ID/Firm */
    uint8_t senderLocation[10];

    static void parse(NativeMassCancelRequest& msg, const uint8_t* data) {
        std::memcpy(msg.clientOrderId, data + 0, 10);
        msg.massCancelType = data[10];
        std::memcpy(&msg.instrumentId, data + 11, 4);
        std::memcpy(msg.segment, data + 15, 10);
        msg.interestType = data[25];
        msg.orderBook = data[26];
        std::memcpy(msg.senderLocation, data + 27, 10);
    }

    static void encode(const NativeMassCancelRequest& msg, uint8_t* data) {
        std::memcpy(data + 0, msg.clientOrderId, 10);
        data[10] = msg.massCancelType;
        std::memcpy(data + 11, &msg.instrumentId, 4);
        std::memcpy(data + 15, msg.segment, 10);
        data[25] = msg.interestType;
        data[26] = msg.orderBook;
        std::memcpy(data + 27, msg.senderLocation, 10);
    }
};

} // namespace a5x::trading::native
