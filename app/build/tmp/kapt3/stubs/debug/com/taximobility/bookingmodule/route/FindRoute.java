package com.taximobility.bookingmodule.route;

import java.lang.System;

/**
 * Draw the route to the map object .
 * Routes are drawn with attributes according to the constructor its triggered.
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001:\u0001VB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0002J\u000e\u00101\u001a\u00020\u00062\u0006\u00102\u001a\u00020\u0006J\u0016\u00103\u001a\b\u0012\u0004\u0012\u00020\f0\u00112\u0006\u00104\u001a\u00020\u0019H\u0002J\b\u00105\u001a\u00020.H\u0002J\u001b\u00106\u001a\u00020.2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\f0\u0011H\u0000\u00a2\u0006\u0002\b8J6\u00109\u001a\u00020.2\u0006\u0010:\u001a\u00020\u00062\u0006\u0010;\u001a\u00020\u00062\u0006\u0010<\u001a\u00020\u00062\u0006\u0010=\u001a\u00020\u00062\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\f0,H\u0002J.\u0010>\u001a\u00020\u00062\u0006\u0010?\u001a\u00020\u00062\u0006\u0010@\u001a\u00020\u00062\u0006\u0010A\u001a\u00020\u00062\u0006\u0010B\u001a\u00020\u00062\u0006\u0010C\u001a\u00020\u0013J8\u0010D\u001a\u00020\u00192\u0006\u0010E\u001a\u00020\u00062\u0006\u0010F\u001a\u00020\u00062\u0006\u0010G\u001a\u00020\u00062\u0006\u0010H\u001a\u00020\u00062\u000e\u0010I\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010,H\u0002J\u0016\u0010J\u001a\b\u0012\u0004\u0012\u00020\f0\u00112\u0006\u0010K\u001a\u00020LH\u0002J\u0018\u0010M\u001a\u00020.2\u0006\u0010N\u001a\u00020\u00192\u0006\u0010O\u001a\u00020\u0019H\u0002J\b\u0010P\u001a\u00020.H\u0002J@\u0010Q\u001a\u00020.2\u0006\u0010R\u001a\u00020\u00152\u0006\u0010S\u001a\u00020\u00132\b\u0010T\u001a\u0004\u0018\u00010\f2\b\u0010U\u001a\u0004\u0018\u00010\f2\f\u0010I\u001a\b\u0012\u0004\u0012\u00020\f0,2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u00020\u0003X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010\u0004R\u0012\u0010(\u001a\u00060)j\u0002`*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020\f0,X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006W"}, d2 = {"Lcom/taximobility/bookingmodule/route/FindRoute;", "", "routeInterface", "Lcom/taximobility/bookingmodule/Interface/RouteListeners;", "(Lcom/taximobility/bookingmodule/Interface/RouteListeners;)V", "approxFare", "", "blackPolyLine", "Lcom/google/android/gms/maps/model/Polyline;", "dLat", "dLng", "drop", "Lcom/google/android/gms/maps/model/LatLng;", "greyPolyLine", "isNeedToDrawRoute", "", "listLatLng", "", "mContext", "Landroid/content/Context;", "mMap", "Lcom/google/android/gms/maps/GoogleMap;", "mRepository", "Lcom/taximobility/roomDB/MapLoggerRepository;", "overViewPolyLine", "", "getOverViewPolyLine", "()Ljava/lang/String;", "setOverViewPolyLine", "(Ljava/lang/String;)V", "pLat", "pLng", "pickUp", "polyLineAnimationListener", "Landroid/animation/Animator$AnimatorListener;", "requestedType", "", "getRouteInterface$app_debug", "()Lcom/taximobility/bookingmodule/Interface/RouteListeners;", "setRouteInterface$app_debug", "wayPoint", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "wayPoints", "Ljava/util/ArrayList;", "animatePolyLine", "", "durations", "", "calculateTime", "distance", "decodePoly", "encodedPath", "drawMarker", "drawRoutePolyline", "result", "drawRoutePolyline$app_debug", "getRouteFromGoogle", "pLatitude", "pLongitude", "D_latitude", "D_longitude", "haverSine", "lat1", "lon1", "lat2", "lon2", "activity", "makeDirectionUrl", "p_latitude", "p_longitude", "d_latitude", "d_longitude", "points", "parsePolylineFromPoints", "jObject", "Lorg/json/JSONObject;", "saveGoogleLog", "latLngKey", "routeResult", "setFailureDistance", "setUpPolyLine", "map", "mcontext", "source", "destination", "getGoogleRouteLog", "app_debug"})
public final class FindRoute {
    private android.content.Context mContext;
    private com.taximobility.roomDB.MapLoggerRepository mRepository;
    private com.google.android.gms.maps.GoogleMap mMap;
    private java.lang.StringBuilder wayPoint;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String overViewPolyLine = "";
    private com.google.android.gms.maps.model.Polyline blackPolyLine;
    private com.google.android.gms.maps.model.Polyline greyPolyLine;
    private java.util.List<com.google.android.gms.maps.model.LatLng> listLatLng;
    private double pLat = 0.0;
    private double pLng = 0.0;
    private double dLat = 0.0;
    private double dLng = 0.0;
    private java.util.ArrayList<com.google.android.gms.maps.model.LatLng> wayPoints;
    private double approxFare = 0.0;
    private boolean isNeedToDrawRoute = true;
    private int requestedType = 0;
    private com.google.android.gms.maps.model.LatLng pickUp;
    private com.google.android.gms.maps.model.LatLng drop;
    private android.animation.Animator.AnimatorListener polyLineAnimationListener;
    @org.jetbrains.annotations.NotNull()
    private com.taximobility.bookingmodule.Interface.RouteListeners routeInterface;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOverViewPolyLine() {
        return null;
    }
    
    public final void setOverViewPolyLine(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    /**
     * Entry point to draw route
     *
     * @param map
     * @param mcontext
     * @param source
     * @param destination
     */
    public final void setUpPolyLine(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap map, @org.jetbrains.annotations.NotNull()
    android.content.Context mcontext, @org.jetbrains.annotations.Nullable()
    com.google.android.gms.maps.model.LatLng source, @org.jetbrains.annotations.Nullable()
    com.google.android.gms.maps.model.LatLng destination, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.google.android.gms.maps.model.LatLng> points, boolean isNeedToDrawRoute) {
    }
    
    private final void getRouteFromGoogle(double pLatitude, double pLongitude, double D_latitude, double D_longitude, java.util.ArrayList<com.google.android.gms.maps.model.LatLng> wayPoints) {
    }
    
    private final void setFailureDistance() {
    }
    
    /**
     * Get a list of latlng from polyline by decode
     *
     * @param jObject
     * @return
     */
    private final java.util.List<com.google.android.gms.maps.model.LatLng> parsePolylineFromPoints(org.json.JSONObject jObject) {
        return null;
    }
    
    private final java.util.List<com.google.android.gms.maps.model.LatLng> decodePoly(java.lang.String encodedPath) {
        return null;
    }
    
    private final java.lang.String makeDirectionUrl(double p_latitude, double p_longitude, double d_latitude, double d_longitude, java.util.ArrayList<com.google.android.gms.maps.model.LatLng> points) {
        return null;
    }
    
    public final void drawRoutePolyline$app_debug(@org.jetbrains.annotations.NotNull()
    java.util.List<com.google.android.gms.maps.model.LatLng> result) {
    }
    
    private final void animatePolyLine(long durations) {
    }
    
    private final void drawMarker() {
    }
    
    private final void saveGoogleLog(java.lang.String latLngKey, java.lang.String routeResult) {
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
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.Interface.RouteListeners getRouteInterface$app_debug() {
        return null;
    }
    
    public final void setRouteInterface$app_debug(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.Interface.RouteListeners p0) {
    }
    
    public FindRoute(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.Interface.RouteListeners routeInterface) {
        super();
    }
    
    /**
     * Check whether available in DB
     */
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B%\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b\u00a2\u0006\u0002\u0010\tJ#\u0010\u0012\u001a\u0004\u0018\u00010\u00032\u0012\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0014\"\u00020\u0002H\u0014\u00a2\u0006\u0002\u0010\u0015J\u0012\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003H\u0014R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/taximobility/bookingmodule/route/FindRoute$getGoogleRouteLog;", "Landroid/os/AsyncTask;", "Ljava/lang/Void;", "Lcom/taximobility/roomDB/GoogleMapModel;", "source", "Lcom/google/android/gms/maps/model/LatLng;", "destination", "points", "Ljava/util/ArrayList;", "(Lcom/taximobility/bookingmodule/route/FindRoute;Lcom/google/android/gms/maps/model/LatLng;Lcom/google/android/gms/maps/model/LatLng;Ljava/util/ArrayList;)V", "dLatitude", "", "dLongitude", "from", "", "pLatitude", "pLongitude", "to", "doInBackground", "voids", "", "([Ljava/lang/Void;)Lcom/taximobility/roomDB/GoogleMapModel;", "onPostExecute", "", "model", "app_debug"})
    final class getGoogleRouteLog extends android.os.AsyncTask<java.lang.Void, java.lang.Void, com.taximobility.roomDB.GoogleMapModel> {
        private final double pLatitude = 0.0;
        private final double pLongitude = 0.0;
        private final double dLatitude = 0.0;
        private final double dLongitude = 0.0;
        private java.lang.String from = "";
        private java.lang.String to = "";
        
        @org.jetbrains.annotations.Nullable()
        @java.lang.Override()
        protected com.taximobility.roomDB.GoogleMapModel doInBackground(@org.jetbrains.annotations.NotNull()
        java.lang.Void... voids) {
            return null;
        }
        
        @java.lang.Override()
        protected void onPostExecute(@org.jetbrains.annotations.Nullable()
        com.taximobility.roomDB.GoogleMapModel model) {
        }
        
        public getGoogleRouteLog(@org.jetbrains.annotations.NotNull()
        com.google.android.gms.maps.model.LatLng source, @org.jetbrains.annotations.NotNull()
        com.google.android.gms.maps.model.LatLng destination, @org.jetbrains.annotations.NotNull()
        java.util.ArrayList<com.google.android.gms.maps.model.LatLng> points) {
            super();
        }
    }
}