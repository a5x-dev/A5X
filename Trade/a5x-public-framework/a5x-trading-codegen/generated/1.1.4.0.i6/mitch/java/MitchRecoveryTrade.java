package br.com.a5x.trd.pub.mitch.model;

import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent during recovery to provide historical trade information
 */
public class MitchRecoveryTrade {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Identifier for the order
	 */
	public long orderId;

	/**
	 * Quantity executed
	 */
	public long executedQuantity;

	/**
	 * Executed price
	 */
	public long price;

	public java.math.BigDecimal priceAsDecimal() { return java.math.BigDecimal.valueOf(price, 8); }

	/**
	 * Numeric identifier of the instrument
	 */
	public long instrumentId;

	/**
	 * Unique identifier of the trade
	 */
	public long tradeId;

	/**
	 * Reserved for future use.
	 */
	public long reserved1;

	public java.math.BigDecimal reserved1AsDecimal() { return java.math.BigDecimal.valueOf(reserved1, 8); }

	/**
	 * Reserved for future use.
	 */
	public long reserved2;

	public java.math.BigDecimal reserved2AsDecimal() { return java.math.BigDecimal.valueOf(reserved2, 8); }

	/**
	 * Reserved for future use.
	 */
	public long reserved3;

	public java.math.BigDecimal reserved3AsDecimal() { return java.math.BigDecimal.valueOf(reserved3, 8); }

	/**
	 * Date and time when the trade was executed [Epoch]
	 */
	public long tradingDateTime;

	/**
	 * Instrument identification code
	 */
	public String instrumentIdentificationCode;

	/**
	 * 1 = ISIN Code
	 */
	public int instrumentIdentificationCodeType;

	/**
	 * Currency in which the price of the trade is expressed
	 */
	public String currency;

	/**
	 * Identification of the venue where the trade was executed
	 */
	public String venueOfExecution;

	/**
	 * 1 = MONE - Monetary value, 2 = PERC - Percentage, 3 = YIEL - Yield
	 */
	public int priceNotation;

	/**
	 * Notional value relevant to the security
	 */
	public long notionalAmount;

	public java.math.BigDecimal notionalAmountAsDecimal() { return java.math.BigDecimal.valueOf(notionalAmount, 8); }

	/**
	 * Reserved for future use.
	 */
	public String reserved4;

	/**
	 * Date and time when the trade was published by the trading venue [Epoch]
	 */
	public long publicationDateTime;

	/**
	 * Buyer Firm ID
	 */
	public String buyerFirm;

	/**
	 * Seller Firm ID
	 */
	public String sellerFirm;

	/**
	 * PTCancellationFlag. Indicates if the trade represented by the message has been cancelled (Value = CANC)
	 */
	public String cancellationFlag;

	/**
	 * PTAmendmentFlag. Indicates if the trade represented by the message has been amended (Value = AMND)
	 */
	public String amendmentFlag;

	/**
	 * 1 = Regular, 11 = Negotiated Trades
	 */
	public int subBook;

	/**
	 * Bit 0: Trade Condition Flag, Bit 1: Internal Crossed Trade, Bit 5: RLP
	 */
	public byte flags;

	/**
	 * A = Re-Opening Auction, C = Closing Auction, O = Opening Auction
	 */
	public byte auctionType;

	/**
	 * Date of the trading day (YYYYMMDD)
	 */
	public String tradeDate;

