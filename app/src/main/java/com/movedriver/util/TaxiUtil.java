package com.movedriver.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
//import com.taximobility.Login.LoginActivity;
//import com.taximobility.bookingmodule.BookTaxiHomeRepository;
//import com.taximobility.data.DriverData;
//import com.taximobility.data.FavouriteData;
//import com.taximobility.data.FavouriteDriverData;
//import com.taximobility.data.HelpData;
//import com.taximobility.data.SplitStatusData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.movedriver.util.ConstantsKt.LOGOUT;
import static com.movedriver.util.ConstantsKt.PASS_API;
import static com.movedriver.util.ConstantsKt.PASS_ID;
import static com.movedriver.util.ConstantsKt.PASS_TRIP_ID;

/**
 * this class is used to put common methods and variables for reuse in whole project
 */

public class TaxiUtil {
    public static final String PREFERENCE_LIST = "Preference_list";
    public static final String PROMO_LIST = "promo_list";
    public static final String LOCAL_STORAGE = "local_storage";
    public static final String NEED_TO_COMPLETE_CARD_REG = "card_need_to_reg";
    public static final String PLAY_STORE_LINK = "play_store_link";
    public static final String CURRENT_TRIP = "current_trip_id";
    public static final String GETCORE_LASTUPDATE = "getcore_lastupdate";
    public static final String ACCESS_KEY = "access_keyy";
    public static Context mContext;
    public static String API_BASE_URL = "";
    public static final String COMPANY_KEY = "";
    public static final String DYNAMIC_AUTH_KEY = "";
    public static final String LAST_FORCEUPDATE_VERSION = "last_forceupdate_version";
    public static final String isGoogleDistance = "isgoogle_distance";
    public static String isGoogleRouteGeo = "isGoogleRouteGeo";
    public static String isGoogleGeocoder = "isGoogleGeocoder";
    public static String isNeedtoFetchAddress = "isNeedtoFetchAddress";
    public static String isNeedtoDrawRoute = "isNeedtoDrawRoute";
    public static String isNeedtoShowFare = "isNeedtoShowFare";
    public static int isGoogleGeocode = 1;
    public static final String MAP_BOX_TOKEN = "MAP_BOX_TOKEN";
    public static final String GOOGLE_KEY = "MAP_KEY";
    public static final String sosEnable = "sos_enable";
    public static final String SKIP_PASSENGER_EMAIL = "skip_passenger_email";
    public static final String BALANCE_CREDIT_OPTION = "balance_credit_option";
    public static final String PASSENGER_TIPS_ENABLE = "passenger_tips_enable";
    public static final String HIDE_OTP = "sms_status";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String IS_STOP_ENABLED = "stops_enable";
    public static final String KM_RESTRICT = "km_restrict_val";
    public static final String USER_WALLET_AMOUNT = "wallet_amount";
    public static final String CANCELLATION_FARE_APPLICABLE = "cancelfare_applicable";
    public static final String SKIP_PAST_BOOKING = "skip_past_booking";
    public static final String SOS_NAME = "u_name";
    public static final String INTERNET_AVAIL = "internet_available";
    public static final String PASSENGER_GRACE_TIME = "passenger_grace_time";
    public static final String PASSENGER_TRIP_TIME = "Trip_time";
    public static final String RENTAL_OUTSTATION_AVAILABLE = "rental_out_availability";
    public static String USER_PRIVACY_POLICY = "user_privacy_policy";
    public static ArrayList<Activity> mActivitylist = new ArrayList<Activity>();
//    public static ArrayList<SplitStatusData> SPLIT_STATUS_ITEM = new ArrayList<>();
    public static int close = 0;
//    public static ArrayList<DriverData> mDrivermovementdata = new ArrayList<>();
    public static String mDevice_id = "";
//    public static ArrayList<FavouriteData> mFavouritelist = new ArrayList<FavouriteData>();
//    public static ArrayList<HelpData> mHelplist = new ArrayList<HelpData>();
    public static String Address = "";
    public static double Latitude;
    public static double Longitude;
//    public static ArrayList<DriverData> mDriverdata = new ArrayList<DriverData>();
//    public static ArrayList<FavouriteDriverData> mFavouriteDriverlist = new ArrayList<FavouriteDriverData>();
    public static double p_lat, p_lng;
    public static int LocationResult = 420;
    public static String current_act = "";
    /*
     * Location Utils
     */
    public static final int MILLISECONDS_PER_SECOND = 1000;
    // The update interval
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    // A fast interval ceiling
    public static final int FAST_CEILING_IN_SECONDS = 1;
    // Update interval in milliseconds
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // A fast ceiling of update intervals, used when the app is visible
    public static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * FAST_CEILING_IN_SECONDS;
    public static Context sContext;
    //split on/off
    public static String isSplitOn = "IS_SPLIT";
    public static String isFavDriverOn = "IS_Fav";
    public static String isSkipFavOn = "IS_SKIP_FAV";
    public static String PASSENGER_LANGUAGE_TIME = "PASSENGER_LANG_TIME";
    public static String PASSENGER_COLOR_TIME = "PASSENGER_COLOR_TIME";
    public final static String SERVICE_PACKAGE = "com.adrop.service";
    public final static String ACTIVITY_ACTION = SERVICE_PACKAGE + ".Activity_Action";
    public static final String NODE_URL = "node_url";
    public static final String RUN_GO_LANG = "is_run_golang";
    public static final String CHAT_NODE_URL = "chat_node_url";
    public static final String CALL_MASKING_ENABLE = "call_masking_enable";
    public static final String NODE_TOKEN = "node_token";
    public static final String NODE_DOMAIN = "mobile_socket_http_domain";
    public static final String AUTH_KEY = "auth_key";
    public static final String AUTH_KEY1 = "auth_key";
    public static final String DEVICE_ID = "device_id";
    public static final String COMPANY_DOMAIN = "company_main_domain";
    public static final String DOMAIN_URL = "domain_url";
    public static final String USER_KEY = "user_key";
    public static final String CORPORATE_PASSENGER = "corporate_passenger"; // "1" corporate user "0" normal user
    public static final String CORPORATE_COMPANY_ID = "corporate_company_id";
    public static final String CORPORATE_COMPANY_NAME = "corporate_company_name";
    public static final String CORPORATE_COMPANY_BLOCK = "corporate_company_block"; // block "1" unblock "0"
    public static String mDevice_id_constant = "123456";

