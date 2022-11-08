package com.taximobility.bookingmodule.Interface;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J(\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH&J\b\u0010\u000b\u001a\u00020\u0003H&J \u0010\f\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH&J\b\u0010\r\u001a\u00020\u0003H&\u00a8\u0006\u000e"}, d2 = {"Lcom/taximobility/bookingmodule/Interface/PickDropSetListener;", "", "dropListener", "", "dropSet", "latitude", "", "longtitue", "address", "", "focus", "kmRestrictListener", "pickUpSet", "pickupListener", "app_debug"})
public abstract interface PickDropSetListener {
    
    public abstract void pickUpSet(double latitude, double longtitue, @org.jetbrains.annotations.NotNull()
    java.lang.String address);
    
    public abstract void dropSet(double latitude, double longtitue, @org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.NotNull()
    java.lang.String focus);
    
    public abstract void pickupListener();
    
    public abstract void dropListener();
    
    public abstract void kmRestrictListener();
}