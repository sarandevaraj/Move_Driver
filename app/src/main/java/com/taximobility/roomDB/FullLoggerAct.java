package com.taximobility.roomDB;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;


import com.taximobility.R;
import com.taximobility.util.Systems;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by developer on 7/5/18.
 */

public class FullLoggerAct extends AppCompatActivity {
    private String jsonString = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_fill_log);
        if (getIntent() != null) {
            jsonString = getIntent().getStringExtra("msg");
        }

        String bv = stringToJson(jsonString);
        ((TextView) findViewById(R.id.text)).setText(Html.fromHtml(bv));
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullLoggerAct.this, FindLogActivity.class);
                startActivity(intent);
            }
        });
    }

    private String stringToJson(String s) {
        String data = "";
        JSONObject sss = null;
        try {
            sss = new JSONObject(s);

            Iterator<?> i = sss.keys();
            do {
                String k = i.next().toString();

                data += "<b>" + k + "</b><br>&thinsp;";
                data += "<i>" + sss.getString(k) + "</i><br><br>";

                Systems.out.println(k + "___" + sss.getString(k));

            } while (i.hasNext());

            Systems.out.println("haiiiii" + data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}