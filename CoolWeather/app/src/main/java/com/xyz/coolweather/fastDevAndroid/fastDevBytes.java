package com.xyz.coolweather.fastDevAndroid;

import java.util.Arrays;

public class fastDevBytes {
    public static byte[] Slice(byte[] in,int start,int end){
        return Arrays.copyOfRange(in, start, end);
    }
}
