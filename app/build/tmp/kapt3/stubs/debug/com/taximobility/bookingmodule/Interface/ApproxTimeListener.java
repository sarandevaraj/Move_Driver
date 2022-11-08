package com.taximobility.bookingmodule.Interface;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H&J\u0018\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH&J(\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000bH&\u00a8\u0006\u000e"}, d2 = {"Lcom/taximobility/bookingmodule/Interface/ApproxTimeListener;", "", "approxTimeBookLater", "", "type", "", "time", "", "dist", "approxTimeETAType", "eTA", "", "approxTimeFareType", "approxFare", "app_debug"})
public abstract interface ApproxTimeListener {
    
    /**
     * private final int DISTANCE_TYPE_FOR_ETA = 1;
     *     private final int DISTANCE_TYPE_FOR_BOOK_LATER = 2;
     *      private final int DISTANCE_TYPE_FOR_FARE = 3;
     */
    public abstract void approxTimeFareType(int type, @org.jetbrains.annotations.NotNull()
    java.lang.String time, @org.jetbrains.annotations.NotNull()
    java.lang.String dist, double approxFare);
    
    public abstract void approxTimeETAType(int type, double eTA);
    
    public abstract void approxTimeBookLater(int type, @org.jetbrains.annotations.NotNull()
    java.lang.String time, @org.jetbrains.annotations.NotNull()
    java.lang.String dist);
}