package com.taximobility.service;

import com.taximobility.data.apiData.ApiRequestData;
import com.taximobility.data.apiData.CompanyDomainResponse;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by developer on 8/31/16.
 * it consists of interface that required by the retrofit api to call appropriate data
 */

public interface CoreClient {

    String owner = "dGF4aV9hbGw=/";

    @GET("{owner}")
    Call<ResponseBody> coreDetailsg(@Header("Cache-Control") String cacheControl/*,@Header("Authorization") String companyKey*/, @Path(value = "owner", encoded = true) String owner, @Query("type") String url, @Query(value = "gt_lst_time", encoded = true) String encode, @Query(value = "dn", encoded = true) String auth_key);

    @GET("{owner}")
    Call<ResponseBody> coreDetailsg(@Header("Cache-Control") String cacheControl, @Path(value = "owner", encoded = true) String owner, @Query("type") String url, @Query("i") String i);

    @GET("{owner}")
    Call<ResponseBody> coreDetails(@Path(value = "owner", encoded = true) String owner, @Query("type") String url, @Query("lang") String lang);

    @POST("{owner}")
    Call<ResponseBody> updateUser(@Path(value = "owner", encoded = true) String owner, @Body RequestBody body, @Query("type") String url, @Query("lang") String lang, @Query("i") String i);

    @GET
    Call<ResponseBody> getWhole(@Header("Cache-Control") String cacheControl, @Url String url);

//    @POST("{owner}" + "?type=booking_list")
//    Call<UpcomingResponse> callData(@Path(value = "owner", encoded = true) String owner, @Body ApiRequestData.UpcomingRequest body, @Query("lang") String lang);
//
//    @POST("{owner}" + "?type=completed_journey_monthwise")
//    Call<PastBookingResponse> callData(@Path(value = "owner", encoded = true) String owner, @Body ApiRequestData.PastBookingRequest body, @Query("lang") String lang);
//
//    @POST("{owner}" + "?type=get_trip_detail")
//    Call<TripDetailResponse> callData(@Path(value = "owner", encoded = true) String owner, @Body ApiRequestData.getTripDetailRequest body, @Query("lang") String lang);
//
//    @GET("{owner}" + "?type=help_content")
//    Call<HelpResponse> helpContent(@Path(value = "owner", encoded = true) String owner, @Query("lang") String lang);
//
//    @POST("{owner}" + "?type=help_comment_update")
//    Call<StandardResponse> helpSubmit(@Path(value = "owner", encoded = true) String owner, @Body ApiRequestData.HelpSubmit body, @Query("lang") String lang);

    @POST("{owner}" + "?type=check_companydomain")
    Call<CompanyDomainResponse> callData(@Path(value = "owner", encoded = true) String owner, @Body ApiRequestData.BaseUrl body);

    @GET
    Call<JsonObject> getJsonbyWholeUrl(@Header("Cache-Control") String cacheControl, @Url String url);

    @POST("nearestdriver_list")
    Call<ResponseBody> nodeUpdate(@Body RequestBody body);

    @POST("get_driver_current_location")
    Call<ResponseBody> getDriverCurrentLocation(@Body RequestBody body);


    @POST
    Call<ResponseBody> urlCheck(@Url String url, @Body RequestBody body);

    @POST("auth")
    Call<ResponseBody> nodeAuth(@Body RequestBody body);

//    @POST
//    Call<ResponseBody> detail_infoCall(@Url String url, @Body DetailInfo body, @Query(value = "lang", encoded = true) String langCode);

    @POST("?type=update_stops")
    Call<ResponseBody> updateStops(@Body RequestBody body, @Query(value = "lang", encoded = true) String langCode);

    @GET("venues/suggestcompletion")
    Call<Object> requestExplore(@Url String url, @Query("v") String v, @Query("ll") String ll, @Query("query") String query, @Query("oauth_token") String oauth_token);

    @GET
    Call<ResponseBody> getPolylineDataWithWayPoint(@Url String url, @Query("origin") String origin, @Query("destination") String destination, @Query(value = "waypoints", encoded = true) String waypoints, @Query("key") String key);

//    @POST("?type=cancel_trip")
//    Call<CancelTripResponseData> callCancelTripApi(@Body CancelTripRequestData body);
//
//
//    @POST("?type=add_favourite")
//    Call<AddFavouriteData> addFavourite(@Body RequestBody body, @Query(value = "lang", encoded = true) String langCode);
//
//
//    @POST("nearestdriver_list")
//    Call<NearestDriverDatas> nodeUpdates(@Body RequestBody body, @Query(value = "lang", encoded = true)String langCode);
//
//    @POST("nearestDrivers")
//    Call<NearestDriverDatas> goLangUpdates(@Body RequestBody body, @Query(value = "lang", encoded = true)String langCode);
//
//    @POST("?type=savebooking")
//    Call<SaveBookingResponse> saveBookingData(@Body RequestBody body, @Query(value = "lang", encoded = true)String langCode);
//
//
//    @POST("?type=delete_favourite")
//    Call<DeleteFavouriteData> deleteFavourite(@Body RequestBody body, @Query(value = "lang", encoded = true) String langCode);
//
//
//    @POST("?type=check_valid_promocode")
//    Call<CheckPromoCodeData> checkProcode(@Body RequestBody body, @Query(value = "lang", encoded = true)String langCode);
//
//    @POST("?type=getPassengerInfo")
//    Call<PassengerInfoData> getPassengerInfo(@Body RequestBody body, @Query(value = "lang", encoded = true)String langCode);


    @POST("?type=getService_models")
    Call<ResponseBody> getServiceModels(@Body RequestBody body, @Query(value = "lang", encoded = true)String langCode);

    @POST("?type=get_preferences")
    Call<ResponseBody> getPreferences(@Body RequestBody body, @Query(value = "lang", encoded = true)String langCode);




}
