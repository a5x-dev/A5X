#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Logon response
 * Message Type: B
 */
struct NativeLogonResponse {
    /** 0 or 3 = Accepted, 6 = Firm suspended, 9 = Account expired, 50 = Staggered Start, 55 = Node suspended, 100 = Not logged into real-time, 626 = User suspended, 628 = User inactive, 630 = End of Day, 9903 = Concurrent limit, 9904 = Invalid gateway, 9905 = System unavailable, 9906 = Logons not allowed */
    int32_t rejectCode;

    /** Number of days for password expiry, ignore if negative */
    int32_t passwordExpiry;

    static void parse(NativeLogonResponse& msg, const uint8_t* data) {
        std::memcpy(&msg.rejectCode, data + 0, 4);
        std::memcpy(&msg.passwordExpiry, data + 4, 4);
    }

    static void encode(const NativeLogonResponse& msg, uint8_t* data) {
        std::memcpy(data + 0, &msg.rejectCode, 4);
        std::memcpy(data + 4, &msg.passwordExpiry, 4);
    }
};

} // namespace a5x::trading::native
