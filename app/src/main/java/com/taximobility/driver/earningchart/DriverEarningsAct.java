package com.taximobility.driver.earningchart;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.squareup.picasso.Picasso;
import com.taximobility.R;
import com.taximobility.driver.DriverBaseActivity;
import com.taximobility.driver.DriverMeAct;
import com.taximobility.driver.DriverMyStatus;
import com.taximobility.driver.DriverStreetPickUpAct;
import com.taximobility.driver.DriverTripHistoryAct;
import com.taximobility.driver.DriverWalletHistory;
import com.taximobility.driver.DriverWebviewAct;
import com.taximobility.driver.DriverWithDrawMenuAct;
import com.taximobility.driver.SettlementHistoryActivityDriver;
import com.taximobility.driver.data.DriverCommonData;
import com.taximobility.driver.data.apiData.DriverApiRequestData;
import com.taximobility.driver.interfaces.DriverAPIResult;
import com.taximobility.driver.interfaces.DriverClickInterface;
import com.taximobility.driver.service.DriverAPIService_Retrofit_JSON;
import com.taximobility.driver.service.DriverCoreClient;
import com.taximobility.driver.service.DriverNonActivity;
import com.taximobility.driver.service.DriverRetrofitCallbackClass;
import com.taximobility.driver.service.DriverServiceGenerator;
import com.taximobility.driver.utils.DirverColorchange;
import com.taximobility.driver.utils.DriverCL;
import com.taximobility.driver.utils.DriverCToast;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverNetworkStatus;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.driver.utils.DriverSystems;
import com.taximobility.driver.utils.Driver_Utils;
import com.taximobility.driver.utils.drawable_program.Drawables_program;
import com.taximobility.driver.interfaces.AlertListener;
import com.taximobility.util.AppController;
import com.taximobility.util.Utility;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.FullscreenPromptBackground;

/**
 * This class is used to show driver earning details based on date
 */
public class DriverEarningsAct extends DriverBaseActivity implements DriverClickInterface {

