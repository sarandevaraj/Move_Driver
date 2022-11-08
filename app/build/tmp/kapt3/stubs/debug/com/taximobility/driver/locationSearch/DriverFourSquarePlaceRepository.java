package com.taximobility.driver.locationSearch;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0016B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\bH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0018\u00010\nR\u00020\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/taximobility/driver/locationSearch/DriverFourSquarePlaceRepository;", "Lcom/taximobility/driver/locationSearch/DriverOnLocationSearched;", "mContext", "Landroid/content/Context;", "listener", "Lcom/taximobility/driver/locationSearch/DriverPlaceSearchList;", "(Landroid/content/Context;Lcom/taximobility/driver/locationSearch/DriverPlaceSearchList;)V", "city", "", "exploreAsyncTask", "Lcom/taximobility/driver/locationSearch/DriverFourSquarePlaceRepository$ExploreAsyncTask;", "getListener", "()Lcom/taximobility/driver/locationSearch/DriverPlaceSearchList;", "getMContext", "()Landroid/content/Context;", "state", "onItemClicked", "", "driverPlacesDetail", "Lcom/taximobility/driver/locationSearch/DriverPlacesDetail;", "onLocationSearched", "queryString", "ExploreAsyncTask", "app_debug"})
public final class DriverFourSquarePlaceRepository implements com.taximobility.driver.locationSearch.DriverOnLocationSearched {
    private com.taximobility.driver.locationSearch.DriverFourSquarePlaceRepository.ExploreAsyncTask exploreAsyncTask;
    private java.lang.String city = "";
    private java.lang.String state = "";
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context mContext = null;
    @org.jetbrains.annotations.NotNull()
    private final com.taximobility.driver.locationSearch.DriverPlaceSearchList listener = null;
    
    @java.lang.Override()
    public void onLocationSearched(@org.jetbrains.annotations.NotNull()
    java.lang.String queryString) {
    }
    
    @java.lang.Override()
    public void onItemClicked(@org.jetbrains.annotations.NotNull()
    com.taximobility.driver.locationSearch.DriverPlacesDetail driverPlacesDetail) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getMContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.driver.locationSearch.DriverPlaceSearchList getListener() {
        return null;
    }
    
    public DriverFourSquarePlaceRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context mContext, @org.jetbrains.annotations.NotNull()
    com.taximobility.driver.locationSearch.DriverPlaceSearchList listener) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0001B\u0005\u00a2\u0006\u0002\u0010\u0004J!\u0010\u0005\u001a\u00020\u00032\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0007\"\u00020\u0002H\u0014\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/taximobility/driver/locationSearch/DriverFourSquarePlaceRepository$ExploreAsyncTask;", "Landroid/os/AsyncTask;", "", "", "(Lcom/taximobility/driver/locationSearch/DriverFourSquarePlaceRepository;)V", "doInBackground", "params", "", "([Ljava/lang/String;)V", "app_debug"})
    final class ExploreAsyncTask extends android.os.AsyncTask<java.lang.String, kotlin.Unit, kotlin.Unit> {
        
        @java.lang.Override()
        protected void doInBackground(@org.jetbrains.annotations.NotNull()
        java.lang.String... params) {
        }
        
        public ExploreAsyncTask() {
            super();
        }
    }
}