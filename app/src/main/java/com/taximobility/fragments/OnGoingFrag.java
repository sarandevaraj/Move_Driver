package com.taximobility.fragments;

/**
 * this class is used to follow the steps after the trip is started
 */


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.PointF;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mayan.sospluginmodlue.service.SOSService;
import com.taximobility.ProfileImageSetupClass;
import com.squareup.picasso.Picasso;
import com.taximobility.ChatWebviewAct;
import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.PreferenceInfoAlert;
import com.taximobility.R;
import com.taximobility.bookingmodule.BookTaxiHomePage;
import com.taximobility.data.MapWrapperLayout;
import com.taximobility.driver.data.apiData.AddonsData;
import com.taximobility.driver.interfaces.DriverAPIResult;
import com.taximobility.driver.service.DriverAPIService_Retrofit_JSON;
import com.taximobility.features.CToast;
import com.taximobility.features.FindApproxDistance;
import com.taximobility.interfaces.APIResult;
import com.taximobility.interfaces.DistanceMatrixInterface;
import com.taximobility.interfaces.GetPassUpdate;
import com.taximobility.locationSearch.AddStopActivity;
import com.taximobility.locationSearch.PlacesData;
import com.taximobility.pdview.CustomShadowProvider;
import com.taximobility.pdview.PickupDropView;
import com.taximobility.route.Route;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.service.GetPassengerUpdate;
import com.taximobility.util.CL;
import com.taximobility.util.CarMovementAnimation;

import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import static com.taximobility.locationSearch.AddStopActivityKt.IS_FROM_ONGOING;
import static com.taximobility.locationSearch.AddStopActivityKt.STOP_SLAB_SIZE;
import static com.taximobility.util.ConstantsKt.BUNDLE_PICKUP_DROP_ADDRESS;
import static com.taximobility.util.ConstantsKt.BUNDLE_STOP_ADDRESS;
import static com.taximobility.util.ConstantsKt.BUNDLE_STOP_LAT;
import static com.taximobility.util.ConstantsKt.BUNDLE_STOP_LNG;
import static com.taximobility.util.ConstantsKt.CREDIT_CARD;
import static com.taximobility.util.ConstantsKt.IS_BUISNESS_KEY;
import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.speed;

//import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class OnGoingFrag extends Fragment implements LocationListener, ConnectionCallbacks, OnConnectionFailedListener, OnMapReadyCallback, DistanceMatrixInterface, GetPassUpdate {

    //Permission request code
    private final int MY_PERMISSIONS_REQUEST_CALL = 124;
    private final int MY_PERMISSIONS_REQUEST_GPS = 125;
    private final int REQUEST_READ_PHONE_STATE = 292;
    private final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private BottomSheetDialog mcancelDialog;
    public int mTripid, layoutheight;
    public Dialog cvv_Dialog;
    public TextView minfareTxt, back_text;
    private String tips_1 = "", tips_2 = "", tips_3 = "", tips = "";
    int show_tips = 1; //0 to show and 1 to hide
    // Animate marker
    ArrayList<LatLng> listPoint = new ArrayList<LatLng>();
    ArrayList<LatLng> savedpoint = new ArrayList<LatLng>();
    // Location updates intervals in sec
    private int UPDATE_INTERVAL = 5000; // 10 sec
    private int FATEST_INTERVAL = 1000; // 5 sec
    private int DISPLACEMENT = 0; // 10 meters
    //class member variable declarations
    private boolean animLocation = false;
    private int countDriverLoc = 0, mTravelstatus, tripType;
    private double previous_latitude, previous_longitude, lat1, log1, distancemtr;
    private double Latitude, Longitude;
    private float zoom = 17f, bearing;
    private String driverphone, routePath, sReason, timetoreach, alert_msg, moveToChat = "", p_image_name;
    private String pickuploc, pickuplat, droplat, droplong;
    private String pickuplong, droploc;
    private String pLatitude, pLongitude, dLatitude, dLongitude;
    private String ondriverlat, ondriverlng, estimate_time;
    private ArrayList<LatLng> latLngArrayList = new ArrayList<>();
    private ArrayList<PlacesData> stopList;
    private Location mLastLocation;
    private LatLng myLocation, pickLocation, dropLocation;
    private MapWrapperLayout mapWrapperLayout;
    private Dialog alertmDialog, dialog1;
    private Route mRoute;
    private CardView card_bottom_lay;
    private Bundle alert_bundle = new Bundle();
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap map;
    private Marker dmarker;
    // A request to connect to Location Services
    private LocationRequest mLocationRequest;
    //View declarations
    private TextView DoneTxt, Title, T_Pickuptime, nodataTxt, txt_pickup;
    private TextView tvEditPickUpDrop, call_icon;
    private TextView calltxt, call_ccancel, txt_estimate, chatTxt;
    private TextView CancelTxt, book_taxi, Taxino_txt, Driver_name, rating_count;
    private LinearLayout Driverlay, lay_call, lay_call_cancel, apptimelay, Transitlay, callbottom_lay;
    public String passengerGraceTime = "";
    private String call_masking_ph_no = "";
    private TextView preference, order_details;
    private ArrayList<AddonsData> preferenceData = new ArrayList<>();
    private PreferenceInfoAlert preferenceInfoAlert;

    private String product_name = "";
    private String product_weight = "";
    private String product_size = "";
    private String delivery_person_name = "", delivery_phone_number = "", delivery_date_time = "";
    private String delivery_notes = "";
    private Dialog orderDialog;


