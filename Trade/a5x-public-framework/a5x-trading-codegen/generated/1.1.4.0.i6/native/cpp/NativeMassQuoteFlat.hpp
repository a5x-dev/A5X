#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * Mass Quote Flat. Mass Quote with the quote entries flattened into numbered fields.
 * Message Type: m
 */
struct NativeMassQuoteFlat {
    /** Client specified identifier of the message. */
    std::string massQuoteId;

    /** Identity of the matching partition. */
    uint8_t partitionId;

    /** 0 = Do Not Cancel, 1 = Cancel */
    uint8_t cancelOnDisconnect;

    /** Whether the quote is anonymous or named. Absence of this field is interpreted as Anonymous (0). */
    uint8_t anonymity;

    /** Identifier of the trading account. (Mandatory for Native Gateway Messages). */
    uint64_t account;

    /** Sender Location assigned to the Trader ID/Firm. */
    std::string senderLocation;

    /** Entering Trader ID. */
    std::string enteringTrader;

    /** Client specified identifier of order relevant to self-execution. */
    std::string noTradeKey;

    /** Free format text field. This field may be used to convey client's relevant info. It's always echoed in the reports. */
    std::string memo;

    /** Desk ID through which the order is placed from. */
    std::string deskId;

    /** Number of quote entries in the mass quote (up to 15). */
    uint8_t totalQuoteEntries;

    /** Client specified identifier of the quote 1. */
    std::string quoteEntryId1;

    /** Identifier of the instrument of quote 1. */
    uint32_t instrumentId1;

    /** Bid price of quote 1. */
    int64_t bidPrice1;

    /** Bid quantity of quote 1. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize1;

    /** Offer price of quote 1. */
    int64_t offerPrice1;

    /** Offer quantity of quote 1. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize1;

    /** Client specified identifier of the quote 2. */
    std::string quoteEntryId2;

    /** Identifier of the instrument of quote 2. */
    uint32_t instrumentId2;

    /** Bid price of quote 2. */
    int64_t bidPrice2;

    /** Bid quantity of quote 2. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize2;

    /** Offer price of quote 2. */
    int64_t offerPrice2;

    /** Offer quantity of quote 2. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize2;

    /** Client specified identifier of the quote 3. */
    std::string quoteEntryId3;

    /** Identifier of the instrument of quote 3. */
    uint32_t instrumentId3;

    /** Bid price of quote 3. */
    int64_t bidPrice3;

    /** Bid quantity of quote 3. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize3;

    /** Offer price of quote 3. */
    int64_t offerPrice3;

    /** Offer quantity of quote 3. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize3;

    /** Client specified identifier of the quote 4. */
    std::string quoteEntryId4;

    /** Identifier of the instrument of quote 4. */
    uint32_t instrumentId4;

    /** Bid price of quote 4. */
    int64_t bidPrice4;

    /** Bid quantity of quote 4. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize4;

    /** Offer price of quote 4. */
    int64_t offerPrice4;

    /** Offer quantity of quote 4. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize4;

    /** Client specified identifier of the quote 5. */
    std::string quoteEntryId5;

    /** Identifier of the instrument of quote 5. */
    uint32_t instrumentId5;

    /** Bid price of quote 5. */
    int64_t bidPrice5;

    /** Bid quantity of quote 5. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize5;

    /** Offer price of quote 5. */
    int64_t offerPrice5;

    /** Offer quantity of quote 5. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize5;

    /** Client specified identifier of the quote 6. */
    std::string quoteEntryId6;

    /** Identifier of the instrument of quote 6. */
    uint32_t instrumentId6;

    /** Bid price of quote 6. */
    int64_t bidPrice6;

    /** Bid quantity of quote 6. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize6;

    /** Offer price of quote 6. */
    int64_t offerPrice6;

    /** Offer quantity of quote 6. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize6;

    /** Client specified identifier of the quote 7. */
    std::string quoteEntryId7;

