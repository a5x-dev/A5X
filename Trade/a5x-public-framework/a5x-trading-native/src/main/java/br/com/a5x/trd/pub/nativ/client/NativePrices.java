package br.com.a5x.trd.pub.nativ.client;

import java.math.BigDecimal;

/**
 * Native Price fields: signed 64-bit little-endian integers with 8 implied decimal places, in
 * two's complement (unlike MITCH prices, which are sign-magnitude). 5.25 travels as
 * 525000000.
 */
public final class NativePrices {

    public static final int DECIMALS = 8;

    private NativePrices() {
    }

    /** 5.25 to 525000000. Fails if the price has more than 8 decimal places. */
    public static long toWire(BigDecimal price) {
        return price.movePointRight(DECIMALS).longValueExact();
    }

    /** 525000000 to 5.25000000. */
    public static BigDecimal fromWire(long value) {
        return BigDecimal.valueOf(value, DECIMALS);
    }
}
