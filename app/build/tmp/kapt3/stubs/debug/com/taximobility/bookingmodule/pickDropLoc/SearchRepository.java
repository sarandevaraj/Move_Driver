package com.taximobility.bookingmodule.pickDropLoc;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/taximobility/bookingmodule/pickDropLoc/SearchRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "db", "Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDatabase;", "getDb", "()Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDatabase;", "mDao", "Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;", "getAllPastBookingPlaces", "Landroidx/lifecycle/LiveData;", "", "Lcom/taximobility/bookingmodule/LocationData;", "app_debug"})
public final class SearchRepository {
    @org.jetbrains.annotations.Nullable()
    private final com.taximobility.bookingmodule.RoomDatabase.PlacesDatabase db = null;
    private final com.taximobility.bookingmodule.RoomDatabase.PlacesDao mDao = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @org.jetbrains.annotations.Nullable()
    public final com.taximobility.bookingmodule.RoomDatabase.PlacesDatabase getDb() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> getAllPastBookingPlaces() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    public SearchRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
}