package com.taximobility.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.data.apiData.PlacesDetail;
import com.taximobility.interfaces.PickupDropSet;
import com.taximobility.interfaces.SetPickup;
import com.taximobility.locationSearch.AddStopActivity;
import com.taximobility.locationSearch.PickupDropSearchActivity;
import com.taximobility.locationSearch.PlacesData;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static com.taximobility.util.ConstantsKt.BUNDLE_PICKUP_DROP_ADDRESS;

/**
 * this class is used to search the pick up and drop place
 */

/**
 * this class is used to search the pick up and drop place
 */
public class SearchFragment extends Fragment {
    private LinearLayout
            dropppp;
    private String fav_place_type, P_Address, droplocEdt, currentlocTxt, pickupHint, dropHint;
    private Dialog alertmDialog;
    private RelativeLayout drop_loc_lay, searchlay;
    PickupDropSet listener;
    SetPickup pickuplistener;
    private LatLng pickuplatlng, droplatlng;

    public String getPickupHint() {
        return pickupHint;
    }

    public void setPickupHint(String pickupHint) {
        this.pickupHint = pickupHint;
    }

    public String getDropHint() {
        return dropHint;
    }

    public void setDropHint(String dropHint) {
        this.dropHint = dropHint;
    }

    public void setSearchFragmentListner(PickupDropSet listener) {
        this.listener = listener;
    }

    public void setLocationListner(SetPickup listener) {
        this.pickuplistener = listener;
    }

    public Double getDroplng() {

        if (droplatlng == null)
            return 0.0;
        return droplatlng.longitude;
    }

//    public void setDroplng(Double droplng) {
//        this.droplng = droplng;
//        HomePage.D_longitude = droplng;
//    }

    public Double getDroplat() {

        if (droplatlng == null)
            return 0.0;
        return droplatlng.latitude;
    }

//    public void setDroplat(Double droplat) {
//        this.droplat = droplat;
//        HomePage.D_latitude = droplat;
//    }

    public Double getPickuplng() {
        if (pickuplatlng == null)
            return 0.0;
        return pickuplatlng.longitude;
    }

//    public void setPickuplng(Double pickuplng) {
//        this.pickuplng = pickuplng;
//        HomePage.P_longitude = pickuplng;
//    }

    public LatLng getPickuplatlng() {
        return pickuplatlng;
    }

    public LatLng getDroplatlng() {

        return droplatlng;
    }

    public void setPickuplatlng(LatLng pickuplng) {
        this.pickuplatlng = pickuplng;
        if (this.pickuplatlng != null)
            listener.pickUpSet(pickuplatlng.latitude, pickuplatlng.longitude);
        //  HomePage.P_longitude = pickuplng;
    }

    public void setDroplatlng(LatLng droplatlng) {
        this.droplatlng = droplatlng;
//        HomePage.D_latitude = droplatlng.latitude;
//        HomePage.D_longitude = droplatlng.longitude;
        if (droplatlng != null)
            listener.dropSet(droplatlng.latitude, droplatlng.longitude);
        //  HomePage.P_longitude = pickuplng;
    }

    public Double getPickuplat() {
        if (pickuplatlng == null)
            return 0.0;
        return pickuplatlng.latitude;

    }

//    public void setPickuplat(Double pickuplat) {
//        this.pickuplat = pickuplat;
//        HomePage.P_latitude = pickuplat;
//    }

    //private Double droplng = 0.0;
    private Dialog r_mDialog;
    private static String LocationRequestedBy = "";

    public String getPickuplocTxt() {
        if (currentlocTxt == null)
            return "";
        return currentlocTxt;
    }

    public void setPickuplocTxt(String pickuplocTxt) {
        this.pickuplocTxt = pickuplocTxt;
        currentlocTxt = (pickuplocTxt.replace(", null", ""));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!currentlocTxt.equals(""))
                    if (pickuplistener != null)
                        pickuplistener.setPickupAddress(currentlocTxt);
            }
        }, 500);

    }

    public String getDroplocTxt() {
        if (droplocEdt == null)
            return "";
        return droplocEdt;
    }

    public void setDroplocTxt(String droplocTxt) {
        this.droplocTxt = droplocTxt;
        droplocEdt = (droplocTxt);
        if (!getPickuplocTxt().trim().equals(""))
            dropVisible();
        else
            dropGone();

    }

    private String pickuplocTxt, droplocTxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_home_new, container, false);
        Colorchange.ChangeColor((ViewGroup) v, getActivity());

        searchlay = v.findViewById(R.id.searchlay);
        drop_loc_lay = v.findViewById(R.id.drop_loc_lay);

        dropppp = v.findViewById(R.id.dropppp);
//        drop_fav = (LinearLayout
//                ) v.findViewById(R.id.drop_fav);
//        drop_close = (ImageView
//                ) v.findViewById(R.id.drop_close);

        //   pickup_drop_Sep = (View) v.findViewById(R.id.pickup_drop_Sep);

        FontHelper.applyFont(getContext(), searchlay);
        String from = "";
        try {
            from = getArguments().getString("type");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (from.equals("home")) {
//            drop_fav.setVisibility(View.VISIBLE);
//            lay_pick_fav.setVisibility(View.VISIBLE);
            //  dropppp.setVisibility(View.GONE);
            //pickup_pin.setVisibility(View.VISIBLE);
            //   pickup_pinlay.setVisibility(View.GONE);
            searchlay.setBackgroundResource(R.color.transparent);

//            pickup_drop_Sep.setVisibility(View.GONE);

            intializeHome();
        }
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void pickupClicked(boolean onlyPickup) {
        LocationRequestedBy = "P";
        if (SessionSave.getSession(TaxiUtil.IS_STOP_ENABLED, getContext(), false) && mList != null && mList.size() >= 3) {
            Intent intent = new Intent(getActivity(), AddStopActivity.class);
            intent.putParcelableArrayListExtra(BUNDLE_PICKUP_DROP_ADDRESS, mList);
            startActivityForResult(intent, TaxiUtil.LocationResult);
        } else {
            Bundle b = new Bundle();
            b.putString("type", "P");
            Intent i = new Intent(getActivity(), PickupDropSearchActivity.class);
            b.putParcelableArrayList(BUNDLE_PICKUP_DROP_ADDRESS, getStopPoints());
            if (droplatlng != null) {
                PlacesDetail drop_obj = new PlacesDetail();
                drop_obj.setLatitude(getDroplat());
                drop_obj.setLongtitute(getDroplng());
                drop_obj.setLocation_name(getDroplocTxt());
                b.putParcelable("drop_obj", drop_obj);
            }
            if (getPickuplatlng() != null) {
                PlacesDetail pickup_obj = new PlacesDetail();
                pickup_obj.setLatitude(getPickuplat());
                pickup_obj.setLongtitute(getPickuplng());
                pickup_obj.setLocation_name(getPickuplocTxt());
                b.putParcelable("pickup_obj", pickup_obj);

            }
            b.putBoolean("onlyPickup", onlyPickup);
            i.putExtras(b);
            startActivityForResult(i, TaxiUtil.LocationResult);
        }
    }

    public void dropClicked(boolean onlyDrop) {
        listener.requestPickupAddress();
        if (SessionSave.getSession(TaxiUtil.IS_STOP_ENABLED, getContext(), false) && mList != null && mList.size() >= 3) {
            Intent intent = new Intent(getActivity(), AddStopActivity.class);
            intent.putParcelableArrayListExtra(BUNDLE_PICKUP_DROP_ADDRESS, mList);
            startActivityForResult(intent, TaxiUtil.LocationResult);
        } else if (!getPickuplocTxt().trim().equals("")) {
            LocationRequestedBy = "D";
            Bundle b = new Bundle();
            b.putString("type", "D");
            b.putBoolean("onlyDrop", onlyDrop);
            b.putParcelableArrayList(BUNDLE_PICKUP_DROP_ADDRESS, getStopPoints());
            if (getPickuplatlng() != null) {
                PlacesDetail pickup_obj = new PlacesDetail();
                pickup_obj.setLatitude(getPickuplat());
                pickup_obj.setLongtitute(getPickuplng());
                pickup_obj.setLocation_name(getPickuplocTxt());
                b.putParcelable("pickup_obj", pickup_obj);
            }
            if (droplatlng != null) {
                PlacesDetail drop_obj = new PlacesDetail();
                drop_obj.setLatitude(getDroplat());
                drop_obj.setLongtitute(getDroplng());
                drop_obj.setLocation_name(getDroplocTxt());
                b.putParcelable("drop_obj", drop_obj);
            }
            Intent i = new Intent(getActivity(), PickupDropSearchActivity.class);
            i.putExtras(b);
            startActivityForResult(i, TaxiUtil.LocationResult);
        }

    }

    private void intializeHome() {
        drop_loc_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropClicked(false);
            }
        });

    }

    public void dropGone() {
        dropppp.setVisibility(View.GONE);
    }

    public void dropVisible() {
        dropppp.setVisibility(View.VISIBLE);
    }

    ArrayList<PlacesData> mList = new ArrayList<>();


    public ArrayList<LatLng> getLatLngPoints() {
        ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
        if (mList != null && mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                arrayList.add(new LatLng(mList.get(i).getLat(), mList.get(i).getLng()));
            }
        } else {
            arrayList.add(new LatLng(getPickuplat(), getPickuplng()));
            if (droplatlng != null)
                arrayList.add(new LatLng(getDroplat(), getDroplng()));
        }


        return arrayList;
    }


    public ArrayList<PlacesData> getStopPoints() {
        if (mList != null) {
            if (mList.size() == 0) {
                mList.add(new PlacesData(0, getPickuplat(), getPickuplng(), getPickuplocTxt(), "", "", "", ""));
                return mList;
            } else return mList;
        } else {
            mList = new ArrayList<>();
            mList.add(new PlacesData(0, getPickuplat(), getPickuplng(), getPickuplocTxt(), "", "", "", ""));
            return mList;
        }
    }

    public void setStopPoints(ArrayList<PlacesData> mList) {
        this.mList = mList;
    }

    public void clearWayPoints() {
        if (mList != null) {
            mList.clear();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean SET_FOR_PICKUP = false;
        String result = "";

        try {
            double lat = 0.0, lng = 0.0;

            if (data != null) {
                Bundle res = data.getExtras();
                result = res.getString("param_result");
                lat = res.getDouble("lat");
                lng = res.getDouble("lng");
                SET_FOR_PICKUP = res.getBoolean("set_for_pickup");
                mList = res.getParcelableArrayList(BUNDLE_PICKUP_DROP_ADDRESS);
            }
            if (SET_FOR_PICKUP && result != null && !result.trim().equals("")) {
                currentlocTxt = (result);
                LatLng p = new LatLng(lat, lng);
                setPickuplatlng(p);
//                if (droplatlng == null)
//                    HomePage.movetoCurrentloc();

            } else if (result != null && !result.trim().equals("")) {
                droplocEdt = (result);
                LatLng p = null;
                if (lat != 0.0)
                    p = new LatLng(lat, lng);

                if (p != null)
                    setDroplatlng(p);
            } else {
                if (mList != null) {
                    PlacesData pickUpStopData = mList.get(0);
                    setPickuplatlng(new LatLng(pickUpStopData.getLat(), pickUpStopData.getLng()));
                    pickuplocTxt = pickUpStopData.getPlaceName();
                    currentlocTxt = pickUpStopData.getPlaceName();
                    if (mList.size() >= 2) {
                        PlacesData stopData = mList.get(mList.size() - 1);
                        droplocEdt = (stopData.getPlaceName());
                        setDroplatlng(new LatLng(stopData.getLat(), stopData.getLng()));
                    }
                }
                Systems.out.println("SearchFragment onActivityResult " + mList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Systems.out.println("SearchFragment onActivityResult Exception: " + e.getMessage());
        }
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

    public void clearData() {
        pickuplatlng = null;
        droplatlng = null;
        pickuplocTxt = "";
        droplocTxt = "";
    }

}
