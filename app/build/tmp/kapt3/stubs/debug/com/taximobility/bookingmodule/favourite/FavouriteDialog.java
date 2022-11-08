package com.taximobility.bookingmodule.favourite;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0010H\u0002J\u0006\u0010#\u001a\u00020!J\b\u0010$\u001a\u00020!H\u0016J\u0010\u0010%\u001a\u00020!2\u0006\u0010&\u001a\u00020\'H\u0016J\u0012\u0010(\u001a\u00020\u00192\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J\b\u0010+\u001a\u00020!H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/taximobility/bookingmodule/favourite/FavouriteDialog;", "Landroidx/appcompat/app/AppCompatDialogFragment;", "Landroid/view/View$OnClickListener;", "Lcom/taximobility/bookingmodule/Interface/AddFavouriteListener;", "()V", "edtOthers", "Landroid/widget/EditText;", "fav", "Landroidx/lifecycle/MutableLiveData;", "", "getFav", "()Landroidx/lifecycle/MutableLiveData;", "favLat", "", "favLng", "favPlace", "", "favViewModel", "Lcom/taximobility/bookingmodule/favourite/FavouriteViewModel;", "layFavType1", "Landroid/widget/LinearLayout;", "layFavType2", "layFavType3", "layFavType4", "loadingDialog", "Landroid/app/Dialog;", "otherDetails", "placeType", "requestData", "Lorg/json/JSONObject;", "txtOkOthers", "Landroid/widget/TextView;", "callAddFavApi", "", "favType", "closeDialog", "loadFavouriteData", "onClick", "view", "Landroid/view/View;", "onCreateDialog", "savedInstanceState", "Landroid/os/Bundle;", "showDialog", "app_debug"})
public final class FavouriteDialog extends androidx.appcompat.app.AppCompatDialogFragment implements android.view.View.OnClickListener, com.taximobility.bookingmodule.Interface.AddFavouriteListener {
    private android.widget.LinearLayout layFavType1;
    private android.widget.LinearLayout layFavType2;
    private android.widget.LinearLayout layFavType3;
    private android.widget.LinearLayout layFavType4;
    private android.widget.LinearLayout otherDetails;
    private android.widget.TextView txtOkOthers;
    private android.widget.EditText edtOthers;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> fav = null;
    private com.taximobility.bookingmodule.favourite.FavouriteViewModel favViewModel;
    private final org.json.JSONObject requestData = null;
    private java.lang.String placeType = "";
    private java.lang.String favPlace = "";
    private double favLat = 0.0;
    private double favLng = 0.0;
    private android.app.Dialog loadingDialog;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getFav() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public android.app.Dialog onCreateDialog(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void loadFavouriteData() {
    }
    
    @java.lang.Override()
    public void onClick(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    private final void callAddFavApi(java.lang.String favType) {
    }
    
    private final void showDialog() {
    }
    
    public final void closeDialog() {
    }
    
    public FavouriteDialog() {
        super();
    }
}