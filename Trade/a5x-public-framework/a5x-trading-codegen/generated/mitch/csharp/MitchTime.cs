using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent by the server for every second for which at least one application message is generated. This message is not transmitted during periods where no other application messages are generated
    /// </summary>
    public class MitchTime
    {
        /// <summary>
        /// Number of seconds since midnight. Midnight will be in terms of the local time for the server (i.e. not UTC)
        /// </summary>
        public uint Seconds { get; set; }

        public static void Parse(MitchTime msg, Span<byte> data)
        {
            int offset = 0;
            msg.Seconds = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
        }

        public static void Encode(MitchTime msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Seconds);
            offset += 4;
        }
    }
}
