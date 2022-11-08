package com.taximobility.tripCancel.service;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\nB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\u0007\u001a\u00020\u00062\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/taximobility/tripCancel/service/GetCardDetailsService;", "Landroid/app/IntentService;", "()V", "creditCardRepository", "Lcom/taximobility/tripCancel/CreditCardRepository;", "onCreate", "", "onHandleIntent", "intent", "Landroid/content/Intent;", "GetCardList", "app_debug"})
public final class GetCardDetailsService extends android.app.IntentService {
    private com.taximobility.tripCancel.CreditCardRepository creditCardRepository;
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    protected void onHandleIntent(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
    }
    
    public GetCardDetailsService() {
        super(null);
    }
    
    /**
     * GetCardlist class is used to get the passenger card details and update it into UI
     */
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016\u00a8\u0006\f"}, d2 = {"Lcom/taximobility/tripCancel/service/GetCardDetailsService$GetCardList;", "Lcom/taximobility/interfaces/APIResult;", "string", "", "data", "Lorg/json/JSONObject;", "(Lcom/taximobility/tripCancel/service/GetCardDetailsService;Ljava/lang/String;Lorg/json/JSONObject;)V", "getResult", "", "isSuccess", "", "result", "app_debug"})
    final class GetCardList implements com.taximobility.interfaces.APIResult {
        
        @java.lang.Override()
        public void getResult(boolean isSuccess, @org.jetbrains.annotations.NotNull()
        java.lang.String result) {
        }
        
        public GetCardList(@org.jetbrains.annotations.NotNull()
        java.lang.String string, @org.jetbrains.annotations.NotNull()
        org.json.JSONObject data) {
            super();
        }
    }
}