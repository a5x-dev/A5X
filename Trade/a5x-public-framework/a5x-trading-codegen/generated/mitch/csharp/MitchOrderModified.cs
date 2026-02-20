using System;
using System.Numerics;
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
        public BigInteger OrderId { get; set; }

        /// <summary>
        /// New displayed quantity of the order
        /// </summary>
        public uint NewQuantity { get; set; }

        /// <summary>
        /// New limit price of the order
        /// </summary>
        public long NewPrice { get; set; }

        public decimal NewPriceAsDecimal => NewPrice / 10000m;

        /// <summary>
        /// Displayed quantity of the order
        /// </summary>
        public uint OldQuantity { get; set; }

        /// <summary>
        /// Limit price of the order
        /// </summary>
        public long OldPrice { get; set; }

        public decimal OldPriceAsDecimal => OldPrice / 10000m;

        /// <summary>
        /// Bit 0: Priority Flag (0 = Priority Lost, 1 = Priority Retained)
        /// </summary>
        public object Flags { get; set; }

        public static void Parse(MitchOrderModified msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.OrderId = new BigInteger(data.Slice(offset, 8));
            offset += 8;
            msg.NewQuantity = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.NewPrice = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.OldQuantity = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.OldPrice = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
        }

        public static void Encode(MitchOrderModified msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            msg.OrderId.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.NewQuantity);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.NewPrice);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.OldQuantity);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.OldPrice);
            offset += 8;
        }
    }
}
