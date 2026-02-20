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
        public byte[] ExecutionId { get; set; }

        /// <summary>Client specified identifier of the order, cancel request or modification requests</summary>
        public byte[] ClientOrderId { get; set; }

        /// <summary>Server specified identifier of the order</summary>
        public byte[] OrderId { get; set; }

        /// <summary>Server specified public order identifier of the order</summary>
        public byte[] PublicOrderId { get; set; }

        /// <summary>Executed price. Ignore if Execution Type is not Trade (F)</summary>
        public int ExecutedPrice { get; set; }

        public decimal ExecutedPriceAsDecimal => ExecutedPrice / 10000m;

        /// <summary>Time the transaction occurred in epoch format</summary>
        public ulong TransactTime { get; set; }

        /// <summary>Identifier of the Trader Account</summary>
        public ulong Account { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public byte[] SenderLocation { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>Identifier of the cross order. Only alphanumeric values allowed</summary>
        public byte[] CrossId { get; set; }

        /// <summary>Executed quantity. Ignore if Execution Type is not Trade (F)</summary>
        public uint ExecutedQuantity { get; set; }

        /// <summary>Quantity available for further execution</summary>
        public uint LeavesQuantity { get; set; }

        /// <summary>0 = New, 4 = Cancelled, 5 = Modified, 8 = Rejected, 9 = Suspended, A = Pending New, C = Expired, D = Restated, E = Pending Replace, F = Trade, H = Trade Cancel, L = Triggered</summary>
        public byte[] ExecutionType { get; set; }

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
        public byte[] ExecutionVenue { get; set; }

        /// <summary>Entering Trader ID</summary>
        public byte[] EnteringTrader { get; set; }

        /// <summary>Free format text field. Echoed in reports</summary>
        public byte[] Memo { get; set; }

        /// <summary>Desk ID through which the order is placed from</summary>
        public byte[] DeskId { get; set; }

        /// <summary>Client specified identifier of order relevant to self-execution</summary>
        public byte[] NoTradeKey { get; set; }

        public static void Parse(NativeExecutionReport msg, Span<byte> data)
        {
            msg.PartitionId = data[0];
            msg.SequenceNumber = BitConverter.ToUInt64(data.Slice(1, 8));
            msg.ExecutionId = data.Slice(9, 21).ToArray();
            msg.ClientOrderId = data.Slice(30, 10).ToArray();
            msg.OrderId = data.Slice(40, 12).ToArray();
            msg.PublicOrderId = data.Slice(52, 12).ToArray();
            msg.ExecutedPrice = BitConverter.ToInt32(data.Slice(64, 4));
            msg.TransactTime = BitConverter.ToUInt64(data.Slice(68, 8));
            msg.Account = BitConverter.ToUInt64(data.Slice(76, 8));
            msg.SenderLocation = data.Slice(84, 10).ToArray();
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(94, 4));
            msg.CrossId = data.Slice(98, 10).ToArray();
            msg.ExecutedQuantity = BitConverter.ToUInt32(data.Slice(108, 4));
            msg.LeavesQuantity = BitConverter.ToUInt32(data.Slice(112, 4));
            msg.ExecutionType = data.Slice(116, 1).ToArray();
            msg.OrderStatus = data[117];
            msg.RejectCode = BitConverter.ToUInt32(data.Slice(118, 4));
            msg.WorkingIndicator = data[122];
            msg.Side = data[123];
            msg.RestatementReason = data[124];
            msg.OrderBook = data[125];
            msg.IndicatorFlags = data[126];
            msg.CrossType = data[127];
            msg.RoutingInstruction = data[128];
            msg.TradingPhase = data[129];
            msg.ExecutionVenue = data.Slice(130, 4).ToArray();
            msg.EnteringTrader = data.Slice(134, 15).ToArray();
            msg.Memo = data.Slice(149, 15).ToArray();
            msg.DeskId = data.Slice(164, 15).ToArray();
            msg.NoTradeKey = data.Slice(179, 10).ToArray();
        }

        public static void Encode(NativeExecutionReport msg, Span<byte> data)
        {
            data[0] = msg.PartitionId;
            BitConverter.TryWriteBytes(data.Slice(1, 8), msg.SequenceNumber);
            msg.ExecutionId.CopyTo(data.Slice(9, 21));
            msg.ClientOrderId.CopyTo(data.Slice(30, 10));
            msg.OrderId.CopyTo(data.Slice(40, 12));
            msg.PublicOrderId.CopyTo(data.Slice(52, 12));
            BitConverter.TryWriteBytes(data.Slice(64, 4), msg.ExecutedPrice);
            BitConverter.TryWriteBytes(data.Slice(68, 8), msg.TransactTime);
            BitConverter.TryWriteBytes(data.Slice(76, 8), msg.Account);
            msg.SenderLocation.CopyTo(data.Slice(84, 10));
            BitConverter.TryWriteBytes(data.Slice(94, 4), msg.InstrumentId);
            msg.CrossId.CopyTo(data.Slice(98, 10));
            BitConverter.TryWriteBytes(data.Slice(108, 4), msg.ExecutedQuantity);
            BitConverter.TryWriteBytes(data.Slice(112, 4), msg.LeavesQuantity);
            msg.ExecutionType.CopyTo(data.Slice(116, 1));
            data[117] = msg.OrderStatus;
            BitConverter.TryWriteBytes(data.Slice(118, 4), msg.RejectCode);
            data[122] = msg.WorkingIndicator;
            data[123] = msg.Side;
            data[124] = msg.RestatementReason;
            data[125] = msg.OrderBook;
            data[126] = msg.IndicatorFlags;
            data[127] = msg.CrossType;
            data[128] = msg.RoutingInstruction;
            data[129] = msg.TradingPhase;
            msg.ExecutionVenue.CopyTo(data.Slice(130, 4));
            msg.EnteringTrader.CopyTo(data.Slice(134, 15));
            msg.Memo.CopyTo(data.Slice(149, 15));
            msg.DeskId.CopyTo(data.Slice(164, 15));
            msg.NoTradeKey.CopyTo(data.Slice(179, 10));
        }
    }
}
