package com.moovex;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.moovex.data.apiData.CompanyDomainResponse;
import com.moovex.driver.DriverMyStatus;
import com.moovex.driver.DriverOngoingAct;
import com.moovex.driver.DriverTripHistoryAct;
import com.moovex.driver.DriverUserLoginAct;
import com.moovex.driver.data.DriverCommonData;
import com.moovex.driver.data.apiData.DriverApiRequestData;
import com.moovex.driver.interfaces.DriverAPIResult;
import com.moovex.driver.utils.DriverCL;
import com.moovex.driver.utils.DriverCToast;
import com.moovex.driver.utils.DriverNC;
import com.moovex.driver.utils.DriverSessionSave;
import com.moovex.driver.utils.DriverSystems;

import com.moovex.service.APIService_Retrofit_JSON_NoProgress;
import com.moovex.service.BackgroundCoreConfig;
import com.moovex.service.CoreClient;
import com.moovex.service.RetrofitCallbackClass;
import com.moovex.util.AppController;
import com.moovex.util.FontHelper;
import com.moovex.util.NetworkStatus;
import com.moovex.util.SessionSave;
import com.moovex.util.TaxiUtil;
import com.moovex.util.Utility;

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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moovex.MainActivity.mgpsDialog;
import static com.moovex.util.ConstantsKt.API_BASE;
import static com.moovex.util.ConstantsKt.DEFAULT_CITY_NAME;
import static com.moovex.util.ConstantsKt.IS_BUISNESS_KEY;
import static com.moovex.util.ConstantsKt.LANG;
import static com.moovex.util.ConstantsKt.MODEL_DETAILS;
import static com.moovex.util.ConstantsKt.PASS_ID;
import static com.moovex.util.ConstantsKt.PASS_PAYMENT_OPTION;
import static com.moovex.util.ConstantsKt.SERVICE_DETAILS;

/**
 * Created by developer on 30/6/17.
 * at ndot
 */

public class SplashActivity extends AppCompatActivity {
    public static final String CURRENT_COUNTRY_CODE = "+234";
    public static final String CURRENT_COUNTRY_ISO_CODE = "NG";
    private static final int MY_PERMISSIONS_REQUEST_GPS = 420;
    public static ArrayList<String> fields = new ArrayList<>();
    public static ArrayList<String> fields_value = new ArrayList<>();
    public static HashMap<String, Integer> fields_id = new HashMap<>();
    public FrameLayout splashLayout;
    ProgressBar progressBar1;
    TextView status_text;
    private Dialog loadingDialog;
    private final boolean askDomain = true;
    private final boolean isLocationasked = false;
    private Dialog urlPopup;
    private long getCore_Utc;
    private String getCoreLangTime;
    private String getCoreColorTime;
    private Dialog myDialog;
    private RelativeLayout relativelay;
    private RelativeLayout access_key_lay;
    private ImageView access_logo;

    private Dialog dialog;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setLocale();
//        MainHomeFragmentActivity.context = this;

        int curVersion = BuildConfig.VERSION_CODE;
        try {
            if (curVersion != 0)
                if (SessionSave.getSession(String.valueOf(curVersion), this).trim().equals("")) {
                    SessionSave.saveSession("base_url", "", SplashActivity.this);
                    SessionSave.saveSession("api_key", "", SplashActivity.this);
                    SessionSave.saveSession("encode", "", SplashActivity.this);
                    SessionSave.saveSession("image_path", "", SplashActivity.this);
                    SessionSave.saveSession(String.valueOf(curVersion), "No", SplashActivity.this);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.splashlay);
        relativelay = (RelativeLayout) findViewById(R.id.relativelay);
        access_key_lay = (RelativeLayout) findViewById(R.id.access_key_lay);
        access_logo = findViewById(R.id.access_logo);
        //  setContentView(R.layout.splashlay);

        FontHelper.applyFont(SplashActivity.this, findViewById(R.id.relativelay));

        progressBar1 = findViewById(R.id.progressBar1);
        status_text = findViewById(R.id.status_text);
        splashLayout = findViewById(R.id.lay_splash);

      /*  if (ActivityCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            dialog = Utility.alert_view_dialog(SplashActivity.this, "",
                    "" + NC.getResources().getString(R.string.str_loc),
                    "" + NC.getResources().getString(R.string.yes),
                    "" + NC.getResources().getString(R.string.no),
                    false, new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(android.content.DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_GPS);
                        }
                    }, new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(android.content.DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }, "");
        } else {
            if (getIntent() != null && getIntent().getStringExtra("alert_message") != null) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                dialog = Utility.alert_view_dialog(SplashActivity.this, "" +
                                NC.getResources().getString(R.string.message),
                        "" + getIntent().getStringExtra("alert_message"),
                        "" + NC.getResources().getString(R.string.ok),
                        "",
                        false, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                SplashActivity.this.finish();
                            }
                        }, null, "");
            } else
                ifPermissionGranted();
        }*/
//        getKeyHash("SHA");
//        getKeyHash("MD5");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Resume Override.... v " + SessionSave.getSession("isLocationasked", SplashActivity.this, false));
        if (NetworkStatus.isOnline(SplashActivity.this)) {
            if (!SessionSave.getSession("isLocationasked", SplashActivity.this, false)) {
                checkLocationPermission();
                SessionSave.saveSession("isLocationasked", true, SplashActivity.this);
            } else {
                init();
                //  connectGoogleApi();
                callApi();
            }
        } else errorInSplash(DriverNC.getString(R.string.check_internet_connection));
        SessionSave.saveSession("isFromSplash", true, SplashActivity.this);
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
            }
        */
    public void checkLocationPermission() {

        if (ActivityCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_GPS);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(this)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
//            }
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        if (dialog != null) Utility.closeDialog(dialog);
        super.onDestroy();
    }

    void callApi() {
        if (!SessionSave.getSession("base_url", SplashActivity.this).trim().equals("")) {
            /*if (!(SessionSave.getSession("wholekey", SplashActivity.this).equals(""))) {
                getAndStoreStringValues(SessionSave.getSession("wholekey", SplashActivity.this));
                getAndStoreColorValues(SessionSave.getSession("wholekeyColor", SplashActivity.this));
            }*/
            if (NetworkStatus.isOnline(SplashActivity.this)) MovetoNavigatorPanel();
            else errorInSplash(DriverNC.getString(R.string.check_internet_connection));
        } else {
            if (askDomain) {
                cancelLoading();
                if (NetworkStatus.isOnline(SplashActivity.this)) getUrl();
                else errorInSplash(DriverNC.getString(R.string.check_internet_connection));
            } else urlApi("", "", "");
        }
    }

