package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Logout message
 * Message Type: 5
 */
public class NativeLogout {

    /** Reason for the logout */
    public byte[] reason = new byte[20];

    public static void parse(NativeLogout msg, ByteBuffer data) {
        msg.reason = readAlpha(data, 20);
    }

    public static void encode(NativeLogout msg, ByteBuffer data) {
        writeAlpha(data, msg.reason, 20);
    }

    @Override
    public String toString() {
        return "NativeLogout{" +
                "reason=" + new String(reason).trim() + '}';
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
