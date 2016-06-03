package com.xyz.coolweather.fastDevAndroid;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by zhangtao on 16/4/28.
 */
public class fastDevScreen {
    //初始化DisplayMetrics,这个类里有屏幕的参数信息
    public static DisplayMetrics initDisplayMetrics(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
    //获得屏幕的密度
    public static float getScreenDensity(Activity activity) {
        return initDisplayMetrics(activity).density;
    }
    //获得屏幕的高度单位是像素
    public static float getScreenHeightPixels(Activity activity) {
        return initDisplayMetrics(activity).heightPixels;
    }
    //获得屏幕的宽度单位是像素
    public static float getScreenWidthPixels(Activity activity) {
        return initDisplayMetrics(activity).widthPixels;
    }
    //获得屏幕的宽度单位是dp
    public static int getScreenDPWidth(Activity activity) {
        return (int) (getScreenWidthPixels(activity) / getScreenDensity(activity));
    }
    //获得屏幕的高度单位是dp
    public static int getScreenDPHeight(Activity activity) {
        return (int) (getScreenHeightPixels(activity) / getScreenDensity(activity));
    }
    //传一个像素值可以转化为dp值
    public static int pxChangedp(Activity activity, int px) {
        return (int) (px / getScreenDensity(activity));
    }
    //传一个dp值可以转化为像素值
    public static int dpChangepx(Activity activity, int dp) {
        return (int) (dp * getScreenDensity(activity));
    }
}
