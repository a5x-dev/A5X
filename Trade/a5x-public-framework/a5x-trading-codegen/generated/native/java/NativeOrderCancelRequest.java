package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Order cancel request
 * Message Type: F
 */
public class NativeOrderCancelRequest {

    /** Client specified identifier of the request */
    public byte[] clientOrderId = new byte[10];

    /** Client specified identifier of the order or quote being cancelled */
    public byte[] origClientOrderId = new byte[10];

    /** Server specified identifier of the order being cancelled */
    public byte[] orderId = new byte[12];

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** 1 = Buy, 2 = Sell */
    public int side;

    /** 1 = Regular */
    public int orderBook;

    /** Sender Location assigned to the Trader ID/Firm */
    public byte[] senderLocation = new byte[10];

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
                "clientOrderId=" + new String(clientOrderId).trim() + ", " +
                "origClientOrderId=" + new String(origClientOrderId).trim() + ", " +
                "orderId=" + new String(orderId).trim() + ", " +
                "instrumentId=" + instrumentId + ", " +
                "side=" + side + ", " +
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
