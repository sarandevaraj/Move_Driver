package com.movedriver;

import static com.movedriver.util.ConstantsKt.API_BASE;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.movedriver.service.FirebaseService;
import com.movedriver.util.GpsStatus;
import com.movedriver.util.SessionSave;
import com.movedriver.util.Utility;

/**
 * Created by Sakthi on 01/02/23.
 */
public class DriverFirebaseChatWebviewNew extends AppCompatActivity {
    WebView simpleWebView;
    String link = "";
    private ImageView iv;
    String trip_id = "";
    Dialog dialog;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_chat_webview_act);
        FirebaseService.activity = this;
        if (getIntent() != null) {
            trip_id = getIntent().hasExtra("trip_id") ? getIntent().getStringExtra("trip_id") : "";
        }
        simpleWebView = findViewById(R.id.simpleWebView);
        iv = findViewById(R.id.giffnew);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
        Glide.with(DriverFirebaseChatWebviewNew.this).load(R.raw.loading_anim).into(imageViewTarget);
        simpleWebView.setWebViewClient(new MyWebViewClient());
        simpleWebView.getSettings().setJavaScriptEnabled(true);

        link = SessionSave.getSession(API_BASE, DriverFirebaseChatWebviewNew.this) + "chat.html?type=DTOP&tripid="+trip_id;

        System.out.println("link====" + link);
        simpleWebView.loadUrl(link);
    }

    @Override
    protected void onDestroy() {
        if (dialog != null) Utility.closeDialog(dialog);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        FirebaseService.activity = null;
        super.onStop();
    }

    public void closeDialog() {
        try {
            if (simpleWebView != null) simpleWebView.setVisibility(View.VISIBLE);
            if (GpsStatus.mDialog != null)
                if (GpsStatus.mDialog.isShowing()) GpsStatus.mDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            try {
                if (simpleWebView != null) {
                    iv.setVisibility(View.GONE);
                    simpleWebView.setVisibility(View.VISIBLE);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            super.onPageStarted(webView, url, favicon);
        }
    }
}