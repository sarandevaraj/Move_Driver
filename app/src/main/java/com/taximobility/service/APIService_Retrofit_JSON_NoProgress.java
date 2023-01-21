package com.taximobility.service;

import android.app.Dialog;
import android.content.Context;

import com.taximobility.R;
import com.taximobility.driver.interfaces.DriverAPIResult;
import com.taximobility.driver.utils.DriverCToast;
import com.taximobility.util.AppController;
import com.taximobility.util.NetworkStatus;
import com.taximobility.util.SessionSave;
import com.taximobility.util.TaxiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.taximobility.util.ConstantsKt.LANG;
import static com.taximobility.util.ConstantsKt.PASS_ID;

import androidx.annotation.NonNull;

/**
 * @author developer This AsyncTask used to communicate the application with server through Retrofit framework.
 * Here the response completely in JSON format. Constructor get the input details List<NameValuePair>,POST or GET then url.
 * In pre execute,Show the progress dialog.
 * In Background,Connect and get the response.
 * In Post execute, Return the result with interface. This class call the API without any progress on UI.
 */
public class APIService_Retrofit_JSON_NoProgress {
    public Dialog mProgressdialog;
    public Context mContext;
    public DriverAPIResult response;
    HashMap<String, String> map;
    String result = "";
    private boolean dont_encode;
    private boolean wholeURL;
    private int timeOut = 0;
    private boolean isSuccess = true;
    private final boolean GetMethod;
    private Dialog mDialog;
    private JSONObject data;
    private String url_type;
    private Call<ResponseBody> coreResponse;

    public APIService_Retrofit_JSON_NoProgress(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod, int time) {
        mContext = ctx;
        response = res;
        this.data = j;
        this.GetMethod = getmethod;
        timeOut = time;
    }

    public APIService_Retrofit_JSON_NoProgress(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod) {
        mContext = ctx;
        response = res;
        this.data = j;
        GetMethod = getmethod;
    }

    public APIService_Retrofit_JSON_NoProgress(Context ctx, DriverAPIResult res, String j, boolean getmethod) {
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

    public APIService_Retrofit_JSON_NoProgress(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod, String url, boolean dont_encode) {
        mContext = ctx;
        response = res;
        this.data = j;
        GetMethod = getmethod;
        String[] type = url.split("type=");
        if (type.length > 1) url_type = type[1];
        else {
            wholeURL = true;
            url_type = url;
        }
        this.dont_encode = dont_encode;
    }

    public APIService_Retrofit_JSON_NoProgress(Context ctx, DriverAPIResult res, boolean getmethod, String url) {
        mContext = ctx;
        response = res;
        this.data = null;
        GetMethod = getmethod;
        wholeURL = true;
        url_type = url;
    }

    public APIService_Retrofit_JSON_NoProgress(Context ctx, DriverAPIResult res, boolean getmethod) {
        mContext = ctx;
        response = res;
        GetMethod = getmethod;
    }

    public APIService_Retrofit_JSON_NoProgress(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod, String url) {
        mContext = ctx;
        response = res;
        this.data = j;
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
        if (!NetworkStatus.isOnline(mContext)) {
            isSuccess = false;
            response.getResult(false, mContext.getResources().getString(R.string.check_internet_connection));
            result = mContext.getResources().getString(R.string.check_internet_connection);
        } else {
            if (GetMethod) {
                CoreClient client;
                if (dont_encode)
                    client = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl();
                else client = AppController.getInstance().getApiManagerWithEncryptBaseUrl();
//                CoreClient client = new ServiceGenerator(mContext, dont_encode).createService(CoreClient.class);
                if (!wholeURL) {
                    if (url_type.equalsIgnoreCase("getmodel_fare_details"))
                        coreResponse = client.coreDetailsg("no-cache", TaxiUtil.COMPANY_KEY, url_type, SessionSave.getSession(PASS_ID, mContext));
                    else {
                        coreResponse = client.coreDetailsg("no-cache", TaxiUtil.COMPANY_KEY, url_type, SessionSave.getSession(TaxiUtil.GETCORE_LASTUPDATE, mContext).equals("") ? "0" : SessionSave.getSession(TaxiUtil.GETCORE_LASTUPDATE, mContext), SessionSave.getSession(TaxiUtil.ACCESS_KEY, mContext));
                    }
                } else coreResponse = client.getWhole("no-cache", url_type);
                //  Call<ResponseBody> coreResponse = client.coreDetails(TaxiUtil.COMPANY_KEY,TaxiUtil.DYNAMIC_AUTH_KEY,url_type, SessionSave.getSession("Lang",mContext));
                coreResponse.enqueue(new RetrofitCallbackClass<ResponseBody>(mContext, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                        String data;
                        if (response.isSuccessful()) {
                            try {
                                if (response.body() != null) {
                                    data = response.body().string();
                                    if (APIService_Retrofit_JSON_NoProgress.this.response != null)
                                        APIService_Retrofit_JSON_NoProgress.this.response.getResult(true, data);
                                } else {
                                    if (APIService_Retrofit_JSON_NoProgress.this.response != null)
                                        APIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                                    DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (APIService_Retrofit_JSON_NoProgress.this.response != null)
                                    APIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                                DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                            }
                        } else {
                            if (APIService_Retrofit_JSON_NoProgress.this.response != null)
                                APIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                            DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        if (APIService_Retrofit_JSON_NoProgress.this.response != null)
                            APIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                        DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                    }
                }));

            } else {
                CoreClient client;
                if (timeOut == 0) {
                    if (dont_encode)
                        client = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl();
                    else client = AppController.getInstance().getApiManagerWithEncryptBaseUrl();
//                    client = new ServiceGenerator(mContext, dont_encode).createService(CoreClient.class);
                } else {
                    if (dont_encode)
                        client = AppController.getInstance().getApiManagerWithTimeoutWithoutEncrypt(timeOut);
                    else
                        client = AppController.getInstance().getApiManagerWithTimeoutWithEncrypt(timeOut);
//                    client = new ServiceGenerator(mContext, dont_encode, timeOut).createService(CoreClient.class);
                }
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (data).toString());

                Call<ResponseBody> coreResponse = client.updateUser(TaxiUtil.COMPANY_KEY, body, url_type, SessionSave.getSession(LANG, mContext), SessionSave.getSession("PASS_ID", mContext));
                coreResponse.enqueue(new RetrofitCallbackClass<ResponseBody>(mContext, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                        String data;
                        if (response.isSuccessful()) {
                            try {
                                if (response.body() != null) {
                                    data = response.body().string();
                                    if (APIService_Retrofit_JSON_NoProgress.this.response != null)
                                        APIService_Retrofit_JSON_NoProgress.this.response.getResult(true, data);
                                } else {
                                    if (APIService_Retrofit_JSON_NoProgress.this.response != null)
                                        APIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                                    DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (APIService_Retrofit_JSON_NoProgress.this.response != null)
                                    APIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                                DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                            }
                        } else {
                            if (APIService_Retrofit_JSON_NoProgress.this.response != null)
                                APIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                            DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        if (APIService_Retrofit_JSON_NoProgress.this.response != null)
                            APIService_Retrofit_JSON_NoProgress.this.response.getResult(false, null);
                        DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
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