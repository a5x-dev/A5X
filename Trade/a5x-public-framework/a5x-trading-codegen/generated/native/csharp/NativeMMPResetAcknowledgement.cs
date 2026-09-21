using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// MMP Reset Acknowledgement. Response to an MMP Reset Request
    /// Message Type: ~
    /// </summary>
    public class NativeMMPResetAcknowledgement
    {
        /// <summary>Client specified identifier of the originating request</summary>
        public int RequestId { get; set; }

        /// <summary>Identifier of the segment</summary>
        public string Segment { get; set; }

        /// <summary>Identity of the matching partition</summary>
        public byte PartitionId { get; set; }

        /// <summary>Entering Trader ID</summary>
        public string EnteringTrader { get; set; }

        /// <summary>Desk ID through which the request is placed from</summary>
        public string DeskId { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public string SenderLocation { get; set; }

        /// <summary>Free format text field. Echoed in reports</summary>
        public string Memo { get; set; }

        /// <summary>Outcome of the reset request</summary>
        public byte Status { get; set; }

        /// <summary>Code specifying the reason for the reject. Applicable only if the request is rejected, null filled otherwise</summary>
        public int RejectCode { get; set; }

        public static void Parse(NativeMMPResetAcknowledgement msg, Span<byte> data)
        {
            msg.RequestId = BitConverter.ToInt32(data.Slice(0, 4));
            msg.Segment = Encoding.ASCII.GetString(data.Slice(4, 10)).TrimEnd('\0');
            msg.PartitionId = data[14];
            msg.EnteringTrader = Encoding.ASCII.GetString(data.Slice(15, 15)).TrimEnd('\0');
            msg.DeskId = Encoding.ASCII.GetString(data.Slice(30, 15)).TrimEnd('\0');
            msg.SenderLocation = Encoding.ASCII.GetString(data.Slice(45, 10)).TrimEnd('\0');
            msg.Memo = Encoding.ASCII.GetString(data.Slice(55, 15)).TrimEnd('\0');
            msg.Status = data[70];
            msg.RejectCode = BitConverter.ToInt32(data.Slice(71, 4));
        }

        public static void Encode(NativeMMPResetAcknowledgement msg, Span<byte> data)
        {
            BitConverter.TryWriteBytes(data.Slice(0, 4), msg.RequestId);
            Encoding.ASCII.GetBytes((msg.Segment ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(4, 10));
            data[14] = msg.PartitionId;
            Encoding.ASCII.GetBytes((msg.EnteringTrader ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(15, 15));
            Encoding.ASCII.GetBytes((msg.DeskId ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(30, 15));
            Encoding.ASCII.GetBytes((msg.SenderLocation ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(45, 10));
            Encoding.ASCII.GetBytes((msg.Memo ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(55, 15));
            data[70] = msg.Status;
            BitConverter.TryWriteBytes(data.Slice(71, 4), msg.RejectCode);
        }
    }
}
