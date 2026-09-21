#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * MMP Reset Request. Sent by the client to reset Market Maker Protection
 * Message Type: }
 */
struct NativeMMPResetRequest {
    /** Client specified identifier of the request */
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

    static void parse(NativeMMPResetRequest& msg, const uint8_t* data) {
        std::memcpy(&msg.requestId, data + 0, 4);
        msg.segment.assign(reinterpret_cast<const char*>(data + 4), strnlen(reinterpret_cast<const char*>(data + 4), 10));
        msg.partitionId = data[14];
        msg.enteringTrader.assign(reinterpret_cast<const char*>(data + 15), strnlen(reinterpret_cast<const char*>(data + 15), 15));
        msg.deskId.assign(reinterpret_cast<const char*>(data + 30), strnlen(reinterpret_cast<const char*>(data + 30), 15));
        msg.senderLocation.assign(reinterpret_cast<const char*>(data + 45), strnlen(reinterpret_cast<const char*>(data + 45), 10));
        msg.memo.assign(reinterpret_cast<const char*>(data + 55), strnlen(reinterpret_cast<const char*>(data + 55), 15));
    }

    static void encode(const NativeMMPResetRequest& msg, uint8_t* data) {
        std::memcpy(data + 0, &msg.requestId, 4);
        std::memset(data + 4, 0, 10); std::memcpy(data + 4, msg.segment.c_str(), std::min(msg.segment.size(), size_t(10)));
        data[14] = msg.partitionId;
        std::memset(data + 15, 0, 15); std::memcpy(data + 15, msg.enteringTrader.c_str(), std::min(msg.enteringTrader.size(), size_t(15)));
        std::memset(data + 30, 0, 15); std::memcpy(data + 30, msg.deskId.c_str(), std::min(msg.deskId.size(), size_t(15)));
        std::memset(data + 45, 0, 10); std::memcpy(data + 45, msg.senderLocation.c_str(), std::min(msg.senderLocation.size(), size_t(10)));
        std::memset(data + 55, 0, 15); std::memcpy(data + 55, msg.memo.c_str(), std::min(msg.memo.size(), size_t(15)));
    }
};

} // namespace a5x::trading::native
