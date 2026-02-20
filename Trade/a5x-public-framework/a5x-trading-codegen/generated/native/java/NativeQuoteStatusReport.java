package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Quote Status Report
 * Message Type: AI
 */
public class NativeQuoteStatusReport {

    /** Identity of the matching partition */
    public int partitionId;

    /** Message sequence number of the Matching Partition */
    public long sequenceNumber;

    /** Client specified identifier of the quote if available. Null filled otherwise */
    public byte[] quoteMsgId = new byte[10];

    /** 6 = Removed from Market, 7 = Expired, 17 = Cancelled */
    public int quoteStatus;

    /** Code specifying the reason for the reject. Applicable only if Quote Status is Rejected, null filled otherwise */
    public int rejectCode;

    /** 1 = Regular */
    public long orderBook;

    /** Time the message was generated in epoch format */
    public long transactTime;

    /** 0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0) */
    public int anonymity;

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** The system generated unique ID for the Bid Side */
    public byte[] bidId = new byte[10];

    /** The system generated unique ID for the Offer Side */
    public byte[] offerId = new byte[10];

    /** Identifier of the Account */
    public long account;

    /** Sender Location assigned to the Trader ID/Firm */
    public byte[] senderLocation = new byte[10];

    /** Entering Trader ID */
    public byte[] enteringTrader = new byte[15];

    /** Free format text field. Echoed in reports */
    public byte[] memo = new byte[15];

    /** Desk ID through which the order is placed from */
    public byte[] deskId = new byte[15];

    public static void parse(NativeQuoteStatusReport msg, ByteBuffer data) {
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.sequenceNumber = data.getLong();
        msg.quoteMsgId = readAlpha(data, 10);
        msg.quoteStatus = Byte.toUnsignedInt(data.get());
        msg.rejectCode = data.getInt();
        msg.orderBook = Integer.toUnsignedLong(data.getInt());
        msg.transactTime = data.getLong();
        msg.anonymity = Byte.toUnsignedInt(data.get());
        msg.instrumentId = Integer.toUnsignedLong(data.getInt());
        msg.bidId = readAlpha(data, 10);
        msg.offerId = readAlpha(data, 10);
        msg.account = data.getLong();
        msg.senderLocation = readAlpha(data, 10);
        msg.enteringTrader = readAlpha(data, 15);
        msg.memo = readAlpha(data, 15);
        msg.deskId = readAlpha(data, 15);
    }

    public static void encode(NativeQuoteStatusReport msg, ByteBuffer data) {
        data.put((byte) msg.partitionId);
        data.putLong(msg.sequenceNumber);
        writeAlpha(data, msg.quoteMsgId, 10);
        data.put((byte) msg.quoteStatus);
        data.putInt(msg.rejectCode);
        data.putInt((int) msg.orderBook);
        data.putLong(msg.transactTime);
        data.put((byte) msg.anonymity);
        data.putInt((int) msg.instrumentId);
        writeAlpha(data, msg.bidId, 10);
        writeAlpha(data, msg.offerId, 10);
        data.putLong(msg.account);
        writeAlpha(data, msg.senderLocation, 10);
        writeAlpha(data, msg.enteringTrader, 15);
        writeAlpha(data, msg.memo, 15);
        writeAlpha(data, msg.deskId, 15);
    }

    @Override
    public String toString() {
        return "NativeQuoteStatusReport{" +
                "partitionId=" + partitionId + ", " +
                "sequenceNumber=" + sequenceNumber + ", " +
                "quoteMsgId=" + new String(quoteMsgId).trim() + ", " +
                "quoteStatus=" + quoteStatus + ", " +
                "rejectCode=" + rejectCode + ", " +
                "orderBook=" + orderBook + ", " +
                "transactTime=" + transactTime + ", " +
                "anonymity=" + anonymity + ", " +
                "instrumentId=" + instrumentId + ", " +
                "bidId=" + new String(bidId).trim() + ", " +
                "offerId=" + new String(offerId).trim() + ", " +
                "account=" + account + ", " +
                "senderLocation=" + new String(senderLocation).trim() + ", " +
                "enteringTrader=" + new String(enteringTrader).trim() + ", " +
                "memo=" + new String(memo).trim() + ", " +
                "deskId=" + new String(deskId).trim() + '}';
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
