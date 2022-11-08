package com.taximobility.bookingmodule.Interface;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J)\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\u0005H&\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H&\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/taximobility/bookingmodule/Interface/RouteListeners;", "", "drawRoutePickToDrop", "", "time", "", "dist", "approxFare", "(Ljava/lang/Double;Ljava/lang/Double;D)V", "getETADiverToPickup", "(Ljava/lang/Double;Ljava/lang/Double;)V", "app_debug"})
public abstract interface RouteListeners {
    
    public abstract void drawRoutePickToDrop(@org.jetbrains.annotations.Nullable()
    java.lang.Double time, @org.jetbrains.annotations.Nullable()
    java.lang.Double dist, double approxFare);
    
    public abstract void getETADiverToPickup(@org.jetbrains.annotations.Nullable()
    java.lang.Double time, @org.jetbrains.annotations.Nullable()
    java.lang.Double dist);
}