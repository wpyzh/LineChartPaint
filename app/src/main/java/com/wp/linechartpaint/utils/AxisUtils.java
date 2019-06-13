package com.wp.linechartpaint.utils;

/**
 * <p>描述：(这里用一句话描述这个类的作用)</p>
 * 作者： wp<br>
 * 日期： 2019/6/6 15:29<br>
 * 版本： v2.0<br>
 */

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AxisUtils {
    public AxisUtils() {
    }

    public static String getStartTime(String string) {
        String[] times = string.split(":");
        return times[0] + ":00:00";
    }

    public static String getEndTime(String startTime, String endTime) {
        String start = getStartTime(startTime);
        int minute = getMinute(start, endTime);
        int hour = minute / 60 + (minute % 60 == 0 ? 0 : 1);
        if (hour > 8) {
            hour += hour % 2 == 0 ? 0 : 1;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;

        try {
            startDate = format.parse(start);
            long time = startDate.getTime() + (long)(hour * 60 * 60 * 1000);
            startDate.setTime(time);
        } catch (ParseException var9) {
            var9.printStackTrace();
        }

        String end = format.format(startDate);
        Log.e("end ", end);
        return end;
    }

    public static String getEndTime(String startTime, String endTime, int maxPiont) {
        if (maxPiont == 0) {
            maxPiont = 6;
        }

        String start = getStartTime(startTime);
        int minute = getMinute(start, endTime);
        if (minute < 60) {
            minute = 60;
        }

        int hour = minute / 60 + (minute % 60 == 0 ? 0 : 1);
        int space = (int)Math.ceil((double)hour * 1.0D / (double)maxPiont);
        if (hour > maxPiont) {
            hour += hour % space == 0 ? 0 : space - hour % space;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;

        try {
            startDate = format.parse(start);
            long time = startDate.getTime() + (long)(hour * 60 * 60 * 1000);
            startDate.setTime(time);
        } catch (ParseException var11) {
            var11.printStackTrace();
        }

        String end = format.format(startDate);
        Log.e("end ", end);
        return end;
    }

    public static String[] getAxixPiont(String startTime, String endTime) {
        String start = getStartTime(startTime);
        int minute = getMinute(start, endTime);
        int hour = minute / 60 + (minute % 60 == 0 ? 0 : 1);
        if (hour > 8) {
            hour += hour % 2 == 0 ? 0 : 1;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        String[] axis = null;

        try {
            int i;
            long time;
            String timeString;
            if (hour > 8) {
                axis = new String[hour / 2 + 1];

                for(i = 0; i <= hour / 2; ++i) {
                    startDate = format.parse(start);
                    time = startDate.getTime() + (long)(i * 2 * 60 * 60 * 1000);
                    startDate.setTime(time);
                    timeString = format.format(startDate).trim().split(" ")[1];
                    axis[i] = timeString.split(":")[0] + ":" + timeString.split(":")[1];
                    Log.e("aaa", axis[i]);
                }
            } else {
                axis = new String[hour + 1];

                for(i = 0; i <= hour; ++i) {
                    startDate = format.parse(start);
                    time = startDate.getTime() + (long)(i * 60 * 60 * 1000);
                    startDate.setTime(time);
                    timeString = format.format(startDate).trim().split(" ")[1];
                    axis[i] = timeString.split(":")[0] + ":" + timeString.split(":")[1];
                    Log.e("aaa", axis[i]);
                }
            }
        } catch (ParseException var12) {
            var12.printStackTrace();
        }

        return axis;
    }

    public static String[] getAxixPiont(String startTime, String endTime, int maxPiont) {
        if (maxPiont == 0) {
            maxPiont = 6;
        }

        String start = getStartTime(startTime);
        int minute = getMinute(start, endTime);
        if (minute < 60) {
            minute = 60;
        }

        int hour = minute / 60 + (minute % 60 == 0 ? 0 : 1);
        int space = (int)Math.ceil((double)hour * 1.0D / (double)maxPiont);
        if (hour > maxPiont) {
            hour += hour % space == 0 ? 0 : space - hour % space;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        String[] axis = null;

        try {
            int i;
            long time;
            String timeString;
            if (hour > maxPiont) {
                axis = new String[hour / space + 1];

                for(i = 0; i <= hour / space; ++i) {
                    startDate = format.parse(start);
                    time = startDate.getTime() + (long)(i * space * 60 * 60 * 1000);
                    startDate.setTime(time);
                    timeString = format.format(startDate).trim().split(" ")[1];
                    axis[i] = timeString.split(":")[0] + ":" + timeString.split(":")[1];
                    Log.e("aaa", axis[i]);
                }
            } else {
                axis = new String[hour + 1];

                for(i = 0; i <= hour; ++i) {
                    startDate = format.parse(start);
                    time = startDate.getTime() + (long)(i * 60 * 60 * 1000);
                    startDate.setTime(time);
                    timeString = format.format(startDate).trim().split(" ")[1];
                    axis[i] = timeString.split(":")[0] + ":" + timeString.split(":")[1];
                    Log.e("aaa", axis[i]);
                }
            }
        } catch (ParseException var14) {
            var14.printStackTrace();
        }

        return axis;
    }

    public static String[] getAxis(String startTime, String endTime, int timescale) {
        int minute = getMinute(startTime, endTime);
        int timeScaleNum = minute / timescale + 1;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        String[] axis = new String[timeScaleNum];

        try {
            startDate = format.parse(startTime);
        } catch (ParseException var12) {
            var12.printStackTrace();
        }

        axis[0] = startTime.split(" ")[1].split(":")[0] + ":" + startTime.split(" ")[1].split(":")[1];

        for(int i = 1; i < timeScaleNum; ++i) {
            long time = startDate.getTime() + (long)(timescale * 60 * 1000);
            startDate.setTime(time);
            String timeString = format.format(startDate).trim().split(" ")[1];
            axis[i] = timeString.split(":")[0] + ":" + timeString.split(":")[1];
            Log.e("i = ", i + "  " + axis[i]);
        }

        return axis;
    }

    public static int getPiontSpace(String startTime, String endTime, int maxPiont) {
        if (maxPiont == 0) {
            maxPiont = 6;
        }

        String start = getStartTime(startTime);
        int minute = getMinute(start, endTime);
        if (minute < 60) {
            minute = 60;
        }

        int hour = minute / 60 + (minute % 60 == 0 ? 0 : 1);
        int space = (int)Math.ceil((double)hour * 1.0D / (double)maxPiont);
        return space;
    }

    public static String[] getAxixPiont2(String startTime, String endTime, int maxPiont) throws ParseException {
        if (maxPiont <= 0) {
            maxPiont = 6;
        }

        int minute = getMinute(startTime, endTime);
        if (minute < maxPiont) {
            return new String[]{startTime, endTime};
        } else {
            int perMinute = (int)Math.ceil((double)minute / 10.0D / (double)maxPiont) * 10;
            String[] xAixs = new String[maxPiont + 1];
            xAixs[0] = startTime.substring(0, 15) + "0:00";
            long startMillis = TimeUtil.parseMillis(startTime.substring(0, 15) + "0:00", "yyyy-MM-dd HH:mm:ss");

            for(int i = 1; i <= maxPiont; ++i) {
                long nextMillis = TimeUtil.modMinute(startMillis, i * perMinute);
                xAixs[i] = TimeUtil.format(nextMillis, "yyyy-MM-dd HH:mm:ss");
            }

            return xAixs;
        }
    }

    public static int getMinute(String startTime, String endTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(true);
        Date startDate = null;
        Date endDate = null;
        int minutes = 0;

        try {
            startDate = format.parse(startTime);
            endDate = format.parse(endTime);
            minutes = (int)Math.ceil((double)((endDate.getTime() - startDate.getTime()) / 60000L));
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return minutes;
    }

    public static int getSecond(String startTime, String endTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(true);
        Date startDate = null;
        Date endDate = null;
        int minutes = 0;

        try {
            startDate = format.parse(startTime);
            endDate = format.parse(endTime);
            minutes = (int)Math.ceil((double)((endDate.getTime() - startDate.getTime()) / 1000L));
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return minutes;
    }
}

