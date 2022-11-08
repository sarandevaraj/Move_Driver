package com.taximobility.bookingmodule.pickDropLoc;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u00105\u001a\u000206J\u000e\u00107\u001a\u0002062\u0006\u00108\u001a\u00020\u0007J\u0012\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020<0;0:J\u0006\u0010=\u001a\u000206J\u000e\u0010)\u001a\u0002062\u0006\u0010>\u001a\u00020\rJ\u000e\u0010\n\u001a\u0002062\u0006\u0010?\u001a\u00020\u0007J\u0016\u0010@\u001a\u0002062\u0006\u0010?\u001a\u00020\u00072\u0006\u0010A\u001a\u00020\u0007J\u000e\u0010\u001c\u001a\u0002062\u0006\u0010?\u001a\u00020\u0007J\u001e\u0010B\u001a\u0002062\u0016\u0010C\u001a\u0012\u0012\u0004\u0012\u00020E0Dj\b\u0012\u0004\u0012\u00020E`FR \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR \u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR \u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\t\"\u0004\b\u0013\u0010\u000bR \u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\t\"\u0004\b\u0016\u0010\u000bR \u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\t\"\u0004\b\u0019\u0010\u000bR \u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\t\"\u0004\b\u001c\u0010\u000bR \u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\t\"\u0004\b\u001f\u0010\u000bR \u0010 \u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\t\"\u0004\b\"\u0010\u000bR \u0010#\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\t\"\u0004\b%\u0010\u000bR \u0010&\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\t\"\u0004\b(\u0010\u000bR \u0010)\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\t\"\u0004\b+\u0010\u000bR \u0010,\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\t\"\u0004\b.\u0010\u000bR\u001a\u0010/\u001a\u000200X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104\u00a8\u0006G"}, d2 = {"Lcom/taximobility/bookingmodule/pickDropLoc/SearchViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "dropLoc", "Landroidx/lifecycle/MutableLiveData;", "", "getDropLoc", "()Landroidx/lifecycle/MutableLiveData;", "setDropLoc", "(Landroidx/lifecycle/MutableLiveData;)V", "dropVisible", "", "getDropVisible", "setDropVisible", "favClick", "", "getFavClick", "setFavClick", "pickDropclick", "getPickDropclick", "setPickDropclick", "pickupDropLoc", "getPickupDropLoc", "setPickupDropLoc", "pickupLoc", "getPickupLoc", "setPickupLoc", "pickupVisible", "getPickupVisible", "setPickupVisible", "recentPlace1", "getRecentPlace1", "setRecentPlace1", "recentPlace2", "getRecentPlace2", "setRecentPlace2", "recentPlace3", "getRecentPlace3", "setRecentPlace3", "recentPlaceClick", "getRecentPlaceClick", "setRecentPlaceClick", "recentVisible", "getRecentVisible", "setRecentVisible", "searchRepository", "Lcom/taximobility/bookingmodule/pickDropLoc/SearchRepository;", "getSearchRepository", "()Lcom/taximobility/bookingmodule/pickDropLoc/SearchRepository;", "setSearchRepository", "(Lcom/taximobility/bookingmodule/pickDropLoc/SearchRepository;)V", "dropClick", "", "favIconClick", "locType", "getAllPastBookingPlaces", "Landroidx/lifecycle/LiveData;", "", "Lcom/taximobility/bookingmodule/LocationData;", "pickupClick", "loc", "address", "setPickupDropLocation", "addressType", "setRecentPlaces", "placeArray", "Ljava/util/ArrayList;", "Lcom/taximobility/locationSearch/PlacesData;", "Lkotlin/collections/ArrayList;", "app_debug"})
public final class SearchViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private com.taximobility.bookingmodule.pickDropLoc.SearchRepository searchRepository;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> pickDropclick;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> favClick;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> pickupLoc;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> dropLoc;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> pickupDropLoc;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> recentVisible;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> pickupVisible;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> dropVisible;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> recentPlace1;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> recentPlace2;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> recentPlace3;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> recentPlaceClick;
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.pickDropLoc.SearchRepository getSearchRepository() {
        return null;
    }
    
    public final void setSearchRepository(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.pickDropLoc.SearchRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> getPickDropclick() {
        return null;
    }
    
    public final void setPickDropclick(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> getFavClick() {
        return null;
    }
    
    public final void setFavClick(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getPickupLoc() {
        return null;
    }
    
    public final void setPickupLoc(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getDropLoc() {
        return null;
    }
    
    public final void setDropLoc(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getPickupDropLoc() {
        return null;
    }
    
    public final void setPickupDropLoc(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getRecentVisible() {
        return null;
    }
    
    public final void setRecentVisible(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getPickupVisible() {
        return null;
    }
    
    public final void setPickupVisible(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getDropVisible() {
        return null;
    }
    
    public final void setDropVisible(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getRecentPlace1() {
        return null;
    }
    
    public final void setRecentPlace1(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getRecentPlace2() {
        return null;
    }
    
    public final void setRecentPlace2(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getRecentPlace3() {
        return null;
    }
    
    public final void setRecentPlace3(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getRecentPlaceClick() {
        return null;
    }
    
    public final void setRecentPlaceClick(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    public final void pickupClick() {
    }
    
    public final void dropClick() {
    }
    
    public final void favIconClick(@org.jetbrains.annotations.NotNull()
    java.lang.String locType) {
    }
    
    public final void setPickupDropLocation(@org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.NotNull()
    java.lang.String addressType) {
    }
    
    public final void setPickupLoc(@org.jetbrains.annotations.NotNull()
    java.lang.String address) {
    }
    
    public final void setDropLoc(@org.jetbrains.annotations.NotNull()
    java.lang.String address) {
    }
    
    public final void setRecentPlaces(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.taximobility.locationSearch.PlacesData> placeArray) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> getAllPastBookingPlaces() {
        return null;
    }
    
    public final void recentPlaceClick(int loc) {
    }
    
    public SearchViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}