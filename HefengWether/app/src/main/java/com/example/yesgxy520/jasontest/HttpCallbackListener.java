package com.example.yesgxy520.jasontest;

/**
 * Created by yesgxy520 on 6/1/2016.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
