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
        public string MassQuoteId { get; set; }

        /// <summary>Identity of the matching partition</summary>
        public byte PartitionId { get; set; }

        /// <summary>0 = Do Not Cancel, 1 = Cancel</summary>
        public byte CancelOnDisconnect { get; set; }

        /// <summary>0 = Anonymous, 1 = Named. Absence interpreted as Anonymous (0)</summary>
        public byte Anonymity { get; set; }

        /// <summary>Identifier of the trading account. Required field</summary>
        public ulong Account { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public string SenderLocation { get; set; }

        /// <summary>Entering Trader ID</summary>
        public string EnteringTrader { get; set; }

        /// <summary>Client specified identifier of order relevant to self-execution</summary>
        public string NoTradeKey { get; set; }

        /// <summary>Free format text field. Echoed in reports</summary>
        public string Memo { get; set; }

        /// <summary>Desk ID through which the order is placed from</summary>
        public string DeskId { get; set; }

        /// <summary>Number of quote entries in the mass quote</summary>
        public ushort TotalQuoteEntries { get; set; }

        public static void Parse(NativeMassQuote msg, Span<byte> data)
        {
            msg.MassQuoteId = Encoding.ASCII.GetString(data.Slice(0, 10)).TrimEnd('\0');
            msg.PartitionId = data[10];
            msg.CancelOnDisconnect = data[11];
            msg.Anonymity = data[12];
            msg.Account = BitConverter.ToUInt64(data.Slice(13, 8));
            msg.SenderLocation = Encoding.ASCII.GetString(data.Slice(21, 10)).TrimEnd('\0');
            msg.EnteringTrader = Encoding.ASCII.GetString(data.Slice(31, 15)).TrimEnd('\0');
            msg.NoTradeKey = Encoding.ASCII.GetString(data.Slice(46, 10)).TrimEnd('\0');
            msg.Memo = Encoding.ASCII.GetString(data.Slice(56, 15)).TrimEnd('\0');
            msg.DeskId = Encoding.ASCII.GetString(data.Slice(71, 15)).TrimEnd('\0');
            msg.TotalQuoteEntries = BitConverter.ToUInt16(data.Slice(86, 2));
        }

        public static void Encode(NativeMassQuote msg, Span<byte> data)
        {
            Encoding.ASCII.GetBytes((msg.MassQuoteId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(0, 10));
            data[10] = msg.PartitionId;
            data[11] = msg.CancelOnDisconnect;
            data[12] = msg.Anonymity;
            BitConverter.TryWriteBytes(data.Slice(13, 8), msg.Account);
            Encoding.ASCII.GetBytes((msg.SenderLocation ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(21, 10));
            Encoding.ASCII.GetBytes((msg.EnteringTrader ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(31, 15));
            Encoding.ASCII.GetBytes((msg.NoTradeKey ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(46, 10));
            Encoding.ASCII.GetBytes((msg.Memo ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(56, 15));
            Encoding.ASCII.GetBytes((msg.DeskId ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(71, 15));
            BitConverter.TryWriteBytes(data.Slice(86, 2), msg.TotalQuoteEntries);
        }
    }
}
