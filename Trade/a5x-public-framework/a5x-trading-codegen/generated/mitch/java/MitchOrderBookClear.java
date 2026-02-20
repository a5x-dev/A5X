package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent to indicate that the order book for an instrument should be cleared
 */
public class MitchOrderBookClear {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Numeric identifier of the instrument
	 */
	public long instrumentId;

	/**
	 * 1 = Regular, 2 = Off-Book, 9 = Bulletin Board, 11 = Negotiated Trades
	 */
	public int subBook;

	/**
	 * 0 = MBP/MBO/Statistics, 1 = Top of Book
	 */
	public byte bookType;

	/**
	 * Parses OrderBookClear message from ByteBuffer.
	 * 
	 * @param pOrderBookClear the MitchOrderBookClear instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchOrderBookClear pOrderBookClear, ByteBuffer pData) {
		pOrderBookClear.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pOrderBookClear.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pOrderBookClear.subBook = BitUtils.byteToUInt8(pData);
		pOrderBookClear.bookType = pData.get();
	}

	/**
	 * Encodes OrderBookClear message to ByteBuffer.
	 * 
	 * @param pOrderBookClear the MitchOrderBookClear instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchOrderBookClear pOrderBookClear, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pOrderBookClear.nanosecond, pData, false);
		BitUtils.uint32ToByteArray(pOrderBookClear.instrumentId, pData, false);
		BitUtils.uint8ToByte(pOrderBookClear.subBook, pData);
		pData.put(pOrderBookClear.bookType);
	}

	@Override
	public String toString() {
		return "MitchOrderBookClear [" + "nanosecond=" + nanosecond + ", instrumentId=" + instrumentId + ", subBook=" + subBook + ", bookType=" + bookType + "]";
	}
}
