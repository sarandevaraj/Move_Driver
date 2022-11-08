package com.taximobility.bookingmodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.taximobility.util.NC;
import com.squareup.picasso.Picasso;
import com.taximobility.R;
import com.taximobility.bookingmodule.Data.ModelData;
import com.taximobility.interfaces.OpenPackage;
import com.taximobility.util.Colorchange;
import com.taximobility.util.SessionSave;

import java.util.ArrayList;

public class ModelListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    ArrayList<ModelData> data;
    int selected_model_id = 0;
    Double fare, time;
    OpenPackage listener;
    boolean isFirstTimeOpen = true;

    public ModelListAdapter(Context c, ArrayList<ModelData> data, OpenPackage listener, int model_id) {
        this.mContext = c;
        this.data = data;
        this.listener = listener;
        this.selected_model_id = model_id;
        this.fare = 0.0;
        this.time = 0.0;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.model_list_item, parent, false);
        Colorchange.ChangeColor((ViewGroup) view, mContext);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomViewHolder vHolder = (CustomViewHolder) holder;
        vHolder.tv_model_name.setText(data.get(position).getModel_name());
        vHolder.pass_count.setText(data.get(position).getModel_size());
        if (selected_model_id == Integer.parseInt(data.get(position).getModel_id())) {
            if (isFirstTimeOpen) {
                isFirstTimeOpen = false;
                listener.getNearestDriver(selected_model_id, data.get(position).getModel_size());
            }
            Picasso.get().load(data.get(position).getFocus_image()).error(R.drawable.car2_unfocus).into(vHolder.img_model);
        } else {
            Picasso.get().load(data.get(position).getUnfocus_image()).error(R.drawable.car2_unfocus).into(vHolder.img_model);
        }
        if (!data.get(position).getModel_id().equalsIgnoreCase("-1")) {
            vHolder.pass_count.setVisibility(View.VISIBLE);
            vHolder.time.setVisibility(View.VISIBLE);
            vHolder.tv_fare.setVisibility(View.VISIBLE);
            if (selected_model_id == Integer.parseInt(data.get(position).getModel_id())) {
                if (fare > 0) {
                    String eta_time = String.valueOf(Math.round(time));
                    System.out.println("eta_time :"+eta_time);
                    vHolder.time.setText(NC.getString(R.string.travelling_time)+" "+eta_time + " - Mins");
//                    vHolder.time.setText(NC.getString(R.string.travelling_time)+" "+eta_time+" - Mins");
                    vHolder.tv_fare.setText(NC.getString(R.string.estimated_fare)+" "+SessionSave.getSession("Currency", mContext) + String.format("%.2f", fare));
                } else {
                    System.out.println("fare estimate null in fare "+fare);
                    vHolder.time.setText("Select to see fare estimate");
                    vHolder.tv_fare.setText(SessionSave.getSession("Currency", mContext) + "0.00");
                }
            } else {
                System.out.println("fare estimate null null model id");
                vHolder.time.setText("Select to see fare estimate");
                vHolder.tv_fare.setText(SessionSave.getSession("Currency", mContext) + "0.00");
            }
        } else {
            vHolder.pass_count.setVisibility(View.GONE);
            vHolder.time.setVisibility(View.GONE);
            vHolder.tv_fare.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateFare(Double faree, Double timee) {
        fare = faree;
        time = timee;
        notifyDataSetChanged();

    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_model;
        TextView tv_model_name, pass_count, time, tv_fare;
        ImageView img_model;

        public CustomViewHolder(View view) {
            super(view);
            ll_model = view.findViewById(R.id.ll_model);
            tv_model_name = view.findViewById(R.id.tv_model_name);
            pass_count = view.findViewById(R.id.pass_count);
            time = view.findViewById(R.id.time);
            tv_fare = view.findViewById(R.id.tv_fare);
            img_model = view.findViewById(R.id.img_model);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    selected_model_id = Integer.parseInt(data.get(pos).getModel_id());
                    notifyDataSetChanged();
                    if (data.get(pos).getModel_id().equalsIgnoreCase("-1")) {
                        listener.openPackage();
                    } else {
                        listener.getNearestDriver(selected_model_id, data.get(pos).getModel_size());
                    }

                }
            });
        }
    }


}
