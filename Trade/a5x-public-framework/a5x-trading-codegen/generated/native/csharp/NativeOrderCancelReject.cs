using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Order Cancel Reject
    /// Message Type: 9
    /// </summary>
    public class NativeOrderCancelReject
    {
        /// <summary>Identity of the matching partition</summary>
        public byte PartitionId { get; set; }

        /// <summary>Message sequence number of the matching partition</summary>
        public ulong SequenceNumber { get; set; }

        /// <summary>Client specified identifier of the rejected cancel or modification request</summary>
        public string ClientOrderId { get; set; }

        /// <summary>Code specifying the reason for the reject</summary>
        public int RejectCode { get; set; }

        /// <summary>1 = Regular</summary>
        public byte OrderBook { get; set; }

        /// <summary>Time the message was generated in epoch format</summary>
        public ulong TransactTime { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public string SenderLocation { get; set; }

        public static void Parse(NativeOrderCancelReject msg, Span<byte> data)
        {
            msg.PartitionId = data[0];
            msg.SequenceNumber = BitConverter.ToUInt64(data.Slice(1, 8));
            msg.ClientOrderId = Encoding.ASCII.GetString(data.Slice(9, 10)).TrimEnd('\0');
            msg.RejectCode = BitConverter.ToInt32(data.Slice(19, 4));
            msg.OrderBook = data[23];
            msg.TransactTime = BitConverter.ToUInt64(data.Slice(24, 8));
            msg.SenderLocation = Encoding.ASCII.GetString(data.Slice(32, 10)).TrimEnd('\0');
        }

        public static void Encode(NativeOrderCancelReject msg, Span<byte> data)
        {
            data[0] = msg.PartitionId;
            BitConverter.TryWriteBytes(data.Slice(1, 8), msg.SequenceNumber);
            Encoding.ASCII.GetBytes((msg.ClientOrderId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(9, 10));
            BitConverter.TryWriteBytes(data.Slice(19, 4), msg.RejectCode);
            data[23] = msg.OrderBook;
            BitConverter.TryWriteBytes(data.Slice(24, 8), msg.TransactTime);
            Encoding.ASCII.GetBytes((msg.SenderLocation ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(32, 10));
        }
    }
}
