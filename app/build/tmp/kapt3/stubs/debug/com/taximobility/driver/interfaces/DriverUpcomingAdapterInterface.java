package com.taximobility.driver.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\u0010\u0010\u0004\u001a\f\u0012\b\u0012\u00060\u0006R\u00020\u00070\u00052\u0006\u0010\b\u001a\u00020\tH&\u00a8\u0006\n"}, d2 = {"Lcom/taximobility/driver/interfaces/DriverUpcomingAdapterInterface;", "", "updateUpcomingAdapter", "", "data", "", "Lcom/taximobility/driver/data/apiData/DriverUpcomingResponse$PastBooking;", "Lcom/taximobility/driver/data/apiData/DriverUpcomingResponse;", "clickedPosition", "", "app_debug"})
public abstract interface DriverUpcomingAdapterInterface {
    
    public abstract void updateUpcomingAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.taximobility.driver.data.apiData.DriverUpcomingResponse.PastBooking> data, int clickedPosition);
}