package com.taximobility.driver.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/taximobility/driver/interfaces/DriverDistanceUpdate;", "", "onDistanceUpdate", "", "distance", "", "s", "", "(Ljava/lang/Double;Ljava/lang/String;)V", "app_debug"})
public abstract interface DriverDistanceUpdate {
    
    public abstract void onDistanceUpdate(@org.jetbrains.annotations.Nullable()
    java.lang.Double distance, @org.jetbrains.annotations.NotNull()
    java.lang.String s);
}