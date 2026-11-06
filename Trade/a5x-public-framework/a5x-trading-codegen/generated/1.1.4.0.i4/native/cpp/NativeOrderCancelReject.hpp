#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * Order Cancel Reject
 * Message Type: 9
 */
struct NativeOrderCancelReject {
    /** Identity of the matching partition */
    uint8_t partitionId;

    /** Message sequence number of the matching partition */
    uint64_t sequenceNumber;

    /** Client specified identifier of the rejected cancel or modification request */
    std::string clientOrderId;

    /** Code specifying the reason for the reject */
    int32_t rejectCode;

    /** 1 = Regular */
    uint8_t orderBook;

    /** Time the message was generated in epoch format */
    uint64_t transactTime;

    /** Sender Location assigned to the Trader ID/Firm */
    std::string senderLocation;

    static void parse(NativeOrderCancelReject& msg, const uint8_t* data) {
        msg.partitionId = data[0];
        std::memcpy(&msg.sequenceNumber, data + 1, 8);
        msg.clientOrderId.assign(reinterpret_cast<const char*>(data + 9), strnlen(reinterpret_cast<const char*>(data + 9), 10));
        std::memcpy(&msg.rejectCode, data + 19, 4);
        msg.orderBook = data[23];
        std::memcpy(&msg.transactTime, data + 24, 8);
        msg.senderLocation.assign(reinterpret_cast<const char*>(data + 32), strnlen(reinterpret_cast<const char*>(data + 32), 10));
    }

    static void encode(const NativeOrderCancelReject& msg, uint8_t* data) {
        data[0] = msg.partitionId;
        std::memcpy(data + 1, &msg.sequenceNumber, 8);
        std::memset(data + 9, 0, 10); std::memcpy(data + 9, msg.clientOrderId.c_str(), std::min(msg.clientOrderId.size(), size_t(10)));
        std::memcpy(data + 19, &msg.rejectCode, 4);
        data[23] = msg.orderBook;
        std::memcpy(data + 24, &msg.transactTime, 8);
        std::memset(data + 32, 0, 10); std::memcpy(data + 32, msg.senderLocation.c_str(), std::min(msg.senderLocation.size(), size_t(10)));
    }
};

} // namespace a5x::trading::native
