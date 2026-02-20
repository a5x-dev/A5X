using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent by server in response to login request
    /// </summary>
    public class MitchLoginResponse
    {
        /// <summary>
        /// A = Login Accepted, a = CompID Inactive/Locked, b = Login Limit Reached, c = Service Unavailable, d = Concurrent Limit Reached, e = Failed (Other)
        /// </summary>
        public byte Status { get; set; }

        public static void Parse(MitchLoginResponse msg, Span<byte> data)
        {
            int offset = 0;
            msg.Status = data[offset++];
        }

        public static void Encode(MitchLoginResponse msg, Span<byte> data)
        {
            int offset = 0;
            data[offset++] = (byte)msg.Status;
        }
    }
}
