package com.xyz.coolweather.fastDevAndroid;

import android.app.Application;
import android.content.Context;

public class fastDevContext {
    private static Context gContext;
    // 不能拿去跳转页面
    public static Context GetAppContext(){
        if (gContext==null) {
            try {
                // TODO(hyangah): check proguard rule.
                Application appl = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
                gContext = appl.getApplicationContext();
            } catch (Exception e) {
                fastDevLog.Log("error","Global context not found: " + e);
            }
        }
        return gContext;
    }
    /*
    public static void SetAppContext(Context context){
        gContext = context;
    }

    private static Activity gActivity;
    public static Activity GetDefaultActivity() {
        return gActivity;
    }
    public static void SetDefaultActivity(Activity activity){
        gActivity = activity;
    }
*/
}
