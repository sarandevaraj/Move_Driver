package com.taximobility;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.taximobility.bookingmodule.BookTaxiHomePage;
import com.taximobility.interfaces.APIResult;
import com.taximobility.interfaces.DialogInterface;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.service.APIService_Retrofit_JSON_NoProgress;
import com.taximobility.service.GetPassengerUpdate;
import com.taximobility.service.RetrofitCallbackClass;
import com.taximobility.util.AppController;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.taximobility.util.ConstantsKt.CREDIT_CARD;
import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.PASS_TRIP_ID;
import static com.taximobility.util.ConstantsKt.REQUEST_TIME;
import static com.taximobility.util.ConstantsKt.REQ_TRIP_ID;


/**
 * this class is used to request taxi
 *
 * @author developer
 */
public class ContinousRequest extends MainActivity implements DialogInterface {
    private final long interval = 5000;
    private long startTime = 90 * 1000;
    private int toaststatus = 0;
    private String json1 = "", url = "", approx_fare = "";
    int time_out = 30;
    private Dialog dialog;
    private TextView remnTimeTxt, secTxt, minTxt;
    private CountDownTimer countDownTimer1;
    private DonutProgress donut_progress;
    private AnimatorSet set;
    private LinearLayout estimate_lay, request_include_lay;
    private LinearLayout leftIconTxt;
    private RelativeLayout loading_contain;
    private SpinKitView progressBar;
    private TextView txt_estimate_fare, estimate_fare;
    private TextView Cancel, headertxt, request_txt;
    private ImageView ivLine;

    private Handler getPassengerUpdateHandler;
    private Call<ResponseBody> getPassengerUpdateCall;
    private AnimatedVectorDrawable animatedVectorDrawable = null;


    private Runnable getPassengerUpdateRunnable = () -> callGetPassengerUpdateAPi();

    // Set the layout to activity.
    @Override
    public int setLayout() {
        setLocale();
        return R.layout.loading;
    }

