#ifndef MITCH_EVENT_WRAPPER_H
#define MITCH_EVENT_WRAPPER_H

#include <cstdint>
#include <cstring>
#include <iostream>
#include "MitchTime.h"
#include "MitchSystemEvent.h"
#include "MitchAddOrder.h"
#include "MitchAddAttributedOrder.h"
#include "MitchOrderDeleted.h"
#include "MitchOrderModified.h"
#include "MitchOrderBookClear.h"
#include "MitchOrderExecuted.h"
#include "MitchOrderExecutedWithPriceSize.h"
#include "MitchAuctionInfo.h"
#include "MitchAuctionTrade.h"
#include "MitchRecoveryTrade.h"
#include "MitchStatistics.h"
#include "MitchExtendedStatistics.h"
#include "MitchSymbolStatus.h"
#include "MitchTopOfBook.h"
#include "MitchTrade.h"
#include "MitchNews.h"
#include "MitchLoginRequest.h"
#include "MitchLoginResponse.h"
#include "MitchLogoutRequest.h"
#include "MitchSymbolDirectory.h"
#include "MitchReplayRequest.h"
#include "MitchReplayResponse.h"
#include "MitchSnapshotRequest.h"
#include "MitchSnapshotResponse.h"
#include "MitchSnapshotComplete.h"

namespace a5x {
namespace mitch {

class MitchEventWrapper {
public:
    char eventType;
    MitchTime time;
    MitchSystemEvent systemEvent;
    MitchAddOrder addOrder;
    MitchAddAttributedOrder addAttributedOrder;
    MitchOrderDeleted orderDeleted;
    MitchOrderModified orderModified;
    MitchOrderBookClear orderBookClear;
    MitchOrderExecuted orderExecuted;
    MitchOrderExecutedWithPriceSize orderExecutedWithPriceSize;
    MitchAuctionInfo auctionInfo;
    MitchAuctionTrade auctionTrade;
    MitchRecoveryTrade recoveryTrade;
    MitchStatistics statistics;
    MitchExtendedStatistics extendedStatistics;
    MitchSymbolStatus symbolStatus;
    MitchTopOfBook topOfBook;
    MitchTrade trade;
    MitchNews news;
    MitchLoginRequest loginRequest;
    MitchLoginResponse loginResponse;
    MitchLogoutRequest logoutRequest;
    MitchSymbolDirectory symbolDirectory;
    MitchReplayRequest replayRequest;
    MitchReplayResponse replayResponse;
    MitchSnapshotRequest snapshotRequest;
    MitchSnapshotResponse snapshotResponse;
    MitchSnapshotComplete snapshotComplete;

    void parse(const uint8_t* data, size_t length) {
        size_t offset = 0;
        size_t initialPosition = offset;
        uint16_t msgLength;
        std::memcpy(&msgLength, data + offset, 2);
        offset += 2;
        if (msgLength == 0) return;

        eventType = static_cast<char>(data[offset]);
        offset += 1;

        switch (eventType) {
            case 0x54:
                MitchTime::parse(&time, data + offset);
                break;
            case 0x53:
                MitchSystemEvent::parse(&systemEvent, data + offset);
                break;
            case 0x41:
                MitchAddOrder::parse(&addOrder, data + offset);
                break;
            case 0x46:
                MitchAddAttributedOrder::parse(&addAttributedOrder, data + offset);
                break;
            case 0x44:
                MitchOrderDeleted::parse(&orderDeleted, data + offset);
                break;
            case 0x55:
                MitchOrderModified::parse(&orderModified, data + offset);
                break;
            case 0x79:
                MitchOrderBookClear::parse(&orderBookClear, data + offset);
                break;
            case 0x45:
                MitchOrderExecuted::parse(&orderExecuted, data + offset);
                break;
            case 0x43:
                MitchOrderExecutedWithPriceSize::parse(&orderExecutedWithPriceSize, data + offset);
                break;
            case 0x49:
                MitchAuctionInfo::parse(&auctionInfo, data + offset);
                break;
            case 0x51:
                MitchAuctionTrade::parse(&auctionTrade, data + offset);
                break;
            case 0x76:
                MitchRecoveryTrade::parse(&recoveryTrade, data + offset);
                break;
            case 0x77:
                MitchStatistics::parse(&statistics, data + offset);
                break;
            case 0x80:
                MitchExtendedStatistics::parse(&extendedStatistics, data + offset);
                break;
            case 0x48:
                MitchSymbolStatus::parse(&symbolStatus, data + offset);
                break;
            case 0x71:
                MitchTopOfBook::parse(&topOfBook, data + offset);
                break;
            case 0x50:
                MitchTrade::parse(&trade, data + offset);
                break;
            case 0x75:
                MitchNews::parse(&news, data + offset);
                break;
            case 0x01:
                MitchLoginRequest::parse(&loginRequest, data + offset);
                break;
            case 0x02:
                MitchLoginResponse::parse(&loginResponse, data + offset);
                break;
            case 0x05:
                MitchLogoutRequest::parse(&logoutRequest, data + offset);
                break;
            case 0x52:
                MitchSymbolDirectory::parse(&symbolDirectory, data + offset);
                break;
            case 0x03:
                MitchReplayRequest::parse(&replayRequest, data + offset);
                break;
            case 0x04:
                MitchReplayResponse::parse(&replayResponse, data + offset);
                break;
            case 0x81:
                MitchSnapshotRequest::parse(&snapshotRequest, data + offset);
                break;
            case 0x82:
                MitchSnapshotResponse::parse(&snapshotResponse, data + offset);
                break;
            case 0x83:
                MitchSnapshotComplete::parse(&snapshotComplete, data + offset);
                break;
            default:
                std::cout << "Unknown message type: " << eventType << " (0x" << std::hex << (int)eventType << "), length: " << msgLength << std::endl;
                break;
        }
    }
};

}  // namespace mitch
}  // namespace a5x

#endif  // MITCH_EVENT_WRAPPER_H
