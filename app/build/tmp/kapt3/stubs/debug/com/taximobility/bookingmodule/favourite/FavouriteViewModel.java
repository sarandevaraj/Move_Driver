package com.taximobility.bookingmodule.favourite;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0007R \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/taximobility/bookingmodule/favourite/FavouriteViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "favData", "Landroidx/lifecycle/MutableLiveData;", "Lcom/taximobility/data/apiData/AddFavouriteData;", "getFavData", "()Landroidx/lifecycle/MutableLiveData;", "setFavData", "(Landroidx/lifecycle/MutableLiveData;)V", "favRepository", "Lcom/taximobility/bookingmodule/favourite/FavouriteRepository;", "callAddFavApiCall", "", "requestData", "Lorg/json/JSONObject;", "setFavDataValue", "data", "app_debug"})
public final class FavouriteViewModel extends androidx.lifecycle.AndroidViewModel {
    private com.taximobility.bookingmodule.favourite.FavouriteRepository favRepository;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<com.taximobility.data.apiData.AddFavouriteData> favData;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.taximobility.data.apiData.AddFavouriteData> getFavData() {
        return null;
    }
    
    public final void setFavData(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<com.taximobility.data.apiData.AddFavouriteData> p0) {
    }
    
    public final void callAddFavApiCall(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData) {
    }
    
    public final void setFavDataValue(@org.jetbrains.annotations.NotNull()
    com.taximobility.data.apiData.AddFavouriteData data) {
    }
    
    public FavouriteViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}