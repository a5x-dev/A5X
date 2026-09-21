package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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
    public String clientOrderId = "";

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
                "clientOrderId=" + clientOrderId + '}';
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
