package com.taximobility.bookingmodule.utils;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0002J\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/taximobility/bookingmodule/utils/CheckNetworkStatus;", "Landroid/content/BroadcastReceiver;", "()V", "IS_NETWORK_AVAILABLE", "", "NETWORK_AVAILABLE_ACTION", "isConnectedToInternet", "", "context", "Landroid/content/Context;", "onReceive", "", "p1", "Landroid/content/Intent;", "app_debug"})
public final class CheckNetworkStatus extends android.content.BroadcastReceiver {
    private final java.lang.String NETWORK_AVAILABLE_ACTION = "com.adrop.service.Activity_Action";
    private final java.lang.String IS_NETWORK_AVAILABLE = "isNetworkAvailable";
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.content.Intent p1) {
    }
    
    private final boolean isConnectedToInternet(android.content.Context context) {
        return false;
    }
    
    public CheckNetworkStatus() {
        super();
    }
}