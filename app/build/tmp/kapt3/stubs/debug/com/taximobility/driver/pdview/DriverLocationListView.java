package com.taximobility.driver.pdview;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u0002B%\b\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00020\u000fH\u0016J\u0010\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\fH\u0002J(\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010 \u001a\u00020\b2\u0006\u0010!\u001a\u00020\bH\u0014J.\u0010\"\u001a\u00020\u001a2\u0016\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\r2\u0006\u0010#\u001a\u00020\u00112\u0006\u0010$\u001a\u00020\u0011R\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015\u00a8\u0006%"}, d2 = {"Lcom/taximobility/driver/pdview/DriverLocationListView;", "Landroid/widget/LinearLayout;", "Lcom/taximobility/driver/pdview/DriverCollapseInterface;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "driverStopArray", "Ljava/util/ArrayList;", "Lcom/taximobility/driver/route/DriverStopData;", "Lkotlin/collections/ArrayList;", "isCollapsed", "", "lang", "", "getLang", "()Ljava/lang/String;", "setLang", "(Ljava/lang/String;)V", "type", "getType", "setType", "collapsed", "", "createStop", "driverStopData", "onSizeChanged", "w", "h", "oldw", "oldh", "setData", "types", "language", "app_debug"})
public final class DriverLocationListView extends android.widget.LinearLayout implements com.taximobility.driver.pdview.DriverCollapseInterface {
    private boolean isCollapsed = false;
    private java.util.ArrayList<com.taximobility.driver.route.DriverStopData> driverStopArray;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String type = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String lang = "";
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public void collapsed(boolean collapsed) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getType() {
        return null;
    }
    
    public final void setType(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLang() {
        return null;
    }
    
    public final void setLang(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.taximobility.driver.route.DriverStopData> driverStopArray, @org.jetbrains.annotations.NotNull()
    java.lang.String types, @org.jetbrains.annotations.NotNull()
    java.lang.String language) {
    }
    
    @java.lang.Override()
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }
    
    private final void createStop(com.taximobility.driver.route.DriverStopData driverStopData) {
    }
    
    public DriverLocationListView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    public DriverLocationListView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    public DriverLocationListView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
}