using System;
using System.Buffers.Binary;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent when a visible order is deleted from the order book
    /// </summary>
    public class MitchOrderDeleted
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
        /// Displayed quantity of the order
        /// </summary>
        public uint OldQuantity { get; set; }

        /// <summary>
        /// Limit price of the order
        /// </summary>
        public long OldPrice { get; set; }

        public decimal OldPriceAsDecimal => OldPrice / 100000000m;

        public static void Parse(MitchOrderDeleted msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            msg.OrderId = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            offset += 8;
            msg.OldQuantity = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            ulong oldPriceRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.OldPrice = (oldPriceRaw >> 63) != 0 ? -(long)(oldPriceRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)oldPriceRaw;
            offset += 8;
        }

        public static void Encode(MitchOrderDeleted msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.OrderId);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.OldQuantity);
            offset += 4;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.OldPrice < 0 ? (ulong)(-msg.OldPrice) | 0x8000000000000000UL : (ulong)msg.OldPrice);
            offset += 8;
        }
    }
}
