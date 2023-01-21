package com.taximobility.driver.utils;

import android.animation.Animator;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.taximobility.R;
import com.taximobility.driver.DriverBaseActivity;
import com.taximobility.driver.DriverSplashAct;
import com.taximobility.driver.data.DriverCommonData;
import com.taximobility.driver.interfaces.DriverClickInterface;

import static com.taximobility.driver.utils.DriverNC.getString;

/**
 * Created by developer on 26/2/18.
 */

public class Driver_Utils {
    private static AlertDialog alert;
    private static AlertDialog gpsAlert;

    /**
     * To check Lollipop verison sdk
     *
     * @return true equal and higher , false for lower 5.0
     */
    public static boolean HigherThanLollipop() {

        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
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

    /**
     * Reveal animate the view
     *
     * @param viewRoot src view
     * @param color    background color
     * @return @{@link Animator}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Animator animateRevealColorFromCoordinates(ViewGroup viewRoot, @ColorRes int color) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(viewRoot.getContext(), color));
        anim.setDuration(viewRoot.getResources().getInteger(R.integer.anim_duration_long_medium));
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        return anim;
    }


    public static AlertDialog alert_view(final Context mContext, String title, String message, String success_txt, String failure_txt, Boolean cancelable_val, final DriverClickInterface dialogInterface, final String s) {
        if (mContext != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
            dialog.setCancelable(cancelable_val);
            dialog.setMessage(message);
            dialog.setPositiveButton(success_txt, (dialog1, id) -> dialogInterface.positiveButtonClick(dialog1, id, s)).setNegativeButton(failure_txt, (dialog12, id) -> dialogInterface.negativeButtonClick(dialog12, id, s));

            if (alert != null && alert.isShowing()) alert.dismiss();
            alert = dialog.create();
            alert.setOnShowListener(arg0 -> {
                if (mContext != null && alert != null) {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(DriverCL.getColor(R.color.button_accept));
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(DriverCL.getColor(R.color.black));
                }
            });
            alert.show();

        }
        return alert;
    }

    public static void closeDialog(Dialog alert) {
        try {
            DriverSystems.out.println("closeDialogCalling");
            if (alert != null && alert.isShowing()) {
                alert.dismiss();
                alert = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeGPSDialog() {
        try {
            DriverSystems.out.println("closeDialogCalling");
            if (gpsAlert != null && gpsAlert.isShowing()) {
                gpsAlert.dismiss();
                gpsAlert = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Dialog alert_view_dialog(final Context mContext, String title, String message, String success_txt, String failure_txt, Boolean cancelable_val, final DialogInterface.OnClickListener postive_dialogInterface, final DialogInterface.OnClickListener negative_dialogInterface, final String s) {
        if (mContext != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
            dialog.setCancelable(cancelable_val);
            dialog.setMessage(message);
            dialog.setPositiveButton(success_txt, postive_dialogInterface).setNegativeButton(failure_txt, negative_dialogInterface);
            if (alert != null && alert.isShowing()) alert.dismiss();
            alert = dialog.create();
            alert.setOnShowListener(arg0 -> {
                if (mContext != null && alert != null) {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(DriverCL.getColor(R.color.button_accept));
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(DriverCL.getColor(R.color.black));
                }
            });
            alert.show();

        }
        return alert;
    }

    public static void alert_view_dialog_GPS(final AppCompatActivity mContext, String title, String message, String success_txt, String failure_txt, Boolean cancelable_val, final DialogInterface.OnClickListener postive_dialogInterface, final DialogInterface.OnClickListener negative_dialogInterface, final String s) {
        if (mContext != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
            dialog.setCancelable(cancelable_val);
            dialog.setMessage(message);
            dialog.setPositiveButton(success_txt, postive_dialogInterface).setNegativeButton(failure_txt, negative_dialogInterface);

            gpsAlert = dialog.create();
            gpsAlert.setOnShowListener(arg0 -> {
                if (mContext != null && gpsAlert != null) {
                    gpsAlert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(DriverCL.getColor(R.color.button_accept));
                    gpsAlert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(DriverCL.getColor(R.color.black));
                }
            });
            gpsAlert.show();

        }
    }

    private final static int freeDriverIdleLimit = 3600000;//1hour
    private final static int busyIdleLimit = 900000;//15 mins
    private final static int activeDriverIdleLimit = 3600000;//1 hour
    private final static int alertInterval = 300000;//5 mins
    private final static int idleNotification = 201;
    private static long lastNotifiedTime = -1;

    public static Boolean checkInteraction(Context context) {
//        Systems.out.println("whattttttttttt" + Math.abs(BaseActivity.getLastInteractionTime() - System.currentTimeMillis()));
        if (DriverSessionSave.getSession("status", context).equals("F")) {
            if ((System.currentTimeMillis() - DriverBaseActivity.getLastInteractionTime()) > (freeDriverIdleLimit - alertInterval))
                generateNotifications(context, getString(R.string.idle_stop), DriverSplashAct.class, false, idleNotification);
            if (DriverSessionSave.getSession(DriverCommonData.ACTIVITY_BG, context).equals("1") && (System.currentTimeMillis() - DriverBaseActivity.getLastInteractionTime()) > (freeDriverIdleLimit))
//                return false;
                return true;
        } else if (DriverSessionSave.getSession("status", context).equals("B")) {
            if ((System.currentTimeMillis() - DriverBaseActivity.getLastInteractionTime()) > busyIdleLimit) {
                if (lastNotifiedTime != -1 && ((System.currentTimeMillis() - lastNotifiedTime) > busyIdleLimit))
                    generateNotifications(context, getString(R.string.trip_stop), DriverSplashAct.class, false, idleNotification);
            }
        } else if (DriverSessionSave.getSession("status", context).equals("A")) {
            if ((System.currentTimeMillis() - DriverBaseActivity.getLastInteractionTime()) > activeDriverIdleLimit) {
                if (lastNotifiedTime != -1 && ((System.currentTimeMillis() - lastNotifiedTime) > activeDriverIdleLimit))
                    generateNotifications(context, getString(R.string.trip_stop), DriverSplashAct.class, false, idleNotification);
            }
        }
        return true;
    }

    public static void generateNotifications(Context context, String message, Class<?> class1, boolean cancelable, int Notification_ID) {
        lastNotifiedTime = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String title = getString(R.string.app_name);
        Intent notificationIntent = new Intent(context, class1);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
        Notification myNotication;
        Notification.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(notificationChannel);

            builder = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID).setContentText(message).setContentTitle(title).setOngoing(true).setSmallIcon(getNotificationIcon()).setContentIntent(pendingIntent).setLargeIcon(((BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.driver_ic_launcher)).getBitmap()).setStyle(new Notification.BigTextStyle().bigText(message)).setWhen(System.currentTimeMillis());
        } else {
            builder = new Notification.Builder(context).setAutoCancel(true).setTicker(context.getResources().getString(R.string.common_name)).setContentTitle(title).setContentText(message).setContentIntent(pendingIntent).setOngoing(true).setSmallIcon(getNotificationIcon()).setStyle(new Notification.BigTextStyle().bigText(message)).setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.drawable.driver_ic_launcher)).getBitmap());

        }

        myNotication = builder.build();

        myNotication.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(Notification_ID, myNotication);
        Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        try {
            Ringtone r = RingtoneManager.getRingtone(context, notification1);
            r.play();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private static int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.driver_small_logo : R.drawable.driver_ic_launcher;
    }
}