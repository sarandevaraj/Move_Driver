package com.taximobility;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;

public class Main2Activity extends AppCompatActivity {
    private ToggleButton tgbroute, tgbdistance, tgbautocomplete, tgbaddressfetching, tgbgeocoder, tgbroutevisiblity, tgbfarevisiblity;
    private Button bt_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tgbroute = findViewById(R.id.tgbroutegeo);
        tgbgeocoder = findViewById(R.id.tgbgeo);
        tgbdistance = findViewById(R.id.tgbdistance);
        tgbautocomplete = findViewById(R.id.tgbautocomplete);
        tgbaddressfetching = findViewById(R.id.tgbaddressfetching);
        tgbroutevisiblity = findViewById(R.id.tgbroutevisiblity);
        tgbfarevisiblity = findViewById(R.id.tgbfarevisiblity);
        bt_done = findViewById(R.id.bt_done);

       /* tgbroutegeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tgbroutegeo.isChecked()) {
                    SessionSave.saveSession(TaxiUtil.isGoogleRouteGeo, true,Main2Activity.this);
                } else {
                    SessionSave.saveSession(TaxiUtil.isGoogleRouteGeo, false,Main2Activity.this);
                }
            }
        });
        tgbdistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tgbdistance.isChecked()) {
                    SessionSave.saveSession(TaxiUtil.isGoogleDistance, true, Main2Activity.this);
                } else {
                    SessionSave.saveSession(TaxiUtil.isGoogleDistance, false, Main2Activity.this);
                }
            }
        });
        tgbautocomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tgbautocomplete.isChecked()) {
                    SessionSave.saveSession("isFourSquare",true,Main2Activity.this);
                } else {
                    SessionSave.saveSession("isFourSquare",false,Main2Activity.this);
                }
            }
        });
        tgbaddressfetching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tgbaddressfetching.isChecked()) {
                    SessionSave.saveSession( TaxiUtil.isNeedtoFetchAddress, true,Main2Activity.this);
                } else {
                    SessionSave.saveSession( TaxiUtil.isNeedtoFetchAddress, false,Main2Activity.this);
                }
            }
        });
        tgbroutevisiblity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tgbroutevisiblity.isChecked()) {
                    SessionSave.saveSession(TaxiUtil.isNeedtoDrawRoute,true,Main2Activity.this);
                } else {
                    SessionSave.saveSession(TaxiUtil.isNeedtoDrawRoute,false,Main2Activity.this);
                }
            }
        });
        tgbfarevisiblity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tgbfarevisiblity.isChecked()) {
                    SessionSave.saveSession( TaxiUtil.isNeedtoShowFare,true,Main2Activity.this);
                } else {
                    SessionSave.saveSession( TaxiUtil.isNeedtoShowFare,false,Main2Activity.this);
                }
            }
        });*/

        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tgbroute.isChecked()) {
                    SessionSave.saveSession(TaxiUtil.isGoogleRouteGeo, true, Main2Activity.this);
                } else {
                    SessionSave.saveSession(TaxiUtil.isGoogleRouteGeo, false, Main2Activity.this);
                }
                if (tgbgeocoder.isChecked()) {
                    SessionSave.saveSession(TaxiUtil.isGoogleGeocoder, true, Main2Activity.this);
                } else {
                    SessionSave.saveSession(TaxiUtil.isGoogleGeocoder, false, Main2Activity.this);
                }
                if (tgbdistance.isChecked()) {
                    SessionSave.saveSession(TaxiUtil.isGoogleDistance, true, Main2Activity.this);
                } else {
                    SessionSave.saveSession(TaxiUtil.isGoogleDistance, false, Main2Activity.this);
                }
                if (tgbautocomplete.isChecked()) {
                    SessionSave.saveSession("isFourSquare", "1", Main2Activity.this);
                } else {
                    SessionSave.saveSession("isFourSquare", "0", Main2Activity.this);
                }
                if (tgbaddressfetching.isChecked()) {
                    SessionSave.saveSession(TaxiUtil.isNeedtoFetchAddress, true, Main2Activity.this);
                } else {
                    SessionSave.saveSession(TaxiUtil.isNeedtoFetchAddress, false, Main2Activity.this);
                }
                if (tgbroutevisiblity.isChecked()) {
                    SessionSave.saveSession(TaxiUtil.isNeedtoDrawRoute, true, Main2Activity.this);
                } else {
                    SessionSave.saveSession(TaxiUtil.isNeedtoDrawRoute, false, Main2Activity.this);
                }
                if (tgbfarevisiblity.isChecked()) {
                    SessionSave.saveSession(TaxiUtil.isNeedtoShowFare, true, Main2Activity.this);
                } else {
                    SessionSave.saveSession(TaxiUtil.isNeedtoShowFare, false, Main2Activity.this);
                }

                Systems.out.println("initialcheck" + "isGoogleRouteGeo" + SessionSave.getSession(TaxiUtil.isGoogleRouteGeo, Main2Activity.this, false)
                        + "isGoogleGeocoder" + SessionSave.getSession(TaxiUtil.isGoogleDistance, Main2Activity.this, false) + "isGoogleDistance"
                        + SessionSave.getSession(TaxiUtil.isGoogleDistance, Main2Activity.this, true) +
                        "isFourSquare" + SessionSave.getSession("isFourSquare", Main2Activity.this) +
                        "isNeedtoFetchAddress" + SessionSave.getSession(TaxiUtil.isNeedtoFetchAddress, Main2Activity.this, true) +
                        "isNeedtoDrawRoute" + SessionSave.getSession(TaxiUtil.isNeedtoDrawRoute, Main2Activity.this, true) +
                        "isNeedtoShowFare" + SessionSave.getSession(TaxiUtil.isNeedtoShowFare, Main2Activity.this, true));


                Intent i = new Intent(Main2Activity.this, SplashActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onDestroy() {
//        Utility.closeDialog();
        super.onDestroy();
    }
}
