#ifndef MITCHLOGINREQUEST_H
#define MITCHLOGINREQUEST_H

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

#ifndef A5X_MITCH_WIRE_HELPERS
#define A5X_MITCH_WIRE_HELPERS
namespace {
inline uint16_t a5x_read_le16(const uint8_t* p) { uint16_t v = 0; for (int i = 1; i >= 0; --i) v = static_cast<uint16_t>((v << 8) | p[i]); return v; }
inline uint32_t a5x_read_le32(const uint8_t* p) { uint32_t v = 0; for (int i = 3; i >= 0; --i) v = (v << 8) | p[i]; return v; }
inline uint64_t a5x_read_le64(const uint8_t* p) { uint64_t v = 0; for (int i = 7; i >= 0; --i) v = (v << 8) | p[i]; return v; }
inline void a5x_write_le16(uint8_t* p, uint16_t v) { for (int i = 0; i < 2; ++i) p[i] = static_cast<uint8_t>(v >> (8 * i)); }
inline void a5x_write_le32(uint8_t* p, uint32_t v) { for (int i = 0; i < 4; ++i) p[i] = static_cast<uint8_t>(v >> (8 * i)); }
inline void a5x_write_le64(uint8_t* p, uint64_t v) { for (int i = 0; i < 8; ++i) p[i] = static_cast<uint8_t>(v >> (8 * i)); }
inline int32_t a5x_read_sm32(const uint8_t* p) { uint32_t v = a5x_read_le32(p); int32_t m = static_cast<int32_t>(v & 0x7FFFFFFFu); return (v >> 31) ? -m : m; }
inline int64_t a5x_read_sm64(const uint8_t* p) { uint64_t v = a5x_read_le64(p); int64_t m = static_cast<int64_t>(v & 0x7FFFFFFFFFFFFFFFull); return (v >> 63) ? -m : m; }
inline void a5x_write_sm32(uint8_t* p, int32_t v) { a5x_write_le32(p, v < 0 ? (static_cast<uint32_t>(-static_cast<int64_t>(v)) | 0x80000000u) : static_cast<uint32_t>(v)); }
inline void a5x_write_sm64(uint8_t* p, int64_t v) { a5x_write_le64(p, v < 0 ? ((~static_cast<uint64_t>(v) + 1) | 0x8000000000000000ull) : static_cast<uint64_t>(v)); }
} // namespace
#endif // A5X_MITCH_WIRE_HELPERS

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
