#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Logon request
 * Message Type: A
 */
struct NativeLogon {
    /** CompID assigned to the client */
    uint8_t compId[11];

    /** Password assigned to the CompID */
    uint8_t password[25];

    /** New password for CompID */
    uint8_t newPassword[25];

    /** Protocol version (1-9) */
    int32_t protocolVersion;

    /** Original IP Address the user */
    uint8_t ipAddress[15];

    /** Nome da solução certificada */
    uint8_t certifiedSolution[44];

    static void parse(NativeLogon& msg, const uint8_t* data) {
        std::memcpy(msg.compId, data + 0, 11);
        std::memcpy(msg.password, data + 11, 25);
        std::memcpy(msg.newPassword, data + 36, 25);
        std::memcpy(&msg.protocolVersion, data + 61, 4);
        std::memcpy(msg.ipAddress, data + 65, 15);
        std::memcpy(msg.certifiedSolution, data + 80, 44);
    }

    static void encode(const NativeLogon& msg, uint8_t* data) {
        std::memcpy(data + 0, msg.compId, 11);
        std::memcpy(data + 11, msg.password, 25);
        std::memcpy(data + 36, msg.newPassword, 25);
        std::memcpy(data + 61, &msg.protocolVersion, 4);
        std::memcpy(data + 65, msg.ipAddress, 15);
        std::memcpy(data + 80, msg.certifiedSolution, 44);
    }
};

} // namespace a5x::trading::native
