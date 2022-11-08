package com.taximobility.util;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;

import com.taximobility.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * this class is use to display common date pickers
 */
public class DatePicker_CardExpiry extends DialogFragment {
    Calendar cal;
    int curMonth;
    int curYear;

    DatePicker datePicker;
    TextView ok, cancel;
    TextView dialogtitle;
    int initMonth, initYear;

    public interface DateDialogInterface {
        void onSuccess(int month, int year);

        void failure(String inputText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.date_picker_dialog, container, false);
        try {
            if (getArguments() != null) {
                initMonth = getArguments().getInt("month");
                initYear = getArguments().getInt("year");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FontHelper.applyFont(getActivity(), v);
        dialogtitle = v.findViewById(R.id.dialogtitle);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        cal = Calendar.getInstance();
        curMonth = cal.get(Calendar.MONTH);
        curYear = cal.get(Calendar.YEAR);
        datePicker = v.findViewById(R.id.datePicker1);
        if (initYear == 0) {
            initYear = curYear;
            initMonth = curMonth;
        }
        datePicker.init(initYear, initMonth-1, 2, (view, year, monthOfYear, dayOfMonth) -> {
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            FontHelper.overrideFonts(getActivity(), view);

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM");

            String formatedDate = "";
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                formatedDate = sdf.format(LocalDate.of(year, month, day));
//            }else{
                formatedDate = sdf.format(new Date(year, month, day));
//            }
            dialogtitle.setText(formatedDate + " " + view.getYear());
        });

        datePicker.setMinDate(cal.getTimeInMillis() - 1000);
        ok = v.findViewById(R.id.ok);
        cancel = v.findViewById(R.id.cancel);

        ok.setOnClickListener(v12 -> ((DateDialogInterface) getActivity()).onSuccess(datePicker.getMonth() + 1, datePicker.getYear()));
        cancel.setOnClickListener(v1 -> ((DateDialogInterface) getActivity()).failure(NC.getString(R.string.cancel)));


        return v;
    }
}
