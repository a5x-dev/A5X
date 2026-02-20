using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent by client to logout
    /// </summary>
    public class MitchLogoutRequest
    {
        public static void Parse(MitchLogoutRequest msg, Span<byte> data)
        {
            int offset = 0;
        }

        public static void Encode(MitchLogoutRequest msg, Span<byte> data)
        {
            int offset = 0;
        }
    }
}
