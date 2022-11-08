package com.taximobility.driver.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.taximobility.ProfileImageSetupClass;
import com.taximobility.driver.utils.DriverSessionSave;
import com.squareup.picasso.Picasso;

import com.taximobility.R;
import com.taximobility.driver.data.apiData.DriverUpcomingResponse;
import com.taximobility.driver.fragments.DriverTripDetailNewFrag;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverSystems;
import com.taximobility.util.SessionSave;

import java.util.List;

/**
 * Created by developer on 1/11/16.
 */

/**
 * This adapter class is used to show driver past booking history
 */
public class DriverPastBookingAdapter extends RecyclerView.Adapter<DriverPastBookingAdapter.CustomViewHolder> {

    private final List<DriverUpcomingResponse.PastBooking> data;
    private final Context mContext;


    public DriverPastBookingAdapter(Context c, List<DriverUpcomingResponse.PastBooking> data) {
        this.mContext = c;
        this.data = data;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = null;
        view = inflater.inflate(R.layout.driver_past_booking_item, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        if (!data.get(position).profile_image.trim().equals("")) {
            //   Glide.with(mContext).load(data.get(position).map_image).into(holder.map_image);
            DriverSystems.out.println("imageLink_" + data.get(position).profile_image);
            Picasso.get().load(data.get(position).profile_image).into(holder.driver_image);
        } else {
//            Picasso.get().load(R.drawable.driver_noimage).into(holder.driver_image);
            if (data.get(position).passenger_name != "") {
                ProfileImageSetupClass.setupProfileImage(
                        data.get(position).passenger_name, holder.driver_image
                );
            } else {
                Picasso.get().load(R.drawable.loadingimage).into(holder.driver_image);
            }
        }
        //    DirverColorchange.ChangeColor(holder.book_lay, mContext);
        holder.trip_time.setText(data.get(position).pickup_time);
        holder.trip_driver_name.setText(data.get(position).passenger_name);
        holder.txt_pickup.setText(data.get(position).pickup_location);
//        holder.txt_pickup.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        holder.txt_pickup.setSingleLine(true);
//        holder.txt_pickup.setMarqueeRepeatLimit(-1);
//        holder.txt_pickup.setSelected(true);
        holder.txt_drop.setText(data.get(position).drop_location);
//        holder.txt_drop.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        holder.txt_drop.setSingleLine(true);
//        holder.txt_drop.setMarqueeRepeatLimit(-1);
//        holder.txt_drop.setSelected(true);
        holder.trip_time.setText(data.get(position).pickup_time);
        holder.model_name.setText(data.get(position).model_name);
        holder.total_distance_fare.setText(DriverSessionSave.getSession("site_currency", mContext) + " " + data.get(position).distance_fare_km);
        // holder.total_km.setText(data.get(position).distance);

        SessionSave.saveSession("past_pickup_location", data.get(position).pickup_location, mContext);
        SessionSave.saveSession("past_drop_location", data.get(position).drop_location, mContext);

//        if (data.get(position).payment_type.trim().equalsIgnoreCase("Cash")) {
//            holder.trip_payment_type.setText(DriverNC.getString(R.string.cash));
//            holder.trip_payment_type.setTextColor(DriverCL.getResources().getColor(R.color.pastbookingcashtext));
//
//        } else if (data.get(position).payment_type.trim().equalsIgnoreCase("Wallet")) {
//            holder.trip_payment_type.setText(DriverNC.getString(R.string.wallet));
//            holder.trip_payment_type.setTextColor(DriverCL.getResources().getColor(R.color.pastbookingcashtext));
//        } else {
//            holder.trip_payment_type.setText(DriverNC.getString(R.string.card));
//            holder.trip_payment_type.setTextColor(DriverCL.getResources().getColor(R.color.pastbookingcard));
//        }
//        if (data.get(position).corporate_booking != null) {
//            if (data.get(position).corporate_booking.equalsIgnoreCase("1")) {
//                holder.trip_payment_type.setText(DriverNC.getString(R.string.corporate));
//                holder.trip_payment_type.setTextColor(DriverCL.getResources().getColor(R.color.pastbookingcashtext));
//            }
//        }
//        holder.trip_payment_amount.setText(DriverSessionSave.getSession("site_currency", mContext) + " " + data.get(position).amt);


        if (data.get(position).travel_status.trim().equals("1")) {
            holder.book_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment ff = new DriverTripDetailNewFrag();
                    Bundle b = new Bundle();
                    b.putString("trip_id", data.get(position).passengers_log_id);
                    b.putString("title", data.get(position).pickup_time);
                    ff.setArguments(b);
                    ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.mainFrag, ff).commit();
                }
            });
        } else {
            holder.trip_status.setText(DriverNC.getString(R.string.cancelled));
            // holder.map_image.setOnClickListener(null);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * View holder class member this contains in every row in list.
     */
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView map_image, driver_image;
        TextView trip_time, trip_driver_name, total_distance_fare, total_km;
        TextView trip_payment_type, trip_payment_amount, trip_status;
        LinearLayout book_lay;
        TextView passengerCallTxt, cancelTxt, startTripTxt, txt_pickup, txt_drop, model_name;

        public CustomViewHolder(View v) {
            super(v);
            //  map_image = v.findViewById(R.id.map_image);
            driver_image = v.findViewById(R.id.driver_image);
            trip_time = v.findViewById(R.id.trip_time);
            trip_driver_name = v.findViewById(R.id.trip_driver_name);
            txt_pickup = v.findViewById(R.id.txt_pickup);
            txt_drop = v.findViewById(R.id.txt_drop);
            book_lay = v.findViewById(R.id.book_lay);
            total_distance_fare = v.findViewById(R.id.total_distance_fare);
            total_km = v.findViewById(R.id.total_km);
            model_name = v.findViewById(R.id.model_name);
//            trip_payment_type = v.findViewById(R.id.trip_payment_type);
//            trip_payment_amount = v.findViewById(R.id.trip_payment_amount);
//            trip_status = v.findViewById(R.id.trip_status);
            //super(view);
        }
    }
}
