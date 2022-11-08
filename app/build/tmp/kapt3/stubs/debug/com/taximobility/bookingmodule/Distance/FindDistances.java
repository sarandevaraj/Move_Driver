package com.taximobility.bookingmodule.Distance;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\r\u0018\u00002\u00020\u0001:\u0001,B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J.\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020 J(\u0010$\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020 H\u0002J0\u0010%\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020\t2\u0006\u0010\'\u001a\u00020 2\u0006\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020\tH\u0002J0\u0010+\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020\t2\u0006\u0010\'\u001a\u00020 2\u0006\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020\tH\u0002R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u001a\u0010\b\u001a\u00020\tX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\tX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u000b\"\u0004\b\u0018\u0010\rR\u001c\u0010\u0019\u001a\u0004\u0018\u00010\tX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000b\"\u0004\b\u001b\u0010\r\u00a8\u0006-"}, d2 = {"Lcom/taximobility/bookingmodule/Distance/FindDistances;", "", "disInterface", "Lcom/taximobility/bookingmodule/Interface/RouteListeners;", "(Lcom/taximobility/bookingmodule/Interface/RouteListeners;)V", "getDisInterface", "()Lcom/taximobility/bookingmodule/Interface/RouteListeners;", "setDisInterface", "from", "", "getFrom$app_debug", "()Ljava/lang/String;", "setFrom$app_debug", "(Ljava/lang/String;)V", "mContext", "Landroid/content/Context;", "getMContext", "()Landroid/content/Context;", "setMContext", "(Landroid/content/Context;)V", "mRepository", "Lcom/taximobility/roomDB/MapLoggerRepository;", "to", "getTo$app_debug", "setTo$app_debug", "url", "getUrl$app_debug", "setUrl$app_debug", "getDistance", "", "c", "P_latitude", "", "P_longitude", "D_latitude", "D_longitude", "makeGoogleApiCall", "saveGoogleLog", "s", "times", "dist", "s1", "result", "saveMapboxLog", "GetGoogleLog", "app_debug"})
public final class FindDistances {
    @org.jetbrains.annotations.Nullable()
    private java.lang.String url;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String from = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String to = "";
    @org.jetbrains.annotations.NotNull()
    public android.content.Context mContext;
    private com.taximobility.roomDB.MapLoggerRepository mRepository;
    @org.jetbrains.annotations.NotNull()
    private com.taximobility.bookingmodule.Interface.RouteListeners disInterface;
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUrl$app_debug() {
        return null;
    }
    
    public final void setUrl$app_debug(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFrom$app_debug() {
        return null;
    }
    
    public final void setFrom$app_debug(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTo$app_debug() {
        return null;
    }
    
    public final void setTo$app_debug(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getMContext() {
        return null;
    }
    
    public final void setMContext(@org.jetbrains.annotations.NotNull()
    android.content.Context p0) {
    }
    
    public final void getDistance(@org.jetbrains.annotations.NotNull()
    android.content.Context c, double P_latitude, double P_longitude, double D_latitude, double D_longitude) {
    }
    
    private final void makeGoogleApiCall(double P_latitude, double P_longitude, double D_latitude, double D_longitude) {
    }
    
    private final void saveGoogleLog(java.lang.String s, double times, double dist, java.lang.String s1, java.lang.String result) {
    }
    
    private final void saveMapboxLog(java.lang.String s, double times, double dist, java.lang.String s1, java.lang.String result) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.Interface.RouteListeners getDisInterface() {
        return null;
    }
    
    public final void setDisInterface(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.Interface.RouteListeners p0) {
    }
    
    public FindDistances(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.Interface.RouteListeners disInterface) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B%\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\tJ#\u0010\n\u001a\u0004\u0018\u00010\u00032\u0012\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\f\"\u00020\u0002H\u0014\u00a2\u0006\u0002\u0010\rJ\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003H\u0014R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/taximobility/bookingmodule/Distance/FindDistances$GetGoogleLog;", "Landroid/os/AsyncTask;", "Ljava/lang/Void;", "Lcom/taximobility/roomDB/GoogleMapModel;", "P_latitude", "", "P_longitude", "D_latitude", "D_longitude", "(Lcom/taximobility/bookingmodule/Distance/FindDistances;DDDD)V", "doInBackground", "voids", "", "([Ljava/lang/Void;)Lcom/taximobility/roomDB/GoogleMapModel;", "onPostExecute", "", "model", "app_debug"})
    final class GetGoogleLog extends android.os.AsyncTask<java.lang.Void, java.lang.Void, com.taximobility.roomDB.GoogleMapModel> {
        private final double P_latitude = 0.0;
        private final double P_longitude = 0.0;
        private final double D_latitude = 0.0;
        private final double D_longitude = 0.0;
        
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
        
        public GetGoogleLog(double P_latitude, double P_longitude, double D_latitude, double D_longitude) {
            super();
        }
    }
}