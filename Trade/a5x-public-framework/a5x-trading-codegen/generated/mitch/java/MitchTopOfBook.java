package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent to provide top of book information
 */
public class MitchTopOfBook {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Numeric Identifier of the instrument
	 */
	public long instrumentId;

	/**
	 * Bit 0: Regular (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes)
	 */
	public byte subBook;

	/**
	 * 1 = Update, 2 = Delete
	 */
	public byte action;

	/**
	 * B = Buy, S = Sell
	 */
	public byte side;

	/**
	 * Best price for the particular side
	 */
	public long price;

	public java.math.BigDecimal priceAsDecimal() { return java.math.BigDecimal.valueOf(price, 4); }

	/**
	 * Cumulative visible size at best price
	 */
	public long quantity;

	/**
	 * Cumulative visible size of market orders
	 */
	public long marketOrderQuantity;

	/**
	 * Reserved for future use
	 */
	public byte[] reservedField = new byte[2];

	/**
	 * Cumulative visible orders at Best price. This will be set to zero on a delete action
	 */
	public long splits;

	/**
	 * Parses TopOfBook message from ByteBuffer.
	 * 
	 * @param pTopOfBook the MitchTopOfBook instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchTopOfBook pTopOfBook, ByteBuffer pData) {
		pTopOfBook.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pTopOfBook.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pTopOfBook.subBook = pData.get();
		pTopOfBook.action = pData.get();
		pTopOfBook.side = pData.get();
		pTopOfBook.price = pData.getLong();
		pTopOfBook.quantity = BitUtils.byteArrayToUInt32(pData, false);
		pTopOfBook.marketOrderQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pData.get(pTopOfBook.reservedField);
		pTopOfBook.splits = BitUtils.byteArrayToUInt32(pData, false);
	}

	/**
	 * Encodes TopOfBook message to ByteBuffer.
	 * 
	 * @param pTopOfBook the MitchTopOfBook instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchTopOfBook pTopOfBook, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pTopOfBook.nanosecond, pData, false);
		BitUtils.uint32ToByteArray(pTopOfBook.instrumentId, pData, false);
		pData.put(pTopOfBook.subBook);
		pData.put(pTopOfBook.action);
		pData.put(pTopOfBook.side);
		pData.putLong(pTopOfBook.price);
		BitUtils.uint32ToByteArray(pTopOfBook.quantity, pData, false);
		BitUtils.uint32ToByteArray(pTopOfBook.marketOrderQuantity, pData, false);
		pData.put(pTopOfBook.reservedField);
		BitUtils.uint32ToByteArray(pTopOfBook.splits, pData, false);
	}

	@Override
	public String toString() {
		return "MitchTopOfBook [" + "nanosecond=" + nanosecond + ", instrumentId=" + instrumentId + ", subBook=" + subBook + ", action=" + action + ", side=" + side + ", price=" + price + ", quantity=" + quantity + ", marketOrderQuantity=" + marketOrderQuantity + ", reservedField=" + reservedField + ", splits=" + splits + "]";
	}
}
