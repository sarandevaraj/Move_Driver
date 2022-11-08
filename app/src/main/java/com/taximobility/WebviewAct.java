package com.taximobility;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.facebook.login.LoginManager;
import com.taximobility.driver.DriverUserLoginAct;
import com.taximobility.util.CL;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static com.taximobility.util.ConstantsKt.API_BASE;
import static com.taximobility.util.ConstantsKt.CREDIT_CARD;
import static com.taximobility.util.ConstantsKt.LOGOUT;
import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.PASS_TRIP_ID;
import static com.taximobility.util.ConstantsKt.REQUEST_TIME;
import static com.taximobility.util.ConstantsKt.REQ_TRIP_ID;
import static com.taximobility.util.GpsStatus.mDialog;


/**
 * Created by developer on 2/1/18.
 */

public class WebviewAct extends AppCompatActivity {

    WebView simpleWebView;
    ProgressDialog progressDialog;
    String driverId, encodeSTr, link_1, link_2_attach, lang_Str;
    String post_params;
    private ImageView iv;
    String type;

    Dialog dialog;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_act);
        if (getIntent() != null) {
            post_params = getIntent().getStringExtra("post_params");
            type = getIntent().getStringExtra("type");
        }


        simpleWebView = findViewById(R.id.simpleWebView);
        iv = findViewById(R.id.giff);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
        Glide.with(WebviewAct.this)
                .load(R.raw.loading_anim)
                .into(imageViewTarget);
        simpleWebView.setWebViewClient(new MyWebViewClient());
        simpleWebView.getSettings().setJavaScriptEnabled(true);

        driverId = SessionSave.getSession(PASS_ID, WebviewAct.this);
        encodeSTr = driverId;
        lang_Str = SessionSave.getSession("Lang", WebviewAct.this);


        String btnAct = "";
        String btnRjt = "";
        try {
            btnAct = Integer.toHexString(CL.getColor(this, R.color.button_accept)).substring(2);
            btnRjt = Integer.toHexString(CL.getColor(this, R.color.button_reject)).substring(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (btnAct != null && btnAct.equals(""))
                btnAct = Integer.toHexString(ContextCompat.getColor(this, R.color.button_accept)).substring(2);
            if (btnRjt != null && btnRjt.equals(""))
                btnRjt = Integer.toHexString(ContextCompat.getColor(this, R.color.button_reject)).substring(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String colorCode = "&b_act=" + btnAct + "&b_cal=" + btnRjt + "&new=1";

        if (type.equalsIgnoreCase("privacy")) {
            link_1 = SessionSave.getSession(API_BASE, WebviewAct.this) + "delete_account.html/";
            link_2_attach = link_1 + encodeSTr + "?lang=" + lang_Str;
            Systems.out.println("Link11" + link_2_attach);
            link_2_attach = link_2_attach + colorCode;
            simpleWebView.postUrl(link_2_attach,/* (new AA().ee(post_params)).getBytes()*/post_params.getBytes());
        } else {
            link_1 = SessionSave.getSession(API_BASE, WebviewAct.this) + "package_plan.html/";

            Systems.out.println("Link11" + link_1);
            link_2_attach = link_1 + encodeSTr + "/?lang=" + lang_Str + "&v=1";
            Systems.out.println("cherry_chk" + "--" + SessionSave.getSession(PASS_ID, WebviewAct.this) + "--lan"
                    + SessionSave.getSession("Lang", WebviewAct.this) + "--" + driverId + "--" + encodeSTr + "--p;'--  **"
                    + link_2_attach + "__" + post_params);
            if (progressDialog == null) {
                if (simpleWebView != null)
                    simpleWebView.setVisibility(View.GONE);
            }
            link_2_attach = link_2_attach + colorCode;
            System.out.println("webview link : "+link_2_attach+"  post_params : "+post_params.getBytes().toString());
            simpleWebView.postUrl(link_2_attach, /*(new AA().ee(post_params)).getBytes()*/post_params.getBytes());
        }
    }

    @Override
    protected void onDestroy() {
        if (dialog != null)
            Utility.closeDialog(dialog);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void closeDialog() {
        try {
            if (simpleWebView != null)
                simpleWebView.setVisibility(View.VISIBLE);
            if (mDialog != null)
                if (mDialog.isShowing())
                    mDialog.dismiss();
        } catch (Exception e) {

        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
            System.out.println("MWebViewClienturl :"+url);
            if (url.contains(SessionSave.getSession(API_BASE, WebviewAct.this) + "package_plan_success")) {
                try {
                    new Handler().postDelayed(() -> {


                        try {
                            Uri uri = Uri.parse(url);
                            SessionSave.saveSession(REQ_TRIP_ID, (uri.getQueryParameter("passenger_tripid")), WebviewAct.this);


                            SessionSave.saveSession("trip_id", (uri.getQueryParameter("passenger_tripid")), WebviewAct.this);
                            SessionSave.saveSession(PASS_TRIP_ID, (uri.getQueryParameter("passenger_tripid")), WebviewAct.this);
                            SessionSave.saveSession(REQUEST_TIME, (uri.getQueryParameter("total_request_time")), WebviewAct.this);
                            SessionSave.saveSession(CREDIT_CARD, "" + (uri.getQueryParameter("credit_card_status")), WebviewAct.this);

                            final Intent i = new Intent(
                                    WebviewAct.this, ContinousRequest.class);
                            i.putExtra("url", "url");
                            i.putExtra("json", "data");
                            i.putExtra("approx_fare", "");
                            startActivity(i);


                        } catch (final Exception e) {
                            SessionSave.saveSession("trip_id", "", WebviewAct.this);
                            e.printStackTrace();
                        }


                    }, 500);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (url.contains(SessionSave.getSession(API_BASE, WebviewAct.this) + "delete_success")) {
                Toast.makeText(WebviewAct.this, NC.getString(R.string.account_deleted_success), Toast.LENGTH_LONG).show();
                SessionSave.saveSession(LOGOUT, "", WebviewAct.this);
                SessionSave.saveSession(PASS_ID, "", WebviewAct.this);
                try {

                    Intent logIn = new Intent(WebviewAct.this, DriverUserLoginAct.class);
                    logIn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    WebviewAct.this.startActivity(logIn);
                    WebviewAct.this.finish();
                    fbLogout();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (url.contains(SessionSave.getSession(API_BASE, WebviewAct.this) + "back")) {
                finish();
            } else if (url.contains(SessionSave.getSession(API_BASE, WebviewAct.this) + "cancel")) {
                finish();
            } else {

                view.loadUrl(url);
            }
            return true;
        }

        public void logout(final Context context) {
            try {


                dialog = Utility.alert_view_dialog(WebviewAct.this, "" + NC.getResources().getString(R.string.message),
                        "" + NC.getResources().getString(R.string.confirmlogout),
                        "" + NC.getResources().getString(R.string.menu_logout),
                        "" + NC.getResources().getString(R.string.cancel),
                        true, (dialog, which) -> {
                            try {
                                dialog.dismiss();
                                JSONObject j = new JSONObject();
                                j.put("id", SessionSave.getSession(PASS_ID, context));

                                if (SessionSave.getSession(LOGOUT, context).equals("")) {
                                    new TaxiUtil.Logout("type=passenger_logout", context, j);
                                    fbLogout();
                                } else

                                    dialog = Utility.alert_view_dialog(WebviewAct.this, "" + NC.getResources().getString(R.string.message), "" + NC.getResources().getString(R.string.bookedtaxi),
                                            "" + NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }, (dialog1, which1) -> dialog1.dismiss(), "");


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, (dialog, which) -> dialog.dismiss(), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * This is method for logout the user from their facebook login if they logged in using facebook.
         */
        public void fbLogout() {
            LoginManager.getInstance().logOut();
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