//    private MaterialTapTargetPrompt mTapTarget;

    /**
     * getActivity() handler used to handle the ongoing trip UI changes based on status.
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case 0:
                    Driverlay.setVisibility(View.GONE);
                    Transitlay.setVisibility(View.GONE);
                    break;
                case 1:
                    Driverlay.setVisibility(View.VISIBLE);
                    Transitlay.setVisibility(View.GONE);
                    break;
                case 2:
                    transit(pLatitude, pLongitude, dLatitude, dLongitude);
                    break;
            }
        }

    };
    private LinearLayout infoLayout, estimate_fare, searchlayl;
    private FrameLayout contact_layout, map_lay;
    private RelativeLayout lay_no_data, header_title_lay;
    private ImageView Driver_img, Rating, imgV_cash;
    private View time_sep;
    private FloatingActionButton mov_cur_loc, share_location;
    private PickupDropView pickUpDropLayout;
    private LinearLayout bottom_sheet;
    private AppCompatButton btn_emergency;
    private TextView txt_drop;

    /**
     * calculate the distance between source and destinchatTxtation.
     */

    private static double getDistanceBetweenTwoPoints(final PointF p1, final PointF p2) {
        final double R = 6371000; // m
        final double dLat = Math.toRadians(p2.x - p1.x);
        final double dLon = Math.toRadians(p2.y - p1.y);
        final double lat1 = Math.toRadians(p1.x);
        final double lat2 = Math.toRadians(p2.x);
        final double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        final double d = R * c;
        return d;
    }

    private static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371000; // for haverSine use R = 6372.8 km instead of 6371 km
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return 2 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        // simplify haverSine:
    }

    /**
     * Computes the bearing in degrees between two points on Earth.
     *
     * @return Bearing between the two points in degrees. A value of 0 means due
     * north.
     */
    public static double bearing(LatLng latLng1, LatLng latLng2) {
        double lat1 = latLng1.latitude;
        double lon1 = latLng1.longitude;
        double lat2 = latLng2.latitude;
        double lon2 = latLng2.longitude;
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLonRad = Math.toRadians(lon2 - lon1);
        double y = Math.sin(deltaLonRad) * Math.cos(lat2Rad);
        double x = Math.cos(lat1Rad) * Math.sin(lat2Rad) - Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(deltaLonRad);
        return radToBearing(Math.atan2(y, x));
    }

    /**
     * Converts an angle in radians to degrees
     */
    public static double radToBearing(double rad) {
        return (Math.toDegrees(rad) + 360) % 360;
    }

    // Get the google map pixels from xml density independent pixel.
    public static int getPixelsFromDp(final Context context, final float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ongoinglay, container, false);
        Initialize(v);
        //   Colorchange.ChangeColor((ViewGroup) v, getActivity());
        estimate_fare.setVisibility(View.GONE);
        mcancelDialog = new BottomSheetDialog(getActivity());
        return v;
    }

    /**
     * Initialize view for help fragment
     *
     * @param v
     */

    public void Initialize(View v) {
        // TODO Auto-generated method stub
        /*
         * Get the details from notification
         */
        alert_bundle = this.getArguments();
        if (alert_bundle != null) {
            alert_msg = alert_bundle.getString("alert_message");
            moveToChat = alert_bundle.getString("chat", "");
        }

        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            mLocationRequest.setFastestInterval(FATEST_INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        }
        TaxiUtil.current_act = "OngoingTrip";
        pickUpDropLayout = v.findViewById(R.id.pd_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pickUpDropLayout.setElevation(8f);
            pickUpDropLayout.setOutlineProvider(new CustomShadowProvider(5f));
            pickUpDropLayout.setClipToOutline(false);
        }
        tvEditPickUpDrop = v.findViewById(R.id.tvEditPickUpDrop);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            tvEditPickUpDrop.setElevation(30f);
//        }
        map_lay = v.findViewById(R.id.map_lay);
        nodataTxt = v.findViewById(R.id.nodataTxt);
        lay_no_data = v.findViewById(R.id.lay_no_data);
        callbottom_lay = v.findViewById(R.id.callbottom_lay);
//        contact_layout = v.findViewById(R.id.contact_layout);
        book_taxi = v.findViewById(R.id.booktaxilay);
        estimate_fare = v.findViewById(R.id.estimate_fare);
        txt_pickup = v.findViewById(R.id.txt_pickup);
        txt_drop = v.findViewById(R.id.txt_drop);
        preference = v.findViewById(R.id.preference);
        order_details = v.findViewById(R.id.order_details);
        btn_emergency = v.findViewById(R.id.btn_emergency);
        btn_emergency.setVisibility(View.VISIBLE);

        bottom_sheet = v.findViewById(R.id.bottom_sheet);
        bottom_sheet.setBackgroundResource(R.drawable.corner_over_tripdetail);

        btn_emergency.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
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
                button_failure.setOnClickListener(view22 -> emergency_dialog.dismiss());
            }
        });
        book_taxi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new HomePage()).commit();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new BookTaxiHomePage()).commit();
            }
        });
        // Start the service if passenger have any ongoing trip.
        if (SessionSave.getSession("trip_id", getActivity()).equals("")) {
            Intent intent = new Intent(getActivity(), GetPassengerUpdate.class);
            getActivity().stopService(intent);
        } else {
            Intent intent = new Intent(getActivity(), GetPassengerUpdate.class);
            getActivity().startService(intent);
        }
        // Create a new global location parameters object
        v.findViewById(R.id.ongoing_contain);
        /*
         * API call to get ongoing trip details
         */
        if (!SessionSave.getSession("trip_id", getActivity()).equals("")) {
            SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapWrapperLayout = v.findViewById(R.id.map_relative_layout);
            mapFrag.getMapAsync(this);
        } else {
            map_lay.setVisibility(View.GONE);
            bottom_sheet.setVisibility(View.GONE);
            lay_no_data.setVisibility(View.VISIBLE);
        }
        /*
         * Initialization of Local Variable
         */
        TaxiUtil.mActivitylist.add(getActivity());
        mRoute = new Route(OnGoingFrag.this);
        DoneTxt = v.findViewById(R.id.rightIconTxt);
        DoneTxt.setVisibility(View.GONE);
        CancelTxt = v.findViewById(R.id.leftIcon);
        CancelTxt.setVisibility(View.GONE);
        back_text = v.findViewById(R.id.back_text);


        txt_estimate = v.findViewById(R.id.txt_estimate);
        time_sep = v.findViewById(R.id.time_sep);
        back_text.setVisibility(View.VISIBLE);
        Title = v.findViewById(R.id.header_titleTxt);
        Title.setText(NC.getResources().getString(R.string.Trip_in_progress));
        Title.setVisibility(View.GONE);
        v.findViewById(R.id.ongoing_contain);
        Transitlay = v.findViewById(R.id.intransitlay);
        Driverlay = v.findViewById(R.id.driverdetailslay);
        lay_call = v.findViewById(R.id.lay_call);
        lay_call_cancel = v.findViewById(R.id.lay_call_cancel);
        calltxt = v.findViewById(R.id.callText);
//        calltxt.setTextColor(CL.getResources().getColor(getActivity(), R.color.button_accept));
        call_ccancel = v.findViewById(R.id.cancel_txt);
        mov_cur_loc = v.findViewById(R.id.mov_cur_loc);
        share_location = v.findViewById(R.id.share_location);
        share_location.setVisibility(View.GONE);
        chatTxt = v.findViewById(R.id.chatTxt);
        chatTxt.setVisibility(View.GONE);

        call_ccancel.setTextColor(CL.getResources().getColor(getActivity(), R.color.button_accept));
        if (getActivity() != null) {
           /* Glide.with(getActivity())
                    .load(SessionSave.getSession("image_path", getActivity()) + "callDriver.png")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_call_new).override(40, 40))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource,
                                                    @Nullable Transition<? super Drawable> transition) {
                            *//* Set a drawable to the left of textView *//*
                            calltxt.setCompoundDrawablesWithIntrinsicBounds(resource, null, null, null);

                        }
                    });

            Glide.with(getActivity())
                    .load(SessionSave.getSession("image_path", getActivity()) + "tripCancel.png")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_cancel_new).override(40, 40))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource,
                                                    @Nullable Transition<? super Drawable> transition) {
                            *//* Set a drawable to the left of textView *//*
                            call_ccancel.setCompoundDrawablesWithIntrinsicBounds(resource, null, null, null);
                        }
                    });*/
        }


        apptimelay = v.findViewById(R.id.apptimelay);
        Driverlay.setVisibility(View.GONE);
        Transitlay.setVisibility(View.GONE);
        // ---Driver
        Driver_name = v.findViewById(R.id.drivername);
        minfareTxt = v.findViewById(R.id.minfareTxt);
        Driver_img = v.findViewById(R.id.driverImg);
        Taxino_txt = v.findViewById(R.id.taxinoTxt);
        rating_count = v.findViewById(R.id.rating_count);
        Rating = v.findViewById(R.id.rating);
        // in transit
        call_icon = v.findViewById(R.id.call_icon);
        T_Pickuptime = v.findViewById(R.id.t_pickup_time_value);


        searchlayl = v.findViewById(R.id.searchlayl);
        header_title_lay = v.findViewById(R.id.header_title_lay);


        imgV_cash = v.findViewById(R.id.imgV_cash);

        mov_cur_loc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null && myLocation != null) {
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, zoom));
                }
            }
        });

//        share_location.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), ContactShareActivity.class);
//                startActivity(intent);
//                //   share_location_alert();
//            }
//        });

        chatTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), ChatWebviewAct.class);
                in.putExtra("type", "2");
                in.putExtra("trip_id", SessionSave.getSession("trip_id", getActivity()));
                startActivityForResult(in, 101);
            }
        });
        infoLayout = v.findViewById(R.id.info_layout);
        final TextView mapInfoTxt = v.findViewById(R.id.mapinfo_txt);
        infoLayout.setVisibility(View.GONE);
        infoLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getChildFragmentManager();
                SplitFareStatusDialog splitFareDialog = new SplitFareStatusDialog();
                splitFareDialog.show(fm, "splitStatus");
            }
        });

        preferenceInfoAlert = new PreferenceInfoAlert();
        preference.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceInfoAlert.PreferenceInfo(getActivity(), preferenceData);
            }
        });


        order_details.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                orderDetailDialog();
            }
        });

    }


