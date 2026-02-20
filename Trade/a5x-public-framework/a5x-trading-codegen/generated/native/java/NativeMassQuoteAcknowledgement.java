package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Mass Quote Acknowledgement
 * Message Type: b
 */
public class NativeMassQuoteAcknowledgement {

    /** Mass Quote ID of the Mass Quote message */
    public byte[] massQuoteId = new byte[10];

    /** Identity of the matching partition */
    public int partitionId;

    /** 0 = Accepted, 5 = Rejected */
    public int status;

    /** Code specifying the reason for the reject. Ignore if Status is not Rejected (0) */
    public int rejectCode;

    /** Time of quote creation in epoch format */
    public long transactTime;

    /** 0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0) */
    public int anonymity;

    /** Identifier of the Account */
    public long account;

    /** Sender Location assigned to the Trader ID/Firm */
    public byte[] senderLocation = new byte[10];

    /** 1 = Regular */
    public int orderBook;

    /** Entering Trader ID */
    public byte[] enteringTrader = new byte[15];

    /** Client specified identifier of order relevant to self-execution */
    public byte[] noTradeKey = new byte[10];

    /** Free format text field. Echoed in reports */
    public byte[] memo = new byte[15];

    /** Desk ID through which the order is placed from */
    public byte[] deskId = new byte[15];

    /** 1 = Regular (Day Session), 2 = Non-Regular (Night Session) */
    public int tradingPhase;

    /** Unique sequence assigned for all market data message */
    public long routingSeq;

    /** Number of quote entries */
    public int totalQuoteEntries;

    public static void parse(NativeMassQuoteAcknowledgement msg, ByteBuffer data) {
        msg.massQuoteId = readAlpha(data, 10);
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.status = Byte.toUnsignedInt(data.get());
        msg.rejectCode = data.getInt();
        msg.transactTime = data.getLong();
        msg.anonymity = Byte.toUnsignedInt(data.get());
        msg.account = data.getLong();
        msg.senderLocation = readAlpha(data, 10);
        msg.orderBook = Byte.toUnsignedInt(data.get());
        msg.enteringTrader = readAlpha(data, 15);
        msg.noTradeKey = readAlpha(data, 10);
        msg.memo = readAlpha(data, 15);
        msg.deskId = readAlpha(data, 15);
        msg.tradingPhase = Byte.toUnsignedInt(data.get());
        msg.routingSeq = data.getLong();
        msg.totalQuoteEntries = Short.toUnsignedInt(data.getShort());
    }

    public static void encode(NativeMassQuoteAcknowledgement msg, ByteBuffer data) {
        writeAlpha(data, msg.massQuoteId, 10);
        data.put((byte) msg.partitionId);
        data.put((byte) msg.status);
        data.putInt(msg.rejectCode);
        data.putLong(msg.transactTime);
        data.put((byte) msg.anonymity);
        data.putLong(msg.account);
        writeAlpha(data, msg.senderLocation, 10);
        data.put((byte) msg.orderBook);
        writeAlpha(data, msg.enteringTrader, 15);
        writeAlpha(data, msg.noTradeKey, 10);
        writeAlpha(data, msg.memo, 15);
        writeAlpha(data, msg.deskId, 15);
        data.put((byte) msg.tradingPhase);
        data.putLong(msg.routingSeq);
        data.putShort((short) msg.totalQuoteEntries);
    }

    @Override
    public String toString() {
        return "NativeMassQuoteAcknowledgement{" +
                "massQuoteId=" + new String(massQuoteId).trim() + ", " +
                "partitionId=" + partitionId + ", " +
                "status=" + status + ", " +
                "rejectCode=" + rejectCode + ", " +
                "transactTime=" + transactTime + ", " +
                "anonymity=" + anonymity + ", " +
                "account=" + account + ", " +
                "senderLocation=" + new String(senderLocation).trim() + ", " +
                "orderBook=" + orderBook + ", " +
                "enteringTrader=" + new String(enteringTrader).trim() + ", " +
                "noTradeKey=" + new String(noTradeKey).trim() + ", " +
                "memo=" + new String(memo).trim() + ", " +
                "deskId=" + new String(deskId).trim() + ", " +
                "tradingPhase=" + tradingPhase + ", " +
                "routingSeq=" + routingSeq + ", " +
                "totalQuoteEntries=" + totalQuoteEntries + '}';
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
