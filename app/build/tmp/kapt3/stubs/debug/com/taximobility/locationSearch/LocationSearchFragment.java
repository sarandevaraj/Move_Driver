package com.taximobility.locationSearch;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020$2\u0006\u0010!\u001a\u00020\"H\u0016J&\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010\'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010*2\b\u0010+\u001a\u0004\u0018\u00010,H\u0016J\b\u0010-\u001a\u00020$H\u0016J\u0010\u0010.\u001a\u00020$2\u0006\u0010/\u001a\u00020\u0013H\u0016J\u0010\u00100\u001a\u00020$2\u0006\u00101\u001a\u00020\u001bH\u0016J\u001a\u00102\u001a\u00020$2\u0006\u00103\u001a\u00020&2\b\u0010+\u001a\u0004\u0018\u00010,H\u0016J\u0010\u00104\u001a\u00020$2\u0006\u00105\u001a\u00020\u0013H\u0016J\u0018\u00106\u001a\u00020$2\u000e\u00107\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R \u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0002X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f\u00a8\u00068"}, d2 = {"Lcom/taximobility/locationSearch/LocationSearchFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/taximobility/locationSearch/OnLocationSearched;", "Lcom/taximobility/locationSearch/PlaceSearchList;", "()V", "bookTaxiHomeRepository", "Lcom/taximobility/bookingmodule/BookTaxiHomeRepository;", "getBookTaxiHomeRepository", "()Lcom/taximobility/bookingmodule/BookTaxiHomeRepository;", "setBookTaxiHomeRepository", "(Lcom/taximobility/bookingmodule/BookTaxiHomeRepository;)V", "isFourSquare", "", "listener", "Lcom/taximobility/locationSearch/SetPlaceResult;", "mAutoCompleteAdapter", "Lcom/taximobility/locationSearch/PlacesAutoCompleteAdapter;", "mList", "Ljava/util/ArrayList;", "Lcom/taximobility/data/apiData/PlacesDetail;", "getMList", "()Ljava/util/ArrayList;", "setMList", "(Ljava/util/ArrayList;)V", "mResultList", "onPlaceSearchedListener", "str", "", "getStr", "()Ljava/lang/String;", "setStr", "(Ljava/lang/String;)V", "getFavouritesList", "context", "Landroid/content/Context;", "onAttach", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDetach", "onItemClicked", "placesDetail", "onLocationSearched", "queryString", "onViewCreated", "view", "setPlaceDetail", "placeDetail", "setPlaceList", "placeDetailResult", "app_debug"})
public final class LocationSearchFragment extends androidx.fragment.app.Fragment implements com.taximobility.locationSearch.OnLocationSearched, com.taximobility.locationSearch.PlaceSearchList {
    @org.jetbrains.annotations.NotNull()
    public com.taximobility.bookingmodule.BookTaxiHomeRepository bookTaxiHomeRepository;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String str = "";
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.taximobility.data.apiData.PlacesDetail> mList;
    private java.util.ArrayList<com.taximobility.data.apiData.PlacesDetail> mResultList;
    private com.taximobility.locationSearch.OnLocationSearched onPlaceSearchedListener;
    private boolean isFourSquare = false;
    private com.taximobility.locationSearch.SetPlaceResult listener;
    private com.taximobility.locationSearch.PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.BookTaxiHomeRepository getBookTaxiHomeRepository() {
        return null;
    }
    
    public final void setBookTaxiHomeRepository(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStr() {
        return null;
    }
    
    public final void setStr(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.taximobility.data.apiData.PlacesDetail> getMList() {
        return null;
    }
    
    public final void setMList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.taximobility.data.apiData.PlacesDetail> p0) {
    }
    
    @java.lang.Override()
    public void onItemClicked(@org.jetbrains.annotations.NotNull()
    com.taximobility.data.apiData.PlacesDetail placesDetail) {
    }
    
    @java.lang.Override()
    public void setPlaceList(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<com.taximobility.data.apiData.PlacesDetail> placeDetailResult) {
    }
    
    @java.lang.Override()
    public void setPlaceDetail(@org.jetbrains.annotations.NotNull()
    com.taximobility.data.apiData.PlacesDetail placeDetail) {
    }
    
    @java.lang.Override()
    public void onLocationSearched(@org.jetbrains.annotations.NotNull()
    java.lang.String queryString) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onAttach(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final java.util.ArrayList<com.taximobility.data.apiData.PlacesDetail> getFavouritesList(android.content.Context context) {
        return null;
    }
    
    @java.lang.Override()
    public void onDetach() {
    }
    
    public LocationSearchFragment() {
        super();
    }
}