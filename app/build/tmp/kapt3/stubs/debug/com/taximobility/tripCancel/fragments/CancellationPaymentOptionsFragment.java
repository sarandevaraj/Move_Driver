package com.taximobility.tripCancel.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 02\u00020\u0001:\u0002/0B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u00042\b\b\u0002\u0010\u0015\u001a\u00020\u000e2\b\b\u0002\u0010\u0016\u001a\u00020\u0004H\u0002J\u0006\u0010\u0017\u001a\u00020\u0014J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u0014H\u0002J\u0010\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0012\u0010\u001f\u001a\u00020\u00142\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J&\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\'2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010(\u001a\u00020\u0014H\u0016J\b\u0010)\u001a\u00020\u0014H\u0016J\b\u0010*\u001a\u00020\u0014H\u0016J\b\u0010+\u001a\u00020\u0014H\u0016J\u001a\u0010,\u001a\u00020\u00142\u0006\u0010-\u001a\u00020#2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u0006\u0010.\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2 = {"Lcom/taximobility/tripCancel/fragments/CancellationPaymentOptionsFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "alertMessage", "", "binding", "Lcom/taximobility/databinding/FragmentCancellationPaymentOptionsBinding;", "cancelPayListener", "Lcom/taximobility/interfaces/CancelPayment;", "cancelReason", "cancellationFee", "creditCardData", "Lcom/taximobility/tripCancel/CreditCardData;", "fromPage", "", "mDialog", "Landroid/app/Dialog;", "paymentModeId", "tripId", "callCancelTripApi", "", "cardId", "creditCardCvv", "cancelLoading", "handleResponse", "cancelTripResponseData", "Lcom/taximobility/tripCancel/CancelTripResponseData;", "moveToPreviousPage", "onAttach", "context", "Landroid/content/Context;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDetach", "onResume", "onStart", "onStop", "onViewCreated", "view", "showLoading", "CancelRazorpay", "Companion", "app_debug"})
public final class CancellationPaymentOptionsFragment extends androidx.fragment.app.DialogFragment {
    private int tripId = 0;
    private java.lang.String paymentModeId = "";
    private java.lang.String cancellationFee = "";
    private java.lang.String cancelReason = "";
    private java.lang.String alertMessage = "";
    private int fromPage = 1;
    private com.taximobility.tripCancel.CreditCardData creditCardData;
    private android.app.Dialog mDialog;
    private com.taximobility.interfaces.CancelPayment cancelPayListener;
    private com.taximobility.databinding.FragmentCancellationPaymentOptionsBinding binding;
    public static final com.taximobility.tripCancel.fragments.CancellationPaymentOptionsFragment.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onAttach(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @java.lang.Override()
    public void onDetach() {
    }
    
    @java.lang.Override()
    public void onStart() {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onStop() {
    }
    
    private final void moveToPreviousPage() {
    }
    
    /**
     * Function which calls cancel_trip API to cancel that trip
     * @param paymentModeId - Payment mode ID. i.e, payment by CASH(2) or WALLET(5) or PAYMENT AT NEXT TRIP(10)
     * @param cardId - Passenger's card ID - default value 0
     * @param creditCardCvv - Passenger's card CVV number - default value empty
     */
    private final void callCancelTripApi(java.lang.String paymentModeId, int cardId, java.lang.String creditCardCvv) {
    }
    
    /**
     * Function to handle API response
     * @param cancelTripResponseData - Cancel trip API response data
     */
    private final void handleResponse(com.taximobility.tripCancel.CancelTripResponseData cancelTripResponseData) {
    }
    
    /**
     */
    public final void showLoading() {
    }
    
    public final void cancelLoading() {
    }
    
    public CancellationPaymentOptionsFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.taximobility.tripCancel.fragments.CancellationPaymentOptionsFragment newInstance(int tripId, @org.jetbrains.annotations.NotNull()
    java.lang.String cancellationFee, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, int fromPage, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentModeId, @org.jetbrains.annotations.Nullable()
    com.taximobility.tripCancel.CreditCardData selectedCard) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001a\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003H\u0016\u00a8\u0006\f"}, d2 = {"Lcom/taximobility/tripCancel/fragments/CancellationPaymentOptionsFragment$CancelRazorpay;", "Lcom/taximobility/interfaces/APIResult;", "url", "", "data", "Lorg/json/JSONObject;", "(Lcom/taximobility/tripCancel/fragments/CancellationPaymentOptionsFragment;Ljava/lang/String;Lorg/json/JSONObject;)V", "getResult", "", "isSuccess", "", "result", "app_debug"})
    final class CancelRazorpay implements com.taximobility.interfaces.APIResult {
        
        @java.lang.Override()
        public void getResult(boolean isSuccess, @org.jetbrains.annotations.Nullable()
        java.lang.String result) {
        }
        
        public CancelRazorpay(@org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        org.json.JSONObject data) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J>\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u0007\u00a8\u0006\u000e"}, d2 = {"Lcom/taximobility/tripCancel/fragments/CancellationPaymentOptionsFragment$Companion;", "", "()V", "newInstance", "Lcom/taximobility/tripCancel/fragments/CancellationPaymentOptionsFragment;", "tripId", "", "cancellationFee", "", "reason", "fromPage", "paymentModeId", "selectedCard", "Lcom/taximobility/tripCancel/CreditCardData;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.taximobility.tripCancel.fragments.CancellationPaymentOptionsFragment newInstance(int tripId, @org.jetbrains.annotations.NotNull()
        java.lang.String cancellationFee, @org.jetbrains.annotations.NotNull()
        java.lang.String reason, int fromPage, @org.jetbrains.annotations.NotNull()
        java.lang.String paymentModeId, @org.jetbrains.annotations.Nullable()
        com.taximobility.tripCancel.CreditCardData selectedCard) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}