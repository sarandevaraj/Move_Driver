package com.taximobility.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.squareup.picasso.Picasso;
import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.features.CToast;
import com.taximobility.interfaces.APIResult;
import com.taximobility.service.APIService_Retrofit_JSON_NoProgress;
import com.taximobility.util.AppCacheImage;
import com.taximobility.util.CL;
import com.taximobility.util.Colorchange;
import com.taximobility.util.DownloadImageAndsavetoCache;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static com.taximobility.util.ConstantsKt.API_BASE;
import static com.taximobility.util.ConstantsKt.MODEL_DETAILS;
import static com.taximobility.util.ConstantsKt.MODEL_DETAILS_UPDATE;
import static com.taximobility.util.ConstantsKt.PASS_ID;

/**
 * this class is used to show fare details
 */

public class FareFrag extends Fragment {
    // Class members declarations.

    private String carModel = "1";
    private String selectedModelID;

    private TextView basefareTxt, minfareTxt, belowfareTxt, abovefareTxt, nightfareTxt;
    private TextView cancelfareTxt, evefareTxt, waitingfareTxt, HeadTitle, fare_detail;
    private TextView minkm, belowkm, abovekm;
    private TextView nighttime, evefare, leftIcon, back_text;
    private TextView far_per_km, far_per_km_txt, minutefareTxt;
    private LinearLayout farecontain, fare_progress;
    private LinearLayout id_all, id_alls, firstCar;
    private LinearLayout parent, eve, night;
    private LinearLayout above_lay, below_lay, fare_per_km_lay;
    private WebView simpleWebView;
    private ViewGroup simpleWebView_lay, fare_detail_layout;
    private ImageView iv_web;
    private Dialog mshowDialog;
    View bottom_line ;
    // Set the layout to activity.

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fare_lay, container, false);
        priorChanges(v);
        Colorchange.ChangeColor((ViewGroup) v, getActivity());

        return v;
    }

    public void priorChanges(View v) {
        farecontain = v.findViewById(R.id.farecontain);
        simpleWebView = v.findViewById(R.id.simpleWebView);
        fare_detail_layout = v.findViewById(R.id.fare_detail_layout);
        simpleWebView_lay = v.findViewById(R.id.simpleWebView_lay);
        fare_progress = v.findViewById(R.id.fare_progress);
        HeadTitle = v.findViewById(R.id.header_titleTxt);
        above_lay = v.findViewById(R.id.above_lay);
        below_lay = v.findViewById(R.id.below_lay);
        fare_per_km_lay = v.findViewById(R.id.fare_per_km_lay);
        far_per_km = v.findViewById(R.id.far_per_km);
        far_per_km_txt = v.findViewById(R.id.far_per_km_txt);
        HeadTitle.setText("" + NC.getResources().getString(R.string.fare_txt));
        leftIcon = v.findViewById(R.id.leftIcon);
        leftIcon.setVisibility(View.GONE);
        back_text = v.findViewById(R.id.back_text);
        back_text.setVisibility(View.VISIBLE);
        iv_web = v.findViewById(R.id.giff_webview);

       // FontHelper.applyFont(getActivity(), farecontain);
        //FontHelper.applyFont(getActivity(), v.findViewById(R.id.headlayout));

        Initialize(v);

    }

    // Initialize the views on layout
    public void Initialize(View v) {
        // TODO Auto-generated method stub
        ((MainHomeFragmentActivity) getActivity()).call_image.setVisibility(View.GONE);
        ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.GONE);
        TaxiUtil.current_act = "FareAct";

      //  FontHelper.applyFont(getActivity(), v.findViewById(R.id.farecontain));

        fare_detail = v.findViewById(R.id.fare_detail_txt);

        basefareTxt = v.findViewById(R.id.basefareTxt);
        minfareTxt = v.findViewById(R.id.minfareTxt);
        belowfareTxt = v.findViewById(R.id.belowfareTxt);
        abovefareTxt = v.findViewById(R.id.abovefareTxt);
        nightfareTxt = v.findViewById(R.id.nightfareTxt);
        eve = v.findViewById(R.id.eve);
        night = v.findViewById(R.id.night);
        cancelfareTxt = v.findViewById(R.id.cancelfareTxt);
        id_all = v.findViewById(R.id.id_all);
        id_alls = v.findViewById(R.id.id_alls);
        evefareTxt = v.findViewById(R.id.evefareTxt);
        waitingfareTxt = v.findViewById(R.id.waitingfareTxt);
        minutefareTxt = v.findViewById(R.id.minutefareTxt);
        evefare = v.findViewById(R.id.evefare);
        minkm = v.findViewById(R.id.minkm);
        belowkm = v.findViewById(R.id.belowkm);
        abovekm = v.findViewById(R.id.abovekm);
        nighttime = v.findViewById(R.id.nightchrgtime);
        // Call this method to set the car model  API result.
        final String url = "type=getmodel_fare_details";
        setcarModel();
        new FareUpdate(url);
    }

    /**
     * set car model that listed in back end with its fare list
     */

    public void setcarModel() {

        try {
            final JSONArray array = new JSONArray(SessionSave.getSession(MODEL_DETAILS, getActivity()));
            if (array.length() <= 4) {
                id_alls.setVisibility(View.GONE);

                for (int n = 0; n < array.length(); n++) {
                    int i = 0;
                    i = n;
                    Systems.out.println("innnnnnnnn " + "other lang" + "___" + SessionSave.getSession(TaxiUtil.CORPORATE_PASSENGER, getActivity()) + "___" + array.getJSONObject(n).getString("model_id"));
                    if (!SessionSave.getSession(TaxiUtil.CORPORATE_PASSENGER, getActivity()).equals("") && SessionSave.getSession(TaxiUtil.CORPORATE_PASSENGER, getActivity()).equals("1") && array.getJSONObject(n).getString("model_id").equals("-1")) {
                        Systems.out.println("innnnnnnnn " + "other lang");
                    } else {
                        View v = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_lay_car, id_all, false);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.weight = 1.0f;
                        LinearLayout carlay = v.findViewById(R.id.lay_model_one);
                        carlay.setLayoutParams(params);
                        try {
                            ((TextView) v.findViewById(R.id.txt_model1)).setText("" + array.getJSONObject(i).getString("model_name"));
                            v.findViewById(R.id.bottom_line).setVisibility(View.VISIBLE);

                            if (!AppCacheImage.loadBitmap(array.getJSONObject(i).getString("unfocus_image"), v.findViewById(R.id.txt_dra_car1))) {
                                Systems.out.println("Image... not avail in cache");
                                new DownloadImageAndsavetoCache(v.findViewById(R.id.txt_dra_car1)).execute(array.getJSONObject(i).getString("unfocus_image"));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        carlay.setTag(i);
                        carlay.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {


                                int pos = Integer.parseInt(v.getTag().toString());
                                Systems.out.println("postionnnnn " + "in onclick listner" + pos);
                                try {
                                    selectedModelID = ((JSONObject) array.get(pos)).getString("model_id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Check whether it is outstation or rental
                                if (selectedModelID.equals("-1")) {
                                    simpleWebView_lay.setVisibility(View.VISIBLE);
                                    fare_detail_layout.setVisibility(View.GONE);

                                    carlay_click(v, 1);
                                    try {
                                        final JSONArray array = new JSONArray(SessionSave.getSession(MODEL_DETAILS_UPDATE, getActivity()));

                                        carModel = array.getJSONObject(pos).getString("model_id");
                                        fare_detail.setText(((TextView) v.findViewById(R.id.txt_model1)).getText().toString() + " - " + " " + NC.getResources().getString(R.string.fare_details));
                                        v.findViewById(R.id.bottom_line).setVisibility(View.VISIBLE);


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {

                                    simpleWebView_lay.setVisibility(View.GONE);

                                    fare_detail_layout.setVisibility(View.VISIBLE);

                                    select_model(v, pos, 1);
                                }
                            }
                        });

                        id_all.addView(v);
                        if (i == 0) {
                            firstCar = carlay;
                        }
                        if (i < (array.length() - 1)) {
                            LinearLayout.LayoutParams paramsv = new LinearLayout.LayoutParams(
                                    1, ViewGroup.LayoutParams.MATCH_PARENT);
                            View vv = new View(getActivity());
                            vv.setLayoutParams(paramsv);
                            id_all.addView(vv);
                        }
                    }
                }
            } else {
                id_all.setVisibility(View.GONE);
                for (int n = 0; n < array.length(); n++) {
                    int i = n;
                    View v = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_lay_car, id_alls, false);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    final LinearLayout carlay = v.findViewById(R.id.lay_model_one);
                    ViewGroup.LayoutParams lp = carlay.getLayoutParams();
                    if (lp instanceof ViewGroup.MarginLayoutParams) {
                        ((ViewGroup.MarginLayoutParams) lp).rightMargin = 17;
                        ((ViewGroup.MarginLayoutParams) lp).leftMargin = 17;
                    }
                    carlay.setTag(i);
                    carlay.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            int pos = Integer.parseInt(v.getTag().toString());
                            try {
                                selectedModelID = ((JSONObject) array.get(pos)).getString("model_id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Check whether it is outstation or rental
                            if (selectedModelID.equals("-1")) {
                                simpleWebView_lay.setVisibility(View.VISIBLE);
                                fare_detail_layout.setVisibility(View.GONE);

                                carlay_click(v, 0);
                                try {
                                    final JSONArray array = new JSONArray(SessionSave.getSession(MODEL_DETAILS_UPDATE, getActivity()));

                                    carModel = array.getJSONObject(pos).getString("model_id");
                                    fare_detail.setText(((TextView) v.findViewById(R.id.txt_model1)).getText().toString() + " - " + " " + NC.getResources().getString(R.string.fare_details));
                                    v.findViewById(R.id.bottom_line).setVisibility(View.VISIBLE);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {

                                simpleWebView_lay.setVisibility(View.GONE);

                                fare_detail_layout.setVisibility(View.VISIBLE);

                                select_model(v, pos, 0);
                            }


                        }
                    });

                    try {
                        ((TextView) v.findViewById(R.id.txt_model1)).setText("" + array.getJSONObject(i).getString("model_name"));
                        v.findViewById(R.id.bottom_line).setVisibility(View.VISIBLE);

                        if (!AppCacheImage.loadBitmap(array.getJSONObject(i).getString("unfocus_image"), v.findViewById(R.id.txt_dra_car1))) {
                            Systems.out.println("Image... not avail in cache");
                            new DownloadImageAndsavetoCache(v.findViewById(R.id.txt_dra_car1)).execute(array.getJSONObject(i).getString("unfocus_image"));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (SessionSave.getSession(TaxiUtil.CORPORATE_PASSENGER, getActivity()).equals("1")) {
                        if (array.getJSONObject(i).getString("model_id") != "-1") {
                            id_alls.addView(v);
                        }
                    } else {
                        id_alls.addView(v);
                    }
                    if (i == 0) {
                        firstCar = carlay;
                    }
                    if (i < (array.length() - 1)) {
                        LinearLayout.LayoutParams paramsv = new LinearLayout.LayoutParams(
                                1, ViewGroup.LayoutParams.MATCH_PARENT);
                        View vv = new View(getActivity());
                        vv.setLayoutParams(paramsv);

                        id_alls.addView(vv);
                    }
                }
            }

        } catch (
                Exception e) {
            // TODO: handle exception
            Systems.out.println("Exception cirlce" + e);
            e.printStackTrace();
        }
    }

    /**
     * this class is used to set the car click position
     */

    private void carlay_click(View v, int type) {

        try {
            final JSONArray array = new JSONArray(SessionSave.getSession(MODEL_DETAILS, getActivity()));
            int pos = Integer.parseInt(v.getTag().toString());
            Systems.out.println("postionnnnn " + "carlay_click" + pos);

            int carPos = pos;
            carModel = array.getJSONObject(carPos).getString("model_id");
            int viewCount = 0;
            if (type == 1)
                parent = id_all;
            else
                parent = id_alls;
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (parent.getChildAt(i) instanceof ViewGroup) {
                    ViewGroup vv = (ViewGroup) parent.getChildAt(i);
                    if (i != (pos + viewCount)) {
                        ((TextView) vv.findViewById(R.id.txt_model1)).setTextColor(CL.getColor(getActivity(), R.color.textviewcolor_light));

                       // vv.findViewById(R.id.bottom_line).setBackgroundColor(0xfe0000);

//
                        Systems.out.println("farepage carlayclick " + (i - viewCount));
                        vv.findViewById(R.id.bottom_line).setVisibility(View.VISIBLE);
                        vv.findViewById(R.id.bottom_line_black).setVisibility(View.GONE);

                        Picasso.get().load(array.getJSONObject(i - viewCount).getString("unfocus_image")).error(R.drawable.car2_unfocus).into((ImageView) vv.findViewById(R.id.txt_dra_car1));
                    } else {
                        Systems.out.println("farepage carlayclick* " + (i - viewCount));
                        ((TextView) vv.findViewById(R.id.txt_model1)).setTextColor(CL.getColor(getActivity(), R.color.black));
                        Picasso.get().load(array.getJSONObject(i - viewCount).getString("focus_image")).error(R.drawable.car2_unfocus).into((ImageView) vv.findViewById(R.id.txt_dra_car1));

                vv.findViewById(R.id.bottom_line).setVisibility(View.GONE);
                vv.findViewById(R.id.bottom_line_black).setVisibility(View.VISIBLE);
                       //vv.findViewById(R.id.bottom_line).setBackgroundColor(0xfe0000);

                    }
                } else {
                    viewCount = viewCount + 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method used parse the details about the selected car model.
     */


    private void select_model(View v, int pos, int type) {
        Systems.out.println("postionnnnn " + "select_model" + pos);
        carlay_click(v, type);
        try {
            final JSONArray array = new JSONArray(SessionSave.getSession(MODEL_DETAILS_UPDATE, getActivity()));

           //

            carModel = array.getJSONObject(pos).getString("model_id");
            fare_detail.setText(((TextView) v.findViewById(R.id.txt_model1)).getText().toString() + " - " + " " + NC.getResources().getString(R.string.fare_details));

            v.findViewById(R.id.bottom_line).setVisibility(View.GONE);
            v.findViewById(R.id.bottom_line_black).setVisibility(View.VISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String base_fare = "";
        String min_fare = "";
        String min_km = "";
        String below_above_km = "";
        String below_km = "";
        String above_km = "";
        String night_charge = "";
        String night_timing_from = "";
        String night_timing_to = "";
        String cancel_fare = "";
        String even_charge = "";
        String waiting_charge = "";
        String minute_fare = "";
        String even_timing_from = "";
        String even_timing_to = "";
        try {
            String model = SessionSave.getSession(MODEL_DETAILS_UPDATE, getActivity());
            Systems.out.println(MODEL_DETAILS + model);
            JSONArray model_array = new JSONArray(model);
            if (model != null) {
                for (int i = 0; i < model_array.length(); i++) {
                    if (carModel.equalsIgnoreCase(model_array.getJSONObject(i).getString("model_id"))) {
                        model_array.getJSONObject(i).getString("model_name");
                        base_fare = model_array.getJSONObject(i).getString("base_fare");
                        min_fare = model_array.getJSONObject(i).getString("min_fare");
                        min_km = model_array.getJSONObject(i).getString("min_km");
                        below_above_km = model_array.getJSONObject(i).getString("below_above_km");
                        below_km = model_array.getJSONObject(i).getString("below_km");
                        above_km = model_array.getJSONObject(i).getString("above_km");
                        night_charge = model_array.getJSONObject(i).getString("night_fare");
                        night_timing_from = model_array.getJSONObject(i).getString("night_timing_from");
                        night_timing_to = model_array.getJSONObject(i).getString("night_timing_to");
                        cancel_fare = model_array.getJSONObject(i).getString("cancellation_fare");
                        even_charge = model_array.getJSONObject(i).getString("evening_fare");
                        waiting_charge = model_array.getJSONObject(i).getString("waiting_fare");
                        minute_fare = model_array.getJSONObject(i).getString("minutes_fare");
                        even_timing_from = model_array.getJSONObject(i).getString("evening_timing_from");
                        even_timing_to = model_array.getJSONObject(i).getString("evening_timing_to");
                        boolean km_wise_fare = ((int) Float.parseFloat(model_array.getJSONObject(i).getString("km_wise_fare"))) == 1;
                        if (km_wise_fare) {
                            below_lay.setVisibility(View.GONE);
                            above_lay.setVisibility(View.GONE);
                          //  fare_per_km_lay.setVisibility(View.VISIBLE);
                            far_per_km.setText(NC.getString(R.string.fare_per_km) + " " + SessionSave.getSession("Metric", getActivity()).toLowerCase());
                            far_per_km_txt.setText(SessionSave.getSession("Currency", getActivity()) + model_array.getJSONObject(i).getString("additional_fare_per_km"));
                        } else {
                            below_lay.setVisibility(View.VISIBLE);
                            above_lay.setVisibility(View.VISIBLE);
                         //   fare_per_km_lay.setVisibility(View.GONE);
                            belowkm.setText("" + NC.getResources().getString(R.string.below) + " " + below_above_km + " " + SessionSave.getSession("Metric", getActivity()).toLowerCase());
                            abovekm.setText("" + NC.getResources().getString(R.string.above) + " " + below_above_km + " " + SessionSave.getSession("Metric", getActivity()).toLowerCase());
                        }
                        minkm.setText("" + NC.getResources().getString(R.string.minimum_fare) + " (" + min_km + " " + SessionSave.getSession("Metric", getActivity()).toLowerCase() + ")");
                        basefareTxt.setText(SessionSave.getSession("Currency", getActivity()) + String.format(Locale.UK, "%.2f", Float.parseFloat(base_fare)));
                        minfareTxt.setText(SessionSave.getSession("Currency", getActivity()) + String.format(Locale.UK, "%.2f", Float.parseFloat(min_fare)));
                        belowfareTxt.setText(SessionSave.getSession("Currency", getActivity()) + String.format(Locale.UK, "%.2f", Float.parseFloat(below_km)));
                        abovefareTxt.setText(SessionSave.getSession("Currency", getActivity()) + String.format(Locale.UK, "%.2f", Float.parseFloat(above_km)));
                        cancelfareTxt.setText(SessionSave.getSession("Currency", getActivity()) + String.format(Locale.UK, "%.2f", Float.parseFloat(cancel_fare)));
                        waitingfareTxt.setText(SessionSave.getSession("Currency", getActivity()) + String.format(Locale.UK, "%.2f", Float.parseFloat(waiting_charge)));
                        minutefareTxt.setText(SessionSave.getSession("Currency", getActivity()) + String.format(Locale.UK, "%.2f", Float.parseFloat(minute_fare)));
                        if (night_charge.trim().equals("0"))
                            night.setVisibility(View.GONE);
                        else
                            night.setVisibility(View.VISIBLE);
                        if (even_charge.trim().equals("0"))
                            eve.setVisibility(View.GONE);
                        else
                            eve.setVisibility(View.VISIBLE);
                        nightfareTxt.setText("" + night_charge + "%");
                        evefareTxt.setText("" + even_charge + "%");
                        if (night_charge.equalsIgnoreCase("") || night_charge.equalsIgnoreCase("0"))
                            nighttime.setText("" + NC.getResources().getString(R.string.nit_fare));
                        else
                            nighttime.setText("" + NC.getResources().getString(R.string.nit_fare) + "\n(" + night_timing_from + " " + NC.getResources().getString(R.string.nit_fare_to) + " " + night_timing_to + ")");
                        if (even_charge.equalsIgnoreCase("") || even_charge.equalsIgnoreCase("0"))
                            evefare.setText("" + NC.getResources().getString(R.string.even_charge));
                        else
                            evefare.setText("" + NC.getResources().getString(R.string.even_charge) + "\n(" + even_timing_from + " " + NC.getResources().getString(R.string.nit_fare_to) + " " + even_timing_to + ")");
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

// Update selected and unselected car model UI based on model change.

    /**
     * Slider menu used to move from one activity to another activity.
     */


    private class FareUpdate implements APIResult {
        public FareUpdate(final String url) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON_NoProgress(getActivity(), this, "", true).execute(url);
            fare_progress.setVisibility(View.VISIBLE);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            farecontain.setVisibility(View.VISIBLE);
            fare_progress.setVisibility(View.GONE);
            if (isSuccess) {
                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        final JSONObject array = json.getJSONObject("detail");
                        SessionSave.saveSession(MODEL_DETAILS_UPDATE, "" + array.getJSONArray("model_details"), getActivity());
                        if (firstCar != null)
                            firstCar.performClick();
                    }
                } catch (final JSONException e) {
                    // TODO Auto-generated catch block
                    Log.e("tag", "errrror :" + e.getMessage());
                    e.printStackTrace();
                } catch (final NullPointerException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            } else {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error));
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (mshowDialog != null && mshowDialog.isShowing()) {
                mshowDialog.dismiss();
                mshowDialog = null;

            }
            TaxiUtil.mActivitylist.remove(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onResume() {
        super.onResume();

        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).left_img.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);

        String btnAct = "";
        String btnRjt = "";
        try {
            btnAct = Integer.toHexString(CL.getColor(getActivity(), R.color.button_accept)).substring(2);
            btnRjt = Integer.toHexString(CL.getColor(getActivity(), R.color.button_reject)).substring(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (btnAct != null && btnAct.equals(""))
                btnAct = Integer.toHexString(ContextCompat.getColor(getActivity(), R.color.button_accept)).substring(2);
            if (btnRjt != null && btnRjt.equals(""))
                btnRjt = Integer.toHexString(ContextCompat.getColor(getActivity(), R.color.button_reject)).substring(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String colorCode = "&b_act=" + btnAct + "&b_cal=" + btnRjt + "&new=1";
        simpleWebView.getSettings().setJavaScriptEnabled(true);
        try {
            simpleWebView.loadUrl(SessionSave.getSession(API_BASE, getActivity()) + "package_fare.html/" +/* HttpRequest.Base64.encodeBytes(SessionSave.getSession(PASS_ID, getActivity()).getBytes())*/SessionSave.getSession(PASS_ID, getActivity()) + "/?lang=" + SessionSave.getSession("Lang", getActivity()) + colorCode);
            WebClientClass webViewClient = new WebClientClass();
            simpleWebView.setWebViewClient(webViewClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class WebClientClass extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            callAnim();
            iv_web.setVisibility(View.VISIBLE);
            Systems.out.println("web____starts");


        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            simpleWebView_lay.setVisibility(View.VISIBLE);
            iv_web.setVisibility(View.GONE);
            Systems.out.println("web____stops");

        }

    }

    private void callAnim() {
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv_web);
        if (getActivity() != null) {
            Glide.with(getActivity().getApplicationContext())
                    .load(R.raw.loading_anim)
                    .into(imageViewTarget);
        }
    }

}
