using System;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent to indicate changes in trading status
    /// </summary>
    public class MitchSymbolStatus
    {
        /// <summary>
        /// Nanoseconds offset from the last Time message
        /// </summary>
        public uint Nanosecond { get; set; }

        /// <summary>
        /// Numeric Identifier of the instrument
        /// </summary>
        public uint InstrumentId { get; set; }

        /// <summary>
        /// H = Halt, T = Regular Trading, a = Opening Auction Call, b = Post-Close, c = Market Close, d = Closing Auction Call, e = Re-Opening Auction Call, l = Pause, w = No Active Session, x = End of Post Close, y = Pre-Trading
        /// </summary>
        public byte TradingStatus { get; set; }

        /// <summary>
        /// Reserved for future use
        /// </summary>
        public byte Flags { get; set; }

        /// <summary>
        /// Reason for the trading halt. This field will contain only spaces if Trading Status is not 'H'
        /// </summary>
        public string HaltReason { get; set; }

        /// <summary>
        /// 0 = Scheduled Transition, 1 = Extended by Market Ops, 2 = Shortened by Market Ops, 3 = Market Order Extension, 4 = Price Monitoring Extension, 5 = Circuit Breaker Tripped, 9 = Unavailable, 10 = No Cancel Period Start
        /// </summary>
        public byte SessionChangeReason { get; set; }

        /// <summary>
        /// New session end time specified in terms of local time for server (UTC) [Epoch in microseconds UTC]. The field will contain only spaces if Session Change Reason is '0' or '9'
        /// </summary>
        public ulong NewEndTime { get; set; }

        /// <summary>
        /// 1 = Regular, 2 = Off-Book, 9 = Bulletin Board, 11 = Negotiated Trades
        /// </summary>
        public byte SubBook { get; set; }

        public static void Parse(MitchSymbolStatus msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.TradingStatus = data[offset++];
            msg.Flags = data[offset++];
            msg.HaltReason = Encoding.ASCII.GetString(data.Slice(offset, 4)).TrimEnd();
            offset += 4;
            msg.SessionChangeReason = data[offset++];
            msg.NewEndTime = BitConverter.ToUInt64(data.Slice(offset, 8));
            offset += 8;
            msg.SubBook = data[offset++];
        }

        public static void Encode(MitchSymbolStatus msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            data[offset++] = (byte)msg.TradingStatus;
            data[offset++] = (byte)msg.Flags;
            var haltReasonBytes = Encoding.ASCII.GetBytes((msg.HaltReason ?? string.Empty).PadRight(4));
            haltReasonBytes.AsSpan(0, 4).CopyTo(data.Slice(offset, 4));
            offset += 4;
            data[offset++] = (byte)msg.SessionChangeReason;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.NewEndTime);
            offset += 8;
            data[offset++] = (byte)msg.SubBook;
        }
    }
}
