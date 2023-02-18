package com.movedriverdriver.driver.utils;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.movedriverdriver.R;
import com.movedriverdriver.driver.DriverBaseActivity;

/**
 * Created by developer on 19/2/18.
 */

public class DriverNoInternetScreen extends DriverBaseActivity {
    public static DriverNoInternetScreen mtag;
    public static TextView Network_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_no_internet_lay);
        mtag = this;
        Network_state = findViewById(R.id.tv_check_connection);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DriverSessionSave.saveSession("no_internet_screen", true, DriverNoInternetScreen.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DriverSessionSave.saveSession("no_internet_screen", false, DriverNoInternetScreen.this);
    }

    public static void CloseNoInternetStatus() {
        Network_state.setText("Internet Connection Established");
        Network_state.setBackgroundColor(mtag.getResources().getColor(R.color.pastbookingcashtext));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mtag.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mtag.getResources().getColor(R.color.pastbookingcashtext));
        }
        new Handler().postDelayed(() -> mtag.finish(), 2000);
    }

    @Override
    public void onBackPressed() {
    }
}
