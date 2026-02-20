package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Missed message request acknowledgement
 * Message Type: N
 */
public class NativeMissedMessageRequestAck {

    /** 0 = Request Accepted, 1 = Request Limit Reached, 2 = Invalid Partition ID, 3 = Service Unavailable */
    public int status;

    public static void parse(NativeMissedMessageRequestAck msg, ByteBuffer data) {
        msg.status = Byte.toUnsignedInt(data.get());
    }

    public static void encode(NativeMissedMessageRequestAck msg, ByteBuffer data) {
        data.put((byte) msg.status);
    }

    @Override
    public String toString() {
        return "NativeMissedMessageRequestAck{" +
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
