package com.taximobility.driver;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.driver.data.DriverCommonData;
import com.taximobility.driver.interfaces.DriverAPIResult;
import com.taximobility.driver.interfaces.DriverClickInterface;
import com.taximobility.driver.service.DriverAPIService_Retrofit_JSON;
import com.taximobility.driver.service.DriverNonActivity;
import com.taximobility.driver.utils.DriverCToast;
import com.taximobility.driver.utils.DirverColorchange;
import com.taximobility.driver.utils.DriverDatePicker_CardExpiry;
import com.taximobility.driver.utils.DriverFontHelper;
import com.taximobility.driver.utils.DriverFourDigitCardFormatWatcher;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.driver.utils.Driver_Utils;
import com.taximobility.features.CToast;
import com.taximobility.service.CoreClient;
import com.taximobility.service.RetrofitCallbackClass;
import com.taximobility.util.AppController;
import com.taximobility.util.NC;
import com.taximobility.util.NetworkStatus;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * This class  is used to complete the payment by new card
 */
public class DriverPayuncardAct extends MainActivityDriver implements OnClickListener, DriverClickInterface, DriverDatePicker_CardExpiry.DialogInterface {
    private EditText cardEdt;
    private TextView expireEdt;
    private TextView DoneBtn, backbtn;
    private EditText cvvEdt;
    private TextView HeadTitle;
    private String message;
    private String f_tripid;
    private String f_distance;
    private String f_nightfareapplicable;
    private String f_nightfare;
    private String f_eveningfare_applicable;
    private String f_eveningfare;
    private String f_passengerdiscount;
    private String f_waitingtime;
    private String f_waitingcost;
    private String f_taxamount;
    private String f_tripfare;
    private String f_total;
    private String f_fare;
    private String f_tips;
    private String creditcard_no;
    private String creditcard_cvv;
    private String expmonth;
    private String expyear;
    private String expdate;
    private int mMonth;
    private int mDay;
    private int mYear;
    private static final int DATE_DIALOG_ID = 0;
    private String f_paymodid = "3";
    private String group_id;
    private String account_id;
    private String info;
    private String f_minutes_traveled;
    private String f_minutes_fare;
    private DriverDatePicker_CardExpiry editNameDialog;
    private String company_tax;
    private String base_fare;
    private String promodiscount_amount;
    private String fare_calculation_type;
    private Dialog dialog1;
    private String distanceFare;
    private String service_id;
    DriverNonActivity nonactiityobj = new DriverNonActivity();
    private String delivery_fare = "0", per_kg_price = "0";
    private WebView webviewww;
    private Dialog mDialog;
    private String order_id="";


    /**
     * set layout to the activity
     */
    @Override
    public int setLayout() {
        setLocale();
        return R.layout.driver_payuncard_lay;
    }

