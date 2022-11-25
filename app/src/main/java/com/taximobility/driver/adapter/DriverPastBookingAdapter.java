package com.taximobility.driver.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.taximobility.ProfileImageSetupClass;
import com.taximobility.R;
import com.taximobility.driver.data.apiData.DriverUpcomingResponse;
import com.taximobility.driver.fragments.DriverTripDetailNewFrag;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.driver.utils.DriverSessionSave;
import com.taximobility.driver.utils.DriverSystems;
import com.taximobility.util.SessionSave;

import java.util.List;

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

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = null;
        view = inflater.inflate(R.layout.driver_past_booking_item, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        if (!data.get(position).profile_image.trim().equals("")) {
            DriverSystems.out.println("imageLink_" + data.get(position).profile_image);
            Picasso.get().load(data.get(position).profile_image).into(holder.driver_image);
        } else {
            if (!data.get(position).passenger_name.equals("")) {
                ProfileImageSetupClass.setupProfileImage(
                        data.get(position).passenger_name, holder.driver_image
                );
            } else {
                Picasso.get().load(R.drawable.loadingimage).into(holder.driver_image);
            }
        }
        holder.trip_time.setText(data.get(position).pickup_time);
        holder.trip_driver_name.setText(data.get(position).passenger_name);
        holder.txt_pickup.setText(data.get(position).pickup_location);
//        holder.txt_pickup.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        holder.txt_pickup.setSingleLine(true);
//        holder.txt_pickup.setMarqueeRepeatLimit(-1);
//        holder.txt_pickup.setSelected(true);
        holder.txt_drop.setText(data.get(position).drop_location);
        holder.trip_time.setText(data.get(position).pickup_time);
        holder.model_name.setText(data.get(position).model_name);
        holder.total_distance_fare.setText(DriverSessionSave.getSession("site_currency", mContext) + " " + data.get(position).distance_fare_km);

        SessionSave.saveSession("past_pickup_location", data.get(position).pickup_location, mContext);
        SessionSave.saveSession("past_drop_location", data.get(position).drop_location, mContext);

        holder.trip_id.setText("#" + data.get(position).passengers_log_id);
        holder.trip_date.setText(data.get(position).pickup_time);
        holder.trip_time_new.setText(data.get(position).pickup_time);
        holder.trip_amt.setText(DriverSessionSave.getSession("site_currency", mContext) + " " + data.get(position).distance_fare_km);
        if (data.get(position).travel_status.equals("1"))
            holder.trip_com_canl.setText(DriverNC.getString(R.string.completed));
        else
            holder.trip_com_canl.setText(DriverNC.getString(R.string.cancelled));


        if (data.get(position).travel_status.trim().equals("1")) {
            holder.book_lay.setOnClickListener(view -> {

                Fragment ff = new DriverTripDetailNewFrag();
                Bundle b = new Bundle();
                b.putString("trip_id", data.get(position).passengers_log_id);
                b.putString("title", data.get(position).pickup_time);
                ff.setArguments(b);
                ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.mainFrag, ff).commit();
            });
        } else {
            holder.trip_status.setText(DriverNC.getString(R.string.cancelled));
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * View holder class member this contains in every row in list.
     */
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView driver_image;
        TextView trip_time, trip_driver_name, total_distance_fare, total_km;
        TextView trip_status;
        LinearLayout book_lay;
        TextView txt_pickup, txt_drop, model_name;
        TextView trip_id, trip_date, trip_time_new, trip_amt, trip_com_canl;
        ImageView icon_arrow;

        public CustomViewHolder(View v) {
            super(v);
            driver_image = v.findViewById(R.id.driver_image);
            trip_time = v.findViewById(R.id.trip_time);
            trip_driver_name = v.findViewById(R.id.trip_driver_name);
            txt_pickup = v.findViewById(R.id.txt_pickup);
            txt_drop = v.findViewById(R.id.txt_drop);
            book_lay = v.findViewById(R.id.book_lay);
            total_distance_fare = v.findViewById(R.id.total_distance_fare);
            total_km = v.findViewById(R.id.total_km);
            model_name = v.findViewById(R.id.model_name);
            trip_id = v.findViewById(R.id.trip_id);
            trip_date = v.findViewById(R.id.trip_date);
            trip_time_new = v.findViewById(R.id.trip_time_new);
            trip_amt = v.findViewById(R.id.trip_amt);
            trip_com_canl = v.findViewById(R.id.trip_com_canl);
            icon_arrow = v.findViewById(R.id.icon_arrow);

        }
    }
}
