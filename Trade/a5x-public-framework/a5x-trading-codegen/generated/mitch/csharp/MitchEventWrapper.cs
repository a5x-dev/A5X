using System;
using System.IO;

namespace A5X.Trading.Mitch
{
    /// <summary>
    /// Wrapper class for parsing and dispatching MITCH protocol messages.
    /// </summary>
    public class MitchEventWrapper
    {
        public char EventType { get; set; }
        public MitchTime Time { get; set; } = new MitchTime();
        public MitchSystemEvent SystemEvent { get; set; } = new MitchSystemEvent();
        public MitchAddOrder AddOrder { get; set; } = new MitchAddOrder();
        public MitchAddAttributedOrder AddAttributedOrder { get; set; } = new MitchAddAttributedOrder();
        public MitchOrderDeleted OrderDeleted { get; set; } = new MitchOrderDeleted();
        public MitchOrderModified OrderModified { get; set; } = new MitchOrderModified();
        public MitchOrderBookClear OrderBookClear { get; set; } = new MitchOrderBookClear();
        public MitchOrderExecuted OrderExecuted { get; set; } = new MitchOrderExecuted();
        public MitchOrderExecutedWithPriceSize OrderExecutedWithPriceSize { get; set; } = new MitchOrderExecutedWithPriceSize();
        public MitchAuctionInfo AuctionInfo { get; set; } = new MitchAuctionInfo();
        public MitchAuctionTrade AuctionTrade { get; set; } = new MitchAuctionTrade();
        public MitchRecoveryTrade RecoveryTrade { get; set; } = new MitchRecoveryTrade();
        public MitchStatistics Statistics { get; set; } = new MitchStatistics();
        public MitchExtendedStatistics ExtendedStatistics { get; set; } = new MitchExtendedStatistics();
        public MitchSymbolStatus SymbolStatus { get; set; } = new MitchSymbolStatus();
        public MitchTopOfBook TopOfBook { get; set; } = new MitchTopOfBook();
        public MitchTrade Trade { get; set; } = new MitchTrade();
        public MitchNews News { get; set; } = new MitchNews();
        public MitchLoginRequest LoginRequest { get; set; } = new MitchLoginRequest();
        public MitchLoginResponse LoginResponse { get; set; } = new MitchLoginResponse();
        public MitchLogoutRequest LogoutRequest { get; set; } = new MitchLogoutRequest();
        public MitchSymbolDirectory SymbolDirectory { get; set; } = new MitchSymbolDirectory();
        public MitchReplayRequest ReplayRequest { get; set; } = new MitchReplayRequest();
        public MitchReplayResponse ReplayResponse { get; set; } = new MitchReplayResponse();
        public MitchSnapshotRequest SnapshotRequest { get; set; } = new MitchSnapshotRequest();
        public MitchSnapshotResponse SnapshotResponse { get; set; } = new MitchSnapshotResponse();
        public MitchSnapshotComplete SnapshotComplete { get; set; } = new MitchSnapshotComplete();

        public void Parse(Span<byte> data)
        {
            int offset = 0;
            int initialPosition = offset;
            int length = BitConverter.ToUInt16(data.Slice(offset, 2));
            offset += 2;
            if (length == 0) return;

            EventType = (char)data[offset];
            offset += 1;

            switch (EventType)
            {
                case (char)0x54:
                    MitchTime.Parse(Time, data.Slice(offset));
                    break;
                case (char)0x53:
                    MitchSystemEvent.Parse(SystemEvent, data.Slice(offset));
                    break;
                case (char)0x41:
                    MitchAddOrder.Parse(AddOrder, data.Slice(offset));
                    break;
                case (char)0x46:
                    MitchAddAttributedOrder.Parse(AddAttributedOrder, data.Slice(offset));
                    break;
                case (char)0x44:
                    MitchOrderDeleted.Parse(OrderDeleted, data.Slice(offset));
                    break;
                case (char)0x55:
                    MitchOrderModified.Parse(OrderModified, data.Slice(offset));
                    break;
                case (char)0x79:
                    MitchOrderBookClear.Parse(OrderBookClear, data.Slice(offset));
                    break;
                case (char)0x45:
                    MitchOrderExecuted.Parse(OrderExecuted, data.Slice(offset));
                    break;
                case (char)0x43:
                    MitchOrderExecutedWithPriceSize.Parse(OrderExecutedWithPriceSize, data.Slice(offset));
                    break;
                case (char)0x49:
                    MitchAuctionInfo.Parse(AuctionInfo, data.Slice(offset));
                    break;
                case (char)0x51:
                    MitchAuctionTrade.Parse(AuctionTrade, data.Slice(offset));
                    break;
                case (char)0x76:
                    MitchRecoveryTrade.Parse(RecoveryTrade, data.Slice(offset));
                    break;
                case (char)0x77:
                    MitchStatistics.Parse(Statistics, data.Slice(offset));
                    break;
                case (char)0x80:
                    MitchExtendedStatistics.Parse(ExtendedStatistics, data.Slice(offset));
                    break;
                case (char)0x48:
                    MitchSymbolStatus.Parse(SymbolStatus, data.Slice(offset));
                    break;
                case (char)0x71:
                    MitchTopOfBook.Parse(TopOfBook, data.Slice(offset));
                    break;
                case (char)0x50:
                    MitchTrade.Parse(Trade, data.Slice(offset));
                    break;
                case (char)0x75:
                    MitchNews.Parse(News, data.Slice(offset));
                    break;
                case (char)0x01:
                    MitchLoginRequest.Parse(LoginRequest, data.Slice(offset));
                    break;
                case (char)0x02:
                    MitchLoginResponse.Parse(LoginResponse, data.Slice(offset));
                    break;
                case (char)0x05:
                    MitchLogoutRequest.Parse(LogoutRequest, data.Slice(offset));
                    break;
                case (char)0x52:
                    MitchSymbolDirectory.Parse(SymbolDirectory, data.Slice(offset));
                    break;
                case (char)0x03:
                    MitchReplayRequest.Parse(ReplayRequest, data.Slice(offset));
                    break;
                case (char)0x04:
                    MitchReplayResponse.Parse(ReplayResponse, data.Slice(offset));
                    break;
                case (char)0x81:
                    MitchSnapshotRequest.Parse(SnapshotRequest, data.Slice(offset));
                    break;
                case (char)0x82:
                    MitchSnapshotResponse.Parse(SnapshotResponse, data.Slice(offset));
                    break;
                case (char)0x83:
                    MitchSnapshotComplete.Parse(SnapshotComplete, data.Slice(offset));
                    break;
                default:
                    Console.WriteLine($"Unknown message type: {EventType} (0x{(int)EventType:X}), length: {length}");
                    break;
            }
        }
    }
}
