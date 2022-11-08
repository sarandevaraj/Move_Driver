package com.taximobility.tripCancel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 -2\u00020\u0001:\u0002,-B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u001bH\u0002J\u0006\u0010\u001d\u001a\u00020\u001bJ\u0012\u0010\u001e\u001a\u00020\u001b2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J&\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010\'\u001a\u00020\u001bH\u0016J\b\u0010(\u001a\u00020\u001bH\u0016J\u001a\u0010)\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\"2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010+\u001a\u00020\u001bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/taximobility/tripCancel/AddCardFragment;", "Landroidx/fragment/app/Fragment;", "()V", "binding", "Lcom/taximobility/databinding/FragmentAddCardBinding;", "cancelReason", "", "cancellationFee", "creditCardRepository", "Lcom/taximobility/tripCancel/CreditCardRepository;", "curmonth", "", "curyear", "dialog", "Landroid/app/Dialog;", "expmonth", "expyear", "fromPage", "isDefault", "monthadapter", "Lcom/taximobility/util/FontHelper$MySpinnerAdapterWhite;", "monthtouched", "", "tripId", "yearadapter", "yeartouched", "cardRegisterData", "", "moveToPreviousPage", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onResume", "onStop", "onViewCreated", "view", "setToolbar", "CardRegister", "Companion", "app_debug"})
public final class AddCardFragment extends androidx.fragment.app.Fragment {
    private int tripId = 0;
    private java.lang.String cancellationFee = "";
    private java.lang.String cancelReason = "";
    private int fromPage = 1;
    private android.app.Dialog dialog;
    private java.lang.String expmonth = "";
    private java.lang.String expyear = "";
    private boolean monthtouched = false;
    private boolean yeartouched = false;
    private int isDefault = 0;
    private int curmonth = 0;
    private int curyear = 0;
    private com.taximobility.util.FontHelper.MySpinnerAdapterWhite monthadapter;
    private com.taximobility.util.FontHelper.MySpinnerAdapterWhite yearadapter;
    private com.taximobility.tripCancel.CreditCardRepository creditCardRepository;
    private com.taximobility.databinding.FragmentAddCardBinding binding;
    public static final com.taximobility.tripCancel.AddCardFragment.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
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
    
    private final void setToolbar() {
    }
    
    public final void onBackPressed() {
    }
    
    private final void moveToPreviousPage() {
    }
    
    /**
     * this method is used to register the card details
     */
    private final void cardRegisterData() {
    }
    
    public AddCardFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.taximobility.tripCancel.AddCardFragment newInstance(int tripId, @org.jetbrains.annotations.NotNull()
    java.lang.String cancellationFee, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, int fromPage) {
        return null;
    }
    
    /**
     * This class used to register with card details
     * @author developer
     */
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0003H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0010"}, d2 = {"Lcom/taximobility/tripCancel/AddCardFragment$CardRegister;", "Lcom/taximobility/interfaces/APIResult;", "url", "", "data", "Lorg/json/JSONObject;", "creditCardData", "Lcom/taximobility/tripCancel/CreditCardData;", "(Lcom/taximobility/tripCancel/AddCardFragment;Ljava/lang/String;Lorg/json/JSONObject;Lcom/taximobility/tripCancel/CreditCardData;)V", "getCreditCardData", "()Lcom/taximobility/tripCancel/CreditCardData;", "getResult", "", "isSuccess", "", "result", "app_debug"})
    final class CardRegister implements com.taximobility.interfaces.APIResult {
        @org.jetbrains.annotations.NotNull()
        private final com.taximobility.tripCancel.CreditCardData creditCardData = null;
        
        @java.lang.Override()
        public void getResult(boolean isSuccess, @org.jetbrains.annotations.NotNull()
        java.lang.String result) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.taximobility.tripCancel.CreditCardData getCreditCardData() {
            return null;
        }
        
        public CardRegister(@org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        org.json.JSONObject data, @org.jetbrains.annotations.NotNull()
        com.taximobility.tripCancel.CreditCardData creditCardData) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0006H\u0007\u00a8\u0006\u000b"}, d2 = {"Lcom/taximobility/tripCancel/AddCardFragment$Companion;", "", "()V", "newInstance", "Lcom/taximobility/tripCancel/AddCardFragment;", "tripId", "", "cancellationFee", "", "reason", "fromPage", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.taximobility.tripCancel.AddCardFragment newInstance(int tripId, @org.jetbrains.annotations.NotNull()
        java.lang.String cancellationFee, @org.jetbrains.annotations.NotNull()
        java.lang.String reason, int fromPage) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}