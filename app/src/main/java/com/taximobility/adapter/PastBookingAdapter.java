
package com.taximobility.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.data.apiData.UpcomingResponse;
import com.taximobility.fragments.TripDetailNewFrag;
import com.taximobility.util.Colorchange;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by developer on 1/11/16.
 * use to populate the past booking recyclerview
 */
public class PastBookingAdapter extends RecyclerView.Adapter<PastBookingAdapter.CustomViewHolder> {

    private final List<UpcomingResponse.PastBooking> data;
    private final Context mContext;


    public PastBookingAdapter(Context c, List<UpcomingResponse.PastBooking> data) {
        this.mContext = c;
        this.data = data;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = null;
        view = inflater.inflate(R.layout.past_booking_item_new, parent, false);
        Colorchange.ChangeColor((ViewGroup) view, mContext);

        return new CustomViewHolder(view);
    }

    /**
     * binds view to recyclerview
     *
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        ((MainHomeFragmentActivity) mContext).call_image.setVisibility(View.GONE);
        ((MainHomeFragmentActivity) mContext).txt_emergency.setVisibility(View.GONE);
        if (!data.get(position).profile_image.trim().equals("")) {
            //   Glide.with(mContext).load(data.get(position).map_image).into(holder.map_image);
            Picasso.get().load(data.get(position).profile_image).into(holder.driver_image);
        }else
        {
            Picasso.get().load(R.drawable.driver_noimage).into(holder.driver_image);
        }
       /* if (!data.get(position).profile_image.trim().equals("")) {
            Picasso.get().load(data.get(position).map_image).into(holder.map_image);
            Picasso.get().load(data.get(position).profile_image).resize(100, 100).into(holder.driver_image);
        }*/
        holder.trip_time.setText(data.get(position).pickup_time);
        holder.trip_driver_name.setText(data.get(position).drivername);
        holder.trip_time.setText(data.get(position).pickup_time);
        holder.total_distance_fare.setText(SessionSave.getSession("Currency", mContext) + " " + data.get(position).fare);
        String pickup = data.get(position).place;
        if (!TextUtils.isEmpty(data.get(position).pickup_location)) {
            pickup = data.get(position).pickup_location;
        }
        holder.model_name.setText(data.get(position).model_name);
        holder.txt_pickup.setText(pickup);
        holder.txt_drop.setText(data.get(position).drop_location);
        if(data.get(position).drop_location.equals("")) {
            holder.txt_drop.setVisibility(View.GONE);
            holder.drop_icon.setVisibility(View.GONE);
        }
        else {
            holder.txt_drop.setVisibility(View.VISIBLE);
            holder.drop_icon.setVisibility(View.VISIBLE);
        }


        SessionSave.saveSession("past_pickup_location", pickup, mContext);
        SessionSave.saveSession("past_drop_location", data.get(position).drop_location, mContext);

       /* if (data.get(position).payment_type.trim().equals("1")) {
            holder.trip_payment_type.setText(NC.getString(R.string.cash));
            holder.trip_payment_type.setTextColor(CL.getColor(mContext, R.color.pickupheadertext));
        } else if (data.get(position).payment_type.trim().equals("5")) {
            holder.trip_payment_type.setText(NC.getString(R.string.wallet));
            holder.trip_payment_type.setTextColor(CL.getColor(mContext, R.color.pickupheadertext));
        } else if ((data.get(position).payment_type.trim().equals("2")) || (data.get(position).payment_type.trim().equals("3"))) {
            holder.trip_payment_type.setText(NC.getString(R.string.card));
            holder.trip_payment_type.setTextColor(CL.getColor(mContext, R.color.paymentcard));
        } else
            holder.trip_payment_type.setVisibility(View.INVISIBLE);*/

  /*  holder.trip_payment_amount.setText(SessionSave.getSession("Currency", mContext) + "" + data.get(position).fare);
        holder.trip_status.setText(NC.getString(R.string.completed));
*/
//        if (!TextUtils.isEmpty(data.get(position).corporate_booking) && data.get(position).corporate_booking.equals("1")) {
//            holder.trip_payment_type.setVisibility(View.INVISIBLE);
//            /*holder.trip_payment_type.setText(NC.getString(R.string.corporate));
//            holder.trip_payment_type.setTextColor(CL.getColor(mContext, R.color.pickupheadertext));*/
//        }

        if (data.get(position).travel_status.trim().equals("1")) {

            holder.bookLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment ff = new TripDetailNewFrag();
                    Bundle b = new Bundle();
                    b.putString("trip_id", data.get(position).trip_id);
                    b.putString("title", data.get(position).pickup_time);
                    b.putBoolean("isFromFareScreen", false);
                    b.putString("tripDetailResponse", null);
                    ff.setArguments(b);
                    ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.mainFrag, ff).commit();
                }
            });

        } else {
            holder.trip_status.setText(NC.getString(R.string.cancelled));
            holder.model_name.setText(R.string.pickup_time);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView map_image, driver_image,drop_icon;
        TextView trip_time, trip_driver_name, total_distance_fare;
        TextView trip_payment_type, trip_payment_amount, trip_status, txt_pickup, txt_drop, model_name;
        LinearLayout bookLay;

        CustomViewHolder(View v) {
            super(v);
            //  map_image = v.findViewById(R.id.map_image);
            driver_image = v.findViewById(R.id.driver_image);
            trip_time = v.findViewById(R.id.trip_time);
            trip_driver_name = v.findViewById(R.id.trip_driver_name);
            bookLay = v.findViewById(R.id.book_lay);
            total_distance_fare = v.findViewById(R.id.total_distance_fare);
            model_name = v.findViewById(R.id.model_name);
            txt_pickup = v.findViewById(R.id.txt_pickup);
            txt_drop = v.findViewById(R.id.txt_drop);
            trip_status =v.findViewById(R.id.trip_status);
            drop_icon = v.findViewById(R.id.drop_icon);

//            trip_payment_type = v.findViewById(R.id.trip_payment_type);
//            trip_payment_amount = v.findViewById(R.id.trip_payment_amount);
        }
    }
}
