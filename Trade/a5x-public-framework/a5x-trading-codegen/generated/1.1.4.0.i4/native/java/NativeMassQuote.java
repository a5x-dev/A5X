package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Mass Quote
 * Message Type: i
 */
public class NativeMassQuote {

    /** Client specified identifier of the message */
    public String massQuoteId = "";

    /** Identity of the matching partition */
    public int partitionId;

    /** 0 = Do Not Cancel, 1 = Cancel */
    public int cancelOnDisconnect;

    /** 0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0) */
    public int anonymity;

    /** Identifier of the trading account. Required field */
    public long account;

    /** Sender Location assigned to the Trader ID/Firm */
    public String senderLocation = "";

    /** Entering Trader ID */
    public String enteringTrader = "";

    /** Client specified identifier of order relevant to self-execution */
    public String noTradeKey = "";

    /** Free format text field. Echoed in reports */
    public String memo = "";

    /** Desk ID through which the order is placed from */
    public String deskId = "";

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
                "massQuoteId=" + massQuoteId + ", " +
                "partitionId=" + partitionId + ", " +
                "cancelOnDisconnect=" + cancelOnDisconnect + ", " +
                "anonymity=" + anonymity + ", " +
                "account=" + account + ", " +
                "senderLocation=" + senderLocation + ", " +
                "enteringTrader=" + enteringTrader + ", " +
                "noTradeKey=" + noTradeKey + ", " +
                "memo=" + memo + ", " +
                "deskId=" + deskId + ", " +
                "totalQuoteEntries=" + totalQuoteEntries + '}';
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
