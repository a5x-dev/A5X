package br.com.a5x.trd.pub.mitch;

/**
 * Configuration constants for MITCH protocol processing.
 */
public class Standards {
	/**
	 * Path to the PCAP capture file.
	 */
	public static String cFile;
	public static String cEnv;
	public static String cMulticastAddress;
	public static String cMulticastPort;
	public static String cNetworkInterface;

	public static String getStringProperty(String pKey, String pDefaultValue) {
		switch (pKey) {
		case "ENV":
			if (cEnv != null) {
				return cEnv;
			}
		case "multicast.address":
			if (cMulticastAddress != null) {
				return cMulticastAddress;
			}
		case "multicast.port":
			if (cMulticastPort != null) {
				return cMulticastPort;
			}
		case "network.interface":
			if (cNetworkInterface != null) {
				return cNetworkInterface;
			}
		}

		return System.getProperty(pKey, pDefaultValue);
	}
}
