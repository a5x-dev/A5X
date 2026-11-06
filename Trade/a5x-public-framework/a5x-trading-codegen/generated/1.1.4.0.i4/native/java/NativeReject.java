package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Reject message
 * Message Type: 3
 */
public class NativeReject {

    /** Code specifying the reason for the reject */
    public int rejectCode;

    /** Reason for the reject */
    public String rejectReason = "";

    /** Type of message rejected */
    public byte refMessageType;

    /** Client specified identifier of the rejected message if available */
    public String clientOrderId = "";

    public static void parse(NativeReject msg, ByteBuffer data) {
        msg.rejectCode = data.getInt();
        msg.rejectReason = readAlpha(data, 30);
        msg.refMessageType = data.get();
        msg.clientOrderId = readAlpha(data, 10);
    }

    public static void encode(NativeReject msg, ByteBuffer data) {
        data.putInt(msg.rejectCode);
        writeAlpha(data, msg.rejectReason, 30);
        data.put(msg.refMessageType);
        writeAlpha(data, msg.clientOrderId, 10);
    }

    @Override
    public String toString() {
        return "NativeReject{" +
                "rejectCode=" + rejectCode + ", " +
                "rejectReason=" + rejectReason + ", " +
                "refMessageType=" + refMessageType + ", " +
                "clientOrderId=" + clientOrderId + '}';
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
