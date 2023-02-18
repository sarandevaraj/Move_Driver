package com.movedriverdriver.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movedriverdriver.R;
import com.movedriverdriver.driver.data.apiData.AddonsData;
import com.movedriverdriver.driver.utils.DriverSessionSave;
import com.movedriverdriver.util.Colorchange;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddonsAdapter extends RecyclerView.Adapter<AddonsAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<AddonsData> data;
    private final int type;

    public AddonsAdapter(Context context, ArrayList<AddonsData> addonsData, int selected_type) {
        this.context = context;
        this.data = addonsData;
        this.type = selected_type;
    }

    @NonNull
    @Override
    public AddonsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.driver_addons_list, parent, false);
        Colorchange.ChangeColor((ViewGroup) view, context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddonsAdapter.ViewHolder holder, final int position) {
        if (type == 1) {
            holder.ll_addon_price.setVisibility(View.GONE);
            holder.addon_name.setVisibility(View.VISIBLE);
            int pos = position + 1;
            holder.addon_name.setText(pos + ". " + data.get(position).preference_name);
        } else {
            holder.addon_name.setVisibility(View.GONE);
            holder.ll_addon_price.setVisibility(View.VISIBLE);
            holder.tv_price.setText(DriverSessionSave.getSession("site_currency", context) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(data.get(position).preference_fare)));
            holder.tv_type.setText(data.get(position).preference_name);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ArrayList<AddonsData> getData() {
        return data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView addon_name, tv_type, tv_price;
        LinearLayout ll_addon_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addon_name = itemView.findViewById(R.id.addon_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_price = itemView.findViewById(R.id.tv_price);
            ll_addon_price = itemView.findViewById(R.id.ll_addon_price);
        }
    }
}