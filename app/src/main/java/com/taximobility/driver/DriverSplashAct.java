package com.taximobility.driver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.taximobility.BuildConfig;
import com.taximobility.R;
import com.taximobility.driver.data.DriverCommonData;
import com.taximobility.driver.data.apiData.DriverApiRequestData;
import com.taximobility.driver.data.apiData.DriverCompanyDomainResponse;
import com.taximobility.driver.interfaces.DriverAPIResult;
import com.taximobility.driver.permission.DriverDevicePermissionActivityDriver;
import com.taximobility.driver.service.DriverAPIService_Retrofit_JSON_NoProgress;
import com.taximobility.driver.service.DriverBackgroundCoreConfig;
import com.taximobility.driver.service.DriverCoreClient;
import com.taximobility.driver.service.LocationUpdate;
import com.taximobility.driver.service.DriverRetrofitCallbackClass;
import com.taximobility.driver.service.DriverServiceGenerator;
import com.taximobility.driver.utils.DriverCL;
import com.taximobility.driver.utils.DriverCToast;
import com.taximobility.driver.utils.DriverFontHelper;
import com.taximobility.driver.utils.DriverLocationDb;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverNetworkStatus;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.driver.utils.DriverSystems;
import com.taximobility.driver.utils.Driver_Utils;
import com.taximobility.interfaces.AlertListener;
import com.taximobility.util.AppController;
import com.taximobility.util.NC;
import com.taximobility.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.mapbox.mapboxsdk.Mapbox;


/**
 * taximobilityapps@gmail.com
 * call2Taxi4travel
 * This class will be called initially on app launch.Here we will load all basic attributes from back end to run app further
 * ndot
 */
public class DriverSplashAct extends MainActivityDriver {
    private static final int MY_PERMISSIONS_REQUEST_GPS = 111;
    public static String REG_ID = "";
    private final int REQUEST_READ_PHONE_STATE = 292;
    public boolean askDomain = false;
    DriverLocationDb objLocationDb;
    private String mDeviceid;
    private LocationManager mLocationManager;
    private boolean isGPSEnabled;
    private int id = 0;
    private Dialog mDialog;
    private long getCore_Utc;
    private String getCoreLangTime;
    private String getCoreColorTime;
    private FrameLayout splashLayout;
    private Dialog myDialog;


    @Override
    public int setLayout() {
        // TODO Auto-generated method stub
        int curVersion = BuildConfig.VERSION_CODE;
        try {

            if (curVersion != 0 && DriverSessionSave.getSession("trip_id", DriverSplashAct.this).equals(""))
                if (DriverSessionSave.getSession(String.valueOf(curVersion), this).trim().equals("")) {
                    DriverSessionSave.saveSession("base_url", "", DriverSplashAct.this);
                    DriverSystems.out.println("chery_chkng_url_base2" + DriverSessionSave.getSession("base_url", DriverSplashAct.this));

                    DriverSessionSave.saveSession("api_key", "", DriverSplashAct.this);
                    DriverSessionSave.saveSession("encode", "", DriverSplashAct.this);
                    DriverSessionSave.saveSession("image_path", "", DriverSplashAct.this);
                    DriverSessionSave.saveSession(String.valueOf(curVersion), "No", DriverSplashAct.this);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.layout.driver_splash_lay;
    }

    @SuppressLint("NewApi")
    @Override
    public void Initialize() {
        // TODO Auto-generated method stub
        if (DriverNetworkStatus.isOnline(DriverSplashAct.this)) {
            init();
        } else {
            errorInSplash(DriverNC.getString(R.string.check_net_connection));
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        DriverSessionSave.ClearSessionOneTime(this);
    }

    /**
     * This method is used to load all basic initialisation
     */
    public void init() {
        AtomicInteger c = new AtomicInteger(0);

        DriverFontHelper.applyFont(this, findViewById(R.id.rootlay));
        splashLayout = findViewById(R.id.lay_splash);
        objLocationDb = new DriverLocationDb(DriverSplashAct.this);
        if (DriverCommonData.mDevice_id.equals("")) {
            if (!UUID.randomUUID().toString().equals("")) {
                mDeviceid = UUID.randomUUID().toString();
            } else {
                mDeviceid = DriverCommonData.mDevice_id_constant + c.incrementAndGet();
            }
            DriverCommonData.mDevice_id = mDeviceid;
        } else {

        }

//        mDeviceid = Secure.getString(SplashAct.this.getContentResolver(), Secure.ANDROID_ID);
        DriverCommonData.current_act = "SplashAct";
        if (DriverSessionSave.getSession("Lang", DriverSplashAct.this).equals("")) {
            DriverSessionSave.saveSession("Lang", "en", DriverSplashAct.this);
            DriverSessionSave.saveSession("Lang_Country", "en_GB", DriverSplashAct.this);
        }
       /* try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("SpalshActivity", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("SpalshActivity", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("SpalshActivity", "printHashKey()", e);
        }*/

//        getKeyHash("SHA");
//        getKeyHash("MD5");


    }

    /*
        private void getKeyHash(String hashStretagy) {
            PackageInfo info;
            try {
                info = getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md;
                    md = MessageDigest.getInstance(hashStretagy);
                    md.update(signature.toByteArray());
                    String something = new String(Base64.encode(md.digest(), 0));
                    Log.e("KeyHash  -->>>>>>>>>>>>" , something+"____hashStretagy"+hashStretagy);

                    // Notification.registerGCM(this);
                }
            } catch (PackageManager.NameNotFoundException e1) {
                Log.e("name not found" , e1.toString());
            } catch (NoSuchAlgorithmException e) {
                Log.e("no such an algorithm" , e.toString());
            } catch (Exception e) {
                Log.e("exception" , e.toString());
            }
        }*/
    private boolean VersionCheck() {
        try {
            String newVersion = DriverSessionSave.getSession("play_store_version", DriverSplashAct.this).equals("") ? "0" : DriverSessionSave.getSession("play_store_version", DriverSplashAct.this);
            int curVersion = BuildConfig.VERSION_CODE;

            System.err.println("New version" + newVersion + "curVersion" + curVersion + "---" + (curVersion < value(newVersion)));
            return curVersion < Integer.parseInt(newVersion);
        } catch (Exception e) {
            // TODO: handle exception

            e.printStackTrace();
        }
        return false;
    }


    /**
     * Method to check is version update is force update or not
     *
     * @return
     */
    private boolean forceUpdateCheck() {
        try {
            String lastForceUpdateVersion = DriverSessionSave.getSession(DriverCommonData.LAST_FORCEUPDATE_VERSION, DriverSplashAct.this).equals("") ? "0" : DriverSessionSave.getSession(DriverCommonData.LAST_FORCEUPDATE_VERSION, DriverSplashAct.this);
            int curVersion = BuildConfig.VERSION_CODE;

            System.err.println("New version" + lastForceUpdateVersion + "curVersion" + curVersion);
            if (curVersion < value(lastForceUpdateVersion)) {
                return true;
            } else return value(lastForceUpdateVersion) <= 0;

        } catch (Exception e) {
            // TODO: handle exception

            e.printStackTrace();
        }
        return false;
    }

    /**
     * Return long value for given string
     */
    private long value(String string) {
        string = string.trim();
        if (string.contains(".")) {
            final int index = string.lastIndexOf(".");
            return value(string.substring(0, index)) * 100 + value(string.substring(index + 1));
        } else {
            return Long.parseLong(string);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String token = FirebaseInstanceId.getInstance().getToken();
        DriverSystems.out.println("resume1" + token);
        if (getIntent() != null && getIntent().getStringExtra("alert_message") != null) {
            String alertSchedule = getIntent().getStringExtra("alert_schedule");
            if (alertSchedule != null && alertSchedule.equals("1")) {
                Intent i = new Intent(DriverSplashAct.this, DriverMyStatus.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                i.putExtra("alert_message", getIntent().getStringExtra("alert_message"));
                i.putExtra("alert_schedule", "1");
                startActivity(i);
                finish();
            } else {
                if (myDialog != null && myDialog.isShowing())
                    myDialog.dismiss();
                Toast.makeText(context,getIntent().getStringExtra("alert_message"),Toast.LENGTH_LONG);
                DriverSplashAct.this.finish();
//                myDialog = Driver_Utils.alert_view_dialog(DriverSplashAct.this, "" +
//                                DriverNC.getString(R.string.message),
//                        "" + getIntent().getStringExtra("alert_message"),
//                        "" + DriverNC.getString(R.string.ok),
//                        "",
//                        false, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                DriverSplashAct.this.finish();
//                            }
//                        }, null, "");
            }
        } else
            LoadDataForsplash();
    }


    private void LoadDataForsplash() {
        String reqString = Build.MANUFACTURER;
        /*if (DriverSessionSave.getSession("settings_alert", DriverSplashAct.this).isEmpty()) {
            if (reqString.toLowerCase().contains("huawei")) {
                HuaweiDeviceAlert();
            } else if (reqString.toLowerCase().contains("vivo")) {
                vivoDeviceAlert();
            } else if (reqString.toLowerCase().contains("xiaomi")) {
                xiaomiDeviceAlert();
            } else if (reqString.toLowerCase().contains("oppo")) {
                oppoDeviceAlert();
            } else {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverSplashAct.this);
                LoadDataForsplash();
            }
        } else */
        if (DriverNetworkStatus.isOnline(DriverSplashAct.this)) {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {


                    getGPS();


                   /* if (ActivityCompat.checkSelfPermission(SplashAct.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(SplashAct.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        dialog1 = Utils.alert_view_dialog(SplashAct.this, "", NC.getResources().getString(R.string.str_loc), NC.getResources().getString(R.string.yes), NC.getResources().getString(R.string.no), false, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                ActivityCompat.requestPermissions(SplashAct.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_GPS);

                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                                finish();
                            }
                        }, "");
                    } else {
                        if (ActivityCompat.checkSelfPermission(SplashAct.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(SplashAct.this,
                                    new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE},
                                    REQUEST_READ_PHONE_STATE);
                        } else {
                            getGPS();
                        }
                    }*/
                }
            }, 100);

        }
    }


    public void HuaweiDeviceAlert() {

        Utility.actionSheet(DriverSplashAct.this, "" + String.format(DriverNC.getResources().getString(R.string.huawei_msg)), DriverNC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverSplashAct.this);
                EnableHuaweiProtectedApps();
            }

            @Override
            public void onFailure() {

            }
        });
        /*
        Driver_Utils.alert_view_dialog(DriverMeAct.this,
                DriverNC.getResources().getString(R.string.huawei_title),
                "" + String.format(DriverNC.getResources().getString(R.string.huawei_msg)),
                "" + DriverNC.getResources().getString(R.string.ok), "", false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverMeAct.this);
                        EnableHuaweiProtectedApps();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, "");

         */
    }

    public void vivoDeviceAlert() {
        Utility.actionSheet(DriverSplashAct.this, "" + String.format(DriverNC.getResources().getString(R.string.auto_start_msg)), DriverNC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverSplashAct.this);
                autostartVivo();
            }

            @Override
            public void onFailure() {

            }
        });
        /*
        Driver_Utils.alert_view_dialog(DriverMeAct.this,
                DriverNC.getResources().getString(R.string.auto_start),
                "" + String.format(DriverNC.getResources().getString(R.string.auto_start_msg)),
                "" + DriverNC.getResources().getString(R.string.ok), "", false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverMeAct.this);
                        autostartVivo();
                    }
                }, (dialog, which) -> dialog.dismiss(), "");

         */
    }

    public void xiaomiDeviceAlert() {
        Utility.actionSheet(DriverSplashAct.this, "" + String.format(DriverNC.getResources().getString(R.string.auto_start_msg)), DriverNC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverSplashAct.this);
                try {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {

            }
        });
        /*
        Driver_Utils.alert_view_dialog(DriverMeAct.this,
                DriverNC.getResources().getString(R.string.auto_start),
                "" + String.format(DriverNC.getResources().getString(R.string.auto_start_msg)),
                "" + DriverNC.getResources().getString(R.string.ok), "", false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverMeAct.this);
                        try {
//                            Intent intent = new Intent();
//                            intent.setComponent(new ComponentName("com.miui.securitycenter",
//                                    "com.miui.permcenter.autostart.AutoStartManagementActivity"));
//                            startActivity(intent);

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, (dialog, which) -> dialog.dismiss(), "");

         */

    }

    public void oppoDeviceAlert() {
        Utility.actionSheet(DriverSplashAct.this, "" + String.format(DriverNC.getResources().getString(R.string.power_saving_msg)), DriverNC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverSplashAct.this);
                try {
                    Intent intentBatteryUsage = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
                    context.startActivity(intentBatteryUsage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {

            }
        });
        /*
        Driver_Utils.alert_view_dialog(DriverMeAct.this,
                DriverNC.getResources().getString(R.string.power_saving),
                "" + String.format(DriverNC.getResources().getString(R.string.power_saving_msg)),
                "" + DriverNC.getResources().getString(R.string.ok), "", false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverMeAct.this);
                        try {
                            Intent intentBatteryUsage = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
                            context.startActivity(intentBatteryUsage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, (dialog, which) -> dialog.dismiss(), "");

         */
    }

    private void EnableHuaweiProtectedApps() {
        try {
            String cmd = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cmd = "am start -n com.huawei.systemmanager/.appcontrol.activity.StartupAppControlActivity";
            } else {
                cmd = "am start -n com.huawei.systemmanager/.optimize.process.ProtectActivity";
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cmd += " --user " + getUserSerial();
            }

            Runtime.getRuntime().exec(cmd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void autostartVivo() {

        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.iqoo.secure",
                    "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"));
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.vivo.permissionmanager",
                        "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                context.startActivity(intent);
            } catch (Exception ex) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName("com.iqoo.secure",
                            "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager");
                    context.startActivity(intent);
                } catch (Exception exx) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private String getUserSerial() {
//noinspection ResourceType
        @SuppressLint("WrongConstant")
        Object userManager = getSystemService("user");
        if (null == userManager) return "";

        try {
            Method myUserHandleMethod = android.os.Process.class.getMethod("myUserHandle", (Class<?>[]) null);
            Object myUserHandle = myUserHandleMethod.invoke(android.os.Process.class, (Object[]) null);
            Method getSerialNumberForUser = userManager.getClass().getMethod("getSerialNumberForUser", myUserHandle.getClass());
            Long userSerial = (Long) getSerialNumberForUser.invoke(userManager, myUserHandle);

            if (userSerial != null) {
                return String.valueOf(userSerial);
            } else {
                return "";
            }
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ignored) {
        }
        return "";
    }

    /**
     * Getting gps and all basic urls
     */
 /*   public void getGPS() {
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.getProvider(LocationManager.GPS_PROVIDER);
        isGPSEnabled = true;
        if (isGPSEnabled) {
            //  gps_alert();
            if (isOnline()) {
                if (!(VersionCheck())) {
                    callApi();
                } else
                    versionAlert(SplashAct.this);
            } else {
                CToast.ShowToast(SplashAct.this, "" + NC.getResources().getString(R.string.check_net_connection));
            }
        } else {
            gpsalert(SplashAct.this, false);
        }
    }*/
    public void getGPS() {
        if (isOnline()) {
            if (!(VersionCheck())) {
                callApi();
            } else
                versionAlert(DriverSplashAct.this);
        } else {
            DriverCToast.ShowToast(DriverSplashAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
        }
    }

    void callApi() {
        DriverSystems.out.println("____" + id++);

        if (DriverSessionSave.getSession("base_url", DriverSplashAct.this).trim().equals(""))
            if (askDomain)
                getUrl();
            else
                urlApi("", "", "");
        else {
            setLocale();
            getAndStoreStringValues(DriverSessionSave.getSession("wholekey", DriverSplashAct.this));
            getAndStoreColorValues(DriverSessionSave.getSession("wholekeyColor", DriverSplashAct.this));
            if (!DriverSessionSave.getSession("base_url", DriverSplashAct.this).trim().equals("")) {
                DriverServiceGenerator.API_BASE_URL = DriverSessionSave.getSession("base_url", DriverSplashAct.this);

                MoveToNavigatorPanel();
            } else {
                if (askDomain)
                    getUrl();
                else
                    urlApi("", "", "");

            }
        }
    }

    private void MoveToNavigatorPanel() {
        startService(new Intent(DriverSplashAct.this, DriverBackgroundCoreConfig.class));
        Intent i;
//        if (DriverSessionSave.getSession("user_privacy_policy", DriverSplashAct.this).equals("")) {
//            i = new Intent(DriverSplashAct.this, DriverDevicePermissionActivityDriver.class);
//            startActivity(i);
//            finish();
//        } else
            if (DriverSessionSave.getSession("Id", DriverSplashAct.this).equals("")) {
            i = new Intent(DriverSplashAct.this, DriverUserLoginAct.class);
            startActivity(i);
            finish();
        } else {
            if (DriverSessionSave.getSession("trip_id", DriverSplashAct.this).equals("")) {
                i = new Intent(DriverSplashAct.this, DriverMyStatus.class);
                startActivity(i);
                finish();
            } else {
                if (DriverSessionSave.getSession("travel_status", DriverSplashAct.this).equals("5")) {
                    i = new Intent(DriverSplashAct.this, DriverTripHistoryAct.class);
                    startActivity(i);
                    finish();
                } else {
                    if (DriverSessionSave.getSession(DriverCommonData.IS_STREET_PICKUP, DriverSplashAct.this, false)) {
                        i = new Intent(DriverSplashAct.this, DriverStreetPickUpAct.class);
                        startActivity(i);
                        finish();
                    } else {
                        i = new Intent(DriverSplashAct.this, DriverOngoingAct.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        }
    }

    /**
     * Setting Language Configuration
     */
    public void setLocale() {
        if (DriverSessionSave.getSession("Lang", DriverSplashAct.this).equals("")) {
            DriverSessionSave.saveSession("Lang", "en", DriverSplashAct.this);
            DriverSessionSave.saveSession("Lang_Country", "en_GB", DriverSplashAct.this);
        }


        Configuration config = new Configuration();
        String language = DriverSessionSave.getSession("Lang", DriverSplashAct.this);
        String langcountry = DriverSessionSave.getSession("Lang_Country", DriverSplashAct.this);
        String[] arry = langcountry.split("_");
        config.locale = new Locale(language, arry[1]);
        Locale.setDefault(new Locale(language, arry[1]));
        DriverSplashAct.this.getBaseContext().getResources().updateConfiguration(config, DriverSplashAct.this.getResources().getDisplayMetrics());

    }

    public void errorInSplash(String message) {
        Utility.actionSheet(DriverSplashAct.this, message, DriverNC.getString(R.string.c_tryagain), DriverNC.getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

            @Override
            public void onFailure() {
                Activity activity = DriverSplashAct.this;

                final Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        /*
        dialog1 = Driver_Utils.alert_view_dialog(DriverSplashAct.this, DriverNC.getString(R.string.message), message, DriverNC.getString(R.string.c_tryagain), DriverNC.getString(R.string.cancel), false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Activity activity = DriverSplashAct.this;

                final Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.finish();
                dialog.dismiss();
            }
        }, "");

         */
    }


    @Override
    protected void onDestroy() {
        if (dialog1 != null)
            Driver_Utils.closeDialog(dialog1);
        super.onDestroy();
    }


    /**
     * Getting Subdomain
     */
    private void getUrl() {
//        if (BuildConfig.DEBUG) {
//            final View view1 = View.inflate(SplashAct.this, R.layout.domain_lay, null);
//            if (myDialog != null && myDialog.isShowing())
//                myDialog.cancel();
//            myDialog = new Dialog(SplashAct.this, R.style.NewDialog);
//            myDialog.setContentView(view1);
//            myDialog.setCancelable(false);
//            myDialog.setCanceledOnTouchOutside(false);
//            FontHelper.applyFont(SplashAct.this, myDialog.findViewById(R.id.inner_content));
//            myDialog.setCancelable(true);
//            myDialog.show();
//
//            final EditText edt_url = myDialog.findViewById(R.id.edt_url);
//            final EditText edt_domain = myDialog.findViewById(R.id.edt_domain);
//            EditText edt_access = myDialog.findViewById(R.id.edt_accesskey);
//            edt_access.setText("demoone");
//            SessionSave.saveSession("api_key", ((EditText) myDialog.findViewById(R.id.edt_company_key)).getText().toString(), SplashAct.this);
//            edt_url.setText("https://mongo.taximobility.com/driverapi201/index/");
//            edt_domain.setText("taximobility.com");
//            Button btn_ok = myDialog.findViewById(R.id.btn_ok);
//            btn_ok.setOnClickListener(new View.OnClickListener() {
//                private String access_key, url, domain;
//
//                @Override
//                public void onClick(final View v) {
//
//                    try {
//                        access_key = edt_access.getText().toString();
//                        url = edt_url.getText().toString();
//                        domain = edt_domain.getText().toString();
//                        if (!access_key.equals("") && !url.equals("") && !domain.equals("")) {
//                            urlApi(access_key, url, domain);
//                            myDialog.dismiss();
//                        } else {
//                            CToast.ShowToast(SplashAct.this, "Please enter all details");
//                        }
//
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } else {
        final View view = View.inflate(DriverSplashAct.this, R.layout.driver_forgot_popup, null);
        if (mDialog != null && mDialog.isShowing())
            mDialog.cancel();
        mDialog = new Dialog(DriverSplashAct.this, R.style.NewDialog);
        mDialog.setContentView(view);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        DriverFontHelper.applyFont(DriverSplashAct.this, mDialog.findViewById(R.id.inner_content));
        mDialog.setCancelable(true);
        mDialog.show();
        final EditText mail = mDialog.findViewById(R.id.forgotmail);
        mail.setHint(DriverNC.getResources().getString(R.string.domain_hint));
        final Button OK = mDialog.findViewById(R.id.okbtn);
        final Button Cancel = mDialog.findViewById(R.id.cancelbtn);
        Cancel.setVisibility(View.GONE);
        int maxLength = 64;
        setEditTextMaxLength(maxLength, mail);

        OK.setOnClickListener(new OnClickListener() {
            private String Email;

            @Override
            public void onClick(final View v) {

                try {
                    Email = mail.getText().toString();
                    if (Email.length() > 2) {
                        urlApi(Email, "", "");
                        mail.setText("");
                        mDialog.dismiss();
                    } else {
                        DriverCToast.ShowToast(DriverSplashAct.this, "Please enter valid access key");
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });
        //}
    }

    public void setEditTextMaxLength(int length, EditText edt_text) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        edt_text.setFilters(FilterArray);
    }

    /**
     * Getting base path
     */
    private void urlApi(String keyy, String mUrl, String domain) {
        String url = "";
        String str_domain = "";
        String key = "";
        try {
//            url = "http://192.168.1.125:1044/driverapi113/index/";
//            url = "http://192.168.2.140:1001/driverapi113/index/";
//            url = "http://192.168.1.115:2007/driverapi113/index/";
//              url = "http://192.168.1.83:1040/driverapi113/index/";
//            url = "http://192.168.1.115:9999/driverapi113/index/";
//            url = "http://192.168.1.115:2007/driverapi113/index/";
//            url = "http://loadtest.taximobility.com/driverapi113/index/";
//            url = "http://mongo.ndotsocial.com/driverapi113/index/";
//            url = "http://192.168.1.246:1005/driverapi113/index/";
//            url = "http://mongo.taximobility.com/driverapi113/index/";

            if (!mUrl.equals("") && !domain.equals("")) {
                url = mUrl;
                str_domain = domain;
                key = keyy;
            } else {
                url = "https://mongo.taximobility.com/driverapi201/index/";
                str_domain = "taximobility.com";
                key = keyy;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DriverSessionSave.saveSession(DriverCommonData.FIREBASE_KEY, "0", DriverSplashAct.this);
        String mUUID = "";
        if (!UUID.randomUUID().toString().equals("")) {
            mUUID = UUID.randomUUID().toString();
        } else {
            mUUID = DriverCommonData.mDevice_id_constant;
        }
//        SessionSave.saveSession(CommonData.DEVICE_ID, Secure.getString(getContentResolver(), Secure.ANDROID_ID), SplashAct.this);
        DriverSessionSave.saveSession(DriverCommonData.DEVICE_ID, mUUID, DriverSplashAct.this);
        //        CoreClient client = new ServiceGenerator(url, SplashAct.this, false).createService(CoreClient.class);
        DriverCoreClient client = AppController.getInstance().getCheckCompanyDomainapiManager_driver(url);
        DriverApiRequestData.BaseUrl request = new DriverApiRequestData.BaseUrl();
        request.company_domain = key;
        request.company_main_domain = str_domain;
        request.device_type = "1";
        DriverSessionSave.saveSession(DriverCommonData.DOMAIN_URL, "", DriverSplashAct.this);
        DriverSessionSave.saveSession(DriverCommonData.DOMAIN_URL, url, DriverSplashAct.this);
        DriverSessionSave.saveSession(DriverCommonData.COMPANY_DOMAIN, "", DriverSplashAct.this);
        DriverSessionSave.saveSession(DriverCommonData.COMPANY_DOMAIN, str_domain, DriverSplashAct.this);
        DriverSessionSave.saveSession(DriverCommonData.ACCESS_KEY, "", DriverSplashAct.this);
        DriverSessionSave.saveSession(DriverCommonData.ACCESS_KEY, key, DriverSplashAct.this);
        showLoading(DriverSplashAct.this);
        Call<DriverCompanyDomainResponse> response = client.callData(DriverServiceGenerator.COMPANY_KEY, request);
        response.enqueue(new DriverRetrofitCallbackClass<>(DriverSplashAct.this, new Callback<DriverCompanyDomainResponse>() {
            @Override
            public void onResponse(Call<DriverCompanyDomainResponse> call, Response<DriverCompanyDomainResponse> response) {
                cancelLoading();
                if (response.isSuccessful() && response.body() != null) {
                    DriverCompanyDomainResponse cr = response.body();
                    if (cr != null) {
                        if (cr.status.trim().equals("1")) {
                            DriverSessionSave.saveSession("show_hauwai_alert", true, DriverSplashAct.this);

//                            SessionSave.saveSession("base_url", cr.baseurl, SplashAct.this);
                            if (cr.https_base_url != null)
                                DriverSessionSave.saveSession("base_url", cr.https_base_url, DriverSplashAct.this);
                            DriverSystems.out.println("chery_chkng_url_base1" + DriverSessionSave.getSession("base_url", DriverSplashAct.this));

                            DriverSessionSave.saveSession("api_key", cr.apikey, DriverSplashAct.this);
                            DriverSessionSave.saveSession("encode", cr.encode, DriverSplashAct.this);
//                            if (cr.auth_key != null)
//                                SessionSave.saveSession(AUTH_KEY, cr.auth_key, SplashAct.this);

//                            ServiceGenerator.API_BASE_URL = cr.baseurl;
                            if (cr.https_base_url != null)
                                DriverServiceGenerator.API_BASE_URL = cr.https_base_url;
                            DriverSessionSave.saveSession("image_path", cr.androidPaths.static_image, DriverSplashAct.this);
                            String totalLanguage = "";
                            String defaultLanguage = cr.default_language;
                            if (cr.androidPaths.driver_language != null) {

                                for (int i = 0; i < cr.androidPaths.driver_language.size(); i++) {
                                    String key_ = "";
                                    totalLanguage += (cr.androidPaths.driver_language.get(i).language).replaceAll(".xml", "") + "____";
                                    DriverSessionSave.saveSession("LANG" + i, cr.androidPaths.driver_language.get(i).language, DriverSplashAct.this);
                                    DriverSessionSave.saveSession("LANGTemp" + i, cr.androidPaths.driver_language.get(i).design_type, DriverSplashAct.this);

                                    DriverSessionSave.saveSession("LANGCode" + i, cr.androidPaths.driver_language.get(i).language_code, DriverSplashAct.this);

                                    DriverSessionSave.saveSession(cr.androidPaths.driver_language.get(i).language, cr.androidPaths.driver_language.get(i).url, DriverSplashAct.this);
                                    if (cr.androidPaths.driver_language.get(i).language_code.equalsIgnoreCase(defaultLanguage)) {
                                        DriverSessionSave.saveSession("LANGTempDef", cr.androidPaths.driver_language.get(i).design_type, DriverSplashAct.this);
                                        DriverSessionSave.saveSession("LANGDef", cr.androidPaths.driver_language.get(i).language, DriverSplashAct.this);
                                        DriverSessionSave.saveSession("Lang", cr.androidPaths.driver_language.get(i).language_code, DriverSplashAct.this);
                                    }
                                }
                                if (DriverSessionSave.getSession("LANGDef", DriverSplashAct.this).trim().equals(""))
                                    DriverSessionSave.saveSession("LANGDef", DriverSessionSave.getSession("LANG0", DriverSplashAct.this), DriverSplashAct.this);
                                if (DriverSessionSave.getSession("LANGTempDef", DriverSplashAct.this).trim().equals(""))
                                    DriverSessionSave.saveSession("LANGTempDef", DriverSessionSave.getSession("LANGTemp0", DriverSplashAct.this), DriverSplashAct.this);
                                DriverSessionSave.saveSession("lang_json", totalLanguage, DriverSplashAct.this);
                                if (DriverSessionSave.getSession("Lang", DriverSplashAct.this).equals(""))
                                    DriverSessionSave.saveSession("Lang", cr.androidPaths.driver_language.get(0).language_code.replaceAll(".xml", ""), DriverSplashAct.this);
                            }
                            DriverSessionSave.saveSession("colorcode", cr.androidPaths.colorcode, DriverSplashAct.this);
                            if (mDialog != null)
                                mDialog.dismiss();
                            String url = "type=getcoreconfig";
                            new CoreConfigCall(url);

                        } else {
                            alert_view_company(DriverSplashAct.this, DriverNC.getString(R.string.message), cr.message, DriverNC.getString(R.string.ok), DriverNC.getString(R.string.cancel));
                        }
                    } else {
                        alert_view_company(DriverSplashAct.this, DriverNC.getString(R.string.message), DriverNC.getString(R.string.server_error), DriverNC.getString(R.string.ok), DriverNC.getString(R.string.cancel));
                    }
                } else {
                    alert_view_company(DriverSplashAct.this, DriverNC.getString(R.string.message), DriverNC.getString(R.string.server_error), DriverNC.getString(R.string.ok), DriverNC.getString(R.string.cancel));
                }
            }

            @Override
            public void onFailure(Call<DriverCompanyDomainResponse> call, Throwable t) {
                cancelLoading();
//                CToast.ShowToast(SplashAct.this, NC.getString(R.string.server_error));
                alert_view_company(DriverSplashAct.this, DriverNC.getString(R.string.message), DriverNC.getString(R.string.server_error), DriverNC.getString(R.string.ok), DriverNC.getString(R.string.cancel));
            }
        }));
    }

    /**
     * Adding string files to Local hashmap
     */
    private synchronized void getAndStoreStringValues(String result) {
        try {


            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            Document doc = dBuilder.parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("*");

            for (int i = 0; i < nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;
                    DriverNC.nfields_byName.put(element2.getAttribute("name"), element2.getTextContent());
                }
            }
            getValueDetail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getting String values from local
     */
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
            DriverNC.nfields_byID.put(DriverNC.fields_id.get(h), DriverNC.nfields_byName.get(h));
            // do stuff
        }

    }

    /**
     * Getting Color values from local hash map
     */
    synchronized void getColorValueDetail() {
        Field[] fieldss = R.color.class.getDeclaredFields();
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
        }

    }

    /**
     * Adding color files to Local hashmap
     */
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

    public void alert_view_company(Context mContext, String title, String message, String success_txt, String failure_txt) {
        Utility.actionSheet(DriverSplashAct.this, message, success_txt, failure_txt, false, new AlertListener() {
            @Override
            public void onSuccess() {
                getUrl();
            }

            @Override
            public void onFailure() {
                finish();
            }
        });
        /*
        dialog1 = Driver_Utils.alert_view_dialog(DriverSplashAct.this, title, message, success_txt,
                failure_txt, true, (dialog, which) -> {
                    dialog.dismiss();
                    getUrl();
                }, (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                }, "");

         */
    }

    public void alert_view_date(Context mContext, String title, String message, String success_txt, String failure_txt) {
        Utility.actionSheet(DriverSplashAct.this, message, success_txt, failure_txt, false, new AlertListener() {
            @Override
            public void onSuccess() {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
            }

            @Override
            public void onFailure() {
                finish();
            }
        });
        /*
        dialog1 = Driver_Utils.alert_view_dialog(DriverSplashAct.this, title, message, success_txt, failure_txt, true, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
            }
        }, (dialog, which) -> {
            dialog.dismiss();
            finish();
        }, "");

         */
    }

    /**
     * Alert dialog to show version alert
     */
    public void versionAlert(final Context mContext) {
        String negativeBtnText;
        boolean forceUpdate = forceUpdateCheck();
        if (forceUpdate) {
            negativeBtnText = DriverNC.getResources().getString(R.string.cancel);
        } else {
            negativeBtnText = DriverNC.getResources().getString(R.string.version_up_later);
        }
        Utility.actionSheetCancel(DriverSplashAct.this, "" + DriverNC.getResources().getString(R.string.version_up_message), "" + DriverNC.getResources().getString(R.string.version_up_now), negativeBtnText, false, new AlertListener() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + mContext.getPackageName()));
                mContext.startActivity(intent);
            }

            @Override
            public void onFailure() {
                if (forceUpdate) {
                    DriverSplashAct.this.finish();
                    if (DriverSessionSave.getSession("trip_id", DriverSplashAct.this).equals(""))
                        stopService(new Intent(DriverSplashAct.this, LocationUpdate.class));
                } else {
                    callApi();
                }
            }
        });
        /*
        dialog1 = Driver_Utils.alert_view_dialog(DriverSplashAct.this, "" + DriverNC.getResources().getString(R.string.version_up_title), "" + DriverNC.getResources().getString(R.string.version_up_message), "" + DriverNC.getResources().getString(R.string.version_up_now), negativeBtnText, false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + mContext.getPackageName()));
                mContext.startActivity(intent);
                dialog.dismiss();
            }
        }, (dialog, which) -> {
            dialog.dismiss();
            if (forceUpdate) {
                DriverSplashAct.this.finish();
                if (DriverSessionSave.getSession("trip_id", DriverSplashAct.this).equals(""))
                    stopService(new Intent(DriverSplashAct.this, LocationUpdate.class));
            } else {
                callApi();
            }
        }, "");

         */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_GPS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    } else {

                        finish();
                    }
                }
                return;
            }
            case REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        getGPS();
                    }
                }
            }
        }
    }


    /**
     * Method to logout user if status -101 and redirect to login page
     *
     * @param message - To intimate user by showing alert message
     */
    private void forceLogout(String message) {
        DriverCToast.ShowToast(DriverSplashAct.this, message);
        DriverServiceGenerator.API_BASE_URL = "";
        DriverSessionSave.saveSession("base_url", "", DriverSplashAct.this);
        DriverSessionSave.saveSession("Id", "", DriverSplashAct.this);
        DriverSessionSave.clearAllSession(DriverSplashAct.this);
        stopService(new Intent(this, LocationUpdate.class));
        finish();
    }


    /**
     * Getting Language Files from Server
     */
    private class callString implements DriverAPIResult {
        public callString(final String url) {
            // TODO Auto-generated constructor stub

            String urls = DriverSessionSave.getSession("currentStringUrl", DriverSplashAct.this);
            if (urls.equals("")) {
                urls = DriverSessionSave.getSession(DriverSessionSave.getSession("LANGDef", DriverSplashAct.this), DriverSplashAct.this);
                if (DriverSessionSave.getSession("LANGTempDef", DriverSplashAct.this).trim().equalsIgnoreCase("RTL")) {
                    DriverSessionSave.saveSession("Lang_Country", "ar_EG", DriverSplashAct.this);
                    DriverSessionSave.saveSession("Lang", "ar", DriverSplashAct.this);
                    Configuration config = new Configuration();
                    String language = DriverSessionSave.getSession("Lang", DriverSplashAct.this);
                    String langcountry = DriverSessionSave.getSession("Lang_Country", DriverSplashAct.this);
                    String[] arry = langcountry.split("_");
                    config.locale = new Locale(language, arry[1]);
                    Locale.setDefault(new Locale(language, arry[1]));
                }
            }
            new DriverAPIService_Retrofit_JSON_NoProgress(DriverSplashAct.this, this, null, true, urls, true).execute();
        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            if (isSuccess) {

                setLocale();
                getAndStoreStringValues(result);
                DriverSessionSave.saveSession("wholekey", result, DriverSplashAct.this);

                if (DriverSessionSave.getSession("wholekeyColor", DriverSplashAct.this).trim().equals("") || !DriverSessionSave.getSession(DriverCommonData.PASSENGER_COLOR_TIME, DriverSplashAct.this).equals(getCoreColorTime))
                    new callColor("");
                else {
                    Intent i = null;
//                    if (CommonData.isCurrentTimeZone(getCore_Utc)) {

//                    if (DriverSessionSave.getSession("user_privacy_policy", DriverSplashAct.this).equals("")) {
//                        i = new Intent(DriverSplashAct.this, DriverDevicePermissionActivityDriver.class);
//                        startActivity(i);
//                        finish();
//                    } else {

                        if (DriverSessionSave.getSession("Id", DriverSplashAct.this).equals("")) {
                            i = new Intent(DriverSplashAct.this, DriverUserLoginAct.class);
                            startActivity(i);
                            finish();
                        } else {
                            if (DriverSessionSave.getSession("trip_id", DriverSplashAct.this).equals("")) {
                                i = new Intent(DriverSplashAct.this, DriverMyStatus.class);
                                startActivity(i);
                                finish();
                            } else {
                                if (DriverSessionSave.getSession("travel_status", DriverSplashAct.this).equals("5")) {
                                    i = new Intent(DriverSplashAct.this, DriverTripHistoryAct.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    if (DriverSessionSave.getSession(DriverCommonData.IS_STREET_PICKUP, DriverSplashAct.this, false)) {
                                        i = new Intent(DriverSplashAct.this, DriverStreetPickUpAct.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        i = new Intent(DriverSplashAct.this, DriverOngoingAct.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            }
                        }

//                    }
//                    } else {
//                        cancelLoading();
//                        alert_view_date(SplashAct.this, NC.getString(R.string.message), NC.getString(R.string.date_change), NC.getString(R.string.ok), NC.getString(R.string.cancel));
//                    }
                    if (!DriverSessionSave.getSession("base_url", DriverSplashAct.this).trim().equals("")) {
                        DriverServiceGenerator.API_BASE_URL = DriverSessionSave.getSession("base_url", DriverSplashAct.this);
                        new callColor("");
                    }
                }

            } else {
                errorInSplash(getString(R.string.error_in_string));
            }
        }
    }

    /**
     * Getting Color Files from Server and response parsing
     */
    private class callColor implements DriverAPIResult {
        public callColor(final String url) {

            // TODO Auto-generated constructor stub


            new DriverAPIService_Retrofit_JSON_NoProgress(DriverSplashAct.this, this, null, true, DriverSessionSave.getSession("colorcode", DriverSplashAct.this).replace("DriverAppColor", "driverAppColors"), true).execute();


        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            if (isSuccess) {
                getAndStoreColorValues(result);
                DriverSessionSave.saveSession("wholekeyColor", result, DriverSplashAct.this);


                Intent i = null;
//                if (DriverSessionSave.getSession("user_privacy_policy", DriverSplashAct.this).equals("")) {
//                    i = new Intent(DriverSplashAct.this, DriverDevicePermissionActivityDriver.class);
//                    startActivity(i);
//                    finish();
//                } else {
                    if (DriverSessionSave.getSession("Id", DriverSplashAct.this).equals("")) {
                        i = new Intent(DriverSplashAct.this, DriverUserLoginAct.class);
                        startActivity(i);
                        finish();
                    } else {
                        if (DriverSessionSave.getSession("trip_id", DriverSplashAct.this).equals("")) {
                            i = new Intent(DriverSplashAct.this, DriverMyStatus.class);
                            startActivity(i);
                            finish();
                        } else if (DriverSessionSave.getSession(DriverCommonData.IS_STREET_PICKUP, DriverSplashAct.this, false)) {
                            i = new Intent(DriverSplashAct.this, DriverStreetPickUpAct.class);
                            startActivity(i);
                            finish();
                        } else {
                            i = new Intent(DriverSplashAct.this, DriverOngoingAct.class);
                            startActivity(i);
                            finish();
                        }
                    }

//                }

            } else {
                errorInSplash(getString(R.string.error_in_color));
            }

        }
    }

    /**
     * CoreConfig method API call and response parsing.
     */
    public class CoreConfigCall implements DriverAPIResult {
        CoreConfigCall(final String url) {
            new DriverAPIService_Retrofit_JSON_NoProgress(DriverSplashAct.this, this, "", true).execute(url);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            DriverSystems.out.println(result);
            if (isSuccess) {

                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {

                        if (json.has("gt_lst_time"))

                            DriverSessionSave.saveSession(DriverCommonData.GETCORE_LASTUPDATE, json.getString("gt_lst_time"), DriverSplashAct.this);
                        if (json.has(DriverCommonData.ACTIVITY_BG))
                            DriverSessionSave.saveSession(DriverCommonData.ACTIVITY_BG, json.getString(DriverCommonData.ACTIVITY_BG), DriverSplashAct.this);
                        if (json.has(DriverCommonData.ERROR_LOGS))
                            DriverSessionSave.saveSession(DriverCommonData.ERROR_LOGS, json.getString(DriverCommonData.ERROR_LOGS).equals("1"), DriverSplashAct.this);
                        JSONArray jArry = json.getJSONArray("detail");
                       /* if (json.has("mobile_socket_http_url")) {
                            SessionSave.saveSession(CommonData.NODE_URL, json.getString("mobile_socket_http_url"), SplashAct.this);
                        }*/


                        if (json.has("https_node_url")) {
                            DriverSessionSave.saveSession(DriverCommonData.NODE_URL, json.getString("https_node_url"), DriverSplashAct.this);
                        }

                        if (json.has("mobile_socket_http_domain")) {
                            DriverSessionSave.saveSession(DriverCommonData.NODE_DOMAIN, json.getString("mobile_socket_http_domain"), DriverSplashAct.this);
                        }

                        if (json.has("chat_node_url")) {
                            DriverSessionSave.saveSession("chat_node_url", json.getString("chat_node_url"), DriverSplashAct.this);
                        }

                        if (json.has(DriverCommonData.HELP_URL)) {
                            DriverSessionSave.saveSession(DriverCommonData.HELP_URL, json.getString(DriverCommonData.HELP_URL), DriverSplashAct.this);
                        }
                        if (jArry.getJSONObject(0).has("manual_waiting_enable")) {
                            DriverSessionSave.saveSession(DriverCommonData.WAITING_TIME_MANUAL, jArry.getJSONObject(0).getString("manual_waiting_enable").equals("1"), DriverSplashAct.this);
                        }
                        if (jArry.getJSONObject(0).has(DriverCommonData.SKIP_DRIVER_EMAIL))
                            DriverSessionSave.saveSession(DriverCommonData.SKIP_DRIVER_EMAIL, jArry.getJSONObject(0).getString(DriverCommonData.SKIP_DRIVER_EMAIL).equals("1"), DriverSplashAct.this);
                        else
                            DriverSessionSave.saveSession(DriverCommonData.SKIP_DRIVER_EMAIL, false, DriverSplashAct.this);
                        DriverSessionSave.saveSession("api_base", jArry.getJSONObject(0).getString("api_base"), DriverSplashAct.this);
                        DriverSessionSave.saveSession("isFourSquare", jArry.getJSONObject(0).getString("android_foursquare_status"), DriverSplashAct.this);
                        DriverSessionSave.saveSession("android_foursquare_api_key", jArry.getJSONObject(0).getString("android_foursquare_api_key"), DriverSplashAct.this);
                        DriverSessionSave.saveSession("facebook_key", jArry.getJSONObject(0).getString("facebook_key"), DriverSplashAct.this);
                        DriverSessionSave.saveSession("play_store_version", jArry.getJSONObject(0).getString("android_driver_version"), DriverSplashAct.this);

                        if (jArry.getJSONObject(0).has("playstore_driver"))
                            DriverSessionSave.saveSession(DriverCommonData.PLAY_STORE_LINK, jArry.getJSONObject(0).getString("playstore_driver"), DriverSplashAct.this);

                        if (jArry.getJSONObject(0).has("last_forceupdate_version"))
                            DriverSessionSave.saveSession(DriverCommonData.LAST_FORCEUPDATE_VERSION, jArry.getJSONObject(0).getString("last_forceupdate_version"), DriverSplashAct.this);
                        else
                            DriverSessionSave.saveSession(DriverCommonData.LAST_FORCEUPDATE_VERSION, "0", DriverSplashAct.this);

                        DriverSessionSave.saveSession("country_iso_code", jArry.getJSONObject(0).getString("country_iso_code"), DriverSplashAct.this);


                        DriverSessionSave.saveSession("android_web_key", jArry.getJSONObject(0).getString("android_google_api_key"), DriverSplashAct.this);

                        if (json.has(DriverCommonData.TIMEZONE)) {
                            DriverSessionSave.saveSession(DriverCommonData.TIMEZONE, json.getString(DriverCommonData.TIMEZONE), DriverSplashAct.this);
                        }

                        int length = jArry.length();

                        String googleApiKey = jArry.getJSONObject(0).getString("android_google_api_key");
                        if (!getString(R.string.googleID).equals(googleApiKey))
                            AppController.getInstance().setPlaceApiKey(googleApiKey);

                        DriverSessionSave.saveSession(DriverCommonData.GOOGLE_KEY, googleApiKey, DriverSplashAct.this);

                        if (jArry.getJSONObject(0).has("android_mapbox_key")) {
                            DriverSessionSave.saveSession(DriverCommonData.MAP_BOX_TOKEN, jArry.getJSONObject(0).getString("android_mapbox_key"), DriverSplashAct.this);
                        } else {
                            DriverSessionSave.saveSession(DriverCommonData.MAP_BOX_TOKEN, "pk.eyJ1IjoibmFuZGhpbmlzIiwiYSI6ImNqaGl0M3U0aDI5MXczYW8xZGY3bmxod3gifQ.CsQZTI8nf5ZDh8ES3Iu87g", DriverSplashAct.this);
                        }
                        if (jArry.getJSONObject(0).has("android_local_map_enable")) {
                            DriverSessionSave.saveSession(DriverCommonData.LOCAL_STORAGE, jArry.getJSONObject(0).getString("android_local_map_enable").equals("1"), DriverSplashAct.this);
                        } else {
                            DriverSessionSave.saveSession(DriverCommonData.LOCAL_STORAGE, false, DriverSplashAct.this);
                        }
//                        if (!SessionSave.getSession(CommonData.MAP_BOX_TOKEN, SplashAct.this).equals(""))
//                            Mapbox.getInstance(SplashAct.this, SessionSave.getSession(CommonData.MAP_BOX_TOKEN, SplashAct.this));
                        if (jArry.getJSONObject(0).has("sos_setting"))
                            DriverSessionSave.saveSession(DriverCommonData.SOS_ENABLED, jArry.getJSONObject(0).getString("sos_setting").equals("1"), DriverSplashAct.this);
                        if (jArry.getJSONObject(0).has("map_settings") && jArry.getJSONObject(0).getJSONObject("map_settings").has("is_google_distance")) {
                            DriverSessionSave.saveSession(DriverCommonData.isGoogleDistance, jArry.getJSONObject(0).getJSONObject("map_settings").getString("is_google_distance").equals("1"), DriverSplashAct.this);
                            DriverSessionSave.saveSession(DriverCommonData.isGoogleRoute, jArry.getJSONObject(0).getJSONObject("map_settings").getString("is_google_direction").equals("1"), DriverSplashAct.this);
                            DriverSessionSave.saveSession(DriverCommonData.isGoogleGeocoder, jArry.getJSONObject(0).getJSONObject("map_settings").getString("is_google_geocode").equals("1"), DriverSplashAct.this);
                            DriverSessionSave.saveSession(DriverCommonData.isNeedtoDrawRoute, jArry.getJSONObject(0).getJSONObject("map_settings").getString("enable_route").equals("1"), DriverSplashAct.this);
                            DriverSessionSave.saveSession(DriverCommonData.isNeedtofetchAddress, jArry.getJSONObject(0).getJSONObject("map_settings").getString("display_current_location").equals("1"), DriverSplashAct.this);
                        } else {
                            DriverSessionSave.saveSession(DriverCommonData.isGoogleDistance, true, DriverSplashAct.this);
                            DriverSessionSave.saveSession(DriverCommonData.isGoogleRoute, true, DriverSplashAct.this);
                            DriverSessionSave.saveSession(DriverCommonData.isGoogleGeocoder, true, DriverSplashAct.this);
                            DriverSessionSave.saveSession(DriverCommonData.isNeedtoDrawRoute, true, DriverSplashAct.this);
                            DriverSessionSave.saveSession(DriverCommonData.isNeedtofetchAddress, true, DriverSplashAct.this);
                        }


                        if (jArry.getJSONObject(0).has("sos_msg"))
                            DriverSessionSave.saveSession("sos_message", jArry.getJSONObject(0).getString("sos_msg"), DriverSplashAct.this);

                        for (int i = 0; i < length; i++) {
                            DriverSessionSave.saveSession("noimage_base", jArry.getJSONObject(i).getString("noimage_base"), getApplicationContext());
                            DriverSessionSave.saveSession("site_currency", jArry.getJSONObject(i).getString("site_currency") + " ", getApplicationContext());
                            DriverSystems.out.println("chry_str_splash" + jArry.getJSONObject(i).getString("site_currency"));
                            DriverSessionSave.saveSession("invite_txt", jArry.getJSONObject(i).getString("aboutpage_description"), getApplicationContext());
                            DriverSessionSave.saveSession("referal", jArry.getJSONObject(i).getString("driver_referral_settings"), getApplicationContext());
                            DriverSessionSave.saveSession("Metric", jArry.getJSONObject(i).getString("metric"), DriverSplashAct.this);
                        }


                        try {
                            getCoreLangTime = json.getJSONObject("language_color_status").getString("android_driver_language");
                            getCoreColorTime = json.getJSONObject("language_color_status").getString("android_driver_colorcode");
                            getCore_Utc = jArry.getJSONObject(0).getLong("utc_time");


                            boolean deflanAvail = false;
                            String totalLanguage = "";
                            JSONArray pArray = json.getJSONObject("language_color").getJSONObject("android").getJSONArray("driver_language");
                            for (int i = 0; i < pArray.length(); i++) {

                                totalLanguage += pArray.getJSONObject(i).getString("language").replaceAll(".xml", "") + "____";

                                DriverSessionSave.saveSession("LANG" + i, pArray.getJSONObject(i).getString("language"), DriverSplashAct.this);
                                DriverSessionSave.saveSession("LANGTemp" + i, pArray.getJSONObject(i).getString("design_type"), DriverSplashAct.this);
                                DriverSessionSave.saveSession("LANGCode" + i, pArray.getJSONObject(i).getString("language_code"), DriverSplashAct.this);
                                DriverSessionSave.saveSession(pArray.getJSONObject(i).getString("language"), pArray.getJSONObject(i).getString("url"), DriverSplashAct.this);
                                if (!DriverSessionSave.getSession("LANGDef", DriverSplashAct.this).equals("") && pArray.getJSONObject(i).getString("language").contains(DriverSessionSave.getSession("LANGDef", DriverSplashAct.this))) {
                                    deflanAvail = true;
                                }

                            }
                            DriverSystems.out.println("___________defff" + deflanAvail);
                            if (DriverSessionSave.getSession("LANGDef", DriverSplashAct.this).trim().equals("") || !deflanAvail) {
                                DriverSessionSave.saveSession("LANGDef", DriverSessionSave.getSession("LANG0", DriverSplashAct.this), DriverSplashAct.this);

                                DriverSessionSave.saveSession("LANGTempDef", DriverSessionSave.getSession("LANGTemp0", DriverSplashAct.this), DriverSplashAct.this);
                                DriverSessionSave.saveSession("Lang", pArray.getJSONObject(0).getString("language_code").replaceAll(".xml", ""), DriverSplashAct.this);
                                String url = DriverSessionSave.getSession(DriverSessionSave.getSession("LANG" + 0, DriverSplashAct.this), DriverSplashAct.this);
                                DriverSessionSave.saveSession("currentStringUrl", url, DriverSplashAct.this);
                            }

                            DriverSessionSave.saveSession("lang_json", totalLanguage, DriverSplashAct.this);

                            DriverSessionSave.saveSession("colorcode", json.getJSONObject("language_color").getJSONObject("android").getString("driverColorCode"), DriverSplashAct.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent i = null;

                        if (!DriverSessionSave.getSession(DriverCommonData.PASSENGER_LANGUAGE_TIME, DriverSplashAct.this).trim().equals(getCoreLangTime)) {
                            new callString(getCoreColorTime);
                        } else if (!DriverSessionSave.getSession(DriverCommonData.PASSENGER_COLOR_TIME, DriverSplashAct.this).trim().equals(getCoreColorTime)) {
                            new callColor(getCoreLangTime);
                        } else {
//                            if (CommonData.isCurrentTimeZone(jArry.getJSONObject(0).getLong("utc_time"))) {

//                            if (DriverSessionSave.getSession("user_privacy_policy", DriverSplashAct.this).equals("")) {
//                                i = new Intent(DriverSplashAct.this, DriverDevicePermissionActivityDriver.class);
//                                startActivity(i);
//                                finish();
//                            } else {
                                if (DriverSessionSave.getSession("Id", DriverSplashAct.this).equals("")) {
                                    i = new Intent(DriverSplashAct.this, DriverUserLoginAct.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    if (DriverSessionSave.getSession("trip_id", DriverSplashAct.this).equals("")) {
                                        i = new Intent(DriverSplashAct.this, DriverMyStatus.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        if (DriverSessionSave.getSession("travel_status", DriverSplashAct.this).equals("5")) {
                                            i = new Intent(DriverSplashAct.this, DriverTripHistoryAct.class);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            i = new Intent(DriverSplashAct.this, DriverOngoingAct.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                                }

//                            }
                           /* } else {
                                cancelLoading();
                                alert_view_date(SplashAct.this, NC.getString(R.string.message), NC.getString(R.string.date_change), NC.getString(R.string.ok), NC.getString(R.string.cancel));
                            }*/

                        }

                    } else if (json.getInt("status") == 0) {
                        //no changes made
                    } else if (json.getInt("status") == -101) {
                        if (json.has("message"))
                            forceLogout(json.getString("message"));
                        else
                            forceLogout(DriverNC.getString(R.string.server_error));
                    } else {
                        errorInSplash(json.getString("message"));
                    }
                } catch (final JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    errorInSplash(DriverNC.getString(R.string.server_error));
                } catch (final NullPointerException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    errorInSplash(DriverNC.getString(R.string.server_error));
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    errorInSplash(DriverNC.getString(R.string.server_error));
                }
            } else {
                Utility.actionSheetCancel(DriverSplashAct.this,DriverNC.getString(R.string.server_error), DriverNC.getString(R.string.c_tryagain), NC.getResources().getString(R.string.cancel), false, new AlertListener() {
                    @Override
                    public void onSuccess() {
                        String url = "type=getcoreconfig";
                        new CoreConfigCall(url);
                    }

                    @Override
                    public void onFailure() {
                        final Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
                /*
                dialog1 = Driver_Utils.alert_view_dialog(DriverSplashAct.this, DriverNC.getString(R.string.message), DriverNC.getString(R.string.server_error), DriverNC.getString(R.string.c_tryagain), DriverNC.getString(R.string.cancel), false, (dialog, which) -> {
                    dialog.dismiss();
                    String url = "type=getcoreconfig";
                    new CoreConfigCall(url);
                }, (dialog, which) -> {
                    dialog.dismiss();
                    final Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }, "");

                 */
            }
        }

    }
}
