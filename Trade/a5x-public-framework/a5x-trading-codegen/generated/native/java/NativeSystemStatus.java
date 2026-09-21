package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * System status
 * Message Type: n
 */
public class NativeSystemStatus {

    /** Identity of the matching partition the message relates to */
    public int partitionId;

    /** 1 = Recovery Service Resumed, 2 = Recovery Service Unavailable, 3 = Partition Suspended */
    public int status;

    public static void parse(NativeSystemStatus msg, ByteBuffer data) {
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.status = Byte.toUnsignedInt(data.get());
    }

    public static void encode(NativeSystemStatus msg, ByteBuffer data) {
        data.put((byte) msg.partitionId);
        data.put((byte) msg.status);
    }

    @Override
    public String toString() {
        return "NativeSystemStatus{" +
                "partitionId=" + partitionId + ", " +
                "status=" + status + '}';
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
