package com.taximobility.driver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taximobility.Login.CardRegisterAct;
import com.taximobility.Login.LoginActivity;
import com.taximobility.Login.VerificationActivity;
import com.taximobility.Login.countrycode.CountryCodePicker;
import com.taximobility.MainActivity;
import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.driver.data.DriverCommonData;
import com.taximobility.driver.interfaces.DriverAPIResult;
import com.taximobility.driver.interfaces.DriverClickInterface;
import com.taximobility.driver.service.DriverAPIService_Retrofit_JSON;
import com.taximobility.driver.service.LocationUpdate;
import com.taximobility.driver.utils.DriverCL;
import com.taximobility.driver.utils.DriverCToast;
import com.taximobility.driver.utils.DriverFontHelper;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverNetworkStatus;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.driver.utils.DriverSystems;
import com.taximobility.driver.utils.Driver_Utils;
import com.taximobility.interfaces.APIResult;
import com.taximobility.interfaces.AlertListener;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.ShowToast;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.appcompat.app.AppCompatActivity;

import static com.taximobility.driver.data.DriverCommonData.USER_KEY;
import static com.taximobility.util.ConstantsKt.CREDIT_CARD;
import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.PASS_NAME;

//import com.taximobility.driver.utils.DeviceUtils;

/**
 * This is the main Login page
 */
public class DriverUserLoginAct extends MainActivityDriver implements DriverClickInterface, TextView.OnEditorActionListener {
    private static boolean FORCE_LOGIN = false;
    Dialog mDialog;
    private EditText PhoneEdt;
    private EditText PasswordEdt;
    private TextView ForgotTxt, signup_web, become_driver, become_pass;
    private TextView DoneBtn, hidePwd;
    private String phone;
    private String password;
    private DriverSplashAct mSplash;
    private String alert_msg;
    private Bundle alert_bundle = new Bundle();
    private Dialog mDialogs;
    private JSONObject jsonDriver;
    private boolean isReferalSuccess;
    private Dialog dialog1;
    private String Auth_key = "";

    // Set the layout to activity.
    @Override
    public int setLayout() {
        setLocale();

        return R.layout.driver_signin;
    }

