using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Order Replace Request
    /// Message Type: G
    /// </summary>
    public class NativeOrderReplaceRequest
    {
        /// <summary>Limit price. Ignored if Order Type is not Limit (2) or Stop Limit (4)</summary>
        public int LimitPrice { get; set; }

        public decimal LimitPriceAsDecimal => LimitPrice / 10000m;

        /// <summary>Total order quantity</summary>
        public uint OrderQuantity { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>1 = Buy, 2 = Sell</summary>
        public byte Side { get; set; }

        /// <summary>Client specified identifier of the order</summary>
        public byte[] ClientOrderId { get; set; }

        /// <summary>Client specified identifier of the order being modified</summary>
        public byte[] OrigClientOrderId { get; set; }

        /// <summary>Server specified identifier of the order being cancelled</summary>
        public byte[] OrderId { get; set; }

        /// <summary>Identifier of the Trader Account. Required field</summary>
        public ulong Account { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public byte[] SenderLocation { get; set; }

        /// <summary>Stop price/Trigger price. Ignored if Order Type is not Stop (3) or Stop Limit (4)</summary>
        public int StopPrice { get; set; }

        public decimal StopPriceAsDecimal => StopPrice / 10000m;

        /// <summary>Maximum quantity to be displayed. Negative value to ignore</summary>
        public uint DisplayQuantity { get; set; }

        /// <summary>1 = Regular</summary>
        public byte OrderBook { get; set; }

        /// <summary>Bit 0: Passive Only (0 = No, 1 = Yes)</summary>
        public byte ExecutionInstruction { get; set; }

        /// <summary>1 = Market, 2 = Limit, 3 = Stop, 4 = Stop Limit, 5 = Market to Limit, 51 = RLP</summary>
        public byte OrderType { get; set; }

        /// <summary>0 = Day, 3 = IOC, 4 = FOK, 5 = OPG, 8 = GTT, 9 = GFA, 10 = ATC</summary>
        public byte TimeInForce { get; set; }

        /// <summary>Expire time in epoch (seconds precision). Ignored if Time in Force is not GTT (8)</summary>
        public ulong ExpireTime { get; set; }

        public static void Parse(NativeOrderReplaceRequest msg, Span<byte> data)
        {
            msg.LimitPrice = BitConverter.ToInt32(data.Slice(0, 4));
            msg.OrderQuantity = BitConverter.ToUInt32(data.Slice(4, 4));
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(8, 4));
            msg.Side = data[12];
            msg.ClientOrderId = data.Slice(13, 10).ToArray();
            msg.OrigClientOrderId = data.Slice(23, 10).ToArray();
            msg.OrderId = data.Slice(33, 12).ToArray();
            msg.Account = BitConverter.ToUInt64(data.Slice(45, 8));
            msg.SenderLocation = data.Slice(53, 10).ToArray();
            msg.StopPrice = BitConverter.ToInt32(data.Slice(63, 4));
            msg.DisplayQuantity = BitConverter.ToUInt32(data.Slice(67, 4));
            msg.OrderBook = data[71];
            msg.ExecutionInstruction = data[72];
            msg.OrderType = data[73];
            msg.TimeInForce = data[74];
            msg.ExpireTime = BitConverter.ToUInt64(data.Slice(75, 8));
        }

        public static void Encode(NativeOrderReplaceRequest msg, Span<byte> data)
        {
            BitConverter.TryWriteBytes(data.Slice(0, 4), msg.LimitPrice);
            BitConverter.TryWriteBytes(data.Slice(4, 4), msg.OrderQuantity);
            BitConverter.TryWriteBytes(data.Slice(8, 4), msg.InstrumentId);
            data[12] = msg.Side;
            msg.ClientOrderId.CopyTo(data.Slice(13, 10));
            msg.OrigClientOrderId.CopyTo(data.Slice(23, 10));
            msg.OrderId.CopyTo(data.Slice(33, 12));
            BitConverter.TryWriteBytes(data.Slice(45, 8), msg.Account);
            msg.SenderLocation.CopyTo(data.Slice(53, 10));
            BitConverter.TryWriteBytes(data.Slice(63, 4), msg.StopPrice);
            BitConverter.TryWriteBytes(data.Slice(67, 4), msg.DisplayQuantity);
            data[71] = msg.OrderBook;
            data[72] = msg.ExecutionInstruction;
            data[73] = msg.OrderType;
            data[74] = msg.TimeInForce;
            BitConverter.TryWriteBytes(data.Slice(75, 8), msg.ExpireTime);
        }
    }
}
