package br.com.a5x.trd.pub.nativ.client;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HexFormat;

import br.com.a5x.trd.pub.nativ.NativeEventWrapper;
import br.com.a5x.trd.pub.nativ.model.NativeExecutionReport;
import br.com.a5x.trd.pub.nativ.model.NativeNewOrderSingle;

/**
 * Encodes a New Order Single and an Execution Report the way they travel on the wire, then
 * decodes them back from a byte stream cut in arbitrary places, as TCP delivers it. No
 * network, no credentials: a way to see the framing and the field layout.
 *
 * <p>Every identifier and price below is made up for the example.
 */
public final class NativeOfflineExample {

    private NativeOfflineExample() {
    }

    public static void main(String[] args) {
        NativeNewOrderSingle order = new NativeNewOrderSingle();
        order.instrumentId = 1234;
        order.side = 1;                                        // Buy
        order.orderQuantity = 10;
        order.limitPrice = NativePrices.toWire(new BigDecimal("5.25"));
        order.account = 1001;
        order.clientOrderId = "EX0001";
        order.orderType = 2;                                   // Limit
        order.timeInForce = 0;                                 // Day
        order.orderBook = 1;                                   // Regular

        NativeExecutionReport report = new NativeExecutionReport();
        report.partitionId = 1;
        report.sequenceNumber = 1;
        report.executionId = "EXEC0001";
        report.clientOrderId = "EX0001";
        report.orderId = "ORD0001";
        report.instrumentId = 1234;
        report.side = 1;
        report.leavesQuantity = 10;
        report.executionType = "0";                            // New
        report.orderStatus = 0;                                // New

        ByteBuffer wire = ByteBuffer.allocate(4096).order(ByteOrder.LITTLE_ENDIAN);
        NativeEventWrapper.encode(wire, order);
        int orderSize = wire.position();
        NativeEventWrapper.encode(wire, report);
        byte[] bytes = new byte[wire.position()];
        wire.flip().get(bytes);

        HexFormat hex = HexFormat.ofDelimiter(" ");
        System.out.printf("New Order Single, %d bytes: Start of Message, Length (from Message Type on), 'D', body%n  %s%n",
                orderSize, hex.formatHex(bytes, 0, orderSize));
        System.out.printf("Execution Report, %d bytes%n  %s%n%n", bytes.length - orderSize,
                hex.formatHex(bytes, orderSize, bytes.length));

        // TCP does not keep message boundaries: feed the two messages in three uneven pieces.
        NativeStreamDecoder decoder = new NativeStreamDecoder(new PrintingHandler());
        int[] cuts = { 0, 5, orderSize + 7, bytes.length };
        for (int i = 0; i + 1 < cuts.length; i++) {
            int n = decoder.feed(bytes, cuts[i], cuts[i + 1] - cuts[i]);
            System.out.printf("read of %d bytes -> %d message(s)%n", cuts[i + 1] - cuts[i], n);
        }
        System.out.println("limit price on the wire " + order.limitPrice + " = " + NativePrices.fromWire(order.limitPrice));
    }

    /** Prints each decoded message. */
    private static final class PrintingHandler extends NativeHandlerAdapter {
        @Override
        public void onNativeNewOrderSingle(NativeNewOrderSingle msg) {
            System.out.println("  decoded " + msg);
        }

        @Override
        public void onNativeExecutionReport(NativeExecutionReport msg) {
            System.out.println("  decoded " + msg);
        }
    }
}
