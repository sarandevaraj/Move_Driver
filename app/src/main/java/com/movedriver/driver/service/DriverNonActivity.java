package com.movedriver.driver.service;

import android.content.Context;
import android.content.Intent;

import static com.movedriver.driver.service.LocationUpdate.startLocationService;

//This class used to make the service stop/start functions easily.
public class DriverNonActivity {
    public Context context;

    // Constructor
    public DriverNonActivity() {
    }

    public void startServicefromNonActivity(Context context) {
        startLocationService(context);
    }

    public void stopServicefromNonActivity(Context context) {
        Intent intent = new Intent(context, LocationUpdate.class);
        context.stopService(intent);
    }
}
