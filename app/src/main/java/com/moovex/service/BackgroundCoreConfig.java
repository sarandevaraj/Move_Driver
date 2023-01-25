package com.moovex.service;

import android.app.Dialog;
import android.app.IntentService;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import com.moovex.R;
import com.moovex.driver.DriverUserLoginAct;
import com.moovex.driver.data.DriverCommonData;
import com.moovex.driver.interfaces.DriverAPIResult;
import com.moovex.driver.utils.DriverCL;
import com.moovex.driver.utils.DriverCToast;
import com.moovex.driver.utils.DriverNC;
import com.moovex.driver.utils.DriverSessionSave;
import com.moovex.driver.utils.DriverSystems;
import com.moovex.util.AppController;
import com.moovex.util.SessionSave;
import com.moovex.util.TaxiUtil;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import androidx.annotation.Nullable;

import static com.moovex.util.ConstantsKt.API_BASE;
import static com.moovex.util.ConstantsKt.DEFAULT_CITY_NAME;
import static com.moovex.util.ConstantsKt.IS_BUISNESS_KEY;
import static com.moovex.util.ConstantsKt.LANG;
import static com.moovex.util.ConstantsKt.MODEL_DETAILS;
import static com.moovex.util.ConstantsKt.PASS_ID;
import static com.moovex.util.ConstantsKt.PASS_PAYMENT_OPTION;
import static com.moovex.util.ConstantsKt.SERVICE_DETAILS;

/**
 * Created by product team on 22/2/18.
 */

public class BackgroundCoreConfig extends IntentService {

    public static ArrayList<String> fields = new ArrayList<>();
    public static ArrayList<String> fields_value = new ArrayList<>();
    public static HashMap<String, Integer> fields_id = new HashMap<>();
    public Intent currentIntent;
    private Dialog loadingDialog, errorDialog;
    private long getCore_Utc;
    private String getCoreLangTime;
    private String getCoreColorTime;

    public BackgroundCoreConfig() {
        super("BackgroundCoreConfig");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        currentIntent = intent;
        DriverSystems.out.println("_callingrrrrgeee3");
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        TaxiUtil.API_BASE_URL = SessionSave.getSession("base_url", BackgroundCoreConfig.this);
        SessionSave.saveSession("auth_last_call_type", ts, BackgroundCoreConfig.this);

        String url = "type=getcoreconfig";
        new CoreConfigCall(url);
    }

    public void errorInSplash(String message) {

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
        }

