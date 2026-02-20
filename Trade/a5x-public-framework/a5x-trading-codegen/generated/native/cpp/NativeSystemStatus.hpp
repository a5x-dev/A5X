#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * System status
 * Message Type: n
 */
struct NativeSystemStatus {
    /** Identity of the matching partition the message relates to */
    uint8_t partitionId;

    /** 1 = Recovery Service Resumed, 2 = Recovery Service Unavailable, 3 = Partition Suspended */
    uint8_t status;

    static void parse(NativeSystemStatus& msg, const uint8_t* data) {
        msg.partitionId = data[0];
        msg.status = data[1];
    }

    static void encode(const NativeSystemStatus& msg, uint8_t* data) {
        data[0] = msg.partitionId;
        data[1] = msg.status;
    }
};

} // namespace a5x::trading::native
