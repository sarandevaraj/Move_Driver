package com.taximobility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.Systems;

/**
 * Created by developer on 1/7/17.
 */

public class Dummy extends AppCompatActivity {
    private Dialog errorDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashlay);
        Intent i;
        i = new Intent(Dummy.this, TripDetailsAct.class);
        startActivity(i);
    }

    public void errorInSplash(String message) {
        try {
            if (true) {
                if (errorDialog != null && errorDialog.isShowing())
                    errorDialog.dismiss();
                Systems.out.println("setCanceledOnTouchOutside" + message);
                //netcon_layy.......
                final View view = View.inflate(Dummy.this, R.layout.netcon_lay, null);
                errorDialog = new Dialog(Dummy.this, R.style.dialogwinddow);
                errorDialog.setContentView(view);
                errorDialog.setCancelable(false);
                errorDialog.setCanceledOnTouchOutside(false);
                FontHelper.applyFont(Dummy.this, errorDialog.findViewById(R.id.alert_id));
                errorDialog.show();
                final TextView title_text = errorDialog.findViewById(R.id.title_text);
                final TextView message_text = errorDialog.findViewById(R.id.message_text);
                final Button button_success = errorDialog.findViewById(R.id.button_success);
                final Button button_failure = errorDialog.findViewById(R.id.button_failure);
                title_text.setText("" + NC.getString(R.string.message));
                message_text.setText("" + message);
                button_success.setText("" + NC.getString(R.string.try_again));
                button_failure.setText("" + NC.getString(R.string.cancel));
                button_success.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        // TODO Auto-generated method stub

                        errorDialog.dismiss();

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });
                button_failure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        // TODO Auto-generated method stub


                        Activity activity = Dummy.this;

                        final Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                        activity.finish();
                        errorDialog.dismiss();

                    }
                });
            } else {
                try {
                    errorDialog.dismiss();
                    if (Dummy.this != null) {
                        Intent intent = new Intent(Dummy.this, Dummy.this.getClass());
                        Dummy.this.startActivity(intent);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
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