        for (Map.Entry<String, String> entry : DriverNC.nfields_byName.entrySet()) {
            String h = entry.getKey();
            String value = entry.getValue();
            DriverNC.nfields_byID.put(fields_id.get(h), DriverNC.nfields_byName.get(h));
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
                }
            }
            getColorValueDetail();
        } catch (Exception e) {
            e.printStackTrace();
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
        }

        for (Map.Entry<String, String> entry : DriverCL.nfields_byName.entrySet()) {
            String h = entry.getKey();
            String value = entry.getValue();
            DriverCL.nfields_byID.put(DriverCL.fields_id.get(h), DriverCL.nfields_byName.get(h));
        }
    }

    private class CoreConfigCall implements DriverAPIResult {
        public CoreConfigCall(final String url) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON_NoProgress(BackgroundCoreConfig.this, this, "", true).execute("type=getcoreconfig");
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub

            if (isSuccess) {
                try {
                    DriverSystems.out.println("_callingrrrrgeee");
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        if (json.has("gt_lst_time"))
                            SessionSave.saveSession(TaxiUtil.GETCORE_LASTUPDATE, json.getString("gt_lst_time"), BackgroundCoreConfig.this);
                        final JSONArray array = json.getJSONArray("detail");

                        if (array.getJSONObject(0).has("customer_wallet_transaction")) {
                            SessionSave.saveSession("customer_wallet_transaction", array.getJSONObject(0).getString("customer_wallet_transaction"), BackgroundCoreConfig.this);
                            System.out.println("customer_wallet_transaction check " + SessionSave.getSession("customer_wallet_transaction", BackgroundCoreConfig.this));
                        }
                        if (array.getJSONObject(0).has("is_driver_auto_accept")) {
                            DriverSessionSave.saveSession("is_driver_auto_accept", array.getJSONObject(0).getString("is_driver_auto_accept"), BackgroundCoreConfig.this);
                        }

                        if (array.getJSONObject(0).has("is_enabled_ive_arrived")) {
                            SessionSave.saveSession("is_enabled_ive_arrived", array.getJSONObject(0).getString("is_enabled_ive_arrived"), BackgroundCoreConfig.this);
                            System.out.println("is_enabled_ive_arrived check " + SessionSave.getSession("is_enabled_ive_arrived", BackgroundCoreConfig.this));
                        }

                        if (array.getJSONObject(0).has(TaxiUtil.KM_RESTRICT))
                            SessionSave.saveSession(TaxiUtil.KM_RESTRICT, array.getJSONObject(0).getString(TaxiUtil.KM_RESTRICT), BackgroundCoreConfig.this);

                        if (array.getJSONObject(0).has(TaxiUtil.SKIP_PASSENGER_EMAIL))
                            SessionSave.saveSession(TaxiUtil.SKIP_PASSENGER_EMAIL, array.getJSONObject(0).getString(TaxiUtil.SKIP_PASSENGER_EMAIL).equals("1"), BackgroundCoreConfig.this);
                        else
                            SessionSave.saveSession(TaxiUtil.SKIP_PASSENGER_EMAIL, false, BackgroundCoreConfig.this);

                        SessionSave.saveSession(TaxiUtil.BALANCE_CREDIT_OPTION, array.getJSONObject(0).optString(TaxiUtil.BALANCE_CREDIT_OPTION, "1"), BackgroundCoreConfig.this);

                        SessionSave.saveSession(TaxiUtil.PASSENGER_TIPS_ENABLE, array.getJSONObject(0).optString(TaxiUtil.PASSENGER_TIPS_ENABLE, "1"), BackgroundCoreConfig.this);

                        if (array.getJSONObject(0).has(TaxiUtil.IS_STOP_ENABLED))
                            SessionSave.saveSession(TaxiUtil.IS_STOP_ENABLED, array.getJSONObject(0).getString(TaxiUtil.IS_STOP_ENABLED).equals("1"), BackgroundCoreConfig.this);
                        else
                            SessionSave.saveSession(TaxiUtil.IS_STOP_ENABLED, false, BackgroundCoreConfig.this);

                        if (array.getJSONObject(0).has(TaxiUtil.PASSENGER_GRACE_TIME))
                            SessionSave.saveSession(TaxiUtil.PASSENGER_GRACE_TIME, array.getJSONObject(0).getString(TaxiUtil.PASSENGER_GRACE_TIME), BackgroundCoreConfig.this);

                        SessionSave.saveSession(TaxiUtil.NODE_DOMAIN, json.getString("mobile_socket_http_domain"), BackgroundCoreConfig.this);

                        if (json.has("https_node_url")) {
                            SessionSave.saveSession(TaxiUtil.NODE_URL, json.getString("https_node_url"), BackgroundCoreConfig.this);
                            SessionSave.saveSession(DriverCommonData.DRIVER_NODE_URL, json.getString("https_node_url"), BackgroundCoreConfig.this);
                        } else {
                            SessionSave.saveSession(TaxiUtil.NODE_URL, json.getString("mobile_socket_http_url"), BackgroundCoreConfig.this);
                            SessionSave.saveSession(DriverCommonData.DRIVER_NODE_URL, json.getString("mobile_socket_http_url"), BackgroundCoreConfig.this);
                        }

                        if (array.getJSONObject(0).has("is_run_golang") && array.getJSONObject(0).getString("is_run_golang").equals("true") && array.getJSONObject(0).has("mobile_golang_nearest_url")) {
                            SessionSave.saveSession(TaxiUtil.RUN_GO_LANG, array.getJSONObject(0).getString("is_run_golang"), BackgroundCoreConfig.this);
                            SessionSave.saveSession(TaxiUtil.NODE_URL, array.getJSONObject(0).getString("mobile_golang_nearest_url"), BackgroundCoreConfig.this);
                        }

                        if (array.getJSONObject(0).has("is_dlh_golang") && array.getJSONObject(0).getString("is_dlh_golang").equals("true") && array.getJSONObject(0).has("mobile_golang_url")) {
                            SessionSave.saveSession(DriverCommonData.RUN_GO_LANG, array.getJSONObject(0).getString("is_dlh_golang"), BackgroundCoreConfig.this);
                            SessionSave.saveSession(DriverCommonData.DRIVER_NODE_URL, array.getJSONObject(0).getString("mobile_golang_url"), BackgroundCoreConfig.this);
//                            SessionSave.saveSession("driver_node_url", json.getString("mobile_socket_http_url"), BackgroundCoreConfig.this);
                        }

                        if (json.has("chat_node_url")) {
                            SessionSave.saveSession(TaxiUtil.CHAT_NODE_URL, json.getString("chat_node_url"), BackgroundCoreConfig.this);
                        }
                        if (json.has("call_masking_enable")) {
                            SessionSave.saveSession(TaxiUtil.CALL_MASKING_ENABLE, json.getString("call_masking_enable"), BackgroundCoreConfig.this);
                        }

                        if (json.has("dispatcher_phone_number")) {
                            SessionSave.saveSession("dispatcher_phone_number", json.getString("dispatcher_phone_number"), BackgroundCoreConfig.this);
                        }

                        if (array.getJSONObject(0).has("pickupsuggestion_url"))
                            SessionSave.saveSession("pickupsuggestion_url", array.getJSONObject(0).getString("pickupsuggestion_url"), BackgroundCoreConfig.this);
                        if (array.getJSONObject(0).has("pickupsuggestion"))
                            SessionSave.saveSession("pickupsuggestion", array.getJSONObject(0).getString("pickupsuggestion"), BackgroundCoreConfig.this);
                        if (array.getJSONObject(0).has("sos_setting")) {
                            SessionSave.saveSession(TaxiUtil.sosEnable, array.getJSONObject(0).getString("sos_setting").equals("1"), BackgroundCoreConfig.this);
                        }
                        SessionSave.saveSession("play_store_version", array.getJSONObject(0).getString("android_passenger_version"), BackgroundCoreConfig.this);

                        if (array.getJSONObject(0).has("last_forceupdate_version"))
                            SessionSave.saveSession(TaxiUtil.LAST_FORCEUPDATE_VERSION, array.getJSONObject(0).getString("last_forceupdate_version"), BackgroundCoreConfig.this);
                        else
                            SessionSave.saveSession(TaxiUtil.LAST_FORCEUPDATE_VERSION, "0", BackgroundCoreConfig.this);

                        if (array.getJSONObject(0).has("rental_out_availability"))
                            SessionSave.saveSession(TaxiUtil.RENTAL_OUTSTATION_AVAILABLE, array.getJSONObject(0).getString("rental_out_availability").equals("1"), BackgroundCoreConfig.this);
                        else
                            SessionSave.saveSession(TaxiUtil.RENTAL_OUTSTATION_AVAILABLE, "", BackgroundCoreConfig.this);

                        SessionSave.saveSession("tax", array.getJSONObject(0).getString("tax"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("facebook_share", array.getJSONObject(0).getString("facebook_share"), BackgroundCoreConfig.this);
                        // SessionSave.saveSession("facebook_share", "https://www.facebook.com/adropapp", BackgroundCoreConfig.this);

                        SessionSave.saveSession("twitter_share", array.getJSONObject(0).getString("twitter_share"), BackgroundCoreConfig.this);

                        //SessionSave.saveSession("twitter_share", "https://twitter.com/adropapp", BackgroundCoreConfig.this);

                        SessionSave.saveSession("About", array.getJSONObject(0).getString("aboutpage_description"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("Currency", array.getJSONObject(0).getString("site_currency") + " ", BackgroundCoreConfig.this);
                        SessionSave.saveSession("AdminMail", array.getJSONObject(0).getString("admin_email"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("TellfrdMsg", array.getJSONObject(0).getString("share_content"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("ShaerMsg", array.getJSONObject(0).getString("tell_to_friend_subject"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("Metric", array.getJSONObject(0).getString("metric"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("country_code", array.getJSONObject(0).getString("country_code"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("android_web_key", array.getJSONObject(0).getString("android_google_api_key"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("country_iso_code", array.getJSONObject(0).getString("country_iso_code"), BackgroundCoreConfig.this);
                        try {
                            SessionSave.saveSession("default_city_id", array.getJSONObject(0).getString("default_city_id"), BackgroundCoreConfig.this);
                            SessionSave.saveSession(DEFAULT_CITY_NAME, array.getJSONObject(0).getString("default_city_name"), BackgroundCoreConfig.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SessionSave.saveSession("android_foursquare_api_key", array.getJSONObject(0).getString("android_foursquare_api_key"), BackgroundCoreConfig.this);

                        SessionSave.saveSession(IS_BUISNESS_KEY, array.getJSONObject(0).getString("google_business_key").equals("1"), BackgroundCoreConfig.this);

                        if (SessionSave.getSession("Metric", BackgroundCoreConfig.this).equalsIgnoreCase("MILES"))
                            SessionSave.saveSession("Metric_type", "m", BackgroundCoreConfig.this);
                        else if (SessionSave.getSession("Metric", BackgroundCoreConfig.this).equalsIgnoreCase("KM"))
                            SessionSave.saveSession("Metric_type", "k", BackgroundCoreConfig.this);
                        else SessionSave.saveSession("Metric_type", "k", BackgroundCoreConfig.this);

                        if (array.getJSONObject(0).has("sos_msg"))
                            SessionSave.saveSession("sos_message", array.getJSONObject(0).getString("sos_msg"), BackgroundCoreConfig.this);

                        if (array.getJSONObject(0).has("playstore_passenger"))
                            SessionSave.saveSession(TaxiUtil.PLAY_STORE_LINK, array.getJSONObject(0).getString("playstore_passenger"), BackgroundCoreConfig.this);

                        SessionSave.saveSession(API_BASE, array.getJSONObject(0).getString("api_base"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("logo_base", array.getJSONObject(0).getString("logo_base"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("site_logo", array.getJSONObject(0).getString("site_logo"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("Cancellation_setting", array.getJSONObject(0).getString("cancellation_setting"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("facebook_key", array.getJSONObject(0).getString("facebook_key"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("skip_credit", array.getJSONObject(0).getString("skip_credit"), BackgroundCoreConfig.this);
                        SessionSave.saveSession(MODEL_DETAILS, array.getJSONObject(0).getString("model_details"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("referral_code_info", array.getJSONObject(0).getString("referral_code_info"), BackgroundCoreConfig.this);

                        SessionSave.saveSession("referral_settings", array.getJSONObject(0).getString("referral_settings"), BackgroundCoreConfig.this);
                        SessionSave.saveSession("referral_settings_message", array.getJSONObject(0).getString("referral_settings_message"), BackgroundCoreConfig.this);

                        SessionSave.saveSession(PASS_PAYMENT_OPTION, array.getJSONObject(0).getString("passenger_payment_option"), BackgroundCoreConfig.this);

                        String googleApiKey = array.getJSONObject(0).getString("android_google_api_key");
                        if (!getString(R.string.googleID).equals(googleApiKey)) {
                            AppController.getInstance().setPlaceApiKey(googleApiKey);
                        }

                        SessionSave.saveSession(TaxiUtil.GOOGLE_KEY, googleApiKey, BackgroundCoreConfig.this);
                        SessionSave.saveSession(SERVICE_DETAILS, array.getJSONObject(0).getString("service_details"), BackgroundCoreConfig.this);

                        if (array.getJSONObject(0).has("android_mapbox_key")) {
                            SessionSave.saveSession(TaxiUtil.MAP_BOX_TOKEN, array.getJSONObject(0).getString("android_mapbox_key"), BackgroundCoreConfig.this);
                        } else {
                            SessionSave.saveSession(TaxiUtil.MAP_BOX_TOKEN, "pk.eyJ1IjoibmFuZGhpbmlzIiwiYSI6ImNqaGl0M3U0aDI5MXczYW8xZGY3bmxod3gifQ.CsQZTI8nf5ZDh8ES3Iu87g", BackgroundCoreConfig.this);
                        }
                        if (array.getJSONObject(0).has("android_local_map_enable")) {
                            SessionSave.saveSession(TaxiUtil.LOCAL_STORAGE, array.getJSONObject(0).getString("android_local_map_enable").equals("1"), BackgroundCoreConfig.this);
                        } else {
                            SessionSave.saveSession(TaxiUtil.LOCAL_STORAGE, false, BackgroundCoreConfig.this);
                        }
//                        if (!SessionSave.getSession(TaxiUtil.MAP_BOX_TOKEN, BackgroundCoreConfig.this).equals(""))
//                            Mapbox.getInstance(BackgroundCoreConfig.this, SessionSave.getSession(TaxiUtil.MAP_BOX_TOKEN, BackgroundCoreConfig.this));

                        if (array.getJSONObject(0).has("map_settings") && array.getJSONObject(0).getJSONObject("map_settings").has("is_google_distance")) {
                            SessionSave.saveSession(TaxiUtil.isGoogleDistance, array.getJSONObject(0).getJSONObject("map_settings").getString("is_google_distance").equals("1"), BackgroundCoreConfig.this);
                            SessionSave.saveSession(TaxiUtil.isGoogleRouteGeo, array.getJSONObject(0).getJSONObject("map_settings").getString("is_google_direction").equals("1"), BackgroundCoreConfig.this);
                            SessionSave.saveSession(TaxiUtil.isGoogleGeocoder, array.getJSONObject(0).getJSONObject("map_settings").getString("is_google_geocode").equals("1"), BackgroundCoreConfig.this);
                            SessionSave.saveSession(TaxiUtil.isNeedtoDrawRoute, array.getJSONObject(0).getJSONObject("map_settings").getString("enable_route").equals("1"), BackgroundCoreConfig.this);
                            SessionSave.saveSession(TaxiUtil.isNeedtoFetchAddress, array.getJSONObject(0).getJSONObject("map_settings").getString("display_current_location").equals("1"), BackgroundCoreConfig.this);
                        } else {
                            SessionSave.saveSession(TaxiUtil.isGoogleDistance, true, BackgroundCoreConfig.this);
                            SessionSave.saveSession(TaxiUtil.isGoogleRouteGeo, true, BackgroundCoreConfig.this);
                            SessionSave.saveSession(TaxiUtil.isGoogleGeocoder, true, BackgroundCoreConfig.this);
                            SessionSave.saveSession(TaxiUtil.isNeedtoDrawRoute, true, BackgroundCoreConfig.this);
                            SessionSave.saveSession(TaxiUtil.isNeedtoFetchAddress, true, BackgroundCoreConfig.this);
                        }

                        JSONArray jsonarray = new JSONArray(array.getJSONObject(0).getString("passenger_payment_option"));
                        SessionSave.saveSession("pay_mod_name", jsonarray.getJSONObject(0).getString("pay_mod_name"), BackgroundCoreConfig.this);

                        getCore_Utc = array.getJSONObject(0).getLong("utc_time");
                        SessionSave.saveSession("current_time", getCore_Utc, BackgroundCoreConfig.this);
                        SessionSave.saveSession("current_time_local", array.getJSONObject(0).getLong("current_time"), BackgroundCoreConfig.this);
                        boolean deflanAvail = false;
                        try {
                            getCoreLangTime = json.getJSONObject("language_color_status").getString("android_passenger_language");
                            getCoreColorTime = json.getJSONObject("language_color_status").getString("android_passenger_colorcode");
                            SessionSave.saveSession("isFourSquare", array.getJSONObject(0).getString("android_foursquare_status"), BackgroundCoreConfig.this);
                            String totalLanguage = "";
                            JSONArray pArray = json.getJSONObject("language_color").getJSONObject("android").getJSONArray("passenger_language");
                            for (int i = 0; i < pArray.length(); i++) {
                                String key_ = "";
                                totalLanguage += pArray.getJSONObject(i).getString("language").replaceAll(".xml", "") + "____";
                                SessionSave.saveSession("LANG" + i, pArray.getJSONObject(i).getString("language"), BackgroundCoreConfig.this);
                                SessionSave.saveSession("LANGTemp" + i, pArray.getJSONObject(i).getString("design_type"), BackgroundCoreConfig.this);
                                SessionSave.saveSession("LANGCode" + i, pArray.getJSONObject(i).getString("language_code"), BackgroundCoreConfig.this);
                                SessionSave.saveSession(pArray.getJSONObject(i).getString("language"), pArray.getJSONObject(i).getString("url"), BackgroundCoreConfig.this);
                                if (!SessionSave.getSession("LANGDef", BackgroundCoreConfig.this).equals("") && pArray.getJSONObject(i).getString("language").contains(SessionSave.getSession("LANGDef", BackgroundCoreConfig.this))) {
                                    deflanAvail = true;
                                }
                            }
                            DriverSystems.out.println("___________defff" + deflanAvail);
                            if (SessionSave.getSession("LANGDef", BackgroundCoreConfig.this).trim().equals("") || !deflanAvail) {
                                SessionSave.saveSession("LANGDef", SessionSave.getSession("LANG0", BackgroundCoreConfig.this), BackgroundCoreConfig.this);
                                SessionSave.saveSession("LANGTempDef", SessionSave.getSession("LANGTemp0", BackgroundCoreConfig.this), BackgroundCoreConfig.this);
                                SessionSave.saveSession(LANG, pArray.getJSONObject(0).getString("language_code").replaceAll(".xml", ""), BackgroundCoreConfig.this);
                                String url = SessionSave.getSession(SessionSave.getSession("LANG" + 0, BackgroundCoreConfig.this), BackgroundCoreConfig.this);
                                SessionSave.saveSession("currentStringUrl", url, BackgroundCoreConfig.this);
                            }
                            SessionSave.saveSession("lang_json", totalLanguage, BackgroundCoreConfig.this);
                            SessionSave.saveSession("colorcode", json.getJSONObject("language_color").getJSONObject("android").getString("colorcode"), BackgroundCoreConfig.this);
                        } catch (JSONException e) {
                            errorInSplash(DriverNC.getString(R.string.server_con_error));
                            e.printStackTrace();
                        }
                        //android_passenger_language
                        if (!SessionSave.getSession(TaxiUtil.PASSENGER_LANGUAGE_TIME, BackgroundCoreConfig.this).trim().equals(getCoreLangTime)) {
                            DriverSystems.out.println("___________defffcs");
                            new callString(getCoreColorTime);
                        } else if (!SessionSave.getSession(TaxiUtil.PASSENGER_COLOR_TIME, BackgroundCoreConfig.this).trim().equals(getCoreColorTime)) {
                            new callColor(getCoreLangTime);
                            DriverSystems.out.println("___________defffcc");
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

    private class callString implements DriverAPIResult {
        String color_time;

        public callString(final String color_time) {
            // TODO Auto-generated constructor stub
            this.color_time = color_time;

            String urls = SessionSave.getSession("currentStringUrl", BackgroundCoreConfig.this);
            Log.e("link__lang", urls + "___" + SessionSave.getSession("currentStringUrl", BackgroundCoreConfig.this));
            if (urls.equals("")) {
                urls = SessionSave.getSession(SessionSave.getSession("LANGDef", BackgroundCoreConfig.this), BackgroundCoreConfig.this);
                if (SessionSave.getSession("LANGTempDef", BackgroundCoreConfig.this).trim().equalsIgnoreCase("RTL")) {
                    SessionSave.saveSession("Lang_Country", "ar_EG", BackgroundCoreConfig.this);
                    SessionSave.saveSession(LANG, "ar", BackgroundCoreConfig.this);
                    Configuration config = new Configuration();
                    String langcountry = SessionSave.getSession("Lang_Country", BackgroundCoreConfig.this);
                    String[] arry = langcountry.split("_");
                    config.locale = new Locale(arry[0], arry[1]);
                    Locale.setDefault(new Locale(arry[0], arry[1]));
                }
            }
            new APIService_Retrofit_JSON_NoProgress(BackgroundCoreConfig.this, this, null, true, urls, true).execute();
        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            if (isSuccess) {
                SessionSave.saveSession(TaxiUtil.PASSENGER_LANGUAGE_TIME, getCoreLangTime, BackgroundCoreConfig.this);
                getAndStoreStringValues(result);
                SessionSave.saveSession("wholekey", result, BackgroundCoreConfig.this);
                if (SessionSave.getSession("wholekeyColor", BackgroundCoreConfig.this).trim().equals("") || !SessionSave.getSession(TaxiUtil.PASSENGER_COLOR_TIME, BackgroundCoreConfig.this).equals(color_time))
                    new callColor("");
            } else errorInSplash(DriverNC.getString(R.string.server_con_error));
        }
    }

    private class callColor implements DriverAPIResult {
        public callColor(final String url) {
            // TODO Auto-generated constructor stub

            new APIService_Retrofit_JSON_NoProgress(BackgroundCoreConfig.this, this, null, true, SessionSave.getSession("colorcode", BackgroundCoreConfig.this), true).execute();
            Log.e("link__color", SessionSave.getSession("colorcode", BackgroundCoreConfig.this));
        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            if (isSuccess) {
                SessionSave.saveSession(TaxiUtil.PASSENGER_COLOR_TIME, getCoreColorTime, BackgroundCoreConfig.this);
                getAndStoreColorValues(result);
                SessionSave.saveSession("wholekeyColor", result, BackgroundCoreConfig.this);
            } else errorInSplash(DriverNC.getString(R.string.server_con_error));
        }
    }

    /**
     * Method to logout user if status -101 and redirect to login page
     *
     * @param message - To intimate user by showing alert message
     */
    private void forceLogout(String message) {
        DriverCToast.ShowToast(BackgroundCoreConfig.this, message);
        TaxiUtil.API_BASE_URL = "";
        SessionSave.saveSession("base_url", "", BackgroundCoreConfig.this);
        SessionSave.saveSession(PASS_ID, "", BackgroundCoreConfig.this);
        SessionSave.clearAllSession(BackgroundCoreConfig.this);
        startActivity(new Intent(BackgroundCoreConfig.this, DriverUserLoginAct.class));
        stopSelf();
    }
}
