package com.movedriverdriver.driver.service;

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
import com.movedriverdriver.R;
import com.movedriverdriver.driver.DriverCanceltripAct;
import com.movedriverdriver.driver.MainActivityDriver;
import com.movedriverdriver.driver.DriverMyStatus;
import com.movedriverdriver.driver.DriverNotificationAct;
import com.movedriverdriver.driver.DriverOngoingAct;

import com.movedriverdriver.driver.DriverSplashAct;
import com.movedriverdriver.driver.DriverUserLoginAct;
import com.movedriverdriver.driver.data.DriverCommonData;
import com.movedriverdriver.driver.data.apiData.DriverDetailInfo;
import com.movedriverdriver.driver.errorLog.DriverApiErrorModel;
import com.movedriverdriver.driver.errorLog.DriverErrorLogRepository;
import com.movedriverdriver.driver.utils.DriverUtils;
import com.movedriverdriver.driver.utils.DriverExceptionConverter;
import com.movedriverdriver.driver.utils.DriverNC;
import com.movedriverdriver.driver.utils.DriverSessionSave;
import com.movedriverdriver.driver.utils.DriverSystems;
import com.movedriverdriver.util.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by developer on 30/8/17.
 */

public class DriverFirebaseService extends FirebaseMessagingService {
    public static final int NOTIFICATION_ID = 123;
    public static MainActivityDriver MAIN_ACT;
    Notification.Builder builder;
    private NotificationManager mNotificationManager;
    public static final int BOOKLATER_NOTIFICATION_ID = 123;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        DriverSystems.out.println("MyFirebaseIIDServicesD" + "onNewToken");
        if (s != null && !TextUtils.isEmpty(s)) {
            DriverSystems.out.println("MyFirebaseIIDServicesD" + "__________" + s);
            DriverSessionSave.saveSession(DriverCommonData.DEVICE_TOKEN, s, this);
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        DriverSystems.out.println("MyFirebaseIIDServicesD" + remoteMessage.getData());
        onHandleIntent(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Log.d("MyFirebaseIIDServiceD", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("sssssD", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


    protected void onHandleIntent(RemoteMessage remoteMessage) {// Handling gcm message from
        String message;
        String unique = "";
        try {
            message = remoteMessage.getData().get("message");
            if (message != null && !message.isEmpty()) {
                JSONObject jsonObject = new JSONObject(message);

                if (jsonObject.getString("status").equals("14")) {
                    if (jsonObject.getString("display").equals("1")) {
                        if (jsonObject.has("message")) {
                            generateNotification(this, message, DriverUserLoginAct.class);// to handle book later schedule
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
                    generateNotification(this, message, DriverUserLoginAct.class);
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

                    generateNotification(this, message, DriverSplashAct.class);
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
                    generateNotification(this, message, DriverSplashAct.class);
                } else if (jsonObject.getString("status").equals("43")) {
                    sendInfo(getApplicationContext(), unique);

                } else if (jsonObject.getString("status").equals("44")) {
                    sendInfo(getApplicationContext(), unique);
                    generateNotification(this, message, DriverSplashAct.class);
                    final Intent i = new Intent(getApplicationContext(), LocationUpdate.class);
                    stopService(i);
                    if (!DriverSessionSave.getSession("driver_type", getApplicationContext()).equals("D") && !DriverSessionSave.getSession(DriverCommonData.SHIFT_OUT, getApplicationContext(), false) && !DriverSessionSave.getSession(DriverCommonData.LOGOUT, getApplicationContext(), false))
                        LocationUpdate.startLocationService(getApplicationContext());
                } else if (jsonObject.getString("status").equals("55")) {
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
                } else generateNotification(this, message, DriverSplashAct.class);

            }
        } catch (Exception e) {
            e.printStackTrace();
            DriverErrorLogRepository.getRepository(getApplicationContext()).insertAllApiErrorLogs(new DriverApiErrorModel(0, DriverCommonData.getCurrentTimeForLogger(), "type=FirebaseService", DriverExceptionConverter.INSTANCE.buildStackTraceString(e.getStackTrace()), DriverUtils.INSTANCE.driverInfo(getApplicationContext()), null, getApplicationContext().getClass().getSimpleName(), 0));
        }
    }

    private void sendNotification(String msg) {
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_10";
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, DriverMyStatus.class), PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).setSmallIcon(R.drawable.ic_launcher).setContentTitle("TM").setStyle(new NotificationCompat.BigTextStyle().bigText(msg)).setContentText(msg);
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

    public void generateNotification(Context context, String message, Class<?> class1) {

        String Message;
        try {
            final JSONObject jo = new JSONObject(message);
            if (jo.getString("status").equals("25") || jo.getString("status").equals("15")) {
                DriverSystems.out.println("_________sddsfdsfdsfsll" + jo);

                DriverSessionSave.saveSession("status", "", DriverFirebaseService.this);
                DriverSessionSave.saveSession("Id", "", DriverFirebaseService.this);
                DriverSessionSave.saveSession("Driver_locations", "", DriverFirebaseService.this);
                DriverSessionSave.saveSession("driver_id", "", DriverFirebaseService.this);
                DriverSessionSave.saveSession("Name", "", DriverFirebaseService.this);
                DriverSessionSave.saveSession("company_id", "", DriverFirebaseService.this);
                DriverSessionSave.saveSession("bookedby", "", DriverFirebaseService.this);
                DriverSessionSave.saveSession("p_image", "", DriverFirebaseService.this);
                DriverSessionSave.saveSession("Email", "", DriverFirebaseService.this);
                DriverSessionSave.saveSession("trip_id", "", DriverFirebaseService.this);
                DriverSessionSave.saveSession("phone_number", "", DriverFirebaseService.this);
                DriverSessionSave.saveSession("driver_password", "", DriverFirebaseService.this);
                DriverSessionSave.setWaitingTime(0L, DriverFirebaseService.this);
                Message = jo.getString("message");
                DriverSessionSave.saveSession(DriverCommonData.USER_KEY, "", DriverFirebaseService.this);
                showNotification(context, Message, message);
                if (MAIN_ACT != null) {
                    Handler h = new Handler();
                    if (h != null) h.post(() -> MAIN_ACT.checkGCM());

                }
                Intent i = new Intent(DriverFirebaseService.this, DriverUserLoginAct.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else if (jo.getString("status").equals("42") || jo.getString("status").equals("45") || jo.getString("status").equals("41") || jo.getString("status").equals("45") || jo.getString("status").equals("44")) {
                Message = jo.getString("message");
                showNotification(context, Message, message);
            } else if (jo.getString("status").equals("14")) {
                Message = jo.getString("message");
                Intent i = new Intent(DriverFirebaseService.this, DriverMyStatus.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                i.putExtra("alert_message", message);
                i.putExtra("alert_schedule", "1");
                startActivity(i);
                showNotificationBookLater(context, Message, message, i);
            } else {
                if (DriverNotificationAct.notificationObject != null) {
                    DriverSystems.out.println("_________sddsfdsfdsfs");
                    if (jo.getString("status").equals("7") || jo.getString("status").equals("26")) {
                        DriverSessionSave.saveSession("trip_id", "", DriverFirebaseService.this);
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
        String title = "TaxiMobilty";
        Intent notificationIntent = new Intent(this, DriverSplashAct.class);
        notificationIntent.putExtra("GCMnotification", data);
        DriverSessionSave.saveSession("GCMnotification", data, context);
        DriverSystems.out.println("GGGGGGGGG" + data);
        int requestID = (int) System.currentTimeMillis();
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        DriverSessionSave.saveSession("LogoutMessage", Message, DriverFirebaseService.this);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            mNotificationManager.createNotificationChannel(notificationChannel);

            builder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).setContentText(Message).setContentTitle(title).setOngoing(true).setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL).setSmallIcon(R.drawable.small_logo).setColor(ContextCompat.getColor(getBaseContext(), R.color.button_accept)).setContentIntent(pendingIntent).setLargeIcon(((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.ic_launcher)).getBitmap()).setWhen(System.currentTimeMillis());
        } else {
            builder = new Notification.Builder(context);
            builder.setAutoCancel(false);
            builder.setTicker(DriverNC.getString(R.string.app_name));
            builder.setContentTitle(title);
            builder.setContentText(Message);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setSmallIcon(R.drawable.small_logo);
                builder.setColor(ContextCompat.getColor(getBaseContext(), R.color.button_accept));
            } else {
                builder.setSmallIcon(R.drawable.small_logo);
            }
            builder.setContentIntent(pendingIntent);
            builder.setOngoing(false);
        }
        Notification myNotication = builder.build();
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

            builder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).setContentText(Message).setContentTitle(title).setOngoing(true).setAutoCancel(true).setSmallIcon(R.drawable.ic_launcher).setContentIntent(pendingIntent).setLargeIcon(((BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher)).getBitmap()).setWhen(System.currentTimeMillis());
        } else {
            builder = new Notification.Builder(context);
            builder.setAutoCancel(true);
            builder.setTicker(DriverNC.getString(R.string.app_name));
            builder.setContentTitle(title);
            builder.setContentText(Message);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setSmallIcon(R.drawable.ic_launcher);
                builder.setColor(ContextCompat.getColor(getBaseContext(), R.color.button_accept));
            } else {
                builder.setSmallIcon(R.drawable.ic_launcher);
            }
            builder.setContentIntent(pendingIntent);
            builder.setOngoing(false);
        }
        builder.build();
        Notification myNotication = builder.getNotification();
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
//        CoreClient client = new ServiceGenerator(context, false, base_url.replaceAll(path, "")).createService(CoreClient.class);
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