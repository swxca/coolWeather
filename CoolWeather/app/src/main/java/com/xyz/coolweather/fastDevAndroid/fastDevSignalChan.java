package com.xyz.coolweather.fastDevAndroid;

import java.util.concurrent.Semaphore;

public class fastDevSignalChan {
    public static fastDevSignalChan New(){
        fastDevSignalChan chan = new fastDevSignalChan();
        chan.semaphore = new Semaphore(0,false);
        return chan;
    }

    private Semaphore semaphore;

    public void send(){
        semaphore.release();
    }
    public void recv(){
        semaphore.acquireUninterruptibly();
    }
}
