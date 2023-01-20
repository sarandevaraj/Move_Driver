package com.taximobility;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.taximobility.service.FirebaseService;
import com.taximobility.util.GpsStatus;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Utility;

import static com.taximobility.util.ConstantsKt.API_BASE;
import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.PASS_NAME;

/**
 * Created by developer on 2/1/18.
 */
public class ChatWebviewAct extends AppCompatActivity {
    WebView simpleWebView;
    ProgressDialog progressDialog;
    String driverId, encodeSTr, link_1 = "", link_2_attach, lang_Str, Id = "", chat_type = "";
    String post_params;
    private ImageView iv;
    String type, trip_id = "", to_type;

    Dialog dialog;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_act);
        FirebaseService.activity = this;
        if (getIntent() != null) {
            post_params = getIntent().getStringExtra("post_params");
            type = getIntent().getStringExtra("type");
            Id = getIntent().hasExtra("Id") ? getIntent().getStringExtra("Id") : "";
            chat_type = getIntent().hasExtra("chat_type") ? getIntent().getStringExtra("chat_type") : "";
            trip_id = getIntent().hasExtra("trip_id") ? getIntent().getStringExtra("trip_id") : "";
            to_type = getIntent().hasExtra("to_type") ? getIntent().getStringExtra("to_type") : "";
        }


        simpleWebView = findViewById(R.id.simpleWebView);
        iv = findViewById(R.id.giff);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
        Glide.with(ChatWebviewAct.this)
                .load(R.raw.loading_anim)
                .into(imageViewTarget);
        simpleWebView.setWebViewClient(new MyWebViewClient());
        simpleWebView.getSettings().setJavaScriptEnabled(true);

        driverId = SessionSave.getSession(PASS_ID, ChatWebviewAct.this);
        encodeSTr = driverId;
        lang_Str = SessionSave.getSession("Lang", ChatWebviewAct.this);

        if (!TextUtils.isEmpty(Id)) {
            link_1 = SessionSave.getSession("chat_node_url", ChatWebviewAct.this) + "?name=" + SessionSave.getSession(PASS_NAME, ChatWebviewAct.this) + "("
                    + SessionSave.getSession(PASS_ID, ChatWebviewAct.this) + ")&id=" + Id + "&image=" + SessionSave.getSession("p_image_name", ChatWebviewAct.this)
                    + "&type=P" + "&chat_type=" + chat_type + "&to_type=" + to_type;
        }

/* link_1 = SessionSave.getSession(DriverCommonData.NODE_URL, ChatWebviewAct.this)+ "?name="+ SessionSave.getSession("Name",ChatWebviewAct.this)+"("
            + SessionSave.getSession("Id", ChatWebviewAct.this)+")&id=JOB_ID_"+SessionSave.getSession("trip_id", ChatWebviewAct.this)+"&image="+ SessionSave.getSession("d_image_name",ChatWebviewAct.this)
                +"&type=P";*/

        if (TextUtils.isEmpty(link_1.trim())) {
            if (type.equalsIgnoreCase("3")) {
                link_1 = SessionSave.getSession("chat_node_url", ChatWebviewAct.this) + "?name=" + SessionSave.getSession(PASS_NAME, ChatWebviewAct.this) + "("
                        + SessionSave.getSession(PASS_ID, ChatWebviewAct.this) + ")&id=Passenger_Chat_ID_" + SessionSave.getSession(PASS_ID, ChatWebviewAct.this) + "&image=" + SessionSave.getSession("p_image_name", ChatWebviewAct.this)
                        + "&type=P" + "&chat_type=2" + "&to_type=A";
            } else {
                link_1 = SessionSave.getSession("chat_node_url", ChatWebviewAct.this) + "?name=" + SessionSave.getSession(PASS_NAME, ChatWebviewAct.this) + "("
                        + SessionSave.getSession(PASS_ID, ChatWebviewAct.this) + ")&id=Job_ID_" + trip_id + "&image=" + SessionSave.getSession("p_image_name", ChatWebviewAct.this)
                        + "&type=P" + "&chat_type=1" + "&to_type=D";


            }
        }
        System.out.println("link====" + link_1);
        simpleWebView.loadUrl(link_1);


    }

    @Override
    protected void onDestroy() {
        if (dialog != null)
            Utility.closeDialog(dialog);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
//        if (!TextUtils.isEmpty(Id)) {
//            Intent intent = new Intent(this, MainHomeFragmentActivity.class);
//            startActivity(intent);
//            finish();
//        } else
            if (type.equals("2")) {
            finish();
            }
//        else {
//            Intent intent = new Intent(this, MainHomeFragmentActivity.class);
//            startActivity(intent);
//            finish();
//        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        FirebaseService.activity = null;
        super.onStop();
    }

    public void closeDialog() {
        try {
            if (simpleWebView != null)
                simpleWebView.setVisibility(View.VISIBLE);
            if (GpsStatus.mDialog != null)
                if (GpsStatus.mDialog.isShowing())
                    GpsStatus.mDialog.dismiss();
        } catch (Exception e) {

        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {

            if (url.contains(SessionSave.getSession(API_BASE, ChatWebviewAct.this) + "back")) {
              /*  Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();*/
                onBackPressed();
            } else if (url.contains(SessionSave.getSession(API_BASE, ChatWebviewAct.this) + "cancel")) {
               /* Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();*/
                onBackPressed();
            } else {

                view.loadUrl(url);
            }
            return true;
        }


        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            try {
                if (simpleWebView != null)
                    iv.setVisibility(View.GONE);
                simpleWebView.setVisibility(View.VISIBLE);

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