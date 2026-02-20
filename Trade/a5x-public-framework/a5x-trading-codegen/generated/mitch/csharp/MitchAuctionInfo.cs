using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent to provide indicative auction information during auction periods
    /// </summary>
    public class MitchAuctionInfo
    {
        /// <summary>
        /// Nanoseconds offset from the last Time message
        /// </summary>
        public uint Nanosecond { get; set; }

        /// <summary>
        /// Quantity that will be matched at the indicative price
        /// </summary>
        public uint PairedQuantity { get; set; }

        /// <summary>
        /// Quantity that is eligible to be matched at the indicative price but will not be matched
        /// </summary>
        public uint ImbalanceQuantity { get; set; }

        /// <summary>
        /// B = Buy Imbalance, N = No Imbalance, O = Insufficient Orders for Auction, S = Sell Imbalance
        /// </summary>
        public byte ImbalanceDirection { get; set; }

        /// <summary>
        /// Numeric identifier of the instrument
        /// </summary>
        public uint InstrumentId { get; set; }

        /// <summary>
        /// Indicative auction price
        /// </summary>
        public long Price { get; set; }

        public decimal PriceAsDecimal => Price / 10000m;

        /// <summary>
        /// A = Re-Opening Auction, C = Closing Auction, O = Opening Auction
        /// </summary>
        public byte AuctionType { get; set; }

        public static void Parse(MitchAuctionInfo msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.PairedQuantity = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.ImbalanceQuantity = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.ImbalanceDirection = data[offset++];
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.Price = BitConverter.ToInt64(data.Slice(offset, 8));
            offset += 8;
            msg.AuctionType = data[offset++];
        }

        public static void Encode(MitchAuctionInfo msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.PairedQuantity);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.ImbalanceQuantity);
            offset += 4;
            data[offset++] = (byte)msg.ImbalanceDirection;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.Price);
            offset += 8;
            data[offset++] = (byte)msg.AuctionType;
        }
    }
}
