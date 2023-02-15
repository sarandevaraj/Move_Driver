package com.movedriver.driver.locationSearch;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.movedriver.R;

import java.util.ArrayList;

/**
 * Created by developer on 14/3/17.
 */

public class DriverPlacesAutoCompleteAdapter extends RecyclerView.Adapter<DriverPlacesAutoCompleteAdapter.PredictionHolder> {

    private ArrayList<DriverPlacesDetail> mResultList;
    private final Context mContext;

    public DriverPlacesAutoCompleteAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public PredictionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = layoutInflater.inflate(R.layout.driver_place_search_list_item, viewGroup, false);
        return new PredictionHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionHolder mPredictionHolder, final int i) {
        DriverPlacesDetail placesDetail = null;
        if (mResultList != null) placesDetail = mResultList.get(i);


        if (placesDetail != null) {
            mPredictionHolder.tvPlaceName.setText(placesDetail.getLabel_name());
            mPredictionHolder.tvAddress.setText(placesDetail.getLocation_name());

            if (placesDetail.getPlaceType() == 0) {
                mPredictionHolder.imgPlaceType.setImageResource(R.drawable.driver_ic_location_black_24dp);
            } else {
                if (placesDetail.getAndroid_image_unfocus() != null) {
                    Glide.with(mContext).load(placesDetail.getAndroid_image_unfocus()).apply(RequestOptions.placeholderOf(R.drawable.driver_ic_location_black_24dp).error(R.drawable.driver_ic_location_black_24dp)).into(mPredictionHolder.imgPlaceType);
                } else mPredictionHolder.imgPlaceType.setImageResource(R.drawable.driver_ic_recent);
            }
        }
    }

    public void submitList(ArrayList<DriverPlacesDetail> mResultList) {
        this.mResultList = mResultList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mResultList != null ? mResultList.size() : 0;
    }

    public DriverPlacesDetail getItem(int position) {
        return mResultList != null ? mResultList.get(position) : null;
    }

    static class PredictionHolder extends RecyclerView.ViewHolder {
        private final TextView tvPlaceName;
        private final TextView tvAddress;
        private final ImageView imgPlaceType;

        PredictionHolder(View itemView) {
            super(itemView);
            tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            imgPlaceType = itemView.findViewById(R.id.imgPlaceType);
        }
    }
}
