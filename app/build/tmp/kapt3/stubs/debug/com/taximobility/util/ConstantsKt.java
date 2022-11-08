package com.taximobility.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 2, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0018\n\u0002\u0010\b\n\u0002\b\u0015\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0011\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0014\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0015\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0016\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0017\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0018\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0019\u001a\u00020\u001aX\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u001b\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u001c\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u001d\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u001e\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u001f\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010 \u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010!\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\"\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010#\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010$\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010%\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010&\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\'\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010(\u001a\u00020\u001aX\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010)\u001a\u00020\u001aX\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010*\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010+\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010,\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010-\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010.\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"API_BASE", "", "BUNDLE_BOOKING_ADDRESS_TYPE", "BUNDLE_DROP_ADDRESS", "BUNDLE_PICKUP_DROP_ADDRESS", "BUNDLE_STOP_ADDRESS", "BUNDLE_STOP_ID", "BUNDLE_STOP_LAT", "BUNDLE_STOP_LNG", "BUNDLE_STOP_PID", "BUNDLE_STOP_TRIP_ID", "CREDIT_CARD", "DATA_OFF", "DATA_ON", "DEFAULT_CITY_NAME", "DropPlace", "FavouritePlace", "FavouritePlaceType", "GPS_OFF", "GPS_ON", "IS_BUISNESS_KEY", "LANG", "LOGOUT", "MODEL_DETAILS", "MODEL_DETAILS_UPDATE", "MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION", "", "PASS_API", "PASS_ID", "PASS_NAME", "PASS_PAYMENT_OPTION", "PASS_TRIP_ID", "PickupPlace", "PopularPlace", "PopularPlaceType", "REQUEST_TIME", "REQ_TRIP_ID", "RecentPlace", "RecentPlaceType", "SERVICE_DETAILS", "SLAB_DEVIATION_IN_METER", "SLAB_SIZE", "TYPE_GPS_ALERT", "TYPE_GPS_PERMISSION_ALERT", "Total_Request_Time", "alertType", "speed", "app_debug"})
public final class ConstantsKt {
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 420;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TYPE_GPS_ALERT = "gps_settings";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TYPE_GPS_PERMISSION_ALERT = "gps_permission";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String alertType = "GPS_SETTINGS";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String GPS_ON = "GPS ON";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String GPS_OFF = "GPS OFF";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATA_ON = "DATA ON";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATA_OFF = "DATA OFF";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BUNDLE_STOP_LAT = "bslat";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BUNDLE_STOP_LNG = "bslng";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BUNDLE_STOP_ID = "bsid";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BUNDLE_STOP_TRIP_ID = "btid";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BUNDLE_STOP_PID = "bspid";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BUNDLE_STOP_ADDRESS = "bsadd";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BUNDLE_PICKUP_DROP_ADDRESS = "pickUpDropAddress";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BUNDLE_DROP_ADDRESS = "dropAddress";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BUNDLE_BOOKING_ADDRESS_TYPE = "addressType";
    public static final int SLAB_SIZE = 3;
    public static final int SLAB_DEVIATION_IN_METER = 100;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REQ_TRIP_ID = "req_trip_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PASS_TRIP_ID = "Pass_Tripid";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REQUEST_TIME = "request_time";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CREDIT_CARD = "Credit_Card";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Total_Request_Time = "total_request_time";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LOGOUT = "Logout";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String API_BASE = "api_base";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PASS_ID = "Id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MODEL_DETAILS = "model_details";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SERVICE_DETAILS = "service_details";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MODEL_DETAILS_UPDATE = "model_details_update";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PASS_NAME = "ProfileName";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PASS_PAYMENT_OPTION = "passenger_payment_option";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String IS_BUISNESS_KEY = "isBUISNESSKEY";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_CITY_NAME = "default_city_name";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LANG = "Lang";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String speed = "45";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PickupPlace = "P";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DropPlace = "D";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FavouritePlace = "-1";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PopularPlace = "-2";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String RecentPlace = "-3";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FavouritePlaceType = "1";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PopularPlaceType = "2";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String RecentPlaceType = "3";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PASS_API = "pass_api";
}