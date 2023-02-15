package com.movedriver.driver;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import android.text.Html;
import android.widget.TextView;

import com.movedriver.R;
import com.movedriver.driver.data.DriverWayPointsData;
import com.movedriver.driver.interfaces.DriverDistanceUpdate;
import com.movedriver.driver.utils.DriverCToast;
import com.movedriver.driver.utils.DriverSessionSave;
import com.movedriver.driver.utils.DriverSystems;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by developer on 8/2/18.
 */

public class DummyActivityDriver extends DriverBaseActivity implements DriverDistanceUpdate {

    private String trip_id = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_dummytest);

        if (getIntent() != null) trip_id = getIntent().getStringExtra("trip_id");
        if (trip_id != null && trip_id.equals(""))
            trip_id = DriverSessionSave.getSession("trip_id", DummyActivityDriver.this);
        DriverSystems.out.println("Trip_idddd" + trip_id);
        DriverCToast.ShowToast(this, trip_id);
//        UpdateLocation.distanceUpdate(DummyActivity.this);
        ((TextView) findViewById(R.id.text)).setText(Html.fromHtml(DriverSessionSave.getSession(trip_id + "data", DummyActivityDriver.this)));

        findViewById(R.id.start).setOnClickListener(view -> {
//                UpdateLocation.startLocationService(DummyActivity.this);
//                UpdateLocation.distanceUpdate(DummyActivity.this);
        });
        findViewById(R.id.stop).setOnClickListener(view -> {
//                stopService(new Intent(DummyActivity.this, UpdateLocation.class));
        });
    }

    @Override
    protected void onDestroy() {
//        Utils.closeDialog();
        super.onDestroy();
    }

    @Override
    public void onDistanceUpdate(Double distance, String s) {
        DriverSystems.out.println("onDistanceUpdate");
        if (s.equals("1")) {
            ((TextView) findViewById(R.id.txt_haver)).setText("" + distance);
        } else {
            JSONArray wayData = DriverSessionSave.ReadGoogleWaypointsWithId(DummyActivityDriver.this, "1");
            DriverSystems.out.println("WayDistance**" + wayData);
            ((TextView) findViewById(R.id.txt_google)).setText("" + wayData);
            try {
                for (int i = 0; i < wayData.length(); i++) {
                    DriverWayPointsData wayPointsData = new Gson().fromJson(wayData.get(i).toString(), DriverWayPointsData.class);
                    if (wayPointsData.getDist() == 0.0) {
                        DriverSystems.out.println("WayDistance" + wayPointsData.getDist());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}