package br.com.a5x.trd.pub.mitch.model;

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
	public long orderId;

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
	 * Parses Trade message from ByteBuffer.
	 * 
	 * @param pTrade the MitchTrade instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchTrade pTrade, ByteBuffer pData) {
		pTrade.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pTrade.orderId = pData.getLong();
		pTrade.executedQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pTrade.price = pData.getLong();
		pTrade.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pTrade.tradeId = pData.getLong();
		pTrade.lastOptPx = pData.getLong();
		pTrade.volatility = pData.getLong();
		pTrade.underlyingReferencePrice = pData.getLong();
		pTrade.tradingDateTime = pData.getLong();
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
		pTrade.publicationDateTime = pData.getLong();
		byte[] buyerFirmBytes = new byte[6];
		pData.get(buyerFirmBytes);
		pTrade.buyerFirm = new String(buyerFirmBytes).trim();
		byte[] sellerFirmBytes = new byte[6];
		pData.get(sellerFirmBytes);
		pTrade.sellerFirm = new String(sellerFirmBytes).trim();
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
		pData.putLong(pTrade.orderId);
		BitUtils.uint32ToByteArray(pTrade.executedQuantity, pData, false);
		pData.putLong(pTrade.price);
		BitUtils.uint32ToByteArray(pTrade.instrumentId, pData, false);
		pData.putLong(pTrade.tradeId);
		pData.putLong(pTrade.lastOptPx);
		pData.putLong(pTrade.volatility);
		pData.putLong(pTrade.underlyingReferencePrice);
		pData.putLong(pTrade.tradingDateTime);
		byte[] instrumentIdentificationCodeBytes = new byte[12];
		java.util.Arrays.fill(instrumentIdentificationCodeBytes, (byte) ' ');
		byte[] instrumentIdentificationCodeSrc = pTrade.instrumentIdentificationCode == null ? new byte[0] : pTrade.instrumentIdentificationCode.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(instrumentIdentificationCodeSrc, 0, instrumentIdentificationCodeBytes, 0, Math.min(instrumentIdentificationCodeSrc.length, 12));
		pData.put(instrumentIdentificationCodeBytes);
		BitUtils.uint8ToByte(pTrade.instrumentIdentificationCodeType, pData);
		byte[] currencyBytes = new byte[3];
		java.util.Arrays.fill(currencyBytes, (byte) ' ');
		byte[] currencySrc = pTrade.currency == null ? new byte[0] : pTrade.currency.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(currencySrc, 0, currencyBytes, 0, Math.min(currencySrc.length, 3));
		pData.put(currencyBytes);
		byte[] venueOfExecutionBytes = new byte[4];
		java.util.Arrays.fill(venueOfExecutionBytes, (byte) ' ');
		byte[] venueOfExecutionSrc = pTrade.venueOfExecution == null ? new byte[0] : pTrade.venueOfExecution.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(venueOfExecutionSrc, 0, venueOfExecutionBytes, 0, Math.min(venueOfExecutionSrc.length, 4));
		pData.put(venueOfExecutionBytes);
		BitUtils.uint8ToByte(pTrade.priceNotation, pData);
		pData.putLong(pTrade.notionalAmount);
		byte[] notionalCurrencyBytes = new byte[3];
		java.util.Arrays.fill(notionalCurrencyBytes, (byte) ' ');
		byte[] notionalCurrencySrc = pTrade.notionalCurrency == null ? new byte[0] : pTrade.notionalCurrency.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(notionalCurrencySrc, 0, notionalCurrencyBytes, 0, Math.min(notionalCurrencySrc.length, 3));
		pData.put(notionalCurrencyBytes);
		pData.putLong(pTrade.publicationDateTime);
		byte[] buyerFirmBytes = new byte[6];
		java.util.Arrays.fill(buyerFirmBytes, (byte) ' ');
		byte[] buyerFirmSrc = pTrade.buyerFirm == null ? new byte[0] : pTrade.buyerFirm.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(buyerFirmSrc, 0, buyerFirmBytes, 0, Math.min(buyerFirmSrc.length, 6));
		pData.put(buyerFirmBytes);
		byte[] sellerFirmBytes = new byte[6];
		java.util.Arrays.fill(sellerFirmBytes, (byte) ' ');
		byte[] sellerFirmSrc = pTrade.sellerFirm == null ? new byte[0] : pTrade.sellerFirm.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(sellerFirmSrc, 0, sellerFirmBytes, 0, Math.min(sellerFirmSrc.length, 6));
		pData.put(sellerFirmBytes);
		byte[] cancellationFlagBytes = new byte[4];
		java.util.Arrays.fill(cancellationFlagBytes, (byte) ' ');
		byte[] cancellationFlagSrc = pTrade.cancellationFlag == null ? new byte[0] : pTrade.cancellationFlag.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(cancellationFlagSrc, 0, cancellationFlagBytes, 0, Math.min(cancellationFlagSrc.length, 4));
		pData.put(cancellationFlagBytes);
		byte[] amendmentFlagBytes = new byte[4];
		java.util.Arrays.fill(amendmentFlagBytes, (byte) ' ');
		byte[] amendmentFlagSrc = pTrade.amendmentFlag == null ? new byte[0] : pTrade.amendmentFlag.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(amendmentFlagSrc, 0, amendmentFlagBytes, 0, Math.min(amendmentFlagSrc.length, 4));
		pData.put(amendmentFlagBytes);
		BitUtils.uint8ToByte(pTrade.subBook, pData);
		pData.put(pTrade.flags);
	}

	@Override
	public String toString() {
		return "MitchTrade [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", executedQuantity=" + executedQuantity + ", price=" + price + ", instrumentId=" + instrumentId + ", tradeId=" + tradeId + ", lastOptPx=" + lastOptPx + ", volatility=" + volatility + ", underlyingReferencePrice=" + underlyingReferencePrice + ", tradingDateTime=" + tradingDateTime + ", instrumentIdentificationCode=" + instrumentIdentificationCode + ", instrumentIdentificationCodeType=" + instrumentIdentificationCodeType + ", currency=" + currency + ", venueOfExecution=" + venueOfExecution + ", priceNotation=" + priceNotation + ", notionalAmount=" + notionalAmount + ", notionalCurrency=" + notionalCurrency + ", publicationDateTime=" + publicationDateTime + ", buyerFirm=" + buyerFirm + ", sellerFirm=" + sellerFirm + ", cancellationFlag=" + cancellationFlag + ", amendmentFlag=" + amendmentFlag + ", subBook=" + subBook + ", flags=" + flags + "]";
	}
}
