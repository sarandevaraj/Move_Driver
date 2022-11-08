package com.taximobility.driver.errorLog;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\bH\u0007J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0004H\u0007J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u0004H\u0007\u00a8\u0006\u0011"}, d2 = {"Lcom/taximobility/driver/errorLog/DriverConverter;", "", "()V", "driverInfotoString", "", "driverInfoDriver", "Lcom/taximobility/driver/data/DriverModelDriverInfo;", "inputParamsToJson", "Lorg/json/JSONObject;", "inputData", "inputParamsToString", "latLngToString", "latLng", "Lcom/google/android/gms/maps/model/LatLng;", "stringToDriverInfo", "value", "stringToLatLng", "app_debug"})
public final class DriverConverter {
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.TypeConverter()
    public final java.lang.String driverInfotoString(@org.jetbrains.annotations.NotNull()
    com.taximobility.driver.data.DriverModelDriverInfo driverInfoDriver) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.TypeConverter()
    public final com.taximobility.driver.data.DriverModelDriverInfo stringToDriverInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.TypeConverter()
    public final java.lang.String latLngToString(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng latLng) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.TypeConverter()
    public final com.google.android.gms.maps.model.LatLng stringToLatLng(@org.jetbrains.annotations.NotNull()
    java.lang.String latLng) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.TypeConverter()
    public final org.json.JSONObject inputParamsToJson(@org.jetbrains.annotations.NotNull()
    java.lang.String inputData) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.TypeConverter()
    public final java.lang.String inputParamsToString(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject inputData) {
        return null;
    }
    
    public DriverConverter() {
        super();
    }
}