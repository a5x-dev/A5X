package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Logon request
 * Message Type: A
 */
public class NativeLogon {

    /** CompID assigned to the client */
    public String compId = "";

    /** Password assigned to the CompID */
    public String password = "";

    /** New password for CompID */
    public String newPassword = "";

    /** Specifies the version of protocol. The system will only accept values from 1-9. If left empty, the system will consider the default value defined in system */
    public int protocolVersion;

    /** Original IP Address the user */
    public String ipAddress = "";

    /** Nome da solução certificada */
    public String certifiedSolution = "";

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
                "compId=" + compId + ", " +
                "password=" + password + ", " +
                "newPassword=" + newPassword + ", " +
                "protocolVersion=" + protocolVersion + ", " +
                "ipAddress=" + ipAddress + ", " +
                "certifiedSolution=" + certifiedSolution + '}';
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
