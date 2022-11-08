package com.taximobility.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.adapter.PastBookingAdapter;
import com.taximobility.adapter.UpcomingAdapter;
import com.taximobility.data.apiData.ApiRequestData;
import com.taximobility.data.apiData.PastBookingResponse;
import com.taximobility.data.apiData.UpcomingResponse;
import com.taximobility.features.CToast;
import com.taximobility.service.CoreClient;
import com.taximobility.service.RetrofitCallbackClass;
import com.taximobility.util.AppController;
import com.taximobility.util.CL;
import com.taximobility.util.Colorchange;
import com.taximobility.util.NC;
import com.taximobility.util.NetworkStatus;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.LANG;

/**
 * Created by developer on 1/11/16.
 * Fragment which contains ongoing and upcoming list
 */
public class TripHistory extends Fragment {

    private static boolean UP_COMING = true;
    LinearLayout txt_up_coming_r, txt_past_booking_r;
    TextView txt_up_coming, txt_past_booking;
    RecyclerView history_recyclerView;
    private TextView no_data;
    int start = 0;
    private int limit = 10;
    private int preLast = -9;
    private LinearLayoutManager mLayoutManager;
    private List<UpcomingResponse.PastBooking> pastData = new ArrayList<>();
    private List<UpcomingResponse.PastBooking> upComingData = new ArrayList<>();
    private int prevLimt;
    private PastBookingAdapter past_booking_adapter;
    private Dialog mDialog;
    private View upcoming_underline, past_underline;
    private boolean isFirst = true;
    private boolean isFirstUpcoming = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.trip_history_lay, container, false);
        Initialize(v);
        Colorchange.ChangeColor((ViewGroup) v, getActivity());

        return v;
    }

    public void Initialize(View v) {
        txt_past_booking = v.findViewById(R.id.txt_past_booking);
        txt_up_coming = v.findViewById(R.id.txt_up_coming);
        txt_up_coming_r = v.findViewById(R.id.txt_up_coming_r);
        txt_past_booking_r = v.findViewById(R.id.txt_past_booking_r);
        history_recyclerView = v.findViewById(R.id.history_recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        history_recyclerView.setLayoutManager(mLayoutManager);
        no_data = v.findViewById(R.id.nodataTxt);

        if (getActivity() != null)
            ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.GONE);

        upcoming_underline = v.findViewById(R.id.upcoming_underline);
        past_underline = v.findViewById(R.id.past_underline);

        txt_up_coming_r.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //txt_up_coming.setBackgroundResource(R.drawable.book_select);
                // txt_past_booking.setBackgroundResource(R.drawable.book_unselect);
                HoldOnClickforasec(view);
                upcoming_underline.setBackgroundColor(CL.getResources().getColor(getActivity(), R.color.white));
                past_underline.setBackgroundColor(CL.getResources().getColor(getActivity(), R.color.black));


                txt_past_booking.setTextColor(CL.getResources().getColor(getActivity(), R.color.textviewcolor_light));
                txt_up_coming.setTextColor(CL.getResources().getColor(getActivity(), R.color.white));

//                if (isFirstUpcoming) {
//                    Systems.out.println("innnnnn "+"1st n upComing " + upComingData.size());
//                    isFirstUpcoming = false;
//                    history_recyclerView.setAdapter(null);
//                    UP_COMING = true;
//                    callUpComingData();
//                }else {
                if (!isFirstUpcoming) {
                    Systems.out.println("innnnnn " + "2nd n upComing " + upComingData.size());
                    if (upComingData.size() == 0)
                        no_data.setVisibility(View.VISIBLE);
                    else
                        no_data.setVisibility(View.GONE);

                    UP_COMING = true;
                    history_recyclerView.setAdapter(new UpcomingAdapter(getContext(), upComingData));
                }
            }
        });


        txt_past_booking.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                HoldOnClickforasec(view);
                past_underline.setBackgroundColor(CL.getResources().getColor(getActivity(), R.color.white));
                upcoming_underline.setBackgroundColor(CL.getResources().getColor(getActivity(), R.color.black));


                txt_past_booking.setTextColor(CL.getResources().getColor(getActivity(), R.color.white));
                txt_up_coming.setTextColor(CL.getResources().getColor(getActivity(), R.color.textviewcolor_light));
                if (isFirst) {
                    Systems.out.println("innnnnn " + "1st");
                    isFirst = false;
                    pastData.clear();
                    history_recyclerView.setAdapter(null);
                    past_booking_adapter = null;
                    start = 0;
                    limit = 10;
                    UP_COMING = false;
                    callPastBookingData();
                } else {
                    Systems.out.println("innnnnn " + "2nd " + pastData.size());
                    UP_COMING = false;
                    if (pastData.size() == 0)
                        no_data.setVisibility(View.VISIBLE);
                    else
                        no_data.setVisibility(View.GONE);
                    past_booking_adapter = new PastBookingAdapter(getContext(), pastData);
                    history_recyclerView.setAdapter(past_booking_adapter);
                }
            }
        });
        callUpComingData();


        history_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!UP_COMING)
                    if (dy > 0) //check for scroll down
                    {
                        int visibleItemCount = mLayoutManager.getChildCount();
                        int totalItemCount = mLayoutManager.getItemCount();
                        int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                        Log.v("...", "Last Item Wow !" + visibleItemCount + "___" + pastVisiblesItems + "___" + totalItemCount);


//                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            // loading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            if (totalItemCount >= 10 && limit >= prevLimt && totalItemCount == limit) {
                                Systems.out.println("_*____*****_" + limit + "***" + start + "***" + totalItemCount);
                                if (start == 0)
                                    start = 11;
                                else
                                    start += 10;
                                prevLimt = limit;
                                limit += 10;
                                callPastBookingData();
                            }
                        }
                        // }
                    }
            }
        });
    }

    private void HoldOnClickforasec(final View v) {
        v.setClickable(false);
        v.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setEnabled(true);
                v.setClickable(true);
            }
        }, 1200);
    }

    @Override
    public void onStop() {
        super.onStop();
        Systems.out.println("Nan BackStatck check" + " onStop TripHistory");
        closeDialog();
    }

    /**
     * request server for upcoming list items
     */
    private void callUpComingData() {
        isFirstUpcoming = false;
//        CoreClient client = new ServiceGenerator(getActivity()).createService(CoreClient.class);
        CoreClient client = AppController.getInstance().getApiManagerWithEncryptBaseUrl();
        ApiRequestData.UpcomingRequest request = new ApiRequestData.UpcomingRequest();
        request.setId(SessionSave.getSession(PASS_ID, getActivity()));
        request.setDeviceType("2");
        request.setLimit("100");
        request.setStart("0");

        Call<UpcomingResponse> LoginResponse = client.callData(TaxiUtil.COMPANY_KEY, request, SessionSave.getSession(LANG, getActivity()));
        showDialog();
        LoginResponse.enqueue(new RetrofitCallbackClass<UpcomingResponse>(getActivity(), new Callback<UpcomingResponse>() {
            @Override
            public void onResponse(Call<UpcomingResponse> call, Response<UpcomingResponse> response) {
                Systems.out.println("callUpComingData onResponse called " + response.isSuccessful());
                closeDialog();
                if (getView() != null && response.isSuccessful()) {
                    UpcomingResponse data = response.body();
                    if (data != null) {
                        if (data.status == 1) {
                            if (upComingData.size() > 0)
                                upComingData.clear();
                            upComingData.addAll(data.detail.pending_bookings);
                            SessionSave.saveSession(TaxiUtil.CANCELLATION_FARE_APPLICABLE, data.detail.cancelfare_applicable != null && data.detail.cancelfare_applicable.equals("1"), requireContext());

                            if (data.detail.pending_bookings == null)
                                no_data.setVisibility(View.VISIBLE);
                            else {
                                if (data.detail.pending_bookings.size() == 0)
                                    no_data.setVisibility(View.VISIBLE);
                                else {
                                    history_recyclerView.setAdapter(new UpcomingAdapter(getContext(), upComingData));

                                    no_data.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            CToast.ShowToast(getActivity(), data.message);
                            isFirstUpcoming = true;
                        }
                    } else {
                        isFirstUpcoming = true;
                        CToast.ShowToast(getActivity(), NC.getString(R.string.server_error));
                    }
                } else {
                    isFirstUpcoming = true;
                    CToast.ShowToast(getActivity(), NC.getString(R.string.server_error));
                }


            }

            @Override
            public void onFailure(Call<UpcomingResponse> call, Throwable t) {
                Systems.out.println("callUpComingData onFailure called " + t.getMessage());
                t.printStackTrace();
                closeDialog();
                CToast.ShowToast(getActivity(), NC.getString(R.string.server_error));
                isFirstUpcoming = true;
                // (getActivity() != null)
                //((MainActivity) getActivity()).closeProgressDialog();
            }
        }));
    }

    public void showDialog() {
        try {
            if (NetworkStatus.isOnline(getActivity())) {
                View view = View.inflate(getActivity(), R.layout.progress_bar, null);
                mDialog = new Dialog(getActivity(), R.style.dialogwinddow);
                mDialog.setContentView(view);
                mDialog.setCancelable(false);
                mDialog.show();

                ImageView iv = mDialog.findViewById(R.id.giff);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                Glide.with(getActivity())
                        .load(R.raw.loading_anim)
                        .into(imageViewTarget);
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

        }
    }

    /**
     * this method is used to call pastbooking api
     */

    private void callPastBookingData() {
//        CoreClient client = new ServiceGenerator(getActivity()).createService(CoreClient.class);
        CoreClient client = AppController.getInstance().getApiManagerWithEncryptBaseUrl();
        ApiRequestData.PastBookingRequest request = new ApiRequestData.PastBookingRequest();
        request.setPassenger_id(SessionSave.getSession(PASS_ID, getActivity()));
        request.setDevice_type("2");
        request.setLimit("10");
        request.setStart(String.valueOf(start));
        request.setMonth("12");
        request.setYear("2016");
        // Systems.out.println("_*____****_"+limit+"***"+start);
        Call<PastBookingResponse> LoginResponse = client.callData(TaxiUtil.COMPANY_KEY, request, SessionSave.getSession(LANG, getActivity()));
        showDialog();
        LoginResponse.enqueue(new RetrofitCallbackClass<PastBookingResponse>(getActivity(), new Callback<PastBookingResponse>() {
            @Override
            public void onResponse(Call<PastBookingResponse> call, Response<PastBookingResponse> response) {
                closeDialog();
                if (getView() != null && response.isSuccessful()) {
                    PastBookingResponse data = response.body();

                    if (data != null) {
                        if (data.status == 1) {
                            if (data.trip_details != null) {
                                if(pastData.size() > 0)
                                    pastData.clear();
                                pastData.addAll(data.trip_details);
                                if (past_booking_adapter == null) {
                                    past_booking_adapter = new PastBookingAdapter(getContext(), pastData);
                                    history_recyclerView.setAdapter(past_booking_adapter);
                                    if (data.trip_details.size() == 0)
                                        no_data.setVisibility(View.VISIBLE);
                                    else
                                        no_data.setVisibility(View.GONE);
                                } else
                                    past_booking_adapter.notifyDataSetChanged();
                            } else
                                no_data.setVisibility(View.VISIBLE);
                        } else {
                            CToast.ShowToast(getActivity(), data.message);
                            isFirst = true;
                        }
                    } else {
                        isFirst = true;
                        CToast.ShowToast(getActivity(), NC.getString(R.string.server_error));
                    }
                } else {
                    isFirst = true;
                    CToast.ShowToast(getActivity(), NC.getString(R.string.server_error));
                }

            }

            @Override
            public void onFailure(Call<PastBookingResponse> call, Throwable t) {
                t.printStackTrace();
                closeDialog();
                isFirst = true;
                CToast.ShowToast(getActivity(), NC.getString(R.string.server_error));
            }
        }));
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("onResume", "onResume");

        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.mybookings));
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);

        ((MainHomeFragmentActivity) getActivity()).left_img.setVisibility(View.VISIBLE);
    }
}
