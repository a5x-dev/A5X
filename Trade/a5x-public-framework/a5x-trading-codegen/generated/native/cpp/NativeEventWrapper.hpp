#pragma once

#include <cstdint>
#include <stdexcept>
#include "NativeLogon.hpp"
#include "NativeLogonResponse.hpp"
#include "NativeHeartbeat.hpp"
#include "NativeReject.hpp"
#include "NativeLogout.hpp"
#include "NativeExecutionReport.hpp"
#include "NativeOrderCancelReject.hpp"
#include "NativeNewOrderSingle.hpp"
#include "NativeOrderCancelRequest.hpp"
#include "NativeOrderReplaceRequest.hpp"
#include "NativeMissedMessageRequest.hpp"
#include "NativeMissedMessageRequestAck.hpp"
#include "NativeTransmissionComplete.hpp"
#include "NativeSystemStatus.hpp"
#include "NativeNewOrderCross.hpp"
#include "NativeBusinessReject.hpp"
#include "NativeMassCancelRequest.hpp"
#include "NativeMassCancelReport.hpp"
#include "NativeNews.hpp"
#include "NativeMassQuote.hpp"
#include "NativeMassQuoteAcknowledgement.hpp"
#include "NativeQuoteStatusReport.hpp"

namespace a5x::trading::native {

class INativePackageHandler {
public:
    virtual ~INativePackageHandler() = default;
    virtual void onNativeLogon(const NativeLogon& msg) = 0;
    virtual void onNativeLogonResponse(const NativeLogonResponse& msg) = 0;
    virtual void onNativeHeartbeat(const NativeHeartbeat& msg) = 0;
    virtual void onNativeReject(const NativeReject& msg) = 0;
    virtual void onNativeLogout(const NativeLogout& msg) = 0;
    virtual void onNativeExecutionReport(const NativeExecutionReport& msg) = 0;
    virtual void onNativeOrderCancelReject(const NativeOrderCancelReject& msg) = 0;
    virtual void onNativeNewOrderSingle(const NativeNewOrderSingle& msg) = 0;
    virtual void onNativeOrderCancelRequest(const NativeOrderCancelRequest& msg) = 0;
    virtual void onNativeOrderReplaceRequest(const NativeOrderReplaceRequest& msg) = 0;
    virtual void onNativeMissedMessageRequest(const NativeMissedMessageRequest& msg) = 0;
    virtual void onNativeMissedMessageRequestAck(const NativeMissedMessageRequestAck& msg) = 0;
    virtual void onNativeTransmissionComplete(const NativeTransmissionComplete& msg) = 0;
    virtual void onNativeSystemStatus(const NativeSystemStatus& msg) = 0;
    virtual void onNativeNewOrderCross(const NativeNewOrderCross& msg) = 0;
    virtual void onNativeBusinessReject(const NativeBusinessReject& msg) = 0;
    virtual void onNativeMassCancelRequest(const NativeMassCancelRequest& msg) = 0;
    virtual void onNativeMassCancelReport(const NativeMassCancelReport& msg) = 0;
    virtual void onNativeNews(const NativeNews& msg) = 0;
    virtual void onNativeMassQuote(const NativeMassQuote& msg) = 0;
    virtual void onNativeMassQuoteAcknowledgement(const NativeMassQuoteAcknowledgement& msg) = 0;
    virtual void onNativeQuoteStatusReport(const NativeQuoteStatusReport& msg) = 0;
};

class NativeEventWrapper {
public:
    static constexpr uint8_t START_OF_MESSAGE = 0x02;