//    public void share_location_alert() {
//
//        Intent intent = new Intent(getContext(), ContactShareActivity.class);
//        startActivity(intent);
//
//        try {
//            BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
//            View sheetView = getActivity().getLayoutInflater().inflate(R.layout.shareride_alert_view, null);
//            mBottomSheetDialog.setContentView(sheetView);
//            mBottomSheetDialog.show();
//
//            Colorchange.ChangeColor((ViewGroup) sheetView, getActivity());
//            FontHelper.applyFont(getActivity(), sheetView.findViewById(R.id.rootlay));
//
//            final EditText contactEdt = sheetView.findViewById(R.id.contactEdt);
//
//            final Button submit = sheetView.findViewById(R.id.submit);
//            final Button cancel = sheetView.findViewById(R.id.cancel);
//
//
//
//            contactEdt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    if (isPermissionsGranted()) {
////                        openContactIntent();
////                    } else {
////                        makeRequest();
////                    }
//                    SessionSave.saveSession("sos_id", SessionSave.getSession(PASS_ID, getContext()), getContext());
//                    SessionSave.saveSession("user_type", "p", getContext());
//                    startActivity(new Intent(getContext(), SOSActivity.class));
//                }
//            });
//            submit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    // TODO Auto-generated method stub
//                    alertmDialog.dismiss();
//                }
//            });
//            cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    // TODO Auto-generated method stub
//                    alertmDialog.dismiss();
//                }
//            });
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void callAlert() {
        try {

            if (moveToChat.equals("")) {
                if (alert_msg != null && alert_msg.length() != 0) {

                    dialog1 = Utility.alert_view_dialog(getActivity(),
                            "" + NC.getResources().getString(R.string.message),
                            "" + alert_msg,
                            "" + NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, "");
                }
            } else {
                Intent in = new Intent(getContext(), ChatWebviewAct.class);
                in.putExtra("type", "2");
                in.putExtra("trip_id", SessionSave.getSession("trip_id", getActivity()));
                startActivityForResult(in, 101);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TaxiUtil.LocationResult) {
                if (data != null) {
                    Bundle result = data.getExtras();
                    String address = result.getString(BUNDLE_STOP_ADDRESS);
                    double latitude = result.getDouble(BUNDLE_STOP_LAT);
                    double longitude = result.getDouble(BUNDLE_STOP_LNG);

                    if (address != null && !address.isEmpty()) {
                        SessionSave.saveSession("Drop_location_push", address, getActivity());
                        SessionSave.saveSession("Drop_location_latitude", "" + latitude, getActivity());
                        SessionSave.saveSession("Drop_location_longitude", "" + longitude, getActivity());
                        dLatitude = String.valueOf(latitude);
                        dLongitude = String.valueOf(longitude);
                        dropLocation = new LatLng(latitude, longitude);
                        droploc = address;
                        txt_drop.setText(droploc);
                        createPickAndStopView();
                        map.clear();
                        if (mLastLocation != null || myLocation != null) {
                            if (myLocation == null)
                                myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());


                            if (dmarker != null) {
                                dmarker.remove();
                            }
                            try {
                                dmarker = map.addMarker(new MarkerOptions().position(myLocation).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.car_movement_icon)).title("" + NC.getResources().getString(R.string.driver_location)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            showDriverLocation(dmarker, myLocation, bearing);
                        }
                        map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_red)).title("" + NC.getResources().getString(R.string.droplocation)));
                        map.addMarker(new MarkerOptions().position(new LatLng(pickLocation.latitude, pickLocation.longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_green)).title("" + NC.getResources().getString(R.string.picklocation)));
                        mRoute.setUpPolyLine(map, getActivity(), pickLocation, dropLocation, null);
                    }
                }
            } else if (requestCode == 101) {
                alert_bundle = null;
                alert_msg = "";
                moveToChat = "";
            }
        }
    }

    /**
     * Method to create views dynamically if ArrayList<PlacesData> value not available (ie., Normal flow)
     * <p>
     * New ArrayList of PlacesData values created with pickup and drop(if available) and dynamic views created based on that ArrayList
     */
    private void createPickAndStopView() {
        ArrayList<PlacesData> pickUpDropList = new ArrayList<>();
        PlacesData pickUpData = new PlacesData(0, Double.parseDouble(pickuplat), Double.parseDouble(pickuplong), pickuploc, "", "", "0", "");
        pickUpDropList.add(pickUpData);
        if (droploc != null && !droploc.isEmpty() && dropLocation != null) {
            PlacesData dropData = new PlacesData((1 + new Random().nextInt()), dropLocation.latitude, dropLocation.longitude, droploc, "", "", "0", "");
            pickUpDropList.add(dropData);
        }
        stopList = pickUpDropList;
        if (getActivity() != null)
            pickUpDropLayout.setData(pickUpDropList);
    }

    /**
     * Method to parse stop list string to ArrayList<PlacesData> to create views dynamically
     *
     * @param path - String of ArrayList<PlacesData> values
     */
    private void parseStop(String path) {
        latLngArrayList = new ArrayList<>();
        Gson gson = new Gson();
        if (!path.equals("")) {
            Type type = new TypeToken<List<PlacesData>>() {
            }.getType();
            stopList = gson.fromJson(path, type);
            for (int i = 0; i < stopList.size(); i++) {
                PlacesData stopData = stopList.get(i);
                latLngArrayList.add(new LatLng(stopData.getLat(), stopData.getLng()));
            }

            if (getActivity() != null)
                pickUpDropLayout.setData(stopList);
        }
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {

        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GoogleApiAvailability.getInstance().isUserResolvableError(resultCode)) {
                GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                CToast.ShowToast(getActivity(), "getActivity() device is not supported.");
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Systems.out.println("ONGOINGFRAG--onStart");
        GetPassengerUpdate.setListener(this);
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else {
            buildGoogleApiClient();
            // mGoogleApiClient.connect();
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        }
    }


    // Slider menu used to move from one activity to another activity.

    public void alert_view(Context mContext, String title, String message, String success_txt, String failure_txt) {
        try {
            final View view = View.inflate(mContext, R.layout.alert_view, null);
            alertmDialog = new Dialog(mContext, R.style.dialogwinddow);
            alertmDialog.setContentView(view);
            alertmDialog.setCancelable(true);
            alertmDialog.findViewById(R.id.alert_id);
            alertmDialog.findViewById(R.id.alert_id);
            alertmDialog.show();
            final TextView title_text = alertmDialog.findViewById(R.id.title_text);
            final TextView message_text = alertmDialog.findViewById(R.id.message_text);
            final Button button_success = alertmDialog.findViewById(R.id.button_success);
            final Button button_failure = alertmDialog.findViewById(R.id.button_failure);
            button_failure.setVisibility(View.GONE);
            title_text.setText(title);
            message_text.setText(message);
            button_success.setText(success_txt);
            button_success.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    alertmDialog.dismiss();
                }
            });
            button_failure.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    alertmDialog.dismiss();
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        mapWrapperLayout.init(map, getPixelsFromDp(getActivity(), 39 + 20), false, null);
        map.getUiSettings().setCompassEnabled(true);
        try {
// Customise the styling of the base map using a JSON object defined
// in a raw resource file.
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.map_style));

            if (!success) {
                Systems.out.println("Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Systems.out.println("Can't find style. Error: ");
        }

        try {
            if (map != null && mLastLocation != null) {
                LatLng mycurrentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(mycurrentLocation, zoom));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void callGetTripDetail() {
        if (SessionSave.getSession("trip_id", getActivity()).equals("")) {
            Systems.out.println("Nan BackStatck check" + "BookTaxiHomePage homePage()3");
            ((MainHomeFragmentActivity) getActivity()).homePage();

        } else {
            mTripid = Integer.parseInt(SessionSave.getSession("trip_id", getActivity()));
            try {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (TaxiUtil.isOnline(getActivity())) {
                            try {
                                SessionSave.saveSessionInt(TaxiUtil.CURRENT_TRIP, mTripid, getActivity());
                                JSONObject j = new JSONObject();
                                j.put("trip_id", mTripid);
                                j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
                                new TripDetail("type=get_trip_detail", j);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("No Internet", "Please check the internet connection");
                        }
                    }
                }, 1000);

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

    }

    /**
     * getActivity() method used to call logout API.
     */
    public void calldriver(final String driverphone) {
        try {
            dialog1 = Utility.alert_view_dialog(getActivity(), "" + NC.getResources().getString(R.string.message),
                    "" + NC.getResources().getString(R.string.calldriver),
                    "" + NC.getResources().getString(R.string.call),
                    "" + NC.getResources().getString(R.string.cancel), true, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                dialog.dismiss();
                                // TODO Auto-generated method stub
                                if (driverphone.length() == 0)
                                    CToast.ShowToast(getActivity(), "Invalid phone number");
                                else {
                                    final Intent callIntent = new Intent(Intent.ACTION_VIEW);
                                    callIntent.setData(Uri.parse("tel:" + driverphone));
                                   /* if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                                            || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                                        dialog1 = Utility.alert_view_dialog(getActivity(), "",
                                                "" + NC.getResources().getString(R.string.str_phone),
                                                "" + NC.getResources().getString(R.string.yes),
                                                "" + NC.getResources().getString(R.string.no),
                                                true, new android.content.DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(android.content.DialogInterface dialog, int which) {
                                                        ActivityCompat.requestPermissions(getActivity(),
                                                                new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE},
                                                                MY_PERMISSIONS_REQUEST_CALL);
                                                    }
                                                }, new android.content.DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(android.content.DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }, "");
                                    } else {*/
                                    startActivity(callIntent);
//                                    }
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                                e.printStackTrace();
                            }
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dropVisible() {
        time_sep.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   /* Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + driverphone));
                    startActivity(callIntent);*/
                }
            }
            break;
            case MY_PERMISSIONS_REQUEST_GPS:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new OnGoingFrag()).commit();
                } else {
                    getActivity().finish();
                }
                break;
            case REQUEST_READ_PHONE_STATE:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        startSOSService();
                    }
                }
                return;
        }
    }

    /**
     * getActivity() method used to get the driver location and move the marker in driver current location
     * <p>
     * getActivity() method used to get the driver location and move the marker in driver current location
     * </p>
     */
    public void driverlocation(String pLatitude2, String pLongitude2, String dLatitude2, String dLongitude2, String driverlat, String driverlong) {
        // TODO Auto-generated method stub
        double dLat = 0, dLong = 0;
        double pLat = Double.parseDouble(pLatitude2);
        double pLong = Double.parseDouble(pLongitude2);
        try {
            map.clear();
            if (!dLatitude2.equals("") && !dLongitude2.equals("")) {
                if (Double.parseDouble(dLatitude2) != 0) {
                    dLat = Double.parseDouble(dLatitude2);
                    dLong = Double.parseDouble(dLongitude2);
                    map.addMarker(new MarkerOptions().position(new LatLng(dLat, dLong)).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_red)).title("" + NC.getResources().getString(R.string.droplocation)));
                }
            }
            if (mTravelstatus == 9) {
                map.addMarker(new MarkerOptions().position(new LatLng(pLat, pLong)).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_green)).title("" + NC.getResources().getString(R.string.picklocation)));
            } else
                map.addMarker(new MarkerOptions().position(new LatLng(pLat, pLong)).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_green)).title("" + NC.getResources().getString(R.string.picklocation)));
            myLocation = new LatLng(Double.parseDouble(driverlat), Double.parseDouble(driverlong));
            if (dmarker != null) {
                dmarker.remove();
            }

            dmarker = map.addMarker(new MarkerOptions().position(myLocation).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.car_movement_icon)).title("" + NC.getResources().getString(R.string.driver_location)));
            pickLocation = new LatLng(pLat, pLong);
            dropLocation = new LatLng(dLat, dLong);
            ondriverlat = driverlat;
            ondriverlng = driverlong;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * transit method used to change the driver location in google map while passenger traveling with driver
     * <p>
     * transit method used to change the driver location in google map while passenger traveling with driver
     * </p>
     */
    public void transit(String pickuplat, String pickuplong, String droplat, String droplong) {
        // TODO Auto-generated method stub
        double dLat = 0, dLong = 0;
        double pLat = 0, pLong = 0;
        try {
            if (pickuplat != null) {
                pLat = Double.parseDouble(pickuplat);
            }
            if (pickuplong != null) {
                pLong = Double.parseDouble(pickuplong);
            }
            map.clear();
            if (!droplat.equals("")) {
                dLat = Double.parseDouble(droplat);
                dLong = Double.parseDouble(droplong);
                map.addMarker(new MarkerOptions().position(new LatLng(dLat, dLong)).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_red)).title("" + NC.getResources().getString(R.string.droplocation)));
            }
            map.addMarker(new MarkerOptions().position(new LatLng(pLat, pLong)).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_green)).title("" + NC.getResources().getString(R.string.picklocation)));
            Latitude = mLastLocation.getLatitude();
            Longitude = mLastLocation.getLongitude();
            bearing = mLastLocation.getBearing();
            if (bearing >= 0)
                bearing = bearing + 90;
            else
                bearing = bearing - 90;
            myLocation = new LatLng(Latitude, Longitude);
            if (dmarker != null) {
                dmarker.remove();
            }
            dmarker = map.addMarker(new MarkerOptions().position(myLocation).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.car_movement_icon)).title("" + NC.getResources().getString(R.string.driver_location)));
            pickLocation = new LatLng(pLat, pLong);
            dropLocation = new LatLng(dLat, dLong);
            bearing = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        TaxiUtil.mActivitylist.remove(getActivity());
        stopLocationUpdates();
        if (dialog1 != null)
            Utility.closeDialog(dialog1);
       /* if (mTapTarget != null) {
            mTapTarget.dismiss();
        }*/
        super.onDestroy();
    }

    /**
     * getActivity() method used to calculate the estimate time between driver and pickup location.Gets the driver lat and lng for GETPASSENGERUPDATE API.
     */
    public void find_ETA(double P_latitude, double P_longitude, double d_latitude2, double d_longitude2) {
        try {
            Double distancekm;
            final PointF FromPoint = new PointF((float) P_latitude, (float) P_longitude);
            final PointF ToPoint = new PointF((float) d_latitude2, (float) d_longitude2);
            distancekm = getDistanceBetweenTwoPoints(FromPoint, ToPoint);
            Systems.out.println("DriverCall3" + distancekm + "__" + Double.parseDouble(SessionSave.getSession("taxi_speed", getActivity())));
            distancemtr = distancekm / 1000;
            estimate_time = "";
            if (distancemtr != 0) {
//                double time = distancemtr / Double.parseDouble(HomePage.speed);
                double time = distancemtr / Double.parseDouble(speed);
                Systems.out.println("DriverCall*" + time);
                time = time * 3600; // time duration in seconds
                double minutes = Math.floor(time / 60);
                time -= minutes * 60;
                Systems.out.println("DriverCall**" + time);
                if (minutes > 0) {
                    estimate_time = "" + Math.round(minutes);// + "M";
                } else {
                    estimate_time = "1";
                }
            } else {
                estimate_time = "1";
            }
            Systems.out.println("DriverCall2" + estimate_time);
            minfareTxt.setText("" + NC.getResources().getString(R.string.eta) + " :" + estimate_time + NC.getResources().getString(R.string.mins));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Starting the location updates
     */
    protected void startLocationUpdates() {
        if (isPermissionGranted()) {
            getGPS();
        }
    }

    public boolean isPermissionGranted() {
        if (getActivity() != null) {
            if ((ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                if ((ActivityCompat.shouldShowRequestPermissionRationale((getActivity()), android.Manifest.permission.ACCESS_FINE_LOCATION)) && (ActivityCompat.shouldShowRequestPermissionRationale((getActivity()), android.Manifest.permission.ACCESS_COARSE_LOCATION))) {


                    dialog1 = Utility.alert_view_dialog(getActivity(), "",
                            "" + NC.getResources().getString(R.string.str_loc),
                            "" + NC.getResources().getString(R.string.yes),
                            "" + NC.getResources().getString(R.string.no),
                            true, new android.content.DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                            MY_PERMISSIONS_REQUEST_GPS);
                                }
                            }, new android.content.DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, "");
                } else {

                    dialog1 = Utility.alert_view_dialog(getActivity(), "",
                            "" + NC.getResources().getString(R.string.str_loc),
                            "" + NC.getResources().getString(R.string.yes),
                            "" + NC.getResources().getString(R.string.no),
                            true, new android.content.DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                            MY_PERMISSIONS_REQUEST_GPS);
                                }
                            }, new android.content.DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, "");
                }
                return false;
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * this method is used to get gps
     */

    private void getGPS() {
        try {
            if (mGoogleApiClient != null && mLocationRequest != null && mGoogleApiClient.isConnected())
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {

        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        // Once connected with google api, get the location
        try {
            if (isPermissionGranted()) {
                if (mGoogleApiClient.isConnected())
                    startLocationUpdates();
                else {
                    mGoogleApiClient.connect();
                }
                if ((ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale((getActivity()), android.Manifest.permission.ACCESS_FINE_LOCATION)) && (ActivityCompat.shouldShowRequestPermissionRationale((getActivity()), android.Manifest.permission.ACCESS_COARSE_LOCATION))) {


                        dialog1 = Utility.alert_view_dialog(getActivity(), "",
                                "" + NC.getResources().getString(R.string.str_loc),
                                "" + NC.getResources().getString(R.string.yes),
                                "" + NC.getResources().getString(R.string.no),
                                true, new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(getActivity(),
                                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                                MY_PERMISSIONS_REQUEST_GPS);
                                    }
                                }, new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }, "");
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        if (mLastLocation != null) {
                            LatLng mycurrentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                            if (!SessionSave.getSession("trip_id", getActivity()).equals("")) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(mycurrentLocation, zoom));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {

        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;
    }

    /**
     * this methos is used to animate the map as the map moves
     */

    public void moveCameraToDriverLoc(Double Latitude, Double Longtitude) {
        LatLng LL = new LatLng(Latitude, Longtitude);
        Systems.out.println("curentDrivertrip___^^^^^" + mTravelstatus + "__" + mTripid);
        if (mLastLocation != null && Latitude != 0.0 && mTravelstatus == 9) {
            if (SessionSave.getSession(IS_BUISNESS_KEY, getActivity(), false)) {
                countDriverLoc += 1;
                if (countDriverLoc == 4) {
                    countDriverLoc = 0;
                    new FindApproxDistance(OnGoingFrag.this).getDistance(getActivity(), mLastLocation.getLatitude(), mLastLocation.getLongitude(),
                            Latitude, Longtitude, 0);
                }
            } else {
                find_ETA(mLastLocation.getLatitude(), mLastLocation.getLongitude(), LL.latitude, LL.longitude);
            }
        }
        if (map != null) {
            lat1 = Double.parseDouble(SessionSave.getSession("driver_latitute", getActivity()));
            log1 = Double.parseDouble(SessionSave.getSession("driver_longtitute", getActivity()));
            LatLng myLocation1 = new LatLng(lat1, log1);

            if (mapWrapperLayout != null && !mapWrapperLayout.isShown())
                mapWrapperLayout.setVisibility(View.VISIBLE);
            if (!animLocation) {
                listPoint.add(myLocation1);
            } else {
                savedpoint.add(myLocation1);
            }
            if (previous_latitude != 0 && previous_longitude != 0) {
                Log.d("carrrrrr lattitude", "" + lat1 + log1 + previous_latitude + previous_longitude);
                double distance = bearing(lat1, log1, previous_latitude, previous_longitude);
                double bear = bearing(lat1, log1, previous_latitude, previous_longitude);
                Systems.out.println("myLocation listPoint " + distance + " Last postion " + bear);

                if (bear != 360.0) {
                    bearing = (float) bear;
                    if (bearing >= 0.0)
                        bearing = bearing + 90;
                    else
                        bearing = bearing - 180;
                }

            }
            if (dmarker == null) {
                try {
                    if (myLocation != null)
                        dmarker = map.addMarker(new MarkerOptions().position(myLocation).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.car_movement_icon)).title("" + NC.getResources().getString(R.string.driver_location)));
                    else
                        dmarker = map.addMarker(new MarkerOptions().position(LL).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.car_movement_icon)).title("" + NC.getResources().getString(R.string.driver_location)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            showDriverLocation(dmarker, new LatLng(Latitude, Longtitude), bearing);
            if (lat1 != previous_latitude && log1 != previous_longitude) {
                previous_latitude = lat1;
                previous_longitude = log1;
            }
        }

    }

    private void showDriverLocation(Marker dmarker, LatLng locates, float bearing) {
        if (MapWrapperLayout.ismMapIsTouched()) {
            if (map != null && locates != null) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locates.latitude, locates.longitude), zoom));
            }
        }
        Systems.out.println("marker animating" + dmarker.getPosition() + "__" + locates + "__" + bearing);
        CarMovementAnimation.getInstance().animateMarker(dmarker, locates, bearing);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStop() {
        Systems.out.println("Nan BackStatck check" + " onStop OngoingFrag");
        ((MainHomeFragmentActivity) getActivity()).disableSlide();
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        /*if (mTapTarget != null) {
            mTapTarget.dismiss();
        }*/
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.GONE);
        ((MainHomeFragmentActivity) getActivity()).disableSlide();
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        if (SessionSave.getSession("Lang", getActivity()).equals("ar"))
            ((MainHomeFragmentActivity) getActivity()).left_icon.setRotation(180);
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.GONE);
        Systems.out.println("Nan BackStatck check" + "BookTaxiHomePage homePage() callGetTripDetail onResume");
        callGetTripDetail();

        tvEditPickUpDrop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTravelstatus == 2) {
//                    if (SessionSave.getSession(TaxiUtil.IS_STOP_ENABLED, getActivity(), false) && (tripType != 3 && tripType != 2)) {
                    Intent intent = new Intent(getActivity(), AddStopActivity.class);
                    intent.putParcelableArrayListExtra(BUNDLE_PICKUP_DROP_ADDRESS, stopList);
                    intent.putExtra(IS_FROM_ONGOING, true);
                    if (!SessionSave.getSession(TaxiUtil.IS_STOP_ENABLED, getActivity(), false) || tripType == 3 || tripType == 2) {
                        intent.putExtra(STOP_SLAB_SIZE, 1);
                    }
                    startActivityForResult(intent, TaxiUtil.LocationResult);
//                    } else {
//                        Intent intent = new Intent(getActivity(), LocationSearchActivity.class);
//                        intent.putExtra(BUNDLE_STOP_ID, "" + v.getId());
//                        startActivityForResult(intent, TaxiUtil.LocationResult);
//                    }
                }
            }
        });

        txt_drop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTravelstatus == 2) {
                    Intent intent = new Intent(getActivity(), AddStopActivity.class);
                    intent.putParcelableArrayListExtra(BUNDLE_PICKUP_DROP_ADDRESS, stopList);
                    intent.putExtra(IS_FROM_ONGOING, true);
                    if (!SessionSave.getSession(TaxiUtil.IS_STOP_ENABLED, getActivity(), false) || tripType == 3 || tripType == 2) {
                        intent.putExtra(STOP_SLAB_SIZE, 1);
                    }
                    startActivityForResult(intent, TaxiUtil.LocationResult);

                }
            }
        });
    }

    @Override
    public void onDistanceCalled(Double time, int type, int requestedType) {
        double E_time = 0;
        estimate_time = "";

        if (time != null) {
            if (time <= 0)
                E_time = 1.0;
            else
                E_time = time;
        }
        int eTime = (int) E_time;
        estimate_time = String.valueOf(eTime);
        minfareTxt.setText("" + NC.getResources().getString(R.string.eta) + " :" + estimate_time + NC.getResources().getString(R.string.mins));

    }


    @Override
    public void updateGetPassUpdate(int tripid, String message) {
        Systems.out.println("updateGetPassUpdate----" + tripid + "___" + SessionSave.getSessionInt(TaxiUtil.CURRENT_TRIP, getActivity()));
        if (tripid == SessionSave.getSessionInt(TaxiUtil.CURRENT_TRIP, getActivity())) {
            Systems.out.println("updateGetPassUpdate*****2");
            Systems.out.println("Nan BackStatck check" + "BookTaxiHomePage homePage() callGetTripDetail");
            callGetTripDetail();

            dialog1 = Utility.alert_view_dialog(getActivity(), "", message, NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }, null, "");
        }
    }

    @Override
    public void updateTips(JSONObject jsonObject) {
        try {
            if (jsonObject.has("tips_enable") && jsonObject.getInt("tips_enable") == 1) {
                show_tips = jsonObject.optInt("tips_notification", 1);
                tips_1 = jsonObject.getString("tips_1");
                tips_2 = jsonObject.getString("tips_2");
                tips_3 = jsonObject.getString("tips_3");
                if (show_tips == 0) {
                    passengerTips();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void startSOSService() {
        SessionSave.saveSession("sos_id", SessionSave.getSession(PASS_ID, getActivity()), getActivity());
        SessionSave.saveSession("user_type", "p", getActivity());


        getActivity().startService(new Intent(getActivity(), SOSService.class));
    }

    public Double bearing(Double lat1, Double long1, Double lat2, Double long2) {
        Double brng = Math.atan2(lat1 - lat2, long1 - long2);
        brng = brng * (180 / Math.PI);
        brng = (brng + 360) % 360;
        brng = 360 - brng;
        return brng;
    }

    // Speed Calculation
    private double roundDecimal(double value, final int decimalPlace) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        value = bd.doubleValue();
        return value;
    }

    private double convertSpeed(double speed) {
        return ((speed * 3600) * 0.001);
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
        private String driverimage;
        private String drivername;
        private String taxino;
        private String pickuptime;
        private String driverphone = "";
        private String driverlat;
        private String driverlong;
        private String p_taxi_speed;
        private String message = "";
        private String cancellationFee = "";

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
                    Systems.out.println("nagadriver-" + result);

                    if (json.getInt("status") == 1 && json.getJSONObject("detail").getInt("travel_status") != 0) {

                        if (json.getJSONObject("detail").has(TaxiUtil.CANCELLATION_FARE_APPLICABLE)) {
                            SessionSave.saveSession(TaxiUtil.CANCELLATION_FARE_APPLICABLE, json.getJSONObject("detail").getString(TaxiUtil.CANCELLATION_FARE_APPLICABLE).equals("1"), requireContext());
                        } else
                            SessionSave.saveSession(TaxiUtil.CANCELLATION_FARE_APPLICABLE, false, requireContext());

                        mTravelstatus = Integer.parseInt(json.getJSONObject("detail").getString("travel_status"));
                        tripType = Integer.parseInt(json.getJSONObject("detail").getString("trip_type"));

                        if (mTravelstatus == 2 && tripType != 3)
                            tvEditPickUpDrop.setVisibility(View.VISIBLE);
                        else if (tripType == 3) {
                            tvEditPickUpDrop.setVisibility(View.GONE);
                        }

                        driverimage = json.getJSONObject("detail").getString("driver_image");
                        drivername = json.getJSONObject("detail").getString("driver_name");
                        taxino = json.getJSONObject("detail").getString("taxi_number");
                        pickuploc = json.getJSONObject("detail").getString("current_location");
                        txt_pickup.setText(pickuploc);
                        pickuplat = json.getJSONObject("detail").getString("pickup_latitude");
                        TaxiUtil.p_lat = Double.parseDouble(pickuplat);
                        pickuplong = json.getJSONObject("detail").getString("pickup_longitude");
                        TaxiUtil.p_lng = Double.parseDouble(pickuplong);
                        SessionSave.saveSession(CREDIT_CARD, json.getJSONObject("detail").getString("credit_card_status"), getActivity());
                        droploc = json.getJSONObject("detail").getString("drop_location");
                        txt_drop.setText(droploc);
                        droplat = json.getJSONObject("detail").getString("drop_latitude");
                        droplong = json.getJSONObject("detail").getString("drop_longitude");
                        pickuptime = json.getJSONObject("detail").getString("pickup_time");
                        driverphone = json.getJSONObject("detail").getString("driver_phone");
                        OnGoingFrag.this.driverphone = json.getJSONObject("detail").getString("driver_phone");
                        driverlat = json.getJSONObject("detail").getString("driver_latitute");
                        driverlong = json.getJSONObject("detail").getString("driver_longtitute");
                        timetoreach = json.getJSONObject("detail").getString("time_to_reach_passen");
                        p_image_name = json.getJSONObject("detail").getString("p_image_name");
                        SessionSave.saveSession("p_image_name", p_image_name, getContext());
                        passengerGraceTime = json.getJSONObject("detail").getString("passenger_grace_time");
                        SessionSave.saveSession(TaxiUtil.PASSENGER_GRACE_TIME, passengerGraceTime, getActivity());
                        routePath = json.getJSONObject("detail").getString("route_path");

                        cancellationFee = json.getJSONObject("detail").getString("cancellation_fee");

                        String approx_fare = json.getJSONObject("detail").getString("approx_fare");


                        double time_to_reach;
                        if (timetoreach.equals("")) {
                            time_to_reach = 0.0;
                        } else
                            time_to_reach = Double.parseDouble(timetoreach);

                        Log.d("String ", " Round OFF" + String.format("%.2f", time_to_reach));
                        p_taxi_speed = json.getJSONObject("detail").getString("taxi_speed");
                        if (!json.getJSONObject("detail").getBoolean("is_primary"))
                            callbottom_lay.setVisibility(View.GONE);
                        else if (json.getJSONObject("detail").getInt("isSplit_fare") == 1)
                            infoLayout.setVisibility(View.VISIBLE);
                        pickLocation = new LatLng(Double.parseDouble(pickuplat), Double.parseDouble(pickuplong));

                        if (droplat != null && !droplat.isEmpty() && !droplat.equalsIgnoreCase("0.0") && !droplat.equalsIgnoreCase("0"))
                            dropLocation = new LatLng(Double.parseDouble(droplat), Double.parseDouble(droplong));

                        ondriverlat = driverlat;
                        ondriverlng = driverlong;

                        if (drivername.length() > 0) {
                            drivername = Character.toUpperCase(drivername.charAt(0)) + drivername.substring(1);
                        }
                        if (p_taxi_speed.length() > 0 && !p_taxi_speed.equals(null)) {
                            SessionSave.saveSession("taxi_speed", p_taxi_speed, getActivity());
                        }
                        txt_estimate.setText(getResources().getString(R.string.estimate_fare) + "\t" + SessionSave.getSession("Currency", getActivity()));
                        txt_estimate.setText(getResources().getString(R.string.estimate_fare) + "\t" + SessionSave.getSession("Currency", getActivity()) + approx_fare);

                        pLatitude = pickuplat;
                        pLongitude = pickuplong;
                        dLatitude = droplat;
                        dLongitude = droplong;
                        JSONArray stops = null;
                        if (json.getJSONObject("detail").has("stops"))
                            stops = json.getJSONObject("detail").getJSONArray("stops");
                        if (SessionSave.getSession(TaxiUtil.IS_STOP_ENABLED, getActivity(), false) && stops != null && stops.length() > 0)
                            parseStop(stops.toString());
                        else
                            createPickAndStopView();

                        preferenceData.clear();
                        if (json.getJSONObject("detail").has("preferences") && !TextUtils.isEmpty(json.getJSONObject("detail").getString("preferences")) && json.getJSONObject("detail").getJSONArray("preferences").length() > 0) {
                            JSONArray jsonArray = json.getJSONObject("detail").getJSONArray("preferences");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                AddonsData data = new AddonsData();
                                data.preference_id = jsonArray.getJSONObject(i).getInt("preference_id");
                                data.preference_name = jsonArray.getJSONObject(i).getString("preference_name");
                                data.preference_fare = jsonArray.getJSONObject(i).getString("preference_fare");
                                preferenceData.add(data);
                            }
                            preference.setVisibility(View.VISIBLE);
                        } else {
                            preference.setVisibility(View.GONE);
                        }

                        if (json.getJSONObject("detail").has("service_id") && json.getJSONObject("detail").getString("service_id").equals("2")) {
                            order_details.setVisibility(View.VISIBLE);
                            product_name = json.getJSONObject("detail").has("product_name") ? json.getJSONObject("detail").getString("product_name") : "";
                            product_weight = json.getJSONObject("detail").has("product_weight") ? json.getJSONObject("detail").getString("product_weight") : "";
                            product_size = json.getJSONObject("detail").has("product_size") ? json.getJSONObject("detail").getString("product_size") : "";
                            delivery_person_name = json.getJSONObject("detail").has("delivery_person_name") ? json.getJSONObject("detail").getString("delivery_person_name") : "";
                            delivery_phone_number = json.getJSONObject("detail").has("delivery_phone_number") ? json.getJSONObject("detail").getString("delivery_phone_number") : "";
                            delivery_date_time = json.getJSONObject("detail").has("delivery_date_time") ? json.getJSONObject("detail").getString("delivery_date_time") : "";
                            delivery_notes = json.getJSONObject("detail").has("delivery_notes") ? json.getJSONObject("detail").getString("delivery_notes") : "";
                        } else {
                            order_details.setVisibility(View.GONE);
                        }


                        if (mTravelstatus == 1) {
                            map_lay.setVisibility(View.GONE);
//                            Intent intent = new Intent(getActivity(), GetPassengerUpdate.class);
//                            getActivity().stopService(intent);
                            Transitlay.setVisibility(View.GONE);
                            T_Pickuptime.setVisibility(View.GONE);

                            ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.intransit));
                        } else if (mTravelstatus == 2) {
                            estimate_fare.setVisibility(View.GONE);
                            Driverlay.setVisibility(View.VISIBLE);
                            mov_cur_loc.setVisibility(View.VISIBLE);
                            share_location.setVisibility(View.GONE);
                            callbottom_lay.setVisibility(View.GONE);
//                            contact_layout.setVisibility(View.INVISIBLE);
                            Driver_name.setText(drivername);
                            if (SessionSave.getSession(TaxiUtil.sosEnable, getActivity(), false)
                                    && !SessionSave.getSession("trip_id", getActivity()).equals("")) {
                                ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.VISIBLE);
                                btn_emergency.setVisibility(View.VISIBLE);
                                ((MainHomeFragmentActivity) getActivity()).call_image.setVisibility(View.GONE);
                            } else {
                                btn_emergency.setVisibility(View.GONE);
                                ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.GONE);
                                ((MainHomeFragmentActivity) getActivity()).call_image.setVisibility(View.GONE);
                            }

                            ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.intransit));


                            mHandler.sendEmptyMessage(2);
                            Transitlay.setVisibility(View.GONE);
                            if (!droploc.trim().equals("")) {
                                dropVisible();
                            } else {
                                ViewGroup.LayoutParams par = searchlayl.getLayoutParams();
                                par.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            }

                            T_Pickuptime.setVisibility(View.VISIBLE);
                            call_icon.setVisibility(View.VISIBLE);

                            T_Pickuptime.setText(NC.getString(R.string.picktime) + " " + pickuptime);
                            Transitlay.post(new Runnable() {
                                @Override
                                public void run() {
                                    layoutheight = Transitlay.getHeight();
                                    map.setPadding(0, layoutheight, 0, 0);
                                }
                            });
                            ((MainHomeFragmentActivity) getActivity()).call_image.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // calldriver(driverphone);
                                    try {
                                        SessionSave.saveSessionInt(TaxiUtil.CURRENT_TRIP, mTripid, getActivity());
                                        JSONObject j = new JSONObject();
                                        j.put("trip_id", mTripid);
                                        new getMaskedPhoneNumber("type=get_twilio_number", j);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            call_icon.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // calldriver(driverphone);
                                    try {
                                        SessionSave.saveSessionInt(TaxiUtil.CURRENT_TRIP, mTripid, getActivity());
                                        JSONObject j = new JSONObject();
                                        j.put("trip_id", mTripid);
                                        new getMaskedPhoneNumber("type=get_twilio_number", j);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            if (SessionSave.getSession(TaxiUtil.IS_STOP_ENABLED, getActivity(), false)) {
                                if (routePath != null && !routePath.isEmpty() && !routePath.equalsIgnoreCase("0")) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mRoute.drawRouteFromPolyline(map, routePath, latLngArrayList);
                                        }
                                    }, 300);
                                } else {
                                    if (pickLocation != null && pickLocation.latitude != 0.0 && dropLocation != null && dropLocation.latitude != 0.0)
                                        mRoute.setUpPolyLineWithColor(map, getActivity(), pickLocation, dropLocation, latLngArrayList, ContextCompat.getColor(getActivity(), R.color.colorFontGreyLight));
                                }
                            } else {
                                if (pickLocation != null && pickLocation.latitude != 0.0 && dropLocation != null && dropLocation.latitude != 0.0)
                                    mRoute.setUpPolyLine(map, getActivity(), pickLocation, dropLocation, null);
                            }
                        } else if (mTravelstatus == 9) {
                            apptimelay.setVisibility(View.VISIBLE);
                            String trip_id = json.getJSONObject("detail").getString("trip_id");
                          /*  if (SessionSave.getSession(TaxiUtil.sosEnable, getActivity(), false)) {
                                if (!SessionSave.getSession("Emergency_trip_id", getActivity()).equals(trip_id)) {
                                    mTapTarget = new MaterialTapTargetPrompt.Builder(getActivity())
                                            .setTarget(((MainHomeFragmentActivity) getActivity()).txt_emergency)
                                            .setBackgroundColour(getResources().getColor(R.color.semii_transparent))
                                            .setPrimaryText(getActivity().getResources().getString(R.string.tab_emer))
                                            .setSecondaryText(getActivity().getResources().getString(R.string.got_it))
                                            .setSecondaryTextColour(getResources().getColor(R.color.pastbookingcashtext))
                                            .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                                @Override
                                                public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                                    SessionSave.saveSession("Emergency_trip_id", trip_id, getActivity());
                                                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                                        // User has pressed the prompt target
                                                    }
                                                }
                                            })
                                            .show();
                                }
                            }*/
                        }

                        if (mTravelstatus == 9 || mTravelstatus == 3 || mTravelstatus == 2) {

                            if (SessionSave.getSession(TaxiUtil.sosEnable, getActivity(), false)
                                    && !SessionSave.getSession("trip_id", getActivity()).equals("")) {
                                ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.VISIBLE);
                                btn_emergency.setVisibility(View.VISIBLE);
                            } else {
                                ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.GONE);
                                btn_emergency.setVisibility(View.GONE);
                            }
                            ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.Confirmation));
                            if (mTravelstatus == 9)
                                mRoute.setUpPolyLine(map, getActivity(), new LatLng(Double.parseDouble(ondriverlat), Double.parseDouble(ondriverlng)), pickLocation, null);
                            else {
                                if (routePath != null && !routePath.isEmpty() && !routePath.equalsIgnoreCase("0")) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mRoute.drawRouteFromPolyline(map, routePath, latLngArrayList);
                                        }
                                    }, 300);
                                }
                            }
                            Driverlay.setVisibility(View.VISIBLE);
                            Transitlay.setVisibility(View.GONE);
                            Driver_name.setText(drivername);
                            if (!droploc.trim().equals("")) {
                                dropVisible();
                                estimate_fare.setVisibility(View.VISIBLE);
                            }
                            minfareTxt.setText(String.format("%.2f", time_to_reach) + " " + NC.getResources().getString(R.string.mins));
