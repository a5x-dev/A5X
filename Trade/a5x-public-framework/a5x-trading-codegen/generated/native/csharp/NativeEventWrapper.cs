using System;

namespace A5X.Trading.Native
{
    public interface INativePackageHandler
    {
        void OnNativeLogon(NativeLogon msg);
        void OnNativeLogonResponse(NativeLogonResponse msg);
        void OnNativeHeartbeat(NativeHeartbeat msg);
        void OnNativeReject(NativeReject msg);
        void OnNativeLogout(NativeLogout msg);
        void OnNativeExecutionReport(NativeExecutionReport msg);
        void OnNativeOrderCancelReject(NativeOrderCancelReject msg);
        void OnNativeNewOrderSingle(NativeNewOrderSingle msg);
        void OnNativeOrderCancelRequest(NativeOrderCancelRequest msg);
        void OnNativeOrderReplaceRequest(NativeOrderReplaceRequest msg);
        void OnNativeMissedMessageRequest(NativeMissedMessageRequest msg);
        void OnNativeMissedMessageRequestAck(NativeMissedMessageRequestAck msg);
        void OnNativeTransmissionComplete(NativeTransmissionComplete msg);
        void OnNativeSystemStatus(NativeSystemStatus msg);
        void OnNativeNewOrderCross(NativeNewOrderCross msg);
        void OnNativeBusinessReject(NativeBusinessReject msg);
        void OnNativeMassCancelRequest(NativeMassCancelRequest msg);
        void OnNativeMassCancelReport(NativeMassCancelReport msg);
        void OnNativeNews(NativeNews msg);
        void OnNativeMassQuote(NativeMassQuote msg);
        void OnNativeMassQuoteAcknowledgement(NativeMassQuoteAcknowledgement msg);
        void OnNativeQuoteStatusReport(NativeQuoteStatusReport msg);
    }

    public static class NativeEventWrapper
    {
        public const byte START_OF_MESSAGE = 0x02;