    /**
     * Initializing UI Components
     */
    @Override
    public void Initialize() {
        Bundle bun = getIntent().getExtras();
        DriverCommonData.current_act = "PayuncardAct";
        DriverFontHelper.applyFont(this, findViewById(R.id.id_paylay));
        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverPayuncardAct.this
                .findViewById(android.R.id.content)).getChildAt(0)), DriverPayuncardAct.this);
        if (bun != null) {
            HeadTitle = findViewById(R.id.signup_title);
            cardEdt = findViewById(R.id.cardEdt);
            cardEdt.addTextChangedListener(new DriverFourDigitCardFormatWatcher());
            expireEdt = findViewById(R.id.expireEdt);
            expireEdt.setHint(DriverNC.getString(R.string.p_expiry));
            cvvEdt = findViewById(R.id.cvvEdt);
            DoneBtn = findViewById(R.id.doneBtn);
            backbtn = findViewById(R.id.backImg);
            HeadTitle.setText("" + DriverNC.getResources().getString(R.string.heading_payuncard));
            webviewww = findViewById(R.id.webview);

            info = bun.getString("info");
            service_id = bun.getString("service_id");
            if (info.equals("Account")) {
                message = bun.getString("message");
                f_total = bun.getString("f_total");
                f_fare = bun.getString("f_fare");
                f_tips = bun.getString("f_tips");
                group_id = bun.getString("gid");
                account_id = bun.getString("aid");
                Log.i("f_total", "" + f_total);
                Log.i("f_fare", "" + f_fare);
                Log.i("f_tips", "" + f_tips);
                Log.i("group", "" + group_id);
                Log.i("account", "" + account_id);
            } else if (info.equals("Uncard")) {
                message = bun.getString("message");
                f_total = bun.getString("f_total");
                f_fare = bun.getString("f_fare");
                f_tips = bun.getString("f_tips");
                Log.i("f_total", "" + f_total);
                Log.i("f_fare", "" + f_fare);
                Log.i("f_tips", "" + f_tips);
            }
            DoneBtn.setText("" + DriverNC.getResources().getString(R.string.done));
            Calendar cal = Calendar.getInstance();
            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);
            setonclickListener();
            try {
                JSONObject detail = new JSONObject(message);
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
                }

                if (json.has("delivery_fare")) {
                    delivery_fare = json.getString("delivery_fare");
                }

                if (json.has("per_kg_price")) {
                    per_kg_price = json.getString("per_kg_price");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            callurl_paymentgateway();
        }
    }

    private void callurl_paymentgateway() {

        try {
            String url = "type=tripfare_transactionlink";
            JSONObject j = new JSONObject();
            j.put("trip_id", f_tripid);
            j.put("distance", f_distance);
            j.put("actual_distance", MainActivityDriver.mMyStatus.getdistance());
            j.put("actual_amount", "" + f_total);
            j.put("trip_fare", f_tripfare);
            j.put("fare", "" + f_fare);
            j.put("tips", "" + f_tips);
            j.put("passenger_promo_discount", f_passengerdiscount);
            j.put("tax_amount", f_taxamount);
            j.put("remarks", "");
            j.put("nightfare_applicable", f_nightfareapplicable);
            j.put("nightfare", f_nightfare);
            j.put("eveningfare_applicable", f_eveningfare_applicable);
            j.put("eveningfare", f_eveningfare);
            j.put("waiting_time", f_waitingtime);
            j.put("waiting_cost", f_waitingcost);
            j.put("creditcard_no", creditcard_no);
            j.put("creditcard_cvv", creditcard_cvv);
            j.put("expmonth", "" + expmonth);
            j.put("expyear", "" + expyear);
            j.put("pay_mod_id", f_paymodid);
            j.put("passenger_discount", "");
            j.put("minutes_traveled", f_minutes_traveled);
            j.put("minutes_fare", f_minutes_fare);
            j.put("company_tax", company_tax);
            j.put("base_fare", base_fare);
            j.put("promodiscount_amount", promodiscount_amount);
            j.put("fare_calculation_type", fare_calculation_type);
            j.put("distance_fare", distanceFare);
            j.put("service_id", service_id);
            j.put("delivery_fare", delivery_fare);
            j.put("per_kg_price", per_kg_price);
            new FareUpdate_Paymentgateway(url, j);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * FareUpdate API response parsing.
     */
    private class FareUpdate_Paymentgateway implements DriverAPIResult {
        String msg = "";

        FareUpdate_Paymentgateway(String url, JSONObject data) {
            if (isOnline()) {
               /* if (nonactiityobj != null) {
                    nonactiityobj.stopServicefromNonActivity(DriverPayuncardAct.this);
                }*/
                new DriverAPIService_Retrofit_JSON(DriverPayuncardAct.this, this, data, false, 3000).execute(url);
            }

        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            try {
               /* if (nonactiityobj != null) {
                    nonactiityobj.startServicefromNonActivity(DriverPayuncardAct.this);
                }
*/
                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        //JSONObject jsonDriver = json.getJSONObject("driver_statistics");
                        redirectwebpage(json.getString("transaction_id"));

                    } else {
                        msg = json.getString("message");
                        DriverCToast.ShowToast(DriverPayuncardAct.this, "" + msg);
//                        dialog1 = Driver_Utils.alert_view(DriverPayuncardAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverPayuncardAct.this, "");
                    }
                } else {
                    runOnUiThread(() -> DriverCToast.ShowToast(DriverPayuncardAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void redirectwebpage(String url) {

        webviewww.setVisibility(View.VISIBLE);
        webviewww.loadUrl(url);
        //showDialog();
        WebSettings webSettings = webviewww.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webviewww.setWebViewClient(new MyWebViewClient());

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                Uri uri = Uri.parse(url);
                //String msg = uri.getQueryParameter("source");
                System.out.println("message is===" + url);

                if (url.contains("status") && url.contains("successful")) {
                    SearchApiCall(url);
                    //showDialog();
                } else {
                    CToast.ShowToast(DriverPayuncardAct.this, "Payment Failed");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            try {
                //closeDialog();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }


    private void SearchApiCall(String url) {
        if (NetworkStatus.isOnline(DriverPayuncardAct.this)) {
            String baseUrl = url;
            CoreClient polyline = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl();
            polyline.getJsonbyWholeUrl("no-cache", baseUrl)
                    .enqueue(new RetrofitCallbackClass<JsonObject>(DriverPayuncardAct.this, new Callback<JsonObject>() {
                        @Override
                        public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                            //closeDialog();
                            if (response.isSuccessful()) {
                                String result = response.body().toString();
                                String msg="";
                                try {
                                    JSONObject json = new JSONObject(result);
                                    if (json.getInt("status") == 1) {

                                        order_id=json.getString("order_id");
                                        callurl();
                                       // CToast.ShowToast(DriverPayuncardAct.this, json.getString("message"));
//                                        DriverCommonData.travel_km = 0;
//                                        msg= json.getString("message");
//                                        DriverSessionSave.setGoogleDistance(0f, DriverPayuncardAct.this);
//                                        DriverSessionSave.setDistance(0f, DriverPayuncardAct.this);
//                                        DriverSessionSave.saveGoogleWaypoints(null, null, "", 0.0, "", DriverPayuncardAct.this);
//                                        DriverSessionSave.saveWaypoints(null, null, "", 0.0, "", DriverPayuncardAct.this);
//                                        Intent jobintent = new Intent(DriverPayuncardAct.this, DriverJobdoneAct.class);
//                                        Bundle bun = new Bundle();
//                                        bun.putString("message", result);
//                                        jobintent.putExtras(bun);
//                                        startActivity(jobintent);


                                    }else {
                                        msg = json.getString("message");
                                    }
                                   // dialog1 = Driver_Utils.alert_view(DriverPayuncardAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverPayuncardAct.this, "");


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            } else {
                                CToast.ShowToast(DriverPayuncardAct.this, NC.getString(R.string.server_con_error));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<JsonObject> call, Throwable t) {
                            // ShowToast.center(DriverPayuncardAct.this, t.getLocalizedMessage());
                        }
                    }));
        } else {
            CToast.ShowToast(DriverPayuncardAct.this, NC.getString(R.string.check_internet_connection));
        }

    }


    public void showDialog() {
        try {
            if (NetworkStatus.isOnline(DriverPayuncardAct.this)) {
                if (DriverPayuncardAct.this != null) {

                    if (mDialog != null && mDialog.isShowing())
                        mDialog.dismiss();
                    View view = View.inflate(DriverPayuncardAct.this, R.layout.progress_bar, null);
                    mDialog = new Dialog(DriverPayuncardAct.this, R.style.dialogwinddow);
                    mDialog.setContentView(view);
                    mDialog.setCancelable(false);

                    mDialog.show();

                    ImageView iv = mDialog.findViewById(R.id.giff);
                    DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                    Glide.with(DriverPayuncardAct.this)
                            .load(R.raw.loading_anim)
                            .into(imageViewTarget);
                }


            }
        } catch (Exception e) {

        }

    }

    public void closeDialog() {
        try {
            if (mDialog != null)
                if (mDialog.isShowing())
                    mDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setting on click listeners
     */
    private void setonclickListener() {
        DoneBtn.setOnClickListener(this);
        backbtn.setOnClickListener(this);
        expireEdt.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        if (dialog1 != null)
            Driver_Utils.closeDialog(dialog1);
        super.onDestroy();
    }

    /**
     * Donebtn onclick
     */
    @Override
    public void onClick(View v) {
        try {
            Calendar cl = Calendar.getInstance();
            int cl_year = cl.get(Calendar.YEAR);
            if (v == DoneBtn) {
                creditcard_no = cardEdt.getText().toString().trim().replaceAll("\\s", "");
                creditcard_cvv = cvvEdt.getText().toString().trim();
                expdate = expireEdt.getText().toString().trim();
                if (creditcard_no.length() == 0) {

                    DriverCToast.ShowToast(DriverPayuncardAct.this, "" + DriverNC.getResources().getString(R.string.ent_ccard_no));
//                    dialog1 = Driver_Utils.alert_view(DriverPayuncardAct.this, "", "" + DriverNC.getResources().getString(R.string.ent_ccard_no), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverPayuncardAct.this, "");


                } else if (creditcard_no.length() < 9) {
                    DriverCToast.ShowToast(DriverPayuncardAct.this, "" + DriverNC.getResources().getString(R.string.ent_chk_ccard));
//                    dialog1 = Driver_Utils.alert_view(DriverPayuncardAct.this, "", "" + DriverNC.getResources().getString(R.string.ent_chk_ccard), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverPayuncardAct.this, "");


                } else if (creditcard_no.length() > 16) {
                    DriverCToast.ShowToast(DriverPayuncardAct.this, "" + DriverNC.getResources().getString(R.string.ent_chk_ccard));
//                    dialog1 = Driver_Utils.alert_view(DriverPayuncardAct.this, "", "" + DriverNC.getResources().getString(R.string.ent_chk_ccard), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverPayuncardAct.this, "");


                } else if (expdate.length() == 0) {
                    DriverCToast.ShowToast(DriverPayuncardAct.this, "" + DriverNC.getResources().getString(R.string.ent_exp_date));
//                    dialog1 = Driver_Utils.alert_view(DriverPayuncardAct.this, "", "" + DriverNC.getResources().getString(R.string.ent_exp_date), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverPayuncardAct.this, "");

                } else if (mYear < cl_year) {
                    DriverCToast.ShowToast(DriverPayuncardAct.this, "" + DriverNC.getResources().getString(R.string.ent_exp_year_gt));
//                    dialog1 = Driver_Utils.alert_view(DriverPayuncardAct.this, "", "" + DriverNC.getResources().getString(R.string.ent_exp_year_gt), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverPayuncardAct.this, "");

                } else if (creditcard_cvv.length() == 0) {
                    DriverCToast.ShowToast(DriverPayuncardAct.this, "" + DriverNC.getResources().getString(R.string.ent_cvv));
//                    dialog1 = Driver_Utils.alert_view(DriverPayuncardAct.this, "", "" + DriverNC.getResources().getString(R.string.ent_cvv), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverPayuncardAct.this, "");


                } else if (creditcard_cvv.length() < 3) {
                    DriverCToast.ShowToast(DriverPayuncardAct.this, "" +  DriverNC.getResources().getString(R.string.ent_chk_cvv));
//                    dialog1 = Driver_Utils.alert_view(DriverPayuncardAct.this, "", "" + DriverNC.getResources().getString(R.string.ent_chk_cvv), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverPayuncardAct.this, "");


                } else if (creditcard_cvv.length() > 4) {
                    DriverCToast.ShowToast(DriverPayuncardAct.this, "" +  DriverNC.getResources().getString(R.string.ent_chk_cvv));
//                    dialog1 = Driver_Utils.alert_view(DriverPayuncardAct.this, "", "" + DriverNC.getResources().getString(R.string.ent_chk_cvv), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverPayuncardAct.this, "");

                } else {
                    expmonth = Integer.toString(mMonth);
                    if (expmonth.length() == 1) {
                        expmonth = "0" + expmonth;
                    }
                    expyear = Integer.toString(mYear);
                    callurl();
                }
            } else if (v == expireEdt) {
                FragmentManager fm = getSupportFragmentManager();
                editNameDialog = new DriverDatePicker_CardExpiry();
                Bundle bundle = new Bundle();
                bundle.putString("KEY", "1");
                editNameDialog.setArguments(bundle);
                editNameDialog.show(fm, "fragment_edit_name");

            } else if (v == backbtn) {
                onBackPressed();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Updating trip fare and response parsing
     */
    private void callurl() {
        try {
            String url = "type=tripfare_update";
            JSONObject j = new JSONObject();
            j.put("trip_id", f_tripid);
            j.put("distance", f_distance);
            j.put("order_id",order_id);
            j.put("actual_distance", MainActivityDriver.mMyStatus.getdistance());
            j.put("actual_amount", "" + f_total);
            j.put("trip_fare", f_tripfare);
            j.put("fare", "" + f_fare);
            j.put("tips", "" + f_tips);
            j.put("passenger_promo_discount", f_passengerdiscount);
            j.put("tax_amount", f_taxamount);
            j.put("remarks", "");
            j.put("nightfare_applicable", f_nightfareapplicable);
            j.put("nightfare", f_nightfare);
            j.put("eveningfare_applicable", f_eveningfare_applicable);
            j.put("eveningfare", f_eveningfare);
            j.put("waiting_time", f_waitingtime);
            j.put("waiting_cost", f_waitingcost);
            j.put("creditcard_no", creditcard_no);
            j.put("creditcard_cvv", creditcard_cvv);
            j.put("expmonth", "" + expmonth);
            j.put("expyear", "" + expyear);
            j.put("pay_mod_id", "2");
            j.put("passenger_discount", "");
            j.put("minutes_traveled", f_minutes_traveled);
            j.put("minutes_fare", f_minutes_fare);
            j.put("company_tax", company_tax);
            j.put("base_fare", base_fare);
            j.put("promodiscount_amount", promodiscount_amount);
            j.put("fare_calculation_type", fare_calculation_type);
            j.put("distance_fare", distanceFare);
            j.put("service_id", service_id);
            j.put("delivery_fare", delivery_fare);
            j.put("per_kg_price", per_kg_price);
            new FareUpdate(url, j);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(int monthOfYear, int year, int day) {
        mYear = year;
        mMonth = monthOfYear + 1;
        mDay = 2;
        editNameDialog.dismiss();
        expireEdt.setText(new StringBuilder().append(mMonth).append("/").append(mYear).append(" "));
    }

    @Override
    public void failure(String inputText) {
        editNameDialog.dismiss();
    }


    /**
     * FareUpdate API response parsing.
     */
    private class FareUpdate implements DriverAPIResult {
        String msg = "";

        FareUpdate(String url, JSONObject data) {
            if (isOnline()) {
                if (nonactiityobj != null) {
                    nonactiityobj.stopServicefromNonActivity(DriverPayuncardAct.this);
                }
                new DriverAPIService_Retrofit_JSON(DriverPayuncardAct.this, this, data, false, 3000).execute(url);
            }

        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            try {
                if (nonactiityobj != null) {
                    nonactiityobj.startServicefromNonActivity(DriverPayuncardAct.this);
                }
                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        JSONObject jsonDriver = json.getJSONObject("driver_statistics");
                        DriverSessionSave.saveSession("driver_statistics", "" + jsonDriver, DriverPayuncardAct.this);
                        msg = json.getString("message");
                        DriverCommonData.travel_km = 0;
                        DriverSessionSave.setGoogleDistance(0f, DriverPayuncardAct.this);
                        DriverSessionSave.setDistance(0f, DriverPayuncardAct.this);
                        DriverSessionSave.saveGoogleWaypoints(null, null, "", 0.0, "", DriverPayuncardAct.this);
                        DriverSessionSave.saveWaypoints(null, null, "", 0.0, "", DriverPayuncardAct.this);
                        Intent jobintent = new Intent(DriverPayuncardAct.this, DriverJobdoneAct.class);
                        Bundle bun = new Bundle();
                        bun.putString("message", result);
                        jobintent.putExtras(bun);
                        startActivity(jobintent);
                    } else {
                        msg = json.getString("message");
                    }
                    DriverCToast.ShowToast(DriverPayuncardAct.this, "" + msg);
//                    dialog1 = Driver_Utils.alert_view(DriverPayuncardAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverPayuncardAct.this, "");
                } else {
                    runOnUiThread(() -> DriverCToast.ShowToast(DriverPayuncardAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Date picker
     */
    private DatePickerDialog.OnDateSetListener mDateSetListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;
            expireEdt.setText(new StringBuilder().append(mMonth).append("/").append(mYear).append(" "));
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATE_DIALOG_ID) {
            DatePickerDialog datePickerDialog = this.customDatePicker();
            return datePickerDialog;
        }
        return null;
    }

    /**
     * Date picker custom dialog
     */
    private DatePickerDialog customDatePicker() {
        DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListner, mYear, mMonth, mDay);
        try {
            Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (Field datePickerField : datePickerFields) {
                        if ("mDayPicker".equals(datePickerField.getName()) || "mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dpd;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void positiveButtonClick(DialogInterface dialog, int id, String s) {
        dialog.dismiss();
    }

    @Override
    public void negativeButtonClick(DialogInterface dialog, int id, String s) {
        dialog.dismiss();
    }
}
