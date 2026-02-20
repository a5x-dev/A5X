#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Logout message
 * Message Type: 5
 */
struct NativeLogout {
    /** Reason for the logout */
    uint8_t reason[20];

    static void parse(NativeLogout& msg, const uint8_t* data) {
        std::memcpy(msg.reason, data + 0, 20);
    }

    static void encode(const NativeLogout& msg, uint8_t* data) {
        std::memcpy(data + 0, msg.reason, 20);
    }
};

} // namespace a5x::trading::native
