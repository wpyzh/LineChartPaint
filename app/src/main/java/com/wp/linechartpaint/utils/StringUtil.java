package com.wp.linechartpaint.utils;

/**
 * <p>描述：(这里用一句话描述这个类的作用)</p>
 * 作者： wp<br>
 * 日期： 2019/6/6 15:31<br>
 * 版本： v2.0<br>
 */

import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {
    public StringUtil() {
    }

    public static String byteToMac(byte[] resBytes) {
        StringBuffer buffer = new StringBuffer();

        for(int i = 0; i < resBytes.length; ++i) {
            String hex = Integer.toHexString(resBytes[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            buffer.append(hex.toUpperCase());
        }

        return buffer.toString();
    }

    public static byte[] short2Byte(short a) {
        byte[] b = new byte[]{(byte)(a >> 8), (byte)a};
        return b;
    }

    public static short byte2Short(byte[] b) {
        return (short)((b[0] & 255) << 8 | b[1] & 255);
    }

    public static String toHexString(byte[] b) {
        StringBuffer buffer = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            String s = Integer.toHexString(b[i] & 255);
            if (s.length() == 1) {
                s = "0" + s;
            }

            buffer.append(s + " ");
        }

        return buffer.toString();
    }

    public static String byteArrayTo4HexString(byte[] bytes) {
        Formatter fmt = new Formatter(new StringBuilder(bytes.length * 2));
        byte[] var2 = bytes;
        int var3 = bytes.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            fmt.format("%02x", b);
        }

        String ret = fmt.toString();
        return spliteStringTo4(ret);
    }

    public static String spliteStringTo4(String str) {
        str = str.toUpperCase();
        StringBuilder ret = new StringBuilder();

        for(int i = 0; i < str.length(); ++i) {
            ret.append(str.charAt(i));
            if ((i + 1) % 8 == 0) {
                ret.append(" ");
            }
        }

        return ret.toString();
    }

    public static String[] getDeviceType(String str) {
        String[] type = new String[]{"-1", "-1"};
        if (str == null) {
            return type;
        } else {
            if (str.length() >= 3) {
                String sub = str.substring(str.length() - 3, str.length());
                if (!sub.contains("-")) {
                    return null;
                }

                type = sub.split("-");
                if (!isNum(type[0])) {
                    type[0] = "-1";
                }

                if (!isNum(type[1])) {
                    type[1] = "-1";
                }
            }

            return type;
        }
    }

    public static String[] getDeviceType1(String str) {
        String[] type = new String[]{"-1", "-1"};
        if (isNull(str)) {
            return type;
        } else if (str.contains("-")) {
            String[] tmp = str.split("-");
            int pos1 = tmp.length - 2;
            if (pos1 >= 0 && isNum(tmp[pos1])) {
                type[0] = tmp[pos1];
            }

            int pos2 = tmp.length - 1;
            if (pos2 > 0 && isNum(tmp[pos2])) {
                type[1] = tmp[pos2];
            }

            return type;
        } else {
            return null;
        }
    }

    public static String byteArrayToHexString(byte[] bytes) {
        Formatter fmt = new Formatter(new StringBuilder(bytes.length * 2));
        byte[] var2 = bytes;
        int var3 = bytes.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            fmt.format("%02x", b);
        }

        return fmt.toString();
    }

    public static boolean isNull(String strSource) {
        return strSource == null || "".equals(strSource.replaceAll(" ", ""));
    }

    public static boolean isNullOrEmpty(String strSource) {
        return strSource == null || "".equals(strSource.replaceAll(" ", ""));
    }

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static int toInt(Object obj) {
        return obj == null ? 0 : toInt(obj.toString(), 0);
    }

    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception var3) {
            return defValue;
        }
    }

    public static boolean isNum(String strNum) {
        return strNum != null && !"".equals(strNum) ? strNum.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$") : false;
    }

    public static boolean isPhoneNum(String strPhoneNum) {
        return Pattern.matches("^1[3|4|5|7|8][0-9]\\d{8}$", strPhoneNum);
    }

    public static boolean isLoginRight(String strPhoneNum) {
        return strPhoneNum.contains("@") ? isEmail(strPhoneNum) : isPhoneNum(strPhoneNum);
    }

    public static boolean isLetter(char c) {
        int k = 255;
        return c / k == 0;
    }

    public static int getCharLength(char c) {
        return isLetter(c) ? 1 : 2;
    }

    public static int getStringLength(String strSource) {
        int iSrcLen = 0;
        char[] arrChars = strSource.toCharArray();
        char[] var3 = arrChars;
        int var4 = arrChars.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            char arrChar = var3[var5];
            iSrcLen += getCharLength(arrChar);
        }

        return iSrcLen;
    }

    public static String sub(String strSource, int iSubLen, String strSuffix) {
        if (isNull(strSource)) {
            return strSource;
        } else {
            String strFilter = strSource.trim();
            int iLength = getStringLength(strFilter);
            if (iLength <= iSubLen) {
                return strFilter;
            } else {
                int iSubIndex = 0;
                char[] arrChars = strFilter.toCharArray();
                int iArrLength = arrChars.length;
                char c = arrChars[iSubIndex];
                StringBuffer sbContent = new StringBuffer();
                int iNum = iSubLen - getCharLength(c);

                while(iNum > -1 && iSubIndex < iArrLength) {
                    ++iSubIndex;
                    sbContent.append(c);
                    if (iSubIndex < iArrLength) {
                        c = arrChars[iSubIndex];
                        iNum -= getCharLength(c);
                    }
                }

                strFilter = sbContent.toString();
                if (!isNull(strSuffix)) {
                    strFilter = strFilter + strSuffix;
                }

                return strFilter;
            }
        }
    }

    public static String subWithDots(String strSource, int iSubLen) {
        return sub(strSource, iSubLen, "...");
    }

    public static String getNumberForString(String includeNumberString) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(includeNumberString);
        String num = m.replaceAll("").trim();
        return num;
    }

    public static int[] minute2Hour(int minutes) {
        int hour = minutes / 60;
        int minute = minutes % 60;
        int[] time = new int[]{hour, minute};
        System.out.println(hour + "小时" + minute + "分");
        return time;
    }

    public static int hour2minute(int[] time) {
        int hour = time[0] * 60 + time[1];
        System.out.println("分钟:" + hour);
        return hour;
    }

    public static String[] getYear(String date) {
        String[] strYear = date.split("-");
        return strYear;
    }

    public static boolean isPwd(String pwd) {
        String regEx = "[a-zA-Z0-9]{6,20}";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    public static String hideString(String data) {
        String result = "";
        result = data.replace(data.subSequence(3, 6), "****");
        return result;
    }

    public static int getThisYear() {
        Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
        int year = dateAndTime.get(1);
        return year;
    }

    public static boolean checkFace(String checkString) {
        String reg = "^([a-z]|[A-Z]|[0-9]|[\u0000-ÿ]|[ -\uffff]){1,}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(checkString.replaceAll(" ", ""));
        return matcher.matches();
    }

    public static boolean checkDevName(String devName) {
        String reg = "^([一-龥]|[a-z]|[A-Z]|[0-9])+$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(devName);
        return matcher.matches();
    }

    public static boolean checkName(String nameString) {
        String reg = "([一-龥]|[a-z]|[A-Z]|[0-9]|\\.|\\-|\\_|\\@)+";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(nameString);
        return matcher.matches();
    }

    public static boolean checkPwd(String pwdString) {
        String reg = "^([a-z]|[A-Z]|[0-9]){6,20}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(pwdString);
        return matcher.matches();
    }

    public static String ToWrap(String input) {
        char[] c = input.toCharArray();

        for(int i = 0; i < c.length; ++i) {
            if (c[i] == 12288) {
                c[i] = ' ';
            } else if (c[i] > '\uff00' && c[i] < '｟') {
                c[i] -= 'ﻠ';
            }
        }

        return new String(c);
    }
}

