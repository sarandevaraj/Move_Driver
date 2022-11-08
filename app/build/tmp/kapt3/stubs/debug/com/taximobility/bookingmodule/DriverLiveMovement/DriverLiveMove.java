package com.taximobility.bookingmodule.DriverLiveMovement;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u001d\u001a\u00020\u001e2\u000e\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\u0002J \u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\"\u0010&\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\'\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010(\u001a\u00020)H\u0002JP\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u00162\u0016\u0010-\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b2\u0016\u0010.\u001a\u0012\u0012\u0004\u0012\u00020\f0\u0006j\b\u0012\u0004\u0012\u00020\f`\b2\b\u0010/\u001a\u0004\u0018\u00010\u0018J(\u00100\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020#2\u0006\u00101\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\fH\u0002J\u0010\u00105\u001a\u00020\u001e2\u0006\u00106\u001a\u00020\u0007H\u0002J\u0010\u00107\u001a\u00020\u001e2\u0006\u00108\u001a\u000209H\u0002J\u0006\u0010:\u001a\u00020\u001eJ\u0006\u0010;\u001a\u00020\u001eJ\u0010\u0010<\u001a\u00020\u001e2\b\u0010=\u001a\u0004\u0018\u00010\u001cJ\u0006\u0010>\u001a\u00020\u001eJ \u0010?\u001a\u00020\u001e2\u0016\u0010@\u001a\u0012\u0012\u0004\u0012\u00020\f0\u0006j\b\u0012\u0004\u0012\u00020\f`\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R.\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\f\u0018\u00010\u0006j\n\u0012\u0004\u0012\u00020\f\u0018\u0001`\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0006j\b\u0012\u0004\u0012\u00020\u0004`\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006A"}, d2 = {"Lcom/taximobility/bookingmodule/DriverLiveMovement/DriverLiveMove;", "", "()V", "availablecarcount", "", "detailList", "Ljava/util/ArrayList;", "Lcom/taximobility/data/DriverData;", "Lkotlin/collections/ArrayList;", "driverDelayHandler", "Landroid/os/Handler;", "driverIdData", "", "getDriverIdData", "()Ljava/util/ArrayList;", "setDriverIdData", "(Ljava/util/ArrayList;)V", "driverLocationHistoryRunnable", "Ljava/lang/Runnable;", "mContext", "Landroid/content/Context;", "mFragment", "Landroidx/fragment/app/Fragment;", "mMap", "Lcom/google/android/gms/maps/GoogleMap;", "onExistingDriverMarkers", "onMovingDriverMarkers", "Landroid/util/SparseArray;", "Lcom/google/android/gms/maps/model/Marker;", "DriverLiveMovement", "", "drivers", "animateMarker", "marker", "newLatLng", "Lcom/google/android/gms/maps/model/LatLng;", "bearing", "", "createAndGetMarker", "latLng", "carIcon", "Landroid/graphics/Bitmap;", "findNearestlocal", "context", "fragment", "detail", "driverData", "map", "getHeadingDirectionFromCoordinate", "currentposition", "isValidCoordinates", "", "latOrLng", "newDriverMarker", "driver", "realTimeTracking", "array", "Lorg/json/JSONArray;", "removeDriverLiveMovement", "removeDriverLiveMovementCallback", "removeMarkerWithAnimation", "removeMarker", "removeStartDriverLiveMovement", "startDriverMovementSocket", "driver_id", "app_debug"})
public final class DriverLiveMove {
    private android.os.Handler driverDelayHandler;
    private android.util.SparseArray<com.google.android.gms.maps.model.Marker> onMovingDriverMarkers;
    private java.util.ArrayList<java.lang.Integer> onExistingDriverMarkers;
    private int availablecarcount = 0;
    private android.content.Context mContext;
    private androidx.fragment.app.Fragment mFragment;
    private java.util.ArrayList<com.taximobility.data.DriverData> detailList;
    @org.jetbrains.annotations.Nullable()
    private java.util.ArrayList<java.lang.String> driverIdData;
    private com.google.android.gms.maps.GoogleMap mMap;
    private final java.lang.Runnable driverLocationHistoryRunnable = null;
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.ArrayList<java.lang.String> getDriverIdData() {
        return null;
    }
    
    public final void setDriverIdData(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<java.lang.String> p0) {
    }
    
    public final void findNearestlocal(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment fragment, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.taximobility.data.DriverData> detail, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.lang.String> driverData, @org.jetbrains.annotations.Nullable()
    com.google.android.gms.maps.GoogleMap map) {
    }
    
    private final void startDriverMovementSocket(java.util.ArrayList<java.lang.String> driver_id) {
    }
    
    private final void realTimeTracking(org.json.JSONArray array) {
    }
    
    /**
     * Driver marker Logic
     *
     * @param drivers current response driver
     */
    private final synchronized void DriverLiveMovement(java.util.ArrayList<com.taximobility.data.DriverData> drivers) {
    }
    
    /**
     * Add new Marker
     *
     * @param driver driver information
     */
    private final void newDriverMarker(com.taximobility.data.DriverData driver) {
    }
    
    /**
     * Check empty, 0
     *
     * @param latOrLng src coordinates
     */
    private final boolean isValidCoordinates(java.lang.String latOrLng) {
        return false;
    }
    
    private final com.google.android.gms.maps.model.Marker createAndGetMarker(com.google.android.gms.maps.model.LatLng latLng, float bearing, android.graphics.Bitmap carIcon) {
        return null;
    }
    
    private final void getHeadingDirectionFromCoordinate(com.google.android.gms.maps.model.Marker marker, com.google.android.gms.maps.model.LatLng latLng, com.google.android.gms.maps.model.LatLng currentposition, float bearing) {
    }
    
    private final void animateMarker(com.google.android.gms.maps.model.Marker marker, com.google.android.gms.maps.model.LatLng newLatLng, float bearing) {
    }
    
    public final void removeMarkerWithAnimation(@org.jetbrains.annotations.Nullable()
    com.google.android.gms.maps.model.Marker removeMarker) {
    }
    
    public final void removeDriverLiveMovementCallback() {
    }
    
    public final void removeDriverLiveMovement() {
    }
    
    public final void removeStartDriverLiveMovement() {
    }
    
    public DriverLiveMove() {
        super();
    }
}