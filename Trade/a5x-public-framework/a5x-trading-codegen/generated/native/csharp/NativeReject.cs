using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Reject message
    /// Message Type: 3
    /// </summary>
    public class NativeReject
    {
        /// <summary>Code specifying the reason for the reject</summary>
        public int RejectCode { get; set; }

        /// <summary>Reason for the reject</summary>
        public byte[] RejectReason { get; set; }

        /// <summary>Type of message rejected</summary>
        public byte RefMessageType { get; set; }

        /// <summary>Client specified identifier of the rejected message if available</summary>
        public byte[] ClientOrderId { get; set; }

        public static void Parse(NativeReject msg, Span<byte> data)
        {
            msg.RejectCode = BitConverter.ToInt32(data.Slice(0, 4));
            msg.RejectReason = data.Slice(4, 30).ToArray();
            msg.RefMessageType = data[34];
            msg.ClientOrderId = data.Slice(35, 10).ToArray();
        }

        public static void Encode(NativeReject msg, Span<byte> data)
        {
            BitConverter.TryWriteBytes(data.Slice(0, 4), msg.RejectCode);
            msg.RejectReason.CopyTo(data.Slice(4, 30));
            data[34] = msg.RefMessageType;
            msg.ClientOrderId.CopyTo(data.Slice(35, 10));
        }
    }
}
