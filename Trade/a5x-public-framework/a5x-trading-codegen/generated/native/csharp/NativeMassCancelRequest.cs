using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Mass Cancel Request
    /// Message Type: q
    /// </summary>
    public class NativeMassCancelRequest
    {
        /// <summary>Client specified identifier of the rejected message if available</summary>
        public byte[] ClientOrderId { get; set; }

        /// <summary>3 = Firm Interest for Instrument, 4 = Firm Interest for Segment, 7 = All Interest for Client, 8 = All Interest for Firm, 9 = Client Interest for Instrument, 14 = Client Interest for Underlying, 15 = Client Interest for Segment, 22 = Firm Interest for Underlying</summary>
        public byte MassCancelType { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>Identifier of the segment</summary>
        public byte[] Segment { get; set; }

        /// <summary>0 = Order, 3 = Quote</summary>
        public byte InterestType { get; set; }

        /// <summary>1 = Regular</summary>
        public byte OrderBook { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public byte[] SenderLocation { get; set; }

        public static void Parse(NativeMassCancelRequest msg, Span<byte> data)
        {
            msg.ClientOrderId = data.Slice(0, 10).ToArray();
            msg.MassCancelType = data[10];
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(11, 4));
            msg.Segment = data.Slice(15, 10).ToArray();
            msg.InterestType = data[25];
            msg.OrderBook = data[26];
            msg.SenderLocation = data.Slice(27, 10).ToArray();
        }

        public static void Encode(NativeMassCancelRequest msg, Span<byte> data)
        {
            msg.ClientOrderId.CopyTo(data.Slice(0, 10));
            data[10] = msg.MassCancelType;
            BitConverter.TryWriteBytes(data.Slice(11, 4), msg.InstrumentId);
            msg.Segment.CopyTo(data.Slice(15, 10));
            data[25] = msg.InterestType;
            data[26] = msg.OrderBook;
            msg.SenderLocation.CopyTo(data.Slice(27, 10));
        }
    }
}
