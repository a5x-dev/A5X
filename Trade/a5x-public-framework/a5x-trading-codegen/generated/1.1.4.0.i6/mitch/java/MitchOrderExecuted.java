package br.com.a5x.trd.pub.mitch.model;

import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent when an order is executed in whole or in part
 */
public class MitchOrderExecuted {
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
	 * The price at which the trade was executed
	 */
	public long price;

	public java.math.BigDecimal priceAsDecimal() { return java.math.BigDecimal.valueOf(price, 8); }

	/**
	 * Numeric Identifier of the instrument
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
	 * Side of the order
	 */
	public byte side;

	/**
	 * Date of the trading day (YYYYMMDD)
	 */
	public String tradeDate;

	/**
	 * Parses OrderExecuted message from ByteBuffer.
	 * 
	 * @param pOrderExecuted the MitchOrderExecuted instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchOrderExecuted pOrderExecuted, ByteBuffer pData) {
		pOrderExecuted.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecuted.orderId = BitUtils.byteArrayToInt64(pData, false);
		pOrderExecuted.executedQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecuted.price = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pOrderExecuted.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecuted.tradeId = BitUtils.byteArrayToInt64(pData, false);
		pOrderExecuted.reserved1 = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pOrderExecuted.reserved2 = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pOrderExecuted.reserved3 = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pOrderExecuted.tradingDateTime = BitUtils.byteArrayToInt64(pData, false);
		byte[] instrumentIdentificationCodeBytes = new byte[12];
		pData.get(instrumentIdentificationCodeBytes);
		pOrderExecuted.instrumentIdentificationCode = new String(instrumentIdentificationCodeBytes).trim();
		pOrderExecuted.instrumentIdentificationCodeType = BitUtils.byteToUInt8(pData);
		byte[] currencyBytes = new byte[3];
		pData.get(currencyBytes);
		pOrderExecuted.currency = new String(currencyBytes).trim();
		byte[] venueOfExecutionBytes = new byte[4];
		pData.get(venueOfExecutionBytes);
		pOrderExecuted.venueOfExecution = new String(venueOfExecutionBytes).trim();
		pOrderExecuted.priceNotation = BitUtils.byteToUInt8(pData);
		pOrderExecuted.notionalAmount = BitUtils.byteArrayToSignMagnitude64(pData, false);
		byte[] reserved4Bytes = new byte[3];
		pData.get(reserved4Bytes);
		pOrderExecuted.reserved4 = new String(reserved4Bytes).trim();
		pOrderExecuted.publicationDateTime = BitUtils.byteArrayToInt64(pData, false);
		byte[] buyerFirmBytes = new byte[6];
		pData.get(buyerFirmBytes);
		pOrderExecuted.buyerFirm = new String(buyerFirmBytes).trim();
		byte[] sellerFirmBytes = new byte[6];
		pData.get(sellerFirmBytes);
		pOrderExecuted.sellerFirm = new String(sellerFirmBytes).trim();
		pOrderExecuted.side = pData.get();
		byte[] tradeDateBytes = new byte[8];
		pData.get(tradeDateBytes);
		pOrderExecuted.tradeDate = new String(tradeDateBytes).trim();
	}

	/**
	 * Encodes OrderExecuted message to ByteBuffer.
	 * 
	 * @param pOrderExecuted the MitchOrderExecuted instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchOrderExecuted pOrderExecuted, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pOrderExecuted.nanosecond, pData, false);
		BitUtils.int64ToByteArray(pOrderExecuted.orderId, pData, false);
		BitUtils.uint32ToByteArray(pOrderExecuted.executedQuantity, pData, false);
		BitUtils.signMagnitude64ToByteArray(pOrderExecuted.price, pData, false);
		BitUtils.uint32ToByteArray(pOrderExecuted.instrumentId, pData, false);
		BitUtils.int64ToByteArray(pOrderExecuted.tradeId, pData, false);
		BitUtils.signMagnitude64ToByteArray(pOrderExecuted.reserved1, pData, false);
		BitUtils.signMagnitude64ToByteArray(pOrderExecuted.reserved2, pData, false);
		BitUtils.signMagnitude64ToByteArray(pOrderExecuted.reserved3, pData, false);
		BitUtils.int64ToByteArray(pOrderExecuted.tradingDateTime, pData, false);
		byte[] instrumentIdentificationCodeBytes = new byte[12];
		java.util.Arrays.fill(instrumentIdentificationCodeBytes, (byte) ' ');
		byte[] instrumentIdentificationCodeSrc = pOrderExecuted.instrumentIdentificationCode == null ? new byte[0] : pOrderExecuted.instrumentIdentificationCode.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(instrumentIdentificationCodeSrc, 0, instrumentIdentificationCodeBytes, 0, Math.min(instrumentIdentificationCodeSrc.length, 12));
		pData.put(instrumentIdentificationCodeBytes);
		BitUtils.uint8ToByte(pOrderExecuted.instrumentIdentificationCodeType, pData);
		byte[] currencyBytes = new byte[3];
		java.util.Arrays.fill(currencyBytes, (byte) ' ');
		byte[] currencySrc = pOrderExecuted.currency == null ? new byte[0] : pOrderExecuted.currency.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(currencySrc, 0, currencyBytes, 0, Math.min(currencySrc.length, 3));
		pData.put(currencyBytes);
		byte[] venueOfExecutionBytes = new byte[4];
		java.util.Arrays.fill(venueOfExecutionBytes, (byte) ' ');
		byte[] venueOfExecutionSrc = pOrderExecuted.venueOfExecution == null ? new byte[0] : pOrderExecuted.venueOfExecution.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(venueOfExecutionSrc, 0, venueOfExecutionBytes, 0, Math.min(venueOfExecutionSrc.length, 4));
		pData.put(venueOfExecutionBytes);
		BitUtils.uint8ToByte(pOrderExecuted.priceNotation, pData);
		BitUtils.signMagnitude64ToByteArray(pOrderExecuted.notionalAmount, pData, false);
		byte[] reserved4Bytes = new byte[3];
		java.util.Arrays.fill(reserved4Bytes, (byte) ' ');
		byte[] reserved4Src = pOrderExecuted.reserved4 == null ? new byte[0] : pOrderExecuted.reserved4.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(reserved4Src, 0, reserved4Bytes, 0, Math.min(reserved4Src.length, 3));
		pData.put(reserved4Bytes);
		BitUtils.int64ToByteArray(pOrderExecuted.publicationDateTime, pData, false);
		byte[] buyerFirmBytes = new byte[6];
		java.util.Arrays.fill(buyerFirmBytes, (byte) ' ');
		byte[] buyerFirmSrc = pOrderExecuted.buyerFirm == null ? new byte[0] : pOrderExecuted.buyerFirm.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(buyerFirmSrc, 0, buyerFirmBytes, 0, Math.min(buyerFirmSrc.length, 6));
		pData.put(buyerFirmBytes);
		byte[] sellerFirmBytes = new byte[6];
		java.util.Arrays.fill(sellerFirmBytes, (byte) ' ');
		byte[] sellerFirmSrc = pOrderExecuted.sellerFirm == null ? new byte[0] : pOrderExecuted.sellerFirm.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(sellerFirmSrc, 0, sellerFirmBytes, 0, Math.min(sellerFirmSrc.length, 6));
		pData.put(sellerFirmBytes);
		pData.put(pOrderExecuted.side);
		byte[] tradeDateBytes = new byte[8];
		java.util.Arrays.fill(tradeDateBytes, (byte) ' ');
		byte[] tradeDateSrc = pOrderExecuted.tradeDate == null ? new byte[0] : pOrderExecuted.tradeDate.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(tradeDateSrc, 0, tradeDateBytes, 0, Math.min(tradeDateSrc.length, 8));
		pData.put(tradeDateBytes);
	}

	@Override
	public String toString() {
		return "MitchOrderExecuted [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", executedQuantity=" + executedQuantity + ", price=" + price + ", instrumentId=" + instrumentId + ", tradeId=" + tradeId + ", reserved1=" + reserved1 + ", reserved2=" + reserved2 + ", reserved3=" + reserved3 + ", tradingDateTime=" + tradingDateTime + ", instrumentIdentificationCode=" + instrumentIdentificationCode + ", instrumentIdentificationCodeType=" + instrumentIdentificationCodeType + ", currency=" + currency + ", venueOfExecution=" + venueOfExecution + ", priceNotation=" + priceNotation + ", notionalAmount=" + notionalAmount + ", reserved4=" + reserved4 + ", publicationDateTime=" + publicationDateTime + ", buyerFirm=" + buyerFirm + ", sellerFirm=" + sellerFirm + ", side=" + (char) side + ", tradeDate=" + tradeDate + "]";
	}
}
