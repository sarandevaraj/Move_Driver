package com.movedriver.driver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mayan.sospluginmodlue.SOSActivity;
import com.movedriver.ProfileImageSetupClass;
import com.squareup.picasso.Picasso;
import com.movedriver.R;
import com.movedriver.driver.data.DriverCommonData;
import com.movedriver.driver.interfaces.DriverAPIResult;
import com.movedriver.driver.interfaces.DriverClickInterface;
import com.movedriver.driver.service.DriverAPIService_Retrofit_JSON;
import com.movedriver.driver.service.LocationUpdate;
import com.movedriver.driver.utils.DriverCL;
import com.movedriver.driver.utils.DriverCToast;
import com.movedriver.driver.utils.DirverColorchange;
import com.movedriver.driver.utils.DriverFontHelper;
import com.movedriver.driver.utils.DriverImageUtils;
import com.movedriver.driver.utils.DriverNC;
import com.movedriver.driver.utils.DriverSessionSave;
import com.movedriver.driver.utils.DriverSystems;
import com.movedriver.driver.utils.Driver_Utils;
import com.movedriver.driver.interfaces.AlertListener;
import com.movedriver.util.Utility;
import com.yalantis.ucrop.UCrop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static com.movedriver.driver.data.DriverCommonData.getDateForCreateImageFile;

/**
 * This class is used to show driver profile info
 */
public class DriverMeAct extends MainActivityDriver implements OnClickListener, DriverClickInterface {
    private static final int FROM_GALLERY = 108;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 113;
    private static final int ACTIVITY_VIEW_ATTACHMENT = 115;
    private static final int ACTION_IMAGE_CAPTURE = 113;
    public static AppCompatActivity mFlagger;
    public static DriverMeAct profileAct;
    public ImageView fileimg;
    public Dialog tDialog;
    Dialog fileDialog;
    int imageSelect = 0, imageSelected = 0;
    // Class members declarations.
    private TextView hidePwd, hideconPwd, tvHelpLink;
    private Button DoneBtn;
    private TextView HeadTitle;
    private TextView forgotpswdTxt;
    private EditText emailEdt;
    private EditText mobileEdt;
    private EditText passwordEdt;
    private EditText confirmpswdEdt;
    private EditText firstTxt;
    private EditText lastTxt;
    private String phone;
    private String newpwd;
    private String confirmpwd;
    private String bankname;
    private String bankaccNo;
    private String taxi_model = "";
    private String taxi_no = "";
    private String taxi_map_from = "";
    private String taxi_map_to = "";
    private int driver_rating;
    private ImageView profileImage;
    private String base64 = "", file_base64 = "";
    private ImageView slider, btn_back, logout_img;
    private TextView btnLogout, btnupload;
    //private ImageLoader imageLoader;
    private Bitmap mBitmap;
    private Uri imageUri;
    private Bitmap downImage;
    private TextView btntaxidetail;
    private TextView btn_emergency_add;
    private RelativeLayout me_layout;
    private TextView emergency_contact;
    private TextView driverRat;
    private TextView emergency_contact_txt;
    private int walletamountr = 0;
    private Dialog mcancelDialog;
    private Dialog mDialog;
    private ScrollView profile_lay_s;

    private Dialog mlangDialog;
    private int types = 1;
    private String encodedImage = "";
    private String destinationFileName = "profileImage";
    private Button invitefriends_bottom, subscription;

    private TextView bt_delete_acc;
    private MaterialTapTargetPrompt mTargetPrompt;
    private FrameLayout layout_loading;
    private LinearLayout layout_bottom;

    private Dialog dialog1;

    private TextView txt_mobile_settings;

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 1;
        else return k;
    }

    /**
     * Set the layout to activity.
     */
    @Override
    public int setLayout() {

        setLocale();
        return R.layout.driver_me_lay2;
    }

    /**
     * Initialize the views on layout
     */
    @Override
    public void Initialize() {

        DriverCommonData.mActivitylist.add(this);
        DriverCommonData.sContext = this;
        DriverCommonData.current_act = "MeAct";

        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverMeAct.this
                .findViewById(android.R.id.content)).getChildAt(0)), DriverMeAct.this);

        DriverFontHelper.applyFont(this, findViewById(R.id.me_layout));
        profileAct = this;
        me_layout = findViewById(R.id.me_layout);
        profile_lay_s = findViewById(R.id.profile_lay_s);

        layout_loading = findViewById(R.id.layout_loading);
        layout_bottom = findViewById(R.id.bottom);
        //  tvHelpLink = findViewById(R.id.tvHelpLink);

        slider = findViewById(R.id.slideImg);
        DoneBtn = findViewById(R.id.donebtn);
        HeadTitle = findViewById(R.id.headerTxt);
        firstTxt = findViewById(R.id.firstTxt);
        lastTxt = findViewById(R.id.lastTxt);
        emailEdt = findViewById(R.id.emailEdt);
        emailEdt.setEnabled(false);
        mobileEdt = findViewById(R.id.mobileEdt);
        mobileEdt.setEnabled(false);
        btnupload = findViewById(R.id.btnupload);
        passwordEdt = findViewById(R.id.passwordEdt);
        confirmpswdEdt = findViewById(R.id.confirmpswdEdt);
        btn_back = findViewById(R.id.slideImg);
        logout_img = findViewById(R.id.logout_img);
        btn_back.setVisibility(View.VISIBLE);
        bt_delete_acc = findViewById(R.id.bt_privacysettings);
        //  ((TextView) findViewById(R.id.language_setting)).setText(Html.fromHtml("<p><u>" + (DriverNC.getString(R.string.select_language).trim()) + "<p><u>"));
        //bankEdt = (EditText) findViewById(R.id.bankEdt);
        // bankaccnoEdt = (EditText) findViewById(R.id.bankaccnoEdt);
        profileImage = findViewById(R.id.profile_image);
        btntaxidetail = findViewById(R.id.btntaxidetail);
        btn_emergency_add = findViewById(R.id.btnadd_emergency);
        btntaxidetail.setText(DriverNC.getString(R.string.taxi_detailsu).trim());
        driverRat = findViewById(R.id.driverRat);
        HeadTitle.setText(DriverNC.getResources().getString(R.string.m_me));
        DoneBtn.setVisibility(View.VISIBLE);
        DoneBtn.setText(DriverNC.getResources().getString(R.string.save));
        txt_mobile_settings = findViewById(R.id.txt_mobile_settings);
        btnLogout = findViewById(R.id.btnlogout);
        btnLogout.setOnClickListener(this);
        logout_img.setOnClickListener(this);
        final View view = View.inflate(DriverMeAct.this, R.layout.driver_fileupload_popup, null);
        fileDialog = new Dialog(DriverMeAct.this, R.style.dialogwinddow);
        fileDialog.setContentView(view);
        hidePwd = findViewById(R.id.hidePwd);
        hideconPwd = findViewById(R.id.hideconPwd);
        emergency_contact_txt = findViewById(R.id.emergency_contact_txt);
        emergency_contact = findViewById(R.id.emergency_contact_txt);

        subscription = findViewById(R.id.subscription);
        invitefriends_bottom = findViewById(R.id.invitefriends_bottom);

    /*    if (SessionSave.getSession(CommonData.SOS_ENABLED, this, false)) {
            emergency_contact.setVisibility(View.VISIBLE);
        }*/
        String reqString = Build.MANUFACTURER;
        /*
        if (reqString.toLowerCase().contains("huawei") || reqString.toLowerCase().contains("vivo") || reqString.toLowerCase().contains("xiaomi") || reqString.toLowerCase().contains("oppo")) {
            txt_mobile_settings.setVisibility(View.VISIBLE);
        } else {
            txt_mobile_settings.setVisibility(View.GONE);
        }
        txt_mobile_settings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String reqString = Build.MANUFACTURER;
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
        });

         */


        if (!DriverSessionSave.getSession(DriverCommonData.HELP_URL, this).equals("")) {
            //  if (emergency_contact.getVisibility() == View.GONE){}
            // tvHelpLink.setGravity(Gravity.CENTER);
            //tvHelpLink.setVisibility(View.VISIBLE);
        }
