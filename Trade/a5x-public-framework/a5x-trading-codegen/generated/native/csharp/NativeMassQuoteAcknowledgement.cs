using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Mass Quote Acknowledgement
    /// Message Type: b
    /// </summary>
    public class NativeMassQuoteAcknowledgement
    {
        /// <summary>Mass Quote ID of the Mass Quote message</summary>
        public byte[] MassQuoteId { get; set; }

        /// <summary>Identity of the matching partition</summary>
        public byte PartitionId { get; set; }

        /// <summary>0 = Accepted, 5 = Rejected</summary>
        public byte Status { get; set; }

        /// <summary>Code specifying the reason for the reject. Ignore if Status is not Rejected (0)</summary>
        public int RejectCode { get; set; }

        /// <summary>Time of quote creation in epoch format</summary>
        public ulong TransactTime { get; set; }

        /// <summary>0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0)</summary>
        public byte Anonymity { get; set; }

        /// <summary>Identifier of the Account</summary>
        public ulong Account { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public byte[] SenderLocation { get; set; }

        /// <summary>1 = Regular</summary>
        public byte OrderBook { get; set; }

        /// <summary>Entering Trader ID</summary>
        public byte[] EnteringTrader { get; set; }

        /// <summary>Client specified identifier of order relevant to self-execution</summary>
        public byte[] NoTradeKey { get; set; }

        /// <summary>Free format text field. Echoed in reports</summary>
        public byte[] Memo { get; set; }

        /// <summary>Desk ID through which the order is placed from</summary>
        public byte[] DeskId { get; set; }

        /// <summary>1 = Regular (Day Session), 2 = Non-Regular (Night Session)</summary>
        public byte TradingPhase { get; set; }

        /// <summary>Unique sequence assigned for all market data message</summary>
        public ulong RoutingSeq { get; set; }

        /// <summary>Number of quote entries</summary>
        public ushort TotalQuoteEntries { get; set; }

        public static void Parse(NativeMassQuoteAcknowledgement msg, Span<byte> data)
        {
            msg.MassQuoteId = data.Slice(0, 10).ToArray();
            msg.PartitionId = data[10];
            msg.Status = data[11];
            msg.RejectCode = BitConverter.ToInt32(data.Slice(12, 4));
            msg.TransactTime = BitConverter.ToUInt64(data.Slice(16, 8));
            msg.Anonymity = data[24];
            msg.Account = BitConverter.ToUInt64(data.Slice(25, 8));
            msg.SenderLocation = data.Slice(33, 10).ToArray();
            msg.OrderBook = data[43];
            msg.EnteringTrader = data.Slice(44, 15).ToArray();
            msg.NoTradeKey = data.Slice(59, 10).ToArray();
            msg.Memo = data.Slice(69, 15).ToArray();
            msg.DeskId = data.Slice(84, 15).ToArray();
            msg.TradingPhase = data[99];
            msg.RoutingSeq = BitConverter.ToUInt64(data.Slice(100, 8));
            msg.TotalQuoteEntries = BitConverter.ToUInt16(data.Slice(108, 2));
        }

        public static void Encode(NativeMassQuoteAcknowledgement msg, Span<byte> data)
        {
            msg.MassQuoteId.CopyTo(data.Slice(0, 10));
            data[10] = msg.PartitionId;
            data[11] = msg.Status;
            BitConverter.TryWriteBytes(data.Slice(12, 4), msg.RejectCode);
            BitConverter.TryWriteBytes(data.Slice(16, 8), msg.TransactTime);
            data[24] = msg.Anonymity;
            BitConverter.TryWriteBytes(data.Slice(25, 8), msg.Account);
            msg.SenderLocation.CopyTo(data.Slice(33, 10));
            data[43] = msg.OrderBook;
            msg.EnteringTrader.CopyTo(data.Slice(44, 15));
            msg.NoTradeKey.CopyTo(data.Slice(59, 10));
            msg.Memo.CopyTo(data.Slice(69, 15));
            msg.DeskId.CopyTo(data.Slice(84, 15));
            data[99] = msg.TradingPhase;
            BitConverter.TryWriteBytes(data.Slice(100, 8), msg.RoutingSeq);
            BitConverter.TryWriteBytes(data.Slice(108, 2), msg.TotalQuoteEntries);
        }
    }
}
