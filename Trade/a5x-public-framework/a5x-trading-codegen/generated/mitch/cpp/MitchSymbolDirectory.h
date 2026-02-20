#ifndef MITCHSYMBOLDIRECTORY_H
#define MITCHSYMBOLDIRECTORY_H

#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::mitch {

/// Used to disseminate information on each instrument
struct MitchSymbolDirectory {
    /// Nanoseconds offset from the last Time message
    uint32_t nanosecond;

    /// Instrument's symbol
    std::string symbol;

    /// Numeric Identifier of the instrument
    uint32_t instrumentId;

    /// Space = Active, H = Halted, S = Suspended, a = Inactive
    std::string symbolStatus;

    /// Instrument identification number (e.g. ISIN, CUSIP, etc.)
    std::string identificationNumber;

    /// Segment the instrument is assigned to
    std::string segment;

    /// Date an instrument expires or matures. This field will contain only spaces if the instrument is not a derivative or fixed income instrument
    void* expirationDate;

    /// Symbol of the underlying instrument. This field will contain only spaces if the instrument is not a derivative
    std::string underlying;

    /// Numeric Identifier of the underlying instrument
    uint32_t underlyingInstrumentId;

    /// Strike price of an option. The price will be zero if the instrument is not an option
    int64_t strikePrice;

    double strikePriceAsDecimal() const { return strikePrice / 10000.0; }

    /// Space = Not option, C = Call Option, P = Put Option
    std::string optionType;

    /// Issuer of the instrument. This field will contain all spaces if the instrument is not a fixed income instrument
    std::string issuer;

    /// Date instrument was issued. This field will contain all spaces if the instrument is not a fixed income instrument
    void* issueDate;

    /// Rate of interest applied to the face value. This is a percentage field (e.g. 0.05 represents 5%)
    int32_t coupon;

    double couponAsDecimal() const { return coupon / 10000.0; }

    /// Bit 0: Inverse Order Book (0 = No, 1 = Yes)
    void* flags;

    /// Bit 0: Regular (0=No, 1=Yes), Bit 1: Off-Book (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes), Bit 6: Negotiated Trades (0=No, 1=Yes)
    void* subBook;

    /// Pipe separated field. Identifies the type of Corporate Actions applicable on the instrument for the current Trading day
    std::string corporateAction;

    /// Identity of the partition
    std::string partitionId;

    /// Space = Not option, A = American, E = European
    std::string exerciseStyle;

    /// Symbol of Leg 1 instrument. This field will contain only spaces if the instrument is not a multi-legged instrument
    std::string leg1Symbol;

    /// Numeric Identifier of the leg 1 instrument
    uint32_t leg1InstrumentId;

    /// Symbol of Leg 2 instrument. This field will contain only spaces if the instrument is not a multi-legged instrument
    std::string leg2Symbol;

    /// Numeric Identifier of the leg 2 instrument
    uint32_t leg2InstrumentId;

    /// Defines the multiplier of the instrument. This field will contain 0 value if the instrument does not have a contract multiplier
    int32_t contractMultiplier;

    double contractMultiplierAsDecimal() const { return contractMultiplier / 10000.0; }

    /// Space = No method, C = Cash, P = Physical
    std::string settlementMethod;

    /// 0 = Not test, 1 = Exchange only, 2 = End to end test
    uint8_t testInstrument;

    /// Trading venue where the symbol was originated
    std::string venueOfExecution;

    /// Lot Size of the Instrument
    uint32_t lotSize;

    /// Description of the instrument
    std::string securityDescription;

    /// P = Pre-Listed, U = User Defined
    uint8_t listMethod;

    /// Trading currency of the instrument
    std::string currency;

    /// Instrument Tick Size (e.g. 0.01, 0.50, 0.001)
    int64_t tickSize;

    double tickSizeAsDecimal() const { return tickSize / 10000.0; }

    /// Minimum size that cross orders should have
    uint32_t minimumCrossOrderSize;

    /// Minimum allowed quantity of an order
    uint32_t minimumSizeQty;

    /// Maximum allowed quantity of an order
    uint32_t maximumSizeQty;

    /// 0=CORP, 1=CST, 2=FUT, 3=MLEG, 4=OOF, 5=OPT, 6=PS, 7=TB, 8=TBILL, 9=TBOND, 10=TIPS
    uint8_t securityType;

    /// Asset Name (e.g. PETR)
    std::string assetName;