    private void urlApi(String keyy, String mUrl, String domain) {
        String url = "";
        String str_domain = "";
        String key = "";
        try {
            if (!TextUtils.isEmpty(keyy)/*!mUrl.equals("") && !domain.equals("")*/) {
             /*   url = mUrl;
                str_domain = domain;*/
                /*url = "http://tmongo.movex.ai/passengerapi301/index/"; //Live
                str_domain = "movex.ai";
                key = keyy;*/

                url = "http://mongo.tmobility.ai/passengerapi301/index/";
                str_domain = "taximobility.ai";
                key = keyy;

               /* url = "http://taxi10.taximobility.com/passengerapi301/index/";
                str_domain = "taximobility";
                key = "taxi10demo";*/
                // key = keyy;
            } else {
                //url = "http://taxi10.taximobility.com/passengerapi301/index/";
                //url = "http://qatmobility.know3.com/passengerapi301/index/"; //QA
                url = "http://tmongo.movex.ai/passengerapi301/index/"; //Live
                str_domain = "movex.ai";
                //key = "taxi10demo";
                //key = "qa_db"; // QA
                key = "snapecabs"; // live
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String mUUID;
        if (!UUID.randomUUID().toString().equals("")) {
            mUUID = UUID.randomUUID().toString();
        } else {
            mUUID = TaxiUtil.mDevice_id_constant;
        }
//        CoreClient client = new ServiceGenerator(SplashActivity.this, url, false).createService(CoreClient.class);
//        SessionSave.saveSession(TaxiUtil.DEVICE_ID, Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID), SplashActivity.this);
        SessionSave.saveSession(TaxiUtil.DEVICE_ID, mUUID, SplashActivity.this);

        CoreClient client = AppController.getInstance().getCheckCompanyDomainapiManager(url);
        DriverApiRequestData.BaseUrl request = new DriverApiRequestData.BaseUrl();
        request.company_domain = key;
        request.company_main_domain = /*"taximobility.com"*/str_domain;
        request.device_type = "1";
        SessionSave.saveSession(TaxiUtil.DOMAIN_URL, "", SplashActivity.this);
        SessionSave.saveSession(TaxiUtil.DOMAIN_URL, url, SplashActivity.this);
        SessionSave.saveSession(TaxiUtil.COMPANY_DOMAIN, "", SplashActivity.this);
        SessionSave.saveSession(TaxiUtil.COMPANY_DOMAIN, str_domain, SplashActivity.this);
        SessionSave.saveSession(TaxiUtil.ACCESS_KEY, "", SplashActivity.this);
        SessionSave.saveSession(TaxiUtil.ACCESS_KEY, key, SplashActivity.this);
        Call<CompanyDomainResponse> response = client.callData(TaxiUtil.COMPANY_KEY, request);
        response.enqueue(new RetrofitCallbackClass<CompanyDomainResponse>(SplashActivity.this, new Callback<CompanyDomainResponse>() {
            @Override
            public void onResponse(@NonNull Call<CompanyDomainResponse> call, @NonNull Response<CompanyDomainResponse> response) {
                CompanyDomainResponse cr = response.body();
                cancelLoading();
                if (cr != null && SplashActivity.this != null) {
                    if (cr.status != null) {
                        if (cr.status.trim().equals("1")) {
                            SessionSave.saveSession("show_hauwai_alert", true, SplashActivity.this);
//                            SessionSave.saveSession("base_url", cr.baseurl, SplashActivity.this);
                            if (cr.https_base_url != null) {
                                SessionSave.saveSession("base_url", cr.https_base_url, SplashActivity.this);
                                SessionSave.saveSession("api_key", cr.apikey, SplashActivity.this);
                                SessionSave.saveSession("image_path", cr.androidPaths.static_image, SplashActivity.this);
                                SessionSave.saveSession("encode", cr.encode, SplashActivity.this);
                            } else {
                                SessionSave.saveSession("base_url", cr.baseurl, SplashActivity.this);
                                SessionSave.saveSession("passenger_base_url", cr.baseurl, SplashActivity.this);
                                SessionSave.saveSession("driver_base_url", cr.driver_baseurl, SplashActivity.this);
                                DriverSessionSave.saveSession("driver_base_url", cr.driver_baseurl, SplashActivity.this);
                                SessionSave.saveSession("api_key", cr.apikey, SplashActivity.this);
                                SessionSave.saveSession("image_path", cr.androidPaths.static_image, SplashActivity.this);
                                SessionSave.saveSession("encode", cr.encode, SplashActivity.this);
                            }
                            if (cr.https_base_url != null) {
                                TaxiUtil.API_BASE_URL = cr.https_base_url;
                            } else {
                                TaxiUtil.API_BASE_URL = cr.baseurl;
                            }
                            String totalLanguage = "";
                            String defaultLanguage = cr.default_language;
                            if (cr.androidPaths.passenger_language != null) {
                                for (int i = 0; i < cr.androidPaths.passenger_language.size(); i++) {
                                    String key_ = "";
                                    totalLanguage += cr.androidPaths.passenger_language.get(i).language.replaceAll(".xml", "") + "____";
                                    SessionSave.saveSession("LANG" + i, cr.androidPaths.passenger_language.get(i).language, SplashActivity.this);
                                    SessionSave.saveSession("LANGTemp" + i, cr.androidPaths.passenger_language.get(i).design_type, SplashActivity.this);
                                    SessionSave.saveSession("LANGCode" + i, cr.androidPaths.passenger_language.get(i).language_code, SplashActivity.this);
                                    SessionSave.saveSession(cr.androidPaths.passenger_language.get(i).language, cr.androidPaths.passenger_language.get(i).url, SplashActivity.this);
                                    DriverSystems.out.println("******" + i + "__" + cr.androidPaths.passenger_language.get(i).language);
                                    if (cr.androidPaths.passenger_language.get(i).language_code.equalsIgnoreCase(defaultLanguage)) {
                                        DriverSystems.out.println("******ccc" + i + "__" + cr.androidPaths.passenger_language.get(i).language);
                                        SessionSave.saveSession(LANG, cr.androidPaths.passenger_language.get(i).language_code, SplashActivity.this);
                                        SessionSave.saveSession("LANGTempDef", cr.androidPaths.passenger_language.get(i).design_type, SplashActivity.this);
                                        SessionSave.saveSession("LANGDef", cr.androidPaths.passenger_language.get(i).language, SplashActivity.this);
                                    }
                                }
                                DriverSystems.out.println("******" + SessionSave.getSession("LANGDef", SplashActivity.this).trim().equals("") + "__");
                                if (SessionSave.getSession("LANGDef", SplashActivity.this).trim().equals(""))
                                    SessionSave.saveSession("LANGDef", SessionSave.getSession("LANG0", SplashActivity.this), SplashActivity.this);
                                if (SessionSave.getSession("LANGTempDef", SplashActivity.this).trim().equals(""))
                                    SessionSave.saveSession("LANGTempDef", SessionSave.getSession("LANGTemp0", SplashActivity.this), SplashActivity.this);
                                SessionSave.saveSession("lang_json", totalLanguage, SplashActivity.this);
                                SessionSave.saveSession("colorcode", cr.androidPaths.colorcode, SplashActivity.this);
                                if (SessionSave.getSession(LANG, SplashActivity.this).equals(""))
                                    SessionSave.saveSession(LANG, cr.androidPaths.passenger_language.get(0).language_code.replaceAll(".xml", ""), SplashActivity.this);
                            }

                            String url = "type=getcoreconfig";
                            new SplashActivity.CoreConfigCall(url);
                            if (!SessionSave.getSession(PASS_ID, SplashActivity.this).equals("")) {
//                                startService(new Intent(SplashActivity.this, GetCardDetailsService.class));
                            }
                        } else {
                            errorInSplash(cr.message == null ? DriverNC.getString(R.string.server_con_error) : cr.message);
                        }
                    }
                } else {
                    errorInSplash(DriverNC.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CompanyDomainResponse> call, @NonNull Throwable t) {
                cancelLoading();
                errorInSplash(DriverNC.getString(R.string.server_error));
            }
        }));
    }

    public void MovetoNavigatorPanel() {
        if (VersionCheck()) {
            versionAlert(SplashActivity.this);
        } else {
            Intent i = null;
            startService(new Intent(SplashActivity.this, BackgroundCoreConfig.class));
            if (SessionSave.getSession(PASS_ID, SplashActivity.this).equals("")) {
                /*if (SessionSave.getSession(TaxiUtil.USER_PRIVACY_POLICY, SplashActivity.this).equals("")) {
                    i = new Intent(SplashActivity.this, DevicePermissionActivity.class);
                    startActivity(i);
                    finish();
                } else {*/
                if (!SessionSave.getSession("IsOTPSend", SplashActivity.this).equals("")) {
                    DriverSystems.out.println("detail_v5");
//                    i = new Intent(SplashActivity.this, VerificationActivity.class);
                } else {
                    i = new Intent(SplashActivity.this, DriverUserLoginAct.class);
                }
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();
                //   }
            } else {
                //if has trip doesn't move from splash

                /*if (SessionSave.getSession("trip_id", SplashActivity.this).equals("")) {
//                                            if (!SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("") && !SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("0.0")) {
                    DivertToHomeScreen();
                    //}
                } else {
                    if (!SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("") && !SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("0.0")) {
                        DivertToHomeScreen();
                    }
                }*/
                DivertToHomeScreen();
            }
        }
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
            int chhh = 0;
            for (int i = 0; i < nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    chhh++;

                    Element element2 = (Element) node;
                    DriverCL.nfields_byName.put(element2.getAttribute("name"), element2.getTextContent());
                    DriverCL.nfields_byName.put(element2.getAttribute("name"), element2.getTextContent());
                }
            }
            // getColorValueDetail();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MovetoNavigatorPanel();
        }
    }

    synchronized void getColorValueDetail() {
        Field[] fieldss = R.color.class.getDeclaredFields();
        for (Field field : fieldss) {
            int id = getResources().getIdentifier(field.getName(), "color", getPackageName());

            if (DriverCL.nfields_byName.containsKey(field.getName())) {
                DriverCL.fields.add(field.getName());
                DriverCL.fields_value.add(getResources().getString(id));
                DriverCL.fields_id.put(field.getName(), id);
            }
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

        for (Map.Entry<String, String> entry : DriverCL.nfields_byName.entrySet()) {
            String h = entry.getKey();
            String value = entry.getValue();
            DriverCL.nfields_byID.put(DriverCL.fields_id.get(h), DriverCL.nfields_byName.get(h));
        }
    }

    public void versionAlert(final Context mContext) {
        boolean forceUpdate = forceUpdateCheck();
        try {
            String negativeBtnText;
            if (forceUpdate) {
                negativeBtnText = DriverNC.getResources().getString(R.string.cancel);
            } else {
                negativeBtnText = DriverNC.getResources().getString(R.string.version_up_later);
            }
            dialog = Utility.alert_view_dialog(SplashActivity.this, "" + DriverNC.getResources().getString(R.string.version_up_title), "" + DriverNC.getResources().getString(R.string.version_up_message), "" + DriverNC.getResources().getString(R.string.version_up_now), negativeBtnText, false, (dialog, which) -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + mContext.getPackageName()));
                mContext.startActivity(intent);
                dialog.dismiss();
            }, (dialog, which) -> {
                dialog.dismiss();
                if (forceUpdate) {
                    SplashActivity.this.finish();
                } else {
                    startService(new Intent(SplashActivity.this, BackgroundCoreConfig.class));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Intent i = null;
                            if (SessionSave.getSession(PASS_ID, SplashActivity.this).equals("")) {
                                       /* if (SessionSave.getSession(TaxiUtil.USER_PRIVACY_POLICY, SplashActivity.this).equals("")) {
                                            i = new Intent(SplashActivity.this, DevicePermissionActivity.class);
                                            startActivity(i);
                                            finish();
                                        } else {*/

                                if (!SessionSave.getSession("IsOTPSend", SplashActivity.this).equals("")) {
                                    DriverSystems.out.println("detail_v6");
//                                                i = new Intent(SplashActivity.this, VerificationActivity.class);
//                                                startActivity(i);
//                                                overridePendingTransition(0, 0);
//                                                finish();
                                } else {
                                    i = new Intent(SplashActivity.this, DriverUserLoginAct.class);
                                    startActivity(i);
                                    overridePendingTransition(0, 0);
                                    finish();
                                }
                                //    }
                            } else {
                                if (SessionSave.getSession("trip_id", SplashActivity.this).equals("")) {
                                    if (!SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("") && !SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("0.0")) {
                                        DivertToHomeScreen();
                                    }
                                } else {
                                    if (!SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("") && !SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("0.0")) {
                                        DivertToHomeScreen();
                                    }
                                }
                            }
                        }
                    }, 200);
                }
            }, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DivertToHomeScreen() {

//        if (SessionSave.getSession("is_driver", SplashActivity.this).equals("")) {
//            if (SessionSave.getSession(TaxiUtil.NEED_TO_COMPLETE_CARD_REG, SplashActivity.this, false)
//
//                    && !SessionSave.getSession(PASS_ID, SplashActivity.this).equals("")) {
//                Intent i;
////                i = new Intent(SplashActivity.this, CardRegisterAct.class);
////                startActivity(i);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        finish();
//                    }
//                }, 2000);
//            } else {
//                Intent i;
//                i = new Intent(SplashActivity.this, MainHomeFragmentActivity.class);
//                startActivity(i);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        finish();
//                    }
//                }, 2000);
//            }
//        } else {
        driverStatus();
//        }
    }

