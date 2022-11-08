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

public class DriverSettlementHistoryAdapter extends RecyclerView.Adapter<DriverSettlementHistoryAdapter.CustomViewHolder> {

    private final Context mContext;

    private final List<DriverListClass> data;

    public DriverSettlementHistoryAdapter(Context context, List<DriverListClass> pastData) {

        this.mContext = context;

        this.data = pastData;

    }

    LayoutInflater layoutInflater;

    @Override

    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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

        holder.binding.txtStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.driver_ic_settled));

    }

    @Override

    public int getItemCount() {

        return data.size();

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        DriverSettlementHistoryListBinding binding;

        public CustomViewHolder(View view) {

            super(view);

            binding = DataBindingUtil.bind(view);

        }

    }

}
