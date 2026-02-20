#pragma once

#include <cstdint>
#include <cstring>

namespace a5x::trading::native {

/**
 * Order cancel request
 * Message Type: F
 */
struct NativeOrderCancelRequest {
    /** Client specified identifier of the request */
    uint8_t clientOrderId[10];

    /** Client specified identifier of the order or quote being cancelled */
    uint8_t origClientOrderId[10];

    /** Server specified identifier of the order being cancelled */
    uint8_t orderId[12];

    /** Numeric Identifier of the instrument */
    uint32_t instrumentId;

    /** 1 = Buy, 2 = Sell */
    uint8_t side;

    /** 1 = Regular */
    uint8_t orderBook;

    /** Sender Location assigned to the Trader ID/Firm */
    uint8_t senderLocation[10];

    static void parse(NativeOrderCancelRequest& msg, const uint8_t* data) {
        std::memcpy(msg.clientOrderId, data + 0, 10);
        std::memcpy(msg.origClientOrderId, data + 10, 10);
        std::memcpy(msg.orderId, data + 20, 12);
        std::memcpy(&msg.instrumentId, data + 32, 4);
        msg.side = data[36];
        msg.orderBook = data[37];
        std::memcpy(msg.senderLocation, data + 38, 10);
    }

    static void encode(const NativeOrderCancelRequest& msg, uint8_t* data) {
        std::memcpy(data + 0, msg.clientOrderId, 10);
        std::memcpy(data + 10, msg.origClientOrderId, 10);
        std::memcpy(data + 20, msg.orderId, 12);
        std::memcpy(data + 32, &msg.instrumentId, 4);
        data[36] = msg.side;
        data[37] = msg.orderBook;
        std::memcpy(data + 38, msg.senderLocation, 10);
    }
};

} // namespace a5x::trading::native
