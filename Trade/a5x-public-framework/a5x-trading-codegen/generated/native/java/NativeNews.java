package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * News
 * Message Type: CB
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
    public byte[] headline = new byte[100];

    /** Text of the announcement */
    public byte[] text = new byte[750];

    /** Pipe separated list of symbols of instruments the announcements relates to */
    public byte[] instruments = new byte[100];

    /** Pipe separated list of symbols of underlyings the instruments relates to */
    public byte[] underlyings = new byte[100];

    /** Pipe separated list of firms that the announcement should be sent to */
    public byte[] firmList = new byte[54];

    /** Pipe separated list of users that the announcement should be sent to */
    public byte[] userList = new byte[54];

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
                "headline=" + new String(headline).trim() + ", " +
                "text=" + new String(text).trim() + ", " +
                "instruments=" + new String(instruments).trim() + ", " +
                "underlyings=" + new String(underlyings).trim() + ", " +
                "firmList=" + new String(firmList).trim() + ", " +
                "userList=" + new String(userList).trim() + '}';
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
