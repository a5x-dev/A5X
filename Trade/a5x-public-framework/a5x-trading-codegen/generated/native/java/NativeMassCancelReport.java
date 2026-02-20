package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Mass Cancel Report
 * Message Type: r
 */
public class NativeMassCancelReport {

    /** Identity of the matching partition */
    public int partitionId;

    /** Message sequence number of the matching partition */
    public long sequenceNumber;

    /** Client specified identifier of the rejected message if available */
    public byte[] clientOrderId = new byte[10];

    /** 0 = Rejected, 7 = Accepted */
    public int status;

    /** Code specifying the reason for the reject. Ignore if Status is not Rejected (0) */
    public long rejectCode;

    /** 1 = Regular */
    public int orderBook;

    public static void parse(NativeMassCancelReport msg, ByteBuffer data) {
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.sequenceNumber = data.getLong();
        msg.clientOrderId = readAlpha(data, 10);
        msg.status = Byte.toUnsignedInt(data.get());
        msg.rejectCode = Integer.toUnsignedLong(data.getInt());
        msg.orderBook = Byte.toUnsignedInt(data.get());
    }

    public static void encode(NativeMassCancelReport msg, ByteBuffer data) {
        data.put((byte) msg.partitionId);
        data.putLong(msg.sequenceNumber);
        writeAlpha(data, msg.clientOrderId, 10);
        data.put((byte) msg.status);
        data.putInt((int) msg.rejectCode);
        data.put((byte) msg.orderBook);
    }

    @Override
    public String toString() {
        return "NativeMassCancelReport{" +
                "partitionId=" + partitionId + ", " +
                "sequenceNumber=" + sequenceNumber + ", " +
                "clientOrderId=" + new String(clientOrderId).trim() + ", " +
                "status=" + status + ", " +
                "rejectCode=" + rejectCode + ", " +
                "orderBook=" + orderBook + '}';
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
