package com.taximobility.util;

import android.content.Context;
import android.location.Geocoder;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.taximobility.features.CToast;
import com.taximobility.interfaces.GetAddress;
import com.taximobility.roomDB.GeocoderModel;
import com.taximobility.roomDB.MapLoggerRepository;
import com.taximobility.service.CoreClient;
import com.taximobility.service.RetrofitCallbackClass;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddressFromLatLng extends AsyncTask<String, String, GeocoderModel> {
    private final MapLoggerRepository mRepository;
    public Context mContext;
    LatLng mPosition;
    String Address = "";
    Geocoder geocoder;
    List<android.location.Address> addresses = null;
    private double latitude;
    private double longitude;

    private GetAddress getAddress_listener;

    public AddressFromLatLng(Context context, LatLng position, GetAddress getAddress_listener) {

        this.mContext = context;
        mPosition = position;
        latitude = mPosition.latitude;
        longitude = mPosition.longitude;
        this.getAddress_listener = getAddress_listener;
        mRepository = new MapLoggerRepository(mContext);
        geocoder = new Geocoder(context, Locale.getDefault());
    }


    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        Systems.out.println("map_box_fir" + Thread.currentThread().getId());
    }

    @Override
    protected GeocoderModel doInBackground(String... params) {
        // TODO Auto-generated method stub
        boolean isStrictMapBox = false;
        if (params != null && params.length > 0)
            isStrictMapBox = params[0].equals("mapbox");
        GeocoderModel model = null;
        model = mRepository.getGeocodeModel("" + latitude + "," + longitude, TaxiUtil.isGoogleGeocode);


        if (model != null) {
            Systems.out.println("haiiiiiiii geocodeeee already available: " + model.result);
            return model;

        } else {
            Systems.out.println("haiiiiiiii geocodeeee new value " + isStrictMapBox);

            if (Geocoder.isPresent()) {
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 3);
                    if (addresses != null) {
                        if (addresses.size() == 0) {
                            convertLatLngtoAddressApi(latitude, longitude);
                        } else {
                            for (int i = 0; i < addresses.size(); i++) {
                                Address += addresses.get(0).getAddressLine(i) + ", ";
                            }
                            if (Address.length() > 0) {
                                Address = Address.substring(0, Address.length() - 2);

                                return (saveGeocodeLog("" + latitude + "," + longitude, Address, TaxiUtil.isGoogleGeocode));
                            }
                        }
                    } else {
                        convertLatLngtoAddressApi(latitude, longitude);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (NetworkStatus.isOnline(mContext)) {
                        Systems.out.println("haiiiiiiii geocodeeee IOException--1");
                        Systems.out.println("map_box_sec" + Thread.currentThread().getId());
                        convertLatLngtoAddressApi(latitude, longitude);
                    }
                }
            } else {
                if (NetworkStatus.isOnline(mContext)) {
                    Systems.out.println("haiiiiiiii geocodeeee Geocoder not available");
                    Systems.out.println("map_box_thr" + Thread.currentThread().getId());
                    convertLatLngtoAddressApi(latitude, longitude);
                }
            }
        }

        return null;
    }


    @Override
    protected void onPostExecute(GeocoderModel result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        Systems.out.println("nnaa--GetAddressFromLatLng--6");
        if (result != null && !result.result.equalsIgnoreCase(""))
            if (getAddress_listener != null) {
                getAddress_listener.setaddress(latitude, longitude, result.result.replaceAll("null", "").replaceAll(", ,", "").replaceAll(", ,", ""));
            }

    }

    private void convertLatLngtoAddressApi(double lati, double longi) {
        Systems.out.println("geocodeeee convertLatLngtoAddressApi ");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lati + "," + longi + "&sensor=false" + "&key=" + SessionSave.getSession(TaxiUtil.GOOGLE_KEY, mContext);
//        CoreClient polyline = new ServiceGenerator(mContext, true).createService(CoreClient.class);
        CoreClient polyline = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl();
        polyline.getJsonbyWholeUrl("no-cache", url)
                .enqueue(new RetrofitCallbackClass<JsonObject>(mContext, new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            String result = response.body().toString();
                            Systems.out.println("geocodeeee result " + result);
                            if (result != null && result.length() > 0)
                                if (getAddress_listener != null) {
                                    try {
                                        JSONObject object = new JSONObject("" + result);
                                        if (object.has("status") && !object.getString("status").equalsIgnoreCase("OK") && object.has("error_message")) {
                                            String msg = object.getString("error_message");
                                            CToast.ShowToast(mContext, msg);
                                            return;
                                        }
                                        JSONArray array = object.getJSONArray("results");
                                        object = array.getJSONObject(0);
                                        String address = null;
                                        if (!object.getString("formatted_address").equalsIgnoreCase(""))
                                            address = object.getString("formatted_address").replaceAll("null", "").replaceAll(", ,", "").replaceAll(", ,", "");
                                        Systems.out.println("formatted_address " + address);
                                        saveGeocodeLog("" + lati + "," + longi, address, TaxiUtil.isGoogleGeocode);
                                        getAddress_listener.setaddress(lati, longi, address);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                }));
    }

    private GeocoderModel saveGeocodeLog(String latLngKey, String result, int type) {
        Systems.out.println("haiiiiiiii geocodeeee saveGeocodeLog" + latLngKey + "***" + result + "*****" + type);
        GeocoderModel model = new GeocoderModel();
        model.latLng = latLngKey;
        model.result = result;
        model.type = type;

        mRepository.insertGeocodeLog(model);
        return model;
    }

    private class GetGeocodeLog extends AsyncTask<Void, Void, GeocoderModel> {

        private double P_latitude, P_longitude;
        private int type;

        public GetGeocodeLog(double p_latitude, double p_longitude, int type) {
            this.P_latitude = p_latitude;
            this.P_longitude = p_longitude;
            this.type = type;
        }

        @Override
        protected GeocoderModel doInBackground(Void... voids) {
            GeocoderModel model = mRepository.getGeocodeModel("" + P_latitude + "," + P_longitude, type);
            return model;
        }

        @Override
        protected void onPostExecute(GeocoderModel model) {
            super.onPostExecute(model);

        }
    }

}
