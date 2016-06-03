package com.xyz.coolweather.fastDevAndroid;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.io.StringWriter;


public class fastDevLog {
    public static void Log(String cat,Object... objs){
        defaultLogger.Log(cat,objs);
    }
    public static void e(String cat,Object... objs){
        defaultLogger.e(cat,objs);
    }
    public static void i(String cat,Object... objs){
        defaultLogger.i(cat,objs);
    }
    public static void d(String cat,Object... objs){
        defaultLogger.d(cat,objs);
    }
    public static void UseAndroidLog(){
        defaultLogger = new AndroidLogger();
    }
    private static Logger defaultLogger = new AndroidLogger();
    public interface Logger {
        void Log(String cat, Object... objs);
        void e(String cat, Object... objs);
        void i(String cat, Object... objs);
        void d(String cat, Object... objs);
    }
    public static void SetDefaultLogger(Logger logger){
        defaultLogger = logger;
    }
    private static class AndroidLogger implements Logger {
        public void Log(String cat,Object... objs){
            Log.w(cat, fastDevJson.MarshalToString(objs));
        }

        public void e(String cat,Object... objs){
            Log.e(cat, fastDevJson.MarshalToString(objs));
        }
        public void i(String cat,Object... objs){
            Log.i(cat, fastDevJson.MarshalToString(objs));
        }
        public void d(String cat,Object... objs){
            Log.d(cat, fastDevJson.MarshalToString(objs));
        }

    }

    public static byte[] logCatToByteArray() throws Exception {
        // For Android 4.0 and earlier, you will get all app's log output, so filter it to
        // mostly limit it to your app's output.  In later versions, the filtering isn't needed.
        //String cmd = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) ?
        //        "logcat -d -v time com.yxnfnd9.android_fcvpn1:v dalvikvm:v System.err:v *:s" :
        //        "logcat -d -v time";
        String cmd = "logcat -d -v threadtime";
        // get input stream
        Process process = Runtime.getRuntime().exec(cmd);
        return fastDevIo.InputStreamReadAtEndSize(process.getInputStream(),1024*1024);
    }

    public static String getDeviceInfoMsg(){
        StringWriter sw = new StringWriter();
        PackageManager manager = fastDevContext.GetAppContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo (fastDevContext.GetAppContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
        }
        String model = Build.MODEL;
        if (!model.startsWith(Build.MANUFACTURER)) {
            model = Build.MANUFACTURER + " " + model;
        }
        sw.write ("Android version: " +  Build.VERSION.SDK_INT + "\n");
        sw.write ("Device: " + model + "\n");
        sw.write ("App version: " + (info == null ? "(null)" : info.versionCode) + "\n");
        //sw.write(fastDevCmd.RunToString("netstat"));
        //sw.write(fastDevCmd.RunToString("netcfg"));
        //sw.write(fastDevCmd.RunToString("cat /proc/net/route"));
        //sw.write(fastDevCmd.RunToString("getprop"));

        return sw.toString();
    }
}
