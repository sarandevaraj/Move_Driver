package com.taximobility;

import static com.taximobility.SplashActivity.fields;
import static com.taximobility.SplashActivity.fields_id;
import static com.taximobility.SplashActivity.fields_value;
import static com.taximobility.util.ConstantsKt.CREDIT_CARD;
import static com.taximobility.util.ConstantsKt.LANG;
import static com.taximobility.util.ConstantsKt.LOGOUT;
import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.PASS_NAME;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
import com.mayan.sospluginmodlue.SOSActivity;
import com.mayan.sospluginmodlue.service.SOSService;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.taximobility.bookingmodule.BookTaxiHomePage;
import com.taximobility.features.CToast;
import com.taximobility.fragments.AboutFrag;
import com.taximobility.fragments.EditFavouriteFrag;
import com.taximobility.fragments.FareFrag;
import com.taximobility.fragments.FavouriteDriverFrag;
import com.taximobility.fragments.FavouriteFrag;
import com.taximobility.fragments.InviteFriendsFrag;
import com.taximobility.fragments.OnGoingFrag;
import com.taximobility.fragments.PaymentOptionFrag;
import com.taximobility.fragments.ProfileFrag;
import com.taximobility.fragments.SettingsFrag;
import com.taximobility.fragments.TripHistory;
import com.taximobility.fragments.WalletFrag;
import com.taximobility.interfaces.APIResult;
import com.taximobility.interfaces.CancelPayment;
import com.taximobility.interfaces.DriverLocation;
import com.taximobility.interfaces.FragPopFront;
import com.taximobility.interfaces.NetworkInterface;
import com.taximobility.interfaces.splitfareDialog;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.service.FirebaseService;
import com.taximobility.service.GetPassengerUpdate;
import com.taximobility.util.CL;
import com.taximobility.util.Colorchange;
import com.taximobility.util.DatePicker_CardExpiry;
import com.taximobility.util.Dialog_Common;
import com.taximobility.util.DisplayDimensions;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.NetworkStatus;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * this class is used as common activity to use all methods in whole project
 */


