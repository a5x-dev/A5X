package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Logout message
 * Message Type: 5
 */
public class NativeLogout {

    /** Reason for the logout */
    public String reason = "";

    public static void parse(NativeLogout msg, ByteBuffer data) {
        msg.reason = readAlpha(data, 20);
    }

    public static void encode(NativeLogout msg, ByteBuffer data) {
        writeAlpha(data, msg.reason, 20);
    }

    @Override
    public String toString() {
        return "NativeLogout{" +
                "reason=" + reason + '}';
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
