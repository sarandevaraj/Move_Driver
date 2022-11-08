package com.taximobility.bookingmodule.pickDropLoc;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0006\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u000e\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\tJ\u0006\u0010.\u001a\u00020\u0012J\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00120\u001cJ\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u00100\u001cJ\b\u00101\u001a\u00020,H\u0002J\u0006\u00102\u001a\u00020,J\u0006\u00103\u001a\u00020,J\"\u00104\u001a\u00020,2\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u0002062\b\u00108\u001a\u0004\u0018\u000109H\u0016J&\u0010:\u001a\u0004\u0018\u00010;2\u0006\u0010<\u001a\u00020=2\b\u0010>\u001a\u0004\u0018\u00010?2\b\u0010@\u001a\u0004\u0018\u00010AH\u0016J\u0018\u0010B\u001a\u00020\u001e2\u0006\u0010C\u001a\u00020;2\u0006\u0010D\u001a\u00020EH\u0016J\b\u0010F\u001a\u00020,H\u0002J\u0006\u0010G\u001a\u00020,J\u000e\u0010H\u001a\u00020,2\u0006\u0010I\u001a\u00020\u0012J\u0010\u0010J\u001a\u00020,2\u0006\u0010K\u001a\u00020\tH\u0002J\u0006\u0010L\u001a\u00020,J\b\u0010M\u001a\u00020,H\u0002J\u0006\u0010N\u001a\u00020,J\u000e\u0010O\u001a\u00020,2\u0006\u0010P\u001a\u00020\u0012J\u0010\u0010Q\u001a\u00020,2\u0006\u0010K\u001a\u00020\tH\u0002J\u0006\u0010R\u001a\u00020,J>\u0010S\u001a\u00020,2\u0006\u0010T\u001a\u0002062\u0006\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020V2\u0006\u0010K\u001a\u00020\t2\u0006\u0010X\u001a\u00020\t2\u0006\u0010Y\u001a\u00020\t2\u0006\u0010Z\u001a\u00020\tJ\u000e\u0010[\u001a\u00020,2\u0006\u0010\\\u001a\u00020\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00100\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\"\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\f\"\u0004\b$\u0010\u000eR\u001a\u0010%\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0014\"\u0004\b\'\u0010\u0016R\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00100\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006]"}, d2 = {"Lcom/taximobility/bookingmodule/pickDropLoc/SearchLocationFrag;", "Landroidx/fragment/app/Fragment;", "Landroid/view/View$OnTouchListener;", "()V", "binding", "Lcom/taximobility/databinding/SearchLocationBinding;", "dialogFragment", "Lcom/taximobility/bookingmodule/favourite/FavouriteDialog;", "dropFavPlaceType", "", "dropLocTxt", "getDropLocTxt", "()Ljava/lang/String;", "setDropLocTxt", "(Ljava/lang/String;)V", "dropPlacesData", "Lcom/taximobility/locationSearch/PlacesData;", "droplatlng", "Lcom/google/android/gms/maps/model/LatLng;", "getDroplatlng", "()Lcom/google/android/gms/maps/model/LatLng;", "setDroplatlng", "(Lcom/google/android/gms/maps/model/LatLng;)V", "listener", "Lcom/taximobility/bookingmodule/Interface/PickDropSetListener;", "locFocus", "locationRequestedBy", "mList", "Ljava/util/ArrayList;", "pickDropClick", "", "pickDropFavClick", "pickFavPlaceType", "pickPlaceData", "pickupLocTxt", "getPickupLocTxt", "setPickupLocTxt", "pickuplatlng", "getPickuplatlng", "setPickuplatlng", "placeArray", "searchViewModel", "Lcom/taximobility/bookingmodule/pickDropLoc/SearchViewModel;", "dropClicked", "", "str", "getDropLatLng", "getLatLngPoints", "getStopPoints", "initialize", "locInVisibility", "locVisibility", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onTouch", "view", "motionEvent", "Landroid/view/MotionEvent;", "pickupClicked", "setDropCardElevation", "setDropLatLng", "dropLatLng", "setDropLoc", "address", "setFavIcon", "setFavIconVisibility", "setFavouriteUnselectIcon", "setPickLatLng", "pickLatLng", "setPickLoc", "setPickupCardElevation", "setPickupDropData", "id", "lat", "", "lng", "placeId", "addrType", "favLocType", "setSearchFragmentListener", "pickupDropSet", "app_debug"})
public final class SearchLocationFrag extends androidx.fragment.app.Fragment implements android.view.View.OnTouchListener {
    private com.taximobility.bookingmodule.pickDropLoc.SearchViewModel searchViewModel;
    private com.taximobility.databinding.SearchLocationBinding binding;
    private java.lang.String locFocus = "P";
    private boolean pickDropFavClick = false;
    private boolean pickDropClick = true;
    private java.util.ArrayList<com.taximobility.locationSearch.PlacesData> placeArray;
    private final com.taximobility.bookingmodule.favourite.FavouriteDialog dialogFragment = null;
    private com.taximobility.locationSearch.PlacesData pickPlaceData;
    private com.taximobility.locationSearch.PlacesData dropPlacesData;
    private com.taximobility.bookingmodule.Interface.PickDropSetListener listener;
    private java.util.ArrayList<com.taximobility.locationSearch.PlacesData> mList;
    private java.lang.String locationRequestedBy = "";
    @org.jetbrains.annotations.NotNull()
    private com.google.android.gms.maps.model.LatLng pickuplatlng;
    @org.jetbrains.annotations.NotNull()
    private com.google.android.gms.maps.model.LatLng droplatlng;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String pickupLocTxt = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String dropLocTxt = "";
    private java.lang.String pickFavPlaceType = "0";
    private java.lang.String dropFavPlaceType = "0";
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.android.gms.maps.model.LatLng getPickuplatlng() {
        return null;
    }
    
    public final void setPickuplatlng(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.android.gms.maps.model.LatLng getDroplatlng() {
        return null;
    }
    
    public final void setDroplatlng(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPickupLocTxt() {
        return null;
    }
    
    public final void setPickupLocTxt(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDropLocTxt() {
        return null;
    }
    
    public final void setDropLocTxt(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.android.gms.maps.model.LatLng getDropLatLng() {
        return null;
    }
    
    private final void initialize() {
    }
    
    public final void setSearchFragmentListener(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.Interface.PickDropSetListener pickupDropSet) {
    }
    
    /**
     * Recent places visibility
     */
    public final void locVisibility() {
    }
    
    /**
     * Recent places invisibility
     */
    public final void locInVisibility() {
    }
    
    /**
     * Favourite icon visibility
     */
    private final void setFavIconVisibility() {
    }
    
    /**
     * Pickup location perform click
     */
    private final void pickupClicked() {
    }
    
    /**
     * Drop location perform click
     */
    public final void dropClicked(@org.jetbrains.annotations.NotNull()
    java.lang.String str) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.taximobility.locationSearch.PlacesData> getStopPoints() {
        return null;
    }
    
    /**
     * Pickup location Elevation
     */
    public final void setPickupCardElevation() {
    }
    
    /**
     * Drop location Elevation
     */
    public final void setDropCardElevation() {
    }
    
    /**
     * Set favourite location icon
     */
    public final void setFavIcon() {
    }
    
    /**
     * Set unfavourite location icon
     */
    public final void setFavouriteUnselectIcon() {
    }
    
    public final void setPickupDropData(int id, double lat, double lng, @org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.NotNull()
    java.lang.String placeId, @org.jetbrains.annotations.NotNull()
    java.lang.String addrType, @org.jetbrains.annotations.NotNull()
    java.lang.String favLocType) {
    }
    
    public final void setPickLatLng(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng pickLatLng) {
    }
    
    public final void setDropLatLng(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng dropLatLng) {
    }
    
    private final void setPickLoc(java.lang.String address) {
    }
    
    private final void setDropLoc(java.lang.String address) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.google.android.gms.maps.model.LatLng> getLatLngPoints() {
        return null;
    }
    
    @java.lang.Override()
    public void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    @java.lang.Override()
    public boolean onTouch(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    android.view.MotionEvent motionEvent) {
        return false;
    }
    
    public SearchLocationFrag() {
        super();
    }
}