//package com.Taximobility.fragments;
//
//import android.Manifest;
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.ValueAnimator;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.Point;
//import android.graphics.Typeface;
//import android.graphics.drawable.AnimatedVectorDrawable;
//import android.location.Location;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.view.ViewCompat;
//import android.text.InputFilter;
//import android.text.InputType;
//import android.text.TextUtils;
//import android.text.format.Time;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.util.SparseArray;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewTreeObserver;
//import android.view.animation.LinearInterpolator;
//import android.view.inputmethod.EditorInfo;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.HorizontalScrollView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.TimePicker;
//
//import com.Taximobility.BuildConfig;
//import com.Taximobility.ContinousRequest;
//import com.Taximobility.MainActivity;
//import com.Taximobility.MainHomeFragmentActivity;
//import com.Taximobility.R;
//import com.Taximobility.SplashActivity;
//import com.Taximobility.WebviewAct;
//import com.Taximobility.data.DriverData;
//import com.Taximobility.data.MapWrapperLayout;
//import com.Taximobility.data.apiData.PlacesDetail;
//import com.Taximobility.features.CToast;
//import com.Taximobility.features.FindApproxDistance;
//import com.Taximobility.interfaces.APIResult;
//import com.Taximobility.interfaces.DialogInterface;
//import com.Taximobility.interfaces.DistanceMatrixInterface;
//import com.Taximobility.interfaces.FragPopFront;
//import com.Taximobility.interfaces.GetAddress;
//import com.Taximobility.interfaces.PickupDropSet;
//import com.Taximobility.locationSearch.PickupDropSearchActivity;
//import com.Taximobility.locationSearch.StopData;
//import com.Taximobility.roomDB.GeocoderModel;
//import com.Taximobility.route.Route;
//import com.Taximobility.service.APIService_Retrofit_JSON;
//import com.Taximobility.service.CoreClient;
//import com.Taximobility.service.GetPassengerUpdate;
//import com.Taximobility.service.NodeAuth;
//import com.Taximobility.service.RetrofitCallbackClass;
//import com.Taximobility.util.AddressFromLatLng;
//import com.Taximobility.util.AppController;
//import com.Taximobility.util.CL;
//import com.Taximobility.util.CarMovementAnimation;
//import com.Taximobility.util.Colorchange;
//import com.Taximobility.util.CustomMarker;
//import com.Taximobility.util.Dialog_Common;
//import com.Taximobility.util.DisplayDimensions;
//import com.Taximobility.util.DotsProgressBarSearch;
//import com.Taximobility.util.FindDistance;
//import com.Taximobility.util.FontHelper;
//import com.Taximobility.util.LocationUtils;
//import com.Taximobility.util.NC;
//import com.Taximobility.util.NetworkStatus;
//import com.Taximobility.util.SessionSave;
//import com.Taximobility.util.ShowToast;
//import com.Taximobility.util.TaxiUtil;
//import com.Taximobility.util.Utility;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.bumptech.glide.request.target.DrawableImageViewTarget;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.MapStyleOptions;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.Polyline;
//import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.android.gms.maps.model.SquareCap;
//import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.gson.Gson;
//import com.squareup.picasso.Picasso;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.ConcurrentModificationException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//
//import static com.Taximobility.features.ApproximateCalculations.approxFare;
//import static com.Taximobility.util.ConstantsKt.SLAB_DEVIATION_IN_METER;
//import static com.google.android.gms.maps.model.JointType.ROUND;
//
///**
// * Page to Book taxi and to check available driver and ETA for two locations
// * <p>
// * Created by developer at Not on 27/2/18.
// */
//
//public class HomePage extends Fragment implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnCameraIdleListener
//        , GoogleMap.OnCameraMoveStartedListener, DialogInterface, GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener, FragPopFront, PickupDropSet, DistanceMatrixInterface, GetAddress {
//    public static final String speed = "45", CURRENT_COUNTRY_CODE = "IN";
//    private static final int MY_PERMISSIONS_REQUEST_CONTACTS = 222;
//    public static String defaultCityName = "";
//    public static boolean IS_HOME_PAGE = false;
//    public static HomePage.BOOKINGSTATE booking_state = HomePage.BOOKINGSTATE.STATE_ONE;
//    public static int z;
//    public static SearchFragment sf;
//    private static double E_time = 0;
//    private static boolean FREE_TO_MOVE = true;
//    private static String selectedModelID = "";
//    private static LinearLayout currentCarModel;
//    private static GoogleMap map;
//    private static LatLng LastKnownLatLng;
//
//    private final int DISTANCE_TYPE_FOR_ETA = 1;
//    private final int DISTANCE_TYPE_FOR_BOOK_LATER = 2;
//    private final int DISTANCE_TYPE_FOR_FARE = 3;
//
//    private ImageView ivLine, initialLay, imagePickupMarker, naviIconBook;
//    private LinearLayout confirmRequest, selectCarLay, favBotLay, idAll, idAlls, promoCodeLay, cashCardLay, skipFavouriteLayout;
//    private TextView availablecars, modelAvail, textConfirmPickup, textviewBookLater, pickupApproxFare, noCarsAvailable, paymentType,
//            fareMinimumPpl, tvSkipFavourite, tvRequestTaxi, tvPickLocConfirm, instructionHeader, tvCashCard;
//    private MapWrapperLayout mapWrapperLayout;
//    private DotsProgressBarSearch dotsProgressBarSearch;
//    private View moveCurrentLocation;
//    private FrameLayout animationLay;
//
//    private ArrayList<String> driverIdData;
//    private int previousSelectedModel = 0, displayWidth, displayHeight, availablecarcount, favDriverAvailable, bookFavDriver,
//            _hour = 0, min = 0, date = 0, month = 0, year = 0, selectedCarModel = 0, intPaymentType = 0, versionCode = BuildConfig.VERSION_CODE;
//    private long previousClickedTime = 0, currentDateTimeString = 0;
//    private String selectedCarmodelName, carModel = "1", favDriverMessage, _ampm = "AM", pickupTime = "", pickupTimeAndDate = "",
//            bookingType, approxTravelTime = "0", approxTravelDist = "0", promoCode = "", alertMsg, minFare, modelSize = "", travelModelId = "", bookingLocation = "";
//    private boolean doubleBackToExitPressedOnce, isBookAfter, bookAgainMsg, isFromBooknow = false, isSocketConnecting;
//    private float zoomLevel = 16f;
//    private double friend1S, friend2S, friend3S, friendA = 100, friend1SA, friend2SA, friend3SA, approxFare = 0.0, approximateDistance, approximateTime, lastKnownLat, lastKnownLng;
//
//    private ArrayList<LatLng> pickupSuggestion, listLatLng;
//    private Marker dropMarker, dropmap, pickupMarker;
//    private AnimatedVectorDrawable animatedVectorDrawable;
//    Runnable action = new Runnable() {
//        @Override
//        public void run() {
//            repeatAnimation();
//        }
//    };
//    private ArrayList<Marker> driverMarkerService;
//    private Polyline blackPolyLine, greyPolyLine;
//    Animator.AnimatorListener polyLineAnimationListener = new Animator.AnimatorListener() {
//        @Override
//        public void onAnimationStart(Animator animator) {
//            if (listLatLng.size() > 0)
//                addMarker(listLatLng.get(listLatLng.size() - 1));
//        }
//
//        @Override
//        public void onAnimationEnd(Animator animator) {
//
//            List<LatLng> blackLatLng = blackPolyLine.getPoints();
//            List<LatLng> greyLatLng = greyPolyLine.getPoints();
//
//            greyLatLng.clear();
//            greyLatLng.addAll(blackLatLng);
//            blackLatLng.clear();
//
//            blackPolyLine.setPoints(blackLatLng);
//            greyPolyLine.setPoints(greyLatLng);
//
//            blackPolyLine.setZIndex(2);
//        }
//
//        @Override
//        public void onAnimationCancel(Animator animator) {
//        }
//
//        @Override
//        public void onAnimationRepeat(Animator animator) {
//        }
//    };
//    private AsyncTask<String, String, GeocoderModel> address;
//    private CountDownTimer countDownTimer;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private LatLng dragLatLng;
//    private Handler handler, driverDelayHandler, handlerServercall;
//    private Runnable callAddressDrag;
//    private Dialog mcDialog, dt_mDialog, loadingDialog, alertDialog;
//    private DecimalFormat decimalFormat;
//    private Bundle alertBundle;
//    private GoogleApiClient mGoogleApiClient;
//    private SplitFareDialog splitFareDialog;
//    private JSONArray modelArray;
//    private Location location;
//    private ArrayList<PlacesDetail> placesDetailArrayList;
//    private SparseArray<Marker> onMovingDriverMarkers;
//    private ArrayList<Integer> onExistingDriverMarkers;
//    private Runnable driverLocationHistoryRunnable = new Runnable() {
//        @Override
//        public void run() {
//            startDriverMovementSocket(driverIdData);
//        }
//    };
//    private Route route;
//
//
//    private boolean zone_fare_applicable;
//    private double zone_zone_fare = 0.0;
//
//
//    public static void movetoCurrentloc() {
//        LatLng ll = new LatLng(LastKnownLatLng.latitude, LastKnownLatLng.longitude);
//        if (ll != null && map != null && sf.getDroplatlng() == null)
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 16f));
//
//        if (currentCarModel != null && !selectedModelID.equals("-1") && booking_state == HomePage.BOOKINGSTATE.STATE_ONE) {
//            Log.v("performClick ", "performClick 2 " + selectedModelID);
//            currentCarModel.performClick();
//        }
//    }
//
//    public static String unique() {
//        if (SessionSave.getSession("locaket_session", MainHomeFragmentActivity.context) == null) {
//            SessionSave.saveSession("locaket_session", "0", MainHomeFragmentActivity.context);
//        }
//        if (!SessionSave.getSession("locaket_session", MainHomeFragmentActivity.context).equals("")) {
//            return SessionSave.getSession("locaket_session", MainHomeFragmentActivity.context);
//        } else {
//            return "0";
//        }
//    }
//
//    /**
//     * Method used to calculate difference between dates
//     *
//     * @param datetime - Date with time to find the difference from current time
//     * @return - Difference of time between that give date and time
//     */
//    private int hoursAgo(String datetime) {
//        Date date = null;
//        try {
//            date = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH).parse(datetime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Date now = Calendar.getInstance().getTime(); // Get time now
//        long differenceInMillis = date.getTime() - now.getTime();
//        long differenceInHours = (differenceInMillis) / 1000L / 60L / 60L;
//        return (int) differenceInHours;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        IS_HOME_PAGE = true;
//        route = new Route(HomePage.this);
//        SessionSave.saveSession(TaxiUtil.NODE_TOKEN, "", getContext());
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.book_taxi_home, container, false);
//        priorChanges(v);
//
//        final int[] totalHt = new int[1];
//        ViewTreeObserver vto = selectCarLay.getViewTreeObserver();
//        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            public boolean onPreDraw() {
//                selectCarLay.getViewTreeObserver().removeOnPreDrawListener(this);
//                totalHt[0] = selectCarLay.getMeasuredHeight();
//                selectCarLay.setVisibility(View.GONE);
//                return true;
//            }
//        });
//
//        handler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(Message message) {
//                try {
//                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                    Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
//                    if (prev != null) {
//                        ft.remove(prev);
//                    }
//                    splitFareDialog = new SplitFareDialog();
//                    splitFareDialog.show(ft, "dialog");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        };
//        return v;
//    }
//
//    private void repeatAnimation() {
//        animatedVectorDrawable.start();
//        ivLine.postDelayed(action, 1000); // Will repeat animation in every 1 second
//    }
//
//    public void priorChanges(final View v) {
//        decimalFormat = new DecimalFormat("####0.00");
//        listLatLng = new ArrayList<>();
//        pickupSuggestion = new ArrayList<>();
//        driverMarkerService = new ArrayList<>();
//        driverDelayHandler = new Handler();
//        handlerServercall = new Handler(Looper.getMainLooper());
//
//        placesDetailArrayList = new ArrayList<>();
//        onMovingDriverMarkers = new SparseArray<>();
//        onExistingDriverMarkers = new ArrayList<>();
//
//        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
//        displayWidth = displayMetrics.widthPixels;
//        displayHeight = displayMetrics.heightPixels;
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
//        animationLay = v.findViewById(R.id.lay_home);
//        tvCashCard = v.findViewById(R.id.cash_card);
//
//        // If user have any ongoing trip, then system runs the service to get the trip status.
//        if (SessionSave.getSession("trip_id", getActivity()).equals("")) {
//            Intent intent = new Intent(getActivity(), GetPassengerUpdate.class);
//            getActivity().stopService(intent);
//        } else {
//            Intent intent = new Intent(getActivity(), GetPassengerUpdate.class);
//            getActivity().startService(intent);
//        }
//        sf = new SearchFragment();
//        sf.setSearchFragmentListener(this);
//        Bundle bundle = new Bundle();
//        bundle.putString("type", "home");
//        sf.setArguments(bundle);
//        getChildFragmentManager().beginTransaction().add(R.id.search_frame_lay, sf, "").commit();
//        imagePickupMarker = v.findViewById(R.id.imagePickupMarker);
//        selectCarLay = v.findViewById(R.id.select_car_lay);
//
//        confirmRequest = v.findViewById(R.id.confirm_request);
//        favBotLay = v.findViewById(R.id.fav_bot_lay);
//
//        initialLay = v.findViewById(R.id.initial_lay);
//        initialLay.setVisibility(View.GONE);
//
//        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.menu);
//        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("menu");
//
//        final int height = DisplayDimensions.getNavigationBarSize(getContext()).y;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ViewTreeObserver vto = initialLay.getViewTreeObserver();
//            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                public boolean onPreDraw() {
//                    initialLay.getViewTreeObserver().removeOnPreDrawListener(this);
//                    int totalHt = initialLay.getMeasuredHeight();
//                    initialLay.getLayoutParams().height = (totalHt + height);
//                    initialLay.setScaleType(ImageView.ScaleType.FIT_XY);
//                    return true;
//                }
//            });
//        }
//
//        if (SessionSave.getSession("isFromSplash", getContext(), false)) {
//            AnimationInScreen();
//        }
//
//
//        mapWrapperLayout = v.findViewById(R.id.map_relative_layout);
//        idAll = v.findViewById(R.id.id_all);
//        idAlls = v.findViewById(R.id.id_alls);
//        instructionHeader = v.findViewById(R.id.instruction_header);
//        HorizontalScrollView carScroll = v.findViewById(R.id.carScroll);
//        TextView fareEstimate = v.findViewById(R.id.fare_estimate);
//        promoCodeLay = v.findViewById(R.id.promo_code_lay);
//        cashCardLay = v.findViewById(R.id.cash_card_lay);
//
//        fareMinimumPpl = v.findViewById(R.id.fare_minimum_ppl);
//        tvSkipFavourite = v.findViewById(R.id.skip_fav);
//        skipFavouriteLayout = v.findViewById(R.id.skip_fav_lay);
//        naviIconBook = v.findViewById(R.id.navi_icon_book);
//        RelativeLayout main = v.findViewById(R.id.booktaxilay_home);
//
//        ivLine = v.findViewById(R.id.iv_line);
//
//        FontHelper.applyFont(getActivity(), main);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    Initialize(v);
//                    setcarModel();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, 200);
//
//        int heightStatus = DisplayDimensions.getStatusBarHeight(getActivity());
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        if (SessionSave.getSession("Lang", getActivity()).equals("ar")) {
//            lp.setMargins(TaxiUtil.getPixelsFromDp(getActivity(), 15), heightStatus, 0, 0);
//            int left = naviIconBook.getPaddingLeft();
//            naviIconBook.setLayoutParams(lp);
//            naviIconBook.setPadding(left, heightStatus, 0, 25);
//
//        } else {
//            int right = naviIconBook.getPaddingRight();
//            lp.setMargins(TaxiUtil.getPixelsFromDp(getActivity(), 15), heightStatus, 0, 0);
//            naviIconBook.setLayoutParams(lp);
//            naviIconBook.setPadding(0, heightStatus, right, 25);
//        }
//        naviIconBook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (booking_state == HomePage.BOOKINGSTATE.STATE_ONE)
//                    ((MainHomeFragmentActivity) getActivity()).left_icon.performClick();
//                else
//                    onBackPress();
//            }
//        });
//
//        RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        if (SessionSave.getSession("Lang", getActivity()).equals("ar"))
//            lparams.setMargins(0, heightStatus, TaxiUtil.getPixelsFromDp(getActivity(), 50), 0);
//        else
//            lparams.setMargins(TaxiUtil.getPixelsFromDp(getActivity(), 50), heightStatus, 0, 0);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            LinearLayout linearLayout = v.findViewById(R.id.instruction_header_lay);
//            linearLayout.setLayoutParams(lparams);
//            linearLayout.setPadding(0, (heightStatus / 2), 0, 0);
//
//        }
//    }
//
//    /**
//     * Check for any bundle message from Activity
//     * initialize view and
//     * its click listener
//     */
//    public void Initialize(View v) {
//        try {
//            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                    .addApi(LocationServices.API)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .build();
//            mGoogleApiClient.connect();
//            alertBundle = this.getArguments();
//            if (alertBundle != null) {
//                alertMsg = alertBundle.getString("alert_message");
//                bookAgainMsg = alertBundle.getBoolean("book_again");
//            }
//            if (alertMsg != null && alertMsg.length() != 0) {
//                alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + alertMsg, "" + NC.getResources().getString(R.string.ok), "");
//                getActivity().getIntent().putExtras(new Bundle());
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        callAddressDrag = new Runnable() {
//            @Override
//            public void run() {
//                Log.e("Locationaa", "onCameraChange: ca" + z);
//                if (z == 1) {
//
//                    if (NetworkStatus.isOnline(getActivity())) {
//                        try {
//                            LatLng ss = null;
//                            if (dragLatLng != null)
//                                ss = dragLatLng;
//                            else
//                                ss = sf.getPickuplatlng();
//                            if (address != null)
//                                address.cancel(true);
//                            Log.e("Location_address", "onCameraChange: ca" + ss);
//                            if (address == null || ((address.getStatus() != AsyncTask.Status.PENDING && address.getStatus() != AsyncTask.Status.RUNNING))) {
//                                address = new AddressFromLatLng(getActivity(), new LatLng(ss.latitude, ss.longitude), HomePage.this).execute();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Log.e("Check your internet", "check the internet");
//                    }
//                }
//            }
//        };
//
//        tvRequestTaxi = v.findViewById(R.id.textRequestTaxi);
//        moveCurrentLocation = v.findViewById(R.id.mov_cur_loc);
//        moveCurrentLocation.setOnClickListener(this);
//        tvPickLocConfirm = v.findViewById(R.id.pickloc_confirm);
//
//        textviewBookLater = v.findViewById(R.id.textBookLater);
//        textConfirmPickup = v.findViewById(R.id.textConfirmPickup);
//
//        noCarsAvailable = v.findViewById(R.id.no_cars_available);
//
//        availablecars = v.findViewById(R.id.availablecars);
//
//        modelAvail = v.findViewById(R.id.model_avail);
//        paymentType = v.findViewById(R.id.cash_card);
//
//
//        tvRequestTaxi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!sf.getPickupLocTxt().trim().equals("")) {
//                    if (availablecarcount > 0) {
//                        if (SessionSave.getSession("pickupsuggestion", getActivity()).equals("1")) {
//                            new HomePage.FindPickupSuggestion();
//                        } else {
//                            if (SessionSave.getSession(TaxiUtil.isSplitOn, getActivity(), true) && sf.getDropLocTxt().trim().length() == 0) {
//                                CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.select_the_drop_location));
//                                closeDialog();
//                            } else {
//                                booking_state = HomePage.BOOKINGSTATE.STATE_THREE;
//                                tvPickLocConfirm.setText(sf.getPickupLocTxt());
//                                textConfirmPickup.setVisibility(View.VISIBLE);
//                                confirmRequest.setVisibility(View.VISIBLE);
//                                clearsetPickDropMarker();
//                            }
//                        }
//
//                    } else {
//                        ShowToast.center(getActivity(), NC.getString(R.string.no_taxi));
//                    }
//                } else {
//                    ShowToast.center(getActivity(), NC.getString(R.string.fetching_address));
//                    getPickupAdress();
//                }
//
//
//            }
//        });
//
//
//        try {
//            JSONArray jsonArray = new JSONArray(SessionSave.getSession("passenger_payment_option", getActivity()));
//            if (jsonArray.length() == 1) {
//                JSONObject jsonObject = jsonArray.getJSONObject(0);
//                if (jsonObject.getInt("pay_mod_id") == 1) {
//
//                    intPaymentType = 1;
//                    paymentType.setText(NC.getResources().getString(R.string.payment_cash));
//                    cashCardLay.setEnabled(false);
//
//                } else if (jsonObject.getInt("pay_mod_id") == 2) {
//                    intPaymentType = 0;
//                    cashCardLay.setEnabled(false);
//                    paymentType.setText(NC.getResources().getString(R.string.card));
//
//                }
//            } else {
//                intPaymentType = 0;
//                cashCardLay.setEnabled(true);
//                paymentType.setText(NC.getResources().getString(R.string.cash_card));
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        textviewBookLater.setOnClickListener(this);
//        textConfirmPickup.setOnClickListener(this);
//
//        promoCodeLay.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                promocode();
//            }
//        });
//        cashCardLay.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (!SessionSave.getSession(TaxiUtil.isSplitOn, getActivity(), true)) {
//                    final View view = View.inflate(getActivity(), R.layout.paymentdialog, null);
//                    final Dialog dialog = new Dialog(getActivity(), R.style.NewDialog);
//                    dialog.setContentView(view);
//                    dialog.setCancelable(false);
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.show();
//                    FontHelper.applyFont(getActivity(), view);
//
//                    Colorchange.ChangeColor(dialog.findViewById(R.id.inner_content), getActivity());
//                    final RadioGroup rgrp = dialog.findViewById(R.id.paymentdialog_rgrp);
//                    final RadioButton rbtn_cash = dialog.findViewById(R.id.paymentdialog_rbtn_cash);
//                    final RadioButton rbtn_card = dialog.findViewById(R.id.paymentdialog_rbtn_card);
//                    final Button btn_submit = dialog.findViewById(R.id.paymentdialog_btn_submit);
//                    final Button btn_cancel = dialog.findViewById(R.id.paymentdialog_btn_cancel);
//
//                    if (intPaymentType == 1)
//                        rbtn_cash.setChecked(true);
//                    else if (intPaymentType == 2)
//                        rbtn_card.setChecked(true);
//                    else {
//                        rbtn_cash.setChecked(false);
//                        rbtn_card.setChecked(false);
//                    }
//                    btn_submit.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//
//                            int selectedId = rgrp.getCheckedRadioButtonId();
//                            RadioButton radioButton = dialog.findViewById(selectedId);
//
//                            if (radioButton != null) {
//                                if (radioButton.getText().toString().equals(NC.getResources().getString(R.string.payment_cash))) {
//                                    intPaymentType = 1;
//                                    paymentType.setText(NC.getResources().getString(R.string.payment_cash));
//                                    tvCashCard.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cash, 0, 0, 0);
//                                } else if (radioButton.getText().toString().equals(NC.getResources().getString(R.string.payment_card))) {
//                                    intPaymentType = 2;
//                                    paymentType.setText(NC.getResources().getString(R.string.payment_card));
//                                    tvCashCard.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cash_b, 0, 0, 0);
//                                }
//
//                                dialog.dismiss();
//                            } else {
//                                CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.select_payment));
//                            }
//                            Log.e("selec payment type ", String.valueOf(intPaymentType));
//                        }
//                    });
//
//                    btn_cancel.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            intPaymentType = 0;
//                            paymentType.setText(NC.getResources().getString(R.string.cash_card));
//                            dialog.dismiss();
//                        }
//                    });
//
//                } else {
//                    ShowToast.center(getActivity(), NC.getString(R.string.mode_selection_split));
//                }
//            }
//        });
//
//        Colorchange.ChangeColor((ViewGroup) v, getActivity());
//        SupportMapFragment mapFragments = (SupportMapFragment) getChildFragmentManager()
//                .findFragmentById(R.id.map);
//        if (mapFragments == null)
//            CToast.ShowToast(getActivity(), "nullll");
//        else {
//            mapFragments.getMapAsync(this);
//
//        }
//
//
//        tvSkipFavourite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NeedtoGetAddress();
//                booking_state = HomePage.BOOKINGSTATE.STATE_TWO;
//                PickupDropSearchActivity.SET_FOR_PICKUP = true;
//                if (location != null)
//                    sf.setPickuplatlng(new LatLng(location.getLatitude(), location.getLongitude()));
//
//            }
//        });
//    }
//
//    private void callSaveBooking() {
//        isFromBooknow = true;
//        callBookNow();
//        /*if (currentCarModel != null && !selectedModelID.equals("-1")) {
//            showDialog();
//            Systems.out.println("performClick " + "performClick 3");
//            currentCarModel.performClick();
//        }*/
//    }
//
//    public void NeedtoGetAddress() {
//        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (location != null && location.getLatitude() != 0.0) {
//            if (sf.getPickuplat() == 0.0) {
//                sf.setPickuplatlng(new LatLng(location.getLatitude(), location.getLongitude()));
//            }
//            float[] dis = new float[1];
//            Location.distanceBetween(sf.getPickuplat(), sf.getPickuplng(), location.getLatitude(), location.getLongitude(), dis);
//            Log.v("distance_2000", SessionSave.getSession(TaxiUtil.isNeedtoFetchAddress, getActivity(), false) + "__" + dis[0] + "____" + sf.getPickuplat() + "," + sf.getPickuplng() + "," + " " + location.getLatitude() + "," + location.getLongitude());
//            if (dis[0] > 200 || (sf.getPickupLocTxt().trim().equals("") || sf.getPickupLocTxt().equals(getString(R.string.fetching_address)))) {
//                sf.setPickupLocTxt(NC.getString(R.string.fetching_address));
//                Log.v("distance_2001 ", "" + dis[0] + "__" + SessionSave.getSession(TaxiUtil.isNeedtoFetchAddress, getActivity(), false));
//                if (!SessionSave.getSession(TaxiUtil.isNeedtoFetchAddress, getActivity(), false)) {
//                    sf.setPickupLocTxt(NC.getString(R.string.pinlocation));
//                    sf.setPickuplatlng(new LatLng(location.getLatitude(), location.getLongitude()));
//                } else {
//                    getPickupAdress();
//                }
//            }
//
//        }
//    }
//
//    @Override
//    public void onPause() {
//        if (splitFareDialog != null)
//            if (splitFareDialog.isVisible())
//                splitFareDialog.dismiss();
//        super.onPause();
//    }
//
//    public void getPickupAdress() {
//        try {
//            if (location != null) {
//                try {
//                    SessionSave.saveSession("PLAT", "" + location.getLatitude(), getActivity());
//                    SessionSave.saveSession("PLNG", "" + location.getLongitude(), getActivity());
//                    Systems.out.println("distance_2002" + address);
//                    if (address == null || ((address.getStatus() != AsyncTask.Status.PENDING && address.getStatus() != AsyncTask.Status.RUNNING))) {
//                        address = new AddressFromLatLng(getActivity(), new LatLng(location.getLatitude(), location.getLongitude()), HomePage.this).execute();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    if (address == null || ((address.getStatus() != AsyncTask.Status.PENDING && address.getStatus() != AsyncTask.Status.RUNNING))) {
//                        address = new AddressFromLatLng(getActivity(), new LatLng(location.getLatitude(), location.getLongitude()), HomePage.this).execute();
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    void clearsetPickDropMarker() {
//        Log.e("clearsetPickDropMarker", "clearsetPickDropMarker: ");
//        if (getActivity() != null && map != null) {
//            if (booking_state == HomePage.BOOKINGSTATE.STATE_ONE) {
//
//                if (map != null) {
//                    map.getUiSettings().setMyLocationButtonEnabled(false);
//                    map.getUiSettings().setCompassEnabled(false);
//                }
//                ((MainHomeFragmentActivity) getActivity()).enableSlide();
//                if (LastKnownLatLng != null && map != null) {
//                    map.clear();
//                    if (TaxiUtil.mDrivermovementdata.size() > 0) {
//                        DriverLiveMovement(null);
//                        DriverLiveMovement(TaxiUtil.mDrivermovementdata);
//                    }
//                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(LastKnownLatLng, 16f));
//                    map.setMyLocationEnabled(true);
//                    map.getUiSettings().setMyLocationButtonEnabled(false);
//                    if (currentCarModel != null && !selectedModelID.equals("-1")) {
//                        Log.v("performClick ", "performClick 1");
//                        currentCarModel.performClick();
//                    }
//                }
//                naviIconBook.setImageResource(R.drawable.menu);
//                instructionHeader.setVisibility(View.GONE);
//                map.getUiSettings().setMapToolbarEnabled(false);
//                if (pickupApproxFare != null) {
//                    pickupApproxFare.setText("");
//                }
//                if (!sf.getPickupLocTxt().trim().equals(""))
//                    sf.dropVisible();
//                else
//                    sf.dropGone();
//
//                sf.setDroplatlng(null);
//                sf.setDropLocTxt("");
//                sf.clearWayPoints();
//                approxFare = 0.0;
//                imagePickupMarker.setVisibility(View.GONE);
//                selectCarLay.setVisibility(View.GONE);
//                confirmRequest.setVisibility(View.GONE);
//                selectCarLay.setVisibility(View.GONE);
//                skipFavouriteLayout.setVisibility(View.VISIBLE);
//                map.setOnCameraIdleListener(null);
//                mapWrapperLayout.invalidate();
//                mapWrapperLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
//                tvPickLocConfirm.setText("");
//                if (sf.getPickupLocTxt().equals("")) {
//                    try {
//                        getPickupAdress();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                updateMarker(TaxiUtil.mDriverdata);
//                moveCurrentLocation.setVisibility(View.VISIBLE);
//            } else if (booking_state == HomePage.BOOKINGSTATE.STATE_TWO) {
//                ((MainHomeFragmentActivity) getActivity()).disableSlide();
//                selectCarLay.setVisibility(View.VISIBLE);
//                moveCurrentLocation.setVisibility(View.GONE);
//                if (TaxiUtil.mDrivermovementdata.size() > 0) {
//                    DriverLiveMovement(null);
//                    DriverLiveMovement(TaxiUtil.mDrivermovementdata);
//                }
//                instructionHeader.setText(NC.getString(R.string.tap_to_edit));
//                closePopup();
//                imagePickupMarker.setVisibility(View.GONE);
//                if (map != null) {
//                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
//                    map.clear();
//                    map.setMyLocationEnabled(false);
//                    map.setOnCameraIdleListener(null);
//                    map.getUiSettings().setMyLocationButtonEnabled(false);
//                    map.getUiSettings().setCompassEnabled(false);
//                    map.getUiSettings().setMapToolbarEnabled(false);
//                }
//
//                naviIconBook.setImageResource(R.drawable.back);
//                selectCarLay.setVisibility(View.VISIBLE);
//
//                confirmRequest.setVisibility(View.GONE);
//                skipFavouriteLayout.setVisibility(View.GONE);
//
//                if (sf.getPickuplatlng() != null) {
//                    if (pickupMarker != null)        //            pickup_marker = map.addMarker(new MarkerOptions()
//                        pickupMarker.remove();
//                    Systems.out.println("settting markk1" + sf.getPickuplatlng());
//                    if (TaxiUtil.mDriverdata.size() > 0) {
//                        if (E_time == 0.0) {
//                            E_time = 1;
//                        }
//                    }
//                    pickupMarker = map.addMarker(new MarkerOptions()
//                            .position(sf.getPickuplatlng())
//                            .icon(BitmapDescriptorFactory.fromBitmap(CustomMarker.getMarkerBitmapFromView(String.valueOf((int) E_time), getActivity(), sf.getPickupLocTxt()))));
//                    pickupMarker.setTag("pickup");
//                    pickupMarker.setAnchor(0.0f, 1f);
//
//                    if (sf.getDroplatlng() == null) {
//                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sf.getPickuplatlng(), 16f));
//                    }
//                }
//
//
//                if (sf.getDroplatlng() != null) {
//                    dropMarker = map.addMarker(new MarkerOptions()
//                            .position(sf.getDroplatlng())
//                            .icon(BitmapDescriptorFactory.fromBitmap(CustomMarker.getMarkerBitmapFromViewForDrop(sf.getDropLocTxt(), getActivity()))));
//                    sf.dropGone();
//                    dropMarker.setTag("dropMarker");
//                    dropMarker.setAnchor(1.0f, 1f);
//                    final LatLng ll = sf.getPickuplatlng() == null ? LastKnownLatLng : sf.getPickuplatlng();
//
//                    if (ll != null && ll.longitude != 0.0) {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                route.setUpPolyLine(map, getActivity(), ll, sf.getDroplatlng(), sf.getLatLngPoints());
//                            }
//                        }, 1000);
//
//                        double Driverdistance;
//                        if (SessionSave.getSession("isBUISNESSKEY", getActivity(), true)) {
//                            if (!SessionSave.getSession(TaxiUtil.isNeedtoDrawRoute, getActivity(), false))
//                                new FindApproxDistance(HomePage.this).getDistance(getActivity(), ll.latitude, ll.longitude, sf.getDroplat(), sf.getDroplng(), DISTANCE_TYPE_FOR_FARE);
//                        } else {
//                            Driverdistance = 0.5 + FindDistance.distance(ll.latitude, ll.longitude, sf.getDroplat(), sf.getDroplng(), SessionSave.getSession("Metric_type", getActivity()), location);
//                            new HomePage.Approximate_Time(Driverdistance, SessionSave.getSession("Metric_type", getActivity()), DISTANCE_TYPE_FOR_FARE).execute();
//
//                        }
//                    }
//                    int layHeight = selectCarLay.getHeight();
//                    int requiredMapHeight = displayHeight - layHeight;
//                    mapWrapperLayout.getLayoutParams().height = requiredMapHeight + 10;
//                    selectCarLay.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                moveCamera();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, 1000);
//                } else {
//                    selectCarLay.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (sf.getDroplatlng() == null)
//                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sf.getPickuplat(), sf.getPickuplng()), 16f));
//                        }
//                    }, 500);
//
//                    if (!sf.getPickupLocTxt().trim().equals(""))
//                        sf.dropVisible();
//                    else
//                        sf.dropGone();
//                }
//                tvPickLocConfirm.setText("");
//                updateMarker(TaxiUtil.mDriverdata);
//            } else if (booking_state == BOOKINGSTATE.STATE_THREE) {
//                map.clear();
//                DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
//                displayWidth = displayMetrics.widthPixels;
//                displayHeight = displayMetrics.heightPixels;
//                mapWrapperLayout.getLayoutParams().height = displayHeight;
//                if (TaxiUtil.mDrivermovementdata.size() > 0) {
//                    DriverLiveMovement(null);
//                    DriverLiveMovement(TaxiUtil.mDrivermovementdata);
//                }
//                moveCurrentLocation.setVisibility(View.VISIBLE);
//                if (map != null) {
//                    map.getUiSettings().setMapToolbarEnabled(false);
//                    map.getUiSettings().setCompassEnabled(false);
//                }
//                map.setMyLocationEnabled(true);
//                map.getUiSettings().setMyLocationButtonEnabled(false);
//                ((MainHomeFragmentActivity) getActivity()).disableSlide();
//                selectCarLay.setVisibility(View.GONE);
//
//                instructionHeader.setVisibility(View.VISIBLE);
//                instructionHeader.setText(NC.getString(R.string.move_map_to));
//                closePopup();
//                naviIconBook.setImageResource(R.drawable.back);
//                skipFavouriteLayout.setVisibility(View.GONE);
//
//
//                LatLng ll = new LatLng(sf.getPickuplat(), sf.getPickuplng());
//                if (ll != null && map != null && map.getCameraPosition().zoom < 15)
//                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 16f));
//                sf.dropGone();
//                confirmRequest.setVisibility(View.VISIBLE);
//                selectCarLay.setVisibility(View.GONE);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (map != null && booking_state == BOOKINGSTATE.STATE_THREE && getActivity() != null) {
//                            map.setOnCameraIdleListener(HomePage.this);
//                        }
//                    }
//                }, 1000);
//
//                Glide.with(this).load(R.drawable.flag_green).into(imagePickupMarker);
//                if (sf.getPickupLocTxt() != null) {
//                    if (sf.getPickupLocTxt().equals(NC.getString(R.string.pinlocation))) {
//                        Systems.out.println("Locationaa-------------------fetching_address");
//                        tvPickLocConfirm.setText(NC.getString(R.string.fetching_address));
//                    } else
//                        tvPickLocConfirm.setText(sf.getPickupLocTxt());
//                    imagePickupMarker.setVisibility(View.VISIBLE);
//                    Log.e("clear", "clearsetPickDropMarker: 1");
//
//
//                }
//                if (sf.getPickupLocTxt().trim().equals("") || sf.getPickupLocTxt().trim().equals(NC.getString(R.string.pinlocation)) || sf.getPickupLocTxt().equals(NC.getString(R.string.fetching_address))) {
//                    z = 1;
//                    handlerServercall.postDelayed(callAddressDrag, 0);
//                }
//                for (int i = 0; i < pickupSuggestion.size(); i++) {
//                    Marker marker = map.addMarker(new MarkerOptions()
//                            .position(pickupSuggestion.get(i))
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.suggestion_pin)));
//                    String s = "suggestion" + i;
//                    marker.setTag(s);
//                }
//
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Message message = handler.obtainMessage(0, "");
//        message.sendToTarget();
//    }
//
//    private void callBookNow() {
//        if (availablecarcount > 0) {
//
//            isFromBooknow = false;
//
//            if (!(sf.getPickupLocTxt().trim().length() == 0 || (sf.getPickupLocTxt().trim().equals(NC.getResources().getString(R.string.fetching_address))))) {
//                if (sf.getDropLocTxt() != null && sf.getDropLocTxt().trim().length() > 0) {
//                    isBookAfter = false;
//                    if (SessionSave.getSession("isBUISNESSKEY", getActivity(), true)) {
//                        UpdateApproximateDistance(approximateTime, approximateDistance, DISTANCE_TYPE_FOR_BOOK_LATER, 1);
//                    } else {
//                        double Driverdistance1 = 0.5 + FindDistance.distance(sf.getPickuplat(), sf.getPickuplng(), sf.getDroplat(), sf.getDroplng(), SessionSave.getSession("Metric_type", getActivity()), location);
//                        new Approximate_Time(Driverdistance1, SessionSave.getSession("Metric_type", getActivity()), DISTANCE_TYPE_FOR_BOOK_LATER).execute();
//                    }
//                } else if (favDriverAvailable > 0 && SessionSave.getSession(TaxiUtil.isFavDriverOn, getActivity(), true)) {
//                    if (SessionSave.getSession(TaxiUtil.isSplitOn, getActivity(), true)) {
//                        CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.select_the_drop_location));
//                        closeDialog();
//                    } else {
//                        alertDialog = new Dialog_Common().setmCustomDialog(getActivity(), this, NC.getResources().getString(R.string.message), favDriverMessage,
//                                NC.getResources().getString(R.string.ok),
//                                NC.getResources().getString(R.string.no_thanks), "1");
//                    }
//
//                } else {
//                    //If splitfare settings is On then dropMarker location is must
//                    if (SessionSave.getSession(TaxiUtil.isSplitOn, getActivity(), true))
//                        CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.select_the_drop_location));
//                    else
//                        bookNow();
//                }
//            } else {
//                alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.select_the_pickup_location), "" + NC.getResources().getString(R.string.ok), "");
//            }
//        } else {
//            alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.nodrivers), "" + NC.getResources().getString(R.string.ok), "");
//        }
//    }
//
//    /**
//     * Clear the data setups for the previous trip
//     */
//    private void clearPreviousData() {
//        friend1S = 0;
//        friend1SA = 0;
//        friend2S = 0;
//        friend2SA = 0;
//        friend3S = 0;
//        friend3SA = 0;
//        bookFavDriver = 0;
//        friendA = 100;
//        promoCode = "";
//    }
//
//    void moveCamera() {
//        try {
//            LatLngBounds.Builder builder = new LatLngBounds.Builder();
//            if (pickupMarker != null)
//                builder.include(pickupMarker.getPosition());
//            if (dropMarker != null)
//                builder.include(dropMarker.getPosition());
//
//
//            LatLngBounds bounds = builder.build();
//
//            final int width = getResources().getDisplayMetrics().widthPixels;
//            int height = getResources().getDisplayMetrics().heightPixels;
//            int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
//            int adjustHeight = (displayHeight - (selectCarLay.getHeight()));
//
//            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, adjustHeight, padding);
//            if (FREE_TO_MOVE) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        FREE_TO_MOVE = true;
//                    }
//                }, 3000);
//                map.moveCamera(cu);
//                if (pickupMarker != null && map.getProjection().toScreenLocation(pickupMarker.getPosition()).x > (width / 2)) {
//                    updateCameraBearing();
//                } else {
//                    resetCameraBearing();
//                }
//                FREE_TO_MOVE = false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void updateCameraBearing() {
//        if (map == null) return;
//        CameraPosition camPos = CameraPosition
//                .builder(
//                        map.getCameraPosition() // current Camera
//                )
//                .bearing(200)
//                .build();
//        if (map != null)
//            map.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
//    }
//
//    public void resetCameraBearing() {
//        if (map == null) return;
//        CameraPosition camPos = CameraPosition
//                .builder(map.getCameraPosition() // current Camera
//                )
//                .bearing(0)
//                .build();
//        if (map != null)
//            map.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
//    }
//
//    /**
//     * //     * this method is used to call book taxi api
//     * //
//     */
//
//    private void bookNow() {
//        try {
//            z = 1;
//            final Calendar cal = Calendar.getInstance();
//            int hour = cal.get(Calendar.HOUR_OF_DAY);
//            int minute = cal.get(Calendar.MINUTE);
//            int sec = cal.get(Calendar.SECOND);
//            int pYear = cal.get(Calendar.YEAR);
//            int pMonth = cal.get(Calendar.MONTH);
//            int pDay = cal.get(Calendar.DAY_OF_MONTH);
//            updateTimer(hour, minute, pDay, pMonth, pYear, sec);
//            String P_Address = sf.getPickupLocTxt();
//            String D_Address = sf.getDropLocTxt();
//            if (TaxiUtil.mDriverdata.size() != 0) {
//                if (sf.getPickupLocTxt().trim().length() == 0 || (sf.getPickupLocTxt().trim().equals(NC.getResources().getString(R.string.fetching_address))))
//                    alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.select_the_pickup_location), "" + NC.getResources().getString(R.string.ok), "");
//                else {
//                    JSONObject j = new JSONObject();
//                    j.put("latitude", sf.getPickuplat());
//                    j.put("longitude", sf.getPickuplng());
//                    j.put("pickupplace", "" + sf.getPickupLocTxt() == null ? "" : sf.getPickupLocTxt());
//                    j.put("dropplace", "" + sf.getDropLocTxt() == null ? "" : sf.getDropLocTxt());
//                    j.put("drop_latitude", sf.getDroplat() == null ? "" : sf.getDroplat());
//                    j.put("drop_longitude", sf.getDroplng() == null ? "" : sf.getDroplng());
//                    j.put("pickup_time", pickupTime);
//                    j.put("motor_model", carModel);
//                    j.put("approx_distance", approximateDistance);
//                    j.put("approx_duration", approximateTime);
//                    j.put("cityname", (defaultCityName == null || defaultCityName.trim().equals("")) ? SessionSave.getSession("default_city_name", getActivity()) : defaultCityName.trim());
//                    j.put("distance_away", E_time);
//                    j.put("sub_logid", "");
//                    j.put("passenger_id", SessionSave.getSession("Id", getActivity()));
//                    j.put("request_type", "1");
//                    j.put("promo_code", promoCode);
//                    j.put("now_after", "0");
//                    j.put("notes", SessionSave.getSession("notes", getActivity()));
//
//                    j.put("fav_driver_booking_type", bookFavDriver);
//                    j.put("friend_id2", friend1S);
//                    j.put("friend_percentage2", friend1SA);
//                    j.put("friend_id3", friend2S);
//                    j.put("friend_percentage3", friend2SA);
//                    j.put("friend_id4", friend3S);
//                    j.put("friend_percentage4", friend3SA);
//                    j.put("friend_id1", SessionSave.getSession("Id", getActivity()));
//                    j.put("friend_percentage1", friendA);
//                    j.put("passenger_payment_option", intPaymentType);
//
//
//                    if (approxFare != 0 && sf.getDroplat() != 0.0 && sf.getDroplng() != 0.0) {
//                        if (friend2SA != 0)
//                            j.put("friend_percentage_amt3", ((friend2SA / 100.00) * approxFare));
//                        if (friend1SA != 0)
//                            j.put("friend_percentage_amt2", ((friend1SA / 100.00) * approxFare));
//                        if (friend3SA != 0)
//                            j.put("friend_percentage_amt4", ((friend3SA / 100.00) * approxFare));
//
//                        j.put("friend_percentage_amt1", ((friendA / 100.00) * approxFare));
//                        j.put("approx_trip_fare", approxFare);
//                    } else {
//                        j.put("friend_percentage_amt1", 0);
//                        j.put("friend_percentage_amt2", 0);
//                        j.put("friend_percentage_amt3", 0);
//                        j.put("approx_trip_fare", 0);
//                    }
//
//                    j.put("travel_modelid", Integer.parseInt(travelModelId.equals("") ? "0" : travelModelId));
//                    j.put("booked_location", bookingLocation);
//                    j.put("booked_latitude", lastKnownLat);
//                    j.put("booked_longitude", lastKnownLng);
//                    String curVersion = BuildConfig.VERSION_NAME;
//
//                    j.put("passenger_app_version", curVersion);
//                    j.put("route_path", route.getOverViewPolyLine());
//
//                    j.put("stops", sf.getStopPoints() == null ? "" : new JSONArray(new Gson().toJson(sf.getStopPoints())));
//                    final String url = "type=savebooking";
//                    SessionSave.saveSession("SearchUrl", url, getActivity());
//                    bookingType = "now";
//
//                    if (!sf.getPickupLocTxt().equalsIgnoreCase("" + NC.getResources().getString(R.string.fetching_address))) {
//                        new SearchTaxi(url, j);
//                    } else
//                        alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.select_the_pickup_location), "" + NC.getResources().getString(R.string.ok), "");
//                }
//            } else {
//                alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.no_taxi), "" + NC.getResources().getString(R.string.ok), "");
//            }
//            textviewBookLater.setClickable(true);
//        } catch (Exception e) {
//            textviewBookLater.setClickable(true);
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * this method is used to set time
//     *
//     * @param day   set day of the month
//     * @param hours set hour of the day
//     * @param mins  set minutes of the hour
//     * @param month set month of the year
//     * @param sec   set seconds of the minute
//     * @param year  set the selected year
//     */
//
//    public void updateTimer(int hours, final int mins, int day, int month, int year, int sec) {
//
//        String timeSet = "";
//        if (hours > 12) {
//            hours -= 12;
//            timeSet = "PM";
//        } else if (hours == 0) {
//            hours += 12;
//            timeSet = "AM";
//        } else if (hours == 12)
//            timeSet = "PM";
//        else
//            timeSet = "AM";
//        String minutes = "";
//        if (mins < 10)
//            minutes = "0" + mins;
//        else
//            minutes = String.valueOf(mins);
//        // Append in a StringBuilder
//        final String aTime = new StringBuilder().append(hours).append(':').append(minutes).append(':').append(sec).append(" ").append(timeSet).toString();
//        pickupTime = aTime;
//        pickupTimeAndDate = "" + day + "-" + month + "-" + year + " " + aTime;
//    }
//
//    /**
//     * Method to car layout in bottom if more than 3 models available add cars to scrollview
//     */
//    private void setcarModel() {
//        Log.d("selectedModelID ", selectedModelID);
//        try {
//            modelArray = new JSONArray(SessionSave.getSession("model_details", getActivity()));
//            if (modelArray.length() <= 4) {
//                idAlls.setVisibility(View.GONE);
//                idAll.setVisibility(View.VISIBLE);
//                for (int n = 0; n < modelArray.length(); n++) {
//                    int i = 0;
//                    i = n;
//                    View v = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_lay_car, idAll, false);
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    params.weight = 1.0f;
//                    final LinearLayout carlay = v.findViewById(R.id.lay_model_one);
//                    carlay.setLayoutParams(params);
//                    try {
//                        ((TextView) v.findViewById(R.id.txt_model1)).setText("" + modelArray.getJSONObject(i).getString("model_name").toUpperCase());
//                        Log.d("Image_url ", modelArray.getJSONObject(i).getString("unfocus_image"));
//                        Picasso.get().load(modelArray.getJSONObject(i).getString("unfocus_image")).error(R.drawable.car2_unfocus).into((ImageView) v.findViewById(R.id.txt_dra_car1));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    carlay.setTag(i);
//                    carlay.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(final View v) {
//                            HoldOnClickforasec(v);
//
//                            Log.v("carmodelTag ", "" + v.getTag().toString());
//                            selectedCarModel = Integer.parseInt(v.getTag().toString());
//                            Systems.out.println("performClick " + "selected car model " + String.valueOf(selectedCarModel));
//                            Log.e("onclick ", String.valueOf(selectedCarModel));
//
//                            try {
//                                selectedModelID = ((JSONObject) modelArray.get(selectedCarModel)).getString("model_id");
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            //Check whether it is outstation or rental
//                            if (selectedModelID.equals("-1")) {
//                                AlertForPackage(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.continue_package), "" + NC.getResources().getString(R.string.ok), "" + NC.getResources().getString(R.string.cancel));
//                            } else {
//                                SessionSave.saveSession("selected_carmodel", String.valueOf(selectedCarModel), getActivity());
//                                if (previousSelectedModel == selectedCarModel) {
//                                    currentDateTimeString = System.currentTimeMillis();
//                                    if (currentDateTimeString - previousClickedTime <= 2000) {
//                                        closeDialog();
//                                    } else {
//                                        carlay_click(v, 1);
//                                    }
//                                } else {
//                                    carlay_click(v, 1);
//                                }
//                                previousSelectedModel = selectedCarModel;
//                                previousClickedTime = System.currentTimeMillis();
//                            }
//                        }
//                    });
//
//                    idAll.addView(v);
//
//                    String pos = SessionSave.getSession("selected_carmodel", getActivity());
//                    Log.e("SelectedCarModel ", String.valueOf(selectedCarModel) + "__" + pos);
//                    if (pos.isEmpty() || Integer.parseInt(pos) >= modelArray.length()) {
//                        selectedCarModel = 0;
//                    } else if (((JSONObject) modelArray.get(Integer.parseInt(pos))).getString("model_id").equals("-1")) {
//
//                        selectedCarModel = 0;
//                    } else
//                        selectedCarModel = Integer.parseInt(pos);
//
//                    if (i == selectedCarModel) {
//                        Systems.out.println("selected_carmodel_" + "in if_ " + selectedCarModel);
//                        currentCarModel = carlay;
//                    }
//                }
//            } else {
//                idAll.setVisibility(View.GONE);
//                idAlls.setVisibility(View.VISIBLE);
//                for (int n = 0; n < modelArray.length(); n++) {
//                    int i = 0;
//                    i = n;
//                    View v = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_lay_car, idAlls, false);
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    final LinearLayout carlay = v.findViewById(R.id.lay_model_one);
//                    ViewGroup.LayoutParams lp = carlay.getLayoutParams();
//                    if (lp instanceof ViewGroup.MarginLayoutParams) {
//                        ((ViewGroup.MarginLayoutParams) lp).rightMargin = 17;
//                        ((ViewGroup.MarginLayoutParams) lp).leftMargin = 17;
//                    }
//                    carlay.setTag(i);
//                    carlay.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            selectedCarModel = Integer.parseInt(v.getTag().toString());
//                            Log.e("onclick ", String.valueOf(selectedCarModel));
//                            try {
//                                selectedModelID = ((JSONObject) modelArray.get(selectedCarModel)).getString("model_id");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            //Check whether it is outstation or rental
//                            if (selectedModelID.equals("-1")) {
//                                AlertForPackage(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.continue_package), "" + NC.getResources().getString(R.string.ok), "" + NC.getResources().getString(R.string.cancel));
//                            } else {
//                                SessionSave.saveSession("selected_carmodel", String.valueOf(selectedCarModel), getActivity());
//
//                                if (previousSelectedModel == selectedCarModel) {
//                                    currentDateTimeString = System.currentTimeMillis();
//                                    if (currentDateTimeString - previousClickedTime <= 2000) {
//                                        closeDialog();
//                                    } else
//                                        carlay_click(v, 2);
//                                } else {
//                                    carlay_click(v, 2);
//                                }
//                                previousSelectedModel = selectedCarModel;
//                                previousClickedTime = System.currentTimeMillis();
//                            }
//
//
//                        }
//                    });
//
//                    try {
//                        ((TextView) v.findViewById(R.id.txt_model1)).setText("" + modelArray.getJSONObject(i).getString("model_name").toUpperCase());
//                        Log.d("Image_url ", modelArray.getJSONObject(i).getString("unfocus_image"));
//                        Picasso.get().load(modelArray.getJSONObject(i).getString("unfocus_image")).error(R.drawable.car2_unfocus).into((ImageView) v.findViewById(R.id.txt_dra_car1));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    idAlls.addView(v);
//                    Log.e("SelectedCarModel ", String.valueOf(selectedCarModel));
//                    String pos = SessionSave.getSession("selected_carmodel", getActivity());
//                    if (pos.isEmpty())
//                        selectedCarModel = 0;
//                    else
//                        selectedCarModel = Integer.parseInt(pos);
//
//                    if (i == selectedCarModel) {
//                        Systems.out.println("selected_carmodel_" + "in else_ " + selectedCarModel);
//                        currentCarModel = carlay;
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void carlay_click(View v, int type) {
//        if (tvRequestTaxi != null) {
//            int pos = Integer.parseInt(v.getTag().toString());
//            int carPos = pos;
//            closePopup();
//            try {
//                if (map != null) {
//                    removeMarker(driverMarkerService);
//                    if (TaxiUtil.mDrivermovementdata != null) {
//                        TaxiUtil.mDrivermovementdata.clear();
//                        DriverLiveMovement(null);
//                    }
//                }
//                tvRequestTaxi.setText(NC.getString(R.string.searching_text));
//                tvRequestTaxi.setEnabled(false);
//                final JSONArray array = new JSONArray(SessionSave.getSession("model_details", getActivity()));
//                carModel = array.getJSONObject(carPos).getString("model_id");
//                String selectModel = String.valueOf(pos + 1);
//                SessionSave.saveSession("carModel", "" + carModel, getActivity());
//                int viewCount = 0;
//                LinearLayout parent;
//                if (type == 1)
//                    parent = idAll;
//                else
//                    parent = idAlls;
//                for (int i = 0; i < parent.getChildCount(); i++) {
//                    if (parent.getChildAt(i) instanceof ViewGroup) {
//                        ViewGroup vv = (ViewGroup) parent.getChildAt(i);
//                        if (i != (pos + viewCount)) {
//                            ((TextView) vv.findViewById(R.id.txt_model1)).setTextColor(CL.getColor(getActivity(), R.color.textviewcolor_light));
//                            vv.findViewById(R.id.pickup_approx_fare).setVisibility(View.GONE);
//                            Picasso.get().load(array.getJSONObject(i - viewCount).getString("unfocus_image")).error(R.drawable.car2_unfocus).into((ImageView) vv.findViewById(R.id.txt_dra_car1));
//                            if (sf.getPickuplatlng() != null) {
//                                availablecars.setText("");
//                                modelAvail.setText("");
//                                (vv.findViewById(R.id.dotsProgressBar1)).setVisibility(View.GONE);
//                            }
//                        } else {
//                            final LinearLayout temp = (LinearLayout) vv;
//                            selectedCarmodelName = modelArray.getJSONObject(i - viewCount).getString("model_name").toUpperCase();
//                            ((TextView) vv.findViewById(R.id.txt_model1)).setTextColor(CL.getColor(getActivity(), R.color.button_accept));
//                            ((TextView) vv.findViewById(R.id.txt_min_car1)).setTextColor(CL.getColor(getActivity(), R.color.button_accept));
//                            TextView textMinCar1_ = vv.findViewById(R.id.txt_min_car1);
//                            pickupApproxFare = vv.findViewById(R.id.pickup_approx_fare);
//                            pickupApproxFare.setVisibility(View.GONE);
//                            pickupApproxFare.setTextColor(CL.getColor(getActivity(), R.color.button_accept));
//                            Picasso.get().load(array.getJSONObject(i - viewCount).getString("focus_image")).error(R.drawable.car2_unfocus).into((ImageView) vv.findViewById(R.id.txt_dra_car1));
//                            dotsProgressBarSearch = vv.findViewById(R.id.dotsProgressBar1);
//
//                            if (sf.getPickuplatlng() != null) {
//                                availablecars.setText("");
//                                modelAvail.setText("");
//                                StartLineProgress();
//                            }
//
//                        }
//                    } else {
//                        viewCount = viewCount + 1;
//                    }
//                }
//                currentCarModel = (LinearLayout) v;
//                noCarsAvailable.setVisibility(View.GONE);
//                JSONObject j = new JSONObject();
//                j.put("latitude", sf.getPickuplat());
//                j.put("longitude", sf.getPickuplng());
//                j.put("motor_model", carModel);
//
//                try {
//                    String[] ss = sf.getPickupLocTxt().split(",");
//                    if (ss.length > 2)
//                        defaultCityName = ss[ss.length - 3];
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                j.put("passenger_id", SessionSave.getSession("Id", getActivity()));
//                j.put("city_name", (defaultCityName == null || defaultCityName.trim().equals("")) ? SessionSave.getSession("default_city_name", getActivity()) : defaultCityName.trim());
//                j.put("skip_fav", "2");
//                final String url = "type=nearestdriver_list";
//                if (sf.getPickuplat() != 0.0 && sf.getPickuplng() != 0.0) {
//                    SessionSave.saveSession("LastServer_latitude", "" + sf.getPickuplat(), getActivity());
//                    SessionSave.saveSession("LastServer_longitude", sf.getPickuplng() + "", getActivity());
//
//
//                    if (TaxiUtil.isOnline(getActivity())) {
//
//                        if (TaxiUtil.mDrivermovementdata != null) {
//                            TaxiUtil.mDrivermovementdata.clear();
//                            DriverLiveMovement(null);
//                        }
//                        getNearTaxi(getNearestJsonObject());
//                    } else {
//                        Log.e("Check your internet", "check the internet");
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void HoldOnClickforasec(final View v) {
//        v.setClickable(false);
//        v.setEnabled(false);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                v.setEnabled(true);
//                v.setClickable(true);
//            }
//        }, 1200);
//    }
//
//    /*
//     * this method is used to
//     * */
//    public void onSplitSuccess(double primary_Percent, double f1, double f2, double f3, double fa1, double fa2, double fa3) {
//        friendA = primary_Percent;
//        friend1S = f1;
//        friend1SA = fa1;
//        friend2S = f2;
//        friend2SA = fa2;
//        friend3S = f3;
//        friend3SA = fa3;
//        if (favDriverAvailable > 0 && SessionSave.getSession(TaxiUtil.isFavDriverOn, getActivity(), true)) {
//            alertDialog = new Dialog_Common().setmCustomDialog(getActivity(), this, NC.getResources().getString(R.string.message), favDriverMessage,
//                    NC.getResources().getString(R.string.ok),
//                    NC.getResources().getString(R.string.no_thanks), "1");
//        } else {
//            bookNow();
//        }
//
//    }
//
//    public void onBackPress() {
//        if (getActivity() != null)
//            if (booking_state == HomePage.BOOKINGSTATE.STATE_ONE) {
//
//
//                if (doubleBackToExitPressedOnce) {
//                    final Intent intent = new Intent(Intent.ACTION_MAIN);
//                    intent.addCategory(Intent.CATEGORY_HOME);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    return;
//                } else {
//                    doubleBackToExitPressedOnce = true;
//                    CToast.ShowToast(getActivity(), NC.getString(R.string.pressBack));
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            doubleBackToExitPressedOnce = false;
//                        }
//                    }, 2000);
//                }
//            } else if (booking_state == HomePage.BOOKINGSTATE.STATE_TWO) {
//                dragLatLng = null;
//                isFromBooknow = false;
//                float[] dis = new float[2];
//                try {
//                    Location.distanceBetween(sf.getPickuplat(), sf.getPickuplng(), lastKnownLat, lastKnownLng, dis);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                if (dis != null && dis[0] > 200) {
//                    if (!SessionSave.getSession(TaxiUtil.isNeedtoFetchAddress, getActivity(), false)) {
//                        sf.setPickupLocTxt(NC.getString(R.string.pinlocation));
//                        sf.setPickuplatlng(new LatLng(location.getLatitude(), location.getLongitude()));
//                    } else
//                        sf.setPickupLocTxt("");
//                }
//                booking_state = HomePage.BOOKINGSTATE.STATE_ONE;
//                clearsetPickDropMarker();
//            } else if (booking_state == HomePage.BOOKINGSTATE.STATE_THREE) {
//                isFromBooknow = false;
//                if (!tvPickLocConfirm.getText().toString().equals(NC.getString(R.string.fetching_address))) {
//                    tvRequestTaxi.setText(NC.getString(R.string.request_taxi));
//                    tvRequestTaxi.setEnabled(true);
//                    booking_state = HomePage.BOOKINGSTATE.STATE_TWO;
//                    clearsetPickDropMarker();
//                }
//            }
//    }
//
//    private void DivertToOngoingScreen(Activity mContext, String title, String message, String success_txt, String failure_txt) {
//        alertDialog = Utility.alert_view_dialog(mContext, "" + title,
//                "" + message,
//                "" + success_txt,
//                "",
//                true, new android.content.DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(android.content.DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        Intent i = new Intent(getActivity(), MainHomeFragmentActivity.class);
//                        startActivity(i);
//                    }
//                }, new android.content.DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(android.content.DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }, "");
//    }
//
//    private void DivertToTripHistory(Activity mContext, String title, String message, String success_txt, String failure_txt) {
//        alertDialog = Utility.alert_view_dialog(mContext, "" + title,
//                "" + message,
//                "" + success_txt,
//                "",
//                true, new android.content.DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(android.content.DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        Intent i = new Intent(getActivity(), MainHomeFragmentActivity.class);
//                        i.putExtra("goto", "TripHistory");
//                        startActivity(i);
//                    }
//                }, new android.content.DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(android.content.DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }, "");
//    }
//
//    private void NearestDriverResponse(boolean isSuccess, String result) {
//        availablecars.setVisibility(View.VISIBLE);
//        modelAvail.setVisibility(View.VISIBLE);
//
//
//        if (tvSkipFavourite.getVisibility() == View.GONE)
//            selectCarLay.setVisibility(View.VISIBLE);
//        if (TaxiUtil.mDriverdata.size() != 0)
//            TaxiUtil.mDriverdata.clear();
//        if (TaxiUtil.d_lat != 0) {
//            if (dropmap != null)
//                dropmap.remove();
//            dropmap = map.addMarker(new MarkerOptions().position(new LatLng(TaxiUtil.d_lat, TaxiUtil.d_lng)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pick)).draggable(false));
//        }
//        E_time = 0;
//        if (isSuccess) {
//            StopLineProgress();
//            String Driverid, Drivername, Lat, Lng, Nearest = "", distance, driver_coordinates;
//            availablecarcount = 0;
//            try {
//
//                final JSONObject json = new JSONObject(result);
//                if (json.has("zone_fare_applicable") && json.getString("zone_fare_applicable").equals("1")) {
//                    zone_fare_applicable = true;
//                    zone_zone_fare = json.getDouble("zone_zone_fare");
//                } else {
//                    zone_fare_applicable = false;
//                }
//                if (map != null) {
//                    removeMarker(driverMarkerService);
//                    if (TaxiUtil.mDrivermovementdata != null) {
//                        TaxiUtil.mDrivermovementdata.clear();
//                        DriverLiveMovement(null);
//                    }
//                }
//                if (booking_state == BOOKINGSTATE.STATE_TWO) {
//                    if (sf.getDroplatlng() != null) {
//                        dropMarker = map.addMarker(new MarkerOptions()
//                                .position(sf.getDroplatlng())
//                                .icon(BitmapDescriptorFactory.fromBitmap(CustomMarker.getMarkerBitmapFromViewForDrop(sf.getDropLocTxt(), getActivity()))));
//                        sf.dropGone();
//                        dropMarker.setTag("dropMarker");
//                        dropMarker.setAnchor(1.0f, 1f);
//                        LatLng ll = sf.getPickuplatlng() == null ? LastKnownLatLng : sf.getPickuplatlng();
//                    } else {
//                        if (!sf.getPickupLocTxt().trim().equals(""))
//                            sf.dropVisible();
//                        else
//                            sf.dropGone();
//                    }
//                }
//
//
//                SessionSave.saveSession("Server_Response", result, getActivity());
//                Log.v("favdriver", result);
//                try {
//                    if (pickupApproxFare != null && approxTravelDist != null) {
//                        Systems.out.println("traveldistanceprev" + approxTravelDist + "__" + approxTravelTime);
//                        approxFare = approxFare(getActivity(), Double.parseDouble(approxTravelDist), Double.parseDouble(approxTravelTime));
//                        if (sf.getDroplat() != 0.0 && pickupApproxFare != null) {
//                            if (zone_fare_applicable) {
//                                pickupApproxFare.setText(SessionSave.getSession("Currency", getActivity()) + decimalFormat.format(Double.parseDouble(String.format(Locale.UK, String.valueOf(zone_zone_fare)))));
//                                approxFare = zone_zone_fare;
//                            } else
//                                pickupApproxFare.setText(SessionSave.getSession("Currency", getActivity()) + decimalFormat.format(Double.parseDouble(String.format(Locale.UK, String.valueOf(approxFare)))));
//                        } else if (pickupApproxFare != null) {
//                            pickupApproxFare.setText("");
//                        }
//                        pickupApproxFare.setVisibility(View.VISIBLE);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                placesDetailArrayList.clear();
//
//
//                if (json.has("favourite_places")) {
//                    if (json.getJSONArray("favourite_places").length() > 0 && !SessionSave.getSession(TaxiUtil.isSkipFavOn, getActivity(), false)) {
//                        for (int j = 0; j < json.getJSONArray("favourite_places").length(); j++) {
//                            int i = 0;
//
//                            //To reverse the array
//                            if (SessionSave.getSession("Lang", getActivity()).equals("ar") || SessionSave.getSession("Lang", getActivity()).equals("fa"))
//                                i = json.getJSONArray("favourite_places").length() - (j + 1);
//                            else
//                                i = j;
//
//                            JSONObject jo = json.getJSONArray("favourite_places").getJSONObject(i);
//                            Double latc = jo.getDouble("latitude");
//                            Double lngc = jo.getDouble("longtitute");
//                            float[] dist = new float[1];
//                            new Location("").distanceBetween(lastKnownLat, lastKnownLng, latc, lngc, dist);
//                            if (dist[0] > 1000) {
//                                PlacesDetail o = new PlacesDetail();
//                                o.setShow(true);
//                                o.setLabel_name(TaxiUtil.firstLetterCaps(jo.getString("label_name")));
//                                o.setLatitude(jo.getDouble("latitude"));
//                                o.setLongtitute(jo.getDouble("longtitute"));
//                                o.setLocation_name(jo.getString("location_name"));
//                                Systems.out.println("nnn---jo.getString(\"android_icon\")" + jo.getString("android_icon"));
//                                o.setAndroid_image_unfocus(jo.getString("android_icon"));
//                                placesDetailArrayList.add(o);
//                            }
//                            tvSkipFavourite.setText(NC.getString(R.string.skip_favourite));
//                        }
//                    } else if (json.getJSONArray("popular_places").length() > 0) {
//                        for (int j = 0; j < json.getJSONArray("popular_places").length(); j++) {
//                            int i = 0;
//                            //To reverse the array
//                            if (SessionSave.getSession("Lang", getActivity()).equals("ar") || SessionSave.getSession("Lang", getActivity()).equals("fa"))
//                                i = json.getJSONArray("popular_places").length() - (j + 1);
//                            else
//                                i = j;
//                            JSONObject jo = json.getJSONArray("popular_places").getJSONObject(i);
//                            Double latc = jo.getDouble("latitude");
//                            Double lngc = jo.getDouble("longtitute");
//                            float[] dist = new float[1];
//                            new Location("").distanceBetween(lastKnownLat, lastKnownLng, latc, lngc, dist);
//                            if (dist[0] > 1000) {
//                                PlacesDetail o = new PlacesDetail();
//                                o.setShow(false);
//                                o.setLabel_name(TaxiUtil.firstLetterCaps(jo.getString("label_name")));
//                                o.setLatitude(jo.getDouble("latitude"));
//                                o.setLongtitute(jo.getDouble("longtitute"));
//                                o.setLocation_name(jo.getString("location_name"));
//                                o.setAndroid_image_unfocus((jo.getString("android_icon")));
//                                placesDetailArrayList.add(o);
//                            }
//                            tvSkipFavourite.setText(NC.getString(R.string.skip_popular));
//                        }
//                    }
//
//                    if (placesDetailArrayList.size() == 0) {
//                        tvSkipFavourite.setText(NC.getString(R.string.skip_drop_location));
//
//                    } else {
//
//                        if (placesDetailArrayList.size() > 3)
//                            for (int i = placesDetailArrayList.size() - 1; i >= 3; i--) {
//                                placesDetailArrayList.remove(i);
//                            }
//
//                        JSONArray popular_place_array = new JSONArray();
//                        for (int i = 0; i < placesDetailArrayList.size(); i++) {
//                            PlacesDetail o = placesDetailArrayList.get(i);
//                            JSONObject ss = new JSONObject();
//                            ss.put("is_show", o.isShow());
//                            ss.put("label_name", o.getLabel_name());
//                            ss.put("latitude", o.getLatitude());
//                            ss.put("longtitute", o.getLongtitute());
//                            ss.put("location_name", o.getLocation_name());
//                            ss.put("android_icon", o.getAndroid_image_unfocus());
//                            popular_place_array.put(ss);
//                        }
//                        SessionSave.saveSession("popular_places", popular_place_array.toString(), getActivity());
//                    }
//
//
//                    setfav_drop();
//                }
//                if (json.getInt("status") == 1) {
//
//                    favDriverAvailable = json.getInt("fav_drivers");
//                    favDriverMessage = json.getString("fav_driver_message");
//                    final JSONArray jarray = json.getJSONArray("detail");
//                    SessionSave.saveSession("driver_around_miles", json.getString("driver_around_miles"), getActivity());
//                    final int l = jarray.length();
//                    if (jarray.length() > 0) {
//                        if (jarray.getJSONObject(0).has("travel_modelid")) {
//                            travelModelId = jarray.getJSONObject(0).getString("travel_modelid");
//                            int next_higher_model = json.getInt("next_higher_model");
//                            String pass_confirm_mess = json.getString("pass_confirm_mess");
//                        } else {
//                            travelModelId = "";
//                        }
//                    }
//                    driverIdData = new ArrayList<>();
//                    try {
//                        for (int i = 0; i < l; i++) {
//                            Driverid = jarray.getJSONObject(i).getString("driver_id");
//                            Drivername = "";// jarray.getJSONObject(i).getString("name");
//                            Lat = jarray.getJSONObject(i).getString("latitude");
//                            Lng = jarray.getJSONObject(i).getString("longitude");
//
//                            Nearest = jarray.getJSONObject(i).getString("nearest_driver");
//                            distance = jarray.getJSONObject(i).getString("distance_km");
//                            driver_coordinates = jarray.getJSONObject(i).getString("driver_coordinates");
//                            List<String> listlatlng = new ArrayList<String>();
//                            String[] latlng = driver_coordinates.split("#");
//                            for (int x = 0; x < latlng.length; x++) {
//                                listlatlng.add(latlng[x]);
//                            }
//                            driverIdData.add(Driverid);
//                            final DriverData data = new DriverData(Driverid, Drivername, speed, Lat, Lng, Nearest, distance, null, listlatlng);
//                            TaxiUtil.mDriverdata.add(data);
//                            minFare = json.getJSONObject("fare_details").getString("min_fare");
//                            modelSize = json.getJSONObject("fare_details").getString("model_size");
//                            fareMinimumPpl.setText("1-" + modelSize);
//
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    tvRequestTaxi.setText(NC.getString(R.string.request_taxi));
//                    tvRequestTaxi.setEnabled(true);
//                    FindNearestlocal();
//                } else if (json.getInt("status") == 100 /*&& isSocket*/) {
//                    closeDialog();
//                    SessionSave.saveSession("locaket_session", "0", MainHomeFragmentActivity.context);
//                    if (currentCarModel != null) {
//                        Log.v("performClick ", " performClick 4");
//                        currentCarModel.performClick();
//                    }
//                } else if (json.getInt("status") == -101) {
//                    forceLogout();
//                } else {
//                    closeDialog();
//                    if (driverDelayHandler != null) {
//                        driverDelayHandler.removeCallbacks(driverLocationHistoryRunnable);
//                    }
//                    DriverLiveMovement(null);
//
//                    tvRequestTaxi.setText(NC.getString(R.string.car_not_available));
//                    StopLineProgress();
//                    availablecars.setText("- " + NC.getResources().getString(R.string.nodrivers));
//                    modelAvail.setText(selectedCarmodelName);
//                    minFare = json.getJSONObject("fare_details").getString("min_fare");
//                    modelSize = json.getJSONObject("fare_details").getString("model_size");
//                    // eta_ = "0";
//                    //   fare_minimum.setText(SessionSave.getSession("Currency", getActivity()) + " " + min_fare);
//                    //fare_minimum_ppl.setText(min_ppl + " " + getString(R.string.ppl));
//                    fareMinimumPpl.setText("1-" + modelSize);
//
//                    if (booking_state == BOOKINGSTATE.STATE_TWO) {
//                        if (pickupMarker != null)
//                            pickupMarker.remove();
//                        if (TaxiUtil.mDriverdata.size() > 0) {
//                            if (E_time == 0.0) {
//                                E_time = 1;
//                            }
//                        }
//                        Bitmap b = CustomMarker.getMarkerBitmapFromView(String.valueOf((int) E_time), getActivity(), sf.getPickupLocTxt());
//                        try {
//                            pickupMarker = map.addMarker(new MarkerOptions()
//                                    .position(sf.getPickuplatlng())
//                                    .icon(BitmapDescriptorFactory.fromBitmap(b)));
//                            pickupMarker.setTag("pickup");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        pickupMarker.setAnchor(0.0f, 1f);
//                        if (sf.getDroplatlng() == null)
//                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sf.getPickuplat(), sf.getPickuplng()), 16f));
//                    }
//                }
//            } catch (final Exception e) {
//                e.printStackTrace();
//                closeDialog();
//                stopLineProgressOnError();
//            }
//        } else {
//            closeDialog();
//            getActivity().runOnUiThread(new Runnable() {
//                public void run() {
//                    if (driverDelayHandler != null) {
//                        driverDelayHandler.removeCallbacks(driverLocationHistoryRunnable);
//                    }
//                    DriverLiveMovement(null);
//                    CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error));
//                }
//            });
//        }
//    }
//
//    private void stopLineProgressOnError() {
//        if (driverDelayHandler != null) {
//            driverDelayHandler.removeCallbacks(driverLocationHistoryRunnable);
//        }
//        DriverLiveMovement(null);
//        StopLineProgress();
//        tvRequestTaxi.setText(NC.getString(R.string.car_not_available));
//        if (getActivity() != null) {
//            CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error));
//        }
//    }
//
//    private void forceLogout() {
//
//        TaxiUtil.API_BASE_URL = "";
//        SessionSave.saveSession("base_url", "", getActivity());
//        SessionSave.saveSession("Id", "", getActivity());
//        SessionSave.clearAllSession(getActivity());
//        getActivity().stopService(new Intent(getActivity(), GetPassengerUpdate.class));
//        getActivity().startActivity(new Intent(getActivity(), SplashActivity.class));
//    }
//
//    /**
//     * @param data list of marker to update
//     */
//    private void updateMarker(ArrayList<DriverData> data) {
//        if (booking_state != BOOKINGSTATE.STATE_THREE)
//            if (data != null) {
//                for (DriverData driverData : data) {
//                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(Double.parseDouble(driverData.getLat()), Double.parseDouble(driverData.getLng())));
//                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.car_movement_icon));
//                    final Marker marker = map.addMarker(markerOptions);
//                    marker.setFlat(true);
//                    CarMovementAnimation.getInstance().addMarkerAnimate(marker);
//                    driverMarkerService.add(marker);
//                }
//
//            }
//    }
//
//    /**
//     * @param data list of marker to update
//     */
//    private void removeMarker(ArrayList<Marker> data) {
//        if (data != null) {
//            for (Marker marker : data) {
//                removeMarkerWithAnimation(marker);
//
//            }
//            driverMarkerService.removeAll(data);
//
//        }
//    }
//
//    /**
//     * Method to find the nearest driver
//     */
//    public void FindNearestlocal() {
//        try {
//            if (TaxiUtil.mDriverdata.size() != 0) {
//                double Driverdistance = 0;
//
//                if (TaxiUtil.d_lat != 0)
//                    dropmap = map.addMarker(new MarkerOptions().position(new LatLng(TaxiUtil.d_lat, TaxiUtil.d_lng)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pick)).draggable(false));
//                availablecarcount = 0;
//                for (int i = 0; i < TaxiUtil.mDriverdata.size(); i++) {
//                    if (i == 0) {
//                        if (SessionSave.getSession("isBUISNESSKEY", getActivity(), true)) {
//                            new FindApproxDistance(HomePage.this).getDistance(getActivity(), sf.getPickuplat(), sf.getPickuplng(), Double.parseDouble(TaxiUtil.mDriverdata.get(i).getLat()), Double.parseDouble(TaxiUtil.mDriverdata.get(i).getLng()), DISTANCE_TYPE_FOR_ETA);
//                        } else
//                            Driverdistance = 0.5 + FindDistance.distance(sf.getPickuplat(), sf.getPickuplng(), Double.parseDouble(TaxiUtil.mDriverdata.get(i).getLat()), Double.parseDouble(TaxiUtil.mDriverdata.get(i).getLng()), SessionSave.getSession("Metric_type", getActivity()), location);
//                    }
//                    availablecarcount++;
//                }
//                TaxiUtil.mDrivermovementdata = TaxiUtil.mDriverdata;
//                DriverLiveMovement(TaxiUtil.mDrivermovementdata);
//                driverDelayHandler.removeCallbacks(driverLocationHistoryRunnable);
//                driverDelayHandler.post(driverLocationHistoryRunnable);
//
//                if (availablecarcount != 0) {
//                    if (!SessionSave.getSession("isBUISNESSKEY", getActivity(), true))
//                        new Approximate_Time(Driverdistance, SessionSave.getSession("Metric_type", getActivity()), DISTANCE_TYPE_FOR_ETA).execute();
//                    availablecars.setText("- " + String.format(Locale.UK, String.valueOf(availablecarcount)) + " " + NC.getResources().getString(R.string.avaiable_car));
//                    modelAvail.setText(selectedCarmodelName);
//                    StopLineProgress();
//                } else {
//                    StopLineProgress();
//                    E_time = 0;
//                    availablecars.setText("- " + NC.getResources().getString(R.string.nodrivers));
//                    modelAvail.setText(selectedCarmodelName);
//                    if (booking_state == BOOKINGSTATE.STATE_TWO) {
//                        if (pickupMarker != null)
//                            pickupMarker.remove();
//                        if (!sf.getPickupLocTxt().trim().equals("") && booking_state == BOOKINGSTATE.STATE_TWO) {
//                            if (TaxiUtil.mDriverdata.size() > 0) {
//                                if (E_time == 0.0) {
//                                    E_time = 1;
//                                }
//                            }
//                            Bitmap b = CustomMarker.getMarkerBitmapFromView(String.valueOf((int) E_time), getActivity(), sf.getPickupLocTxt());
//                            pickupMarker = map.addMarker(new MarkerOptions()
//                                    .position(sf.getPickuplatlng())
//                                    .icon(BitmapDescriptorFactory.fromBitmap(b)));
//                            pickupMarker.setTag("pickup");
//                            pickupMarker.setAnchor(0.0f, 1f);
//                            if (sf.getDroplatlng() == null)
//                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sf.getPickuplat(), sf.getPickuplng()), 16f));
//                        }
//                    }
//                }
//
//                if (isFromBooknow) {
//                    callBookNow();
//                }
//
//            } else {
//
//                E_time = 0;
//                availablecars.setText("- " + NC.getResources().getString(R.string.nodrivers));
//                modelAvail.setText(selectedCarmodelName);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void closePopup() {
//        final Handler handler = new Handler();
//        if (booking_state != BOOKINGSTATE.STATE_ONE)
//            instructionHeader.setVisibility(View.VISIBLE);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (booking_state != BOOKINGSTATE.STATE_THREE) {
//                    instructionHeader.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            instructionHeader.setAlpha(1);
//                            instructionHeader.setVisibility(View.GONE);
//                        }
//                    });
//                }
//            }
//        }, 12000);
//    }
//
//    private void setfav_drop() {
//        favBotLay.removeAllViews();
//        for (PlacesDetail p : placesDetailArrayList) {
//            if (p.isShow()) {
//                View v = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_fav_itme, favBotLay, false);
//                LinearLayout ll = v.findViewById(R.id.fav_item_lay);
//                ll.setTag(p);
//                Glide.with(getActivity()).load(p.android_image_unfocus)
//                        .apply(new RequestOptions().centerCrop().override(100, 100).placeholder(R.drawable.fav_placeholder).error(R.drawable.fav_placeholder))
//                        .into((ImageView) v.findViewById(R.id.fav_image));
//                ll.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        PlacesDetail p = (PlacesDetail) view.getTag();
//                        sf.setDropLocTxt(p.getLocation_name());
//                        sf.setDroplatlng(new LatLng(p.getLatitude(), p.getLongtitute()));
//                    }
//                });
//
//                ImageView fav_image = v.findViewById(R.id.fav_image);
//                TextView fav_label = v.findViewById(R.id.fav_label);
//                fav_label.setEnabled(true);
//                fav_label.setTypeface(Typeface.DEFAULT_BOLD);
//                fav_label.setText(p.getLabel_name());
//                fav_label.setEnabled(false);
//                favBotLay.addView(v);
//            }
//        }
//    }
//
//    public void StartLineProgress() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ivLine.setVisibility(View.VISIBLE);
//            ivLine.setBackground(getActivity().getDrawable(R.drawable.progress_line_anim));
//            animatedVectorDrawable = (AnimatedVectorDrawable) ivLine.getBackground();
//            repeatAnimation();
//        } else {
//            ivLine.setVisibility(View.GONE);
//        }
//    }
//
//    public void StopLineProgress() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (animatedVectorDrawable != null) {
//                animatedVectorDrawable.stop();
//                ivLine.setVisibility(View.GONE);
//            }
//        } else {
//            if (dotsProgressBarSearch != null)
//                dotsProgressBarSearch.setVisibility(View.GONE);
//        }
//    }
//
//    /**
//     * Book later dialog popup
//     */
//    private void book_later_fun() {
//        try {
//
//            Locale locale = new Locale("EN");
//            Locale.setDefault(locale);
//            Configuration config = new Configuration();
//            config.locale = locale;
//            getActivity().getResources().updateConfiguration(config, null);
//            if (dt_mDialog != null && dt_mDialog.isShowing())
//                dt_mDialog.cancel();
//
//            z = 1;
//            pickupTime = "";
//            final View r_view = View.inflate(getActivity(), R.layout.date_time_picker_dialog, null);
//            dt_mDialog = new Dialog(getActivity(), R.style.dialogwinddow);
//            dt_mDialog.setContentView(r_view);
//            dt_mDialog.setCancelable(true);
//            dt_mDialog.show();
//            final DatePicker _datePicker = dt_mDialog.findViewById(R.id.datePicker1);
//            final TimePicker _timePicker = dt_mDialog.findViewById(R.id.timePicker1);
//
//            Calendar c = Calendar.getInstance();
//            _timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY) + 1);
//            _timePicker.setCurrentMinute(c.get(Calendar.MINUTE) + 1);
//
//            _timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//                @Override
//                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                    Calendar c = Calendar.getInstance();
//                    if (_datePicker.getDayOfMonth() == c.get(Calendar.DAY_OF_MONTH)) {
//                        if ((c.get(Calendar.HOUR_OF_DAY) + 1) > hourOfDay) {
//                            _timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY) + 1);
//                            _timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
//                        }
//                        if ((c.get(Calendar.HOUR_OF_DAY) + 1) >= hourOfDay && (c.get(Calendar.MINUTE)) > minute) {
//                            _timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY) + 1);
//                            _timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
//                        }
//                    }
//                }
//
//            });
//            Time now = new Time();
//            now.setToNow();
//            _datePicker.updateDate(now.year, now.month, now.monthDay);
//            _datePicker.setMinDate(c.getTimeInMillis() - 1000);
//            final Button butConfirmTime = dt_mDialog.findViewById(R.id.butConfirmTime);
//            TextView f_textview = dt_mDialog.findViewById(R.id.f_textview);
//            if (SessionSave.getSession("Lang", getActivity()).equals("ar") || SessionSave.getSession("Lang", getActivity()).equals("fa")) {
//                f_textview.setText(NC.getString(R.string.select_date_arab));
//                butConfirmTime.setText(NC.getString(R.string.submit_arab));
//            }
//            if (_datePicker.getVisibility() == View.VISIBLE) {
//                butConfirmTime.setText(NC.getString(R.string.setDate));
//            }
//            butConfirmTime.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    if (_datePicker.isShown()) {
//                        _timePicker.setVisibility(View.VISIBLE);
//                        _datePicker.setVisibility(View.GONE);
//                        if (_timePicker.getVisibility() == View.VISIBLE) {
//                            butConfirmTime.setText(NC.getString(R.string.set_time));
//                        }
//                    } else {
//                        getCurrentDateAndTime(_timePicker, _datePicker);
//                        String seletecedString = "" + year + "/" + month + "/" + date + " " + _timePicker.getCurrentHour() + ":" + _timePicker.getCurrentMinute();
//                        if (hoursAgo(seletecedString) > 0) {
//                            pickupTimeAndDate = "" + year + "-" + month + "-" + date + " " + _hour + ":" + min + ":" + "00" + " " + _ampm;
//                            if (sf.getPickuplatlng() != null && !sf.getPickupLocTxt().equalsIgnoreCase("" + NC.getResources().getString(R.string.fetching_address)))
//                                if (sf.getDroplatlng() != null && sf.getDroplat() != 0.0) {
//                                    UpdateApproximateDistance(approximateTime, approximateDistance, DISTANCE_TYPE_FOR_BOOK_LATER, 1);
//                                } else {
//                                    Apicall_Book_After("0", "0");
//                                }
//                            else
//                                CToast.ShowToast(getActivity(), "pickup address try again");
//                        } else {
//                            alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.hour_must_greater_than), "" + NC.getResources().getString(R.string.ok), "");
//                        }
//                        dt_mDialog.dismiss();
//                    }
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            textviewBookLater.setClickable(true);
//        }
//        dt_mDialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(android.content.DialogInterface dialogInterface) {
//                if (getActivity() != null)
//                    ((MainHomeFragmentActivity) getActivity()).setLocale();
//            }
//        });
//    }
//
//    /**
//     * Method to get date and time from date picker dialog
//     *
//     * @param _timePicker - timePikcer object to get selected time
//     * @param _datePicker - datePicker object to get selected date
//     */
//    public void getCurrentDateAndTime(TimePicker _timePicker, DatePicker _datePicker) {
//        _hour = _timePicker.getCurrentHour();
//        min = _timePicker.getCurrentMinute();
//        date = _datePicker.getDayOfMonth();
//        month = _datePicker.getMonth() + 1;
//        year = _datePicker.getYear();
//        ampmValidation(_hour);
//    }
//
//    /**
//     * this method is used to check the am & pm for given input time
//     *
//     * @param inputHour hour is given as input
//     */
//    private String ampmValidation(int inputHour) {
//
//        if (inputHour >= 13) {
//            _hour = inputHour - 12;
//            _ampm = "PM";
//        } else if (inputHour == 12) {
//            _ampm = "PM";
//        } else if (inputHour == 0) {
//            _hour = 12;
//            _ampm = "AM";
//        } else {
//            _ampm = "AM";
//        }
//        return _ampm;
//    }
//
//    /**
//     * Dilog pops up which store promo code and verify while save_booking api is called
//     */
//    public void promocode() {
//        final View view = View.inflate(getActivity(), R.layout.forgot_popup, null);
//        final Dialog mDialog = new Dialog(getActivity(), R.style.NewDialog);
//        mDialog.setContentView(view);
//        mDialog.setCancelable(false);
//        mDialog.setCanceledOnTouchOutside(false);
//        mDialog.show();
//        Colorchange.ChangeColor(mDialog.findViewById(R.id.inner_content), getActivity());
//        FontHelper.applyFont(getActivity(), view);
//        final Spinner ftmobilecodespn = mDialog.findViewById(R.id.ftmobilecodespn);
//        final EditText mail = mDialog.findViewById(R.id.forgotmail);
//        mail.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        int maxLengthpromoCode = getResources().getInteger(R.integer.promoMaxLength);
//        mail.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
//        mail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthpromoCode)});
//        InputFilter[] editFilters = mail.getFilters();
//        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
//        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
//        newFilters[editFilters.length] = new InputFilter.AllCaps();
//        mail.setFilters(newFilters);
//        mail.setHint(NC.getResources().getString(R.string.enter_promo_code));
//        mail.setText(promoCode);
//        final TextView OK = mDialog.findViewById(R.id.okbtn);
//        OK.setText(NC.getResources().getString(R.string.apply));
//        final TextView Cancel = mDialog.findViewById(R.id.cancelbtn);
//        Cancel.setVisibility(View.VISIBLE);
//
//
//        Point pointSize = new Point();
//        getActivity().getWindowManager().getDefaultDisplay().getSize(pointSize);
//        Cancel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                View view = getActivity().getCurrentFocus();
//                if (view != null) {
//                    mail.onEditorAction(EditorInfo.IME_ACTION_DONE);
//                }
//                mDialog.dismiss();
//            }
//        });
//        OK.setOnClickListener(new View.OnClickListener() {
//            private String Phone;
//
//            @Override
//            public void onClick(final View arg0) {
//                try {
//                    Phone = mail.getText().toString();
//                    View view = getActivity().getCurrentFocus();
//                    if (view != null) {
//                        mail.onEditorAction(EditorInfo.IME_ACTION_DONE);
//                    }
//                    if (Phone.trim().equals(""))
//                        CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.promo_code_empty));
//                    promoCode = Phone;
//                    mDialog.dismiss();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    /**
//     * Custom alert dialog used in entire project.can call from anywhere with the following
//     *
//     * @param title       set the title for alert dialog
//     * @param message     set the message for alert dialog
//     * @param success_txt set the success text in success button
//     * @param failure_txt set the failure text in failure button
//     */
//    public void alert_view(Activity mContext, String title, String message, String success_txt, String failure_txt) {
//        try {
//
//
//            alertDialog = Utility.alert_view_dialog(mContext, "" + title,
//                    "" + message,
//                    "" + success_txt,
//                    "",
//                    true, new android.content.DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(android.content.DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }, new android.content.DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(android.content.DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }, "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * @param mContext    - Context to show alert dialog
//     * @param title       - title for alert dialog
//     * @param message     - message for alert dialog
//     * @param success_txt - success text in success button
//     * @param failure_txt - ailure text in failure button
//     */
//    public void AlertForPackage(Activity mContext, String title, String message, String success_txt, String failure_txt) {
//        try {
//
//
//            alertDialog = Utility.alert_view_dialog(mContext, "" + title,
//                    "" + message,
//                    "" + success_txt,
//                    "" + failure_txt,
//                    true, new android.content.DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(android.content.DialogInterface dialog, int which) {
//
//                            JSONObject jsonObject = new JSONObject();
//                            try {
//
//                                jsonObject.put("latitude", String.valueOf(sf.getPickuplat()));
//                                jsonObject.put("longitude", String.valueOf(sf.getPickuplng()));
//                                jsonObject.put("drop_latitude", String.valueOf(sf.getDroplat()));
//                                jsonObject.put("drop_longitude", String.valueOf(sf.getDroplng()));
//                                jsonObject.put("pickupplace", sf.getPickupLocTxt());
//                                jsonObject.put("dropplace", sf.getDropLocTxt());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            Intent in = new Intent(getActivity(), WebviewAct.class);
//                            in.putExtra("post_params", jsonObject.toString());
//                            in.putExtra("type", "");
//                            startActivity(in);
//                            if (driverDelayHandler != null) {
//                                driverDelayHandler.removeCallbacks(driverLocationHistoryRunnable);
//                            }
//                            dialog.dismiss();
//
//                        }
//                    }, new android.content.DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(android.content.DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }, "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /*
//     * this method is used to calculate time trip time
//     * */
//    public double calculatetime(double distance, String metric, int type) {
//        double distance_m = 0.0;
//
//        try {
//            double dist = distance;
//            double timez;
//            timez = dist / Double.parseDouble(speed);
//            timez = timez * 3600; // time duration in seconds
//            double minutes = Math.floor(timez / 60);
//            timez -= minutes * 60;
//            double seconds1 = Math.floor(timez);
//            String timeString = (int) minutes + "." + (int) seconds1;
//            float minsfloatValue = Float.parseFloat(timeString);
//
//            distance_m = Math.round(minsfloatValue * 100.0) / 100.0;
//
//            if (distance_m <= 1) {
//                distance_m = 1;
//            }
//            if (type == DISTANCE_TYPE_FOR_ETA) {
//                E_time = distance_m;
//
//            } else if (type == DISTANCE_TYPE_FOR_BOOK_LATER) {
//                approxTravelTime = String.valueOf(distance_m);
//                approxTravelDist = String.valueOf(distance);
//
//                if (isBookAfter)
//                    Apicall_Book_After(approxTravelDist, approxTravelTime);
//                else {
//                    approxFare = approxFare(getActivity(), Double.parseDouble(approxTravelDist), distance_m);
//                    if (!SessionSave.getSession(TaxiUtil.isSplitOn, getActivity(), true)) {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (favDriverAvailable > 0 && SessionSave.getSession(TaxiUtil.isFavDriverOn, getActivity(), true)) {
//                                    alertDialog = new Dialog_Common().setmCustomDialog(getActivity(), HomePage.this, NC.getResources().getString(R.string.message), favDriverMessage,
//                                            NC.getResources().getString(R.string.ok),
//                                            NC.getResources().getString(R.string.no_thanks), "1");
//                                } else {
//                                    bookNow();
//                                }
//                            }
//                        });
//                    } else {
//                        closeDialog();
//                        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    alertDialog = Utility.alert_view_dialog(getActivity(), "", NC.getResources().getString(R.string.split_fare), NC.getResources().getString(R.string.yes), NC.getResources().getString(R.string.no), true, new android.content.DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(android.content.DialogInterface dialog, int which) {
//                                            ActivityCompat.requestPermissions(getActivity(),
//                                                    new String[]{Manifest.permission.READ_CONTACTS},
//                                                    MY_PERMISSIONS_REQUEST_CONTACTS);
//                                        }
//                                    }, new android.content.DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(android.content.DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    }, "");
//                                }
//                            });
//                        } else {
//                            Message message = handler.obtainMessage(0, "");
//                            message.sendToTarget();
//                        }
//                    }
//                }
//            } else if (type == DISTANCE_TYPE_FOR_FARE) {
//                approxTravelTime = String.valueOf(distance_m);
//                approxTravelDist = String.valueOf(distance);
//                approxFare = approxFare(getActivity(), distance, distance_m);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return distance_m;
//    }
//
//    //All override methods found below.
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Log.d("onActivityCreated", "onActivityCreated: ");
//    }
//
//    @Override
//    public void pickUpSet(double latitude, double longtitue) {
//        if (getActivity() != null) {
//            if (LastKnownLatLng == null)
//                LastKnownLatLng = new LatLng(latitude, longtitue);
//
//
//            if (!selectCarLay.isShown() && booking_state != BOOKINGSTATE.STATE_THREE /*&& booking_state != BOOKINGSTATE.STATE_ONE*/) {
//                if (PickupDropSearchActivity.SET_FOR_PICKUP) {
//                    float[] dis = new float[1];
//                    Location.distanceBetween(lastKnownLat, lastKnownLng, sf.getPickuplat(), sf.getPickuplng(), dis);
//                    if (dis[0] > 200)
//                        booking_state = BOOKINGSTATE.STATE_TWO;
//                    clearsetPickDropMarker();
//                    PickupDropSearchActivity.SET_FOR_PICKUP = false;
//                }
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (currentCarModel != null) {
//                            Systems.out.println("performClick " + "performClick 5");
//                            currentCarModel.performClick();
//                        }
//                    }
//                }, 1000);
//
//
//            } else if (selectCarLay.isShown() && booking_state == BOOKINGSTATE.STATE_TWO) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        instructionHeader.setText(NC.getString(R.string.tap_to_edit));
//                        instructionHeader.setVisibility(View.VISIBLE);
//                        closePopup();
//                        route.setUpPolyLine(map, getActivity(), sf.getPickuplatlng(), sf.getDroplatlng(), sf.getLatLngPoints());
//                    }
//                }, 2000);
//            } else if (booking_state == BOOKINGSTATE.STATE_THREE) {
//                clearsetPickDropMarker();
//                tvPickLocConfirm.setText(sf.getPickupLocTxt());
//                textConfirmPickup.setVisibility(View.VISIBLE);
//            }
//            if (booking_state == BOOKINGSTATE.STATE_ONE) {
//                if (!sf.getPickupLocTxt().trim().equals("")) {
//                    sf.dropVisible();
//                } else
//                    sf.dropGone();
//            }
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mapWrapperLayout.setVisibility(View.VISIBLE);
//                    tvSkipFavourite.setVisibility(View.VISIBLE);
//                }
//            }, 1000);
//        }
//    }
//
//    @Override
//    public void dropSet(double latitude, double longtitue) {
//        booking_state = BOOKINGSTATE.STATE_TWO;
//        sf.dropGone();
//        StopLineProgress();
//        clearsetPickDropMarker();
//        pickupApproxFare.setText("");
//        instructionHeader.setText(NC.getString(R.string.tap_to_edit));
//        instructionHeader.setVisibility(View.VISIBLE);
//
//        closePopup();
//    }
//
//    @Override
//    public void requestPickupAddress() {
//        if (sf.getPickupLocTxt() == null || sf.getPickupLocTxt().equals("")
//                || sf.getPickupLocTxt().equals(NC.getString(R.string.pinlocation)) || sf.getPickupLocTxt().equals(NC.getString(R.string.fetching_address))) {
//            NeedtoGetAddress();
//        }
//    }
//
//    @Override
//    public void trigger_FragPopFront() {
//        ((MainHomeFragmentActivity) requireActivity()).showDarkStatusBarIcon();
//        if (requireActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrag) instanceof HomePage) {
//            ((MainHomeFragmentActivity) requireActivity()).homePage_title();
//            ((MainHomeFragmentActivity) requireActivity()).toolbarRightIcon(false);
//            ((MainHomeFragmentActivity) requireActivity()).left_icon.setImageResource(R.drawable.menu);
//            ((MainHomeFragmentActivity) requireActivity()).left_icon.setTag("menu");
//            ((MainHomeFragmentActivity) requireActivity()).enableSlide();
//
//            if (driverDelayHandler != null) {
//                driverDelayHandler.removeCallbacks(driverLocationHistoryRunnable);
//                driverDelayHandler.postDelayed(driverLocationHistoryRunnable, 4000);
//            }
//
//        }
//        if (favBotLay != null) {
//            for (int i = 0; i < favBotLay.getChildCount(); i++) {
//                TextView tv = favBotLay.getChildAt(i).findViewById(R.id.fav_label);
//                tv.setTypeface(Typeface.DEFAULT_BOLD);
//            }
//        }
//
//        if (getActivity() != null && currentCarModel != null && !selectedModelID.equals("-1") && booking_state != BOOKINGSTATE.STATE_THREE) {
//            currentCarModel.performClick();
//        }
//
//    }
//
//    @Override
//    public void onSuccess(Dialog dialog, String resultcode) {
//        if (resultcode.equals("1")) {
//            bookFavDriver = 1;
//            bookNow();
//        } else if (resultcode.equals("4")) {
//            bookFavDriver = 2;
//            bookNow();
//        }
//        if (dialog != null) {
//            dialog.dismiss();
//        }
//    }
//
//    @Override
//    public void onFailure(Dialog dialog, String resultcode) {
//        if (resultcode.equals("1")) {
//            bookFavDriver = 2;
//            bookNow();
//        }
//        if (dialog != null) {
//            dialog.dismiss();
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.mov_cur_loc:
//                MapWrapperLayout.setmMapIsTouched(true);
//                try {
//                    if (sf.getPickuplat() == 0) {
//                        getPickupAdress();
//                    } else {
//                        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//                        if (location != null)
//                            LastKnownLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                        if (map != null && LastKnownLatLng != null & getActivity() != null)
//                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(LastKnownLatLng, zoomLevel));
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//
//            case R.id.textBookLater:
//                isBookAfter = true;
//                book_later_fun();
//                break;
//
//            case R.id.textConfirmPickup:
//                showDialog();
//                if (!sf.getPickupLocTxt().trim().equals("") && !tvPickLocConfirm.getText().toString().equals(NC.getString(R.string.fetching_address))) {
//                    if (availablecarcount > 0) {
//                        ArrayList<StopData> mList = sf.getStopPoints();
//                        StopData stopdata = mList.get(0);
//                        if (LocationUtils.INSTANCE.calculateDistanceInMeter(sf.getPickuplatlng()
//                                , new LatLng(stopdata.getLat(), stopdata.getLng())) > SLAB_DEVIATION_IN_METER) {
//                            stopdata.setLat(sf.getPickuplat());
//                            stopdata.setLng(sf.getPickuplng());
//                            stopdata.setPlaceName(sf.getPickupLocTxt());
//                            mList.set(0, stopdata);
//                            sf.setStopPoints(mList);
//                            Systems.out.println("routee getApproximateDistance called " + mList);
//                            route.getApproximateDistance(getActivity(), sf.getPickuplatlng(), sf.getDroplatlng(), sf.getLatLngPoints());
//                        } else {
//                            callSaveBooking();
//                        }
//
//                    } else {
//                        closeDialog();
//                        ShowToast.center(getActivity(), NC.getString(R.string.no_taxi));
//                    }
//                } else {
//                    getPickupAdress();
//                }
//
//                break;
//
//        }
//    }
//
//    /*
//     * this method is used to get the location accuracy
//     * */
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        try {
//            if (getActivity() != null) {
//                location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//                Systems.out.println("onconnected" + location);
//                float[] dis = new float[1];
//                boolean isMoved = false;
//                if (LastKnownLatLng != null) {
//                    Location.distanceBetween(lastKnownLat, lastKnownLng, location.getLatitude(), location.getLongitude(), dis);
//                    if (dis[0] > 200) {
//                        isMoved = true;
//                    }
//                } else {
//                    isMoved = true;
//                }
//                if (location != null && map != null) {
//                    lastKnownLat = location.getLatitude();
//                    lastKnownLng = location.getLongitude();
//
//                    mapWrapperLayout.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mapWrapperLayout.setVisibility(View.VISIBLE);
//                        }
//                    });
//                    Log.d("onMapReady", "locationChanged" + lastKnownLat + "__" + lastKnownLng);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            tvSkipFavourite.setVisibility(View.VISIBLE);
//                            if (getActivity() != null && placesDetailArrayList.size() == 0 && tvSkipFavourite.getText().toString().equalsIgnoreCase(NC.getString(R.string.skip_popular))) {
//                                if (currentCarModel != null && !selectedModelID.equals("-1")) {
//                                    Systems.out.println("performClick " + "performClick 8");
//                                    currentCarModel.performClick();
//                                }
//                            }
//
//                        }
//                    }, 1000);
//
//                    LastKnownLatLng = new LatLng(lastKnownLat, lastKnownLng);
//                    if (sf.getPickuplat() == 0.0) {
//                        sf.setPickuplatlng(LastKnownLatLng);
//                        movetoCurrentloc();
//                    }
//                    map.setMyLocationEnabled(true);
//                    map.getUiSettings().setMyLocationButtonEnabled(false);
//                    if (sf.getDroplatlng() == null)
//                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LastKnownLatLng, zoomLevel));
//
//                    /* if (!book_again_msg) {
//                        sf.setPickuplatlng(LastKnownLatLng);
//                        if (isMoved) {
//                            getPickupAdress();
//                        }
//                    }*/
//                }
//
//                if (sf.getPickuplatlng() != null) {
//                    map.setOnCameraMoveStartedListener(this);
////                    map.setOnCameraIdleListener(this);
//                }
//                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//
//                    return;
//                }
//                if (!mGoogleApiClient.isConnected())
//                    mGoogleApiClient.connect();
//            }
//
//
//            if (map != null && location != null && booking_state == HomePage.BOOKINGSTATE.STATE_ONE) {
//                map.setMyLocationEnabled(true);
//                map.getUiSettings().setMyLocationButtonEnabled(false);
////                sf.setPickuplatlng(LastKnownLatLng);
//
//                Log.d("onMapReady", "locationChanged" + sf.getPickupLocTxt());
//                if (sf.getPickupLocTxt().trim().equals("")) {
//                    getPickupAdress();
//
//                } else {
//                    NeedtoGetAddress();
//                }
//            }
//
//
//            this.location = location;
//            if (sf.getPickuplat() == 0) {
//                getPickupAdress();
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    /**
//     * //     * this method is used to change map position
//     * //
//     */
////
//    @Override
//    public void onCameraIdle() {
//        if (NetworkStatus.isOnline(getActivity())) {
//            Log.d("onCameraChange", "onCameraChange" + map.getCameraPosition().target.latitude + "---" + bookAgainMsg);
//            try {
//                if (bookAgainMsg) {
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            bookAgainMsg = false;
//                        }
//                    }, 5000);
//                } else {
//                    if (booking_state == BOOKINGSTATE.STATE_THREE) {
//                        dragLatLng = map.getCameraPosition().target;
//                        Log.e("Location", "onCameraChange: ca");
//                        textConfirmPickup.setVisibility(View.VISIBLE);
//                        tvPickLocConfirm.setText(NC.getString(R.string.fetching_address));
//                        handlerServercall.removeCallbacks(callAddressDrag);
//                        handlerServercall.removeCallbacksAndMessages(null);
//                        handlerServercall.postDelayed(callAddressDrag, 800);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//        }
//    }
//
//    @Override
//    public void onMapReady(GoogleMap map) {
//        HomePage.map = map;
//
//        try {
//
//// Customise the styling of the base map using a JSON object defined in a raw resource file.
//            boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map_style));
//            if (!success) {
//                Systems.out.println("Style parsing failed.");
//            }
//        } catch (Resources.NotFoundException e) {
//            Systems.out.println("Can't find style. Error: ");
//        }
//        mapWrapperLayout.init(map, TaxiUtil.getPixelsFromDp(getActivity(), 39 + 20), true, null);
//
//        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                String y = "";
//                if (marker.getTag() != null)
//                    y = (String) marker.getTag();
//                if (marker.getTag() != null && marker.getTag().equals("pickup")) {
//                    sf.pickupClicked(false);
//                } else if (marker.getTag() != null && marker.getTag().equals("dropMarker")) {
//                    sf.dropClicked(false);
//                }
//                return false;
//            }
//        });
//        map.getUiSettings().setRotateGesturesEnabled(true);
//
//        if (LastKnownLatLng != null) {
//
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LastKnownLatLng, zoomLevel));
//            if (booking_state != HomePage.BOOKINGSTATE.STATE_TWO)
//                sf.setPickuplatlng(LastKnownLatLng);
//            if (sf.getPickupLocTxt().trim().equals(""))
//                getPickupAdress();
//        }
//
//        Log.d("onMapReady", "onMapReady");
////        map.setOnCameraIdleListener(this);
//        try {
//            if (address != null)
//                address.cancel(true);
//            if (bookAgainMsg) {
//                Systems.out.println("____**" + alertBundle.getString("drop_location") + "__" + alertBundle.getString("pickup_location"));
//                if (alertBundle.getString("drop_location") != null) {
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Systems.out.println("____****" + alertBundle.getString("drop_location") + "__" + alertBundle.getString("pickup_location"));
//                            booking_state = HomePage.BOOKINGSTATE.STATE_TWO;
//                            sf.setPickuplatlng(new LatLng(alertBundle.getDouble("pickup_latitude"), alertBundle.getDouble("pickup_longitude")));
//                            sf.setPickupLocTxt(alertBundle.getString("pickup_location"));
//                            sf.setDroplatlng(new LatLng(alertBundle.getDouble("drop_latitude"), alertBundle.getDouble("drop_longitude")));
//                            sf.setDropLocTxt(alertBundle.getString("drop_location"));
//                            if (!sf.getPickupLocTxt().trim().equals(""))
//                                sf.dropVisible();
//                            else
//                                sf.dropGone();
////                             clearsetPickDropMarker();
//                        }
//                    }, 1000);
//
//
//                } else {
//                    booking_state = HomePage.BOOKINGSTATE.STATE_TWO;
//                    sf.setPickuplatlng(new LatLng(alertBundle.getDouble("pickup_latitude"), alertBundle.getDouble("pickup_longitude")));
//                    sf.setPickupLocTxt(alertBundle.getString("pickup_location"));
//                }
//
//
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(alertBundle.getDouble("pickup_latitude"),
//                        alertBundle.getDouble("pickup_longitude")), zoomLevel));
//            } else {
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //All inner Classes listed below
//
//    /*
//     * this method is used to call the api
//     * */
//    public void Apicall_Book_After(final String approx_distance, final String apprpx_time) {
//        try {
//            isBookAfter = false;
//            alertDialog = Utility.alert_view_dialog(requireActivity(), "",
//                    "" + NC.getString(R.string.confirm_booking),
//                    "" + NC.getString(R.string.ok),
//                    "" + NC.getString(R.string.cancel),
//                    false, new android.content.DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(android.content.DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            try {
//                                if (sf.getPickupLocTxt().trim().length() == 0) {
//
//                                    CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.select_the_pickup_location));
//                                } else {
//
//                                    JSONObject j = new JSONObject();
//                                    j.put("latitude", sf.getPickuplat());
//                                    j.put("longitude", sf.getPickuplng());
//                                    j.put("pickupplace", sf.getPickupLocTxt() != null ? sf.getPickupLocTxt() : "");
//                                    j.put("dropplace", sf.getDropLocTxt() != null ? sf.getDropLocTxt() : "");
//                                    j.put("drop_latitude", sf.getDroplat());
//                                    j.put("drop_longitude", sf.getDroplng());
//                                    j.put("pickup_time", pickupTimeAndDate);
//                                    j.put("motor_model", carModel);
//                                    j.put("cityname", (defaultCityName == null || defaultCityName.trim().equals("")) ? SessionSave.getSession("default_city_name", getActivity()) : defaultCityName.trim());
//                                    j.put("distance_away", E_time);
//                                    j.put("sub_logid", "");
//                                    j.put("sub_logid", "");
//                                    j.put("friend_id2", "0");
//                                    j.put("friend_percentage2", "0");
//                                    j.put("friend_id3", "0");
//                                    j.put("friend_percentage3", "0");
//                                    j.put("friend_id4", "0");
//                                    j.put("friend_percentage4", "0");
//                                    j.put("friend_id1", SessionSave.getSession("Id", getActivity()));
//                                    j.put("friend_percentage1", "100");
////                            Double approx_timee = 0.0, approx_diste = 0.0;
////                            try {
////                                approx_diste = Double.parseDouble(approx_distance) / 1000;
////                                approx_timee = Double.parseDouble(apprpx_time) / 60;
////                            } catch (NumberFormatException e) {
////                                e.printStackTrace();
////                            }
//                                    j.put("approx_distance", approximateDistance);
//                                    j.put("approx_duration", approximateTime);
//                                    j.put("passenger_id", SessionSave.getSession("Id", getActivity()));
//                                    j.put("request_type", "1");
//                                    //j.put("promo_code", ed_promocode.getText().toString());
//                                    j.put("promo_code", promoCode);
//                                    j.put("now_after", "1");
//                                    j.put("notes", SessionSave.getSession("notes", getActivity()));
//                                    j.put("passenger_app_version", MainActivity.APP_VERSION);
//                                    j.put("travel_modelid", Integer.parseInt(travelModelId.equals("") ? "0" : travelModelId));
//                                    j.put("booked_location", bookingLocation);
//                                    j.put("booked_latitude", lastKnownLat);
//                                    j.put("booked_longitude", lastKnownLng);
//
//                                    if (approxFare != 0 && sf.getDroplat() != 0.0 && sf.getDroplng() != 0.0) {
//                                        if (friend2SA != 0)
//                                            j.put("friend_percentage_amt3", ((friend2SA / 100.00) * approxFare));
//                                        if (friend1SA != 0)
//                                            j.put("friend_percentage_amt2", ((friend1SA / 100.00) * approxFare));
//                                        if (friend3SA != 0)
//                                            j.put("friend_percentage_amt4", ((friend3SA / 100.00) * approxFare));
//
//                                        j.put("friend_percentage_amt1", ((friendA / 100.00) * approxFare));
//                                        j.put("approx_trip_fare", approxFare);
//                                    } else {
//                                        j.put("friend_percentage_amt1", 0);
//                                        j.put("friend_percentage_amt2", 0);
//                                        j.put("friend_percentage_amt3", 0);
//                                        j.put("approx_trip_fare", 0);
//                                    }
//                                    j.put("route_path", route.getOverViewPolyLine());
//                                    JSONArray arr = new JSONArray();
//                                    if (sf.getStopPoints() != null) {
//                                        //Loop index size()
//                                        for (int i = 0; i < sf.getStopPoints().size(); i++) {
//                                            JSONObject eachData = new JSONObject();
//                                            try {
//                                                eachData.put("id", sf.getStopPoints().get(i).getId());
//                                                eachData.put("lat", sf.getStopPoints().get(i).getLat());
//                                                eachData.put("lng", sf.getStopPoints().get(i).getLng());
//                                                eachData.put("placeName", sf.getStopPoints().get(i).getPlaceName());
//                                                eachData.put("placeId", sf.getStopPoints().get(i).getPlaceId());
//
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
//                                            arr.put(eachData);
//                                        }
//                                    }
//                                    j.put("stops", sf.getStopPoints() == null ? "" : arr);
//                                    final String url = "type=savebooking";
////                            textBookLater.setBackgroundColor(NC.getResources().getColor(R.color.header_header_bgcolorColor));
//                                    textviewBookLater.setClickable(true);
//                                    bookingType = "after";
//                                    SessionSave.saveSession("SearchUrl", url, getActivity());
//                                    Systems.out.println("api called");
//                                    new SearchTaxi(url, j);
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new android.content.DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(android.content.DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }, "");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * Driver marker Logic
//     *
//     * @param drivers current response driver
//     */
//
//    private synchronized void DriverLiveMovement(final ArrayList<DriverData> drivers) {
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            public void run() {
//                try {
//                    if (drivers == null || (onMovingDriverMarkers.size() > 0 && drivers.size() == 0)) {
//                        //If no driver and clear the previous drivers
//                        int len = onMovingDriverMarkers.size();
//                        for (int i = 0; i < len; i++) {
//                            int key = onMovingDriverMarkers.keyAt(i);
//                            //clear the marker
//                            onMovingDriverMarkers.get(key).remove();
//                        }
//                        onMovingDriverMarkers.clear();
//                    } else {
//                        // has driver to show or move the driver marker
//                        if (onMovingDriverMarkers.size() == 0) {
//                            // Initial drivers
//                            for (DriverData driver : drivers) {
//                                //add the marker to google map
//                                newDriverMarker(driver);
//                            }
//                        } else {
//                            // Main part add, remove , move the duplicate driver
//                            int len = onMovingDriverMarkers.size();
//                            // get the previous marker icons
//                            onExistingDriverMarkers.clear();
//                            for (int i = 0; i < len; i++) {
//                                onExistingDriverMarkers.add(onMovingDriverMarkers.keyAt(i));
//                            }
//                            for (DriverData driver : drivers) {
//                                Integer driverId = Integer.parseInt(driver.getDriverId());
//                                final Marker marker = onMovingDriverMarkers.get(driverId);
//                                if (marker != null) {
//                                    //duplicate one, so move it with bearing
//                                    if (isValidCoordinates(driver.getLat()) && isValidCoordinates(driver.getLng())) {
//                                        try {
//                                            final double lat = Double.parseDouble(driver.getLat());
//                                            final double lng = Double.parseDouble(driver.getLng());
//                                            float bearing = 0f;
//                                            try {
//                                                bearing = Float.parseFloat(driver.getNearest());
//                                            } catch (NumberFormatException e) {
//                                                bearing = 0f;
//                                                e.printStackTrace();
//                                            }
//                                            getHeadingDirectionFromCoordinate(marker, new LatLng(lat, lng), marker.getPosition(), bearing);
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//
//                                        }
//                                    }
//                                    onExistingDriverMarkers.remove(driverId);
//                                } else {
//                                    // add new driver , Welcome driver !
//                                    newDriverMarker(driver);
//                                }
//                            }
//                            //Now its time to remove out of drivers from map, Bye bye driver
//                            for (final Integer previousOnRoleDriver : onExistingDriverMarkers) {
//                                removeMarkerWithAnimation(onMovingDriverMarkers.get(previousOnRoleDriver));
//                                onMovingDriverMarkers.remove(previousOnRoleDriver);
//                            }
//                            onExistingDriverMarkers.clear();
//                        }
//                    }
//                } catch (ConcurrentModificationException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 100);
//
//
//    }
//
//    private JSONObject getNearestJsonObject() {
//        JSONObject data = new JSONObject();
//        try {
//            JSONObject j = new JSONObject();
//            j.put("latitude", sf.getPickuplat());
//            j.put("longitude", sf.getPickuplng());
//
//            if (sf.getDroplatlng() != null && sf.getDroplat() != 0.0) {
//                j.put("drop_latitude", sf.getDroplatlng().latitude);
//                j.put("drop_longitude", sf.getDroplatlng().longitude);
//            } else {
//                j.put("drop_latitude", "");
//                j.put("drop_longitude", "");
//            }
//
//            j.put("motor_model", carModel);
//
//            try {
//                String[] ss = sf.getPickupLocTxt().split(",");
//                if (ss.length > 2)
//                    defaultCityName = ss[ss.length - 3];
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            j.put("passenger_id", SessionSave.getSession("Id", getActivity()));
//            j.put("city_name", (defaultCityName == null || defaultCityName.trim().equals("")) ? SessionSave.getSession("default_city_name", getActivity()) : defaultCityName.trim());
//            j.put("skip_fav", "2");
//            j.put("device_token", SessionSave.getSession(TaxiUtil.DEVICE_TOKEN, getActivity()));
//
//            data.put("data", j);
//            data.put("platform", "ANDROID");
//            data.put("app", "PASS");
//            data.put("lang", SessionSave.getSession("Lang", getActivity()));
//            data.put("id", SessionSave.getSession("Id", getActivity()));
//            data.put("unique", unique());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return data;
//    }
//
//    private void startDriverMovementSocket(final ArrayList<String> driver_id) {
//        if (driver_id != null) {
//            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    JSONArray array = new JSONArray();
//                    for (int i = 0; i < driver_id.size(); i++) {
//                        array.put(Integer.parseInt(driver_id.get(i)));
//                    }
//                    realTimeTracking(array);
//
//                }
//            }, 200);
//        }
//    }
//
//    private void realTimeTracking(JSONArray array) {
//        Systems.out.println("nan----realTimeTracking " + array.toString());
//        try {
//            JSONObject data = new JSONObject();
//            data.put("data", array);
//            data.put("unique", unique());
//            data.put("platform", "ANDROID");
//            data.put("app", "PASS");
//            data.put("id", SessionSave.getSession("Id", getActivity()));
//
//            Systems.out.println("check the driver id data  " + data);
//
//
//            if (NetworkStatus.isOnline(getActivity())) {
//                CoreClient client = null;
//
////                client = new NodeServiceGenerator(getActivity(), SessionSave.getSession(TaxiUtil.NODE_URL, getActivity()), 8).createService(CoreClient.class);
//
//                client = AppController.getInstance().getNodeApiManagerWithTimeOut(SessionSave.getSession(TaxiUtil.NODE_URL, getActivity()), 8L);
//                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), data.toString());
//
//                Call<ResponseBody> coreResponse = client.getDriverCurrentLocation(body);
//                coreResponse.enqueue(new RetrofitCallbackClass<ResponseBody>(getActivity(), new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                        String data = null;
//                        JSONArray json = null;
//                        String Driverid, Drivername, Lat, Lng, Location, bearing;
//                        try {
//                            data = response.body().string();
//                            if (data != null) {
//                                JSONObject jsonObject = new JSONObject(data);
//                                if (jsonObject.getString("status").equals("1")) {
//                                    json = (jsonObject).getJSONArray("data");
//                                    if (json.length() > 0 && availablecarcount > 0) {
//                                        TaxiUtil.mDrivermovementdata.clear();
//                                        for (int i = 0; i < json.length(); i++) {
//                                            try {
//                                                Driverid = json.getJSONObject(i).getString("driver_id");
//                                                Drivername = "";// jarray.getJSONObject(i).getString("name");
//                                                Location = json.getJSONObject(i).getString("locations");
//                                                Location = Location.substring(0, Location.length() - 1);
//                                                String[] latlong = Location.split(",");
//                                                Lat = latlong[0];
//                                                Lng = latlong[1];
//                                                int length = -1;
//                                                length = Lng.indexOf("|");
//                                                if (length != -1) {
//                                                    Lng = Lng.substring(0, length);
//                                                }
//                                                Systems.out.println("vimal check the last position  " + Lng);
//                                                if (json.getJSONObject(i).has("bearings")) {
//                                                    bearing = json.getJSONObject(i).getString("bearings");
//                                                } else {
//                                                    bearing = "0";
//                                                }
//                                                final DriverData mDriverData = new DriverData(Driverid, Drivername, speed, Lat, Lng, bearing, "", null, null);
//                                                TaxiUtil.mDrivermovementdata.add(mDriverData);
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                        DriverLiveMovement(TaxiUtil.mDrivermovementdata);
//                                        if (driverDelayHandler != null) {
//                                            driverDelayHandler.removeCallbacks(driverLocationHistoryRunnable);
//                                            Fragment fragment = requireActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrag);
//                                            if (fragment instanceof HomePage && fragment.isResumed()) {
//                                                driverDelayHandler.postDelayed(driverLocationHistoryRunnable, 4000);
//                                            }
//                                        }
//                                    } else {
//                                        DriverLiveMovement(null);
//                                        if (driverDelayHandler != null)
//                                            driverDelayHandler.removeCallbacks(driverLocationHistoryRunnable);
//                                        driverIdData.clear();
//                                        TaxiUtil.mDrivermovementdata.clear();
//                                        TaxiUtil.mDriverdata.clear();
//                                        availablecarcount = 0;
//                                        tvRequestTaxi.setText(NC.getString(R.string.car_not_available));
//                                    }
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                }));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            if (driverDelayHandler != null)
//                driverDelayHandler.removeCallbacks(driverLocationHistoryRunnable);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }
//
//    /**
//     * Add new Marker
//     *
//     * @param driver driver information
//     */
//    private void newDriverMarker(final DriverData driver) {
//        if (isValidCoordinates(driver.getLat()) && isValidCoordinates(driver.getLng())) {
//            double lat = Double.parseDouble(driver.getLat());
//            double lng = Double.parseDouble(driver.getLng());
//            if (booking_state != BOOKINGSTATE.STATE_THREE) {
//                Bitmap carIcon = BitmapFactory.decodeResource(getResources(), R.drawable.car_movement_icon);
//                if (carIcon != null) {
//                    if (onMovingDriverMarkers.indexOfKey(Integer.parseInt(driver.getDriverId())) < 0) {
//                        Marker marker = createAndGetMarker(new LatLng(lat, lng), 0, carIcon);
//                        onMovingDriverMarkers.put(Integer.parseInt(driver.getDriverId()), marker);
//                    }
//                }
//            }
//        }
//
//    }
//
//    /**
//     * Check empty, 0
//     *
//     * @param latOrLng src coordinates
//     */
//    private boolean isValidCoordinates(String latOrLng) {
//        return !TextUtils.isEmpty(latOrLng) && !latOrLng.equals("0") && !latOrLng.equals("0.0");
//    }
//
//    public Marker createAndGetMarker(LatLng latLng, float bearing, Bitmap carIcon) {
//        if (map == null) {
//            return null;
//        }
//        if (bearing < 0) {
//            bearing = 0;
//        }
//        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.car_movement_icon));
//        final Marker marker = map.addMarker(markerOptions);
//        marker.setFlat(true);
//        marker.setRotation(bearing);
//        CarMovementAnimation.getInstance().addMarkerAnimate(marker);
//        return marker;
//    }
//
//    private void getHeadingDirectionFromCoordinate(Marker marker, LatLng latLng, LatLng currentposition, float bearing) {
//        animateMarker(marker, latLng, bearing);
//    }
//
//    public void animateMarker(final Marker marker, final LatLng newLatLng, float bearing) {
//        if (bearing < 0) {
//            bearing = 0;
//        }
//        CarMovementAnimation.getInstance().animateMarker(marker, newLatLng, bearing);
//
//
//    }
//
//    public void removeMarkerWithAnimation(final Marker removeMarker) {
//        if (removeMarker != null) {
//            CarMovementAnimation.getInstance().removeMarkerWithAnimation(removeMarker);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        IS_HOME_PAGE = false;
//        if (alertDialog != null)
//            Utility.closeDialog(alertDialog);
//        CarMovementAnimation.getInstance().StopMovement();
//        driverDelayHandler.removeCallbacks(driverLocationHistoryRunnable);
//        driverDelayHandler = null;
//        SessionSave.saveSession("locaket_session", "0", MainHomeFragmentActivity.context);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        ((MainHomeFragmentActivity) requireActivity()).showDarkStatusBarIcon();
//        if (mGoogleApiClient != null) {
//            if (!mGoogleApiClient.isConnected()) {
//                mGoogleApiClient.connect();
//            } else {
//                mGoogleApiClient.reconnect();
//            }
//        }
//        //To hide toolbar
//        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.GONE);
//        ((MainHomeFragmentActivity) getActivity()).homePage_title();
//        ((MainHomeFragmentActivity) getActivity()).toolbarRightIcon(false);
//        ((MainHomeFragmentActivity) getActivity()).enableSlide();
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        Point display_size = new Point();
//        display.getSize(display_size);
//
//        countDownTimer = new CountDownTimer(20000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                try {
//                    if (mGoogleApiClient != null)
//                        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//                    if (location != null)
//                        LastKnownLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    if (LastKnownLatLng != null && countDownTimer != null) {
//                        if (booking_state == BOOKINGSTATE.STATE_ONE)
//                            movetoCurrentloc();
//                        countDownTimer.cancel();
//                    }
//
//                    if (LastKnownLatLng == null && getActivity() != null && (millisUntilFinished < 11000 && millisUntilFinished > 10000)) {
//                        if (!(((MainHomeFragmentActivity) getActivity()).gpsAlert != null && ((MainHomeFragmentActivity) getActivity()).gpsAlert.isShowing())) {
//                            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//                            if (location != null)
//                                LastKnownLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                        }
//                        CToast.ShowToast(getActivity(), NC.getString(R.string.getting_gps_low));
//
//                    } else if ((millisUntilFinished < 2000 && millisUntilFinished > 1400)) {
//                        if (!(((MainHomeFragmentActivity) getActivity()).gpsAlert != null && ((MainHomeFragmentActivity) getActivity()).gpsAlert.isShowing())) {
//                            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrag);
//                            if (fragment instanceof HomePage && fragment.isResumed()) {
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (LastKnownLatLng == null && getActivity() != null)
//                                            sf.pickupClicked(true);
//                                    }
//                                }, 2000);
//                            }
//                        }
//
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            public void onFinish() {
//            }
//        }.start();
//
//    }
//
//    @Override
//    public void onStop() {
//        //To prevent window leakage error close all dialogs before activity stops.
//        ((MainHomeFragmentActivity) requireActivity()).cancel_b.setVisibility(View.GONE);
//        if (dt_mDialog != null)
//            if (dt_mDialog.isShowing())
//                dt_mDialog.dismiss();
//        if (Dialog_Common.mCustomDialog != null)
//            if (Dialog_Common.mCustomDialog.isShowing())
//                Dialog_Common.mCustomDialog.dismiss();
//        super.onStop();
//    }
//
//    private void AnimationInScreen() {
//        if (getActivity() != null && isAdded() && animationLay != null && animationLay.isAttachedToWindow()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                ViewCompat.postOnAnimation(animationLay, new Runnable() {
//                    @Override
//                    public void run() {
//                        //Cicular animation
//                        Animator animator = Utility.animateRevealWithoutColorFromCoordinates(animationLay);
//                        animator.addListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                if (getActivity() != null)
//                                    SessionSave.saveSession("isFromSplash", false, getActivity());
//                            }
//
//                            @Override
//                            public void onAnimationCancel(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animator animation) {
//
//                            }
//
//                        });
//
//                    }
//                });
//            }
//        }
//    }
//
//    public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
//
//        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
//        JSONArray jRoutes = null;
//        JSONArray jLegs = null;
//        JSONArray jSteps = null;
//
//        try {
//
//            jRoutes = jObject.getJSONArray("routes");
//
//            /** Traversing all routes */
//            for (int i = 0; i < jRoutes.length(); i++) {
//                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
//                List path = new ArrayList<HashMap<String, String>>();
//
//                /** Traversing all legs */
//                for (int j = 0; j < jLegs.length(); j++) {
//                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");
//
//                    /** Traversing all steps */
//                    for (int k = 0; k < jSteps.length(); k++) {
//                        String polyline = "";
//                        polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
//                        List<LatLng> list = decodePoly(polyline);
//
//                        /** Traversing all points */
//                        for (int l = 0; l < list.size(); l++) {
//                            HashMap<String, String> hm = new HashMap<String, String>();
//                            hm.put("lat", Double.toString(list.get(l).latitude));
//                            hm.put("lng", Double.toString(list.get(l).longitude));
//                            path.add(hm);
//                        }
//                    }
//                    routes.add(path);
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (Exception ignored) {
//        }
//
//        return routes;
//    }
//
//    private List<LatLng> decodePoly(String encoded) {
//
//        List<LatLng> poly = new ArrayList<LatLng>();
//        int index = 0, len = encoded.length();
//        int lat = 0, lng = 0;
//
//        while (index < len) {
//            int b, shift = 0, result = 0;
//            do {
//                b = encoded.charAt(index++) - 63;
//                result |= (b & 0x1f) << shift;
//                shift += 5;
//            } while (b >= 0x20);
//            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//            lat += dlat;
//
//            shift = 0;
//            result = 0;
//            do {
//                b = encoded.charAt(index++) - 63;
//                result |= (b & 0x1f) << shift;
//                shift += 5;
//            } while (b >= 0x20);
//            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//            lng += dlng;
//
//            LatLng p = new LatLng((((double) lat / 1E5)),
//                    (((double) lng / 1E5)));
//            poly.add(p);
//        }
//
//        return poly;
//    }
//
//    void drawPolyline(List<List<HashMap<String, String>>> result) {
//
//        ArrayList<LatLng> points = null;
//        PolylineOptions lineOptions = null;
//        listLatLng.clear();
////        if(blackPolyLine!=null)
////        blackPolyLine.setPoints(listLatLng);
//        // Traversing through all the routes
//        for (int i = 0; i < result.size(); i++) {
//            points = new ArrayList<LatLng>();
//            lineOptions = new PolylineOptions();
//
//            // Fetching i-th route
//            List<HashMap<String, String>> path = result.get(i);
//
//            // Fetching all the points in i-th route
//            for (int j = 0; j < path.size(); j++) {
//                HashMap<String, String> point = path.get(j);
//
//                double lat = Double.parseDouble(point.get("lat"));
//                double lng = Double.parseDouble(point.get("lng"));
//                LatLng position = new LatLng(lat, lng);
//
//                points.add(position);
//            }
//
//            this.listLatLng.addAll(points);
//        }
//        if (lineOptions != null) {
//            lineOptions.width(10);
//            lineOptions.color(Color.GRAY);
//            lineOptions.startCap(new SquareCap());
//            lineOptions.endCap(new SquareCap());
//            lineOptions.jointType(ROUND);
//            blackPolyLine = map.addPolyline(lineOptions);
//
//            PolylineOptions greyOptions = new PolylineOptions();
//            greyOptions.width(10);
//            greyOptions.color(Color.BLACK);
//            greyOptions.startCap(new SquareCap());
//            greyOptions.endCap(new SquareCap());
//            greyOptions.jointType(ROUND);
//            greyPolyLine = map.addPolyline(greyOptions);
//
////            blackPolyLine.setPoints(listLatLng);
//
//            animatePolyLine(1000);
//        }
//    }
//
//    private void animatePolyLine(long duration) {
//
//        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
//        animator.setDuration(duration);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//
//                List<LatLng> latLngList = blackPolyLine.getPoints();
//                int initialPointSize = latLngList.size();
//                int animatedValue = (int) animator.getAnimatedValue();
//                int newPoints = (animatedValue * listLatLng.size()) / 100;
//
//                if (initialPointSize < newPoints) {
//                    latLngList.addAll(listLatLng.subList(initialPointSize, newPoints));
//                    blackPolyLine.setPoints(latLngList);
//                }
//
//
//            }
//        });
//
//        animator.addListener(polyLineAnimationListener);
//        animator.start();
//
//    }
//
//    private void addMarker(LatLng destination) {
//
////        MarkerOptions options = new MarkerOptions();
////        options.position(destination);
////        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
////        map.addMarker(options);
//
//    }
//
//    @Override
//    public void onCameraMoveStarted(int i) {
//        if (booking_state == BOOKINGSTATE.STATE_TWO) {
//            if (MapWrapperLayout.ismMapIsTouched())
//                moveCurrentLocation.setVisibility(View.GONE);
//            else {
//                moveCurrentLocation.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    @Override
//    public void onDistanceCalled(Double time, Double dist, int type, int requestedType) {
//
//        if (!isFromBooknow)
//            closeDialog();
//        if (time != null && dist != null) {
//            UpdateApproximateDistance(time, dist, type, requestedType);
//        }
//    }
//
//    @Override
//    public void callAfter() {
//        Apicall_Book_After("", "");
//    }
//
//    @Override
//    public void setaddress(double latitude, double longitude, String Address) {
//        TaxiUtil.pAddress = "" + Address;
//        Systems.out.println("distance_2003" + Address);
//        try {
//            if (getActivity() != null && Address != null) {
//                closeDialog();
//                lastKnownLat = location.getLatitude();
//                lastKnownLng = location.getLongitude();
//                LastKnownLatLng = new LatLng(lastKnownLat, lastKnownLng);
//                if (map != null && booking_state != HomePage.BOOKINGSTATE.STATE_TWO)
//                    sf.setPickuplatlng(LastKnownLatLng);
//
//                Bundle params = new Bundle();
//                params.putString("user", Address);
//
//                params.putString("type", "passenger");
//                mFirebaseAnalytics.logEvent("address4050", params);
//
//                if (booking_state != HomePage.BOOKINGSTATE.STATE_TWO || (booking_state == BOOKINGSTATE.STATE_TWO
//                        && (sf.getPickupLocTxt().equals("") || sf.getPickupLocTxt().equals(NC.getString(R.string.fetching_address))))) {
//                    HomePage.sf.setPickupLocTxt(Address);
//                    bookingLocation = Address;
//                    HomePage.sf.setPickuplatlng(new LatLng(latitude, longitude));
//                    HomePage.sf.setPickupLocTxt(Address);
//                    tvPickLocConfirm.setText(Address);
//                } else if (selectCarLay.getVisibility() != View.VISIBLE && booking_state == HomePage.BOOKINGSTATE.STATE_TWO) {
//                    HomePage.sf.setPickupLocTxt(Address);
//                    bookingLocation = Address;
//                    HomePage.sf.setPickuplatlng(new LatLng(latitude, longitude));
//                }
//                if (HomePage.sf.getPickupLocTxt().equals("")) {
//                    HomePage.sf.setPickupLocTxt(Address);
//                    HomePage.sf.setPickuplatlng(new LatLng(latitude, longitude));
//                }
//                if (booking_state != BOOKINGSTATE.STATE_THREE)
//                    if (getActivity() != null && !sf.getPickupLocTxt().trim().equals("") && sf.getDroplatlng() == null) {
//                        sf.dropVisible();
//                    } else if (getActivity() != null)
//                        sf.dropGone();
//
//
//                textConfirmPickup.setVisibility(View.VISIBLE);
//
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//
//
//            if (getActivity() != null) {
//                if (!NetworkStatus.isOnline(getActivity()))
//                    CToast.ShowToast(getActivity(), NC.getString(R.string.check_internet_connection));
//            }
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 200) {
//                alertBundle = data.getExtras();
//                if (alertBundle != null) {
//                    alertMsg = alertBundle.getString("alert_message");
//                }
//                if (alertMsg != null && alertMsg.length() != 0) {
//                    alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + alertMsg, "" + NC.getResources().getString(R.string.ok), "");
//                }
//                booking_state = HomePage.BOOKINGSTATE.STATE_THREE;
//                if (driverDelayHandler != null) {
//                    driverDelayHandler.removeCallbacks(driverLocationHistoryRunnable);
//                    driverDelayHandler.postDelayed(driverLocationHistoryRunnable, 4000);
//                }
//            }
//        }
//    }
//
//    /**
//     * @param times         - Total time required for pick up and drop
//     * @param dist          - Distance between pick up and drop
//     * @param type          - Distance calculated type [DISTANCE_TYPE_FOR_ETA or DISTANCE_TYPE_FOR_BOOK_LATER or DISTANCE_TYPE_FOR_FARE]
//     * @param requestedType - Requested type of whether Route Api only for distance calculation or not [ie., 1 means route and distance | 0 means only for distance]
//     */
//    public void UpdateApproximateDistance(double times, double dist, int type, int requestedType) {
//        Systems.out.println("haiiiii UpdateApproximateDistance" + type);
//        try {
//
//            if (times > 0) {
//                if (type == DISTANCE_TYPE_FOR_ETA) {
//
//                    try {
//                        if (times <= 0)
//                            E_time = 1.0;
//                        else
//                            E_time = Double.parseDouble(decimalFormat.format(Double.parseDouble(String.format(Locale.UK, String.valueOf((int) times)))));
//                        Systems.out.println("carmodel" + "___" + times + "___" + (int) E_time + "__" + E_time);
//
//                        int eTime = (int) E_time;
//                        if (!sf.getPickupLocTxt().trim().equals("") && booking_state == BOOKINGSTATE.STATE_TWO) {
//                            if (pickupMarker != null)        //            pickup_marker = map.addMarker(new MarkerOptions()
//                                pickupMarker.remove();
//                            Systems.out.println("settting markk4" + E_time + "__" + eTime);
//                            if (TaxiUtil.mDriverdata.size() > 0) {
//                                if (eTime == 0) {
//                                    eTime = 1;
//                                }
//                            }
//                            Bitmap b = CustomMarker.getMarkerBitmapFromView(String.valueOf(eTime), getActivity(), sf.getPickupLocTxt());
//                            pickupMarker = map.addMarker(new MarkerOptions()
//                                    .position(sf.getPickuplatlng())
//                                    .icon(BitmapDescriptorFactory.fromBitmap(b)));
//                            pickupMarker.setTag("pickup");
//                            pickupMarker.setAnchor(0.0f, 1f);
//                        }
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }
//                } else if (type == DISTANCE_TYPE_FOR_BOOK_LATER) {
//                    approxTravelTime = String.valueOf(times);
//                    approxTravelDist = String.valueOf(dist);
//                    if (isBookAfter)
//                        Apicall_Book_After(approxTravelDist, approxTravelTime);
//                    else {
//                        approxFare = approxFare(getActivity(), dist, times);
//
//                        Log.e("SplitOn ", String.valueOf(SessionSave.getSession(TaxiUtil.isSplitOn, getActivity(), true)));
//
//                        if (!SessionSave.getSession(TaxiUtil.isSplitOn, getActivity(), true)) {
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    if (favDriverAvailable > 0 && SessionSave.getSession(TaxiUtil.isFavDriverOn, getActivity(), true)) {
//                                        alertDialog = new Dialog_Common().setmCustomDialog(getActivity(), HomePage.this, NC.getString(R.string.message), favDriverMessage,
//                                                NC.getString(R.string.ok),
//                                                NC.getString(R.string.no_thanks), "1");
//                                    } else {
//                                        bookNow();
//                                    }
//                                }
//                            });
//                        } else {
//                            closeDialog();
//                            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                                alertDialog = Utility.alert_view_dialog(getActivity(), "", NC.getResources().getString(R.string.split_fare), NC.getResources().getString(R.string.yes), NC.getResources().getString(R.string.no), true, new android.content.DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(android.content.DialogInterface dialog, int which) {
//                                        ActivityCompat.requestPermissions(getActivity(),
//                                                new String[]{Manifest.permission.READ_CONTACTS},
//                                                MY_PERMISSIONS_REQUEST_CONTACTS);
//                                    }
//                                }, new android.content.DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(android.content.DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }, "");
//                            } else {
//                                Message message = handler.obtainMessage(0, "");
//                                message.sendToTarget();
//                            }
//                        }
//
//                    }
//                } else if (type == DISTANCE_TYPE_FOR_FARE) {
//                    approxTravelTime = String.valueOf(times);
//                    approxTravelDist = String.valueOf(dist);
//                    approximateDistance = dist;
//                    approximateTime = times;
//                    approxFare = approxFare(getActivity(), dist, times);
//
////                    if (sf.getDroplat() != 0.0 && pickupApproxFare != null) {
////                        if (zone_fare_applicable) {
////                            pickupApproxFare.setText(SessionSave.getSession("Currency", getActivity()) + decimalFormat.format(Double.parseDouble(String.format(Locale.UK, String.valueOf(zone_zone_fare)))));
////                            approxFare = zone_zone_fare;
////                        } else
////                            pickupApproxFare.setText(SessionSave.getSession("Currency", getActivity()) + decimalFormat.format(Double.parseDouble(String.format(Locale.UK, String.valueOf(approxFare)))));
////                    } else if (pickupApproxFare != null) {
////                        pickupApproxFare.setText("");
////                    }
//
//                    if (requestedType == 0) {
//                        callSaveBooking();
//                    } else {
//                        if (currentCarModel != null) {
//                            Log.v("performClick ", " performClick 4");
//                            currentCarModel.performClick();
//                        }
//                    }
//                }
//            } else if (isBookAfter)
//                Apicall_Book_After("0", "0");
//            else if (type == DISTANCE_TYPE_FOR_FARE) {
//                double Driverdistance = 0.0;
//                Driverdistance = 0.5 + FindDistance.distance(sf.getPickuplat(), sf.getPickuplng(), sf.getDroplat(), sf.getDroplng(), SessionSave.getSession("Metric_type", getActivity()), location);
//                new HomePage.Approximate_Time(Driverdistance, SessionSave.getSession("Metric_type", getActivity()), DISTANCE_TYPE_FOR_FARE).execute();
//            } else closeDialog();
//
//        } catch (Exception e) {
//            closeDialog();
//            ShowToast.center(getActivity(), NC.getString(R.string.server_con_error));
//            e.printStackTrace();
//        }
//    }
//
//
//    //methods for route animation
//
//    public void showDialog() {
//        try {
//            if (NetworkStatus.isOnline(getActivity())) {
//                if (loadingDialog != null && loadingDialog.isShowing())
//                    loadingDialog.dismiss();
//                View view = View.inflate(getActivity(), R.layout.progress_bar, null);
//                loadingDialog = new Dialog(getActivity(), R.style.dialogwinddow);
//                loadingDialog.setContentView(view);
//                loadingDialog.setCancelable(false);
//                if (this != null)
//                    loadingDialog.show();
//
//                ImageView iv = loadingDialog.findViewById(R.id.giff);
//                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
//                Glide.with(this)
//                        .load(R.raw.loading_anim)
//                        .into(imageViewTarget);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    //method to close dialog
//    public void closeDialog() {
//
//        try {
//            if (loadingDialog != null)
//                if (loadingDialog.isShowing())
//                    loadingDialog.dismiss();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * To Get nearest driver list for particular model_id
//     */
//
//    public void getNearTaxi(JSONObject data) {
//        try {
//
//
//            if (NetworkStatus.isOnline(getActivity())) {
//                if (!SessionSave.getSession(TaxiUtil.NODE_TOKEN, getActivity()).equals("")) {
//                    CoreClient client = null;
////                    client = new NodeServiceGenerator(getActivity(), SessionSave.getSession(TaxiUtil.NODE_URL, getActivity()), 8).createService(CoreClient.class);
//                    client = AppController.getInstance().getNodeApiManagerWithTimeOut(SessionSave.getSession(TaxiUtil.NODE_URL, getActivity()), 8L);
//                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), data.toString());
//
//                    Call<ResponseBody> coreResponse = client.nodeUpdate(body);
//                    coreResponse.enqueue(new RetrofitCallbackClass<ResponseBody>(getActivity(), new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                            closeDialog();
//                            String data = null;
//                            JSONObject json = null;
//                            if (response.isSuccessful()) {
//                                try {
//
//                                    data = response.body().string();
//                                    if (data != null) {
//                                        NearestDriverResponse(true, data);
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    stopLineProgressOnError();
//                                }
//                            } else {
//                                stopLineProgressOnError();
//                            }
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            t.printStackTrace();
//                            closeDialog();
//                            stopLineProgressOnError();
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (getActivity() != null) {
//                                        getNearTaxi(data);
//                                    }
//                                }
//                            }, 5000);
//                        }
//                    }));
//                } else {
//                    closeDialog();
//                    if (!SessionSave.getSession("Id", getActivity()).equals("")) {
//                        NodeAuth.getInstance().getAuth(getContext());
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (getActivity() != null) {
//                                    getNearTaxi(data);
//                                }
//                            }
//                        }, 2000);
//                    }
//                }
//            }
//
//
//            SessionSave.saveSession("status", SessionSave.getSession("status", getActivity()), getActivity());
//
//        } catch (final Exception e) {
//            e.printStackTrace();
//
//        }
//
//    }
//
//    public enum BOOKINGSTATE {STATE_ONE, STATE_TWO, STATE_THREE}
//
//    /**
//     * this class is used to ssearch taxi if available
//     */
//
//    public class SearchTaxi implements APIResult {
//        String msg = "";
//        String url = "";
//        String data = "";
//
//        SearchTaxi(final String url, JSONObject data) {
//            textConfirmPickup.setEnabled(false);
//            if (bookingType.equals("after") || SessionSave.getSession(TaxiUtil.isSplitOn, getActivity(), false)) {
//                new APIService_Retrofit_JSON(getActivity(), this, data, false, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession("Lang", getActivity()) + "&" + url).execute();
//            } else {
//                new APIService_Retrofit_JSON(getActivity(), this, data, false, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession("Lang", getActivity()) + "&" + url).execute();
//            }
//            this.url = url;
//            this.data = data.toString();
//        }
//
//        @Override
//        public void getResult(final boolean isSuccess, final String result) {
//            closeDialog();
//            textConfirmPickup.setEnabled(true);
//            if (isSuccess) {
//                try {
//                    final JSONObject json = new JSONObject(result);
//                    if (json.getInt("status") == 1) {
//                        SessionSave.saveSession("req_trip_id", json.getJSONObject("detail").getString("passenger_tripid"), getActivity());
//                        SessionSave.saveSession("Pass_Tripid", json.getJSONObject("detail").getString("passenger_tripid"), getActivity());
//                        SessionSave.saveSession("request_time", json.getJSONObject("detail").getString("total_request_time"), getActivity());
//                        SessionSave.saveSession("Credit_Card", "" + json.getJSONObject("detail").getString("credit_card_status"), getActivity());
//                        if (bookingType.equals("now")) {
//                            if (driverDelayHandler != null)
//                                driverDelayHandler.removeCallbacks(driverLocationHistoryRunnable);
//                            textviewBookLater.setClickable(true);
//                            final Intent i = new Intent(getActivity(), ContinousRequest.class);
//                            i.putExtra("url", url);
//                            if (zone_fare_applicable) {
//                                approxFare = zone_zone_fare;
//                                i.putExtra("approx_fare", "" + decimalFormat.format(Double.parseDouble(String.format(Locale.UK, String.valueOf(approxFare)))));
//                            } else {
//                                i.putExtra("approx_fare", "" + decimalFormat.format(Double.parseDouble(String.format(Locale.UK, String.valueOf(approxFare)))));
//                            }
//                            i.putExtra("json", data);
//                            startActivityForResult(i, 200);
//                        } else {
//                            textviewBookLater.setClickable(true);
//                            alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");
//                        }
//                    } else if (json.getInt("status") == -6 || json.getInt("status") == 2 || json.getInt("status") == 5 || json.getInt("status") == 6) {
//                        alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");
//                    } else if (json.getInt("status") == 3) {
//                        if (json.has("trip_id")) {
//                            SessionSave.saveSession("trip_id", json.getString("trip_id"), getActivity());
//                            DivertToOngoingScreen(getActivity(), "" + NC.getResources().getString(R.string.message), "" + json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");
//                        }
//                    } else if (json.getInt("status") == 4) {
//                        alertDialog = new Dialog_Common().setmCustomDialog(getActivity(), HomePage.this, NC.getResources().getString(R.string.message), json.getString("message"),
//                                NC.getResources().getString(R.string.ok),
//                                NC.getResources().getString(R.string.no_thanks), "4");
//                    } else if (json.getInt("status") == -10) {
//                        try {
//                            JSONObject j = new JSONObject();
//                            j.put("id", SessionSave.getSession("Id", getActivity()));
//                            if (SessionSave.getSession("Logout", getActivity()).equals("")) {
//                                new TaxiUtil.Logout("type=passenger_logout", getActivity(), j);
//                                ((MainHomeFragmentActivity) getActivity()).fbLogout();
//                            } else
//                                alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.bookedtaxi), "" + NC.getResources().getString(R.string.ok), "");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        ShowToast.center(getActivity(), json.getString("message"));
//
//                    } else if (json.getInt("status") == -2678) {
//                        DivertToTripHistory(getActivity(), "" + NC.getResources().getString(R.string.message), "" + json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");
//
//                    } else {
//                        alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");
//                    }
//
//                } catch (final Exception e) {
//                    SessionSave.saveSession("req_trip_id", "", getActivity());
//                    textviewBookLater.setClickable(true);
//                    closeDialog();
//                    getActivity().runOnUiThread(new Runnable() {
//                        public void run() {
//                            CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error));
//                        }
//                    });
//                    e.printStackTrace();
//                }
//            } else {
//                closeDialog();
//                if (getActivity() != null) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        public void run() {
//                            CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error));
//                        }
//                    });
//                }
//            }
//        }
//    }
//
//    public class FindPickupSuggestion implements APIResult {
//        int type;
//
//        public FindPickupSuggestion() {
//
//            String url = SessionSave.getSession("pickupsuggestion_url", getActivity()) + "&booking_longitude=" + sf.getPickuplng() + "&booking_latitude=" + sf.getPickuplat();
//            if (url != null && !url.equals("")) {
//                new APIService_Retrofit_JSON(getActivity(), this, true, url).execute();
//            }
//
//        }
//
//
//        @Override
//        public void getResult(boolean isSuccess, String result) {
//
//            if (isSuccess) {
//                if (getView() != null)
//                    try {
//                        JSONObject jsonObject = new JSONObject(result);
//                        JSONArray jsonArray = jsonObject.getJSONArray("Suggested Points");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            String latlong = ((JSONArray) jsonArray.get(i)).get(0).toString();
//                            Systems.out.println("latlong" + latlong);
//                            pickupSuggestion.add(new LatLng(Double.parseDouble(((JSONArray) jsonArray.get(i)).get(0).toString()), Double.parseDouble(((JSONArray) jsonArray.get(i)).get(1).toString())));
//                        }
//                        booking_state = HomePage.BOOKINGSTATE.STATE_THREE;
//                        tvPickLocConfirm.setText(sf.getPickupLocTxt());
//                        textConfirmPickup.setVisibility(View.VISIBLE);
//                        clearsetPickDropMarker();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//
//            } else {
//                ShowToast.center(getActivity(), NC.getString(R.string.server_con_error));
//            }
//        }
//    }
//
//    /**
//     * Class to calculate ETA and Trip distance if user does not have buisness key
//     */
//    private class Approximate_Time extends AsyncTask<Void, Void, Void> {
//        double Ddistance;
//        String Smetric;
//        int type;
//        double time;
//
//        Approximate_Time(double distance, String metric, int isETA) {
//            Ddistance = distance;
//            Smetric = metric;
//            this.type = isETA;
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
//
//            try {
//                time = calculatetime(Ddistance, Smetric, type);
//            } catch (final Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(final Void result) {
//            super.onPostExecute(result);
//            if (getActivity() != null) {
//                if (booking_state == BOOKINGSTATE.STATE_TWO && sf.getPickuplatlng() != null) {
//                    if (pickupMarker != null)
//                        pickupMarker.remove();
//                    if (TaxiUtil.mDriverdata.size() > 0) {
//                        if (E_time == 0.0) {
//                            E_time = 1;
//                        }
//                    }
//                    Bitmap b = CustomMarker.getMarkerBitmapFromView(String.valueOf((int) E_time), getActivity(), sf.getPickupLocTxt());        //                    .icon(BitmapDescriptorFactory.fromBitmap(b)));
//                    pickupMarker = map.addMarker(new MarkerOptions()
//                            .position(sf.getPickuplatlng())
//                            .icon(BitmapDescriptorFactory.fromBitmap(b)));
//                    pickupMarker.setTag("pickup");
//                    pickupMarker.setAnchor(0.0f, 1f);
//                }
//                if (type == DISTANCE_TYPE_FOR_FARE) {
//                    approxTravelTime = String.valueOf(time);
//                    approxTravelDist = String.valueOf(Ddistance);
//                    approxFare = approxFare(getActivity(), Ddistance, time);
//                    if (sf.getDroplat() != 0.0 && pickupApproxFare != null) {
//                        if (zone_fare_applicable) {
//                            pickupApproxFare.setText(SessionSave.getSession("Currency", getActivity()) + decimalFormat.format(Double.parseDouble(String.format(Locale.UK, String.valueOf(zone_zone_fare)))));
//                            approxFare = zone_zone_fare;
//                        } else
//                            pickupApproxFare.setText(SessionSave.getSession("Currency", getActivity()) + decimalFormat.format(Double.parseDouble(String.format(Locale.UK, String.valueOf(approxFare)))));
//                    } else if (pickupApproxFare != null) {
//                        pickupApproxFare.setText("");
//                    }
//                }
//            }
//        }
//    }
//
//}
