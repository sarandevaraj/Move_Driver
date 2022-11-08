package com.taximobility.driver.locationSearch;

import java.lang.System;

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J&\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u0017H\u0016J\u0010\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\bH\u0016J\u0010\u0010#\u001a\u00020\u00172\u0006\u0010$\u001a\u00020%H\u0016J\u001a\u0010&\u001a\u00020\u00172\u0006\u0010\'\u001a\u00020\u00192\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u0010\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\bH\u0016J\u0018\u0010*\u001a\u00020\u00172\u000e\u0010+\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007H\u0016R\u000e\u0010\u0005\u001a\u00020\u0002X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/taximobility/driver/locationSearch/DriverLocationSearchFragmentDriverDriver;", "Landroidx/fragment/app/Fragment;", "Lcom/taximobility/driver/locationSearch/DriverOnLocationSearched;", "Lcom/taximobility/driver/locationSearch/DriverPlaceSearchList;", "()V", "driverOnPlaceSearchedListener", "favouritesList", "Ljava/util/ArrayList;", "Lcom/taximobility/driver/locationSearch/DriverPlacesDetail;", "imgPoweredBy", "Landroidx/appcompat/widget/AppCompatImageView;", "isFourSquare", "", "listener", "Lcom/taximobility/driver/locationSearch/DriverSetPlaceResult;", "mAutoCompleteAdapterDriver", "Lcom/taximobility/driver/locationSearch/DriverPlacesAutoCompleteAdapter;", "rvLocationItems", "Landroidx/recyclerview/widget/RecyclerView;", "getFavouritesList", "context", "Landroid/content/Context;", "onAttach", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDetach", "onItemClicked", "driverPlacesDetail", "onLocationSearched", "queryString", "", "onViewCreated", "view", "setPlaceDetail", "placeDetailDriver", "setPlaceList", "placeDetailResultDriver", "app_debug"})
public final class DriverLocationSearchFragmentDriverDriver extends androidx.fragment.app.Fragment implements com.taximobility.driver.locationSearch.DriverOnLocationSearched, com.taximobility.driver.locationSearch.DriverPlaceSearchList {
    private com.taximobility.driver.locationSearch.DriverOnLocationSearched driverOnPlaceSearchedListener;
    private boolean isFourSquare = false;
    private com.taximobility.driver.locationSearch.DriverSetPlaceResult listener;
    private com.taximobility.driver.locationSearch.DriverPlacesAutoCompleteAdapter mAutoCompleteAdapterDriver;
    private java.util.ArrayList<com.taximobility.driver.locationSearch.DriverPlacesDetail> favouritesList;
    private androidx.recyclerview.widget.RecyclerView rvLocationItems;
    private androidx.appcompat.widget.AppCompatImageView imgPoweredBy;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public void onItemClicked(@org.jetbrains.annotations.NotNull()
    com.taximobility.driver.locationSearch.DriverPlacesDetail driverPlacesDetail) {
    }
    
    @java.lang.Override()
    public void setPlaceList(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<com.taximobility.driver.locationSearch.DriverPlacesDetail> placeDetailResultDriver) {
    }
    
    @java.lang.Override()
    public void setPlaceDetail(@org.jetbrains.annotations.NotNull()
    com.taximobility.driver.locationSearch.DriverPlacesDetail placeDetailDriver) {
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
    
    private final java.util.ArrayList<com.taximobility.driver.locationSearch.DriverPlacesDetail> getFavouritesList(android.content.Context context) {
        return null;
    }
    
    @java.lang.Override()
    public void onDetach() {
    }
    
    public DriverLocationSearchFragmentDriverDriver() {
        super();
    }
}