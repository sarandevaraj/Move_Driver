package com.moovex.driver.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;


import com.moovex.BuildConfig;
import com.moovex.R;
import com.moovex.driver.data.DriverCommonData;
import com.moovex.driver.utils.DriverCToast;
import com.moovex.driver.utils.DriverInternetSpeedChecker;
import com.moovex.driver.utils.DriverNC;
import com.moovex.driver.utils.DriverSessionSave;
import com.moovex.util.SessionSave;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.moovex.driver.data.DriverCommonData.AUTH_KEY;
import static com.moovex.driver.data.DriverCommonData.USER_KEY;

/**
 * Created by developer on 8/31/16.
 */
public class DriverServiceGenerator {
    public static final String COMPANY_KEY = "=";
    public static String API_BASE_URL = "";
    private static OkHttpClient.Builder httpClient;
    private static Retrofit.Builder builder;

    public static Retrofit getRetrofitWithoutEncryptBaseUrl(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        Base64EncodeRequestInterceptor requestInterceptor = new Base64EncodeRequestInterceptor(DriverSessionSave.getSession("api_key", context), context);
        DecryptedPayloadInterceptor d = new DecryptedPayloadInterceptor(context);
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);

        httpClient.addInterceptor(requestInterceptor);
        System.out.println("Driver_Test" + " " + DriverSessionSave.getSession("base_url", context));
        if (BuildConfig.DEBUG) httpClient.interceptors().add(logging);
        builder = new Retrofit.Builder().baseUrl(DriverSessionSave.getSession("base_url", context)).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build());
        return builder.build();
    }

    public static Retrofit getRetrofitWithEncryptBaseUrl(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Base64EncodeRequestInterceptor requestInterceptor = new Base64EncodeRequestInterceptor(DriverSessionSave.getSession("api_key", context), context);
        DecryptedPayloadInterceptor responseInterceptor = new DecryptedPayloadInterceptor(context);
        httpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);

        httpClient.addInterceptor(responseInterceptor);
        httpClient.addInterceptor(requestInterceptor);

        if (BuildConfig.DEBUG) httpClient.interceptors().add(logging);
        builder = new Retrofit.Builder().baseUrl(DriverSessionSave.getSession("base_url", context)).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build());
        return builder.build();
    }

    public static Retrofit getRetrofitWithEncryptBaseUrl(Context context, int time_out) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Base64EncodeRequestInterceptor requestInterceptor = new Base64EncodeRequestInterceptor(DriverSessionSave.getSession("api_key", context), context);
        DecryptedPayloadInterceptor responseInterceptor = new DecryptedPayloadInterceptor(context);
        httpClient = new OkHttpClient.Builder().connectTimeout(time_out, TimeUnit.SECONDS).readTimeout(time_out, TimeUnit.SECONDS);

        httpClient.addInterceptor(responseInterceptor);
        httpClient.addInterceptor(requestInterceptor);

        if (BuildConfig.DEBUG) httpClient.interceptors().add(logging);
        builder = new Retrofit.Builder().baseUrl(DriverSessionSave.getSession("base_url", context)).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build());
        return builder.build();
    }


    public static Retrofit getRetrofitEncryptUrl(Context context, String url) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Base64EncodeRequestInterceptor requestInterceptor = new Base64EncodeRequestInterceptor(DriverSessionSave.getSession("api_key", context), context);
        DecryptedPayloadInterceptor responseInterceptor = new DecryptedPayloadInterceptor(context);

        httpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);

    /*    if (BuildConfig.DEBUG) {
            httpClient.addNetworkInterceptor(new StethoInterceptor());
            httpClient.interceptors().add(logging);
        }*/
        if (BuildConfig.DEBUG) httpClient.interceptors().add(logging);

        httpClient.addInterceptor(responseInterceptor);
        httpClient.addInterceptor(requestInterceptor);

        builder = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build());
        return builder.build();
    }


    public static class DecryptedPayloadInterceptor implements Interceptor {
        Context c;

        DecryptedPayloadInterceptor(Context c) {
            this.c = c;
        }

        @NotNull
        @Override
        public Response intercept(final Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            if (response.isSuccessful()) {
                Response.Builder newResponse = response.newBuilder();
                String contentType = response.header("Content-Type");
                if (TextUtils.isEmpty(contentType)) contentType = "application/json";
                InputStream cryptedStream = response.body().byteStream();
                String decrypted = null;
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = cryptedStream.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }

                try {
                    if (!result.toString("UTF-8").isEmpty()) decrypted = result.toString("UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (decrypted == null || decrypted.trim().isEmpty()) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> DriverCToast.ShowToast(c.getApplicationContext(), DriverNC.getString(R.string.server_error)));
                } else {
                    try {
                        new DriverCheckStatus(new JSONObject(decrypted), c).updateAuthKey();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    newResponse.body(ResponseBody.create(MediaType.parse(contentType), decrypted));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cryptedStream != null) cryptedStream.close();
                }
                Response ress = newResponse.build();
                String url_type = String.valueOf(ress.request().url());
                try {
                    if (url_type.contains("driverapi")) {
                        if (new DriverCheckStatus(new JSONObject(decrypted), c).isNormal())
                            return ress;
                        else return response;
                    } else return ress;
                } catch (Exception e) {
                    e.printStackTrace();
                    return ress;
                }
            }
            return response;
        }
    }

    public static class Base64EncodeRequestInterceptor implements Interceptor {
        String companyKey = "FNpfuspyEAzhjfoh2ONpWK0rsnClVL6OCaasqDQtWdI=";
        //        String companyKey = "eBU2X1fY+P5G7/nR1S2AsUW7dOaU6KXM3S+b4vYYFs4=";
        private final Context mContext;

        Base64EncodeRequestInterceptor(String key, Context mContext) {
            if (!key.trim().isEmpty()) companyKey = key;
            this.mContext = mContext;
        }
        @NotNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder();
            if (originalRequest.method().equalsIgnoreCase("POST")) {
                builder = originalRequest.newBuilder().method(originalRequest.method(), originalRequest.body());
            }

            builder.addHeader("authkey", DriverSessionSave.getSession(AUTH_KEY, mContext));
//            builder.addHeader("token", SessionSave.getSession(DEVICE_ID, mContext));
            builder.addHeader("userAuth", DriverSessionSave.getSession(USER_KEY, mContext));

            System.out.println("authkey : " + " " + SessionSave.getSession(AUTH_KEY, mContext));
            System.out.println("userauth : " + " " + SessionSave.getSession(USER_KEY, mContext));
            HttpUrl originalHttpUrl = originalRequest.url();
            HttpUrl url = originalHttpUrl.newBuilder().addQueryParameter("dt", "a").addQueryParameter("i", DriverSessionSave.getSession("Id", mContext)).addQueryParameter("pv", "" + BuildConfig.VERSION_CODE).addQueryParameter("k", DriverSessionSave.getSession(DriverCommonData.FIREBASE_KEY, mContext)).addQueryParameter("s", DriverInternetSpeedChecker.INSTANCE.getDownloadSpeed()).build();

            builder.url(url);
            String body_value = "";
            try {

                if (originalRequest.body() != null) {
                    final RequestBody body = originalRequest.body();
                    final Buffer buffer = new Buffer();
                    if (body != null) body.writeTo(buffer);
                    body_value = buffer.readUtf8();
                }

            } catch (final IOException e) {
                e.printStackTrace();
            }
            String logs = url + " - " + originalRequest.method() + " - " + body_value + " - auth: " + SessionSave.getSession(AUTH_KEY, mContext) + " - user_auth: " + SessionSave.getSession(USER_KEY, mContext);
            DriverSessionSave.saveAPI(logs, mContext);
            return chain.proceed(builder.build());
        }
    }
}
