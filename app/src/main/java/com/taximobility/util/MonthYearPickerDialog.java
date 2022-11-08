package com.taximobility.util;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.taximobility.R;

import java.util.Calendar;

public class MonthYearPickerDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.month_year_picker_dialog, null);
        Colorchange.ChangeColor((ViewGroup) dialog, requireContext());
        final NumberPicker monthPicker = dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = dialog.findViewById(R.id.picker_year);

        int month = cal.get(Calendar.MONTH);
        monthPicker.setMinValue(month+1);

        yearPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {

            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {

                if (scrollState==SCROLL_STATE_IDLE){
                    if (yearPicker.getMinValue()==view.getValue()){
                        int month = cal.get(Calendar.MONTH);
                        monthPicker.setMinValue(month+1);
                    }else {
                        monthPicker.setMinValue(1);
                    }
                }
            }
        });

        int year = cal.get(Calendar.YEAR);

        yearPicker.setMinValue(year);
        yearPicker.setMaxValue(year + 30);
        yearPicker.setValue(year);

        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        builder.setView(dialog)
                .setPositiveButton(NC.getString(R.string.ok), new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        ((DialogInterface) requireActivity()).onSuccess(monthPicker.getValue(), yearPicker.getValue(), 0);
                    }
                })
                .setNegativeButton(NC.getString(R.string.cancel), new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        ((DialogInterface) requireActivity()).failure("cancel");
                    }
                });
        AlertDialog mDialog = builder.create();
        mDialog.setOnShowListener(new android.content.DialogInterface.OnShowListener() {
            @Override
            public void onShow(android.content.DialogInterface dialog) {
                if (mDialog != null) {
                    mDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(CL.getColor(getActivity(),R.color.button_accept));
                    mDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(CL.getColor(getActivity(),R.color.black));
                }
            }
        });
        return mDialog;
    }

    public interface DialogInterface {
        void onSuccess(int month, int year, int day);

        void failure(String inputText);
    }
}