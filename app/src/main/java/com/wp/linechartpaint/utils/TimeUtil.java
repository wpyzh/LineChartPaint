package com.wp.linechartpaint.utils;

/**
 * <p>描述：(这里用一句话描述这个类的作用)</p>
 * 作者： wp<br>
 * 日期： 2019/6/6 15:30<br>
 * 版本： v2.0<br>
 */

import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class TimeUtil {
    private TimeUtil() {
    }

    public static int getTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        int lRawOffset = timeZone.getRawOffset() / 1000 / 60;
        return lRawOffset;
    }

    public static Time getTime(long lMillis) {
        Time time = new Time();
        time.set(lMillis);
        return time;
    }

    public static long getCurUtcMillis() {
        Time time = new Time();
        time.setToNow();
        long lCurMillis = time.toMillis(false);
        long lOffset = time.gmtoff * 1000L;
        long lUtcMillis = lCurMillis - lOffset;
        return lUtcMillis;
    }

    public static String getCurUtcString(String strInFmt) {
        if (StringUtil.isNull(strInFmt)) {
            throw new NullPointerException("参数strInFmt不能为空");
        } else {
            return format(getCurUtcMillis(), strInFmt);
        }
    }

    public static String getCurUtcDateTimeString() {
        return getCurUtcString("yyyy-MM-dd kk:mm:ss");
    }

    public static String getCurUtcDateString() {
        return getCurUtcString("yyyy-MM-dd");
    }

    public static String getYestadayUtcDateString() {
        String now = getCurUtcString("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(TextUtil.df.parse(now));
            calendar.add(5, -1);
        } catch (ParseException var3) {
            var3.printStackTrace();
        }

        return TextUtil.df.format(calendar.getTime());
    }

    public static String getCurUserZoneDay() {
        Time time = new Time();
        time.setToNow();
        return String.valueOf(time.monthDay);
    }

    public static String getCurUserZoneDateString() {
        return format(System.currentTimeMillis(), "yyyy-MM-dd");
    }

    public static String getCurUserZoneDateTimeString() {
        return format(System.currentTimeMillis(), "yyyy-MM-dd kk:mm:ss");
    }

    public static long getUserZoneMillis(String strUtcTime, String strInFmt) throws ParseException {
        if (StringUtil.isNull(strUtcTime)) {
            throw new NullPointerException("参数strUtcTime不能为空");
        } else if (StringUtil.isNull(strInFmt)) {
            throw new NullPointerException("参数strInFmt不能为空");
        } else {
            long lUtcMillis = parseMillis(strUtcTime, strInFmt);
            Time time = new Time();
            time.setToNow();
            long lOffset = time.gmtoff * 1000L;
            long lUserZoneMillis = lUtcMillis + lOffset;
            return lUserZoneMillis;
        }
    }

    public static String getUserZoneString(String strUtcTime, String strInFmt, String strOutFmt) throws ParseException {
        if (StringUtil.isNull(strUtcTime)) {
            throw new NullPointerException("参数strDate不能为空");
        } else if (StringUtil.isNull(strInFmt)) {
            throw new NullPointerException("参数strInFmt不能为空");
        } else {
            long lUserMillis = getUserZoneMillis(strUtcTime, strInFmt);
            String strFmt = strInFmt;
            if (!StringUtil.isNull(strOutFmt)) {
                strFmt = strOutFmt;
            }

            return format(lUserMillis, strFmt);
        }
    }

    public static String getUserZoneDateTimeString(String strUtcDateTime) throws ParseException {
        if (StringUtil.isNull(strUtcDateTime)) {
            throw new NullPointerException("参数strUtcDateTime不能为空");
        } else {
            return getUserZoneString(strUtcDateTime, "yyyy-MM-dd kk:mm:ss", (String)null);
        }
    }

    public static String getUserZoneDateString(String strUtcDate) throws ParseException {
        if (StringUtil.isNull(strUtcDate)) {
            throw new NullPointerException("参数strDateTime不能为空");
        } else {
            return getUserZoneString(strUtcDate, "yyyy-MM-dd", (String)null);
        }
    }

    public static long getUtcMillis(String strUserZoneTime, String strInFmt) throws ParseException {
        if (StringUtil.isNull(strUserZoneTime)) {
            throw new NullPointerException("参数strUserZoneTime不能为空");
        } else if (StringUtil.isNull(strInFmt)) {
            throw new NullPointerException("参数strInFmt不能为空");
        } else {
            long lUserZoneMillis = parseMillis(strUserZoneTime, strInFmt);
            Time time = new Time();
            time.setToNow();
            long lOffset = time.gmtoff * 1000L;
            long lUtcMillis = lUserZoneMillis - lOffset;
            return lUtcMillis;
        }
    }

    public static String getUtcString(String strUserZoneTime, String strInFmt, String strOutFmt) throws ParseException {
        if (StringUtil.isNull(strUserZoneTime)) {
            throw new NullPointerException("参数strUserZoneTime不能为空");
        } else if (StringUtil.isNull(strInFmt)) {
            throw new NullPointerException("参数strInFmt不能为空");
        } else {
            long lUtcMillis = getUtcMillis(strUserZoneTime, strInFmt);
            String strFmt = strInFmt;
            if (!StringUtil.isNull(strOutFmt)) {
                strFmt = strOutFmt;
            }

            return format(lUtcMillis, strFmt);
        }
    }

    public static String getUtcDateTimeString(String strUserZoneDateTime) throws ParseException {
        if (StringUtil.isNull(strUserZoneDateTime)) {
            throw new NullPointerException("参数strUserZoneDateTime不能为空");
        } else {
            return getUtcString(strUserZoneDateTime, "yyyy-MM-dd kk:mm:ss", (String)null);
        }
    }

    public static String getUtcDateTimeString1(String strUserZoneDateTime) {
        if (StringUtil.isNull(strUserZoneDateTime)) {
            throw new NullPointerException("参数strUserZoneDateTime不能为空");
        } else {
            try {
                return getUtcString(strUserZoneDateTime, "yyyy-MM-dd kk:mm:ss", (String)null);
            } catch (ParseException var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    public static String getUtcDateString(String strUserZoneDate) throws ParseException {
        if (StringUtil.isNull(strUserZoneDate)) {
            throw new NullPointerException("参数strUserZoneDate不能为空");
        } else {
            return getUtcString(strUserZoneDate, "yyyy-MM-dd", (String)null);
        }
    }

    public static String getFirstDayInWeek(long lMillis) {
        Calendar nativeCalendar = parseCalendar(lMillis);
        nativeCalendar.set(7, 2);
        return format(nativeCalendar.getTimeInMillis(), "yyyy-MM-dd");
    }

    public static String getLastDayInWeek(long lMillis) {
        Calendar nativeCalendar = parseCalendar(lMillis);
        nativeCalendar.set(7, 1);
        return format(nativeCalendar.getTimeInMillis(), "yyyy-MM-dd");
    }

    public static String getFirstDayInWeekByDate(String date) throws ParseException {
        long lMillis = parseMillis(date, "yyyy-MM-dd");
        return format(lMillis, "yyyy-MM-dd");
    }

    public static String getFirstDayLastWeek(long lMillis) {
        return getFirstDayInWeek(modWeek(lMillis, -1));
    }

    public static String getLastDayLastWeek(String date) {
        try {
            long lMillis = parseMillis(date, "yyyy-MM-dd");
            return getLastDayInWeek(modWeek(lMillis, -1));
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String getLastDayNextWeek(String date) {
        try {
            long lMillis = parseMillis(date, "yyyy-MM-dd");
            return getLastDayNextWeek(modWeek(lMillis, -1));
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String getLastDayNextWeek1(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;

        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int dayOfWeek = cal.get(7) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }

        cal.add(5, -dayOfWeek + 14);
        return simpleDateFormat.format(cal.getTime()).trim();
    }

    public static String getFirstDayInWeek(String strDateTime) throws ParseException {
        long lMillis = parseMillis(strDateTime, "yyyy-MM-dd HH:mm:ss");
        return getFirstDayInWeek(lMillis);
    }

    public static String getFirstDayLastWeek(String strDateTime) {
        try {
            long lMillis = parseMillis(strDateTime, "yyyy-MM-dd");
            return getFirstDayLastWeek(lMillis);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String getFirstDayNextWeek(long lMillis) {
        return getFirstDayInWeek(modWeek(lMillis, 1));
    }

    public static String getLastDayNextWeek(long lMillis) {
        return getLastDayInWeek(modWeek(lMillis, 1));
    }

    public static String getFirstDayNextWeek(String strDateTime) {
        try {
            long lMillis = parseMillis(strDateTime, "yyyy-MM-dd");
            return getFirstDayNextWeek(lMillis);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String getFirstDayInMonth(long lMillis) {
        Calendar nativeCalendar = parseCalendar(lMillis);
        nativeCalendar.set(5, 1);
        return format(nativeCalendar.getTimeInMillis(), "yyyy-MM-dd");
    }

    public static String getLastDayInMonth(long lMillis) {
        Calendar nativeCalendar = parseCalendar(lMillis);
        nativeCalendar.set(5, nativeCalendar.getActualMaximum(5));
        return format(nativeCalendar.getTimeInMillis(), "yyyy-MM-dd");
    }

    public static String getFirstDayInMonth(String strDateTime) throws ParseException {
        long lMillis = parseMillis(strDateTime, "yyyy-MM-dd");
        return getFirstDayInMonth(lMillis);
    }

    public static String getFirstDayLastMonth(long lMillis) {
        return getFirstDayInMonth(modMonth(lMillis, -1));
    }

    public static String getLastDayLastMonth(long lMillis) {
        return getLastDayInMonth(modMonth(lMillis, -1));
    }

    public static String getFirstDayNextMonth(long lMillis) {
        return getFirstDayInMonth(modMonth(lMillis, 1));
    }

    public static String getLastDayNextMonth(long lMillis) {
        return getLastDayInMonth(modMonth(lMillis, 1));
    }

    public static String getFirstDayLastMonth(String strDateTime) {
        try {
            long lMillis = parseMillis(strDateTime, "yyyy-MM-dd");
            return getFirstDayLastMonth(lMillis);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String getLastDayLastMonth(String strDateTime) {
        try {
            long lMillis = parseMillis(strDateTime, "yyyy-MM-dd");
            return getLastDayLastMonth(lMillis);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String getFirstDayNextMonth(String strDateTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(strDateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(5, 1);
            calendar.add(2, 1);
            return format.format(date.getTime()).trim();
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static String getFirstDayNextMonth1(String strDateTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(strDateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(5, 1);
            calendar.add(2, 1);
            long lMillis = parseMillis(strDateTime, "yyyy-MM-dd");
            return getFirstDayNextMonth(lMillis);
        } catch (ParseException var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static String getLastDayNextMonth(String strDateTime) {
        try {
            long lMillis = parseMillis(strDateTime, "yyyy-MM-dd");
            return getLastDayNextMonth(lMillis);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static long parseMillis(String strDate, String strInFmt) throws ParseException {
        if (StringUtil.isNull(strDate)) {
            throw new NullPointerException("参数strDate不能为空");
        } else if (StringUtil.isNull(strInFmt)) {
            throw new NullPointerException("参数strInFmt不能为空");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(strInFmt, Locale.getDefault());
            Date date = sdf.parse(strDate.toString());
            return date.getTime();
        }
    }

    public static Calendar parseCalendar(long lMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTimeInMillis(lMillis);
        return calendar;
    }

    public static long remainMillis(long lMillis) {
        Calendar nativeCalendar = Calendar.getInstance();
        nativeCalendar.setFirstDayOfWeek(2);
        nativeCalendar.setTimeInMillis(lMillis);
        nativeCalendar.set(11, 0);
        nativeCalendar.set(12, 0);
        nativeCalendar.set(13, 0);
        nativeCalendar.set(14, 0);
        return nativeCalendar.getTimeInMillis();
    }

    public static long modMillis(long lMillis, long lRawOffset) {
        return lMillis + lRawOffset;
    }

    public static long modSecond(long lMillis, int iSeconds) {
        Time time = new Time();
        time.set(lMillis);
        time.second += iSeconds;
        return time.toMillis(false);
    }

    public static long modMinute(long lMillis, int iMinutes) {
        Time time = new Time();
        time.set(lMillis);
        time.minute += iMinutes;
        return time.toMillis(false);
    }

    public static long modDay(long lMillis, int iDays) {
        Time time = new Time();
        time.set(lMillis);
        time.monthDay += iDays;
        return time.toMillis(false);
    }

    public static long modWeek(long lMillis, int iWeeks) {
        Time time = new Time();
        time.set(lMillis);
        time.monthDay += iWeeks * 7;
        return time.toMillis(false);
    }

    public static long modMonth(long lMillis, int iMonths) {
        Time time = new Time();
        time.set(lMillis);
        time.month += iMonths;
        return time.toMillis(false);
    }

    public static String format(long lMillis, String strInFmt) {
        if (StringUtil.isNull(strInFmt)) {
            throw new NullPointerException("参数strInFmt不能为空");
        } else {
            return (String)DateFormat.format(strInFmt, lMillis);
        }
    }

    public static String format(String strIn, String strInFmt, String strOutFmt) throws ParseException {
        if (StringUtil.isNull(strIn)) {
            throw new NullPointerException("参数strIn不能为空");
        } else if (StringUtil.isNull(strInFmt)) {
            throw new NullPointerException("参数strInFmt不能为空");
        } else if (StringUtil.isNull(strOutFmt)) {
            throw new NullPointerException("参数strOutFmt不能为空");
        } else {
            long lMillis = parseMillis(strIn, strInFmt);
            return format(lMillis, strOutFmt);
        }
    }

    public static boolean isLater(long lPsv, long lNgv) {
        long lResult = lPsv - lNgv;
        return lResult > 0L;
    }

    public static boolean isLater(String strPsv, String strNgv, String strFormat) throws ParseException {
        if (StringUtil.isNull(strPsv)) {
            throw new NullPointerException("参数strPsv不能为空");
        } else if (StringUtil.isNull(strNgv)) {
            throw new NullPointerException("参数strNgv不能为空");
        } else if (StringUtil.isNull(strFormat)) {
            throw new NullPointerException("参数strFormat不能为空");
        } else {
            long lPsv = parseMillis(strPsv, strFormat);
            long lNgv = parseMillis(strNgv, strFormat);
            return isLater(lPsv, lNgv);
        }
    }

    public static int calMonths(long lFrom, long lTo) {
        Time fromTime = getTime(lFrom);
        Time toTime = getTime(lTo);
        int iFromYear = fromTime.year;
        int iToYear = toTime.year;
        int iFromMonth = fromTime.month;
        int iToMonth = toTime.month;
        return (iToYear - iFromYear) * 12 + (iToMonth - iFromMonth);
    }

    public static int calMinutes(long lFrom, long lTo) {
        long lMillis = lTo - lFrom;
        int iMinutes = (int)(lMillis / 60000L);
        return iMinutes;
    }

    public static String getDayOfWeekStringCN(int iDayOfWeek) {
        String strWhatDay = null;
        switch(iDayOfWeek + 1) {
            case 1:
                strWhatDay = "周日";
                break;
            case 2:
                strWhatDay = "周一";
                break;
            case 3:
                strWhatDay = "周二";
                break;
            case 4:
                strWhatDay = "周三";
                break;
            case 5:
                strWhatDay = "周四";
                break;
            case 6:
                strWhatDay = "周五";
                break;
            case 7:
                strWhatDay = "周六";
                break;
            default:
                strWhatDay = "周一";
        }

        return strWhatDay;
    }

    public static String getMonthStringCN(int iMonth) {
        String strWhatMonth = null;
        switch(iMonth) {
            case 0:
                strWhatMonth = "1月";
                break;
            case 1:
                strWhatMonth = "2月";
                break;
            case 2:
                strWhatMonth = "3月";
                break;
            case 3:
                strWhatMonth = "4月";
                break;
            case 4:
                strWhatMonth = "5月";
                break;
            case 5:
                strWhatMonth = "6月";
                break;
            case 6:
                strWhatMonth = "7月";
                break;
            case 7:
                strWhatMonth = "8月";
                break;
            case 8:
                strWhatMonth = "9月";
                break;
            case 9:
                strWhatMonth = "10月";
                break;
            case 10:
                strWhatMonth = "11月";
                break;
            case 11:
                strWhatMonth = "12月";
                break;
            default:
                strWhatMonth = "1月";
        }

        return strWhatMonth;
    }

    public static String getComparedDateStringCN(String strDate, String strInFmt) throws ParseException {
        long lTodayMillis = remainMillis(System.currentTimeMillis());
        long lComparedMillis = parseMillis(strDate, strInFmt);
        long lOffset = lTodayMillis - lComparedMillis;
        if (lOffset == 0L) {
            return "今天";
        } else if (lOffset > 0L && lOffset <= 86400000L) {
            return "昨天";
        } else if (lOffset > 86400000L && lOffset <= 172800000L) {
            return "前天";
        } else {
            Time time = new Time();
            time.set(lComparedMillis);
            int iWhatDay = time.weekDay;
            return getDayOfWeekStringCN(iWhatDay);
        }
    }

    public static String getDateStringForDayCN(String strDate, String strInFmt) throws ParseException {
        long lMillis = parseMillis(strDate, strInFmt);
        Time time = new Time();
        time.set(lMillis);
        int iDay = time.monthDay;
        if (iDay == 1) {
            int iMonth = time.month;
            return getMonthStringCN(iMonth);
        } else {
            return String.valueOf(iDay);
        }
    }

    public static String getDateStringForWeekCN(String strDate, String strInFmt) throws ParseException {
        long lMillis = parseMillis(strDate, strInFmt);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTimeInMillis(lMillis);
        int iWeek = calendar.get(8);
        int iDay;
        if (iWeek == 1) {
            iDay = calendar.get(2);
            return getMonthStringCN(iDay);
        } else {
            iDay = calendar.get(5);
            return String.valueOf(iDay);
        }
    }

    public static boolean isWeekend(Calendar cal) {
        int week = cal.get(7) - 1;
        return week == 6 || week == 0;
    }

    public static String dealLeftTime(int leftTime) {
        String res = "";
        int hour = leftTime / 60;
        int min = leftTime % 60;
        if (hour < 10) {
            res = "0" + hour;
        } else {
            res = "" + hour;
        }

        if (min < 10) {
            res = res + ":0" + min;
        } else {
            res = res + ":" + min;
        }

        return res;
    }

    public static byte[] getUtcTimeByte() {
        byte[] timeBytes = new byte[]{(byte)Integer.parseInt(getCurUtcString("yy")), (byte)Integer.parseInt(getCurUtcString("MM")), (byte)Integer.parseInt(getCurUtcString("dd")), (byte)Integer.parseInt(getCurUtcString("kk")), (byte)Integer.parseInt(getCurUtcString("mm")), (byte)Integer.parseInt(getCurUtcString("ss"))};
        return timeBytes;
    }

    public static byte getByteTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        long lRawOffset = (long)timeZone.getRawOffset();
        long lAbsRawOffset = Math.abs(lRawOffset);
        int iHour = (int)(lAbsRawOffset / 3600000L) + 12;
        int iMinute = (int)(lAbsRawOffset - (long)(iHour * 3600000)) / '\uea60';
        int minType = 0;
        int res = 0;
        switch(iMinute) {
            case 0:
                minType = 0;
                break;
            case 15:
                minType = 1;
                break;
            case 30:
                minType = 2;
                break;
            case 45:
                minType = 3;
        }

        int j;
        for(j = 0; j < 8; ++j) {
            if (((byte)iHour >> j & 1) == 1) {
                res = (int)((double)res + Math.pow(2.0D, (double)(j + 3)));
            }
        }

        for(j = 0; j < 3; ++j) {
            if (((byte)minType >> j & 1) == 1) {
                res = (int)((double)res + Math.pow(2.0D, (double)j));
            }
        }

        return (byte)res;
    }

    public static String getUCTTime() {
        Calendar cal = Calendar.getInstance();
        int zoneOffset = cal.get(15);
        int dstOffset = cal.get(16);
        cal.add(14, -(zoneOffset + dstOffset));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date(cal.getTimeInMillis());
        return sdf.format(date);
    }

    public static long parseLong(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = format.parse(time);
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        return date.getTime();
    }

    public static String getNextDate(String time) {
        long lt = parseLong(time) + 86400000L;
        return format(lt, "yyyy-MM-dd");
    }

    public static Date strToDateLong(String strDate) {
        if (!TextUtils.isEmpty(strDate)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(strDate, pos);
            return strtodate;
        } else {
            return null;
        }
    }

    public static String minToHour(int time) {
        int hours = time / 60;
        int minutes = time % 60;
        return hours + "h" + minutes + "min";
    }

    public static String getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -1);
        return (new SimpleDateFormat("yyyy-MM-dd ")).format(cal.getTime()).trim();
    }

    public static String getYesterday1() {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -1);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime()).trim();
    }

    public static String getAnteayer() {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -2);
        return (new SimpleDateFormat("yyyy-MM-dd ")).format(cal.getTime()).trim();
    }

    public static String getBeforeDate(int count) {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -count);
        return (new SimpleDateFormat("yyyy-MM-dd ")).format(cal.getTime()).trim();
    }

    public static String getAfterDate(int count) {
        Calendar cal = Calendar.getInstance();
        cal.add(5, count);
        return (new SimpleDateFormat("yyyy-MM-dd ")).format(cal.getTime()).trim();
    }

    public static int getDayOfMonth() {
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int day = aCalendar.getActualMaximum(5);
        return day;
    }

    public static int getDayOfMonth(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;

        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        aCalendar.setTime(date1);
        int day = aCalendar.getActualMaximum(5);
        return day;
    }

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static Date getLastWeekMondayDate(Date date) {
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(7) - 1;
        int offset = 1 - dayOfWeek;
        cal.setTime(date);
        cal.add(5, offset - 7);
        return cal.getTime();
    }

    public static String getFirstdayofThisMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(5, 1);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
    }

    public static String getEarlierToday(int n) {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -n);
        Log.e("getAnteayer", (new SimpleDateFormat("yyyy-MM-dd ")).format(cal.getTime()));
        return (new SimpleDateFormat("yyyy-MM-dd ")).format(cal.getTime()).trim();
    }

    public static String getLastWeekMonday(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(7) - 1;
        int offset = 1 - dayOfWeek;
        cal.setTime(date);
        cal.add(5, offset - 7);
        return simpleDateFormat.format(cal.getTime()).trim();
    }

    public static String getLastWeekSunday(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(7) - 1;
        int offset = 7 - dayOfWeek;
        cal.setTime(date);
        cal.add(5, offset - 7);
        return simpleDateFormat.format(cal.getTime()).trim();
    }

    public static String getThisWeekMonday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(7) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }

        cal.add(5, -dayOfWeek + 1);
        return simpleDateFormat.format(cal.getTime()).trim();
    }

    public static String getThisWeekSunday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(7) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }

        cal.add(5, -dayOfWeek + 7);
        return simpleDateFormat.format(cal.getTime()).trim();
    }

    public static String getThisMonthFirstDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal_1 = Calendar.getInstance();
        cal_1.set(5, 1);
        String firstDay = format.format(cal_1.getTime());
        return firstDay;
    }

    public static String getThisMonthLastDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(5, ca.getActualMaximum(5));
        String last = format.format(ca.getTime());
        return last;
    }

    public static String getTodayday() {
        Calendar cal = Calendar.getInstance();
        cal.add(5, 0);
        return (new SimpleDateFormat("yyyy-MM-dd ")).format(cal.getTime()).trim();
    }

    public static String getTodayday1() {
        Calendar cal = Calendar.getInstance();
        cal.add(5, 0);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime()).trim();
    }

    public static int getCurHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(11);
    }

    public static int getCurMunite() {
        Calendar cal = Calendar.getInstance();
        return cal.get(12);
    }
}

