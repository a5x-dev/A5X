using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// New Order Cross
    /// Message Type: u
    /// </summary>
    public class NativeNewOrderCross
    {
        /// <summary>Identifier of the cross order. Only alphanumeric values allowed</summary>
        public byte[] CrossId { get; set; }

        /// <summary>Price of the cross order</summary>
        public int LimitPrice { get; set; }

        public decimal LimitPriceAsDecimal => LimitPrice / 10000m;

        /// <summary>Total order quantity of the cross order</summary>
        public uint OrderQuantity { get; set; }

        /// <summary>Entering Trader ID</summary>
        public byte[] EnteringTrader { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public byte[] SenderLocation { get; set; }

        /// <summary>5 = Internal Cross. Any other value rejected</summary>
        public byte CrossType { get; set; }

        /// <summary>Client specified identifier of the buy side. Required</summary>
        public byte[] BuySideClientOrderId { get; set; }

        /// <summary>Identifier of the buyer's trading account. Required</summary>
        public ulong BuySideAccountId { get; set; }

        /// <summary>Client specified identifier of the sell side. Required</summary>
        public byte[] SellSideClientOrderId { get; set; }

        /// <summary>Identifier of the seller's trading account. Required</summary>
        public ulong SellSideAccountId { get; set; }

        /// <summary>2 = Limit. Any other value rejected</summary>
        public byte OrderType { get; set; }

        /// <summary>0 = Day. Only DAY TIF allowed for cross orders</summary>
        public byte TimeInForce { get; set; }

        /// <summary>Desk ID through which the order is placed from</summary>
        public byte[] DeskId { get; set; }

        /// <summary>Free format text field. Echoed in reports</summary>
        public byte[] Memo { get; set; }

        public static void Parse(NativeNewOrderCross msg, Span<byte> data)
        {
            msg.CrossId = data.Slice(0, 10).ToArray();
            msg.LimitPrice = BitConverter.ToInt32(data.Slice(10, 4));
            msg.OrderQuantity = BitConverter.ToUInt32(data.Slice(14, 4));
            msg.EnteringTrader = data.Slice(18, 15).ToArray();
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(33, 4));
            msg.SenderLocation = data.Slice(37, 10).ToArray();
            msg.CrossType = data[47];
            msg.BuySideClientOrderId = data.Slice(48, 10).ToArray();
            msg.BuySideAccountId = BitConverter.ToUInt64(data.Slice(58, 8));
            msg.SellSideClientOrderId = data.Slice(66, 10).ToArray();
            msg.SellSideAccountId = BitConverter.ToUInt64(data.Slice(76, 8));
            msg.OrderType = data[84];
            msg.TimeInForce = data[85];
            msg.DeskId = data.Slice(86, 15).ToArray();
            msg.Memo = data.Slice(101, 15).ToArray();
        }

        public static void Encode(NativeNewOrderCross msg, Span<byte> data)
        {
            msg.CrossId.CopyTo(data.Slice(0, 10));
            BitConverter.TryWriteBytes(data.Slice(10, 4), msg.LimitPrice);
            BitConverter.TryWriteBytes(data.Slice(14, 4), msg.OrderQuantity);
            msg.EnteringTrader.CopyTo(data.Slice(18, 15));
            BitConverter.TryWriteBytes(data.Slice(33, 4), msg.InstrumentId);
            msg.SenderLocation.CopyTo(data.Slice(37, 10));
            data[47] = msg.CrossType;
            msg.BuySideClientOrderId.CopyTo(data.Slice(48, 10));
            BitConverter.TryWriteBytes(data.Slice(58, 8), msg.BuySideAccountId);
            msg.SellSideClientOrderId.CopyTo(data.Slice(66, 10));
            BitConverter.TryWriteBytes(data.Slice(76, 8), msg.SellSideAccountId);
            data[84] = msg.OrderType;
            data[85] = msg.TimeInForce;
            msg.DeskId.CopyTo(data.Slice(86, 15));
            msg.Memo.CopyTo(data.Slice(101, 15));
        }
    }
}
