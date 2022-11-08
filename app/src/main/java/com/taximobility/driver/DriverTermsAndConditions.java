package com.taximobility.driver;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.driver.data.DriverCommonData;
import com.taximobility.driver.utils.DirverColorchange;
import com.taximobility.driver.utils.DriverFontHelper;
import com.taximobility.driver.utils.DriverNetworkStatus;

/**
 * this class is used to show TermsAndConditions of the application
 *
 * @author ndot
 */
public class DriverTermsAndConditions extends DriverBaseActivity {
    TextView back_text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_termsandconditions);
        DriverNetworkStatus.appContext = this;
        Initialize();
    }

    /**
     * this method is used for fields declaration
     */
    @SuppressLint("NewApi")
    public void Initialize() {
        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0)), DriverTermsAndConditions.this);
        DriverCommonData.sContext = this;
        DriverCommonData.mActivitylist.add(this);
        DriverFontHelper.applyFont(this, findViewById(R.id.terms_contain));

        back_text = findViewById(R.id.slideImg);
        back_text.setVisibility(View.VISIBLE);

        back_text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            TextView title = findViewById(R.id.headerTxt);
            title.setText(bundle.getString("name"));
            WebView webview = findViewById(R.id.webview);
            String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/" + DriverFontHelper.FONT_TYPEFACE + "\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>";
            String pas = "</body></html>";
            String myHtmlString = pish + getIntent().getExtras().getString("content") + pas;
            webview.loadDataWithBaseURL("file:///android_asset/", myHtmlString, "text/html", "UTF-8", null);
            WebSettings webSettings = webview.getSettings();
            webSettings.setDefaultFontSize(14);

        }
    }




    @Override
    protected void onDestroy() {
        DriverCommonData.mActivitylist.remove(this);
        super.onDestroy();
    }
}