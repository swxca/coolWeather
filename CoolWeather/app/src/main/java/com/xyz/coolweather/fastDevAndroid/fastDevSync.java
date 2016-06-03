package com.xyz.coolweather.fastDevAndroid;

public class fastDevSync {
    public static class Once{
        private final Object locker = new Object();
        private boolean isInit = false;
        public void Do(Runnable f){
            synchronized (locker){
                if (isInit){
                    return;
                }
                f.run();
                isInit = true;
            }
        }
    }
}
