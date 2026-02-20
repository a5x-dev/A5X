package br.com.a5x.trd.pub.mitch.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for MITCH protocol message parsing.
 * Tests all message types defined in the MITCH specification.
 */
class MitchMessageTest {

	@Test
	void testMitchTimeParser() {
		ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(3600); // 1 hour in seconds
		buffer.flip();
		
		MitchTime time = new MitchTime();
		MitchTime.parse(time, buffer);
		
		assertEquals(3600, time.seconds);
	}

	@Test
	void testMitchSystemEventParser() {
		ByteBuffer buffer = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1000000); // nanoseconds
		buffer.put((byte) 'O'); // Start of Day
		buffer.flip();
		
		MitchSystemEvent event = new MitchSystemEvent();
		MitchSystemEvent.parse(event, buffer);
		
		assertEquals(1000000, event.nanosecond);
		assertEquals('O', event.eventCode);
	}

	@Test
	void testMitchAddOrderParser() {
		ByteBuffer buffer = ByteBuffer.allocate(33).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(500000); // nanosecond
		buffer.putLong(123456789L); // orderId
		buffer.put((byte) 'B'); // side
		buffer.putInt(1000); // quantity
		buffer.putInt(100); // instrumentId
		buffer.putLong(5000L); // price
		buffer.put((byte) 0); // flags
		buffer.flip();
		
		MitchAddOrder addOrder = new MitchAddOrder();
		MitchAddOrder.parse(addOrder, buffer);
		
		assertEquals(500000, addOrder.nanosecond);
		assertEquals(new BigInteger("123456789"), addOrder.orderId);
		assertEquals('B', addOrder.side);
		assertEquals(1000, addOrder.quantity);
		assertEquals(100, addOrder.instrumentId);
		assertEquals(5000L, addOrder.price);
		assertEquals(0, addOrder.flags);
	}

	@Test
	void testMitchOrderDeletedParser() {
		ByteBuffer buffer = ByteBuffer.allocate(24).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(600000); // nanosecond
		buffer.putLong(987654321L); // orderId
		buffer.putInt(500); // oldQuantity
		buffer.putLong(4500L); // oldPrice
		buffer.flip();
		
		MitchOrderDeleted orderDeleted = new MitchOrderDeleted();
		MitchOrderDeleted.parse(orderDeleted, buffer);
		
		assertEquals(600000, orderDeleted.nanosecond);
		assertEquals(new BigInteger("987654321"), orderDeleted.orderId);
		assertEquals(500, orderDeleted.oldQuantity);
		assertEquals(4500L, orderDeleted.oldPrice);
	}

	@Test
	void testMitchOrderModifiedParser() {
		ByteBuffer buffer = ByteBuffer.allocate(41).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(700000); // nanosecond
		buffer.putLong(111222333L); // orderId
		buffer.putInt(800); // newQuantity
		buffer.putLong(5500L); // newPrice
		buffer.putInt(1000); // oldQuantity
		buffer.putLong(5000L); // oldPrice
		buffer.put((byte) 1); // flags (priority retained)
		buffer.flip();
		
		MitchOrderModified orderModified = new MitchOrderModified();
		MitchOrderModified.parse(orderModified, buffer);
		
		assertEquals(700000, orderModified.nanosecond);
		assertEquals(new BigInteger("111222333"), orderModified.orderId);
		assertEquals(800, orderModified.newQuantity);
		assertEquals(5500L, orderModified.newPrice);
		assertEquals(1000, orderModified.oldQuantity);
		assertEquals(5000L, orderModified.oldPrice);
		assertEquals(1, orderModified.flags);
	}

	@Test
	void testMitchOrderBookClearParser() {
		ByteBuffer buffer = ByteBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(800000); // nanosecond
		buffer.putInt(200); // instrumentId
		buffer.put((byte) 1); // subBook (Regular)
		buffer.put((byte) 0); // bookType (MBP/MBO/Statistics)
		buffer.flip();
		
		MitchOrderBookClear orderBookClear = new MitchOrderBookClear();
		MitchOrderBookClear.parse(orderBookClear, buffer);
		
		assertEquals(800000, orderBookClear.nanosecond);
		assertEquals(200, orderBookClear.instrumentId);
		assertEquals(1, orderBookClear.subBook);
		assertEquals(0, orderBookClear.bookType);
	}

	@Test
	void testMitchAuctionInfoParser() {
		ByteBuffer buffer = ByteBuffer.allocate(29).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(900000); // nanosecond
		buffer.putInt(5000); // pairedQuantity
		buffer.putInt(1000); // imbalanceQuantity
		buffer.put((byte) 'B'); // imbalanceDirection (Buy Imbalance)
		buffer.putInt(300); // instrumentId
		buffer.putLong(10000L); // price
		buffer.put((byte) 'O'); // auctionType (Opening Auction)
		buffer.flip();
		
		MitchAuctionInfo auctionInfo = new MitchAuctionInfo();
		MitchAuctionInfo.parse(auctionInfo, buffer);
		
		assertEquals(900000, auctionInfo.nanosecond);
		assertEquals(5000, auctionInfo.pairedQuantity);
		assertEquals(1000, auctionInfo.imbalanceQuantity);
		assertEquals('B', auctionInfo.imbalanceDirection);
		assertEquals(300, auctionInfo.instrumentId);
		assertEquals(10000L, auctionInfo.price);
		assertEquals('O', auctionInfo.auctionType);
	}

	@Test
	void testMitchStatisticsParser() {
		ByteBuffer buffer = ByteBuffer.allocate(22).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1000000); // nanosecond
		buffer.putInt(400); // instrumentId
		buffer.put((byte) 'O'); // statisticType (Opening Price)
		buffer.putLong(15000L); // price
		buffer.put((byte) 'A'); // openCloseIndicator (Auction Trade)
		buffer.put((byte) 1); // subBook (Regular)
		buffer.flip();
		
		MitchStatistics statistics = new MitchStatistics();
		MitchStatistics.parse(statistics, buffer);
		
		assertEquals(1000000, statistics.nanosecond);
		assertEquals(400, statistics.instrumentId);
		assertEquals('O', statistics.statisticType);
		assertEquals(15000L, statistics.price);
		assertEquals('A', statistics.openCloseIndicator);
		assertEquals(1, statistics.subBook);
	}

	@Test
	void testMitchSymbolDirectoryToString() {
		MitchSymbolDirectory symbolDir = new MitchSymbolDirectory();
		symbolDir.nanosecond = 123456;
		symbolDir.symbol = new String("PETR4   ".getBytes()).trim();
		symbolDir.instrumentId = 100;
		symbolDir.symbolStatus = "A";
		
		String result = symbolDir.toString();
		assertTrue(result.contains("nanosecond=123456"));
		assertTrue(result.contains("symbol=PETR4"));
		assertTrue(result.contains("instrumentId=100"));
	}

	@Test
	void testMitchTopOfBookParser() {
		ByteBuffer buffer = ByteBuffer.allocate(38).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1100000); // nanosecond
		buffer.putInt(500); // instrumentId
		buffer.put((byte) 1); // subBook
		buffer.put((byte) '1'); // action (Update)
		buffer.put((byte) 'B'); // side (Buy)
		buffer.putLong(20000L); // price
		buffer.putInt(10000); // quantity
		buffer.putInt(500); // marketOrderQuantity
		buffer.put(new byte[2]); // reservedField
		buffer.putInt(5); // splits
		buffer.flip();
		
		MitchTopOfBook topOfBook = new MitchTopOfBook();
		MitchTopOfBook.parse(topOfBook, buffer);
		
		assertEquals(1100000, topOfBook.nanosecond);
		assertEquals(500, topOfBook.instrumentId);
		assertEquals(1, topOfBook.subBook);
		assertEquals('1', topOfBook.action);
		assertEquals('B', topOfBook.side);
		assertEquals(10000, topOfBook.quantity);
		assertEquals(5, topOfBook.splits);
	}

	@Test
	void testMitchLoginRequestParser() {
		ByteBuffer buffer = ByteBuffer.allocate(75).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put("USER01".getBytes()); // username (6 bytes)
		buffer.put("PASS123456".getBytes()); // password (10 bytes)
		buffer.put("192.168.001.100".getBytes()); // ipAddress (15 bytes)
		buffer.put("abcd1234efgh5678ijkl9012mnop3456qrst7890wxyz".getBytes()); // loginHash (44 bytes)
		buffer.flip();
		
		MitchLoginRequest loginRequest = new MitchLoginRequest();
		MitchLoginRequest.parse(loginRequest, buffer);
		
		assertEquals("USER01", new String(loginRequest.username.getBytes()).trim());
		assertEquals("PASS123456", new String(loginRequest.password.getBytes()).trim());
		assertEquals("192.168.001.100", new String(loginRequest.ipAddress.getBytes()).trim());
		assertEquals("abcd1234efgh5678ijkl9012mnop3456qrst7890wxyz", new String(loginRequest.certifiedSolution.getBytes()).trim());
	}

	@Test
	void testMitchLoginResponseParser() {
		ByteBuffer buffer = ByteBuffer.allocate(1).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put((byte) 'A'); // status = Login Accepted
		buffer.flip();
		
		MitchLoginResponse loginResponse = new MitchLoginResponse();
		MitchLoginResponse.parse(loginResponse, buffer);
		
		assertEquals('A', loginResponse.status);
	}

	@Test
	void testMitchReplayRequestParser() {
		ByteBuffer buffer = ByteBuffer.allocate(11).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put((byte) 1); // marketDataGroup
		buffer.putLong(1000L); // firstMessage
		buffer.putShort((short) 50); // count
		buffer.flip();
		
		MitchReplayRequest replayRequest = new MitchReplayRequest();
		MitchReplayRequest.parse(replayRequest, buffer);
		
		assertEquals(1, replayRequest.marketDataGroup);
		assertEquals(new BigInteger("1000"), replayRequest.firstMessage);
		assertEquals(50, replayRequest.count);
	}

	@Test
	void testMitchReplayResponseParser() {
		ByteBuffer buffer = ByteBuffer.allocate(12).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put((byte) 1); // marketDataGroup
		buffer.putLong(1000L); // firstMessage
		buffer.putShort((short) 50); // count
		buffer.put((byte) 'A'); // status = Request Accepted
		buffer.flip();
		
		MitchReplayResponse replayResponse = new MitchReplayResponse();
		MitchReplayResponse.parse(replayResponse, buffer);
		
		assertEquals(1, replayResponse.marketDataGroup);
		assertEquals(new BigInteger("1000"), replayResponse.firstMessage);
		assertEquals(50, replayResponse.count);
		assertEquals('A', replayResponse.status);
	}

	@Test
	void testMitchLogoutRequestParser() {
		ByteBuffer buffer = ByteBuffer.allocate(0).order(ByteOrder.LITTLE_ENDIAN);
		buffer.flip();
		
		MitchLogoutRequest logoutRequest = new MitchLogoutRequest();
		MitchLogoutRequest.parse(logoutRequest, buffer);
		
		assertNotNull(logoutRequest);
	}

	@Test
	void testMitchSnapshotRequestParser() {
		ByteBuffer buffer = ByteBuffer.allocate(44).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(5000L); // sequenceNumber
		buffer.put("SEGMENT001".getBytes()); // segment (10 bytes)
		buffer.putInt(100); // instrumentId
		buffer.put((byte) 1); // subBook
		buffer.put((byte) 0); // snapshotType = Order Book
		buffer.putLong(1704067200L); // recoverFromTime
		buffer.putInt(12345); // requestId
		buffer.flip();
		
		MitchSnapshotRequest snapshotRequest = new MitchSnapshotRequest();
		MitchSnapshotRequest.parse(snapshotRequest, buffer);
		
		assertEquals(new BigInteger("5000"), snapshotRequest.sequenceNumber);
		assertEquals("SEGMENT001", snapshotRequest.segment);
		assertEquals(100, snapshotRequest.instrumentId);
		assertEquals(1, snapshotRequest.subBook);
		assertEquals(0, snapshotRequest.snapshotType);
		assertEquals(new BigInteger("1704067200"), snapshotRequest.recoverFromTime);
		assertEquals(12345, snapshotRequest.requestId);
	}

	@Test
	void testMitchSnapshotResponseParser() {
		ByteBuffer buffer = ByteBuffer.allocate(18).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(5000L); // sequenceNumber
		buffer.putInt(250); // orderCount
		buffer.put((byte) 'A'); // status = Request Accepted
		buffer.put((byte) 0); // snapshotType = Order Book
		buffer.putInt(12345); // requestId
		buffer.flip();
		
		MitchSnapshotResponse snapshotResponse = new MitchSnapshotResponse();
		MitchSnapshotResponse.parse(snapshotResponse, buffer);
		
		assertEquals(new BigInteger("5000"), snapshotResponse.sequenceNumber);
		assertEquals(250, snapshotResponse.orderCount);
		assertEquals('A', snapshotResponse.status);
		assertEquals(0, snapshotResponse.snapshotType);
		assertEquals(12345, snapshotResponse.requestId);
	}

	@Test
	void testMitchSnapshotCompleteParser() {
		ByteBuffer buffer = ByteBuffer.allocate(29).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(5000L); // sequenceNumber
		buffer.put("SEGMENT001".getBytes()); // segment (10 bytes)
		buffer.putInt(100); // instrumentId
		buffer.put((byte) 1); // subBook
		buffer.put((byte) 'T'); // tradingStatus = Regular Trading
		buffer.put((byte) 0); // snapshotType = Order Book
		buffer.putInt(12345); // requestId
		buffer.flip();
		
		MitchSnapshotComplete snapshotComplete = new MitchSnapshotComplete();
		MitchSnapshotComplete.parse(snapshotComplete, buffer);
		
		assertEquals(new BigInteger("5000"), snapshotComplete.sequenceNumber);
		assertEquals("SEGMENT001", snapshotComplete.segment);
		assertEquals(100, snapshotComplete.instrumentId);
		assertEquals(1, snapshotComplete.subBook);
		assertEquals('T', snapshotComplete.tradingStatus);
		assertEquals(0, snapshotComplete.snapshotType);
		assertEquals(12345, snapshotComplete.requestId);
	}
}
