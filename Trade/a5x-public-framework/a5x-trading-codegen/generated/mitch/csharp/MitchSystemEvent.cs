using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent to indicate the start and end of the day
    /// </summary>
    public class MitchSystemEvent
    {
        /// <summary>
        /// Nanoseconds offset from the last Time message
        /// </summary>
        public uint Nanosecond { get; set; }

        /// <summary>
        /// C = End of Day, O = Start of Day
        /// </summary>
        public byte EventCode { get; set; }

        public static void Parse(MitchSystemEvent msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.EventCode = data[offset++];
        }

        public static void Encode(MitchSystemEvent msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            data[offset++] = (byte)msg.EventCode;
        }
    }
}
