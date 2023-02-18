package com.movedriverdriver.driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.movedriverdriver.R;
import com.movedriverdriver.driver.data.DriverCommonData;
import com.movedriverdriver.driver.service.DriverNonActivity;
import com.movedriverdriver.driver.utils.DirverColorchange;
import com.movedriverdriver.driver.utils.DriverNC;
import com.movedriverdriver.driver.utils.DriverSessionSave;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * This class is used to show trip fare once the payment is completed
 */
public class DriverJobdoneAct extends MainActivityDriver {
    // Class members declarations
    private TextView fareTxt;
    private TextView referalTxt;
    private TextView HeadTitle;
    private Button back_main;
    private String j_fare;
    private String j_referal;
    private double m_fare = 0.0;
    private String message;
    private ImageView back;
    DriverNonActivity nonactiityobj = new DriverNonActivity();


    /**
     * Set the layout to activity.
     */
    @Override
    public int setLayout() {

        // This is method for set the language configuration.
        setLocale();
        return R.layout.driver_paypopup_lay;
    }


    /**
     * Initialize the views on layout
     */
    @Override
    public void Initialize() {

        DriverCommonData.mActivitylist.add(this);
        Bundle bun = getIntent().getExtras();
        DriverSessionSave.saveSession("status", "F", getApplicationContext());
        nonactiityobj.startServicefromNonActivity(DriverJobdoneAct.this);
        // DriverFontHelper.applyFont(this, findViewById(R.id.inner_content));
        DriverCommonData.current_act = "JobdoneAct";

        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverJobdoneAct.this.findViewById(android.R.id.content)).getChildAt(0)), DriverJobdoneAct.this);

        Glide.with(DriverJobdoneAct.this).load(DriverSessionSave.getSession("image_path", DriverJobdoneAct.this) + "eReceiptCash.png").into((ImageView) findViewById(R.id.currency_symbol));
        if (bun != null) {
            message = bun.getString("message");
            fareTxt = findViewById(R.id.fareTxt);
            referalTxt = findViewById(R.id.jobreferralTxt);
            HeadTitle = findViewById(R.id.headerTxt);
            back_main = findViewById(R.id.back_main);
            back = findViewById(R.id.slideImg);
            back.setVisibility(View.VISIBLE);
            HeadTitle.setText("" + DriverNC.getResources().getString(R.string.trip_completed));
            DriverCommonData.km_calc = 0;
            DriverSessionSave.saveSession("speedwaiting", "", DriverJobdoneAct.this);
            DriverSessionSave.saveSession("drop_location", "", DriverJobdoneAct.this);
            DriverSessionSave.saveSession("waitingHr", "", DriverJobdoneAct.this);

            // Show the job completion notification with fare,id and payment type.
            try {
                JSONObject json = new JSONObject(message);
                JSONObject detail = json.getJSONObject("detail");
                if (json.getInt("status") == 1) {
                    j_fare = detail.getString("fare");
                    j_referal = detail.getString("trip_id");
                    if (j_fare.length() > 0) {
                        m_fare = Double.parseDouble(j_fare);
                    }
                    fareTxt.setText(DriverSessionSave.getSession("site_currency", getApplicationContext()) + " " + String.format(Locale.UK, "%.2f", m_fare));
                    referalTxt.setText("" + DriverNC.getResources().getString(R.string.trip_id) + " : " + j_referal);

                    MainActivityDriver.mMyStatus.setStatus("F");
                    DriverSessionSave.saveSession("status", "F", DriverJobdoneAct.this);
                    MainActivityDriver.mMyStatus.settripId("");
                    DriverSessionSave.saveSession("trip_id", "", DriverJobdoneAct.this);
                    DriverSessionSave.setWaitingTime(0L, DriverJobdoneAct.this);
                    MainActivityDriver.mMyStatus.setOnstatus("");
                    MainActivityDriver.mMyStatus.setOnPassengerImage("");
                    MainActivityDriver.mMyStatus.setOnpassengerName("");
                    MainActivityDriver.mMyStatus.setOndropLocation("");
                    MainActivityDriver.mMyStatus.setOnpickupLatitude("");
                    MainActivityDriver.mMyStatus.setOnpickupLongitude("");
                    MainActivityDriver.mMyStatus.setOndropLatitude("");
                    MainActivityDriver.mMyStatus.setOndropLongitude("");
                    MainActivityDriver.mMyStatus.setOndriverLatitude("");
                    MainActivityDriver.mMyStatus.setOndriverLongitude("");
                    DriverSessionSave.saveSession(DriverCommonData.ST_WAITING_TIME, false, getApplicationContext());
                    DriverSessionSave.saveSession(DriverCommonData.WAITING_TIME, false, getApplicationContext());

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // This for close this activity and move to dashboard activity.
        back_main.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            showLoading(DriverJobdoneAct.this);
            startActivity(new Intent(getApplicationContext(), DriverMyStatus.class));
            finish();
        });
        back.setOnClickListener(v -> {

            showLoading(DriverJobdoneAct.this);
            startActivity(new Intent(getApplicationContext(), DriverMyStatus.class));
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
