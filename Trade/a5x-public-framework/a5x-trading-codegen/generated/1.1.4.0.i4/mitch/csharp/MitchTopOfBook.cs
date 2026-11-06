using System;
using System.Buffers.Binary;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent to provide top of book information
    /// </summary>
    public class MitchTopOfBook
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
        /// 1 = Update, 2 = Delete
        /// </summary>
        public byte Action { get; set; }

        /// <summary>
        /// B = Buy, S = Sell
        /// </summary>
        public byte Side { get; set; }

        /// <summary>
        /// Best price for the particular side
        /// </summary>
        public long Price { get; set; }

        public decimal PriceAsDecimal => Price / 100000000m;

        /// <summary>
        /// Cumulative visible size at best price. This will be set to zero on a delete action
        /// </summary>
        public uint Quantity { get; set; }

        /// <summary>
        /// Cumulative visible size of market orders
        /// </summary>
        public uint MarketOrderQuantity { get; set; }

        /// <summary>
        /// Reserved for future use.
        /// </summary>
        public byte[] Reserved1 { get; set; } = new byte[2];

        /// <summary>
        /// Cumulative visible orders at Best price. This will be set to zero on a delete action
        /// </summary>
        public uint Splits { get; set; }

        /// <summary>
        /// Order ID of the order that last impacted the top of book
        /// </summary>
        public ulong OrderId { get; set; }

        public static void Parse(MitchTopOfBook msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            msg.InstrumentId = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            msg.SubBook = data[offset++];
            msg.Action = data[offset++];
            msg.Side = data[offset++];
            ulong priceRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.Price = (priceRaw >> 63) != 0 ? -(long)(priceRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)priceRaw;
            offset += 8;
            msg.Quantity = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            msg.MarketOrderQuantity = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            data.Slice(offset, 2).CopyTo(msg.Reserved1);
            offset += 2;
            msg.Splits = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            msg.OrderId = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            offset += 8;
        }

        public static void Encode(MitchTopOfBook msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            data[offset++] = (byte)msg.SubBook;
            data[offset++] = (byte)msg.Action;
            data[offset++] = (byte)msg.Side;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.Price < 0 ? (ulong)(-msg.Price) | 0x8000000000000000UL : (ulong)msg.Price);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Quantity);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.MarketOrderQuantity);
            offset += 4;
            msg.Reserved1.CopyTo(data.Slice(offset, 2));
            offset += 2;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Splits);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.OrderId);
            offset += 8;
        }
    }
}
