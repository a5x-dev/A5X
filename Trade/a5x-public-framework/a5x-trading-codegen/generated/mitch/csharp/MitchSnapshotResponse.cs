using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent by server in response to snapshot request
    /// </summary>
    public class MitchSnapshotResponse
    {
        /// <summary>
        /// Sequence number with which the snapshot is synchronized. This will be zero if Status is not 'A'. Ignore if Snapshot Type is not Order Book (0)
        /// </summary>
        public BigInteger SequenceNumber { get; set; }

        /// <summary>
        /// Number of orders that will be transmitted in the snapshot. This will be zero if Order Book is empty or Status is not 'A'. However, if the Snapshot Request was submitted for multiple order books, this field will be set to zero. Ignore if Snapshot Type is not Order Book (0)
        /// </summary>
        public uint OrderCount { get; set; }

        /// <summary>
        /// A = Request Accepted, O = Out of Range, U = Snapshot Unavailable, a = Segment/Symbol/Sub Book Invalid or Not Specified, b = Request Limit Reached, c = Concurrent Limit Reached, d = Unsupported Message Type, e = Failed (Other)
        /// </summary>
        public byte Status { get; set; }

        /// <summary>
        /// 0=Order Book, 1=Symbol Status, 2=Instrument, 3=Trades, 4=Statistics, 5=News, 8=Top of Book
        /// </summary>
        public byte SnapshotType { get; set; }

        /// <summary>
        /// Identifier, if any, of Snapshot Request
        /// </summary>
        public uint RequestId { get; set; }

        public static void Parse(MitchSnapshotResponse msg, Span<byte> data)
        {
            int offset = 0;
            msg.SequenceNumber = new BigInteger(data.Slice(offset, 8));
            offset += 8;
            msg.OrderCount = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.Status = data[offset++];
            msg.SnapshotType = data[offset++];
            msg.RequestId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
        }

        public static void Encode(MitchSnapshotResponse msg, Span<byte> data)
        {
            int offset = 0;
            msg.SequenceNumber.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.OrderCount);
            offset += 4;
            data[offset++] = (byte)msg.Status;
            data[offset++] = (byte)msg.SnapshotType;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.RequestId);
            offset += 4;
        }
    }
}