    static void parse(MitchSymbolDirectory& msg, const uint8_t* data) {
        size_t offset = 0;
        std::memcpy(&msg.nanosecond, data + offset, 4);
        offset += 4;
        msg.symbol = std::string(reinterpret_cast<const char*>(data + offset), 25);
        msg.symbol.erase(msg.symbol.find_last_not_of(' ') + 1);
        offset += 25;
        std::memcpy(&msg.instrumentId, data + offset, 4);
        offset += 4;
        msg.symbolStatus = std::string(reinterpret_cast<const char*>(data + offset), 1);
        msg.symbolStatus.erase(msg.symbolStatus.find_last_not_of(' ') + 1);
        offset += 1;
        msg.identificationNumber = std::string(reinterpret_cast<const char*>(data + offset), 12);
        msg.identificationNumber.erase(msg.identificationNumber.find_last_not_of(' ') + 1);
        offset += 12;
        msg.segment = std::string(reinterpret_cast<const char*>(data + offset), 10);
        msg.segment.erase(msg.segment.find_last_not_of(' ') + 1);
        offset += 10;
        msg.underlying = std::string(reinterpret_cast<const char*>(data + offset), 25);
        msg.underlying.erase(msg.underlying.find_last_not_of(' ') + 1);
        offset += 25;
        std::memcpy(&msg.underlyingInstrumentId, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.strikePrice, data + offset, 8);
        offset += 8;
        msg.optionType = std::string(reinterpret_cast<const char*>(data + offset), 1);
        msg.optionType.erase(msg.optionType.find_last_not_of(' ') + 1);
        offset += 1;
        msg.issuer = std::string(reinterpret_cast<const char*>(data + offset), 6);
        msg.issuer.erase(msg.issuer.find_last_not_of(' ') + 1);
        offset += 6;
        std::memcpy(&msg.coupon, data + offset, 4);
        offset += 4;
        msg.corporateAction = std::string(reinterpret_cast<const char*>(data + offset), 5);
        msg.corporateAction.erase(msg.corporateAction.find_last_not_of(' ') + 1);
        offset += 5;
        msg.partitionId = std::string(reinterpret_cast<const char*>(data + offset), 1);
        msg.partitionId.erase(msg.partitionId.find_last_not_of(' ') + 1);
        offset += 1;
        msg.exerciseStyle = std::string(reinterpret_cast<const char*>(data + offset), 1);
        msg.exerciseStyle.erase(msg.exerciseStyle.find_last_not_of(' ') + 1);
        offset += 1;
        msg.leg1Symbol = std::string(reinterpret_cast<const char*>(data + offset), 25);
        msg.leg1Symbol.erase(msg.leg1Symbol.find_last_not_of(' ') + 1);
        offset += 25;
        std::memcpy(&msg.leg1InstrumentId, data + offset, 4);
        offset += 4;
        msg.leg2Symbol = std::string(reinterpret_cast<const char*>(data + offset), 25);
        msg.leg2Symbol.erase(msg.leg2Symbol.find_last_not_of(' ') + 1);
        offset += 25;
        std::memcpy(&msg.leg2InstrumentId, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.contractMultiplier, data + offset, 4);
        offset += 4;
        msg.settlementMethod = std::string(reinterpret_cast<const char*>(data + offset), 1);
        msg.settlementMethod.erase(msg.settlementMethod.find_last_not_of(' ') + 1);
        offset += 1;
        msg.testInstrument = data[offset++];
        msg.venueOfExecution = std::string(reinterpret_cast<const char*>(data + offset), 4);
        msg.venueOfExecution.erase(msg.venueOfExecution.find_last_not_of(' ') + 1);
        offset += 4;
        std::memcpy(&msg.lotSize, data + offset, 4);
        offset += 4;
        msg.securityDescription = std::string(reinterpret_cast<const char*>(data + offset), 110);
        msg.securityDescription.erase(msg.securityDescription.find_last_not_of(' ') + 1);
        offset += 110;
        msg.listMethod = data[offset++];
        msg.currency = std::string(reinterpret_cast<const char*>(data + offset), 3);
        msg.currency.erase(msg.currency.find_last_not_of(' ') + 1);
        offset += 3;
        std::memcpy(&msg.tickSize, data + offset, 8);
        offset += 8;
        std::memcpy(&msg.minimumCrossOrderSize, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.minimumSizeQty, data + offset, 4);
        offset += 4;
        std::memcpy(&msg.maximumSizeQty, data + offset, 4);
        offset += 4;
        msg.securityType = data[offset++];
        msg.assetName = std::string(reinterpret_cast<const char*>(data + offset), 6);
        msg.assetName.erase(msg.assetName.find_last_not_of(' ') + 1);
        offset += 6;
    }

