package com.taximobility.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.data.apiData.PromoDataList;
import com.taximobility.util.Colorchange;

/**
 * Created by developer on 18/8/17.
 */

public class PromoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    PromoDataList data;

    public PromoAdapter(Context c, PromoDataList data) {
        this.mContext = c;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.promo_list_item, parent, false);
        Colorchange.ChangeColor((ViewGroup) view, mContext);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomViewHolder vHolder = (CustomViewHolder) holder;
        vHolder.header.setText(data.promoDatas.get(position).getStatus());
        vHolder.message.setText(data.promoDatas.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return data.promoDatas.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView header, message;

        public CustomViewHolder(View view) {
            super(view);
            header = view.findViewById(R.id.header);
            message = view.findViewById(R.id.message);
        }
    }

}