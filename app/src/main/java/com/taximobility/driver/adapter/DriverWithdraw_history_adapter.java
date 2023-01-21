package com.taximobility.driver.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.driver.DriverWithdrawReqAct;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This adapter class is used to show withdraw trip history
 */
public class DriverWithdraw_history_adapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<HashMap<String, String>> mList;
    private int mtype = 1;

    // constructor
    public DriverWithdraw_history_adapter(Context context, ArrayList<HashMap<String, String>> list, int type) {
        this.mContext = context;
        this.mList = list;
        this.mtype = type;
    }

    // It returns the list item count.
    @Override
    public int getCount() {
        return mList.size();
    }

    // It returns the item detail with select position.
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    // It returns the item id with select position.
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // Get the view for each row in the list used view holder.
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.driver_withdrawrefhistoryitem_new, parent, false);
            holder = new ViewHolder();
            holder.request_amount = convertView.findViewById(R.id.request_amount);
            holder.request_taxi = convertView.findViewById(R.id.request_taxi);
            holder.status = convertView.findViewById(R.id.status);
            holder.layout = convertView.findViewById(R.id.main);
            convertView.setTag(holder);

            holder.layout.setOnClickListener(v -> {
                String withdrawrequestId = mList.get(position).get("wallet_request_id");
                Intent in = new Intent(mContext, DriverWithdrawReqAct.class);
                in.putExtra("wallet_request_id", withdrawrequestId);
                mContext.startActivity(in);
            });

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            holder.request_taxi.setText("" + mList.get(position).get("wallet_request_date"));
            holder.request_amount.setText("" + mList.get(position).get("wallet_request_amount"));
            holder.status.setText("" + mList.get(position).get("status"));

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return convertView;
    }

    /**
     * View holder class member this contains in every row in list.
     */
    private static class ViewHolder {
        LinearLayout layout;
        TextView view_lay;
        TextView request_amount;
        TextView request_taxi;
        TextView status;
    }
}
