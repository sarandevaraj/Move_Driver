package com.taximobility.driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.driver.utils.DirverColorchange;
import com.taximobility.driver.utils.DriverFontHelper;
import com.taximobility.driver.utils.DriverNC;

public class DeleteAccountActivityDriver extends MainActivityDriver {
    Button deleteacc;
    private TextView HeadTitle;
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayout() {
        return R.layout.driver_activity_delete_account;
    }

    @Override
    public void Initialize() {
        DirverColorchange.ChangeColor((ViewGroup) (((ViewGroup) DeleteAccountActivityDriver.this
                .findViewById(android.R.id.content)).getChildAt(0)), DeleteAccountActivityDriver.this);

        DriverFontHelper.applyFont(this, findViewById(R.id.content));


        deleteacc = findViewById(R.id.deleteacc);
        HeadTitle = findViewById(R.id.headerTxt);
        btn_back = findViewById(R.id.slideImg);
        btn_back.setVisibility(View.VISIBLE);
        HeadTitle.setText(DriverNC.getString(R.string.privacy_settings));
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        deleteacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(DeleteAccountActivityDriver.this, DriverWebviewAct.class);
                in.putExtra("type", "delete");
                startActivity(in);
            }
        });


    }

}
