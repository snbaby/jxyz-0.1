package com.isoftstone.jxyz.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

	public static DateFormat df() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public static String translateDateStr(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return df().format(df().parse(obj.toString()));
		} catch (Exception e) {
			return null;
		}
	}

	public static String getYearMonth() {
		// 获取当前时间
		LocalDateTime nowTime = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter =   DateTimeFormatter.ofPattern("yyyyMM");
		return nowTime.format(dateTimeFormatter);
	}
}
