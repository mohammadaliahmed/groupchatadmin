package com.appsinventiv.corperstripadmin.Utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.widget.Toast;


import java.util.Calendar;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by AliAh on 29/03/2018.
 */

public class CommonUtils {

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    /*
    * Showing Toast on thread
    * */
    public static void showToast(final String msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @SuppressLint("WrongConstant")
            public void run() {
                Toast.makeText(ApplicationClass.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    * Formatting date from milli seconds
    * */
    public static String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "dd MMM ";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "" + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday ";
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("dd MMM , h:mm aa", smsTime).toString();
        }
    }
    public static String getDuration(long seconds) {
        seconds=(seconds/1000);
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%2d:%02d", m,s);
    }

    public static boolean isAppInForeground(Context context) {
        List<ActivityManager.RunningTaskInfo> task =
                ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
                        .getRunningTasks(1);
        if (task.isEmpty()) {
            // app is in background
            return false;
        }
        return task
                .get(0)
                .topActivity
                .getPackageName()
                .equalsIgnoreCase(context.getPackageName());
    }

}