	/**
	 * Parses RecoveryTrade message from ByteBuffer.
	 * 
	 * @param pRecoveryTrade the MitchRecoveryTrade instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchRecoveryTrade pRecoveryTrade, ByteBuffer pData) {
		pRecoveryTrade.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pRecoveryTrade.orderId = BitUtils.byteArrayToInt64(pData, false);
		pRecoveryTrade.executedQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pRecoveryTrade.price = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pRecoveryTrade.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pRecoveryTrade.tradeId = BitUtils.byteArrayToInt64(pData, false);
		pRecoveryTrade.reserved1 = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pRecoveryTrade.reserved2 = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pRecoveryTrade.reserved3 = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pRecoveryTrade.tradingDateTime = BitUtils.byteArrayToInt64(pData, false);
		byte[] instrumentIdentificationCodeBytes = new byte[12];
		pData.get(instrumentIdentificationCodeBytes);
		pRecoveryTrade.instrumentIdentificationCode = new String(instrumentIdentificationCodeBytes).trim();
		pRecoveryTrade.instrumentIdentificationCodeType = BitUtils.byteToUInt8(pData);
		byte[] currencyBytes = new byte[3];
		pData.get(currencyBytes);
		pRecoveryTrade.currency = new String(currencyBytes).trim();
		byte[] venueOfExecutionBytes = new byte[4];
		pData.get(venueOfExecutionBytes);
		pRecoveryTrade.venueOfExecution = new String(venueOfExecutionBytes).trim();
		pRecoveryTrade.priceNotation = BitUtils.byteToUInt8(pData);
		pRecoveryTrade.notionalAmount = BitUtils.byteArrayToSignMagnitude64(pData, false);
		byte[] reserved4Bytes = new byte[3];
		pData.get(reserved4Bytes);
		pRecoveryTrade.reserved4 = new String(reserved4Bytes).trim();
		pRecoveryTrade.publicationDateTime = BitUtils.byteArrayToInt64(pData, false);
		byte[] buyerFirmBytes = new byte[6];
		pData.get(buyerFirmBytes);
		pRecoveryTrade.buyerFirm = new String(buyerFirmBytes).trim();
		byte[] sellerFirmBytes = new byte[6];
		pData.get(sellerFirmBytes);
		pRecoveryTrade.sellerFirm = new String(sellerFirmBytes).trim();
		byte[] cancellationFlagBytes = new byte[4];
		pData.get(cancellationFlagBytes);
		pRecoveryTrade.cancellationFlag = new String(cancellationFlagBytes).trim();
		byte[] amendmentFlagBytes = new byte[4];
		pData.get(amendmentFlagBytes);
		pRecoveryTrade.amendmentFlag = new String(amendmentFlagBytes).trim();
		pRecoveryTrade.subBook = BitUtils.byteToUInt8(pData);
		pRecoveryTrade.flags = pData.get();
		pRecoveryTrade.auctionType = pData.get();
		byte[] tradeDateBytes = new byte[8];
		pData.get(tradeDateBytes);
		pRecoveryTrade.tradeDate = new String(tradeDateBytes).trim();
	}

	/**
	 * Encodes RecoveryTrade message to ByteBuffer.
	 * 
	 * @param pRecoveryTrade the MitchRecoveryTrade instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchRecoveryTrade pRecoveryTrade, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pRecoveryTrade.nanosecond, pData, false);
		BitUtils.int64ToByteArray(pRecoveryTrade.orderId, pData, false);
		BitUtils.uint32ToByteArray(pRecoveryTrade.executedQuantity, pData, false);
		BitUtils.signMagnitude64ToByteArray(pRecoveryTrade.price, pData, false);
		BitUtils.uint32ToByteArray(pRecoveryTrade.instrumentId, pData, false);
		BitUtils.int64ToByteArray(pRecoveryTrade.tradeId, pData, false);
		BitUtils.signMagnitude64ToByteArray(pRecoveryTrade.reserved1, pData, false);
		BitUtils.signMagnitude64ToByteArray(pRecoveryTrade.reserved2, pData, false);
		BitUtils.signMagnitude64ToByteArray(pRecoveryTrade.reserved3, pData, false);
		BitUtils.int64ToByteArray(pRecoveryTrade.tradingDateTime, pData, false);
		byte[] instrumentIdentificationCodeBytes = new byte[12];
		java.util.Arrays.fill(instrumentIdentificationCodeBytes, (byte) ' ');
		byte[] instrumentIdentificationCodeSrc = pRecoveryTrade.instrumentIdentificationCode == null ? new byte[0] : pRecoveryTrade.instrumentIdentificationCode.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(instrumentIdentificationCodeSrc, 0, instrumentIdentificationCodeBytes, 0, Math.min(instrumentIdentificationCodeSrc.length, 12));
		pData.put(instrumentIdentificationCodeBytes);
		BitUtils.uint8ToByte(pRecoveryTrade.instrumentIdentificationCodeType, pData);
		byte[] currencyBytes = new byte[3];
		java.util.Arrays.fill(currencyBytes, (byte) ' ');
		byte[] currencySrc = pRecoveryTrade.currency == null ? new byte[0] : pRecoveryTrade.currency.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(currencySrc, 0, currencyBytes, 0, Math.min(currencySrc.length, 3));
		pData.put(currencyBytes);
		byte[] venueOfExecutionBytes = new byte[4];
		java.util.Arrays.fill(venueOfExecutionBytes, (byte) ' ');
		byte[] venueOfExecutionSrc = pRecoveryTrade.venueOfExecution == null ? new byte[0] : pRecoveryTrade.venueOfExecution.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(venueOfExecutionSrc, 0, venueOfExecutionBytes, 0, Math.min(venueOfExecutionSrc.length, 4));
		pData.put(venueOfExecutionBytes);
		BitUtils.uint8ToByte(pRecoveryTrade.priceNotation, pData);
		BitUtils.signMagnitude64ToByteArray(pRecoveryTrade.notionalAmount, pData, false);
		byte[] reserved4Bytes = new byte[3];
		java.util.Arrays.fill(reserved4Bytes, (byte) ' ');
		byte[] reserved4Src = pRecoveryTrade.reserved4 == null ? new byte[0] : pRecoveryTrade.reserved4.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(reserved4Src, 0, reserved4Bytes, 0, Math.min(reserved4Src.length, 3));
		pData.put(reserved4Bytes);
		BitUtils.int64ToByteArray(pRecoveryTrade.publicationDateTime, pData, false);
		byte[] buyerFirmBytes = new byte[6];
		java.util.Arrays.fill(buyerFirmBytes, (byte) ' ');
		byte[] buyerFirmSrc = pRecoveryTrade.buyerFirm == null ? new byte[0] : pRecoveryTrade.buyerFirm.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(buyerFirmSrc, 0, buyerFirmBytes, 0, Math.min(buyerFirmSrc.length, 6));
		pData.put(buyerFirmBytes);
		byte[] sellerFirmBytes = new byte[6];
		java.util.Arrays.fill(sellerFirmBytes, (byte) ' ');
		byte[] sellerFirmSrc = pRecoveryTrade.sellerFirm == null ? new byte[0] : pRecoveryTrade.sellerFirm.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(sellerFirmSrc, 0, sellerFirmBytes, 0, Math.min(sellerFirmSrc.length, 6));
		pData.put(sellerFirmBytes);
		byte[] cancellationFlagBytes = new byte[4];
		java.util.Arrays.fill(cancellationFlagBytes, (byte) ' ');
		byte[] cancellationFlagSrc = pRecoveryTrade.cancellationFlag == null ? new byte[0] : pRecoveryTrade.cancellationFlag.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(cancellationFlagSrc, 0, cancellationFlagBytes, 0, Math.min(cancellationFlagSrc.length, 4));
		pData.put(cancellationFlagBytes);
		byte[] amendmentFlagBytes = new byte[4];
		java.util.Arrays.fill(amendmentFlagBytes, (byte) ' ');
		byte[] amendmentFlagSrc = pRecoveryTrade.amendmentFlag == null ? new byte[0] : pRecoveryTrade.amendmentFlag.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(amendmentFlagSrc, 0, amendmentFlagBytes, 0, Math.min(amendmentFlagSrc.length, 4));
		pData.put(amendmentFlagBytes);
		BitUtils.uint8ToByte(pRecoveryTrade.subBook, pData);
		pData.put(pRecoveryTrade.flags);
		pData.put(pRecoveryTrade.auctionType);
		byte[] tradeDateBytes = new byte[8];
		java.util.Arrays.fill(tradeDateBytes, (byte) ' ');
		byte[] tradeDateSrc = pRecoveryTrade.tradeDate == null ? new byte[0] : pRecoveryTrade.tradeDate.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(tradeDateSrc, 0, tradeDateBytes, 0, Math.min(tradeDateSrc.length, 8));
		pData.put(tradeDateBytes);
	}

	@Override
	public String toString() {
		return "MitchRecoveryTrade [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", executedQuantity=" + executedQuantity + ", price=" + price + ", instrumentId=" + instrumentId + ", tradeId=" + tradeId + ", reserved1=" + reserved1 + ", reserved2=" + reserved2 + ", reserved3=" + reserved3 + ", tradingDateTime=" + tradingDateTime + ", instrumentIdentificationCode=" + instrumentIdentificationCode + ", instrumentIdentificationCodeType=" + instrumentIdentificationCodeType + ", currency=" + currency + ", venueOfExecution=" + venueOfExecution + ", priceNotation=" + priceNotation + ", notionalAmount=" + notionalAmount + ", reserved4=" + reserved4 + ", publicationDateTime=" + publicationDateTime + ", buyerFirm=" + buyerFirm + ", sellerFirm=" + sellerFirm + ", cancellationFlag=" + cancellationFlag + ", amendmentFlag=" + amendmentFlag + ", subBook=" + subBook + ", flags=" + flags + ", auctionType=" + (char) auctionType + ", tradeDate=" + tradeDate + "]";
	}
}
