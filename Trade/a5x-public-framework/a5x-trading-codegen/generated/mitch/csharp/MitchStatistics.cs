using System;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent to provide statistical information about instruments
    /// </summary>
    public class MitchStatistics
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
        /// C = Closing Price, O = Opening Price, P = Previous Close
        /// </summary>
        public byte StatisticType { get; set; }

        /// <summary>
        /// Opening or closing price
        /// </summary>
        public long Price { get; set; }

        public decimal PriceAsDecimal => Price / 10000m;

        /// <summary>
        /// A = Auction Trade, B = Regular Trade, D = Last Regular Trade, E = Last Auction, F = Manual, I = Previous Close, T = Theoretical Price, W = None, Y = Reference Price, Z = Price Unavailable
        /// </summary>
        public byte OpenCloseIndicator { get; set; }

        /// <summary>
        /// 1 = Regular
        /// </summary>
        public byte SubBook { get; set; }

        public static void Parse(MitchStatistics msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.StatisticType = data[offset++];
            msg.Price = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.OpenCloseIndicator = data[offset++];
            msg.SubBook = data[offset++];
        }

        public static void Encode(MitchStatistics msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            data[offset++] = (byte)msg.StatisticType;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.Price);
            offset += 8;
            data[offset++] = (byte)msg.OpenCloseIndicator;
            data[offset++] = (byte)msg.SubBook;
        }
    }
}
