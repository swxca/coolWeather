package com.xyz.coolweather.fastDevAndroid;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class fastDevTime {
    static public String DateFormatAll = "yyyy-MM-dd HH:mm:ss";
    static public String DateFormatYMD = "yyyy-MM-dd";
    static public String DateFormatMD  = "MM-dd";

    public static Date Now(){
        return new Date();
    }
    public static Date Zero(){
        return new Date(0);
    }
    // 返回目标时间到当前时间的秒的差距.
    public static int SinceSecond(Date date){
        if (date==null){
            return (int)(Now().getTime())/1000;
        }
        return (int)(Now().getTime()-date.getTime())/1000;
    }
    public static Date ParseGolangDate(String st) throws Exception {
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(st.substring(0, 4));
        int month = Integer.parseInt(st.substring(5, 7));
        int day = Integer.parseInt(st.substring(8, 10));
        int hour = Integer.parseInt(st.substring(11, 13));
        int minute = Integer.parseInt(st.substring(14, 16));
        int second = Integer.parseInt(st.substring(17, 19));
        float nonaSecond = 0;
        int tzStartPos = 19;
        if (st.charAt(19)=='.'){
            // 从19开始找到第一个不是数字的字符串.
            for (;;){
                tzStartPos++;
                if (st.length()<=tzStartPos){
                    throw new Exception("can not parse "+st);
                }
                char thisChar = st.charAt(tzStartPos);
                if (thisChar>='0' && thisChar<='9'){
                }else{
                    break;
                }
            }
            nonaSecond = Float.parseFloat("0." + st.substring(20, tzStartPos));
        }
        cal.set(Calendar.MILLISECOND,(int)(nonaSecond*1e3));
        char tzStart = st.charAt(tzStartPos);
        if (tzStart=='Z'){
            cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        }else {
            int tzHour = Integer.parseInt(st.substring(tzStartPos + 1, tzStartPos + 3));
            int tzMin = Integer.parseInt(st.substring(tzStartPos + 4, tzStartPos + 6));
            int tzOffset = tzHour*3600*1000 + tzMin * 60*1000;
            if (tzStart=='-'){
                tzOffset = - tzOffset;
            }
            TimeZone tz = new SimpleTimeZone(tzOffset,"");
            cal.setTimeZone(tz);
        }
        cal.set(year,month-1,day,hour,minute,second);
        return cal.getTime();
    }
    public static String FormatGolangDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        StringBuilder buf = new StringBuilder();
        formatYear(cal,buf);
        buf.append('-');
        formatMonth(cal, buf);
        buf.append('-');
        formatDays(cal, buf);
        buf.append('T');
        formatHours(cal, buf);
        buf.append(':');
        formatMinutes(cal, buf);
        buf.append(':');
        formatSeconds(cal,buf);
        formatTimeZone(cal, buf);
        return buf.toString();
    }
    private static void formatYear(Calendar cal, StringBuilder buf) {
        int year = cal.get(Calendar.YEAR);
        String s;
        if (year <= 0) // negative value
        {
            s = Integer.toString(1 - year);
        } else // positive value
        {
            s = Integer.toString(year);
        }
        while (s.length() < 4) {
            s = '0' + s;
        }
        if (year <= 0) {
            s = '-' + s;
        }
        buf.append(s);
    }
    private static void formatMonth(Calendar cal, StringBuilder buf) {
        formatTwoDigits(cal.get(Calendar.MONTH) + 1, buf);
    }
    private static void formatDays(Calendar cal, StringBuilder buf) {
        formatTwoDigits(cal.get(Calendar.DAY_OF_MONTH), buf);
    }
    private static void formatHours(Calendar cal, StringBuilder buf) {
        formatTwoDigits(cal.get(Calendar.HOUR_OF_DAY), buf);
    }
    private static void formatMinutes(Calendar cal, StringBuilder buf) {
        formatTwoDigits(cal.get(Calendar.MINUTE), buf);
    }
    private static void formatSeconds(Calendar cal, StringBuilder buf) {
        formatTwoDigits(cal.get(Calendar.SECOND), buf);
        if (cal.isSet(Calendar.MILLISECOND)) { // milliseconds
            int n = cal.get(Calendar.MILLISECOND);
            if (n != 0) {
                String ms = Integer.toString(n);
                while (ms.length() < 3) {
                    ms = '0' + ms; // left 0 paddings.
                }
                buf.append('.');
                buf.append(ms);
            }
        }
    }
    /** formats time zone specifier. */
    private static void formatTimeZone(Calendar cal, StringBuilder buf) {
        TimeZone tz = cal.getTimeZone();
        if (tz == null) {
            return;
        }
        // otherwise print out normally.
        int offset = tz.getOffset(cal.getTime().getTime());
        if (offset == 0) {
            buf.append('Z');
            return;
        }
        if (offset >= 0) {
            buf.append('+');
        } else {
            buf.append('-');
            offset *= -1;
        }
        offset /= 60 * 1000; // offset is in milli-seconds
        formatTwoDigits(offset / 60, buf);
        buf.append(':');
        formatTwoDigits(offset % 60, buf);
    }
    /** formats Integer into two-character-wide string. */
    private static void formatTwoDigits(int n, StringBuilder buf) {
        // n is always non-negative.
        if (n < 10) {
            buf.append('0');
        }
        buf.append(n);
    }

    public static String FormatMysqlDate(Date date){
        return fastDevTime.DateFormat(date, DateFormatYMD);
    }


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
        if (fastDevTime.DateFormat(date, DateFormatYMD).equals("0001-01-01")) {
            return true;
        }
        return false;
    }
}
