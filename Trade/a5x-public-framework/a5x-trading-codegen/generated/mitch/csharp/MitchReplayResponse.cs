using System;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent by server in response to replay request
    /// </summary>
    public class MitchReplayResponse
    {
        /// <summary>
        /// Identity of the market data group the replay request relates to
        /// </summary>
        public byte MarketDataGroup { get; set; }

        /// <summary>
        /// Sequence number of the first message in range to be retransmitted. This will be zero if Status is not 'A'
        /// </summary>
        public ulong FirstMessage { get; set; }

        /// <summary>
        /// Number of messages to be resent. This will be zero if Status is not 'A'
        /// </summary>
        public ushort Count { get; set; }

        /// <summary>
        /// A = Request Accepted, D = Request Limit Reached, I = Invalid Market Data Group, O = Out of Range, U = Replay Unavailable, c = Concurrent Limit Reached, d = Unsupported Message Type, e = Failed (Other)
        /// </summary>
        public byte Status { get; set; }

        public static void Parse(MitchReplayResponse msg, Span<byte> data)
        {
            int offset = 0;
            msg.MarketDataGroup = data[offset++];
            msg.FirstMessage = BitConverter.ToUInt64(data.Slice(offset, 8));
            offset += 8;
            msg.Count = BitConverter.ToUInt16(data.Slice(offset, 2));
            offset += 2;
            msg.Status = data[offset++];
        }

        public static void Encode(MitchReplayResponse msg, Span<byte> data)
        {
            int offset = 0;
            data[offset++] = (byte)msg.MarketDataGroup;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.FirstMessage);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 2), msg.Count);
            offset += 2;
            data[offset++] = (byte)msg.Status;
        }
    }
}
