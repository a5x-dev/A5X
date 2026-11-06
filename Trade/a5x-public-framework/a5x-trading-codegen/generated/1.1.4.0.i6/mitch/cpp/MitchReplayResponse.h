#ifndef MITCHREPLAYRESPONSE_H
#define MITCHREPLAYRESPONSE_H

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

/// Sent by server in response to replay request
struct MitchReplayResponse {
    /// Identity of the market data group the replay request relates to
    uint8_t marketDataGroup;

    /// Sequence number of the first message in range to be retransmitted. This will be zero if Status is not 'A'
    uint64_t firstMessage;

    /// Number of messages to be resent. This will be zero if Status is not 'A'
    uint16_t count;

    /// A = Request Accepted, D = Request Limit Reached, I = Invalid Market Data Group, O = Out of Range, U = Replay Unavailable, c = Concurrent Limit Reached, d = Unsupported Message Type, e = Failed (Other)
    uint8_t status;

    static void parse(MitchReplayResponse& msg, const uint8_t* data) {
        size_t offset = 0;
        msg.marketDataGroup = data[offset++];
        msg.firstMessage = a5x_read_le64(data + offset);
        offset += 8;
        msg.count = a5x_read_le16(data + offset);
        offset += 2;
        msg.status = data[offset++];
    }

    static void encode(const MitchReplayResponse& msg, uint8_t* data) {
        size_t offset = 0;
        data[offset++] = msg.marketDataGroup;
        a5x_write_le64(data + offset, msg.firstMessage);
        offset += 8;
        a5x_write_le16(data + offset, msg.count);
        offset += 2;
        data[offset++] = msg.status;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHREPLAYRESPONSE_H