    /** Identifier of the instrument of quote 7. */
    uint32_t instrumentId7;

    /** Bid price of quote 7. */
    int64_t bidPrice7;

    /** Bid quantity of quote 7. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize7;

    /** Offer price of quote 7. */
    int64_t offerPrice7;

    /** Offer quantity of quote 7. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize7;

    /** Client specified identifier of the quote 8. */
    std::string quoteEntryId8;

    /** Identifier of the instrument of quote 8. */
    uint32_t instrumentId8;

    /** Bid price of quote 8. */
    int64_t bidPrice8;

    /** Bid quantity of quote 8. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize8;

    /** Offer price of quote 8. */
    int64_t offerPrice8;

    /** Offer quantity of quote 8. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize8;

    /** Client specified identifier of the quote 9. */
    std::string quoteEntryId9;

    /** Identifier of the instrument of quote 9. */
    uint32_t instrumentId9;

    /** Bid price of quote 9. */
    int64_t bidPrice9;

    /** Bid quantity of quote 9. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize9;

    /** Offer price of quote 9. */
    int64_t offerPrice9;

    /** Offer quantity of quote 9. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize9;

    /** Client specified identifier of the quote 10. */
    std::string quoteEntryId10;

    /** Identifier of the instrument of quote 10. */
    uint32_t instrumentId10;

    /** Bid price of quote 10. */
    int64_t bidPrice10;

    /** Bid quantity of quote 10. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize10;

    /** Offer price of quote 10. */
    int64_t offerPrice10;

    /** Offer quantity of quote10. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize10;

    /** Client specified identifier of the quote 11. */
    std::string quoteEntryId11;

    /** Identifier of the instrument of quote 11. */
    uint32_t instrumentId11;

    /** Bid price of quote 11. */
    int64_t bidPrice11;

    /** Bid quantity of quote 11. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize11;

    /** Offer price of quote 11. */
    int64_t offerPrice11;

    /** Offer quantity of quote11. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize11;

    /** Client specified identifier of the quote 12. */
    std::string quoteEntryId12;

    /** Identifier of the instrument of quote 12. */
    uint32_t instrumentId12;

    /** Bid price of quote 12. */
    int64_t bidPrice12;

    /** Bid quantity of quote 12. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize12;

    /** Offer price of quote 12. */
    int64_t offerPrice12;

    /** Offer quantity of quote12. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize12;

    /** Client specified identifier of the quote 13. */
    std::string quoteEntryId13;

    /** Identifier of the instrument of quote 13. */
    uint32_t instrumentId13;

    /** Bid price of quote 13. */
    int64_t bidPrice13;

    /** Bid quantity of quote 13. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize13;

    /** Offer price of quote 13. */
    int64_t offerPrice13;

    /** Offer quantity of quote13. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize13;

    /** Client specified identifier of the quote 14. */
    std::string quoteEntryId14;

    /** Identifier of the instrument of quote 14. */
    uint32_t instrumentId14;

    /** Bid price of quote 14. */
    int64_t bidPrice14;

    /** Bid quantity of quote 14. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize14;

    /** Offer price of quote 14. */
    int64_t offerPrice14;

    /** Offer quantity of quote14. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize14;

    /** Client specified identifier of the quote 15. */
    std::string quoteEntryId15;

    /** Identifier of the instrument of quote 15. */
    uint32_t instrumentId15;

    /** Bid price of quote 15. */
    int64_t bidPrice15;

    /** Bid quantity of quote 15. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t bidSize15;

    /** Offer price of quote 15. */
    int64_t offerPrice15;

    /** Offer quantity of quote15. A value of '0' cancels this side of the quote for the given instrument. */
    uint32_t offerSize15;

