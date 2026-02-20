package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Logon response
 * Message Type: B
 */
public class NativeLogonResponse {

    /** 0 or 3 = Accepted, 6 = Firm suspended, 9 = Account expired, 50 = Staggered Start, 55 = Node suspended, 100 = Not logged into real-time, 626 = User suspended, 628 = User inactive, 630 = End of Day, 9903 = Concurrent limit, 9904 = Invalid gateway, 9905 = System unavailable, 9906 = Logons not allowed */
    public int rejectCode;

    /** Number of days for password expiry, ignore if negative */
    public int passwordExpiry;

    public static void parse(NativeLogonResponse msg, ByteBuffer data) {
        msg.rejectCode = data.getInt();
        msg.passwordExpiry = data.getInt();
    }

    public static void encode(NativeLogonResponse msg, ByteBuffer data) {
        data.putInt(msg.rejectCode);
        data.putInt(msg.passwordExpiry);
    }

    @Override
    public String toString() {
        return "NativeLogonResponse{" +
                "rejectCode=" + rejectCode + ", " +
                "passwordExpiry=" + passwordExpiry + '}';
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
