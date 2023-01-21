package com.taximobility.driver.utils;

import android.content.Context;
import android.graphics.Color;

import com.taximobility.driver.MainActivityDriver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by developer on 29/11/16.
 * Used to Store and get color files from Hash map
 */
public class DriverCL {
    public static HashMap<Integer, String> nfields_byID = new HashMap<>();
    public static HashMap<String, String> nfields_byName = new HashMap<>();
    public static ArrayList<String> fields = new ArrayList<>();
    public static ArrayList<String> fields_value = new ArrayList<>();
    public static HashMap<String, Integer> fields_id = new HashMap<>();

    static DriverCL CL = null;

    static DriverCL getInstance() {
        if (CL == null) CL = new DriverCL();
        else CL = CL;
        return CL;
    }

    public static DriverCL getResources() {
        return getInstance();
    }

    public static DriverCL getActivity() {
        return getInstance();
    }

    public static int getColor(Context mcontext, int c) {

        if (nfields_byID.get(c) != null) return Color.parseColor(nfields_byID.get(c));
        else {
            if (MainActivityDriver.context != null) {
                DriverColorRestore.getAndStoreColorValues(DriverSessionSave.getSession("wholekeyColor", MainActivityDriver.context), MainActivityDriver.context);

                if (nfields_byID.get(c) == null)
                    return MainActivityDriver.context.getResources().getColor(c);
                else return Color.parseColor(nfields_byID.get(c));
            } else return Color.WHITE;
        }
    }

    public static int getColor(int c) {

        if (nfields_byID.get(c) != null) return Color.parseColor(nfields_byID.get(c));
        else {
            if (MainActivityDriver.context != null) {
                DriverColorRestore.getAndStoreColorValues(DriverSessionSave.getSession("wholekeyColor", MainActivityDriver.context), MainActivityDriver.context);

                if (nfields_byID.get(c) == null)
                    return MainActivityDriver.context.getResources().getColor(c);
                else return Color.parseColor(nfields_byID.get(c));
            } else return Color.WHITE;
        }
    }
}
