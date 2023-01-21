package com.taximobility.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.taximobility.R;
import com.taximobility.databinding.DriverSettlementHistoryListBinding;
import com.taximobility.driver.data.apiData.DriverListClass;

import com.taximobility.driver.utils.DirverColorchange;

import java.util.List;

public class DriverPendingHistoryAdapter extends RecyclerView.Adapter<DriverPendingHistoryAdapter.CustomViewHolder> {

    private final Context mContext;

    private final List<DriverListClass> data;

    public DriverPendingHistoryAdapter(Context context, List<DriverListClass> pastData) {
        this.mContext = context;
        this.data = pastData;
    }

    LayoutInflater layoutInflater;

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        DriverSettlementHistoryListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.driver_settlement_history_list, parent, false);
        DirverColorchange.ChangeColor(binding.mainLay, mContext);
        return new CustomViewHolder(binding.getRoot());
    }

    @Override

    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.binding.setList(data.get(position));
        holder.binding.txtTripId.setVisibility(View.VISIBLE);
        holder.binding.txtPaymentBy.setVisibility(View.GONE);

        if (data.get(position).settlement_status.equals("1")) {
            holder.binding.txtStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.driver_ic_approved));
        } else {
            holder.binding.txtStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.driver_ic_pending));
        }
    }

    @Override

    public int getItemCount() {
        return data.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        DriverSettlementHistoryListBinding binding;

        public CustomViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }
}