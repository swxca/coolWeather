package com.xyz.coolweather.fastDevAndroid;

import android.content.SharedPreferences;

public class fastDevKvdb {
    private final static Object locker = new Object();
    public static <T> T Get(String key,Class<T> type){
        synchronized (locker) {
            try {
                SharedPreferences sharedPreferences = fastDevContext.GetAppContext().getSharedPreferences(key, 0);
                String cache_userInfo = sharedPreferences.getString("fastDevKvdb", "");
                return fastDevJson.UnmarshalFromString(cache_userInfo, type);
            } catch (Exception e) {
                return null;
            }
        }
    }
    public static void Set(String key,Object obj){
        synchronized (locker) {
            String jsonData =  fastDevJson.MarshalToString(obj);
            SharedPreferences cache_userInfo = fastDevContext.GetAppContext().getSharedPreferences(key, 0);
            SharedPreferences.Editor editor = cache_userInfo.edit();
            editor.putString("fastDevKvdb", jsonData);
            editor.commit();
        }
    }
}
