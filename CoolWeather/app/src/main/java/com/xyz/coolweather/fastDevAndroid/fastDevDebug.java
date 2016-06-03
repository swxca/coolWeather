package com.xyz.coolweather.fastDevAndroid;

import java.io.PrintWriter;
import java.io.StringWriter;

public class fastDevDebug {
    public static String ThrowableStackToString(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
