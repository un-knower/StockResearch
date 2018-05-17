package com.ifdiv.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

public class TimeUtils {
	public static final String DEFAULT_ERROR_DATE = "1900-11-11";
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATEYMD_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_DATEYMD_FORMAT2 = "yyyy-M-dd";
	public static final String DEFAULT_DATEYM_FORMAT = "yyyy-MM";
	public static final String DATE_FORMAT_LONG = "yyyyMMddHHmmss";

	// public static final SimpleDateFormat FORMAT_FULL_DATETIME = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// public static final SimpleDateFormat FORMAT_FULL_DATETIME_LONG = new
	// SimpleDateFormat("yyyyMMddHHmmss");
	
	
	
	public static void main(String[] args) {
//		System.out.println(TimeUtils.toString(TimeUtils.addDay(new Date(), -180), TimeUtils.DEFAULT_DATEYMD_FORMAT));
//		System.out.println(TimeUtils.getStockDate());
//		Calendar cal1 = Calendar.getInstance();
//		cal1.set(1, 2010);
//		cal1.set(2, 0);
//		cal1.set(5, 9);
//
//		Calendar cal2 = Calendar.getInstance();
//		cal2.set(1, 2010);
//		cal2.set(2, 0);
//		cal2.set(5, 13);
//
//		Date startDate = cal1.getTime();
//		Date endDate = cal2.getTime();

//		getDayList(startDate, endDate);
//		System.out.println(countDays(startDate, endDate));
	}

	public static long countDays(Date startDate, Date endDate) {
		long startValue = startDate.getTime();
		long endValue = endDate.getTime();

		if (startValue > endValue) {
			startValue = endValue + (endValue = startValue) * 0L;
		}

		return (endValue - startValue) / 1000L / 60L / 60L / 24L;
	}

	public static List<String> getDayList(Date startDate, Date endDate,SimpleDateFormat dateFormat) {
		long countDays = countDays(startDate, endDate);

		Calendar cal = Calendar.getInstance();
		boolean needTurn = startDate.getTime() > endDate.getTime();
		cal.setTime(needTurn ? endDate : startDate);

		List<String > dayList = new ArrayList<String>();

		for (int i = 1; i < countDays; i++) {
			cal.add(5, 1);
			if(dateFormat!=null){
				dayList.add( dateFormat.format(cal.getTime()));
			}
				
		}
 
		return dayList;
	}

	public static Date toDate(String source, String format) {
		Date date = null;
		if (StringUtils.hasText(source)) {
			DateFormat df = new SimpleDateFormat(
					StringUtils.hasText(format) ? format.trim()
							: "yyyy-MM-dd HH:mm:ss");
			try {
				date = df.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	public static Date toDefaultDate(String source) {
		return toDate(source, null);
	}

	public static String toString(Date source, String format) {
		String dateStr = null;
		if (source != null) {
			DateFormat df = new SimpleDateFormat(
					StringUtils.hasText(format) ? format.trim()
							: "yyyy-MM-dd HH:mm:ss");
			dateStr = df.format(source);
		}
		return dateStr;
	}

	public static String toDefaultString(Date source) {
		return toString(source, null);
	}

	public static Calendar toCalendar007788(Date date) {
		Calendar c = null;
		if (date != null) {
			c = Calendar.getInstance();
			c.setTime(date);
		}
		return c;
	}

	public static Date toEarliestOfDate(Date date) {
		Calendar c = toCalendar007788(date);
		if (c != null) {
			c.set(11, 0);
			c.set(12, 0);
			c.set(13, 0);
			c.set(14, 0);
			return c.getTime();
		}
		return null;
	}

	public static Date toLatestOfDate(Date date) {
		Calendar c = toCalendar007788(date);
		if (c != null) {
			c.set(11, 23);
			c.set(12, 59);
			c.set(13, 59);
			c.set(14, 999);
			return c.getTime();
		}
		return null;
	}

	public static String getTodayStart() {
		String todayDate = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date());
		String startTime = todayDate + " 00:00:00";
		return startTime;
	}

	public static String getTodayEnd() {
		String todayDate = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date());
		String endTime = todayDate + " 23:59:59";
		return endTime;
	}

	public static int getYear(Date date) {
		Calendar c = toCalendar007788(date);
		return null == c ? -1 : c.get(1);
	}

	public static int getMonth(Date date) {
		Calendar c = toCalendar007788(date);
		return null == c ? -1 : c.get(2) + 1;
	}

	public static int getHourOfDate(Date date) {
		Calendar c = toCalendar007788(date);
		return null == c ? -1 : c.get(10);
	}

	public static int getSecondOfDate(Date date) {
		Calendar c = toCalendar007788(date);
		return null == c ? -1 : c.get(13);
	}

	public static int getMinuteOfDate(Date date) {
		Calendar c = toCalendar007788(date);
		return null == c ? -1 : c.get(12);
	}

	public static int getMillSecondOfDate(Date date) {
		Calendar c = toCalendar007788(date);
		return null == c ? -1 : c.get(14);
	}

	public static boolean isFirstDayOfMonth(Date date) {
		Calendar c = toCalendar007788(date);
		return null != c;
	}

	public static Date addDate(Date date, int year, int month, int day) {
		Calendar c = toCalendar007788(date);
		if (c != null) {
			c.add(1, year);
			c.add(2, month);
			c.add(5, day);
			return c.getTime();
		}
		return null;
	}

	public static Date addTime(Date date, int hour, int minute, int second,
			int millisecond) {
		Calendar c = toCalendar007788(date);
		if (c != null) {
			c.add(11, hour);
			c.add(12, minute);
			c.add(13, second);
			c.add(14, millisecond);
			return c.getTime();
		}
		return null;
	}

	public static Date addDay(Date date, int days) {
		Calendar c = toCalendar007788(date);
		if (c != null) {
			c.add(5, days);
			return c.getTime();
		}
		return null;
	}

	public static Date getPreviousDate(Date date) {
		return addDay(date, -1);
	}

	public static Date getNextDate(Date date) {
		return addDay(date, 1);
	}

	public static boolean isTheSameDay(Date date1, Date date2) {
		String value1 = toString(date1, "yyyy-MM-dd HH:mm:ss");
		String value2 = toString(date2, "yyyy-MM-dd HH:mm:ss");
		return (value1 != null) && (value1.equals(value2));
	}

	public static Date toFirstMonthDay(Date date) {
		if (date != null) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.set(5, calendar.getActualMinimum(5));
			return calendar.getTime();
		}
		return null;
	}