    static void parse(NativeMassQuoteFlat& msg, const uint8_t* data) {
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
        msg.totalQuoteEntries = data[86];
        msg.quoteEntryId1.assign(reinterpret_cast<const char*>(data + 87), strnlen(reinterpret_cast<const char*>(data + 87), 10));
        std::memcpy(&msg.instrumentId1, data + 97, 4);
        std::memcpy(&msg.bidPrice1, data + 101, 8);
        std::memcpy(&msg.bidSize1, data + 109, 4);
        std::memcpy(&msg.offerPrice1, data + 113, 8);
        std::memcpy(&msg.offerSize1, data + 121, 4);
        msg.quoteEntryId2.assign(reinterpret_cast<const char*>(data + 125), strnlen(reinterpret_cast<const char*>(data + 125), 10));
        std::memcpy(&msg.instrumentId2, data + 135, 4);
        std::memcpy(&msg.bidPrice2, data + 139, 8);
        std::memcpy(&msg.bidSize2, data + 147, 4);
        std::memcpy(&msg.offerPrice2, data + 151, 8);
        std::memcpy(&msg.offerSize2, data + 159, 4);
        msg.quoteEntryId3.assign(reinterpret_cast<const char*>(data + 163), strnlen(reinterpret_cast<const char*>(data + 163), 10));
        std::memcpy(&msg.instrumentId3, data + 173, 4);
        std::memcpy(&msg.bidPrice3, data + 177, 8);
        std::memcpy(&msg.bidSize3, data + 185, 4);
        std::memcpy(&msg.offerPrice3, data + 189, 8);
        std::memcpy(&msg.offerSize3, data + 197, 4);
        msg.quoteEntryId4.assign(reinterpret_cast<const char*>(data + 201), strnlen(reinterpret_cast<const char*>(data + 201), 10));
        std::memcpy(&msg.instrumentId4, data + 211, 4);
        std::memcpy(&msg.bidPrice4, data + 215, 8);
        std::memcpy(&msg.bidSize4, data + 223, 4);
        std::memcpy(&msg.offerPrice4, data + 227, 8);
        std::memcpy(&msg.offerSize4, data + 235, 4);
        msg.quoteEntryId5.assign(reinterpret_cast<const char*>(data + 239), strnlen(reinterpret_cast<const char*>(data + 239), 10));
        std::memcpy(&msg.instrumentId5, data + 249, 4);
        std::memcpy(&msg.bidPrice5, data + 253, 8);
        std::memcpy(&msg.bidSize5, data + 261, 4);
        std::memcpy(&msg.offerPrice5, data + 265, 8);
        std::memcpy(&msg.offerSize5, data + 273, 4);
        msg.quoteEntryId6.assign(reinterpret_cast<const char*>(data + 277), strnlen(reinterpret_cast<const char*>(data + 277), 10));
        std::memcpy(&msg.instrumentId6, data + 287, 4);
        std::memcpy(&msg.bidPrice6, data + 291, 8);
        std::memcpy(&msg.bidSize6, data + 299, 4);
        std::memcpy(&msg.offerPrice6, data + 303, 8);
        std::memcpy(&msg.offerSize6, data + 311, 4);
        msg.quoteEntryId7.assign(reinterpret_cast<const char*>(data + 315), strnlen(reinterpret_cast<const char*>(data + 315), 10));
        std::memcpy(&msg.instrumentId7, data + 325, 4);
        std::memcpy(&msg.bidPrice7, data + 329, 8);
        std::memcpy(&msg.bidSize7, data + 337, 4);
        std::memcpy(&msg.offerPrice7, data + 341, 8);
        std::memcpy(&msg.offerSize7, data + 349, 4);
        msg.quoteEntryId8.assign(reinterpret_cast<const char*>(data + 353), strnlen(reinterpret_cast<const char*>(data + 353), 10));
        std::memcpy(&msg.instrumentId8, data + 363, 4);
        std::memcpy(&msg.bidPrice8, data + 367, 8);
        std::memcpy(&msg.bidSize8, data + 375, 4);
        std::memcpy(&msg.offerPrice8, data + 379, 8);
        std::memcpy(&msg.offerSize8, data + 387, 4);
        msg.quoteEntryId9.assign(reinterpret_cast<const char*>(data + 391), strnlen(reinterpret_cast<const char*>(data + 391), 10));
        std::memcpy(&msg.instrumentId9, data + 401, 4);
        std::memcpy(&msg.bidPrice9, data + 405, 8);
        std::memcpy(&msg.bidSize9, data + 413, 4);
        std::memcpy(&msg.offerPrice9, data + 417, 8);
        std::memcpy(&msg.offerSize9, data + 425, 4);
        msg.quoteEntryId10.assign(reinterpret_cast<const char*>(data + 429), strnlen(reinterpret_cast<const char*>(data + 429), 10));
        std::memcpy(&msg.instrumentId10, data + 439, 4);
        std::memcpy(&msg.bidPrice10, data + 443, 8);
        std::memcpy(&msg.bidSize10, data + 451, 4);
        std::memcpy(&msg.offerPrice10, data + 455, 8);
        std::memcpy(&msg.offerSize10, data + 463, 4);
        msg.quoteEntryId11.assign(reinterpret_cast<const char*>(data + 467), strnlen(reinterpret_cast<const char*>(data + 467), 10));
        std::memcpy(&msg.instrumentId11, data + 477, 4);
        std::memcpy(&msg.bidPrice11, data + 481, 8);
        std::memcpy(&msg.bidSize11, data + 489, 4);
        std::memcpy(&msg.offerPrice11, data + 493, 8);
        std::memcpy(&msg.offerSize11, data + 501, 4);
        msg.quoteEntryId12.assign(reinterpret_cast<const char*>(data + 505), strnlen(reinterpret_cast<const char*>(data + 505), 10));
        std::memcpy(&msg.instrumentId12, data + 515, 4);
        std::memcpy(&msg.bidPrice12, data + 519, 8);
        std::memcpy(&msg.bidSize12, data + 527, 4);
        std::memcpy(&msg.offerPrice12, data + 531, 8);
        std::memcpy(&msg.offerSize12, data + 539, 4);
        msg.quoteEntryId13.assign(reinterpret_cast<const char*>(data + 543), strnlen(reinterpret_cast<const char*>(data + 543), 10));
        std::memcpy(&msg.instrumentId13, data + 553, 4);
        std::memcpy(&msg.bidPrice13, data + 557, 8);
        std::memcpy(&msg.bidSize13, data + 565, 4);
        std::memcpy(&msg.offerPrice13, data + 569, 8);
        std::memcpy(&msg.offerSize13, data + 577, 4);
        msg.quoteEntryId14.assign(reinterpret_cast<const char*>(data + 581), strnlen(reinterpret_cast<const char*>(data + 581), 10));
        std::memcpy(&msg.instrumentId14, data + 591, 4);
        std::memcpy(&msg.bidPrice14, data + 595, 8);
        std::memcpy(&msg.bidSize14, data + 603, 4);
        std::memcpy(&msg.offerPrice14, data + 607, 8);
        std::memcpy(&msg.offerSize14, data + 615, 4);
        msg.quoteEntryId15.assign(reinterpret_cast<const char*>(data + 619), strnlen(reinterpret_cast<const char*>(data + 619), 10));
        std::memcpy(&msg.instrumentId15, data + 629, 4);
        std::memcpy(&msg.bidPrice15, data + 633, 8);
        std::memcpy(&msg.bidSize15, data + 641, 4);
        std::memcpy(&msg.offerPrice15, data + 645, 8);
        std::memcpy(&msg.offerSize15, data + 653, 4);
    }

