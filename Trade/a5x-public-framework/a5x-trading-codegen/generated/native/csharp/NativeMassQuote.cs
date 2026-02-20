using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Mass Quote
    /// Message Type: i
    /// </summary>
    public class NativeMassQuote
    {
        /// <summary>Client specified identifier of the message</summary>
        public byte[] MassQuoteId { get; set; }

        /// <summary>Identity of the matching partition</summary>
        public byte PartitionId { get; set; }

        /// <summary>0 = Do Not Cancel, 1 = Cancel</summary>
        public byte CancelOnDisconnect { get; set; }

        /// <summary>0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0)</summary>
        public byte Anonymity { get; set; }

        /// <summary>Identifier of the trading account. Required field</summary>
        public ulong Account { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public byte[] SenderLocation { get; set; }

        /// <summary>Entering Trader ID</summary>
        public byte[] EnteringTrader { get; set; }

        /// <summary>Client specified identifier of order relevant to self-execution</summary>
        public byte[] NoTradeKey { get; set; }

        /// <summary>Free format text field. Echoed in reports</summary>
        public byte[] Memo { get; set; }

        /// <summary>Desk ID through which the order is placed from</summary>
        public byte[] DeskId { get; set; }

        /// <summary>Number of quote entries in the mass quote</summary>
        public ushort TotalQuoteEntries { get; set; }

        public static void Parse(NativeMassQuote msg, Span<byte> data)
        {
            msg.MassQuoteId = data.Slice(0, 10).ToArray();
            msg.PartitionId = data[10];
            msg.CancelOnDisconnect = data[11];
            msg.Anonymity = data[12];
            msg.Account = BitConverter.ToUInt64(data.Slice(13, 8));
            msg.SenderLocation = data.Slice(21, 10).ToArray();
            msg.EnteringTrader = data.Slice(31, 15).ToArray();
            msg.NoTradeKey = data.Slice(46, 10).ToArray();
            msg.Memo = data.Slice(56, 15).ToArray();
            msg.DeskId = data.Slice(71, 15).ToArray();
            msg.TotalQuoteEntries = BitConverter.ToUInt16(data.Slice(86, 2));
        }

        public static void Encode(NativeMassQuote msg, Span<byte> data)
        {
            msg.MassQuoteId.CopyTo(data.Slice(0, 10));
            data[10] = msg.PartitionId;
            data[11] = msg.CancelOnDisconnect;
            data[12] = msg.Anonymity;
            BitConverter.TryWriteBytes(data.Slice(13, 8), msg.Account);
            msg.SenderLocation.CopyTo(data.Slice(21, 10));
            msg.EnteringTrader.CopyTo(data.Slice(31, 15));
            msg.NoTradeKey.CopyTo(data.Slice(46, 10));
            msg.Memo.CopyTo(data.Slice(56, 15));
            msg.DeskId.CopyTo(data.Slice(71, 15));
            BitConverter.TryWriteBytes(data.Slice(86, 2), msg.TotalQuoteEntries);
        }
    }
}
