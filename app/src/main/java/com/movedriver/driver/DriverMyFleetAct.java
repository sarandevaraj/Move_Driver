package com.movedriver.driver;

import android.content.Intent;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.movedriver.R;
import com.movedriver.driver.adapter.DriverMyFleetListAdapter;
import com.movedriver.driver.data.DriverFleetData;
import com.movedriver.driver.interfaces.DriverAPIResult;
import com.movedriver.driver.service.DriverAPIService_Retrofit_JSON;
import com.movedriver.driver.utils.DriverCToast;
import com.movedriver.driver.utils.DriverNC;
import com.movedriver.driver.utils.DriverSessionSave;
import com.movedriver.driver.interfaces.AlertListener;
import com.movedriver.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DriverMyFleetAct extends MainActivityDriver {
    RecyclerView myFleetRv;
    ImageView add_fleet;
    DriverMyFleetListAdapter fleetListAdapter;
    String modelArray = "";
    String ownerName = "";
    private final List<DriverFleetData> fleetList = new ArrayList<>();

    @Override
    public int setLayout() {
        return R.layout.driver_my_fleet_act;
    }

    @Override
    public void Initialize() {
        myFleetRv = findViewById(R.id.fleets_rv);
        add_fleet = findViewById(R.id.add_fleet);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myFleetRv.setLayoutManager(llm);

        String url = "type=driver_Taxidetails";
        new FleetList(url);

        fleetListAdapter = new DriverMyFleetListAdapter(DriverMyFleetAct.this, fleetList);
        myFleetRv.setAdapter(fleetListAdapter);

        findViewById(R.id.slideImg).setOnClickListener(v -> onBackPressed());

        add_fleet.setOnClickListener(view -> {
            Intent intent = new Intent(DriverMyFleetAct.this, DriverAddFleetAct.class);
            intent.putExtra("model_details", modelArray);
            intent.putExtra("owner_name", ownerName);
            startActivity(intent);
        });
    }

    private class FleetList implements DriverAPIResult {
        String msg = "";

        public FleetList(String url) {

            try {
                JSONObject j = new JSONObject();
                j.put("driver_id", DriverSessionSave.getSession("Id", DriverMyFleetAct.this));

                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverMyFleetAct.this, this, j, false).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverMyFleetAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverMyFleetAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyFleetAct.this, "");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            try {
                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {

                        JSONArray mFleetArray = json.getJSONArray("detail");
                        JSONArray model_detailArray = json.getJSONArray("model_details");
                        modelArray = model_detailArray.toString();
                        System.out.println("testData" + mFleetArray.length());
                        fleetList.clear();
                        for (int i = 0; i < mFleetArray.length(); i++) {
                            JSONObject mJsonObject = mFleetArray.getJSONObject(i);
                            DriverFleetData mDriverFleetData = new DriverFleetData();

                            mDriverFleetData.setdetails_model_name(mJsonObject.getString("details_model_name"));
                            mDriverFleetData.setdetails_mapping_startdate(mJsonObject.getString("details_mapping_startdate"));
                            mDriverFleetData.setdetails_mapping_enddate(mJsonObject.getString("details_mapping_enddate"));
                            mDriverFleetData.setdetails_taxi_no(mJsonObject.getString("details_taxi_no"));
                            mDriverFleetData.setfocus_image_android(mJsonObject.getString("focus_image_android"));
                            mDriverFleetData.setdetails_taxi_id(mJsonObject.getString("details_taxi_id"));
                            mDriverFleetData.setprimaryFleet(mJsonObject.getString("primaryFleet"));
                            DriverSessionSave.saveSession("fleet_company_id", mJsonObject.getString("company_id"), DriverMyFleetAct.this);
                            ownerName = mJsonObject.getString("name");
                            System.out.println("Sakthi check name for driver  My fleet-----> " + mJsonObject.getString("name") + "   "+ ownerName);

                            fleetList.add(mDriverFleetData);
                        }
                        if (fleetList.size() == 0) {
                            DriverCToast.ShowToast(DriverMyFleetAct.this, "No Data Found");
                        } else {

                            fleetListAdapter = new DriverMyFleetListAdapter(DriverMyFleetAct.this, fleetList);
                            myFleetRv.setAdapter(fleetListAdapter);
                            fleetListAdapter.notifyDataSetChanged();
                        }

                    } else {
                        msg = json.getString("message");
                        DriverCToast.ShowToast(DriverMyFleetAct.this, msg);
//                        dialog1 = Driver_Utils.alert_view(DriverMyFleetAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyFleetAct.this, "");
                    }
                } else {
                    runOnUiThread(() -> DriverCToast.ShowToast(DriverMyFleetAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPrimaryFleet(final String fleetid) {
        Utility.actionSheet(DriverMyFleetAct.this, DriverNC.getResources().getString(R.string.change_fleet), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancell), false, new AlertListener() {
            @Override
            public void onSuccess() {
                String url = "type=setPrimary_fleet";
                new PrimaryFleet(url, fleetid);
            }

            @Override
            public void onFailure() {

            }
        });
        /*
        dialog1 = Driver_Utils.alert_view_dialog(DriverMyFleetAct.this, DriverNC.getResources().getString(R.string.message), DriverNC.getResources().getString(R.string.change_fleet), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancell), false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String url = "type=setPrimary_fleet";
                new PrimaryFleet(url, fleetid);
            }
        }, (dialog, which) -> dialog.dismiss(), "");

         */
    }

    class PrimaryFleet implements DriverAPIResult {
        String msg = "";

        public PrimaryFleet(String url, String fleetid) {

            try {
                JSONObject j = new JSONObject();
                j.put("driver_id", DriverSessionSave.getSession("Id", DriverMyFleetAct.this));
                j.put("taxi_id", fleetid);

                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverMyFleetAct.this, this, j, false, 3000).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverMyFleetAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverMyFleetAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyFleetAct.this, "");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, String result) {
            try {
                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {

                        String url = "type=driver_Taxidetails";
                        new FleetList(url);

                        msg = json.getString("message");
                        DriverCToast.ShowToast(DriverMyFleetAct.this, msg);
//                        dialog1 = Driver_Utils.alert_view(DriverMyFleetAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyFleetAct.this, "");
                    } else {
                        msg = json.getString("message");
                        DriverCToast.ShowToast(DriverMyFleetAct.this, msg);
//                        dialog1 = Driver_Utils.alert_view(DriverMyFleetAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyFleetAct.this, "");
                    }
                } else {
                    runOnUiThread(() -> DriverCToast.ShowToast(DriverMyFleetAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
