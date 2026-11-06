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
        /// <summary>Limit price. This field will be ignored if the Order Type is not Limit (2) or Stop Limit (4)</summary>
        public long LimitPrice { get; set; }

        /// <summary>Total order quantity</summary>
        public uint OrderQuantity { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>1 = Buy, 2 = Sell</summary>
        public byte Side { get; set; }

        /// <summary>Client specified identifier of the order</summary>
        public string ClientOrderId { get; set; }

        /// <summary>Client specified identifier of the order being modified</summary>
        public string OrigClientOrderId { get; set; }

        /// <summary>Identifier of the Trader Account. This is a required field</summary>
        public ulong Account { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public string SenderLocation { get; set; }

        /// <summary>Stop price/Trigger price. This field will be ignored if the Order Type is not Stop (3) or Stop Limit (4)</summary>
        public long StopPrice { get; set; }

        /// <summary>Maximum quantity to be displayed. A negative value should be used if this field is to be ignored</summary>
        public uint DisplayQuantity { get; set; }

        /// <summary>1 = Regular</summary>
        public byte OrderBook { get; set; }

        /// <summary>1 = Market, 2 = Limit, 3 = Stop, 4 = Stop Limit, 5 = Market to Limit, 51 = RLP</summary>
        public byte OrderType { get; set; }

        /// <summary>0 = Day, 3 = IOC, 4 = FOK, 5 = OPG, 8 = GTT, 9 = GFA, 10 = ATC</summary>
        public byte TimeInForce { get; set; }

        /// <summary>Expire time in epoch (seconds precision). Ignored if Time in Force is not GTT (8)</summary>
        public ulong ExpireTime { get; set; }

        public static void Parse(NativeOrderReplaceRequest msg, Span<byte> data)
        {
            msg.LimitPrice = BitConverter.ToInt64(data.Slice(0, 8));
            msg.OrderQuantity = BitConverter.ToUInt32(data.Slice(8, 4));
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(12, 4));
            msg.Side = data[16];
            msg.ClientOrderId = Encoding.ASCII.GetString(data.Slice(17, 10)).TrimEnd('\0');
            msg.OrigClientOrderId = Encoding.ASCII.GetString(data.Slice(27, 10)).TrimEnd('\0');
            msg.Account = BitConverter.ToUInt64(data.Slice(37, 8));
            msg.SenderLocation = Encoding.ASCII.GetString(data.Slice(45, 10)).TrimEnd('\0');
            msg.StopPrice = BitConverter.ToInt64(data.Slice(55, 8));
            msg.DisplayQuantity = BitConverter.ToUInt32(data.Slice(63, 4));
            msg.OrderBook = data[67];
            msg.OrderType = data[68];
            msg.TimeInForce = data[69];
            msg.ExpireTime = BitConverter.ToUInt64(data.Slice(70, 8));
        }

        public static void Encode(NativeOrderReplaceRequest msg, Span<byte> data)
        {
            BitConverter.TryWriteBytes(data.Slice(0, 8), msg.LimitPrice);
            BitConverter.TryWriteBytes(data.Slice(8, 4), msg.OrderQuantity);
            BitConverter.TryWriteBytes(data.Slice(12, 4), msg.InstrumentId);
            data[16] = msg.Side;
            Encoding.ASCII.GetBytes((msg.ClientOrderId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(17, 10));
            Encoding.ASCII.GetBytes((msg.OrigClientOrderId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(27, 10));
            BitConverter.TryWriteBytes(data.Slice(37, 8), msg.Account);
            Encoding.ASCII.GetBytes((msg.SenderLocation ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(45, 10));
            BitConverter.TryWriteBytes(data.Slice(55, 8), msg.StopPrice);
            BitConverter.TryWriteBytes(data.Slice(63, 4), msg.DisplayQuantity);
            data[67] = msg.OrderBook;
            data[68] = msg.OrderType;
            data[69] = msg.TimeInForce;
            BitConverter.TryWriteBytes(data.Slice(70, 8), msg.ExpireTime);
        }
    }
}
