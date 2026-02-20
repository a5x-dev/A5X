package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent when a new visible order is added to the order book
 */
public class MitchAddOrder {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Unique identifier of the order
	 */
	public BigInteger orderId;

	/**
	 * B = Buy, S = Sell
	 */
	public byte side;

	/**
	 * Displayed quantity of the order
	 */
	public long quantity;

	/**
	 * Numeric identifier of the instrument
	 */
	public long instrumentId;

	/**
	 * Limit price of the order. If Market, it will be filled with spaces
	 */
	public long price;

	public java.math.BigDecimal priceAsDecimal() { return java.math.BigDecimal.valueOf(price, 4); }

	/**
	 * Bit 4: Market Order (0 = No, 1 = Yes), Bit 5: Bulletin Board (0 = No, 1 = Yes)
	 */
	public byte flags;

	/**
	 * Parses AddOrder message from ByteBuffer.
	 * 
	 * @param pAddOrder the MitchAddOrder instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchAddOrder pAddOrder, ByteBuffer pData) {
		pAddOrder.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pAddOrder.orderId = BitUtils.byteArrayToUInt64(pData, false);
		pAddOrder.side = pData.get();
		pAddOrder.quantity = BitUtils.byteArrayToUInt32(pData, false);
		pAddOrder.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pAddOrder.price = pData.getLong();
		pAddOrder.flags = pData.get();
	}

	/**
	 * Encodes AddOrder message to ByteBuffer.
	 * 
	 * @param pAddOrder the MitchAddOrder instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchAddOrder pAddOrder, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pAddOrder.nanosecond, pData, false);
		BitUtils.uint64ToByteArray(pAddOrder.orderId, pData, false);
		pData.put(pAddOrder.side);
		BitUtils.uint32ToByteArray(pAddOrder.quantity, pData, false);
		BitUtils.uint32ToByteArray(pAddOrder.instrumentId, pData, false);
		pData.putLong(pAddOrder.price);
		pData.put(pAddOrder.flags);
	}

	@Override
	public String toString() {
		return "MitchAddOrder [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", side=" + side + ", quantity=" + quantity + ", instrumentId=" + instrumentId + ", price=" + price + ", flags=" + flags + "]";
	}
}
