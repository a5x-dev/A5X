using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Missed message request
    /// Message Type: M
    /// </summary>
    public class NativeMissedMessageRequest
    {
        /// <summary>Identity of the matching partition the request relates to</summary>
        public byte PartitionId { get; set; }

        /// <summary>Sequence number immediately after that of the last message received from the partition</summary>
        public ulong SequenceNumber { get; set; }

        public static void Parse(NativeMissedMessageRequest msg, Span<byte> data)
        {
            msg.PartitionId = data[0];
            msg.SequenceNumber = BitConverter.ToUInt64(data.Slice(1, 8));
        }

        public static void Encode(NativeMissedMessageRequest msg, Span<byte> data)
        {
            data[0] = msg.PartitionId;
            BitConverter.TryWriteBytes(data.Slice(1, 8), msg.SequenceNumber);
        }
    }
}
