package com.taximobility.features;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.taximobility.interfaces.DistanceMatrixInterface;
import com.taximobility.roomDB.GoogleMapModel;
import com.taximobility.roomDB.MapLoggerRepository;
import com.taximobility.roomDB.MapboxModel;
import com.taximobility.service.CoreClient;
import com.taximobility.service.RetrofitCallbackClass;
import com.taximobility.util.AppController;
import com.taximobility.util.SessionSave;
import com.taximobility.util.ShowToast;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by developer on 23/5/18.
 */

public class FindApproxDistance {
    int type;
    String url;
    String from = "";
    String to = "";
    Context mContext;
    private DistanceMatrixInterface matrixInterface;
    private MapLoggerRepository mRepository;

    public FindApproxDistance(DistanceMatrixInterface matrixInterface) {
        this.matrixInterface = matrixInterface;
    }

    public void getDistance(Context c, double P_latitude, double P_longitude,
                            double D_latitude, double D_longitude, final int type) {
        this.mContext = c;
        this.type = type;
        this.from = P_latitude + "," + P_longitude;
        this.to = D_latitude + "," + D_longitude;

        mRepository = new MapLoggerRepository(mContext);
        new GetGoogleLog(P_latitude, P_longitude, D_latitude, D_longitude).execute();
    }


    private void makeGoogleApiCall(double P_latitude, double P_longitude, double D_latitude, double D_longitude) {

        String baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + P_latitude + "," + P_longitude + "&destinations=" + D_latitude + "," + D_longitude + "&key=" + SessionSave.getSession(TaxiUtil.GOOGLE_KEY, mContext);
//        CoreClient polyline = new ServiceGenerator(mContext, true).createService(CoreClient.class);
        CoreClient polyline = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl();
        polyline.getJsonbyWholeUrl("no-cache", baseUrl)
                .enqueue(new RetrofitCallbackClass<JsonObject>(mContext, new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            String result = response.body().toString();
                            Systems.out.println("pppppppppppppppp" + from.trim() + "___" + to.trim() + "*** " + result);

                            Systems.out.println("carmodel" + url + "" + result.replaceAll("\\s", ""));
                            JSONObject obj = null;
                            try {

                                JSONObject object = new JSONObject(result);
                                if (object.has("status") && !object.getString("status").equalsIgnoreCase("OK") && object.has("error_message")) {
                                    String msg = object.getString("error_message");
                                    CToast.ShowToast(mContext, msg);
                                    return;
                                }
                                obj = new JSONObject(result).getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0);
                                JSONObject ds = obj.getJSONObject("distance");
                                String dis = ds.getString("value");
                                JSONObject timee = obj.getJSONObject("duration");
                                String time = timee.getString("value");
                                double times = Double.parseDouble(time) / 60;
                                double dist = Double.parseDouble(dis) / 1000;
                                saveGoogleLog(from.trim() + to.trim(), times, dist, "", result);

                                matrixInterface.onDistanceCalled(times, type, 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                setFailureDistance(e.getLocalizedMessage());
//                                matrixInterface.onDistanceCalled(null, null, type, 1);
                            }

                        } else {
                            setFailureDistance("Api Failed");
//                            matrixInterface.onDistanceCalled(null, null, type, 1);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, Throwable t) {
                        ShowToast.center(mContext, t.getLocalizedMessage());
                        setFailureDistance(t.getLocalizedMessage());
                    }
                }));
    }


    private void setFailureDistance(String message) {
        if (matrixInterface != null)
            matrixInterface.onDistanceCalled(null, type, 1);
    }


    private void saveGoogleLog(String s, double times, double dist, String s1, String result) {
        GoogleMapModel model = new GoogleMapModel();
        model.fromTo = s;
        model.time = times;
        model.distance = dist;
        model.routeResult = s1;
        model.distanceResult = result;

        mRepository.insertGoogleLog(model);
    }

    private void saveMapboxLog(String s, double times, double dist, String s1, String result) {
        MapboxModel model = new MapboxModel();
        model.fromTo = s;
        model.time = times;
        model.distance = dist;
        model.routeResult = s1;
        model.distanceResult = result;

        mRepository.insertMapboxLog(model);
    }

    private class GetGoogleLog extends AsyncTask<Void, Void, GoogleMapModel> {

        private double P_latitude, P_longitude, D_latitude, D_longitude;

        public GetGoogleLog(double p_latitude, double p_longitude, double d_latitude, double d_longitude) {
            this.P_latitude = p_latitude;
            this.P_longitude = p_longitude;
            this.D_latitude = d_latitude;
            this.D_longitude = d_longitude;
        }

        @Override
        protected GoogleMapModel doInBackground(Void... voids) {
            GoogleMapModel model = mRepository.getGoogleModel(from.trim() + to.trim());

            return model;
        }

        @Override
        protected void onPostExecute(GoogleMapModel model) {
            super.onPostExecute(model);
            if (model != null) {
                matrixInterface.onDistanceCalled(model.time, type, 1);
            } else {
                makeGoogleApiCall(P_latitude, P_longitude, D_latitude, D_longitude);
            }
        }
    }
}