package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * New Order Cross
 * Message Type: C
 */
public class NativeNewOrderCross {

    /** Identifier of the cross order. Only alphanumeric values allowed */
    public String crossId = "";

    /** Price of the cross order */
    public long limitPrice;

    public java.math.BigDecimal limitPriceAsDecimal() { return java.math.BigDecimal.valueOf(limitPrice, 4); }

    /** Total order quantity of the cross order */
    public long orderQuantity;

    /** Entering Trader ID */
    public String enteringTrader = "";

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** Sender Location assigned to the Trader ID/Firm */
    public String senderLocation = "";

    /** 5 = Internal Cross. Any other value rejected */
    public int crossType;

    /** Client specified identifier of the buy side. Required */
    public String buySideClientOrderId = "";

    /** Identifier of the buyer's trading account. Required */
    public long buySideAccountId;

    /** Client specified identifier of the sell side. Required */
    public String sellSideClientOrderId = "";

    /** Identifier of the seller's trading account. Required */
    public long sellSideAccountId;

    /** 2 = Limit. Any other value rejected */
    public int orderType;

    /** 0 = Day. Only DAY TIF allowed for cross orders */
    public int timeInForce;

    /** Desk ID through which the order is placed from */
    public String deskId = "";

    /** Free format text field. Echoed in reports */
    public String memo = "";

    public static void parse(NativeNewOrderCross msg, ByteBuffer data) {
        msg.crossId = readAlpha(data, 10);
        msg.limitPrice = data.getLong();
        msg.orderQuantity = Integer.toUnsignedLong(data.getInt());
        msg.enteringTrader = readAlpha(data, 15);
        msg.instrumentId = Integer.toUnsignedLong(data.getInt());
        msg.senderLocation = readAlpha(data, 10);
        msg.crossType = Byte.toUnsignedInt(data.get());
        msg.buySideClientOrderId = readAlpha(data, 10);
        msg.buySideAccountId = data.getLong();
        msg.sellSideClientOrderId = readAlpha(data, 10);
        msg.sellSideAccountId = data.getLong();
        msg.orderType = Byte.toUnsignedInt(data.get());
        msg.timeInForce = Byte.toUnsignedInt(data.get());
        msg.deskId = readAlpha(data, 15);
        msg.memo = readAlpha(data, 15);
    }

    public static void encode(NativeNewOrderCross msg, ByteBuffer data) {
        writeAlpha(data, msg.crossId, 10);
        data.putLong(msg.limitPrice);
        data.putInt((int) msg.orderQuantity);
        writeAlpha(data, msg.enteringTrader, 15);
        data.putInt((int) msg.instrumentId);
        writeAlpha(data, msg.senderLocation, 10);
        data.put((byte) msg.crossType);
        writeAlpha(data, msg.buySideClientOrderId, 10);
        data.putLong(msg.buySideAccountId);
        writeAlpha(data, msg.sellSideClientOrderId, 10);
        data.putLong(msg.sellSideAccountId);
        data.put((byte) msg.orderType);
        data.put((byte) msg.timeInForce);
        writeAlpha(data, msg.deskId, 15);
        writeAlpha(data, msg.memo, 15);
    }

    @Override
    public String toString() {
        return "NativeNewOrderCross{" +
                "crossId=" + crossId + ", " +
                "limitPrice=" + limitPrice + ", " +
                "orderQuantity=" + orderQuantity + ", " +
                "enteringTrader=" + enteringTrader + ", " +
                "instrumentId=" + instrumentId + ", " +
                "senderLocation=" + senderLocation + ", " +
                "crossType=" + crossType + ", " +
                "buySideClientOrderId=" + buySideClientOrderId + ", " +
                "buySideAccountId=" + buySideAccountId + ", " +
                "sellSideClientOrderId=" + sellSideClientOrderId + ", " +
                "sellSideAccountId=" + sellSideAccountId + ", " +
                "orderType=" + orderType + ", " +
                "timeInForce=" + timeInForce + ", " +
                "deskId=" + deskId + ", " +
                "memo=" + memo + '}';
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
