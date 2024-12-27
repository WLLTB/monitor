package com.wlltb.monitor.utils;

import java.text.DecimalFormat;

public class MonitorUtils {
    public static final DecimalFormat RATE_DECIMAL_FORMAT = new DecimalFormat("0.00%");

    public static String formatByte(long byteCount) {
        if (byteCount < 1024) return byteCount + " B";
        int exp = (int) (Math.log(byteCount) / Math.log(1024));
        char prefix = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sB", byteCount / Math.pow(1024, exp), prefix);
    }
}