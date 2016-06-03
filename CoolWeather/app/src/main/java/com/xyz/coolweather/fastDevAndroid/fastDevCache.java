package com.xyz.coolweather.fastDevAndroid;

import android.content.Context;
import android.content.SharedPreferences;

public class fastDevCache {
    /*
    *   fileName : 本地缓存文件名
    *   cacheName : key
    */
    static public void SetLocalCache(Context context, String fileName, String cacheName, Object data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(cacheName, data.toString());
        editor.commit();
    }

    static public String GetLocalCache(Context context, String fileName, String cacheName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, 0);
        return sharedPreferences.getString(cacheName, "");
    }

    static public Long GetLocalCacheLong(Context context, String fileName, String cacheName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, 0);
        return sharedPreferences.getLong(cacheName, Long.valueOf("0"));
    }
}
