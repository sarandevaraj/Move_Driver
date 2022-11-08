package com.taximobility.roomDB;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.taximobility.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.taximobility.util.Systems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CommonLoggerAct extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {


    private List<String> mList;
    private RecyclerView mRecyclerView;
    private LoggerModel mModel;
    private LinearLayoutManager manager;
    private String sessionKey;
    private String prefKey;
    private int start = 0;
    private int limit = 50;
    private int prevLimt;
    private LoggerViewModel viewModel;
    private PieChart mChart;
    private List<LoggerModel> mList1, mList2, mList3, mList4, mList5, mList6;
    private Spinner spinnerFilter;
    private List<String> apiList;
    private List<LoggerModel> loggerModels = new ArrayList<>();
    private boolean flag = true;
    private LoggerPagdeAdapter mPagedAdapter;
    private TextView textShowChart;
    private TextView textCount;
    private boolean showChart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_logger);

        mRecyclerView = findViewById(R.id.recyclerView);
        spinnerFilter = findViewById(R.id.spinner_filter);
        mChart = findViewById(R.id.piechart);
        textShowChart = findViewById(R.id.text_showChart);
        textCount = findViewById(R.id.text_count);

        viewModel = ViewModelProviders.of(CommonLoggerAct.this).get(LoggerViewModel.class);

//        mList = new ArrayList<>();

        mPagedAdapter = new LoggerPagdeAdapter(CommonLoggerAct.this);


        manager = new LinearLayoutManager(CommonLoggerAct.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);

        mRecyclerView.setAdapter(mPagedAdapter);

        viewModel.loadDistinctApi().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> loggerModel) {
                // Update the cached copy of the words in the adapter.
                Systems.out.println("haiiii " + "loadDistinctApi() " + loggerModel.size());
                mList = loggerModel;
                setSpinnerData();
//                viewModel.loadDistinctApi().removeObservers(CommonLoggerAct.this);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        String[] apiType = {"driver_profile", "driver_recent_trip_list", "getcoreconfig", "directions", "Place AutoComplete", "Places API"};

        spinnerFilter.setOnItemSelectedListener(this);

        textShowChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOrGoneChart();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mChart.setUsePercentValues(false);
        mChart.setDrawHoleEnabled(true);
        mChart.setTransparentCircleRadius(30f);
        mChart.setHoleRadius(30f);
        mChart.animateY(1400);

//        Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);


        // entry label styling
        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTextSize(12f);
        mChart.getLegend().setEnabled(false);


    }

    private void setSpinnerData() {
        HashSet<String> apiKeys = new HashSet<>();
        apiList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            apiKeys.add(mList.get(i));
        }
        apiList.addAll(apiKeys);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerFilter.setAdapter(aa);
    }

    private List<LoggerModel> getFilteredLog(String queryName) {
        Systems.out.println("keyyyyyyy getFilteredLog " + queryName);
        viewModel.getAllUser(queryName).observe(this, new Observer<List<LoggerModel>>() {
            @Override
            public void onChanged(@Nullable List<LoggerModel> loggerModels) {
                CommonLoggerAct.this.loggerModels = loggerModels;
            }
        });
        return loggerModels;
    }

    private ArrayList<PieEntry> getEntries() {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        Systems.out.println("keyyyyyyy " + mList.size());
//        entries.clear();
        for (int i = 0; i < mList.size(); i++) {

            Systems.out.println("nan---getalluser " + mList.get(i));
            final int finalI = i;

            viewModel.getCount(mList.get(finalI)).observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer lModels) {

                    if (lModels != null && lModels != 0) {
                        Systems.out.println("hlooooo " + "modelList " + mList.size() + "    " + entries.size() + "   " + finalI);
                        if (entries.size() <= mList.size()) {
                            entries.add(new PieEntry((lModels),
                                    mList.get(finalI) + lModels));
                            if (entries.size() == mList.size())
                                if (mChart.getData() == null)
                                    setData(entries);
                        }
                    }
                }
            });
        }

        return entries;

    }

    private void setData(final ArrayList<PieEntry> entries) {
        Systems.out.println("entriessss size" + entries.size());
        PieDataSet dataSet = new PieDataSet(entries, "API Logs");

//        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(0f);
//        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        mChart.setData(data);

        Systems.out.println("hlooooo entries" + entries.size() + "___" + colors.size() + "___" + dataSet.getFormSize() + "___" + dataSet.getValueTextSize());

//        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        final String sss = String.valueOf(adapterView.getItemAtPosition(i));

        viewModel.logsByApiType(sss).observe(CommonLoggerAct.this, new Observer<PagedList<LoggerModel>>() {
            @Override
            public void onChanged(@Nullable PagedList<LoggerModel> loggerModels) {
                if (loggerModels != null) {
                    mPagedAdapter.submitList(loggerModels);
                    textCount.setText("Count " + loggerModels.size());
                }
            }
        });

        getEntries();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void viewOrGoneChart() {
        if (showChart) {
            showChart = false;
            mChart.setVisibility(View.VISIBLE);
            textCount.setVisibility(View.GONE);
        } else {
            showChart = true;
            mChart.setVisibility(View.GONE);
            textCount.setVisibility(View.VISIBLE);
        }
    }

}