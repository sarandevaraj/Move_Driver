package com.taximobility.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.taximobility.R;
import com.taximobility.data.apiData.PreferencesDataList;
import com.taximobility.util.SessionSave;


public class PreferenceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    PreferencesDataList data;

    public PreferenceListAdapter(Context c, PreferencesDataList data) {
        this.mContext = c;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.preferences_list_item, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomViewHolder vHolder = (CustomViewHolder) holder;
        vHolder.preference.setText(data.preferencesDatas.get(position).preference_name);
        if (!TextUtils.isEmpty(data.preferencesDatas.get(position).preference_fare) && Double.parseDouble(data.preferencesDatas.get(position).preference_fare) > 0) {
            vHolder.tv_amt.setText(SessionSave.getSession("Currency", mContext) + data.preferencesDatas.get(position).preference_fare);
        } else {
            vHolder.tv_amt.setText("Free");
        }


        vHolder.preference.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data.preferencesDatas.get(position).isSelected = isChecked;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.preferencesDatas.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        CheckBox preference;
        TextView tv_amt;

        public CustomViewHolder(View view) {
            super(view);
            preference = view.findViewById(R.id.preference);
            tv_amt = view.findViewById(R.id.tv_amt);
        }
    }

}