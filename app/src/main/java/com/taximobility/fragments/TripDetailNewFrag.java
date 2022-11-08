package com.taximobility.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taximobility.ProfileImageSetupClass;
import com.squareup.picasso.Picasso;
import com.taximobility.ChatWebviewAct;
import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.MapZoomAct;
import com.taximobility.R;
import com.taximobility.data.SplitStatusData;
import com.taximobility.data.apiData.ApiRequestData;
import com.taximobility.data.apiData.TripDetailResponse;
import com.taximobility.driver.adapter.AddonsAdapter;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.features.CToast;
import com.taximobility.locationSearch.PlacesData;
import com.taximobility.pdview.PickupDropView;
import com.taximobility.service.CoreClient;
import com.taximobility.service.RetrofitCallbackClass;
import com.taximobility.util.AppController;
import com.taximobility.util.CL;
import com.taximobility.util.NC;
import com.taximobility.util.RoundedImageView;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.taximobility.util.ConstantsKt.LANG;
import static com.taximobility.util.ConstantsKt.PASS_ID;


/**
 * Created by developer on 2/11/16.
 * class for trip detail page directed from trip history
 */
public class TripDetailNewFrag extends Fragment {
    DecimalFormat df = new DecimalFormat("####0.00");
    private TextView details_trip_id, distance, dfare,
            vdfare, waiting, wcost, vWait, sub, tax, promo, total,
            wallet, cash, tips, min_fare, min_fare_per, min_total_fare, pay_type;
    private String trip_id;
    private ImageView trip_map_view;
    private RoundedImageView driverImg;
    private TextView fares;
    private ImageView paid_type_img;
    private TextView paid_type_txt;
    private TextView txt_pickup, txt_drop;
    private TextView cancel_fee;
    private LinearLayout cancelFareLay;
    //for outstation and rental
    private TextView cancelFeeVal;
    private LinearLayout cancelFareLayOut;
    private ImageView driverRat;
    private LinearLayout help_lay;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private String title = "";
    private LinearLayout loading;
    private TextView user, rating_count;
    private TextView night_fare;
    private TextView evefare;
    private LinearLayout distance_lay;
    private LinearLayout minutes_layout;
    private LinearLayout evening_lay;
    private LinearLayout night_lay;
    private LinearLayout promo_lay;
    private FrameLayout bottomFrameLay;
    private TextView tax_label;
    private ImageView split_ref;
    private SplitFareStatusDialog splitFareDialog;
    private TextView distance_fare_txt;
    private View eve_night_sep;
    private String header_booking_time;
    private LinearLayout addons_lay, order_details;
    private RecyclerView rc_addons;
    private TextView promotion_val, tax_val, tripcost_val, subtotal_val, nettotal_val, trip_type, additional_dist_lable;
    private TextView baseFare, walletAmount, paidAmount, paymentType, taxTxt;
    private TextView trip_total_amount, tv_delivery_fare;

    //outstation receipt variables
    private TextView tv_additional_time;
    private TextView tv_additional_distance;
    private LinearLayout layoutNormal, normal_trip_lay, outstation_trip_lay;
    private LinearLayout layoutOutstation;
    private TextView tv_total_addon_fare;

    private LinearLayout Nightfare;
    private LinearLayout Eveningfare;

    private LinearLayout additonal_time_lay, additonal_distance_lay, promotion_val_lay, WalletAmt_lay;
    private LinearLayout wallet_lay;


    private String mapImageUri = "";
    private TextView base_fare;
    private LinearLayout base_fare_lay;

    private PickupDropView pickUpDropLayout;

    private boolean isFromFareScreen = false;
    private TripDetailResponse tripDetailResponse;
    RelativeLayout payby_lay;
    LinearLayout cash_lay, tips_lay, menu_chat_helpline, ll_delivery;
    private AddonsAdapter addonsAdapter;
    private TextView v_product_name, v_product_weight, v_product_size, v_name, v_date_time, order_description_details,driver_phone;

