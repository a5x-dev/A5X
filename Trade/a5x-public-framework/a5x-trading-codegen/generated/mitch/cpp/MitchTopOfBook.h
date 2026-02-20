#ifndef MITCHTOPOFBOOK_H
#define MITCHTOPOFBOOK_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent to provide top of book information
struct MitchTopOfBook {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Numeric Identifier of the instrument
    uint32_t instrumentId;

    /// Bit 0: Regular (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes)
    void* subBook;

    /// 1 = Update, 2 = Delete
    uint8_t action;

    /// B = Buy, S = Sell
    uint8_t side;

    /// Best price for the particular side
    int64_t price;

    double priceAsDecimal() const { return price / 10000.0; }

    /// Cumulative visible size at best price
    uint32_t quantity;

    /// Cumulative visible size of market orders
    uint32_t marketOrderQuantity;

    /// Reserved for future use
    uint8_t reservedField[2];

    /// Cumulative visible orders at Best price. This will be set to zero on a delete action
    uint32_t splits;

    static void parse(MitchTopOfBook& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.instrumentId, data + offset, 4);
        offset += 4;
        msg.action = data[offset++];
        msg.side = data[offset++];
        std::memcpy(&msg.price, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.quantity, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.marketOrderQuantity, data + offset, 4);
        offset += 4;
        std::memcpy(msg.reservedField, data + offset, 2);
        offset += 2;
        std::memcpy(&msg.splits, data + offset, 4);
        offset += 4;
    }

    static void encode(const MitchTopOfBook& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.instrumentId, 4);
        offset += 4;
        data[offset++] = msg.action;
        data[offset++] = msg.side;
        std::memcpy(data + offset, &msg.price, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.quantity, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.marketOrderQuantity, 4);
        offset += 4;
        std::memcpy(data + offset, msg.reservedField, 2);
        offset += 2;
        std::memcpy(data + offset, &msg.splits, 4);
        offset += 4;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHTOPOFBOOK_H
