package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent when a visible order is modified in the order book
 */
public class MitchOrderModified {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Unique identifier of the order
	 */
	public BigInteger orderId;

	/**
	 * New displayed quantity of the order
	 */
	public long newQuantity;

	/**
	 * New limit price of the order
	 */
	public long newPrice;

	public java.math.BigDecimal newPriceAsDecimal() { return java.math.BigDecimal.valueOf(newPrice, 4); }

	/**
	 * Displayed quantity of the order
	 */
	public long oldQuantity;

	/**
	 * Limit price of the order
	 */
	public long oldPrice;

	public java.math.BigDecimal oldPriceAsDecimal() { return java.math.BigDecimal.valueOf(oldPrice, 4); }

	/**
	 * Bit 0: Priority Flag (0 = Priority Lost, 1 = Priority Retained)
	 */
	public byte flags;

	/**
	 * Parses OrderModified message from ByteBuffer.
	 * 
	 * @param pOrderModified the MitchOrderModified instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchOrderModified pOrderModified, ByteBuffer pData) {
		pOrderModified.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pOrderModified.orderId = BitUtils.byteArrayToUInt64(pData, false);
		pOrderModified.newQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pOrderModified.newPrice = pData.getLong();
		pOrderModified.oldQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pOrderModified.oldPrice = pData.getLong();
		pOrderModified.flags = pData.get();
	}

	/**
	 * Encodes OrderModified message to ByteBuffer.
	 * 
	 * @param pOrderModified the MitchOrderModified instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchOrderModified pOrderModified, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pOrderModified.nanosecond, pData, false);
		BitUtils.uint64ToByteArray(pOrderModified.orderId, pData, false);
		BitUtils.uint32ToByteArray(pOrderModified.newQuantity, pData, false);
		pData.putLong(pOrderModified.newPrice);
		BitUtils.uint32ToByteArray(pOrderModified.oldQuantity, pData, false);
		pData.putLong(pOrderModified.oldPrice);
		pData.put(pOrderModified.flags);
	}

	@Override
	public String toString() {
		return "MitchOrderModified [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", newQuantity=" + newQuantity + ", newPrice=" + newPrice + ", oldQuantity=" + oldQuantity + ", oldPrice=" + oldPrice + ", flags=" + flags + "]";
	}
}
