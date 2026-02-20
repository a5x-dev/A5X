#ifndef MITCHSTATISTICS_H
#define MITCHSTATISTICS_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent to provide statistical information about instruments
struct MitchStatistics {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Numeric identifier of the instrument
    uint32_t instrumentId;

    /// C = Closing Price, O = Opening Price, P = Previous Close
    uint8_t statisticType;

    /// Opening or closing price
    int64_t price;

    double priceAsDecimal() const { return price / 10000.0; }

    /// A = Auction Trade, B = Regular Trade, C = Mid Point, D = Last Regular Trade, E = Last Auction, F = Manual, H = VWAP, I = Previous Close, T = Theoretical Price, L = VWAP n Volume, U = Best Bid, V = Best Offer, W = None, X = VWAP of Last n Trades, Y = Reference Price, Z = Price Unavailable
    uint8_t openCloseIndicator;

    /// 1 = Regular, 2 = Off-Book, 9 = Bulletin Board
    uint8_t subBook;

    static void parse(MitchStatistics& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.instrumentId, data + offset, 4);
        offset += 4;
        msg.statisticType = data[offset++];
        std::memcpy(&msg.price, data + offset, 8);
        offset += 8;
        msg.openCloseIndicator = data[offset++];
        msg.subBook = data[offset++];
    }

    static void encode(const MitchStatistics& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.instrumentId, 4);
        offset += 4;
        data[offset++] = msg.statisticType;
        std::memcpy(data + offset, &msg.price, 8);
        offset += 8;
        data[offset++] = msg.openCloseIndicator;
        data[offset++] = msg.subBook;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHSTATISTICS_H