        public static void Parse(Span<byte> data, INativePackageHandler handler)
        {
            if (data[0] != START_OF_MESSAGE)
                throw new ArgumentException($"Invalid start of message: {data[0]}");

            ushort length = BitConverter.ToUInt16(data.Slice(1, 2));
            byte messageType = data[3];

            switch ((char)messageType)
            {
                case 'A':
                    var nativeLogon = new NativeLogon();
                    NativeLogon.Parse(nativeLogon, data.Slice(4));
                    handler.OnNativeLogon(nativeLogon);
                    break;
                case 'B':
                    var nativeLogonResponse = new NativeLogonResponse();
                    NativeLogonResponse.Parse(nativeLogonResponse, data.Slice(4));
                    handler.OnNativeLogonResponse(nativeLogonResponse);
                    break;
                case '0':
                    var nativeHeartbeat = new NativeHeartbeat();
                    NativeHeartbeat.Parse(nativeHeartbeat, data.Slice(4));
                    handler.OnNativeHeartbeat(nativeHeartbeat);
                    break;
                case '3':
                    var nativeReject = new NativeReject();
                    NativeReject.Parse(nativeReject, data.Slice(4));
                    handler.OnNativeReject(nativeReject);
                    break;
                case '5':
                    var nativeLogout = new NativeLogout();
                    NativeLogout.Parse(nativeLogout, data.Slice(4));
                    handler.OnNativeLogout(nativeLogout);
                    break;
                case '8':
                    var nativeExecutionReport = new NativeExecutionReport();
                    NativeExecutionReport.Parse(nativeExecutionReport, data.Slice(4));
                    handler.OnNativeExecutionReport(nativeExecutionReport);
                    break;
                case '9':
                    var nativeOrderCancelReject = new NativeOrderCancelReject();
                    NativeOrderCancelReject.Parse(nativeOrderCancelReject, data.Slice(4));
                    handler.OnNativeOrderCancelReject(nativeOrderCancelReject);
                    break;
                case 'D':
                    var nativeNewOrderSingle = new NativeNewOrderSingle();
                    NativeNewOrderSingle.Parse(nativeNewOrderSingle, data.Slice(4));
                    handler.OnNativeNewOrderSingle(nativeNewOrderSingle);
                    break;
                case 'F':
                    var nativeOrderCancelRequest = new NativeOrderCancelRequest();
                    NativeOrderCancelRequest.Parse(nativeOrderCancelRequest, data.Slice(4));
                    handler.OnNativeOrderCancelRequest(nativeOrderCancelRequest);
                    break;
                case 'G':
                    var nativeOrderReplaceRequest = new NativeOrderReplaceRequest();
                    NativeOrderReplaceRequest.Parse(nativeOrderReplaceRequest, data.Slice(4));
                    handler.OnNativeOrderReplaceRequest(nativeOrderReplaceRequest);
                    break;
                case 'M':
                    var nativeMissedMessageRequest = new NativeMissedMessageRequest();
                    NativeMissedMessageRequest.Parse(nativeMissedMessageRequest, data.Slice(4));
                    handler.OnNativeMissedMessageRequest(nativeMissedMessageRequest);
                    break;
                case 'N':
                    var nativeMissedMessageRequestAck = new NativeMissedMessageRequestAck();
                    NativeMissedMessageRequestAck.Parse(nativeMissedMessageRequestAck, data.Slice(4));
                    handler.OnNativeMissedMessageRequestAck(nativeMissedMessageRequestAck);
                    break;
                case 'P':
                    var nativeTransmissionComplete = new NativeTransmissionComplete();
                    NativeTransmissionComplete.Parse(nativeTransmissionComplete, data.Slice(4));
                    handler.OnNativeTransmissionComplete(nativeTransmissionComplete);
                    break;
                case 'n':
                    var nativeSystemStatus = new NativeSystemStatus();
                    NativeSystemStatus.Parse(nativeSystemStatus, data.Slice(4));
                    handler.OnNativeSystemStatus(nativeSystemStatus);
                    break;
                case 'u':
                    var nativeNewOrderCross = new NativeNewOrderCross();
                    NativeNewOrderCross.Parse(nativeNewOrderCross, data.Slice(4));
                    handler.OnNativeNewOrderCross(nativeNewOrderCross);
                    break;
                case 'j':
                    var nativeBusinessReject = new NativeBusinessReject();
                    NativeBusinessReject.Parse(nativeBusinessReject, data.Slice(4));
                    handler.OnNativeBusinessReject(nativeBusinessReject);
                    break;
                case 'q':
                    var nativeMassCancelRequest = new NativeMassCancelRequest();
                    NativeMassCancelRequest.Parse(nativeMassCancelRequest, data.Slice(4));
                    handler.OnNativeMassCancelRequest(nativeMassCancelRequest);
                    break;
                case 'r':
                    var nativeMassCancelReport = new NativeMassCancelReport();
                    NativeMassCancelReport.Parse(nativeMassCancelReport, data.Slice(4));
                    handler.OnNativeMassCancelReport(nativeMassCancelReport);
                    break;
                case 'CB':
                    var nativeNews = new NativeNews();
                    NativeNews.Parse(nativeNews, data.Slice(4));
                    handler.OnNativeNews(nativeNews);
                    break;
                case 'i':
                    var nativeMassQuote = new NativeMassQuote();
                    NativeMassQuote.Parse(nativeMassQuote, data.Slice(4));
                    handler.OnNativeMassQuote(nativeMassQuote);
                    break;
                case 'b':
                    var nativeMassQuoteAcknowledgement = new NativeMassQuoteAcknowledgement();
                    NativeMassQuoteAcknowledgement.Parse(nativeMassQuoteAcknowledgement, data.Slice(4));
                    handler.OnNativeMassQuoteAcknowledgement(nativeMassQuoteAcknowledgement);
                    break;
                case 'AI':
                    var nativeQuoteStatusReport = new NativeQuoteStatusReport();
                    NativeQuoteStatusReport.Parse(nativeQuoteStatusReport, data.Slice(4));
                    handler.OnNativeQuoteStatusReport(nativeQuoteStatusReport);
                    break;
                default:
                    throw new ArgumentException($"Unknown message type: {(char)messageType}");
            }
        }
    }
}
