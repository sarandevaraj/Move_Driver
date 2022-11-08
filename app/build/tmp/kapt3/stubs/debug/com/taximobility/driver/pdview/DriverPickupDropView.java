package com.taximobility.driver.pdview;

import java.lang.System;

@android.annotation.SuppressLint(value = {"ResourceType"})
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u001b\u001a\u00020\u001cJ\u0018\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0007H\u0014J.\u0010!\u001a\u00020\u001c2\u0016\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00020\r`\u000e2\u0006\u0010\"\u001a\u00020\u00122\u0006\u0010#\u001a\u00020\u0012R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00020\r`\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/taximobility/driver/pdview/DriverPickupDropView;", "Landroid/widget/RelativeLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "customIconView", "Lcom/taximobility/driver/pdview/DriverCustomIconView;", "driverStopArray", "Ljava/util/ArrayList;", "Lcom/taximobility/driver/route/DriverStopData;", "Lkotlin/collections/ArrayList;", "isCollapsed", "", "lang", "", "getLang", "()Ljava/lang/String;", "setLang", "(Ljava/lang/String;)V", "locationListView", "Lcom/taximobility/driver/pdview/DriverLocationListView;", "upDownImageView", "Landroidx/appcompat/widget/AppCompatImageView;", "forceInvalidate", "", "onVisibilityChanged", "changedView", "Landroid/view/View;", "visibility", "setData", "type", "language", "app_debug"})
public final class DriverPickupDropView extends android.widget.RelativeLayout {
    private java.util.ArrayList<com.taximobility.driver.route.DriverStopData> driverStopArray;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String lang = "";
    private boolean isCollapsed = false;
    private final com.taximobility.driver.pdview.DriverLocationListView locationListView = null;
    private final com.taximobility.driver.pdview.DriverCustomIconView customIconView = null;
    private final androidx.appcompat.widget.AppCompatImageView upDownImageView = null;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLang() {
        return null;
    }
    
    public final void setLang(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @java.lang.Override()
    protected void onVisibilityChanged(@org.jetbrains.annotations.NotNull()
    android.view.View changedView, int visibility) {
    }
    
    public final void forceInvalidate() {
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.taximobility.driver.route.DriverStopData> driverStopArray, @org.jetbrains.annotations.NotNull()
    java.lang.String type, @org.jetbrains.annotations.NotNull()
    java.lang.String language) {
    }
    
    public DriverPickupDropView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    public DriverPickupDropView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    public DriverPickupDropView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
}