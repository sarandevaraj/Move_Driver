package com.taximobility.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.adapter.PromoAdapter;
import com.taximobility.data.apiData.PromoDataList;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;

/**
 * Created by developer on 18/8/17.
 */

public class PromoList extends Fragment {
    private RecyclerView list_view;
    PromoDataList dataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.promo_list, container, false);
        list_view = v.findViewById(R.id.list_view);
        PromoDataList promoDataLists;
        if (!SessionSave.getSession(TaxiUtil.PROMO_LIST, getActivity()).trim().equals("")) {
            promoDataLists = TaxiUtil.fromJson(SessionSave.getSession(TaxiUtil.PROMO_LIST, getActivity()), PromoDataList.class);
         //   Systems.out.println("heelllll" + promoDataLists.promoDatas.size());

            dataList = new PromoDataList();
            for (int i = 0; i < promoDataLists.promoDatas.size(); i++) {
                Systems.out.println("__________PPO" + promoDataLists.promoDatas.get(i).getExpiry_date() + "__" + SessionSave.getSession("current_time_local", getActivity(), 0));
                if (promoDataLists.promoDatas.get(i).getExpiry_date() > SessionSave.getSession("current_time_local", getActivity(), 0)) {
                    dataList.promoDatas.add(promoDataLists.promoDatas.get(i));
                }
            }
            if(dataList.promoDatas.size()==0)
                SessionSave.saveSession(TaxiUtil.PROMO_LIST, "", getActivity());
            else
            SessionSave.saveSession(TaxiUtil.PROMO_LIST, TaxiUtil.toString(dataList), getActivity());
            list_view.setAdapter(new PromoAdapter(getActivity(), dataList));
            list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        }


        return v;
    }

    @Override
    public void onResume() {
        ((MainHomeFragmentActivity) getActivity()).toolbarRightIcon(false);
        ((MainHomeFragmentActivity) getActivity()).toolbar_title.setText(NC.getString(R.string.promo_code));
        super.onResume();
    }

    @Override
    public void onStop() {
        ((MainHomeFragmentActivity) getActivity()).toolbar_title.setText(NC.getString(R.string.menu_profile));
        ((MainHomeFragmentActivity) getActivity()).toolbar_title.setText(NC.getString(R.string.menu_profile));
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).toolbarRightIcon(true);
        super.onStop();
    }
}
