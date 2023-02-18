package com.movedriverdriver.driver.utils.drawable_program;

import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.movedriverdriver.R;
import com.movedriverdriver.driver.utils.DriverCL;

/**
 * Created by developer on 7/3/17.
 */
public class Drawables_program {
    public static View shift_bg_grey(View v) {

        GradientDrawable drawable = (GradientDrawable) v.getBackground();
        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(DriverCL.getColor(R.color.shiftoffcolor));
        gdDefault.setCornerRadius(50);
        gdDefault.setStroke(1, DriverCL.getColor(R.color.shiftoffcolor));
        v.setBackground(gdDefault);
        return v;
    }

    public static View shift_on(View v) {

        GradientDrawable drawable = (GradientDrawable) v.getBackground();
        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(DriverCL.getColor(R.color.shiftoncolor));
        gdDefault.setCornerRadius(50);
        gdDefault.setStroke(1, DriverCL.getColor(R.color.shiftoncolor));
        v.setBackground(gdDefault);
        return v;
    }
}
