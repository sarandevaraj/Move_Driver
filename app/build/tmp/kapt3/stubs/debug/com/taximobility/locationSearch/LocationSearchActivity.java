package com.taximobility.locationSearch;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0006\u00106\u001a\u000207J\u0010\u00108\u001a\u0002072\u0006\u00109\u001a\u00020\u0013H\u0016J\u0010\u0010:\u001a\u0002072\u0006\u0010;\u001a\u00020<H\u0016J\u0012\u0010=\u001a\u0002072\b\u0010>\u001a\u0004\u0018\u00010?H\u0014J\u0010\u0010@\u001a\u0002072\u0006\u0010A\u001a\u00020BH\u0016J\b\u0010C\u001a\u000207H\u0014J\b\u0010D\u001a\u000207H\u0002R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R \u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\b\"\u0004\b\u001a\u0010\nR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.\u00a2\u0006\u0002\n\u0000R \u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0015\"\u0004\b!\u0010\u0017R\u000e\u0010\"\u001a\u00020#X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010$\u001a\u00020%X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\'\"\u0004\b(\u0010)R \u0010*\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0015\"\u0004\b,\u0010\u0017R \u0010-\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0015\"\u0004\b/\u0010\u0017R\u001a\u00100\u001a\u000201X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105\u00a8\u0006E"}, d2 = {"Lcom/taximobility/locationSearch/LocationSearchActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/taximobility/locationSearch/SetPlaceResult;", "Lcom/taximobility/bookingmodule/adapter/LocationListAdapter$DeleteClickListener;", "()V", "addrType", "", "getAddrType", "()Ljava/lang/String;", "setAddrType", "(Ljava/lang/String;)V", "binding", "Lcom/taximobility/databinding/ActivityLocationSearchBinding;", "getBinding", "()Lcom/taximobility/databinding/ActivityLocationSearchBinding;", "setBinding", "(Lcom/taximobility/databinding/ActivityLocationSearchBinding;)V", "favList", "Ljava/util/ArrayList;", "Lcom/taximobility/bookingmodule/LocationData;", "getFavList", "()Ljava/util/ArrayList;", "setFavList", "(Ljava/util/ArrayList;)V", "id", "getId", "setId", "isFourSquare", "", "listener", "Lcom/taximobility/locationSearch/OnLocationSearched;", "locationListData", "getLocationListData", "setLocationListData", "locationSearchFragment", "Lcom/taximobility/locationSearch/LocationSearchFragment;", "mAdapter", "Lcom/taximobility/bookingmodule/adapter/LocationListAdapter;", "getMAdapter", "()Lcom/taximobility/bookingmodule/adapter/LocationListAdapter;", "setMAdapter", "(Lcom/taximobility/bookingmodule/adapter/LocationListAdapter;)V", "popList", "getPopList", "setPopList", "recList", "getRecList", "setRecList", "viewModel", "Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;", "getViewModel", "()Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;", "setViewModel", "(Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;)V", "clearAllList", "", "deleteClick", "locationData", "itemClick", "pos", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onPlaceSelected", "placesDetail", "Lcom/taximobility/data/apiData/PlacesDetail;", "onResume", "updateListAdapter", "app_debug"})
public final class LocationSearchActivity extends androidx.appcompat.app.AppCompatActivity implements com.taximobility.locationSearch.SetPlaceResult, com.taximobility.bookingmodule.adapter.LocationListAdapter.DeleteClickListener {
    private boolean isFourSquare = false;
    private com.taximobility.locationSearch.LocationSearchFragment locationSearchFragment;
    private com.taximobility.locationSearch.OnLocationSearched listener;
    @org.jetbrains.annotations.NotNull()
    public com.taximobility.bookingmodule.adapter.LocationListAdapter mAdapter;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.taximobility.bookingmodule.LocationData> favList;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.taximobility.bookingmodule.LocationData> popList;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.taximobility.bookingmodule.LocationData> recList;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.taximobility.bookingmodule.LocationData> locationListData;
    @org.jetbrains.annotations.NotNull()
    public com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel;
    @org.jetbrains.annotations.NotNull()
    public com.taximobility.databinding.ActivityLocationSearchBinding binding;
    @org.jetbrains.annotations.NotNull()
    public java.lang.String id;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String addrType = "";
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.adapter.LocationListAdapter getMAdapter() {
        return null;
    }
    
    public final void setMAdapter(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.adapter.LocationListAdapter p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.taximobility.bookingmodule.LocationData> getFavList() {
        return null;
    }
    
    public final void setFavList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.taximobility.bookingmodule.LocationData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.taximobility.bookingmodule.LocationData> getPopList() {
        return null;
    }
    
    public final void setPopList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.taximobility.bookingmodule.LocationData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.taximobility.bookingmodule.LocationData> getRecList() {
        return null;
    }
    
    public final void setRecList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.taximobility.bookingmodule.LocationData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.taximobility.bookingmodule.LocationData> getLocationListData() {
        return null;
    }
    
    public final void setLocationListData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.taximobility.bookingmodule.LocationData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.BookTaxiHomeViewModel getViewModel() {
        return null;
    }
    
    public final void setViewModel(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.databinding.ActivityLocationSearchBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull()
    com.taximobility.databinding.ActivityLocationSearchBinding p0) {
    }
    
    @java.lang.Override()
    public void onPlaceSelected(@org.jetbrains.annotations.NotNull()
    com.taximobility.data.apiData.PlacesDetail placesDetail) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    public final void setId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAddrType() {
        return null;
    }
    
    public final void setAddrType(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void clearAllList() {
    }
    
    private final void updateListAdapter() {
    }
    
    @java.lang.Override()
    public void deleteClick(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.LocationData locationData) {
    }
    
    @java.lang.Override()
    public void itemClick(int pos) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    public LocationSearchActivity() {
        super();
    }
}