package com.movedriver.driver;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.movedriver.R;
import com.movedriver.driver.data.DriverCommonData;
import com.movedriver.driver.interfaces.DriverAPIResult;
import com.movedriver.driver.interfaces.DriverClickInterface;
import com.movedriver.driver.service.DriverAPIService_Retrofit_JSON;
import com.movedriver.driver.service.DriverServiceGenerator;
import com.movedriver.driver.utils.DriverCToast;
import com.movedriver.driver.utils.DirverColorchange;
import com.movedriver.driver.utils.DriverFontHelper;
import com.movedriver.driver.utils.DriverNC;
import com.movedriver.driver.utils.DriverSessionSave;
import com.movedriver.driver.utils.Driver_Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is used to  withdraw both referal and trip amount
 */

public class DriverWithDrawMenuAct extends MainActivityDriver implements DriverClickInterface {

    public static DriverWithDrawMenuAct withdrawAct;
    private TextView withdraw_btn1, withdraw_btn2, txt_referalamt, txt_tripamount, txt_refpendingamt, txt_trippendingamt, btn_back, versionText;
    private ImageView btn_withdrawhistory;
    private Dialog dialog1;

    @Override
    public int setLayout() {
        setLocale();
        return R.layout.driver_withdraw_menu;
    }

    // Initialize the views on layout and variable declarations
    @Override
    public void Initialize() {

        try {
            DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverWithDrawMenuAct.this.findViewById(android.R.id.content)).getChildAt(0)), DriverWithDrawMenuAct.this);

            DriverCommonData.mActivitylist.add(this);
            DriverCommonData.sContext = this;
            DriverCommonData.current_act = "WithDrawMenuAct";
            //DriverFontHelper.applyFont(this, findViewById(R.id.withdraw_menu));
            withdrawAct = this;
            withdraw_btn1 = findViewById(R.id.withdraw_btn1);
            withdraw_btn2 = findViewById(R.id.withdraw_btn2);
            txt_referalamt = findViewById(R.id.withdraw_BalanceAmttext1);
            txt_tripamount = findViewById(R.id.withdraw_BalanceAmttext2);
            txt_refpendingamt = findViewById(R.id.withdraw_pendingtext1);
            txt_trippendingamt = findViewById(R.id.withdraw_pendingtext2);
            versionText = findViewById(R.id.version_text);
            btn_back = findViewById(R.id.slideImg);
            btn_withdrawhistory = findViewById(R.id.history);
            txt_referalamt.setText(DriverSessionSave.getSession("site_currency", DriverWithDrawMenuAct.this) + "" + DriverSessionSave.getSession("driver_wallet_amount", DriverWithDrawMenuAct.this));
            txt_refpendingamt.setText(DriverNC.getResources().getString(R.string.payment_pending) + "- " + DriverSessionSave.getSession("site_currency", DriverWithDrawMenuAct.this) + " " + DriverSessionSave.getSession("driver_wallet_pending_amount", DriverWithDrawMenuAct.this));
            txt_tripamount.setText(DriverSessionSave.getSession("site_currency", DriverWithDrawMenuAct.this) + "" + DriverSessionSave.getSession("trip_amount", DriverWithDrawMenuAct.this));
            txt_trippendingamt.setText(DriverNC.getResources().getString(R.string.payment_pending) + "- " + DriverSessionSave.getSession("site_currency", DriverWithDrawMenuAct.this) + " " + DriverSessionSave.getSession("trip_pending_amount", DriverWithDrawMenuAct.this));
            String host = "";
            try {
                URL urls = new URL(DriverServiceGenerator.API_BASE_URL);
                host = urls.getHost();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //((TextView) findViewById(R.id.baseUrl)).setText(host);

           /* if (APP_VERSION.equals("")) {
                APP_VERSION = BuildConfig.VERSION_NAME;
            }
*/

            versionText.setText(APP_VERSION);
            withdraw_btn1.setOnClickListener(v -> {

                if (Double.parseDouble(DriverSessionSave.getSession("driver_wallet_amount", DriverWithDrawMenuAct.this).trim()) > 0) {
                    withDraw(1);
                } else {
                    Toast.makeText(DriverWithDrawMenuAct.this, "" + DriverNC.getResources().getString(R.string.no_sufficient_amount), Toast.LENGTH_LONG).show();
//                    dialog1 = Driver_Utils.alert_view(DriverWithDrawMenuAct.this, "", "" + DriverNC.getResources().getString(R.string.no_sufficient_amount), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverWithDrawMenuAct.this, "");
                }
            });

            withdraw_btn2.setOnClickListener(v -> {
                if (Double.parseDouble(DriverSessionSave.getSession("trip_amount", DriverWithDrawMenuAct.this).trim()) > 0) {
                    withDraw(2);
                } else {
                    Toast.makeText(DriverWithDrawMenuAct.this, "" + DriverNC.getResources().getString(R.string.no_sufficient_amount), Toast.LENGTH_LONG).show();
//                    dialog1 = Driver_Utils.alert_view(DriverWithDrawMenuAct.this, "", "" + DriverNC.getResources().getString(R.string.no_sufficient_amount), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverWithDrawMenuAct.this, "");
                }
            });

            btn_withdrawhistory.setOnClickListener(v -> startActivity(new Intent(DriverWithDrawMenuAct.this, DriverWithdrawHistoryAct.class)));
            btn_back.setOnClickListener(v -> onBackPressed());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (dialog1 != null) Driver_Utils.closeDialog(dialog1);
        super.onDestroy();
    }

    /**
     * This class is used to  withdraw both referal and trip amount
     * Withdraw popup
     * type:1(referal)
     * type:2(trip)
     */
    public void withDraw(final int type) {
        try {
            View view = getLayoutInflater().inflate(R.layout.withdraw_req_sheet, null);
            BottomSheetDialog mcancelDialog = new BottomSheetDialog(this);
            mcancelDialog.setContentView(view);
            mcancelDialog.setCancelable(true);
            mcancelDialog.show();

//            final View view = View.inflate(DriverWithDrawMenuAct.this, R.layout.withdraw_req_sheet, null);
//            final Dialog mcancelDialog = new Dialog(DriverWithDrawMenuAct.this);
//            mcancelDialog.setContentView(view);
//            mcancelDialog.setCancelable(true);
//            mcancelDialog.show();

            DriverFontHelper.applyFont(DriverWithDrawMenuAct.this, mcancelDialog.findViewById(R.id.alert_id));
            DirverColorchange.ChangeColor((ViewGroup) view, DriverWithDrawMenuAct.this);

            final Button button_success = mcancelDialog.findViewById(R.id.okbtn);
            final Button button_failure = mcancelDialog.findViewById(R.id.cancelbtn);

            TextView txt_avaibal = mcancelDialog.findViewById(R.id.paymentavailedTxt);
            TextView txt_pendingbal = mcancelDialog.findViewById(R.id.paymentpendingTxt);
            TextView txt_reqqmt = mcancelDialog.findViewById(R.id.withdrawamountTxt);

            if (type == 1) {
                if (txt_avaibal != null) {
                    txt_avaibal.setText(DriverSessionSave.getSession("site_currency", this) + " " + DriverSessionSave.getSession("driver_wallet_amount", DriverWithDrawMenuAct.this));
                }
                if (txt_pendingbal != null) {
                    txt_pendingbal.setText(DriverSessionSave.getSession("site_currency", this) + " " + DriverSessionSave.getSession("driver_wallet_pending_amount", DriverWithDrawMenuAct.this));
                }
                if (txt_reqqmt != null) {
                    txt_reqqmt.setText(DriverSessionSave.getSession("site_currency", this) + " " + DriverSessionSave.getSession("driver_wallet_amount", DriverWithDrawMenuAct.this));
                }
            } else if (type == 2) {
                if (txt_avaibal != null) {
                    txt_avaibal.setText(DriverSessionSave.getSession("site_currency", this) + " " + DriverSessionSave.getSession("trip_amount", DriverWithDrawMenuAct.this));
                }
                if (txt_pendingbal != null) {
                    txt_pendingbal.setText(DriverSessionSave.getSession("site_currency", this) + " " + DriverSessionSave.getSession("trip_pending_amount", DriverWithDrawMenuAct.this));
                }
                if (txt_reqqmt != null) {
                    txt_reqqmt.setText(DriverSessionSave.getSession("site_currency", this) + " " + DriverSessionSave.getSession("trip_amount", DriverWithDrawMenuAct.this));
                }
            } else {
            }

            if (button_success != null) {
                button_success.setOnClickListener(v -> {
                    mcancelDialog.dismiss();
                    try {
                        if (type == 1) {
                            if (Double.parseDouble(DriverSessionSave.getSession("driver_wallet_amount", DriverWithDrawMenuAct.this).trim()) > 0) {
                                JSONObject j = new JSONObject();

                                try {
                                    j.put("driver_id", DriverSessionSave.getSession("Id", DriverWithDrawMenuAct.this));
                                    j.put("driver_wallet_amount", DriverSessionSave.getSession("driver_wallet_amount", DriverWithDrawMenuAct.this));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                final String withdrawUrl = "type=driver_wallet_request";
                                new WithDraw(withdrawUrl, j);
                            } else {
                                DriverCToast.ShowToast(DriverWithDrawMenuAct.this, DriverNC.getString(R.string.insufficent_amout));
                            }
                        } else {
                            if (Double.parseDouble(DriverSessionSave.getSession("trip_amount", DriverWithDrawMenuAct.this).trim()) > 0) {
                                JSONObject j = new JSONObject();

                                j.put("driver_id", DriverSessionSave.getSession("Id", DriverWithDrawMenuAct.this));
                                j.put("available_amount", DriverSessionSave.getSession("trip_amount", DriverWithDrawMenuAct.this));
                                j.put("request_amount", DriverSessionSave.getSession("trip_amount", DriverWithDrawMenuAct.this));

                                final String withdrawUrl = "type=driver_send_withdraw_request";
                                new WithDraw(withdrawUrl, j);
                            } else {
                                DriverCToast.ShowToast(DriverWithDrawMenuAct.this, DriverNC.getString(R.string.insufficent_amout));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }

            button_failure.setVisibility(View.VISIBLE);
            button_failure.setOnClickListener(v -> mcancelDialog.dismiss());

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Withdraw API response parsing.
     */
    private class WithDraw implements DriverAPIResult {

        public WithDraw(final String url, JSONObject data) {

            try {
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverWithDrawMenuAct.this, this, data, false).execute(url);
                } else {
                    Toast.makeText(DriverWithDrawMenuAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG).show();
//                    dialog1 = Driver_Utils.alert_view(DriverWithDrawMenuAct.this, "", "" + DriverNC.getResources().getString(R.string.check_net_connection), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverWithDrawMenuAct.this, "");

                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        /**
         * Parse the response and update the UI.
         */
        @Override
        public void getResult(final boolean isSuccess, final String result) {

            try {

                Log.e("result", result);
                if (isSuccess) {
                    final JSONObject json = new JSONObject(result);
                    if (json.getString("status").trim().equals("1")) {

                        if (json.has("details")) {
                            JSONObject details = json.getJSONObject("details");
                            if (details.has("trip_pending_amount")) {
                                DriverSessionSave.saveSession("trip_pending_amount", details.getString("trip_pending_amount"), DriverWithDrawMenuAct.this);
                                DriverSessionSave.saveSession("trip_amount", details.getString("trip_amount"), DriverWithDrawMenuAct.this);
                                DriverSessionSave.saveSession("total_amount", details.getString("total_amount"), DriverWithDrawMenuAct.this);
                                startActivity(new Intent(DriverWithDrawMenuAct.this, DriverWithDrawMenuAct.class));
                            }
                        }
                        if (json.has("driver_wallet_amount")) {
                            DriverSessionSave.saveSession("driver_wallet_amount", json.getString("driver_wallet_amount"), DriverWithDrawMenuAct.this);
                            DriverSessionSave.saveSession("driver_wallet_pending_amount", json.getString("driver_wallet_pending_amount"), DriverWithDrawMenuAct.this);
                            startActivity(new Intent(DriverWithDrawMenuAct.this, DriverWithDrawMenuAct.class));
                        }
                        txt_referalamt.setText(DriverSessionSave.getSession("site_currency", DriverWithDrawMenuAct.this) + " " + DriverSessionSave.getSession("driver_wallet_amount", DriverWithDrawMenuAct.this));
                        txt_refpendingamt.setText(DriverNC.getResources().getString(R.string.payment_pending) + "- " + DriverSessionSave.getSession("site_currency", DriverWithDrawMenuAct.this) + " " + DriverSessionSave.getSession("driver_wallet_pending_amount", DriverWithDrawMenuAct.this));

                        txt_tripamount.setText(DriverSessionSave.getSession("site_currency", DriverWithDrawMenuAct.this) + " " + DriverSessionSave.getSession("trip_amount", DriverWithDrawMenuAct.this));
                        txt_trippendingamt.setText(DriverNC.getResources().getString(R.string.payment_pending) + "- " + DriverSessionSave.getSession("site_currency", DriverWithDrawMenuAct.this) + " " + DriverSessionSave.getSession("trip_pending_amount", DriverWithDrawMenuAct.this));
                    }
                    Toast.makeText(DriverWithDrawMenuAct.this, "" + json.getString("message"), Toast.LENGTH_LONG).show();
//                    dialog1 = Driver_Utils.alert_view(DriverWithDrawMenuAct.this, "", "" + json.getString("message"), DriverNC.getResources().getString(R.string.ok),
//                            "", true, DriverWithDrawMenuAct.this, "");
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
