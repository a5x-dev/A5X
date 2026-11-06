package br.com.a5x.trd.pub.nativ.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.a5x.trd.pub.nativ.NativeEventWrapper;
import br.com.a5x.trd.pub.nativ.model.NativeExecutionReport;
import br.com.a5x.trd.pub.nativ.model.NativeHeartbeat;
import br.com.a5x.trd.pub.nativ.model.NativeNewOrderSingle;

/** TCP framing: message boundaries do not survive the stream, the decoder restores them. */
class NativeStreamDecoderTest {

    private static final class Collector extends NativeHandlerAdapter {
        final List<Object> messages = new ArrayList<>();

        @Override
        public void onNativeNewOrderSingle(NativeNewOrderSingle msg) {
            messages.add(msg);
        }

        @Override
        public void onNativeExecutionReport(NativeExecutionReport msg) {
            messages.add(msg);
        }

        @Override
        public void onNativeHeartbeat(NativeHeartbeat msg) {
            messages.add(msg);
        }
    }

    private static byte[] wire(Object... messages) {
        ByteBuffer buffer = ByteBuffer.allocate(8192).order(ByteOrder.LITTLE_ENDIAN);
        for (Object m : messages) {
            NativeEventWrapper.encode(buffer, m);
        }
        byte[] bytes = new byte[buffer.position()];
        buffer.flip().get(bytes);
        return bytes;
    }

    private static NativeNewOrderSingle order(String id) {
        NativeNewOrderSingle o = new NativeNewOrderSingle();
        o.clientOrderId = id;
        o.instrumentId = 1234;
        o.side = 2;
        o.orderQuantity = 7;
        o.limitPrice = NativePrices.toWire(new BigDecimal("-0.5"));
        return o;
    }

    @Test
    void lengthCountsFromTheMessageType() {
        byte[] bytes = wire(new NativeHeartbeat());
        assertEquals(4, bytes.length, "0x02, Length (2 bytes), '0'");
        assertEquals(0x02, bytes[0]);
        assertEquals(1, (bytes[1] & 0xff) | (bytes[2] & 0xff) << 8, "only the Message Type follows");
        assertEquals('0', bytes[3]);
    }

    @Test
    void everySplitPointDecodesTheSameMessages() {
        byte[] bytes = wire(order("A1"), new NativeHeartbeat(), order("A2"));
        for (int cut = 0; cut <= bytes.length; cut++) {
            Collector c = new Collector();
            NativeStreamDecoder decoder = new NativeStreamDecoder(c);
            decoder.feed(bytes, 0, cut);
            decoder.feed(bytes, cut, bytes.length - cut);
            assertEquals(3, c.messages.size(), "cut at " + cut);
            assertEquals("A1", ((NativeNewOrderSingle) c.messages.get(0)).clientOrderId);
            assertEquals("A2", ((NativeNewOrderSingle) c.messages.get(2)).clientOrderId);
            assertEquals(-50_000_000L, ((NativeNewOrderSingle) c.messages.get(2)).limitPrice, "two's complement");
        }
    }

    @Test
    void oneByteAtATime() {
        byte[] bytes = wire(order("B1"), order("B2"));
        Collector c = new Collector();
        NativeStreamDecoder decoder = new NativeStreamDecoder(c);
        for (int i = 0; i < bytes.length; i++) {
            decoder.feed(bytes, i, 1);
        }
        assertEquals(2, c.messages.size());
        assertEquals(0, decoder.undecodedBytes());
    }

    @Test
    void streamOutOfSyncIsRefused() {
        byte[] bytes = wire(order("C1"));
        bytes[0] = 0x7f;
        NativeStreamDecoder decoder = new NativeStreamDecoder(new Collector());
        assertThrows(NativeStreamDecoder.NativeProtocolException.class, () -> decoder.feed(bytes, 0, bytes.length));
    }

    @Test
    void unknownMessageTypeIsRefused() {
        byte[] bytes = { 0x02, 1, 0, '#' };
        NativeStreamDecoder decoder = new NativeStreamDecoder(new Collector());
        NativeStreamDecoder.NativeProtocolException e = assertThrows(NativeStreamDecoder.NativeProtocolException.class,
                () -> decoder.feed(bytes, 0, bytes.length));
        assertTrue(e.getMessage().contains("'#'"), e.getMessage());
    }

    @Test
    void extraBytesInAFrameDoNotShiftTheNextMessage() {
        // a frame longer than its model (a newer field, a repeating group): the decoder skips
        // the rest of the frame by its Length instead of reading it as the next message
        byte[] heartbeatWithTail = { 0x02, 4, 0, '0', 9, 9, 9 };
        byte[] next = wire(order("D1"));
        byte[] bytes = new byte[heartbeatWithTail.length + next.length];
        System.arraycopy(heartbeatWithTail, 0, bytes, 0, heartbeatWithTail.length);
        System.arraycopy(next, 0, bytes, heartbeatWithTail.length, next.length);
        Collector c = new Collector();
        NativeStreamDecoder decoder = new NativeStreamDecoder(c);
        decoder.feed(bytes, 0, bytes.length);
        assertEquals(2, c.messages.size());
        assertEquals(3, decoder.undecodedBytes());
    }

    @Test
    void largestFramesFitTheBuffer() {
        // many frames in one feed, more bytes than the internal buffer holds at once
        Object[] many = new Object[2000];
        for (int i = 0; i < many.length; i++) {
            many[i] = order("E" + i);
        }
        ByteBuffer buffer = ByteBuffer.allocate(1 << 20).order(ByteOrder.LITTLE_ENDIAN);
        for (Object m : many) {
            NativeEventWrapper.encode(buffer, m);
        }
        byte[] bytes = new byte[buffer.position()];
        buffer.flip().get(bytes);
        Collector c = new Collector();
        assertEquals(many.length, new NativeStreamDecoder(c).feed(bytes, 0, bytes.length));
    }
}
