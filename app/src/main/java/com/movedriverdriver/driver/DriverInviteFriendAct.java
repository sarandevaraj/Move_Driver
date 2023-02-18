package com.movedriverdriver.driver;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.movedriverdriver.R;
import com.movedriverdriver.driver.data.DriverCommonData;
import com.movedriverdriver.driver.interfaces.DriverAPIResult;
import com.movedriverdriver.driver.service.DriverAPIService_Retrofit_JSON;
import com.movedriverdriver.driver.utils.DriverAppCacheImage;
import com.movedriverdriver.driver.utils.DriverCToast;
import com.movedriverdriver.driver.utils.DirverColorchange;
import com.movedriverdriver.driver.utils.DriverFontHelper;
import com.movedriverdriver.driver.utils.DriverNC;
import com.movedriverdriver.driver.utils.DriverRoundedImageView;
import com.movedriverdriver.driver.utils.DriverSessionSave;
import com.movedriverdriver.driver.utils.DriverSystems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

/**
 * This class is used to invite friends in social media using referal code
 */
public class DriverInviteFriendAct extends MainActivityDriver implements OnClickListener {
    private LinearLayout SlideImg;
    private LinearLayout Donelay;
    private TextView BackBtn;
    private TextView leftIcon;
    private TextView HeadTitle;
    private TextView ismsTxt;
    private TextView iemailTxt;
    private TextView ifacebookTxt;
    private TextView iwhatsappTxt;
    private TextView itwitterTxt;
    private TextView referalamtTxt;
    private TextView referalcdeTxt;
    public static DriverInviteFriendAct mInviteAct;
    private String PENDING_ACTION_BUNDLE_KEY = "";
    private PendingAction pendingAction = PendingAction.NONE;
    private DriverRoundedImageView profileImg;
    private String invitesubject = "";
    private String invitemsg = "";
    private double refamount = 0.0;
    private LinearLayout invite_main;
    LinearLayout invite_loading, share_full_lay;
    private String[] packarr;
    private Button wallet_history;
    private TextView shareVia;

    /**
     * Setting layout in activity
     */
    @Override
    public int setLayout() {
        // TODO Auto-generated method stub
        return R.layout.driver_invitefriend_lay;

    }

    /**
     * Loading UI Component Resources
     */
    public void priorChanges() {
        PENDING_ACTION_BUNDLE_KEY = getPackageName() + "PendingAction";
        profileImg = findViewById(R.id.profileImg);
        Donelay = findViewById(R.id.rightlay);
        Donelay.setVisibility(View.INVISIBLE);
        leftIcon = findViewById(R.id.leftIcon);
        leftIcon.setVisibility(View.VISIBLE);
        leftIcon.setBackgroundResource(R.drawable.driver_back);
        BackBtn = findViewById(R.id.slideImg);
        HeadTitle = findViewById(R.id.header_titleTxt);
        HeadTitle.setText(DriverNC.getResources().getString(R.string.invite_friend));
        DriverFontHelper.applyFont(this, findViewById(R.id.me_layout));

    }

    @Override
    public void Initialize() {
        // TODO Auto-generated method stub
        priorChanges();
        invite_main = findViewById(R.id.invite_main);
        mInviteAct = this;

        invite_loading = findViewById(R.id.invite_loading);

        invite_main.setVisibility(View.GONE);

        invite_loading.setVisibility(View.VISIBLE);

        ImageView iv = findViewById(R.id.giff);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
        Glide.with(DriverInviteFriendAct.this).load(R.raw.driver_loading_anim).into(imageViewTarget);

        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DriverInviteFriendAct.this.findViewById(android.R.id.content)).getChildAt(0)), DriverInviteFriendAct.this);

        SlideImg = findViewById(R.id.leftIconTxt);

        shareVia = findViewById(R.id.shareVia);
