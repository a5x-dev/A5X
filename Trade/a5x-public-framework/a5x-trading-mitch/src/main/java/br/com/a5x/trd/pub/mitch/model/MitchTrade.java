package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent when a non-displayed trade occurs
 */
public class MitchTrade {
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
	 * Executed price
	 */
	public long price;

	public java.math.BigDecimal priceAsDecimal() { return java.math.BigDecimal.valueOf(price, 4); }

	/**
	 * Numeric identifier of the instrument
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
	 * Indicates if the trade has been cancelled (Value = CANC)
	 */
	public String cancellationFlag;

	/**
	 * Indicates if the trade has been amended (Value = AMND)
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
	 * Parses Trade message from ByteBuffer.
	 * 
	 * @param pTrade the MitchTrade instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchTrade pTrade, ByteBuffer pData) {
		pTrade.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pTrade.orderId = BitUtils.byteArrayToUInt64(pData, false);
		pTrade.executedQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pTrade.price = pData.getLong();
		pTrade.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pTrade.tradeId = BitUtils.byteArrayToUInt64(pData, false);
		pTrade.lastOptPx = pData.getLong();
		pTrade.volatility = pData.getLong();
		pTrade.underlyingReferencePrice = pData.getLong();
		pTrade.tradingDateTime = BitUtils.byteArrayToUInt64(pData, false);
		byte[] instrumentIdentificationCodeBytes = new byte[12];
		pData.get(instrumentIdentificationCodeBytes);
		pTrade.instrumentIdentificationCode = new String(instrumentIdentificationCodeBytes).trim();
		pTrade.instrumentIdentificationCodeType = BitUtils.byteToUInt8(pData);
		byte[] currencyBytes = new byte[3];
		pData.get(currencyBytes);
		pTrade.currency = new String(currencyBytes).trim();
		byte[] venueOfExecutionBytes = new byte[4];
		pData.get(venueOfExecutionBytes);
		pTrade.venueOfExecution = new String(venueOfExecutionBytes).trim();
		pTrade.priceNotation = BitUtils.byteToUInt8(pData);
		pTrade.notionalAmount = pData.getLong();
		byte[] notionalCurrencyBytes = new byte[3];
		pData.get(notionalCurrencyBytes);
		pTrade.notionalCurrency = new String(notionalCurrencyBytes).trim();
		pTrade.publicationDateTime = BitUtils.byteArrayToUInt64(pData, false);
		byte[] firmBytes = new byte[6];
		pData.get(firmBytes);
		pTrade.firm = new String(firmBytes).trim();
		byte[] contraFirmBytes = new byte[6];
		pData.get(contraFirmBytes);
		pTrade.contraFirm = new String(contraFirmBytes).trim();
		byte[] cancellationFlagBytes = new byte[4];
		pData.get(cancellationFlagBytes);
		pTrade.cancellationFlag = new String(cancellationFlagBytes).trim();
		byte[] amendmentFlagBytes = new byte[4];
		pData.get(amendmentFlagBytes);
		pTrade.amendmentFlag = new String(amendmentFlagBytes).trim();
		pTrade.subBook = BitUtils.byteToUInt8(pData);
		pTrade.flags = pData.get();
	}

	/**
	 * Encodes Trade message to ByteBuffer.
	 * 
	 * @param pTrade the MitchTrade instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchTrade pTrade, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pTrade.nanosecond, pData, false);
		BitUtils.uint64ToByteArray(pTrade.orderId, pData, false);
		BitUtils.uint32ToByteArray(pTrade.executedQuantity, pData, false);
		pData.putLong(pTrade.price);
		BitUtils.uint32ToByteArray(pTrade.instrumentId, pData, false);
		BitUtils.uint64ToByteArray(pTrade.tradeId, pData, false);
		pData.putLong(pTrade.lastOptPx);
		pData.putLong(pTrade.volatility);
		pData.putLong(pTrade.underlyingReferencePrice);
		BitUtils.uint64ToByteArray(pTrade.tradingDateTime, pData, false);
		byte[] instrumentIdentificationCodeBytes = new byte[12];
		byte[] instrumentIdentificationCodeSrc = pTrade.instrumentIdentificationCode.getBytes();
		System.arraycopy(instrumentIdentificationCodeSrc, 0, instrumentIdentificationCodeBytes, 0, Math.min(instrumentIdentificationCodeSrc.length, 12));
		pData.put(instrumentIdentificationCodeBytes);
		BitUtils.uint8ToByte(pTrade.instrumentIdentificationCodeType, pData);
		byte[] currencyBytes = new byte[3];
		byte[] currencySrc = pTrade.currency.getBytes();
		System.arraycopy(currencySrc, 0, currencyBytes, 0, Math.min(currencySrc.length, 3));
		pData.put(currencyBytes);
		byte[] venueOfExecutionBytes = new byte[4];
		byte[] venueOfExecutionSrc = pTrade.venueOfExecution.getBytes();
		System.arraycopy(venueOfExecutionSrc, 0, venueOfExecutionBytes, 0, Math.min(venueOfExecutionSrc.length, 4));
		pData.put(venueOfExecutionBytes);
		BitUtils.uint8ToByte(pTrade.priceNotation, pData);
		pData.putLong(pTrade.notionalAmount);
		byte[] notionalCurrencyBytes = new byte[3];
		byte[] notionalCurrencySrc = pTrade.notionalCurrency.getBytes();
		System.arraycopy(notionalCurrencySrc, 0, notionalCurrencyBytes, 0, Math.min(notionalCurrencySrc.length, 3));
		pData.put(notionalCurrencyBytes);
		BitUtils.uint64ToByteArray(pTrade.publicationDateTime, pData, false);
		byte[] firmBytes = new byte[6];
		byte[] firmSrc = pTrade.firm.getBytes();
		System.arraycopy(firmSrc, 0, firmBytes, 0, Math.min(firmSrc.length, 6));
		pData.put(firmBytes);
		byte[] contraFirmBytes = new byte[6];
		byte[] contraFirmSrc = pTrade.contraFirm.getBytes();
		System.arraycopy(contraFirmSrc, 0, contraFirmBytes, 0, Math.min(contraFirmSrc.length, 6));
		pData.put(contraFirmBytes);
		byte[] cancellationFlagBytes = new byte[4];
		byte[] cancellationFlagSrc = pTrade.cancellationFlag.getBytes();
		System.arraycopy(cancellationFlagSrc, 0, cancellationFlagBytes, 0, Math.min(cancellationFlagSrc.length, 4));
		pData.put(cancellationFlagBytes);
		byte[] amendmentFlagBytes = new byte[4];
		byte[] amendmentFlagSrc = pTrade.amendmentFlag.getBytes();
		System.arraycopy(amendmentFlagSrc, 0, amendmentFlagBytes, 0, Math.min(amendmentFlagSrc.length, 4));
		pData.put(amendmentFlagBytes);
		BitUtils.uint8ToByte(pTrade.subBook, pData);
		pData.put(pTrade.flags);
	}

	@Override
	public String toString() {
		return "MitchTrade [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", executedQuantity=" + executedQuantity + ", price=" + price + ", instrumentId=" + instrumentId + ", tradeId=" + tradeId + ", lastOptPx=" + lastOptPx + ", volatility=" + volatility + ", underlyingReferencePrice=" + underlyingReferencePrice + ", tradingDateTime=" + tradingDateTime + ", instrumentIdentificationCode=" + instrumentIdentificationCode + ", instrumentIdentificationCodeType=" + instrumentIdentificationCodeType + ", currency=" + currency + ", venueOfExecution=" + venueOfExecution + ", priceNotation=" + priceNotation + ", notionalAmount=" + notionalAmount + ", notionalCurrency=" + notionalCurrency + ", publicationDateTime=" + publicationDateTime + ", firm=" + firm + ", contraFirm=" + contraFirm + ", cancellationFlag=" + cancellationFlag + ", amendmentFlag=" + amendmentFlag + ", subBook=" + subBook + ", flags=" + flags + "]";
	}
}
