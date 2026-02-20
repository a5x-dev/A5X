using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Missed message request acknowledgement
    /// Message Type: N
    /// </summary>
    public class NativeMissedMessageRequestAck
    {
        /// <summary>0 = Request Accepted, 1 = Request Limit Reached, 2 = Invalid Partition ID, 3 = Service Unavailable</summary>
        public byte Status { get; set; }

        public static void Parse(NativeMissedMessageRequestAck msg, Span<byte> data)
        {
            msg.Status = data[0];
        }

        public static void Encode(NativeMissedMessageRequestAck msg, Span<byte> data)
        {
            data[0] = msg.Status;
        }
    }
}
