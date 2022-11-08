package com.taximobility;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mayan.sospluginmodlue.service.SOSService;
import com.taximobility.bookingmodule.BookTaxiHomePage;
import com.taximobility.features.CToast;
import com.taximobility.interfaces.APIResult;
import com.taximobility.interfaces.DialogInterface;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.util.Colorchange;
import com.taximobility.util.Dialog_Common;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.NetworkStatus;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.PASS_TRIP_ID;

public class ReceiptAct extends MainActivity {
    private static String NotifyMessage = "";
    private final String PENDING_ACTION_BUNDLE_KEY = "com.adrop:PendingAction";
    public String pickup_place, alert_msg;
    Dialog dialog;
    private RatingBar P_ratingbar;
    private TextView P_fare, skip_this, leftIcon, header_titleTxt, shareVia;
    private TextView night_val, evefare_val, promotion_val, tax_val;
    private TextView baseFare, waitingFare, walletAmount, paidAmount, paymentType, minutes_fare;
    private TextView tv_additional_time, tvCancellationFare, txt_emergency, tv_additional_distance;
    private ImageView Fb_share, Tw_share, Msg_share, Mail_share;
    private LinearLayout layoutNormal, layoutOutstation, cancelFareLayOut, emergency_lay, leftIconTxt;
    private Bundle mMessage = null;
    private Bundle alert_bundle = new Bundle();
    private boolean canPresentShareDialog;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private PendingAction pendingAction = PendingAction.NONE;
    private TextView preferences_fare_amt, tv_delivery_fare;

    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {


        @Override
        public void onCancel() {

            Log.d("HelloFacebook", "Canceled");
        }

        @Override
        public void onError(FacebookException error) {

            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
            String title = NC.getString(R.string.error);
            String alertMessage = error.getMessage();
            showResult(title, alertMessage);
        }

        @Override
        public void onSuccess(Sharer.Result result) {

            Log.d("HelloFacebook", "Success!");
            if (result.getPostId() != null) {
                NC.getString(R.string.success);
                result.getPostId();
            }
        }

        private void showResult(String title, String alertMessage) {

            dialog = Utility.alert_view_dialog(ReceiptAct.this, title, alertMessage, NC.getResources().getString(R.string.ok), "", false, null, null, "");
        }
    };
    private EditText comment;
    private TextView comment_txt, distanceFareValue, comments_txtview;
    private LinearLayout Nightfare;
    private LinearLayout Eveningfare;
    private LinearLayout Minute_lay, comment_lay;
    private Dialog mDialog;
    private String trip_id;
    private Button submit;

    private LinearLayout paymentLayout, ll_delivery;

    @Override
    public int setLayout() {

        setLocale();
        return R.layout.lay_receipt2;
    }

