package com.movedriver.driver;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.movedriver.R;
import com.movedriver.driver.adapter.DriverWalletHistoryListAdapter;
import com.movedriver.driver.data.DriverWalletHistoryData;
import com.movedriver.driver.interfaces.DriverAPIResult;
import com.movedriver.driver.service.DriverAPIService_Retrofit_JSON;
import com.movedriver.driver.utils.DriverCToast;
import com.movedriver.driver.utils.DriverNC;
import com.movedriver.driver.utils.DriverNetworkStatus;
import com.movedriver.driver.utils.DriverSessionSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DriverWalletHistory extends MainActivityDriver {

    int start = 0;
    DriverWalletHistoryListAdapter past_booking_adapter;
    RecyclerView history_recyclerView;
    private final List<DriverWalletHistoryData> pastData = new ArrayList<>();
    TextView no_data;
    Dialog loadingDialog;
    private Dialog dialog1;
    private int limit = 10;
    private int prevLimt;

    @Override
    public int setLayout() {
        return R.layout.corporate_list;
    }

    @Override
    public void Initialize() {
        history_recyclerView = findViewById(R.id.corporate_recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        history_recyclerView.setLayoutManager(llm);
        past_booking_adapter = new DriverWalletHistoryListAdapter(DriverWalletHistory.this, pastData);
        history_recyclerView.setAdapter(past_booking_adapter);
        findViewById(R.id.back_text).setOnClickListener(v -> onBackPressed());

        no_data = findViewById(R.id.nodataTxt);
        showDialog();

        try {
            JSONObject j = new JSONObject();
            j.put("driver_id", DriverSessionSave.getSession("Id", DriverWalletHistory.this));
            j.put("start", start);
            j.put("limit", limit);

            final String url = "type=driver_wallet_logs";
            new callWalletHistory(url, j);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        history_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0) //check for scroll down
                    {
                        int visibleItemCount = llm.getChildCount();
                        int totalItemCount = llm.getItemCount();
                        int pastVisiblesItems = llm.findFirstVisibleItemPosition();
                        Log.v("...", "Last Item Wow !" + visibleItemCount + "___" + pastVisiblesItems + "___" + totalItemCount);

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            // loading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            if (totalItemCount >= 10 && limit >= prevLimt && totalItemCount == limit) {
                                System.out.println("_*____*****_" + limit + "***" + start + "***" + totalItemCount);
                                if (start == 0)
                                    start = 11;
                                else
                                    start += 10;
                                prevLimt = limit;
                                limit += 10;
                                try {
                                    JSONObject j = new JSONObject();
                                    j.put("driver_id", DriverSessionSave.getSession("Id", DriverWalletHistory.this));
                                    j.put("start", start);
                                    j.put("limit", limit);

                                    final String url = "type=driver_wallet_logs";
                                    new callWalletHistory(url, j);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            }
        });
    }

    private class callWalletHistory implements DriverAPIResult {
        public callWalletHistory(String url, JSONObject data) {
            if (isOnline()) {
                new DriverAPIService_Retrofit_JSON(DriverWalletHistory.this, this, data, false).execute(url);
            } else {
                Toast.makeText(DriverWalletHistory.this,"" + DriverNC.getResources().getString(R.string.check_net_connection), Toast.LENGTH_LONG).show();
//                dialog1 = Driver_Utils.alert_view(DriverWalletHistory.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverWalletHistory.this, "");
            }
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            if (isSuccess) {
                pastData.clear();
                try {
                    final JSONObject json = new JSONObject(result);
                    closeDialog();
                    if (json.getInt("status") == 1) {

                        JSONArray mWalletArray = json.getJSONArray("result");
                        for (int i = 0; i < mWalletArray.length(); i++) {
                            JSONObject mJsonObject = mWalletArray.getJSONObject(i);
                            DriverWalletHistoryData mWalletHistoryData = new DriverWalletHistoryData();
                            mWalletHistoryData.setAmt(mJsonObject.getString("amt"));
                            mWalletHistoryData.setComments(mJsonObject.getString("comments"));
                            mWalletHistoryData.setCreated_date(mJsonObject.getString("created_date"));
                            mWalletHistoryData.setSign(mJsonObject.getString("sign"));
                            mWalletHistoryData.setUpdated_balance(mJsonObject.getString("updated_balance"));
                            pastData.add(mWalletHistoryData);
                        }
                    }
                } catch (final JSONException e) {
                    e.printStackTrace();
                } finally {

                    if (pastData.size() == 0) {
                        no_data.setVisibility(View.VISIBLE);
                    } else {
                        no_data.setVisibility(View.GONE);
                        past_booking_adapter = new DriverWalletHistoryListAdapter(DriverWalletHistory.this, pastData);
                        history_recyclerView.setAdapter(past_booking_adapter);
                        past_booking_adapter.notifyDataSetChanged();
                    }
                }
            } else {
                runOnUiThread(() -> DriverCToast.ShowToast(DriverWalletHistory.this, getString(R.string.server_error)));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showDialog() {
        try {
            if (DriverNetworkStatus.isOnline(DriverWalletHistory.this)) {
                if (loadingDialog != null && loadingDialog.isShowing())
                    loadingDialog.dismiss();
                View view = View.inflate(DriverWalletHistory.this, R.layout.driver_progress_bar, null);
                loadingDialog = new Dialog(DriverWalletHistory.this, R.style.dialogwinddow);
                loadingDialog.setContentView(view);
                loadingDialog.setCancelable(false);
                loadingDialog.show();

                ImageView iv = loadingDialog.findViewById(R.id.giff);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                Glide.with(this)
                        .load(R.raw.driver_loading_anim)
                        .into(imageViewTarget);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //method to close dialog
    public void closeDialog() {

        try {
            if (loadingDialog != null)
                if (loadingDialog.isShowing())
                    loadingDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
