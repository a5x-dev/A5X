#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * MMP Reset Acknowledgement. Response to an MMP Reset Request
 * Message Type: ~
 */
struct NativeMMPResetAcknowledgement {
    /** Client specified identifier of the originating request */
    int32_t requestId;

    /** Identifier of the segment */
    std::string segment;

    /** Identity of the matching partition */
    uint8_t partitionId;

    /** Entering Trader ID */
    std::string enteringTrader;

    /** Desk ID through which the request is placed from */
    std::string deskId;

    /** Sender Location assigned to the Trader ID/Firm */
    std::string senderLocation;

    /** Free format text field. Echoed in reports */
    std::string memo;

    /** Outcome of the reset request */
    uint8_t status;

    /** Code specifying the reason for the reject. Applicable only if the request is rejected, null filled otherwise */
    int32_t rejectCode;

    static void parse(NativeMMPResetAcknowledgement& msg, const uint8_t* data) {
        std::memcpy(&msg.requestId, data + 0, 4);
        msg.segment.assign(reinterpret_cast<const char*>(data + 4), strnlen(reinterpret_cast<const char*>(data + 4), 10));
        msg.partitionId = data[14];
        msg.enteringTrader.assign(reinterpret_cast<const char*>(data + 15), strnlen(reinterpret_cast<const char*>(data + 15), 15));
        msg.deskId.assign(reinterpret_cast<const char*>(data + 30), strnlen(reinterpret_cast<const char*>(data + 30), 15));
        msg.senderLocation.assign(reinterpret_cast<const char*>(data + 45), strnlen(reinterpret_cast<const char*>(data + 45), 10));
        msg.memo.assign(reinterpret_cast<const char*>(data + 55), strnlen(reinterpret_cast<const char*>(data + 55), 15));
        msg.status = data[70];
        std::memcpy(&msg.rejectCode, data + 71, 4);
    }

    static void encode(const NativeMMPResetAcknowledgement& msg, uint8_t* data) {
        std::memcpy(data + 0, &msg.requestId, 4);
        std::memset(data + 4, 0, 10); std::memcpy(data + 4, msg.segment.c_str(), std::min(msg.segment.size(), size_t(10)));
        data[14] = msg.partitionId;
        std::memset(data + 15, 0, 15); std::memcpy(data + 15, msg.enteringTrader.c_str(), std::min(msg.enteringTrader.size(), size_t(15)));
        std::memset(data + 30, 0, 15); std::memcpy(data + 30, msg.deskId.c_str(), std::min(msg.deskId.size(), size_t(15)));
        std::memset(data + 45, 0, 10); std::memcpy(data + 45, msg.senderLocation.c_str(), std::min(msg.senderLocation.size(), size_t(10)));
        std::memset(data + 55, 0, 15); std::memcpy(data + 55, msg.memo.c_str(), std::min(msg.memo.size(), size_t(15)));
        data[70] = msg.status;
        std::memcpy(data + 71, &msg.rejectCode, 4);
    }
};

} // namespace a5x::trading::native
