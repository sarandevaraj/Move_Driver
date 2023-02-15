package com.movedriver.driver.interfaces;

import android.content.DialogInterface;

/**
 * Created by developer on 27/2/18.
 */

public interface DriverClickInterface {
    void positiveButtonClick(DialogInterface dialog, int id, String s);

    void negativeButtonClick(DialogInterface dialog, int id, String s);
}
