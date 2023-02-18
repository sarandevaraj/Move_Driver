package com.movedriverdriver.service;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.movedriverdriver.R;
import com.movedriverdriver.driver.interfaces.DriverAPIResult;
import com.movedriverdriver.driver.utils.DriverCToast;
import com.movedriverdriver.driver.utils.DriverSystems;
import com.movedriverdriver.util.AppController;
import com.movedriverdriver.util.NetworkStatus;
import com.movedriverdriver.util.SessionSave;
import com.movedriverdriver.util.TaxiUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author developer This AsyncTask used to communicate the application with server through Retrofit framework.
 * Here the response completely in JSON format. Constructor get the input details List<NameValuePair>,POST or GET then url.
 * In pre execute,Show the progress dialog.
 * In Background,Connect and get the response.
 * In Post execute, Return the result with interface.
 * This class call the API without any progress on UI.
 */
public class APIService_Retrofit_JSON {
    private int timeOut = 0;
    HashMap<String, String> map;
    public Dialog mProgressdialog;
    public Context mContext;
    private boolean isSuccess = true;
    private final boolean GetMethod;
    private Dialog mDialog;
    private JSONObject data;
    public DriverAPIResult response;
    public boolean wholeURL;
    String result = "";
    private String url_type;
    boolean dont_encode;

    public APIService_Retrofit_JSON(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod) {
        mContext = ctx;
        response = res;
        this.data = j;
        GetMethod = getmethod;
    }

