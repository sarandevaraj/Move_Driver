package com.movedriver.driver.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.movedriver.R;
import com.movedriver.driver.DriverTripHistoryAct;
import com.movedriver.driver.adapter.DriverPastBookingAdapter;
import com.movedriver.driver.adapter.DriverUpcomingAdapter;
import com.movedriver.driver.data.apiData.DriverApiRequestData;
import com.movedriver.driver.data.apiData.DriverUpcomingResponse;
import com.movedriver.driver.interfaces.DriverUpcomingAdapterInterface;
import com.movedriver.driver.service.DriverCoreClient;
import com.movedriver.driver.service.DriverRetrofitCallbackClass;
import com.movedriver.driver.service.DriverServiceGenerator;
import com.movedriver.driver.utils.DriverCL;
import com.movedriver.driver.utils.DriverCToast;
import com.movedriver.driver.utils.DirverColorchange;
import com.movedriver.driver.utils.DriverNC;
import com.movedriver.driver.utils.DriverNetworkStatus;
import com.movedriver.driver.utils.DriverSessionSave;
import com.movedriver.driver.utils.DriverSystems;
import com.movedriver.util.AppController;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by developer on 1/11/16.
 * This class is used to show trip history,Here we can see both upcoming and past booking history
 */
public class DriverTripHistory extends Fragment implements DriverUpcomingAdapterInterface {

    private static boolean UP_COMING = true;
    TextView txt_up_coming, txt_past_booking;
    RecyclerView history_recyclerView;
    private TextView no_data;
    int start = 0;
    private int limit = 10;
    private LinearLayoutManager mLayoutManager;
    private final List<DriverUpcomingResponse.PastBooking> pastData = new ArrayList<>();
    private List<DriverUpcomingResponse.PastBooking> upComingData = new ArrayList<>();
    private int prevLimt;
    private DriverPastBookingAdapter past_booking_adapter;
    private Dialog mDialog;
    private View upcoming_underline, past_underline;
    private boolean isFirst = true;
    private boolean isFirstUpcoming = true;

