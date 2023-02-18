package com.movedriverdriver.driver;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.movedriverdriver.R;
import com.movedriverdriver.driver.data.DriverCommonData;
import com.movedriverdriver.driver.service.DriverNonActivity;
import com.movedriverdriver.driver.utils.DirverColorchange;
import com.movedriverdriver.driver.utils.DriverFontHelper;
import com.movedriverdriver.driver.utils.DriverSessionSave;
import com.movedriverdriver.driver.utils.DriverSystems;

/**
 * This is cancel the trip
 */
public class DriverCanceltripAct extends MainActivityDriver implements OnClickListener {
    private TextView m_message;
    private String cancel_msg;
    private TextView m_cancel;

    /**
     * setting the layout
     */
    @Override
    public int setLayout() {
        // TODO Auto-generated method stub
        return R.layout.driver_canceltrip_lay;
    }

    /**
     * Initializing the component variables
     */
    @Override
    public void Initialize() {
        // TODO Auto-generated method stub
        DriverFontHelper.applyFont(DriverCanceltripAct.this, findViewById(R.id.canceltrip));

        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverCanceltripAct.this.findViewById(android.R.id.content)).getChildAt(0)), DriverCanceltripAct.this);

        unlockScreen();
        DriverCommonData.mActivitylist.add(this);
        Bundle bun = getIntent().getExtras();
        if (bun != null) {
            m_message = findViewById(R.id.message);
            m_cancel = findViewById(R.id.button1);
            cancel_msg = bun.getString("message");
            m_message.setText(cancel_msg);
            DriverSessionSave.saveSession("trip_id", "", DriverCanceltripAct.this);
            setonclickListener();
        }
    }

    /**
     * on click listener
     */
    private void setonclickListener() {
        m_cancel.setOnClickListener(this);
    }

    /**
     * on click listener
     */
    @Override
    public void onClick(View v) {
        if (v == m_cancel) {
            MainActivityDriver.mMyStatus.setStatus("F");
            DriverSessionSave.saveSession("status", "F", getApplicationContext());
            MainActivityDriver.mMyStatus.settripId("");
            DriverSessionSave.saveSession("trip_id", "", getApplicationContext());
            MainActivityDriver.mMyStatus.setOnstatus("On");
            MainActivityDriver.mMyStatus.setOnPassengerImage("");
            MainActivityDriver.mMyStatus.setOnpassengerName("");
            MainActivityDriver.mMyStatus.setOndropLocation("");
            MainActivityDriver.mMyStatus.setPassengerOndropLocation("");
            MainActivityDriver.mMyStatus.setOnpickupLatitude("");
            MainActivityDriver.mMyStatus.setOnpickupLongitude("");
            MainActivityDriver.mMyStatus.setOndropLatitude("");
            MainActivityDriver.mMyStatus.setOndropLongitude("");
            MainActivityDriver.mMyStatus.setOndriverLatitude("");
            MainActivityDriver.mMyStatus.setOndriverLongitude("");
            DriverSystems.out.println("Comminggggg_cancel");
            new DriverNonActivity().stopServicefromNonActivity(DriverCanceltripAct.this);
            new DriverNonActivity().startServicefromNonActivity(DriverCanceltripAct.this);
            Intent in = new Intent(DriverCanceltripAct.this, DriverMyStatus.class);
            in.setAction(Intent.ACTION_MAIN);
            in.addCategory(Intent.CATEGORY_LAUNCHER);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            ComponentName cn = new ComponentName(getApplicationContext(), DriverMyStatus.class);
            in.setComponent(cn);
            startActivity(in);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DriverSessionSave.saveSession("trip_id", "", DriverCanceltripAct.this);
        Intent in = new Intent(DriverCanceltripAct.this, DriverMyStatus.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
        finish();
    }

    /**
     * This method is to check and open the notification view in front even the mobile screen off.
     */
    private void unlockScreen() {
        Window window = this.getWindow();
        window.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);
    }

}