//        ismsTxt = findViewById(R.id.ismsTxt);
//        iemailTxt = findViewById(R.id.iemailTxt);
//        ifacebookTxt = findViewById(R.id.ifacebookTxt);
//        iwhatsappTxt = findViewById(R.id.iwhatsappTxt);
//        itwitterTxt = findViewById(R.id.itwitterTxt);
        wallet_history = findViewById(R.id.wallet_history);
        referalcdeTxt = findViewById(R.id.referalcdeTxt);
        referalamtTxt = findViewById(R.id.referalamtTxt);
        wallet_history.setVisibility(View.GONE);
        if (BsavedInstanceState != null) {
            String name = BsavedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }

        SlideImg.setOnClickListener(v -> finish());
        // Can we present the share dialog for regular links?
        String Profileimgepath = DriverSessionSave.getSession("ProfileImage", DriverInviteFriendAct.this);
        if (!DriverAppCacheImage.loadBitmap(Profileimgepath, profileImg)) {
            DriverSystems.out.println("Image... not avail in cache");
        }
        if (!DriverSessionSave.getSession("RefCode", DriverInviteFriendAct.this).equalsIgnoreCase("") && !DriverSessionSave.getSession("RefCode", DriverInviteFriendAct.this).equalsIgnoreCase(null))
            referalcdeTxt.setText("" + DriverSessionSave.getSession("RefCode", DriverInviteFriendAct.this));
        if (!DriverSessionSave.getSession("RefAmount", DriverInviteFriendAct.this).equalsIgnoreCase("") && !DriverSessionSave.getSession("RefAmount", DriverInviteFriendAct.this).equalsIgnoreCase(null))
            refamount = Double.parseDouble(DriverSessionSave.getSession("RefAmount", DriverInviteFriendAct.this));
        referalamtTxt.setText("" + DriverSessionSave.getSession("site_currency", DriverInviteFriendAct.this) + "" + String.format(Locale.UK, "%.2f", refamount));
        Package pack = DriverInviteFriendAct.this.getClass().getPackage();
        String packtxt = null;
        if (pack != null) {
            packtxt = pack.toString();
        }
        packarr = packtxt.split(" ");
        invitesubject = "" + DriverNC.getResources().getString(R.string.invite_friend);
        //check referal amount
        CheckReferral();

        setOnclickListener();
    }


    /**
     * Checking Referal amount
     */
    private void CheckReferral() {

        try {
            JSONObject j = new JSONObject();
            j.put("driver_id", DriverSessionSave.getSession("Id", DriverInviteFriendAct.this));
            String url = "type=driver_invite_with_referral";
            new RefferalAmt(url, j);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * This class used to check RefferalAmt
     *
     * @author developer
     */
    private class RefferalAmt implements DriverAPIResult {
        private RefferalAmt(final String url, JSONObject data) {

            new DriverAPIService_Retrofit_JSON(DriverInviteFriendAct.this, this, data, false).execute(url);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            DriverSystems.out.println("calleddd" + result);
            if (isSuccess) {
                invite_main.setVisibility(View.VISIBLE);
                wallet_history.setVisibility(View.VISIBLE);
                invite_loading.setVisibility(View.GONE);
                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        DriverSessionSave.saveSession("RefCode", json.getJSONObject("detail").getString("referral_code"), DriverInviteFriendAct.this);
                        DriverSessionSave.saveSession("RefAmount", json.getJSONObject("detail").getString("referral_amount"), DriverInviteFriendAct.this);

                        if (!DriverSessionSave.getSession("RefCode", DriverInviteFriendAct.this).equalsIgnoreCase("") && !DriverSessionSave.getSession("RefCode", DriverInviteFriendAct.this).equalsIgnoreCase(null))
                            referalcdeTxt.setText("" + DriverSessionSave.getSession("RefCode", DriverInviteFriendAct.this));
                        if (!DriverSessionSave.getSession("RefAmount", DriverInviteFriendAct.this).equalsIgnoreCase("") && !DriverSessionSave.getSession("RefAmount", DriverInviteFriendAct.this).equalsIgnoreCase(null))
                            refamount = Double.parseDouble(DriverSessionSave.getSession("RefAmount", DriverInviteFriendAct.this));

                        invitemsg = DriverNC.getResources().getString(R.string.excuse_me_brevity) + DriverSessionSave.getSession("RefCode", DriverInviteFriendAct.this) + DriverNC.getResources().getString(R.string.and_earn) + " " + DriverSessionSave.getSession("site_currency", DriverInviteFriendAct.this) + String.format(Locale.UK, "%.2f", refamount) + " " + DriverNC.getResources().getString(R.string.download_from) + DriverSessionSave.getSession(DriverCommonData.PLAY_STORE_LINK, DriverInviteFriendAct.this);
                        referalamtTxt.setText("" + DriverSessionSave.getSession("site_currency", DriverInviteFriendAct.this) + " " + String.format(Locale.UK, "%.2f", refamount));

                        Log.e("_image_", json.getJSONObject("detail").getString("profile_image"));

                        Picasso.get().load(json.getJSONObject("detail").getString("profile_image")).placeholder(R.drawable.driver_loadingimage).into(profileImg);
                    } else if (json.getInt("status") == -1) {
                        startActivity(new Intent(DriverInviteFriendAct.this, DriverMeAct.class));
                        DriverSessionSave.saveSession("referal", "0", getApplicationContext());
                        finish();

                    }

                } catch (final Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(() -> DriverCToast.ShowToast(DriverInviteFriendAct.this, DriverNC.getString(R.string.server_error)));
            }
        }
    }

    /**
     * Enum class
     */
    private enum PendingAction {
        NONE, POST_PHOTO, POST_STATUS_UPDATE
    }

    /**
     * Onclick listener
     */
    private void setOnclickListener() {

        BackBtn.setOnClickListener(this);
        wallet_history.setOnClickListener(this);
//        ismsTxt.setOnClickListener(this);
//        iemailTxt.setOnClickListener(this);
//        ifacebookTxt.setOnClickListener(this);
//        itwitterTxt.setOnClickListener(this);
//        iwhatsappTxt.setOnClickListener(this);
        shareVia.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    protected void onDestroy() {

        if (mshowDialog != null && mshowDialog.isShowing()) {
            mshowDialog.dismiss();
            mshowDialog = null;

        }
        super.onDestroy();
    }


    /**
     * This method is used to invite via sms
     */
    public void sendSms() {

        final Intent intentsms = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + ""));
        intentsms.putExtra("sms_body", invitemsg);
        startActivity(intentsms);
    }


    /**
     * This method is used to invite via email
     */
    public void sendEmail() {

        final String[] TO = {""};
        final String[] CC = {""};
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "" + DriverNC.getResources().getString(R.string.app_name) + "-" + invitesubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "\n" + invitemsg);
        PackageManager packManager = getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(emailIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.google.android.gm")) {
                emailIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                break;
            }
        }
        try {
            startActivity(Intent.createChooser(emailIntent, DriverNC.getResources().getString(R.string.sendmail)));
        } catch (final android.content.ActivityNotFoundException ex) {
            DriverCToast.ShowToast(DriverInviteFriendAct.this, "" + DriverNC.getResources().getString(R.string.there_is_no_email_client_installed));
        }
    }


    /**
     * This method is used to invite via twitter
     */
    public void initShareIntentTwi(final String type) {

        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, invitemsg);
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
            DriverCToast.ShowToast(this, "Twitter app isn't found");
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.twitter.android"));
            startActivity(i);
        }
    }


    /**
     * This method is used to invite via whatsapp
     */
    public void onClickWhatsApp() {

        PackageManager pm = getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "" + invitemsg;
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");
            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (NameNotFoundException e) {
            DriverCToast.ShowToast(this, "WhatsApp not Installed");
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp"));
            startActivity(i);
        }
    }


    /**
     * To perform onclick listener
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back_text:
                onBackPressed();
                break;
            case R.id.wallet_history:
                startActivity(new Intent(DriverInviteFriendAct.this, DriverWithdrawHistoryAct.class));
                break;
            case R.id.shareVia:
                callChooser();
                break;
            case R.id.slideImg:
                Intent in = new Intent(DriverInviteFriendAct.this, DriverMeAct.class);
                startActivity(in);
                finish();
                break;
            default:
                break;
        }
    }

    private void callChooser() {
        DriverSystems.out.println("choser_cherry");
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Share your friends");
        share.putExtra(Intent.EXTRA_TEXT, invitemsg);

        startActivity(Intent.createChooser(share, "Share link!"));

    }

}
