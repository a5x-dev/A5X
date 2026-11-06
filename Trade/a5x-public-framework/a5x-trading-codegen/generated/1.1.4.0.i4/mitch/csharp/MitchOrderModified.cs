using System;
using System.Buffers.Binary;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent when a visible order is modified in the order book
    /// </summary>
    public class MitchOrderModified
    {
        /// <summary>
        /// Nanoseconds offset from the last Time message
        /// </summary>
        public uint Nanosecond { get; set; }

        /// <summary>
        /// Unique identifier of the order
        /// </summary>
        public ulong OrderId { get; set; }

        /// <summary>
        /// New displayed quantity of the order
        /// </summary>
        public uint NewQuantity { get; set; }

        /// <summary>
        /// New limit price of the order
        /// </summary>
        public long NewPrice { get; set; }

        public decimal NewPriceAsDecimal => NewPrice / 100000000m;

        /// <summary>
        /// Displayed quantity of the order
        /// </summary>
        public uint OldQuantity { get; set; }

        /// <summary>
        /// Limit price of the order
        /// </summary>
        public long OldPrice { get; set; }

        public decimal OldPriceAsDecimal => OldPrice / 100000000m;

        /// <summary>
        /// Bit 0: Priority Flag (0 = Priority Lost, 1 = Priority Retained)
        /// </summary>
        public byte Flags { get; set; }

        public static void Parse(MitchOrderModified msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            msg.OrderId = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            offset += 8;
            msg.NewQuantity = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            ulong newPriceRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.NewPrice = (newPriceRaw >> 63) != 0 ? -(long)(newPriceRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)newPriceRaw;
            offset += 8;
            msg.OldQuantity = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            ulong oldPriceRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.OldPrice = (oldPriceRaw >> 63) != 0 ? -(long)(oldPriceRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)oldPriceRaw;
            offset += 8;
            msg.Flags = data[offset++];
        }

        public static void Encode(MitchOrderModified msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.OrderId);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.NewQuantity);
            offset += 4;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.NewPrice < 0 ? (ulong)(-msg.NewPrice) | 0x8000000000000000UL : (ulong)msg.NewPrice);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.OldQuantity);
            offset += 4;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.OldPrice < 0 ? (ulong)(-msg.OldPrice) | 0x8000000000000000UL : (ulong)msg.OldPrice);
            offset += 8;
            data[offset++] = (byte)msg.Flags;
        }
    }
}
