package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
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

	public java.math.BigDecimal highPriceAsDecimal() { return java.math.BigDecimal.valueOf(highPrice, 4); }

	/**
	 * Low Price of the instrument. Will be set to a negative value if not set or withdrawn
	 */
	public long lowPrice;

	public java.math.BigDecimal lowPriceAsDecimal() { return java.math.BigDecimal.valueOf(lowPrice, 4); }

	/**
	 * VWAP of the instrument. Will be set to a negative value if not set or withdrawn
	 */
	public long vwap;

	public java.math.BigDecimal vwapAsDecimal() { return java.math.BigDecimal.valueOf(vwap, 4); }

	/**
	 * Volume of the instrument. Will be set to zero if not set or withdrawn
	 */
	public long volume;

	/**
	 * Turnover of the instrument. Will be set to a negative value if not set or withdrawn
	 */
	public long turnover;

	public java.math.BigDecimal turnoverAsDecimal() { return java.math.BigDecimal.valueOf(turnover, 4); }

	/**
	 * Number of trades for this instrument. Will be set to zero if not set or withdrawn
	 */
	public long numberOfTrades;

	/**
	 * Reserved for future use
	 */
	public byte[] reservedField = new byte[8];

	/**
	 * 1 = Regular, 2 = Off-Book, 11 = Negotiated Trades
	 */
	public int subBook;

	/**
	 * Notional exposure related to the options trade executions
	 */
	public long notionalExposure;

	public java.math.BigDecimal notionalExposureAsDecimal() { return java.math.BigDecimal.valueOf(notionalExposure, 4); }

	/**
	 * Notional exposure updated by the delta of the option based on trade executions
	 */
	public long notionalDeltaExposure;

	public java.math.BigDecimal notionalDeltaExposureAsDecimal() { return java.math.BigDecimal.valueOf(notionalDeltaExposure, 4); }

	/**
	 * Theoretical Price of the options trade
	 */
	public long theoreticalPrice;

	public java.math.BigDecimal theoreticalPriceAsDecimal() { return java.math.BigDecimal.valueOf(theoreticalPrice, 4); }

	/**
	 * Converted volatility of the executed price of the options instrument
	 */
	public long volatility;

	public java.math.BigDecimal volatilityAsDecimal() { return java.math.BigDecimal.valueOf(volatility, 4); }

	/**
	 * Upper Dynamic Price Band Limit of the instrument
	 */
	public long upperDynamicPBLimit;

	public java.math.BigDecimal upperDynamicPBLimitAsDecimal() { return java.math.BigDecimal.valueOf(upperDynamicPBLimit, 4); }

	/**
	 * Lower Dynamic Price Band Limit of the instrument
	 */
	public long lowerDynamicPBLimit;

	public java.math.BigDecimal lowerDynamicPBLimitAsDecimal() { return java.math.BigDecimal.valueOf(lowerDynamicPBLimit, 4); }

	/**
	 * Upper Static Price Band Limit of the instrument
	 */
	public long upperStaticPBLimit;

	public java.math.BigDecimal upperStaticPBLimitAsDecimal() { return java.math.BigDecimal.valueOf(upperStaticPBLimit, 4); }

	/**
	 * Lower Static Price Band Limit of the instrument
	 */
	public long lowerStaticPBLimit;

	public java.math.BigDecimal lowerStaticPBLimitAsDecimal() { return java.math.BigDecimal.valueOf(lowerStaticPBLimit, 4); }

	/**
	 * Upper Dynamic Circuit Breaker Limit of the instrument
	 */
	public long upperDynamicCBLimit;

	public java.math.BigDecimal upperDynamicCBLimitAsDecimal() { return java.math.BigDecimal.valueOf(upperDynamicCBLimit, 4); }

	/**
	 * Lower Dynamic Circuit Breaker Limit of the instrument
	 */
	public long lowerDynamicCBLimit;

	public java.math.BigDecimal lowerDynamicCBLimitAsDecimal() { return java.math.BigDecimal.valueOf(lowerDynamicCBLimit, 4); }

	/**
	 * Parses ExtendedStatistics message from ByteBuffer.
	 * 
	 * @param pExtendedStatistics the MitchExtendedStatistics instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchExtendedStatistics pExtendedStatistics, ByteBuffer pData) {
		pExtendedStatistics.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pExtendedStatistics.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pExtendedStatistics.highPrice = pData.getLong();
		pExtendedStatistics.lowPrice = pData.getLong();
		pExtendedStatistics.vwap = pData.getLong();
		pExtendedStatistics.volume = BitUtils.byteArrayToUInt32(pData, false);
		pExtendedStatistics.turnover = pData.getLong();
		pExtendedStatistics.numberOfTrades = BitUtils.byteArrayToUInt32(pData, false);
		pData.get(pExtendedStatistics.reservedField);
		pExtendedStatistics.subBook = BitUtils.byteToUInt8(pData);
		pExtendedStatistics.notionalExposure = pData.getLong();
		pExtendedStatistics.notionalDeltaExposure = pData.getLong();
		pExtendedStatistics.theoreticalPrice = pData.getLong();
		pExtendedStatistics.volatility = pData.getLong();
		pExtendedStatistics.upperDynamicPBLimit = pData.getLong();
		pExtendedStatistics.lowerDynamicPBLimit = pData.getLong();
		pExtendedStatistics.upperStaticPBLimit = pData.getLong();
		pExtendedStatistics.lowerStaticPBLimit = pData.getLong();
		pExtendedStatistics.upperDynamicCBLimit = pData.getLong();
		pExtendedStatistics.lowerDynamicCBLimit = pData.getLong();
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
		pData.putLong(pExtendedStatistics.highPrice);
		pData.putLong(pExtendedStatistics.lowPrice);
		pData.putLong(pExtendedStatistics.vwap);
		BitUtils.uint32ToByteArray(pExtendedStatistics.volume, pData, false);
		pData.putLong(pExtendedStatistics.turnover);
		BitUtils.uint32ToByteArray(pExtendedStatistics.numberOfTrades, pData, false);
		pData.put(pExtendedStatistics.reservedField);
		BitUtils.uint8ToByte(pExtendedStatistics.subBook, pData);
		pData.putLong(pExtendedStatistics.notionalExposure);
		pData.putLong(pExtendedStatistics.notionalDeltaExposure);
		pData.putLong(pExtendedStatistics.theoreticalPrice);
		pData.putLong(pExtendedStatistics.volatility);
		pData.putLong(pExtendedStatistics.upperDynamicPBLimit);
		pData.putLong(pExtendedStatistics.lowerDynamicPBLimit);
		pData.putLong(pExtendedStatistics.upperStaticPBLimit);
		pData.putLong(pExtendedStatistics.lowerStaticPBLimit);
		pData.putLong(pExtendedStatistics.upperDynamicCBLimit);
		pData.putLong(pExtendedStatistics.lowerDynamicCBLimit);
	}

	@Override
	public String toString() {
		return "MitchExtendedStatistics [" + "nanosecond=" + nanosecond + ", instrumentId=" + instrumentId + ", highPrice=" + highPrice + ", lowPrice=" + lowPrice + ", vwap=" + vwap + ", volume=" + volume + ", turnover=" + turnover + ", numberOfTrades=" + numberOfTrades + ", reservedField=" + reservedField + ", subBook=" + subBook + ", notionalExposure=" + notionalExposure + ", notionalDeltaExposure=" + notionalDeltaExposure + ", theoreticalPrice=" + theoreticalPrice + ", volatility=" + volatility + ", upperDynamicPBLimit=" + upperDynamicPBLimit + ", lowerDynamicPBLimit=" + lowerDynamicPBLimit + ", upperStaticPBLimit=" + upperStaticPBLimit + ", lowerStaticPBLimit=" + lowerStaticPBLimit + ", upperDynamicCBLimit=" + upperDynamicCBLimit + ", lowerDynamicCBLimit=" + lowerDynamicCBLimit + "]";
	}
}
