package com.taximobility.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import androidx.core.content.ContextCompat;
import android.util.Log;

import com.taximobility.BuildConfig;
import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.ReceiptAct;
import com.taximobility.data.SplitStatusData;
import com.taximobility.fragments.SplitFareStatusDialog;
import com.taximobility.interfaces.APIResult;
import com.taximobility.interfaces.GetPassUpdate;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.taximobility.util.ConstantsKt.LANG;
import static com.taximobility.util.ConstantsKt.PASS_ID;

/**
 * @author developer This class used to get the trip status as notification. Continuously hit the server with fixed time interval and get the status about ongoing trip, generate that as notification.Based on the trip status this will redirect the control to different activity.
 */
public class GetPassengerUpdate extends Service {
    public static MainHomeFragmentActivity context;
    static GetPassUpdate getPassUpdateListener;
    private final Timer mTimer = new Timer();
    public boolean cleardroplocation = false;
    int getdetailtimer = 1000 * 4;
    String result;
    Handler handler;
    String Dlocationname = "";
    Double Dlat = 0.0;
    Double Dlon = 0.0;
    private int mStatus;
    private NotificationManager notificationManager;
    private String driver_latitute;
    private String driver_longtitute;
    private String msg = "";
    private String driver_id = "";

    public static void setListener(GetPassUpdate listener) {
        getPassUpdateListener = listener;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            if (intent != null) {
                Bundle b = intent.getExtras();
                if (b != null) {
                    Dlocationname = b.getString("Dlname");
                    Dlat = b.getDouble("Dlatitude");
                    Dlon = b.getDouble("Dlongitude");
                } else {
                    Dlocationname = "";
                    Dlat = 0.0;
                    Dlon = 0.0;
                }
            }
        } catch (Exception e) {
            Dlocationname = "";
            Dlat = 0.0;
            Dlon = 0.0;
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        mTimer.scheduleAtFixedRate(new getpassengerupdates(), 0, getdetailtimer);
    }

