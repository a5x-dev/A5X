#ifndef MITCHEXTENDEDSTATISTICS_H
#define MITCHEXTENDEDSTATISTICS_H

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

#ifndef A5X_MITCH_WIRE_HELPERS
#define A5X_MITCH_WIRE_HELPERS
namespace {
inline uint16_t a5x_read_le16(const uint8_t* p) { uint16_t v = 0; for (int i = 1; i >= 0; --i) v = static_cast<uint16_t>((v << 8) | p[i]); return v; }
inline uint32_t a5x_read_le32(const uint8_t* p) { uint32_t v = 0; for (int i = 3; i >= 0; --i) v = (v << 8) | p[i]; return v; }
inline uint64_t a5x_read_le64(const uint8_t* p) { uint64_t v = 0; for (int i = 7; i >= 0; --i) v = (v << 8) | p[i]; return v; }
inline void a5x_write_le16(uint8_t* p, uint16_t v) { for (int i = 0; i < 2; ++i) p[i] = static_cast<uint8_t>(v >> (8 * i)); }
inline void a5x_write_le32(uint8_t* p, uint32_t v) { for (int i = 0; i < 4; ++i) p[i] = static_cast<uint8_t>(v >> (8 * i)); }
inline void a5x_write_le64(uint8_t* p, uint64_t v) { for (int i = 0; i < 8; ++i) p[i] = static_cast<uint8_t>(v >> (8 * i)); }
inline int32_t a5x_read_sm32(const uint8_t* p) { uint32_t v = a5x_read_le32(p); int32_t m = static_cast<int32_t>(v & 0x7FFFFFFFu); return (v >> 31) ? -m : m; }
inline int64_t a5x_read_sm64(const uint8_t* p) { uint64_t v = a5x_read_le64(p); int64_t m = static_cast<int64_t>(v & 0x7FFFFFFFFFFFFFFFull); return (v >> 63) ? -m : m; }
inline void a5x_write_sm32(uint8_t* p, int32_t v) { a5x_write_le32(p, v < 0 ? (static_cast<uint32_t>(-static_cast<int64_t>(v)) | 0x80000000u) : static_cast<uint32_t>(v)); }
inline void a5x_write_sm64(uint8_t* p, int64_t v) { a5x_write_le64(p, v < 0 ? ((~static_cast<uint64_t>(v) + 1) | 0x8000000000000000ull) : static_cast<uint64_t>(v)); }
} // namespace
#endif // A5X_MITCH_WIRE_HELPERS

namespace a5x::trading::mitch {

/// Sent to provide extended statistical information about instruments
struct MitchExtendedStatistics {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Numeric identifier of the instrument
    uint32_t instrumentId;

    /// High Price of the instrument. Will be set to a negative value if not set or withdrawn
    int64_t highPrice;

    double highPriceAsDecimal() const { return highPrice / 100000000.0; }

    /// Low Price of the instrument. Will be set to a negative value if not set or withdrawn
    int64_t lowPrice;

    double lowPriceAsDecimal() const { return lowPrice / 100000000.0; }

    /// VWAP of the instrument. Will be set to a negative value if not set or withdrawn
    int64_t vwap;

    double vwapAsDecimal() const { return vwap / 100000000.0; }

    /// Volume of the instrument. Will be set to zero if not set or withdrawn
    uint32_t volume;

    /// Turnover of the instrument. Will be set to a negative value if not set or withdrawn
    int64_t turnover;

    double turnoverAsDecimal() const { return turnover / 100000000.0; }

    /// Number of trades for this instrument. Will be set to zero if not set or withdrawn
    uint32_t numberOfTrades;

    /// Reserved for future use.
    uint8_t reserved1[8];

    /// 1 = Regular, 2 = Off-Book, 11 = Negotiated Trades
    uint8_t subBook;

    /// Notional exposure related to the options trade executions
    int64_t notionalExposure;

    double notionalExposureAsDecimal() const { return notionalExposure / 100000000.0; }

    /// Notional exposure updated by the delta of the option based on trade executions
    int64_t notionalDeltaExposure;

    double notionalDeltaExposureAsDecimal() const { return notionalDeltaExposure / 100000000.0; }

    /// Theoretical Price of the options trade
    int64_t theoreticalPrice;

    double theoreticalPriceAsDecimal() const { return theoreticalPrice / 100000000.0; }

    /// Converted volatility of the executed price of the options instrument
    int64_t volatility;

    double volatilityAsDecimal() const { return volatility / 100000000.0; }

    /// Upper Dynamic Price Band Limit of the instrument
    int64_t upperDynamicPBLimit;

    double upperDynamicPBLimitAsDecimal() const { return upperDynamicPBLimit / 100000000.0; }

    /// Lower Dynamic Price Band Limit of the instrument
    int64_t lowerDynamicPBLimit;

    double lowerDynamicPBLimitAsDecimal() const { return lowerDynamicPBLimit / 100000000.0; }

    /// Upper Static Price Band Limit of the instrument
    int64_t upperStaticPBLimit;

    double upperStaticPBLimitAsDecimal() const { return upperStaticPBLimit / 100000000.0; }

    /// Lower Static Price Band Limit of the instrument
    int64_t lowerStaticPBLimit;

    double lowerStaticPBLimitAsDecimal() const { return lowerStaticPBLimit / 100000000.0; }

