package com.taximobility.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.taximobility.Login.countrycode.CountryCodePicker;
import com.taximobility.MainActivity;
import com.taximobility.R;
import com.taximobility.SplashActivity;
import com.taximobility.driver.DriverUserLoginAct;
import com.taximobility.interfaces.APIResult;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.util.CL;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import static com.taximobility.util.ConstantsKt.CREDIT_CARD;
import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.PASS_NAME;

//import com.taximobility.util.DeviceUtils;
//import com.google.firebase.analytics.FirebaseAnalytics;

public class LoginActivity extends MainActivity {
    private String[] mobilenoary;
    private String fbname = "", lname = "", countrycode = "", phone;
    private CountryCodePicker ccp;
    private EditText MobileNumber;
    private Button Continue_mobile;
    private ImageView back_click;
    private boolean isFacebookLogin = false;

    @Override
    public int setLayout() {
        // TODO Auto-generated method stub
        setLocale();
        return R.layout.activity_login;
    }

    @SuppressLint("NewApi")
    @Override
    public void Initialize() {
        Colorchange.ChangeColor(findViewById(R.id.login_contain), LoginActivity.this);
        FontHelper.applyFont(this, findViewById(R.id.login_contain));
        String mDeviceid = "";
      /*  if (!UUID.randomUUID().toString().equals("")) {
            mDeviceid = UUID.randomUUID().toString();
        } else {
            mDeviceid = TaxiUtil.mDevice_id_constant;
        }*/
//        TaxiUtil.mDevice_id = Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
//        TaxiUtil.mDevice_id = mDeviceid;
        if (!TaxiUtil.mDevice_id.equals("")) {
            SessionSave.saveSession("mDevice_id", TaxiUtil.mDevice_id, LoginActivity.this);
        }
        ccp = findViewById(R.id.ccp);
        MobileNumber = findViewById(R.id.edt_mobileno);
        Continue_mobile = findViewById(R.id.continue_phone);
        back_click = findViewById(R.id.back_click);
        if (!SessionSave.getSession("country_iso_code", LoginActivity.this).equals("")) {
            ccp.setCountryForNameCode(SessionSave.getSession("country_iso_code", LoginActivity.this));
            System.out.println("country_iso_code : "+SessionSave.getSession("country_iso_code", LoginActivity.this));
            System.out.println("country_iso_code : "+ccp.getFullNumberWithPlus());

        }
        else ccp.setCountryForNameCode(SplashActivity.CURRENT_COUNTRY_ISO_CODE);

        MobileNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Continue_mobile.performClick();
                }
                return false;
            }
        });
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(MobileNumber, InputMethodManager.SHOW_IMPLICIT);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Systems.out.println("nn--isFacebookLogin" + extras.getBoolean("isfacebooklogin"));
            isFacebookLogin = extras.getBoolean("isfacebooklogin");
        }

        back_click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /**
         * @author developer
         *         <p>
         *         This section helps to login to app.
         *         </p>
         * @param string
         *
         *            mobile number, string password
         *
         *            API used:- type=passenger_login
         */
        Continue_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
//                    FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(LoginActivity.this);
                    Bundle params = new Bundle();
                    params.putString("user", phone);
//                    params.putString(FirebaseAnalytics.Param.ITEM_NAME, phone);
                    params.putString("type", "passenger");