//
//                            if (driverimage != null && driverimage.length() > 0) {
//                                Picasso.get().load(driverimage).placeholder(getResources().getDrawable(R.drawable.loadingimage)).error(getResources().getDrawable(R.drawable.profileimage)).into(Driver_img);
//                            }

                            if (driverimage != null && driverimage.length() > 0) {
                                Picasso.get().load(driverimage).error(R.drawable.loadingimage).placeholder(R.drawable.loadingimage).into(Driver_img);
                            } else {
                                if (drivername != "") {
                                    ProfileImageSetupClass.setupProfileImage(
                                            drivername, Driver_img
                                    );
                                } else {
                                    Picasso.get().load(R.drawable.loadingimage).into(Driver_img);
                                }
                            }

                            String rating1 = json.getJSONObject("detail").getString("driver_rating");
                            rating_count.setText(rating1);
                            Taxino_txt.setText(taxino);
                            Driverlay.post(new Runnable() {
                                @Override
                                public void run() {
                                    layoutheight = Driverlay.getHeight();
                                }
                            });
//                            if (rating1 == 0) {
//                                Rating.setImageResource(R.drawable.star6);
//                            }
//                            if (rating1 == 1) {
//                                Rating.setImageResource(R.drawable.star1);
//                            }
//                            if (rating1 == 2) {
//                                Rating.setImageResource(R.drawable.star2);
//                            }
//                            if (rating1 == 3) {
//                                Rating.setImageResource(R.drawable.star3);
//                            }
//                            if (rating1 == 4) {
//                                Rating.setImageResource(R.drawable.star4);
//                            }
//                            if (rating1 == 5) {
//                                Rating.setImageResource(R.drawable.star5);
//                            }
                            lay_call_cancel.setOnClickListener(new OnClickListener() {
                                private Dialog mDialog;

                                @Override
                                public void onClick(View v) {

                                    dialog1 = Utility.alert_view_dialog(getActivity(),
                                            "" + NC.getResources().getString(R.string.cancel_in_going_trip),
                                            "" + NC.getResources().getString(R.string.cancel_in_going_trip),
                                            "" + NC.getResources().getString(R.string.yes), "" + NC.getResources().getString(R.string.no), true, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    FragmentManager fm1 = getActivity().getSupportFragmentManager();
                                                    Bundle bb = new Bundle();
                                                    bb.putString("trip_id", SessionSave.getSession("trip_id", getActivity()));
                                                    bb.putString("Cancel_fee", cancellationFee);
                                                    ReasonListFrag rl = new ReasonListFrag();
                                                    rl.setArguments(bb);
                                                    fm1.beginTransaction().add(rl, "").commitAllowingStateLoss();
                                                }
                                            }, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }, "");


                                }
                            });
                            lay_call.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //calldriver(driverphone);
                                    try {
                                        SessionSave.saveSessionInt(TaxiUtil.CURRENT_TRIP, mTripid, getActivity());
                                        JSONObject j = new JSONObject();
                                        j.put("trip_id", mTripid);
                                        new getMaskedPhoneNumber("type=get_twilio_number", j);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            if (mTravelstatus == 2) {
                                calltxt.setVisibility(View.GONE);
                                lay_call.setVisibility(View.VISIBLE);
                                lay_call_cancel.setVisibility(View.INVISIBLE);
                                minfareTxt.setVisibility(View.INVISIBLE);
                            }
                        }
                        if (mTravelstatus == 3) {
                            minfareTxt.setVisibility(View.INVISIBLE);
                        }
                        if (mTravelstatus == 5) {
                            estimate_fare.setVisibility(View.GONE);
                            message = json.getString("message");
                            map_lay.setVisibility(View.GONE);
                            bottom_sheet.setVisibility(View.GONE);
                            lay_no_data.setVisibility(View.VISIBLE);
                            imgV_cash.setVisibility(View.VISIBLE);
                            header_title_lay.setVisibility(View.GONE);
                            String completeTripresponse = String.valueOf(json.getJSONObject("complete_trip"));

                            if (SessionSave.getSession(TaxiUtil.sosEnable, getActivity(), false)
                                    && !SessionSave.getSession("trip_id", getActivity()).equals("")) {
                                ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.VISIBLE);
                                btn_emergency.setVisibility(View.VISIBLE);
                            } else {
                                ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.GONE);
                                btn_emergency.setVisibility(View.GONE);
                            }
                            ((MainHomeFragmentActivity) getActivity()).call_image.setVisibility(View.GONE);
                            ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.payment_complete));
                            book_taxi.setVisibility(View.GONE);
                            nodataTxt.setText("" + message == null ? getString(R.string.payment_complete) : message);
                            ((MainHomeFragmentActivity) getActivity()).redirectFareScreen(completeTripresponse);


                        }
                        if (mTravelstatus == 4) {
                            estimate_fare.setVisibility(View.GONE);
                            message = json.getString("message");
                            map_lay.setVisibility(View.GONE);
                            lay_no_data.setVisibility(View.VISIBLE);
                            bottom_sheet.setVisibility(View.GONE);
                            ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.Trip_in_progress));
                            nodataTxt.setText("" + message == null ? getString(R.string.try_again) : message);
                        }
                        driverlocation(pLatitude, pLongitude, dLatitude, dLongitude, driverlat, driverlong);


                    } else {
                        message = json.getString("message");
                        map_lay.setVisibility(View.GONE);
                        lay_no_data.setVisibility(View.VISIBLE);
                        bottom_sheet.setVisibility(View.GONE);
                        nodataTxt.setText("" + message == null ? getString(R.string.try_again) : message);
                    }

                    callAlert();
                } catch (Exception e) {
                    e.printStackTrace();
                    map_lay.setVisibility(View.GONE);
                    lay_no_data.setVisibility(View.VISIBLE);
                    bottom_sheet.setVisibility(View.GONE);
                }
            } else {
                map_lay.setVisibility(View.GONE);
                lay_no_data.setVisibility(View.VISIBLE);
                bottom_sheet.setVisibility(View.GONE);
                nodataTxt.setText("" + message == null ? getString(R.string.try_again) : message);
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            CToast.ShowToast(getActivity(), getString(R.string.server_con_error));
                        }
                    });
            }
        }
    }


    private class getMaskedPhoneNumber implements DriverAPIResult {


        public getMaskedPhoneNumber(final String url, JSONObject data) {
            try {
                if (TaxiUtil.isOnline(getActivity())) {
                    new DriverAPIService_Retrofit_JSON(getActivity(), this, data, false).execute(url);
                } else {
                    CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.check_net_connection));
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
                                CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.invalid_mobile_number));
                            } else {
                                calldriver(call_masking_ph_no);
                            }
                        }
                    } else {
                        if (json.has("message")) {
                            CToast.ShowToast(getActivity(), json.getString("message"));
                        }
                    }
                }
            } catch (final Exception e) {
                // TODO: handle exception
                e.printStackTrace();

            }
        }
    }

    public void passengerTips() {
        try {
            if (!mcancelDialog.isShowing()) {
                View view = getLayoutInflater().inflate(R.layout.passenger_tips_sheet, null);
                mcancelDialog.setContentView(view);
                mcancelDialog.setCancelable(true);
                mcancelDialog.setCanceledOnTouchOutside(false);
                mcancelDialog.show();

                final Button button_success = mcancelDialog.findViewById(R.id.okbtn);
                final Button button_failure = mcancelDialog.findViewById(R.id.cancelbtn);
                final TextView tips_1_tv = mcancelDialog.findViewById(R.id.tips_1);
                final TextView tips_2_tv = mcancelDialog.findViewById(R.id.tips_2);
                final TextView tips_3_tv = mcancelDialog.findViewById(R.id.tips_3);
                final EditText tips_edt = mcancelDialog.findViewById(R.id.tips_edt);

                tips_1_tv.setText(SessionSave.getSession("Currency", getActivity()) + tips_1);
                tips_2_tv.setText(SessionSave.getSession("Currency", getActivity()) + tips_2);
                tips_3_tv.setText(SessionSave.getSession("Currency", getActivity()) + tips_3);

                tips_3_tv.setSelected(true);
                tips_3_tv.setTextColor(getResources().getColor(R.color.white));
                tips_1_tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tips_edt.setText(tips_1);

                        tips_1_tv.setSelected(true);
                        tips_1_tv.setTextColor(getResources().getColor(R.color.white));
                        tips_2_tv.setSelected(false);
                        tips_2_tv.setTextColor(getResources().getColor(R.color.block));
                        tips_3_tv.setSelected(false);
                        tips_3_tv.setTextColor(getResources().getColor(R.color.block));
                    }
                });

                tips_2_tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tips_edt.setText(tips_2);

                        tips_2_tv.setSelected(true);
                        tips_2_tv.setTextColor(getResources().getColor(R.color.white));
                        tips_3_tv.setSelected(false);
                        tips_3_tv.setTextColor(getResources().getColor(R.color.block));
                        tips_1_tv.setSelected(false);
                        tips_1_tv.setTextColor(getResources().getColor(R.color.block));
                    }
                });

                tips_3_tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tips_edt.setText(tips_3);

                        tips_3_tv.setSelected(true);
                        tips_3_tv.setTextColor(getResources().getColor(R.color.white));
                        tips_2_tv.setSelected(false);
                        tips_2_tv.setTextColor(getResources().getColor(R.color.block));
                        tips_1_tv.setSelected(false);
                        tips_1_tv.setTextColor(getResources().getColor(R.color.block));
                    }
                });


                button_success.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        try {
                            tips = tips_edt.getText().toString();
                            if (!TextUtils.isEmpty(tips) && Double.parseDouble(tips) > 0) {
                                JSONObject j = new JSONObject();
                                j.put("trip_id", SessionSave.getSession("trip_id", getActivity()));
                                j.put("tips_amount", tips);
                                final String add_tips = "type=add_tips";
                                new TipsApi(add_tips, j);
                            } else {
                                CToast.ShowToast(getActivity(), NC.getString(R.string.tips_error));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


                button_failure.setVisibility(View.VISIBLE);
                button_failure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        try {
                            JSONObject j = new JSONObject();
                            j.put("trip_id", SessionSave.getSession("trip_id", getActivity()));
                            j.put("tips_amount", "0");
                            final String add_tips = "type=add_tips";
                            new TipsApi(add_tips, j);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private class TipsApi implements APIResult {


        public TipsApi(final String url, JSONObject data) {
            try {
                if (TaxiUtil.isOnline(getActivity())) {
                    new APIService_Retrofit_JSON(getActivity(), this, data, false).execute(url);
                } else {
                    CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.check_net_connection));
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
                        if (mcancelDialog != null && mcancelDialog.isShowing()) {
                            mcancelDialog.dismiss();
                        }
                    } else {
                        if (json.has("message")) {
                            CToast.ShowToast(getActivity(), json.getString("message"));
                        }
                    }
                }
            } catch (final Exception e) {
                // TODO: handle exception
                e.printStackTrace();

            }
        }
    }


    private void orderDetailDialog() {
       /* BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.order_detail_dialog, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();*/

        final View view = View.inflate(getActivity(), R.layout.order_detail_dialog, null);
        orderDialog = new Dialog(getActivity(), R.style.dialogAnimation);
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

        close_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                orderDialog.dismiss();

            }
        });


    }


}