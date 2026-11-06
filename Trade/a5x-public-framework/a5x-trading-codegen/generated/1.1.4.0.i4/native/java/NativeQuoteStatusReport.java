package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Quote Status Report
 * Message Type: c
 */
public class NativeQuoteStatusReport {

    /** Identity of the matching partition */
    public int partitionId;

    /** Message sequence number of the Matching Partition */
    public long sequenceNumber;

    /** Client specified identifier of the quote if available. Null filled otherwise */
    public String quoteMsgId = "";

    /** 6 = Removed from Market, 7 = Expired, 17 = Cancelled */
    public int quoteStatus;

    /** Code specifying the reason for the reject. Applicable only if Quote Status is Rejected, null filled otherwise */
    public int rejectCode;

    /** 1 = Regular */
    public int orderBook;

    /** Time the message was generated in epoch format */
    public long transactTime;

    /** 0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0) */
    public int anonymity;

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** The system generated unique ID for the Bid Side */
    public String bidId = "";

    /** The system generated unique ID for the Offer Side */
    public String offerId = "";

    /** Identifier of the Account */
    public long account;

    /** Sender Location assigned to the Trader ID/Firm */
    public String senderLocation = "";

    /** Entering Trader ID */
    public String enteringTrader = "";

    /** Free format text field. Echoed in reports */
    public String memo = "";

    /** Desk ID through which the order is placed from */
    public String deskId = "";

    public static void parse(NativeQuoteStatusReport msg, ByteBuffer data) {
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.sequenceNumber = data.getLong();
        msg.quoteMsgId = readAlpha(data, 10);
        msg.quoteStatus = Byte.toUnsignedInt(data.get());
        msg.rejectCode = data.getInt();
        msg.orderBook = Byte.toUnsignedInt(data.get());
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
        data.put((byte) msg.orderBook);
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
                "quoteMsgId=" + quoteMsgId + ", " +
                "quoteStatus=" + quoteStatus + ", " +
                "rejectCode=" + rejectCode + ", " +
                "orderBook=" + orderBook + ", " +
                "transactTime=" + transactTime + ", " +
                "anonymity=" + anonymity + ", " +
                "instrumentId=" + instrumentId + ", " +
                "bidId=" + bidId + ", " +
                "offerId=" + offerId + ", " +
                "account=" + account + ", " +
                "senderLocation=" + senderLocation + ", " +
                "enteringTrader=" + enteringTrader + ", " +
                "memo=" + memo + ", " +
                "deskId=" + deskId + '}';
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
