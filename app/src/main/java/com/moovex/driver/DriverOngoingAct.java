package com.moovex.driver;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.PolyUtil;
import com.mayan.sospluginmodlue.service.SOSService;
import com.moovex.ProfileImageSetupClass;
import com.squareup.picasso.Picasso;
import com.moovex.BuildConfig;
import com.moovex.R;
import com.moovex.driver.adapter.DriverStopListAdapter;
import com.moovex.driver.data.DriverCommonData;
import com.moovex.driver.data.DriverMapWrapperLayout;
import com.moovex.driver.data.apiData.AddonsData;
import com.moovex.driver.interfaces.DriverAPIResult;
import com.moovex.driver.interfaces.DriverClickInterface;
import com.moovex.driver.interfaces.DriverGetAddress;
import com.moovex.driver.interfaces.DriverLocalDistanceInterface;
import com.moovex.driver.interfaces.DriverPickupupdate;
import com.moovex.driver.pdview.DriverPickupDropView;
import com.moovex.driver.route.DriverRoute;
import com.moovex.driver.route.DriverStopData;
import com.moovex.driver.service.DriverAPIService_Retrofit_JSON;
import com.moovex.driver.service.LocationUpdate;
import com.moovex.driver.service.DriverNonActivity;
import com.moovex.driver.utils.DriverCToast;
import com.moovex.driver.utils.DirverColorchange;
import com.moovex.driver.utils.DriverFontHelper;
import com.moovex.driver.utils.DriverGetAddressFromLatLng;
import com.moovex.driver.utils.DriverGpsStatus;
import com.moovex.driver.utils.DriverLatLngInterpolator;
import com.moovex.driver.utils.DriverLocationUtils;
import com.moovex.driver.utils.DriverNC;
import com.moovex.driver.utils.DriverRoundedImageView;
import com.moovex.driver.utils.DriverSessionSave;
import com.moovex.driver.utils.DriverSystems;
import com.moovex.driver.utils.Driver_Utils;
import com.moovex.driver.interfaces.AlertListener;
import com.moovex.util.SessionSave;
import com.moovex.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.moovex.driver.DriverStreetPickUpAct.WAITING_TIME_RUN;
import static com.moovex.driver.service.LocationUpdate.currentAccuracy;
import static com.moovex.driver.service.LocationUpdate.localDistance;
import static com.moovex.driver.service.LocationUpdate.runningFor;
import static com.moovex.driver.service.LocationUpdate.slabAccuracy;

/**
 * This class will be called once the trip is accepted.Here we can start,end trip etc.
 */
@SuppressLint("DefaultLocale")
public class DriverOngoingAct extends MainActivityDriver implements DriverClickInterface, OnMapReadyCallback, DriverLocalDistanceInterface, GoogleMap.OnCameraMoveStartedListener, DriverGetAddress {
    //static member declarations
    public static final int MY_PERMISSIONS_REQUEST_CALL = 112;
    private static final int MY_PERMISSIONS_REQUEST_GPS = 113;
    private static boolean ROUTE_DRAW_ON_START, LOCATION_UPDATE_STOPPED;
    private static DriverPickupupdate sendPickupPoints;
    private final int LOCATION_REQUEST_TYPE_RETRY = 1;
    private final int LOCATION_REQUEST_TYPE_INITIAL = 2;
    private final int LOCATION_REQUEST_TYPE_COMPLETE_TRIP = 3;
    public int retryCount = 1;
    DriverLatLngInterpolator _latLngInterpolator = new DriverLatLngInterpolator.Spherical();
    ObjectAnimator animator = null;
    long timeclear = 0L;
    float zoom = 17f, bearing, bearings;
    float animteBearing;
    int layoutheight;
    //array list declarations
    ArrayList<LatLng> listPoint = new ArrayList<LatLng>();
    ArrayList<LatLng> savedpoint = new ArrayList<LatLng>();
    ArrayList<LatLng> _trips = new ArrayList<LatLng>();
    //Marker declarations
    Marker _marker;
    LocalBroadcastManager localBroadcastManager;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest mLocationRequest;
    private Dialog orderDialog;

    private GoogleMap map;
    private DriverRoute route = null;
    private final DriverNonActivity nonactiityobj = new DriverNonActivity();
    private Button butt_onboard;
    private DriverRoundedImageView proimg;
    private final String dummydata = "";
    private DriverMapWrapperLayout mapWrapperLayout;
    private Location mLastLocation;
    private Float waitingHr;
    private String p_travelstatus = "", alert_msg = "", status, Address = "";
    private String mroute, metricss = "";
    private LatLng savedLatLng = null;
    private LatLng viaLatlng;
    private LatLng pickupLatLng, dropLatLng, currentLatLng;
    private double latitude1 = 0.0;
    private double longitude1 = 0.0;
    private double speed = 0.0;
    private Double p_latitude, p_longtitude;
    private Double d_latitude, d_longtitude;
    private Double driver_latitude, driver_longtitude;
    private String waitingTime = "";
    private boolean animStarted = false;
    private boolean animLocation = false;
    private Bundle alert_bundle = new Bundle();
    //layout declarations
    private LinearLayout speed_lay, km_lay;
    private LinearLayout pickup_drop_lay, tripInfo, dropppp;
    private LinearLayout tripinprogress_lay, tripDetails_lay;
    private RelativeLayout navigator_layout, slide_lay;
    private RelativeLayout dropLay, mapsupport_lay;
    private LinearLayout infoLayout;
    private FrameLayout pickup_pinlay;
    private CardView card_bottom_lay;

