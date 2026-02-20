#ifndef MITCHNEWS_H
#define MITCHNEWS_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent to disseminate news and announcements
struct MitchNews {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Time the announcement was published (EPOCH)
    uint64_t time;

    /// 0 = Regular, 1 = High Priority, 2 = Low Priority
    uint8_t urgency;

    /// Headline or subject of announcement
    std::string headline;

    /// Text of the announcement
    std::string text;

    /// Pipe separated list of Instrument IDs
    std::string instrumentIds;

    /// Pipe separated list of Underlying Instrument IDs
    std::string underlyingInstrumentIds;

    static void parse(MitchNews& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.time, data + offset, 8);
        offset += 8;
        msg.urgency = data[offset++];
        msg.headline = std::string(reinterpret_cast<const char*>(data + offset), 100);
        msg.headline.erase(msg.headline.find_last_not_of(' ') + 1);
        offset += 100;
        msg.text = std::string(reinterpret_cast<const char*>(data + offset), 750);
        msg.text.erase(msg.text.find_last_not_of(' ') + 1);
        offset += 750;
        msg.instrumentIds = std::string(reinterpret_cast<const char*>(data + offset), 100);
        msg.instrumentIds.erase(msg.instrumentIds.find_last_not_of(' ') + 1);
        offset += 100;
        msg.underlyingInstrumentIds = std::string(reinterpret_cast<const char*>(data + offset), 100);
        msg.underlyingInstrumentIds.erase(msg.underlyingInstrumentIds.find_last_not_of(' ') + 1);
        offset += 100;
    }

    static void encode(const MitchNews& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.time, 8);
        offset += 8;
        data[offset++] = msg.urgency;
        std::memset(data + offset, ' ', 100);
        std::memcpy(data + offset, msg.headline.c_str(), std::min(msg.headline.size(), size_t(100)));
        offset += 100;
        std::memset(data + offset, ' ', 750);
        std::memcpy(data + offset, msg.text.c_str(), std::min(msg.text.size(), size_t(750)));
        offset += 750;
        std::memset(data + offset, ' ', 100);
        std::memcpy(data + offset, msg.instrumentIds.c_str(), std::min(msg.instrumentIds.size(), size_t(100)));
        offset += 100;
        std::memset(data + offset, ' ', 100);
        std::memcpy(data + offset, msg.underlyingInstrumentIds.c_str(), std::min(msg.underlyingInstrumentIds.size(), size_t(100)));
        offset += 100;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHNEWS_H
