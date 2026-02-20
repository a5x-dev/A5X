#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Mass Quote
 * Message Type: i
 */
struct NativeMassQuote {
    /** Client specified identifier of the message */
    uint8_t massQuoteId[10];

    /** Identity of the matching partition */
    uint8_t partitionId;

    /** 0 = Do Not Cancel, 1 = Cancel */
    uint8_t cancelOnDisconnect;

    /** 0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0) */
    uint8_t anonymity;

    /** Identifier of the trading account. Required field */
    uint64_t account;

    /** Sender Location assigned to the Trader ID/Firm */
    uint8_t senderLocation[10];

    /** Entering Trader ID */
    uint8_t enteringTrader[15];

    /** Client specified identifier of order relevant to self-execution */
    uint8_t noTradeKey[10];

    /** Free format text field. Echoed in reports */
    uint8_t memo[15];

    /** Desk ID through which the order is placed from */
    uint8_t deskId[15];

    /** Number of quote entries in the mass quote */
    uint16_t totalQuoteEntries;

    static void parse(NativeMassQuote& msg, const uint8_t* data) {
        std::memcpy(msg.massQuoteId, data + 0, 10);
        msg.partitionId = data[10];
        msg.cancelOnDisconnect = data[11];
        msg.anonymity = data[12];
        std::memcpy(&msg.account, data + 13, 8);
        std::memcpy(msg.senderLocation, data + 21, 10);
        std::memcpy(msg.enteringTrader, data + 31, 15);
        std::memcpy(msg.noTradeKey, data + 46, 10);
        std::memcpy(msg.memo, data + 56, 15);
        std::memcpy(msg.deskId, data + 71, 15);
        std::memcpy(&msg.totalQuoteEntries, data + 86, 2);
    }

    static void encode(const NativeMassQuote& msg, uint8_t* data) {
        std::memcpy(data + 0, msg.massQuoteId, 10);
        data[10] = msg.partitionId;
        data[11] = msg.cancelOnDisconnect;
        data[12] = msg.anonymity;
        std::memcpy(data + 13, &msg.account, 8);
        std::memcpy(data + 21, msg.senderLocation, 10);
        std::memcpy(data + 31, msg.enteringTrader, 15);
        std::memcpy(data + 46, msg.noTradeKey, 10);
        std::memcpy(data + 56, msg.memo, 15);
        std::memcpy(data + 71, msg.deskId, 15);
        std::memcpy(data + 86, &msg.totalQuoteEntries, 2);
    }
};

} // namespace a5x::trading::native
