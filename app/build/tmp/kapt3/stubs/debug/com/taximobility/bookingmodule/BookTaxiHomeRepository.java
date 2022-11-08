package com.taximobility.bookingmodule;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u00042345B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u0016\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u0016\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020 J\u0006\u0010!\u001a\u00020\u0017J\u0012\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100#J\u0012\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100#J\u001a\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100#2\u0006\u0010\u001f\u001a\u00020 J\u001a\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100#2\u0006\u0010\u001a\u001a\u00020\u001bJ\u0016\u0010\'\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u0016\u0010(\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u0016\u0010)\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010*\u001a\u00020\u00172\u0006\u0010+\u001a\u00020,J\u0018\u0010-\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J \u0010.\u001a\u00020\u00172\u0006\u0010/\u001a\u0002002\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0016\u00101\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\u00a8\u00066"}, d2 = {"Lcom/taximobility/bookingmodule/BookTaxiHomeRepository;", "Lcom/taximobility/interfaces/NodeAuthListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "db", "Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDatabase;", "getDb", "()Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDatabase;", "mDao", "Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;", "getMDao", "()Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;", "pastListData", "", "Lcom/taximobility/bookingmodule/LocationData;", "getPastListData", "()Ljava/util/List;", "setPastListData", "(Ljava/util/List;)V", "callDeleteFavourite", "", "requestData", "Lorg/json/JSONObject;", "viewModel", "Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;", "callNearestApi", "checkPromoCodeApiCall", "deletFavourite", "str", "", "deleteSavedPlaces", "getAllFavPopRecPlaces", "Landroidx/lifecycle/LiveData;", "getAllFavouritePlaces", "getFavPlaceWithFilter", "getFavPlaces", "getModelApiCall", "getPassengerInfoApiCall", "getPrefrenceApiCall", "insertFavPlaces", "data", "", "nearestDriverApiCall", "nodeAuthListener", "listener", "", "saveBookingApiCall", "deleteAsyncTask", "deleteAsyncTaskPopular", "deleteFavAsyncTask", "insertAsyncTask", "app_debug"})
public final class BookTaxiHomeRepository implements com.taximobility.interfaces.NodeAuthListener {
    @org.jetbrains.annotations.Nullable()
    private final com.taximobility.bookingmodule.RoomDatabase.PlacesDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.taximobility.bookingmodule.RoomDatabase.PlacesDao mDao = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.taximobility.bookingmodule.LocationData> pastListData;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @java.lang.Override()
    public void nodeAuthListener(boolean listener, @org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.taximobility.bookingmodule.RoomDatabase.PlacesDatabase getDb() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.RoomDatabase.PlacesDao getMDao() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.taximobility.bookingmodule.LocationData> getPastListData() {
        return null;
    }
    
    public final void setPastListData(@org.jetbrains.annotations.NotNull()
    java.util.List<com.taximobility.bookingmodule.LocationData> p0) {
    }
    
    public final void callNearestApi(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel) {
    }
    
    private final void nearestDriverApiCall(org.json.JSONObject requestData, com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel) {
    }
    
    public final void saveBookingApiCall(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel) {
    }
    
    public final void getModelApiCall(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel) {
    }
    
    public final void getPrefrenceApiCall(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel) {
    }
    
    public final void callDeleteFavourite(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel) {
    }
    
    public final void checkPromoCodeApiCall(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel) {
    }
    
    public final void insertFavPlaces(@org.jetbrains.annotations.NotNull()
    java.lang.Object data) {
    }
    
    public final void getPassengerInfoApiCall(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> getFavPlaces(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> getAllFavPopRecPlaces() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> getAllFavouritePlaces() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> getFavPlaceWithFilter(@org.jetbrains.annotations.NotNull()
    java.lang.String str) {
        return null;
    }
    
    public final void deletFavourite(@org.jetbrains.annotations.NotNull()
    java.lang.String str) {
    }
    
    public final void deleteSavedPlaces() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    public BookTaxiHomeRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0002\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ#\u0010\u000b\u001a\u0004\u0018\u00010\u00032\u0012\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\r\"\u00020\u0002H\u0014\u00a2\u0006\u0002\u0010\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000f"}, d2 = {"Lcom/taximobility/bookingmodule/BookTaxiHomeRepository$deleteFavAsyncTask;", "Landroid/os/AsyncTask;", "Lcom/taximobility/bookingmodule/LocationData;", "Ljava/lang/Void;", "mDao", "Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;", "str", "", "(Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;Ljava/lang/String;)V", "getStr", "()Ljava/lang/String;", "doInBackground", "params", "", "([Lcom/taximobility/bookingmodule/LocationData;)Ljava/lang/Void;", "app_debug"})
    static final class deleteFavAsyncTask extends android.os.AsyncTask<com.taximobility.bookingmodule.LocationData, java.lang.Void, java.lang.Void> {
        private final com.taximobility.bookingmodule.RoomDatabase.PlacesDao mDao = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String str = null;
        
        @org.jetbrains.annotations.Nullable()
        @java.lang.Override()
        protected java.lang.Void doInBackground(@org.jetbrains.annotations.NotNull()
        com.taximobility.bookingmodule.LocationData... params) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getStr() {
            return null;
        }
        
        public deleteFavAsyncTask(@org.jetbrains.annotations.NotNull()
        com.taximobility.bookingmodule.RoomDatabase.PlacesDao mDao, @org.jetbrains.annotations.NotNull()
        java.lang.String str) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0002\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0001B\u001d\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007\u00a2\u0006\u0002\u0010\bJ#\u0010\u000b\u001a\u0004\u0018\u00010\u00032\u0012\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\r\"\u00020\u0002H\u0014\u00a2\u0006\u0002\u0010\u000eR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/taximobility/bookingmodule/BookTaxiHomeRepository$insertAsyncTask;", "Landroid/os/AsyncTask;", "Lcom/taximobility/bookingmodule/LocationData;", "Ljava/lang/Void;", "mPlaceDao", "Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;", "favModel", "", "(Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;Ljava/util/List;)V", "getFavModel", "()Ljava/util/List;", "doInBackground", "params", "", "([Lcom/taximobility/bookingmodule/LocationData;)Ljava/lang/Void;", "app_debug"})
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
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0002\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J#\u0010\u0007\u001a\u0004\u0018\u00010\u00032\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\t\"\u00020\u0002H\u0014\u00a2\u0006\u0002\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/taximobility/bookingmodule/BookTaxiHomeRepository$deleteAsyncTask;", "Landroid/os/AsyncTask;", "Lcom/taximobility/bookingmodule/LocationData;", "Ljava/lang/Void;", "mPlaceDao", "Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;", "(Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;)V", "doInBackground", "params", "", "([Lcom/taximobility/bookingmodule/LocationData;)Ljava/lang/Void;", "app_debug"})
    static final class deleteAsyncTask extends android.os.AsyncTask<com.taximobility.bookingmodule.LocationData, java.lang.Void, java.lang.Void> {
        private final com.taximobility.bookingmodule.RoomDatabase.PlacesDao mPlaceDao = null;
        
        @org.jetbrains.annotations.Nullable()
        @java.lang.Override()
        protected java.lang.Void doInBackground(@org.jetbrains.annotations.NotNull()
        com.taximobility.bookingmodule.LocationData... params) {
            return null;
        }
        
        public deleteAsyncTask(@org.jetbrains.annotations.NotNull()
        com.taximobility.bookingmodule.RoomDatabase.PlacesDao mPlaceDao) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0002\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J#\u0010\u0007\u001a\u0004\u0018\u00010\u00032\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\t\"\u00020\u0002H\u0014\u00a2\u0006\u0002\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/taximobility/bookingmodule/BookTaxiHomeRepository$deleteAsyncTaskPopular;", "Landroid/os/AsyncTask;", "Lcom/taximobility/bookingmodule/LocationData;", "Ljava/lang/Void;", "mPlaceDao", "Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;", "(Lcom/taximobility/bookingmodule/RoomDatabase/PlacesDao;)V", "doInBackground", "params", "", "([Lcom/taximobility/bookingmodule/LocationData;)Ljava/lang/Void;", "app_debug"})
    static final class deleteAsyncTaskPopular extends android.os.AsyncTask<com.taximobility.bookingmodule.LocationData, java.lang.Void, java.lang.Void> {
        private final com.taximobility.bookingmodule.RoomDatabase.PlacesDao mPlaceDao = null;
        
        @org.jetbrains.annotations.Nullable()
        @java.lang.Override()
        protected java.lang.Void doInBackground(@org.jetbrains.annotations.NotNull()
        com.taximobility.bookingmodule.LocationData... params) {
            return null;
        }
        
        public deleteAsyncTaskPopular(@org.jetbrains.annotations.NotNull()
        com.taximobility.bookingmodule.RoomDatabase.PlacesDao mPlaceDao) {
            super();
        }
    }
}