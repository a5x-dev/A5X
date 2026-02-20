package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent by server in response to login request
 */
public class MitchLoginResponse {
	/**
	 * A = Login Accepted, a = CompID Inactive/Locked, b = Login Limit Reached, c = Service Unavailable, d = Concurrent Limit Reached, e = Failed (Other)
	 */
	public byte status;

	/**
	 * Parses LoginResponse message from ByteBuffer.
	 * 
	 * @param pLoginResponse the MitchLoginResponse instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchLoginResponse pLoginResponse, ByteBuffer pData) {
		pLoginResponse.status = pData.get();
	}

	/**
	 * Encodes LoginResponse message to ByteBuffer.
	 * 
	 * @param pLoginResponse the MitchLoginResponse instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchLoginResponse pLoginResponse, ByteBuffer pData) {
		pData.put(pLoginResponse.status);
	}

	@Override
	public String toString() {
		return "MitchLoginResponse [" + "status=" + status + "]";
	}
}
