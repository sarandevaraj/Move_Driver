package com.taximobility.util;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taximobility.R;
import com.taximobility.driver.utils.DriverNC;
import com.taximobility.features.CToast;
import com.taximobility.interfaces.AlertListener;

public class Utility {
    private static AlertDialog alert;
    private static AlertDialog gpsalert;
    private static BottomSheetDialog mBottomSheetDialogcancel;

    public enum ValidateAction {
        NONE, isValueNULL, isValidPassword, isValidSalutation, isValidFirstname, isValidLastname, isValidCard, isValidExpiry, isValidMail, isValidConfirmPassword, isNullPromoCode, isValidCvv, isNullMonth, isNullYear, isNullCardname, isValidphone
    }

    /**
     * This is method to validate the field like Mail,Password,Name,Salutation etc and show the appropriate alert message.
     */
    public static boolean validations(ValidateAction VA, Context con, String stringToValidate) {
        String message = "";
        boolean result = false;
        switch (VA) {
            case isValueNULL:
                if (TextUtils.isEmpty(stringToValidate))
                    message = "" + DriverNC.getString(R.string.enter_the_mobile_number);
                else
                    result = true;
                break;
            case isValidPassword:
                if (TextUtils.isEmpty(stringToValidate))
                    message = "" + DriverNC.getString(R.string.enter_the_password);
                else if (stringToValidate.length() < 5)
                    message = "" + DriverNC.getString(R.string.password_min_character);
                else if (stringToValidate.length() > 32)
                    message = "" + DriverNC.getString(R.string.password_max_character);
                else
                    result = true;
                break;
            case isValidSalutation:
                if (TextUtils.isEmpty(stringToValidate) || stringToValidate == null)
                    message = "" + DriverNC.getString(R.string.please_select_your_salutation);
                else
                    result = true;
                break;
            case isValidFirstname:
                if (TextUtils.isEmpty(stringToValidate) || stringToValidate.length() < 3)
                    message = "" + DriverNC.getString(R.string.enter_the_first_name);
                else
                    result = true;
                break;
            case isValidLastname:
                if (TextUtils.isEmpty(stringToValidate))
                    message = "" + DriverNC.getString(R.string.enter_the_last_name);
                else
                    result = true;
                break;
            case isValidCard:
                if (TextUtils.isEmpty(stringToValidate))
                    message = "" + DriverNC.getString(R.string.enter_the_card_number);
                else if (stringToValidate.length() < 9 || stringToValidate.length() > 16)
                    message = "" + DriverNC.getString(R.string.enter_the_valid_card_number);
                else
                    result = true;
                break;
            case isValidExpiry:
                if (TextUtils.isEmpty(stringToValidate))
                    message = "" + DriverNC.getString(R.string.enter_the_expiry_date);
                else
                    result = true;
                break;
            case isValidMail:
                if (SessionSave.getSession(TaxiUtil.SKIP_PASSENGER_EMAIL, con, false)) {
                    if (TextUtils.isEmpty(stringToValidate))
                        result = true;
                    else if (!validMail(stringToValidate))
                        message = "" + DriverNC.getString(R.string.enter_the_valid_email);
                    else
                        result = true;
                } else {
                    if (TextUtils.isEmpty(stringToValidate))
                        message = "" + DriverNC.getString(R.string.enter_the_email);
                    else if (!validMail(stringToValidate))
                        message = "" + DriverNC.getString(R.string.enter_the_valid_email);
                    else
                        result = true;
                }
                break;
            case isValidConfirmPassword:
                if (TextUtils.isEmpty(stringToValidate))
                    message = "" + DriverNC.getString(R.string.enter_the_confirmation_password);
                else
                    result = true;
                break;
            case isNullPromoCode:
                if (TextUtils.isEmpty(stringToValidate))
                    message = "" + DriverNC.getString(R.string.reg_enterprcode);
                else
                    result = true;
                break;
            case isNullMonth:
                if (TextUtils.isEmpty(stringToValidate))
                    message = "" + DriverNC.getString(R.string.reg_expmonth);
                else
                    result = true;
                break;
            case isNullYear:
                if (TextUtils.isEmpty(stringToValidate))
                    message = "" + DriverNC.getString(R.string.reg_expyear);
                else
                    result = true;
                break;
            case isValidCvv:
                if (TextUtils.isEmpty(stringToValidate))
                    message = "" + DriverNC.getString(R.string.enter_the_valid_CVV);
                else
                    result = true;
                break;
            case isNullCardname:
                if (TextUtils.isEmpty(stringToValidate))
                    message = "" + DriverNC.getString(R.string.reg_entercardname);
                else
                    result = true;
                break;
            case isValidphone:
                if (TextUtils.isEmpty(stringToValidate))
                    message = "" + DriverNC.getString(R.string.enter_the_confirmation_phoneno);
                else if (stringToValidate.length() < 6 || stringToValidate.length() > 15)
                    message = "" + DriverNC.getString(R.string.enter_the_confirmation_phoneno);
                else
                    result = true;
                break;


        }
        if (!message.equals("")) {
            CToast.ShowToast(con, message);
        }
        return result;
    }

    /**
     * This is method for check the mail is valid by the use of regex class.
     */
    private static boolean validMail(String string) {
        return isValidEmail(string);
    }

