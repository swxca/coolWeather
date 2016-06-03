package com.xyz.coolweather.fastDevAndroid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zhangtao on 16/5/14.
 */
public class fastDevCheckNet {
    public static  boolean CheckNet(){
        ConnectivityManager cm = (ConnectivityManager) fastDevContext.GetAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()){
            return true;
        }else {
            return false;
        }
    }
}
