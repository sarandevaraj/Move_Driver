package com.taximobility.bookingmodule.RoomDatabase;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\b\u0010\u0006\u001a\u00020\u0003H\'J\b\u0010\u0007\u001a\u00020\u0003H\'J\u001c\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010\f\u001a\u00020\u0005H\'J\u0016\u0010\r\u001a\u00020\u00032\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\'J\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tH\'J\u0014\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tH\'J\u0014\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tH\'J\u0014\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tH\'\u00a8\u0006\u0013"}, d2 = {"Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;", "", "deleteFavourite", "", "str", "", "deletePlace", "deletePopular", "getLocationFilter", "Landroidx/lifecycle/LiveData;", "", "Lcom/taximobility/bookingmodule/LocationData;", "values", "insertLog", "models", "loadAllFavourite", "loadAllFavouriteAndPopularAndRecent", "loadAllPastBookingPlaces", "loadFavPlaces", "app_debug"})
public abstract interface PlacesDao {
    
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract void insertLog(@org.jetbrains.annotations.NotNull()
    java.util.List<com.taximobility.bookingmodule.LocationData> models);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * From favModel WHERE type = 1 or type = 2 ORDER BY type ASC LIMIT 3 ")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> loadFavPlaces();
    
    @androidx.room.Query(value = "DELETE FROM favModel")
    public abstract void deletePlace();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * From favModel WHERE type=1")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> loadAllFavourite();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * From favModel")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> loadAllFavouriteAndPopularAndRecent();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM favModel WHERE location_name LIKE :values || \'%\'")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> getLocationFilter(@org.jetbrains.annotations.NotNull()
    java.lang.String values);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * From favModel WHERE type=3")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> loadAllPastBookingPlaces();
    
    @androidx.room.Query(value = "DELETE FROM favModel WHERE location_name LIKE :str || \'%\'")
    public abstract void deleteFavourite(@org.jetbrains.annotations.NotNull()
    java.lang.String str);
    
    @androidx.room.Query(value = "DELETE FROM favModel WHERE type=2 or type=1")
    public abstract void deletePopular();
}