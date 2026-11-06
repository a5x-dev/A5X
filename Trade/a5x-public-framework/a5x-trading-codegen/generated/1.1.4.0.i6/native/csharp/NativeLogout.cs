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
        public string Reason { get; set; }

        public static void Parse(NativeLogout msg, Span<byte> data)
        {
            msg.Reason = Encoding.ASCII.GetString(data.Slice(0, 20)).TrimEnd('\0');
        }

        public static void Encode(NativeLogout msg, Span<byte> data)
        {
            Encoding.ASCII.GetBytes((msg.Reason ?? string.Empty).PadRight(20, '\0')).AsSpan(0, 20).CopyTo(data.Slice(0, 20));
        }
    }
}
