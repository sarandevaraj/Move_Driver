package com.moovex.driver.service;

import android.content.Context;

import androidx.annotation.NonNull;

import com.moovex.R;
import com.moovex.driver.data.DriverCommonData;
import com.moovex.driver.interfaces.DriverAPIResult;
import com.moovex.driver.utils.DriverCToast;
import com.moovex.driver.utils.DriverNC;
import com.moovex.driver.utils.DriverNetworkStatus;
import com.moovex.driver.utils.DriverSessionSave;
import com.moovex.util.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author developer This AsyncTask used to communicate the application with server through Volley framework. Here the response completely in JSON format. Constructor get the input details List<NameValuePair>,POST or GET then url. In pre execute,Show the progress dialog. In Background,Connect and get the response. In Post execute, Return the result with interface. This class call the API without any progress on UI.
 */
public class DriverAPIService_Retrofit_JSON_NoProgress {
    public Context mContext;
    public DriverAPIResult response;
    String result = "";
    boolean dont_encode;
    private boolean wholeURL;
    private final boolean GetMethod;
    private final JSONObject data;
    private String url_type;
    private Call<ResponseBody> coreResponse;

    public DriverAPIService_Retrofit_JSON_NoProgress(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod) {
        mContext = ctx;
        response = res;
        this.data = j;
        GetMethod = getmethod;
    }

    public DriverAPIService_Retrofit_JSON_NoProgress(Context ctx, DriverAPIResult res, String j, boolean getmethod) {
        mContext = ctx;
        response = res;
        JSONObject jobj = null;
        try {
            if (!getmethod) jobj = new JSONObject(j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.data = jobj;
        GetMethod = getmethod;
    }

    public DriverAPIService_Retrofit_JSON_NoProgress(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod, String url, boolean dont_encode) {
        mContext = ctx;
        response = res;
        this.data = j;
        this.dont_encode = dont_encode;
        GetMethod = getmethod;
        String[] type = url.split("type=");
        if (type.length > 1) url_type = type[1];
        else {
            wholeURL = true;
            url_type = url;
        }
    }

    //@Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        doInBackground();
    }

    //@Override
    protected void doInBackground() {
        // TODO Auto-generated method stub
        if (!DriverNetworkStatus.isOnline(mContext)) {
            response.getResult(false, DriverNC.getString(R.string.check_net_connection));
            result = DriverNC.getString(R.string.check_net_connection);
            //return result;
        } else {
            if (GetMethod) {
                DriverCoreClient client;
                if (dont_encode) {
                    client = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl_driver();
                } else {
                    client = AppController.getInstance().getApiManagerWithEncryptBaseUrl_driver();
                }

                if (!wholeURL)
                    coreResponse = client.coreDetails("", "no-cache", url_type, DriverSessionSave.getSession(DriverCommonData.GETCORE_LASTUPDATE, mContext).equals("") ? "0" : DriverSessionSave.getSession(DriverCommonData.GETCORE_LASTUPDATE, mContext), DriverSessionSave.getSession(DriverCommonData.ACCESS_KEY, mContext));
                else coreResponse = client.getWhole("no-cache", url_type);
                coreResponse.enqueue(new DriverRetrofitCallbackClass<>(mContext, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                        String data;
                        if (response.isSuccessful()) {
                            try {
                                if (response.body() != null) {
                                    data = response.body().string();
                                    if (DriverAPIService_Retrofit_JSON_NoProgress.this.response != null)
                                        DriverAPIService_Retrofit_JSON_NoProgress.this.response.getResult(true, data);
                                } else {
                                    if (DriverAPIService_Retrofit_JSON_NoProgress.this.response != null)
                                        DriverAPIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                                    DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (DriverAPIService_Retrofit_JSON_NoProgress.this.response != null)
                                    DriverAPIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                                DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                            }
                        } else {
                            if (DriverAPIService_Retrofit_JSON_NoProgress.this.response != null)
                                DriverAPIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                            DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        if (DriverAPIService_Retrofit_JSON_NoProgress.this.response != null)
                            DriverAPIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                        DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                        t.printStackTrace();
                    }
                }));

            } else {
                DriverCoreClient client;
                if (dont_encode) {
                    client = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl_driver();
                } else {
                    client = AppController.getInstance().getApiManagerWithEncryptBaseUrl_driver();
                }

                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (data).toString());

                Call<ResponseBody> coreResponse = client.updateUser(DriverServiceGenerator.COMPANY_KEY, body, url_type, DriverSessionSave.getSession("Lang", mContext));
                coreResponse.enqueue(new DriverRetrofitCallbackClass<>(mContext, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                        String data;
                        if (response.isSuccessful()) {
                            try {
                                if (response.body() != null) {
                                    data = response.body().string();
                                    if (DriverAPIService_Retrofit_JSON_NoProgress.this.response != null)
                                        DriverAPIService_Retrofit_JSON_NoProgress.this.response.getResult(true, data);
                                } else {
                                    if (DriverAPIService_Retrofit_JSON_NoProgress.this.response != null)
                                        DriverAPIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                                    DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (DriverAPIService_Retrofit_JSON_NoProgress.this.response != null)
                                    DriverAPIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                                DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                            }
                        } else {
                            if (DriverAPIService_Retrofit_JSON_NoProgress.this.response != null)
                                DriverAPIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                            DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        if (DriverAPIService_Retrofit_JSON_NoProgress.this.response != null)
                            DriverAPIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                        DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                        t.printStackTrace();
                    }
                }));
            }
        }
    }

    public void execute(String url) {
        String[] type = url.split("=");
        this.url_type = type[1];
        onPreExecute();
    }

    public void execute() {
        onPreExecute();
    }
}