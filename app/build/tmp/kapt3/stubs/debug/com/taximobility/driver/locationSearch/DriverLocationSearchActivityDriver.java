package com.taximobility.driver.locationSearch;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\rH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/taximobility/driver/locationSearch/DriverLocationSearchActivityDriver;", "Lcom/taximobility/driver/DriverBaseActivity;", "Lcom/taximobility/driver/locationSearch/DriverSetPlaceResult;", "()V", "driverLocationSearchFragment", "Lcom/taximobility/driver/locationSearch/DriverLocationSearchFragmentDriverDriver;", "edLocation", "Landroid/widget/EditText;", "isFourSquare", "", "listener", "Lcom/taximobility/driver/locationSearch/DriverOnLocationSearched;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onPlaceSelected", "driverPlacesDetail", "Lcom/taximobility/driver/locationSearch/DriverPlacesDetail;", "onResume", "app_debug"})
public final class DriverLocationSearchActivityDriver extends com.taximobility.driver.DriverBaseActivity implements com.taximobility.driver.locationSearch.DriverSetPlaceResult {
    private boolean isFourSquare = false;
    private com.taximobility.driver.locationSearch.DriverLocationSearchFragmentDriverDriver driverLocationSearchFragment;
    private com.taximobility.driver.locationSearch.DriverOnLocationSearched listener;
    private android.widget.EditText edLocation;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public void onPlaceSelected(@org.jetbrains.annotations.NotNull()
    com.taximobility.driver.locationSearch.DriverPlacesDetail driverPlacesDetail) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    public DriverLocationSearchActivityDriver() {
        super();
    }
}