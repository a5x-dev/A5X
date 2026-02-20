package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent by client to logout
 */
public class MitchLogoutRequest {
	/**
	 * Parses LogoutRequest message from ByteBuffer.
	 * 
	 * @param pLogoutRequest the MitchLogoutRequest instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchLogoutRequest pLogoutRequest, ByteBuffer pData) {
	}

	/**
	 * Encodes LogoutRequest message to ByteBuffer.
	 * 
	 * @param pLogoutRequest the MitchLogoutRequest instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchLogoutRequest pLogoutRequest, ByteBuffer pData) {
	}

	@Override
	public String toString() {
		return "MitchLogoutRequest[]";
	}
}
