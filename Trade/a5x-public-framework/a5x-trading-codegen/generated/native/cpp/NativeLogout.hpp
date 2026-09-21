#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * Logout message
 * Message Type: 5
 */
struct NativeLogout {
    /** Reason for the logout */
    std::string reason;

    static void parse(NativeLogout& msg, const uint8_t* data) {
        msg.reason.assign(reinterpret_cast<const char*>(data + 0), strnlen(reinterpret_cast<const char*>(data + 0), 20));
    }

    static void encode(const NativeLogout& msg, uint8_t* data) {
        std::memset(data + 0, 0, 20); std::memcpy(data + 0, msg.reason.c_str(), std::min(msg.reason.size(), size_t(20)));
    }
};

} // namespace a5x::trading::native
