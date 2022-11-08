package com.taximobility;

import android.view.View;
import android.widget.TextView;

public class ActivityThanks extends MainActivity{
    private TextView txt_msg;
    private TextView BackBtn, HeadTitle;
    @Override
    public int setLayout() {
        return R.layout.activity_thanks;
    }

    @Override
    public void Initialize() {

        BackBtn = findViewById(R.id.slideImg);
        txt_msg = findViewById(R.id.txt_msg);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
