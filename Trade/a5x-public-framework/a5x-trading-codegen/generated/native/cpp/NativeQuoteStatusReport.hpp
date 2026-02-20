#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Quote Status Report
 * Message Type: AI
 */
struct NativeQuoteStatusReport {
    /** Identity of the matching partition */
    uint8_t partitionId;

    /** Message sequence number of the Matching Partition */
    uint64_t sequenceNumber;

    /** Client specified identifier of the quote if available. Null filled otherwise */
    uint8_t quoteMsgId[10];

    /** 6 = Removed from Market, 7 = Expired, 17 = Cancelled */
    uint8_t quoteStatus;

    /** Code specifying the reason for the reject. Applicable only if Quote Status is Rejected, null filled otherwise */
    int32_t rejectCode;

    /** 1 = Regular */
    uint32_t orderBook;

    /** Time the message was generated in epoch format */
    uint64_t transactTime;

    /** 0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0) */
    uint8_t anonymity;

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** The system generated unique ID for the Bid Side */
    uint8_t bidId[10];

    /** The system generated unique ID for the Offer Side */
    uint8_t offerId[10];

    /** Identifier of the Account */
    uint64_t account;

    /** Sender Location assigned to the Trader ID/Firm */
    uint8_t senderLocation[10];

    /** Entering Trader ID */
    uint8_t enteringTrader[15];

    /** Free format text field. Echoed in reports */
    uint8_t memo[15];

    /** Desk ID through which the order is placed from */
    uint8_t deskId[15];

    static void parse(NativeQuoteStatusReport& msg, const uint8_t* data) {
        msg.partitionId = data[0];
        std::memcpy(&msg.sequenceNumber, data + 1, 8);
        std::memcpy(msg.quoteMsgId, data + 9, 10);
        msg.quoteStatus = data[19];
        std::memcpy(&msg.rejectCode, data + 20, 4);
        std::memcpy(&msg.orderBook, data + 24, 4);
        std::memcpy(&msg.transactTime, data + 28, 8);
        msg.anonymity = data[36];
        std::memcpy(&msg.instrumentId, data + 37, 4);
        std::memcpy(msg.bidId, data + 41, 10);
        std::memcpy(msg.offerId, data + 51, 10);
        std::memcpy(&msg.account, data + 61, 8);
        std::memcpy(msg.senderLocation, data + 69, 10);
        std::memcpy(msg.enteringTrader, data + 79, 15);
        std::memcpy(msg.memo, data + 94, 15);
        std::memcpy(msg.deskId, data + 109, 15);
    }

    static void encode(const NativeQuoteStatusReport& msg, uint8_t* data) {
        data[0] = msg.partitionId;
        std::memcpy(data + 1, &msg.sequenceNumber, 8);
        std::memcpy(data + 9, msg.quoteMsgId, 10);
        data[19] = msg.quoteStatus;
        std::memcpy(data + 20, &msg.rejectCode, 4);
        std::memcpy(data + 24, &msg.orderBook, 4);
        std::memcpy(data + 28, &msg.transactTime, 8);
        data[36] = msg.anonymity;
        std::memcpy(data + 37, &msg.instrumentId, 4);
        std::memcpy(data + 41, msg.bidId, 10);
        std::memcpy(data + 51, msg.offerId, 10);
        std::memcpy(data + 61, &msg.account, 8);
        std::memcpy(data + 69, msg.senderLocation, 10);
        std::memcpy(data + 79, msg.enteringTrader, 15);
        std::memcpy(data + 94, msg.memo, 15);
        std::memcpy(data + 109, msg.deskId, 15);
    }
};

} // namespace a5x::trading::native
