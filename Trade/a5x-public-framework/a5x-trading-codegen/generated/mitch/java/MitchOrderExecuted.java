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
	 * Parses OrderExecuted message from ByteBuffer.
	 * 
	 * @param pOrderExecuted the MitchOrderExecuted instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchOrderExecuted pOrderExecuted, ByteBuffer pData) {
		pOrderExecuted.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecuted.orderId = pData.getLong();
		pOrderExecuted.executedQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecuted.price = pData.getLong();
		pOrderExecuted.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecuted.tradeId = pData.getLong();
		pOrderExecuted.lastOptPx = pData.getLong();
		pOrderExecuted.volatility = pData.getLong();
		pOrderExecuted.underlyingReferencePrice = pData.getLong();
		pOrderExecuted.tradingDateTime = pData.getLong();
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
		pOrderExecuted.notionalAmount = pData.getLong();
		byte[] notionalCurrencyBytes = new byte[3];
		pData.get(notionalCurrencyBytes);
		pOrderExecuted.notionalCurrency = new String(notionalCurrencyBytes).trim();
		pOrderExecuted.publicationDateTime = pData.getLong();
		byte[] buyerFirmBytes = new byte[6];
		pData.get(buyerFirmBytes);
		pOrderExecuted.buyerFirm = new String(buyerFirmBytes).trim();
		byte[] sellerFirmBytes = new byte[6];
		pData.get(sellerFirmBytes);
		pOrderExecuted.sellerFirm = new String(sellerFirmBytes).trim();
	}

	/**
	 * Encodes OrderExecuted message to ByteBuffer.
	 * 
	 * @param pOrderExecuted the MitchOrderExecuted instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchOrderExecuted pOrderExecuted, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pOrderExecuted.nanosecond, pData, false);
		pData.putLong(pOrderExecuted.orderId);
		BitUtils.uint32ToByteArray(pOrderExecuted.executedQuantity, pData, false);
		pData.putLong(pOrderExecuted.price);
		BitUtils.uint32ToByteArray(pOrderExecuted.instrumentId, pData, false);
		pData.putLong(pOrderExecuted.tradeId);
		pData.putLong(pOrderExecuted.lastOptPx);
		pData.putLong(pOrderExecuted.volatility);
		pData.putLong(pOrderExecuted.underlyingReferencePrice);
		pData.putLong(pOrderExecuted.tradingDateTime);
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
		pData.putLong(pOrderExecuted.notionalAmount);
		byte[] notionalCurrencyBytes = new byte[3];
		java.util.Arrays.fill(notionalCurrencyBytes, (byte) ' ');
		byte[] notionalCurrencySrc = pOrderExecuted.notionalCurrency == null ? new byte[0] : pOrderExecuted.notionalCurrency.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(notionalCurrencySrc, 0, notionalCurrencyBytes, 0, Math.min(notionalCurrencySrc.length, 3));
		pData.put(notionalCurrencyBytes);
		pData.putLong(pOrderExecuted.publicationDateTime);
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
	}

	@Override
	public String toString() {
		return "MitchOrderExecuted [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", executedQuantity=" + executedQuantity + ", price=" + price + ", instrumentId=" + instrumentId + ", tradeId=" + tradeId + ", lastOptPx=" + lastOptPx + ", volatility=" + volatility + ", underlyingReferencePrice=" + underlyingReferencePrice + ", tradingDateTime=" + tradingDateTime + ", instrumentIdentificationCode=" + instrumentIdentificationCode + ", instrumentIdentificationCodeType=" + instrumentIdentificationCodeType + ", currency=" + currency + ", venueOfExecution=" + venueOfExecution + ", priceNotation=" + priceNotation + ", notionalAmount=" + notionalAmount + ", notionalCurrency=" + notionalCurrency + ", publicationDateTime=" + publicationDateTime + ", buyerFirm=" + buyerFirm + ", sellerFirm=" + sellerFirm + "]";
	}
}
