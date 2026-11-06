package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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
