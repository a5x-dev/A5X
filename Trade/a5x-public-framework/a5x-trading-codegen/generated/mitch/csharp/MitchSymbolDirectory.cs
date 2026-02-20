using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Used to disseminate information on each instrument
    /// </summary>
    public class MitchSymbolDirectory
    {
        /// <summary>
        /// Nanoseconds offset from the last Time message
        /// </summary>
        public uint Nanosecond { get; set; }

        /// <summary>
        /// Instrument's symbol
        /// </summary>
        public string Symbol { get; set; }

        /// <summary>
        /// Numeric Identifier of the instrument
        /// </summary>
        public uint InstrumentId { get; set; }

        /// <summary>
        /// Space = Active, H = Halted, S = Suspended, a = Inactive
        /// </summary>
        public string SymbolStatus { get; set; }

        /// <summary>
        /// Instrument identification number (e.g. ISIN, CUSIP, etc.)
        /// </summary>
        public string IdentificationNumber { get; set; }

        /// <summary>
        /// Segment the instrument is assigned to
        /// </summary>
        public string Segment { get; set; }

        /// <summary>
        /// Date an instrument expires or matures. This field will contain only spaces if the instrument is not a derivative or fixed income instrument
        /// </summary>
        public object ExpirationDate { get; set; }

        /// <summary>
        /// Symbol of the underlying instrument. This field will contain only spaces if the instrument is not a derivative
        /// </summary>
        public string Underlying { get; set; }

        /// <summary>
        /// Numeric Identifier of the underlying instrument
        /// </summary>
        public uint UnderlyingInstrumentId { get; set; }

        /// <summary>
        /// Strike price of an option. The price will be zero if the instrument is not an option
        /// </summary>
        public long StrikePrice { get; set; }

        public decimal StrikePriceAsDecimal => StrikePrice / 10000m;

        /// <summary>
        /// Space = Not option, C = Call Option, P = Put Option
        /// </summary>
        public string OptionType { get; set; }

        /// <summary>
        /// Issuer of the instrument. This field will contain all spaces if the instrument is not a fixed income instrument
        /// </summary>
        public string Issuer { get; set; }

        /// <summary>
        /// Date instrument was issued. This field will contain all spaces if the instrument is not a fixed income instrument
        /// </summary>
        public object IssueDate { get; set; }

        /// <summary>
        /// Rate of interest applied to the face value. This is a percentage field (e.g. 0.05 represents 5%)
        /// </summary>
        public int Coupon { get; set; }

        public decimal CouponAsDecimal => Coupon / 10000m;

        /// <summary>
        /// Bit 0: Inverse Order Book (0 = No, 1 = Yes)
        /// </summary>
        public object Flags { get; set; }

        /// <summary>
        /// Bit 0: Regular (0=No, 1=Yes), Bit 1: Off-Book (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes), Bit 6: Negotiated Trades (0=No, 1=Yes)
        /// </summary>
        public object SubBook { get; set; }

        /// <summary>
        /// Pipe separated field. Identifies the type of Corporate Actions applicable on the instrument for the current Trading day
        /// </summary>
        public string CorporateAction { get; set; }

        /// <summary>
        /// Identity of the partition
        /// </summary>
        public string PartitionId { get; set; }

        /// <summary>
        /// Space = Not option, A = American, E = European
        /// </summary>
        public string ExerciseStyle { get; set; }

        /// <summary>
        /// Symbol of Leg 1 instrument. This field will contain only spaces if the instrument is not a multi-legged instrument
        /// </summary>
        public string Leg1Symbol { get; set; }

        /// <summary>
        /// Numeric Identifier of the leg 1 instrument
        /// </summary>
        public uint Leg1InstrumentId { get; set; }

        /// <summary>
        /// Symbol of Leg 2 instrument. This field will contain only spaces if the instrument is not a multi-legged instrument
        /// </summary>
        public string Leg2Symbol { get; set; }

        /// <summary>
        /// Numeric Identifier of the leg 2 instrument
        /// </summary>
        public uint Leg2InstrumentId { get; set; }

        /// <summary>
        /// Defines the multiplier of the instrument. This field will contain 0 value if the instrument does not have a contract multiplier
        /// </summary>
        public int ContractMultiplier { get; set; }

        public decimal ContractMultiplierAsDecimal => ContractMultiplier / 10000m;

        /// <summary>
        /// Space = No method, C = Cash, P = Physical
        /// </summary>
        public string SettlementMethod { get; set; }

        /// <summary>
        /// 0 = Not test, 1 = Exchange only, 2 = End to end test
        /// </summary>
        public byte TestInstrument { get; set; }

        /// <summary>
        /// Trading venue where the symbol was originated
        /// </summary>
        public string VenueOfExecution { get; set; }

        /// <summary>
        /// Lot Size of the Instrument
        /// </summary>
        public uint LotSize { get; set; }

        /// <summary>
        /// Description of the instrument
        /// </summary>
        public string SecurityDescription { get; set; }

        /// <summary>
        /// P = Pre-Listed, U = User Defined
        /// </summary>
        public byte ListMethod { get; set; }

        /// <summary>
        /// Trading currency of the instrument
        /// </summary>
        public string Currency { get; set; }

        /// <summary>
        /// Instrument Tick Size (e.g. 0.01, 0.50, 0.001)
        /// </summary>
        public long TickSize { get; set; }

        public decimal TickSizeAsDecimal => TickSize / 10000m;

        /// <summary>
        /// Minimum size that cross orders should have
        /// </summary>
        public uint MinimumCrossOrderSize { get; set; }

        /// <summary>
        /// Minimum allowed quantity of an order
        /// </summary>
        public uint MinimumSizeQty { get; set; }

        /// <summary>
        /// Maximum allowed quantity of an order
        /// </summary>
        public uint MaximumSizeQty { get; set; }

        /// <summary>
        /// 0=CORP, 1=CST, 2=FUT, 3=MLEG, 4=OOF, 5=OPT, 6=PS, 7=TB, 8=TBILL, 9=TBOND, 10=TIPS
        /// </summary>
        public byte SecurityType { get; set; }

        /// <summary>
        /// Asset Name (e.g. PETR)
        /// </summary>
        public string AssetName { get; set; }

        public static void Parse(MitchSymbolDirectory msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.Symbol = Encoding.ASCII.GetString(data.Slice(offset, 25)).TrimEnd();
            offset += 25;
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.SymbolStatus = Encoding.ASCII.GetString(data.Slice(offset, 1)).TrimEnd();
            offset += 1;
            msg.IdentificationNumber = Encoding.ASCII.GetString(data.Slice(offset, 12)).TrimEnd();
            offset += 12;
            msg.Segment = Encoding.ASCII.GetString(data.Slice(offset, 10)).TrimEnd();
            offset += 10;
            msg.Underlying = Encoding.ASCII.GetString(data.Slice(offset, 25)).TrimEnd();
            offset += 25;
            msg.UnderlyingInstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.StrikePrice = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.OptionType = Encoding.ASCII.GetString(data.Slice(offset, 1)).TrimEnd();
            offset += 1;
            msg.Issuer = Encoding.ASCII.GetString(data.Slice(offset, 6)).TrimEnd();
            offset += 6;
            msg.Coupon = BitConverter.ToInt32(data.Slice(offset, 4));
            offset += 4;
            msg.CorporateAction = Encoding.ASCII.GetString(data.Slice(offset, 5)).TrimEnd();
            offset += 5;
            msg.PartitionId = Encoding.ASCII.GetString(data.Slice(offset, 1)).TrimEnd();
            offset += 1;
            msg.ExerciseStyle = Encoding.ASCII.GetString(data.Slice(offset, 1)).TrimEnd();
            offset += 1;
            msg.Leg1Symbol = Encoding.ASCII.GetString(data.Slice(offset, 25)).TrimEnd();
            offset += 25;
            msg.Leg1InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.Leg2Symbol = Encoding.ASCII.GetString(data.Slice(offset, 25)).TrimEnd();
            offset += 25;
            msg.Leg2InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.ContractMultiplier = BitConverter.ToInt32(data.Slice(offset, 4));
            offset += 4;
            msg.SettlementMethod = Encoding.ASCII.GetString(data.Slice(offset, 1)).TrimEnd();
            offset += 1;
            msg.TestInstrument = data[offset++];
            msg.VenueOfExecution = Encoding.ASCII.GetString(data.Slice(offset, 4)).TrimEnd();
            offset += 4;
            msg.LotSize = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.SecurityDescription = Encoding.ASCII.GetString(data.Slice(offset, 110)).TrimEnd();
            offset += 110;
            msg.ListMethod = data[offset++];
            msg.Currency = Encoding.ASCII.GetString(data.Slice(offset, 3)).TrimEnd();
            offset += 3;
            msg.TickSize = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.MinimumCrossOrderSize = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.MinimumSizeQty = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.MaximumSizeQty = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.SecurityType = data[offset++];
            msg.AssetName = Encoding.ASCII.GetString(data.Slice(offset, 6)).TrimEnd();
            offset += 6;
        }

        public static void Encode(MitchSymbolDirectory msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            var symbolBytes = Encoding.ASCII.GetBytes(msg.Symbol.PadRight(25));
            symbolBytes.AsSpan(0, 25).CopyTo(data.Slice(offset, 25));
            offset += 25;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            var symbolStatusBytes = Encoding.ASCII.GetBytes(msg.SymbolStatus.PadRight(1));
            symbolStatusBytes.AsSpan(0, 1).CopyTo(data.Slice(offset, 1));
            offset += 1;
            var identificationNumberBytes = Encoding.ASCII.GetBytes(msg.IdentificationNumber.PadRight(12));
            identificationNumberBytes.AsSpan(0, 12).CopyTo(data.Slice(offset, 12));
            offset += 12;
            var segmentBytes = Encoding.ASCII.GetBytes(msg.Segment.PadRight(10));
            segmentBytes.AsSpan(0, 10).CopyTo(data.Slice(offset, 10));
            offset += 10;
            var underlyingBytes = Encoding.ASCII.GetBytes(msg.Underlying.PadRight(25));
            underlyingBytes.AsSpan(0, 25).CopyTo(data.Slice(offset, 25));
            offset += 25;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.UnderlyingInstrumentId);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.StrikePrice);
            offset += 8;
            var optionTypeBytes = Encoding.ASCII.GetBytes(msg.OptionType.PadRight(1));
            optionTypeBytes.AsSpan(0, 1).CopyTo(data.Slice(offset, 1));
            offset += 1;
            var issuerBytes = Encoding.ASCII.GetBytes(msg.Issuer.PadRight(6));
            issuerBytes.AsSpan(0, 6).CopyTo(data.Slice(offset, 6));
            offset += 6;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Coupon);
            offset += 4;
            var corporateActionBytes = Encoding.ASCII.GetBytes(msg.CorporateAction.PadRight(5));
            corporateActionBytes.AsSpan(0, 5).CopyTo(data.Slice(offset, 5));
            offset += 5;
            var partitionIdBytes = Encoding.ASCII.GetBytes(msg.PartitionId.PadRight(1));
            partitionIdBytes.AsSpan(0, 1).CopyTo(data.Slice(offset, 1));
            offset += 1;
            var exerciseStyleBytes = Encoding.ASCII.GetBytes(msg.ExerciseStyle.PadRight(1));
            exerciseStyleBytes.AsSpan(0, 1).CopyTo(data.Slice(offset, 1));
            offset += 1;
            var leg1SymbolBytes = Encoding.ASCII.GetBytes(msg.Leg1Symbol.PadRight(25));
            leg1SymbolBytes.AsSpan(0, 25).CopyTo(data.Slice(offset, 25));
            offset += 25;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Leg1InstrumentId);
            offset += 4;
            var leg2SymbolBytes = Encoding.ASCII.GetBytes(msg.Leg2Symbol.PadRight(25));
            leg2SymbolBytes.AsSpan(0, 25).CopyTo(data.Slice(offset, 25));
            offset += 25;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Leg2InstrumentId);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.ContractMultiplier);
            offset += 4;
            var settlementMethodBytes = Encoding.ASCII.GetBytes(msg.SettlementMethod.PadRight(1));
            settlementMethodBytes.AsSpan(0, 1).CopyTo(data.Slice(offset, 1));
            offset += 1;
            data[offset++] = (byte)msg.TestInstrument;
            var venueOfExecutionBytes = Encoding.ASCII.GetBytes(msg.VenueOfExecution.PadRight(4));
            venueOfExecutionBytes.AsSpan(0, 4).CopyTo(data.Slice(offset, 4));
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.LotSize);
            offset += 4;
            var securityDescriptionBytes = Encoding.ASCII.GetBytes(msg.SecurityDescription.PadRight(110));
            securityDescriptionBytes.AsSpan(0, 110).CopyTo(data.Slice(offset, 110));
            offset += 110;
            data[offset++] = (byte)msg.ListMethod;
            var currencyBytes = Encoding.ASCII.GetBytes(msg.Currency.PadRight(3));
            currencyBytes.AsSpan(0, 3).CopyTo(data.Slice(offset, 3));
            offset += 3;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.TickSize);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.MinimumCrossOrderSize);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.MinimumSizeQty);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.MaximumSizeQty);
            offset += 4;
            data[offset++] = (byte)msg.SecurityType;
            var assetNameBytes = Encoding.ASCII.GetBytes(msg.AssetName.PadRight(6));
            assetNameBytes.AsSpan(0, 6).CopyTo(data.Slice(offset, 6));
            offset += 6;
        }
    }
}
