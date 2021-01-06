package jxyz.exchanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import jxyz.Application;
import jxyz.utils.HttpUtil;

/**
 * 处理现费数据
 * 作战图：4个现费指标
 * 输入表：sdi_jxyz_pkp_waybill_base_2020
 * 输出表：dm_jxyz_sand_table_t
 * @author 刘毅
 *
 */
public class SanderExchanger implements Exchanger{
	
	@SuppressWarnings("unused")
	public void process(Connection connection) throws Exception
    {
		// 查询当日现费  -- 段道
    	PreparedStatement level4Day = connection.prepareStatement("select count(b.grid_code) as yesterday_cash_qty,b.grid_code,sum(postage_total) as yesterday_cash_income from ( \r\n" + 
    			"    select *, row_number() over (partition by waybill_no order by id) as group_idx  \r\n" + 
    			"    from sdi_jxyz_pkp_waybill_base_"+(String) Application.GLOBAL_PARAM.get(Application.CURR_YEAR)+"  where biz_occur_date > ? and biz_occur_date < ? and sender_type = '0' and sender_province_no = '360000' and base_product_no = '11210' and postage_total != 0 \r\n" + 
    			") a left join dwr_jxyz_emp_d b on a.post_person_no = b.emp_code  GROUP BY b.grid_code");
    	level4Day.setString(1, (String) Application.GLOBAL_PARAM.get(Application.LAST_DAY));
    	level4Day.setString(2, (String) Application.GLOBAL_PARAM.get(Application.CURR_DAY));
    	ResultSet level4DayRs = level4Day.executeQuery();
    	while (level4DayRs.next()) {
    		PreparedStatement write = connection.prepareStatement("update dm_jxyz_sand_table_t set yesterday_cash_qty = ?,yesterday_cash_income = ? where grid_code = ? and grid_level = 4");
    		write.setInt(1, level4DayRs.getInt("yesterday_cash_qty"));
    		write.setDouble(2, level4DayRs.getDouble("yesterday_cash_income"));
    		write.setString(3, level4DayRs.getString("grid_code"));
    		write.executeUpdate();
    		write.close();
    	}
    	level4DayRs.close();
		level4Day.close();
    	
		// 查询当月现费  -- 段道
    	PreparedStatement level4Month = connection.prepareStatement("select count(b.grid_code) as month_cash_qty,b.grid_code,sum(postage_total) as month_cash_income from ( \r\n" + 
    			"    select *, row_number() over (partition by waybill_no order by id) as group_idx  \r\n" + 
    			"    from sdi_jxyz_pkp_waybill_base_"+(String) Application.GLOBAL_PARAM.get(Application.CURR_YEAR)+"  where biz_occur_date > ? and biz_occur_date < ? and sender_type = '0' and sender_province_no = '360000' and base_product_no = '11210' and postage_total != 0\r\n" + 
    			") a left join dwr_jxyz_emp_d b on a.post_person_no = b.emp_code  GROUP BY b.grid_code ");
    	level4Month.setString(1, (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_FIRSTDAY));
    	level4Month.setString(2, (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_LASTDAY));
    	ResultSet level4MonthRs = level4Month.executeQuery();
    	while (level4MonthRs.next()) {
    		PreparedStatement write = connection.prepareStatement("update dm_jxyz_sand_table_t set month_cash_qty = ?,month_cash_income = ? where grid_code = ? and grid_level = 4");
    		write.setInt(1, level4MonthRs.getInt("month_cash_qty"));
    		write.setDouble(2, level4MonthRs.getDouble("month_cash_income"));
    		write.setString(3, level4MonthRs.getString("grid_code"));
    		write.executeUpdate();
    		write.close();
    	}
    	level4MonthRs.close();
    	level4Month.close();
    	
    	
    	// 查询段道覆盖人数  -- 段道
    	PreparedStatement level4Person = connection.prepareStatement("select grid_code,(school_user_num+gyy_user_num+house_user_num+busi_user_num) as total_user_num from jpx_resource_collect_d ");
    	ResultSet level4PersonRs = level4Person.executeQuery();
    	while (level4PersonRs.next()) {
    		PreparedStatement write = connection.prepareStatement("update dm_jxyz_sand_table_t set created_user = ? where grid_code = ? and grid_level = 4");
    		write.setInt(1, level4PersonRs.getInt("total_user_num"));
    		write.setString(2, level4PersonRs.getString("grid_code"));
    		write.executeUpdate();
    		write.close();
    	}
    	level4PersonRs.close();
    	level4Person.close();
    	
//    	// 营业部统计
//    	PreparedStatement level3 = connection.prepareStatement("select sum(yesterday_cash_qty) as yesterday_cash_qty ,sum(yesterday_cash_income) as yesterday_cash_income,sum(month_cash_qty) as month_cash_qty ,sum(month_cash_income) as month_cash_income,post_org_no as grid_code from dm_jxyz_sand_table_t where grid_level = 4 GROUP BY post_org_no");
//    	ResultSet level3Rs = level3.executeQuery();
//    	while (level3Rs.next()) {
//    		PreparedStatement write = connection.prepareStatement("update dm_jxyz_sand_table_t set yesterday_cash_qty = ?,yesterday_cash_income = ?, month_cash_qty = ?,month_cash_income = ? where grid_code = ? and grid_level = 3");
//    		write.setInt(1, level3Rs.getInt("yesterday_cash_qty"));
//    		write.setDouble(2, level3Rs.getDouble("yesterday_cash_income"));
//    		write.setInt(3, level3Rs.getInt("month_cash_qty"));
//    		write.setDouble(4, level3Rs.getDouble("month_cash_income"));
//    		write.setString(5, level3Rs.getString("grid_code"));
//    		write.executeUpdate();
//    		write.close();
//    	}
//    	level3Rs.close();
//    	level3.close();
//    	
//    	// 区县统计
//    	PreparedStatement level2 = connection.prepareStatement("select sum(yesterday_cash_qty) as yesterday_cash_qty ,sum(yesterday_cash_income) as yesterday_cash_income,sum(month_cash_qty) as month_cash_qty ,sum(month_cash_income) as month_cash_income,county_no as grid_code from dm_jxyz_sand_table_t where grid_level = 4 GROUP BY county_no");
//    	ResultSet level2Rs = level2.executeQuery();
//    	while (level2Rs.next()) {
//    		PreparedStatement write = connection.prepareStatement("update dm_jxyz_sand_table_t set yesterday_cash_qty = ?,yesterday_cash_income = ?, month_cash_qty = ?,month_cash_income = ? where grid_code = ? and grid_level = 2");
//    		write.setInt(1, level2Rs.getInt("yesterday_cash_qty"));
//    		write.setDouble(2, level2Rs.getDouble("yesterday_cash_income"));
//    		write.setInt(3, level2Rs.getInt("month_cash_qty"));
//    		write.setDouble(4, level2Rs.getDouble("month_cash_income"));
//    		write.setString(5, level2Rs.getString("grid_code"));
//    		write.executeUpdate();
//    		write.close();
//    	}
//    	level2Rs.close();
//    	level2.close();
//    	
//    	// 地市统计
//    	PreparedStatement level1 = connection.prepareStatement("select sum(yesterday_cash_qty) as yesterday_cash_qty ,sum(yesterday_cash_income) as yesterday_cash_income,sum(month_cash_qty) as month_cash_qty ,sum(month_cash_income) as month_cash_income,city_no as grid_code from dm_jxyz_sand_table_t where grid_level = 4 GROUP BY city_no");
//    	ResultSet level1Rs = level1.executeQuery();
//    	while (level1Rs.next()) {
//    		PreparedStatement write = connection.prepareStatement("update dm_jxyz_sand_table_t set yesterday_cash_qty = ?,yesterday_cash_income = ?, month_cash_qty = ?,month_cash_income = ? where grid_code = ? and grid_level = 1");
//    		write.setInt(1, level1Rs.getInt("yesterday_cash_qty"));
//    		write.setDouble(2, level1Rs.getDouble("yesterday_cash_income"));
//    		write.setInt(3, level1Rs.getInt("month_cash_qty"));
//    		write.setDouble(4, level1Rs.getDouble("month_cash_income"));
//    		write.setString(5, level1Rs.getString("grid_code"));
//    		write.executeUpdate();
//    		write.close();
//    	}
//    	level1Rs.close();
//    	level1.close();
//    	
//    	// 省统计
//    	PreparedStatement level = connection.prepareStatement("select sum(yesterday_cash_qty) as yesterday_cash_qty ,sum(yesterday_cash_income) as yesterday_cash_income,sum(month_cash_qty) as month_cash_qty ,sum(month_cash_income) as month_cash_income,province_no as grid_code from dm_jxyz_sand_table_t where grid_level = 4 GROUP BY province_no");
//    	ResultSet levelRs = level.executeQuery();
//    	while (levelRs.next()) {
//    		PreparedStatement write = connection.prepareStatement("update dm_jxyz_sand_table_t set yesterday_cash_qty = ?,yesterday_cash_income = ?, month_cash_qty = ?,month_cash_income = ? where grid_code = ? and grid_level = 0");
//    		write.setInt(1, levelRs.getInt("yesterday_cash_qty"));
//    		write.setDouble(2, levelRs.getDouble("yesterday_cash_income"));
//    		write.setInt(3, levelRs.getInt("month_cash_qty"));
//    		write.setDouble(4, levelRs.getDouble("month_cash_income"));
//    		write.setString(5, levelRs.getString("grid_code"));
//    		write.executeUpdate();
//    		write.close();
//    	}
//    	levelRs.close();
//    	level.close();
    	
    	
    	Map<String,String> transferMap = new HashMap<>();
        transferMap.put("tableName", "dm_jxyz_sand_table_t");
		String selectSql = "select * from dm_jxyz_sand_table_t ";
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", "TRUNCATE dm_jxyz_sand_table_t");
		HttpUtil.upload(transferMap);
    }
	
}
