package com.wp.linechartpaint.utils;

import android.app.Activity;
import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author yangzheng
 * @time 2017/10/20 16:08
 * @description
 */
public class DateTimeUtil {
    public final static int DAY_TYPE = 10;
    public final static int WEEK_TYPE = 11;
    public final static int MONTH_TYPE = 12;

    public static String formatTime(long timeMillis, String pattern) {
        Date date = new Date(timeMillis);
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static long getMinTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getMaxTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前时间
     * */
    public static String getCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(new Date());
    }

    /**
     * 获取当前日期
     * */
    public static String getCurrentDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(new Date());
    }

    public static Date format(String dateType, String dateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateType);
        return format.parse(dateStr);
    }

    /**
     * 获取指定日期的
     * @param dateType
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static long formatTime(String dateType, String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat(dateType);
        try {
            return format.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取两个时间的分钟差
     */
    public static int getMinuteDiff(String startTime, String endTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        format.setLenient(true);
        Date startDate = null;
        Date endDate = null;
        int minutes = 0;

        try {
            startDate = format.parse(startTime);
            endDate = format.parse(endTime);
            minutes = (int) Math.ceil((double) ((endDate.getTime() - startDate.getTime()) / 60000L));
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return minutes;
    }

    /**
     * 上一天 ，周，月的第一天
     *
     * @param dateString 当前日期
     * @param type       切换类型，是切换一天/一周/一月
     */
    public static String lastDate(int type, String dateString) {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = sFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (type == DAY_TYPE) {//日
            calendar.add(Calendar.DATE, -1);
        } else if (type == WEEK_TYPE) {//周
            calendar.add(Calendar.DATE, -7);
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                calendar.add(Calendar.DATE, -1);
            }
        } else if (type == MONTH_TYPE) {//月
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        }
        Log.e("last", sFormat.format(calendar.getTime()));
        return sFormat.format(calendar.getTime());
    }

    /**
     * 下一天 ，周，月的第一天
     *
     * @param dateString 当前日期
     * @param type       切换类型，是切换一天/一周/一月
     */
    public static String nextDate(int type, String dateString) {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = sFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (type == DAY_TYPE) {//日
            calendar.add(Calendar.DATE, 1);
        } else if (type == WEEK_TYPE) {//周
            calendar.add(Calendar.DATE, 7);
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                calendar.add(Calendar.DATE, -1);
            }
        } else if (type == MONTH_TYPE) {//月
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        }
        Log.e("next", sFormat.format(calendar.getTime()));
        return sFormat.format(calendar.getTime());
    }


    /**
     * 根据分钟数转化为小时加分钟
     */
    public static String switchTime(String minuteStr) {
        int minute = Integer.parseInt(minuteStr);
        if (minute > 60) {
            return minute / 60 + "时" + minute % 60 + "分";
        } else {
            return minute + "分";
        }
    }
    public static String switchTimeRange(Activity activity, String sleepScope) {
        String[] dateTimeArray = sleepScope.split("~");
        if (dateTimeArray.length > 1) {
            try {
                String startTime = TimeUtil.getUserZoneDateTimeString(dateTimeArray[0]).substring(11, 16);
                int startHour = Integer.parseInt(startTime.split(":")[0]);
                int startMinute = Integer.parseInt(startTime.split(":")[1]);
                if (startHour * 60 + startMinute < 12 * 60) {
                    startTime = startTime + "AM";
                } else {
                    startTime = startTime + "PM";
                }

                String endTime = TimeUtil.getUserZoneDateTimeString(dateTimeArray[1]).substring(11, 16);
                int endHour = Integer.parseInt(endTime.split(":")[0]);
                int endMinute = Integer.parseInt(endTime.split(":")[1]);
                if (endHour * 60 + endMinute < 12 * 60) {
                    endTime = endTime + "AM";
                } else {
                    endTime = endTime + "PM";
                }

                return startTime + "~" + endTime;
            } catch (ParseException e) {
                e.printStackTrace();
                return "--PM~--AM";
            }
        } else {
            return sleepScope;
        }
    }

    /**
     * 返回date1-dat2相差的秒数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int subSecond(Date date1, Date date2) {
        long d1 = date1.getTime();
        long d2 = date2.getTime();
        int sub = (int) ((d1 - d2) / 1000);
        return sub;
    }

    public static int getTodayLeftSeconds(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        int second = subSecond(cal.getTime(), new Date());
        return second;
    }
}
