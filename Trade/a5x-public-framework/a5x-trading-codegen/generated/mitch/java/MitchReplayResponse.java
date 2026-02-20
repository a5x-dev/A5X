package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent by server in response to replay request
 */
public class MitchReplayResponse {
	/**
	 * Identity of the market data group the replay request relates to
	 */
	public byte marketDataGroup;

	/**
	 * Sequence number of the first message in range to be retransmitted. This will be zero if Status is not 'A'
	 */
	public BigInteger firstMessage;

	/**
	 * Number of messages to be resent. This will be zero if Status is not 'A'
	 */
	public int count;

	/**
	 * A = Request Accepted, D = Request Limit Reached, I = Invalid Market Data Group, O = Out of Range, U = Replay Unavailable, c = Concurrent Limit Reached, d = Unsupported Message Type, e = Failed (Other)
	 */
	public byte status;

	/**
	 * Parses ReplayResponse message from ByteBuffer.
	 * 
	 * @param pReplayResponse the MitchReplayResponse instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchReplayResponse pReplayResponse, ByteBuffer pData) {
		pReplayResponse.marketDataGroup = pData.get();
		pReplayResponse.firstMessage = BitUtils.byteArrayToUInt64(pData, false);
		pReplayResponse.count = BitUtils.byteArrayToUInt16(pData, false);
		pReplayResponse.status = pData.get();
	}

	/**
	 * Encodes ReplayResponse message to ByteBuffer.
	 * 
	 * @param pReplayResponse the MitchReplayResponse instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchReplayResponse pReplayResponse, ByteBuffer pData) {
		pData.put(pReplayResponse.marketDataGroup);
		BitUtils.uint64ToByteArray(pReplayResponse.firstMessage, pData, false);
		BitUtils.uint16ToByteArray(pReplayResponse.count, pData, false);
		pData.put(pReplayResponse.status);
	}

	@Override
	public String toString() {
		return "MitchReplayResponse [" + "marketDataGroup=" + marketDataGroup + ", firstMessage=" + firstMessage + ", count=" + count + ", status=" + status + "]";
	}
}