    private LinearLayout contact_lay;
    private View view_line_trip, pickup_drop_Sep;
    //View Declarations
    private ImageView pickup_pin;
    private CardView drop_lay, card_view_pickup;
    private TextView order_details;
    private TextView contact_txt, backup, back_up, mapInfoTxt, chatTxt;
    private TextView CurrentlocationTxt, pickup_location_txt, txt_pickup, txt_drop;
    private TextView droplocationTxt, tv_notes, TripcancelTxt;
    private TextView nodataTxt, passengerphoneTxt, passnameTxt, speedTxt, vichle_name;
    private TextView HeadTitle, CancelTxt;
    private TextView waitingTimeTxt, total_km;
    private LinearLayout lay_call;
    private TextView preference;
    private boolean haspreference = false;
    BroadcastReceiver listener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LocationUpdate.sTimer = intent.getStringExtra(DriverCommonData.FINAL_WAITING_TIME);
            waitingTimeTxt.setText("" + DriverCommonData.getDateForWaitingTime(DriverSessionSave.getWaitingTime(DriverOngoingAct.this)));
        }
    };
    private AppCompatImageView ssWaitingTime_img;
    private AppCompatButton btn_emergency_contact;
    private FloatingActionButton mov_cur_loc;
    private RecyclerView stop_recyclerView;
    private LinearLayoutManager mLayoutManager;
    private ImageView pick_fav;
    private View trip_view;
    private ArrayList<LatLng> stopListData = new ArrayList<>();
    private ArrayList<DriverStopData> stopLists = new ArrayList<>();
    private Marker c_marker, p_marker, d_marker;
    private Marker a_marker;
    //Dialog declarations
    private Dialog mProgressdialog;


    double os_distance, os_duration, os_fare, promo_percentage = 0.0;
    private String trip_type = "1";
    private String promo_type;
    private String existing_wallet_amount = "";
    private String distanceFare = "";
    private double os_tax;
    private double os_plan_fare, os_plan_distance;
    private double os_plan_duration;
    private double os_additional_fare_per_distance, os_additional_fare_per_hour;
    private double os_minute_fare;
    private String f_farediscount = "";
    double tax = 0.0;
    private String[] os_hr_min = new String[2];
    private String f_metric;
    private String f_tripid;
    private String f_totalfare;
    private double m_totalfare;
    private String f_distance;
    private double m_distance;
    private double m_waitingcost;
    private String f_waitingcost;
    private String f_payamt = "";
    private double m_payamt;
    private String f_walletamt = "";
    private double m_walletamt;
    private String f_nightfareapplicable;
    private String f_nightfare;
    private String f_eveningfare_applicable = "0";
    private String f_eveningfare;
    private String f_pickup = "", drop_location = "";
    private String f_waitingtime;
    private String f_taxamount;
    private String f_tripfare;
    private double m_taxamount;
    private double m_tripfare;
    private String p_dis = "";
    private double f_fare;
    private double f_total;
    Double amount_tobe_paid = 0.0;
    Double amount_used_from_wallet = 0.0;
    private String base_fare = "";
    private final String promotax = "";
    private String Cvv;
    private String cmpTax = "";
    private String f_minutes_traveled;
    private String f_minutes_fare;
    private String fare_calculation_type = "3";
    private String pending_cancel_amount = "";
    private String call_masking_ph_no = "";
    private final ArrayList<AddonsData> addonsData = new ArrayList<>();
    private AddonsInfoAlert addonsInfoAlert;

    private final String service_id = "";
    private String product_name = "";
    private String product_weight = "";
    private String product_size = "";
    private String delivery_person_name = "", delivery_phone_number = "", delivery_date_time = "";
    private String delivery_notes = "";
    AppCompatImageView fav_icon_drop;
    /**
     * This handler helps to draw the route between driver place to pickup place and pickup place to drop place.
     */
    Handler mHandler = new Handler() {
        private CountDownTimer countDownTimer;

        @Override
        public void handleMessage(final android.os.Message msg) {

            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    try {
                        if (map != null && (!LOCATION_UPDATE_STOPPED || ROUTE_DRAW_ON_START)) {

                            LOCATION_UPDATE_STOPPED = true;
                            ROUTE_DRAW_ON_START = false;
                            if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("Complete") || MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("Arrivd")) {
                                if (route != null) route.removePolyLines();
                                pickUpDropMarker();
                                ArrayList<LatLng> pp = new ArrayList<>();
                                pp.add(pickupLatLng);
                                pp.add(dropLatLng);
                                if (viaLatlng != null) pp.add(viaLatlng);

                                if (pickupLatLng != null && pickupLatLng.latitude != 0.0 && pickupLatLng.longitude != 0.0) {
                                    p_marker = map.addMarker(new MarkerOptions().position(new LatLng(pickupLatLng.latitude, pickupLatLng.longitude)).title("" + DriverNC.getResources().getString(R.string.pickuploc)).icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_flag_green)).draggable(false));
                                }
                                if (dropLatLng != null && dropLatLng.latitude != 0.0 && dropLatLng.longitude != 0.0) {
                                    d_marker = map.addMarker(new MarkerOptions().position(new LatLng(dropLatLng.latitude, dropLatLng.longitude)).title("" + DriverNC.getResources().getString(R.string.droploc)).icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_flag_red)).draggable(false));
//                                    route.setUpPolyLine(map, OngoingAct.this, pp.get(0), pp.get(1));

                                    new Handler().postDelayed(() -> {
                                        if (mroute != null && !mroute.isEmpty() && !mroute.equalsIgnoreCase("0"))
                                            route.drawRouteFromPolyline(map, mroute, stopListData);
                                        else
                                            route.setUpPolyLine(map, DriverOngoingAct.this, pp.get(0), pp.get(1), stopListData);
                                    }, 500);
                                }
                            } else if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("On")) {


                                ArrayList<LatLng> pp = new ArrayList<>();
                                pp.add(currentLatLng);
                                pp.add(pickupLatLng);
//                                if (viaLatlng != null)
//                                    pp.add(viaLatlng);
                                if (pp != null) {
                                    route.setUpPolyLine(map, DriverOngoingAct.this, pp.get(0), pp.get(1), pp);
                                }
                            } else {
                                ArrayList<LatLng> pp = new ArrayList<>();
                                pp.add(pickupLatLng);
                                pp.add(dropLatLng);
                                if (viaLatlng != null) pp.add(viaLatlng);
                                try {
                                    if (pp != null && map != null) {
                                        new Handler().postDelayed(() -> {
                                            if (mroute != null && !mroute.isEmpty() && !mroute.equalsIgnoreCase("0"))
                                                route.drawRouteFromPolyline(map, mroute, stopListData);
                                            else
                                                route.setUpPolyLine(map, DriverOngoingAct.this, pp.get(0), pp.get(1), stopListData);
                                        }, 500);

//                                        route.setUpPolyLine(map, OngoingAct.this, pp.get(0), pp.get(1));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            new Handler().postDelayed(() -> LOCATION_UPDATE_STOPPED = false, 50000);
                        }

                    } catch (final Exception e) {
                        mHandler.sendEmptyMessage(5);
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    final View view = View.inflate(DriverOngoingAct.this, R.layout.driver_progress_bar, null);
                    mProgressdialog = new Dialog(DriverOngoingAct.this, R.style.NewDialog);
                    mProgressdialog.setContentView(view);
                    mProgressdialog.setCancelable(false);
                    mProgressdialog.show();

                    ImageView iv = mProgressdialog.findViewById(R.id.giff);
                    DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                    Glide.with(DriverOngoingAct.this).load(R.raw.driver_loading_anim).into(imageViewTarget);

                    mHandler.sendEmptyMessage(1);
                    break;
                case 3:
                    showLog("dismiss handler");
                    mProgressdialog.dismiss();
                    break;
                case 4:
                    countDownTimer.cancel();
                    break;
                case 5:
                    try {
                        new Handler().postDelayed(() -> {
                            if (mroute != null && !mroute.isEmpty() && !mroute.equalsIgnoreCase("0"))
                                route.drawRouteFromPolyline(map, mroute, stopListData);
                        }, 500);
//                        route.setUpPolyLine(map, OngoingAct.this, pickupLatLng, dropLatLng);
//                        route.drawRoute(map, OngoingAct.this, pickupLatLng, dropLatLng, "en", Color.parseColor("#00BFFF"));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

    };

    private JSONArray stops;
    private Dialog dialog1;
    private DriverPickupDropView pickUpDropViewDriver;

    public static void registerDistanceInterface(DriverPickupupdate distanceInterface) {
        sendPickupPoints = distanceInterface;
    }

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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return R.layout.driver_accept_lay;

    }

    @Override
    protected void onStop() {
        Driver_Utils.closeDialog(mProgressdialog);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalDriverDistanceCalculation.registerDistanceInterface(DriverOngoingAct.this);
    }

    /**
     * Handling functionality after permission granted
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    runOnUiThread(() -> ensureCall());

                }
                break;
            case MY_PERMISSIONS_REQUEST_GPS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    fusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());
                }
                break;

        }
    }

    /**
     * Call passenger
     */
    private void ensureCall() {
        Utility.actionSheet(DriverOngoingAct.this, DriverNC.getResources().getString(R.string.confirm_call), DriverNC.getResources().getString(R.string.call), DriverNC.getResources().getString(R.string.cancel), false, new AlertListener() {
            @Override
            public void onSuccess() {
                try {
                    final Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + MainActivityDriver.mMyStatus.getpassengerphone()/* call_masking_ph_no*/));
                    /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }*/
                    startActivity(callIntent);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {

            }
        });
//        dialog1 = Driver_Utils.alert_view(this, DriverNC.getResources().getString(R.string.message), DriverNC.getResources().getString(R.string.confirm_call), DriverNC.getResources().getString(R.string.call), DriverNC.getResources().getString(R.string.cancel), false, DriverOngoingAct.this, "1");
    }

    /**
     * Initialize the views on layout
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void Initialize() {
        DriverCommonData.mActivitylist.add(this);
        DriverCommonData.current_act = "OngoingAct";
        DriverCommonData.sContext = this;
        DriverCommonData.current_trip_accept = 1;
//
        //      DriverFontHelper.applyFont(this, findViewById(R.id.ongoing_lay));

        route = new DriverRoute();
        createLocationRequest();

        //Map initialization
        final SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        //local broadcast manager initialization
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        pickUpDropViewDriver = findViewById(R.id.pdview);
        dropppp = findViewById(R.id.dropppp);
        dropLay = findViewById(R.id.searchlay);
        pickup_pinlay = findViewById(R.id.pickup_pinlay);
        pick_fav = findViewById(R.id.pick_fav);
        pick_fav.setVisibility(View.GONE);
        pickup_pin = findViewById(R.id.pickup_pin);
        HeadTitle = findViewById(R.id.headerTxt);
        CancelTxt = findViewById(R.id.waittime_txt);
        TripcancelTxt = findViewById(R.id.TripcancelTxt);
        nodataTxt = findViewById(R.id.nodataTxt);
        butt_onboard = findViewById(R.id.butt_onboard);
        HeadTitle.setText(" " + DriverNC.getResources().getString(R.string.app_name));
        speed_lay = findViewById(R.id.timerlayout);
        waitingTimeTxt = findViewById(R.id.waittime_txt);
        km_lay = findViewById(R.id.km_lay);
        total_km = findViewById(R.id.total_km);
        backup = findViewById(R.id.back_txt);
        back_up = findViewById(R.id.backup);
        CurrentlocationTxt = findViewById(R.id.currentlocTxt);
        txt_pickup = findViewById(R.id.txt_pickup);
        txt_drop = findViewById(R.id.txt_drop);
        droplocationTxt = findViewById(R.id.droplocTxt);
        CurrentlocationTxt.setSelected(true);
        droplocationTxt.setSelected(true);
        passnameTxt = findViewById(R.id.passnameTxt);
        vichle_name = findViewById(R.id.vichle_name);
        preference = findViewById(R.id.preference);
        proimg = findViewById(R.id.proimg);
        lay_call = findViewById(R.id.lay_call);
        passengerphoneTxt = findViewById(R.id.phoneTxt);
        speedTxt = findViewById(R.id.speedTxt);
        tripinprogress_lay = findViewById(R.id.tripinprogress_lay);
        tripDetails_lay = findViewById(R.id.tripDetails_lay);
        contact_txt = findViewById(R.id.contact_txt);
        stop_recyclerView = findViewById(R.id.stop_listview);
        mLayoutManager = new LinearLayoutManager(DriverOngoingAct.this);
        stop_recyclerView.setLayoutManager(mLayoutManager);
        contact_lay = findViewById(R.id.contact_lay);
        pickup_drop_lay = findViewById(R.id.pickup_drop_lay);
        drop_lay = findViewById(R.id.drop_lay);
        trip_view = findViewById(R.id.trip_view);
        mov_cur_loc = findViewById(R.id.mov_cur_loc);
        card_view_pickup = findViewById(R.id.card_view_pickup);
        view_line_trip = findViewById(R.id.view_line_trip);
        pickup_location_txt = findViewById(R.id.pickup_location_txt);
        pickup_drop_Sep = findViewById(R.id.pickup_drop_Sep);
        slide_lay = findViewById(R.id.slide_lay);
        pickup_location_txt.setVisibility(View.VISIBLE);
        ssWaitingTime_img = findViewById(R.id.img_start);
        tripInfo = findViewById(R.id.tripdetail_layout);
        tv_notes = findViewById(R.id.notes);
        mapsupport_lay = findViewById(R.id.mapsupport_lay);
        navigator_layout = findViewById(R.id.botton_layout);
        infoLayout = findViewById(R.id.info_layout);
        mapInfoTxt = findViewById(R.id.mapinfo_txt);
        chatTxt = findViewById(R.id.chatTxt);
        order_details = findViewById(R.id.order_details);
        card_bottom_lay = findViewById(R.id.card_bottom_lay);
        card_bottom_lay.setBackgroundResource(R.drawable.corner_over_wallet);
        card_bottom_lay.setCardElevation(20);
        chatTxt.setVisibility(View.GONE);
        fav_icon_drop = findViewById(R.id.drop_loc_select);
        fav_icon_drop.setVisibility(View.GONE);
        if (dropppp.getVisibility() == View.GONE) {

            final float scale = this.getResources().getDisplayMetrics().density;
            int pixels = (int) (60 * scale + 0.5f);
            dropLay.getLayoutParams().height = pixels;
            dropLay.invalidate();
        }

        try {
            alert_bundle = getIntent().getExtras();
            if (alert_bundle != null) {
                alert_msg = alert_bundle.getString("alert_message");
                try {
                    status = alert_bundle.getString("status");
                    status = alert_bundle.getString("status");
                    getIntent().replaceExtras(new Bundle());
                    getIntent().setAction("");
                    getIntent().setData(null);
                    getIntent().setFlags(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (status != null) if (status.equals("11")) {
                startActivity(new Intent(DriverOngoingAct.this, DriverOngoingAct.class));
            }
            if (alert_msg != null && alert_msg.length() != 0)
                DriverCToast.ShowToast(DriverOngoingAct.this, "" + alert_msg);
//                dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + alert_msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
            if (!DriverSessionSave.getSession("trip_id", DriverOngoingAct.this).equals("")) {
                JSONObject j = new JSONObject();
                j.put("trip_id", DriverSessionSave.getSession("trip_id", DriverOngoingAct.this));
                final String Url = "type=get_trip_detail";
                new Tripdetails(Url, j);
                nonactiityobj.startServicefromNonActivity(DriverOngoingAct.this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //SOS Handling in Trip
        btn_emergency_contact = findViewById(R.id.btn_emergency_contact);
        if (SessionSave.getSession(DriverCommonData.SOS_ENABLED, this, false)) {
            btn_emergency_contact.setVisibility(View.GONE);
        }
        btn_emergency_contact.setOnClickListener(view -> {
            Utility.actionSheet(DriverOngoingAct.this, DriverNC.getResources().getString(R.string.send_emergency_alert), DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), false, new AlertListener() {
                @Override
                public void onSuccess() {
                    startSOSService();
                }

                @Override
                public void onFailure() {

                }
            });
            /*
            final View view1 = View.inflate(DriverOngoingAct.this, R.layout.driver_emergency_alert, null);
            Dialog emergency_dialog = new Dialog(DriverOngoingAct.this, R.style.dialogwinddow);
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
            button_failure.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    emergency_dialog.dismiss();
                }
            });

             */
        });


        contact_txt.setOnClickListener(v -> {
            if (contact_lay.isShown()) {
                stop_recyclerView.setVisibility(View.VISIBLE);
                pickUpDropViewDriver.setVisibility(View.GONE);
                contact_lay.setVisibility(View.GONE);
                pickup_drop_lay.setVisibility(View.GONE);
                contact_txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.driver_user_unfocus, 0);
                setCurrentLocationPosition(0, 5, 10, 150);
                if (haspreference) {
                    preference.setVisibility(View.VISIBLE);
                }


            } else {

                stop_recyclerView.setVisibility(View.GONE);
                pickUpDropViewDriver.setVisibility(View.VISIBLE);
                contact_lay.setVisibility(View.VISIBLE);
                drop_lay.setVisibility(View.GONE);
                pickup_location_txt.setVisibility(View.INVISIBLE);
                pickup_drop_lay.setVisibility(View.VISIBLE);
                contact_txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.driver_user_focus, 0);
                DriverMapWrapperLayout.setmMapIsTouched(true);
                setCurrentLocationPosition(0, 50, 10, 100);
            }
        });
        order_details.setOnClickListener(view -> orderDetailDialog());


        mov_cur_loc.setOnClickListener(v -> {
            if (map != null && mLastLocation != null) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), zoom));
                navigator_layout.setVisibility(View.VISIBLE);
                mov_cur_loc.setVisibility(View.GONE);
            }
        });

        //To update the metric in speed
        if (DriverSessionSave.getSession("Metric", DriverOngoingAct.this).equalsIgnoreCase("KM")) {
            metricss = " km/hr";
        } else {
            metricss = " miles/hr";
        }
        LocalBroadcastManager.getInstance(DriverOngoingAct.this).registerReceiver(listener, new IntentFilter(LocationUpdate.WAITING_TIME));
        // to handle the whether the waiting time is auto or manual

        //set waiting time image if waiting time is manual
        if (!DriverSessionSave.getSession(DriverCommonData.WAITING_TIME, DriverOngoingAct.this, false)) {
            ssWaitingTime_img.setImageResource(R.drawable.driver_ic_play_circle);
            if (!DriverSessionSave.getSession("trip_id", DriverOngoingAct.this).equals("")) {
                DriverCommonData.km_calc = 1;
                if (localBroadcastManager != null) {
                    Intent localIntent = new Intent(WAITING_TIME_RUN);
                    localIntent.putExtra(DriverCommonData.WAITING_TIME_START_STOP, DriverCommonData.WAITING_TIME_STOP);
                    localBroadcastManager.sendBroadcast(localIntent);
                }
            }
        } else {
            ssWaitingTime_img.setImageResource(R.drawable.driver_ic_pause_circle);
            if (!DriverSessionSave.getSession("trip_id", DriverOngoingAct.this).equals("")) {
                DriverCommonData.km_calc = 0;
                if (localBroadcastManager != null) {
                    Intent localIntent = new Intent(WAITING_TIME_RUN);
                    localIntent.putExtra(DriverCommonData.WAITING_TIME_START_STOP, DriverCommonData.WAITING_TIME_START);
                    localBroadcastManager.sendBroadcast(localIntent);
                }
            }
        }

        ssWaitingTime_img.setOnClickListener(view -> {
            if (!DriverSessionSave.getSession(DriverCommonData.WAITING_TIME, DriverOngoingAct.this, false)) {
                DriverCommonData.km_calc = 0;
                if (!DriverSessionSave.getSession("trip_id", DriverOngoingAct.this).equals("")) {
//                        WaitingTimerRun.startTimerService(OngoingAct.this);
//                        myHandler.postDelayed(r, 0);

                    if (localBroadcastManager != null) {
                        Intent localIntent = new Intent(WAITING_TIME_RUN);
                        localIntent.putExtra(DriverCommonData.WAITING_TIME_START_STOP, DriverCommonData.WAITING_TIME_START);
                        localBroadcastManager.sendBroadcast(localIntent);
                    }
                    ssWaitingTime_img.setImageResource(R.drawable.driver_ic_pause_circle);
                    DriverSessionSave.saveSession(DriverCommonData.WAITING_TIME, true, DriverOngoingAct.this);

                    waitingTimeTxt.setText(String.format(Locale.UK, DriverCommonData.getDateForWaitingTime(DriverSessionSave.getWaitingTime(DriverOngoingAct.this))));
                }
            } else {
                DriverSystems.out.println("timer started ongoing" + DriverSessionSave.getWaitingTime(DriverOngoingAct.this));

//                    stopService(new Intent(OngoingAct.this, WaitingTimerRun.class));
                DriverSessionSave.saveSession(DriverCommonData.WAITING_TIME, false, DriverOngoingAct.this);
                if (localBroadcastManager != null) {
                    Intent localIntent = new Intent(WAITING_TIME_RUN);
                    localIntent.putExtra(DriverCommonData.WAITING_TIME_START_STOP, DriverCommonData.WAITING_TIME_STOP);
                    localBroadcastManager.sendBroadcast(localIntent);
                }
                ssWaitingTime_img.setImageResource(R.drawable.driver_ic_play_circle);
                DriverCommonData.km_calc = 1;

                waitingTimeTxt.setText(String.format(Locale.UK, DriverCommonData.getDateForWaitingTime(DriverSessionSave.getWaitingTime(DriverOngoingAct.this))));
            }
        });
        ViewEnabledWithDelay(3000, butt_onboard);

      /*  Glide.with(DriverOngoingAct.this)
                .load(DriverSessionSave.getSession("image_path", DriverOngoingAct.this) + "callDriver.png")
                .apply(RequestOptions.placeholderOf(R.drawable.driver_cancel).override((int) pxtoDp(50), (int) pxtoDp(50)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        *//* Set a drawable to the left of textView *//*
                        passengerphoneTxt.setCompoundDrawablesWithIntrinsicBounds(resource, null, null, null);

                    }
                });*/

      /*  Glide.with(DriverOngoingAct.this)
                .load(DriverSessionSave.getSession("image_path", DriverOngoingAct.this) + "tripCancel.png")
                .apply(RequestOptions.placeholderOf(R.drawable.driver_cancel).override((int) pxtoDp(100), (int) pxtoDp(100)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        *//* Set a drawable to the left of textView *//*
                        TripcancelTxt.setCompoundDrawablesWithIntrinsicBounds(resource, null, null, null);

                    }
                });
*/

        Glide.with(this).load(DriverSessionSave.getSession("image_path", this) + "mapDirection.png").apply(RequestOptions.placeholderOf(R.drawable.driver_gps_navigator).error(R.drawable.driver_gps_navigator)).into((ImageView) findViewById(R.id.butt_navigator));

        butt_onboard.setVisibility(View.VISIBLE);
        // This onclick method used to hide the passenger info view.
        mapInfoTxt.setOnClickListener(v -> {

            tripInfo.setVisibility(View.VISIBLE);
            infoLayout.setVisibility(View.GONE);
        });


        chatTxt.setOnClickListener(v -> {

            Intent in = new Intent(DriverOngoingAct.this, DriverChatWebviewAct.class);
            in.putExtra("type", "2");
            in.putExtra("trip_id", DriverSessionSave.getSession("trip_id", DriverOngoingAct.this));
            startActivity(in);
        });
        // This onclick method used to show the passenger info view.
        // Following set of code to initialize and google map.

        // This onclick method used to make a call to passenger.

        addonsInfoAlert = new AddonsInfoAlert();
        preference.setOnClickListener(view -> addonsInfoAlert.AddonsInfo(DriverOngoingAct.this, addonsData));


        lay_call.setOnClickListener(v -> {
            ensureCall();

//                try {
//                    JSONObject j = new JSONObject();
//                    j.put("trip_id", DriverSessionSave.getSession("trip_id", DriverOngoingAct.this));
//                    final String Url = "type=get_twilio_number";
//                    new getMaskedPhoneNumber(Url, j);

/*
               if (MainActivityDriver.mMyStatus.getpassengerphone().length() == 0)
                   Toast.makeText(DriverOngoingAct.this,"" + DriverNC.getResources().getString(R.string.invalid_mobile_number),Toast.LENGTH_LONG).show();
//                        dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.invalid_mobile_number), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
                else {
                    final Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + MainActivityDriver.mMyStatus.getpassengerphone()));
                    if (ActivityCompat.checkSelfPermission(DriverOngoingAct.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(DriverOngoingAct.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Utility.actionSheet(DriverOngoingAct.this, NC.getResources().getString(R.string.str_phone), NC.getResources().getString(R.string.yes), NC.getResources().getString(R.string.no), false, new AlertListener() {
                            @Override
                            public void onSuccess() {
                                try {
                                    ActivityCompat.requestPermissions(DriverOngoingAct.this,
                                            new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE},
                                            MY_PERMISSIONS_REQUEST_CALL);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                        /*
                        dialog1 = Driver_Utils.alert_view_dialog(DriverOngoingAct.this, "", NC.getResources().getString(R.string.str_phone), NC.getResources().getString(R.string.yes), NC.getResources().getString(R.string.no), true, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                ActivityCompat.requestPermissions(DriverOngoingAct.this,
                                        new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE},
                                        MY_PERMISSIONS_REQUEST_CALL);
                                dialog.dismiss();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        }, "");
                        */
            /*
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ensureCall();
                           }
                        });

                    }
                }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

             */
        });
        // This onclick method used to cancel the current ongoing trip.
        TripcancelTxt.setOnClickListener(v -> {
            Utility.actionSheet(DriverOngoingAct.this, DriverNC.getResources().getString(R.string.cancel_in_going_trip), DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), false, new AlertListener() {
                @Override
                public void onSuccess() {
                    try {
                        // TODO Auto-generated method stub
                        if (DriverSessionSave.getSession("status", DriverOngoingAct.this).equalsIgnoreCase("A"))
                            DriverCToast.ShowToast(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.you_are_in_trip));
//                        dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.you_are_in_trip), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
                        else if (DriverSessionSave.getSession("trip_id", DriverOngoingAct.this).length() == 0)
                            finish();
                        else {
                            nonactiityobj.stopServicefromNonActivity(DriverOngoingAct.this);
                            JSONObject j = new JSONObject();
                            j.put("pass_logid", DriverSessionSave.getSession("trip_id", DriverOngoingAct.this));
                            j.put("driver_id", DriverSessionSave.getSession("Id", DriverOngoingAct.this));
                            j.put("taxi_id", DriverSessionSave.getSession("taxi_id", DriverOngoingAct.this));
                            j.put("company_id", DriverSessionSave.getSession("company_id", DriverOngoingAct.this));
                            j.put("driver_reply", "C");
                            j.put("field", "");
                            j.put("flag", "1");
                            if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("Arrivd"))
                                j.put("driver_arrived", 1);
                            else j.put("driver_arrived", 0);
                            final String canceltrip_url = "type=driver_reply";
                            new CancelTrip(canceltrip_url, j);
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure() {

                }
            });
//                dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, DriverNC.getResources().getString(R.string.message), DriverNC.getResources().getString(R.string.cancel_in_going_trip), DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), true, DriverOngoingAct.this, "3");
        });
        // This onclick method used to move from this activity to home activity.
        back_up.setOnClickListener(view -> backup.performClick());
        backup.setOnClickListener(v -> {

            showLoading(DriverOngoingAct.this);
            try {
                stopLocationUpdates();
                map = null;
                if (c_marker != null && a_marker != null) {
                    c_marker = null;
                    a_marker = null;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            backup.setEnabled(false);
            Intent jobintent = new Intent(DriverOngoingAct.this, DriverMyStatus.class);
            startActivity(jobintent);
            finish();
        });
        // This onclick method used to move navigator application with pickup and drop place lat/lng.
        navigator_layout.setOnClickListener(v -> {
            try {
                Log.e("URL_Test" + mMyStatus.getOnstatus(), "hai");
                if (mMyStatus.getOnstatus().equalsIgnoreCase("Complete")) {
                    if (pickupLatLng.latitude != 0.0 && pickupLatLng.longitude != 0.0) {

                        String locationurl;
                        if (stopListData != null && stopListData.size() > 0) {
                            if (stopListData.size() == 1) {
                                //http://maps.google.com/maps?saddr=
                                locationurl = "https://www.google.com/maps/dir/?api=1&origin=" + stopListData.get(0).latitude + "," + stopListData.get(0).longitude;
                            } else /*if (mLastLocation.getLatitude() != 0.0 && mLastLocation.getLongitude() != 0.0 && pickupLatLng.latitude != 0.0 && pickupLatLng.longitude != 0.0)*/ {
                                locationurl = "https://www.google.com/maps/dir/?api=1&origin=" + stopListData.get(0).latitude + "," + stopListData.get(0).longitude + "&destination=" + stopListData.get(stopListData.size() - 1).latitude + "," + stopListData.get(stopListData.size() - 1).longitude + "&travelmode=driving&waypoints=" + route.makeDirectionUrl(stopListData);
                            }
                            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationurl));
                            startActivity(intent);
                        } else {

                            if (dropLatLng != null && dropLatLng.latitude != 0.0) {
                                locationurl = "https://www.google.com/maps/dir/?api=1&origin=" + pickupLatLng.latitude + "," + pickupLatLng.longitude + "&destination=" + dropLatLng.latitude + "," + dropLatLng.longitude + "&travelmode=driving";
                                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationurl));
                                startActivity(intent);
                            } else {
                                locationurl = "https://www.google.com/maps/dir/?api=1&origin=" + pickupLatLng.latitude + "," + pickupLatLng.longitude;
                                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationurl));
                                startActivity(intent);
                            }
                        }
                    }
                } else {
                    String locationurl;
                    if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("On")) {
                        if (mLastLocation.getLatitude() != pickupLatLng.latitude) {
                            locationurl = "https://www.google.com/maps/dir/?api=1&origin=" + mLastLocation.getLatitude() + "," + mLastLocation.getLongitude() + "&destination=" + pickupLatLng.latitude + "," + pickupLatLng.longitude + "&travelmode=driving";
                            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationurl));
                            startActivity(intent);
                        } else {
                            locationurl = "https://www.google.com/maps/dir/?api=1&origin=" + pickupLatLng.latitude + "," + pickupLatLng.longitude;
                            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationurl));
                            startActivity(intent);
                        }
                    } else {
                        if (stopListData != null && stopListData.size() > 0) {
                            if (stopListData.size() == 1) {
                                //http://maps.google.com/maps?saddr=
                                locationurl = "https://www.google.com/maps/dir/?api=1&origin=" + stopListData.get(0).latitude + "," + stopListData.get(0).longitude;
                            } else /*if (mLastLocation.getLatitude() != 0.0 && mLastLocation.getLongitude() != 0.0 && pickupLatLng.latitude != 0.0 && pickupLatLng.longitude != 0.0)*/ {
                                locationurl = "https://www.google.com/maps/dir/?api=1&origin=" + stopListData.get(0).latitude + "," + stopListData.get(0).longitude + "&destination=" + stopListData.get(stopListData.size() - 1).latitude + "," + stopListData.get(stopListData.size() - 1).longitude + "&travelmode=driving&waypoints=" + route.makeDirectionUrl(stopListData);
                            }
                            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationurl));
                            startActivity(intent);
                        } else {
                            if (dropLatLng != null && dropLatLng.latitude != 0.0) {
                                locationurl = "https://www.google.com/maps/dir/?api=1&origin=" + pickupLatLng.latitude + "," + pickupLatLng.longitude + "&destination=" + dropLatLng.latitude + "," + dropLatLng.longitude + "&travelmode=driving";
                                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationurl));
                                startActivity(intent);
                            } else {
                                locationurl = "https://www.google.com/maps/dir/?api=1&origin=" + pickupLatLng.latitude + "," + pickupLatLng.longitude;
                                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationurl));
                                startActivity(intent);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        });
        // This onclick method used to handle the three state in ongoing trip page(Arrived,Start and End).In each phase will use different API.
        butt_onboard.setOnClickListener(v -> {
            try {
                // If the trip in accepted not get arrived
                if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("On")) {

                    LocationUpdate.ClearSession(DriverOngoingAct.this);
                    DriverSystems.out.println("distanceeeeee " + DriverSessionSave.getDistance(DriverOngoingAct.this) + "____" + DriverSessionSave.getGoogleDistance(DriverOngoingAct.this));
                    JSONObject jDriverArrived = new JSONObject();
                    jDriverArrived.put("trip_id", DriverSessionSave.getSession("trip_id", DriverOngoingAct.this));
                    jDriverArrived.put("driver_id", DriverSessionSave.getSession("Id", DriverOngoingAct.this));
                    jDriverArrived.put("taxi_id", DriverSessionSave.getSession("taxi_id", DriverOngoingAct.this));
                    final String arrived_url = "type=driver_arrived";
                    new DriverArrived(arrived_url, jDriverArrived);
                }
                // If trip in arrived state and going to start the trip
                else if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("Arrivd")) {


                    retryCount = 1;
                    //                        nonactiityobj.stopServicefromNonActivity(OngoingAct.this);
                    if (latitude1 != 0.0 && longitude1 != 0.0) {
                        JSONObject jstart = new JSONObject();
                        jstart.put("driver_id", DriverSessionSave.getSession("Id", DriverOngoingAct.this));
                        jstart.put("latitude", latitude1);
                        jstart.put("longitude", longitude1);
                        jstart.put("status", "A");
                        stopLists.get(0).setLat(latitude1);
                        stopLists.get(0).setLng(longitude1);
                        jstart.put("stops", new JSONArray(new Gson().toJson(stopLists)));
                        jstart.put("trip_id", DriverSessionSave.getSession("trip_id", DriverOngoingAct.this));
                        jstart.put("driver_id", DriverSessionSave.getSession("Id", DriverOngoingAct.this));
                        jstart.put("taxi_id", DriverSessionSave.getSession("taxi_id", DriverOngoingAct.this));
                        final String driver_status_update = "type=driver_status_update";
                        DriverSessionSave.saveSession("slat", "" + latitude1, DriverOngoingAct.this);
                        DriverSessionSave.saveSession("slng", "" + longitude1, DriverOngoingAct.this);
                        DriverCommonData.last_getlatitude = latitude1;
                        DriverCommonData.last_getlongitude = longitude1;
                        if (currentAccuracy <= slabAccuracy) {
                            new Onboard(driver_status_update, jstart);
                        } else {
                            showLowAccuracyAlert();
                        }
                    } else {
                        JSONObject jstart = new JSONObject();
                        jstart.put("driver_id", DriverSessionSave.getSession("Id", DriverOngoingAct.this));
                        jstart.put("latitude", "");
                        jstart.put("longitude", "");
                        jstart.put("status", "A");
                        stopLists.get(0).setLat(0.0);
                        stopLists.get(0).setLng(0.0);
                        jstart.put("stops", new JSONArray(new Gson().toJson(stopLists)));
                        jstart.put("trip_id", DriverSessionSave.getSession("trip_id", DriverOngoingAct.this));
                        jstart.put("driver_id", DriverSessionSave.getSession("Id", DriverOngoingAct.this));
                        jstart.put("taxi_id", DriverSessionSave.getSession("taxi_id", DriverOngoingAct.this));
                        final String driver_status_update = "type=driver_status_update";
                        if (currentAccuracy <= slabAccuracy) {
                            new Onboard(driver_status_update, jstart);
                        } else {
                            showLowAccuracyAlert();
                        }
                    }
                }
                // If trip in progress and going to end the trip
                else if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("Complete")) {

                    CompleteTrip(DriverOngoingAct.this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void orderDetailDialog() {
       /* BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(DriverOngoingAct.this);
        View sheetView = DriverOngoingAct.this.getLayoutInflater().inflate(R.layout.order_detail_dialog, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.getBehavior().setHideable(false);
        mBottomSheetDialog.getBehavior().setDraggable(false);
        mBottomSheetDialog.show();*/

        final View view = View.inflate(DriverOngoingAct.this, R.layout.order_detail_dialog, null);
        orderDialog = new Dialog(DriverOngoingAct.this, R.style.dialogAnimation);
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

        close_btn.setOnClickListener(view1 -> orderDialog.dismiss());


    }

    private void initializeLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    mLastLocation = location;
                    if (mapWrapperLayout != null && !mapWrapperLayout.isShown())
                        mapWrapperLayout.setVisibility(View.VISIBLE);
                    latitude1 = location.getLatitude();
                    longitude1 = location.getLongitude();
                    mLastLocation = location;
                    speed = LocationUpdate.speed;
                    speedTxt.setText("" + String.format(Locale.UK, "%.2f", speed) + "" + metricss.toLowerCase());


                    if (dropLatLng != null && p_travelstatus.trim().equals("2"))
                        if (!checkLocationInRoute()) {
                            viaLatlng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                            mHandler.sendEmptyMessage(1);
                        }

                    if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("Complete")) {
                        HeadTitle.setText(" " + DriverNC.getResources().getString(R.string.ongoing_journey));
                    }
                    bearing = location.getBearing();
                    bearings = location.getBearing();
                    if (map != null) zoom = map.getCameraPosition().zoom;

                    if (bearing >= 0) bearing = bearing + 90;
                    else bearing = bearing - 90;

                    try {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        // marker Animation Function
                        if (!animLocation) {
                            listPoint.add(latLng);
                        } else {
                            savedpoint.add(latLng);
                        }
                        if (listPoint.size() > 1) {

                            if (a_marker != null) {
                                a_marker.setVisible(false);
                                a_marker.remove();
                            }

                            if (!animStarted) {
                                if (savedLatLng != null) {
                                    listPoint.set(0, savedLatLng);
                                }
                                if (speed > 2 && map != null) {
                                    animStarted = true;
                                    animLocation = true;
                                    c_marker = map.addMarker(new MarkerOptions().position(listPoint.get(0)).rotation(0).anchor(0.5f, 0.5f).title("" + Address).icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_img)));
                                    c_marker.setVisible(true);
                                    if (map != null) {
                                        CameraPosition camPos = CameraPosition.builder(map.getCameraPosition() // current Camera
                                        ).bearing(bearings).build();

                                        if (DriverMapWrapperLayout.ismMapIsTouched()) {
                                            map.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
                                        }
                                    }
                                    savedLatLng = listPoint.get(listPoint.size() - 1);
                                    animateLine(listPoint, c_marker, bearings);
                                } else {
                                    if (c_marker != null) {
                                        c_marker.setVisible(false);
                                        c_marker.remove();
                                    }
                                    if (DriverGpsStatus.ischecked == 0) {
                                        DriverGpsStatus.ischecked = 1;
                                        if (map != null) {
                                            a_marker = map.addMarker(new MarkerOptions().position(latLng).rotation(0).anchor(0.5f, 0.5f).title("" + Address).icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_img)));
                                        }
                                        a_marker.setVisible(true);

                                        CameraPosition camPos = CameraPosition.builder(map.getCameraPosition() // current Camera
                                        ).bearing(bearings).build();
                                        map.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));


                                    } else {

                                        if (map != null) {
                                            a_marker = map.addMarker(new MarkerOptions().position(listPoint.get(0)).rotation(0).anchor(0.5f, 0.5f).title("" + Address).icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_img)));
                                        }
                                        a_marker.setVisible(true);

                                        CameraPosition camPos = CameraPosition.builder(map.getCameraPosition() // current Camera
                                        ).bearing(bearings).build();
                                        map.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));


                                    }
                                }
                            }

                        }

                        bearing = 0;
                        bearings = 0;
                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }
                }
            }
        };
    }

    public void setCurrentLocationPosition(int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) mov_cur_loc.getLayoutParams();
        marginParams.setMargins(left, top, right, bottom);
        mov_cur_loc.setLayoutParams(marginParams);

        ViewGroup.MarginLayoutParams navigatorLayoutLayoutParams = (ViewGroup.MarginLayoutParams) navigator_layout.getLayoutParams();
        navigatorLayoutLayoutParams.setMargins(left, top, right, bottom);
        navigator_layout.setLayoutParams(navigatorLayoutLayoutParams);
    }

    /**
     * View enabling in display with delay
     */
    public void ViewEnabledWithDelay(int i, Button butt_onboard) {
        butt_onboard.setEnabled(false);
        new Handler().postDelayed(() -> {
            if (butt_onboard != null) butt_onboard.setEnabled(true);
        }, i);
    }


    private void RetryLocationPopUp() {
        cancelLoading();
        if (retryCount > 2) {
            Utility.actionSheetCancel(DriverOngoingAct.this, DriverNC.getString(R.string.address_cant_fetch), DriverNC.getString(R.string.retry), DriverNC.getString(R.string.use_map), false, new AlertListener() {
                @Override
                public void onSuccess() {
                    retryCount++;
                    showLoading(DriverOngoingAct.this);
                    getCurrentLocation(LOCATION_REQUEST_TYPE_RETRY);
                }

                @Override
                public void onFailure() {
                    Intent intent = new Intent(DriverOngoingAct.this, DriverSelectDropLocationAct.class);
                    intent.putExtra("dropLocation", dropLatLng);
                    startActivityForResult(intent, 300);
                }
            });
            /*
            dialog1 = Driver_Utils.alert_view_dialog(DriverOngoingAct.this, DriverNC.getResources().getString(R.string.message), DriverNC.getString(R.string.address_cant_fetch), DriverNC.getString(R.string.retry), DriverNC.getString(R.string.use_map), false, (dialog, which) -> {
                retryCount++;
                showLoading(DriverOngoingAct.this);
                getCurrentLocation(LOCATION_REQUEST_TYPE_RETRY);
            }, (dialog, which) -> {
                Intent intent = new Intent(DriverOngoingAct.this, DriverSelectDropLocationAct.class);
                intent.putExtra("dropLocation", dropLatLng);
                startActivityForResult(intent, 300);
            }, "");

             */
        } else {
            Utility.actionSheetCancel(DriverOngoingAct.this, DriverNC.getString(R.string.address_cant_fetch), DriverNC.getString(R.string.retry), "", false, new AlertListener() {
                @Override
                public void onSuccess() {
                    retryCount++;
                    showLoading(DriverOngoingAct.this);
                    getCurrentLocation(LOCATION_REQUEST_TYPE_RETRY);
                }

                @Override
                public void onFailure() {

                }
            });
            /*
            dialog1 = Driver_Utils.alert_view_dialog(DriverOngoingAct.this, DriverNC.getResources().getString(R.string.message), DriverNC.getString(R.string.address_cant_fetch), DriverNC.getString(R.string.retry), null, false, (dialog, which) -> {
                retryCount++;
                showLoading(DriverOngoingAct.this);
                getCurrentLocation(LOCATION_REQUEST_TYPE_RETRY);
            }, null, "");

             */
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        showLoading(DriverOngoingAct.this);
        String Address;
        Double obtainedlatitude, obtainedlongitude;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {
            if (data != null) {
                Bundle res = data.getExtras();
                Address = res.getString("param_result");
                obtainedlatitude = res.getDouble("lat");
                obtainedlongitude = res.getDouble("lng");
                latitude1 = obtainedlatitude;
                longitude1 = obtainedlongitude;
                DriverSessionSave.saveSession("drop_location", Address, DriverOngoingAct.this);
                if (!DriverSessionSave.getSession(DriverCommonData.LAST_KNOWN_LAT, DriverOngoingAct.this).equals("")) {
                    Double lastknownlatitude = Double.parseDouble(DriverSessionSave.getSession(DriverCommonData.LAST_KNOWN_LAT, DriverOngoingAct.this));
                    Double lastknowlongitude = Double.parseDouble(DriverSessionSave.getSession(DriverCommonData.LAST_KNOWN_LONG, DriverOngoingAct.this));
                    LocalDriverDistanceCalculation.newInstance(DriverOngoingAct.this).haversine(lastknownlatitude, lastknowlongitude, latitude1, longitude1);
                } else {
                    LocalDriverDistanceCalculation.newInstance(DriverOngoingAct.this).haversine(latitude1, longitude1, latitude1, longitude1);
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation(int locationRequestType) {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                handleLastLocation(location, locationRequestType);
            }
        });
        startLocationUpdates();
    }

    private void handleLastLocation(Location location, int locationRequestType) {
        switch (locationRequestType) {
            case LOCATION_REQUEST_TYPE_RETRY:
                new Handler().postDelayed(() -> {
                    if (location.getAccuracy() < 500) {
                        if (location.getLatitude() != 0.0) {
                            latitude1 = location.getLatitude();
                            longitude1 = location.getLongitude();
                            new DriverGetAddressFromLatLng(DriverOngoingAct.this, new LatLng(latitude1, longitude1), DriverOngoingAct.this, "").execute();
                        }

                    } else {
                        cancelLoading();
                        RetryLocationPopUp();
                    }
                }, 2000);
                break;
            case LOCATION_REQUEST_TYPE_INITIAL:
                mLastLocation = location;
                if (mLastLocation != null) {
                    latitude1 = mLastLocation.getLatitude();
                    longitude1 = mLastLocation.getLongitude();
                    viaLatlng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    currentLatLng = new LatLng(latitude1, longitude1);
                    final LatLng coordinate = new LatLng(latitude1, longitude1);
                    if (map != null)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, zoom));
                    if (mapWrapperLayout != null) mapWrapperLayout.setVisibility(View.VISIBLE);
                }
                break;
            case LOCATION_REQUEST_TYPE_COMPLETE_TRIP:
                mLastLocation = location;
                if (mLastLocation != null && mLastLocation.getAccuracy() <= slabAccuracy) {

                    latitude1 = mLastLocation.getLatitude();
                    longitude1 = mLastLocation.getLongitude();
                    new DriverGetAddressFromLatLng(DriverOngoingAct.this, new LatLng(latitude1, longitude1), DriverOngoingAct.this, "").execute();
                } else {
                    cancelLoading();
                    RetryLocationPopUp();
                }
                break;
        }
    }

    public float pxtoDp(int px) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        DriverSystems.out.println("pxxxxxx" + dp + "___" + px + "__" + metrics.densityDpi);
        return dp;

    }

    /**
     * This method is called once the map initializtion is ready
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {

            try {
// Customise the styling of the base map using a JSON object defined
// in a raw resource file.
                boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(DriverOngoingAct.this, R.raw.driver_map_style));

                if (!success) {
                    DriverSystems.out.println("Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                DriverSystems.out.println("Can't find style. Error: ");
            }
            try {
                final int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(DriverOngoingAct.this);
                if (resultCode == ConnectionResult.SUCCESS) {
                    String imagepath = "";
                    if (!DriverSessionSave.getSession("p_image", DriverOngoingAct.this).equals("")) {
                        imagepath = "" + DriverSessionSave.getSession("p_image", DriverOngoingAct.this);
                        Log.i("Imagepath in session", DriverSessionSave.getSession("p_image", DriverOngoingAct.this));
                    } else
//                        imagepath = DriverSessionSave.getSession("noimage_base", DriverOngoingAct.this);
//                    Picasso.get().load(imagepath).placeholder(getResources().getDrawable(R.drawable.driver_loadingimage)).error(getResources().getDrawable(R.drawable.driver_noimage)).into(proimg);

                        if (imagepath != null && imagepath.length() > 0) {
                            Picasso.get().load(imagepath).error(R.drawable.loadingimage).placeholder(R.drawable.loadingimage).into(proimg);
                        } else {
                            if (DriverSessionSave.getSession("passenger_name", DriverOngoingAct.this) != "") {
                                ProfileImageSetupClass.setupProfileImage(DriverSessionSave.getSession("passenger_name", DriverOngoingAct.this), proimg);
                            } else {
                                Picasso.get().load(R.drawable.loadingimage).into(proimg);
                            }
                        }


                    MapsInitializer.initialize(DriverOngoingAct.this);
                    mapWrapperLayout = findViewById(R.id.map_relative_layout);
                    mapWrapperLayout.init(map, getPixelsFromDp(this, 39 + 20));
                    map.getUiSettings().setZoomControlsEnabled(false);
                    map.setOnCameraMoveStartedListener(this);
                    map.getUiSettings().setCompassEnabled(false);
                    map.getUiSettings().setMyLocationButtonEnabled(false);
                    map.setMyLocationEnabled(false);
                    map.setPadding(0, 0, 0, 120);
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mapsupport_lay.setVisibility(View.VISIBLE);

                    System.err.println("animate camera:" + latitude1 + "lng" + longitude1);

                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LocationUpdate.currentLatitude, LocationUpdate.currentLongtitude), zoom));
                    if (mapWrapperLayout != null && !mapWrapperLayout.isShown())
                        mapWrapperLayout.setVisibility(View.VISIBLE);
                } else {
                    mapsupport_lay.setVisibility(View.GONE);
                    nodataTxt.setVisibility(View.VISIBLE);
                    nodataTxt.setText("" + "" + DriverNC.getResources().getString(R.string.device_not_support_map));
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void haversineResult(Boolean success) {
        if (success) {
            //do the complete trip process....
            CompleteSuccessClick();
        }
    }


    @Override
    public void onCameraMoveStarted(int i) {
        if (!DriverMapWrapperLayout.ismMapIsTouched()) {
            navigator_layout.setVisibility(View.GONE);
            mov_cur_loc.setVisibility(View.VISIBLE);
        } else {
            navigator_layout.setVisibility(View.VISIBLE);
            mov_cur_loc.setVisibility(View.GONE);
        }

    }

    @Override
    public void positiveButtonClick(DialogInterface dialog, int id, String s) {
        switch (s) {
            case "1":
                try {
                    dialog.dismiss();
                    final Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" +/* MainActivityDriver.mMyStatus.getpassengerphone()*/ call_masking_ph_no));
                    /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }*/
                    startActivity(callIntent);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;
            case "2":
                dialog.dismiss();
                if (runningFor() > 10 && !LocationUpdate.DISTANCE_CALCULATION_INPROGRESS) {
                    showLoading(DriverOngoingAct.this);
                    getCurrentLocation(LOCATION_REQUEST_TYPE_COMPLETE_TRIP);
                } else {
                    cancelLoading();
                    DriverCToast.ShowToast(DriverOngoingAct.this, DriverNC.getString(R.string.distance_calcuation_inprogress));
                }
                break;
            case "3":
                try {
                    dialog.dismiss();
                    // TODO Auto-generated method stub
                    if (DriverSessionSave.getSession("status", DriverOngoingAct.this).equalsIgnoreCase("A"))
                        DriverCToast.ShowToast(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.you_are_in_trip));
