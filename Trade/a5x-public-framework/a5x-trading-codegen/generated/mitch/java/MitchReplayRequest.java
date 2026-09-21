package br.com.a5x.trd.pub.mitch.model;

import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent by client to request retransmission of messages
 */
public class MitchReplayRequest {
	/**
	 * Identity of the market data group the replay request relates to
	 */
	public byte marketDataGroup;

	/**
	 * Sequence number of the first message in range to be retransmitted
	 */
	public long firstMessage;

	/**
	 * Number of messages to be resent
	 */
	public int count;

	/**
	 * Parses ReplayRequest message from ByteBuffer.
	 * 
	 * @param pReplayRequest the MitchReplayRequest instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchReplayRequest pReplayRequest, ByteBuffer pData) {
		pReplayRequest.marketDataGroup = pData.get();
		pReplayRequest.firstMessage = pData.getLong();
		pReplayRequest.count = BitUtils.byteArrayToUInt16(pData, false);
	}

	/**
	 * Encodes ReplayRequest message to ByteBuffer.
	 * 
	 * @param pReplayRequest the MitchReplayRequest instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchReplayRequest pReplayRequest, ByteBuffer pData) {
		pData.put(pReplayRequest.marketDataGroup);
		pData.putLong(pReplayRequest.firstMessage);
		BitUtils.uint16ToByteArray(pReplayRequest.count, pData, false);
	}

	@Override
	public String toString() {
		return "MitchReplayRequest [" + "marketDataGroup=" + marketDataGroup + ", firstMessage=" + firstMessage + ", count=" + count + "]";
	}
}
