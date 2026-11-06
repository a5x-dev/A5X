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
        public string ClientOrderId { get; set; }

        /// <summary>3 = Firm Interest for Instrument, 4 = Firm Interest for Segment, 7 = All Interest for Client, 8 = All Interest for Firm, 9 = Client Interest for Instrument, 14 = Client Interest for Underlying, 15 = Client Interest for Segment, 22 = Firm Interest for Underlying</summary>
        public byte MassCancelType { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>Identifier of the segment</summary>
        public string Segment { get; set; }

        /// <summary>0 = Order, 3 = Quote</summary>
        public byte InterestType { get; set; }

        /// <summary>1 = Regular</summary>
        public byte OrderBook { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public string SenderLocation { get; set; }

        /// <summary>Entering Trader ID</summary>
        public string EnteringTrader { get; set; }

        /// <summary>Desk ID through which the request is placed from</summary>
        public string DeskId { get; set; }

        public static void Parse(NativeMassCancelRequest msg, Span<byte> data)
        {
            msg.ClientOrderId = Encoding.ASCII.GetString(data.Slice(0, 10)).TrimEnd('\0');
            msg.MassCancelType = data[10];
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(11, 4));
            msg.Segment = Encoding.ASCII.GetString(data.Slice(15, 10)).TrimEnd('\0');
            msg.InterestType = data[25];
            msg.OrderBook = data[26];
            msg.SenderLocation = Encoding.ASCII.GetString(data.Slice(27, 10)).TrimEnd('\0');
            msg.EnteringTrader = Encoding.ASCII.GetString(data.Slice(37, 15)).TrimEnd('\0');
            msg.DeskId = Encoding.ASCII.GetString(data.Slice(52, 15)).TrimEnd('\0');
        }

        public static void Encode(NativeMassCancelRequest msg, Span<byte> data)
        {
            Encoding.ASCII.GetBytes((msg.ClientOrderId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(0, 10));
            data[10] = msg.MassCancelType;
            BitConverter.TryWriteBytes(data.Slice(11, 4), msg.InstrumentId);
            Encoding.ASCII.GetBytes((msg.Segment ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(15, 10));
            data[25] = msg.InterestType;
            data[26] = msg.OrderBook;
            Encoding.ASCII.GetBytes((msg.SenderLocation ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(27, 10));
            Encoding.ASCII.GetBytes((msg.EnteringTrader ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(37, 15));
            Encoding.ASCII.GetBytes((msg.DeskId ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(52, 15));
        }
    }
}
