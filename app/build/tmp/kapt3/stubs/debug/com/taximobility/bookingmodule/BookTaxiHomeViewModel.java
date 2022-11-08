package com.taximobility.bookingmodule;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u00105\u001a\u0002062\u0006\u00107\u001a\u000208J\u000e\u00109\u001a\u0002062\u0006\u00107\u001a\u000208J\u000e\u0010:\u001a\u0002062\u0006\u00107\u001a\u000208J\u000e\u0010;\u001a\u0002062\u0006\u00107\u001a\u000208J\u000e\u0010<\u001a\u0002062\u0006\u00107\u001a\u000208J\u000e\u0010=\u001a\u0002062\u0006\u00107\u001a\u000208J\u0006\u0010\u0012\u001a\u000206J\u000e\u0010\u0015\u001a\u0002062\u0006\u0010>\u001a\u00020\rJ\u000e\u0010?\u001a\u0002062\u0006\u0010@\u001a\u00020!J\u000e\u0010A\u001a\u0002062\u0006\u00107\u001a\u000208J\u0012\u0010B\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0D0CJ\u0012\u0010F\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0D0CJ\u0012\u0010G\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0D0CJ\u000e\u0010H\u001a\u0002062\u0006\u0010@\u001a\u00020!J\u000e\u0010I\u001a\u0002062\u0006\u0010J\u001a\u00020KR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR \u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R \u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000f\"\u0004\b\u0017\u0010\u0011R \u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000f\"\u0004\b\u001b\u0010\u0011R \u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u000f\"\u0004\b\u001f\u0010\u0011R \u0010 \u001a\b\u0012\u0004\u0012\u00020!0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u000f\"\u0004\b#\u0010\u0011R \u0010$\u001a\b\u0012\u0004\u0012\u00020!0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u000f\"\u0004\b&\u0010\u0011R \u0010\'\u001a\b\u0012\u0004\u0012\u00020(0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u000f\"\u0004\b*\u0010\u0011R \u0010+\u001a\b\u0012\u0004\u0012\u00020!0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u000f\"\u0004\b-\u0010\u0011R \u0010.\u001a\b\u0012\u0004\u0012\u00020/0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u000f\"\u0004\b1\u0010\u0011R \u00102\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u000f\"\u0004\b4\u0010\u0011\u00a8\u0006L"}, d2 = {"Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "bookTaxiHomeRepository", "Lcom/taximobility/bookingmodule/BookTaxiHomeRepository;", "getBookTaxiHomeRepository", "()Lcom/taximobility/bookingmodule/BookTaxiHomeRepository;", "setBookTaxiHomeRepository", "(Lcom/taximobility/bookingmodule/BookTaxiHomeRepository;)V", "carLayClick", "Landroidx/lifecycle/MutableLiveData;", "", "getCarLayClick", "()Landroidx/lifecycle/MutableLiveData;", "setCarLayClick", "(Landroidx/lifecycle/MutableLiveData;)V", "cashCardClick", "getCashCardClick", "setCashCardClick", "cashCardEnable", "getCashCardEnable", "setCashCardEnable", "checkPromocodeResponse", "Lcom/taximobility/data/apiData/CheckPromoCodeData;", "getCheckPromocodeResponse", "setCheckPromocodeResponse", "deleteFavouriteData", "Lcom/taximobility/data/apiData/DeleteFavouriteData;", "getDeleteFavouriteData", "setDeleteFavouriteData", "modelDetailsRes", "", "getModelDetailsRes", "setModelDetailsRes", "modelPreferenceRes", "getModelPreferenceRes", "setModelPreferenceRes", "nearestResponse", "Lcom/taximobility/bookingmodule/Data/NearestDriverDatas;", "getNearestResponse", "setNearestResponse", "payType", "getPayType", "setPayType", "saveBookingRes", "Lcom/taximobility/bookingmodule/Data/SaveBookingResponse;", "getSaveBookingRes", "setSaveBookingRes", "skipDropClick", "getSkipDropClick", "setSkipDropClick", "callCheckPromoCode", "", "requestData", "Lorg/json/JSONObject;", "callGetPassengerInfoApi", "callModelDetailsApi", "callModelPrefrenceApi", "callNearestApiCall", "callSaveBookingApi", "enable", "deleteFavourite", "str", "deleteFavouriteApiCall", "getAllFavPopRecPlaces", "Landroidx/lifecycle/LiveData;", "", "Lcom/taximobility/bookingmodule/LocationData;", "getFavouritePlaces", "loadAllFavourite", "setPaymentType", "skipDropLocClick", "loc", "", "app_debug"})
public final class BookTaxiHomeViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private com.taximobility.bookingmodule.BookTaxiHomeRepository bookTaxiHomeRepository;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> skipDropClick;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<com.taximobility.bookingmodule.Data.NearestDriverDatas> nearestResponse;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<com.taximobility.bookingmodule.Data.SaveBookingResponse> saveBookingRes;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<com.taximobility.data.apiData.CheckPromoCodeData> checkPromocodeResponse;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<com.taximobility.data.apiData.DeleteFavouriteData> deleteFavouriteData;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> carLayClick;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> cashCardClick;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> payType;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> cashCardEnable;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> modelDetailsRes;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> modelPreferenceRes;
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.BookTaxiHomeRepository getBookTaxiHomeRepository() {
        return null;
    }
    
    public final void setBookTaxiHomeRepository(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> getSkipDropClick() {
        return null;
    }
    
    public final void setSkipDropClick(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.taximobility.bookingmodule.Data.NearestDriverDatas> getNearestResponse() {
        return null;
    }
    
    public final void setNearestResponse(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<com.taximobility.bookingmodule.Data.NearestDriverDatas> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.taximobility.bookingmodule.Data.SaveBookingResponse> getSaveBookingRes() {
        return null;
    }
    
    public final void setSaveBookingRes(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<com.taximobility.bookingmodule.Data.SaveBookingResponse> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.taximobility.data.apiData.CheckPromoCodeData> getCheckPromocodeResponse() {
        return null;
    }
    
    public final void setCheckPromocodeResponse(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<com.taximobility.data.apiData.CheckPromoCodeData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.taximobility.data.apiData.DeleteFavouriteData> getDeleteFavouriteData() {
        return null;
    }
    
    public final void setDeleteFavouriteData(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<com.taximobility.data.apiData.DeleteFavouriteData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> getCarLayClick() {
        return null;
    }
    
    public final void setCarLayClick(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> getCashCardClick() {
        return null;
    }
    
    public final void setCashCardClick(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getPayType() {
        return null;
    }
    
    public final void setPayType(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> getCashCardEnable() {
        return null;
    }
    
    public final void setCashCardEnable(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getModelDetailsRes() {
        return null;
    }
    
    public final void setModelDetailsRes(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getModelPreferenceRes() {
        return null;
    }
    
    public final void setModelPreferenceRes(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    public final void skipDropLocClick(int loc) {
    }
    
    public final void callNearestApiCall(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData) {
    }
    
    public final void callSaveBookingApi(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData) {
    }
    
    public final void callGetPassengerInfoApi(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData) {
    }
    
    public final void deleteFavouriteApiCall(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData) {
    }
    
    public final void callModelDetailsApi(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData) {
    }
    
    public final void callModelPrefrenceApi(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> getFavouritePlaces() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> getAllFavPopRecPlaces() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.taximobility.bookingmodule.LocationData>> loadAllFavourite() {
        return null;
    }
    
    public final void callCheckPromoCode(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData) {
    }
    
    public final void deleteFavourite(@org.jetbrains.annotations.NotNull()
    java.lang.String str) {
    }
    
    public final void cashCardClick() {
    }
    
    public final void setPaymentType(@org.jetbrains.annotations.NotNull()
    java.lang.String str) {
    }
    
    public final void cashCardEnable(boolean enable) {
    }
    
    public BookTaxiHomeViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}