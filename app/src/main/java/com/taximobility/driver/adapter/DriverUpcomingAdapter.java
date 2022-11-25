package com.taximobility.driver.adapter;

import static com.taximobility.driver.DriverOngoingAct.MY_PERMISSIONS_REQUEST_CALL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.taximobility.ProfileImageSetupClass;
import com.taximobility.R;
import com.taximobility.driver.DriverOngoingAct;
import com.taximobility.driver.MainActivityDriver;
import com.taximobility.driver.data.DriverCommonData;
import com.taximobility.driver.data.apiData.DriverUpcomingResponse;
import com.taximobility.driver.interfaces.DriverAPIResult;
import com.taximobility.driver.interfaces.DriverClickInterface;
import com.taximobility.driver.interfaces.DriverUpcomingAdapterInterface;
import com.taximobility.driver.pdview.DriverPickupDropView;
import com.taximobility.driver.route.DriverStopData;
import com.taximobility.driver.service.DriverAPIService_Retrofit_JSON;
import com.taximobility.driver.service.DriverNonActivity;
import com.taximobility.driver.utils.DirverColorchange;
import com.taximobility.driver.utils.DriverCToast;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.interfaces.AlertListener;
import com.taximobility.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This adapter class is used to show upcoming trip and pending trip
 */
