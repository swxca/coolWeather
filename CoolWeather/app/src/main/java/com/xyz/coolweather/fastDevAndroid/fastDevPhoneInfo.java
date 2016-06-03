package com.xyz.coolweather.fastDevAndroid;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class fastDevPhoneInfo {

    // 获取手机型号
    public static String GetPhoneModel() {
        return Build.MODEL;
    }

    // 获取手机号码
    public static String GetPhoneNumber() {
        TelephonyManager tm = (TelephonyManager) fastDevContext.GetAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    // 获取SDK版本号
    public static String GetSDKVersion() {
        return Build.VERSION.SDK;
    }

    // 手机系统版本号
    public static String GetSystemVersion() {
        return Build.VERSION.RELEASE;
    }
}
