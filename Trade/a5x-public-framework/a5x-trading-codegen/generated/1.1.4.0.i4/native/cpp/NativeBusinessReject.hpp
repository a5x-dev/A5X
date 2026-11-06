#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

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
    std::string clientOrderId;

    static void parse(NativeBusinessReject& msg, const uint8_t* data) {
        msg.partitionId = data[0];
        std::memcpy(&msg.sequenceNumber, data + 1, 8);
        std::memcpy(&msg.rejectCode, data + 9, 4);
        msg.clientOrderId.assign(reinterpret_cast<const char*>(data + 13), strnlen(reinterpret_cast<const char*>(data + 13), 10));
    }

    static void encode(const NativeBusinessReject& msg, uint8_t* data) {
        data[0] = msg.partitionId;
        std::memcpy(data + 1, &msg.sequenceNumber, 8);
        std::memcpy(data + 9, &msg.rejectCode, 4);
        std::memset(data + 13, 0, 10); std::memcpy(data + 13, msg.clientOrderId.c_str(), std::min(msg.clientOrderId.size(), size_t(10)));
    }
};

} // namespace a5x::trading::native
