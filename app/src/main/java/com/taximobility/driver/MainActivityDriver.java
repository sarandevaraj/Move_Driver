package com.taximobility.driver;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.taximobility.R;
import com.taximobility.driver.data.DriverCommonData;
import com.taximobility.driver.data.DriverMystatusData;
import com.taximobility.driver.interfaces.DriverAPIResult;
import com.taximobility.driver.interfaces.DriverClickInterface;
import com.taximobility.driver.permission.DriverDevicePermissionActivityDriver;
import com.taximobility.driver.permission.DriverStoreAndSecureActivityDriver;
import com.taximobility.driver.service.DriverAPIService_Retrofit_JSON;
import com.taximobility.driver.service.DriverNonActivity;
import com.taximobility.driver.service.LocationUpdate;
import com.taximobility.driver.utils.DriverCL;
import com.taximobility.driver.utils.DriverFontHelper;
import com.taximobility.driver.utils.DriverGpsStatus;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverNetworkStatus;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.driver.utils.DriverSystems;
import com.taximobility.driver.utils.Driver_Utils;
import com.taximobility.interfaces.AlertListener;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class is the parent abstract class for all other activities
 */
public abstract class MainActivityDriver extends DriverBaseActivity implements DriverClickInterface {
    public static DriverMystatusData mMyStatus;
    public static Dialog mgpsDialog;
    public static Dialog mshowDialog;
    public static String APP_VERSION = "";
    public static MainActivityDriver context;
    public final String TAG = getClass().getSimpleName();
    public DriverNetworkStatus networkStatus;
    public DriverGpsStatus gpsStatus;
    public Dialog alertDialog;
    Bundle BsavedInstanceState;
    Dialog dialog1;
    DriverNonActivity nonactiityobj = new DriverNonActivity();
    private static final int MY_PERMISSIONS_REQUEST_GPS = 111;

    /**
     * clear all driver session variables used except getcoreconfig details
     *
     * @param ctx - Context
     */
    public static void clearsession(Context ctx) {

        try {
            DriverSessionSave.saveSession("status", "", ctx);
            DriverSessionSave.saveSession("Id", "", ctx);
            DriverSessionSave.saveSession("Driver_locations", "", ctx);
            DriverSessionSave.saveSession("driver_id", "", ctx);
            DriverSessionSave.saveSession("Name", "", ctx);
            DriverSessionSave.saveSession("company_id", "", ctx);
            DriverSessionSave.saveSession("bookedby", "", ctx);
            DriverSessionSave.saveSession("p_image", "", ctx);
            DriverSessionSave.saveSession("Email", "", ctx);
            DriverSessionSave.saveSession("phone_number", "", ctx);
            DriverSessionSave.saveSession("driver_password", "", ctx);
            DriverSessionSave.saveSession("trip_id", "", ctx);
            DriverSessionSave.setWaitingTime(0L, ctx);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static boolean isNetworkEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    /**
     * Showing gps alert enable
     */
    public static void gpsalert(final AppCompatActivity mContext, boolean isconnect) {

        if (!isconnect) {


            if (mContext instanceof DriverSplashAct) {
                LinearLayout sub_can = mgpsDialog.findViewById(R.id.sub_can);
                sub_can.setPadding(0, 10, 0, 10);
            }
            String message = "";
            if (!isNetworkEnabled(mContext))
                message = DriverNC.getString(R.string.location_enable);
            else
                message = DriverNC.getString(R.string.change_network);
            Utility.actionSheetCancel(mContext, message, DriverNC.getResources().getString(R.string.enable), "", false, new AlertListener() {
                @Override
                public void onSuccess() {
                    Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(mIntent);
                }
                @Override
                public void onFailure() {

                }
            });
            /*
            Driver_Utils.alert_view_dialog_GPS(mContext, "" + DriverNC.getResources().getString(R.string.location_disable),
                    "" + message,
                    "" + DriverNC.getResources().getString(R.string.enable),
                    "", false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            mContext.startActivity(mIntent);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }, "");

             */

        } else {
            try {
                DriverSystems.out.println("________called" + DriverSessionSave.getSession("trip_id", mContext));
                if (!DriverSessionSave.getSession("Id", mContext).trim().equals("")) {
                    LocationUpdate.startLocationService(mContext);
                }
                Driver_Utils.closeGPSDialog();
                mgpsDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Utility.closeactionsheet();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_GPS) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivityDriver.this, LocationUpdate.class);
                    stopService(intent);
                    final Intent i = new Intent(MainActivityDriver.this, DriverSplashAct.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    finish();
                }
            }
        }
    }


    public static boolean isGpsEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Storing color values from server to local hashmap
     *
     * @param result -> response from color file url got from company domain response
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

