package br.com.a5x.trd.pub.mitch.model;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import br.com.a5x.trd.pub.fw.utils.BitUtils;

/**
 * Used to disseminate information on each instrument
 */
public class MitchSymbolDirectory {
	/**
	 * Nanoseconds offset from the last Time message
	 */
	public long nanosecond;

	/**
	 * Instrument's symbol
	 */
	public String symbol;

	/**
	 * Numeric Identifier of the instrument
	 */
	public long instrumentId;

	/**
	 * Space = Active, H = Halted, S = Suspended, a = Inactive
	 */
	public String symbolStatus;

	/**
	 * Instrument identification number (e.g. ISIN, CUSIP, etc.)
	 */
	public String identificationNumber;

	/**
	 * Segment the instrument is assigned to
	 */
	public String segment;

	/**
	 * Date an instrument expires or matures. This field will contain only spaces if the instrument is not a derivative or fixed income instrument
	 */
	public String expirationDate;

	/**
	 * Symbol of the underlying instrument. This field will contain only spaces if the instrument is not a derivative
	 */
	public String underlying;

	/**
	 * Numeric Identifier of the underlying instrument
	 */
	public long underlyingInstrumentId;

	/**
	 * Strike price of an option. The price will be zero if the instrument is not an option
	 */
	public long strikePrice;

	public java.math.BigDecimal strikePriceAsDecimal() { return java.math.BigDecimal.valueOf(strikePrice, 4); }

	/**
	 * Space = Not option, C = Call Option, P = Put Option
	 */
	public String optionType;

	/**
	 * Issuer of the instrument. This field will contain all spaces if the instrument is not a fixed income instrument
	 */
	public String issuer;

	/**
	 * Date instrument was issued. This field will contain all spaces if the instrument is not a fixed income instrument
	 */
	public String issueDate;

	/**
	 * Rate of interest applied to the face value. This is a percentage field (e.g. 0.05 represents 5%)
	 */
	public int coupon;

	public java.math.BigDecimal couponAsDecimal() { return java.math.BigDecimal.valueOf(coupon, 4); }

	/**
	 * Bit 0: Inverse Order Book (0 = No, 1 = Yes)
	 */
	public byte flags;

	/**
	 * Bit 0: Regular (0=No, 1=Yes), Bit 1: Off-Book (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes), Bit 6: Negotiated Trades (0=No, 1=Yes)
	 */
	public byte subBook;

	/**
	 * Pipe separated field. Identifies the type of Corporate Actions applicable on the instrument for the current Trading day
	 */
	public String corporateAction;

	/**
	 * Identity of the partition
	 */
	public String partitionId;

	/**
	 * Space = Not option, A = American, E = European
	 */
	public String exerciseStyle;

	/**
	 * Symbol of Leg 1 instrument. This field will contain only spaces if the instrument is not a multi-legged instrument
	 */
	public String leg1Symbol;

	/**
	 * Numeric Identifier of the leg 1 instrument
	 */
	public long leg1InstrumentId;

	/**
	 * Symbol of Leg 2 instrument. This field will contain only spaces if the instrument is not a multi-legged instrument
	 */
	public String leg2Symbol;

	/**
	 * Numeric Identifier of the leg 2 instrument
	 */
	public long leg2InstrumentId;

	/**
	 * Defines the multiplier of the instrument. This field will contain 0 value if the instrument does not have a contract multiplier
	 */
	public int contractMultiplier;

	public java.math.BigDecimal contractMultiplierAsDecimal() { return java.math.BigDecimal.valueOf(contractMultiplier, 4); }

	/**
	 * Space = No method, C = Cash, P = Physical
	 */
	public String settlementMethod;

	/**
	 * 0 = Not test, 1 = Exchange only, 2 = End to end test
	 */
	public int testInstrument;

	/**
	 * Trading venue where the symbol was originated
	 */
	public String venueOfExecution;

	/**
	 * Lot Size of the Instrument
	 */
	public long lotSize;

