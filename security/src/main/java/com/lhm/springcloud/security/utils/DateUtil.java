package com.lhm.springcloud.security.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author: xiazichao
 * @description: 时间工具类
 * @date: Created in 9:10 2017/12/12
 */
public class DateUtil {

    private static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static String DATETIME_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";
    private static String DATETIME_FORMAT_SECOND_NOSPACE = "yyyy/MM/dd-HH:mm:ss";
    private static String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    private static String DATETIMEFORMAT = "yyyyMMdd";
    private static String DATETIMEFORMAT_HH = "yyMMddHH";
    private static String DATE_FORMAT = "yyyy-MM-dd";
    private static String DATE_FORMAT_MONTH = "yyyy-MM";
    private static String DATETIME_FORMAT_T_SECOND = "yyyy-MM-dd'T'HH:mm:ss";
    private static String DATETIME_FORMAT_PULL_REFRSH = "MM月dd日 HH:mm";
    private static String DATETIME_GMT_FORMAT = "EEE, d MMM yyyy HH:mm:ss 'GMT'";
    private static String DATETIME_FORMAT_SEC_OBLIQUE = "yyyy/MM/dd HH:mm:ss";
    private static String DATETIME_FORMAT_MIN_OBLIQUE = "yyyy/MM/dd HH:mm";
    public static String DATETIME_FORMAT_TIME = "HH:mm:ss";

    private static ZoneId zone = ZoneId.systemDefault();

    /**
     * 获取当前时间
     */
    public static Date getNow() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * date + time 转字符串
     */
    public static String date2LongString(Date date) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT_SECOND);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * date + time 转字符串
     */
    public static String date2FormatLongString(Date date) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT_T_SECOND);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * date 转字符串
     */
    public static String date2ShortString(Date date) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 字符串 转 date
     */
    public static Date convertToDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 时间戳转日期
     *
     * @param timestamp
     * @return
     */
    public static String timestamp2Date_YYYYMMDD(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date(timestamp));
    }

    /**
     * 得到时间区间的天数
     *
     * @param starttime
     * @param endtime
     * @return
     */
    public static int getRangeDayNum(long starttime, long endtime) {
        return (int) ((endtime - starttime) / (1000 * 3600 * 24));
    }

    /**
     * 指定日期加上天数后的日期
     *
     * @param num       为增加的天数
     * @param timestamp 指定时间
     * @return
     */
    public static long plusDay(int num, long timestamp) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date currdate = formatter.parse(formatter.format(new Date(timestamp)));
            System.out.println("现在的日期是：" + currdate);
            Calendar ca = Calendar.getInstance();
            ca.setTime(currdate);
            ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
            currdate = ca.getTime();
            String enddate = formatter.format(currdate);
            System.out.println("增加天数以后的日期：" + enddate);
            return formatter.parse(enddate).getTime();
        } catch (ParseException e) {
            return 0l;
        }
    }

    public static long getOneDayStartTimeStamp(String targetTime) {
        try {
            long result = -1l;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.parse(targetTime + " 00:00:00").getTime();
        } catch (ParseException e) {
            return 0l;
        }
    }

    public static long getOneDayEndTimeStamp(String targetTime) {
        try {
            long result = -1l;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.parse(targetTime + " 23:59:59").getTime();
        } catch (ParseException e) {
            return 0l;
        }
    }

    public static Long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * Get the previous time, from how many days to now.
     *
     * @param days How many days.
     * @return The new previous time.
     */
    public static Date previous(int days) {
        return new Date(System.currentTimeMillis() - days * 3600000L * 24L);
    }

    /**
     * Convert date and time to string like "yyyy-MM-dd HH:mm:ss".
     */
    public static String formatDateTime(Date d) {
        return formatDate(d, DATETIME_FORMAT_SECOND);
    }

    public static String formatDateTimeNoSpace(Date d) {
        return formatDate(d, DATETIME_FORMAT_SECOND_NOSPACE);
    }

    /**
     * Convert date and time to string like "yyyy-MM-dd HH:mm".
     */
    public static String formatDateTimeNoSec(Date d) {
        return formatDate(d, DATETIME_FORMAT);
    }

    /**
     * Convert date and time to string like "yyyyMMddHH".
     *
     * @param d
     * @return
     */
    public static String formatDateTimeHH(Date d) {
        return formatDate(d, DATETIMEFORMAT_HH);
    }

    /**
     * Convert date and time to string like "yyyyMMdd".
     *
     * @param d
     * @return
     */
    public static String formatDateTimeDD(Date d) {
        return formatDate(d, DATETIMEFORMAT);
    }

    /**
     * Convert date and time to string like "yyyyMMddHHmmss".
     */
    public static String formatDateToString(Date d) {
        return formatDate(d, DATE_TIME_FORMAT);
    }

    /**
     * Convert date and time to string like "yyyy-MM-dd HH:mm".
     */
    public static String formatDateTime(long d) {
        return formatDate(d, DATETIME_FORMAT);
    }

    /**
     * Convert date to String like "yyyy-MM-dd".
     */
    public static String formatDate(Date d) {
        return formatDate(d, DATE_FORMAT);
    }

    /**
     * Convert date to String like "yyyy-MM".
     */
    public static String formatDateMonth(Date d) {
        return formatDate(d, DATE_FORMAT_MONTH);
    }

    /**
     * convert date to string like "yyyy/mm/dd hh:mm:ss"
     *
     * @param d
     * @return
     */
    public static String formateDateOblique(Date d) {
        return formatDate(d, DATETIME_FORMAT_SEC_OBLIQUE);
    }

    public static String formatDate(Date d, String dataFormat) {
        return new SimpleDateFormat(dataFormat).format(d);
    }

    public static String formatDate(long d, String dataFormat) {
        return new SimpleDateFormat(dataFormat).format(d);
    }

    /**
     * Parse date like "yyyy-MM-dd".
     */
    public static Date parseDate(String d) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(d);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Parse date like "yyyy-MM-dd" to "yyyy-MM-dd 00:00:00".
     */
    public static Date parseDateBegin(Date d) {
        try {
            String str_date = formatDate(d) + " 00:00:00";
            // return parseDateTime(str_date);
            return parseStringToDateHMS(str_date);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Parse date like "yyyy-MM-dd" to "yyyy-MM-dd 23:59:59".
     */
    public static Date parseDateEnd(Date d) {
        try {
            String str_date = formatDate(d) + " 23:59:59";
            // return parseDateTime(str_date);
            return parseStringToDateHMS(str_date);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Convert date and time to string like "yyyy-MM-dd HH:mm:ss".
     */
    public static String formatDateToStringHMS(Date d) {
        return new SimpleDateFormat(DATETIME_FORMAT_SECOND).format(d);
    }

    /**
     * Parse date like "yyyy-MM-dd" .
     *
     * @throws ParseException
     */
    public static Date paraseStringToDate(String d) throws ParseException {
        return parse(DATE_FORMAT, d);
    }

    /**
     * Parse date and time like "yyyy-MM-dd hh:mm"
     *
     * @throws ParseException
     */
    public static Date parseDateTime(String d) throws ParseException {
        return parse(DATETIME_FORMAT, d);
    }

    /**
     * Parse date like "yyyy-MM-dd HH:mm:ss" .
     *
     * @throws ParseException
     */
    public static Date parseStringToDateHMS(String d) throws ParseException {
        return parse(DATETIME_FORMAT_SECOND, d);
    }

    public static Date parseStringToDateTHMS(String d) throws ParseException {
        return parse(DATETIME_FORMAT_T_SECOND, d);
    }

    /**
     * parse date like "2015/10/10 15:05"
     *
     * @param d
     * @return
     * @throws ParseException
     */
    public static Date parseStringToOblique(String d) throws ParseException {
        return parse(DATETIME_FORMAT_MIN_OBLIQUE, d);
    }

    public static Date parse(String f, String d) throws ParseException {
        return new SimpleDateFormat(f).parse(d);
    }

    /**
     * Convert date and time to string like "yyyy-MM-dd HH:mm".
     */
    public static String formatDateTimePullRefresh(Date d) {
        return formatDate(d, DATETIME_FORMAT_PULL_REFRSH);
    }

    public static Date parseGMT(String d) throws ParseException {
        DateFormat format = new SimpleDateFormat(DATETIME_GMT_FORMAT, Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.parse(d);
    }

    /**
     * 返回指定日志对应的星期一时间 xxx-xx-xx 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getSpecMondayDate(Date date) {
        int mondayPlus = getMondayPlus();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = gc.getTime();
        return parseDateBegin(monday);
    }

    /**
     * 返回本周星期一日期时间 xxx-xx-xx 00:00:00
     *
     * @return
     */
    public static Date getCurMondayDate() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar cur = new GregorianCalendar();
        cur.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = cur.getTime();
        return parseDateBegin(monday);
    }

    /**
     * 按照与当前时间月差参数，返回时间，格式 xxx-xx-xx 00:00:00
     *
     * @return
     */
    public static String getTimeByMonth(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, months);
        return formatDate(calendar.getTime());
    }

    /**
     * 按照与当前时间月差参数，返回时间，格式 xxx-xx-xx 00:00:00
     *
     * @return
     */
    public static String getTimeByMonth(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return formatDate(calendar.getTime());
    }

    /**
     * 按照与当前时间月差参数，返回时间，格式 xxx-xx-xx 00:00:00
     *
     * @return
     */
    public static String getTimeByDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, days);
        return formatDate(calendar.getTime());
    }

    /**
     * 获取本周星期一的日期 yyyymmdd
     *
     * @return
     */
    public static String getCurMonday() {
        return formatDateTimeDD(getCurMondayDate());
    }

    /**
     * 获取指定时间对应所在周周一的日期
     *
     * @param date
     * @return 周一日期，格式为yyyymmdd
     */
    public static String getMonday(Date date) {
        return formatDateTimeDD(getSpecMondayDate(date));
    }

    /**
     * 传入date是否是星期一
     *
     * @param date
     * @return
     */
    public static boolean isMonday(Date date) {
        return getMondayPlus(date) == 0 ? true : false;
    }

    /**
     * 获取当前年
     *
     * @param
     * @return
     */
    public static int getCurYear() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     *
     * @param
     * @return
     */
    public static String getCurMonth() {
        Calendar now = Calendar.getInstance();
        String monthStr = now.get(Calendar.MONTH) + 1 + "";
        return monthStr.length() == 1 ? "0" + monthStr : monthStr;
    }

    /**
     * 当前日与本周星期一相差的天数
     *
     * @return
     */
    private static int getMondayPlus() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * 返回指定日期与其所在周星期一相差的天数
     *
     * @param date
     * @return
     */
    private static int getMondayPlus(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    public static Date yearAdd(Date time, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, years);
        Date newTime = calendar.getTime();
        return newTime;
    }

    public static Date monthAdd(Date time, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MONTH, months);
        Date newTime = calendar.getTime();
        return newTime;
    }

    /**
     * 得到当前时间的前N个月的时间戳
     *
     * @param count
     * @return
     */
    public static long getPreMonthTimestamp(int count) {
        Date date = new Date();
        // System.out.println((new
        // SimpleDateFormat("yyyy-MM-dd")).format(date));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -count * 30);
        // 格式化输出年月日
        // SimpleDateFormat sdf = new SimpleDateFormat("dyyyy-MM-dd 00:00:00");
        // System.out.println(sdf.format(cal.getTime()));
        cal.add(Calendar.DATE, -count * 30);
        // 格式化输出年月日
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        // System.out.println(sdf.format(cal.getTime()));
        cal.add(Calendar.DATE, -count * 30);
        // 格式化输出年月日
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        // System.out.println(sdf.format(cal.getTime()));
        return cal.getTimeInMillis();
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时长
     */
    public static String stampToHours(long time) {
        String result;
        float num = (float) time / (1000 * 3600);
        DecimalFormat df = new DecimalFormat("0.0");
        result = df.format(num);
        return result;
    }


    /**
     * 两个日期间的天数
     *
     * @param start
     * @param end
     * @return
     */
    public static long getBetweenDays(Date start, Date end) {
        return (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 提前或推迟数月后的时间
     *
     * @param date
     * @param step
     * @return
     */
    public static Date getFutureDate(Date date, int step) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, step);
        Date futureDate = calendar.getTime();
        return futureDate;
    }

    /**
     * @author:liuheming
     * @Description:把秒转为 小时：分钟：秒的格式
     * @Date:17:03 2018/7/5
     * @Param：[time]
     * @return: java.lang.String
     **/
    public static String secToTime(int time) {


        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;

    }
}
