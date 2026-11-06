#pragma once

#include <algorithm>
#include <cstdint>
#include <cstring>
#include <string>

namespace a5x::trading::native {

/**
 * New order single
 * Message Type: D
 */
struct NativeNewOrderSingle {
    /** Limit price. This field will be ignored if the Order Type is not Limit (2) or Stop Limit (4) */
    int64_t limitPrice;

    /** Total order quantity */
    uint32_t orderQuantity;

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** 1 = Buy, 2 = Sell */
    uint8_t side;

    /** Entering Trader ID */
    std::string enteringTrader;

    /** Client specified identifier of the order */
    std::string clientOrderId;

    /** Identifier of the Trader Account. This is a required field */
    uint64_t account;

    /** Sender Location assigned to the Trader ID/Firm */
    std::string senderLocation;

    /** Client specified identifier of order relevant to self-execution */
    std::string noTradeKey;

    /** Stop price/Trigger price. This field will be ignored if the Order Type is not Stop (3) or Stop Limit (4) */
    int64_t stopPrice;

    /** Max Floor. Maximum quantity to be displayed. A negative value should be used if this field is to be ignored */
    uint32_t displayQuantity;

    /** 0 = Do Not Cancel, 1 = Cancel */
    uint8_t cancelOnDisconnect;

    /** 0 = Anonymous, 1 = Named */
    uint8_t anonymity;

    /** 1 = Regular */
    uint8_t orderBook;

    /** Bit 0: Passive Only (0 = No, 1 = Yes) */
    uint8_t executionInstruction;

    /** 1 = Retail Liquidity Taker, 2 = Waived Priority, 3 = Broker Only */
    uint8_t routingInstruction;

    /** 1 = Market, 2 = Limit, 3 = Stop, 4 = Stop Limit, 5 = Market to Limit, 51 = RLP */
    uint8_t orderType;

    /** 0 = Day, 3 = IOC, 4 = FOK, 5 = OPG, 8 = GTT, 9 = GFA, 10 = ATC */
    uint8_t timeInForce;

    /** Expire time in epoch (seconds precision) */
    uint64_t expireTime;

    /** Free format text field */
    std::string memo;

    /** Desk ID through which the order is placed from */
    std::string deskId;

    static void parse(NativeNewOrderSingle& msg, const uint8_t* data) {
        std::memcpy(&msg.limitPrice, data + 0, 8);
        std::memcpy(&msg.orderQuantity, data + 8, 4);
        std::memcpy(&msg.instrumentId, data + 12, 4);
        msg.side = data[16];
        msg.enteringTrader.assign(reinterpret_cast<const char*>(data + 17), strnlen(reinterpret_cast<const char*>(data + 17), 15));
        msg.clientOrderId.assign(reinterpret_cast<const char*>(data + 32), strnlen(reinterpret_cast<const char*>(data + 32), 10));
        std::memcpy(&msg.account, data + 42, 8);
        msg.senderLocation.assign(reinterpret_cast<const char*>(data + 50), strnlen(reinterpret_cast<const char*>(data + 50), 10));
        msg.noTradeKey.assign(reinterpret_cast<const char*>(data + 60), strnlen(reinterpret_cast<const char*>(data + 60), 10));
        std::memcpy(&msg.stopPrice, data + 70, 8);
        std::memcpy(&msg.displayQuantity, data + 78, 4);
        msg.cancelOnDisconnect = data[82];
        msg.anonymity = data[83];
        msg.orderBook = data[84];
        msg.executionInstruction = data[85];
        msg.routingInstruction = data[86];
        msg.orderType = data[87];
        msg.timeInForce = data[88];
        std::memcpy(&msg.expireTime, data + 89, 8);
        msg.memo.assign(reinterpret_cast<const char*>(data + 97), strnlen(reinterpret_cast<const char*>(data + 97), 15));
        msg.deskId.assign(reinterpret_cast<const char*>(data + 112), strnlen(reinterpret_cast<const char*>(data + 112), 15));
    }

    static void encode(const NativeNewOrderSingle& msg, uint8_t* data) {
        std::memcpy(data + 0, &msg.limitPrice, 8);
        std::memcpy(data + 8, &msg.orderQuantity, 4);
        std::memcpy(data + 12, &msg.instrumentId, 4);
        data[16] = msg.side;
        std::memset(data + 17, 0, 15); std::memcpy(data + 17, msg.enteringTrader.c_str(), std::min(msg.enteringTrader.size(), size_t(15)));
        std::memset(data + 32, 0, 10); std::memcpy(data + 32, msg.clientOrderId.c_str(), std::min(msg.clientOrderId.size(), size_t(10)));
        std::memcpy(data + 42, &msg.account, 8);
        std::memset(data + 50, 0, 10); std::memcpy(data + 50, msg.senderLocation.c_str(), std::min(msg.senderLocation.size(), size_t(10)));
        std::memset(data + 60, 0, 10); std::memcpy(data + 60, msg.noTradeKey.c_str(), std::min(msg.noTradeKey.size(), size_t(10)));
        std::memcpy(data + 70, &msg.stopPrice, 8);
        std::memcpy(data + 78, &msg.displayQuantity, 4);
        data[82] = msg.cancelOnDisconnect;
        data[83] = msg.anonymity;
        data[84] = msg.orderBook;
        data[85] = msg.executionInstruction;
        data[86] = msg.routingInstruction;
        data[87] = msg.orderType;
        data[88] = msg.timeInForce;
        std::memcpy(data + 89, &msg.expireTime, 8);
        std::memset(data + 97, 0, 15); std::memcpy(data + 97, msg.memo.c_str(), std::min(msg.memo.size(), size_t(15)));
        std::memset(data + 112, 0, 15); std::memcpy(data + 112, msg.deskId.c_str(), std::min(msg.deskId.size(), size_t(15)));
    }
};

} // namespace a5x::trading::native
