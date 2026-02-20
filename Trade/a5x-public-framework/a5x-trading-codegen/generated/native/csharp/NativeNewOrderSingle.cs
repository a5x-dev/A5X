using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// New order single
    /// Message Type: D
    /// </summary>
    public class NativeNewOrderSingle
    {
        /// <summary>Limit price</summary>
        public int LimitPrice { get; set; }

        public decimal LimitPriceAsDecimal => LimitPrice / 10000m;

        /// <summary>Total order quantity</summary>
        public uint OrderQuantity { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>1 = Buy, 2 = Sell</summary>
        public byte Side { get; set; }

        /// <summary>Entering Trader ID</summary>
        public byte[] EnteringTrader { get; set; }

        /// <summary>Client specified identifier of the order</summary>
        public byte[] ClientOrderId { get; set; }

        /// <summary>Identifier of the Trader Account</summary>
        public ulong Account { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public byte[] SenderLocation { get; set; }

        /// <summary>Client specified identifier of order relevant to self-execution</summary>
        public byte[] NoTradeKey { get; set; }

        /// <summary>Stop price/Trigger price</summary>
        public int StopPrice { get; set; }

        public decimal StopPriceAsDecimal => StopPrice / 10000m;

        /// <summary>Maximum quantity to be displayed</summary>
        public uint DisplayQuantity { get; set; }

        /// <summary>0 = Do Not Cancel, 1 = Cancel</summary>
        public byte CancelOnDisconnect { get; set; }

        /// <summary>0 = Anonymous, 1 = Named</summary>
        public byte Anonymity { get; set; }

        /// <summary>1 = Regular</summary>
        public byte OrderBook { get; set; }

        /// <summary>Bit 0: Passive Only (0 = No, 1 = Yes)</summary>
        public byte ExecutionInstruction { get; set; }

        /// <summary>1 = Retail Liquidity Taker, 2 = Waived Priority, 3 = Broker Only</summary>
        public byte RoutingInstruction { get; set; }

        /// <summary>1 = Market, 2 = Limit, 3 = Stop, 4 = Stop Limit, 5 = Market to Limit, 51 = RLP</summary>
        public byte OrderType { get; set; }

        /// <summary>0 = Day, 3 = IOC, 4 = FOK, 5 = OPG, 8 = GTT, 9 = GFA, 10 = ATC</summary>
        public byte TimeInForce { get; set; }

        /// <summary>Expire time in epoch (seconds precision)</summary>
        public ulong ExpireTime { get; set; }

        /// <summary>Free format text field</summary>
        public byte[] Memo { get; set; }

        /// <summary>Desk ID through which the order is placed from</summary>
        public byte[] DeskId { get; set; }

        public static void Parse(NativeNewOrderSingle msg, Span<byte> data)
        {
            msg.LimitPrice = BitConverter.ToInt32(data.Slice(0, 4));
            msg.OrderQuantity = BitConverter.ToUInt32(data.Slice(4, 4));
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(8, 4));
            msg.Side = data[12];
            msg.EnteringTrader = data.Slice(13, 15).ToArray();
            msg.ClientOrderId = data.Slice(28, 10).ToArray();
            msg.Account = BitConverter.ToUInt64(data.Slice(38, 8));
            msg.SenderLocation = data.Slice(46, 10).ToArray();
            msg.NoTradeKey = data.Slice(56, 10).ToArray();
            msg.StopPrice = BitConverter.ToInt32(data.Slice(66, 4));
            msg.DisplayQuantity = BitConverter.ToUInt32(data.Slice(70, 4));
            msg.CancelOnDisconnect = data[74];
            msg.Anonymity = data[75];
            msg.OrderBook = data[76];
            msg.ExecutionInstruction = data[77];
            msg.RoutingInstruction = data[78];
            msg.OrderType = data[79];
            msg.TimeInForce = data[80];
            msg.ExpireTime = BitConverter.ToUInt64(data.Slice(81, 8));
            msg.Memo = data.Slice(89, 15).ToArray();
            msg.DeskId = data.Slice(104, 15).ToArray();
        }

        public static void Encode(NativeNewOrderSingle msg, Span<byte> data)
        {
            BitConverter.TryWriteBytes(data.Slice(0, 4), msg.LimitPrice);
            BitConverter.TryWriteBytes(data.Slice(4, 4), msg.OrderQuantity);
            BitConverter.TryWriteBytes(data.Slice(8, 4), msg.InstrumentId);
            data[12] = msg.Side;
            msg.EnteringTrader.CopyTo(data.Slice(13, 15));
            msg.ClientOrderId.CopyTo(data.Slice(28, 10));
            BitConverter.TryWriteBytes(data.Slice(38, 8), msg.Account);
            msg.SenderLocation.CopyTo(data.Slice(46, 10));
            msg.NoTradeKey.CopyTo(data.Slice(56, 10));
            BitConverter.TryWriteBytes(data.Slice(66, 4), msg.StopPrice);
            BitConverter.TryWriteBytes(data.Slice(70, 4), msg.DisplayQuantity);
            data[74] = msg.CancelOnDisconnect;
            data[75] = msg.Anonymity;
            data[76] = msg.OrderBook;
            data[77] = msg.ExecutionInstruction;
            data[78] = msg.RoutingInstruction;
            data[79] = msg.OrderType;
            data[80] = msg.TimeInForce;
            BitConverter.TryWriteBytes(data.Slice(81, 8), msg.ExpireTime);
            msg.Memo.CopyTo(data.Slice(89, 15));
            msg.DeskId.CopyTo(data.Slice(104, 15));
        }
    }
}
