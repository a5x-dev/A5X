package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent to provide indicative auction information during auction periods
 */
public class MitchAuctionInfo {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Quantity that will be matched at the indicative price
	 */
	public long pairedQuantity;

	/**
	 * Quantity that is eligible to be matched at the indicative price but will not be matched
	 */
	public long imbalanceQuantity;

	/**
	 * B = Buy Imbalance, N = No Imbalance, O = Insufficient Orders for Auction, S = Sell Imbalance
	 */
	public byte imbalanceDirection;

	/**
	 * Numeric identifier of the instrument
	 */
	public long instrumentId;

	/**
	 * Indicative auction price
	 */
	public long price;

	public java.math.BigDecimal priceAsDecimal() { return java.math.BigDecimal.valueOf(price, 4); }

	/**
	 * A = Re-Opening Auction, C = Closing Auction, O = Opening Auction
	 */
	public byte auctionType;

	/**
	 * Parses AuctionInfo message from ByteBuffer.
	 * 
	 * @param pAuctionInfo the MitchAuctionInfo instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchAuctionInfo pAuctionInfo, ByteBuffer pData) {
		pAuctionInfo.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pAuctionInfo.pairedQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pAuctionInfo.imbalanceQuantity = BitUtils.byteArrayToUInt32(pData, false);
		pAuctionInfo.imbalanceDirection = pData.get();
		pAuctionInfo.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pAuctionInfo.price = pData.getLong();
		pAuctionInfo.auctionType = pData.get();
	}

	/**
	 * Encodes AuctionInfo message to ByteBuffer.
	 * 
	 * @param pAuctionInfo the MitchAuctionInfo instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchAuctionInfo pAuctionInfo, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pAuctionInfo.nanosecond, pData, false);
		BitUtils.uint32ToByteArray(pAuctionInfo.pairedQuantity, pData, false);
		BitUtils.uint32ToByteArray(pAuctionInfo.imbalanceQuantity, pData, false);
		pData.put(pAuctionInfo.imbalanceDirection);
		BitUtils.uint32ToByteArray(pAuctionInfo.instrumentId, pData, false);
		pData.putLong(pAuctionInfo.price);
		pData.put(pAuctionInfo.auctionType);
	}

	@Override
	public String toString() {
		return "MitchAuctionInfo [" + "nanosecond=" + nanosecond + ", pairedQuantity=" + pairedQuantity + ", imbalanceQuantity=" + imbalanceQuantity + ", imbalanceDirection=" + imbalanceDirection + ", instrumentId=" + instrumentId + ", price=" + price + ", auctionType=" + auctionType + "]";
	}
}
