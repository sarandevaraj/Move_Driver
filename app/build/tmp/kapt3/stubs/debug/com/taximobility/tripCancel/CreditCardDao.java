package com.taximobility.tripCancel;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\'J\u0016\u0010\u0004\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\'J!\u0010\b\u001a\u00020\u00032\u0012\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\t\"\u00020\u0007H\'\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\fH\'\u00a8\u0006\r"}, d2 = {"Lcom/taximobility/tripCancel/CreditCardDao;", "", "deleteAllCards", "", "insertAllCreditCards", "models", "", "Lcom/taximobility/tripCancel/CreditCardData;", "insertCreditCard", "", "([Lcom/taximobility/tripCancel/CreditCardData;)V", "loadAllCards", "Landroidx/lifecycle/LiveData;", "app_debug"})
public abstract interface CreditCardDao {
    
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract void insertCreditCard(@org.jetbrains.annotations.NotNull()
    com.taximobility.tripCancel.CreditCardData... models);
    
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract void insertAllCreditCards(@org.jetbrains.annotations.NotNull()
    java.util.List<com.taximobility.tripCancel.CreditCardData> models);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * From CardDetails")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.taximobility.tripCancel.CreditCardData>> loadAllCards();
    
    @androidx.room.Query(value = "DELETE From CardDetails")
    public abstract void deleteAllCards();
}