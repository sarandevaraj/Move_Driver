package com.taximobility.driver;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.interfaces.AlertListener;
import com.taximobility.util.NC;
import com.taximobility.util.Utility;

public class DriverSettingsAct extends MainActivityDriver implements View.OnClickListener {


    private TextView bt_privacysettings,notification_settings,log_out_txt;
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
        slider = findViewById(R.id.slideImg);
        HeadTitle = findViewById(R.id.headerTxt);
        slide_lay=findViewById(R.id.slide_lay);

        slide_lay.setBackgroundColor(getResources().getColor(R.color.button_accept));

        HeadTitle.setText(DriverNC.getResources().getString(R.string.settings));


        log_out_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.actionSheet(DriverSettingsAct.this, NC.getResources().getString(R.string.confirmlogout), NC.getResources().getString(R.string.menu_logout), NC.getResources().getString(R.string.cancel), false, new AlertListener() {
                    @Override
                    public void onSuccess() {
                        if (view == log_out_txt) {
                            logout(DriverSettingsAct.this);
                        }
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });

        bt_privacysettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deleteAcc = new Intent(DriverSettingsAct.this, DeleteAccountActivityDriver.class);
                startActivity(deleteAcc);
            }
        });

        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


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
