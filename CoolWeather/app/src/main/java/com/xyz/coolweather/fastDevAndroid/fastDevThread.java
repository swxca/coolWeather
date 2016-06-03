package com.xyz.coolweather.fastDevAndroid;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class fastDevThread {
    public static void RunOnMainThread(Runnable f){
        if (IsMainThread()){
            f.run();
            return;
        }
        new Handler(Looper.getMainLooper()).post(f);
    }

    public static void RunOnAsyncThread(Runnable f){
        new Thread(f).start();
    }

    public static boolean IsMainThread(){
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public static Repeater RunRepeat(final int millisecond,final Runnable f){
        final Repeater ret = new Repeater();
        RunOnAsyncThread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if (ret.isClose){
                        return;
                    }
                    f.run();
                    try {
                        Thread.sleep(millisecond);
                    }catch(Exception e){
                        Log.i("fastDevThread", "RunRepeat error " + e.getMessage());
                        return;
                    }
                }
            }
        });
        return ret;
    }

    public static class Repeater{
        private Repeater(){}
        private boolean isClose = false;
        public void Stop(){
            isClose = true;
        }
    }
}
