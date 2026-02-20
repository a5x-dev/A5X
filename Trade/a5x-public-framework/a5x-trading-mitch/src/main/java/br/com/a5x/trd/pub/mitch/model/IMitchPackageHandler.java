package br.com.a5x.trd.pub.mitch.model;

import java.net.Inet4Address;
import java.nio.ByteBuffer;

/**
 * Handler interface for processing MITCH protocol packages.
 * Implementations define how to handle incoming MITCH messages from multicast streams.
 */
public interface IMitchPackageHandler {
	/**
	 * Handles a MITCH protocol package.
	 * 
	 * @param pData the ByteBuffer containing the package data
	 * @param pMcastAddress the multicast address from which the package was received
	 */
	public void handle(ByteBuffer pData, Inet4Address pMcastAddress);
}
