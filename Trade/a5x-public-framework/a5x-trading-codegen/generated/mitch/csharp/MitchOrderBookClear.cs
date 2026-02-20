using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent to indicate that the order book for an instrument should be cleared
    /// </summary>
    public class MitchOrderBookClear
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
        /// 1 = Regular, 2 = Off-Book, 9 = Bulletin Board, 11 = Negotiated Trades
        /// </summary>
        public byte SubBook { get; set; }

        /// <summary>
        /// 0 = MBP/MBO/Statistics, 1 = Top of Book
        /// </summary>
        public byte BookType { get; set; }

        public static void Parse(MitchOrderBookClear msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.SubBook = data[offset++];
            msg.BookType = data[offset++];
        }

        public static void Encode(MitchOrderBookClear msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            data[offset++] = (byte)msg.SubBook;
            data[offset++] = (byte)msg.BookType;
        }
    }
}
