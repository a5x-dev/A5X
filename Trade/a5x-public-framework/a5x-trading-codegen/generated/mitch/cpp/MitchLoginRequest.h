#ifndef MITCHLOGINREQUEST_H
#define MITCHLOGINREQUEST_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent by client to authenticate with the server
struct MitchLoginRequest {
    /// CompID assigned to the client
    std::string username;

    /// Password assigned to the CompID
    std::string password;

    /// Original IP Address the user is running from (the server IP Address for audit purpose)
    std::string ipAddress;

    /// Identifier of the solution that has been certificated against A5X trading platform (ex.: Trading System v1.0)
    std::string certifiedSolution;

    static void parse(MitchLoginRequest& msg, const uint8_t* data) {
        size_t offset = 0;
        msg.username = std::string(reinterpret_cast<const char*>(data + offset), 6);
        msg.username.erase(msg.username.find_last_not_of(' ') + 1);
        offset += 6;
        msg.password = std::string(reinterpret_cast<const char*>(data + offset), 10);
        msg.password.erase(msg.password.find_last_not_of(' ') + 1);
        offset += 10;
        msg.ipAddress = std::string(reinterpret_cast<const char*>(data + offset), 15);
        msg.ipAddress.erase(msg.ipAddress.find_last_not_of(' ') + 1);
        offset += 15;
        msg.certifiedSolution = std::string(reinterpret_cast<const char*>(data + offset), 44);
        msg.certifiedSolution.erase(msg.certifiedSolution.find_last_not_of(' ') + 1);
        offset += 44;
    }

    static void encode(const MitchLoginRequest& msg, uint8_t* data) {
        size_t offset = 0;
        std::memset(data + offset, ' ', 6);
        std::memcpy(data + offset, msg.username.c_str(), std::min(msg.username.size(), size_t(6)));
        offset += 6;
        std::memset(data + offset, ' ', 10);
        std::memcpy(data + offset, msg.password.c_str(), std::min(msg.password.size(), size_t(10)));
        offset += 10;
        std::memset(data + offset, ' ', 15);
        std::memcpy(data + offset, msg.ipAddress.c_str(), std::min(msg.ipAddress.size(), size_t(15)));
        offset += 15;
        std::memset(data + offset, ' ', 44);
        std::memcpy(data + offset, msg.certifiedSolution.c_str(), std::min(msg.certifiedSolution.size(), size_t(44)));
        offset += 44;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHLOGINREQUEST_H
