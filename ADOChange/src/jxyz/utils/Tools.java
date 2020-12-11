package jxyz.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxyz.model.Grid;

public class Tools {

	public static DecimalFormat DATAFORMAT = new DecimalFormat("#.##");

	public static String createUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid.toUpperCase();
	}

	public static String parse(String str, Map<String, String> param) {

		String pattern = "\\$\\{(.+?)\\}";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String key = m.group(1);
			String value = param.get(key);
			m.appendReplacement(sb, value == null ? "" : value);
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static Boolean isExistFieldName(String fieldName, Class cls) {
		if (cls == null || fieldName.length() == 0) {
			return false;
		}
		// 获取这个类的所有属性
		Field[] fields = cls.getDeclaredFields();
		boolean flag = false;
		// 循环遍历所有的fields
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(fieldName)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static void main(String[] args) throws Exception {
		// Map<String, String> data = new HashMap<>();
		// data.put("name", "zhangs");
		// data.put("code", "2222");
		// String content = "恭喜${name}报名成功，请凭报名编号${code}到现场参${name}加活动";
		// System.out.println(parse(content, data));

		createSQL("dm_sales_department_collection_month_t");
		// beautify();
//		 createClass("dm_sales_department_collection_month_t");
		// test();

	}

	public static void beautify() throws Exception {
		File file = new File("D:\\项目\\软通ADO项目\\sql.txt");
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String str = "";
		while ((str = reader.readLine()) != null) {
			sb.append(str.trim()).append(" ");
		}
		System.out.println(sb.toString());

	}

	public static void test() throws Exception {
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/jxyz?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=CONVERT_TO_NULL&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false&serverTimezone=Asia/Shanghai",
				"elon", "Elon2108");
		PreparedStatement pStemt = connection.prepareStatement("select * from t_grid_m");
		ResultSet rs = pStemt.executeQuery();

		while (rs.next()) {
			Grid grid = new Grid(rs);
			System.out.println("gridcode: " + grid.getCode() + " " + grid.getCenter_longitude());
		}
	}

	public static void createClass(String table) throws Exception {
		// File file = new File("D:\\" + table + ".class");
		// PrintWriter pw = new PrintWriter(file, "UTF-8");
		StringBuffer sb = new StringBuffer();
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/jxyz?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=CONVERT_TO_NULL&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false&serverTimezone=Asia/Shanghai",
				"elon", "Elon2108");
		Map<String, String> typeMap = new HashMap<String, String>();
		String sql = "SELECT * FROM " + table;
		PreparedStatement pStemt = connection.prepareStatement(sql);
		// 结果集元数据
		ResultSetMetaData rsmd = pStemt.getMetaData();
		// 表列数
		int size = rsmd.getColumnCount();
		for (int i = 0; i < size; i++) {

			sb.append("private ");
			String type = rsmd.getColumnTypeName(i + 1);
			if (type.equals("BIGINT")) {
				sb.append("Long ");
			} else if (type.equals("VARCHAR")) {
				sb.append("String ");
			} else if (type.equals("INT")) {
				sb.append("int ");
			} else if (type.equals("DATE")) {
				sb.append("Date ");
			} else if (type.equals("DATETIME")) {
				sb.append("Date ");
			} else if (type.equals("TIMESTAMP")) {
				sb.append("Date ");
			} else if (type.equals("DECIMAL")) {
				sb.append("BigDecimal ");
			} else if (type.equals("JSON")) {
				sb.append("String ");
			} else {
				throw new Exception("未知类型" + type);
			}

			sb.append(rsmd.getColumnName(i + 1));
			sb.append(";\n");
		}
		System.out.println(sb.toString());
		pStemt.close();
		connection.close();

	}

	public static void createSQL(String mappingName) throws Exception {
		File file = new File("D:\\项目\\软通ADO项目\\建库\\" + mappingName + ".mapping");
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/jxyz?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=CONVERT_TO_NULL&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false&serverTimezone=Asia/Shanghai",
				"elon", "Elon2108");
		String table = file.getName().substring(0, file.getName().lastIndexOf("."));

		Map<String, String> typeMap = new HashMap<String, String>();
		String sql = "SELECT * FROM " + table;
		PreparedStatement pStemt = connection.prepareStatement(sql);
		// 结果集元数据
		ResultSetMetaData rsmd = pStemt.getMetaData();
		// 表列数
		int size = rsmd.getColumnCount();
		for (int i = 0; i < size; i++) {

			System.out.println(rsmd.getColumnName(i + 1) + " " + rsmd.getColumnTypeName(i + 1));
			typeMap.put(rsmd.getColumnName(i + 1), rsmd.getColumnTypeName(i + 1));
		}

		pStemt.close();

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		Map<String, String> mapping = new HashMap<String, String>();
		String str = "";
		while ((str = reader.readLine()) != null) {
			String[] items = str.split("\t");
			mapping.put(items[0], items[1]);
		}

		reader.close();

		StringBuffer createSQL = new StringBuffer();
		createSQL.append("insert into ").append(table).append("(");
		for (String key : mapping.keySet()) {
			createSQL.append(key).append(",");
		}
		createSQL.deleteCharAt(createSQL.length() - 1);
		createSQL.append(")").append("value(");
		for (String key : mapping.keySet()) {
			createSQL.append("?").append(",");
		}
		createSQL.deleteCharAt(createSQL.length() - 1);
		createSQL.append(")");
		System.out.println(createSQL.toString());

		StringBuffer setSQL = new StringBuffer();
		String pstmtKey = "insertPs";
		String resultSetKey = "rs";

		int count = 1;
		
		for (String key : mapping.keySet()) {
			String type = typeMap.get(key);
			if (type.equals("BIGINT")) {
				setSQL.append(pstmtKey).append(".").append("setInt(").append(count).append(", ").append(resultSetKey)
						.append(".getInt(\"").append(mapping.get(key)).append("\"));\n");
			} else if (type.equals("VARCHAR")) {
				setSQL.append(pstmtKey).append(".").append("setString(").append(count).append(", ").append(resultSetKey)
						.append(".getString(\"").append(mapping.get(key)).append("\"));\n");
			} else if (type.equals("INT")) {
				setSQL.append(pstmtKey).append(".").append("setInt(").append(count).append(", ").append(resultSetKey)
						.append(".getInt(\"").append(mapping.get(key)).append("\"));\n");
			} else if (type.equals("DATE")) {
				setSQL.append(pstmtKey).append(".").append("setDate(").append(count).append(", ").append(resultSetKey)
						.append(".getDate(\"").append(mapping.get(key)).append("\"));\n");
			} else if (type.equals("DATETIME")) {
				setSQL.append(pstmtKey).append(".").append("setDate(").append(count).append(", ").append(resultSetKey)
						.append(".getDate(\"").append(mapping.get(key)).append("\"));\n");
			} else if (type.equals("DECIMAL")) {
				setSQL.append(pstmtKey).append(".").append("setBigDecimal(").append(count).append(", ")
						.append(resultSetKey).append(".getBigDecimal(\"").append(mapping.get(key)).append("\"));\n");
			} else {
				throw new Exception("未知类型" + type);
			}
			count++;

		}
		
		setSQL.append("=================================\n");
		count = 1;

		for (String key : mapping.keySet()) {
			String type = typeMap.get(key);
			String name = mapping.get(key);
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			if (type.equals("BIGINT")) {
				setSQL.append(pstmtKey).append(".").append("setInt(").append(count).append(", ").append("obj.get")
						.append(name).append("());\n");
			} else if (type.equals("VARCHAR")) {
				setSQL.append(pstmtKey).append(".").append("setString(").append(count).append(", ").append("obj.get")
						.append(name).append("());\n");
			} else if (type.equals("INT")) {
				setSQL.append(pstmtKey).append(".").append("setInt(").append(count).append(", ").append("obj.get")
						.append(name).append("());\n");
			} else if (type.equals("DATE")) {
				setSQL.append(pstmtKey).append(".").append("setDate(").append(count).append(", ").append("obj.get")
						.append(name).append("());\n");
			} else if (type.equals("DATETIME")) {
				setSQL.append(pstmtKey).append(".").append("setDate(").append(count).append(", ").append("obj.get")
						.append(name).append("());\n");
			} else if (type.equals("DECIMAL")) {
				setSQL.append(pstmtKey).append(".").append("setBigDecimal(").append(count).append(", ")
						.append("obj.get").append(name).append("());\n");
			} else {
				throw new Exception("未知类型" + type);
			}
			count++;

		}

		System.out.println(setSQL.toString());

	}

}
