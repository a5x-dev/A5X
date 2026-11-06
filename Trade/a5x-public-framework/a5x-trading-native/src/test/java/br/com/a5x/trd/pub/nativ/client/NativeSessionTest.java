package br.com.a5x.trd.pub.nativ.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import br.com.a5x.trd.pub.nativ.model.NativeExecutionReport;
import br.com.a5x.trd.pub.nativ.model.NativeHeartbeat;
import br.com.a5x.trd.pub.nativ.model.NativeLogon;
import br.com.a5x.trd.pub.nativ.model.NativeLogonResponse;
import br.com.a5x.trd.pub.nativ.model.NativeLogout;
import br.com.a5x.trd.pub.nativ.model.NativeNewOrderSingle;

/** The session against a fake gateway on the loopback interface. */
class NativeSessionTest {

    private static final class Recorder implements NativeSession.Listener {
        final BlockingQueue<Object> messages = new LinkedBlockingQueue<>();
        final CountDownLatch disconnected = new CountDownLatch(1);
        volatile String reason;

        @Override
        public void onMessage(Object message) {
            messages.add(message);
        }

        @Override
        public void onDisconnected(String why, Throwable cause) {
            reason = why;
            disconnected.countDown();
        }
    }

    private static NativeSession.Options fast() {
        return new NativeSession.Options(Duration.ofMillis(100), Duration.ofMillis(400), Duration.ofSeconds(2),
                Duration.ofSeconds(2));
    }

    private static NativeLogon logon() {
        NativeLogon l = new NativeLogon();
        l.compId = "TEST01";
        l.password = "pw"; // the fake gateway accepts any password
        return l;
    }

    private static <T> T next(BlockingQueue<Object> q, Class<T> type) throws InterruptedException {
        for (long end = System.nanoTime() + TimeUnit.SECONDS.toNanos(3); System.nanoTime() < end;) {
            Object m = q.poll(100, TimeUnit.MILLISECONDS);
            if (type.isInstance(m)) {
                return type.cast(m);
            }
        }
        throw new AssertionError("no " + type.getSimpleName() + " within 3 s");
    }

    @Test
    void logonOrderAndLogout() throws Exception {
        try (FakeGateway gateway = new FakeGateway(FakeGateway.LogonBehaviour.ACCEPT)) {
            Recorder recorder = new Recorder();
            NativeSession session = NativeSession.connect(gateway.host(), gateway.port(), fast(), recorder);
            NativeLogonResponse response = session.logon(logon());
            assertEquals(0, response.rejectCode);
            assertEquals("TEST01", next(gateway.received, NativeLogon.class).compId);

            NativeNewOrderSingle order = NativeClientExample.limitOrder(Map.of("instrument", "1234", "side", "buy",
                    "quantity", "10", "price", "5.25", "account", "1001", "client-order-id", "T1"));
            session.send(order);
            NativeExecutionReport report = next(recorder.messages, NativeExecutionReport.class);
            assertEquals("T1", report.clientOrderId);
            assertEquals(525_000_000L, next(gateway.received, NativeNewOrderSingle.class).limitPrice);

            session.logout(Duration.ofSeconds(2));
            assertInstanceOf(NativeLogout.class, next(recorder.messages, NativeLogout.class));
            assertTrue(session.isClosed());
        }
    }

    @Test
    void heartbeatsGoOutWhenIdle() throws Exception {
        try (FakeGateway gateway = new FakeGateway(FakeGateway.LogonBehaviour.ACCEPT)) {
            NativeSession session = NativeSession.connect(gateway.host(), gateway.port(), fast(), new Recorder());
            session.logon(logon());
            next(gateway.received, NativeHeartbeat.class);
            next(gateway.received, NativeHeartbeat.class);
            assertFalse(session.isClosed(), "the gateway answers, so the session stays up");
            session.close();
        }
    }

    @Test
    void silentGatewayIsDisconnected() throws Exception {
        try (FakeGateway gateway = new FakeGateway(FakeGateway.LogonBehaviour.ACCEPT)) {
            Recorder recorder = new Recorder();
            NativeSession session = NativeSession.connect(gateway.host(), gateway.port(), fast(), recorder);
            session.logon(logon());
            gateway.goSilent();
            assertTrue(recorder.disconnected.await(3, TimeUnit.SECONDS));
            assertTrue(recorder.reason.startsWith("nothing received"), recorder.reason);
            assertTrue(session.isClosed());
        }
    }

    @Test
    void rejectedLogonReturnsTheCode() throws Exception {
        try (FakeGateway gateway = new FakeGateway(FakeGateway.LogonBehaviour.REJECT_5)) {
            Recorder recorder = new Recorder();
            NativeSession session = NativeSession.connect(gateway.host(), gateway.port(), fast(), recorder);
            NativeLogonResponse response = session.logon(logon());
            assertEquals(5, response.rejectCode);
            assertFalse(NativeSession.isAccepted(response));
            assertTrue(recorder.disconnected.await(3, TimeUnit.SECONDS), "the gateway closes after the reject");
        }
    }

    @Test
    void droppedLogonIsAnError() throws Exception {
        try (FakeGateway gateway = new FakeGateway(FakeGateway.LogonBehaviour.DROP)) {
            NativeSession session = NativeSession.connect(gateway.host(), gateway.port(), fast(), new Recorder());
            IOException e = assertThrows(IOException.class, () -> session.logon(logon()));
            assertTrue(e.getMessage().contains("without a Logon Response"), e.getMessage());
        }
    }

    @Test
    void unansweredLogonTimesOut() throws Exception {
        try (FakeGateway gateway = new FakeGateway(FakeGateway.LogonBehaviour.SILENT)) {
            NativeSession.Options options = new NativeSession.Options(Duration.ofMillis(100), Duration.ofMillis(400),
                    Duration.ofSeconds(2), Duration.ofMillis(300));
            NativeSession session = NativeSession.connect(gateway.host(), gateway.port(), options, new Recorder());
            IOException e = assertThrows(IOException.class, () -> session.logon(logon()));
            assertTrue(e.getMessage().startsWith("No Logon Response"), e.getMessage());
            assertTrue(session.isClosed());
        }
    }

    @Test
    void nothingIsSentAfterClose() throws Exception {
        try (FakeGateway gateway = new FakeGateway(FakeGateway.LogonBehaviour.ACCEPT)) {
            NativeSession session = NativeSession.connect(gateway.host(), gateway.port(), fast(), new Recorder());
            session.close();
            assertThrows(IOException.class, () -> session.send(new NativeHeartbeat()));
        }
    }
}
