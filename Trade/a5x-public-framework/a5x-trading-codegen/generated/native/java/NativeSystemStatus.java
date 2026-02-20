package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

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
