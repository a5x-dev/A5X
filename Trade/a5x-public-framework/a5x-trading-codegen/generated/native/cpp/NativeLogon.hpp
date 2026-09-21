#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * Logon request
 * Message Type: A
 */
struct NativeLogon {
    /** CompID assigned to the client */
    std::string compId;

    /** Password assigned to the CompID */
    std::string password;

    /** New password for CompID */
    std::string newPassword;

    /** Specifies the version of protocol. The system will only accept values from 1-9. If left empty, the system will consider the default value defined in system */
    int32_t protocolVersion;

    /** Original IP Address the user */
    std::string ipAddress;

    /** Nome da solução certificada */
    std::string certifiedSolution;

    static void parse(NativeLogon& msg, const uint8_t* data) {
        msg.compId.assign(reinterpret_cast<const char*>(data + 0), strnlen(reinterpret_cast<const char*>(data + 0), 11));
        msg.password.assign(reinterpret_cast<const char*>(data + 11), strnlen(reinterpret_cast<const char*>(data + 11), 25));
        msg.newPassword.assign(reinterpret_cast<const char*>(data + 36), strnlen(reinterpret_cast<const char*>(data + 36), 25));
        std::memcpy(&msg.protocolVersion, data + 61, 4);
        msg.ipAddress.assign(reinterpret_cast<const char*>(data + 65), strnlen(reinterpret_cast<const char*>(data + 65), 15));
        msg.certifiedSolution.assign(reinterpret_cast<const char*>(data + 80), strnlen(reinterpret_cast<const char*>(data + 80), 44));
    }

    static void encode(const NativeLogon& msg, uint8_t* data) {
        std::memset(data + 0, 0, 11); std::memcpy(data + 0, msg.compId.c_str(), std::min(msg.compId.size(), size_t(11)));
        std::memset(data + 11, 0, 25); std::memcpy(data + 11, msg.password.c_str(), std::min(msg.password.size(), size_t(25)));
        std::memset(data + 36, 0, 25); std::memcpy(data + 36, msg.newPassword.c_str(), std::min(msg.newPassword.size(), size_t(25)));
        std::memcpy(data + 61, &msg.protocolVersion, 4);
        std::memset(data + 65, 0, 15); std::memcpy(data + 65, msg.ipAddress.c_str(), std::min(msg.ipAddress.size(), size_t(15)));
        std::memset(data + 80, 0, 44); std::memcpy(data + 80, msg.certifiedSolution.c_str(), std::min(msg.certifiedSolution.size(), size_t(44)));
    }
};

} // namespace a5x::trading::native
