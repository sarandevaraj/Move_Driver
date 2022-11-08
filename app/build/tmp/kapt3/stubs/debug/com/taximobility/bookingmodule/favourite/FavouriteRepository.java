package com.taximobility.bookingmodule.favourite;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0014\u0010\u0015\u001a\u00020\u00102\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2 = {"Lcom/taximobility/bookingmodule/favourite/FavouriteRepository;", "", "mContext", "Landroid/content/Context;", "(Landroid/content/Context;)V", "db", "Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDatabase;", "getDb", "()Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDatabase;", "getMContext", "()Landroid/content/Context;", "mDao", "Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;", "getMDao", "()Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;", "callAddFavApi", "", "requestData", "Lorg/json/JSONObject;", "viewModel", "Lcom/taximobility/bookingmodule/favourite/FavouriteViewModel;", "insertFavPlaces", "data", "", "Lcom/taximobility/bookingmodule/LocationData;", "insertAsyncTask", "app_debug"})
public final class FavouriteRepository {
    @org.jetbrains.annotations.Nullable()
    private final com.taximobility.bookingmodule.RoomDatabase.PlacesDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.taximobility.bookingmodule.RoomDatabase.PlacesDao mDao = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context mContext = null;
    
    @org.jetbrains.annotations.Nullable()
    public final com.taximobility.bookingmodule.RoomDatabase.PlacesDatabase getDb() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.RoomDatabase.PlacesDao getMDao() {
        return null;
    }
    
    public final void callAddFavApi(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.favourite.FavouriteViewModel viewModel) {
    }
    
    public final void insertFavPlaces(@org.jetbrains.annotations.NotNull()
    java.util.List<com.taximobility.bookingmodule.LocationData> data) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getMContext() {
        return null;
    }
    
    public FavouriteRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context mContext) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0002\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0001B\u001d\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007\u00a2\u0006\u0002\u0010\bJ#\u0010\u000b\u001a\u0004\u0018\u00010\u00032\u0012\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\r\"\u00020\u0002H\u0014\u00a2\u0006\u0002\u0010\u000eR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/taximobility/bookingmodule/favourite/FavouriteRepository$insertAsyncTask;", "Landroid/os/AsyncTask;", "Lcom/taximobility/bookingmodule/LocationData;", "Ljava/lang/Void;", "mPlaceDao", "Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;", "favModel", "", "(Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;Ljava/util/List;)V", "getFavModel", "()Ljava/util/List;", "doInBackground", "params", "", "([Lcom/taximobility/bookingmodule/LocationData;)Ljava/lang/Void;", "app_debug"})
    static final class insertAsyncTask extends android.os.AsyncTask<com.taximobility.bookingmodule.LocationData, java.lang.Void, java.lang.Void> {
        private final com.taximobility.bookingmodule.RoomDatabase.PlacesDao mPlaceDao = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.taximobility.bookingmodule.LocationData> favModel = null;
        
        @org.jetbrains.annotations.Nullable()
        @java.lang.Override()
        protected java.lang.Void doInBackground(@org.jetbrains.annotations.NotNull()
        com.taximobility.bookingmodule.LocationData... params) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.taximobility.bookingmodule.LocationData> getFavModel() {
            return null;
        }
        
        public insertAsyncTask(@org.jetbrains.annotations.NotNull()
        com.taximobility.bookingmodule.RoomDatabase.PlacesDao mPlaceDao, @org.jetbrains.annotations.NotNull()
        java.util.List<com.taximobility.bookingmodule.LocationData> favModel) {
            super();
        }
    }
}