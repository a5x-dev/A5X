package br.com.a5x.trd.pub.mitch;

import java.net.Inet4Address;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;
import br.com.a5x.trd.pub.mitch.model.IMitchPackageHandler;
import br.com.a5x.trd.pub.mitch.model.MitchEventWrapper;
import br.com.a5x.trd.pub.utils.PcapReader;

public class MitchPcapApp implements Runnable, IMitchPackageHandler {
	private final PcapReader mPcapReader;
	private final MitchEventWrapper mMitchEventWrapper = new MitchEventWrapper();
	
	private long[] mNextSequenceNumber = new long[100];
	
	public MitchPcapApp() {
		mPcapReader = new PcapReader();
		
		// initialize recovery counters
		for(int i = 0; i < mNextSequenceNumber.length; i++) {
			mNextSequenceNumber[i] = 1;
		}
	}
	
	@Override
	public void run() {
		try {
			mPcapReader.readPcap(Standards.cFile, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		
		for(int i = 0; i < tMessageCount; i++) {
			mMitchEventWrapper.parse(pData);
			
			if (pData.position() > tLength) {
				throw new RuntimeException("End of data, message decode error on message sizes");
			}
		}
		
		mNextSequenceNumber[tMarketDataGroup]+=tMessageCount;;
	}
}
