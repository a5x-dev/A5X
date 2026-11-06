package br.com.a5x.trd.pub.nativ.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import br.com.a5x.trd.pub.nativ.NativeEventWrapper;
import br.com.a5x.trd.pub.nativ.NativeEventWrapper.INativePackageHandler;

/**
 * Every message type of the handler interface: filled with non-default values, framed,
 * decoded back through the stream decoder, and compared field by field. A model whose encode
 * and parse disagree on a single field size fails here.
 */
class NativeRoundTripTest {

    @Test
    void everyMessageTypeSurvivesTheWire() throws Exception {
        int checked = 0;
        for (Method callback : INativePackageHandler.class.getMethods()) {
            Class<?> type = callback.getParameterTypes()[0];
            Object sent = type.getDeclaredConstructor().newInstance();
            fill(sent);

            ByteBuffer buffer = ByteBuffer.allocate(NativeStreamDecoder.MAX_FRAME_SIZE).order(ByteOrder.LITTLE_ENDIAN);
            NativeEventWrapper.encode(buffer, sent);
            byte[] bytes = new byte[buffer.position()];
            buffer.flip().get(bytes);
            int length = (bytes[1] & 0xff) | (bytes[2] & 0xff) << 8;
            assertEquals(bytes.length - 3, length, type.getSimpleName() + ": Length is the bytes after it");

            AtomicReference<Object> received = new AtomicReference<>();
            INativePackageHandler handler = (INativePackageHandler) Proxy.newProxyInstance(getClass().getClassLoader(),
                    new Class<?>[] { INativePackageHandler.class }, (p, m, a) -> {
                        received.set(a[0]);
                        return null;
                    });
            NativeStreamDecoder decoder = new NativeStreamDecoder(handler);
            assertEquals(1, decoder.feed(bytes, 0, bytes.length), type.getSimpleName());
            assertNotNull(received.get(), type.getSimpleName());
            assertEquals(type, received.get().getClass());
            assertEquals(0, decoder.undecodedBytes(), type.getSimpleName() + ": parse read the whole frame");
            assertEquals(sent.toString(), received.get().toString(), type.getSimpleName());
            checked++;
        }
        assertEquals(INativePackageHandler.class.getMethods().length, checked);
    }

    /** A distinct value per field, inside the range of its wire type. */
    private static void fill(Object message) throws IllegalAccessException {
        int seed = 1;
        for (Field f : message.getClass().getFields()) {
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            seed++;
            Class<?> t = f.getType();
            if (t == String.class) {
                // one character: the shortest alpha field (Execution Type) is 1 byte long
                f.set(message, String.valueOf((char) ('A' + seed % 26)));
            } else if (t == int.class) {
                f.setInt(message, seed % 100); // also fits uint8 and uint16
            } else if (t == long.class) {
                f.setLong(message, seed * 1000L);
            } else if (t == byte.class) {
                f.setByte(message, (byte) ('A' + seed % 26));
            } else if (t == short.class) {
                f.setShort(message, (short) seed);
            }
        }
    }
}
