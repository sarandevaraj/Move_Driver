package com.taximobility.bookingmodule;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006JH\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0016\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u001bj\b\u0012\u0004\u0012\u00020\u0018`\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u001e\u0010\u001f\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J.\u0010 \u001a\u00020\u00122\u0006\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u0006J\u0006\u0010&\u001a\u00020\'J.\u0010(\u001a\u00020\u00062\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00062\u0006\u0010+\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/taximobility/bookingmodule/FindETAFare;", "", "routeListener", "Lcom/taximobility/bookingmodule/Interface/RouteListeners;", "(Lcom/taximobility/bookingmodule/Interface/RouteListeners;)V", "approxFare", "", "approximateDistance", "approximateTime", "mDistance", "Lcom/taximobility/bookingmodule/Distance/FindDistances;", "mRoute", "Lcom/taximobility/bookingmodule/route/FindRoute;", "speed", "", "calculateTime", "distance", "drawRoutePickDrop", "", "mMap", "Lcom/google/android/gms/maps/GoogleMap;", "activity", "Landroid/content/Context;", "pickupLatLng", "Lcom/google/android/gms/maps/model/LatLng;", "dropLatLng", "mListLatLng", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "isNeedToDrawRoute", "", "findApproximateFare", "findETA", "context", "pickLat", "pickLng", "driverLat", "driverLng", "getOverviewPolyline", "", "haverSine", "lat1", "lon1", "lat2", "lon2", "app_debug"})
public final class FindETAFare {
    private com.taximobility.bookingmodule.route.FindRoute mRoute;
    private com.taximobility.bookingmodule.Distance.FindDistances mDistance;
    private double approxFare = 0.0;
    private int speed = 45;
    private double approximateDistance = 0.0;
    private double approximateTime = 0.0;
    private final com.taximobility.bookingmodule.Interface.RouteListeners routeListener = null;
    
    public final void drawRoutePickDrop(@org.jetbrains.annotations.Nullable()
    com.google.android.gms.maps.GoogleMap mMap, @org.jetbrains.annotations.NotNull()
    android.content.Context activity, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng pickupLatLng, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng dropLatLng, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.google.android.gms.maps.model.LatLng> mListLatLng, boolean isNeedToDrawRoute) {
    }
    
    public final void findApproximateFare(@org.jetbrains.annotations.NotNull()
    android.content.Context activity, double approximateDistance, double approximateTime) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOverviewPolyline() {
        return null;
    }
    
    public final void findETA(@org.jetbrains.annotations.NotNull()
    android.content.Context context, double pickLat, double pickLng, double driverLat, double driverLng) {
    }
    
    /**
     * This Function is used for calculate the distance travelled
     */
    public final synchronized double haverSine(double lat1, double lon1, double lat2, double lon2, @org.jetbrains.annotations.NotNull()
    android.content.Context activity) {
        return 0.0;
    }
    
    public final double calculateTime(double distance) {
        return 0.0;
    }
    
    public FindETAFare(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.Interface.RouteListeners routeListener) {
        super();
    }
}