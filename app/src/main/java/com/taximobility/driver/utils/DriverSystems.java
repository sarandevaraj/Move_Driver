package com.taximobility.driver.utils;

import com.taximobility.BuildConfig;

/**
 * Created by developer on 31/1/18.
 */

public class DriverSystems {
    public static class out {
        public static void println(String s) {
            if (BuildConfig.DEBUG) {
                //It's not a release version.
                System.out.println(s);
            }
        }
    }

    public static class err {
        public static void println(String s) {
            if (BuildConfig.DEBUG) {
                //It's not a release version.
                System.err.println(s);
            }
        }
    }
}
