package com.moovex.driver.service;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.moovex.R;
import com.moovex.driver.data.DriverCommonData;
import com.moovex.driver.interfaces.DriverAPIResult;
import com.moovex.driver.utils.DriverCToast;
import com.moovex.driver.utils.DirverColorchange;
import com.moovex.driver.utils.DriverNC;
import com.moovex.driver.utils.DriverNetworkStatus;
import com.moovex.driver.utils.DriverSessionSave;
import com.moovex.driver.utils.DriverSystems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
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
public class DriverAPIService_Retrofit_JSON {
    private boolean dont_encode;
    public Context mContext;
    private boolean isSuccess = true;
    private final boolean GetMethod;
    private Dialog mDialog;
    private JSONObject data;
    public DriverAPIResult response;
    public boolean wholeURL;
    String result = "";
    private String url_type;
    private int timeOut = 0;

    public DriverAPIService_Retrofit_JSON(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod) {
        mContext = ctx;
        response = res;
        this.data = j;
        GetMethod = getmethod;
    }

    public DriverAPIService_Retrofit_JSON(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod, int timeOut) {
        mContext = ctx;
        response = res;
        this.data = j;
        GetMethod = getmethod;
        this.timeOut = timeOut;
    }

    public DriverAPIService_Retrofit_JSON(Context ctx, DriverAPIResult res, boolean getmethod, String url) {
        mContext = ctx;
        response = res;
        this.data = null;
        GetMethod = getmethod;
        dont_encode = true;
        wholeURL = true;
        url_type = url;
    }

    public DriverAPIService_Retrofit_JSON(Context ctx, DriverAPIResult res, String j, boolean getmethod) {
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


    public DriverAPIService_Retrofit_JSON(Context ctx, DriverAPIResult res, JSONObject j, boolean getmethod, String url, boolean dont_encode) {
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


    public DriverAPIService_Retrofit_JSON(Context ctx, DriverAPIResult res, boolean getmethod) {
        mContext = ctx;
        response = res;
        GetMethod = getmethod;
    }

    //@Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        showDialog();
        doInBackground();
    }

    public void showDialog() {
        try {
            if (DriverNetworkStatus.isOnline(mContext)) {
                if (mDialog != null && mContext != null) mDialog.dismiss();
                View view = View.inflate(mContext, R.layout.driver_progress_bar, null);
                mDialog = new Dialog(mContext, R.style.dialogwinddow);
                DirverColorchange.ChangeColor((ViewGroup) view, mContext);
                mDialog.setContentView(view);
                mDialog.setCancelable(false);
                try {
                    if (mContext != null) mDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                ImageView iv = mDialog.findViewById(R.id.giff);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                Glide.with(mContext).load(R.raw.driver_loading_anim).into(imageViewTarget);
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
        if (!DriverNetworkStatus.isOnline(mContext)) {
            isSuccess = false;
            response.getResult(false, DriverNC.getResources().getString(R.string.please_check_internet));
            result = DriverNC.getString(R.string.please_check_internet);
            //return result;
        } else {
            if (GetMethod) {
                DriverCoreClient client;
                DriverSystems.out.println("rrc_____get" + url_type + "___" + wholeURL + "___" + data);
                if (dont_encode) {
                    client = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl_driver();
                } else {
                    client = AppController.getInstance().getApiManagerWithEncryptBaseUrl_driver();
                }
//                client = new ServiceGenerator(mContext, dont_encode).createService(CoreClient.class);
                Call<ResponseBody> coreResponse;
                if (!wholeURL)
                    coreResponse = client.coreDetails("", "no-cache", url_type, DriverSessionSave.getSession(DriverCommonData.GETCORE_LASTUPDATE, mContext).equals("") ? "0" : DriverSessionSave.getSession(DriverCommonData.GETCORE_LASTUPDATE, mContext), DriverSessionSave.getSession(DriverCommonData.ACCESS_KEY, mContext));
                else coreResponse = client.getWhole("no-cache", url_type);
                coreResponse.enqueue(new DriverRetrofitCallbackClass<>(mContext, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                        String data;
                        closeDialog();
                        if (response.isSuccessful()) {
                            try {
                                if (response.body() != null) {
                                    data = response.body().string();
                                    if (DriverAPIService_Retrofit_JSON.this.response != null)
                                        DriverAPIService_Retrofit_JSON.this.response.getResult(true, data);
                                } else {
                                    if (DriverAPIService_Retrofit_JSON.this.response != null)
                                        DriverAPIService_Retrofit_JSON.this.response.getResult(false, null);
                                    DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (DriverAPIService_Retrofit_JSON.this.response != null)
                                    DriverAPIService_Retrofit_JSON.this.response.getResult(false, null);
                                DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                            }
                        } else {
                            if (DriverAPIService_Retrofit_JSON.this.response != null)
                                DriverAPIService_Retrofit_JSON.this.response.getResult(false, null);
                            DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        if (DriverAPIService_Retrofit_JSON.this.response != null)
                            DriverAPIService_Retrofit_JSON.this.response.getResult(false, null);
                        DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                        t.printStackTrace();
                        closeDialog();
                    }
                }));

            } else {
//                CoreClient client = new ServiceGenerator(mContext, dont_encode).createService(CoreClient.class);

                DriverCoreClient client;

                if (dont_encode) {
                    client = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl_driver();
                } else {
                    if (timeOut == 0) {
                        client = AppController.getInstance().getApiManagerWithEncryptBaseUrl_driver();
                    } else {
                        client = AppController.getInstance().getApiManagerWithEncryptBaseUrl_driver(timeOut);
                    }

                }
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (data).toString());

                Call<ResponseBody> coreResponse = client.updateUser(DriverServiceGenerator.COMPANY_KEY, body, url_type, DriverSessionSave.getSession("Lang", mContext));
                coreResponse.enqueue(new DriverRetrofitCallbackClass<>(mContext, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                        String data;
                        closeDialog();
                        if (response.isSuccessful()) {
                            try {
                                if (response.body() != null) {
                                    data = response.body().string();
                                    if (DriverAPIService_Retrofit_JSON.this.response != null)
                                        DriverAPIService_Retrofit_JSON.this.response.getResult(true, data);
                                } else {
                                    if (DriverAPIService_Retrofit_JSON.this.response != null)
                                        DriverAPIService_Retrofit_JSON.this.response.getResult(false, null);
                                    DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (DriverAPIService_Retrofit_JSON.this.response != null)
                                    DriverAPIService_Retrofit_JSON.this.response.getResult(false, null);
                                DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                            }
                        } else {
                            if (DriverAPIService_Retrofit_JSON.this.response != null)
                                DriverAPIService_Retrofit_JSON.this.response.getResult(false, null);
                            DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        if (DriverAPIService_Retrofit_JSON.this.response != null)
                            DriverAPIService_Retrofit_JSON.this.response.getResult(false, null);
                        DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
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