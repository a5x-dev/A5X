package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;

/**
 * Execution report
 * Message Type: 8
 */
public class NativeExecutionReport {

    /** Identity of the matching partition */
    public int partitionId;

    /** Message sequence number of the matching partition */
    public long sequenceNumber;

    /** Identifier of the Execution Report. Identifier of the trade being cancelled or corrected if Execution Type is Trade Cancel (H) */
    public byte[] executionId = new byte[21];

    /** Client specified identifier of the order, cancel request or modification requests */
    public byte[] clientOrderId = new byte[10];

    /** Server specified identifier of the order */
    public byte[] orderId = new byte[12];

    /** Server specified public order identifier of the order */
    public byte[] publicOrderId = new byte[12];

    /** Executed price. Ignore if Execution Type is not Trade (F) */
    public int executedPrice;

    public java.math.BigDecimal executedPriceAsDecimal() { return java.math.BigDecimal.valueOf(executedPrice, 4); }

    /** Time the transaction occurred in epoch format */
    public long transactTime;

    /** Identifier of the Trader Account */
    public long account;

    /** Sender Location assigned to the Trader ID/Firm */
    public byte[] senderLocation = new byte[10];

    /** Numeric Identifier of the instrument */
    public long instrumentId;

    /** Identifier of the cross order. Only alphanumeric values allowed */
    public byte[] crossId = new byte[10];

    /** Executed quantity. Ignore if Execution Type is not Trade (F) */
    public long executedQuantity;

    /** Quantity available for further execution */
    public long leavesQuantity;

    /** 0 = New, 4 = Cancelled, 5 = Modified, 8 = Rejected, 9 = Suspended, A = Pending New, C = Expired, D = Restated, E = Pending Replace, F = Trade, H = Trade Cancel, L = Triggered */
    public byte[] executionType = new byte[1];

    /** 0 = New, 1 = Partially Filled, 2 = Filled, 4 = Cancelled, 6 = Expired, 8 = Rejected, 9 = Suspended, 12 = Pending New, 14 = Pending Replace */
    public int orderStatus;

    /** Code specifying the reason for the reject. Ignore if Execution Type is not Rejected (8) */
    public long rejectCode;

    /** 0 = Unset, 1 = Order is being worked, 2 = Order is not currently in a working state. Only applicable when Order Status is New (0) */
    public int workingIndicator;

    /** 1 = Buy, 2 = Sell. When a quote is rejected, field will be set to Buy (1) */
    public int side;

    /** 0 = GT Corporate Action, 1 = GT Renewal/Restatement, 3 = Order Re-Priced, 8 = Market Option, 100 = Order Replenishment. Only applicable if Exec Type is Restated (D) or unsolicited cancellation */
    public int restatementReason;

    /** 1 = Regular */
    public int orderBook;

    /** Bit 0: Aggressor Indicator (0 = Passive, 1 = Aggressor), Bit 5: RLP (0 = No, 1 = Yes). Applicable only when Exec Type is Trade */
    public byte indicatorFlags;

    /** 5 = Internal Cross. Any other value rejected */
    public int crossType;

    /** 1 = Retail Liquidity Taker, 2 = Waived Priority, 3 = Broker Only */
    public int routingInstruction;

    /** 1 = Regular, 2 = Non-Regular */
    public int tradingPhase;

    /** MIC Code of the instrument as set up under Execution Venue in Reference Data */
    public byte[] executionVenue = new byte[4];

    /** Entering Trader ID */
    public byte[] enteringTrader = new byte[15];

    /** Free format text field. Echoed in reports */
    public byte[] memo = new byte[15];

    /** Desk ID through which the order is placed from */
    public byte[] deskId = new byte[15];

    /** Client specified identifier of order relevant to self-execution */
    public byte[] noTradeKey = new byte[10];

