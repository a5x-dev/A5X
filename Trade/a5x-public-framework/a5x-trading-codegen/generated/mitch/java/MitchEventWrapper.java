package br.com.a5x.trd.pub.mitch.model;

import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Wrapper class for parsing and dispatching MITCH protocol messages.
 * Handles all MITCH message types and routes them to appropriate message objects.
 */
public class MitchEventWrapper {
	public char eventType;
	public MitchTime time = new MitchTime();
	public MitchSystemEvent systemEvent = new MitchSystemEvent();
	public MitchAddOrder addOrder = new MitchAddOrder();
	public MitchAddAttributedOrder addAttributedOrder = new MitchAddAttributedOrder();
	public MitchOrderDeleted orderDeleted = new MitchOrderDeleted();
	public MitchOrderModified orderModified = new MitchOrderModified();
	public MitchOrderBookClear orderBookClear = new MitchOrderBookClear();
	public MitchOrderExecuted orderExecuted = new MitchOrderExecuted();
	public MitchOrderExecutedWithPriceSize orderExecutedWithPriceSize = new MitchOrderExecutedWithPriceSize();
	public MitchAuctionInfo auctionInfo = new MitchAuctionInfo();
	public MitchAuctionTrade auctionTrade = new MitchAuctionTrade();
	public MitchRecoveryTrade recoveryTrade = new MitchRecoveryTrade();
	public MitchStatistics statistics = new MitchStatistics();
	public MitchExtendedStatistics extendedStatistics = new MitchExtendedStatistics();
	public MitchSymbolStatus symbolStatus = new MitchSymbolStatus();
	public MitchTopOfBook topOfBook = new MitchTopOfBook();
	public MitchTrade trade = new MitchTrade();
	public MitchNews news = new MitchNews();
	public MitchLoginRequest loginRequest = new MitchLoginRequest();
	public MitchLoginResponse loginResponse = new MitchLoginResponse();
	public MitchLogoutRequest logoutRequest = new MitchLogoutRequest();
	public MitchSymbolDirectory symbolDirectory = new MitchSymbolDirectory();
	public MitchReplayRequest replayRequest = new MitchReplayRequest();
	public MitchReplayResponse replayResponse = new MitchReplayResponse();
	public MitchSnapshotRequest snapshotRequest = new MitchSnapshotRequest();
	public MitchSnapshotResponse snapshotResponse = new MitchSnapshotResponse();
	public MitchSnapshotComplete snapshotComplete = new MitchSnapshotComplete();
	
	/**
	 * Parses a MITCH message from ByteBuffer and populates the appropriate message object.
	 * Automatically handles message length and positioning.
	 * 
	 * @param pData the ByteBuffer containing the message data (little-endian)
	 */
	public void parse(ByteBuffer pData) {
		int tInitialPosition = pData.position();
		int tLength = BitUtils.byteArrayToUInt16(pData, false);
		if (tLength == 0) {
			return;
		}
		
		eventType = (char)pData.get();
		
		switch (eventType) {
		case (char)0x54: {
			MitchTime.parse(time, pData);
			break;
		}
		case (char)0x53: {
			MitchSystemEvent.parse(systemEvent, pData);
			break;
		}
		case (char)0x41: {
			MitchAddOrder.parse(addOrder, pData);
			break;
		}
		case (char)0x46: {
			MitchAddAttributedOrder.parse(addAttributedOrder, pData);
			break;
		}
		case (char)0x44: {
			MitchOrderDeleted.parse(orderDeleted, pData);
			break;
		}
		case (char)0x55: {
			MitchOrderModified.parse(orderModified, pData);
			break;
		}
		case (char)0x79: {
			MitchOrderBookClear.parse(orderBookClear, pData);
			break;
		}
		case (char)0x45: {
			MitchOrderExecuted.parse(orderExecuted, pData);
			break;
		}
		case (char)0x43: {
			MitchOrderExecutedWithPriceSize.parse(orderExecutedWithPriceSize, pData);
			break;
		}
		case (char)0x49: {
			MitchAuctionInfo.parse(auctionInfo, pData);
			break;
		}
		case (char)0x51: {
			MitchAuctionTrade.parse(auctionTrade, pData);
			break;
		}
		case (char)0x76: {
			MitchRecoveryTrade.parse(recoveryTrade, pData);
			break;
		}
		case (char)0x77: {
			MitchStatistics.parse(statistics, pData);
			break;
		}
		case (char)0x80: {
			MitchExtendedStatistics.parse(extendedStatistics, pData);
			break;
		}
		case (char)0x48: {
			MitchSymbolStatus.parse(symbolStatus, pData);
			break;
		}
		case (char)0x71: {
			MitchTopOfBook.parse(topOfBook, pData);
			break;
		}
		case (char)0x50: {
			MitchTrade.parse(trade, pData);
			break;
		}
		case (char)0x75: {
			MitchNews.parse(news, pData);
			break;
		}
		case (char)0x01: {
			MitchLoginRequest.parse(loginRequest, pData);
			break;
		}
		case (char)0x02: {
			MitchLoginResponse.parse(loginResponse, pData);
			break;
		}
		case (char)0x05: {
			MitchLogoutRequest.parse(logoutRequest, pData);
			break;
		}
		case (char)0x52: {
			MitchSymbolDirectory.parse(symbolDirectory, pData);
			break;
		}
		case (char)0x03: {
			MitchReplayRequest.parse(replayRequest, pData);
			break;
		}
		case (char)0x04: {
			MitchReplayResponse.parse(replayResponse, pData);
			break;
		}
		case (char)0x81: {
			MitchSnapshotRequest.parse(snapshotRequest, pData);
			break;
		}
		case (char)0x82: {
			MitchSnapshotResponse.parse(snapshotResponse, pData);
			break;
		}
		case (char)0x83: {
			MitchSnapshotComplete.parse(snapshotComplete, pData);
			break;
		}
		default:
			System.out.println("Unknown message type: " + eventType + " (0x" + Integer.toHexString(eventType) + "), length: " + tLength);
		}
		
		// Handle message length changes
		pData.position(tInitialPosition + tLength);
	}
}
