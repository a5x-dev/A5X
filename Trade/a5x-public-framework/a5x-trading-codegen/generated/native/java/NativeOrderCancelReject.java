package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Order Cancel Reject
 * Message Type: 9
 */
public class NativeOrderCancelReject {

    /** Identity of the matching partition */
    public int partitionId;

    /** Message sequence number of the matching partition */
    public long sequenceNumber;

    /** Client specified identifier of the rejected cancel or modification request */
    public byte[] clientOrderId = new byte[10];

    /** Code specifying the reason for the reject */
    public int rejectCode;

    /** 1 = Regular */
    public int orderBook;

    /** Time the message was generated in epoch format */
    public long transactTime;

    /** Sender Location assigned to the Trader ID/Firm */
    public byte[] senderLocation = new byte[10];

    public static void parse(NativeOrderCancelReject msg, ByteBuffer data) {
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.sequenceNumber = data.getLong();
        msg.clientOrderId = readAlpha(data, 10);
        msg.rejectCode = data.getInt();
        msg.orderBook = Byte.toUnsignedInt(data.get());
        msg.transactTime = data.getLong();
        msg.senderLocation = readAlpha(data, 10);
    }

    public static void encode(NativeOrderCancelReject msg, ByteBuffer data) {
        data.put((byte) msg.partitionId);
        data.putLong(msg.sequenceNumber);
        writeAlpha(data, msg.clientOrderId, 10);
        data.putInt(msg.rejectCode);
        data.put((byte) msg.orderBook);
        data.putLong(msg.transactTime);
        writeAlpha(data, msg.senderLocation, 10);
    }

    @Override
    public String toString() {
        return "NativeOrderCancelReject{" +
                "partitionId=" + partitionId + ", " +
                "sequenceNumber=" + sequenceNumber + ", " +
                "clientOrderId=" + new String(clientOrderId).trim() + ", " +
                "rejectCode=" + rejectCode + ", " +
                "orderBook=" + orderBook + ", " +
                "transactTime=" + transactTime + ", " +
                "senderLocation=" + new String(senderLocation).trim() + '}';
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
