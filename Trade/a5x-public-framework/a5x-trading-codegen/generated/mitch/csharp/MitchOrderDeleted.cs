using System;
using System.Numerics;
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
        public BigInteger OrderId { get; set; }

        /// <summary>
        /// Displayed quantity of the order
        /// </summary>
        public uint OldQuantity { get; set; }

        /// <summary>
        /// Limit price of the order
        /// </summary>
        public long OldPrice { get; set; }

        public decimal OldPriceAsDecimal => OldPrice / 10000m;

        public static void Parse(MitchOrderDeleted msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.OrderId = new BigInteger(data.Slice(offset, 8));
            offset += 8;
            msg.OldQuantity = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.OldPrice = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
        }

        public static void Encode(MitchOrderDeleted msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            msg.OrderId.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.OldQuantity);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.OldPrice);
            offset += 8;
        }
    }
}
