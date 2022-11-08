package com.taximobility.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.TermsAndConditions;
import com.taximobility.adapter.CreditCardAdapter;
import com.taximobility.bookingmodule.BookTaxiHomePage;
import com.taximobility.features.CToast;
import com.taximobility.features.Validation;
import com.taximobility.interfaces.APIResult;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.tripCancel.CreditCardData;
import com.taximobility.tripCancel.CreditCardRepository;
import com.taximobility.util.CL;
import com.taximobility.util.Colorchange;
import com.taximobility.util.DatePicker_CardExpiry;
import com.taximobility.util.DrawableJava;
import com.taximobility.util.FontHelper;
import com.taximobility.util.FourDigitCardFormatWatcher;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.taximobility.util.ConstantsKt.CREDIT_CARD;
import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.PASS_NAME;


/**
 * this method is used to show the payment to user
 */

public class PaymentOptionFrag extends Fragment implements DatePicker_CardExpiry.DateDialogInterface, CreditCardAdapter.RecyclerViewItemClickListener {
    // Class members declarations.
    private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
    private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
    private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
    private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
    private static final char DIVIDER = ' ';
    public static boolean Add;
    private static String Type = "";
    private static String cardId;
    private static int DefaultCheck;
    private static String OriginalCard;
    String c = "";
    String[] cardtype;
    Dialog dialog1;
    private LinearLayout CancelBtn;
    private Button DoneBtn;
    private TextView CancelTxt;
    private TextView HeadTitle, passengername_card;
    private LinearLayout CarddetailLay;
    private LinearLayout ListLay;
    private Spinner CardtypeSpn;
    private Spinner pay_monthspn;
    private Spinner pay_yearspn;
    private EditText CardnoEdt;
    private EditText CvvEdt;
    private RecyclerView mCardList;
    private CheckBox pay_termsTxt;
    private TextView termsTxtCash, total_cards;
    private String mMonth;
    private String mYear;
    private int cardPos;
    private ArrayAdapter<String> card_adapter;
    private boolean pay_monthtouched;
    private boolean pay_yeartouched;
    private CheckBox Defaultcheck;
    private CreditCardAdapter adapter;
    private String xCardno, xCvv;
    private Button bookingsBtn;
    private TextView back_text;
    private Dialog mshowDialog;
    private Dialog alertmDialog;
    private TextView ontxt;
    private ArrayAdapter<String> pay_monthadapter;
    private ArrayAdapter<String> pay_yearadapter;
    private String[] pay_monthLst;
    private String[] pay_yearLst;
    private Calendar cal;
    private int curmonth;
    private int curyear;
    private EditText pay_cardnameEdt;
    private TextView CardnoTxt;
    private Button removecardBtn;
    private List<CreditCardData> mCreditCardList = new ArrayList<>();
    private CreditCardRepository creditCardRepository;
    ArrayList<String> pay_yearLstArr = new ArrayList<>();
    ArrayList<String> pay_monthLstArr = new ArrayList<>();

    private String addAmount = "0", promoCode = "";
    private TextView add_new_card;
    private LinearLayout ll_add_card;

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
                DoneBtn.setVisibility(View.GONE);
                ll_add_card.setVisibility(View.VISIBLE);
                Add = false;
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
                removecardBtn.setVisibility(View.GONE);
                CarddetailLay.setVisibility(View.GONE);
                ListLay.setVisibility(View.VISIBLE);
                HeadTitle.setText(NC.getResources().getString(R.string.payment));
                CancelTxt.setText(NC.getResources().getString(R.string.back));
                CancelTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_back, 0, 0, 0);
                ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
            }
        }

    };
    private View v;
    // Set the layout to activity.
    private FrameLayout edit_bg_card;
    // The callback received when the user "sets" the date in the dialog
  /*  private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            mYear = year;
            mMonth = monthOfYear;
            updateDisplay();
        }
    };*/
    private DatePicker datePicker;

    static boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
        boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
        for (int i = 0; i < s.length(); i++) { // chech that every element is right
            if (i > 0 && (i + 1) % dividerModulo == 0) {
                isCorrect &= divider == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;
    }

    static String buildCorrecntString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }
        return formatted.toString();
    }

    static char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.paymentoptionlay, container, false);

        priorChanges(v);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {

//            }
//        },100);
        return v;
    }

    public void priorChanges(View v) {
        creditCardRepository = CreditCardRepository.getRepository(requireContext());
        DoneBtn = v.findViewById(R.id.saveB);
        DoneBtn.setVisibility(View.GONE);
        back_text = v.findViewById(R.id.back_text);
        back_text.setVisibility(View.VISIBLE);
        CancelTxt = v.findViewById(R.id.leftIcon);
        CancelTxt.setVisibility(View.GONE);
        HeadTitle = v.findViewById(R.id.header_titleTxt);
        pay_monthspn = v.findViewById(R.id.pay_monthspn);
        pay_yearspn = v.findViewById(R.id.pay_yearspn);
        pay_termsTxt = v.findViewById(R.id.pay_termsTxt);

        total_cards = v.findViewById(R.id.total_cards);

        passengername_card = v.findViewById(R.id.passengername_card);
        termsTxtCash = v.findViewById(R.id.termsTxtCash);
        add_new_card = v.findViewById(R.id.add_new_card);
        ll_add_card = v.findViewById(R.id.ll_add_card);
        add_new_card.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.add_card));
                HeadTitle.setText(NC.getString(R.string.payment));
                CardtypeSpn.setAdapter(card_adapter);
                pay_cardnameEdt.setText("");
                pay_monthtouched = false;
                pay_yeartouched = false;
                pay_monthspn.setSelection(0);
                pay_yearspn.setSelection(0);
                Type = "";
                Add = true;
                CardnoTxt.setText("");
                CvvEdt.setText("");
                xCvv = "";
                xCardno = "";
                ListLay.setVisibility(View.GONE);
                DoneBtn.setVisibility(View.VISIBLE);
                if (adapter.getItemCount() != 1) {
                    Defaultcheck.setChecked(false);
                    Defaultcheck.setEnabled(true);
                    checkChange(false);
                } else {
                    Defaultcheck.setChecked(true);
                    Defaultcheck.setEnabled(false);
                    checkChange(true);
                }
                pay_termsTxt.setChecked(false);
                CarddetailLay.setVisibility(View.VISIBLE);
                CardnoTxt.setText(NC.getString(R.string.cardnumber));
                Calendar cal = Calendar.getInstance();
                DoneBtn.setText(NC.getString(R.string.save));
                ll_add_card.setVisibility(View.VISIBLE);
            }
        });
