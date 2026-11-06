package br.com.a5x.trd.pub.nativ.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.a5x.trd.pub.nativ.NativeEventWrapper;
import br.com.a5x.trd.pub.nativ.NativeEventWrapper.INativePackageHandler;
import br.com.a5x.trd.pub.nativ.model.NativeHeartbeat;
import br.com.a5x.trd.pub.nativ.model.NativeLogon;
import br.com.a5x.trd.pub.nativ.model.NativeLogonResponse;
import br.com.a5x.trd.pub.nativ.model.NativeLogout;

/**
 * A client session with the A5X Native Gateway, following the connection rules of the
 * Technical Portal (Order Entry Binary page, "Establishing a Connection"):
 *
 * <ol>
 * <li><b>Logon.</b> The client sends {@code Logon} and must wait for {@code Logon Response}
 * before sending anything else. Reject Code 0 is a successful logon; 3 is also a successful
 * logon whose new password was refused by the password policy. On an invalid CompID,
 * password, gateway or privilege the gateway closes the connection without a response.</li>
 * <li><b>Heartbeat.</b> Both sides send {@code Heartbeat} at the heartbeat interval. The
 * gateway disconnects a client that is silent for longer than the interval plus a reasonable
 * transmission time, and the client is expected to do the same: this session sends a
 * Heartbeat whenever nothing else was sent for one interval, and disconnects when nothing
 * was received for {@link Options#inactivityTimeout()}.</li>
 * <li><b>Logout.</b> The client sends {@code Logout}; the gateway answers with {@code Logout}
 * and closes the connection.</li>
 * </ol>
 *
 * <p>Every message received, session messages included, goes to {@link Listener#onMessage}
 * on the socket reader thread. Decoding here goes through a dynamic proxy to keep the example
 * short; a latency-sensitive client implements {@link INativePackageHandler} directly.
 *
 * <p>The {@code Logon} message carries the password: never log it (its {@code toString}
 * prints every field).
 */
public final class NativeSession implements AutoCloseable {

    /** Receives what the gateway sends. Called on the socket reader thread. */
    public interface Listener {
        /** Every message received, decoded into its model class (NativeExecutionReport, ...). */
        void onMessage(Object message);

        /** The connection is gone: closed by either side, timed out or out of sync. */
        default void onDisconnected(String reason, Throwable cause) {
        }
    }

    /**
     * @param heartbeatInterval the interval A5X configures for the environment
     * @param inactivityTimeout silence from the gateway after which the session gives up
     * @param connectTimeout    TCP connect timeout
     * @param logonTimeout      how long to wait for the Logon Response
     */
    public record Options(Duration heartbeatInterval, Duration inactivityTimeout, Duration connectTimeout,
            Duration logonTimeout) {

        /** Inactivity timeout of two intervals: one interval plus a generous transmission time. */
        public static Options withHeartbeat(Duration heartbeatInterval) {
            return new Options(heartbeatInterval, heartbeatInterval.multipliedBy(2), Duration.ofSeconds(5),
                    Duration.ofSeconds(10));
        }
    }

    private final Socket socket;
    private final OutputStream out;
    private final Options options;
    private final Listener listener;
    private final NativeStreamDecoder decoder;
    private final ByteBuffer sendBuffer = ByteBuffer.allocate(NativeStreamDecoder.MAX_FRAME_SIZE).order(ByteOrder.LITTLE_ENDIAN);
    private final ScheduledExecutorService timer;
    private final Thread reader;
    private final AtomicBoolean closed = new AtomicBoolean();
    private final CompletableFuture<NativeLogonResponse> logonResponse = new CompletableFuture<>();
    private final CompletableFuture<NativeLogout> logoutReceived = new CompletableFuture<>();
    private volatile long lastSentNanos = System.nanoTime();
    private volatile long lastReceivedNanos = System.nanoTime();