    public static <T> T fromJson(String data, Class<T> classn) {
        return new Gson().fromJson(data, classn);
    }

    public static String toString(Object s) {
        return new Gson().toJson(s);
    }

    public static boolean isCurrentTimeZone(long s) {
        long dateInMillis = System.currentTimeMillis() / 1000;
        return TimeUnit.MILLISECONDS.toSeconds(Math.abs(s - dateInMillis)) < 24;
    }

    /**
     * This is method for check the Internet connection
     * <p>
     * <P>
     * This is method for check the Internet connection
     * </p>
     *
     * @return boolean is online
     */
    public static boolean isOnline(Context ctx) {

        mContext = ctx;
        if (mContext != null) {
            ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
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
        }
        return false;
    }

    public static int getPixelsFromDp(final Context context, final float dp) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * This is method for logout from the application
     * <p>
     * <P>
     * This is method for logout from the application
     * </p>
     *
     * @return void clear session
     */
//    private static void logout(Context ctx) {
//
//        mContext = ctx;
//        clearsession(ctx);
//        int length = mActivitylist.size();
//        if (length != 0) {
//            for (int i = 0; i < length; i++) {
//                mActivitylist.get(i).finish();
//            }
//        }
//        Intent i_s = new Intent(mContext, LoginActivity.class);
//        mContext.startActivity(i_s);
//    }

    /**
     * This is method for logout from the application
     * <p>
     * <P>
     * This is method for logout from the application
     * </p>
     *
     * @return void clear session
     */
//    public static class Logout implements APIResult {
//        private CreditCardRepository creditCardRepository;
//        private BookTaxiHomeRepository placesRepository;
//
//        public Logout(String string, Context ctx, JSONObject data) {
//            creditCardRepository = CreditCardRepository.getRepository(ctx);
//            placesRepository = new BookTaxiHomeRepository(ctx);
//            mContext = ctx;
//            new APIService_Retrofit_JSON(mContext, this, data, false).execute(string);
//        }
//
//        @Override
//        public void getResult(boolean isSuccess, String result) {
//            // TODO Auto-generated method stub
//            if (isSuccess) {
//                logout(mContext);
//                try {
//                    JSONObject json = new JSONObject(result);
//                    if (json.getInt("status") == 1) {
//                        SessionSave.saveSession(LOGOUT, "", mContext);
//                        SessionSave.saveSession(TaxiUtil.USER_KEY, "", mContext);
//                        SessionSave.saveSession(TaxiUtil.CORPORATE_PASSENGER, "", mContext);
//                        SessionSave.saveSession(TaxiUtil.CORPORATE_COMPANY_BLOCK, "", mContext);
//                        SessionSave.saveSession(TaxiUtil.CORPORATE_COMPANY_ID, "", mContext);
//                        try {
//
//                            Intent logIn = new Intent(mContext, DriverUserLoginAct.class);
//                            logIn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            mContext.startActivity(logIn);
//                            ((Activity) mContext).finish();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        CToast.ShowToast(mContext, json.getString("message"));
//                        if (creditCardRepository != null)
//                            creditCardRepository.deleteAllCards();
//                        if (placesRepository != null) {
//                            placesRepository.deleteSavedPlaces();
//                        }
//                        SessionSave.saveWalletAmount(0f, mContext);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    private static void clearsession(Context ctx) {

        try {
            SessionSave.saveSession("TaxiStatus", "", ctx);
            SessionSave.saveSession(LOGOUT, "", ctx);
            SessionSave.saveSession(PASS_ID, "", ctx);
            SessionSave.saveSession("service_type_name", "", ctx);
            SessionSave.saveSession("AdminMail", "", ctx);
            SessionSave.saveSession("Email", "", ctx);
            SessionSave.saveSession(PASS_TRIP_ID, "", ctx);
            SessionSave.saveSession("Register", "", ctx);
            SessionSave.saveSession("PLAT", "", ctx);
            SessionSave.saveSession("PLNG", "", ctx);
            SessionSave.saveSession("service_type", "", ctx);
            SessionSave.saveSession("NotifyMessage", "", ctx);
            SessionSave.saveSession("Server_Response", "", ctx);
            SessionSave.saveSession("Server_bookinglist", "", ctx);
            SessionSave.saveSession("trip_id", "", ctx);
            SessionSave.saveSession(PASS_API, "", ctx);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static String tostring(Object s) {
        return new Gson().toJson(s);
    }

    public static String getCurrentTimeForFourSquare() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.UK);
        return df.format(c.getTime());
    }

    /* *//**
     * remove first and last space in a string
     *//*
    public static String removeSpaceFromString(String password) {
        String removeFirstSpaces = "";
        String removeLastSpaces = "";
        try {
            removeFirstSpaces = password.replaceFirst("^\\s*", "");
            removeLastSpaces = removeFirstSpaces.replaceAll("\\s+$", "");
        } catch (Exception e) {
            e.printStackTrace();
            return password;
        }
        return removeLastSpaces;
    }*/
}
