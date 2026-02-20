using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent to disseminate news and announcements
    /// </summary>
    public class MitchNews
    {
        /// <summary>
        /// Nanoseconds offset from the last Time message
        /// </summary>
        public uint Nanosecond { get; set; }

        /// <summary>
        /// Time the announcement was published (EPOCH)
        /// </summary>
        public BigInteger Time { get; set; }

        /// <summary>
        /// 0 = Regular, 1 = High Priority, 2 = Low Priority
        /// </summary>
        public byte Urgency { get; set; }

        /// <summary>
        /// Headline or subject of announcement
        /// </summary>
        public string Headline { get; set; }

        /// <summary>
        /// Text of the announcement
        /// </summary>
        public string Text { get; set; }

        /// <summary>
        /// Pipe separated list of Instrument IDs
        /// </summary>
        public string InstrumentIds { get; set; }

        /// <summary>
        /// Pipe separated list of Underlying Instrument IDs
        /// </summary>
        public string UnderlyingInstrumentIds { get; set; }

        public static void Parse(MitchNews msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.Time = new BigInteger(data.Slice(offset, 8));
            offset += 8;
            msg.Urgency = data[offset++];
            msg.Headline = Encoding.ASCII.GetString(data.Slice(offset, 100)).TrimEnd();
            offset += 100;
            msg.Text = Encoding.ASCII.GetString(data.Slice(offset, 750)).TrimEnd();
            offset += 750;
            msg.InstrumentIds = Encoding.ASCII.GetString(data.Slice(offset, 100)).TrimEnd();
            offset += 100;
            msg.UnderlyingInstrumentIds = Encoding.ASCII.GetString(data.Slice(offset, 100)).TrimEnd();
            offset += 100;
        }

        public static void Encode(MitchNews msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            msg.Time.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
            data[offset++] = (byte)msg.Urgency;
            var headlineBytes = Encoding.ASCII.GetBytes(msg.Headline.PadRight(100));
            headlineBytes.AsSpan(0, 100).CopyTo(data.Slice(offset, 100));
            offset += 100;
            var textBytes = Encoding.ASCII.GetBytes(msg.Text.PadRight(750));
            textBytes.AsSpan(0, 750).CopyTo(data.Slice(offset, 750));
            offset += 750;
            var instrumentIdsBytes = Encoding.ASCII.GetBytes(msg.InstrumentIds.PadRight(100));
            instrumentIdsBytes.AsSpan(0, 100).CopyTo(data.Slice(offset, 100));
            offset += 100;
            var underlyingInstrumentIdsBytes = Encoding.ASCII.GetBytes(msg.UnderlyingInstrumentIds.PadRight(100));
            underlyingInstrumentIdsBytes.AsSpan(0, 100).CopyTo(data.Slice(offset, 100));
            offset += 100;
        }
    }
}
