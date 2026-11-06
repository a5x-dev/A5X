package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * News
 * Message Type: Z
 */
public class NativeNews {

    /** Identity of the matching partition */
    public int partitionId;

    /** Message sequence number of the matching partition */
    public long sequenceNumber;

    /** Time the announcement was published in EPOCH time */
    public long origTime;

    /** 0 = Rejected, 1 = High Priority, 2 = Low Priority */
    public int urgency;

    /** Headline or subject of the announcement */
    public String headline = "";

    /** Text of the announcement */
    public String text = "";

    /** Pipe separated list of symbols of instruments the announcements relates to */
    public String instruments = "";

    /** Pipe separated list of symbols of underlyings the instruments relates to */
    public String underlyings = "";

    /** Pipe separated list of firms that the announcement should be sent to */
    public String firmList = "";

    /** Pipe separated list of users that the announcement should be sent to */
    public String userList = "";

    public static void parse(NativeNews msg, ByteBuffer data) {
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.sequenceNumber = data.getLong();
        msg.origTime = data.getLong();
        msg.urgency = Byte.toUnsignedInt(data.get());
        msg.headline = readAlpha(data, 100);
        msg.text = readAlpha(data, 750);
        msg.instruments = readAlpha(data, 100);
        msg.underlyings = readAlpha(data, 100);
        msg.firmList = readAlpha(data, 54);
        msg.userList = readAlpha(data, 54);
    }

    public static void encode(NativeNews msg, ByteBuffer data) {
        data.put((byte) msg.partitionId);
        data.putLong(msg.sequenceNumber);
        data.putLong(msg.origTime);
        data.put((byte) msg.urgency);
        writeAlpha(data, msg.headline, 100);
        writeAlpha(data, msg.text, 750);
        writeAlpha(data, msg.instruments, 100);
        writeAlpha(data, msg.underlyings, 100);
        writeAlpha(data, msg.firmList, 54);
        writeAlpha(data, msg.userList, 54);
    }

    @Override
    public String toString() {
        return "NativeNews{" +
                "partitionId=" + partitionId + ", " +
                "sequenceNumber=" + sequenceNumber + ", " +
                "origTime=" + origTime + ", " +
                "urgency=" + urgency + ", " +
                "headline=" + headline + ", " +
                "text=" + text + ", " +
                "instruments=" + instruments + ", " +
                "underlyings=" + underlyings + ", " +
                "firmList=" + firmList + ", " +
                "userList=" + userList + '}';
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
