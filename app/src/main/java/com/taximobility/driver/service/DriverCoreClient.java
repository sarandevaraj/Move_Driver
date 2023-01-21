package com.taximobility.driver.service;

import com.taximobility.driver.data.apiData.DriverApiRequestData;
import com.taximobility.driver.data.apiData.DriverCompanyDomainResponse;
import com.taximobility.driver.data.apiData.DriverDetailInfo;
import com.taximobility.driver.data.apiData.DriverEndStreetPickupResponse;
import com.taximobility.driver.data.apiData.DriverGetTripDetailResponse;
import com.taximobility.driver.data.apiData.DriverSettlementHistoryData;
import com.taximobility.driver.data.apiData.DriverSettlementPaymentData;
import com.taximobility.driver.data.apiData.DriverSettlementReqData;
import com.taximobility.driver.data.apiData.DriverStreetCompleteResponse;
import com.taximobility.driver.data.apiData.DriverStreetPickUpResponse;
import com.taximobility.driver.data.apiData.DriverTripDetailResponse;
import com.taximobility.driver.data.apiData.DriverUpcomingResponse;
import com.taximobility.driver.earningchart.DriverEarningresponse;
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
 */

public interface DriverCoreClient {

    @GET("{owner}")
    Call<ResponseBody> coreDetails(@Path(value = "owner", encoded = true) String owner, @Header("Cache-Control") String cacheControl, @Query("type") String url, @Query(value = "gt_lst_time", encoded = true) String encode, @Query(value = "dn", encoded = true) String auth_key);

    @POST("{owner}")
    Call<ResponseBody> updateUser(@Path(value = "owner", encoded = true) String owner, @Body RequestBody body, @Query("type") String url, @Query(value = "lang", encoded = true) String lang);

    @POST("{owner}" + "?type=driver_booking_list")
    Call<DriverUpcomingResponse> callData(@Path(value = "owner", encoded = true) String owner, @Body DriverApiRequestData.UpcomingRequest body, @Query("lang") String lang);

    @POST("{owner}" + "?type=driver_booking_list")
    Call<DriverUpcomingResponse> callData_(@Path(value = "owner", encoded = true) String owner, @Body DriverApiRequestData.UpcomingRequest body, @Query("lang") String lang);

    @POST("{owner}" + "?type=get_trip_detail")
    Call<DriverTripDetailResponse> callData(@Path(value = "owner", encoded = true) String owner, @Body DriverApiRequestData.getTripDetailRequest body, @Query("lang") String lang);

    @GET
    Call<ResponseBody> getWhole(@Header("Cache-Control") String cacheControl, @Url String url);

    /**
     * method to start Street trip
     *
     * @param body StreetPickRequest class object
     * @param lang Language
     * @return returns api result on call back
     */
    @POST("{owner}" + "?type=driver_start_trip")
    Call<DriverStreetPickUpResponse> startStreetTrip(@Path(value = "owner", encoded = true) String owner, @Body DriverApiRequestData.StreetPickRequest body, @Query("lang") String lang);

    @POST("{owner}" + "?type=street_pickup_end_trip")
    Call<DriverEndStreetPickupResponse> endStreetTrip(@Path(value = "owner", encoded = true) String owner, @Body DriverApiRequestData.EndStreetPickup body, @Query("lang") String lang);

    @POST("{owner}" + "?type=get_trip_detail")
    Call<DriverGetTripDetailResponse> getTripDetail(@Path(value = "owner", encoded = true) String owner, @Body DriverApiRequestData.TripDetailRequest body, @Query("lang") String lang);

    @POST("{owner}" + "?type=street_pickup_tripfare_update")
    Call<DriverStreetCompleteResponse> completeStreetPickUpdate(@Path(value = "owner", encoded = true) String owner, @Body DriverApiRequestData.StreetPickComplete body, @Query("lang") String lang);

    @POST("{owner}" + "?type=driver_earnings")
    Call<DriverEarningresponse> callData(@Path(value = "owner", encoded = true) String owner, @Body DriverApiRequestData.Earnings body);

    @POST("{owner}" + "?type=check_companydomain")
    Call<DriverCompanyDomainResponse> callData(@Path(value = "owner", encoded = true) String owner, @Body DriverApiRequestData.BaseUrl body);

    @GET
    Call<JsonObject> getJsonbyWholeUrl(@Header("Cache-Control") String cacheControl, @Url String url);

    @POST("?type=settlement_history")
    Call<DriverSettlementHistoryData> settlement_historyCall(@Body DriverApiRequestData.SettlementHistory body, @Query(value = "lang", encoded = true) String langCode);

    @POST("?type=driver_request_settlement")
    Call<DriverSettlementPaymentData> settlement_paymentCall(@Body DriverApiRequestData.PaymentReq body, @Query(value = "lang", encoded = true) String langCode);

    @POST("?type=settlement_request_amount")
    Call<DriverSettlementReqData> settlement_reqCall(@Body DriverApiRequestData.SettlementReq body, @Query(value = "lang", encoded = true) String langCode);

    @POST("driver_location_history_update")
    Call<ResponseBody> nodeUpdate(@Body RequestBody body, @Query(value = "h", encoded = true) String hours, @Query(value = "t", encoded = true) String timeUpdate);

    @POST("locationUpdate")
    Call<ResponseBody> goLangUpdate(@Body RequestBody body, @Query(value = "h", encoded = true) String hours, @Query(value = "t", encoded = true) String timeUpdate);

    @POST("auth")
    Call<ResponseBody> nodeAuth(@Body RequestBody body);

    @POST
    Call<ResponseBody> urlCheck(@Url String url, @Body RequestBody body);

    @POST
    Call<ResponseBody> detail_infoCall(@Url String url, @Body DriverDetailInfo body, @Query(value = "lang", encoded = true) String langCode);

    @POST("?type=error_logs")
    Call<ResponseBody> errorLogUpdate(@Body RequestBody body);

    @GET
    Call<ResponseBody> getPolylineDataWithWayPoint(@Url String url, @Query("origin") String origin, @Query("destination") String destination, @Query(value = "waypoints", encoded = true) String waypoints, @Query("key") String key);

    @GET
    Call<Object> requestExplore(@Url String url, @Query("v") String v, @Query("ll") String ll, @Query("query") String query, @Query("oauth_token") String oauth_token);

}
