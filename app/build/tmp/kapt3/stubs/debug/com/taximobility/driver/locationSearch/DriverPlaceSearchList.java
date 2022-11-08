package com.taximobility.driver.locationSearch;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0006\u001a\u00020\u00032\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\bH&\u00a8\u0006\t"}, d2 = {"Lcom/taximobility/driver/locationSearch/DriverPlaceSearchList;", "", "setPlaceDetail", "", "placeDetailDriver", "Lcom/taximobility/driver/locationSearch/DriverPlacesDetail;", "setPlaceList", "placeDetailResultDriver", "Ljava/util/ArrayList;", "app_debug"})
public abstract interface DriverPlaceSearchList {
    
    public abstract void setPlaceList(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<com.taximobility.driver.locationSearch.DriverPlacesDetail> placeDetailResultDriver);
    
    public abstract void setPlaceDetail(@org.jetbrains.annotations.NotNull()
    com.taximobility.driver.locationSearch.DriverPlacesDetail placeDetailDriver);
}