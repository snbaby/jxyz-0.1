package com.isoftstone.jxyz.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static DateFormat df() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	//获取当前时间年月yyyyMM 202001
	public static String getYearMonth(){
		DateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(new Date());
	}
	
	public static String translateDateStr(Object obj) {
		if (null == obj) {
			return null;
		}
		try {
			return df().format(df().parse(obj.toString()));
		} catch (Exception e) {
			return null;
		}
	}
}
