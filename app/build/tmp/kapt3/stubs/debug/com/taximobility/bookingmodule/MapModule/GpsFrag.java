package com.taximobility.bookingmodule.MapModule;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013J\u0018\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H&J\n\u0010\u0015\u001a\u0004\u0018\u00010\bH\u0002J\u0010\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0006H&J\b\u0010\u0018\u001a\u00020\u0010H\u0002J\u0012\u0010\u0019\u001a\u00020\u00102\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0010H\u0002J\b\u0010\u001d\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/taximobility/bookingmodule/MapModule/GpsFrag;", "Landroidx/fragment/app/Fragment;", "()V", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "lastKnownLatLng", "Lcom/google/android/gms/maps/model/LatLng;", "location", "Landroid/location/Location;", "locationCallback", "Lcom/google/android/gms/location/LocationCallback;", "requestingLocationUpdates", "", "createLocationRequest", "Lcom/google/android/gms/location/LocationRequest;", "getInitializeLocation", "", "getLastKnownLatLng", "lastLocationReqType", "", "getLastKnownLattitudeLongtitude", "getLastLocation", "getLatlngUpdates", "lastLatLng", "makeLocationSettingsClient", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "startLocationUpdates", "stopLocationUpdates", "app_debug"})
public abstract class GpsFrag extends androidx.fragment.app.Fragment {
    private android.location.Location location;
    private com.google.android.gms.maps.model.LatLng lastKnownLatLng;
    private com.google.android.gms.location.LocationCallback locationCallback;
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient;
    private final boolean requestingLocationUpdates = true;
    private java.util.HashMap _$_findViewCache;
    
    private final com.google.android.gms.location.LocationRequest createLocationRequest() {
        return null;
    }
    
    private final void startLocationUpdates() {
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void getInitializeLocation() {
    }
    
    private final void stopLocationUpdates() {
    }
    
    public abstract void getLatlngUpdates(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng lastLatLng);
    
    public abstract void getLastKnownLattitudeLongtitude(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng lastKnownLatLng, int lastLocationReqType);
    
    public final void getLastKnownLatLng(int lastLocationReqType) {
    }
    
    private final android.location.Location getLastLocation() {
        return null;
    }
    
    private final void makeLocationSettingsClient() {
    }
    
    public GpsFrag() {
        super();
    }
}