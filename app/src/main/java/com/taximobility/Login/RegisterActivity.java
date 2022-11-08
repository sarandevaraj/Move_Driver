package com.taximobility.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.service.APIService_Retrofit_JSON_NoProgress;
import com.google.android.material.textfield.TextInputLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.taximobility.MainActivity;
import com.taximobility.R;
import com.taximobility.TermsAndConditions;
import com.taximobility.interfaces.APIResult;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.util.CL;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.TaxiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import static com.taximobility.util.ConstantsKt.CREDIT_CARD;
import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.PASS_NAME;

public class RegisterActivity extends MainActivity implements View.OnClickListener {
    ClickableSpan termsOfServicesClick = new ClickableSpan() {
        @Override
        public void onClick(View view) {
            try {
                String url = "&type=dynamic_page&pagename=3&device_type=1";
                new RegisterActivity.ShowWebpage(url, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(CL.getColor(RegisterActivity.this, R.color.button_accept));    // you can use custom color
            ds.setUnderlineText(true);
        }
    };
    private EditText edt_name, edt_pass, edt_email, fnameEdt, edt_referal;
    private Button signup_submit;
    private ImageView back_click, referralinfo;
    private TextView txt_agree;

    @Override
    public int setLayout() {
        // TODO Auto-generated method stub
        return R.layout.activity_register;
    }

    @Override
    public void priorChanges() {
        FontHelper.applyFont(this, findViewById(R.id.register_contain));
        super.priorChanges();
    }

    @Override
    public void Initialize() {
        Colorchange.ChangeColor((ViewGroup) (((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0)), RegisterActivity.this);
        FontHelper.applyFont(this, findViewById(R.id.register_contain));
        TextInputLayout textInputLayoutEmail = findViewById(R.id.email_txt);
        TextInputLayout textInputLayoutName = findViewById(R.id.name_txt);
        TextInputLayout textInputLayoutPassword = findViewById(R.id.pass_txt);
        TextInputLayout textInputLayoutReferral = findViewById(R.id.referal_Text);

        back_click = findViewById(R.id.back_click);
        edt_name = findViewById(R.id.edt_name);
        edt_pass = findViewById(R.id.edt_pass);
        edt_email = findViewById(R.id.edt_email);
        edt_referal = findViewById(R.id.edt_referal);
        referralinfo = findViewById(R.id.referralinfo);
        signup_submit = findViewById(R.id.signup_submit);
        txt_agree = findViewById(R.id.txt_agree);


        if (SessionSave.getSession(TaxiUtil.SKIP_PASSENGER_EMAIL, this, false))
            textInputLayoutEmail.setHint(NC.getString(R.string.email_optional));
        else
            textInputLayoutEmail.setHint(NC.getString(R.string.email));

        textInputLayoutName.setHint(NC.getString(R.string.name_txt));
        textInputLayoutPassword.setHint(NC.getString(R.string.password));
        textInputLayoutReferral.setHint(NC.getString(R.string.referal));

        back_click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm.isAcceptingText()) {
                    InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    im.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }
        });
        setOnclickListener();
        txt_agree.setText(NC.getString(R.string.sign_agree) + " " + NC.getString(R.string.terms_cond));
        makeLinks(txt_agree, new String[]{NC.getString(R.string.terms_cond)}, new ClickableSpan[]{
                termsOfServicesClick
        });
        edt_referal.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                    signup_submit.performClick();
                }
                return false;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(CL.getColor(this, R.color.button_accept));
        }
    }

    private void setOnclickListener() {
        back_click.setOnClickListener(this);
        signup_submit.setOnClickListener(this);
        referralinfo.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * this method is used to call the register api
     */

    private void RegisterData() {

        String fnameTxt = edt_name.getText().toString();
        String emailTxt = edt_email.getText().toString();
        String passwdTxt = edt_pass.getText().toString().trim();
        String referalTxt = edt_referal.getText().toString();

        if (validations(ValidateAction.isValidFirstname, RegisterActivity.this, fnameTxt))
            if (validations(ValidateAction.isValidMail, RegisterActivity.this, emailTxt))
                if (validations(ValidateAction.isValidPassword, RegisterActivity.this, passwdTxt))
                    try {
                        JSONObject j = new JSONObject();
                        j.put("name", fnameTxt);
                        j.put("email", emailTxt);
                        j.put("password", passwdTxt);
                        j.put("referral_code", referalTxt);
                        j.put("device_id", TaxiUtil.mDevice_id);
                        String token = SessionSave.getSession(TaxiUtil.DEVICE_TOKEN, RegisterActivity.this);
                        j.put("device_token", token == null ? SessionSave.getSession("mDevice_id", RegisterActivity.this) : token);
                        j.put("device_type", "1");
                        j.put("passenger_id", SessionSave.getSession("passenger_id", RegisterActivity.this));
                        final String url = "type=passenger_signup_completion_v1";
                        new RegisterActivity.SignUp(url, j);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }


    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) {
            InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            im.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }

        SessionSave.saveSession("IsOTPSend", "", RegisterActivity.this);
        SessionSave.saveSession("f_name", "", RegisterActivity.this);
        SessionSave.saveSession("l_name", "", RegisterActivity.this);
        SessionSave.saveSession("e_mail", "", RegisterActivity.this);
        SessionSave.saveSession("m_no", "", RegisterActivity.this);
        SessionSave.saveSession("p_wd", "", RegisterActivity.this);
        SessionSave.saveSession("cp_wd", "", RegisterActivity.this);
        SessionSave.saveSession("ref_txt", "", RegisterActivity.this);

        finish();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_click:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm.isAcceptingText()) {
                    InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    im.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
                finish();
                break;
            case R.id.signup_submit:
                RegisterData();
                break;
            case R.id.referralinfo:
                String s = SessionSave.getSession("referral_code_info", RegisterActivity.this);
                alert_view(RegisterActivity.this, "" + NC.getResources().getString(R.string.message), "" + s.replace("-", "\n"), "" + NC.getResources().getString(R.string.ok), "");
                break;
            default:
                break;
        }
    }

    public void makeLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans) {
        SpannableString spannableString = new SpannableString(textView.getText());
        for (int i = 0; i < links.length; i++) {
            ClickableSpan clickableSpan = clickableSpans[i];
            String link = links[i];

            int startIndexOfLink = textView.getText().toString().indexOf(link);
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * This class used to register with account details
     * <p>
     * This class used to register with account details
     * </p>
     *
     * @author developer
     */
    private class SignUp implements APIResult {
        private SignUp(final String url, JSONObject data) {

            new APIService_Retrofit_JSON(RegisterActivity.this, this, data, false).execute(url);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            if (isSuccess) {
                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        SessionSave.saveSession("p_wd", edt_pass.getText().toString(), RegisterActivity.this);
                        SessionSave.saveSession("ref_txt", edt_referal.getText().toString(), RegisterActivity.this);
                        SessionSave.saveSession("Email", json.getJSONObject("detail").getString("email"), RegisterActivity.this);
                        SessionSave.saveSession(PASS_ID, json.getJSONObject("detail").getString("id"), RegisterActivity.this);
                        SessionSave.saveSession("Tellfrdmsg", json.getJSONObject("detail").getString("telltofriend_message"), RegisterActivity.this);
                        SessionSave.saveSession("Phone", json.getJSONObject("detail").getString("phone"), RegisterActivity.this);
                        SessionSave.saveSession("ProfileImage", json.getJSONObject("detail").getString("profile_image"), RegisterActivity.this);
                        SessionSave.saveSession(PASS_NAME, edt_name.getText().toString(), RegisterActivity.this);
                        SessionSave.saveSession("About", json.getJSONObject("detail").getString("aboutpage_description"), RegisterActivity.this);
//                        SessionSave.saveSession("Currency", json.getJSONObject("detail").getString("site_currency") + " ", RegisterActivity.this);
                        SessionSave.saveSession("RefCode", json.getJSONObject("detail").getString("referral_code"), RegisterActivity.this);
                        SessionSave.saveSession("RefAmount", json.getJSONObject("detail").getString("referral_code_amount"), RegisterActivity.this);
                        SessionSave.saveSession("Register", "", RegisterActivity.this);
                        SessionSave.saveSession(CREDIT_CARD, "" + json.getJSONObject("detail").getString("credit_card_status"), RegisterActivity.this);
                        SessionSave.saveSession("CountyCode", json.getJSONObject("detail").getString("country_code"), RegisterActivity.this);

                        if (json.has(TaxiUtil.USER_KEY)) {
                            if (!json.getString(TaxiUtil.USER_KEY).equals("") && json.getString(TaxiUtil.USER_KEY) != null)
                                SessionSave.saveSession(TaxiUtil.USER_KEY, json.getString(TaxiUtil.USER_KEY), RegisterActivity.this);
                        }
                        if (json.getJSONObject("detail").getString("split_fare").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isSplitOn, true, RegisterActivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isSplitOn, false, RegisterActivity.this);
                        if (json.getJSONObject("detail").getString("favourite_driver").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isFavDriverOn, true, RegisterActivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isFavDriverOn, false, RegisterActivity.this);
                        if (json.getJSONObject("detail").getString("skip_favourite").equals("1"))
                            SessionSave.saveSession(TaxiUtil.isSkipFavOn, true, RegisterActivity.this);
                        else
                            SessionSave.saveSession(TaxiUtil.isSkipFavOn, false, RegisterActivity.this);
                        if (edt_referal.getText().toString().length() > 0)
                            SessionSave.saveSession("IsReferAvail", "1", RegisterActivity.this);
                        else
                            SessionSave.saveSession("IsReferAvail", "0", RegisterActivity.this);

                        try {
                            if (RegisterActivity.this != null) {
                                /*final Intent i = new Intent(RegisterActivity.this, CardRegisterAct.class);
                                i.putExtra("alert_message", json.getString("message"));
                                if (json.getJSONObject("detail").has("SKIP_CREDIT_CARD") && json.getJSONObject("detail").getString("SKIP_CREDIT_CARD").equals("1"))
                                    SessionSave.saveSession("SKIP_CREDIT_CARD", true, RegisterActivity.this);
                                else
                                    SessionSave.saveSession("SKIP_CREDIT_CARD", false, RegisterActivity.this);
                                startActivity(i);
                                finish();*/
                                callSkipApi();
                                SessionSave.saveSession(TaxiUtil.NEED_TO_COMPLETE_CARD_REG, false, RegisterActivity.this);
                                final Intent i = new Intent(RegisterActivity.this, MainHomeFragmentActivity.class);
                                startActivity(i);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        SessionSave.saveSession("Register", "", RegisterActivity.this);
                        alert_view(RegisterActivity.this, "" + NC.getResources().getString(R.string.message), "" + json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");
                    }
                } catch (final JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ShowToast(RegisterActivity.this, NC.getString(R.string.server_con_error));
                    }
                });
            }
        }
    }

    private void callSkipApi() {


        JSONObject j = new JSONObject();

        try {
            j.put("id", SessionSave.getSession(PASS_ID, RegisterActivity.this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String url = "type=skip_credit_card";
        new SkipCard(url, j);


    }

    private class SkipCard implements APIResult {
        private SkipCard(final String url, JSONObject data) {

            new APIService_Retrofit_JSON_NoProgress(RegisterActivity.this, this, data, false).execute(url);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub

        }
    }

    /**
     * this class is used to show web page for terms and conditions
     *
     * @author developer
     */
    private class ShowWebpage implements APIResult {
        public ShowWebpage(final String string, JSONObject data) {
            new APIService_Retrofit_JSON(RegisterActivity.this, this, true, TaxiUtil.API_BASE_URL + TaxiUtil.COMPANY_KEY + "/?" + "lang=" + SessionSave.getSession("Lang", RegisterActivity.this) + "&encode=" + SessionSave.getSession("encode", RegisterActivity.this) + string).execute();

        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            try {
                if (isSuccess) {
                    final Intent intent = new Intent(RegisterActivity.this, TermsAndConditions.class);
                    final Bundle bundle = new Bundle();
                    intent.putExtra("content", result);
                    bundle.putString("name", NC.getString(R.string.termcond));
                    bundle.putBoolean("status", true);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            ShowToast(RegisterActivity.this, NC.getString(R.string.server_con_error));
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

