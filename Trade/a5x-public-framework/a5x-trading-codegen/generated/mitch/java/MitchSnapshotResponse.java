package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent by server in response to snapshot request
 */
public class MitchSnapshotResponse {
	/**
	 * Sequence number with which the snapshot is synchronized. This will be zero if Status is not 'A'. Ignore if Snapshot Type is not Order Book (0)
	 */
	public BigInteger sequenceNumber;

	/**
	 * Number of orders that will be transmitted in the snapshot. This will be zero if Order Book is empty or Status is not 'A'. However, if the Snapshot Request was submitted for multiple order books, this field will be set to zero. Ignore if Snapshot Type is not Order Book (0)
	 */
	public long orderCount;

	/**
	 * A = Request Accepted, O = Out of Range, U = Snapshot Unavailable, a = Segment/Symbol/Sub Book Invalid or Not Specified, b = Request Limit Reached, c = Concurrent Limit Reached, d = Unsupported Message Type, e = Failed (Other)
	 */
	public byte status;

	/**
	 * 0=Order Book, 1=Symbol Status, 2=Instrument, 3=Trades, 4=Statistics, 5=News, 8=Top of Book
	 */
	public int snapshotType;

	/**
	 * Identifier, if any, of Snapshot Request
	 */
	public long requestId;

	/**
	 * Parses SnapshotResponse message from ByteBuffer.
	 * 
	 * @param pSnapshotResponse the MitchSnapshotResponse instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchSnapshotResponse pSnapshotResponse, ByteBuffer pData) {
		pSnapshotResponse.sequenceNumber = BitUtils.byteArrayToUInt64(pData, false);
		pSnapshotResponse.orderCount = BitUtils.byteArrayToUInt32(pData, false);
		pSnapshotResponse.status = pData.get();
		pSnapshotResponse.snapshotType = BitUtils.byteToUInt8(pData);
		pSnapshotResponse.requestId = BitUtils.byteArrayToUInt32(pData, false);
	}

	/**
	 * Encodes SnapshotResponse message to ByteBuffer.
	 * 
	 * @param pSnapshotResponse the MitchSnapshotResponse instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchSnapshotResponse pSnapshotResponse, ByteBuffer pData) {
		BitUtils.uint64ToByteArray(pSnapshotResponse.sequenceNumber, pData, false);
		BitUtils.uint32ToByteArray(pSnapshotResponse.orderCount, pData, false);
		pData.put(pSnapshotResponse.status);
		BitUtils.uint8ToByte(pSnapshotResponse.snapshotType, pData);
		BitUtils.uint32ToByteArray(pSnapshotResponse.requestId, pData, false);
	}

	@Override
	public String toString() {
		return "MitchSnapshotResponse [" + "sequenceNumber=" + sequenceNumber + ", orderCount=" + orderCount + ", status=" + status + ", snapshotType=" + snapshotType + ", requestId=" + requestId + "]";
	}
}
