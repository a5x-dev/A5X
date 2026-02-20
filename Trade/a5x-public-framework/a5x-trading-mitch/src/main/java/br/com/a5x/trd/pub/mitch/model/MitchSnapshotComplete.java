package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent by server to indicate snapshot transmission is complete
 */
public class MitchSnapshotComplete {
	/**
	 * Sequence number with which the snapshot is synchronized
	 */
	public BigInteger sequenceNumber;

	/**
	 * Segment the snapshot relates to. The field will contain only spaces if it does not relate to a segment
	 */
	public String segment;

	/**
	 * Instrument ID of the instrument that the snapshot relates to. The field will contain only spaces if it does not relate to an instrument
	 */
	public long instrumentId;

	/**
	 * Bit 0: Regular (0=No, 1=Yes), Bit 1: Off-Book (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes), Bit 6: Negotiated Trades (0=No, 1=Yes)
	 */
	public byte subBook;

	/**
	 * H=Halt, T=Regular Trading, a=Opening Auction Call, b=Post-Close, c=Market Closed, d=Closing Auction Call, e=Re-Opening Auction Call, I=Pause, n=Order Entry, u=Closing Price Cross, w=No Active Session, y=Pre-Trading, m=No Cancel Period. Only indicated if message is sent as book level complete and Snapshot Type is Order Book (0) or Statistics (4)
	 */
	public byte tradingStatus;

	/**
	 * 0=Order Book, 1=Symbol Status, 2=Instrument, 3=Trades, 4=Statistics, 5=News, 8=Top of Book
	 */
	public int snapshotType;

	/**
	 * Identifier, if any, of Snapshot Request
	 */
	public long requestId;

	/**
	 * Parses SnapshotComplete message from ByteBuffer.
	 * 
	 * @param pSnapshotComplete the MitchSnapshotComplete instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchSnapshotComplete pSnapshotComplete, ByteBuffer pData) {
		pSnapshotComplete.sequenceNumber = BitUtils.byteArrayToUInt64(pData, false);
		byte[] segmentBytes = new byte[10];
		pData.get(segmentBytes);
		pSnapshotComplete.segment = new String(segmentBytes).trim();
		pSnapshotComplete.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pSnapshotComplete.subBook = pData.get();
		pSnapshotComplete.tradingStatus = pData.get();
		pSnapshotComplete.snapshotType = BitUtils.byteToUInt8(pData);
		pSnapshotComplete.requestId = BitUtils.byteArrayToUInt32(pData, false);
	}

	/**
	 * Encodes SnapshotComplete message to ByteBuffer.
	 * 
	 * @param pSnapshotComplete the MitchSnapshotComplete instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchSnapshotComplete pSnapshotComplete, ByteBuffer pData) {
		BitUtils.uint64ToByteArray(pSnapshotComplete.sequenceNumber, pData, false);
		byte[] segmentBytes = new byte[10];
		byte[] segmentSrc = pSnapshotComplete.segment.getBytes();
		System.arraycopy(segmentSrc, 0, segmentBytes, 0, Math.min(segmentSrc.length, 10));
		pData.put(segmentBytes);
		BitUtils.uint32ToByteArray(pSnapshotComplete.instrumentId, pData, false);
		pData.put(pSnapshotComplete.subBook);
		pData.put(pSnapshotComplete.tradingStatus);
		BitUtils.uint8ToByte(pSnapshotComplete.snapshotType, pData);
		BitUtils.uint32ToByteArray(pSnapshotComplete.requestId, pData, false);
	}

	@Override
	public String toString() {
		return "MitchSnapshotComplete [" + "sequenceNumber=" + sequenceNumber + ", segment=" + segment + ", instrumentId=" + instrumentId + ", subBook=" + subBook + ", tradingStatus=" + tradingStatus + ", snapshotType=" + snapshotType + ", requestId=" + requestId + "]";
	}
}
