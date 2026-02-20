package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Business Reject
 * Message Type: j
 */
public class NativeBusinessReject {

    /** Identity of the matching partition */
    public int partitionId;

    /** Message sequence number of the matching partition */
    public long sequenceNumber;

    /** Code specifying the reason for the reject. 9000 = Unknown instrument, 9998 = Matching partition suspended, 9999 = System suspended */
    public long rejectCode;

    /** Client specified identifier of the rejected message if available */
    public byte[] clientOrderId = new byte[10];

    public static void parse(NativeBusinessReject msg, ByteBuffer data) {
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.sequenceNumber = data.getLong();
        msg.rejectCode = Integer.toUnsignedLong(data.getInt());
        msg.clientOrderId = readAlpha(data, 10);
    }

    public static void encode(NativeBusinessReject msg, ByteBuffer data) {
        data.put((byte) msg.partitionId);
        data.putLong(msg.sequenceNumber);
        data.putInt((int) msg.rejectCode);
        writeAlpha(data, msg.clientOrderId, 10);
    }

    @Override
    public String toString() {
        return "NativeBusinessReject{" +
                "partitionId=" + partitionId + ", " +
                "sequenceNumber=" + sequenceNumber + ", " +
                "rejectCode=" + rejectCode + ", " +
                "clientOrderId=" + new String(clientOrderId).trim() + '}';
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
