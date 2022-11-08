package com.taximobility.util;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.SplashActivity;

import java.util.HashMap;

/**
 * this class is used to store the string files commonly and used in whole project
 */
public class NC {
    public static HashMap<Integer, String> nfields_byID = new HashMap<>();
    public static HashMap<String, String> nfields_byName = new HashMap<>();

    static NC NC = null;

    static NC getInstance() {
        if (NC == null)
            NC = new NC();
        else
            NC = NC;
        return NC;
    }

    public static NC getResources() {
        return getInstance();
    }

    public static NC getActivity() {
        return getInstance();
    }

    public static String getString(int c) {
        String text = "";
        if (SplashActivity.fields_value.contains(text)) {
        }
        if (nfields_byID.get(c) == null && MainHomeFragmentActivity.context != null) {
            try {
                return MainHomeFragmentActivity.context.getString(c);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } else
            return nfields_byID.get(c);
    }
}
