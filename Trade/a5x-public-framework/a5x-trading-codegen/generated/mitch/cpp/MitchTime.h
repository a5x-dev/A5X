#ifndef MITCHTIME_H
#define MITCHTIME_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent by the server for every second for which at least one application message is generated. This message is not transmitted during periods where no other application messages are generated
struct MitchTime {
    /// Number of seconds since midnight. Midnight will be in terms of the local time for the server (i.e. not UTC)
    uint32_t seconds;

    static void parse(MitchTime& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.seconds, data + offset, 4);
        offset += 4;
    }

    static void encode(const MitchTime& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.seconds, 4);
        offset += 4;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHTIME_H
