package com.movedriver.driver;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movedriver.R;
import com.movedriver.driver.data.DriverCommonData;
import com.movedriver.driver.interfaces.DriverAPIResult;
import com.movedriver.driver.interfaces.DriverClickInterface;
import com.movedriver.driver.service.DriverAPIService_Retrofit_JSON;
import com.movedriver.driver.utils.DirverColorchange;
import com.movedriver.driver.utils.DriverFontHelper;
import com.movedriver.driver.utils.DriverNC;
import com.movedriver.driver.utils.DriverSessionSave;
import com.movedriver.driver.utils.Driver_Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class is used to view withdraw history list details
 */

public class DriverWithdrawReqAct extends MainActivityDriver implements DriverClickInterface {

    public static DriverWithdrawReqAct withdrawAct;

    TextView reqId, brandType, companyName, withdrawAmount, waitTimeCost, reqDate, status, paymentmode, transactionID, comments, btn_back;

    ImageView img_attachment;

    String withdrawrequestId = "";

    Dialog dialog1;

    @Override
    public int setLayout() {

        setLocale();
        return R.layout.driver_withdrawreq;
    }

    /**
     * Initialize the views on layout and variable declarations
     */
    @Override
    public void Initialize() {

        try {

            DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverWithdrawReqAct.this.findViewById(android.R.id.content)).getChildAt(0)), DriverWithdrawReqAct.this);

            DriverCommonData.mActivitylist.add(this);
            DriverCommonData.sContext = this;
            DriverCommonData.current_act = "WithDrawMenuAct";
            DriverFontHelper.applyFont(this, findViewById(R.id.withdraw_menu));
            withdrawAct = this;

            Bundle bundle = getIntent().getExtras();

            if (bundle != null) {
                withdrawrequestId = bundle.getString("wallet_request_id");
                Log.e("wallet_request_id", withdrawrequestId);
            }

            reqId = findViewById(R.id.reqIdTxt);
            brandType = findViewById(R.id.brandtypeTxt);
            companyName = findViewById(R.id.companynameTxt);
            withdrawAmount = findViewById(R.id.withdrawamtTxt);
            waitTimeCost = findViewById(R.id.waitingtimecostTxt);
            reqDate = findViewById(R.id.requesteddateTxt);
            status = findViewById(R.id.statusTxt);
            paymentmode = findViewById(R.id.pmodeTxt);
            transactionID = findViewById(R.id.TIdTxt);
            comments = findViewById(R.id.commentstxt);
            img_attachment = findViewById(R.id.imgattachment);
            btn_back = findViewById(R.id.slideImg);

            btn_back.setOnClickListener(v -> startActivity(new Intent(DriverWithdrawReqAct.this, DriverWithdrawHistoryAct.class)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        new requestingDriverApi();
    }

    /**
     * Withdraw History View API response parsing.
     */
    public class requestingDriverApi implements DriverAPIResult {
        public requestingDriverApi() {

            try {
                JSONObject j = new JSONObject();

                j.put("withdraw_request_id", withdrawrequestId);
                j.put("driver_id", DriverSessionSave.getSession("Id", DriverWithdrawReqAct.this));

                Log.e("Json ", j.toString());

                String driverTripRequesting = "type=driver_withdraw_list_detail";
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverWithdrawReqAct.this, this, j, false).execute(driverTripRequesting);
                } else {
                    Toast.makeText(DriverWithdrawReqAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG).show();
//                    dialog1 = Driver_Utils.alert_view(DriverWithdrawReqAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverWithdrawReqAct.this, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            try {

                Log.e("isSuccess ", result);

                if (isSuccess) {

                    JSONObject object = new JSONObject(result);
                    if (object.getInt("status") == 1) {
                        JSONArray jobject = object.getJSONArray("details");

                        JSONArray jobject2 = object.getJSONArray("activity_log");

                        reqId.setText(jobject.getJSONObject(0).getString("request_id"));

                        brandType.setText(jobject.getJSONObject(0).getString("brand_type"));
                        companyName.setText(jobject.getJSONObject(0).getString("company_name"));
                        withdrawAmount.setText(jobject.getJSONObject(0).getString("withdraw_amount"));
                        waitTimeCost.setText("");
                        reqDate.setText(jobject.getJSONObject(0).getString("request_date"));
                        status.setText(jobject.getJSONObject(0).getString("request_status"));
                        if (jobject2.getJSONObject(0).has("payment_mode_name"))
                            paymentmode.setText(jobject2.getJSONObject(0).getString("payment_mode_name"));
                        if (jobject2.getJSONObject(0).has("transaction_id"))
                            transactionID.setText(jobject2.getJSONObject(0).getString("transaction_id"));
                        if (jobject2.getJSONObject(0).has("comments"))
                            comments.setText(jobject2.getJSONObject(0).getString("comments"));
                        if (jobject2.getJSONObject(0).has("attachment")) {
                            String attachment = jobject2.getJSONObject(0).getString("attachment");
                            if (attachment.endsWith("png")) {
                                img_attachment.setVisibility(View.VISIBLE);
                                Picasso.get().load(attachment).into(img_attachment);
                            } else {
                                img_attachment.setVisibility(View.INVISIBLE);
                            }
                        }

                    } else {
                        Toast.makeText(DriverWithdrawReqAct.this, "" + object.getString("message"), Toast.LENGTH_LONG).show();
//                        dialog1 = Driver_Utils.alert_view(DriverWithdrawReqAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + object.getString("message"), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverWithdrawReqAct.this, "");
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(DriverWithdrawReqAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG).show());
//                            dialog1 = Driver_Utils.alert_view(DriverWithdrawReqAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverWithdrawReqAct.this, ""));
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

    @Override
    protected void onDestroy() {
        if (dialog1 != null) Driver_Utils.closeDialog(dialog1);
        super.onDestroy();
    }
}