    static void encode(const MitchSymbolDirectory& msg, uint8_t* data) {
        size_t offset = 0;
        std::memcpy(data + offset, &msg.nanosecond, 4);
        offset += 4;
        std::memset(data + offset, ' ', 25);
        std::memcpy(data + offset, msg.symbol.c_str(), std::min(msg.symbol.size(), size_t(25)));
        offset += 25;
        std::memcpy(data + offset, &msg.instrumentId, 4);
        offset += 4;
        std::memset(data + offset, ' ', 1);
        std::memcpy(data + offset, msg.symbolStatus.c_str(), std::min(msg.symbolStatus.size(), size_t(1)));
        offset += 1;
        std::memset(data + offset, ' ', 12);
        std::memcpy(data + offset, msg.identificationNumber.c_str(), std::min(msg.identificationNumber.size(), size_t(12)));
        offset += 12;
        std::memset(data + offset, ' ', 10);
        std::memcpy(data + offset, msg.segment.c_str(), std::min(msg.segment.size(), size_t(10)));
        offset += 10;
        std::memset(data + offset, ' ', 25);
        std::memcpy(data + offset, msg.underlying.c_str(), std::min(msg.underlying.size(), size_t(25)));
        offset += 25;
        std::memcpy(data + offset, &msg.underlyingInstrumentId, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.strikePrice, 8);
        offset += 8;
        std::memset(data + offset, ' ', 1);
        std::memcpy(data + offset, msg.optionType.c_str(), std::min(msg.optionType.size(), size_t(1)));
        offset += 1;
        std::memset(data + offset, ' ', 6);
        std::memcpy(data + offset, msg.issuer.c_str(), std::min(msg.issuer.size(), size_t(6)));
        offset += 6;
        std::memcpy(data + offset, &msg.coupon, 4);
        offset += 4;
        std::memset(data + offset, ' ', 5);
        std::memcpy(data + offset, msg.corporateAction.c_str(), std::min(msg.corporateAction.size(), size_t(5)));
        offset += 5;
        std::memset(data + offset, ' ', 1);
        std::memcpy(data + offset, msg.partitionId.c_str(), std::min(msg.partitionId.size(), size_t(1)));
        offset += 1;
        std::memset(data + offset, ' ', 1);
        std::memcpy(data + offset, msg.exerciseStyle.c_str(), std::min(msg.exerciseStyle.size(), size_t(1)));
        offset += 1;
        std::memset(data + offset, ' ', 25);
        std::memcpy(data + offset, msg.leg1Symbol.c_str(), std::min(msg.leg1Symbol.size(), size_t(25)));
        offset += 25;
        std::memcpy(data + offset, &msg.leg1InstrumentId, 4);
        offset += 4;
        std::memset(data + offset, ' ', 25);
        std::memcpy(data + offset, msg.leg2Symbol.c_str(), std::min(msg.leg2Symbol.size(), size_t(25)));
        offset += 25;
        std::memcpy(data + offset, &msg.leg2InstrumentId, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.contractMultiplier, 4);
        offset += 4;
        std::memset(data + offset, ' ', 1);
        std::memcpy(data + offset, msg.settlementMethod.c_str(), std::min(msg.settlementMethod.size(), size_t(1)));
        offset += 1;
        data[offset++] = msg.testInstrument;
        std::memset(data + offset, ' ', 4);
        std::memcpy(data + offset, msg.venueOfExecution.c_str(), std::min(msg.venueOfExecution.size(), size_t(4)));
        offset += 4;
        std::memcpy(data + offset, &msg.lotSize, 4);
        offset += 4;
        std::memset(data + offset, ' ', 110);
        std::memcpy(data + offset, msg.securityDescription.c_str(), std::min(msg.securityDescription.size(), size_t(110)));
        offset += 110;
        data[offset++] = msg.listMethod;
        std::memset(data + offset, ' ', 3);
        std::memcpy(data + offset, msg.currency.c_str(), std::min(msg.currency.size(), size_t(3)));
        offset += 3;
        std::memcpy(data + offset, &msg.tickSize, 8);
        offset += 8;
        std::memcpy(data + offset, &msg.minimumCrossOrderSize, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.minimumSizeQty, 4);
        offset += 4;
        std::memcpy(data + offset, &msg.maximumSizeQty, 4);
        offset += 4;
        data[offset++] = msg.securityType;
        std::memset(data + offset, ' ', 6);
        std::memcpy(data + offset, msg.assetName.c_str(), std::min(msg.assetName.size(), size_t(6)));
        offset += 6;
    }
};

} // namespace a5x::trading::mitch

#endif // MITCHSYMBOLDIRECTORY_H
