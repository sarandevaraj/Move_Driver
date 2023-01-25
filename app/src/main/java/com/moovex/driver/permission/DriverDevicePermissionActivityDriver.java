package com.moovex.driver.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.moovex.R;
import com.moovex.driver.MainActivityDriver;
import com.moovex.driver.DriverTermsAndConditions;
import com.moovex.driver.DriverUserLoginAct;
import com.moovex.driver.interfaces.DriverAPIResult;
import com.moovex.driver.service.DriverAPIService_Retrofit_JSON;
import com.moovex.driver.utils.DriverCL;
import com.moovex.driver.utils.DriverCToast;
import com.moovex.driver.utils.DriverFontHelper;
import com.moovex.driver.utils.DriverNC;
import com.moovex.driver.utils.DriverSessionSave;
import com.moovex.driver.utils.DriverSystems;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DriverDevicePermissionActivityDriver extends MainActivityDriver {
    AppCompatCheckBox accessLocationCheckBox, readContactsCheckBox, readDeviceInformationCheckbox, accessCallPhoneCheckBox, accessStorageCheckBox, termsCheckBox;
    AppCompatButton buttonProceed;
    AppCompatTextView storeDataView, loc_mandatory, tv_contacts, tv_device_info, tv_call_permission, tv_storage_gallery;

    private static final String ACCESS_COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String ACCESS_FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String READ_EXTERNAL_STOARGE_PERMISSSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String WRITE_EXTERNAL_STOARGE_PERMISSSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    //    private static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    private static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    private static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    private static final int COMMON_REQUEST_CODE = 1000;
    private static final int FROM_RATIONALE = 1;
    private static final int NORMAL = 2;
    private ArrayList<String> permissionsList;
    boolean isEnable = true;
    private String location, mandatory;

    @Override
    public int setLayout() {
        return R.layout.driver_activity_device_permission;
    }

    @Override
    public void Initialize() {

        accessLocationCheckBox = findViewById(R.id.accessLocationCheckBox);
        readContactsCheckBox = findViewById(R.id.readContactsCheckBox);
        readDeviceInformationCheckbox = findViewById(R.id.readDeviceInformationCheckbox);
        accessCallPhoneCheckBox = findViewById(R.id.accessCallPhoneCheckBox);
        accessStorageCheckBox = findViewById(R.id.accessStorageCheckBox);
        buttonProceed = findViewById(R.id.buttonProceed);
        storeDataView = findViewById(R.id.storeDataView);
        termsCheckBox = findViewById(R.id.termsCheckBox);
        loc_mandatory = findViewById(R.id.loc_mandatory);
        tv_contacts = findViewById(R.id.tv_contacts);
        tv_device_info = findViewById(R.id.tv_device_info);
        tv_call_permission = findViewById(R.id.tv_call_permission);
        tv_storage_gallery = findViewById(R.id.tv_storage_gallery);
        DriverFontHelper.applyFont(this, findViewById(R.id.id_privacy_parent_lay));
        location = getColoredSpanned(DriverNC.getString(R.string.location), DriverCL.getColor(R.color.black));
        mandatory = getColoredSpanned(DriverNC.getString(R.string.mandatory), DriverCL.getColor(R.color.colorAccent));
        loc_mandatory.setText(Html.fromHtml(location.concat(" ").concat(mandatory)));

//        loc_mandatory.setTypeface(MyApplication.getInstance().getTypeFace(0));
//        tv_contacts.setTypeface(MyApplication.getInstance().getTypeFace(0));
//        tv_device_info.setTypeface(MyApplication.getInstance().getTypeFace(0));
//        tv_call_permission.setTypeface(MyApplication.getInstance().getTypeFace(0));
//        tv_storage_gallery.setTypeface(MyApplication.getInstance().getTypeFace(0));

        accessLocationCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> DriverCToast.ShowToast(context, DriverNC.getString(R.string.location_permission_required) + " " + DriverNC.getString(R.string.without_loc))

        );
//                        Driver_Utils.alert_view_dialog(DriverDevicePermissionActivityDriver.this, DriverNC.getString(R.string.location_permission_required), DriverNC.getString(R.string.without_loc), DriverNC.getString(R.string.ok), "", true, (dialog, i) -> {
//
//                    dialog.dismiss();
//
//
//                }, (dialog2, i) -> {
//                    dialog2.dismiss();
//
//                }, "")
//        );

        buttonProceed.setOnClickListener(View -> {
            permissionsList = new ArrayList<>();

            if (ContextCompat.checkSelfPermission(DriverDevicePermissionActivityDriver.this, ACCESS_COARSE_LOCATION_PERMISSION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(DriverDevicePermissionActivityDriver.this, ACCESS_FINE_LOCATION_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(ACCESS_COARSE_LOCATION_PERMISSION);
                permissionsList.add(ACCESS_FINE_LOCATION_PERMISSION);
                permissionsList.add(CALL_PHONE);
            }
            if (accessStorageCheckBox.isChecked()) {
                permissionsList.add(READ_EXTERNAL_STOARGE_PERMISSSION);
                permissionsList.add(WRITE_EXTERNAL_STOARGE_PERMISSSION);
                permissionsList.add(CALL_PHONE);
            }
/*
            if (accessCallPhoneCheckBox.isChecked())
                permissionsList.add(CALL_PHONE);*/
/*
            if (readContactsCheckBox.isChecked())
                permissionsList.add(READ_CONTACTS);*/

            if (readDeviceInformationCheckbox.isChecked()) permissionsList.add(READ_PHONE_STATE);

            if (termsCheckBox.isChecked()) CheckPermissions(permissionsList);
            else
                ShowToast(DriverDevicePermissionActivityDriver.this, DriverNC.getString(R.string.agreed_checkbox));
        });
        storeDataView.setOnClickListener(View -> startActivity(new Intent(DriverDevicePermissionActivityDriver.this, DriverStoreAndSecureActivityDriver.class)));
        setSpannableTextView(findViewById(R.id.spannable_txt));
    }

    private String getColoredSpanned(String text, int color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    private void CheckPermissions(ArrayList<String> permissionsList) {

        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < permissionsList.size(); i++) {
                if (ContextCompat.checkSelfPermission(DriverDevicePermissionActivityDriver.this, permissionsList.get(i)) != PackageManager.PERMISSION_GRANTED) {
                    isEnable = false;
                    break;
                } else isEnable = true;
            }
        } else isEnable = true;

        if (!isEnable) makeRequest(permissionsList);
        else moveToLoginScreen();
    }

    private void makeRequest(ArrayList<String> permissionsList) {
        String[] permissionStringList = new String[permissionsList.size()];

        for (int i = 0; i < permissionsList.size(); i++) {
            permissionStringList[i] = permissionsList.get(i);
        }

        if (permissionStringList.length > 0)
            ActivityCompat.requestPermissions(DriverDevicePermissionActivityDriver.this, permissionStringList, COMMON_REQUEST_CODE);
        else moveToLoginScreen();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        if (requestCode == COMMON_REQUEST_CODE) {
            if (ContextCompat.checkSelfPermission(DriverDevicePermissionActivityDriver.this, ACCESS_COARSE_LOCATION_PERMISSION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(DriverDevicePermissionActivityDriver.this, ACCESS_FINE_LOCATION_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                if (permissionsList != null) {

                    if (permissionsList.size() > 0) {
                        permissionsList.clear();
                        permissionsList.add(ACCESS_FINE_LOCATION_PERMISSION);
                        permissionsList.add(ACCESS_COARSE_LOCATION_PERMISSION);
                        permissionsList.add(CALL_PHONE);
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(DriverDevicePermissionActivityDriver.this, ACCESS_FINE_LOCATION_PERMISSION) || ActivityCompat.shouldShowRequestPermissionRationale(DriverDevicePermissionActivityDriver.this, ACCESS_COARSE_LOCATION_PERMISSION);
                        if (!showRationale) {
                            userAlertView(FROM_RATIONALE);

                        } else {
                            userAlertView(NORMAL);
                        }
                    }
                }
            } else {
                moveToLoginScreen();
            }
        }
    }

    private void moveToLoginScreen() {
        DriverSessionSave.saveSession("user_privacy_policy", "true", DriverDevicePermissionActivityDriver.this);
        Intent i = new Intent(DriverDevicePermissionActivityDriver.this, DriverUserLoginAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void userAlertView(int checkRationaleOrNormal) {
//        Driver_Utils.alert_view_dialog(DriverDevicePermissionActivityDriver.this, checkRationaleOrNormal == 2 ? DriverNC.getString(R.string.location_permission_denied) : DriverNC.getString(R.string.location_permission_required), checkRationaleOrNormal == 1 ? DriverNC.getString(R.string.above_permission_settings) : DriverNC.getString(R.string.deny_permission), checkRationaleOrNormal == 2 ? DriverNC.getString(R.string.retry) : DriverNC.getString(R.string.edit_permissions), checkRationaleOrNormal == 2 ? DriverNC.getString(R.string.privacy_exit_app) : DriverNC.getString(R.string.exit_anyway), true, (dialog, i) -> {
//            if (checkRationaleOrNormal == 2) {
//                CheckPermissions(permissionsList);
//                dialog.dismiss();
//            } else {
//                dialog.dismiss();
//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
//                intent.setData(uri);
//                startActivity(intent);
//
//            }
//
//        }, (dialog2, i) -> {
//            dialog2.dismiss();
//            finish();
//
//        }, "");
    }

    private void setSpannableTextView(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(DriverNC.getString(R.string.by_clicking_proceed) + " ");
        spanTxt.setSpan(new ForegroundColorSpan(DriverCL.getColor(R.color.quantum_grey500)), spanTxt.length() - DriverNC.getString(R.string.by_clicking_proceed).length() - 1, spanTxt.length(), 0);
        spanTxt.append(DriverNC.getString(R.string.terms_condition2));
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(DriverCL.getColor(R.color.button_accept));    // you can use custom color
                ds.setUnderlineText(true);
            }

            @Override
            public void onClick(View widget) {
                String url = "&type=dynamic_page&pagename=10&device_type=1";
                new ShowWebpage(url, null, "T");
            }
        }, spanTxt.length() - DriverNC.getString(R.string.terms_condition2).length(), spanTxt.length(), 0);
        spanTxt.append(" ").append(DriverNC.getString(R.string.and));
        spanTxt.setSpan(new ForegroundColorSpan(DriverCL.getColor(R.color.quantum_grey500)), spanTxt.length() - DriverNC.getString(R.string.and).length(), spanTxt.length(), 0);
        spanTxt.append(" ").append(DriverNC.getString(R.string.privacy_policy));

        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(DriverCL.getColor(R.color.button_accept));    // you can use custom color
                ds.setUnderlineText(true);
            }

            @Override
            public void onClick(View widget) {
                String url = "&type=dynamic_page&pagename=11&device_type=1";
                new ShowWebpage(url, null, "P");
            }
        }, spanTxt.length() - DriverNC.getString(R.string.privacy_policy).length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    private class ShowWebpage implements DriverAPIResult {
        String type = "T";

        public ShowWebpage(final String string, JSONObject data, String type) {
            // TODO Auto-generated constructor stub
            this.type = type;
            String ss = DriverSessionSave.getSession("base_url", DriverDevicePermissionActivityDriver.this) + "?" + "lang=" + DriverSessionSave.getSession("Lang", DriverDevicePermissionActivityDriver.this) + string;
            DriverSystems.out.println("weburl____" + ss);
            new DriverAPIService_Retrofit_JSON(DriverDevicePermissionActivityDriver.this, this, true, ss).execute();
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            try {
                if (isSuccess) {
                    final Intent intent = new Intent(DriverDevicePermissionActivityDriver.this, DriverTermsAndConditions.class);
                    final Bundle bundle = new Bundle();
                    intent.putExtra("content", result);
                    if (type.equals("T"))
                        bundle.putString("name", DriverNC.getString(R.string.terms_condition2));
                    else bundle.putString("name", DriverNC.getString(R.string.privacy_policy));
                    bundle.putBoolean("status", true);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    runOnUiThread(() -> ShowToast(DriverDevicePermissionActivityDriver.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}
