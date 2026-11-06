package br.com.a5x.trd.pub.mitch.model;

import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent when a visible order is deleted from the order book
 */
public class MitchOrderDeleted {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Unique identifier of the order
	 */
	public long orderId;

	/**
	 * Displayed quantity of the order
	 */
	public long oldQuantity;

	/**
	 * Limit price of the order
	 */
	public long oldPrice;

	public java.math.BigDecimal oldPriceAsDecimal() { return java.math.BigDecimal.valueOf(oldPrice, 8); }

	/**
	 * Numeric identifier of the instrument
	 */
	public long instrumentId;

	/**
	 * Side of the order
	 */
	public byte side;

	/**
	 * Parses OrderDeleted message from ByteBuffer.
	 * 
	 * @param pOrderDeleted the MitchOrderDeleted instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchOrderDeleted pOrderDeleted, ByteBuffer pData) {
		pOrderDeleted.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pOrderDeleted.orderId = BitUtils.byteArrayToInt64(pData, false);
		pOrderDeleted.oldQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pOrderDeleted.oldPrice = BitUtils.byteArrayToSignMagnitude64(pData, false);
		pOrderDeleted.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pOrderDeleted.side = pData.get();
	}

	/**
	 * Encodes OrderDeleted message to ByteBuffer.
	 * 
	 * @param pOrderDeleted the MitchOrderDeleted instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchOrderDeleted pOrderDeleted, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pOrderDeleted.nanosecond, pData, false);
		BitUtils.int64ToByteArray(pOrderDeleted.orderId, pData, false);
		BitUtils.uint32ToByteArray(pOrderDeleted.oldQuantity, pData, false);
		BitUtils.signMagnitude64ToByteArray(pOrderDeleted.oldPrice, pData, false);
		BitUtils.uint32ToByteArray(pOrderDeleted.instrumentId, pData, false);
		pData.put(pOrderDeleted.side);
	}

	@Override
	public String toString() {
		return "MitchOrderDeleted [" + "nanosecond=" + nanosecond + ", orderId=" + orderId + ", oldQuantity=" + oldQuantity + ", oldPrice=" + oldPrice + ", instrumentId=" + instrumentId + ", side=" + (char) side + "]";
	}
}
