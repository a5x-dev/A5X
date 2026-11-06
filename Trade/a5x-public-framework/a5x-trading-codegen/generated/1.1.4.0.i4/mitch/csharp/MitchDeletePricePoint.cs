using System;
using System.Buffers.Binary;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent when a price level is removed from an aggregated (price point) order book
    /// </summary>
    public class MitchDeletePricePoint
    {
        /// <summary>
        /// Nanoseconds offset from the last Time message
        /// </summary>
        public uint Nanosecond { get; set; }

        /// <summary>
        /// Numeric Identifier of the instrument
        /// </summary>
        public uint InstrumentId { get; set; }

        /// <summary>
        /// Bit 0: Regular (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes)
        /// </summary>
        public byte SubBook { get; set; }

        /// <summary>
        /// B = Buy, S = Sell
        /// </summary>
        public byte Side { get; set; }

        /// <summary>
        /// Position of the price level that is removed, counting from the best price
        /// </summary>
        public ushort PriceLevel { get; set; }

        /// <summary>
        /// Reserved for future use.
        /// </summary>
        public byte[] Reserved1 { get; set; } = new byte[1];

        /// <summary>
        /// Reserved for future use.
        /// </summary>
        public string Reserved2 { get; set; }

        public static void Parse(MitchDeletePricePoint msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            msg.InstrumentId = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            msg.SubBook = data[offset++];
            msg.Side = data[offset++];
            msg.PriceLevel = BinaryPrimitives.ReadUInt16LittleEndian(data.Slice(offset, 2));
            offset += 2;
            data.Slice(offset, 1).CopyTo(msg.Reserved1);
            offset += 1;
            msg.Reserved2 = Encoding.ASCII.GetString(data.Slice(offset, 10)).TrimEnd();
            offset += 10;
        }

        public static void Encode(MitchDeletePricePoint msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            data[offset++] = (byte)msg.SubBook;
            data[offset++] = (byte)msg.Side;
            BitConverter.TryWriteBytes(data.Slice(offset, 2), msg.PriceLevel);
            offset += 2;
            msg.Reserved1.CopyTo(data.Slice(offset, 1));
            offset += 1;
            var reserved2Bytes = Encoding.ASCII.GetBytes((msg.Reserved2 ?? string.Empty).PadRight(10));
            reserved2Bytes.AsSpan(0, 10).CopyTo(data.Slice(offset, 10));
            offset += 10;
        }
    }
}
