#ifndef MITCHREPLAYRESPONSE_H
#define MITCHREPLAYRESPONSE_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent by server in response to replay request
struct MitchReplayResponse {
    /// Identity of the market data group the replay request relates to
    uint8_t marketDataGroup;

    /// Sequence number of the first message in range to be retransmitted. This will be zero if Status is not 'A'
    uint64_t firstMessage;

    /// Number of messages to be resent. This will be zero if Status is not 'A'
    void* count;

    /// A = Request Accepted, D = Request Limit Reached, I = Invalid Market Data Group, O = Out of Range, U = Replay Unavailable, c = Concurrent Limit Reached, d = Unsupported Message Type, e = Failed (Other)
    uint8_t status;

    static void parse(MitchReplayResponse& msg, const uint8_t* data) {
        size_t offset = 0;
        msg.marketDataGroup = data[offset++];
        std::memcpy(&msg.firstMessage, data + offset, 8);
        offset += 8;
        msg.status = data[offset++];
    }

    static void encode(const MitchReplayResponse& msg, uint8_t* data) {
        size_t offset = 0;
        data[offset++] = msg.marketDataGroup;
        std::memcpy(data + offset, &msg.firstMessage, 8);
        offset += 8;
        data[offset++] = msg.status;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHREPLAYRESPONSE_H
