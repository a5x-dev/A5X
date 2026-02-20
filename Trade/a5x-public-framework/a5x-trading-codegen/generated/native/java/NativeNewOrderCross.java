package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * New Order Cross
 * Message Type: u
 */
public class NativeNewOrderCross {

    /** Identifier of the cross order. Only alphanumeric values allowed */
    public byte[] crossId = new byte[10];

    /** Price of the cross order */
    public int limitPrice;

    public java.math.BigDecimal limitPriceAsDecimal() { return java.math.BigDecimal.valueOf(limitPrice, 4); }

    /** Total order quantity of the cross order */
    public long orderQuantity;

    /** Entering Trader ID */
    public byte[] enteringTrader = new byte[15];

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** Sender Location assigned to the Trader ID/Firm */
    public byte[] senderLocation = new byte[10];

    /** 5 = Internal Cross. Any other value rejected */
    public int crossType;

    /** Client specified identifier of the buy side. Required */
    public byte[] buySideClientOrderId = new byte[10];

    /** Identifier of the buyer's trading account. Required */
    public long buySideAccountId;

    /** Client specified identifier of the sell side. Required */
    public byte[] sellSideClientOrderId = new byte[10];

    /** Identifier of the seller's trading account. Required */
    public long sellSideAccountId;

    /** 2 = Limit. Any other value rejected */
    public int orderType;

    /** 0 = Day. Only DAY TIF allowed for cross orders */
    public int timeInForce;

    /** Desk ID through which the order is placed from */
    public byte[] deskId = new byte[15];

    /** Free format text field. Echoed in reports */
    public byte[] memo = new byte[15];

    public static void parse(NativeNewOrderCross msg, ByteBuffer data) {
        msg.crossId = readAlpha(data, 10);
        msg.limitPrice = data.getInt();
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
        data.putInt(msg.limitPrice);
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
                "crossId=" + new String(crossId).trim() + ", " +
                "limitPrice=" + limitPrice + ", " +
                "orderQuantity=" + orderQuantity + ", " +
                "enteringTrader=" + new String(enteringTrader).trim() + ", " +
                "instrumentId=" + instrumentId + ", " +
                "senderLocation=" + new String(senderLocation).trim() + ", " +
                "crossType=" + crossType + ", " +
                "buySideClientOrderId=" + new String(buySideClientOrderId).trim() + ", " +
                "buySideAccountId=" + buySideAccountId + ", " +
                "sellSideClientOrderId=" + new String(sellSideClientOrderId).trim() + ", " +
                "sellSideAccountId=" + sellSideAccountId + ", " +
                "orderType=" + orderType + ", " +
                "timeInForce=" + timeInForce + ", " +
                "deskId=" + new String(deskId).trim() + ", " +
                "memo=" + new String(memo).trim() + '}';
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
