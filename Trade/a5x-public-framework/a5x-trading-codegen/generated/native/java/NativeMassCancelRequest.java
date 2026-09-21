package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Mass Cancel Request
 * Message Type: q
 */
public class NativeMassCancelRequest {

    /** Client specified identifier of the rejected message if available */
    public String clientOrderId = "";

    /** 3 = Firm Interest for Instrument, 4 = Firm Interest for Segment, 7 = All Interest for Client, 8 = All Interest for Firm, 9 = Client Interest for Instrument, 14 = Client Interest for Underlying, 15 = Client Interest for Segment, 22 = Firm Interest for Underlying */
    public int massCancelType;

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** Identifier of the segment */
    public String segment = "";

    /** 0 = Order, 3 = Quote */
    public int interestType;

    /** 1 = Regular */
    public int orderBook;

    /** Sender Location assigned to the Trader ID/Firm */
    public String senderLocation = "";

    /** Entering Trader ID */
    public String enteringTrader = "";

    /** Desk ID through which the request is placed from */
    public String deskId = "";

    public static void parse(NativeMassCancelRequest msg, ByteBuffer data) {
        msg.clientOrderId = readAlpha(data, 10);
        msg.massCancelType = Byte.toUnsignedInt(data.get());
        msg.instrumentId = Integer.toUnsignedLong(data.getInt());
        msg.segment = readAlpha(data, 10);
        msg.interestType = Byte.toUnsignedInt(data.get());
        msg.orderBook = Byte.toUnsignedInt(data.get());
        msg.senderLocation = readAlpha(data, 10);
        msg.enteringTrader = readAlpha(data, 15);
        msg.deskId = readAlpha(data, 15);
    }

    public static void encode(NativeMassCancelRequest msg, ByteBuffer data) {
        writeAlpha(data, msg.clientOrderId, 10);
        data.put((byte) msg.massCancelType);
        data.putInt((int) msg.instrumentId);
        writeAlpha(data, msg.segment, 10);
        data.put((byte) msg.interestType);
        data.put((byte) msg.orderBook);
        writeAlpha(data, msg.senderLocation, 10);
        writeAlpha(data, msg.enteringTrader, 15);
        writeAlpha(data, msg.deskId, 15);
    }

    @Override
    public String toString() {
        return "NativeMassCancelRequest{" +
                "clientOrderId=" + clientOrderId + ", " +
                "massCancelType=" + massCancelType + ", " +
                "instrumentId=" + instrumentId + ", " +
                "segment=" + segment + ", " +
                "interestType=" + interestType + ", " +
                "orderBook=" + orderBook + ", " +
                "senderLocation=" + senderLocation + ", " +
                "enteringTrader=" + enteringTrader + ", " +
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
