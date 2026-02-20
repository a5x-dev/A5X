using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Logout message
    /// Message Type: 5
    /// </summary>
    public class NativeLogout
    {
        /// <summary>Reason for the logout</summary>
        public byte[] Reason { get; set; }

        public static void Parse(NativeLogout msg, Span<byte> data)
        {
            msg.Reason = data.Slice(0, 20).ToArray();
        }

        public static void Encode(NativeLogout msg, Span<byte> data)
        {
            msg.Reason.CopyTo(data.Slice(0, 20));
        }
    }
}
