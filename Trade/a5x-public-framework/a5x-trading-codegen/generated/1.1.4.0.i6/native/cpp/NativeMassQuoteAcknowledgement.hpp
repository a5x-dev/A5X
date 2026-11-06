#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * Mass Quote Acknowledgement
 * Message Type: J
 */
struct NativeMassQuoteAcknowledgement {
    /** Mass Quote ID of the Mass Quote message */
    std::string massQuoteId;

    /** Identity of the matching partition */
    uint8_t partitionId;

    /** 0 = Accepted, 5 = Rejected */
    uint8_t status;

    /** Code specifying the reason for the reject. Ignore if Status is not Rejected (0) */
    int32_t rejectCode;

    /** Time of quote creation in epoch format */
    uint64_t transactTime;

    /** 0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0) */
    uint8_t anonymity;

    /** Identifier of the Account */
    uint64_t account;

    /** Sender Location assigned to the Trader ID/Firm */
    std::string senderLocation;

    /** 1 = Regular */
    uint8_t orderBook;

    /** Entering Trader ID */
    std::string enteringTrader;

    /** Client specified identifier of order relevant to self-execution */
    std::string noTradeKey;

    /** Free format text field. Echoed in reports */
    std::string memo;

    /** Desk ID through which the order is placed from */
    std::string deskId;

    /** 1 = Regular (Day Session), 2 = Non-Regular (Night Session) */
    uint8_t tradingPhase;

    /** Unique sequence assigned for all market data message */
    uint64_t routingSeq;

    /** Number of quote entries */
    uint16_t totalQuoteEntries;

    static void parse(NativeMassQuoteAcknowledgement& msg, const uint8_t* data) {
        msg.massQuoteId.assign(reinterpret_cast<const char*>(data + 0), strnlen(reinterpret_cast<const char*>(data + 0), 10));
        msg.partitionId = data[10];
        msg.status = data[11];
        std::memcpy(&msg.rejectCode, data + 12, 4);
        std::memcpy(&msg.transactTime, data + 16, 8);
        msg.anonymity = data[24];
        std::memcpy(&msg.account, data + 25, 8);
        msg.senderLocation.assign(reinterpret_cast<const char*>(data + 33), strnlen(reinterpret_cast<const char*>(data + 33), 10));
        msg.orderBook = data[43];
        msg.enteringTrader.assign(reinterpret_cast<const char*>(data + 44), strnlen(reinterpret_cast<const char*>(data + 44), 15));
        msg.noTradeKey.assign(reinterpret_cast<const char*>(data + 59), strnlen(reinterpret_cast<const char*>(data + 59), 10));
        msg.memo.assign(reinterpret_cast<const char*>(data + 69), strnlen(reinterpret_cast<const char*>(data + 69), 15));
        msg.deskId.assign(reinterpret_cast<const char*>(data + 84), strnlen(reinterpret_cast<const char*>(data + 84), 15));
        msg.tradingPhase = data[99];
        std::memcpy(&msg.routingSeq, data + 100, 8);
        std::memcpy(&msg.totalQuoteEntries, data + 108, 2);
    }

    static void encode(const NativeMassQuoteAcknowledgement& msg, uint8_t* data) {
        std::memset(data + 0, 0, 10); std::memcpy(data + 0, msg.massQuoteId.c_str(), std::min(msg.massQuoteId.size(), size_t(10)));
        data[10] = msg.partitionId;
        data[11] = msg.status;
        std::memcpy(data + 12, &msg.rejectCode, 4);
        std::memcpy(data + 16, &msg.transactTime, 8);
        data[24] = msg.anonymity;
        std::memcpy(data + 25, &msg.account, 8);
        std::memset(data + 33, 0, 10); std::memcpy(data + 33, msg.senderLocation.c_str(), std::min(msg.senderLocation.size(), size_t(10)));
        data[43] = msg.orderBook;
        std::memset(data + 44, 0, 15); std::memcpy(data + 44, msg.enteringTrader.c_str(), std::min(msg.enteringTrader.size(), size_t(15)));
        std::memset(data + 59, 0, 10); std::memcpy(data + 59, msg.noTradeKey.c_str(), std::min(msg.noTradeKey.size(), size_t(10)));
        std::memset(data + 69, 0, 15); std::memcpy(data + 69, msg.memo.c_str(), std::min(msg.memo.size(), size_t(15)));
        std::memset(data + 84, 0, 15); std::memcpy(data + 84, msg.deskId.c_str(), std::min(msg.deskId.size(), size_t(15)));
        data[99] = msg.tradingPhase;
        std::memcpy(data + 100, &msg.routingSeq, 8);
        std::memcpy(data + 108, &msg.totalQuoteEntries, 2);
    }
};

} // namespace a5x::trading::native
