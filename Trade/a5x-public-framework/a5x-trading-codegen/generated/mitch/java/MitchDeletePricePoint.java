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
	 * Reserved for future use
	 */
	public byte[] reservedField = new byte[1];

	/**
	 * Identifier of the RFQ the price level belongs to. Null filled when the level is not RFQ driven
	 */
	public String rfqId;

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
		pData.get(pDeletePricePoint.reservedField);
		byte[] rfqIdBytes = new byte[10];
		pData.get(rfqIdBytes);
		pDeletePricePoint.rfqId = new String(rfqIdBytes).trim();
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
		pData.put(pDeletePricePoint.reservedField);
		byte[] rfqIdBytes = new byte[10];
		java.util.Arrays.fill(rfqIdBytes, (byte) ' ');
		byte[] rfqIdSrc = pDeletePricePoint.rfqId == null ? new byte[0] : pDeletePricePoint.rfqId.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(rfqIdSrc, 0, rfqIdBytes, 0, Math.min(rfqIdSrc.length, 10));
		pData.put(rfqIdBytes);
	}

	@Override
	public String toString() {
		return "MitchDeletePricePoint [" + "nanosecond=" + nanosecond + ", instrumentId=" + instrumentId + ", subBook=" + subBook + ", side=" + side + ", priceLevel=" + priceLevel + ", reservedField=" + reservedField + ", rfqId=" + rfqId + "]";
	}
}