    public APIService_Retrofit_JSON(Context ctx, DriverAPIResult res, String j, boolean getmethod) {
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

    public APIService_Retrofit_JSON(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod, String url) {
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

    public APIService_Retrofit_JSON(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod, String url, boolean dont_encode) {
        mContext = ctx;
        response = res;
        this.data = j;
        GetMethod = getmethod;
        this.dont_encode = dont_encode;
        String[] type = url.split("type=");
        if (type.length > 1) url_type = type[1];
        else {
            wholeURL = true;
            url_type = url;
        }
    }

    public APIService_Retrofit_JSON(Context ctx, DriverAPIResult res, boolean getmethod, String url) {
        mContext = ctx;
        response = res;
        this.data = null;
        GetMethod = getmethod;
        dont_encode = true;
        wholeURL = true;
        url_type = url;
    }

    public APIService_Retrofit_JSON(Context ctx, DriverAPIResult res, boolean getmethod) {
        mContext = ctx;
        response = res;
        GetMethod = getmethod;
    }

    public APIService_Retrofit_JSON(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod, int i) {
        mContext = ctx;
        response = res;
        this.data = j;
        GetMethod = getmethod;
        timeOut = i;
    }

    //@Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
//		super.onPreExecute();
        showDialog();
        doInBackground();
    }

    public void showDialog() {
        try {
            if (NetworkStatus.isOnline(mContext)) {
                if (mContext != null) {
                    if (mContext instanceof Activity) {
                        Activity activity = ((Activity) mContext);
                        if (activity.getCurrentFocus() != null) {
                            if (mDialog != null && mDialog.isShowing()) mDialog.dismiss();
                            View view = View.inflate(mContext, R.layout.progress_bar, null);
                            mDialog = new Dialog(mContext, R.style.dialogwinddow);
                            mDialog.setContentView(view);
                            mDialog.setCancelable(false);
                            mDialog.show();
                            ImageView iv = mDialog.findViewById(R.id.giff);
                            DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                            Glide.with(mContext).load(R.raw.loading_anim).into(imageViewTarget);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDialog() {
        try {
            if (mDialog != null) if (mDialog.isShowing()) mDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Override
    protected void doInBackground() {
        // TODO Auto-generated method stub
        if (!NetworkStatus.isOnline(mContext)) {
            isSuccess = false;
            response.getResult(false, mContext.getResources().getString(R.string.check_internet_connection));
            result = mContext.getResources().getString(R.string.check_internet_connection);
            //return result;
        } else {
            if (GetMethod) {
                CoreClient client;
                if (timeOut == 0) {
                    if (dont_encode)
                        client = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl();
                    else client = AppController.getInstance().getApiManagerWithEncryptBaseUrl();
//                    client = new ServiceGenerator(mContext, dont_encode).createService(CoreClient.class);
                } else {
//                    client = new ServiceGenerator(mContext, dont_encode, timeOut).createService(CoreClient.class);
                    if (dont_encode)
                        client = AppController.getInstance().getApiManagerWithTimeoutWithoutEncrypt(timeOut);
                    else
                        client = AppController.getInstance().getApiManagerWithTimeoutWithEncrypt(timeOut);
                }
                Call<ResponseBody> coreResponse;
                if (!wholeURL) {
                    coreResponse = client.coreDetailsg("no-cache", TaxiUtil.COMPANY_KEY, url_type, SessionSave.getSession(TaxiUtil.GETCORE_LASTUPDATE, mContext).equals("") ? "0" : SessionSave.getSession(TaxiUtil.GETCORE_LASTUPDATE, mContext), SessionSave.getSession(TaxiUtil.ACCESS_KEY, mContext));
                } else coreResponse = client.getWhole("no-cache", url_type);
                coreResponse.enqueue(new RetrofitCallbackClass<ResponseBody>(mContext, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                        String data;
                        closeDialog();
                        if (response.isSuccessful()) {
                            try {
                                if (response.body() != null) {
                                    data = response.body().string();
                                    if (APIService_Retrofit_JSON.this.response != null)
                                        APIService_Retrofit_JSON.this.response.getResult(true, data);
                                } else {
                                    if (APIService_Retrofit_JSON.this.response != null)
                                        APIService_Retrofit_JSON.this.response.getResult(false, null);
                                    DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (APIService_Retrofit_JSON.this.response != null)
                                    APIService_Retrofit_JSON.this.response.getResult(false, null);
                                DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                            }
                        } else {
                            if (APIService_Retrofit_JSON.this.response != null)
                                APIService_Retrofit_JSON.this.response.getResult(false, null);
                            DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        if (APIService_Retrofit_JSON.this.response != null)
                            APIService_Retrofit_JSON.this.response.getResult(false, null);
                        DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                        t.printStackTrace();
                        closeDialog();
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
//                    client = new ServiceGenerator(mContext, dont_encode, timeOut).createService(CoreClient.class);
                    if (dont_encode)
                        client = AppController.getInstance().getApiManagerWithTimeoutWithoutEncrypt(timeOut);
                    else
                        client = AppController.getInstance().getApiManagerWithTimeoutWithEncrypt(timeOut);
                }
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (data).toString());
                DriverSystems.out.println("calling");
                Call<ResponseBody> coreResponse = client.updateUser(TaxiUtil.COMPANY_KEY, body, url_type, SessionSave.getSession("Lang", mContext), SessionSave.getSession("PASS_ID", mContext));
                coreResponse.enqueue(new RetrofitCallbackClass<ResponseBody>(mContext, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                        String data;
                        closeDialog();
                        if (response.isSuccessful()) {
                            try {
                                if (response.body() != null) {
                                    data = response.body().string();
                                    if (APIService_Retrofit_JSON.this.response != null)
                                        APIService_Retrofit_JSON.this.response.getResult(true, data);
                                } else {
                                    if (APIService_Retrofit_JSON.this.response != null)
                                        APIService_Retrofit_JSON.this.response.getResult(false, null);
                                    DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (APIService_Retrofit_JSON.this.response != null)
                                    APIService_Retrofit_JSON.this.response.getResult(false, null);
                                DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                            }
                        } else {
                            if (APIService_Retrofit_JSON.this.response != null)
                                APIService_Retrofit_JSON.this.response.getResult(false, null);
                            DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        if (APIService_Retrofit_JSON.this.response != null)
                            APIService_Retrofit_JSON.this.response.getResult(false, null);
                        DriverCToast.ShowToast(mContext, mContext.getString(R.string.server_error));
                        t.printStackTrace();
                        closeDialog();
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