package com.taximobility.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.TermsAndConditions;
import com.taximobility.features.CToast;
import com.taximobility.features.Validation;
import com.taximobility.interfaces.APIResult;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.tripCancel.CreditCardData;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.FourDigitCardFormatWatcher;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.taximobility.util.ConstantsKt.CREDIT_CARD;
import static com.taximobility.util.ConstantsKt.LANG;
import static com.taximobility.util.ConstantsKt.PASS_ID;

/**
 * Created by developer on 4/29/16.
 * class contains fragment for Addmoney page
 *
 * @author developer
 */

public class AddMoneyFrag extends Fragment implements View.OnClickListener {
    private TextView BackBtn, DoneBut, leftIcon, HeadTitle, paypalTxt;
    private TextView braintreeTxt, brain_terms, pay_terms;
    private EditText pay_cardnameEdt, pay_cvvEdt, pay_cardnumEdt;
    private EditText brain_cvvEdt, brain_cardnumEdt, brain_cardnameEdt;
    private CheckBox brain_isDefaultChk, brain_termsTxt, pay_termsTxt, pay_isDefaultChk;
    private LinearLayout brain_carddetaillay, pay_carddetaillay;
    private Spinner brain_monthspn, brain_yearspn, pay_yearspn, pay_monthspn;
    private Button brain_submitBtn, pay_submitBtn;

    private ArrayAdapter<String> pay_monthadapter, pay_yearadapter;
    private ArrayAdapter<String> brain_monthadapter, brain_yearadapter;

    private String pay_expmonth = "", pay_expyear = "", brain_expmonth = "", brain_expyear = "";
    private String addAmount = "0", promoCode = "", isAdd, clickedPosition = "";
    private String[] brain_monthLst, brain_yearLst, pay_monthLst, pay_yearLst;
    private int curmonth, curyear, brain_isDefault = 1, paymentType = 1, pay_isDefault = 1;
    private boolean pay_monthtouched, pay_yeartouched, brain_monthtouched, brain_yeartouched;
    private Dialog alertmDialog, mshowDialog, dialog;
    private FourDigitCardFormatWatcher fourDigitCardFormatWatcher;
    private Calendar cal;


