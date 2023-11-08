package com.mayan.sospluginmodlue.countrycode;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.mayan.sospluginmodlue.R;
import com.mayan.sospluginmodlue.util.CToast;

import java.util.List;

/**
 * Created by developer on 22/2/18.
 */

public class CountryCodeDialog {

    public static void openCountryCodeDialog(CountryCodePicker codePicker) {
        Context context = codePicker.getContext();
        final Dialog dialog = new Dialog(context);
        codePicker.refreshCustomMasterList();
        codePicker.refreshPreferredCountries();
        List<Country> masterCountries = Country.getCustomMasterCountryList(codePicker);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setContentView(R.layout.layout_picker_dialog);
        final ViewGroup countryCodeLayout = dialog.findViewById(R.id.countryCodeLayout);
//        FontHelper.applyFont(context, countryCodeLayout);
//        Colorchange.ChangeColor(countryCodeLayout, context);
        RecyclerView recyclerView_countryDialog = dialog.findViewById(R.id.recycler_countryDialog);
        final EditText editText_search = dialog.findViewById(R.id.editText_search);
        TextView textView_noResult = dialog.findViewById(R.id.textView_noresult);
        final CountryCodeAdapter cca = new CountryCodeAdapter(context, masterCountries, codePicker, editText_search, textView_noResult, dialog);
        if (!codePicker.isSelectionDialogShowSearch()) {
            CToast.ShowToast(context, context.getString(R.string.found_search));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) recyclerView_countryDialog.getLayoutParams();
            params.height = RecyclerView.LayoutParams.WRAP_CONTENT;
            recyclerView_countryDialog.setLayoutParams(params);
        }
        recyclerView_countryDialog.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_countryDialog.setAdapter(cca);
        dialog.show();
    }
}
