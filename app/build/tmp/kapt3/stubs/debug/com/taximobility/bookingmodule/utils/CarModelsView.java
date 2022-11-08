package com.taximobility.bookingmodule.utils;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0002\b\u0007\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0016\u0010\u001e\u001a\u00020\u001c2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0002J\u0018\u0010\u001f\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0003H\u0002J\u0006\u0010\"\u001a\u00020\u0016J\u0006\u0010#\u001a\u00020\u0016J\u0015\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&H\u0000\u00a2\u0006\u0002\b\'J\u0015\u0010(\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020&H\u0000\u00a2\u0006\u0002\b*J\u0016\u0010+\u001a\u00020\u001c2\u0006\u0010,\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/taximobility/bookingmodule/utils/CarModelsView;", "Landroid/widget/HorizontalScrollView;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "currentDateTimeString", "", "displayMetrics", "Landroid/util/DisplayMetrics;", "mLinearLayout", "Landroid/widget/LinearLayout;", "modelArray", "Ljava/util/ArrayList;", "Lcom/taximobility/bookingmodule/Data/ModelData;", "previousClickedTime", "previousSelectedModel", "selectedCarModel", "selectedModelId", "", "selectedModelSize", "selectedPosition", "viewModel", "Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;", "carLayClick", "", "position", "createViews", "dpToPx", "dp", "con", "getSelectedCarModel", "getSelectedCarModelSize", "setApproximateFare", "approximateFare", "", "setApproximateFare$app_debug", "setApproximateTime", "approximateTime", "setApproximateTime$app_debug", "setCarModelArray", "array", "app_debug"})
public final class CarModelsView extends android.widget.HorizontalScrollView {
    private java.util.ArrayList<com.taximobility.bookingmodule.Data.ModelData> modelArray;
    private int selectedPosition = 0;
    private java.lang.String selectedModelId = "";
    private final android.widget.LinearLayout mLinearLayout = null;
    private final android.util.DisplayMetrics displayMetrics = null;
    private com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel;
    private long currentDateTimeString = 0L;
    private long previousClickedTime = 0L;
    private int previousSelectedModel = 0;
    private int selectedCarModel = 0;
    private java.lang.String selectedModelSize = "";
    private java.util.HashMap _$_findViewCache;
    
    public final void setCarModelArray(@org.jetbrains.annotations.NotNull()
    java.lang.String array, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel) {
    }
    
    private final void createViews(java.util.ArrayList<com.taximobility.bookingmodule.Data.ModelData> modelArray) {
    }
    
    private final void carLayClick(int position) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSelectedCarModelSize() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSelectedCarModel() {
        return null;
    }
    
    public final void setApproximateFare$app_debug(double approximateFare) {
    }
    
    public final void setApproximateTime$app_debug(double approximateTime) {
    }
    
    private final int dpToPx(int dp, android.content.Context con) {
        return 0;
    }
    
    public CarModelsView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    public CarModelsView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    public CarModelsView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
}