package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent to provide statistical information about instruments
 */
public class MitchStatistics {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Numeric identifier of the instrument
	 */
	public long instrumentId;

	/**
	 * C = Closing Price, O = Opening Price, P = Previous Close
	 */
	public byte statisticType;

	/**
	 * Opening or closing price
	 */
	public long price;

	public java.math.BigDecimal priceAsDecimal() { return java.math.BigDecimal.valueOf(price, 4); }

	/**
	 * A = Auction Trade, B = Regular Trade, C = Mid Point, D = Last Regular Trade, E = Last Auction, F = Manual, H = VWAP, I = Previous Close, T = Theoretical Price, L = VWAP n Volume, U = Best Bid, V = Best Offer, W = None, X = VWAP of Last n Trades, Y = Reference Price, Z = Price Unavailable
	 */
	public byte openCloseIndicator;

	/**
	 * 1 = Regular, 2 = Off-Book, 9 = Bulletin Board
	 */
	public int subBook;

	/**
	 * Parses Statistics message from ByteBuffer.
	 * 
	 * @param pStatistics the MitchStatistics instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchStatistics pStatistics, ByteBuffer pData) {
		pStatistics.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pStatistics.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pStatistics.statisticType = pData.get();
		pStatistics.price = pData.getLong();
		pStatistics.openCloseIndicator = pData.get();
		pStatistics.subBook = BitUtils.byteToUInt8(pData);
	}

	/**
	 * Encodes Statistics message to ByteBuffer.
	 * 
	 * @param pStatistics the MitchStatistics instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchStatistics pStatistics, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pStatistics.nanosecond, pData, false);
		BitUtils.uint32ToByteArray(pStatistics.instrumentId, pData, false);
		pData.put(pStatistics.statisticType);
		pData.putLong(pStatistics.price);
		pData.put(pStatistics.openCloseIndicator);
		BitUtils.uint8ToByte(pStatistics.subBook, pData);
	}

	@Override
	public String toString() {
		return "MitchStatistics [" + "nanosecond=" + nanosecond + ", instrumentId=" + instrumentId + ", statisticType=" + statisticType + ", price=" + price + ", openCloseIndicator=" + openCloseIndicator + ", subBook=" + subBook + "]";
	}
}
