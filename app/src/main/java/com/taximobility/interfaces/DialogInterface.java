package com.taximobility.interfaces;

import android.app.Dialog;

/**
 * Created by developer on 3/22/16.
 * Interface to get result on dialog action done
 */
public interface DialogInterface {
    void onSuccess(Dialog dialog, String resultcode);

    void onFailure(Dialog dialog, String resultcode);
}
