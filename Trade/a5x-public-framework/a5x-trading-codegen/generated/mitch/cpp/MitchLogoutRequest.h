#ifndef MITCHLOGOUTREQUEST_H
#define MITCHLOGOUTREQUEST_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent by client to logout
struct MitchLogoutRequest {
    static void parse(MitchLogoutRequest& msg, const uint8_t* data) {
        size_t offset = 0;
    }

    static void encode(const MitchLogoutRequest& msg, uint8_t* data) {
        size_t offset = 0;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHLOGOUTREQUEST_H