//                        dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.you_are_in_trip), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
                    else if (DriverSessionSave.getSession("trip_id", DriverOngoingAct.this).length() == 0)
                        finish();
                    else {
                        nonactiityobj.stopServicefromNonActivity(DriverOngoingAct.this);
                        JSONObject j = new JSONObject();
                        j.put("pass_logid", DriverSessionSave.getSession("trip_id", DriverOngoingAct.this));
                        j.put("driver_id", DriverSessionSave.getSession("Id", DriverOngoingAct.this));
                        j.put("taxi_id", DriverSessionSave.getSession("taxi_id", DriverOngoingAct.this));
                        j.put("company_id", DriverSessionSave.getSession("company_id", DriverOngoingAct.this));
                        j.put("driver_reply", "C");
                        j.put("field", "");
                        j.put("flag", "1");
                        if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("Arrivd"))
                            j.put("driver_arrived", 1);
                        else j.put("driver_arrived", 0);
                        final String canceltrip_url = "type=driver_reply";
                        new CancelTrip(canceltrip_url, j);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;
            case "4":
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void negativeButtonClick(DialogInterface dialog, int id, String s) {
        switch (s) {
            case "1":
                dialog.dismiss();
                break;
            case "2":
                dialog.dismiss();
                break;
            case "3":
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void setaddress(double latitude, double longitude, String Address, String type) {
        if (Address.length() != 0) {
            cancelLoading();
            try {
                Address = Address.replaceAll("null", "").replaceAll(", ,", "").replaceAll(", ,", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            DriverSessionSave.saveSession("drop_location", Address, DriverOngoingAct.this);
            haversineResult(true);
          /*  if (!SessionSave.getSession(CommonData.LAST_KNOWN_LAT, OngoingAct.this).equals("")) {
                Double lastknownlatitude = Double.parseDouble(SessionSave.getSession(CommonData.LAST_KNOWN_LAT, OngoingAct.this));
                Double lastknowlongitude = Double.parseDouble(SessionSave.getSession(CommonData.LAST_KNOWN_LONG, OngoingAct.this));
                LocalDistanceCalculation.newInstance(OngoingAct.this).haversine(lastknownlatitude, lastknowlongitude, latitude1, longitude1);
            } else

                LocalDistanceCalculation.newInstance(OngoingAct.this).haversine(latitude1, longitude1, latitude1, longitude1);
      */
        }
    }

    /**
     * This is method for confirmation for complete the trip
     */
    public void CompleteTrip(final AppCompatActivity context) {
        Utility.actionSheet(DriverOngoingAct.this, DriverNC.getResources().getString(R.string.confirm_complete), DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), false, new AlertListener() {
            @Override
            public void onSuccess() {
                if (runningFor() > 10 && !LocationUpdate.DISTANCE_CALCULATION_INPROGRESS) {
                    showLoading(DriverOngoingAct.this);
                    getCurrentLocation(LOCATION_REQUEST_TYPE_COMPLETE_TRIP);
                } else {
                    cancelLoading();
                    DriverCToast.ShowToast(DriverOngoingAct.this, DriverNC.getString(R.string.distance_calcuation_inprogress));
                }
            }

            @Override
            public void onFailure() {

            }
        });
//        dialog1 = Driver_Utils.alert_view(context, DriverNC.getResources().getString(R.string.message), DriverNC.getResources().getString(R.string.confirm_complete), DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), false, DriverOngoingAct.this, "2");
    }

    public void CompleteSuccessClick() {
        try {
            MainActivityDriver.mMyStatus.setOnstatus("Complete");
//            stopService(new Intent(OngoingAct.this, WaitingTimerRun.class));
//            myHandler.removeCallbacks(r);
            float h = 0.0f;
            waitingTime = DriverCommonData.getDateForWaitingTime(DriverSessionSave.getWaitingTime(DriverOngoingAct.this));
            if (waitingTime.equals("")) waitingTime = "00:00:00";
            String waitNoArabic = DriverFontHelper.convertfromArabic(waitingTime);
            DriverSystems.out.println("Errror in okkkk" + waitNoArabic + "---" + waitingTime);
            String[] split = waitNoArabic.split(":");
            int hr = Integer.parseInt(split[0]);
            float min = Integer.parseInt(split[1]);
            float sec = Float.parseFloat(split[2]);
            DriverSystems.out.println("Hour:" + hr + "min:" + min + "sec:" + sec);
            min = min / 60;
            sec = sec / 3600;
            waitingHr = hr + min + sec;
            MainActivityDriver.mMyStatus.setDriverWaitingHr(Float.toString(waitingHr));
            DriverSessionSave.saveSession("waitingHr", Float.toString(waitingHr), DriverOngoingAct.this);
            final String completeUrl = "type=complete_trip";
            new CompleteTrip(completeUrl, latitude1, longitude1);
        } catch (Exception e) {
            e.printStackTrace();
            DriverSystems.out.println("Errror in okkkk" + e);
            // TODO: handle exception
        }
    }

    /**
     * Onstart method by default it called when activity is open.
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Starting the location updates
     */
    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(DriverOngoingAct.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(DriverOngoingAct.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Utility.actionSheet(DriverOngoingAct.this, DriverNC.getResources().getString(R.string.str_loc), DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), false, new AlertListener() {
                @Override
                public void onSuccess() {
                    ActivityCompat.requestPermissions(DriverOngoingAct.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_GPS);
                }

                @Override
                public void onFailure() {

                }
            });
            /*
            dialog1 = Driver_Utils.alert_view_dialog(DriverOngoingAct.this, "", DriverNC.getResources().getString(R.string.str_loc), DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), true, (dialog, i) -> {
                ActivityCompat.requestPermissions(DriverOngoingAct.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_GPS);
                dialog.dismiss();
            }, (dialog, i) -> dialog.dismiss(), "");

             */

        } else {
            fusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    protected void createLocationRequest() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(DriverLocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(DriverLocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        initializeLocationCallback();
        getCurrentLocation(LOCATION_REQUEST_TYPE_INITIAL);
    }

    @Override
    protected void onDestroy() {
        if (dialog1 != null) Driver_Utils.closeDialog(dialog1);

        super.onDestroy();
        stopLocationUpdates();

        // unregister local broadcast
        LocalBroadcastManager.getInstance(this).unregisterReceiver(listener);

        if (animator != null && animator.isRunning()) {

            animator.cancel();
            map = null;
            if (c_marker != null) {
                c_marker.setVisible(false);
                c_marker.remove();
            } else if (a_marker != null) {
                a_marker = null;
            }
        }
    }

    /**
     * This method is used to get string details
     */
    synchronized void getValueDetail() {
        Field[] fieldss = R.string.class.getDeclaredFields();
        // fields =new int[fieldss.length];
        for (Field field : fieldss) {
            int id = getResources().getIdentifier(field.getName(), "string", getPackageName());
            if (DriverNC.nfields_byName.containsKey(field.getName())) {
                DriverNC.fields.add(field.getName());
                DriverNC.fields_value.add(getResources().getString(id));
                DriverNC.fields_id.put(field.getName(), id);

            }
        }


        for (Map.Entry<String, String> entry : DriverNC.nfields_byName.entrySet()) {
            String h = entry.getKey();
            String value = entry.getValue();
            DriverNC.nfields_byID.put(DriverNC.fields_id.get(h), DriverNC.nfields_byName.get(h));
            // do stuff
        }

    }

    /**
     * To get current location as address.
     */
    private void location() {

        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            if (mLastLocation != null) {
                addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
            }
        } catch (final IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            try {
                Address = Address.replaceAll("null", "").replaceAll(", ,", "").replaceAll(", ,", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else Address = "";
    }

    private void pickUpDropMarker() {
        try {
            if (map != null) {
                if (p_latitude != null && p_latitude != 0.0 && p_longtitude != null && p_longtitude != 0.0) {
                    if (p_marker != null) p_marker.remove();
                    p_marker = map.addMarker(new MarkerOptions().position(new LatLng(p_latitude, p_longtitude)).title("" + DriverNC.getResources().getString(R.string.pickuploc)).icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_flag_green)).draggable(false));
                    pickupLatLng = new LatLng(p_latitude, p_longtitude);
                }
                if (d_latitude != null && d_latitude != 0.0 && d_longtitude != null && d_longtitude != 0.0) {
                    if (d_marker != null) d_marker.remove();
                    int px = getResources().getDimensionPixelSize(R.dimen.map_dot_marker_size);
                    Bitmap mDotMarkerBitmap = Bitmap.createBitmap(px, px, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(mDotMarkerBitmap);
                    Drawable shape = getResources().getDrawable(R.drawable.driver_cust_progress);
                    shape.setBounds(0, 0, mDotMarkerBitmap.getWidth(), mDotMarkerBitmap.getHeight());
                    shape.draw(canvas);
                    d_marker = map.addMarker(new MarkerOptions().position(new LatLng(d_latitude, d_longtitude)).title("" + DriverNC.getResources().getString(R.string.droploc)).icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_flag_red)).draggable(false));
                    dropLatLng = new LatLng(d_latitude, d_longtitude);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to visible and invisible pickup and dropup address.
     */
    public void dropVisible() {
      /*  final float scale = this.getResources().getDisplayMetrics().density;
        int pixels = (int) (95 * scale + 0.5f);
        dropLay.getLayoutParams().height = pixels;
        dropLay.invalidate();

        dropppp.setVisibility(View.VISIBLE);

        pickup_pinlay.setVisibility(View.VISIBLE);
        pickup_pin.setVisibility(View.GONE);
        pickup_drop_Sep.setVisibility(View.VISIBLE);*/

    }

    public void Call_arrived() {
        butt_onboard.performClick();
    }

    /**
     * Initially update the trip details based on get_trip_detail response.
     */
    private void init() {

        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverOngoingAct.this.findViewById(android.R.id.content)).getChildAt(0)), DriverOngoingAct.this);
        DriverSystems.out.println("_________________OOOO" + MainActivityDriver.mMyStatus.getOnstatus());
        if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("on")) {
            if (SessionSave.getSession("is_enabled_ive_arrived", DriverOngoingAct.this).equals("0")) {
                Call_arrived();
            }
            HeadTitle.setText("" + DriverNC.getResources().getString(R.string.pickup_passenger));
            HeadTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            butt_onboard.setText("" + DriverNC.getResources().getString(R.string.ive_arrived));
            butt_onboard.setVisibility(View.VISIBLE);
            passnameTxt.setText(MainActivityDriver.mMyStatus.getOnpassengerName());
            vichle_name.setText(SessionSave.getSession("model_name", DriverOngoingAct.this));
            final String pickup = MainActivityDriver.mMyStatus.getOnpickupLocation();

            CurrentlocationTxt.setText(Html.fromHtml(pickup));
            txt_pickup.setText(Html.fromHtml(pickup));
            DriverFontHelper.applyFont(DriverOngoingAct.this, CurrentlocationTxt);

            if (!MainActivityDriver.mMyStatus.getPassengerOndropLocation().equals("")) {
                final String drop = MainActivityDriver.mMyStatus.getPassengerOndropLocation();
                if (!drop.trim().equals("")) {
                    dropVisible();
                    txt_drop.setText(Html.fromHtml(drop));
                    //droplocationTxt.setText(Html.fromHtml(drop));
                }
            } else droplocationTxt.setVisibility(View.GONE);
            if (!MainActivityDriver.mMyStatus.getpassengerNotes().equals("")) {
                final String notes = MainActivityDriver.mMyStatus.getpassengerNotes();
                tv_notes.setText(Html.fromHtml(notes));
            } else tv_notes.setVisibility(View.GONE);
            if (MainActivityDriver.mMyStatus.getOnpickupLatitude().length() != 0)
                p_latitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOnpickupLatitude());
            if (MainActivityDriver.mMyStatus.getOnpickupLongitude().length() != 0)
                p_longtitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOnpickupLongitude());
            if (MainActivityDriver.mMyStatus.getOndropLatitude().length() != 0)
                d_latitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOndropLatitude());
            if (MainActivityDriver.mMyStatus.getOndropLongitude().length() != 0)
                d_longtitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOndropLongitude());
            if (MainActivityDriver.mMyStatus.getOndriverLatitude().length() != 0)
                driver_latitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOndriverLatitude());
            if (MainActivityDriver.mMyStatus.getOndriverLongitude().length() != 0)
                driver_longtitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOndriverLongitude());
            new GetPickdropLoc().execute();
            navigator_layout.setVisibility(View.VISIBLE);
        } else if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("Arrivd")) {
            HeadTitle.setText("" + DriverNC.getResources().getString(R.string.heading_ongoing));
            HeadTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            butt_onboard.setText("" + DriverNC.getResources().getString(R.string.pass_onboard));
            passnameTxt.setText(MainActivityDriver.mMyStatus.getOnpassengerName());
            vichle_name.setText(SessionSave.getSession("model_name", DriverOngoingAct.this));
            butt_onboard.setVisibility(View.VISIBLE);
            final String pickup = MainActivityDriver.mMyStatus.getOnpickupLocation();
            CurrentlocationTxt.setText(Html.fromHtml(pickup));
            txt_pickup.setText(Html.fromHtml(pickup));
            if (!MainActivityDriver.mMyStatus.getPassengerOndropLocation().equals("")) {
                final String drop = MainActivityDriver.mMyStatus.getPassengerOndropLocation();
                if (!drop.trim().equals("")) {
                    dropVisible();
                    //droplocationTxt.setText(Html.fromHtml(drop));
                    txt_drop.setText(Html.fromHtml(drop));
                }
            } else droplocationTxt.setVisibility(View.GONE);
            if (!MainActivityDriver.mMyStatus.getpassengerNotes().equals("")) {
                final String notes = MainActivityDriver.mMyStatus.getpassengerNotes();
                tv_notes.setText(Html.fromHtml(notes));
            } else tv_notes.setVisibility(View.GONE);
            if (MainActivityDriver.mMyStatus.getOnpickupLatitude().length() != 0)
                p_latitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOnpickupLatitude());
            if (MainActivityDriver.mMyStatus.getOnpickupLongitude().length() != 0)
                p_longtitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOnpickupLongitude());
            if (MainActivityDriver.mMyStatus.getOndropLatitude().length() != 0)
                d_latitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOndropLatitude());
            if (MainActivityDriver.mMyStatus.getOndropLongitude().length() != 0)
                d_longtitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOndropLongitude());
            if (MainActivityDriver.mMyStatus.getOndriverLatitude().length() != 0)
                driver_latitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOndriverLatitude());
            if (MainActivityDriver.mMyStatus.getOndriverLongitude().length() != 0)
                driver_longtitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOndriverLongitude());
            new GetPickdropLoc().execute();
        } else if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("Complete")) {
            TripcancelTxt.setVisibility(View.GONE);
            HeadTitle.setText("" + DriverNC.getResources().getString(R.string.ongoing_journey));
            HeadTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            //tripinprogress_lay.setVisibility(View.VISIBLE);
            pickup_drop_lay.setVisibility(View.GONE);
            contact_lay.setVisibility(View.VISIBLE);
            TripcancelTxt.setVisibility(View.GONE);
            contact_txt.setVisibility(View.VISIBLE);
            tripDetails_lay.setBackgroundColor(getResources().getColor(R.color.white));
            trip_view.setVisibility(View.VISIBLE);
            butt_onboard.setText("" + DriverNC.getResources().getString(R.string.arvd_destination));
            passnameTxt.setText(MainActivityDriver.mMyStatus.getOnpassengerName());
            vichle_name.setText(SessionSave.getSession("model_name", DriverOngoingAct.this));
            final String pickup = MainActivityDriver.mMyStatus.getOnpickupLocation();
            CurrentlocationTxt.setText(Html.fromHtml(pickup));
            txt_pickup.setText(Html.fromHtml(pickup));
            DriverFontHelper.applyFont(DriverOngoingAct.this, CurrentlocationTxt);

            if (!MainActivityDriver.mMyStatus.getPassengerOndropLocation().equals("")) {
                final String drop = MainActivityDriver.mMyStatus.getPassengerOndropLocation();
                if (!drop.trim().equals("")) {
                    dropVisible();
                    // droplocationTxt.setText(Html.fromHtml(drop));
                    txt_drop.setText(Html.fromHtml(drop));
                }
            } else droplocationTxt.setVisibility(View.GONE);
            if (!MainActivityDriver.mMyStatus.getpassengerNotes().equals("")) {
                final String notes = MainActivityDriver.mMyStatus.getpassengerNotes();
                tv_notes.setText(Html.fromHtml(notes));
            } else tv_notes.setVisibility(View.GONE);
            if (MainActivityDriver.mMyStatus.getOnpickupLatitude().length() != 0)
                p_latitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOnpickupLatitude());
            if (MainActivityDriver.mMyStatus.getOnpickupLongitude().length() != 0)
                p_longtitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOnpickupLongitude());
            if (MainActivityDriver.mMyStatus.getOndropLatitude().length() != 0)
                d_latitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOndropLatitude());
            if (MainActivityDriver.mMyStatus.getOndropLongitude().length() != 0)
                d_longtitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOndropLongitude());
            if (MainActivityDriver.mMyStatus.getOndriverLatitude().length() != 0)
                driver_latitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOndriverLatitude());
            if (MainActivityDriver.mMyStatus.getOndriverLongitude().length() != 0)
                driver_longtitude = Double.parseDouble(MainActivityDriver.mMyStatus.getOndriverLongitude());
            new GetPickdropLoc().execute();

            speed_lay.setVisibility(View.GONE);
