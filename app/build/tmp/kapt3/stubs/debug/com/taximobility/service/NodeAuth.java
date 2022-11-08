package com.taximobility.service;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u000f\u00a8\u0006\u0011"}, d2 = {"Lcom/taximobility/service/NodeAuth;", "", "()V", "getAuth", "", "context", "Landroid/content/Context;", "setListener", "listener", "Lcom/taximobility/interfaces/NodeAuthListener;", "mRequestData", "Lorg/json/JSONObject;", "mViewModel", "Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;", "setListenerForAuthResponse", "", "Companion", "app_debug"})
public class NodeAuth {
    private static boolean isAuthCallInProgress = false;
    private static volatile com.taximobility.service.NodeAuth instance;
    private static com.taximobility.interfaces.NodeAuthListener nodeAuthListener;
    @org.jetbrains.annotations.NotNull()
    public static org.json.JSONObject requestData;
    @org.jetbrains.annotations.NotNull()
    public static com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel;
    public static final com.taximobility.service.NodeAuth.Companion Companion = null;
    
    public final void setListener(@org.jetbrains.annotations.NotNull()
    com.taximobility.interfaces.NodeAuthListener listener, @org.jetbrains.annotations.NotNull()
    org.json.JSONObject mRequestData, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel mViewModel) {
    }
    
    public final void getAuth(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void setListenerForAuthResponse(boolean listener) {
    }
    
    public NodeAuth() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.taximobility.service.NodeAuth getInstance() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0004H\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0016"}, d2 = {"Lcom/taximobility/service/NodeAuth$Companion;", "", "()V", "instance", "Lcom/taximobility/service/NodeAuth;", "isAuthCallInProgress", "", "nodeAuthListener", "Lcom/taximobility/interfaces/NodeAuthListener;", "requestData", "Lorg/json/JSONObject;", "getRequestData", "()Lorg/json/JSONObject;", "setRequestData", "(Lorg/json/JSONObject;)V", "viewModel", "Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;", "getViewModel", "()Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;", "setViewModel", "(Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;)V", "getInstance", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final org.json.JSONObject getRequestData() {
            return null;
        }
        
        public final void setRequestData(@org.jetbrains.annotations.NotNull()
        org.json.JSONObject p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.taximobility.bookingmodule.BookTaxiHomeViewModel getViewModel() {
            return null;
        }
        
        public final void setViewModel(@org.jetbrains.annotations.NotNull()
        com.taximobility.bookingmodule.BookTaxiHomeViewModel p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.taximobility.service.NodeAuth getInstance() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}