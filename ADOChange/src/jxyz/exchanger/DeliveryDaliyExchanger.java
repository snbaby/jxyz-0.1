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
 * 处理投递日表
 * 输入表：sdi_jxyz_pkp_trace_message_704_$, dwr_jxyz_region_d,dwr_jxyz_department_d 
 * 输出表：dwr_delivery_detail_t
 * @author xiaoxin
 *
 */
public class DeliveryDaliyExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		// 删除目标数据(从全局变量里取开始时间和结束时间)
		String deleteSQL = "delete from dwr_delivery_detail_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		// 替换变量
		Map<String, String> params = new HashMap<String, String>();
		params.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.LAST_DAY));
		params.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.CURR_DAY));
		deleteSQL = Tools.parse(deleteSQL, params);

		PreparedStatement deletePs = connection.prepareStatement(deleteSQL);
		int num = deletePs.executeUpdate();
		System.out.println("删除: " + num);
		deletePs.close();

		String querySQL = "SELECT\r\n" + "	DATE_FORMAT( t.`op_time`, '%Y-%m-%d' ) period_id,\r\n"
				+ "	t.op_erator_no post_person_no,\r\n" + "	t.op_erator_name post_person_name,\r\n"
				+ "	r.`province_code` receiver_province_no,\r\n" + "	t.op_org_prov_name receiver_province_name,\r\n"
				+ "	r.`city_code` receiver_city_no,\r\n" + "	r.`city_name` receiver_city_name,\r\n"
				+ "	IFNULL( r.`county_code`, d.county_code ) receiver_county_no,\r\n"
				+ "	IFNULL( r.`county_name`, d.county_name ) receiver_county_name,\r\n"
				+ "	t.`op_org_city` receiver_district_no,\r\n" + "	t.`op_org_code` deliver_org_no,\r\n"
				+ "	IFNULL( d.dept_name, t.op_org_name ) deliver_org_name,\r\n"
				+ "	COUNT( t.`trace_no` ) deliver_qty,\r\n" + "	0 yesterday_deliver_qty,\r\n"
				+ "	SUM( CASE WHEN RIGHT ( t.trace_no, 2 ) IN ( '93', '94' ) THEN 1 ELSE 0 END ) AS court_mail_qty \r\n"
				+ "FROM\r\n" + "	sdi_jxyz_pkp_trace_message_704_${CURRENT_MONTH} t\r\n"
				+ "	LEFT OUTER JOIN dwr_jxyz_region_d r ON t.op_org_city = r.region_name\r\n"
				+ "	LEFT OUTER JOIN dwr_jxyz_department_d d ON t.op_org_code = d.dept_code \r\n" + "WHERE\r\n"
				+ "	t.op_code = '704' \r\n" + "	AND t.op_org_prov_name = '江西省' \r\n"
				+ "	AND t.`op_time` >= '${START_DATE}' \r\n" + "	AND t.`op_time` <= '${END_DATE}' \r\n"
				+ "GROUP BY\r\n" + "	DATE_FORMAT( t.`op_time`, '%Y-%m-%d' ),\r\n" + "	t.op_erator_no,\r\n"
				+ "	t.op_erator_name,\r\n" + "	r.`province_code`,\r\n" + "	t.op_org_prov_name,\r\n"
				+ "	r.`city_code`,\r\n" + "	r.`city_name`,\r\n" + "	IFNULL( r.`county_code`, d.county_code ),\r\n"
				+ "	IFNULL( r.`county_name`, d.county_name ),\r\n" + "	t.`op_org_city`,\r\n"
				+ "	t.`op_org_code`,\r\n" + "	IFNULL( d.dept_name, t.op_org_name )";
		// 替换变量
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("C_YEAR", (String) Application.GLOBAL_PARAM.get(Application.CURR_YEAR));
		params2.put("L_YEAR", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR));
		params2.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.LAST_DAY));
		params2.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.CURR_DAY));
		params2.put("CURRENT_MONTH", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH));
		querySQL = Tools.parse(querySQL, params2);
        System.out.println("查询SQL: " + querySQL);
		PreparedStatement queryPs = connection.prepareStatement(querySQL);

		String insertSQL = "insert into dwr_delivery_detail_t(receiver_city_name,receiver_county_no,deliver_qty,deliver_org_name,post_person_no,yesterday_deliver_qty,receiver_district_no,receiver_province_name,receiver_city_no,receiver_county_name,court_mail_qty,receiver_province_no,deliver_org_no,post_person_name,period_id)value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertPs = connection.prepareStatement(insertSQL);

		ResultSet rs = queryPs.executeQuery();
		int count = 0;
		while (rs.next()) {
			insertPs.setString(1, rs.getString("receiver_city_name"));
			insertPs.setString(2, rs.getString("receiver_county_no"));
			insertPs.setInt(3, rs.getInt("deliver_qty"));
			insertPs.setString(4, rs.getString("deliver_org_name"));
			insertPs.setString(5, rs.getString("post_person_no"));
			insertPs.setInt(6, rs.getInt("yesterday_deliver_qty"));
			insertPs.setString(7, rs.getString("receiver_district_no"));
			insertPs.setString(8, rs.getString("receiver_province_name"));
			insertPs.setString(9, rs.getString("receiver_city_no"));
			insertPs.setString(10, rs.getString("receiver_county_name"));
			insertPs.setInt(11, rs.getInt("court_mail_qty"));
			insertPs.setString(12, rs.getString("receiver_province_no"));
			insertPs.setString(13, rs.getString("deliver_org_no"));
			insertPs.setString(14, rs.getString("post_person_name"));
			insertPs.setDate(15, rs.getDate("period_id"));
			insertPs.executeUpdate();
			count++;
		}
		rs.close();
		queryPs.close();
		insertPs.close();
		System.out.println("写入dwr_delivery_detail_t" + " 记录数:" + count);
		
		Map<String,String> transferMap = new HashMap<>();
		transferMap.put("tableName", "dwr_delivery_detail_t");
		String selectSql = "select `period_id`,`post_person_id`,`post_person_no`,`post_person_name`,`post_person_mobile`,`receiver_country_no`,`receiver_country_name`,`receiver_province_no`,`receiver_province_name`,`receiver_city_no`,`receiver_city_name`,`receiver_county_no`,`receiver_county_name`,`receiver_district_no`,`deliver_org_no`,`deliver_org_name`,`deliver_section`,`deliver_section_name`,`deliver_section_code`,`deliver_qty`,`yesterday_deliver_qty`,`court_mail_qty`,`created_date`,`created_by`,`extend_column`,`create_user`,`create_date`,`modify_user`,`modify_date` from dwr_delivery_detail_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		selectSql = Tools.parse(selectSql, params);
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", deleteSQL);
		HttpUtil.upload(transferMap);
	}

}
