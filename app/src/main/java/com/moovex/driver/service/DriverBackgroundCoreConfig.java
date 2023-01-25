package com.moovex.driver.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.res.Configuration;

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
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import androidx.annotation.Nullable;

/**
 * Created by developer on 22/2/18.
 */

public class DriverBackgroundCoreConfig extends IntentService {
    private long getCore_Utc;
    private String getCoreLangTime;
    private String getCoreColorTime;

    public DriverBackgroundCoreConfig() {
        super("BackgroundCoreConfig");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        DriverSessionSave.saveSession("auth_last_call_type", ts, DriverBackgroundCoreConfig.this);
        if (DriverSessionSave.getSession("wholekey", DriverBackgroundCoreConfig.this).equals(""))
            new callString("");
        else {
            String url = "type=getcoreconfig";
            new CoreConfigCall(url);
        }
    }

    /**
     * Setting Language Configuration
     */
    public void setLocale() {
        if (DriverSessionSave.getSession("Lang", DriverBackgroundCoreConfig.this).equals("")) {
            DriverSessionSave.saveSession("Lang", "en", DriverBackgroundCoreConfig.this);
            DriverSessionSave.saveSession("Lang_Country", "en_GB", DriverBackgroundCoreConfig.this);
        }

        Configuration config = new Configuration();
        String langcountry = DriverSessionSave.getSession("Lang_Country", DriverBackgroundCoreConfig.this);
        String language = DriverSessionSave.getSession("Lang", DriverBackgroundCoreConfig.this);
        String[] arry = langcountry.split("_");
        config.locale = new Locale(language, arry[1]);
        Locale.setDefault(new Locale(language, arry[1]));
        DriverBackgroundCoreConfig.this.getBaseContext().getResources().updateConfiguration(config, DriverBackgroundCoreConfig.this.getResources().getDisplayMetrics());

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

    /**
     * Getting Color values from local hash map
     */
    synchronized void getColorValueDetail() {
        Field[] fieldss = R.color.class.getDeclaredFields();
        // fields =new int[fieldss.length];
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
     * Method to logout user if status -101 and redirect to login page
     *
     * @param message - To intimate user by showing alert message
     */
    private void forceLogout(String message) {
        DriverCToast.ShowToast(DriverBackgroundCoreConfig.this, message);
        DriverServiceGenerator.API_BASE_URL = "";
        DriverSessionSave.saveSession("base_url", "", DriverBackgroundCoreConfig.this);
        DriverSessionSave.saveSession("Id", "", DriverBackgroundCoreConfig.this);
        DriverSessionSave.clearAllSession(DriverBackgroundCoreConfig.this);
        stopService(new Intent(this, LocationUpdate.class));
        stopSelf();
        Intent intent = new Intent(DriverBackgroundCoreConfig.this, DriverUserLoginAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Getting Language Files from Server
     */
    private class callString implements DriverAPIResult {
        public callString(final String url) {
            // TODO Auto-generated constructor stub

            String urls = DriverSessionSave.getSession("currentStringUrl", DriverBackgroundCoreConfig.this);
            if (urls.equals("")) {
                urls = DriverSessionSave.getSession(DriverSessionSave.getSession("LANGDef", DriverBackgroundCoreConfig.this), DriverBackgroundCoreConfig.this);
                if (DriverSessionSave.getSession("LANGTempDef", DriverBackgroundCoreConfig.this).trim().equalsIgnoreCase("RTL")) {
                    DriverSessionSave.saveSession("Lang_Country", "ar_EG", DriverBackgroundCoreConfig.this);
                    DriverSessionSave.saveSession("Lang", "ar", DriverBackgroundCoreConfig.this);
                    Configuration config = new Configuration();
                    String langcountry = DriverSessionSave.getSession("Lang_Country", DriverBackgroundCoreConfig.this);
                    String language = DriverSessionSave.getSession("Lang", DriverBackgroundCoreConfig.this);
                    String[] arry = langcountry.split("_");
                    config.locale = new Locale(language, arry[1]);
                    Locale.setDefault(new Locale(language, arry[1]));
                }
            }
            new DriverAPIService_Retrofit_JSON_NoProgress(DriverBackgroundCoreConfig.this, this, null, true, urls, true).execute();
        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            if (isSuccess) {
                setLocale();
                getAndStoreStringValues(result);
                DriverSessionSave.saveSession("wholekey", result, DriverBackgroundCoreConfig.this);

                if (DriverSessionSave.getSession("wholekeyColor", DriverBackgroundCoreConfig.this).trim().equals("") || !DriverSessionSave.getSession(DriverCommonData.PASSENGER_COLOR_TIME, DriverBackgroundCoreConfig.this).equals(getCoreColorTime))
                    new callColor("");

            } else {
            }
        }
    }

    /**
     * Getting Color Files from Server and response parsing
     */
    private class callColor implements DriverAPIResult {
        public callColor(final String url) {
            new DriverAPIService_Retrofit_JSON_NoProgress(DriverBackgroundCoreConfig.this, this, null, true, DriverSessionSave.getSession("colorcode", DriverBackgroundCoreConfig.this).replace("DriverAppColor", "driverAppColors"), true).execute();
        }

        @Override
        public void getResult(boolean isSuccess, String result) {
            if (isSuccess) {
                getAndStoreColorValues(result);
                DriverSessionSave.saveSession("wholekeyColor", result, DriverBackgroundCoreConfig.this);
            }
        }
    }

    /**
     * CoreConfig method API call and response parsing.
     */
    public class CoreConfigCall implements DriverAPIResult {
        public CoreConfigCall(final String url) {
            // TODO Auto-generated constructor stub
            new DriverAPIService_Retrofit_JSON_NoProgress(DriverBackgroundCoreConfig.this, this, "", true).execute(url);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            if (isSuccess) {

                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {

                        if (json.has("gt_lst_time"))
                            DriverSessionSave.saveSession(DriverCommonData.GETCORE_LASTUPDATE, json.getString("gt_lst_time"), DriverBackgroundCoreConfig.this);

                        if (json.has(DriverCommonData.ACTIVITY_BG))
                            DriverSessionSave.saveSession(DriverCommonData.ACTIVITY_BG, json.getString(DriverCommonData.ACTIVITY_BG), DriverBackgroundCoreConfig.this);

                        if (json.has(DriverCommonData.ERROR_LOGS))
                            DriverSessionSave.saveSession(DriverCommonData.ERROR_LOGS, json.getString(DriverCommonData.ERROR_LOGS).equals("1"), DriverBackgroundCoreConfig.this);
                        if (json.has(DriverCommonData.TIMEZONE)) {
                            DriverSessionSave.saveSession(DriverCommonData.TIMEZONE, json.getString(DriverCommonData.TIMEZONE), DriverBackgroundCoreConfig.this);
                        }
                        JSONArray jArry = json.getJSONArray("detail");

                        if (jArry.getJSONObject(0).has("customer_wallet_transaction")) {
                            SessionSave.saveSession("customer_wallet_transaction", jArry.getJSONObject(0).getString("customer_wallet_transaction"), DriverBackgroundCoreConfig.this);
                            System.out.println("customer_wallet_transaction check " + SessionSave.getSession("customer_wallet_transaction", DriverBackgroundCoreConfig.this));
                        }
                        DriverSessionSave.saveSession("api_base", jArry.getJSONObject(0).getString("api_base"), DriverBackgroundCoreConfig.this);
                        DriverSessionSave.saveSession("isFourSquare", jArry.getJSONObject(0).getString("android_foursquare_status"), DriverBackgroundCoreConfig.this);
                        DriverSessionSave.saveSession("android_foursquare_api_key", jArry.getJSONObject(0).getString("android_foursquare_api_key"), DriverBackgroundCoreConfig.this);
                        DriverSessionSave.saveSession("facebook_key", jArry.getJSONObject(0).getString("facebook_key"), DriverBackgroundCoreConfig.this);
                        DriverSessionSave.saveSession("play_store_version", jArry.getJSONObject(0).getString("android_driver_version"), DriverBackgroundCoreConfig.this);
                        if (jArry.getJSONObject(0).has("playstore_driver"))
                            DriverSessionSave.saveSession(DriverCommonData.PLAY_STORE_LINK, jArry.getJSONObject(0).getString("playstore_driver"), DriverBackgroundCoreConfig.this);

                        if (jArry.getJSONObject(0).has("last_forceupdate_version"))
                            DriverSessionSave.saveSession(DriverCommonData.LAST_FORCEUPDATE_VERSION, jArry.getJSONObject(0).getString("last_forceupdate_version"), DriverBackgroundCoreConfig.this);
                        else
                            DriverSessionSave.saveSession(DriverCommonData.LAST_FORCEUPDATE_VERSION, "0", DriverBackgroundCoreConfig.this);

                        if (jArry.getJSONObject(0).has("manual_waiting_enable")) {
                            DriverSessionSave.saveSession(DriverCommonData.WAITING_TIME_MANUAL, jArry.getJSONObject(0).getString("manual_waiting_enable").equals("1"), DriverBackgroundCoreConfig.this);
                        }
                        if (jArry.getJSONObject(0).has("is_driver_auto_accept")) {
                            DriverSessionSave.saveSession("is_driver_auto_accept", jArry.getJSONObject(0).getString("is_driver_auto_accept"), DriverBackgroundCoreConfig.this);
                        }
                        DriverSessionSave.saveSession("country_iso_code", jArry.getJSONObject(0).getString("country_iso_code"), DriverBackgroundCoreConfig.this);

                        String googleApiKey = jArry.getJSONObject(0).getString("android_google_api_key");
                        if (!getString(R.string.googleID).equals(googleApiKey))
                            AppController.getInstance().setPlaceApiKey(googleApiKey);

                        DriverSessionSave.saveSession(DriverCommonData.GOOGLE_KEY, googleApiKey, DriverBackgroundCoreConfig.this);

                        if (jArry.getJSONObject(0).has("android_mapbox_key")) {
                            DriverSessionSave.saveSession(DriverCommonData.MAP_BOX_TOKEN, jArry.getJSONObject(0).getString("android_mapbox_key"), DriverBackgroundCoreConfig.this);
                        } else {
                            DriverSessionSave.saveSession(DriverCommonData.MAP_BOX_TOKEN, "pk.eyJ1Ijoic2FiYXJpc2hqIiwiYSI6ImNqaGc1Yzd1ZDFlb24zZG4yNzNzaGo0aDgifQ.TzQA9NFpczQ5Yu5duB753A", DriverBackgroundCoreConfig.this);
                        }
                        if (jArry.getJSONObject(0).has("android_local_map_enable")) {
                            DriverSessionSave.saveSession(DriverCommonData.LOCAL_STORAGE, jArry.getJSONObject(0).getString("android_local_map_enable").equals("1"), DriverBackgroundCoreConfig.this);
                        } else {
                            DriverSessionSave.saveSession(DriverCommonData.LOCAL_STORAGE, false, DriverBackgroundCoreConfig.this);
                        }
                        if (jArry.getJSONObject(0).has("sos_msg"))
                            DriverSessionSave.saveSession("sos_message", jArry.getJSONObject(0).getString("sos_msg"), DriverBackgroundCoreConfig.this);
                       /* if (json.has("mobile_socket_http_url")) {
                            SessionSave.saveSession(CommonData.NODE_URL, json.getString("mobile_socket_http_url"), BackgroundCoreConfig.this);
                        }
*/
                        if (json.has("https_node_url")) {
                            DriverSessionSave.saveSession(DriverCommonData.NODE_URL, json.getString("https_node_url"), DriverBackgroundCoreConfig.this);
                        }

                        if (json.has("chat_node_url")) {
                            SessionSave.saveSession(TaxiUtil.CHAT_NODE_URL, json.getString("chat_node_url"), DriverBackgroundCoreConfig.this);
                        }
                        if (json.has("call_masking_enable")) {
                            SessionSave.saveSession(TaxiUtil.CALL_MASKING_ENABLE, json.getString("chat_node_url"), DriverBackgroundCoreConfig.this);
                        }

                        if (json.has("dispatcher_phone_number")) {
                            SessionSave.saveSession("dispatcher_phone_number", json.getString("dispatcher_phone_number"), DriverBackgroundCoreConfig.this);
                        }

                        if (json.has("mobile_socket_http_domain")) {
                            DriverSessionSave.saveSession(DriverCommonData.NODE_DOMAIN, json.getString("mobile_socket_http_domain"), DriverBackgroundCoreConfig.this);
                        }

                        if (json.has("chat_node_url")) {
                            DriverSessionSave.saveSession("chat_node_url", json.getString("chat_node_url"), DriverBackgroundCoreConfig.this);
                        }

                        if (json.has(DriverCommonData.HELP_URL)) {
                            DriverSessionSave.saveSession(DriverCommonData.HELP_URL, json.getString(DriverCommonData.HELP_URL), DriverBackgroundCoreConfig.this);
                        }

                        if (jArry.getJSONObject(0).has("sos_setting"))
                            DriverSessionSave.saveSession(DriverCommonData.SOS_ENABLED, jArry.getJSONObject(0).getString("sos_setting").equals("1"), DriverBackgroundCoreConfig.this);
                        if (jArry.getJSONObject(0).has("map_settings") && jArry.getJSONObject(0).getJSONObject("map_settings").has("is_google_distance")) {
                            DriverSessionSave.saveSession(DriverCommonData.isGoogleDistance, jArry.getJSONObject(0).getJSONObject("map_settings").getString("is_google_distance").equals("1"), DriverBackgroundCoreConfig.this);
                            DriverSessionSave.saveSession(DriverCommonData.isGoogleRoute, jArry.getJSONObject(0).getJSONObject("map_settings").getString("is_google_direction").equals("1"), DriverBackgroundCoreConfig.this);
                            DriverSessionSave.saveSession(DriverCommonData.isGoogleGeocoder, jArry.getJSONObject(0).getJSONObject("map_settings").getString("is_google_geocode").equals("1"), DriverBackgroundCoreConfig.this);
                            DriverSessionSave.saveSession(DriverCommonData.isNeedtoDrawRoute, jArry.getJSONObject(0).getJSONObject("map_settings").getString("enable_route").equals("1"), DriverBackgroundCoreConfig.this);
                            DriverSessionSave.saveSession(DriverCommonData.isNeedtofetchAddress, jArry.getJSONObject(0).getJSONObject("map_settings").getString("display_current_location").equals("1"), DriverBackgroundCoreConfig.this);

                        } else {
                            DriverSessionSave.saveSession(DriverCommonData.isGoogleDistance, true, DriverBackgroundCoreConfig.this);
                            DriverSessionSave.saveSession(DriverCommonData.isGoogleRoute, true, DriverBackgroundCoreConfig.this);
                            DriverSessionSave.saveSession(DriverCommonData.isGoogleGeocoder, true, DriverBackgroundCoreConfig.this);
                            DriverSessionSave.saveSession(DriverCommonData.isNeedtoDrawRoute, true, DriverBackgroundCoreConfig.this);
                            DriverSessionSave.saveSession(DriverCommonData.isNeedtofetchAddress, true, DriverBackgroundCoreConfig.this);
                        }
                        int length = jArry.length();
                        for (int i = 0; i < length; i++) {
                            DriverSessionSave.saveSession("noimage_base", jArry.getJSONObject(i).getString("noimage_base"), getApplicationContext());
                            DriverSessionSave.saveSession("site_currency", jArry.getJSONObject(i).getString("site_currency") + " ", getApplicationContext());
                            DriverSessionSave.saveSession("invite_txt", jArry.getJSONObject(i).getString("aboutpage_description"), getApplicationContext());
                            DriverSessionSave.saveSession("referal", jArry.getJSONObject(i).getString("driver_referral_settings"), getApplicationContext());
                            DriverSessionSave.saveSession("Metric", jArry.getJSONObject(i).getString("metric"), DriverBackgroundCoreConfig.this);
                        }
                        try {
                            getCoreLangTime = json.getJSONObject("language_color_status").getString("android_driver_language");
                            getCoreColorTime = json.getJSONObject("language_color_status").getString("android_driver_colorcode");
                            getCore_Utc = jArry.getJSONObject(0).getLong("utc_time");
                            DriverSessionSave.saveSession("utc_time", "" + getCore_Utc, DriverBackgroundCoreConfig.this);
                            boolean deflanAvail = false;
                            String totalLanguage = "";
                            JSONArray pArray = json.getJSONObject("language_color").getJSONObject("android").getJSONArray("driver_language");
                            for (int i = 0; i < pArray.length(); i++) {
                                totalLanguage += pArray.getJSONObject(i).getString("language").replaceAll(".xml", "") + "____";
                                DriverSessionSave.saveSession("LANG" + i, pArray.getJSONObject(i).getString("language"), DriverBackgroundCoreConfig.this);
                                DriverSessionSave.saveSession("LANGTemp" + i, pArray.getJSONObject(i).getString("design_type"), DriverBackgroundCoreConfig.this);
                                DriverSessionSave.saveSession("LANGCode" + i, pArray.getJSONObject(i).getString("language_code"), DriverBackgroundCoreConfig.this);
                                DriverSessionSave.saveSession(pArray.getJSONObject(i).getString("language"), pArray.getJSONObject(i).getString("url"), DriverBackgroundCoreConfig.this);
                                if (!DriverSessionSave.getSession("LANGDef", DriverBackgroundCoreConfig.this).equals("") && pArray.getJSONObject(i).getString("language").contains(DriverSessionSave.getSession("LANGDef", DriverBackgroundCoreConfig.this))) {
                                    deflanAvail = true;
                                }
                            }
                            if (DriverSessionSave.getSession("LANGDef", DriverBackgroundCoreConfig.this).trim().equals("") || !deflanAvail) {
                                DriverSessionSave.saveSession("LANGDef", DriverSessionSave.getSession("LANG0", DriverBackgroundCoreConfig.this), DriverBackgroundCoreConfig.this);
                                DriverSessionSave.saveSession("LANGTempDef", DriverSessionSave.getSession("LANGTemp0", DriverBackgroundCoreConfig.this), DriverBackgroundCoreConfig.this);
                                DriverSessionSave.saveSession("Lang", pArray.getJSONObject(0).getString("language_code").replaceAll(".xml", ""), DriverBackgroundCoreConfig.this);
                                String url = DriverSessionSave.getSession(DriverSessionSave.getSession("LANG" + 0, DriverBackgroundCoreConfig.this), DriverBackgroundCoreConfig.this);
                                DriverSessionSave.saveSession("currentStringUrl", url, DriverBackgroundCoreConfig.this);
                            }

                            DriverSessionSave.saveSession("lang_json", totalLanguage, DriverBackgroundCoreConfig.this);

                            DriverSessionSave.saveSession("colorcode", json.getJSONObject("language_color").getJSONObject("android").getString("driverColorCode"), DriverBackgroundCoreConfig.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (!DriverSessionSave.getSession(DriverCommonData.PASSENGER_LANGUAGE_TIME, DriverBackgroundCoreConfig.this).trim().equals(getCoreLangTime)) {
                            new callString(getCoreColorTime);
                        } else if (!DriverSessionSave.getSession(DriverCommonData.PASSENGER_COLOR_TIME, DriverBackgroundCoreConfig.this).trim().equals(getCoreColorTime)) {
                            new callColor(getCoreLangTime);
                        }
                    } else if (json.getInt("status") == 0) {
                        //no changes made
                    } else if (json.getInt("status") == -101) {
                        if (json.has("message")) forceLogout(json.getString("message"));
                        else forceLogout(DriverNC.getString(R.string.server_error));
                    }
                } catch (final JSONException | NullPointerException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } // TODO: handle exception
                catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            } else {
            }
        }
    }
}