    // Initialize the views on layout
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void Initialize() {
        /*Colorchange.ChangeColor((ViewGroup) (((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0)), ContinousRequest.this);*/
        try {
            json1 = getIntent().getStringExtra("json");
            url = getIntent().getStringExtra("url");
            approx_fare = getIntent().getStringExtra("approx_fare");
        } catch (Exception e) {
            e.printStackTrace();
            SessionSave.saveSession(REQ_TRIP_ID, "", ContinousRequest.this);
            finish();
        }
        time_out= Integer.parseInt(SessionSave.getSession(REQUEST_TIME, ContinousRequest.this));
        getPassengerUpdateHandler = new Handler();
        TaxiUtil.sContext = this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Cancel = findViewById(R.id.ReqcancelTxt);
        headertxt = findViewById(R.id.header_titleTxt);
        leftIconTxt = findViewById(R.id.leftIconTxt);
        leftIconTxt.setVisibility(View.INVISIBLE);
        loading_contain = findViewById(R.id.loading_contain);
        headertxt.setText(NC.getResources().getString(R.string.book_taxi));
        request_txt = findViewById(R.id.request_txt);
        progressBar = findViewById(R.id.dotsProgressBar);
        estimate_lay = findViewById(R.id.estimate_lay);
        txt_estimate_fare = findViewById(R.id.txt_estimate_fare);
        donut_progress = findViewById(R.id.donut_progress);
        request_include_lay = findViewById(R.id.request_include_lay);
        estimate_fare = findViewById(R.id.estimate_fare);
        ivLine = findViewById(R.id.img_iv_line);
        secTxt = findViewById(R.id.secTxt);
        minTxt = findViewById(R.id.minTxt);
        remnTimeTxt = findViewById(R.id.rmnTimeTxt);
        donut_progress.setShowText(false);
        set = (AnimatorSet) AnimatorInflater.loadAnimator(ContinousRequest.this, R.animator.progress_anim);
        set.setInterpolator(new DecelerateInterpolator());
        set.setTarget(donut_progress);
        set.setDuration((time_out) * 1000);
        set.start();

        if (BookTaxiHomePage.Companion.getSearchPage().getDropLatLng() != null && BookTaxiHomePage.Companion.getSearchPage().getDropLatLng().latitude != 0.0) {
            estimate_lay.setVisibility(View.VISIBLE);
            txt_estimate_fare.setText(NC.getString(R.string.estimate_fare) + "\t" + SessionSave.getSession("Currency", ContinousRequest.this) + approx_fare);
            request_include_lay.setBackgroundColor(getResources().getColor(R.color.white));
        }

        if (!TextUtils.isEmpty(approx_fare)) {
            estimate_fare.setText(SessionSave.getSession("Currency", ContinousRequest.this) + approx_fare);
        } else {
            estimate_fare.setText(SessionSave.getSession("Currency", ContinousRequest.this) + "0.0");

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivLine.setBackground(getDrawable(R.drawable.progress_line_anim));
        }
        startLineProgress();
        TaxiUtil.current_act = "ContinousRequest";
        FontHelper.applyFont(this, findViewById(R.id.loading_contain));

        if (!SessionSave.getSession(REQUEST_TIME, ContinousRequest.this).equalsIgnoreCase("") && !SessionSave.getSession(REQUEST_TIME, ContinousRequest.this).equalsIgnoreCase(null))
            startTime = (Long.parseLong(SessionSave.getSession(REQUEST_TIME, ContinousRequest.this))) * 1000;
        countDownTimer1 = new CountDownTimer((time_out) * 1000, 1000) {
            int time = 1;

            @Override
            public void onTick(final long millisUntilFinished_) {

                Systems.out.println("NOTIFY onTick");
                long sec = millisUntilFinished_ / 1000;
                long minutes = 0;
                if (sec >= 60) {
                    minutes = sec / 60;
                    sec = sec - (minutes * 60);
                }
                minTxt.setText("" + String.format(Locale.UK, String.valueOf(minutes))+" min");
                secTxt.setText("" + String.format(Locale.UK, "%1$02d", sec)+" sec");
                if (minutes > 0)
                    remnTimeTxt.setText(String.format("%1$02d", minutes) + " " + NC.getResources().getString(R.string.minutestxt).toUpperCase() + ":" + String.format("%1$02d", sec) + " " + NC.getResources().getString(R.string.secondstxt).toUpperCase() + " " + NC.getResources().getString(R.string.seconds_to_left));
                else
                    remnTimeTxt.setText(String.format("%1$02d", sec) + " " + NC.getResources().getString(R.string.secondstxt).toUpperCase() + " " + NC.getResources().getString(R.string.seconds_to_left));
                time++;
            }

            @Override
            public void onFinish() {
                try {
                    minTxt.setText("0 min");
                    secTxt.setText("00 sec");
                    set.start();
                    countDownTimer1.start();
                    if (!SessionSave.getSession(REQ_TRIP_ID, ContinousRequest.this).equals("")) {
                        JSONObject j = new JSONObject();
                        j.put("passenger_tripid", SessionSave.getSession(REQ_TRIP_ID, ContinousRequest.this));
                        new CancelTrip("type=getdriver_reply", j);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


        getPassengerUpdateHandler.post(getPassengerUpdateRunnable);
        Cancel.setOnClickListener(v -> {
            if (getPassengerUpdateHandler != null)
                getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
            try {
                request_txt.setText("" + NC.getResources().getString(R.string.canceling_taxi));
                Cancel.setClickable(false);
                JSONObject j = new JSONObject();
                j.put("passenger_tripid", SessionSave.getSession(REQ_TRIP_ID, ContinousRequest.this));
                final String url = SessionSave.getSession("base_url", ContinousRequest.this) + SessionSave.getSession("api_key", ContinousRequest.this) + "/?" + "lang=" + SessionSave.getSession("Lang", ContinousRequest.this) + "&" + "type=getdriver_reply" + "&" + "encode=" + SessionSave.getSession("encode", ContinousRequest.this);
                new Getpassenger("type=getdriver_reply", j);
            } catch (Exception e) {
                e.printStackTrace();
                clearIdAndRedirect();
            }
            countDownTimer1.cancel();
            SessionSave.saveSession(REQ_TRIP_ID, "", ContinousRequest.this);
            Intent intent = new Intent(getApplicationContext(), ContinousRequest.class);
            getApplicationContext().stopService(intent);
            finish();
        });
    }

    private void callGetPassengerUpdateAPi() {
        JSONObject j = new JSONObject();
        try {
            j.put("trip_id", SessionSave.getSession(REQ_TRIP_ID, ContinousRequest.this));
            j.put("request_type", "1");
            j.put("passenger_id", SessionSave.getSession(PASS_ID, ContinousRequest.this));
            j.put("multi_tripID", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url1 = "getpassenger_update";
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (j).toString());
        getPassengerUpdateCall = AppController.getInstance().getApiManagerWithEncryptBaseUrl().updateUser(TaxiUtil.COMPANY_KEY, body, url1, SessionSave.getSession("Lang", ContinousRequest.this), SessionSave.getSession(PASS_ID, ContinousRequest.this));
        getPassengerUpdateCall.enqueue(new RetrofitCallbackClass<>(ContinousRequest.this, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseString = response.body().string();
                        if (!responseString.isEmpty()) {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.getInt("status") == 1) {
                                if (jsonObject.has("multi_tripID")) {
                                    JSONArray multi_tripID = jsonObject.getJSONArray("multi_tripID");
                                    StringBuilder arrayString = new StringBuilder();
                                    boolean intrip = false;
                                    for (int i = 0; i < multi_tripID.length(); i++) {
                                        if (SessionSave.getSession("trip_id", ContinousRequest.this).equals(multi_tripID.getString(i)))
                                            intrip = true;

                                        arrayString.append(multi_tripID.getString(i)).append(i != multi_tripID.length() - 1 ? "," : "");
                                    }
                                    if (!intrip)
                                        SessionSave.saveSession("trip_id", multi_tripID.getString(0), ContinousRequest.this);
                                    SessionSave.saveSession("multi_tripID", arrayString.toString(), ContinousRequest.this);
                                }
                                if (jsonObject.has("multi_trips")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("multi_trips");
                                    for (int n = 0; n < jsonArray.length(); n++) {
                                        JSONObject json = jsonArray.getJSONObject(n);
                                        handleResponse(json);
                                    }
                                }
                            } else if (jsonObject.getInt("status") == -1) {
                                ShowToast(ContinousRequest.this, jsonObject.getString("message"));
                                clearIdAndRedirect();
                            }
                        } else {
                            if (getPassengerUpdateHandler != null) {
                                getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
                                getPassengerUpdateHandler.postDelayed(getPassengerUpdateRunnable, interval);
                            }
                        }
                    } else {
                        if (getPassengerUpdateHandler != null) {
                            getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
                            getPassengerUpdateHandler.postDelayed(getPassengerUpdateRunnable, interval);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (getPassengerUpdateHandler != null) {
                        getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
                        getPassengerUpdateHandler.postDelayed(getPassengerUpdateRunnable, interval);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                if (getPassengerUpdateHandler != null) {
                    getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
                    getPassengerUpdateHandler.postDelayed(getPassengerUpdateRunnable, interval);
                }
            }
        }));
    }

    private void startLineProgress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivLine.setVisibility(View.VISIBLE);
            ivLine.setBackground(getDrawable(R.drawable.progress_line_anim));
            animatedVectorDrawable = (AnimatedVectorDrawable) ivLine.getBackground();
            repeatAnimation();
        } else {
            ivLine.setVisibility(View.GONE);
        }
    }


    private void stopLineProgress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (animatedVectorDrawable != null) {
                animatedVectorDrawable.stop();
                ivLine.setVisibility(View.GONE);
            }
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            repeatAnimation();
        }
    };

