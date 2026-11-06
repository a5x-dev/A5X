#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * Quote Status Report
 * Message Type: c
 */
struct NativeQuoteStatusReport {
    /** Identity of the matching partition */
    uint8_t partitionId;

    /** Message sequence number of the Matching Partition */
    uint64_t sequenceNumber;

    /** Client specified identifier of the quote if available. Null filled otherwise */
    std::string quoteMsgId;

    /** 6 = Removed from Market, 7 = Expired, 17 = Cancelled */
    uint8_t quoteStatus;

    /** Code specifying the reason for the reject. Applicable only if Quote Status is Rejected, null filled otherwise */
    int32_t rejectCode;

    /** 1 = Regular */
    uint8_t orderBook;

    /** Time the message was generated in epoch format */
    uint64_t transactTime;

    /** 0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0) */
    uint8_t anonymity;

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** The system generated unique ID for the Bid Side */
    std::string bidId;

    /** The system generated unique ID for the Offer Side */
    std::string offerId;

    /** Identifier of the Account */
    uint64_t account;

    /** Sender Location assigned to the Trader ID/Firm */
    std::string senderLocation;

    /** Entering Trader ID */
    std::string enteringTrader;

    /** Free format text field. Echoed in reports */
    std::string memo;

    /** Desk ID through which the order is placed from */
    std::string deskId;

    static void parse(NativeQuoteStatusReport& msg, const uint8_t* data) {
        msg.partitionId = data[0];
        std::memcpy(&msg.sequenceNumber, data + 1, 8);
        msg.quoteMsgId.assign(reinterpret_cast<const char*>(data + 9), strnlen(reinterpret_cast<const char*>(data + 9), 10));
        msg.quoteStatus = data[19];
        std::memcpy(&msg.rejectCode, data + 20, 4);
        msg.orderBook = data[24];
        std::memcpy(&msg.transactTime, data + 25, 8);
        msg.anonymity = data[33];
        std::memcpy(&msg.instrumentId, data + 34, 4);
        msg.bidId.assign(reinterpret_cast<const char*>(data + 38), strnlen(reinterpret_cast<const char*>(data + 38), 10));
        msg.offerId.assign(reinterpret_cast<const char*>(data + 48), strnlen(reinterpret_cast<const char*>(data + 48), 10));
        std::memcpy(&msg.account, data + 58, 8);
        msg.senderLocation.assign(reinterpret_cast<const char*>(data + 66), strnlen(reinterpret_cast<const char*>(data + 66), 10));
        msg.enteringTrader.assign(reinterpret_cast<const char*>(data + 76), strnlen(reinterpret_cast<const char*>(data + 76), 15));
        msg.memo.assign(reinterpret_cast<const char*>(data + 91), strnlen(reinterpret_cast<const char*>(data + 91), 15));
        msg.deskId.assign(reinterpret_cast<const char*>(data + 106), strnlen(reinterpret_cast<const char*>(data + 106), 15));
    }

    static void encode(const NativeQuoteStatusReport& msg, uint8_t* data) {
        data[0] = msg.partitionId;
        std::memcpy(data + 1, &msg.sequenceNumber, 8);
        std::memset(data + 9, 0, 10); std::memcpy(data + 9, msg.quoteMsgId.c_str(), std::min(msg.quoteMsgId.size(), size_t(10)));
        data[19] = msg.quoteStatus;
        std::memcpy(data + 20, &msg.rejectCode, 4);
        data[24] = msg.orderBook;
        std::memcpy(data + 25, &msg.transactTime, 8);
        data[33] = msg.anonymity;
        std::memcpy(data + 34, &msg.instrumentId, 4);
        std::memset(data + 38, 0, 10); std::memcpy(data + 38, msg.bidId.c_str(), std::min(msg.bidId.size(), size_t(10)));
        std::memset(data + 48, 0, 10); std::memcpy(data + 48, msg.offerId.c_str(), std::min(msg.offerId.size(), size_t(10)));
        std::memcpy(data + 58, &msg.account, 8);
        std::memset(data + 66, 0, 10); std::memcpy(data + 66, msg.senderLocation.c_str(), std::min(msg.senderLocation.size(), size_t(10)));
        std::memset(data + 76, 0, 15); std::memcpy(data + 76, msg.enteringTrader.c_str(), std::min(msg.enteringTrader.size(), size_t(15)));
        std::memset(data + 91, 0, 15); std::memcpy(data + 91, msg.memo.c_str(), std::min(msg.memo.size(), size_t(15)));
        std::memset(data + 106, 0, 15); std::memcpy(data + 106, msg.deskId.c_str(), std::min(msg.deskId.size(), size_t(15)));
    }
};

} // namespace a5x::trading::native
