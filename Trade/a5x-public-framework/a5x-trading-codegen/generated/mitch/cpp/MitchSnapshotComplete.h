#ifndef MITCHSNAPSHOTCOMPLETE_H
#define MITCHSNAPSHOTCOMPLETE_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent by server to indicate snapshot transmission is complete
struct MitchSnapshotComplete {
    /// Sequence number with which the snapshot is synchronized
    uint64_t sequenceNumber;

    /// Segment the snapshot relates to. The field will contain only spaces if it does not relate to a segment
    std::string segment;

    /// Instrument ID of the instrument that the snapshot relates to. The field will contain only spaces if it does not relate to an instrument
    uint32_t instrumentId;

    /// Bit 0: Regular (0=No, 1=Yes), Bit 1: Off-Book (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes), Bit 6: Negotiated Trades (0=No, 1=Yes)
    void* subBook;

    /// H=Halt, T=Regular Trading, a=Opening Auction Call, b=Post-Close, c=Market Closed, d=Closing Auction Call, e=Re-Opening Auction Call, I=Pause, n=Order Entry, u=Closing Price Cross, w=No Active Session, y=Pre-Trading, m=No Cancel Period. Only indicated if message is sent as book level complete and Snapshot Type is Order Book (0) or Statistics (4)
    uint8_t tradingStatus;

    /// 0=Order Book, 1=Symbol Status, 2=Instrument, 3=Trades, 4=Statistics, 5=News, 8=Top of Book
    uint8_t snapshotType;

    /// Identifier, if any, of Snapshot Request
    uint32_t requestId;

    static void parse(MitchSnapshotComplete& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.sequenceNumber, data + offset, 8);
        offset += 8;
        msg.segment = std::string(reinterpret_cast<const char*>(data + offset), 10);
        msg.segment.erase(msg.segment.find_last_not_of(' ') + 1);
        offset += 10;
        std::memcpy(&msg.instrumentId, data + offset, 4);
        offset += 4;
        msg.tradingStatus = data[offset++];
        msg.snapshotType = data[offset++];
        std::memcpy(&msg.requestId, data + offset, 4);
        offset += 4;
    }

    static void encode(const MitchSnapshotComplete& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.sequenceNumber, 8);
        offset += 8;
        std::memset(data + offset, ' ', 10);
        std::memcpy(data + offset, msg.segment.c_str(), std::min(msg.segment.size(), size_t(10)));
        offset += 10;
        std::memcpy(data + offset, &msg.instrumentId, 4);
        offset += 4;
        data[offset++] = msg.tradingStatus;
        data[offset++] = msg.snapshotType;
        std::memcpy(data + offset, &msg.requestId, 4);
        offset += 4;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHSNAPSHOTCOMPLETE_H
