package com.movedriver;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.movedriver.driver.utils.DriverCToast;
import com.movedriver.driver.utils.DriverNC;
import com.movedriver.driver.utils.DriverSystems;
import com.movedriver.util.FontHelper;
import com.movedriver.util.SessionSave;
import com.movedriver.util.TaxiUtil;
import com.movedriver.util.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.util.Locale;

/**
 * <p>
 * this class is used to put some common methods<br>
 * which can access any part of the code
 * <p>
 * </p>
 *
 * @author developer
 */
public abstract class MainActivity extends AppCompatActivity {
    public static Dialog mshowDialog;
    public static Dialog mgpsDialog;
    public static Dialog mlogoutDialog;
    public static Dialog sDialog;
    boolean i = false;
    public Bundle BsavedInstanceState;
    public static String APP_VERSION;
    Dialog dialog;

    public enum ValidateAction {
        NONE, isValueNULL, isValidPassword, isValidSalutation, isValidFirstname, isValidLastname, isValidCard, isValidExpiry, isValidMail, isValidConfirmPassword, isNullPromoCode, isValidCvv, isNullMonth, isNullYear, isNullCardname, isValidphone
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BsavedInstanceState = savedInstanceState;
//        MainHomeFragmentActivity.context = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (!SessionSave.getSession("facebook_key", MainActivity.this).equals(""))
            FacebookSdk.setApplicationId(SessionSave.getSession("facebook_key", MainActivity.this));
        else FacebookSdk.setApplicationId(getString(R.string.facebookAppId));
//        FacebookSdk.sdkInitialize(this.getApplicationContext());
//        Fabric.with(this, new Crashlytics());
        DriverSystems.out.println("appVersionnnn" + APP_VERSION);
        if (APP_VERSION == null) {
            APP_VERSION = BuildConfig.VERSION_NAME;
        }
        setView();
    }


