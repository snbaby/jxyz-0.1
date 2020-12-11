package jxyz.exchanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

import jxyz.Application;
import jxyz.model.CustomMonth;
import jxyz.model.DeptSaleMonth;
import jxyz.utils.HttpUtil;
import jxyz.utils.Tools;

/**
 * 处理客户月收入汇总
 * 输入表：dwr_customer_daily_revenue_t
 * 输出表：dm_customer_month_revenue_t
 * @author xiaoxin
 *
 */
public class CustomMonthExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		// 删除目标数据(从全局变量里取开始时间和结束时间)
		String deleteSQL = "delete from dm_customer_month_revenue_t where period_id >= ${START_DATE} and period_id <= ${END_DATE}";
		// 替换变量
		Map<String, String> params = new HashMap<String, String>();
		params.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.DEL_CURR_MONTH_START));
		params.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.DEL_CURR_MONTH_END));
		deleteSQL = Tools.parse(deleteSQL, params);

		PreparedStatement deletePs = connection.prepareStatement(deleteSQL);
		int num = deletePs.executeUpdate();
		System.out.println("删除: " + num);
		deletePs.close();

		//查找本月和上月
		String querySQL = "SELECT\r\n" + 
				"	post_org_no,\r\n" + 
				"	post_org_name,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_country_name,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_city_name,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	sender_id,\r\n" + 
				"	sender_no,\r\n" + 
				"	sender,\r\n" + 
				"	sender_warehouse_id,\r\n" + 
				"	sender_warehouse_name,\r\n" + 
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
				"	sum( month_delivery_qty ) AS month_delivery_qty,\r\n" + 
				"	sum( last_month_delivery_qty ) AS last_month_delivery_qty,\r\n" + 
				"	sum( daily_effective_person ) AS daily_effective_person,\r\n" + 
				"	sum( files_qty ) AS files_qty,\r\n" + 
				"	sum( last_month_files_qty ) AS last_month_files_qty,\r\n" + 
				"	sum( goods_qty ) AS goods_qty,\r\n" + 
				"	sum( last_month_goods_qty ) AS last_month_goods_qty,\r\n" + 
				"	NULL is_new_customer,\r\n" + 
				"	customer_send_time_type,\r\n" + 
				"	sum( customer_send_qty1 ) AS customer_send_qty1,\r\n" + 
				"	sum( customer_send_qty2 ) AS customer_send_qty2,\r\n" + 
				"	sum( customer_send_qty3 ) AS customer_send_qty3 \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		t.post_org_no,\r\n" + 
				"		t.post_org_name,\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		sender_id,\r\n" + 
				"		sender_no,\r\n" + 
				"		sender,\r\n" + 
				"		sender_warehouse_id,\r\n" + 
				"		sender_warehouse_name,\r\n" + 
				"		date_format( period_id, '%Y%m' ) AS period_id,\r\n" + 
				"		ifnull( order_weight, 0 ) AS order_weight,\r\n" + 
				"		ifnull( real_weight, 0 ) AS real_weight,\r\n" + 
				"		ifnull( fee_weight, 0 ) AS fee_weight,\r\n" + 
				"		ifnull( postage_total, 0 ) AS postage_total,\r\n" + 
				"		0 AS last_month_postage_total,\r\n" + 
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
				"		ifnull( delivery_qty, 0 ) AS month_delivery_qty,\r\n" + 
				"		0 AS last_month_delivery_qty,\r\n" + 
				"		ifnull( daily_effective_person, 0 ) AS daily_effective_person,\r\n" + 
				"		ifnull( files_qty, 0 ) AS files_qty,\r\n" + 
				"		0 AS last_month_files_qty,\r\n" + 
				"		ifnull( goods_qty, 0 ) AS goods_qty,\r\n" + 
				"		0 AS last_month_goods_qty,\r\n" + 
				"		NULL AS is_new_customer,\r\n" + 
				"		customer_send_time_type AS customer_send_time_type,\r\n" + 
				"		ifnull( customer_send_qty1, 0 ) AS customer_send_qty1,\r\n" + 
				"		ifnull( customer_send_qty2, 0 ) AS customer_send_qty2,\r\n" + 
				"		ifnull( customer_send_qty3, 0 ) AS customer_send_qty3 \r\n" + 
				"	FROM\r\n" + 
				"		dwr_customer_daily_revenue_t t \r\n" + 
				"	WHERE\r\n" + 
				"		period_id >= adddate( '${first_date}', INTERVAL 0 DAY ) \r\n" + 
				"		AND period_id <= adddate( '${last_date}', INTERVAL 0 DAY ) UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		t.post_org_no,\r\n" + 
				"		t.post_org_name,\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		sender_id,\r\n" + 
				"		sender_no,\r\n" + 
				"		sender,\r\n" + 
				"		sender_warehouse_id,\r\n" + 
				"		sender_warehouse_name,\r\n" + 
				"		date_format( '${last_date}', '%Y%m' ) AS period_id,\r\n" + 
				"		0 AS order_weight,\r\n" + 
				"		0 AS real_weight,\r\n" + 
				"		0 AS fee_weight,\r\n" + 
				"		0 AS postage_total,\r\n" + 
				"		ifnull( postage_total, 0 ) AS last_month_postage_total,\r\n" + 
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
				"		0 AS month_delivery_qty,\r\n" + 
				"		ifnull( delivery_qty, 0 ) AS last_month_delivery_qty,\r\n" + 
				"		0 AS daily_effective_person,\r\n" + 
				"		0 AS files_qty,\r\n" + 
				"		ifnull( files_qty, 0 ) AS last_month_files_qty,\r\n" + 
				"		0 AS goods_qty,\r\n" + 
				"		ifnull( goods_qty, 0 ) AS last_month_goods_qty,\r\n" + 
				"		NULL is_new_customer,\r\n" + 
				"		customer_send_time_type,\r\n" + 
				"		0 AS customer_send_qty1,\r\n" + 
				"		0 AS customer_send_qty2,\r\n" + 
				"		0 AS customer_send_qty3 \r\n" + 
				"	FROM\r\n" + 
				"		dwr_customer_daily_revenue_t t \r\n" + 
				"	WHERE\r\n" + 
				"		period_id >= adddate( '${lm_first_date}', INTERVAL 0 DAY ) \r\n" + 
				"		AND period_id <= adddate( '${lm_last_date}', INTERVAL 0 DAY ) \r\n" + 
				"	) f \r\n" + 
				"GROUP BY\r\n" + 
				"	post_org_no,\r\n" + 
				"	post_org_name,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_country_name,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_city_name,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	sender_id,\r\n" + 
				"	sender_no,\r\n" + 
				"	sender,\r\n" + 
				"	sender_warehouse_id,\r\n" + 
				"	sender_warehouse_name,\r\n" + 
				"	period_id,\r\n" +
				"   is_new_customer,\r\n" + 
				"   customer_send_time_type";
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
		Map<String, CustomMonth> map = new HashMap<>();
		while (rs.next()) {
			CustomMonth cm = new CustomMonth(rs);count++;
			map.put(cm.getGroupName(), cm);
            count++;
		}
		rs.close();
		queryPs.close();
		System.out.println("查询: " + count);
		
		
		//查找去年月同期和去年年同期
		querySQL = "SELECT\r\n" + 
				"	post_org_name,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_country_name,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_city_name,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	sender_id,\r\n" + 
				"	sender_no,\r\n" + 
				"	sender,\r\n" + 
				"	sender_warehouse_id,\r\n" + 
				"	sender_warehouse_name,\r\n" + 
				"	NULL is_new_customer,\r\n" + 
				"	customer_send_time_type,\r\n" + 
				"	sum( last_year_collection_qty ) last_year_collection_qty,\r\n" + 
				"	sum( last_year_postage_total ) last_year_postage_total,\r\n" + 
				"	sum( last_year_total_qty ) last_year_total_qty,\r\n" + 
				"	sum( last_year_all_postage_total ) last_year_all_postage_total,\r\n" + 
				"	period_id \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		t.post_org_no,\r\n" + 
				"       t.post_org_name,\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.sender_id,\r\n" + 
				"		t.sender_no,\r\n" + 
				"		t.sender,\r\n" + 
				"		t.sender_warehouse_id,\r\n" + 
				"		t.sender_warehouse_name,\r\n" + 
				"		NULL AS is_new_customer,\r\n" + 
				"		t.customer_send_time_type AS customer_send_time_type,\r\n" + 
				"		sum( t.collected_qty ) last_year_collection_qty,\r\n" + 
				"		SUM( t.postage_total ) last_year_postage_total,\r\n" + 
				"		0 last_year_total_qty,\r\n" + 
				"		0 last_year_all_postage_total,\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) period_id \r\n" + 
				"	FROM\r\n" + 
				"		dwr_customer_daily_revenue_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id <= '${last_year_same_date}' AND t.period_id >= '${last_year_same_month_start}' \r\n" + 
				"	GROUP BY\r\n" + 
					"	t.post_org_no,\r\n" + 
					"	t.post_org_name,\r\n" + 
					"	t.sender_country_no,\r\n" + 
					"	t.sender_country_name,\r\n" + 
					"	t.sender_city_no,\r\n" + 
					"	t.sender_city_name,\r\n" + 
					"	t.sender_county_no,\r\n" + 
					"	t.sender_district_no,\r\n" + 
					"	t.sender_id,\r\n" + 
					"	t.sender_no,\r\n" + 
					"	t.sender,\r\n" + 
					"	t.sender_warehouse_id,\r\n" + 
					"	t.sender_warehouse_name,\r\n" + 
					"	t.period_id,\r\n" +
					"   t.customer_send_time_type" +
				"   UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		t1.post_org_no,\r\n" + 
				"       t1.post_org_name,\r\n" + 
				"		t1.sender_country_no,\r\n" + 
				"		t1.sender_country_name,\r\n" + 
				"		t1.sender_city_no,\r\n" + 
				"		t1.sender_city_name,\r\n" + 
				"		t1.sender_county_no,\r\n" + 
				"		t1.sender_district_no,\r\n" + 
				"		t1.sender_id,\r\n" + 
				"		t1.sender_no,\r\n" + 
				"		t1.sender,\r\n" + 
				"		t1.sender_warehouse_id,\r\n" + 
				"		t1.sender_warehouse_name,\r\n" + 
				"		NULL AS is_new_customer,\r\n" + 
				"		t1.customer_send_time_type AS customer_send_time_type,\r\n" + 
				"		0 last_year_collection_qty,\r\n" + 
				"		0 last_year_postage_total,\r\n" + 
				"		sum( t1.collected_qty ) last_year_total_qty,\r\n" + 
				"		SUM( t1.postage_total ) last_year_all_postage_total,\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) period_id \r\n" + 
				"	FROM\r\n" + 
				"		dwr_customer_daily_revenue_t t1 \r\n" + 
				"	WHERE\r\n" + 
				"		t1.period_id <= '${last_year_same_date}' AND t1.period_id >= '${last_year_start}' \r\n" + 
				"	GROUP BY\r\n" + 
				"	t1.post_org_no,\r\n" + 
				"	t1.post_org_name,\r\n" + 
				"	t1.sender_country_no,\r\n" + 
				"	t1.sender_country_name,\r\n" + 
				"	t1.sender_city_no,\r\n" + 
				"	t1.sender_city_name,\r\n" + 
				"	t1.sender_county_no,\r\n" + 
				"	t1.sender_district_no,\r\n" + 
				"	t1.sender_id,\r\n" + 
				"	t1.sender_no,\r\n" + 
				"	t1.sender,\r\n" + 
				"	t1.sender_warehouse_id,\r\n" + 
				"	t1.sender_warehouse_name,\r\n" + 
				"	t1.period_id,\r\n" +
				"   t1.customer_send_time_type" +
				"	) k \r\n" + 
				"GROUP BY\r\n" + 
				"   post_org_no,\r\n" +
				"	post_org_name,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_country_name,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_city_name,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	sender_id,\r\n" + 
				"	sender_no,\r\n" + 
				"	sender,\r\n" + 
				"	sender_warehouse_id,\r\n" + 
				"	sender_warehouse_name,\r\n" + 
				"	period_id,\r\n" +
				"   is_new_customer,\r\n" + 
				"   customer_send_time_type";
		
		Map<String, String> params3 = new HashMap<String, String>();
		params3.put("first_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_FIRSTDAY));
		params3.put("last_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_LASTDAY));
		params3.put("last_year_same_date", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR_SAME_DATE));
		params3.put("last_year_same_month_start", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR_SAME_MONTH_START));
		params3.put("last_year_start", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR_START));
		querySQL = Tools.parse(querySQL, params3);
		queryPs = connection.prepareStatement(querySQL);
		System.out.println("去年: " + querySQL);
		rs = queryPs.executeQuery();
		int count2 = 0;
		while (rs.next()) {
			CustomMonth rm = new CustomMonth(rs);
			CustomMonth cm = map.get(rm.getGroupName());
			if(cm != null)
			{
				cm.setLast_year_collection_qty(rs.getBigDecimal("last_year_collection_qty"));
				cm.setLast_year_postage_total((rs.getBigDecimal("last_year_postage_total")));
				cm.setLast_year_total_qty(rs.getBigDecimal("last_year_total_qty"));
				cm.setLast_year_all_postage_total((rs.getBigDecimal("last_year_all_postage_total")));
			}
			else
			{
				System.out.println("未找到记录：CustomMonthExchanger1＝" + rs.getString("post_org_no") + "-" + rs.getString("sender_id") + "-" + rs.getInt("period_id"));
			}
			count2++;
		}
		rs.close();
		queryPs.close();
		System.out.println("更新: " + count2);
		
		
		//查找今年同期和去年年同期
		querySQL = "SELECT\r\n" + 
				"	post_org_no,\r\n" + 
				"	post_org_name,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_country_name,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_city_name,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	sender_id,\r\n" + 
				"	sender_no,\r\n" + 
				"	sender,\r\n" + 
				"	NULL is_new_customer,\r\n" + 
				"	customer_send_time_type,\r\n" + 
				"	sender_warehouse_id,\r\n" + 
				"	sender_warehouse_name,\r\n" + 
				"	period_id,\r\n" + 
				"	sum( cur_year_collection_total ) cur_year_collection_total,\r\n" + 
				"	sum( cur_year_postage_total ) cur_year_postage_total \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		t.post_org_no,\r\n" + 
				"		t.post_org_name,\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.sender_id,\r\n" + 
				"		t.sender_no,\r\n" + 
				"		t.sender,\r\n" + 
				"		t.sender_warehouse_id,\r\n" + 
				"		t.sender_warehouse_name,\r\n" + 
				"		NULL AS is_new_customer,\r\n" + 
				"		t.customer_send_time_type AS customer_send_time_type,\r\n" + 
				"		sum( t.collected_qty ) cur_year_collection_total,\r\n" + 
				"		SUM( t.postage_total ) cur_year_postage_total,\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) period_id \r\n" + 
				"	FROM\r\n" + 
				"		dwr_customer_daily_revenue_t t\r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id <= '${last_date}' AND t.period_id >= '${curr_year_start}' \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.post_org_no,\r\n" + 
				"		t.post_org_name,\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.sender_id,\r\n" + 
				"		t.sender_no,\r\n" + 
				"		t.sender,\r\n" + 
				"		t.sender_warehouse_id,\r\n" + 
				"		t.sender_warehouse_name,\r\n" + 
				"       t.is_new_customer,\r\n" + 
				"       t.customer_send_time_type" +
				"	) k \r\n" + 
				"GROUP BY\r\n" + 
				"	post_org_no,\r\n" + 
				"	post_org_name,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_country_name,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_city_name,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	sender_id,\r\n" + 
				"	sender_no,\r\n" + 
				"	sender,\r\n" + 
				"	sender_warehouse_id,\r\n" + 
				"	sender_warehouse_name,\r\n" + 
				"	period_id,\r\n" +
				"   is_new_customer,\r\n" + 
				"   customer_send_time_type";
		
		Map<String, String> params4 = new HashMap<String, String>();
		params4.put("last_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_LASTDAY));
		params4.put("curr_year_start", (String) Application.GLOBAL_PARAM.get(Application.CURR_YEAR_START));
		querySQL = Tools.parse(querySQL, params4);
		queryPs = connection.prepareStatement(querySQL);
		System.out.println("今年: " +querySQL);
		rs = queryPs.executeQuery();
		int count3 = 0;
		while (rs.next()) {
			CustomMonth rm = new CustomMonth(rs);
			CustomMonth cm = map.get(rm.getGroupName());
			if(cm != null)
			{
				cm.setCur_year_collection_total(rs.getBigDecimal("cur_year_collection_total"));
				cm.setCur_year_postage_total((rs.getBigDecimal("cur_year_postage_total")));
			}
			else
			{
				System.out.println("未找到记录：CustomMonthExchanger2＝" + rs.getString("post_org_no") + "-" + rs.getString("sender_id") + "-" + rs.getInt("period_id"));
			}
			count3++;
		}
		rs.close();
		queryPs.close();
		System.out.println("更新: " + count3);
		

		String insertSQL = "insert into dm_customer_month_revenue_t(last_year_total_qty,sender_district_no,real_weight,post_org_no,postage_total,month_delivery_qty,customer_send_qty1,customer_send_qty3,fee_weight,customer_send_qty2,sender_warehouse_id,sender_warehouse_name,last_month_goods_qty,sender_id,sender_country_name,collected_qty,last_month_postage_total,postage_other,sender_country_no,last_month_files_qty,post_org_name,cur_year_postage_total,sender_city_no,sender_city_name,period_id,order_weight,customer_send_time_type,sender_no,last_year_postage_total,last_year_all_postage_total,sender_county_no,last_year_collection_qty,payment_amount,goods_qty,total_current_charges,is_new_customer,cur_year_collection_total,daily_effective_person,postage_paid,total_charge_owed,last_month_delivery_qty,sender,last_month_collection_qty,files_qty,total_prepaid_charges,unpaid_amount,postage_standard)value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertPs = connection.prepareStatement(insertSQL);
        System.out.println("insert: " + insertPs.toString());
        
		int insertCount = 0;
		for(CustomMonth obj : map.values()) {
			insertPs.setBigDecimal(1, obj.getLast_year_total_qty());
			insertPs.setString(2, obj.getSender_district_no());
			insertPs.setBigDecimal(3, obj.getReal_weight());
			insertPs.setString(4, obj.getPost_org_no());
			insertPs.setBigDecimal(5, obj.getPostage_total());
			insertPs.setBigDecimal(6, obj.getMonth_delivery_qty());
			insertPs.setBigDecimal(7, obj.getCustomer_send_qty1());
			insertPs.setBigDecimal(8, obj.getCustomer_send_qty3());
			insertPs.setBigDecimal(9, obj.getFee_weight());
			insertPs.setBigDecimal(10, obj.getCustomer_send_qty2());
			insertPs.setLong(11, obj.getSender_warehouse_id());
			insertPs.setString(12, obj.getSender_warehouse_name());
			insertPs.setBigDecimal(13, obj.getLast_month_goods_qty());
			insertPs.setLong(14, obj.getSender_id());
			insertPs.setString(15, obj.getSender_country_name());
			insertPs.setBigDecimal(16, obj.getCollected_qty());
			insertPs.setBigDecimal(17, obj.getLast_month_postage_total());
			insertPs.setBigDecimal(18, obj.getPostage_other());
			insertPs.setString(19, obj.getSender_country_no());
			insertPs.setBigDecimal(20, obj.getLast_month_files_qty());
			insertPs.setString(21, obj.getPost_org_name());
			insertPs.setBigDecimal(22, obj.getCur_year_postage_total());
			insertPs.setString(23, obj.getSender_city_no());
			insertPs.setString(24, obj.getSender_city_name());
			insertPs.setString(25, obj.getPeriod_id());
			insertPs.setBigDecimal(26, obj.getOrder_weight());
			insertPs.setString(27, obj.getCustomer_send_time_type());
			insertPs.setString(28, obj.getSender_no());
			insertPs.setBigDecimal(29, obj.getLast_year_postage_total());
			insertPs.setBigDecimal(30, obj.getLast_year_all_postage_total());
			insertPs.setString(31, obj.getSender_county_no());
			insertPs.setBigDecimal(32, obj.getLast_year_collection_qty());
			insertPs.setBigDecimal(33, obj.getPayment_amount());
			insertPs.setBigDecimal(34, obj.getGoods_qty());
			insertPs.setBigDecimal(35, obj.getTotal_current_charges());
			insertPs.setBigDecimal(36, obj.getIs_new_customer());
			insertPs.setBigDecimal(37, obj.getCur_year_collection_total());
			insertPs.setBigDecimal(38, obj.getDaily_effective_person());
			insertPs.setBigDecimal(39, obj.getPostage_paid());
			insertPs.setBigDecimal(40, obj.getTotal_charge_owed());
			insertPs.setBigDecimal(41, obj.getLast_month_delivery_qty());
			insertPs.setString(42, obj.getSender());
			insertPs.setBigDecimal(43, obj.getLast_month_collection_qty());
			insertPs.setBigDecimal(44, obj.getFiles_qty());
			insertPs.setBigDecimal(45, obj.getTotal_prepaid_charges());
			insertPs.setBigDecimal(46, obj.getUnpaid_amount());
			insertPs.setBigDecimal(47, obj.getPostage_standard());
			insertPs.executeUpdate();
			insertCount++;
		}
		rs.close();
		queryPs.close();
		insertPs.close();
		System.out.println("写入dm_customer_month_revenue_t" + " 记录数:" + insertCount);
		
		Map<String,String> transferMap = new HashMap<>();
		transferMap.put("tableName", "dm_customer_month_revenue_t");
		String selectSql = "select `post_org_no`,`post_org_name`,`sender_country_no`,`sender_country_name`,`sender_province_no`,`sender_province_name`,`sender_city_no`,`sender_city_name`,`sender_county_no`,`sender_county_name`,`sender_district_no`,`sender_id`,`sender_no`,`sender`,`sender_warehouse_id`,`sender_warehouse_name`,`period_id`,`order_weight`,`real_weight`,`fee_weight`,`postage_total`,`last_month_postage_total`,`postage_standard`,`postage_paid`,`postage_other`,`total_current_charges`,`total_charge_owed`,`total_prepaid_charges`,`unpaid_amount`,`payment_amount`,`collected_qty`,`last_month_collection_qty`,`month_delivery_qty`,`last_month_delivery_qty`,`daily_effective_person`,`files_qty`,`last_month_files_qty`,`goods_qty`,`last_month_goods_qty`,`is_new_customer`,`customer_send_time_type`,`customer_send_qty1`,`customer_send_qty2`,`customer_send_qty3`,`cur_year_collection_total`,`last_year_collection_qty`,`last_year_total_qty`,`cur_year_postage_total`,`last_year_postage_total`,`last_year_all_postage_total`,`created_date`,`created_by`,`extend_column`,`create_user`,`create_date`,`modify_user`,`modify_date` from dm_customer_month_revenue_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		selectSql = Tools.parse(selectSql, params);
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", deleteSQL);
		HttpUtil.upload(transferMap);
	}

}
