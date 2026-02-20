package br.com.a5x.trd.pub.nativ;

import br.com.a5x.trd.pub.nativ.model.*;
import java.nio.ByteBuffer;

/**
 * Native protocol message dispatcher.
 * Parses message header and routes to appropriate message parser.
 */
public class NativeEventWrapper {

    public static final byte START_OF_MESSAGE = 0x02;

    public interface INativePackageHandler {
        void onNativeLogon(NativeLogon msg);
        void onNativeLogonResponse(NativeLogonResponse msg);
        void onNativeHeartbeat(NativeHeartbeat msg);
        void onNativeReject(NativeReject msg);
        void onNativeLogout(NativeLogout msg);
        void onNativeExecutionReport(NativeExecutionReport msg);
        void onNativeOrderCancelReject(NativeOrderCancelReject msg);
        void onNativeNewOrderSingle(NativeNewOrderSingle msg);
        void onNativeOrderCancelRequest(NativeOrderCancelRequest msg);
        void onNativeOrderReplaceRequest(NativeOrderReplaceRequest msg);
        void onNativeMissedMessageRequest(NativeMissedMessageRequest msg);
        void onNativeMissedMessageRequestAck(NativeMissedMessageRequestAck msg);
        void onNativeTransmissionComplete(NativeTransmissionComplete msg);
        void onNativeSystemStatus(NativeSystemStatus msg);
        void onNativeNewOrderCross(NativeNewOrderCross msg);
        void onNativeBusinessReject(NativeBusinessReject msg);
        void onNativeMassCancelRequest(NativeMassCancelRequest msg);
        void onNativeMassCancelReport(NativeMassCancelReport msg);
        void onNativeNews(NativeNews msg);
        void onNativeMassQuote(NativeMassQuote msg);
        void onNativeMassQuoteAcknowledgement(NativeMassQuoteAcknowledgement msg);
        void onNativeQuoteStatusReport(NativeQuoteStatusReport msg);
    }