    private final int REQUEST_READ_PHONE_STATE = 292;
    BarChart mChart;
    ArrayList<BarEntry> yVals1;
    DriverEarningresponse data;
    TextView trips, eAmt, weekAmt, tripHist, wek_txt;
    String checked = "OUT";
    DriverNonActivity nonactiityobj = new DriverNonActivity();
    private LinearLayout date;
    private LinearLayout home_lay, earnings_lay, profile_lay, streetpick_lay;
    private ImageView earnings_iv;
    private TextView btn_shift;
    TextView slideImg;
    private RelativeLayout triphistory_lay;
    private ScrollView earnings_layout;
    private DriverNetworkStatus networkStatus;
    private Dialog errorDialog;
    private AppCompatButton btn_emergency;
    private TextView btn_withdraw, btn_settlement, txt_recharge_link, wallet_amount, btnWithdrawHistory, trip_hours;
    private MaterialTapTargetSequence mTapTarget;
    private Dialog mDialog;
    private int pos = 0, total_weeks = 0;
    private ImageView img_left, img_right;
    private TextView tv_this_week, tv_this_week_amt, tv_total_trip, tv_total_distance;
    private Dialog dialog1;
    private LinearLayout layout_earnings_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_lay);
        DriverNetworkStatus.appContext = this;
        networkStatus = new DriverNetworkStatus();
        registerReceiver(networkStatus, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        btn_shift = findViewById(R.id.btn_shift);
        slideImg = findViewById(R.id.slideImg);
        initalize();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(networkStatus);
        if (mTapTarget != null) {
            mTapTarget.dismiss();
        }
        if (dialog1 != null) Driver_Utils.closeDialog(dialog1);
        closeDialog();
        super.onDestroy();
    }

    public void initalize() {
        slideImg.setOnClickListener(view -> onBackPressed());
        btn_emergency = findViewById(R.id.btn_emergency);

        btn_emergency.setOnClickListener(view -> Utility.actionSheet(DriverEarningsAct.this, DriverNC.getResources().getString(R.string.send_emergency_alert), DriverNC.getResources().getString(R.string.yes), DriverNC.getResources().getString(R.string.no), false, new AlertListener() {
            @Override
            public void onSuccess() {
                startSOSService();
            }

            @Override
            public void onFailure() {

            }
        }));

        layout_earnings_items = findViewById(R.id.layout_earnings_items);
        mChart = findViewById(R.id.chart1);
        date = findViewById(R.id.dates);
        trips = findViewById(R.id.trips);
        eAmt = findViewById(R.id.amt);
        wek_txt = findViewById(R.id.wek_txt);
        earnings_layout = findViewById(R.id.earnings_layout);
        earnings_iv = findViewById(R.id.earnings_iv);
        earnings_iv.setImageResource(R.drawable.ic_earnings_focus);
        home_lay = findViewById(R.id.home_lay);
        earnings_lay = findViewById(R.id.earnings_lay);
        profile_lay = findViewById(R.id.profile_lay);
        streetpick_lay = findViewById(R.id.streetpick_lay);
        triphistory_lay = findViewById(R.id.triphistory_lay);
        weekAmt = findViewById(R.id.week_amt);
        tripHist = findViewById(R.id.trip_history);
        btn_withdraw = findViewById(R.id.btn_withdraw);
        btn_settlement = findViewById(R.id.btn_settlement);
        txt_recharge_link = findViewById(R.id.txt_recharge_link);
        wallet_amount = findViewById(R.id.wallet_amount);
        btnWithdrawHistory = findViewById(R.id.btn_withdraw_history);
        trip_hours = findViewById(R.id.trip_hours);
        img_left = findViewById(R.id.img_left);
        img_right = findViewById(R.id.img_right);
        tv_this_week = findViewById(R.id.tv_this_week);
        tv_this_week_amt = findViewById(R.id.tv_this_week_amt);
        tv_total_trip = findViewById(R.id.tv_total_trip);
        tv_total_distance = findViewById(R.id.tv_total_distance);
        btn_withdraw.setOnClickListener(v -> {
            Intent in = new Intent(DriverEarningsAct.this, DriverWithDrawMenuAct.class);
            startActivity(in);
        });

        btn_settlement.setOnClickListener(view -> startActivity(new Intent(DriverEarningsAct.this, SettlementHistoryActivityDriver.class)));

        txt_recharge_link.setOnClickListener(v -> {
            Intent in = new Intent(DriverEarningsAct.this, DriverWebviewAct.class);
            in.putExtra("type", "1");
            in.putExtra(DriverCommonData.IS_FROM_EARNINGS, true);
            startActivity(in);
        });

        btnWithdrawHistory.setOnClickListener(v -> {
            Intent in = new Intent(DriverEarningsAct.this, DriverWalletHistory.class);
            startActivity(in);
        });

        img_left.setOnClickListener(view -> {
            if (pos > 0) {
                pos = pos - 1;
                tv_this_week.setText(data.weekly_earnings.get(pos).date_text);
                tv_this_week_amt.setText(DriverSessionSave.getSession("site_currency", DriverEarningsAct.this) + " " + data.weekly_earnings.get(pos).this_week_earnings);
                setmChart(pos);
            }
        });

        img_right.setOnClickListener(view -> {
            if (pos < total_weeks - 1) {
                pos = pos + 1;
                tv_this_week.setText(data.weekly_earnings.get(pos).date_text);
                tv_this_week_amt.setText(DriverSessionSave.getSession("site_currency", DriverEarningsAct.this) + " " + data.weekly_earnings.get(pos).this_week_earnings);
                setmChart(pos);
            }
        });

        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverEarningsAct.this.findViewById(android.R.id.content)).getChildAt(0)), DriverEarningsAct.this);
        ImageView headerlogo = findViewById(R.id.headicon);
        Picasso.get().load(DriverSessionSave.getSession("image_path", this) + "headerLogo_driver.png").into((ImageView) findViewById(R.id.headicon));
        headerlogo.setVisibility(View.GONE);
        mChart.setVisibility(View.INVISIBLE);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        mChart.setMaxVisibleValueCount(60);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.setDrawValueAboveBar(true);
        DriverWeekaxisformatter xAxisFormatter = new DriverWeekaxisformatter();
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setDrawAxisLine(false);
        IAxisValueFormatter custom = new DriverMyAxisValueFormatter(DriverSessionSave.getSession("site_currency", DriverEarningsAct.this));
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(25f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setGranularityEnabled(false);
        rightAxis.setGranularity(0.1f);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);

      /*  DriverXYMarkerView mv = new DriverXYMarkerView(this, xAxisFormatter, DriverSessionSave.getSession("site_currency", DriverEarningsAct.this));
        mv.setChartView(mChart);
        mChart.setMarker(mv);*/
        mChart.getLegend().setEnabled(false);
        setData();

        //Setting driver shift status
        if (DriverSessionSave.getSession("shift_status", DriverEarningsAct.this).equals("IN")) {
            Drawables_program.shift_on(btn_shift);
            btn_shift.setText(DriverNC.getString(R.string.online));
            if (!DriverSessionSave.getSession("driver_type", DriverEarningsAct.this).equals("D"))
                nonactiityobj.startServicefromNonActivity(DriverEarningsAct.this);
        } else {
            Drawables_program.shift_bg_grey(btn_shift);
            btn_shift.setText(DriverNC.getString(R.string.offline));
            nonactiityobj.stopServicefromNonActivity(DriverEarningsAct.this);
        }

        earnings_lay.setOnClickListener(view -> {
            //Intent intent = new Intent(EarningsAct.this, EarningsAct.class);
            // startActivity(intent);
            //finish();
        });

        home_lay.setOnClickListener(view -> {
            Intent intent = new Intent(DriverEarningsAct.this, DriverMyStatus.class);
            startActivity(intent);
            // finish();
        });

        profile_lay.setOnClickListener(view -> {
            Intent intent = new Intent(DriverEarningsAct.this, DriverMeAct.class);
            startActivity(intent);
            // finish();
        });

        streetpick_lay.setOnClickListener(view -> {
            if (!DriverSessionSave.getSession("driver_type", DriverEarningsAct.this).equalsIgnoreCase("D")) {
                if (DriverSessionSave.getSession("trip_id", DriverEarningsAct.this).equals("")) {
                    Intent intent = new Intent(DriverEarningsAct.this, DriverStreetPickUpAct.class);
                    startActivity(intent);
                } else if (!DriverSessionSave.getSession("trip_id", DriverEarningsAct.this).equals("") && DriverSessionSave.getSession(DriverCommonData.IS_STREET_PICKUP, DriverEarningsAct.this, false)) {
                    Intent intent = new Intent(DriverEarningsAct.this, DriverStreetPickUpAct.class);
                    startActivity(intent);
                } else {
//                        showStreetAlert(DriverNC.getString(R.string.you_are_in_trip));
                    DriverCToast.ShowToast(DriverEarningsAct.this, DriverNC.getString(R.string.you_are_in_trip));
                }
            } else {
                DriverCToast.ShowToast(DriverEarningsAct.this, DriverSessionSave.getSession("account_message", DriverEarningsAct.this));
            }
        });

        btn_shift.setOnClickListener(v -> {
            btn_shift.setClickable(false);
            new RequestingCheckBox();
        });

        triphistory_lay.setOnClickListener(v -> {
            Intent intent = new Intent(DriverEarningsAct.this, DriverTripHistoryAct.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //setting driver shift update
       /* if (DriverSessionSave.getSession("shift_status", DriverEarningsAct.this).equals("IN")) {
            Drawables_program.shift_on(btn_shift);
            btn_shift.setText(DriverNC.getString(R.string.online));
            DriverSessionSave.saveSession(DriverCommonData.SHIFT_OUT, false, DriverEarningsAct.this);
            if (!DriverSessionSave.getSession("driver_type", DriverEarningsAct.this).equals("D"))
                nonactiityobj.startServicefromNonActivity(DriverEarningsAct.this);

        } else {
            Drawables_program.shift_bg_grey(btn_shift);
            btn_shift.setText(DriverNC.getString(R.string.offline));
            nonactiityobj.stopServicefromNonActivity(DriverEarningsAct.this);
        }*/
    }

    /**
     * Getting Earnings detail API call and response parsing.
     */
    private void setData() {
        showLoading();
        yVals1 = new ArrayList<>();

        DriverCoreClient client = AppController.getInstance().getApiManagerWithEncryptBaseUrl_driver();
        DriverApiRequestData.Earnings request = new DriverApiRequestData.Earnings();
        request.setDriver_id(DriverSessionSave.getSession("Id", DriverEarningsAct.this));
        Call<DriverEarningresponse> response = client.callData(DriverServiceGenerator.COMPANY_KEY, request);
        response.enqueue(new DriverRetrofitCallbackClass<>(DriverEarningsAct.this, new Callback<DriverEarningresponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<DriverEarningresponse> call, @NonNull Response<DriverEarningresponse> response) {
                closeLoading();

                if (response.isSuccessful() && DriverEarningsAct.this != null) {
                    data = response.body();
                    DriverSystems.out.println("***__" + data);
                    if (data != null) {
                        if (data.status == 1) {

                            if (data.withdraw_array != null && data.withdraw_array.size() != 0) {
                                if (data.withdraw_array.get(0).driver_wallet_amount != null)
                                    DriverSessionSave.saveSession("driver_wallet_amount", "" + data.withdraw_array.get(0).driver_wallet_amount, DriverEarningsAct.this);
                                if (data.withdraw_array.get(0).driver_wallet_pending_amount != null)
                                    DriverSessionSave.saveSession("driver_wallet_pending_amount", data.withdraw_array.get(0).driver_wallet_pending_amount, DriverEarningsAct.this);
                                if (data.withdraw_array.get(0).trip_amount != null)
                                    DriverSessionSave.saveSession("trip_amount", data.withdraw_array.get(0).trip_amount, DriverEarningsAct.this);
                                if (data.withdraw_array.get(0).trip_pending_amount != null)
                                    DriverSessionSave.saveSession("trip_pending_amount", data.withdraw_array.get(0).trip_pending_amount, DriverEarningsAct.this);
                                if (data.withdraw_array.get(0).total_amount != null)
                                    DriverSessionSave.saveSession("total_amount", data.withdraw_array.get(0).total_amount, DriverEarningsAct.this);
                                if (data.withdraw_array.get(0).driver_trip_wallet_amount != null)
                                    wallet_amount.setText(DriverSessionSave.getSession("site_currency", DriverEarningsAct.this) + " " + data.withdraw_array.get(0).driver_trip_wallet_amount);
                            }

                            earnings_layout.setVisibility(View.VISIBLE);
                            // date.removeAllViews();

                            //showTapTargetPrompt();
                            total_weeks = data.weekly_earnings.size();
                            for (int i = 0; i < data.weekly_earnings.size(); i++) {
                                /*final TextView et = new TextView(DriverEarningsAct.this);
                                et.setText(data.weekly_earnings.get(i).date_text + "\n" + DriverSessionSave.getSession("site_currency", DriverEarningsAct.this) + " " + data.weekly_earnings.get(i).this_week_earnings);
                                et.setClickable(true);
                                et.setId(i);
                                et.setPadding(15, 15, 10, 10);
                                et.setTag(i);
                                if (i == 0) {
                                    pos = i;
                                    et.setTextColor(DriverCL.getColor(R.color.black));
                                    setmChart(0);

                                }*/

                                if (i == 0) {
                                    pos = i;
                                    tv_this_week.setText(data.weekly_earnings.get(i).date_text);
                                    tv_this_week_amt.setText(DriverSessionSave.getSession("site_currency", DriverEarningsAct.this) + " " + data.weekly_earnings.get(i).this_week_earnings);
                                    setmChart(0);
                                }

                               /* et.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        int i = (int) view.getTag();
                                        pos = i;
                                        if (i == 0) {
                                            wek_txt.setText(DriverNC.getResources().getString(R.string.Week));
                                        } else {
                                            wek_txt.setText(DriverNC.getResources().getString(R.string.Selected_Week));
                                        }
                                        mChart.notifyDataSetChanged();
                                        weekAmt.setText("" + DriverSessionSave.getSession("site_currency", DriverEarningsAct.this) + " " + data.weekly_earnings.get(i).this_week_earnings);
                                        for (int j = 0; j < date.getChildCount(); j++) {
                                            TextView v = (TextView) date.getChildAt(j);
                                            v.setTextColor(Color.GRAY);
                                        }
                                        ((TextView) view).setTextColor(DriverCL.getColor(R.color.black));
                                        ((TextView) view).setText(data.weekly_earnings.get(i).date_text + "\n" + DriverSessionSave.getSession("site_currency", DriverEarningsAct.this) + " " + data.weekly_earnings.get(i).this_week_earnings);
                                        date.invalidate();
                                        setmChart(i);
                                    }
                                });

                                date.addView(et);*/

                                try {
                                    //if(data.)
                                    tv_total_trip.setText(data.total_earnings.get(0).total_trips);
                                    tv_total_distance.setText(data.total_earnings.get(0).total_distance);
                                    trips.setText(data.today_earnings.get(0).total_trips + " " + DriverNC.getResources().getString(R.string.trips1));
                                    eAmt.setText("" + DriverSessionSave.getSession("site_currency", DriverEarningsAct.this) + "" + data.today_earnings.get(0).total_amount);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (data.weekly_earnings.get(0).this_week_earnings != null)
                                    weekAmt.setText("" + DriverSessionSave.getSession("site_currency", DriverEarningsAct.this) + " " + data.weekly_earnings.get(0).this_week_earnings);
                                else
                                    weekAmt.setText("" + DriverSessionSave.getSession("site_currency", DriverEarningsAct.this) + " 0");
                            }
                        } else if (data.status == -1) {
                            DriverCToast.ShowToast(DriverEarningsAct.this, data.message);
                        } else {
                            DriverCToast.ShowToast(DriverEarningsAct.this, DriverNC.getString(R.string.server_error));
                        }
                    } else {
                        DriverCToast.ShowToast(DriverEarningsAct.this, DriverNC.getString(R.string.server_error));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DriverEarningresponse> call, @NonNull Throwable t) {
                closeLoading();
                t.printStackTrace();
            }
        }));
    }

    private void closeLoading() {
        //layout_earnings_items.setVisibility(View.VISIBLE);
        closeDialog();
    }

    private void showLoading() {
        //layout_earnings_items.setVisibility(View.GONE);
        showDialog();
    }

    private void showTapTargetPrompt() {
        if (!DriverSessionSave.getSession(DriverCommonData.SHOW_TOOLTIP, DriverEarningsAct.this, false) && txt_recharge_link != null) {
            txt_recharge_link.post(() -> {
                mTapTarget = new MaterialTapTargetSequence().addPrompt(new MaterialTapTargetPrompt.Builder(DriverEarningsAct.this).setTarget(txt_recharge_link).setAnimationInterpolator(new LinearOutSlowInInterpolator()).setFocalColour(DriverCL.getResources().getColor(R.color.focal_white)).setFocalRadius(Float.parseFloat(Integer.toString(txt_recharge_link.getHeight() + wallet_amount.getHeight()))).setBackgroundColour(DriverCL.getResources().getColor(R.color.tooltip_background)).setPrimaryText(DriverNC.getString(R.string.wallet_tooltip_text)).setPrimaryTextColour(DriverCL.getResources().getColor(R.color.white)).setSecondaryText(DriverNC.getString(R.string.next)).setSecondaryTextColour(DriverCL.getResources().getColor(R.color.pastbookingcashtext)).setPromptBackground(new FullscreenPromptBackground()).create(), 4000).addPrompt(new MaterialTapTargetPrompt.Builder(DriverEarningsAct.this).setTarget(btn_withdraw).setAnimationInterpolator(new LinearOutSlowInInterpolator()).setBackgroundColour(DriverCL.getResources().getColor(R.color.tooltip_background)).setPrimaryText(DriverNC.getString(R.string.withdraw_tooltip_text)).setSecondaryText(DriverNC.getString(R.string.next)).setPrimaryTextColour(DriverCL.getResources().getColor(R.color.white)).setSecondaryTextColour(DriverCL.getResources().getColor(R.color.pastbookingcashtext)).setFocalPadding(R.dimen.sp_20).setPromptBackground(new FullscreenPromptBackground()).create(), 4000).addPrompt(new MaterialTapTargetPrompt.Builder(DriverEarningsAct.this).setTarget(btn_settlement).setAnimationInterpolator(new LinearOutSlowInInterpolator()).setBackgroundColour(DriverCL.getResources().getColor(R.color.tooltip_background)).setPrimaryText(DriverNC.getString(R.string.settlement_tooltip_text)).setSecondaryText(DriverNC.getString(R.string.ok)).setPrimaryTextColour(DriverCL.getResources().getColor(R.color.white)).setSecondaryTextColour(DriverCL.getResources().getColor(R.color.pastbookingcashtext)).setFocalPadding(R.dimen.sp_20).setPromptBackground(new FullscreenPromptBackground()).create(), 4000).show();
                DriverSessionSave.saveSession(DriverCommonData.SHOW_TOOLTIP, true, DriverEarningsAct.this);
            });
        }
    }

    /**
     * Setting Barchart values
     */
    void setmChart(int x) {

        try {
            mChart.setVisibility(View.VISIBLE);
            yVals1.clear();
            for (int i = 0; i < data.weekly_earnings.get(x).trip_amount.size(); i++) {
                yVals1.add(new BarEntry(i, Float.parseFloat(data.weekly_earnings.get(x).trip_amount.get(i).toString())));
            }
            DriverWeekaxisformatter.mMonths = data.weekly_earnings.get(x).day_list;
            mChart.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BarDataSet set1;
        set1 = new BarDataSet(yVals1, "");
        set1.setDrawValues(true);
        set1.setColor(getResources().getColor(R.color.linebottom_light));
        ArrayList<IBarDataSet> dataSets;
        dataSets = new ArrayList<>();
        dataSets.add(set1);
        BarData datas = new BarData(dataSets);
        datas.setValueFormatter(new DriverMyAxisValueFormatter(DriverSessionSave.getSession("site_currency", DriverEarningsAct.this) + " "));
        datas.setValueTextSize(10f);
        datas.setBarWidth(0.5f);
        mChart.invalidate();
        mChart.setData(null);
        mChart.setData(datas);
        mChart.setFitBars(false);
        mChart.invalidate();
    }

    @Override
    public void positiveButtonClick(DialogInterface dialog, int id, String s) {
        dialog.dismiss();
    }

    @Override
    public void negativeButtonClick(DialogInterface dialog, int id, String s) {
        dialog.dismiss();
    }

    /**
     * To check internet connectivity
     */
    public boolean isOnline() {

        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) for (NetworkInfo networkInfo : info)
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
        }
        return false;
    }
/*
    public void showStreetAlert(String message) {
        try {
            if (DriverEarningsAct.this != null) {
                if (errorDialog != null && errorDialog.isShowing())
                    errorDialog.dismiss();
                DriverSystems.out.println("setCanceledOnTouchOutside" + message);
                final View view = View.inflate(DriverEarningsAct.this, R.layout.driver_netcon_lay, null);
                errorDialog = new Dialog(DriverEarningsAct.this, R.style.dialogwinddow);
                errorDialog.setContentView(view);
                errorDialog.setCancelable(false);
                errorDialog.setCanceledOnTouchOutside(false);
                DriverFontHelper.applyFont(DriverEarningsAct.this, errorDialog.findViewById(R.id.alert_id));
                errorDialog.show();
                final TextView title_text = errorDialog.findViewById(R.id.title_text);
                final TextView message_text = errorDialog.findViewById(R.id.message_text);
                final Button button_success = errorDialog.findViewById(R.id.button_success);
                final Button button_failure = errorDialog.findViewById(R.id.button_failure);
                title_text.setText("" + DriverNC.getResources().getString(R.string.message));
                message_text.setText("" + message);
                button_success.setText("" + DriverNC.getResources().getString(R.string.track_now));
                button_failure.setText("" + DriverNC.getResources().getString(R.string.cancel));
                button_success.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        // TODO Auto-generated method stub
                        errorDialog.dismiss();
                        Intent i = new Intent(DriverEarningsAct.this, DriverOngoingAct.class);
                        startActivity(i);
                    }
                });
                button_failure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        // TODO Auto-generated method stub
                        errorDialog.dismiss();
                    }
                });
            } else {
                try {
                    if (DriverEarningsAct.this != null && errorDialog != null)
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
 */

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DriverEarningsAct.this, DriverMyStatus.class);
        startActivity(intent);
        finish();
    }

    private void startSOSService() {
        DriverSessionSave.saveSession("sos_id", DriverSessionSave.getSession("Id", DriverEarningsAct.this), DriverEarningsAct.this);
        DriverSessionSave.saveSession("user_type", "d", DriverEarningsAct.this);
        //    startService(new Intent(EarningsAct.this, SOSService.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_PHONE_STATE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //             startSOSService();
                }
            }
        }
    }

    /**
     * Driver Shift API call and response parsing.
     */

    private class RequestingCheckBox implements DriverAPIResult {
        public RequestingCheckBox() {

            try {
                if (btn_shift.getText().toString().equals(DriverNC.getString(R.string.online)))
                    checked = "OUT";
                else checked = "IN";
                JSONObject j = new JSONObject();
                j.put("driver_id", DriverSessionSave.getSession("Id", DriverEarningsAct.this));
                j.put("shiftstatus", checked);
                j.put("reason", "");
                Log.e("shiftbefore ", j.toString());
                j.put("update_id", DriverSessionSave.getSession("Shiftupdate_Id", DriverEarningsAct.this));

                String requestingCheckBox = "type=driver_shift_status";
                if (isOnline())
                    new DriverAPIService_Retrofit_JSON(DriverEarningsAct.this, this, j, false).execute(requestingCheckBox);
                else {
                    btn_shift.setClickable(true);
                    DriverCToast.ShowToast(DriverEarningsAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverEarningsAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverEarningsAct.this, "");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            try {
                Log.e("driverstatus", result);
                if (isSuccess && DriverEarningsAct.this != null) {
                    btn_shift.setClickable(true);

                    JSONObject object = new JSONObject(result);
                    if (object.getInt("status") == 1) {
                        if (checked.equals("IN")) {

                            DriverCToast.ShowToast(DriverEarningsAct.this, "" + object.getString("message"));

//                            dialog1 = Driver_Utils.alert_view(DriverEarningsAct.this, "", "" + object.getString("message"), DriverNC.getResources().getString(R.string.ok),
//                                    "", true, DriverEarningsAct.this, "");

                            btn_shift.setText(DriverNC.getString(R.string.online));
                            Drawables_program.shift_on(btn_shift);
                            DriverSessionSave.saveSession("shift_status", "IN", DriverEarningsAct.this);
                            DriverSessionSave.saveSession("Shiftupdate_Id", object.getJSONObject("detail").getString("update_id"), DriverEarningsAct.this);
                            Log.e("sess", DriverSessionSave.getSession("shift_status", DriverEarningsAct.this));
                            DriverSessionSave.saveSession(DriverCommonData.SHIFT_OUT, false, DriverEarningsAct.this);
                            if (!DriverSessionSave.getSession("driver_type", DriverEarningsAct.this).equals("D"))
                                nonactiityobj.startServicefromNonActivity(DriverEarningsAct.this);
                        } else {
                            DriverCToast.ShowToast(DriverEarningsAct.this, "" + object.getString("message"));
//                            dialog1 = Driver_Utils.alert_view(DriverEarningsAct.this, "", "" + object.getString("message"), DriverNC.getResources().getString(R.string.ok),
//                                    "", true, DriverEarningsAct.this, "");
                            btn_shift.setText(DriverNC.getString(R.string.offline));
                            Drawables_program.shift_bg_grey(btn_shift);
                            DriverSessionSave.saveSession("shift_status", "OUT", DriverEarningsAct.this);
                            DriverSessionSave.saveSession("trip_id", "", DriverEarningsAct.this);
                            DriverSessionSave.setWaitingTime(0L, DriverEarningsAct.this);
                            nonactiityobj.stopServicefromNonActivity(DriverEarningsAct.this);
                        }
                    } else if (object.getInt("status") == -4) {
                        DriverCToast.ShowToast(DriverEarningsAct.this, "" + object.getString("message"));
//                        dialog1 = Driver_Utils.alert_view(DriverEarningsAct.this, "", "" + object.getString("message"), DriverNC.getResources().getString(R.string.ok),
//                                "", true, DriverEarningsAct.this, "");
                    } else {
                        DriverCToast.ShowToast(DriverEarningsAct.this, "" + object.getString("message"));
//                        dialog1 = Driver_Utils.alert_view(DriverEarningsAct.this, "", "" + object.getString("message"), DriverNC.getResources().getString(R.string.ok),
//                                "", true, DriverEarningsAct.this, "");
                    }
                } else {
                    runOnUiThread(() -> DriverCToast.ShowToast(DriverEarningsAct.this, DriverNC.getString(R.string.please_check_internet)));
                    btn_shift.setClickable(true);
                    if (checked.equals("IN")) {
                        btn_shift.setText(DriverNC.getString(R.string.online));
                        Drawables_program.shift_on(btn_shift);
                    } else {
                        btn_shift.setText(DriverNC.getString(R.string.offline));
                        Drawables_program.shift_bg_grey(btn_shift);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                btn_shift.setClickable(true);
                DriverCToast.ShowToast(DriverEarningsAct.this, "" + DriverNC.getResources().getString(R.string.server_error));
            }
        }
    }

    public void showDialog() {
        try {
            if (DriverNetworkStatus.isOnline(DriverEarningsAct.this)) {
                View view = View.inflate(DriverEarningsAct.this, R.layout.driver_progress_bar, null);
                mDialog = new Dialog(DriverEarningsAct.this, R.style.dialogwinddow);
                mDialog.setContentView(view);
                mDialog.setCancelable(false);
                mDialog.show();
                ImageView iv = mDialog.findViewById(R.id.giff);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                Glide.with(DriverEarningsAct.this).load(R.raw.driver_loading_anim).into(imageViewTarget);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close dialog
     */
    public void closeDialog() {
        try {
            if (mDialog != null) if (mDialog.isShowing()) mDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
