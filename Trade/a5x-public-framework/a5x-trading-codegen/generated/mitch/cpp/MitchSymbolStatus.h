#ifndef MITCHSYMBOLSTATUS_H
#define MITCHSYMBOLSTATUS_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent to indicate changes in trading status
struct MitchSymbolStatus {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Numeric Identifier of the instrument
    uint32_t instrumentId;

    /// H = Halt, T = Regular Trading, a = Opening Auction Call, b = Post-Close, c = Market Close, d = Closing Auction Call, e = Re-Opening Auction Call, l = Pause, w = No Active Session, x = End of Post Close, y = Pre-Trading
    uint8_t tradingStatus;

    /// Reserved for future use
    void* flags;

    /// Reason for the trading halt. This field will contain only spaces if Trading Status is not 'H'
    std::string haltReason;

    /// 0 = Scheduled Transition, 1 = Extended by Market Ops, 2 = Shortened by Market Ops, 3 = Market Order Extension, 4 = Price Monitoring Extension, 5 = Circuit Breaker Tripped, 9 = Unavailable, 10 = No Cancel Period Start
    uint8_t sessionChangeReason;

    /// New session end time (EPOCH). The field will contain only spaces if Session Change Reason is '0' or '9'
    uint64_t newEndTime;

    /// 1 = Regular, 2 = Off-Book, 9 = Bulletin Board, 11 = Negotiated Trades
    uint8_t subBook;

    static void parse(MitchSymbolStatus& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.instrumentId, data + offset, 4);
        offset += 4;
        msg.tradingStatus = data[offset++];
        msg.haltReason = std::string(reinterpret_cast<const char*>(data + offset), 4);
        msg.haltReason.erase(msg.haltReason.find_last_not_of(' ') + 1);
        offset += 4;
        msg.sessionChangeReason = data[offset++];
        std::memcpy(&msg.newEndTime, data + offset, 8);
        offset += 8;
        msg.subBook = data[offset++];
    }

    static void encode(const MitchSymbolStatus& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.instrumentId, 4);
        offset += 4;
        data[offset++] = msg.tradingStatus;
        std::memset(data + offset, ' ', 4);
        std::memcpy(data + offset, msg.haltReason.c_str(), std::min(msg.haltReason.size(), size_t(4)));
        offset += 4;
        data[offset++] = msg.sessionChangeReason;
        std::memcpy(data + offset, &msg.newEndTime, 8);
        offset += 8;
        data[offset++] = msg.subBook;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHSYMBOLSTATUS_H
