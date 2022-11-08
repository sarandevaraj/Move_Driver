
package com.taximobility.fragments;

import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SwitchCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.features.CToast;
import com.taximobility.interfaces.APIResult;
import com.taximobility.interfaces.AlertListener;
import com.taximobility.interfaces.FragPopFront;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.util.Colorchange;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import static com.taximobility.util.ConstantsKt.LOGOUT;
import static com.taximobility.util.ConstantsKt.PASS_ID;

/**
 * this class is used for settings
 */
public class SettingsFrag extends Fragment implements FragPopFront {
    private SwitchCompat spliton_off, skip_drop_on_off;
    private CheckBox favoritedriveron_off;
    private TextView favoritedriver,logout_txt;
    private TextView version;
    private String APP_VERSION = "";
    private int typeOfApi;
    private Dialog alertmDialog;

    private TextView txt_privacy_policy;

    public void setLocale() {
        if (SessionSave.getSession("Lang", getActivity()).equals("")) {
            SessionSave.saveSession("Lang", "en", getActivity());
            SessionSave.saveSession("Lang_Country", "en_US", getActivity());
        }
        Configuration config = new Configuration();
        String langcountry = SessionSave.getSession("Lang_Country", getActivity());
        String[] arry = langcountry.split("_");
        String language = SessionSave.getSession("Lang", getActivity());
        config.locale = new Locale(language, arry[1]);
        Locale.setDefault(new Locale(language, arry[1]));
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings, container, false);
        setLocale();
        Systems.out.println("sessionValues " + SessionSave.getSession("Lang_Country", getActivity()));
        Systems.out.println("sessionValues " + SessionSave.getSession("Lang", getActivity()));

        ((MainHomeFragmentActivity) getActivity()).call_image.setVisibility(View.GONE);
        ((MainHomeFragmentActivity) getActivity()).txt_emergency.setVisibility(View.GONE);
        //Colorchange.ChangeColor((ViewGroup) v, getActivity());
        String host = "";
        try {
            URL urls = new URL(TaxiUtil.API_BASE_URL);
            host = urls.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ((TextView) v.findViewById(R.id.baseUrl)).setText(host);
       // FontHelper.applyFont(getActivity(), v);
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getResources().getString(R.string.settings));
        v.findViewById(R.id.lang).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new LanguageFrag()).addToBackStack(null).commit();
            }
        });
        version = v.findViewById(R.id.version);
        favoritedriver = v.findViewById(R.id.favoritedriver);
        logout_txt = v.findViewById(R.id.logout);
        spliton_off = v.findViewById(R.id.spliton_off);
        favoritedriveron_off = v.findViewById(R.id.favoritedriverson_off);
        skip_drop_on_off = v.findViewById(R.id.skip_drop_on_off);
        txt_privacy_policy = v.findViewById(R.id.txt_privacy_policy);



        txt_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new PrivacyPolicyFrag()).addToBackStack(null).commit();