    private boolean VersionCheck() {
        try {
            String newVersion = SessionSave.getSession("play_store_version", SplashActivity.this).equals("") ? "0" : SessionSave.getSession("play_store_version", SplashActivity.this);
            int curVersion = BuildConfig.VERSION_CODE;

            System.err.println("New version" + newVersion + "curVersion" + curVersion);
            return curVersion < value(newVersion);
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
            String lastForceUpdateVersion = SessionSave.getSession(TaxiUtil.LAST_FORCEUPDATE_VERSION, SplashActivity.this).equals("") ? "0" : SessionSave.getSession(TaxiUtil.LAST_FORCEUPDATE_VERSION, SplashActivity.this);
            int curVersion = BuildConfig.VERSION_CODE;

            System.err.println("New version" + lastForceUpdateVersion + "curVersion" + curVersion);
            if (curVersion < value(lastForceUpdateVersion)) {
                return true;
            } else return value(lastForceUpdateVersion) <= 0;
            //   }
        } catch (Exception e) {
            // TODO: handle exception

            e.printStackTrace();
        }
        return false;
    }

    private long value(String string) {
        string = string.trim();
        if (string.contains(".")) {
            final int index = string.lastIndexOf(".");
            return value(string.substring(0, index)) * 100 + value(string.substring(index + 1));
        } else {
            return Long.valueOf(string);
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
//                    DriverNC.nfields_byName.put(element2.getAttribute("name"), element2.getTextContent());
                    DriverNC.nfields_byName.put(element2.getAttribute("name"), element2.getTextContent());
                }
            }
            getValueDetail();
        } catch (Exception e) {
            DriverSystems.out.println("string error" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    synchronized void getValueDetail() {
        Field[] fieldss = R.string.class.getDeclaredFields();
        for (Field field : fieldss) {
            int id = getResources().getIdentifier(field.getName(), "string", getPackageName());
            if (DriverNC.nfields_byName.containsKey(field.getName())) {
                fields.add(field.getName());
                fields_value.add(getResources().getString(id));
                fields_id.put(field.getName(), id);
            }
            if (DriverNC.nfields_byName.containsKey(field.getName())) {
                fields.add(field.getName());
                fields_value.add(getResources().getString(id));
                fields_id.put(field.getName(), id);
            }
        }
//        for (Map.Entry<String, String> entry : NC.nfields_byName.entrySet()) {
//            String h = entry.getKey();
//            String value = entry.getValue();
//            NC.nfields_byID.put(fields_id.get(h), NC.nfields_byName.get(h));
//            // do stuff
//        }

        for (Map.Entry<String, String> entry : DriverNC.nfields_byName.entrySet()) {
            String h = entry.getKey();
            String value = entry.getValue();
            DriverNC.nfields_byID.put(fields_id.get(h), DriverNC.nfields_byName.get(h));
        }
    }

    public void setEditTextMaxLength(int length, EditText edt_text) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        edt_text.setFilters(FilterArray);
    }

    private void getUrl() {
       /* if (BuildConfig.DEBUG) {
            final View view1 = View.inflate(SplashActivity.this, R.layout.domain_lay, null);
            if (myDialog != null && myDialog.isShowing())
                myDialog.cancel();
            myDialog = new Dialog(SplashActivity.this, R.style.NewDialog);
            myDialog.setContentView(view1);
            myDialog.setCancelable(false);
            myDialog.setCanceledOnTouchOutside(false);
            FontHelper.applyFont(SplashActivity.this, myDialog.findViewById(R.id.inner_content));
            myDialog.setCancelable(true);
            myDialog.show();

            final EditText edt_url = myDialog.findViewById(R.id.edt_url);
            final EditText edt_domain = myDialog.findViewById(R.id.edt_domain);
            EditText edt_access = myDialog.findViewById(R.id.edt_accesskey);
            edt_access.setText("tmongo");
            SessionSave.saveSession("api_key", ((EditText) myDialog.findViewById(R.id.edt_company_key)).getText().toString(), SplashActivity.this);
            edt_url.setText("http://tmongo.movex.ai/passengerapi301/index/");
            edt_domain.setText("taximobility");
            Button btn_ok = myDialog.findViewById(R.id.btn_ok);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                private String access_key, url, domain;

                @Override
                public void onClick(final View v) {

                    try {
                        access_key = edt_access.getText().toString();
                        url = edt_url.getText().toString();
                        domain = edt_domain.getText().toString();
                        if (!access_key.equals("") && !url.equals("") && !domain.equals("")) {
                            urlApi(access_key, url, domain);
                            myDialog.dismiss();
                        } else {
                            CToast.ShowToast(SplashActivity.this, "Please enter all details");
                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            });
        } else {*/
//        final View view = View.inflate(SplashActivity.this, R.layout.forgot_popup, null);
//        if (urlPopup != null)
//            urlPopup.dismiss();
//        urlPopup = new Dialog(SplashActivity.this, R.style.NewDialog);
//        urlPopup.setContentView(view);
//        urlPopup.setCancelable(false);
//        urlPopup.setCanceledOnTouchOutside(false);
//        FontHelper.applyFont(SplashActivity.this, urlPopup.findViewById(R.id.inner_content));
//        urlPopup.setCancelable(false);
//        urlPopup.show();

        relativelay.setVisibility(View.GONE);
        access_key_lay.setVisibility(View.VISIBLE);
        access_logo.setVisibility(View.VISIBLE);

        final EditText mail = findViewById(R.id.access_key_text);
        //domain_hint
        mail.setHint("Enter your Account ID");
        final Button OK = findViewById(R.id.okbtn);
        OK.setText("Get Started");
        int maxLength = 64;
        setEditTextMaxLength(maxLength, mail);
        View for_sepa = findViewById(R.id.for_sep);
        for_sepa.setVisibility(View.GONE);
        final Button Cancel = findViewById(R.id.cancelbtn);
        Cancel.setVisibility(View.GONE);

        OK.setOnClickListener(new View.OnClickListener() {
            private String Email;

            @Override
            public void onClick(final View v) {
                try {
                    Email = mail.getText().toString();
                    if (!TextUtils.isEmpty(Email) && Email.length() >= 3) {
                        urlApi(Email, "", "");
                        mail.setText("");
                        access_key_lay.setVisibility(View.GONE);
                        access_logo.setVisibility(View.GONE);
                        relativelay.setVisibility(View.VISIBLE);
                    } else {
                        DriverCToast.ShowToast(SplashActivity.this, "Please enter valid Account ID");
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });
        /* }*/
    }

    @Override
    protected void onStop() {

        if (mgpsDialog != null && mgpsDialog.isShowing()) mgpsDialog.cancel();
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
//        if (mGoogleApiClient != null)
//            mGoogleApiClient.disconnect();
        cancelLoading();
        super.onStop();
    }

    public void ifPermissionGranted() {

        if (isGpsEnabled(SplashActivity.this)) {
            //buildGoogleApiClient();
            if (NetworkStatus.isOnline(SplashActivity.this)) {
                DriverSystems.out.println("Init Called");
                init();
                //  connectGoogleApi();
                //  callApi();
            } else errorInSplash(DriverNC.getString(R.string.check_internet_connection));
        } else {
            gpsalert(SplashActivity.this, false);
        }
    }

    public void init() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        FontHelper.applyFont(SplashActivity.this, findViewById(R.id.rootlay));

        try {
            TaxiUtil.current_act = "SplashActivity";
            AtomicInteger c = new AtomicInteger(0);

            String mUUID = "";
            if (TaxiUtil.mDevice_id.equals("")) {
                if (!UUID.randomUUID().toString().equals("")) {
                    mUUID = UUID.randomUUID().toString();
                } else {
                    mUUID = TaxiUtil.mDevice_id_constant + c.incrementAndGet();
                }
                TaxiUtil.mDevice_id = mUUID;
            }
//            TaxiUtil.mDevice_id = Settings.Secure.getString(SplashActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
            System.out.println("DEVOCE ID __" + mUUID);
            if (!TaxiUtil.mDevice_id.equals("")) {
                SessionSave.saveSession("mDevice_id", TaxiUtil.mDevice_id, SplashActivity.this);
            }
//            Intent intent = new Intent(getApplicationContext(), GetPassengerUpdate.class);
//            getApplicationContext().stopService(intent);
            if (SessionSave.getSession(LANG, SplashActivity.this).equals("")) {
                SessionSave.saveSession(LANG, "en", SplashActivity.this);
                SessionSave.saveSession("Lang_Country", "en_GB", SplashActivity.this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (SessionSave.getSession(LANG, SplashActivity.this).equals("")) {
            SessionSave.saveSession(LANG, "en", SplashActivity.this);
            SessionSave.saveSession("Lang_Country", "en_GB", SplashActivity.this);
        }
        // check google play services for accept google map
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_GPS) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
            }
        }
    }

    private void moveToLoginScreen() {
        SessionSave.saveSession(TaxiUtil.USER_PRIVACY_POLICY, "true", SplashActivity.this);
        Intent i = new Intent(SplashActivity.this, DriverUserLoginAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void gpsalert(final Context mContext, boolean isconnect) {
        if (!isconnect) {
            dialog = Utility.alert_view_dialog(SplashActivity.this, "" + DriverNC.getString(R.string.location_disable), "" + DriverNC.getString(R.string.location_enable), "" + DriverNC.getResources().getString(R.string.ok), "", false, (dialog, which) -> {
                Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(mIntent);
            }, (dialog, which) -> dialog.dismiss(), "");
        } else {
            try {
                if (mgpsDialog != null && mgpsDialog.isShowing()) mgpsDialog.dismiss();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    public void showDialog() {
        try {
            DriverSystems.out.println("---rotae4" + NetworkStatus.isOnline(this));
            if (NetworkStatus.isOnline(this)) {
                if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
                View view = View.inflate(this, R.layout.progress_bar, null);
                loadingDialog = new Dialog(this, R.style.dialogwinddow);
                loadingDialog.setContentView(view);
                loadingDialog.setCancelable(false);
                if (this != null) loadingDialog.show();
                ImageView iv = loadingDialog.findViewById(R.id.giff);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                Glide.with(this).load(R.raw.loading_anim).into(imageViewTarget);
            } else {
                errorInSplash(DriverNC.getString(R.string.check_internet_connection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void errorInSplash(String message) {
        try {
            dialog = Utility.alert_view_dialog(SplashActivity.this, "" + DriverNC.getString(R.string.message), "" + message, "" + DriverNC.getString(R.string.try_again), "" + DriverNC.getString(R.string.cancel), false, (dialog, which) -> {
                dialog.dismiss();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }, (dialog, which) -> {
                Activity activity = SplashActivity.this;
                final Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.finish();
                dialog.dismiss();
            }, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelLoading() {
        if (loadingDialog != null && SplashActivity.this != null)
            if (loadingDialog.isShowing()) loadingDialog.dismiss();
    }

    public boolean isGpsEnabled(Context context) {
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else return activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
            } else {
                return false;
                // not connected to the internet
            }
        }
        return false;
    }

    public void setLocale() {
        if (SessionSave.getSession(LANG, SplashActivity.this).equals("")) {
            SessionSave.saveSession(LANG, "en", SplashActivity.this);
            SessionSave.saveSession("Lang_Country", "en_GB", SplashActivity.this);
        }
        Configuration config = new Configuration();
        String langcountry = SessionSave.getSession("Lang_Country", SplashActivity.this);
        String[] arry = langcountry.split("_");
        String language = SessionSave.getSession(LANG, SplashActivity.this);
        config.locale = new Locale(language, arry[1]);
        Locale.setDefault(new Locale(language, arry[1]));
        SplashActivity.this.getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    /**
     * Method to logout user if status -101 and redirect to login page
     *
     * @param message - To intimate user by showing alert message
     */
    private void forceLogout(String message) {
        DriverCToast.ShowToast(SplashActivity.this, message);
        TaxiUtil.API_BASE_URL = "";
        SessionSave.saveSession("base_url", "", SplashActivity.this);
        SessionSave.saveSession(PASS_ID, "", SplashActivity.this);
        SessionSave.clearAllSession(SplashActivity.this);
//        stopService(new Intent(SplashActivity.this, GetPassengerUpdate.class));
        finish();
    }

    private class CoreConfigCall implements DriverAPIResult {
        CoreConfigCall(final String url) {
            showDialog();
            new APIService_Retrofit_JSON_NoProgress(SplashActivity.this, this, "", true).execute("type=getcoreconfig");
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            cancelLoading();
            if (isSuccess) {
                try {
                    final JSONObject json = new JSONObject(result);
                    saveDriverSession(result);
                    if (json.getInt("status") == 1) {
                        if (json.has("gt_lst_time"))
                            SessionSave.saveSession(TaxiUtil.GETCORE_LASTUPDATE, json.getString("gt_lst_time"), SplashActivity.this);
                        final JSONArray array = json.getJSONArray("detail");

                        if (array.getJSONObject(0).has("customer_wallet_transaction")) {
                            SessionSave.saveSession("customer_wallet_transaction", array.getJSONObject(0).getString("customer_wallet_transaction"), SplashActivity.this);
                            System.out.println("customer_wallet_transaction check " + SessionSave.getSession("customer_wallet_transaction", SplashActivity.this));
                        }

                        if (array.getJSONObject(0).has("is_enabled_ive_arrived")) {
                            SessionSave.saveSession("is_enabled_ive_arrived", array.getJSONObject(0).getString("is_enabled_ive_arrived"), SplashActivity.this);
                            System.out.println("is_enabled_ive_arrived check " + SessionSave.getSession("is_enabled_ive_arrived", SplashActivity.this));
                        }

                        if (array.getJSONObject(0).has(TaxiUtil.KM_RESTRICT))
                            SessionSave.saveSession(TaxiUtil.KM_RESTRICT, array.getJSONObject(0).getString(TaxiUtil.KM_RESTRICT), SplashActivity.this);

                        if (array.getJSONObject(0).has(TaxiUtil.SKIP_PASSENGER_EMAIL))
                            SessionSave.saveSession(TaxiUtil.SKIP_PASSENGER_EMAIL, array.getJSONObject(0).getString(TaxiUtil.SKIP_PASSENGER_EMAIL).equals("1"), SplashActivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.SKIP_PASSENGER_EMAIL, false, SplashActivity.this);

                        SessionSave.saveSession(TaxiUtil.BALANCE_CREDIT_OPTION, array.getJSONObject(0).optString(TaxiUtil.BALANCE_CREDIT_OPTION, "1"), SplashActivity.this);

                        SessionSave.saveSession(TaxiUtil.PASSENGER_TIPS_ENABLE, array.getJSONObject(0).optString(TaxiUtil.PASSENGER_TIPS_ENABLE, "1"), SplashActivity.this);

                        if (array.getJSONObject(0).has(TaxiUtil.IS_STOP_ENABLED))
                            SessionSave.saveSession(TaxiUtil.IS_STOP_ENABLED, array.getJSONObject(0).getString(TaxiUtil.IS_STOP_ENABLED).equals("1"), SplashActivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.IS_STOP_ENABLED, false, SplashActivity.this);

                        if (array.getJSONObject(0).has(TaxiUtil.PASSENGER_GRACE_TIME))
                            SessionSave.saveSession(TaxiUtil.PASSENGER_GRACE_TIME, array.getJSONObject(0).getString(TaxiUtil.PASSENGER_GRACE_TIME), SplashActivity.this);

                        SessionSave.saveSession(TaxiUtil.NODE_DOMAIN, json.getString("mobile_socket_http_domain"), SplashActivity.this);

                        if (json.has("https_node_url")) {
                            SessionSave.saveSession(TaxiUtil.NODE_URL, json.getString("https_node_url"), SplashActivity.this);
                            SessionSave.saveSession(DriverCommonData.DRIVER_NODE_URL, json.getString("https_node_url"), SplashActivity.this);
                        } else {
                            SessionSave.saveSession(TaxiUtil.NODE_URL, json.getString("mobile_socket_http_url"), SplashActivity.this);
                            SessionSave.saveSession(DriverCommonData.DRIVER_NODE_URL, json.getString("mobile_socket_http_url"), SplashActivity.this);
                        }

                        if (array.getJSONObject(0).has("is_run_golang") && array.getJSONObject(0).getString("is_run_golang").equals("true") && array.getJSONObject(0).has("mobile_golang_nearest_url")) {
                            SessionSave.saveSession(TaxiUtil.RUN_GO_LANG, array.getJSONObject(0).getString("is_run_golang"), SplashActivity.this);
                            SessionSave.saveSession(TaxiUtil.NODE_URL, array.getJSONObject(0).getString("mobile_golang_nearest_url"), SplashActivity.this);
                        }

                        if (array.getJSONObject(0).has("is_dlh_golang") && array.getJSONObject(0).getString("is_dlh_golang").equals("true") && array.getJSONObject(0).has("mobile_golang_url")) {
                            SessionSave.saveSession(DriverCommonData.RUN_GO_LANG, array.getJSONObject(0).getString("is_dlh_golang"), SplashActivity.this);
                            SessionSave.saveSession(DriverCommonData.DRIVER_NODE_URL, array.getJSONObject(0).getString("mobile_golang_url"), SplashActivity.this);
//                            SessionSave.saveSession("driver_node_url", json.getString("mobile_socket_http_url"), SplashActivity.this);
                        }

                        if (json.has("chat_node_url")) {
                            SessionSave.saveSession(TaxiUtil.CHAT_NODE_URL, json.getString("chat_node_url"), SplashActivity.this);
                        }
                        if (json.has("call_masking_enable")) {
                            SessionSave.saveSession(TaxiUtil.CALL_MASKING_ENABLE, json.getString("call_masking_enable"), SplashActivity.this);
                        }

                        if (json.has("dispatcher_phone_number")) {
                            SessionSave.saveSession("dispatcher_phone_number", json.getString("dispatcher_phone_number"), SplashActivity.this);
                        }
                        if (array.getJSONObject(0).has("is_driver_auto_accept")) {
                            DriverSessionSave.saveSession("is_driver_auto_accept", array.getJSONObject(0).getString("is_driver_auto_accept"), SplashActivity.this);
                            System.out.println("Check Trip auto accept : " + DriverSessionSave.getSession("is_driver_auto_accept", SplashActivity.this));
                        }
                        if (array.getJSONObject(0).has("pickupsuggestion_url"))
                            SessionSave.saveSession("pickupsuggestion_url", array.getJSONObject(0).getString("pickupsuggestion_url"), SplashActivity.this);
                        if (array.getJSONObject(0).has("pickupsuggestion"))
                            SessionSave.saveSession("pickupsuggestion", array.getJSONObject(0).getString("pickupsuggestion"), SplashActivity.this);
                        if (array.getJSONObject(0).has("sos_setting")) {
                            SessionSave.saveSession(TaxiUtil.sosEnable, array.getJSONObject(0).getString("sos_setting").equals("1"), SplashActivity.this);
                        }
                        SessionSave.saveSession("play_store_version", array.getJSONObject(0).getString("android_passenger_version"), SplashActivity.this);

                        if (array.getJSONObject(0).has("last_forceupdate_version"))
                            SessionSave.saveSession(TaxiUtil.LAST_FORCEUPDATE_VERSION, array.getJSONObject(0).getString("last_forceupdate_version"), SplashActivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.LAST_FORCEUPDATE_VERSION, "0", SplashActivity.this);

                        if (array.getJSONObject(0).has("rental_out_availability"))
                            SessionSave.saveSession(TaxiUtil.RENTAL_OUTSTATION_AVAILABLE, array.getJSONObject(0).getString("rental_out_availability").equals("1"), SplashActivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.RENTAL_OUTSTATION_AVAILABLE, "", SplashActivity.this);

                        SessionSave.saveSession("tax", array.getJSONObject(0).getString("tax"), SplashActivity.this);
                        SessionSave.saveSession("facebook_share", array.getJSONObject(0).getString("facebook_share"), SplashActivity.this);
                        // SessionSave.saveSession("facebook_share", "https://www.facebook.com/adropapp", SplashActivity.this);

                        SessionSave.saveSession("twitter_share", array.getJSONObject(0).getString("twitter_share"), SplashActivity.this);
                        //SessionSave.saveSession("twitter_share", "https://twitter.com/adropapp", SplashActivity.this);

                        SessionSave.saveSession("About", array.getJSONObject(0).getString("aboutpage_description"), SplashActivity.this);
                        SessionSave.saveSession("Currency", array.getJSONObject(0).getString("site_currency") + " ", SplashActivity.this);
                        SessionSave.saveSession("AdminMail", array.getJSONObject(0).getString("admin_email"), SplashActivity.this);
                        SessionSave.saveSession("TellfrdMsg", array.getJSONObject(0).getString("share_content"), SplashActivity.this);
                        SessionSave.saveSession("ShaerMsg", array.getJSONObject(0).getString("tell_to_friend_subject"), SplashActivity.this);
                        SessionSave.saveSession("Metric", array.getJSONObject(0).getString("metric"), SplashActivity.this);
                        SessionSave.saveSession("country_code", array.getJSONObject(0).getString("country_code"), SplashActivity.this);
                        SessionSave.saveSession("android_web_key", array.getJSONObject(0).getString("android_google_api_key"), SplashActivity.this);
                        SessionSave.saveSession("country_iso_code", array.getJSONObject(0).getString("country_iso_code"), SplashActivity.this);
                        try {
                            SessionSave.saveSession("default_city_id", array.getJSONObject(0).getString("default_city_id"), SplashActivity.this);
                            SessionSave.saveSession(DEFAULT_CITY_NAME, array.getJSONObject(0).getString("default_city_name"), SplashActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SessionSave.saveSession("android_foursquare_api_key", array.getJSONObject(0).getString("android_foursquare_api_key"), SplashActivity.this);

                        SessionSave.saveSession(IS_BUISNESS_KEY, array.getJSONObject(0).getString("google_business_key").equals("1"), SplashActivity.this);

                        if (SessionSave.getSession("Metric", SplashActivity.this).equalsIgnoreCase("MILES"))
                            SessionSave.saveSession("Metric_type", "m", SplashActivity.this);
                        else if (SessionSave.getSession("Metric", SplashActivity.this).equalsIgnoreCase("KM"))
                            SessionSave.saveSession("Metric_type", "k", SplashActivity.this);
                        else SessionSave.saveSession("Metric_type", "k", SplashActivity.this);

                        if (array.getJSONObject(0).has("sos_msg"))
                            SessionSave.saveSession("sos_message", array.getJSONObject(0).getString("sos_msg"), SplashActivity.this);

                        if (array.getJSONObject(0).has("playstore_passenger"))
                            SessionSave.saveSession(TaxiUtil.PLAY_STORE_LINK, array.getJSONObject(0).getString("playstore_passenger"), SplashActivity.this);

                        SessionSave.saveSession(API_BASE, array.getJSONObject(0).getString("api_base"), SplashActivity.this);
                        SessionSave.saveSession("logo_base", array.getJSONObject(0).getString("logo_base"), SplashActivity.this);
                        SessionSave.saveSession("site_logo", array.getJSONObject(0).getString("site_logo"), SplashActivity.this);
                        SessionSave.saveSession("Cancellation_setting", array.getJSONObject(0).getString("cancellation_setting"), SplashActivity.this);
                        SessionSave.saveSession("facebook_key", array.getJSONObject(0).getString("facebook_key"), SplashActivity.this);
                        SessionSave.saveSession("skip_credit", array.getJSONObject(0).getString("skip_credit"), SplashActivity.this);
                        SessionSave.saveSession(MODEL_DETAILS, array.getJSONObject(0).getString("model_details"), SplashActivity.this);
                        SessionSave.saveSession("referral_code_info", array.getJSONObject(0).getString("referral_code_info"), SplashActivity.this);

                        SessionSave.saveSession("referral_settings", array.getJSONObject(0).getString("referral_settings"), SplashActivity.this);
                        SessionSave.saveSession("referral_settings_message", array.getJSONObject(0).getString("referral_settings_message"), SplashActivity.this);

                        SessionSave.saveSession(PASS_PAYMENT_OPTION, array.getJSONObject(0).getString("passenger_payment_option"), SplashActivity.this);

                        String googleApiKey = array.getJSONObject(0).getString("android_google_api_key");
                        if (!getString(R.string.googleID).equals(googleApiKey)) {
                            AppController.getInstance().setPlaceApiKey(googleApiKey);
                        }

                        SessionSave.saveSession(TaxiUtil.GOOGLE_KEY, googleApiKey, SplashActivity.this);

                        SessionSave.saveSession(SERVICE_DETAILS, array.getJSONObject(0).getString("service_details"), SplashActivity.this);

                        if (array.getJSONObject(0).has("android_mapbox_key")) {
                            SessionSave.saveSession(TaxiUtil.MAP_BOX_TOKEN, array.getJSONObject(0).getString("android_mapbox_key"), SplashActivity.this);
                        } else {
                            SessionSave.saveSession(TaxiUtil.MAP_BOX_TOKEN, "pk.eyJ1IjoibmFuZGhpbmlzIiwiYSI6ImNqaGl0M3U0aDI5MXczYW8xZGY3bmxod3gifQ.CsQZTI8nf5ZDh8ES3Iu87g", SplashActivity.this);
                        }
                        if (array.getJSONObject(0).has("android_local_map_enable")) {
                            SessionSave.saveSession(TaxiUtil.LOCAL_STORAGE, array.getJSONObject(0).getString("android_local_map_enable").equals("1"), SplashActivity.this);
                        } else {
                            SessionSave.saveSession(TaxiUtil.LOCAL_STORAGE, false, SplashActivity.this);
                        }
//                        if (!SessionSave.getSession(TaxiUtil.MAP_BOX_TOKEN, SplashActivity.this).equals(""))
//                            Mapbox.getInstance(SplashActivity.this, SessionSave.getSession(TaxiUtil.MAP_BOX_TOKEN, SplashActivity.this));

                        if (array.getJSONObject(0).has("map_settings") && array.getJSONObject(0).getJSONObject("map_settings").has("is_google_distance")) {
                            SessionSave.saveSession(TaxiUtil.isGoogleDistance, array.getJSONObject(0).getJSONObject("map_settings").getString("is_google_distance").equals("1"), SplashActivity.this);
                            SessionSave.saveSession(TaxiUtil.isGoogleRouteGeo, array.getJSONObject(0).getJSONObject("map_settings").getString("is_google_direction").equals("1"), SplashActivity.this);
                            SessionSave.saveSession(TaxiUtil.isGoogleGeocoder, array.getJSONObject(0).getJSONObject("map_settings").getString("is_google_geocode").equals("1"), SplashActivity.this);
                            SessionSave.saveSession(TaxiUtil.isNeedtoDrawRoute, array.getJSONObject(0).getJSONObject("map_settings").getString("enable_route").equals("1"), SplashActivity.this);
                            SessionSave.saveSession(TaxiUtil.isNeedtoFetchAddress, array.getJSONObject(0).getJSONObject("map_settings").getString("display_current_location").equals("1"), SplashActivity.this);
                        } else {
                            SessionSave.saveSession(TaxiUtil.isGoogleDistance, true, SplashActivity.this);
                            SessionSave.saveSession(TaxiUtil.isGoogleRouteGeo, true, SplashActivity.this);
                            SessionSave.saveSession(TaxiUtil.isGoogleGeocoder, true, SplashActivity.this);
                            SessionSave.saveSession(TaxiUtil.isNeedtoDrawRoute, true, SplashActivity.this);
                            SessionSave.saveSession(TaxiUtil.isNeedtoFetchAddress, true, SplashActivity.this);
                        }
                        JSONArray jsonarray = new JSONArray(array.getJSONObject(0).getString("passenger_payment_option"));
                        SessionSave.saveSession("pay_mod_name", jsonarray.getJSONObject(0).getString("pay_mod_name"), SplashActivity.this);
                        getCore_Utc = array.getJSONObject(0).getLong("utc_time");
                        SessionSave.saveSession("current_time", getCore_Utc, SplashActivity.this);
                        SessionSave.saveSession("current_time_local", array.getJSONObject(0).getLong("current_time"), SplashActivity.this);
                        boolean deflanAvail = false;
                        try {
                            getCoreLangTime = json.getJSONObject("language_color_status").getString("android_passenger_language");
                            getCoreColorTime = json.getJSONObject("language_color_status").getString("android_passenger_colorcode");
                            SessionSave.saveSession("isFourSquare", array.getJSONObject(0).getString("android_foursquare_status"), SplashActivity.this);
                            String totalLanguage = "";
                            JSONArray pArray = json.getJSONObject("language_color").getJSONObject("android").getJSONArray("passenger_language");
                            for (int i = 0; i < pArray.length(); i++) {
                                String key_ = "";
                                totalLanguage += pArray.getJSONObject(i).getString("language").replaceAll(".xml", "") + "____";
                                SessionSave.saveSession("LANG" + i, pArray.getJSONObject(i).getString("language"), SplashActivity.this);
                                SessionSave.saveSession("LANGTemp" + i, pArray.getJSONObject(i).getString("design_type"), SplashActivity.this);
                                SessionSave.saveSession("LANGCode" + i, pArray.getJSONObject(i).getString("language_code"), SplashActivity.this);
                                SessionSave.saveSession(pArray.getJSONObject(i).getString("language"), pArray.getJSONObject(i).getString("url"), SplashActivity.this);
                                if (!SessionSave.getSession("LANGDef", SplashActivity.this).equals("") && pArray.getJSONObject(i).getString("language").contains(SessionSave.getSession("LANGDef", SplashActivity.this))) {
                                    deflanAvail = true;
                                }
                            }
                            DriverSystems.out.println("___________defff" + deflanAvail);
                            if (SessionSave.getSession("LANGDef", SplashActivity.this).trim().equals("") || !deflanAvail) {
                                SessionSave.saveSession("LANGDef", SessionSave.getSession("LANG0", SplashActivity.this), SplashActivity.this);
                                SessionSave.saveSession("LANGTempDef", SessionSave.getSession("LANGTemp0", SplashActivity.this), SplashActivity.this);
                                SessionSave.saveSession(LANG, pArray.getJSONObject(0).getString("language_code").replaceAll(".xml", ""), SplashActivity.this);
                                String url = SessionSave.getSession(SessionSave.getSession("LANG" + 0, SplashActivity.this), SplashActivity.this);
                                SessionSave.saveSession("currentStringUrl", url, SplashActivity.this);
                            }
                            SessionSave.saveSession("lang_json", totalLanguage, SplashActivity.this);
                            SessionSave.saveSession("colorcode", json.getJSONObject("language_color").getJSONObject("android").getString("colorcode"), SplashActivity.this);
                        } catch (JSONException e) {
                            errorInSplash(DriverNC.getString(R.string.server_con_error));
                            e.printStackTrace();
                        }
                        //android_passenger_language
                        if (!SessionSave.getSession(TaxiUtil.PASSENGER_LANGUAGE_TIME, SplashActivity.this).trim().equals(getCoreLangTime)) {
                            DriverSystems.out.println("___________defffcs");
                            new SplashActivity.callString(getCoreColorTime);
                        } else if (!SessionSave.getSession(TaxiUtil.PASSENGER_COLOR_TIME, SplashActivity.this).trim().equals(getCoreColorTime)) {
                            new SplashActivity.callColor(getCoreLangTime);
                            DriverSystems.out.println("___________defffcc");
                        } else if (VersionCheck()) {
                            DriverSystems.out.println("___________defffver");
                            versionAlert(SplashActivity.this);
                        } else {
                            new Handler().postDelayed(() -> {
                                Intent i;
                                if (SessionSave.getSession(PASS_ID, SplashActivity.this).equals("")) {
                                    if (!SessionSave.getSession("IsOTPSend", SplashActivity.this).equals("")) {
                                        DriverSystems.out.println("detail_v9");
//                                        i = new Intent(SplashActivity.this, VerificationActivity.class);
//                                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                        startActivity(i);
//                                        overridePendingTransition(0, 0);
//                                        finish();
                                    } else {
                                        i = new Intent(SplashActivity.this, DriverUserLoginAct.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(i);
                                        overridePendingTransition(0, 0);
                                        finish();
                                    }

                                } else {
                                    if (SessionSave.getSession("trip_id", SplashActivity.this).equals("")) {
                                        DivertToHomeScreen();
                                    } else {
                                        if (!SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("") && !SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("0.0")) {
                                            DivertToHomeScreen();
                                        }
                                    }
                                }
                            }, 200);
                        }
                    } else if (json.getInt("status") == -101) {
                        if (json.has("message")) forceLogout(json.getString("message"));
                        else forceLogout(DriverNC.getString(R.string.server_error));
                    } else if (json.getInt("status") == 0) {
                        //no changes made
                    } else {
                        errorInSplash(json.getString("message"));
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    errorInSplash(DriverNC.getString(R.string.server_con_error));
                    e.printStackTrace();
                }
            } else {
                errorInSplash(DriverNC.getString(R.string.server_error));
            }
        }
    }

    private class callColor implements DriverAPIResult {
        public callColor(final String url) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON_NoProgress(SplashActivity.this, this, null, true, SessionSave.getSession("colorcode", SplashActivity.this), true).execute();

            Log.e("link__color", SessionSave.getSession("colorcode", SplashActivity.this));
        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            if (isSuccess) {
                SessionSave.saveSession(TaxiUtil.PASSENGER_COLOR_TIME, getCoreColorTime, SplashActivity.this);
                getAndStoreColorValues(result);
                SessionSave.saveSession("wholekeyColor", result, SplashActivity.this);
                new Handler().postDelayed(() -> {
                    Intent i;

                   /* if (SessionSave.getSession(TaxiUtil.USER_PRIVACY_POLICY, SplashActivity.this).equals("")) {
                        i = new Intent(SplashActivity.this, DevicePermissionActivity.class);
                        startActivity(i);
                        finish();
                    } else {*/
                    if (SessionSave.getSession(PASS_ID, SplashActivity.this).equals("")) {

                        if (!SessionSave.getSession("IsOTPSend", SplashActivity.this).equals("")) {
                            DriverSystems.out.println("detail_v7");
//                            i = new Intent(SplashActivity.this, VerificationActivity.class);
//                            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            startActivity(i);
//                            overridePendingTransition(0, 0);
//                            finish();
                        } else {
                            i = new Intent(SplashActivity.this, DriverUserLoginAct.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            finish();
                        }

                    } else {
                        if (SessionSave.getSession("trip_id", SplashActivity.this).equals("")) {
                            if (!SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("") && !SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("0.0")) {
                                DivertToHomeScreen();
                            }
                        } else {
                            if (!SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("") && !SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("0.0")) {

                                DivertToHomeScreen();
                            }
                        }
                    }
                    //  }
                }, 200);

            } else errorInSplash(DriverNC.getString(R.string.server_con_error));
        }
    }

    private class callString implements DriverAPIResult {
        String color_time;

        public callString(final String color_time) {
            // TODO Auto-generated constructor stub
            this.color_time = color_time;

            String urls = SessionSave.getSession("currentStringUrl", SplashActivity.this);
            if (urls.equals("")) {
                urls = SessionSave.getSession(SessionSave.getSession("LANGDef", SplashActivity.this), SplashActivity.this);
                if (SessionSave.getSession("LANGTempDef", SplashActivity.this).trim().equalsIgnoreCase("RTL")) {
                    SessionSave.saveSession("Lang_Country", "ar_EG", SplashActivity.this);
                    SessionSave.saveSession(LANG, "ar", SplashActivity.this);
                    Configuration config = new Configuration();
                    String langcountry = SessionSave.getSession("Lang_Country", SplashActivity.this);
                    String[] arry = langcountry.split("_");
                    config.locale = new Locale(arry[0], arry[1]);
                    Locale.setDefault(new Locale(arry[0], arry[1]));
                }
            }
            new APIService_Retrofit_JSON_NoProgress(SplashActivity.this, this, null, true, urls, true).execute();
        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            if (isSuccess) {
                SessionSave.saveSession(TaxiUtil.PASSENGER_LANGUAGE_TIME, getCoreLangTime, SplashActivity.this);
                getAndStoreStringValues(result);
                SessionSave.saveSession("wholekey", result, SplashActivity.this);
                if (SessionSave.getSession("wholekeyColor", SplashActivity.this).trim().equals("") || !SessionSave.getSession(TaxiUtil.PASSENGER_COLOR_TIME, SplashActivity.this).equals(color_time))
                    new SplashActivity.callColor("");
                else {
                    if (TaxiUtil.isCurrentTimeZone(getCore_Utc)) {
                        new Handler().postDelayed(() -> {
                            Intent i;
                            if (SessionSave.getSession(PASS_ID, SplashActivity.this).equals("")) {

                                if (!SessionSave.getSession("IsOTPSend", SplashActivity.this).equals("")) {
                                    DriverSystems.out.println("detail_v8");
//                                    i = new Intent(SplashActivity.this, VerificationActivity.class);
//                                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                    startActivity(i);
//                                    overridePendingTransition(0, 0);
//                                    finish();
                                } else {
                                    i = new Intent(SplashActivity.this, DriverUserLoginAct.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(i);
                                    overridePendingTransition(0, 0);
                                    finish();
                                }
                            } else {
                                if (SessionSave.getSession("trip_id", SplashActivity.this).equals("")) {
                                    if (!SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("") && !SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("0.0")) {
                                        DivertToHomeScreen();
                                    }
                                } else {
                                    if (!SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("") && !SessionSave.getSession("PLAT", SplashActivity.this).equalsIgnoreCase("0.0")) {
                                        DivertToHomeScreen();
                                    }
                                }
                            }
                        }, 200);
                    } else {
                        cancelLoading();
                        errorInSplash(DriverNC.getString(R.string.date_change));
                    }
                }
            } else errorInSplash(DriverNC.getString(R.string.server_con_error));
        }
    }

    public void saveDriverSession(String result) throws JSONException {

        final JSONObject json = new JSONObject(result);
        DriverSessionSave.saveSession(DriverCommonData.GETCORE_LASTUPDATE, json.getString("gt_lst_time"), SplashActivity.this);
        if (json.has(DriverCommonData.ACTIVITY_BG))
            DriverSessionSave.saveSession(DriverCommonData.ACTIVITY_BG, json.getString(DriverCommonData.ACTIVITY_BG), SplashActivity.this);
        if (json.has(DriverCommonData.ERROR_LOGS))
            DriverSessionSave.saveSession(DriverCommonData.ERROR_LOGS, json.getString(DriverCommonData.ERROR_LOGS).equals("1"), SplashActivity.this);
        JSONArray jArry = json.getJSONArray("detail");
                       /* if (json.has("mobile_socket_http_url")) {
                            SessionSave.saveSession(CommonData.NODE_URL, json.getString("mobile_socket_http_url"), SplashAct.this);
                        }*/
        if (json.has("https_node_url")) {
            DriverSessionSave.saveSession(DriverCommonData.NODE_URL, json.getString("https_node_url"), SplashActivity.this);
        }

        if (json.has("mobile_socket_http_domain")) {
            DriverSessionSave.saveSession(DriverCommonData.NODE_DOMAIN, json.getString("mobile_socket_http_domain"), SplashActivity.this);
        }

        if (json.has(DriverCommonData.HELP_URL)) {
            DriverSessionSave.saveSession(DriverCommonData.HELP_URL, json.getString(DriverCommonData.HELP_URL), SplashActivity.this);
        }
        if (jArry.getJSONObject(0).has("manual_waiting_enable")) {
            DriverSessionSave.saveSession(DriverCommonData.WAITING_TIME_MANUAL, jArry.getJSONObject(0).getString("manual_waiting_enable").equals("1"), SplashActivity.this);
        }
        if (jArry.getJSONObject(0).has(DriverCommonData.SKIP_DRIVER_EMAIL))
            DriverSessionSave.saveSession(DriverCommonData.SKIP_DRIVER_EMAIL, jArry.getJSONObject(0).getString(DriverCommonData.SKIP_DRIVER_EMAIL).equals("1"), SplashActivity.this);
        else
            DriverSessionSave.saveSession(DriverCommonData.SKIP_DRIVER_EMAIL, false, SplashActivity.this);
        DriverSessionSave.saveSession("api_base", jArry.getJSONObject(0).getString("api_base"), SplashActivity.this);
        DriverSessionSave.saveSession("isFourSquare", jArry.getJSONObject(0).getString("android_foursquare_status"), SplashActivity.this);
        DriverSessionSave.saveSession("android_foursquare_api_key", jArry.getJSONObject(0).getString("android_foursquare_api_key"), SplashActivity.this);
        DriverSessionSave.saveSession("facebook_key", jArry.getJSONObject(0).getString("facebook_key"), SplashActivity.this);
        DriverSessionSave.saveSession("play_store_version", jArry.getJSONObject(0).getString("android_driver_version"), SplashActivity.this);

        if (jArry.getJSONObject(0).has("playstore_driver"))
            DriverSessionSave.saveSession(DriverCommonData.PLAY_STORE_LINK, jArry.getJSONObject(0).getString("playstore_driver"), SplashActivity.this);

        if (jArry.getJSONObject(0).has("last_forceupdate_version"))
            DriverSessionSave.saveSession(DriverCommonData.LAST_FORCEUPDATE_VERSION, jArry.getJSONObject(0).getString("last_forceupdate_version"), SplashActivity.this);
        else
            DriverSessionSave.saveSession(DriverCommonData.LAST_FORCEUPDATE_VERSION, "0", SplashActivity.this);

        DriverSessionSave.saveSession("country_iso_code", jArry.getJSONObject(0).getString("country_iso_code"), SplashActivity.this);

        DriverSessionSave.saveSession("android_web_key", jArry.getJSONObject(0).getString("android_google_api_key"), SplashActivity.this);

        if (json.has(DriverCommonData.TIMEZONE)) {
            DriverSessionSave.saveSession(DriverCommonData.TIMEZONE, json.getString(DriverCommonData.TIMEZONE), SplashActivity.this);
        }

        int length = jArry.length();

        String googleApiKey = jArry.getJSONObject(0).getString("android_google_api_key");
        if (!getString(R.string.googleID).equals(googleApiKey))
            AppController.getInstance().setPlaceApiKey(googleApiKey);

        DriverSessionSave.saveSession(DriverCommonData.GOOGLE_KEY, googleApiKey, SplashActivity.this);

        if (jArry.getJSONObject(0).has("android_mapbox_key")) {
            DriverSessionSave.saveSession(DriverCommonData.MAP_BOX_TOKEN, jArry.getJSONObject(0).getString("android_mapbox_key"), SplashActivity.this);
        } else {
            DriverSessionSave.saveSession(DriverCommonData.MAP_BOX_TOKEN, "pk.eyJ1IjoibmFuZGhpbmlzIiwiYSI6ImNqaGl0M3U0aDI5MXczYW8xZGY3bmxod3gifQ.CsQZTI8nf5ZDh8ES3Iu87g", SplashActivity.this);
        }
        if (jArry.getJSONObject(0).has("android_local_map_enable")) {
            DriverSessionSave.saveSession(DriverCommonData.LOCAL_STORAGE, jArry.getJSONObject(0).getString("android_local_map_enable").equals("1"), SplashActivity.this);
        } else {
            DriverSessionSave.saveSession(DriverCommonData.LOCAL_STORAGE, false, SplashActivity.this);
        }
//                        if (!SessionSave.getSession(CommonData.MAP_BOX_TOKEN, SplashAct.this).equals(""))
//                            Mapbox.getInstance(SplashAct.this, SessionSave.getSession(CommonData.MAP_BOX_TOKEN, SplashAct.this));
        if (jArry.getJSONObject(0).has("sos_setting"))
            DriverSessionSave.saveSession(DriverCommonData.SOS_ENABLED, jArry.getJSONObject(0).getString("sos_setting").equals("1"), SplashActivity.this);
        if (jArry.getJSONObject(0).has("map_settings") && jArry.getJSONObject(0).getJSONObject("map_settings").has("is_google_distance")) {
            DriverSessionSave.saveSession(DriverCommonData.isGoogleDistance, jArry.getJSONObject(0).getJSONObject("map_settings").getString("is_google_distance").equals("1"), SplashActivity.this);
            DriverSessionSave.saveSession(DriverCommonData.isGoogleRoute, jArry.getJSONObject(0).getJSONObject("map_settings").getString("is_google_direction").equals("1"), SplashActivity.this);
            DriverSessionSave.saveSession(DriverCommonData.isGoogleGeocoder, jArry.getJSONObject(0).getJSONObject("map_settings").getString("is_google_geocode").equals("1"), SplashActivity.this);
            DriverSessionSave.saveSession(DriverCommonData.isNeedtoDrawRoute, jArry.getJSONObject(0).getJSONObject("map_settings").getString("enable_route").equals("1"), SplashActivity.this);
            DriverSessionSave.saveSession(DriverCommonData.isNeedtofetchAddress, jArry.getJSONObject(0).getJSONObject("map_settings").getString("display_current_location").equals("1"), SplashActivity.this);
        } else {
            DriverSessionSave.saveSession(DriverCommonData.isGoogleDistance, true, SplashActivity.this);
            DriverSessionSave.saveSession(DriverCommonData.isGoogleRoute, true, SplashActivity.this);
            DriverSessionSave.saveSession(DriverCommonData.isGoogleGeocoder, true, SplashActivity.this);
            DriverSessionSave.saveSession(DriverCommonData.isNeedtoDrawRoute, true, SplashActivity.this);
            DriverSessionSave.saveSession(DriverCommonData.isNeedtofetchAddress, true, SplashActivity.this);
        }

        if (jArry.getJSONObject(0).has("sos_msg"))
            DriverSessionSave.saveSession("sos_message", jArry.getJSONObject(0).getString("sos_msg"), SplashActivity.this);

        for (int i = 0; i < length; i++) {
            DriverSessionSave.saveSession("noimage_base", jArry.getJSONObject(i).getString("noimage_base"), getApplicationContext());
            DriverSessionSave.saveSession("site_currency", jArry.getJSONObject(i).getString("site_currency") + " ", getApplicationContext());
            DriverSystems.out.println("chry_str_splash" + jArry.getJSONObject(i).getString("site_currency"));
            DriverSessionSave.saveSession("invite_txt", jArry.getJSONObject(i).getString("aboutpage_description"), getApplicationContext());
            DriverSessionSave.saveSession("referal", jArry.getJSONObject(i).getString("driver_referral_settings"), getApplicationContext());
            DriverSessionSave.saveSession("Metric", jArry.getJSONObject(i).getString("metric"), SplashActivity.this);
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
                DriverSessionSave.saveSession("LANG" + i, pArray.getJSONObject(i).getString("language"), SplashActivity.this);
                DriverSessionSave.saveSession("LANGTemp" + i, pArray.getJSONObject(i).getString("design_type"), SplashActivity.this);
                DriverSessionSave.saveSession("LANGCode" + i, pArray.getJSONObject(i).getString("language_code"), SplashActivity.this);
                DriverSessionSave.saveSession(pArray.getJSONObject(i).getString("language"), pArray.getJSONObject(i).getString("url"), SplashActivity.this);
                if (!DriverSessionSave.getSession("LANGDef", SplashActivity.this).equals("") && pArray.getJSONObject(i).getString("language").contains(DriverSessionSave.getSession("LANGDef", SplashActivity.this))) {
                    deflanAvail = true;
                }
            }
            DriverSystems.out.println("___________defff" + deflanAvail);
            if (DriverSessionSave.getSession("LANGDef", SplashActivity.this).trim().equals("") || !deflanAvail) {
                DriverSessionSave.saveSession("LANGDef", DriverSessionSave.getSession("LANG0", SplashActivity.this), SplashActivity.this);
                DriverSessionSave.saveSession("LANGTempDef", DriverSessionSave.getSession("LANGTemp0", SplashActivity.this), SplashActivity.this);
                DriverSessionSave.saveSession("Lang", pArray.getJSONObject(0).getString("language_code").replaceAll(".xml", ""), SplashActivity.this);
                String url = DriverSessionSave.getSession(DriverSessionSave.getSession("LANG" + 0, SplashActivity.this), SplashActivity.this);
                DriverSessionSave.saveSession("currentStringUrl", url, SplashActivity.this);
            }

            DriverSessionSave.saveSession("lang_json", totalLanguage, SplashActivity.this);
            DriverSessionSave.saveSession("colorcode", json.getJSONObject("language_color").getJSONObject("android").getString("driverColorCode"), SplashActivity.this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void driverStatus() {

        Intent i;
        if (DriverSessionSave.getSession("Id", SplashActivity.this).equals("")) {
            i = new Intent(SplashActivity.this, DriverUserLoginAct.class);
            startActivity(i);
            finish();
        } else {
            if (SessionSave.getSession("user_type", SplashActivity.this).trim().equalsIgnoreCase("P")) {
//                if (!SessionSave.getSession(PASS_ID, SplashActivity.this).trim().isEmpty()) {
//                    i = new Intent(SplashActivity.this, MainHomeFragmentActivity.class);
//                    startActivity(i);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            finish();
//                        }
//                    }, 2000);
//                } else {
                i = new Intent(SplashActivity.this, DriverUserLoginAct.class);
                startActivity(i);
                finish();
//                }

            } else {
                if (DriverSessionSave.getSession("trip_id", SplashActivity.this).equals("")) {
                    i = new Intent(SplashActivity.this, DriverMyStatus.class);
                    DriverSessionSave.saveSession("need_animation", true, SplashActivity.this);
                    startActivity(i);
                    finish();
                } else {
                    if (DriverSessionSave.getSession("travel_status", SplashActivity.this).equals("5")) {
                        i = new Intent(SplashActivity.this, DriverTripHistoryAct.class);
                        startActivity(i);
                        finish();
                    } else {
                        i = new Intent(SplashActivity.this, DriverOngoingAct.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        }
    }
}