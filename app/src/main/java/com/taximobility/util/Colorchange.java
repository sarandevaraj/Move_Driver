package com.taximobility.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.taximobility.R;
import com.taximobility.SplashActivity;
import com.taximobility.driver.utils.DriverCL;
import com.taximobility.driver.utils.DriverNC;

/**
 * this class is used to set the dynamic srings and colors commonly and used in whole project
 */
public class Colorchange {

    public static void ChangeColor(ViewGroup parentLayout, Context cc) {
        for (int count = 0; count < parentLayout.getChildCount(); count++) {
            View view = parentLayout.getChildAt(count);
            int color;
            Drawable background = view.getBackground();
            if (background instanceof ShapeDrawable) {
                // cast to 'ShapeDrawable'
                ShapeDrawable shapeDrawable = (ShapeDrawable) background;
                shapeDrawable.getPaint().setColor(DriverCL.getResources().getColor(cc, R.color.com_facebook_button_background_color));
                color = ((ColorDrawable) background).getColor();

            } else if (background instanceof GradientDrawable) {
                // cast to 'GradientDrawable'
                GradientDrawable gradientDrawable = (GradientDrawable) background;

               // gradientDrawable.setStroke(1, DriverCL.getResources().getColor(cc, R.color.linebottom_dark));
            } else if (background instanceof LayerDrawable) {

                try {
                    LayerDrawable layerDrawable = (LayerDrawable) background;
                    GradientDrawable selectedItem = (GradientDrawable) layerDrawable.getDrawable(0);
                    if (layerDrawable.getId(0) > 0) {
                        String name = cc.getResources().getResourceEntryName(layerDrawable.getId(0));
                       // selectedItem.setStroke(2, DriverCL.getResources().getColor(cc, R.color.button_accept));
                    } else {
                       // selectedItem.setStroke(1, DriverCL.getResources().getColor(cc, R.color.linebottom_dark));
                    }
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            } else if (background instanceof StateListDrawable) {
            } else if (background instanceof ColorDrawable) {
                color = ((ColorDrawable) background).getColor();
                String hexColor = String.format("#%06X", (0xFFFFFF & color));

                if (view instanceof EditText || view instanceof TextView || view instanceof Button) {
                    try {
                        String tt = "";
                        String text;
                        if (view instanceof EditText) {
                            EditText et = (EditText) (view);
                            if (et.getText().toString().trim().equals("")) {
                                text = et.getHint().toString();
                                if (SplashActivity.fields_value.indexOf(text) != -1) {
                                    String keyValue = SplashActivity.fields.get(SplashActivity.fields_value.indexOf(text));
                                    et.setHint(DriverNC.nfields_byName.get(keyValue));
                                }
                            } else {
                                text = et.getText().toString();
                                if (SplashActivity.fields_value.indexOf(text) != -1) {
                                    String keyValue = SplashActivity.fields.get(SplashActivity.fields_value.indexOf(text));
                                    et.setText(DriverNC.nfields_byName.get(keyValue));
                                }
                            }
                        } else {
                            if (view instanceof TextView && !(view instanceof EditText)) {

                                TextView tv = (TextView) (view);
                                text = (tv).getText().toString();

                                switch (hexColor) {
                                    case "#F5F5F5":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.header_bgcolor));
                                        break;
                                    case "#404041":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.header_text));
                                        break;
                                    case "#C2C2C2":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.linebottom_light));
                                        break;
                                    case "#646464":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.linebottom_dark));
                                        break;
                                    case "#666666":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.hintcolor));
                                        break;
                                    case "#48BF27":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.pickupheadertext));
                                        break;
                                   /* case "#00000000":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.black));
                                        break;*/
                                    case "#66000000":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.semi_transparent));
                                        break;
                                    case "#EE3324":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.button_accept));
                                        break;
                                    case "#8E1F16":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.button_reject));
                                        break;
                                    case "#00BFFF":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.paymentcard));
                                        break;
                                    case "#ECBE2A":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.MatThemectrlActive));
                                        break;
                                    case "#FE0000":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.cancelbtntxtcolor));
                                        break;
                                    case "#FF6666":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.buttonnormaltheme));
                                        break;
                                    case "#A2A2A2":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.textviewcolor_light));
                                        break;
                                    case "#ffffff":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.buttontextcolor));
                                        break;
                                    case "#FFFFFF":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.white));
                                        break;
                                    /*case "#000000":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.black));
                                        break;*/
                                    case "#1C1C24":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_bg));
                                        break;
                                    case "#333333":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_head_bg));
                                        break;
                                    case "#00000F":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.edittextcolor));
                                        // You can have any number of case statements.
                                }
                                if (SplashActivity.fields_value.indexOf(text) > 0) {
                                    String keyValue = SplashActivity.fields.get(SplashActivity.fields_value.indexOf(text));
                                    tv.setText(DriverNC.nfields_byName.get(keyValue));
                                    String hexColor11 = String.format("#%06X", (0xFFFFFF & tv.getCurrentTextColor()));
                                    switch (hexColor11) {
                                        case "#F5F5F5":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.header_bgcolor));
                                            break;
                                        case "#404041":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.header_text));
                                            break;
                                        case "#C2C2C2":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_light));
                                            break;
                                        case "#646464":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_dark));
                                            break;
                                        case "#666666":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.hintcolor));
                                            break;
                                        case "#48BF27":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.pickupheadertext));
                                            break;
                                     /*   case "#00000000":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                            break;*/
                                        case "#66000000":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.semi_transparent));
                                            break;
                                        case "#EE3324":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.button_accept));
                                            break;
                                        case "#8E1F16":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.button_reject));
                                            break;
                                        case "#00BFFF":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.paymentcard));
                                            break;
                                        case "#ECBE2A":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.MatThemectrlActive));
                                            break;
                                        case "#FE0000":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.cancelbtntxtcolor));
                                            break;
                                        case "#FF6666":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.buttonnormaltheme));
                                            break;
                                        case "#A2A2A2":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.textviewcolor_light));
                                            break;
                                        case "#ffffff":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.buttontextcolor));
                                            break;
                                        case "#FFFFFF":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.white));
                                            break;
                                     /*   case "#000000":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                            break;*/
                                        case "#1C1C24":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_bg));
                                            break;
                                        case "#333333":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_head_bg));
                                            break;
                                        case "#00000F":
                                            tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.edittextcolor));
                                            // You can have any number of case statements.
                                    }
                                }
                            } else {
                                Button tv = (Button) (view);
                                text = (tv).getText().toString();

                                String keyValue = SplashActivity.fields.get(SplashActivity.fields_value.indexOf(text));
                                tv.setText(DriverNC.nfields_byName.get(keyValue));

                                String hexColor11 = String.format("#%06X", (0xFFFFFF & tv.getCurrentTextColor()));

                                switch (hexColor) {
                                    case "#F5F5F5":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.header_bgcolor));
                                        break;
                                    case "#404041":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.header_text));
                                        break;
                                    case "#C2C2C2":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.linebottom_light));
                                        break;
                                    case "#646464":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.linebottom_dark));
                                        break;
                                    case "#666666":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.hintcolor));
                                        break;
                                    case "#48BF27":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.pickupheadertext));
                                        break;
                                /*    case "#00000000":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.black));
                                        break;*/
                                    case "#66000000":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.semi_transparent));
                                        break;
                                    case "#EE3324":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.button_accept));
                                        break;
                                    case "#8E1F16":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.button_reject));
                                        break;
                                    case "#00BFFF":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.paymentcard));
                                        break;
                                    case "#ECBE2A":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.MatThemectrlActive));
                                        break;
                                    case "#FE0000":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.cancelbtntxtcolor));
                                        break;
                                    case "#FF6666":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.buttonnormaltheme));
                                        break;
                                    case "#A2A2A2":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.textviewcolor_light));
                                        break;
                                    case "#ffffff":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.buttontextcolor));
                                        break;
                                    case "#FFFFFF":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.white));
                                        break;
                                /*    case "#000000":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.black));
                                        break;*/
                                    case "#1C1C24":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_bg));
                                        break;
                                    case "#333333":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_head_bg));
                                        break;
                                    case "#00000F":
                                        tv.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.edittextcolor));
                                        // You can have any number of case statements.
                                }
                                switch (hexColor11) {
                                    case "#F5F5F5":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.header_bgcolor));
                                        break;
                                    case "#404041":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.header_text));
                                        break;
                                    case "#C2C2C2":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_light));
                                        break;
                                    case "#646464":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_dark));
                                        break;
                                    case "#666666":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.hintcolor));
                                        break;
                                    case "#48BF27":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.pickupheadertext));
                                        break;
                                /*    case "#00000000":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                        break;*/
                                    case "#66000000":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.semi_transparent));
                                        break;
                                    case "#EE3324":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.button_accept));
                                        break;
                                    case "#8E1F16":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.button_reject));
                                        break;
                                    case "#00BFFF":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.paymentcard));
                                        break;
                                    case "#ECBE2A":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.MatThemectrlActive));
                                        break;
                                    case "#FE0000":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.cancelbtntxtcolor));
                                        break;
                                    case "#FF6666":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.buttonnormaltheme));
                                        break;
                                    case "#A2A2A2":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.textviewcolor_light));
                                        break;
                                    case "#ffffff":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.buttontextcolor));
                                        break;
                                    case "#FFFFFF":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.white));
                                        break;
                                  /*  case "#000000":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                        break;*/
                                    case "#1C1C24":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_bg));
                                        break;
                                    case "#333333":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_head_bg));
                                        break;
                                    case "#00000F":
                                        tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.edittextcolor));
                                        // You can have any number of case statements.
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                switch (hexColor) {
                    case "#F5F5F5":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.header_bgcolor));
                        break;
                    case "#404041":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.header_text));
                        break;
                    case "#C2C2C2":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.linebottom_light));
                        break;
                    case "#646464":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.linebottom_dark));
                        break;
                    case "#666666":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.hintcolor));
                        break;
                    case "#48BF27":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.pickupheadertext));
                        break;
    /*                case "#000000":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.black));
                        break;*/
                    case "#1C1C24":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_bg));
                        break;
                    case "#333333":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_head_bg));
                        break;
                    case "#00000F":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.edittextcolor));
                    case "#660000":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.semi_transparent));
                        break;
                    case "#EE3324":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.button_accept));
                        break;
                    case "#8E1F16":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.button_reject));
                        break;
                    case "#00BFFF":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.paymentcard));
                        break;
                    case "#ECBE2A":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.MatThemectrlActive));
                        break;
                    case "#FE0000":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.cancelbtntxtcolor));
                        break;
                    case "#FF6666":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.buttonnormaltheme));
                        break;
                    case "#A2A2A2":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.textviewcolor_light));
                        break;
                    case "#ffffff":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.buttontextcolor));
                        break;
                    case "#FFFFFF":
                        view.setBackgroundColor(DriverCL.getResources().getColor(cc, R.color.white));
                        break;
                }
            }

            if (view instanceof EditText || view instanceof TextView || view instanceof Button) {
                try {
                    String tt = "";
                    String text;
                    if (view instanceof EditText) {
                        EditText tv = (EditText) (view);

                        if (tv != null && tv.getHint() != null && !tv.getHint().toString().trim().equals("")) {
                            text = tv.getHint().toString();
                            if (SplashActivity.fields_value.indexOf(text) != -1) {
                                String keyValue = SplashActivity.fields.get(SplashActivity.fields_value.indexOf(text));
                                ((EditText) view).setHint(DriverNC.nfields_byName.get(keyValue));
                            }
                        } else {
                        }
                        String hexColor = String.format("#%06X", (0xFFFFFF & tv.getCurrentHintTextColor()));

                        switch (hexColor) {
                            case "#F5F5F5":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.header_bgcolor));
                                break;
                            case "#404041":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.header_text));
                                break;
                            case "#C2C2C2":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_light));
                                break;
                            case "#646464":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_dark));
                                break;
                            case "#666666":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.hintcolor));
                                break;
                            case "#48BF27":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.pickupheadertext));
                                break;
                       /*     case "#00000000":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                break;*/
                            case "#66000000":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.semi_transparent));
                                break;
                            case "#EE3324":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.button_accept));
                                break;
                            case "#8E1F16":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.button_reject));
                                break;
                            case "#00BFFF":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.paymentcard));
                                break;
                            case "#ECBE2A":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.MatThemectrlActive));
                                break;
                            case "#FE0000":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.cancelbtntxtcolor));
                                break;
                            case "#FF6666":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.buttonnormaltheme));
                                break;
                            case "#A2A2A2":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.textviewcolor_light));
                                break;
                            case "#ffffff":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.buttontextcolor));
                                break;
                            case "#FFFFFF":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.white));
                                break;
                            case "#000000":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                break;
                            case "#1C1C24":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_bg));
                                break;
                            case "#333333":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_head_bg));
                                break;
                            case "#00000F":
                                tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.edittextcolor));
                        }
                        if (!tv.getText().toString().trim().equals("")) {
                            text = tv.getText().toString();
                            if (SplashActivity.fields_value.indexOf(text) != -1) {
                                String keyValue = SplashActivity.fields.get(SplashActivity.fields_value.indexOf(text));
                                ((EditText) view).setText(DriverNC.nfields_byName.get(keyValue));
                            }
                        }
                        String hexColort = String.format("#%06X", (0xFFFFFF & tv.getCurrentTextColor()));

                        switch (hexColort) {
                            case "#F5F5F5":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.header_bgcolor));
                                break;
                            case "#404041":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.header_text));
                                break;
                            case "#C2C2C2":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_light));
                                break;
                            case "#646464":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_dark));
                                break;
                            case "#666666":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.hintcolor));
                                break;
                            case "#48BF27":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.pickupheadertext));
                                break;
                            case "#00000000":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                break;
                            case "#66000000":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.semi_transparent));
                                break;
                            case "#EE3324":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.button_accept));
                                break;
                            case "#8E1F16":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.button_reject));
                                break;
                            case "#00BFFF":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.paymentcard));
                                break;
                            case "#ECBE2A":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.MatThemectrlActive));
                                break;
                            case "#FE0000":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.cancelbtntxtcolor));
                                break;
                            case "#FF6666":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.buttonnormaltheme));
                                break;
                            case "#A2A2A2":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.textviewcolor_light));
                                break;
                            case "#ffffff":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.buttontextcolor));
                                break;
                            case "#FFFFFF":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.white));
                                break;
                            case "#000000":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                break;
                            case "#1C1C24":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_bg));
                                break;
                            case "#333333":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_head_bg));
                                break;
                            case "#00000F":
                                tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.edittextcolor));
                        }
                    } else {
                        if (view instanceof TextView) {

                            TextView tv = (TextView) (view);
                            text = (tv).getText().toString();
                            if (SplashActivity.fields_value.indexOf(text) != -1) {
                                String keyValue = SplashActivity.fields.get(SplashActivity.fields_value.indexOf(text));
                                tv.setText(DriverNC.nfields_byName.get(keyValue));
                            }
                            String hexColor = String.format("#%06X", (0xFFFFFF & tv.getCurrentTextColor()));

                            switch (hexColor) {
                                case "#F5F5F5":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.header_bgcolor));
                                    break;
                                case "#404041":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.header_text));
                                    break;
                                case "#C2C2C2":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_light));
                                    break;
                                case "#646464":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_dark));
                                    break;
                                case "#666666":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.hintcolor));
                                    break;
                                case "#48BF27":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.pickupheadertext));
                                    break;
                                case "#00000000":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                    break;
                                case "#66000000":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.semi_transparent));
                                    break;
                                case "#EE3324":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.button_accept));
                                    break;
                                case "#8E1F16":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.button_reject));
                                    break;
                                case "#00BFFF":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.paymentcard));
                                    break;
                                case "#ECBE2A":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.MatThemectrlActive));
                                    break;
                                case "#FE0000":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.cancelbtntxtcolor));
                                    break;
                                case "#FF6666":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.buttonnormaltheme));
                                    break;
                                case "#A2A2A2":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.textviewcolor_light));
                                    break;
                                case "#ffffff":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.buttontextcolor));
                                    break;
                                case "#FFFFFF":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.white));
                                    break;
                                /*case "#000000":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                    break;*/
                                case "#1C1C24":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_bg));
                                    break;
                                case "#333333":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_head_bg));
                                    break;
                                case "#00000F":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.edittextcolor));
                            }
                            String hexColort = String.format("#%06X", (0xFFFFFF & tv.getCurrentHintTextColor()));
                            switch (hexColort) {
                                case "#F5F5F5":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.header_bgcolor));
                                    break;
                                case "#404041":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.header_text));
                                    break;
                                case "#C2C2C2":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_light));
                                    break;
                                case "#646464":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_dark));
                                    break;
                                case "#666666":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.hintcolor));
                                    break;
                                case "#48BF27":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.pickupheadertext));
                                    break;
                             /*   case "#00000000":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                    break;*/
                                case "#66000000":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.semi_transparent));
                                    break;
                                case "#EE3324":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.button_accept));
                                    break;
                                case "#8E1F16":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.button_reject));
                                    break;
                                case "#00BFFF":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.paymentcard));
                                    break;
                                case "#ECBE2A":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.MatThemectrlActive));
                                    break;
                                case "#FE0000":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.cancelbtntxtcolor));
                                    break;
                                case "#FF6666":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.buttonnormaltheme));
                                    break;
                                case "#A2A2A2":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.textviewcolor_light));
                                    break;
                                case "#ffffff":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.buttontextcolor));
                                    break;
                                case "#FFFFFF":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.white));
                                    break;
                             /*   case "#000000":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                    break;*/
                                case "#1C1C24":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_bg));
                                    break;
                                case "#333333":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_head_bg));
                                    break;
                                case "#00000F":
                                    tv.setHintTextColor(DriverCL.getResources().getColor(cc, R.color.edittextcolor));
                            }
                        } else {
                            Button tv = (Button) (view);
                            text = (tv).getText().toString();
                            if (SplashActivity.fields_value.indexOf(text) != -1) {
                                String keyValue = SplashActivity.fields.get(SplashActivity.fields_value.indexOf(text));
                                tv.setText(DriverNC.nfields_byName.get(keyValue));
                            }
                            String hexColor = String.format("#%06X", (0xFFFFFF & tv.getCurrentTextColor()));

                            switch (hexColor) {
                                case "#F5F5F5":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.header_bgcolor));
                                    break;
                                case "#404041":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.header_text));
                                    break;
                                case "#C2C2C2":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_light));
                                    break;
                                case "#646464":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.linebottom_dark));
                                    break;
                                case "#666666":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.hintcolor));
                                    break;
                                case "#48BF27":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.pickupheadertext));
                                    break;
                                /*case "#00000000":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                    break;*/
                                case "#66000000":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.semi_transparent));
                                    break;
                                case "#EE3324":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.button_accept));
                                    break;
                                case "#8E1F16":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.button_reject));
                                    break;
                                case "#00BFFF":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.paymentcard));
                                    break;
                                case "#ECBE2A":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.MatThemectrlActive));
                                    break;
                                case "#F0000":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.cancelbtntxtcolor));
                                    break;
                                case "#FF6666":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.buttonnormaltheme));
                                    break;
                                case "#A2A2A2":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.textviewcolor_light));
                                    break;
                                case "#ffffff":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.buttontextcolor));
                                    break;
                                case "#FFFFFF":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.white));
                                    break;
                             /*   case "#000000":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.black));
                                    break;*/
                                case "#1C1C24":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_bg));
                                    break;
                                case "#333333":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.home_drawer_head_bg));
                                    break;
                                case "#00000F":
                                    tv.setTextColor(DriverCL.getResources().getColor(cc, R.color.edittextcolor));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (view instanceof ViewGroup) {
                ChangeColor((ViewGroup) view, cc);
            }
        }
    }

    private static String getStringResourceByName(String aString, Context cc) {
        String resId = cc.getResources().getString(cc.getResources().getIdentifier(aString, "string", cc.getPackageName()));
        return resId;
    }
}