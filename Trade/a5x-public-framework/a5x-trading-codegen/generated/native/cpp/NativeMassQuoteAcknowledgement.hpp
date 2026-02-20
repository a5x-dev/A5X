#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Mass Quote Acknowledgement
 * Message Type: b
 */
struct NativeMassQuoteAcknowledgement {
    /** Mass Quote ID of the Mass Quote message */
    uint8_t massQuoteId[10];

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
    uint8_t senderLocation[10];

    /** 1 = Regular */
    uint8_t orderBook;

    /** Entering Trader ID */
    uint8_t enteringTrader[15];

    /** Client specified identifier of order relevant to self-execution */
    uint8_t noTradeKey[10];

    /** Free format text field. Echoed in reports */
    uint8_t memo[15];

    /** Desk ID through which the order is placed from */
    uint8_t deskId[15];

    /** 1 = Regular (Day Session), 2 = Non-Regular (Night Session) */
    uint8_t tradingPhase;

    /** Unique sequence assigned for all market data message */
    uint64_t routingSeq;

    /** Number of quote entries */
    uint16_t totalQuoteEntries;

    static void parse(NativeMassQuoteAcknowledgement& msg, const uint8_t* data) {
        std::memcpy(msg.massQuoteId, data + 0, 10);
        msg.partitionId = data[10];
        msg.status = data[11];
        std::memcpy(&msg.rejectCode, data + 12, 4);
        std::memcpy(&msg.transactTime, data + 16, 8);
        msg.anonymity = data[24];
        std::memcpy(&msg.account, data + 25, 8);
        std::memcpy(msg.senderLocation, data + 33, 10);
        msg.orderBook = data[43];
        std::memcpy(msg.enteringTrader, data + 44, 15);
        std::memcpy(msg.noTradeKey, data + 59, 10);
        std::memcpy(msg.memo, data + 69, 15);
        std::memcpy(msg.deskId, data + 84, 15);
        msg.tradingPhase = data[99];
        std::memcpy(&msg.routingSeq, data + 100, 8);
        std::memcpy(&msg.totalQuoteEntries, data + 108, 2);
    }

    static void encode(const NativeMassQuoteAcknowledgement& msg, uint8_t* data) {
        std::memcpy(data + 0, msg.massQuoteId, 10);
        data[10] = msg.partitionId;
        data[11] = msg.status;
        std::memcpy(data + 12, &msg.rejectCode, 4);
        std::memcpy(data + 16, &msg.transactTime, 8);
        data[24] = msg.anonymity;
        std::memcpy(data + 25, &msg.account, 8);
        std::memcpy(data + 33, msg.senderLocation, 10);
        data[43] = msg.orderBook;
        std::memcpy(data + 44, msg.enteringTrader, 15);
        std::memcpy(data + 59, msg.noTradeKey, 10);
        std::memcpy(data + 69, msg.memo, 15);
        std::memcpy(data + 84, msg.deskId, 15);
        data[99] = msg.tradingPhase;
        std::memcpy(data + 100, &msg.routingSeq, 8);
        std::memcpy(data + 108, &msg.totalQuoteEntries, 2);
    }
};

} // namespace a5x::trading::native