//            myHandler.postDelayed(r, 0);
            butt_onboard.setVisibility(View.VISIBLE);
        } else {
            new GetPickdropLoc().execute();
            butt_onboard.setVisibility(View.INVISIBLE);
            passnameTxt.setText("" + DriverNC.getResources().getString(R.string.you));
            nodataTxt.setText("" + DriverNC.getResources().getString(R.string.nodata));
            nodataTxt.setVisibility(View.VISIBLE);
            mapsupport_lay.setVisibility(View.GONE);
        }
    }

    double roundTwoDecimals(double d) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat formatter = (DecimalFormat) nf;
        formatter.applyPattern("#.##");
        String fString = formatter.format(d);
        return Double.parseDouble(fString);
    }

    /**
     * This method is to check current location is in the route.
     */
    private boolean checkLocationInRoute() {
        boolean inside = true;
        if (DriverRoute.line != null) {
            inside = PolyUtil.isLocationOnEdge(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), DriverRoute.line.getPoints(), true, 1500);
        }
        return inside;
    }

    @Override
    public void onBackPressed() {
        ActivityManager mngr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

        if (taskList.get(0).numActivities == 1 && taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
            Log.i(TAG, "This is last activity in the stack");
            startActivity(new Intent(DriverOngoingAct.this, DriverMyStatus.class));
            finish();
        } else {
            super.onBackPressed();
        }

    }

    /**
     * convert Speed
     */
    private double convertSpeed(double speed) {
        return ((speed * 3600) * 0.001);
    }

    /**
     * This method is used to round the decimal value
     */
    private double roundDecimal(double value, final int decimalPlace) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        value = bd.doubleValue();
        return value;
    }

    /**
     * Marker Animation with array of latlng
     */
    public void animateLine(ArrayList<LatLng> Trips, Marker marker, float bearings) {
        _trips.clear();
        _trips.addAll(Trips);
        _marker = marker;
        animteBearing = bearings;
        animateMarker();
    }

    /**
     * Marker Animation with array of latlng
     */
    public void animateMarker() {
        TypeEvaluator<LatLng> typeEvaluator = (fraction, startValue, endValue) -> _latLngInterpolator.interpolate(fraction, startValue, endValue);
        Property<Marker, LatLng> property = Property.of(Marker.class, LatLng.class, "position");


        for (int i = 0; i < _trips.size(); i++) {
            animator = ObjectAnimator.ofObject(_marker, property, typeEvaluator, _trips.get(i));
        }

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listPoint.clear();
                if (c_marker != null && map != null) {
                    c_marker.setVisible(false);
                    c_marker.remove();
                    a_marker = map.addMarker(new MarkerOptions().position(savedLatLng).rotation(0).anchor(0.5f, 0.5f).title("" + DriverNC.getResources().getString(R.string.you_are_here)).icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_img)));
                    a_marker.setVisible(true);
                    if (DriverMapWrapperLayout.ismMapIsTouched()) {
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(savedLatLng, zoom));
                    }
                    if (savedpoint.size() > 1) {
                        for (int i = 0; i < savedpoint.size(); i++) {
                            listPoint.add(savedpoint.get(i));
                        }
                        savedpoint.clear();
                        animStarted = false;
                        animLocation = true;
                    } else {
                        animStarted = false;
                        animLocation = false;
                    }
                }

            }
        });
        animator.setDuration(5000);
        animator.start();
    }

    private void startSOSService() {
        DriverSessionSave.saveSession("sos_id", DriverSessionSave.getSession("Id", DriverOngoingAct.this), DriverOngoingAct.this);
        DriverSessionSave.saveSession("user_type", "d", DriverOngoingAct.this);
        startService(new Intent(DriverOngoingAct.this, SOSService.class));
    }

    private ArrayList<DriverStopData> parseStop(String path) {
        ArrayList<DriverStopData> driverStopDataArrayList = new ArrayList<>();
        stopListData = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<DriverStopData>>() {
        }.getType();

        ArrayList<DriverStopData> stopList = gson.fromJson(path, type);

        for (int i = 0; i < stopList.size(); i++) {
            stopListData.add(stopList.get(i).getLatLng());
            driverStopDataArrayList.add(stopList.get(i));
        }
        pickUpDropViewDriver.setData(stopList, "ONGOING", DriverSessionSave.getSession("Lang", DriverOngoingAct.this));
        return driverStopDataArrayList;

    }

    private void setStopAdapter() {
        if (getStops(stopLists).size() > 0) {
            stop_recyclerView.setVisibility(View.VISIBLE);
            DriverStopListAdapter adapter = new DriverStopListAdapter(DriverOngoingAct.this, getStops(stopLists));
            stop_recyclerView.setAdapter(adapter);
        } else {
            stop_recyclerView.setVisibility(View.GONE);
            stop_recyclerView.setAdapter(null);
        }
    }

    private ArrayList<String> getStops(ArrayList<DriverStopData> stopLists) {
        ArrayList<String> splitStops = new ArrayList<>();
        for (int j = 1; j < stopLists.size(); j++) {
            splitStops.add(stopLists.get(j).getPlaceName());
        }
        return splitStops;
    }

    private void showLowAccuracyAlert() {
        DriverCToast.ShowToast(DriverOngoingAct.this, "" + DriverNC.getString(R.string.low_gps_alert_message));
//        dialog1 = Driver_Utils.alert_view_dialog(this, null, DriverNC.getString(R.string.low_gps_alert_message), DriverNC.getString(R.string.ok), DriverNC.getString(R.string.cancel), true, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        }, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        }, "");
    }

    /**
     * Method to create views dynamically if ArrayList<StopData> value not available (ie., Normal flow)
     * <p>
     * New ArrayList of StopData values created with pickup and drop(if available) and dynamic views created based on that ArrayList
     */
    private ArrayList<DriverStopData> createPickAndStopView(String p_pickloc, String p_latitude, String p_longtitude, String p_droploc, String d_latitude, String d_longtitude) {
        ArrayList<DriverStopData> pickUpDropList = new ArrayList<>();
        if (p_pickloc != null && !p_pickloc.isEmpty() && p_latitude != null && !p_latitude.isEmpty() && p_longtitude != null && !p_longtitude.isEmpty())
            pickUpDropList.add(new DriverStopData(0, Double.parseDouble(p_latitude), Double.parseDouble(p_longtitude), p_pickloc, "", ""));
        if (p_droploc != null && !p_droploc.isEmpty() && d_latitude != null && !d_latitude.isEmpty() && d_longtitude != null && !d_longtitude.isEmpty())
            pickUpDropList.add(new DriverStopData(0, Double.parseDouble(d_latitude), Double.parseDouble(d_longtitude), p_droploc, "", ""));
        pickUpDropViewDriver.setData(pickUpDropList, "ONGOING", DriverSessionSave.getSession("Lang", DriverOngoingAct.this));
        return pickUpDropList;
    }

    private void setDelayForCancel() {
        if (!DriverSessionSave.getSession(DriverCommonData.DRIVER_ARRIVED_TIME, this).isEmpty()) {
            long enableTime = 0L, driverArrivedTime = 0L, currentTime;
            try {
                driverArrivedTime = Long.parseLong(DriverSessionSave.getSession(DriverCommonData.DRIVER_ARRIVED_TIME, this));
                enableTime = Long.parseLong(DriverSessionSave.getSession(DriverCommonData.SHOW_CANCEL_BUTTON, this)) * (1000 * 60); //300000
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            currentTime = System.currentTimeMillis();
            DriverSystems.out.println("setDelayForCancel(): " + enableTime + "**" + driverArrivedTime + "**" + (currentTime - driverArrivedTime) + "**" + currentTime);
            if ((currentTime - driverArrivedTime) > enableTime) {
                DriverSystems.out.println("setDelayForCancel(): 1");
                TripcancelTxt.setVisibility(View.GONE);
            } else {
                DriverSystems.out.println("setDelayForCancel(): 2 " + (enableTime - (currentTime - driverArrivedTime)));
                TripcancelTxt.setVisibility(View.GONE);
                new Handler().postDelayed(() -> TripcancelTxt.setVisibility(View.GONE), enableTime - (currentTime - driverArrivedTime));
            }
        } else TripcancelTxt.setVisibility(View.GONE);
    }

    /**
     * To get pickup/drip location as address and place the pickup/drop markers on map.
     */
    private class GetPickdropLoc extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(final Void... params) {
            try {
                location();
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Void result) {

            super.onPostExecute(result);
            try {
                map.clear();
                startLocationUpdates();
                if (mLastLocation != null) {
                    latitude1 = mLastLocation.getLatitude();
                    longitude1 = mLastLocation.getLongitude();
                    bearing = mLastLocation.getBearing();
                    currentLatLng = new LatLng(latitude1, longitude1);
                }
                if (bearing >= 0) bearing = bearing + 90;
                else bearing = bearing - 90;
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                bearing = 0;
                pickUpDropMarker();
                if (driver_latitude != null && driver_latitude != 0.0 && driver_longtitude != null && driver_longtitude != 0.0) {
                    currentLatLng = new LatLng(driver_latitude, driver_longtitude);
                }

                new Handler().postDelayed(() -> {
                    ROUTE_DRAW_ON_START = true;
                    mHandler.sendEmptyMessage(1);
                }, 5000);

                if (!Address.equals("")) {
                    MainActivityDriver.mMyStatus.setOndropLocation(Address);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Used to call the driver arrived Api and parse the response
     */
    private class DriverArrived implements DriverAPIResult {
        DriverArrived(final String url, JSONObject data) {

            try {
                if (isOnline()) {
                    butt_onboard.setEnabled(false);
                    new DriverAPIService_Retrofit_JSON(DriverOngoingAct.this, this, data, false).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
                }
            } catch (Exception e) {
                butt_onboard.setEnabled(true);
                e.printStackTrace();
            }
        }

        /**
         * Parse the response and update the UI.
         */
        @Override
        public void getResult(final boolean isSuccess, final String result) {
            butt_onboard.setEnabled(true);
            try {
                if (isSuccess) {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        DriverSessionSave.saveSession(DriverCommonData.DRIVER_ARRIVED_TIME, "" + System.currentTimeMillis(), DriverOngoingAct.this);
                        setDelayForCancel();
                        DriverSessionSave.saveSession("Ongoing", "ongoing", DriverOngoingAct.this);
                        MainActivityDriver.mMyStatus.setOnstatus("Arrivd");
                        HeadTitle.setText("" + DriverNC.getResources().getString(R.string.heading_ongoing));
                        HeadTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        butt_onboard.setText("" + DriverNC.getResources().getString(R.string.pass_onboard));
                        DriverSessionSave.saveSession("status", "B", DriverOngoingAct.this);
                        DriverSessionSave.setWaitingTime(0L, DriverOngoingAct.this);
                        nonactiityobj.startServicefromNonActivity(DriverOngoingAct.this);
                        CancelTxt.setText(String.format(Locale.UK, "00:00:00"));
                        new GetPickdropLoc().execute();
                    } else if (json.getInt("status") == -1) {
                        DriverSessionSave.saveSession("status", "F", DriverOngoingAct.this);
                        MainActivityDriver.mMyStatus.settripId("");
                        DriverSessionSave.saveSession("trip_id", "", DriverOngoingAct.this);
                        MainActivityDriver.mMyStatus.setOnstatus("On");
                        MainActivityDriver.mMyStatus.setOnPassengerImage("");
                        MainActivityDriver.mMyStatus.setOnpassengerName("");
                        MainActivityDriver.mMyStatus.setOndropLocation("");
                        MainActivityDriver.mMyStatus.setOnpickupLatitude("");
                        MainActivityDriver.mMyStatus.setOnpickupLongitude("");
                        MainActivityDriver.mMyStatus.setOndropLatitude("");
                        MainActivityDriver.mMyStatus.setOndropLongitude("");
                        DriverSessionSave.saveSession("Ongoing", "farecal", DriverOngoingAct.this);
                        final Intent jobintent = new Intent(DriverOngoingAct.this, DriverMyStatus.class);
                        Bundle extras = new Bundle();
                        extras.putString("alert_message", json.getString("message"));
                        jobintent.putExtras(extras);
                        startActivity(jobintent);
                        finish();
                    } else {
                        CancelTxt.setText(String.format(Locale.UK, "00:00:00"));
                        nonactiityobj.startServicefromNonActivity(DriverOngoingAct.this);
                    }
                } else {
                    runOnUiThread(() -> ShowToast(DriverOngoingAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Used to call the driver_status_update API and parse the response.
     */
    private class Onboard implements DriverAPIResult {
        String p_pickloc = "";
        String p_droploc = "";
        String dropLattitue = "";
        String dropLongitute = "";


        public Onboard(final String url, JSONObject data) {
            LocationUpdate.ClearSession(DriverOngoingAct.this);
            LocationUpdate.sLocation = "";
            LocationUpdate.localDistance = 0.0;
            DriverSessionSave.saveSession(DriverCommonData.DRIVER_LOCATION, "", DriverOngoingAct.this);
            try {
                if (isOnline()) {
                    butt_onboard.setEnabled(false);
                    new DriverAPIService_Retrofit_JSON(DriverOngoingAct.this, this, data, false).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
                }
            } catch (Exception e) {
                butt_onboard.setEnabled(true);
                e.printStackTrace();
            }
        }

        /**
         * Parse the response and update the UI.
         */
        @Override
        public void getResult(final boolean isSuccess, final String result) {
            butt_onboard.setEnabled(true);
            try {
                if (isSuccess) {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        if (sendPickupPoints != null) {
                            DriverSessionSave.saveLastLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), DriverOngoingAct.this);
                            sendPickupPoints.pickUpdate(mLastLocation);
                        }
                        final JSONObject detail = json.getJSONObject("detail");
                        DriverSessionSave.saveSession("Metric", detail.getString("metric"), DriverOngoingAct.this);
                        if (DriverSessionSave.getSession("Metric", DriverOngoingAct.this).equalsIgnoreCase("KM")) {
                            total_km.setText("Total km");
                        } else {
                            total_km.setText("Total miles");
                        }
                        card_view_pickup.setCardElevation(0);
                        card_view_pickup.setUseCompatPadding(false);
                        card_view_pickup.setRadius(0);
                        view_line_trip.setVisibility(View.GONE);
                        HeadTitle.setText("" + DriverNC.getResources().getString(R.string.ongoing_journey));
                        // tripinprogress_lay.setVisibility(View.VISIBLE); //hide
                        pickup_drop_lay.setVisibility(View.GONE);
                        contact_lay.setVisibility(View.VISIBLE);
                        contact_txt.setVisibility(View.VISIBLE);
                        tripDetails_lay.setBackgroundColor(getResources().getColor(R.color.white));
                        trip_view.setVisibility(View.VISIBLE);
                        HeadTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        TripcancelTxt.setVisibility(View.GONE);
                        //chatTxt.setVisibility(View.GONE);
                        butt_onboard.setText("" + DriverNC.getResources().getString(R.string.arvd_destination));
                        speed_lay.setVisibility(View.GONE);

                        waitingTimeTxt.setVisibility(View.VISIBLE);

                        speedTxt.setText("" + String.format(Locale.UK, "%.2f", LocationUpdate.speed) + "" + metricss.toLowerCase());
                        waitingTimeTxt.setText(String.format(Locale.UK, DriverCommonData.getDateForWaitingTime(DriverSessionSave.getWaitingTime(DriverOngoingAct.this))));

                        DriverSessionSave.setWaitingTime(0L, DriverOngoingAct.this);
                        DriverSessionSave.saveSession("travel_status", "2", DriverOngoingAct.this);
                        MainActivityDriver.mMyStatus.setOnstatus("Complete");
                        MainActivityDriver.mMyStatus.setdistance("");
                        DriverSessionSave.saveSession("status", "A", DriverOngoingAct.this);
                        nonactiityobj.startServicefromNonActivity(DriverOngoingAct.this);
                        p_pickloc = detail.getString("pickup_location");
                        p_droploc = detail.getString("drop_location");
                        dropLattitue = detail.getString("drop_latitude");
                        dropLongitute = detail.getString("drop_longitude");

                        MainActivityDriver.mMyStatus.setOnpickupLocation(p_pickloc);
                        MainActivityDriver.mMyStatus.setOndropLocation(p_droploc);
                        final String pickup = MainActivityDriver.mMyStatus.getOnpickupLocation();
                        String s = Html.fromHtml(pickup).toString();
                        CurrentlocationTxt.setText(s);
                        DriverFontHelper.applyFont(DriverOngoingAct.this, CurrentlocationTxt);
                        if (latitude1 != 0.0) {
                            pickupLatLng = new LatLng(latitude1, longitude1);
                            p_latitude = latitude1;
                            p_longtitude = longitude1;
                        }
                        if (pickupLatLng != null && pickupLatLng.latitude != 0.0 && pickupLatLng.longitude != 0.0) {
                            if (p_marker != null) p_marker.remove();
                            p_marker = map.addMarker(new MarkerOptions().position(new LatLng(pickupLatLng.latitude, pickupLatLng.longitude)).title("" + DriverNC.getResources().getString(R.string.pickuploc)).icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_flag_green)).draggable(false));
                        }
                        if (!MainActivityDriver.mMyStatus.getPassengerOndropLocation().equals("")) {
                            final String drop = MainActivityDriver.mMyStatus.getPassengerOndropLocation();

                            if (!drop.trim().equals("")) {
                                dropVisible();
                                // droplocationTxt.setText(Html.fromHtml(drop));
                                txt_drop.setText(Html.fromHtml(drop));
                            }

                            navigator_layout.setVisibility(View.VISIBLE);
                        } else {
                            droplocationTxt.setVisibility(View.GONE);
                        }


                        JSONArray stops = null;
                        if (json.getJSONObject("detail").has("stops"))
                            stops = json.getJSONObject("detail").getJSONArray("stops");

                        if (stops != null && stops.length() > 0)
                            stopLists = parseStop(stops.toString());
                        else
                            stopLists = createPickAndStopView(p_pickloc, p_latitude.toString(), p_longtitude.toString(), p_droploc, dropLattitue, dropLongitute);

                        setStopAdapter();
                        if (json.getJSONObject("detail").has("route_path")) {
                            mroute = json.getJSONObject("detail").getString("route_path");
                        }


                        new GetPickdropLoc().execute();
                    } else if (json.getInt("status") == -1) {
                        final Intent jobintent = new Intent(DriverOngoingAct.this, DriverTripHistoryAct.class);
                        Bundle extras = new Bundle();
                        extras.putString("alert_message", json.getString("message"));
                        jobintent.putExtras(extras);
                        startActivity(jobintent);
                        finish();
                    }
                } else {
                    runOnUiThread(() -> ShowToast(DriverOngoingAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Used to call the driver_fare_update API and parse the response.
     */
    private class FreeUpdate implements DriverAPIResult {
        public FreeUpdate(final String url) {

            try {
                if (isOnline()) {
                    butt_onboard.setEnabled(false);
                    new DriverAPIService_Retrofit_JSON(DriverOngoingAct.this, this, "", true).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
                }
            } catch (Exception e) {
                butt_onboard.setEnabled(true);
                e.printStackTrace();
            }
        }

        /**
         * Parse the response and update the UI.
         */
        @Override
        public void getResult(final boolean isSuccess, final String result) {
            butt_onboard.setEnabled(true);
            try {
                if (isSuccess) {
                    DriverSessionSave.saveSession("Ongoing", "farecal", DriverOngoingAct.this);
                } else {
                    runOnUiThread(() -> ShowToast(DriverOngoingAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Used to call the complete_trip API and parse the response.
     */
    private class CompleteTrip implements DriverAPIResult {
        public CompleteTrip(final String url, final Double latitude, final Double longitude) {
            DriverSystems.out.println("distanceeeeee " + stopLists.size() + "____" + DriverSessionSave.getGoogleDistance(DriverOngoingAct.this));
            try {
                final JSONObject j = new JSONObject();
                DriverCommonData.current_trip_accept = 0;

                if (stopLists.size() >= 2)
                    stopLists.set(stopLists.size() - 1, new DriverStopData((int) (new Date().getTime()), latitude1, longitude1, DriverSessionSave.getSession("drop_location", DriverOngoingAct.this).replaceAll("\n", " "), "", ""));
                else
                    stopLists.add(new DriverStopData((int) (new Date().getTime()), latitude1, longitude1, DriverSessionSave.getSession("drop_location", DriverOngoingAct.this).replaceAll("\n", " "), "", ""));
                j.put("trip_id", DriverSessionSave.getSession("trip_id", DriverOngoingAct.this));
                j.put("drop_latitude", Double.toString(latitude1));
                j.put("drop_longitude", Double.toString(longitude1));
                j.put("drop_location", DriverSessionSave.getSession("drop_location", DriverOngoingAct.this).replaceAll("\n", " "));
                DriverSystems.out.println("Nandhini Distance Calculation setGoogleDistance --- 1" + DriverSessionSave.getDistance(DriverOngoingAct.this) + "___" + DriverSessionSave.getGoogleDistance(DriverOngoingAct.this) + "______" + (DriverSessionSave.getDistance(DriverOngoingAct.this) + DriverSessionSave.getGoogleDistance(DriverOngoingAct.this)));
                j.put("distance", DriverSessionSave.getDistance(DriverOngoingAct.this) + DriverSessionSave.getGoogleDistance(DriverOngoingAct.this));
                j.put("actual_distance", DriverSessionSave.getDistance(DriverOngoingAct.this) + DriverSessionSave.getGoogleDistance(DriverOngoingAct.this));
                j.put("waiting_hour", DriverSessionSave.getSession("waitingHr", DriverOngoingAct.this));
                j.put("waypoints", DriverSessionSave.ReadGoogleWaypoints(DriverOngoingAct.this));
                String curVersion = BuildConfig.VERSION_NAME;
                j.put("driver_app_version", curVersion);
                j.put("new_distance", localDistance);
                j.put("stops", new JSONArray(new Gson().toJson(stopLists)));
                boolean distanceCalcInprogress = false;

               /* JSONArray wayData = SessionSave.ReadGoogleWaypoints(OngoingAct.this);
                Systems.out.println("WayDistance**" + j);
                try {
                    for (int i = 0; i < wayData.length(); i++) {
                        WayPointsData wayPointsData = new Gson().fromJson(wayData.get(i).toString(), WayPointsData.class);
                        if (wayPointsData.getDist() == 0.0)
                            distanceCalcInprogress = true;
                        Systems.out.println("WayDistance" + wayPointsData.getDist());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                if (isOnline()) {
                    butt_onboard.setEnabled(false);
                    new DriverAPIService_Retrofit_JSON(DriverOngoingAct.this, this, j, false).execute(url);
                }
            } catch (Exception e) {
                butt_onboard.setEnabled(true);
                e.printStackTrace();
            }
        }

        /**
         * Parse the response and update the UI.
         */
        @Override
        public void getResult(final boolean isSuccess, final String result) {
            cancelLoading();
            butt_onboard.setEnabled(true);
            if (dialog1 != null) {
                Driver_Utils.closeDialog(dialog1);
            }
            try {
                if (isSuccess) {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 4) {
                        DriverSessionSave.saveSession(DriverCommonData.WAITING_TIME, false, DriverOngoingAct.this);
                        MainActivityDriver.mMyStatus.setOnstatus("");

                        MainActivityDriver.mMyStatus.setOnPassengerImage("");
                        MainActivityDriver.mMyStatus.setOnstatus("Complete");
                        MainActivityDriver.mMyStatus.setOnpassengerName("");
                        MainActivityDriver.mMyStatus.setOnpickupLatitude("");
                        MainActivityDriver.mMyStatus.setOnpickupLongitude("");
                        MainActivityDriver.mMyStatus.setOndropLatitude("");
                        MainActivityDriver.mMyStatus.setOndropLongitude("");
                        DriverSessionSave.saveSession("Ongoing", "farecal", DriverOngoingAct.this);
                        DriverSessionSave.saveSession("travel_status", "5", DriverOngoingAct.this);
                        if (json.getJSONObject("detail").has("model_fare_type")) {
                            DriverSessionSave.saveSession("model_fare_type", json.getJSONObject("detail").getString("model_fare_type"), DriverOngoingAct.this);
                        }
                        waitingTimeTxt.setText("" + String.format(Locale.UK, DriverNC.getResources().getString(R.string.m_timer)));
                        DriverSessionSave.saveSession("speedwaiting", "", DriverOngoingAct.this);
                        DriverSessionSave.setWaitingTime(0L, DriverOngoingAct.this);
//                        Intent i = new Intent(OngoingAct.this, WaitingTimerRun.class);
//                        stopService(i);
//                        myHandler.removeCallbacks(r);
                        MainActivityDriver.mMyStatus.setsaveTime(timeclear);
                        showLoading(DriverOngoingAct.this);
                        if (DriverSessionSave.getSession(DriverCommonData.IS_CORPORATE_BOOKING, DriverOngoingAct.this).equals("1")) {
                            setFareCalculatorScreen(result);
                        } else {
                            System.out.println("FarecalcAct ___" + DriverSessionSave.getSession(DriverCommonData.IS_CORPORATE_BOOKING, DriverOngoingAct.this));
                            final Intent farecal = new Intent(DriverOngoingAct.this, DriverFarecalcAct.class);
                            farecal.putExtra("from", "direct");
                            farecal.putExtra("message", result);
                            farecal.putExtra("corporate", DriverSessionSave.getSession(DriverCommonData.IS_CORPORATE_BOOKING, DriverOngoingAct.this));
                            startActivity(farecal);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    } else if (json.getInt("status") == -1) {
                        MainActivityDriver.mMyStatus.setOnstatus("");
                        MainActivityDriver.mMyStatus.setOnPassengerImage("");
                        MainActivityDriver.mMyStatus.setOnstatus("Complete");
                        MainActivityDriver.mMyStatus.setOnpassengerName("");
                        MainActivityDriver.mMyStatus.setOndropLocation("");
                        MainActivityDriver.mMyStatus.setOnpickupLatitude("");
                        MainActivityDriver.mMyStatus.setOnpickupLongitude("");
                        MainActivityDriver.mMyStatus.setOndropLatitude("");
                        MainActivityDriver.mMyStatus.setOndropLongitude("");
                        MainActivityDriver.mMyStatus.setOndriverLatitude("");
                        MainActivityDriver.mMyStatus.setOndriverLongitude("");
                        DriverSessionSave.saveSession("status", "F", DriverOngoingAct.this);
                        DriverSessionSave.saveSession("trip_id", "", DriverOngoingAct.this);
                        final String status_update = "type=driver_status_update&driver_id=" + DriverSessionSave.getSession("Id", DriverOngoingAct.this) + "&latitude=" + latitude1 + "&longitude=" + longitude1 + "&status=" + "F" + "&trip_id=";
                        DriverSessionSave.saveSession("Ongoing", "flagger", DriverOngoingAct.this);
                        new FreeUpdate(status_update);
                        showLoading(DriverOngoingAct.this);
                        final Intent jobintent = new Intent(DriverOngoingAct.this, DriverMyStatus.class);
                        Bundle extras = new Bundle();
                        extras.putString("alert_message", json.getString("message"));
                        jobintent.putExtras(extras);
                        startActivity(jobintent);
                        finish();
                    } else {
                        DriverCToast.ShowToast(DriverOngoingAct.this, "" + json.getString("message"));
//                        dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + json.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
                    }
                } else {
                    runOnUiThread(() -> ShowToast(DriverOngoingAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * This for update the fare calculator page with API result.
     *
     * @param result
     */
    @SuppressLint("SdCardPath")
    private void setFareCalculatorScreen(String result) {
        // Need to uncommand
        if (result != null) {
            try {
                JSONObject obj = new JSONObject(result);
                JSONObject json = obj.getJSONObject("detail");
                trip_type = json.getString("trip_type");
                if (json.has("promo_type")) promo_type = json.getString("promo_type");
                if (json.has("existing_wallet_amount")) {
                    existing_wallet_amount = json.getString("existing_wallet_amount");
                }
                if (json.has("distance_fare")) {
                    distanceFare = json.getString("distance_fare");
                }

                os_distance = json.getDouble("distance");
                if (json.has("os_duration")) os_duration = (json.getDouble("os_duration") / 60);
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

                f_metric = json.getString("metric");
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
//
//
//                fare_per_minute = json.getString("fare_per_minute");
//                waiting_fare_minutes = json.getString("waiting_fare_minutes");
//                trip_minutes = json.getString("trip_minutes");
//                min_distance_status = json.getInt("min_distance_status");
//                subtotal = json.getString("subtotal");
//                new_distance_fare = json.getString("new_distance_fare");
//                new_base_fare = json.getString("new_base_fare");
//                distance_fare_metric = json.getString("distance_fare_metric");
//                amt = json.getString("amt");
//                promocode_fare = json.getString("promocode_fare");
//                tax_fare = json.getString("tax_fare");
//                nightfare = json.getString("nightfare");
//                eveningfare = json.getString("eveningfare");

//                if (json.has("pending_cancel_amount"))
//                    cancellation_fee = json.getString("pending_cancel_amount");


                if (json.has("pending_cancel_amount"))
                    pending_cancel_amount = json.getString("pending_cancel_amount");
                fare_calculation_type = json.getString("fare_calculation_type");
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
                p_dis = String.valueOf(promo_percentage);

                f_fare = m_totalfare;
//                if (tipsTxt.length() != 0) {
//                    f_tips = Double.parseDouble(Uri.decode(tipsTxt.getText().toString()));
//                }
//                f_total = f_fare + f_tips;
                JSONArray ary = new JSONArray(json.getString("gateway_details"));
                int length = ary.length();
                if (trip_type.equals("3")) {
                    calculateAndUpdateFare();
                }
                callurl();
            } catch (JSONException e) {
                DriverSystems.out.println("errorToCovert " + e);
                e.printStackTrace();
            }
        }
    }


    public void calculateAndUpdateFare() {
        double discount_amount = 0.0;
        os_fare = os_plan_fare;
//
//        if (!et_time_hour.getText().toString().isEmpty() && !et_time_mins.getText().toString().isEmpty() && !et_total_disatnce.getText().toString().isEmpty() && !p_dis.isEmpty()) {
//            os_duration = Double.parseDouble(et_time_hour.getText().toString()) + (Double.parseDouble(et_time_mins.getText().toString()) / 60);
//            if (os_duration > os_plan_duration) {
//                os_minute_fare = ((os_duration - (os_plan_duration)) * os_additional_fare_per_hour);
//                os_fare += os_minute_fare;
//            }
//            os_distance = Double.parseDouble(et_total_disatnce.getText().toString());
//            if (os_distance > os_plan_distance) {
//                os_fare += ((os_distance - os_plan_distance) * os_additional_fare_per_distance);
//            }
//
//            if (promo_percentage > 0 && !promo_type.equals("1")) {
//                discount_amount = os_fare * promo_percentage / 100;
//                os_fare -= discount_amount;
//            } else if (promo_type.equals("1")) {
//                discount_amount = Double.parseDouble(f_farediscount);
//                os_fare -= discount_amount;
//            }
//            if (os_tax != 0) {
//                tax = os_fare * os_tax / 100;
//            }
//
//        }
    }

    private void callurl() {

        String url = "type=tripfare_update";
        try {
            JSONObject j = new JSONObject();
//            if (trip_type.equals("3")) {
//                j.put("os_distance", os_distance);
//                j.put("os_actual_amount", "" + amountpayTxt.getText().toString());
//                j.put("os_trip_fare", df.format((os_fare)));
//                j.put("os_promodiscount_amount", b_discount.getText().toString());
//                j.put("os_minutes_traveled", (os_duration * 60));
//                j.put("os_minutes_fare", os_minute_fare);
//            }
            j.put("distance", f_distance);
            j.put("actual_amount", "" + f_total);
            j.put("trip_fare", f_tripfare);
            j.put("promodiscount_amount", f_farediscount);
            j.put("fare", "" + f_payamt);
            j.put("amount_tobe_paid", amount_tobe_paid);
            j.put("amount_used_from_wallet", amount_used_from_wallet);

            j.put("trip_type", trip_type);
            j.put("trip_id", f_tripid);

            j.put("distance_fare", distanceFare);

            j.put("actual_distance", f_distance);

            j.put("base_fare", base_fare);

            j.put("tips", ""/* + tipsTxt.getText().toString()*/);
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
            j.put("pay_mod_id", "7");
            j.put("passenger_discount", p_dis);
            j.put("minutes_traveled", f_minutes_traveled);
            j.put("minutes_fare", f_minutes_fare);
            j.put("fare_calculation_type", fare_calculation_type);
            j.put("model_fare_type", DriverSessionSave.getSession("model_fare_type", DriverOngoingAct.this));
            j.put("pending_cancel_amount", pending_cancel_amount);
            new FareUpdate(url, j);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    /**
     * This class helps to call the Fare Update API,get the result and parse it.
     */
    private class FareUpdate implements DriverAPIResult {
        String msg = "";

        public FareUpdate(String url, JSONObject data) {

            if (isOnline()) {
                new DriverAPIService_Retrofit_JSON(DriverOngoingAct.this, this, data, false).execute(url);
            } else {
                DriverCToast.ShowToast(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.check_internet));
//                dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "", "" + DriverNC.getResources().getString(R.string.check_internet), DriverNC.getResources().getString(R.string.ok),
//                        "", true, DriverOngoingAct.this, "");

            }
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            DriverSessionSave.saveSession(DriverCommonData.AMOUNT_USED_FROM_WALLET, "", DriverOngoingAct.this);
            if (isSuccess) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        LocationUpdate.ClearSessionwithTrip(getApplicationContext());
                        DriverSessionSave.saveSession("travel_status", "", DriverOngoingAct.this);
                        DriverSessionSave.saveSession("trip_id", "", DriverOngoingAct.this);
                        DriverSessionSave.saveSession("status", "F", DriverOngoingAct.this);
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
                        DriverSessionSave.saveSession("driver_statistics", "" + jsonDriver, DriverOngoingAct.this);
                        LocationUpdate.sTimer = "00:00:00";
                        LocationUpdate.finalTime = 0L;
                        LocationUpdate.timeInMillies = 0L;
                        DriverSessionSave.saveSession("waitingHr", "", DriverOngoingAct.this);
                        DriverCommonData.travel_km = 0;
                        DriverSessionSave.setGoogleDistance(0f, DriverOngoingAct.this);
                        DriverSessionSave.setDistance(0f, DriverOngoingAct.this);
                        DriverSessionSave.saveGoogleWaypoints(null, null, "", 0.0, "", DriverOngoingAct.this);
                        DriverSessionSave.saveWaypoints(null, null, "", 0.0, "", DriverOngoingAct.this);
                        Intent jobintent = new Intent(DriverOngoingAct.this, DriverJobdoneAct.class);
                        Bundle bun = new Bundle();
                        bun.putString("message", result);
                        jobintent.putExtras(bun);
                        startActivity(jobintent);
                        finish();
                    } else if (json.getInt("status") == -9) {
                        msg = json.getString("message");
//                        lay_fare.setVisibility(View.VISIBLE);

                        DriverCToast.ShowToast(DriverOngoingAct.this, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "", "" + msg, DriverNC.getResources().getString(R.string.ok),
//                                "", true, DriverOngoingAct.this, "");


                    } else if (json.getInt("status") == 0) {
                        msg = json.getString("message");
//                        lay_fare.setVisibility(View.VISIBLE);
                        DriverCToast.ShowToast(DriverOngoingAct.this, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "", "" + msg, DriverNC.getResources().getString(R.string.ok),
//                                "", true, DriverOngoingAct.this, "");


                    } else if (json.getInt("status") == -1) {
                        msg = json.getString("message");

                        DriverCToast.ShowToast(DriverOngoingAct.this, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "", "" + msg, DriverNC.getResources().getString(R.string.ok),
//                                "", true, DriverOngoingAct.this, "");

                        if (json.has("driver_statistics")) {
                            DriverSessionSave.saveSession("trip_id", "", DriverOngoingAct.this);
                            DriverSessionSave.saveSession("status", "F", DriverOngoingAct.this);
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
                            DriverSessionSave.saveSession("driver_statistics", "" + jsonDriver, DriverOngoingAct.this);
                        }
                        Intent intent = new Intent(DriverOngoingAct.this, DriverMyStatus.class);
                        startActivity(intent);
                        finish();
                    } else {
//                        lay_fare.setVisibility(View.VISIBLE);

                        DriverCToast.ShowToast(DriverOngoingAct.this, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "", "" + msg, DriverNC.getResources().getString(R.string.ok),
//                                "", true, DriverOngoingAct.this, "");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(() -> DriverCToast.ShowToast(DriverOngoingAct.this, DriverNC.getString(R.string.server_error)));
//                lay_fare.setVisibility(View.VISIBLE);
            }
        }


    }

    /**
     * Used to call the cancel_trip API and parse the response.
     */
    private class CancelTrip implements DriverAPIResult {
        private String msg;

        public CancelTrip(final String url, JSONObject data) {

            try {
                if (isOnline()) {
                    butt_onboard.setEnabled(false);
                    new DriverAPIService_Retrofit_JSON(DriverOngoingAct.this, this, data, false).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
                }
            } catch (Exception e) {
                butt_onboard.setEnabled(true);
                e.printStackTrace();
            }
        }

        /**
         * Parse the response and update the UI.
         */
        @Override
        public void getResult(final boolean isSuccess, final String result) {
            butt_onboard.setEnabled(true);
            try {
                if (isSuccess) {
                    final JSONObject json = new JSONObject(result);
                    DriverCommonData.current_trip_accept = 0;

                    if (json.getInt("status") == 3 || json.getInt("status") == 7) {
                        msg = json.getString("message");
                        JSONObject jsonDriver = json.getJSONObject("driver_statistics");
                        DriverSessionSave.saveSession("driver_statistics", "" + jsonDriver, DriverOngoingAct.this);
                        DriverSessionSave.saveSession("status", "F", DriverOngoingAct.this);
                        MainActivityDriver.mMyStatus.settripId("");
                        DriverSessionSave.saveSession("trip_id", "", DriverOngoingAct.this);
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
                        DriverSessionSave.saveSession("Ongoing", "farecal", DriverOngoingAct.this);

                        Intent cancelIntent = new Intent();
                        cancelIntent.putExtra("alert_message", json.getString("message"));
                        cancelIntent.setAction(Intent.ACTION_MAIN);
                        cancelIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        cancelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        ComponentName cn = new ComponentName(DriverOngoingAct.this, DriverMyStatus.class);
                        cancelIntent.setComponent(cn);

                        startActivity(cancelIntent);
                        finish();
                    } else {
                        msg = json.getString("message");
                        DriverCToast.ShowToast(DriverOngoingAct.this, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
                    }
                } else {
                    runOnUiThread(() -> ShowToast(DriverOngoingAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This class is used to get the trip details when activity in opened, It calls the API and parse the response.
     */
    private class Tripdetails implements DriverAPIResult {
        String p_logid = "";
        String p_name = "";
        String p_pickloc = "";
        String p_droploc = "";
        String p_picklat = "";
        String p_picklng = "";
        String p_droplat = "";
        String p_droplng = "";
        String p_driverlat = "";
        String p_driverlng = "";

        private String p_image = "";
        private String d_image_name = "";
        private String p_phone = "";
        private String p_notes = "";
        private String p_driverstatus = "", p_taxi_speed = "";


        public Tripdetails(final String url, JSONObject data) {

            try {
                if (isOnline()) {
                    butt_onboard.setEnabled(false);
                    new DriverAPIService_Retrofit_JSON(DriverOngoingAct.this, this, data, false).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
                }
            } catch (Exception e) {
                butt_onboard.setEnabled(true);
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            butt_onboard.setEnabled(true);
            try {
                if (isSuccess) {
                    tripInfo.setVisibility(View.GONE);//hide
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        final JSONObject detail = json.getJSONObject("detail");

                        if (detail.getString("street_pickup_trip").trim().equals("1")) {
                            startActivity(new Intent(DriverOngoingAct.this, DriverStreetPickUpAct.class));
                            Toast.makeText(DriverOngoingAct.this, DriverNC.getString(R.string.you_are_in_trip), Toast.LENGTH_LONG).show();
                            finish();
                        } else {

                            speedTxt.setText("" + String.format(Locale.UK, "%.2f", LocationUpdate.speed) + "" + metricss.toLowerCase());
                            waitingTimeTxt.setText(String.format(Locale.UK, DriverCommonData.getDateForWaitingTime(DriverSessionSave.getWaitingTime(DriverOngoingAct.this))));

                            p_logid = detail.getString("trip_id");
                            p_name = detail.getString("passenger_name");
                            DriverSessionSave.saveSession("passenger_name", p_name, DriverOngoingAct.this);
                            p_pickloc = detail.getString("current_location");
                            p_droploc = detail.getString("drop_location");
                            p_picklat = detail.getString("pickup_latitude");
                            p_picklng = detail.getString("pickup_longitude");
                            p_droplat = detail.getString("drop_latitude");
                            p_droplng = detail.getString("drop_longitude");
                            p_driverlat = detail.getString("driver_latitute");
                            p_driverlng = detail.getString("driver_longtitute");
                            p_travelstatus = detail.getString("travel_status");
                            p_driverstatus = detail.getString("driver_status");
                            p_notes = detail.getString("notes");
                            p_phone = detail.getString("passenger_phone");
                            p_image = detail.getString("passenger_image");
                            d_image_name = detail.getString("d_image_name");
                            p_taxi_speed = detail.getString("taxi_min_speed");

                            if (detail.getString("service_id").equals("2")) {
                                order_details.setVisibility(View.VISIBLE);
                                product_name = detail.has("product_name") ? detail.getString("product_name") : "";
                                product_weight = detail.has("product_weight") ? detail.getString("product_weight") : "";
                                product_size = detail.has("product_size") ? detail.getString("product_size") : "";
                                delivery_person_name = detail.has("delivery_person_name") ? detail.getString("delivery_person_name") : "";
                                delivery_phone_number = detail.has("delivery_phone_number") ? detail.getString("delivery_phone_number") : "";
                                delivery_date_time = detail.has("delivery_date_time") ? detail.getString("delivery_date_time") : "";
                                delivery_notes = detail.has("delivery_notes") ? detail.getString("delivery_notes") : "";
                            } else {
                                order_details.setVisibility(View.GONE);
                            }

                            DriverSessionSave.saveSession("status", detail.getString("driver_status"), DriverOngoingAct.this);
                            DriverSessionSave.saveSession("Metric", detail.getString("metric"), DriverOngoingAct.this);
                            DriverSessionSave.saveSession("p_image", p_image, DriverOngoingAct.this);
                            DriverSessionSave.saveSession("d_image_name", d_image_name, DriverOngoingAct.this);
                            DriverSessionSave.saveSession("c", p_travelstatus, DriverOngoingAct.this);
                            if (detail.has(DriverCommonData.IS_CORPORATE_BOOKING)) {
                                DriverSessionSave.saveSession(DriverCommonData.IS_CORPORATE_BOOKING, detail.getString("corporate_booking"), DriverOngoingAct.this);
                            }

                            if (json.getJSONObject("detail").has("route_path"))
                                mroute = json.getJSONObject("detail").getString("route_path");

                            if (json.getJSONObject("detail").has("stops"))
                                stops = json.getJSONObject("detail").getJSONArray("stops");

                            if (stops != null && stops.length() > 0)
                                stopLists = parseStop(stops.toString());
                            else
                                stopLists = createPickAndStopView(p_pickloc, p_picklat, p_picklng, p_droploc, p_droplat, p_droplng);

                            if (json.getJSONObject("detail").has("manual_waiting_time")) {
                                DriverSessionSave.saveSession(DriverCommonData.WAITING_TIME_MANUAL, json.getJSONObject("detail").getString("manual_waiting_time").equals("1"), DriverOngoingAct.this);
                            }
                            if (DriverSessionSave.getSession(DriverCommonData.WAITING_TIME_MANUAL, DriverOngoingAct.this, false)) {
                                ssWaitingTime_img.setVisibility(View.VISIBLE);
                            } else {
                                ssWaitingTime_img.setVisibility(View.GONE);
                            }

                            if (p_travelstatus.equalsIgnoreCase("2")) {
                                setStopAdapter();
                            }

                            DriverSystems.out.println("statusss" + p_driverstatus + "__" + p_travelstatus + "___" + DriverSessionSave.getSession(DriverCommonData.IS_CORPORATE_BOOKING, DriverOngoingAct.this));
                            if ((p_driverstatus.equalsIgnoreCase("F") || p_driverstatus.equalsIgnoreCase("B") || (p_driverstatus.equalsIgnoreCase("A"))) && !p_travelstatus.equalsIgnoreCase("5")) {
                                if (p_travelstatus.equalsIgnoreCase("3")) {
                                    HeadTitle.setText(DriverNC.getResources().getString(R.string.waitingpassenger));
                                    view_line_trip.setVisibility(View.GONE);
                                    MainActivityDriver.mMyStatus.setOnstatus("Arrivd");
                                    setDelayForCancel();
                                } else if (p_travelstatus.equalsIgnoreCase("2")) {
                                    card_view_pickup.setCardElevation(0);
                                    card_view_pickup.setUseCompatPadding(false);
                                    card_view_pickup.setRadius(0);
                                    view_line_trip.setVisibility(View.GONE);
                                    HeadTitle.setText(DriverNC.getResources().getString(R.string.tripprogress));
                                    //tripinprogress_lay.setVisibility(View.VISIBLE); //hide
                                    pickup_drop_lay.setVisibility(View.GONE);
                                    contact_lay.setVisibility(View.GONE);
                                    contact_txt.setVisibility(View.VISIBLE);
                                    tripDetails_lay.setBackgroundColor(getResources().getColor(R.color.white));
                                    trip_view.setVisibility(View.VISIBLE);
                                    MainActivityDriver.mMyStatus.setOnstatus("Complete");

                                } else if (p_travelstatus.equalsIgnoreCase("9")) {
                                    view_line_trip.setVisibility(View.GONE);
                                    HeadTitle.setText(DriverNC.getResources().getString(R.string.tripdetails));
                                    MainActivityDriver.mMyStatus.setOnstatus("On");
                                } else {
                                    HeadTitle.setText(DriverNC.getResources().getString(R.string.tripdetails));
                                }
                                p_pickloc = p_pickloc.trim();
                                if (p_pickloc.length() > 0 && DriverSessionSave.getSession("Lang", DriverOngoingAct.this).equals("en")) {
                                    p_pickloc = Character.toUpperCase(p_pickloc.charAt(0)) + p_pickloc.substring(1);
                                    p_droploc = p_droploc.trim();
                                }
                                if (p_droploc.length() > 0) {
                                    p_droploc = Character.toUpperCase(p_droploc.charAt(0)) + p_droploc.substring(1);
                                }
                                if (p_name.length() > 0) {
                                    p_name = Character.toUpperCase(p_name.charAt(0)) + p_name.substring(1);
                                }
                                if (p_taxi_speed != null && p_taxi_speed.length() > 0) {
                                    DriverSessionSave.saveSession("taxi_speed", p_taxi_speed, DriverOngoingAct.this);
                                }
                                if (p_notes.length() > 0) {
                                    p_notes = Character.toUpperCase(p_notes.charAt(0)) + p_notes.substring(1);
                                }

                                addonsData.clear();
                                if (detail.has("preferences") && !TextUtils.isEmpty(detail.getString("preferences")) && detail.getJSONArray("preferences").length() > 0) {
                                    JSONArray jsonArray = detail.getJSONArray("preferences");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        AddonsData data = new AddonsData();
                                        data.preference_id = jsonArray.getJSONObject(i).getInt("preference_id");
                                        data.preference_name = jsonArray.getJSONObject(i).getString("preference_name");
                                        data.preference_fare = jsonArray.getJSONObject(i).getString("preference_fare");
                                        addonsData.add(data);
                                    }
                                    haspreference = true;
                                    preference.setVisibility(View.VISIBLE);
                                } else {
                                    haspreference = false;
                                    preference.setVisibility(View.GONE);
                                }


                                MainActivityDriver.mMyStatus.setOnpickupLocation(p_pickloc);
                                MainActivityDriver.mMyStatus.setOndropLocation(p_droploc);
                                MainActivityDriver.mMyStatus.setPassengerOndropLocation(p_droploc);
                                MainActivityDriver.mMyStatus.setOnpickupLatitude(p_picklat);
                                MainActivityDriver.mMyStatus.setOnpickupLongitude(p_picklng);
                                MainActivityDriver.mMyStatus.setOndriverLatitude(p_driverlat);
                                MainActivityDriver.mMyStatus.setOndriverLongitude(p_driverlng);
                                MainActivityDriver.mMyStatus.setOnpassengerName(p_name);
                                MainActivityDriver.mMyStatus.settripId(p_logid);
                                DriverSessionSave.saveSession("trip_id", p_logid, DriverOngoingAct.this);
                                MainActivityDriver.mMyStatus.setpickupLoc(p_pickloc);
                                MainActivityDriver.mMyStatus.setOndropLatitude(p_droplat);
                                MainActivityDriver.mMyStatus.setOndropLongitude(p_droplng);
                                MainActivityDriver.mMyStatus.setdropLoc(p_droploc);
                                MainActivityDriver.mMyStatus.setpassengerId(p_logid);
                                MainActivityDriver.mMyStatus.setphoneNo(p_phone);
                                MainActivityDriver.mMyStatus.setOnPassengerImage(p_image);
                                MainActivityDriver.mMyStatus.setpassengerNotes(p_notes);
                                MainActivityDriver.mMyStatus.setpassengerphone(p_phone);
                                init();
                                String imagepath;
                                if (!DriverSessionSave.getSession("p_image", DriverOngoingAct.this).equals("")) {
                                    imagepath = "" + DriverSessionSave.getSession("p_image", DriverOngoingAct.this);
                                    Log.i("Imagepath in session", DriverSessionSave.getSession("p_image", DriverOngoingAct.this));
//                                }
//                                else
//                                    imagepath = DriverSessionSave.getSession("noimage_base", DriverOngoingAct.this);
                                    //   Picasso.get().load(imagepath).placeholder(getResources().getDrawable(R.drawable.driver_loadingimage)).error(getResources().getDrawable(R.drawable.driver_noimage)).into(proimg);

                                    if (imagepath != null && imagepath.length() > 0) {
                                        Picasso.get().load(imagepath).error(R.drawable.loadingimage).placeholder(R.drawable.loadingimage).into(proimg);
                                    } else {
                                        if (!p_name.equals("")) {
                                            ProfileImageSetupClass.setupProfileImage(p_name, proimg);
                                        } else {
                                            Picasso.get().load(R.drawable.loadingimage).into(proimg);
                                        }
                                    }
                                }

                            } else if (p_driverstatus.equalsIgnoreCase("A") && p_travelstatus.equalsIgnoreCase("5")) {
                                System.out.println("FarecalcAct ___Tripdetail__" + DriverSessionSave.getSession(DriverCommonData.IS_CORPORATE_BOOKING, DriverOngoingAct.this));

                                if (DriverSessionSave.getSession(DriverCommonData.IS_CORPORATE_BOOKING, DriverOngoingAct.this).equals("1")) {
//                                    setFareCalculatorScreen(result);
                                    CompleteSuccessClick();
                                } else {
                                    Intent i = new Intent(DriverOngoingAct.this, DriverFarecalcAct.class);
                                    i.putExtra("from", "pending");
                                    i.putExtra("lat", detail.getString("drop_latitude"));
                                    i.putExtra("lon", detail.getString("drop_longitude"));
                                    i.putExtra("distance", detail.getString("distance"));
                                    i.putExtra("waitingHr", detail.getString("waiting_time"));
                                    i.putExtra("drop_location", detail.getString("drop_location"));
                                    i.putExtra("stopList", detail.getJSONArray("stops").toString());
                                    i.putExtra("corporate", DriverSessionSave.getSession(DriverCommonData.IS_CORPORATE_BOOKING, DriverOngoingAct.this));
                                    startActivity(i);
                                    overridePendingTransition(0, 0);
                                    finish();
                                }
                            } else {
                                DriverSystems.out.println("haiiiiiiiTriphistory" + p_driverstatus + "___" + p_travelstatus);
                                ShowToast(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.you_are_in_trip));
                                Intent i = new Intent(DriverOngoingAct.this, DriverTripHistoryAct.class);
                                startActivity(i);
                                finish();
                            }
                            tripInfo.post(() -> {

                                layoutheight = tripInfo.getHeight() - 20;
                                if (map != null) {
                                    map.setPadding(0, layoutheight, 0, 120);
                                }
                            });
                        }

                        if (status != null && status.equals("55")) {
                            Intent in = new Intent(DriverOngoingAct.this, DriverChatWebviewAct.class);
                            in.putExtra("type", "2");
                            in.putExtra("trip_id", DriverSessionSave.getSession("trip_id", DriverOngoingAct.this));
                            startActivity(in);
                        }
                        nodataTxt.setVisibility(View.GONE);
                    } else {

                        Intent i = new Intent(DriverOngoingAct.this, DriverTripHistoryAct.class);
                        startActivity(i);
                        finish();

                    }
                } else {

                    runOnUiThread(() -> ShowToast(DriverOngoingAct.this, DriverNC.getString(R.string.server_error)));
                    Intent i = new Intent(DriverOngoingAct.this, DriverTripHistoryAct.class);
                    startActivity(i);
                    finish();
                }
            } catch (final Exception e) {
                // TODO: handle exception
                DriverSystems.out.println("pass---j" + e);
                e.printStackTrace();
                Intent i = new Intent(DriverOngoingAct.this, DriverTripHistoryAct.class);
                startActivity(i);
                finish();
            }
        }
    }


    private class getMaskedPhoneNumber implements DriverAPIResult {


        public getMaskedPhoneNumber(final String url, JSONObject data) {
            try {
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverOngoingAct.this, this, data, false).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            try {
                if (isSuccess) {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        if (!TextUtils.isEmpty(json.getString("twilio_number"))) {
                            call_masking_ph_no = json.getString("twilio_number");
                            if (call_masking_ph_no.trim().length() == 0) {
                                DriverCToast.ShowToast(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.invalid_mobile_number));
//                                dialog1 = Driver_Utils.alert_view(DriverOngoingAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.invalid_mobile_number), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverOngoingAct.this, "4");
                            } else {
                                ensureCall();
                            }
                        }
                    } else {
                        if (json.has("message")) {
                            ShowToast(DriverOngoingAct.this, json.getString("message"));
                        }
                    }
                } else {
                    runOnUiThread(() -> ShowToast(DriverOngoingAct.this, DriverNC.getString(R.string.server_error)));

                }
            } catch (final Exception e) {
                // TODO: handle exception
                e.printStackTrace();

            }
        }
    }
}
