package com.taximobility;

import android.content.Intent;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taximobility.data.SplitStatusData;
import com.taximobility.fragments.SplitFareStatusDialog;
import com.taximobility.interfaces.APIResult;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.NetworkStatus;
import com.taximobility.util.RoundedImageView;
import com.taximobility.util.SessionSave;
import com.taximobility.util.TaxiUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


/**
 * this class is used to display the tripdetails when user book a taxi
 */
public class TripDetailsAct extends MainActivity {

    private RoundedImageView driverImg;
    private ImageView split_ref, ratingBar;
    private TextView time, place, booking_txt, HeadTitle, driverNameTxt, taxiNumberTxt;
    private TextView paymenttype, jobref, amount, waitingfare;
    private TextView passnameTxt, dropplace, droptimeTxt, distanceTxt, duartionTxt;
    private Button bookingBtn;
    private RelativeLayout tripdetail_contain;


    private SplitFareStatusDialog splitFareDialog;
    private double mdistance;
    private String Tripid;


    /**
     * Set the layout to activity.
     */
    @Override
    public int setLayout() {
        setLocale();
        return R.layout.tripdetaillay;
    }

    @Override
    public void priorChanges() {
        super.priorChanges();
        tripdetail_contain = findViewById(R.id.tripdetail_contain);
        FontHelper.applyFont(this, tripdetail_contain);
    }

    /**
     * Initialize the views on layout
     */
    @Override
    public void Initialize() {
        Colorchange.ChangeColor((ViewGroup) (((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0)), TripDetailsAct.this);
        TaxiUtil.sContext = this;
        TaxiUtil.mActivitylist.add(this);
        Intent i = getIntent();
        TaxiUtil.current_act = "TripDetailsAct";
        Tripid = i.getStringExtra("Tripid");
        findViewById(R.id.headlayout).setVisibility(View.VISIBLE);
        HeadTitle = findViewById(R.id.header_titleTxt);
        HeadTitle.setText(NC.getResources().getString(R.string.tripdetail));
        driverImg = findViewById(R.id.driverImg);
        driverNameTxt = findViewById(R.id.drivernameTxt);
        taxiNumberTxt = findViewById(R.id.taxinoTxt);
        time = findViewById(R.id.time);
        amount = findViewById(R.id.amount);
        waitingfare = findViewById(R.id.waiting);
        split_ref = findViewById(R.id.split_ref);
        place = findViewById(R.id.place);
        booking_txt = findViewById(R.id.booking_txt);
        dropplace = findViewById(R.id.dropplace);
        paymenttype = findViewById(R.id.payment);
        jobref = findViewById(R.id.jobref);
        bookingBtn = findViewById(R.id.bookingBtn);
        passnameTxt = findViewById(R.id.passnameTxt);
        droptimeTxt = findViewById(R.id.droptimeTxt);
        distanceTxt = findViewById(R.id.distanceTxt);
        duartionTxt = findViewById(R.id.duartionTxt);
        ratingBar = findViewById(R.id.rating);

        try {
            JSONObject j = new JSONObject();
            j.put("trip_id", Tripid);
            new TripDetail("type=get_trip_detail", j);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        bookingBtn.setOnClickListener(v -> {
            Intent i1;
            if (!SessionSave.getSession("trip_id", TripDetailsAct.this).equals("")) {
                i1 = new Intent(TripDetailsAct.this, MainHomeFragmentActivity.class);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                startActivity(i1);
                finish();
            } else {
                i1 = new Intent(TripDetailsAct.this, MainHomeFragmentActivity.class);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                startActivity(i1);
                finish();
            }
        });
        split_ref.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            splitFareDialog = new SplitFareStatusDialog();
            splitFareDialog.show(fm, "splitStatus");
        });

    }


