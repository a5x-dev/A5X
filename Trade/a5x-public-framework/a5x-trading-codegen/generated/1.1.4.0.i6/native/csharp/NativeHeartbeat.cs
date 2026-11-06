using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Heartbeat message
    /// Message Type: 0
    /// </summary>
    public class NativeHeartbeat
    {
        public static void Parse(NativeHeartbeat msg, Span<byte> data)
        {
        }

        public static void Encode(NativeHeartbeat msg, Span<byte> data)
        {
        }
    }
}
