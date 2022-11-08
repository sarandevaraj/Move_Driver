package com.taximobility.driver;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0010\t\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\u0018\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\b\u0010\u001e\u001a\u00020\u001aH\u0002J\u0018\u0010\u001f\u001a\u00020\t2\u0006\u0010 \u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0007H\u0002J\u0018\u0010\"\u001a\u00020\t2\u0006\u0010 \u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0007H\u0002J\u0010\u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u0015H\u0002J\u0012\u0010%\u001a\u00020\u001a2\b\u0010&\u001a\u0004\u0018\u00010\u0007H\u0016J\u0010\u0010\'\u001a\u00020\u00072\u0006\u0010(\u001a\u00020)H\u0002J\u0012\u0010*\u001a\u00020\u001a2\b\u0010+\u001a\u0004\u0018\u00010\u0005H\u0014J\b\u0010,\u001a\u00020\u001aH\u0014J \u0010-\u001a\u00020\u001a2\u0006\u0010.\u001a\u00020\u00152\u0006\u0010/\u001a\u00020\u00152\u0006\u00100\u001a\u00020\u0015H\u0016J\b\u00101\u001a\u00020\u001aH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\r\u001a\b\u0018\u00010\u000eR\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lcom/taximobility/driver/DriverSettlementDetail;", "Lcom/taximobility/driver/DriverBaseActivity;", "Lcom/taximobility/driver/utils/DriverDatePicker_CardExpiry$DialogInterface;", "()V", "args", "Landroid/os/Bundle;", "datePic", "", "datePick", "", "editNameDialog", "Lcom/taximobility/driver/utils/DriverDatePicker_CardExpiry;", "fromDate", "infoObj", "Lcom/taximobility/driver/data/apiData/DriverSettlementReqData$Info;", "Lcom/taximobility/driver/data/apiData/DriverSettlementReqData;", "getInfoObj", "()Lcom/taximobility/driver/data/apiData/DriverSettlementReqData$Info;", "setInfoObj", "(Lcom/taximobility/driver/data/apiData/DriverSettlementReqData$Info;)V", "mDay", "", "mMonth", "mYear", "toDate", "callPaymentApi", "", "callRequestApi", "from", "to", "cancelLoading", "checkDateAfter", "date_End", "date_Start", "checkDateBefore", "checkDigit", "number", "failure", "inputText", "getDate", "timeStamp", "", "onCreate", "savedInstanceState", "onResume", "onSuccess", "monthOfYear", "year", "day", "showLoading", "app_debug"})
public final class DriverSettlementDetail extends com.taximobility.driver.DriverBaseActivity implements com.taximobility.driver.utils.DriverDatePicker_CardExpiry.DialogInterface {
    private com.taximobility.driver.utils.DriverDatePicker_CardExpiry editNameDialog;
    private int mMonth = 0;
    private int mDay = 0;
    private int mYear = 0;
    private boolean datePick = false;
    private java.lang.String fromDate = "";
    private java.lang.String toDate = "";
    private java.lang.String datePic = "";
    private final android.os.Bundle args = null;
    @org.jetbrains.annotations.Nullable()
    private com.taximobility.driver.data.apiData.DriverSettlementReqData.Info infoObj;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.Nullable()
    public final com.taximobility.driver.data.apiData.DriverSettlementReqData.Info getInfoObj() {
        return null;
    }
    
    public final void setInfoObj(@org.jetbrains.annotations.Nullable()
    com.taximobility.driver.data.apiData.DriverSettlementReqData.Info p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final void callPaymentApi() {
    }
    
    private final void showLoading() {
    }
    
    private final void cancelLoading() {
    }
    
    private final void callRequestApi(java.lang.String from, java.lang.String to) {
    }
    
    @java.lang.Override()
    public void onSuccess(int monthOfYear, int year, int day) {
    }
    
    @java.lang.Override()
    public void failure(@org.jetbrains.annotations.Nullable()
    java.lang.String inputText) {
    }
    
    private final java.lang.String checkDigit(int number) {
        return null;
    }
    
    private final boolean checkDateBefore(java.lang.String date_End, java.lang.String date_Start) {
        return false;
    }
    
    private final boolean checkDateAfter(java.lang.String date_End, java.lang.String date_Start) {
        return false;
    }
    
    private final java.lang.String getDate(long timeStamp) {
        return null;
    }
    
    public DriverSettlementDetail() {
        super();
    }
}