package com.taximobility.bookingmodule.Data;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b4\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0097\u0001\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b\u0012\b\b\u0002\u0010\f\u001a\u00020\u0006\u0012\b\b\u0002\u0010\r\u001a\u00020\b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\b\u0012\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0003\u0012\u000e\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0003\u0012\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0003\u0012\u0006\u0010\u0013\u001a\u00020\b\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\u0002\u0010\u0016J\u000f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0011\u0010<\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0003H\u00c6\u0003J\u0011\u0010=\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0003H\u00c6\u0003J\t\u0010>\u001a\u00020\bH\u00c6\u0003J\t\u0010?\u001a\u00020\u0015H\u00c6\u0003J\t\u0010@\u001a\u00020\u0006H\u00c6\u0003J\t\u0010A\u001a\u00020\bH\u00c6\u0003J\t\u0010B\u001a\u00020\nH\u00c6\u0003J\t\u0010C\u001a\u00020\bH\u00c6\u0003J\t\u0010D\u001a\u00020\u0006H\u00c6\u0003J\t\u0010E\u001a\u00020\bH\u00c6\u0003J\t\u0010F\u001a\u00020\bH\u00c6\u0003J\u0011\u0010G\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0003H\u00c6\u0003J\u00a9\u0001\u0010H\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\b2\b\b\u0002\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u000e\u001a\u00020\b2\u0010\b\u0002\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u00032\u0010\b\u0002\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u00032\u0010\b\u0002\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u00032\b\b\u0002\u0010\u0013\u001a\u00020\b2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u00c6\u0001J\u0013\u0010I\u001a\u00020J2\b\u0010K\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010L\u001a\u00020\u0006H\u00d6\u0001J\t\u0010M\u001a\u00020\bH\u00d6\u0001R \u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u000b\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001c\"\u0004\b$\u0010\u001eR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R\"\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0018\"\u0004\b*\u0010\u001aR\u001a\u0010\r\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u001c\"\u0004\b,\u0010\u001eR\u001a\u0010\u000e\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u001c\"\u0004\b.\u0010\u001eR\"\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0018\"\u0004\b0\u0010\u001aR\"\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u0018\"\u0004\b2\u0010\u001aR\u001a\u0010\f\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u0010&\"\u0004\b4\u0010(R\u001a\u0010\u0013\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\u001c\"\u0004\b6\u0010\u001eR\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:\u00a8\u0006N"}, d2 = {"Lcom/taximobility/bookingmodule/Data/NearestDriverDatas;", "", "detail", "", "Lcom/taximobility/bookingmodule/Data/Detail;", "fav_drivers", "", "fav_driver_message", "", "fare_details", "Lcom/taximobility/bookingmodule/Data/FareDetails;", "driver_around_miles", "status", "message", "metric", "favourite_places", "Lcom/taximobility/bookingmodule/LocationData;", "popular_places", "past_booking_places", "zone_fare_applicable", "zone_zone_fare", "", "(Ljava/util/List;ILjava/lang/String;Lcom/taximobility/bookingmodule/Data/FareDetails;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/String;D)V", "getDetail", "()Ljava/util/List;", "setDetail", "(Ljava/util/List;)V", "getDriver_around_miles", "()Ljava/lang/String;", "setDriver_around_miles", "(Ljava/lang/String;)V", "getFare_details", "()Lcom/taximobility/bookingmodule/Data/FareDetails;", "setFare_details", "(Lcom/taximobility/bookingmodule/Data/FareDetails;)V", "getFav_driver_message", "setFav_driver_message", "getFav_drivers", "()I", "setFav_drivers", "(I)V", "getFavourite_places", "setFavourite_places", "getMessage", "setMessage", "getMetric", "setMetric", "getPast_booking_places", "setPast_booking_places", "getPopular_places", "setPopular_places", "getStatus", "setStatus", "getZone_fare_applicable", "setZone_fare_applicable", "getZone_zone_fare", "()D", "setZone_zone_fare", "(D)V", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class NearestDriverDatas {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.taximobility.bookingmodule.Data.Detail> detail;
    private int fav_drivers;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String fav_driver_message;
    @org.jetbrains.annotations.NotNull()
    private com.taximobility.bookingmodule.Data.FareDetails fare_details;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String driver_around_miles;
    private int status;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String message;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String metric;
    @org.jetbrains.annotations.Nullable()
    private java.util.List<com.taximobility.bookingmodule.LocationData> favourite_places;
    @org.jetbrains.annotations.Nullable()
    private java.util.List<com.taximobility.bookingmodule.LocationData> popular_places;
    @org.jetbrains.annotations.Nullable()
    private java.util.List<com.taximobility.bookingmodule.LocationData> past_booking_places;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String zone_fare_applicable;
    private double zone_zone_fare;
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.taximobility.bookingmodule.Data.Detail> getDetail() {
        return null;
    }
    
    public final void setDetail(@org.jetbrains.annotations.NotNull()
    java.util.List<com.taximobility.bookingmodule.Data.Detail> p0) {
    }
    
    public final int getFav_drivers() {
        return 0;
    }
    
    public final void setFav_drivers(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFav_driver_message() {
        return null;
    }
    
    public final void setFav_driver_message(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.Data.FareDetails getFare_details() {
        return null;
    }
    
    public final void setFare_details(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.Data.FareDetails p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDriver_around_miles() {
        return null;
    }
    
    public final void setDriver_around_miles(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final int getStatus() {
        return 0;
    }
    
    public final void setStatus(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMessage() {
        return null;
    }
    
    public final void setMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMetric() {
        return null;
    }
    
    public final void setMetric(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.taximobility.bookingmodule.LocationData> getFavourite_places() {
        return null;
    }
    
    public final void setFavourite_places(@org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.taximobility.bookingmodule.LocationData> getPopular_places() {
        return null;
    }
    
    public final void setPopular_places(@org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.taximobility.bookingmodule.LocationData> getPast_booking_places() {
        return null;
    }
    
    public final void setPast_booking_places(@org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getZone_fare_applicable() {
        return null;
    }
    
    public final void setZone_fare_applicable(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final double getZone_zone_fare() {
        return 0.0;
    }
    
    public final void setZone_zone_fare(double p0) {
    }
    
    public NearestDriverDatas(@org.jetbrains.annotations.NotNull()
    java.util.List<com.taximobility.bookingmodule.Data.Detail> detail, int fav_drivers, @org.jetbrains.annotations.NotNull()
    java.lang.String fav_driver_message, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.Data.FareDetails fare_details, @org.jetbrains.annotations.NotNull()
    java.lang.String driver_around_miles, int status, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    java.lang.String metric, @org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> favourite_places, @org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> popular_places, @org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> past_booking_places, @org.jetbrains.annotations.NotNull()
    java.lang.String zone_fare_applicable, double zone_zone_fare) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.taximobility.bookingmodule.Data.Detail> component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.Data.FareDetails component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.taximobility.bookingmodule.LocationData> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.taximobility.bookingmodule.LocationData> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.taximobility.bookingmodule.LocationData> component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    public final double component13() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.Data.NearestDriverDatas copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.taximobility.bookingmodule.Data.Detail> detail, int fav_drivers, @org.jetbrains.annotations.NotNull()
    java.lang.String fav_driver_message, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.Data.FareDetails fare_details, @org.jetbrains.annotations.NotNull()
    java.lang.String driver_around_miles, int status, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    java.lang.String metric, @org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> favourite_places, @org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> popular_places, @org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> past_booking_places, @org.jetbrains.annotations.NotNull()
    java.lang.String zone_fare_applicable, double zone_zone_fare) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object p0) {
        return false;
    }
}