#ifndef MITCHORDERBOOKCLEAR_H
#define MITCHORDERBOOKCLEAR_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent to indicate that the order book for an instrument should be cleared
struct MitchOrderBookClear {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Numeric identifier of the instrument
    uint32_t instrumentId;

    /// 1 = Regular, 2 = Off-Book, 9 = Bulletin Board, 11 = Negotiated Trades
    uint8_t subBook;

    /// 0 = MBP/MBO/Statistics, 1 = Top of Book
    uint8_t bookType;

    static void parse(MitchOrderBookClear& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.instrumentId, data + offset, 4);
        offset += 4;
        msg.subBook = data[offset++];
        msg.bookType = data[offset++];
    }

    static void encode(const MitchOrderBookClear& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.instrumentId, 4);
        offset += 4;
        data[offset++] = msg.subBook;
        data[offset++] = msg.bookType;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHORDERBOOKCLEAR_H
