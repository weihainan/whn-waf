package com.whn.waf.common.utils;

/**
 * @author Administrator.
 * @since 0.1 created on 2017/3/19 0019.
 */
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    /**
     * 2个日期差几天
     */
    public static int ntervalDays(Date date, Date otherDate) {
        long time = Math.abs(new LocalDate(date).toDate().getTime() - new LocalDate(otherDate).toDate().getTime());
        return (int) time / (24 * 60 * 60 * 1000);
    }


    private static List<Date> getDates(int year, int month) { // 获取每个月的工作日
        List<Date> dates = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);

        while (cal.get(Calendar.YEAR) == year &&
                cal.get(Calendar.MONTH) < month) {
            int day = cal.get(Calendar.DAY_OF_WEEK);

            if (!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)) {
                dates.add((Date) cal.getTime().clone());
            }
            cal.add(Calendar.DATE, 1);
        }
        return dates;
    }

    /**
     * 获取星期一的日期
     */
    public static Date getFirstDayOfWeek() {
        return LocalDate.now().dayOfWeek().withMinimumValue().toDate();
    }

    /**
     * 获取星期天的日期
     */
    public static Date getLastDayOfWeek() {
        return LocalDate.now().dayOfWeek().withMaximumValue().toDate();
    }

    /**
     * 获取每月第一天的日期
     */
    public static Date getFirstDayOfMonth() {
        return LocalDate.now().dayOfMonth().withMinimumValue().toDate();
    }

    /**
     * 获取每月最后一天的日期
     */
    public static Date getLastDayOfMonth() {
        return LocalDate.now().dayOfMonth().withMaximumValue().toDate();
    }

    /**
     * 获取每年第一天的日期
     */
    public static Date getFirstDayOfYear() {
        return LocalDate.now().dayOfYear().withMinimumValue().toDate();
    }

    /**
     * 获取每年最后一天的日期
     */
    public static Date getLastDayOfYear() {
        return LocalDate.now().dayOfYear().withMaximumValue().toDate();
    }

    /**
     * 生成随机时间
     */
    public static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    public static String formatDateTime(Date date, String format) {
        if (date == null) return "";
        if (format == null) return date.toString();
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String formatY0M0D(Date date) {
        return date == null ? "" : formatDateTime(date, "yyyyMMdd");
    }

    public static String formatYMD(Date date) {
        return date == null ? "" : formatDateTime(date, "yyyy-MM-dd");
    }

    public static String formatYMDHM(Date date) {
        return date == null ? "" : formatDateTime(date, "yyyy-MM-dd HH:mm");
    }

    public static String formatDateTimeByDate(Date date) {
        return date == null ? "" : formatDateTime(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatTimeStampByDate(Date date) {
        return date == null ? "" : formatDateTime(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static String formatTimeStampSByDate(Date date) {
        return date == null ? "" : formatDateTime(date, "yyyy-MM-dd HH:mm:ss.S");
    }
}
