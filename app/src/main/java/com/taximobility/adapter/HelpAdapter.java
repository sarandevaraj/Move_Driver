package com.taximobility.adapter;

import android.app.Dialog;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.data.apiData.ApiRequestData;
import com.taximobility.data.apiData.HelpResponse;
import com.taximobility.data.apiData.StandardResponse;
import com.taximobility.features.CToast;
import com.taximobility.interfaces.DialogInterface;
import com.taximobility.service.CoreClient;
import com.taximobility.service.RetrofitCallbackClass;
import com.taximobility.util.AppController;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.TaxiUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by developer on 1/11/16.
 * use to populate the help list directed from trip detail page
 */
public class HelpAdapter extends BaseAdapter implements DialogInterface {
    private final Context context;
    private final LayoutInflater mInflater;
    HelpResponse data;
    String trip_id;
    ViewGroup selectedView;

    // constructor
    public HelpAdapter(Context helpadapter, HelpResponse data, String trip_id) {
        // TODO Auto-generated constructor stub
        context = helpadapter;
        mInflater = LayoutInflater.from(context);
        this.data = data;
        this.trip_id = trip_id;

    }

    @Override
    public int getCount() {
        return data.details.size();
    }

    @Override
    public Object getItem(int i) {
        return TaxiUtil.mHelplist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    // View holder class member this contains in every row in list.
    class ViewHolder {
        public ImageView drop_pin;
        private EditText comment;
        private Button pay_submitBtn;
        public LinearLayout help_comments_containter;
        public TextView helptext;
        public ViewGroup help_container;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        final HelpAdapter.ViewHolder mHolder;
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.helpfrag_item, viewGroup, false);

            mHolder = new ViewHolder();
            FontHelper.applyFont(context, convertView.findViewById(R.id.help_container));
            mHolder.helptext = convertView.findViewById(R.id.helptext);
            mHolder.helptext.setText(data.details.get(position).help_content);
            mHolder.pay_submitBtn = convertView.findViewById(R.id.pay_submitBtn);
            mHolder.help_container = convertView.findViewById(R.id.help_container);
            mHolder.help_comments_containter = convertView.findViewById(R.id.help_comments_containter);
            mHolder.comment = convertView.findViewById(R.id.comment);
            convertView.setTag(mHolder);
        } else {
            mHolder = (HelpAdapter.ViewHolder) convertView.getTag();
        }
        Colorchange.ChangeColor((ViewGroup) convertView, context);
        mHolder.helptext.setTag(mHolder.help_comments_containter);
        mHolder.pay_submitBtn.setTag(mHolder.comment);
        mHolder.comment.setTag(data.details.get(position).help_id);
        mHolder.helptext.setOnClickListener(view -> {
            LinearLayout ll = ((LinearLayout) view.getTag());
            selectedView = ll;
            notifyDataSetChanged();

        });
        if (selectedView == mHolder.help_comments_containter) {
            if (mHolder.help_comments_containter.getVisibility() == View.VISIBLE) {
                mHolder.help_comments_containter.setVisibility(View.GONE);
            } else {
                mHolder.help_comments_containter.setVisibility(View.VISIBLE);
            }
        } else {
            mHolder.help_comments_containter.setVisibility(View.GONE);
        }


        mHolder.pay_submitBtn.setOnClickListener(view -> {
            EditText tt = ((EditText) view.getTag());
            if (tt.getText().toString().trim().equals(""))
                CToast.ShowToast(context, NC.getString(R.string.enter_valid_comment));
            else
                callHelpSubmit(tt.getText().toString(), tt.getTag().toString());
        });

        return convertView;
    }

    /**
     * Calls api for submit resson for help and user comment
     *
     * @param comment ---> comments by user.
     * @param help_id ---> Help reason id.
     */
    private void callHelpSubmit(String comment, String help_id) {
//        CoreClient client = new ServiceGenerator(context).createService(CoreClient.class);
        CoreClient client = AppController.getInstance().getApiManagerWithEncryptBaseUrl();
        ApiRequestData.HelpSubmit request = new ApiRequestData.HelpSubmit();
        request.setTrip_id(trip_id);
        request.setHelp_id(help_id);
        request.setHelp_comment(comment);
        Call<StandardResponse> response = client.helpSubmit(TaxiUtil.COMPANY_KEY, request, SessionSave.getSession("Lang", context));
        response.enqueue(new RetrofitCallbackClass<>(context, new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (data != null)
                    if (context != null) {
                        if (data.status.toString().trim().equals("1")) {
                            ((AppCompatActivity) context).onBackPressed();
                        }
                        CToast.ShowToast(context, data.message);
                    }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {

            }
        }));

    }

    @Override
    public void onSuccess(Dialog dialog, String resultcode) {
        dialog.dismiss();

    }

    @Override
    public void onFailure(Dialog dialog, String resultcode) {
        dialog.dismiss();

    }

}
