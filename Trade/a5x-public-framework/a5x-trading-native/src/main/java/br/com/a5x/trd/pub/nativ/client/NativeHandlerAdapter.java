package br.com.a5x.trd.pub.nativ.client;

import br.com.a5x.trd.pub.nativ.NativeEventWrapper.INativePackageHandler;
import br.com.a5x.trd.pub.nativ.model.NativeBusinessReject;
import br.com.a5x.trd.pub.nativ.model.NativeExecutionReport;
import br.com.a5x.trd.pub.nativ.model.NativeHeartbeat;
import br.com.a5x.trd.pub.nativ.model.NativeLogon;
import br.com.a5x.trd.pub.nativ.model.NativeLogonResponse;
import br.com.a5x.trd.pub.nativ.model.NativeLogout;
import br.com.a5x.trd.pub.nativ.model.NativeMMPResetAcknowledgement;
import br.com.a5x.trd.pub.nativ.model.NativeMMPResetRequest;
import br.com.a5x.trd.pub.nativ.model.NativeMassCancelReport;
import br.com.a5x.trd.pub.nativ.model.NativeMassCancelRequest;
import br.com.a5x.trd.pub.nativ.model.NativeMassQuote;
import br.com.a5x.trd.pub.nativ.model.NativeMassQuoteAcknowledgement;
import br.com.a5x.trd.pub.nativ.model.NativeMassQuoteFlat;
import br.com.a5x.trd.pub.nativ.model.NativeMissedMessageRequest;
import br.com.a5x.trd.pub.nativ.model.NativeMissedMessageRequestAck;
import br.com.a5x.trd.pub.nativ.model.NativeNewOrderCross;
import br.com.a5x.trd.pub.nativ.model.NativeNewOrderSingle;
import br.com.a5x.trd.pub.nativ.model.NativeNews;
import br.com.a5x.trd.pub.nativ.model.NativeOrderCancelReject;
import br.com.a5x.trd.pub.nativ.model.NativeOrderCancelRequest;
import br.com.a5x.trd.pub.nativ.model.NativeOrderReplaceRequest;
import br.com.a5x.trd.pub.nativ.model.NativeQuoteStatusReport;
import br.com.a5x.trd.pub.nativ.model.NativeReject;
import br.com.a5x.trd.pub.nativ.model.NativeSystemStatus;
import br.com.a5x.trd.pub.nativ.model.NativeTransmissionComplete;

/**
 * {@link INativePackageHandler} that ignores every message: extend it and override only the
 * messages you handle. A message type added to the specification shows up here as a compile
 * error, which is the point: regenerate this adapter with the models.
 */
public abstract class NativeHandlerAdapter implements INativePackageHandler {

    @Override
    public void onNativeLogon(NativeLogon msg) {
    }

    @Override
    public void onNativeLogonResponse(NativeLogonResponse msg) {
    }

    @Override
    public void onNativeHeartbeat(NativeHeartbeat msg) {
    }

    @Override
    public void onNativeReject(NativeReject msg) {
    }

    @Override
    public void onNativeLogout(NativeLogout msg) {
    }

    @Override
    public void onNativeExecutionReport(NativeExecutionReport msg) {
    }

    @Override
    public void onNativeOrderCancelReject(NativeOrderCancelReject msg) {
    }

    @Override
    public void onNativeNewOrderSingle(NativeNewOrderSingle msg) {
    }

    @Override
    public void onNativeOrderCancelRequest(NativeOrderCancelRequest msg) {
    }

    @Override
    public void onNativeOrderReplaceRequest(NativeOrderReplaceRequest msg) {
    }

    @Override
    public void onNativeMissedMessageRequest(NativeMissedMessageRequest msg) {
    }

    @Override
    public void onNativeMissedMessageRequestAck(NativeMissedMessageRequestAck msg) {
    }

    @Override
    public void onNativeTransmissionComplete(NativeTransmissionComplete msg) {
    }

    @Override
    public void onNativeSystemStatus(NativeSystemStatus msg) {
    }

    @Override
    public void onNativeNewOrderCross(NativeNewOrderCross msg) {
    }

    @Override
    public void onNativeBusinessReject(NativeBusinessReject msg) {
    }

    @Override
    public void onNativeMassCancelRequest(NativeMassCancelRequest msg) {
    }

    @Override
    public void onNativeMassCancelReport(NativeMassCancelReport msg) {
    }

    @Override
    public void onNativeNews(NativeNews msg) {
    }

    @Override
    public void onNativeMassQuote(NativeMassQuote msg) {
    }

    @Override
    public void onNativeMassQuoteAcknowledgement(NativeMassQuoteAcknowledgement msg) {
    }

    @Override
    public void onNativeQuoteStatusReport(NativeQuoteStatusReport msg) {
    }

    @Override
    public void onNativeMMPResetRequest(NativeMMPResetRequest msg) {
    }

    @Override
    public void onNativeMMPResetAcknowledgement(NativeMMPResetAcknowledgement msg) {
    }

    @Override
    public void onNativeMassQuoteFlat(NativeMassQuoteFlat msg) {
    }
}
