package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Order Cancel Reject
 * Message Type: 9
 */
public class NativeOrderCancelReject {

    /** Identity of the matching partition */
    public int partitionId;

    /** Message sequence number of the matching partition */
    public long sequenceNumber;

    /** Client specified identifier of the rejected cancel or modification request */
    public String clientOrderId = "";

    /** Code specifying the reason for the reject */
    public int rejectCode;

    /** 1 = Regular */
    public int orderBook;

    /** Time the message was generated in epoch format */
    public long transactTime;

    /** Sender Location assigned to the Trader ID/Firm */
    public String senderLocation = "";

    public static void parse(NativeOrderCancelReject msg, ByteBuffer data) {
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.sequenceNumber = data.getLong();
        msg.clientOrderId = readAlpha(data, 10);
        msg.rejectCode = data.getInt();
        msg.orderBook = Byte.toUnsignedInt(data.get());
        msg.transactTime = data.getLong();
        msg.senderLocation = readAlpha(data, 10);
    }

    public static void encode(NativeOrderCancelReject msg, ByteBuffer data) {
        data.put((byte) msg.partitionId);
        data.putLong(msg.sequenceNumber);
        writeAlpha(data, msg.clientOrderId, 10);
        data.putInt(msg.rejectCode);
        data.put((byte) msg.orderBook);
        data.putLong(msg.transactTime);
        writeAlpha(data, msg.senderLocation, 10);
    }

    @Override
    public String toString() {
        return "NativeOrderCancelReject{" +
                "partitionId=" + partitionId + ", " +
                "sequenceNumber=" + sequenceNumber + ", " +
                "clientOrderId=" + clientOrderId + ", " +
                "rejectCode=" + rejectCode + ", " +
                "orderBook=" + orderBook + ", " +
                "transactTime=" + transactTime + ", " +
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
