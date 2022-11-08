package com.taximobility.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&\u00a8\u0006\n"}, d2 = {"Lcom/taximobility/interfaces/NodeAuthListener;", "", "nodeAuthListener", "", "listener", "", "requestData", "Lorg/json/JSONObject;", "viewModel", "Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;", "app_debug"})
public abstract interface NodeAuthListener {
    
    public abstract void nodeAuthListener(boolean listener, @org.jetbrains.annotations.NotNull()
    org.json.JSONObject requestData, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel);
}