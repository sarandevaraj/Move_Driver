package com.movedriverdriver.driver;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.movedriverdriver.BuildConfig;
import com.movedriverdriver.R;
import com.movedriverdriver.driver.data.DriverCommonData;
import com.movedriverdriver.driver.data.apiData.DriverApiRequestData;
import com.movedriverdriver.driver.data.apiData.DriverStreetCompleteResponse;
import com.movedriverdriver.driver.data.apiData.DriverTripDetailResponse;
import com.movedriverdriver.driver.interfaces.DriverAPIResult;
import com.movedriverdriver.driver.interfaces.DriverClickInterface;
import com.movedriverdriver.driver.service.DriverAPIService_Retrofit_JSON;
import com.movedriverdriver.driver.service.DriverCoreClient;
import com.movedriverdriver.driver.service.DriverNonActivity;
import com.movedriverdriver.driver.service.LocationUpdate;
import com.movedriverdriver.driver.service.DriverRetrofitCallbackClass;
import com.movedriverdriver.driver.service.DriverServiceGenerator;
import com.movedriverdriver.driver.utils.DriverCToast;
import com.movedriverdriver.driver.utils.DirverColorchange;
import com.movedriverdriver.driver.utils.DriverFontHelper;
import com.movedriverdriver.driver.utils.DriverLocationDb;
import com.movedriverdriver.driver.utils.DriverNC;
import com.movedriverdriver.driver.utils.DriverNetworkStatus;
import com.movedriverdriver.driver.utils.DriverSessionSave;
import com.movedriverdriver.driver.utils.DriverSystems;
import com.movedriverdriver.driver.utils.Driver_Utils;
import com.movedriverdriver.driver.interfaces.AlertListener;
import com.movedriverdriver.util.AppController;
import com.movedriverdriver.util.SessionSave;
import com.movedriverdriver.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is used to calculate the trip fare.
 */

public class DriverFarecalcAct extends MainActivityDriver implements DriverClickInterface {
    public static AppCompatActivity mFlagger;
    public static DriverFarecalcAct activity;
    private final int REQUEST_READ_PHONE_STATE = 292;
    double os_distance, os_duration, os_fare, promo_percentage = 0.0;
    Intent details;
    RadioButton radiowalletButton;
    TextView radiocashButton, radiocardButton, radiouncardButton;
    LinearLayout cash_lay, card_lay, uncard_lay, pay_back_lay;
    ImageView cash_img, card_img, uncard_img;
    View vid_discount;
    DriverLocationDb objLocationDb;
    TextView eve_fare;
    ImageButton tipsCheck;
    boolean tipsChecked = false;
    DecimalFormat df = new DecimalFormat("####0.00");
    double tax = 0.0;
    // Class members declarations.
    private String message;
    private String f_tripid;
    private double os_plan_fare, os_plan_distance;
    private double os_plan_duration;
    private double os_additional_fare_per_distance, os_additional_fare_per_hour;
    private String f_distance;
    private String f_metric;
    private String f_totalfare;
    private String f_nightfareapplicable;
    private String f_nightfare;
    private String f_eveningfare_applicable = "0";
    private String f_eveningfare;
    private String f_pickup = "", drop_location = "";
    private String total_preference_fare = "";
    private String f_waitingtime;
    private String f_waitingcost;
    private String f_taxamount;
    private String f_tripfare;
    private String f_farediscount = "";
    private final String promotax = "";
    private final String promoamt = "";
    private String f_paymodid = "";
    private String p_dis = "";
    private String f_walletamt = "";
    private String f_payamt = "";
    private double m_distance;
    private double m_tripfare;
    private double m_totalfare;
    private double m_taxamount;
    private double m_waitingcost;
    private double m_walletamt;
    private double m_payamt;
    private double f_fare;
    private double f_tips;
    private double f_total;
    private EditText farecalTxt;
    private EditText tipsTxt;
    private TextView HeadTitle;
    private TextView tv_startTime;
    private TextView tv_dropTime;
    private TextView tv_tripFare;
    private TextView tv_total_disatnce;
    private EditText et_tripFare;
    private EditText et_total_disatnce;
    private EditText et_time_hour;
    private EditText et_time_mins;
    private LinearLayout layoutNormal;
    private LinearLayout layoutOutstation;
    private TextView totalamountTxt;
    private TextView actdistanceTxt;
    private TextView metricTxt, os_metricTxt;
    private TextView promopercentTxt;
    private TextView b_farecalCurrency;
    private TextView b_tipsCurrency;
    private TextView b_pickuplocation;
    private TextView b_droplocation;
    private TextView b_total_amt_curency;
    private TextView b_waitingcost, b_tax, b_discount, b_roundtrip, v_trip_fare;
    private TextView remarks;
    private TextView walletamountTxt;
    private TextView amountpayTxt, tipsPayTxt;
    private TextView idwaitingcost;
    private Dialog mDialog;
    private LinearLayout lay_fare;
    private LinearLayout walletlay;
    private LinearLayout paylay;
    private String cmpTax = "";
    private LinearLayout promoLayout, tax_lay;
    private TextView txtCmp;
    private ImageView slideImg;
    private String f_minutes_traveled;
    private String f_minutes_fare;
    private String Cvv;
    private String base_fare = "";
    private boolean fromStreetPickUp;
    private String fare_calculation_type = "3";
    private LinearLayout distance_lay, minutes_lay, waiting_lay;
    private TextView minutes_value;
    private TextView walletamountCurrency;
    private TextView amountpayCurrency, tipsPayCurrency;
    private LinearLayout eve_fare_lay;
    private String[] os_hr_min = new String[2];
    private double os_tax;
    private String trip_type = "1";
    private double os_minute_fare;
    private String promo_type;
    private ScrollView scrollview;
    private ViewGroup payment_layout;
    private ViewGroup rootLay;
    private boolean keyboardListenersAttached = false;
    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = null;
    private TextView night_fare, total_amt, v_preferences_fare;
    private LinearLayout night_fare_lay,prefrences_lay;
    private LinearLayout totalamountTxt_lay;
    private AppCompatButton btn_emergency;
    private Dialog dialog1;
    private String distanceFare = "";
    private ImageView fabInfo;

    private int min_distance_status;
    private String fare_per_minute, waiting_fare_minutes, trip_minutes, subtotal, new_distance_fare, new_base_fare, distance_fare_metric, amt, promocode_fare, tax_fare, nightfare, eveningfare, cancellation_fee;

    private String existing_wallet_amount = "";
    Double amount_used_from_wallet = 0.0;
    Double amount_tobe_paid = 0.0;
    private String pending_cancel_amount = "";

    private String isCorporate = "", tips = "", tips_fare = "", trip_id = "";
    private String complete_service_id = "";
    private String add_amount_to_wallet = "", product_weight = "", per_kg_price = "", delivery_fare = "0";
    private TextView tv_tips, tv_product_weight, tv_prodcut_fare, tv_delivery_fare;
    private LinearLayout product_lay, delivery_lay;

