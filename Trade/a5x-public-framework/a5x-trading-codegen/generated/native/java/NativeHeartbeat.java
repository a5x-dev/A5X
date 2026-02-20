package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Heartbeat message
 * Message Type: 0
 */
public class NativeHeartbeat {

    public static void parse(NativeHeartbeat msg, ByteBuffer data) {
    }

    public static void encode(NativeHeartbeat msg, ByteBuffer data) {
    }

    @Override
    public String toString() {
        return "NativeHeartbeat{}";
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
