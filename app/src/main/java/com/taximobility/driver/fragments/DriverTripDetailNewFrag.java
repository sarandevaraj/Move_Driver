package com.taximobility.driver.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taximobility.ProfileImageSetupClass;
import com.squareup.picasso.Picasso;
import com.taximobility.R;
import com.taximobility.driver.DriverChatWebviewAct;
import com.taximobility.driver.DriverMapZoomAct;
import com.taximobility.driver.DriverTripHistoryAct;
import com.taximobility.driver.adapter.AddonsAdapter;
import com.taximobility.driver.data.apiData.DriverApiRequestData;
import com.taximobility.driver.data.apiData.DriverTripDetailResponse;
import com.taximobility.driver.pdview.DriverPickupDropView;
import com.taximobility.driver.route.DriverStopData;
import com.taximobility.driver.service.DriverCoreClient;
import com.taximobility.driver.service.DriverRetrofitCallbackClass;
import com.taximobility.driver.service.DriverServiceGenerator;
import com.taximobility.driver.utils.DirverColorchange;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverRoundedImageView;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.util.AppController;
import com.taximobility.util.SessionSave;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by developer on 2/11/16.
 */


/**
 * This class is used to show driver trip details
 */
public class DriverTripDetailNewFrag extends Fragment {
    private TextView details_trip_id, distance, dfare,
            vdfare, waiting, wcost, vWait, sub, tax, promo, total,
            wallet, cash, tips, min_fare, min_fare_per, min_total_fare, pay_type;
    private String trip_id = " ";
    private ImageView trip_map_view;
    private DriverRoundedImageView driverImg;
    private TextView fares;
    private ImageView driverRat;
    private LinearLayout tripdetails, help_lay, paymentByLayout;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private TextView paid_type_txt;
    private View eve_night_sep;
    private LinearLayout loading;
    private TextView user;
    private TextView night_fare;
    private TextView evefare;
    private LinearLayout distance_lay;
    private LinearLayout miniutes_lay;
    private LinearLayout night_lay, evening_lay, promo_lay, tips_lay;
    private TextView tax_label;
    private TextView distance_fare_txt;
    private TextView payment_type_c;
    private RecyclerView rc_addons;

    //outstation receipt variables
    private TextView tv_additional_time;
    private TextView tv_additional_distance;
    private LinearLayout layoutNormal, normal_trip_lay, outstation_trip_lay;
    private LinearLayout layoutOutstation;

    private TextView baseFare, waitingFare, walletAmount, paidAmount, paymentType;
    private TextView night_val, evefare_val, promotion_val, tax_val, taxLabel, tripcost_val, subtotal_val, nettotal_val, trip_type, additional_dist_lable;

    private LinearLayout Nightfare;
    private LinearLayout Eveningfare;
    private int count = 0;
    private TextView trip_total_amount;
    private String mapImageUri = "";
    private TextView base_fare;
    private LinearLayout base_fare_lay;
    private LinearLayout addons_lay, order_details;
    private TextView tv_total_addon_fare;
    private TextView v_product_name, v_product_weight, v_product_size, v_name, v_date_time, order_description_details;

