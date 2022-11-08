package com.taximobility.bookingmodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.taximobility.R;
import com.taximobility.bookingmodule.Data.ServiceData;
import com.taximobility.interfaces.GetModelDetails;
import com.taximobility.util.Colorchange;

import java.util.ArrayList;

public class ServiceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    ArrayList<ServiceData> data;
    GetModelDetails listener;

    public ServiceListAdapter(Context c, ArrayList<ServiceData> data, GetModelDetails listener) {
        this.mContext = c;
        this.data = data;
        this.listener = listener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.service_list_item, parent, false);
        Colorchange.ChangeColor((ViewGroup) view, mContext);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomViewHolder vHolder = (CustomViewHolder) holder;
        String model_name = data.get(position).getLabel().substring(0, 1).toUpperCase() + data.get(position).getLabel().substring(1).toLowerCase();
        vHolder.tv_service_name.setText(model_name);
        vHolder.tv_service_name.setVisibility(View.GONE);
        Picasso.get().load(data.get(position).getFocus_image()).error(R.drawable.car2_unfocus).into(vHolder.img_model);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView img_model;
        TextView tv_service_name;

        public CustomViewHolder(View view) {
            super(view);
            img_model = view.findViewById(R.id.img_model);
            tv_service_name = view.findViewById(R.id.tv_service_name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.getModelDetails(data.get(getAdapterPosition()).getId());
                }
            });
        }
    }


}
