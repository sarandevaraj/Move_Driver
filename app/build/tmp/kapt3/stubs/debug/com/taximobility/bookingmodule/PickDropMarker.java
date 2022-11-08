package com.taximobility.bookingmodule;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\rH\u0002J\u0016\u0010\u0012\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014J&\u0010\u0015\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0018\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u0014J.\u0010\u001a\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u001dJ\u0010\u0010\u001e\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/taximobility/bookingmodule/PickDropMarker;", "", "()V", "FREE_TO_MOVE", "", "dropMarker", "Lcom/google/android/gms/maps/model/Marker;", "pickupMarker", "moveCamera", "", "activity", "Landroid/app/Activity;", "map", "Lcom/google/android/gms/maps/GoogleMap;", "bottomViewLay", "Landroid/widget/LinearLayout;", "resetCameraBearing", "mMap", "setDropMarker", "dropLatLng", "Lcom/google/android/gms/maps/model/LatLng;", "setDropMarkerWithCustomView", "dropLoc", "", "setPickMarker", "pickupLatLng", "setPickMarkerWithCustomView", "pickLoc", "ETime", "", "updateCameraBearing", "app_debug"})
public final class PickDropMarker {
    private static com.google.android.gms.maps.model.Marker dropMarker;
    private static com.google.android.gms.maps.model.Marker pickupMarker;
    private static boolean FREE_TO_MOVE = true;
    public static final com.taximobility.bookingmodule.PickDropMarker INSTANCE = null;
    
    public final void setPickMarker(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap map, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng pickupLatLng) {
    }
    
    public final void setDropMarker(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap map, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng dropLatLng) {
    }
    
    public final void setPickMarkerWithCustomView(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap map, @org.jetbrains.annotations.NotNull()
    java.lang.String pickLoc, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng pickupLatLng, double ETime) {
    }
    
    public final void setDropMarkerWithCustomView(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap map, @org.jetbrains.annotations.NotNull()
    java.lang.String dropLoc, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng dropLatLng) {
    }
    
    public final void moveCamera(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap map, @org.jetbrains.annotations.NotNull()
    android.widget.LinearLayout bottomViewLay) {
    }
    
    private final void updateCameraBearing(com.google.android.gms.maps.GoogleMap mMap) {
    }
    
    private final void resetCameraBearing(com.google.android.gms.maps.GoogleMap mMap) {
    }
    
    private PickDropMarker() {
        super();
    }
}