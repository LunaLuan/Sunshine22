package learnenglishhou.bluebirdaward.com.sunshine2.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.List;

import learnenglishhou.bluebirdaward.com.sunshine2.R;

/**
 * Created by SVTest on 18/08/2016.
 */

public class Utility {

    public static String getCityIdPreferences(Context context) {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        String input= prefs.getString(context.getString(R.string.pref_location_key), context.getString(R.string.city_id_default));
        return input;
    }

    public static String getMetric(Context context) {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        String input= prefs.getString(context.getString(R.string.pref_units_key), "Metric");
        return input;
    }

    public static int getTimeUpdate(Context context) {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        String input= prefs.getString("sync_frequency", "180");
        Log.d("input", input);
        return Integer.parseInt(input);
    }

    public static String getReadableDateString(long time){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE - dd - MM");
        return shortenedDateFormat.format(time);
    }

    public static boolean getAllowNotification(Context context) {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("notifications_new_message", true);
    }

    public static String getPackageNameApp(Context context) {
        String name= context.getString(R.string.content_authority);
        return name;
    }

    public static boolean isAppRunning(Context context) {
        ActivityManager am=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo= am.getRunningTasks(1);

        for(int i=0;i<taskInfo.size();i++) {
            ComponentName componentInfo = taskInfo.get(i).topActivity;
            if (componentInfo.getPackageName().equalsIgnoreCase(getPackageNameApp(context)))
                return true;
        }

        return false;
    }


}
