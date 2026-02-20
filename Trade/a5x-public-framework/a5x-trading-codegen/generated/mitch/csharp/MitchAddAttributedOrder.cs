using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent when a new visible order with firm attribution is added to the order book
    /// </summary>
    public class MitchAddAttributedOrder
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
        /// B = Buy, S = Sell
        /// </summary>
        public byte Side { get; set; }

        /// <summary>
        /// Displayed quantity of the order
        /// </summary>
        public uint Quantity { get; set; }

        /// <summary>
        /// Numeric identifier of the instrument
        /// </summary>
        public uint InstrumentId { get; set; }

        /// <summary>
        /// Limit price of the order. If Market, it will be filled with spaces
        /// </summary>
        public long Price { get; set; }

        public decimal PriceAsDecimal => Price / 10000m;

        /// <summary>
        /// Identity of firm that submitted the order
        /// </summary>
        public string Firm { get; set; }

        /// <summary>
        /// Bit 4: Market Order (0 = No, 1 = Yes), Bit 5: Bulletin Board (0 = No, 1 = Yes)
        /// </summary>
        public object Flags { get; set; }

        public static void Parse(MitchAddAttributedOrder msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.OrderId = new BigInteger(data.Slice(offset, 8));
            offset += 8;
            msg.Side = data[offset++];
            msg.Quantity = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.Price = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.Firm = Encoding.ASCII.GetString(data.Slice(offset, 6)).TrimEnd();
            offset += 6;
        }

        public static void Encode(MitchAddAttributedOrder msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            msg.OrderId.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
            data[offset++] = (byte)msg.Side;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Quantity);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.Price);
            offset += 8;
            var firmBytes = Encoding.ASCII.GetBytes(msg.Firm.PadRight(6));
            firmBytes.AsSpan(0, 6).CopyTo(data.Slice(offset, 6));
            offset += 6;
        }
    }
}
