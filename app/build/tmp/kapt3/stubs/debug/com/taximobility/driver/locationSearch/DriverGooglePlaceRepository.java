package com.taximobility.driver.locationSearch;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J \u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0010H\u0016J\u0010\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020&H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/taximobility/driver/locationSearch/DriverGooglePlaceRepository;", "Lcom/taximobility/driver/locationSearch/DriverOnLocationSearched;", "Landroid/widget/Filterable;", "mContext", "Landroid/content/Context;", "listener", "Lcom/taximobility/driver/locationSearch/DriverPlaceSearchList;", "(Landroid/content/Context;Lcom/taximobility/driver/locationSearch/DriverPlaceSearchList;)V", "getListener", "()Lcom/taximobility/driver/locationSearch/DriverPlaceSearchList;", "mBounds", "Lcom/google/android/libraries/places/api/model/RectangularBounds;", "getMContext", "()Landroid/content/Context;", "mResultList", "Ljava/util/ArrayList;", "Lcom/taximobility/driver/locationSearch/DriverPlacesDetail;", "placesClient", "Lcom/google/android/libraries/places/api/net/PlacesClient;", "token", "Lcom/google/android/libraries/places/api/model/AutocompleteSessionToken;", "getAutocomplete", "constraint", "", "getBoundingBox", "Lcom/google/android/gms/maps/model/LatLngBounds;", "pLatitude", "", "pLongitude", "pDistanceInMeters", "", "getFilter", "Landroid/widget/Filter;", "onItemClicked", "", "driverPlacesDetail", "onLocationSearched", "queryString", "", "app_debug"})
public final class DriverGooglePlaceRepository implements com.taximobility.driver.locationSearch.DriverOnLocationSearched, android.widget.Filterable {
    private java.util.ArrayList<com.taximobility.driver.locationSearch.DriverPlacesDetail> mResultList;
    private com.google.android.libraries.places.api.model.RectangularBounds mBounds;
    private com.google.android.libraries.places.api.net.PlacesClient placesClient;
    private final com.google.android.libraries.places.api.model.AutocompleteSessionToken token = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context mContext = null;
    @org.jetbrains.annotations.NotNull()
    private final com.taximobility.driver.locationSearch.DriverPlaceSearchList listener = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public android.widget.Filter getFilter() {
        return null;
    }
    
    @java.lang.Override()
    public void onLocationSearched(@org.jetbrains.annotations.NotNull()
    java.lang.String queryString) {
    }
    
    @java.lang.Override()
    public void onItemClicked(@org.jetbrains.annotations.NotNull()
    com.taximobility.driver.locationSearch.DriverPlacesDetail driverPlacesDetail) {
    }
    
    private final java.util.ArrayList<com.taximobility.driver.locationSearch.DriverPlacesDetail> getAutocomplete(java.lang.CharSequence constraint) {
        return null;
    }
    
    private final com.google.android.gms.maps.model.LatLngBounds getBoundingBox(double pLatitude, double pLongitude, int pDistanceInMeters) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getMContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.driver.locationSearch.DriverPlaceSearchList getListener() {
        return null;
    }
    
    public DriverGooglePlaceRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context mContext, @org.jetbrains.annotations.NotNull()
    com.taximobility.driver.locationSearch.DriverPlaceSearchList listener) {
        super();
    }
}