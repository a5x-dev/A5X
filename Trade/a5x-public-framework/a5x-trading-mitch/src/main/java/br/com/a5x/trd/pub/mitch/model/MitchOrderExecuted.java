package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
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
	public BigInteger orderId;

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
	public BigInteger tradeId;

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
	public BigInteger tradingDateTime;

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
	public BigInteger publicationDateTime;

	/**
	 * Firm ID of the party firm
	 */
	public String firm;

	/**
	 * Firm ID of the counter party firm
	 */
	public String contraFirm;

	/**
	 * Parses OrderExecuted message from ByteBuffer.
	 * 
	 * @param pOrderExecuted the MitchOrderExecuted instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchOrderExecuted pOrderExecuted, ByteBuffer pData) {
		pOrderExecuted.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecuted.orderId = BitUtils.byteArrayToUInt64(pData, false);
		pOrderExecuted.executedQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecuted.price = pData.getLong();
		pOrderExecuted.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pOrderExecuted.tradeId = BitUtils.byteArrayToUInt64(pData, false);
		pOrderExecuted.lastOptPx = pData.getLong();
		pOrderExecuted.volatility = pData.getLong();
		pOrderExecuted.underlyingReferencePrice = pData.getLong();
		pOrderExecuted.tradingDateTime = BitUtils.byteArrayToUInt64(pData, false);
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
		pOrderExecuted.publicationDateTime = BitUtils.byteArrayToUInt64(pData, false);
		byte[] firmBytes = new byte[6];
		pData.get(firmBytes);
		pOrderExecuted.firm = new String(firmBytes).trim();
		byte[] contraFirmBytes = new byte[6];
		pData.get(contraFirmBytes);
		pOrderExecuted.contraFirm = new String(contraFirmBytes).trim();
	}

	/**
	 * Encodes OrderExecuted message to ByteBuffer.
	 * 
	 * @param pOrderExecuted the MitchOrderExecuted instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchOrderExecuted pOrderExecuted, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pOrderExecuted.nanosecond, pData, false);
		BitUtils.uint64ToByteArray(pOrderExecuted.orderId, pData, false);
		BitUtils.uint32ToByteArray(pOrderExecuted.executedQuantity, pData, false);
		pData.putLong(pOrderExecuted.price);
		BitUtils.uint32ToByteArray(pOrderExecuted.instrumentId, pData, false);
		BitUtils.uint64ToByteArray(pOrderExecuted.tradeId, pData, false);
		pData.putLong(pOrderExecuted.lastOptPx);
		pData.putLong(pOrderExecuted.volatility);
		pData.putLong(pOrderExecuted.underlyingReferencePrice);
		BitUtils.uint64ToByteArray(pOrderExecuted.tradingDateTime, pData, false);
		byte[] instrumentIdentificationCodeBytes = new byte[12];
		byte[] instrumentIdentificationCodeSrc = pOrderExecuted.instrumentIdentificationCode.getBytes();
		System.arraycopy(instrumentIdentificationCodeSrc, 0, instrumentIdentificationCodeBytes, 0, Math.min(instrumentIdentificationCodeSrc.length, 12));
		pData.put(instrumentIdentificationCodeBytes);
		BitUtils.uint8ToByte(pOrderExecuted.instrumentIdentificationCodeType, pData);
		byte[] currencyBytes = new byte[3];
		byte[] currencySrc = pOrderExecuted.currency.getBytes();
		System.arraycopy(currencySrc, 0, currencyBytes, 0, Math.min(currencySrc.length, 3));
		pData.put(currencyBytes);
		byte[] venueOfExecutionBytes = new byte[4];
		byte[] venueOfExecutionSrc = pOrderExecuted.venueOfExecution.getBytes();
		System.arraycopy(venueOfExecutionSrc, 0, venueOfExecutionBytes, 0, Math.min(venueOfExecutionSrc.length, 4));
		pData.put(venueOfExecutionBytes);
		BitUtils.uint8ToByte(pOrderExecuted.priceNotation, pData);
		pData.putLong(pOrderExecuted.notionalAmount);
		byte[] notionalCurrencyBytes = new byte[3];
		byte[] notionalCurrencySrc = pOrderExecuted.notionalCurrency.getBytes();
		System.arraycopy(notionalCurrencySrc, 0, notionalCurrencyBytes, 0, Math.min(notionalCurrencySrc.length, 3));
		pData.put(notionalCurrencyBytes);
		BitUtils.uint64ToByteArray(pOrderExecuted.publicationDateTime, pData, false);
		byte[] firmBytes = new byte[6];
		byte[] firmSrc = pOrderExecuted.firm.getBytes();
		System.arraycopy(firmSrc, 0, firmBytes, 0, Math.min(firmSrc.length, 6));
		pData.put(firmBytes);
		byte[] contraFirmBytes = new byte[6];
		byte[] contraFirmSrc = pOrderExecuted.contraFirm.getBytes();
		System.arraycopy(contraFirmSrc, 0, contraFirmBytes, 0, Math.min(contraFirmSrc.length, 6));
		pData.put(contraFirmBytes);
	}

	@Override
	public String toString() {
		return "MitchOrderExecuted [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", executedQuantity=" + executedQuantity + ", price=" + price + ", instrumentId=" + instrumentId + ", tradeId=" + tradeId + ", lastOptPx=" + lastOptPx + ", volatility=" + volatility + ", underlyingReferencePrice=" + underlyingReferencePrice + ", tradingDateTime=" + tradingDateTime + ", instrumentIdentificationCode=" + instrumentIdentificationCode + ", instrumentIdentificationCodeType=" + instrumentIdentificationCodeType + ", currency=" + currency + ", venueOfExecution=" + venueOfExecution + ", priceNotation=" + priceNotation + ", notionalAmount=" + notionalAmount + ", notionalCurrency=" + notionalCurrency + ", publicationDateTime=" + publicationDateTime + ", firm=" + firm + ", contraFirm=" + contraFirm + "]";
	}
}
