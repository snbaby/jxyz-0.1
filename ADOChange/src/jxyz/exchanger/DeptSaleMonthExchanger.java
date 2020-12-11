package jxyz.exchanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import jxyz.Application;
import jxyz.model.DeptSaleMonth;
import jxyz.model.RegionMonth;
import jxyz.utils.HttpUtil;
import jxyz.utils.Tools;

/**
 * 部门收入月统计
 * 输入表：dwr_sales_department_collection_t
 * 输出表：dm_sales_department_collection_month_t
 * 
 * @author xiaoxin
 *
 */
public class DeptSaleMonthExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		// 删除目标数据(从全局变量里取开始时间和结束时间)
		String deleteSQL = "delete from dm_sales_department_collection_month_t where period_id >= ${START_DATE} and period_id <= ${END_DATE}";
		// 替换变量
		Map<String, String> params = new HashMap<String, String>();
		params.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.DEL_CURR_MONTH_START));
		params.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.DEL_CURR_MONTH_END));
		deleteSQL = Tools.parse(deleteSQL, params);

		PreparedStatement deletePs = connection.prepareStatement(deleteSQL);
		int num = deletePs.executeUpdate();
		System.out.println("删除: " + num);
		deletePs.close();

		// 查找本月和上月
		String querySQL = "SELECT\r\n" + 
				"	post_org_id,\r\n" + 
				"	post_org_no,\r\n" + 
				"	org_drds_code,\r\n" + 
				"	post_org_name,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_country_name,\r\n" + 
				"	sender_province_no,\r\n" + 
				"	sender_province_name,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_city_name,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_county_name,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	period_id,\r\n" + 
				"	sum( order_weight ) AS order_weight,\r\n" + 
				"	sum( real_weight ) AS real_weight,\r\n" + 
				"	sum( fee_weight ) AS fee_weight,\r\n" + 
				"	sum( postage_total ) AS postage_total,\r\n" + 
				"	sum( last_month_postage_total ) AS last_month_postage_total,\r\n" + 
				"	sum( postage_standard ) AS postage_standard,\r\n" + 
				"	sum( postage_paid ) AS postage_paid,\r\n" + 
				"	sum( postage_other ) AS postage_other,\r\n" + 
				"	sum( total_current_charges ) AS total_current_charges,\r\n" + 
				"	sum( total_charge_owed ) AS total_charge_owed,\r\n" + 
				"	sum( total_prepaid_charges ) AS total_prepaid_charges,\r\n" + 
				"	sum( unpaid_amount ) AS unpaid_amount,\r\n" + 
				"	sum( payment_amount ) AS payment_amount,\r\n" + 
				"	sum( collected_qty ) AS collected_qty,\r\n" + 
				"	sum( last_month_collection_qty ) AS last_month_collection_qty,\r\n" + 
				"	sum( month_deliver_qty ) AS month_deliver_qty,\r\n" + 
				"	sum( last_month_delivery_qty ) AS last_month_delivery_qty,\r\n" + 
				"	sum( daily_effective_person ) AS daily_effective_person,\r\n" + 
				"	sum( files_qty ) AS files_qty,\r\n" + 
				"	sum( last_month_files_qty ) AS last_month_files_qty,\r\n" + 
				"	sum( goods_qty ) AS goods_qty,\r\n" + 
				"	sum( last_month_goods_qty ) AS last_month_goods_qty,\r\n" + 
				"	sum( files_revenue ) AS files_revenue,\r\n" + 
				"	sum( goods_revenue ) AS goods_revenue,\r\n" + 
				"CASE\r\n" + 
				"		\r\n" + 
				"		WHEN sum( files_qty ) + sum( goods_qty ) <> 0 THEN\r\n" + 
				"		sum( files_qty ) / ( sum( files_qty ) + sum( goods_qty ) ) \r\n" + 
				"	END AS files_rate,\r\n" + 
				"CASE\r\n" + 
				"		\r\n" + 
				"		WHEN sum( files_qty ) + sum( goods_qty ) <> 0 THEN\r\n" + 
				"		sum( goods_qty ) / ( sum( files_qty ) + sum( goods_qty ) ) \r\n" + 
				"	END AS goods_rate,\r\n" + 
				"CASE\r\n" + 
				"		\r\n" + 
				"		WHEN sum( files_qty ) <> 0 THEN\r\n" + 
				"		sum( postage_total ) / sum( files_qty ) \r\n" + 
				"	END AS files_unit_price,\r\n" + 
				"CASE\r\n" + 
				"		\r\n" + 
				"		WHEN sum( goods_qty ) <> 0 THEN\r\n" + 
				"		sum( postage_total ) / sum( goods_qty ) \r\n" + 
				"	END AS goods_unit_price,\r\n" + 
				"CASE\r\n" + 
				"		\r\n" + 
				"		WHEN sum( fee_weight ) <> 0 THEN\r\n" + 
				"		sum( postage_total ) / sum( fee_weight ) \r\n" + 
				"	END AS fee_weight_unit_price \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		0 post_org_id,\r\n" + 
				"		post_org_no,\r\n" + 
				"		org_drds_code,\r\n" + 
				"		post_org_name,\r\n" + 
				"		sender_country_no,\r\n" + 
				"		sender_country_name,\r\n" + 
				"		sender_province_no,\r\n" + 
				"		sender_province_name,\r\n" + 
				"		sender_city_no,\r\n" + 
				"		sender_city_name,\r\n" + 
				"		sender_county_no,\r\n" + 
				"		sender_county_name,\r\n" + 
				"		sender_district_no,\r\n" + 
				"		date_format( period_id, '%Y%m' ) AS period_id,\r\n" + 
				"		ifnull( order_weight, 0 ) AS order_weight,\r\n" + 
				"		ifnull( real_weight, 0 ) AS real_weight,\r\n" + 
				"		ifnull( fee_weight, 0 ) AS fee_weight,\r\n" + 
				"		ifnull( postage_total, 0 ) AS postage_total,\r\n" + 
				"		ifnull( postage_total, 0 ) AS last_month_postage_total,\r\n" + 
				"		ifnull( postage_standard, 0 ) AS postage_standard,\r\n" + 
				"		ifnull( postage_paid, 0 ) AS postage_paid,\r\n" + 
				"		ifnull( postage_other, 0 ) AS postage_other,\r\n" + 
				"		ifnull( total_current_charges, 0 ) AS total_current_charges,\r\n" + 
				"		ifnull( total_charge_owed, 0 ) AS total_charge_owed,\r\n" + 
				"		ifnull( total_prepaid_charges, 0 ) AS total_prepaid_charges,\r\n" + 
				"		ifnull( unpaid_amount, 0 ) AS unpaid_amount,\r\n" + 
				"		ifnull( payment_amount, 0 ) AS payment_amount,\r\n" + 
				"		ifnull( collected_qty, 0 ) AS collected_qty,\r\n" + 
				"		0 AS last_month_collection_qty,\r\n" + 
				"		ifnull( delivery_qty, 0 ) AS month_deliver_qty,\r\n" + 
				"		0 AS last_month_delivery_qty,\r\n" + 
				"		ifnull( daily_effective_person, 0 ) AS daily_effective_person,\r\n" + 
				"		ifnull( files_qty, 0 ) AS files_qty,\r\n" + 
				"		0 AS last_month_files_qty,\r\n" + 
				"		ifnull( goods_qty, 0 ) AS goods_qty,\r\n" + 
				"		0 AS last_month_goods_qty,\r\n" + 
				"		ifnull( files_revenue, 0 ) AS files_revenue,\r\n" + 
				"		ifnull( goods_revenue, 0 ) AS goods_revenue \r\n" + 
				"	FROM\r\n" + 
				"		dwr_sales_department_collection_t \r\n" + 
				"	WHERE\r\n" + 
				"		period_id >= adddate( '${first_date}', INTERVAL 0 DAY ) \r\n" + 
				"		AND period_id <= adddate( '${last_date}', INTERVAL 0 DAY ) UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		0 post_org_id,\r\n" + 
				"		post_org_no,\r\n" + 
				"		org_drds_code,\r\n" + 
				"		post_org_name,\r\n" + 
				"		sender_country_no,\r\n" + 
				"		sender_country_name,\r\n" + 
				"		sender_province_no,\r\n" + 
				"		sender_province_name,\r\n" + 
				"		sender_city_no,\r\n" + 
				"		sender_city_name,\r\n" + 
				"		sender_county_no,\r\n" + 
				"		sender_county_name,\r\n" + 
				"		sender_district_no,\r\n" + 
				"		date_format('${last_date}', '%Y%m' ) AS period_id,\r\n" + 
				"		0 AS order_weight,\r\n" + 
				"		0 AS real_weight,\r\n" + 
				"		0 AS fee_weight,\r\n" + 
				"		0 AS postage_total,\r\n" + 
				"		ifnull( postage_total, 0 ) AS yesterday_postage_total,\r\n" + 
				"		0 AS postage_standard,\r\n" + 
				"		0 AS postage_paid,\r\n" + 
				"		0 AS postage_other,\r\n" + 
				"		0 AS total_current_charges,\r\n" + 
				"		0 AS total_charge_owed,\r\n" + 
				"		0 AS total_prepaid_charges,\r\n" + 
				"		0 AS unpaid_amount,\r\n" + 
				"		0 AS payment_amount,\r\n" + 
				"		0 AS collected_qty,\r\n" + 
				"		ifnull( collected_qty, 0 ) AS last_month_collection_qty,\r\n" + 
				"		0 AS month_deliver_qty,\r\n" + 
				"		ifnull( delivery_qty, 0 ) AS last_month_delivery_qty,\r\n" + 
				"		0 AS daily_effective_person,\r\n" + 
				"		0 AS files_qty,\r\n" + 
				"		ifnull( files_qty, 0 ) AS last_month_files_qty,\r\n" + 
				"		0 AS goods_qty,\r\n" + 
				"		ifnull( goods_qty, 0 ) AS last_month_goods_qty,\r\n" + 
				"		0 AS files_revenue,\r\n" + 
				"		0 AS goods_revenue \r\n" + 
				"	FROM\r\n" + 
				"		dwr_sales_department_collection_t \r\n" + 
				"	WHERE\r\n" + 
				"		period_id >= adddate( '${lm_first_date}', INTERVAL 0 DAY ) \r\n" + 
				"		AND period_id <= adddate( '${lm_last_date}', INTERVAL 0 DAY ) \r\n" + 
				"	) f \r\n" + 
				"GROUP BY\r\n" + 
				"	post_org_id,\r\n" + 
				"	post_org_no,\r\n" + 
				"	org_drds_code,\r\n" + 
				"	post_org_name,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_country_name,\r\n" + 
				"	sender_province_no,\r\n" + 
				"	sender_province_name,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_city_name,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_county_name,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	period_id";
		// 替换变量
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("first_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_FIRSTDAY));
		params2.put("last_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_LASTDAY));
		params2.put("lm_first_date", (String) Application.GLOBAL_PARAM.get(Application.LAST_MONTH_FIRSTDAY));
		params2.put("lm_last_date", (String) Application.GLOBAL_PARAM.get(Application.LAST_MONTH_LASTDAY));
		querySQL = Tools.parse(querySQL, params2);
		System.out.println(querySQL);
		PreparedStatement queryPs = connection.prepareStatement(querySQL);
		ResultSet rs = queryPs.executeQuery();
		int count = 0;
		Map<String, DeptSaleMonth> map = new HashMap<>();
		while (rs.next()) {
			DeptSaleMonth cm = new DeptSaleMonth(rs);
			count++;
			map.put(cm.getGroupName(), cm);
			count++;
		}
		rs.close();
		queryPs.close();
		System.out.println("查询: " + count);

		// 查找去年月同期和去年年同期
		querySQL = "SELECT\r\n" + 
				"	post_org_no,\r\n" + 
				"	post_org_name,\r\n" + 
				"	org_drds_code,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_country_name,\r\n" + 
				"	sender_province_no,\r\n" + 
				"	sender_province_name,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_city_name,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_county_name,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	sum(last_year_collection_qty) last_year_collection_qty,\r\n" + 
				"	sum(last_year_postage_total) last_year_postage_total,\r\n" + 
				"	sum(last_year_total_qty) last_year_total_qty,\r\n" + 
				"	sum(last_year_all_postage_total) last_year_all_postage_total,\r\n" + 
				"	period_id \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		t.post_org_no,\r\n" + 
				"		t.post_org_name,\r\n" + 
				"		t.org_drds_code,\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_province_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_county_name,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		SUM( t.collected_qty ) last_year_collection_qty,\r\n" + 
				"		SUM( t.postage_total ) last_year_postage_total,\r\n" + 
				"		0 last_year_total_qty,\r\n" + 
				"		0 AS last_year_all_postage_total,\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) period_id \r\n" + 
				"	FROM\r\n" + 
				"		dwr_sales_department_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${last_year_same_month_start}' \r\n" + 
				"		AND t.period_id <= '${last_year_same_date}' \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.post_org_no,\r\n" + 
				"		t.post_org_name,\r\n" + 
				"		t.org_drds_code,\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_province_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_county_name,\r\n" + 
				"		t.sender_district_no\r\n" + 
				        "UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		t.post_org_no,\r\n" + 
				"		t.post_org_name,\r\n" + 
				"		t.org_drds_code,\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_province_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_county_name,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		0 AS last_year_collection_qty,\r\n" + 
				"		0 AS last_year_postage_total,\r\n" + 
				"		SUM( t.collected_qty ) last_year_total_qty,\r\n" + 
				"		SUM( t.postage_total ) last_year_all_postage_total,\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) period_id \r\n" + 
				"	FROM\r\n" + 
				"		dwr_sales_department_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${last_year_start}' \r\n" + 
				"		AND t.period_id <= '${last_year_same_date}' \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.post_org_no,\r\n" + 
				"		t.post_org_name,\r\n" + 
				"		t.org_drds_code,\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_province_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_county_name,\r\n" + 
				"		t.sender_district_no\r\n" + 
				"	) k \r\n" + 
				"GROUP BY\r\n" + 
				"	org_drds_code,\r\n" + 
				"	post_org_no,\r\n" + 
				"	post_org_name,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_country_name,\r\n" + 
				"	sender_province_no,\r\n" + 
				"	sender_province_name,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_city_name,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_county_name,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	period_id";

		Map<String, String> params3 = new HashMap<String, String>();
		params3.put("first_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_FIRSTDAY));
		params3.put("last_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_LASTDAY));
		params3.put("last_year_same_date", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR_SAME_DATE));
		params3.put("last_year_same_month_start",
				(String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR_SAME_MONTH_START));
		params3.put("last_year_start", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR_START));
		querySQL = Tools.parse(querySQL, params3);
		queryPs = connection.prepareStatement(querySQL);
		System.out.println("去年: " + querySQL);
		rs = queryPs.executeQuery();
		int count2 = 0;
		while (rs.next()) {
			DeptSaleMonth rm = new DeptSaleMonth(rs);
			DeptSaleMonth cm = map.get(rm.getGroupName());
			if (cm != null) {
				cm.setLast_year_collection_qty(rs.getBigDecimal("last_year_collection_qty"));
				cm.setLast_year_postage_total((rs.getBigDecimal("last_year_postage_total")));
				cm.setLast_year_total_qty(rs.getBigDecimal("last_year_total_qty"));
				cm.setLast_year_all_postage_total((rs.getBigDecimal("last_year_all_postage_total")));
			} else {
				System.out.println("未找到记录：DeptSaleExchanger1＝" + rm.getGroupName());
			}
			count2++;
		}
		rs.close();
		queryPs.close();
		System.out.println("更新: " + count2);

		// 查找今年同期和去年年同期
		querySQL = "SELECT\r\n" + 
				"	org_drds_code,\r\n" + 
				"	post_org_no,\r\n" + 
				"	post_org_name,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_country_name,\r\n" + 
				"	sender_province_no,\r\n" + 
				"	sender_province_name,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_city_name,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_county_name,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	period_id,\r\n" + 
				"	sum( cur_year_collection_total) cur_year_collection_total,\r\n" + 
				"	sum( cur_year_postage_total ) cur_year_postage_total \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		t.post_org_no,\r\n" + 
				"		t.org_drds_code,\r\n" + 
				"		t.post_org_name,\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_province_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_county_name,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		SUM( t.collected_qty ) cur_year_collection_total,\r\n" + 
				"		SUM( t.postage_total ) cur_year_postage_total,\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) period_id \r\n" + 
				"	FROM\r\n" + 
				"		dwr_sales_department_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${curr_year_start}' \r\n" + 
				"		AND t.period_id <= '${last_date}' \r\n" + 
				"	GROUP BY\r\n" + 
				"	t.org_drds_code,\r\n" + 
				"	t.post_org_no,\r\n" + 
				"	t.post_org_name,\r\n" + 
				"	t.sender_country_no,\r\n" + 
				"	t.sender_country_name,\r\n" + 
				"	t.sender_province_no,\r\n" + 
				"	t.sender_province_name,\r\n" + 
				"	t.sender_city_no,\r\n" + 
				"	t.sender_city_name,\r\n" + 
				"	t.sender_county_no,\r\n" + 
				"	t.sender_county_name,\r\n" + 
				"	t.sender_district_no\r\n" + 
				"	) k \r\n" + 
				"GROUP BY\r\n" + 
				"	org_drds_code,\r\n" + 
				"	post_org_no,\r\n" + 
				"	post_org_name,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_country_name,\r\n" + 
				"	sender_province_no,\r\n" + 
				"	sender_province_name,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_city_name,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_county_name,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	period_id";

		Map<String, String> params4 = new HashMap<String, String>();
		params4.put("last_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_LASTDAY));
		params4.put("curr_year_start", (String) Application.GLOBAL_PARAM.get(Application.CURR_YEAR_START));
		querySQL = Tools.parse(querySQL, params4);
		queryPs = connection.prepareStatement(querySQL);
		System.out.println("今年: " + querySQL);
		rs = queryPs.executeQuery();
		int count3 = 0;
		while (rs.next()) {
			DeptSaleMonth rm = new DeptSaleMonth(rs);
			DeptSaleMonth cm = map.get(rm.getGroupName());
			if (cm != null) {
				cm.setCur_year_collection_total(rs.getBigDecimal("cur_year_collection_total"));
				cm.setCur_year_postage_total((rs.getBigDecimal("cur_year_postage_total")));
			} else {
				System.out.println("未找到记录：DeptSaleExchanger2＝" + rm.getGroupName());
			}
			count3++;
		}
		rs.close();
		queryPs.close();
		System.out.println("更新: " + count3);

		String insertSQL = "insert into dm_sales_department_collection_month_t(goods_revenue,last_year_total_qty,sender_district_no,post_org_no,real_weight,postage_total,fee_weight,last_month_goods_qty,sender_country_name,sender_province_no,sender_county_name,collected_qty,sender_country_no,last_month_postage_total,postage_other,post_org_name,last_month_files_qty,cur_year_postage_total,sender_city_no,sender_city_name,period_id,order_weight,goods_rate,last_year_postage_total,fee_weight_unit_price,last_year_all_postage_total,post_org_id,sender_county_no,last_year_collection_qty,payment_amount,goods_qty,sender_province_name,total_current_charges,goods_unit_price,cur_year_collection_total,daily_effective_person,postage_paid,total_charge_owed,org_drds_code,last_month_delivery_qty,last_month_collection_qty,files_qty,files_revenue,total_prepaid_charges,unpaid_amount,postage_standard,files_unit_price,files_rate)value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertPs = connection.prepareStatement(insertSQL);
		System.out.println("insert: " + insertPs.toString());

		int insertCount = 0;
		for (DeptSaleMonth obj : map.values()) {
			insertPs.setBigDecimal(1, obj.getGoods_revenue());
			insertPs.setBigDecimal(2, obj.getLast_year_total_qty());
			insertPs.setString(3, obj.getSender_district_no());
			insertPs.setString(4, obj.getPost_org_no());
			insertPs.setBigDecimal(5, obj.getReal_weight());
			insertPs.setBigDecimal(6, obj.getPostage_total());
			insertPs.setBigDecimal(7, obj.getFee_weight());
			insertPs.setBigDecimal(8, obj.getLast_month_goods_qty());
			insertPs.setString(9, obj.getSender_country_name());
			insertPs.setString(10, obj.getSender_province_no());
			insertPs.setString(11, obj.getSender_county_name());
			insertPs.setBigDecimal(12, obj.getCollected_qty());
			insertPs.setString(13, obj.getSender_country_no());
			insertPs.setBigDecimal(14, obj.getLast_month_postage_total());
			insertPs.setBigDecimal(15, obj.getPostage_other());
			insertPs.setString(16, obj.getPost_org_name());
			insertPs.setBigDecimal(17, obj.getLast_month_files_qty());
			insertPs.setBigDecimal(18, obj.getCur_year_postage_total());
			insertPs.setString(19, obj.getSender_city_no());
			insertPs.setString(20, obj.getSender_city_name());
			insertPs.setString(21, obj.getPeriod_id());
			insertPs.setBigDecimal(22, obj.getOrder_weight());
			insertPs.setBigDecimal(23, obj.getGoods_rate());
			insertPs.setBigDecimal(24, obj.getLast_year_postage_total());
			insertPs.setBigDecimal(25, obj.getFee_weight_unit_price());
			insertPs.setBigDecimal(26, obj.getLast_year_all_postage_total());
			insertPs.setLong(27, obj.getPost_org_id());
			insertPs.setString(28, obj.getSender_county_no());
			insertPs.setBigDecimal(29, obj.getLast_year_collection_qty());
			insertPs.setBigDecimal(30, obj.getPayment_amount());
			insertPs.setBigDecimal(31, obj.getGoods_qty());
			insertPs.setString(32, obj.getSender_province_name());
			insertPs.setBigDecimal(33, obj.getTotal_current_charges());
			insertPs.setBigDecimal(34, obj.getGoods_unit_price());
			insertPs.setBigDecimal(35, obj.getCur_year_collection_total());
			insertPs.setBigDecimal(36, obj.getDaily_effective_person());
			insertPs.setBigDecimal(37, obj.getPostage_paid());
			insertPs.setBigDecimal(38, obj.getTotal_charge_owed());
			insertPs.setString(39, obj.getOrg_drds_code());
			insertPs.setBigDecimal(40, obj.getLast_month_delivery_qty());
			insertPs.setBigDecimal(41, obj.getLast_month_collection_qty());
			insertPs.setBigDecimal(42, obj.getFiles_qty());
			insertPs.setBigDecimal(43, obj.getFiles_revenue());
			insertPs.setBigDecimal(44, obj.getTotal_prepaid_charges());
			insertPs.setBigDecimal(45, obj.getUnpaid_amount());
			insertPs.setBigDecimal(46, obj.getPostage_standard());
			insertPs.setBigDecimal(47, obj.getFiles_unit_price());
			insertPs.setBigDecimal(48, obj.getFiles_rate());
			insertPs.executeUpdate();
			insertCount++;
		}
		rs.close();
		queryPs.close();
		insertPs.close();
		System.out.println("写入dm_sales_department_collection_month_t" + " 记录数:" + insertCount);
		
		Map<String,String> transferMap = new HashMap<>();
		transferMap.put("tableName", "dm_sales_department_collection_month_t");
		String selectSql = "select `post_org_id`,`post_org_no`,`org_drds_code`,`post_org_name`,`sender_country_no`,`sender_country_name`,`sender_province_no`,`sender_province_name`,`sender_city_no`,`sender_city_name`,`sender_county_no`,`sender_county_name`,`sender_district_no`,`period_id`,`order_weight`,`real_weight`,`fee_weight`,`postage_total`,`last_month_postage_total`,`postage_standard`,`postage_paid`,`postage_other`,`total_current_charges`,`total_charge_owed`,`total_prepaid_charges`,`unpaid_amount`,`payment_amount`,`collected_qty`,`last_month_collection_qty`,`month_delivery_qty`,`last_month_delivery_qty`,`daily_effective_person`,`files_qty`,`last_month_files_qty`,`goods_qty`,`last_month_goods_qty`,`files_revenue`,`goods_revenue`,`files_rate`,`goods_rate`,`files_unit_price`,`goods_unit_price`,`fee_weight_unit_price`,`cur_year_collection_total`,`last_year_collection_qty`,`last_year_total_qty`,`cur_year_postage_total`,`last_year_postage_total`,`last_year_all_postage_total`,`created_date`,`created_by`,`extend_column`,`create_user`,`create_date`,`modify_user`,`modify_date` from dm_sales_department_collection_month_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		selectSql = Tools.parse(selectSql, params);
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", deleteSQL);
		HttpUtil.upload(transferMap);
	}

}