	/**
	 * Description of the instrument
	 */
	public String securityDescription;

	/**
	 * P = Pre-Listed, U = User Defined
	 */
	public byte listMethod;

	/**
	 * Trading currency of the instrument
	 */
	public String currency;

	/**
	 * Instrument Tick Size (e.g. 0.01, 0.50, 0.001)
	 */
	public long tickSize;

	public java.math.BigDecimal tickSizeAsDecimal() { return java.math.BigDecimal.valueOf(tickSize, 4); }

	/**
	 * Minimum size that cross orders should have
	 */
	public long minimumCrossOrderSize;

	/**
	 * Minimum allowed quantity of an order
	 */
	public long minimumSizeQty;

	/**
	 * Maximum allowed quantity of an order
	 */
	public long maximumSizeQty;

	/**
	 * 0=CORP, 1=CST, 2=FUT, 3=MLEG, 4=OOF, 5=OPT, 6=PS, 7=TB, 8=TBILL, 9=TBOND, 10=TIPS
	 */
	public int securityType;

	/**
	 * Asset Name (e.g. PETR)
	 */
	public String assetName;

	/**
	 * Parses SymbolDirectory message from ByteBuffer.
	 * 
	 * @param pSymbolDirectory the MitchSymbolDirectory instance to populate
	 * @param pData the ByteBuffer containing message data (little-endian)
	 */
	public static void parse(MitchSymbolDirectory pSymbolDirectory, ByteBuffer pData) {
		pSymbolDirectory.nanosecond = BitUtils.byteArrayToUInt32(pData, false);
		byte[] symbolBytes = new byte[25];
		pData.get(symbolBytes);
		pSymbolDirectory.symbol = new String(symbolBytes).trim();
		pSymbolDirectory.instrumentId = BitUtils.byteArrayToUInt32(pData, false);
		byte[] symbolStatusBytes = new byte[1];
		pData.get(symbolStatusBytes);
		pSymbolDirectory.symbolStatus = new String(symbolStatusBytes).trim();
		byte[] identificationNumberBytes = new byte[12];
		pData.get(identificationNumberBytes);
		pSymbolDirectory.identificationNumber = new String(identificationNumberBytes).trim();
		byte[] segmentBytes = new byte[10];
		pData.get(segmentBytes);
		pSymbolDirectory.segment = new String(segmentBytes).trim();
		byte[] expirationDateBytes = new byte[8];
		pData.get(expirationDateBytes);
		pSymbolDirectory.expirationDate = new String(expirationDateBytes).trim();
		byte[] underlyingBytes = new byte[25];
		pData.get(underlyingBytes);
		pSymbolDirectory.underlying = new String(underlyingBytes).trim();
		pSymbolDirectory.underlyingInstrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pSymbolDirectory.strikePrice = pData.getLong();
		byte[] optionTypeBytes = new byte[1];
		pData.get(optionTypeBytes);
		pSymbolDirectory.optionType = new String(optionTypeBytes).trim();
		byte[] issuerBytes = new byte[6];
		pData.get(issuerBytes);
		pSymbolDirectory.issuer = new String(issuerBytes).trim();
		byte[] issueDateBytes = new byte[8];
		pData.get(issueDateBytes);
		pSymbolDirectory.issueDate = new String(issueDateBytes).trim();
		pSymbolDirectory.coupon = pData.getInt();
		pSymbolDirectory.flags = pData.get();
		pSymbolDirectory.subBook = pData.get();
		byte[] corporateActionBytes = new byte[5];
		pData.get(corporateActionBytes);
		pSymbolDirectory.corporateAction = new String(corporateActionBytes).trim();
		byte[] partitionIdBytes = new byte[1];
		pData.get(partitionIdBytes);
		pSymbolDirectory.partitionId = new String(partitionIdBytes).trim();
		byte[] exerciseStyleBytes = new byte[1];
		pData.get(exerciseStyleBytes);
		pSymbolDirectory.exerciseStyle = new String(exerciseStyleBytes).trim();
		byte[] leg1SymbolBytes = new byte[25];
		pData.get(leg1SymbolBytes);
		pSymbolDirectory.leg1Symbol = new String(leg1SymbolBytes).trim();
		pSymbolDirectory.leg1InstrumentId = BitUtils.byteArrayToUInt32(pData, false);
		byte[] leg2SymbolBytes = new byte[25];
		pData.get(leg2SymbolBytes);
		pSymbolDirectory.leg2Symbol = new String(leg2SymbolBytes).trim();
		pSymbolDirectory.leg2InstrumentId = BitUtils.byteArrayToUInt32(pData, false);
		pSymbolDirectory.contractMultiplier = pData.getInt();
		byte[] settlementMethodBytes = new byte[1];
		pData.get(settlementMethodBytes);
		pSymbolDirectory.settlementMethod = new String(settlementMethodBytes).trim();
		pSymbolDirectory.testInstrument = BitUtils.byteToUInt8(pData);
		byte[] venueOfExecutionBytes = new byte[4];
		pData.get(venueOfExecutionBytes);
		pSymbolDirectory.venueOfExecution = new String(venueOfExecutionBytes).trim();
		pSymbolDirectory.lotSize = BitUtils.byteArrayToUInt32(pData, false);
		byte[] securityDescriptionBytes = new byte[110];
		pData.get(securityDescriptionBytes);
		pSymbolDirectory.securityDescription = new String(securityDescriptionBytes).trim();
		pSymbolDirectory.listMethod = pData.get();
		byte[] currencyBytes = new byte[3];
		pData.get(currencyBytes);
		pSymbolDirectory.currency = new String(currencyBytes).trim();
		pSymbolDirectory.tickSize = pData.getLong();
		pSymbolDirectory.minimumCrossOrderSize = BitUtils.byteArrayToUInt32(pData, false);
		pSymbolDirectory.minimumSizeQty = BitUtils.byteArrayToUInt32(pData, false);
		pSymbolDirectory.maximumSizeQty = BitUtils.byteArrayToUInt32(pData, false);
		pSymbolDirectory.securityType = BitUtils.byteToUInt8(pData);
		byte[] assetNameBytes = new byte[6];
		pData.get(assetNameBytes);
		pSymbolDirectory.assetName = new String(assetNameBytes).trim();
	}

