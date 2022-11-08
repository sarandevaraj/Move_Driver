package com.taximobility.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.data.apiData.UpcomingResponse;
import com.taximobility.fragments.OnGoingFrag;
import com.taximobility.fragments.ReasonListFrag;
import com.taximobility.util.CL;
import com.taximobility.util.Colorchange;
import com.taximobility.util.SessionSave;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by developer on 1/11/16.
 * use to populate the upcoming list in trip history page
 */
public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.CustomViewHolder> {

    private final List<UpcomingResponse.PastBooking> data;
    private final Context mContext;


    public UpcomingAdapter(Context c, List<UpcomingResponse.PastBooking> data) {
        this.mContext = c;
        this.data = data;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = null;
        view = inflater.inflate(R.layout.upcoming_list_item, parent, false);
        Colorchange.ChangeColor((ViewGroup) view, mContext);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        ((MainHomeFragmentActivity) mContext).call_image.setVisibility(View.GONE);
        ((MainHomeFragmentActivity) mContext).txt_emergency.setVisibility(View.GONE);
        if (!data.get(position).profile_image.trim().equals("")) {
            // Picasso.get().load(data.get(position).map_image).into(holder.map_image);
            Picasso.get().load(data.get(position).profile_image).into(holder.driver_image);
        }
        holder.trip_time.setText(data.get(position).pickup_time);
        holder.trip_driver_name.setText(data.get(position).drivername);
        if (data.get(position).drop_location.equals("")) {
            holder.txt_drop.setVisibility(View.GONE);
            holder.approx_fare.setVisibility(View.GONE);
            holder.approx_distance.setVisibility(View.GONE);
            holder.drop_icon.setVisibility(View.GONE);
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.txt_drop.setVisibility(View.VISIBLE);
            holder.approx_fare.setVisibility(View.VISIBLE);
            holder.approx_distance.setVisibility(View.VISIBLE);
            holder.drop_icon.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.VISIBLE);
            holder.approx_fare.setText("" + SessionSave.getSession("site_currency", mContext) + data.get(position).approx_fare);
            holder.approx_distance.setText(data.get(position).approx_distance + " " + SessionSave.getSession("Metric", mContext));
            holder.txt_drop.setText(data.get(position).drop_location);
        }
        if(data.get(position).now_after.equals("1"))
        {
            holder.model_name.setText(R.string.pickup_time);
        }else {
            holder.model_name.setText(data.get(position).model_name);
        }
        holder.txt_pickup.setText(data.get(position).pickup_location);
        if (data.get(position).travel_status != null)
            if (!data.get(position).travel_status.trim().equals("0")) {
                //holder.trip_track.setVisibility(View.VISIBLE);
                holder.trip_track.setVisibility(View.VISIBLE);
                holder.trip_cancel.setVisibility(View.GONE);
                holder.trip_track.setTag(position);
                holder.trip_track.setOnClickListener(view -> {
                    SessionSave.saveSession("trip_id", data.get((Integer) view.getTag()).passengers_log_id.trim(), mContext);
                    ((AppCompatActivity) mContext).getSupportFragmentManager()
                            .beginTransaction().addToBackStack(null).replace(R.id.mainFrag, new OnGoingFrag()).commit();
                });
                holder.book_lay.setTag(position);
//                holder.book_lay.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        SessionSave.saveSession("trip_id", data.get((Integer) view.getTag()).passengers_log_id.trim(), mContext);
//                        ((AppCompatActivity) mContext).getSupportFragmentManager()
//                                .beginTransaction().addToBackStack(null).add(R.id.mainFrag, new OnGoingFrag()).commit();
//                    }
//                });
            } else {
                holder.book_lay.setOnClickListener(null);
                holder.trip_track.setVisibility(View.GONE);
                holder.trip_cancel.setVisibility(View.VISIBLE);
                holder.trip_cancel.setTag(position);
                holder.trip_cancel.setTextColor(CL.getColor(mContext, R.color.button_accept));
                holder.trip_cancel.setOnClickListener(view -> {
                    FragmentManager fm1 = ((AppCompatActivity) mContext).getSupportFragmentManager();
                    Bundle bb = new Bundle();
                    bb.putString("From", "2");
                    bb.putString("trip_id", data.get((Integer) view.getTag()).passengers_log_id.trim());
                    bb.putString("Cancel_fee", data.get((Integer) view.getTag()).cancellation_fee.trim());
                    ReasonListFrag rl = new ReasonListFrag();
                    rl.setArguments(bb);
                    rl.show(fm1, "rl");

                });

            }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView map_image, driver_image, drop_icon,call_driver;
        TextView trip_time, trip_driver_name, trip_track, trip_cancel, txt_drop, txt_pickup, model_name, approx_fare, approx_distance;
        LinearLayout book_lay;
        View divider;

        CustomViewHolder(View v) {
            super(v);
            map_image = v.findViewById(R.id.map_image);
            driver_image = v.findViewById(R.id.driver_image);
            trip_time = v.findViewById(R.id.trip_time);
            trip_driver_name = v.findViewById(R.id.trip_driver_name);
            trip_track = v.findViewById(R.id.trip_track);
            trip_cancel = v.findViewById(R.id.trip_cancel);
            txt_pickup = v.findViewById(R.id.txt_pickup);
            txt_drop = v.findViewById(R.id.txt_drop);
            book_lay = v.findViewById(R.id.book_lay);
            model_name = v.findViewById(R.id.model_name);
            approx_fare = v.findViewById(R.id.approx_fare);
            approx_distance = v.findViewById(R.id.approx_distance);
            drop_icon = v.findViewById(R.id.drop_icon);
            divider = v.findViewById(R.id.divider);
            call_driver =v.findViewById(R.id.call_driver);

        }
    }
}
