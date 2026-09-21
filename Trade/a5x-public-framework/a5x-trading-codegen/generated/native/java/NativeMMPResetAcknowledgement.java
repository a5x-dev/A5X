package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * MMP Reset Acknowledgement. Response to an MMP Reset Request
 * Message Type: ~
 */
public class NativeMMPResetAcknowledgement {

    /** Client specified identifier of the originating request */
    public int requestId;

    /** Identifier of the segment */
    public String segment = "";

    /** Identity of the matching partition */
    public int partitionId;

    /** Entering Trader ID */
    public String enteringTrader = "";

    /** Desk ID through which the request is placed from */
    public String deskId = "";

    /** Sender Location assigned to the Trader ID/Firm */
    public String senderLocation = "";

    /** Free format text field. Echoed in reports */
    public String memo = "";

    /** Outcome of the reset request */
    public int status;

    /** Code specifying the reason for the reject. Applicable only if the request is rejected, null filled otherwise */
    public int rejectCode;

    public static void parse(NativeMMPResetAcknowledgement msg, ByteBuffer data) {
        msg.requestId = data.getInt();
        msg.segment = readAlpha(data, 10);
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.enteringTrader = readAlpha(data, 15);
        msg.deskId = readAlpha(data, 15);
        msg.senderLocation = readAlpha(data, 10);
        msg.memo = readAlpha(data, 15);
        msg.status = Byte.toUnsignedInt(data.get());
        msg.rejectCode = data.getInt();
    }

    public static void encode(NativeMMPResetAcknowledgement msg, ByteBuffer data) {
        data.putInt(msg.requestId);
        writeAlpha(data, msg.segment, 10);
        data.put((byte) msg.partitionId);
        writeAlpha(data, msg.enteringTrader, 15);
        writeAlpha(data, msg.deskId, 15);
        writeAlpha(data, msg.senderLocation, 10);
        writeAlpha(data, msg.memo, 15);
        data.put((byte) msg.status);
        data.putInt(msg.rejectCode);
    }

    @Override
    public String toString() {
        return "NativeMMPResetAcknowledgement{" +
                "requestId=" + requestId + ", " +
                "segment=" + segment + ", " +
                "partitionId=" + partitionId + ", " +
                "enteringTrader=" + enteringTrader + ", " +
                "deskId=" + deskId + ", " +
                "senderLocation=" + senderLocation + ", " +
                "memo=" + memo + ", " +
                "status=" + status + ", " +
                "rejectCode=" + rejectCode + '}';
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
