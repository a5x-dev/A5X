package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Order Replace Request
 * Message Type: G
 */
public class NativeOrderReplaceRequest {

    /** Limit price. Ignored if Order Type is not Limit (2) or Stop Limit (4) */
    public int limitPrice;

    public java.math.BigDecimal limitPriceAsDecimal() { return java.math.BigDecimal.valueOf(limitPrice, 4); }

    /** Total order quantity */
    public long orderQuantity;

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** 1 = Buy, 2 = Sell */
    public int side;

    /** Client specified identifier of the order */
    public byte[] clientOrderId = new byte[10];

    /** Client specified identifier of the order being modified */
    public byte[] origClientOrderId = new byte[10];

    /** Server specified identifier of the order being cancelled */
    public byte[] orderId = new byte[12];

    /** Identifier of the Trader Account. Required field */
    public long account;

    /** Sender Location assigned to the Trader ID/Firm */
    public byte[] senderLocation = new byte[10];

    /** Stop price/Trigger price. Ignored if Order Type is not Stop (3) or Stop Limit (4) */
    public int stopPrice;

    public java.math.BigDecimal stopPriceAsDecimal() { return java.math.BigDecimal.valueOf(stopPrice, 4); }

    /** Maximum quantity to be displayed. Negative value to ignore */
    public long displayQuantity;

    /** 1 = Regular */
    public int orderBook;

    /** Bit 0: Passive Only (0 = No, 1 = Yes) */
    public byte executionInstruction;

    /** 1 = Market, 2 = Limit, 3 = Stop, 4 = Stop Limit, 5 = Market to Limit, 51 = RLP */
    public int orderType;

    /** 0 = Day, 3 = IOC, 4 = FOK, 5 = OPG, 8 = GTT, 9 = GFA, 10 = ATC */
    public int timeInForce;

    /** Expire time in epoch (seconds precision). Ignored if Time in Force is not GTT (8) */
    public long expireTime;

    public static void parse(NativeOrderReplaceRequest msg, ByteBuffer data) {
        msg.limitPrice = data.getInt();
        msg.orderQuantity = Integer.toUnsignedLong(data.getInt());
        msg.instrumentId = Integer.toUnsignedLong(data.getInt());
        msg.side = Byte.toUnsignedInt(data.get());
        msg.clientOrderId = readAlpha(data, 10);
        msg.origClientOrderId = readAlpha(data, 10);
        msg.orderId = readAlpha(data, 12);
        msg.account = data.getLong();
        msg.senderLocation = readAlpha(data, 10);
        msg.stopPrice = data.getInt();
        msg.displayQuantity = Integer.toUnsignedLong(data.getInt());
        msg.orderBook = Byte.toUnsignedInt(data.get());
        msg.executionInstruction = data.get();
        msg.orderType = Byte.toUnsignedInt(data.get());
        msg.timeInForce = Byte.toUnsignedInt(data.get());
        msg.expireTime = data.getLong();
    }

    public static void encode(NativeOrderReplaceRequest msg, ByteBuffer data) {
        data.putInt(msg.limitPrice);
        data.putInt((int) msg.orderQuantity);
        data.putInt((int) msg.instrumentId);
        data.put((byte) msg.side);
        writeAlpha(data, msg.clientOrderId, 10);
        writeAlpha(data, msg.origClientOrderId, 10);
        writeAlpha(data, msg.orderId, 12);
        data.putLong(msg.account);
        writeAlpha(data, msg.senderLocation, 10);
        data.putInt(msg.stopPrice);
        data.putInt((int) msg.displayQuantity);
        data.put((byte) msg.orderBook);
        data.put(msg.executionInstruction);
        data.put((byte) msg.orderType);
        data.put((byte) msg.timeInForce);
        data.putLong(msg.expireTime);
    }

    @Override
    public String toString() {
        return "NativeOrderReplaceRequest{" +
                "limitPrice=" + limitPrice + ", " +
                "orderQuantity=" + orderQuantity + ", " +
                "instrumentId=" + instrumentId + ", " +
                "side=" + side + ", " +
                "clientOrderId=" + new String(clientOrderId).trim() + ", " +
                "origClientOrderId=" + new String(origClientOrderId).trim() + ", " +
                "orderId=" + new String(orderId).trim() + ", " +
                "account=" + account + ", " +
                "senderLocation=" + new String(senderLocation).trim() + ", " +
                "stopPrice=" + stopPrice + ", " +
                "displayQuantity=" + displayQuantity + ", " +
                "orderBook=" + orderBook + ", " +
                "executionInstruction=" + executionInstruction + ", " +
                "orderType=" + orderType + ", " +
                "timeInForce=" + timeInForce + ", " +
                "expireTime=" + expireTime + '}';
    }

    private static byte[] readAlpha(ByteBuffer data, int length) {
        byte[] result = new byte[length];
        data.get(result);
        return result;
    }

    private static void writeAlpha(ByteBuffer data, byte[] value, int length) {
        byte[] temp = new byte[length];
        System.arraycopy(value, 0, temp, 0, Math.min(value.length, length));
        data.put(temp);
    }
}
