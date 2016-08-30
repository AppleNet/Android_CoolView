package com.llc.android_coolview.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.regex.Pattern;

import android.text.TextUtils;

public class DateUtils {

	public static final int DATETIME_FIELD_REFERSH = 20;
	public static final String HH_mm = "HH:mm";
	public static final String HH_mm_ss = "HH:mm:ss";
	public static final String MM_Yue_dd_Ri = "MM月dd日";
	public static final String MM_yy = "MM/yy";
	public static final String M_Yue_d_Ri = "M月d日";
	public static final long ONE_DAY = 86400000L;
	public static final long ONE_HOUR = 3600000L;
	public static final long ONE_MINUTE = 60000L;
	public static final long ONE_SECOND = 1000L;
	private static final String[] PATTERNS = { "yyyy-MM-dd HH:mm:ss",
			"yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyyMMdd" };
	public static final String dd_MM = "dd/MM";
	public static boolean hasServerTime = false;
	public static long tslgapm = 0L;
	public static String tss;
	private static String[] weekdays = { "", "周日", "周一", "周二", "周三", "周四", "周五",
			"周六" };
	private static String[] weekdays1 = { "", "星期日", "星期一", "星期二", "星期三", "星期四",
			"星期五", "星期六" };
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String yyyy_MM = "yyyy-MM";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyy_Nian_MM_Yue_dd_Ri = "yyyy年MM月dd日";

	/**
	 * 判断一年是否为闰年
	 */
	public static boolean IsLeapYear(int year) {
		return (year % 400 == 0 || year % 4 == 0 && year % 100 != 0);
	}

	/**
	 * 获得某一年的总天数：（闰年366天，非闰年355）
	 *
	 */
	public static int GetAllDays(int year) {
		return (IsLeapYear(year) ? 366 : 365);
	}