public class MainHomeFragmentActivity extends AppCompatActivity implements splitfareDialog, DatePicker_CardExpiry.DateDialogInterface
        , DriverLocation, NetworkInterface, PaymentResultListener, CancelPayment {

    public static Dialog gpsAlert;
    private Dialog alertmDialog, networkAlert, mDialog, dialog;
    public static Context context;
    public static MainHomeFragmentActivity mtag;
    private AboutFrag aboutfrag;
    public NetworkStatus networkStatus;
    public boolean cancelbtn = false;
    private boolean booktaxi = true;
    private String s, requestTripID;
    private String dummy_image = "";


    public Toolbar toolbar;
    public ImageView toolbar_logo, call_image, left_img;
    public ImageButton left_icon, fav_add;
    public LinearLayout toolbar_titletm, menu_emergency;
    public FrameLayout mainFrag;
    public TextView cancel_b, toolbar_title;
    public ViewGroup tool_bar_lay;
    private DrawerLayout drawer_layout;
    public TextView menu_profile_name, menu_pno, txt_emergency;
    private ImageView menu_profile_img;
    private SwitchCompat fav_switch;
    private InternetConnectionReceiver mBroadcastReceiver;

    private LinearLayout menu_payment, menu_wallet, menuInvite, menu_chat_helpline;

    private String fare_calculation_type, distanceFare, c_reason;
    private String razor_type = "";
    private int c_tripid;
    private String cancel_fare = "";
    private long addmoney = 0;
    private String promoCode;
    private DecimalFormat precision = new DecimalFormat("0.00");
    private String f_distance, f_tripid, f_nightfareapplicable, message, f_nightfare, f_eveningfare_applicable;
    private String f_eveningfare, f_passengerdiscount, f_waitingcost, f_taxamount, f_waitingtime;
    private String f_tripfare, f_fare_payment, f_tips_payment, f_total_payment;
    private String f_minutes_fare, f_minutes_traveled;
    private String promodiscount_amount, base_fare, company_tax;
//    private RazerpayListener razerpayListener ;

    /**
     * @param target email string is passed as parameter
     * @boolean check the valid email id
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isNetworkEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    public static void gpsalert(final Context mContext, boolean isconnect) {
        if (!isconnect) {

            Utility.closeDialog("gps");
            String message = "";
            String failure_txt = "";

            if (!isNetworkEnabled(mContext)) {
                message = NC.getString(R.string.location_enable);
                failure_txt = "";
            } else {
                message = NC.getString(R.string.change_network);
                failure_txt = NC.getString(R.string.cancel);
            }

            Utility.gps_dialog(mContext, "" +
                            NC.getResources().getString(R.string.location_disable),
                    "" + message,
                    "" + NC.getResources().getString(R.string.enable),
                    "" + failure_txt,
                    false, (dialog, which) -> {
                        dialog.dismiss();
                        Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(mIntent);
                        dialog.dismiss();

                    }, (dialog, which) -> dialog.dismiss(), "");
        } else {
            try {
                Utility.closeDialog("gps");
                if (gpsAlert != null && gpsAlert.isShowing())
                    gpsAlert.dismiss();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }

    @Override
    public void driverLocationUpdate(final double lat, final double lng) {
        final Fragment ff = getSupportFragmentManager().findFragmentById(R.id.mainFrag);
        if (ff instanceof OnGoingFrag) {
            runOnUiThread(() -> {
                try {
                    ((OnGoingFrag) ff).moveCameraToDriverLoc(lat, lng);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
    }

    @Override
    public void networkError(boolean isConnected) {
        isConnect(isConnected);
    }

    /**
     * To transfer data from SplitFareDialog fragment to HomePage fragment via this activity
     */
    @Override
    public void onSplitSuccess(double primary_Percent, double f1, double f2, double f3, double fa1, double fa2, double fa3) {
        BookTaxiHomePage frag = (BookTaxiHomePage) getSupportFragmentManager().findFragmentById(R.id.mainFrag);
        frag.onSplitSuccess(primary_Percent, f1, f2, f3, fa1, fa2, fa3);
    }

    /**
     * To transfer data from DatePicker_CardExpiry  dialog fragment to PaymentOptionFrag fragment
     */
    @Override
    public void onSuccess(int month, int year) {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.mainFrag);
        if (frag.getClass().equals(PaymentOptionFrag.class)) {
            ((PaymentOptionFrag) frag).onSuccess(month, year);
        }
    }

    @Override
    public void failure(String inputText) {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.mainFrag);
        if (frag.getClass().equals(PaymentOptionFrag.class)) {
            ((PaymentOptionFrag) frag).failure(inputText);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Fragment ff = getSupportFragmentManager().findFragmentById(R.id.mainFrag);
        if (ff != null) {
            ff.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void setLocale() {
        if (SessionSave.getSession(LANG, MainHomeFragmentActivity.this).equals("")) {
            SessionSave.saveSession(LANG, "en", MainHomeFragmentActivity.this);
            SessionSave.saveSession("Lang_Country", "en_US", MainHomeFragmentActivity.this);
        }
        Configuration config = new Configuration();
        String langcountry = SessionSave.getSession("Lang_Country", MainHomeFragmentActivity.this);
        String[] arry = langcountry.split("_");
        String language = SessionSave.getSession(LANG, MainHomeFragmentActivity.this);
        config.locale = new Locale(language, arry[1]);
        Locale.setDefault(new Locale(language, arry[1]));
        MainHomeFragmentActivity.this.getBaseContext().getResources().updateConfiguration(config, MainHomeFragmentActivity.this.getResources().getDisplayMetrics());
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionSave.saveSession(TaxiUtil.NEED_TO_COMPLETE_CARD_REG, false, MainHomeFragmentActivity.this);
        setContentView(R.layout.homepage_drawer);
        context = this;
        mtag = this;
        setLocale();
        SessionSave.saveSession("user_type", "p", MainHomeFragmentActivity.this);
        FirebaseService.activity = null;

        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        int heightStatus = DisplayDimensions.getStatusBarHeight(MainHomeFragmentActivity.this);
        tool_bar_lay = findViewById(R.id.tool_bar_lay);
        tool_bar_lay.setPadding(0, heightStatus, 0, 0);

        if (!SessionSave.getSession("wholekey", MainHomeFragmentActivity.this).trim().equals("")) {
            getAndStoreStringValues(SessionSave.getSession("wholekey", this));
            getAndStoreColorValues(SessionSave.getSession("wholekeyColor", this));
        }
        TaxiUtil.API_BASE_URL = SessionSave.getSession("base_url", this);


        GetPassengerUpdate.context = MainHomeFragmentActivity.this;
        toolbar = findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        mainFrag = findViewById(R.id.mainFrag);

        setSupportActionBar(toolbar);


        networkStatus = new NetworkStatus();
        mBroadcastReceiver = new InternetConnectionReceiver();
        registerNetworkBroadcast(networkStatus, mBroadcastReceiver);
        // create our manager instance after the content view is set
        toolbar_logo = findViewById(R.id.toolbar_logo);
        toolbar_title = findViewById(R.id.toolbar_title);
        cancel_b = findViewById(R.id.cancel_b);


        toolbar_titletm = findViewById(R.id.toolbar_titletm);
        toolbar_title.setTextSize(20);
        fav_add = findViewById(R.id.right_icon);
        fav_switch = findViewById(R.id.switch_right_icon);
        drawer_layout = findViewById(R.id.drawer_layout);

        menu_profile_name = findViewById(R.id.menu_profile_name);
        menu_pno = findViewById(R.id.menu_pno);
        menu_profile_img = findViewById(R.id.menu_profile_img);
        call_image = findViewById(R.id.call_image);
        txt_emergency = findViewById(R.id.txt_emergency);
        menu_emergency = findViewById(R.id.menu_emergency);
        menu_emergency.setVisibility(View.VISIBLE);

        menu_payment = findViewById(R.id.menu_payment);
        menu_wallet = findViewById(R.id.menu_wallet);
        menuInvite = findViewById(R.id.menu_invite);
        menu_chat_helpline = findViewById(R.id.menu_chat_helpline);
        /*if (SessionSave.getSession(TaxiUtil.sosEnable, MainHomeFragmentActivity.this, false)) {
            menu_emergency.setVisibility(View.VISIBLE);
        } else {
            menu_emergency.setVisibility(View.GONE);
        }*/
        txt_emergency.setVisibility(View.GONE);
        call_image.setVisibility(View.GONE);
        Glide.with(MainHomeFragmentActivity.this).load(SessionSave.getSession("image_path", MainHomeFragmentActivity.this) + "call_driver.png").apply(RequestOptions.errorOf(R.drawable.call_driver)).into((ImageView) findViewById(R.id.call_image));
        String f_Firstname = SessionSave.getSession(PASS_NAME, this);
        if (f_Firstname != null && (f_Firstname.length() != 0)) {
            f_Firstname = f_Firstname.substring(0, 1).toUpperCase() + f_Firstname.substring(1);
        }
        menu_profile_name.setText(f_Firstname);
        menu_pno.setText(SessionSave.getSession("CountyCode", this) + " " + SessionSave.getSession("Phone", this));
        //    Picasso.get().load(SessionSave.getSession("ProfileImage", this)).into(menu_profile_img);
        left_icon = findViewById(R.id.left_icon);
        left_img = findViewById(R.id.left_img);

        left_icon.setOnClickListener(v -> {
            String imageTag = String.valueOf(left_icon.getTag());
            if (imageTag.equals("menu")) {
                drawer_layout.openDrawer(GravityCompat.START);
                corporateMenuSettings();
            } else if (imageTag.equals("backarrow")) {
                onBackPressed();
            }

        });

        left_img.setOnClickListener(v -> {
            String imageTag = String.valueOf(left_icon.getTag());
            if (imageTag.equals("menu")) {
                drawer_layout.openDrawer(GravityCompat.START);
                corporateMenuSettings();
            } else if (imageTag.equals("backarrow")) {
                onBackPressed();
            }

        });

        txt_emergency.setOnClickListener(view -> {
            final View view1 = View.inflate(MainHomeFragmentActivity.this, R.layout.emergency_alert, null);
            Dialog emergency_dialog = new Dialog(MainHomeFragmentActivity.this, R.style.dialogwinddow);
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
        });

        profileEdited();

        TaxiUtil.sContext = this;

        drawer_layout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                FontHelper.applyFont(getApplicationContext(), drawer_layout);
            }
        });
        if (!SessionSave.getSession("trip_id", MainHomeFragmentActivity.this).equals("")) {
            Bundle alert_bundle = getIntent().getExtras();
            mainFrag.setBackgroundColor(Color.WHITE);
            OnGoingFrag ongoing = new OnGoingFrag();
            GetPassengerUpdate.context = MainHomeFragmentActivity.this;
            String alert_msg = "", moveToChat = "";
            if (alert_bundle != null) {
                alert_msg = alert_bundle.getString("alert_message");
                moveToChat = alert_bundle.getString("chat", "");
            }
            if (alert_msg != null && alert_msg.length() != 0) {
                Bundle bundle = new Bundle();
                bundle.putString("alert_message", alert_msg);
                bundle.putString("chat", moveToChat);
                ongoing.setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, ongoing).commit();
        } else {
            try {
                if (getIntent() != null) {
                    s = getIntent().getStringExtra("goto");
                    if (s != null) {
                        if (s.equals("favdriv"))
                            getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new FavouriteDriverFrag()).commit();
                    } else {
                        BookTaxiHomePage.Companion.setBookingState(BookTaxiHomePage.BOOKINGSTATE.STATE_ONE);
                        Bundle alert_bundle;
                        BookTaxiHomePage booknew = new BookTaxiHomePage();
                        alert_bundle = getIntent().getExtras();
                        String alert_msg = "";
                        if (alert_bundle != null) {
                            alert_msg = alert_bundle.getString("alert_message");
                        }
                        if (alert_msg != null && alert_msg.length() != 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("alert_message", alert_msg);
                            booknew.setArguments(bundle);
                        }
                        homePage();
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, booknew).commitAllowingStateLoss();
                    }
                } else {
                    BookTaxiHomePage.Companion.setBookingState(BookTaxiHomePage.BOOKINGSTATE.STATE_ONE);
                    Bundle alert_bundle;
                    BookTaxiHomePage booknew = new BookTaxiHomePage();
                    alert_bundle = getIntent().getExtras();
                    String alert_msg = "";
                    if (alert_bundle != null) {
                        alert_msg = alert_bundle.getString("alert_message");
                    }
                    if (alert_msg != null && alert_msg.length() != 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("alert_message", alert_msg);
                        booknew.setArguments(bundle);
                    }
                    getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, booknew).commitAllowingStateLoss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        corporateMenuSettings();
    }

    private void corporateMenuSettings() {
        if (SessionSave.getSession(TaxiUtil.CORPORATE_PASSENGER, MainHomeFragmentActivity.this).equals("1")) {
            menu_payment.setVisibility(View.GONE);
            menu_wallet.setVisibility(View.GONE);
            menuInvite.setVisibility(View.GONE);
        } else {
            menu_payment.setVisibility(View.GONE);
            menu_wallet.setVisibility(View.VISIBLE);

            int inviteFriend = 0;
            try {
                inviteFriend = Integer.parseInt(SessionSave.getSession("referral_settings", this));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (inviteFriend == 0)
                menuInvite.setVisibility(View.GONE);
            else
                menuInvite.setVisibility(View.VISIBLE);
        }
    }

    public void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    public void showDarkStatusBarIcon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public void showNormalStatusBarIcon() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void redirectFareScreen(String fareDetails) {
        try {
            JSONObject obj = new JSONObject(fareDetails);
            if (obj.has("detail")) {
                JSONObject json = obj.getJSONObject("detail");
                Fragment ff = new FareCalcAct();
                Bundle farecal = new Bundle();
                farecal.putString("from", "direct");
                left_icon.setTag("backarrow");
                left_icon.setImageResource(R.drawable.ic_back_white);
                setTitle_m(NC.getString(R.string.Trip_in_progress));
                farecal.putString("message", fareDetails);
                ff.setArguments(farecal);
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mainFrag);
                if (fragment instanceof OnGoingFrag) {
                    toolbar_title.setVisibility(View.GONE);
                    toolbar_logo.setVisibility(View.GONE);
                    toolbar_titletm.setVisibility(View.GONE);
                    tool_bar_lay.setVisibility(View.GONE);

                    getSupportFragmentManager().beginTransaction().remove(fragment).commitNow();
                }
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.mainFrag, ff).commit();
            }
        } catch (JSONException ee) {
            ee.fillInStackTrace();
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public boolean isGpsEnabled(Context context) {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkStatus.appContext = this;

        new Handler().postDelayed(() -> {
            gpsalert(MainHomeFragmentActivity.this, isGpsEnabled(MainHomeFragmentActivity.this));
            NetworkStatus.isOnline(MainHomeFragmentActivity.this);
        }, 100);
        System.out.println("current trip_id : "+SessionSave.getSession("trip_id",MainHomeFragmentActivity.this));
        TaxiUtil.sContext = this;
        Colorchange.ChangeColor((ViewGroup) (((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0)), MainHomeFragmentActivity.this);

        /*Glide.with(MainHomeFragmentActivity.this)
                .load(RequestOptions.placeholderOf(R.drawable.fare_menu))
                .apply(RequestOptions.placeholderOf(R.drawable.fare_menu).error(R.drawable.fare_menu))
                .into((ImageView) findViewById(R.id.menu_fare_image));*/


        FirebaseService.MAIN_ACT = MainHomeFragmentActivity.this;
        final FrameLayout mainFrag = findViewById(R.id.mainFrag);
        new Handler().postDelayed(() -> {
            if (getSupportFragmentManager().findFragmentById(R.id.mainFrag) instanceof BookTaxiHomePage) {
                tool_bar_lay.setVisibility(View.GONE);
            } else if (getSupportFragmentManager().findFragmentById(R.id.mainFrag) instanceof OnGoingFrag) {
                tool_bar_lay.setVisibility(View.GONE);
            } else if (getSupportFragmentManager().findFragmentById(R.id.mainFrag) instanceof ProfileFrag) {
                tool_bar_lay.setVisibility(View.GONE);
            } else
                tool_bar_lay.setVisibility(View.VISIBLE);
        }, 200);

    }

    private void registerNetworkBroadcast(NetworkStatus networkStatus, InternetConnectionReceiver internetreceiver) {
        registerReceiver(networkStatus, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        LocalBroadcastManager.getInstance(this).registerReceiver(internetreceiver, new IntentFilter(TaxiUtil.ACTIVITY_ACTION));
    }

    private void unregisterNetworkChanges(NetworkStatus networkStatus, InternetConnectionReceiver internetreceiver) {
        unregisterReceiver(networkStatus);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(internetreceiver);
    }

    public void enableSlide() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void disableSlide() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void small_title(boolean toSmall) {
        if (toSmall)
            toolbar_title.setTextSize(16);
        else
            toolbar_title.setTextSize(20);

    }

    /**
     * this method is used to set the click method for navigation drawer
     */

    public void ClickMethod(View v) {
        drawer_layout.closeDrawer(GravityCompat.START);

        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        Intent i;
        toolbarRightIcon(false);
        showNormalStatusBarIcon();
        switch (v.getId()) {
            case R.id.book_taxi:
                toolbar_title.setVisibility(View.GONE);
                toolbar_logo.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                BookTaxiHomePage.Companion.setBookingState(BookTaxiHomePage.BOOKINGSTATE.STATE_ONE);
//                HomePage.booking_state = HomePage.BOOKINGSTATE.STATE_ONE;
//                getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new HomePage()).addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new BookTaxiHomePage()).addToBackStack(null).commit();
                break;
            case R.id.menu_me:
                cancel_b.setVisibility(View.GONE);
                toolbar_logo.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(NC.getResources().getString(R.string.menu_profile));
                getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new ProfileFrag(), "ProfileFrag").addToBackStack(null).commit();
                break;
            case R.id.menu_payment:
                cancel_b.setVisibility(View.GONE);
                toolbar_logo.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(NC.getResources().getString(R.string.payment));
                getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new PaymentOptionFrag(), "PaymentOptionFrag").addToBackStack(null).commit();

                break;
            case R.id.menu_gettaxi:
                cancel_b.setVisibility(View.GONE);
                toolbar_logo.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.GONE);
                toolbar_title.setText(NC.getResources().getString(R.string.on_going));

                toolbar_title.setVisibility(View.GONE);
                toolbar_logo.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                tool_bar_lay.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new OnGoingFrag()).addToBackStack(null).commit();

                break;
            case R.id.menu_favourites:
                cancel_b.setVisibility(View.GONE);
                toolbar_logo.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(NC.getResources().getString(R.string.menu_favourites));
                getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new FavouriteFrag()).addToBackStack(null).commit();

                break;
            case R.id.menu_history:
                cancel_b.setVisibility(View.GONE);
                toolbar_logo.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(NC.getResources().getString(R.string.mybookings));
                getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.mainFrag, new TripHistory()).commit();

                break;
            case R.id.menu_about:
                cancel_b.setVisibility(View.GONE);
                toolbar_logo.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(NC.getResources().getString(R.string.menu_about_us));
                aboutfrag = new AboutFrag();
                getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, aboutfrag).addToBackStack(null).commit();
                break;
            case R.id.menu_logout:
                try {
                    logout(MainHomeFragmentActivity.this);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                break;
            case R.id.menu_fare:
                cancel_b.setVisibility(View.GONE);
                toolbar_logo.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(NC.getResources().getString(R.string.menu_fare));
                getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new FareFrag()).addToBackStack(null).commit();

                break;
            case R.id.menu_settings:
                cancel_b.setVisibility(View.GONE);
                toolbar_logo.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(NC.getResources().getString(R.string.menu_settings));
                getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new SettingsFrag()).addToBackStack(null).commit();
                break;
            case R.id.menu_wallet:
                cancel_b.setVisibility(View.GONE);
                toolbar_logo.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(NC.getResources().getString(R.string.wallet));
                getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new WalletFrag()).addToBackStack(null).commit();
                break;
            case R.id.menu_invite:
                cancel_b.setVisibility(View.GONE);
                toolbar_logo.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(NC.getResources().getString(R.string.invite_friend));
                getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new InviteFriendsFrag()).addToBackStack(null).commit();
                break;
            case R.id.menu_emergency:
                SessionSave.saveSession("sos_id", SessionSave.getSession(PASS_ID, MainHomeFragmentActivity.this), MainHomeFragmentActivity.this);
                SessionSave.saveSession("user_type", "p", MainHomeFragmentActivity.this);

                startActivity(new Intent(MainHomeFragmentActivity.this, SOSActivity.class));
                break;

            case R.id.menu_chat_helpline:
                cancel_b.setVisibility(View.GONE);
                toolbar_titletm.setVisibility(View.GONE);
                Intent in = new Intent(this, ChatWebviewAct.class);
                in.putExtra("type", "3");
                startActivityForResult(in, 101);
                break;
            default:
                tool_bar_lay.setVisibility(View.VISIBLE);

        }
    }

    public void clickMethod(View v) {
        if (aboutfrag != null) {
            aboutfrag.clickMethod(v);
        }

    }

    /**
     * Call addonClick() method in FavouriteFrag while the add button in tool bar clicked
     */

    public void toolbarRightIcon(boolean visible) {
        if (visible) {
            fav_add.setVisibility(View.VISIBLE);

            final Fragment ff = (getSupportFragmentManager().findFragmentById(R.id.mainFrag));
            if (ff instanceof FavouriteFrag) {
                fav_add.setImageResource(R.drawable.plus);
                fav_add.setOnClickListener(v -> ((FavouriteFrag) ff).addonClick());
            } else if (ff instanceof EditFavouriteFrag) {
                fav_add.setImageResource(R.drawable.edit_favourite_delete);
                fav_add.setOnClickListener(v -> ((EditFavouriteFrag) ff).deleteonClick());

            } else if (ff instanceof FavouriteDriverFrag) {

                fav_add.setVisibility(View.GONE);
                fav_switch.setVisibility(View.VISIBLE);
                if (SessionSave.getSession(TaxiUtil.isFavDriverOn, this, false))
                    fav_switch.setChecked(true);
                else
                    fav_switch.setChecked(false);

                fav_switch.setOnClickListener(v -> callApi(!SessionSave.getSession(TaxiUtil.isFavDriverOn, MainHomeFragmentActivity.this, true)));
            } else if (ff instanceof ProfileFrag) {
                fav_add.setImageResource(R.drawable.ic_notification);
                fav_add.setOnClickListener(v -> {
                });

            }
        } else {

            fav_add.setVisibility(View.GONE);
            fav_switch.setVisibility(View.GONE);
        }
    }

    public void callApi(boolean isChecked) {
        JSONObject j = new JSONObject();
        try {
            j.put("passenger_id", SessionSave.getSession(PASS_ID, MainHomeFragmentActivity.this));

            if (isChecked)
                j.put("value", "1");
            else
                j.put("value", "2");
            j.put("type", 2);

            Log.e("Json ", j.toString());

            new SaveSplitType("type=set_split_fare", j);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * To notify slider menu about the changes in profile image
     */

    public void profileEdited() {


        menu_profile_name.setText(SessionSave.getSession(PASS_NAME, this));
        //   Picasso.get().load(SessionSave.getSession("ProfileImage", this)).into(menu_profile_img);

        if (SessionSave.getSession("ProfileImage", this) != null && SessionSave.getSession("ProfileImage", this).length() > 0) {
            Picasso.get().load(SessionSave.getSession("ProfileImage", this)).error(R.drawable.loadingimage).placeholder(R.drawable.loadingimage).into(menu_profile_img);
        } else {
            if (SessionSave.getSession(PASS_NAME, this) != "") {
                ProfileImageSetupClass.setupProfileImage(
                        SessionSave.getSession(PASS_NAME, this), menu_profile_img
                );
            } else {
                Picasso.get().load(R.drawable.loadingimage).into(menu_profile_img);
            }
        }

    }

    @Override
    public void onBackPressed() {
        Fragment ff = getSupportFragmentManager().findFragmentById(R.id.mainFrag);
        Systems.out.println("Nan BackStatck check " + "onBackPressed " + getSupportFragmentManager().getBackStackEntryCount());
        if (ff instanceof PaymentOptionFrag) {
            ((PaymentOptionFrag) ff).onBackPressed();
        } else if (ff instanceof ProfileFrag) {
            ((ProfileFrag) ff).onBackPressed();
        } else if (ff instanceof BookTaxiHomePage) {
            ((BookTaxiHomePage) ff).onBackPress();
        } else if (ff instanceof WalletFrag) {
            homePage_title();
            toolbarRightIcon(false);
            left_icon.setImageResource(R.drawable.ic_menu);
            left_icon.setTag("menu");
            enableSlide();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new BookTaxiHomePage()).commit();

        }else if (ff instanceof FareCalcAct) {
            CToast.ShowToast(context,NC.getString(R.string.pls_complete_the_payment));
        }  else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                homePage_title();
                toolbarRightIcon(false);
                left_icon.setImageResource(R.drawable.ic_menu);
                left_icon.setTag("menu");
                enableSlide();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new BookTaxiHomePage()).commit();
            } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                homePage_title();
                toolbarRightIcon(false);
                left_icon.setImageResource(R.drawable.ic_menu);
                left_icon.setTag("menu");
                enableSlide();
                BookTaxiHomePage.Companion.setBookingState(BookTaxiHomePage.BOOKINGSTATE.STATE_ONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new BookTaxiHomePage()).commit();
            } else {
                super.onBackPressed();
            }

            if (getSupportFragmentManager().findFragmentById(R.id.mainFrag) instanceof FragPopFront)
                ((FragPopFront) getSupportFragmentManager().findFragmentById(R.id.mainFrag)).trigger_FragPopFront();
        }

    }

    /**
     * Toolbar changes while loading HomePage fragment
     */
    public void homePage() {
        homePage_title();
    }

    public void homePage_title() {
        tool_bar_lay.setVisibility(View.GONE);
    }

    private synchronized void getAndStoreStringValues(String result) {
        try {


            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            Document doc = dBuilder.parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("*");

            for (int i = 0; i < nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element2 = (Element) node;
                    NC.nfields_byName.put(element2.getAttribute("name"), element2.getTextContent());
                }
            }
            getValueDetail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized void getValueDetail() {
        Field[] fieldss = R.string.class.getDeclaredFields();
        for (int i = 0; i < fieldss.length; i++) {
            int id = getResources().getIdentifier(fieldss[i].getName(), "string", getPackageName());
            if (NC.nfields_byName.containsKey(fieldss[i].getName())) {
                fields.add(fieldss[i].getName());
                fields_value.add(NC.getResources().getString(id));
                fields_id.put(fieldss[i].getName(), id);

            } else {
            }
        }

        for (Map.Entry<String, String> entry : NC.nfields_byName.entrySet()) {
            String h = entry.getKey();
            String value = entry.getValue();
            NC.nfields_byID.put(fields_id.get(h), NC.nfields_byName.get(h));
            // do stuff
        }

    }

    /**
     * set logout dialog box and call logout api
     */

    public void logout(final Context context) {
        try {


            dialog = Utility.alert_view_dialog(MainHomeFragmentActivity.this, "" + NC.getResources().getString(R.string.message),
                    "" + NC.getResources().getString(R.string.confirmlogout),
                    "" + NC.getResources().getString(R.string.menu_logout),
                    "" + NC.getResources().getString(R.string.cancel),
                    true, (dialog, which) -> {
                        try {
                            dialog.dismiss();
                            // TODO Auto-generated method stub
                            JSONObject j = new JSONObject();
                            j.put("id", SessionSave.getSession(PASS_ID, context));
                            if (SessionSave.getSession(LOGOUT, context).equals("")) {
                                new TaxiUtil.Logout("type=passenger_logout", context, j);
                                fbLogout();
                            } else

                                dialog = Utility.alert_view_dialog(MainHomeFragmentActivity.this, "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.bookedtaxi),
                                        "" + NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }, (dialog1, which1) -> dialog1.dismiss(), "");


                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }, (dialog, which) -> dialog.dismiss(), "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set toolbar items which can be dynamically changed
     */

    public void setTitle_m(String title) {

        toolbar_logo.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(title);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (dialog != null)
            Utility.closeDialog(dialog);
        unregisterNetworkChanges(networkStatus, mBroadcastReceiver);
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        //To prevent window leakage error close all dialogs before activity stops.
        Utility.closeDialog(alertmDialog);
        Utility.closeDialog(mDialog);
        Utility.closeDialog(Dialog_Common.mCustomDialog);
        Utility.closeDialog(networkAlert);
        Utility.closeDialog(gpsAlert);
        super.onStop();
    }

    /**
     * This is method for logout the user from their facebook login if they logged in using facebook.
     */
    public void fbLogout() {
        LoginManager.getInstance().logOut();
    }

    //not used
    public void alert_view(Context mContext, String title, String message, String success_txt, String failure_txt) {
        try {
            final View view = View.inflate(mContext, R.layout.alert_view, null);
            alertmDialog = new Dialog(mContext, R.style.dialogwinddow);
            alertmDialog.setContentView(view);
            alertmDialog.setCancelable(true);
            //  FontHelper.applyFont(mContext, alertmDialog.findViewById(R.id.alert_id));
            alertmDialog.show();
            final TextView title_text = alertmDialog.findViewById(R.id.title_text);
            final TextView message_text = alertmDialog.findViewById(R.id.message_text);
            final Button button_success = alertmDialog.findViewById(R.id.button_success);
            final Button button_failure = alertmDialog.findViewById(R.id.button_failure);
            button_failure.setVisibility(View.GONE);
            title_text.setText(title);
            message_text.setText(message);
            button_success.setText(success_txt);
            button_success.setOnClickListener(v -> {
                alertmDialog.dismiss();
            });
            button_failure.setOnClickListener(v -> {
                alertmDialog.dismiss();
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    /**
     * Handle validation action
     */

    public boolean validations(ValidateAction VA, Context con, String stringtovalidate) {
        String message = "";
        boolean result = false;
        switch (VA) {
            case isValueNULL:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + NC.getResources().getString(R.string.enter_the_mobile_number);
                else
                    result = true;
                break;
            case isValidPassword:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + NC.getResources().getString(R.string.enter_the_password);
                else if (stringtovalidate.length() < 6)
                    message = "" + NC.getResources().getString(R.string.password_min_character);
                else if (stringtovalidate.length() > 32)
                    message = "" + NC.getResources().getString(R.string.password_max_character);
                else
                    result = true;
                break;
            case isValidSalutation:
                if (TextUtils.isEmpty(stringtovalidate) || stringtovalidate == null)
                    message = "" + NC.getResources().getString(R.string.please_select_your_salutation);
                else
                    result = true;
                break;
            case isValidFirstname:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + NC.getResources().getString(R.string.enter_the_first_name);
                else
                    result = true;
                break;
            case isValidLastname:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + NC.getResources().getString(R.string.enter_the_last_name);
                else
                    result = true;
                break;
            case isValidCard:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + NC.getResources().getString(R.string.enter_the_card_number);
                else if (stringtovalidate.length() < 9 || stringtovalidate.length() > 16)
                    message = "" + NC.getResources().getString(R.string.enter_the_valid_card_number);
                else
                    result = true;
                break;
            case isValidExpiry:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + NC.getResources().getString(R.string.enter_the_expiry_date);
                else
                    result = true;
                break;
            case isValidMail:
                if (SessionSave.getSession(TaxiUtil.SKIP_PASSENGER_EMAIL, MainHomeFragmentActivity.this, false)) {
                    if (TextUtils.isEmpty(stringtovalidate))
                        result = true;
                    else if (!validdmail(stringtovalidate))
                        message = "" + NC.getResources().getString(R.string.enter_the_valid_email);
                    else
                        result = true;
                } else {
                    if (TextUtils.isEmpty(stringtovalidate))
                        message = "" + NC.getResources().getString(R.string.enter_the_email);
                    else if (!validdmail(stringtovalidate))
                        message = "" + NC.getResources().getString(R.string.enter_the_valid_email);
                    else
                        result = true;
                }
                break;
            case isValidConfirmPassword:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + NC.getResources().getString(R.string.enter_the_confirmation_password);
                else
                    result = true;
                break;
            case isNullPromoCode:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + NC.getResources().getString(R.string.reg_enterprcode);
                else
                    result = true;
                break;
            case isNullMonth:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + NC.getResources().getString(R.string.reg_expmonth);
                else
                    result = true;
                break;
            case isNullYear:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + NC.getResources().getString(R.string.reg_expyear);
                else
                    result = true;
                break;
            case isValidCvv:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + NC.getResources().getString(R.string.enter_the_valid_CVV);
                else
                    result = true;
                break;
            case isNullCardname:
                if (TextUtils.isEmpty(stringtovalidate))
                    message = "" + NC.getResources().getString(R.string.reg_entercardname);
                else
                    result = true;
                break;
        }
        if (!message.equals("")) {

            dialog = Utility.alert_view_dialog(MainHomeFragmentActivity.this, "" + NC.getResources().getString(R.string.message), "" + message,
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
        return result;
    }


    /**
     * @param string email string is passed as parameter
     * @boolean check the valid email id
     */
    public boolean validdmail(String string) {
        return isValidEmail(string);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mainFrag);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * this class is used to parse the color values in xml
     */

    private synchronized void getAndStoreColorValues(String result) {
        try {


            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            Document doc = dBuilder.parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("*");

            for (int i = 0; i < nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;
                    CL.nfields_byName.put(element2.getAttribute("name"), element2.getTextContent());

                }
            }

            getColorValueDetail();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * this method is used to get color codes from api
     */

    synchronized void getColorValueDetail() {
        Field[] fieldss = R.color.class.getDeclaredFields();
        for (int i = 0; i < fieldss.length; i++) {
            int id = getResources().getIdentifier(fieldss[i].getName(), "color", getPackageName());

            if (CL.nfields_byName.containsKey(fieldss[i].getName())) {
                CL.fields.add(fieldss[i].getName());
                CL.fields_value.add(NC.getResources().getString(id));
                CL.fields_id.put(fieldss[i].getName(), id);
            } else {
            }
        }

        for (Map.Entry<String, String> entry : CL.nfields_byName.entrySet()) {
            String h = entry.getKey();
            String value = entry.getValue();
            CL.nfields_byID.put(CL.fields_id.get(h), CL.nfields_byName.get(h));
        }

    }

    public void isConnect(final boolean isconnect) {

        try {
            if (!isconnect) {


                if (networkAlert != null && networkAlert.isShowing())
                    networkAlert.dismiss();
                final View view = View.inflate(MainHomeFragmentActivity.this, R.layout.netcon_lay, null);
                networkAlert = new Dialog(MainHomeFragmentActivity.this, R.style.dialogwinddow);
                networkAlert.setContentView(view);
                networkAlert.setCancelable(false);
                networkAlert.setCanceledOnTouchOutside(false);
                // FontHelper.applyFont(MainHomeFragmentActivity.this, networkAlert.findViewById(R.id.alert_id));
                networkAlert.show();
                final TextView title_text = networkAlert.findViewById(R.id.title_text);
                final TextView message_text = networkAlert.findViewById(R.id.message_text);
                final Button button_success = networkAlert.findViewById(R.id.button_success);
                final Button button_failure = networkAlert.findViewById(R.id.button_failure);
                title_text.setText("" + NC.getString(R.string.message));
                message_text.setText("" + NC.getString(R.string.check_internet_connection));
                button_success.setText("" + NC.getString(R.string.try_again));
                button_failure.setText("" + NC.getString(R.string.cancel));
                button_success.setOnClickListener(v -> {
                    if (!SessionSave.getSession(PASS_ID, MainHomeFragmentActivity.this).equals("")) {
                        Systems.out.println("tryAgain1");
                        Systems.out.println("tryAgain5");
                        Intent intent = new Intent(MainHomeFragmentActivity.this, SplashActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        startActivity(intent);
                        networkAlert.dismiss();
                        finish();
                    } else {
                        Systems.out.println("tryAgain4");
                    }
                });
                button_failure.setOnClickListener(v -> {
                    networkAlert.dismiss();
                    finish();
                    final Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                });
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSOSService() {
        SessionSave.saveSession("sos_id", SessionSave.getSession(PASS_ID, MainHomeFragmentActivity.this), MainHomeFragmentActivity.this);
        SessionSave.saveSession("user_type", "p", MainHomeFragmentActivity.this);


        startService(new Intent(MainHomeFragmentActivity.this, SOSService.class));
    }

    @Override
    public void onCancelPay(int trip_id, @Nullable String reason, @Nullable String cancelFare, @Nullable String type, @Nullable String order_id) {
        cancelPayment(trip_id, reason, cancelFare, type, order_id);
    }

    public enum ValidateAction {
        NONE, isValueNULL, isValidPassword, isValidSalutation, isValidFirstname, isValidLastname, isValidCard, isValidExpiry, isValidMail, isValidConfirmPassword, isNullPromoCode, isValidCvv, isNullMonth, isNullYear, isNullCardname
    }

    public class SaveSplitType implements APIResult {


        public SaveSplitType(final String url, JSONObject data) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON(MainHomeFragmentActivity.this, this, data, false).execute(url);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {

            try {
                final JSONObject json = new JSONObject(result);
                if (isSuccess) {
                    CToast.ShowToast(MainHomeFragmentActivity.this, json.getString("message"));

                    if (json.getInt("status") == 1) {
                        SessionSave.saveSession(TaxiUtil.isFavDriverOn, true, MainHomeFragmentActivity.this);
                        fav_add.setImageResource(R.drawable.on_btn);
                    } else {
                        SessionSave.saveSession(TaxiUtil.isFavDriverOn, false, MainHomeFragmentActivity.this);
                        fav_add.setImageResource(R.drawable.off_btn);
                    }
                    Log.e("SplitOn ", String.valueOf(SessionSave.getSession(TaxiUtil.isSplitOn, MainHomeFragmentActivity.this, true)));
                } else {
                    MainHomeFragmentActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                CToast.ShowToast(MainHomeFragmentActivity.this, json.getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class InternetConnectionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (getSupportFragmentManager().findFragmentById(R.id.mainFrag) != null) {
                if (getSupportFragmentManager().findFragmentById(R.id.mainFrag) instanceof OnGoingFrag) {
                    try {
                        toolbar_title.setVisibility(View.GONE);
                        toolbar_logo.setVisibility(View.GONE);
                        toolbar_titletm.setVisibility(View.GONE);
                        tool_bar_lay.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new OnGoingFrag()).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void farePayment(String totalFare, String response, String f_fare, String f_tips, String f_total, String type, String order_id) {

        f_fare_payment = f_fare;
        f_tips_payment = f_tips;
        f_total_payment = f_total;

        razor_type = type;


        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Snap-e Cabs");
            options.put("description", "EC WHEELS INDIA PRIVATE LIMITED");
            options.put("image", "https://snapecabs.movex.ai/public/snapecabs/site_logo/logo-small.png");
            options.put("currency", "INR");
            options.put("amount", precision.format(Double.parseDouble(totalFare) * 100));
            options.put("order_id", order_id);
            JSONObject preFill = new JSONObject();
            preFill.put("email", SessionSave.getSession("Email", MainHomeFragmentActivity.this));
            preFill.put("contact", SessionSave.getSession("Phone", MainHomeFragmentActivity.this));
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }


        try {
            JSONObject detail = new JSONObject(response);
            JSONObject json = detail.getJSONObject("detail");
            f_tripid = json.getString("trip_id");
            f_distance = json.getString("distance");
            f_nightfareapplicable = json.getString("nightfare_applicable");
            f_nightfare = json.getString("nightfare");
            f_passengerdiscount = json.getString("passenger_discount");
            f_waitingtime = json.getString("waiting_time");
            f_waitingcost = json.getString("waiting_cost");
            f_taxamount = json.getString("tax_amount");
            f_tripfare = json.getString("trip_fare");
            f_eveningfare_applicable = json.getString("eveningfare_applicable");
            f_eveningfare = json.getString("eveningfare");
            f_minutes_traveled = json.getString("minutes_traveled");
            f_minutes_fare = json.getString("minutes_fare");
            company_tax = json.getString("company_tax");
            base_fare = json.getString("base_fare");
            promodiscount_amount = json.getString("promodiscount_amount");
            fare_calculation_type = json.getString("fare_calculation_type");
            if (json.has("distance_fare")) {
                distanceFare = json.getString("distance_fare");
                System.out.println("distance_fare" + distanceFare);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void walletPayment(long amount, String promocode, String type, String order_id) {
        addmoney = amount;
        promoCode = promocode;
        razor_type = type;
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Snape-E");
            options.put("description", "EC WHEELS INDIA PRIVATE LIMITED");
            options.put("image", "https://snapecabs.movex.ai/public/snapecabs/site_logo/logo-small.png");
            options.put("currency", "INR");
            options.put("amount", precision.format(addmoney * 100));
            options.put("order_id", order_id);
            JSONObject preFill = new JSONObject();
            preFill.put("email", SessionSave.getSession("Email", MainHomeFragmentActivity.this));
            preFill.put("contact", SessionSave.getSession("Phone", MainHomeFragmentActivity.this));
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    public void cancelPayment(int trip_id, String reason, String cancelFare, String type, String order_id) {
        c_tripid = trip_id;
        c_reason = reason;
        cancel_fare = cancelFare;
        razor_type = type;
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Snape-E");
            options.put("description", "EC WHEELS INDIA PRIVATE LIMITED");
            options.put("image", "https://snapecabs.movex.ai/public/snapecabs/site_logo/logo-small.png");
            options.put("currency", "INR");
            options.put("amount", precision.format(Double.parseDouble(cancelFare) * 100));
            options.put("order_id", order_id);
            JSONObject preFill = new JSONObject();
            preFill.put("email", SessionSave.getSession("Email", MainHomeFragmentActivity.this));
            preFill.put("contact", SessionSave.getSession("Phone", MainHomeFragmentActivity.this));
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {

        if (razor_type.equalsIgnoreCase("1")) {
            try {
                try {
                    JSONObject j = new JSONObject();
                    j.put("passenger_id", SessionSave.getSession(PASS_ID, MainHomeFragmentActivity.this));
                    j.put("creditcard_no", "");
                    j.put("expmonth", "");
                    j.put("expyear", "");
                    j.put("creditcard_cvv", "");
                    j.put("savecard", "0");
                    j.put("default", "");
                    j.put("promo_code", promoCode);
                    j.put("cardholder_name", "");
                    j.put("money", addmoney);
                    j.put("order_id", razorpayPaymentID);
                    j.put("payment_type", "");
                    final String url = "type=passenger_wallet_addmoney";
                    new AddMoney(url, j);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.e("Razor_payment", "Exception in onPaymentSuccess", e);
            }
        } else if (razor_type.equalsIgnoreCase("2")) {

            try {
                String url = "type=tripfare_update";
                JSONObject j = new JSONObject();
                j.put("trip_id", f_tripid);
                j.put("distance", f_distance);
                j.put("actual_distance", "");
                j.put("actual_amount", "" + f_total_payment);
                j.put("trip_fare", f_tripfare);
                j.put("fare", "" + f_fare_payment);
                j.put("tips", "" + f_tips_payment);
                j.put("passenger_promo_discount", f_passengerdiscount);
                j.put("tax_amount", f_taxamount);
                j.put("remarks", "");
                j.put("nightfare_applicable", f_nightfareapplicable);
                j.put("nightfare", f_nightfare);
                j.put("eveningfare_applicable", f_eveningfare_applicable);
                j.put("eveningfare", f_eveningfare);
                j.put("waiting_time", f_waitingtime);
                j.put("waiting_cost", f_waitingcost);
                j.put("creditcard_no", "");
                j.put("creditcard_cvv", "");
                j.put("expmonth", "" + "");
                j.put("expyear", "" + "");
                j.put("pay_mod_id", "2");
                j.put("passenger_discount", "");
                j.put("minutes_traveled", f_minutes_traveled);
                j.put("minutes_fare", f_minutes_fare);
                j.put("order_id", razorpayPaymentID);
                j.put("company_tax", company_tax);
                j.put("base_fare", base_fare);
                j.put("promodiscount_amount", promodiscount_amount);
                j.put("fare_calculation_type", fare_calculation_type);
                j.put("distance_fare", distanceFare);
                new FareUpdate(url, j);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (razor_type.equalsIgnoreCase("3")) {
            try {
                JSONObject j = new JSONObject();
                j.put("passenger_log_id", c_tripid);
                j.put("travel_status", "4");
                j.put("remarks", c_reason);
                j.put("pay_mod_id", "2");
                j.put("total_fare", cancel_fare);
                j.put("order_id", razorpayPaymentID);
                j.put("creditcard_cvv", "");
                new Cancel_afterTrip("type=cancel_trip", j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Payment failed:", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            System.out.println("Payment_Failed....." + code + "......" + response);
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Razor_payment", "Exception in onPaymentError", e);
        }
    }

    private class FareUpdate implements APIResult {
        String msg = "";

        FareUpdate(String url, JSONObject data) {
            new APIService_Retrofit_JSON(MainHomeFragmentActivity.this, this, data, false).execute(url);
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {

            try {
                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        msg = json.getString("message");
                    } else {
                        msg = json.getString("message");
                    }
                    CToast.ShowToast(MainHomeFragmentActivity.this, msg);
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            CToast.ShowToast(MainHomeFragmentActivity.this, NC.getString(R.string.server_error));
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class Cancel_afterTrip implements APIResult {

        String alert_message = "";


        public Cancel_afterTrip(String url, JSONObject data) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON(MainHomeFragmentActivity.this, this, data, false, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession("Lang", MainHomeFragmentActivity.this) + "&" + url).execute();
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {

            try {
                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        if (SessionSave.getSession("multi_tripID", MainHomeFragmentActivity.this).equals("") || SessionSave.getSession("multi_tripID", MainHomeFragmentActivity.this).equals(c_tripid))
                            SessionSave.saveSession("trip_id", "", MainHomeFragmentActivity.this);

                        SessionSave.saveSession("TaxiStatus", "", MainHomeFragmentActivity.this);
                        alert_message = json.getString("message") + "\n" + NC.getResources().getString(R.string.canceled_amount) + " " + SessionSave.getSession("Currency", MainHomeFragmentActivity.this) + json.getString("cancellation_amount") + "\n" + getResources().getString(R.string.canceled_from) + " " + json.getString("cancellation_from");
                        Intent intent = new Intent(MainHomeFragmentActivity.this, GetPassengerUpdate.class);
                        stopService(intent);

                        CToast.ShowToast(MainHomeFragmentActivity.this, alert_message);
                        BookTaxiHomePage.Companion.setBookingState(BookTaxiHomePage.BOOKINGSTATE.STATE_ONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new BookTaxiHomePage()).commitAllowingStateLoss();


                    } else if (json.getInt("status") == 2) {

                        if (SessionSave.getSession("multi_tripID", MainHomeFragmentActivity.this).equals("") || SessionSave.getSession("multi_tripID", MainHomeFragmentActivity.this).equals(c_tripid))
                            SessionSave.saveSession("trip_id", "", MainHomeFragmentActivity.this);

                        SessionSave.saveSession("TaxiStatus", "", MainHomeFragmentActivity.this);
                        SessionSave.saveSession("TaxiStatus", "", MainHomeFragmentActivity.this);
                        alert_message = json.getString("message");
                        Intent intent = new Intent(MainHomeFragmentActivity.this, GetPassengerUpdate.class);
                        stopService(intent);
                        CToast.ShowToast(MainHomeFragmentActivity.this, alert_message);

                        BookTaxiHomePage.Companion.setBookingState(BookTaxiHomePage.BOOKINGSTATE.STATE_ONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new BookTaxiHomePage()).commit();

                    } else if (json.getInt("status") == -1) {
                        CToast.ShowToast(MainHomeFragmentActivity.this, json.getString("message"));
                        SessionSave.saveSession("TaxiStatus", "", MainHomeFragmentActivity.this);
                        if (SessionSave.getSession("multi_tripID", MainHomeFragmentActivity.this).equals("") || SessionSave.getSession("multi_tripID", MainHomeFragmentActivity.this).equals(c_tripid))
                            SessionSave.saveSession("trip_id", "", MainHomeFragmentActivity.this);
                        SessionSave.saveSession("TaxiStatus", "", MainHomeFragmentActivity.this);
                        //SessionSave.saveSession("trip_id", "", getActivity());
                        alert_message = json.getString("message");
                        Intent intent = new Intent(MainHomeFragmentActivity.this, GetPassengerUpdate.class);
                        stopService(intent);


                        BookTaxiHomePage.Companion.setBookingState(BookTaxiHomePage.BOOKINGSTATE.STATE_ONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, new BookTaxiHomePage()).commit();

                    } else if (json.getInt("status") == 3) {
                        CToast.ShowToast(MainHomeFragmentActivity.this, json.getString("message"));
                    } else {
                        CToast.ShowToast(MainHomeFragmentActivity.this, json.getString("message"));
                    }

                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            CToast.ShowToast(MainHomeFragmentActivity.this, NC.getString(R.string.server_con_error));
                        }
                    });
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    /**
     * This class used to register with card details
     * <p/>
     * This class used to register with card details
     * <p/>
     *
     * @author developer
     */
    private class AddMoney implements APIResult {
        private AddMoney(final String url, JSONObject data) {

            new APIService_Retrofit_JSON(MainHomeFragmentActivity.this, this, data, false).execute(url);

        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            if (isSuccess) {
                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        SessionSave.saveSession(CREDIT_CARD, "" + json.getString("credit_card_status"), MainHomeFragmentActivity.this);
                      //  this.razerpayListener.responseReceived(true);
                    } else {

                    }
                } catch (final JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        CToast.ShowToast(MainHomeFragmentActivity.this, NC.getString(R.string.server_con_error));
                    }
                });
            }
        }
    }

}
