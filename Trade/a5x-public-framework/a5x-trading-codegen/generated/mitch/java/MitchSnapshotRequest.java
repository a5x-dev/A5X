package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent by client to request snapshot data
 */
public class MitchSnapshotRequest {
	/**
	 * Sequence number from which client can build the order book
	 */
	public BigInteger sequenceNumber;

	/**
	 * Segment the request relates to. The field should contain only spaces if it does not relate to a segment
	 */
	public String segment;

	/**
	 * Instrument ID of the instrument that the request relates to. The field should contain only spaces if it does not relate to an instrument. This field is ignored if Segment is specified
	 */
	public long instrumentId;

	/**
	 * Bit 0: Regular (0=No, 1=Yes), Bit 1: Off-Book (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes), Bit 6: Negotiated Trades (0=No, 1=Yes)
	 */
	public byte subBook;

	/**
	 * 0=Order Book, 1=Symbol Status, 2=Instrument, 3=Trades, 4=Statistics, 5=News, 8=Top of Book
	 */
	public int snapshotType;

	/**
	 * Sending time of the last processed trade (EPOCH time in seconds). This field is ignored if the Snapshot Type is not Trades (3) or News (5)
	 */
	public BigInteger recoverFromTime;

	/**
	 * Optional identifier of the request
	 */
	public long requestId;

	/**
	 * Parses SnapshotRequest message from ByteBuffer.
	 * 
	 * @param pSnapshotRequest the MitchSnapshotRequest instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchSnapshotRequest pSnapshotRequest, ByteBuffer pData) {
		pSnapshotRequest.sequenceNumber = BitUtils.byteArrayToUInt64(pData, false);
		byte[] segmentBytes = new byte[10];
		pData.get(segmentBytes);
		pSnapshotRequest.segment = new String(segmentBytes).trim();
		pSnapshotRequest.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pSnapshotRequest.subBook = pData.get();
		pSnapshotRequest.snapshotType = BitUtils.byteToUInt8(pData);
		pSnapshotRequest.recoverFromTime = BitUtils.byteArrayToUInt64(pData, false);
		pSnapshotRequest.requestId = BitUtils.byteArrayToUInt32(pData, false);
	}

	/**
	 * Encodes SnapshotRequest message to ByteBuffer.
	 * 
	 * @param pSnapshotRequest the MitchSnapshotRequest instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchSnapshotRequest pSnapshotRequest, ByteBuffer pData) {
		BitUtils.uint64ToByteArray(pSnapshotRequest.sequenceNumber, pData, false);
		byte[] segmentBytes = new byte[10];
		byte[] segmentSrc = pSnapshotRequest.segment.getBytes();
		System.arraycopy(segmentSrc, 0, segmentBytes, 0, Math.min(segmentSrc.length, 10));
		pData.put(segmentBytes);
		BitUtils.uint32ToByteArray(pSnapshotRequest.instrumentId, pData, false);
		pData.put(pSnapshotRequest.subBook);
		BitUtils.uint8ToByte(pSnapshotRequest.snapshotType, pData);
		BitUtils.uint64ToByteArray(pSnapshotRequest.recoverFromTime, pData, false);
		BitUtils.uint32ToByteArray(pSnapshotRequest.requestId, pData, false);
	}

	@Override
	public String toString() {
		return "MitchSnapshotRequest [" + "sequenceNumber=" + sequenceNumber + ", segment=" + segment + ", instrumentId=" + instrumentId + ", subBook=" + subBook + ", snapshotType=" + snapshotType + ", recoverFromTime=" + recoverFromTime + ", requestId=" + requestId + "]";
	}
}