	/**
	 * 获得某年、某月的最大天数
	 *
	 */
	public static int GetMaxDay(int year, int month) {
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 2:
				return (IsLeapYear(year) ? 29 : 28);
			default:
				return -1;
		}
	}

	/**
	 * 获得某年、某月、某日是这一年的第几天
	 *
	 */
	public static int GetDays(int year, int month, int day) {
		int sum = 0;
		for (int i = 1; i < month; i++) {
			sum += GetMaxDay(year, i);
		}
		return sum + day;
	}

	/**
	 * 获得某年某月某日的下一天
	 *
	 */
	public static void GetNextDay(int year, int month, int day) {
		if (day != GetMaxDay(year, month)) {
			day++;
		} else {
			if (month != 12) {
				month++;
				day = 1;
			} else {
				year++;
				month = day = 1;
			}
		}
	}

	/**
	 * 获得某年某月某日n天之后的日期
	 *
	 */
	public static void GetXDay(int year, int month, int day, int X) {
		for (int i = 1; i <= X; i++) {
			if (day != GetMaxDay(year, month)) {
				day++;
			} else {
				if (month != 12) {
					month++;
					day = 1;
				} else {
					year++;
					month = day = 1;
				}
			}
		}
		System.out.println(X + " 天后是：" + year + "-" + month + "-" + day);
	}

	/**
	 * 比较日期大小
	 *
	 */
	public static int CompareDay(int year1, int month1, int day1, int year2,
								 int month2, int day2) {
		return year1 != year2 ? (year1 - year2)
				: (month1 != month2 ? (month1 - month2) : day1 - day2);
	}

	/**
	 * 求日期之差
	 *
	 */
	public static int GetDateDiff1(int year1, int month1, int day1, int year2,
								   int month2, int day2) {
		int sum = GetAllDays(year1) - GetDays(year1, month1, day1);
		for (int i = year1 + 1; i < year2; i++) {
			sum += GetAllDays(i);
		}
		sum += GetDays(year2, month2, day2);
		return sum;
	}

	public static int GetDateDiff(int year1, int month1, int day1, int year2,
								  int month2, int day2) {
		if (CompareDay(year1, month1, day1, year2, month2, day2) > 0) {
			return -GetDateDiff1(year2, month2, day2, year1, month1, day1);
		} else if (CompareDay(year1, month1, day1, year2, month2, day2) < 0) {
			return GetDateDiff1(year1, month1, day1, year2, month2, day2);
		}
		return 0;
	}

	/**
	 * 推算本周日期
	 *
	 * @return
	 */
	public static HashMap<String, String> computeDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		HashMap<String, String> map = new HashMap<String, String>();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
		map.put("MONDAY", df.format(cal.getTime()));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY); // 获取本周二的日期
		map.put("TUESDAY", df.format(cal.getTime()));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY); // 获取本周三的日期
		map.put("WEDNESDAY", df.format(cal.getTime()));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY); // 获取本周四的日期
		map.put("THURSDAY", df.format(cal.getTime()));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY); // 获取本周五的日期
		map.put("FRIDAY", df.format(cal.getTime()));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY); // 获取本周六的日期
		map.put("SATURDAY", df.format(cal.getTime()));
		// 这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		map.put("SUNDAY", df.format(cal.getTime()));
		return map;
	}

	/**
	 * 根据日期推算星期
	 *
	 * @param date
	 */
	public static String computeWeek(String date) {

		Calendar calendar = Calendar.getInstance();

		int year = Integer.valueOf(date.substring(0, date.indexOf("-")));
		int month = Integer.valueOf(
				date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
		int day = Integer.valueOf(date.substring(date.lastIndexOf("-") + 1));
		calendar.set(year, month - 1, day);
		int number = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		String[] str = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

		return str[number];
	}

	/**
	 * 根据日期推算星期
	 *
	 * @param date
	 */
	public static String computeWeekE(String date) {

		Calendar calendar = Calendar.getInstance();

		int year = Integer.valueOf(date.substring(0, date.indexOf("-")));
		int month = Integer.valueOf(
				date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
		int day = Integer.valueOf(date.substring(date.lastIndexOf("-") + 1));
		calendar.set(year, month - 1, day);
		int number = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		String[] str = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY",
				"FRIDAY", "SATURDAY" };

		return str[number];
	}

	/**
	 * 根据日期推算星期
	 *
	 * @param date
	 */
	public static String computeWeekN(String date) {

		Calendar calendar = Calendar.getInstance();

		int year = Integer.valueOf(date.substring(0, date.indexOf("-")));
		int month = Integer.valueOf(
				date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
		int day = Integer.valueOf(date.substring(date.lastIndexOf("-") + 1));
		calendar.set(year, month - 1, day);
		int number = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		String[] str = { "7", "1", "2", "3", "4", "5", "6" };

		return str[number];
	}

	/**
	 * 根据日期推算星期
	 *
	 * @param date
	 */
	public static String computeWeekC(int number) {
		String[] str = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		return str[number];
	}

	/**
	 * 解析一个日期之间的所有月份
	 *
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	public static ArrayList<String> getMonthList(String beginDateStr,
												 String endDateStr) {
		// 指定要解析的时间格式
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM",
				Locale.getDefault());
		// 返回的月份列表
		String sRet = "";

		// 定义一些变量
		Date beginDate = null;
		Date endDate = null;

		GregorianCalendar beginGC = null;
		GregorianCalendar endGC = null;
		ArrayList<String> list = new ArrayList<String>();

		try {
			// 将字符串parse成日期
			beginDate = f.parse(beginDateStr);
			endDate = f.parse(endDateStr);

			// 设置日历
			beginGC = new GregorianCalendar();
			beginGC.setTime(beginDate);

			endGC = new GregorianCalendar();
			endGC.setTime(endDate);

			// 直到两个时间相同
			while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {
				sRet = beginGC.get(Calendar.YEAR) + "-"
						+ (beginGC.get(Calendar.MONTH) + 1);
				list.add(sRet);
				// 以月为单位，增加时间
				beginGC.add(Calendar.MONTH, 1);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析一个日期段之间的所有日期
	 *
	 * @param beginDateStr
	 *            开始日期
	 * @param endDateStr
	 *            结束日期
	 * @return
	 */
	public static ArrayList<String> getDayList(String beginDateStr,
											   String endDateStr) {
		// 指定要解析的时间格式
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());

		// 定义一些变量
		Date beginDate = null;
		Date endDate = null;

		Calendar beginGC = null;
		Calendar endGC = null;
		ArrayList<String> list = new ArrayList<String>();

		try {
			// 将字符串parse成日期
			beginDate = f.parse(beginDateStr);
			endDate = f.parse(endDateStr);

			// 设置日历
			beginGC = Calendar.getInstance();
			beginGC.setTime(beginDate);

			endGC = Calendar.getInstance();
			endGC.setTime(endDate);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
					Locale.getDefault());

			// 直到两个时间相同
			while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {

				list.add(sdf.format(beginGC.getTime()));
				// 以日为单位，增加时间
				beginGC.add(Calendar.DAY_OF_MONTH, 1);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到年份的集合
	 *
	 */
	public static ArrayList<Integer> getYearList() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Calendar c = null;
		c = Calendar.getInstance();
		c.setTime(new Date());
		int currYear = Calendar.getInstance().get(Calendar.YEAR);

		int startYear = currYear - 5;
		int endYear = currYear + 10;
		for (int i = startYear; i < endYear; i++) {
			list.add(new Integer(i));
		}
		return list;
	}

	/**
	 * 得到当前年份
	 *
	 */
	public static int getCurrYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 得到某一年周的总数
	 *
	 * @param year
	 * @return
	 */
	public static LinkedHashMap<Integer, String> getWeekList(int year) {
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		int count = getWeekOfYear(c.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		String dayOfWeekStart = "";
		String dayOfWeekEnd = "";
		for (int i = 1; i <= count; i++) {
			dayOfWeekStart = sdf.format(getFirstDayOfWeek(year, i));
			dayOfWeekEnd = sdf.format(getLastDayOfWeek(year, i));
			map.put(new Integer(i), "第" + i + "周(从" + dayOfWeekStart + "至"
					+ dayOfWeekEnd + ")");
		}
		return map;

	}

	/**
	 * 得到一年的总周数
	 *
	 * @param year
	 * @return
	 */
	public static int getWeekCountInYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		int count = getWeekOfYear(c.getTime());
		return count;
	}

	/**
	 * 取得当前日期是多少周
	 *
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 得到某年某周的第一天
	 *
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONDAY, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某周的最后一天
	 *
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONDAY, 0);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某月的第一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirestDayOfMonth(int year, int month) {
		month = month - 1;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		@SuppressWarnings("static-access")
		int day = c.getActualMinimum(c.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, day);
		return c.getTime();

	}

	/**
	 * 提到某年某月的最后一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		month = month - 1;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		@SuppressWarnings("static-access")
		int day = c.getActualMaximum(c.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}

	/**
	 * 取得当前日期所在周的第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/**
	 * 得到某年某季度第一天
	 *
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getFirstDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = (quarter - 1) * 3 + 1;
		}
		return getFirstDayOfMonth(year, month);
	}

	/**
	 * 得到某年某季度最后一天
	 *
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getLastDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = quarter * 3;
		}
		return getLastDayOfMonth(year, month);
	}

	/**
	 * 得到某年第一天
	 *
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfYear(int year) {
		return getFirstDayOfQuarter(year, 1);
	}

	/**
	 * 得到某年最后一天
	 *
	 * @param year
	 * @return
	 */
	public static Date getLastDayOfYear(int year) {
		return getLastDayOfQuarter(year, 4);
	}

	/**
	 * 得到某年某月的第一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));

		return c.getTime();
	}

	/**
	 * 获取某年某月某一周的第一天和最后一天（flag=true表示取第一天）
	 *
	 * @param year
	 * @param month
	 * @param week
	 * @param flag
	 * @return
	 */
	public static String getDayByWeek(int year, int month, int week,
									  boolean flag) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.WEEK_OF_MONTH, week);
		int dw = cal.get(Calendar.DAY_OF_WEEK);
		if (!flag)
			cal.setTimeInMillis(
					cal.getTimeInMillis() + (8 - dw) * 24 * 60 * 60 * 1000);
		else
			cal.setTimeInMillis(
					cal.getTimeInMillis() - (dw - 2) * 24 * 60 * 60 * 1000);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		String showTime = formatter.format(cal.getTime());
		return showTime.toString();
	}

	public static String getStringToDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		return sdf.format(date);
	}

	/**
	 * 转化字符串时间格式
	 *
	 * @param strTime
	 * @return
	 * @throws ParseException
	 */
	public static String formatTimeString(String strTime, String format) {
		SimpleDateFormat getsdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.sss", Locale.getDefault());
		SimpleDateFormat resdf = new SimpleDateFormat(format);
		try {
			return resdf.format(getsdf.parse(strTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取某一个月的周数
	 *
	 * @param date
	 * @throws ParseException
	 * @throws Exception
	 */
	public static int getMonthWeek(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM",
				Locale.getDefault());
		Date s = sdf.parse(date);
		Calendar ca = Calendar.getInstance();
		ca.setTime(s);
		ca.setFirstDayOfWeek(Calendar.MONDAY);
		int weeks = ca.getActualMaximum(Calendar.WEEK_OF_MONTH);
		return weeks;
	};

	/**
	 * 获取当前日期，自定义格式类型
	 *
	 */
	public static String getCurrentDay(String format) {
		SimpleDateFormat getsdf = new SimpleDateFormat(format,
				Locale.getDefault());
		return getsdf.format(new Date());
	}

	/**
	 * 获取当前日期的前几天或者后几天
	 *
	 * @param day
	 *            传整数为后几天 传负数为前几天
	 */
	public static String getAfterDay(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, day);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",
				Locale.getDefault()); // 设置时间格式
		return "day" + "_" + sdf.format(calendar.getTime());
	}

	/**
	 * 获得指定日期的前一天
	 *
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 *
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}

	public static void cleanCalendarTime(Calendar paramCalendar) {
		paramCalendar.set(11, 0);
		paramCalendar.set(12, 0);
		paramCalendar.set(13, 0);
		paramCalendar.set(14, 0);
	}

	private static String fixDateString(String paramString) {
		if (TextUtils.isEmpty(paramString))
			return paramString;
		String[] arrayOfString = paramString.split("[年月日]");
		if (arrayOfString.length == 1)
			arrayOfString = paramString.split("-");
		for (int i = 0; i < 3; i++) {
			if (arrayOfString[i].length() != 1)
				continue;
			arrayOfString[i] = ("0" + arrayOfString[i]);
		}
		return arrayOfString[0] + "-" + arrayOfString[1] + "-"
				+ arrayOfString[2];
	}

	public static <T> Calendar getCalendar(T paramT) {
		Calendar localCalendar1 = Calendar.getInstance();
		localCalendar1.setLenient(false);
		if (paramT == null)
			return null;
		if ((paramT instanceof Calendar)) {
			localCalendar1
					.setTimeInMillis(((Calendar) paramT).getTimeInMillis());
			return localCalendar1;
		}
		if ((paramT instanceof Date)) {
			localCalendar1.setTime((Date) paramT);
			return localCalendar1;
		}
		if ((paramT instanceof Long)) {
			localCalendar1.setTimeInMillis(((Long) paramT).longValue());
			return localCalendar1;
		}
		if ((paramT instanceof String)) {
			String str = (String) paramT;
			try {
				if (Pattern.compile("\\d{4}年\\d{1,2}月\\d{1,2}日").matcher(str)
						.find()) {
					str = fixDateString(str);
					return getCalendarByPattern(str, "yyyy-MM-dd");
				}
				Calendar localCalendar2 = getCalendarByPatterns(str, PATTERNS);
				return localCalendar2;
			} catch (Exception localException) {
				try {
					localCalendar1
							.setTimeInMillis(Long.valueOf(str).longValue());
					return localCalendar1;
				} catch (NumberFormatException localNumberFormatException) {
					throw new IllegalArgumentException(
							localNumberFormatException);
				}
			}
		}
		throw new IllegalArgumentException();
	}

	public static <T> Calendar getCalendar(T paramT, Calendar paramCalendar) {
		if (paramT != null)
			try {
				Calendar localCalendar = getCalendar(paramT);
				return localCalendar;
			} catch (Exception localException) {
			}
		return (Calendar) paramCalendar.clone();
	}

	public static Calendar getCalendarByPattern(String paramString1,
												String paramString2) {
		try {
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
					paramString2, Locale.US);
			localSimpleDateFormat.setLenient(false);
			Date localDate = localSimpleDateFormat.parse(paramString1);
			Calendar localCalendar = Calendar.getInstance();
			localCalendar.setLenient(false);
			localCalendar.setTimeInMillis(localDate.getTime());
			return localCalendar;
		} catch (Exception localException) {
			throw new IllegalArgumentException(localException);
		}

	}

	public static Calendar getCalendarByPatterns(String paramString,
												 String[] paramArrayOfString) {
		int i = paramArrayOfString.length;
		int j = 0;
		while (j < i) {
			String str = paramArrayOfString[j];
			try {
				Calendar localCalendar = getCalendarByPattern(paramString, str);
				return localCalendar;
			} catch (Exception localException) {
				j++;
			}
		}
		throw new IllegalArgumentException();
	}

	public static Calendar getCurrentDateTime() {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setLenient(false);
		if (hasServerTime)
			localCalendar
					.setTimeInMillis(localCalendar.getTimeInMillis() + tslgapm);
		return localCalendar;
	}

	public static Calendar getDateAdd(Calendar paramCalendar, int paramInt) {
		if (paramCalendar == null)
			return null;
		Calendar localCalendar = (Calendar) paramCalendar.clone();
		localCalendar.add(5, paramInt);
		return localCalendar;
	}

	public static <T> int getIntervalDays(T paramT1, T paramT2) {
		Calendar localCalendar1 = getCalendar(paramT1);
		Calendar localCalendar2 = getCalendar(paramT2);
		cleanCalendarTime(localCalendar1);
		cleanCalendarTime(localCalendar2);
		return (int) getIntervalTimes(localCalendar1, localCalendar2,
				86400000L);
	}

	public static int getIntervalDays(String paramString1, String paramString2,
									  String paramString3) {
		if ((paramString1 == null) || (paramString2 == null))
			return 0;
		return getIntervalDays(getCalendarByPattern(paramString1, paramString3),
				getCalendarByPattern(paramString2, paramString3));
	}

	public static long getIntervalTimes(Calendar paramCalendar1,
										Calendar paramCalendar2, long paramLong) {
		if ((paramCalendar1 == null) || (paramCalendar2 == null))
			return 0L;
		return Math.abs(paramCalendar1.getTimeInMillis()
				- paramCalendar2.getTimeInMillis()) / paramLong;
	}

	public static Calendar getLoginServerDate() {
		return getCalendar(tss);
	}

	public static String getWeekDayFromCalendar(Calendar paramCalendar) {
		if (paramCalendar == null)
			throw new IllegalArgumentException();
		return weekdays[paramCalendar.get(7)];
	}

	public static String getWeekDayFromCalendar1(Calendar paramCalendar) {
		if (paramCalendar == null)
			throw new IllegalArgumentException();
		return weekdays1[paramCalendar.get(7)];
	}

	public static boolean isLeapyear(String paramString) {
		Calendar localCalendar = getCalendar(paramString);
		if (localCalendar != null) {
			int i = localCalendar.get(1);
			return (i % 4 == 0) && ((i % 100 != 0) || (i % 400 == 0));
		}
		return false;
	}

	public static boolean isRefersh(long paramLong) {
		return isRefersh(1200000L, paramLong);
	}

	public static boolean isRefersh(long paramLong1, long paramLong2) {
		return new Date().getTime() - paramLong2 >= paramLong1;
	}

	public static String printCalendarByPattern(Calendar paramCalendar,
												String paramString) {
		if ((paramCalendar == null) || (paramString == null))
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				paramString, Locale.US);
		localSimpleDateFormat.setLenient(false);
		return localSimpleDateFormat.format(paramCalendar.getTime());
	}

	public static String computeDateTime(String beginTime, String endTime) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		try {
			Date now = df.parse(beginTime);
			Date date = df.parse(endTime);
			long l = now.getTime() - date.getTime();
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			return hour + "小时" + min + "分";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
