package br.com.a5x.trd.pub.nativ.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Mass Quote Flat. Mass Quote with the quote entries flattened into numbered fields.
 * Message Type: m
 */
public class NativeMassQuoteFlat {

    /** Client specified identifier of the message. */
    public String massQuoteId = "";

    /** Identity of the matching partition. */
    public int partitionId;

    /** 0 = Do Not Cancel, 1 = Cancel */
    public int cancelOnDisconnect;

    /** Whether the quote is anonymous or named. Absence of this field is interpreted as Anonymous (0). */
    public int anonymity;

    /** Identifier of the trading account. (Mandatory for Native Gateway Messages). */
    public long account;

    /** Sender Location assigned to the Trader ID/Firm. */
    public String senderLocation = "";

    /** Entering Trader ID. */
    public String enteringTrader = "";

    /** Client specified identifier of order relevant to self-execution. */
    public String noTradeKey = "";

    /** Free format text field. This field may be used to convey client's relevant info. It's always echoed in the reports. */
    public String memo = "";

    /** Desk ID through which the order is placed from. */
    public String deskId = "";

    /** Number of quote entries in the mass quote (up to 15). */
    public int totalQuoteEntries;

    /** Client specified identifier of the quote 1. */
    public String quoteEntryId1 = "";

    /** Identifier of the instrument of quote 1. */
    public long instrumentId1;

    /** Bid price of quote 1. */
    public long bidPrice1;

