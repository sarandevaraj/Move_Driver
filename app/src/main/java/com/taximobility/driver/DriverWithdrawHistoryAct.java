package com.taximobility.driver;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.taximobility.R;
import com.taximobility.driver.adapter.DriverWithdraw_history_adapter;
import com.taximobility.driver.adapter.DriverWithdraw_referalhistory_adapter;
import com.taximobility.driver.interfaces.DriverAPIResult;
import com.taximobility.driver.interfaces.DriverClickInterface;
import com.taximobility.driver.service.DriverAPIService_Retrofit_JSON;
import com.taximobility.driver.utils.DriverCL;
import com.taximobility.driver.utils.DirverColorchange;
import com.taximobility.driver.utils.DriverFontHelper;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.driver.utils.Driver_Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by developer on 5/10/16.
 */


/**
 * This class is used to view withdraw history for both referal and trip
 */
public class DriverWithdrawHistoryAct extends MainActivityDriver implements DriverClickInterface {


    private ArrayList<HashMap<String, String>> data;
    private ListView list;
    private TextView back_home, btn_referal, btn_trip, btn_back;
    private ImageView btn_filter;

    private TextView txt_view;

    private TextView txt_fromdate, txt_todate;

    Spinner Statusspn;
    private int viewType = 1;

    private int _hour = 0;
    private int _min = 0;
    private int _date = 0;
    private int _month = 0;
    private int _year = 0;
    private String _ampm = "AM";

    private String fromtime = " 00:00";
    private String totime = " 23:59";

    int temp_status = 0;


    private Dialog dt_mDialog;
    private LinearLayout no_data_txt;
    RelativeLayout lay_list;
    private View trip_underline;
    private View referl_underline;

    private Dialog dialog1;

    @Override
    public int setLayout() {
        return R.layout.driver_listview;
    }


