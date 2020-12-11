package jxyz.exchanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import jxyz.Application;
import jxyz.utils.HttpUtil;
import jxyz.utils.Tools;

/**
 * 处理投递月表 
 * 输入表：dwr_delivery_detail_t 
 * 输出表：dm_delivery_month_t
 * 
 * @author xiaoxin
 *
 */
public class DeliveryMonthExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		// 删除目标数据(从全局变量里取开始时间和结束时间)
		String deleteSQL = "delete from dm_delivery_month_t where period_id >= ${START_DATE} and period_id <= ${END_DATE}";
		// 替换变量
		Map<String, String> params = new HashMap<String, String>();
		params.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.DEL_CURR_MONTH_START));
		params.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.DEL_CURR_MONTH_END));
		deleteSQL = Tools.parse(deleteSQL, params);

		PreparedStatement deletePs = connection.prepareStatement(deleteSQL);
		int num = deletePs.executeUpdate();
		System.out.println("删除: " + num);
		deletePs.close();
		
		String querySQL = "SELECT\r\n" + 
				"	tmp.period_id,\r\n" + 
				"	tmp.`post_person_id`,\r\n" + 
				"	tmp.`post_person_no`,\r\n" + 
				"	tmp.`post_person_name`,\r\n" + 
				"	tmp.`post_person_mobile`,\r\n" + 
				"	tmp.`receiver_country_no`,\r\n" + 
				"	tmp.`receiver_country_name`,\r\n" + 
				"	tmp.`receiver_province_no`,\r\n" + 
				"	tmp.`receiver_province_name`,\r\n" + 
				"	tmp.`receiver_city_no`,\r\n" + 
				"	tmp.`receiver_city_name`,\r\n" + 
				"	tmp.`receiver_county_no`,\r\n" + 
				"	tmp.`receiver_county_name`,\r\n" + 
				"	tmp.`receiver_district_no`,\r\n" + 
				"	tmp.`deliver_org_no`,\r\n" + 
				"	tmp.`deliver_org_name`,\r\n" + 
				"	tmp.`deliver_section`,\r\n" + 
				"	tmp.`deliver_section_name`,\r\n" + 
				"	tmp.`deliver_section_code`,\r\n" + 
				"	SUM( tmp.deliver_qty ) deliver_qty,\r\n" + 
				"	SUM( tmp.last_month_deliver_qty ) last_month_deliver_qty,\r\n" + 
				"	SUM( tmp.current_year_total_qty ) current_year_total_qty,\r\n" + 
				"	SUM( tmp.`last_year_total_qty` ) last_year_total_qty,\r\n" + 
				"	SUM( court_mail_qty ) court_mail_qty \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) period_id,\r\n" + 
				"		t.`post_person_id`,\r\n" + 
				"		t.`post_person_no`,\r\n" + 
				"		t.`post_person_name`,\r\n" + 
				"		t.`post_person_mobile`,\r\n" + 
				"		t.`receiver_country_no`,\r\n" + 
				"		t.`receiver_country_name`,\r\n" + 
				"		t.`receiver_province_no`,\r\n" + 
				"		t.`receiver_province_name`,\r\n" + 
				"		t.`receiver_city_no`,\r\n" + 
				"		t.`receiver_city_name`,\r\n" + 
				"		t.`receiver_county_no`,\r\n" + 
				"		t.`receiver_county_name`,\r\n" + 
				"		t.`receiver_district_no`,\r\n" + 
				"		t.`deliver_org_no`,\r\n" + 
				"		t.`deliver_org_name`,\r\n" + 
				"		t.`deliver_section`,\r\n" + 
				"		t.`deliver_section_name`,\r\n" + 
				"		t.`deliver_section_code`,\r\n" + 
				"		sum( court_mail_qty ) court_mail_qty,\r\n" + 
				"		SUM( t.`deliver_qty` ) deliver_qty,\r\n" + 
				"		0 last_month_deliver_qty,\r\n" + 
				"		0 current_year_total_qty,\r\n" + 
				"		0 last_year_total_qty \r\n" + 
				"	FROM\r\n" + 
				"		`dwr_delivery_detail_t` t \r\n" + 
				"	WHERE\r\n" + 
				"		t.`period_id` <= ADDDATE( '${last_date}', INTERVAL 0 MONTH ) AND t.`period_id` >= ADDDATE( '${first_date}', INTERVAL 0 MONTH ) \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.`post_person_id`,\r\n" + 
				"		t.`post_person_no`,\r\n" + 
				"		t.`post_person_name`,\r\n" + 
				"		t.`post_person_mobile`,\r\n" + 
				"		t.`receiver_country_no`,\r\n" + 
				"		t.`receiver_country_name`,\r\n" + 
				"		t.`receiver_province_no`,\r\n" + 
				"		t.`receiver_province_name`,\r\n" + 
				"		t.`receiver_city_no`,\r\n" + 
				"		t.`receiver_city_name`,\r\n" + 
				"		t.`receiver_county_no`,\r\n" + 
				"		t.`receiver_county_name`,\r\n" + 
				"		t.`receiver_district_no`,\r\n" + 
				"		t.`deliver_org_no`,\r\n" + 
				"		t.`deliver_org_name`,\r\n" + 
				"		t.`deliver_section`,\r\n" + 
				"		t.`deliver_section_name`,\r\n" + 
				"		t.`deliver_section_code` UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) period_id,\r\n" + 
				"		t.`post_person_id`,\r\n" + 
				"		t.`post_person_no`,\r\n" + 
				"		t.`post_person_name`,\r\n" + 
				"		t.`post_person_mobile`,\r\n" + 
				"		t.`receiver_country_no`,\r\n" + 
				"		t.`receiver_country_name`,\r\n" + 
				"		t.`receiver_province_no`,\r\n" + 
				"		t.`receiver_province_name`,\r\n" + 
				"		t.`receiver_city_no`,\r\n" + 
				"		t.`receiver_city_name`,\r\n" + 
				"		t.`receiver_county_no`,\r\n" + 
				"		t.`receiver_county_name`,\r\n" + 
				"		t.`receiver_district_no`,\r\n" + 
				"		t.`deliver_org_no`,\r\n" + 
				"		t.`deliver_org_name`,\r\n" + 
				"		t.`deliver_section`,\r\n" + 
				"		t.`deliver_section_name`,\r\n" + 
				"		t.`deliver_section_code`,\r\n" + 
				"		0 court_mail_qty,\r\n" + 
				"		0 deliver_qty,\r\n" + 
				"		SUM( t.`deliver_qty` ) last_month_deliver_qty,\r\n" + 
				"		0 current_year_total_qty,\r\n" + 
				"		0 last_year_total_qty \r\n" + 
				"	FROM\r\n" + 
				"		`dwr_delivery_detail_t` t \r\n" + 
				"	WHERE\r\n" + 
				"		t.`period_id` <= '${lm_last_date}' AND t.`period_id` >= '${lm_first_date}' \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.`post_person_id`,\r\n" + 
				"		t.`post_person_no`,\r\n" + 
				"		t.`post_person_name`,\r\n" + 
				"		t.`post_person_mobile`,\r\n" + 
				"		t.`receiver_country_no`,\r\n" + 
				"		t.`receiver_country_name`,\r\n" + 
				"		t.`receiver_province_no`,\r\n" + 
				"		t.`receiver_province_name`,\r\n" + 
				"		t.`receiver_city_no`,\r\n" + 
				"		t.`receiver_city_name`,\r\n" + 
				"		t.`receiver_county_no`,\r\n" + 
				"		t.`receiver_county_name`,\r\n" + 
				"		t.`receiver_district_no`,\r\n" + 
				"		t.`deliver_org_no`,\r\n" + 
				"		t.`deliver_org_name`,\r\n" + 
				"		t.`deliver_section`,\r\n" + 
				"		t.`deliver_section_name`,\r\n" + 
				"		t.`deliver_section_code` UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) period_id,\r\n" + 
				"		t.`post_person_id`,\r\n" + 
				"		t.`post_person_no`,\r\n" + 
				"		t.`post_person_name`,\r\n" + 
				"		t.`post_person_mobile`,\r\n" + 
				"		t.`receiver_country_no`,\r\n" + 
				"		t.`receiver_country_name`,\r\n" + 
				"		t.`receiver_province_no`,\r\n" + 
				"		t.`receiver_province_name`,\r\n" + 
				"		t.`receiver_city_no`,\r\n" + 
				"		t.`receiver_city_name`,\r\n" + 
				"		t.`receiver_county_no`,\r\n" + 
				"		t.`receiver_county_name`,\r\n" + 
				"		t.`receiver_district_no`,\r\n" + 
				"		t.`deliver_org_no`,\r\n" + 
				"		t.`deliver_org_name`,\r\n" + 
				"		t.`deliver_section`,\r\n" + 
				"		t.`deliver_section_name`,\r\n" + 
				"		t.`deliver_section_code`,\r\n" + 
				"		0 court_mail_qty,\r\n" + 
				"		0 deliver_qty,\r\n" + 
				"		0 last_month_deliver_qty,\r\n" + 
				"		SUM( t.`deliver_qty` ) current_year_total_qty,\r\n" + 
				"		0 last_year_total_qty \r\n" + 
				"	FROM\r\n" + 
				"		`dwr_delivery_detail_t` t \r\n" + 
				"	WHERE\r\n" + 
				"		t.`period_id` <= '${last_date}' AND t.`period_id` >= CONCAT( YEAR ( '${last_date}' ), '-01-01' ) \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.`post_person_id`,\r\n" + 
				"		t.`post_person_no`,\r\n" + 
				"		t.`post_person_name`,\r\n" + 
				"		t.`post_person_mobile`,\r\n" + 
				"		t.`receiver_country_no`,\r\n" + 
				"		t.`receiver_country_name`,\r\n" + 
				"		t.`receiver_province_no`,\r\n" + 
				"		t.`receiver_province_name`,\r\n" + 
				"		t.`receiver_city_no`,\r\n" + 
				"		t.`receiver_city_name`,\r\n" + 
				"		t.`receiver_county_no`,\r\n" + 
				"		t.`receiver_county_name`,\r\n" + 
				"		t.`receiver_district_no`,\r\n" + 
				"		t.`deliver_org_no`,\r\n" + 
				"		t.`deliver_org_name`,\r\n" + 
				"		t.`deliver_section`,\r\n" + 
				"		t.`deliver_section_name`,\r\n" + 
				"		t.`deliver_section_code` UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) period_id,\r\n" + 
				"		t.`post_person_id`,\r\n" + 
				"		t.`post_person_no`,\r\n" + 
				"		t.`post_person_name`,\r\n" + 
				"		t.`post_person_mobile`,\r\n" + 
				"		t.`receiver_country_no`,\r\n" + 
				"		t.`receiver_country_name`,\r\n" + 
				"		t.`receiver_province_no`,\r\n" + 
				"		t.`receiver_province_name`,\r\n" + 
				"		t.`receiver_city_no`,\r\n" + 
				"		t.`receiver_city_name`,\r\n" + 
				"		t.`receiver_county_no`,\r\n" + 
				"		t.`receiver_county_name`,\r\n" + 
				"		t.`receiver_district_no`,\r\n" + 
				"		t.`deliver_org_no`,\r\n" + 
				"		t.`deliver_org_name`,\r\n" + 
				"		t.`deliver_section`,\r\n" + 
				"		t.`deliver_section_name`,\r\n" + 
				"		t.`deliver_section_code`,\r\n" + 
				"		0 court_mail_qty,\r\n" + 
				"		0 deliver_qty,\r\n" + 
				"		0 last_month_deliver_qty,\r\n" + 
				"		0 current_year_total_qty,\r\n" + 
				"		SUM( t.`deliver_qty` ) last_year_total_qty \r\n" + 
				"	FROM\r\n" + 
				"		`dwr_delivery_detail_t` t \r\n" + 
				"	WHERE\r\n" + 
				"		t.`period_id` <= ADDDATE( '${last_date}', INTERVAL - 1 YEAR ) AND t.`period_id` >= CONCAT( YEAR ( ADDDATE( '${last_date}', INTERVAL - 1 YEAR ) ), '-01-01' ) \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.`post_person_id`,\r\n" + 
				"		t.`post_person_no`,\r\n" + 
				"		t.`post_person_name`,\r\n" + 
				"		t.`post_person_mobile`,\r\n" + 
				"		t.`receiver_country_no`,\r\n" + 
				"		t.`receiver_country_name`,\r\n" + 
				"		t.`receiver_province_no`,\r\n" + 
				"		t.`receiver_province_name`,\r\n" + 
				"		t.`receiver_city_no`,\r\n" + 
				"		t.`receiver_city_name`,\r\n" + 
				"		t.`receiver_county_no`,\r\n" + 
				"		t.`receiver_county_name`,\r\n" + 
				"		t.`receiver_district_no`,\r\n" + 
				"		t.`deliver_org_no`,\r\n" + 
				"		t.`deliver_org_name`,\r\n" + 
				"		t.`deliver_section`,\r\n" + 
				"		t.`deliver_section_name`,\r\n" + 
				"		t.`deliver_section_code` \r\n" + 
				"	) tmp \r\n" + 
				"GROUP BY\r\n" + 
				"	tmp.period_id,\r\n" + 
				"	tmp.`post_person_id`,\r\n" + 
				"	tmp.`post_person_no`,\r\n" + 
				"	tmp.`post_person_name`,\r\n" + 
				"	tmp.`post_person_mobile`,\r\n" + 
				"	tmp.`receiver_country_no`,\r\n" + 
				"	tmp.`receiver_country_name`,\r\n" + 
				"	tmp.`receiver_province_no`,\r\n" + 
				"	tmp.`receiver_province_name`,\r\n" + 
				"	tmp.`receiver_city_no`,\r\n" + 
				"	tmp.`receiver_city_name`,\r\n" + 
				"	tmp.`receiver_county_no`,\r\n" + 
				"	tmp.`receiver_county_name`,\r\n" + 
				"	tmp.`receiver_district_no`,\r\n" + 
				"	tmp.`deliver_org_no`,\r\n" + 
				"	tmp.`deliver_org_name`,\r\n" + 
				"	tmp.`deliver_section`,\r\n" + 
				"	tmp.`deliver_section_name`,\r\n" + 
				"	tmp.`deliver_section_code`";
		// 替换变量
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("first_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_FIRSTDAY));
		params2.put("last_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_LASTDAY));
		params2.put("lm_first_date", (String) Application.GLOBAL_PARAM.get(Application.LAST_MONTH_FIRSTDAY));
		params2.put("lm_last_date", (String) Application.GLOBAL_PARAM.get(Application.LAST_MONTH_LASTDAY));
		querySQL = Tools.parse(querySQL, params2);
		System.out.println(querySQL);
		PreparedStatement queryPs = connection.prepareStatement(querySQL);
		

		String insertSQL = "insert into dm_delivery_month_t(receiver_city_name,deliver_section_name,last_year_total_qty,receiver_county_no,post_person_id,deliver_qty,deliver_org_name,post_person_mobile,post_person_no,receiver_district_no,receiver_country_no,receiver_province_name,receiver_city_no,receiver_county_name,court_mail_qty,last_month_deliver_qty,current_year_total_qty,receiver_country_name,receiver_province_no,deliver_org_no,post_person_name,deliver_section_code,period_id,deliver_section)value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertPs = connection.prepareStatement(insertSQL);

		ResultSet rs = queryPs.executeQuery();
		int count = 0;
		while (rs.next()) {
			insertPs.setString(1, rs.getString("receiver_city_name"));
			insertPs.setString(2, rs.getString("deliver_section_name"));
			insertPs.setInt(3, rs.getInt("last_year_total_qty"));
			insertPs.setString(4, rs.getString("receiver_county_no"));
			insertPs.setInt(5, rs.getInt("post_person_id"));
			insertPs.setInt(6, rs.getInt("deliver_qty"));
			insertPs.setString(7, rs.getString("deliver_org_name"));
			insertPs.setString(8, rs.getString("post_person_mobile"));
			insertPs.setString(9, rs.getString("post_person_no"));
			insertPs.setString(10, rs.getString("receiver_district_no"));
			insertPs.setString(11, rs.getString("receiver_country_no"));
			insertPs.setString(12, rs.getString("receiver_province_name"));
			insertPs.setString(13, rs.getString("receiver_city_no"));
			insertPs.setString(14, rs.getString("receiver_county_name"));
			insertPs.setInt(15, rs.getInt("court_mail_qty"));
			insertPs.setInt(16, rs.getInt("last_month_deliver_qty"));
			insertPs.setInt(17, rs.getInt("current_year_total_qty"));
			insertPs.setString(18, rs.getString("receiver_country_name"));
			insertPs.setString(19, rs.getString("receiver_province_no"));
			insertPs.setString(20, rs.getString("deliver_org_no"));
			insertPs.setString(21, rs.getString("post_person_name"));
			insertPs.setString(22, rs.getString("deliver_section_code"));
			insertPs.setInt(23, rs.getInt("period_id"));
			insertPs.setString(24, rs.getString("deliver_section"));
			insertPs.executeUpdate();
			count++;
		}
		rs.close();
		queryPs.close();
		insertPs.close();
		System.out.println("写入dm_delivery_month_t" + " 记录数:" + count);
		
		Map<String,String> transferMap = new HashMap<>();
		transferMap.put("tableName", "dm_delivery_month_t");
		String selectSql = "select `period_id`,`post_person_id`,`post_person_no`,`post_person_name`,`post_person_mobile`,`receiver_country_no`,`receiver_country_name`,`receiver_province_no`,`receiver_province_name`,`receiver_city_no`,`receiver_city_name`,`receiver_county_no`,`receiver_county_name`,`receiver_district_no`,`deliver_org_no`,`deliver_org_name`,`deliver_section`,`deliver_section_name`,`deliver_section_code`,`deliver_qty`,`last_month_deliver_qty`,`cur_year_collection_total`,`current_year_total_qty`,`last_year_total_qty`,`court_mail_qty`,`created_date`,`created_by`,`extend_column`,`create_user`,`create_date`,`modify_user`,`modify_date` from dm_delivery_month_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		selectSql = Tools.parse(selectSql, params);
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", deleteSQL);
		HttpUtil.upload(transferMap);
	}

}
