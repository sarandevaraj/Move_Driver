package com.taximobility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatButton;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taximobility.features.CToast;
import com.taximobility.fragments.TripDetailNewFrag;
import com.taximobility.fragments.WalletFrag;
import com.taximobility.interfaces.APIResult;
import com.taximobility.interfaces.ClickInterface;
import com.taximobility.interfaces.FragPopFront;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.NetworkStatus;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Locale;

import static com.taximobility.util.ConstantsKt.LANG;
import static com.taximobility.util.ConstantsKt.PASS_ID;

/**
 * This class is used to calculate the trip fare.
 */

public class FareCalcAct extends Fragment implements ClickInterface, FragPopFront {

    public static Activity mFlagger;
    public static FareCalcAct activity;
    private final int REQUEST_READ_PHONE_STATE = 292;
    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = null;
    DecimalFormat df = new DecimalFormat("####0.00");
    private Dialog dialog1, mDialog;
    Bundle details;

    private TextView tv_dropTime, tv_startTime, HeadTitle;
    private TextView tv_total_disatnce, tv_tripFare;
    private EditText et_time_hour, et_total_disatnce, et_time_mins, et_tripFare;
    private LinearLayout layoutOutstation, layoutNormal;
    private TextView radiocashButton, radiocardButton, radiouncardButton, radiowalletButton, eve_fare;
    private TextView actdistanceTxt, totalamountTxt, metricTxt, promopercentTxt, idwaitingcost;
    private TextView b_pickuplocation, b_droplocation, b_total_amt_curency, amountpayTxt;
    private TextView b_waitingcost, b_tax, b_discount, v_trip_fare, walletamountTxt;
    private LinearLayout walletlay, paylay, lay_fare;
    private TextView night_fare, minutes_value, walletamountCurrency, amountpayCurrency, txtCmp, slideImg;
    private LinearLayout totalamountTxt_lay, night_fare_lay, eve_fare_lay;
    private LinearLayout distance_lay, minutes_lay, waiting_lay, promoLayout, tax_lay;
    private AppCompatButton btn_emergency;
    private ImageView fabInfo;
    private ViewGroup rootLay;
    private View vid_discount;

