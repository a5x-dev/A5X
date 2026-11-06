using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Transmission complete
    /// Message Type: P
    /// </summary>
    public class NativeTransmissionComplete
    {
        /// <summary>0 = All Messages Transmitted, 1 = Message Limit Reached, 3 = Service Unavailable</summary>
        public byte Status { get; set; }

        public static void Parse(NativeTransmissionComplete msg, Span<byte> data)
        {
            msg.Status = data[0];
        }

        public static void Encode(NativeTransmissionComplete msg, Span<byte> data)
        {
            data[0] = msg.Status;
        }
    }
}