    private NativeSession(Socket socket, Options options, Listener listener) throws IOException {
        this.socket = socket;
        this.out = socket.getOutputStream();
        this.options = options;
        this.listener = listener;
        INativePackageHandler handler = (INativePackageHandler) Proxy.newProxyInstance(
                INativePackageHandler.class.getClassLoader(), new Class<?>[] { INativePackageHandler.class },
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() == Object.class) {
                        return switch (method.getName()) {
                            case "hashCode" -> System.identityHashCode(proxy);
                            case "equals" -> proxy == args[0];
                            default -> "NativeSession handler";
                        };
                    }
                    onMessage(args[0]);
                    return null;
                });
        this.decoder = new NativeStreamDecoder(handler);
        this.timer = Executors.newSingleThreadScheduledExecutor(r -> daemon(r, "native-session-timer"));
        this.reader = daemon(this::readLoop, "native-session-reader");
    }

    /** Opens the TCP connection. Nothing is sent until {@link #logon}. */
    public static NativeSession connect(String host, int port, Options options, Listener listener) throws IOException {
        Socket socket = new Socket();
        try {
            socket.setTcpNoDelay(true); // order entry: send each message now, do not batch
            socket.connect(new InetSocketAddress(host, port), (int) options.connectTimeout().toMillis());
            NativeSession session = new NativeSession(socket, options, listener);
            session.reader.start();
            return session;
        } catch (IOException | RuntimeException e) {
            socket.close();
            throw e;
        }
    }

    /**
     * Sends the Logon and waits for the Logon Response. Heartbeats start once the logon is
     * accepted (Reject Code 0 or 3).
     *
     * @throws IOException when the gateway closes the connection without answering (invalid
     *                     CompID, password, gateway or privilege) or does not answer in time
     */
    public NativeLogonResponse logon(NativeLogon logon) throws IOException, InterruptedException {
        send(logon);
        NativeLogonResponse response;
        try {
            response = logonResponse.get(options.logonTimeout().toMillis(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            close();
            throw new IOException("No Logon Response within " + options.logonTimeout());
        } catch (ExecutionException e) {
            throw new IOException("The gateway closed the connection without a Logon Response: check the CompID,"
                    + " the password, the gateway address and the CompID privileges", e.getCause());
        }
        if (isAccepted(response)) {
            long period = Math.max(1, options.heartbeatInterval().toMillis() / 4);
            timer.scheduleAtFixedRate(this::onTimer, period, period, TimeUnit.MILLISECONDS);
        }
        return response;
    }

    /** Reject Code 0, or 3 (logged on, but the new password was refused). */
    public static boolean isAccepted(NativeLogonResponse response) {
        return response.rejectCode == 0 || response.rejectCode == 3;
    }

    /** Frames and sends one message (any model class). Safe to call from any thread. */
    public synchronized void send(Object message) throws IOException {
        if (closed.get()) {
            throw new IOException("Session closed");
        }
        sendBuffer.clear();
        NativeEventWrapper.encode(sendBuffer, message);
        out.write(sendBuffer.array(), 0, sendBuffer.position());
        out.flush();
        lastSentNanos = System.nanoTime();
    }

    /**
     * Sends Logout, waits for the gateway's Logout (or for it to close the connection) and
     * closes the session.
     */
    public void logout(Duration wait) throws IOException, InterruptedException {
        if (!closed.get()) {
            send(new NativeLogout());
            try {
                logoutReceived.get(wait.toMillis(), TimeUnit.MILLISECONDS);
            } catch (ExecutionException | TimeoutException e) {
                // closed by the gateway, or no answer in time: closing anyway
            }
        }
        close();
    }

    public boolean isClosed() {
        return closed.get();
    }

    /** Bytes received that the generated models did not decode (see {@link NativeStreamDecoder}). */
    public long undecodedBytes() {
        return decoder.undecodedBytes();
    }

    @Override
    public void close() {
        disconnect("closed by the client", null);
    }

    // ---------------------------------------------------------------------------------

    private void onMessage(Object message) {
        if (message instanceof NativeLogonResponse response) {
            logonResponse.complete(response);
        } else if (message instanceof NativeLogout logout) {
            logoutReceived.complete(logout);
        }
        listener.onMessage(message);
    }

    private void readLoop() {
        byte[] buffer = new byte[64 * 1024];
        try (InputStream in = socket.getInputStream()) {
            int n;
            while ((n = in.read(buffer)) > 0) {
                lastReceivedNanos = System.nanoTime();
                decoder.feed(buffer, 0, n);
            }
            disconnect("connection closed by the gateway", null);
        } catch (NativeStreamDecoder.NativeProtocolException e) {
            disconnect("invalid data from the gateway: " + e.getMessage(), e);
        } catch (IOException e) {
            disconnect(closed.get() ? "closed by the client" : "connection lost: " + e.getMessage(), e);
        }
    }

    private void onTimer() {
        long now = System.nanoTime();
        if (now - lastReceivedNanos > options.inactivityTimeout().toNanos()) {
            disconnect("nothing received from the gateway for " + options.inactivityTimeout(), null);
            return;
        }
        if (now - lastSentNanos >= options.heartbeatInterval().toNanos()) {
            try {
                send(new NativeHeartbeat());
            } catch (IOException e) {
                disconnect("could not send Heartbeat: " + e.getMessage(), e);
            }
        }
    }

    private void disconnect(String reason, Throwable cause) {
        if (!closed.compareAndSet(false, true)) {
            return;
        }
        timer.shutdownNow();
        try {
            socket.close();
        } catch (IOException ignored) {
            // already gone
        }
        IOException gone = new IOException(reason, cause);
        logonResponse.completeExceptionally(gone);
        logoutReceived.completeExceptionally(gone);
        listener.onDisconnected(reason, cause);
    }

    private static Thread daemon(Runnable task, String name) {
        Thread thread = new Thread(task, name);
        thread.setDaemon(true);
        return thread;
    }
}
