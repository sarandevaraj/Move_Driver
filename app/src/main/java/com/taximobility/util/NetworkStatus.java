package com.taximobility.util;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mayan.sospluginmodlue.service.SOSService;
import com.taximobility.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.taximobility.util.ConstantsKt.PASS_ID;

/**
 * This class is used to get the network status when it is enable/disable.
 */
public class NetworkStatus extends BroadcastReceiver {
    private static Context mContext;
    public static Context appContext;
    private String message;
    public static Dialog errorDialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.requireNonNull(intent.getAction()).matches("android.net.conn.CONNECTIVITY_CHANGE")) {

            mContext = context;
            try {
                Systems.out.println("_____________netChange onReceive");
                if (isOnline(mContext)) {
                    Systems.out.println("_____________netChange*$");
                    Intent i = new Intent(TaxiUtil.ACTIVITY_ACTION);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(i);
                } else {
                    Systems.out.println("_____________netChange*!!");
                    DivertToNoInternetScreen();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    public static boolean isOnline(Context mContext2) {
        if (mContext2 != null) {
            ConnectivityManager connectivity = (ConnectivityManager) mContext2.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            CloseNoInternetScreen();
                            return true;
                        }
            }
        }
        return false;
    }

    private static class URLReachable extends AsyncTask<URL, Boolean, Boolean> {
        Context mContext;

        URLReachable(Context mContext) {
            this.mContext = mContext;
        }

        protected Boolean doInBackground(URL... urls) {
            try {
                URL url = new URL("http://google.com");   // Change to "http://google.com" for www  test.
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(10 * 1000);
                urlc.setReadTimeout(10 * 1000);
                // 10 s.

                urlc.connect();
                if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                    Log.wtf("Connection", "Success !");
                    return true;
                } else {
                    return false;
                }
            } catch (MalformedURLException e1) {
                return false;
            } catch (IOException e) {
                return false;
            }
        }


        protected void onPostExecute(Boolean result) {
            Systems.out.println("connection_reachable " + result);
        }
    }


    static public boolean isURLReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL("http://google.com");   // Change to "http://google.com" for www  test.
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(10 * 1000);
                // 10 s.

                urlc.connect();
                if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                    Log.wtf("Connection", "Success !");
                    return true;
                } else {
                    return false;
                }
            } catch (MalformedURLException e1) {
                return false;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean getConnectivityStatus(Context context) {
        boolean conn = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                conn = activeNetwork.isConnected();
            }
        } else {
            conn = false;
        }
        return conn;
    }

    private void errorInSplash(String message) {
        try {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (appContext != null && appContext instanceof AppCompatActivity && !((AppCompatActivity) appContext).isFinishing()) {
                        if (errorDialog != null && errorDialog.isShowing()) {
                            errorDialog.dismiss();
                        }
                        final View view = View.inflate(appContext, R.layout.no_internet_lay, null);
                        errorDialog = new Dialog(appContext, R.style.Theme_Transparent1);
                        errorDialog.setContentView(view);
                        errorDialog.setCancelable(false);
                        errorDialog.setCanceledOnTouchOutside(false);
                        Window window = errorDialog.getWindow();
                        window.setGravity(Gravity.TOP);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(Color.BLACK);
                        }
                        AppCompatButton btn_emergency = errorDialog.findViewById(R.id.btn_emergency);
                        if (SessionSave.getSession(TaxiUtil.sosEnable, appContext, false)) {
                            btn_emergency.setVisibility(View.VISIBLE);
                        }
                        btn_emergency.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startSOSService(appContext);
                            }
                        });
                        errorDialog.show();
                    } else {
                        try {
                            errorDialog.dismiss();
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                }
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void DivertToNoInternetScreen() {
        if (mContext != null)
            errorInSplash(mContext.getString(R.string.check_internet_connection));
    }

    private static void CloseNoInternetScreen() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (errorDialog != null && errorDialog.isShowing()) {

                    errorDialog.dismiss();
                }
            }
        });

    }

    private void startSOSService(Context context) {
        if (!SessionSave.getSession("trip_id", context).equals("") && !SessionSave.getSession(PASS_ID, context).equals("")) {
            SessionSave.saveSession("sos_id", SessionSave.getSession(PASS_ID, context), context);
            SessionSave.saveSession("user_type", "p", context);
            context.startService(new Intent(context, SOSService.class));
        }
    }
}
