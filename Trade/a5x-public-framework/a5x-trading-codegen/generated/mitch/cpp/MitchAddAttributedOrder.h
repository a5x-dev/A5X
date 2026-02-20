#ifndef MITCHADDATTRIBUTEDORDER_H
#define MITCHADDATTRIBUTEDORDER_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent when a new visible order with firm attribution is added to the order book
struct MitchAddAttributedOrder {
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

    /// Identity of firm that submitted the order
    std::string firm;

    /// Bit 4: Market Order (0 = No, 1 = Yes), Bit 5: Bulletin Board (0 = No, 1 = Yes)
    void* flags;

    static void parse(MitchAddAttributedOrder& msg, const uint8_t* data) {
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
        msg.firm = std::string(reinterpret_cast<const char*>(data + offset), 6);
        msg.firm.erase(msg.firm.find_last_not_of(' ') + 1);
        offset += 6;
    }

    static void encode(const MitchAddAttributedOrder& msg, uint8_t* data) {
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
        std::memset(data + offset, ' ', 6);
        std::memcpy(data + offset, msg.firm.c_str(), std::min(msg.firm.size(), size_t(6)));
        offset += 6;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHADDATTRIBUTEDORDER_H
