package com.movedriverdriver.driver.earningchart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

public class DriverWeekaxisformatter implements IAxisValueFormatter {

    public static List<String> mMonths;

    public DriverWeekaxisformatter() {

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        if (mMonths.size() == ((int) value)) {
            return "11";
        } else {
            if (mMonths.size() > ((int) value)) return mMonths.get(((int) value));
            else return "0";
        }
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}