    private DriverUpcomingAdapter upcomingAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.driver_trip_history_lay, container, false);
        Initialize(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Initializing UI Components
     */
    public void Initialize(View v) {
        txt_past_booking = v.findViewById(R.id.txt_past_booking);
        txt_up_coming = v.findViewById(R.id.txt_up_coming);
        history_recyclerView = v.findViewById(R.id.history_recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        history_recyclerView.setLayoutManager(mLayoutManager);
        no_data = v.findViewById(R.id.nodataTxt);
        ((DriverTripHistoryAct) getActivity()).setTitle(getString(R.string.mybookings));
        DirverColorchange.ChangeColor((ViewGroup) v, getActivity());
        upcoming_underline = v.findViewById(R.id.upcoming_underline);
        past_underline = v.findViewById(R.id.past_underline);
        //upcoming trip history onclick
        txt_up_coming.setOnClickListener(view -> {
            upcoming_underline.setBackgroundColor(DriverCL.getResources().getColor(R.color.white));
            past_underline.setBackgroundColor(DriverCL.getResources().getColor(R.color.black));
            // txt_up_coming.setBackgroundColor(DriverCL.getResources().getColor(R.color.button_accept));
            txt_up_coming.setTextColor(DriverCL.getResources().getColor(R.color.white));
            txt_past_booking.setTextColor(DriverCL.getResources().getColor(R.color.textviewcolor_light));

            if (isFirstUpcoming) {
                DriverSystems.out.println("innnnnn " + "1st n upComing " + upComingData.size());
                isFirstUpcoming = false;
                history_recyclerView.setAdapter(null);
                UP_COMING = true;
                callUpComingData();
            } else {
                DriverSystems.out.println("innnnnn " + "2nd n upComing " + upComingData.size());
                if (upComingData.size() == 0) no_data.setVisibility(View.VISIBLE);
                else no_data.setVisibility(View.GONE);
                UP_COMING = true;
                upcomingAdapter = new DriverUpcomingAdapter(getContext(), upComingData, DriverTripHistory.this);
                history_recyclerView.setAdapter(upcomingAdapter);
            }
        });

        //past booking trip history onclick
        txt_past_booking.setOnClickListener(view -> {
            past_underline.setBackgroundColor(DriverCL.getResources().getColor(R.color.white));
            upcoming_underline.setBackgroundColor(DriverCL.getResources().getColor(R.color.black));
            //  txt_up_coming.setBackgroundColor(DriverCL.getResources().getColor(R.color.button_accept));
            txt_past_booking.setTextColor(DriverCL.getResources().getColor(R.color.white));
            txt_up_coming.setTextColor(DriverCL.getResources().getColor(R.color.textviewcolor_light));
            if (isFirst) {
                DriverSystems.out.println("innnnnn " + "1st");
                isFirst = false;
                pastData.clear();
                history_recyclerView.setAdapter(null);
                past_booking_adapter = null;
                start = 0;
                limit = 10;
                UP_COMING = false;
                callPastBookingData();
            } else {
                DriverSystems.out.println("innnnnn " + "2nd " + pastData.size());
                UP_COMING = false;
                if (pastData.size() == 0) no_data.setVisibility(View.VISIBLE);
                else no_data.setVisibility(View.GONE);
                past_booking_adapter = new DriverPastBookingAdapter(getContext(), pastData);
                history_recyclerView.setAdapter(past_booking_adapter);
            }
        });
        boolean ispastbookingenable = false;
        if (getArguments() != null) {
            System.out.println("  Sakthi check up coming pastbooking inside bundle -----> "+ispastbookingenable);
            Bundle mBundle = getArguments();
            ispastbookingenable = mBundle.getBoolean("ispastbookingenable");
        }
        System.out.println("  Sakthi check up coming pastbooking  -----> "+ispastbookingenable);
        if(ispastbookingenable)
            txt_past_booking.callOnClick();
        else
            txt_up_coming.callOnClick();

        history_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!UP_COMING) if (dy > 0) //check for scroll down
                {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        // loading = false;
                        //Do pagination.. i.e. fetch new data
                        if (totalItemCount >= 10 && limit >= prevLimt && totalItemCount == limit) {
                            DriverSystems.out.println("_*____*****_" + limit + "***" + start + "***" + totalItemCount);
                            if (start == 0) start = 11;
                            else start += 10;
                            prevLimt = limit;
                            limit += 10;
                            callPastBookingData();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        closeDialog();
    }


    /**
     * Upcomingtrip API response parsing.
     */
    private void callUpComingData() {
        isFirstUpcoming = false;
        DriverCoreClient client = AppController.getInstance().getApiManagerWithEncryptBaseUrl_driver();
        DriverApiRequestData.UpcomingRequest request = new DriverApiRequestData.UpcomingRequest();
        request.setId(DriverSessionSave.getSession("Id", getActivity()));
        request.setDeviceType("2");
        request.setLimit("10");
        request.setStart("0");
        request.setRequestType("1");

        Call<DriverUpcomingResponse> LoginResponse = client.callData(DriverServiceGenerator.COMPANY_KEY, request, DriverSessionSave.getSession("Lang", getActivity()));
        showDialog();
        LoginResponse.enqueue(new DriverRetrofitCallbackClass<>(getActivity(), new Callback<DriverUpcomingResponse>() {

            @Override
            public void onResponse(@NonNull Call<DriverUpcomingResponse> call, @NonNull Response<DriverUpcomingResponse> response) {
                closeDialog();
                if (getView() != null && response.isSuccessful()) {
                    DriverUpcomingResponse data = response.body();
                    if (data != null) {
                        if (data.status == 1) {
                            upComingData.addAll(data.detail.pending_booking);
                            if (data.detail.pending_booking == null)
                                no_data.setVisibility(View.VISIBLE);
                            else {
                                if (data.detail.pending_booking.size() == 0)
                                    no_data.setVisibility(View.VISIBLE);
                                else {
                                    if (upcomingAdapter == null) {
                                        upcomingAdapter = new DriverUpcomingAdapter(getContext(), upComingData, DriverTripHistory.this);
                                        history_recyclerView.setAdapter(upcomingAdapter);
                                        no_data.setVisibility(View.GONE);
                                    } else {
                                        upcomingAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        } else {
                            DriverCToast.ShowToast(getActivity(), data.message);
                            isFirstUpcoming = true;
                        }
                    } else {
                        DriverCToast.ShowToast(getActivity(), DriverNC.getString(R.string.server_error));
                        isFirstUpcoming = true;
                    }
                } else {
                    DriverCToast.ShowToast(getActivity(), DriverNC.getString(R.string.server_error));
                    isFirstUpcoming = true;
                }
            }

            @Override
            public void onFailure(@NonNull Call<DriverUpcomingResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                DriverCToast.ShowToast(getActivity(), DriverNC.getString(R.string.please_check_internet));
                closeDialog();
                isFirstUpcoming = true;
            }
        }));
    }

    /**
     * Show alert dialog
     */

    public void showDialog() {
        try {
            if (DriverNetworkStatus.isOnline(getActivity())) {
                View view = View.inflate(getActivity(), R.layout.driver_progress_bar, null);
                mDialog = new Dialog(getActivity(), R.style.dialogwinddow);
                mDialog.setContentView(view);
                mDialog.setCancelable(false);
                mDialog.show();

                ImageView iv = mDialog.findViewById(R.id.giff);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                Glide.with(DriverTripHistory.this).load(R.raw.driver_loading_anim).into(imageViewTarget);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close alert dialog
     */
    public void closeDialog() {
        try {
            if (mDialog != null) if (mDialog.isShowing()) mDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Pastbooking API response parsing.
     */
    private void callPastBookingData() {
        DriverCoreClient client = AppController.getInstance().getApiManagerWithEncryptBaseUrl_driver();
        DriverApiRequestData.UpcomingRequest request = new DriverApiRequestData.UpcomingRequest();
        request.setId(DriverSessionSave.getSession("Id", getActivity()));
        request.setDeviceType("2");
        request.setLimit("10");
        request.setStart(String.valueOf(start));
        request.setRequestType("2");

        Call<DriverUpcomingResponse> LoginResponse = client.callData_(DriverServiceGenerator.COMPANY_KEY, request, DriverSessionSave.getSession("Lang", getActivity()));
        showDialog();
        LoginResponse.enqueue(new DriverRetrofitCallbackClass<>(getActivity(), new Callback<DriverUpcomingResponse>() {
            @Override
            public void onResponse(@NonNull Call<DriverUpcomingResponse> call, @NonNull Response<DriverUpcomingResponse> response) {
                closeDialog();
                if (getView() != null && response.isSuccessful()) {
                    DriverUpcomingResponse data = response.body();
                    if (data != null) {
                        if (data.status == 1) {
                            if (data != null) {
                                if (data.status == 1) {
                                    DriverSystems.out.println("_)_____" + data.detail.past_booking.size());
                                    pastData.addAll(data.detail.past_booking);
                                    if (past_booking_adapter == null) {
                                        past_booking_adapter = new DriverPastBookingAdapter(getContext(), pastData);
                                        history_recyclerView.setAdapter(past_booking_adapter);
                                        if (data.detail.past_booking.size() == 0)
                                            no_data.setVisibility(View.VISIBLE);
                                        else no_data.setVisibility(View.GONE);
                                    } else {
                                        past_booking_adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    DriverCToast.ShowToast(getActivity(), data.message);
                                }
                            } else {
                                DriverCToast.ShowToast(getActivity(), DriverNC.getString(R.string.server_error));
                                isFirst = true;
                            }
                        } else {
                            isFirst = true;
                        }
                    } else {
                        DriverCToast.ShowToast(getActivity(), DriverNC.getString(R.string.server_error));
                        isFirst = true;
                    }
                } else {
                    isFirst = true;
                    DriverCToast.ShowToast(getActivity(), DriverNC.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DriverUpcomingResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                closeDialog();
                isFirst = true;
                if (getActivity() != null)
                    DriverCToast.ShowToast(getActivity(), DriverNC.getString(R.string.server_error));
            }
        }));
    }

    @Override
    public void updateUpcomingAdapter(@NonNull List<? extends DriverUpcomingResponse.PastBooking> data, int clickedPosition) {
        upComingData = new ArrayList<>();
        upComingData.addAll(data);
        if (upComingData.size() == 0) no_data.setVisibility(View.VISIBLE);
        else no_data.setVisibility(View.GONE);
        upcomingAdapter.notifyDataSetChanged();
    }
}
