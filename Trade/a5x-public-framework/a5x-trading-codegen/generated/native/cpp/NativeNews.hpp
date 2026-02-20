#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * News
 * Message Type: CB
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
    uint8_t headline[100];

    /** Text of the announcement */
    uint8_t text[750];

    /** Pipe separated list of symbols of instruments the announcements relates to */
    uint8_t instruments[100];

    /** Pipe separated list of symbols of underlyings the instruments relates to */
    uint8_t underlyings[100];

    /** Pipe separated list of firms that the announcement should be sent to */
    uint8_t firmList[54];

    /** Pipe separated list of users that the announcement should be sent to */
    uint8_t userList[54];

    static void parse(NativeNews& msg, const uint8_t* data) {
        msg.partitionId = data[0];
        std::memcpy(&msg.sequenceNumber, data + 1, 8);
        std::memcpy(&msg.origTime, data + 9, 8);
        msg.urgency = data[17];
        std::memcpy(msg.headline, data + 18, 100);
        std::memcpy(msg.text, data + 118, 750);
        std::memcpy(msg.instruments, data + 868, 100);
        std::memcpy(msg.underlyings, data + 968, 100);
        std::memcpy(msg.firmList, data + 1068, 54);
        std::memcpy(msg.userList, data + 1122, 54);
    }

    static void encode(const NativeNews& msg, uint8_t* data) {
        data[0] = msg.partitionId;
        std::memcpy(data + 1, &msg.sequenceNumber, 8);
        std::memcpy(data + 9, &msg.origTime, 8);
        data[17] = msg.urgency;
        std::memcpy(data + 18, msg.headline, 100);
        std::memcpy(data + 118, msg.text, 750);
        std::memcpy(data + 868, msg.instruments, 100);
        std::memcpy(data + 968, msg.underlyings, 100);
        std::memcpy(data + 1068, msg.firmList, 54);
        std::memcpy(data + 1122, msg.userList, 54);
    }
};

} // namespace a5x::trading::native
