package br.com.a5x.trd.pub.mitch.model;

import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent when a trade occurs during an auction
 */
public class MitchAuctionTrade {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

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
	 * PTCancellationFlag. Indicates if the trade represented by the message has been cancelled (Value = CANC)
	 */
	public String cancellationFlag;

	/**
	 * PTAmendmentFlag. Indicates if the trade represented by the message has been amended (Value = AMND)
	 */
	public String amendmentFlag;

	/**
	 * A = Re-Opening Auction, C = Closing Auction, O = Opening Auction
	 */
	public byte auctionType;

	/**
	 * Date of the trading day (YYYYMMDD)
	 */
	public String tradeDate;

	/**
	 * Parses AuctionTrade message from ByteBuffer.
	 * 
	 * @param pAuctionTrade the MitchAuctionTrade instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchAuctionTrade pAuctionTrade, ByteBuffer pData) {
		pAuctionTrade.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pAuctionTrade.executedQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pAuctionTrade.price = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pAuctionTrade.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pAuctionTrade.tradeId = BitUtils.byteArrayToInt64(pData, false);
		pAuctionTrade.reserved1 = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pAuctionTrade.reserved2 = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pAuctionTrade.reserved3 = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pAuctionTrade.tradingDateTime = BitUtils.byteArrayToInt64(pData, false);
		byte[] instrumentIdentificationCodeBytes = new byte[12];
		pData.get(instrumentIdentificationCodeBytes);
		pAuctionTrade.instrumentIdentificationCode = new String(instrumentIdentificationCodeBytes).trim();
		pAuctionTrade.instrumentIdentificationCodeType = BitUtils.byteToUInt8(pData);
		byte[] currencyBytes = new byte[3];
		pData.get(currencyBytes);
		pAuctionTrade.currency = new String(currencyBytes).trim();
		byte[] venueOfExecutionBytes = new byte[4];
		pData.get(venueOfExecutionBytes);
		pAuctionTrade.venueOfExecution = new String(venueOfExecutionBytes).trim();
		pAuctionTrade.priceNotation = BitUtils.byteToUInt8(pData);
		pAuctionTrade.notionalAmount = BitUtils.byteArrayToSignMagnitude64(pData, false);
		byte[] reserved4Bytes = new byte[3];
		pData.get(reserved4Bytes);
		pAuctionTrade.reserved4 = new String(reserved4Bytes).trim();
		pAuctionTrade.publicationDateTime = BitUtils.byteArrayToInt64(pData, false);
		byte[] cancellationFlagBytes = new byte[4];
		pData.get(cancellationFlagBytes);
		pAuctionTrade.cancellationFlag = new String(cancellationFlagBytes).trim();
		byte[] amendmentFlagBytes = new byte[4];
		pData.get(amendmentFlagBytes);
		pAuctionTrade.amendmentFlag = new String(amendmentFlagBytes).trim();
		pAuctionTrade.auctionType = pData.get();
		byte[] tradeDateBytes = new byte[8];
		pData.get(tradeDateBytes);
		pAuctionTrade.tradeDate = new String(tradeDateBytes).trim();
	}

	/**
	 * Encodes AuctionTrade message to ByteBuffer.
	 * 
	 * @param pAuctionTrade the MitchAuctionTrade instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchAuctionTrade pAuctionTrade, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pAuctionTrade.nanosecond, pData, false);
		BitUtils.uint32ToByteArray(pAuctionTrade.executedQuantity, pData, false);
		BitUtils.signMagnitude64ToByteArray(pAuctionTrade.price, pData, false);
		BitUtils.uint32ToByteArray(pAuctionTrade.instrumentId, pData, false);
		BitUtils.int64ToByteArray(pAuctionTrade.tradeId, pData, false);
		BitUtils.signMagnitude64ToByteArray(pAuctionTrade.reserved1, pData, false);
		BitUtils.signMagnitude64ToByteArray(pAuctionTrade.reserved2, pData, false);
		BitUtils.signMagnitude64ToByteArray(pAuctionTrade.reserved3, pData, false);
		BitUtils.int64ToByteArray(pAuctionTrade.tradingDateTime, pData, false);
		byte[] instrumentIdentificationCodeBytes = new byte[12];
		java.util.Arrays.fill(instrumentIdentificationCodeBytes, (byte) ' ');
		byte[] instrumentIdentificationCodeSrc = pAuctionTrade.instrumentIdentificationCode == null ? new byte[0] : pAuctionTrade.instrumentIdentificationCode.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(instrumentIdentificationCodeSrc, 0, instrumentIdentificationCodeBytes, 0, Math.min(instrumentIdentificationCodeSrc.length, 12));
		pData.put(instrumentIdentificationCodeBytes);
		BitUtils.uint8ToByte(pAuctionTrade.instrumentIdentificationCodeType, pData);
		byte[] currencyBytes = new byte[3];
		java.util.Arrays.fill(currencyBytes, (byte) ' ');
		byte[] currencySrc = pAuctionTrade.currency == null ? new byte[0] : pAuctionTrade.currency.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(currencySrc, 0, currencyBytes, 0, Math.min(currencySrc.length, 3));
		pData.put(currencyBytes);
		byte[] venueOfExecutionBytes = new byte[4];
		java.util.Arrays.fill(venueOfExecutionBytes, (byte) ' ');
		byte[] venueOfExecutionSrc = pAuctionTrade.venueOfExecution == null ? new byte[0] : pAuctionTrade.venueOfExecution.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(venueOfExecutionSrc, 0, venueOfExecutionBytes, 0, Math.min(venueOfExecutionSrc.length, 4));
		pData.put(venueOfExecutionBytes);
		BitUtils.uint8ToByte(pAuctionTrade.priceNotation, pData);
		BitUtils.signMagnitude64ToByteArray(pAuctionTrade.notionalAmount, pData, false);
		byte[] reserved4Bytes = new byte[3];
		java.util.Arrays.fill(reserved4Bytes, (byte) ' ');
		byte[] reserved4Src = pAuctionTrade.reserved4 == null ? new byte[0] : pAuctionTrade.reserved4.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(reserved4Src, 0, reserved4Bytes, 0, Math.min(reserved4Src.length, 3));
		pData.put(reserved4Bytes);
		BitUtils.int64ToByteArray(pAuctionTrade.publicationDateTime, pData, false);
		byte[] cancellationFlagBytes = new byte[4];
		java.util.Arrays.fill(cancellationFlagBytes, (byte) ' ');
		byte[] cancellationFlagSrc = pAuctionTrade.cancellationFlag == null ? new byte[0] : pAuctionTrade.cancellationFlag.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(cancellationFlagSrc, 0, cancellationFlagBytes, 0, Math.min(cancellationFlagSrc.length, 4));
		pData.put(cancellationFlagBytes);
		byte[] amendmentFlagBytes = new byte[4];
		java.util.Arrays.fill(amendmentFlagBytes, (byte) ' ');
		byte[] amendmentFlagSrc = pAuctionTrade.amendmentFlag == null ? new byte[0] : pAuctionTrade.amendmentFlag.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(amendmentFlagSrc, 0, amendmentFlagBytes, 0, Math.min(amendmentFlagSrc.length, 4));
		pData.put(amendmentFlagBytes);
		pData.put(pAuctionTrade.auctionType);
		byte[] tradeDateBytes = new byte[8];
		java.util.Arrays.fill(tradeDateBytes, (byte) ' ');
		byte[] tradeDateSrc = pAuctionTrade.tradeDate == null ? new byte[0] : pAuctionTrade.tradeDate.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(tradeDateSrc, 0, tradeDateBytes, 0, Math.min(tradeDateSrc.length, 8));
		pData.put(tradeDateBytes);
	}

	@Override
	public String toString() {
		return "MitchAuctionTrade [" + "nanosecond=" + nanosecond + ", executedQuantity=" + executedQuantity + ", price=" + price + ", instrumentId=" + instrumentId + ", tradeId=" + tradeId + ", reserved1=" + reserved1 + ", reserved2=" + reserved2 + ", reserved3=" + reserved3 + ", tradingDateTime=" + tradingDateTime + ", instrumentIdentificationCode=" + instrumentIdentificationCode + ", instrumentIdentificationCodeType=" + instrumentIdentificationCodeType + ", currency=" + currency + ", venueOfExecution=" + venueOfExecution + ", priceNotation=" + priceNotation + ", notionalAmount=" + notionalAmount + ", reserved4=" + reserved4 + ", publicationDateTime=" + publicationDateTime + ", cancellationFlag=" + cancellationFlag + ", amendmentFlag=" + amendmentFlag + ", auctionType=" + (char) auctionType + ", tradeDate=" + tradeDate + "]";
	}
}
