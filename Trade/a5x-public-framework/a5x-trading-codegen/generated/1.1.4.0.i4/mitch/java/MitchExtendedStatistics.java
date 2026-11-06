package br.com.a5x.trd.pub.mitch.model;

import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent to provide extended statistical information about instruments
 */
public class MitchExtendedStatistics {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Numeric identifier of the instrument
	 */
	public long instrumentId;

	/**
	 * High Price of the instrument. Will be set to a negative value if not set or withdrawn
	 */
	public long highPrice;

	public java.math.BigDecimal highPriceAsDecimal() { return java.math.BigDecimal.valueOf(highPrice, 8); }

	/**
	 * Low Price of the instrument. Will be set to a negative value if not set or withdrawn
	 */
	public long lowPrice;

	public java.math.BigDecimal lowPriceAsDecimal() { return java.math.BigDecimal.valueOf(lowPrice, 8); }

	/**
	 * VWAP of the instrument. Will be set to a negative value if not set or withdrawn
	 */
	public long vwap;

	public java.math.BigDecimal vwapAsDecimal() { return java.math.BigDecimal.valueOf(vwap, 8); }

	/**
	 * Volume of the instrument. Will be set to zero if not set or withdrawn
	 */
	public long volume;

	/**
	 * Turnover of the instrument. Will be set to a negative value if not set or withdrawn
	 */
	public long turnover;

	public java.math.BigDecimal turnoverAsDecimal() { return java.math.BigDecimal.valueOf(turnover, 8); }

	/**
	 * Number of trades for this instrument. Will be set to zero if not set or withdrawn
	 */
	public long numberOfTrades;

	/**
	 * Reserved for future use.
	 */
	public byte[] reserved1 = new byte[8];

	/**
	 * 1 = Regular, 2 = Off-Book, 11 = Negotiated Trades
	 */
	public int subBook;

	/**
	 * Notional exposure related to the options trade executions
	 */
	public long notionalExposure;

	public java.math.BigDecimal notionalExposureAsDecimal() { return java.math.BigDecimal.valueOf(notionalExposure, 8); }

	/**
	 * Notional exposure updated by the delta of the option based on trade executions
	 */
	public long notionalDeltaExposure;

	public java.math.BigDecimal notionalDeltaExposureAsDecimal() { return java.math.BigDecimal.valueOf(notionalDeltaExposure, 8); }

	/**
	 * Theoretical Price of the options trade
	 */
	public long theoreticalPrice;

	public java.math.BigDecimal theoreticalPriceAsDecimal() { return java.math.BigDecimal.valueOf(theoreticalPrice, 8); }

	/**
	 * Converted volatility of the executed price of the options instrument
	 */
	public long volatility;

	public java.math.BigDecimal volatilityAsDecimal() { return java.math.BigDecimal.valueOf(volatility, 8); }

	/**
	 * Upper Dynamic Price Band Limit of the instrument
	 */
	public long upperDynamicPBLimit;

	public java.math.BigDecimal upperDynamicPBLimitAsDecimal() { return java.math.BigDecimal.valueOf(upperDynamicPBLimit, 8); }

	/**
	 * Lower Dynamic Price Band Limit of the instrument
	 */
	public long lowerDynamicPBLimit;

	public java.math.BigDecimal lowerDynamicPBLimitAsDecimal() { return java.math.BigDecimal.valueOf(lowerDynamicPBLimit, 8); }

	/**
	 * Upper Static Price Band Limit of the instrument
	 */
	public long upperStaticPBLimit;

	public java.math.BigDecimal upperStaticPBLimitAsDecimal() { return java.math.BigDecimal.valueOf(upperStaticPBLimit, 8); }

	/**
	 * Lower Static Price Band Limit of the instrument
	 */
	public long lowerStaticPBLimit;

	public java.math.BigDecimal lowerStaticPBLimitAsDecimal() { return java.math.BigDecimal.valueOf(lowerStaticPBLimit, 8); }

