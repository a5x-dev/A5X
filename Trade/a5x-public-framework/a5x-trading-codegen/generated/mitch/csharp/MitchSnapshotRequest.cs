using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent by client to request snapshot data
    /// </summary>
    public class MitchSnapshotRequest
    {
        /// <summary>
        /// Sequence number from which client can build the order book
        /// </summary>
        public BigInteger SequenceNumber { get; set; }

        /// <summary>
        /// Segment the request relates to. The field should contain only spaces if it does not relate to a segment
        /// </summary>
        public string Segment { get; set; }

        /// <summary>
        /// Instrument ID of the instrument that the request relates to. The field should contain only spaces if it does not relate to an instrument. This field is ignored if Segment is specified
        /// </summary>
        public uint InstrumentId { get; set; }

        /// <summary>
        /// Bit 0: Regular (0=No, 1=Yes), Bit 1: Off-Book (0=No, 1=Yes), Bit 5: Bulletin Board (0=No, 1=Yes), Bit 6: Negotiated Trades (0=No, 1=Yes)
        /// </summary>
        public object SubBook { get; set; }

        /// <summary>
        /// 0=Order Book, 1=Symbol Status, 2=Instrument, 3=Trades, 4=Statistics, 5=News, 8=Top of Book
        /// </summary>
        public byte SnapshotType { get; set; }

        /// <summary>
        /// Sending time of the last processed trade (EPOCH time in seconds). This field is ignored if the Snapshot Type is not Trades (3) or News (5)
        /// </summary>
        public BigInteger RecoverFromTime { get; set; }

        /// <summary>
        /// Optional identifier of the request
        /// </summary>
        public uint RequestId { get; set; }

        public static void Parse(MitchSnapshotRequest msg, Span<byte> data)
        {
            int offset = 0;
            msg.SequenceNumber = new BigInteger(data.Slice(offset, 8));
            offset += 8;
            msg.Segment = Encoding.ASCII.GetString(data.Slice(offset, 10)).TrimEnd();
            offset += 10;
            msg.InstrumentId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
            msg.SnapshotType = data[offset++];
            msg.RecoverFromTime = new BigInteger(data.Slice(offset, 8));
            offset += 8;
            msg.RequestId = BitConverter.ToUInt32(data.Slice(offset, 4));
            offset += 4;
        }

        public static void Encode(MitchSnapshotRequest msg, Span<byte> data)
        {
            int offset = 0;
            msg.SequenceNumber.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
            var segmentBytes = Encoding.ASCII.GetBytes(msg.Segment.PadRight(10));
            segmentBytes.AsSpan(0, 10).CopyTo(data.Slice(offset, 10));
            offset += 10;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            data[offset++] = (byte)msg.SnapshotType;
            msg.RecoverFromTime.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.RequestId);
            offset += 4;
        }
    }
}
