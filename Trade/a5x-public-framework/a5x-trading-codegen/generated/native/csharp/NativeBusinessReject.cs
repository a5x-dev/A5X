using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Business Reject
    /// Message Type: j
    /// </summary>
    public class NativeBusinessReject
    {
        /// <summary>Identity of the matching partition</summary>
        public byte PartitionId { get; set; }

        /// <summary>Message sequence number of the matching partition</summary>
        public ulong SequenceNumber { get; set; }

        /// <summary>Code specifying the reason for the reject. 9000 = Unknown instrument, 9998 = Matching partition suspended, 9999 = System suspended</summary>
        public uint RejectCode { get; set; }

        /// <summary>Client specified identifier of the rejected message if available</summary>
        public byte[] ClientOrderId { get; set; }

        public static void Parse(NativeBusinessReject msg, Span<byte> data)
        {
            msg.PartitionId = data[0];
            msg.SequenceNumber = BitConverter.ToUInt64(data.Slice(1, 8));
            msg.RejectCode = BitConverter.ToUInt32(data.Slice(9, 4));
            msg.ClientOrderId = data.Slice(13, 10).ToArray();
        }

        public static void Encode(NativeBusinessReject msg, Span<byte> data)
        {
            data[0] = msg.PartitionId;
            BitConverter.TryWriteBytes(data.Slice(1, 8), msg.SequenceNumber);
            BitConverter.TryWriteBytes(data.Slice(9, 4), msg.RejectCode);
            msg.ClientOrderId.CopyTo(data.Slice(13, 10));
        }
    }
}
