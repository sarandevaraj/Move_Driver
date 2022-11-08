package com.taximobility.driver;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taximobility.ProfileImageSetupClass;
import com.squareup.picasso.Picasso;
import com.taximobility.R;
import com.taximobility.driver.data.DriverCommonData;
import com.taximobility.driver.data.DriverMapWrapperLayout;
import com.taximobility.driver.errorLog.DriverApiErrorModel;
import com.taximobility.driver.errorLog.DriverErrorLogRepository;
import com.taximobility.driver.interfaces.DriverAPIResult;
import com.taximobility.driver.pdview.DriverPickupDropView;
import com.taximobility.driver.route.DriverStopData;
import com.taximobility.driver.service.DriverAPIService_Retrofit_JSON;
import com.taximobility.driver.service.DriverNonActivity;
import com.taximobility.driver.utils.DriverCToast;
import com.taximobility.driver.utils.DirverColorchange;
import com.taximobility.driver.utils.DriverUtils;
import com.taximobility.driver.utils.DriverExceptionConverter;
import com.taximobility.driver.utils.DriverFontHelper;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverNetworkStatus;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.driver.utils.DriverSystems;
import com.taximobility.util.SessionSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * This class is the base  for showing app notifications
 */
public class DriverNotificationAct extends MainActivityDriver implements OnMapReadyCallback {
    public static DriverNotificationAct notificationObject;
    public String pickup_time, profile_image;
    public String message, distance, passenger_id;
    int time_out;
    private Dialog orderDialog;
    AppCompatActivity nActivity;
    CountDownTimer countDownTimer;
    Ringtone r;
    DriverNonActivity nonactiityobj = new DriverNonActivity();
    Bundle bun;
    private LinearLayout pickuppinlay;
    private boolean ACCEPT_TRIP_IN_PROGRESS = false;
    private TextView remnTimeTxt, secTxt;
    private TextView passNameTxt, pickupLocTxt, dropLocTxt;
    private TextView minTxt, slideImg, text_notes;
    private TextView model_name_txt, tv_tripType,backupTxt, HeadTitle, txt_notes,txt_order;
    private TextView approxment_fare,km_txt;
    //layout declaration
    private LinearLayout noteslayout, droplayout, pick_lay;
    private Button accept_trip,Rightlay ;
    // Class members declarations.
    private String trip_id = "", pickup, drop, bookedby;
    private String passenger_phone, cityname, passenger_name, notes;
    private String model_name, trip_type;
    private String service_id = "";
    private String product_name = "" ;
    private String product_weight = "" ;
    private String product_size = "";
    private String delivery_person_name = "", delivery_phone_number = "", delivery_date_time = "";
    private String delivery_notes = "";
    private double pickup_lat, pickup_lng;
    //Class declartion
    private GoogleMap map;
    private DriverMapWrapperLayout mapWrapperLayout;
    private DonutProgress donutProgress;
    private AnimatorSet set;
    private DriverPickupDropView pickUpDropLayout;
    private int nowAfter = -1;
    LinearLayout pickupTime_layout;
    TextView pickup_time_txt;
    private FrameLayout pickup_pinlay;
    private CardView card_bottom_lay;

    private ImageView proimg;

    /**
     * Get the google map pixels from xml density independent pixel.
     */
    public static int getPixelsFromDp(final Context context, final float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    // Set the layout to activity.
    @Override
    public int setLayout() {

        setLocale();
        return R.layout.driver_notification_lay;
    }

    /**
     * This method is used to enable gps
     */
    private boolean GPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // Initialize the views on layout
    @Override
    public void Initialize() {
        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverNotificationAct.this
                .findViewById(android.R.id.content)).getChildAt(0)), DriverNotificationAct.this);

        try {
            getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
            bun = getIntent().getExtras();
            nActivity = this;
            DriverCommonData.current_act = "NotificationAct";
            DriverCommonData.current_trip_accept = 1;
            SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);

            if (bun != null) {
                unlockScreen();
                DriverFontHelper.applyFont(this, findViewById(R.id.noti_font));
                nonactiityobj.stopServicefromNonActivity(DriverNotificationAct.this);
                pickUpDropLayout = findViewById(R.id.pd_view);
                pickUpDropLayout.setVisibility(View.GONE);
                HeadTitle = findViewById(R.id.headerTxt);
                HeadTitle.setText(" " + DriverNC.getResources().getString(R.string.Trip_Request));
                slideImg = findViewById(R.id.slideImg);
                pickup_pinlay = findViewById(R.id.pickup_pinlay);
                slideImg.setVisibility(View.GONE);
                remnTimeTxt = findViewById(R.id.TripcancelTxt);
                secTxt = findViewById(R.id.secTxt);
                model_name_txt = findViewById(R.id.vichle_name);
                approxment_fare = findViewById(R.id.approxment_fare);
                km_txt = findViewById(R.id.km_txt);
                tv_tripType = findViewById(R.id.trip_type);
                passNameTxt = findViewById(R.id.passnameTxt);
                pickuppinlay = findViewById(R.id.pickuppinlay);
                pickuppinlay.setVisibility(View.VISIBLE);
                pickupLocTxt = findViewById(R.id.currentlocTxt);
                dropLocTxt = findViewById(R.id.droplocTxt);
                minTxt = findViewById(R.id.minTxt);
                text_notes = findViewById(R.id.notes);
                noteslayout = findViewById(R.id.noteslayout);
                droplayout = findViewById(R.id.droplayout);
                backupTxt = findViewById(R.id.backup);
                Rightlay = findViewById(R.id.reject_trip);
                pick_lay = findViewById(R.id.pic);
                pickupTime_layout = findViewById(R.id.pickupTime_layout);
                pickup_time_txt = findViewById(R.id.pickup_time_txt);
                ((TextView) findViewById(R.id.request_for)).setText(DriverNC.getString(R.string.req_model));
                Rightlay.setVisibility(View.GONE);
                backupTxt.setVisibility(View.GONE);
                donutProgress = findViewById(R.id.donut_progress);
                accept_trip = findViewById(R.id.accept_trip);
                proimg = findViewById(R.id.proimg);
                txt_notes = findViewById(R.id.txt_notes);
                txt_order = findViewById(R.id.txt_order);
                card_bottom_lay = findViewById(R.id.card_bottom_lay);
                card_bottom_lay.setBackgroundResource(R.drawable.corner_over_wallet);
                card_bottom_lay.setCardElevation(20);

                accept_trip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        donutProgress.performClick();
                    }
                });


                    txt_notes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (notes.length() != 0 && !notes.contains("null")) {
                                Toast.makeText(DriverNotificationAct.this, notes, Toast.LENGTH_LONG).show();
                            }


                        }
                    });

                txt_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orderDetailDialog();
                    }
                });






                // DriverFontHelper.applyFont(this, passNameTxt, "Roboto_Medium.ttf");

                //  DriverFontHelper.applyFont(this, minTxt, "Roboto_Medium.ttf");


                message = bun.getString("message");
                final Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                r = RingtoneManager.getRingtone(DriverNotificationAct.this, notification);
                try {
                    final JSONObject json = new JSONObject(message);
                    final JSONObject tripdetails = json.getJSONObject("trip_details");
                    time_out = tripdetails.getInt("notification_time");
                    km_txt.setText(tripdetails.getString("approx_distance"));
                    approxment_fare.setText(tripdetails.getString("approx_fare"));
                    notes = tripdetails.getString("notes");
                    trip_id = tripdetails.getString("passengers_log_id");
                    if (tripdetails.has(DriverCommonData.SHOW_CANCEL_BUTTON))
                        DriverSessionSave.saveSession(DriverCommonData.SHOW_CANCEL_BUTTON, tripdetails.getString(DriverCommonData.SHOW_CANCEL_BUTTON), this);
                    else
                        DriverSessionSave.saveSession(DriverCommonData.SHOW_CANCEL_BUTTON, "0", this);

                    final JSONObject details = tripdetails.getJSONObject("booking_details");
                    if (details.has("now_after")) {
                        nowAfter = details.getInt("now_after");
                        if (nowAfter == 1) {
                            //pickupTime_layout.setVisibility(View.VISIBLE);
                            if (details.has("pickup_time"))
                                pickup_time_txt.setText(details.getString("pickup_time"));
                        } else {
                           // pickupTime_layout.setVisibility(View.GONE);
                        }
                    }
                    model_name = details.getString("model_name");
                    SessionSave.saveSession("model_name", details.getString("model_name"), DriverNotificationAct.this);
                    model_name_txt.setText(model_name);


                    if (tripdetails.has("trip_type")) {
                        trip_type = tripdetails.getString("trip_type");

                        if (trip_type.equals("0"))
                            tv_tripType.setText(DriverNC.getString(R.string.trip_type_normal));
                        else if (trip_type.equals("2"))
                            tv_tripType.setText(DriverNC.getString(R.string.trip_type_rental));
                        else if (trip_type.equals("3"))
                            tv_tripType.setText(DriverNC.getString(R.string.trip_type_outstation));
                        else if (trip_type.equals("22"))
                            tv_tripType.setText(DriverNC.getString(R.string.corporate_trip));
                    }

                    pickup = details.getString("pickupplace");
                    pickup_lat = details.getDouble("pickup_latitude");
                    pickup_lng = details.getDouble("pickup_longitude");
                    String dropLocation = details.getString("dropplace");
                    String dropLattitude = details.getString("drop_latitude");
                    String dropLongitude = details.getString("drop_longitude");


                    if (details.has("service_id") && details.getString("service_id").equals("2")){
                        txt_order.setVisibility(View.VISIBLE);
                        txt_notes.setVisibility(View.GONE);
                    }else{
                        txt_order.setVisibility(View.GONE);
                        txt_notes.setVisibility(View.VISIBLE);
                    }

                    product_name = details.has("product_name") ? details.getString("product_name") : "";
                    product_weight = details.has("product_weight") ? details.getString("product_weight") : "";
                    product_size = details.has("product_size") ? details.getString("product_size") : "";
                    delivery_person_name = details.has("delivery_person_name") ? details.getString("delivery_person_name") : "";
                    delivery_phone_number = details.has("delivery_phone_number") ? details.getString("delivery_phone_number") : "";
                    delivery_date_time = details.has("delivery_date_time") ? details.getString("delivery_date_time") : "";
                    delivery_notes = details.has("delivery_notes") ? details.getString("delivery_notes") : "";




                    pickup = pickup.trim();
                    if (pickup.length() != 0)
                        pickup = Character.toUpperCase(pickup.charAt(0)) + pickup.substring(1);
                    drop = details.getString("drop");
                    drop = drop.trim();
                    if (drop.length() != 0 || !drop.equals(""))
                        drop = Character.toUpperCase(drop.charAt(0)) + drop.substring(1);
                    else
                        droplayout.setVisibility(View.GONE);

                    JSONArray stops = null;
                    if (details.has("stops"))
                        stops = details.getJSONArray("stops");

                    if (stops != null && stops.length() > 0) {
                        parseStop(stops.toString());
                    } else {
                        droplayout.setVisibility(View.GONE);
                        pick_lay.setVisibility(View.GONE);
                        createPickAndStopView(pickup, pickup_lat, pickup_lng, dropLocation, dropLattitude, dropLongitude);
                    }

                    pickup_time = details.getString("pickup_time");
                    passenger_phone = details.getString("passenger_phone");
                    passenger_id = details.getString("passenger_id");
                    distance = details.getString("distance_away");
                    passenger_name = details.getString("passenger_name");
                    if (details.getString("bookedby").length() != 0) {
                        bookedby = details.getString("bookedby");
                    }
                    MainActivityDriver.mMyStatus.setpassengerphone(passenger_phone);
                    passenger_name = passenger_name.trim();
                    if (passenger_name.length() != 0)
                        passenger_name = Character.toUpperCase(passenger_name.charAt(0)) + passenger_name.substring(1);
                    cityname = details.getString("cityname").trim();
                    if (cityname.length() != 0)
                        cityname = Character.toUpperCase(cityname.charAt(0)) + cityname.substring(1);


                    profile_image = details.getString("profile_image");

//                    if (!TextUtils.isEmpty(profile_image)) {
//                        Picasso.get().load(profile_image).placeholder(getResources().getDrawable(R.drawable.driver_loadingimage)).error(getResources().getDrawable(R.drawable.driver_noimage)).into(proimg);
//                    }

                    if (profile_image != null && profile_image.length() > 0) {
                        Picasso.get().load(profile_image).error(R.drawable.loadingimage).placeholder(R.drawable.loadingimage).into(proimg);
                    } else {
                        if (passenger_name != "") {
                            ProfileImageSetupClass.setupProfileImage(
                                    passenger_name, proimg
                            );
                        } else {
                            Picasso.get().load(R.drawable.loadingimage).into(proimg);
                        }
                    }


                    set = (AnimatorSet) AnimatorInflater.loadAnimator(DriverNotificationAct.this, R.animator.progress_anim);
                    set.setInterpolator(new DecelerateInterpolator());
                    set.setTarget(donutProgress);
                    set.setDuration((time_out) * 1000);
                    set.start();


                } catch (final JSONException e) {
                    e.printStackTrace();
                }
                if (notes.length() != 0 && !notes.contains("null")) {
                    text_notes.setText("" + notes);
                    noteslayout.setVisibility(View.VISIBLE);
                }
                passNameTxt.setText(passenger_name);
                pickupLocTxt.setText(pickup);
                dropLocTxt.setText(drop);
                // Timer function runs based on server response and once it finished, Onfinish() method calls the reject _trip API to update the driver timeout status to server.
                countDownTimer = new CountDownTimer((time_out) * 1000, 1000) {
                    int time = 1;

                    @Override
                    public void onTick(final long millisUntilFinished_) {
                        DriverSystems.out.println("NOTIFY onTick");
                        r.play();
                        long sec = millisUntilFinished_ / 1000;
                        long minutes = 0;
                        if (sec >= 60) {
                            minutes = sec / 60;
                            sec = sec - (minutes * 60);
                        }
                        minTxt.setText("" + String.format(Locale.UK, String.valueOf(minutes)));
                        secTxt.setText("" + String.format(Locale.UK, "%1$02d", sec));
                        if (minutes > 0)
                            remnTimeTxt.setText(String.format("%1$02d", minutes) + " " + DriverNC.getResources().getString(R.string.minutestxt).toUpperCase() + ":" + String.format("%1$02d", sec) + " " + DriverNC.getResources().getString(R.string.secondstxt).toUpperCase());
                        else
                            remnTimeTxt.setText(String.format("%1$02d", sec) + " " + DriverNC.getResources().getString(R.string.secondstxt).toUpperCase());
                        time++;
                    }

                    @Override
                    public void onFinish() {
                        DriverSystems.out.println("NOTIFY onFinish");
                        try {
                            countDownTimer.cancel();
                            r.stop();
                            JSONObject j = new JSONObject();
                            j.put("trip_id", trip_id);
                            j.put("driver_id", DriverSessionSave.getSession("Id", DriverNotificationAct.this));
                            j.put("taxi_id", DriverSessionSave.getSession("taxi_id", DriverNotificationAct.this));
                            j.put("company_id", DriverSessionSave.getSession("company_id", DriverNotificationAct.this));
                            j.put("reason", "");
                            j.put("reject_type", "0");
                            final String Url = "type=reject_trip";

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!ACCEPT_TRIP_IN_PROGRESS) {
                                        DriverSystems.out.println("NOTIFY onFinish");
                                        new TripReject(Url, j);
                                    }
                                }
                            }, 2000);

                        } catch (Exception e) {
                            countDownTimer.cancel();
                            set.cancel();
                            r.stop();
                            if (DriverNotificationAct.this != null) {
                                final Intent intent = new Intent(DriverNotificationAct.this, DriverMyStatus.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                finish();
                                DriverCToast.ShowToast(DriverNotificationAct.this, DriverNC.getString(R.string.server_error));
                            }
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            // If driver accept the trip,following actions will perform.
            donutProgress.setOnClickListener(v -> {

                try {
                    ViewEnabledWithDelay(3000, donutProgress);
                    if (DriverNetworkStatus.isOnline(DriverNotificationAct.this)) {
                        if (GPSEnabled(DriverNotificationAct.this)) {
                            countDownTimer.cancel();
                            set.cancel();
                            r.stop();
                            MainActivityDriver.mMyStatus.settripId(trip_id);
                            DriverSessionSave.saveSession("trip_id", "" + trip_id, DriverNotificationAct.this);
                            MainActivityDriver.mMyStatus.setpassengerId(trip_id);
                            JSONObject j = new JSONObject();
                            j.put("pass_logid", trip_id);
                            j.put("driver_id", DriverSessionSave.getSession("Id", DriverNotificationAct.this));
                            j.put("taxi_id", DriverSessionSave.getSession("taxi_id", DriverNotificationAct.this));
                            j.put("company_id", DriverSessionSave.getSession("company_id", DriverNotificationAct.this));
                            j.put("driver_reply", "A");
                            j.put("drop_location", drop);
                            j.put("field", "rejection");
                            j.put("flag", "0");
                            final String Url = "type=driver_reply";
                            DriverSystems.out.println("result" + "Sucess");
                            new TripAccept(Url, j);
                        } else {
                            DriverCToast.ShowToast(DriverNotificationAct.this, "GPS Connection Failed");
                            countDownTimer.cancel();
                            set.cancel();
                            r.stop();
                            nonactiityobj.startServicefromNonActivity(DriverNotificationAct.this);
                            finish();
                        }
                    } else {
                        countDownTimer.cancel();
                        set.cancel();
                        r.stop();
                        DriverCToast.ShowToast(DriverNotificationAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // If driver decline the trip,following actions will perform.
            Rightlay.setOnClickListener(v -> {

                try {

                    if (DriverNetworkStatus.isOnline(DriverNotificationAct.this)) {
                        countDownTimer.cancel();
                        set.cancel();
                        r.stop();
                        JSONObject j = new JSONObject();
                        j.put("trip_id", trip_id);
                        j.put("driver_id", DriverSessionSave.getSession("Id", DriverNotificationAct.this));
                        j.put("taxi_id", DriverSessionSave.getSession("taxi_id", DriverNotificationAct.this));
                        j.put("company_id", DriverSessionSave.getSession("company_id", DriverNotificationAct.this));
                        j.put("reason", "");
                        j.put("reject_type", "1");
                        final String Url = "type=reject_trip";
                        new TripReject(Url, j);
                    } else {
                        countDownTimer.cancel();
                        set.cancel();
                        r.stop();
                        DriverCToast.ShowToast(DriverNotificationAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void orderDetailDialog() {
        final View view = View.inflate(DriverNotificationAct.this, R.layout.order_detail_dialog, null);
        orderDialog = new Dialog(DriverNotificationAct.this, R.style.dialogAnimation);
        orderDialog.setContentView(view);
        orderDialog.setCancelable(true);
        orderDialog.show();

        final TextView v_product_name = orderDialog.findViewById(R.id.v_product_name);
        final TextView v_product_weight = orderDialog.findViewById(R.id.v_product_weight);
        final TextView v_product_size = orderDialog.findViewById(R.id.v_product_size);
        final TextView call_passenger = orderDialog.findViewById(R.id.call_passenger);
        final TextView order_id = orderDialog.findViewById(R.id.order_id);
        final TextView v_name = orderDialog.findViewById(R.id.v_name);
        final TextView v_date_time = orderDialog.findViewById(R.id.v_date_time);
        final TextView order_description_details = orderDialog.findViewById(R.id.order_description_details);
        final Button close_btn = orderDialog.findViewById(R.id.close_btn);

        v_product_name.setText(product_name);
        v_product_size.setText(product_size);
        v_product_weight.setText(product_weight);
        v_name.setText(delivery_person_name);
        v_date_time.setText(delivery_date_time);
        order_description_details.setText(delivery_notes);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderDialog.dismiss();
            }
        });


    }


    /**
     * View enabling in display with delay
     *
     * @param i
     * @param butt_onboard
     */
    public void ViewEnabledWithDelay(int i, View butt_onboard) {
        butt_onboard.setEnabled(false);
        new Handler().postDelayed(() -> {
            if (butt_onboard != null)
                butt_onboard.setEnabled(true);
        }, i);
    }


    /**
     * Parse the stops from path
     *
     * @param path
     * @return
     */
    private ArrayList parseStop(String path) {
        ArrayList tempstopLists = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<DriverStopData>>() {
        }.getType();
        ArrayList<DriverStopData> stopList = gson.fromJson(path, type);

        for (int i = 0; i < stopList.size(); i++) {
            tempstopLists.add(new DriverStopData(0, stopList.get(i).getLat(), stopList.get(i).getLng(), stopList.get(i).getPlaceName(), "", stopList.get(i).getPlaceId()));
        }
        pickUpDropLayout.setData(stopList, "NOTIFY", DriverSessionSave.getSession("Lang", DriverNotificationAct.this));
        return tempstopLists;

    }

    /**
     * Navigate the screen to home page
     *
     * @param msg
     */
    public void stopTimerAndNavigateToHome(final String msg) {
        countDownTimer.cancel();
        runOnUiThread(() -> set.cancel());
        r.stop();
        final Intent intent = new Intent(DriverNotificationAct.this, DriverMyStatus.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle extras = new Bundle();
        extras.putString("alert_message", msg);
        intent.putExtras(extras);
        startActivity(intent);
        finish();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DriverCToast.ShowToast(getBaseContext(), msg);
            }
        });

    }

    @Override
    protected void onResume() {
        notificationObject = this;
        super.onResume();
    }

    /**
     * This method is initalize map settings
     */
    @SuppressLint("MissingPermission")
    public void initalizemap() {
        try {
            final int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(DriverNotificationAct.this);
            if (resultCode == ConnectionResult.SUCCESS) {
                MapsInitializer.initialize(DriverNotificationAct.this);
                mapWrapperLayout = findViewById(R.id.map_relative_layout);
                mapWrapperLayout.init(map, getPixelsFromDp(this, 39 + 20));
                mapWrapperLayout.setVisibility(View.VISIBLE);
                map.getUiSettings().setZoomControlsEnabled(false);
                map.getUiSettings().setCompassEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                map.setMyLocationEnabled(false);
                map.setPadding(0, 0, 0, 120);
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pickup_lat - 0.001, pickup_lng + 0.0001), 17f));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        bun.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bun.clear();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        initalizemap();
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

    @Override
    public void onBackPressed() {
    }

    /**
     * Method to create views dynamically if ArrayList<StopData> value not available (ie., Normal flow)
     * <p>
     * New ArrayList of StopData values created with pickup and drop(if available) and dynamic views created based on that ArrayList
     *
     * @param pickup_location
     * @param pickup_latitude
     * @param pickup_longitude
     * @param drop_location
     * @param drop_latitude
     * @param drop_longitude
     */
    private void createPickAndStopView(String pickup_location, double pickup_latitude, double pickup_longitude, String drop_location, String drop_latitude, String drop_longitude) {
        ArrayList<DriverStopData> pickUpDropList = new ArrayList<>();
        DriverStopData pickUpData = new DriverStopData(0, 0.0, 0.0, pickup_location, "", "");
        pickUpDropList.add(pickUpData);
        if (drop_location != null && !drop_location.isEmpty()) {
            DriverStopData dropData = new DriverStopData((1 + new Random().nextInt()), 0.0, 0.0, drop_location, "", "");
            pickUpDropList.add(dropData);
        }
        pickUpDropLayout.setData(pickUpDropList, "NOTIFY", DriverSessionSave.getSession("Lang", DriverNotificationAct.this));
    }

    /**
     * Used to call the trip accept API and parse the response
     */
    private class TripAccept implements DriverAPIResult {
        String msg;
        JSONObject jsonObject;

        public TripAccept(final String url, JSONObject data) {
            jsonObject = data;
            DriverSystems.out.println("result" + url);
            ACCEPT_TRIP_IN_PROGRESS = true;

            new DriverAPIService_Retrofit_JSON(DriverNotificationAct.this, this, data, false).execute(url);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {

            ACCEPT_TRIP_IN_PROGRESS = false;
            try {
                if (isSuccess) {

                    final JSONObject json = new JSONObject(result);
                    msg = json.getString("message");
                    DriverCommonData.current_trip_accept = 1;

                    if (json.getInt("status") == 7) {
                        bookedby = "";
                        DriverSessionSave.saveSession("trip_id", "", DriverNotificationAct.this);
                        msg = json.getString("message");
                        Intent i = new Intent(getBaseContext(), DriverMyStatus.class);
                        showLoading(DriverNotificationAct.this);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        Bundle extras = new Bundle();
                        extras.putString("alert_message", msg);
                        DriverCToast.ShowToast(DriverNotificationAct.this, msg);
                        getApplication().startActivity(i);
                        nActivity.finish();
                    } else if (json.getInt("status") == 1 || bookedby.equals("2")) {
                        DriverSessionSave.saveSession("speedwaiting", "", DriverNotificationAct.this);
                        MainActivityDriver.mMyStatus.settripId(trip_id);
                        DriverSessionSave.saveSession("trip_id", "" + trip_id, DriverNotificationAct.this);
                        DriverSessionSave.saveSession("status", "B",
                                DriverNotificationAct.this);
                        DriverSessionSave.saveSession(DriverCommonData.IS_STREET_PICKUP, false, DriverNotificationAct.this);
                        DriverSessionSave.saveSession("bookedby", "" + bookedby, DriverNotificationAct.this);
                        showLoading(DriverNotificationAct.this);
                        final Intent intent = new Intent(DriverNotificationAct.this, DriverOngoingAct.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        Bundle extras = new Bundle();
                        extras.putString("alert_message", msg);
                        intent.putExtras(extras);
                        startActivity(intent);
                        finish();
                    } else if (json.getInt("status") == 5) {
                        DriverSessionSave.saveSession("trip_id", "", DriverNotificationAct.this);
                        msg = json.getString("message");
                        Intent i = new Intent(getBaseContext(), DriverMyStatus.class);
                        showLoading(DriverNotificationAct.this);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        Bundle extras = new Bundle();
                        extras.putString("alert_message", msg);
                        //i.putExtras(extras);
                        getApplication().startActivity(i);
                        DriverCToast.ShowToast(DriverNotificationAct.this, msg);
                        nActivity.finish();

                    } else if (json.getInt("status") == 25) {
                        runOnUiThread(() -> DriverCToast.ShowToast(DriverNotificationAct.this, DriverNC.getString(R.string.server_error)));
                    } else {
                        runOnUiThread(() -> DriverCToast.ShowToast(DriverNotificationAct.this, msg));
                        finish();
                    }
                } else {

                    runOnUiThread(() -> DriverCToast.ShowToast(DriverNotificationAct.this, DriverNC.getString(R.string.server_error)));
                    finish();
                }
            } catch (final JSONException e) {
                DriverErrorLogRepository.getRepository(DriverNotificationAct.this).insertAllApiErrorLogs(new DriverApiErrorModel(0, DriverCommonData.getCurrentTimeForLogger(), "type=driver_reply", DriverExceptionConverter.INSTANCE.buildStackTraceString(e.getStackTrace()), DriverUtils.INSTANCE.driverInfo(DriverNotificationAct.this), jsonObject, DriverNotificationAct.this.getClass().getSimpleName(), 0));

                ACCEPT_TRIP_IN_PROGRESS = false;
                e.printStackTrace();
            }
        }
    }

    /**
     * Used to call the trip reject API and parse the response
     */
    public class TripReject implements DriverAPIResult {
        String msg;
        JSONObject jsonObject;

        public TripReject(final String url, JSONObject data) {
            jsonObject = data;
            new DriverAPIService_Retrofit_JSON(DriverNotificationAct.this, this, data, false).execute(url);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            Log.d("result", "result" + result);
            try {
                if (isSuccess) {
                    nonactiityobj.startServicefromNonActivity(DriverNotificationAct.this);
                    DriverCommonData.current_trip_accept = 0;

                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 6) {
                        msg = json.getString("message");
                    } else if (json.getInt("status") == 7) {
                        msg = json.getString("message");
                    } else if (json.getInt("status") == 8) {
                        msg = json.getString("message");
                    } else if (json.getInt("status") != 6 || json.getInt("status") != 8 || json.getInt("status") != 3 || json.getInt("status") != 2 || json.getInt("status") != -1) {
                        msg = "Trip has been rejected";
                    } else {
                        msg = "Trip has been already cancelled";
                    }
                    DriverSessionSave.saveSession("trip_id", "", DriverNotificationAct.this);
                    showLoading(DriverNotificationAct.this);
                    final Intent intent = new Intent(DriverNotificationAct.this, DriverMyStatus.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    DriverCToast.ShowToast(DriverNotificationAct.this, msg);
                } else {
                    runOnUiThread(() -> DriverCToast.ShowToast(DriverNotificationAct.this, DriverNC.getString(R.string.server_error)));
                    finish();
                }
            } catch (final JSONException e) {
                if (DriverNotificationAct.this != null) {
                    final Intent intent = new Intent(DriverNotificationAct.this, DriverMyStatus.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    DriverCToast.ShowToast(DriverNotificationAct.this, DriverNC.getString(R.string.server_error));
                }
                DriverErrorLogRepository.getRepository(DriverNotificationAct.this).insertAllApiErrorLogs(new DriverApiErrorModel(0, DriverCommonData.getCurrentTimeForLogger(), "type=reject_trip", DriverExceptionConverter.INSTANCE.buildStackTraceString(e.getStackTrace()), DriverUtils.INSTANCE.driverInfo(DriverNotificationAct.this), jsonObject, DriverNotificationAct.this.getClass().getSimpleName(), 0));

                e.printStackTrace();
            }
        }
    }
}
