#ifndef MITCHDELETEPRICEPOINT_H
#define MITCHDELETEPRICEPOINT_H

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent when a price level is removed from an aggregated (price point) order book
struct MitchDeletePricePoint {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Numeric Identifier of the instrument
    uint32_t instrumentId;

    /// Bit 0: Regular (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes)
    uint8_t subBook;

    /// B = Buy, S = Sell
    uint8_t side;

    /// Position of the price level that is removed, counting from the best price
    uint16_t priceLevel;

    /// Reserved for future use
    uint8_t reservedField[1];

    /// Identifier of the RFQ the price level belongs to. Null filled when the level is not RFQ driven
    std::string rfqId;

    static void parse(MitchDeletePricePoint& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.instrumentId, data + offset, 4);
        offset += 4;
        msg.subBook = data[offset++];
        msg.side = data[offset++];
        std::memcpy(&msg.priceLevel, data + offset, 2);
        offset += 2;
        std::memcpy(msg.reservedField, data + offset, 1);
        offset += 1;
        msg.rfqId = std::string(reinterpret_cast<const char*>(data + offset), 10);
        msg.rfqId.erase(msg.rfqId.find_last_not_of(' ') + 1);
        offset += 10;
    }

    static void encode(const MitchDeletePricePoint& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.instrumentId, 4);
        offset += 4;
        data[offset++] = msg.subBook;
        data[offset++] = msg.side;
        std::memcpy(data + offset, &msg.priceLevel, 2);
        offset += 2;
        std::memcpy(data + offset, msg.reservedField, 1);
        offset += 1;
        std::memset(data + offset, ' ', 10);
        std::memcpy(data + offset, msg.rfqId.c_str(), std::min(msg.rfqId.size(), size_t(10)));
        offset += 10;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHDELETEPRICEPOINT_H
