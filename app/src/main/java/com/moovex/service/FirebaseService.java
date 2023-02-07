package com.moovex.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.moovex.ChatWebviewAct;
import com.moovex.R;
import com.moovex.SplashActivity;
import com.moovex.data.apiData.PromoDataList;
import com.moovex.driver.DriverCanceltripAct;
import com.moovex.driver.DriverChatWebviewAct;
import com.moovex.driver.DriverMyStatus;
import com.moovex.driver.DriverNotificationAct;
import com.moovex.driver.DriverOngoingAct;
import com.moovex.driver.DriverSplashAct;
import com.moovex.driver.DriverUserLoginAct;
import com.moovex.driver.MainActivityDriver;
import com.moovex.driver.data.DriverCommonData;
import com.moovex.driver.data.apiData.DriverDetailInfo;
import com.moovex.driver.errorLog.DriverApiErrorModel;
import com.moovex.driver.errorLog.DriverErrorLogRepository;
import com.moovex.driver.service.DriverCoreClient;
import com.moovex.driver.service.LocationUpdate;
import com.moovex.driver.utils.DriverExceptionConverter;
import com.moovex.driver.utils.DriverNC;
import com.moovex.driver.utils.DriverSessionSave;
import com.moovex.driver.utils.DriverSystems;
import com.moovex.driver.utils.DriverUtils;
import com.moovex.util.AppController;
import com.moovex.util.SessionSave;
import com.moovex.util.TaxiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by developer on 30/8/17.
 */

public class FirebaseService extends FirebaseMessagingService {
    public static final int NOTIFICATION_ID = 123;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public static DriverMyStatus MAIN_ACT;
    public static AppCompatActivity activity;
    private JSONObject jo;
    public static MainActivityDriver MAIN_ACT_D;
    public static final int BOOKLATER_NOTIFICATION_ID = 123;
    Notification.Builder builderD;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        DriverSystems.out.println("MyFirebaseIIDServices" + "onNewToken");
        if (s != null && !TextUtils.isEmpty(s)) {
            DriverSystems.out.println("MyFirebaseIIDServices" + "__________" + s);
            SessionSave.saveSession(TaxiUtil.DEVICE_TOKEN, s, this);
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (SessionSave.getSession("user_type", FirebaseService.this).equalsIgnoreCase("p")) {
            onHandleIntent(remoteMessage);
        } else {
            onHandleIntentD(remoteMessage);
        }
        // Check if message contains a data payload.
        DriverSystems.out.println("MyFirebaseIIDServices" + remoteMessage.getData());
        if (remoteMessage.getData().size() > 0) {
            Log.d("MyFirebaseIIDService", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("sssss", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


    protected void onHandleIntent(RemoteMessage remoteMessage) {// Handling gcm message from
        String messageType = remoteMessage.getMessageType();
        String message = "";
        try {
            message = remoteMessage.getData().get("message");
        } catch (Exception e) {
            e.printStackTrace();
        }
        DriverSystems.out.println("MyFirebaseIIDServices" + message);
        if (!TextUtils.isEmpty(message)) {
            Log.i("", "Received: " + message);
            JSONObject jos;
            try {
                if (message != null) {
                    jos = new JSONObject(message);

                    if (jos.getString("status").equals("55")) {
                        if (!(FirebaseService.activity instanceof ChatWebviewAct)) {
                            String type = "";
                            JSONObject json = new JSONObject(message);
                            sendNotification(json.getString("message"));
                            type = json.getString("type");
                            Intent in = new Intent();
                            in.putExtra("type", "2");
                            in.putExtra("Id", json.getString("chat_id"));
                            in.putExtra("chat_type", json.getString("chat_type"));
                            in.putExtra("to_type", json.getString("to_type"));
                            in.setAction(Intent.ACTION_MAIN);
                            in.addCategory(Intent.CATEGORY_LAUNCHER);
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                            ComponentName cn = new ComponentName(getApplicationContext(), ChatWebviewAct.class);
                            in.setComponent(cn);
                            getApplication().startActivity(in);
                        }
                    } else {
                        generateNotification(this, message, DriverMyStatus.class);
                    }

                    if (jos.getString("status") != null) {
                        if (jos.getString("status").trim().equals("21")) {
                            DriverSystems.out.println("VVVVVVVVV*_" + SessionSave.getSession(TaxiUtil.PROMO_LIST, this).trim());
                            PromoDataList promoDataLists = null;
                            if (!SessionSave.getSession(TaxiUtil.PROMO_LIST, this).trim().equals(""))
                                promoDataLists = TaxiUtil.fromJson(SessionSave.getSession(TaxiUtil.PROMO_LIST, this), PromoDataList.class);
                            if (promoDataLists == null) promoDataLists = new PromoDataList();
                            PromoDataList.PromoData obj = promoDataLists.getPromoData();
                            obj.setMessage(jos.getString("message"));
                            obj.setStatus(jos.getString("title"));
                            obj.setExpiry_date(jos.getLong("expiry_date"));
                            promoDataLists.promoDatas.add(obj);
                            DriverSystems.out.println("VVVVVVVVV***_" + SessionSave.getSession(TaxiUtil.PROMO_LIST, this).trim());
                            SessionSave.saveSession(TaxiUtil.PROMO_LIST, TaxiUtil.toString(promoDataLists), this);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                DriverSystems.out.println("VVVVVVVVV*****" + e.getLocalizedMessage());
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void generateNotification(Context context, String message, Class<?> class1) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Notification notification = new Notification(R.drawable.app_icon, message, System.currentTimeMillis());
        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(this, SplashActivity.class);
        notificationIntent.putExtra("GCMnotification", message);
        SessionSave.saveSession("GCMnotification", message, context);

        int requestID = (int) System.currentTimeMillis();
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        String Message = "";

        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
        Notification.Builder builder;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            mNotificationManager.createNotificationChannel(notificationChannel);

            builder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }
        try {
            jo = new JSONObject(message);

            builder.setAutoCancel(false);
            builder.setOngoing(true);
            if (jo.has("passenger_name"))
                Message = DriverNC.getString(R.string.z_split_fare_with) + " " + jo.getString("passenger_name");
            else {
                Message = jo.getString("message");
                SessionSave.saveSession("GCMnotificationPopup", Message, context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        builder.setTicker(context.getString(R.string.app_name));
        builder.setContentTitle(title);
        builder.setContentText(Message);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setColor(ContextCompat.getColor(getBaseContext(), R.color.button_accept));
        } else {
            builder.setSmallIcon(R.drawable.ic_launcher);
        }
        builder.setContentIntent(pendingIntent);

        builder.setLargeIcon(((BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher)).getBitmap());
        builder.build();
        Notification myNotication = builder.getNotification();
        myNotication.flags |= Notification.FLAG_AUTO_CANCEL;
        DriverSystems.out.println("_______ssss1" + MAIN_ACT);
        mNotificationManager.notify(NOTIFICATION_ID, myNotication);
        DriverSystems.out.println("_______ssss2" + MAIN_ACT);
        Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        try {
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification1);
            r.play();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        try {
            if (jo.getString("status").equals("111") || jo.getString("status").equals("112")) {
                Intent intent = new Intent("TipsStatus");
                intent.putExtra("isTipsGiven", jo.getString("status").trim().equalsIgnoreCase("111"));
                intent.putExtra("msg", jo.getString("message"));
                intent.putExtra("tips_amount", jo.getString("tips_amount"));
                intent.putExtra("trip_id", jo.getString("trip_id"));
                sendBroadcast(intent);
            } else if (MAIN_ACT != null && !jo.getString("status").equals("21")) {
                DriverSystems.out.println("__________VVVVVVV");
                Intent home = new Intent();
                Bundle extras = new Bundle();
                try {
                    extras.putString("alert_message", jo.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                home.putExtras(extras);
                home.setAction(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_LAUNCHER);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ComponentName cn = new ComponentName(FirebaseService.this, DriverMyStatus.class);
                home.setComponent(cn);
                getApplication().startActivity(home);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(String msg) {
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_10";
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, DriverMyStatus.class), PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).setSmallIcon(R.drawable.ic_launcher).setContentTitle("TaxiMobility").setStyle(new NotificationCompat.BigTextStyle().bigText(msg)).setContentText(msg);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        try {
            Ringtone r = RingtoneManager.getRingtone(getBaseContext(), notification1);
            r.play();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    protected void onHandleIntentD(RemoteMessage remoteMessage) {// Handling gcm message from
        String message;
        String unique = "";
        try {
            message = remoteMessage.getData().get("message");
            if (message != null && !message.isEmpty()) {
                JSONObject jsonObject = new JSONObject(message);
                if (jsonObject.getString("status").equals("14")) {
                    if (jsonObject.getString("display").equals("1")) {
                        if (jsonObject.has("message")) {
                            generateNotificationD(this, message, DriverUserLoginAct.class);// to handle book later schedule
                        }
                    }
                } else if (jsonObject.getString("status").equals("15")) {

                    DriverSessionSave.saveSession("status", "", getApplicationContext());
                    DriverSessionSave.saveSession("Id", "", getApplicationContext());
                    DriverSessionSave.saveSession("Driver_locations", "", getApplicationContext());
                    DriverSessionSave.saveSession("driver_id", "", getApplicationContext());
                    DriverSessionSave.saveSession("Name", "", getApplicationContext());
                    DriverSessionSave.saveSession("company_id", "", getApplicationContext());
                    DriverSessionSave.saveSession("bookedby", "", getApplicationContext());
                    DriverSessionSave.saveSession("p_image", "", getApplicationContext());
                    DriverSessionSave.saveSession("Email", "", getApplicationContext());
                    DriverSessionSave.saveSession("trip_id", "", getApplicationContext());
                    DriverSessionSave.saveSession("phone_number", "", getApplicationContext());
                    DriverSessionSave.saveSession("driver_password", "", getApplicationContext());
                    DriverSessionSave.saveSession("shift_status", "", getApplicationContext());
                    DriverSessionSave.setWaitingTime(0L, getApplicationContext());

                    if (MAIN_ACT != null) {
                        Intent i = new Intent(MAIN_ACT, DriverUserLoginAct.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(getApplicationContext(), DriverUserLoginAct.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                    generateNotificationD(this, message, DriverUserLoginAct.class);
                } else if (jsonObject.getInt("status") == 7 || jsonObject.getInt("status") == 10) {
                    String cancelmsg;
                    cancelmsg = jsonObject.getString("message");
                    if (cancelmsg.contains("_")) {
                        cancelmsg = DriverNC.getString(R.string.trip_cancelled);
                    }
                    MainActivityDriver.mMyStatus.setStatus("F");
                    DriverSessionSave.saveSession("status", "F", getApplicationContext());
                    MainActivityDriver.mMyStatus.settripId("");
                    DriverSessionSave.saveSession("trip_id", "", getApplicationContext());
                    DriverSessionSave.setWaitingTime(0L, getApplicationContext());
                    MainActivityDriver.mMyStatus.setOnstatus("");
                    MainActivityDriver.mMyStatus.setOnPassengerImage("");
                    MainActivityDriver.mMyStatus.setOnpassengerName("");
                    MainActivityDriver.mMyStatus.setOndropLocation("");
                    MainActivityDriver.mMyStatus.setOnpickupLatitude("");
                    MainActivityDriver.mMyStatus.setOnpickupLongitude("");
                    MainActivityDriver.mMyStatus.setOndropLatitude("");
                    MainActivityDriver.mMyStatus.setOndropLongitude("");
                    MainActivityDriver.mMyStatus.setOndriverLatitude("");
                    MainActivityDriver.mMyStatus.setOndriverLongitude("");
                    LocationUpdate.ClearSessionwithTrip(getApplicationContext());
                    DriverSessionSave.saveSession(DriverCommonData.ST_WAITING_TIME, false, getApplicationContext());
                    DriverSessionSave.saveSession(DriverCommonData.WAITING_TIME, false, getApplicationContext());
                    Intent cancelIntent = new Intent();
                    Bundle bun = new Bundle();
                    bun.putString("message", cancelmsg);
                    cancelIntent.putExtras(bun);
                    cancelIntent.setAction(Intent.ACTION_MAIN);
                    cancelIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    cancelIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    ComponentName cn = new ComponentName(getApplicationContext(), DriverCanceltripAct.class);
                    cancelIntent.setComponent(cn);
                    startActivity(cancelIntent);

                } else if (jsonObject.getString("status").equals("99")) {
                    JSONObject json = new JSONObject(message);
                    sendNotification(json.getString("message"));
                    Intent ongoing = new Intent();
                    Bundle extras = new Bundle();
                    String lTaximobilityutlmsg;
                    lTaximobilityutlmsg = json.getString("message");
                    extras.putString("alert_message", lTaximobilityutlmsg);
                    extras.putString("status", json.getString("status"));
                    ongoing.putExtras(extras);
                    ongoing.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    ComponentName cn = new ComponentName(getApplicationContext(), DriverOngoingAct.class);
                    ongoing.setComponent(cn);
                    getApplication().startActivity(ongoing);
                } else if (jsonObject.getString("status").equals("41")) {

                    sendInfo(getApplicationContext(), unique);

                    generateNotificationD(this, message, DriverSplashAct.class);
                    new Handler(Looper.getMainLooper()).post(() -> {
                        try {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (jsonObject.getString("status").equals("42")) {
                    sendInfo(getApplicationContext(), unique);
                    final Intent i = new Intent(getApplicationContext(), DriverSplashAct.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    DriverSessionSave.saveSession("need_animation", true, getApplicationContext());
                    generateNotificationD(this, message, DriverSplashAct.class);
                } else if (jsonObject.getString("status").equals("43")) {
                    sendInfo(getApplicationContext(), unique);

                } else if (jsonObject.getString("status").equals("44")) {
                    sendInfo(getApplicationContext(), unique);
                    generateNotificationD(this, message, DriverSplashAct.class);
                    final Intent i = new Intent(getApplicationContext(), LocationUpdate.class);
                    stopService(i);
                    if (!DriverSessionSave.getSession("driver_type", getApplicationContext()).equals("D") && !DriverSessionSave.getSession(DriverCommonData.SHIFT_OUT, getApplicationContext(), false) && !DriverSessionSave.getSession(DriverCommonData.LOGOUT, getApplicationContext(), false))
                        LocationUpdate.startLocationService(getApplicationContext());
                } /*else if (jsonObject.getString("status").equals("55")) {
                        JSONObject json = new JSONObject(message);
                        sendNotification(json.getString("message"));
                        Intent ongoing = new Intent();
                        Bundle extras = new Bundle();
                        String lTaximobilityutlmsg = "";
                        lTaximobilityutlmsg = json.getString("message");
                        extras.putString("alert_message", "");
                        extras.putString("status", json.getString("status"));
                        ongoing.putExtras(extras);
                        ongoing.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        ComponentName cn = new ComponentName(getApplicationContext(), DriverOngoingAct.class);
                        ongoing.setComponent(cn);
                        getApplication().startActivity(ongoing);
                    }*/ else if (jsonObject.getString("status").equals("55")) {
                    if (!(FirebaseService.activity instanceof DriverChatWebviewAct)) {
                        String type = "";
                        JSONObject json = new JSONObject(message);
                        sendNotification(json.getString("message"));
                        type = json.getString("type");
                        Intent in = new Intent();
                        in.putExtra("type", "2");
                        in.putExtra("Id", json.getString("chat_id"));
                        in.putExtra("chat_type", json.getString("chat_type"));
                        in.putExtra("to_type", json.getString("to_type"));
                        in.setAction(Intent.ACTION_MAIN);
                        in.addCategory(Intent.CATEGORY_LAUNCHER);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        ComponentName cn = new ComponentName(getApplicationContext(), DriverChatWebviewAct.class);
                        in.setComponent(cn);
                        getApplication().startActivity(in);


                    }

                } else if (jsonObject.getString("status").equals("111") || jsonObject.getString("status").equals("112")) {
                    Intent intent = new Intent("TipsStatus");
                    intent.putExtra("isTipsGiven", jsonObject.getString("status").trim().equalsIgnoreCase("111"));
                    intent.putExtra("msg", jsonObject.getString("message"));
                    intent.putExtra("tips_amount", jsonObject.getString("tips_amount"));
                    intent.putExtra("trip_id", jsonObject.getString("trip_id"));
                    DriverSessionSave.saveSession("tips_trip_id", jsonObject.getString("trip_id"), FirebaseService.this);
                    DriverSessionSave.saveSession("tips_amount", jsonObject.getString("tips_amount"), FirebaseService.this);
                    DriverSessionSave.saveSession("tips_fare", "", FirebaseService.this);
                    sendBroadcast(intent);
                    JSONObject json = new JSONObject(message);
                    sendNotification(json.getString("message"));
                } else generateNotificationD(this, message, DriverSplashAct.class);

            }
        } catch (Exception e) {
            e.printStackTrace();
            DriverErrorLogRepository.getRepository(getApplicationContext()).insertAllApiErrorLogs(new DriverApiErrorModel(0, DriverCommonData.getCurrentTimeForLogger(), "type=FirebaseService", DriverExceptionConverter.INSTANCE.buildStackTraceString(e.getStackTrace()), DriverUtils.INSTANCE.driverInfo(getApplicationContext()), null, getApplicationContext().getClass().getSimpleName(), 0));
        }
    }

    public void generateNotificationD(Context context, String message, Class<?> class1) {

        String Message;
        try {
            final JSONObject jo = new JSONObject(message);
            if (jo.getString("status").equals("25") || jo.getString("status").equals("15")) {
                DriverSystems.out.println("_________sddsfdsfdsfsll" + jo);
                DriverSessionSave.saveSession("status", "", FirebaseService.this);
                DriverSessionSave.saveSession("Id", "", FirebaseService.this);
                DriverSessionSave.saveSession("Driver_locations", "", FirebaseService.this);
                DriverSessionSave.saveSession("driver_id", "", FirebaseService.this);
                DriverSessionSave.saveSession("Name", "", FirebaseService.this);
                DriverSessionSave.saveSession("company_id", "", FirebaseService.this);
                DriverSessionSave.saveSession("bookedby", "", FirebaseService.this);
                DriverSessionSave.saveSession("p_image", "", FirebaseService.this);
                DriverSessionSave.saveSession("Email", "", FirebaseService.this);
                DriverSessionSave.saveSession("trip_id", "", FirebaseService.this);
                DriverSessionSave.saveSession("phone_number", "", FirebaseService.this);
                DriverSessionSave.saveSession("driver_password", "", FirebaseService.this);
                DriverSessionSave.setWaitingTime(0L, FirebaseService.this);
                Message = jo.getString("message");
                DriverSessionSave.saveSession(DriverCommonData.USER_KEY, "", FirebaseService.this);
                showNotification(context, Message, message);
                if (MAIN_ACT_D != null) {
                    Handler h = new Handler();
                    if (h != null) h.post(() -> MAIN_ACT_D.checkGCM());
                }
                Intent i = new Intent(FirebaseService.this, DriverUserLoginAct.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else if (jo.getString("status").equals("42") || jo.getString("status").equals("45") || jo.getString("status").equals("41") || jo.getString("status").equals("45") || jo.getString("status").equals("44")) {
                Message = jo.getString("message");
                showNotification(context, Message, message);
            } else if (jo.getString("status").equals("14")) {
                Message = jo.getString("message");
                Intent i = new Intent(FirebaseService.this, DriverMyStatus.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                i.putExtra("alert_message", message);
                i.putExtra("alert_schedule", "1");
                startActivity(i);
                showNotificationBookLater(context, Message, message, i);
            } else {
                if (DriverNotificationAct.notificationObject != null) {
                    DriverSystems.out.println("_________sddsfdsfdsfs");
                    if (jo.getString("status").equals("7") || jo.getString("status").equals("26")) {
                        DriverSessionSave.saveSession("trip_id", "", FirebaseService.this);
                    }
                    DriverNotificationAct.notificationObject.stopTimerAndNavigateToHome(jo.getString("message"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            DriverErrorLogRepository.getRepository(getApplicationContext()).insertAllApiErrorLogs(new DriverApiErrorModel(0, DriverCommonData.getCurrentTimeForLogger(), "type=FirebaseService", DriverExceptionConverter.INSTANCE.buildStackTraceString(e.getStackTrace()), DriverUtils.INSTANCE.driverInfo(getApplicationContext()), null, getApplicationContext().getClass().getSimpleName(), 0));
        }
    }

    private void showNotification(Context context, String Message, String data) {

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(this, DriverSplashAct.class);
        notificationIntent.putExtra("GCMnotification", data);
        DriverSessionSave.saveSession("GCMnotification", data, context);
        DriverSystems.out.println("GGGGGGGGG" + data);
        int requestID = (int) System.currentTimeMillis();
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        DriverSessionSave.saveSession("LogoutMessage", Message, FirebaseService.this);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            mNotificationManager.createNotificationChannel(notificationChannel);
            builderD = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).setContentText(Message).setContentTitle(title).setOngoing(true).setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL).setSmallIcon(R.drawable.driver_notification_icon).setColor(ContextCompat.getColor(getBaseContext(), R.color.button_accept)).setContentIntent(pendingIntent).setLargeIcon(((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.ic_launcher)).getBitmap()).setWhen(System.currentTimeMillis());
        } else {
            builderD = new Notification.Builder(context);
            builderD.setAutoCancel(false);
            builderD.setTicker(DriverNC.getString(R.string.app_name));
            builderD.setContentTitle(title);
            builderD.setContentText(Message);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builderD.setSmallIcon(R.drawable.ic_launcher);
                builderD.setColor(ContextCompat.getColor(getBaseContext(), R.color.button_accept));
            } else {
                builderD.setSmallIcon(R.drawable.ic_launcher);
            }
            builderD.setContentIntent(pendingIntent);
            builderD.setOngoing(false);
        }
        Notification myNotication = builderD.build();
        myNotication.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(NOTIFICATION_ID, myNotication);
        Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        try {
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification1);
            r.play();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void showNotificationBookLater(Context context, String Message, String message, Intent intent) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(this, DriverSplashAct.class);
        int requestID = (int) System.currentTimeMillis();
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra("alert_message", message);
        notificationIntent.putExtra("alert_schedule", "1");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            mNotificationManager.createNotificationChannel(notificationChannel);

            builderD = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).setContentText(Message).setContentTitle(title).setOngoing(true).setAutoCancel(true).setSmallIcon(R.drawable.driver_notification_icon).setColor(ContextCompat.getColor(getBaseContext(), R.color.button_accept)).setContentIntent(pendingIntent).setLargeIcon(((BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher)).getBitmap()).setWhen(System.currentTimeMillis());
        } else {
            builderD = new Notification.Builder(context);
            builderD.setAutoCancel(true);
            builderD.setTicker(DriverNC.getString(R.string.app_name));
            builderD.setContentTitle(title);
            builderD.setContentText(Message);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builderD.setSmallIcon(R.drawable.ic_launcher);
                builderD.setColor(ContextCompat.getColor(getBaseContext(), R.color.button_accept));
            } else {
                builderD.setSmallIcon(R.drawable.ic_launcher);
            }
            builderD.setContentIntent(pendingIntent);
            builderD.setOngoing(false);
        }
        builderD.build();
        Notification myNotication = builderD.getNotification();
        myNotication.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(BOOKLATER_NOTIFICATION_ID, myNotication);
        Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        try {
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification1);
            r.play();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void sendInfo(Context context, String unique) {

        String base_url = DriverSessionSave.getSession("base_url", context);
        Uri uri = Uri.parse(base_url);
        String path = uri.getPath();
        String url = base_url.replaceAll(path, "") + "/taxidispatch/report_push_notification";
        DriverCoreClient client = AppController.getInstance().getApiManagerWithEncryptBaseUrl_driver();
        Call<ResponseBody> detail_infoCall = client.detail_infoCall(url, new DriverDetailInfo(/*DeviceUtils.INSTANCE.getAllInfo(context), */DriverUtils.INSTANCE.driverInfo(context), unique), DriverSessionSave.getSession("Lang", context));
        detail_infoCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override

            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
