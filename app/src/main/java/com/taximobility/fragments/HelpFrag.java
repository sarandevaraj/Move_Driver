package com.taximobility.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.adapter.HelpAdapter;
import com.taximobility.data.apiData.HelpResponse;
import com.taximobility.service.CoreClient;
import com.taximobility.service.RetrofitCallbackClass;
import com.taximobility.util.AppController;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.TaxiUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * this class is used to show whether the user face some inconvenience
 */

public class HelpFrag extends Fragment {
    // Class members declarations.
    private TextView AddFavImg;
    private TextView CancelTxt;
    private ListView List;

    private LinearLayout rightlay;
    private LinearLayout favlay;
    TextView back_text;
    public Dialog mDialog;
    private ImageView favourite_loading;
    private HelpAdapter adapter;
    private String trip_id;

    // Set the layout to activity.

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.helplay, container, false);

        Bundle b = this.getArguments();
        if (b.getString("trip_id") != null) {
            trip_id = b.getString("trip_id");
        }
        priorChanges(v);
        Colorchange.ChangeColor((ViewGroup) v, getActivity());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.help));
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);

        //((MainHomeFragmentActivity) getActivity()).small_title(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainHomeFragmentActivity) getActivity()).small_title(false);
    }

    public void priorChanges(View v) {
        // super.priorChanges();
        FontHelper.applyFont(getActivity(), v.findViewById(R.id.frame_lay));
        back_text = v.findViewById(R.id.back_text);
        back_text.setVisibility(View.VISIBLE);
        rightlay = v.findViewById(R.id.rightlay);
        rightlay.setVisibility(View.GONE);
        TextView HeadTitle = v.findViewById(R.id.header_titleTxt);
        HeadTitle.setText(NC.getResources().getString(R.string.menu_favourites));
        CancelTxt = v.findViewById(R.id.leftIcon);
        CancelTxt.setVisibility(View.GONE);
        Initialize(v);
    }

    // Initialize the views on layout
    public void Initialize(View v) {
        // TODO Auto-generated method stub
        FontHelper.applyFont(getActivity(), v.findViewById(R.id.fav_lay2));
        favourite_loading = v.findViewById(R.id.favourite_loading);

        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(favourite_loading);
        Glide.with(getActivity())
                .load(R.raw.loading_anim)
                .into(imageViewTarget);

        favlay = v.findViewById(R.id.editfavlay);

        favlay.setVisibility(View.VISIBLE);
        AddFavImg = v.findViewById(R.id.addIconTxt);
        List = v.findViewById(R.id.list);
        List.setDivider(null);
        // To add new favorite place.Move from this activity to edit favorite activity.

        getHelp();
    }

    @Override
    public void onStop() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.onStop();
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");

        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).small_title(true);
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.trip_summary));
    }

    /**
     * this method is used to call the help api
     */

    private void getHelp() {
        CoreClient client = null;

//        client = new ServiceGenerator(getActivity()).createService(CoreClient.class);
        client = AppController.getInstance().getApiManagerWithEncryptBaseUrl();
        Call<HelpResponse> coreResponse = null;
        coreResponse = client.helpContent(TaxiUtil.COMPANY_KEY, SessionSave.getSession("Lang", getActivity()));

        coreResponse.enqueue(new RetrofitCallbackClass<>(getActivity(), new Callback<HelpResponse>() {
            @Override
            public void onResponse(Call<HelpResponse> call, retrofit2.Response<HelpResponse> response) {
                HelpResponse data = null;
                data = response.body();
                favourite_loading.setVisibility(View.GONE);
                if (data != null) {
                    try {
                        if (getView() != null) {
                            favourite_loading.setVisibility(View.GONE);

                            adapter = new HelpAdapter(getActivity(), data, trip_id);
                            List.setAdapter(adapter);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<HelpResponse> call, Throwable t) {
                t.printStackTrace();
            }
        }));
    }

}
