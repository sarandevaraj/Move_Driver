package com.taximobility.driver;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mayan.sospluginmodlue.SOSActivity;
import com.taximobility.ProfileImageSetupClass;
import com.taximobility.SplashActivity;
import com.taximobility.driver.fragments.DriverTripDetailNewFrag;
import com.taximobility.features.CToast;
import com.squareup.picasso.Picasso;
import com.taximobility.R;
import com.taximobility.driver.data.DriverCommonData;
import com.taximobility.driver.data.DriverMapWrapperLayout;
import com.taximobility.driver.earningchart.DriverEarningsAct;
import com.taximobility.driver.interfaces.DriverAPIResult;
import com.taximobility.driver.interfaces.DriverGetAddress;
import com.taximobility.driver.roomDB.GeocoderModel;
import com.taximobility.driver.service.DriverAPIService_Retrofit_JSON;
import com.taximobility.driver.service.DriverAPIService_Retrofit_JSON_NoProgress;
import com.taximobility.driver.service.LocationUpdate;
import com.taximobility.driver.service.DriverNonActivity;
import com.taximobility.driver.utils.DriverCToast;
import com.taximobility.driver.utils.DriverFontHelper;
import com.taximobility.driver.utils.DriverGetAddressFromLatLng;
import com.taximobility.driver.utils.DriverLatLngInterpolator;
import com.taximobility.driver.utils.DriverListViewEX;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.driver.utils.DriverSystems;
import com.taximobility.driver.utils.Driver_Utils;
import com.taximobility.interfaces.AlertListener;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.RoundedImageView;
import com.taximobility.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.taximobility.driver.service.DriverFirebaseService.BOOKLATER_NOTIFICATION_ID;
import static com.taximobility.driver.utils.DriverGpsStatus.ischecked;

/**
 * @author developer
 */

/**
 * This class is home page where you can select other menus from here
 */
public class DriverMyStatus extends MainActivityDriver implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener, OnMapReadyCallback, GoogleMap.OnCameraMoveStartedListener, DriverGetAddress {
    public static boolean GEOCODE_EXPIRY = false;
    // Class members declarations.
    public static GoogleMap googleMap;
    public static DriverMyStatus mystatus;
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 5000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 250; // 10 meters
    private final int REQUEST_READ_PHONE_STATE = 292;
    LatLng coordinate;
    // Animate marker
//    ArrayList<LatLng> listPoint = new ArrayList<LatLng>();
//    ArrayList<LatLng> savedpoint = new ArrayList<LatLng>();
    ArrayList<String> savedlocation = new ArrayList<>();
    ArrayList<LatLng> _trips = new ArrayList<>();
    Marker _marker;
    DriverLatLngInterpolator _latLngInterpolator = new DriverLatLngInterpolator.Spherical();
    TextView sendEmail;
    float bearing;
    LatLng savedLatLng = null;
    String checked = "OUT";
    DriverNonActivity nonactiityobj = new DriverNonActivity();
    Bitmap theBitmap = null;
    Bitmap bm = null;
    int height = 60;
    int width = 100;
    int templength = 0;
    private Marker c_marker;
    private TextView slider;
    private TextView currentLocation1, headerTxt;
    private TextView curlocation;
    private int mapstatus;
    private Button new_ride;
    private DrawerLayout drawerLayout;
    private LinearLayout nav_home_lay, menu_my_earnings_lay, menu_profile_lay, menu_street_pickup_lay, menu_me,
            menu_my_ride_lay, menu_settings_lay, menu_fleet_lay, menu_call_helpline, menu_chat_helpline;
    private DriverMapWrapperLayout mapWrapperLayout;
    private Location mLastLocation;
    private LatLng previousLatLong;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private boolean animStarted = false;
    private boolean animLocation = false;
    // private double speed = 0.0;
    private float zoom = 16f;
    private TextView menu_profile_name, menu_pno;
    private TextView Trip_history;
    private TextView third_tripamt, first_tripdate, first_tripmodel, first_tripamt, second_tripdate, second_tripmodel, second_tripamt, third_tripdate, third_tripmodel;
    private LinearLayout trip_detailslay;
    private LinearLayout first_lay, second_lay, third_lay;
    private LinearLayout home_lay, earnings_lay, profile_lay, streetpick_lay, menu_emergency_lay,menu_sos;
    private ImageView home_iv;
    private ImageButton navi_icon_diver;
    private TextView lasttripheader;
    private TextView no_taxi_assign, online_txt_new;
    private ImageView btn_shift;
    private LinearLayout offline_lay_bottom;
    private Bundle alert_bundle;
    private String alert_msg;
    private Dialog errorDialog;
    private int HighAccuracyCount = 0;
    private CountDownTimer countDownTimer;
    private boolean doubleBackToExitPressedOnce;
    private LinearLayout animlayout;
    private Dialog alertDialog;
    private FloatingActionButton mov_cur_loc;
    private ViewGroup coordinatorLayout;
    private LinearLayout no_taxi_view;
    private RelativeLayout slide_lay;
    private AsyncTask<String, String, GeocoderModel> getAddress;
    private AppCompatButton btn_emergency;
    private int shift_value = 0;
    private RoundedImageView menu_profile_img;


    Dialog dialog1;
    private Snackbar redAlertSnackBar;
    private ProgressBar progressBar;

    private static final int MY_PERMISSIONS_REQUEST_GPS = 111;

    String recentListMessage = "";


    private LinearLayout tapCurrentLocation;
    private LatLng mLastLocationTemp;


    private Boolean scheduleAlert = false;
    private String scheduleTripId = "";
    private Marker a_marker;

    /**
     * Get the google map pixels from xml density independent pixel.
     */
    public static int getPixelsFromDp(final Context context, final float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    BroadcastReceiver listener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean show_alert = intent.getBooleanExtra("show_alert", false);
            if (show_alert) {
                showRedAlert(DriverNC.getString(R.string.low_gps_alert_message));
            } else {
                hideRedAlert();
            }

        }
    };

    /**
     * Set the layout to activity.
     */
    @Override
    public int setLayout() {
        mapstatus = 0;
        setLocale();
        return R.layout.driver_mystatus_lay;
    }

    private String getAccuracyText() {
        if (HighAccuracyCount < 3)
            return "High Accuracy " + mLastLocation.getAccuracy();
        else if (HighAccuracyCount < 7)
            return "Low Accuracy " + mLastLocation.getAccuracy();
        return " no accuracy";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityDriver.context = this;
        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named LocationUpdate.LOCATION_ACCURACY_LOW.
        LocalBroadcastManager.getInstance(this).registerReceiver(listener,
                new IntentFilter(LocationUpdate.LOCATION_ACCURACY_LOW));
        DriverFontHelper.applyFont(this, findViewById(R.id.drawer_layout));
//        DriverFontHelper.applyFont(this, findViewById(R.id.nav_view));
        FontHelper.applyFont(this, findViewById(R.id.carlayout));

    }


    /**
     * Initialize the views on layout
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void Initialize() {
        DriverSystems.out.println("Mystatus Initialize");
        btn_emergency = findViewById(R.id.btn_emergency);
        btn_emergency.setVisibility(View.VISIBLE);
       /* if (SessionSave.getSession(CommonData.SOS_ENABLED, this, false)) {
            btn_emergency.setVisibility(View.VISIBLE);
        }*/

        btn_emergency.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.actionSheet(DriverMyStatus.this, DriverNC.getResources().getString(R.string.send_emergency_alert), DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), false, new AlertListener() {
                    @Override
                    public void onSuccess() {
                        startSOSService();
                    }
                    @Override
                    public void onFailure() {

                    }
                });
                /*
                final View view1 = View.inflate(DriverMyStatus.this, R.layout.driver_emergency_alert, null);
                Dialog emergency_dialog = new Dialog(DriverMyStatus.this, R.style.dialogwinddow);
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
                 */
                        /*if (ActivityCompat.checkSelfPermission(MyStatus.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                                ActivityCompat.checkSelfPermission(MyStatus.this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            emergency_dialog.dismiss();

                            dialog1 = Utils.alert_view_dialog(MyStatus.this, "", NC.getResources().getString(R.string.str_sms), NC.getResources().getString(R.string.yes), NC.getResources().getString(R.string.no), true, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    ActivityCompat.requestPermissions(MyStatus.this,
                                            new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE},
                                            REQUEST_READ_PHONE_STATE);
                                    dialog.dismiss();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.dismiss();
                                }
                            }, "");
                        } else {
                            emergency_dialog.dismiss();
                            startSOSService();
                        }*/
                /*
                    }
                });
                button_failure.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emergency_dialog.dismiss();
                    }
                });
                */
            }
        });
        animlayout = findViewById(R.id.lay_home);
        coordinatorLayout = findViewById(R.id.mystatus_layout);
        if (DriverSessionSave.getSession("need_animation", DriverMyStatus.this, false)) {
            AnimationInScreen();
        }
        Point pointSize = new Point();
        DriverSystems.out.println("__________hi" + pointSize.x);
        getWindowManager().getDefaultDisplay().getSize(pointSize);
        DriverSystems.out.println("__________bye" + pointSize.x);
        height = pointSize.x / 6;
        width = pointSize.x / 9;
        DriverCommonData.mActivitylist.add(this);
        DriverCommonData.sContext = this;
        mapstatus = 0;
        DriverCommonData.current_act = "MyStatus";

   /*     DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverMyStatus.this
                .findViewById(android.R.id.content)).getChildAt(0)), DriverMyStatus.this);*/

