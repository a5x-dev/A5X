#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * New order single
 * Message Type: D
 */
struct NativeNewOrderSingle {
    /** Limit price */
    int32_t limitPrice;

    double limitPriceAsDecimal() const { return limitPrice / 10000.0; }

    /** Total order quantity */
    uint32_t orderQuantity;

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** 1 = Buy, 2 = Sell */
    uint8_t side;

    /** Entering Trader ID */
    uint8_t enteringTrader[15];

    /** Client specified identifier of the order */
    uint8_t clientOrderId[10];

    /** Identifier of the Trader Account */
    uint64_t account;

    /** Sender Location assigned to the Trader ID/Firm */
    uint8_t senderLocation[10];

    /** Client specified identifier of order relevant to self-execution */
    uint8_t noTradeKey[10];

    /** Stop price/Trigger price */
    int32_t stopPrice;

    double stopPriceAsDecimal() const { return stopPrice / 10000.0; }

    /** Maximum quantity to be displayed */
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
    uint8_t memo[15];

    /** Desk ID through which the order is placed from */
    uint8_t deskId[15];

    static void parse(NativeNewOrderSingle& msg, const uint8_t* data) {
        std::memcpy(&msg.limitPrice, data + 0, 4);
        std::memcpy(&msg.orderQuantity, data + 4, 4);
        std::memcpy(&msg.instrumentId, data + 8, 4);
        msg.side = data[12];
        std::memcpy(msg.enteringTrader, data + 13, 15);
        std::memcpy(msg.clientOrderId, data + 28, 10);
        std::memcpy(&msg.account, data + 38, 8);
        std::memcpy(msg.senderLocation, data + 46, 10);
        std::memcpy(msg.noTradeKey, data + 56, 10);
        std::memcpy(&msg.stopPrice, data + 66, 4);
        std::memcpy(&msg.displayQuantity, data + 70, 4);
        msg.cancelOnDisconnect = data[74];
        msg.anonymity = data[75];
        msg.orderBook = data[76];
        msg.executionInstruction = data[77];
        msg.routingInstruction = data[78];
        msg.orderType = data[79];
        msg.timeInForce = data[80];
        std::memcpy(&msg.expireTime, data + 81, 8);
        std::memcpy(msg.memo, data + 89, 15);
        std::memcpy(msg.deskId, data + 104, 15);
    }

    static void encode(const NativeNewOrderSingle& msg, uint8_t* data) {
        std::memcpy(data + 0, &msg.limitPrice, 4);
        std::memcpy(data + 4, &msg.orderQuantity, 4);
        std::memcpy(data + 8, &msg.instrumentId, 4);
        data[12] = msg.side;
        std::memcpy(data + 13, msg.enteringTrader, 15);
        std::memcpy(data + 28, msg.clientOrderId, 10);
        std::memcpy(data + 38, &msg.account, 8);
        std::memcpy(data + 46, msg.senderLocation, 10);
        std::memcpy(data + 56, msg.noTradeKey, 10);
        std::memcpy(data + 66, &msg.stopPrice, 4);
        std::memcpy(data + 70, &msg.displayQuantity, 4);
        data[74] = msg.cancelOnDisconnect;
        data[75] = msg.anonymity;
        data[76] = msg.orderBook;
        data[77] = msg.executionInstruction;
        data[78] = msg.routingInstruction;
        data[79] = msg.orderType;
        data[80] = msg.timeInForce;
        std::memcpy(data + 81, &msg.expireTime, 8);
        std::memcpy(data + 89, msg.memo, 15);
        std::memcpy(data + 104, msg.deskId, 15);
    }
};

} // namespace a5x::trading::native
