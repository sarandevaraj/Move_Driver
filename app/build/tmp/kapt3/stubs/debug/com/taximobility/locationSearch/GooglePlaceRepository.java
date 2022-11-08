package com.taximobility.locationSearch;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J \u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020&H\u0016J\u0010\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0016H\u0016J\u0010\u0010*\u001a\u00020(2\u0006\u0010+\u001a\u00020,H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/taximobility/locationSearch/GooglePlaceRepository;", "Lcom/taximobility/locationSearch/OnLocationSearched;", "Landroid/widget/Filterable;", "mContext", "Landroid/content/Context;", "listener", "Lcom/taximobility/locationSearch/PlaceSearchList;", "(Landroid/content/Context;Lcom/taximobility/locationSearch/PlaceSearchList;)V", "getListener", "()Lcom/taximobility/locationSearch/PlaceSearchList;", "mBounds", "Lcom/google/android/libraries/places/api/model/RectangularBounds;", "getMBounds", "()Lcom/google/android/libraries/places/api/model/RectangularBounds;", "setMBounds", "(Lcom/google/android/libraries/places/api/model/RectangularBounds;)V", "getMContext", "()Landroid/content/Context;", "mRepository", "Lcom/taximobility/roomDB/LoggerRepository;", "mResultList", "Ljava/util/ArrayList;", "Lcom/taximobility/data/apiData/PlacesDetail;", "placesClient", "Lcom/google/android/libraries/places/api/net/PlacesClient;", "token", "Lcom/google/android/libraries/places/api/model/AutocompleteSessionToken;", "getAutocomplete", "constraint", "", "getBoundingBox", "Lcom/google/android/gms/maps/model/LatLngBounds;", "pLatitude", "", "pLongitude", "pDistanceInMeters", "", "getFilter", "Landroid/widget/Filter;", "onItemClicked", "", "placesDetail", "onLocationSearched", "queryString", "", "app_debug"})
public final class GooglePlaceRepository implements com.taximobility.locationSearch.OnLocationSearched, android.widget.Filterable {
    private final com.taximobility.roomDB.LoggerRepository mRepository = null;
    private java.util.ArrayList<com.taximobility.data.apiData.PlacesDetail> mResultList;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.libraries.places.api.model.RectangularBounds mBounds;
    private com.google.android.libraries.places.api.net.PlacesClient placesClient;
    private final com.google.android.libraries.places.api.model.AutocompleteSessionToken token = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context mContext = null;
    @org.jetbrains.annotations.NotNull()
    private final com.taximobility.locationSearch.PlaceSearchList listener = null;
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.libraries.places.api.model.RectangularBounds getMBounds() {
        return null;
    }
    
    public final void setMBounds(@org.jetbrains.annotations.Nullable()
    com.google.android.libraries.places.api.model.RectangularBounds p0) {
    }
    
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
    com.taximobility.data.apiData.PlacesDetail placesDetail) {
    }
    
    private final java.util.ArrayList<com.taximobility.data.apiData.PlacesDetail> getAutocomplete(java.lang.CharSequence constraint) {
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
    public final com.taximobility.locationSearch.PlaceSearchList getListener() {
        return null;
    }
    
    public GooglePlaceRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context mContext, @org.jetbrains.annotations.NotNull()
    com.taximobility.locationSearch.PlaceSearchList listener) {
        super();
    }
}