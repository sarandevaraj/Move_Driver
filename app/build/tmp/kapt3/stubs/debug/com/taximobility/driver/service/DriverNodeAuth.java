package com.taximobility.driver.service;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\b"}, d2 = {"Lcom/taximobility/driver/service/DriverNodeAuth;", "", "()V", "getAuth", "", "context", "Landroid/content/Context;", "Companion", "app_debug"})
public final class DriverNodeAuth {
    private static boolean isAuthCallInProgress = false;
    private static volatile com.taximobility.driver.service.DriverNodeAuth instance;
    public static final com.taximobility.driver.service.DriverNodeAuth.Companion Companion = null;
    
    public final void getAuth(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    private DriverNodeAuth() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.taximobility.driver.service.DriverNodeAuth getInstance() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\u0004H\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/taximobility/driver/service/DriverNodeAuth$Companion;", "", "()V", "instance", "Lcom/taximobility/driver/service/DriverNodeAuth;", "isAuthCallInProgress", "", "getInstance", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.taximobility.driver.service.DriverNodeAuth getInstance() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}