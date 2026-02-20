#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Heartbeat message
 * Message Type: 0
 */
struct NativeHeartbeat {
    static void parse(NativeHeartbeat& msg, const uint8_t* data) {
    }

    static void encode(const NativeHeartbeat& msg, uint8_t* data) {
    }
};

} // namespace a5x::trading::native
