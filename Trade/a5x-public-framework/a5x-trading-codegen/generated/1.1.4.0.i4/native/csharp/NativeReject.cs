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
        public string RejectReason { get; set; }

        /// <summary>Type of message rejected</summary>
        public byte RefMessageType { get; set; }

        /// <summary>Client specified identifier of the rejected message if available</summary>
        public string ClientOrderId { get; set; }

        public static void Parse(NativeReject msg, Span<byte> data)
        {
            msg.RejectCode = BitConverter.ToInt32(data.Slice(0, 4));
            msg.RejectReason = Encoding.ASCII.GetString(data.Slice(4, 30)).TrimEnd('\0');
            msg.RefMessageType = data[34];
            msg.ClientOrderId = Encoding.ASCII.GetString(data.Slice(35, 10)).TrimEnd('\0');
        }

        public static void Encode(NativeReject msg, Span<byte> data)
        {
            BitConverter.TryWriteBytes(data.Slice(0, 4), msg.RejectCode);
            Encoding.ASCII.GetBytes((msg.RejectReason ?? string.Empty).PadRight(30, '\0')).AsSpan(0, 30).CopyTo(data.Slice(4, 30));
            data[34] = msg.RefMessageType;
            Encoding.ASCII.GetBytes((msg.ClientOrderId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(35, 10));
        }
    }
}
