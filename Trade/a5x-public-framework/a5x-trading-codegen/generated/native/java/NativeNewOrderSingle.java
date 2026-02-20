package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * New order single
 * Message Type: D
 */
public class NativeNewOrderSingle {

    /** Limit price */
    public int limitPrice;

    public java.math.BigDecimal limitPriceAsDecimal() { return java.math.BigDecimal.valueOf(limitPrice, 4); }

    /** Total order quantity */
    public long orderQuantity;

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** 1 = Buy, 2 = Sell */
    public int side;

    /** Entering Trader ID */
    public byte[] enteringTrader = new byte[15];

    /** Client specified identifier of the order */
    public byte[] clientOrderId = new byte[10];

    /** Identifier of the Trader Account */
    public long account;

    /** Sender Location assigned to the Trader ID/Firm */
    public byte[] senderLocation = new byte[10];

    /** Client specified identifier of order relevant to self-execution */
    public byte[] noTradeKey = new byte[10];

    /** Stop price/Trigger price */
    public int stopPrice;

    public java.math.BigDecimal stopPriceAsDecimal() { return java.math.BigDecimal.valueOf(stopPrice, 4); }

    /** Maximum quantity to be displayed */
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
    public byte[] memo = new byte[15];

    /** Desk ID through which the order is placed from */
    public byte[] deskId = new byte[15];

    public static void parse(NativeNewOrderSingle msg, ByteBuffer data) {
        msg.limitPrice = data.getInt();
        msg.orderQuantity = Integer.toUnsignedLong(data.getInt());
        msg.instrumentId = Integer.toUnsignedLong(data.getInt());
        msg.side = Byte.toUnsignedInt(data.get());
        msg.enteringTrader = readAlpha(data, 15);
        msg.clientOrderId = readAlpha(data, 10);
        msg.account = data.getLong();
        msg.senderLocation = readAlpha(data, 10);
        msg.noTradeKey = readAlpha(data, 10);
        msg.stopPrice = data.getInt();
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
        data.putInt(msg.limitPrice);
        data.putInt((int) msg.orderQuantity);
        data.putInt((int) msg.instrumentId);
        data.put((byte) msg.side);
        writeAlpha(data, msg.enteringTrader, 15);
        writeAlpha(data, msg.clientOrderId, 10);
        data.putLong(msg.account);
        writeAlpha(data, msg.senderLocation, 10);
        writeAlpha(data, msg.noTradeKey, 10);
        data.putInt(msg.stopPrice);
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
                "enteringTrader=" + new String(enteringTrader).trim() + ", " +
                "clientOrderId=" + new String(clientOrderId).trim() + ", " +
                "account=" + account + ", " +
                "senderLocation=" + new String(senderLocation).trim() + ", " +
                "noTradeKey=" + new String(noTradeKey).trim() + ", " +
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
                "memo=" + new String(memo).trim() + ", " +
                "deskId=" + new String(deskId).trim() + '}';
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