/*

        try {
            alert_bundle = getIntent().getExtras();
            if (alert_bundle != null) {
                alert_msg = alert_bundle.getString("alert_message");
                Systems.err.println("oncreate called....." + alert_msg);
            }
            if (alert_msg != null && alert_msg.length() != 0)
                dialog1 = Utils.alert_view(MyStatus.this, "" + NC.getResources().getString(R.string.message), "" + alert_msg, "" + NC.getResources().getString(R.string.ok), "", true, MyStatus.this, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
*/


        try {
            alert_bundle = getIntent().getExtras();
            if (alert_bundle != null) {
                alert_msg = alert_bundle.getString("alert_message");
                String alertSchedule = alert_bundle.getString("alert_schedule");
                if (alertSchedule != null && alertSchedule.equals("1")) {
                    scheduleAlert = true;
                }
            }
            if (scheduleAlert) {
                if (alert_msg != null && alert_msg.length() != 0) {
                    bookLaterNotificationAlert(alert_msg);
                }
            } else {
                if (alert_msg != null && alert_msg.length() != 0)
                    DriverCToast.ShowToast(DriverMyStatus.this, "" + alert_msg);
//                    dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.message), "" + alert_msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyStatus.this, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        DriverFontHelper.applyFont(this, findViewById(R.id.mystatus_layout));
        if (servicesConnected()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            mLocationRequest.setFastestInterval(FATEST_INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
            startLocationUpdates();
        }
        try {
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

            SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);
            //  googleMap = mapFrag.getMap();

            // Systems.gc();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
          /*  Intent intent = getIntent();
            finish();
            startActivity(intent);*/
        }
        mov_cur_loc = findViewById(R.id.mov_cur_loc);
//        Glide.with(this).load(SessionSave.getSession("image_path", this) + "currentLocation.png").placeholder(R.drawable.mapmove).error(R.drawable.mapmove).into((FloatingActionButton) findViewById(R.id.mov_cur_loc));
        mov_cur_loc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startLocationUpdates();
                    if (mLastLocation == null) {
                        if (ActivityCompat.checkSelfPermission(DriverMyStatus.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DriverMyStatus.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    }
                    if (mLastLocation != null) {
                        coordinate = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, zoom));
                        //mov_cur_loc.setVisibility(View.GONE);
                        DriverMapWrapperLayout.setmMapIsTouched(true);
                        if (mLastLocation != null) {
                            if (a_marker != null) {
                                a_marker.remove();
                            }
                            if (googleMap != null) {
                                a_marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())).rotation(0).anchor(0.5f, 0.5f).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_img)));
                            }

                        }
                    }
                    DriverSystems.out.println("__________________LLLLLLLppppm" + mLastLocation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        currentLocation1 = findViewById(R.id.currentlocation);
        currentLocation1.setSelected(true);
        new_ride = findViewById(R.id.new_ride);
        Trip_history = findViewById(R.id.trip_history_header);

        Trip_history.setText(/*Html.fromHtml("<u>" +*/ DriverNC.getResources().getString(R.string.trip_history) /*+ "</u>")*/);

        btn_shift = findViewById(R.id.btn_shift);
        offline_lay_bottom = findViewById(R.id.offline_lay_bottom);
        online_txt_new = findViewById(R.id.online_txt_new);
        navi_icon_diver = findViewById(R.id.navi_icon_diver);

        drawerLayout = findViewById(R.id.drawer_layout);
        nav_home_lay = findViewById(R.id.nav_home_lay);
        menu_my_ride_lay = findViewById(R.id.menu_my_ride_lay);
        menu_my_earnings_lay = findViewById(R.id.menu_my_earnings_lay);
        menu_profile_lay = findViewById(R.id.menu_profile_lay);
        menu_street_pickup_lay = findViewById(R.id.menu_street_pickup_lay);
        menu_settings_lay = findViewById(R.id.menu_settings_lay);
        menu_fleet_lay = findViewById(R.id.menu_fleet_lay);
        menu_call_helpline = findViewById(R.id.menu_call_helpline);
        menu_chat_helpline = findViewById(R.id.menu_chat_helpline);
        menu_me = findViewById(R.id.menu_me);
        menu_profile_name = findViewById(R.id.menu_profile_name);
        menu_pno = findViewById(R.id.menu_pno);
        menu_profile_img = findViewById(R.id.menu_profile_img);
        lasttripheader = findViewById(R.id.lasttrip_header);
        first_tripdate = findViewById(R.id.first_tripdate);
        first_tripmodel = findViewById(R.id.first_tripmodel);
        first_tripamt = findViewById(R.id.first_tripamt);
        second_tripdate = findViewById(R.id.second_tripdate);
        second_tripmodel = findViewById(R.id.second_tripmodel);
        second_tripamt = findViewById(R.id.second_tripamt);
        third_tripdate = findViewById(R.id.third_tripdate);
        third_tripmodel = findViewById(R.id.third_tripmodel);
        third_tripamt = findViewById(R.id.third_tripamt);
        trip_detailslay = findViewById(R.id.trip_detailslay);
        second_lay = findViewById(R.id.second_lay);
        first_lay = findViewById(R.id.firstlay);
        third_lay = findViewById(R.id.third_lay);
        slide_lay = findViewById(R.id.slide_lay);
        ImageView headerlogo = findViewById(R.id.headicon);
        headerlogo.setVisibility(View.VISIBLE);
        tapCurrentLocation = findViewById(R.id.tapCurrentLocation);

        Picasso.get().load(DriverSessionSave.getSession("Picture", DriverMyStatus.this)).into(menu_profile_img);
        menu_profile_name.setText(DriverSessionSave.getSession("Name", DriverMyStatus.this) + " " +
                DriverSessionSave.getSession("Lastname", DriverMyStatus.this));
        menu_pno.setText(DriverSessionSave.getSession("Phone", DriverMyStatus.this));

        if (DriverSessionSave.getSession(DriverCommonData.isNeedtofetchAddress, DriverMyStatus.this, false)) {
            currentLocation1.setVisibility(View.VISIBLE);
        } else {
            currentLocation1.setVisibility(View.GONE);
        }
        currentLocation1.setText(DriverNC.getString(R.string.tap_loc));

        first_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Trip_history.performClick();
            }
        });
        second_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Trip_history.performClick();
            }
        });
        third_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Trip_history.performClick();
            }
        });

        new_ride.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DriverSessionSave.getSession("shift_status", DriverMyStatus.this).equals("IN")) {

                    if (!DriverSessionSave.getSession("driver_type", DriverMyStatus.this).equalsIgnoreCase("D")) {
                        if (DriverSessionSave.getSession("trip_id", DriverMyStatus.this).equals("")) {
                            Intent intent = new Intent(DriverMyStatus.this, DriverStreetPickUpAct.class);
                            startActivity(intent);
//                        finish();
                        } else if (!DriverSessionSave.getSession("trip_id", DriverMyStatus.this).equals("") && DriverSessionSave.getSession(DriverCommonData.IS_STREET_PICKUP, DriverMyStatus.this, false)) {
                            Intent intent = new Intent(DriverMyStatus.this, DriverStreetPickUpAct.class);
                            startActivity(intent);
//                        finish();
                        } else {
                            showStreetAlert(DriverNC.getString(R.string.you_are_in_trip));
                        }
                    } else {
//                    dialog1 = Utils.alert_view(MyStatus.this, "" + NC.getResources().getString(R.string.message), "" + recentListMessage, "" + NC.getResources().getString(R.string.ok), "", true, MyStatus.this, "3");
                        DriverCToast.ShowToast(DriverMyStatus.this, DriverSessionSave.getSession("account_message", DriverMyStatus.this));
                    }
                } else {
                    CToast.ShowToast(context, DriverNC.getString(R.string.you_are_in_shift_out));
                }

            }
        });
        tapCurrentLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getAddress == null || ((getAddress.getStatus() != AsyncTask.Status.PENDING && getAddress.getStatus() != AsyncTask.Status.RUNNING))) {
                    if (mLastLocation.getLatitude() != 0.0 && mLastLocation.getLongitude() != 0.0) {
                        if (mLastLocationTemp != null) {
                            if (NeedToGetAddress(mLastLocation, new LatLng(mLastLocationTemp.latitude, mLastLocationTemp.longitude))) {
                                mLastLocationTemp = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                                getAddress = new DriverGetAddressFromLatLng(DriverMyStatus.this, new LatLng(mLastLocationTemp.latitude, mLastLocationTemp.longitude), DriverMyStatus.this, "").execute();
                            }
                        } else {
                            mLastLocationTemp = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                            getAddress = new DriverGetAddressFromLatLng(DriverMyStatus.this, new LatLng(mLastLocationTemp.latitude, mLastLocationTemp.longitude), DriverMyStatus.this, "").execute();
                        }
                    } else
                        DriverCToast.ShowToast(DriverMyStatus.this, DriverNC.getString(R.string.low_gps_alert_message));
                }
            }
        });
        Picasso.get().load(DriverSessionSave.getSession("image_path", this) + "headerLogo_driver.png").into((ImageView) findViewById(R.id.headicon));

        Log.e("_imagepath_", DriverSessionSave.getSession("image_path", this));

        Trip_history.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverMyStatus.this, DriverTripHistoryAct.class);
                startActivity(intent);
            }
        });

        home_iv = findViewById(R.id.home_iv);
        home_iv.setImageResource(R.drawable.ic_home_focus);
        //   Glide.with(this).load(DriverSessionSave.getSession("image_path", this) + "homeFocus.png").apply(new RequestOptions().error(R.drawable.driver_home_focus)).into((ImageView) findViewById(R.id.home_iv));
        home_lay = findViewById(R.id.home_lay);
        earnings_lay = findViewById(R.id.earnings_lay);
        profile_lay = findViewById(R.id.profile_lay);
        streetpick_lay = findViewById(R.id.streetpick_lay);
        no_taxi_view = findViewById(R.id.no_taxi_view);
        no_taxi_assign = findViewById(R.id.no_taxi_assigned);
        menu_emergency_lay = findViewById(R.id.menu_emergency_lay);
        menu_sos = findViewById(R.id.menu_sos);
        menu_emergency_lay.setVisibility(View.GONE);
        menu_chat_helpline.setVisibility(View.GONE);
        earnings_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DriverMyStatus.this, DriverEarningsAct.class);
                startActivity(intent);
                finish();

            }
        });

        home_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        profile_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverMyStatus.this, DriverMeAct.class);
                startActivity(intent);
                finish();
            }
        });

        streetpick_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!DriverSessionSave.getSession("driver_type", DriverMyStatus.this).equalsIgnoreCase("D")) {
                    if (DriverSessionSave.getSession("trip_id", DriverMyStatus.this).equals("")) {
                        Intent intent = new Intent(DriverMyStatus.this, DriverStreetPickUpAct.class);
                        startActivity(intent);
//                        finish();
                    } else if (!DriverSessionSave.getSession("trip_id", DriverMyStatus.this).equals("") && DriverSessionSave.getSession(DriverCommonData.IS_STREET_PICKUP, DriverMyStatus.this, false)) {
                        Intent intent = new Intent(DriverMyStatus.this, DriverStreetPickUpAct.class);
                        startActivity(intent);
//                        finish();
                    } else {
                        showStreetAlert(DriverNC.getString(R.string.you_are_in_trip));
                    }
                } else {
//                    dialog1 = Utils.alert_view(MyStatus.this, "" + NC.getResources().getString(R.string.message), "" + recentListMessage, "" + NC.getResources().getString(R.string.ok), "", true, MyStatus.this, "3");
                    DriverCToast.ShowToast(DriverMyStatus.this, DriverSessionSave.getSession("account_message", DriverMyStatus.this));
                }
            }
        });

        btn_shift.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_shift.setClickable(false);
                new RequestingCheckBox();

            }
        });

        navi_icon_diver.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = findViewById(R.id.nav_view);
                drawerLayout.openDrawer(linearLayout);
            }
        });


        menu_me.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverMyStatus.this, DriverMeAct.class);
                startActivity(intent);
                drawerLayout.closeDrawers();

            }
        });

        nav_home_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
            }
        });

        menu_my_ride_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(DriverMyStatus.this, DriverTripHistoryAct.class);
                startActivity(intent);

            }
        });

        menu_my_earnings_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverMyStatus.this, DriverEarningsAct.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                finish();
            }
        });

        menu_profile_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverMyStatus.this, DriverMeAct.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                finish();
            }
        });

        menu_settings_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverMyStatus.this, DriverSettingsAct.class);
                startActivity(intent);
                drawerLayout.closeDrawers();

            }
        });

        menu_fleet_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverMyStatus.this, DriverMyFleetAct.class);
                startActivity(intent);
                drawerLayout.closeDrawers();

            }
        });

        menu_chat_helpline.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverMyStatus.this, DriverChatWebviewAct.class);
                intent.putExtra("type", "3");
                intent.putExtra("fromMyStatus", "YES");
                startActivity(intent);
                drawerLayout.closeDrawers();

            }
        });

        menu_emergency_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startSOSService();
                drawerLayout.closeDrawers();
            }
        });

        menu_sos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startSOSService();
                drawerLayout.closeDrawers();
            }
        });

        menu_call_helpline.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                Utility.actionSheet(DriverMyStatus.this, DriverNC.getResources().getString(R.string.confirm_call_admin), DriverNC.getResources().getString(R.string.call), DriverNC.getString(R.string.cancel), false, new AlertListener() {
                    @Override
                    public void onSuccess() {
                        try {
                            final Intent callIntent = new Intent(Intent.ACTION_VIEW);
                            callIntent.setData(Uri.parse("tel:" + DriverSessionSave.getSession("dispatcher_phone_number", DriverMyStatus.this)));
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
//                dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, DriverNC.getResources().getString(R.string.message), DriverNC.getResources().getString(R.string.confirm_call_admin), DriverNC.getResources().getString(R.string.call), DriverNC.getResources().getString(R.string.cancel), false, DriverMyStatus.this, "7");
            }
        });


        menu_street_pickup_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!DriverSessionSave.getSession("driver_type", DriverMyStatus.this).equalsIgnoreCase("D")) {
                    if (DriverSessionSave.getSession("trip_id", DriverMyStatus.this).equals("")) {
                        Intent intent = new Intent(DriverMyStatus.this, DriverStreetPickUpAct.class);
                        startActivity(intent);
//                        finish();
                    } else if (!DriverSessionSave.getSession("trip_id", DriverMyStatus.this).equals("") && DriverSessionSave.getSession(DriverCommonData.IS_STREET_PICKUP, DriverMyStatus.this, false)) {
                        Intent intent = new Intent(DriverMyStatus.this, DriverStreetPickUpAct.class);
                        startActivity(intent);
//                        finish();
                    } else {
                        showStreetAlert(DriverNC.getString(R.string.you_are_in_trip));
                    }
                } else {
//                    dialog1 = Utils.alert_view(MyStatus.this, "" + NC.getResources().getString(R.string.message), "" + recentListMessage, "" + NC.getResources().getString(R.string.ok), "", true, MyStatus.this, "3");
                    DriverCToast.ShowToast(DriverMyStatus.this, DriverSessionSave.getSession("account_message", DriverMyStatus.this));
                }
            }
        });


        DriverFontHelper.applyFont(this, curlocation);
        slider = findViewById(R.id.backup);
        headerTxt = findViewById(R.id.headerTxt);
        headerTxt.setText("" + DriverNC.getResources().getString(R.string.my_status));
        headerTxt.setVisibility(View.GONE);


        // Close this activity.
        slider.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // menu.toggle();
                if (c_marker != null) {
                    c_marker = null;
                }

                showLoading(DriverMyStatus.this);
                finish();
            }
        });

        DriverSystems.out.println("shift_chek_7c" + DriverSessionSave.getSession("shift_status", DriverMyStatus.this).equals("IN"));
        //Showing driver shift status
        if (DriverSessionSave.getSession("shift_status", DriverMyStatus.this).equals("IN")) {

            //Drawables_program.shift_on(btn_shift);
//            btn_shift.setText(DriverNC.getString(R.string.online));
            btn_shift.setImageResource(R.drawable.online_24_new);
            online_txt_new.setText(R.string.online);
            online_txt_new.setTextColor(getColor(R.color.green_new));
            offline_lay_bottom.setVisibility(View.GONE);
            btn_shift.setBackground(null);
            shift_value = 1;

            DriverSystems.out.println("shift_chek_7c");
            DriverSessionSave.saveSession(DriverCommonData.SHIFT_OUT, false, DriverMyStatus.this);

        } else {
            shift_value = 0;

            // Drawables_program.shift_bg_grey(btn_shift);
            // btn_shift.setText(DriverNC.getString(R.string.offline));
            btn_shift.setImageResource(R.drawable.offline_24_neww);
            online_txt_new.setText(R.string.offline);
            online_txt_new.setTextColor(getColor(R.color.black_new));
            offline_lay_bottom.setVisibility(View.VISIBLE);
            btn_shift.setBackground(null);

            DriverSystems.out.println("shift_chek_8");
            nonactiityobj.stopServicefromNonActivity(DriverMyStatus.this);
        }

        if (DriverSessionSave.getSession("driver_type", DriverMyStatus.this).equalsIgnoreCase("D")) {
            AccountNotActivated(DriverSessionSave.getSession("account_message", DriverMyStatus.this));
        } else {
            DriverSessionSave.saveSession("account_activate", true, DriverMyStatus.this);
            slide_lay.setVisibility(View.GONE);
            no_taxi_view.setVisibility(View.GONE);
            Window window = getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.header_text));
            }
            DriverSystems.out.println("nan---nOTyET Activated");
        }
    }


    private boolean NeedToGetAddress(Location currentLocation, LatLng previousLocation) {
        float[] dis = new float[1];
        Location.distanceBetween(previousLocation.latitude, previousLocation.longitude, currentLocation.getLatitude(), currentLocation.getLongitude(), dis);
        return dis[0] > 200;
    }

    public void AccountNotActivated(String Message) {
        DriverSystems.out.println("nan-----AccountNotActivated");
        DriverSessionSave.saveSession("account_activate", false, DriverMyStatus.this);
        slide_lay.setVisibility(View.GONE);
        no_taxi_view.setVisibility(View.VISIBLE);
        Window window = getWindow();
        no_taxi_assign.setText(Message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.button_accept));
        }
        Intent i = new Intent(DriverMyStatus.this, LocationUpdate.class);
        stopService(i);
    }

    public void showRedAlert(String alert_msg) {

        if (redAlertSnackBar == null) {

            redAlertSnackBar = Snackbar.make(animlayout, alert_msg, Snackbar.LENGTH_LONG)
                    .setDuration(Snackbar.LENGTH_INDEFINITE);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) redAlertSnackBar.getView();
            // Hide the text
            TextView textView = layout.findViewById(R.id.snackbar_text);
            textView.setVisibility(View.INVISIBLE);

            View snackView = LayoutInflater.from(DriverMyStatus.this).inflate(R.layout.driver_layout_red_alert, null);
            TextView textViewTop = snackView.findViewById(R.id.text_redAlertMessage);
            textViewTop.setText(alert_msg);
            textViewTop.setTextColor(Color.WHITE);

            progressBar = snackView.findViewById(R.id.retry_progress);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(0);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 3000);

            layout.setPadding(0, 0, 0, 0);

            layout.addView(snackView, 0);
            layout.setBackgroundColor(Color.RED);
            redAlertSnackBar.show(); // Don’t forget to show!
        } else {
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(0);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }, 3000);
            }
        }
    }

    public void hideRedAlert() {
        if (redAlertSnackBar != null) {
            redAlertSnackBar.dismiss();
            redAlertSnackBar = null;
        }
    }

    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(final ConnectionResult arg0) {
    }

    @Override
    protected void onPause() {
        DriverSystems.out.println("pauseCalled");
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            stopLocationUpdates();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

//        if (DriverSessionSave.getSession("main_image_path", DriverMyStatus.this) != null) {
//            Picasso.get().load(DriverSessionSave.getSession("main_image_path", DriverMyStatus.this)).into(menu_profile_img);
//        }
        if (DriverSessionSave.getSession("Picture", DriverMyStatus.this) != null && DriverSessionSave.getSession("Picture", DriverMyStatus.this).length() > 0) {
            Picasso.get().load(DriverSessionSave.getSession("Picture", DriverMyStatus.this)).placeholder(getResources().getDrawable(R.drawable.driver_loadingimage)).error(getResources().getDrawable(R.drawable.driver_noimage)).into(menu_profile_img);
        } else {
            if (DriverSessionSave.getSession("Name", DriverMyStatus.this) != "") {
                ProfileImageSetupClass.setupProfileImage(
                        DriverSessionSave.getSession("Name", DriverMyStatus.this), menu_profile_img
                );
            } else {
                Picasso.get().load(R.drawable.loadingimage).into(menu_profile_img);
            }
        }


        if (!DriverSessionSave.getSession("phone_number", DriverMyStatus.this).equalsIgnoreCase("")) {
            menu_pno.setText(DriverSessionSave.getSession("phone_number", DriverMyStatus.this));
        }


        stopLocationUpdates();
        startLocationUpdates();
        if (mLastLocation == null) {
            countDownTimer = new CountDownTimer(30000, 1000) {

                public void onTick(long millisUntilFinished) {
                    DriverSystems.out.println("seconds remaining: " + millisUntilFinished / 1000);
                    try {
                        if (mLastLocation != null && countDownTimer != null)
                            countDownTimer.cancel();


                        if (mLastLocation == null && DriverMyStatus.this != null && (millisUntilFinished < 15000 && millisUntilFinished > 14000)) {

                            //latLongAlert("We are trying to get your location in low accuracy", null);
                            if (mLocationRequest.getPriority() != LocationRequest.PRIORITY_LOW_POWER) {
                                stopLocationUpdates();
                                mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
                                startLocationUpdates();
                            } else {
                                //latLongAlert("Sorry we can't get your current location", null);
                                DriverCToast.ShowToast(DriverMyStatus.this, DriverNC.getString(R.string.address_cant_fetch));
                            }
                            DriverCToast.ShowToast(DriverMyStatus.this, DriverNC.getString(R.string.getting_gps_low));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        DriverCToast.ShowToast(DriverMyStatus.this, DriverNC.getString(R.string.address_cant_fetch));
                    }


                }

                public void onFinish() {
                    if (mLastLocation == null && DriverMyStatus.this != null) {

//                        latLongAlert("Sorry we can't get your current location", null);

                    }
                }
            }.start();
        }
        Picasso.get().load(DriverSessionSave.getSession("image_path", this) + "headerLogo_driver.png").into((ImageView) findViewById(R.id.headicon));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (DriverMyStatus.this != null /*&& MyStatus.this.getCurrentFocus() != null*/) {
                    try {
                        JSONObject j = new JSONObject();
                        j.put("driver_id", DriverSessionSave.getSession("Id", DriverMyStatus.this));
                        j.put("driver_type", DriverSessionSave.getSession("driver_type", DriverMyStatus.this));
                        j.put("device_token", DriverSessionSave.getSession(DriverCommonData.DEVICE_TOKEN, DriverMyStatus.this));
                        String pro_url = "type=driver_recent_trip_list";
                        if (!DriverSessionSave.getSession("Id", DriverMyStatus.this).equals(""))
                            new GetTripData(pro_url, j);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 500);


    }

    /**
     * This method will be called once map ready
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        DriverSystems.out.println("chek the screen google map screen");
        DriverMyStatus.googleMap = googleMap;
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
        DriverMyStatus.googleMap.setMyLocationEnabled(false);
        DriverMyStatus.googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        try {
// Customise the styling of the base map using a JSON object defined
// in a raw resource file.
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(DriverMyStatus.this, R.raw.driver_map_style));
            if (!success) {
                DriverSystems.out.println("Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            DriverSystems.out.println("Can't find style. Error: ");
        }
        DriverMyStatus.googleMap.getUiSettings().setZoomControlsEnabled(false);
        MapsInitializer.initialize(DriverMyStatus.this);
        mapWrapperLayout = findViewById(R.id.map_relative_layout);
        mapWrapperLayout.init(googleMap, getPixelsFromDp(DriverMyStatus.this, 39 + 20));
        DriverMyStatus.googleMap.setOnCameraMoveStartedListener(this);
        setMap();
    }

    @Override
    public void onCameraMoveStarted(int i) {
        if (DriverMapWrapperLayout.ismMapIsTouched()) {
            //mov_cur_loc.setVisibility(View.GONE);
        } else {
            //mov_cur_loc.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void positiveButtonClick(DialogInterface dialog, int id, String s) {
        switch (s) {
            case "1":
                Intent intent = new Intent(DriverMyStatus.this, DriverWebviewAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("fromMyStatus", "YES");
                intent.putExtra("type", "2");
                startActivity(intent);
                finish();
                break;
            case "2":
                Intent intent1 = new Intent(DriverMyStatus.this, DriverWebviewAct.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("fromMyStatus", "YES");
                intent1.putExtra("type", "1");
                startActivity(intent1);
                finish();
                break;
            case "3":
            case "6":
                dialog.dismiss();
                break;
            case "4":
                dialog.dismiss();
                Intent intent2 = getIntent();
                finish();
                startActivity(intent2);
                break;
            case "5":
                dialog.dismiss();

                Intent i = new Intent(DriverMyStatus.this, DriverOngoingAct.class);
                startActivity(i);
                break;
            case "7":
                try {
                    dialog.dismiss();
                    final Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + DriverSessionSave.getSession("dispatcher_phone_number", DriverMyStatus.this)));
                    startActivity(callIntent);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;

            default:
                break;

        }
    }

    @Override
    public void negativeButtonClick(DialogInterface dialog, int id, String s) {
        switch (s) {
            case "1":
            case "2":
            case "5":
                dialog.dismiss();
                break;
            case "3":
                try {
                    if (DriverMyStatus.this != null && dialog != null)
                        dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "4":
                Activity activity1 = DriverMyStatus.this;
                final Intent intent1 = new Intent(Intent.ACTION_MAIN);
                intent1.addCategory(Intent.CATEGORY_HOME);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity1.startActivity(intent1);
                activity1.finish();
                dialog.dismiss();
                break;
            default:
                break;
        }
    }


    private void bookLaterNotificationAlert(String bookLaterDetails) {
//        Utility.actionSheetCancel(DriverMyStatus.this, NC.getResources().getString(R.string.confirmlogout), NC.getResources().getString(R.string.menu_logout), NC.getResources().getString(R.string.cancel), false, new AlertListener() {
//            @Override
//            public void onSuccess() {
//
//            }
//            @Override
//            public void onFailure() {
//
//            }
//        });
        final View bookLaterView = View.inflate(DriverMyStatus.this, R.layout.driver_booklater_alert, null);
        Dialog bookLaterDialog = new Dialog(DriverMyStatus.this, R.style.dialogwinddow);
        bookLaterDialog.setContentView(bookLaterView);
        bookLaterDialog.setCancelable(false);
        bookLaterDialog.show();
        DriverListViewEX driverListViewEX = bookLaterView.findViewById(R.id.testLay);
        driverListViewEX.setData(getStopArray(bookLaterDetails), "SCHEDULE", DriverSessionSave.getSession("Lang", DriverMyStatus.this));
        bookLaterView.findViewById(R.id.btnAccept).setOnClickListener(view -> {
            bookLaterDialog.dismiss();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(BOOKLATER_NOTIFICATION_ID);
            try {
                JSONObject j = new JSONObject();
                j.put("trip_id", scheduleTripId);
                j.put("driver_id", DriverSessionSave.getSession("Id", DriverMyStatus.this));
                String scheduleTripUrl = "type=schedule_accept_trip";
                new ScheduleTrip(scheduleTripUrl, j);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        bookLaterView.findViewById(R.id.btnDecline).setOnClickListener(view -> {
            bookLaterDialog.dismiss();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(BOOKLATER_NOTIFICATION_ID);
            try {
                JSONObject j = new JSONObject();
                j.put("pass_logid", scheduleTripId);
                j.put("driver_id", DriverSessionSave.getSession("Id", DriverMyStatus.this));
                j.put("taxi_id", DriverSessionSave.getSession("taxi_id", DriverMyStatus.this));
                j.put("company_id", DriverSessionSave.getSession("company_id", DriverMyStatus.this));
                j.put("driver_reply", "C");
                j.put("field", "");
                j.put("flag", "1");
                if (MainActivityDriver.mMyStatus.getOnstatus().equalsIgnoreCase("Arrivd"))
                    j.put("driver_arrived", 1);
                else
                    j.put("driver_arrived", 0);
                j.put("schedule", "1");
                final String canceltrip_url = "type=driver_reply";
                new CancelTrip(canceltrip_url, j);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private ArrayList<HashMap<String, String>> getStopArray(String alertMsg) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(alertMsg);
            if (jsonObject.has("info")) {
                JSONObject infoJsonObject = jsonObject.getJSONObject("info");
                Iterator<String> iter = infoJsonObject.keys();
                while (iter.hasNext()) {
                    HashMap<String, String> h2 = new HashMap<>();
                    String key = iter.next();
                    try {
                        Object value = infoJsonObject.get(key);
                        h2.put("KEY", key);
                        h2.put("VALUE", value.toString());
                        scheduleTripId = infoJsonObject.getString("trip_id");
                        arrayList.add(h2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private class CancelTrip implements DriverAPIResult {

        CancelTrip(final String url, JSONObject data) {
            try {
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverMyStatus.this, this, data, false).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    Driver_Utils.alert_view(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyStatus.this, "4");
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
                        DriverCToast.ShowToast(DriverMyStatus.this, json.getString("message"));
                    } else {
                        DriverCToast.ShowToast(DriverMyStatus.this, json.getString("message"));
                    }
                    if (!DriverSessionSave.getSession("trip_id", DriverMyStatus.this).equals("")) {
                        startActivity(new Intent(DriverMyStatus.this, DriverOngoingAct.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                DriverCToast.ShowToast(DriverMyStatus.this, DriverNC.getString(R.string.server_error));
            }
        }

    }


    private class ScheduleTrip implements DriverAPIResult {

        ScheduleTrip(final String url, JSONObject data) {
            try {
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverMyStatus.this, this, data, false).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    Driver_Utils.alert_view(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyStatus.this, "4");
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
                        DriverCToast.ShowToast(DriverMyStatus.this, json.getString("message"));
                    } else {
                        DriverCToast.ShowToast(DriverMyStatus.this, json.getString("message"));
                    }
                    if (!DriverSessionSave.getSession("trip_id", DriverMyStatus.this).equals("")) {
                        startActivity(new Intent(DriverMyStatus.this, DriverOngoingAct.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                DriverCToast.ShowToast(DriverMyStatus.this, DriverNC.getString(R.string.server_error));
            }
        }

    }


    @Override
    public void setaddress(double latitude, double longitude, String Address, String type) {
        DriverSystems.out.println("nan----streeetpickup--MYSTATUS" + Address);
        currentLocation1.setText(Address);
    }

    public void enableDrivertoActiveState() {
        DriverSessionSave.saveSession("driver_type", "A", DriverMyStatus.this);
        if (DriverSessionSave.getSession("shift_status", DriverMyStatus.this).equals("IN")) {
            nonactiityobj.startServicefromNonActivity(DriverMyStatus.this);
        }
        DriverSessionSave.saveSession("account_activate", true, DriverMyStatus.this);
        slide_lay.setVisibility(View.GONE);
        no_taxi_view.setVisibility(View.GONE);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.header_text));
        }
    }


    public void ShowSnackBar(String message, final boolean isRecentList) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isRecentList) {
                            try {
                                JSONObject j = new JSONObject();
                                j.put("driver_id", DriverSessionSave.getSession("Id", DriverMyStatus.this));
                                // j.put("driver_id", "1531");
                                String pro_url = "type=driver_recent_trip_list";
                                if (!DriverSessionSave.getSession("Id", DriverMyStatus.this).equals(""))
                                    new GetTripData(pro_url, j);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            btn_shift.performClick();
                        }
                    }
                });
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        // Changing message text color
        snackbar.setActionTextColor(getResources().getColor(R.color.button_accept));
        snackbar.show();
    }

    /**
     * Starting the location updates
     */
    protected void startLocationUpdates() {
        try {
            if (mGoogleApiClient != null && mLocationRequest != null && mGoogleApiClient.isConnected()) {
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
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    // prakash@abinfosoft.com

    // When connect with location client the following function get the current lat/lng and update the UI.
    @Override
    public void onConnected(Bundle connectionHint) {
        try {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mLastLocation == null && DriverMyStatus.this != null) {
                        try {
                            errorInSplash(DriverNC.getString(R.string.error_in_getting_gps));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, 10000);
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
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), zoom));
                setMap();
                DriverSessionSave.saveSession(DriverCommonData.CURRENT_LAT, "" + mLastLocation.getLatitude(), DriverMyStatus.this);
                DriverSessionSave.saveSession(DriverCommonData.CURRENT_LNG, "" + mLastLocation.getLongitude(), DriverMyStatus.this);
            }
            //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.2006, 92.9376), zoom));
            startLocationUpdates();
            //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (servicesConnected()) {
                if (isOnline()) {
                    if (mLastLocation != null) {
                        coordinate = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                        DriverSessionSave.saveSession(DriverCommonData.CURRENT_LAT, "" + mLastLocation.getLatitude(), DriverMyStatus.this);
                        DriverSessionSave.saveSession(DriverCommonData.CURRENT_LNG, "" + mLastLocation.getLongitude(), DriverMyStatus.this);

                        DriverSessionSave.saveSession("PLAT", "" + mLastLocation.getLatitude(), DriverMyStatus.this);
                        DriverSessionSave.saveSession("PLNG", "" + mLastLocation.getLongitude(), DriverMyStatus.this);
                        //Removed by Nan
                       /* if (SessionSave.getSession(CommonData.isNeedtofetchAddress, MyStatus.this, false)) {
                            if (getAddress == null || ((getAddress.getStatus() != AsyncTask.Status.PENDING && getAddress.getStatus() != AsyncTask.Status.RUNNING)))
                                getAddress = new GetAddressFromLatLng(this, new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), MyStatus.this, "").execute();
                        }*/
                    }
                }
            }

            if (mLastLocation != null) {
                if (a_marker != null) {
                    a_marker.remove();
                }
                if (googleMap != null) {
                    a_marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())).rotation(0).anchor(0.5f, 0.5f).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_img)));
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for check the google service available or not.
     */
    private boolean servicesConnected() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        return ConnectionResult.SUCCESS == resultCode;
    }

    // Function get the current lat/lng and update the UI onlocationchange.
    @Override
    public void onLocationChanged(Location location) {
        DriverSystems.out.println("__________________LLLLLLLpppp" + location);
        if (DriverMyStatus.this != null && location != null) {
            //     if (HighAccuracyCount != 0)
            mLastLocation = location;
            DriverSessionSave.saveSession(DriverCommonData.SOS_LAST_LAT, "" + mLastLocation.getLatitude(), DriverMyStatus.this);
            DriverSessionSave.saveSession(DriverCommonData.SOS_LAST_LNG, "" + mLastLocation.getLongitude(), DriverMyStatus.this);
            DriverSystems.out.println("__________________LLLLLLLpppps" + location.getAccuracy());
            try {
                if (previousLatLong != null) {
                    if (!(previousLatLong.latitude == location.getLatitude() && previousLatLong.longitude == location.getLongitude())) {
                        setMap();
                    }
                } else {
                    setMap();
                }
                previousLatLong = new LatLng(location.getLatitude(), location.getLongitude());
                mapWrapperLayout.setVisibility(View.VISIBLE);
                if (DriverMapWrapperLayout.ismMapIsTouched()) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(previousLatLong, zoom));
                }

                if (mLastLocation != null) {
                    if (a_marker != null) {
                        a_marker.remove();
                    }
                    if (googleMap != null) {
                        a_marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())).rotation(0).anchor(0.5f, 0.5f).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_img)));
                    }

                }

            } catch (Exception e) {
                if (mapWrapperLayout != null)
                    mapWrapperLayout.setVisibility(View.VISIBLE);
                // e.printStackTrace();
            }
        }
    }

    /**
     * setting location movement
     */
    public void setMap() {
        try {
            if (googleMap != null && mLastLocation != null && DriverMyStatus.this != null) {
                mapWrapperLayout.setVisibility(View.VISIBLE);
                LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                if (true) {

                    if (c_marker != null)
                        c_marker.remove();
                    if (googleMap != null)
                        googleMap.clear();

                    final LatLng templatlon = latLng;
                    if (googleMap != null)
                        googleMap.clear();
                    if (null != theBitmap) {
                        //Set image to imageview.
                        if (googleMap != null)
                            googleMap.clear();
                        c_marker = googleMap.addMarker(new MarkerOptions().position(templatlon).title("" + DriverNC.getResources().getString(R.string.you_are_here)).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(theBitmap))));
                    }

                }
                //Removed by Nan
               /* if (SessionSave.getSession(CommonData.isNeedtofetchAddress, MyStatus.this, false)) {
                    if (getAddress == null || ((getAddress.getStatus() != AsyncTask.Status.PENDING && getAddress.getStatus() != AsyncTask.Status.RUNNING)))
                        getAddress = new GetAddressFromLatLng(this, new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), MyStatus.this, "").execute();
                }*/

                if (mLastLocation != null) {
                    if (a_marker != null) {
                        a_marker.remove();
                    }
                    if (googleMap != null) {
                        a_marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())).rotation(0).anchor(0.5f, 0.5f).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_img)));
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap resizeMapIcons(Bitmap bitmap) {
        int newWidth = width;
        int newHeight = height;
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }

    /**
     * This method used to get current address
     */
    private void getCurrentAddress(double lat, double lon) {
        Geocoder geocoder;
        List<Address> addresses = null;
        String address = "";
        String city = "";
        String country = "";
        geocoder = new Geocoder(this, Locale.UK);
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            if (addresses.size() != 0) {
                address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getAddressLine(1);
                country = addresses.get(0).getAddressLine(2);
                LatLng coordinate = new LatLng(lat, lon);

                if (mapstatus == 0) {
                    if (googleMap != null)
                        googleMap.clear();
                    if (c_marker != null) {
                        c_marker.remove();
                    }


                    final LatLng templatlon = coordinate;
                    new AsyncTask<String, Void, Void>() {
                        Bitmap theBitmap = null;
                        Bitmap bm = null;

                        @Override
                        protected Void doInBackground(String... params) {
                            String TAG = "Error Message: ";
                            try {
                                theBitmap = Glide.
                                        with(DriverMyStatus.this).
                                        asBitmap().
                                        load(DriverSessionSave.getSession("image_path", DriverMyStatus.this) + "setPickupPin.png").
                                        submit(100, 100). // Width and height
                                                get();

                            } catch (final Exception e) {
                                //
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void dummy) {
                            if (null != theBitmap) {
                                //Set image to imageview.
                                if (googleMap != null)
                                    googleMap.clear();
                                c_marker = googleMap.addMarker(new MarkerOptions().position(templatlon).title("" + DriverNC.getResources().getString(R.string.you_are_here)).icon(BitmapDescriptorFactory.fromBitmap(theBitmap)));
                            }
                        }
                    }.execute();
                    if (!address.equalsIgnoreCase(""))
                        currentLocation1.setText(("" + address + " " + city + " " + country).replaceAll("null", "").replaceAll(", ,", "").replaceAll(", ,", ""));
                    mapstatus = 1;
                }
                bearing = 0;
                if (!address.equalsIgnoreCase(""))
                    currentLocation1.setText(("" + address + "\n" + city + "\n" + country).replaceAll("null", "").replaceAll(", ,", "").replaceAll(", ,", ""));
            } else {
                DriverCToast.ShowToast(DriverMyStatus.this, DriverNC.getString(R.string.address_not_found));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        if (mGoogleApiClient != null)
            stopLocationUpdates();
        googleMap = null;
        if (c_marker != null) {
            c_marker = null;
        }
        if (dialog1 != null)
            Driver_Utils.closeDialog(dialog1);

        LocalBroadcastManager.getInstance(this).unregisterReceiver(listener);
        super.onDestroy();
    }

    /**
     * This method used to round the decimal values
     */
    private double roundDecimal(double value, final int decimalPlace) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        value = bd.doubleValue();
        return value;
    }

    /**
     * This method used to convert speed
     */
    private double convertSpeed(double speed) {
        return ((speed * 3600) * 0.001);
    }


    public void latLongAlert(String message, LatLng latLng) {
        try {
            if (DriverMyStatus.this != null) {
                Toast.makeText(DriverMyStatus.this, DriverNC.getString(R.string.message), Toast.LENGTH_SHORT).show();
//                dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, DriverNC.getString(R.string.message), message, DriverNC.getString(R.string.ok), DriverNC.getString(R.string.cancel), false, DriverMyStatus.this, "3");
            } else {
                try {
                    if (DriverMyStatus.this != null && errorDialog != null)
                        errorDialog.dismiss();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void errorInSplash(String message) {
        DriverSystems.out.println("GPSSSSSSSSSSSSSs" + ischecked);
        try {
            if (DriverMyStatus.this != null) {

                if ((ActivityCompat.checkSelfPermission(DriverMyStatus.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(DriverMyStatus.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                    Utility.actionSheet(DriverMyStatus.this, DriverNC.getResources().getString(R.string.str_loc),  DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), false, new AlertListener() {
                        @Override
                        public void onSuccess() {
                            ActivityCompat.requestPermissions(DriverMyStatus.this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_GPS);
                        }
                        @Override
                        public void onFailure() {
                            finish();
                        }
                    });
                    /*
                    dialog1 = Driver_Utils.alert_view_dialog(DriverMyStatus.this, "", DriverNC.getResources().getString(R.string.str_loc), DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            ActivityCompat.requestPermissions(DriverMyStatus.this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_GPS);
                            dialog.dismiss();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                            finish();
                        }
                    }, "");

                     */
                } else {
                    message = DriverNC.getString(R.string.change_network);
//            if (isGpsEnabled(mContext)) {
                    Utility.actionSheetCancel(DriverMyStatus.this,message, DriverNC.getResources().getString(R.string.enable),"", false, new AlertListener() {
                        @Override
                        public void onSuccess() {
                            Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(mIntent);
                        }
                        @Override
                        public void onFailure() {
                            finish();
                        }
                    });
                    /*
                    Driver_Utils.alert_view_dialog_GPS(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.location_disable),
                            "" + message,
                            "" + DriverNC.getResources().getString(R.string.enable),
                            "", false, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(mIntent);
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            }, "");

                     */
//                    dialog1 = Utils.alert_view(MyStatus.this, getResources().getString(R.string.message), message, getResources().getString(R.string.c_tryagain), getResources().getString(R.string.cancel), false, MyStatus.this, "4");
                }
            } else {
                try {
                    if (DriverMyStatus.this != null && errorDialog != null)
                        errorDialog.dismiss();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void showStreetAlert(String message) {
        try {
            if (DriverMyStatus.this != null) {
                Utility.actionSheet(DriverMyStatus.this, message, DriverNC.getString(R.string.track_now), DriverNC.getString(R.string.cancel), false, new AlertListener() {
                    @Override
                    public void onSuccess() {
                        Intent i = new Intent(DriverMyStatus.this, DriverOngoingAct.class);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
//                dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, DriverNC.getString(R.string.message), message, DriverNC.getString(R.string.track_now), DriverNC.getString(R.string.cancel), false, DriverMyStatus.this, "5");

            } else {
                try {
                    if (DriverMyStatus.this != null && errorDialog != null)
                        errorDialog.dismiss();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//
//            startActivity(intent);
            final Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            this.doubleBackToExitPressedOnce = true;
            DriverCToast.ShowToast(this, "" + DriverNC.getResources().getString(R.string.please_click_back_again_exit));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    private void AnimationInScreen() {
        if (Driver_Utils.HigherThanLollipop()) {
            ViewCompat.postOnAnimation(animlayout, new Runnable() {
                @Override
                public void run() {
                    //Cicular animation
                    Animator animator = Driver_Utils.animateRevealWithoutColorFromCoordinates(animlayout);
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            DriverSessionSave.saveSession("need_animation", false, DriverMyStatus.this);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }

                    });

                }
            });
        } else {
        }
    }

    private void startSOSService() {
        DriverSessionSave.saveSession("sos_id", DriverSessionSave.getSession("Id", DriverMyStatus.this), DriverMyStatus.this);
        DriverSessionSave.saveSession("user_type", "d", DriverMyStatus.this);
        startActivity(new Intent(DriverMyStatus.this, SOSActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        startSOSService();
                    }
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_GPS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        nonactiityobj.stopServicefromNonActivity(DriverMyStatus.this);
                        final Intent i = new Intent(getApplicationContext(), SplashActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        finish();
                    }
                }
            }
        }
    }

    /**
     * @API call(get method) to get the driver trip data and parsing the response
     */
    private class GetTripData implements DriverAPIResult {
        public GetTripData(String url, JSONObject data) {

            try {
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON_NoProgress(DriverMyStatus.this, this, data, false).execute(url);
                } else {
                    Log.d("No Internet Connection", "No Internet");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {

            try {

                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1 ||
                            json.getInt("status") == -4 ||
                            json.getInt("status") == -2 ||
                            json.getInt("status") == -3) {
                        enableDrivertoActiveState();
                        if (json.getInt("status") == -4) {
                            DriverCToast.ShowToast(DriverMyStatus.this, "" + json.getString("message"));
//                            dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.message), "" + json.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyStatus.this, "3");
                        } else if (json.getInt("status") == -2) {
                            DriverSystems.out.println("myCode_______" + "in -2 condition");
                            Utility.actionSheetCancel(DriverMyStatus.this, json.getString("message"), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancel), false, new AlertListener() {
                                @Override
                                public void onSuccess() {
                                    Intent intent = new Intent(DriverMyStatus.this, DriverWebviewAct.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("fromMyStatus", "YES");
                                    intent.putExtra("type", "2");
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure() {

                                }
                            });
//                            dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, DriverNC.getResources().getString(R.string.message), json.getString("message"), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancel), false, DriverMyStatus.this, "1");
                        } else if (json.getInt("status") == -3) {
                            DriverSystems.out.println("myCode_______" + "in -3 condition");
                            Utility.actionSheetCancel(DriverMyStatus.this, json.getString("message"), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancel), false, new AlertListener() {
                                @Override
                                public void onSuccess() {
                                    Intent intent1 = new Intent(DriverMyStatus.this, DriverWebviewAct.class);
                                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent1.putExtra("fromMyStatus", "YES");
                                    intent1.putExtra("type", "1");
                                    startActivity(intent1);
                                    finish();
                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                            /*
                            dialog1 = Driver_Utils.alert_view_dialog(DriverMyStatus.this, DriverNC.getResources().getString(R.string.message), json.getString("message"), DriverNC.getResources().getString(R.string.ok), DriverNC.getResources().getString(R.string.cancel), false, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Utils.closeDialog();
                                    dialogInterface.dismiss();
                                    Intent intent1 = new Intent(DriverMyStatus.this, DriverWebviewAct.class);
                                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent1.putExtra("fromMyStatus", "YES");
                                    intent1.putExtra("type", "1");
                                    startActivity(intent1);
                                    finish();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }, "");

                             */

                        }

                        JSONArray details = json.getJSONArray("trip_list");
                        System.out.println("Trip list count     " + details  + "   " +  details.length());
                        templength = details.length();
                        if (templength > 0) {
                            trip_detailslay.setVisibility(View.VISIBLE);
                            if (templength == 1) {
                                first_lay.setVisibility(View.VISIBLE);
                                second_lay.setVisibility(View.GONE);
                                third_lay.setVisibility(View.GONE);

                                lasttripheader.setText(DriverNC.getString(R.string.Lasttrip));

                            } else if (templength == 2) {
                                first_lay.setVisibility(View.VISIBLE);
                                second_lay.setVisibility(View.VISIBLE);
                                third_lay.setVisibility(View.GONE);

                                lasttripheader.setText(DriverNC.getString(R.string.Last2trip));
                            } else if (templength == 3) {
                                first_lay.setVisibility(View.VISIBLE);
                                second_lay.setVisibility(View.VISIBLE);
                                third_lay.setVisibility(View.VISIBLE);

                                lasttripheader.setText(DriverNC.getString(R.string.Last3trip));
                            } else {
                                first_lay.setVisibility(View.VISIBLE);
                                second_lay.setVisibility(View.VISIBLE);
                                third_lay.setVisibility(View.VISIBLE);

                                lasttripheader.setText(DriverNC.getString(R.string.Last3trip));
                            }


                            for (int i = 0; i < templength; i++) {
                                if (i == 0) {
                                    first_tripdate.setText(details.getJSONObject(i).getString("drop_time").trim());
                                    first_tripmodel.setText(details.getJSONObject(i).getString("model_name").trim());
                                    first_tripamt.setText(DriverSessionSave.getSession("site_currency", DriverMyStatus.this) + " " + details.getJSONObject(i).getString("fare").trim());
                                }
                                if (i == 1) {
                                    second_tripdate.setText(details.getJSONObject(i).getString("drop_time").trim());
                                    second_tripmodel.setText(details.getJSONObject(i).getString("model_name").trim());
                                    second_tripamt.setText(DriverSessionSave.getSession("site_currency", DriverMyStatus.this) + " " + details.getJSONObject(i).getString("fare").trim());
                                }
                                if (i == 2) {
                                    third_tripdate.setText(details.getJSONObject(i).getString("drop_time").trim());
                                    third_tripmodel.setText(details.getJSONObject(i).getString("model_name").trim());
                                    third_tripamt.setText(DriverSessionSave.getSession("site_currency", DriverMyStatus.this) + " " + details.getJSONObject(i).getString("fare").trim());
                                }
                            }

                        } else {
                            trip_detailslay.setVisibility(View.GONE);
                        }

                    } else if (json.getInt("status") == 10) {
                        trip_detailslay.setVisibility(View.GONE);
                        DriverSessionSave.saveSession("driver_type", "D", DriverMyStatus.this);
                        DriverSessionSave.saveSession("account_activate", false, DriverMyStatus.this);
                        AccountNotActivated(DriverSessionSave.getSession("account_message", DriverMyStatus.this));
                    } else if (json.getInt("status") == -4) {
                        DriverCToast.ShowToast(DriverMyStatus.this, "" + json.getString("message"));
//                        dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.message), "" + json.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyStatus.this, "3");
                        trip_detailslay.setVisibility(View.GONE);
                    } else if (json.getInt("status") == 40) {
                        enableDrivertoActiveState();
                        DriverCToast.ShowToast(DriverMyStatus.this, "" + json.getString("message"));
//                        dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.message), "" + json.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyStatus.this, "3");
                        trip_detailslay.setVisibility(View.GONE);
                    } else if (json.getInt("status") == 41) {
                        recentListMessage = json.getString("message");
                        no_taxi_view.setVisibility(View.VISIBLE);
                        Window window = getWindow();
                        no_taxi_assign.setText(recentListMessage);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.button_accept));
                        }
                        if (DriverSessionSave.getSession("shift_status", DriverMyStatus.this).equals("IN")) {
                            nonactiityobj.startServicefromNonActivity(DriverMyStatus.this);
                        }
                        trip_detailslay.setVisibility(View.GONE);
//                        SessionSave.saveSession("driver_type", "D", MyStatus.this);
//                        SessionSave.saveSession("account_activate", false, MyStatus.this);
//                        AccountNotActivated(json.getString("message"));
                    } else if (json.getInt("status") == -1) {
                        trip_detailslay.setVisibility(View.GONE);
                        enableDrivertoActiveState();
                    } else {
                        trip_detailslay.setVisibility(View.GONE);
                    }
                } else {
                    trip_detailslay.setVisibility(View.GONE);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            DriverCToast.ShowToast(DriverMyStatus.this, DriverNC.getString(R.string.server_error));
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Driver Shift API response parsing.
     */
    private class RequestingCheckBox implements DriverAPIResult {
        public RequestingCheckBox() {
            try {
                if (shift_value == 1)
                    checked = "OUT";
                else
                    checked = "IN";
                JSONObject j = new JSONObject();
                j.put("driver_id", DriverSessionSave.getSession("Id", DriverMyStatus.this));
                j.put("shiftstatus", checked);
                j.put("reason", "");
                Log.e("shiftbefore ", j.toString());

                j.put("update_id", DriverSessionSave.getSession("Shiftupdate_Id", DriverMyStatus.this));
                String requestingCheckBox = "type=driver_shift_status";
                if (isOnline())
                    new DriverAPIService_Retrofit_JSON(DriverMyStatus.this, this, j, false).execute(requestingCheckBox);
                else {
                    btn_shift.setClickable(true);
                    DriverCToast.ShowToast(DriverMyStatus.this, "" +  DriverNC.getResources().getString(R.string.server_error));
//                    dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.server_error), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyStatus.this, "3");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @SuppressLint("ResourceAsColor")
        @Override
        public void getResult(boolean isSuccess, final String result) {

            try {

                Log.e("driverstatus", result);

                if (isSuccess && DriverMyStatus.this != null) {
                    btn_shift.setClickable(true);

                    JSONObject object = new JSONObject(result);
                    if (object.getInt("status") == 1) {
                        if (checked.equals("IN")) {
                            DriverCToast.ShowToast(DriverMyStatus.this, "" + object.getString("message"));
//                            dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.message), "" + object.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyStatus.this, "6");
                            // btn_shift.setText(DriverNC.getString(R.string.online));
                            //  Drawables_program.shift_on(btn_shift);
                            shift_value = 1;
                            btn_shift.setImageResource(R.drawable.online_24_new);
                            offline_lay_bottom.setVisibility(View.GONE);
                            online_txt_new.setText(R.string.online);
                            online_txt_new.setTextColor(getColor(R.color.green_new));
                            btn_shift.setBackground(null);

                            DriverSystems.out.println("innnnnn " + "shift_chek_3");
                            DriverSessionSave.saveSession("shift_status", "IN", DriverMyStatus.this);
                            DriverSessionSave.saveSession(DriverCommonData.SHIFT_OUT, false, DriverMyStatus.this);
                            DriverSessionSave.saveSession("Shiftupdate_Id", object.getJSONObject("detail").getString("update_id"), DriverMyStatus.this);
                            Log.e("sess", DriverSessionSave.getSession("shift_status", DriverMyStatus.this));

                            if (!DriverSessionSave.getSession("driver_type", DriverMyStatus.this).equalsIgnoreCase("D"))
                                nonactiityobj.startServicefromNonActivity(DriverMyStatus.this);
                        } else {
                            DriverCToast.ShowToast(DriverMyStatus.this, "" + object.getString("message"));
//                            dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.message), "" + object.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyStatus.this, "6");
                            // btn_shift.setText(DriverNC.getString(R.string.offline));
                            btn_shift.setImageResource(R.drawable.offline_24_neww);
                            online_txt_new.setText(R.string.offline);
                            online_txt_new.setTextColor(getColor(R.color.black));
                            offline_lay_bottom.setVisibility(View.VISIBLE);
                            btn_shift.setBackground(null);
                            shift_value = 0;
                            //Drawables_program.shift_bg_grey(btn_shift);
                            DriverSystems.out.println("innnnnn " + "shift_chek_4");
                            DriverSessionSave.saveSession("shift_status", "OUT", DriverMyStatus.this);
                            DriverSessionSave.saveSession("trip_id", "", DriverMyStatus.this);
                            DriverSessionSave.setWaitingTime(0L, DriverMyStatus.this);
                            Log.e("sess", DriverSessionSave.getSession("shift_status", DriverMyStatus.this));
                            nonactiityobj.stopServicefromNonActivity(DriverMyStatus.this);
                        }
                    } else {
                        DriverCToast.ShowToast(DriverMyStatus.this, "" + object.getString("message"));
//                        dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.message), "" + object.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyStatus.this, "3");

                    }
                } else {

                    runOnUiThread(new Runnable() {
                        public void run() {
                            DriverCToast.ShowToast(DriverMyStatus.this, DriverNC.getString(R.string.please_check_internet));
                        }
                    });
                    btn_shift.setClickable(true);
                    if (checked.equals("IN")) {
                        //  btn_shift.setText(DriverNC.getString(R.string.online));
                        DriverSystems.out.println("shift_chek_12");
                        // Drawables_program.shift_on(btn_shift);
                        shift_value = 1;
                        btn_shift.setImageResource(R.drawable.online_24_new);
                        offline_lay_bottom.setVisibility(View.GONE);
                        online_txt_new.setText(R.string.online);
                        online_txt_new.setTextColor(getColor(R.color.green_new));
                        btn_shift.setBackground(null);

                    } else {
                        //btn_shift.setText(DriverNC.getString(R.string.offline));
                        DriverSystems.out.println("shift_chek_13");
                        shift_value = 0;
                        // Drawables_program.shift_bg_grey(btn_shift);
                        btn_shift.setImageResource(R.drawable.offline_24_neww);
                        online_txt_new.setText(R.string.offline);
                        online_txt_new.setTextColor(getColor(R.color.black));
                        offline_lay_bottom.setVisibility(View.VISIBLE);
                        btn_shift.setBackground(null);

                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                DriverSystems.out.println("thambiError" + ex.getLocalizedMessage());
                btn_shift.setClickable(true);
                DriverCToast.ShowToast(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.server_error));
//                dialog1 = Driver_Utils.alert_view(DriverMyStatus.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.server_error), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverMyStatus.this, "3");
            }
        }
    }
}