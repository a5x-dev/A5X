using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Mass Cancel Report
    /// Message Type: r
    /// </summary>
    public class NativeMassCancelReport
    {
        /// <summary>Identity of the matching partition</summary>
        public byte PartitionId { get; set; }

        /// <summary>Message sequence number of the matching partition</summary>
        public ulong SequenceNumber { get; set; }

        /// <summary>Client specified identifier of the rejected message if available</summary>
        public byte[] ClientOrderId { get; set; }

        /// <summary>0 = Rejected, 7 = Accepted</summary>
        public byte Status { get; set; }

        /// <summary>Code specifying the reason for the reject. Ignore if Status is not Rejected (0)</summary>
        public uint RejectCode { get; set; }

        /// <summary>1 = Regular</summary>
        public byte OrderBook { get; set; }

        public static void Parse(NativeMassCancelReport msg, Span<byte> data)
        {
            msg.PartitionId = data[0];
            msg.SequenceNumber = BitConverter.ToUInt64(data.Slice(1, 8));
            msg.ClientOrderId = data.Slice(9, 10).ToArray();
            msg.Status = data[19];
            msg.RejectCode = BitConverter.ToUInt32(data.Slice(20, 4));
            msg.OrderBook = data[24];
        }

        public static void Encode(NativeMassCancelReport msg, Span<byte> data)
        {
            data[0] = msg.PartitionId;
            BitConverter.TryWriteBytes(data.Slice(1, 8), msg.SequenceNumber);
            msg.ClientOrderId.CopyTo(data.Slice(9, 10));
            data[19] = msg.Status;
            BitConverter.TryWriteBytes(data.Slice(20, 4), msg.RejectCode);
            data[24] = msg.OrderBook;
        }
    }
}
