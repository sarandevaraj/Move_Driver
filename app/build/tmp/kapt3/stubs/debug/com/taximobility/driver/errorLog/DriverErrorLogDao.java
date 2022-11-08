package com.taximobility.driver.errorLog;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\'J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005H\'J!\u0010\r\u001a\u00020\u00032\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u000f\"\u00020\bH\'\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\nH\'\u00a8\u0006\u0014"}, d2 = {"Lcom/taximobility/driver/errorLog/DriverErrorLogDao;", "", "deleteAllApiErrorLogs", "", "date", "", "getAllApiErrorLogs", "", "Lcom/taximobility/driver/errorLog/DriverApiErrorModel;", "getCount", "", "error", "currTime", "insertApiErrorLog", "modelDrivers", "", "([Lcom/taximobility/driver/errorLog/DriverApiErrorModel;)V", "updateSendStatus", "status", "id", "app_debug"})
public abstract interface DriverErrorLogDao {
    
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.IGNORE)
    public abstract void insertApiErrorLog(@org.jetbrains.annotations.NotNull()
    com.taximobility.driver.errorLog.DriverApiErrorModel... modelDrivers);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM apiErrorModel WHERE sendStatus = 0 LIMIT 1")
    public abstract java.util.List<com.taximobility.driver.errorLog.DriverApiErrorModel> getAllApiErrorLogs();
    
    @androidx.room.Query(value = "DELETE FROM apiErrorModel WHERE timeStamp < :date ")
    public abstract void deleteAllApiErrorLogs(@org.jetbrains.annotations.NotNull()
    java.lang.String date);
    
    @androidx.room.Query(value = "SELECT COUNT(timeStamp) FROM apiErrorModel WHERE error = :error and timeStamp = :currTime")
    public abstract int getCount(@org.jetbrains.annotations.NotNull()
    java.lang.String error, @org.jetbrains.annotations.NotNull()
    java.lang.String currTime);
    
    @androidx.room.Query(value = "UPDATE apiErrorModel set sendStatus = :status WHERE ids = :id")
    public abstract void updateSendStatus(int status, int id);
}