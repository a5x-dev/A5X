package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Order cancel request
 * Message Type: F
 */
public class NativeOrderCancelRequest {

    /** Client specified identifier of the request */
    public String clientOrderId = "";

    /** Client specified identifier of the order or quote being cancelled */
    public String origClientOrderId = "";

    /** Server specified identifier of the order being cancelled */
    public String orderId = "";

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** 1 = Buy, 2 = Sell */
    public int side;

    /** 1 = Regular */
    public int orderBook;

    /** Sender Location assigned to the Trader ID/Firm */
    public String senderLocation = "";

    public static void parse(NativeOrderCancelRequest msg, ByteBuffer data) {
        msg.clientOrderId = readAlpha(data, 10);
        msg.origClientOrderId = readAlpha(data, 10);
        msg.orderId = readAlpha(data, 12);
        msg.instrumentId = Integer.toUnsignedLong(data.getInt());
        msg.side = Byte.toUnsignedInt(data.get());
        msg.orderBook = Byte.toUnsignedInt(data.get());
        msg.senderLocation = readAlpha(data, 10);
    }

    public static void encode(NativeOrderCancelRequest msg, ByteBuffer data) {
        writeAlpha(data, msg.clientOrderId, 10);
        writeAlpha(data, msg.origClientOrderId, 10);
        writeAlpha(data, msg.orderId, 12);
        data.putInt((int) msg.instrumentId);
        data.put((byte) msg.side);
        data.put((byte) msg.orderBook);
        writeAlpha(data, msg.senderLocation, 10);
    }

    @Override
    public String toString() {
        return "NativeOrderCancelRequest{" +
                "clientOrderId=" + clientOrderId + ", " +
                "origClientOrderId=" + origClientOrderId + ", " +
                "orderId=" + orderId + ", " +
                "instrumentId=" + instrumentId + ", " +
                "side=" + side + ", " +
                "orderBook=" + orderBook + ", " +
                "senderLocation=" + senderLocation + '}';
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
