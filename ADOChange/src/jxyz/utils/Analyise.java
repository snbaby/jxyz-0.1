package jxyz.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Analyise {
	
	public static final String DIR = "/home/analy.txt";
	public static final String DATABASE_IP = "127.0.0.1:3306";
	public static final String DATABASE_USER = "root";
	public static final String DATABASE_PASS = "root@123";
	public static final String DATABASE_COV = "convertToNull";	
	
//	public static final String DIR = "D:\\f.txt";
//	public static final String DATABASE_IP = "127.0.0.1:3306";
//	public static final String DATABASE_USER = "root";
//	public static final String DATABASE_PASS = "Elon2108";
//	public static final String DATABASE_COV = "CONVERT_TO_NULL";

	
	public static void main(String[] args) throws Exception
	{
		

		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		
		Connection connection = DriverManager.getConnection(String.format("jdbc:mysql://%s/jxyz?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=%s&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false&serverTimezone=Asia/Shanghai", DATABASE_IP, DATABASE_COV),
				DATABASE_USER, DATABASE_PASS);
		calcCityIncome(connection);

	}
	
	public static void calcCityIncome(Connection connection) throws Exception
	{
		
		long start = System.currentTimeMillis();
		System.out.println("开始执行");
		
		PreparedStatement cityPs = connection.prepareStatement("select d.name from t_grid_m d where d.code = (select k.parent_code from t_grid_m k where code = (select m.parent_code from t_grid_m m where m.`code` = ?))");
		
		//去重MAP
		Map<String, String> dupMap = new HashMap();
		
		Map<String, String> oMap = new HashMap();
		PreparedStatement orgPs = connection.prepareStatement("select * from t_grid_m where level = 4");
		ResultSet orgRs = orgPs.executeQuery();
		while(orgRs.next())
		{
			
			cityPs.setString(1, orgRs.getString("code"));
			ResultSet rs = cityPs.executeQuery();
			if(rs.next())
			{
				oMap.put(orgRs.getString("code"), rs.getString("name"));
			}
			rs.close();
			
		}
		orgRs.close();
		orgPs.close();
		
		System.out.println("缓存营业部: " + oMap.size());
		
		PreparedStatement ps = connection.prepareStatement("SELECT\r\n" +
				"   t.waybill_no,\r\n" + 
				"	t.postage_total,\r\n" + 
				"	t.post_org_no,\r\n" + 
				"	DATE_FORMAT( biz_occur_date, '%Y%m' ) dmonth\r\n" + 
				"FROM\r\n" + 
				"	sdi_jxyz_pkp_waybill_base_2020 t \r\n" + 
				"WHERE\r\n" + 
				"	t.waybill_no LIKE '1%' "
				+ " and t.biz_occur_date >= '2020-08-01'");
		
		Map<String, Map> orgMap = new HashMap<>();
		ResultSet rs = ps.executeQuery();
		long t1 = System.currentTimeMillis();
		System.out.println("查询结果: " + (t1 - start) / 1000);
		while(rs.next())
		{
			//判断重复
			String no = rs.getString("waybill_no");
			if(dupMap.containsKey(no))
			{
				continue;
			}
			dupMap.put(no,no);
			//判断城市
			String month = rs.getString("dmonth");
			BigDecimal value = rs.getBigDecimal("postage_total");
		    String city = oMap.get(rs.getString("post_org_no"));
		    System.out.println(no + " " + month + " " + value.doubleValue() + " " + city);
		    if(city == null)
		    {
		    	continue;
		    }

		    if(orgMap.containsKey(city))
		    {
		    	Map<String, Double> monthMap = orgMap.get(city);
		    	if(monthMap.containsKey(month))
		    	{
		    	   monthMap.put(month, monthMap.get(month) + value.doubleValue());
		    	}
		    	else
		    	{
		    		monthMap.put(month, value.doubleValue());
		    	}
		    }
		    else
		    {
		    	Map<String, Double> monthMap = new HashMap<>();
		    	monthMap.put(month, value.doubleValue());
		    	orgMap.put(city, monthMap);
		    }
		}
		rs.close();
		ps.close();
		
		long eplase = System.currentTimeMillis() - start;
		
		File file = new File(DIR);
		PrintWriter pw = new PrintWriter(file, "UTF-8");
		pw.println("time: " + eplase);
		pw.println("城市\t8月\t9月\t10月");
		for(String city : orgMap.keySet())
		{
			pw.print(city);
			pw.print("\t");
			if(!orgMap.containsKey(city))
			{
			    pw.println("0\t0\t0");
				continue;
			}
			Map<String, Double> monthMap = orgMap.get(city);
			if(monthMap.containsKey("202008"))
			{
				pw.print(Tools.DATAFORMAT.format(monthMap.get("202008")));
				pw.print("\t");
			}
			else
			{
				pw.print(0);
				pw.print("\t");
			}
			if(monthMap.containsKey("202009"))
			{
				pw.print(Tools.DATAFORMAT.format(monthMap.get("202009")));
				pw.print("\t");
			}
			else
			{
				pw.print(0);
				pw.print("\t");
			}
			if(monthMap.containsKey("202010"))
			{
				pw.print(Tools.DATAFORMAT.format(monthMap.get("202010")));
				pw.print("\t");
			}
			else
			{
				pw.print(0);
				pw.print("\t");
			}
			pw.println("");
		}
		pw.flush();
		pw.close();
		System.out.println("分析完成，用时： " + (System.currentTimeMillis() - start) / 1000);
	}

}
