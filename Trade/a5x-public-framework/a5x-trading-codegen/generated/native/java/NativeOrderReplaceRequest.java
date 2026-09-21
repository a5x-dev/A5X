package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Order Replace Request
 * Message Type: G
 */
public class NativeOrderReplaceRequest {

    /** Limit price. This field will be ignored if the Order Type is not Limit (2) or Stop Limit (4) */
    public long limitPrice;

    public java.math.BigDecimal limitPriceAsDecimal() { return java.math.BigDecimal.valueOf(limitPrice, 4); }

    /** Total order quantity */
    public long orderQuantity;

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** 1 = Buy, 2 = Sell */
    public int side;

    /** Client specified identifier of the order */
    public String clientOrderId = "";

    /** Client specified identifier of the order being modified */
    public String origClientOrderId = "";

    /** Identifier of the Trader Account. This is a required field */
    public long account;

    /** Sender Location assigned to the Trader ID/Firm */
    public String senderLocation = "";

    /** Stop price/Trigger price. This field will be ignored if the Order Type is not Stop (3) or Stop Limit (4) */
    public long stopPrice;

    public java.math.BigDecimal stopPriceAsDecimal() { return java.math.BigDecimal.valueOf(stopPrice, 4); }

    /** Maximum quantity to be displayed. A negative value should be used if this field is to be ignored */
    public long displayQuantity;

    /** 1 = Regular */
    public int orderBook;

    /** 1 = Market, 2 = Limit, 3 = Stop, 4 = Stop Limit, 5 = Market to Limit, 51 = RLP */
    public int orderType;

    /** 0 = Day, 3 = IOC, 4 = FOK, 5 = OPG, 8 = GTT, 9 = GFA, 10 = ATC */
    public int timeInForce;

    /** Expire time in epoch (seconds precision). Ignored if Time in Force is not GTT (8) */
    public long expireTime;

    public static void parse(NativeOrderReplaceRequest msg, ByteBuffer data) {
        msg.limitPrice = data.getLong();
        msg.orderQuantity = Integer.toUnsignedLong(data.getInt());
        msg.instrumentId = Integer.toUnsignedLong(data.getInt());
        msg.side = Byte.toUnsignedInt(data.get());
        msg.clientOrderId = readAlpha(data, 10);
        msg.origClientOrderId = readAlpha(data, 10);
        msg.account = data.getLong();
        msg.senderLocation = readAlpha(data, 10);
        msg.stopPrice = data.getLong();
        msg.displayQuantity = Integer.toUnsignedLong(data.getInt());
        msg.orderBook = Byte.toUnsignedInt(data.get());
        msg.orderType = Byte.toUnsignedInt(data.get());
        msg.timeInForce = Byte.toUnsignedInt(data.get());
        msg.expireTime = data.getLong();
    }

    public static void encode(NativeOrderReplaceRequest msg, ByteBuffer data) {
        data.putLong(msg.limitPrice);
        data.putInt((int) msg.orderQuantity);
        data.putInt((int) msg.instrumentId);
        data.put((byte) msg.side);
        writeAlpha(data, msg.clientOrderId, 10);
        writeAlpha(data, msg.origClientOrderId, 10);
        data.putLong(msg.account);
        writeAlpha(data, msg.senderLocation, 10);
        data.putLong(msg.stopPrice);
        data.putInt((int) msg.displayQuantity);
        data.put((byte) msg.orderBook);
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
                "clientOrderId=" + clientOrderId + ", " +
                "origClientOrderId=" + origClientOrderId + ", " +
                "account=" + account + ", " +
                "senderLocation=" + senderLocation + ", " +
                "stopPrice=" + stopPrice + ", " +
                "displayQuantity=" + displayQuantity + ", " +
                "orderBook=" + orderBook + ", " +
                "orderType=" + orderType + ", " +
                "timeInForce=" + timeInForce + ", " +
                "expireTime=" + expireTime + '}';
    }

    private static String readAlpha(ByteBuffer data, int length) {
        byte[] raw = new byte[length];
        data.get(raw);
        int end = 0;
        while (end < length && raw[end] != 0) {
            end++;
        }
        return new String(raw, 0, end, StandardCharsets.US_ASCII);
    }

    private static void writeAlpha(ByteBuffer data, String value, int length) {
        byte[] out = new byte[length];
        if (value != null) {
            byte[] src = value.getBytes(StandardCharsets.US_ASCII);
            System.arraycopy(src, 0, out, 0, Math.min(src.length, length));
        }
        data.put(out);
    }
}
