package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent when a new visible order with firm attribution is added to the order book
 */
public class MitchAddAttributedOrder {
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
	 * Identity of firm that submitted the order
	 */
	public String firm;

	/**
	 * Bit 4: Market Order (0 = No, 1 = Yes), Bit 5: Bulletin Board (0 = No, 1 = Yes)
	 */
	public byte flags;

	/**
	 * Parses AddAttributedOrder message from ByteBuffer.
	 * 
	 * @param pAddAttributedOrder the MitchAddAttributedOrder instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchAddAttributedOrder pAddAttributedOrder, ByteBuffer pData) {
		pAddAttributedOrder.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pAddAttributedOrder.orderId = BitUtils.byteArrayToUInt64(pData, false);
		pAddAttributedOrder.side = pData.get();
		pAddAttributedOrder.quantity = BitUtils.byteArrayToUInt32(pData, false);
		pAddAttributedOrder.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pAddAttributedOrder.price = pData.getLong();
		byte[] firmBytes = new byte[6];
		pData.get(firmBytes);
		pAddAttributedOrder.firm = new String(firmBytes).trim();
		pAddAttributedOrder.flags = pData.get();
	}

	/**
	 * Encodes AddAttributedOrder message to ByteBuffer.
	 * 
	 * @param pAddAttributedOrder the MitchAddAttributedOrder instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchAddAttributedOrder pAddAttributedOrder, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pAddAttributedOrder.nanosecond, pData, false);
		BitUtils.uint64ToByteArray(pAddAttributedOrder.orderId, pData, false);
		pData.put(pAddAttributedOrder.side);
		BitUtils.uint32ToByteArray(pAddAttributedOrder.quantity, pData, false);
		BitUtils.uint32ToByteArray(pAddAttributedOrder.instrumentId, pData, false);
		pData.putLong(pAddAttributedOrder.price);
		byte[] firmBytes = new byte[6];
		byte[] firmSrc = pAddAttributedOrder.firm.getBytes();
		System.arraycopy(firmSrc, 0, firmBytes, 0, Math.min(firmSrc.length, 6));
		pData.put(firmBytes);
		pData.put(pAddAttributedOrder.flags);
	}

	@Override
	public String toString() {
		return "MitchAddAttributedOrder [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", side=" + side + ", quantity=" + quantity + ", instrumentId=" + instrumentId + ", price=" + price + ", firm=" + firm + ", flags=" + flags + "]";
	}
}
