package com.qingyu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具�? * 
 * @author poll
 * @version $Id: DateUtils.java, v 0.1 2016�?1�?5�?下午4:39:27 poll Exp $
 */
public class DateUtils {

	private static String[] parsePatterns = { "yyyy-MM-dd",
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",

			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",

			"yyyyMMdd", "yyyyMMddHHmmss", "yyyyMMddHHmm" };

	public static final String DATE_TIME_FORMAT_NUM = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_NUM = "yyyyMMdd";

	public static final String DATE_TIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_STR = "yyyy-MM-dd";

	/**
	 * 得到当前日期字符�?格式（yyyy-MM-dd�?	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符�?格式（yyyy-MM-dd�?pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符�?默认格式（yyyy-MM-dd�?pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	public static String formatNow(String pattern) {
		String formatDate = DateFormatUtils.format(new Date(), pattern);
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss�?	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符�?格式（HH:mm:ss�?	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

    /**
     * 得到当前时间字符�?格式（HHmmss�?     */
    public static String getHHmmss() {
        return formatDate(new Date(), "HHmmss");
    }

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss�?	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符�?格式（yyyy�?	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

    /**
     * 得到当前小时字符�?格式（hh�?     */
    public static String getHour() {
        return formatDate(new Date(), "HH");
    }

	/**
	 * 得到当前月份字符�?格式（MM�?	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符�?格式（dd�?	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符�?格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日�?格式
	 */
	public static Date parseDate(String str) {
		if (str == null) {
			return null;
		}
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(str,
					parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 日期型字符串转化为日�?格式
	 */
	public static Date parseDate(String str, String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		try {
			return sf.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date addMinuteToDate(Date date, int minutes) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MINUTE, minutes);
		return ca.getTime();
	}

	public static Date addHourToDate(Date date, int hours) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.HOUR_OF_DAY, hours);
		return ca.getTime();
	}

	public static Date addDayToDate(Date date, int days) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_MONTH, days);
		return ca.getTime();
	}

	/**
	 * 获取指定日期的开始时�?	 * 
	 * @param dt
	 * @return
	 */
	public static Date getMinTime(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取指定日期的结束时�?	 * 
	 * @param dt
	 * @return
	 */
	public static Date getMaxTime(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	/**
	 * 获取过去的天�?	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小�?	 * 
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分�?	 * 
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}

	/**
	 * 转换为时间（�?�?�?�?毫秒�?	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (1000 * 60 * 60 * 24);
		long hour = (timeMillis / (1000 * 60 * 60) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60
				* 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "."
				+ sss;
	}

	/**
	 * 获取两个日期之间的天�?	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

    /**
    * 获取两个日期之间的秒�?    * 
    * @param before
    * @param after
    * @return
    */
    public static double getDistanceSecOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / 1000;
    }

	/**
	 * 获取yyyy-MM-dd HH:mm:ss格式的当前时�?	 * 
	 * @return
	 */
	public static String getYYYYmmddHHmmss() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

    /**
     * 获取指定日期的年月日时分�?     * 
     * @return YYYYmmdd
     */
    public static String getYYYYmmddHHmmss(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

	/**
	 * 获取当前日期的年月日
	 * 
	 * @return
	 */
	public static String getYYYYmmdd() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}
	/**
	 * 获取指定日期的年月日
	 * 
	 * @return YYYYmmdd
	 */
	public static String getYYYYmmdd(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

    /**
    * 是否是今�?    * 
    * @param date
    * @return
    */
    public static boolean isToday(final Date date) {
        return isTheDay(date, new Date());
    }

    /**
     * 是否是指定日�?     * 
     * @param date
     * @param day
     * @return
     */
    public static boolean isTheDay(final Date date, final Date day) {
        return date.getTime() >= DateUtils.dayBegin(day).getTime() && date.getTime() <= DateUtils.dayEnd(day).getTime();
    }

    /**
     * 获取指定时间的那�?00:00:00.000 的时�?     * 
     * @param date
     * @return
     */
    public static Date dayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时间的那�?23:59:59.999 的时�?     * 
     * @param date
     * @return
     */
    public static Date dayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /*
    * 得到本月的第�?��   
    * @return   
    */
    public static Date getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    /**   
     * 得到本月的最后一�?  
     *    
     * @return   
     */
    public static Date getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
}
