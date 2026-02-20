package br.com.a5x.trd.pub.mitch.model;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MitchSymbolStatus} class.
 * Tests symbol status message parsing.
 */
class MitchSymbolStatusTest {

	@Test
	void testParseSymbolStatusHalt() {
		ByteBuffer buffer = ByteBuffer.allocate(30).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(2000);
		buffer.putInt(12345);
		buffer.put((byte) 'H');
		buffer.put((byte) 0);
		buffer.put("REGR".getBytes());
		buffer.put((byte) 1);
		buffer.putLong(1234567890L);
		buffer.put((byte) 1);
		buffer.flip();
		
		MitchSymbolStatus status = new MitchSymbolStatus();
		MitchSymbolStatus.parse(status, buffer);
		
		assertEquals(2000, status.nanosecond);
		assertEquals(12345, status.instrumentId);
		assertEquals('H', status.tradingStatus);
		assertEquals("REGR", new String(status.haltReason));
		assertEquals(1, status.sessionChangeReason);
		assertEquals(1, status.subBook);
	}
	
	@Test
	void testParseSymbolStatusTrading() {
		ByteBuffer buffer = ByteBuffer.allocate(30).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(3000);
		buffer.putInt(54321);
		buffer.put((byte) 'T');
		buffer.put((byte) 0);
		buffer.put("    ".getBytes());
		buffer.put((byte) 0);
		buffer.putLong(0L);
		buffer.put((byte) 1);
		buffer.flip();
		
		MitchSymbolStatus status = new MitchSymbolStatus();
		MitchSymbolStatus.parse(status, buffer);
		
		assertEquals(3000, status.nanosecond);
		assertEquals(54321, status.instrumentId);
		assertEquals('T', status.tradingStatus);
		assertEquals(0, status.sessionChangeReason);
	}
}
