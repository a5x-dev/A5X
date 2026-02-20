package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent to indicate the start and end of the day
 */
public class MitchSystemEvent {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * C = End of Day, O = Start of Day
	 */
	public byte eventCode;

	/**
	 * Parses SystemEvent message from ByteBuffer.
	 * 
	 * @param pSystemEvent the MitchSystemEvent instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchSystemEvent pSystemEvent, ByteBuffer pData) {
		pSystemEvent.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pSystemEvent.eventCode = pData.get();
	}

	/**
	 * Encodes SystemEvent message to ByteBuffer.
	 * 
	 * @param pSystemEvent the MitchSystemEvent instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchSystemEvent pSystemEvent, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pSystemEvent.nanosecond, pData, false);
		pData.put(pSystemEvent.eventCode);
	}

	@Override
	public String toString() {
		return "MitchSystemEvent [" + "nanosecond=" + nanosecond + ", eventCode=" + eventCode + "]";
	}
}
