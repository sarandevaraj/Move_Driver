package com.moovex.driver;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moovex.R;
import com.moovex.driver.utils.DriverNC;
import com.moovex.driver.interfaces.AlertListener;
import com.moovex.util.Utility;

public class DriverSettingsAct extends MainActivityDriver implements View.OnClickListener {

    private TextView bt_privacysettings,notification_settings,log_out_txt,edit_profile_txt;
    private ImageView slider;
    private TextView HeadTitle;
    private RelativeLayout slide_lay;

    @Override
    public int setLayout() {
        setLocale();
        return R.layout.driver_settings;
    }

    @Override
    public void Initialize() {
        bt_privacysettings = findViewById(R.id.bt_privacysettings);
        notification_settings = findViewById(R.id.notification_settings);
        log_out_txt = findViewById(R.id.log_out_txt);
        edit_profile_txt = findViewById(R.id.edit_profile_txt);
        slider = findViewById(R.id.slideImg);
        HeadTitle = findViewById(R.id.headerTxt);
        slide_lay=findViewById(R.id.slide_lay);
        slide_lay.setBackgroundColor(getResources().getColor(R.color.button_accept));
        HeadTitle.setText(DriverNC.getResources().getString(R.string.settings));

        log_out_txt.setOnClickListener(view -> Utility.actionSheet(DriverSettingsAct.this, DriverNC.getResources().getString(R.string.confirmlogout), DriverNC.getResources().getString(R.string.menu_logout), DriverNC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                if (view == log_out_txt) {
                    logout(DriverSettingsAct.this);
                }
            }

            @Override
            public void onFailure() {

            }
        }));

        bt_privacysettings.setOnClickListener(view -> {
            Intent deleteAcc = new Intent(DriverSettingsAct.this, DeleteAccountActivityDriver.class);
            startActivity(deleteAcc);
        });

        edit_profile_txt.setOnClickListener(v -> {
            Intent profile = new Intent(DriverSettingsAct.this, DriverMeAct.class);
            startActivity(profile);
        });

        slider.setOnClickListener(view -> onBackPressed());

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    @Override
//    public void onClick(View view) {
//        if (view == log_out_txt) {
//            logout(DriverSettingsAct.this);
//        }
//
//    }
}
