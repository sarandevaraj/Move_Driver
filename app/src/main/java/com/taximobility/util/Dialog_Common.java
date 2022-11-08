package com.taximobility.util;

import android.app.Activity;
import android.app.Dialog;

import com.taximobility.interfaces.DialogInterface;

/**
 * this class is used to set common alert dialog
 */
public class Dialog_Common {

    public static Dialog mCustomDialog;

    private static Dialog dialog;

    /**
     * This method used to call logout API.
     */
    public static Dialog setmCustomDialog(final Activity context,
                                          final DialogInterface dialogInterface,
                                          String title, String message,
                                          String ok, String cancel, final String resultCode) {
        try {


            dialog = Utility.alert_view_dialog(context,
                    "" + title,
                    "" + message, "" + ok,
                    "" + cancel,
                    false, new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(android.content.DialogInterface dialog, int which) {
                            dialogInterface.onSuccess(mCustomDialog, resultCode);
                        }
                    }, new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(android.content.DialogInterface dialog, int which) {

                            dialogInterface.onFailure(mCustomDialog, resultCode);

                        }
                    }, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public Dialog setmCustomDialog(final Activity context, final DialogInterface dialogInterface,
                                   String title, String message, String ok, String cancel) {
        dialog = setmCustomDialog(context, dialogInterface,
                title, message, ok, cancel, "0");
        return dialog;
    }

    public Dialog setmCustomDialogs(final Activity context, final DialogInterface dialogInterface,
                                   String title, String message, String ok, String cancel,String s) {
        dialog = setmCustomDialog(context, dialogInterface,
                title, message, ok, cancel, s);
        return dialog;
    }

}