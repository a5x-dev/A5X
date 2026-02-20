using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Logon response
    /// Message Type: B
    /// </summary>
    public class NativeLogonResponse
    {
        /// <summary>0 or 3 = Accepted, 6 = Firm suspended, 9 = Account expired, 50 = Staggered Start, 55 = Node suspended, 100 = Not logged into real-time, 626 = User suspended, 628 = User inactive, 630 = End of Day, 9903 = Concurrent limit, 9904 = Invalid gateway, 9905 = System unavailable, 9906 = Logons not allowed</summary>
        public int RejectCode { get; set; }

        /// <summary>Number of days for password expiry, ignore if negative</summary>
        public int PasswordExpiry { get; set; }

        public static void Parse(NativeLogonResponse msg, Span<byte> data)
        {
            msg.RejectCode = BitConverter.ToInt32(data.Slice(0, 4));
            msg.PasswordExpiry = BitConverter.ToInt32(data.Slice(4, 4));
        }

        public static void Encode(NativeLogonResponse msg, Span<byte> data)
        {
            BitConverter.TryWriteBytes(data.Slice(0, 4), msg.RejectCode);
            BitConverter.TryWriteBytes(data.Slice(4, 4), msg.PasswordExpiry);
        }
    }
}