    /**
     * This class for call the get_trip_detail API and process the response to update the UI.
     */
    private class TripDetail implements APIResult {
        public TripDetail(String string, JSONObject data) {
            new APIService_Retrofit_JSON(TripDetailsAct.this, this, data, false).execute(string);
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {

            tripdetail_contain.setVisibility(View.VISIBLE);
            if (isSuccess) {
                try {
                    JSONObject json = new JSONObject(result);

                    Log.e("Trip details ", json.toString());

                    if (json.getInt("status") == 1) {
                        String driverImage = json.getJSONObject("detail").getString("driver_image");

                        String d_name = json.getJSONObject("detail").getString("driver_name");

//                        if (driverImage != null && driverImage.length() > 0) {
//                            Picasso.get().load(driverImage).placeholder(getResources().getDrawable(R.drawable.loadingimage)).error(getResources().getDrawable(R.drawable.profileimage)).into(driverImg);
//                        }

                        if (driverImage != null && driverImage.length() > 0) {
                            Picasso.get().load(driverImage).placeholder(getResources().getDrawable(R.drawable.driver_loadingimage)).error(getResources().getDrawable(R.drawable.driver_noimage)).into(driverImg);
                        } else {
                            if (d_name != "") {
                                ProfileImageSetupClass.setupProfileImage(
                                        d_name, driverImg
                                );
                            } else {
                                Picasso.get().load(R.drawable.loadingimage).into(driverImg);
                            }
                        }


                        d_name = Character.toUpperCase(d_name.charAt(0)) + d_name.substring(1);
                        driverNameTxt.setText(d_name);
                        passnameTxt.setText(d_name);
                        taxiNumberTxt.setText(json.getJSONObject("detail").getString("taxi_number"));
                        jobref.setText(json.getJSONObject("detail").getString("trip_id"));
                        paymenttype.setText(json.getJSONObject("detail").getString("payment_type"));
                        dropplace.setText(json.getJSONObject("detail").getString("drop_location"));
                        place.setText(json.getJSONObject("detail").getString("current_location"));
                        booking_txt.setText((json.getJSONObject("detail").getString("booking_time")));
                        int mdriverrating = 0;
                        if (!json.getJSONObject("detail").getString("rating").equalsIgnoreCase(""))
                            mdriverrating = (int) Float.parseFloat(json.getJSONObject("detail").getString("driver_rating"));
                        droptimeTxt.setText((json.getJSONObject("detail").getString("drop_time")));
                        ratingBar.setImageResource(0);
                        if (mdriverrating == 0) {
                            ratingBar.setImageResource(R.drawable.star6);
                        }
                        if (mdriverrating == 1) {
                            ratingBar.setImageResource(R.drawable.star1);
                        }
                        if (mdriverrating == 2) {
                            ratingBar.setImageResource(R.drawable.star2);
                        }
                        if (mdriverrating == 3) {
                            ratingBar.setImageResource(R.drawable.star3);
                        }
                        if (mdriverrating == 4) {
                            ratingBar.setImageResource(R.drawable.star4);
                        }
                        if (mdriverrating == 5) {
                            ratingBar.setImageResource(R.drawable.star5);
                        }
                        if (json.getJSONObject("detail").getString("distance").length() != 0)
                            mdistance = Double.parseDouble(json.getJSONObject("detail").getString("distance"));
                        distanceTxt.setText("" + String.format(Locale.UK, "%.2f", mdistance) + " " +
                                "" + json.getJSONObject("detail").getString("metric"));
                        duartionTxt.setText(json.getJSONObject("detail").getString("trip_duration"));
                        String t_amt = json.getJSONObject("detail").getString("amt");
                        String w_fare = json.getJSONObject("detail").getString("waiting_fare");

                        if (t_amt.length() > 0) {
                            amount.setText(SessionSave.getSession("Currency", TripDetailsAct.this) + "" + String.format(Locale.UK, "%.2f", Double.parseDouble(t_amt)));
                        } else {
                            amount.setText(SessionSave.getSession("Currency", TripDetailsAct.this) + "" + 0);
                        }

                        if (w_fare.length() > 0) {
                            waitingfare.setText(SessionSave.getSession("Currency", TripDetailsAct.this) + "" + String.format(Locale.UK, "%.2f", Double.parseDouble(w_fare)));
                        } else {
                            waitingfare.setText(SessionSave.getSession("Currency", TripDetailsAct.this) + "" + 0);
                        }


                        time.setText((json.getJSONObject("detail").getString("pickup_time")));
                        try {
                            if (json.getJSONObject("detail").has("isSplit_fare"))
                                if (json.getJSONObject("detail").getInt("isSplit_fare") == 1) {

                                    split_ref.setVisibility(View.VISIBLE);
                                    TaxiUtil.SPLIT_STATUS_ITEM.clear();
                                    JSONArray splitArray = json.getJSONObject("detail").getJSONArray("splitFareDetails");
                                    for (int i = 0; i < splitArray.length(); i++) {
                                        JSONObject SplitArrayData = splitArray.getJSONObject(i);
                                        SplitStatusData obj = new SplitStatusData(getApplicationContext());
                                        obj.setImage(SplitArrayData.getString("profile_image"));
                                        obj.setName(SplitArrayData.getString("firstname"));
                                        obj.setStatus(SplitArrayData.getString("approve_status"));
                                        obj.setFare_perc(SplitArrayData.getString("fare_percentage"));
                                        obj.setSplitfare(SplitArrayData.getString("splitted_fare"));
                                        obj.setWallet(SplitArrayData.getString("used_wallet_amount"));
                                        TaxiUtil.SPLIT_STATUS_ITEM.add(obj);
                                    }

                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                }
            } else {
                runOnUiThread(() -> ShowToast(TripDetailsAct.this, NC.getString(R.string.server_con_error)));
            }
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        this.finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkStatus.appContext = this;
        NetworkStatus.isOnline(TripDetailsAct.this);
    }

    @Override
    protected void onDestroy() {
        TaxiUtil.mActivitylist.remove(this);
        super.onDestroy();
    }

}