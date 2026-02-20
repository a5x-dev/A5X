#ifndef MITCHORDEREXECUTEDWITHPRICESIZE_H
#define MITCHORDEREXECUTEDWITHPRICESIZE_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Sent when an order is executed and includes remaining display quantity
struct MitchOrderExecutedWithPriceSize {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Identifier for the order
    uint64_t orderId;

    /// Quantity executed
    uint32_t executedQuantity;

    /// The price at which the trade was executed
    int64_t price;

    double priceAsDecimal() const { return price / 10000.0; }

    /// Numeric Identifier of the instrument
    uint32_t instrumentId;

    /// Unique identifier of the trade
    uint64_t tradeId;

    /// Converted price of the executed volatility of the options instrument
    int64_t lastOptPx;

    double lastOptPxAsDecimal() const { return lastOptPx / 10000.0; }

    /// Converted volatility of the executed price of the options instrument
    int64_t volatility;

    double volatilityAsDecimal() const { return volatility / 10000.0; }

    /// Underlying Reference Price related to converted value calculated upon an options instrument trade execution
    int64_t underlyingReferencePrice;

    double underlyingReferencePriceAsDecimal() const { return underlyingReferencePrice / 10000.0; }

    /// Date and time when the trade was executed [Epoch]
    uint64_t tradingDateTime;

    /// Instrument identification code
    std::string instrumentIdentificationCode;

    /// 1 = ISIN Code
    uint8_t instrumentIdentificationCodeType;

    /// Currency in which the price of the trade is expressed
    std::string currency;

    /// Identification of the venue where the trade was executed
    std::string venueOfExecution;

    /// 1 = MONE - Monetary value, 2 = PERC - Percentage, 3 = YIEL - Yield
    uint8_t priceNotation;

    /// Notional value relevant to the security
    int64_t notionalAmount;

    double notionalAmountAsDecimal() const { return notionalAmount / 10000.0; }

    /// The currency in which the notional is represented
    std::string notionalCurrency;

    /// Date and time when the trade was published by the trading venue [Epoch]
    uint64_t publicationDateTime;

    /// Firm ID of the party firm
    std::string firm;

    /// Firm ID of the counter party firm
    std::string contraFirm;

    /// Displayed quantity of the order after the execution
    uint32_t displayQuantity;

    /// N = Non-Printable, Y = Printable
    uint8_t printable;

    static void parse(MitchOrderExecutedWithPriceSize& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.orderId, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.executedQuantity, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.price, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.instrumentId, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.tradeId, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.lastOptPx, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.volatility, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.underlyingReferencePrice, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.tradingDateTime, data + offset, 8);
        offset += 8;
        msg.instrumentIdentificationCode = std::string(reinterpret_cast<const char*>(data + offset), 12);
        msg.instrumentIdentificationCode.erase(msg.instrumentIdentificationCode.find_last_not_of(' ') + 1);
        offset += 12;
        msg.instrumentIdentificationCodeType = data[offset++];
        msg.currency = std::string(reinterpret_cast<const char*>(data + offset), 3);
        msg.currency.erase(msg.currency.find_last_not_of(' ') + 1);
        offset += 3;
        msg.venueOfExecution = std::string(reinterpret_cast<const char*>(data + offset), 4);
        msg.venueOfExecution.erase(msg.venueOfExecution.find_last_not_of(' ') + 1);
        offset += 4;
        msg.priceNotation = data[offset++];
        std::memcpy(&msg.notionalAmount, data + offset, 8);
        offset += 8;
        msg.notionalCurrency = std::string(reinterpret_cast<const char*>(data + offset), 3);
        msg.notionalCurrency.erase(msg.notionalCurrency.find_last_not_of(' ') + 1);
        offset += 3;
        std::memcpy(&msg.publicationDateTime, data + offset, 8);
        offset += 8;
        msg.firm = std::string(reinterpret_cast<const char*>(data + offset), 6);
        msg.firm.erase(msg.firm.find_last_not_of(' ') + 1);
        offset += 6;
        msg.contraFirm = std::string(reinterpret_cast<const char*>(data + offset), 6);
        msg.contraFirm.erase(msg.contraFirm.find_last_not_of(' ') + 1);
        offset += 6;
        std::memcpy(&msg.displayQuantity, data + offset, 4);
        offset += 4;
        msg.printable = data[offset++];
    }

    static void encode(const MitchOrderExecutedWithPriceSize& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.orderId, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.executedQuantity, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.price, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.instrumentId, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.tradeId, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.lastOptPx, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.volatility, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.underlyingReferencePrice, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.tradingDateTime, 8);
        offset += 8;
        std::memset(data + offset, ' ', 12);
        std::memcpy(data + offset, msg.instrumentIdentificationCode.c_str(), std::min(msg.instrumentIdentificationCode.size(), size_t(12)));
        offset += 12;
        data[offset++] = msg.instrumentIdentificationCodeType;
        std::memset(data + offset, ' ', 3);
        std::memcpy(data + offset, msg.currency.c_str(), std::min(msg.currency.size(), size_t(3)));
        offset += 3;
        std::memset(data + offset, ' ', 4);
        std::memcpy(data + offset, msg.venueOfExecution.c_str(), std::min(msg.venueOfExecution.size(), size_t(4)));
        offset += 4;
        data[offset++] = msg.priceNotation;
        std::memcpy(data + offset, &msg.notionalAmount, 8);
        offset += 8;
        std::memset(data + offset, ' ', 3);
        std::memcpy(data + offset, msg.notionalCurrency.c_str(), std::min(msg.notionalCurrency.size(), size_t(3)));
        offset += 3;
        std::memcpy(data + offset, &msg.publicationDateTime, 8);
        offset += 8;
        std::memset(data + offset, ' ', 6);
        std::memcpy(data + offset, msg.firm.c_str(), std::min(msg.firm.size(), size_t(6)));
        offset += 6;
        std::memset(data + offset, ' ', 6);
        std::memcpy(data + offset, msg.contraFirm.c_str(), std::min(msg.contraFirm.size(), size_t(6)));
        offset += 6;
        std::memcpy(data + offset, &msg.displayQuantity, 4);
        offset += 4;
        data[offset++] = msg.printable;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHORDEREXECUTEDWITHPRICESIZE_H