    static void parse(const uint8_t* data, INativePackageHandler& handler) {
        if (data[0] != START_OF_MESSAGE)
            throw std::runtime_error("Invalid start of message");

        uint16_t length;
        std::memcpy(&length, data + 1, 2);
        uint8_t messageType = data[3];

        switch (messageType) {
            case 'A': {
                NativeLogon nativeLogon;
                NativeLogon::parse(nativeLogon, data + 4);
                handler.onNativeLogon(nativeLogon);
                break;
            }
            case 'B': {
                NativeLogonResponse nativeLogonResponse;
                NativeLogonResponse::parse(nativeLogonResponse, data + 4);
                handler.onNativeLogonResponse(nativeLogonResponse);
                break;
            }
            case '0': {
                NativeHeartbeat nativeHeartbeat;
                NativeHeartbeat::parse(nativeHeartbeat, data + 4);
                handler.onNativeHeartbeat(nativeHeartbeat);
                break;
            }
            case '3': {
                NativeReject nativeReject;
                NativeReject::parse(nativeReject, data + 4);
                handler.onNativeReject(nativeReject);
                break;
            }
            case '5': {
                NativeLogout nativeLogout;
                NativeLogout::parse(nativeLogout, data + 4);
                handler.onNativeLogout(nativeLogout);
                break;
            }
            case '8': {
                NativeExecutionReport nativeExecutionReport;
                NativeExecutionReport::parse(nativeExecutionReport, data + 4);
                handler.onNativeExecutionReport(nativeExecutionReport);
                break;
            }
            case '9': {
                NativeOrderCancelReject nativeOrderCancelReject;
                NativeOrderCancelReject::parse(nativeOrderCancelReject, data + 4);
                handler.onNativeOrderCancelReject(nativeOrderCancelReject);
                break;
            }
            case 'D': {
                NativeNewOrderSingle nativeNewOrderSingle;
                NativeNewOrderSingle::parse(nativeNewOrderSingle, data + 4);
                handler.onNativeNewOrderSingle(nativeNewOrderSingle);
                break;
            }
            case 'F': {
                NativeOrderCancelRequest nativeOrderCancelRequest;
                NativeOrderCancelRequest::parse(nativeOrderCancelRequest, data + 4);
                handler.onNativeOrderCancelRequest(nativeOrderCancelRequest);
                break;
            }
            case 'G': {
                NativeOrderReplaceRequest nativeOrderReplaceRequest;
                NativeOrderReplaceRequest::parse(nativeOrderReplaceRequest, data + 4);
                handler.onNativeOrderReplaceRequest(nativeOrderReplaceRequest);
                break;
            }
            case 'M': {
                NativeMissedMessageRequest nativeMissedMessageRequest;
                NativeMissedMessageRequest::parse(nativeMissedMessageRequest, data + 4);
                handler.onNativeMissedMessageRequest(nativeMissedMessageRequest);
                break;
            }
            case 'N': {
                NativeMissedMessageRequestAck nativeMissedMessageRequestAck;
                NativeMissedMessageRequestAck::parse(nativeMissedMessageRequestAck, data + 4);
                handler.onNativeMissedMessageRequestAck(nativeMissedMessageRequestAck);
                break;
            }
            case 'P': {
                NativeTransmissionComplete nativeTransmissionComplete;
                NativeTransmissionComplete::parse(nativeTransmissionComplete, data + 4);
                handler.onNativeTransmissionComplete(nativeTransmissionComplete);
                break;
            }
            case 'n': {
                NativeSystemStatus nativeSystemStatus;
                NativeSystemStatus::parse(nativeSystemStatus, data + 4);
                handler.onNativeSystemStatus(nativeSystemStatus);
                break;
            }
            case 'u': {
                NativeNewOrderCross nativeNewOrderCross;
                NativeNewOrderCross::parse(nativeNewOrderCross, data + 4);
                handler.onNativeNewOrderCross(nativeNewOrderCross);
                break;
            }
            case 'j': {
                NativeBusinessReject nativeBusinessReject;
                NativeBusinessReject::parse(nativeBusinessReject, data + 4);
                handler.onNativeBusinessReject(nativeBusinessReject);
                break;
            }
            case 'q': {
                NativeMassCancelRequest nativeMassCancelRequest;
                NativeMassCancelRequest::parse(nativeMassCancelRequest, data + 4);
                handler.onNativeMassCancelRequest(nativeMassCancelRequest);
                break;
            }
            case 'r': {
                NativeMassCancelReport nativeMassCancelReport;
                NativeMassCancelReport::parse(nativeMassCancelReport, data + 4);
                handler.onNativeMassCancelReport(nativeMassCancelReport);
                break;
            }
            case 'CB': {
                NativeNews nativeNews;
                NativeNews::parse(nativeNews, data + 4);
                handler.onNativeNews(nativeNews);
                break;
            }
            case 'i': {
                NativeMassQuote nativeMassQuote;
                NativeMassQuote::parse(nativeMassQuote, data + 4);
                handler.onNativeMassQuote(nativeMassQuote);
                break;
            }
            case 'b': {
                NativeMassQuoteAcknowledgement nativeMassQuoteAcknowledgement;
                NativeMassQuoteAcknowledgement::parse(nativeMassQuoteAcknowledgement, data + 4);
                handler.onNativeMassQuoteAcknowledgement(nativeMassQuoteAcknowledgement);
                break;
            }
            case 'AI': {
                NativeQuoteStatusReport nativeQuoteStatusReport;
                NativeQuoteStatusReport::parse(nativeQuoteStatusReport, data + 4);
                handler.onNativeQuoteStatusReport(nativeQuoteStatusReport);
                break;
            }
            default:
                throw std::runtime_error("Unknown message type");
        }
    }
};

} // namespace a5x::trading::native