    @Override
    public void Initialize() {
        BookTaxiHomePage.Companion.setBookingState(BookTaxiHomePage.BOOKINGSTATE.STATE_ONE);
        SessionSave.saveSessionInt(TaxiUtil.SKIP_PAST_BOOKING, 1, ReceiptAct.this);
        Colorchange.ChangeColor((ViewGroup) (((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0)), ReceiptAct.this);
        if (!SessionSave.getSession("facebook_key", ReceiptAct.this).equals(""))
            FacebookSdk.setApplicationId(SessionSave.getSession("facebook_key", ReceiptAct.this));
        else
            FacebookSdk.setApplicationId(getString(R.string.facebookAppId));
        if (BsavedInstanceState != null) {
            String name = BsavedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }
        findViewById(R.id.headlayout).setVisibility(View.VISIBLE);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        findViewById(R.id.layout_skip).setVisibility(View.GONE);
        shareDialog.registerCallback(callbackManager, shareCallback);
        // Can we present the share dialog for regular links?
        canPresentShareDialog = ShareDialog.canShow(ShareLinkContent.class);
        // Can we present the share dialog for photos?
        TaxiUtil.sContext = this;
        FontHelper.applyFont(this, findViewById(R.id.lay_pay));

        //outstation initalization
        layoutNormal = findViewById(R.id.layout_normalreceipt);
        layoutOutstation = findViewById(R.id.layout_outstationreceipt);
        tv_additional_time = findViewById(R.id.additonal_time_fare);
        tv_additional_distance = findViewById(R.id.additonal_distance_fare);
        tvCancellationFare = findViewById(R.id.tvCancellationFare);
        cancelFareLayOut = findViewById(R.id.cancelFareLayOut);
        distanceFareValue = findViewById(R.id.distanceFareValue);
        P_fare = findViewById(R.id.fare_amt);
        skip_this = findViewById(R.id.skip_this);
        leftIcon = findViewById(R.id.leftIcon);
        leftIconTxt = findViewById(R.id.leftIconTxt);
        Nightfare = findViewById(R.id.night_farelay);
        Eveningfare = findViewById(R.id.evening_farelay);
        Minute_lay = findViewById(R.id.minute_lay);
        shareVia = findViewById(R.id.shareVia);
        txt_emergency = findViewById(R.id.txt_emergency);
        emergency_lay = findViewById(R.id.emergency_lay);
        header_titleTxt = findViewById(R.id.header_titleTxt);
        P_ratingbar = findViewById(R.id.ratingBar1);
        submit = findViewById(R.id.submit);
        comment = findViewById(R.id.comment);
        comment_txt = findViewById(R.id.comment_txttick);
        comments_txtview = findViewById(R.id.comments_txtview);
        comment_lay = findViewById(R.id.comment_lay);
        Fb_share = findViewById(R.id.share1);
        Tw_share = findViewById(R.id.share2);
        Msg_share = findViewById(R.id.share3);
        Mail_share = findViewById(R.id.share4);
        minutes_fare = findViewById(R.id.minutes_fare);
        baseFare = findViewById(R.id.BaseFare);
        waitingFare = findViewById(R.id.WaitingFare);
        walletAmount = findViewById(R.id.WalletAmt);
        paidAmount = findViewById(R.id.PaidAmt);
        paymentType = findViewById(R.id.Paymenttype);
        night_val = findViewById(R.id.night_val);
        evefare_val = findViewById(R.id.evefare_val);
        promotion_val = findViewById(R.id.promotion_val);
        tax_val = findViewById(R.id.tax_val);
        preferences_fare_amt = findViewById(R.id.preferences_fare_amt);
        tv_delivery_fare = findViewById(R.id.tv_delivery_fare);
        header_titleTxt.setText("" + NC.getResources().getString(R.string.e_receipt));
        header_titleTxt.setPadding(10, 10, 10, 10);

        paymentLayout = findViewById(R.id.paymentLayout);
        ll_delivery = findViewById(R.id.ll_delivery);

        if (SessionSave.getSession(TaxiUtil.CORPORATE_PASSENGER, ReceiptAct.this).equals("1")) {
            paymentLayout.setVisibility(View.GONE);
        } else {
            paymentLayout.setVisibility(View.VISIBLE);
        }
        leftIcon.setVisibility(View.VISIBLE);
        leftIconTxt.setVisibility(View.VISIBLE);
        if (SessionSave.getSession(TaxiUtil.sosEnable, ReceiptAct.this, false)) {
            txt_emergency.setVisibility(View.VISIBLE);
            emergency_lay.setVisibility(View.VISIBLE);
        } else {
            txt_emergency.setVisibility(View.GONE);
            emergency_lay.setVisibility(View.GONE);
        }

        txt_emergency.setOnClickListener(view -> {
            final View view1 = View.inflate(ReceiptAct.this, R.layout.emergency_alert, null);
            Dialog emergency_dialog = new Dialog(ReceiptAct.this, R.style.dialogwinddow);
            emergency_dialog.setContentView(view1);
            emergency_dialog.setCancelable(true);
            emergency_dialog.show();
            final Button button_success = emergency_dialog.findViewById(R.id.button_success);
            final Button button_failure = emergency_dialog.findViewById(R.id.button_failure);
            button_success.setOnClickListener(view22 -> {
                SessionSave.saveSession("sos_id", SessionSave.getSession(PASS_ID, ReceiptAct.this), ReceiptAct.this);
                SessionSave.saveSession("user_type", "p", ReceiptAct.this);

                     startService(new Intent(ReceiptAct.this, SOSService.class));
                emergency_dialog.dismiss();
            });
            button_failure.setOnClickListener(view2 -> emergency_dialog.dismiss());
        });

        leftIcon.setBackgroundResource(R.drawable.back);
        leftIcon.setOnClickListener(v -> {
            SessionSave.saveSession(PASS_TRIP_ID, "", ReceiptAct.this);
            SessionSave.saveSession(trip_id, "", ReceiptAct.this);
            Intent i;
            i = new Intent(ReceiptAct.this, MainHomeFragmentActivity.class);
            startActivity(i);
            finish();
        });

        leftIconTxt.setOnClickListener(v -> {
            SessionSave.saveSession(PASS_TRIP_ID, "", ReceiptAct.this);
            SessionSave.saveSession(trip_id, "", ReceiptAct.this);
            Intent i;
            i = new Intent(ReceiptAct.this, MainHomeFragmentActivity.class);
            startActivity(i);
            finish();
        });

        try {
            trip_id = alert_bundle.getString("trip_id");
            if (alert_bundle != null) {
                alert_msg = alert_bundle.getString("alert_message");
            }
            if (alert_msg != null && alert_msg.length() != 0)
                //going native
                alert_view(ReceiptAct.this, "" + NC.getResources().getString(R.string.message), "" + alert_msg, "" + NC.getResources().getString(R.string.ok), "");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        final Intent i = getIntent();
        mMessage = i.getExtras();
        if (mMessage != null) {
            NotifyMessage = mMessage.getString("Message");
            try {
                final JSONObject obj = new JSONObject(NotifyMessage);
                Log.e("receiptdetails", obj.toString());
                pickup_place = obj.getString("pickup");

                if (!obj.getString("trip_type").equals("3") && !obj.getString("trip_type").equals("2")) {
                    layoutNormal.setVisibility(View.VISIBLE);
                    layoutOutstation.setVisibility(View.GONE);

                } else {
                    layoutNormal.setVisibility(View.GONE);
                    layoutOutstation.setVisibility(View.VISIBLE);
                    tv_additional_distance.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + obj.getString("additional_distance_fare"));
                    tv_additional_time.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + obj.getString("additional_time_fare"));
                }

                if (obj.has("distance_fare"))
                    distanceFareValue.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("distance_fare"))));
                else
                    distanceFareValue.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "0.0");

                P_fare.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("fare"))));
                if (obj.has("cancellation_fee")) {
                    if (Float.valueOf(obj.getString("cancellation_fee")) > 0.0) {
                        cancelFareLayOut.setVisibility(View.VISIBLE);
                        tvCancellationFare.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("cancellation_fee"))));
                    } else
                        cancelFareLayOut.setVisibility(View.GONE);
                }

                if (obj.has(TaxiUtil.USER_WALLET_AMOUNT))
                    SessionSave.saveWalletAmount((float) obj.getDouble(TaxiUtil.USER_WALLET_AMOUNT), ReceiptAct.this);

                baseFare.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("trans_base_fare"))));
                walletAmount.setText(SessionSave.getSession("Currency", ReceiptAct.this) + " " + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("used_wallet_amount"))));
                paidAmount.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("paid_amount"))));
                if (obj.has("total_preference_fare")) {
                    preferences_fare_amt.setText(SessionSave.getSession("Currency", ReceiptAct.this) + " " + obj.getString("total_preference_fare"));
                }
                paymentType.setText(obj.getString("payment_type"));
                night_val.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("nightfare"))));
                if (Float.valueOf(obj.getString("nightfare")) <= 0.0)
                    Nightfare.setVisibility(View.GONE);
                if (Float.valueOf(obj.getString("eveningfare")) <= 0.0)
                    Eveningfare.setVisibility(View.GONE);
                evefare_val.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("eveningfare"))));
                promotion_val.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("promotion"))));
                tax_val.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("tax"))));
                paymentType.setText(obj.getString("payment_type"));
                minutes_fare.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("minutes_fare"))));
                if (Float.valueOf(obj.getString("minutes_fare")) <= 0.0)
                    Minute_lay.setVisibility(View.GONE);

                waitingFare.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("waiting_fare"))));

                if (obj.has("delivery_fare") && !TextUtils.isEmpty(obj.getString("delivery_fare")) && Double.parseDouble(obj.getString("delivery_fare")) > 0) {
                    ll_delivery.setVisibility(View.VISIBLE);
                    tv_delivery_fare.setText(SessionSave.getSession("Currency", ReceiptAct.this) + " " + obj.getString("delivery_fare"));
                } else {
                    ll_delivery.setVisibility(View.GONE);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        } else if (SessionSave.getSession("receipt_details", ReceiptAct.this).length() != 0) {
            try {
                final JSONObject obj = new JSONObject(SessionSave.getSession("receipt_details", ReceiptAct.this));
                pickup_place = obj.getString("pickup");
                P_fare.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format("%.2f", Float.valueOf(obj.getString("fare"))));

                baseFare.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("trans_base_fare"))));
                waitingFare.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("waiting_fare"))));
                walletAmount.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("used_wallet_amount"))));
                paidAmount.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("paid_amount"))));
                paymentType.setText(obj.getString("payment_type"));
                night_val.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("nightfare"))));
                evefare_val.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("eveningfare"))));
                promotion_val.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("promotion"))));
                tax_val.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("tax"))));
                paymentType.setText(obj.getString("payment_type"));
                minutes_fare.setText(SessionSave.getSession("Currency", ReceiptAct.this) + "" + String.format(Locale.UK, "%.2f", Float.valueOf(obj.getString("minutes_fare"))));

                if (obj.has("total_preference_fare")) {
                    preferences_fare_amt.setText(SessionSave.getSession("Currency", ReceiptAct.this) + " " + obj.getString("total_preference_fare"));
                }

                if (obj.has("delivery_fare") && !TextUtils.isEmpty(obj.getString("delivery_fare")) && Double.parseDouble(obj.getString("delivery_fare")) > 0) {
                    ll_delivery.setVisibility(View.VISIBLE);
                    tv_delivery_fare.setText(SessionSave.getSession("Currency", ReceiptAct.this) + " " + obj.getString("delivery_fare"));
                } else {
                    ll_delivery.setVisibility(View.GONE);
                }


                if (Float.valueOf(obj.getString("nightfare")) <= 0.0)
                    Nightfare.setVisibility(View.GONE);
                if (Float.valueOf(obj.getString("eveningfare")) <= 0.0)
                    Eveningfare.setVisibility(View.GONE);
                if (Float.valueOf(obj.getString("minutes_fare")) <= 0.0)
                    Minute_lay.setVisibility(View.GONE);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        FontHelper.applyFont(this, findViewById(R.id.headlayout));
        try {
            if (trip_id != null)
                ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(Integer.parseInt(SessionSave.getSession("trip_id", ReceiptAct.this)));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        SessionSave.saveSession("TaxiStatus", "", ReceiptAct.this);
        comment_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit.setVisibility(View.VISIBLE);
                comment.setVisibility(View.VISIBLE);
            }
        });

        submit.setOnClickListener(v -> {
            try {

                comment.setVisibility(View.GONE);
                comment_txt.setVisibility(View.VISIBLE);


                final float Rating_number = P_ratingbar.getRating();
                JSONObject j = new JSONObject();
                j.put("pass_id", SessionSave.getSession(PASS_TRIP_ID, ReceiptAct.this));
                j.put("ratings", "" + Rating_number);
                j.put("comments", comment.getText().toString());
                if (Rating_number == 0 && comment.getText().toString().trim().equals(""))
                    CToast.ShowToast(ReceiptAct.this, NC.getString(R.string.no_info));
                else if (!comment.getText().toString().trim().equals("") && Rating_number == 0) {
                    dialog = Utility.alert_view_dialog(ReceiptAct.this, "",
                            "" + NC.getString(R.string.zerorating),
                            "" + NC.getResources().getString(R.string.ok), "" + NC.getResources().getString(R.string.cancel),
                            true, (dialog, which) -> {
                                try {
                                    dialog.dismiss();

                                    JSONObject j1 = new JSONObject();
                                    j1.put("pass_id", SessionSave.getSession(PASS_TRIP_ID, ReceiptAct.this));
                                    j1.put("ratings", "" + Rating_number);
                                    j1.put("comments", comment.getText().toString());
                                    new RatingTrip("type=update_ratings_comments", j1);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }
                            }, (dialog, which) -> dialog.dismiss(), "");
                } else
                    new RatingTrip("type=update_ratings_comments", j);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                finish();
            }
        });
        // This click listener finish this activity and move to book taxi activity.
        skip_this.setOnClickListener(v -> {
            SessionSave.saveSession(PASS_TRIP_ID, "", ReceiptAct.this);
            SessionSave.saveSession(trip_id, "", ReceiptAct.this);
            showLoading(ReceiptAct.this);
            Intent i1;
            i1 = new Intent(ReceiptAct.this, MainHomeFragmentActivity.class);
            startActivity(i1);
            finish();
        });

        // This click listener for twitter share with company details.
        Tw_share.setOnClickListener(v -> initShareIntentTwi("com.twitter.android"));
        // This click listener for message using network service share with company details.
        Msg_share.setOnClickListener(v -> sendSms());
        // This click listener for mail share with company details and play store app links.
        Mail_share.setOnClickListener(v -> sendEmail());

        shareVia.setOnClickListener(view -> ShareContent());
    }

    private void ShareContent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, SessionSave.getSession("TellfrdMsg", ReceiptAct.this) + "\n" + SessionSave.getSession(TaxiUtil.PLAY_STORE_LINK, ReceiptAct.this));
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, NC.getString(R.string.share_link)));
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        NetworkStatus.appContext = this;
        NetworkStatus.isOnline(ReceiptAct.this);
