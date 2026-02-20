#ifndef MITCHSNAPSHOTRESPONSE_H
#define MITCHSNAPSHOTRESPONSE_H

#include <cstdint>
#include <cstring>
#include <string>

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
        std::memcpy(&msg.sequenceNumber, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.orderCount, data + offset, 4);
        offset += 4;
        msg.status = data[offset++];
        msg.snapshotType = data[offset++];
        std::memcpy(&msg.requestId, data + offset, 4);
        offset += 4;
    }

    static void encode(const MitchSnapshotResponse& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.sequenceNumber, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.orderCount, 4);
        offset += 4;
        data[offset++] = msg.status;
        data[offset++] = msg.snapshotType;
        std::memcpy(data + offset, &msg.requestId, 4);
        offset += 4;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHSNAPSHOTRESPONSE_H
