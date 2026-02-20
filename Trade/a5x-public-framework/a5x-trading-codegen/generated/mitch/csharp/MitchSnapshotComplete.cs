using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent by server to indicate snapshot transmission is complete
    /// </summary>
    public class MitchSnapshotComplete
    {
        /// <summary>
        /// Sequence number with which the snapshot is synchronized
        /// </summary>
        public BigInteger SequenceNumber { get; set; }

        /// <summary>
        /// Segment the snapshot relates to. The field will contain only spaces if it does not relate to a segment
        /// </summary>
        public string Segment { get; set; }

        /// <summary>
        /// Instrument ID of the instrument that the snapshot relates to. The field will contain only spaces if it does not relate to an instrument
        /// </summary>
        public uint InstrumentId { get; set; }

        /// <summary>
        /// Bit 0: Regular (0=No, 1=Yes), Bit 1: Off-Book (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes), Bit 6: Negotiated Trades (0=No, 1=Yes)
        /// </summary>
        public object SubBook { get; set; }

        /// <summary>
        /// H=Halt, T=Regular Trading, a=Opening Auction Call, b=Post-Close, c=Market Closed, d=Closing Auction Call, e=Re-Opening Auction Call, I=Pause, n=Order Entry, u=Closing Price Cross, w=No Active Session, y=Pre-Trading, m=No Cancel Period. Only indicated if message is sent as book level complete and Snapshot Type is Order Book (0) or Statistics (4)
        /// </summary>
        public byte TradingStatus { get; set; }

        /// <summary>
        /// 0=Order Book, 1=Symbol Status, 2=Instrument, 3=Trades, 4=Statistics, 5=News, 8=Top of Book
        /// </summary>
        public byte SnapshotType { get; set; }

        /// <summary>
        /// Identifier, if any, of Snapshot Request
        /// </summary>
        public uint RequestId { get; set; }

        public static void Parse(MitchSnapshotComplete msg, Span<byte> data)
        {
            int offset = 0;
            msg.SequenceNumber = new BigInteger(data.Slice(offset, 8));
            offset += 8;
            msg.Segment = Encoding.ASCII.GetString(data.Slice(offset, 10)).TrimEnd();
            offset += 10;
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.TradingStatus = data[offset++];
            msg.SnapshotType = data[offset++];
            msg.RequestId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
        }

        public static void Encode(MitchSnapshotComplete msg, Span<byte> data)
        {
            int offset = 0;
            msg.SequenceNumber.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
            var segmentBytes = Encoding.ASCII.GetBytes(msg.Segment.PadRight(10));
            segmentBytes.AsSpan(0, 10).CopyTo(data.Slice(offset, 10));
            offset += 10;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            data[offset++] = (byte)msg.TradingStatus;
            data[offset++] = (byte)msg.SnapshotType;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.RequestId);
            offset += 4;
        }
    }
}
