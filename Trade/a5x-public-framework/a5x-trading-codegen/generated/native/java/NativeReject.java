package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Reject message
 * Message Type: 3
 */
public class NativeReject {

    /** Code specifying the reason for the reject */
    public int rejectCode;

    /** Reason for the reject */
    public byte[] rejectReason = new byte[30];

    /** Type of message rejected */
    public byte refMessageType;

    /** Client specified identifier of the rejected message if available */
    public byte[] clientOrderId = new byte[10];

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
                "rejectReason=" + new String(rejectReason).trim() + ", " +
                "refMessageType=" + refMessageType + ", " +
                "clientOrderId=" + new String(clientOrderId).trim() + '}';
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