    /**
     * Storing String values from server to local hashmap
     *
     * @param result -> response from String file url got from company domain response
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
     * Getting String values Splits string , key ,id and save in hashmap.
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
     * Getting Color values Splits color , key ,id and save in hashmap.
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
            // do stuff
        }

    }

    /**
     * This is method for set up the base data for the child activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SessionSave.saveSession("user_type", "d", this);
        DriverNetworkStatus.appContext = this;
        mMyStatus = new DriverMystatusData(MainActivityDriver.this);
        BsavedInstanceState = savedInstanceState;
        context = this;
        networkStatus = new DriverNetworkStatus();
        gpsStatus = new DriverGpsStatus();
        registerReceiver(networkStatus, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(gpsStatus, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        // TODO: Move this to where you establish a user session
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

/*

                if (!(DriverSessionSave.getSession("wholekeyColor", MainActivityDriver.this).trim().equals(""))) {
                    getAndStoreStringValues(DriverSessionSave.getSession("wholekey", MainActivityDriver.this));
                    getAndStoreColorValues(DriverSessionSave.getSession("wholekeyColor", MainActivityDriver.this));
                }
                if (DriverSessionSave.getSession("base_url", MainActivityDriver.this).trim().equals("")) {
                    DriverServiceGenerator.API_BASE_URL = DriverSessionSave.getSession("base_url", MainActivityDriver.this);
                    getAndStoreStringValues(DriverSessionSave.getSession("wholekey", MainActivityDriver.this));
                    getAndStoreColorValues(DriverSessionSave.getSession("wholekeyColor", MainActivityDriver.this));
                }
*/

/*
                if (APP_VERSION == null) {
                    APP_VERSION = BuildConfig.VERSION_NAME;
                }*/

            }
        }, 200);

        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        int view = setLayout();
        setLocale();
        if (view != 0) {
            setContentView(view);
            if (MainActivityDriver.this != null) {
                Initialize();
                try {
                    if (mshowDialog != null && mshowDialog.isShowing())
                        mshowDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Abstract method to set layout
     */
    public abstract int setLayout();

    /**
     * Abstract method to initialize variable
     */
    public abstract void Initialize();

    /**
     * This is method for show the toast
     */
    public void ShowToast(Context contex, String message) {
        if (contex != null && message != null) {
            Toast toast = Toast.makeText(contex, message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * This is method for show the Log
     */
    public void showLog(String msg) {

        Log.i(TAG, msg);
    }


    /**
     * This is method for check the mail is valid by the use of regex class.
     */
    public boolean validdmail(String string) {
        // TODO Auto-generated method stub
        boolean isValid = false;
        String expression = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(string);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * This is method for check the Internet connection
     */
    public boolean isOnline() {

        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo networkInfo : info)
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    /**
     * This is method for show progress bar over all activity
     */
    public void showLoading(Context context) {

        try {
            if (mshowDialog != null)
                if (mshowDialog.isShowing())
                    mshowDialog.dismiss();
            View view = View.inflate(context, R.layout.driver_progress_bar, null);
            mshowDialog = new Dialog(context, R.style.dialogwinddow);
            mshowDialog.setContentView(view);
            mshowDialog.setCancelable(false);

            mshowDialog.show();

            ImageView iv = mshowDialog.findViewById(R.id.giff);
            DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
            Glide.with(MainActivityDriver.this)
                    .load(R.raw.driver_loading_anim)
                    .into(imageViewTarget);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * This is method for convert the string value into MD5
     *
     * @param pass - String to convert to MD5
     */
    public String convertPassMd5(String pass) {

        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

    /**
     * This is method for logout the user from their current session.
     *
     * @param context
     */
    public void logout(final Context context) {


        JSONObject j = new JSONObject();
        try {
            j.put("driver_id", DriverSessionSave.getSession("Id", context));
            j.put("shiftupdate_id", DriverSessionSave.getSession("Shiftupdate_Id", context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "type=user_logout";
        new Logout(url, j);

    }


    @Override
    public void positiveButtonClick(DialogInterface dialog, int id, String s) {
        switch (s) {
            case "1":
                dialog.dismiss();
                JSONObject j = new JSONObject();
                try {
                    j.put("driver_id", DriverSessionSave.getSession("Id", context));
                    j.put("shiftupdate_id", DriverSessionSave.getSession("Shiftupdate_Id", context));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = "type=user_logout";
                new Logout(url, j);
                break;
            case "2":
                dialog.dismiss();
                break;
            case "3":
                dialog.dismiss();
                Intent i = new Intent(MainActivityDriver.this, DriverOngoingAct.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void negativeButtonClick(DialogInterface dialog, int id, String s) {
        dialog.dismiss();
    }

    /**
     * This is method for set the language configuration.
     */
    public void setLocale() {
        if (DriverSessionSave.getSession("Lang", MainActivityDriver.this).equals("")) {
            DriverSessionSave.saveSession("Lang", "en", MainActivityDriver.this);
            DriverSessionSave.saveSession("Lang_Country", "en_GB", MainActivityDriver.this);
        }
        DriverSystems.out.println("Lang" + DriverSessionSave.getSession("Lang", MainActivityDriver.this));
        DriverSystems.out.println("Lang_Country" + DriverSessionSave.getSession("Lang_Country", MainActivityDriver.this));
        Configuration config = new Configuration();
        String langcountry = DriverSessionSave.getSession("Lang_Country", MainActivityDriver.this);
        String language = DriverSessionSave.getSession("Lang", MainActivityDriver.this);
        String[] arry = langcountry.split("_");
        config.locale = new Locale(language, arry[1]);
        Locale.setDefault(new Locale(language, arry[1]));
        MainActivityDriver.this.getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    /**
     * Method to show Gcm notification
     */
    public void checkGCM() {
        String dialogMessage = DriverSessionSave.getSession("GCMnotification", this);
        try {
            if (dialogMessage != null && !dialogMessage.trim().equals("")) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the activity comes to onresume state.
     * Check whether driver is logged in or not
     * if id is empty userlogin activity is called.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (MainActivityDriver.this != null) {

            DriverNetworkStatus.isOnline(MainActivityDriver.this);
            if (DriverSessionSave.getSession("user_privacy_policy", MainActivityDriver.this).equals("")) {
//                if (!((this instanceof DriverDevicePermissionActivityDriver) || (this instanceof DriverSplashAct) || (this instanceof DriverStoreAndSecureActivityDriver))) {
//                    Intent i = new Intent(MainActivityDriver.this, DriverDevicePermissionActivityDriver.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(i);
//                }
//            } else {
                if (DriverSessionSave.getSession("Id", MainActivityDriver.this).trim().equals("")) {
                    if (!((this instanceof DriverUserLoginAct) || (this instanceof DriverSplashAct))) {
                        Intent i = new Intent(MainActivityDriver.this, DriverUserLoginAct.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                }
            }
        }


    }

    /**
     * Custom alert dialog used in entire project.can call from anywhere with the following param Context,title,message,success and failure button text.
     */
    //not used...
    public void alert_view(Context mContext, String title, String message, String success_txt, String failure_txt) {
        if (alertDialog != null)
            if (alertDialog.isShowing())
                alertDialog.dismiss();

        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//        final View view = View.inflate(mContext, R.layout.driver_alert_view, null);
//
//        alertDialog = new Dialog(mContext, R.style.NewDialog);
//        alertDialog.setContentView(view);
//        alertDialog.setCancelable(true);
//        DriverFontHelper.applyFont(mContext, alertDialog.findViewById(R.id.alert_id));
//        //DirverColorchange.ChangeColor(alertDialog.findViewById(R.id.alert_id), mContext);
//
//        alertDialog.show();
//        final TextView title_text = alertDialog.findViewById(R.id.title_text);
//        final TextView message_text = alertDialog.findViewById(R.id.message_text);
//        final Button button_success = alertDialog.findViewById(R.id.button_success);
//        final Button button_failure = alertDialog.findViewById(R.id.button_failure);
//        button_failure.setVisibility(View.GONE);
//        title_text.setText(title);
//        message_text.setText(message);
//        button_success.setText(success_txt);
//        button_success.setOnClickListener(v -> {
//            alertDialog.dismiss();
//        });

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(networkStatus);
        unregisterReceiver(gpsStatus);
        if (dialog1 != null)
            Driver_Utils.closeDialog(dialog1);
        super.onDestroy();
    }

    /**
     * Cancel dialog Loading
     */
    public void cancelLoading() {
        try {
            if (mshowDialog != null)
                if (mshowDialog.isShowing() && MainActivityDriver.this != null)
                    mshowDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This is method to validate the field like Mail,Password,Name,Salutation etc and show the appropriate alert message.
     *
     * @param con              -context
     * @param VA               -validation action element
     * @param stringtovalidate -String to validate
     */
    public boolean validations(ValidateAction VA, AppCompatActivity con, String stringtovalidate) {

        String message = "";
        boolean result = false;
        switch (VA) {
            case isValueNULL:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_mobile_number);
                else
                    result = true;
                break;
            case isValidPassword:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_password);
                else if (stringtovalidate.length() < 5)
                    message = "" + DriverNC.getResources().getString(R.string.pwd_min);
                else if (stringtovalidate.length() > 32)
                    message = "" + DriverNC.getResources().getString(R.string.s_pass_max);
                else
                    result = true;
                break;
            case isValidFirstname:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_first_name);
                else
                    result = true;
                break;
            case isValidLastname:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_last_name);
                else
                    result = true;
                break;
            case isValidCard:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_card_number);
                else if (stringtovalidate.length() < 9 || stringtovalidate.length() > 16)
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_valid_card_number);
                else
                    result = true;
                break;
            case isValidExpiry:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_expiry_date);
                else
                    break;
            case isValidMail:


                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_email);
                else if (!validdmail(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_valid_email);
                else
                    result = true;
                break;
            case isValidConfirmPassword:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + DriverNC.getResources().getString(R.string.enter_the_confirmation_password);
                else
                    result = true;
                break;
        }
        if (!message.equals("")) {
            Toast.makeText(con,"" + message, Toast.LENGTH_LONG).show();
//            dialog1 = Driver_Utils.alert_view(con, "" + DriverNC.getResources().getString(R.string.message), "" + message, "" + DriverNC.getResources().getString(R.string.ok), "", true, MainActivityDriver.this, "2");
        }
        return result;
    }

    /**
     * Called when activity is
     * Close all dialog to avoid memory leakage error
     */
    @Override
    protected void onStop() {
        Driver_Utils.closeDialog(mgpsDialog);
        Driver_Utils.closeDialog(mshowDialog);
        super.onStop();
    }

    /**
     * Enum class for validation
     */
    public enum ValidateAction {
        NONE, isValueNULL, isValidPassword, isValidSalutation, isValidFirstname, isValidLastname, isValidCard, isValidExpiry, isValidMail, isValidConfirmPassword
    }

    /**
     * This is class for logout API call and process the response
     * Clear their current session.
     */
    private class Logout implements DriverAPIResult {
        public Logout(String url, JSONObject data) {

            DriverSystems.out.println("" + url);
            DriverSystems.out.println("" + data);
            if (isOnline()) {
                if (nonactiityobj != null) {
                    nonactiityobj.stopServicefromNonActivity(MainActivityDriver.this);
                }
                new DriverAPIService_Retrofit_JSON(MainActivityDriver.this, this, data, false).execute(url);
            } else {
                Toast.makeText(MainActivityDriver.this,"" +  DriverNC.getResources().getString(R.string.please_check_internet), Toast.LENGTH_LONG).show();
//                dialog1 = Driver_Utils.alert_view(MainActivityDriver.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.please_check_internet), "" + DriverNC.getResources().getString(R.string.ok), "", true, MainActivityDriver.this, "2");
            }
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {

            if (isSuccess) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        Intent locationService = new Intent(MainActivityDriver.this, LocationUpdate.class);
                        stopService(new Intent(locationService));
                        clearsession(MainActivityDriver.this);

//                        dialog1 = Driver_Utils.alert_view_dialog(MainActivityDriver.this, DriverNC.getResources().getString(R.string.message), json.getString("message"), DriverNC.getResources().getString(R.string.ok), "", false, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
                                int length = DriverCommonData.mActivitylist.size();
                                if (length != 0) {
                                    for (int i = 0; i < length; i++) {
                                        DriverCommonData.mActivitylist.get(i).finish();
                                    }
                                }
//                                dialog.dismiss();
                                Intent intent = new Intent(MainActivityDriver.this, DriverUserLoginAct.class);
                                startActivity(intent);
                                finish();
//                            }
//                        }, (dialog, which) -> dialog.dismiss(), "");
                        Toast.makeText(MainActivityDriver.this,"" + json.getString("message"), Toast.LENGTH_LONG).show();
                    } else if (json.getInt("status") == -4) {
                        if (json.has("trip_id")) {
                            if (nonactiityobj != null) {
                                nonactiityobj.startServicefromNonActivity(MainActivityDriver.this);
                            }
                            DriverSessionSave.saveSession("trip_id", json.getString("trip_id"), MainActivityDriver.this);
                            Toast.makeText(MainActivityDriver.this,"" + json.getString("message"), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(MainActivityDriver.this, DriverOngoingAct.class);
                            startActivity(i);
//                            dialog1 = Driver_Utils.alert_view(MainActivityDriver.this, DriverNC.getResources().getString(R.string.message), json.getString("message"), DriverNC.getResources().getString(R.string.ok), "", true, MainActivityDriver.this, "3");
                        }
                    } else {
                        if (nonactiityobj != null) {
                            nonactiityobj.startServicefromNonActivity(MainActivityDriver.this);
                        }
                        Toast.makeText(MainActivityDriver.this,"" + json.getString("message"), Toast.LENGTH_LONG).show();
//                        dialog1 = Driver_Utils.alert_view(MainActivityDriver.this, "" + DriverNC.getResources().getString(R.string.message), "" + json.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "", true, MainActivityDriver.this, "2");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(() -> ShowToast(MainActivityDriver.this, DriverNC.getString(R.string.server_error)));
            }
        }
    }


}