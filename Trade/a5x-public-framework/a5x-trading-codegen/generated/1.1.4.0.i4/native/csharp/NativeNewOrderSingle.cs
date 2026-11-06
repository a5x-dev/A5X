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
        /// <summary>Limit price. This field will be ignored if the Order Type is not Limit (2) or Stop Limit (4)</summary>
        public long LimitPrice { get; set; }

        /// <summary>Total order quantity</summary>
        public uint OrderQuantity { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>1 = Buy, 2 = Sell</summary>
        public byte Side { get; set; }

        /// <summary>Entering Trader ID</summary>
        public string EnteringTrader { get; set; }

        /// <summary>Client specified identifier of the order</summary>
        public string ClientOrderId { get; set; }

        /// <summary>Identifier of the Trader Account. This is a required field</summary>
        public ulong Account { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public string SenderLocation { get; set; }

        /// <summary>Client specified identifier of order relevant to self-execution</summary>
        public string NoTradeKey { get; set; }

        /// <summary>Stop price/Trigger price. This field will be ignored if the Order Type is not Stop (3) or Stop Limit (4)</summary>
        public long StopPrice { get; set; }

        /// <summary>Max Floor. Maximum quantity to be displayed. A negative value should be used if this field is to be ignored</summary>
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
        public string Memo { get; set; }

        /// <summary>Desk ID through which the order is placed from</summary>
        public string DeskId { get; set; }

        public static void Parse(NativeNewOrderSingle msg, Span<byte> data)
        {
            msg.LimitPrice = BitConverter.ToInt64(data.Slice(0, 8));
            msg.OrderQuantity = BitConverter.ToUInt32(data.Slice(8, 4));
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(12, 4));
            msg.Side = data[16];
            msg.EnteringTrader = Encoding.ASCII.GetString(data.Slice(17, 15)).TrimEnd('\0');
            msg.ClientOrderId = Encoding.ASCII.GetString(data.Slice(32, 10)).TrimEnd('\0');
            msg.Account = BitConverter.ToUInt64(data.Slice(42, 8));
            msg.SenderLocation = Encoding.ASCII.GetString(data.Slice(50, 10)).TrimEnd('\0');
            msg.NoTradeKey = Encoding.ASCII.GetString(data.Slice(60, 10)).TrimEnd('\0');
            msg.StopPrice = BitConverter.ToInt64(data.Slice(70, 8));
            msg.DisplayQuantity = BitConverter.ToUInt32(data.Slice(78, 4));
            msg.CancelOnDisconnect = data[82];
            msg.Anonymity = data[83];
            msg.OrderBook = data[84];
            msg.ExecutionInstruction = data[85];
            msg.RoutingInstruction = data[86];
            msg.OrderType = data[87];
            msg.TimeInForce = data[88];
            msg.ExpireTime = BitConverter.ToUInt64(data.Slice(89, 8));
            msg.Memo = Encoding.ASCII.GetString(data.Slice(97, 15)).TrimEnd('\0');
            msg.DeskId = Encoding.ASCII.GetString(data.Slice(112, 15)).TrimEnd('\0');
        }

        public static void Encode(NativeNewOrderSingle msg, Span<byte> data)
        {
            BitConverter.TryWriteBytes(data.Slice(0, 8), msg.LimitPrice);
            BitConverter.TryWriteBytes(data.Slice(8, 4), msg.OrderQuantity);
            BitConverter.TryWriteBytes(data.Slice(12, 4), msg.InstrumentId);
            data[16] = msg.Side;
            Encoding.ASCII.GetBytes((msg.EnteringTrader ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(17, 15));
            Encoding.ASCII.GetBytes((msg.ClientOrderId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(32, 10));
            BitConverter.TryWriteBytes(data.Slice(42, 8), msg.Account);
            Encoding.ASCII.GetBytes((msg.SenderLocation ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(50, 10));
            Encoding.ASCII.GetBytes((msg.NoTradeKey ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(60, 10));
            BitConverter.TryWriteBytes(data.Slice(70, 8), msg.StopPrice);
            BitConverter.TryWriteBytes(data.Slice(78, 4), msg.DisplayQuantity);
            data[82] = msg.CancelOnDisconnect;
            data[83] = msg.Anonymity;
            data[84] = msg.OrderBook;
            data[85] = msg.ExecutionInstruction;
            data[86] = msg.RoutingInstruction;
            data[87] = msg.OrderType;
            data[88] = msg.TimeInForce;
            BitConverter.TryWriteBytes(data.Slice(89, 8), msg.ExpireTime);
            Encoding.ASCII.GetBytes((msg.Memo ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(97, 15));
            Encoding.ASCII.GetBytes((msg.DeskId ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(112, 15));
        }
    }
}
