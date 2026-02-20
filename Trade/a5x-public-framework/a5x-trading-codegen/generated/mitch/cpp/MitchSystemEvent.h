#ifndef MITCHSYSTEMEVENT_H
#define MITCHSYSTEMEVENT_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent to indicate the start and end of the day
struct MitchSystemEvent {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// C = End of Day, O = Start of Day
    uint8_t eventCode;

    static void parse(MitchSystemEvent& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        msg.eventCode = data[offset++];
    }

    static void encode(const MitchSystemEvent& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        data[offset++] = msg.eventCode;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHSYSTEMEVENT_H