//        tvHelpLink.setOnClickListener(v -> {
//            tvHelpLink.setClickable(false);
//            Intent in = new Intent(DriverMeAct.this, DriverWebviewAct.class);
//            in.putExtra("type", DriverCommonData.HELP_URL);
//            startActivity(in);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    tvHelpLink.setClickable(true);
//                }
//            }, 2000L);
//        });
        bt_delete_acc.setOnClickListener(view1 -> {
            Intent deleteAcc = new Intent(DriverMeAct.this, DeleteAccountActivityDriver.class);
            startActivity(deleteAcc);
        });


        emergency_contact_txt.setOnClickListener(view12 -> {

            DriverSessionSave.saveSession("sos_id", DriverSessionSave.getSession("Id", DriverMeAct.this), DriverMeAct.this);
            DriverSessionSave.saveSession("user_type", "d", DriverMeAct.this);

            //               startActivity(new Intent(DriverMeAct.this, SOSActivity.class));
        });

        btn_emergency_add.setOnClickListener(v -> StartSOSActivity());
        //Getting Driver Profile
        JSONObject j = new JSONObject();
        try {
            j.put("userid", DriverSessionSave.getSession("Id", DriverMeAct.this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        showLoading();
        String pro_url = "type=driver_profile";
        new GetProfileData(pro_url, j);

        invitefriends_bottom.setOnClickListener(v -> startActivity(new Intent(DriverMeAct.this, DriverInviteFriendAct.class)));
        subscription.setOnClickListener(v -> {
            Intent in = new Intent(DriverMeAct.this, DriverWebviewAct.class);

            in.putExtra("type", DriverSessionSave.getSession(DriverCommonData.PLAN_TYPE, DriverMeAct.this).equals("1") ? "1" : "2");
            startActivity(in);
        });


        //
        lastTxt.setOnEditorActionListener((v, actionId, event) -> {

            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                passwordEdt.requestFocus();
            }
            return true;
        });

        DriverFontHelper.applyFont(DriverMeAct.this, fileDialog.findViewById(R.id.topid_fileup));
        fileDialog.setCancelable(true);


        setOnclicklistener();

        /* newly add for password hide and show*/

        hidePwd.setOnClickListener(v -> {
            // TODO Auto-generated method stub

            if (hidePwd.getText().toString().equals(DriverNC.getResources().getString(R.string.show))) {
                passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                DriverFontHelper.applyFont(DriverMeAct.this, passwordEdt);
                hidePwd.setText("" + DriverNC.getResources().getString(R.string.hide));

            } else {
                passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                hidePwd.setText("" + DriverNC.getResources().getString(R.string.show));
                DriverFontHelper.applyFont(DriverMeAct.this, passwordEdt);

            }
        });
        passwordEdt.setOnFocusChangeListener((v, hasFocus) -> {
            // TODO Auto-generated method stub
            if (hasFocus) {
                hidePwd.setVisibility(View.VISIBLE);


            } else {
                hidePwd.setVisibility(View.GONE);
                passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                DriverFontHelper.applyFont(DriverMeAct.this, passwordEdt);
                if (hidePwd.getText().toString().equals(DriverNC.getResources().getString(R.string.hide))) {
                    DriverFontHelper.applyFont(DriverMeAct.this, passwordEdt);
                    hidePwd.setText("" + DriverNC.getResources().getString(R.string.show));
                }
            }
        });

        hideconPwd.setOnClickListener(v -> {

            if (hideconPwd.getText().toString().equals(DriverNC.getResources().getString(R.string.show))) {
                confirmpswdEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                hideconPwd.setText("" + DriverNC.getResources().getString(R.string.hide));
                DriverFontHelper.applyFont(DriverMeAct.this, confirmpswdEdt);
            } else {
                confirmpswdEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                hideconPwd.setText("" + DriverNC.getResources().getString(R.string.show));
                DriverFontHelper.applyFont(DriverMeAct.this, confirmpswdEdt);

            }
        });
        confirmpswdEdt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                hideconPwd.setVisibility(View.VISIBLE);


            } else {
                hideconPwd.setVisibility(View.GONE);
                confirmpswdEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                DriverFontHelper.applyFont(DriverMeAct.this, confirmpswdEdt);
                if (hideconPwd.getText().toString().equals(DriverNC.getResources().getString(R.string.hide))) {
                    hideconPwd.setText("" + DriverNC.getResources().getString(R.string.show));
                }
            }
        });

        btn_back.setOnClickListener(v -> {

            Intent in = new Intent(DriverMeAct.this, DriverMyStatus.class);
            startActivity(in);
            finish();


        });


        if (DriverSessionSave.getAPI(DriverMeAct.this).length() > 0) {
            JSONArray jsonArray = DriverSessionSave.getAPI(DriverMeAct.this);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Log.e("log", jsonArray.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } else {
            Log.e("log", "0");
        }

    }

    private void StartSOSActivity() {
        DriverSessionSave.saveSession("sos_id", DriverSessionSave.getSession("Id", DriverMeAct.this), DriverMeAct.this);
        DriverSessionSave.saveSession("user_type", "d", DriverMeAct.this);
        startActivity(new Intent(DriverMeAct.this, SOSActivity.class));
    }

    public void HuaweiDeviceAlert() {

        Utility.actionSheet(DriverMeAct.this, "" + String.format(DriverNC.getResources().getString(R.string.huawei_msg)), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverMeAct.this);
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
        Utility.actionSheet(DriverMeAct.this, "" + String.format(DriverNC.getResources().getString(R.string.auto_start_msg)), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverMeAct.this);
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
        Utility.actionSheet(DriverMeAct.this, "" + String.format(DriverNC.getResources().getString(R.string.auto_start_msg)), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverMeAct.this);
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
        Utility.actionSheet(DriverMeAct.this, "" + String.format(DriverNC.getResources().getString(R.string.power_saving_msg)), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                DriverSessionSave.saveSession("settings_alert", "SETTINGS", DriverMeAct.this);
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
            String cmd;
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
     * Click method used to change the language and update the UI based on selected language.
     */
    public void language_settings(View v) {

        final View view = View.inflate(DriverMeAct.this, R.layout.driver_lang_list, null);
        mlangDialog = new Dialog(DriverMeAct.this, R.style.dialogwinddow);
        mlangDialog.setContentView(view);
        DriverFontHelper.applyFont(DriverMeAct.this, mlangDialog.findViewById(R.id.id_lang));
        DirverColorchange.ChangeColor(mlangDialog.findViewById(R.id.id_lang), DriverMeAct.this);
        mlangDialog.setCancelable(true);
        mlangDialog.show();
        String[] totalLang = (DriverSessionSave.getSession("lang_json", DriverMeAct.this)).trim().split("____");

        final LinearLayout lay_fav_res1 = mlangDialog.findViewById(R.id.language_list);
        for (int i = 0; i < totalLang.length; i++) {
            // lay_fav_res1.
            TextView tv = new TextView(DriverMeAct.this);
            tv.setText(DriverSessionSave.getSession("LANG" + i, DriverMeAct.this).replaceAll(".xml", ""));
            tv.setTag(i);
            tv.setPadding(15, 15, 15, 15);
            if (DriverSessionSave.getSession("Lang", DriverMeAct.this).equals("ar") || DriverSessionSave.getSession("Lang", DriverMeAct.this).equals("fa"))
                tv.setGravity(Gravity.RIGHT);
            tv.setOnClickListener(v1 -> {
                int pos = (int) v1.getTag();
                types = pos;
                String url = DriverSessionSave.getSession(DriverSessionSave.getSession("LANG" + pos, DriverMeAct.this), DriverMeAct.this);
                DriverSystems.out.println("current_url" + url);
                DriverSessionSave.saveSession("currentStringUrl", url, DriverMeAct.this);
                // if (!DriverSessionSave.getSession("Lang", DriverMeAct.this).equalsIgnoreCase(DriverSessionSave.getSession("LANGCode" + pos, DriverMeAct.this)))
                //new callString("strings.xml");

            });
            lay_fav_res1.addView(tv);
        }


    }

    @Override
    public void positiveButtonClick(DialogInterface dialog, int id, String s) {
        dialog.dismiss();
    }

    @Override
    public void negativeButtonClick(DialogInterface dialog, int id, String s) {

    }

    /**
     * Storing string files in local hash map
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
     * Getting string files in local hash map
     */
    synchronized void getValueDetail() {
        Field[] fieldss = R.string.class.getDeclaredFields();
        for (Field field : fieldss) {
            int id = getResources().getIdentifier(field.getName(), "string", DriverMeAct.this.getPackageName());
            if (DriverNC.nfields_byName.containsKey(field.getName())) {
                DriverNC.fields.add(field.getName());
                DriverNC.fields_value.add(DriverNC.getResources().getString(id));
                DriverNC.fields_id.put(field.getName(), id);

            }
            for (Map.Entry<String, String> entry : DriverNC.nfields_byName.entrySet()) {
                String h = entry.getKey();
                DriverNC.nfields_byID.put(DriverNC.fields_id.get(h), DriverNC.nfields_byName.get(h));
                // do stuff
            }


        }
    }

    /**
     * This method used to refresh UI after language selection
     */
    private void RefreshAct() {
        String temptype = DriverSessionSave.getSession("LANGTemp" + types, DriverMeAct.this);
        DriverSessionSave.saveSession("Lang", DriverSessionSave.getSession("LANGCode" + types, DriverMeAct.this), DriverMeAct.this);

        if (temptype.equals("LTR")) {
            DriverSessionSave.saveSession("Lang_Country", "en_US", DriverMeAct.this);
        } else {
            DriverSessionSave.saveSession("Lang_Country", "ar_EG", DriverMeAct.this);
        }
        Configuration config = new Configuration();
        String langcountry = DriverSessionSave.getSession("Lang_Country", DriverMeAct.this);
        String[] arry = langcountry.split("_");
        String language = DriverSessionSave.getSession("Lang", DriverMeAct.this);

        config.locale = new Locale(language, arry[1]);
        Locale.setDefault(new Locale(language, arry[1]));
        DriverMeAct.this.getBaseContext().getResources().updateConfiguration(config, DriverMeAct.this.getResources().getDisplayMetrics());

        Intent intent = new Intent(DriverMeAct.this, DriverMyStatus.class);
        //  showLoading(DriverMeAct.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Adding click listener to required views.
     */
    private void setOnclicklistener() {
        slider.setOnClickListener(this);
        DoneBtn.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        btnupload.setOnClickListener(this);
        btntaxidetail.setOnClickListener(this);
        me_layout.setOnClickListener(this);
        /*try {
            PackageInfo info = getPackageManager().getPackageInfo("com.Taximobility.driver", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
*/
    }

    @Override
    public void onBackPressed() {

        // super.onBackPressed();

        Intent intent = new Intent(DriverMeAct.this, DriverMyStatus.class);
        startActivity(intent);
        finish();
    }

    /**
     * Actions to be performed Onclick.
     */
    @Override
    public void onClick(View v) {

        try {
            // If logout view clicked the following process runs.
            if (v == btnLogout) {
                logout(DriverMeAct.this);
            }
            if (v == logout_img) {
                logout(DriverMeAct.this);
            }
            // If back view clicked the following process runs.
            if (v == slider) {
                if (profileImage != null) {
                    finish();
                }
            }
            // If profile image view clicked the following process run.
            else if (v == profileImage) {

                try {
                    if (ActivityCompat.checkSelfPermission(DriverMeAct.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(DriverMeAct.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        Utility.actionSheet(DriverMeAct.this, DriverNC.getResources().getString(R.string.str_media), DriverNC.getResources().getString(R.string.yes), "", false, new AlertListener() {
                            @Override
                            public void onSuccess() {
                                ActivityCompat.requestPermissions(DriverMeAct.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_CAMERA);
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                        /*
                        dialog1 = Driver_Utils.alert_view_dialog(DriverMeAct.this, "", DriverNC.getResources().getString(R.string.str_media), DriverNC.getResources().getString(R.string.yes), "", true, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                ActivityCompat.requestPermissions(DriverMeAct.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_CAMERA);
                                dialog.dismiss();
                            }
                        }, (dialogInterface, i) -> dialogInterface.dismiss(), "");

                         */
                    } else
                        getCamera();
                } catch (Exception e) {

                    // TODO: handle exception
                }
            }
            // If done view to update the driver basic details.
            else if (v == DoneBtn) {
                phone = mobileEdt.getText().toString().trim();
                newpwd = passwordEdt.getText().toString().trim();
                confirmpwd = confirmpswdEdt.getText().toString().trim();
                bankname = "";
                bankaccNo = "";
                Drawable drawable = profileImage.getDrawable();
                Bitmap bitmap = DriverImageUtils.drawableToBitmap(drawable);
                if (phone.equalsIgnoreCase(DriverSessionSave.getSession("Phone", DriverMeAct.this)) && confirmpwd.equalsIgnoreCase(DriverSessionSave.getSession("Org_Password", DriverMeAct.this)) && bankname.equalsIgnoreCase(DriverSessionSave.getSession("Bankname", DriverMeAct.this)) && bankaccNo.equalsIgnoreCase(DriverSessionSave.getSession("Bankaccount_No", DriverMeAct.this)) && firstTxt.getText().toString().equalsIgnoreCase(DriverSessionSave.getSession("Name", DriverMeAct.this)) && lastTxt.getText().toString().equalsIgnoreCase(DriverSessionSave.getSession("Lastname", DriverMeAct.this)) && emailEdt.getText().toString().equalsIgnoreCase(DriverSessionSave.getSession("Email", DriverMeAct.this)) && bitmap == downImage) {
                    DriverCToast.ShowToast(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.no_changes));
//                    dialog1 = Driver_Utils.alert_view(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.no_changes), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                } else if (newpwd.length() > 0) {
                    if (validations(ValidateAction.isValidPassword, DriverMeAct.this, newpwd)) {
                        if (!newpwd.equals(confirmpwd)) {
                            DriverCToast.ShowToast(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.pwd_same));
//                            dialog1 = Driver_Utils.alert_view(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.pwd_same), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                        } else {
                            if (validations(ValidateAction.isValueNULL, DriverMeAct.this, phone)) {
                                Drawable d = profileImage.getDrawable();
                                Bitmap bit = DriverImageUtils.drawableToBitmap(d);
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                bit.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                String url = "type=edit_driver_profile_v1";
                                new EditProfile(url);
                            }
                        }
                    }
                } else {
                    if (validations(ValidateAction.isValueNULL, DriverMeAct.this, phone)) {
                        Drawable d = profileImage.getDrawable();
                        Bitmap bit = DriverImageUtils.drawableToBitmap(d);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bit.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        String url = "type=edit_driver_profile_v1";
                        Log.i("profile picture", base64);
                        new EditProfile(url);
                    }
                }
            } else if (v == forgotpswdTxt) {
                Intent intent = new Intent(DriverMeAct.this, DriverChangepassAct.class);
                startActivity(intent);
            }
            // If file upload view to update the driver document details.
            else if (v == btnupload) {
                final TextView btnchoose = fileDialog.findViewById(R.id.btnchoose);
                final TextView btnsubmit = fileDialog.findViewById(R.id.btnsubmit);
                fileimg = fileDialog.findViewById(R.id.fileimg);
                fileimg.setImageResource(R.drawable.driver_no_file);
                fileDialog.show();
                btnchoose.setOnClickListener(v1 -> {
                    imageSelect = 0;
                    getCamera();
                });
                btnsubmit.setOnClickListener(v12 -> {
                    try {
                        if (imageSelected == 1) {
                            Drawable d = fileimg.getDrawable();
                            Bitmap bit = DriverImageUtils.drawableToBitmap(d);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bit.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            file_base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                            String url = "type=driver_document_upload";
                            new FileUpload(url);
                        } else {
                            DriverCToast.ShowToast(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.please_choose));
//                            dialog1 = Driver_Utils.alert_view(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.please_choose), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        DriverCToast.ShowToast(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.image_failed));
//                        dialog1 = Driver_Utils.alert_view(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.image_failed), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                    }
                });
                fileDialog.setOnDismissListener(dialog -> fileimg.setImageResource(R.drawable.driver_no_file));
            } else if (v == btntaxidetail) {
                showtaxiDetails();
            } else if (v == me_layout) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(me_layout.getWindowToken(), 0);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                getCamera();

            }  // permission denied, boo! Disable the
            // functionality that depends on this permission.

        }
    }

    @Override
    protected void onStop() {
        Driver_Utils.closeDialog(fileDialog);
        Driver_Utils.closeDialog(tDialog);
        Driver_Utils.closeDialog(mDialog);
        Driver_Utils.closeDialog(mlangDialog);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mTargetPrompt != null) {
            mTargetPrompt.dismiss();
        }
        if (dialog1 != null)
            Driver_Utils.closeDialog(dialog1);
        super.onDestroy();
    }

    public void showLoading() {
        profile_lay_s.setVisibility(View.GONE);
        layout_loading.setVisibility(View.VISIBLE);
        layout_bottom.setVisibility(View.GONE);
    }

    public void cancelLoading() {
        profile_lay_s.setVisibility(View.VISIBLE);
        layout_loading.setVisibility(View.GONE);
        layout_bottom.setVisibility(View.VISIBLE);
        showPrompt();
    }

    /**
     * Getting path
     */
    private String getRealPathFromURI(final String contentURI) {

        final Uri contentUri = Uri.parse(contentURI);
        final Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null)
            return contentUri.getPath();
        else {
            cursor.moveToFirst();
            final int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void getCamera() {

        Utility.actionSheet(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.choose_an_image), "" + DriverNC.getResources().getString(R.string.camera), "" + DriverNC.getResources().getString(R.string.gallery), false, new AlertListener() {
            @Override
            public void onSuccess() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            imageUri = FileProvider.getUriForFile(DriverMeAct.this,
                                    DriverMeAct.this.getPackageName().concat(".files_root"),
                                    photoFile);
                        } else {
                            imageUri = Uri.fromFile(photoFile);
                        }

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(takePictureIntent, 1);
                    }
                }

            }

            @Override
            public void onFailure() {
                final Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 0);
            }
        });
        /*
        dialog1 = Driver_Utils.alert_view_dialog(this, DriverNC.getResources().getString(R.string.profile_image), DriverNC.getResources().getString(R.string.choose_an_image), DriverNC.getResources().getString(R.string.camera), DriverNC.getResources().getString(R.string.gallery), true, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            imageUri = FileProvider.getUriForFile(DriverMeAct.this,
                                    DriverMeAct.this.getPackageName().concat(".files_root"),
                                    photoFile);
                        } else {
                            imageUri = Uri.fromFile(photoFile);
                        }

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(takePictureIntent, 1);
                    }
                }


            }
        }, (dialog, i) -> {
            final Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(intent, 0);
            dialog.cancel();
        }, "");

         */
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = getDateForCreateImageFile();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            // ResultActivity.startWithUri(SampleActivity.this, resultUri);
            DriverSystems.out.println("Hellow" + resultUri);
            new ImageCompressionAsyncTask().execute(resultUri.toString());
        } else {
            // Toast.makeText(SampleActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(final int requestcode, final int resultcode, final Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        try {
            if (requestcode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            } else if (resultcode == RESULT_OK) {
                switch (requestcode) {
                    case 0:
                        try {
                            UCrop uCrop = UCrop.of(Uri.fromFile(new File(getRealPathFromURI(data.getDataString()))), Uri.fromFile(new File(DriverMeAct.this.getCacheDir(), destinationFileName)))
                                    .useSourceImageAspectRatio().withAspectRatio(1, 1)
                                    .withMaxResultSize(400, 400);
                            UCrop.Options options = new UCrop.Options();
                            options.setToolbarColor(ContextCompat.getColor(DriverMeAct.this, R.color.appbg));
                            options.setStatusBarColor(ContextCompat.getColor(DriverMeAct.this, R.color.header_text));
                            options.setToolbarWidgetColor(ContextCompat.getColor(DriverMeAct.this, R.color.header_text));
                            options.setMaxBitmapSize(1000000000);
                            uCrop.withOptions(options);
                            uCrop.start(DriverMeAct.this);
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            UCrop.of(imageUri, Uri.fromFile(new File(DriverMeAct.this.getCacheDir(), destinationFileName)))
                                    .withAspectRatio(1, 1)
                                    .withMaxResultSize(2000, 2000)
                                    .start(DriverMeAct.this);
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method used to call logout API.
     */
    public void showtaxiDetails() {

        try {
//            final View view = View.inflate(DriverMeAct.this, R.layout.driver_taxidetail_lay, null);
//            if (tDialog != null && tDialog.isShowing())
//                tDialog.cancel();
//            tDialog = new Dialog(DriverMeAct.this, R.style.NewDialog);
//
//            tDialog.setContentView(view);
//            tDialog.setCancelable(true);
//            tDialog.show();

            View view = getLayoutInflater().inflate(R.layout.driver_taxidetail_lay, null);

            BottomSheetDialog tDialog = new BottomSheetDialog(this);
            tDialog.setContentView(view);
            tDialog.setCancelable(true);
            tDialog.show();


            DirverColorchange.ChangeColor(tDialog.findViewById(R.id.alert_id), DriverMeAct.this);
            DriverFontHelper.applyFont(DriverMeAct.this, tDialog.findViewById(R.id.alert_id));
            final TextView modelTxt = tDialog.findViewById(R.id.modelTxt);
            final TextView taxinoTxt = tDialog.findViewById(R.id.taxinoTxt);
            final TextView assignfromTxt = tDialog.findViewById(R.id.assignfromTxt);
            final TextView assigntoTxt = tDialog.findViewById(R.id.assigntoTxt);
            final Button close_dialog = tDialog.findViewById(R.id.close_dialog);

            if (close_dialog != null) {
                close_dialog.setOnClickListener(view1 -> tDialog.dismiss());
            }
            modelTxt.setText(taxi_model);
            taxinoTxt.setText(taxi_no);
            assignfromTxt.setText("" + (taxi_map_from));
            assigntoTxt.setText("" + (taxi_map_to));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPrompt() {
        if (!DriverSessionSave.getSession(DriverCommonData.SHOW_PROFILE_TOOLTIP, DriverMeAct.this, false)) {
            mTargetPrompt = new MaterialTapTargetPrompt.Builder(DriverMeAct.this)
                    .setTarget(findViewById(R.id.tool_tip))
                    .setFocalRadius(0f)
                    .setAnimationInterpolator(new FastOutSlowInInterpolator())
                    .setBackgroundColour(DriverCL.getResources().getColor(R.color.tooltip_background))
                    .setPrimaryText(DriverNC.getString(R.string.wallet_moved_tooltip))
                    .setSecondaryText(DriverNC.getString(R.string.ok))
                    .setPrimaryTextColour(DriverCL.getResources().getColor(R.color.white))
                    .setSecondaryTextColour(DriverCL.getResources().getColor(R.color.pastbookingcashtext))
                    .setFocalPadding(R.dimen.sp_20)
                    .show();

            DriverSessionSave.saveSession(DriverCommonData.SHOW_PROFILE_TOOLTIP, true, DriverMeAct.this);
        }
    }

    /**
     * Call string method used to call string file from back end
     */
    private class callString implements DriverAPIResult {
        public callString(final String url) {
            String urls = DriverSessionSave.getSession("currentStringUrl", DriverMeAct.this);
            if (urls.equals(""))
                urls = DriverSessionSave.getSession("Lang_English", DriverMeAct.this);
            new DriverAPIService_Retrofit_JSON(DriverMeAct.this, this, null, true, urls, true).execute();
        }

        @Override
        public void getResult(boolean isSuccess, String result) {
            if (isSuccess) {
                DriverNC.nfields_byID.clear();
                DriverNC.nfields_byName.clear();
                DriverNC.fields.clear();
                DriverNC.fields_value.clear();
                DriverNC.fields_id.clear();
                setLocale();
                DriverSessionSave.saveSession("wholekey", result, DriverMeAct.this);
                //   getAndStoreStringValues(result);
                RefreshAct();
            }
        }
    }

    /**
     * Used to call the edit profile Api(post method) and parse the response
     */
    private class EditProfile implements DriverAPIResult {
        String msg = "";

        public EditProfile(String url) {

            try {
                JSONObject j = new JSONObject();
                j.put("driver_id", DriverSessionSave.getSession("Id", DriverMeAct.this));
                j.put("salutation", DriverSessionSave.getSession("Salutation", DriverMeAct.this));
                j.put("email", emailEdt.getText().toString());
                j.put("phone", phone);
                j.put("firstname", firstTxt.getText().toString());
                j.put("lastname", lastTxt.getText().toString());
                j.put("password", confirmpwd);
                j.put("bankname", bankname);
                j.put("bankaccount_no", bankaccNo);
                j.put("profile_picture", encodedImage);
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverMeAct.this, this, j, false).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {

            try {
                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        DriverSessionSave.saveSession("Bankname", bankname, DriverMeAct.this);
                        DriverSessionSave.saveSession("Bankaccount_No", bankaccNo, DriverMeAct.this);
                        DriverSessionSave.saveSession("Org_Password", confirmpwd, DriverMeAct.this);
                        DriverSessionSave.saveSession("Phone", phone, DriverMeAct.this);
                        DriverSessionSave.saveSession("Name", firstTxt.getText().toString(), DriverMeAct.this);
                        DriverSessionSave.saveSession("Lastname", lastTxt.getText().toString(), DriverMeAct.this);
                        DriverSessionSave.saveSession("Email", emailEdt.getText().toString(), DriverMeAct.this);
                        DriverSessionSave.saveSession("driver_wallet_amount", json.getJSONObject("detail").getString("driver_wallet_amount"), DriverMeAct.this);
                        DriverSessionSave.saveSession("driver_wallet_pending_amount", json.getJSONObject("detail").getString("driver_wallet_pending_amount"), DriverMeAct.this);
                        DriverSessionSave.saveSession("trip_amount", json.getJSONObject("detail").getString("trip_amount"), DriverMeAct.this);
                        DriverSessionSave.saveSession("trip_pending_amount", json.getJSONObject("detail").getString("trip_pending_amount"), DriverMeAct.this);
                        Drawable drawable = profileImage.getDrawable();
                        downImage = DriverImageUtils.drawableToBitmap(drawable);
                        msg = json.getString("message");
                        confirmpswdEdt.setText("");
                        passwordEdt.setText("");
                        DriverCToast.ShowToast(DriverMeAct.this, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                    } else {
                        msg = json.getString("message");
                        DriverCToast.ShowToast(DriverMeAct.this, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                    }
                } else {
                    runOnUiThread(() -> DriverCToast.ShowToast(DriverMeAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @API call(get method) to get the driver profile data and parsing the response
     */
    private class GetProfileData implements DriverAPIResult {
        public GetProfileData(String url, JSONObject data) {

            try {
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverMeAct.this, this, data, false).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            // cancelLoading();
            try {
                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        JSONObject details = json.getJSONObject("detail");

                        if (details.has(DriverCommonData.SKIP_DRIVER_EMAIL))
                            DriverSessionSave.saveSession(DriverCommonData.SKIP_DRIVER_EMAIL, details.getString(DriverCommonData.SKIP_DRIVER_EMAIL).equals("1"), DriverMeAct.this);
                        else
                            DriverSessionSave.saveSession(DriverCommonData.SKIP_DRIVER_EMAIL, false, DriverMeAct.this);

                        if (DriverSessionSave.getSession(DriverCommonData.SKIP_DRIVER_EMAIL, DriverMeAct.this, false))
                            emailEdt.setHint(DriverNC.getString(R.string.email_optional));
                        else
                            emailEdt.setHint(DriverNC.getString(R.string.email));

                        firstTxt.setText(Html.fromHtml(details.getString("name")));
                        lastTxt.setText(Html.fromHtml(details.getString("lastname")));
                        emailEdt.setText(details.getString("email"));
                        mobileEdt.setText(details.getString("phone"));
                        DriverSessionSave.saveSession(DriverCommonData.PLAN_TYPE, details.getString("commission_subscription"), DriverMeAct.this);
                        DriverSessionSave.saveSession("driver_wallet_amount", details.getString("driver_wallet_amount"), DriverMeAct.this);
                        DriverSessionSave.saveSession("driver_wallet_pending_amount", details.getString("driver_wallet_pending_amount"), DriverMeAct.this);
                        DriverSessionSave.saveSession("trip_amount", details.getString("trip_amount"), DriverMeAct.this);
                        DriverSessionSave.saveSession("trip_pending_amount", details.getString("trip_pending_amount"), DriverMeAct.this);
                        DriverSessionSave.saveSession("referal_amount", "300", DriverMeAct.this);
                        DriverSessionSave.saveSession("total_amount", details.getString("total_amount"), DriverMeAct.this);
                        if (details.has("driver_referral_settings"))
                            DriverSessionSave.saveSession("referal", details.getString("driver_referral_settings"), getApplicationContext());


                        //   HeadTitle.setTypeface(HeadTitle.getTypeface(), Typeface.BOLD);


                        walletamountr = details.getInt("driver_wallet_amount");

                        String imgPath = details.getString("main_image_path").trim();
                        DriverSessionSave.saveSession("main_image_path", imgPath, DriverMeAct.this);
                        taxi_model = details.getString("taxi_model");
                        taxi_no = details.getString("taxi_no");
                        taxi_map_from = details.getString("taxi_map_from");
                        taxi_map_to = details.getString("taxi_map_to");
                        driver_rating = details.getInt("driver_rating");
//                        driverRat.setText(driver_rating);
//                        if (driver_rating == 0)
//                            driverRat.setImageResource(R.drawable.driver_star6);
//                        else if (driver_rating == 1)
//                            driverRat.setImageResource(R.drawable.driver_star1);
//                        else if (driver_rating == 2)
//                            driverRat.setImageResource(R.drawable.driver_star2);
//                        else if (driver_rating == 3)
//                            driverRat.setImageResource(R.drawable.driver_star3);
//                        else if (driver_rating == 4)
//                            driverRat.setImageResource(R.drawable.driver_star4);
//                        else if (driver_rating == 5)
//                            driverRat.setImageResource(R.drawable.driver_star5);
                        if (imgPath != null && imgPath.length() > 0) {
                            Picasso.get().load(imgPath).placeholder(getResources().getDrawable(R.drawable.driver_loadingimage)).error(getResources().getDrawable(R.drawable.driver_noimage)).into(profileImage);
                        } else {
                            if (details.getString("name") != "") {
                                ProfileImageSetupClass.setupProfileImage(
                                        details.getString("name"), profileImage
                                );
                            } else {
                                Picasso.get().load(R.drawable.loadingimage).into(profileImage);
                            }
                        }

                        Drawable drawable = profileImage.getDrawable();
                        downImage = DriverImageUtils.drawableToBitmap(drawable);
                        DriverSessionSave.saveSession("Bankname", details.getString("bankname"), DriverMeAct.this);
                        DriverSessionSave.saveSession("Bankaccount_No", details.getString("bankaccount_no"), DriverMeAct.this);
                        DriverSessionSave.saveSession("Org_Password", confirmpwd, DriverMeAct.this);
                        DriverSessionSave.saveSession("Phone", details.getString("phone"), DriverMeAct.this);
                        DriverSessionSave.saveSession("Name", details.getString("name"), DriverMeAct.this);
                        DriverSessionSave.saveSession("Lastname", details.getString("lastname"), DriverMeAct.this);
                        DriverSessionSave.saveSession("Email", details.getString("email"), DriverMeAct.this);
                        if (!details.getString("plan_expiration_message").equals("")) {
                            DriverCToast.ShowToast(DriverMeAct.this, "" + details.getString("plan_expiration_message"));
//                            dialog1 = Driver_Utils.alert_view(DriverMeAct.this, DriverNC.getResources().getString(R.string.message), details.getString("plan_expiration_message"), DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                        }
                    } else if (json.getInt("status") == -4 || json.getInt("status") == -1) {
                        Intent locationService = new Intent(DriverMeAct.this, LocationUpdate.class);
                        stopService(new Intent(locationService));
                        clearsession(DriverMeAct.this);
                        Utility.actionSheet(DriverMeAct.this, json.getString("message"), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancel), false, new AlertListener() {
                            @Override
                            public void onSuccess() {
                                int length = DriverCommonData.mActivitylist.size();
                                if (length != 0) {
                                    for (int i = 0; i < length; i++) {
                                        DriverCommonData.mActivitylist.get(i).finish();
                                    }
                                }
                                Intent intent = new Intent(DriverMeAct.this, DriverUserLoginAct.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                        /*
                        dialog1 = Driver_Utils.alert_view_dialog(DriverMeAct.this, DriverNC.getResources().getString(R.string.message), json.getString("message"), DriverNC.getResources().getString(R.string.ok), "", false, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int length = DriverCommonData.mActivitylist.size();
                                if (length != 0) {
                                    for (int i = 0; i < length; i++) {
                                        DriverCommonData.mActivitylist.get(i).finish();
                                    }
                                }
                                dialog.dismiss();
                                Intent intent = new Intent(DriverMeAct.this, DriverUserLoginAct.class);
                                startActivity(intent);
                                finish();
                            }
                        }, (dialog, which) -> dialog.dismiss(), "");
                        */
                    } else if (json.getInt("status") == -2) {
                        DriverCToast.ShowToast(DriverMeAct.this, "" + json.getString("message"));
//                        dialog1 = Driver_Utils.alert_view(DriverMeAct.this, DriverNC.getResources().getString(R.string.message), json.getString("message"), DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                    } else if (json.getInt("status") == -3) {
                        DriverCToast.ShowToast(DriverMeAct.this, "" + json.getString("message"));
//                        dialog1 = Driver_Utils.alert_view(DriverMeAct.this, DriverNC.getResources().getString(R.string.message), json.getString("message"), DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                    } else {
                        DriverCToast.ShowToast(DriverMeAct.this, json.getString("message"));
                    }
                    profile_lay_s.setVisibility(View.VISIBLE);
                    me_layout.setVisibility(View.VISIBLE);

                    if (DriverSessionSave.getSession(DriverCommonData.PLAN_TYPE, DriverMeAct.this).equals("1")) {
                        if (DriverSessionSave.getSession("referal", DriverMeAct.this).equalsIgnoreCase("1"))
                            invitefriends_bottom.setVisibility(View.VISIBLE);
                        else {
                            invitefriends_bottom.setVisibility(View.GONE);
                            // findViewById(R.id.inviteView).setVisibility(View.GONE);
                        }
                        subscription.setVisibility(View.GONE);
                    } else {
                        DriverSystems.out.println("nan----222");
                        if (DriverSessionSave.getSession("referal", DriverMeAct.this).equalsIgnoreCase("1"))
                            invitefriends_bottom.setVisibility(View.VISIBLE);
                        else
                            invitefriends_bottom.setVisibility(View.GONE);

                        subscription.setVisibility(View.VISIBLE);
                    }
                } else {
                    DriverCToast.ShowToast(DriverMeAct.this, DriverNC.getString(R.string.server_error));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class ImageCompressionAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private Dialog mDialog;
        private String result;
        private int orientation;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            final View view = View.inflate(DriverMeAct.this, R.layout.driver_progress_bar, null);
            mDialog = new Dialog(DriverMeAct.this, R.style.NewDialog);
            mDialog.setContentView(view);
            mDialog.setCancelable(false);
            mDialog.show();

            ImageView iv = mDialog.findViewById(R.id.giff);
            DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
            Glide.with(DriverMeAct.this)
                    .load(R.raw.driver_loading_anim)
                    .into(imageViewTarget);
        }

        @Override
        protected Bitmap doInBackground(final String... params) {
            try {
                result = getRealPathFromURI(params[0]);
                final File file = new File(result);
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                mBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                final byte[] image = stream.toByteArray();
                encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
            } catch (final Exception e) {
                // TODO: handle exception
                runOnUiThread(() -> DriverCToast.ShowToast(DriverMeAct.this, DriverNC.getResources().getString(R.string.image_failed)));
            }
            return mBitmap;
        }

        @Override
        protected void onPostExecute(final Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                if (DriverMeAct.this != null && mDialog.isShowing())
                    mDialog.dismiss();

                profileImage.setBackgroundResource(0);
                if (result != null)
                    profileImage.setImageBitmap(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @API call(method) to update the driver document file details and parsing the response
     */
    private class FileUpload implements DriverAPIResult {
        String msg = "";

        public FileUpload(String url) {

            try {
                if (isOnline()) {
                    JSONObject j = new JSONObject();
                    j.put("driver_id", DriverSessionSave.getSession("Id", DriverMeAct.this));
                    j.put("driver_document", file_base64);
                    j.put("device_type", "1");
                    new DriverAPIService_Retrofit_JSON(DriverMeAct.this, this, j, false).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {

            try {
                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        msg = json.getString("message");
                        fileDialog.dismiss();
                        file_base64 = "";
                        DriverCToast.ShowToast(DriverMeAct.this, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                    } else {
                        msg = json.getString("message");
                        fileDialog.dismiss();
                        file_base64 = "";
                        DriverCToast.ShowToast(DriverMeAct.this, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverMeAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMeAct.this, "");
                    }
                } else {
                    fileDialog.dismiss();
                    runOnUiThread(() -> DriverCToast.ShowToast(DriverMeAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                fileDialog.dismiss();
            }
        }


    }
}