package com.taximobility.interfaces;

import android.content.DialogInterface;


public interface ClickInterface {
    void positiveButtonClick(DialogInterface dialog, int id, String s);

    void negativeButtonClick(DialogInterface dialog, int id, String s);
}
