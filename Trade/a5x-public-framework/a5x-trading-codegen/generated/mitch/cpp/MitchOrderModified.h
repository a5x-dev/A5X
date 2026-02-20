#ifndef MITCHORDERMODIFIED_H
#define MITCHORDERMODIFIED_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent when a visible order is modified in the order book
struct MitchOrderModified {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Unique identifier of the order
    uint64_t orderId;

    /// New displayed quantity of the order
    uint32_t newQuantity;

    /// New limit price of the order
    int64_t newPrice;

    double newPriceAsDecimal() const { return newPrice / 10000.0; }

    /// Displayed quantity of the order
    uint32_t oldQuantity;

    /// Limit price of the order
    int64_t oldPrice;

    double oldPriceAsDecimal() const { return oldPrice / 10000.0; }

    /// Bit 0: Priority Flag (0 = Priority Lost, 1 = Priority Retained)
    void* flags;

    static void parse(MitchOrderModified& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.orderId, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.newQuantity, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.newPrice, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.oldQuantity, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.oldPrice, data + offset, 8);
        offset += 8;
    }

    static void encode(const MitchOrderModified& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.orderId, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.newQuantity, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.newPrice, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.oldQuantity, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.oldPrice, 8);
        offset += 8;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHORDERMODIFIED_H
