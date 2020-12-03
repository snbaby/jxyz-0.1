package com.isoftstone.util;

import java.util.Map;

public class Utils {
	public static String getMapStr(Map<String, Object> map, String name) {
		Object resultObj = map.get(name);
		if (resultObj == null) {
			return "";
		} else {
			return resultObj.toString();
		}

	}

	public static double getMapDouble(Map<String, Object> map, String name) {
		Object resultObj = map.get(name);
		if (resultObj == null) {
			return 0;
		} else {
			try {
				return Double.parseDouble(resultObj.toString());
			} catch (Exception e) {
				// TODO: handle exception
				return 0;
			}
		}
	}
}
