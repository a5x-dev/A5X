#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Missed message request
 * Message Type: M
 */
struct NativeMissedMessageRequest {
    /** Identity of the matching partition the request relates to */
    uint8_t partitionId;

    /** Sequence number immediately after that of the last message received from the partition */
    uint64_t sequenceNumber;

    static void parse(NativeMissedMessageRequest& msg, const uint8_t* data) {
        msg.partitionId = data[0];
        std::memcpy(&msg.sequenceNumber, data + 1, 8);
    }

    static void encode(const NativeMissedMessageRequest& msg, uint8_t* data) {
        data[0] = msg.partitionId;
        std::memcpy(data + 1, &msg.sequenceNumber, 8);
    }
};

} // namespace a5x::trading::native