    @Override
    public void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }

    private void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_launcher : R.drawable.ic_launcher;
    }

    @SuppressWarnings("deprecation")
    public void generateNotification(Context context, final String message, Class<?> class1, final int trip_id) {
        int tripId = 0;
        if (trip_id != -1) {
            tripId = trip_id;
        } else {
            if (!SessionSave.getSession("trip_id", context).equals(""))
                tripId = Integer.parseInt(SessionSave.getSession("trip_id", context));
        }
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(this, class1);

        notificationIntent.putExtra("CURRENT", tripId);
        notificationIntent.putExtra("alert_message", message);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, tripId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        String NOTIFICATION_CHANNEL_ID = String.valueOf(tripId);
        Notification.Builder builder = null;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);

            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);

            notificationManager.createNotificationChannel(notificationChannel);

            builder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }

        builder.setAutoCancel(false);
        builder.setTicker(message);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.driver_notification_icon);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(((BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher)).getBitmap());
        builder.build();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            System.out.println("Android version : "+Build.VERSION.SDK_INT);
            builder.setSmallIcon(R.drawable.driver_notification_icon);
            builder.setColor(ContextCompat.getColor(getBaseContext(), R.color.button_accept));
        } else {
            builder.setSmallIcon(R.drawable.driver_notification_icon);
        }

        Notification myNotication = builder.getNotification();

        notificationManager.notify(tripId, myNotication);
        Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        try {
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification1);
            r.play();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    class MyBinder extends Binder {

        GetPassengerUpdate getService() {
            return GetPassengerUpdate.this;
        }
    }

    private class getpassengerupdates extends TimerTask {
        @Override
        public void run() {
            try {
                Double drop_lat = 0.0, drop_lng = 0.0;
                TaxiUtil.API_BASE_URL = SessionSave.getSession("base_url", GetPassengerUpdate.this);
                if (!SessionSave.getSession("Drop_location_push", GetPassengerUpdate.this).equals("")) {
                    cleardroplocation = true;
                    try {
                        drop_lat = Double.parseDouble(SessionSave.getSession("Drop_location_latitude", GetPassengerUpdate.this));
                        drop_lng = Double.parseDouble(SessionSave.getSession("Drop_location_longitude", GetPassengerUpdate.this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                JSONObject j = new JSONObject();
                j.put("trip_id", SessionSave.getSession("trip_id", GetPassengerUpdate.this));
                j.put("request_type", "0");
                j.put("drop_lat", drop_lat);
                j.put("drop_long", drop_lng);
                j.put("drop_location_name", SessionSave.getSession("Drop_location_push", GetPassengerUpdate.this));
                j.put("passenger_id", SessionSave.getSession(PASS_ID, GetPassengerUpdate.this));
                j.put("multi_tripID", SessionSave.getSession("multi_tripID", GetPassengerUpdate.this));
                j.put("driver_id", SessionSave.getSession("Driver_id", GetPassengerUpdate.this));
                Log.e("LocationUpdate: ", j.toString());
                String url1 = "type=getpassenger_update";
                String url = TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession(LANG, GetPassengerUpdate.this) + "&" + url1 + "&encode=" + TaxiUtil.DYNAMIC_AUTH_KEY + "&stable_version=" + BuildConfig.VERSION_CODE + "&tmrelease=23_08_2018";
                Log.v("Pass service response", "" + url + "___" + j.toString());

                if (!SessionSave.getSession("trip_id", GetPassengerUpdate.this).trim().equals(""))
                    new Getpassenger(url1, j);
                else {
                    SessionSave.saveSession("Driver_id", "", GetPassengerUpdate.this);
                    driver_id = "";
                    System.out.println("Driver_id is set to null.." + SessionSave.getSession("Driver_id", GetPassengerUpdate.this));
                    GetPassengerUpdate.this.stopSelf();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    public class Getpassenger implements APIResult {

        public Getpassenger(String Url, JSONObject j) {
            // TODO Auto-generated method stub
            result = "";
            try {
                new APIService_Retrofit_JSON(GetPassengerUpdate.this, this, j, false).execute(Url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, String result) {
            if (result != null && result.length() > 0) {
                try {
                    JSONObject jsons = new JSONObject(result);

                    if (jsons.getInt("status") == 1) {
                        if (jsons.has("multi_trips")) {
                            if (jsons.has("multi_tripID")) {
                                JSONArray multi_tripID = jsons.getJSONArray("multi_tripID");
                                String arrayString = "";
                                boolean intrip = false;
                                for (int y = 0; y < multi_tripID.length(); y++) {

                                    if (SessionSave.getSession("trip_id", GetPassengerUpdate.this).equals(multi_tripID.getString(y)))
                                        intrip = true;

                                    arrayString += multi_tripID.getString(y) + (y != multi_tripID.length() - 1 ? "," : "");
                                }
                                if (!intrip)
                                    SessionSave.saveSession("trip_id", multi_tripID.getString(0), GetPassengerUpdate.this);
                                SessionSave.saveSession("multi_tripID", arrayString, GetPassengerUpdate.this);
                            } else {
                                SessionSave.saveSession("multi_tripID", "", GetPassengerUpdate.this);
                                SessionSave.saveSession("trip_id", "", GetPassengerUpdate.this);
                            }
                            JSONArray jsonArray = jsons.getJSONArray("multi_trips");
                            for (int n = 0; n < jsonArray.length(); n++) {

                                JSONObject json = jsonArray.getJSONObject(n);
                                Log.e("GetPassengerUpdate ", json.toString());
                                moveToTripUpdate(json, true);

                                if (json.has("tips_array")&& json.getJSONObject("tips_array").getInt("tips_enable")==1){
                                    getPassUpdateListener.updateTips(json.getJSONObject("tips_array"));

                                }

                            }



                        }

                    } else if (jsons.getInt("status") == -1) {
                        cancelTripAndRedirect(msg);
                    }
                    if (!jsons.has("multi_trips") && jsons.getInt("status") != -1)
                        moveToTripUpdate(jsons, false);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }

        private void moveToTripUpdate(JSONObject json, boolean isFromNew) {
            try {
                mStatus = json.getInt("status");


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Dlocationname = "";
                                Dlat = 0.0;
                                Dlon = 0.0;
                                if (cleardroplocation) {
                                    SessionSave.saveSession("Drop_location_push", "", GetPassengerUpdate.this);
                                    SessionSave.saveSession("Drop_location_latitude", "", GetPassengerUpdate.this);
                                    SessionSave.saveSession("Drop_location_longitude", "", GetPassengerUpdate.this);
                                    cleardroplocation = false;
                                }
                            }
                        }, 1000);
                    }
                });

                if (json.has("driver_id")) {
                    driver_id = json.getString("driver_id");
                    System.out.println("Driver_id setted : " + driver_id);
                    SessionSave.saveSession("Driver_id", driver_id, GetPassengerUpdate.this);
                }
                msg = json.getString("message");
                int trip_id = -1;
                if (json.has("trip_id"))
                    trip_id = Integer.parseInt(json.getString("trip_id"));
                else if (!SessionSave.getSession("trip_id", GetPassengerUpdate.this).equals(""))
                    trip_id = Integer.parseInt(SessionSave.getSession("trip_id", GetPassengerUpdate.this));
                Systems.out.println("NAN GetPassengerUpdate" + mStatus);
                Systems.out.println("status___*****" + mStatus + "__" + SessionSave.getSessionInt(TaxiUtil.CURRENT_TRIP, GetPassengerUpdate.this) + "__" + trip_id);
                if (json.has("driver_latitute") && trip_id != -1) {

                    if (SessionSave.getSessionInt(TaxiUtil.CURRENT_TRIP, GetPassengerUpdate.this) == trip_id) {
                        if (json.has("driver_latitute")) {
                            driver_latitute = json.getString("driver_latitute");
                            driver_longtitute = json.getString("driver_longtitute");
                            Systems.out.println("curentDrivertrip___*****" + driver_latitute + "__" + driver_longtitute + "__" + trip_id);
                            SessionSave.saveSession("driver_latitute", driver_latitute, GetPassengerUpdate.this);
                            SessionSave.saveSession("driver_longtitute", driver_longtitute, GetPassengerUpdate.this);

                            if (context != null) {
                                (context).driverLocationUpdate(Double.parseDouble(driver_latitute), Double.parseDouble(driver_longtitute));
                            }

                            try {
                                if (json.has("isSplit_fare"))
                                    if (json.getInt("isSplit_fare") == 1) {
                                        TaxiUtil.SPLIT_STATUS_ITEM.clear();
                                        JSONArray splitArray = json.getJSONArray("splitfaredetail");
                                        for (int i = 0; i < splitArray.length(); i++) {
                                            JSONObject SplitArrayData = splitArray.getJSONObject(i);
                                            SplitStatusData obj = new SplitStatusData(getApplicationContext());
                                            obj.setImage(SplitArrayData.getString("profile_image"));
                                            obj.setName(SplitArrayData.getString("firstname"));
                                            obj.setStatus(SplitArrayData.getString("approve_status"));
                                            TaxiUtil.SPLIT_STATUS_ITEM.add(obj);
                                        }
                                        if (SplitFareStatusDialog.rv != null)
                                            if (SplitFareStatusDialog.rv.getAdapter() != null)
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        SplitFareStatusDialog.rv.getAdapter().notifyDataSetChanged();
                                                    }
                                                });

                                    }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    GetPassengerUpdate.this.stopSelf();
                    Intent home = new Intent();
                    Bundle extras = new Bundle();
                    extras.putString("alert_message", msg);
                    home.putExtras(extras);
                    home.setAction(Intent.ACTION_MAIN);
                    home.addCategory(Intent.CATEGORY_LAUNCHER);
                    home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    ComponentName cn = new ComponentName(GetPassengerUpdate.this, MainHomeFragmentActivity.class);
                    home.setComponent(cn);
                    startActivity(home);
                }
                if (json.has("display")) {
                    if (json.getString("display").equals("1")) {
                        if (mStatus == 3) {

                            if (json.has("trip_id")) {
                                generateNotification(GetPassengerUpdate.this, json.getString("message"), MainHomeFragmentActivity.class, Integer.parseInt(json.getString("trip_id")));
                            } else {
                                generateNotification(GetPassengerUpdate.this, json.getString("message"), MainHomeFragmentActivity.class, -1);
                            }
                            Intent ongoing = new Intent();
                            Bundle extras = new Bundle();
                            extras.putString("alert_message", msg);
                            ongoing.putExtras(extras);
                            ongoing.setAction(Intent.ACTION_MAIN);
                            ongoing.addCategory(Intent.CATEGORY_LAUNCHER);
                            ongoing.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            ComponentName cn = new ComponentName(GetPassengerUpdate.this, MainHomeFragmentActivity.class);
                            ongoing.setComponent(cn);

                            if (getPassUpdateListener != null) {
                                Systems.out.println("updateGetPassUpdate*****1" + "____" + mStatus);
                                getPassUpdateListener.updateGetPassUpdate(trip_id, json.getString("message"));
                            }
                        } else if (mStatus == 4) {
                            if (json.has("trip_id")) {
                                generateNotification(GetPassengerUpdate.this, json.getString("message"), MainHomeFragmentActivity.class, Integer.parseInt(json.getString("trip_id")));
                            } else {
                                generateNotification(GetPassengerUpdate.this, json.getString("message"), MainHomeFragmentActivity.class, -1);
                            }
                            Intent home = new Intent();
                            Bundle extras = new Bundle();
                            extras.putString("alert_message", msg);
                            home.putExtras(extras);
                            home.setAction(Intent.ACTION_MAIN);
                            home.addCategory(Intent.CATEGORY_LAUNCHER);
                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            ComponentName cn = new ComponentName(GetPassengerUpdate.this, MainHomeFragmentActivity.class);
                            home.setComponent(cn);

                            if (getPassUpdateListener != null) {
                                Systems.out.println("updateGetPassUpdate*****1" + "____" + mStatus);
                                getPassUpdateListener.updateGetPassUpdate(trip_id, json.getString("message"));
                            }
                        } else if (mStatus == 5) {
                            if (!isFromNew) {
                                SessionSave.saveSession("trip_id", "", GetPassengerUpdate.this);
                            }
                            SessionSave.saveSessionInt(TaxiUtil.SKIP_PAST_BOOKING, 1, GetPassengerUpdate.this);
                            SessionSave.saveSession("receipt_details", new Gson().toJson(json), GetPassengerUpdate.this);
                            Intent home = new Intent();
                            Systems.out.println("haiiiMessage" + new Gson().toJson(json) + "____" + json.toString());
                            home.putExtra("Message", json.toString());
                            Bundle extras = new Bundle();
                            extras.putString("trip_id", "" + trip_id);
                            extras.putString("alert_message", msg);
                            home.putExtras(extras);

                            home.setAction(Intent.ACTION_MAIN);
                            home.addCategory(Intent.CATEGORY_LAUNCHER);
                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            ComponentName cn = new ComponentName(GetPassengerUpdate.this, ReceiptAct.class);
                            home.setComponent(cn);
                            getApplication().startActivity(home);

                            if (getPassUpdateListener != null) {
                                Systems.out.println("updateGetPassUpdate*****1" + "____" + mStatus);
                                getPassUpdateListener.updateGetPassUpdate(Integer.parseInt(json.getString("trip_id")), json.getString("message"));
                            }

                            try {
                                Systems.out.println("vvvvvv_____" + SessionSave.getSession("trip_id", GetPassengerUpdate.this));
                                ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(Integer.parseInt(json.getString("trip_id")));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        } else if (mStatus == 8 || mStatus == 9 || mStatus == -1) {
                            if (json.has("trip_id")) {
                                generateNotification(GetPassengerUpdate.this, json.getString("message"), MainHomeFragmentActivity.class, Integer.parseInt(json.getString("trip_id")));
                            } else {
                                generateNotification(GetPassengerUpdate.this, json.getString("message"), MainHomeFragmentActivity.class, -1);
                            }
                            cancelTripAndRedirect(msg);
                            GetPassengerUpdate.this.stopSelf();
                            Intent home = new Intent();
                            Bundle extras = new Bundle();
                            extras.putString("alert_message", msg);
                            home.putExtras(extras);
                            home.setAction(Intent.ACTION_MAIN);
                            home.addCategory(Intent.CATEGORY_LAUNCHER);
                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            ComponentName cn = new ComponentName(GetPassengerUpdate.this, MainHomeFragmentActivity.class);
                            home.setComponent(cn);
                            startActivity(home);
                            try {
                                Systems.out.println("vvvvvv_____@" + msg + "__" + SessionSave.getSession("trip_id", GetPassengerUpdate.this));
                                ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(Integer.parseInt(json.getString("trip_id")));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        } else if (mStatus == 10) {
                            if (json.has("trip_id")) {
                                generateNotification(GetPassengerUpdate.this, json.getString("message"), MainHomeFragmentActivity.class, Integer.parseInt(json.getString("trip_id")));
                            } else {
                                generateNotification(GetPassengerUpdate.this, json.getString("message"), MainHomeFragmentActivity.class, 0);
                            }
                            cancelTripAndRedirect(msg);
                            GetPassengerUpdate.this.stopSelf();
                            Intent home = new Intent();
                            Bundle extras = new Bundle();
                            extras.putString("alert_message", msg);
                            home.putExtras(extras);
                            home.setAction(Intent.ACTION_MAIN);
                            home.addCategory(Intent.CATEGORY_LAUNCHER);
                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            ComponentName cn = new ComponentName(GetPassengerUpdate.this, MainHomeFragmentActivity.class);
                            home.setComponent(cn);
                            startActivity(home);
                            try {
                                Systems.out.println("vvvvvv_____!" + SessionSave.getSession("trip_id", GetPassengerUpdate.this));
                                ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(Integer.parseInt(json.getString("trip_id")));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (json.has("trip_id")) {
                                generateNotification(GetPassengerUpdate.this, json.getString("message"), MainHomeFragmentActivity.class, Integer.parseInt(json.getString("trip_id")));
                            } else {
                                generateNotification(GetPassengerUpdate.this, json.getString("message"), MainHomeFragmentActivity.class, -1);
                            }

                            Intent home = new Intent();
                            home.putExtra("Message", result);
                            Bundle extras = new Bundle();
                            extras.putString("alert_message", msg);
                            home.putExtras(extras);
                            home.setAction(Intent.ACTION_MAIN);
                            home.addCategory(Intent.CATEGORY_LAUNCHER);
                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            ComponentName cn = new ComponentName(GetPassengerUpdate.this, MainHomeFragmentActivity.class);
                            home.setComponent(cn);
                            if (getPassUpdateListener != null) {
                                Systems.out.println("updateGetPassUpdate*****1" + "____" + mStatus);
                                getPassUpdateListener.updateGetPassUpdate(Integer.parseInt(json.getString("trip_id")), json.getString("message"));
                            }

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void cancelTripAndRedirect(String msg) {
        SessionSave.saveSession("multi_tripID", "", GetPassengerUpdate.this);
        SessionSave.saveSession("trip_id", "", GetPassengerUpdate.this);
        stopSelf();
        if (getPassUpdateListener != null) {
            Intent home = new Intent();
            Bundle extras = new Bundle();
            extras.putString("alert_message", msg);
            home.putExtras(extras);
            home.setAction(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_LAUNCHER);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            ComponentName cn = new ComponentName(GetPassengerUpdate.this, MainHomeFragmentActivity.class);
            home.setComponent(cn);
            startActivity(home);
        }
    }

}