    private int min_distance_status, mTravelstatus;
    private String fare_per_minute, waiting_fare_minutes, trip_minutes, subtotal, new_distance_fare;
    private String new_base_fare, distance_fare_metric, amt, promocode_fare, tax_fare, nightfare, eveningfare;
    private String existing_wallet_amount = "", pending_cancel_amount;
    private String[] os_hr_min = new String[2];
    private String trip_type = "1", promo_type, distanceFare = "";
    private String f_minutes_traveled, f_minutes_fare, cmpTax = "";
    private String Cvv, base_fare = "", fare_calculation_type = "3";
    private String f_nightfareapplicable, f_nightfare, f_totalfare, f_metric, f_distance;
    private String f_pickup = "", drop_location = "", f_eveningfare, f_eveningfare_applicable = "0";
    private String f_waitingcost, f_waitingtime, f_taxamount, f_tripfare;
    private String f_farediscount = "", promotax = "", promoamt = "", message, f_tripid;
    private String f_paymodid = "", p_dis = "", f_walletamt = "", f_payamt = "";
    private double f_tips, f_fare, m_walletamt, m_waitingcost, m_taxamount, os_tax, os_minute_fare;
    private double f_total, m_payamt, m_totalfare, m_tripfare, m_distance;
    private double os_plan_fare, os_plan_distance, os_plan_duration;
    private double os_additional_fare_per_distance, os_additional_fare_per_hour;
    private double os_distance, os_duration, os_fare, promo_percentage = 0.0, tax = 0.0;
    private boolean keyboardListenersAttached = false;
    private String razor_total = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.farecalc_lay2, container, false);
        Initialize(v);
        return v;
    }

    public String getStringFromTime(long mins) {
        String s = "0 " + NC.getString(R.string.hrs) + " 00" + NC.getString(R.string.mins) + " ";
        if (mins > 0) {
            s = mins / 60 + " " + NC.getString(R.string.hrs) + " " + mins % 60 + " " + NC.getString(R.string.mins) + " ";
        }
        return s;
    }

    public void calculateAndUpdateFare() {
        double discount_amount = 0.0;
        os_fare = os_plan_fare;

        if (!et_time_hour.getText().toString().isEmpty() && !et_time_mins.getText().toString().isEmpty() && !et_total_disatnce.getText().toString().isEmpty() && !p_dis.isEmpty()) {
            os_duration = Double.parseDouble(et_time_hour.getText().toString()) + (Double.parseDouble(et_time_mins.getText().toString()) / 60);
            if (os_duration > os_plan_duration) {
                os_minute_fare = ((os_duration - (os_plan_duration)) * os_additional_fare_per_hour);
                os_fare += os_minute_fare;
            }
            os_distance = Double.parseDouble(et_total_disatnce.getText().toString());
            if (os_distance > os_plan_distance) {
                os_fare += ((os_distance - os_plan_distance) * os_additional_fare_per_distance);
            }


            if (promo_percentage > 0 && !promo_type.equals("1")) {
                discount_amount = os_fare * promo_percentage / 100;
                os_fare -= discount_amount;
                // b_discount.setText(String.format(Locale.UK, "%.2d", (os_fare * promo_percentage / 100)+" "));
                b_discount.setText(SessionSave.getSession("Currency", getActivity()) + " " + df.format(discount_amount));
            } else if (promo_type.equals("1")) {
                discount_amount = Double.parseDouble(f_farediscount);
                os_fare -= discount_amount;
                b_discount.setText(SessionSave.getSession("Currency", getActivity()) + " " + df.format(discount_amount));
            }
            if (os_tax != 0) {
                tax = os_fare * os_tax / 100;
                b_tax.setText(SessionSave.getSession("Currency", getActivity()) + " " + df.format(((tax))));
            }

            totalamountTxt.setText(String.format(Locale.ENGLISH, df.format((os_fare + tax))));
            amountpayTxt.setText(String.format(Locale.ENGLISH, df.format(((os_fare + tax) - m_walletamt))));

            Systems.out.println("ettttttttt" + os_fare + "__" + os_plan_fare);

            et_tripFare.setText(FontHelper.convertfromArabic((df.format(Double.parseDouble(FontHelper.convertfromArabic(String.valueOf(os_fare))) + discount_amount))));
        }
    }


    // Initialize the views on layout
    public void Initialize(View view) {
        Colorchange.ChangeColor((ViewGroup) view, getActivity());
        btn_emergency = view.findViewById(R.id.btn_emergency);
        btn_emergency.setVisibility(View.GONE);
       /* if (SessionSave.getSession(TaxiUtil.sosEnable, getActivity(), false)) {
            btn_emergency.setVisibility(View.VISIBLE);
        }*/

        btn_emergency.setOnClickListener(view12 -> {
            final View view1 = View.inflate(getActivity(), R.layout.emergency_alert, null);
            Dialog emergency_dialog = new Dialog(getActivity(), R.style.dialogwinddow);
            emergency_dialog.setContentView(view1);
            emergency_dialog.setCancelable(true);
            emergency_dialog.show();
            final Button button_success = emergency_dialog.findViewById(R.id.button_success);
            final Button button_failure = emergency_dialog.findViewById(R.id.button_failure);
            button_success.setOnClickListener(view2 -> {
                emergency_dialog.dismiss();
                startSOSService();
            });
            button_failure.setOnClickListener(view2 -> emergency_dialog.dismiss());
        });
        TaxiUtil.sContext = getActivity();
        TaxiUtil.current_act = "FareCalcAct";
        TaxiUtil.mActivitylist.add(getActivity());
        activity = this;
        mFlagger = getActivity();


        FontHelper.applyFont(getActivity(), view.findViewById(R.id.id_farelay));
        fabInfo = view.findViewById(R.id.fabInfo);
        rootLay = view.findViewById(R.id.id_farelay);
        tv_startTime = view.findViewById(R.id.tv_startTime);
        tv_dropTime = view.findViewById(R.id.tv_dropTime);
        tv_tripFare = view.findViewById(R.id.tv_trip_fare);
        tv_total_disatnce = view.findViewById(R.id.tv_total_distance);
        et_time_hour = view.findViewById(R.id.ed_time_hour);
        et_time_mins = view.findViewById(R.id.ed_time_mins);
        et_tripFare = view.findViewById(R.id.ed_trip_fare);
        et_total_disatnce = view.findViewById(R.id.ed_total_distance);
        layoutNormal = view.findViewById(R.id.normal_fare_layout);
        layoutOutstation = view.findViewById(R.id.outstation_fare_layout);

        b_pickuplocation = view.findViewById(R.id.pickuplocTxt);
        b_droplocation = view.findViewById(R.id.droplocTxt);
        actdistanceTxt = view.findViewById(R.id.actdistanceTxt);
        metricTxt = view.findViewById(R.id.metricTxt);
        HeadTitle = view.findViewById(R.id.headerTxt);
        distance_lay = view.findViewById(R.id.distance_lay);
        minutes_lay = view.findViewById(R.id.minutes_lay);
        waiting_lay = view.findViewById(R.id.waiting_lay);
        minutes_value = view.findViewById(R.id.min_value);
        eve_fare_lay = view.findViewById(R.id.eve_fare_lay);
        night_fare_lay = view.findViewById(R.id.night_fare_lay);
        totalamountTxt_lay = view.findViewById(R.id.totalamountTxt_lay);
        eve_fare = view.findViewById(R.id.eve_fare);
        night_fare = view.findViewById(R.id.night_fare);
        totalamountTxt = view.findViewById(R.id.totalamountTxt);
        promopercentTxt = view.findViewById(R.id.promopercentage);
        walletamountTxt = view.findViewById(R.id.walletamountTxt);
        walletamountCurrency = view.findViewById(R.id.walletamountCurrency);
        amountpayCurrency = view.findViewById(R.id.amountpayCurrency);
        amountpayTxt = view.findViewById(R.id.amountpayTxt);
        tax_lay = view.findViewById(R.id.tax_lay);
        txtCmp = view.findViewById(R.id.txtcmpTax);
        slideImg = view.findViewById(R.id.slideImg);
        walletlay = view.findViewById(R.id.walletlay);
        paylay = view.findViewById(R.id.paylay);
        slideImg.setVisibility(View.VISIBLE);
        promoLayout = view.findViewById(R.id.discountlayout);
        lay_fare = view.findViewById(R.id.lay_fare);
        b_total_amt_curency = view.findViewById(R.id.toatalamtCurrency);
        b_waitingcost = view.findViewById(R.id.waitingcost);
        idwaitingcost = view.findViewById(R.id.idwaitingcost);
        b_tax = view.findViewById(R.id.tax);
        b_discount = view.findViewById(R.id.discount);
        v_trip_fare = view.findViewById(R.id.v_trip_fare);
        radiocashButton = view.findViewById(R.id.rbtn_cash);
        vid_discount = view.findViewById(R.id.vid_discount);
        radiowalletButton = view.findViewById(R.id.rbtn_wallet);
        radiocardButton = view.findViewById(R.id.rbtn_card);
        radiouncardButton = view.findViewById(R.id.rbtn_uncard);
        HeadTitle.setText("" + NC.getResources().getString(R.string.fare_calculator));
        details = this.getArguments();


        try {
//            // If Directly comes from end trip page(OngoingAct)
            if (details != null) {
                if (details.getString("from") != null && details.getString("from").equalsIgnoreCase("direct")) {
                    message = details.getString("message");
                    setFareCalculatorScreen();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        NetworkStatus.isOnline(getActivity());

        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).left_img.setVisibility(View.GONE);
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.payment_complete));
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.GONE);

        et_time_hour.setOnFocusChangeListener((view, hasFocus) -> {
            try {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(et_time_hour.getText().toString())) {
                        et_time_hour.setText("00");
                        os_hr_min[0] = et_time_hour.getText().toString();
                        calculateAndUpdateFare();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        et_time_mins.setOnFocusChangeListener((view, hasFocus) -> {
            try {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(et_time_mins.getText().toString())) {


                        et_time_mins.setText("00");
                        os_hr_min[1] = et_time_mins.getText().toString();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        et_total_disatnce.setOnFocusChangeListener((view, hasFocus) -> {

            try {
                if (trip_type.equals("3")) {
                    if (!hasFocus) {
                        String text = et_total_disatnce.getText().toString();
                        if (TextUtils.isEmpty(text)) {
                            et_total_disatnce.setText("0.00");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        et_time_hour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (trip_type.equals("3")) {
                        if (!et_time_hour.getText().toString().isEmpty())
                            os_hr_min[0] = et_time_hour.getText().toString();
                        if (!et_time_hour.getText().toString().isEmpty()) {
                            calculateAndUpdateFare();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_time_mins.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (trip_type.equals("3")) {
                        if (!et_time_mins.getText().toString().isEmpty())
                            os_hr_min[1] = et_time_mins.getText().toString();

                        if (!et_time_mins.getText().toString().isEmpty()) {
                            if (Integer.parseInt(os_hr_min[1]) > 59) {
                                os_hr_min[1] = "59";
                                et_time_mins.setText(os_hr_min[1]);
                            }
                            calculateAndUpdateFare();
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_total_disatnce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (trip_type.equals("3")) {
                        if (!et_total_disatnce.getText().toString().isEmpty()) {
                            try {
                                os_distance = Double.parseDouble(et_total_disatnce.getText().toString());
                                calculateAndUpdateFare();
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        et_tripFare.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (trip_type.equals("3")) {
                        if (!et_tripFare.getText().toString().isEmpty()) {
                            double discount_amount = 0;
                            double faree = Double.parseDouble(FontHelper.convertfromArabic(et_tripFare.getText().toString()));

                            if (promo_percentage > 0 && !promo_type.equals("1")) {
                                discount_amount = faree * promo_percentage / 100;
                                b_discount.setText(SessionSave.getSession("Currency", getActivity()) + " " + df.format(discount_amount));
                            } else if (promo_type.equals("1")) {
                                Systems.out.println("os_fareed__" + os_fare + "___" + discount_amount);
                                discount_amount = Double.parseDouble(f_farediscount);
                                Systems.out.println("os_fareed__*" + os_fare + "___" + discount_amount);
                            }


                            os_fare = faree - discount_amount;
                            if (os_tax != 0) {
                                tax = os_fare * os_tax / 100;
                                b_tax.setText(SessionSave.getSession("Currency", getActivity()) + " " + df.format(((tax))));
                            }
                            totalamountTxt.setText("" + (os_fare + tax));
                            amountpayTxt.setText("" + (os_fare + tax - m_walletamt));

                            //                    calculateAndUpdateFare();}
                        } else {
                            totalamountTxt.setText("0.00");
                            amountpayTxt.setText("0.00");
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });

        et_tripFare.setOnFocusChangeListener((view, hasFocus) -> {
            try {
                if (trip_type.equals("3")) {
                    if (!hasFocus) {
                        String text = et_tripFare.getText().toString();
                        if (TextUtils.isEmpty(text)) {
                            et_tripFare.setText("0.00");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        fabInfo.setOnClickListener(v -> {
            Fragment ff = new TripDetailNewFrag();
            Bundle b = new Bundle();
            b.putString("trip_id", f_tripid);
            b.putString("title", "");
            b.putString("tripDetailResponse", message);
            System.out.println("Response when info clicked.. " + message);
            b.putBoolean("isFromFareScreen", true);
            ff.setArguments(b);
            details = null;
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, ff).addToBackStack(null).commit();
        });
    }


    @Override
    public void trigger_FragPopFront() {
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.payment_complete));
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
        if (details == null) {
            if (!SessionSave.getSession("trip_id", getActivity()).equalsIgnoreCase("")) {
                if (TaxiUtil.isOnline(getActivity())) {
                    try {
                        SessionSave.saveSessionInt(TaxiUtil.CURRENT_TRIP, Integer.parseInt(SessionSave.getSession("trip_id", getActivity())), getActivity());
                        JSONObject j = new JSONObject();
                        j.put("trip_id", Integer.parseInt(SessionSave.getSession("trip_id", getActivity())));
                        j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
                        new TripDetail("type=get_trip_detail", j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("No Internet", "Please check the internet connection");
                }
            }
        }
    }

    /**
     * getActivity() class used to get the trip details
     * <p/>
     * <p>
     * getActivity() class used to get the trip details
     * </p>
     *
     * @author developer
     */
    private class TripDetail implements APIResult {

        public TripDetail(String url, JSONObject data) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON(getActivity(), this, data, false).execute(url);
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            if (isSuccess) {
                try {

                    JSONObject json = new JSONObject(result);

                    if (json.getInt("status") == 1 && json.getJSONObject("detail").getInt("travel_status") != 0) {
                        mTravelstatus = Integer.parseInt(json.getJSONObject("detail").getString("travel_status"));
                        if (mTravelstatus == 5) {
                            message = String.valueOf(json.getJSONObject("complete_trip"));
                            setFareCalculatorScreen();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (getActivity() != null)
                    getActivity().runOnUiThread(() -> CToast.ShowToast(getActivity(), getString(R.string.server_con_error)));
            }
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        if (keyboardListenersAttached) {
//            rootLay.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
            rootLay.getViewTreeObserver().removeOnGlobalLayoutListener(keyboardLayoutListener);
        }
    }

    @Override
    public void onStop() {
        Utility.closeDialog(mDialog);
        ((MainHomeFragmentActivity) getActivity()).disableSlide();
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.mybookings));
        ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.GONE);

        super.onStop();
    }

    /**
     * This for update the fare calculator page with API result.
     */
    @SuppressLint("SdCardPath")
    private void setFareCalculatorScreen() {
        details = null;
        if (message != null) {
            try {
                JSONObject obj = new JSONObject(message);
                JSONObject json = obj.getJSONObject("detail");
                trip_type = json.getString("trip_type");
                promo_type = json.getString("promo_type");
                if (json.has("existing_wallet_amount")) {
                    existing_wallet_amount = json.getString("existing_wallet_amount");
                }
                if (json.has("distance_fare")) {
                    distanceFare = json.getString("distance_fare");
                }
                if (trip_type.equals("3")) {
                    layoutNormal.setVisibility(View.GONE);
                    layoutOutstation.setVisibility(View.VISIBLE);
                    waiting_lay.setVisibility(View.GONE);
                    fabInfo.setVisibility(View.GONE);
                } else {
                    layoutNormal.setVisibility(View.VISIBLE);
                    layoutOutstation.setVisibility(View.GONE);
                    waiting_lay.setVisibility(View.GONE);
                    fabInfo.setVisibility(View.GONE);
                    if (!trip_type.equals("2"))
                        setNormalTripFareScreen();
                }
                os_distance = json.getDouble("distance");
                os_duration = (json.getDouble("os_duration") / 60);
                os_fare = Double.parseDouble(json.getString("trip_fare"));

                os_tax = (json.getDouble("company_tax"));


                promo_percentage = (json.getDouble("promo_discount_per"));

                os_plan_fare = json.getDouble("os_plan_fare");
                os_plan_distance = json.getDouble("os_plan_distance");
                os_plan_duration = json.getDouble("os_plan_duration") / 60;
                os_additional_fare_per_distance = json.getDouble("os_additional_fare_per_distance");
                os_additional_fare_per_hour = json.getDouble("os_additional_fare_per_hour");
                os_hr_min = new String[2];
                os_hr_min[0] = String.valueOf((int) json.getDouble("os_duration") / 60);
                os_hr_min[1] = String.valueOf((int) json.getDouble("os_duration") % 60);
                et_time_hour.setText(os_hr_min[0]);
                et_time_mins.setText(os_hr_min[1]);
                et_total_disatnce.setText(String.valueOf(os_distance));


                f_metric = json.getString("metric");
                tv_total_disatnce.setText(NC.getResources().getString(R.string.total_distance) + " (" + f_metric.toLowerCase() + ")");
                tv_tripFare.setText(NC.getResources().getString(R.string.trip_fare) + "(" + SessionSave.getSession("Currency", getActivity()) + ")");
                et_tripFare.setText(FontHelper.convertfromArabic(json.getString("trip_fare")));
                tv_startTime.setText(json.getString("trip_start_time"));
                tv_dropTime.setText(json.getString("trip_end_time"));
                f_tripid = json.getString("trip_id");
                f_distance = json.getString("distance");
                f_totalfare = json.getString("subtotal_fare");
                f_nightfareapplicable = json.getString("nightfare_applicable");
                f_nightfare = json.getString("nightfare");
                f_eveningfare_applicable = json.getString("eveningfare_applicable");
                f_eveningfare = json.getString("eveningfare");
                f_pickup = json.getString("pickup");
                drop_location = json.getString("drop");
                f_waitingtime = json.getString("waiting_time");
                f_waitingcost = json.getString("waiting_cost");
                f_taxamount = json.getString("tax_amount");
                f_tripfare = json.getString("trip_fare");
                f_payamt = json.getString("total_fare");
                f_walletamt = json.getString("wallet_amount_used");
                f_minutes_traveled = json.getString("minutes_traveled");
                f_minutes_fare = json.getString("minutes_fare");
                f_farediscount = json.getString("promodiscount_amount");
                base_fare = json.getString("base_fare");
                cmpTax = json.getString("company_tax");

                if (json.has("pending_cancel_amount"))
                    pending_cancel_amount = json.getString("pending_cancel_amount");

                fare_per_minute = json.getString("fare_per_minute");
                waiting_fare_minutes = json.getString("waiting_fare_minutes");
                trip_minutes = json.getString("trip_minutes");
                min_distance_status = json.getInt("min_distance_status");
                subtotal = json.getString("subtotal");
                new_distance_fare = json.getString("new_distance_fare");
                new_base_fare = json.getString("new_base_fare");
                distance_fare_metric = json.getString("distance_fare_metric");
                amt = json.getString("amt");
                promocode_fare = json.getString("promocode_fare");
                tax_fare = json.getString("tax_fare");
                nightfare = json.getString("nightfare");
                eveningfare = json.getString("eveningfare");

                if (f_eveningfare_applicable.equalsIgnoreCase("1") && (trip_type.equals("2") || trip_type.equals("3"))) {
                    eve_fare.setText("" + f_eveningfare);
                    eve_fare_lay.setVisibility(View.VISIBLE);
                } else
                    eve_fare_lay.setVisibility(View.GONE);

                if (f_nightfareapplicable.equalsIgnoreCase("1") && (trip_type.equals("2") || trip_type.equals("3"))) {
                    night_fare.setText("" + f_nightfare);
                    night_fare_lay.setVisibility(View.VISIBLE);
                } else
                    night_fare_lay.setVisibility(View.GONE);

                try {

                    fare_calculation_type = json.getString("fare_calculation_type");
                    if (fare_calculation_type.trim().equals("1"))
                        minutes_lay.setVisibility(View.GONE);
                    else if (fare_calculation_type.trim().equals("2"))
                        distance_lay.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (f_walletamt.length() != 0)
                    m_walletamt = Double.parseDouble(f_walletamt);
                f_walletamt = String.format(Locale.UK, "%.2f", m_walletamt);
                Systems.out.println("char convert" + f_payamt);
                if (f_payamt.length() != 0)
                    m_payamt = Double.parseDouble(f_payamt);
                f_payamt = String.format(Locale.UK, "%.2f", m_payamt);
                if (f_waitingcost.length() != 0)
                    m_waitingcost = Double.parseDouble(f_waitingcost);
                f_waitingcost = String.format(Locale.UK, "%.2f", m_waitingcost);
                if (f_totalfare.length() != 0)
                    m_totalfare = Double.parseDouble(f_totalfare);
                f_totalfare = String.format(Locale.UK, "%.2f", m_totalfare);
                if (f_distance.length() != 0)
                    m_distance = Double.parseDouble(f_distance);
                f_distance = String.format(Locale.UK, "%.2f", m_distance);
                if (f_tripfare.length() != 0)
                    m_tripfare = Double.parseDouble(f_tripfare);
                f_tripfare = String.format(Locale.UK, "%.2f", m_tripfare);
                if (f_taxamount.length() != 0)
                    m_taxamount = Double.parseDouble(f_taxamount);
                f_taxamount = String.format(Locale.UK, "%.2f", m_taxamount);
                if (f_waitingtime.equals("0")) {
                    idwaitingcost.setText("" + NC.getResources().getString(R.string.waiting_cost) + "(" + "00:00" + ")");
                } else {
                    idwaitingcost.setText("" + NC.getResources().getString(R.string.waiting_cost) + "(" + f_waitingtime + ")");
                }
                if (!cmpTax.trim().equals("0"))
                    txtCmp.setText("" + NC.getResources().getString(R.string.tax) + cmpTax + "" + NC.getResources().getString(R.string.tax_percent));
                else
                    tax_lay.setVisibility(View.GONE);
                v_trip_fare.setText("" + SessionSave.getSession("Currency", getActivity()) + " " + f_tripfare);
                p_dis = String.valueOf(promo_percentage);

                Systems.out.println("promo_dis" + p_dis);
                if (!p_dis.trim().equals("")) {
                    if (!promo_type.equals("1") && !p_dis.equals("0")) {
                        if (promo_type.equals("2"))
                            promopercentTxt.setText("" + NC.getResources().getString(R.string.discount) + "(" + p_dis + "" + NC.getResources().getString(R.string.tax_percent));
                        else
                            promopercentTxt.setText("" + NC.getResources().getString(R.string.discount));
                        if (Double.parseDouble(f_farediscount) > 0.0) {
                            Systems.out.println("aaaaaaaaaaa1" + f_farediscount + "_____________" + Double.parseDouble(f_farediscount));
                            b_discount.setText("" + SessionSave.getSession("Currency", getActivity()) + " " + f_farediscount);
                        } else {
                            Systems.out.println("aaaaaaaaaaa2");
                            promoLayout.setVisibility(View.GONE);
                        }
                    } else {
                        if (Double.parseDouble(json.getString("promodiscount_amount")) >= 0.0) {
                            b_discount.setText("" + SessionSave.getSession("Currency", getActivity()) + " " + json.getString("promodiscount_amount"));
                        } else {
                            promoLayout.setVisibility(View.GONE);
                        }
                    }

                    vid_discount.setVisibility(View.GONE);

                } else {
                    promoLayout.setVisibility(View.GONE);
                    vid_discount.setVisibility(View.GONE);
                }
                if (promoamt.equals("0")) {
                    promoLayout.setVisibility(View.GONE);
                }
                metricTxt.setText(f_metric.toLowerCase());
                actdistanceTxt.setText("" + f_distance);
                minutes_value.setText(getStringFromTime(Long.parseLong(f_minutes_traveled)));
                b_pickuplocation.setText(f_pickup);
                b_droplocation.setText("" + drop_location);
                b_total_amt_curency.setText("" + SessionSave.getSession("Currency", getActivity()) + " ");
                if (!f_waitingcost.equals("0")) {
                    b_waitingcost.setText("" + SessionSave.getSession("Currency", getActivity()) + " " + f_waitingcost);
                } else {
                    waiting_lay.setVisibility(View.GONE);
                }
                b_tax.setText("" + SessionSave.getSession("Currency", getActivity()) + " " + f_taxamount);
                f_fare = m_totalfare;
                f_total = f_fare + f_tips;
                if (f_total != 0.0) {
                    totalamountTxt.setText("" + String.format(Locale.UK, "%.2f", f_total));
                } else {
                    totalamountTxt_lay.setVisibility(View.GONE);
                }
                JSONArray ary = new JSONArray(json.getString("gateway_details"));
                int length = ary.length();
                walletamountCurrency.setText(SessionSave.getSession("Currency", getActivity()) + " ");
                amountpayCurrency.setText(SessionSave.getSession("Currency", getActivity()) + " ");
                if (m_walletamt > 0) {
                    walletlay.setVisibility(View.VISIBLE);
                    walletamountTxt.setText(f_walletamt);
                    paylay.setVisibility(View.VISIBLE);
                    amountpayTxt.setText(f_payamt);
                }
                amountpayTxt.setText(f_payamt);
                for (int i = 0; i < length; i++) {
                    String paymentModeDefault = ary.getJSONObject(i).getString("pay_mod_default");
                    String paymentMode_Id = ary.getJSONObject(i).getString("pay_mod_id");
                    if (paymentMode_Id.equalsIgnoreCase("5")) {
                        radiowalletButton.setVisibility(View.VISIBLE);
                        if (paymentModeDefault.equals("1")) {
                            radiowalletButton.setTextColor(Color.DKGRAY);
                        }
                    } else if (paymentMode_Id.equalsIgnoreCase("1")) {
                        radiocashButton.setVisibility(View.VISIBLE);
                        if (paymentModeDefault.equals("1")) {
                            radiocashButton.setTextColor(Color.DKGRAY);
                        }
                    } else if (paymentMode_Id.equalsIgnoreCase("2")) {
                        radiocardButton.setVisibility(View.VISIBLE);
                        if (paymentModeDefault.equals("1")) {
                            radiocardButton.setTextColor(Color.DKGRAY);
                        }
                    } else if (paymentMode_Id.equalsIgnoreCase("3")) {

                        radiouncardButton.setVisibility(View.VISIBLE);
                        if (paymentModeDefault.equals("1")) {
                            radiouncardButton.setTextColor(Color.DKGRAY);
                        }
                    } else if (paymentMode_Id.equalsIgnoreCase("4")) {

                    }
                }
                if (trip_type.equals("3")) {
                    calculateAndUpdateFare();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // The following process will done while select the payment mode as wallet.
        radiowalletButton.setOnClickListener(v -> {

            radiowalletButton.setTextColor(Color.DKGRAY);
            radiocashButton.setTextColor(Color.LTGRAY);
            radiocardButton.setTextColor(Color.LTGRAY);
            radiouncardButton.setTextColor(Color.LTGRAY);
            f_total = f_fare + f_tips;
            totalamountTxt.setText("" + String.format(Locale.UK, "%.2f", f_total));
            f_paymodid = "5";

            if (!f_payamt.equals("") && !existing_wallet_amount.equals("")) {
                if (Double.parseDouble(f_payamt) < Double.parseDouble(existing_wallet_amount)) {
                    confirmCompleteTrip(getActivity());
                } else {
                    dialog1 = Utility.alert_view_dialog(getActivity(), NC.getResources().getString(R.string.message), NC.getResources().getString(R.string.wallet_amount_alert), NC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancell), false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            details = null;
                            ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
                            ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
                            ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getResources().getString(R.string.wallet));
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new WalletFrag()).addToBackStack(null).commit();
                        }
                    }, (dialog, which) -> dialog.dismiss(), "");
                }
            } else {
                confirmCompleteTrip(getActivity());
            }
        });
        // The following process will done while select the payment mode as card. And it shows the dialog to get the CVV number.
        radiocardButton.setOnClickListener(v -> {

            radiocashButton.setTextColor(Color.LTGRAY);
            radiocardButton.setTextColor(Color.DKGRAY);
            radiouncardButton.setTextColor(Color.LTGRAY);

            radiocardButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.credit_card_unfocus, 0, 0);
            radiocashButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cash_unfocus, 0, 0);
            radiouncardButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.newcard_unfocus, 0, 0);
            f_total = f_fare + f_tips;
            f_paymodid = "2";
            confirmCompleteTrip(getActivity());
        });
        // The following process will done while select the payment mode as uncard.
        radiouncardButton.setOnClickListener(v -> {
            details = null;
            radiocashButton.setTextColor(Color.LTGRAY);
            radiocardButton.setTextColor(Color.LTGRAY);
            radiouncardButton.setTextColor(Color.DKGRAY);
            radiocardButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.credit_card_unfocus, 0, 0);
            radiocashButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cash_unfocus, 0, 0);
            radiouncardButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.newcard_unfocus, 0, 0);

            Double fare = Double.parseDouble(et_tripFare.getText().toString());

            if (trip_type.equals("3") && fare <= 0) {
                CToast.ShowToast(getActivity(), NC.getString(R.string.endter_valid_fare));
            } else {
                if (f_total > 0) {

                    dialog1 = Utility.alert_view_dialog(getActivity(), NC.getResources().getString(R.string.message), NC.getResources().getString(R.string.confir_complete_payment), NC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancell), false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            /*
                            razor_total = String.valueOf(f_total);
                            try {
                                JSONObject j = new JSONObject();
                                j.put("passenger_id", SessionSave.getSession("Id", getActivity()));
                                j.put("amount", razor_total);
                                final String url = "type=get_order_id_razorpay";
                                new FarecalRazorpay(url, j);
                            } catch (Exception e) {
                                // TODO: handle exception
                                e.printStackTrace();
                            }

                             */

                            Intent payintent = new Intent(getActivity(), PayuncardAct.class);
                            Bundle bun = new Bundle();
                            bun.putString("info", "Uncard");
                            bun.putString("message", message);
                            if (trip_type.equals("3")) {
                                if (!SessionSave.getSession(LANG, getActivity()).equals("en")) {
                                    bun.putString("f_fare", amountpayTxt.getText().toString());
                                    bun.putString("f_tips", Double.toString(f_tips));
                                    bun.putString("f_total", amountpayTxt.getText().toString());
                                } else {
                                    bun.putString("f_fare", FontHelper.convertfromArabic(amountpayTxt.getText().toString()));
                                    bun.putString("f_tips", FontHelper.convertfromArabic(Double.toString(f_tips)));
                                    bun.putString("f_total", FontHelper.convertfromArabic(amountpayTxt.getText().toString()));
                                }
                            } else {
                                if (!SessionSave.getSession(LANG, getActivity()).equals("en")) {
                                    bun.putString("f_fare", FontHelper.convertfromArabic(f_payamt));
                                    bun.putString("f_tips", FontHelper.convertfromArabic(Double.toString(f_tips)));
                                    bun.putString("f_total", FontHelper.convertfromArabic(Double.toString(f_total)));
                                } else {
                                    bun.putString("f_fare", f_payamt);
                                    bun.putString("f_tips", Double.toString(f_tips));
                                    bun.putString("f_total", Double.toString(f_total));
                                }
                            }
                            payintent.putExtras(bun);
                            startActivity(payintent);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, "");

                }
            }
        });

    }

    private void setNormalTripFareScreen() {
        layoutNormal.setVisibility(View.GONE);
        paylay.setVisibility(View.VISIBLE);
        promoLayout.setVisibility(View.GONE);
        tax_lay.setVisibility(View.GONE);
        totalamountTxt_lay.setVisibility(View.GONE);
        fabInfo.setVisibility(View.VISIBLE);
    }

    private void confirmCompleteTrip(final Activity mContext) {
        double fare = 0;
        try {
            if (!et_tripFare.getText().toString().trim().equals(""))
                fare = Double.parseDouble(et_tripFare.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (trip_type.equals("3") && fare <= 0) {
            CToast.ShowToast(mContext, NC.getString(R.string.endter_valid_fare));
        } else {
            dialog1 = Utility.alert_view_dialog(mContext, NC.getResources().getString(R.string.message), NC.getResources().getString(R.string.confir_complete_payment), NC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancell), false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (NetworkStatus.isOnline(mContext)) {
                        dialog.dismiss();
                        callurl();
                    } else {
                        CToast.ShowToast(mContext, NC.getResources().getString(R.string.check_net_connection));
                    }
                }
            }, (dialog, which) -> dialog.dismiss(), "");
        }
    }

    /**
     * Common API for fareupdate the following method for arrange the inputs and calls the API.
     */
    private void callurl() {

        String url = "type=tripfare_update";
        try {
            JSONObject j = new JSONObject();

            if (trip_type.equals("3")) {
                j.put("os_distance", os_distance);
                j.put("os_actual_amount", "" + amountpayTxt.getText().toString());
                j.put("os_trip_fare", df.format((os_fare)));
                j.put("os_promodiscount_amount", b_discount.getText().toString());
                j.put("os_minutes_traveled", (os_duration * 60));
                j.put("os_minutes_fare", os_minute_fare);
            }
            j.put("distance", f_distance);
            j.put("actual_amount", "" + f_total);
            j.put("trip_fare", f_tripfare);
            j.put("promodiscount_amount", f_farediscount);
            j.put("fare", "" + f_payamt);

            j.put("existing_wallet_amount", existing_wallet_amount);


            j.put("trip_type", trip_type);
            j.put("trip_id", f_tripid);

            j.put("distance_fare", distanceFare);

            j.put("actual_distance", f_distance);

            j.put("base_fare", base_fare);
            //Karthick Update


            //nandhini
            j.put("tips", "");
            j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
            j.put("passenger_promo_discount", promotax);
            j.put("tax_amount", f_taxamount);
            j.put("remarks", "");
            j.put("nightfare_applicable", f_nightfareapplicable);
            j.put("nightfare", f_nightfare);
            j.put("eveningfare_applicable", f_eveningfare_applicable);
            j.put("eveningfare", f_eveningfare);
            j.put("waiting_time", f_waitingtime);
            j.put("waiting_cost", f_waitingcost);
            j.put("creditcard_no", "");
            j.put("creditcard_cvv", Cvv);
            j.put("company_tax", cmpTax);
            j.put("expmonth", "");
            j.put("expyear", "");
            j.put("pay_mod_id", f_paymodid);
            j.put("passenger_discount", p_dis);
            j.put("minutes_traveled", f_minutes_traveled);
            j.put("minutes_fare", f_minutes_fare);
            j.put("fare_calculation_type", fare_calculation_type);
            j.put("model_fare_type", SessionSave.getSession("model_fare_type", getActivity()));
            j.put("pending_cancel_amount", pending_cancel_amount);
            new FareUpdate(url, j);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Closing the alert dialog.
     */
    public void closeDialog() {
        try {
            if (mDialog != null)
                if (mDialog.isShowing())
                    mDialog.dismiss();
        } catch (Exception e) {

        }
    }

    /**
     * Showing the alert dialog
     */
    public void showDialog() {
        try {
            if (NetworkStatus.isOnline(getActivity())) {
                View view = View.inflate(getActivity(), R.layout.progress_bar, null);
                mDialog = new Dialog(getActivity(), R.style.dialogwinddow);
                mDialog.setContentView(view);
                mDialog.setCancelable(false);
                mDialog.show();

                ImageView iv = mDialog.findViewById(R.id.giff);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                Glide.with(getActivity())
                        .load(R.raw.loading_anim)
                        .into(imageViewTarget);

            }
        } catch (Exception e) {

        }

    }

    private void startSOSService() {
        SessionSave.saveSession("sos_id", SessionSave.getSession(PASS_ID, getActivity()), getActivity());
        SessionSave.saveSession("user_type", "d", getActivity());


        // getActivity().startService(new Intent(getActivity(), SOSService.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //       startSOSService();
                    }
                }
                return;
            }
        }
    }

    @Override
    public void positiveButtonClick(DialogInterface dialog, int id, String s) {
        if (dialog != null)
            dialog.dismiss();
    }

    @Override
    public void negativeButtonClick(DialogInterface dialog, int id, String s) {
        if (dialog != null)
            dialog.dismiss();
    }

    /**
     * This class helps to call the Fare Update API,get the result and parse it.
     */

    /**
     * This class helps to call the Fare Update API,get the result and parse it.
     */
    private class FareUpdate implements APIResult {
        String msg = "";

        public FareUpdate(String url, JSONObject data) {

            if (NetworkStatus.isOnline(getActivity())) {
                showDialog();
                new APIService_Retrofit_JSON(getActivity(), this, data, false).execute(url);
            } else {

                dialog1 = Utility.alert_view(getActivity(), "", "" + NC.getResources().getString(R.string.check_internet_connection), NC.getResources().getString(R.string.ok),
                        "", true, FareCalcAct.this, "");

            }
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            closeDialog();
            if (isSuccess) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {

                        Intent jobintent = new Intent(getActivity(), MainHomeFragmentActivity.class);
                        Bundle bun = new Bundle();
                        bun.putString("message", result);
                        jobintent.putExtras(bun);
                        requireActivity().startActivity(jobintent);
                    } else if (json.getInt("status") == -9) {
                        msg = json.getString("message");
                        lay_fare.setVisibility(View.VISIBLE);

                        dialog1 = Utility.alert_view(getActivity(), "", "" + msg, NC.getResources().getString(R.string.ok),
                                "", true, FareCalcAct.this, "");
                    } else if (json.getInt("status") == 0) {
                        msg = json.getString("message");
                        lay_fare.setVisibility(View.VISIBLE);
                        dialog1 = Utility.alert_view(getActivity(), "", "" + msg, NC.getResources().getString(R.string.ok),
                                "", true, FareCalcAct.this, "");
                    } else if (json.getInt("status") == -1) {
                        msg = json.getString("message");

                        dialog1 = Utility.alert_view(getActivity(), "", "" + msg, NC.getResources().getString(R.string.ok),
                                "", true, FareCalcAct.this, "");
                        SessionSave.saveSession("trip_id", "", getActivity());
                        SessionSave.saveSession("status", "F", getActivity());
                        JSONObject jsonDriver = json.getJSONObject("driver_statistics");
                        SessionSave.saveSession("driver_statistics", "" + jsonDriver, getActivity());
                        Intent intent = new Intent(getActivity(), MainHomeFragmentActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        msg = json.getString("message");
                        lay_fare.setVisibility(View.VISIBLE);

                        dialog1 = Utility.alert_view(getActivity(), "", "" + msg, NC.getResources().getString(R.string.ok),
                                "", true, FareCalcAct.this, "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                getActivity().runOnUiThread(() -> CToast.ShowToast(getActivity(), getString(R.string.server_error)));
                closeDialog();
                lay_fare.setVisibility(View.VISIBLE);
            }
        }
    }

    private class FarecalRazorpay implements APIResult {
        private FarecalRazorpay(final String url, JSONObject data) {
            if (NetworkStatus.isOnline(getActivity())) {
                new APIService_Retrofit_JSON(getActivity(), this, data, false).execute(url);
            } else {
                Log.e("No Internet Available", "no internet");
            }
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            if (isSuccess) {
                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        //System.out.println("Wallet_amoutn...."+json.getString("order_id"));
                        ((MainHomeFragmentActivity) getActivity()).farePayment(razor_total, message, amountpayTxt.getText().toString(), Double.toString(f_tips), amountpayTxt.getText().toString(), "2", json.getString("order_id"));
                    } else {

                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (result != null) {
                    Toast.makeText(getActivity(), NC.getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}