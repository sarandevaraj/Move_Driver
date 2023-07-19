package com.movedriverdriver.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.movedriverdriver.R;
import com.movedriverdriver.driver.interfaces.DriverAPIResult;
import com.movedriverdriver.driver.service.DriverAPIService_Retrofit_JSON;
import com.movedriverdriver.driver.utils.DriverNC;
import com.movedriverdriver.driver.utils.DriverSessionSave;
import com.movedriverdriver.driver.utils.DriverSystems;

import java.util.Locale;

public class DriverlanguageAct extends AppCompatActivity {

    private Dialog mlangDialog;
    private int types = 1;

    private LinearLayout lay_fav_res1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverlanguage);

        String[] totalLang = (DriverSessionSave.getSession("lang_json", DriverlanguageAct.this)).trim().split("____");

        lay_fav_res1 = findViewById(R.id.language_list);
        for (int i = 0; i < totalLang.length; i++) {
            // lay_fav_res1.
            TextView tv = new TextView(DriverlanguageAct.this);
            tv.setText(DriverSessionSave.getSession("LANG" + i, DriverlanguageAct.this).replaceAll(".xml", ""));
            tv.setTag(i);
            tv.setPadding(15, 15, 15, 15);
            if (DriverSessionSave.getSession("Lang", DriverlanguageAct.this).equals("ar") || DriverSessionSave.getSession("Lang", DriverlanguageAct.this).equals("fa"))
                tv.setGravity(Gravity.RIGHT);
            tv.setOnClickListener(v1 -> {
                int pos = (int) v1.getTag();
                types = pos;
                String url = DriverSessionSave.getSession(DriverSessionSave.getSession("LANG" + pos, DriverlanguageAct.this), DriverlanguageAct.this);
                DriverSystems.out.println("current_url" + url);
                DriverSessionSave.saveSession("currentStringUrl", url, DriverlanguageAct.this);
                if (DriverSessionSave.getSession("Lang", DriverlanguageAct.this).equalsIgnoreCase(DriverSessionSave.getSession("LANGCode" + pos, DriverlanguageAct.this)))
                    new callString("strings.xml");

            });
            lay_fav_res1.addView(tv);
        }

    }

    private class callString implements DriverAPIResult {
        public callString(final String url) {
            String urls = DriverSessionSave.getSession("currentStringUrl", DriverlanguageAct.this);
            if (urls.equals(""))
                urls = DriverSessionSave.getSession("Lang_English", DriverlanguageAct.this);
            new DriverAPIService_Retrofit_JSON(DriverlanguageAct.this, this, null, true, urls, true).execute();
        }

        @Override
        public void getResult(boolean isSuccess, String result) {
            if (isSuccess) {
                DriverNC.nfields_byID.clear();
                DriverNC.nfields_byName.clear();
                DriverNC.fields.clear();
                DriverNC.fields_value.clear();
                DriverNC.fields_id.clear();
                setLocale();
                DriverSessionSave.saveSession("wholekey", result, DriverlanguageAct.this);
                //   getAndStoreStringValues(result);
                RefreshAct();
            }
        }
    }

    public void setLocale() {
        if (DriverSessionSave.getSession("Lang", DriverlanguageAct.this).equals("")) {
            DriverSessionSave.saveSession("Lang", "en", DriverlanguageAct.this);
            DriverSessionSave.saveSession("Lang_Country", "en_GB", DriverlanguageAct.this);
        }
        DriverSystems.out.println("Lang" + DriverSessionSave.getSession("Lang", DriverlanguageAct.this));
        DriverSystems.out.println("Lang_Country" + DriverSessionSave.getSession("Lang_Country", DriverlanguageAct.this));
        Configuration config = new Configuration();
        String langcountry = DriverSessionSave.getSession("Lang_Country", DriverlanguageAct.this);
        String language = DriverSessionSave.getSession("Lang", DriverlanguageAct.this);
        String[] arry = langcountry.split("_");
        config.locale = new Locale(language, arry[1]);
        Locale.setDefault(new Locale(language, arry[1]));
        DriverlanguageAct.this.getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    private void RefreshAct() {
        String temptype = DriverSessionSave.getSession("LANGTemp" + types, DriverlanguageAct.this);
        DriverSessionSave.saveSession("Lang", DriverSessionSave.getSession("LANGCode" + types, DriverlanguageAct.this), DriverlanguageAct.this);

        if (temptype.equals("LTR")) {
            DriverSessionSave.saveSession("Lang_Country", "en_US", DriverlanguageAct.this);
        } else {
            DriverSessionSave.saveSession("Lang_Country", "ar_EG", DriverlanguageAct.this);
        }
        Configuration config = new Configuration();
        String langcountry = DriverSessionSave.getSession("Lang_Country", DriverlanguageAct.this);
        String[] arry = langcountry.split("_");
        String language = DriverSessionSave.getSession("Lang", DriverlanguageAct.this);

        config.locale = new Locale(language, arry[1]);
        Locale.setDefault(new Locale(language, arry[1]));
        DriverlanguageAct.this.getBaseContext().getResources().updateConfiguration(config, DriverlanguageAct.this.getResources().getDisplayMetrics());

        Intent intent = new Intent(DriverlanguageAct.this, DriverMyStatus.class);
        //  showLoading(DriverMeAct.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}