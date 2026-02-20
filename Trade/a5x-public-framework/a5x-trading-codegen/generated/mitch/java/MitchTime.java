package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent by the server for every second for which at least one application message is generated. This message is not transmitted during periods where no other application messages are generated
 */
public class MitchTime {
	/**
	 * Number of seconds since midnight. Midnight will be in terms of the local time for the server (i.e. not UTC)
	 */
	public long seconds;

	/**
	 * Parses Time message from ByteBuffer.
	 * 
	 * @param pTime the MitchTime instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchTime pTime, ByteBuffer pData) {
		pTime.seconds = BitUtils.byteArrayToUInt32(pData, false);
	}

	/**
	 * Encodes Time message to ByteBuffer.
	 * 
	 * @param pTime the MitchTime instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchTime pTime, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pTime.seconds, pData, false);
	}

	@Override
	public String toString() {
		return "MitchTime [" + "seconds=" + seconds + "]";
	}
}