	public static Date toLastMonthDay(Date date) {
		if (date != null) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.set(5, calendar.getActualMaximum(5));
			return calendar.getTime();
		}
		return null;
	}

	public static Date getWeekDate(Date date) {
		Calendar c = toCalendar007788(date);
		if (c != null) {
			int m = c.get(7);
			if (m - 1 == 0)
				c.add(7, -6);
			else {
				c.add(7, -(m - 2));
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			df.format(c.getTime());
			return c.getTime();
		}
		return null;
	}

	public static Date getMonthDate(Date date) {
		return getMonthDate(date, -2);
	}

	public static Date getMonthDate(Date date, int num) {
		Calendar c = toCalendar007788(date);
		if (c != null) {
			int m = num <= 0 ? c.get(5) - 1 : num;
			c.add(2, -m);
			return c.getTime();
		}
		return null;
	}

	public static Date getYearDate(Date date) {
		Calendar c = toCalendar007788(date);
		if (c != null) {
			c.add(1, -1);
			return c.getTime();
		}
		return null;
	}

	public static Integer naturalDaysBetween(Date beginDate, Date endDate) {
		if ((beginDate == null) || (endDate == null)) {
			return null;
		}
		long msPerDay = 86400000L;
		Calendar c1 = toCalendar007788(beginDate);
		Calendar c2 = toCalendar007788(endDate);
		long msDiff = c2.getTimeInMillis() - c1.getTimeInMillis();
		int days = (int) (msDiff / msPerDay);
		int msResidue = (int) (msDiff % msPerDay);
		Calendar c3 = Calendar.getInstance();
		c3.setTimeInMillis(c2.getTimeInMillis() - msResidue);
		Calendar c4 = (Calendar) c2.clone();
		c4.add(5, -1);
		if (c3.get(5) == c4.get(5)) {
			days++;
		} else {
			c4.add(5, 2);
			if (c3.get(5) == c4.get(5)) {
				days--;
			}
		}
		return Integer.valueOf(days);
	}

	public static Integer[] naturalDHMBetween(Date beginDate, Date endDate) {
		if ((beginDate == null) || (endDate == null)) {
			return null;
		}
		Integer[] dhm = new Integer[3];
		long leftSecond;
		long intervalSecond = leftSecond = (endDate.getTime() - beginDate
				.getTime()) / 1000L;
		dhm[0] = Integer.valueOf((int) intervalSecond / 86400);
		leftSecond -= dhm[0].intValue() * 60 * 60 * 24;
		dhm[1] = Integer.valueOf((int) leftSecond / 3600);
		leftSecond -= dhm[1].intValue() * 60 * 60;
		dhm[2] = Integer.valueOf((int) leftSecond / 60);
		return dhm;
	}

	// public static Date parseFullDateTime(String dateTime)
	// {
	// if (dateTime == null) {
	// return null;
	// }
	// try
	// {
	// return FORMAT_FULL_DATETIME.parse(dateTime);
	// } catch (ParseException e) {
	// throw new RuntimeException(new
	// StringBuilder().append("将字符串").append(dateTime).append("解析为").append(FORMAT_FULL_DATETIME.toPattern()).append("格式的日期时发生异常:").toString(),
	// e);
	// }
	// }
	//
	// public static Date parseDateFromLong(long dateTime)
	// {
	// if (dateTime < 19000101000000L) {
	// return null;
	// }
	// try
	// {
	// return FORMAT_FULL_DATETIME_LONG.parse(String.valueOf(dateTime));
	// } catch (ParseException e) {
	// throw new RuntimeException(new
	// StringBuilder().append("将数字").append(dateTime).append("解析为").append(FORMAT_FULL_DATETIME_LONG.toPattern()).append("格式的日期时发生异常:").toString(),
	// e);
	// }
	// }
	//
	// public static String formatFullDate(Date date)
	// {
	// return null == date ? "" : FORMAT_FULL_DATETIME.format(date);
	// }

	// public static long formatDateToLong(Date date)
	// {
	// String str = null == date ? null :
	// FORMAT_FULL_DATETIME_LONG.format(date);
	// return NumberUtils.parseLong(str);
	// }
	//
	// public static String getDateTimeByMillisecond(String str)
	// {
	// Date date = new Date(Long.valueOf(str).longValue());
	// return FORMAT_FULL_DATETIME.format(date);
	// }

	public static String getCountdownStringSimple(Date date) {
		if (date == null) {
			return "时间错误";
		}

		long d = date.getTime() - System.currentTimeMillis();
		if (d <= 0L) {
			return "已到期";
		}

		long base = 31104000000L;
		if (d >= base)
			return new StringBuilder().append(d / base).append("年").toString();
		base = 2592000000L;
		if (d >= base)
			return new StringBuilder().append(d / base).append("月").toString();
		base = 86400000L;
		if (d >= base)
			return new StringBuilder().append(d / base).append("天").toString();
		base = 3600000L;
		if (d >= base)
			return new StringBuilder().append(d / base).append("小时").toString();
		base = 60000L;
		if (d >= base)
			return new StringBuilder().append(d / base).append("分").toString();
		base = 1000L;
		if (d >= base)
			return new StringBuilder().append(d / base).append("秒").toString();
		return "已到期";
	}

	public static String getDateDiffStringSimple(Date date) {
		if (date == null)
			return "";
		long d = System.currentTimeMillis() - date.getTime();
		if (d < 0L) {
			d = 0L;
		}
		long base = 31104000000L;
		if (d >= base)
			return new StringBuilder().append(d / base).append("年").toString();
		base = 2592000000L;
		if (d >= base)
			return new StringBuilder().append(d / base).append("月").toString();
		base = 86400000L;
		if (d >= base)
			return new StringBuilder().append(d / base).append("天").toString();
		base = 3600000L;
		if (d >= base)
			return new StringBuilder().append(d / base).append("小时").toString();
		base = 60000L;
		if (d >= base)
			return new StringBuilder().append(d / base).append("分").toString();
		base = 1000L;
		if (d >= base)
			return new StringBuilder().append(d / base).append("秒").toString();
		return "";
	}

	public static boolean isGoodDate(int year, int month, int day) {
		if ((year < 1900) || (year > 2100) || (month < 1) || (month > 12)
				|| (day < 1) || (day > 31)) {
			return false;
		}

		if (((month == 2) || (month == 4) || (month == 6) || (month == 9) || (month == 11))
				&& (day == 31)) {
			return false;
		}

		if ((month == 2) && (day == 30)) {
			return false;
		}

		if ((month == 2) && (day == 29) && (!isRunYear(year))) {
			return false;
		}

		return true;
	}

	public static boolean isRunYear(int year) {
		return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
	}

	public static String surplusTime(Date startDate, Date endDate) {
		long surplus = (endDate.getTime() - startDate.getTime()) / 1000L;
		long secondNumOfOneDay = 86400L;
		StringBuilder sb = new StringBuilder();

		if (surplus < 0L) {
			sb.append("过期啦");
		} else if (surplus < 60L) {
			sb.append("剩余").append(surplus).append("秒");
		} else if (surplus < 3600L) {
			sb.append("剩余").append(surplus / 60L).append("分钟");
		} else if (surplus < secondNumOfOneDay) {
			sb.append("剩余").append(surplus / 3600L).append("小时");
			long mo = surplus % 3600L;
			if (mo >= 60L)
				sb.append(mo / 60L).append("分钟");
		} else {
			sb.append("剩余").append(surplus / secondNumOfOneDay).append("天");
			long mo = surplus % secondNumOfOneDay;
			if (mo >= 3600L) {
				sb.append(mo / 3600L).append("小时");
			}
		}

		return sb.toString();
	}

	public static String surplusTime(Date date) {
		return surplusTime(new Date(), date);
	}

	public static String getFileUploadDatePath() {
		String str = toString(new Date(), "yyyyMMdd");
		StringBuilder sb = new StringBuilder();
		sb.append(str.substring(0, 6));
		sb.append("/");
		sb.append(str.substring(6));
		sb.append("/");

		return sb.toString();
	}

	public static String formatDate(Date date) {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		return df.format(date);
	}

	/** 日期格式yyyy-MM-dd字符串常量 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/** 日期格式yyyy-MM-dd字符串常量 */
	public static final String DATE_FORMAT_HH = "HH";

	/** 日期格式yyyy-MM-dd HH:mm:ss字符串常量 */
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** 日期格式yyyy-MM字符串常量 */
	public static final String MONTH_FORMAT = "yyyy-MM";

	/** 某天开始时分秒字符串常量 00:00:00 */
	public static final String DAY_BEGIN_STRING_HHMMSS = " 00:00:00";

	/** 某天结束时分秒字符串常量 23:59:59 */
	public static final String DAY_END_STRING_HHMMSS = " 23:59:59";

	/** 一个月时间大约的long型数字 */
	public static final long MONTH_LONG = 2651224907l;

	/**
	 * 获取当前之前或者之后的某一周的礼拜几
	 * 
	 * @param before
	 *            之前(+)或之后(-)几周
	 * @param day
	 *            礼拜几:0为礼拜天，1为列表1以此类推
	 * @return
	 */
	public static Date getBeforeWeekDay(int before, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_MONTH, before);
		calendar.add(Calendar.DAY_OF_MONTH,
				-calendar.get(Calendar.DAY_OF_WEEK) + 1);// before周的礼拜天
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	/**
	 * 得到当前日期的前/后　beforeDays　天的日期数
	 * 
	 * @param beforeDays
	 * @return
	 */
	public static String getDateString(int beforeDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, beforeDays);
		String a = dateToString(c.getTime(), DATE_FORMAT);
		return a;
	}

	/**
	 * 得到当前日期日期数
	 * 
	 * @param beforeDays
	 * @return
	 */
	public static String getNowDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		String a = dateToString(c.getTime(), DATE_FORMAT);
		return a;
	}

	/**
	 * 得到指定日期
	 * 
	 * @param beforeDays
	 *            今天以前（负数）或以后（正数）,0为今天
	 * @return
	 */
	public static Date getDate(int beforeDays) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, beforeDays);
		return c.getTime();
	}

	/**
	 * 获取这礼拜天的日期
	 * 
	 * @return
	 */
	public static Date getThisWeekSunday() {
		Calendar c = Calendar.getInstance();
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		int beforeDays = 1 - weekday;
		c.add(Calendar.DAY_OF_MONTH, beforeDays);
		return c.getTime();
	}

	/**
	 * 获取上周礼拜的日期,-7为星期日，-1为星期六
	 * 
	 * @return
	 */
	public static Date getLastWeekDay(int beforeDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(getThisWeekSunday());
		c.add(Calendar.DAY_OF_MONTH, beforeDays);
		return c.getTime();
	}

	/**
	 * 获取本月第一天
	 * 
	 * @return
	 */
	public static Date getThisMonthFirstDay() {
		Calendar c = Calendar.getInstance();
		int beforeDays = c.get(Calendar.DATE);
		c.add(Calendar.DAY_OF_MONTH, -beforeDays + 1);
		return c.getTime();
	}

	/**
	 * 获取上月的第一天和最后一天
	 * 
	 * @param type
	 *            :第一天first,最后一天end
	 * @return
	 */
	public static Date getLastMonthFirstAndEndDay(String type) {
		Calendar c = Calendar.getInstance();
		int beforeDays = c.get(Calendar.DATE);
		c.add(Calendar.DAY_OF_MONTH, -beforeDays);
		if ("end".equals(type)) {
			return c.getTime();
		} else if ("first".equals(type)) {
			int dayCount = c.get(Calendar.DAY_OF_MONTH);
			c.add(Calendar.DAY_OF_MONTH, -dayCount + 1);
			return c.getTime();
		} else {
			return null;
		}
	}

	/**
	 * 得到当前日期的前/后　beforeDays　天的日期数,格式自定
	 * 
	 * @param beforeDays
	 * @param dateFormat
	 * @return
	 */
	public static String getDateString(int beforeDays, String dateFormat) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, beforeDays);
		String a = dateToString(c.getTime(), dateFormat);
		return a;
	}

	/**
	 * 将日期类型转换为yyyy-MM-dd字符串
	 * 
	 * @param dateValue
	 * @return String
	 */
	public static String dateToString(Date dateValue) {
		return dateToString(dateValue, DATETIME_FORMAT);
	}

	/**
	 * 将日期类型转换为指定格式的字符串
	 * 
	 * @param dateValue
	 * @param format
	 * @return String
	 */
	public static String dateToString(Date dateValue, String format) {
		if (dateValue == null || format == null) {
			return null;
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(dateValue);
		}
	}

	/**
	 * 将日期yyyy-MM-dd字符串转为日期类型，如果转换失败返回null
	 * 
	 * @param stringValue
	 * @return Date
	 */
	public static Date stringToDate(String stringValue) {
		return stringToDate(stringValue, DATE_FORMAT);
	}

	/**
	 * 将指定日期格式的字符串转为日期类型，如果转换失败返回null
	 * 
	 * @param stringValue
	 * @param format
	 * @return Date
	 */
	public static Date stringToDate(String stringValue, String format) {
		Date dateValue = null;
		if (stringValue != null && format != null) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				dateValue = dateFormat.parse(stringValue);

			} catch (ParseException ex) {
				dateValue = null;
			}
		}
		return dateValue;
	}

	/**
	 * 获得当前年
	 * 
	 * @return string
	 */
	public static String getNowYear() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		return String.valueOf(year);
	}

	/**
	 * 获得当前月
	 * 
	 * @return string
	 */
	public static String getNowMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month < 10) {
			return "0" + month;
		} else {
			return String.valueOf(month);
		}
	}

	/**
	 * 获得上一月
	 * 
	 * @return string
	 */
	public static String getLastMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		if (month < 10) {
			return "0" + month;
		} else {
			return String.valueOf(month);
		}
	}

	/**
	 * 获得当前日
	 * 
	 * @return string
	 */
	public static String getNowDay() {
		return dateToString(new Date(), "dd");

	}

	/**
	 * 昨天
	 * 
	 * @return
	 */
	public static String getYestday() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -1);
		return dateToString(date.getTime(), "dd");

	}

	/**
	 * 返回几个月前的Date类型
	 * 
	 * @param monthCount
	 *            几个月
	 * @return Date
	 */
	public static Date getDateFront(int monthCount) {
		return new Date(Calendar.getInstance().getTimeInMillis() - MONTH_LONG
				* monthCount);
	}

	/**
	 * 返回当前小时
	 * 
	 * @return string
	 */
	public static String getNowHour() {
		return dateToString(new Date(), "HH");
	}

	/**
	 * 返回当前分钟
	 * 
	 * @return string
	 */
	public static String getNowMinute() {
		return dateToString(new Date(), "mm");
	}

	/**
	 * 设置时间的日期值
	 * 
	 * @param stringDate
	 * @param num
	 * @return Date
	 */
	public static Date setDate(String stringDate, int num) {
		if (stringDate != null) {
			Date date = stringToDate(stringDate, "yyyy-MM-dd");
			return setDate(date, num);
		} else {
			return null;
		}
	}

	/**
	 * 设置时间的日期值
	 * 
	 * @param date
	 * @param num
	 * @return Date
	 */
	public static Date setDate(Date date, int num) {
		Date dateValue = null;
		Calendar c = null;
		if (date != null) {
			c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, num);
			dateValue = c.getTime();
		}
		return dateValue;
	}

	/**
	 * 取得两个日期的时间间隔,相差的天数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDayBetween(Date d1, Date d2) {
		Calendar before = Calendar.getInstance();
		Calendar after = Calendar.getInstance();
		if (d1.before(d2)) {
			before.setTime(d1);
			after.setTime(d2);
		} else {
			before.setTime(d2);
			after.setTime(d1);
		}
		int days = 0;

		int startDay = before.get(Calendar.DAY_OF_YEAR);
		int endDay = after.get(Calendar.DAY_OF_YEAR);

		int startYear = before.get(Calendar.YEAR);
		int endYear = after.get(Calendar.YEAR);
		before.clear();
		before.set(startYear, 0, 1);

		while (startYear != endYear) {
			before.set(startYear++, Calendar.DECEMBER, 31);
			days += before.get(Calendar.DAY_OF_YEAR);
		}
		return days + endDay - startDay;
	}

	public static Date addDateDay(Date myDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}

	public static Date getYesterday() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}

	/**
	 * 获取某个月的开始日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getSomeMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取某个月的结束日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getSomeMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, maxDay);
		return calendar.getTime();
	}

	/**
	 * 获取某个月附近的月份日期
	 * 
	 * @param date
	 * @param before
	 *            前+后-
	 * @return
	 */
	public static Date getSomeMonthDay(Date date, int before) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, before);
		return calendar.getTime();
	}

	/**
	 * 获取当前月附近的月份日期
	 * 
	 * @param before
	 *            前+后-
	 * @return
	 */
	public static Date getSomeMonthDay(int before) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, before);
		return calendar.getTime();
	}

	/**
	 * 獲得某個月的最大天數
	 */
	public static int maxDayOfMonth(String d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();

		Date date;
		try {
			date = sdf.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}

		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 獲得某個月的最大天數
	 */
	public static int maxDayOfMonth(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

	}

	/**
	 * 获得某一天的开始时点
	 * 
	 * @param d
	 * @return
	 */
	public static Date getDayBegin(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 获得某月的开始时点
	 * 
	 * @param d
	 * @return
	 */
	public static Date getMonthBegin(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	/**
	 * @param d
	 * @return
	 */
	public static Date getDayEnd(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime();
	}

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static Date getBeginOfDay(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(11, 0);
			cal.set(12, 0);
			cal.set(13, 0);
			cal.set(14, 0);
			return cal.getTime();
		}
	}

	public static Date getEndOfDay(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(11, 23);
			cal.set(12, 59);
			cal.set(13, 59);
			cal.set(14, 999);
			return cal.getTime();
		}
	}

	public static Date parseDayDate(String day) {
		if (day == null)
			return null;
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = df.parse(day);
		} catch (ParseException e) {
		}
		return date;
	}

	public static Date parseDayDate(String day, String srcFormat) {
		if (day == null)
			return null;
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat(srcFormat);
		try {
			date = df.parse(day);
		} catch (ParseException e) {
		}
		return date;
	}

	/**
	 * parseDayDate("2011-06-08 14:02:43", "yyyy-MM-dd HH:mm:ss",
	 * "yyyyMMdd")-->20110608
	 * 
	 * @param date
	 * @param srcFormat
	 * @param targetFormat
	 * @return
	 */
	public static String parseDayDate(String date, String srcFormat,
			String targetFormat) {
		String target;
		Date srcDate = parseDayDate(date, srcFormat);

		target = format(srcDate, targetFormat);

		return target;
	}

	public static String formatSourceDate(Date date) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(date);
		}
	}

	public static String format3Date(Date date) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			return df.format(date);
		}
	}

	public static String format2Date(Date date) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(date);
		}
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format(date);
		}
	}

	public static boolean isFinish(Date date) {
		boolean isFinish = false;
		Calendar cal = Calendar.getInstance();
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		long currentDay = cal.getTimeInMillis();
		if (date != null && date.getTime() < currentDay)
			isFinish = true;
		return isFinish;
	}

	public static Date parseString2Date(String dateStr) {
		if (dateStr == null)
			return null;
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getBeginOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(5, 1);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	public static Date getEndOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(2, cal.get(2) + 1);
		cal.set(5, 1);
		cal.set(5, cal.get(5) - 1);
		cal.set(10, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		cal.set(14, 999);
		return cal.getTime();
	}

	public static String getDate() {
		return getDate(YYYY_MM_DD_HH_MM_SS);
	}

	public static String getDate(String format) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);

	}

	public static Timestamp getTimestamp() {
		return new Timestamp((new Date()).getTime());
	}

	public static boolean isBetween(Date startDate, Date endDate, Date now) {
		if ((now.equals(startDate) || now.after(startDate))
				&& (now.equals(endDate) || now.before(endDate))) {
			return true;
		}
		return false;
	}
	
	 /** 
     *  
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度 
     *  
     * @param date 
     * @return 
     */  
    public static int getSeason(Date date) {  
  
        int season = 0;  
  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        int month = c.get(Calendar.MONTH);  
        switch (month) {  
        case Calendar.JANUARY:  
        case Calendar.FEBRUARY:  
        case Calendar.MARCH:  
            season = 1;  
            break;  
        case Calendar.APRIL:  
        case Calendar.MAY:  
        case Calendar.JUNE:  
            season = 2;  
            break;  
        case Calendar.JULY:  
        case Calendar.AUGUST:  
        case Calendar.SEPTEMBER:  
            season = 3;  
            break;  
        case Calendar.OCTOBER:  
        case Calendar.NOVEMBER:  
        case Calendar.DECEMBER:  
            season = 4;  
            break;  
        default:  
            break;  
        }  
        return season;  
    }  
    public  static  int  dayForWeek(String pTime) throws ParseException      {  
    	SimpleDateFormat  format = new  SimpleDateFormat("yyyy-MM-dd" );  
    	 Calendar c = Calendar.getInstance();  
    	 c.setTime(format.parse(pTime));  
    	 int  dayForWeek = 0 ;  
    	 if (c.get(Calendar.DAY_OF_WEEK) == 1 ){  
    	  dayForWeek = 7 ;  
    	 }else {  
    	  dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1 ;  
    	 }  
    	 return  dayForWeek;  
    	}  
    
    
    /**
     * 获取最后开盘时间(下午3点前取前一天，周末取星期五，不包含特殊节假日)
     * @return
     */
    public static String getStockDate(){
    	SimpleDateFormat  format = new  SimpleDateFormat("yyyy-MM-dd" );  
    	Calendar ncalendar = new GregorianCalendar();
    	TimeZone tz = TimeZone.getTimeZone("GMT+8");
    	ncalendar.setTimeZone(tz);
    	int w = ncalendar.get(Calendar.DAY_OF_WEEK) - 1;
    	if(w > 5){
    		ncalendar.add(Calendar.DATE, 5-w);
    		return format.format(ncalendar.getTime());
    	}
    	int H = ncalendar.get(Calendar.HOUR_OF_DAY);
    	if(H < 15 && w == 1){
    		ncalendar.add(Calendar.DATE,-3);
    	}else if(H < 15 && w != 1){
    		ncalendar.add(Calendar.DATE,-1);
    	}
    	return format.format(ncalendar.getTime());
    }
    
    public static String getaLstTradeDate(){
		 
		 
		SimpleDateFormat  format = new  SimpleDateFormat("yyyy-MM-dd" );  
    	 Calendar c = Calendar.getInstance();  
    	 c.setTime(new Date());  
    	 int  dayForWeek = 0 ;  
    	 if (c.get(Calendar.DAY_OF_WEEK) == 1 ){  
    	  dayForWeek = 7 ;  
    	 }else {  
    	  dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1 ;  
    	 }  
    	   int  uDay=-1;
    	  if(dayForWeek==7)
    		  uDay=-2;
    	  if(dayForWeek==1)
    		  uDay=-3;
    	 return   format.format(addDay(new Date(), uDay));
    } 
    
    /**
     * 日期转星期
     * 
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_DATEYMD_FORMAT);
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
    public static String addSubDay(String time , int num){
    	SimpleDateFormat f = new SimpleDateFormat(DEFAULT_DATEYMD_FORMAT);
    	Calendar cal = Calendar.getInstance(); // 获得一个日历
    	 Date datet = null;
         try {
        	 if(null != time){
        		 datet = f.parse(time);
        	 }else{
        		 datet = new Date(); 
        	 }
             cal.setTime(datet);
             cal.add(Calendar.DATE,num);
         } catch (ParseException e) {
             e.printStackTrace();
         }
    	return f.format(cal.getTime());
    }
    
    public static  boolean tradeTime(){
		Calendar ncalendar = Calendar.getInstance();
		int H = ncalendar.get(Calendar.HOUR_OF_DAY);
		int M = ncalendar.get(Calendar.MINUTE);
		int w = ncalendar.get(Calendar.DAY_OF_WEEK) - 2;
		boolean k = true;
		if(((H == 9 && M >= 30) || (H==10) || (H==11 && M <= 30) || (H==13) || (H==14)) 
				&& w !=6 && w !=7){
			k = true;
		}else{
			k = false;
		}
		return k;
	}
    
    public static long diffTime(String t1 , String t2){
    	SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
    	long n = 0;
    	try {
    		Date d1 = df.parse(t1);
    		Date d2 = df.parse(t2);
    		n = d1.getTime() - d2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return n;
	}
}
