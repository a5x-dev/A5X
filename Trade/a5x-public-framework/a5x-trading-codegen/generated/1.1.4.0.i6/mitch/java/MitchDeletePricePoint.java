package br.com.a5x.trd.pub.mitch.model;

import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent when a price level is removed from an aggregated (price point) order book
 */
public class MitchDeletePricePoint {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Numeric Identifier of the instrument
	 */
	public long instrumentId;

	/**
	 * Bit 0: Regular (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes)
	 */
	public byte subBook;

	/**
	 * B = Buy, S = Sell
	 */
	public byte side;

	/**
	 * Position of the price level that is removed, counting from the best price
	 */
	public int priceLevel;

	/**
	 * Reserved for future use.
	 */
	public byte[] reserved1 = new byte[1];

	/**
	 * Reserved for future use.
	 */
	public String reserved2;

	/**
	 * Parses DeletePricePoint message from ByteBuffer.
	 * 
	 * @param pDeletePricePoint the MitchDeletePricePoint instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchDeletePricePoint pDeletePricePoint, ByteBuffer pData) {
		pDeletePricePoint.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pDeletePricePoint.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pDeletePricePoint.subBook = pData.get();
		pDeletePricePoint.side = pData.get();
		pDeletePricePoint.priceLevel = BitUtils.byteArrayToUInt16(pData, false);
		pData.get(pDeletePricePoint.reserved1);
		byte[] reserved2Bytes = new byte[10];
		pData.get(reserved2Bytes);
		pDeletePricePoint.reserved2 = new String(reserved2Bytes).trim();
	}

	/**
	 * Encodes DeletePricePoint message to ByteBuffer.
	 * 
	 * @param pDeletePricePoint the MitchDeletePricePoint instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchDeletePricePoint pDeletePricePoint, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pDeletePricePoint.nanosecond, pData, false);
		BitUtils.uint32ToByteArray(pDeletePricePoint.instrumentId, pData, false);
		pData.put(pDeletePricePoint.subBook);
		pData.put(pDeletePricePoint.side);
		BitUtils.uint16ToByteArray(pDeletePricePoint.priceLevel, pData, false);
		pData.put(pDeletePricePoint.reserved1);
		byte[] reserved2Bytes = new byte[10];
		java.util.Arrays.fill(reserved2Bytes, (byte) ' ');
		byte[] reserved2Src = pDeletePricePoint.reserved2 == null ? new byte[0] : pDeletePricePoint.reserved2.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(reserved2Src, 0, reserved2Bytes, 0, Math.min(reserved2Src.length, 10));
		pData.put(reserved2Bytes);
	}

	@Override
	public String toString() {
		return "MitchDeletePricePoint [" + "nanosecond=" + nanosecond + ", instrumentId=" + instrumentId + ", subBook=" + subBook + ", side=" + (char) side + ", priceLevel=" + priceLevel + ", reserved1=" + (reserved1 == null ? "" : java.util.HexFormat.of().formatHex(reserved1)) + ", reserved2=" + reserved2 + "]";
	}
}
