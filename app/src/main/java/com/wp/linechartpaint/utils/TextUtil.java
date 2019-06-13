package com.wp.linechartpaint.utils;

/**
 * <p>描述：(这里用一句话描述这个类的作用)</p>
 * 作者： wp<br>
 * 日期： 2019/6/6 15:32<br>
 * 版本： v2.0<br>
 */

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class TextUtil {
    public static final SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat dwf = new SimpleDateFormat("dd/MM E");
    public static final SimpleDateFormat yMf = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat dta = new SimpleDateFormat("yyyy-MM-dd h:mm a");
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat dfCN = new SimpleDateFormat("yyyy年MM月dd日");
    public static final DecimalFormat nf = new DecimalFormat("0.0");

    public TextUtil() {
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }

    public static boolean isTextEmpty(String text) {
        return text == null || "".equals(text) || "null".equalsIgnoreCase(text);
    }
}
