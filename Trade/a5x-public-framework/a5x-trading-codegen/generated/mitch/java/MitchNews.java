package br.com.a5x.trd.pub.mitch.model;

import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Sent to disseminate news and announcements
 */
public class MitchNews {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Time the announcement was published (EPOCH)
	 */
	public long time;

	/**
	 * 0 = Regular, 1 = High Priority, 2 = Low Priority
	 */
	public byte urgency;

	/**
	 * Headline or subject of announcement
	 */
	public String headline;

	/**
	 * Text of the announcement
	 */
	public String text;

	/**
	 * Pipe separated list of Instrument IDs
	 */
	public String instrumentIds;

	/**
	 * Pipe separated list of Underlying Instrument IDs
	 */
	public String underlyingInstrumentIds;

	/**
	 * Parses News message from ByteBuffer.
	 * 
	 * @param pNews the MitchNews instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchNews pNews, ByteBuffer pData) {
		pNews.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		pNews.time = pData.getLong();
		pNews.urgency = pData.get();
		byte[] headlineBytes = new byte[100];
		pData.get(headlineBytes);
		pNews.headline = new String(headlineBytes).trim();
		byte[] textBytes = new byte[750];
		pData.get(textBytes);
		pNews.text = new String(textBytes).trim();
		byte[] instrumentIdsBytes = new byte[100];
		pData.get(instrumentIdsBytes);
		pNews.instrumentIds = new String(instrumentIdsBytes).trim();
		byte[] underlyingInstrumentIdsBytes = new byte[100];
		pData.get(underlyingInstrumentIdsBytes);
		pNews.underlyingInstrumentIds = new String(underlyingInstrumentIdsBytes).trim();
	}

	/**
	 * Encodes News message to ByteBuffer.
	 * 
	 * @param pNews the MitchNews instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchNews pNews, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pNews.nanosecond, pData, false);
		pData.putLong(pNews.time);
		pData.put(pNews.urgency);
		byte[] headlineBytes = new byte[100];
		java.util.Arrays.fill(headlineBytes, (byte) ' ');
		byte[] headlineSrc = pNews.headline == null ? new byte[0] : pNews.headline.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(headlineSrc, 0, headlineBytes, 0, Math.min(headlineSrc.length, 100));
		pData.put(headlineBytes);
		byte[] textBytes = new byte[750];
		java.util.Arrays.fill(textBytes, (byte) ' ');
		byte[] textSrc = pNews.text == null ? new byte[0] : pNews.text.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(textSrc, 0, textBytes, 0, Math.min(textSrc.length, 750));
		pData.put(textBytes);
		byte[] instrumentIdsBytes = new byte[100];
		java.util.Arrays.fill(instrumentIdsBytes, (byte) ' ');
		byte[] instrumentIdsSrc = pNews.instrumentIds == null ? new byte[0] : pNews.instrumentIds.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(instrumentIdsSrc, 0, instrumentIdsBytes, 0, Math.min(instrumentIdsSrc.length, 100));
		pData.put(instrumentIdsBytes);
		byte[] underlyingInstrumentIdsBytes = new byte[100];
		java.util.Arrays.fill(underlyingInstrumentIdsBytes, (byte) ' ');
		byte[] underlyingInstrumentIdsSrc = pNews.underlyingInstrumentIds == null ? new byte[0] : pNews.underlyingInstrumentIds.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		System.arraycopy(underlyingInstrumentIdsSrc, 0, underlyingInstrumentIdsBytes, 0, Math.min(underlyingInstrumentIdsSrc.length, 100));
		pData.put(underlyingInstrumentIdsBytes);
	}

	@Override
	public String toString() {
		return "MitchNews [" + "nanosecond=" + nanosecond + ", time=" + time + ", urgency=" + urgency + ", headline=" + headline + ", text=" + text + ", instrumentIds=" + instrumentIds + ", underlyingInstrumentIds=" + underlyingInstrumentIds + "]";
	}
}
