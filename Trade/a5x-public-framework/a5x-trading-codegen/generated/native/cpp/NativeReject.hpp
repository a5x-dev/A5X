#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * Reject message
 * Message Type: 3
 */
struct NativeReject {
    /** Code specifying the reason for the reject */
    int32_t rejectCode;

    /** Reason for the reject */
    std::string rejectReason;

    /** Type of message rejected */
    uint8_t refMessageType;

    /** Client specified identifier of the rejected message if available */
    std::string clientOrderId;

    static void parse(NativeReject& msg, const uint8_t* data) {
        std::memcpy(&msg.rejectCode, data + 0, 4);
        msg.rejectReason.assign(reinterpret_cast<const char*>(data + 4), strnlen(reinterpret_cast<const char*>(data + 4), 30));
        msg.refMessageType = data[34];
        msg.clientOrderId.assign(reinterpret_cast<const char*>(data + 35), strnlen(reinterpret_cast<const char*>(data + 35), 10));
    }

    static void encode(const NativeReject& msg, uint8_t* data) {
        std::memcpy(data + 0, &msg.rejectCode, 4);
        std::memset(data + 4, 0, 30); std::memcpy(data + 4, msg.rejectReason.c_str(), std::min(msg.rejectReason.size(), size_t(30)));
        data[34] = msg.refMessageType;
        std::memset(data + 35, 0, 10); std::memcpy(data + 35, msg.clientOrderId.c_str(), std::min(msg.clientOrderId.size(), size_t(10)));
    }
};

} // namespace a5x::trading::native