    /**
     * Initialize the views on layout
     */
    @Override
    public void Initialize() {
        //  mSplash = new DriverSplashAct();
        alert_bundle = getIntent().getExtras();
        DriverSystems.out.println("values_bundle");
      /*  DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverUserLoginAct.this
                .findViewById(android.R.id.content)).getChildAt(0)), DriverUserLoginAct.this);
*/
//        SessionSave.clearSession(DriverUserLoginAct.this);
//        DriverSessionSave.clearSession(DriverUserLoginAct.this);
        if (alert_bundle != null) {
            alert_msg = alert_bundle.getString("alert_message");
        }


        if (alert_msg != null && alert_msg.length() != 0)
            Toast.makeText(DriverUserLoginAct.this,"" + alert_msg, Toast.LENGTH_LONG).show();
//            dialog1 = Driver_Utils.alert_view(DriverUserLoginAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + alert_msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverUserLoginAct.this, "");


        //   DriverFontHelper.applyFont(this, findViewById(R.id.signinlayout));
        DriverCommonData.current_act = "SplashAct";

        PhoneEdt = findViewById(R.id.phoneEdt);
        DoneBtn = findViewById(R.id.doneBtn1);

        setSpannableTextView(findViewById(R.id.t_c_web_txt));


        TextView HeadTitle = findViewById(R.id.header_titleTxt);

        LinearLayout leftIcontxt = findViewById(R.id.leftIconTxt);

        TextView leftIcon = findViewById(R.id.leftIcon);
        leftIcon.setVisibility(View.VISIBLE);
        leftIcon.setBackgroundResource(R.drawable.driver_back);
        hidePwd = findViewById(R.id.hidePwd);
        HeadTitle.setText("" + DriverNC.getResources().getString(R.string.signin));
        Glide.with(this).load(DriverSessionSave.getSession("image_path", this) + "signinlogo_driver.png").apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into((ImageView) findViewById(R.id.imageview));


        if (DriverSessionSave.getSession("image_path", this).trim().isEmpty()) {
            Glide.with(this).load(DriverSessionSave.getSession("image_path", this) + "signinlogo_driver.png").apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into((ImageView) findViewById(R.id.imageview));
        } else {
            Glide.with(this).load(R.drawable.access_logo).into((ImageView) findViewById(R.id.imageview));
        }

        PasswordEdt = findViewById(R.id.passwordEdt);
        PasswordEdt.setOnEditorActionListener(this);
        signup_web = findViewById(R.id.signup_web);

        ForgotTxt = findViewById(R.id.forgotpswdTxt);
        become_driver = findViewById(R.id.become_driver);
        become_pass = findViewById(R.id.become_pass);
        SessionSave.saveSession("base_url", SessionSave.getSession("passenger_base_url", DriverUserLoginAct.this), DriverUserLoginAct.this);
        become_driver.setOnClickListener(V -> {
            String driverSignUpUrl = DriverSessionSave.getSession("api_base", DriverUserLoginAct.this) + "become_driver_mobile.html" + "?v=1";

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(driverSignUpUrl));
            startActivity(intent);
        });
        become_pass.setOnClickListener(V -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        AtomicInteger c = new AtomicInteger(0);
        String mDeviceid = "";

        if (DriverCommonData.mDevice_id.equals("")) {
            if (!UUID.randomUUID().toString().equals("")) {
                mDeviceid = UUID.randomUUID().toString();
            } else {
                mDeviceid = DriverCommonData.mDevice_id_constant + c.incrementAndGet();
            }
            DriverCommonData.mDevice_id = mDeviceid;
        } else {

        }
     /*   String mDeviceid = "";
        if (!UUID.randomUUID().toString().equals("")) {
            mDeviceid = UUID.randomUUID().toString();
        } else {
            mDeviceid = CommonData.mDevice_id_constant;
        }*/
//        CommonData.mDevice_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
//        CommonData.mDevice_id = mDeviceid;
        /* newly add for password hide and show*/

        hidePwd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (hidePwd.getText().toString().equals(DriverNC.getResources().getString(R.string.show))) {
                    PasswordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    DriverFontHelper.applyFont(getApplicationContext(), PasswordEdt);
                    hidePwd.setText("" + DriverNC.getResources().getString(R.string.hide));

                } else {
                    PasswordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    DriverFontHelper.applyFont(getApplicationContext(), PasswordEdt);
                    hidePwd.setText("" + DriverNC.getResources().getString(R.string.show));

                }
            }
        });
        PasswordEdt.setOnFocusChangeListener((v, hasFocus) -> {
            // TODO Auto-generated method stub
            if (hasFocus) {
                hidePwd.setVisibility(View.VISIBLE);
                hidePwd.setText(DriverNC.getString(R.string.show));

            } else {
                hidePwd.setVisibility(View.GONE);
                PasswordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                DriverFontHelper.applyFont(getApplicationContext(), PasswordEdt);
            }
        });


        signup_web.setOnClickListener(v -> {
            String driverSignUpUrl = DriverSessionSave.getSession("api_base", DriverUserLoginAct.this) + "become_driver_mobile.html" + "?v=1";

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(driverSignUpUrl));
            startActivity(intent);

            /*Intent web_signup = new Intent(UserLoginAct.this, WebviewAct.class);
            web_signup.putExtra("type", "12");
            startActivityForResult(web_signup, 350);*/

        });

        /**
         * Forget password action on click
         *
         */
        ForgotTxt.setOnClickListener(new OnClickListener() {
            private Dialog mDialog;

            @Override
            public void onClick(final View v) {
//                final View view = View.inflate(DriverUserLoginAct.this, R.layout.driver_forgot_popup, null);
//                mDialog = new Dialog(DriverUserLoginAct.this, R.style.NewDialog);
//                mDialog.setContentView(view);
//                //  DirverColorchange.ChangeColor(mDialog.findViewById(R.id.inner_content), DriverUserLoginAct.this);
//                DriverFontHelper.applyFont(DriverUserLoginAct.this, mDialog.findViewById(R.id.inner_content));
//                mDialog.setCancelable(true);
//                mDialog.show();

                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(DriverUserLoginAct.this);
                View sheetView = DriverUserLoginAct.this.getLayoutInflater().inflate(R.layout.driver_forgot_popup, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();


                final EditText mail = sheetView.findViewById(R.id.forgotmail);
                mail.setInputType(InputType.TYPE_CLASS_NUMBER);
                final Button OK = sheetView.findViewById(R.id.okbtn);
                final Button Cancel = sheetView.findViewById(R.id.cancelbtn);
                OK.setOnClickListener(new OnClickListener() {
                    private String Email;

                    @Override
                    public void onClick(final View v) {
                        try {
                            Email = mail.getText().toString();
                            if (validations(ValidateAction.isValueNULL, DriverUserLoginAct.this, Email)) {
                                JSONObject j = new JSONObject();
                                j.put("phone_no", Email);
                                j.put("user_type", "D");
                                j.put("country_code", "+91");
                                SessionSave.saveSession("base_url", SessionSave.getSession("passenger_base_url", DriverUserLoginAct.this), DriverUserLoginAct.this);
                                //SessionSave.saveSession("base_url", SessionSave.getSession("driver_base_url", DriverUserLoginAct.this), DriverUserLoginAct.this);
                                final String url = "type=forgot_password";
                                new ForgotPassword(url, j);
                                mail.setText("");
                                mBottomSheetDialog.cancel();
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                });
                Cancel.setOnClickListener(v1 -> mBottomSheetDialog.cancel());
            }
        });
        /**
         * Action performed,done button onclick
         *
         */
//        DoneBtn.setOnClickListener(v -> {
//            SessionSave.saveSession("base_url", SessionSave.getSession("passenger_base_url", DriverUserLoginAct.this), DriverUserLoginAct.this);
//
//            phone = PhoneEdt.getText().toString().trim();
//            if (validations(ValidateAction.isValueNULL, DriverUserLoginAct.this, phone))
//                if (validations(ValidateAction.isValidPassword, DriverUserLoginAct.this, PasswordEdt.getText().toString().trim())) {
//                    DriverSessionSave.saveSession("phone_number", phone, DriverUserLoginAct.this);
//                    DriverSessionSave.saveSession("driver_password", PasswordEdt.getText().toString().trim(), DriverUserLoginAct.this);
//                    password = PasswordEdt.getText().toString().trim();
//                    final String url = "type=user_validate";
//                    new SignIn(url, FORCE_LOGIN);
//                }
//        });


        DoneBtn.setOnClickListener(v -> {
            phone = PhoneEdt.getText().toString().trim();
            try {

                if (validations(ValidateAction.isValueNULL, DriverUserLoginAct.this, phone)){
                    if (validations(ValidateAction.isValidPassword, DriverUserLoginAct.this, PasswordEdt.getText().toString().trim())) {
                        SessionSave.saveSession("base_url", SessionSave.getSession("driver_base_url", DriverUserLoginAct.this), DriverUserLoginAct.this);
                        final String url = "type=driver_login";
                        DriverSessionSave.saveSession(USER_KEY, "", DriverUserLoginAct.this);
                        new DriverSignIn(url, FORCE_LOGIN);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        leftIcon.setVisibility(View.INVISIBLE);
        leftIcon.setOnClickListener(view -> {
            final Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        leftIcontxt.setVisibility(View.INVISIBLE);
        leftIcontxt.setOnClickListener(view -> {
            final Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    private void setSpannableTextView(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                DriverNC.getString(R.string.terms_condition) + " ");
        spanTxt.append(DriverNC.getString(R.string.terms_condition2));
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(DriverCL.getColor(R.color.button_accept));    // you can use custom color
                ds.setUnderlineText(true);
            }

            @Override
            public void onClick(View widget) {
                String url = "&type=dynamic_page&pagename=10&device_type=1";
                new ShowWebpage(url, null, "T");
            }
        }, spanTxt.length() - DriverNC.getString(R.string.terms_condition2).length(), spanTxt.length(), 0);
        spanTxt.append(" " + DriverNC.getString(R.string.and));
        spanTxt.setSpan(new ForegroundColorSpan(DriverCL.getColor(R.color.black)), spanTxt.length() - DriverNC.getString(R.string.and).length(), spanTxt.length(), 0);
        spanTxt.append(" " + DriverNC.getString(R.string.privacy_policy));
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(DriverCL.getColor(R.color.button_accept));    // you can use custom color
                ds.setUnderlineText(true);
            }

            @Override
            public void onClick(View widget) {
                String url = "&type=dynamic_page&pagename=11&device_type=1";
                new ShowWebpage(url, null, "P");
            }
        }, spanTxt.length() - DriverNC.getString(R.string.privacy_policy).length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    public void HuaweiDeviceAlert() {

        Utility.actionSheet(DriverUserLoginAct.this, "" + String.format(DriverNC.getResources().getString(R.string.huawei_msg)), DriverNC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverUserLoginAct.this);
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
        Utility.actionSheet(DriverUserLoginAct.this, "" + String.format(DriverNC.getResources().getString(R.string.auto_start_msg)), DriverNC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverUserLoginAct.this);
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
        Utility.actionSheet(DriverUserLoginAct.this, "" + String.format(DriverNC.getResources().getString(R.string.auto_start_msg)), DriverNC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverUserLoginAct.this);
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
        Utility.actionSheet(DriverUserLoginAct.this, "" + String.format(DriverNC.getResources().getString(R.string.power_saving_msg)), DriverNC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverUserLoginAct.this);
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
                cmd += " --u ser " + getUserSerial();
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


    @Override
    protected void onResume() {
        super.onResume();

/*
        String reqString = Build.MANUFACTURER;
        if (DriverSessionSave.getSession("settings_alert", DriverUserLoginAct.this).isEmpty()) {
            if (reqString.toLowerCase().contains("huawei")) {
                HuaweiDeviceAlert();
            } else if (reqString.toLowerCase().contains("vivo")) {
                vivoDeviceAlert();
            } else if (reqString.toLowerCase().contains("xiaomi")) {
                xiaomiDeviceAlert();
            } else if (reqString.toLowerCase().contains("oppo")) {
                oppoDeviceAlert();
            }
        }

 */
    }

    @Override
    protected void onStop() {
        Driver_Utils.closeDialog(mDialog);
        Driver_Utils.closeDialog(mDialogs);
        Driver_Utils.closeDialog(alertDialog);
        super.onStop();
    }

    /**
     * Referal post method API call and response parsing.
     */
    private void referalPopup() {

        try {
//            final View view = View.inflate(VerificationActivity.this, R.layout.forgot_popupnew, null);
//            mDialog = new Dialog(VerificationActivity.this, R.style.dialogwinddow);
//            mDialog.setContentView(view);
//            mDialog.setCancelable(true);

            BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(DriverUserLoginAct.this);
            View forgetView = DriverUserLoginAct.this.getLayoutInflater().inflate(R.layout.driver_referal_popup, null);
            mBottomSheetDialog.setContentView(forgetView);
            mBottomSheetDialog.setCancelable(true);
            if (!mBottomSheetDialog.isShowing())
                mBottomSheetDialog.show();
            FontHelper.applyFont(DriverUserLoginAct.this, forgetView.findViewById(R.id.inner_content));
            Colorchange.ChangeColor(forgetView.findViewById(R.id.inner_content), DriverUserLoginAct.this);
            forgetView.findViewById(R.id.f_textview);
            final EditText mail = forgetView.findViewById(R.id.forgotmail);
            final Button OK = forgetView.findViewById(R.id.okbtn);
            final Button Cancel = forgetView.findViewById(R.id.cancelbtn);

            OK.setOnClickListener(new View.OnClickListener() {
                private String mobilenumber;

                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    try {
                        mobilenumber = mail.getText().toString();
                        if (!mobilenumber.trim().equals("")) {
                            JSONObject j = new JSONObject();
                            j.put("driver_id", DriverSessionSave.getSession("Id", DriverUserLoginAct.this));
                            j.put("referral_code", mobilenumber);
                            final String url = "type=check_driver_referral_code";
                            new ReferalCode(url, j);
                            mail.setText("");
                            mDialogs.dismiss();
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            });
            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    pop_up(jsonDriver);
                    mBottomSheetDialog.cancel();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        try {
            final View view = View.inflate(DriverUserLoginAct.this, R.layout.driver_referal_popup, null);
            mDialogs = new Dialog(DriverUserLoginAct.this, R.style.dialogwinddow);
            mDialogs.setContentView(view);
            mDialogs.setCancelable(true);
            if (!mDialogs.isShowing())
                mDialogs.show();
            DriverFontHelper.applyFont(DriverUserLoginAct.this, mDialogs.findViewById(R.id.inner_content));
            mDialogs.findViewById(R.id.f_textview);
            final EditText mail = mDialogs.findViewById(R.id.forgotmail);

            final Button OK = mDialogs.findViewById(R.id.okbtn);
            final Button Cancel = mDialogs.findViewById(R.id.cancelbtn);
            OK.setOnClickListener(new OnClickListener() {
                private String mobilenumber;

                @Override
                public void onClick(final View v) {
                    try {
                        mobilenumber = mail.getText().toString();
                        if (!mobilenumber.trim().equals("")) {
                            JSONObject j = new JSONObject();
                            j.put("driver_id", DriverSessionSave.getSession("Id", DriverUserLoginAct.this));
                            j.put("referral_code", mobilenumber);
                            final String url = "type=check_driver_referral_code";
                            new ReferalCode(url, j);
                            mail.setText("");
                            mDialogs.dismiss();
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            });
            Cancel.setOnClickListener(v -> {
                mDialogs.dismiss();
                pop_up(jsonDriver);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

         */
    }

    /**
     * Alert view for referal code
     */
    public void alert_views(AppCompatActivity m,
                            String title,
                            String message,
                            String success_txt,
                            String failure_txt) {


//changed
        Utility.actionSheet(DriverUserLoginAct.this, message, success_txt, failure_txt, false, new AlertListener() {
            @Override
            public void onSuccess() {
                if (isReferalSuccess)
                    pop_up(jsonDriver);
                else referalPopup();
            }

            @Override
            public void onFailure() {
                finish();
            }
        });
        /*
        dialog1 = Driver_Utils.alert_view_dialog(m, title, message, success_txt,
                failure_txt, true, (dialog, which) -> {

                    if (isReferalSuccess)
                        pop_up(jsonDriver);
                    else referalPopup();
                    dialog.dismiss();
                }, (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                }, "");

         */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if (dialog1 != null)
            Driver_Utils.closeDialog(dialog1);
        super.onDestroy();
    }

    /**
     * This function will redirect to other activities
     */
    public void pop_up(final JSONObject jsonDriverObject) {
        if (!DriverSessionSave.getSession("trip_id", DriverUserLoginAct.this).equals("")) {
            if (DriverSessionSave.getSession("travel_status", DriverUserLoginAct.this).equals("5")) {
                DriverSessionSave.saveSession("status", "A", DriverUserLoginAct.this);
                Intent in = new Intent(DriverUserLoginAct.this, DriverOngoingAct.class);
                startActivity(in);
                LocationUpdate.startLocationService(DriverUserLoginAct.this);
                finish();
                if (mDialog != null)
                    mDialog.dismiss();
            } else if (DriverSessionSave.getSession("travel_status", DriverUserLoginAct.this).equals("2")) {
                DriverSessionSave.saveSession("status", "A", DriverUserLoginAct.this);
                Intent in = new Intent(DriverUserLoginAct.this, DriverOngoingAct.class);
                startActivity(in);
                LocationUpdate.startLocationService(DriverUserLoginAct.this);
                finish();
                if (mDialog != null && DriverUserLoginAct.this != null)
                    mDialog.dismiss();
            } else {
                final Intent i = new Intent(DriverUserLoginAct.this, DriverMyStatus.class);
                DriverSessionSave.saveSession("need_animation", true, DriverUserLoginAct.this);
                DriverSessionSave.saveSession(DriverCommonData.SHIFT_OUT, false, DriverUserLoginAct.this);
                DriverSessionSave.saveSession(DriverCommonData.LOGOUT, false, DriverUserLoginAct.this);
                LocationUpdate.startLocationService(DriverUserLoginAct.this);
                startActivity(i);
                finish();
                overridePendingTransition(0, 0);
                if (mDialog != null)
                    mDialog.dismiss();
            }
        } else {
            final Intent i = new Intent(DriverUserLoginAct.this, DriverMyStatus.class);
            DriverSessionSave.saveSession(DriverCommonData.SHIFT_OUT, false, DriverUserLoginAct.this);
            DriverSessionSave.saveSession(DriverCommonData.LOGOUT, false, DriverUserLoginAct.this);
            DriverSessionSave.saveSession("need_animation", true, DriverUserLoginAct.this);
            LocationUpdate.startLocationService(DriverUserLoginAct.this);
            startActivity(i);
            finish();
            overridePendingTransition(0, 0);
            if (mDialog != null)
                mDialog.dismiss();
        }
    }

    public void Userselection_Dialog() {

        try {
            final View view = View.inflate(DriverUserLoginAct.this, R.layout.selection_popup, null);
            mDialog = new Dialog(DriverUserLoginAct.this);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(view);
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(true);
            RadioGroup radioGroup = mDialog.findViewById(R.id.rb_group);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (group.getCheckedRadioButtonId() == R.id.rb_pass) {
                        mDialog.dismiss();
                        try {
                            //SessionSave.saveSession("base_url", SessionSave.getSession("passenger_base_url", DriverUserLoginAct.this), DriverUserLoginAct.this);
                            JSONObject json = new JSONObject();
                            json.put("phone", phone);
                            json.put("country_code", "+91");
                            json.put("password", Uri.encode(password));
                            json.put("deviceid", "" + SessionSave.getSession("mDevice_id", DriverUserLoginAct.this));
                            String token = SessionSave.getSession(TaxiUtil.DEVICE_TOKEN, DriverUserLoginAct.this);

                            json.put("devicetoken", token == null ? SessionSave.getSession("mDevice_id", DriverUserLoginAct.this) : token);
                            json.put("devicetype", "1");

                            new Pass_SignIn("type=passenger_login", json);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else if (group.getCheckedRadioButtonId() == R.id.rb_driv) {
                        mDialog.dismiss();
                        try {
                            SessionSave.saveSession("base_url", SessionSave.getSession("driver_base_url", DriverUserLoginAct.this), DriverUserLoginAct.this);
                            final String url = "type=driver_login";
                            DriverSessionSave.saveSession(USER_KEY, "", DriverUserLoginAct.this);
                            new DriverSignIn(url, FORCE_LOGIN);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

            Window window = mDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            window.setAttributes(wlp);
            mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mDialog.show();


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


    /**
     * Already logged in dialog
     */

    public void loggedInOtherDevice(String msg) {
        try {

            Utility.actionSheetCancel(DriverUserLoginAct.this,msg, DriverNC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancel), false, new AlertListener() {
                @Override
                public void onSuccess() {
                    if (DriverNetworkStatus.isOnline(DriverUserLoginAct.this)) {
                        FORCE_LOGIN = true;
                        DriverSessionSave.saveSession("base_url", SessionSave.getSession("driver_base_url", DriverUserLoginAct.this), DriverUserLoginAct.this);


                        final String url = "type=driver_login";
                        DriverSessionSave.saveSession(USER_KEY, "", DriverUserLoginAct.this);
                        new DriverSignIn(url, FORCE_LOGIN);

                        // DriverUserLoginAct.this.DoneBtn.performClick();
                    } else {
                        DriverCToast.ShowToast(DriverUserLoginAct.this, DriverNC.getResources().getString(R.string.check_net_connection));
                    }
                }

                @Override
                public void onFailure() {

                }
            });

            /*
            dialog1 = Driver_Utils.alert_view_dialog(DriverUserLoginAct.this,
                    "" + DriverNC.getResources().getString(R.string.message),
                    "" + msg,
                    "" + DriverNC.getResources().getString(R.string.ok),
                    "" + DriverNC.getResources().getString(R.string.cancell),
                    false, (dialog, which) -> {
                        dialog.dismiss();
                        if (DriverNetworkStatus.isOnline(DriverUserLoginAct.this)) {
                            dialog.dismiss();
                            FORCE_LOGIN = true;
                            DriverSessionSave.saveSession("base_url", SessionSave.getSession("driver_base_url", DriverUserLoginAct.this), DriverUserLoginAct.this);


                            final String url = "type=driver_login";
                            DriverSessionSave.saveSession(USER_KEY, "", DriverUserLoginAct.this);
                            new DriverSignIn(url, FORCE_LOGIN);

                            // DriverUserLoginAct.this.DoneBtn.performClick();
                        } else {
                            DriverCToast.ShowToast(DriverUserLoginAct.this, DriverNC.getResources().getString(R.string.check_net_connection));
                        }
                    }, (dialog, which) -> dialog.dismiss(), "");

             */

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 350) {
                DriverSystems.out.println("actvty_from_webview");
                if (data != null) {
                    String bookdriver_msg = data.getStringExtra("bok_driver");
                    Toast.makeText(DriverUserLoginAct.this,"" + bookdriver_msg, Toast.LENGTH_LONG).show();
//                    dialog1 = Driver_Utils.alert_view_dialog(DriverUserLoginAct.this, "",
//                            "" + bookdriver_msg,
//                            "" + DriverNC.getResources().getString(R.string.ok), "",
//                            true, (dialog, which) -> dialog.dismiss(), (dialog, which) -> dialog.dismiss(), "");
                }
            }
        }
    }

    @Override
    public void positiveButtonClick(DialogInterface dialog, int id, String s) {
        dialog.dismiss();
    }

    /**
     * Function: To show the success popup dialog and move to dashboard
     */

    @Override
    public void negativeButtonClick(DialogInterface dialog, int id, String s) {
        dialog.dismiss();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
//            phone = PhoneEdt.getText().toString().trim();
//            SessionSave.saveSession("base_url", SessionSave.getSession("passenger_base_url", DriverUserLoginAct.this), DriverUserLoginAct.this);
//            if (validations(ValidateAction.isValueNULL, DriverUserLoginAct.this, phone))
//                if (validations(ValidateAction.isValidPassword, DriverUserLoginAct.this, PasswordEdt.getText().toString().trim())) {
//                    DriverSessionSave.saveSession("phone_number", phone, DriverUserLoginAct.this);
//                    DriverSessionSave.saveSession("driver_password", PasswordEdt.getText().toString().trim(), DriverUserLoginAct.this);
//                    password = PasswordEdt.getText().toString().trim();
//                    final String url = "type=user_validate";
//                    new SignIn(url, false);
            DoneBtn.performClick();
        }
        return false;
    }

    private void showAlertView(String message) {
        Toast.makeText(DriverUserLoginAct.this,"" + message, Toast.LENGTH_LONG).show();
//        dialog1 = Driver_Utils.alert_view_dialog(DriverUserLoginAct.this,
//                "",
//                "" + message,
//                "" + DriverNC.getString(R.string.ok),
//                "",
//                false, (dialog, which) -> dialog.dismiss(), null, "");
    }

    private class ShowWebpage implements DriverAPIResult {
        String type = "T";

        public ShowWebpage(final String string, JSONObject data, String type) {
            // TODO Auto-generated constructor stub
            this.type = type;
            String ss = DriverSessionSave.getSession("base_url", DriverUserLoginAct.this) + "?" + "lang=" + DriverSessionSave.getSession("Lang", DriverUserLoginAct.this) + string;
            DriverSystems.out.println("weburl____" + ss);
            new DriverAPIService_Retrofit_JSON(DriverUserLoginAct.this, this, true, ss).execute();
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            try {
                if (isSuccess) {
                    final Intent intent = new Intent(DriverUserLoginAct.this, DriverTermsAndConditions.class);
                    final Bundle bundle = new Bundle();
                    intent.putExtra("content", result);
                    if (type.equals("T"))
                        bundle.putString("name", DriverNC.getString(R.string.terms_condition2));
                    else
                        bundle.putString("name", DriverNC.getString(R.string.privacy_policy));
                    bundle.putBoolean("status", true);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    runOnUiThread(() -> ShowToast(DriverUserLoginAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    /**
     * ForgotPassword API response parsing.
     */
    private class ForgotPassword implements DriverAPIResult {
        ForgotPassword(String url, JSONObject data) {
            if (isOnline()) {
                new DriverAPIService_Retrofit_JSON(DriverUserLoginAct.this, this, data, false).execute(url);
            } else {
                Toast.makeText(DriverUserLoginAct.this,"" +  DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG).show();
//                dialog1 = Driver_Utils.alert_view(DriverUserLoginAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverUserLoginAct.this, "");
            }
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            if (isSuccess) {
                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1)
                        Toast.makeText(DriverUserLoginAct.this,"" + json.getString("message"), Toast.LENGTH_LONG).show();
//                        dialog1 = Driver_Utils.alert_view(DriverUserLoginAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + json.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverUserLoginAct.this, "");
                    else
                        Toast.makeText(DriverUserLoginAct.this,"" + json.getString("message"), Toast.LENGTH_LONG).show();
//                        dialog1 = Driver_Utils.alert_view(DriverUserLoginAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + json.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverUserLoginAct.this, "");
                } catch (final JSONException e) {
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(() -> DriverCToast.ShowToast(DriverUserLoginAct.this, DriverNC.getString(R.string.server_error)));
            }
        }
    }

    /**
     * Signin post method API call and response parsing.
     */
    private class SignIn implements APIResult {
        SignIn(final String url, boolean FORCE_LOGIN) {
            try {
                System.out.println("LOGIN  " + DriverCommonData.mDevice_id);

                JSONObject j = new JSONObject();
                j.put("phone", phone);
                j.put("password", password);
                String token = SessionSave.getSession(TaxiUtil.DEVICE_TOKEN, DriverUserLoginAct.this);
                j.put("device_id", Settings.Secure.getString(DriverUserLoginAct.this.getContentResolver(), Settings.Secure.ANDROID_ID));
                j.put("device_token", token == null || token == "" ? SessionSave.getSession("mDevice_id", DriverUserLoginAct.this) : token);

                j.put("device_type", "1");
                j.put("country_code", SessionSave.getSession("country_code", DriverUserLoginAct.this));


                j.put("force_login", FORCE_LOGIN);
                //    j.put("device_info", new JSONObject(new Gson().toJson(DeviceUtils.INSTANCE.getAllInfo(UserLoginAct.this))));
                DriverUserLoginAct.FORCE_LOGIN = false;
                DoneBtn.setEnabled(false);
                PasswordEdt.setOnEditorActionListener(null);
                new APIService_Retrofit_JSON(DriverUserLoginAct.this, this, j, false).execute(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            Runnable runnableServerError = () -> DriverCToast.ShowToast(DriverUserLoginAct.this, DriverNC.getString(R.string.server_error));
            try {
                if (isSuccess) {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1 || json.getInt("status") == 10) {
                        final JSONObject obj = json.getJSONObject("detail");
                        if (json.getString("user_type").equals("P")) {

                            SessionSave.saveSession("is_driver", "", DriverUserLoginAct.this);
                            SessionSave.saveSession("Email", json.getJSONObject("detail").getString("email"), DriverUserLoginAct.this);
                            SessionSave.saveSession(PASS_ID, json.getJSONObject("detail").getString("id"), DriverUserLoginAct.this);
                            SessionSave.saveSession("Tellfrdmsg", json.getJSONObject("detail").getString("telltofriend_message"), DriverUserLoginAct.this);
                            SessionSave.saveSession("Phone", json.getJSONObject("detail").getString("phone"), DriverUserLoginAct.this);
                            SessionSave.saveSession("ProfileImage", json.getJSONObject("detail").getString("profile_image"), DriverUserLoginAct.this);
                            SessionSave.saveSession(PASS_NAME, json.getJSONObject("detail").getString("name"), DriverUserLoginAct.this);
                            SessionSave.saveSession("About", json.getJSONObject("detail").getString("aboutpage_description"), DriverUserLoginAct.this);
//                        SessionSave.saveSession("Currency", json.getJSONObject("detail").getString("site_currency") + " ", DriverUserLoginAct.this);
                            SessionSave.saveSession("RefCode", json.getJSONObject("detail").getString("referral_code"), DriverUserLoginAct.this);
                            SessionSave.saveSession("RefAmount", json.getJSONObject("detail").getString("referral_code_amount"), DriverUserLoginAct.this);
                            SessionSave.saveSession("Register", "", DriverUserLoginAct.this);
                            SessionSave.saveSession(CREDIT_CARD, "" + json.getJSONObject("detail").getString("credit_card_status"), DriverUserLoginAct.this);
                            SessionSave.saveSession("CountyCode", json.getJSONObject("detail").getString("country_code"), DriverUserLoginAct.this);
                            if (json.getJSONObject("detail").getString("split_fare").equals("1"))
                                SessionSave.saveSession(TaxiUtil.isSplitOn, true, DriverUserLoginAct.this);
                            else
                                SessionSave.saveSession(TaxiUtil.isSplitOn, false, DriverUserLoginAct.this);
                            if (json.getJSONObject("detail").getString("favourite_driver").equals("1"))
                                SessionSave.saveSession(TaxiUtil.isFavDriverOn, true, DriverUserLoginAct.this);
                            else
                                SessionSave.saveSession(TaxiUtil.isFavDriverOn, false, DriverUserLoginAct.this);
                            if (json.getJSONObject("detail").getString("skip_favourite").equals("1"))
                                SessionSave.saveSession(TaxiUtil.isSkipFavOn, true, DriverUserLoginAct.this);
                            else
                                SessionSave.saveSession(TaxiUtil.isSkipFavOn, false, DriverUserLoginAct.this);

                            Log.e("splitfare", json.getJSONObject("detail").getString("split_fare"));

                            if (json.has("sos_detail")) {
                                SessionSave.saveSession("contact_sos_list", json.getString("sos_detail"), DriverUserLoginAct.this);
                            }

                            if (json.has(TaxiUtil.USER_KEY)) {
                                if (!json.getString(TaxiUtil.USER_KEY).equals("") && json.getString(TaxiUtil.USER_KEY) != null)
                                    SessionSave.saveSession(TaxiUtil.USER_KEY, json.getString(TaxiUtil.USER_KEY), DriverUserLoginAct.this);
                            }

                            if (json.getJSONObject("detail").has(TaxiUtil.CORPORATE_PASSENGER)) {
                                SessionSave.saveSession(TaxiUtil.CORPORATE_PASSENGER, json.getJSONObject("detail").getString(TaxiUtil.CORPORATE_PASSENGER), DriverUserLoginAct.this);
                            }

                            if (json.getJSONObject("detail").has(TaxiUtil.CORPORATE_COMPANY_ID)) {
                                SessionSave.saveSession(TaxiUtil.CORPORATE_COMPANY_ID, json.getJSONObject("detail").getString(TaxiUtil.CORPORATE_COMPANY_ID), DriverUserLoginAct.this);
                            }

                            if (json.getJSONObject("detail").has(TaxiUtil.CORPORATE_COMPANY_NAME)) {
                                SessionSave.saveSession(TaxiUtil.CORPORATE_COMPANY_NAME, json.getJSONObject("detail").getString(TaxiUtil.CORPORATE_COMPANY_NAME), DriverUserLoginAct.this);
                            }

                            if (json.getJSONObject("detail").has(TaxiUtil.CORPORATE_COMPANY_BLOCK)) {
                                SessionSave.saveSession(TaxiUtil.CORPORATE_COMPANY_BLOCK, json.getJSONObject("detail").getString(TaxiUtil.CORPORATE_COMPANY_BLOCK), DriverUserLoginAct.this);
                            }
                            if (json.getJSONObject("detail").has("creditcard_details"))
                                // storeCardList(json.getJSONObject("detail").getJSONArray("creditcard_details"));

                                if (json.getJSONObject("detail").has(TaxiUtil.USER_WALLET_AMOUNT))
                                    SessionSave.saveWalletAmount((float) json.getJSONObject("detail").getDouble(TaxiUtil.USER_WALLET_AMOUNT), DriverUserLoginAct.this);
                                else
                                    SessionSave.saveWalletAmount(0f, DriverUserLoginAct.this);

                            Intent intent = new Intent(getApplicationContext(), MainHomeFragmentActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        } else if (json.getString("user_type").equals("D")) {
                            DriverSessionSave.saveSession("base_url", SessionSave.getSession("driver_base_url", DriverUserLoginAct.this), DriverUserLoginAct.this);

                            SessionSave.saveSession("is_driver", "true", DriverUserLoginAct.this);
                            final JSONArray ary = obj.getJSONArray("driver_details");
                            final JSONObject detail = ary.getJSONObject(0);
                            DriverSessionSave.saveSession("Email", detail.getString("email"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("Id", detail.getString("userid"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("Lastname", detail.getString("lastname"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("Name", detail.getString("name"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("Phone", detail.getString("phone"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("u_name", detail.getString("name"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("Bankname", detail.getString("bankname"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("Bankaccount_No", detail.getString("bankaccount_no"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("Salutation", detail.getString("salutation"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("taxi_id", detail.getString("taxi_id"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("company_id", detail.getString("company_id"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("status", detail.getString("driver_status"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("Shiftupdate_Id", detail.getString("shiftupdate_id"), DriverUserLoginAct.this);
                            if (detail.has("driver_type")) {
                                DriverSessionSave.saveSession("account_message", json.getString("message"), DriverUserLoginAct.this);
                                DriverSessionSave.saveSession("driver_type", detail.getString("driver_type"), DriverUserLoginAct.this);
                            } else {
                                DriverSessionSave.saveSession("driver_type", "A", DriverUserLoginAct.this);
                            }
                            if (!detail.getString("shiftupdate_id").equals(""))
                                DriverSessionSave.saveSession("driver_shift", "IN", DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("Picture", detail.getString("profile_picture"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("Register", "", DriverUserLoginAct.this);
                            if (!detail.getString("trip_id").equals("")) {
                                DriverSessionSave.saveSession("trip_id", detail.getString("trip_id"), DriverUserLoginAct.this);
                                MainActivityDriver.mMyStatus.settripId(detail.getString("trip_id"));
                                DriverSessionSave.saveSession("status", detail.getString("driver_status"), DriverUserLoginAct.this);
                                DriverSessionSave.saveSession("travel_status", detail.getString("travel_status"), DriverUserLoginAct.this);
                            }

                          /*  if (json.has("user_key")) {
                                String userKey = json.getString(DriverCommonData.USER_KEY);
                                if (!TextUtils.isEmpty(userKey))
                                    DriverSessionSave.saveSession(DriverCommonData.USER_KEY, userKey, DriverUserLoginAct.this);
                            }*/

                            if (json.has("sos_detail"))
                                DriverSessionSave.saveSession("contact_sos_list", json.getString("sos_detail"), DriverUserLoginAct.this);
                            jsonDriver = detail.getJSONObject("driver_statistics");
                            DriverSessionSave.saveSession("driver_statistics", "" + jsonDriver, DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("Version_Update", "0", DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("shift_status", detail.getJSONObject("driver_statistics").getString("shift_status"), DriverUserLoginAct.this);


                            DriverSessionSave.saveSession("taxi_no", detail.getString("taxi_no"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("model_name", detail.getString("model_name"), DriverUserLoginAct.this);

                            String isFirst = detail.getString("driver_first_login");
                            if (isFirst.equals("1")) {
                                if ((Integer.parseInt(DriverSessionSave.getSession("referal", DriverUserLoginAct.this))) == 1)
                                    referalPopup();
                                else
                                    pop_up(jsonDriver);
                            } else
                                pop_up(jsonDriver);
                        }


                    } else if (json.getInt("status") == -3) {
                        if (json.has("detail")) {
                            SessionSave.saveSession("Email", json.getJSONObject("detail").getString("email"), DriverUserLoginAct.this);
                            SessionSave.saveSession(PASS_ID, json.getJSONObject("detail").getString("id"), DriverUserLoginAct.this);
                            SessionSave.saveSession("Tellfrdmsg", json.getJSONObject("detail").getString("telltofriend_message"), DriverUserLoginAct.this);
                            SessionSave.saveSession("Phone", json.getJSONObject("detail").getString("phone"), DriverUserLoginAct.this);
                            SessionSave.saveSession("ProfileImage", json.getJSONObject("detail").getString("profile_image"), DriverUserLoginAct.this);
                            SessionSave.saveSession(PASS_NAME, json.getJSONObject("detail").getString("name"), DriverUserLoginAct.this);
                            SessionSave.saveSession("About", json.getJSONObject("detail").getString("aboutpage_description"), DriverUserLoginAct.this);
//                        SessionSave.saveSession("Currency", json.getJSONObject("detail").getString("site_currency") + " ", DriverUserLoginAct.this);
                            SessionSave.saveSession("RefCode", json.getJSONObject("detail").getString("referral_code"), DriverUserLoginAct.this);
                            SessionSave.saveSession("RefAmount", json.getJSONObject("detail").getString("referral_code_amount"), DriverUserLoginAct.this);
                            SessionSave.saveSession("Register", "", DriverUserLoginAct.this);
                            SessionSave.saveSession(CREDIT_CARD, "" + json.getJSONObject("detail").getString("credit_card_status"), DriverUserLoginAct.this);
                            SessionSave.saveSession("CountyCode", json.getJSONObject("detail").getString("country_code"), DriverUserLoginAct.this);
                            if (json.getJSONObject("detail").getString("split_fare").equals("1"))
                                SessionSave.saveSession(TaxiUtil.isSplitOn, true, DriverUserLoginAct.this);
                            else
                                SessionSave.saveSession(TaxiUtil.isSplitOn, false, DriverUserLoginAct.this);
                            if (json.getJSONObject("detail").getString("favourite_driver").equals("1"))
                                SessionSave.saveSession(TaxiUtil.isFavDriverOn, true, DriverUserLoginAct.this);
                            else
                                SessionSave.saveSession(TaxiUtil.isFavDriverOn, false, DriverUserLoginAct.this);
                            if (json.getJSONObject("detail").getString("skip_favourite").equals("1"))
                                SessionSave.saveSession(TaxiUtil.isSkipFavOn, true, DriverUserLoginAct.this);
                            else
                                SessionSave.saveSession(TaxiUtil.isSkipFavOn, false, DriverUserLoginAct.this);

                            if (json.has("sos_detail")) {
                                SessionSave.saveSession("contact_sos_list", json.getString("sos_detail"), DriverUserLoginAct.this);
                            }


                            final Intent i = new Intent(DriverUserLoginAct.this, CardRegisterAct.class);
                            i.putExtra("alert_message", json.getString("message"));
                            if (json.getJSONObject("detail").has("SKIP_CREDIT_CARD") && json.getJSONObject("detail").getString("SKIP_CREDIT_CARD").equals("1"))
                                SessionSave.saveSession("SKIP_CREDIT_CARD", true, DriverUserLoginAct.this);
                            else
                                SessionSave.saveSession("SKIP_CREDIT_CARD", false, DriverUserLoginAct.this);
                            startActivity(i);
                            finish();
                        } else {
                            showAlertView(json.getString("message"));
                        }
                    } else if (json.getInt("status") == -5)
                        DriverCToast.ShowToast(DriverUserLoginAct.this, "" + json.getString("message"));
                    else if (json.getInt("status") == 0) {
                        loggedInOtherDevice(json.getString("message"));
                    } else if (json.getInt("status") == 100) {
                        Userselection_Dialog();
                    } else
                        showAlertView(json.getString("message"));

                    DoneBtn.setEnabled(true);
                } else {
                    runOnUiThread(runnableServerError);
                    DoneBtn.setEnabled(true);
                }

                PasswordEdt.setOnEditorActionListener(DriverUserLoginAct.this);

            } catch (final Exception e) {
                e.printStackTrace();
                DoneBtn.setEnabled(true);
                PasswordEdt.setOnEditorActionListener(DriverUserLoginAct.this);
                runOnUiThread(runnableServerError);
            }
        }
    }


    private class Pass_SignIn implements APIResult {
        Pass_SignIn(final String url, JSONObject j) {
            // new APIService_Retrofit_JSON(DriverUserLoginAct.this, this, j, false, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession("Lang", DriverUserLoginAct.this) + "&" + url).execute();

            new APIService_Retrofit_JSON(DriverUserLoginAct.this, this, j, false).execute(url);


        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            try {

                if (isSuccess) {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        SessionSave.saveSession("Email", json.getJSONObject("detail").getString("email"), DriverUserLoginAct.this);
                        SessionSave.saveSession(PASS_ID, json.getJSONObject("detail").getString("id"), DriverUserLoginAct.this);
                        SessionSave.saveSession("Tellfrdmsg", json.getJSONObject("detail").getString("telltofriend_message"), DriverUserLoginAct.this);
                        SessionSave.saveSession("Phone", json.getJSONObject("detail").getString("phone"), DriverUserLoginAct.this);
                        SessionSave.saveSession("ProfileImage", json.getJSONObject("detail").getString("profile_image"), DriverUserLoginAct.this);
                        SessionSave.saveSession(PASS_NAME, json.getJSONObject("detail").getString("name"), DriverUserLoginAct.this);
                        SessionSave.saveSession("About", json.getJSONObject("detail").getString("aboutpage_description"), DriverUserLoginAct.this);
//                        SessionSave.saveSession("Currency", json.getJSONObject("detail").getString("site_currency") + " ", DriverUserLoginAct.this);
                        SessionSave.saveSession("RefCode", json.getJSONObject("detail").getString("referral_code"), DriverUserLoginAct.this);
                        SessionSave.saveSession("RefAmount", json.getJSONObject("detail").getString("referral_code_amount"), DriverUserLoginAct.this);
                        SessionSave.saveSession("Register", "", DriverUserLoginAct.this);
                        SessionSave.saveSession(CREDIT_CARD, "" + json.getJSONObject("detail").getString("credit_card_status"), DriverUserLoginAct.this);
                        SessionSave.saveSession("CountyCode", json.getJSONObject("detail").getString("country_code"), DriverUserLoginAct.this);
                        if (json.getJSONObject("detail").getString("split_fare").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isSplitOn, true, DriverUserLoginAct.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isSplitOn, false, DriverUserLoginAct.this);
                        if (json.getJSONObject("detail").getString("favourite_driver").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isFavDriverOn, true, DriverUserLoginAct.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isFavDriverOn, false, DriverUserLoginAct.this);
                        if (json.getJSONObject("detail").getString("skip_favourite").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isSkipFavOn, true, DriverUserLoginAct.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isSkipFavOn, false, DriverUserLoginAct.this);

                        Log.e("splitfare", json.getJSONObject("detail").getString("split_fare"));

                        if (json.has("sos_detail")) {
                            SessionSave.saveSession("contact_sos_list", json.getString("sos_detail"), DriverUserLoginAct.this);
                        }

                        if (json.has(TaxiUtil.USER_KEY)) {
                            if (!json.getString(TaxiUtil.USER_KEY).equals("") && json.getString(TaxiUtil.USER_KEY) != null)
                                SessionSave.saveSession(TaxiUtil.USER_KEY, json.getString(TaxiUtil.USER_KEY), DriverUserLoginAct.this);
                        }

                        if (json.getJSONObject("detail").has(TaxiUtil.CORPORATE_PASSENGER)) {
                            SessionSave.saveSession(TaxiUtil.CORPORATE_PASSENGER, json.getJSONObject("detail").getString(TaxiUtil.CORPORATE_PASSENGER), DriverUserLoginAct.this);
                        }

                        if (json.getJSONObject("detail").has(TaxiUtil.CORPORATE_COMPANY_ID)) {
                            SessionSave.saveSession(TaxiUtil.CORPORATE_COMPANY_ID, json.getJSONObject("detail").getString(TaxiUtil.CORPORATE_COMPANY_ID), DriverUserLoginAct.this);
                        }

                        if (json.getJSONObject("detail").has(TaxiUtil.CORPORATE_COMPANY_NAME)) {
                            SessionSave.saveSession(TaxiUtil.CORPORATE_COMPANY_NAME, json.getJSONObject("detail").getString(TaxiUtil.CORPORATE_COMPANY_NAME), DriverUserLoginAct.this);
                        }

                        if (json.getJSONObject("detail").has(TaxiUtil.CORPORATE_COMPANY_BLOCK)) {
                            SessionSave.saveSession(TaxiUtil.CORPORATE_COMPANY_BLOCK, json.getJSONObject("detail").getString(TaxiUtil.CORPORATE_COMPANY_BLOCK), DriverUserLoginAct.this);
                        }
                        if (json.getJSONObject("detail").has("creditcard_details"))
                            // storeCardList(json.getJSONObject("detail").getJSONArray("creditcard_details"));

                            if (json.getJSONObject("detail").has(TaxiUtil.USER_WALLET_AMOUNT))
                                SessionSave.saveWalletAmount((float) json.getJSONObject("detail").getDouble(TaxiUtil.USER_WALLET_AMOUNT), DriverUserLoginAct.this);
                            else
                                SessionSave.saveWalletAmount(0f, DriverUserLoginAct.this);

                        Intent intent = new Intent(getApplicationContext(), MainHomeFragmentActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else if (json.getInt("status") == -3) {


                        SessionSave.saveSession("Email", json.getJSONObject("detail").getString("email"), DriverUserLoginAct.this);
                        SessionSave.saveSession(PASS_ID, json.getJSONObject("detail").getString("id"), DriverUserLoginAct.this);
                        SessionSave.saveSession("Tellfrdmsg", json.getJSONObject("detail").getString("telltofriend_message"), DriverUserLoginAct.this);
                        SessionSave.saveSession("Phone", json.getJSONObject("detail").getString("phone"), DriverUserLoginAct.this);
                        SessionSave.saveSession("ProfileImage", json.getJSONObject("detail").getString("profile_image"), DriverUserLoginAct.this);
                        SessionSave.saveSession(PASS_NAME, json.getJSONObject("detail").getString("name"), DriverUserLoginAct.this);
                        SessionSave.saveSession("About", json.getJSONObject("detail").getString("aboutpage_description"), DriverUserLoginAct.this);
//                        SessionSave.saveSession("Currency", json.getJSONObject("detail").getString("site_currency") + " ", DriverUserLoginAct.this);
                        SessionSave.saveSession("RefCode", json.getJSONObject("detail").getString("referral_code"), DriverUserLoginAct.this);
                        SessionSave.saveSession("RefAmount", json.getJSONObject("detail").getString("referral_code_amount"), DriverUserLoginAct.this);
                        SessionSave.saveSession("Register", "", DriverUserLoginAct.this);
                        SessionSave.saveSession(CREDIT_CARD, "" + json.getJSONObject("detail").getString("credit_card_status"), DriverUserLoginAct.this);
                        SessionSave.saveSession("CountyCode", json.getJSONObject("detail").getString("country_code"), DriverUserLoginAct.this);
                        if (json.getJSONObject("detail").getString("split_fare").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isSplitOn, true, DriverUserLoginAct.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isSplitOn, false, DriverUserLoginAct.this);
                        if (json.getJSONObject("detail").getString("favourite_driver").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isFavDriverOn, true, DriverUserLoginAct.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isFavDriverOn, false, DriverUserLoginAct.this);
                        if (json.getJSONObject("detail").getString("skip_favourite").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isSkipFavOn, true, DriverUserLoginAct.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isSkipFavOn, false, DriverUserLoginAct.this);

                        if (json.has("sos_detail")) {
                            SessionSave.saveSession("contact_sos_list", json.getString("sos_detail"), DriverUserLoginAct.this);
                        }


                        final Intent i = new Intent(DriverUserLoginAct.this, CardRegisterAct.class);
                        i.putExtra("alert_message", json.getString("message"));
                        if (json.getJSONObject("detail").has("SKIP_CREDIT_CARD") && json.getJSONObject("detail").getString("SKIP_CREDIT_CARD").equals("1"))
                            SessionSave.saveSession("SKIP_CREDIT_CARD", true, DriverUserLoginAct.this);
                        else
                            SessionSave.saveSession("SKIP_CREDIT_CARD", false, DriverUserLoginAct.this);
                        startActivity(i);
                        finish();
                    } else if (json.getInt("status") == -10) {
                        ShowToast.center(DriverUserLoginAct.this, json.getString("message"));

                    } else if (json.getInt("status") == -2) {

                        SessionSave.saveSession("Email", json.getJSONObject("detail").getString("email"), DriverUserLoginAct.this);
                        SessionSave.saveSession("Phone", json.getJSONObject("detail").getString("phone"), DriverUserLoginAct.this);
                        SessionSave.saveSession("Register", "1", DriverUserLoginAct.this);
                        SessionSave.saveSession("m_no", phone, DriverUserLoginAct.this);
                        final Intent i = new Intent(DriverUserLoginAct.this, DriverUserLoginAct.class);
                        Bundle detail_fb = new Bundle();

                        if (json.has("phone_exist"))
                            detail_fb.putString("phone_exist", json.getString("phone_exist"));
                        else detail_fb.putString("phone_exist", "0");

                        detail_fb.putString("phone", phone);
                        detail_fb.putString("country", "+91");
                        i.putExtras(detail_fb);
                        startActivity(i);
                        finish();
                    } else if (json.getInt("status") == -5) {
                        Toast.makeText(DriverUserLoginAct.this,"" + json.getString("message"), Toast.LENGTH_LONG).show();
//                        dialog1 = Utility.alert_view_dialog(DriverUserLoginAct.this, "" + NC.getResources().getString(R.string.message), "" + json.getString("message"), NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }, "");
                    } else if (json.getInt("status") == 4) {
                        Toast.makeText(DriverUserLoginAct.this,"" + json.getString("message"), Toast.LENGTH_LONG).show();
//                        dialog1 = Utility.alert_view_dialog(DriverUserLoginAct.this, "" + NC.getResources().getString(R.string.message), "" + json.getString("message"), NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }, "");
                    } else {
                        Toast.makeText(DriverUserLoginAct.this,"" + json.getString("message"), Toast.LENGTH_LONG).show();
//                        dialog1 = Utility.alert_view_dialog(DriverUserLoginAct.this, "" + NC.getResources().getString(R.string.message), "" + json.getString("message"), NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }, "");
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            ShowToast(DriverUserLoginAct.this, NC.getString(R.string.server_con_error));
                        }
                    });
                }
            } catch (final Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Referal  method API call and response parsing.
     */
    private class ReferalCode implements DriverAPIResult {
        ReferalCode(String url, JSONObject data) {
            if (isOnline()) {
                new DriverAPIService_Retrofit_JSON(DriverUserLoginAct.this, this, data, false).execute(url);
            } else {
                Toast.makeText(DriverUserLoginAct.this,"" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG).show();
//                dialog1 = Driver_Utils.alert_view(DriverUserLoginAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverUserLoginAct.this, "");
            }
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            if (isSuccess) {
                try {
                    final JSONObject json = new JSONObject(result);

                    if (json.getInt("status") == 1) {
                        isReferalSuccess = true;
                        alert_views(DriverUserLoginAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + json.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "");
                    } else {
                        isReferalSuccess = false;
                        alert_views(DriverUserLoginAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + json.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "");
                    }
                } catch (final JSONException e) {
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(() -> DriverCToast.ShowToast(DriverUserLoginAct.this, DriverNC.getString(R.string.server_error)));
            }
        }
    }


    private class DriverSignIn implements DriverAPIResult {
        DriverSignIn(final String url, boolean FORCE_LOGIN) {
            try {
                System.out.println("LOGIN  " + DriverCommonData.mDevice_id);
                String new_password = convertPassMd5(PasswordEdt.getText().toString().trim());

                JSONObject j = new JSONObject();
                j.put("phone", phone);
                j.put("password", new_password);
                String token = SessionSave.getSession(TaxiUtil.DEVICE_TOKEN, DriverUserLoginAct.this);
                j.put("device_id", Settings.Secure.getString(DriverUserLoginAct.this.getContentResolver(), Settings.Secure.ANDROID_ID));
                j.put("device_token", token == null || token == "" ? SessionSave.getSession("mDevice_id", DriverUserLoginAct.this) : token);

                j.put("device_type", "1");
                j.put("country_code", "+91");
              /*  Auth_key = SessionSave.getSession(TaxiUtil.AUTH_KEY, DriverUserLoginAct.this);
                SessionSave.saveSession(TaxiUtil.AUTH_KEY, "", DriverUserLoginAct.this);*/

                j.put("force_login", FORCE_LOGIN);
                //    j.put("device_info", new JSONObject(new Gson().toJson(DeviceUtils.INSTANCE.getAllInfo(UserLoginAct.this))));
                // DriverUserLoginAct.FORCE_LOGIN = false;
                DoneBtn.setEnabled(false);
                PasswordEdt.setOnEditorActionListener(null);
                new DriverAPIService_Retrofit_JSON(DriverUserLoginAct.this, this, j, false).execute(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            Runnable runnableServerError = () -> DriverCToast.ShowToast(DriverUserLoginAct.this, DriverNC.getString(R.string.server_error));
            try {
                if (isSuccess) {

                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1 || json.getInt("status") == 10) {
                        SessionSave.saveSession("is_driver", "true", DriverUserLoginAct.this);
                        final JSONObject obj = json.getJSONObject("detail");
                        final JSONArray ary = obj.getJSONArray("driver_details");
                        final JSONObject detail = ary.getJSONObject(0);
                        DriverSessionSave.saveSession("Email", detail.getString("email"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("Id", detail.getString("userid"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("Lastname", detail.getString("lastname"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("Name", detail.getString("name"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("Phone", detail.getString("phone"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("u_name", detail.getString("name"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("Bankname", detail.getString("bankname"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("Bankaccount_No", detail.getString("bankaccount_no"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("Salutation", detail.getString("salutation"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("taxi_id", detail.getString("taxi_id"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("company_id", detail.getString("company_id"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("status", detail.getString("driver_status"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("Shiftupdate_Id", detail.getString("shiftupdate_id"), DriverUserLoginAct.this);
                        if (detail.has("driver_type")) {
                            DriverSessionSave.saveSession("account_message", json.getString("message"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("driver_type", detail.getString("driver_type"), DriverUserLoginAct.this);
                        } else {
                            DriverSessionSave.saveSession("driver_type", "A", DriverUserLoginAct.this);
                        }
                        if (!detail.getString("shiftupdate_id").equals(""))
                            DriverSessionSave.saveSession("driver_shift", "IN", DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("Picture", detail.getString("profile_picture"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("Register", "", DriverUserLoginAct.this);
                        if (!detail.getString("trip_id").equals("")) {
                            DriverSessionSave.saveSession("trip_id", detail.getString("trip_id"), DriverUserLoginAct.this);
                            MainActivityDriver.mMyStatus.settripId(detail.getString("trip_id"));
                            DriverSessionSave.saveSession("status", detail.getString("driver_status"), DriverUserLoginAct.this);
                            DriverSessionSave.saveSession("travel_status", detail.getString("travel_status"), DriverUserLoginAct.this);
                        }

                        if (json.has("user_key")) {
                            String userKey = json.getString(DriverCommonData.USER_KEY);
                            if (!TextUtils.isEmpty(userKey))
                                DriverSessionSave.saveSession(DriverCommonData.USER_KEY, userKey, DriverUserLoginAct.this);
                        }

                        if (json.has("sos_detail"))
                            DriverSessionSave.saveSession("contact_sos_list", json.getString("sos_detail"), DriverUserLoginAct.this);
                        jsonDriver = detail.getJSONObject("driver_statistics");
                        DriverSessionSave.saveSession("driver_statistics", "" + jsonDriver, DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("Version_Update", "0", DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("shift_status", detail.getJSONObject("driver_statistics").getString("shift_status"), DriverUserLoginAct.this);


                        DriverSessionSave.saveSession("taxi_no", detail.getString("taxi_no"), DriverUserLoginAct.this);
                        DriverSessionSave.saveSession("model_name", detail.getString("model_name"), DriverUserLoginAct.this);

                        String isFirst = detail.getString("driver_first_login");
                        if (isFirst.equals("1")) {
                            if ((Integer.parseInt(DriverSessionSave.getSession("referal", DriverUserLoginAct.this))) == 1)
                                referalPopup();
                            else
                                pop_up(jsonDriver);
                        } else
                            pop_up(jsonDriver);

                    } else if (json.getInt("status") == -5)
                        DriverCToast.ShowToast(DriverUserLoginAct.this, "" + json.getString("message"));
                    else if (json.getInt("status") == 0) {
                        loggedInOtherDevice(json.getString("message"));
                    } else if (json.getInt("status") == 100) {
                        Userselection_Dialog();
                    } else
                        showAlertView(json.getString("message"));

                    DoneBtn.setEnabled(true);
                } else {
                    runOnUiThread(runnableServerError);
                    DoneBtn.setEnabled(true);
                }

                PasswordEdt.setOnEditorActionListener(DriverUserLoginAct.this);

            } catch (final Exception e) {
                e.printStackTrace();
                DoneBtn.setEnabled(true);
                PasswordEdt.setOnEditorActionListener(DriverUserLoginAct.this);
                runOnUiThread(runnableServerError);
            }
        }
    }
}