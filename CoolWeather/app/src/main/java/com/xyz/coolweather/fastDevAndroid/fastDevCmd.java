package com.xyz.coolweather.fastDevAndroid;

import java.io.StringWriter;

public class fastDevCmd {
    // 输出命令行的返回结果,或者出现错误时返回""
    // 注意正常情况也会返回 ""
    // 会回显命令行
    public static String RunToString(String cmd){
        StringWriter sw = new StringWriter();
        sw.write(cmd);
        sw.write("\n");
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            sw.write(fastDevString.ArrayByteToString(fastDevIo.InputStreamReadAll(process.getInputStream())));
        }catch(Exception e){
            fastDevLog.Log("error","[fastDevCmd.RunToString]",e.getMessage());
            e.printStackTrace();
            sw.write(e.getMessage());
        }
        return sw.toString();
    }
}
