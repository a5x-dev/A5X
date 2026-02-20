using System;
using System.Numerics;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent by client to request retransmission of messages
    /// </summary>
    public class MitchReplayRequest
    {
        /// <summary>
        /// Identity of the market data group the replay request relates to
        /// </summary>
        public byte MarketDataGroup { get; set; }

        /// <summary>
        /// Sequence number of the first message in range to be retransmitted
        /// </summary>
        public BigInteger FirstMessage { get; set; }

        /// <summary>
        /// Number of messages to be resent
        /// </summary>
        public object Count { get; set; }

        public static void Parse(MitchReplayRequest msg, Span<byte> data)
        {
            int offset = 0;
            msg.MarketDataGroup = data[offset++];
            msg.FirstMessage = new BigInteger(data.Slice(offset, 8));
            offset += 8;
        }

        public static void Encode(MitchReplayRequest msg, Span<byte> data)
        {
            int offset = 0;
            data[offset++] = (byte)msg.MarketDataGroup;
            msg.FirstMessage.TryWriteBytes(data.Slice(offset, 8), out _);
            offset += 8;
        }
    }
}
