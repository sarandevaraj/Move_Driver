package com.taximobility.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.bookingmodule.BookTaxiHomePage;
import com.taximobility.fragments.EditFavouriteFrag;
import com.taximobility.interfaces.DialogInterface;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.bumptech.glide.Glide;

import java.util.Calendar;

/**
 * This adapter used to populate the favorite details into list view.
 */
//
public class FavouriteAdapter extends BaseAdapter implements DialogInterface {
    private final Context context;
    private final LayoutInflater mInflater;
    private String FavId = "", fLat = "", dplace = "", dlat = "", dlong = "", fav_comments = "", notes = "";
    private String Place_type, PickupTime, fLong = "", fPlace = "";
    private int book_fav_Driver = 2;
    private Dialog alertmDialog;

    // constructor
    public FavouriteAdapter(Context favouritesAct) {
        // TODO Auto-generated constructor stub
        context = favouritesAct;
        mInflater = LayoutInflater.from(context);
        int hour;
        int minute;
        int seconds;
        final Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        seconds = cal.get(Calendar.SECOND);
        updateTime(hour, minute, seconds);
    }

    // Return list size.
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return TaxiUtil.mFavouritelist.size();
    }

    // It returns the item detail with select position.
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return TaxiUtil.mFavouritelist.get(position);
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
        ((MainHomeFragmentActivity) context).call_image.setVisibility(View.GONE);
        ((MainHomeFragmentActivity) context).txt_emergency.setVisibility(View.GONE);
        // TODO Auto-generated method stub

        ViewHolder mHolder;
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.favourite_item, parent, false);
            mHolder = new ViewHolder();
            FontHelper.applyFont(context, convertView.findViewById(R.id.favitem_contain));
            mHolder.place_image = convertView.findViewById(R.id.place_image);
            mHolder.Place = convertView.findViewById(R.id.placeTxt);
            mHolder.drop_placeTxt = convertView.findViewById(R.id.drop_placeTxt);
            mHolder.single_place = convertView.findViewById(R.id.single_place);
            mHolder.img_lineup = convertView.findViewById(R.id.line1);
            mHolder.BookBtn = convertView.findViewById(R.id.BookBtn);
            mHolder.placelay = convertView.findViewById(R.id.placelay);
            mHolder.drop_pin = convertView.findViewById(R.id.drop_pin);
            mHolder.drop_pin_dot = convertView.findViewById(R.id.drop_pin_dot);
            mHolder.fav_img = convertView.findViewById(R.id.fav_img);
            mHolder.favitem_contain = convertView.findViewById(R.id.favitem_contains);


            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        Colorchange.ChangeColor((ViewGroup) convertView, context);

        try {
            mHolder.BookBtn.setText(NC.getString(R.string.book_now));
            Spannable pickupplace = new SpannableString("" + TaxiUtil.mFavouritelist.get(position).getPlace());
            pickupplace.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0, 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mHolder.Place.setText(pickupplace);
            Spannable dropplace = new SpannableString("" + TaxiUtil.mFavouritelist.get(position).getD_Place());
            dropplace.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0, 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (TaxiUtil.mFavouritelist.get(position).getD_Place().trim().length() > 2)
                mHolder.drop_placeTxt.setVisibility(View.VISIBLE);
            Systems.out.println("position" + TaxiUtil.mFavouritelist.get(position).getD_Place().length());
            mHolder.drop_placeTxt.setText(dropplace);
            if (TaxiUtil.mFavouritelist.get(position).getPlace_type().equals("1")) {
                mHolder.place_image.setImageResource(R.drawable.fav_home);
            } else if (TaxiUtil.mFavouritelist.get(position).getPlace_type().equals("2")) {
                mHolder.place_image.setImageResource(R.drawable.company);
            } else if (TaxiUtil.mFavouritelist.get(position).getPlace_type().equals("3")) {
                mHolder.place_image.setImageResource(R.drawable.fav_airport);
            } else {
                mHolder.place_image.setImageResource(R.drawable.fav_others);
            }

            Glide.with(context).load(TaxiUtil.mFavouritelist.get(position).getMap_image()).into(mHolder.fav_img);


            if (TaxiUtil.mFavouritelist.get(position).getD_Place().length() < 2) {
                mHolder.drop_pin.setVisibility(View.GONE);
                mHolder.drop_pin_dot.setVisibility(View.VISIBLE);
                mHolder.single_place.setVisibility(View.INVISIBLE);
                mHolder.drop_placeTxt.setVisibility(View.GONE);
                mHolder.Place.setVisibility(View.VISIBLE);
            } else if (TaxiUtil.mFavouritelist.get(position).getD_Place().length() > 2) {
                mHolder.drop_pin_dot.setVisibility(View.GONE);
                mHolder.drop_pin.setVisibility(View.VISIBLE);
                mHolder.single_place.setVisibility(View.GONE);
            }
            // this listener helps call book the taxi from already stored favorite place.
            mHolder.BookBtn.setOnClickListener(v -> {
                FavId = TaxiUtil.mFavouritelist.get(position).getFavouriteId();
                fLat = TaxiUtil.mFavouritelist.get(position).getF_lat();
                fLong = TaxiUtil.mFavouritelist.get(position).getF_lng();
                fPlace = TaxiUtil.mFavouritelist.get(position).getPlace();
                dplace = TaxiUtil.mFavouritelist.get(position).getD_Place();
                dlat = TaxiUtil.mFavouritelist.get(position).getD_Lat();
                dlong = TaxiUtil.mFavouritelist.get(position).getD_Long();
                notes = TaxiUtil.mFavouritelist.get(position).getNotes();
                BookTaxiHomePage ef = new BookTaxiHomePage();
                Bundle bundle = new Bundle();
                bundle.putDouble("pickup_latitude", Double.parseDouble(fLat));
                bundle.putDouble("pickup_longitude", Double.parseDouble(fLong));
                if (!dlat.trim().equals("")) {
                    bundle.putDouble("drop_latitude", Double.parseDouble(dlat));
                    bundle.putDouble("drop_longitude", Double.parseDouble(dlong));
                    bundle.putString("drop_location", dplace);
                } else {
                    bundle.putDouble("drop_latitude", 0.0);
                    bundle.putDouble("drop_longitude", 0.0);
                    bundle.putString("drop_location", null);
                }
                bundle.putString("pickup_location", fPlace);
                bundle.putString("driver_notes", notes);
                bundle.putBoolean("book_again", true);
                ef.setArguments(bundle);
                FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag, ef).commit();

            });
            mHolder.favitem_contain.setTag(position);
            mHolder.placelay.setTag(position);
            mHolder.favitem_contain.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();

                    FavId = TaxiUtil.mFavouritelist.get(position).getFavouriteId();
                    fLat = TaxiUtil.mFavouritelist.get(position).getF_lat();
                    fLong = TaxiUtil.mFavouritelist.get(position).getF_lng();
                    fPlace = TaxiUtil.mFavouritelist.get(position).getPlace();
                    dplace = TaxiUtil.mFavouritelist.get(position).getD_Place();
                    dlat = TaxiUtil.mFavouritelist.get(position).getD_Lat();
                    dlong = TaxiUtil.mFavouritelist.get(position).getD_Long();
                    fav_comments = TaxiUtil.mFavouritelist.get(position).getComments();
                    notes = TaxiUtil.mFavouritelist.get(position).getNotes();
                    Place_type = TaxiUtil.mFavouritelist.get(position).getPlace_type();
                    EditFavouriteFrag ef = new EditFavouriteFrag();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("ADD", false);
                    bundle.putString("Id", FavId);
                    bundle.putString("Lat", fLat);
                    bundle.putString("Lng", fLong);
                    bundle.putString("Place", fPlace);
                    bundle.putString("D_place", dplace);
                    bundle.putString("D_lat", dlat);
                    bundle.putString("D_long", dlong);
                    bundle.putString("Cmt", fav_comments);
                    bundle.putString("notes", notes);
                    bundle.putString("Place_type", Place_type);
                    ef.setArguments(bundle);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, ef).addToBackStack(null).commit();
                    ((MainHomeFragmentActivity) context).toolbarRightIcon(false);
                }
            });
            // this listener helps edit the already stored favorite place from the list.
            mHolder.placelay.setOnClickListener(v -> {
                int position1 = (int) v.getTag();
                FavId = TaxiUtil.mFavouritelist.get(position1).getFavouriteId();
                fLat = TaxiUtil.mFavouritelist.get(position1).getF_lat();
                fLong = TaxiUtil.mFavouritelist.get(position1).getF_lng();
                fPlace = TaxiUtil.mFavouritelist.get(position1).getPlace();
                dplace = TaxiUtil.mFavouritelist.get(position1).getD_Place();
                dlat = TaxiUtil.mFavouritelist.get(position1).getD_Lat();
                dlong = TaxiUtil.mFavouritelist.get(position1).getD_Long();
                fav_comments = TaxiUtil.mFavouritelist.get(position1).getComments();
                notes = TaxiUtil.mFavouritelist.get(position1).getNotes();
                Place_type = TaxiUtil.mFavouritelist.get(position1).getPlace_type();
                EditFavouriteFrag ef = new EditFavouriteFrag();
                Bundle bundle = new Bundle();
                bundle.putBoolean("ADD", false);
                bundle.putString("Id", FavId);
                bundle.putString("Lat", fLat);
                bundle.putString("Lng", fLong);
                bundle.putString("Place", fPlace);
                bundle.putString("D_place", dplace);
                bundle.putString("D_lat", dlat);
                bundle.putString("D_long", dlong);
                bundle.putString("Cmt", fav_comments);
                bundle.putString("notes", notes);
                bundle.putString("Place_type", Place_type);
                ef.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, ef).addToBackStack(null).commit();
                ((MainHomeFragmentActivity) context).toolbarRightIcon(false);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public void onSuccess(Dialog dialog, String resultcode) {
        dialog.dismiss();
        book_fav_Driver = 1;
    }

    @Override
    public void onFailure(Dialog dialog, String resultcode) {
        book_fav_Driver = 2;
        dialog.dismiss();
    }

    // View holder class member this contains in every row in list.
    class ViewHolder {
        public TextView Place;
        public View img_lineup;
        public TextView BookBtn;
        public ImageView place_image, drop_pin, drop_pin_dot;
        private LinearLayout placelay, favitem_contain;
        private TextView drop_placeTxt, single_place;
        public ImageView fav_img;
    }

    //not used
    public void alert_view(Context mContext, String title, String message, String success_txt, String failure_txt) {
        try {
            final View view = View.inflate(mContext, R.layout.alert_view, null);
            alertmDialog = new Dialog(mContext, R.style.dialogwinddow);
            alertmDialog.setContentView(view);
            alertmDialog.setCancelable(true);
            FontHelper.applyFont(mContext, alertmDialog.findViewById(R.id.alert_id));
            alertmDialog.show();
            final TextView title_text = alertmDialog.findViewById(R.id.title_text);
            final TextView message_text = alertmDialog.findViewById(R.id.message_text);
            final Button button_success = alertmDialog.findViewById(R.id.button_success);
            final Button button_failure = alertmDialog.findViewById(R.id.button_failure);
            button_failure.setVisibility(View.GONE);
            title_text.setText(title);
            message_text.setText(message);
            button_success.setText(success_txt);
            button_success.setOnClickListener(v -> {
                alertmDialog.dismiss();
            });
            button_failure.setOnClickListener(v -> {
                alertmDialog.dismiss();
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Is used to convert the values into hour and mins format.
     *
     * @param hours
     * @param mins
     * @param sec
     */

    private void updateTime(int hours, final int mins, final int sec) {

        try {
            String timeSet = "";
            if (hours > 12) {
                hours -= 12;
                timeSet = "PM";
            } else if (hours == 0) {
                hours += 12;
                timeSet = "AM";
            } else if (hours == 12)
                timeSet = "PM";
            else
                timeSet = "AM";
            String minutes = "";
            if (mins < 10)
                minutes = "0" + mins;
            else
                minutes = String.valueOf(mins);
            final String aTime = new StringBuilder().append(hours).append(':').append(minutes).append(':').append(sec).append(" ").append(timeSet).toString();
            PickupTime = aTime;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
