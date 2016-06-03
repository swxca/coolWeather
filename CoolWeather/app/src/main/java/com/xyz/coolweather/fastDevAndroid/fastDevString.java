package com.xyz.coolweather.fastDevAndroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fastDevString {
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static byte[] StringToArrayByte(String str){
        if (str==null){
            return null;
        }
        return str.getBytes(UTF_8);
    }
    public static String ArrayByteToString(byte[] bytes){
        if (bytes==null){
            return null;
        }
        return new String(bytes, UTF_8);
    }

    static public boolean IsEmailValid(String EmailStr) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,10}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(EmailStr);
        return m.matches();
    }

    static public boolean IsPhoneValid(String PhoneStr) {
        String str = "^1[3|4|5|6|7|8|9][0-9]{1}[0-9]{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(PhoneStr);
        return m.matches();
    }

    static public String ByteToUnitString(long byteTransfer) {
        double transfer = (new Long(byteTransfer)).doubleValue();
        if (byteTransfer >= 1024L * 1024 * 1024 * 1024 || byteTransfer<=-1024L * 1024 * 1024 * 1024) {
            return GetTwoDecimal(transfer / (1024L * 1024 * 1024 * 1024)) + "TB";
        }else if (byteTransfer >= 1024L * 1024 * 1024|| byteTransfer<=-1024L * 1024 * 1024) {
            return GetTwoDecimal(transfer / (1024L * 1024 * 1024)) + "GB";
        }else if (byteTransfer >= 1024 * 1024|| byteTransfer<=-1024L * 1024) {
            return GetTwoDecimal(transfer / (1024 * 1024)) + "MB";
        }else if (byteTransfer >= 1024|| byteTransfer<=-1024L) {
            return GetTwoDecimal(transfer / 1024) + "KB";
        }else {
            return Double.toString(transfer) + "B";
        }
    }

// Data num
    static public String ByteToUnitString_num(long byteTransfer) {
        double transfer = (new Long(byteTransfer)).doubleValue();
        if (byteTransfer >= 1024L * 1024 * 1024 * 1024 || byteTransfer<=-1024L * 1024 * 1024 * 1024) {
            return GetTwoDecimal(transfer / (1024L * 1024 * 1024 * 1024));
        }else if (byteTransfer >= 1024L * 1024 * 1024|| byteTransfer<=-1024L * 1024 * 1024) {
            return GetTwoDecimal(transfer / (1024L * 1024 * 1024));
        }else if (byteTransfer >= 1024 * 1024|| byteTransfer<=-1024L * 1024) {
            return GetTwoDecimal(transfer / (1024 * 1024));
        }else if (byteTransfer >= 1024|| byteTransfer<=-1024L) {
            return GetTwoDecimal(transfer / 1024);
        }else {
            return Double.toString(transfer);
        }
    }

//Data unit
    static public String ByteToUnitString_unit(long byteTransfer) {
        if (byteTransfer >= 1024L * 1024 * 1024 * 1024 || byteTransfer<=-1024L * 1024 * 1024 * 1024) {
            return "TB";
        }else if (byteTransfer >= 1024L * 1024 * 1024|| byteTransfer<=-1024L * 1024 * 1024) {
            return "GB";
        }else if (byteTransfer >= 1024 * 1024|| byteTransfer<=-1024L * 1024) {
            return "MB";
        }else if (byteTransfer >= 1024|| byteTransfer<=-1024L) {
            return "KB";
        }else {
            return "B";
        }
    }
    static public String GetTwoDecimal (double input) {
        if (input == 0) {
            return "0";
        }
        BigDecimal b = new BigDecimal(input);
        Double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return Double.toString(d);
    }

//    static public boolean CheckInputEmpty(Context context, String inputStr, String name) {
//        if (inputStr.isEmpty()) {
//            SVProgressHUD.showInfoWithStatus(context, name + "不能为空");
//            return false;
//        }
//        return true;
//    }

    static public String IntToString(int input) {
        return Integer.toString(input);
    }

    public static String IonvertISToString(InputStream inputStream) {
        StringBuffer string = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                string.append(line + "\n");
            }
        } catch (IOException e) {
        }
        return string.toString();
    }

    public static boolean isNullOrEmpty( final List< ? > c ) {
        return c == null || c.isEmpty();
    }

    public static boolean IsEmpty (String str) {
        return !"".equals(str);
    }
}

