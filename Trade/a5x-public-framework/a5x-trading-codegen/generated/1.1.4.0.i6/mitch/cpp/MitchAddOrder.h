#ifndef MITCHADDORDER_H
#define MITCHADDORDER_H

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

/// Sent when a new visible order is added to the order book
struct MitchAddOrder {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Unique identifier of the order
    uint64_t orderId;

    /// B = Buy, S = Sell
    uint8_t side;

    /// Displayed quantity of the order
    uint32_t quantity;

    /// Numeric identifier of the instrument
    uint32_t instrumentId;

    /// Limit price of the order. If Market, it will be filled with spaces
    int64_t price;

    double priceAsDecimal() const { return price / 100000000.0; }

    /// Bit 4: Market Order (0 = No, 1 = Yes), Bit 5: Bulletin Board (0 = No, 1 = Yes)
    uint8_t flags;

    /// Reserved for future use.
    std::string reserved1;

    static void parse(MitchAddOrder& msg, const uint8_t* data) {
        size_t offset = 0;
        msg.nanosecond = a5x_read_le32(data + offset);
        offset += 4;
        msg.orderId = a5x_read_le64(data + offset);
        offset += 8;
        msg.side = data[offset++];
        msg.quantity = a5x_read_le32(data + offset);
        offset += 4;
        msg.instrumentId = a5x_read_le32(data + offset);
        offset += 4;
        msg.price = a5x_read_sm64(data + offset);
        offset += 8;
        msg.flags = data[offset++];
        msg.reserved1 = std::string(reinterpret_cast<const char*>(data + offset), 10);
        msg.reserved1.erase(msg.reserved1.find_last_not_of(' ') + 1);
        offset += 10;
    }

    static void encode(const MitchAddOrder& msg, uint8_t* data) {
        size_t offset = 0;
        a5x_write_le32(data + offset, msg.nanosecond);
        offset += 4;
        a5x_write_le64(data + offset, msg.orderId);
        offset += 8;
        data[offset++] = msg.side;
        a5x_write_le32(data + offset, msg.quantity);
        offset += 4;
        a5x_write_le32(data + offset, msg.instrumentId);
        offset += 4;
        a5x_write_sm64(data + offset, msg.price);
        offset += 8;
        data[offset++] = msg.flags;
        std::memset(data + offset, ' ', 10);
        std::memcpy(data + offset, msg.reserved1.c_str(), std::min(msg.reserved1.size(), size_t(10)));
        offset += 10;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHADDORDER_H
