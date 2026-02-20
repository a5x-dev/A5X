package br.com.a5x.trd.pub.mitch.model;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MitchEventWrapper} class.
 * Tests message parsing and dispatching functionality.
 */
class MitchEventWrapperTest {

	@Test
	void testParseTimeMessage() {
		ByteBuffer buffer = ByteBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 7);
		buffer.put((byte) 'T');
		buffer.putInt(3600);
		buffer.flip();
		
		MitchEventWrapper wrapper = new MitchEventWrapper();
		wrapper.parse(buffer);
		
		assertEquals('T', wrapper.eventType);
		assertEquals(3600, wrapper.time.seconds);
	}
	
	@Test
	void testParseSystemEventMessage() {
		ByteBuffer buffer = ByteBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 8);
		buffer.put((byte) 'S');
		buffer.putInt(1000);
		buffer.put((byte) 'O');
		buffer.flip();
		
		MitchEventWrapper wrapper = new MitchEventWrapper();
		wrapper.parse(buffer);
		
		assertEquals('S', wrapper.eventType);
		assertEquals(1000, wrapper.systemEvent.nanosecond);
		assertEquals('O', wrapper.systemEvent.eventCode);
	}
	
	@Test
	void testParseEmptyMessage() {
		ByteBuffer buffer = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 0);
		buffer.flip();
		
		MitchEventWrapper wrapper = new MitchEventWrapper();
		wrapper.parse(buffer);
		
		assertEquals(0, wrapper.eventType);
	}
	
	@Test
	void testParseAddOrderMessage() {
		ByteBuffer buffer = ByteBuffer.allocate(35).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 28);
		buffer.put((byte) 'A');
		buffer.putInt(5000);
		buffer.putLong(123456L);
		buffer.put((byte) 'B');
		buffer.putInt(100);
		buffer.putInt(50000);
		buffer.putLong(10050L);
		buffer.put((byte) 0);
		buffer.flip();
		
		MitchEventWrapper wrapper = new MitchEventWrapper();
		wrapper.parse(buffer);
		
		assertEquals('A', wrapper.eventType);
		assertEquals(5000, wrapper.addOrder.nanosecond);
		assertEquals(123456L, wrapper.addOrder.orderId.longValue());
	}
}
