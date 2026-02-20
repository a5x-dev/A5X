#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Reject message
 * Message Type: 3
 */
struct NativeReject {
    /** Code specifying the reason for the reject */
    int32_t rejectCode;

    /** Reason for the reject */
    uint8_t rejectReason[30];

    /** Type of message rejected */
    uint8_t refMessageType;

    /** Client specified identifier of the rejected message if available */
    uint8_t clientOrderId[10];

    static void parse(NativeReject& msg, const uint8_t* data) {
        std::memcpy(&msg.rejectCode, data + 0, 4);
        std::memcpy(msg.rejectReason, data + 4, 30);
        msg.refMessageType = data[34];
        std::memcpy(msg.clientOrderId, data + 35, 10);
    }

    static void encode(const NativeReject& msg, uint8_t* data) {
        std::memcpy(data + 0, &msg.rejectCode, 4);
        std::memcpy(data + 4, msg.rejectReason, 30);
        data[34] = msg.refMessageType;
        std::memcpy(data + 35, msg.clientOrderId, 10);
    }
};

} // namespace a5x::trading::native
