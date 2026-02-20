package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Mass Quote
 * Message Type: i
 */
public class NativeMassQuote {

    /** Client specified identifier of the message */
    public byte[] massQuoteId = new byte[10];

    /** Identity of the matching partition */
    public int partitionId;

    /** 0 = Do Not Cancel, 1 = Cancel */
    public int cancelOnDisconnect;

    /** 0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0) */
    public int anonymity;

    /** Identifier of the trading account. Required field */
    public long account;

    /** Sender Location assigned to the Trader ID/Firm */
    public byte[] senderLocation = new byte[10];

    /** Entering Trader ID */
    public byte[] enteringTrader = new byte[15];

    /** Client specified identifier of order relevant to self-execution */
    public byte[] noTradeKey = new byte[10];

    /** Free format text field. Echoed in reports */
    public byte[] memo = new byte[15];

    /** Desk ID through which the order is placed from */
    public byte[] deskId = new byte[15];

    /** Number of quote entries in the mass quote */
    public int totalQuoteEntries;

    public static void parse(NativeMassQuote msg, ByteBuffer data) {
        msg.massQuoteId = readAlpha(data, 10);
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.cancelOnDisconnect = Byte.toUnsignedInt(data.get());
        msg.anonymity = Byte.toUnsignedInt(data.get());
        msg.account = data.getLong();
        msg.senderLocation = readAlpha(data, 10);
        msg.enteringTrader = readAlpha(data, 15);
        msg.noTradeKey = readAlpha(data, 10);
        msg.memo = readAlpha(data, 15);
        msg.deskId = readAlpha(data, 15);
        msg.totalQuoteEntries = Short.toUnsignedInt(data.getShort());
    }

    public static void encode(NativeMassQuote msg, ByteBuffer data) {
        writeAlpha(data, msg.massQuoteId, 10);
        data.put((byte) msg.partitionId);
        data.put((byte) msg.cancelOnDisconnect);
        data.put((byte) msg.anonymity);
        data.putLong(msg.account);
        writeAlpha(data, msg.senderLocation, 10);
        writeAlpha(data, msg.enteringTrader, 15);
        writeAlpha(data, msg.noTradeKey, 10);
        writeAlpha(data, msg.memo, 15);
        writeAlpha(data, msg.deskId, 15);
        data.putShort((short) msg.totalQuoteEntries);
    }

    @Override
    public String toString() {
        return "NativeMassQuote{" +
                "massQuoteId=" + new String(massQuoteId).trim() + ", " +
                "partitionId=" + partitionId + ", " +
                "cancelOnDisconnect=" + cancelOnDisconnect + ", " +
                "anonymity=" + anonymity + ", " +
                "account=" + account + ", " +
                "senderLocation=" + new String(senderLocation).trim() + ", " +
                "enteringTrader=" + new String(enteringTrader).trim() + ", " +
                "noTradeKey=" + new String(noTradeKey).trim() + ", " +
                "memo=" + new String(memo).trim() + ", " +
                "deskId=" + new String(deskId).trim() + ", " +
                "totalQuoteEntries=" + totalQuoteEntries + '}';
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
