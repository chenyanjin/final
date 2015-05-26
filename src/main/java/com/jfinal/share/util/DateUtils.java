package com.jfinal.share.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间处理工具
 * 
 * @author 風佑兲 (375910297@.qq.com)
 * @date 2014年7月30日 下午8:53:05
 */
public class DateUtils {
	private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

	private static SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

	private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static SimpleDateFormat nowFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 获得当前年份
	public static String getCurrentYear() {
		return yearFormat.format(new Date());
	}

	// 获得当前月份
	public static String getCurrentMonth() {
		return monthFormat.format(new Date());
	}

	// 获得当前日期 精确到天
	public static String getCurrentDay() {
		return dayFormat.format(new Date());
	}

	// 获得上个月份
	public static String getLastMonth() {
		Calendar a = Calendar.getInstance();
		a.setTime(new Date());
		a.set(Calendar.DAY_OF_MONTH, -1);
		return monthFormat.format(a.getTime());
	}

	/**
	 * 获得前day天的日期
	 * 
	 * @param day
	 * @return
	 */
	public static String getDayBefore(int day) {
		Calendar a = Calendar.getInstance();
		a.setTime(new Date());
		a.add(Calendar.DATE, -day);
		return nowFormat.format(a.getTime());
	}

	// 获得当前时间 精确到秒
	public static String getCurrentTime() {
		return nowFormat.format(new Date());
	}

	public static List<String> getYearMonth(String year) {
		if (null == year || "".equals(year.trim())) {
			year = yearFormat.format(new Date());
		}

		List<String> yearMonth = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			yearMonth.add(year + "-" + String.format("%02d", i));
		}
		return yearMonth;
	}

	public static List<String> getPerMonthByCuttentYear() {
		String year = getCurrentYear();
		Calendar a = Calendar.getInstance();
		int month = a.get(Calendar.MONTH);
		List<String> months = new ArrayList<String>();
		for (int i = month; i > 0; i--) {
			months.add(year + "-" + String.format("%02d", i));
		}
		return months;
	}

	public static List<String> getYearList(int size) {
		String currentYear = DateUtils.getCurrentYear();
		int year = Integer.parseInt(currentYear);
		List<String> years = new ArrayList<String>();
		for (int i = year - size; i <= year + size; i++) {
			years.add(String.valueOf(i));
		}
		return years;
	}
}
