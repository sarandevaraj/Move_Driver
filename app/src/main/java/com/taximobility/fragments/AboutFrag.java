package com.taximobility.fragments;

/**
 * This class contains the fragment for AboutUs page
 *
 * @author developer
 */


import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.TermsAndConditions;
import com.taximobility.features.CToast;
import com.taximobility.interfaces.APIResult;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.TaxiUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import static com.taximobility.util.ConstantsKt.LANG;

public class AboutFrag extends Fragment {
    public static Activity mAbout;
    private final boolean t_status = false;
    private TextView DoneBtn, HeadTitle, CancelTxt, SubTitleTxt;
    private TextView fblinkTxt, back_text, twlinkTxt, about_sub_title;
    private Dialog mshowDialog;

    public void setLocale() {
        if (SessionSave.getSession(LANG, getActivity()).equals("")) {
            SessionSave.saveSession(LANG, "en", getActivity());
            SessionSave.saveSession("Lang_Country", "en_US", getActivity());
        }
        Configuration config = new Configuration();
        String langcountry = SessionSave.getSession("Lang_Country", getActivity());
        String[] arry = langcountry.split("_");
        String language = SessionSave.getSession(LANG, getActivity());
        config.locale = new Locale(language, arry[1]);
        Locale.setDefault(new Locale(language, arry[1]));
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.aboutactlay, container, false);
        setLocale();
        priorChanges(v);
        //Colorchange.ChangeColor((ViewGroup) v, getActivity());
        return v;
    }


    /**
     * method to render view and intialize necessary view for About us page
     *
     * @param v -->fragment View
     */

    public void priorChanges(View v) {

        mAbout = getActivity();
        TaxiUtil.sContext = getActivity();
        TaxiUtil.mActivitylist.add(getActivity());
        // TODO Auto-generated method stub

        FontHelper.applyFont(getActivity(), v.findViewById(R.id.about_containlay));
        // FontHelper.applyFont(getActivity(), v.findViewById(R.id.headlayout));
        about_sub_title = v.findViewById(R.id.ride_from_anywhere);

        CancelTxt = v.findViewById(R.id.leftIcon);
        CancelTxt.setVisibility(View.GONE);
        back_text = v.findViewById(R.id.back_text);
        back_text.setVisibility(View.VISIBLE);
        DoneBtn = v.findViewById(R.id.rightIconTxt);
        DoneBtn.setVisibility(View.GONE);
        HeadTitle = v.findViewById(R.id.header_titleTxt);
        HeadTitle.setText(NC.getResources().getString(R.string.menu_about));
        SubTitleTxt = v.findViewById(R.id.bold_title_below);
        fblinkTxt = v.findViewById(R.id.fblink);
        //  fblinkTxt.setText("" + SessionSave.getSession("facebook_share", getActivity()));
        twlinkTxt = v.findViewById(R.id.twlink);
        //  twlinkTxt.setText("" + SessionSave.getSession("twitter_share", getActivity()));
        SubTitleTxt.setText("" + Html.fromHtml(getResources().getString(R.string.site_desc)));

        ((MainHomeFragmentActivity) getActivity()).call_image.setVisibility(View.GONE);
        ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.GONE);

        if (SessionSave.getAPI(getActivity()).length() > 0) {
            JSONArray jsonArray = SessionSave.getAPI(getActivity());
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Log.e("log", jsonArray.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } else {
            Log.e("log", "0");
        }

    }

    /**
     * Method to perform the user select operations(Social shares like linkedin,fb,twitter etc).
     *
     * @param v --> layout view of the about us page
     */

    //
    public void clickMethod(View v) {

        switch (v.getId()) {
            case R.id.rateapplay:
                try {
                    Package pack = getActivity().getClass().getPackage();
                    String packtxt = pack.toString();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName()));
                    startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    CToast.ShowToast(getActivity(), " You don't have any browser to open web page");
                }

                break;
            case R.id.likfblay:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SessionSave.getSession("facebook_share", getActivity())));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    CToast.ShowToast(getActivity(), " You don't have any browser to open web page");
                }
                break;
            case R.id.fllwtwlay:
                try {
                    Intent intent_t = new Intent(Intent.ACTION_VIEW, Uri.parse(SessionSave.getSession("twitter_share", getActivity())));
                    startActivity(intent_t);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    CToast.ShowToast(getActivity(), " You don't have any browser to open web page");
                }
                break;
            case R.id.termslay:
                try {

                    String url = "&type=dynamic_page&pagename=3&device_type=1";
                    new ShowWebpage(url, null, 1);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;
            case R.id.privacypolicylay:
                try {
                    String url = "&type=dynamic_page&pagename=9&device_type=1";
                    new ShowWebpage(url, null, 2);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;
            case R.id.cancellation_policy:
                try {
                    String url = "&type=dynamic_page&pagename=12&device_type=1";
                    new ShowWebpage(url, null, 3);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;
            case R.id.refund_policy:
                try {
                    String url = "&type=dynamic_page&pagename=13&device_type=1";
                    new ShowWebpage(url, null, 4);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;
            case R.id.fblinklay:
                try {
                    if (fblinkTxt.getText().toString().trim().length() > 0) {
                        Intent intent_fb = new Intent(Intent.ACTION_VIEW, Uri.parse(SessionSave.getSession("facebook_share", getActivity())));
                        startActivity(intent_fb);
                    }
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    CToast.ShowToast(getActivity(), " You don't have any browser to open web page");
                }
                break;
            case R.id.twlinklay:
                try {
                    if (twlinkTxt.getText().toString().trim().length() > 0) {
                        Intent intent_tw = new Intent(Intent.ACTION_VIEW, Uri.parse(SessionSave.getSession("twitter_share", getActivity())));
                        startActivity(intent_tw);
                    }
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    CToast.ShowToast(getActivity(), " You don't have any browser to open web page");
                }
                break;
            default:
                break;
        }
    }

    /**
     * this method is used to dismiss the dialog when activity destroys
     */

    @Override
    public void onDestroy() {

        if (mshowDialog != null && mshowDialog.isShowing()) {
            mshowDialog.dismiss();
            mshowDialog = null;

        }
        TaxiUtil.mActivitylist.remove(getActivity());
        super.onDestroy();
    }

    /**
     * this method is used to set back icon when activity resumes
     */
    @Override
    public void onResume() {
        super.onResume();
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.VISIBLE);
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).left_img.setVisibility(View.VISIBLE);
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (about_sub_title != null && getActivity() != null) {
                        String str = NC.getString(R.string.about_sub_title);
                        str = str.replaceAll("\\\\n", "\n");
                        str = str.replaceAll("n\\\\", "\n");
                        Systems.out.println("haiii" + str);
                        about_sub_title.setText((str));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1);*/


    }

    /**
     * ShowWebpage class is helps to call API to get the term & conditions and privacy policy web page.
     */

    private class ShowWebpage implements APIResult {
        int type;

        public ShowWebpage(String string, JSONObject data, int i) {
            type = i;
            new APIService_Retrofit_JSON(getActivity(), this, true, SessionSave.getSession("base_url", getActivity()) + "/?" + "lang=" + SessionSave.getSession(LANG, getActivity()) + string).execute();
        }

        @Override
        public void getResult(boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            try {
                if (isSuccess) {
                    Intent intent = new Intent(getActivity(), TermsAndConditions.class);
                    Bundle bundle = new Bundle();
                    intent.putExtra("content", "" + result);
                    if (type == 1) {
                        bundle.putString("name", NC.getString(R.string.termcond));
                        bundle.putBoolean("status", t_status);
                    } else if (type == 2) {
                        bundle.putString("name", NC.getString(R.string.policy));
                    } else if (type == 3) {
                        bundle.putString("name", NC.getString(R.string.cancellation_policy));
                    } else if (type == 4) {
                        bundle.putString("name", NC.getString(R.string.refund_policy));
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    getActivity().runOnUiThread(() -> CToast.ShowToast(getActivity(), result));
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}
