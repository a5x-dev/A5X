#ifndef MITCHSNAPSHOTREQUEST_H
#define MITCHSNAPSHOTREQUEST_H

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

/// Sent by client to request snapshot data
struct MitchSnapshotRequest {
    /// Sequence number from which client can build the order book
    uint64_t sequenceNumber;

    /// Segment the request relates to. The field should contain only spaces if it does not relate to a segment
    std::string segment;

    /// Instrument ID of the instrument that the request relates to. The field should contain only spaces if it does not relate to an instrument. This field is ignored if Segment is specified
    uint32_t instrumentId;

    /// Bit 0: Regular (0=No, 1=Yes), Bit 1: Off-Book (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes), Bit 6: Negotiated Trades (0=No, 1=Yes)
    uint8_t subBook;

    /// 0=Order Book, 1=Symbol Status, 2=Instrument, 3=Trades, 4=Statistics, 5=News, 8=Top of Book
    uint8_t snapshotType;

    /// Sending time of the last processed trade (EPOCH time in seconds). This field is ignored if the Snapshot Type is not Trades (3) or News (5)
    uint64_t recoverFromTime;

    /// Optional identifier of the request
    uint32_t requestId;

    static void parse(MitchSnapshotRequest& msg, const uint8_t* data) {
        size_t offset = 0;
        msg.sequenceNumber = a5x_read_le64(data + offset);
        offset += 8;
        msg.segment = std::string(reinterpret_cast<const char*>(data + offset), 10);
        msg.segment.erase(msg.segment.find_last_not_of(' ') + 1);
        offset += 10;
        msg.instrumentId = a5x_read_le32(data + offset);
        offset += 4;
        msg.subBook = data[offset++];
        msg.snapshotType = data[offset++];
        msg.recoverFromTime = a5x_read_le64(data + offset);
        offset += 8;
        msg.requestId = a5x_read_le32(data + offset);
        offset += 4;
    }

    static void encode(const MitchSnapshotRequest& msg, uint8_t* data) {
        size_t offset = 0;
        a5x_write_le64(data + offset, msg.sequenceNumber);
        offset += 8;
        std::memset(data + offset, ' ', 10);
        std::memcpy(data + offset, msg.segment.c_str(), std::min(msg.segment.size(), size_t(10)));
        offset += 10;
        a5x_write_le32(data + offset, msg.instrumentId);
        offset += 4;
        data[offset++] = msg.subBook;
        data[offset++] = msg.snapshotType;
        a5x_write_le64(data + offset, msg.recoverFromTime);
        offset += 8;
        a5x_write_le32(data + offset, msg.requestId);
        offset += 4;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHSNAPSHOTREQUEST_H
