package com.taximobility.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.mayan.sospluginmodlue.service.CheckStatus;
import com.taximobility.BuildConfig;
import com.taximobility.R;
import com.taximobility.driver.service.DriverCheckStatus;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.features.CToast;
import com.taximobility.roomDB.LoggerRepository;
import com.taximobility.util.SessionSave;
import com.taximobility.util.TaxiUtil;

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

import static com.taximobility.util.ConstantsKt.PASS_ID;

/**
 * Created by developer on 8/31/16.
 * Initiate the Service generator class for Retrofit service
 * This class to be initiated before each api is calling
 */
public class ServiceGenerator {
    private static OkHttpClient.Builder httpClient;
    private static Retrofit.Builder builder;
    private static LoggerRepository mRepository;
    private static String to_encode = "";


    public static Retrofit getRetrofitWithEncryptBaseUrl(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Base64EncodeRequestInterceptor requestInterceptor = new Base64EncodeRequestInterceptor(SessionSave.getSession("api_key", context), context);
        DecryptedPayloadInterceptor responseInterceptor = new DecryptedPayloadInterceptor(context, false);
        httpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);

     /*   if (BuildConfig.DEBUG) {
            httpClient.addNetworkInterceptor(new StethoInterceptor());
            httpClient.interceptors().add(logging);
        }*/
        if (BuildConfig.DEBUG)
            httpClient.interceptors().add(logging);

