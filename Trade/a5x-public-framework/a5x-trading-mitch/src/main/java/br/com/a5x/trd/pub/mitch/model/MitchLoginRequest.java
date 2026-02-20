package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent by client to authenticate with the server
 */
public class MitchLoginRequest {
	/**
	 * CompID assigned to the client
	 */
	public String username;

	/**
	 * Password assigned to the CompID
	 */
	public String password;

	/**
	 * Original IP Address the user is running from (the server IP Address for audit purpose)
	 */
	public String ipAddress;

	/**
	 * Identifier of the solution that has been certificated against A5X trading platform (ex.: Trading System v1.0)
	 */
	public String certifiedSolution;

	/**
	 * Parses LoginRequest message from ByteBuffer.
	 * 
	 * @param pLoginRequest the MitchLoginRequest instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchLoginRequest pLoginRequest, ByteBuffer pData) {
		byte[] usernameBytes = new byte[6];
		pData.get(usernameBytes);
		pLoginRequest.username = new String(usernameBytes).trim();
		byte[] passwordBytes = new byte[10];
		pData.get(passwordBytes);
		pLoginRequest.password = new String(passwordBytes).trim();
		byte[] ipAddressBytes = new byte[15];
		pData.get(ipAddressBytes);
		pLoginRequest.ipAddress = new String(ipAddressBytes).trim();
		byte[] certifiedSolutionBytes = new byte[44];
		pData.get(certifiedSolutionBytes);
		pLoginRequest.certifiedSolution = new String(certifiedSolutionBytes).trim();
	}

	/**
	 * Encodes LoginRequest message to ByteBuffer.
	 * 
	 * @param pLoginRequest the MitchLoginRequest instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchLoginRequest pLoginRequest, ByteBuffer pData) {
		byte[] usernameBytes = new byte[6];
		byte[] usernameSrc = pLoginRequest.username.getBytes();
		System.arraycopy(usernameSrc, 0, usernameBytes, 0, Math.min(usernameSrc.length, 6));
		pData.put(usernameBytes);
		byte[] passwordBytes = new byte[10];
		byte[] passwordSrc = pLoginRequest.password.getBytes();
		System.arraycopy(passwordSrc, 0, passwordBytes, 0, Math.min(passwordSrc.length, 10));
		pData.put(passwordBytes);
		byte[] ipAddressBytes = new byte[15];
		byte[] ipAddressSrc = pLoginRequest.ipAddress.getBytes();
		System.arraycopy(ipAddressSrc, 0, ipAddressBytes, 0, Math.min(ipAddressSrc.length, 15));
		pData.put(ipAddressBytes);
		byte[] certifiedSolutionBytes = new byte[44];
		byte[] certifiedSolutionSrc = pLoginRequest.certifiedSolution.getBytes();
		System.arraycopy(certifiedSolutionSrc, 0, certifiedSolutionBytes, 0, Math.min(certifiedSolutionSrc.length, 44));
		pData.put(certifiedSolutionBytes);
	}

	@Override
	public String toString() {
		return "MitchLoginRequest [" + "username=" + username + ", password=" + password + ", ipAddress=" + ipAddress + ", certifiedSolution=" + certifiedSolution + "]";
	}
}
