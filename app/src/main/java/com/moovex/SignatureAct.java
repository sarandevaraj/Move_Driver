package com.moovex;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.mayan.sospluginmodlue.util.CToast;
import com.moovex.driver.DriverMyStatus;
import com.moovex.driver.DriverOngoingAct;
import com.moovex.driver.data.DriverCommonData;
import com.moovex.driver.utils.DriverNC;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * This class is used to change the driver password
 */
public class SignatureAct extends MainActivity implements OnClickListener {
    private Button btn_complete, btn_force_complete;
    private SignaturePad mSignaturePad;
    private TextView btn_clear;

    private ImageView slideImg;

    @Override
    public int setLayout() {
        return R.layout.signature_lay;
    }

    // Initialize the views on layout and variable declarations
    @Override
    public void Initialize() {
        // TODO Auto-generated method stub
        DriverCommonData.sContext = this;
        DriverCommonData.mActivitylist.add(this);
//        Colorchange.ChangeColor((ViewGroup) (((ViewGroup) SignatureAct.this
//                .findViewById(android.R.id.content)).getChildAt(0)), SignatureAct.this);

        mSignaturePad =  findViewById(R.id.signature_pad);
        btn_clear = findViewById(R.id.btn_clear);
        btn_complete = findViewById(R.id.btn_complete);
        btn_force_complete = findViewById(R.id.btn_force_complete);
        slideImg = findViewById(R.id.slideImg);

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                if (btn_clear.getVisibility() == View.GONE) {
                    btn_clear.setVisibility(View.VISIBLE);
                }

                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });

        setonclickListener();

        slideImg.setOnClickListener(v -> {
            onBackPressed();
        });
    }


    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }


    /**
     * On click listener
     */
    private void setonclickListener() {
        btn_complete.setOnClickListener(this);
        btn_force_complete.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        try {
            if (v.getId() == R.id.btn_complete) {
                Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                if (!mSignaturePad.isEmpty() && signatureBitmap != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    Bundle conData = new Bundle();
                    conData.putString("sign", encoded);
                    conData.putBoolean("force_complete", false);
                    Intent intent = new Intent();
                    intent.putExtras(conData);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    CToast.ShowToast(SignatureAct.this, DriverNC.getResources().getString(R.string.sign_required));
                }

            } else if (v.getId() == R.id.btn_force_complete) {
                Bundle conData = new Bundle();
                conData.putString("sign", "");
                conData.putBoolean("force_complete", true);
                Intent intent = new Intent();
                intent.putExtras(conData);
                setResult(RESULT_OK, intent);
                finish();
            } else if (v.getId() == R.id.btn_clear) {
                mSignaturePad.clear();
                btn_clear.setVisibility(View.GONE);

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
