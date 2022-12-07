package com.taximobility.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.taximobility.R;
import com.taximobility.driver.DriverMyFleetAct;
import com.taximobility.driver.data.DriverFleetData;
import com.taximobility.util.CL;

import java.util.List;

/**
 * Created by developer on 1/11/16.
 * use to populate the past booking recyclerview
 */
public class DriverMyFleetListAdapter extends RecyclerView.Adapter<DriverMyFleetListAdapter.CustomViewHolder> {

    private final List<DriverFleetData> data;
    private final Context mContext;


    public DriverMyFleetListAdapter(Context c, List<DriverFleetData> data) {
        this.mContext = c;
        this.data = data;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.driver_my_fleet_item, parent, false);
        return new CustomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {

        if (data.get(position).getprimaryFleet().equals("1")) {
            holder.primary_txt.setText("Assigned");
            holder.primary_txt.setTextColor(CL.getColor(mContext,R.color.green_new));
        } else {
            holder.primary_txt.setTextColor(CL.getColor(mContext,R.color.white));
            holder.primary_txt.setText("Assign");
        }

        holder.taxiNameTxt.setText(data.get(position).getdetails_model_name());
        holder.taxiNoTxt.setText(data.get(position).getdetails_taxi_no());
        holder.fromDate.setText(data.get(position).getdetails_mapping_startdate());
        holder.to_date.setText(data.get(position).getdetails_mapping_enddate());
        Picasso.get().load(data.get(position).getfocus_image_android()).into(holder.taxi_img);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView taxiNameTxt, taxiNoTxt;
        TextView fromDate, to_date;
        Button primary_txt;
        ImageView taxi_img;
        LinearLayout fleet_list_lay;

        public CustomViewHolder(View v) {
            super(v);
            taxiNameTxt = v.findViewById(R.id.taxi_name_txt);
            taxiNoTxt = v.findViewById(R.id.taxi_no_txt);
            fromDate = v.findViewById(R.id.from_date);
            to_date = v.findViewById(R.id.to_date);
            taxi_img = v.findViewById(R.id.taxi_img);
            primary_txt = v.findViewById(R.id.primary_txt);
            fleet_list_lay = v.findViewById(R.id.fleet_list_lay);

            primary_txt.setOnClickListener(view -> ((DriverMyFleetAct) mContext).setPrimaryFleet(data.get(getAdapterPosition()).getdetails_taxi_id()));


        }
    }


}