    private void repeatAnimation() {
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.start();
        }
        ivLine.postDelayed(runnable, 1000);
    }


    public void clearIdAndRedirect() {
        if (getPassengerUpdateHandler != null)
            getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
        SessionSave.saveSession(REQ_TRIP_ID, "", ContinousRequest.this);
        finish();
    }

    @Override
    public void onSuccess(Dialog dialog, String resultcode) {
        if (resultcode.equals("No_fav_avail")) {
            if (!(json1.equals("") || url.equals(""))) {
                JSONObject jj = null;
                try {
                    jj = new JSONObject(json1);
                    jj.remove("fav_driver_booking_type");
                    jj.put("fav_driver_booking_type", "0");
                } catch (JSONException e) {
                    SessionSave.saveSession(REQ_TRIP_ID, "", ContinousRequest.this);
                    finish();
                    e.printStackTrace();
                }

                new SearchTaxi(url, jj);
            }
        }
        if (dialog != null)
            dialog.dismiss();
    }

    @Override
    public void onFailure(Dialog dialog, String resultcode) {
        if (dialog != null) {
            dialog.dismiss();
        }
        Intent intent = new Intent(getApplicationContext(), ContinousRequest.class);
        getApplicationContext().stopService(intent);
        finish();
        clearIdAndRedirect();
    }

    @Override
    protected void onDestroy() {
        if (dialog != null)
            Utility.closeDialog(dialog);
        if (countDownTimer1 != null) {
            countDownTimer1.cancel();
            countDownTimer1 = null;
        }
        if (getPassengerUpdateHandler != null)
            getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
        if (getPassengerUpdateCall != null)
            getPassengerUpdateCall.cancel();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (Cancel != null) {
            Cancel.performClick();
        } else {
            SessionSave.saveSession(REQ_TRIP_ID, "", ContinousRequest.this);
            final Intent i = new Intent();
            setResult(Activity.RESULT_OK, i);
            finish();
        }
        super.onBackPressed();

    }

    /**
     * 1	=> Trip ID & driver details request_confirmed_passenger
     * 7	=> trip_cancel
     * 4    => fav_driver_not_available
     * 2	=> driver_busy
     * 6	=> trip_not_started
     * -1    => invalid_trip
     *
     * @param json - Json response
     */
    private void handleResponse(JSONObject json) {
        try {
            int status = json.getInt("status");
            String message = json.getString("message");
            if (status == 1) {
                countDownTimer1.cancel();
                if (getPassengerUpdateHandler != null)
                    getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
                SessionSave.saveSession("trip_id", json.getJSONObject("detail").getString("trip_id"), ContinousRequest.this);
                SessionSave.saveSession(TaxiUtil.PASSENGER_TRIP_TIME, "" + System.currentTimeMillis(), ContinousRequest.this);
                Intent intent = new Intent(ContinousRequest.this, GetPassengerUpdate.class);
                ContinousRequest.this.startService(intent);
                final Intent i = new Intent(ContinousRequest.this, MainHomeFragmentActivity.class);
                Bundle extras = new Bundle();
                extras.putString("alert_message", "" + message);
                i.putExtras(extras);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            } else if (status == 6) {
                if (getPassengerUpdateHandler != null) {
                    getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
                    getPassengerUpdateHandler.postDelayed(getPassengerUpdateRunnable, interval);
                }
            } else if (json.getInt("status") == 2 || json.getInt("status") == 7) {
                if (toaststatus == 0) {
                    toaststatus = 1;
                }
                SessionSave.saveSession(REQ_TRIP_ID, "", ContinousRequest.this);
                countDownTimer1.cancel();
                if (getPassengerUpdateHandler != null)
                    getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
                final Intent i = new Intent();
                Bundle extras = new Bundle();
                extras.putString("alert_message", "" + message);
                i.putExtras(extras);
                setResult(Activity.RESULT_OK, i);
                finish();
            } else if (json.getInt("status") == 4) {
                if (getPassengerUpdateHandler != null)
                    getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
                dialog = Utility.alert_view_dialog(ContinousRequest.this,
                        "",
                        "" + message, "" + NC.getString(R.string.ok),
                        "" + NC.getString(R.string.no_thanks),
                        false, (dialog, which) -> {
                            if (!(json1.equals("") || url.equals(""))) {
                                JSONObject jj = null;
                                try {
                                    jj = new JSONObject(json1);
                                    jj.remove("fav_driver_booking_type");
                                    jj.put("fav_driver_booking_type", "0");
                                } catch (JSONException e) {
                                    SessionSave.saveSession(REQ_TRIP_ID, "", ContinousRequest.this);
                                    finish();
                                    e.printStackTrace();
                                }

                                new SearchTaxi(url, jj);

                            }//else if(resultcode.equals())
                            if (dialog != null)
                                dialog.dismiss();
                        }, (dialog, which) -> {
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                            Intent intent = new Intent(getApplicationContext(), ContinousRequest.class);
                            getApplicationContext().stopService(intent);
                            clearIdAndRedirect();

                        }, "");


            } else if (status == -1) {
                runOnUiThread(() -> ShowToast(ContinousRequest.this, message));
                clearIdAndRedirect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (getPassengerUpdateHandler != null) {
                getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
                getPassengerUpdateHandler.postDelayed(getPassengerUpdateRunnable, interval);
            }
        }
    }

    public class Getpassenger implements APIResult {
        Getpassenger(String url, JSONObject j) {
            new APIService_Retrofit_JSON(ContinousRequest.this, this, j, false, 3000).execute(url);
        }

        @Override
        public void getResult(boolean isSuccess, String result) {
            if (isSuccess) {
                if (!result.equalsIgnoreCase("")) {
                    try {
                        final JSONObject json = new JSONObject(result);
                        if (json.getInt("status") == 3) {
                            if (countDownTimer1 != null)
                                countDownTimer1.cancel();
                            if (getPassengerUpdateHandler != null)
                                getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);
                            if (toaststatus == 0) {
                                toaststatus = 1;
                            }
                            clearIdAndRedirect();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        clearIdAndRedirect();
                    }
                }
            } else {
                clearIdAndRedirect();
            }
        }
    }

    /**
     * continuous request cancel response handling.
     */

    private class CancelTrip implements APIResult {
        CancelTrip(final String string, JSONObject data) {
            new APIService_Retrofit_JSON_NoProgress(ContinousRequest.this, this, data, false).execute(string);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            if (isSuccess) {
                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 3) {
                        if (getPassengerUpdateHandler != null)
                            getPassengerUpdateHandler.removeCallbacks(getPassengerUpdateRunnable);

                        countDownTimer1.cancel();
                        if (toaststatus == 0) {
                            toaststatus = 1;
                        }
                        SessionSave.saveSession(REQ_TRIP_ID, "", ContinousRequest.this);
                        final Intent i = new Intent();
                        Bundle extras = new Bundle();
                        extras.putString("alert_message", "" + json.getString("message"));
                        i.putExtras(extras);
                        setResult(Activity.RESULT_OK, i);
                        finish();
                    }
                } catch (final Exception e) {
                    clearIdAndRedirect();
                }
            } else {
                runOnUiThread(() -> ShowToast(ContinousRequest.this, NC.getString(R.string.server_con_error)));
                clearIdAndRedirect();
            }
        }
    }

    /**
     * this class is used to search taxi
     *
     * @author developer
     */
    public class SearchTaxi implements APIResult {
        String url = "";

        /**
         * this method is used to call SearchTaxi api
         *
         * @param url  passing url as a parameter
         * @param data passing jsonobject as parameter
         *             <p/>
         *             this method is invoked while calling this method with these params
         */
        SearchTaxi(final String url, JSONObject data) {

            new APIService_Retrofit_JSON(ContinousRequest.this, this, data, false, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession("Lang", ContinousRequest.this) + "&" + url).execute();
            this.url = url;
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            if (isSuccess) {
                try {

                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        SessionSave.saveSession(REQ_TRIP_ID, json.getJSONObject("detail").getString("passenger_tripid"), ContinousRequest.this);
                        SessionSave.saveSession(PASS_TRIP_ID, json.getJSONObject("detail").getString("passenger_tripid"), ContinousRequest.this);
                        SessionSave.saveSession(REQUEST_TIME, json.getJSONObject("detail").getString("total_request_time"), ContinousRequest.this);
                        SessionSave.saveSession(CREDIT_CARD, "" + json.getJSONObject("detail").getString("credit_card_status"), ContinousRequest.this);

                        if (countDownTimer1 != null) {
                            countDownTimer1.cancel();
                            countDownTimer1 = null;
                        }
                        final Intent i = new Intent(ContinousRequest.this, ContinousRequest.class);
                        i.putExtra("url", url);
                        i.putExtra("json", result);
                        startActivity(i);
                        finish();
                    } else if (json.getInt("status") == 2 || json.getInt("status") == 5 || json.getInt("status") == 6 || json.getInt("status") == 3) {
                        final Intent i = new Intent();
                        Bundle extras = new Bundle();
                        extras.putString("alert_message", "" + json.getString("message"));
                        i.putExtras(extras);
                        setResult(Activity.RESULT_OK, i);
                        finish();
                    }

                } catch (final Exception e) {
                    SessionSave.saveSession(REQ_TRIP_ID, "", ContinousRequest.this);
                    finish();
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(() -> ShowToast(ContinousRequest.this, NC.getString(R.string.server_con_error)));
                SessionSave.saveSession(REQ_TRIP_ID, "", ContinousRequest.this);
                finish();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLineProgress();
    }
}