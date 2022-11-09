package com.taximobility.Login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.taximobility.MainActivity;
import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.TermsAndConditions;
import com.taximobility.driver.DriverSplashAct;
import com.taximobility.driver.DriverUserLoginAct;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.features.CToast;
import com.taximobility.interfaces.APIResult;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.tripCancel.CreditCardData;
import com.taximobility.tripCancel.CreditCardRepository;
import com.taximobility.util.CL;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.NetworkStatus;
import com.taximobility.util.SessionSave;
import com.taximobility.util.ShowToast;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;

import static com.taximobility.util.ConstantsKt.CREDIT_CARD;
import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.PASS_NAME;

//import com.taximobility.util.DeviceUtils;

public class LoginSelectionAcivity extends MainActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private LoginButton fbloginBtn;
    private ImageView body_iv, center_image;
    private Button continue_phone;
    private CallbackManager callbackManager;
    private String fbaccesstoken = "", fbuserid = "", fbname = "", lname = "", Access_token;
    private GoogleApiClient mGoogleApiClient;
    private LoginSelectionAcivity.PendingAction pendingAction = LoginSelectionAcivity.PendingAction.NONE;
    private Dialog dialog;
    private CreditCardRepository creditCardRepository;
TextView driver_start;
    @Override
    public int setLayout() {
        setLocale();
        return R.layout.activity_login_selection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null && getIntent().getStringExtra("alert_message") != null) {
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
            dialog = Utility.alert_view_dialog(LoginSelectionAcivity.this, "" +
                            NC.getResources().getString(R.string.message),
                    "" + getIntent().getStringExtra("alert_message"),
                    "" + NC.getResources().getString(R.string.ok),
                    "",
                    true, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, null, "");
        }

    }

    @Override
    public void onStart() {

        super.onStart();
        /*
         * Connect the client. Don't re-start any requests here; instead, wait for onResume()
         */
        try {
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkStatus.appContext = this;
        NetworkStatus.isOnline(LoginSelectionAcivity.this);
    }

    /**
     * this method is used to apply font for all fields
     */

    @Override
    public void priorChanges() {
        FontHelper.applyFont(this, findViewById(R.id.userhome_contains));
        super.priorChanges();
    }

    @Override
    public void Initialize() {
        if (!SessionSave.getSession("facebook_key", LoginSelectionAcivity.this).equals(""))
            FacebookSdk.setApplicationId(SessionSave.getSession("facebook_key", LoginSelectionAcivity.this));
        else
            FacebookSdk.setApplicationId(getString(R.string.facebookAppId));

        Colorchange.ChangeColor((ViewGroup) (((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0)), LoginSelectionAcivity.this);

        TaxiUtil.mActivitylist.add(this);
        LoginManager.getInstance().logOut();
        creditCardRepository = CreditCardRepository.getRepository(this);
//        FacebookSdk.sdkInitialize(this);

        body_iv = findViewById(R.id.body_iv);
        center_image = findViewById(R.id.center_image);
        continue_phone = findViewById(R.id.continue_phone);
        fbloginBtn = findViewById(R.id.fbloginBtn);
        callbackManager = CallbackManager.Factory.create();
        driver_start = findViewById(R.id.driver_start);
        driver_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DriverSessionSave.saveSession("base_url","",LoginSelectionAcivity.this);
                Intent mIntent = new Intent(LoginSelectionAcivity.this, DriverSplashAct.class);
                startActivity(mIntent);

            }
        });
        setSpannableTextView(findViewById(R.id.t_c_web_txt));

        fbloginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Access_token = loginResult.getAccessToken().getToken();
                handlePendingAction();
                updateUI();
            }

            @Override
            public void onCancel() {

                if (pendingAction != LoginSelectionAcivity.PendingAction.NONE) {
                    showAlert();
                    pendingAction = LoginSelectionAcivity.PendingAction.NONE;
                }
                updateUI();
            }

            @Override
            public void onError(FacebookException exception) {

                if (pendingAction != LoginSelectionAcivity.PendingAction.NONE && exception instanceof FacebookAuthorizationException) {
                    showAlert();
                    pendingAction = LoginSelectionAcivity.PendingAction.NONE;
                }
                updateUI();
            }

            private void showAlert() {
                dialog = Utility.alert_view_dialog(LoginSelectionAcivity.this, "" + NC.getResources().getString(R.string.cancelled),
                        "" + NC.getResources().getString(R.string.permission_not_granted),
                        "" + NC.getResources().getString(R.string.ok),
                        "",
                        true, null, null, "");
            }
        });

        fbloginBtn.setReadPermissions("email");
        continue_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionSave.saveSession(TaxiUtil.NEED_TO_COMPLETE_CARD_REG, false, LoginSelectionAcivity.this);
                SessionSave.saveSession(PASS_ID, "", LoginSelectionAcivity.this);
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if (isLoggedIn) {
                    LoginManager.getInstance().logOut();
                }
                final Intent i = new Intent(LoginSelectionAcivity.this, DriverUserLoginAct.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle detail_fb = new Bundle();
                i.putExtra("isfacebooklogin", false);
                i.putExtras(detail_fb);
                startActivity(i);
                finish();
            }
        });

        Glide.with(this).load(SessionSave.getSession("image_path", this) + "signInLogo.png").apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(body_iv);
        if (servicesConnected()) {
            buildGoogleApiClient();
            @SuppressLint("MissingPermission") Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                double P_latitude = mLastLocation.getLatitude();
                double P_longitude = mLastLocation.getLongitude();
                SessionSave.saveSession("PLAT", "" + P_latitude, LoginSelectionAcivity.this);
                SessionSave.saveSession("PLNG", P_longitude + "", LoginSelectionAcivity.this);
            }
        }
