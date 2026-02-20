package br.com.a5x.trd.pub.mitch;

import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;
import br.com.a5x.trd.pub.mitch.model.IMitchPackageHandler;
import br.com.a5x.trd.pub.mitch.model.MitchEventWrapper;

public class MitchJoinApp implements Runnable, IMitchPackageHandler {

	private final MitchEventWrapper mMitchEventWrapper = new MitchEventWrapper();
	private long[] mNextSequenceNumber = new long[100];
	private volatile boolean running = true;

	private String multicastAddress;
	private int port;
	private String networkInterface;

	public MitchJoinApp() {
		// initialize recovery counters
		for (int i = 0; i < mNextSequenceNumber.length; i++) {
			mNextSequenceNumber[i] = 1;
		}
	}

	private void loadParameters() {
		switch (Standards.getStringProperty("ENV", "none")) {
		case "local":
			multicastAddress = Standards.getStringProperty("multicast.address", "239.100.140.50");
			port = Integer.parseInt(Standards.getStringProperty("multicast.port", "10498"));
			networkInterface = Standards.getStringProperty("network.interface", "eth0");
			break;
		case "cert-tob-p":
			multicastAddress = Standards.getStringProperty("multicast.address", "224.4.85.254");
			port = Integer.parseInt(Standards.getStringProperty("multicast.port", "10210"));
			networkInterface = Standards.getStringProperty("network.interface", "eth0");
			break;
		case "cert-tob-s":
			multicastAddress = Standards.getStringProperty("multicast.address", "224.4.85.255");
			port = Integer.parseInt(Standards.getStringProperty("multicast.port", "10211"));
			networkInterface = Standards.getStringProperty("network.interface", "eth0");
			break;
		case "cert-mbo-fut-p":
			multicastAddress = Standards.getStringProperty("multicast.address", "224.4.85.240");
			port = Integer.parseInt(Standards.getStringProperty("multicast.port", "10498"));
			networkInterface = Standards.getStringProperty("network.interface", "eth0");
			break;
		case "cert-mbo-fut-s":
			multicastAddress = Standards.getStringProperty("multicast.address", "224.4.85.241");
			port = Integer.parseInt(Standards.getStringProperty("multicast.port", "10499"));
			networkInterface = Standards.getStringProperty("network.interface", "eth0");
			break;
		case "cert-mbo-opt-p":
			multicastAddress = Standards.getStringProperty("multicast.address", "224.4.85.242");
			port = Integer.parseInt(Standards.getStringProperty("multicast.port", "10200"));
			networkInterface = Standards.getStringProperty("network.interface", "eth0");
			break;
		case "cert-mbo-opt-s":
			multicastAddress = Standards.getStringProperty("multicast.address", "224.4.85.243");
			port = Integer.parseInt(Standards.getStringProperty("multicast.port", "10201"));
			networkInterface = Standards.getStringProperty("network.interface", "eth0");
			break;
		default:			
			multicastAddress = Standards.getStringProperty("multicast.address", "239.100.140.50");
			port = Integer.parseInt(Standards.getStringProperty("multicast.port", "10498"));
			networkInterface = Standards.getStringProperty("network.interface", "eth0");
		}
	}

	@Override
	public void run() {
		loadParameters();

		try (MulticastSocket socket = new MulticastSocket(port)) {
			InetAddress group = InetAddress.getByName(multicastAddress);
			InetSocketAddress groupAddress = new InetSocketAddress(group, port);
			NetworkInterface netIf = NetworkInterface.getByName(networkInterface);

			socket.joinGroup(groupAddress, netIf);
			System.out.println("Joined multicast group: " + multicastAddress + ":" + port);

			byte[] buffer = new byte[1400];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			while (running) {
				socket.receive(packet);
				ByteBuffer byteBuffer = ByteBuffer.wrap(packet.getData(), 0, packet.getLength());
				handle(byteBuffer, (Inet4Address) packet.getAddress());
			}

			socket.leaveGroup(groupAddress, netIf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		running = false;
	}

	@Override
	public void handle(ByteBuffer pData, Inet4Address pMcastAddress) {
		pData.position(0);
		int tLength = BitUtils.byteArrayToUInt16(pData, false);
		if (tLength <= 12) {
			return;
		}

		int tMessageCount = BitUtils.byteToUInt8(pData);
		byte tMarketDataGroup = pData.get();
		long tSequenceNumber = BitUtils.byteArrayToUInt64(pData, false).longValue();

		// for pcap no sequence number check is needed
		long tNextMessage = mNextSequenceNumber[tMarketDataGroup];

		if (tNextMessage > tSequenceNumber) {
			// message already handled.
			return;
		} else if (tNextMessage < tSequenceNumber) {
			System.out.println("Message lost");
		}

		for (int i = 0; i < tMessageCount; i++) {
			mMitchEventWrapper.parse(pData);

			if (pData.position() > tLength) {
				throw new RuntimeException("End of data, message decode error on message sizes");
			}
		}

		mNextSequenceNumber[tMarketDataGroup] += tMessageCount;
		;
	}
}
