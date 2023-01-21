package com.taximobility.driver;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.driver.fragments.DriverTripDetailNewFrag;
import com.taximobility.driver.fragments.DriverTripHistory;
import com.taximobility.driver.utils.DirverColorchange;
import com.taximobility.driver.utils.DriverNetworkStatus;

import androidx.annotation.Nullable;

/**
 * Created by developer on 15/11/16.
 * This is class is used to show driver trip history
 */
public class DriverTripHistoryAct extends DriverBaseActivity {

    private TextView backtext;
    private DriverNetworkStatus networkStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_trip_history);
        DriverNetworkStatus.appContext = this;
        networkStatus = new DriverNetworkStatus();
        registerReceiver(networkStatus, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        backtext = findViewById(R.id.slideImg);
        backtext.setVisibility(View.VISIBLE);
        setTitle(getString(R.string.mybookings));
        backtext.setOnClickListener(v -> onBackPressed());

        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverTripHistoryAct.this.findViewById(android.R.id.content)).getChildAt(0)), DriverTripHistoryAct.this);

        boolean isFromFareScreen = false;
        String tripId = "";
        String tripDetailResponse = null;
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            isFromFareScreen = bundle.getBoolean("isFromFareScreen", false);
            tripId = bundle.getString("trip_id");
            tripDetailResponse = bundle.getString("tripDetailResponse");
        }

        if (isFromFareScreen) {
            DriverTripDetailNewFrag ff = new DriverTripDetailNewFrag();
            Bundle b = new Bundle();
            b.putBoolean("isFromFareScreen", true);
            b.putString("trip_id", tripId);
            b.putString("tripDetailResponse", tripDetailResponse);
            ff.setArguments(b);
            getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, ff).commit();
        } else
            getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new DriverTripHistory()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DriverNetworkStatus.isOnline(DriverTripHistoryAct.this);
    }

    public void setTitle(String s) {
        try {
            ((TextView) findViewById(R.id.headerTxt)).setText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.mainFrag) instanceof DriverTripHistory) {
            Intent intent = new Intent(DriverTripHistoryAct.this, DriverMyStatus.class);
            startActivity(intent);
            finish();
        } else {
            setTitle(getString(R.string.mybookings));
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(networkStatus);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
