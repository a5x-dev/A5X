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
        public byte[] ClientOrderId { get; set; }

        /// <summary>Client specified identifier of the order or quote being cancelled</summary>
        public byte[] OrigClientOrderId { get; set; }

        /// <summary>Server specified identifier of the order being cancelled</summary>
        public byte[] OrderId { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>1 = Buy, 2 = Sell</summary>
        public byte Side { get; set; }

        /// <summary>1 = Regular</summary>
        public byte OrderBook { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public byte[] SenderLocation { get; set; }

        public static void Parse(NativeOrderCancelRequest msg, Span<byte> data)
        {
            msg.ClientOrderId = data.Slice(0, 10).ToArray();
            msg.OrigClientOrderId = data.Slice(10, 10).ToArray();
            msg.OrderId = data.Slice(20, 12).ToArray();
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(32, 4));
            msg.Side = data[36];
            msg.OrderBook = data[37];
            msg.SenderLocation = data.Slice(38, 10).ToArray();
        }

        public static void Encode(NativeOrderCancelRequest msg, Span<byte> data)
        {
            msg.ClientOrderId.CopyTo(data.Slice(0, 10));
            msg.OrigClientOrderId.CopyTo(data.Slice(10, 10));
            msg.OrderId.CopyTo(data.Slice(20, 12));
            BitConverter.TryWriteBytes(data.Slice(32, 4), msg.InstrumentId);
            data[36] = msg.Side;
            data[37] = msg.OrderBook;
            msg.SenderLocation.CopyTo(data.Slice(38, 10));
        }
    }
}
