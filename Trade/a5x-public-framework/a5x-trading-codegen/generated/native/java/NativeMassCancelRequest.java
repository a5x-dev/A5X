package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Mass Cancel Request
 * Message Type: q
 */
public class NativeMassCancelRequest {

    /** Client specified identifier of the rejected message if available */
    public byte[] clientOrderId = new byte[10];

    /** 3 = Firm Interest for Instrument, 4 = Firm Interest for Segment, 7 = All Interest for Client, 8 = All Interest for Firm, 9 = Client Interest for Instrument, 14 = Client Interest for Underlying, 15 = Client Interest for Segment, 22 = Firm Interest for Underlying */
    public int massCancelType;

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** Identifier of the segment */
    public byte[] segment = new byte[10];

    /** 0 = Order, 3 = Quote */
    public int interestType;

    /** 1 = Regular */
    public int orderBook;

    /** Sender Location assigned to the Trader ID/Firm */
    public byte[] senderLocation = new byte[10];

    public static void parse(NativeMassCancelRequest msg, ByteBuffer data) {
        msg.clientOrderId = readAlpha(data, 10);
        msg.massCancelType = Byte.toUnsignedInt(data.get());
        msg.instrumentId = Integer.toUnsignedLong(data.getInt());
        msg.segment = readAlpha(data, 10);
        msg.interestType = Byte.toUnsignedInt(data.get());
        msg.orderBook = Byte.toUnsignedInt(data.get());
        msg.senderLocation = readAlpha(data, 10);
    }

    public static void encode(NativeMassCancelRequest msg, ByteBuffer data) {
        writeAlpha(data, msg.clientOrderId, 10);
        data.put((byte) msg.massCancelType);
        data.putInt((int) msg.instrumentId);
        writeAlpha(data, msg.segment, 10);
        data.put((byte) msg.interestType);
        data.put((byte) msg.orderBook);
        writeAlpha(data, msg.senderLocation, 10);
    }

    @Override
    public String toString() {
        return "NativeMassCancelRequest{" +
                "clientOrderId=" + new String(clientOrderId).trim() + ", " +
                "massCancelType=" + massCancelType + ", " +
                "instrumentId=" + instrumentId + ", " +
                "segment=" + new String(segment).trim() + ", " +
                "interestType=" + interestType + ", " +
                "orderBook=" + orderBook + ", " +
                "senderLocation=" + new String(senderLocation).trim() + '}';
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
