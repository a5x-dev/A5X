package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent to indicate changes in trading status
 */
public class MitchSymbolStatus {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Numeric Identifier of the instrument
	 */
	public long instrumentId;

	/**
	 * H = Halt, T = Regular Trading, a = Opening Auction Call, b = Post-Close, c = Market Close, d = Closing Auction Call, e = Re-Opening Auction Call, l = Pause, w = No Active Session, x = End of Post Close, y = Pre-Trading
	 */
	public byte tradingStatus;

	/**
	 * Reserved for future use
	 */
	public byte flags;

	/**
	 * Reason for the trading halt. This field will contain only spaces if Trading Status is not 'H'
	 */
	public String haltReason;

	/**
	 * 0 = Scheduled Transition, 1 = Extended by Market Ops, 2 = Shortened by Market Ops, 3 = Market Order Extension, 4 = Price Monitoring Extension, 5 = Circuit Breaker Tripped, 9 = Unavailable, 10 = No Cancel Period Start
	 */
	public int sessionChangeReason;

	/**
	 * New session end time (EPOCH). The field will contain only spaces if Session Change Reason is '0' or '9'
	 */
	public BigInteger newEndTime;

	/**
	 * 1 = Regular, 2 = Off-Book, 9 = Bulletin Board, 11 = Negotiated Trades
	 */
	public int subBook;

	/**
	 * Parses SymbolStatus message from ByteBuffer.
	 * 
	 * @param pSymbolStatus the MitchSymbolStatus instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchSymbolStatus pSymbolStatus, ByteBuffer pData) {
		pSymbolStatus.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pSymbolStatus.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pSymbolStatus.tradingStatus = pData.get();
		pSymbolStatus.flags = pData.get();
		byte[] haltReasonBytes = new byte[4];
		pData.get(haltReasonBytes);
		pSymbolStatus.haltReason = new String(haltReasonBytes).trim();
		pSymbolStatus.sessionChangeReason = BitUtils.byteToUInt8(pData);
		pSymbolStatus.newEndTime = BitUtils.byteArrayToUInt64(pData, false);
		pSymbolStatus.subBook = BitUtils.byteToUInt8(pData);
	}

	/**
	 * Encodes SymbolStatus message to ByteBuffer.
	 * 
	 * @param pSymbolStatus the MitchSymbolStatus instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchSymbolStatus pSymbolStatus, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pSymbolStatus.nanosecond, pData, false);
		BitUtils.uint32ToByteArray(pSymbolStatus.instrumentId, pData, false);
		pData.put(pSymbolStatus.tradingStatus);
		pData.put(pSymbolStatus.flags);
		byte[] haltReasonBytes = new byte[4];
		byte[] haltReasonSrc = pSymbolStatus.haltReason.getBytes();
		System.arraycopy(haltReasonSrc, 0, haltReasonBytes, 0, Math.min(haltReasonSrc.length, 4));
		pData.put(haltReasonBytes);
		BitUtils.uint8ToByte(pSymbolStatus.sessionChangeReason, pData);
		BitUtils.uint64ToByteArray(pSymbolStatus.newEndTime, pData, false);
		BitUtils.uint8ToByte(pSymbolStatus.subBook, pData);
	}

	@Override
	public String toString() {
		return "MitchSymbolStatus [" + "nanosecond=" + nanosecond + ", instrumentId=" + instrumentId + ", tradingStatus=" + tradingStatus + ", flags=" + flags + ", haltReason=" + haltReason + ", sessionChangeReason=" + sessionChangeReason + ", newEndTime=" + newEndTime + ", subBook=" + subBook + "]";
	}
}
