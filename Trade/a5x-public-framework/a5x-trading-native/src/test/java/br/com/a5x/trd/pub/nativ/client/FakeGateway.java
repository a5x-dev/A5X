package br.com.a5x.trd.pub.nativ.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import br.com.a5x.trd.pub.nativ.NativeEventWrapper;
import br.com.a5x.trd.pub.nativ.NativeEventWrapper.INativePackageHandler;
import br.com.a5x.trd.pub.nativ.model.NativeExecutionReport;
import br.com.a5x.trd.pub.nativ.model.NativeLogon;
import br.com.a5x.trd.pub.nativ.model.NativeLogonResponse;
import br.com.a5x.trd.pub.nativ.model.NativeLogout;
import br.com.a5x.trd.pub.nativ.model.NativeNewOrderSingle;

/**
 * A stand-in for the gateway on the loopback interface, for the session tests: answers Logon
 * with the configured Reject Code (or by dropping the connection), New Order Single with an
 * Execution Report, and Logout with Logout. It records everything it receives.
 */
final class FakeGateway implements AutoCloseable {

    enum LogonBehaviour { ACCEPT, REJECT_5, DROP, SILENT }

    final BlockingQueue<Object> received = new LinkedBlockingQueue<>();
    private final ServerSocket server;
    private final LogonBehaviour behaviour;
    private volatile Socket client;
    private volatile boolean answerHeartbeats = true;

    FakeGateway(LogonBehaviour behaviour) throws IOException {
        this.behaviour = behaviour;
        this.server = new ServerSocket(0, 1, InetAddress.getLoopbackAddress());
        Thread t = new Thread(this::serve, "fake-gateway");
        t.setDaemon(true);
        t.start();
    }

    String host() {
        return server.getInetAddress().getHostAddress();
    }

    int port() {
        return server.getLocalPort();
    }

    /** Stops sending anything, to exercise the client's inactivity timeout. */
    void goSilent() {
        answerHeartbeats = false;
    }

    private void serve() {
        try (Socket s = server.accept(); InputStream in = s.getInputStream(); OutputStream out = s.getOutputStream()) {
            client = s;
            INativePackageHandler handler = (INativePackageHandler) Proxy.newProxyInstance(getClass().getClassLoader(),
                    new Class<?>[] { INativePackageHandler.class }, (p, m, a) -> {
                        onMessage(a[0], out);
                        return null;
                    });
            NativeStreamDecoder decoder = new NativeStreamDecoder(handler);
            byte[] buffer = new byte[4096];
            int n;
            while ((n = in.read(buffer)) > 0) {
                decoder.feed(buffer, 0, n);
            }
        } catch (IOException | RuntimeException e) {
            // connection closed: the test decides what that means
        }
    }

    private void onMessage(Object message, OutputStream out) throws IOException {
        received.add(message);
        if (message instanceof NativeLogon) {
            switch (behaviour) {
            case ACCEPT -> send(out, logonResponse(0));
            case REJECT_5 -> {
                send(out, logonResponse(5));
                client.close();
            }
            case DROP -> client.close();
            case SILENT -> { }
            }
        } else if (message instanceof NativeNewOrderSingle order) {
            NativeExecutionReport report = new NativeExecutionReport();
            report.clientOrderId = order.clientOrderId;
            report.instrumentId = order.instrumentId;
            report.leavesQuantity = order.orderQuantity;
            report.executionType = "0";
            send(out, report);
        } else if (message instanceof NativeLogout) {
            send(out, new NativeLogout());
            client.close();
        } else if (answerHeartbeats) {
            send(out, new br.com.a5x.trd.pub.nativ.model.NativeHeartbeat());
        }
    }

    private static NativeLogonResponse logonResponse(int code) {
        NativeLogonResponse r = new NativeLogonResponse();
        r.rejectCode = code;
        r.passwordExpiry = 30;
        return r;
    }

    private static synchronized void send(OutputStream out, Object message) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(NativeStreamDecoder.MAX_FRAME_SIZE).order(ByteOrder.LITTLE_ENDIAN);
        NativeEventWrapper.encode(buffer, message);
        out.write(buffer.array(), 0, buffer.position());
        out.flush();
    }

    @Override
    public void close() throws IOException {
        server.close();
        Socket c = client;
        if (c != null) {
            c.close();
        }
    }
}
