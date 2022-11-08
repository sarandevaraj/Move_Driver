package com.taximobility.bookingmodule.Data;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\bV\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u00d7\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u001aJ\t\u0010K\u001a\u00020\u0003H\u00c6\u0003J\t\u0010L\u001a\u00020\u0005H\u00c6\u0003J\t\u0010M\u001a\u00020\u0005H\u00c6\u0003J\t\u0010N\u001a\u00020\u000bH\u00c6\u0003J\t\u0010O\u001a\u00020\u0003H\u00c6\u0003J\t\u0010P\u001a\u00020\u000bH\u00c6\u0003J\t\u0010Q\u001a\u00020\u000bH\u00c6\u0003J\t\u0010R\u001a\u00020\u0005H\u00c6\u0003J\t\u0010S\u001a\u00020\u0003H\u00c6\u0003J\t\u0010T\u001a\u00020\u0003H\u00c6\u0003J\t\u0010U\u001a\u00020\u000bH\u00c6\u0003J\t\u0010V\u001a\u00020\u0005H\u00c6\u0003J\t\u0010W\u001a\u00020\u000bH\u00c6\u0003J\t\u0010X\u001a\u00020\u0005H\u00c6\u0003J\t\u0010Y\u001a\u00020\u0005H\u00c6\u0003J\t\u0010Z\u001a\u00020\u0005H\u00c6\u0003J\t\u0010[\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\\\u001a\u00020\u0005H\u00c6\u0003J\t\u0010]\u001a\u00020\u000bH\u00c6\u0003J\t\u0010^\u001a\u00020\u000bH\u00c6\u0003J\t\u0010_\u001a\u00020\u0005H\u00c6\u0003J\u00db\u0001\u0010`\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u000b2\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u000b2\b\b\u0002\u0010\u0013\u001a\u00020\u000b2\b\b\u0002\u0010\u0014\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u000b2\b\b\u0002\u0010\u0018\u001a\u00020\u000b2\b\b\u0002\u0010\u0019\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010a\u001a\u00020b2\b\u0010c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010d\u001a\u00020\u0003H\u00d6\u0001J\t\u0010e\u001a\u00020\u000bH\u00d6\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010\u0014\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010 \"\u0004\b$\u0010\"R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010 \"\u0004\b&\u0010\"R\u001a\u0010\u0012\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010(\"\u0004\b)\u0010*R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010 \"\u0004\b,\u0010\"R\u001a\u0010\u000e\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010 \"\u0004\b.\u0010\"R\u001a\u0010\u0016\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u001c\"\u0004\b0\u0010\u001eR\u001a\u0010\u0018\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010(\"\u0004\b2\u0010*R\u001a\u0010\u0013\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u0010(\"\u0004\b4\u0010*R\u001a\u0010\u0017\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u0010(\"\u0004\b6\u0010*R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u0010 \"\u0004\b8\u0010\"R\u001a\u0010\u000f\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b9\u0010 \"\u0004\b:\u0010\"R\u001a\u0010\t\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u0010 \"\u0004\b<\u0010\"R\u001a\u0010\u0010\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b=\u0010(\"\u0004\b>\u0010*R\u001a\u0010\u0011\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\u001c\"\u0004\b@\u0010\u001eR\u001a\u0010\r\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bA\u0010 \"\u0004\bB\u0010\"R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bC\u0010(\"\u0004\bD\u0010*R\u001a\u0010\f\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bE\u0010(\"\u0004\bF\u0010*R\u001a\u0010\u0015\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bG\u0010\u001c\"\u0004\bH\u0010\u001eR\u001a\u0010\u0019\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bI\u0010 \"\u0004\bJ\u0010\"\u00a8\u0006f"}, d2 = {"Lcom/taximobility/bookingmodule/Data/FareDetails;", "", "_id", "", "base_fare", "", "min_fare", "below_km", "above_km", "minutes_fare", "night_timing_from", "", "night_timing_to", "night_fare", "evening_fare", "min_km", "model_name", "model_size", "below_above_km", "km_wise_fare", "additional_fare_per_km", "nightfare_applicable", "eveningfare_applicable", "metric", "fare_calculation_type", "zone_zone_fare", "(IDDDDDLjava/lang/String;Ljava/lang/String;DDDLjava/lang/String;ILjava/lang/String;Ljava/lang/String;DIILjava/lang/String;Ljava/lang/String;D)V", "get_id", "()I", "set_id", "(I)V", "getAbove_km", "()D", "setAbove_km", "(D)V", "getAdditional_fare_per_km", "setAdditional_fare_per_km", "getBase_fare", "setBase_fare", "getBelow_above_km", "()Ljava/lang/String;", "setBelow_above_km", "(Ljava/lang/String;)V", "getBelow_km", "setBelow_km", "getEvening_fare", "setEvening_fare", "getEveningfare_applicable", "setEveningfare_applicable", "getFare_calculation_type", "setFare_calculation_type", "getKm_wise_fare", "setKm_wise_fare", "getMetric", "setMetric", "getMin_fare", "setMin_fare", "getMin_km", "setMin_km", "getMinutes_fare", "setMinutes_fare", "getModel_name", "setModel_name", "getModel_size", "setModel_size", "getNight_fare", "setNight_fare", "getNight_timing_from", "setNight_timing_from", "getNight_timing_to", "setNight_timing_to", "getNightfare_applicable", "setNightfare_applicable", "getZone_zone_fare", "setZone_zone_fare", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class FareDetails {
    private int _id;
    private double base_fare;
    private double min_fare;
    private double below_km;
    private double above_km;
    private double minutes_fare;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String night_timing_from;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String night_timing_to;
    private double night_fare;
    private double evening_fare;
    private double min_km;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String model_name;
    private int model_size;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String below_above_km;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String km_wise_fare;
    private double additional_fare_per_km;
    private int nightfare_applicable;
    private int eveningfare_applicable;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String metric;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String fare_calculation_type;
    private double zone_zone_fare;
    
    public final int get_id() {
        return 0;
    }
    
    public final void set_id(int p0) {
    }
    
    public final double getBase_fare() {
        return 0.0;
    }
    
    public final void setBase_fare(double p0) {
    }
    
    public final double getMin_fare() {
        return 0.0;
    }
    
    public final void setMin_fare(double p0) {
    }
    
    public final double getBelow_km() {
        return 0.0;
    }
    
    public final void setBelow_km(double p0) {
    }
    
    public final double getAbove_km() {
        return 0.0;
    }
    
    public final void setAbove_km(double p0) {
    }
    
    public final double getMinutes_fare() {
        return 0.0;
    }
    
    public final void setMinutes_fare(double p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNight_timing_from() {
        return null;
    }
    
    public final void setNight_timing_from(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNight_timing_to() {
        return null;
    }
    
    public final void setNight_timing_to(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final double getNight_fare() {
        return 0.0;
    }
    
    public final void setNight_fare(double p0) {
    }
    
    public final double getEvening_fare() {
        return 0.0;
    }
    
    public final void setEvening_fare(double p0) {
    }
    
    public final double getMin_km() {
        return 0.0;
    }
    
    public final void setMin_km(double p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getModel_name() {
        return null;
    }
    
    public final void setModel_name(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final int getModel_size() {
        return 0;
    }
    
    public final void setModel_size(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBelow_above_km() {
        return null;
    }
    
    public final void setBelow_above_km(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getKm_wise_fare() {
        return null;
    }
    
    public final void setKm_wise_fare(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final double getAdditional_fare_per_km() {
        return 0.0;
    }
    
    public final void setAdditional_fare_per_km(double p0) {
    }
    
    public final int getNightfare_applicable() {
        return 0;
    }
    
    public final void setNightfare_applicable(int p0) {
    }
    
    public final int getEveningfare_applicable() {
        return 0;
    }
    
    public final void setEveningfare_applicable(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMetric() {
        return null;
    }
    
    public final void setMetric(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFare_calculation_type() {
        return null;
    }
    
    public final void setFare_calculation_type(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final double getZone_zone_fare() {
        return 0.0;
    }
    
    public final void setZone_zone_fare(double p0) {
    }
    
    public FareDetails(int _id, double base_fare, double min_fare, double below_km, double above_km, double minutes_fare, @org.jetbrains.annotations.NotNull()
    java.lang.String night_timing_from, @org.jetbrains.annotations.NotNull()
    java.lang.String night_timing_to, double night_fare, double evening_fare, double min_km, @org.jetbrains.annotations.NotNull()
    java.lang.String model_name, int model_size, @org.jetbrains.annotations.NotNull()
    java.lang.String below_above_km, @org.jetbrains.annotations.NotNull()
    java.lang.String km_wise_fare, double additional_fare_per_km, int nightfare_applicable, int eveningfare_applicable, @org.jetbrains.annotations.NotNull()
    java.lang.String metric, @org.jetbrains.annotations.NotNull()
    java.lang.String fare_calculation_type, double zone_zone_fare) {
        super();
    }
    
    public FareDetails() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final double component2() {
        return 0.0;
    }
    
    public final double component3() {
        return 0.0;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final double component6() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    public final double component9() {
        return 0.0;
    }
    
    public final double component10() {
        return 0.0;
    }
    
    public final double component11() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    public final int component13() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    public final double component16() {
        return 0.0;
    }
    
    public final int component17() {
        return 0;
    }
    
    public final int component18() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component20() {
        return null;
    }
    
    public final double component21() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.Data.FareDetails copy(int _id, double base_fare, double min_fare, double below_km, double above_km, double minutes_fare, @org.jetbrains.annotations.NotNull()
    java.lang.String night_timing_from, @org.jetbrains.annotations.NotNull()
    java.lang.String night_timing_to, double night_fare, double evening_fare, double min_km, @org.jetbrains.annotations.NotNull()
    java.lang.String model_name, int model_size, @org.jetbrains.annotations.NotNull()
    java.lang.String below_above_km, @org.jetbrains.annotations.NotNull()
    java.lang.String km_wise_fare, double additional_fare_per_km, int nightfare_applicable, int eveningfare_applicable, @org.jetbrains.annotations.NotNull()
    java.lang.String metric, @org.jetbrains.annotations.NotNull()
    java.lang.String fare_calculation_type, double zone_zone_fare) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object p0) {
        return false;
    }
}