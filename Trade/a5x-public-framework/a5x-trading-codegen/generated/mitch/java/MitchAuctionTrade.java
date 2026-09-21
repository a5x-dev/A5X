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
	 * Parses AuctionTrade message from ByteBuffer.
	 * 
	 * @param pAuctionTrade the MitchAuctionTrade instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchAuctionTrade pAuctionTrade, ByteBuffer pData) {
		pAuctionTrade.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pAuctionTrade.executedQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pAuctionTrade.price = pData.getLong();
		pAuctionTrade.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pAuctionTrade.tradeId = pData.getLong();
		pAuctionTrade.lastOptPx = pData.getLong();
		pAuctionTrade.volatility = pData.getLong();
		pAuctionTrade.underlyingReferencePrice = pData.getLong();
		pAuctionTrade.tradingDateTime = pData.getLong();
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
		pAuctionTrade.notionalAmount = pData.getLong();
		byte[] notionalCurrencyBytes = new byte[3];
		pData.get(notionalCurrencyBytes);
		pAuctionTrade.notionalCurrency = new String(notionalCurrencyBytes).trim();
		pAuctionTrade.publicationDateTime = pData.getLong();
		byte[] cancellationFlagBytes = new byte[4];
		pData.get(cancellationFlagBytes);
		pAuctionTrade.cancellationFlag = new String(cancellationFlagBytes).trim();
		byte[] amendmentFlagBytes = new byte[4];
		pData.get(amendmentFlagBytes);
		pAuctionTrade.amendmentFlag = new String(amendmentFlagBytes).trim();
		pAuctionTrade.auctionType = pData.get();
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
		pData.putLong(pAuctionTrade.price);
		BitUtils.uint32ToByteArray(pAuctionTrade.instrumentId, pData, false);
		pData.putLong(pAuctionTrade.tradeId);
		pData.putLong(pAuctionTrade.lastOptPx);
		pData.putLong(pAuctionTrade.volatility);
		pData.putLong(pAuctionTrade.underlyingReferencePrice);
		pData.putLong(pAuctionTrade.tradingDateTime);
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
		pData.putLong(pAuctionTrade.notionalAmount);
		byte[] notionalCurrencyBytes = new byte[3];
		java.util.Arrays.fill(notionalCurrencyBytes, (byte) ' ');
		byte[] notionalCurrencySrc = pAuctionTrade.notionalCurrency == null ? new byte[0] : pAuctionTrade.notionalCurrency.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(notionalCurrencySrc, 0, notionalCurrencyBytes, 0, Math.min(notionalCurrencySrc.length, 3));
		pData.put(notionalCurrencyBytes);
		pData.putLong(pAuctionTrade.publicationDateTime);
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
	}

	@Override
	public String toString() {
		return "MitchAuctionTrade [" + "nanosecond=" + nanosecond + ", executedQuantity=" + executedQuantity + ", price=" + price + ", instrumentId=" + instrumentId + ", tradeId=" + tradeId + ", lastOptPx=" + lastOptPx + ", volatility=" + volatility + ", underlyingReferencePrice=" + underlyingReferencePrice + ", tradingDateTime=" + tradingDateTime + ", instrumentIdentificationCode=" + instrumentIdentificationCode + ", instrumentIdentificationCodeType=" + instrumentIdentificationCodeType + ", currency=" + currency + ", venueOfExecution=" + venueOfExecution + ", priceNotation=" + priceNotation + ", notionalAmount=" + notionalAmount + ", notionalCurrency=" + notionalCurrency + ", publicationDateTime=" + publicationDateTime + ", cancellationFlag=" + cancellationFlag + ", amendmentFlag=" + amendmentFlag + ", auctionType=" + auctionType + "]";
	}
}
