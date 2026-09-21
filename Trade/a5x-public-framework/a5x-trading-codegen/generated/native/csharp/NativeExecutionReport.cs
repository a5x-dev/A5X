using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Execution report
    /// Message Type: 8
    /// </summary>
    public class NativeExecutionReport
    {
        /// <summary>Identity of the matching partition</summary>
        public byte PartitionId { get; set; }

        /// <summary>Message sequence number of the matching partition</summary>
        public ulong SequenceNumber { get; set; }

        /// <summary>Identifier of the Execution Report. Identifier of the trade being cancelled or corrected if Execution Type is Trade Cancel (H)</summary>
        public string ExecutionId { get; set; }

        /// <summary>Client specified identifier of the order, cancel request or modification requests</summary>
        public string ClientOrderId { get; set; }

        /// <summary>Server specified identifier of the order</summary>
        public string OrderId { get; set; }

        /// <summary>Server specified public order identifier of the order</summary>
        public string PublicOrderId { get; set; }

        /// <summary>Executed price. This field should be ignored if Execution Type is not Trade (F)</summary>
        public long ExecutedPrice { get; set; }

        /// <summary>Time the transaction occurred in epoch format</summary>
        public ulong TransactTime { get; set; }

        /// <summary>Identifier of the Trader Account</summary>
        public ulong Account { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public string SenderLocation { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>Identifier of the cross order. Only alphanumeric values allowed</summary>
        public string CrossId { get; set; }

        /// <summary>Executed quantity. Ignore if Execution Type is not Trade (F)</summary>
        public uint ExecutedQuantity { get; set; }

        /// <summary>Quantity available for further execution</summary>
        public uint LeavesQuantity { get; set; }

        /// <summary>0 = New, 4 = Cancelled, 5 = Modified, 8 = Rejected, 9 = Suspended, A = Pending New, C = Expired, D = Restated, E = Pending Replace, F = Trade, H = Trade Cancel, L = Triggered</summary>
        public string ExecutionType { get; set; }

        /// <summary>0 = New, 1 = Partially Filled, 2 = Filled, 4 = Cancelled, 6 = Expired, 8 = Rejected, 9 = Suspended, 12 = Pending New, 14 = Pending Replace</summary>
        public byte OrderStatus { get; set; }

        /// <summary>Code specifying the reason for the reject. Ignore if Execution Type is not Rejected (8)</summary>
        public uint RejectCode { get; set; }

        /// <summary>0 = Unset, 1 = Order is being worked, 2 = Order is not currently in a working state. Only applicable when Order Status is New (0)</summary>
        public byte WorkingIndicator { get; set; }

        /// <summary>1 = Buy, 2 = Sell. When a quote is rejected, field will be set to Buy (1)</summary>
        public byte Side { get; set; }

        /// <summary>0 = GT Corporate Action, 1 = GT Renewal/Restatement, 3 = Order Re-Priced, 8 = Market Option, 100 = Order Replenishment. Only applicable if Exec Type is Restated (D) or unsolicited cancellation</summary>
        public byte RestatementReason { get; set; }

        /// <summary>1 = Regular</summary>
        public byte OrderBook { get; set; }

        /// <summary>Bit 0: Aggressor Indicator (0 = Passive, 1 = Aggressor), Bit 5: RLP (0 = No, 1 = Yes). Applicable only when Exec Type is Trade</summary>
        public byte IndicatorFlags { get; set; }

        /// <summary>5 = Internal Cross. Any other value rejected</summary>
        public byte CrossType { get; set; }

        /// <summary>1 = Retail Liquidity Taker, 2 = Waived Priority, 3 = Broker Only</summary>
        public byte RoutingInstruction { get; set; }

        /// <summary>1 = Regular, 2 = Non-Regular</summary>
        public byte TradingPhase { get; set; }

        /// <summary>MIC Code of the instrument as set up under Execution Venue in Reference Data</summary>
        public string ExecutionVenue { get; set; }

        /// <summary>Entering Trader ID</summary>
        public string EnteringTrader { get; set; }

        /// <summary>Free format text field. Echoed in reports</summary>
        public string Memo { get; set; }

        /// <summary>Desk ID through which the order is placed from</summary>
        public string DeskId { get; set; }

        /// <summary>Client specified identifier of order relevant to self-execution</summary>
        public string NoTradeKey { get; set; }

        public static void Parse(NativeExecutionReport msg, Span<byte> data)
        {
            msg.PartitionId = data[0];
            msg.SequenceNumber = BitConverter.ToUInt64(data.Slice(1, 8));
            msg.ExecutionId = Encoding.ASCII.GetString(data.Slice(9, 21)).TrimEnd('\0');
            msg.ClientOrderId = Encoding.ASCII.GetString(data.Slice(30, 10)).TrimEnd('\0');
            msg.OrderId = Encoding.ASCII.GetString(data.Slice(40, 12)).TrimEnd('\0');
            msg.PublicOrderId = Encoding.ASCII.GetString(data.Slice(52, 12)).TrimEnd('\0');
            msg.ExecutedPrice = BitConverter.ToInt64(data.Slice(64, 8));
            msg.TransactTime = BitConverter.ToUInt64(data.Slice(72, 8));
            msg.Account = BitConverter.ToUInt64(data.Slice(80, 8));
            msg.SenderLocation = Encoding.ASCII.GetString(data.Slice(88, 10)).TrimEnd('\0');
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(98, 4));
            msg.CrossId = Encoding.ASCII.GetString(data.Slice(102, 10)).TrimEnd('\0');
            msg.ExecutedQuantity = BitConverter.ToUInt32(data.Slice(112, 4));
            msg.LeavesQuantity = BitConverter.ToUInt32(data.Slice(116, 4));
            msg.ExecutionType = Encoding.ASCII.GetString(data.Slice(120, 1)).TrimEnd('\0');
            msg.OrderStatus = data[121];
            msg.RejectCode = BitConverter.ToUInt32(data.Slice(122, 4));
            msg.WorkingIndicator = data[126];
            msg.Side = data[127];
            msg.RestatementReason = data[128];
            msg.OrderBook = data[129];
            msg.IndicatorFlags = data[130];
            msg.CrossType = data[131];
            msg.RoutingInstruction = data[132];
            msg.TradingPhase = data[133];
            msg.ExecutionVenue = Encoding.ASCII.GetString(data.Slice(134, 4)).TrimEnd('\0');
            msg.EnteringTrader = Encoding.ASCII.GetString(data.Slice(138, 15)).TrimEnd('\0');
            msg.Memo = Encoding.ASCII.GetString(data.Slice(153, 15)).TrimEnd('\0');
            msg.DeskId = Encoding.ASCII.GetString(data.Slice(168, 15)).TrimEnd('\0');
            msg.NoTradeKey = Encoding.ASCII.GetString(data.Slice(183, 10)).TrimEnd('\0');
        }

        public static void Encode(NativeExecutionReport msg, Span<byte> data)
        {
            data[0] = msg.PartitionId;
            BitConverter.TryWriteBytes(data.Slice(1, 8), msg.SequenceNumber);
            Encoding.ASCII.GetBytes((msg.ExecutionId ?? string.Empty).PadRight(21, '\0')).AsSpan(0, 21).CopyTo(data.Slice(9, 21));
            Encoding.ASCII.GetBytes((msg.ClientOrderId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(30, 10));
            Encoding.ASCII.GetBytes((msg.OrderId ?? string.Empty).PadRight(12, '\0')).AsSpan(0, 12).CopyTo(data.Slice(40, 12));
            Encoding.ASCII.GetBytes((msg.PublicOrderId ?? string.Empty).PadRight(12, '\0')).AsSpan(0, 12).CopyTo(data.Slice(52, 12));
            BitConverter.TryWriteBytes(data.Slice(64, 8), msg.ExecutedPrice);
            BitConverter.TryWriteBytes(data.Slice(72, 8), msg.TransactTime);
            BitConverter.TryWriteBytes(data.Slice(80, 8), msg.Account);
            Encoding.ASCII.GetBytes((msg.SenderLocation ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(88, 10));
            BitConverter.TryWriteBytes(data.Slice(98, 4), msg.InstrumentId);
            Encoding.ASCII.GetBytes((msg.CrossId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(102, 10));
            BitConverter.TryWriteBytes(data.Slice(112, 4), msg.ExecutedQuantity);
            BitConverter.TryWriteBytes(data.Slice(116, 4), msg.LeavesQuantity);
            Encoding.ASCII.GetBytes((msg.ExecutionType ?? string.Empty).PadRight(1, '\0')).AsSpan(0, 1).CopyTo(data.Slice(120, 1));
            data[121] = msg.OrderStatus;
            BitConverter.TryWriteBytes(data.Slice(122, 4), msg.RejectCode);
            data[126] = msg.WorkingIndicator;
            data[127] = msg.Side;
            data[128] = msg.RestatementReason;
            data[129] = msg.OrderBook;
            data[130] = msg.IndicatorFlags;
            data[131] = msg.CrossType;
            data[132] = msg.RoutingInstruction;
            data[133] = msg.TradingPhase;
            Encoding.ASCII.GetBytes((msg.ExecutionVenue ?? string.Empty).PadRight(4, '\0')).AsSpan(0, 4).CopyTo(data.Slice(134, 4));
            Encoding.ASCII.GetBytes((msg.EnteringTrader ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(138, 15));
            Encoding.ASCII.GetBytes((msg.Memo ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(153, 15));
            Encoding.ASCII.GetBytes((msg.DeskId ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(168, 15));
            Encoding.ASCII.GetBytes((msg.NoTradeKey ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(183, 10));
        }
    }
}
