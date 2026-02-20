#ifndef MITCHEXTENDEDSTATISTICS_H
#define MITCHEXTENDEDSTATISTICS_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent to provide extended statistical information about instruments
struct MitchExtendedStatistics {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Numeric identifier of the instrument
    uint32_t instrumentId;

    /// High Price of the instrument. Will be set to a negative value if not set or withdrawn
    int64_t highPrice;

    double highPriceAsDecimal() const { return highPrice / 10000.0; }

    /// Low Price of the instrument. Will be set to a negative value if not set or withdrawn
    int64_t lowPrice;

    double lowPriceAsDecimal() const { return lowPrice / 10000.0; }

    /// VWAP of the instrument. Will be set to a negative value if not set or withdrawn
    int64_t vwap;

    double vwapAsDecimal() const { return vwap / 10000.0; }

    /// Volume of the instrument. Will be set to zero if not set or withdrawn
    uint32_t volume;

    /// Turnover of the instrument. Will be set to a negative value if not set or withdrawn
    int64_t turnover;

    double turnoverAsDecimal() const { return turnover / 10000.0; }

    /// Number of trades for this instrument. Will be set to zero if not set or withdrawn
    uint32_t numberOfTrades;

    /// Reserved for future use
    uint8_t reservedField[8];

    /// 1 = Regular, 2 = Off-Book, 11 = Negotiated Trades
    uint8_t subBook;

    /// Notional exposure related to the options trade executions
    int64_t notionalExposure;

    double notionalExposureAsDecimal() const { return notionalExposure / 10000.0; }

    /// Notional exposure updated by the delta of the option based on trade executions
    int64_t notionalDeltaExposure;

    double notionalDeltaExposureAsDecimal() const { return notionalDeltaExposure / 10000.0; }

    /// Theoretical Price of the options trade
    int64_t theoreticalPrice;

    double theoreticalPriceAsDecimal() const { return theoreticalPrice / 10000.0; }

    /// Converted volatility of the executed price of the options instrument
    int64_t volatility;

    double volatilityAsDecimal() const { return volatility / 10000.0; }

    /// Upper Dynamic Price Band Limit of the instrument
    int64_t upperDynamicPBLimit;

    double upperDynamicPBLimitAsDecimal() const { return upperDynamicPBLimit / 10000.0; }

    /// Lower Dynamic Price Band Limit of the instrument
    int64_t lowerDynamicPBLimit;

    double lowerDynamicPBLimitAsDecimal() const { return lowerDynamicPBLimit / 10000.0; }

    /// Upper Static Price Band Limit of the instrument
    int64_t upperStaticPBLimit;

    double upperStaticPBLimitAsDecimal() const { return upperStaticPBLimit / 10000.0; }

    /// Lower Static Price Band Limit of the instrument
    int64_t lowerStaticPBLimit;

    double lowerStaticPBLimitAsDecimal() const { return lowerStaticPBLimit / 10000.0; }

    /// Upper Dynamic Circuit Breaker Limit of the instrument
    int64_t upperDynamicCBLimit;

    double upperDynamicCBLimitAsDecimal() const { return upperDynamicCBLimit / 10000.0; }

    /// Lower Dynamic Circuit Breaker Limit of the instrument
    int64_t lowerDynamicCBLimit;

    double lowerDynamicCBLimitAsDecimal() const { return lowerDynamicCBLimit / 10000.0; }

    static void parse(MitchExtendedStatistics& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.instrumentId, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.highPrice, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.lowPrice, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.vwap, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.volume, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.turnover, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.numberOfTrades, data + offset, 4);
        offset += 4;
        std::memcpy(msg.reservedField, data + offset, 8);
        offset += 8;
        msg.subBook = data[offset++];
        std::memcpy(&msg.notionalExposure, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.notionalDeltaExposure, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.theoreticalPrice, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.volatility, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.upperDynamicPBLimit, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.lowerDynamicPBLimit, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.upperStaticPBLimit, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.lowerStaticPBLimit, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.upperDynamicCBLimit, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.lowerDynamicCBLimit, data + offset, 8);
        offset += 8;
    }

    static void encode(const MitchExtendedStatistics& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.instrumentId, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.highPrice, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.lowPrice, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.vwap, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.volume, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.turnover, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.numberOfTrades, 4);
        offset += 4;
        std::memcpy(data + offset, msg.reservedField, 8);
        offset += 8;
        data[offset++] = msg.subBook;
        std::memcpy(data + offset, &msg.notionalExposure, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.notionalDeltaExposure, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.theoreticalPrice, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.volatility, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.upperDynamicPBLimit, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.lowerDynamicPBLimit, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.upperStaticPBLimit, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.lowerStaticPBLimit, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.upperDynamicCBLimit, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.lowerDynamicCBLimit, 8);
        offset += 8;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHEXTENDEDSTATISTICS_H
