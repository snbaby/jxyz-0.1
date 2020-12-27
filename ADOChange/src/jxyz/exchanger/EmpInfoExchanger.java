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
		String querySQL = "SELECT\r\n" + 
				"		e.grid_code,\r\n" + 
				"		e.emp_code,\r\n" + 
				"		e.emp_name,\r\n" + 
				"		e.emp_dept_code,\r\n" + 
				"		e.emp_dept_name,\r\n" + 
				"		e.emp_section_code,\r\n" + 
				"		e.emp_section_name,\r\n" + 
				"		e.emp_working_seniority,\r\n" + 
				"		e.emp_post,\r\n" + 
				"		e.emp_tel,\r\n" + 
				"		e.emp_training_times,\r\n" + 
				"		e.ado_id,\r\n" + 
				"		e.hire_time,\r\n" + 
				"		e.`leavedate`,\r\n" + 
				"		e.location_longitude,\r\n" + 
				"		e.location_latitude,\r\n" + 
				"		CONCAT( 'POINT(', e.location_longitude, ',', e.location_latitude, ')' ) bundary_coordinate,\r\n" + 
				"		IFNULL( ed.collected_qty, 0 ) daily_volume,\r\n" + 
				"		IFNULL( em.month_collected_qty, 0 ) monthly_volume,\r\n" + 
				"		IFNULL( dd.deliver_qty, 0 ) daily_delivery,\r\n" + 
				"		IFNULL( dm.deliver_qty, 0 ) monthly_delivery\r\n" + 
				"		FROM\r\n" + 
				"		(\r\n" + 
				"		SELECT\r\n" + 
				"		tt.post_person_no \r\n" + 
				"		FROM\r\n" + 
				"		(\r\n" + 
				"		SELECT DISTINCT\r\n" + 
				"		( post_person_no ) AS post_person_no \r\n" + 
				"		FROM\r\n" + 
				"		dwr_emp_daily_collection_t \r\n" + 
				"		WHERE\r\n" + 
				"		period_id >= ADDDATE( NOW(), INTERVAL - 30 DAY ) \r\n" + 
				"		) tt \r\n" + 
				"		) a\r\n" + 
				"		LEFT JOIN dwr_jxyz_emp_d e ON a.post_person_no = e.emp_code\r\n" + 
				"		LEFT JOIN (\r\n" + 
				"		SELECT\r\n" + 
				"		t.post_person_no \r\n" + 
				"		FROM\r\n" + 
				"		dm_emp_month_collection_t t \r\n" + 
				"		WHERE\r\n" + 
				"		t.period_id = DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 30 DAY ), '%Y%m' ) \r\n" + 
				"		GROUP BY\r\n" + 
				"		t.post_person_no \r\n" + 
				"		) km ON e.`emp_code` = km.`post_person_no`\r\n" + 
				"		LEFT JOIN (\r\n" + 
				"		SELECT\r\n" + 
				"		ed.post_person_no,\r\n" + 
				"		SUM( ed.collected_qty ) collected_qty\r\n" + 
				"		FROM\r\n" + 
				"		`dwr_emp_daily_collection_t` ed \r\n" + 
				"		WHERE\r\n" + 
				"		ed.period_id = ADDDATE( CURDATE( ), INTERVAL - 1 DAY ) \r\n" + 
				"		GROUP BY\r\n" + 
				"		ed.post_person_no \r\n" + 
				"		) ed ON e.`emp_code` = ed.`post_person_no`\r\n" + 
				"		\r\n" + 
				"		LEFT JOIN (\r\n" + 
				"		SELECT\r\n" + 
				"		ed.post_person_no,\r\n" + 
				"		SUM( ed.deliver_qty ) deliver_qty\r\n" + 
				"		FROM\r\n" + 
				"		`dwr_delivery_detail_t` ed \r\n" + 
				"		WHERE\r\n" + 
				"		ed.period_id = ADDDATE( CURDATE(), INTERVAL - 1 DAY ) \r\n" + 
				"		GROUP BY\r\n" + 
				"		ed.post_person_no \r\n" + 
				"		) dd ON e.`emp_code` = dd.`post_person_no`\r\n" + 
				"		\r\n" + 
				"		LEFT JOIN (\r\n" + 
				"		SELECT\r\n" + 
				"		t.post_person_no,\r\n" + 
				"		SUM( t.collected_qty ) month_collected_qty\r\n" + 
				"		FROM\r\n" + 
				"		dm_emp_month_collection_t t \r\n" + 
				"		WHERE\r\n" + 
				"		t.period_id = DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 0 MONTH ), '%Y%m' ) \r\n" + 
				"		GROUP BY\r\n" + 
				"		t.post_person_no \r\n" + 
				"		) em ON e.`emp_code` = em.`post_person_no`\r\n" + 
				"		LEFT JOIN (\r\n" + 
				"		SELECT\r\n" + 
				"		ed.post_person_no,\r\n" + 
				"		SUM( ed.deliver_qty ) deliver_qty\r\n" + 
				"		FROM\r\n" + 
				"		`dm_delivery_month_t` ed \r\n" + 
				"		WHERE\r\n" + 
				"		ed.period_id = DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 0 MONTH ), '%Y%m' ) \r\n" + 
				"		GROUP BY\r\n" + 
				"		ed.post_person_no \r\n" + 
				"		) dm ON e.`emp_code` = dm.`post_person_no` where  e.emp_status != '02' ";
		System.out.println(querySQL);
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
