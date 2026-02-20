using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent during recovery to provide historical trade information
    /// </summary>
    public class MitchRecoveryTrade
    {
        /// <summary>
        /// Nanoseconds offset from the last Time message
        /// </summary>
        public uint Nanosecond { get; set; }

        /// <summary>
        /// Identifier for the order
        /// </summary>
        public BigInteger OrderId { get; set; }

        /// <summary>
        /// Quantity executed
        /// </summary>
        public uint ExecutedQuantity { get; set; }

        /// <summary>
        /// Executed price
        /// </summary>
        public long Price { get; set; }

        public decimal PriceAsDecimal => Price / 10000m;

        /// <summary>
        /// Numeric identifier of the instrument
        /// </summary>
        public uint InstrumentId { get; set; }

        /// <summary>
        /// Unique identifier of the trade
        /// </summary>
        public BigInteger TradeId { get; set; }

        /// <summary>
        /// Converted price of the executed volatility of the options instrument
        /// </summary>
        public long LastOptPx { get; set; }

        public decimal LastOptPxAsDecimal => LastOptPx / 10000m;

        /// <summary>
        /// Converted volatility of the executed price of the options instrument
        /// </summary>
        public long Volatility { get; set; }

        public decimal VolatilityAsDecimal => Volatility / 10000m;

        /// <summary>
        /// Underlying Reference Price related to converted value calculated upon an options instrument trade execution
        /// </summary>
        public long UnderlyingReferencePrice { get; set; }

        public decimal UnderlyingReferencePriceAsDecimal => UnderlyingReferencePrice / 10000m;

        /// <summary>
        /// Date and time when the trade was executed [Epoch]
        /// </summary>
        public BigInteger TradingDateTime { get; set; }

        /// <summary>
        /// Instrument identification code
        /// </summary>
        public string InstrumentIdentificationCode { get; set; }

        /// <summary>
        /// 1 = ISIN Code
        /// </summary>
        public byte InstrumentIdentificationCodeType { get; set; }

        /// <summary>
        /// Currency in which the price of the trade is expressed
        /// </summary>
        public string Currency { get; set; }

        /// <summary>
        /// Identification of the venue where the trade was executed
        /// </summary>
        public string VenueOfExecution { get; set; }

        /// <summary>
        /// 1 = MONE - Monetary value, 2 = PERC - Percentage, 3 = YIEL - Yield
        /// </summary>
        public byte PriceNotation { get; set; }

        /// <summary>
        /// Notional value relevant to the security
        /// </summary>
        public long NotionalAmount { get; set; }

        public decimal NotionalAmountAsDecimal => NotionalAmount / 10000m;

        /// <summary>
        /// The currency in which the notional is represented
        /// </summary>
        public string NotionalCurrency { get; set; }

        /// <summary>
        /// Date and time when the trade was published by the trading venue [Epoch]
        /// </summary>
        public BigInteger PublicationDateTime { get; set; }

        /// <summary>
        /// Firm ID of the party firm
        /// </summary>
        public string Firm { get; set; }

        /// <summary>
        /// Firm ID of the counter party firm
        /// </summary>
        public string ContraFirm { get; set; }

        /// <summary>
        /// Indicates if the trade has been cancelled (Value = CANC)
        /// </summary>
        public string CancellationFlag { get; set; }

        /// <summary>
        /// Indicates if the trade has been amended (Value = AMND)
        /// </summary>
        public string AmendmentFlag { get; set; }

        /// <summary>
        /// 1 = Regular, 11 = Negotiated Trades
        /// </summary>
        public byte SubBook { get; set; }

        /// <summary>
        /// Bit 0: Trade Condition Flag, Bit 1: Internal Crossed Trade, Bit 5: RLP
        /// </summary>
        public object Flags { get; set; }

        /// <summary>
        /// A = Re-Opening Auction, C = Closing Auction, O = Opening Auction
        /// </summary>
        public byte AuctionType { get; set; }

        public static void Parse(MitchRecoveryTrade msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.OrderId = new BigInteger(data.Slice(offset, 8));
            offset += 8;
            msg.ExecutedQuantity = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.Price = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.TradeId = new BigInteger(data.Slice(offset, 8));
            offset += 8;
            msg.LastOptPx = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.Volatility = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.UnderlyingReferencePrice = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.TradingDateTime = new BigInteger(data.Slice(offset, 8));
            offset += 8;
            msg.InstrumentIdentificationCode = Encoding.ASCII.GetString(data.Slice(offset, 12)).TrimEnd();
            offset += 12;
            msg.InstrumentIdentificationCodeType = data[offset++];
            msg.Currency = Encoding.ASCII.GetString(data.Slice(offset, 3)).TrimEnd();
            offset += 3;
            msg.VenueOfExecution = Encoding.ASCII.GetString(data.Slice(offset, 4)).TrimEnd();
            offset += 4;
            msg.PriceNotation = data[offset++];
            msg.NotionalAmount = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.NotionalCurrency = Encoding.ASCII.GetString(data.Slice(offset, 3)).TrimEnd();
            offset += 3;
            msg.PublicationDateTime = new BigInteger(data.Slice(offset, 8));
            offset += 8;
            msg.Firm = Encoding.ASCII.GetString(data.Slice(offset, 6)).TrimEnd();
            offset += 6;
            msg.ContraFirm = Encoding.ASCII.GetString(data.Slice(offset, 6)).TrimEnd();
            offset += 6;
            msg.CancellationFlag = Encoding.ASCII.GetString(data.Slice(offset, 4)).TrimEnd();
            offset += 4;
            msg.AmendmentFlag = Encoding.ASCII.GetString(data.Slice(offset, 4)).TrimEnd();
            offset += 4;
            msg.SubBook = data[offset++];
            msg.AuctionType = data[offset++];
        }

        public static void Encode(MitchRecoveryTrade msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            msg.OrderId.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.ExecutedQuantity);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.Price);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            msg.TradeId.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.LastOptPx);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.Volatility);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.UnderlyingReferencePrice);
            offset += 8;
            msg.TradingDateTime.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
            var instrumentIdentificationCodeBytes = Encoding.ASCII.GetBytes(msg.InstrumentIdentificationCode.PadRight(12));
            instrumentIdentificationCodeBytes.AsSpan(0, 12).CopyTo(data.Slice(offset, 12));
            offset += 12;
            data[offset++] = (byte)msg.InstrumentIdentificationCodeType;
            var currencyBytes = Encoding.ASCII.GetBytes(msg.Currency.PadRight(3));
            currencyBytes.AsSpan(0, 3).CopyTo(data.Slice(offset, 3));
            offset += 3;
            var venueOfExecutionBytes = Encoding.ASCII.GetBytes(msg.VenueOfExecution.PadRight(4));
            venueOfExecutionBytes.AsSpan(0, 4).CopyTo(data.Slice(offset, 4));
            offset += 4;
            data[offset++] = (byte)msg.PriceNotation;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.NotionalAmount);
            offset += 8;
            var notionalCurrencyBytes = Encoding.ASCII.GetBytes(msg.NotionalCurrency.PadRight(3));
            notionalCurrencyBytes.AsSpan(0, 3).CopyTo(data.Slice(offset, 3));
            offset += 3;
            msg.PublicationDateTime.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
            var firmBytes = Encoding.ASCII.GetBytes(msg.Firm.PadRight(6));
            firmBytes.AsSpan(0, 6).CopyTo(data.Slice(offset, 6));
            offset += 6;
            var contraFirmBytes = Encoding.ASCII.GetBytes(msg.ContraFirm.PadRight(6));
            contraFirmBytes.AsSpan(0, 6).CopyTo(data.Slice(offset, 6));
            offset += 6;
            var cancellationFlagBytes = Encoding.ASCII.GetBytes(msg.CancellationFlag.PadRight(4));
            cancellationFlagBytes.AsSpan(0, 4).CopyTo(data.Slice(offset, 4));
            offset += 4;
            var amendmentFlagBytes = Encoding.ASCII.GetBytes(msg.AmendmentFlag.PadRight(4));
            amendmentFlagBytes.AsSpan(0, 4).CopyTo(data.Slice(offset, 4));
            offset += 4;
            data[offset++] = (byte)msg.SubBook;
            data[offset++] = (byte)msg.AuctionType;
        }
    }
}
