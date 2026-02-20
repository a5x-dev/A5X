using System;
using System.Numerics;
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
        public object SubBook { get; set; }

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

        public decimal PriceAsDecimal => Price / 10000m;

        /// <summary>
        /// Cumulative visible size at best price
        /// </summary>
        public uint Quantity { get; set; }

        /// <summary>
        /// Cumulative visible size of market orders
        /// </summary>
        public uint MarketOrderQuantity { get; set; }

        /// <summary>
        /// Reserved for future use
        /// </summary>
        public byte[] ReservedField = new byte[2] { get; set; }

        /// <summary>
        /// Cumulative visible orders at Best price. This will be set to zero on a delete action
        /// </summary>
        public uint Splits { get; set; }

        public static void Parse(MitchTopOfBook msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.Action = data[offset++];
            msg.Side = data[offset++];
            msg.Price = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.Quantity = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.MarketOrderQuantity = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            data.Slice(offset, 2).CopyTo(msg.ReservedField);
            offset += 2;
            msg.Splits = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
        }

        public static void Encode(MitchTopOfBook msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            data[offset++] = (byte)msg.Action;
            data[offset++] = (byte)msg.Side;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.Price);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Quantity);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.MarketOrderQuantity);
            offset += 4;
            msg.ReservedField.CopyTo(data.Slice(offset, 2));
            offset += 2;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Splits);
            offset += 4;
        }
    }
}