	/**
	 * Encodes SymbolDirectory message to ByteBuffer.
	 * 
	 * @param pSymbolDirectory the MitchSymbolDirectory instance to encode
	 * @param pData the ByteBuffer to write message data (little-endian)
	 */
	public static void encode(MitchSymbolDirectory pSymbolDirectory, ByteBuffer pData) {
		BitUtils.uint32ToByteArray(pSymbolDirectory.nanosecond, pData, false);
		byte[] symbolBytes = new byte[25];
		byte[] symbolSrc = pSymbolDirectory.symbol.getBytes();
		System.arraycopy(symbolSrc, 0, symbolBytes, 0, Math.min(symbolSrc.length, 25));
		pData.put(symbolBytes);
		BitUtils.uint32ToByteArray(pSymbolDirectory.instrumentId, pData, false);
		byte[] symbolStatusBytes = new byte[1];
		byte[] symbolStatusSrc = pSymbolDirectory.symbolStatus.getBytes();
		System.arraycopy(symbolStatusSrc, 0, symbolStatusBytes, 0, Math.min(symbolStatusSrc.length, 1));
		pData.put(symbolStatusBytes);
		byte[] identificationNumberBytes = new byte[12];
		byte[] identificationNumberSrc = pSymbolDirectory.identificationNumber.getBytes();
		System.arraycopy(identificationNumberSrc, 0, identificationNumberBytes, 0, Math.min(identificationNumberSrc.length, 12));
		pData.put(identificationNumberBytes);
		byte[] segmentBytes = new byte[10];
		byte[] segmentSrc = pSymbolDirectory.segment.getBytes();
		System.arraycopy(segmentSrc, 0, segmentBytes, 0, Math.min(segmentSrc.length, 10));
		pData.put(segmentBytes);
		byte[] expirationDateBytes = new byte[8];
		byte[] expirationDateSrc = pSymbolDirectory.expirationDate.getBytes();
		System.arraycopy(expirationDateSrc, 0, expirationDateBytes, 0, Math.min(expirationDateSrc.length, 8));
		pData.put(expirationDateBytes);
		byte[] underlyingBytes = new byte[25];
		byte[] underlyingSrc = pSymbolDirectory.underlying.getBytes();
		System.arraycopy(underlyingSrc, 0, underlyingBytes, 0, Math.min(underlyingSrc.length, 25));
		pData.put(underlyingBytes);
		BitUtils.uint32ToByteArray(pSymbolDirectory.underlyingInstrumentId, pData, false);
		pData.putLong(pSymbolDirectory.strikePrice);
		byte[] optionTypeBytes = new byte[1];
		byte[] optionTypeSrc = pSymbolDirectory.optionType.getBytes();
		System.arraycopy(optionTypeSrc, 0, optionTypeBytes, 0, Math.min(optionTypeSrc.length, 1));
		pData.put(optionTypeBytes);
		byte[] issuerBytes = new byte[6];
		byte[] issuerSrc = pSymbolDirectory.issuer.getBytes();
		System.arraycopy(issuerSrc, 0, issuerBytes, 0, Math.min(issuerSrc.length, 6));
		pData.put(issuerBytes);
		byte[] issueDateBytes = new byte[8];
		byte[] issueDateSrc = pSymbolDirectory.issueDate.getBytes();
		System.arraycopy(issueDateSrc, 0, issueDateBytes, 0, Math.min(issueDateSrc.length, 8));
		pData.put(issueDateBytes);
		pData.putInt(pSymbolDirectory.coupon);
		pData.put(pSymbolDirectory.flags);
		pData.put(pSymbolDirectory.subBook);
		byte[] corporateActionBytes = new byte[5];
		byte[] corporateActionSrc = pSymbolDirectory.corporateAction.getBytes();
		System.arraycopy(corporateActionSrc, 0, corporateActionBytes, 0, Math.min(corporateActionSrc.length, 5));
		pData.put(corporateActionBytes);
		byte[] partitionIdBytes = new byte[1];
		byte[] partitionIdSrc = pSymbolDirectory.partitionId.getBytes();
		System.arraycopy(partitionIdSrc, 0, partitionIdBytes, 0, Math.min(partitionIdSrc.length, 1));
		pData.put(partitionIdBytes);
		byte[] exerciseStyleBytes = new byte[1];
		byte[] exerciseStyleSrc = pSymbolDirectory.exerciseStyle.getBytes();
		System.arraycopy(exerciseStyleSrc, 0, exerciseStyleBytes, 0, Math.min(exerciseStyleSrc.length, 1));
		pData.put(exerciseStyleBytes);
		byte[] leg1SymbolBytes = new byte[25];
		byte[] leg1SymbolSrc = pSymbolDirectory.leg1Symbol.getBytes();
		System.arraycopy(leg1SymbolSrc, 0, leg1SymbolBytes, 0, Math.min(leg1SymbolSrc.length, 25));
		pData.put(leg1SymbolBytes);
		BitUtils.uint32ToByteArray(pSymbolDirectory.leg1InstrumentId, pData, false);
		byte[] leg2SymbolBytes = new byte[25];
		byte[] leg2SymbolSrc = pSymbolDirectory.leg2Symbol.getBytes();
		System.arraycopy(leg2SymbolSrc, 0, leg2SymbolBytes, 0, Math.min(leg2SymbolSrc.length, 25));
		pData.put(leg2SymbolBytes);
		BitUtils.uint32ToByteArray(pSymbolDirectory.leg2InstrumentId, pData, false);
		pData.putInt(pSymbolDirectory.contractMultiplier);
		byte[] settlementMethodBytes = new byte[1];
		byte[] settlementMethodSrc = pSymbolDirectory.settlementMethod.getBytes();
		System.arraycopy(settlementMethodSrc, 0, settlementMethodBytes, 0, Math.min(settlementMethodSrc.length, 1));
		pData.put(settlementMethodBytes);
		BitUtils.uint8ToByte(pSymbolDirectory.testInstrument, pData);
		byte[] venueOfExecutionBytes = new byte[4];
		byte[] venueOfExecutionSrc = pSymbolDirectory.venueOfExecution.getBytes();
		System.arraycopy(venueOfExecutionSrc, 0, venueOfExecutionBytes, 0, Math.min(venueOfExecutionSrc.length, 4));
		pData.put(venueOfExecutionBytes);
		BitUtils.uint32ToByteArray(pSymbolDirectory.lotSize, pData, false);
		byte[] securityDescriptionBytes = new byte[110];
		byte[] securityDescriptionSrc = pSymbolDirectory.securityDescription.getBytes();
		System.arraycopy(securityDescriptionSrc, 0, securityDescriptionBytes, 0, Math.min(securityDescriptionSrc.length, 110));
		pData.put(securityDescriptionBytes);
		pData.put(pSymbolDirectory.listMethod);
		byte[] currencyBytes = new byte[3];
		byte[] currencySrc = pSymbolDirectory.currency.getBytes();
		System.arraycopy(currencySrc, 0, currencyBytes, 0, Math.min(currencySrc.length, 3));
		pData.put(currencyBytes);
		pData.putLong(pSymbolDirectory.tickSize);
		BitUtils.uint32ToByteArray(pSymbolDirectory.minimumCrossOrderSize, pData, false);
		BitUtils.uint32ToByteArray(pSymbolDirectory.minimumSizeQty, pData, false);
		BitUtils.uint32ToByteArray(pSymbolDirectory.maximumSizeQty, pData, false);
		BitUtils.uint8ToByte(pSymbolDirectory.securityType, pData);
		byte[] assetNameBytes = new byte[6];
		byte[] assetNameSrc = pSymbolDirectory.assetName.getBytes();
		System.arraycopy(assetNameSrc, 0, assetNameBytes, 0, Math.min(assetNameSrc.length, 6));
		pData.put(assetNameBytes);
	}

