package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

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
