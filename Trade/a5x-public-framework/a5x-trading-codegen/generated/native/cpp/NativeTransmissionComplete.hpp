#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Transmission complete
 * Message Type: P
 */
struct NativeTransmissionComplete {
    /** 0 = All Messages Transmitted, 1 = Message Limit Reached, 3 = Service Unavailable */
    uint8_t status;

    static void parse(NativeTransmissionComplete& msg, const uint8_t* data) {
        msg.status = data[0];
    }

    static void encode(const NativeTransmissionComplete& msg, uint8_t* data) {
        data[0] = msg.status;
    }
};

} // namespace a5x::trading::native