	@Override
	public String toString() {
		return "MitchSymbolDirectory [" + "nanosecond=" + nanosecond + ", symbol=" + symbol + ", instrumentId=" + instrumentId + ", symbolStatus=" + symbolStatus + ", identificationNumber=" + identificationNumber + ", segment=" + segment + ", expirationDate=" + expirationDate + ", underlying=" + underlying + ", underlyingInstrumentId=" + underlyingInstrumentId + ", strikePrice=" + strikePrice + ", optionType=" + optionType + ", issuer=" + issuer + ", issueDate=" + issueDate + ", coupon=" + coupon + ", flags=" + flags + ", subBook=" + subBook + ", corporateAction=" + corporateAction + ", partitionId=" + partitionId + ", exerciseStyle=" + exerciseStyle + ", leg1Symbol=" + leg1Symbol + ", leg1InstrumentId=" + leg1InstrumentId + ", leg2Symbol=" + leg2Symbol + ", leg2InstrumentId=" + leg2InstrumentId + ", contractMultiplier=" + contractMultiplier + ", settlementMethod=" + settlementMethod + ", testInstrument=" + testInstrument + ", venueOfExecution=" + venueOfExecution + ", lotSize=" + lotSize + ", securityDescription=" + securityDescription + ", listMethod=" + listMethod + ", currency=" + currency + ", tickSize=" + tickSize + ", minimumCrossOrderSize=" + minimumCrossOrderSize + ", minimumSizeQty=" + minimumSizeQty + ", maximumSizeQty=" + maximumSizeQty + ", securityType=" + securityType + ", assetName=" + assetName + "]";
	}
}
