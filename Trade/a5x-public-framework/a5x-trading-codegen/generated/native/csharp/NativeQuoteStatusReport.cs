using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Quote Status Report
    /// Message Type: AI
    /// </summary>
    public class NativeQuoteStatusReport
    {
        /// <summary>Identity of the matching partition</summary>
        public byte PartitionId { get; set; }

        /// <summary>Message sequence number of the Matching Partition</summary>
        public ulong SequenceNumber { get; set; }

        /// <summary>Client specified identifier of the quote if available. Null filled otherwise</summary>
        public byte[] QuoteMsgId { get; set; }

        /// <summary>6 = Removed from Market, 7 = Expired, 17 = Cancelled</summary>
        public byte QuoteStatus { get; set; }

        /// <summary>Code specifying the reason for the reject. Applicable only if Quote Status is Rejected, null filled otherwise</summary>
        public int RejectCode { get; set; }

        /// <summary>1 = Regular</summary>
        public uint OrderBook { get; set; }

        /// <summary>Time the message was generated in epoch format</summary>
        public ulong TransactTime { get; set; }

        /// <summary>0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0)</summary>
        public byte Anonymity { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>The system generated unique ID for the Bid Side</summary>
        public byte[] BidId { get; set; }

        /// <summary>The system generated unique ID for the Offer Side</summary>
        public byte[] OfferId { get; set; }

        /// <summary>Identifier of the Account</summary>
        public ulong Account { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public byte[] SenderLocation { get; set; }

        /// <summary>Entering Trader ID</summary>
        public byte[] EnteringTrader { get; set; }

        /// <summary>Free format text field. Echoed in reports</summary>
        public byte[] Memo { get; set; }

        /// <summary>Desk ID through which the order is placed from</summary>
        public byte[] DeskId { get; set; }

        public static void Parse(NativeQuoteStatusReport msg, Span<byte> data)
        {
            msg.PartitionId = data[0];
            msg.SequenceNumber = BitConverter.ToUInt64(data.Slice(1, 8));
            msg.QuoteMsgId = data.Slice(9, 10).ToArray();
            msg.QuoteStatus = data[19];
            msg.RejectCode = BitConverter.ToInt32(data.Slice(20, 4));
            msg.OrderBook = BitConverter.ToUInt32(data.Slice(24, 4));
            msg.TransactTime = BitConverter.ToUInt64(data.Slice(28, 8));
            msg.Anonymity = data[36];
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(37, 4));
            msg.BidId = data.Slice(41, 10).ToArray();
            msg.OfferId = data.Slice(51, 10).ToArray();
            msg.Account = BitConverter.ToUInt64(data.Slice(61, 8));
            msg.SenderLocation = data.Slice(69, 10).ToArray();
            msg.EnteringTrader = data.Slice(79, 15).ToArray();
            msg.Memo = data.Slice(94, 15).ToArray();
            msg.DeskId = data.Slice(109, 15).ToArray();
        }

        public static void Encode(NativeQuoteStatusReport msg, Span<byte> data)
        {
            data[0] = msg.PartitionId;
            BitConverter.TryWriteBytes(data.Slice(1, 8), msg.SequenceNumber);
            msg.QuoteMsgId.CopyTo(data.Slice(9, 10));
            data[19] = msg.QuoteStatus;
            BitConverter.TryWriteBytes(data.Slice(20, 4), msg.RejectCode);
            BitConverter.TryWriteBytes(data.Slice(24, 4), msg.OrderBook);
            BitConverter.TryWriteBytes(data.Slice(28, 8), msg.TransactTime);
            data[36] = msg.Anonymity;
            BitConverter.TryWriteBytes(data.Slice(37, 4), msg.InstrumentId);
            msg.BidId.CopyTo(data.Slice(41, 10));
            msg.OfferId.CopyTo(data.Slice(51, 10));
            BitConverter.TryWriteBytes(data.Slice(61, 8), msg.Account);
            msg.SenderLocation.CopyTo(data.Slice(69, 10));
            msg.EnteringTrader.CopyTo(data.Slice(79, 15));
            msg.Memo.CopyTo(data.Slice(94, 15));
            msg.DeskId.CopyTo(data.Slice(109, 15));
        }
    }
}
