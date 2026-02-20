package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Transmission complete
 * Message Type: P
 */
public class NativeTransmissionComplete {

    /** 0 = All Messages Transmitted, 1 = Message Limit Reached, 3 = Service Unavailable */
    public int status;

    public static void parse(NativeTransmissionComplete msg, ByteBuffer data) {
        msg.status = Byte.toUnsignedInt(data.get());
    }

    public static void encode(NativeTransmissionComplete msg, ByteBuffer data) {
        data.put((byte) msg.status);
    }

    @Override
    public String toString() {
        return "NativeTransmissionComplete{" +
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