    /**
     * This is method for set the layout for the child activity
     */
    private void setView() {
        // TODO Auto-generated method stub
        int view = setLayout();
        if (view != 0) {
            setContentView(view);
            priorChanges();
            Initialize();
            try {
                if (mshowDialog.isShowing()) mshowDialog.dismiss();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    @Override
    public View onCreateView(View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        View v = super.onCreateView(parent, name, context, attrs);
        return v;
    }

    public abstract int setLayout();

    public void priorChanges() {

    }

    public abstract void Initialize();

    public Dialog alertmDialog;

    /**
     * Custom alert dialog used in entire project.can call from anywhere with the following and also used in utility class
     *
     * @param title       set the title for alert dialog
     * @param message     set the message for alert dialog
     * @param success_txt set the success text in success button
     * @param failure_txt set the failure text in failure button
     */
    public void alert_view(Context mContext, String title, String message, String success_txt, String failure_txt) {
        try {
            dialog = Utility.alert_view_dialog(MainActivity.this, "" + title, "" + message, "" + success_txt, "", true, (dialog, which) -> dialog.dismiss(), (dialog, which) -> dialog.dismiss(), "");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void cancelLoading() {
        if (mshowDialog != null) if (mshowDialog.isShowing()) mshowDialog.dismiss();
    }

    /**
     * method which shows alert dialog to turn on gps when gps is in offline mode
     */
    public static void gpsalert(final Context mContext, boolean isconnect) {
        if (!isconnect) {
            final View view = View.inflate(mContext, R.layout.alert_view, null);
            mgpsDialog = new Dialog(mContext, R.style.NewDialog);
            mgpsDialog.setContentView(view);
            FontHelper.applyFont(mContext, mgpsDialog.findViewById(R.id.alert_id));
            mgpsDialog.setCancelable(false);
            if (!mgpsDialog.isShowing()) mgpsDialog.show();
            final TextView title_text = mgpsDialog.findViewById(R.id.title_text);
            final TextView message_text = mgpsDialog.findViewById(R.id.message_text);
            final Button button_success = mgpsDialog.findViewById(R.id.button_success);
            final Button button_failure = mgpsDialog.findViewById(R.id.button_failure);
            button_failure.setVisibility(View.GONE);
            title_text.setText("" + DriverNC.getResources().getString(R.string.location_disable));
            message_text.setText("" + DriverNC.getResources().getString(R.string.location_enable));
            button_success.setText("" + DriverNC.getResources().getString(R.string.enable));
            button_success.setOnClickListener(v -> {
                Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(mIntent);
            });
            button_failure.setOnClickListener(v -> mgpsDialog.dismiss());
        } else {
            try {
                if (mgpsDialog != null && mgpsDialog.isShowing()) mgpsDialog.dismiss();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    /**
     * This is method for check the mail is valid by the use of regex class.
     */
    public boolean validdmail(String string) {
        // TODO Auto-generated method stub
        return isValidEmail(string);
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /**
     * This is method for show the toast
     */
    public void ShowToast(Context context, String s) {
        DriverCToast.ShowToast(context, s);
    }

    /**
     * This method used to call logout API.
     */
//    public void logout(final Context context) {
//        try {
//            final View view = View.inflate(context, R.layout.netcon_lay, null);
//            mlogoutDialog = new Dialog(context, R.style.dialogwinddow);
//            mlogoutDialog.setContentView(view);
//            mlogoutDialog.setCancelable(false);
//            mlogoutDialog.show();
//            FontHelper.applyFont(context, mlogoutDialog.findViewById(R.id.alert_id));
//            final TextView title_text = mlogoutDialog.findViewById(R.id.title_text);
//            final TextView message_text = mlogoutDialog.findViewById(R.id.message_text);
//            final Button button_success = mlogoutDialog.findViewById(R.id.button_success);
//            final Button button_failure = mlogoutDialog.findViewById(R.id.button_failure);
//            title_text.setText("" + DriverNC.getResources().getString(R.string.message));
//            message_text.setText("" + DriverNC.getResources().getString(R.string.confirmlogout));
//            button_success.setText("" + DriverNC.getResources().getString(R.string.yes));
//            button_failure.setText("" + DriverNC.getResources().getString(R.string.no));
//            button_success.setOnClickListener(v -> {
//                try {
//                    mlogoutDialog.dismiss();
//                    JSONObject j = new JSONObject();
//                    j.put("id", SessionSave.getSession(PASS_ID, context));
//                    if (SessionSave.getSession(LOGOUT, context).equals("")) {
//                        new Logout("type=passenger_logout", context, j);
//                        fbLogout();
//                    } else
//                        alert_view(context, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.bookedtaxi), "" + DriverNC.getResources().getString(R.string.ok), "");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//            button_failure.setOnClickListener(v -> {
//                mlogoutDialog.dismiss();
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * This is method for logout the user from their facebook login if they logged in using facebook.
     */
    public void fbLogout() {
        LoginManager.getInstance().logOut();
    }

    /**
     * This is method for set the language configuration.
     */
    public void setLocale() {
        if (SessionSave.getSession("Lang", MainActivity.this).equals("")) {
            SessionSave.saveSession("Lang", "en", MainActivity.this);
        }
        if (SessionSave.getSession("Lang_Country", MainActivity.this).equals("")) {
            SessionSave.saveSession("Lang_Country", "en_US", MainActivity.this);
        }

        Configuration config = new Configuration();
        String langcountry = SessionSave.getSession("Lang_Country", MainActivity.this);
        String[] arry = langcountry.split("_");
        String language = SessionSave.getSession("Lang", MainActivity.this);
        config.locale = new Locale(language, arry[1]);
        Locale.setDefault(new Locale(language, arry[1]));
        MainActivity.this.getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    /**
     * This is method for check the Internet connection
     */
    public boolean isOnline() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo element : info) {
                    if (element.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if (dialog != null) Utility.closeDialog(dialog);
        super.onDestroy();
    }

    /**
     * This is method to validate the field like Mail,Password,Name,Salutation etc and show the appropriate alert message.
     */
    public boolean validations(ValidateAction VA, Context con, String stringtovalidate) {
        String message = "";
        boolean result = false;
        switch (VA) {
            case isValueNULL:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_mobile_number);
                else result = true;
                break;
            case isValidPassword:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_password);
                else if (stringtovalidate.length() < 6)
                    message = "" + DriverNC.getResources().getString(R.string.password_min_character);
                else if (stringtovalidate.length() > 32)
                    message = "" + DriverNC.getResources().getString(R.string.password_max_character);
                else result = true;
                break;
            case isValidSalutation:
                if (TextUtils.isEmpty(stringtovalidate) || stringtovalidate == null)
                    message = "" + DriverNC.getResources().getString(R.string.please_select_your_salutation);
                else result = true;
                break;
            case isValidFirstname:
                if (TextUtils.isEmpty(stringtovalidate) || stringtovalidate.length() < 3)
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_first_name);
                else result = true;
                break;
            case isValidLastname:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_last_name);
                else result = true;
                break;
            case isValidCard:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_card_number);
                else if (stringtovalidate.length() < 9 || stringtovalidate.length() > 16)
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_valid_card_number);
                else result = true;
                break;
            case isValidExpiry:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_expiry_date);
                else result = true;
                break;
            case isValidMail:
                if (SessionSave.getSession(TaxiUtil.SKIP_PASSENGER_EMAIL, MainActivity.this, false)) {
                    if (TextUtils.isEmpty(stringtovalidate)) result = true;
                    else if (!validdmail(stringtovalidate))
                        message = "" + DriverNC.getResources().getString(R.string.enter_the_valid_email);
                    else result = true;
                } else {
                    if (TextUtils.isEmpty(stringtovalidate))
                        message = "" + DriverNC.getResources().getString(R.string.enter_the_email);
                    else if (!validdmail(stringtovalidate))
                        message = "" + DriverNC.getResources().getString(R.string.enter_the_valid_email);
                    else result = true;
                }
                break;
            case isValidConfirmPassword:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_confirmation_password);
                else result = true;
                break;
            case isNullPromoCode:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.reg_enterprcode);
                else result = true;
                break;
            case isNullMonth:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.reg_expmonth);
                else result = true;
                break;
            case isNullYear:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.reg_expyear);
                else result = true;
                break;
            case isValidCvv:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_valid_CVV);
                else result = true;
                break;
            case isNullCardname:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.reg_entercardname);
                else result = true;
                break;
            case isValidphone:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_confirmation_phoneno);
                else if (stringtovalidate.length() < 6 || stringtovalidate.length() > 15)
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_confirmation_phoneno);
                else result = true;
                break;
        }
        if (!message.equals("")) {
            ShowToast(con, message);
        }
        return result;
    }

    /**
     * This is method for show progress bar
     */
    public void showLoading(Context context) {
        try {
            if (context != null) {
                if (mshowDialog != null) mshowDialog.dismiss();
                View view = View.inflate(context, R.layout.progress_bar, null);
                mshowDialog = new Dialog(context, R.style.dialogwinddow);
                mshowDialog.setContentView(view);
                mshowDialog.setCancelable(false);
                mshowDialog.show();

                ImageView iv = mshowDialog.findViewById(R.id.giff);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                Glide.with(MainActivity.this).load(R.raw.loading_anim).into(imageViewTarget);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        Utility.closeDialog(mgpsDialog);
        Utility.closeDialog(mlogoutDialog);
        Utility.closeDialog(mshowDialog);
        Utility.closeDialog(alertmDialog);
        Utility.closeDialog(sDialog);
        super.onStop();
    }
}