//                    mFirebaseAnalytics.logEvent("Login_clicked_by", params);
                    Systems.out.println("analyticsLogTrigger");
                    if (isFacebookLogin) {
                        UpdateMobileNumber();
                    } else {
                        ContinueWithMobile();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ShowToast(LoginActivity.this, "Invaid format");
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(CL.getColor(this, R.color.button_accept));
        }

    }

    private void UpdateMobileNumber() {
        try {
            phone = MobileNumber.getText().toString().trim();
            if (validations(ValidateAction.isValidphone, LoginActivity.this, phone)) {
                JSONObject j = new JSONObject();
                j.put("fbemail", SessionSave.getSession("Email", LoginActivity.this));
                j.put("mobile", phone);
                j.put("country_code", ccp.getTextView_selectedCountry().getText().toString().trim());
                final String url = "type=passenger_mobile_otp";
                new LoginActivity.MobilenumberUpdate(url, j);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ContinueWithMobile() {
        try {
            phone = MobileNumber.getText().toString().trim();
            String mUUID = "";
            if (TaxiUtil.mDevice_id.equals("")) {
                if (UUID.randomUUID().toString().equals(""))
                    mUUID = TaxiUtil.mDevice_id_constant;
                else
                    mUUID = UUID.randomUUID().toString();
                TaxiUtil.mDevice_id = mUUID;
            } else {
            }
            if (!TaxiUtil.mDevice_id.equals("")) {
                SessionSave.saveSession("mDevice_id", TaxiUtil.mDevice_id, LoginActivity.this);
            }
            if (validations(ValidateAction.isValidphone, LoginActivity.this, phone)) {
                JSONObject json = new JSONObject();
                json.put("phone", phone);
                json.put("country_code", ccp.getTextView_selectedCountry().getText().toString().trim());
//                String token = FirebaseInstanceId.getInstance().getToken();
                String token = SessionSave.getSession(TaxiUtil.DEVICE_TOKEN, LoginActivity.this);
                System.out.println("DEVOCE ID __" + SessionSave.getSession("mDevice_id", LoginActivity.this) + "____" + token);
                json.put("device_id", "" + SessionSave.getSession("mDevice_id", LoginActivity.this));
                json.put("device_token", token == null || token == "" ? SessionSave.getSession("mDevice_id", LoginActivity.this) : token);
                json.put("device_type", "1");
//                json.put("device_info", new JSONObject(new Gson().toJson(DeviceUtils.INSTANCE.getAllInfo(LoginActivity.this))));
                new LoginActivity.SignIn("type=signupwith_phone", json);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            ShowToast(LoginActivity.this, "Invaid format");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
//        AppEventsLogger.activateApp(getApplication());
    }

    /**
     * <p>
     * This method used store the FB logged user details into bundle
     * </p>
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {

        super.onPause();
//        AppEventsLogger.deactivateApp(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
//        Utility.closeDialog();
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, DriverUserLoginAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    /**
     * This class used to login in app
     * <p>
     * This class used to login in app
     * </p>
     *
     * @author developer
     */
    private class SignIn implements APIResult {
        private SignIn(final String url, JSONObject j) {
            new APIService_Retrofit_JSON(LoginActivity.this, this, j, false, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession("Lang", LoginActivity.this) + "&" + url).execute();
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            try {
                Continue_mobile.setEnabled(true);
                if (isSuccess) {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        if (json.has("otp")) {
                            SessionSave.saveSession("otp_number", json.getString("otp"), LoginActivity.this);
                        } else {
                            SessionSave.saveSession("otp_number", "", LoginActivity.this);
                        }

                        if (json.has(TaxiUtil.HIDE_OTP)) {
                            SessionSave.saveSession(TaxiUtil.HIDE_OTP, json.getString(TaxiUtil.HIDE_OTP).equals("1"), LoginActivity.this);
                        } else {
                            SessionSave.saveSession(TaxiUtil.HIDE_OTP, false, LoginActivity.this);
                        }

                        if (json.has("phone_exist")) {
                            if (json.getInt("phone_exist") == 3) {
                                if (json.getJSONObject("detail").has("passenger_id")) {
                                    SessionSave.saveSession("passenger_id", json.getJSONObject("detail").getString("passenger_id"), LoginActivity.this);
                                }
                                if (json.getJSONObject("detail").has(TaxiUtil.SKIP_PASSENGER_EMAIL))
                                    SessionSave.saveSession(TaxiUtil.SKIP_PASSENGER_EMAIL, json.getJSONObject("detail").getString(TaxiUtil.SKIP_PASSENGER_EMAIL).equals("1"), LoginActivity.this);
                                else
                                    SessionSave.saveSession(TaxiUtil.SKIP_PASSENGER_EMAIL, false, LoginActivity.this);

                                final Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                                startActivity(i);
                            } else {
                                Systems.out.println("detail_v2" + "");
                                final Intent i = new Intent(LoginActivity.this, VerificationActivity.class);
                                Bundle detail_fb = new Bundle();
                                detail_fb.putString("phone_exist", json.getString("phone_exist"));
                                detail_fb.putString("phone", json.getJSONObject("detail").getString("phone"));
                                detail_fb.putString("country", json.getJSONObject("detail").getString("country_code"));
                                i.putExtras(detail_fb);
                                startActivity(i);
                            }
                        }
                    } else {
                        alert_view(LoginActivity.this, "" + NC.getResources().getString(R.string.message), "" + json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");
                        Continue_mobile.setEnabled(true);
                    }
                } else {
                    Continue_mobile.setEnabled(true);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            ShowToast(LoginActivity.this, NC.getString(R.string.server_con_error));
                        }
                    });
                }
            } catch (final Exception e) {
                // TODO Auto-generated catch block
                Continue_mobile.setEnabled(true);
                e.printStackTrace();
            }
        }
    }

    /**
     * This class used to update the mobileno to the api
     * <p>
     * This class used to update the mobileno to the api
     * </p>
     */
    private class MobilenumberUpdate implements APIResult {
        public MobilenumberUpdate(final String url, final JSONObject data) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON(LoginActivity.this, this, data, false).execute(url);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            try {
                if (isSuccess) {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        SessionSave.saveSession("Register", "", LoginActivity.this);
                        SessionSave.saveSession("Email", json.getJSONObject("detail").getString("email"), LoginActivity.this);
                        SessionSave.saveSession(PASS_ID, json.getJSONObject("detail").getString("id"), LoginActivity.this);
                        SessionSave.saveSession("ProfileImage", json.getJSONObject("detail").getString("profile_image"), LoginActivity.this);
                        SessionSave.saveSession(PASS_NAME, json.getJSONObject("detail").getString("name"), LoginActivity.this);
                        SessionSave.saveSession("Phone", json.getJSONObject("detail").getString("phone"), LoginActivity.this);
                        SessionSave.saveSession("About", json.getJSONObject("detail").getString("aboutpage_description"), LoginActivity.this);
//                        SessionSave.saveSession("Currency", json.getJSONObject("detail").getString("site_currency") + " ", LoginActivity.this);
                        SessionSave.saveSession(CREDIT_CARD, "" + json.getJSONObject("detail").getString("credit_card_status"), LoginActivity.this);
                        SessionSave.saveSession("RefCode", json.getJSONObject("detail").getString("referral_code"), LoginActivity.this);
                        SessionSave.saveSession("RefAmount", json.getJSONObject("detail").getString("referral_code_amount"), LoginActivity.this);

                        if (json.getJSONObject("detail").getString("split_fare").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isSplitOn, true, LoginActivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isSplitOn, false, LoginActivity.this);
                        SessionSave.saveSession(TaxiUtil.isFavDriverOn, true, LoginActivity.this);
                        try {
                            if (LoginActivity.this != null) {
                                showLoading(LoginActivity.this);
                                if (json.getJSONObject("detail").has("SKIP_CREDIT_CARD") && json.getJSONObject("detail").getString("SKIP_CREDIT_CARD").equals("1"))
                                    SessionSave.saveSession("SKIP_CREDIT_CARD", true, LoginActivity.this);
                                else
                                    SessionSave.saveSession("SKIP_CREDIT_CARD", false, LoginActivity.this);
                                final Intent i = new Intent(LoginActivity.this, CardRegisterAct.class);
                                i.putExtra("alert_message", json.getString("message"));
                                startActivity(i);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    ShowToast(LoginActivity.this, json.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            ShowToast(LoginActivity.this, NC.getString(R.string.server_con_error));
                        }
                    });
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
}