    DriverNonActivity nonactiityobj = new DriverNonActivity();


    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equalsIgnoreCase("TipsStatus")) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    if (bundle.getBoolean("isTipsGiven")) {
                        tips = bundle.getString("tips_amount", "");
                        trip_id = bundle.getString("trip_id", "");

                        if (tips.length() != 0) {
                            tips_fare = String.format(Locale.UK, "%.2f", (Double.parseDouble(tips) + m_payamt));
                        }
                        DriverSessionSave.saveSession("tips_trip_id", trip_id, context);
                        DriverSessionSave.saveSession("tips_amount", tips, context);
                        DriverSessionSave.saveSession("tips_fare", tips_fare, context);
                        amountpayTxt.setText(tips_fare);
                        tipsPayTxt.setText(tips);
                        tipsCheck.setVisibility(View.GONE);
                        tv_tips.setVisibility(View.GONE);
                        ShowToast(DriverFarecalcAct.this, bundle.getString("msg"));
//                        MoveToNextPage();
                    } else {
                        ShowToast(DriverFarecalcAct.this, bundle.getString("msg"));
//                        clearIdAndRedirect();

                    }
                }

            }

        }

    };


    // Set the layout to activity.
    @Override
    public int setLayout() {

        setLocale();
        return R.layout.driver_farecalc_lay2;
    }

    public String getStringFromTime(long mins) {
        String s = "0 " + DriverNC.getString(R.string.hrs) + " 00" + DriverNC.getString(R.string.mins) + " ";
        if (mins > 0) {
            s = mins / 60 + " " + DriverNC.getString(R.string.hrs) + " " + mins % 60 + " " + DriverNC.getString(R.string.mins) + " ";
        }
        return s;
    }

    public void calculateAndUpdateFare() {
        double discount_amount = 0.0;
        os_fare = os_plan_fare;

        if (!et_time_hour.getText().toString().isEmpty() && !et_time_mins.getText().toString().isEmpty() && !et_total_disatnce.getText().toString().isEmpty() && !p_dis.isEmpty()) {
            os_duration = Double.parseDouble(et_time_hour.getText().toString()) + (Double.parseDouble(et_time_mins.getText().toString()) / 60);
            if (os_duration > os_plan_duration) {
                DriverSystems.out.println("duration double " + os_duration + "__" + (os_plan_duration));
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
                b_discount.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + df.format(discount_amount));
            } else if (promo_type.equals("1")) {
                DriverSystems.out.println("os_fareee__" + os_fare + "___" + discount_amount);
                discount_amount = Double.parseDouble(f_farediscount);
                os_fare -= discount_amount;
                DriverSystems.out.println("os_fareee__*" + os_fare + "___" + discount_amount);
                b_discount.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + df.format(discount_amount));
            }
            if (os_tax != 0) {
                tax = os_fare * os_tax / 100;
                b_tax.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + df.format(((tax))));
            }

            totalamountTxt.setText(String.format(Locale.ENGLISH, df.format((os_fare + tax))));
            amountpayTxt.setText(String.format(Locale.ENGLISH, df.format(((os_fare + tax) - m_walletamt))));
            total_amt.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + String.format(Locale.ENGLISH, df.format(((os_fare + tax) - m_walletamt))));

            et_tripFare.setText(DriverFontHelper.convertfromArabic((df.format(Double.parseDouble(DriverFontHelper.convertfromArabic(String.valueOf(os_fare))) + discount_amount))));
        }
    }

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }

        rootLay.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenersAttached = true;
    }

    // Initialize the views on layout
    @Override
    public void Initialize() {
        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverFarecalcAct.this.findViewById(android.R.id.content)).getChildAt(0)), DriverFarecalcAct.this);
        btn_emergency = findViewById(R.id.btn_emergency);
        btn_emergency.setVisibility(View.GONE);
      /*  if (SessionSave.getSession(CommonData.SOS_ENABLED, this, false)) {
            btn_emergency.setVisibility(View.VISIBLE);
        }*/

        registerReceiver(mMessageReceiver, new IntentFilter("TipsStatus"));
        btn_emergency.setOnClickListener(view -> {
            Utility.actionSheet(DriverFarecalcAct.this, DriverNC.getResources().getString(R.string.send_emergency_alert), DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), false, new AlertListener() {
                @Override
                public void onSuccess() {
                    startSOSService();
                }

                @Override
                public void onFailure() {

                }
            });
            /*
            final View view1 = View.inflate(DriverFarecalcAct.this, R.layout.driver_emergency_alert, null);
            Dialog emergency_dialog = new Dialog(DriverFarecalcAct.this, R.style.dialogwinddow);
            emergency_dialog.setContentView(view1);
            emergency_dialog.setCancelable(true);
            emergency_dialog.show();
            final Button button_success = emergency_dialog.findViewById(R.id.button_success);
            final Button button_failure = emergency_dialog.findViewById(R.id.button_failure);
            button_success.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    emergency_dialog.dismiss();
                    startSOSService();
                }
            });
            button_failure.setOnClickListener(view2 -> emergency_dialog.dismiss());

             */
        });
        DriverCommonData.sContext = this;
        DriverCommonData.current_act = "FarecalcAct";
        DriverCommonData.mActivitylist.add(this);
        activity = this;
        mFlagger = this;


        objLocationDb = new DriverLocationDb(DriverFarecalcAct.this);
        // DriverFontHelper.applyFont(this, findViewById(R.id.id_farelay));
        fabInfo = findViewById(R.id.fabInfo);
        rootLay = findViewById(R.id.id_farelay);
        payment_layout = findViewById(R.id.payment_layout);
        // outstation initialization
        tv_startTime = findViewById(R.id.tv_startTime);
        tv_dropTime = findViewById(R.id.tv_dropTime);
        tv_tripFare = findViewById(R.id.tv_trip_fare);
        v_preferences_fare = findViewById(R.id.v_preferences_fare);
        prefrences_lay = findViewById(R.id.prefrences_lay);
        tv_total_disatnce = findViewById(R.id.tv_total_distance);
        et_time_hour = findViewById(R.id.ed_time_hour);
        et_time_mins = findViewById(R.id.ed_time_mins);
        et_tripFare = findViewById(R.id.ed_trip_fare);
        et_total_disatnce = findViewById(R.id.ed_total_distance);
        layoutNormal = findViewById(R.id.normal_fare_layout);
        layoutOutstation = findViewById(R.id.outstation_fare_layout);

        delivery_lay = findViewById(R.id.delivery_lay);
        tv_delivery_fare = findViewById(R.id.tv_delivery_fare);
        product_lay = findViewById(R.id.product_lay);
        tv_product_weight = findViewById(R.id.tv_product_weight);
        tv_prodcut_fare = findViewById(R.id.tv_prodcut_fare);

        b_pickuplocation = findViewById(R.id.pickuplocTxt);
        b_droplocation = findViewById(R.id.droplocTxt);
        b_farecalCurrency = findViewById(R.id.farecalCurrency);
        actdistanceTxt = findViewById(R.id.actdistanceTxt);
        metricTxt = findViewById(R.id.metricTxt);
        os_metricTxt = findViewById(R.id.os_metricTxt);
        b_tipsCurrency = findViewById(R.id.tipsCurrency);
        farecalTxt = findViewById(R.id.farecalTxt);
        tipsTxt = findViewById(R.id.tipsTxt);
        HeadTitle = findViewById(R.id.headerTxt);
        distance_lay = findViewById(R.id.distance_lay);
        minutes_lay = findViewById(R.id.minutes_lay);
        waiting_lay = findViewById(R.id.waiting_lay);
        minutes_value = findViewById(R.id.min_value);
        eve_fare_lay = findViewById(R.id.eve_fare_lay);
        night_fare_lay = findViewById(R.id.night_fare_lay);
        totalamountTxt_lay = findViewById(R.id.totalamountTxt_lay);
        scrollview = findViewById(R.id.scrollview);
        eve_fare = findViewById(R.id.eve_fare);
        night_fare = findViewById(R.id.night_fare);
        totalamountTxt = findViewById(R.id.totalamountTxt);
        promopercentTxt = findViewById(R.id.promopercentage);
        walletamountTxt = findViewById(R.id.walletamountTxt);
        walletamountCurrency = findViewById(R.id.walletamountCurrency);
        amountpayCurrency = findViewById(R.id.amountpayCurrency);
        tipsPayCurrency = findViewById(R.id.tipspayCurrency);
        amountpayTxt = findViewById(R.id.amountpayTxt);
        tv_tips = findViewById(R.id.tv_tips);
        tipsPayTxt = findViewById(R.id.tipspayTxt);
        tipsCheck = findViewById(R.id.tips_check);
        tax_lay = findViewById(R.id.tax_lay);
        txtCmp = findViewById(R.id.txtcmpTax);
        slideImg = findViewById(R.id.slideImg);
        walletlay = findViewById(R.id.walletlay);
        paylay = findViewById(R.id.paylay);
        slideImg.setVisibility(View.GONE);
        promoLayout = findViewById(R.id.discountlayout);
        lay_fare = findViewById(R.id.lay_fare);
        remarks = findViewById(R.id.remarks);
        b_total_amt_curency = findViewById(R.id.toatalamtCurrency);
        b_waitingcost = findViewById(R.id.waitingcost);
        idwaitingcost = findViewById(R.id.idwaitingcost);
        b_tax = findViewById(R.id.tax);
        b_discount = findViewById(R.id.discount);
        b_roundtrip = findViewById(R.id.roundtrip);
        v_trip_fare = findViewById(R.id.v_trip_fare);
        vid_discount = findViewById(R.id.vid_discount);
        radiocashButton = findViewById(R.id.rbtn_cash);
        total_amt = findViewById(R.id.total_amt);
        cash_img = findViewById(R.id.cash_img);
        card_img = findViewById(R.id.card_img);
        uncard_img = findViewById(R.id.uncard_img);
        cash_lay = findViewById(R.id.cash_lay);
        pay_back_lay = findViewById(R.id.pay_back_lay);
        card_lay = findViewById(R.id.card_lay);
        uncard_lay = findViewById(R.id.uncard_lay);
        radiowalletButton = findViewById(R.id.rbtn_wallet);
        radiocardButton = findViewById(R.id.rbtn_card);
        radiouncardButton = findViewById(R.id.rbtn_uncard);
        HeadTitle.setText("" + DriverNC.getResources().getString(R.string.fare_txt));
        details = getIntent();


        keyboardLayoutListener = () -> {
            // navigation bar height
            int navigationBarHeight = 0;
            int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
            }

            // status bar height
            int statusBarHeight = 0;
            resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }

            // display window size for the app layout
            Rect rect = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

            // screen height - (user app height + status + nav) ..... if non-zero, then there is a soft keyboard
            int keyboardHeight = rootLay.getRootView().getHeight() - (statusBarHeight + navigationBarHeight + rect.height());
            DriverSystems.out.println("scrollllview overall" + payment_layout.getHeight() + "__" + keyboardHeight + "___" + rootLay.getRootView().getHeight() + "__" + statusBarHeight + "____" + navigationBarHeight + "__" + rect.height() + "__" + scrollview.getHeight() + "__" + rootLay.getHeight());
            if (keyboardHeight <= 0) {
                // onHideKeyboard();
                DriverSystems.out.println("scrollllview hide");
                scrollview.fullScroll(View.FOCUS_DOWN);
            } else {
                DriverSystems.out.println("scrollllview show" + scrollview.getHeight());
                scrollview.smoothScrollTo(0, rootLay.getRootView().getHeight() - (payment_layout.getHeight() + keyboardHeight));
                //   onShowKeyboard(keyboardHeight);
            }
        };

        attachKeyboardListeners();

        slideImg.setOnClickListener(v -> {
            onBackPressed();
//            Intent intent = new Intent(DriverFarecalcAct.this, DriverMyStatus.class);
//            startActivity(intent);
//            finish();
        });

        if (details.getStringExtra("corporate") != null) {
            isCorporate = details.getStringExtra("corporate");
            if (isCorporate.equals("1")) {
                message = details.getStringExtra("message");
                setFareCalculatorScreen();
            } else {
                try {

//            // If Directly comes from end trip page(OngoingAct)
                    if (details.getStringExtra("from") != null && details.getStringExtra("from").equalsIgnoreCase("direct")) {
                        message = details.getStringExtra("message");

                        if (details.getBooleanExtra("from_split", false)) fromStreetPickUp = true;

                        // This for update the fare calculator page with API result.
                        setFareCalculatorScreen();

                    }
                    // If comes from Pending bookings(JobsAct).
                    else {
                        String lat = details.getStringExtra("lat");
                        String lon = details.getStringExtra("lon");
                        String distance = details.getStringExtra("distance");
                        String waitingHr = details.getStringExtra("waitingHr");
                        String drop_location = details.getStringExtra("drop_location");
                        String stopList = details.getStringExtra("stopList");
                        String url = "type=complete_trip";
                        new CompleteTrip(url, lat, lon, distance, waitingHr, drop_location, stopList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {

//            // If Directly comes from end trip page(OngoingAct)
                if (details.getStringExtra("from") != null && details.getStringExtra("from").equalsIgnoreCase("direct")) {
                    message = details.getStringExtra("message");
                    if (details.getBooleanExtra("from_split", false)) fromStreetPickUp = true;

                    // This for update the fare calculator page with API result.
                    setFareCalculatorScreen();

                }
                // If comes from Pending bookings(JobsAct).
                else {
                    String lat = details.getStringExtra("lat");
                    String lon = details.getStringExtra("lon");
                    String distance = details.getStringExtra("distance");
                    String waitingHr = details.getStringExtra("waitingHr");
                    String drop_location = details.getStringExtra("drop_location");
                    String stopList = details.getStringExtra("stopList");
                    String url = "type=complete_trip";
                    new CompleteTrip(url, lat, lon, distance, waitingHr, drop_location, stopList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        DriverNetworkStatus.isOnline(DriverFarecalcAct.this);

        et_time_hour.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
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
            }
        });

        et_time_mins.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
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
                            double faree = Double.parseDouble(DriverFontHelper.convertfromArabic(et_tripFare.getText().toString()));

                            if (promo_percentage > 0 && !promo_type.equals("1")) {
                                discount_amount = faree * promo_percentage / 100;
                                b_discount.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + df.format(discount_amount));
                            } else if (promo_type.equals("1")) {
                                discount_amount = Double.parseDouble(f_farediscount);
                            }


                            os_fare = faree - discount_amount;
                            if (os_tax != 0) {
                                tax = os_fare * os_tax / 100;
                                b_tax.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + df.format(((tax))));
                            }
                            totalamountTxt.setText("" + (os_fare + tax));
                            amountpayTxt.setText("" + (os_fare + tax - m_walletamt));
                            total_amt.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + (os_fare + tax - m_walletamt));

                            //                    calculateAndUpdateFare();}
                        } else {
                            totalamountTxt.setText("0.00");
                            amountpayTxt.setText("0.00");
                            total_amt.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + "0.00 ");
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
            Bundle mBundle = new Bundle();
            mBundle.putBoolean("isFromFareScreen", true);
            mBundle.putString("trip_id", f_tripid);
            mBundle.putString("tripDetailResponse", makeInfo());
            Intent intent = new Intent(DriverFarecalcAct.this, DriverTripHistoryAct.class);
            intent.putExtras(mBundle);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        unregisterReceiver(mMessageReceiver);
        if (dialog1 != null) Driver_Utils.closeDialog(dialog1);
        super.onDestroy();

        if (keyboardListenersAttached) {
            rootLay.getViewTreeObserver().removeOnGlobalLayoutListener(keyboardLayoutListener);
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        Driver_Utils.closeDialog(mDialog);
        super.onStop();
    }

    /**
     * This for update the fare calculator page with API result.
     */
    @SuppressLint("SdCardPath")
    private void setFareCalculatorScreen() {
        // Need to uncommand
        if (details != null) {
            try {
                JSONObject obj = new JSONObject(message);
                JSONObject json = obj.getJSONObject("detail");
                trip_type = json.getString("trip_type");
                if (json.has("promo_type")) promo_type = json.getString("promo_type");
                if (json.has("existing_wallet_amount")) {
                    existing_wallet_amount = json.getString("existing_wallet_amount");
                }
                if (json.has("distance_fare")) {
                    distanceFare = json.getString("distance_fare");
                }
                if (trip_type.equals("3")) {
                    layoutNormal.setVisibility(View.GONE);
                    layoutOutstation.setVisibility(View.VISIBLE);
                    waiting_lay.setVisibility(View.VISIBLE);
                    fabInfo.setVisibility(View.GONE);
                } else {
                    layoutNormal.setVisibility(View.VISIBLE);
                    layoutOutstation.setVisibility(View.GONE);
                    waiting_lay.setVisibility(View.VISIBLE);
                    fabInfo.setVisibility(View.GONE);
                    if (!trip_type.equals("2")) setNormalTripFareScreen();
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
                tv_total_disatnce.setText(DriverNC.getResources().getString(R.string.total_distance) + " (" + f_metric.toLowerCase() + ")");
                tv_tripFare.setText(DriverNC.getResources().getString(R.string.trip_fare) + "(" + DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + ")");

                et_tripFare.setText(DriverFontHelper.convertfromArabic(json.getString("trip_fare")));
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
                total_preference_fare = json.has("total_preference_fare") ? json.getString("total_preference_fare") : "";
                complete_service_id = json.has("service_id") ? json.getString("service_id") : "";
                v_preferences_fare.setText("" + DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + total_preference_fare);
                if(total_preference_fare.equals("")||total_preference_fare.equals("0"))
                    prefrences_lay.setVisibility(View.GONE);
                else
                    prefrences_lay.setVisibility(View.VISIBLE);
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

                if (json.has("pending_cancel_amount"))
                    cancellation_fee = json.getString("pending_cancel_amount");


                if (json.has("pending_cancel_amount"))
                    pending_cancel_amount = json.getString("pending_cancel_amount");

                if (json.has("product_weight")) {
                    product_weight = json.getString("product_weight");
                }

                if (json.has("per_kg_price")) {
                    per_kg_price = json.getString("per_kg_price");
                }

                if (json.has("delivery_fare")) {
                    delivery_fare = json.getString("delivery_fare");
                }

                if (!TextUtils.isEmpty(delivery_fare) && Double.parseDouble(delivery_fare) > 0) {
                    product_lay.setVisibility(View.VISIBLE);
                    delivery_lay.setVisibility(View.VISIBLE);
                    tv_product_weight.setText("Product Weight" + "(" + product_weight + " kg)");
                    tv_prodcut_fare.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + per_kg_price + "/kg");
                    tv_delivery_fare.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + delivery_fare);
                } else {
                    delivery_lay.setVisibility(View.GONE);
                    product_lay.setVisibility(View.GONE);
                }

                if (f_eveningfare_applicable.equalsIgnoreCase("1")/* && (trip_type.equals("2") || trip_type.equals("3"))*/) {
                    eve_fare.setText("" +DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this)+ " "+f_eveningfare);
                    eve_fare_lay.setVisibility(View.VISIBLE);
                } else eve_fare_lay.setVisibility(View.GONE);

                if (f_nightfareapplicable.equalsIgnoreCase("1") /*&& (trip_type.equals("2") || trip_type.equals("3"))*/) {
                    night_fare.setText("" +DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this)+ " "+ f_nightfare);
                    night_fare_lay.setVisibility(View.VISIBLE);
                } else night_fare_lay.setVisibility(View.GONE);

                try {

                    fare_calculation_type = json.getString("fare_calculation_type");
                    if (fare_calculation_type.trim().equals("1"))
                        minutes_lay.setVisibility(View.GONE);
                    else if (fare_calculation_type.trim().equals("2"))
                        distance_lay.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (f_walletamt.length() != 0) m_walletamt = Double.parseDouble(f_walletamt);
                f_walletamt = String.format(Locale.UK, "%.2f", m_walletamt);
                if (f_payamt.length() != 0) m_payamt = Double.parseDouble(f_payamt);
                f_payamt = String.format(Locale.UK, "%.2f", m_payamt);
                if (f_waitingcost.length() != 0) m_waitingcost = Double.parseDouble(f_waitingcost);
                f_waitingcost = String.format(Locale.UK, "%.2f", m_waitingcost);
                if (f_totalfare.length() != 0) m_totalfare = Double.parseDouble(f_totalfare);
                f_totalfare = String.format(Locale.UK, "%.2f", m_totalfare);
                if (f_distance.length() != 0) m_distance = Double.parseDouble(f_distance);
                f_distance = String.format(Locale.UK, "%.2f", m_distance);
                if (f_tripfare.length() != 0) m_tripfare = Double.parseDouble(f_tripfare);
                f_tripfare = String.format(Locale.UK, "%.2f", m_tripfare);
                if (f_taxamount.length() != 0) m_taxamount = Double.parseDouble(f_taxamount);
                f_taxamount = String.format(Locale.UK, "%.2f", m_taxamount);
                if (f_waitingtime.equals("0")) {
                    idwaitingcost.setText("" + DriverNC.getResources().getString(R.string.waiting_cost) + "(" + "00:00" + ")");
                } else {
                    idwaitingcost.setText("" + DriverNC.getResources().getString(R.string.waiting_cost) + "(" + f_waitingtime + ")");
                }
                if (!cmpTax.trim().equals("0"))
                    txtCmp.setText("" + DriverNC.getResources().getString(R.string.tax) + cmpTax + "" + DriverNC.getResources().getString(R.string.tax_percent));
                else tax_lay.setVisibility(View.VISIBLE);
                farecalTxt.setText(f_totalfare);
                v_trip_fare.setText("" + DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + f_tripfare);
                p_dis = String.valueOf(promo_percentage);

                DriverSystems.out.println("promo_dis" + p_dis);
                if (!p_dis.trim().equals("")) {
                    if (!promo_type.equals("1") && !p_dis.equals("0")) {
                        if (promo_type.equals("2"))
                            promopercentTxt.setText("" + DriverNC.getResources().getString(R.string.discount) + "(" + p_dis + "" + DriverNC.getResources().getString(R.string.tax_percent));
                        else
                            promopercentTxt.setText("" + DriverNC.getResources().getString(R.string.discount));
                        if (Double.parseDouble(f_farediscount) > 0.0) {
                            DriverSystems.out.println("aaaaaaaaaaa1" + f_farediscount + "_____________" + Double.parseDouble(f_farediscount));
                            b_discount.setText("" + DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + f_farediscount);
                        } else {
                            DriverSystems.out.println("aaaaaaaaaaa2");
//                            promoLayout.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (Double.parseDouble(json.getString("promodiscount_amount")) >= 0.0) {
                            b_discount.setText("" + DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + json.getString("promodiscount_amount"));
                        } else {
//                            promoLayout.setVisibility(View.VISIBLE);
                        }
                    }

                    vid_discount.setVisibility(View.GONE);

                } else {
//                    promoLayout.setVisibility(View.VISIBLE);
                    vid_discount.setVisibility(View.GONE);
                }
                if (promoamt.equals("0")) {
//                    promoLayout.setVisibility(View.VISIBLE);
                }
                metricTxt.setText(f_metric.toLowerCase());
                actdistanceTxt.setText("" + f_distance);
                minutes_value.setText(getStringFromTime(Long.parseLong(f_minutes_traveled)));
                b_pickuplocation.setText(f_pickup);
                b_droplocation.setText("" + drop_location);
                b_total_amt_curency.setText("" + DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " ");
                if (!f_waitingcost.equals("0")) {
                    b_waitingcost.setText("" + DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + f_waitingcost);
                } else {
                    waiting_lay.setVisibility(View.VISIBLE);
                }
                b_tax.setText("" + DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + f_taxamount);
                b_roundtrip.setText("" + json.getString("roundtrip"));
                tipsTxt.setHint("0");
                remarks.setText("" + objLocationDb.getdistance(f_tripid));
                if (DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) != null) {
                    b_farecalCurrency.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " ");
                    b_tipsCurrency.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " ");
                }
                f_fare = m_totalfare;
                if (tipsTxt.length() != 0) {
                    f_tips = Double.parseDouble(Uri.decode(tipsTxt.getText().toString()));
                }
                f_total = f_fare + f_tips;
                if (f_total != 0.0) {
                    totalamountTxt.setText("" + String.format(Locale.UK, "%.2f", f_total));
                } else {
                    totalamountTxt_lay.setVisibility(View.VISIBLE);
                }
                DriverSystems.out.println("gateway_details" + json.getString("gateway_details"));
                JSONArray ary = new JSONArray(json.getString("gateway_details"));
                // the following code for handle the payment mode dynamically.
                int length = ary.length();
                DriverSystems.out.println("ary lenght" + length);
                walletamountCurrency.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " ");
                amountpayCurrency.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " ");
                tipsPayCurrency.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " ");
                if (m_walletamt > 0) {
                    walletlay.setVisibility(View.VISIBLE);
                    walletamountTxt.setText(f_walletamt);
//                    paylay.setVisibility(View.VISIBLE);
                    amountpayTxt.setText(f_payamt);
                }

                if (DriverSessionSave.getSession(DriverCommonData.BALANCE_CREDIT_OPTION, DriverFarecalcAct.this).equals("1") && !fromStreetPickUp) {
                    if (SessionSave.getSession("customer_wallet_transaction", DriverFarecalcAct.this).equals("1"))
                        pay_back_lay.setVisibility(View.VISIBLE);
                    else pay_back_lay.setVisibility(View.GONE);
                } else {
                    pay_back_lay.setVisibility(View.GONE);
                }
                if (DriverSessionSave.getSession(DriverCommonData.PASSENGER_TIPS_ENABLE, DriverFarecalcAct.this).equals("1") && !fromStreetPickUp) {
                    tipsCheck.setVisibility(View.GONE);
                    tv_tips.setVisibility(View.GONE);
                } else {
                    tipsCheck.setVisibility(View.GONE);
                    tv_tips.setVisibility(View.GONE);
                }

                if (f_tripid.equals(DriverSessionSave.getSession("tips_trip_id", DriverFarecalcAct.this))) {
                    tips_fare = DriverSessionSave.getSession("tips_fare", DriverFarecalcAct.this);
                    tips = DriverSessionSave.getSession("tips_amount", DriverFarecalcAct.this);
                    if (tips.length() != 0) {
                        tips_fare = String.format(Locale.UK, "%.2f", (Double.parseDouble(tips) + m_payamt));
                        DriverSessionSave.saveSession("tips_fare", tips_fare, context);
                    }
                    amountpayTxt.setText(tips);
                    tipsPayTxt.setText(tips_fare);
                    tipsCheck.setVisibility(View.GONE);
                    tv_tips.setVisibility(View.GONE);
                } else {
                    amountpayTxt.setText(f_payamt);
                    tipsPayTxt.setText("0");
                }
                total_amt.setText(DriverSessionSave.getSession("site_currency", DriverFarecalcAct.this) + " " + f_payamt);
                for (int i = 0; i < length; i++) {
                    String paymentModeDefault = ary.getJSONObject(i).getString("pay_mod_default");
                    String paymentMode_Id = ary.getJSONObject(i).getString("pay_mod_id");
                    if (paymentMode_Id.equalsIgnoreCase("5")) {
                        if (SessionSave.getSession("customer_wallet_transaction", DriverFarecalcAct.this).equals("1"))
                            radiowalletButton.setVisibility(View.VISIBLE);
                        else radiowalletButton.setVisibility(View.GONE);
                        if (paymentModeDefault.equals("1")) {
                            radiowalletButton.setTextColor(Color.DKGRAY);
                        }
                    } else if (paymentMode_Id.equalsIgnoreCase("1")) {
                        cash_lay.setVisibility(View.VISIBLE);

                        if (paymentModeDefault.equals("1")) {
                            //  radiocashButton.setTextColor(Color.DKGRAY);
                        }
                    } else if (paymentMode_Id.equalsIgnoreCase("2")) {
                        if (SessionSave.getSession("customer_wallet_transaction", DriverFarecalcAct.this).equals("1"))
                            card_lay.setVisibility(View.VISIBLE);
                        else card_lay.setVisibility(View.GONE);
                        if (paymentModeDefault.equals("1")) {
                            //  radiocardButton.setTextColor(Color.DKGRAY);
                        }
                    } else if (paymentMode_Id.equalsIgnoreCase("3")) {
                        if (SessionSave.getSession("customer_wallet_transaction", DriverFarecalcAct.this).equals("1"))
                            uncard_lay.setVisibility(View.VISIBLE);
                        else uncard_lay.setVisibility(View.GONE);
                        if (paymentModeDefault.equals("1")) {
                            //  radiouncardButton.setTextColor(Color.DKGRAY);
                        }
                    } else if (paymentMode_Id.equalsIgnoreCase("4")) {

                    }
                }

                if (trip_type.equals("3")) {
                    calculateAndUpdateFare();
                }
            } catch (JSONException e) {
                DriverSystems.out.println("errorToCovert " + e.toString());
                e.printStackTrace();
            }
        }


        tipsCheck.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!tipsChecked) {
                    tipsCheck.setImageResource(R.drawable.on_btn);
                    tipsChecked = true;
                } else {
                    tipsCheck.setImageResource(R.drawable.off_btn);
                    tipsChecked = false;
                }

            }
        });

        // The following process will done while select the payment mode as cash.
        cash_lay.setOnClickListener(v -> {
            if (fromStreetPickUp) {
                /*
                dialog1 = Driver_Utils.alert_view_dialog(DriverFarecalcAct.this, DriverNC.getResources().getString(R.string.message), DriverNC.getResources().getString(R.string.confir_complete_payment), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancell), false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        completeStreetTrip();
                    }
                }, (dialog, which) -> dialog.dismiss(), "");

                 */
                completeStreetTrip();
            } else {
//                radiocardButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_card, 0, 0);
//                radiocashButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cash_new, 0, 0);
//                radiouncardButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_add_card, 0, 0);

//                radiocashButton.setTextColor(Color.DKGRAY);
//                radiocardButton.setTextColor(Color.LTGRAY);
//                radiouncardButton.setTextColor(Color.LTGRAY);
                if (farecalTxt.length() != 0) {
                    f_fare = Double.parseDouble(((DriverFontHelper.convertfromArabic(f_totalfare)).replace(",", ".")));
                }
                if (tipsTxt.length() != 0) {
                    f_tips = Double.parseDouble((DriverFontHelper.convertfromArabic((tipsTxt.getText().toString())).replace(",", ".")));
                }
                f_total = f_fare + f_tips;
                f_paymodid = "1";

                if (tipsCheck.getVisibility() == View.VISIBLE && checkTips()) {
                    if (tips == null || TextUtils.isEmpty(tips)) {
                        DriverCToast.ShowToast(DriverFarecalcAct.this, DriverNC.getString(R.string.wait_tips));
                        return;
                    }

                }
                if (checkTips()) {
                    confirmCompleteTrip(DriverFarecalcAct.this, 1);
                } else {
                    confirmCompleteTrip(DriverFarecalcAct.this, 3);
                }
            }
        });
        if (fromStreetPickUp) {
            pay_back_lay.setVisibility(View.GONE);
        }
        pay_back_lay.setOnClickListener(v -> {

//                radiocardButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_card, 0, 0);
//                radiocashButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cash_new, 0, 0);
//                radiouncardButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_add_card, 0, 0);

//                radiocashButton.setTextColor(Color.DKGRAY);
//                radiocardButton.setTextColor(Color.LTGRAY);
//                radiouncardButton.setTextColor(Color.LTGRAY);
            if (farecalTxt.length() != 0) {
                f_fare = Double.parseDouble(((DriverFontHelper.convertfromArabic(f_totalfare)).replace(",", ".")));
            }
            if (tipsTxt.length() != 0) {
                f_tips = Double.parseDouble((DriverFontHelper.convertfromArabic((tipsTxt.getText().toString())).replace(",", ".")));
            }
            f_total = f_fare + f_tips;
            f_paymodid = "1";
            if (tipsCheck.getVisibility() == View.VISIBLE && checkTips()) {
                if (tips == null || TextUtils.isEmpty(tips)) {
                    DriverCToast.ShowToast(DriverFarecalcAct.this, DriverNC.getString(R.string.wait_tips));
                    return;
                }

            }
            balanceAmountSheet();

        });
        // The following process will done while select the payment mode as wallet.
        radiowalletButton.setOnClickListener(v -> {

//            radiowalletButton.setTextColor(Color.DKGRAY);
//            radiocashButton.setTextColor(Color.LTGRAY);
//            radiocardButton.setTextColor(Color.LTGRAY);
//            radiouncardButton.setTextColor(Color.LTGRAY);
            if (farecalTxt.length() != 0) {
                f_fare = Double.parseDouble(DriverFontHelper.convertfromArabic(farecalTxt.getText().toString()));
            }
            if (tipsTxt.length() != 0) {
                f_tips = Double.parseDouble(DriverFontHelper.convertfromArabic(tipsTxt.getText().toString()));
            }
            f_total = f_fare + f_tips;
            totalamountTxt.setText("" + String.format(Locale.UK, "%.2f", f_total));
            f_paymodid = "5";
            if (tipsCheck.getVisibility() == View.VISIBLE && checkTips()) {
                if (tips == null || TextUtils.isEmpty(tips)) {
                    DriverCToast.ShowToast(DriverFarecalcAct.this, DriverNC.getString(R.string.wait_tips));
                    return;
                }

            }

            if (Double.parseDouble(f_payamt) < Double.parseDouble(existing_wallet_amount)) {
                amount_used_from_wallet = Double.parseDouble(f_payamt);
                DriverSessionSave.saveSession(DriverCommonData.AMOUNT_USED_FROM_WALLET, "" + amount_used_from_wallet, DriverFarecalcAct.this);
                if (checkTips()) {
                    confirmCompleteTrip(DriverFarecalcAct.this, 1);
                } else {
                    confirmCompleteTrip(DriverFarecalcAct.this, 3);
                }
            } else {
                amount_tobe_paid = Double.parseDouble(f_payamt) - Double.parseDouble(existing_wallet_amount);
                amount_used_from_wallet = Double.parseDouble(f_payamt);
                DriverSessionSave.saveSession(DriverCommonData.AMOUNT_USED_FROM_WALLET, "" + amount_used_from_wallet, DriverFarecalcAct.this);
                Utility.actionSheet(DriverFarecalcAct.this, "You have insufficient wallet amount", DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancell), false, new AlertListener() {
                    @Override
                    public void onSuccess() {
                        Intent in = new Intent(DriverFarecalcAct.this, DriverWebviewAct.class);
                        in.putExtra("type", "1");
                        in.putExtra(DriverCommonData.IS_FROM_EARNINGS, false);
                        startActivity(in);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                /*
                dialog1 = Driver_Utils.alert_view_dialog(DriverFarecalcAct.this, DriverNC.getResources().getString(R.string.message), "You have insufficient wallet amount", DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancell), false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent in = new Intent(DriverFarecalcAct.this, DriverWebviewAct.class);
                        in.putExtra("type", "1");
                        in.putExtra(DriverCommonData.IS_FROM_EARNINGS, false);
                        startActivity(in);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, "");

                 */
            }
        });
        // The following process will done while select the payment mode as card. And it shows the dialog to get the CVV number.
        card_lay.setOnClickListener(v -> {
//
//            radiocashButton.setTextColor(Color.LTGRAY);
//            radiocardButton.setTextColor(Color.DKGRAY);
//            radiouncardButton.setTextColor(Color.LTGRAY);

//            radiocardButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_card, 0, 0);
//            radiocashButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cash_new, 0, 0);
//            radiouncardButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_add_card, 0, 0);
            if (farecalTxt.length() != 0) {
                f_fare = Double.parseDouble(DriverFontHelper.convertfromArabic(farecalTxt.getText().toString()));
            }
            if (tipsTxt.length() != 0) {
                f_tips = Double.parseDouble(DriverFontHelper.convertfromArabic(tipsTxt.getText().toString()));
            }
            f_total = f_fare + f_tips;
            f_paymodid = "2";
            if (tipsCheck.getVisibility() == View.VISIBLE && checkTips()) {
                if (tips == null || TextUtils.isEmpty(tips)) {
                    DriverCToast.ShowToast(DriverFarecalcAct.this, DriverNC.getString(R.string.wait_tips));
                    return;
                }

            }
            if (checkTips()) {
                confirmCompleteTrip(DriverFarecalcAct.this, 1);
            } else {
                confirmCompleteTrip(DriverFarecalcAct.this, 3);
            }
        });
        // The following process will done while select the payment mode as uncard.
        uncard_lay.setOnClickListener(v -> {

//            radiocashButton.setTextColor(Color.LTGRAY);
//            radiocardButton.setTextColor(Color.LTGRAY);
//            radiouncardButton.setTextColor(Color.DKGRAY);
//            radiocardButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_card, 0, 0);
//            radiocashButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cash_new, 0, 0);
//            radiouncardButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_add_card, 0, 0);

            Double fare = Double.parseDouble(et_tripFare.getText().toString());

            if (trip_type.equals("3") && fare <= 0) {
                DriverCToast.ShowToast(DriverFarecalcAct.this, DriverNC.getString(R.string.endter_valid_fare));
            } else {
                if (f_total > 0) {
                    if (tipsCheck.getVisibility() == View.VISIBLE && checkTips()) {
                        if (tips == null || TextUtils.isEmpty(tips)) {
                            DriverCToast.ShowToast(DriverFarecalcAct.this, DriverNC.getString(R.string.wait_tips));
                            return;
                        }

                    }
//                    dialog1 = Driver_Utils.alert_view_dialog(DriverFarecalcAct.this, DriverNC.getResources().getString(R.string.message), DriverNC.getResources().getString(R.string.confir_complete_payment), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancell), false, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
                    Intent payintent = new Intent(DriverFarecalcAct.this, DriverPayuncardAct.class);
                    Bundle bun = new Bundle();
                    bun.putString("info", "Uncard");
                    bun.putString("message", message);
                    bun.putString("service_id", complete_service_id);
                    if (trip_type.equals("3")) {
                        if (!DriverSessionSave.getSession("Lang", DriverFarecalcAct.this).equals("en")) {
                            bun.putString("f_fare", amountpayTxt.getText().toString());
                            bun.putString("f_tips", Double.toString(f_tips));
                            bun.putString("f_total", amountpayTxt.getText().toString());
                        } else {
                            bun.putString("f_fare", DriverFontHelper.convertfromArabic(amountpayTxt.getText().toString()));
                            bun.putString("f_tips", DriverFontHelper.convertfromArabic(Double.toString(f_tips)));
                            bun.putString("f_total", DriverFontHelper.convertfromArabic(amountpayTxt.getText().toString()));
                        }
                    } else {
                        if (!DriverSessionSave.getSession("Lang", DriverFarecalcAct.this).equals("en")) {
                            bun.putString("f_fare", DriverFontHelper.convertfromArabic(f_payamt));
                            bun.putString("f_tips", DriverFontHelper.convertfromArabic(Double.toString(f_tips)));
                            bun.putString("f_total", DriverFontHelper.convertfromArabic(Double.toString(f_total)));
                        } else {
                            bun.putString("f_fare", f_payamt);
                            bun.putString("f_tips", Double.toString(f_tips));
                            bun.putString("f_total", Double.toString(f_total));
                        }
                    }
                    payintent.putExtras(bun);
                    startActivity(payintent);


//                        }
//                    }, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }, "");

                }
            }
        });

    }

    private void setNormalTripFareScreen() {
        layoutNormal.setVisibility(View.GONE);
//        paylay.setVisibility(View.VISIBLE);
//        promoLayout.setVisibility(View.VISIBLE);
        tax_lay.setVisibility(View.VISIBLE);
        totalamountTxt_lay.setVisibility(View.VISIBLE);
        fabInfo.setVisibility(View.GONE);
    }

    private void confirmCompleteTrip(final AppCompatActivity mContext, int type) {
        /** type 1 normal complete trip procedure
         2 payback to passenger wallet
         3 tips alert*/

        double fare = 0;
        String alert_msg = "";
        try {
            if (!et_tripFare.getText().toString().trim().equals(""))
                fare = Double.parseDouble(et_tripFare.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (trip_type.equals("3") && fare <= 0) {
            DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.endter_valid_fare));
        } else {
            /*
            alert_msg = DriverNC.getResources().getString(R.string.confir_complete_payment);
            if (type == 3) {
                alert_msg = DriverNC.getResources().getString(R.string.confir_tips_payment);
            }
            dialog1 = Driver_Utils.alert_view_dialog(mContext, DriverNC.getResources().getString(R.string.message), alert_msg, DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancell), false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (DriverNetworkStatus.isOnline(mContext)) {
                        dialog.dismiss();
                        if (type == 2) {
                            callAddToPassWallet();
                        } else {
                            callurl();
                        }
                    } else {
                        DriverCToast.ShowToast(mContext, DriverNC.getResources().getString(R.string.check_net_connection));
                    }
                }
            }, (dialog, which) -> dialog.dismiss(), "");

             */

            if (DriverNetworkStatus.isOnline(mContext)) {
                if (type == 2) {
                    callAddToPassWallet();
                } else {
                    callurl();
                }
            } else {
                DriverCToast.ShowToast(mContext, DriverNC.getResources().getString(R.string.check_net_connection));
            }
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
            j.put("amount_tobe_paid", amount_tobe_paid);
            j.put("amount_used_from_wallet", amount_used_from_wallet);

            j.put("trip_type", trip_type);
            j.put("trip_id", f_tripid);
            j.put("total_preference_fare", total_preference_fare);
            j.put("distance_fare", distanceFare);

            j.put("actual_distance", f_distance);

            j.put("base_fare", base_fare);

            j.put("tips", "" + tipsTxt.getText().toString());
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
            j.put("model_fare_type", DriverSessionSave.getSession("model_fare_type", DriverFarecalcAct.this));
            j.put("pending_cancel_amount", pending_cancel_amount);
            j.put("tips_amt", tips);
            j.put("tips_fare", tips_fare);
            j.put("service_id", complete_service_id);
            new FareUpdate(url, j);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * completeStreetTrip API response parsing.
     */

    private void completeStreetTrip() {
//        CoreClient client = new ServiceGenerator(FarecalcAct.this).createService(CoreClient.class);
        DriverCoreClient client = AppController.getInstance().getApiManagerWithEncryptBaseUrl_driver();

        DriverApiRequestData.StreetPickComplete request = new DriverApiRequestData.StreetPickComplete();
        request.pay_mod_id = "1";
        request.trip_fare = f_tripfare;

        request.eveningfare_applicable = f_eveningfare_applicable;
        request.eveningfare = f_eveningfare;
        request.waiting_cost = f_waitingcost;
        request.fare = String.valueOf(f_fare);
        request.minutes_traveled = f_minutes_traveled;
        request.remarks = "";
        request.actual_amount = String.valueOf(f_total);
        request.trip_id = f_tripid;
        request.distance = f_distance;
        request.base_fare = base_fare;
        request.company_tax = cmpTax;
        request.actual_distance = MainActivityDriver.mMyStatus.getdistance();
        request.tax_amount = f_taxamount;
        request.minutes_fare = f_minutes_fare;
        request.nightfare = f_nightfare;
        request.tips = tipsTxt.getText().toString();
        request.nightfare_applicable = f_nightfareapplicable;
        request.waiting_time = f_waitingtime;
        request.service_id = complete_service_id;

        if (DriverNetworkStatus.isOnline(this)) {
            Call<DriverStreetCompleteResponse> response = client.completeStreetPickUpdate(DriverServiceGenerator.COMPANY_KEY, request, "en");
            showDialog();
            response.enqueue(new DriverRetrofitCallbackClass<>(DriverFarecalcAct.this, new Callback<DriverStreetCompleteResponse>() {
                @Override
                public void onResponse(Call<DriverStreetCompleteResponse> call, Response<DriverStreetCompleteResponse> response) {
                    closeDialog();
                    if (response.isSuccessful()) {
                        DriverStreetCompleteResponse data = response.body();
                        if (data != null) {
                            String msg = data.message;
                            if (data.status.trim().equals("1")) {
                                DriverSessionSave.saveSession("travel_status", "", DriverFarecalcAct.this);
                                DriverSessionSave.saveSession("trip_id", "", DriverFarecalcAct.this);
                                DriverSessionSave.saveSession("status", "F", DriverFarecalcAct.this);
                                MainActivityDriver.mMyStatus.setdistance("");
                                DriverSessionSave.saveSession("street_completed", "", DriverFarecalcAct.this);
                                MainActivityDriver.mMyStatus.setOnstatus("");
                                MainActivityDriver.mMyStatus.setStatus("F");
                                MainActivityDriver.mMyStatus.setOnPassengerImage("");
                                MainActivityDriver.mMyStatus.setOnstatus("On");
                                MainActivityDriver.mMyStatus.setOnstatus("Complete");
                                MainActivityDriver.mMyStatus.setOnpassengerName("");
                                MainActivityDriver.mMyStatus.setOndropLocation("");
                                MainActivityDriver.mMyStatus.setOndropLocation("");
                                MainActivityDriver.mMyStatus.setOnpickupLatitude("");
                                MainActivityDriver.mMyStatus.setOnpickupLongitude("");
                                MainActivityDriver.mMyStatus.setOndropLatitude("");
                                MainActivityDriver.mMyStatus.setOndropLongitude("");
                                LocationUpdate.sTimer = "00:00:00";
                                LocationUpdate.finalTime = 0L;
                                LocationUpdate.timeInMillies = 0L;
                                DriverSessionSave.saveSession("waitingHr", "", DriverFarecalcAct.this);
                                DriverCommonData.travel_km = 0;
                                DriverSessionSave.setGoogleDistance(0f, DriverFarecalcAct.this);
                                DriverSessionSave.setDistance(0f, DriverFarecalcAct.this);
                                DriverSessionSave.saveGoogleWaypoints(null, null, "", 0.0, "", DriverFarecalcAct.this);
                                DriverSessionSave.saveWaypoints(null, null, "", 0.0, "", DriverFarecalcAct.this);
                                Intent jobintent = new Intent(DriverFarecalcAct.this, DriverJobdoneAct.class);
                                Bundle bun = new Bundle();
                                Gson gson = new GsonBuilder().create();
                                String result = gson.toJson(data);
                                bun.putString("message", result);
                                jobintent.putExtras(bun);
                                startActivity(jobintent);
                                finish();


                            } else {
                                DriverCToast.ShowToast(DriverFarecalcAct.this, msg);
                            }
                        } else {
                            DriverCToast.ShowToast(DriverFarecalcAct.this, DriverNC.getString(R.string.server_error));
                        }

                    } else {
                        DriverCToast.ShowToast(DriverFarecalcAct.this, DriverNC.getString(R.string.server_error));
                    }
                }

                @Override
                public void onFailure(Call<DriverStreetCompleteResponse> call, Throwable t) {
                    closeDialog();
                }
            }));
        } else
            DriverCToast.ShowToast(DriverFarecalcAct.this, DriverNC.getString(R.string.check_net_connection));
    }

    /**
     * Closing the alert dialog.
     */
    public void closeDialog() {
        try {
            if (mDialog != null) if (mDialog.isShowing()) mDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Showing the alert dialog
     */
    public void showDialog() {
        try {
            if (DriverNetworkStatus.isOnline(DriverFarecalcAct.this)) {
                View view = View.inflate(DriverFarecalcAct.this, R.layout.driver_progress_bar, null);
                mDialog = new Dialog(DriverFarecalcAct.this, R.style.dialogwinddow);
                mDialog.setContentView(view);
                mDialog.setCancelable(false);
                mDialog.show();

                ImageView iv = mDialog.findViewById(R.id.giff);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                Glide.with(DriverFarecalcAct.this).load(R.raw.driver_loading_anim).into(imageViewTarget);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void positiveButtonClick(DialogInterface dialog, int id, String s) {
        dialog.dismiss();
    }

    @Override
    public void negativeButtonClick(DialogInterface dialog, int id, String s) {
        dialog.dismiss();
    }

    private void startSOSService() {
        DriverSessionSave.saveSession("sos_id", DriverSessionSave.getSession("Id", DriverFarecalcAct.this), DriverFarecalcAct.this);
        DriverSessionSave.saveSession("user_type", "d", DriverFarecalcAct.this);


        //  startService(new Intent(FarecalcAct.this, SOSService.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_READ_PHONE_STATE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //      startSOSService();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DriverCToast.ShowToast(context, DriverNC.getString(R.string.pls_complete_the_payment));
//        Intent intent = new Intent(DriverFarecalcAct.this, DriverMyStatus.class);
//        startActivity(intent);
//        finish();
    }

    private String makeInfo() {
        DriverTripDetailResponse tripDetailResponse = new DriverTripDetailResponse();
        tripDetailResponse.status = 1;
        tripDetailResponse.message = "Success";
        DriverTripDetailResponse.Detail detail = tripDetailResponse.new Detail();
        detail.amt = amt;
        detail.passenger_name = "";
        detail.passenger_image = null;
        detail.map_image = null;
        detail.new_base_fare = new_base_fare;
        detail.new_distance_fare = new_distance_fare;
        detail.fare_per_minute = fare_per_minute;
        detail.distance_fare_metric = distance_fare_metric;
        detail.waiting_fare_minutes = waiting_fare_minutes;
        detail.waiting_fare = f_waitingcost;
        detail.subtotal = subtotal;
        detail.tax_fare = tax_fare;
        detail.tax_percentage = cmpTax;
        detail.promocode_fare = f_farediscount;
        detail.used_wallet_amount = f_walletamt;
        detail.min_distance_status = min_distance_status;
        detail.payment_type_label = null;
        detail.payment_type = null;
        detail.rating = null;
        detail.stops = null;
        detail.trip_minutes = trip_minutes;
        detail.promocode_fare = promocode_fare;
        detail.actual_paid_amount = null;
        detail.eveningfare = eveningfare;
        detail.nightfare = nightfare;
        detail.delivery_fare = delivery_fare;
        detail.per_kg_price = per_kg_price;

        detail.trip_id = f_tripid;
        detail.distance = f_distance;
        detail.metric = f_metric;
        detail.distance_fare = distanceFare;
        detail.minutes_fare = f_minutes_fare;
        detail.waiting_time = f_waitingtime;
        detail.fare_calculation_type = fare_calculation_type;
        detail.pending_cancel_amount = cancellation_fee;
        tripDetailResponse.detail = detail;
        String stringData = new Gson().toJson(tripDetailResponse);
        return stringData;
    }

    /**
     * This class helps to call the Fare Update API,get the result and parse it.
     */
    private class FareUpdate implements DriverAPIResult {
        String msg = "";

        public FareUpdate(String url, JSONObject data) {
            if (isOnline()) {
                if (nonactiityobj != null) {
                    nonactiityobj.stopServicefromNonActivity(DriverFarecalcAct.this);
                }
                new DriverAPIService_Retrofit_JSON(DriverFarecalcAct.this, this, data, false).execute(url);
            } else {
                DriverCToast.ShowToast(context, "" + DriverNC.getResources().getString(R.string.check_internet));
//                dialog1 = Driver_Utils.alert_view(DriverFarecalcAct.this, "", "" + DriverNC.getResources().getString(R.string.check_internet), DriverNC.getResources().getString(R.string.ok),
//                        "", true, DriverFarecalcAct.this, "");

            }
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            if (nonactiityobj != null) {
                nonactiityobj.startServicefromNonActivity(DriverFarecalcAct.this);
            }
            DriverSessionSave.saveSession(DriverCommonData.AMOUNT_USED_FROM_WALLET, "", DriverFarecalcAct.this);
            if (isSuccess) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        DriverSessionSave.saveSession("travel_status", "", DriverFarecalcAct.this);
                        DriverSessionSave.saveSession("trip_id", "", DriverFarecalcAct.this);
                        DriverSessionSave.saveSession("status", "F", DriverFarecalcAct.this);
                        MainActivityDriver.mMyStatus.setdistance("");
                        msg = json.getString("message");
                        MainActivityDriver.mMyStatus.setOnstatus("");
                        MainActivityDriver.mMyStatus.setStatus("F");
                        MainActivityDriver.mMyStatus.setOnPassengerImage("");
                        MainActivityDriver.mMyStatus.setOnstatus("On");
                        MainActivityDriver.mMyStatus.setOnstatus("Complete");
                        MainActivityDriver.mMyStatus.setOnpassengerName("");
                        MainActivityDriver.mMyStatus.setOndropLocation("");
                        MainActivityDriver.mMyStatus.setOndropLocation("");
                        MainActivityDriver.mMyStatus.setOnpickupLatitude("");
                        MainActivityDriver.mMyStatus.setOnpickupLongitude("");
                        MainActivityDriver.mMyStatus.setOndropLatitude("");
                        MainActivityDriver.mMyStatus.setOndropLongitude("");
                        JSONObject jsonDriver = json.getJSONObject("driver_statistics");
                        DriverSessionSave.saveSession("driver_statistics", "" + jsonDriver, DriverFarecalcAct.this);
                        LocationUpdate.sTimer = "00:00:00";
                        LocationUpdate.finalTime = 0L;
                        LocationUpdate.timeInMillies = 0L;
                        DriverSessionSave.saveSession("waitingHr", "", DriverFarecalcAct.this);
                        DriverCommonData.travel_km = 0;
                        DriverSessionSave.setGoogleDistance(0f, DriverFarecalcAct.this);
                        DriverSessionSave.setDistance(0f, DriverFarecalcAct.this);
                        DriverSessionSave.saveGoogleWaypoints(null, null, "", 0.0, "", DriverFarecalcAct.this);
                        DriverSessionSave.saveWaypoints(null, null, "", 0.0, "", DriverFarecalcAct.this);
                        Intent jobintent = new Intent(DriverFarecalcAct.this, DriverJobdoneAct.class);
                        Bundle bun = new Bundle();
                        bun.putString("message", result);
                        jobintent.putExtras(bun);
                        startActivity(jobintent);
                        finish();
                    } else if (json.getInt("status") == -9) {
                        msg = json.getString("message");
                        lay_fare.setVisibility(View.VISIBLE);

                        DriverCToast.ShowToast(context, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverFarecalcAct.this, "", "" + msg, DriverNC.getResources().getString(R.string.ok),
//                                "", true, DriverFarecalcAct.this, "");


                    } else if (json.getInt("status") == 0) {
                        msg = json.getString("message");
                        lay_fare.setVisibility(View.VISIBLE);
                        DriverCToast.ShowToast(context, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverFarecalcAct.this, "", "" + msg, DriverNC.getResources().getString(R.string.ok),
//                                "", true, DriverFarecalcAct.this, "");


                    } else if (json.getInt("status") == -1) {
                        msg = json.getString("message");
                        DriverCToast.ShowToast(context, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverFarecalcAct.this, "", "" + msg, DriverNC.getResources().getString(R.string.ok),
//                                "", true, DriverFarecalcAct.this, "");

                        if (json.has("driver_statistics")) {
                            DriverSessionSave.saveSession("trip_id", "", DriverFarecalcAct.this);
                            DriverSessionSave.saveSession("status", "F", DriverFarecalcAct.this);
                            MainActivityDriver.mMyStatus.setOnstatus("");
                            MainActivityDriver.mMyStatus.setStatus("F");
                            MainActivityDriver.mMyStatus.setOnPassengerImage("");
                            MainActivityDriver.mMyStatus.setOnstatus("On");
                            MainActivityDriver.mMyStatus.setOnstatus("Complete");
                            MainActivityDriver.mMyStatus.setOnpassengerName("");
                            MainActivityDriver.mMyStatus.setOndropLocation("");
                            MainActivityDriver.mMyStatus.setOndropLocation("");
                            MainActivityDriver.mMyStatus.setOnpickupLatitude("");
                            MainActivityDriver.mMyStatus.setOnpickupLongitude("");
                            MainActivityDriver.mMyStatus.setOndropLatitude("");
                            MainActivityDriver.mMyStatus.setOndropLongitude("");
                            MainActivityDriver.mMyStatus.setOndriverLatitude("");
                            MainActivityDriver.mMyStatus.setOndriverLongitude("");

                            JSONObject jsonDriver = json.getJSONObject("driver_statistics");
                            DriverSessionSave.saveSession("driver_statistics", "" + jsonDriver, DriverFarecalcAct.this);
                        }
                        Intent intent = new Intent(DriverFarecalcAct.this, DriverMyStatus.class);
                        startActivity(intent);
                        finish();
                    } else {
                        msg = json.getString("message");
                        lay_fare.setVisibility(View.VISIBLE);

                        DriverCToast.ShowToast(context, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverFarecalcAct.this, "", "" + msg, DriverNC.getResources().getString(R.string.ok),
//                                "", true, DriverFarecalcAct.this, "");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(() -> DriverCToast.ShowToast(DriverFarecalcAct.this, DriverNC.getString(R.string.server_error)));
                lay_fare.setVisibility(View.VISIBLE);
            }
        }


    }


    /**
     * CompleteTrip API response parsing.
     */
    private class CompleteTrip implements DriverAPIResult {
        public CompleteTrip(String url, String latitude, String longitude, String distance, String waitingHr, String drop_location, String stopList) {

            try {
                JSONObject j = new JSONObject();
                j.put("trip_id", DriverSessionSave.getSession("trip_id", DriverFarecalcAct.this));
                j.put("drop_latitude", latitude);
                j.put("drop_longitude", longitude);
                j.put("drop_location", drop_location);
                j.put("distance", distance);
                j.put("actual_distance", "");
                j.put("waiting_hour", waitingHr);
                j.put("driver_app_version", BuildConfig.VERSION_NAME);
                j.put("stops", new JSONArray(stopList));
                new DriverAPIService_Retrofit_JSON(DriverFarecalcAct.this, this, j, false).execute(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            if (isSuccess) {
                try {
                    message = result;
                    setFareCalculatorScreen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void callAddToPassWallet() {

        String url = "type=addto_wallet";
        try {
            JSONObject j = new JSONObject();
            j.put("trip_id", DriverSessionSave.getSession("trip_id", DriverFarecalcAct.this));
            j.put("e_amount", Double.parseDouble(add_amount_to_wallet));
            new AddToPassWallet(url, j);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private class AddToPassWallet implements DriverAPIResult {
        public AddToPassWallet(String url, JSONObject jsonObject) {

            try {
                new DriverAPIService_Retrofit_JSON(DriverFarecalcAct.this, this, jsonObject, false).execute(url);
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
                        callurl();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void balanceAmountSheet() {
        try {

            View view = getLayoutInflater().inflate(R.layout.balance_credit_sheet, null);
            BottomSheetDialog mcancelDialog = new BottomSheetDialog(DriverFarecalcAct.this);
            mcancelDialog.setContentView(view);
            mcancelDialog.setCancelable(true);
            mcancelDialog.setCanceledOnTouchOutside(false);
            if (!mcancelDialog.isShowing()) {
                mcancelDialog.show();
            }


            DriverFontHelper.applyFont(DriverFarecalcAct.this, mcancelDialog.findViewById(R.id.alert_id));
            DirverColorchange.ChangeColor((ViewGroup) view, DriverFarecalcAct.this);
            EditText balEdt = mcancelDialog.findViewById(R.id.balEdt);
            final Button button_success = mcancelDialog.findViewById(R.id.okbtn);
            final Button button_failure = mcancelDialog.findViewById(R.id.cancelbtn);


            button_success.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    mcancelDialog.dismiss();
                    if (balEdt != null && !TextUtils.isEmpty(balEdt.getText().toString().trim()) && Double.parseDouble(balEdt.getText().toString()) > 0) {
                        add_amount_to_wallet = balEdt.getText().toString();
                        confirmCompleteTrip(DriverFarecalcAct.this, 2);
                      /*  if (checkTips()) {

                        } else {
                            confirmCompleteTrip(DriverFarecalcAct.this, 3);
                        }*/
                    } else {
                        DriverCToast.ShowToast(DriverFarecalcAct.this, DriverNC.getString(R.string.enter_amount));
                    }


                }
            });


            button_failure.setVisibility(View.VISIBLE);
            button_failure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    mcancelDialog.dismiss();
                }
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    boolean checkTips() {
        if (DriverSessionSave.getSession(DriverCommonData.PASSENGER_TIPS_ENABLE, DriverFarecalcAct.this).equals("1")) {
            if (f_tripid.equals(DriverSessionSave.getSession("tips_trip_id", DriverFarecalcAct.this))) {
                return true;
            }
            return tipsChecked;
        }
        return true;
    }


}