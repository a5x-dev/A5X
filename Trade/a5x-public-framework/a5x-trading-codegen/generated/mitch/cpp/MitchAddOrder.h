#ifndef MITCHADDORDER_H
#define MITCHADDORDER_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent when a new visible order is added to the order book
struct MitchAddOrder {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Unique identifier of the order
    uint64_t orderId;

    /// B = Buy, S = Sell
    uint8_t side;

    /// Displayed quantity of the order
    uint32_t quantity;

    /// Numeric identifier of the instrument
    uint32_t instrumentId;

    /// Limit price of the order. If Market, it will be filled with spaces
    int64_t price;

    double priceAsDecimal() const { return price / 10000.0; }

    /// Bit 4: Market Order (0 = No, 1 = Yes), Bit 5: Bulletin Board (0 = No, 1 = Yes)
    void* flags;

    static void parse(MitchAddOrder& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.orderId, data + offset, 8);
        offset += 8;
        msg.side = data[offset++];
        std::memcpy(&msg.quantity, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.instrumentId, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.price, data + offset, 8);
        offset += 8;
    }

    static void encode(const MitchAddOrder& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.orderId, 8);
        offset += 8;
        data[offset++] = msg.side;
        std::memcpy(data + offset, &msg.quantity, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.instrumentId, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.price, 8);
        offset += 8;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHADDORDER_H
