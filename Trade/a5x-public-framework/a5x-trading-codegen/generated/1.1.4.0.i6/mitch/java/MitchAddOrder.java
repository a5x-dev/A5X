package br.com.a5x.trd.pub.mitch.model;

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
	public long orderId;

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

	public java.math.BigDecimal priceAsDecimal() { return java.math.BigDecimal.valueOf(price, 8); }

	/**
	 * Bit 4: Market Order (0 = No, 1 = Yes), Bit 5: Bulletin Board (0 = No, 1 = Yes)
	 */
	public byte flags;

	/**
	 * Reserved for future use.
	 */
	public String reserved1;

	/**
	 * Parses AddOrder message from ByteBuffer.
	 * 
	 * @param pAddOrder the MitchAddOrder instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchAddOrder pAddOrder, ByteBuffer pData) {
		pAddOrder.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pAddOrder.orderId = BitUtils.byteArrayToInt64(pData, false);
		pAddOrder.side = pData.get();
		pAddOrder.quantity = BitUtils.byteArrayToUInt32(pData, false);
		pAddOrder.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pAddOrder.price = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pAddOrder.flags = pData.get();
		byte[] reserved1Bytes = new byte[10];
		pData.get(reserved1Bytes);
		pAddOrder.reserved1 = new String(reserved1Bytes).trim();
	}

	/**
	 * Encodes AddOrder message to ByteBuffer.
	 * 
	 * @param pAddOrder the MitchAddOrder instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchAddOrder pAddOrder, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pAddOrder.nanosecond, pData, false);
		BitUtils.int64ToByteArray(pAddOrder.orderId, pData, false);
		pData.put(pAddOrder.side);
		BitUtils.uint32ToByteArray(pAddOrder.quantity, pData, false);
		BitUtils.uint32ToByteArray(pAddOrder.instrumentId, pData, false);
		BitUtils.signMagnitude64ToByteArray(pAddOrder.price, pData, false);
		pData.put(pAddOrder.flags);
		byte[] reserved1Bytes = new byte[10];
		java.util.Arrays.fill(reserved1Bytes, (byte) ' ');
		byte[] reserved1Src = pAddOrder.reserved1 == null ? new byte[0] : pAddOrder.reserved1.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(reserved1Src, 0, reserved1Bytes, 0, Math.min(reserved1Src.length, 10));
		pData.put(reserved1Bytes);
	}

	@Override
	public String toString() {
		return "MitchAddOrder [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", side=" + (char) side + ", quantity=" + quantity + ", instrumentId=" + instrumentId + ", price=" + price + ", flags=" + flags + ", reserved1=" + reserved1 + "]";
	}
}
