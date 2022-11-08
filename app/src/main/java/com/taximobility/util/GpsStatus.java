package com.taximobility.util;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.taximobility.MainActivity;
import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;

import java.util.Objects;

/**
 * This class is used to get the network status when it is enable/disable.
 */
public class GpsStatus extends BroadcastReceiver {
    public Context mContext;
    Context appContext;
    private String message;
    public static Dialog mDialog;
    public static int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.requireNonNull(intent.getAction()).matches("android.location.PROVIDERS_CHANGED")) {
            mContext = context;
            try {
                Systems.out.println("Gps");
                if (!isGpsEnabled(mContext)) {
                    if (TaxiUtil.sContext != null && TaxiUtil.sContext instanceof MainHomeFragmentActivity)
                        MainHomeFragmentActivity.gpsalert(TaxiUtil.sContext, false);
                    count++;
                } else {
                    count = 0;
                    message = NC.getString(R.string.gps_status);
                    if (!TaxiUtil.current_act.equals("SplashAct")) {
                        if (TaxiUtil.sContext != null)
                            MainActivity.gpsalert(TaxiUtil.sContext, true);
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }


    public void gpsalert(boolean isconnect, String title, String message, String success_txt, String failure_txt) {
        if (!isconnect) {
            final View view = View.inflate(TaxiUtil.sContext, R.layout.alert_view, null);
            mDialog = new Dialog(TaxiUtil.sContext, R.style.NewDialog);
            mDialog.setContentView(view);
            mDialog.setCancelable(false);
            if (!mDialog.isShowing())
                mDialog.show();
            final TextView title_text = mDialog.findViewById(R.id.title_text);
            final TextView message_text = mDialog.findViewById(R.id.message_text);
            final Button button_success = mDialog.findViewById(R.id.button_success);
            final Button button_failure = mDialog.findViewById(R.id.button_failure);
            button_failure.setVisibility(View.GONE);
            title_text.setText(title);
            message_text.setText(message);
            button_success.setText(success_txt);
            button_success.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    TaxiUtil.sContext.startActivity(mIntent);
                }
            });
            button_failure.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    mDialog.dismiss();
                }
            });
        } else {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    /**
     * this method is used detect whether the gps is enabled or not
     */

    public boolean isGpsEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean isNetworkEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }
}