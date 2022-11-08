package com.taximobility.driver.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.taximobility.driver.service.LocationUpdate.startLocationService;

/**
 * Created by developer on 22/2/18.
 */

public class DriverBootCompletedIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            if (!DriverSessionSave.getSession("Id", context).equals("") && DriverSessionSave.getSession("shift_status", context).equals("IN")) {
                startLocationService(context);
                if (DriverSessionSave.getSession("travel_status", context).equalsIgnoreCase("2")) {
                    DriverSystems.out.println("tamilllll " + "BootCompletedIntentReceiver");
//                    WaitingTimerRun.startTimerService(context);
                }

            }
        }
    }
}

