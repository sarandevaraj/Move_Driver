package com.taximobility.driver.errorLog;

import java.lang.System;

@androidx.room.Entity(tableName = "apiErrorModel")
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001BG\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\tH\u00c6\u0003J\t\u0010 \u001a\u00020\u000bH\u00c6\u0003J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003JY\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00d6\u0001J\t\u0010(\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010\u00a8\u0006)"}, d2 = {"Lcom/taximobility/driver/errorLog/DriverApiErrorModel;", "", "ids", "", "timeStamp", "", "apiCase", "error", "driverDataDriver", "Lcom/taximobility/driver/data/DriverModelDriverInfo;", "inputParams", "Lorg/json/JSONObject;", "classContext", "sendStatus", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/taximobility/driver/data/DriverModelDriverInfo;Lorg/json/JSONObject;Ljava/lang/String;I)V", "getApiCase", "()Ljava/lang/String;", "getClassContext", "getDriverDataDriver", "()Lcom/taximobility/driver/data/DriverModelDriverInfo;", "getError", "getIds", "()I", "getInputParams", "()Lorg/json/JSONObject;", "getSendStatus", "getTimeStamp", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class DriverApiErrorModel {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final int ids = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String timeStamp = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String apiCase = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String error = null;
    @org.jetbrains.annotations.NotNull()
    private final com.taximobility.driver.data.DriverModelDriverInfo driverDataDriver = null;
    @org.jetbrains.annotations.NotNull()
    private final org.json.JSONObject inputParams = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String classContext = null;
    private final int sendStatus = 0;
    
    public final int getIds() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimeStamp() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getApiCase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getError() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.driver.data.DriverModelDriverInfo getDriverDataDriver() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.json.JSONObject getInputParams() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getClassContext() {
        return null;
    }
    
    public final int getSendStatus() {
        return 0;
    }
    
    public DriverApiErrorModel(int ids, @org.jetbrains.annotations.NotNull()
    java.lang.String timeStamp, @org.jetbrains.annotations.NotNull()
    java.lang.String apiCase, @org.jetbrains.annotations.NotNull()
    java.lang.String error, @org.jetbrains.annotations.NotNull()
    com.taximobility.driver.data.DriverModelDriverInfo driverDataDriver, @org.jetbrains.annotations.NotNull()
    org.json.JSONObject inputParams, @org.jetbrains.annotations.NotNull()
    java.lang.String classContext, int sendStatus) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.driver.data.DriverModelDriverInfo component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.json.JSONObject component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    public final int component8() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.driver.errorLog.DriverApiErrorModel copy(int ids, @org.jetbrains.annotations.NotNull()
    java.lang.String timeStamp, @org.jetbrains.annotations.NotNull()
    java.lang.String apiCase, @org.jetbrains.annotations.NotNull()
    java.lang.String error, @org.jetbrains.annotations.NotNull()
    com.taximobility.driver.data.DriverModelDriverInfo driverDataDriver, @org.jetbrains.annotations.NotNull()
    org.json.JSONObject inputParams, @org.jetbrains.annotations.NotNull()
    java.lang.String classContext, int sendStatus) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object p0) {
        return false;
    }
}