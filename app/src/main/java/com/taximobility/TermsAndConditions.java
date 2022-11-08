/**
 *
 */
package com.taximobility;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.TaxiUtil;

/**
 * this class is used to show TermsAndConditions of the application
 *
 * @author ndot
 */
public class TermsAndConditions extends MainActivity {
    private boolean t_status;
    private TextView CancelTxt;
    ImageView back_img;

    @Override
    public int setLayout() {
        setLocale();
        return R.layout.termsandconditions;
    }

    /**
     * this method is used for fields declaration
     */
    @SuppressLint("NewApi")
    @Override
    public void Initialize() {
        Colorchange.ChangeColor((ViewGroup) (((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0)), TermsAndConditions.this);
        TaxiUtil.sContext = this;
        TaxiUtil.mActivitylist.add(this);
        FontHelper.applyFont(this, findViewById(R.id.terms_contain));

        TextView DoneBtn = findViewById(R.id.rightIconTxt);
        CancelTxt = findViewById(R.id.leftIcon);
        DoneBtn.setVisibility(View.GONE);
        CancelTxt.setVisibility(View.GONE);
        back_img = findViewById(R.id.back_img);
        back_img.setVisibility(View.VISIBLE);
        LinearLayout CancelBtn = findViewById(R.id.leftIconTxt);

        back_img.setOnClickListener(v -> onBackPressed());
        CancelBtn.setOnClickListener(v -> finish());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            findViewById(R.id.headlayout).setVisibility(View.VISIBLE);
            TextView title = findViewById(R.id.header_titleTxt);
            title.setText(bundle.getString("name"));
            WebView webview = findViewById(R.id.webview);
            String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/" + FontHelper.FONT_TYPEFACE + "\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>";
            String pas = "</body></html>";
            String myHtmlString = pish + getIntent().getExtras().getString("content") + pas;
            webview.loadDataWithBaseURL("file:///android_asset/", myHtmlString, "text/html", "UTF-8", null);
            WebSettings webSettings = webview.getSettings();
            webSettings.setDefaultFontSize(14);
            t_status = bundle.getBoolean("status");
            if (t_status) {
                CancelTxt.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                CancelTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_back, 0, 0, 0);
                CancelTxt.setCompoundDrawablePadding(5);
                CancelTxt.setText("Back");
            } else {
                CancelTxt.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                CancelTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_back, 0, 0, 0);
                CancelTxt.setCompoundDrawablePadding(5);
                CancelTxt.setText("Back");
            }
        }
    }


    @Override
    protected void onDestroy() {
        TaxiUtil.mActivitylist.remove(this);
        super.onDestroy();
    }
}