    private Button removecardBtn;
    public CreditCardData cardData;
    private List<CreditCardData> mCreditCardList = new ArrayList<>();
    private String xCardno, xCvv, xCardName;
    private String mMonth;
    private String mYear;
    private TextView CardnoTxt;
    private static String OriginalCard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.addmoneylay, container, false);
        priorChanges(v);
        Colorchange.ChangeColor((ViewGroup) v, getActivity());

        return v;
    }


    /**
     * method to render view and intialize necessary view for About us page
     *
     * @param v
     */
    public void priorChanges(View v) {
        FontHelper.applyFont(getActivity(), v.findViewById(R.id.cardroot_lay));
        Initialize(v);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Colorchange.ChangeColor((ViewGroup) (((ViewGroup) getActivity()
                        .findViewById(android.R.id.content)).getChildAt(0)), getActivity());
            }
        }, 100);
    }

    /**
     * Intitialize view for the addmoney fragment
     *
     * @param v -> view of the fragment
     */
    public void Initialize(View v) {

        ((MainHomeFragmentActivity) getActivity()).call_image.setVisibility(View.GONE);
        ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.GONE);

        // TODO Auto-generated method stub
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            addAmount = bundle.getString("MONEY");
            promoCode = bundle.getString("PROMOCODE");
            isAdd = bundle.getString("ADD");
            clickedPosition = bundle.getString("POSITION");
            cardData = TaxiUtil.fromJson(bundle.getString("CREDITCARDDETAIL"), CreditCardData.class);
            Systems.out.println("cardData" + cardData.getMonth());
        }
        fourDigitCardFormatWatcher = new FourDigitCardFormatWatcher(getActivity());
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).cancel_b.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).cancel_b.setText(SessionSave.getSession("Currency", getActivity()) + addAmount);
        ((MainHomeFragmentActivity) getActivity()).cancel_b.setOnClickListener(null);

        DoneBut = v.findViewById(R.id.rightIconTxt);
        DoneBut.setText("" + NC.getResources().getString(R.string.amount) + ":\n" + SessionSave.getSession("Currency", getActivity()) + "" + addAmount);
        leftIcon = v.findViewById(R.id.leftIcon);
        leftIcon.setVisibility(View.GONE);
        BackBtn = v.findViewById(R.id.back_text);
        BackBtn.setVisibility(View.VISIBLE);
        HeadTitle = v.findViewById(R.id.header_titleTxt);
        HeadTitle.setText(NC.getResources().getString(R.string.addmoney));
        paypalTxt = v.findViewById(R.id.paypalTxt);
        pay_carddetaillay = v.findViewById(R.id.pay_carddetaillay);
        pay_cardnumEdt = v.findViewById(R.id.pay_cardnumEdt);
        pay_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        pay_monthspn = v.findViewById(R.id.pay_monthspn);
        pay_yearspn = v.findViewById(R.id.pay_yearspn);
        pay_cvvEdt = v.findViewById(R.id.pay_cvvEdt);
        pay_cardnameEdt = v.findViewById(R.id.pay_cardnameEdt);
        pay_isDefaultChk = v.findViewById(R.id.pay_isDefaultChk);
        pay_termsTxt = v.findViewById(R.id.pay_termsTxt);
        pay_submitBtn = v.findViewById(R.id.pay_submitBtn);
        pay_monthLst = getResources().getStringArray(R.array.monthlistary);
        pay_yearLst = new String[20];
        brain_yearLst = new String[20];
        cal = Calendar.getInstance();
        curmonth = cal.get(Calendar.MONTH);
        curyear = cal.get(Calendar.YEAR);
        ArrayList<String> pay_yearLstArr = new ArrayList<>();
        ArrayList<String> pay_monthLstArr = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            pay_yearLst[i] = String.valueOf(curyear + i);
            brain_yearLst[i] = String.valueOf(curyear + i);
        }
        for (int i = 0; i < pay_monthLst.length; i++)
            pay_monthLstArr.add(pay_monthLst[i]);
        for (int i = 0; i < pay_yearLst.length; i++)
            pay_yearLstArr.add(pay_yearLst[i]);
        pay_monthadapter = new FontHelper.MySpinnerAdapterWhite(getActivity(), R.layout.monthitem_spinnerlay, pay_monthLstArr);
        pay_monthspn.setAdapter(pay_monthadapter);
        pay_yearadapter = new FontHelper.MySpinnerAdapterWhite(getActivity(), R.layout.monthitem_spinnerlay, pay_yearLstArr);
        pay_yearspn.setAdapter(pay_yearadapter);
        braintreeTxt = v.findViewById(R.id.braintreeTxt);
        brain_carddetaillay = v.findViewById(R.id.brain_carddetaillay);
        brain_cardnumEdt = v.findViewById(R.id.brain_cardnumEdt);
        brain_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        brain_monthspn = v.findViewById(R.id.brain_monthspn);
        brain_yearspn = v.findViewById(R.id.brain_yearspn);
        brain_cvvEdt = v.findViewById(R.id.brain_cvvEdt);
        brain_cardnameEdt = v.findViewById(R.id.brain_cardnameEdt);
        brain_isDefaultChk = v.findViewById(R.id.brain_isDefaultChk);
        brain_termsTxt = v.findViewById(R.id.brain_termsTxt);
        brain_submitBtn = v.findViewById(R.id.brain_submitBtn);
        brain_monthLst = getResources().getStringArray(R.array.monthlistary);

        removecardBtn = v.findViewById(R.id.removecardBtn);
        CardnoTxt = v.findViewById(R.id.cardnoText);

        if (isAdd.equals("1")) {
            removecardBtn.setVisibility(View.GONE);
        } else {
            removecardBtn.setVisibility(View.GONE);
        }


        ArrayList<String> brain_yearLstArr = new ArrayList<>();
        ArrayList<String> brain_monthLstArr = new ArrayList<>();
        for (int i = 0; i < brain_monthLst.length; i++)
            brain_monthLstArr.add(brain_monthLst[i]);
        for (int i = 0; i < brain_yearLst.length; i++)
            brain_yearLstArr.add(brain_yearLst[i]);
        brain_monthadapter = new FontHelper.MySpinnerAdapterWhite(getActivity(), R.layout.monthitem_spinnerlay, brain_monthLstArr);
        brain_monthspn.setAdapter(brain_monthadapter);
        brain_yearadapter = new FontHelper.MySpinnerAdapterWhite(getActivity(), R.layout.monthitem_spinnerlay, brain_yearLstArr);
        brain_yearspn.setAdapter(brain_yearadapter);
        pay_terms = v.findViewById(R.id.pay_terms);
        brain_terms = v.findViewById(R.id.brain_terms);
        pay_monthspn.setSelection(curmonth);

        pay_monthspn.setSelected(true);
        brain_monthspn.setSelection(curmonth);
        brain_monthspn.setSelected(true);
        pay_cardnumEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (s.toString().startsWith("4")) {
                    if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa"))
                        pay_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.card_visa, 0, 0, 0);
                    else
                        pay_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.card_visa, 0);
                } else if (s.toString().length() >= 2) {
                    int prefix = Integer.parseInt(s.toString().substring(0, 2));
                    if (prefix >= 51 && prefix <= 55) {
                        if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa"))
                            pay_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.card_master, 0, 0, 0);
                        else
                            pay_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.card_master, 0);
                    }
                } else {
                    pay_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                FourDigitCardFormatWatcher.additional(s);
            }
        });
        brain_cardnumEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (s.toString().startsWith("4")) {
                    if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa"))
                        brain_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.card_visa, 0, 0, 0);
                    else
                        brain_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.card_visa, 0);
                } else if (s.toString().length() >= 2) {
                    int prefix = Integer.parseInt(s.toString().substring(0, 2));
                    if (prefix >= 51 && prefix <= 55) {
                        if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa"))
                            brain_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.card_master, 0, 0, 0);
                        else
                            brain_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.card_master, 0);
                    } else {
                        brain_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                } else {
                    brain_cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                FourDigitCardFormatWatcher.additional(s);
            }
        });
        int selection_yearpos = 0;
        for (int i = 0; i < pay_yearLst.length; i++) {
            if (pay_yearLst[i].equalsIgnoreCase(String.valueOf(curyear))) {
                selection_yearpos = i;
            }
        }
        pay_yearspn.setSelection(selection_yearpos);
        pay_yearspn.setSelected(true);
        pay_monthspn.setOnTouchListener((v1, event) -> {
            pay_monthtouched = true;
            pay_expmonth = "" + (curmonth + 1);
            pay_monthadapter.notifyDataSetChanged();
            return false;
        });
        pay_monthspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                TextView txt = ((TextView) view);
                txt.setTextColor(getResources().getColor(R.color.textviewcolor_light));
                txt.setGravity(Gravity.LEFT);
                if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa"))
                    txt.setGravity(Gravity.RIGHT);
                if (!pay_monthtouched)
                    txt.setText("" + NC.getResources().getString(R.string.reg_month));
                else {
                    pay_expmonth = parent.getItemAtPosition(arg2).toString();
                    if (pay_yearspn.getSelectedItem().equals(String.valueOf(curyear)) && Integer.parseInt(pay_expmonth) <= curmonth) {
                        pay_expmonth = String.valueOf(curmonth + 1);
                        pay_monthspn.setSelection(curmonth);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        int brain_selectionyearpos = 0;
        for (int i = 0; i < brain_yearLst.length; i++) {
            if (brain_yearLst[i].equalsIgnoreCase(String.valueOf(curyear))) {
                brain_selectionyearpos = i;
            }
        }
        brain_yearspn.setSelection(brain_selectionyearpos);
        brain_yearspn.setSelected(true);
        brain_monthspn.setOnTouchListener((v12, event) -> {
            brain_monthtouched = true;
            brain_expmonth = "" + curmonth;
            brain_monthadapter.notifyDataSetChanged();
            return false;
        });
        brain_monthspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                // TODO Auto-generated method stub
                TextView txt = ((TextView) view);
                txt.setTextColor(getResources().getColor(R.color.textviewcolor_light));
                txt.setGravity(Gravity.LEFT);
                if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa"))
                    txt.setGravity(Gravity.RIGHT);
                if (!brain_monthtouched)
                    txt.setText("" + NC.getResources().getString(R.string.reg_month));
                else {
                    brain_expmonth = parent.getItemAtPosition(arg2).toString();
                    if (brain_yearspn.getSelectedItem().equals(String.valueOf(curyear)) && Integer.parseInt(brain_expmonth) <= curmonth) {
                        brain_expmonth = String.valueOf(curmonth);
                        brain_monthspn.setSelection(curmonth);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        pay_yearspn.setOnTouchListener((v13, event) -> {
            pay_yeartouched = true;
            pay_expyear = "" + curyear;
            pay_yearadapter.notifyDataSetChanged();
            return false;
        });
        pay_yearspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                // TODO Auto-generated method stub
                TextView txt = ((TextView) view);
                txt.setTextColor(getResources().getColor(R.color.textviewcolor_light));
                txt.setGravity(Gravity.LEFT);
                if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa"))
                    txt.setGravity(Gravity.RIGHT);
                if (!pay_yeartouched)
                    txt.setText("" + NC.getResources().getString(R.string.reg_year));
                else {
                    if (parent.getItemAtPosition(arg2).toString().equals(String.valueOf(curyear)) && Integer.valueOf(pay_expmonth) < (curmonth + 1))
                        pay_monthspn.setSelection(curmonth);
                    pay_expyear = parent.getItemAtPosition(arg2).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        brain_yearspn.setOnTouchListener((v14, event) -> {
            brain_yeartouched = true;
            brain_expyear = "" + curyear;
            brain_yearadapter.notifyDataSetChanged();
            return false;
        });
        brain_yearspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                // TODO Auto-generated method stub
                TextView txt = ((TextView) view);
                txt.setTextColor(getResources().getColor(R.color.textviewcolor_light));
                txt.setGravity(Gravity.LEFT);
                if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa"))
                    txt.setGravity(Gravity.RIGHT);
                if (!brain_yeartouched)
                    txt.setText("" + NC.getResources().getString(R.string.reg_year));
                else {

                    if (parent.getItemAtPosition(arg2).toString().equals(String.valueOf(curyear)) && Integer.valueOf(brain_expmonth) < (curmonth + 1))
                        brain_monthspn.setSelection(curmonth);
                    brain_expyear = parent.getItemAtPosition(arg2).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        pay_isDefaultChk.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                pay_isDefault = 1;
            else
                pay_isDefault = 0;
        });
        brain_isDefaultChk.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                brain_isDefault = 1;
            else
                brain_isDefault = 0;
        });


        if (isAdd.equals("0")) {
            xCardno = cardData.getCard();
            OriginalCard = cardData.getOriginal_cardno();
            xCvv = cardData.getCvv();
            mMonth = cardData.getMonth();
            mYear = cardData.getYear();
            xCardName = cardData.getName();
            CardnoTxt.setVisibility(View.VISIBLE);
            pay_cardnumEdt.setVisibility(View.GONE);
            pay_cardnameEdt.setText(xCardName);
            CardnoTxt.setText(xCardno);


            try {
                pay_monthtouched = true;
                pay_yeartouched = true;
                pay_monthspn.setSelection(Integer.parseInt(mMonth) - 1);
                if (Integer.parseInt(mYear) > curyear)
                    pay_yearspn.setSelection(Integer.parseInt(mYear) - curyear);
                else
                    CToast.ShowToast(getActivity(), NC.getString(R.string.your_card_expiry));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        setonclickListener();
    }


    /*
     * this method is used to set click event for all buttons
     * */
    private void setonclickListener() {

        pay_submitBtn.setOnClickListener(this);
        brain_submitBtn.setOnClickListener(this);
        pay_carddetaillay.setOnClickListener(this);
        BackBtn.setOnClickListener(this);
        paypalTxt.setOnClickListener(this);
        braintreeTxt.setOnClickListener(this);
        brain_termsTxt.setOnClickListener(this);
        pay_termsTxt.setOnClickListener(this);
        brain_terms.setOnClickListener(this);
        pay_terms.setOnClickListener(this);
    }

    /**
     * this method which is used to call api to add money through paypal and braintree
     */

    private void AddMoneyData() {

        if (paymentType == 1) {
            String pay_cardNumber = "";
            String pay_cvvNumber = pay_cvvEdt.getText().toString().trim();
            if (isAdd.equals("1")) {
                pay_cardNumber = pay_cardnumEdt.getText().toString().trim().replaceAll("\\s", "");
            } else {
                if (xCardno.contains("X")) {
                    xCardno = OriginalCard;
                }
                pay_cardNumber = xCardno;

            }

            String pay_cardName = pay_cardnameEdt.getText().toString().trim();
            if (Validation.validations(Validation.ValidateAction.isValidCard, getActivity(), pay_cardNumber))
                if (Validation.validations(Validation.ValidateAction.isNullMonth, getActivity(), pay_expmonth))
                    if (Validation.validations(Validation.ValidateAction.isNullYear, getActivity(), pay_expyear))
                        if (Validation.validations(Validation.ValidateAction.isValidCvv, getActivity(), pay_cvvNumber))
                            if (!pay_termsTxt.isChecked()) {
                                dialog = Utility.alert_view_dialog(getActivity(), "Message", "" + NC.getResources().getString(R.string.agree_the_terms_and_condition),
                                        "" + NC.getResources().getString(R.string.ok), "", true, (dialog, which) -> dialog.dismiss(), (dialog, which) -> dialog.dismiss(), "");
                            } else {
                                try {
                                    JSONObject j = new JSONObject();
                                    j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
                                    j.put("creditcard_no", pay_cardNumber);
                                    if (pay_expmonth.trim().equals("0"))
                                        j.put("expmonth", "1");
                                    else
                                        j.put("expmonth", pay_expmonth);
                                    j.put("expyear", pay_expyear);
                                    j.put("creditcard_cvv", pay_cvvNumber);
                                    j.put("savecard", "0");
                                    j.put("default", pay_isDefault);
                                    j.put("promo_code", promoCode);
                                    j.put("cardholder_name", pay_cardName);
                                    j.put("money", addAmount);
                                    j.put("payment_type", paymentType);
                                    final String url = "type=passenger_wallet_addmoney";
                                    new AddMoney(url, j);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }
                            }
        } else if (paymentType == 2) {
            String brain_cardNumber = brain_cardnumEdt.getText().toString().trim().replaceAll("\\s", "");
            String brain_cvvNumber = brain_cvvEdt.getText().toString().trim();
            String brain_cardName = brain_cardnameEdt.getText().toString().trim();
            if (Validation.validations(Validation.ValidateAction.isValidCard, getActivity(), brain_cardNumber))
                if (Validation.validations(Validation.ValidateAction.isNullMonth, getActivity(), brain_expmonth))
                    if (Validation.validations(Validation.ValidateAction.isNullYear, getActivity(), brain_expyear))
                        if (Validation.validations(Validation.ValidateAction.isValidCvv, getActivity(), brain_cvvNumber))
                            if (!brain_termsTxt.isChecked()) {
                                dialog = Utility.alert_view_dialog(getActivity(), "Message", "" + NC.getResources().getString(R.string.agree_the_terms_and_condition),
                                        "" + NC.getResources().getString(R.string.ok), "", true, (dialog, which) -> dialog.dismiss(), (dialog, which) -> dialog.dismiss(), "");
                            } else {
                                try {
                                    JSONObject j = new JSONObject();
                                    j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
                                    j.put("creditcard_no", brain_cardNumber);
                                    if (brain_expmonth.trim().equals("0"))
                                        j.put("expmonth", "1");
                                    else
                                        j.put("expmonth", brain_expmonth);
                                    j.put("expyear", brain_expyear);
                                    j.put("creditcard_cvv", brain_cvvNumber);
                                    j.put("savecard", "0");
                                    j.put("default", brain_isDefault);
                                    j.put("promo_code", promoCode);
                                    j.put("cardholder_name", brain_cardName);
                                    j.put("money", addAmount);
                                    j.put("payment_type", paymentType);
                                    final String url = "type=passenger_wallet_addmoney";
                                    new AddMoney(url, j);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }
                            }
        }
    }

    /*
     * this method is used to call TermsConditions api
     * */
    private void TermsConditions() {

        try {
            String url = "&type=dynamic_page&pagename=3&device_type=1";
            new ShowWebpage(url, null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * This class used to register with card details
     * <p/>
     * This class used to register with card details
     * <p/>
     *
     * @author developer
     */
    private class AddMoney implements APIResult {
        private AddMoney(final String url, JSONObject data) {

            new APIService_Retrofit_JSON(getActivity(), this, data, false).execute(url);

        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            if (isSuccess) {
                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        SessionSave.saveSession(CREDIT_CARD, "" + json.getString("credit_card_status"), getActivity());
                        SessionSave.saveSession("Credit_new_ok_alert", json.getString("message"), getActivity());
                        Systems.out.println("cherry_card_status" + SessionSave.getSession("Credit_new_ok_alert", getActivity()));
                        dialog = Utility.alert_view_dialog(getActivity(), "Message", "" + json.getString("message"),
                                "" + NC.getResources().getString(R.string.ok), "", true, (dialog, which) -> {
                                    Fragment addMoneyNewFrag = requireActivity().getSupportFragmentManager().findFragmentByTag("AddMoneyNewFrag");
                                    if (addMoneyNewFrag != null)
                                        requireActivity().getSupportFragmentManager().beginTransaction().remove(addMoneyNewFrag).commitNow();
                                    Fragment fragment = requireActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrag);
                                    if (fragment instanceof WalletFrag) {
                                        requireActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commitNow();
                                    }
                                    requireActivity().getSupportFragmentManager().popBackStack();
                                    getActivity().onBackPressed();
                                }, (dialog, which) -> dialog.dismiss(), "");

                    } else
                        dialog = Utility.alert_view_dialog(getActivity(), "Message", "" + json.getString("message"),
                                "" + NC.getResources().getString(R.string.ok), "", true, (dialog, which) -> dialog.dismiss(), (dialog, which) -> dialog.dismiss(), "");

                } catch (final JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                getActivity().runOnUiThread(() -> CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error)));
            }
        }
    }

    /**
     * class to show webpage for terms and condition
     *
     * @author developer
     */
    private class ShowWebpage implements APIResult {
        public ShowWebpage(final String string, JSONObject data) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON(getActivity(), this, true, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession(LANG, getActivity()) + "&encode=" + SessionSave.getSession("encode", getActivity()) + string).execute();

        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            try {
                if (isSuccess) {
                    final Intent intent = new Intent(getActivity(), TermsAndConditions.class);
                    final Bundle bundle = new Bundle();
                    intent.putExtra("content", result);
                    bundle.putString("name", NC.getString(R.string.termcond));
                    bundle.putBoolean("status", true);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    getActivity().runOnUiThread(() -> CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error)));
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_text:
                // finish();
                getActivity().onBackPressed();
                break;
            case R.id.pay_submitBtn:
                AddMoneyData();
                break;
            case R.id.brain_submitBtn:
                AddMoneyData();
                break;
            case R.id.pay_terms:
                TermsConditions();
                break;
            case R.id.brain_terms:
                TermsConditions();
                break;
            case R.id.paypalTxt:
                if (pay_carddetaillay.isShown()) {
                    if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa")) {
                        paypalTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.left_arrow, 0, R.drawable.mon_paypal, 0);
                    } else
                        paypalTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mon_paypal, 0, R.drawable.right_arrow, 0);
                    pay_carddetaillay.setVisibility(View.GONE);
                } else {
                    paymentType = 1;
                    pay_carddetaillay.setVisibility(View.VISIBLE);
                    brain_carddetaillay.setVisibility(View.GONE);

                    if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa")) {
                        braintreeTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.left_arrow, 0, R.drawable.mon_braintree, 0);
                    } else
                        braintreeTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mon_braintree, 0, R.drawable.right_arrow, 0);
                    if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa")) {
                        paypalTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.down_arrow, 0, R.drawable.mon_paypal, 0);
                    } else {
                        paypalTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mon_paypal, 0, R.drawable.down_arrow, 0);
                    }
                }
                break;
            case R.id.braintreeTxt:
                if (brain_carddetaillay.isShown()) {
                    if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa")) {
                        braintreeTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.left_arrow, 0, R.drawable.mon_braintree, 0);
                    } else
                        braintreeTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mon_braintree, 0, R.drawable.right_arrow, 0);
                    brain_carddetaillay.setVisibility(View.GONE);
                } else {
                    paymentType = 2;
                    brain_carddetaillay.setVisibility(View.VISIBLE);
                    pay_carddetaillay.setVisibility(View.GONE);
                    if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa"))
                        paypalTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.left_arrow, 0, R.drawable.mon_paypal, 0);
                    else
                        paypalTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mon_paypal, 0, R.drawable.right_arrow, 0);
                    if (SessionSave.getSession(LANG, getActivity()).equals("ar") || SessionSave.getSession(LANG, getActivity()).equals("fa"))
                        braintreeTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.down_arrow, 0, R.drawable.mon_braintree, 0);
                    else
                        braintreeTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mon_braintree, 0, R.drawable.down_arrow, 0);
                }
            default:
                break;
        }
    }

    /**
     * this method is used to display alert dialog
     */
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
            button_success.setOnClickListener(v -> alertmDialog.dismiss());
            button_failure.setOnClickListener(v -> alertmDialog.dismiss());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {

        if (mshowDialog != null && mshowDialog.isShowing()) {
            mshowDialog.dismiss();
            mshowDialog = null;

        }
        if (dialog != null) {
            Utility.closeDialog(dialog);
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).cancel_b.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).cancel_b.setText(SessionSave.getSession("Currency", getActivity()) + addAmount);
        ((MainHomeFragmentActivity) getActivity()).cancel_b.setOnClickListener(null);
    }

    @Override
    public void onStop() {
        if (alertmDialog != null)
            if (alertmDialog.isShowing())
                alertmDialog.dismiss();

        super.onStop();
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getResources().getString(R.string.payment));

    }
}
