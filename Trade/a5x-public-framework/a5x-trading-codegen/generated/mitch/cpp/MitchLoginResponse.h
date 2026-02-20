#ifndef MITCHLOGINRESPONSE_H
#define MITCHLOGINRESPONSE_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent by server in response to login request
struct MitchLoginResponse {
    /// A = Login Accepted, a = CompID Inactive/Locked, b = Login Limit Reached, c = Service Unavailable, d = Concurrent Limit Reached, e = Failed (Other)
    uint8_t status;

    static void parse(MitchLoginResponse& msg, const uint8_t* data) {
        size_t offset = 0;
        msg.status = data[offset++];
    }

    static void encode(const MitchLoginResponse& msg, uint8_t* data) {
        size_t offset = 0;
        data[offset++] = msg.status;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHLOGINRESPONSE_H
