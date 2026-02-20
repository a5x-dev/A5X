using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// News
    /// Message Type: CB
    /// </summary>
    public class NativeNews
    {
        /// <summary>Identity of the matching partition</summary>
        public byte PartitionId { get; set; }

        /// <summary>Message sequence number of the matching partition</summary>
        public ulong SequenceNumber { get; set; }

        /// <summary>Time the announcement was published in EPOCH time</summary>
        public ulong OrigTime { get; set; }

        /// <summary>0 = Rejected, 1 = High Priority, 2 = Low Priority</summary>
        public byte Urgency { get; set; }

        /// <summary>Headline or subject of the announcement</summary>
        public byte[] Headline { get; set; }

        /// <summary>Text of the announcement</summary>
        public byte[] Text { get; set; }

        /// <summary>Pipe separated list of symbols of instruments the announcements relates to</summary>
        public byte[] Instruments { get; set; }

        /// <summary>Pipe separated list of symbols of underlyings the instruments relates to</summary>
        public byte[] Underlyings { get; set; }

        /// <summary>Pipe separated list of firms that the announcement should be sent to</summary>
        public byte[] FirmList { get; set; }

        /// <summary>Pipe separated list of users that the announcement should be sent to</summary>
        public byte[] UserList { get; set; }

        public static void Parse(NativeNews msg, Span<byte> data)
        {
            msg.PartitionId = data[0];
            msg.SequenceNumber = BitConverter.ToUInt64(data.Slice(1, 8));
            msg.OrigTime = BitConverter.ToUInt64(data.Slice(9, 8));
            msg.Urgency = data[17];
            msg.Headline = data.Slice(18, 100).ToArray();
            msg.Text = data.Slice(118, 750).ToArray();
            msg.Instruments = data.Slice(868, 100).ToArray();
            msg.Underlyings = data.Slice(968, 100).ToArray();
            msg.FirmList = data.Slice(1068, 54).ToArray();
            msg.UserList = data.Slice(1122, 54).ToArray();
        }

        public static void Encode(NativeNews msg, Span<byte> data)
        {
            data[0] = msg.PartitionId;
            BitConverter.TryWriteBytes(data.Slice(1, 8), msg.SequenceNumber);
            BitConverter.TryWriteBytes(data.Slice(9, 8), msg.OrigTime);
            data[17] = msg.Urgency;
            msg.Headline.CopyTo(data.Slice(18, 100));
            msg.Text.CopyTo(data.Slice(118, 750));
            msg.Instruments.CopyTo(data.Slice(868, 100));
            msg.Underlyings.CopyTo(data.Slice(968, 100));
            msg.FirmList.CopyTo(data.Slice(1068, 54));
            msg.UserList.CopyTo(data.Slice(1122, 54));
        }
    }
}
