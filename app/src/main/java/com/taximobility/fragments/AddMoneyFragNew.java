package com.taximobility.fragments;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.adapter.CreditCardAdapter;
import com.taximobility.features.CToast;
import com.taximobility.interfaces.APIResult;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.service.CoreClient;
import com.taximobility.service.RetrofitCallbackClass;
import com.taximobility.tripCancel.CreditCardData;
import com.taximobility.tripCancel.CreditCardRepository;
import com.taximobility.util.AppController;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.NetworkStatus;
import com.taximobility.util.SessionSave;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.PASS_NAME;

public class AddMoneyFragNew extends Fragment implements CreditCardAdapter.RecyclerViewItemClickListener {


    private List<CreditCardData> mCreditCardList = new ArrayList<>();
    private CreditCardRepository creditCardRepository;
    Dialog dialog1;
    private CreditCardAdapter adapter;
    private RecyclerView mCardList;
    private LinearLayout cardListLay;
    private WebView webviewww;
    private Dialog mDialog;


    private String addAmount = "0", promoCode = "";


    /**
     * handler is used to update the UI change whenever user performed the ADD,EDIT and OPEN the particular card details.
     */

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == 0) {
                adapter.submitList(mCreditCardList);
                mCardList.setAdapter(adapter);
            }
            if (msg.what == 1) {
                ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.payment));
                try {
                    JSONObject j = new JSONObject();
                    j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
                    j.put("card_type", "");
                    j.put("default", "");
                    new GetCardlist("type=get_credit_card_details", j);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                cardListLay.setVisibility(View.VISIBLE);
                ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
            }
        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.addmoneylay_new, container, false);
        priorChanges(v);
        Colorchange.ChangeColor((ViewGroup) v, getActivity());
        FontHelper.applyFont(getActivity(), v.findViewById(R.id.rootContain));

        return v;
    }

    private void priorChanges(View v) {
        creditCardRepository = CreditCardRepository.getRepository(requireContext());
        Initialize(v);
    }

    private void Initialize(View v) {
        ((MainHomeFragmentActivity) getActivity()).call_image.setVisibility(View.GONE);
        ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.GONE);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            addAmount = bundle.getString("MONEY");
            promoCode = bundle.getString("PROMOCODE");
        }


        // TODO Auto-generated method stub
        try {
            JSONObject j = new JSONObject();
            j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
            j.put("amount", addAmount);
            j.put("passenger_name", SessionSave.getSession(PASS_NAME, getActivity()));
            j.put("passenger_email", SessionSave.getSession("Email", getActivity()));
            j.put("promo_code", promoCode);
            new GetCardlist("type=get_order_id_test", j);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        mCardList = v.findViewById(R.id.cardList);
        cardListLay = v.findViewById(R.id.cardlistLay);
        cardListLay.setVisibility(View.VISIBLE);
        webviewww = v.findViewById(R.id.webview);

        adapter = new CreditCardAdapter(requireActivity(), this);
    }


    /**
     * GetCardlist class is used to get the passenger card details and update it into UI
     */
    public class GetCardlist implements APIResult {
        public GetCardlist(String string, JSONObject data) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON(getActivity(), this, data, false).execute(string);
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            try {
                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        redirectwebpage(json.getString("transaction_id"));
                    }
                } else {
                    requireActivity().runOnUiThread(() -> CToast.ShowToast(requireActivity(), NC.getString(R.string.server_con_error)));
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void redirectwebpage(String url) {

        webviewww.setVisibility(View.VISIBLE);
        webviewww.loadUrl(url);
        showDialog();
        WebSettings webSettings = webviewww.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webviewww.setWebViewClient(new MyWebViewClient());

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                Uri uri = Uri.parse(url);
                //String msg = uri.getQueryParameter("source");
                System.out.println("message is===" + url);

                if (url.contains("status") && url.contains("successful")) {
                    SearchApiCall(url);
                    showDialog();
                } else {
                    CToast.ShowToast(getActivity(), "Payment Failed");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            try {
                closeDialog();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void SearchApiCall(String url) {
        if (NetworkStatus.isOnline(getActivity())) {
            String baseUrl = url;
            CoreClient polyline = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl();
            polyline.getJsonbyWholeUrl("no-cache", baseUrl)
                    .enqueue(new RetrofitCallbackClass<JsonObject>(getActivity(), new Callback<JsonObject>() {
                        @Override
                        public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                            closeDialog();
                            if (response.isSuccessful()) {
                                String result = response.body().toString();
                                try {
                                    JSONObject json = new JSONObject(result);
                                    if (json.getInt("status") == 1) {
                                        CToast.ShowToast(getActivity(), json.getString("message"));
                                        ((MainHomeFragmentActivity) getActivity()).cancel_b.setVisibility(View.GONE);
                                        ((MainHomeFragmentActivity) getActivity()).toolbar_logo.setVisibility(View.GONE);
                                        ((MainHomeFragmentActivity) getActivity()).toolbar_titletm.setVisibility(View.GONE);
                                        ((MainHomeFragmentActivity) getActivity()).toolbar_title.setVisibility(View.VISIBLE);
                                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new WalletFrag()).addToBackStack(null).commit();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            } else {
                                CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<JsonObject> call, Throwable t) {
                            // ShowToast.center(getActivity(), t.getLocalizedMessage());
                        }
                    }));
        } else {
            CToast.ShowToast(getActivity(), NC.getString(R.string.check_internet_connection));
        }

    }

    public void showDialog() {
        try {
            if (NetworkStatus.isOnline(getActivity())) {
                if (getActivity() != null) {

                    if (mDialog != null && mDialog.isShowing())
                        mDialog.dismiss();
                    View view = View.inflate(getActivity(), R.layout.progress_bar, null);
                    mDialog = new Dialog(getActivity(), R.style.dialogwinddow);
                    mDialog.setContentView(view);
                    mDialog.setCancelable(false);

                    mDialog.show();

                    ImageView iv = mDialog.findViewById(R.id.giff);
                    DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                    Glide.with(getActivity())
                            .load(R.raw.loading_anim)
                            .into(imageViewTarget);
                }


            }
        } catch (Exception e) {

        }

    }

    public void closeDialog() {
        try {
            if (mDialog != null)
                if (mDialog.isShowing())
                    mDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getResources().getString(R.string.payment));
        ((MainHomeFragmentActivity) getActivity()).cancel_b.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).cancel_b.setText(SessionSave.getSession("Currency", getActivity()) + addAmount);
        ((MainHomeFragmentActivity) getActivity()).cancel_b.setOnClickListener(null);
    }


    @Override
    public void onClick(CreditCardData cardData, int position) {
        if (mCreditCardList.get(position).getCard().equals("" + NC.getString(R.string.addcard))) {
            AddMoneyFrag addMoneyFrag = new AddMoneyFrag();
            ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.addmoney));
            Bundle bundle = new Bundle();
            bundle.putString("MONEY", "" + addAmount);
            bundle.putString("PROMOCODE", promoCode);
            bundle.putString("ADD", "1");
            bundle.putString("POSITION", "" + position);
            bundle.putString("CREDITCARDDETAIL", new Gson().toJson(mCreditCardList.get(position)));
            addMoneyFrag.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, addMoneyFrag).addToBackStack(null).commit();
        } else {
            ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.addmoney));
            AddMoneyFrag addMoneyFrag = new AddMoneyFrag();
            Bundle bundle = new Bundle();
            bundle.putString("MONEY", "" + addAmount);
            bundle.putString("PROMOCODE", promoCode);
            bundle.putString("ADD", "0");
            bundle.putString("POSITION", "" + position);
            bundle.putString("CREDITCARDDETAIL", new Gson().toJson(cardData));
            addMoneyFrag.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, addMoneyFrag).addToBackStack(null).commit();
        }
    }


    @Override
    public void onStop() {

        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.wallet));
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);

        super.onStop();
    }


}