/*
        String reqString = Build.MANUFACTURER;
        if (reqString.toLowerCase().contains("huawei")) {
            if (SessionSave.getSession("show_hauwai_alert", LoginSelectionAcivity.this, false)) {
                HuaweiDeviceAlert();
            }
        }

 */
    }

    /**
     * Method to set spannable textview for clickable Terms and Condition and Privacy Policy
     *
     * @param view
     */
    private void setSpannableTextView(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                NC.getString(R.string.terms_condition) + " ");
        spanTxt.append(NC.getString(R.string.terms_condition2));
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(CL.getColor(LoginSelectionAcivity.this, R.color.button_accept));    // you can use custom color
                ds.setUnderlineText(true);
            }

            @Override
            public void onClick(View widget) {
                TermsConditions();
            }
        }, spanTxt.length() - NC.getString(R.string.terms_condition2).length(), spanTxt.length(), 0);
        spanTxt.append(" " + NC.getString(R.string.and));
        spanTxt.setSpan(new ForegroundColorSpan(CL.getColor(LoginSelectionAcivity.this, R.color.black)), spanTxt.length() - NC.getString(R.string.and).length(), spanTxt.length(), 0);
        spanTxt.append(" " + NC.getString(R.string.privacy_policy));
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(CL.getColor(LoginSelectionAcivity.this, R.color.button_accept));    // you can use custom color
                ds.setUnderlineText(true);
            }

            @Override
            public void onClick(View widget) {
                String url = "&type=dynamic_page&pagename=9&device_type=1";
                new LoginSelectionAcivity.ShowWebpage(url, null, "P");
            }
        }, spanTxt.length() - NC.getString(R.string.privacy_policy).length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    /*
     * this method is used to call TermsConditions api
     * */
    private void TermsConditions() {

        try {
            String url = "&type=dynamic_page&pagename=3&device_type=1";
            new LoginSelectionAcivity.ShowWebpage(url, null, "T");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    protected void HuaweiDeviceAlert() {


        dialog = Utility.alert_view_dialog(LoginSelectionAcivity.this,
                NC.getResources().getString(R.string.huawei_title),
                "" + String.format(NC.getResources().getString(R.string.huawei_msg)),
                "" + NC.getResources().getString(R.string.ok), "", false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SessionSave.saveSession("show_hauwai_alert", false, LoginSelectionAcivity.this);
                        EnableHuaweiProtectedApps();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, "");

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

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    private String getUserSerial() {
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

    private void handlePendingAction() {

        LoginSelectionAcivity.PendingAction previouslyPendingAction = pendingAction;
        pendingAction = LoginSelectionAcivity.PendingAction.NONE;
    }

    private boolean servicesConnected() {

        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        return ConnectionResult.SUCCESS == resultCode;
    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    /**
     * <p>
     * This method used get the FB logged user details
     * </p>
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * <p>
     * This method used to check active session available or not,<br>
     * then call the passenger_fb_connect api with fb user information.
     * </p>
     */
    private void updateUI() {

        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
        final Profile profile = Profile.getCurrentProfile();
        Systems.out.println("success" + enableButtons);
        Systems.out.println("success profile" + profile);
        if (enableButtons && profile != null) {
            Systems.out.println("success 1");
            Bundle params = new Bundle();
            params.putString("fields", "id,name,email");
            new GraphRequest(AccessToken.getCurrentAccessToken(), "/me", params, HttpMethod.GET, new GraphRequest.Callback() {
                public void onCompleted(GraphResponse response) {

                    try {
                        Log.e("JSON", response.toString());
                        JSONObject data = response.getJSONObject();
                        fbaccesstoken = Access_token;
                        fbuserid = data.getString("id");
                        fbname = profile.getFirstName();
                        lname = profile.getLastName();

                        if (data.has("email")) {
                            String emailid = data.getString("email");
                            callfb(data.getString("id"), emailid, profile.getFirstName(), profile.getLastName());

                        } else {
                            callfb(data.getString("id"), "", profile.getFirstName(), profile.getLastName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).executeAsync();
        } else {
            Bundle params = new Bundle();
            params.putString("fields", "id,name,email");
            new GraphRequest(AccessToken.getCurrentAccessToken(), "/me", params, HttpMethod.GET, new GraphRequest.Callback() {
                public void onCompleted(GraphResponse response) {

                    try {
                        Log.e("JSON", response.toString());
                        JSONObject data = response.getJSONObject();
                        fbaccesstoken = Access_token;
                        fbuserid = data.getString("id");
                        fbname = data.getString("name");
                        String emailid = "";
                        if (data.has("email")) {
                            emailid = data.getString("email");
                            callfb(data.getString("id"), emailid, data.getString("name"), "");
                        } else {
                            callfb(data.getString("id"), "", data.getString("name"), "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).executeAsync();
        }
    }

    /**
     * this method is used to call the fb api call
     *
     * @param emailid parameter which passes by getting from user if it <br>
     *                not exist in fb sdk</br>
     * @param id      this id get from fb and passes as parameter
     */

    void callfb(String id, String emailid, String firstname, String lastname) {
        try {
            JSONObject j = new JSONObject();
            j.put("accesstoken", Access_token);
            j.put("userid", id);
            j.put("fname", "" + fbname);
            j.put("lname", "" + lname);
            j.put("fbemail", emailid);
            String token = SessionSave.getSession(TaxiUtil.DEVICE_TOKEN, LoginSelectionAcivity.this);
            j.put("devicetoken", token);
            j.put("deviceid", "" + SessionSave.getSession("mDevice_id", LoginSelectionAcivity.this));
            j.put("devicetype", "1");
     //       j.put("device_info", new JSONObject(new Gson().toJson(DeviceUtils.INSTANCE.getAllInfo(LoginSelectionAcivity.this))));
            new LoginSelectionAcivity.FbLogin("type=passenger_fb_connect", j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void DivertToLoginScreen(String email) {
        final Intent i = new Intent(LoginSelectionAcivity.this, DriverUserLoginAct.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle detail_fb = new Bundle();
        detail_fb.putString("facebook_name", fbname);
        detail_fb.putString("facebook_email_id", email);
        detail_fb.putString("facebook_id", fbuserid);
        detail_fb.putString("facebook_token", fbaccesstoken);
        i.putExtras(detail_fb);
        i.putExtra("isfacebooklogin", true);
        startActivity(i);
        finish();
    }

    protected void setEditTextMaxLength(final EditText editText, int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(FilterArray);
    }

    protected void getEmailId(final String id, final String firstname, final String lastname) {
        // TODO Auto-generated method stub

        final View view = View.inflate(LoginSelectionAcivity.this, R.layout.forgot_popup, null);
        final Dialog mDialog = new Dialog(LoginSelectionAcivity.this, R.style.NewDialog);
        mDialog.setContentView(view);
        mDialog.setCancelable(false);
        mDialog.show();
//        final TextView t = mDialog.findViewById(R.id.f_textview);
//        t.setText(NC.getResources().getString(R.string.email));
        final EditText mail = mDialog.findViewById(R.id.forgotmail);
        mail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mail.setHint(NC.getResources().getString(R.string.enter_the_email));
        mDialog.findViewById(R.id.for_sep).setVisibility(View.VISIBLE);
        setEditTextMaxLength(mail, 60);
        final TextView OK = mDialog.findViewById(R.id.okbtn);
        final TextView Cancel = mDialog.findViewById(R.id.cancelbtn);

        Point pointSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(pointSize);

        OK.setOnClickListener(new View.OnClickListener() {
            private String emailid;

            @Override
            public void onClick(final View arg0) {
                // TODO Auto-generated method stub
                try {
                    emailid = mail.getText().toString();
                    if (validations(ValidateAction.isValidMail, LoginSelectionAcivity.this, emailid)) {
                        callfb(id, emailid, firstname, lastname);
                    } else {
                        CToast.ShowToast(LoginSelectionAcivity.this, NC.getResources().getString(R.string.enter_the_valid_email));
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {

                LoginManager.getInstance().logOut();
                mDialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (dialog != null)
            Utility.closeDialog(dialog);
        super.onDestroy();
        TaxiUtil.mActivitylist.remove(this);
    }

    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                double P_latitude = mLastLocation.getLatitude();
                double P_longitude = mLastLocation.getLongitude();
                SessionSave.saveSession("PLAT", "" + P_latitude, LoginSelectionAcivity.this);
                SessionSave.saveSession("PLNG", P_longitude + "", LoginSelectionAcivity.this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }


    private enum PendingAction {
        NONE, POST_PHOTO, POST_STATUS_UPDATE
    }

    /**
     * class to show webpage for terms and condition
     *
     * @author developer
     */
    private class ShowWebpage implements APIResult {
        String type;

        protected ShowWebpage(final String string, JSONObject data, String type) {
            // TODO Auto-generated constructor stub
            this.type = type;
            new APIService_Retrofit_JSON(LoginSelectionAcivity.this, this, true, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "?" + "lang=" + SessionSave.getSession("Lang", LoginSelectionAcivity.this) + string).execute();
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            try {
                if (isSuccess) {
                    final Intent intent = new Intent(LoginSelectionAcivity.this, TermsAndConditions.class);
                    final Bundle bundle = new Bundle();
                    intent.putExtra("content", result);
                    if (type.equals("T"))
                        bundle.putString("name", NC.getString(R.string.termcond));
                    else
                        bundle.putString("name", NC.getString(R.string.privacy_policy));
                    bundle.putBoolean("status", true);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            CToast.ShowToast(LoginSelectionAcivity.this, NC.getString(R.string.server_con_error));
                        }
                    });
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    /**
     * This method used to login through fb
     * <p>
     * This method used to login through fb
     * </p>
     *
     * @author developer
     */
    private class FbLogin implements APIResult {
        private String email;
        private String id;
        private String photo;
        private String tellfrdMsg = "";
        private JSONObject fbData;

        private FbLogin(final String string, final JSONObject data) {
            showLoading(LoginSelectionAcivity.this);
            new APIService_Retrofit_JSON(LoginSelectionAcivity.this, this, data, false, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession("Lang", LoginSelectionAcivity.this) + "&" + string).execute();
            fbData = data;
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            try {
                cancelLoading();
                if (isSuccess) {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        final JSONObject detail = json.getJSONObject("detail");
                        email = detail.getString("email");
                        tellfrdMsg = detail.getString("telltofriend_message");
                        id = detail.getString("id");
                        photo = detail.getString("profile_image");
                        SessionSave.saveSession(PASS_NAME, detail.getString("name"), LoginSelectionAcivity.this);
                        SessionSave.saveSession("Email", email, LoginSelectionAcivity.this);
                        SessionSave.saveSession(PASS_ID, id, LoginSelectionAcivity.this);

                        SessionSave.saveSession("CountyCode", detail.getString("country_code"), LoginSelectionAcivity.this);
                        SessionSave.saveSession("Phone", detail.getString("phone"), LoginSelectionAcivity.this);

                        SessionSave.saveSession("ProfileImage", photo, LoginSelectionAcivity.this);
                        SessionSave.saveSession("RefCode", json.getJSONObject("detail").getString("referral_code"), LoginSelectionAcivity.this);
                        SessionSave.saveSession("RefAmount", json.getJSONObject("detail").getString("referral_code_amount"), LoginSelectionAcivity.this);
                        SessionSave.saveSession("Register", "", LoginSelectionAcivity.this);
                        SessionSave.saveSession("Tellfrdmsg", tellfrdMsg, LoginSelectionAcivity.this);
                        SessionSave.saveSession("About", json.getJSONObject("detail").getString("aboutpage_description"), LoginSelectionAcivity.this);
//                        SessionSave.saveSession("Currency", json.getJSONObject("detail").getString("site_currency") + " ", LoginSelectionAcivity.this);
                        SessionSave.saveSession(CREDIT_CARD, "" + json.getJSONObject("detail").getString("credit_card_status"), LoginSelectionAcivity.this);
                        if (json.getJSONObject("detail").getString("split_fare").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isSplitOn, true, LoginSelectionAcivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isSplitOn, false, LoginSelectionAcivity.this);
                        if (json.getJSONObject("detail").getString("favourite_driver").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isFavDriverOn, true, LoginSelectionAcivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isFavDriverOn, false, LoginSelectionAcivity.this);
                        if (json.getJSONObject("detail").getString("skip_favourite").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isSkipFavOn, true, LoginSelectionAcivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isSkipFavOn, false, LoginSelectionAcivity.this);


                        if (json.has(TaxiUtil.USER_KEY)) {
                            if (!json.getString(TaxiUtil.USER_KEY).equals("") && json.getString(TaxiUtil.USER_KEY) != null)
                                SessionSave.saveSession(TaxiUtil.USER_KEY, json.getString(TaxiUtil.USER_KEY), LoginSelectionAcivity.this);
                        }
                        if (json.getJSONObject("detail").has("creditcard_details"))
                            storeCardList(json.getJSONObject("detail").getJSONArray("creditcard_details"));

                        if (json.getJSONObject("detail").has(TaxiUtil.USER_WALLET_AMOUNT))
                            SessionSave.saveWalletAmount((float) json.getJSONObject("detail").getDouble(TaxiUtil.USER_WALLET_AMOUNT), LoginSelectionAcivity.this);
                        else
                            SessionSave.saveWalletAmount(0f, LoginSelectionAcivity.this);

                        Intent intent = new Intent(getApplicationContext(), MainHomeFragmentActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else if (json.getInt("status") == -10) {
                        ShowToast.center(LoginSelectionAcivity.this, json.getString("message"));

                    } else if (json.getInt("status") == 2) {
                        SessionSave.saveSession("Register", "2", LoginSelectionAcivity.this);
                        final JSONObject detail = json.getJSONObject("detail");
                        email = detail.getString("email");
                        SessionSave.saveSession("Email", email, LoginSelectionAcivity.this);
                        LoginManager.getInstance().logOut();
                        DivertToLoginScreen(email);
                    } else if (json.getInt("status") == 3) {
                        final JSONObject detail = json.getJSONObject("detail");
                        email = detail.getString("email");
                        SessionSave.saveSession("Email", email, LoginSelectionAcivity.this);
                        SessionSave.saveSession("Register", "1", LoginSelectionAcivity.this);
                        SessionSave.saveSession("IsOTPSend", "", LoginSelectionAcivity.this);
                        SessionSave.saveSession("f_name", "", LoginSelectionAcivity.this);
                        SessionSave.saveSession("l_name", "", LoginSelectionAcivity.this);
                        SessionSave.saveSession("e_mail", "", LoginSelectionAcivity.this);
                        SessionSave.saveSession("m_no", "", LoginSelectionAcivity.this);
                        SessionSave.saveSession("p_wd", "", LoginSelectionAcivity.this);
                        SessionSave.saveSession("cp_wd", "", LoginSelectionAcivity.this);
                        SessionSave.saveSession("ref_txt", "", LoginSelectionAcivity.this);

                        final Intent i = new Intent(LoginSelectionAcivity.this, RegisterActivity.class);
                        Bundle data = new Bundle();
                        data.putString("Message", json.getString("message"));
                        i.putExtras(data);
                        startActivity(i);
                    } else if (json.getInt("status") == -2) {
                        Systems.out.println("detail_v3" + json);
                        final JSONObject detail = json.getJSONObject("detail");
                        email = detail.getString("email");
                        SessionSave.saveSession("Email", email, LoginSelectionAcivity.this);
                        SessionSave.saveSession("Register", "1", LoginSelectionAcivity.this);
                        final Intent i = new Intent(LoginSelectionAcivity.this, VerificationActivity.class);
                        i.putExtra("Message", json.getString("message"));
                        startActivity(i);
                        finish();
                    } else if (json.getInt("status") == 4) {
                        final JSONObject detail = json.getJSONObject("detail");
                        email = detail.getString("email");
                        tellfrdMsg = detail.getString("telltofriend_message");
                        id = detail.getString("id");
                        photo = detail.getString("profile_image");
                        SessionSave.saveSession(PASS_NAME, detail.getString("name"), LoginSelectionAcivity.this);
                        SessionSave.saveSession("Email", email, LoginSelectionAcivity.this);
                        SessionSave.saveSession(PASS_ID, id, LoginSelectionAcivity.this);
                        SessionSave.saveSession("ProfileImage", photo, LoginSelectionAcivity.this);
                        SessionSave.saveSession("RefCode", json.getJSONObject("detail").getString("referral_code"), LoginSelectionAcivity.this);
                        SessionSave.saveSession("RefAmount", json.getJSONObject("detail").getString("referral_code_amount"), LoginSelectionAcivity.this);
                        SessionSave.saveSession("Register", "", LoginSelectionAcivity.this);
                        SessionSave.saveSession("Tellfrdmsg", tellfrdMsg, LoginSelectionAcivity.this);
                        SessionSave.saveSession("About", json.getJSONObject("detail").getString("aboutpage_description"), LoginSelectionAcivity.this);
//                        SessionSave.saveSession("Currency", json.getJSONObject("detail").getString("site_currency") + " ", LoginSelectionAcivity.this);
                        SessionSave.saveSession(CREDIT_CARD, "" + json.getJSONObject("detail").getString("credit_card_status"), LoginSelectionAcivity.this);
                        if (json.getJSONObject("detail").getString("split_fare").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isSplitOn, true, LoginSelectionAcivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isSplitOn, false, LoginSelectionAcivity.this);
                        if (json.getJSONObject("detail").getString("favourite_driver").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isFavDriverOn, true, LoginSelectionAcivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isFavDriverOn, false, LoginSelectionAcivity.this);
                        if (json.getJSONObject("detail").getString("skip_favourite").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isSkipFavOn, true, LoginSelectionAcivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isSkipFavOn, false, LoginSelectionAcivity.this);

                        if (json.has("sos_detail")) {
                            SessionSave.saveSession("contact_sos_list", json.getString("sos_detail"), LoginSelectionAcivity.this);
                        }


                        final Intent i = new Intent(LoginSelectionAcivity.this, CardRegisterAct.class);
                        i.putExtra("alert_message", json.getString("message"));
                        if (json.getJSONObject("detail").has("SKIP_CREDIT_CARD") && json.getJSONObject("detail").getString("SKIP_CREDIT_CARD").equals("1"))
                            SessionSave.saveSession("SKIP_CREDIT_CARD", true, LoginSelectionAcivity.this);
                        else
                            SessionSave.saveSession("SKIP_CREDIT_CARD", false, LoginSelectionAcivity.this);
                        startActivity(i);
                        finish();
                    } else if (json.getInt("status") == 10) {
                        getEmailId(fbuserid, fbname, "");
                    } else {
                        LoginManager.getInstance().logOut();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                DivertToLoginScreen(email);
                            }
                        }, 4000);
                        dialog = Utility.alert_view_dialog(LoginSelectionAcivity.this, "" + NC.getResources().getString(R.string.message), "" + json.getString("message"),
                                "" + NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }, "");
                    }


                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            ShowToast(LoginSelectionAcivity.this, NC.getString(R.string.server_con_error));
                        }
                    });
                }
            } catch (final Exception e) {
                e.printStackTrace();
                ShowToast(LoginSelectionAcivity.this, NC.getString(R.string.try_again));
            }
        }
    }

    /**
     * Function to parse and store credit card details of passenger in local database
     *
     * @param jsonArray - Array of credit card details
     */
    private void storeCardList(JSONArray jsonArray) {
        try {
            if (jsonArray.length() > 0 && creditCardRepository != null) {
                List<CreditCardData> cardDataList = new ArrayList<>();
                creditCardRepository.deleteAllCards();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String id = jsonArray.getJSONObject(i).getString("passenger_cardid");
                    String type = jsonArray.getJSONObject(i).getString("card_type");
                    String month = jsonArray.getJSONObject(i).getString("expdatemonth");
                    String year = jsonArray.getJSONObject(i).getString("expdateyear");
                    String card = jsonArray.getJSONObject(i).getString("masked_creditcard_no");
                    String cvv = jsonArray.getJSONObject(i).getString("masked_creditcard_cvv");
                    String original_cardno = jsonArray.getJSONObject(i).getString("creditcard_no");
                    String original_cvv = jsonArray.getJSONObject(i).getString("creditcard_cvv");
                    String default_card = jsonArray.getJSONObject(i).getString("default_card");
                    String name = jsonArray.getJSONObject(i).getString("card_holder_name");
                    cardDataList.add(new CreditCardData(name, id, type, month, year, card, cvv, default_card, original_cardno, original_cvv));
                }
                creditCardRepository.insertAllCreditCards(cardDataList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
