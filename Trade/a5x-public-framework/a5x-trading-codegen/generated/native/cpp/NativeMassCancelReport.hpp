#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Mass Cancel Report
 * Message Type: r
 */
struct NativeMassCancelReport {
    /** Identity of the matching partition */
    uint8_t partitionId;

    /** Message sequence number of the matching partition */
    uint64_t sequenceNumber;

    /** Client specified identifier of the rejected message if available */
    uint8_t clientOrderId[10];

    /** 0 = Rejected, 7 = Accepted */
    uint8_t status;

    /** Code specifying the reason for the reject. Ignore if Status is not Rejected (0) */
    uint32_t rejectCode;

    /** 1 = Regular */
    uint8_t orderBook;

    static void parse(NativeMassCancelReport& msg, const uint8_t* data) {
        msg.partitionId = data[0];
        std::memcpy(&msg.sequenceNumber, data + 1, 8);
        std::memcpy(msg.clientOrderId, data + 9, 10);
        msg.status = data[19];
        std::memcpy(&msg.rejectCode, data + 20, 4);
        msg.orderBook = data[24];
    }

    static void encode(const NativeMassCancelReport& msg, uint8_t* data) {
        data[0] = msg.partitionId;
        std::memcpy(data + 1, &msg.sequenceNumber, 8);
        std::memcpy(data + 9, msg.clientOrderId, 10);
        data[19] = msg.status;
        std::memcpy(data + 20, &msg.rejectCode, 4);
        data[24] = msg.orderBook;
    }
};

} // namespace a5x::trading::native