public class DriverUpcomingAdapter extends RecyclerView.Adapter<DriverUpcomingAdapter.CustomViewHolder> implements
        DriverClickInterface, ActivityCompat.OnRequestPermissionsResultCallback {

    private List<DriverUpcomingResponse.PastBooking> data = new ArrayList<>();
    private final Context mContext;
    private String passPhoneNo;
    private String upcomingTripId;
    private int cancelTripPosition;
    DriverUpcomingAdapterInterface mInterface;

    public DriverUpcomingAdapter(Context c, List<DriverUpcomingResponse.PastBooking> data, DriverUpcomingAdapterInterface driverUpcomingAdapterInterface) {
        this.mContext = c;
        this.data = data;
        this.mInterface = driverUpcomingAdapterInterface;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view;
        view = inflater.inflate(R.layout.driver_upcoming_list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (!data.get(position).profile_image.trim().equals("")) {
            Picasso.get().load(data.get(position).map_image).into(holder.map_image);
            Picasso.get().load(data.get(position).profile_image).into(holder.driver_image);
            Picasso.get().load(data.get(position).profile_image).into(holder.passengerImg);
        } else {
            if (data.get(position).passenger_name != "") {
                ProfileImageSetupClass.setupProfileImage(
                        data.get(position).passenger_name, holder.driver_image
                );
            } else {
                Picasso.get().load(R.drawable.loadingimage).into(holder.driver_image);
            }
        }
        DirverColorchange.ChangeColor(holder.book_lay, mContext);
        holder.trip_time.setText(data.get(position).pickup_time);
        holder.trip_driver_name.setText(data.get(position).passenger_name);
        holder.txt_pickup.setText(data.get(position).pickup_location);
        holder.txt_drop.setText(data.get(position).drop_location);
        holder.model_name.setText(data.get(position).model_name);

        if (data.get(position).drop_location.equals("")) {
            holder.txt_drop.setVisibility(View.GONE);
            holder.approx_fare.setVisibility(View.GONE);
            holder.approx_distance.setVisibility(View.GONE);
            holder.drop_icon.setVisibility(View.GONE);
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.txt_drop.setVisibility(View.VISIBLE);
            holder.approx_fare.setVisibility(View.VISIBLE);
            holder.approx_distance.setVisibility(View.VISIBLE);
            holder.drop_icon.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.VISIBLE);
            holder.approx_fare.setText("" + DriverSessionSave.getSession("site_currency", mContext) + data.get(position).approx_fare);
            holder.approx_distance.setText(data.get(position).approx_distance + " " + DriverSessionSave.getSession("Metric", mContext));
            holder.txt_drop.setText(data.get(position).drop_location);
        }
        if (data.get(position).travel_status != null)
           /* if (!data.get(position).travel_status.trim().equals("0")) {
                holder.trip_details_lay.setVisibility(View.VISIBLE);
                holder.trip_track.setVisibility(View.VISIBLE);
                holder.bookLaterLayout.setVisibility(View.GONE);
                holder.trip_cancel.setVisibility(View.GONE);
                holder.trip_track.setTag(position);
                holder.trip_track.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SessionSave.getSession("shift_status", mContext).equalsIgnoreCase("IN")) {
                            SessionSave.saveSession("trip_id", data.get((Integer) view.getTag()).passengers_log_id.trim(), mContext);
                            Intent in = new Intent(mContext, OngoingAct.class);
                            mContext.startActivity(in);
                        } else {
                            CToast.ShowToast(mContext, NC.getResources().getString(R.string.track_shift_status));
                        }
                    }
                });
//            } else {*/
            if (data.get(position).schedule.trim().equals("1")) {
                holder.trip_track.setVisibility(View.GONE);
                holder.book_lay.setVisibility(View.GONE);
                holder.trip_cancel.setVisibility(View.GONE);
                holder.trip_cancel.setTag(position);
                holder.trip_details_lay.setVisibility(View.GONE);
                holder.bookLaterLayout.setVisibility(View.VISIBLE);
                holder.passengerPhoneTxt.setVisibility(View.GONE);
                holder.passengerPhoneTxt.setText(data.get(position).passenger_phone);
                holder.passengerName.setText(data.get(position).passenger_name);
                holder.pickupTimeTxt.setText(data.get(position).pickup_time);
                holder.updateTimeTxt.setText(data.get(position).time);
                holder.updateDistanceTxt.setText(data.get(position).away);

                holder.pickUpDropLayout.setData(getStopArray(position, data), "SCHEDULE", DriverSessionSave.getSession("Lang", mContext));


                holder.passengerCallTxt.setOnClickListener(view -> {
                    try {
                        passPhoneNo = data.get(position).passenger_country_code + data.get(position).passenger_phone;
                        /*     if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                Utils.alert_view_dialog(mContext, "", NC.getResources().getString(R.string.str_phone), NC.getResources().getString(R.string.yes), NC.getResources().getString(R.string.no), true, (dialog, i) -> {
                                    ActivityCompat.requestPermissions((Activity) mContext,
                                            new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE},
                                            MY_PERMISSIONS_REQUEST_CALL);
                                    dialog.dismiss();
                                }, (dialog, i) -> dialog.dismiss(), "");
                            } else {*/
                        //                            }
                        if (passPhoneNo.equals("0"))
                            Toast.makeText(mContext, "" + DriverNC.getString(R.string.invalid_mobile_number), Toast.LENGTH_LONG);
                        else
                            ensureCall();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                holder.startTripTxt.setOnClickListener(view -> {
                    try {
                        JSONObject j = new JSONObject();
                        j.put("trip_id", data.get(position).passengers_log_id.trim());
                        j.put("driver_id", DriverSessionSave.getSession("Id", mContext));
                        j.put("pickup_latitude", DriverSessionSave.getSession(DriverCommonData.CURRENT_LAT, mContext));
                        j.put("pickup_longitude", DriverSessionSave.getSession(DriverCommonData.CURRENT_LNG, mContext));
                        String scheduleTripUrl = "type=schedule_start_trip";
                        new DriverNonActivity().stopServicefromNonActivity(mContext);
                        new ScheduleStartTrip(scheduleTripUrl, j, mInterface, position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });

                holder.cancelTxt.setOnClickListener(view ->
                {
                    upcomingTripId = data.get(position).passengers_log_id.trim();
                    cancelTripPosition = position;
                    Utility.actionSheet((Activity) mContext, DriverNC.getResources().getString(R.string.cancel_in_going_trip), DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), false, new AlertListener() {
                        @Override
                        public void onSuccess() {
                            try {
                                JSONObject j = new JSONObject();
                                j.put("pass_logid", upcomingTripId);
                                j.put("driver_id", DriverSessionSave.getSession("Id", mContext));
                                j.put("taxi_id", DriverSessionSave.getSession("taxi_id", mContext));
                                j.put("company_id", DriverSessionSave.getSession("company_id", mContext));
                                j.put("driver_reply", "C");
                                j.put("field", "");
                                j.put("flag", "1");
                                if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("Arrivd"))
                                    j.put("driver_arrived", 1);
                                else
                                    j.put("driver_arrived", 0);
                                j.put("schedule", "1");
                                final String canceltrip_url = "type=driver_reply";
                                new CancelTrip(canceltrip_url, j, mInterface, cancelTripPosition);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                });

            } else {
                if (!data.get(position).travel_status.trim().equals("0")) {
                    holder.trip_details_lay.setVisibility(View.VISIBLE);
                    holder.trip_track.setVisibility(View.VISIBLE);
                    holder.book_lay.setVisibility(View.VISIBLE);
                    holder.bookLaterLayout.setVisibility(View.GONE);
                    holder.trip_cancel.setVisibility(View.GONE);
                    holder.trip_track.setTag(position);
                    holder.book_lay.setTag(position);
                    holder.trip_track.setOnClickListener(view -> {
                        if (DriverSessionSave.getSession("shift_status", mContext).equalsIgnoreCase("IN")) {
                            DriverSessionSave.saveSession("trip_id", data.get((Integer) view.getTag()).passengers_log_id.trim(), mContext);
                            Intent in = new Intent(mContext, DriverOngoingAct.class);
                            mContext.startActivity(in);
                        } else {
                            DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.track_shift_status));
                        }
                    });
                    holder.book_lay.setOnClickListener(view -> {

                    });


                } else {
                    holder.trip_details_lay.setVisibility(View.VISIBLE);
                    holder.trip_track.setVisibility(View.GONE);
                    holder.book_lay.setVisibility(View.VISIBLE);
                    holder.trip_cancel.setVisibility(View.VISIBLE);
                    holder.bookLaterLayout.setVisibility(View.GONE);
                    holder.trip_cancel.setTag(position);
                }
            }
    }

    private ArrayList<DriverStopData> getStopArray(int position, List<DriverUpcomingResponse.PastBooking> data) {
        String pickupLat = data.get(position).pickup_latitude;
        String pickupLng = data.get(position).pickup_longitude;
        String dropLat = data.get(position).drop_latitude;
        String dropLng = data.get(position).drop_longitude;
        String pickLoc = data.get(position).pickup_location;
        String dropLoc = data.get(position).drop_location;
        String tripId = data.get(position).passengers_log_id;
        ArrayList<DriverStopData> driverStopData = new ArrayList<>();
        if (!pickupLat.equals("") && !pickupLng.equals(""))
            driverStopData.add(new DriverStopData(new Random().nextInt(), Double.parseDouble(pickupLat), Double.parseDouble(pickupLng), pickLoc, tripId, ""));
        if (!dropLat.equals("") && !dropLng.equals(""))
            driverStopData.add(new DriverStopData(new Random().nextInt(), Double.parseDouble(dropLat), Double.parseDouble(dropLng), dropLoc, tripId, ""));
        return driverStopData;
    }

    /**
     * Call passenger
     */
    private void ensureCall() {
        Utility.actionSheet((Activity) mContext, DriverNC.getString(R.string.confirm_call), DriverNC.getResources().getString(R.string.call), DriverNC.getResources().getString(R.string.call), false, new AlertListener() {
            @Override
            public void onSuccess() {
                try {
                    final Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + passPhoneNo));
                   /* if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }*/
                    mContext.startActivity(callIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }


    /**
     * Handling functionality after permission granted
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ensureCall();
                }
                break;
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void positiveButtonClick(DialogInterface dialog, int id, String s) {
        switch (s) {
            case "1":
                try {
                    dialog.dismiss();
                    final Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + passPhoneNo));
                   /* if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }*/
                    mContext.startActivity(callIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "3":
                try {
                    JSONObject j = new JSONObject();
                    j.put("pass_logid", upcomingTripId);
                    j.put("driver_id", DriverSessionSave.getSession("Id", mContext));
                    j.put("taxi_id", DriverSessionSave.getSession("taxi_id", mContext));
                    j.put("company_id", DriverSessionSave.getSession("company_id", mContext));
                    j.put("driver_reply", "C");
                    j.put("field", "");
                    j.put("flag", "1");
                    if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("Arrivd"))
                        j.put("driver_arrived", 1);
                    else
                        j.put("driver_arrived", 0);
                    j.put("schedule", "1");
                    final String canceltrip_url = "type=driver_reply";
                    new CancelTrip(canceltrip_url, j, mInterface, cancelTripPosition);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void negativeButtonClick(DialogInterface dialog, int id, String s) {
        dialog.dismiss();
    }


    /**
     * View holder class member this contains in every row in list.
     */
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView map_image, driver_image;
        TextView trip_time, trip_driver_name, trip_track, trip_cancel, model_name;
        LinearLayout book_lay;

        CardView bookLaterLayout;
        TextView updateTimeTxt, updateDistanceTxt, passengerPhoneTxt, passengerName, pickupTimeTxt;
        TextView passengerCallTxt, cancelTxt, startTripTxt, txt_pickup, txt_drop, approx_fare, approx_distance;
        ImageView passengerImg, drop_icon;
        View divider;
        private DriverPickupDropView pickUpDropLayout;
        private LinearLayout trip_details_lay;

        public CustomViewHolder(View v) {
            super(v);
            map_image = v.findViewById(R.id.map_image);
            driver_image = v.findViewById(R.id.driver_image);
            trip_time = v.findViewById(R.id.trip_time);
            trip_driver_name = v.findViewById(R.id.trip_driver_name);
            trip_track = v.findViewById(R.id.trip_track);
            trip_cancel = v.findViewById(R.id.trip_cancel);
            book_lay = v.findViewById(R.id.book_lay);
            txt_pickup = v.findViewById(R.id.txt_pickup);
            txt_drop = v.findViewById(R.id.txt_drop);

            bookLaterLayout = v.findViewById(R.id.bookLaterLay);
            updateTimeTxt = v.findViewById(R.id.updateTimeTxt);
            updateDistanceTxt = v.findViewById(R.id.updateDisTxt);
            passengerPhoneTxt = v.findViewById(R.id.passengerPhoneTxt);
            passengerCallTxt = v.findViewById(R.id.passengerCallTxt);
            cancelTxt = v.findViewById(R.id.cancelTxt);
            startTripTxt = v.findViewById(R.id.startTripTxt);
            passengerName = v.findViewById(R.id.passengerName);
            pickupTimeTxt = v.findViewById(R.id.pickupTimeTxt);
            passengerImg = v.findViewById(R.id.passengerImg);
            pickUpDropLayout = v.findViewById(R.id.pd_view);
            trip_details_lay = v.findViewById(R.id.trip_details_lay);
            model_name = v.findViewById(R.id.model_name);
            approx_fare = v.findViewById(R.id.approx_fare);
            approx_distance = v.findViewById(R.id.approx_distance);
            drop_icon = v.findViewById(R.id.drop_icon);
            divider = v.findViewById(R.id.divider);


        }
    }


    private class CancelTrip implements DriverAPIResult {
        DriverUpcomingAdapterInterface driverUpcomingAdapterInterface;
        int clickedPosition;

        CancelTrip(final String url, JSONObject data, DriverUpcomingAdapterInterface mInterface, int position) {
            this.driverUpcomingAdapterInterface = mInterface;
            this.clickedPosition = position;
            try {
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(mContext, this, data, false).execute(url);
                } else {
                    Toast.makeText(mContext, "" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG);
//                    Driver_Utils.alert_view(mContext, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverUpcomingAdapter.this, "4");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, String result) {
            if (isSuccess) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        DriverCToast.ShowToast(mContext, json.getString("message"));
                    } else {
                        DriverCToast.ShowToast(mContext, json.getString("message"));
                    }
                    data.remove(clickedPosition);
                    driverUpcomingAdapterInterface.updateUpcomingAdapter(data, clickedPosition);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
            }
        }

    }

    private class ScheduleStartTrip implements DriverAPIResult {
        int clickedPosition;
        DriverUpcomingAdapterInterface driverUpcomingAdapterInterface;

        ScheduleStartTrip(final String url, JSONObject data, DriverUpcomingAdapterInterface mInterface, int position) {
            this.clickedPosition = position;
            this.driverUpcomingAdapterInterface = mInterface;
            try {
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(mContext, this, data, false).execute(url);
                } else {
                    Toast.makeText(mContext, "" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG);
//                    Driver_Utils.alert_view(mContext, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverUpcomingAdapter.this, "4");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, String result) {
            if (isSuccess) {
                new DriverNonActivity().startServicefromNonActivity(mContext);
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        DriverSessionSave.saveSession("trip_id", json.getString("trip_id"), mContext);
                        DriverSessionSave.saveSession("status", json.getString("driver_status"), mContext);
                        DriverSessionSave.saveSession("travel_status", json.getString("travel_status"), mContext);
                        if (DriverSessionSave.getSession("shift_status", mContext).equalsIgnoreCase("IN")) {
                            DriverSessionSave.saveSession("trip_id", data.get(clickedPosition).passengers_log_id.trim(), mContext);
                            Intent in = new Intent(mContext, DriverOngoingAct.class);
                            mContext.startActivity(in);
                        } else {
                            DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.track_shift_status));
                        }
                    } else if (json.getInt("status") == -2) {
                        data.remove(clickedPosition);
                        driverUpcomingAdapterInterface.updateUpcomingAdapter(data, clickedPosition);
                    } else {
                        DriverCToast.ShowToast(mContext, json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error));
                new DriverNonActivity().startServicefromNonActivity(mContext);
            }
        }
    }

    /**
     * This is method for check the Internet connection
     */
    public boolean isOnline() {

        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo networkInfo : info)
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }
}
