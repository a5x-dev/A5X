using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// News
    /// Message Type: Z
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
        public string Headline { get; set; }

        /// <summary>Text of the announcement</summary>
        public string Text { get; set; }

        /// <summary>Pipe separated list of symbols of instruments the announcements relates to</summary>
        public string Instruments { get; set; }

        /// <summary>Pipe separated list of symbols of underlyings the instruments relates to</summary>
        public string Underlyings { get; set; }

        /// <summary>Pipe separated list of firms that the announcement should be sent to</summary>
        public string FirmList { get; set; }

        /// <summary>Pipe separated list of users that the announcement should be sent to</summary>
        public string UserList { get; set; }

        public static void Parse(NativeNews msg, Span<byte> data)
        {
            msg.PartitionId = data[0];
            msg.SequenceNumber = BitConverter.ToUInt64(data.Slice(1, 8));
            msg.OrigTime = BitConverter.ToUInt64(data.Slice(9, 8));
            msg.Urgency = data[17];
            msg.Headline = Encoding.ASCII.GetString(data.Slice(18, 100)).TrimEnd('\0');
            msg.Text = Encoding.ASCII.GetString(data.Slice(118, 750)).TrimEnd('\0');
            msg.Instruments = Encoding.ASCII.GetString(data.Slice(868, 100)).TrimEnd('\0');
            msg.Underlyings = Encoding.ASCII.GetString(data.Slice(968, 100)).TrimEnd('\0');
            msg.FirmList = Encoding.ASCII.GetString(data.Slice(1068, 54)).TrimEnd('\0');
            msg.UserList = Encoding.ASCII.GetString(data.Slice(1122, 54)).TrimEnd('\0');
        }

        public static void Encode(NativeNews msg, Span<byte> data)
        {
            data[0] = msg.PartitionId;
            BitConverter.TryWriteBytes(data.Slice(1, 8), msg.SequenceNumber);
            BitConverter.TryWriteBytes(data.Slice(9, 8), msg.OrigTime);
            data[17] = msg.Urgency;
            Encoding.ASCII.GetBytes((msg.Headline ?? string.Empty).PadRight(100, '\0')).AsSpan(0, 100).CopyTo(data.Slice(18, 100));
            Encoding.ASCII.GetBytes((msg.Text ?? string.Empty).PadRight(750, '\0')).AsSpan(0, 750).CopyTo(data.Slice(118, 750));
            Encoding.ASCII.GetBytes((msg.Instruments ?? string.Empty).PadRight(100, '\0')).AsSpan(0, 100).CopyTo(data.Slice(868, 100));
            Encoding.ASCII.GetBytes((msg.Underlyings ?? string.Empty).PadRight(100, '\0')).AsSpan(0, 100).CopyTo(data.Slice(968, 100));
            Encoding.ASCII.GetBytes((msg.FirmList ?? string.Empty).PadRight(54, '\0')).AsSpan(0, 54).CopyTo(data.Slice(1068, 54));
            Encoding.ASCII.GetBytes((msg.UserList ?? string.Empty).PadRight(54, '\0')).AsSpan(0, 54).CopyTo(data.Slice(1122, 54));
        }
    }
}
