package com.taximobility.pdview;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u001b\u001a\u00020\u001cJ\u0014\u0010\u001d\u001a\u00020\u001c2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/taximobility/pdview/PickupDropView;", "Landroid/widget/RelativeLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "customIconView", "Lcom/taximobility/pdview/CustomIconView;", "isCollapsed", "", "()Z", "setCollapsed", "(Z)V", "locationListView", "Lcom/taximobility/pdview/LocationListView;", "stopArray", "Ljava/util/ArrayList;", "Lcom/taximobility/locationSearch/PlacesData;", "getStopArray", "()Ljava/util/ArrayList;", "setStopArray", "(Ljava/util/ArrayList;)V", "upDownImageView", "Landroidx/appcompat/widget/AppCompatImageView;", "forceInvalidate", "", "setData", "app_debug"})
public final class PickupDropView extends android.widget.RelativeLayout {
    @org.jetbrains.annotations.NotNull()
    public java.util.ArrayList<com.taximobility.locationSearch.PlacesData> stopArray;
    private boolean isCollapsed = false;
    private final com.taximobility.pdview.LocationListView locationListView = null;
    private final com.taximobility.pdview.CustomIconView customIconView = null;
    private final androidx.appcompat.widget.AppCompatImageView upDownImageView = null;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.taximobility.locationSearch.PlacesData> getStopArray() {
        return null;
    }
    
    public final void setStopArray(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.taximobility.locationSearch.PlacesData> p0) {
    }
    
    public final boolean isCollapsed() {
        return false;
    }
    
    public final void setCollapsed(boolean p0) {
    }
    
    public final void forceInvalidate() {
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.taximobility.locationSearch.PlacesData> stopArray) {
    }
    
    public PickupDropView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    public PickupDropView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    public PickupDropView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
}