//sandeep
        termsTxtCash.setPaintFlags(termsTxtCash.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        edit_bg_card = v.findViewById(R.id.edit_bg_card);
        HeadTitle.setText(NC.getResources().getString(R.string.payment));

        FontHelper.applyFont(getActivity(), v.findViewById(R.id.headlayout));


        Initialize(v);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Colorchange.ChangeColor((ViewGroup)(PaymentOptionFrag.this.v),getActivity());
                Colorchange.ChangeColor((ViewGroup) (((ViewGroup) getActivity()
                        .findViewById(android.R.id.content)).getChildAt(0)), getActivity());
                DrawableJava.draw_edittext_bg(edit_bg_card, CL.getColor(getActivity(), R.color.header_bgcolor), CL.getColor(getActivity(), R.color.header_bgcolor));
            }
        }, 100);

        //   Colorchange.ChangeColor((ViewGroup)v,getActivity());
    }

    // Initialize the views on layout
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void Initialize(View v) {

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
            j.put("card_type", "");
            j.put("default", "");
            new GetCardlist("type=get_credit_card_details", j);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        TaxiUtil.current_act = "PaymentOptionsAct";
        //   FontHelper.applyFont(getActivity(), v.findViewById(R.id.payment_contain));

        adapter = new CreditCardAdapter(requireActivity(), this);
        CancelBtn = v.findViewById(R.id.leftIconTxt);
        mCardList = v.findViewById(R.id.list);

        // DoneBtn.setBackground(NC.getResources().getDrawable(R.drawable.draw_back_header_bgcolor));

        DoneBtn.setText(NC.getResources().getString(R.string.edit));
        //  DoneBtn.setBackground(NC.getResources().getDrawable(R.drawable.draw_back_header_bgcolor));
        ontxt = v.findViewById(R.id.ontxt);
        CarddetailLay = v.findViewById(R.id.carddetail);
        ListLay = v.findViewById(R.id.cardlist);
        CarddetailLay.setVisibility(View.GONE);

        ListLay.setVisibility(View.VISIBLE);
        CardtypeSpn = v.findViewById(R.id.cardtypeSpn);
        CardnoEdt = v.findViewById(R.id.cardnoEdt);
        pay_cardnameEdt = v.findViewById(R.id.pay_cardnameEdt);
        passengername_card.setText("Hi" + " " + SessionSave.getSession(PASS_NAME, getActivity()));
        CardnoTxt = v.findViewById(R.id.cardnoText);
        CardnoTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CardnoEdt.setText("");
                CardnoEdt.setHint(CardnoTxt.getText().toString());
                v.setVisibility(View.GONE);
            }
        });

        termsTxtCash.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                TermsConditions();
            }
        });

        CardnoEdt.addTextChangedListener(new FourDigitCardFormatWatcher(getActivity()));

        CvvEdt = v.findViewById(R.id.cvvEdt);
        Defaultcheck = v.findViewById(R.id.pay_checkBox1);
        bookingsBtn = v.findViewById(R.id.bookingBtn);
        removecardBtn = v.findViewById(R.id.removecardBtn);

        pay_monthLst = getResources().getStringArray(R.array.monthlistary);
        pay_yearLst = new String[20];
        cal = Calendar.getInstance();
        curmonth = cal.get(Calendar.MONTH);
        curyear = cal.get(Calendar.YEAR);

        for (int i = 0; i < 20; i++) {
            pay_yearLst[i] = String.valueOf(curyear + i);
        }
        for (int i = 0; i < pay_monthLst.length; i++)
            pay_monthLstArr.add(pay_monthLst[i]);
        for (int i = 0; i < pay_yearLst.length; i++)
            pay_yearLstArr.add(pay_yearLst[i]);
        pay_monthadapter = new FontHelper.MySpinnerAdapterWhite(getActivity(), R.layout.monthitem_spinnerlay, pay_monthLstArr);
        pay_monthspn.setAdapter(pay_monthadapter);
        pay_yearadapter = new FontHelper.MySpinnerAdapterWhite(getActivity(), R.layout.monthitem_spinnerlay, pay_yearLstArr);
        pay_yearspn.setAdapter(pay_yearadapter);


        int selection_yearpos = 0;
        for (int i = 0; i < pay_yearLst.length; i++) {
            if (pay_yearLst[i].equalsIgnoreCase(String.valueOf(curyear))) {
                selection_yearpos = i;
            }
        }

        pay_yearspn.setSelection(selection_yearpos);
        pay_yearspn.setSelected(true);
        pay_monthspn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                // TODO Auto-generated method stub
                pay_monthtouched = true;
                mMonth = "" + (curmonth + 1);
                pay_monthadapter.notifyDataSetChanged();
                return false;
            }
        });
        pay_monthspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                // TODO Auto-generated method stub
                TextView txt = ((TextView) view);
                txt.setTextColor(getResources().getColor(R.color.textviewcolor_light));
                txt.setGravity(Gravity.LEFT);
                if (SessionSave.getSession("Lang", getActivity()).equals("fa") || SessionSave.getSession("Lang", getActivity()).equals("ar"))
                    txt.setGravity(Gravity.RIGHT);

                if (!pay_monthtouched) {
                    txt.setText("" + NC.getResources().getString(R.string.reg_month));
                    txt.setTextColor(CL.getColor(getActivity(), R.color.sub_heading));
                } else {
                    Systems.out.println("________mm" + curmonth + "___" + mMonth + "___" + curyear + "___" + parent.getItemAtPosition(arg2).toString().trim());

                    mMonth = parent.getItemAtPosition(arg2).toString();
                    if (pay_yearspn.getSelectedItem().equals(String.valueOf(curyear)) && Integer.parseInt(mMonth) <= curmonth) {
                        mMonth = String.valueOf(curmonth + 1);
                        pay_monthspn.setSelection(curmonth);
                    } else if (!pay_yeartouched)
                        pay_monthspn.setSelection(curmonth);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                Systems.out.println("________mm" + curmonth + "___" + mMonth + "___" + curyear + "___");
                if (!pay_yeartouched)
                    pay_monthspn.setSelection(curmonth);
            }
        });

        pay_yearspn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                // TODO Auto-generated method stub
                pay_yeartouched = true;
                mYear = "" + curyear;
                pay_yearadapter.notifyDataSetChanged();
                return false;
            }
        });
        pay_yearspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                // TODO Auto-generated method stub
                TextView txt = ((TextView) view);
                txt.setTextColor(getResources().getColor(R.color.textviewcolor_light));
                //    txt.setGravity(Gravity.CENTER);
                txt.setGravity(Gravity.LEFT);
                if (SessionSave.getSession("Lang", getActivity()).equals("fa") || SessionSave.getSession("Lang", getActivity()).equals("ar"))
                    txt.setGravity(Gravity.RIGHT);
                if (!pay_yeartouched) {
                    txt.setText("" + NC.getResources().getString(R.string.reg_year));
                    txt.setTextColor(CL.getColor(getActivity(), R.color.sub_heading));
                } else {
                    Systems.out.println("________" + curmonth + "___" + mMonth + "___" + curyear + "___" + parent.getItemAtPosition(arg2).toString().trim());
                    if (parent.getItemAtPosition(arg2).toString().trim().equals(String.valueOf(curyear)) && Integer.valueOf(mMonth) < (curmonth + 1))
                        pay_monthspn.setSelection(curmonth);
                    mYear = parent.getItemAtPosition(arg2).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        // To move from getActivity() activity to taxi search activity.
        bookingsBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i;
                if (!SessionSave.getSession("trip_id", getActivity()).equals("")) {
                    showLoading(getActivity());
                    //  i = new Intent(getActivity(), OngoingTrip.class);
//                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
//                    startActivity(i);
//                    finish();
                } else {
                    //showLoading(getActivity());
                    Systems.out.println("Nan BackStatck check" + "BookTaxiHomePage homePage()4");
                    ((MainHomeFragmentActivity) getActivity()).homePage();
                    //  i = new Intent(getActivity(), BookTaxiAct.class);
//                    overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
//                    startActivity(i);
//                    finish();
                }
            }
        });

        // To change the card number edit text value on focus.
        CardnoEdt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    if (CardnoEdt.getText().toString().contains("X"))
                        CardnoEdt.setText("");

                } else {
                    if (CardnoEdt.getText().length() <= 0) {
//                        CardnoEdt.setText(xCardno);
                        CardnoTxt.setText(xCardno);
                        CardnoTxt.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        CardnoEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (s.toString().startsWith("4")) {
                    if (SessionSave.getSession("Lang", getActivity()).equals("fa") || SessionSave.getSession("Lang", getActivity()).equals("ar"))
                        CardnoEdt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.card_visa, 0, 0, 0);
                    else
                        CardnoEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.card_visa, 0);
                } else if (s.toString().length() >= 2) {
                    int prefix = Integer.parseInt(s.toString().substring(0, 2));
                    if (prefix >= 51 && prefix <= 55) {
                        if (SessionSave.getSession("Lang", getActivity()).equals("fa") || SessionSave.getSession("Lang", getActivity()).equals("ar"))
                            CardnoEdt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.card_master, 0, 0, 0);
                        else
                            CardnoEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.card_master, 0);
                    } else {
                        CardnoEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                } else {
                    CardnoEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrecntString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }
        });
        // To change the CVV number edit text value on focus.
        CvvEdt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    if (CvvEdt.getText().toString().contains("X"))
                        CvvEdt.setText("");
                } else {
                    if (CvvEdt.getText().length() <= 0) {
                        CvvEdt.setText(xCvv);
                    }
                }
            }
        });
        // To set the default card for payment on check.
        Defaultcheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean checked) {
                // TODO Auto-generated method stub
                if (checked) {
                    checkChange(true);
                    DefaultCheck = 1;
                } else {
                    checkChange(false);
                    DefaultCheck = 0;
                }
            }
        });
        // To show the Slider menu for move from one activity to another activity.
        CancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                HeadTitle.setText(NC.getResources().getString(R.string.payment));
                if (CarddetailLay.isShown()) {
                    CardnoTxt.setVisibility(View.VISIBLE);
                    mHandler.sendEmptyMessage(1);
                    return;
                } else {
                    // menu.toggle();
                }
            }
        });

        // To add the passengers card details or edit the card details based on the boolean value.
        DoneBtn.setOnClickListener(new OnClickListener() {
            private String cardno;
            private String cvv;

            @Override
            public void onClick(View v) {

                try {

                    if (Add) {
                        cardno = (CardnoEdt.getText().toString().trim()).replaceAll("\\s", "");
                        cvv = CvvEdt.getText().toString().trim();
                        if (Type.equals("" + NC.getResources().getString(R.string.personalcard))) {
                            Type = "P";
                        } else if (Type.equals("" + NC.getResources().getString(R.string.businesscard))) {
                            Type = "B";
                        }
                /*        if (Type.equals("")) {
                            //alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.select_card_type), "" + NC.getResources().getString(R.string.ok), "");

                            dialog1 = Utility.alert_view_dialog(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.select_card_type),
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
                        }*/
                        if (Validation.validations(Validation.ValidateAction.isValidCard, getActivity(), cardno))
                            if (Validation.validations(Validation.ValidateAction.isNullMonth, getActivity(), mMonth)) {
                                if (Validation.validations(Validation.ValidateAction.isNullYear, getActivity(), mYear)) {
                                    if (Validation.validations(Validation.ValidateAction.isValidCvv, getActivity(), cvv))
                                        if (!pay_termsTxt.isChecked())
                                            //alert_view(getActivity(), "Message", "" + NC.getResources().getString(R.string.agree_the_terms_and_condition), "" + NC.getResources().getString(R.string.ok), "");

                                            dialog1 = Utility.alert_view_dialog(getActivity(), "Message", "" + NC.getResources().getString(R.string.agree_the_terms_and_condition),
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


                                        else {
                                            try {
                                                JSONObject j = new JSONObject();
                                                j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
                                                j.put("email", SessionSave.getSession("Email", getActivity()));
                                                j.put("creditcard_no", cardno);
                                                j.put("expdatemonth", (mMonth));
                                                j.put("expdateyear", mYear);
                                                j.put("creditcard_cvv", cvv);
                                                j.put("card_type", Type);
                                                j.put("default", DefaultCheck);
                                                j.put("card_holder_name", pay_cardnameEdt.getText().toString());
                                                Log.e("_r_", j.toString());

                                                String url = "type=add_card_details";
                                                new CreditCard(url, j);
                                            } catch (Exception e) {
                                                // TODO: handle exception
                                                e.printStackTrace();
                                            }
                                        }
                                }
                            }
                    } else {
                        if (CardnoTxt.getVisibility() == View.VISIBLE)
                            cardno = OriginalCard;
                        else
                            cardno = (CardnoEdt.getText().toString().trim()).replaceAll("\\s", "");

                        Systems.out.println("Pass Api" + cardno);
                        cvv = CvvEdt.getText().toString().trim();
                        if (Type.trim().equals("" + NC.getResources().getString(R.string.personalcard))) {
                            Type = "P";
                        } else if (Type.trim().equals("" + NC.getResources().getString(R.string.businesscard))) {
                            Type = "B";
                        }
                        if (Type.equals("")) {
                            // alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.select_card_type), "" + NC.getResources().getString(R.string.ok), "");

                            dialog1 = Utility.alert_view_dialog(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.select_card_type),
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

                        if (Validation.validations(Validation.ValidateAction.isValidCard, getActivity(), cardno))
                            if (Validation.validations(Validation.ValidateAction.isNullMonth, getActivity(), mMonth)) {
                                if (Validation.validations(Validation.ValidateAction.isNullYear, getActivity(), mYear)) {
                                    if (Validation.validations(Validation.ValidateAction.isValidCvv, getActivity(), cvv)) {
                                        if (cardno.contains("X")) {
                                            cardno = OriginalCard;
                                        }
                                        if (!pay_termsTxt.isChecked()) {
                                            //alert_view(getActivity(), "Message", "" + NC.getResources().getString(R.string.agree_the_terms_and_condition), "" + NC.getResources().getString(R.string.ok), "");


                                            dialog1 = Utility.alert_view_dialog(getActivity(), "Message", "" + NC.getResources().getString(R.string.agree_the_terms_and_condition),
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
                                        } else {
                                            try {
                                                JSONObject j = new JSONObject();
                                                j.put("passenger_cardid", cardId);
                                                j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
                                                j.put("email", SessionSave.getSession("Email", getActivity()));
                                                j.put("creditcard_no", cardno);
                                                j.put("expdatemonth", (mMonth));
                                                j.put("expdateyear", mYear);
                                                j.put("creditcard_cvv", cvv);
                                                j.put("card_type", Type);
                                                j.put("default", DefaultCheck);
                                                j.put("card_holder_name", pay_cardnameEdt.getText().toString());
                                                String url = "type=edit_card_details";
                                                new CreditCard(url, j);
                                            } catch (Exception e) {
                                                // TODO: handle exception
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }

                            }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });
        c = "" + NC.getResources().getString(R.string.personalcard) + "~" + NC.getResources().getString(R.string.businesscard) + "~";
        cardtype = c.split("~");
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < cardtype.length; i++) {
            arr.add(cardtype[i]);
        }
        card_adapter = new FontHelper.MySpinnerAdapter(getActivity(), R.layout.spinneritem_lay, arr);
        card_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        CardtypeSpn.setAdapter(card_adapter);
        // To select the card type from spinner.
        CardtypeSpn.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                //  Colorchange.ChangeColor(parent, getActivity());
                Type = parent.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        removecardBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (!Defaultcheck.isChecked())
                if (SessionSave.getSession("trip_id", getActivity()).equals("")) {

                    try {
                        //native
                        final View view = View.inflate(getActivity(), R.layout.netcon_lay, null);
                        final Dialog mDialog = new Dialog(getActivity(), R.style.dialogwinddow);
                        mDialog.setContentView(view);
                        mDialog.setCancelable(false);
                        mDialog.show();
                        //   FontHelper.applyFont(getActivity(), mDialog.findViewById(R.id.alert_id));
                        final TextView title_text = mDialog.findViewById(R.id.title_text);
                        final TextView message_text = mDialog.findViewById(R.id.message_text);
                        final Button button_success = mDialog.findViewById(R.id.button_success);
                        final Button button_failure = mDialog.findViewById(R.id.button_failure);
                        title_text.setText("" + NC.getResources().getString(R.string.message));
                        message_text.setText("" + NC.getResources().getString(R.string.confirmdelete));
                        button_success.setText("" + NC.getResources().getString(R.string.yes));
                        button_failure.setText("" + NC.getResources().getString(R.string.no));
                        button_success.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                // TODO
                                // Auto-generated
                                // method stub
                                try {
                                    mDialog.dismiss();
                                    // TODO Auto-generated method stub
                                    try {
                                        JSONObject j = new JSONObject();
                                        j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
                                        j.put("passenger_cardid", cardId);
                                        new Deletecard("type=credit_card_delete", j);
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                        e.printStackTrace();
                                    }
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }
                            }
                        });
                        button_failure.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                // TODO
                                // Auto-generated
                                // method stub
                                mDialog.dismiss();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.Trip_in_progress));
            }
        });
    }

    //nativeee
    public void alert_views(Context mContext, String title, String message, String success_txt, String failure_txt) {
        try {


            dialog1 = Utility.alert_view_dialog(getActivity(),
                    "" + title,
                    "" + message, "" + success_txt,
                    "", true, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            mHandler.sendEmptyMessage(1);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    }, "");



           /* final View view = View.inflate(mContext, R.layout.alert_view, null);
            alertmDialog = new Dialog(mContext, R.style.dialogwinddow);
            alertmDialog.setContentView(view);
            alertmDialog.setCancelable(true);
            FontHelper.applyFont(mContext, alertmDialog.findViewById(R.id.alert_id));
            alertmDialog.show();
            final TextView title_text = (TextView) alertmDialog.findViewById(R.id.title_text);
            final TextView message_text = (TextView) alertmDialog.findViewById(R.id.message_text);
            final Button button_success = (Button) alertmDialog.findViewById(R.id.button_success);
            final Button button_failure = (Button) alertmDialog.findViewById(R.id.button_failure);
            button_failure.setVisibility(View.GONE);
            title_text.setText(title);
            message_text.setText(message);
            button_success.setText(success_txt);
            button_success.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    alertmDialog.dismiss();
                    mHandler.sendEmptyMessage(1);
                }
            });
            button_failure.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    alertmDialog.dismiss();
                }
            });*/
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    //not used
    public void alert_view(Context mContext, String title, String message, String success_txt, String failure_txt) {
        try {
            final View view = View.inflate(mContext, R.layout.alert_view, null);
            alertmDialog = new Dialog(mContext, R.style.dialogwinddow);
            alertmDialog.setContentView(view);
            alertmDialog.setCancelable(true);
            //   FontHelper.applyFont(mContext, alertmDialog.findViewById(R.id.alert_id));
            alertmDialog.show();
            final TextView title_text = alertmDialog.findViewById(R.id.title_text);
            final TextView message_text = alertmDialog.findViewById(R.id.message_text);
            final Button button_success = alertmDialog.findViewById(R.id.button_success);
            final Button button_failure = alertmDialog.findViewById(R.id.button_failure);
            button_failure.setVisibility(View.GONE);
            title_text.setText(title);
            message_text.setText(message);
            button_success.setText(success_txt);
            button_success.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    alertmDialog.dismiss();
                }
            });
            button_failure.setOnClickListener(new OnClickListener() {
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

    @Override
    public void onSuccess(int month, int year) {
        //  mYear = year;
        // mMonth = month;

        Log.e("test ", mYear + " " + mMonth);

        updateDisplay();
    }

    public void showLoading(Context context) {
        View view = View.inflate(context, R.layout.progress_bar, null);
        mshowDialog = new Dialog(context, R.style.dialogwinddow);
        mshowDialog.setContentView(view);
        mshowDialog.setCancelable(false);
        mshowDialog.show();


        ImageView iv = mshowDialog.findViewById(R.id.giff);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
        Glide.with(getActivity())
                .load(R.raw.loading_anim)
                .into(imageViewTarget);
    }

    @Override
    public void failure(String inputText) {
    }

    public void back_Clicked() {
        mHandler.sendEmptyMessage(1);
    }

    public void checkChange(boolean on) {
        if (on) {
            ontxt.setText(NC.getString(R.string.ontxt));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
            params.setMargins(0, 0, 6, 0);
            ontxt.setLayoutParams(params);
            Defaultcheck.setBackgroundResource(R.drawable.on_btn);
            DefaultCheck = 1;
        } else {
            ontxt.setText(NC.getString(R.string.offtxt));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
            params.setMargins(6, 0, 0, 0);
            ontxt.setLayoutParams(params);
            Defaultcheck.setBackgroundResource(R.drawable.off_btn);
            DefaultCheck = 0;
        }

    }

    /**
     * updateDisplay() method used to update UI from calendar dialog
     */

    private void updateDisplay() {

        // ExpiryEdt.setText(new StringBuilder().append(mMonth).append("/").append(mYear));
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        TaxiUtil.mActivitylist.remove(getActivity());

        if (mshowDialog != null && mshowDialog.isShowing()) {
            mshowDialog.dismiss();
            mshowDialog = null;

        }
        if (dialog1 != null)
            Utility.closeDialog(dialog1);
        super.onDestroy();
    }

    /**
     * To show the terms and condtion webpage
     */
    private void TermsConditions() {

        try {
            //			JSONObject j = new JSONObject();
            //			j.put("pagename", "termsconditions");
            //			j.put("device_type", "1");
            String url = "&type=dynamic_page&pagename=3&device_type=1";
            new ShowWebpage(url, null);
            //			new ShowWebpage("type=dynamic_page", j);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).left_img.setVisibility(View.VISIBLE);

        ((MainHomeFragmentActivity) getActivity()).left_icon.setVisibility(View.GONE);

    }


    public void onBackPressed() {
        if (ListLay.getVisibility() == View.GONE)
            mHandler.sendEmptyMessage(1);
        else {
            requireActivity().getSupportFragmentManager().beginTransaction().remove(requireActivity().getSupportFragmentManager().findFragmentByTag("PaymentOptionFrag")).commitNow();
            ((MainHomeFragmentActivity) requireActivity()).left_icon.setImageResource(R.drawable.ic_menu);
            ((MainHomeFragmentActivity) requireActivity()).left_icon.setTag("menu");
            ((MainHomeFragmentActivity) requireActivity()).tool_bar_lay.setVisibility(View.GONE);
            ((MainHomeFragmentActivity) requireActivity()).showDarkStatusBarIcon();

            Fragment fragment = requireActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrag);
//            if (fragment instanceof HomePage)
//                ((HomePage) fragment).trigger_FragPopFront();

            if (fragment instanceof BookTaxiHomePage)
                ((BookTaxiHomePage) fragment).trigger_FragPopFront();
        }
    }

    @Override
    public void onClick(CreditCardData cardData, int position) {
        CardnoTxt.setVisibility(View.VISIBLE);
        CancelTxt.setText(NC.getString(R.string.cancel));
        CancelTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        if (!mCreditCardList.get(position).getCard().equals("" + NC.getString(R.string.addcard))) {
            ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.edit_card));
            HeadTitle.setText(NC.getString(R.string.payment));
            ListLay.setVisibility(View.GONE);
            DoneBtn.setVisibility(View.VISIBLE);
            CarddetailLay.setVisibility(View.VISIBLE);
            removecardBtn.setVisibility(View.VISIBLE);
            // Toast.makeText(getActivity(), "hii", Toast.LENGTH_SHORT).show();
            CardnoTxt.setText(NC.getString(R.string.cardnumber));
            if (mCreditCardList.size() == 2)
                Defaultcheck.setEnabled(false);

            Add = false;
            DoneBtn.setText(NC.getString(R.string.save));
            //  DoneBtn.setBackground(NC.getResources().getDrawable(R.drawable.draw_back_header_bgcolor));
            mYear = mCreditCardList.get(position).getYear();
            mMonth = mCreditCardList.get(position).getMonth();

            xCardno = mCreditCardList.get(position).getCard();
            CardnoTxt.setText(xCardno);
            xCvv = mCreditCardList.get(position).getCvv();
            CvvEdt.setText(xCvv);
            OriginalCard = mCreditCardList.get(position).getOriginal_cardno();
            cardId = mCreditCardList.get(position).getId();
            cardPos = position;
            checkChange(false);
            Defaultcheck.setChecked(false);
            if (mCreditCardList.get(position).getDefault_card().equals("1")) {
                Defaultcheck.setChecked(true);
                checkChange(true);
                removecardBtn.setVisibility(View.GONE);
                Defaultcheck.setEnabled(false);

            } else {
                Defaultcheck.setEnabled(true);
            }

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

            pay_cardnameEdt.setText(mCreditCardList.get(position).getName());
            if (mCreditCardList.get(position).getType().equals("P")) {
                CardtypeSpn.setSelection(0);
            } else {
                CardtypeSpn.setSelection(1);
            }
        } else if (mCreditCardList.get(position).getCard().equals("" + NC.getString(R.string.addcard))) {


            ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getString(R.string.add_card));
            HeadTitle.setText(NC.getString(R.string.payment));
            CardtypeSpn.setAdapter(card_adapter);
            pay_cardnameEdt.setText("");
            pay_monthtouched = false;
            pay_yeartouched = false;
            pay_monthspn.setSelection(0);
            pay_yearspn.setSelection(0);
            Type = "";
            Add = true;
            CardnoTxt.setText("");
            CvvEdt.setText("");
            xCvv = "";
            xCardno = "";
            ListLay.setVisibility(View.GONE);
            DoneBtn.setVisibility(View.VISIBLE);
            if (adapter.getItemCount() != 1) {
                Defaultcheck.setChecked(false);
                Defaultcheck.setEnabled(true);
                checkChange(false);
            } else {
                Defaultcheck.setChecked(true);
                Defaultcheck.setEnabled(false);
                checkChange(true);
            }
            pay_termsTxt.setChecked(false);
            CarddetailLay.setVisibility(View.VISIBLE);
            CardnoTxt.setText(NC.getString(R.string.cardnumber));
            Calendar cal = Calendar.getInstance();
            DoneBtn.setText(NC.getString(R.string.save));
        }
    }

    /**
     * CreditCard class is used to request and process the response for both add and edit card API
     */

    private class CreditCard implements APIResult {
        public CreditCard(String url, JSONObject data) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON(getActivity(), this, data, false, 3000).execute(url);
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            if (isSuccess) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        //alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");
                        dialog1 = Utility.alert_view_dialog(getActivity(), "" + NC.getResources().getString(R.string.message), json.getString("message"),
                                "" + NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        onBackPressed();
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }, "");


