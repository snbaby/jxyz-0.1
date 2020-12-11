package jxyz.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static String createDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String str = sdf.format(date);
        return str;
    }

    public static String createdDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String str = sdf.format(date);
        return str;
    }

    public static String translateDateTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str;
    }


    public static String translateDateTime(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(date);
        return str;
    }


    public static String translateTime(Timestamp time) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(
                "HH:mm:ss");
        String str = sdf.format(time);
        return str;
    }


    public static String translateTime(Date time) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(
                "HH:mm:ss");
        String str = sdf.format(time);
        return str;
    }

    public static String translateTimeHHMM(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(
                "HH:mm");
        String str = sdf.format(date);
        return str;
    }


    public static String translateDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd");
        String str = sdf.format(date);
        return str;
    }

    public static String translateDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(date);
        return str;
    }

    public static Date stringToTime(String time) {
        if (time == null || time.equals(""))
            return null;
        SimpleDateFormat sdf;
        if (time.split(":").length == 2) {
            sdf = new SimpleDateFormat("HH:mm");
        } else {
            sdf = new SimpleDateFormat("HH:mm:ss");
        }
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String stringToYYMM(String time) {
        if (time == null || time.equals(""))
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
        try {
            return sdf1.format(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date stringToDatetime(String str) {
        if (str == null || str.equals(""))
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long stringToTimestamp(String str) {
        if (str == null || str.equals(""))
            return 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Date stringToDate(String str) {
        if (str == null || str.equals(""))
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date HHMMToDate(String str) {
        if (str == null || str.equals(""))
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // YYYYMMDDhhmm
    public static long getSystemTimeYYYYMMDDhhmmLong() {
        Calendar cal = Calendar.getInstance();
        long year = cal.get(Calendar.YEAR);
        long month = cal.get(Calendar.MONTH) + 1;
        long day = cal.get(Calendar.DAY_OF_MONTH);
        long hour = cal.get(Calendar.HOUR_OF_DAY);
        long minute = cal.get(Calendar.MINUTE);
        long time = year * 100000000 + month * 1000000 + day * 10000 + hour
                * 100 + minute;
        return time;
    }

    public static Date stringToDatetime(String str, String format) {
        if (str == null || str.equals(""))
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
