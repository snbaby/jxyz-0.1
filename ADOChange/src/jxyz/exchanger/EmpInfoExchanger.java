package jxyz.exchanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import jxyz.utils.HttpUtil;

/**
 * 沙盘微网格看板
 * 员工数据
 * 输入表：dwr_jxyz_emp_d
 * 输出表：dm_jxyz_emp_info_t
 * @author xiaoxin
 *
 */
public class EmpInfoExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		String querySQL = "SELECT\n" +
                "\te.grid_code,\n" +
                "\te.emp_code,\n" +
                "\te.emp_name,\n" +
                "\te.emp_dept_code,\n" +
                "\te.emp_dept_name,\n" +
                "\te.emp_section_code,\n" +
                "\te.emp_section_name,\n" +
                "\te.emp_working_seniority,\n" +
                "\te.emp_post,\n" +
                "\te.emp_tel,\n" +
                "\te.emp_training_times,\n" +
                "\te.ado_id,\n" +
                "\te.hire_time,\n" +
                "\te.`leavedate`,\n" +
                "\te.location_longitude,\n" +
                "\te.location_latitude,\n" +
                "\tCONCAT( 'POINT(', e.location_longitude, ',', e.location_latitude, ')' ) bundary_coordinate,\n" +
                "\tIFNULL( ed.collected_qty, 0 ) daily_volume,\n" +
                "\tIFNULL( em.month_collected_qty, 0 ) monthly_volume\n" +
                "FROM\n" +
                "\t(\n" +
                "\tSELECT\n" +
                "\t\ttt.post_person_no \n" +
                "\tFROM\n" +
                "\t\t(\n" +
                "\t\tSELECT DISTINCT\n" +
                "\t\t\t( post_person_no ) AS post_person_no \n" +
                "\t\tFROM\n" +
                "\t\t\tdwr_emp_daily_collection_t \n" +
                "\t\tWHERE\n" +
                "\t\t\tperiod_id >= ADDDATE( NOW(), INTERVAL - 30 DAY ) \n" +
                "\t\t) tt \n" +
                "\t) a\n" +
                "\tLEFT JOIN dwr_jxyz_emp_d e ON a.post_person_no = e.emp_code\n" +
                "\tLEFT JOIN (\n" +
                "\tSELECT\n" +
                "\t\tt.post_person_no \n" +
                "\tFROM\n" +
                "\t\tdm_emp_month_collection_t t \n" +
                "\tWHERE\n" +
                "\t\tt.period_id = DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 30 DAY ), '%Y%m' ) \n" +
                "\tGROUP BY\n" +
                "\t\tt.post_person_no \n" +
                "\t) km ON e.`emp_code` = km.`post_person_no`\n" +
                "\tLEFT JOIN (\n" +
                "\tSELECT\n" +
                "\t\ted.post_person_no,\n" +
                "\t\tSUM( ed.collected_qty ) collected_qty \n" +
                "\tFROM\n" +
                "\t\t`dwr_emp_daily_collection_t` ed \n" +
                "\tWHERE\n" +
                "\t\ted.period_id = ADDDATE( CURDATE( ), INTERVAL - 1 DAY ) \n" +
                "\tGROUP BY\n" +
                "\t\ted.post_person_no \n" +
                "\t) ed ON e.`emp_code` = ed.`post_person_no`\n" +
                "\t\n" +
                "\tLEFT JOIN (\n" +
                "\tSELECT\n" +
                "\t\tt.post_person_no,\n" +
                "\t\tSUM( t.collected_qty ) month_collected_qty \n" +
                "\tFROM\n" +
                "\t\tdm_emp_month_collection_t t \n" +
                "\tWHERE\n" +
                "\t\tt.period_id = DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 0 MONTH ), '%Y%m' ) \n" +
                "\tGROUP BY\n" +
                "\t\tt.post_person_no \n" +
                "\t) em ON e.`emp_code` = em.`post_person_no`";
		
		PreparedStatement p = connection.prepareStatement(querySQL);
		ResultSet rs = p.executeQuery();
		int count = 0;
		
		PreparedStatement dp = connection.prepareStatement(
				"truncate  dm_jxyz_emp_info_t");
		dp.executeUpdate();
		dp.close();
		
		//写入
		PreparedStatement write = connection.prepareStatement("insert into dm_jxyz_emp_info_t("
				+ "grid_code,\r\n" + 
				"	  emp_code,\r\n" + 
				"	  emp_name,\r\n" + 
				"	  emp_dept_code,\r\n" + 
				"	  emp_dept_name,\r\n" + 
				"	  emp_section_code,\r\n" + 
				"	  emp_section_name ,\r\n" + 
				"	  emp_working_seniority,\r\n" + 
				"	  emp_post,\r\n" + 
				"	  emp_tel,\r\n" + 
				"	  emp_training_times,\r\n" + 
				"	  ado_id,\r\n" + 
				"	  hire_time,\r\n" + 
				"	  `leavedate`,\r\n" + 
				"	  location_longitude,\r\n" + 
				"	  location_latitude,\r\n" + 
				"	  bundary_coordinate,\r\n" + 
				"	  daily_volume,\r\n" + 
				"	  monthly_volume,\r\n" + 
				"	  daily_delivery,\r\n" + 
				"	  monthly_delivery)" +
				" value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		while(rs.next())
		{
			write.setString(1, rs.getString("grid_code"));
			write.setString(2, rs.getString("emp_code"));
			write.setString(3, rs.getString("emp_name"));
			write.setString(4, rs.getString("emp_dept_code"));
			write.setString(5, rs.getString("emp_dept_name"));
			write.setString(6, rs.getString("emp_section_code"));
			write.setString(7, rs.getString("emp_section_name"));
			write.setDouble(8, rs.getDouble("emp_working_seniority"));
			write.setString(9, rs.getString("emp_post"));
			write.setString(10, rs.getString("emp_tel"));
			write.setInt(11, rs.getInt("emp_training_times"));
			write.setString(12, rs.getString("ado_id"));
			write.setDate(13, rs.getDate("hire_time"));
			write.setDate(14, rs.getDate("leavedate"));
			write.setBigDecimal(15, rs.getBigDecimal("location_longitude"));
			write.setBigDecimal(16, rs.getBigDecimal("location_latitude"));
			write.setString(17, rs.getString("bundary_coordinate"));
			write.setInt(18, rs.getInt("daily_volume"));
			write.setInt(19, rs.getInt("monthly_volume"));
			write.setInt(20, rs.getInt("daily_delivery"));
			write.setInt(21, rs.getInt("monthly_delivery"));
			write.executeUpdate();
			count++;
		}

		System.out.println("插入dm_jxyz_emp_info_t表=======》" + count + "条");

		rs.close();
		p.close();
		write.close();
		 Map<String,String> transferMap = new HashMap<>();
	        transferMap.put("tableName", "dm_jxyz_emp_info_t");
			String selectSql = "select * from dm_jxyz_emp_info_t ";
			transferMap.put("selectSql", selectSql);
			transferMap.put("prefix", "TRUNCATE dm_jxyz_emp_info_t");
			HttpUtil.upload(transferMap);
		
	}

}
