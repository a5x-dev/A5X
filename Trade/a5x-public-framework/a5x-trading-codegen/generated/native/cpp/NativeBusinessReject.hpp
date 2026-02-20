#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Business Reject
 * Message Type: j
 */
struct NativeBusinessReject {
    /** Identity of the matching partition */
    uint8_t partitionId;

    /** Message sequence number of the matching partition */
    uint64_t sequenceNumber;

    /** Code specifying the reason for the reject. 9000 = Unknown instrument, 9998 = Matching partition suspended, 9999 = System suspended */
    uint32_t rejectCode;

    /** Client specified identifier of the rejected message if available */
    uint8_t clientOrderId[10];

    static void parse(NativeBusinessReject& msg, const uint8_t* data) {
        msg.partitionId = data[0];
        std::memcpy(&msg.sequenceNumber, data + 1, 8);
        std::memcpy(&msg.rejectCode, data + 9, 4);
        std::memcpy(msg.clientOrderId, data + 13, 10);
    }

    static void encode(const NativeBusinessReject& msg, uint8_t* data) {
        data[0] = msg.partitionId;
        std::memcpy(data + 1, &msg.sequenceNumber, 8);
        std::memcpy(data + 9, &msg.rejectCode, 4);
        std::memcpy(data + 13, msg.clientOrderId, 10);
    }
};

} // namespace a5x::trading::native
