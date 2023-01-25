package com.moovex.driver;

import android.content.Intent;
import android.util.Log;

import com.google.android.libraries.places.api.Places;
import com.moovex.driver.data.DriverCommonData;
import com.moovex.driver.service.DriverCoreClient;
import com.moovex.driver.utils.DriverSessionSave;

import androidx.multidex.MultiDexApplication;

/**
 * Created by developer on 15/2/18.
 */

public class DriverMyApplication extends MultiDexApplication {

    private static DriverMyApplication mInstance;
    private DriverCoreClient apiManagerWithBaseUrl_driver, checkCompanyDomainapiManager_driver, googleapiManager_driver;
    private DriverCoreClient  nodeApiManagerWithTimeOut_driver;
    private final long nodeTimeOut_driver = 0L;

    @Override
    public void onCreate() {
        super.onCreate();
       /* if (BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build());
        }*/
//        if (!SessionSave.getSession(CommonData.MAP_BOX_TOKEN, MyApplication.this).equals(""))
//            Mapbox.getInstance(MyApplication.this, SessionSave.getSession(CommonData.MAP_BOX_TOKEN, MyApplication.this));
//        else
//            Mapbox.getInstance(MyApplication.this, "pk.eyJ1IjoibmFuZGhpbmlzIiwiYSI6ImNqaGl0M3U0aDI5MXczYW8xZGY3bmxod3gifQ.CsQZTI8nf5ZDh8ES3Iu87g");

        mInstance = this;

       /* if (SessionSave.getSession(CommonData.GOOGLE_KEY, this).equals(""))
            SessionSave.saveSession(CommonData.GOOGLE_KEY, getString(R.string.googleID), this);*/

     //   setPlaceApiKey(getResources().getString(R.string.googleID));

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

    public void setPlaceApiKey(String apiKey) {
        DriverSessionSave.saveSession(DriverCommonData.GOOGLE_KEY, apiKey, this);
        Places.initialize(this, apiKey);
    }
}