//        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {

        super.onPause();
//        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onDestroy() {
        if (dialog != null)
            Utility.closeDialog(dialog);
        super.onDestroy();
    }

    /**
     * this metrhod is used to share the app link to twitterthrough sms
     */

    public void sendSms() {

        final Intent intentsms = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + ""));
        intentsms.putExtra("sms_body", SessionSave.getSession("TellfrdMsg", ReceiptAct.this) + "\n" + NC.getResources().getString(R.string.pass_app_link));
        startActivity(intentsms);
    }

    /**
     * this metrhod is used to share the app link through email
     */

    public void sendEmail() {

        final String[] TO = {""};
        final String[] CC = {""};
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Experience with " + getString(R.string.app_name));
        emailIntent.putExtra(Intent.EXTRA_TEXT, "\n" + SessionSave.getSession("TellfrdMsg", ReceiptAct.this) + "\n" + NC.getResources().getString(R.string.pass_app_link));
        try {
            startActivity(Intent.createChooser(emailIntent, NC.getResources().getString(R.string.sendmail)));
        } catch (final android.content.ActivityNotFoundException ex) {
            ShowToast(ReceiptAct.this, "" + NC.getResources().getString(R.string.there_is_no_email_client_installed));
        }
    }

    /**
     * this metrhod is used to share the app link to twitter
     */

    public void initShareIntentTwi(final String type) {

        final String review = "" + NC.getResources().getString(R.string.pass_app_link);
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, review);
        tweetIntent.setType("text/plain");
        PackageManager packManager = getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            startActivity(tweetIntent);
        } else {
            CToast.ShowToast(this, "Twitter app isn't found");
            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.twitter.android"));
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        SessionSave.saveSession(PASS_TRIP_ID, "", ReceiptAct.this);
        SessionSave.saveSession(trip_id, "", ReceiptAct.this);
        Intent i;
        i = new Intent(ReceiptAct.this, MainHomeFragmentActivity.class);
        startActivity(i);
        finish();
    }

    private enum PendingAction {
        NONE, POST_PHOTO, POST_STATUS_UPDATE
    }

    /**
     * this class is used to call the rating api which user rate the trip
     */

    private class RatingTrip implements APIResult, DialogInterface {
        private String driverID;

        public RatingTrip(final String string, JSONObject data) {
            new APIService_Retrofit_JSON(ReceiptAct.this, this, data, false).execute(string);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {

            if (isSuccess)
                try {

                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        submit.setClickable(false);
                        submit.setVisibility(View.GONE);
                        comment.setVisibility(View.GONE);
                        P_ratingbar.setIsIndicator(true);
                        int favDriver = 0;
                        try {
                            favDriver = json.getInt("set_fav_driver");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (favDriver == 1) {
                            driverID = json.getString("fav_driver_id");
                            dialog = new Dialog_Common().setmCustomDialog(ReceiptAct.this, this, NC.getResources().getString(R.string.message),
                                    json.getString("message"), NC.getResources().getString(R.string.ok), NC.getResources().getString(R.string.cancel));
                        } else {

                            dialog = Utility.alert_view_dialog(ReceiptAct.this, "" + NC.getResources().getString(R.string.message),
                                    "" + json.getString("message"),
                                    "" + NC.getResources().getString(R.string.ok), "" + NC.getResources().getString(R.string.cancel),
                                    true, (dialog, which) -> {
                                        try {
                                            dialog.dismiss();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }, (dialog, which) -> dialog.dismiss(), "");
                        }
                    }
                } catch (final Exception e) {
                    finish();
                    SessionSave.saveSession(PASS_TRIP_ID, "", ReceiptAct.this);
                    SessionSave.saveSession(trip_id, "", ReceiptAct.this);
                }
        }

        @Override
        public void onSuccess(Dialog mDialog, String resultCode) {
            //mDialog.dismiss();
            Systems.out.println("chkng_rate");
            if (dialog != null)
                Utility.closeDialog(dialog);
            try {
                JSONObject j = new JSONObject();
                j.put("passenger_id", SessionSave.getSession(PASS_ID, ReceiptAct.this));
                j.put("driver_id", "" + driverID);
                new FavouriteDriver("type=set_favourite_driver", j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Dialog mDialog, String resultCode) {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            if (dialog != null)
                Utility.closeDialog(dialog);

        }
    }

    /**
     * this class is used to call the favourite driver api
     */

    private class FavouriteDriver implements APIResult {

        public FavouriteDriver(final String string, JSONObject data) {

            new APIService_Retrofit_JSON(ReceiptAct.this, this, data, false).execute(string);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {

            if (isSuccess)
                try {
                    final JSONObject json = new JSONObject(result);
                    CToast.ShowToast(ReceiptAct.this, json.getString("message"));
                    SessionSave.saveSession(PASS_TRIP_ID, "", ReceiptAct.this);
                    SessionSave.saveSession(trip_id, "", ReceiptAct.this);
                    Intent i;
                    i = new Intent(ReceiptAct.this, MainHomeFragmentActivity.class);
                    if (json.getString("status").trim().equals("1")) {
                        i.putExtra("goto", "favdriv");
                    }
                    startActivity(i);
                    finish();
                } catch (final Exception e) {
                    finish();
                    SessionSave.saveSession(PASS_TRIP_ID, "", ReceiptAct.this);
                    SessionSave.saveSession(trip_id, "", ReceiptAct.this);
                }
        }
    }
}