    private static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /**
     * Reveal animate the view
     *
     * @param viewRoot src view
     * @return @{@link Animator}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Animator animateRevealWithoutColorFromCoordinates(ViewGroup viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        anim.setDuration(viewRoot.getResources().getInteger(R.integer.anim_duration_long_medium));
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        return anim;
    }

    public static AlertDialog alert_view_dialog(final Activity mContext, String title, String message,
                                                String success_txt, String failure_txt,
                                                Boolean cancelable_val, final DialogInterface.OnClickListener postive_dialogInterface, final DialogInterface.OnClickListener negative_dialogInterface, final String s) {
        if (mContext != null) {
            Systems.out.println("updateGetPassUpdate*****3");
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext,R.style.DialogSlideAnim);
            dialog.setCancelable(cancelable_val);
            dialog.setMessage(message);
            dialog.setPositiveButton(success_txt, postive_dialogInterface)
                    .setNegativeButton(failure_txt, negative_dialogInterface);
            if (alert != null && alert.isShowing())
                alert.dismiss();
            alert = dialog.create();


            alert.setOnShowListener(arg0 -> {
                if (alert != null) {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(CL.getColor(mContext, R.color.button_accept));
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(CL.getColor(mContext, R.color.black));
                }
            });
            alert.show();
        }
        return alert;
    }

//    public static AlertDialog alert_view(final Activity mContext, String title, String message,
//                                         String success_txt, String failure_txt,
//                                         Boolean cancelable_val, final ClickInterface dialogInterface, final String s) {
//        if (mContext != null) {
//            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext,R.style.DialogSlideAnim);
//            dialog.setCancelable(cancelable_val);
//            dialog.setMessage(message);
//            dialog.setPositiveButton(success_txt, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int id) {
//                    dialogInterface.positiveButtonClick(dialog, id, s);
//                }
//            })
//                    .setNegativeButton(failure_txt, (dialog1, id) -> dialogInterface.negativeButtonClick(dialog1, id, s));
//
//            if (alert != null && alert.isShowing())
//                alert.dismiss();
//            alert = dialog.create();
//            alert.setOnShowListener(arg0 -> {
//                if (mContext != null && alert != null) {
//                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.button_accept));
//                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.black));
//                }
//            });
//            alert.show();
//
//        }
//        return alert;
//    }


    public static void gps_dialog(final Context mContext, String title, String message,
                                  String success_txt, String failure_txt,
                                  Boolean cancelable_val, final DialogInterface.OnClickListener postive_dialogInterface, final DialogInterface.OnClickListener negative_dialogInterface, final String s) {
        if (mContext != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setCancelable(cancelable_val);
            dialog.setMessage(message);
            dialog.setPositiveButton(success_txt, postive_dialogInterface)
                    .setNegativeButton(failure_txt, negative_dialogInterface);

            gpsalert = dialog.create();

            gpsalert.show();
            gpsalert.setOnShowListener(arg0 -> {
                if (gpsalert != null) {
                    gpsalert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(CL.getColor(mContext, R.color.button_accept));
                    gpsalert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(CL.getColor(mContext, R.color.black));
                }
            });
        }

    }

    public static void closeDialog(Dialog alert) {
        Systems.out.println("Utility---closeDialog");
        try {
            if (alert != null && alert.isShowing()) {
                alert.dismiss();
                alert = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeDialog(String type) {
        if (type.equals("gps")) {
            try {
                if (gpsalert != null && gpsalert.isShowing()) {
                    gpsalert.dismiss();
                    gpsalert = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void closeactionsheet(){
        if(mBottomSheetDialogcancel.isShowing()){
            mBottomSheetDialogcancel.dismiss();
            mBottomSheetDialogcancel.cancel();
        }

    }
    public static void actionSheet(final Activity mContext, String title,
                                   String success_txt, String failure_txt,
                                   Boolean cancelable, AlertListener listener) {

        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mContext);
        View sheetView = mContext.getLayoutInflater().inflate(R.layout.alert_sheet, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
//        Colorchange.ChangeColor((ViewGroup) sheetView, mContext);
        FontHelper.applyFont(mContext, sheetView.findViewById(R.id.rootlay));
        final TextView titleTxt = sheetView.findViewById(R.id.title_txt);
        final Button cancelBtn = sheetView.findViewById(R.id.cancel_btn);
        if(!failure_txt.equals("")){
            cancelBtn.setText(failure_txt);
        }
        final Button submitBtn = sheetView.findViewById(R.id.submit_btn);
        submitBtn.setText(success_txt);

        titleTxt.setText(title);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSuccess();
                mBottomSheetDialog.cancel();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.cancel();
                listener.onFailure();
            }
        });

    }

    public static void actionSheetCancel(final Activity mContext, String title,
                                   String success_txt, String failure_txt,
                                   Boolean cancelable, AlertListener listener) {


        mBottomSheetDialogcancel = new BottomSheetDialog(mContext);
        View sheetView = mContext.getLayoutInflater().inflate(R.layout.alert_sheet, null);
        mBottomSheetDialogcancel.setContentView(sheetView);
        mBottomSheetDialogcancel.setCancelable(false);
        mBottomSheetDialogcancel.show();
//        Colorchange.ChangeColor((ViewGroup) sheetView, mContext);
        FontHelper.applyFont(mContext, sheetView.findViewById(R.id.rootlay));
        final TextView titleTxt = sheetView.findViewById(R.id.title_txt);
        final Button cancelBtn = sheetView.findViewById(R.id.cancel_btn);
        cancelBtn.setText(failure_txt);
        final Button submitBtn = sheetView.findViewById(R.id.submit_btn);
        submitBtn.setText(success_txt);
        if(failure_txt.equals("")){
            cancelBtn.setVisibility(View.INVISIBLE);
        }
        titleTxt.setText(title);





        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSuccess();
                mBottomSheetDialogcancel.cancel();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialogcancel.cancel();
                listener.onFailure();
            }
        });

    }

}
