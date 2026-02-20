using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent to provide extended statistical information about instruments
    /// </summary>
    public class MitchExtendedStatistics
    {
        /// <summary>
        /// Nanoseconds offset from the last Time message
        /// </summary>
        public uint Nanosecond { get; set; }

        /// <summary>
        /// Numeric identifier of the instrument
        /// </summary>
        public uint InstrumentId { get; set; }

        /// <summary>
        /// High Price of the instrument. Will be set to a negative value if not set or withdrawn
        /// </summary>
        public long HighPrice { get; set; }

        public decimal HighPriceAsDecimal => HighPrice / 10000m;

        /// <summary>
        /// Low Price of the instrument. Will be set to a negative value if not set or withdrawn
        /// </summary>
        public long LowPrice { get; set; }

        public decimal LowPriceAsDecimal => LowPrice / 10000m;

        /// <summary>
        /// VWAP of the instrument. Will be set to a negative value if not set or withdrawn
        /// </summary>
        public long Vwap { get; set; }

        public decimal VwapAsDecimal => Vwap / 10000m;

        /// <summary>
        /// Volume of the instrument. Will be set to zero if not set or withdrawn
        /// </summary>
        public uint Volume { get; set; }

        /// <summary>
        /// Turnover of the instrument. Will be set to a negative value if not set or withdrawn
        /// </summary>
        public long Turnover { get; set; }

        public decimal TurnoverAsDecimal => Turnover / 10000m;

        /// <summary>
        /// Number of trades for this instrument. Will be set to zero if not set or withdrawn
        /// </summary>
        public uint NumberOfTrades { get; set; }

        /// <summary>
        /// Reserved for future use
        /// </summary>
        public byte[] ReservedField = new byte[8] { get; set; }

        /// <summary>
        /// 1 = Regular, 2 = Off-Book, 11 = Negotiated Trades
        /// </summary>
        public byte SubBook { get; set; }

        /// <summary>
        /// Notional exposure related to the options trade executions
        /// </summary>
        public long NotionalExposure { get; set; }

        public decimal NotionalExposureAsDecimal => NotionalExposure / 10000m;

        /// <summary>
        /// Notional exposure updated by the delta of the option based on trade executions
        /// </summary>
        public long NotionalDeltaExposure { get; set; }

        public decimal NotionalDeltaExposureAsDecimal => NotionalDeltaExposure / 10000m;

        /// <summary>
        /// Theoretical Price of the options trade
        /// </summary>
        public long TheoreticalPrice { get; set; }

        public decimal TheoreticalPriceAsDecimal => TheoreticalPrice / 10000m;

        /// <summary>
        /// Converted volatility of the executed price of the options instrument
        /// </summary>
        public long Volatility { get; set; }

        public decimal VolatilityAsDecimal => Volatility / 10000m;

        /// <summary>
        /// Upper Dynamic Price Band Limit of the instrument
        /// </summary>
        public long UpperDynamicPBLimit { get; set; }

        public decimal UpperDynamicPBLimitAsDecimal => UpperDynamicPBLimit / 10000m;

        /// <summary>
        /// Lower Dynamic Price Band Limit of the instrument
        /// </summary>
        public long LowerDynamicPBLimit { get; set; }

        public decimal LowerDynamicPBLimitAsDecimal => LowerDynamicPBLimit / 10000m;

        /// <summary>
        /// Upper Static Price Band Limit of the instrument
        /// </summary>
        public long UpperStaticPBLimit { get; set; }

        public decimal UpperStaticPBLimitAsDecimal => UpperStaticPBLimit / 10000m;

        /// <summary>
        /// Lower Static Price Band Limit of the instrument
        /// </summary>
        public long LowerStaticPBLimit { get; set; }

        public decimal LowerStaticPBLimitAsDecimal => LowerStaticPBLimit / 10000m;

        /// <summary>
        /// Upper Dynamic Circuit Breaker Limit of the instrument
        /// </summary>
        public long UpperDynamicCBLimit { get; set; }

        public decimal UpperDynamicCBLimitAsDecimal => UpperDynamicCBLimit / 10000m;

        /// <summary>
        /// Lower Dynamic Circuit Breaker Limit of the instrument
        /// </summary>
        public long LowerDynamicCBLimit { get; set; }

        public decimal LowerDynamicCBLimitAsDecimal => LowerDynamicCBLimit / 10000m;

        public static void Parse(MitchExtendedStatistics msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.HighPrice = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.LowPrice = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.Vwap = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.Volume = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.Turnover = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.NumberOfTrades = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            data.Slice(offset, 8).CopyTo(msg.ReservedField);
            offset += 8;
            msg.SubBook = data[offset++];
            msg.NotionalExposure = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.NotionalDeltaExposure = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.TheoreticalPrice = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.Volatility = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.UpperDynamicPBLimit = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.LowerDynamicPBLimit = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.UpperStaticPBLimit = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.LowerStaticPBLimit = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.UpperDynamicCBLimit = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.LowerDynamicCBLimit = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
        }

        public static void Encode(MitchExtendedStatistics msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.HighPrice);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.LowPrice);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.Vwap);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Volume);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.Turnover);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.NumberOfTrades);
            offset += 4;
            msg.ReservedField.CopyTo(data.Slice(offset, 8));
            offset += 8;
            data[offset++] = (byte)msg.SubBook;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.NotionalExposure);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.NotionalDeltaExposure);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.TheoreticalPrice);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.Volatility);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.UpperDynamicPBLimit);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.LowerDynamicPBLimit);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.UpperStaticPBLimit);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.LowerStaticPBLimit);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.UpperDynamicCBLimit);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.LowerDynamicCBLimit);
            offset += 8;
        }
    }
}
