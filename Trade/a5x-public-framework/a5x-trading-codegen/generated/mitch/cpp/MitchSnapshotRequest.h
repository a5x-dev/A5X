#ifndef MITCHSNAPSHOTREQUEST_H
#define MITCHSNAPSHOTREQUEST_H

#include <cstdint>
#include <cstring>
#include <string>

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
    void* subBook;

    /// 0=Order Book, 1=Symbol Status, 2=Instrument, 3=Trades, 4=Statistics, 5=News, 8=Top of Book
    uint8_t snapshotType;

    /// Sending time of the last processed trade (EPOCH time in seconds). This field is ignored if the Snapshot Type is not Trades (3) or News (5)
    uint64_t recoverFromTime;

    /// Optional identifier of the request
    uint32_t requestId;

    static void parse(MitchSnapshotRequest& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.sequenceNumber, data + offset, 8);
        offset += 8;
        msg.segment = std::string(reinterpret_cast<const char*>(data + offset), 10);
        msg.segment.erase(msg.segment.find_last_not_of(' ') + 1);
        offset += 10;
        std::memcpy(&msg.instrumentId, data + offset, 4);
        offset += 4;
        msg.snapshotType = data[offset++];
        std::memcpy(&msg.recoverFromTime, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.requestId, data + offset, 4);
        offset += 4;
    }

    static void encode(const MitchSnapshotRequest& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.sequenceNumber, 8);
        offset += 8;
        std::memset(data + offset, ' ', 10);
        std::memcpy(data + offset, msg.segment.c_str(), std::min(msg.segment.size(), size_t(10)));
        offset += 10;
        std::memcpy(data + offset, &msg.instrumentId, 4);
        offset += 4;
        data[offset++] = msg.snapshotType;
        std::memcpy(data + offset, &msg.recoverFromTime, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.requestId, 4);
        offset += 4;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHSNAPSHOTREQUEST_H
