package com.taximobility.bookingmodule.MapModule;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0006\n\u0002\b\u0007\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005B\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\fJ\u0010\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u0011H\u0002J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u0011H\u0002J\u0010\u0010%\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020\u0011H\u0016J\u000e\u0010\'\u001a\u00020\u001f2\u0006\u0010(\u001a\u00020\u0011J\u000e\u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020+J\u0018\u0010,\u001a\u00020\u001f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010-\u001a\u00020\u0011H&J\u001e\u0010.\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\tJ\b\u0010/\u001a\u00020\u001fH\u0002J\b\u00100\u001a\u00020\u001fH\u0016J\u0010\u00101\u001a\u00020\u001f2\u0006\u00102\u001a\u00020\u000eH\u0016J\u0010\u00103\u001a\u00020\u001f2\u0006\u00104\u001a\u00020\u0018H\u0016J \u00105\u001a\u00020\u001f2\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u0002072\u0006\u0010\u0007\u001a\u00020\tH&J\b\u00109\u001a\u00020\u001fH\u0002J\"\u0010:\u001a\u00020\u001f2\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u0002072\b\u0010;\u001a\u0004\u0018\u00010\tH\u0016J\u0016\u0010<\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\tJ\b\u0010=\u001a\u00020\u001fH\u0002R\"\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006>"}, d2 = {"Lcom/taximobility/bookingmodule/MapModule/MapFragment;", "Lcom/taximobility/bookingmodule/MapModule/GpsFrag;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "Lcom/google/android/gms/maps/GoogleMap$OnCameraMoveStartedListener;", "Lcom/google/android/gms/maps/GoogleMap$OnCameraIdleListener;", "Lcom/taximobility/interfaces/GetAddress;", "()V", "address", "Landroid/os/AsyncTask;", "", "Lcom/taximobility/roomDB/GeocoderModel;", "addressRunning", "", "cameraMove", "", "currentIconClick", "currentLatLng", "Lcom/google/android/gms/maps/model/LatLng;", "dragLatLng", "dragTempLatLng", "handlerServercall", "Landroid/os/Handler;", "isneedAddress", "map", "Lcom/google/android/gms/maps/GoogleMap;", "mapFragments", "Lcom/google/android/gms/maps/SupportMapFragment;", "pickDropLoc", "pickupLatLng", "pickupLocation", "cameraChangeListeners", "", "boolean", "findAddress", "mLatLng", "getDropAddress", "dropLatLng", "getLatlngUpdates", "lastLatLng", "getPickupAddress", "pickuplatlng", "initializeMap", "view", "Landroid/view/View;", "mapGpsInitialized", "currLatLng", "moveTothisLocation", "needTogetAddress", "onCameraIdle", "onCameraMoveStarted", "reason", "onMapReady", "googleMap", "setDraggedAddress", "latitude", "", "longitude", "setMapStyle", "setaddress", "Address", "updatedPickupLatLng", "zoomToLastKnownLatLng", "app_debug"})
public abstract class MapFragment extends com.taximobility.bookingmodule.MapModule.GpsFrag implements com.google.android.gms.maps.OnMapReadyCallback, com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener, com.google.android.gms.maps.GoogleMap.OnCameraIdleListener, com.taximobility.interfaces.GetAddress {
    private com.google.android.gms.maps.GoogleMap map;
    private com.google.android.gms.maps.SupportMapFragment mapFragments;
    private int cameraMove = 0;
    private android.os.AsyncTask<java.lang.String, java.lang.String, com.taximobility.roomDB.GeocoderModel> address;
    private android.os.Handler handlerServercall;
    private com.google.android.gms.maps.model.LatLng dragLatLng;
    private com.google.android.gms.maps.model.LatLng dragTempLatLng;
    private com.google.android.gms.maps.model.LatLng currentLatLng;
    private com.google.android.gms.maps.model.LatLng pickupLatLng;
    private java.lang.String pickupLocation = "";
    private java.lang.String pickDropLoc = "";
    private boolean addressRunning = false;
    private boolean currentIconClick = false;
    private boolean isneedAddress = true;
    private java.util.HashMap _$_findViewCache;
    
    public final void initializeMap(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    public final void cameraChangeListeners(boolean p0_32355860) {
    }
    
    @java.lang.Override()
    public void onMapReady(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap googleMap) {
    }
    
    @java.lang.Override()
    public void onCameraMoveStarted(int reason) {
    }
    
    @java.lang.Override()
    public void onCameraIdle() {
    }
    
    @java.lang.Override()
    public void setaddress(double latitude, double longitude, @org.jetbrains.annotations.Nullable()
    java.lang.String Address) {
    }
    
    public abstract void setDraggedAddress(double latitude, double longitude, @org.jetbrains.annotations.NotNull()
    java.lang.String address);
    
    public abstract void mapGpsInitialized(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap map, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng currLatLng);
    
    private final void setMapStyle() {
    }
    
    public final void moveTothisLocation(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng mLatLng, boolean isneedAddress, @org.jetbrains.annotations.NotNull()
    java.lang.String address) {
    }
    
    public final void updatedPickupLatLng(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng mLatLng, @org.jetbrains.annotations.NotNull()
    java.lang.String address) {
    }
    
    public final void getPickupAddress(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng pickuplatlng) {
    }
    
    private final void findAddress(com.google.android.gms.maps.model.LatLng mLatLng) {
    }
    
    @java.lang.Override()
    public void getLatlngUpdates(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng lastLatLng) {
    }
    
    private final void zoomToLastKnownLatLng() {
    }
    
    private final void getDropAddress(com.google.android.gms.maps.model.LatLng dropLatLng) {
    }
    
    private final void needTogetAddress() {
    }
    
    public MapFragment() {
        super();
    }
}