    public static void parse(NativeExecutionReport msg, ByteBuffer data) {
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.sequenceNumber = data.getLong();
        msg.executionId = readAlpha(data, 21);
        msg.clientOrderId = readAlpha(data, 10);
        msg.orderId = readAlpha(data, 12);
        msg.publicOrderId = readAlpha(data, 12);
        msg.executedPrice = data.getInt();
        msg.transactTime = data.getLong();
        msg.account = data.getLong();
        msg.senderLocation = readAlpha(data, 10);
        msg.instrumentId = Integer.toUnsignedLong(data.getInt());
        msg.crossId = readAlpha(data, 10);
        msg.executedQuantity = Integer.toUnsignedLong(data.getInt());
        msg.leavesQuantity = Integer.toUnsignedLong(data.getInt());
        msg.executionType = readAlpha(data, 1);
        msg.orderStatus = Byte.toUnsignedInt(data.get());
        msg.rejectCode = Integer.toUnsignedLong(data.getInt());
        msg.workingIndicator = Byte.toUnsignedInt(data.get());
        msg.side = Byte.toUnsignedInt(data.get());
        msg.restatementReason = Byte.toUnsignedInt(data.get());
        msg.orderBook = Byte.toUnsignedInt(data.get());
        msg.indicatorFlags = data.get();
        msg.crossType = Byte.toUnsignedInt(data.get());
        msg.routingInstruction = Byte.toUnsignedInt(data.get());
        msg.tradingPhase = Byte.toUnsignedInt(data.get());
        msg.executionVenue = readAlpha(data, 4);
        msg.enteringTrader = readAlpha(data, 15);
        msg.memo = readAlpha(data, 15);
        msg.deskId = readAlpha(data, 15);
        msg.noTradeKey = readAlpha(data, 10);
    }

    public static void encode(NativeExecutionReport msg, ByteBuffer data) {
        data.put((byte) msg.partitionId);
        data.putLong(msg.sequenceNumber);
        writeAlpha(data, msg.executionId, 21);
        writeAlpha(data, msg.clientOrderId, 10);
        writeAlpha(data, msg.orderId, 12);
        writeAlpha(data, msg.publicOrderId, 12);
        data.putInt(msg.executedPrice);
        data.putLong(msg.transactTime);
        data.putLong(msg.account);
        writeAlpha(data, msg.senderLocation, 10);
        data.putInt((int) msg.instrumentId);
        writeAlpha(data, msg.crossId, 10);
        data.putInt((int) msg.executedQuantity);
        data.putInt((int) msg.leavesQuantity);
        writeAlpha(data, msg.executionType, 1);
        data.put((byte) msg.orderStatus);
        data.putInt((int) msg.rejectCode);
        data.put((byte) msg.workingIndicator);
        data.put((byte) msg.side);
        data.put((byte) msg.restatementReason);
        data.put((byte) msg.orderBook);
        data.put(msg.indicatorFlags);
        data.put((byte) msg.crossType);
        data.put((byte) msg.routingInstruction);
        data.put((byte) msg.tradingPhase);
        writeAlpha(data, msg.executionVenue, 4);
        writeAlpha(data, msg.enteringTrader, 15);
        writeAlpha(data, msg.memo, 15);
        writeAlpha(data, msg.deskId, 15);
        writeAlpha(data, msg.noTradeKey, 10);
    }

    @Override
    public String toString() {
        return "NativeExecutionReport{" +
                "partitionId=" + partitionId + ", " +
                "sequenceNumber=" + sequenceNumber + ", " +
                "executionId=" + new String(executionId).trim() + ", " +
                "clientOrderId=" + new String(clientOrderId).trim() + ", " +
                "orderId=" + new String(orderId).trim() + ", " +
                "publicOrderId=" + new String(publicOrderId).trim() + ", " +
                "executedPrice=" + executedPrice + ", " +
                "transactTime=" + transactTime + ", " +
                "account=" + account + ", " +
                "senderLocation=" + new String(senderLocation).trim() + ", " +
                "instrumentId=" + instrumentId + ", " +
                "crossId=" + new String(crossId).trim() + ", " +
                "executedQuantity=" + executedQuantity + ", " +
                "leavesQuantity=" + leavesQuantity + ", " +
                "executionType=" + new String(executionType).trim() + ", " +
                "orderStatus=" + orderStatus + ", " +
                "rejectCode=" + rejectCode + ", " +
                "workingIndicator=" + workingIndicator + ", " +
                "side=" + side + ", " +
                "restatementReason=" + restatementReason + ", " +
                "orderBook=" + orderBook + ", " +
                "indicatorFlags=" + indicatorFlags + ", " +
                "crossType=" + crossType + ", " +
                "routingInstruction=" + routingInstruction + ", " +
                "tradingPhase=" + tradingPhase + ", " +
                "executionVenue=" + new String(executionVenue).trim() + ", " +
                "enteringTrader=" + new String(enteringTrader).trim() + ", " +
                "memo=" + new String(memo).trim() + ", " +
                "deskId=" + new String(deskId).trim() + ", " +
                "noTradeKey=" + new String(noTradeKey).trim() + '}';
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
