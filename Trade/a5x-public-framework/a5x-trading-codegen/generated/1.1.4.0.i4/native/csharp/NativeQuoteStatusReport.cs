using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Quote Status Report
    /// Message Type: c
    /// </summary>
    public class NativeQuoteStatusReport
    {
        /// <summary>Identity of the matching partition</summary>
        public byte PartitionId { get; set; }

        /// <summary>Message sequence number of the Matching Partition</summary>
        public ulong SequenceNumber { get; set; }

        /// <summary>Client specified identifier of the quote if available. Null filled otherwise</summary>
        public string QuoteMsgId { get; set; }

        /// <summary>6 = Removed from Market, 7 = Expired, 17 = Cancelled</summary>
        public byte QuoteStatus { get; set; }

        /// <summary>Code specifying the reason for the reject. Applicable only if Quote Status is Rejected, null filled otherwise</summary>
        public int RejectCode { get; set; }

        /// <summary>1 = Regular</summary>
        public byte OrderBook { get; set; }

        /// <summary>Time the message was generated in epoch format</summary>
        public ulong TransactTime { get; set; }

        /// <summary>0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0)</summary>
        public byte Anonymity { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>The system generated unique ID for the Bid Side</summary>
        public string BidId { get; set; }

        /// <summary>The system generated unique ID for the Offer Side</summary>
        public string OfferId { get; set; }

        /// <summary>Identifier of the Account</summary>
        public ulong Account { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public string SenderLocation { get; set; }

        /// <summary>Entering Trader ID</summary>
        public string EnteringTrader { get; set; }

        /// <summary>Free format text field. Echoed in reports</summary>
        public string Memo { get; set; }

        /// <summary>Desk ID through which the order is placed from</summary>
        public string DeskId { get; set; }

        public static void Parse(NativeQuoteStatusReport msg, Span<byte> data)
        {
            msg.PartitionId = data[0];
            msg.SequenceNumber = BitConverter.ToUInt64(data.Slice(1, 8));
            msg.QuoteMsgId = Encoding.ASCII.GetString(data.Slice(9, 10)).TrimEnd('\0');
            msg.QuoteStatus = data[19];
            msg.RejectCode = BitConverter.ToInt32(data.Slice(20, 4));
            msg.OrderBook = data[24];
            msg.TransactTime = BitConverter.ToUInt64(data.Slice(25, 8));
            msg.Anonymity = data[33];
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(34, 4));
            msg.BidId = Encoding.ASCII.GetString(data.Slice(38, 10)).TrimEnd('\0');
            msg.OfferId = Encoding.ASCII.GetString(data.Slice(48, 10)).TrimEnd('\0');
            msg.Account = BitConverter.ToUInt64(data.Slice(58, 8));
            msg.SenderLocation = Encoding.ASCII.GetString(data.Slice(66, 10)).TrimEnd('\0');
            msg.EnteringTrader = Encoding.ASCII.GetString(data.Slice(76, 15)).TrimEnd('\0');
            msg.Memo = Encoding.ASCII.GetString(data.Slice(91, 15)).TrimEnd('\0');
            msg.DeskId = Encoding.ASCII.GetString(data.Slice(106, 15)).TrimEnd('\0');
        }

        public static void Encode(NativeQuoteStatusReport msg, Span<byte> data)
        {
            data[0] = msg.PartitionId;
            BitConverter.TryWriteBytes(data.Slice(1, 8), msg.SequenceNumber);
            Encoding.ASCII.GetBytes((msg.QuoteMsgId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(9, 10));
            data[19] = msg.QuoteStatus;
            BitConverter.TryWriteBytes(data.Slice(20, 4), msg.RejectCode);
            data[24] = msg.OrderBook;
            BitConverter.TryWriteBytes(data.Slice(25, 8), msg.TransactTime);
            data[33] = msg.Anonymity;
            BitConverter.TryWriteBytes(data.Slice(34, 4), msg.InstrumentId);
            Encoding.ASCII.GetBytes((msg.BidId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(38, 10));
            Encoding.ASCII.GetBytes((msg.OfferId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(48, 10));
            BitConverter.TryWriteBytes(data.Slice(58, 8), msg.Account);
            Encoding.ASCII.GetBytes((msg.SenderLocation ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(66, 10));
            Encoding.ASCII.GetBytes((msg.EnteringTrader ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(76, 15));
            Encoding.ASCII.GetBytes((msg.Memo ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(91, 15));
            Encoding.ASCII.GetBytes((msg.DeskId ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(106, 15));
        }
    }
}
