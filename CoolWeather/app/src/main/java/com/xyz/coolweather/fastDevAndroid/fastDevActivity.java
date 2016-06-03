package com.xyz.coolweather.fastDevAndroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class fastDevActivity extends Activity {

    static public void JumpToByClass(Context fromActivity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(fromActivity, cls);
        fromActivity.startActivity(intent);
    }

    // 可以在任意线程调用,已解决主线程问题
    static public void JumpToByClassAndFinish(final Activity fromActivity, final Class<?> cls) {
        fastDevThread.RunOnMainThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(fromActivity, cls);
                fromActivity.startActivity(intent);
                fromActivity.finish();
            }
        });
    }

    public void JumpToByClassWithoutContext(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), cls);
        startActivity(intent);
    }
}