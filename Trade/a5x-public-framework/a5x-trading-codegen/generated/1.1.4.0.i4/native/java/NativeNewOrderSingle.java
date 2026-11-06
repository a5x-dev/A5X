package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * New order single
 * Message Type: D
 */
public class NativeNewOrderSingle {

    /** Limit price. This field will be ignored if the Order Type is not Limit (2) or Stop Limit (4) */
    public long limitPrice;

    public java.math.BigDecimal limitPriceAsDecimal() { return java.math.BigDecimal.valueOf(limitPrice, 4); }

    /** Total order quantity */
    public long orderQuantity;

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** 1 = Buy, 2 = Sell */
    public int side;

    /** Entering Trader ID */
    public String enteringTrader = "";

    /** Client specified identifier of the order */
    public String clientOrderId = "";

    /** Identifier of the Trader Account. This is a required field */
    public long account;

    /** Sender Location assigned to the Trader ID/Firm */
    public String senderLocation = "";

    /** Client specified identifier of order relevant to self-execution */
    public String noTradeKey = "";

    /** Stop price/Trigger price. This field will be ignored if the Order Type is not Stop (3) or Stop Limit (4) */
    public long stopPrice;

    public java.math.BigDecimal stopPriceAsDecimal() { return java.math.BigDecimal.valueOf(stopPrice, 4); }

    /** Max Floor. Maximum quantity to be displayed. A negative value should be used if this field is to be ignored */
    public long displayQuantity;

    /** 0 = Do Not Cancel, 1 = Cancel */
    public int cancelOnDisconnect;

    /** 0 = Anonymous, 1 = Named */
    public int anonymity;

    /** 1 = Regular */
    public int orderBook;

    /** Bit 0: Passive Only (0 = No, 1 = Yes) */
    public byte executionInstruction;

    /** 1 = Retail Liquidity Taker, 2 = Waived Priority, 3 = Broker Only */
    public int routingInstruction;

    /** 1 = Market, 2 = Limit, 3 = Stop, 4 = Stop Limit, 5 = Market to Limit, 51 = RLP */
    public int orderType;

    /** 0 = Day, 3 = IOC, 4 = FOK, 5 = OPG, 8 = GTT, 9 = GFA, 10 = ATC */
    public int timeInForce;

    /** Expire time in epoch (seconds precision) */
    public long expireTime;

    /** Free format text field */
    public String memo = "";

    /** Desk ID through which the order is placed from */
    public String deskId = "";

    public static void parse(NativeNewOrderSingle msg, ByteBuffer data) {
        msg.limitPrice = data.getLong();
        msg.orderQuantity = Integer.toUnsignedLong(data.getInt());
        msg.instrumentId = Integer.toUnsignedLong(data.getInt());
        msg.side = Byte.toUnsignedInt(data.get());
        msg.enteringTrader = readAlpha(data, 15);
        msg.clientOrderId = readAlpha(data, 10);
        msg.account = data.getLong();
        msg.senderLocation = readAlpha(data, 10);
        msg.noTradeKey = readAlpha(data, 10);
        msg.stopPrice = data.getLong();
        msg.displayQuantity = Integer.toUnsignedLong(data.getInt());
        msg.cancelOnDisconnect = Byte.toUnsignedInt(data.get());
        msg.anonymity = Byte.toUnsignedInt(data.get());
        msg.orderBook = Byte.toUnsignedInt(data.get());
        msg.executionInstruction = data.get();
        msg.routingInstruction = Byte.toUnsignedInt(data.get());
        msg.orderType = Byte.toUnsignedInt(data.get());
        msg.timeInForce = Byte.toUnsignedInt(data.get());
        msg.expireTime = data.getLong();
        msg.memo = readAlpha(data, 15);
        msg.deskId = readAlpha(data, 15);
    }

    public static void encode(NativeNewOrderSingle msg, ByteBuffer data) {
        data.putLong(msg.limitPrice);
        data.putInt((int) msg.orderQuantity);
        data.putInt((int) msg.instrumentId);
        data.put((byte) msg.side);
        writeAlpha(data, msg.enteringTrader, 15);
        writeAlpha(data, msg.clientOrderId, 10);
        data.putLong(msg.account);
        writeAlpha(data, msg.senderLocation, 10);
        writeAlpha(data, msg.noTradeKey, 10);
        data.putLong(msg.stopPrice);
        data.putInt((int) msg.displayQuantity);
        data.put((byte) msg.cancelOnDisconnect);
        data.put((byte) msg.anonymity);
        data.put((byte) msg.orderBook);
        data.put(msg.executionInstruction);
        data.put((byte) msg.routingInstruction);
        data.put((byte) msg.orderType);
        data.put((byte) msg.timeInForce);
        data.putLong(msg.expireTime);
        writeAlpha(data, msg.memo, 15);
        writeAlpha(data, msg.deskId, 15);
    }

    @Override
    public String toString() {
        return "NativeNewOrderSingle{" +
                "limitPrice=" + limitPrice + ", " +
                "orderQuantity=" + orderQuantity + ", " +
                "instrumentId=" + instrumentId + ", " +
                "side=" + side + ", " +
                "enteringTrader=" + enteringTrader + ", " +
                "clientOrderId=" + clientOrderId + ", " +
                "account=" + account + ", " +
                "senderLocation=" + senderLocation + ", " +
                "noTradeKey=" + noTradeKey + ", " +
                "stopPrice=" + stopPrice + ", " +
                "displayQuantity=" + displayQuantity + ", " +
                "cancelOnDisconnect=" + cancelOnDisconnect + ", " +
                "anonymity=" + anonymity + ", " +
                "orderBook=" + orderBook + ", " +
                "executionInstruction=" + executionInstruction + ", " +
                "routingInstruction=" + routingInstruction + ", " +
                "orderType=" + orderType + ", " +
                "timeInForce=" + timeInForce + ", " +
                "expireTime=" + expireTime + ", " +
                "memo=" + memo + ", " +
                "deskId=" + deskId + '}';
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
