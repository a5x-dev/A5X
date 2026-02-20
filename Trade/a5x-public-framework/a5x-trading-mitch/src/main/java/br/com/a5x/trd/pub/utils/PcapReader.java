package br.com.a5x.trd.pub.utils;

import java.nio.ByteBuffer;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;

import br.com.a5x.trd.pub.mitch.model.IMitchPackageHandler;

/**
 * PCAP file reader for processing network packet captures.
 * Extracts UDP payload data and delegates to a MITCH package handler.
 */
public class PcapReader {
	private ByteBuffer mBuffer = ByteBuffer.allocate(Short.MAX_VALUE * 2);
    
    /**
     * Reads a PCAP file and processes each packet through the provided handler.
     * 
     * @param filePath the path to the PCAP file
     * @param pHandler the handler to process each packet's payload
     * @throws Exception if file cannot be read or packet processing fails
     */
    public void readPcap(String filePath, IMitchPackageHandler pHandler) throws Exception {
        PcapHandle handle = Pcaps.openOffline(filePath);
        
        Packet tPacket;
        while ((tPacket = handle.getNextPacket()) != null) {
            mBuffer.clear();
            mBuffer.put(tPacket.getPayload().getPayload().getPayload().getRawData());
            
            IpV4Packet tIpV4Packet = (IpV4Packet)tPacket.getPayload();
            pHandler.handle(mBuffer, tIpV4Packet.getHeader().getDstAddr());
        }
        
        handle.close();
    }
}
