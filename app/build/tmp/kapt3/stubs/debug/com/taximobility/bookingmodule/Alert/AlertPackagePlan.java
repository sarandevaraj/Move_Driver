package com.taximobility.bookingmodule.Alert;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J^\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0013R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u0004\u00a8\u0006\u001f"}, d2 = {"Lcom/taximobility/bookingmodule/Alert/AlertPackagePlan;", "", "packageClickListener", "Lcom/taximobility/interfaces/PackageClick;", "(Lcom/taximobility/interfaces/PackageClick;)V", "alertDialog", "Landroid/app/AlertDialog;", "getAlertDialog", "()Landroid/app/AlertDialog;", "setAlertDialog", "(Landroid/app/AlertDialog;)V", "getPackageClickListener", "()Lcom/taximobility/interfaces/PackageClick;", "setPackageClickListener", "alertPackage", "", "context", "Landroid/app/Activity;", "title", "", "msg", "successTxt", "failureTxt", "pickupLatLng", "Lcom/google/android/gms/maps/model/LatLng;", "dropLatLng", "pickLoc", "dropLoc", "driverLiveMovement", "Lcom/taximobility/bookingmodule/DriverLiveMovement/DriverLiveMove;", "requestType", "app_debug"})
public final class AlertPackagePlan {
    @org.jetbrains.annotations.NotNull()
    public android.app.AlertDialog alertDialog;
    @org.jetbrains.annotations.NotNull()
    private com.taximobility.interfaces.PackageClick packageClickListener;
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.AlertDialog getAlertDialog() {
        return null;
    }
    
    public final void setAlertDialog(@org.jetbrains.annotations.NotNull()
    android.app.AlertDialog p0) {
    }
    
    public final void alertPackage(@org.jetbrains.annotations.NotNull()
    android.app.Activity context, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String msg, @org.jetbrains.annotations.NotNull()
    java.lang.String successTxt, @org.jetbrains.annotations.NotNull()
    java.lang.String failureTxt, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng pickupLatLng, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng dropLatLng, @org.jetbrains.annotations.NotNull()
    java.lang.String pickLoc, @org.jetbrains.annotations.NotNull()
    java.lang.String dropLoc, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.DriverLiveMovement.DriverLiveMove driverLiveMovement, @org.jetbrains.annotations.NotNull()
    java.lang.String requestType) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.interfaces.PackageClick getPackageClickListener() {
        return null;
    }
    
    public final void setPackageClickListener(@org.jetbrains.annotations.NotNull()
    com.taximobility.interfaces.PackageClick p0) {
    }
    
    public AlertPackagePlan(@org.jetbrains.annotations.NotNull()
    com.taximobility.interfaces.PackageClick packageClickListener) {
        super();
    }
}