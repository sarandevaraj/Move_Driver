package com.taximobility.locationSearch;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\nH\u0002J\b\u0010\u0017\u001a\u00020\u0013H\u0002J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\nH\u0002J\b\u0010\u0019\u001a\u00020\u0013H\u0002J\"\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0014J\u0012\u0010 \u001a\u00020\u00132\b\u0010!\u001a\u0004\u0018\u00010\"H\u0014J\b\u0010#\u001a\u00020\u0013H\u0014J\b\u0010$\u001a\u00020\u0013H\u0014J\b\u0010%\u001a\u00020\u0013H\u0002J\b\u0010&\u001a\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R&\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n0\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u0006\'"}, d2 = {"Lcom/taximobility/locationSearch/AddStopActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "hintText", "", "isFromOnGoing", "", "isStopUpdate", "list", "Ljava/util/ArrayList;", "Lcom/taximobility/locationSearch/PlacesData;", "onlyPackage", "stopDataArray", "Ljava/util/HashMap;", "getStopDataArray", "()Ljava/util/HashMap;", "setStopDataArray", "(Ljava/util/HashMap;)V", "callUpdatesStopsApi", "", "clearLay", "createClearStopLay", "stopData", "createDynamicLay", "createStop", "hideLoading", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "onStart", "showLoading", "validateDone", "app_debug"})
public final class AddStopActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    private java.util.HashMap<java.lang.String, com.taximobility.locationSearch.PlacesData> stopDataArray;
    private java.util.ArrayList<com.taximobility.locationSearch.PlacesData> list;
    private boolean isFromOnGoing = false;
    private java.lang.String hintText = "";
    private java.lang.String onlyPackage;
    private boolean isStopUpdate = false;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.HashMap<java.lang.String, com.taximobility.locationSearch.PlacesData> getStopDataArray() {
        return null;
    }
    
    public final void setStopDataArray(@org.jetbrains.annotations.NotNull()
    java.util.HashMap<java.lang.String, com.taximobility.locationSearch.PlacesData> p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final void callUpdatesStopsApi() {
    }
    
    private final void showLoading() {
    }
    
    private final void hideLoading() {
    }
    
    /**
     * Function to clear existing view and create new views based on stopDataArray values
     * And to call validateDone() function to validate created views reached SLAB_SIZE
     */
    private final void createDynamicLay() {
    }
    
    /**
     * Function to remove existing views and draw lines (ie., pickup, drop connecting)
     * <p>
     * stopDataArray - ArrayList of PlacesData values to create connecting lines dynamically
     * <p>
     */
    private final void clearLay() {
    }
    
    /**
     * Method to create pick up, drop and stop(if available) views dynamically
     *
     * @param stopData     - Model class data for view (ie., pickup or drop or stop)
     */
    private final void createStop(com.taximobility.locationSearch.PlacesData stopData) {
    }
    
    /**
     * Function to add remove icon to clear added stop
     *
     * @param stopData - PlacesData object to check remove icon can be add or not based on ID
     */
    private final void createClearStopLay(com.taximobility.locationSearch.PlacesData stopData) {
    }
    
    /**
     * Function to enable done button based on stopDataArray size - Min two required (ie., pickup and drop)
     */
    private final void validateDone() {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    public AddStopActivity() {
        super();
    }
}