    public static void parse(ByteBuffer data, INativePackageHandler handler) {
        byte startOfMessage = data.get();
        if (startOfMessage != START_OF_MESSAGE) {
            throw new IllegalArgumentException("Invalid start of message: " + startOfMessage);
        }

        int length = Short.toUnsignedInt(data.getShort());
        byte messageType = data.get();

        switch (messageType) {
            case 'A':
                NativeLogon nativeLogon = new NativeLogon();
                NativeLogon.parse(nativeLogon, data);
                handler.onNativeLogon(nativeLogon);
                break;
            case 'B':
                NativeLogonResponse nativeLogonResponse = new NativeLogonResponse();
                NativeLogonResponse.parse(nativeLogonResponse, data);
                handler.onNativeLogonResponse(nativeLogonResponse);
                break;
            case '0':
                NativeHeartbeat nativeHeartbeat = new NativeHeartbeat();
                NativeHeartbeat.parse(nativeHeartbeat, data);
                handler.onNativeHeartbeat(nativeHeartbeat);
                break;
            case '3':
                NativeReject nativeReject = new NativeReject();
                NativeReject.parse(nativeReject, data);
                handler.onNativeReject(nativeReject);
                break;
            case '5':
                NativeLogout nativeLogout = new NativeLogout();
                NativeLogout.parse(nativeLogout, data);
                handler.onNativeLogout(nativeLogout);
                break;
            case '8':
                NativeExecutionReport nativeExecutionReport = new NativeExecutionReport();
                NativeExecutionReport.parse(nativeExecutionReport, data);
                handler.onNativeExecutionReport(nativeExecutionReport);
                break;
            case '9':
                NativeOrderCancelReject nativeOrderCancelReject = new NativeOrderCancelReject();
                NativeOrderCancelReject.parse(nativeOrderCancelReject, data);
                handler.onNativeOrderCancelReject(nativeOrderCancelReject);
                break;
            case 'D':
                NativeNewOrderSingle nativeNewOrderSingle = new NativeNewOrderSingle();
                NativeNewOrderSingle.parse(nativeNewOrderSingle, data);
                handler.onNativeNewOrderSingle(nativeNewOrderSingle);
                break;
            case 'F':
                NativeOrderCancelRequest nativeOrderCancelRequest = new NativeOrderCancelRequest();
                NativeOrderCancelRequest.parse(nativeOrderCancelRequest, data);
                handler.onNativeOrderCancelRequest(nativeOrderCancelRequest);
                break;
            case 'G':
                NativeOrderReplaceRequest nativeOrderReplaceRequest = new NativeOrderReplaceRequest();
                NativeOrderReplaceRequest.parse(nativeOrderReplaceRequest, data);
                handler.onNativeOrderReplaceRequest(nativeOrderReplaceRequest);
                break;
            case 'M':
                NativeMissedMessageRequest nativeMissedMessageRequest = new NativeMissedMessageRequest();
                NativeMissedMessageRequest.parse(nativeMissedMessageRequest, data);
                handler.onNativeMissedMessageRequest(nativeMissedMessageRequest);
                break;
            case 'N':
                NativeMissedMessageRequestAck nativeMissedMessageRequestAck = new NativeMissedMessageRequestAck();
                NativeMissedMessageRequestAck.parse(nativeMissedMessageRequestAck, data);
                handler.onNativeMissedMessageRequestAck(nativeMissedMessageRequestAck);
                break;
            case 'P':
                NativeTransmissionComplete nativeTransmissionComplete = new NativeTransmissionComplete();
                NativeTransmissionComplete.parse(nativeTransmissionComplete, data);
                handler.onNativeTransmissionComplete(nativeTransmissionComplete);
                break;
            case 'n':
                NativeSystemStatus nativeSystemStatus = new NativeSystemStatus();
                NativeSystemStatus.parse(nativeSystemStatus, data);
                handler.onNativeSystemStatus(nativeSystemStatus);
                break;
            case 'u':
                NativeNewOrderCross nativeNewOrderCross = new NativeNewOrderCross();
                NativeNewOrderCross.parse(nativeNewOrderCross, data);
                handler.onNativeNewOrderCross(nativeNewOrderCross);
                break;
            case 'j':
                NativeBusinessReject nativeBusinessReject = new NativeBusinessReject();
                NativeBusinessReject.parse(nativeBusinessReject, data);
                handler.onNativeBusinessReject(nativeBusinessReject);
                break;
            case 'q':
                NativeMassCancelRequest nativeMassCancelRequest = new NativeMassCancelRequest();
                NativeMassCancelRequest.parse(nativeMassCancelRequest, data);
                handler.onNativeMassCancelRequest(nativeMassCancelRequest);
                break;
            case 'r':
                NativeMassCancelReport nativeMassCancelReport = new NativeMassCancelReport();
                NativeMassCancelReport.parse(nativeMassCancelReport, data);
                handler.onNativeMassCancelReport(nativeMassCancelReport);
                break;
            case 'CB':
                NativeNews nativeNews = new NativeNews();
                NativeNews.parse(nativeNews, data);
                handler.onNativeNews(nativeNews);
                break;
            case 'i':
                NativeMassQuote nativeMassQuote = new NativeMassQuote();
                NativeMassQuote.parse(nativeMassQuote, data);
                handler.onNativeMassQuote(nativeMassQuote);
                break;
            case 'b':
                NativeMassQuoteAcknowledgement nativeMassQuoteAcknowledgement = new NativeMassQuoteAcknowledgement();
                NativeMassQuoteAcknowledgement.parse(nativeMassQuoteAcknowledgement, data);
                handler.onNativeMassQuoteAcknowledgement(nativeMassQuoteAcknowledgement);
                break;
            case 'AI':
                NativeQuoteStatusReport nativeQuoteStatusReport = new NativeQuoteStatusReport();
                NativeQuoteStatusReport.parse(nativeQuoteStatusReport, data);
                handler.onNativeQuoteStatusReport(nativeQuoteStatusReport);
                break;
            default:
                throw new IllegalArgumentException("Unknown message type: " + (char) messageType);
        }
    }