    private DriverPickupDropView pickUpDropLayout;
    private TextView txt_pickup, txt_drop;
    private boolean isFromFareScreen = false;
    private DriverTripDetailResponse tripDetailResponse;
    private AddonsAdapter addonsAdapter;
    private TextView cancel_fee;
    private LinearLayout cancelFareLay;
    //for outstation and rental
    private TextView cancelFeeVal,tv_delivery_fare;
    private LinearLayout cancelFareLayOut, walletLayout, menu_chat_helpline,ll_delivery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle mBundle = getArguments();
            isFromFareScreen = mBundle.getBoolean("isFromFareScreen");
            trip_id = mBundle.getString("trip_id");
            Type type = new TypeToken<DriverTripDetailResponse>() {
            }.getType();
            String response = mBundle.getString("tripDetailResponse");
            tripDetailResponse = new Gson().fromJson(response, type);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.driver_include_details, container, false);
        loading = v.findViewById(R.id.loading);
        DirverColorchange.ChangeColor((ViewGroup) v, getActivity());
        ImageView iv = v.findViewById(R.id.giff);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
        Glide.with(getActivity())
                .load(R.raw.driver_loading_anim)
                .into(imageViewTarget);
        //    pickUpDropLayout = v.findViewById(R.id.pd_view);
        tv_additional_time = v.findViewById(R.id.additonal_time_fare);
        tv_additional_distance = v.findViewById(R.id.additonal_distance_fare);
        walletAmount = v.findViewById(R.id.WalletAmt);
        paidAmount = v.findViewById(R.id.PaidAmt);
        paymentType = v.findViewById(R.id.Paymenttype);
        night_val = v.findViewById(R.id.night_val);
        evefare_val = v.findViewById(R.id.evefare_val);
        tripcost_val = v.findViewById(R.id.tripcost_val);
        subtotal_val = v.findViewById(R.id.subtotal_val);
        nettotal_val = v.findViewById(R.id.nettotal_val);
        trip_type = v.findViewById(R.id.details_trip_type);
        additional_dist_lable = v.findViewById(R.id.additonal_distance_lable);
        cancel_fee = v.findViewById(R.id.cancel_fee);
        cancelFareLay = v.findViewById(R.id.cancelFareLay);
        //for outstation and rental
        cancelFeeVal = v.findViewById(R.id.cancelFeeVal);
        cancelFareLayOut = v.findViewById(R.id.cancelFareLayOut);
        //    walletLayout = v.findViewById(R.id.walletLayout);

        layoutNormal = v.findViewById(R.id.layout_normalreceipt);
        layoutOutstation = v.findViewById(R.id.layout_outstationreceipt);
        baseFare = v.findViewById(R.id.BaseFare);
        normal_trip_lay = v.findViewById(R.id.normal_trip_lay);
        outstation_trip_lay = v.findViewById(R.id.outstation_trip_lay);
        paid_type_txt = v.findViewById(R.id.paid_type_txt);
        promotion_val = v.findViewById(R.id.promotion_val);
        tax_val = v.findViewById(R.id.tax_val);
        taxLabel = v.findViewById(R.id.taxLabel);
        v_product_name = v.findViewById(R.id.v_product_name);
        v_product_weight = v.findViewById(R.id.v_product_weight);
        v_product_size = v.findViewById(R.id.v_product_size);
        v_name = v.findViewById(R.id.v_name);
        v_date_time = v.findViewById(R.id.v_date_time);
        order_description_details = v.findViewById(R.id.order_description_details);
        tv_total_addon_fare = v.findViewById(R.id.tv_total_addon_fare);
        addons_lay = v.findViewById(R.id.addons_lay);
        order_details = v.findViewById(R.id.order_details);
        rc_addons = v.findViewById(R.id.rc_addons);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rc_addons.setLayoutManager(layoutManager);

        distance_lay = v.findViewById(R.id.distance_lay);
        miniutes_lay = v.findViewById(R.id.miniutes_lay);
        distance_fare_txt = v.findViewById(R.id.distance_fare_txt);
        driverImg = v.findViewById(R.id.driverImg);
        trip_map_view = v.findViewById(R.id.trip_map_view);
        night_lay = v.findViewById(R.id.night_lay);
        evening_lay = v.findViewById(R.id.evening_lay);
        promo_lay = v.findViewById(R.id.promo_lay);
        tips_lay = v.findViewById(R.id.tips_lay);
        driverRat = v.findViewById(R.id.rating);
        // tripdetails = v.findViewById(R.id.tripdetails);
        paymentByLayout = v.findViewById(R.id.paymentByLayout);
        help_lay = v.findViewById(R.id.help_lay);
        user = v.findViewById(R.id.user);
        details_trip_id = v.findViewById(R.id.details_trip_id);
        distance = v.findViewById(R.id.dist);
        dfare = v.findViewById(R.id.dfare);
        vdfare = v.findViewById(R.id.vehicle_detail_fare);
        waiting = v.findViewById(R.id.wait);
        wcost = v.findViewById(R.id.wcost);
        vWait = v.findViewById(R.id.vwcost);
        sub = v.findViewById(R.id.sTotal);
        tax = v.findViewById(R.id.tax);
        tax_label = v.findViewById(R.id.tax_label);
        promo = v.findViewById(R.id.promo);
        total = v.findViewById(R.id.total);
        wallet = v.findViewById(R.id.wallet);
        cash = v.findViewById(R.id.cash);
        tips = v.findViewById(R.id.tips);
        eve_night_sep = v.findViewById(R.id.eve_night_sep);
        fares = v.findViewById(R.id.fares);
        min_fare = v.findViewById(R.id.min_fare);
        min_fare_per = v.findViewById(R.id.min_fare_per);
        min_total_fare = v.findViewById(R.id.min_total_fare);
        pay_type = v.findViewById(R.id.pay_type);
        Nightfare = v.findViewById(R.id.night_farelay);
        Eveningfare = v.findViewById(R.id.evening_farelay);
        base_fare = v.findViewById(R.id.base_fare);
        base_fare_lay = v.findViewById(R.id.base_fare_lay);
        evefare = v.findViewById(R.id.evefare);
        night_fare = v.findViewById(R.id.night_fare);
        View bottomSheet = v.findViewById(R.id.tripdetails_scroll);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        //   payment_type_c = v.findViewById(R.id.payment_type_c);

        txt_pickup = v.findViewById(R.id.txt_pickup);
        txt_drop = v.findViewById(R.id.txt_drop);
        trip_total_amount = v.findViewById(R.id.trip_total_amount);
        menu_chat_helpline = v.findViewById(R.id.menu_chat_helpline);
        ll_delivery = v.findViewById(R.id.ll_delivery);
        tv_delivery_fare = v.findViewById(R.id.tv_delivery_fare);





      /*  driverImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count < 10) {
                    count++;
                } else {
                    Intent i = new Intent(getActivity(), DummyActivity.class);
                    i.putExtra("trip_id", trip_id);
                    startActivity(i);
                    count = 0;
                }

            }
        });*/

        menu_chat_helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(requireActivity(), DriverChatWebviewAct.class);
                in.putExtra("trip_id", trip_id);
                in.putExtra("type",  "2");
                startActivity(in);
            }
        });

        if (isFromFareScreen) {
            mBottomSheetBehavior.setHideable(false);
            bottomSheet.post(() -> {
                mBottomSheetBehavior.setPeekHeight(bottomSheet.getHeight());
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            });
            showFare(tripDetailResponse);
        } else
            callDetail();

        trip_map_view.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), DriverMapZoomAct.class);
            intent.putExtra("IMAGE_URI", mapImageUri);
            getActivity().startActivity(intent);
        });
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();


        try {

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * TripDetail method API call and response parsing.
     */
    private void callDetail() {
        DriverCoreClient client = AppController.getInstance().getApiManagerWithEncryptBaseUrl_driver();
        DriverApiRequestData.getTripDetailRequest request = new DriverApiRequestData.getTripDetailRequest();
        request.setTrip_id(trip_id);

        Call<DriverTripDetailResponse> LoginResponse = client.callData(DriverServiceGenerator.COMPANY_KEY, request, DriverSessionSave.getSession("Lang", getActivity()));
        LoginResponse.enqueue(new DriverRetrofitCallbackClass<>(getActivity(), new Callback<DriverTripDetailResponse>() {
            @Override
            public void onResponse(Call<DriverTripDetailResponse> call, Response<DriverTripDetailResponse> response) {
                loading.setVisibility(View.GONE);
                if (getView() != null && response.isSuccessful()) {
                    DriverTripDetailResponse data = response.body();
                    if (data != null) {
                        if (data.status == 1) {
                            //   tripdetails.setOnClickListener(view -> mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED));
                            if (data.detail.trip_type.equals("3") || data.detail.trip_type.equals("2")) {
                                normal_trip_lay.setVisibility(View.GONE);
                                outstation_trip_lay.setVisibility(View.VISIBLE);
                                layoutNormal.setVisibility(View.GONE);
                                layoutOutstation.setVisibility(View.VISIBLE);
                                Nightfare.setVisibility(View.GONE);
                                Eveningfare.setVisibility(View.GONE);

                                base_fare_lay.setVisibility(View.GONE);

                                if (data.detail.trip_type.equals("3"))
                                    trip_type.setText(DriverNC.getString(R.string.trip_type_outstation));//+ " Trip"
                                else if (data.detail.trip_type.equals("2"))
                                    trip_type.setText(DriverNC.getString(R.string.trip_type_rental));//+ " Trip"

                                additional_dist_lable.setText(DriverNC.getString(R.string.additonal_distance_fare) + " " + data.detail.metric.toLowerCase());

                                tv_additional_distance.setText(DriverSessionSave.getSession("site_currency", getActivity()) + "" + data.detail.additional_distance_fare);
                                tv_additional_time.setText(DriverSessionSave.getSession("site_currency", getActivity()) + "" + data.detail.additional_time_fare);
                                baseFare.setText(DriverSessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(data.detail.base_fare)));

                                subtotal_val.setText(DriverSessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", Float.parseFloat(data.detail.additional_distance_fare) + Float.valueOf(data.detail.additional_time_fare) + Float.parseFloat(data.detail.base_fare)));
                                promotion_val.setText("- " + DriverSessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(data.detail.promotion)));

                                float subTotal = Float.parseFloat(data.detail.additional_distance_fare) + Float.parseFloat(data.detail.additional_time_fare) + Float.parseFloat(data.detail.base_fare);
                                float withoutPromo = Float.parseFloat(data.detail.promotion);
                                tripcost_val.setText(DriverSessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", (subTotal - withoutPromo)));
                                tax_val.setText(DriverSessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(data.detail.tax_fare)));
                                taxLabel.setText(DriverNC.getString(R.string.Tax) + " (" + data.detail.tax_percentage + "%)");
                                nettotal_val.setText(DriverSessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", ((subTotal - withoutPromo)) + (Float.parseFloat(data.detail.tax_fare))));
                                walletAmount.setText("- " + DriverSessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(data.detail.used_wallet_amount)));

                                paidAmount.setText(DriverSessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", (Float.valueOf(data.detail.paid_amount))));
                                paymentType.setText(data.detail.payment_type_label);

                                details_trip_id.setText(DriverNC.getString(R.string.trip_id) + ": " + data.detail.trip_id);
                                user.setText(data.detail.driver_name);
                                fares.setText(DriverSessionSave.getSession("site_currency", getActivity()) + data.detail.paid_amount);
                                //   Picasso.get().load(data.detail.driver_image).resize(100, 100).into(driverImg);

                                if (data.detail.driver_image != null && data.detail.driver_image.length() > 0) {
                                    Picasso.get().load(data.detail.driver_image).placeholder(getResources().getDrawable(R.drawable.driver_loadingimage)).error(getResources().getDrawable(R.drawable.driver_noimage)).into(driverImg);
                                } else {
                                    if (data.detail.driver_name != "") {
                                        ProfileImageSetupClass.setupProfileImage(
                                                data.detail.driver_name, driverImg
                                        );
                                    } else {
                                        Picasso.get().load(R.drawable.loadingimage).into(driverImg);
                                    }
                                }
                                Picasso.get().load(data.detail.map_image).into(trip_map_view);
                                mapImageUri = data.detail.map_image;

                                createPickAndStopView(data.detail.current_location, data.detail.pickup_latitude, data.detail.pickup_longitude, data.detail.drop_location, data.detail.drop_latitude, data.detail.drop_longitude);

                                if (data.detail.pending_cancel_amount != null && (Float.parseFloat(data.detail.pending_cancel_amount) > 0.0)) {
                                    cancelFeeVal.setText(DriverSessionSave.getSession("site_currency", getActivity()) + (data.detail.pending_cancel_amount));
                                } else {
                                    cancelFareLayOut.setVisibility(View.GONE);
                                }


                                int driver_rating = (int) Float.parseFloat(data.detail.rating);
                                if (driver_rating == 0)
                                    driverRat.setImageResource(R.drawable.driver_star6);
                                else if (driver_rating == 1)
                                    driverRat.setImageResource(R.drawable.driver_star1);
                                else if (driver_rating == 2)
                                    driverRat.setImageResource(R.drawable.driver_star2);
                                else if (driver_rating == 3)
                                    driverRat.setImageResource(R.drawable.driver_star3);
                                else if (driver_rating == 4)
                                    driverRat.setImageResource(R.drawable.driver_star4);
                                else if (driver_rating == 5)
                                    driverRat.setImageResource(R.drawable.driver_star5);

                            } else {
                                showFare(data);

                            }

                            if (data.detail.payment_type != null && data.detail.payment_type.equals("5")) {
                                //  walletLayout.setVisibility(View.VISIBLE);
                            } else {
                                //  walletLayout.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(getActivity(), data.message, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), DriverNC.getString(R.string.please_check_internet), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), DriverNC.getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DriverTripDetailResponse> call, Throwable t) {
                t.printStackTrace();
                loading.setVisibility(View.GONE);
                Toast.makeText(getActivity(), DriverNC.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        }));
    }

    private void showFare(DriverTripDetailResponse data) {


        if (data.detail.payment_type == "1") {
            paid_type_txt.setText("Cash");
        } else if (data.detail.payment_type == "2") {
            paid_type_txt.setText("card");
        } else if (data.detail.payment_type == "3") {
            paid_type_txt.setText("New Card");
        } else if (data.detail.payment_type == "5") {
            paid_type_txt.setText("Wallet");
        }

        txt_pickup.setText(data.detail.current_location);
        txt_drop.setText(data.detail.drop_location);
//        txt_pickup.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        txt_pickup.setSingleLine(true);
//        txt_pickup.setMarqueeRepeatLimit(-1);
//        txt_pickup.setSelected(true);
//
//        txt_drop.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        txt_drop.setSingleLine(true);
//        txt_drop.setMarqueeRepeatLimit(-1);
//        txt_drop.setSelected(true);

        ((DriverTripHistoryAct) getActivity()).setTitle("Trip Details #"+data.detail.trip_id);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        loading.setVisibility(View.GONE);
        normal_trip_lay.setVisibility(View.VISIBLE);
        outstation_trip_lay.setVisibility(View.GONE);
        layoutNormal.setVisibility(View.VISIBLE);
        layoutOutstation.setVisibility(View.GONE);
        trip_type.setText(DriverNC.getString(R.string.trip_type_normal));//+ " Trip"
        details_trip_id.setText(DriverNC.getString(R.string.trip_id) + ": " + data.detail.trip_id);
        user.setText(data.detail.passenger_name);
        fares.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + data.detail.amt);

//        if (data.detail.passenger_image != null && !data.detail.passenger_image.isEmpty())
//            Picasso.get().load(data.detail.passenger_image).into(driverImg);

        if (data.detail.passenger_image != null && data.detail.passenger_image.length() > 0) {
            Picasso.get().load(data.detail.passenger_image).placeholder(getResources().getDrawable(R.drawable.driver_loadingimage)).error(getResources().getDrawable(R.drawable.driver_noimage)).into(driverImg);
        } else {
            if (data.detail.passenger_name != "") {
                ProfileImageSetupClass.setupProfileImage(
                        data.detail.passenger_name, driverImg
                );
            } else {
                Picasso.get().load(R.drawable.loadingimage).into(driverImg);
            }
        }

        mapImageUri = data.detail.map_image;
        if (mapImageUri != null && !mapImageUri.isEmpty())
            Picasso.get().load(mapImageUri).into(trip_map_view);

        distance.setText(data.detail.distance + " " + data.detail.metric.toLowerCase());
        base_fare.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + data.detail.new_base_fare);

        dfare.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + data.detail.new_distance_fare);
        vdfare.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.distance_fare));
        min_fare.setText(data.detail.trip_minutes + " " + DriverNC.getString(R.string.mins));
        min_fare_per.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + data.detail.fare_per_minute);
        min_total_fare.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.minutes_fare));
        waiting.setText(String.valueOf(data.detail.waiting_time));


        if (data.detail.payment_type_label != null) {
            //   payment_type_c.setText(data.detail.payment_type_label);
            pay_type.setText(data.detail.payment_type_label);
            if (data.detail.payment_type_label.trim().equalsIgnoreCase(DriverNC.getString(R.string.cash).trim())) {
                //       payment_type_c.setTextColor(DriverCL.getResources().getColor(R.color.pastbookingcashtext));

            } else if (data.detail.payment_type_label.trim().equalsIgnoreCase(DriverNC.getString(R.string.wallet).trim())) {
                //     payment_type_c.setTextColor(DriverCL.getResources().getColor(R.color.pastbookingcashtext));
            } else {
                //    payment_type_c.setTextColor(DriverCL.getResources().getColor(R.color.pastbookingcard));
            }
        } else {
            //  payment_type_c.setVisibility(View.GONE);
            pay_type.setVisibility(View.GONE);
        }

        if (data.detail.preferences != null && data.detail.preferences.size() > 0) {
            addons_lay.setVisibility(View.VISIBLE);
            addonsAdapter = new AddonsAdapter(getActivity(), data.detail.preferences, 2);
            rc_addons.setAdapter(addonsAdapter);
            if (!TextUtils.isEmpty(data.detail.total_preference_fare) && Double.parseDouble(data.detail.total_preference_fare) > 0) {
                tv_total_addon_fare.setText(SessionSave.getSession("site_currency", getActivity()) + " " + data.detail.total_preference_fare);
            }
        } else {
            addons_lay.setVisibility(View.GONE);
        }
        if (data.detail.service_id.equals("2")) {
            order_details.setVisibility(View.VISIBLE);
        } else {
            order_details.setVisibility(View.GONE);
        }


        if (data.detail.delivery_fare != null && !TextUtils.isEmpty(data.detail.delivery_fare) && Double.parseDouble(data.detail.delivery_fare) > 0.0) {
            tv_delivery_fare.setText(SessionSave.getSession("site_currency", getActivity()) + (data.detail.delivery_fare));
            ll_delivery.setVisibility(View.VISIBLE);
        } else
            ll_delivery.setVisibility(View.GONE);


        v_product_name.setText(data.detail.product_name);
        v_product_weight.setText(data.detail.product_weight);
        v_product_size.setText(data.detail.product_size);
        v_name.setText(data.detail.delivery_person_name);
        v_date_time.setText(data.detail.delivery_date_time);
        order_description_details.setText(data.detail.delivery_notes);

        wcost.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + data.detail.waiting_fare_minutes);
        vWait.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.waiting_fare));
        sub.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.subtotal));
        tax.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.tax_fare));
        tax_label.setText(DriverNC.getString(R.string.Tax) + " (" + data.detail.tax_percentage + "%)");

        promo.setText("- " + DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.promocode_fare));
        total.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.amt));
        trip_total_amount.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.amt));
        wallet.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.used_wallet_amount));

        if (data.detail.actual_paid_amount != null)
            cash.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.actual_paid_amount));
        else
            cash.setVisibility(View.GONE);

        if (data.detail.given_tips != null)
            tips.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.given_tips));
        else {
            tips_lay.setVisibility(View.GONE);
        }

        if (data.detail.pending_cancel_amount != null && (Float.parseFloat(data.detail.pending_cancel_amount) > 0.0)) {
            cancel_fee.setText(DriverSessionSave.getSession("site_currency", getActivity()) + (data.detail.pending_cancel_amount));
        } else {
            cancelFareLay.setVisibility(View.GONE);
        }
        if (data.detail.min_distance_status == 0)
            distance_fare_txt.setText(DriverNC.getString(R.string.dist_fare) + " " + DriverNC.getString(R.string.per) + " " + data.detail.metric.toLowerCase());
        else {
            distance_fare_txt.setText(DriverNC.getString(R.string.minimum_fare));
            base_fare_lay.setVisibility(View.GONE);
        }

        if (night_fare != null) {
            night_fare.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.nightfare));
            evefare.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.eveningfare));
        }
        if (data.detail.fare_calculation_type != null) {
            if (data.detail.fare_calculation_type.trim().equals("1"))
                miniutes_lay.setVisibility(View.GONE);
            else if (data.detail.fare_calculation_type.trim().equals("2"))
                distance_lay.setVisibility(View.GONE);
        }
        if (data.detail.nightfare != null && !data.detail.nightfare.isEmpty()) {
            night_lay.setVisibility(View.VISIBLE);
            if (Double.parseDouble(data.detail.nightfare) <= 0.0)
                night_lay.setVisibility(View.GONE);
            if (data.detail.eveningfare != null && !data.detail.eveningfare.isEmpty()) {
                if (Double.parseDouble(data.detail.nightfare) <= 0.0 && Double.parseDouble(data.detail.eveningfare) <= 0.0) {
                    eve_night_sep.setVisibility(View.GONE);
                }
            } else
                eve_night_sep.setVisibility(View.GONE);

        } else {
            eve_night_sep.setVisibility(View.GONE);
            night_lay.setVisibility(View.GONE);
        }

        if (data.detail.eveningfare != null && !data.detail.eveningfare.isEmpty() && Double.parseDouble(data.detail.eveningfare) > 0.0) {
            evening_lay.setVisibility(View.VISIBLE);
        } else
            evening_lay.setVisibility(View.GONE);

        if (data.detail.promocode_fare != null && !data.detail.promocode_fare.isEmpty() && !data.detail.promocode_fare.equals("0") && !data.detail.promocode_fare.equals("0.00")) {
            promo_lay.setVisibility(View.VISIBLE);
        } else promo_lay.setVisibility(View.GONE);

        ArrayList<DriverStopData> stops = data.detail.stops;

      /*  if (stops != null && stops.size() > 0)
            pickUpDropLayout.setData(stops, "ONGOING", DriverSessionSave.getSession("Lang", getActivity()));
        else if (!isFromFareScreen)
            createPickAndStopView(data.detail.current_location, data.detail.pickup_latitude, data.detail.pickup_longitude, data.detail.drop_location, data.detail.drop_latitude, data.detail.drop_longitude);
*/

        if (data.detail.payment_type != null) {
            if (data.detail.payment_type.equals("1"))
                cash.setVisibility(View.VISIBLE);
                //     pay_type.setCompoundDrawablesWithIntrinsicBounds(R.drawable.driver_cash, 0, 0, 0);
            else if (data.detail.payment_type.equals("5")) {
                //   pay_type.setCompoundDrawablesWithIntrinsicBounds(R.drawable.driver_cash, 0, 0, 0);
                pay_type.setVisibility(View.GONE);
                cash.setVisibility(View.GONE);
            } else
                pay_type.setCompoundDrawablesWithIntrinsicBounds(R.drawable.driver_credit_card, 0, 0, 0);
        } else if (isFromFareScreen)
            paymentByLayout.setVisibility(View.GONE);


        if (data.detail.corporate_booking != null && data.detail.corporate_booking.equalsIgnoreCase("1") && !isFromFareScreen) {
            paymentByLayout.setVisibility(View.VISIBLE);
            pay_type.setText(getString(R.string.corporate));
            //   payment_type_c.setText(getString(R.string.corporate));
            pay_type.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }


        if (data.detail.rating != null) {
            int driver_rating = (int) Float.parseFloat(data.detail.rating);
            if (driver_rating == 0)
                driverRat.setImageResource(R.drawable.driver_star6);
            else if (driver_rating == 1)
                driverRat.setImageResource(R.drawable.driver_star1);
            else if (driver_rating == 2)
                driverRat.setImageResource(R.drawable.driver_star2);
            else if (driver_rating == 3)
                driverRat.setImageResource(R.drawable.driver_star3);
            else if (driver_rating == 4)
                driverRat.setImageResource(R.drawable.driver_star4);
            else if (driver_rating == 5)
                driverRat.setImageResource(R.drawable.driver_star5);
        } else
            driverRat.setVisibility(View.GONE);

    }

    /**
     * Method to create views dynamically if ArrayList<StopData> value not available (ie., Normal flow)
     * <p>
     * New ArrayList of StopData values created with pickup and drop(if available) and dynamic views created based on that ArrayList
     *
     * @param pickup_location
     * @param pickup_latitude
     * @param pickup_longitude
     * @param drop_location
     * @param drop_latitude
     * @param drop_longitude
     */
    private void createPickAndStopView(String pickup_location, String pickup_latitude, String pickup_longitude, String drop_location, String drop_latitude, String drop_longitude) {
        ArrayList<DriverStopData> pickUpDropList = new ArrayList<>();
        DriverStopData pickUpData = new DriverStopData(0, Double.parseDouble(pickup_latitude), Double.parseDouble(pickup_longitude), pickup_location, "", "");
        pickUpDropList.add(pickUpData);
        if (drop_location != null && !drop_location.isEmpty()) {
            DriverStopData dropData = new DriverStopData((1 + new Random().nextInt()), Double.parseDouble(drop_latitude), Double.parseDouble(drop_longitude), drop_location, "", "");
            pickUpDropList.add(dropData);
        }
      /*  if (getActivity() != null)
            pickUpDropLayout.setData(pickUpDropList, "ONGOING", DriverSessionSave.getSession("Lang", getActivity()));*/
    }
}