#ifndef MITCHAUCTIONINFO_H
#define MITCHAUCTIONINFO_H

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

#ifndef A5X_MITCH_WIRE_HELPERS
#define A5X_MITCH_WIRE_HELPERS
namespace {
inline uint16_t a5x_read_le16(const uint8_t* p) { uint16_t v = 0; for (int i = 1; i >= 0; --i) v = static_cast<uint16_t>((v << 8) | p[i]); return v; }
inline uint32_t a5x_read_le32(const uint8_t* p) { uint32_t v = 0; for (int i = 3; i >= 0; --i) v = (v << 8) | p[i]; return v; }
inline uint64_t a5x_read_le64(const uint8_t* p) { uint64_t v = 0; for (int i = 7; i >= 0; --i) v = (v << 8) | p[i]; return v; }
inline void a5x_write_le16(uint8_t* p, uint16_t v) { for (int i = 0; i < 2; ++i) p[i] = static_cast<uint8_t>(v >> (8 * i)); }
inline void a5x_write_le32(uint8_t* p, uint32_t v) { for (int i = 0; i < 4; ++i) p[i] = static_cast<uint8_t>(v >> (8 * i)); }
inline void a5x_write_le64(uint8_t* p, uint64_t v) { for (int i = 0; i < 8; ++i) p[i] = static_cast<uint8_t>(v >> (8 * i)); }
inline int32_t a5x_read_sm32(const uint8_t* p) { uint32_t v = a5x_read_le32(p); int32_t m = static_cast<int32_t>(v & 0x7FFFFFFFu); return (v >> 31) ? -m : m; }
inline int64_t a5x_read_sm64(const uint8_t* p) { uint64_t v = a5x_read_le64(p); int64_t m = static_cast<int64_t>(v & 0x7FFFFFFFFFFFFFFFull); return (v >> 63) ? -m : m; }
inline void a5x_write_sm32(uint8_t* p, int32_t v) { a5x_write_le32(p, v < 0 ? (static_cast<uint32_t>(-static_cast<int64_t>(v)) | 0x80000000u) : static_cast<uint32_t>(v)); }
inline void a5x_write_sm64(uint8_t* p, int64_t v) { a5x_write_le64(p, v < 0 ? ((~static_cast<uint64_t>(v) + 1) | 0x8000000000000000ull) : static_cast<uint64_t>(v)); }
} // namespace
#endif // A5X_MITCH_WIRE_HELPERS

namespace a5x::trading::mitch {

/// Sent to provide indicative auction information during auction periods
struct MitchAuctionInfo {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Quantity that will be matched at the indicative price
    uint32_t pairedQuantity;

    /// Quantity that is eligible to be matched at the indicative price but will not be matched
    uint32_t imbalanceQuantity;

    /// B = Buy Imbalance, N = No Imbalance, O = Insufficient Orders for Auction, S = Sell Imbalance
    uint8_t imbalanceDirection;

    /// Numeric identifier of the instrument
    uint32_t instrumentId;

    /// Indicative auction price
    int64_t price;

    double priceAsDecimal() const { return price / 100000000.0; }

    /// A = Re-Opening Auction, C = Closing Auction, O = Opening Auction
    uint8_t auctionType;

    static void parse(MitchAuctionInfo& msg, const uint8_t* data) {
        size_t offset = 0;
        msg.nanosecond = a5x_read_le32(data + offset);
        offset += 4;
        msg.pairedQuantity = a5x_read_le32(data + offset);
        offset += 4;
        msg.imbalanceQuantity = a5x_read_le32(data + offset);
        offset += 4;
        msg.imbalanceDirection = data[offset++];
        msg.instrumentId = a5x_read_le32(data + offset);
        offset += 4;
        msg.price = a5x_read_sm64(data + offset);
        offset += 8;
        msg.auctionType = data[offset++];
    }

    static void encode(const MitchAuctionInfo& msg, uint8_t* data) {
        size_t offset = 0;
        a5x_write_le32(data + offset, msg.nanosecond);
        offset += 4;
        a5x_write_le32(data + offset, msg.pairedQuantity);
        offset += 4;
        a5x_write_le32(data + offset, msg.imbalanceQuantity);
        offset += 4;
        data[offset++] = msg.imbalanceDirection;
        a5x_write_le32(data + offset, msg.instrumentId);
        offset += 4;
        a5x_write_sm64(data + offset, msg.price);
        offset += 8;
        data[offset++] = msg.auctionType;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHAUCTIONINFO_H