    /**
     * Initialize the views on layout
     */
    @Override
    public void Initialize() {
        list = findViewById(R.id.listView);
        back_home = findViewById(R.id.back_home);
        btn_referal = findViewById(R.id.btnreferal);
        btn_trip = findViewById(R.id.btntrip);
        btn_back = findViewById(R.id.slideImg);
        txt_view = findViewById(R.id.view);
        trip_underline = findViewById(R.id.trip_underline);
        referl_underline = findViewById(R.id.referl_underline);
        btn_filter = findViewById(R.id.filter);
        no_data_txt = findViewById(R.id.no_data);
        lay_list = findViewById(R.id.lay_list);


        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverWithdrawHistoryAct.this
                .findViewById(android.R.id.content)).getChildAt(0)), DriverWithdrawHistoryAct.this);

        /*ImageView iv = findViewById(R.id.progress_history);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
        Glide.with(DriverWithdrawHistoryAct.this)
                .load(R.raw.driver_loading_anim)
                .into(imageViewTarget);*/


        btn_filter.setVisibility(View.INVISIBLE);


        btn_referal.setOnClickListener(v -> {

            viewType = 1;
            if (data != null)
                data.clear();
            list.setAdapter(null);
            btn_filter.setVisibility(View.INVISIBLE);
            SetWithdrawList();
        });

        btn_trip.setOnClickListener(v -> {

            viewType = 2;
            if (data != null)
                data.clear();
            list.setAdapter(null);
            btn_filter.setVisibility(View.GONE);
            SetWithdrawList();
        });

        btn_back.setOnClickListener(v -> finish());

        back_home.setOnClickListener(v -> startActivity(new Intent(DriverWithdrawHistoryAct.this, DriverMyStatus.class)));

        btn_filter.setOnClickListener(v -> withDrawFilter());

    }

    /**
     * showing Withdraw filter popup
     */
    public void withDrawFilter() {
        try {

            final View view = View.inflate(DriverWithdrawHistoryAct.this, R.layout.driver_withdrawfilter, null);
            final Dialog mcancelDialog = new Dialog(DriverWithdrawHistoryAct.this, R.style.dialogwinddow);
            mcancelDialog.setContentView(view);
            mcancelDialog.setCancelable(true);
            mcancelDialog.show();
            DirverColorchange.ChangeColor(mcancelDialog.findViewById(R.id.alert_id), DriverWithdrawHistoryAct.this);
            DriverFontHelper.applyFont(DriverWithdrawHistoryAct.this, mcancelDialog.findViewById(R.id.alert_id));

            txt_fromdate = view.findViewById(R.id.fromdateTxt);
            txt_todate = view.findViewById(R.id.todateTxt);
            Statusspn = view.findViewById(R.id.statusspn);


            ArrayAdapter adapter = ArrayAdapter.createFromResource(
                    this, R.array.Status, android.R.layout.simple_spinner_item);

            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            Statusspn.setAdapter(adapter);
            txt_fromdate.setText("yyyy-mm-dd");
            txt_todate.setText("yyyy-mm-dd");

            txt_fromdate.setOnClickListener(v -> Pickdate(1));

            txt_todate.setOnClickListener(v -> Pickdate(2));


            Statusspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    temp_status = parent.getSelectedItemPosition();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });

            final Button button_success = mcancelDialog.findViewById(R.id.okbtn);
            final Button button_cancel = mcancelDialog.findViewById(R.id.cancelbtn);

            button_success.setOnClickListener(v -> {

                if ((!txt_fromdate.getText().toString().isEmpty()) && (!txt_todate.getText().toString().isEmpty())) {
                    lay_list.setVisibility(View.GONE);
                    list.setAdapter(null);
                    mcancelDialog.dismiss();
                    new FilterDriverApi(txt_fromdate.getText().toString(), txt_todate.getText().toString(), temp_status);
                } else {
                    Toast.makeText(DriverWithdrawHistoryAct.this,"" + DriverNC.getResources().getString(R.string.please_choosedate), Toast.LENGTH_LONG).show();
//                    dialog1 = Driver_Utils.alert_view(DriverWithdrawHistoryAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.please_choosedate), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverWithdrawHistoryAct.this, "");
                }

            });

            button_cancel.setOnClickListener(v -> mcancelDialog.dismiss());

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        if (dialog1 != null)
            Driver_Utils.closeDialog(dialog1);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        viewType = 1;

        if (data != null)
            data.clear();
        btn_filter.setVisibility(View.INVISIBLE);
        SetWithdrawList();
    }

    /**
     * Pick date selection
     * Type:1(fromdate)
     * Type:2(todate)
     */
    private void Pickdate(final int type) {
        final Context context = this;
        try {
            final View r_view = View.inflate(context, R.layout.date_time_picker_dialog, null);

            DriverFontHelper.applyFont(context, r_view.findViewById(R.id.inner_content));
            dt_mDialog = new Dialog(context, R.style.dialogwinddow);
            dt_mDialog.setContentView(r_view);
            dt_mDialog.setCancelable(true);
            dt_mDialog.show();
            DirverColorchange.ChangeColor(dt_mDialog.findViewById(R.id.inner_content), DriverWithdrawHistoryAct.this);
            final DatePicker _datePicker = dt_mDialog.findViewById(R.id.datePicker1);
            final TimePicker _timePicker = dt_mDialog.findViewById(R.id.timePicker1);
            _timePicker.setVisibility(View.GONE);
            DriverFontHelper.overrideFonts(context, _datePicker);
            DriverFontHelper.overrideFonts(context, _timePicker);
            Calendar c = Calendar.getInstance();
            _timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY) + 1);
            _timePicker.setCurrentMinute(c.get(Calendar.MINUTE) + 1);
            _timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            });
            Time now = new Time();
            Button butConfirmTime = dt_mDialog.findViewById(R.id.butConfirmTime);
            butConfirmTime.setOnClickListener(v -> {
                getCurrentDateAndTime(_timePicker, _datePicker);
                String seletecedString = "" + _year + "-" + _month + "-" + _date;

                if (type == 1) {
                    txt_fromdate.setText(seletecedString);
                } else {
                    txt_todate.setText(seletecedString);
                }

                dt_mDialog.dismiss();
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    /**
     * Getting current date and time from time picker
     */
    public void getCurrentDateAndTime(TimePicker _timePicker, DatePicker _datePicker) {

        _hour = _timePicker.getCurrentHour();
        _min = _timePicker.getCurrentMinute();
        _date = _datePicker.getDayOfMonth();
        _month = _datePicker.getMonth() + 1;
        _year = _datePicker.getYear();
        ampmValidation(_hour);
    }


    /**
     * Am and Pm Validation
     */
    private String ampmValidation(int inputHour) {

        if (inputHour >= 13) {
            _hour = inputHour - 12;
            _ampm = "PM";
        } else if (inputHour == 12) {
            _ampm = "PM";
        } else if (inputHour == 0) {
            _hour = 12;
            _ampm = "AM";
        }
        return _ampm;
    }


    /**
     * This method will be called from referal on click and trip onclick listener
     * viewType - 1(show referal history)
     * viewType - 2(show trip history)
     */
    public void SetWithdrawList() {
        if (viewType == 1) {
            btn_referal.setTextColor(DriverCL.getResources().getColor(R.color.white));

            referl_underline.setBackgroundColor(DriverCL.getResources().getColor(R.color.linebottom_light));
            trip_underline.setBackgroundColor(DriverCL.getResources().getColor(R.color.button_accept));
            btn_trip.setTextColor(DriverCL.getResources().getColor(R.color.hintcolor));

            txt_view.setVisibility(View.GONE);

            new ReferalDriverApi();
        } else {
            btn_referal.setTextColor(DriverCL.getResources().getColor(R.color.hintcolor));
            btn_trip.setTextColor(DriverCL.getResources().getColor(R.color.white));
            referl_underline.setBackgroundColor(DriverCL.getResources().getColor(R.color.button_accept));
            trip_underline.setBackgroundColor(DriverCL.getResources().getColor(R.color.linebottom_light));
            txt_view.setVisibility(View.VISIBLE);

            new requestingDriverApi();
        }
    }

    /**
     * Filter withdrawlist API response parsing.
     */
    public class FilterDriverApi implements DriverAPIResult {
        public FilterDriverApi(String fromdate, String todate, int status) {

            try {
                JSONObject j = new JSONObject();
                j.put("driver_id", DriverSessionSave.getSession("Id", DriverWithdrawHistoryAct.this));
                if (fromdate.contains("yyyy-mm-dd"))
                    j.put("from", "");
                else
                    j.put("from", fromdate + fromtime);
                if (todate.contains("yyyy-mm-dd"))
                    j.put("to", "");
                else
                    j.put("to", todate + totime);
                j.put("status", status);

                String driverTripRequesting = "type=search_driver_withdraw_list";
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverWithdrawHistoryAct.this, this, j, false).execute(driverTripRequesting);
                } else {
                    Toast.makeText(DriverWithdrawHistoryAct.this,"" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG).show();
//                    dialog1 = Driver_Utils.alert_view(DriverWithdrawHistoryAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverWithdrawHistoryAct.this, "");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            try {
                Log.e("_Result_", result);

                if (isSuccess) {
                    data = new ArrayList<>();
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("status") == 1) {
                        JSONArray jobject = object.getJSONArray("details");
                        for (int i = 0; i < jobject.length(); i++) {
                            JSONObject jo = jobject.getJSONObject(i);
                            HashMap<String, String> hm = new HashMap<>();

                            hm.put("wallet_request_id", jo.getString("withdraw_request_id"));
                            hm.put("wallet_request_amount", jo.getString("withdraw_amount"));
                            hm.put("status", jo.getString("request_status"));
                            hm.put("wallet_request_date", jo.getString("request_date"));

                            data.add(hm);


                        }
                        list.setAdapter(new DriverWithdraw_history_adapter(DriverWithdrawHistoryAct.this, data, viewType));
                        if (data.size() <= 0) {
                            no_data_txt.setVisibility(View.VISIBLE);
                            lay_list.setVisibility(View.GONE);
                            //findViewById(R.id.progress_history).setVisibility(View.GONE);
                        } else {
                            no_data_txt.setVisibility(View.GONE);
                            lay_list.setVisibility(View.VISIBLE);
                            //findViewById(R.id.progress_history).setVisibility(View.GONE);
                        }
                    } else {
                        no_data_txt.setVisibility(View.VISIBLE);
                        lay_list.setVisibility(View.GONE);
                        //findViewById(R.id.progress_history).setVisibility(View.GONE);
                    }
                } else {

                    runOnUiThread(() -> Toast.makeText(DriverWithdrawHistoryAct.this,"" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG).show());
//                            dialog1 = Driver_Utils.alert_view(DriverWithdrawHistoryAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverWithdrawHistoryAct.this, ""));
                }
            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
    }

    /**
     * Trip Withdraw history API response parsing.
     */
    public class requestingDriverApi implements DriverAPIResult {
        public requestingDriverApi() {

            try {
                JSONObject j = new JSONObject();
                j.put("driver_id", DriverSessionSave.getSession("Id", DriverWithdrawHistoryAct.this));

                String driverTripRequesting = "type=driver_withdraw_list";
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverWithdrawHistoryAct.this, this, j, false).execute(driverTripRequesting);
                } else {
                    Toast.makeText(DriverWithdrawHistoryAct.this,"" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG).show();
//                    dialog1 = Driver_Utils.alert_view(DriverWithdrawHistoryAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverWithdrawHistoryAct.this, "");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            try {
                if (isSuccess) {
                    data = new ArrayList<>();
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("status") == 1) {
                        JSONArray jobject = object.getJSONArray("details");
                        for (int i = 0; i < jobject.length(); i++) {
                            JSONObject jo = jobject.getJSONObject(i);
                            HashMap<String, String> hm = new HashMap<>();

                            hm.put("wallet_request_id", jo.getString("withdraw_request_id"));
                            hm.put("wallet_request_amount", jo.getString("withdraw_amount"));
                            hm.put("status", jo.getString("request_status"));
                            hm.put("wallet_request_date", jo.getString("request_date"));

                            data.add(hm);


                        }
                        list.setAdapter(new DriverWithdraw_history_adapter(DriverWithdrawHistoryAct.this, data, viewType));
                        if (data.size() <= 0) {
                            no_data_txt.setVisibility(View.VISIBLE);
                            lay_list.setVisibility(View.GONE);
                            //findViewById(R.id.progress_history).setVisibility(View.GONE);
                        } else {
                            no_data_txt.setVisibility(View.GONE);
                            lay_list.setVisibility(View.VISIBLE);
                           // findViewById(R.id.progress_history).setVisibility(View.GONE);
                        }
                    } else {
                        no_data_txt.setVisibility(View.VISIBLE);
                        lay_list.setVisibility(View.GONE);
                    }
                } else {

                    runOnUiThread(() -> Toast.makeText(DriverWithdrawHistoryAct.this,"" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG).show());
//                            dialog1 = Driver_Utils.alert_view(DriverWithdrawHistoryAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverWithdrawHistoryAct.this, ""));
                }
            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
    }


    /**
     * Referal Withdraw history API response parsing.
     */
    public class ReferalDriverApi implements DriverAPIResult {
        public ReferalDriverApi() {

            try {
                JSONObject j = new JSONObject();
                j.put("driver_id", DriverSessionSave.getSession("Id", DriverWithdrawHistoryAct.this));

                String driverTripRequesting = "type=driver_wallet";
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverWithdrawHistoryAct.this, this, j, false).execute(driverTripRequesting);
                } else {
                    Toast.makeText(DriverWithdrawHistoryAct.this,"" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG).show();
//                    dialog1 = Driver_Utils.alert_view(DriverWithdrawHistoryAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverWithdrawHistoryAct.this, "");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            Log.e("result ", result);
            try {
                if (isSuccess) {
                    data = new ArrayList<>();
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("status") == 1) {

                        JSONArray jobject = object.getJSONArray("request_lists");
                        for (int i = 0; i < jobject.length(); i++) {
                            JSONObject jo = jobject.getJSONObject(i);
                            HashMap<String, String> hm = new HashMap<>();

                            hm.put("wallet_request_id", jo.getString("wallet_request_id"));
                            hm.put("wallet_request_amount", jo.getString("wallet_request_amount"));
                            hm.put("status", jo.getString("status"));
                            hm.put("wallet_request_date", jo.getString("wallet_request_date"));
                            data.add(hm);
                        }
                        list.setAdapter(new DriverWithdraw_referalhistory_adapter(DriverWithdrawHistoryAct.this, data));
                        if (data.size() <= 0) {
                            no_data_txt.setVisibility(View.VISIBLE);
                            lay_list.setVisibility(View.GONE);
                            // findViewById(R.id.progress_history).setVisibility(View.GONE);
                        } else {
                            no_data_txt.setVisibility(View.GONE);
                            lay_list.setVisibility(View.VISIBLE);
                            // findViewById(R.id.progress_history).setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(DriverWithdrawHistoryAct.this,"" +object.getString("message"), Toast.LENGTH_LONG).show();
//                        dialog1 = Driver_Utils.alert_view(DriverWithdrawHistoryAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + object.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverWithdrawHistoryAct.this, "");
                    }
                } else {

                    runOnUiThread(() -> Toast.makeText(DriverWithdrawHistoryAct.this,"" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG).show());
//                            dialog1 = Driver_Utils.alert_view(DriverWithdrawHistoryAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverWithdrawHistoryAct.this, ""));
                }
            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
    }

    @Override
    public void positiveButtonClick(DialogInterface dialog, int id, String s) {
        dialog.dismiss();
    }
}