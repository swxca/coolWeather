package com.xyz.coolweather.fastDevAndroid;


import java.text.SimpleDateFormat;
import java.util.Date;

public class fastDevDate {
    static public String DateFormatAll = "yyyy-MM-dd HH:mm:ss ZZZZZ";
    static public String DateFormatDefault = "yyyy-MM-dd HH:mm:ss";
    static public String DateFormatYMD = "yyyy-MM-dd";
    static public String DateFormatMD  = "MM-dd";

    static public String DateFormat(Date time ,String type) {
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(time);
    }

    static public long DateToMillionSecond(Date time) {
        return time.getTime();
    }

    static public boolean IsTimeEmpty(Date date) {
        Date now = new Date();
        if (date.after(now)) {
            return true;
        }
        return false;
    }

    static public boolean IsTimeZero(Date date) {
        if (fastDevDate.DateFormat(date, DateFormatYMD).equals("0001-01-01")) {
            return true;
        }
        return false;
    }
    static public long NowMillionSecond(){
        return System.currentTimeMillis();
    }
    static public double SinceMillionSecondToSecond(long t){
        return (double)(System.currentTimeMillis() - t)/1000.0;
    }
}

