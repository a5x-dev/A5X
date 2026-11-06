using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// System status
    /// Message Type: n
    /// </summary>
    public class NativeSystemStatus
    {
        /// <summary>Identity of the matching partition the message relates to</summary>
        public byte PartitionId { get; set; }

        /// <summary>1 = Recovery Service Resumed, 2 = Recovery Service Unavailable, 3 = Partition Suspended</summary>
        public byte Status { get; set; }

        public static void Parse(NativeSystemStatus msg, Span<byte> data)
        {
            msg.PartitionId = data[0];
            msg.Status = data[1];
        }

        public static void Encode(NativeSystemStatus msg, Span<byte> data)
        {
            data[0] = msg.PartitionId;
            data[1] = msg.Status;
        }
    }
}
