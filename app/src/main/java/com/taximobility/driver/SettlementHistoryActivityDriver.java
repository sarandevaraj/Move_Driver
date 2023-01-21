package com.taximobility.driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.driver.adapter.DriverSettlementHistoryAdapter;
import com.taximobility.driver.data.apiData.DriverApiRequestData;
import com.taximobility.driver.data.apiData.DriverListClass;
import com.taximobility.driver.data.apiData.DriverSettlementHistoryData;
import com.taximobility.driver.earningchart.DriverEarningsAct;
import com.taximobility.driver.service.DriverCoreClient;
import com.taximobility.driver.service.DriverRetrofitCallbackClass;
import com.taximobility.driver.utils.DirverColorchange;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.util.AppController;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettlementHistoryActivityDriver extends DriverBaseActivity {

    RecyclerView recyclerView;
    TextView txt_credit_val;
    TextView btn_req;
    DriverSettlementHistoryAdapter mAdapter;
    TextView leftIcon, header_titleTxt, txt_nodata;
    FrameLayout showProgress;
    private final List<DriverListClass> pastData = new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_settlement_history_lay);
        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) SettlementHistoryActivityDriver.this.findViewById(android.R.id.content)).getChildAt(0)), SettlementHistoryActivityDriver.this);
        mAdapter = new DriverSettlementHistoryAdapter(SettlementHistoryActivityDriver.this, pastData);
        recyclerView = findViewById(R.id.recycler_view);
        txt_credit_val = findViewById(R.id.txt_credit_val);
        txt_nodata = findViewById(R.id.txt_nodata);
        btn_req = findViewById(R.id.btn_req);
        leftIcon = findViewById(R.id.leftIcon);
        header_titleTxt = findViewById(R.id.header_titleTxt);
        leftIcon.setVisibility(View.VISIBLE);
        header_titleTxt.setText(DriverNC.getString(R.string.settlement_history));
        showProgress = findViewById(R.id.showProgress);
        showProgress.setVisibility(View.VISIBLE);
        LinearLayoutManager ss = new LinearLayoutManager(SettlementHistoryActivityDriver.this);
        ss.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ss);
        setHistoryDetail();
        recyclerView.setAdapter(mAdapter);
        txt_credit_val.setSelected(true);

        btn_req.setOnClickListener(view -> startActivity(new Intent(SettlementHistoryActivityDriver.this, DriverSettlementDetail.class)));

        leftIcon.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }

    private void setHistoryDetail() {

//        CoreClient client = new ServiceGenerator(SettlementHistoryActivity.this, false).createService(CoreClient.class);
        DriverCoreClient client = AppController.getInstance().getApiManagerWithEncryptBaseUrl_driver();
        DriverApiRequestData.SettlementHistory apiData = new DriverApiRequestData.SettlementHistory();
        apiData.driver_id = DriverSessionSave.getSession("Id", SettlementHistoryActivityDriver.this);
        Call<DriverSettlementHistoryData> settlementHistoryDataCall = client.settlement_historyCall(apiData, DriverSessionSave.getSession("Lang", SettlementHistoryActivityDriver.this));
        settlementHistoryDataCall.enqueue(new DriverRetrofitCallbackClass<>(SettlementHistoryActivityDriver.this, new Callback<DriverSettlementHistoryData>() {

            @Override
            public void onResponse(@NonNull Call<DriverSettlementHistoryData> call, @NonNull Response<DriverSettlementHistoryData> response) {
                showProgress.setVisibility(View.GONE);
                try {
                    if (response.isSuccessful()) {
                        DriverSettlementHistoryData data = response.body();
                        if (data != null) {
                            if (data.status == 1) {
                                txt_credit_val.setText(DriverSessionSave.getSession("site_currency", SettlementHistoryActivityDriver.this) + "" + data.total_amount_driver);
                                if (data.list == null || data.list.size() == 0) {
                                    recyclerView.setVisibility(View.GONE);
                                    txt_nodata.setVisibility(View.VISIBLE);
                                    txt_nodata.setText(data.message);
                                } else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    txt_nodata.setVisibility(View.GONE);
                                    pastData.addAll(data.list);
                                    if (mAdapter == null) {
                                        mAdapter = new DriverSettlementHistoryAdapter(SettlementHistoryActivityDriver.this, pastData);
                                        recyclerView.setAdapter(mAdapter);
                                    } else {
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                txt_nodata.setVisibility(View.VISIBLE);
                                txt_nodata.setText(data.message);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DriverSettlementHistoryData> call, @NonNull Throwable t) {
                t.printStackTrace();
                showProgress.setVisibility(View.GONE);
            }
        }));
    }

    @Override

    public void onBackPressed() {

        super.onBackPressed();
        startActivity(new Intent(SettlementHistoryActivityDriver.this, DriverEarningsAct.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}