    /// Upper Dynamic Circuit Breaker Limit of the instrument
    int64_t upperDynamicCBLimit;

    double upperDynamicCBLimitAsDecimal() const { return upperDynamicCBLimit / 100000000.0; }

    /// Lower Dynamic Circuit Breaker Limit of the instrument
    int64_t lowerDynamicCBLimit;

    double lowerDynamicCBLimitAsDecimal() const { return lowerDynamicCBLimit / 100000000.0; }

    /// Upper Static Circuit Breaker Limit of the instrument
    int64_t upperStaticCBLimit;

    double upperStaticCBLimitAsDecimal() const { return upperStaticCBLimit / 100000000.0; }

    /// Lower Static Circuit Breaker Limit of the instrument
    int64_t lowerStaticCBLimit;

    double lowerStaticCBLimitAsDecimal() const { return lowerStaticCBLimit / 100000000.0; }

    /// Previous day's closing Open Interest of the instrument
    uint64_t openInterest;

    static void parse(MitchExtendedStatistics& msg, const uint8_t* data) {
        size_t offset = 0;
        msg.nanosecond = a5x_read_le32(data + offset);
        offset += 4;
        msg.instrumentId = a5x_read_le32(data + offset);
        offset += 4;
        msg.highPrice = a5x_read_sm64(data + offset);
        offset += 8;
        msg.lowPrice = a5x_read_sm64(data + offset);
        offset += 8;
        msg.vwap = a5x_read_sm64(data + offset);
        offset += 8;
        msg.volume = a5x_read_le32(data + offset);
        offset += 4;
        msg.turnover = a5x_read_sm64(data + offset);
        offset += 8;
        msg.numberOfTrades = a5x_read_le32(data + offset);
        offset += 4;
        std::memcpy(msg.reserved1, data + offset, 8);
        offset += 8;
        msg.subBook = data[offset++];
        msg.notionalExposure = a5x_read_sm64(data + offset);
        offset += 8;
        msg.notionalDeltaExposure = a5x_read_sm64(data + offset);
        offset += 8;
        msg.theoreticalPrice = a5x_read_sm64(data + offset);
        offset += 8;
        msg.volatility = a5x_read_sm64(data + offset);
        offset += 8;
        msg.upperDynamicPBLimit = a5x_read_sm64(data + offset);
        offset += 8;
        msg.lowerDynamicPBLimit = a5x_read_sm64(data + offset);
        offset += 8;
        msg.upperStaticPBLimit = a5x_read_sm64(data + offset);
        offset += 8;
        msg.lowerStaticPBLimit = a5x_read_sm64(data + offset);
        offset += 8;
        msg.upperDynamicCBLimit = a5x_read_sm64(data + offset);
        offset += 8;
        msg.lowerDynamicCBLimit = a5x_read_sm64(data + offset);
        offset += 8;
        msg.upperStaticCBLimit = a5x_read_sm64(data + offset);
        offset += 8;
        msg.lowerStaticCBLimit = a5x_read_sm64(data + offset);
        offset += 8;
        msg.openInterest = a5x_read_le64(data + offset);
        offset += 8;
    }

    static void encode(const MitchExtendedStatistics& msg, uint8_t* data) {
        size_t offset = 0;
        a5x_write_le32(data + offset, msg.nanosecond);
        offset += 4;
        a5x_write_le32(data + offset, msg.instrumentId);
        offset += 4;
        a5x_write_sm64(data + offset, msg.highPrice);
        offset += 8;
        a5x_write_sm64(data + offset, msg.lowPrice);
        offset += 8;
        a5x_write_sm64(data + offset, msg.vwap);
        offset += 8;
        a5x_write_le32(data + offset, msg.volume);
        offset += 4;
        a5x_write_sm64(data + offset, msg.turnover);
        offset += 8;
        a5x_write_le32(data + offset, msg.numberOfTrades);
        offset += 4;
        std::memcpy(data + offset, msg.reserved1, 8);
        offset += 8;
        data[offset++] = msg.subBook;
        a5x_write_sm64(data + offset, msg.notionalExposure);
        offset += 8;
        a5x_write_sm64(data + offset, msg.notionalDeltaExposure);
        offset += 8;
        a5x_write_sm64(data + offset, msg.theoreticalPrice);
        offset += 8;
        a5x_write_sm64(data + offset, msg.volatility);
        offset += 8;
        a5x_write_sm64(data + offset, msg.upperDynamicPBLimit);
        offset += 8;
        a5x_write_sm64(data + offset, msg.lowerDynamicPBLimit);
        offset += 8;
        a5x_write_sm64(data + offset, msg.upperStaticPBLimit);
        offset += 8;
        a5x_write_sm64(data + offset, msg.lowerStaticPBLimit);
        offset += 8;
        a5x_write_sm64(data + offset, msg.upperDynamicCBLimit);
        offset += 8;
        a5x_write_sm64(data + offset, msg.lowerDynamicCBLimit);
        offset += 8;
        a5x_write_sm64(data + offset, msg.upperStaticCBLimit);
        offset += 8;
        a5x_write_sm64(data + offset, msg.lowerStaticCBLimit);
        offset += 8;
        a5x_write_le64(data + offset, msg.openInterest);
        offset += 8;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHEXTENDEDSTATISTICS_H
