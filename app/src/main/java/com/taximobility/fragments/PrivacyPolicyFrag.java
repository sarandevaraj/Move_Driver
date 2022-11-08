package com.taximobility.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.WebviewAct;
import com.taximobility.util.Colorchange;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;

import org.json.JSONException;
import org.json.JSONObject;

import static com.taximobility.util.ConstantsKt.PASS_ID;

public class PrivacyPolicyFrag extends Fragment {
    private TextView btn_delete_acc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.privacy_policy, container, false);
        Colorchange.ChangeColor((ViewGroup) v, getActivity());
        btn_delete_acc= v.findViewById(R.id.btn_delete_acc);
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getResources().getString(R.string.privacy_settings));
        btn_delete_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userid",SessionSave.getSession(PASS_ID, getActivity()));
//                    jsonObject.put("latitude", 0.0);
//                    jsonObject.put("longitude",0.0);
//                    jsonObject.put("drop_latitude", 0.0);
//                    jsonObject.put("drop_longitude",0.0);
//                    jsonObject.put("pickupplace","erode");
//                    jsonObject.put("dropplace","salem");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                  Need to remove
                Intent in = new Intent(getActivity(), WebviewAct.class);
                in.putExtra("post_params", jsonObject.toString());
                in.putExtra("type","privacy");
                startActivity(in);
            }
        });
        return v;
    }
}