    public java.math.BigDecimal bidPrice1AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice1, 4); }

    /** Bid quantity of quote 1. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize1;

    /** Offer price of quote 1. */
    public long offerPrice1;

    public java.math.BigDecimal offerPrice1AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice1, 4); }

    /** Offer quantity of quote 1. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize1;

    /** Client specified identifier of the quote 2. */
    public String quoteEntryId2 = "";

    /** Identifier of the instrument of quote 2. */
    public long instrumentId2;

    /** Bid price of quote 2. */
    public long bidPrice2;

    public java.math.BigDecimal bidPrice2AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice2, 4); }

    /** Bid quantity of quote 2. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize2;

    /** Offer price of quote 2. */
    public long offerPrice2;

    public java.math.BigDecimal offerPrice2AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice2, 4); }

    /** Offer quantity of quote 2. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize2;

    /** Client specified identifier of the quote 3. */
    public String quoteEntryId3 = "";

    /** Identifier of the instrument of quote 3. */
    public long instrumentId3;

    /** Bid price of quote 3. */
    public long bidPrice3;

    public java.math.BigDecimal bidPrice3AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice3, 4); }

    /** Bid quantity of quote 3. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize3;

    /** Offer price of quote 3. */
    public long offerPrice3;

    public java.math.BigDecimal offerPrice3AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice3, 4); }

    /** Offer quantity of quote 3. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize3;

    /** Client specified identifier of the quote 4. */
    public String quoteEntryId4 = "";

    /** Identifier of the instrument of quote 4. */
    public long instrumentId4;

    /** Bid price of quote 4. */
    public long bidPrice4;

    public java.math.BigDecimal bidPrice4AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice4, 4); }

    /** Bid quantity of quote 4. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize4;

    /** Offer price of quote 4. */
    public long offerPrice4;

    public java.math.BigDecimal offerPrice4AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice4, 4); }

    /** Offer quantity of quote 4. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize4;

    /** Client specified identifier of the quote 5. */
    public String quoteEntryId5 = "";

    /** Identifier of the instrument of quote 5. */
    public long instrumentId5;

    /** Bid price of quote 5. */
    public long bidPrice5;

    public java.math.BigDecimal bidPrice5AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice5, 4); }

    /** Bid quantity of quote 5. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize5;

    /** Offer price of quote 5. */
    public long offerPrice5;

    public java.math.BigDecimal offerPrice5AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice5, 4); }

    /** Offer quantity of quote 5. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize5;

    /** Client specified identifier of the quote 6. */
    public String quoteEntryId6 = "";

    /** Identifier of the instrument of quote 6. */
    public long instrumentId6;

    /** Bid price of quote 6. */
    public long bidPrice6;

    public java.math.BigDecimal bidPrice6AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice6, 4); }

    /** Bid quantity of quote 6. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize6;

    /** Offer price of quote 6. */
    public long offerPrice6;

    public java.math.BigDecimal offerPrice6AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice6, 4); }

    /** Offer quantity of quote 6. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize6;

    /** Client specified identifier of the quote 7. */
    public String quoteEntryId7 = "";

    /** Identifier of the instrument of quote 7. */
    public long instrumentId7;

    /** Bid price of quote 7. */
    public long bidPrice7;

    public java.math.BigDecimal bidPrice7AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice7, 4); }

    /** Bid quantity of quote 7. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize7;

    /** Offer price of quote 7. */
    public long offerPrice7;

    public java.math.BigDecimal offerPrice7AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice7, 4); }

    /** Offer quantity of quote 7. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize7;

    /** Client specified identifier of the quote 8. */
    public String quoteEntryId8 = "";

    /** Identifier of the instrument of quote 8. */
    public long instrumentId8;

    /** Bid price of quote 8. */
    public long bidPrice8;

    public java.math.BigDecimal bidPrice8AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice8, 4); }

    /** Bid quantity of quote 8. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize8;

    /** Offer price of quote 8. */
    public long offerPrice8;

    public java.math.BigDecimal offerPrice8AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice8, 4); }

    /** Offer quantity of quote 8. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize8;

    /** Client specified identifier of the quote 9. */
    public String quoteEntryId9 = "";

    /** Identifier of the instrument of quote 9. */
    public long instrumentId9;

    /** Bid price of quote 9. */
    public long bidPrice9;

    public java.math.BigDecimal bidPrice9AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice9, 4); }

    /** Bid quantity of quote 9. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize9;

    /** Offer price of quote 9. */
    public long offerPrice9;

    public java.math.BigDecimal offerPrice9AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice9, 4); }

    /** Offer quantity of quote 9. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize9;

    /** Client specified identifier of the quote 10. */
    public String quoteEntryId10 = "";

    /** Identifier of the instrument of quote 10. */
    public long instrumentId10;

    /** Bid price of quote 10. */
    public long bidPrice10;

    public java.math.BigDecimal bidPrice10AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice10, 4); }

    /** Bid quantity of quote 10. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize10;

    /** Offer price of quote 10. */
    public long offerPrice10;

    public java.math.BigDecimal offerPrice10AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice10, 4); }

    /** Offer quantity of quote10. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize10;

    /** Client specified identifier of the quote 11. */
    public String quoteEntryId11 = "";

    /** Identifier of the instrument of quote 11. */
    public long instrumentId11;

    /** Bid price of quote 11. */
    public long bidPrice11;

    public java.math.BigDecimal bidPrice11AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice11, 4); }

    /** Bid quantity of quote 11. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize11;

    /** Offer price of quote 11. */
    public long offerPrice11;

    public java.math.BigDecimal offerPrice11AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice11, 4); }

    /** Offer quantity of quote11. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize11;

    /** Client specified identifier of the quote 12. */
    public String quoteEntryId12 = "";

    /** Identifier of the instrument of quote 12. */
    public long instrumentId12;

    /** Bid price of quote 12. */
    public long bidPrice12;

    public java.math.BigDecimal bidPrice12AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice12, 4); }

    /** Bid quantity of quote 12. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize12;

    /** Offer price of quote 12. */
    public long offerPrice12;

    public java.math.BigDecimal offerPrice12AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice12, 4); }

    /** Offer quantity of quote12. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize12;

    /** Client specified identifier of the quote 13. */
    public String quoteEntryId13 = "";

    /** Identifier of the instrument of quote 13. */
    public long instrumentId13;

    /** Bid price of quote 13. */
    public long bidPrice13;

    public java.math.BigDecimal bidPrice13AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice13, 4); }

    /** Bid quantity of quote 13. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize13;

    /** Offer price of quote 13. */
    public long offerPrice13;

    public java.math.BigDecimal offerPrice13AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice13, 4); }

    /** Offer quantity of quote13. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize13;

    /** Client specified identifier of the quote 14. */
    public String quoteEntryId14 = "";

    /** Identifier of the instrument of quote 14. */
    public long instrumentId14;

    /** Bid price of quote 14. */
    public long bidPrice14;

    public java.math.BigDecimal bidPrice14AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice14, 4); }

    /** Bid quantity of quote 14. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize14;

    /** Offer price of quote 14. */
    public long offerPrice14;

    public java.math.BigDecimal offerPrice14AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice14, 4); }

    /** Offer quantity of quote14. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize14;

    /** Client specified identifier of the quote 15. */
    public String quoteEntryId15 = "";

    /** Identifier of the instrument of quote 15. */
    public long instrumentId15;

    /** Bid price of quote 15. */
    public long bidPrice15;

    public java.math.BigDecimal bidPrice15AsDecimal() { return java.math.BigDecimal.valueOf(bidPrice15, 4); }

    /** Bid quantity of quote 15. A value of '0' cancels this side of the quote for the given instrument. */
    public long bidSize15;

    /** Offer price of quote 15. */
    public long offerPrice15;

    public java.math.BigDecimal offerPrice15AsDecimal() { return java.math.BigDecimal.valueOf(offerPrice15, 4); }

    /** Offer quantity of quote15. A value of '0' cancels this side of the quote for the given instrument. */
    public long offerSize15;

    public static void parse(NativeMassQuoteFlat msg, ByteBuffer data) {
        msg.massQuoteId = readAlpha(data, 10);
        msg.partitionId = Byte.toUnsignedInt(data.get());
        msg.cancelOnDisconnect = Byte.toUnsignedInt(data.get());
        msg.anonymity = Byte.toUnsignedInt(data.get());
        msg.account = data.getLong();
        msg.senderLocation = readAlpha(data, 10);
        msg.enteringTrader = readAlpha(data, 15);
        msg.noTradeKey = readAlpha(data, 10);
        msg.memo = readAlpha(data, 15);
        msg.deskId = readAlpha(data, 15);
        msg.totalQuoteEntries = Byte.toUnsignedInt(data.get());
        msg.quoteEntryId1 = readAlpha(data, 10);
        msg.instrumentId1 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice1 = data.getLong();
        msg.bidSize1 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice1 = data.getLong();
        msg.offerSize1 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId2 = readAlpha(data, 10);
        msg.instrumentId2 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice2 = data.getLong();
        msg.bidSize2 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice2 = data.getLong();
        msg.offerSize2 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId3 = readAlpha(data, 10);
        msg.instrumentId3 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice3 = data.getLong();
        msg.bidSize3 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice3 = data.getLong();
        msg.offerSize3 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId4 = readAlpha(data, 10);
        msg.instrumentId4 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice4 = data.getLong();
        msg.bidSize4 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice4 = data.getLong();
        msg.offerSize4 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId5 = readAlpha(data, 10);
        msg.instrumentId5 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice5 = data.getLong();
        msg.bidSize5 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice5 = data.getLong();
        msg.offerSize5 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId6 = readAlpha(data, 10);
        msg.instrumentId6 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice6 = data.getLong();
        msg.bidSize6 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice6 = data.getLong();
        msg.offerSize6 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId7 = readAlpha(data, 10);
        msg.instrumentId7 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice7 = data.getLong();
        msg.bidSize7 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice7 = data.getLong();
        msg.offerSize7 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId8 = readAlpha(data, 10);
        msg.instrumentId8 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice8 = data.getLong();
        msg.bidSize8 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice8 = data.getLong();
        msg.offerSize8 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId9 = readAlpha(data, 10);
        msg.instrumentId9 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice9 = data.getLong();
        msg.bidSize9 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice9 = data.getLong();
        msg.offerSize9 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId10 = readAlpha(data, 10);
        msg.instrumentId10 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice10 = data.getLong();
        msg.bidSize10 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice10 = data.getLong();
        msg.offerSize10 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId11 = readAlpha(data, 10);
        msg.instrumentId11 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice11 = data.getLong();
        msg.bidSize11 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice11 = data.getLong();
        msg.offerSize11 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId12 = readAlpha(data, 10);
        msg.instrumentId12 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice12 = data.getLong();
        msg.bidSize12 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice12 = data.getLong();
        msg.offerSize12 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId13 = readAlpha(data, 10);
        msg.instrumentId13 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice13 = data.getLong();
        msg.bidSize13 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice13 = data.getLong();
        msg.offerSize13 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId14 = readAlpha(data, 10);
        msg.instrumentId14 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice14 = data.getLong();
        msg.bidSize14 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice14 = data.getLong();
        msg.offerSize14 = Integer.toUnsignedLong(data.getInt());
        msg.quoteEntryId15 = readAlpha(data, 10);
        msg.instrumentId15 = Integer.toUnsignedLong(data.getInt());
        msg.bidPrice15 = data.getLong();
        msg.bidSize15 = Integer.toUnsignedLong(data.getInt());
        msg.offerPrice15 = data.getLong();
        msg.offerSize15 = Integer.toUnsignedLong(data.getInt());
    }

    public static void encode(NativeMassQuoteFlat msg, ByteBuffer data) {
        writeAlpha(data, msg.massQuoteId, 10);
        data.put((byte) msg.partitionId);
        data.put((byte) msg.cancelOnDisconnect);
        data.put((byte) msg.anonymity);
        data.putLong(msg.account);
        writeAlpha(data, msg.senderLocation, 10);
        writeAlpha(data, msg.enteringTrader, 15);
        writeAlpha(data, msg.noTradeKey, 10);
        writeAlpha(data, msg.memo, 15);
        writeAlpha(data, msg.deskId, 15);
        data.put((byte) msg.totalQuoteEntries);
        writeAlpha(data, msg.quoteEntryId1, 10);
        data.putInt((int) msg.instrumentId1);
        data.putLong(msg.bidPrice1);
        data.putInt((int) msg.bidSize1);
        data.putLong(msg.offerPrice1);
        data.putInt((int) msg.offerSize1);
        writeAlpha(data, msg.quoteEntryId2, 10);
        data.putInt((int) msg.instrumentId2);
        data.putLong(msg.bidPrice2);
        data.putInt((int) msg.bidSize2);
        data.putLong(msg.offerPrice2);
        data.putInt((int) msg.offerSize2);
        writeAlpha(data, msg.quoteEntryId3, 10);
        data.putInt((int) msg.instrumentId3);
        data.putLong(msg.bidPrice3);
        data.putInt((int) msg.bidSize3);
        data.putLong(msg.offerPrice3);
        data.putInt((int) msg.offerSize3);
        writeAlpha(data, msg.quoteEntryId4, 10);
        data.putInt((int) msg.instrumentId4);
        data.putLong(msg.bidPrice4);
        data.putInt((int) msg.bidSize4);
        data.putLong(msg.offerPrice4);
        data.putInt((int) msg.offerSize4);
        writeAlpha(data, msg.quoteEntryId5, 10);
        data.putInt((int) msg.instrumentId5);
        data.putLong(msg.bidPrice5);
        data.putInt((int) msg.bidSize5);
        data.putLong(msg.offerPrice5);
        data.putInt((int) msg.offerSize5);
        writeAlpha(data, msg.quoteEntryId6, 10);
        data.putInt((int) msg.instrumentId6);
        data.putLong(msg.bidPrice6);
        data.putInt((int) msg.bidSize6);
        data.putLong(msg.offerPrice6);
        data.putInt((int) msg.offerSize6);
        writeAlpha(data, msg.quoteEntryId7, 10);
        data.putInt((int) msg.instrumentId7);
        data.putLong(msg.bidPrice7);
        data.putInt((int) msg.bidSize7);
        data.putLong(msg.offerPrice7);
        data.putInt((int) msg.offerSize7);
        writeAlpha(data, msg.quoteEntryId8, 10);
        data.putInt((int) msg.instrumentId8);
        data.putLong(msg.bidPrice8);
        data.putInt((int) msg.bidSize8);
        data.putLong(msg.offerPrice8);
        data.putInt((int) msg.offerSize8);
        writeAlpha(data, msg.quoteEntryId9, 10);
        data.putInt((int) msg.instrumentId9);
        data.putLong(msg.bidPrice9);
        data.putInt((int) msg.bidSize9);
        data.putLong(msg.offerPrice9);
        data.putInt((int) msg.offerSize9);
        writeAlpha(data, msg.quoteEntryId10, 10);
        data.putInt((int) msg.instrumentId10);
        data.putLong(msg.bidPrice10);
        data.putInt((int) msg.bidSize10);
        data.putLong(msg.offerPrice10);
        data.putInt((int) msg.offerSize10);
        writeAlpha(data, msg.quoteEntryId11, 10);
        data.putInt((int) msg.instrumentId11);
        data.putLong(msg.bidPrice11);
        data.putInt((int) msg.bidSize11);
        data.putLong(msg.offerPrice11);
        data.putInt((int) msg.offerSize11);
        writeAlpha(data, msg.quoteEntryId12, 10);
        data.putInt((int) msg.instrumentId12);
        data.putLong(msg.bidPrice12);
        data.putInt((int) msg.bidSize12);
        data.putLong(msg.offerPrice12);
        data.putInt((int) msg.offerSize12);
        writeAlpha(data, msg.quoteEntryId13, 10);
        data.putInt((int) msg.instrumentId13);
        data.putLong(msg.bidPrice13);
        data.putInt((int) msg.bidSize13);
        data.putLong(msg.offerPrice13);
        data.putInt((int) msg.offerSize13);
        writeAlpha(data, msg.quoteEntryId14, 10);
        data.putInt((int) msg.instrumentId14);
        data.putLong(msg.bidPrice14);
        data.putInt((int) msg.bidSize14);
        data.putLong(msg.offerPrice14);
        data.putInt((int) msg.offerSize14);
        writeAlpha(data, msg.quoteEntryId15, 10);
        data.putInt((int) msg.instrumentId15);
        data.putLong(msg.bidPrice15);
        data.putInt((int) msg.bidSize15);
        data.putLong(msg.offerPrice15);
        data.putInt((int) msg.offerSize15);
    }

    @Override
    public String toString() {
        return "NativeMassQuoteFlat{" +
                "massQuoteId=" + massQuoteId + ", " +
                "partitionId=" + partitionId + ", " +
                "cancelOnDisconnect=" + cancelOnDisconnect + ", " +
                "anonymity=" + anonymity + ", " +
                "account=" + account + ", " +
                "senderLocation=" + senderLocation + ", " +
                "enteringTrader=" + enteringTrader + ", " +
                "noTradeKey=" + noTradeKey + ", " +
                "memo=" + memo + ", " +
                "deskId=" + deskId + ", " +
                "totalQuoteEntries=" + totalQuoteEntries + ", " +
                "quoteEntryId1=" + quoteEntryId1 + ", " +
                "instrumentId1=" + instrumentId1 + ", " +
                "bidPrice1=" + bidPrice1 + ", " +
                "bidSize1=" + bidSize1 + ", " +
                "offerPrice1=" + offerPrice1 + ", " +
                "offerSize1=" + offerSize1 + ", " +
                "quoteEntryId2=" + quoteEntryId2 + ", " +
                "instrumentId2=" + instrumentId2 + ", " +
                "bidPrice2=" + bidPrice2 + ", " +
                "bidSize2=" + bidSize2 + ", " +
                "offerPrice2=" + offerPrice2 + ", " +
                "offerSize2=" + offerSize2 + ", " +
                "quoteEntryId3=" + quoteEntryId3 + ", " +
                "instrumentId3=" + instrumentId3 + ", " +
                "bidPrice3=" + bidPrice3 + ", " +
                "bidSize3=" + bidSize3 + ", " +
                "offerPrice3=" + offerPrice3 + ", " +
                "offerSize3=" + offerSize3 + ", " +
                "quoteEntryId4=" + quoteEntryId4 + ", " +
                "instrumentId4=" + instrumentId4 + ", " +
                "bidPrice4=" + bidPrice4 + ", " +
                "bidSize4=" + bidSize4 + ", " +
                "offerPrice4=" + offerPrice4 + ", " +
                "offerSize4=" + offerSize4 + ", " +
                "quoteEntryId5=" + quoteEntryId5 + ", " +
                "instrumentId5=" + instrumentId5 + ", " +
                "bidPrice5=" + bidPrice5 + ", " +
                "bidSize5=" + bidSize5 + ", " +
                "offerPrice5=" + offerPrice5 + ", " +
                "offerSize5=" + offerSize5 + ", " +
                "quoteEntryId6=" + quoteEntryId6 + ", " +
                "instrumentId6=" + instrumentId6 + ", " +
                "bidPrice6=" + bidPrice6 + ", " +
                "bidSize6=" + bidSize6 + ", " +
                "offerPrice6=" + offerPrice6 + ", " +
                "offerSize6=" + offerSize6 + ", " +
                "quoteEntryId7=" + quoteEntryId7 + ", " +
                "instrumentId7=" + instrumentId7 + ", " +
                "bidPrice7=" + bidPrice7 + ", " +
                "bidSize7=" + bidSize7 + ", " +
                "offerPrice7=" + offerPrice7 + ", " +
                "offerSize7=" + offerSize7 + ", " +
                "quoteEntryId8=" + quoteEntryId8 + ", " +
                "instrumentId8=" + instrumentId8 + ", " +
                "bidPrice8=" + bidPrice8 + ", " +
                "bidSize8=" + bidSize8 + ", " +
                "offerPrice8=" + offerPrice8 + ", " +
                "offerSize8=" + offerSize8 + ", " +
                "quoteEntryId9=" + quoteEntryId9 + ", " +
                "instrumentId9=" + instrumentId9 + ", " +
                "bidPrice9=" + bidPrice9 + ", " +
                "bidSize9=" + bidSize9 + ", " +
                "offerPrice9=" + offerPrice9 + ", " +
                "offerSize9=" + offerSize9 + ", " +
                "quoteEntryId10=" + quoteEntryId10 + ", " +
                "instrumentId10=" + instrumentId10 + ", " +
                "bidPrice10=" + bidPrice10 + ", " +
                "bidSize10=" + bidSize10 + ", " +
                "offerPrice10=" + offerPrice10 + ", " +
                "offerSize10=" + offerSize10 + ", " +
                "quoteEntryId11=" + quoteEntryId11 + ", " +
                "instrumentId11=" + instrumentId11 + ", " +
                "bidPrice11=" + bidPrice11 + ", " +
                "bidSize11=" + bidSize11 + ", " +
                "offerPrice11=" + offerPrice11 + ", " +
                "offerSize11=" + offerSize11 + ", " +
                "quoteEntryId12=" + quoteEntryId12 + ", " +
                "instrumentId12=" + instrumentId12 + ", " +
                "bidPrice12=" + bidPrice12 + ", " +
                "bidSize12=" + bidSize12 + ", " +
                "offerPrice12=" + offerPrice12 + ", " +
                "offerSize12=" + offerSize12 + ", " +
                "quoteEntryId13=" + quoteEntryId13 + ", " +
                "instrumentId13=" + instrumentId13 + ", " +
                "bidPrice13=" + bidPrice13 + ", " +
                "bidSize13=" + bidSize13 + ", " +
                "offerPrice13=" + offerPrice13 + ", " +
                "offerSize13=" + offerSize13 + ", " +
                "quoteEntryId14=" + quoteEntryId14 + ", " +
                "instrumentId14=" + instrumentId14 + ", " +
                "bidPrice14=" + bidPrice14 + ", " +
                "bidSize14=" + bidSize14 + ", " +
                "offerPrice14=" + offerPrice14 + ", " +
                "offerSize14=" + offerSize14 + ", " +
                "quoteEntryId15=" + quoteEntryId15 + ", " +
                "instrumentId15=" + instrumentId15 + ", " +
                "bidPrice15=" + bidPrice15 + ", " +
                "bidSize15=" + bidSize15 + ", " +
                "offerPrice15=" + offerPrice15 + ", " +
                "offerSize15=" + offerSize15 + '}';
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
