package br.com.a5x.trd.pub.nativ.client;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import br.com.a5x.trd.pub.nativ.model.NativeHeartbeat;
import br.com.a5x.trd.pub.nativ.model.NativeLogon;
import br.com.a5x.trd.pub.nativ.model.NativeLogonResponse;
import br.com.a5x.trd.pub.nativ.model.NativeNewOrderSingle;

/**
 * Connects to the Native Gateway, logs on, optionally sends one limit order, prints what the
 * gateway sends and logs out.
 *
 * <pre>
 * A5X_NATIVE_PASSWORD=... java ... NativeClientExample --host &lt;gateway&gt; --port &lt;port&gt; --comp-id &lt;CompID&gt;
 *     [--heartbeat-seconds 3] [--wait-seconds 5]
 *     [--order --instrument &lt;id&gt; --side buy|sell --quantity &lt;n&gt; --price &lt;decimal&gt; --account &lt;n&gt;
 *      --client-order-id &lt;id&gt; [--entering-trader &lt;id&gt;] [--sender-location &lt;id&gt;]]
 * </pre>
 *
 * <p>The gateway address, port and CompID are the ones A5X assigns to you; the Technical
 * Portal lists the addresses of each environment. The password is read from the
 * {@code A5X_NATIVE_PASSWORD} environment variable (and the optional new password from
 * {@code A5X_NATIVE_NEW_PASSWORD}) so it never shows up in the command line or the process
 * list.
 */
public final class NativeClientExample {

    private NativeClientExample() {
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> opt = parse(args);
        String host = required(opt, "host");
        int port = Integer.parseInt(required(opt, "port"));
        String compId = required(opt, "comp-id");
        String password = System.getenv("A5X_NATIVE_PASSWORD");
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Set the password in the A5X_NATIVE_PASSWORD environment variable");
        }
        Duration heartbeat = Duration.ofSeconds(Long.parseLong(opt.getOrDefault("heartbeat-seconds", "3")));
        Duration wait = Duration.ofSeconds(Long.parseLong(opt.getOrDefault("wait-seconds", "5")));

        NativeSession.Listener printer = new NativeSession.Listener() {
            @Override
            public void onMessage(Object message) {
                if (!(message instanceof NativeHeartbeat)) {
                    System.out.println("<- " + message);
                }
            }

            @Override
            public void onDisconnected(String reason, Throwable cause) {
                System.out.println("-- disconnected: " + reason);
            }
        };

        try (NativeSession session = NativeSession.connect(host, port, NativeSession.Options.withHeartbeat(heartbeat), printer)) {
            NativeLogon logon = new NativeLogon();
            logon.compId = compId;
            logon.password = password;
            logon.newPassword = System.getenv().getOrDefault("A5X_NATIVE_NEW_PASSWORD", "");
            logon.protocolVersion = Integer.parseInt(opt.getOrDefault("protocol-version", "0")); // 0: gateway default
            logon.ipAddress = opt.getOrDefault("ip-address", "");
            logon.certifiedSolution = opt.getOrDefault("certified-solution", "");
            System.out.println("-> Logon " + compId); // never print the Logon itself: it carries the password

            NativeLogonResponse response = session.logon(logon);
            if (!NativeSession.isAccepted(response)) {
                System.out.println("Logon refused, Reject Code " + response.rejectCode
                        + " (see the Reject Code page of the Technical Portal)");
                return;
            }
            if (response.rejectCode == 3) {
                System.out.println("Logged on, but the new password was refused by the password policy");
            }

            if (opt.containsKey("order")) {
                NativeNewOrderSingle order = limitOrder(opt);
                System.out.println("-> " + order);
                session.send(order);
            }

            Thread.sleep(wait.toMillis()); // execution reports arrive on the reader thread
            session.logout(Duration.ofSeconds(5));
        }
    }

    /** A Day limit order. Every value comes from the command line: nothing here is real. */
    static NativeNewOrderSingle limitOrder(Map<String, String> opt) {
        NativeNewOrderSingle order = new NativeNewOrderSingle();
        order.instrumentId = Long.parseLong(required(opt, "instrument"));
        order.side = switch (required(opt, "side").toLowerCase()) {
            case "buy", "1" -> 1;
            case "sell", "2" -> 2;
            default -> throw new IllegalArgumentException("--side is buy or sell");
        };
        order.orderQuantity = Long.parseLong(required(opt, "quantity"));
        order.limitPrice = NativePrices.toWire(new BigDecimal(required(opt, "price")));
        order.account = Long.parseUnsignedLong(required(opt, "account"));
        order.clientOrderId = required(opt, "client-order-id");
        order.enteringTrader = opt.getOrDefault("entering-trader", "");
        order.senderLocation = opt.getOrDefault("sender-location", "");
        order.orderType = 2;          // Limit
        order.timeInForce = 0;        // Day
        order.orderBook = 1;          // Regular
        order.cancelOnDisconnect = 1; // cancel if this session drops (applies when the CompID has the feature)
        // Max Floor: the specification asks for a negative value to ignore the field; the
        // field is UInt32, so -1 travels as 0xFFFFFFFF. Confirm with A5X for your use case.
        order.displayQuantity = 0xFFFF_FFFFL;
        return order;
    }

    private static Map<String, String> parse(String[] args) {
        Map<String, String> opt = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (!args[i].startsWith("--")) {
                throw new IllegalArgumentException("Unexpected argument: " + args[i]);
            }
            String key = args[i].substring(2);
            boolean flag = i + 1 >= args.length || args[i + 1].startsWith("--");
            opt.put(key, flag ? "true" : args[++i]);
        }
        return opt;
    }

    private static String required(Map<String, String> opt, String key) {
        String value = opt.get(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Missing --" + key);
        }
        return value;
    }
}