    public static void encode(ByteBuffer data, Object msg) {
        data.put(START_OF_MESSAGE);
        int lengthPos = data.position();
        data.putShort((short) 0);
        int startPos = data.position();

        if (msg instanceof NativeLogon) {
            data.put((byte) 'A');
            NativeLogon.encode((NativeLogon) msg, data);
        } else if (msg instanceof NativeLogonResponse) {
            data.put((byte) 'B');
            NativeLogonResponse.encode((NativeLogonResponse) msg, data);
        } else if (msg instanceof NativeHeartbeat) {
            data.put((byte) '0');
            NativeHeartbeat.encode((NativeHeartbeat) msg, data);
        } else if (msg instanceof NativeReject) {
            data.put((byte) '3');
            NativeReject.encode((NativeReject) msg, data);
        } else if (msg instanceof NativeLogout) {
            data.put((byte) '5');
            NativeLogout.encode((NativeLogout) msg, data);
        } else if (msg instanceof NativeExecutionReport) {
            data.put((byte) '8');
            NativeExecutionReport.encode((NativeExecutionReport) msg, data);
        } else if (msg instanceof NativeOrderCancelReject) {
            data.put((byte) '9');
            NativeOrderCancelReject.encode((NativeOrderCancelReject) msg, data);
        } else if (msg instanceof NativeNewOrderSingle) {
            data.put((byte) 'D');
            NativeNewOrderSingle.encode((NativeNewOrderSingle) msg, data);
        } else if (msg instanceof NativeOrderCancelRequest) {
            data.put((byte) 'F');
            NativeOrderCancelRequest.encode((NativeOrderCancelRequest) msg, data);
        } else if (msg instanceof NativeOrderReplaceRequest) {
            data.put((byte) 'G');
            NativeOrderReplaceRequest.encode((NativeOrderReplaceRequest) msg, data);
        } else if (msg instanceof NativeMissedMessageRequest) {
            data.put((byte) 'M');
            NativeMissedMessageRequest.encode((NativeMissedMessageRequest) msg, data);
        } else if (msg instanceof NativeMissedMessageRequestAck) {
            data.put((byte) 'N');
            NativeMissedMessageRequestAck.encode((NativeMissedMessageRequestAck) msg, data);
        } else if (msg instanceof NativeTransmissionComplete) {
            data.put((byte) 'P');
            NativeTransmissionComplete.encode((NativeTransmissionComplete) msg, data);
        } else if (msg instanceof NativeSystemStatus) {
            data.put((byte) 'n');
            NativeSystemStatus.encode((NativeSystemStatus) msg, data);
        } else if (msg instanceof NativeNewOrderCross) {
            data.put((byte) 'u');
            NativeNewOrderCross.encode((NativeNewOrderCross) msg, data);
        } else if (msg instanceof NativeBusinessReject) {
            data.put((byte) 'j');
            NativeBusinessReject.encode((NativeBusinessReject) msg, data);
        } else if (msg instanceof NativeMassCancelRequest) {
            data.put((byte) 'q');
            NativeMassCancelRequest.encode((NativeMassCancelRequest) msg, data);
        } else if (msg instanceof NativeMassCancelReport) {
            data.put((byte) 'r');
            NativeMassCancelReport.encode((NativeMassCancelReport) msg, data);
        } else if (msg instanceof NativeNews) {
            data.put((byte) 'CB');
            NativeNews.encode((NativeNews) msg, data);
        } else if (msg instanceof NativeMassQuote) {
            data.put((byte) 'i');
            NativeMassQuote.encode((NativeMassQuote) msg, data);
        } else if (msg instanceof NativeMassQuoteAcknowledgement) {
            data.put((byte) 'b');
            NativeMassQuoteAcknowledgement.encode((NativeMassQuoteAcknowledgement) msg, data);
        } else if (msg instanceof NativeQuoteStatusReport) {
            data.put((byte) 'AI');
            NativeQuoteStatusReport.encode((NativeQuoteStatusReport) msg, data);
        } else {
            throw new IllegalArgumentException("Unknown message type: " + msg.getClass().getName());
        }

        int endPos = data.position();
        data.putShort(lengthPos, (short) (endPos - startPos));
    }
}
