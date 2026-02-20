package br.com.a5x.trd.pub.mitch;

import java.util.HashMap;
import java.util.Map;

public class MitchApp {
	private final static Map<String, Runnable> cModes;
	
	static {
		cModes = new HashMap<>();
		cModes.put("pcap", new MitchPcapApp());
		cModes.put("join", new MitchJoinApp());
	}
	
	public static void main(String[] pArgs) {
		String tAppMode = null;
		
		for (int i = 0; i < pArgs.length; i++) {
			String tArg = pArgs[i];
			
			switch (tArg) {
			case "-m":
			case "--mode":
				tAppMode = pArgs[++i];
				break;
			case "-f":
			case "--file":
				Standards.cFile = pArgs[++i];
				break;
			case "--env":
				Standards.cEnv = pArgs[++i];
				break;
			case "--mcast.addr":
			case "--multicast.address":
				Standards.cMulticastAddress = pArgs[++i];
				break;
			case "--mcast.port":
			case "--multicast.port":
				Standards.cMulticastPort = pArgs[++i];
				break;
			case "--net.if":
			case "--network.interface":
				Standards.cNetworkInterface = pArgs[++i];
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + tArg);
			}
		}
		
		Runnable tMode = cModes.get(tAppMode);
		if (tMode == null) {
			throw new IllegalArgumentException("Mode not found: " + tAppMode);
		}
		
		tMode.run();
	}
}
