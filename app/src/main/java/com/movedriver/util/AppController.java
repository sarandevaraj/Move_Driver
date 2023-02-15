package com.movedriver.util;

import android.content.Intent;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import android.util.Log;

import com.movedriver.R;
import com.movedriver.driver.service.DriverCoreClient;
import com.movedriver.driver.service.DriverNodeServiceGenerator;
import com.movedriver.driver.service.DriverServiceGenerator;
import com.movedriver.service.CoreClient;
import com.movedriver.service.ServiceGenerator;

import com.google.android.libraries.places.api.Places;

/**
 * This class is for get the crash report from the app to registered mail id by using ACRA library.
 *
 * @author developer
 */
public class AppController extends MultiDexApplication {

    private static AppController mInstance;
    private CoreClient apiManagerWithBaseUrl, checkCompanyDomainapiManager, googleapiManager, apiManagerWithTimeoutWithEncrypt;
    private CoreClient apiManagerWithTimeoutWithoutEncrypt, nodeApiManagerWithTimeOut;
    private long nodeTimeOut = 0L;
    private DriverCoreClient apiManagerWithBaseUrl_driver, checkCompanyDomainapiManager_driver, googleapiManager_driver;
    private DriverCoreClient nodeApiManagerWithTimeOut_driver;
    private long nodeTimeOut_driver = 0L;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
     /*   if (BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build());
        }*/
//        Fabric.with(this, new Crashlytics());
        MultiDex.install(this);
//        if (!SessionSave.getSession(TaxiUtil.MAP_BOX_TOKEN, AppController.this).equals(""))
//            Mapbox.getInstance(AppController.this, SessionSave.getSession(TaxiUtil.MAP_BOX_TOKEN, AppController.this));
//        else
//            Mapbox.getInstance(AppController.this, "pk.eyJ1IjoibmFuZGhpbmlzIiwiYSI6ImNqaGl0M3U0aDI5MXczYW8xZGY3bmxod3gifQ.CsQZTI8nf5ZDh8ES3Iu87g");
        mInstance = this;
        if (SessionSave.getSession(TaxiUtil.GOOGLE_KEY, this).equals(""))
            SessionSave.saveSession(TaxiUtil.GOOGLE_KEY, getString(R.string.googleID), this);
        setPlaceApiKey(SessionSave.getSession(TaxiUtil.GOOGLE_KEY, this));
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        String stackTrace = Log.getStackTraceString(e);
        String message = e.getMessage();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"nagarajan.s@ndot.in"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "App log file");
        intent.putExtra(Intent.EXTRA_TEXT, stackTrace);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
        startActivity(intent);
    }

    /**
     * Get the API Manager for calling API with Base url
     *
     * @return com.owayride.retrofit.apiManager instance
     */
    public CoreClient getApiManagerWithEncryptBaseUrl() {
        if (apiManagerWithBaseUrl == null) {
            apiManagerWithBaseUrl = ServiceGenerator.getRetrofitWithEncryptBaseUrl(this).create(CoreClient.class);
        }
        return apiManagerWithBaseUrl;
    }

    public CoreClient getApiManagerWithoutEncryptBaseUrl() {
        if (googleapiManager == null) {
            googleapiManager = ServiceGenerator.getRetrofitWithoutEncryptBaseUrl(this).create(CoreClient.class);
        }
        return googleapiManager;
    }

    public CoreClient getCheckCompanyDomainapiManager(String url) {
        checkCompanyDomainapiManager = ServiceGenerator.getRetrofitEncryptUrl(this, url).create(CoreClient.class);
        return checkCompanyDomainapiManager;
    }

    public CoreClient getApiManagerWithTimeoutWithEncrypt(int timeOut) {
        if (apiManagerWithTimeoutWithEncrypt == null) {
            apiManagerWithTimeoutWithEncrypt = ServiceGenerator.getRetrofitWithTimeOutWithEncrypt(this, timeOut).create(CoreClient.class);
        }
        return apiManagerWithTimeoutWithEncrypt;
    }

    public CoreClient getApiManagerWithTimeoutWithoutEncrypt(int timeOut) {
        if (apiManagerWithTimeoutWithoutEncrypt == null) {
            apiManagerWithTimeoutWithoutEncrypt = ServiceGenerator.getRetrofitWithTimeOutWithoutEncrypt(this, timeOut).create(CoreClient.class);
        }
        return apiManagerWithTimeoutWithoutEncrypt;
    }

    public CoreClient getNodeApiManagerWithTimeOut(String base_url, long timeOut) {
        if (nodeTimeOut != timeOut) {
            nodeTimeOut = timeOut;
            nodeApiManagerWithTimeOut = null;
        }
        if (nodeApiManagerWithTimeOut == null) {
//            nodeApiManagerWithTimeOut = NodeServiceGenerator.INSTANCE.nodeGetRetrofitWithTimeOut(this, base_url, nodeTimeOut).create(CoreClient.class);
        }
        return nodeApiManagerWithTimeOut;
    }

    public void setPlaceApiKey(String apiKey) {
        SessionSave.saveSession(TaxiUtil.GOOGLE_KEY, apiKey, this);
        Places.initialize(this, apiKey);
    }

//Driver

    public DriverCoreClient getCheckCompanyDomainapiManager_driver(String url) {
        checkCompanyDomainapiManager_driver = DriverServiceGenerator.getRetrofitEncryptUrl(this, url).create(DriverCoreClient.class);
        return checkCompanyDomainapiManager_driver;
    }

    /**
     * Get the API Manager for calling API with Base url
     *
     * @return com.owayride.retrofit.apiManager instance
     */
    public DriverCoreClient getApiManagerWithEncryptBaseUrl_driver() {
        if (apiManagerWithBaseUrl_driver == null) {
            apiManagerWithBaseUrl_driver = DriverServiceGenerator.getRetrofitWithEncryptBaseUrl(this).create(DriverCoreClient.class);
        }
        return apiManagerWithBaseUrl_driver;
    }

    public DriverCoreClient getApiManagerWithEncryptBaseUrl_driver(int timeout) {
        apiManagerWithBaseUrl_driver = null;
        apiManagerWithBaseUrl_driver = DriverServiceGenerator.getRetrofitWithEncryptBaseUrl(this, timeout).create(DriverCoreClient.class);
        return apiManagerWithBaseUrl_driver;
    }

    public DriverCoreClient getApiManagerWithoutEncryptBaseUrl_driver() {
        if (googleapiManager_driver == null) {
            googleapiManager_driver = DriverServiceGenerator.getRetrofitWithoutEncryptBaseUrl(this).create(DriverCoreClient.class);
        }
        return googleapiManager_driver;
    }

    public DriverCoreClient getNodeApiManagerWithTimeOut_driver(String base_url, long timeOut) {
        if (nodeTimeOut_driver != timeOut) {
            nodeTimeOut_driver = timeOut;
            nodeApiManagerWithTimeOut_driver = null;
        }
        if (nodeApiManagerWithTimeOut_driver == null) {
            nodeApiManagerWithTimeOut_driver = DriverNodeServiceGenerator.INSTANCE.nodeGetRetrofitWithTimeOut(this, base_url, nodeTimeOut_driver).create(DriverCoreClient.class);
        }
        return nodeApiManagerWithTimeOut_driver;
    }
}