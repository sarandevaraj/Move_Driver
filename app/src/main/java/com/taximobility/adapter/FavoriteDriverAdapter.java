package com.taximobility.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.features.CToast;
import com.taximobility.interfaces.APIResult;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.RoundedImageView;
import com.taximobility.util.SessionSave;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static com.taximobility.util.ConstantsKt.PASS_ID;

/**
 * Created by developer on 7/10/16.
 * populate favourite driver listview
 */
public class FavoriteDriverAdapter extends BaseAdapter {
    private final Context context;
    LayoutInflater mInflater = null;
    private Dialog alertmDialog, dialog;

    // constructor
    public FavoriteDriverAdapter(Context favouritesAct) {
        // TODO Auto-generated constructor stub
        context = favouritesAct;
        try {
            mInflater = LayoutInflater.from(context);
        } catch (Exception e) {

        }

    }

    // Return list size.
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return TaxiUtil.mFavouriteDriverlist.size();
    }

    // It returns the item detail with select position.
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return TaxiUtil.mFavouriteDriverlist.get(position);
    }

    // It returns the item id with select position.
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    // Get the view for each row in the list used view holder.
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder mHolder;
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.favouritedriver_item, parent, false);
            mHolder = new ViewHolder();
            FontHelper.applyFont(context, convertView.findViewById(R.id.favdriveritem_contain));
            mHolder.img_profile = convertView.findViewById(R.id.driverImg);
            mHolder.txt_name = convertView.findViewById(R.id.driver_nameTxt);
            mHolder.txt_vechileno = convertView.findViewById(R.id.driver_vechilenum);
            mHolder.img_delete = convertView.findViewById(R.id.img_delete);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        Colorchange.ChangeColor((ViewGroup) convertView, context);

        try {

            Picasso.get().load(TaxiUtil.mFavouriteDriverlist.get(position).profile_image).into(mHolder.img_profile);
            mHolder.txt_name.setText(TaxiUtil.mFavouriteDriverlist.get(position).name);
            mHolder.txt_vechileno.setText(TaxiUtil.mFavouriteDriverlist.get(position).taxino);


            mHolder.img_delete.setOnClickListener(v -> alert_view(context, "" + NC.getResources().getString(R.string.message), NC.getString(R.string.delete_fdrivers), "" + NC.getString(R.string.ok), "" + NC.getString(R.string.cancel), position));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    /**
     * calls api to delete the driver from favourite list
     */
    private class DelFavDriver implements APIResult {
        private String Message;
        int pos;

        public DelFavDriver(String url, JSONObject data, int _pos) {
            // TODO Auto-generated constructor stub
            this.pos = _pos;
            new APIService_Retrofit_JSON(context, this, data, false).execute(url);
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            // TODO Auto-generated method stub

            Log.e("Result ", result);
            if (isSuccess) {
                try {
                    JSONObject json = new JSONObject(result);

                    Log.e("Result ", json.toString());

                    Message = json.getString("message");

                    if (json.getInt("status") == 1) {

                        //remove deleted value from arraylist
                        TaxiUtil.mFavouriteDriverlist.remove(this.pos);
                        notifyDataSetChanged();

                    } else {
                        ((Activity) context).runOnUiThread(() -> CToast.ShowToast(context, Message));
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {

                ((Activity) context).runOnUiThread(() -> CToast.ShowToast(context, NC.getString(R.string.server_con_error)));

            }
        }
    }

    public void alert_view(Context mContext, String title, String message, String success_txt, String failure_txt, final int position) {


        dialog = Utility.alert_view_dialog((Activity) mContext, "", "" + message,
                "" + NC.getResources().getString(R.string.yes), "" + NC.getResources().getString(R.string.no), true, (dialog, which) -> {
                    dialog.dismiss();
                    try {
                        String url;
                        JSONObject j = new JSONObject();
                        j.put("passenger_id", "" + SessionSave.getSession(PASS_ID, context));
                        j.put("driver_id", TaxiUtil.mFavouriteDriverlist.get(position).DriverId);
                        url = "type=unfavourite_driver";
                        new DelFavDriver(url, j, position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (dialog, which) -> dialog.dismiss(), "");
    }

    /**
     * View holder class member this contains in every row in list.
     */

    class ViewHolder {
        public TextView txt_name, txt_vechileno;

        public RoundedImageView img_profile;

        public ImageView img_delete;
    }

}