package com.taximobility.bookingmodule.Data;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0007J\u0011\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0011\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0011\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u00c6\u0003J?\u0010\u0013\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R&\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR&\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR&\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000b\u00a8\u0006\u001b"}, d2 = {"Lcom/taximobility/bookingmodule/Data/PassengerInfoDetail;", "", "favourite_places", "", "Lcom/taximobility/bookingmodule/LocationData;", "popular_places", "past_booking_places", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getFavourite_places", "()Ljava/util/List;", "setFavourite_places", "(Ljava/util/List;)V", "getPast_booking_places", "setPast_booking_places", "getPopular_places", "setPopular_places", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class PassengerInfoDetail {
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "favorite_place")
    private java.util.List<com.taximobility.bookingmodule.LocationData> favourite_places;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "popular_place")
    private java.util.List<com.taximobility.bookingmodule.LocationData> popular_places;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "past_bookings")
    private java.util.List<com.taximobility.bookingmodule.LocationData> past_booking_places;
    
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
    
    public PassengerInfoDetail(@org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> favourite_places, @org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> popular_places, @org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> past_booking_places) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.taximobility.bookingmodule.LocationData> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.taximobility.bookingmodule.LocationData> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.taximobility.bookingmodule.LocationData> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.Data.PassengerInfoDetail copy(@org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> favourite_places, @org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> popular_places, @org.jetbrains.annotations.Nullable()
    java.util.List<com.taximobility.bookingmodule.LocationData> past_booking_places) {
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