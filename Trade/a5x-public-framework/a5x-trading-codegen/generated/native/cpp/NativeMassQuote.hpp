#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * Mass Quote
 * Message Type: i
 */
struct NativeMassQuote {
    /** Client specified identifier of the message */
    std::string massQuoteId;

    /** Identity of the matching partition */
    uint8_t partitionId;

    /** 0 = Do Not Cancel, 1 = Cancel */
    uint8_t cancelOnDisconnect;

    /** 0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0) */
    uint8_t anonymity;

    /** Identifier of the trading account. Required field */
    uint64_t account;

    /** Sender Location assigned to the Trader ID/Firm */
    std::string senderLocation;

    /** Entering Trader ID */
    std::string enteringTrader;

    /** Client specified identifier of order relevant to self-execution */
    std::string noTradeKey;

    /** Free format text field. Echoed in reports */
    std::string memo;

    /** Desk ID through which the order is placed from */
    std::string deskId;

    /** Number of quote entries in the mass quote */
    uint16_t totalQuoteEntries;

    static void parse(NativeMassQuote& msg, const uint8_t* data) {
        msg.massQuoteId.assign(reinterpret_cast<const char*>(data + 0), strnlen(reinterpret_cast<const char*>(data + 0), 10));
        msg.partitionId = data[10];
        msg.cancelOnDisconnect = data[11];
        msg.anonymity = data[12];
        std::memcpy(&msg.account, data + 13, 8);
        msg.senderLocation.assign(reinterpret_cast<const char*>(data + 21), strnlen(reinterpret_cast<const char*>(data + 21), 10));
        msg.enteringTrader.assign(reinterpret_cast<const char*>(data + 31), strnlen(reinterpret_cast<const char*>(data + 31), 15));
        msg.noTradeKey.assign(reinterpret_cast<const char*>(data + 46), strnlen(reinterpret_cast<const char*>(data + 46), 10));
        msg.memo.assign(reinterpret_cast<const char*>(data + 56), strnlen(reinterpret_cast<const char*>(data + 56), 15));
        msg.deskId.assign(reinterpret_cast<const char*>(data + 71), strnlen(reinterpret_cast<const char*>(data + 71), 15));
        std::memcpy(&msg.totalQuoteEntries, data + 86, 2);
    }

    static void encode(const NativeMassQuote& msg, uint8_t* data) {
        std::memset(data + 0, 0, 10); std::memcpy(data + 0, msg.massQuoteId.c_str(), std::min(msg.massQuoteId.size(), size_t(10)));
        data[10] = msg.partitionId;
        data[11] = msg.cancelOnDisconnect;
        data[12] = msg.anonymity;
        std::memcpy(data + 13, &msg.account, 8);
        std::memset(data + 21, 0, 10); std::memcpy(data + 21, msg.senderLocation.c_str(), std::min(msg.senderLocation.size(), size_t(10)));
        std::memset(data + 31, 0, 15); std::memcpy(data + 31, msg.enteringTrader.c_str(), std::min(msg.enteringTrader.size(), size_t(15)));
        std::memset(data + 46, 0, 10); std::memcpy(data + 46, msg.noTradeKey.c_str(), std::min(msg.noTradeKey.size(), size_t(10)));
        std::memset(data + 56, 0, 15); std::memcpy(data + 56, msg.memo.c_str(), std::min(msg.memo.size(), size_t(15)));
        std::memset(data + 71, 0, 15); std::memcpy(data + 71, msg.deskId.c_str(), std::min(msg.deskId.size(), size_t(15)));
        std::memcpy(data + 86, &msg.totalQuoteEntries, 2);
    }
};

} // namespace a5x::trading::native
