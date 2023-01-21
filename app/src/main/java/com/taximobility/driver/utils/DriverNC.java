package com.taximobility.driver.utils;

import com.taximobility.driver.MainActivityDriver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Used to Store and get string files from Hash map
 */
public class DriverNC {
    public static HashMap<Integer, String> nfields_byID = new HashMap<>();
    public static HashMap<String, String> nfields_byName = new HashMap<>();


    public static ArrayList<String> fields = new ArrayList<>();
    public static ArrayList<String> fields_value = new ArrayList<>();

    public static HashMap<String, Integer> fields_id = new HashMap<>();

    static DriverNC NC = null;

    static DriverNC getInstance() {
        if (NC == null) NC = new DriverNC();
        return NC;
    }

    public static DriverNC getResources() {
        return getInstance();
    }

    public static DriverNC getActivity() {
        return getInstance();
    }

    public static String getString(int c) {

        if (nfields_byID.get(c) == null && MainActivityDriver.context != null) {
            try {
                return MainActivityDriver.context.getString(c);
            } catch (Exception e) {

                e.printStackTrace();
                return "";
            }
        } else return nfields_byID.get(c);
    }
}
