package com.moovex;

import static com.moovex.util.ConstantsKt.API_BASE;

import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import com.moovex.util.SessionSave;


public class DriverFirebaseChatWebView extends AppCompatActivity {


    private String trip_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_chat_webview_act);
        WebView mywebview = (WebView) findViewById(R.id.simpleWebView);

        if (getIntent() != null) {
            trip_id = getIntent().hasExtra("trip_id") ? getIntent().getStringExtra("trip_id") : "";
        }

        System.out.println("SathishTest" + SessionSave.getSession(API_BASE, DriverFirebaseChatWebView.this) + "chat.html?" +"type=" + "DTOP" + "&tripid=" +trip_id );
        mywebview.loadUrl(SessionSave.getSession(API_BASE, DriverFirebaseChatWebView.this) + "chat.html?" +"type=" + "DTOP" + "&tripid=" +trip_id );
    }
}
