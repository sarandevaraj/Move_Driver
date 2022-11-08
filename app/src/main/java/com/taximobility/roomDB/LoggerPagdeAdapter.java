package com.taximobility.roomDB;

import androidx.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taximobility.R;
import com.google.gson.Gson;
import com.taximobility.util.Systems;

/**
 * Created by developer on 18/5/18.
 */

public class LoggerPagdeAdapter extends PagedListAdapter<LoggerModel, LoggerPagdeAdapter.CustomViewHolder> {
    private Context mContext;

    public LoggerPagdeAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.logger_lay, parent, false);
        return new LoggerPagdeAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final LoggerModel model = getItem(position);
        Systems.out.println("LoggerPagdeAdapter "+ getItemCount());
        if (model != null) {
            holder.bindTo(model, position);
        } else {
            // Null defines a placeholder item - PagedListAdapter will automatically invalidate
            // this row when the actual object is loaded from the database
            holder.clear();
        }
    }
    public static final DiffUtil.ItemCallback<LoggerModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<LoggerModel>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull LoggerModel oldUser, @NonNull LoggerModel newUser) {
                    // LoggerModel properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUser.id == newUser.id;
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull LoggerModel oldUser, @NonNull LoggerModel newUser) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldUser.equals(newUser);
                }
            };
    /**
     * View holder class member this contains in every row in list.
     */
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView time, api_url,arrayCount;

        public CustomViewHolder(View v) {
            super(v);
            layout = v.findViewById(R.id.logger_lay);
            time = v.findViewById(R.id.txtTime);
            api_url = v.findViewById(R.id.txtUrl);
            arrayCount = v.findViewById(R.id.arrayCount);
        }

        public void bindTo(final LoggerModel model, int position) {
            time .setText(model.time);
            api_url .setText(model.apiType);
            arrayCount .setText(""+position);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,FullLoggerAct.class);
                    intent.putExtra("msg",new Gson().toJson(model));
                    mContext.startActivity(intent);
                }
            });
        }
        public void clear() {
            time .setText("");
            api_url .setText("");
            arrayCount .setText("");
            layout.setOnClickListener(null);
        }
    }
}