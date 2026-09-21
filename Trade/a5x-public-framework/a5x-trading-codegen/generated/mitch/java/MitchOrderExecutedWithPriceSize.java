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

	public java.math.BigDecimal priceAsDecimal() { return java.math.BigDecimal.valueOf(price, 4); }

	/**
	 * Numeric Identifier of the instrument
	 */
	public long instrumentId;

	/**
	 * Unique identifier of the trade
	 */
	public long tradeId;

	/**
	 * Converted price of the executed volatility of the options instrument
	 */
	public long lastOptPx;

	public java.math.BigDecimal lastOptPxAsDecimal() { return java.math.BigDecimal.valueOf(lastOptPx, 4); }

	/**
	 * Converted volatility of the executed price of the options instrument
	 */
	public long volatility;

	public java.math.BigDecimal volatilityAsDecimal() { return java.math.BigDecimal.valueOf(volatility, 4); }

	/**
	 * Underlying Reference Price related to converted value calculated upon an options instrument trade execution
	 */
	public long underlyingReferencePrice;

	public java.math.BigDecimal underlyingReferencePriceAsDecimal() { return java.math.BigDecimal.valueOf(underlyingReferencePrice, 4); }

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

	public java.math.BigDecimal notionalAmountAsDecimal() { return java.math.BigDecimal.valueOf(notionalAmount, 4); }

	/**
	 * The currency in which the notional is represented
	 */
	public String notionalCurrency;

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
	 * Parses OrderExecutedWithPriceSize message from ByteBuffer.
	 * 
	 * @param pOrderExecutedWithPriceSize the MitchOrderExecutedWithPriceSize instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchOrderExecutedWithPriceSize pOrderExecutedWithPriceSize, ByteBuffer pData) {
		pOrderExecutedWithPriceSize.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecutedWithPriceSize.orderId = pData.getLong();
		pOrderExecutedWithPriceSize.executedQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecutedWithPriceSize.price = pData.getLong();
		pOrderExecutedWithPriceSize.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecutedWithPriceSize.tradeId = pData.getLong();
		pOrderExecutedWithPriceSize.lastOptPx = pData.getLong();
		pOrderExecutedWithPriceSize.volatility = pData.getLong();
		pOrderExecutedWithPriceSize.underlyingReferencePrice = pData.getLong();
		pOrderExecutedWithPriceSize.tradingDateTime = pData.getLong();
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
		pOrderExecutedWithPriceSize.notionalAmount = pData.getLong();
		byte[] notionalCurrencyBytes = new byte[3];
		pData.get(notionalCurrencyBytes);
		pOrderExecutedWithPriceSize.notionalCurrency = new String(notionalCurrencyBytes).trim();
		pOrderExecutedWithPriceSize.publicationDateTime = pData.getLong();
		byte[] buyerFirmBytes = new byte[6];
		pData.get(buyerFirmBytes);
		pOrderExecutedWithPriceSize.buyerFirm = new String(buyerFirmBytes).trim();
		byte[] sellerFirmBytes = new byte[6];
		pData.get(sellerFirmBytes);
		pOrderExecutedWithPriceSize.sellerFirm = new String(sellerFirmBytes).trim();
		pOrderExecutedWithPriceSize.displayQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecutedWithPriceSize.printable = pData.get();
	}

	/**
	 * Encodes OrderExecutedWithPriceSize message to ByteBuffer.
	 * 
	 * @param pOrderExecutedWithPriceSize the MitchOrderExecutedWithPriceSize instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchOrderExecutedWithPriceSize pOrderExecutedWithPriceSize, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pOrderExecutedWithPriceSize.nanosecond, pData, false);
		pData.putLong(pOrderExecutedWithPriceSize.orderId);
		BitUtils.uint32ToByteArray(pOrderExecutedWithPriceSize.executedQuantity, pData, false);
		pData.putLong(pOrderExecutedWithPriceSize.price);
		BitUtils.uint32ToByteArray(pOrderExecutedWithPriceSize.instrumentId, pData, false);
		pData.putLong(pOrderExecutedWithPriceSize.tradeId);
		pData.putLong(pOrderExecutedWithPriceSize.lastOptPx);
		pData.putLong(pOrderExecutedWithPriceSize.volatility);
		pData.putLong(pOrderExecutedWithPriceSize.underlyingReferencePrice);
		pData.putLong(pOrderExecutedWithPriceSize.tradingDateTime);
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
		pData.putLong(pOrderExecutedWithPriceSize.notionalAmount);
		byte[] notionalCurrencyBytes = new byte[3];
		java.util.Arrays.fill(notionalCurrencyBytes, (byte) ' ');
		byte[] notionalCurrencySrc = pOrderExecutedWithPriceSize.notionalCurrency == null ? new byte[0] : pOrderExecutedWithPriceSize.notionalCurrency.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(notionalCurrencySrc, 0, notionalCurrencyBytes, 0, Math.min(notionalCurrencySrc.length, 3));
		pData.put(notionalCurrencyBytes);
		pData.putLong(pOrderExecutedWithPriceSize.publicationDateTime);
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
	}

	@Override
	public String toString() {
		return "MitchOrderExecutedWithPriceSize [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", executedQuantity=" + executedQuantity + ", price=" + price + ", instrumentId=" + instrumentId + ", tradeId=" + tradeId + ", lastOptPx=" + lastOptPx + ", volatility=" + volatility + ", underlyingReferencePrice=" + underlyingReferencePrice + ", tradingDateTime=" + tradingDateTime + ", instrumentIdentificationCode=" + instrumentIdentificationCode + ", instrumentIdentificationCodeType=" + instrumentIdentificationCodeType + ", currency=" + currency + ", venueOfExecution=" + venueOfExecution + ", priceNotation=" + priceNotation + ", notionalAmount=" + notionalAmount + ", notionalCurrency=" + notionalCurrency + ", publicationDateTime=" + publicationDateTime + ", buyerFirm=" + buyerFirm + ", sellerFirm=" + sellerFirm + ", displayQuantity=" + displayQuantity + ", printable=" + printable + "]";
	}
}