        httpClient.addInterceptor(responseInterceptor);
        httpClient.addInterceptor(requestInterceptor);
        builder = new Retrofit.Builder()
                .baseUrl(SessionSave.getSession("base_url", context))
                .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build());
        return builder.build();
    }


    public static Retrofit getRetrofitEncryptUrl(Context context, String url) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Base64EncodeRequestInterceptor requestInterceptor = new Base64EncodeRequestInterceptor(SessionSave.getSession("api_key", context), context);
        DecryptedPayloadInterceptor responseInterceptor = new DecryptedPayloadInterceptor(context, false);
        httpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);

     /*   if (BuildConfig.DEBUG) {
            httpClient.addNetworkInterceptor(new StethoInterceptor());
            httpClient.interceptors().add(logging);
        }*/
        if (BuildConfig.DEBUG)
            httpClient.interceptors().add(logging);

        httpClient.addInterceptor(responseInterceptor);
        httpClient.addInterceptor(requestInterceptor);

        builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build());
        return builder.build();
    }


    public static Retrofit getRetrofitWithoutEncryptBaseUrl(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Base64EncodeRequestInterceptor requestInterceptor = new Base64EncodeRequestInterceptor(SessionSave.getSession("api_key", context), context);
        DecryptedPayloadInterceptor responseInterceptor = new DecryptedPayloadInterceptor(context, true);
        httpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);

       /* if (BuildConfig.DEBUG) {
            httpClient.addNetworkInterceptor(new StethoInterceptor());
            httpClient.interceptors().add(logging);
        }*/
        if (BuildConfig.DEBUG)
            httpClient.interceptors().add(logging);

        httpClient.addInterceptor(responseInterceptor);
        httpClient.addInterceptor(requestInterceptor);

        builder = new Retrofit.Builder()
                .baseUrl(SessionSave.getSession("base_url", context))
                .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build());
        return builder.build();
    }


    public static Retrofit getRetrofitWithTimeOutWithoutEncrypt(Context context, int timeOut) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Base64EncodeRequestInterceptor requestInterceptor = new Base64EncodeRequestInterceptor(SessionSave.getSession("api_key", context), context);
        DecryptedPayloadInterceptor responseInterceptor = new DecryptedPayloadInterceptor(context, true);
        httpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);

      /*  if (BuildConfig.DEBUG) {
            httpClient.addNetworkInterceptor(new StethoInterceptor());
            httpClient.interceptors().add(logging);
        }*/
        if (BuildConfig.DEBUG)
            httpClient.interceptors().add(logging);

        httpClient.addInterceptor(responseInterceptor);
        httpClient.addInterceptor(requestInterceptor);

        builder = new Retrofit.Builder()
                .baseUrl(SessionSave.getSession("base_url", context))
                .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build());
        return builder.build();
    }


    public static Retrofit getRetrofitWithTimeOutWithEncrypt(Context context, int timeOut) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Base64EncodeRequestInterceptor requestInterceptor = new Base64EncodeRequestInterceptor(SessionSave.getSession("api_key", context), context);
        DecryptedPayloadInterceptor responseInterceptor = new DecryptedPayloadInterceptor(context, false);
        httpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);

     /*   if (BuildConfig.DEBUG) {
            httpClient.addNetworkInterceptor(new StethoInterceptor());
            httpClient.interceptors().add(logging);
        }*/
        if (BuildConfig.DEBUG)
            httpClient.interceptors().add(logging);

        httpClient.addInterceptor(responseInterceptor);
        httpClient.addInterceptor(requestInterceptor);

        builder = new Retrofit.Builder()
                .baseUrl(SessionSave.getSession("base_url", context))
                .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build());
        return builder.build();
    }


    public static class DecryptedPayloadInterceptor implements Interceptor {
        Context c;
        boolean dont_encode = true;

        DecryptedPayloadInterceptor(Context c, boolean dont_encode) {
            this.c = c;
            this.dont_encode = dont_encode;
            mRepository = new LoggerRepository(c);
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
                decrypted = result.toString("UTF-8");

                if (decrypted == null || decrypted.trim().isEmpty()) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            CToast.ShowToast(c.getApplicationContext(), DriverNC.getString(R.string.server_error));
                        }
                    });
                } else {
                    try {
                        new CheckStatus(new JSONObject(decrypted), c).updateAuthKey();
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
                    if (cryptedStream != null)
                        cryptedStream.close();
                }
                Response ress = newResponse.build();

                long requestedTime = ress.sentRequestAtMillis();
                long respondedTime = ress.receivedResponseAtMillis();

                String url_type = String.valueOf(ress.request().url());

                if (url_type.contains("googleapis")) {
                    if (decrypted != null)
                        mRepository.createApiLog(decrypted, to_encode, url_type, requestedTime, respondedTime);
                }
                try {
                    if (url_type.contains("passengerapi")) {
                        if (new CheckStatus(new JSONObject(decrypted), c).isNormal())
                            return ress;
                        else
                            return response;
                    } else return ress;
                } catch (Exception e) {
                    e.printStackTrace();
                    return ress;
                }
            }
            return response;
        }
    }

    /**
     * this class converts the url to base 64
     */

    public static class Base64EncodeRequestInterceptor implements Interceptor {
        String companyKey = "FNpfuspyEAzhjfoh2ONpWK0rsnClVL6OCaasqDQtWdI=";
        private Context context;

        Base64EncodeRequestInterceptor(String key, Context context) {
            this.context = context;
            if (!key.trim().isEmpty())
                companyKey = key;
        }

        @NotNull
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder();

            if (originalRequest.method().equalsIgnoreCase("POST")) {
                builder = originalRequest.newBuilder()
                        .method(originalRequest.method(), originalRequest.body());
            }
            builder.addHeader("authkey", SessionSave.getSession(TaxiUtil.AUTH_KEY, context));
            builder.addHeader("userAuth", SessionSave.getSession(TaxiUtil.USER_KEY, context));

            System.out.println("ok authkey : " + " " + SessionSave.getSession(TaxiUtil.AUTH_KEY, context));
            System.out.println("ok userauth : " + " " + SessionSave.getSession(TaxiUtil.USER_KEY, context));
            HttpUrl originalHttpUrl = originalRequest.url();
            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("dt", "a")
                    .addQueryParameter("i", SessionSave.getSession(PASS_ID, context))
                    .addQueryParameter("pv", "" + BuildConfig.VERSION_CODE)
                    .build();

            builder.url(url.toString().replace("%26", "&"));
            String body_value = "";
            try {

                if (originalRequest.body() != null) {
                    final RequestBody body = originalRequest.body();
                    final Buffer buffer = new Buffer();
                    if (body != null)
                        body.writeTo(buffer);
                    body_value = buffer.readUtf8();
                }

            } catch (final IOException e) {
            }
            String logs = url + " - " + originalRequest.method() + " - " + body_value + " - auth: " + SessionSave.getSession(TaxiUtil.AUTH_KEY, context) + " - user_auth: " + SessionSave.getSession(TaxiUtil.USER_KEY, context);
            SessionSave.saveAPI(logs, context);
            return chain.proceed(builder.build());
        }

    }


}