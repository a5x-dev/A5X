using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Order cancel request
    /// Message Type: F
    /// </summary>
    public class NativeOrderCancelRequest
    {
        /// <summary>Client specified identifier of the request</summary>
        public string ClientOrderId { get; set; }

        /// <summary>Client specified identifier of the order or quote being cancelled</summary>
        public string OrigClientOrderId { get; set; }

        /// <summary>Server specified identifier of the order being cancelled</summary>
        public string OrderId { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>1 = Buy, 2 = Sell</summary>
        public byte Side { get; set; }

        /// <summary>1 = Regular</summary>
        public byte OrderBook { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public string SenderLocation { get; set; }

        public static void Parse(NativeOrderCancelRequest msg, Span<byte> data)
        {
            msg.ClientOrderId = Encoding.ASCII.GetString(data.Slice(0, 10)).TrimEnd('\0');
            msg.OrigClientOrderId = Encoding.ASCII.GetString(data.Slice(10, 10)).TrimEnd('\0');
            msg.OrderId = Encoding.ASCII.GetString(data.Slice(20, 12)).TrimEnd('\0');
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(32, 4));
            msg.Side = data[36];
            msg.OrderBook = data[37];
            msg.SenderLocation = Encoding.ASCII.GetString(data.Slice(38, 10)).TrimEnd('\0');
        }

        public static void Encode(NativeOrderCancelRequest msg, Span<byte> data)
        {
            Encoding.ASCII.GetBytes((msg.ClientOrderId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(0, 10));
            Encoding.ASCII.GetBytes((msg.OrigClientOrderId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(10, 10));
            Encoding.ASCII.GetBytes((msg.OrderId ?? string.Empty).PadRight(12, '\0')).AsSpan(0, 12).CopyTo(data.Slice(20, 12));
            BitConverter.TryWriteBytes(data.Slice(32, 4), msg.InstrumentId);
            data[36] = msg.Side;
            data[37] = msg.OrderBook;
            Encoding.ASCII.GetBytes((msg.SenderLocation ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(38, 10));
        }
    }
}
