package br.com.a5x.trd.pub.nativ.client;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import br.com.a5x.trd.pub.nativ.NativeEventWrapper;
import br.com.a5x.trd.pub.nativ.NativeEventWrapper.INativePackageHandler;

/**
 * Splits a TCP byte stream into Native messages and decodes each one.
 *
 * <p>TCP delivers bytes, not messages: one read may carry half a message, or several. Every
 * Native message starts with Start of Message ({@code 0x02}) and a little-endian UInt16
 * Length that counts the bytes from the Message Type onwards, so a whole frame is
 * {@code 3 + Length} bytes. The decoder keeps the bytes of an incomplete frame until the rest
 * arrives, then hands the frame to {@link NativeEventWrapper#parse}.
 *
 * <p>Each frame is decoded from its own slice, so a message whose body the SDK does not fully
 * decode (the entries of a repeating group, for example) cannot shift the next one; those
 * bytes are counted in {@link #undecodedBytes()}.
 *
 * <p>Not thread-safe: feed it from the thread that reads the socket.
 */
public final class NativeStreamDecoder {

    /** Start of Message (1 byte) plus Length (2 bytes). */
    public static final int HEADER_SIZE = 3;
    /** The largest frame the 16-bit Length allows. */
    public static final int MAX_FRAME_SIZE = HEADER_SIZE + 0xFFFF;

    private final INativePackageHandler handler;
    // twice the largest frame: after draining, what is left is always less than one frame
    private final ByteBuffer pending = ByteBuffer.allocate(2 * MAX_FRAME_SIZE).order(ByteOrder.LITTLE_ENDIAN);
    private long undecodedBytes;

    public NativeStreamDecoder(INativePackageHandler handler) {
        this.handler = handler;
    }

    /**
     * Feeds bytes read from the socket and decodes every complete message among them.
     *
     * @return how many messages were decoded
     * @throws NativeProtocolException when the stream is not Native framing (the session
     *                                 should be closed: there is no way to resynchronise)
     */
    public int feed(byte[] bytes, int offset, int length) {
        int decoded = 0;
        while (length > 0) {
            int chunk = Math.min(length, pending.remaining());
            pending.put(bytes, offset, chunk);
            offset += chunk;
            length -= chunk;
            decoded += drain();
        }
        return decoded;
    }

    /** Bytes of received frames that the generated models did not decode. */
    public long undecodedBytes() {
        return undecodedBytes;
    }

    private int drain() {
        pending.flip();
        int decoded = 0;
        try {
            while (pending.remaining() >= HEADER_SIZE) {
                int start = pending.position();
                byte startOfMessage = pending.get(start);
                if (startOfMessage != NativeEventWrapper.START_OF_MESSAGE) {
                    throw new NativeProtocolException(String.format(
                            "Start of Message is 0x%02x, expected 0x02: the stream is out of sync", startOfMessage));
                }
                int bodyLength = Short.toUnsignedInt(pending.getShort(start + 1));
                if (bodyLength == 0) {
                    throw new NativeProtocolException("Length 0: a Native message has at least the Message Type");
                }
                if (pending.remaining() < HEADER_SIZE + bodyLength) {
                    break; // the rest of this frame has not arrived yet
                }
                ByteBuffer frame = pending.slice(start, HEADER_SIZE + bodyLength).order(ByteOrder.LITTLE_ENDIAN);
                try {
                    NativeEventWrapper.parse(frame, handler);
                } catch (IllegalArgumentException | java.nio.BufferUnderflowException e) {
                    throw new NativeProtocolException("Could not decode message type '"
                            + (char) pending.get(start + HEADER_SIZE) + "': " + e, e);
                }
                undecodedBytes += frame.remaining();
                pending.position(start + HEADER_SIZE + bodyLength);
                decoded++;
            }
        } finally {
            pending.compact();
        }
        return decoded;
    }

    /** The bytes received are not a valid Native stream. */
    public static final class NativeProtocolException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public NativeProtocolException(String message) {
            super(message);
        }

        public NativeProtocolException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