//                        mHandler.sendEmptyMessage(1);
                    } else {
                        //alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");
                        dialog1 = Utility.alert_view_dialog(getActivity(), "" + NC.getResources().getString(R.string.message), json.getString("message"),
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
     * GetCardlist class is used to get the passenger card details and update it into UI
     */
    private class GetCardlist implements APIResult {
        public GetCardlist(String string, JSONObject data) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON(getActivity(), this, data, false).execute(string);
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            try {
                if (isSuccess) {
                    if (mCreditCardList.size() != 0) {
                        mCreditCardList.clear();
                    }
                    creditCardRepository.deleteAllCards();
                    String name = "", id = "", type = "", month = "", year = "", card = "", cvv = "", default_card = "", original_cardno = "", original_cvv = "";
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {

                        if (json.has(TaxiUtil.USER_WALLET_AMOUNT))
                            SessionSave.saveWalletAmount((float) json.getDouble(TaxiUtil.USER_WALLET_AMOUNT), requireContext());
                        else SessionSave.saveWalletAmount(0, requireContext());

                        JSONArray jarry = json.getJSONArray("detail");
                        int length = jarry.length();

                        total_cards.setText("you have verified " + jarry.length() + " cards ");
                        for (int i = 0; i < length; i++) {
                            id = jarry.getJSONObject(i).getString("passenger_cardid");
                            type = jarry.getJSONObject(i).getString("card_type");
                            month = jarry.getJSONObject(i).getString("expdatemonth");
                            year = jarry.getJSONObject(i).getString("expdateyear");
                            card = jarry.getJSONObject(i).getString("masked_creditcard_no");
                            cvv = jarry.getJSONObject(i).getString("masked_creditcard_cvv");
                            original_cardno = jarry.getJSONObject(i).getString("creditcard_no");
                            //original_cvv = jarry.getJSONObject(i).getString("creditcard_cvv");
                            original_cvv = jarry.getJSONObject(i).getString("creditcard_cvv");
                            default_card = jarry.getJSONObject(i).getString("default_card");
                            name = jarry.getJSONObject(i).getString("card_holder_name");
                            CreditCardData data = new CreditCardData(name, id, type, month, year, card, cvv, default_card, original_cardno, original_cvv);
                            mCreditCardList.add(data);
                            creditCardRepository.insertCreditCard(data);
                        }
                        // CreditCardData data = new CreditCardData("", "", "", "", "", "" + NC.getString(R.string.addcard), "", "", "", "");
                        //mCreditCardList.add(data);
                    } else if (json.getInt("status") == 2) {
                        CToast.ShowToast(getActivity(), json.getString("message"));
                        // total_cards.setText("you have verified 0 cards ");

                      /*  CreditCardData data = new CreditCardData("", "", "", "", "", "" + NC.getString(R.string.addcard), "", "", "", "");
                        mCreditCardList.add(data);*/
                        SessionSave.saveSession(CREDIT_CARD, "0", getActivity());
                        //alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.nocard), "" + NC.getResources().getString(R.string.ok), "");
                        mCardList.setVisibility(View.GONE);
                       /* dialog1 = Utility.alert_view_dialog(getActivity(), "" + NC.getString(R.string.message), "" + NC.getString(R.string.nocard),
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
                                }, "");*/

                    }
                    if (mCreditCardList.size() != 0) {
                        mHandler.sendEmptyMessage(0);
                        SessionSave.saveSession(CREDIT_CARD, "1", requireActivity());
                    }
                } else {
                    requireActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            CToast.ShowToast(requireActivity(), NC.getString(R.string.server_con_error));
                        }
                    });
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletecard class is used to get the passenger card details and update it into UI
     */

    private class Deletecard implements APIResult {
        public Deletecard(String string, JSONObject data) {
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
                        mCreditCardList.remove(cardPos);
                        alert_views(getActivity(), "" + NC.getResources().getString(R.string.message), "" + json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");
                    } else {
                        // alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");

                        dialog1 = Utility.alert_view_dialog(getActivity(), "" + NC.getResources().getString(R.string.message), "" + json.getString("message"),
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
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error));
                        }
                    });
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private class ShowWebpage implements APIResult {
        public ShowWebpage(final String string, JSONObject data) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON(getActivity(), this, true, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession("Lang", getActivity()) + "&encode=" + SessionSave.getSession("encode", getActivity()) + string).execute();

            //  new APIService_Retrofit_JSON(getActivity(), this,  true).execute(TaxiUtil.APIBase_Path + "lang=" + SessionSave.getSession("Lang", getActivity()) + string);
//            new APIService_HTTP_JSON(getActivity(), this, data, false, TaxiUtil.APIBase_Path + "lang=" + SessionSave.getSession("Lang", getActivity()) + "&" + string).execute();
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
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            CToast.ShowToast(getActivity(), NC.getString(R.string.server_con_error));
                        }
                    });
//                    alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), "" + result, "" + NC.getResources().getString(R.string.ok), "");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}