//           alert_view();
            }
        });
        Log.e("preference ", String.valueOf(SessionSave.getSession(TaxiUtil.isSplitOn, getActivity(), true)));
        if (APP_VERSION.equals("")) {
            PackageInfo info = null;
            PackageManager manager = getActivity().getPackageManager();
            try {
                info = manager.getPackageInfo(getActivity().getPackageName(), 0);
                APP_VERSION = info.versionName;
                Systems.out.println("apppversion----" + APP_VERSION);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        version.setText("V " + APP_VERSION + " Service");
        version.setTextColor(Color.RED);


        if (SessionSave.getSession(TaxiUtil.isSplitOn, getActivity(), false))
            spliton_off.setChecked(true);
        else
            spliton_off.setChecked(false);


        favoritedriveron_off.setChecked(SessionSave.getSession(TaxiUtil.isFavDriverOn, getActivity(), false));
        skip_drop_on_off.setChecked(SessionSave.getSession(TaxiUtil.isSkipFavOn, getActivity(), false));

        /*spliton_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SessionSave.saveSession(TaxiUtil.isSplitOn, isChecked, getActivity());
            }
        });*/

        favoritedriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                //getFragmentManager().beginTransaction().add(R.id.mainFrag, new FavouriteDriverFrag()).commit();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainFrag, new FavouriteDriverFrag()).addToBackStack(null).commit();

            }
        });

        logout_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.actionSheet(getActivity(), NC.getResources().getString(R.string.confirmlogout), NC.getResources().getString(R.string.menu_logout), "", false, new AlertListener() {
                    @Override
                    public void onSuccess() {
                        logout();
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });

//        favoritedriveron_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (isChecked)
//                    SessionSave.saveSession(TaxiUtil.isFavDriverOn, true, getActivity());
//                else
//                    SessionSave.saveSession(TaxiUtil.isFavDriverOn, false, getActivity());
//
//            }
//        });

//        skip_drop_on_off.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                typeOfApi = 3;
//                callApi(isChecked);
//                //      Toast.makeText(getActivity(), String.valueOf(SessionSave.getSession(TaxiUtil.isFavDriverOn, getActivity(), true)), Toast.LENGTH_SHORT).show();
////                if (!SessionSave.getSession(TaxiUtil.isSkipFavOn, getActivity(), false)) {
////                    //  fav_add.setImageResource(R.drawable.on_btn);
////                    SessionSave.saveSession(TaxiUtil.isSkipFavOn, true, getActivity());
////                    ShowToast.center(getActivity(), getString(R.string.skip_fav_off));
////                    // Toast.makeText(getActivity(), getString(R.string.fav_driver_on), Toast.LENGTH_SHORT).show();
////                } else {
////                    // fav_add.setImageResource(R.drawable.off_btn);
////                    SessionSave.saveSession(TaxiUtil.isSkipFavOn, false, getActivity());
////                    ShowToast.center(getActivity(), getString(R.string.skip_fav_on));
////                    //Toast.makeText(getActivity(), getString(R.string.fav_driver_off), Toast.LENGTH_SHORT).show();
////                }
//            }
//        });
//        favoritedriveron_off.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                typeOfApi = 2;
//                callApi(isChecked);
//                //      Toast.makeText(getActivity(), String.valueOf(SessionSave.getSession(TaxiUtil.isFavDriverOn, getActivity(), true)), Toast.LENGTH_SHORT).show();
////                if (!SessionSave.getSession(TaxiUtil.isFavDriverOn, getActivity(), false)) {
////                    //  fav_add.setImageResource(R.drawable.on_btn);
////                    SessionSave.saveSession(TaxiUtil.isFavDriverOn, true, getActivity());
////                    ShowToast.center(getActivity(), NC.getString(R.string.fav_driver_on));
////                    // Toast.makeText(getActivity(), getString(R.string.fav_driver_on), Toast.LENGTH_SHORT).show();
////                } else {
////                    // fav_add.setImageResource(R.drawable.off_btn);
////                    SessionSave.saveSession(TaxiUtil.isFavDriverOn, false, getActivity());
////                    ShowToast.center(getActivity(), NC.getString(R.string.fav_driver_off));
////
////                    //Toast.makeText(getActivity(), getString(R.string.fav_driver_off), Toast.LENGTH_SHORT).show();
////                }
//            }
//        });
        skip_drop_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //SessionSave.saveSession(TaxiUtil.isSplitOn, isChecked, getActivity());
                typeOfApi = 3;
                callApi(isChecked);

            }
        });
        favoritedriveron_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //SessionSave.saveSession(TaxiUtil.isSplitOn, isChecked, getActivity());
                typeOfApi = 2;
                callApi(isChecked);

            }
        });
        spliton_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //SessionSave.saveSession(TaxiUtil.isSplitOn, isChecked, getActivity());
                typeOfApi = 1;
                callApi(isChecked);

            }
        });


        return v;
    }

    private void logout() {

        try {


            try {
                // TODO Auto-generated method stub
                JSONObject j = new JSONObject();
                j.put("id", SessionSave.getSession(PASS_ID, getActivity()));
                if (SessionSave.getSession(LOGOUT, getActivity()).equals("")) {
                    new TaxiUtil.Logout("type=passenger_logout", getActivity(), j);
                    ((MainHomeFragmentActivity) getActivity()).fbLogout();
                } else
                    CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.bookedtaxi));
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

//    public void alert_view() {
//
//        Intent intent = new Intent(getContext(), ContactShareActivity.class);
//        startActivity(intent);

//        try {
//            BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
//            View sheetView = getActivity().getLayoutInflater().inflate(R.layout.shareride_alert_view, null);
//            mBottomSheetDialog.setContentView(sheetView);
//            mBottomSheetDialog.show();
//
//            Colorchange.ChangeColor((ViewGroup) sheetView, getActivity());
//            FontHelper.applyFont(getActivity(), sheetView.findViewById(R.id.rootlay));
//
//            final EditText contactEdt = sheetView.findViewById(R.id.contactEdt);
//
//            final Button submit = sheetView.findViewById(R.id.submit);
//            final Button cancel = sheetView.findViewById(R.id.cancel);
//
//
//
//            contactEdt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    if (isPermissionsGranted()) {
////                        openContactIntent();
////                    } else {
////                        makeRequest();
////                    }
//                    SessionSave.saveSession("sos_id", SessionSave.getSession(PASS_ID, getContext()), getContext());
//                    SessionSave.saveSession("user_type", "p", getContext());
//                    startActivity(new Intent(getContext(), SOSActivity.class));
//                }
//            });
//            submit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    // TODO Auto-generated method stub
//                    alertmDialog.dismiss();
//                }
//            });
//            cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    // TODO Auto-generated method stub
//                    alertmDialog.dismiss();
//                }
//            });
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
 //   }

//    private void openContactIntent() {
//        Intent intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI).apply {
//            startActivityForResult(this, 1)
//        }
//    }

//    private boolean isPermissionsGranted() {
//        Permission permission = ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.READ_CONTACTS);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            Log.i("", "Permission to record denied");
////            makeRequest()
//            return false;
//        }
//        return true;
//    }

    public void callApi(boolean isChecked) {
        JSONObject j = new JSONObject();
        try {
            j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));

            if (isChecked)
                j.put("value", "1");
            else
                j.put("value", "2");
            j.put("type", typeOfApi);

            Log.e("Json ", j.toString());

            new SaveSplitType("type=set_split_fare", j);


        } catch (Exception e) {
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Colorchange.ChangeColor((ViewGroup)(PaymentOptionFrag.this.v),getActivity());
                Colorchange.ChangeColor((ViewGroup) (((ViewGroup) getActivity()
                        .findViewById(android.R.id.content)).getChildAt(0)), getActivity());
            }
        }, 100);
    }

    /**
     * this show the favourite driver to user
     */

    @Override
    public void trigger_FragPopFront() {
        ((MainHomeFragmentActivity) getActivity()).setTitle_m(NC.getResources().getString(R.string.settings));
        if (SessionSave.getSession(TaxiUtil.isFavDriverOn, getActivity(), false))
            favoritedriveron_off.setChecked(true);
        else
            favoritedriveron_off.setChecked(false);
    }

    /**
     * this class is used to set the split fare on off
     */

    public class SaveSplitType implements APIResult {


        public SaveSplitType(final String url, JSONObject data) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON(getActivity(), this, data, false).execute(url);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {

            try {
                final JSONObject json = new JSONObject(result);
                if (isSuccess) {
                    CToast.ShowToast(getActivity(), json.getString("message"));
                    if (json.getInt("status") == 1) {
                        if (typeOfApi == 1) {
                            SessionSave.saveSession(TaxiUtil.isSplitOn, true, getActivity());
                            spliton_off.setChecked(true);
                        } else if (typeOfApi == 2) {
                            SessionSave.saveSession(TaxiUtil.isFavDriverOn, true, getActivity());
                            favoritedriveron_off.setChecked(true);
                        } else if (typeOfApi == 3) {
                            SessionSave.saveSession(TaxiUtil.isSkipFavOn, true, getActivity());
                            skip_drop_on_off.setChecked(true);
                        }
                    } else {
                        if (typeOfApi == 1) {
                            SessionSave.saveSession(TaxiUtil.isSplitOn, false, getActivity());
                            spliton_off.setChecked(false);
                        } else if (typeOfApi == 2) {
                            SessionSave.saveSession(TaxiUtil.isFavDriverOn, false, getActivity());
                            favoritedriveron_off.setChecked(false);
                        } else if (typeOfApi == 3) {
                            SessionSave.saveSession(TaxiUtil.isSkipFavOn, false, getActivity());
                            skip_drop_on_off.setChecked(false);
                        }
//                        SessionSave.saveSession(TaxiUtil.isSplitOn, false, getActivity());
//                        spliton_off.setChecked(false);
                    }
                    Log.e("SplitOn ", String.valueOf(SessionSave.getSession(TaxiUtil.isSplitOn, getActivity(), true)));
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                CToast.ShowToast(getActivity(), json.getString("message"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
