package com.movedriverdriver.driver.service;

import static com.movedriverdriver.driver.MainActivityDriver.mshowDialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.movedriverdriver.BuildConfig;
import com.movedriverdriver.R;
import com.movedriverdriver.driver.DriverCallReceiver;
import com.movedriverdriver.driver.DriverCanceltripAct;
import com.movedriverdriver.driver.MainActivityDriver;
import com.movedriverdriver.driver.DriverMyStatus;
import com.movedriverdriver.driver.DriverNotificationAct;
import com.movedriverdriver.driver.DriverOngoingAct;
import com.movedriverdriver.driver.DriverSplashAct;
import com.movedriverdriver.driver.DriverStreetPickUpAct;
import com.movedriverdriver.driver.DriverUserLoginAct;
import com.movedriverdriver.driver.data.DriverCommonData;
import com.movedriverdriver.driver.errorLog.DriverApiErrorModel;
import com.movedriverdriver.driver.errorLog.DriverErrorLogRepository;
import com.movedriverdriver.driver.interfaces.DriverAPIResult;
import com.movedriverdriver.driver.interfaces.DriverDistanceMatrixInterface;
import com.movedriverdriver.driver.interfaces.DriverPickupupdate;
import com.movedriverdriver.driver.interfaces.DriverStreetPickupInterface;
import com.movedriverdriver.driver.route.DriverFindApproxDistance;
import com.movedriverdriver.driver.utils.DriverCL;
import com.movedriverdriver.driver.utils.DriverCToast;
import com.movedriverdriver.driver.utils.DriverDistanceMatrixUtil;
import com.movedriverdriver.driver.utils.DriverUtils;
import com.movedriverdriver.driver.utils.DriverExceptionConverter;
import com.movedriverdriver.driver.utils.DriverFontHelper;
import com.movedriverdriver.driver.utils.DriverLocationDb;
import com.movedriverdriver.driver.utils.DriverLocationUtils;
import com.movedriverdriver.driver.utils.DriverNC;
import com.movedriverdriver.driver.utils.DriverNetworkStatus;
import com.movedriverdriver.driver.utils.DriverSessionSave;
import com.movedriverdriver.driver.utils.DriverSystems;
import com.movedriverdriver.util.AppController;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * getting gps status without location manager
 * This class helps to get the driver current location using location client. It
 * Keep on updating driver location to server with certain time interval (Every
 * 5sec). In this class,Driver gets the new request notification and trip cancel
 * notifications.
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class LocationUpdate extends Service implements DriverDistanceMatrixInterface {
    public static final String LOCATION_ACCURACY_LOW = "LOW_ACCURACY";
    public static final String WAITING_TIME = "waiting_time";
    public static final float slabAccuracy = 70f;
    public static String oLocation = "";
    public static double currentLatitude = 0.0;
    public static double currentLongtitude = 0.0;
    public static double currentAccuracy = 0.0;
    public static double speed = 0.0;
    public static double HTotdistanceKM = 0.0;
    public static boolean DISTANCE_CALCULATION_INPROGRESS;
    public static long STARTED_AT;
    public static long startTime = 0L, timeInMillies = 0L, finalTime = 0L;
    public static LocationUpdate instance;
    public static DriverStreetPickupInterface streetPickupInterface;
    public static Location currentLocation = null;
    public static String sTimer = "00:00:00";
    private static final int Notification_ID = 1;
    private static final Handler myHandler = new Handler();
    private static boolean waitingTimeRunning;
    private static final int idleNotification = 201;
    private final long FREE_UPDATE_INTERVAL = 5000;
    private final long INTRIP_UPDATE_INTERVAL = 10000;
    DriverLocationDb LocDB;
    DecimalFormat latlngdf = new DecimalFormat("#.######");
    private LocalBroadcastManager localBroadcastManager;
    private final ArrayList<Float> locationAccuracyList = new ArrayList<>();
    private long logLocationInterval = 0;
    private double lastlatitude = 0.0, lastlongitude = 0.0;
    private final double slabDistance = 250;
    public String updateLocation = "", bearing = "0";
    public static String sLocation = "";
    private String serviceStartedFrom = "", serviceStartedTime = "", serviceCreatedTime = "";
    private boolean canCalculateDistance;
    private boolean UPDATE_LOCATION_NO_TRAFFIC = true;
    private final long locationUpdatedAt = 0L;
    private int startID, errorCount = 0;
    private long UPDATE_INTERVAL = 0;
    private final long TIMER_INTERVAL = 5000;
    private final long DELAY_DUE_TO_TRAFFIC = 10000;
    private long timeSwap;
    private String trip_id = "", drop, bookedby;
    JSONObject data = new JSONObject();
    private final Runnable updateTimerMethod = new Runnable() {
        @Override
        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            if (startTime != 0) {
                finalTime = timeSwap + timeInMillies;
                sTimer = DriverCommonData.getDateForWaitingTime(finalTime);

                if (finalTime != 0) {
                    DriverSessionSave.setWaitingTime(finalTime, LocationUpdate.this);
                    if (localBroadcastManager != null) {
                        Intent localIntent = new Intent(WAITING_TIME);
                        localIntent.putExtra(DriverCommonData.FINAL_WAITING_TIME, sTimer);
                        localBroadcastManager.sendBroadcast(localIntent);
                    }
                }
            }
            myHandler.postDelayed(this, 1000);
        }
    };
    BroadcastReceiver listener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DriverSystems.out.println("LISTENER Location Update");
            if (intent.getStringExtra(DriverCommonData.WAITING_TIME_START_STOP).equalsIgnoreCase(DriverCommonData.WAITING_TIME_START)) {
                startWaitingTime();
            } else {
                stopWaitingTime();
            }
        }
    };
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Handler DistanceHandler, mhandler;
    private Runnable distanceRunnable, timerRunnable;
    private Date mDateObject;
    private ScheduledFuture<?> excecuter;
    private NotificationManager notificationManager;
    private final ScheduledExecutorService mTimer = Executors.newSingleThreadScheduledExecutor();

    FusedLocationProviderClient mFusedLocationClient;

    private static Location mLastLocationTemp;
    public static double localDistance = 0.0;

    public static int runningFor() {

        return (int) ((new Date().getTime() - STARTED_AT) / 1000);
    }

    public static void startLocationService(Context context) {
        DriverSystems.out.println("Location  ConnectionResult !");
        if (!DriverSessionSave.getSession("Id", context).equals("") && !DriverSessionSave.getSession(DriverCommonData.SHIFT_OUT, context, false)) {
            if (!DriverCommonData.serviceIsRunningInForeground(context)) {
                String serviceStartedFrom = context.getClass().getSimpleName();
                Intent pushIntent1 = new Intent(context, LocationUpdate.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    pushIntent1.putExtra("started_from", serviceStartedFrom);
                    context.startForegroundService(pushIntent1);
                } else {
                    pushIntent1.putExtra("started_from", serviceStartedFrom);
                    context.startService(pushIntent1);
                }
            }
        }
    }

    public static void registerInterface(DriverStreetPickupInterface streetPickupInterfaces) {
        streetPickupInterface = streetPickupInterfaces;
    }

    public static boolean isNetworkEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }


    public static String GetUTCdatetimeAsString() {
        return "" + System.currentTimeMillis();
    }

    public static void ClearSession(Context context) {
        DriverSystems.out.println("nn--ClearSession");
        timeInMillies = 0L;
        finalTime = 0L;
        startTime = 0L;
        sTimer = "00:00:00";
        mLastLocationTemp = null;
        localDistance = 0.0;
        waitingTimeRunning = false;
        LocationUpdate.sLocation = "";
        DriverSessionSave.setWaitingTime(0L, context);
        DriverSessionSave.setDistance(0.0, context);
        DriverSessionSave.setGoogleDistance(0f, context);
        DriverSessionSave.saveSession(DriverCommonData.LAST_KNOWN_LAT, "", context);
        DriverSessionSave.saveGoogleWaypoints(null, null, "", 0.0, "", context);
        DriverSessionSave.saveWaypoints(null, null, "", 0.0, "", context);
    }

    public static void ClearSessionwithTrip(Context context) {
        DriverSystems.out.println("nn--ClearSessionwithTrip");
        timeInMillies = 0L;
        finalTime = 0L;
        startTime = 0L;
        sTimer = "00:00:00";
        waitingTimeRunning = false;
        mLastLocationTemp = null;
        localDistance = 0.0;
        LocationUpdate.sLocation = "";
        DriverSessionSave.setWaitingTime(0L, context);
        DriverSessionSave.setDistance(0.0, context);
        DriverSessionSave.setGoogleDistance(0f, context);
        DriverSessionSave.saveSession(DriverCommonData.LAST_KNOWN_LAT, "", context);
        DriverSessionSave.saveSession("status", "F", context);
        DriverSessionSave.saveSession("travel_status", "", context);
        DriverSessionSave.saveSession("trip_id", "", context);
        DriverSessionSave.saveGoogleWaypoints(null, null, "", 0.0, "", context);
        DriverSessionSave.saveWaypoints(null, null, "", 0.0, "", context);
    }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DriverSystems.out.println("Location  ConnectionResult @");
        Log.e("", "Location update service started Start");
        if (intent != null && intent.getStringExtra("started_from") != null)
            serviceStartedFrom = intent.getStringExtra("started_from");
        serviceStartedTime = GetUTCdatetimeAsString();
        startID = startId;
        DriverSystems.out.println("startID" + startId);
        DriverStreetPickUpAct.registerDistanceInterface(new DriverPickupupdate() {

            @Override
            public void pickUpdate(Location location) {
                currentLocation = location;
                lastlatitude = location.getLatitude();
                lastlongitude = location.getLongitude();
                DistanceHandler.postDelayed(distanceRunnable, 0);
            }
        });

        DriverOngoingAct.registerDistanceInterface(new DriverPickupupdate() {

            @Override
            public void pickUpdate(Location location) {
                currentLocation = location;
                lastlatitude = location.getLatitude();
                lastlongitude = location.getLongitude();
                DistanceHandler.postDelayed(distanceRunnable, 0);
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("Wakelock")
    @Override
    public void onCreate() {
        DriverSystems.out.println("Location  ConnectionResult #");
        super.onCreate();
        this.startForeground(10, getNotification());
        serviceCreatedTime = GetUTCdatetimeAsString();
        cancelNotification(LocationUpdate.this, idleNotification);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mDateObject = new Date();
        STARTED_AT = mDateObject.getTime();
        DriverSessionSave.saveSession("service_status", true, LocationUpdate.this);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        mhandler = new Handler(Looper.getMainLooper());
        lastlatitude = DriverSessionSave.getLastLng(LocationUpdate.this).latitude;
        lastlongitude = DriverSessionSave.getLastLng(LocationUpdate.this).longitude;
        DistanceHandler = new Handler();
        this.localBroadcastManager.registerReceiver(listener, new IntentFilter(DriverStreetPickUpAct.WAITING_TIME_RUN));
        distanceRunnable = new Runnable() {
            @Override
            public void run() {
                canCalculateDistance = true;
            }
        };
        DistanceHandler.post(distanceRunnable);

        instance = this;
        DriverSystems.out.println("Location  ConnectionResult 1");
        Log.e("", "Location update service create");

        if (!DriverSessionSave.getSession(DriverCommonData.RUN_GO_LANG, LocationUpdate.this).equals("true")) {
            //Get Auth
            DriverSessionSave.saveSession(DriverCommonData.NODE_TOKEN, "0", LocationUpdate.this);
            DriverNodeAuth.getInstance().getAuth(LocationUpdate.this);
        }

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(DriverLocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setFastestInterval(DriverLocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);


        LocDB = new DriverLocationDb(LocationUpdate.this);


        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (excecuter != null && (excecuter.getDelay(TimeUnit.SECONDS) >= 0 && excecuter.getDelay(TimeUnit.SECONDS) < 5)) {
                    DriverSystems.out.println("Gcmupdate ----->   " + new Date());

                    UPDATE_INTERVAL += TIMER_INTERVAL;


                    if (sLocation.equals("")) {
                        if (ActivityCompat.checkSelfPermission(LocationUpdate.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationUpdate.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        mLastLocation = location;
                                        if (servicesConnected() && mLastLocation != null && mLastLocation.hasAccuracy() && mLastLocation.getAccuracy() <= slabAccuracy) {
                                            DriverSystems.out.println("nnn---onConnected!!!!!!%%%%%%");
                                            currentLatitude = mLastLocation.getLatitude();
                                            currentLongtitude = mLastLocation.getLongitude();
                                            if (mLastLocation.hasBearing())
                                                bearing = String.valueOf(mLastLocation.getBearing());
                                        }
                                    }
                                }
                            });
                        }

                    } else {
                        if (!GPSEnabled(LocationUpdate.this) && DriverSessionSave.getSession("status", LocationUpdate.this).equalsIgnoreCase("F")) {
                            sLocation = "";
                        }
                    }

                    try {
                        utilizeLocationToCalcDistance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (DriverSessionSave.getSession("Id", LocationUpdate.this).trim().equals("") || !DriverSessionSave.getSession("shift_status", LocationUpdate.this).equals("IN")) {
                        if (mTimer != null) mTimer.shutdown();
                        stopSelf();
                    } else {
                        if (DriverNetworkStatus.isOnline(LocationUpdate.this)) {
                            if (localBroadcastManager != null) {
                                Intent localIntent = new Intent(LOCATION_ACCURACY_LOW);
                                localIntent.putExtra("show_alert", false);
                                localBroadcastManager.sendBroadcast(localIntent);
                            }
                            if (!DriverSessionSave.getSession("Id", LocationUpdate.this).equals("")) {
                                /**
                                 * if location history throws error for 3 times continously
                                 * we will wait for DELAY_DUE_TO_TRAFFIC sec to next update
                                 */
                                if (errorCount >= 3 && UPDATE_LOCATION_NO_TRAFFIC) {
                                    DriverSystems.out.println("Nandhini Distance Calculation UPDATE_LOCATION_NO_TRAFFIC");
                                    UPDATE_LOCATION_NO_TRAFFIC = false;
                                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            errorCount = 0;
                                            UPDATE_LOCATION_NO_TRAFFIC = true;
                                        }
                                    }, DELAY_DUE_TO_TRAFFIC);
                                }
                                if (UPDATE_LOCATION_NO_TRAFFIC) {
                                    if (DriverSessionSave.getSession("travel_status", LocationUpdate.this).equals("2")) {
                                        if (UPDATE_INTERVAL >= INTRIP_UPDATE_INTERVAL) {
                                            if (DriverSessionSave.getSession(DriverCommonData.RUN_GO_LANG, LocationUpdate.this).equals("true")) {
                                                DriverStatusUpdate(DriverSessionSave.getSession("Id", LocationUpdate.this), DriverSessionSave.getSession("status", LocationUpdate.this), "");
                                            } else {
                                                if (!DriverSessionSave.getSession(DriverCommonData.NODE_TOKEN, LocationUpdate.this).equals("0"))
                                                    DriverStatusUpdate(DriverSessionSave.getSession("Id", LocationUpdate.this), DriverSessionSave.getSession("status", LocationUpdate.this), "");
                                                else
                                                    DriverNodeAuth.getInstance().getAuth(LocationUpdate.this);
                                            }
                                            UPDATE_INTERVAL = 0;
                                        }
                                    } else if (UPDATE_INTERVAL >= FREE_UPDATE_INTERVAL) {
                                        if (DriverSessionSave.getSession(DriverCommonData.RUN_GO_LANG, LocationUpdate.this).equals("true")) {
                                            DriverStatusUpdate(DriverSessionSave.getSession("Id", LocationUpdate.this), DriverSessionSave.getSession("status", LocationUpdate.this), "");
                                        } else {
                                            if (!DriverSessionSave.getSession(DriverCommonData.NODE_TOKEN, LocationUpdate.this).equals("0"))
                                                DriverStatusUpdate(DriverSessionSave.getSession("Id", LocationUpdate.this), DriverSessionSave.getSession("status", LocationUpdate.this), "");
                                            else
                                                DriverNodeAuth.getInstance().getAuth(LocationUpdate.this);
                                        }
                                        UPDATE_INTERVAL = 0;
                                    }
                                }
                            } else {
                                DriverSessionSave.saveSession(DriverCommonData.LOGOUT, true, LocationUpdate.this);
                                Intent intent = new Intent(LocationUpdate.this, DriverUserLoginAct.class);
                                startActivity(intent);
                                if (mTimer != null) mTimer.shutdown();
                                stopSelf();
                            }
                        } else if (sLocation.equals("")) {
                            if (localBroadcastManager != null) {
                                Intent localIntent = new Intent(LOCATION_ACCURACY_LOW);
                                localIntent.putExtra("show_alert", true);
// Send local broadcast
                                localBroadcastManager.sendBroadcast(localIntent);
                            }
                        }
                    }
                }

            }
        };

        excecuter = mTimer.scheduleAtFixedRate(timerRunnable, 0, TIMER_INTERVAL, TimeUnit.MILLISECONDS);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    if (servicesConnected() && mLastLocation != null && mLastLocation.hasAccuracy() && mLastLocation.getAccuracy() <= slabAccuracy) {
                        DriverSystems.out.println("nnn---onConnected!!!!!!%%%%%%");

                        currentLatitude = mLastLocation.getLatitude();
                        currentLongtitude = mLastLocation.getLongitude();
                        sLocation = currentLatitude + "," + currentLongtitude + "NAN5" + "|";
                        if (mLastLocation.hasBearing())
                            bearing = String.valueOf(mLastLocation.getBearing());
                    } else {

                    }
                } else {
//                            Toast.makeText(LocationUpdate.this, "No Last known location found. Try current location..!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void utilizeLocationToCalcDistance() {
        if (mLastLocation != null) {
            Location location = mLastLocation;
            if (DriverSessionSave.getSession("status", LocationUpdate.this).equalsIgnoreCase("A")) {
                if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                    if (location.hasAccuracy() && location.getAccuracy() <= slabAccuracy) {
                        if (mLastLocationTemp != null) {
                            if (mLastLocationTemp.getLatitude() != 0.0 && mLastLocationTemp.getLongitude() != 0.0) {
                                DriverSystems.out.println("Nan distance mLastLocationTemp" + "___1");
                                if (mLastLocationTemp.getLatitude() != location.getLatitude() && mLastLocationTemp.getLongitude() != location.getLongitude()) {
                                    DriverSystems.out.println("Nan distance mLastLocationTemp" + "mLastLocationTemp" + mLastLocationTemp + "___location" + location);
                                    double distance = DriverDistanceMatrixUtil.INSTANCE.calculateDistance(DriverSessionSave.getSession("Metric", LocationUpdate.this).trim(), new LatLng(location.getLatitude(), location.getLongitude()), new LatLng(mLastLocationTemp.getLatitude(), mLastLocationTemp.getLongitude()));
                                    localDistance = localDistance + distance;
                                    sLocation += location.getLatitude() + "," + location.getLongitude() + "," + location.getAccuracy() + "," + speed + "," + DriverSessionSave.getDistance(LocationUpdate.this) + "," + distance + "," + DateFormat.getTimeInstance().format(new Date()) + "|";
                                    distanceCalculation(location, mLastLocationTemp);
                                }
                                mLastLocationTemp = location;
                            } else {
                                DriverSystems.out.println("Nan distance mLastLocationTemp" + "___2");
                                sLocation += currentLatitude + "," + currentLongtitude + "," + location.getAccuracy() + "," + speed + "," + DriverSessionSave.getDistance(LocationUpdate.this) + ",1" + "|";
                                mLastLocationTemp = location;
                            }
                        } else {
                            DriverSystems.out.println("Nan distance mLastLocationTemp" + "___3");
                            sLocation += currentLatitude + "," + currentLongtitude + "," + location.getAccuracy() + "," + speed + "," + DriverSessionSave.getDistance(LocationUpdate.this) + ",2" + "|";
                            mLastLocationTemp = location;
                        }
                    }
                }
            } else {
                if (DriverSessionSave.getSession("status", LocationUpdate.this).equalsIgnoreCase("F")) {
                    if (location != null) if (location.getAccuracy() <= slabAccuracy) {
                        sLocation = currentLatitude + "," + currentLongtitude + "|";
                        lastlatitude = currentLatitude;
                        lastlongitude = currentLongtitude;
                    } else {
                        if (lastlatitude != 0.0 && lastlongitude != 0.0) {
                            sLocation = lastlatitude + "," + lastlongitude + "|";
                        }
                    }
                } else if (DriverSessionSave.getSession("status", LocationUpdate.this).equalsIgnoreCase("B")) {
                    if (location != null) {
                        if (location.getAccuracy() <= slabAccuracy) {
                            sLocation = currentLatitude + "," + currentLongtitude + "|";
                            lastlatitude = currentLatitude;
                            lastlongitude = currentLongtitude;
                        } else {
                            if (lastlatitude != 0.0 && lastlongitude != 0.0) {
                                sLocation = lastlatitude + "," + lastlongitude + "|";
                            }
                        }
                    }
                }
                mLastLocationTemp = null;
            }
        }
    }

    private void distanceCalculation(Location location, Location lastLatLng) {
        DistanceCalculation(location, lastLatLng);
        DriverSessionSave.saveLastLng(new LatLng(location.getLatitude(), location.getLongitude()), LocationUpdate.this);
    }

    private void createLocationLog() {
        if (logLocationInterval == (TIMER_INTERVAL * 60)) {
            logLocationInterval = 0L;
            float totalAccuracy = 0.0f;
            for (Float accuracy : locationAccuracyList) {
                totalAccuracy += accuracy;
            }
//            errorLogRepository.insertLocationLog(new LocationModel(GetUTCdatetimeAsString(), new LatLng(currentLatitude, currentLongtitude), String.valueOf(totalAccuracy / locationAccuracyList.size()), SessionSave.getSession("travel_status", LocationUpdate.this)));
            locationAccuracyList.clear();
        } else logLocationInterval += TIMER_INTERVAL;
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    public void cancelNotify() {
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }

    @Override
    public void onDestroy() {
        DriverSessionSave.saveSession(DriverCommonData.SERVICE_STOPPED_TIME, GetUTCdatetimeAsString(), LocationUpdate.this);
        if (localBroadcastManager != null) {
            Intent localIntent = new Intent(LOCATION_ACCURACY_LOW);
            localIntent.putExtra("show_alert", false);
            localBroadcastManager.sendBroadcast(localIntent);
        }
        // unregister local broadcast
        this.localBroadcastManager.unregisterReceiver(listener);
        stopWaitingTime();
        try {
            removeLocationUpdates();
            if (DriverSessionSave.getSession("Id", LocationUpdate.this).equals("")) {
                stopLocationUpdates();
            } else {
                stopLocationUpdates();
            }
            if (mTimer != null) {
                mTimer.shutdown();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        stopForeground(true);
        DriverSystems.out.println("LocationUpdate.sTimer" + LocationUpdate.sTimer);
        super.onDestroy();
    }


    public void startWaitingTime() {
        if (!DriverSessionSave.getSession("trip_id", LocationUpdate.this).equals("") && DriverSessionSave.getSession("travel_status", LocationUpdate.this).equalsIgnoreCase("2")) {
            waitingTimeRunning = true;
            startTime = SystemClock.uptimeMillis();

            timeInMillies = SystemClock.uptimeMillis() - startTime;
            timeSwap = DriverSessionSave.getWaitingTime(LocationUpdate.this);
            if (DriverSessionSave.getSession("taxi_speed", LocationUpdate.this).trim().equals(""))
                DriverSessionSave.saveSession("taxi_speed", "0.0", LocationUpdate.this);

            if (myHandler != null) {
                if (updateTimerMethod != null) {
                    myHandler.removeCallbacks(updateTimerMethod);
                }
                myHandler.postDelayed(updateTimerMethod, 1000);
            }
        }
    }

    public void stopWaitingTime() {
        waitingTimeRunning = false;
        if (myHandler != null) {
            myHandler.removeCallbacks(updateTimerMethod);
        }
        timeSwap += DriverSessionSave.getWaitingTime(LocationUpdate.this);
    }

    /**
     * Calculates the Internal distance that travel by fleet during active status
     */
    public void DistanceCalculation(Location currentLocation, Location to) {
        haversine(currentLocation.getLatitude(), currentLocation.getLongitude(), to.getLatitude(), to.getLongitude());
    }


    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            DriverSystems.out.println("nnn---onConnected LocationResult!!!!!!%%%%%%^^^^^&&&&");
            for (Location location : locationResult.getLocations()) {
                if (location != null && location.getLatitude() != 0.0) {
                    if (location.hasAccuracy() && location.getAccuracy() < 250 && sLocation.isEmpty()) {
                        location.setAccuracy(45.0f);
                    }

                    try {
                        location.setLatitude(Double.parseDouble(latlngdf.format(location.getLatitude())));
                        location.setLongitude(Double.parseDouble(latlngdf.format(location.getLongitude())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String mLastUpdateTime = DateFormat.getTimeInstance().format(mDateObject);
                    double _speed = location.getSpeed();
                    speed = roundDecimal(convertSpeed(_speed), 2);
                    if (!DriverSessionSave.getSession("Metric", LocationUpdate.this).equalsIgnoreCase("KM")) {
                        try {
                            speed = Double.parseDouble(DriverFontHelper.convertfromArabic("" + speed)) / 1.60934;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    currentLatitude = location.getLatitude();
                    currentLongtitude = location.getLongitude();
                    currentAccuracy = location.getAccuracy();
                    locationAccuracyList.add(location.getAccuracy());
                    DriverSessionSave.saveSession(DriverCommonData.SOS_LAST_LAT, "" + currentLatitude, LocationUpdate.this);
                    DriverSessionSave.saveSession(DriverCommonData.SOS_LAST_LNG, "" + currentLongtitude, LocationUpdate.this);
                    if (DriverSessionSave.getSession("taxi_speed", LocationUpdate.this).trim().equals(""))
                        DriverSessionSave.saveSession("taxi_speed", "0.0", LocationUpdate.this);
                    double taxiMinimumSpeed = Double.parseDouble(DriverSessionSave.getSession("taxi_speed", LocationUpdate.this));

                    DriverSystems.out.println("nnn---onConnectedsssss LocationResult "+ speed+"    "+ taxiMinimumSpeed +"     "+ waitingTimeRunning);
                    if (speed <= taxiMinimumSpeed) {
                        if (!waitingTimeRunning && !DriverSessionSave.getSession(DriverCommonData.WAITING_TIME_MANUAL, LocationUpdate.this, false))
                            startWaitingTime();

                        if (!waitingTimeRunning && DriverSessionSave.getSession(DriverCommonData.WAITING_TIME_MANUAL, LocationUpdate.this, false) && (DriverSessionSave.getSession(DriverCommonData.WAITING_TIME, LocationUpdate.this, false) || DriverSessionSave.getSession(DriverCommonData.ST_WAITING_TIME, LocationUpdate.this, false))) {
                            startWaitingTime();
                        }
                    } else if (waitingTimeRunning && !DriverSessionSave.getSession(DriverCommonData.WAITING_TIME_MANUAL, LocationUpdate.this, false))
                        stopWaitingTime();
//                    else  if (!waitingTimeRunning && SessionSave.getSession(CommonData.WAITING_TIME_MANUAL, LocationUpdate.this, false) &&(SessionSave.getSession(CommonData.WAITING_TIME, LocationUpdate.this, false)|| SessionSave.getSession(CommonData.ST_WAITING_TIME, LocationUpdate.this, false))){
//                        startWaitingTime();
//                    }

                    if (!DriverSessionSave.getSession("trip_id", LocationUpdate.this).equals("")) {
                        if (speed > 5) {
                            DriverSessionSave.saveSession(DriverCommonData.LAST_KNOWN_LAT, "" + currentLatitude, LocationUpdate.this);
                            DriverSessionSave.saveSession(DriverCommonData.LAST_KNOWN_LONG, "" + currentLongtitude, LocationUpdate.this);
                        }
                    } else {
                        DriverSessionSave.saveSession(DriverCommonData.LAST_KNOWN_LAT, "" + currentLatitude, LocationUpdate.this);
                        DriverSessionSave.saveSession(DriverCommonData.LAST_KNOWN_LONG, "" + currentLongtitude, LocationUpdate.this);
                    }

                    mLastLocation = location;
                    DriverSystems.out.println("onNewLocationAvailable " + startID + "__" + mLastUpdateTime + "_" + DriverSessionSave.getSession("status", LocationUpdate.this) + "++" + currentLatitude + "__" + speed + "__" + location.getAccuracy());
                }
            }
        }


    };

    private void stopLocationUpdates() {
        DriverSystems.out.println("disconnect " + "stopLocationUpdates");
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private synchronized void getAndStoreStringValues(String result) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            Document doc = dBuilder.parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();
            NodeList nList = doc.getElementsByTagName("*");
            int chhh = 0;
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    chhh++;
                    Element element2 = (Element) node;
                    DriverNC.nfields_byName.put(element2.getAttribute("name"), element2.getTextContent());
                }
            }
            getValueDetail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized void getValueDetail() {
        Field[] fieldss = R.string.class.getDeclaredFields();
        for (Field field : fieldss) {
            int id = getResources().getIdentifier(field.getName(), "string", getPackageName());
            if (DriverNC.nfields_byName.containsKey(field.getName())) {
                DriverNC.fields.add(field.getName());
                DriverNC.fields_value.add(getResources().getString(id));
                DriverNC.fields_id.put(field.getName(), id);
            }
        }
        for (Map.Entry<String, String> entry : DriverNC.nfields_byName.entrySet()) {
            String h = entry.getKey();
            String value = entry.getValue();
            DriverNC.nfields_byID.put(DriverNC.fields_id.get(h), DriverNC.nfields_byName.get(h));
            // do stuff
        }
    }

    synchronized void getColorValueDetail() {
        Field[] fieldss = R.color.class.getDeclaredFields();
        // fields =new int[fieldss.length];
        for (Field field : fieldss) {
            int id = getResources().getIdentifier(field.getName(), "color", getPackageName());
            if (DriverCL.nfields_byName.containsKey(field.getName())) {
                DriverCL.fields.add(field.getName());
                DriverCL.fields_value.add(getResources().getString(id));
                DriverCL.fields_id.put(field.getName(), id);
            }
        }

        for (Map.Entry<String, String> entry : DriverCL.nfields_byName.entrySet()) {
            String h = entry.getKey();
            String value = entry.getValue();
            DriverCL.nfields_byID.put(DriverCL.fields_id.get(h), DriverCL.nfields_byName.get(h));
            // do stuff
        }
    }

    private double convertSpeed(double speed) {
        return ((speed * 3600) * 0.001);
    }

    private double roundDecimal(double value, final int decimalPlace) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        value = bd.doubleValue();
        return value;
    }

    /**
     * This Function is used for calculate the distance travelled
     */
    public synchronized void haversine(double lat1, double lon1, double lat2, double lon2) {
        double tempDistance = 0.0;
        LatLng from = new LatLng(lat1, lon1);
        LatLng to = new LatLng(lat2, lon2);
        double distance = DriverDistanceMatrixUtil.INSTANCE.calculateDistance(DriverSessionSave.getSession("Metric", LocationUpdate.this).trim(), from, to);
        DriverSystems.out.println("Nan distance mLastLocationTemp" + "distance" + distance + "FROM" + from + "TOOO" + to);
//        tempDistance = distance * 1000;
        if (distance > 0) {
            if (distance < 5) {
                DriverSystems.out.println("Nan distance mLastLocationTemp" + "___4");
                DriverSystems.out.println("NANdhini Distan Calc" + distance + "___Total Dis" + (distance + DriverSessionSave.getDistance(LocationUpdate.this)) + "____Time" + DateFormat.getTimeInstance().format(new Date()) + "___haversine");
                distance += DriverSessionSave.getDistance(LocationUpdate.this);
                DriverSessionSave.setDistance(distance, LocationUpdate.this);
                DriverSessionSave.saveWaypoints(from, to, "haversine", distance, "" + "___" + startID, LocationUpdate.this);
            } else {
                DriverSystems.out.println("Nan distance mLastLocationTemp" + "___5");
                DISTANCE_CALCULATION_INPROGRESS = true;
                DriverSessionSave.saveWaypoints(new LatLng(from.latitude, from.longitude), new LatLng(to.latitude, to.longitude), "googleDistanceCall", 0.0, "server" + "___" + startID, LocationUpdate.this);
                new DriverFindApproxDistance(this).getDistance(this, to.latitude, to.longitude, from.latitude, from.longitude);
            }
        }

        DriverSessionSave.saveSession(DriverCommonData.LAST_KNOWN_LAT, "" + to.latitude, LocationUpdate.this);
        DriverSessionSave.saveSession(DriverCommonData.LAST_KNOWN_LONG, "" + to.longitude, LocationUpdate.this);

    }

    /**
     * Removes location updates. Note that in this sample we merely log the
     */
    public void removeLocationUpdates() {
        try {
            this.stopSelf();
        } catch (SecurityException unlikely) {
            unlikely.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("Wakelock")
    public String DriverStatusUpdate(final String id, final String status, final String gcmid) {
        String r_message = "";
        try {


            if (DriverNetworkStatus.isOnline(LocationUpdate.this) && DriverCallReceiver.phoneState()) {
                updateLocation = sLocation;
                DriverSessionSave.saveSession(DriverCommonData.DRIVER_LOCATION_STATIC, updateLocation, LocationUpdate.this);
                DriverSessionSave.saveSession(DriverCommonData.DRIVER_LOCATION, updateLocation, LocationUpdate.this);

                DriverServiceGenerator.API_BASE_URL = DriverSessionSave.getSession("base_url", LocationUpdate.this);
                if (!DriverSessionSave.getSession("wholekey", LocationUpdate.this).trim().equals("")) {
                    if (DriverNC.getString(R.string.ok) == null) {
                        getAndStoreStringValues(DriverSessionSave.getSession("wholekey", LocationUpdate.this));
                        getAndStoreColorValues(DriverSessionSave.getSession("wholekeyColor", LocationUpdate.this));
                    }
                }
                if (DriverSessionSave.getSession("status", LocationUpdate.this).equalsIgnoreCase("F")) {
                    sLocation = "";
                }
                JSONObject j = new JSONObject();
                j.put("driver_id", DriverSessionSave.getSession("Id", LocationUpdate.this));
                j.put("trip_id", DriverSessionSave.getSession("trip_id", LocationUpdate.this));

                DriverSystems.out.println("NAN sLocation" + sLocation + "____" + updateLocation);
                //Need to handle When location Settings disable
                if (updateLocation.equals("")) {
                    updateLocation = "0.0,0.0|";
                    currentAccuracy = 10000;
                    DriverSessionSave.saveSession(DriverCommonData.DRIVER_LOCATION, updateLocation, LocationUpdate.this);
                }

                j.put("locations", DriverSessionSave.getSession(DriverCommonData.DRIVER_LOCATION, LocationUpdate.this).replace("null", ""));

                if (DriverSessionSave.getSession("trip_id", LocationUpdate.this).equals("")) {
                    DriverSessionSave.saveSession("status", "F", LocationUpdate.this);
                    DriverSessionSave.saveSession("travel_status", "", LocationUpdate.this);
                }
                j.put("status", DriverSessionSave.getSession("status", LocationUpdate.this));
                j.put("travel_status", DriverSessionSave.getSession("travel_status", LocationUpdate.this));


                j.put("device_token", Settings.Secure.getString(LocationUpdate.this.getContentResolver(), Settings.Secure.ANDROID_ID));
                j.put("device_type", "1");
                j.put("above_min_km", "" + DriverCommonData.km_calc);
                j.put("bearings", mLastLocation != null ? Double.valueOf(String.format("%.4f", mLastLocation.getBearing())) : 0.0);
                j.put("distance", String.valueOf(DriverSessionSave.getDistance(LocationUpdate.this)));
                j.put("shift_id", DriverSessionSave.getSession("Shiftupdate_Id", LocationUpdate.this));
                j.put("driver_name", DriverSessionSave.getSession("Name", LocationUpdate.this));
                j.put("driver_taxi_number", DriverSessionSave.getSession("taxi_no", LocationUpdate.this));
                j.put("driver_taxi_model", DriverSessionSave.getSession("model_name", LocationUpdate.this));
                j.put("waiting_hour", String.valueOf(Float.valueOf(Float.valueOf(DriverSessionSave.getWaitingTime(LocationUpdate.this)) / 3600000)));
                j.put("accuracy", currentAccuracy);
                //     j.put("brand", Build.MANUFACTURER);
                //     j.put("model", Build.MODEL);
                j.put("service_status", DriverSessionSave.getSession("service_status", LocationUpdate.this, false));
                j.put("version_code", BuildConfig.VERSION_CODE);
//                j.put("carrier_name", DeviceUtils.INSTANCE.getCarriername(LocationUpdate.this));
                j.put("carrier_name", "");
                data.put("data", j);
                data.put("platform", "ANDROID");
                data.put("app", "DRIVER");
                data.put("lang", DriverSessionSave.getSession("Lang", LocationUpdate.this));
                data.put("id", DriverSessionSave.getSession("Id", LocationUpdate.this));
                DriverCoreClient client = null;
                String nodeUrl = DriverSessionSave.getSession(DriverCommonData.DRIVER_NODE_URL, LocationUpdate.this);
                if (DriverSessionSave.getSession("travel_status", LocationUpdate.this).equals("2")) {
                    client = AppController.getInstance().getNodeApiManagerWithTimeOut_driver(nodeUrl, 12L);
//                    client = new NodeServiceGenerator(LocationUpdate.this, dont_encode, SessionSave.getSession(CommonData.NODE_URL, LocationUpdate.this), 12).createService(CoreClient.class);
                } else {
                    client = AppController.getInstance().getNodeApiManagerWithTimeOut_driver(nodeUrl, 8L);
//                    client = new NodeServiceGenerator(LocationUpdate.this, dont_encode, SessionSave.getSession(CommonData.NODE_URL, LocationUpdate.this), 8).createService(CoreClient.class);
                }
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), data.toString());

                Call<ResponseBody> coreResponse = client.nodeUpdate(body, DriverCommonData.getTime(LocationUpdate.this), DriverSessionSave.getSession("trip_id", LocationUpdate.this).equals("") ? "5" : "12");

                if (DriverSessionSave.getSession(DriverCommonData.RUN_GO_LANG, LocationUpdate.this).equals("true")) {
                    coreResponse = client.goLangUpdate(body, DriverCommonData.getTime(LocationUpdate.this), DriverSessionSave.getSession("trip_id", LocationUpdate.this).equals("") ? "5" : "12");
                }

                coreResponse.enqueue(new DriverRetrofitCallbackClass<>(LocationUpdate.this, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        String datas = null;
                        JSONObject json = null;
                        if (!DriverSessionSave.getSession("status", LocationUpdate.this).equalsIgnoreCase("F")) {
                            sLocation = "";
                        }
                        try {
                            datas = response.body().string();
                            if (datas != null) {
                                driverResponseHandling(new JSONObject(datas));
                            } else {
                                errorCount += 1;
                                updateLocation = "";
                                DriverSessionSave.saveSession(DriverCommonData.DRIVER_LOCATION, "", LocationUpdate.this);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            errorCount += 1;
                            updateLocation = "";
                            DriverErrorLogRepository.getRepository(LocationUpdate.this).insertAllApiErrorLogs(new DriverApiErrorModel(0, DriverCommonData.getCurrentTimeForLogger(), "type=driver_location_history_update", DriverExceptionConverter.INSTANCE.buildStackTraceString(e.getStackTrace()), DriverUtils.INSTANCE.driverInfo(LocationUpdate.this), data, LocationUpdate.this.getClass().getSimpleName(), 0));
                            DriverSessionSave.saveSession(DriverCommonData.DRIVER_LOCATION, "", LocationUpdate.this);
                            DriverCToast.ShowToast(LocationUpdate.this, DriverNC.getString(R.string.server_error));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        updateLocation = "";
                        errorCount += 1;
//                        ErrorLogRepository.getRepository(LocationUpdate.this).insertAllApiErrorLogs(new ApiErrorModel(0,"2019-03-28", "type=driver_location_history_update",ExceptionConverter.INSTANCE.buildStackTraceString(t.getStackTrace()), DriverUtils.INSTANCE.driverInfo(LocationUpdate.this), data, LocationUpdate.this.getClass().getSimpleName(),0));
                        DriverErrorLogRepository.getRepository(LocationUpdate.this).insertAllApiErrorLogs(new DriverApiErrorModel(0, DriverCommonData.getCurrentTimeForLogger(), "type=driver_location_history_update", DriverExceptionConverter.INSTANCE.buildStackTraceString(t.getStackTrace()), DriverUtils.INSTANCE.driverInfo(LocationUpdate.this), data, LocationUpdate.this.getClass().getSimpleName(), 0));
                        DriverSessionSave.saveSession(DriverCommonData.DRIVER_LOCATION, "", LocationUpdate.this);
                        DriverCToast.ShowToast(LocationUpdate.this, DriverNC.getString(R.string.server_error));
                    }
                }));
            }


            DriverSessionSave.saveSession("status", DriverSessionSave.getSession("status", LocationUpdate.this), LocationUpdate.this);
            if (DriverSessionSave.getSession("base_url", LocationUpdate.this).trim().equals("")) {
                DriverServiceGenerator.API_BASE_URL = DriverSessionSave.getSession("base_url", LocationUpdate.this);
                getAndStoreStringValues(DriverSessionSave.getSession("wholekey", LocationUpdate.this));
                getAndStoreColorValues(DriverSessionSave.getSession("wholekeyColor", LocationUpdate.this));
            }
        } catch (final Exception e) {
            e.printStackTrace();
            DriverErrorLogRepository.getRepository(LocationUpdate.this).insertAllApiErrorLogs(new DriverApiErrorModel(0, DriverCommonData.getCurrentTimeForLogger(), "type=driver_location_history_update", DriverExceptionConverter.INSTANCE.buildStackTraceString(e.getStackTrace()), DriverUtils.INSTANCE.driverInfo(LocationUpdate.this), data, LocationUpdate.this.getClass().getSimpleName(), 0));
        }

        return r_message;
    }

    private void driverResponseHandling(JSONObject json) {

        try {
            DriverSystems.out.println("GCMM____driverResponseHandling_____" + json.getInt("status"));

            /**
             * To handle IF driver has trip but driver loc history updated as free
             * Need to move to ongoing screen
             */
            if (json.has("current_trip_id")) {
                if (!json.getString("current_trip_id").equals("0") && DriverSessionSave.getSession("status", LocationUpdate.this).equals("F")) {
                    cancelNotify();
                    generateNotifications(LocationUpdate.this, json.getString("message"), DriverOngoingAct.class, false, Notification_ID);
                    Intent ongoing = new Intent();
                    Bundle extras = new Bundle();
                    extras.putString("alert_message", "");
                    extras.putString("status", json.getString("status"));
                    DriverSessionSave.saveSession("trip_id", json.getString("current_trip_id"), LocationUpdate.this);
                    ongoing.putExtras(extras);
                    ongoing.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    ComponentName cn = new ComponentName(LocationUpdate.this, DriverOngoingAct.class);
                    ongoing.setComponent(cn);
                    getApplication().startActivity(ongoing);
                } else {

                }
            }

            if (json.getInt("status") == 1) {
                updateLocation = "";
                errorCount = 0;
                DriverSystems.out.println("ssssssres____________" + json.toString() + "__" + DriverSessionSave.getSession("status", LocationUpdate.this) + "__" + streetPickupInterface);
                DriverSessionSave.saveSession(DriverCommonData.DRIVER_LOCATION, "", LocationUpdate.this);
                if (DriverSessionSave.getSession("status", LocationUpdate.this).equals("A")) {
                    if (streetPickupInterface != null) {

                        final JSONObject jsons = json;
                        if (mhandler != null) {
                            mhandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        streetPickupInterface.updateFare(jsons.getString("trip_fare"), mLastLocation);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                    }
//                    CommonData.travel_km = json.getDouble("distance");
/*
                    try {
                        CommonData.DISTANCE_FARE = json.getString("trip_fare");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                } else {
                    DriverCommonData.travel_km = 0;
                    DriverSessionSave.setGoogleDistance(0f, LocationUpdate.this);
                    DriverSessionSave.setDistance(0f, LocationUpdate.this);
                    DriverSessionSave.saveWaypoints(null, null, "", 0.0, "" + "___" + startID, LocationUpdate.this);
                    DriverSessionSave.saveGoogleWaypoints(null, null, "", 0.0, "", LocationUpdate.this);
                }

            } else if (json.getInt("status") == 5) {


                notificationforTrip();
                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                boolean isScreenOn = pm.isScreenOn();
                if (isScreenOn == false) {
                    @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
                    wl.acquire(10000);
                    @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");

                    wl_cpu.acquire(10000);
                }
                int time_out = json.getJSONObject("trip_details").getInt("notification_time");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (DriverCommonData.current_trip_accept == 0) {
                                json.getJSONObject("trip_details").getString("notification_time");
                                JSONObject j = new JSONObject();
                                j.put("trip_id", json.getJSONObject("trip_details").getString("passengers_log_id"));
                                j.put("driver_id", DriverSessionSave.getSession("Id", LocationUpdate.this));
                                j.put("taxi_id", DriverSessionSave.getSession("taxi_id", LocationUpdate.this));
                                j.put("company_id", DriverSessionSave.getSession("company_id", LocationUpdate.this));
                                j.put("reason", "");
                                j.put("reject_type", "0");
                                final String Url = "type=reject_trip";
                                new TripRejectLocationUpdate(Url, j);
                                DriverCommonData.current_trip_accept = 1;
                            }


                        } catch (Exception e) {

                        }
                    }
                }, time_out * 1000);

                System.out.println("Check Trip auto accept : " + DriverSessionSave.getSession("is_driver_auto_accept", LocationUpdate.this));
                if (DriverSessionSave.getSession("is_driver_auto_accept", LocationUpdate.this).equals("1")) {
                    String message = json.toString();
                    try {
                        final JSONObject jsonnew = new JSONObject(message);
                        final JSONObject tripdetails = jsonnew.getJSONObject("trip_details");
                        trip_id = tripdetails.getString("passengers_log_id");
                        final JSONObject details = tripdetails.getJSONObject("booking_details");
                        drop = details.getString("drop");
                        if (details.getString("bookedby").length() != 0) {
                            bookedby = details.getString("bookedby");
                        }
                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                    auto_accept();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("message", json.toString());
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    ComponentName cn = new ComponentName(LocationUpdate.this, DriverNotificationAct.class);
                    intent.setComponent(cn);
                    startActivity(intent);
                }
            } else if (json.getInt("status") == 7 && !TextUtils.isEmpty(DriverSessionSave.getSession("trip_id", LocationUpdate.this)) || json.getInt("status") == 12 && !TextUtils.isEmpty(DriverSessionSave.getSession("trip_id", LocationUpdate.this)) || json.getInt("status") == 10) {
                DriverSystems.out.println("VVVVVVVVVVv" + json.getInt("status"));
                final JSONObject jsons = json;
                mhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String cancelmsg = "";
                            LocationUpdate.ClearSessionwithTrip(LocationUpdate.this);
//                            stopService(new Intent(LocationUpdate.this, WaitingTimerRun.class));
                            cancelmsg = jsons.getString("message");
                            if (cancelmsg.equals("booking_cancel_message")) {
                                cancelmsg = DriverNC.getString(R.string.trip_cancelled_by_passenger);
                            } else if (cancelmsg.equals("trip_completed_by_pass_wallet")) {
                                cancelmsg = DriverNC.getString(R.string.trip_completed_by_passenger_using_wallet);
                            }
                            generateNotifications(LocationUpdate.this, cancelmsg, DriverMyStatus.class, true, Notification_ID);

                            MainActivityDriver.mMyStatus.setStatus("F");
                            DriverSessionSave.saveSession("status", "F", LocationUpdate.this);
                            MainActivityDriver.mMyStatus.settripId("");
                            DriverSessionSave.saveSession("trip_id", "", LocationUpdate.this);
                            DriverSessionSave.setWaitingTime(0L, LocationUpdate.this);
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
                            DriverSessionSave.saveSession(DriverCommonData.ST_WAITING_TIME, false, getApplicationContext());
                            DriverSessionSave.saveSession(DriverCommonData.WAITING_TIME, false, getApplicationContext());
                            DriverCToast.ShowToast(getApplicationContext(), cancelmsg);
                            movetohome();
//                            Intent cancelIntent = new Intent();
//                            Bundle bun = new Bundle();
//                            bun.putString("message", cancelmsg);
//                            cancelIntent.putExtras(bun);
//                            cancelIntent.setAction(Intent.ACTION_MAIN);
//                            cancelIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//                            cancelIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                            ComponentName cn = new ComponentName(getApplicationContext(), DriverCanceltripAct.class);
//                            cancelIntent.setComponent(cn);
//                            startActivity(cancelIntent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 100);
            } else if (json.getInt("status") == 15 || json.getInt("status") == -15) {
                DriverSystems.out.println("lTaximobilityut_____" + json);
                LocationUpdate.this.stopSelf();
                int length = DriverCommonData.mActivitylist.size();
                if (length != 0) {
                    for (int i = 0; i < length; i++) {
                        DriverCommonData.mActivitylist.get(i).finish();
                    }
                }

                try {
                    DriverSessionSave.saveSession("status", "", LocationUpdate.this);
                    DriverSessionSave.saveSession("Id", "", LocationUpdate.this);
                    DriverSessionSave.saveSession(DriverCommonData.DRIVER_LOCATION, "", LocationUpdate.this);
                    DriverSessionSave.saveSession("driver_id", "", LocationUpdate.this);
                    DriverSessionSave.saveSession("Name", "", LocationUpdate.this);
                    DriverSessionSave.saveSession("company_id", "", LocationUpdate.this);
                    DriverSessionSave.saveSession("bookedby", "", LocationUpdate.this);
                    DriverSessionSave.saveSession("p_image", "", LocationUpdate.this);
                    DriverSessionSave.saveSession("Email", "", LocationUpdate.this);
                    DriverSessionSave.saveSession("trip_id", "", LocationUpdate.this);
                    DriverSessionSave.saveSession("phone_number", "", LocationUpdate.this);
                    DriverSessionSave.saveSession("driver_password", "", LocationUpdate.this);
                    DriverSessionSave.setWaitingTime(0L, LocationUpdate.this);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                DriverSessionSave.saveSession(DriverCommonData.USER_KEY, "", LocationUpdate.this);
                Intent intent = new Intent();
                Bundle bun = new Bundle();
                bun.putString("alert_message", json.getString("message"));
                intent.putExtras(bun);
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ComponentName cn = new ComponentName(LocationUpdate.this, DriverUserLoginAct.class);
                intent.setComponent(cn);
                startActivity(intent);
            }

            // For getting SMS From Passenger
            else if (json.getInt("status") == 11) {
                cancelNotify();
                generateNotifications(LocationUpdate.this, json.getString("message"), DriverOngoingAct.class, false, Notification_ID);
                Intent ongoing = new Intent();
                Bundle extras = new Bundle();
                String lTaximobilityutlmsg = "";
                lTaximobilityutlmsg = json.getString("message");
                extras.putString("alert_message", lTaximobilityutlmsg);
                extras.putString("status", json.getString("status"));
                ongoing.putExtras(extras);
                ongoing.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ComponentName cn = new ComponentName(LocationUpdate.this, DriverOngoingAct.class);
                ongoing.setComponent(cn);
                getApplication().startActivity(ongoing);
            } else if (json.getInt("status") == 16) {
                LocationUpdate.this.stopSelf();
                String lTaximobilityutlmsg = "";
                generateNotifications(LocationUpdate.this, json.getString("message"), DriverCanceltripAct.class, false, Notification_ID);
                lTaximobilityutlmsg = json.getString("message");
                Intent intent = new Intent();
                intent.putExtra("message", lTaximobilityutlmsg);
                intent.putExtra("status", json.getInt("status"));
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                ComponentName cn = new ComponentName(LocationUpdate.this, DriverCanceltripAct.class);
                intent.setComponent(cn);
                startActivity(intent);
            } else if (json.getInt("status") == -4 || json.getInt("status") == -3) {
                updateLocation = "";
                DriverSessionSave.saveSession(DriverCommonData.DRIVER_LOCATION, "", LocationUpdate.this);
            } else if (json.getInt("status") == -1) {
                updateLocation = "";
                DriverSessionSave.saveSession(DriverCommonData.DRIVER_LOCATION, "", LocationUpdate.this);
            } else if (json.getInt("status") == -101) {
                forceLogout();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            DriverErrorLogRepository.getRepository(LocationUpdate.this).insertAllApiErrorLogs(new DriverApiErrorModel(0, DriverCommonData.getCurrentTimeForLogger(), "type=driver_location_history_update", DriverExceptionConverter.INSTANCE.buildStackTraceString(e.getStackTrace()), DriverUtils.INSTANCE.driverInfo(LocationUpdate.this), data, LocationUpdate.this.getClass().getSimpleName(), 0));
        }

    }

    private void auto_accept() {
        try {
            if (DriverNetworkStatus.isOnline(LocationUpdate.this)) {
                if (GPSEnabled(LocationUpdate.this)) {
                    MainActivityDriver.mMyStatus.settripId(trip_id);
                    DriverSessionSave.saveSession("trip_id", "" + trip_id, LocationUpdate.this);
                    MainActivityDriver.mMyStatus.setpassengerId(trip_id);
                    JSONObject j = new JSONObject();
                    j.put("pass_logid", trip_id);
                    j.put("driver_id", DriverSessionSave.getSession("Id", LocationUpdate.this));
                    j.put("taxi_id", DriverSessionSave.getSession("taxi_id", LocationUpdate.this));
                    j.put("company_id", DriverSessionSave.getSession("company_id", LocationUpdate.this));
                    j.put("driver_reply", "A");
                    j.put("drop_location", drop);
                    j.put("field", "rejection");
                    j.put("flag", "0");
                    final String Url = "type=driver_reply";
                    DriverSystems.out.println("result" + "Sucess");
                    new TripAccept(Url, j);
                } else {
                    DriverCToast.ShowToast(LocationUpdate.this, "GPS Connection Failed");

                }
            } else {
                DriverCToast.ShowToast(LocationUpdate.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class TripAccept implements DriverAPIResult {
        String msg;
        JSONObject jsonObject;

        public TripAccept(final String url, JSONObject data) {
            jsonObject = data;
            DriverSystems.out.println("result" + url);

            new DriverAPIService_Retrofit_JSON(LocationUpdate.this, this, data, false).execute(url);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {

//            ACCEPT_TRIP_IN_PROGRESS = false;
            try {
                if (isSuccess) {

                    final JSONObject json = new JSONObject(result);
                    msg = json.getString("message");
                    DriverCommonData.current_trip_accept = 1;

                    if (json.getInt("status") == 7) {
                        bookedby = "";
                        DriverSessionSave.saveSession("trip_id", "", LocationUpdate.this);
                        msg = json.getString("message");
                        Intent i = new Intent(getBaseContext(), DriverMyStatus.class);
                        showLoading(LocationUpdate.this);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        Bundle extras = new Bundle();
                        extras.putString("alert_message", msg);
                        DriverCToast.ShowToast(LocationUpdate.this, msg);
                        getApplication().startActivity(i);
                    } else if (json.getInt("status") == 1 || bookedby.equals("2")) {
                        DriverSessionSave.saveSession("speedwaiting", "", LocationUpdate.this);
                        MainActivityDriver.mMyStatus.settripId(trip_id);
                        DriverSessionSave.saveSession("trip_id", "" + trip_id, LocationUpdate.this);
                        DriverSessionSave.saveSession("status", "B", LocationUpdate.this);
                        DriverSessionSave.saveSession(DriverCommonData.IS_STREET_PICKUP, false, LocationUpdate.this);
                        DriverSessionSave.saveSession("bookedby", "" + bookedby, LocationUpdate.this);
                        showLoading(LocationUpdate.this);
                        final Intent intent = new Intent(LocationUpdate.this, DriverOngoingAct.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        Bundle extras = new Bundle();
                        extras.putString("alert_message", msg);
                        intent.putExtras(extras);
                        startActivity(intent);
                    } else if (json.getInt("status") == 5) {
                        DriverSessionSave.saveSession("trip_id", "", LocationUpdate.this);
                        msg = json.getString("message");
                        Intent i = new Intent(getBaseContext(), DriverMyStatus.class);
                        showLoading(LocationUpdate.this);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        Bundle extras = new Bundle();
                        extras.putString("alert_message", msg);
                        //i.putExtras(extras);
                        getApplication().startActivity(i);
                        DriverCToast.ShowToast(LocationUpdate.this, msg);

                    } else if (json.getInt("status") == 25) {
                        DriverCToast.ShowToast(LocationUpdate.this, DriverNC.getString(R.string.server_error));
                    } else {
                        DriverCToast.ShowToast(LocationUpdate.this, msg);
                    }
                } else {

                    DriverCToast.ShowToast(LocationUpdate.this, DriverNC.getString(R.string.server_error));
//                    finish();
                }
            } catch (final JSONException e) {
                DriverErrorLogRepository.getRepository(LocationUpdate.this).insertAllApiErrorLogs(new DriverApiErrorModel(0, DriverCommonData.getCurrentTimeForLogger(), "type=driver_reply", DriverExceptionConverter.INSTANCE.buildStackTraceString(e.getStackTrace()), DriverUtils.INSTANCE.driverInfo(LocationUpdate.this), jsonObject, LocationUpdate.this.getClass().getSimpleName(), 0));

//                ACCEPT_TRIP_IN_PROGRESS = false;
                e.printStackTrace();
            }
        }
    }

    public void showLoading(Context context) {

        try {
            if (mshowDialog != null) if (mshowDialog.isShowing()) mshowDialog.dismiss();
            View view = View.inflate(context, R.layout.driver_progress_bar, null);
            mshowDialog = new Dialog(context, R.style.dialogwinddow);
            mshowDialog.setContentView(view);
            mshowDialog.setCancelable(false);

            mshowDialog.show();

            ImageView iv = mshowDialog.findViewById(R.id.giff);
            DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
            Glide.with(MainActivityDriver.context).load(R.raw.driver_loading_anim).into(imageViewTarget);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void movetohome() {
        MainActivityDriver.mMyStatus.setStatus("F");
        DriverSessionSave.saveSession("status", "F", getApplicationContext());
        MainActivityDriver.mMyStatus.settripId("");
        DriverSessionSave.saveSession("trip_id", "", getApplicationContext());
        MainActivityDriver.mMyStatus.setOnstatus("On");
        MainActivityDriver.mMyStatus.setOnPassengerImage("");
        MainActivityDriver.mMyStatus.setOnpassengerName("");
        MainActivityDriver.mMyStatus.setOndropLocation("");
        MainActivityDriver.mMyStatus.setPassengerOndropLocation("");
        MainActivityDriver.mMyStatus.setOnpickupLatitude("");
        MainActivityDriver.mMyStatus.setOnpickupLongitude("");
        MainActivityDriver.mMyStatus.setOndropLatitude("");
        MainActivityDriver.mMyStatus.setOndropLongitude("");
        MainActivityDriver.mMyStatus.setOndriverLatitude("");
        MainActivityDriver.mMyStatus.setOndriverLongitude("");
        DriverSystems.out.println("Comminggggg_cancel");
        Intent in = new Intent(getApplicationContext(), DriverMyStatus.class);
        in.setAction(Intent.ACTION_MAIN);
        in.addCategory(Intent.CATEGORY_LAUNCHER);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        ComponentName cn = new ComponentName(getApplicationContext(), DriverMyStatus.class);
        in.setComponent(cn);
        startActivity(in);
//        finish();
    }

    private boolean servicesConnected() {
        // Check that Google Play services is available
        final int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        // Google Play services was not available for some reason
        // Display an error dialog
        return ConnectionResult.SUCCESS == resultCode;
    }

    public void generateNotifications(Context context, String message, Class<?> class1, boolean cancelable, int Notification_ID) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String title = getString(R.string.app_name);
        Intent notificationIntent = new Intent(this, class1);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
        Notification myNotication;
        Notification.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(notificationChannel);

            builder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).setContentText(message).setContentTitle(title).setOngoing(true).setSmallIcon(getNotificationIcon()).setContentIntent(pendingIntent).setLargeIcon(((BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.ic_launcher)).getBitmap()).setStyle(new Notification.BigTextStyle().bigText(message)).setWhen(System.currentTimeMillis());
        } else {
            builder = new Notification.Builder(this).setAutoCancel(true).setTicker(getResources().getString(R.string.common_name)).setContentTitle(title).setContentText(message).setContentIntent(pendingIntent).setOngoing(true).setSmallIcon(getNotificationIcon()).setStyle(new Notification.BigTextStyle().bigText(message)).setLargeIcon(((BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher)).getBitmap());

        }

        myNotication = builder.build();

        myNotication.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(Notification_ID, myNotication);
        Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        try {
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification1);
            r.play();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_launcher : R.drawable.ic_launcher;
    }

    private boolean GPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private synchronized void getAndStoreColorValues(String result) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            Document doc = dBuilder.parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("*");

            DriverSystems.out.println("lislength" + nList.getLength());
            for (int i = 0; i < nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element2 = (Element) node;
                    DriverCL.nfields_byName.put(element2.getAttribute("name"), element2.getTextContent());

                }
            }
            getColorValueDetail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to logout user if status -101 and redirect to login page
     */
    private void forceLogout() {
        DriverServiceGenerator.API_BASE_URL = "";
        DriverSessionSave.saveSession("base_url", "", LocationUpdate.this);
        DriverSessionSave.saveSession("Id", "", LocationUpdate.this);
        DriverSessionSave.clearAllSession(LocationUpdate.this);
        stopSelf();
//        stopService(new Intent(this, WaitingTimerRun.class));
        Intent intent = new Intent(LocationUpdate.this, DriverUserLoginAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onDistanceCalled(LatLng pick, LatLng drop, double distance, double time, String result, String status) {
        DriverSystems.out.println("haiiiiiii " + "LocationUpdate " + pick.latitude + "__" + pick.longitude + "_____" + drop.latitude + "__" + drop.longitude + "___" + distance + "____" + status);
        if (status.equalsIgnoreCase("OK")) {

            DriverSessionSave.setGoogleDistance(DriverSessionSave.getGoogleDistance(LocationUpdate.this) + distance, LocationUpdate.this);

            DriverSessionSave.saveSession(DriverCommonData.LAST_KNOWN_LAT, "" + drop.latitude, LocationUpdate.this);
            DriverSessionSave.saveSession(DriverCommonData.LAST_KNOWN_LONG, "" + drop.longitude, LocationUpdate.this);
            if (DriverSessionSave.getSession(DriverCommonData.isGoogleDistance, LocationUpdate.this, true)) {
                DriverSessionSave.saveGoogleWaypoints(pick, drop, "google", distance, "" + "___" + startID + "____" + System.currentTimeMillis(), LocationUpdate.this);
                DriverSessionSave.saveWaypoints(pick, drop, "google", distance, "server" + "___" + startID, LocationUpdate.this);

            } else {
                DriverSessionSave.saveGoogleWaypoints(pick, drop, "mapbox", distance, "" + "___" + startID, LocationUpdate.this);
                DriverSessionSave.saveWaypoints(pick, drop, "mapbox", distance, "server" + "___" + startID, LocationUpdate.this);
            }
        } else {
            DriverSessionSave.setGoogleDistance(distance, LocationUpdate.this);
            DriverSessionSave.saveGoogleWaypoints(pick, drop, "haversine", distance, "UNKNOWN" + result, LocationUpdate.this);
            DriverSessionSave.saveSession(DriverCommonData.LAST_KNOWN_LAT, "" + drop.latitude, LocationUpdate.this);
            DriverSessionSave.saveSession(DriverCommonData.LAST_KNOWN_LONG, "" + drop.longitude, LocationUpdate.this);
            DriverSessionSave.saveWaypoints(pick, drop, "google_haversine", distance, "" + "___" + startID, LocationUpdate.this);
        }
        DISTANCE_CALCULATION_INPROGRESS = false;
    }

    private Notification getNotification() {

        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, DriverSplashAct.class), PendingIntent.FLAG_IMMUTABLE);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
        int notifyId = 10;
        Notification notification;
        Notification.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Action action = new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.driver_ic_launcher), DriverNC.getString(R.string.notiy_lanch_app), activityPendingIntent).build();
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).addAction(action).setContentText(DriverNC.getString(R.string.app_running)).setContentTitle(getResources().getString(R.string.app_name)).setOngoing(true).setSmallIcon(R.drawable.small_logo).setColor(ContextCompat.getColor(getBaseContext(), R.color.button_accept)).setWhen(System.currentTimeMillis());
        } else {
            builder = new Notification.Builder(this).addAction(0, getString(R.string.notiy_lanch_app) + ""/* + getTripStatus()*/, activityPendingIntent).setContentText(DriverNC.getString(R.string.app_running)).setContentTitle(getResources().getString(R.string.app_name)).setOngoing(true).setPriority(Notification.PRIORITY_HIGH).setSmallIcon(R.drawable.small_logo).setWhen(System.currentTimeMillis());
        }

        notification = builder.build();
        notificationManager.notify(notifyId, notification);
        return notification;
    }

    private void notificationforTrip() {
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, DriverMyStatus.class), PendingIntent.FLAG_IMMUTABLE);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
        int notifyId = 10;
        Notification notification;
        Notification.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Action action = new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.driver_ic_launcher), DriverNC.getString(R.string.notiy_lanch_app), activityPendingIntent).build();
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).addAction(action).setContentText("You have new trip").setContentTitle(getResources().getString(R.string.app_name)).setOngoing(true).setSmallIcon(R.drawable.small_logo).setWhen(System.currentTimeMillis());
        } else {
            builder = new Notification.Builder(this).addAction(0, getString(R.string.notiy_lanch_app) + ""/* + getTripStatus()*/, activityPendingIntent).setContentText("You have new trip").setContentTitle(getResources().getString(R.string.app_name)).setContentTitle(getResources().getString(R.string.app_name)).setOngoing(true).setPriority(Notification.PRIORITY_HIGH).setSmallIcon(R.drawable.small_logo).setWhen(System.currentTimeMillis());
        }

        notification = builder.build();
        //  notificationManager.notify(notifyId, notification);
        startForeground(notifyId, notification);
    }