	/**
	 * Upper Dynamic Circuit Breaker Limit of the instrument
	 */
	public long upperDynamicCBLimit;

	public java.math.BigDecimal upperDynamicCBLimitAsDecimal() { return java.math.BigDecimal.valueOf(upperDynamicCBLimit, 8); }

	/**
	 * Lower Dynamic Circuit Breaker Limit of the instrument
	 */
	public long lowerDynamicCBLimit;

	public java.math.BigDecimal lowerDynamicCBLimitAsDecimal() { return java.math.BigDecimal.valueOf(lowerDynamicCBLimit, 8); }

	/**
	 * Parses ExtendedStatistics message from ByteBuffer.
	 * 
	 * @param pExtendedStatistics the MitchExtendedStatistics instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchExtendedStatistics pExtendedStatistics, ByteBuffer pData) {
		pExtendedStatistics.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pExtendedStatistics.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pExtendedStatistics.highPrice = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.lowPrice = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.vwap = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.volume = BitUtils.byteArrayToUInt32(pData, false);
		pExtendedStatistics.turnover = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.numberOfTrades = BitUtils.byteArrayToUInt32(pData, false);
		pData.get(pExtendedStatistics.reserved1);
		pExtendedStatistics.subBook = BitUtils.byteToUInt8(pData);
		pExtendedStatistics.notionalExposure = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.notionalDeltaExposure = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.theoreticalPrice = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.volatility = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.upperDynamicPBLimit = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.lowerDynamicPBLimit = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.upperStaticPBLimit = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.lowerStaticPBLimit = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.upperDynamicCBLimit = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pExtendedStatistics.lowerDynamicCBLimit = BitUtils.byteArrayToSignMagnitude64(pData, false);
	}

	/**
	 * Encodes ExtendedStatistics message to ByteBuffer.
	 * 
	 * @param pExtendedStatistics the MitchExtendedStatistics instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchExtendedStatistics pExtendedStatistics, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pExtendedStatistics.nanosecond, pData, false);
		BitUtils.uint32ToByteArray(pExtendedStatistics.instrumentId, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.highPrice, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.lowPrice, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.vwap, pData, false);
		BitUtils.uint32ToByteArray(pExtendedStatistics.volume, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.turnover, pData, false);
		BitUtils.uint32ToByteArray(pExtendedStatistics.numberOfTrades, pData, false);
		pData.put(pExtendedStatistics.reserved1);
		BitUtils.uint8ToByte(pExtendedStatistics.subBook, pData);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.notionalExposure, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.notionalDeltaExposure, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.theoreticalPrice, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.volatility, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.upperDynamicPBLimit, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.lowerDynamicPBLimit, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.upperStaticPBLimit, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.lowerStaticPBLimit, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.upperDynamicCBLimit, pData, false);
		BitUtils.signMagnitude64ToByteArray(pExtendedStatistics.lowerDynamicCBLimit, pData, false);
	}

	@Override
	public String toString() {
		return "MitchExtendedStatistics [" + "nanosecond=" + nanosecond + ", instrumentId=" + instrumentId + ", highPrice=" + highPrice + ", lowPrice=" + lowPrice + ", vwap=" + vwap + ", volume=" + volume + ", turnover=" + turnover + ", numberOfTrades=" + numberOfTrades + ", reserved1=" + (reserved1 == null ? "" : java.util.HexFormat.of().formatHex(reserved1)) + ", subBook=" + subBook + ", notionalExposure=" + notionalExposure + ", notionalDeltaExposure=" + notionalDeltaExposure + ", theoreticalPrice=" + theoreticalPrice + ", volatility=" + volatility + ", upperDynamicPBLimit=" + upperDynamicPBLimit + ", lowerDynamicPBLimit=" + lowerDynamicPBLimit + ", upperStaticPBLimit=" + upperStaticPBLimit + ", lowerStaticPBLimit=" + lowerStaticPBLimit + ", upperDynamicCBLimit=" + upperDynamicCBLimit + ", lowerDynamicCBLimit=" + lowerDynamicCBLimit + "]";
	}
}
