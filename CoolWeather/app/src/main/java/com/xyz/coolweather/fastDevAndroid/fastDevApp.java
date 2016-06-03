package com.xyz.coolweather.fastDevAndroid;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by keyson on 16/1/16.
 */

public class fastDevApp {
    public static boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