//Trip Reject Api

    public class TripRejectLocationUpdate implements DriverAPIResult {
        String msg;
        JSONObject jsonObject;

        public TripRejectLocationUpdate(final String url, JSONObject data) {
            jsonObject = data;
            new DriverAPIService_Retrofit_JSON(LocationUpdate.this, this, data, false).execute(url);
        }


        @Override
        public void getResult(final boolean isSuccess, final String result) {
            Log.d("result", "result" + result);
            try {
                if (isSuccess) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                DriverCommonData.current_trip_accept = 0;

                                cancelNotifications(LocationUpdate.this, Notification_ID);
                            } catch (Exception e) {

                            }
                        }
                    }, 20 * 1000);
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 6) {
                        msg = json.getString("message");
                    } else if (json.getInt("status") == 7) {
                        msg = json.getString("message");
                    } else if (json.getInt("status") == 8) {
                        msg = json.getString("message");
                    } else if (json.getInt("status") != 6 || json.getInt("status") != 8 || json.getInt("status") != 3 || json.getInt("status") != 2 || json.getInt("status") != -1) {
                        msg = "Trip has been rejected";
                    } else {
                        msg = "Trip has been already cancelled";
                    }


                }
            } catch (final JSONException e) {

                e.printStackTrace();
            }
        }
    }


    public void cancelNotifications(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
        stopForeground(false);

    }

}


