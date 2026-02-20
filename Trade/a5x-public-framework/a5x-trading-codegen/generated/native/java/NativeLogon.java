package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Logon request
 * Message Type: A
 */
public class NativeLogon {

    /** CompID assigned to the client */
    public byte[] compId = new byte[11];

    /** Password assigned to the CompID */
    public byte[] password = new byte[25];

    /** New password for CompID */
    public byte[] newPassword = new byte[25];

    /** Protocol version (1-9) */
    public int protocolVersion;

    /** Original IP Address the user */
    public byte[] ipAddress = new byte[15];

    /** Nome da solução certificada */
    public byte[] certifiedSolution = new byte[44];

    public static void parse(NativeLogon msg, ByteBuffer data) {
        msg.compId = readAlpha(data, 11);
        msg.password = readAlpha(data, 25);
        msg.newPassword = readAlpha(data, 25);
        msg.protocolVersion = data.getInt();
        msg.ipAddress = readAlpha(data, 15);
        msg.certifiedSolution = readAlpha(data, 44);
    }

    public static void encode(NativeLogon msg, ByteBuffer data) {
        writeAlpha(data, msg.compId, 11);
        writeAlpha(data, msg.password, 25);
        writeAlpha(data, msg.newPassword, 25);
        data.putInt(msg.protocolVersion);
        writeAlpha(data, msg.ipAddress, 15);
        writeAlpha(data, msg.certifiedSolution, 44);
    }

    @Override
    public String toString() {
        return "NativeLogon{" +
                "compId=" + new String(compId).trim() + ", " +
                "password=" + new String(password).trim() + ", " +
                "newPassword=" + new String(newPassword).trim() + ", " +
                "protocolVersion=" + protocolVersion + ", " +
                "ipAddress=" + new String(ipAddress).trim() + ", " +
                "certifiedSolution=" + new String(certifiedSolution).trim() + '}';
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
