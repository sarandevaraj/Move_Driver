package com.taximobility.features;

import java.lang.System;

/**
 * Created by developer on 5/3/16.
 * Used to calculate the approximate fare amount
 * if distance is less than minimun km then the min fare is set as fare amount
 * else if distance is greater than minimum km and less than below_above_km value fare amount=distance_travelled * below_above_km +base fare
 * else if distance is greater than minimum km and greater than below_above_km value fare amount=distance_travelled * below_above_km +base fare
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/taximobility/features/ApproximateCalculation;", "", "()V", "additional_fare_per_km", "", "aprrox_fare", "getAprrox_fare", "()D", "setAprrox_fare", "(D)V", "km_wise_fare", "", "min_fare", "min_km", "approxFare", "c", "Landroid/content/Context;", "dist", "time", "app_debug"})
public final class ApproximateCalculation {
    private static double aprrox_fare = 0.0;
    private static float min_km = 0.0F;
    private static float min_fare = 0.0F;
    private static float km_wise_fare = 0.0F;
    private static double additional_fare_per_km = 0.0;
    public static final com.taximobility.features.ApproximateCalculation INSTANCE = null;
    
    public final double getAprrox_fare() {
        return 0.0;
    }
    
    public final void setAprrox_fare(double p0) {
    }
    
    public final double approxFare(@org.jetbrains.annotations.NotNull()
    android.content.Context c, double dist, double time) {
        return 0.0;
    }
    
    private ApproximateCalculation() {
        super();
    }
}