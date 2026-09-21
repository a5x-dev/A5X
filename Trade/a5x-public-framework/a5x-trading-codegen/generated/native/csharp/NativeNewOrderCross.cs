using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// New Order Cross
    /// Message Type: C
    /// </summary>
    public class NativeNewOrderCross
    {
        /// <summary>Identifier of the cross order. Only alphanumeric values allowed</summary>
        public string CrossId { get; set; }

        /// <summary>Price of the cross order</summary>
        public long LimitPrice { get; set; }

        /// <summary>Total order quantity of the cross order</summary>
        public uint OrderQuantity { get; set; }

        /// <summary>Entering Trader ID</summary>
        public string EnteringTrader { get; set; }

        /// <summary>Numeric Identifier of the instrument</summary>
        public uint InstrumentId { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm</summary>
        public string SenderLocation { get; set; }

        /// <summary>5 = Internal Cross. Any other value rejected</summary>
        public byte CrossType { get; set; }

        /// <summary>Client specified identifier of the buy side. Required</summary>
        public string BuySideClientOrderId { get; set; }

        /// <summary>Identifier of the buyer's trading account. Required</summary>
        public ulong BuySideAccountId { get; set; }

        /// <summary>Client specified identifier of the sell side. Required</summary>
        public string SellSideClientOrderId { get; set; }

        /// <summary>Identifier of the seller's trading account. Required</summary>
        public ulong SellSideAccountId { get; set; }

        /// <summary>2 = Limit. Any other value rejected</summary>
        public byte OrderType { get; set; }

        /// <summary>0 = Day. Only DAY TIF allowed for cross orders</summary>
        public byte TimeInForce { get; set; }

        /// <summary>Desk ID through which the order is placed from</summary>
        public string DeskId { get; set; }

        /// <summary>Free format text field. Echoed in reports</summary>
        public string Memo { get; set; }

        public static void Parse(NativeNewOrderCross msg, Span<byte> data)
        {
            msg.CrossId = Encoding.ASCII.GetString(data.Slice(0, 10)).TrimEnd('\0');
            msg.LimitPrice = BitConverter.ToInt64(data.Slice(10, 8));
            msg.OrderQuantity = BitConverter.ToUInt32(data.Slice(18, 4));
            msg.EnteringTrader = Encoding.ASCII.GetString(data.Slice(22, 15)).TrimEnd('\0');
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(37, 4));
            msg.SenderLocation = Encoding.ASCII.GetString(data.Slice(41, 10)).TrimEnd('\0');
            msg.CrossType = data[51];
            msg.BuySideClientOrderId = Encoding.ASCII.GetString(data.Slice(52, 10)).TrimEnd('\0');
            msg.BuySideAccountId = BitConverter.ToUInt64(data.Slice(62, 8));
            msg.SellSideClientOrderId = Encoding.ASCII.GetString(data.Slice(70, 10)).TrimEnd('\0');
            msg.SellSideAccountId = BitConverter.ToUInt64(data.Slice(80, 8));
            msg.OrderType = data[88];
            msg.TimeInForce = data[89];
            msg.DeskId = Encoding.ASCII.GetString(data.Slice(90, 15)).TrimEnd('\0');
            msg.Memo = Encoding.ASCII.GetString(data.Slice(105, 15)).TrimEnd('\0');
        }

        public static void Encode(NativeNewOrderCross msg, Span<byte> data)
        {
            Encoding.ASCII.GetBytes((msg.CrossId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(0, 10));
            BitConverter.TryWriteBytes(data.Slice(10, 8), msg.LimitPrice);
            BitConverter.TryWriteBytes(data.Slice(18, 4), msg.OrderQuantity);
            Encoding.ASCII.GetBytes((msg.EnteringTrader ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(22, 15));
            BitConverter.TryWriteBytes(data.Slice(37, 4), msg.InstrumentId);
            Encoding.ASCII.GetBytes((msg.SenderLocation ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(41, 10));
            data[51] = msg.CrossType;
            Encoding.ASCII.GetBytes((msg.BuySideClientOrderId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(52, 10));
            BitConverter.TryWriteBytes(data.Slice(62, 8), msg.BuySideAccountId);
            Encoding.ASCII.GetBytes((msg.SellSideClientOrderId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(70, 10));
            BitConverter.TryWriteBytes(data.Slice(80, 8), msg.SellSideAccountId);
            data[88] = msg.OrderType;
            data[89] = msg.TimeInForce;
            Encoding.ASCII.GetBytes((msg.DeskId ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(90, 15));
            Encoding.ASCII.GetBytes((msg.Memo ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(105, 15));
        }
    }
}
