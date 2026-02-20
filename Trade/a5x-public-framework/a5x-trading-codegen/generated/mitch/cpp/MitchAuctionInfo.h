#ifndef MITCHAUCTIONINFO_H
#define MITCHAUCTIONINFO_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent to provide indicative auction information during auction periods
struct MitchAuctionInfo {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Quantity that will be matched at the indicative price
    uint32_t pairedQuantity;

    /// Quantity that is eligible to be matched at the indicative price but will not be matched
    uint32_t imbalanceQuantity;

    /// B = Buy Imbalance, N = No Imbalance, O = Insufficient Orders for Auction, S = Sell Imbalance
    uint8_t imbalanceDirection;

    /// Numeric identifier of the instrument
    uint32_t instrumentId;

    /// Indicative auction price
    int64_t price;

    double priceAsDecimal() const { return price / 10000.0; }

    /// A = Re-Opening Auction, C = Closing Auction, O = Opening Auction
    uint8_t auctionType;

    static void parse(MitchAuctionInfo& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.pairedQuantity, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.imbalanceQuantity, data + offset, 4);
        offset += 4;
        msg.imbalanceDirection = data[offset++];
        std::memcpy(&msg.instrumentId, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.price, data + offset, 8);
        offset += 8;
        msg.auctionType = data[offset++];
    }

    static void encode(const MitchAuctionInfo& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.pairedQuantity, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.imbalanceQuantity, 4);
        offset += 4;
        data[offset++] = msg.imbalanceDirection;
        std::memcpy(data + offset, &msg.instrumentId, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.price, 8);
        offset += 8;
        data[offset++] = msg.auctionType;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHAUCTIONINFO_H
