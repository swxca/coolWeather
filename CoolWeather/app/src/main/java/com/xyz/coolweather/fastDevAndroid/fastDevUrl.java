package com.xyz.coolweather.fastDevAndroid;

import android.net.Uri;

/**
 * Created by keyson on 16/3/5.
 */
public class fastDevUrl {

    public static String getUrlParameter(String url,String aimParameter){
        Uri uri = Uri.parse(url);
        return uri.getQueryParameter(aimParameter);
    }
}
