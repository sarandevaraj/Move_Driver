package com.taximobility.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.adapter.FavoriteDriverAdapter;
import com.taximobility.data.FavouriteDriverData;
import com.taximobility.features.CToast;
import com.taximobility.interfaces.APIResult;
import com.taximobility.service.APIService_Retrofit_JSON_NoProgress;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.taximobility.util.ConstantsKt.PASS_ID;

/**
 * this class is used to favoutite the driver
 */
public class FavouriteDriverFrag extends Fragment {

    // Class members declarations.
    private TextView HeadTitle;
    private TextView nodataTxt;
    private ListView List;


    public Dialog mDialog;
    private ImageView favourite_loading;
    private Dialog alertmDialog;

    private Dialog dialog;

    // Set the layout to activity.

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favouritedriverlay, container, false);
        priorChanges(v);
        Colorchange.ChangeColor((ViewGroup) v, getActivity());

        return v;
    }

    public void priorChanges(View v) {
        // super.priorChanges();
        FontHelper.applyFont(getActivity(), v.findViewById(R.id.frame_lay));

        HeadTitle = v.findViewById(R.id.header_titleTxt);
        HeadTitle.setText(NC.getResources().getString(R.string.favoritedriver_));

        Initialize(v);
    }

    // Initialize the views on layout
    public void Initialize(View v) {
        // TODO Auto-generated method stub
        FontHelper.applyFont(getActivity(), v.findViewById(R.id.favdriver_lay2));
        favourite_loading = v.findViewById(R.id.giff);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(favourite_loading);
        Glide.with(getActivity())
                .load(R.raw.loading_anim)
                .into(imageViewTarget);

        nodataTxt = v.findViewById(R.id.nodataTxt);

        List = v.findViewById(R.id.list);
        List.setDivider(null);

    }

    /**
     * Default on resume method used to call the get_favourite_list to update the favorite place list in UI.
     */

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        ((MainHomeFragmentActivity) getActivity()).toolbarRightIcon(true);
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.favoritedriver_));
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");

        try {
            JSONObject j = new JSONObject();
            //j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
            j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
            if (SessionSave.getAPI(getActivity()).length() > 0) {
                JSONArray jsonArray = SessionSave.getAPI(getActivity());
                j.put("report_array",jsonArray);

            }

            if (TaxiUtil.isOnline(getActivity())) {
                new GetFavDriverlist("type=favourite_driver_list", j);
            } else {
                // alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + getResources().getString(R.string.check_internet_connection), "" + getResources().getString(R.string.ok), "");

                dialog = Utility.alert_view_dialog(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getString(R.string.check_internet_connection),
                        "" + NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }, "");
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        ((MainHomeFragmentActivity) getActivity()).toolbarRightIcon(false);
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getResources().getString(R.string.menu_settings));
        super.onStop();
    }

    /**
     * This class used to get the favorite list
     * <p>
     * <p>
     * This class used to get the favorite list
     * </p>
     *
     * @author developer
     */
    private class GetFavDriverlist implements APIResult {
        private String Message;

        public GetFavDriverlist(String url, JSONObject JO) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON_NoProgress(getActivity(), this, JO, false).execute(url);
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            favourite_loading.setVisibility(View.GONE);
            if (TaxiUtil.mFavouriteDriverlist.size() != 0) {
                TaxiUtil.mFavouriteDriverlist.clear();
            }
            if (isSuccess) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        JSONArray jArry = json.getJSONArray("details");
                        int length = jArry.length();
                        for (int i = 0; i < length; i++) {
                            FavouriteDriverData data = new FavouriteDriverData(jArry.getJSONObject(i).getString("driver_id"), jArry.getJSONObject(i).getString("name"), jArry.getJSONObject(i).getString("email"), jArry.getJSONObject(i).getString("phone"), jArry.getJSONObject(i).getString("profile_image"), jArry.getJSONObject(i).getString("taxi_no"));
                            TaxiUtil.mFavouriteDriverlist.add(data);
                            nodataTxt.setVisibility(View.GONE);
                            List.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Message = json.getString("message");
                        nodataTxt.setVisibility(View.VISIBLE);
                        List.setVisibility(View.GONE);
                        nodataTxt.setText(Message);
                    }
                    if (TaxiUtil.mFavouriteDriverlist.size() != 0) {
                        mHandler.sendEmptyMessage(0);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error));
                    }
                });
            }
        }
    }

    /**
     * This handler used to update the values into list using array adapter.
     */

    public Handler mHandler = new Handler() {
        private FavoriteDriverAdapter adapter;

        @Override
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case 0:
                    try {
                        adapter = new FavoriteDriverAdapter(getActivity());
                        List.setAdapter(adapter);
                    } catch (Exception e) {

                    }
                    break;
            }
        }


    };

    // Slider menu used to move from one activity to another activity.


    @Override
    public void onDetach() {
        ((MainHomeFragmentActivity) getActivity()).toolbarRightIcon(false);
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        ((MainHomeFragmentActivity) getActivity()).toolbarRightIcon(false);
        TaxiUtil.mActivitylist.remove(this);
        if (dialog != null)
            Utility.closeDialog(dialog);
        super.onDestroy();
    }

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
            button_success.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    alertmDialog.dismiss();
                }
            });
            button_failure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    alertmDialog.dismiss();
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