    static void encode(const NativeMassQuoteFlat& msg, uint8_t* data) {
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
        data[86] = msg.totalQuoteEntries;
        std::memset(data + 87, 0, 10); std::memcpy(data + 87, msg.quoteEntryId1.c_str(), std::min(msg.quoteEntryId1.size(), size_t(10)));
        std::memcpy(data + 97, &msg.instrumentId1, 4);
        std::memcpy(data + 101, &msg.bidPrice1, 8);
        std::memcpy(data + 109, &msg.bidSize1, 4);
        std::memcpy(data + 113, &msg.offerPrice1, 8);
        std::memcpy(data + 121, &msg.offerSize1, 4);
        std::memset(data + 125, 0, 10); std::memcpy(data + 125, msg.quoteEntryId2.c_str(), std::min(msg.quoteEntryId2.size(), size_t(10)));
        std::memcpy(data + 135, &msg.instrumentId2, 4);
        std::memcpy(data + 139, &msg.bidPrice2, 8);
        std::memcpy(data + 147, &msg.bidSize2, 4);
        std::memcpy(data + 151, &msg.offerPrice2, 8);
        std::memcpy(data + 159, &msg.offerSize2, 4);
        std::memset(data + 163, 0, 10); std::memcpy(data + 163, msg.quoteEntryId3.c_str(), std::min(msg.quoteEntryId3.size(), size_t(10)));
        std::memcpy(data + 173, &msg.instrumentId3, 4);
        std::memcpy(data + 177, &msg.bidPrice3, 8);
        std::memcpy(data + 185, &msg.bidSize3, 4);
        std::memcpy(data + 189, &msg.offerPrice3, 8);
        std::memcpy(data + 197, &msg.offerSize3, 4);
        std::memset(data + 201, 0, 10); std::memcpy(data + 201, msg.quoteEntryId4.c_str(), std::min(msg.quoteEntryId4.size(), size_t(10)));
        std::memcpy(data + 211, &msg.instrumentId4, 4);
        std::memcpy(data + 215, &msg.bidPrice4, 8);
        std::memcpy(data + 223, &msg.bidSize4, 4);
        std::memcpy(data + 227, &msg.offerPrice4, 8);
        std::memcpy(data + 235, &msg.offerSize4, 4);
        std::memset(data + 239, 0, 10); std::memcpy(data + 239, msg.quoteEntryId5.c_str(), std::min(msg.quoteEntryId5.size(), size_t(10)));
        std::memcpy(data + 249, &msg.instrumentId5, 4);
        std::memcpy(data + 253, &msg.bidPrice5, 8);
        std::memcpy(data + 261, &msg.bidSize5, 4);
        std::memcpy(data + 265, &msg.offerPrice5, 8);
        std::memcpy(data + 273, &msg.offerSize5, 4);
        std::memset(data + 277, 0, 10); std::memcpy(data + 277, msg.quoteEntryId6.c_str(), std::min(msg.quoteEntryId6.size(), size_t(10)));
        std::memcpy(data + 287, &msg.instrumentId6, 4);
        std::memcpy(data + 291, &msg.bidPrice6, 8);
        std::memcpy(data + 299, &msg.bidSize6, 4);
        std::memcpy(data + 303, &msg.offerPrice6, 8);
        std::memcpy(data + 311, &msg.offerSize6, 4);
        std::memset(data + 315, 0, 10); std::memcpy(data + 315, msg.quoteEntryId7.c_str(), std::min(msg.quoteEntryId7.size(), size_t(10)));
        std::memcpy(data + 325, &msg.instrumentId7, 4);
        std::memcpy(data + 329, &msg.bidPrice7, 8);
        std::memcpy(data + 337, &msg.bidSize7, 4);
        std::memcpy(data + 341, &msg.offerPrice7, 8);
        std::memcpy(data + 349, &msg.offerSize7, 4);
        std::memset(data + 353, 0, 10); std::memcpy(data + 353, msg.quoteEntryId8.c_str(), std::min(msg.quoteEntryId8.size(), size_t(10)));
        std::memcpy(data + 363, &msg.instrumentId8, 4);
        std::memcpy(data + 367, &msg.bidPrice8, 8);
        std::memcpy(data + 375, &msg.bidSize8, 4);
        std::memcpy(data + 379, &msg.offerPrice8, 8);
        std::memcpy(data + 387, &msg.offerSize8, 4);
        std::memset(data + 391, 0, 10); std::memcpy(data + 391, msg.quoteEntryId9.c_str(), std::min(msg.quoteEntryId9.size(), size_t(10)));
        std::memcpy(data + 401, &msg.instrumentId9, 4);
        std::memcpy(data + 405, &msg.bidPrice9, 8);
        std::memcpy(data + 413, &msg.bidSize9, 4);
        std::memcpy(data + 417, &msg.offerPrice9, 8);
        std::memcpy(data + 425, &msg.offerSize9, 4);
        std::memset(data + 429, 0, 10); std::memcpy(data + 429, msg.quoteEntryId10.c_str(), std::min(msg.quoteEntryId10.size(), size_t(10)));
        std::memcpy(data + 439, &msg.instrumentId10, 4);
        std::memcpy(data + 443, &msg.bidPrice10, 8);
        std::memcpy(data + 451, &msg.bidSize10, 4);
        std::memcpy(data + 455, &msg.offerPrice10, 8);
        std::memcpy(data + 463, &msg.offerSize10, 4);
        std::memset(data + 467, 0, 10); std::memcpy(data + 467, msg.quoteEntryId11.c_str(), std::min(msg.quoteEntryId11.size(), size_t(10)));
        std::memcpy(data + 477, &msg.instrumentId11, 4);
        std::memcpy(data + 481, &msg.bidPrice11, 8);
        std::memcpy(data + 489, &msg.bidSize11, 4);
        std::memcpy(data + 493, &msg.offerPrice11, 8);
        std::memcpy(data + 501, &msg.offerSize11, 4);
        std::memset(data + 505, 0, 10); std::memcpy(data + 505, msg.quoteEntryId12.c_str(), std::min(msg.quoteEntryId12.size(), size_t(10)));
        std::memcpy(data + 515, &msg.instrumentId12, 4);
        std::memcpy(data + 519, &msg.bidPrice12, 8);
        std::memcpy(data + 527, &msg.bidSize12, 4);
        std::memcpy(data + 531, &msg.offerPrice12, 8);
        std::memcpy(data + 539, &msg.offerSize12, 4);
        std::memset(data + 543, 0, 10); std::memcpy(data + 543, msg.quoteEntryId13.c_str(), std::min(msg.quoteEntryId13.size(), size_t(10)));
        std::memcpy(data + 553, &msg.instrumentId13, 4);
        std::memcpy(data + 557, &msg.bidPrice13, 8);
        std::memcpy(data + 565, &msg.bidSize13, 4);
        std::memcpy(data + 569, &msg.offerPrice13, 8);
        std::memcpy(data + 577, &msg.offerSize13, 4);
        std::memset(data + 581, 0, 10); std::memcpy(data + 581, msg.quoteEntryId14.c_str(), std::min(msg.quoteEntryId14.size(), size_t(10)));
        std::memcpy(data + 591, &msg.instrumentId14, 4);
        std::memcpy(data + 595, &msg.bidPrice14, 8);
        std::memcpy(data + 603, &msg.bidSize14, 4);
        std::memcpy(data + 607, &msg.offerPrice14, 8);
        std::memcpy(data + 615, &msg.offerSize14, 4);
        std::memset(data + 619, 0, 10); std::memcpy(data + 619, msg.quoteEntryId15.c_str(), std::min(msg.quoteEntryId15.size(), size_t(10)));
        std::memcpy(data + 629, &msg.instrumentId15, 4);
        std::memcpy(data + 633, &msg.bidPrice15, 8);
        std::memcpy(data + 641, &msg.bidSize15, 4);
        std::memcpy(data + 645, &msg.offerPrice15, 8);
        std::memcpy(data + 653, &msg.offerSize15, 4);
    }
};

} // namespace a5x::trading::native
