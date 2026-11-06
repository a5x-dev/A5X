#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * News
 * Message Type: Z
 */
struct NativeNews {
    /** Identity of the matching partition */
    uint8_t partitionId;

    /** Message sequence number of the matching partition */
    uint64_t sequenceNumber;

    /** Time the announcement was published in EPOCH time */
    uint64_t origTime;

    /** 0 = Rejected, 1 = High Priority, 2 = Low Priority */
    uint8_t urgency;

    /** Headline or subject of the announcement */
    std::string headline;

    /** Text of the announcement */
    std::string text;

    /** Pipe separated list of symbols of instruments the announcements relates to */
    std::string instruments;

    /** Pipe separated list of symbols of underlyings the instruments relates to */
    std::string underlyings;

    /** Pipe separated list of firms that the announcement should be sent to */
    std::string firmList;

    /** Pipe separated list of users that the announcement should be sent to */
    std::string userList;

    static void parse(NativeNews& msg, const uint8_t* data) {
        msg.partitionId = data[0];
        std::memcpy(&msg.sequenceNumber, data + 1, 8);
        std::memcpy(&msg.origTime, data + 9, 8);
        msg.urgency = data[17];
        msg.headline.assign(reinterpret_cast<const char*>(data + 18), strnlen(reinterpret_cast<const char*>(data + 18), 100));
        msg.text.assign(reinterpret_cast<const char*>(data + 118), strnlen(reinterpret_cast<const char*>(data + 118), 750));
        msg.instruments.assign(reinterpret_cast<const char*>(data + 868), strnlen(reinterpret_cast<const char*>(data + 868), 100));
        msg.underlyings.assign(reinterpret_cast<const char*>(data + 968), strnlen(reinterpret_cast<const char*>(data + 968), 100));
        msg.firmList.assign(reinterpret_cast<const char*>(data + 1068), strnlen(reinterpret_cast<const char*>(data + 1068), 54));
        msg.userList.assign(reinterpret_cast<const char*>(data + 1122), strnlen(reinterpret_cast<const char*>(data + 1122), 54));
    }

    static void encode(const NativeNews& msg, uint8_t* data) {
        data[0] = msg.partitionId;
        std::memcpy(data + 1, &msg.sequenceNumber, 8);
        std::memcpy(data + 9, &msg.origTime, 8);
        data[17] = msg.urgency;
        std::memset(data + 18, 0, 100); std::memcpy(data + 18, msg.headline.c_str(), std::min(msg.headline.size(), size_t(100)));
        std::memset(data + 118, 0, 750); std::memcpy(data + 118, msg.text.c_str(), std::min(msg.text.size(), size_t(750)));
        std::memset(data + 868, 0, 100); std::memcpy(data + 868, msg.instruments.c_str(), std::min(msg.instruments.size(), size_t(100)));
        std::memset(data + 968, 0, 100); std::memcpy(data + 968, msg.underlyings.c_str(), std::min(msg.underlyings.size(), size_t(100)));
        std::memset(data + 1068, 0, 54); std::memcpy(data + 1068, msg.firmList.c_str(), std::min(msg.firmList.size(), size_t(54)));
        std::memset(data + 1122, 0, 54); std::memcpy(data + 1122, msg.userList.c_str(), std::min(msg.userList.size(), size_t(54)));
    }
};

} // namespace a5x::trading::native
