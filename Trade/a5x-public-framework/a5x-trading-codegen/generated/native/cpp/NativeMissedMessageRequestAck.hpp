#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Missed message request acknowledgement
 * Message Type: N
 */
struct NativeMissedMessageRequestAck {
    /** 0 = Request Accepted, 1 = Request Limit Reached, 2 = Invalid Partition ID, 3 = Service Unavailable */
    uint8_t status;

    static void parse(NativeMissedMessageRequestAck& msg, const uint8_t* data) {
        msg.status = data[0];
    }

    static void encode(const NativeMissedMessageRequestAck& msg, uint8_t* data) {
        data[0] = msg.status;
    }
};

} // namespace a5x::trading::native