    /**
     * to convert double values to 2 decimel string ex:12.6677896 to 12.66
     *
     * @param value  -->double value
     * @param places --> how many digits after decimel
     * @return round off string
     */
    public static String round(double value, int places) {
        return String.valueOf(value);
//        if (places < 0) throw new IllegalArgumentException();
//
//        long factor = (long) Math.pow(10, places);
//        value = value * factor;
//        long tmp = Math.round(value);
//        return String.valueOf((double) tmp / factor);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle mBundle = getArguments();
            isFromFareScreen = mBundle.getBoolean("isFromFareScreen");
            trip_id = mBundle.getString("trip_id");
            Type type = new TypeToken<TripDetailResponse>() {
            }.getType();
            String response = mBundle.getString("tripDetailResponse");
            if (response != null && !response.equals("")) {
                Systems.out.println("TripDetailResponse stringAfter: " + response);
                tripDetailResponse = new Gson().fromJson(response, type);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.include_details, container, false);

        //  Colorchange.ChangeColor((ViewGroup) v, getActivity());

        loading = v.findViewById(R.id.loading);

        ImageView iv = v.findViewById(R.id.giff);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
        Glide.with(getActivity())
                .load(R.raw.loading_anim)
                .into(imageViewTarget);
        pickUpDropLayout = v.findViewById(R.id.pd_view);

        layoutNormal = v.findViewById(R.id.layout_normalreceipt);
        layoutOutstation = v.findViewById(R.id.layout_outstationreceipt);
        baseFare = v.findViewById(R.id.BaseFare);
        normal_trip_lay = v.findViewById(R.id.normal_trip_lay);
        outstation_trip_lay = v.findViewById(R.id.outstation_trip_lay);

        tv_additional_time = v.findViewById(R.id.additonal_time_fare);
        tv_additional_distance = v.findViewById(R.id.additonal_distance_fare);
        walletAmount = v.findViewById(R.id.WalletAmt);
        paidAmount = v.findViewById(R.id.PaidAmt);
        paymentType = v.findViewById(R.id.Paymenttype);
        promotion_val = v.findViewById(R.id.promotion_val);
        tax_val = v.findViewById(R.id.tax_val);
        taxTxt = v.findViewById(R.id.txt_tax);

        tripcost_val = v.findViewById(R.id.tripcost_val);
        subtotal_val = v.findViewById(R.id.subtotal_val);
        nettotal_val = v.findViewById(R.id.nettotal_val);
        trip_type = v.findViewById(R.id.details_trip_type);
        additional_dist_lable = v.findViewById(R.id.additonal_distance_lable);

        v_product_name = v.findViewById(R.id.v_product_name);
        v_product_weight = v.findViewById(R.id.v_product_weight);
        v_product_size = v.findViewById(R.id.v_product_size);
        v_name = v.findViewById(R.id.v_name);
        v_date_time = v.findViewById(R.id.v_date_time);
        order_description_details = v.findViewById(R.id.order_description_details);
        rc_addons = v.findViewById(R.id.rc_addons);
        addons_lay = v.findViewById(R.id.addons_lay);
        order_details = v.findViewById(R.id.order_details);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rc_addons.setLayoutManager(layoutManager);
        tv_total_addon_fare = v.findViewById(R.id.tv_total_addon_fare);
        tax_label = v.findViewById(R.id.tax_label);
        bottomFrameLay = v.findViewById(R.id.bottomFrameLay);
        driverImg = v.findViewById(R.id.driverImg);
        paid_type_txt = v.findViewById(R.id.paid_type_txt);
        trip_map_view = v.findViewById(R.id.trip_map_view);
        driverRat = v.findViewById(R.id.rating);
        rating_count = v.findViewById(R.id.rating_count);
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
        cancel_fee = v.findViewById(R.id.cancel_fee);
        cancelFareLay = v.findViewById(R.id.cancelFareLay);
        cancelFeeVal = v.findViewById(R.id.cancelFeeVal);
        cancelFareLayOut = v.findViewById(R.id.cancelFareLayOut);
        tax = v.findViewById(R.id.tax);
        promo = v.findViewById(R.id.promo);
        total = v.findViewById(R.id.total);
        wallet = v.findViewById(R.id.wallet);
        cash = v.findViewById(R.id.cash);
        tips = v.findViewById(R.id.tips);
        fares = v.findViewById(R.id.fares);
        min_fare = v.findViewById(R.id.min_fare);
        min_fare_per = v.findViewById(R.id.min_fare_per);
        min_total_fare = v.findViewById(R.id.min_total_fare);
        pay_type = v.findViewById(R.id.pay_type);
        trip_id = getArguments().getString("trip_id");

        base_fare = v.findViewById(R.id.base_fare);
        base_fare_lay = v.findViewById(R.id.base_fare_lay);
        payby_lay = v.findViewById(R.id.payby_lay);
        cash_lay = v.findViewById(R.id.cash_lay);
        tips_lay = v.findViewById(R.id.tips_lay);
        txt_pickup = v.findViewById(R.id.txt_pickup);
        txt_drop = v.findViewById(R.id.txt_drop);
        trip_total_amount = v.findViewById(R.id.trip_total_amount);
        menu_chat_helpline = v.findViewById(R.id.menu_chat_helpline);
        ll_delivery = v.findViewById(R.id.ll_delivery);
        tv_delivery_fare = v.findViewById(R.id.tv_delivery_fare);
        driver_phone =v.findViewById(R.id.driver_phone);


        View bottomSheet = v.findViewById(R.id.tripdetails_scroll);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        menu_chat_helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getContext(), ChatWebviewAct.class);
                in.putExtra("type", "2");
                in.putExtra("trip_id", trip_id);
                startActivityForResult(in, 101);
            }
        });


        TextView help_txt = v.findViewById(R.id.help_txt);
        help_txt.setTextColor(CL.getResources().getColor(getActivity(), R.color.button_accept));
        help_txt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Fragment ff = new HelpFrag();
                Bundle bb = new Bundle();
                bb.putString("trip_id", trip_id);
                bb.putString("title", title);
                ff.setArguments(bb);
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, ff).addToBackStack(null).commit();
            }
        });
        dropVisible(v);

        if (isFromFareScreen) {
            mBottomSheetBehavior.setHideable(false);
            bottomSheet.post(new Runnable() {
                @Override
                public void run() {
                    mBottomSheetBehavior.setPeekHeight(bottomSheet.getHeight());
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            });
            if (tripDetailResponse != null)
                showFare(tripDetailResponse);
            else
                callDetail();
        } else
            callDetail();
        return v;
    }


    public void showFare(TripDetailResponse data) {
        System.out.println("tripDetailResponse : "+data.message);

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


        header_booking_time = data.detail.booking_time;
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(header_booking_time);

        loading.setVisibility(View.GONE);
        if (isFromFareScreen) {
            payby_lay.setVisibility(View.GONE);
            wallet_lay.setVisibility(View.GONE);
            cash_lay.setVisibility(View.GONE);
        } else {
            payby_lay.setVisibility(View.VISIBLE);
            wallet_lay.setVisibility(View.VISIBLE);
            cash_lay.setVisibility(View.VISIBLE);
        }


        normal_trip_lay.setVisibility(View.VISIBLE);
        outstation_trip_lay.setVisibility(View.GONE);
        layoutNormal.setVisibility(View.VISIBLE);
        layoutOutstation.setVisibility(View.GONE);
        trip_type.setText(NC.getString(R.string.trip_type_normal));//+" Trip"
        details_trip_id.setText(NC.getString(R.string.trip_id) + ": " + data.detail.trip_id);
        user.setText(data.detail.driver_name);
        fares.setText(SessionSave.getSession("Currency", getActivity()) + data.detail.amt);
        //  Picasso.get().load(data.detail.driver_image).resize(100, 100).into(driverImg);

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
        mapImageUri = data.detail.map_image;
        if (!mapImageUri.equals(""))
            Picasso.get().load(mapImageUri).into(trip_map_view);

        distance.setText(data.detail.distance + " " + data.detail.metric.toLowerCase());
        driver_phone.setText(data.detail.driver_phone);
        base_fare.setText(SessionSave.getSession("Currency", getActivity()) + " " + data.detail.new_base_fare);
        dfare.setText(SessionSave.getSession("Currency", getActivity()) + data.detail.new_distance_fare);

        vdfare.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.distance_fare));
        min_fare.setText(data.detail.trip_minutes + " " + NC.getString(R.string.mins));
        if (data.detail.trip_minutes.equals("0")) {
            minutes_layout.setVisibility(View.GONE);
        } else {
            minutes_layout.setVisibility(View.VISIBLE);
        }
        min_fare_per.setText(SessionSave.getSession("Currency", getActivity()) + data.detail.fare_per_minute);
        min_total_fare.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.minutes_fare));
        waiting.setText(data.detail.waiting_time);
        // wcost.setText(SessionSave.getSession("Currency", getActivity()) + data.detail.waiting_fare_minutes + "/" + NC.getString(R.string.hour));

        wcost.setText(SessionSave.getSession("Currency", getActivity()) + data.detail.waiting_fare_minutes);

        vWait.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.waiting_fare));
        sub.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.subtotal));
        if (data.detail.pending_cancel_amount != null && (Float.valueOf(data.detail.pending_cancel_amount) > 0.0)) {
            cancel_fee.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.pending_cancel_amount));
        } else {
            cancelFareLay.setVisibility(View.GONE);
        }
        tax.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.tax_fare));
        tax_label.setText(NC.getString(R.string.Tax) + " (" + data.detail.tax_percentage + " %)");
        promo.setText("- " + SessionSave.getSession("Currency", getActivity()) + (data.detail.promocode_fare));
        total.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.amt));
        wallet.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.used_wallet_amount));
        cash.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.actual_paid_amount));
        Systems.out.println("payment_type" + data.detail.payment_type_label);
        pay_type.setText(data.detail.payment_type_label);
        trip_total_amount.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.amt));

        if (data.detail.given_tips != null)
            tips.setText(DriverSessionSave.getSession("site_currency", getActivity()) + " " + (data.detail.given_tips));
        else {
            tips_lay.setVisibility(View.GONE);
        }

        if (data.detail.min_distance_status == 0)
            distance_fare_txt.setText(NC.getString(R.string.dist_fare) + " " + NC.getString(R.string.per) + " " + data.detail.metric.toLowerCase());
        else {
            distance_fare_txt.setText(NC.getString(R.string.minimum_fare));
            base_fare_lay.setVisibility(View.GONE);
        }

        if (data.detail.payment_type_label != null && !data.detail.payment_type_label.equals("")) {
            pay_type.setVisibility(View.VISIBLE);
            if (data.detail.payment_type_label.trim().equalsIgnoreCase(NC.getString(R.string.cash).trim())) {
                // payment_type_c.setText(NC.getString(R.string.cash));
                pay_type.setTextColor(getResources().getColor(R.color.black));

            } else if (data.detail.payment_type_label.trim().equalsIgnoreCase(NC.getString(R.string.wallet).trim())) {
                //  payment_type_c.setText(NC.getString(R.string.wallet));
                pay_type.setTextColor(getResources().getColor(R.color.black));
            } else {
                // payment_type_c.setText(NC.getString(R.string.card));
                pay_type.setTextColor(getResources().getColor(R.color.black));
            }
        } else {
            pay_type.setVisibility(View.GONE);
        }


        if (data.detail.preferences != null && data.detail.preferences.size() > 0) {
            addons_lay.setVisibility(View.VISIBLE);
            addonsAdapter = new AddonsAdapter(getActivity(), data.detail.preferences, 2);
            rc_addons.setAdapter(addonsAdapter);
            if (!TextUtils.isEmpty(data.detail.total_preference_fare) && Double.parseDouble(data.detail.total_preference_fare) > 0) {
                tv_total_addon_fare.setText(SessionSave.getSession("Currency", getActivity()) + " " + data.detail.total_preference_fare);
            }
        } else {
            addons_lay.setVisibility(View.GONE);
        }


        if (data.detail.service_id.equals("2")) {
            order_details.setVisibility(View.VISIBLE);
        } else {
            order_details.setVisibility(View.GONE);
        }


        v_product_name.setText(data.detail.product_name);
        v_product_weight.setText(data.detail.product_weight);
        v_product_size.setText(data.detail.product_size);
        v_name.setText(data.detail.delivery_person_name);
        v_date_time.setText(data.detail.delivery_date_time);
        order_description_details.setText(data.detail.delivery_notes);

        //Changes
        night_fare.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.nightfare));
        evefare.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.eveningfare));

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

        if (data.detail.fare_calculation_type.equals("1")) {
            minutes_layout.setVisibility(View.GONE);

        } else if (data.detail.fare_calculation_type.equals("2")) {
            distance_lay.setVisibility(View.GONE);

        }


        if (data.detail.delivery_fare != null && !TextUtils.isEmpty(data.detail.delivery_fare) && Double.parseDouble(data.detail.delivery_fare) > 0.0) {
            tv_delivery_fare.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.delivery_fare));
            ll_delivery.setVisibility(View.VISIBLE);
        } else
            ll_delivery.setVisibility(View.GONE);

        ArrayList<PlacesData> stops = data.detail.stops;

        if (SessionSave.getSession(TaxiUtil.IS_STOP_ENABLED, getActivity(), false) && stops != null && stops.size() > 0)
            pickUpDropLayout.setData(stops);
        else
            createPickAndStopView(data.detail.current_location, data.detail.pickup_latitude, data.detail.pickup_longitude, data.detail.drop_location, data.detail.drop_latitude, data.detail.drop_longitude);

        if (data.detail.payment_type != null) {
            if (data.detail.payment_type.equals("1"))
                cash.setVisibility(View.VISIBLE);
                //   pay_type.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cash, 0, 0, 0);
            else if (data.detail.payment_type.equals("5")) {
                //   pay_type.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cash, 0, 0, 0);
                pay_type.setVisibility(View.GONE);
                cash.setVisibility(View.GONE);
            } else
                pay_type.setCompoundDrawablesWithIntrinsicBounds(R.drawable.credit_card, 0, 0, 0);
        }
        //   pay_type.setText(data.detail.payment_type_label);
        if (data.detail.rating != null) {
               int driver_rating = (int) Float.parseFloat(data.detail.rating);
    //        String driver_rating = data.detail.rating;

 //           rating_count.setText(driver_rating);


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
        }
        try {
            Systems.out.println("_______________ddddd" + data.detail.isSplit_fare);
            if (data.detail.isSplit_fare != null)
                if (data.detail.isSplit_fare == 1) {
                    //Toast.makeText(getActivity(), "hiiiii", Toast.LENGTH_SHORT).show();
                    split_ref.setVisibility(View.VISIBLE);
                    TaxiUtil.SPLIT_STATUS_ITEM.clear();
                    Gson gson = new Gson();
                    String json = gson.toJson(data.detail.splitFareDetails);
                    Systems.out.println("_______________ddddd" + json);
                    JSONArray splitArray = new JSONArray(json);
                    //JSONArray splitArray = jsonn.getJSONObject("detail").getJSONArray("splitFareDetails");
                    for (int i = 0; i < splitArray.length(); i++) {
                        Systems.out.println("_______________ddddd" + i);
                        JSONObject SplitArrayData = splitArray.getJSONObject(i);
                        SplitStatusData obj = new SplitStatusData(getActivity());
                        obj.setImage(SplitArrayData.getString("profile_image"));
                        obj.setName(SplitArrayData.getString("firstname"));
                        obj.setStatus(SplitArrayData.getString("approve_status"));
                        obj.setFare_perc(SplitArrayData.getString("fare_percentage"));
                        obj.setSplitfare(SplitArrayData.getString("splitted_fare"));
                        obj.setWallet(SplitArrayData.getString("used_wallet_amount"));
                        TaxiUtil.SPLIT_STATUS_ITEM.add(obj);
                    }

                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (data.detail.corporate_booking != null && data.detail.corporate_booking.equalsIgnoreCase("1") && !isFromFareScreen) {
            payby_lay.setVisibility(View.GONE);
            wallet_lay.setVisibility(View.GONE);
            cash_lay.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() != null) {
                title = getArguments().getString("title");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * to make drop location visible
     *
     * @param v
     */
    public void dropVisible(View v) {
        Nightfare = v.findViewById(R.id.night_farelay);
        additonal_time_lay = v.findViewById(R.id.additonal_time_lay);
        additonal_distance_lay = v.findViewById(R.id.additonal_distance_lay);
        promotion_val_lay = v.findViewById(R.id.promotion_val_lay);
        WalletAmt_lay = v.findViewById(R.id.WalletAmt_lay);
        wallet_lay = v.findViewById(R.id.wallet_lay);
        Eveningfare = v.findViewById(R.id.evening_farelay);
        evefare = v.findViewById(R.id.evefare);
        eve_night_sep = v.findViewById(R.id.eve_night_sep);
        distance_lay = v.findViewById(R.id.distance_lay);
        evening_lay = v.findViewById(R.id.evening_lay);
        night_lay = v.findViewById(R.id.night_lay);
        distance_fare_txt = v.findViewById(R.id.distance_fare_txt);
        promo_lay = v.findViewById(R.id.promo_lay);
        minutes_layout = v.findViewById(R.id.minutes_layout);
        night_fare = v.findViewById(R.id.night_fare);

        split_ref = v.findViewById(R.id.split_ref);
        split_ref.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                splitFareDialog = new SplitFareStatusDialog();
                splitFareDialog.show(fm, "splitStatus");
            }

        });


//        if (SessionSave.getSession("Lang", getActivity()).equals("fa")||SessionSave.getSession("Lang", getActivity()).equals("ar"))
//            pickupp.setBackgroundResource(R.drawable.search_pickup_ar);
//        else
//            pickupp.setBackgroundResource(R.drawable.search_pickup);
    }

    @Override
    public void onStop() {
        super.onStop();
        Systems.out.println("Nan BackStatck check" + " onStop TripDetailNewFrag");
//        TaxiUtil.close = 1;
        if (isFromFareScreen) {
            ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.payment_complete));
            ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
            ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
            ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
        } else {
            ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.mybookings));
            ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
            ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
            ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
            ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
            // ((MainHomeFragmentActivity) getActivity()).setTitle_m(header_booking_time);
            ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);

            try {
                getActivity().getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            } catch (Exception e) {
                e.printStackTrace();
            }

            trip_map_view.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), MapZoomAct.class);
                intent.putExtra("IMAGE_URI", mapImageUri);
                getActivity().startActivity(intent);
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainHomeFragmentActivity) getActivity()).small_title(false);
    }

    /**
     * request to fill trip detail fragment
     */
    private void callDetail() {
//        CoreClient client = new ServiceGenerator(getActivity()).createService(CoreClient.class);
        CoreClient client = AppController.getInstance().getApiManagerWithEncryptBaseUrl();
        ApiRequestData.getTripDetailRequest request = new ApiRequestData.getTripDetailRequest();
        request.setTrip_id(trip_id);
        request.setPassenger_id(SessionSave.getSession(PASS_ID, getActivity()));

        Call<TripDetailResponse> LoginResponse = client.callData(TaxiUtil.COMPANY_KEY, request, SessionSave.getSession(LANG, getActivity()));
        LoginResponse.enqueue(new RetrofitCallbackClass<TripDetailResponse>(getActivity(), new Callback<TripDetailResponse>() {
            @Override
            public void onResponse(Call<TripDetailResponse> call, Response<TripDetailResponse> response) {
                if (getView() != null) {
                    TripDetailResponse data = response.body();
                    if (data != null) {
                        System.out.println("Response "+data.detail.driver_name);
                        if (data.status == 1) {


                            loading.setVisibility(View.GONE);
                            bottomFrameLay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                }
                            });
                            if (data.detail.trip_type.equals("3") || data.detail.trip_type.equals("2")) {
                                normal_trip_lay.setVisibility(View.GONE);
                                outstation_trip_lay.setVisibility(View.VISIBLE);
                                layoutNormal.setVisibility(View.GONE);
                                layoutOutstation.setVisibility(View.VISIBLE);
                                Nightfare.setVisibility(View.GONE);
                                Eveningfare.setVisibility(View.GONE);

                                base_fare_lay.setVisibility(View.GONE);

                                if (data.detail.trip_type.equals("3"))
                                    trip_type.setText(NC.getString(R.string.trip_type_outstation));//+" Trip"
                                else if (data.detail.trip_type.equals("2"))
                                    trip_type.setText(NC.getString(R.string.trip_type_rental));//+" Trip"

                                additional_dist_lable.setText(NC.getString(R.string.additonal_distance_fare) + " " + data.detail.metric.toLowerCase());

                                tv_additional_distance.setText(SessionSave.getSession("Currency", getActivity()) + "" + data.detail.additional_distance_fare);
                                tv_additional_time.setText(SessionSave.getSession("Currency", getActivity()) + "" + data.detail.additional_time_fare);
                                baseFare.setText(SessionSave.getSession("Currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(data.detail.base_fare)));
//                                waitingFare.setText(SessionSave.getSession("Currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(data.detail.waiting_fare)));

                                subtotal_val.setText(SessionSave.getSession("Currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(data.detail.additional_distance_fare) + Float.valueOf(data.detail.additional_time_fare) + Float.valueOf(data.detail.base_fare)));
                                promotion_val.setText("- " + SessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(data.detail.promotion)));

                                float subTotal = Float.valueOf(data.detail.additional_distance_fare) + Float.valueOf(data.detail.additional_time_fare) + Float.valueOf(data.detail.base_fare);
                                float withoutPromo = Float.valueOf(data.detail.promotion);
                                tripcost_val.setText(SessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", (subTotal - withoutPromo)));
                                tax_val.setText(SessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(data.detail.tax_fare)));
                                taxTxt.setText(NC.getString(R.string.Tax) + " (" + data.detail.tax_percentage + " %)");
                                walletAmount.setText("- " + SessionSave.getSession("Currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(data.detail.used_wallet_amount)));
                                nettotal_val.setText(SessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", ((subTotal - withoutPromo)) + (Float.valueOf(data.detail.tax_fare))));
                                paidAmount.setText(SessionSave.getSession("site_currency", getActivity()) + "" + String.format(Locale.UK, "%.2f", (Float.valueOf(data.detail.paid_amount))));
                                paymentType.setText(data.detail.payment_type_label);
                                details_trip_id.setText(NC.getString(R.string.trip_id) + ": " + data.detail.trip_id);

                                if (Double.parseDouble(data.detail.additional_time_fare) <= 0.0) {
                                    additonal_time_lay.setVisibility(View.GONE);
                                }
                                if (Double.parseDouble(data.detail.additional_distance_fare) <= 0.0) {
                                    additonal_distance_lay.setVisibility(View.GONE);
                                }
                                if (Float.valueOf(data.detail.promotion) <= 0.0) {
                                    promotion_val_lay.setVisibility(View.GONE);
                                }
                                if (Float.valueOf(data.detail.used_wallet_amount) <= 0.0) {
                                    WalletAmt_lay.setVisibility(View.GONE);
                                }
                                user.setText(data.detail.driver_name);
                                fares.setText(SessionSave.getSession("Currency", getActivity()) + data.detail.paid_amount);
                                // Picasso.get().load(data.detail.driver_image).resize(100, 100).into(driverImg);
                                String name = data.detail.driver_name;
                                if (data.detail.driver_image != null && data.detail.driver_image.length() > 0) {
                                    Picasso.get().load(data.detail.driver_image).placeholder(getResources().getDrawable(R.drawable.driver_loadingimage)).error(getResources().getDrawable(R.drawable.driver_noimage)).into(driverImg);
                                } else {
                                    if (data.detail.driver_name != "") {
                                        ProfileImageSetupClass.setupProfileImage(
                                                name, driverImg
                                        );
                                    } else {
                                        Picasso.get().load(R.drawable.loadingimage).into(driverImg);
                                    }
                                }
                                mapImageUri = data.detail.map_image;
                                Picasso.get().load(mapImageUri).into(trip_map_view);

                                createPickAndStopView(data.detail.current_location, data.detail.pickup_latitude, data.detail.pickup_longitude, data.detail.drop_location, data.detail.drop_latitude, data.detail.drop_longitude);

                                if (data.detail.pending_cancel_amount != null && (Float.valueOf(data.detail.pending_cancel_amount) > 0.0)) {
                                    cancelFeeVal.setText(SessionSave.getSession("Currency", getActivity()) + (data.detail.pending_cancel_amount));
                                } else {
                                    cancelFareLayOut.setVisibility(View.GONE);
                                }

                                int driver_rating = (int) Float.parseFloat(data.detail.rating);
                                if (driver_rating == 0)
                                    driverRat.setImageResource(R.drawable.star6);
                                else if (driver_rating == 1)
                                    driverRat.setImageResource(R.drawable.star1);
                                else if (driver_rating == 2)
                                    driverRat.setImageResource(R.drawable.star2);
                                else if (driver_rating == 3)
                                    driverRat.setImageResource(R.drawable.star3);
                                else if (driver_rating == 4)
                                    driverRat.setImageResource(R.drawable.star4);
                                else if (driver_rating == 5)
                                    driverRat.setImageResource(R.drawable.star5);


                            } else {
                                showFare(data);
                            }


                        } else {
                            CToast.ShowToast(getActivity(), data.message);
                        }
                    } else {
                        CToast.ShowToast(getActivity(), NC.getString(R.string.check_internet_connection));
                    }
                    //((MainActivity) getActivity()).closeProgressDialog();
                }


            }

            @Override
            public void onFailure(Call<TripDetailResponse> call, Throwable t) {
                t.printStackTrace();
                CToast.ShowToast(getActivity(), NC.getString(R.string.check_internet_connection));

                // (getActivity() != null)
                //((MainActivity) getActivity()).closeProgressDialog();
            }
        }));
    }


    /**
     * Method to create views dynamically if ArrayList<PlacesData> value not available (ie., Normal flow)
     * <p>
     * New ArrayList of PlacesData values created with pickup and drop(if available) and dynamic views created based on that ArrayList
     *
     * @param pickup_location
     * @param pickup_latitude
     * @param pickup_longitude
     * @param drop_location
     * @param drop_latitude
     * @param drop_longitude
     */
    private void createPickAndStopView(String pickup_location, Double pickup_latitude, Double pickup_longitude, String drop_location, String drop_latitude, String drop_longitude) {
        ArrayList<PlacesData> pickUpDropList = new ArrayList<>();
        PlacesData pickUpData = new PlacesData(0, pickup_latitude, pickup_longitude, pickup_location, "", "", "0", "");
        pickUpDropList.add(pickUpData);
        if (drop_location != null && !drop_location.isEmpty()) {
            PlacesData dropData = new PlacesData((1 + new Random().nextInt()), Double.parseDouble(drop_latitude), Double.parseDouble(drop_longitude), drop_location, "", "", "0", "");
            pickUpDropList.add(dropData);
        }
        if (getActivity() != null)
            pickUpDropLayout.setData(pickUpDropList);
    }
}