package br.com.a5x.trd.pub.mitch.model;

import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent when an order is executed and includes remaining display quantity
 */
public class MitchOrderExecutedWithPriceSize {
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
	 * Displayed quantity of the order after the execution
	 */
	public long displayQuantity;

	/**
	 * N = Non-Printable, Y = Printable
	 */
	public byte printable;

	/**
	 * Side of the order
	 */
	public byte side;

	/**
	 * Date of the trading day (YYYYMMDD)
	 */
	public String tradeDate;

	/**
	 * Parses OrderExecutedWithPriceSize message from ByteBuffer.
	 * 
	 * @param pOrderExecutedWithPriceSize the MitchOrderExecutedWithPriceSize instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchOrderExecutedWithPriceSize pOrderExecutedWithPriceSize, ByteBuffer pData) {
		pOrderExecutedWithPriceSize.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecutedWithPriceSize.orderId = BitUtils.byteArrayToInt64(pData, false);
		pOrderExecutedWithPriceSize.executedQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecutedWithPriceSize.price = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pOrderExecutedWithPriceSize.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecutedWithPriceSize.tradeId = BitUtils.byteArrayToInt64(pData, false);
		pOrderExecutedWithPriceSize.reserved1 = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pOrderExecutedWithPriceSize.reserved2 = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pOrderExecutedWithPriceSize.reserved3 = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pOrderExecutedWithPriceSize.tradingDateTime = BitUtils.byteArrayToInt64(pData, false);
		byte[] instrumentIdentificationCodeBytes = new byte[12];
		pData.get(instrumentIdentificationCodeBytes);
		pOrderExecutedWithPriceSize.instrumentIdentificationCode = new String(instrumentIdentificationCodeBytes).trim();
		pOrderExecutedWithPriceSize.instrumentIdentificationCodeType = BitUtils.byteToUInt8(pData);
		byte[] currencyBytes = new byte[3];
		pData.get(currencyBytes);
		pOrderExecutedWithPriceSize.currency = new String(currencyBytes).trim();
		byte[] venueOfExecutionBytes = new byte[4];
		pData.get(venueOfExecutionBytes);
		pOrderExecutedWithPriceSize.venueOfExecution = new String(venueOfExecutionBytes).trim();
		pOrderExecutedWithPriceSize.priceNotation = BitUtils.byteToUInt8(pData);
		pOrderExecutedWithPriceSize.notionalAmount = BitUtils.byteArrayToSignMagnitude64(pData, false);
		byte[] reserved4Bytes = new byte[3];
		pData.get(reserved4Bytes);
		pOrderExecutedWithPriceSize.reserved4 = new String(reserved4Bytes).trim();
		pOrderExecutedWithPriceSize.publicationDateTime = BitUtils.byteArrayToInt64(pData, false);
		byte[] buyerFirmBytes = new byte[6];
		pData.get(buyerFirmBytes);
		pOrderExecutedWithPriceSize.buyerFirm = new String(buyerFirmBytes).trim();
		byte[] sellerFirmBytes = new byte[6];
		pData.get(sellerFirmBytes);
		pOrderExecutedWithPriceSize.sellerFirm = new String(sellerFirmBytes).trim();
		pOrderExecutedWithPriceSize.displayQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecutedWithPriceSize.printable = pData.get();
		pOrderExecutedWithPriceSize.side = pData.get();
		byte[] tradeDateBytes = new byte[8];
		pData.get(tradeDateBytes);
		pOrderExecutedWithPriceSize.tradeDate = new String(tradeDateBytes).trim();
	}

	/**
	 * Encodes OrderExecutedWithPriceSize message to ByteBuffer.
	 * 
	 * @param pOrderExecutedWithPriceSize the MitchOrderExecutedWithPriceSize instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchOrderExecutedWithPriceSize pOrderExecutedWithPriceSize, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pOrderExecutedWithPriceSize.nanosecond, pData, false);
		BitUtils.int64ToByteArray(pOrderExecutedWithPriceSize.orderId, pData, false);
		BitUtils.uint32ToByteArray(pOrderExecutedWithPriceSize.executedQuantity, pData, false);
		BitUtils.signMagnitude64ToByteArray(pOrderExecutedWithPriceSize.price, pData, false);
		BitUtils.uint32ToByteArray(pOrderExecutedWithPriceSize.instrumentId, pData, false);
		BitUtils.int64ToByteArray(pOrderExecutedWithPriceSize.tradeId, pData, false);
		BitUtils.signMagnitude64ToByteArray(pOrderExecutedWithPriceSize.reserved1, pData, false);
		BitUtils.signMagnitude64ToByteArray(pOrderExecutedWithPriceSize.reserved2, pData, false);
		BitUtils.signMagnitude64ToByteArray(pOrderExecutedWithPriceSize.reserved3, pData, false);
		BitUtils.int64ToByteArray(pOrderExecutedWithPriceSize.tradingDateTime, pData, false);
		byte[] instrumentIdentificationCodeBytes = new byte[12];
		java.util.Arrays.fill(instrumentIdentificationCodeBytes, (byte) ' ');
		byte[] instrumentIdentificationCodeSrc = pOrderExecutedWithPriceSize.instrumentIdentificationCode == null ? new byte[0] : pOrderExecutedWithPriceSize.instrumentIdentificationCode.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(instrumentIdentificationCodeSrc, 0, instrumentIdentificationCodeBytes, 0, Math.min(instrumentIdentificationCodeSrc.length, 12));
		pData.put(instrumentIdentificationCodeBytes);
		BitUtils.uint8ToByte(pOrderExecutedWithPriceSize.instrumentIdentificationCodeType, pData);
		byte[] currencyBytes = new byte[3];
		java.util.Arrays.fill(currencyBytes, (byte) ' ');
		byte[] currencySrc = pOrderExecutedWithPriceSize.currency == null ? new byte[0] : pOrderExecutedWithPriceSize.currency.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(currencySrc, 0, currencyBytes, 0, Math.min(currencySrc.length, 3));
		pData.put(currencyBytes);
		byte[] venueOfExecutionBytes = new byte[4];
		java.util.Arrays.fill(venueOfExecutionBytes, (byte) ' ');
		byte[] venueOfExecutionSrc = pOrderExecutedWithPriceSize.venueOfExecution == null ? new byte[0] : pOrderExecutedWithPriceSize.venueOfExecution.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(venueOfExecutionSrc, 0, venueOfExecutionBytes, 0, Math.min(venueOfExecutionSrc.length, 4));
		pData.put(venueOfExecutionBytes);
		BitUtils.uint8ToByte(pOrderExecutedWithPriceSize.priceNotation, pData);
		BitUtils.signMagnitude64ToByteArray(pOrderExecutedWithPriceSize.notionalAmount, pData, false);
		byte[] reserved4Bytes = new byte[3];
		java.util.Arrays.fill(reserved4Bytes, (byte) ' ');
		byte[] reserved4Src = pOrderExecutedWithPriceSize.reserved4 == null ? new byte[0] : pOrderExecutedWithPriceSize.reserved4.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(reserved4Src, 0, reserved4Bytes, 0, Math.min(reserved4Src.length, 3));
		pData.put(reserved4Bytes);
		BitUtils.int64ToByteArray(pOrderExecutedWithPriceSize.publicationDateTime, pData, false);
		byte[] buyerFirmBytes = new byte[6];
		java.util.Arrays.fill(buyerFirmBytes, (byte) ' ');
		byte[] buyerFirmSrc = pOrderExecutedWithPriceSize.buyerFirm == null ? new byte[0] : pOrderExecutedWithPriceSize.buyerFirm.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(buyerFirmSrc, 0, buyerFirmBytes, 0, Math.min(buyerFirmSrc.length, 6));
		pData.put(buyerFirmBytes);
		byte[] sellerFirmBytes = new byte[6];
		java.util.Arrays.fill(sellerFirmBytes, (byte) ' ');
		byte[] sellerFirmSrc = pOrderExecutedWithPriceSize.sellerFirm == null ? new byte[0] : pOrderExecutedWithPriceSize.sellerFirm.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(sellerFirmSrc, 0, sellerFirmBytes, 0, Math.min(sellerFirmSrc.length, 6));
		pData.put(sellerFirmBytes);
		BitUtils.uint32ToByteArray(pOrderExecutedWithPriceSize.displayQuantity, pData, false);
		pData.put(pOrderExecutedWithPriceSize.printable);
		pData.put(pOrderExecutedWithPriceSize.side);
		byte[] tradeDateBytes = new byte[8];
		java.util.Arrays.fill(tradeDateBytes, (byte) ' ');
		byte[] tradeDateSrc = pOrderExecutedWithPriceSize.tradeDate == null ? new byte[0] : pOrderExecutedWithPriceSize.tradeDate.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(tradeDateSrc, 0, tradeDateBytes, 0, Math.min(tradeDateSrc.length, 8));
		pData.put(tradeDateBytes);
	}

	@Override
	public String toString() {
		return "MitchOrderExecutedWithPriceSize [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", executedQuantity=" + executedQuantity + ", price=" + price + ", instrumentId=" + instrumentId + ", tradeId=" + tradeId + ", reserved1=" + reserved1 + ", reserved2=" + reserved2 + ", reserved3=" + reserved3 + ", tradingDateTime=" + tradingDateTime + ", instrumentIdentificationCode=" + instrumentIdentificationCode + ", instrumentIdentificationCodeType=" + instrumentIdentificationCodeType + ", currency=" + currency + ", venueOfExecution=" + venueOfExecution + ", priceNotation=" + priceNotation + ", notionalAmount=" + notionalAmount + ", reserved4=" + reserved4 + ", publicationDateTime=" + publicationDateTime + ", buyerFirm=" + buyerFirm + ", sellerFirm=" + sellerFirm + ", displayQuantity=" + displayQuantity + ", printable=" + (char) printable + ", side=" + (char) side + ", tradeDate=" + tradeDate + "]";
	}
}
