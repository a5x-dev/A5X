#ifndef MITCHTRADE_H
#define MITCHTRADE_H

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

/// Sent when a non-displayed trade occurs
struct MitchTrade {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Identifier for the order
    uint64_t orderId;

    /// Quantity executed
    uint32_t executedQuantity;

    /// Executed price
    int64_t price;

    double priceAsDecimal() const { return price / 100000000.0; }

    /// Numeric identifier of the instrument
    uint32_t instrumentId;

    /// Unique identifier of the trade
    uint64_t tradeId;

    /// Reserved for future use.
    int64_t reserved1;

    double reserved1AsDecimal() const { return reserved1 / 100000000.0; }

    /// Reserved for future use.
    int64_t reserved2;

    double reserved2AsDecimal() const { return reserved2 / 100000000.0; }

    /// Reserved for future use.
    int64_t reserved3;

    double reserved3AsDecimal() const { return reserved3 / 100000000.0; }

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

    double notionalAmountAsDecimal() const { return notionalAmount / 100000000.0; }

    /// Reserved for future use.
    std::string reserved4;

    /// Date and time when the trade was published by the trading venue [Epoch]
    uint64_t publicationDateTime;

    /// Buyer Firm ID
    std::string buyerFirm;

    /// Seller Firm ID
    std::string sellerFirm;

    /// PTCancellationFlag. Indicates if the trade represented by the message has been cancelled (Value = CANC)
    std::string cancellationFlag;

    /// PTAmendmentFlag. Indicates if the trade represented by the message has been amended (Value = AMND)
    std::string amendmentFlag;

    /// 1 = Regular, 11 = Negotiated Trades
    uint8_t subBook;

    /// Bit 0: Trade Condition Flag, Bit 1: Internal Crossed Trade, Bit 5: RLP
    uint8_t flags;

    /// Date of the trading day (YYYYMMDD)
    std::string tradeDate;

    static void parse(MitchTrade& msg, const uint8_t* data) {
        size_t offset = 0;
        msg.nanosecond = a5x_read_le32(data + offset);
        offset += 4;
        msg.orderId = a5x_read_le64(data + offset);
        offset += 8;
        msg.executedQuantity = a5x_read_le32(data + offset);
        offset += 4;
        msg.price = a5x_read_sm64(data + offset);
        offset += 8;
        msg.instrumentId = a5x_read_le32(data + offset);
        offset += 4;
        msg.tradeId = a5x_read_le64(data + offset);
        offset += 8;
        msg.reserved1 = a5x_read_sm64(data + offset);
        offset += 8;
        msg.reserved2 = a5x_read_sm64(data + offset);
        offset += 8;
        msg.reserved3 = a5x_read_sm64(data + offset);
        offset += 8;
        msg.tradingDateTime = a5x_read_le64(data + offset);
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
        msg.notionalAmount = a5x_read_sm64(data + offset);
        offset += 8;
        msg.reserved4 = std::string(reinterpret_cast<const char*>(data + offset), 3);
        msg.reserved4.erase(msg.reserved4.find_last_not_of(' ') + 1);
        offset += 3;
        msg.publicationDateTime = a5x_read_le64(data + offset);
        offset += 8;
        msg.buyerFirm = std::string(reinterpret_cast<const char*>(data + offset), 6);
        msg.buyerFirm.erase(msg.buyerFirm.find_last_not_of(' ') + 1);
        offset += 6;
        msg.sellerFirm = std::string(reinterpret_cast<const char*>(data + offset), 6);
        msg.sellerFirm.erase(msg.sellerFirm.find_last_not_of(' ') + 1);
        offset += 6;
        msg.cancellationFlag = std::string(reinterpret_cast<const char*>(data + offset), 4);
        msg.cancellationFlag.erase(msg.cancellationFlag.find_last_not_of(' ') + 1);
        offset += 4;
        msg.amendmentFlag = std::string(reinterpret_cast<const char*>(data + offset), 4);
        msg.amendmentFlag.erase(msg.amendmentFlag.find_last_not_of(' ') + 1);
        offset += 4;
        msg.subBook = data[offset++];
        msg.flags = data[offset++];
        msg.tradeDate = std::string(reinterpret_cast<const char*>(data + offset), 8);
        msg.tradeDate.erase(msg.tradeDate.find_last_not_of(' ') + 1);
        offset += 8;
    }

    static void encode(const MitchTrade& msg, uint8_t* data) {
        size_t offset = 0;
        a5x_write_le32(data + offset, msg.nanosecond);
        offset += 4;
        a5x_write_le64(data + offset, msg.orderId);
        offset += 8;
        a5x_write_le32(data + offset, msg.executedQuantity);
        offset += 4;
        a5x_write_sm64(data + offset, msg.price);
        offset += 8;
        a5x_write_le32(data + offset, msg.instrumentId);
        offset += 4;
        a5x_write_le64(data + offset, msg.tradeId);
        offset += 8;
        a5x_write_sm64(data + offset, msg.reserved1);
        offset += 8;
        a5x_write_sm64(data + offset, msg.reserved2);
        offset += 8;
        a5x_write_sm64(data + offset, msg.reserved3);
        offset += 8;
        a5x_write_le64(data + offset, msg.tradingDateTime);
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
        a5x_write_sm64(data + offset, msg.notionalAmount);
        offset += 8;
        std::memset(data + offset, ' ', 3);
        std::memcpy(data + offset, msg.reserved4.c_str(), std::min(msg.reserved4.size(), size_t(3)));
        offset += 3;
        a5x_write_le64(data + offset, msg.publicationDateTime);
        offset += 8;
        std::memset(data + offset, ' ', 6);
        std::memcpy(data + offset, msg.buyerFirm.c_str(), std::min(msg.buyerFirm.size(), size_t(6)));
        offset += 6;
        std::memset(data + offset, ' ', 6);
        std::memcpy(data + offset, msg.sellerFirm.c_str(), std::min(msg.sellerFirm.size(), size_t(6)));
        offset += 6;
        std::memset(data + offset, ' ', 4);
        std::memcpy(data + offset, msg.cancellationFlag.c_str(), std::min(msg.cancellationFlag.size(), size_t(4)));
        offset += 4;
        std::memset(data + offset, ' ', 4);
        std::memcpy(data + offset, msg.amendmentFlag.c_str(), std::min(msg.amendmentFlag.size(), size_t(4)));
        offset += 4;
        data[offset++] = msg.subBook;
        data[offset++] = msg.flags;
        std::memset(data + offset, ' ', 8);
        std::memcpy(data + offset, msg.tradeDate.c_str(), std::min(msg.tradeDate.size(), size_t(8)));
        offset += 8;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHTRADE_H
