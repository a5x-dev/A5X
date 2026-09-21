package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Missed message request
 * Message Type: M
 */
public class NativeMissedMessageRequest {

    /** Identity of the matching partition the request relates to */
    public int partitionId;

    /** Sequence number immediately after that of the last message received from the partition */
    public long sequenceNumber;

    public static void parse(NativeMissedMessageRequest msg, ByteBuffer data) {
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.sequenceNumber = data.getLong();
    }

    public static void encode(NativeMissedMessageRequest msg, ByteBuffer data) {
        data.put((byte) msg.partitionId);
        data.putLong(msg.sequenceNumber);
    }

    @Override
    public String toString() {
        return "NativeMissedMessageRequest{" +
                "partitionId=" + partitionId + ", " +
                "sequenceNumber=" + sequenceNumber + '}';
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
