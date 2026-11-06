using System;
using System.Buffers.Binary;
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

        public decimal HighPriceAsDecimal => HighPrice / 100000000m;

        /// <summary>
        /// Low Price of the instrument. Will be set to a negative value if not set or withdrawn
        /// </summary>
        public long LowPrice { get; set; }

        public decimal LowPriceAsDecimal => LowPrice / 100000000m;

        /// <summary>
        /// VWAP of the instrument. Will be set to a negative value if not set or withdrawn
        /// </summary>
        public long Vwap { get; set; }

        public decimal VwapAsDecimal => Vwap / 100000000m;

        /// <summary>
        /// Volume of the instrument. Will be set to zero if not set or withdrawn
        /// </summary>
        public uint Volume { get; set; }

        /// <summary>
        /// Turnover of the instrument. Will be set to a negative value if not set or withdrawn
        /// </summary>
        public long Turnover { get; set; }

        public decimal TurnoverAsDecimal => Turnover / 100000000m;

        /// <summary>
        /// Number of trades for this instrument. Will be set to zero if not set or withdrawn
        /// </summary>
        public uint NumberOfTrades { get; set; }

        /// <summary>
        /// Reserved for future use.
        /// </summary>
        public byte[] Reserved1 { get; set; } = new byte[8];

        /// <summary>
        /// 1 = Regular, 2 = Off-Book, 11 = Negotiated Trades
        /// </summary>
        public byte SubBook { get; set; }

        /// <summary>
        /// Notional exposure related to the options trade executions
        /// </summary>
        public long NotionalExposure { get; set; }

        public decimal NotionalExposureAsDecimal => NotionalExposure / 100000000m;

        /// <summary>
        /// Notional exposure updated by the delta of the option based on trade executions
        /// </summary>
        public long NotionalDeltaExposure { get; set; }

        public decimal NotionalDeltaExposureAsDecimal => NotionalDeltaExposure / 100000000m;

        /// <summary>
        /// Theoretical Price of the options trade
        /// </summary>
        public long TheoreticalPrice { get; set; }

        public decimal TheoreticalPriceAsDecimal => TheoreticalPrice / 100000000m;

        /// <summary>
        /// Converted volatility of the executed price of the options instrument
        /// </summary>
        public long Volatility { get; set; }

        public decimal VolatilityAsDecimal => Volatility / 100000000m;

        /// <summary>
        /// Upper Dynamic Price Band Limit of the instrument
        /// </summary>
        public long UpperDynamicPBLimit { get; set; }

        public decimal UpperDynamicPBLimitAsDecimal => UpperDynamicPBLimit / 100000000m;

        /// <summary>
        /// Lower Dynamic Price Band Limit of the instrument
        /// </summary>
        public long LowerDynamicPBLimit { get; set; }

        public decimal LowerDynamicPBLimitAsDecimal => LowerDynamicPBLimit / 100000000m;

        /// <summary>
        /// Upper Static Price Band Limit of the instrument
        /// </summary>
        public long UpperStaticPBLimit { get; set; }

        public decimal UpperStaticPBLimitAsDecimal => UpperStaticPBLimit / 100000000m;

        /// <summary>
        /// Lower Static Price Band Limit of the instrument
        /// </summary>
        public long LowerStaticPBLimit { get; set; }

        public decimal LowerStaticPBLimitAsDecimal => LowerStaticPBLimit / 100000000m;

        /// <summary>
        /// Upper Dynamic Circuit Breaker Limit of the instrument
        /// </summary>
        public long UpperDynamicCBLimit { get; set; }

        public decimal UpperDynamicCBLimitAsDecimal => UpperDynamicCBLimit / 100000000m;

        /// <summary>
        /// Lower Dynamic Circuit Breaker Limit of the instrument
        /// </summary>
        public long LowerDynamicCBLimit { get; set; }

        public decimal LowerDynamicCBLimitAsDecimal => LowerDynamicCBLimit / 100000000m;

        /// <summary>
        /// Upper Static Circuit Breaker Limit of the instrument
        /// </summary>
        public long UpperStaticCBLimit { get; set; }

        public decimal UpperStaticCBLimitAsDecimal => UpperStaticCBLimit / 100000000m;

        /// <summary>
        /// Lower Static Circuit Breaker Limit of the instrument
        /// </summary>
        public long LowerStaticCBLimit { get; set; }

        public decimal LowerStaticCBLimitAsDecimal => LowerStaticCBLimit / 100000000m;

        /// <summary>
        /// Previous day's closing Open Interest of the instrument
        /// </summary>
        public ulong OpenInterest { get; set; }

        public static void Parse(MitchExtendedStatistics msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            msg.InstrumentId = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            ulong highPriceRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.HighPrice = (highPriceRaw >> 63) != 0 ? -(long)(highPriceRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)highPriceRaw;
            offset += 8;
            ulong lowPriceRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.LowPrice = (lowPriceRaw >> 63) != 0 ? -(long)(lowPriceRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)lowPriceRaw;
            offset += 8;
            ulong vwapRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.Vwap = (vwapRaw >> 63) != 0 ? -(long)(vwapRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)vwapRaw;
            offset += 8;
            msg.Volume = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            ulong turnoverRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.Turnover = (turnoverRaw >> 63) != 0 ? -(long)(turnoverRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)turnoverRaw;
            offset += 8;
            msg.NumberOfTrades = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            data.Slice(offset, 8).CopyTo(msg.Reserved1);
            offset += 8;
            msg.SubBook = data[offset++];
            ulong notionalExposureRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.NotionalExposure = (notionalExposureRaw >> 63) != 0 ? -(long)(notionalExposureRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)notionalExposureRaw;
            offset += 8;
            ulong notionalDeltaExposureRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.NotionalDeltaExposure = (notionalDeltaExposureRaw >> 63) != 0 ? -(long)(notionalDeltaExposureRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)notionalDeltaExposureRaw;
            offset += 8;
            ulong theoreticalPriceRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.TheoreticalPrice = (theoreticalPriceRaw >> 63) != 0 ? -(long)(theoreticalPriceRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)theoreticalPriceRaw;
            offset += 8;
            ulong volatilityRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.Volatility = (volatilityRaw >> 63) != 0 ? -(long)(volatilityRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)volatilityRaw;
            offset += 8;
            ulong upperDynamicPBLimitRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.UpperDynamicPBLimit = (upperDynamicPBLimitRaw >> 63) != 0 ? -(long)(upperDynamicPBLimitRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)upperDynamicPBLimitRaw;
            offset += 8;
            ulong lowerDynamicPBLimitRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.LowerDynamicPBLimit = (lowerDynamicPBLimitRaw >> 63) != 0 ? -(long)(lowerDynamicPBLimitRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)lowerDynamicPBLimitRaw;
            offset += 8;
            ulong upperStaticPBLimitRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.UpperStaticPBLimit = (upperStaticPBLimitRaw >> 63) != 0 ? -(long)(upperStaticPBLimitRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)upperStaticPBLimitRaw;
            offset += 8;
            ulong lowerStaticPBLimitRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.LowerStaticPBLimit = (lowerStaticPBLimitRaw >> 63) != 0 ? -(long)(lowerStaticPBLimitRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)lowerStaticPBLimitRaw;
            offset += 8;
            ulong upperDynamicCBLimitRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.UpperDynamicCBLimit = (upperDynamicCBLimitRaw >> 63) != 0 ? -(long)(upperDynamicCBLimitRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)upperDynamicCBLimitRaw;
            offset += 8;
            ulong lowerDynamicCBLimitRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.LowerDynamicCBLimit = (lowerDynamicCBLimitRaw >> 63) != 0 ? -(long)(lowerDynamicCBLimitRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)lowerDynamicCBLimitRaw;
            offset += 8;
            ulong upperStaticCBLimitRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.UpperStaticCBLimit = (upperStaticCBLimitRaw >> 63) != 0 ? -(long)(upperStaticCBLimitRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)upperStaticCBLimitRaw;
            offset += 8;
            ulong lowerStaticCBLimitRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.LowerStaticCBLimit = (lowerStaticCBLimitRaw >> 63) != 0 ? -(long)(lowerStaticCBLimitRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)lowerStaticCBLimitRaw;
            offset += 8;
            msg.OpenInterest = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            offset += 8;
        }

        public static void Encode(MitchExtendedStatistics msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.HighPrice < 0 ? (ulong)(-msg.HighPrice) | 0x8000000000000000UL : (ulong)msg.HighPrice);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.LowPrice < 0 ? (ulong)(-msg.LowPrice) | 0x8000000000000000UL : (ulong)msg.LowPrice);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.Vwap < 0 ? (ulong)(-msg.Vwap) | 0x8000000000000000UL : (ulong)msg.Vwap);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Volume);
            offset += 4;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.Turnover < 0 ? (ulong)(-msg.Turnover) | 0x8000000000000000UL : (ulong)msg.Turnover);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.NumberOfTrades);
            offset += 4;
            msg.Reserved1.CopyTo(data.Slice(offset, 8));
            offset += 8;
            data[offset++] = (byte)msg.SubBook;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.NotionalExposure < 0 ? (ulong)(-msg.NotionalExposure) | 0x8000000000000000UL : (ulong)msg.NotionalExposure);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.NotionalDeltaExposure < 0 ? (ulong)(-msg.NotionalDeltaExposure) | 0x8000000000000000UL : (ulong)msg.NotionalDeltaExposure);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.TheoreticalPrice < 0 ? (ulong)(-msg.TheoreticalPrice) | 0x8000000000000000UL : (ulong)msg.TheoreticalPrice);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.Volatility < 0 ? (ulong)(-msg.Volatility) | 0x8000000000000000UL : (ulong)msg.Volatility);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.UpperDynamicPBLimit < 0 ? (ulong)(-msg.UpperDynamicPBLimit) | 0x8000000000000000UL : (ulong)msg.UpperDynamicPBLimit);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.LowerDynamicPBLimit < 0 ? (ulong)(-msg.LowerDynamicPBLimit) | 0x8000000000000000UL : (ulong)msg.LowerDynamicPBLimit);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.UpperStaticPBLimit < 0 ? (ulong)(-msg.UpperStaticPBLimit) | 0x8000000000000000UL : (ulong)msg.UpperStaticPBLimit);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.LowerStaticPBLimit < 0 ? (ulong)(-msg.LowerStaticPBLimit) | 0x8000000000000000UL : (ulong)msg.LowerStaticPBLimit);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.UpperDynamicCBLimit < 0 ? (ulong)(-msg.UpperDynamicCBLimit) | 0x8000000000000000UL : (ulong)msg.UpperDynamicCBLimit);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.LowerDynamicCBLimit < 0 ? (ulong)(-msg.LowerDynamicCBLimit) | 0x8000000000000000UL : (ulong)msg.LowerDynamicCBLimit);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.UpperStaticCBLimit < 0 ? (ulong)(-msg.UpperStaticCBLimit) | 0x8000000000000000UL : (ulong)msg.UpperStaticCBLimit);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.LowerStaticCBLimit < 0 ? (ulong)(-msg.LowerStaticCBLimit) | 0x8000000000000000UL : (ulong)msg.LowerStaticCBLimit);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.OpenInterest);
            offset += 8;
        }
    }
}
