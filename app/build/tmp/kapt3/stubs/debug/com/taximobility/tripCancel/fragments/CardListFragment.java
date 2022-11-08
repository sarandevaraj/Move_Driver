package com.taximobility.tripCancel.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 02\u00020\u00012\u00020\u0002:\u00010B\u0005\u00a2\u0006\u0002\u0010\u0003J$\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u000b2\b\b\u0002\u0010\u0015\u001a\u00020\u0005H\u0002J\u0006\u0010\u0016\u001a\u00020\u0012J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0012H\u0002J\u0014\u0010\u001b\u001a\u00020\u00122\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0002J\u0018\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u000bH\u0016J\u0012\u0010!\u001a\u00020\u00122\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J&\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020\'2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\b\u0010*\u001a\u00020\u0012H\u0016J\b\u0010+\u001a\u00020\u0012H\u0016J\b\u0010,\u001a\u00020\u0012H\u0016J\u001a\u0010-\u001a\u00020\u00122\u0006\u0010.\u001a\u00020%2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\u0006\u0010/\u001a\u00020\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2 = {"Lcom/taximobility/tripCancel/fragments/CardListFragment;", "Landroidx/fragment/app/DialogFragment;", "Lcom/taximobility/adapter/CreditCardAdapter$RecyclerViewItemClickListener;", "()V", "alertMessage", "", "binding", "Lcom/taximobility/databinding/FragmentCardListBinding;", "cancelReason", "cancellationFee", "fromPage", "", "mDialog", "Landroid/app/Dialog;", "tripId", "viewModel", "Lcom/taximobility/tripCancel/CreditCardViewModel;", "callCancelTripApi", "", "paymentModeId", "cardId", "creditCardCvv", "cancelLoading", "handleResponse", "cancelTripResponseData", "Lcom/taximobility/tripCancel/CancelTripResponseData;", "moveToAddCardPage", "moveToPreviousPage", "selectedCard", "Lcom/taximobility/tripCancel/CreditCardData;", "onClick", "cardData", "position", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onResume", "onStart", "onStop", "onViewCreated", "view", "showLoading", "Companion", "app_debug"})
public final class CardListFragment extends androidx.fragment.app.DialogFragment implements com.taximobility.adapter.CreditCardAdapter.RecyclerViewItemClickListener {
    private int tripId = 0;
    private java.lang.String cancellationFee = "";
    private java.lang.String cancelReason = "";
    private java.lang.String alertMessage = "";
    private int fromPage = 1;
    private android.app.Dialog mDialog;
    private com.taximobility.tripCancel.CreditCardViewModel viewModel;
    private com.taximobility.databinding.FragmentCardListBinding binding;
    public static final com.taximobility.tripCancel.fragments.CardListFragment.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
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
    
    private final void moveToPreviousPage(com.taximobility.tripCancel.CreditCardData selectedCard) {
    }
    
    @java.lang.Override()
    public void onClick(@org.jetbrains.annotations.NotNull()
    com.taximobility.tripCancel.CreditCardData cardData, int position) {
    }
    
    private final void moveToAddCardPage() {
    }
    
    private final void callCancelTripApi(java.lang.String paymentModeId, int cardId, java.lang.String creditCardCvv) {
    }
    
    private final void handleResponse(com.taximobility.tripCancel.CancelTripResponseData cancelTripResponseData) {
    }
    
    public final void showLoading() {
    }
    
    public final void cancelLoading() {
    }
    
    public CardListFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.taximobility.tripCancel.fragments.CardListFragment newInstance(int tripId, @org.jetbrains.annotations.NotNull()
    java.lang.String cancellationFee, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, int fromPage) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0006H\u0007\u00a8\u0006\u000b"}, d2 = {"Lcom/taximobility/tripCancel/fragments/CardListFragment$Companion;", "", "()V", "newInstance", "Lcom/taximobility/tripCancel/fragments/CardListFragment;", "tripId", "", "cancellationFee", "", "reason", "fromPage", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.taximobility.tripCancel.fragments.CardListFragment newInstance(int tripId, @org.jetbrains.annotations.NotNull()
        java.lang.String cancellationFee, @org.jetbrains.annotations.NotNull()
        java.lang.String reason, int fromPage) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}