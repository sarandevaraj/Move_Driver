package com.taximobility.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.features.CToast;
import com.taximobility.features.Validation;
import com.taximobility.interfaces.APIResult;
import com.taximobility.interfaces.FragPopFront;
import com.taximobility.interfaces.RazerpayListener;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.util.CL;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.NetworkStatus;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import static com.taximobility.util.ConstantsKt.LANG;
import static com.taximobility.util.ConstantsKt.PASS_ID;

/**
 * Created by developer on 4/26/16.
 * Class for wallet page fragment
 */
public class WalletFrag extends Fragment implements View.OnClickListener, FragPopFront , RazerpayListener {

    private LinearLayout Donelay, loading;
    private TextView addmoneyBut;
    private EditText addmoneyEdt;
    private TextView BackBtn, walletbalTxt, leftIcon, HeadTitle;
    private TextView monoption1, monoption2, monoption3, procodeTxt;
    private Double walletAmount;
    private String wallet_amount_range = "", wallet_msg, wallet_amount1 = "", wallet_amount2, wallet_amount3;
    private String promoCode = "";
    private long range1, range2;
    private Dialog alertmDialog, mshowDialog, mDialog, dialog;
    private long addMoney = 0L;
    private boolean isbalancechecked;
    private boolean receivedStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.walletlay, container, false);
        findViewById(v);
        return v;
    }

    public void findViewById(View v) {
        FontHelper.applyFont(getActivity(), v.findViewById(R.id.rootContain));
        HeadTitle = v.findViewById(R.id.header_titleTxt);
        HeadTitle.setText(NC.getResources().getString(R.string.wallet));
        leftIcon = v.findViewById(R.id.leftIcon);
        leftIcon.setVisibility(View.GONE);
        BackBtn = v.findViewById(R.id.back_text);
        BackBtn.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.wallet));
       // Glide.with(this).load(SessionSave.getSession("image_path", getActivity()) + "walletPageIcon.png").apply(RequestOptions.errorOf(R.drawable.wallet2)).into((ImageView) v.findViewById(R.id.walletPageIcon));
        Initialize(v);
    }

    public void Initialize(View v) {

        ((MainHomeFragmentActivity) getActivity()).call_image.setVisibility(View.GONE);
        ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.GONE);

        // TODO Auto-generated method stub

     //   Colorchange.ChangeColor((ViewGroup) v, getActivity());


        Donelay = v.findViewById(R.id.rightlay);
        Donelay.setVisibility(View.INVISIBLE);
        ((TextView) v.findViewById(R.id.cur_sym)).setText(SessionSave.getSession("Currency", getActivity()));

        walletbalTxt = v.findViewById(R.id.walletbalTxt);
       loading = v.findViewById(R.id.loading);
        ImageView iv = v.findViewById(R.id.giff);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
        Glide.with(getActivity())
                .load(R.raw.loading_anim)
                .into(imageViewTarget);

        addmoneyBut = v.findViewById(R.id.addmoneyBut);
        addmoneyEdt = v.findViewById(R.id.addmoneyEdt);
        monoption1 = v.findViewById(R.id.monoption1);
        monoption2 = v.findViewById(R.id.monoption2);
        monoption3 = v.findViewById(R.id.monoption3);
        procodeTxt = v.findViewById(R.id.procodeTxt);

        setOnclickListener();

        addmoneyEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                    addmoneyBut.performClick();
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainHomeFragmentActivity) getActivity()).left_img.setVisibility(View.VISIBLE);
        CheckWallet();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public void onStop() {

        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        //((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.payment_complete));
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);

        super.onStop();
    }

    private void setOnclickListener() {

        BackBtn.setOnClickListener(this);
        addmoneyBut.setOnClickListener(this);
        monoption1.setOnClickListener(this);
        monoption2.setOnClickListener(this);
        monoption3.setOnClickListener(this);
        procodeTxt.setOnClickListener(this);

        SpannableString content = new SpannableString(NC.getString(R.string.have_promocode).trim());
        content.setSpan(new UnderlineSpan(), 0, NC.getString(R.string.have_promocode).trim().length(), 0);
//        procodeTxt.setText(Html.fromHtml("<p><u>" + (NC.getString(R.string.have_promocode).trim()) + "<p><u>"));
      //  procodeTxt.setText(Html.fromHtml("<p>" + (NC.getString(R.string.have_promocode).trim()) + "<p>"));
    }

    /**
     * Calls api for wallet amount enqury
     */
    private void CheckWallet() {

        try {
            isbalancechecked = true;
            JSONObject j = new JSONObject();
            j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
            String url = "type=passenger_wallet";
          new WalletBal(url, j);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void trigger_FragPopFront() {

        Systems.out.println("chv=ckng_____over");
        Systems.out.println("AddMoneyFrag trigger_FragPopFront");
        wallet_msg = SessionSave.getSession("Credit_new_ok_alert", getActivity());
        if (wallet_msg.length() != 0) {
            SessionSave.saveSession("Credit_new_ok_alert", "", getActivity());
            Systems.out.println("cherry_card_status_card" + SessionSave.getSession("Credit_new_ok_alert", getActivity()));

            if (dialog != null)
                Utility.closeDialog(dialog);
            dialog = Utility.alert_view_dialog(getActivity(),
                    "Message",
                    "" + wallet_msg,
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

        CheckWallet();
        ((MainHomeFragmentActivity) getActivity()).cancel_b.setVisibility(View.GONE);
        if (addmoneyEdt != null)
            addmoneyEdt.setText("");
        if (monoption1 != null) {
            monoption1.setBackgroundResource(R.drawable.draw_edittext_bg);
            monoption2.setBackgroundResource(R.drawable.draw_edittext_bg);
            monoption3.setBackgroundResource(R.drawable.draw_edittext_bg);
            monoption1.setTextColor(CL.getColor(getActivity(), R.color.hintcolor));
            monoption2.setTextColor(CL.getColor(getActivity(), R.color.hintcolor));
            monoption3.setTextColor(CL.getColor(getActivity(), R.color.hintcolor));
        }
    }

    public void alert_view(Context mContext, String title, String message, String success_txt, String failure_txt) {
        try {
            final View view = View.inflate(mContext, R.layout.alert_view, null);
            alertmDialog = new Dialog(mContext, R.style.dialogwinddow);
            alertmDialog.setContentView(view);
            alertmDialog.setCancelable(true);
         //   FontHelper.applyFont(mContext, alertmDialog.findViewById(R.id.alert_id));
       //     Colorchange.ChangeColor(alertmDialog.findViewById(R.id.alert_id), getActivity());
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

    /**
     * this method is used to show the promo code dialog and execute api
     */

    private void ShowPromoDilaog() {

        try {

            final View view = View.inflate(getActivity(), R.layout.alert_view, null);
            mDialog = new Dialog(getActivity(), R.style.dialogwinddow);
            mDialog.setContentView(view);
            mDialog.setCancelable(true);
            mDialog.show();
            Colorchange.ChangeColor(mDialog.findViewById(R.id.alert_id), getActivity());
           // FontHelper.applyFont(getActivity(), mDialog.findViewById(R.id.alert_id));
            final TextView titleTxt = mDialog.findViewById(R.id.title_text);
            final TextView msgTxt = mDialog.findViewById(R.id.message_text);
            msgTxt.setVisibility(View.GONE);
            final EditText promocodeEdt = mDialog.findViewById(R.id.forgotmail);
            final Button OK = mDialog.findViewById(R.id.button_success);
            final Button Cancel = mDialog.findViewById(R.id.button_failure);
            Cancel.setVisibility(View.GONE);
            promocodeEdt.setVisibility(View.VISIBLE);
            OK.setText("" + NC.getResources().getString(R.string.ok));
            titleTxt.setText("" + NC.getResources().getString(R.string.reg_promocode));

            int maxLengthpromoCode = getResources().getInteger(R.integer.promoMaxLength);

            promocodeEdt.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

            promocodeEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthpromoCode)});

            InputFilter[] editFilters = promocodeEdt.getFilters();
            InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
            newFilters[editFilters.length] = new InputFilter.AllCaps();
            promocodeEdt.setFilters(newFilters);
            promocodeEdt.setHint("" + NC.getResources().getString(R.string.reg_enterprcode));
            OK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    try {
                        promoCode = promocodeEdt.getText().toString();
                        if (Validation.validations(Validation.ValidateAction.isNullPromoCode, getActivity(), promoCode)) {
                            mDialog.dismiss();
                            JSONObject j = new JSONObject();
                            j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
                            j.put("promo_code", promoCode);
                            String url = "type=check_valid_promocode";
                            new CheckPromoCode(url, j);
                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            });
            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    mDialog.dismiss();
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Slider menu used to move from one activity to another activity.
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_text:
                // menu.toggle();
                break;
            case R.id.addmoneyBut:
                if (addmoneyEdt.getText().toString().trim().length() != 0) {

                    try {
                        addMoney = Long.parseLong(addmoneyEdt.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    /*
                    try {
                        JSONObject j = new JSONObject();
                        j.put("passenger_id", SessionSave.getSession("Id", getActivity()));
                        j.put("amount", addMoney);
                        final String url = "type=get_order_id_razorpay";
                        new WalletRazorpay(url, j);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }

                     */
//                    if (addMoney < range1 || addMoney > range2) {
//                        dialog = Utility.alert_view_dialog(getActivity(),
//                                "" + NC.getResources().getString(R.string.message),
//                                "" + NC.getString(R.string.amount_between),
//                                "" + NC.getResources().getString(R.string.ok), "",
//                                true, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }, "");
//                    } else {
//
                    AddMoneyFragNew addMoneyFrag = new AddMoneyFragNew();
                    Bundle bundle = new Bundle();
                    bundle.putString("MONEY", "" + addMoney);
                    bundle.putString("PROMOCODE", promoCode);
                    addMoneyFrag.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, addMoneyFrag,"AddMoneyNewFrag").addToBackStack(null).commit();


//                        AddMoneyFrag addMoneyFrag = new AddMoneyFrag();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("MONEY", "" + addMoney);
//                        bundle.putString("PROMOCODE", promoCode);
//                        addMoneyFrag.setArguments(bundle);
//                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, addMoneyFrag).addToBackStack(null).commit();
//                    }
                } else {
                    dialog = Utility.alert_view_dialog(getActivity(),
                            "" + NC.getResources().getString(R.string.message),
                            "" + NC.getResources().getString(R.string.enter_amount),
                            "" + NC.getResources().getString(R.string.ok), "",
                            true, new DialogInterface.OnClickListener() {
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
                break;
            case R.id.monoption1:
                monoption1.setBackgroundResource(R.drawable.draw_select_bg);
                monoption2.setBackgroundResource(R.drawable.draw_edittext_bg);
                monoption3.setBackgroundResource(R.drawable.draw_edittext_bg);
                monoption1.setTextColor(CL.getColor(getActivity(), R.color.button_accept));
                monoption2.setTextColor(CL.getColor(getActivity(), R.color.hintcolor));
                monoption3.setTextColor(CL.getColor(getActivity(), R.color.hintcolor));
                addmoneyEdt.setText("" + wallet_amount1);
                break;
            case R.id.monoption2:
                monoption1.setBackgroundResource(R.drawable.draw_edittext_bg);
                monoption2.setBackgroundResource(R.drawable.draw_select_bg);
                monoption3.setBackgroundResource(R.drawable.draw_edittext_bg);
                monoption2.setTextColor(CL.getColor(getActivity(), R.color.button_accept));
                monoption1.setTextColor(CL.getColor(getActivity(), R.color.hintcolor));
                monoption3.setTextColor(CL.getColor(getActivity(), R.color.hintcolor));
                addmoneyEdt.setText("" + wallet_amount2);
                break;
            case R.id.monoption3:
                monoption1.setBackgroundResource(R.drawable.draw_edittext_bg);
                monoption2.setBackgroundResource(R.drawable.draw_edittext_bg);
                monoption3.setBackgroundResource(R.drawable.draw_select_bg);
                monoption3.setTextColor(CL.getColor(getActivity(), R.color.button_accept));
                monoption2.setTextColor(CL.getColor(getActivity(), R.color.hintcolor));
                monoption1.setTextColor(CL.getColor(getActivity(), R.color.hintcolor));
                addmoneyEdt.setText("" + wallet_amount3);
                break;
            case R.id.procodeTxt:
                ShowPromoDilaog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        TaxiUtil.mActivitylist.remove(this);
        try {
            if (mshowDialog != null && mshowDialog.isShowing()) {
                mshowDialog.dismiss();
                mshowDialog = null;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dialog != null)
            Utility.closeDialog(dialog);
        super.onDestroy();
    }

    @Override
    public void responseReceived(Boolean receivedStatus) {
//        this.receivedStatus = receivedStatus;
//        Systems.out.println("receivedStatus "+receivedStatus+"isbalancechecked "+isbalancechecked);
//        if (receivedStatus && isbalancechecked)
//        {
//            CheckWallet();
//        }
    }

    //

    /**
     * This class used to check wallet balance
     * <p>
     * This class used to check wallet balance
     * <p>
     *
     * @author developer
     */
    private class WalletBal implements APIResult {
        private WalletBal(final String url, JSONObject data) {
            if (NetworkStatus.isOnline(getActivity())) {
                new APIService_Retrofit_JSON(getActivity(), this, data, false).execute(url);
            } else {
                Log.e("No Internet Available", "no internet");
            }
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            if (isSuccess) {
                try {
                    loading.setVisibility(View.GONE);
                    addmoneyBut.setVisibility(View.VISIBLE);
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        walletAmount = Double.parseDouble(json.getString("wallet_amount"));
                        wallet_amount_range = json.getJSONObject("amount_details").getString("wallet_amount_range");
                        wallet_amount1 = json.getJSONObject("amount_details").getString("wallet_amount1");
                        wallet_amount2 = json.getJSONObject("amount_details").getString("wallet_amount2");
                        wallet_amount3 = json.getJSONObject("amount_details").getString("wallet_amount3");
                        String[] rangeary = wallet_amount_range.split("-");
                        Systems.out.println("wallet wallet amount" + Float.parseFloat(json.getString("wallet_amount")));
                        SessionSave.saveWalletAmount(Float.parseFloat(json.getString("wallet_amount")), getActivity());
                        if (rangeary.length > 1) {
                            range1 = Long.parseLong(rangeary[0].replaceAll(",", ""));
                            range2 = Long.parseLong(rangeary[1].replaceAll(",", ""));
                        }
                        addmoneyEdt.setHint("" + NC.getResources().getString(R.string.amount_between));
                        monoption1.setText("" + SessionSave.getSession("Currency", getActivity()) + wallet_amount1);
                        monoption2.setText("" + SessionSave.getSession("Currency", getActivity()) + wallet_amount2);
                        monoption3.setText("" + SessionSave.getSession("Currency", getActivity()) + wallet_amount3);
                        walletbalTxt.setText("" + SessionSave.getSession("Currency", getActivity()) + String.format(Locale.UK, "%.2f", walletAmount));

                        HeadTitle.setTypeface(HeadTitle.getTypeface(), Typeface.BOLD);


                    } else {
                        walletbalTxt.setText("" + SessionSave.getSession("Currency", getActivity()) + "0.00");
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (result != null) {
                    dialog = Utility.alert_view_dialog(getActivity(),
                            "Message",
                            "" + result,
                            "" + NC.getResources().getString(R.string.ok), "",
                            true, new DialogInterface.OnClickListener() {
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
            }
        }
    }

    /**
     * This class used to check valid promo code
     * <p>
     * This class used to check valid promo code
     * <p>
     *
     * @author developer
     */
    private class CheckPromoCode implements APIResult {
        private CheckPromoCode(final String url, JSONObject data) {
            new APIService_Retrofit_JSON(getActivity(), this, data, false, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession(LANG, getActivity()) + "&" + url).execute();
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            if (isSuccess) {
                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {

                        dialog = Utility.alert_view_dialog(getActivity(),
                                "Message",
                                "" + json.getString("message"),
                                "" + NC.getResources().getString(R.string.ok), "",
                                true, new DialogInterface.OnClickListener() {
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


                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    CToast.ShowToast(getActivity(), json.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        promoCode = "";
                    }
                } catch (final JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    promoCode = "";
                }
            } else {
                promoCode = "";
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error));
                    }
                });
            }
        }
    }

//   Razor Pay

    private class WalletRazorpay implements APIResult {
        private WalletRazorpay(final String url, JSONObject data) {
            if (NetworkStatus.isOnline(getActivity())) {
                showDialog();
                new APIService_Retrofit_JSON(getActivity(), this, data, false).execute(url);
            } else {
                Log.e("No Internet Available", "no internet");
            }
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            closeDialog();
            // TODO Auto-generated method stub
            if (isSuccess) {
                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        //System.out.println("Wallet_amoutn...."+json.getString("order_id"));
                        System.out.println("response...." + json.toString());
                        isbalancechecked = false;
                        ((MainHomeFragmentActivity) getActivity()).walletPayment(addMoney, promoCode, "1", json.getString("order_id"));
                    } else {

                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (result != null) {
                    dialog = Utility.alert_view_dialog(getActivity(),
                            "Message",
                            "" + result,
                            "" + NC.getResources().getString(R.string.ok), "",
                            true, new DialogInterface.OnClickListener() {
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
            }
        }
    }

    public void showDialog() {
        try {
            if (NetworkStatus.isOnline(getActivity())) {
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
        } catch (Exception e) {

        }

    }

    public void closeDialog() {
        try {
            if (mDialog != null)
                if (mDialog.isShowing())
                    mDialog.dismiss();
        } catch (Exception e) {

        }
    }
}


