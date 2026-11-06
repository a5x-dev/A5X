#ifndef MITCHSNAPSHOTRESPONSE_H
#define MITCHSNAPSHOTRESPONSE_H

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

/// Sent by server in response to snapshot request
struct MitchSnapshotResponse {
    /// Sequence number with which the snapshot is synchronized. This will be zero if Status is not 'A'. Ignore if Snapshot Type is not Order Book (0)
    uint64_t sequenceNumber;

    /// Number of orders that will be transmitted in the snapshot. This will be zero if Order Book is empty or Status is not 'A'. However, if the Snapshot Request was submitted for multiple order books, this field will be set to zero. Ignore if Snapshot Type is not Order Book (0)
    uint32_t orderCount;

    /// A = Request Accepted, O = Out of Range, U = Snapshot Unavailable, a = Segment/Symbol/Sub Book Invalid or Not Specified, b = Request Limit Reached, c = Concurrent Limit Reached, d = Unsupported Message Type, e = Failed (Other)
    uint8_t status;

    /// 0=Order Book, 1=Symbol Status, 2=Instrument, 3=Trades, 4=Statistics, 5=News, 8=Top of Book
    uint8_t snapshotType;

    /// Identifier, if any, of Snapshot Request
    uint32_t requestId;

    static void parse(MitchSnapshotResponse& msg, const uint8_t* data) {
        size_t offset = 0;
        msg.sequenceNumber = a5x_read_le64(data + offset);
        offset += 8;
        msg.orderCount = a5x_read_le32(data + offset);
        offset += 4;
        msg.status = data[offset++];
        msg.snapshotType = data[offset++];
        msg.requestId = a5x_read_le32(data + offset);
        offset += 4;
    }

    static void encode(const MitchSnapshotResponse& msg, uint8_t* data) {
        size_t offset = 0;
        a5x_write_le64(data + offset, msg.sequenceNumber);
        offset += 8;
        a5x_write_le32(data + offset, msg.orderCount);
        offset += 4;
        data[offset++] = msg.status;
        data[offset++] = msg.snapshotType;
        a5x_write_le32(data + offset, msg.requestId);
        offset += 4;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHSNAPSHOTRESPONSE_H
