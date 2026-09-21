#ifndef MITCHREPLAYREQUEST_H
#define MITCHREPLAYREQUEST_H

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent by client to request retransmission of messages
struct MitchReplayRequest {
    /// Identity of the market data group the replay request relates to
    uint8_t marketDataGroup;

    /// Sequence number of the first message in range to be retransmitted
    uint64_t firstMessage;

    /// Number of messages to be resent
    uint16_t count;

    static void parse(MitchReplayRequest& msg, const uint8_t* data) {
        size_t offset = 0;
        msg.marketDataGroup = data[offset++];
        std::memcpy(&msg.firstMessage, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.count, data + offset, 2);
        offset += 2;
    }

    static void encode(const MitchReplayRequest& msg, uint8_t* data) {
        size_t offset = 0;
        data[offset++] = msg.marketDataGroup;
        std::memcpy(data + offset, &msg.firstMessage, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.count, 2);
        offset += 2;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHREPLAYREQUEST_H
