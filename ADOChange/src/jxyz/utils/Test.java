package jxyz.utils;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import jxyz.Application;

public class Test {
	
	public static Map<String, Object> GLOBAL_PARAM = new HashMap<String, Object>();
	
	public static final String CURR_YEAR = "curr_year";
	public static final String LAST_YEAR = "last_year";
	public static final String CURR_DAY = "curr_day";
	public static final String LAST_DAY = "last_day";
	public static final String CURR_MONTH = "curr_month";

	public static final String CURR_MONTH_FIRSTDAY = "curr_month_first_day";
	public static final String CURR_MONTH_LASTDAY = "curr_month_last_day";

	public static final String LAST_MONTH_FIRSTDAY = "last_month_first_day";
	public static final String LAST_MONTH_LASTDAY = "last_month_last_day";

	public static final String DEL_CURR_MONTH_START = "del_curr_month_start";
	public static final String DEL_CURR_MONTH_END = "del_curr_month_end";

	public static final String LAST_YEAR_SAME_DATE = "last_year_same_date";
	public static final String LAST_YEAR_SAME_MONTH_START = "last_year_same_month_start";
	public static final String LAST_YEAR_START = "last_year_start";
	public static final String CURR_YEAR_START = "curr_year_start";

	public static void main(String[] args) {
		
		String date = "2020-09-01";
		Date targetDate = TimeUtil.stringToDate(date);
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		c.setTime(targetDate);
		for (int i = 0; i < 30; ++i) {
            System.out.println("================计算日期【" + TimeUtil.translateDate(c.getTime()) + "】开始===========");
            test(c.getTime());
            System.out.println("================计算结束===========");
            c.add(Calendar.DATE, 1);
		}
		
	
	}
	
	public static void test(Date targetDate) {
		// 设置全局变量
		// 计算当年
		Calendar cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cale.setTime(targetDate);
		GLOBAL_PARAM.put(CURR_YEAR, TimeUtil.translateDate(cale.getTime(), "yyyy")); // 当前年
		cale.add(Calendar.YEAR, -1);
		GLOBAL_PARAM.put(LAST_YEAR, TimeUtil.translateDate(cale.getTime(), "yyyy")); // 去年
		cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cale.setTime(targetDate);
		cale.set(Calendar.HOUR_OF_DAY, 0);
		cale.set(Calendar.MINUTE, 0);
		cale.set(Calendar.SECOND, 0);
		GLOBAL_PARAM.put(LAST_DAY, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd HH:mm:ss")); // 当天凌晨0点
		cale.add(Calendar.DATE, 1);
		GLOBAL_PARAM.put(CURR_DAY, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd HH:mm:ss")); // 第二天凌晨0点

		cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cale.setTime(targetDate);
		GLOBAL_PARAM.put(CURR_MONTH, TimeUtil.translateDate(cale.getTime(), "yyyyMM")); // 本月
		// 计算本月第一天
		cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cale.setTime(targetDate);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		GLOBAL_PARAM.put(CURR_MONTH_FIRSTDAY, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));
		GLOBAL_PARAM.put(DEL_CURR_MONTH_START, TimeUtil.translateDate(cale.getTime(), "yyyyMM"));
		// 本月今天
		cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cale.setTime(targetDate);
		GLOBAL_PARAM.put(CURR_MONTH_LASTDAY, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));
		GLOBAL_PARAM.put(DEL_CURR_MONTH_END, TimeUtil.translateDate(cale.getTime(), "yyyyMM"));
		// 计算上月第一天
		cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cale.setTime(targetDate);
		cale.add(Calendar.MONTH, -1);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		GLOBAL_PARAM.put(LAST_MONTH_FIRSTDAY, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));
		// 计算上月同期
		cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cale.setTime(targetDate);
		cale.add(Calendar.MONTH, -1);
		GLOBAL_PARAM.put(LAST_MONTH_LASTDAY, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));

		// 计算去年同期
		cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cale.setTime(targetDate);
		cale.add(Calendar.YEAR, -1);
		GLOBAL_PARAM.put(LAST_YEAR_SAME_DATE, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));
		// 计算去年同月1号
		cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cale.setTime(targetDate);
		cale.add(Calendar.YEAR, -1);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		GLOBAL_PARAM.put(LAST_YEAR_SAME_MONTH_START, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));
		// 计算去年开始
		cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cale.setTime(targetDate);
		cale.add(Calendar.YEAR, -1);
		cale.set(Calendar.DAY_OF_YEAR, 1);
		GLOBAL_PARAM.put(LAST_YEAR_START, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));
		// 计算今年开始
		cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cale.setTime(targetDate);
		cale.set(Calendar.DAY_OF_YEAR, 1);
		GLOBAL_PARAM.put(CURR_YEAR_START, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));


		for (String key : GLOBAL_PARAM.keySet()) {
			System.out.println("GLOBALPARAM[" + key + "]: " + GLOBAL_PARAM.get(key));
		}

	}

}
