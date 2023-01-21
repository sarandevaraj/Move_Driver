package com.taximobility.driver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.taximobility.driver.utils.DriverSystems;

public class DriverCallReceiver extends BroadcastReceiver {

    static String phoneState = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.intent.action.PHONE_STATE")) {
            phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            DriverSystems.out.println("phoneState" + phoneState);
            phoneState();
        }

    }

    public static boolean phoneState() {
        if (phoneState == null || phoneState.equals("")) return true;
        return phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE);
    }
}
