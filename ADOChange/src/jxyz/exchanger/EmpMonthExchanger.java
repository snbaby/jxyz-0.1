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
 * 员工月统计 输入表：dwr_emp_daily_collection_t
 * 输出表：dm_emp_month_collection_t
 * 
 * @author xiaoxin
 *
 */
public class EmpMonthExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		// 删除目标数据(从全局变量里取开始时间和结束时间)
		String deleteSQL = "delete from dm_emp_month_collection_t where period_id >= ${START_DATE} and period_id <= ${END_DATE}";
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
				"	post_person_id,\r\n" + 
				"	post_person_no,\r\n" + 
				"	post_person_name,\r\n" + 
				"	section_code,\r\n" + 
				"	section_name,\r\n" + 
				"	period_id,\r\n" + 
				"	sum( last_year_collection_qty ) last_year_collection_qty,#去年同期揽收量\r\n" + 
				"	sum( last_year_postage_total ) last_year_postage_total,#去年同期收入\r\n" + 
				"	sum( last_year_total_qty ) last_year_total_qty,#去年累计到同日期揽收量\r\n" + 
				"	sum( last_year_all_postage_total ) last_year_all_postage_total,#去年累计收入\r\n" + 
				"	sum( cur_year_collection_total ) cur_year_collection_total,#今年累计揽收量\r\n" + 
				"	sum( cur_year_postage_total ) cur_year_postage_total,#今年年累计收入\r\n" + 
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
				"	sum( standard_express_collection ) standard_express_collection,\r\n" + 
				"	sum( international_sales_volume ) international_sales_volume,\r\n" + 
				"	sum( package_collection ) package_collection,\r\n" + 
				"	sum( standard_express_salary ) AS standard_express_salary,\r\n" + 
				"	sum( package_collection_salary ) AS package_collection_salary,\r\n" + 
				"	sum( international_sales__salary ) AS international_sales__salary \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		post_org_id,\r\n" + 
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
				"		post_person_id,\r\n" + 
				"		post_person_no,\r\n" + 
				"		post_person_name,\r\n" + 
				"		section_code,\r\n" + 
				"		section_name,\r\n" + 
				"		0 last_year_collection_qty,#去年同期揽收量\r\n" + 
				"		0 last_year_postage_total,#去年同期收入\r\n" + 
				"		0 last_year_total_qty,#去年累计到同日期揽收量\r\n" + 
				"		0 last_year_all_postage_total,#去年累计收入\r\n" + 
				"		0 cur_year_collection_total,#今年累计揽收量\r\n" + 
				"		0 cur_year_postage_total,#今年年累计收入\r\n" + 
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
				"		ifnull( standard_express_collection, 0 ) standard_express_collection,\r\n" + 
				"		ifnull( international_sales_volume, 0 ) international_sales_volume,\r\n" + 
				"		IFNULL( package_collection, 0 ) package_collection,\r\n" + 
				"		ifnull( standard_express_salary, 0 ) AS standard_express_salary,\r\n" + 
				"		ifnull( package_collection_salary, 0 ) AS package_collection_salary,\r\n" + 
				"		ifnull( international_sales__salary, 0 ) AS international_sales__salary \r\n" + 
				"	FROM\r\n" + 
				"		dwr_emp_daily_collection_t \r\n" + 
				"	WHERE\r\n" + 
				"		period_id >= adddate( '${first_date}', INTERVAL 0 DAY ) \r\n" + 
				"		AND period_id <= adddate( '${last_date}', INTERVAL 0 DAY ) UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		post_org_id,\r\n" + 
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
				"		post_person_id,\r\n" + 
				"		post_person_no,\r\n" + 
				"		post_person_name,\r\n" + 
				"		section_code,\r\n" + 
				"		section_name,\r\n" + 
				"		0 last_year_collection_qty,#去年同期揽收量\r\n" + 
				"		0 last_year_postage_total,#去年同期收入\r\n" + 
				"		0 last_year_total_qty,#去年累计到同日期揽收量\r\n" + 
				"		0 last_year_all_postage_total,#去年累计收入\r\n" + 
				"		0 cur_year_collection_total,#今年累计揽收量\r\n" + 
				"		0 cur_year_postage_total,#今年年累计收入\r\n" + 
				"		date_format( date_add( period_id, INTERVAL 1 MONTH ), '%Y%m' ) AS period_id,\r\n" + 
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
				"		0 standard_express_collection,\r\n" + 
				"		0 international_sales_volume,\r\n" + 
				"		0 package_collection,\r\n" + 
				"		0 AS standard_express_salary,\r\n" + 
				"		0 AS package_collection_salary,\r\n" + 
				"		0 AS international_sales__salary \r\n" + 
				"	FROM\r\n" + 
				"		dwr_emp_daily_collection_t \r\n" + 
				"	WHERE\r\n" + 
				"		period_id >= adddate( '${lm_first_date}', INTERVAL 0 DAY ) \r\n" + 
				"		AND period_id <= adddate( '${lm_last_date}', INTERVAL 0 DAY )" + 
				"	UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		post_org_id,\r\n" + 
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
				"		post_person_id,\r\n" + 
				"		post_person_no,\r\n" + 
				"		post_person_name,\r\n" + 
				"		section_code,\r\n" + 
				"		section_name,\r\n" + 
				"		sum( t.collected_qty ) last_year_collection_qty,#去年同期揽收量\r\n" + 
				"		sum( t.postage_total ) last_year_postage_total,#去年同期收入\r\n" + 
				"		0 last_year_total_qty,#去年累计到同日期揽收量\r\n" + 
				"		0 last_year_all_postage_total,#去年累计收入\r\n" + 
				"		0 cur_year_collection_total,#今年累计揽收量\r\n" + 
				"		0 cur_year_postage_total,#今年年累计收入\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) AS period_id,\r\n" + 
				"		0 AS order_weight,\r\n" + 
				"		0 AS real_weight,\r\n" + 
				"		0 AS fee_weight,\r\n" + 
				"		0 AS postage_total,\r\n" + 
				"		0 AS last_month_postage_total,\r\n" + 
				"		0 AS postage_standard,\r\n" + 
				"		0 AS postage_paid,\r\n" + 
				"		0 AS postage_other,\r\n" + 
				"		0 AS total_current_charges,\r\n" + 
				"		0 AS total_charge_owed,\r\n" + 
				"		0 AS total_prepaid_charges,\r\n" + 
				"		0 AS unpaid_amount,\r\n" + 
				"		0 AS payment_amount,\r\n" + 
				"		0 AS collected_qty,\r\n" + 
				"		0 AS last_month_collection_qty,\r\n" + 
				"		0 AS month_delivery_qty,\r\n" + 
				"		0 AS last_month_delivery_qty,\r\n" + 
				"		0 AS standard_express_collection,\r\n" + 
				"		0 AS international_sales_volume,\r\n" + 
				"		0 AS package_collection,\r\n" + 
				"		0 AS standard_express_salary,\r\n" + 
				"		0 AS package_collection_salary,\r\n" + 
				"		0 AS international_sales__salary \r\n" + 
				"	FROM\r\n" + 
				"		dwr_emp_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${last_year_same_month_start}' \r\n" + 
				"		AND t.period_id <= '${last_year_same_date}' \r\n" + 
				"	GROUP BY\r\n" + 
				"post_org_id,\r\n" + 
				"post_org_no,\r\n" + 
				"org_drds_code,\r\n" + 
				"post_org_name,\r\n" + 
				"sender_country_no,\r\n" + 
				"sender_country_name,\r\n" + 
				"sender_province_no,\r\n" + 
				"sender_province_name,\r\n" + 
				"sender_city_no,\r\n" + 
				"sender_city_name,\r\n" + 
				"sender_county_no,\r\n" + 
				"sender_county_name,\r\n" + 
				"sender_district_no,\r\n" + 
				"post_person_id,\r\n" + 
				"post_person_no,\r\n" + 
				"post_person_name,\r\n" + 
				"section_code,\r\n" + 
				"section_name,\r\n" + 
				"DATE_FORMAT('${last_date}','%Y%m')" +
				"		UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		post_org_id,\r\n" + 
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
				"		post_person_id,\r\n" + 
				"		post_person_no,\r\n" + 
				"		post_person_name,\r\n" + 
				"		section_code,\r\n" + 
				"		section_name,\r\n" + 
				"		0 last_year_collection_qty,#去年同期揽收量\r\n" + 
				"		0 last_year_postage_total,#去年同期收入\r\n" + 
				"		sum( t.collected_qty ) last_year_total_qty,#去年累计到同日期揽收量\r\n" + 
				"		sum( t.postage_total ) last_year_all_postage_total,#去年累计收入\r\n" + 
				"		0 cur_year_collection_total,#今年累计揽收量\r\n" + 
				"		0 cur_year_postage_total,#今年年累计收入\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ),\r\n" + 
				"		0 AS order_weight,\r\n" + 
				"		0 AS real_weight,\r\n" + 
				"		0 AS fee_weight,\r\n" + 
				"		0 AS postage_total,\r\n" + 
				"		0 AS last_month_postage_total,\r\n" + 
				"		0 AS postage_standard,\r\n" + 
				"		0 AS postage_paid,\r\n" + 
				"		0 AS postage_other,\r\n" + 
				"		0 AS total_current_charges,\r\n" + 
				"		0 AS total_charge_owed,\r\n" + 
				"		0 AS total_prepaid_charges,\r\n" + 
				"		0 AS unpaid_amount,\r\n" + 
				"		0 AS payment_amount,\r\n" + 
				"		0 AS collected_qty,\r\n" + 
				"		0 AS last_month_collection_qty,\r\n" + 
				"		0 AS month_delivery_qty,\r\n" + 
				"		0 AS last_month_delivery_qty,\r\n" + 
				"		0 AS standard_express_collection,\r\n" + 
				"		0 AS international_sales_volume,\r\n" + 
				"		0 AS package_collection,\r\n" + 
				"		0 AS standard_express_salary,\r\n" + 
				"		0 AS package_collection_salary,\r\n" + 
				"		0 AS international_sales__salary \r\n" + 
				"	FROM\r\n" + 
				"		dwr_emp_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${last_year_start}' \r\n" + 
				"		AND t.period_id <= '${last_year_same_date}' \r\n" + 
				"	GROUP BY\r\n" + 
				"post_org_id,\r\n" + 
				"post_org_no,\r\n" + 
				"org_drds_code,\r\n" + 
				"post_org_name,\r\n" + 
				"sender_country_no,\r\n" + 
				"sender_country_name,\r\n" + 
				"sender_province_no,\r\n" + 
				"sender_province_name,\r\n" + 
				"sender_city_no,\r\n" + 
				"sender_city_name,\r\n" + 
				"sender_county_no,\r\n" + 
				"sender_county_name,\r\n" + 
				"sender_district_no,\r\n" + 
				"post_person_id,\r\n" + 
				"post_person_no,\r\n" + 
				"post_person_name,\r\n" + 
				"section_code,\r\n" + 
				"section_name,\r\n" + 
				"DATE_FORMAT('${last_date}','%Y%m')" +
				"		UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		post_org_id,\r\n" + 
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
				"		post_person_id,\r\n" + 
				"		post_person_no,\r\n" + 
				"		post_person_name,\r\n" + 
				"		section_code,\r\n" + 
				"		section_name,\r\n" + 
				"		sum( t.collected_qty ) last_year_collection_qty,#去年同期揽收量\r\n" + 
				"		sum( t.postage_total ) last_year_postage_total,#去年同期收入\r\n" + 
				"		0 last_year_total_qty,#去年累计到同日期揽收量\r\n" + 
				"		0 last_year_all_postage_total,#去年累计收入\r\n" + 
				"		0 cur_year_collection_total,#今年累计揽收量\r\n" + 
				"		0 cur_year_postage_total,#今年年累计收入\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) AS period_id,\r\n" + 
				"		0 AS order_weight,\r\n" + 
				"		0 AS real_weight,\r\n" + 
				"		0 AS fee_weight,\r\n" + 
				"		0 AS postage_total,\r\n" + 
				"		0 AS last_month_postage_total,\r\n" + 
				"		0 AS postage_standard,\r\n" + 
				"		0 AS postage_paid,\r\n" + 
				"		0 AS postage_other,\r\n" + 
				"		0 AS total_current_charges,\r\n" + 
				"		0 AS total_charge_owed,\r\n" + 
				"		0 AS total_prepaid_charges,\r\n" + 
				"		0 AS unpaid_amount,\r\n" + 
				"		0 AS payment_amount,\r\n" + 
				"		0 AS collected_qty,\r\n" + 
				"		0 AS last_month_collection_qty,\r\n" + 
				"		0 AS month_delivery_qty,\r\n" + 
				"		0 AS last_month_delivery_qty,\r\n" + 
				"		0 AS standard_express_collection,\r\n" + 
				"		0 AS international_sales_volume,\r\n" + 
				"		0 AS package_collection,\r\n" + 
				"		0 AS standard_express_salary,\r\n" + 
				"		0 AS package_collection_salary,\r\n" + 
				"		0 AS international_sales__salary \r\n" + 
				"	FROM\r\n" + 
				"		dwr_emp_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= ADDDATE( '${first_date}', INTERVAL - 1 YEAR ) \r\n" + 
				"		AND t.period_id <= ADDDATE( LAST_DAY( '${first_date}' ), INTERVAL - 1 YEAR ) \r\n" + 
				"	GROUP BY\r\n" + 
				"post_org_id,\r\n" + 
				"post_org_no,\r\n" + 
				"org_drds_code,\r\n" + 
				"post_org_name,\r\n" + 
				"sender_country_no,\r\n" + 
				"sender_country_name,\r\n" + 
				"sender_province_no,\r\n" + 
				"sender_province_name,\r\n" + 
				"sender_city_no,\r\n" + 
				"sender_city_name,\r\n" + 
				"sender_county_no,\r\n" + 
				"sender_county_name,\r\n" + 
				"sender_district_no,\r\n" + 
				"post_person_id,\r\n" + 
				"post_person_no,\r\n" + 
				"post_person_name,\r\n" + 
				"section_code,\r\n" + 
				"section_name,\r\n" + 
				"DATE_FORMAT('${last_date}','%Y%m')" +
				"		UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		post_org_id,\r\n" + 
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
				"		post_person_id,\r\n" + 
				"		post_person_no,\r\n" + 
				"		post_person_name,\r\n" + 
				"		section_code,\r\n" + 
				"		section_name,\r\n" + 
				"		0 last_year_collection_qty,#去年同期揽收量\r\n" + 
				"		0 last_year_postage_total,#去年同期收入\r\n" + 
				"		sum( t.collected_qty ) last_year_total_qty,#去年累计到同日期揽收量\r\n" + 
				"		sum( t.postage_total ) last_year_all_postage_total,#去年累计收入\r\n" + 
				"		0 cur_year_collection_total,#今年累计揽收量\r\n" + 
				"		0 cur_year_postage_total,#今年年累计收入\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) AS period_id,\r\n" + 
				"		0 AS order_weight,\r\n" + 
				"		0 AS real_weight,\r\n" + 
				"		0 AS fee_weight,\r\n" + 
				"		0 AS postage_total,\r\n" + 
				"		0 AS last_month_postage_total,\r\n" + 
				"		0 AS postage_standard,\r\n" + 
				"		0 AS postage_paid,\r\n" + 
				"		0 AS postage_other,\r\n" + 
				"		0 AS total_current_charges,\r\n" + 
				"		0 AS total_charge_owed,\r\n" + 
				"		0 AS total_prepaid_charges,\r\n" + 
				"		0 AS unpaid_amount,\r\n" + 
				"		0 AS payment_amount,\r\n" + 
				"		0 AS collected_qty,\r\n" + 
				"		0 AS last_month_collection_qty,\r\n" + 
				"		0 AS month_delivery_qty,\r\n" + 
				"		0 AS last_month_delivery_qty,\r\n" + 
				"		0 AS standard_express_collection,\r\n" + 
				"		0 AS international_sales_volume,\r\n" + 
				"		0 AS package_collection,\r\n" + 
				"		0 AS standard_express_salary,\r\n" + 
				"		0 AS package_collection_salary,\r\n" + 
				"		0 AS international_sales__salary \r\n" + 
				"	FROM\r\n" + 
				"		dwr_emp_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${last_year_start}' \r\n" + 
				"		AND t.period_id <= ADDDATE( LAST_DAY( '${first_date}' ), INTERVAL - 1 YEAR ) \r\n" + 
				"	GROUP BY\r\n" + 
				"post_org_id,\r\n" + 
				"post_org_no,\r\n" + 
				"org_drds_code,\r\n" + 
				"post_org_name,\r\n" + 
				"sender_country_no,\r\n" + 
				"sender_country_name,\r\n" + 
				"sender_province_no,\r\n" + 
				"sender_province_name,\r\n" + 
				"sender_city_no,\r\n" + 
				"sender_city_name,\r\n" + 
				"sender_county_no,\r\n" + 
				"sender_county_name,\r\n" + 
				"sender_district_no,\r\n" + 
				"post_person_id,\r\n" + 
				"post_person_no,\r\n" + 
				"post_person_name,\r\n" + 
				"section_code,\r\n" + 
				"section_name,\r\n" + 
				"DATE_FORMAT('${last_date}','%Y%m')" +
				"	UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		post_org_id,\r\n" + 
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
				"		post_person_id,\r\n" + 
				"		post_person_no,\r\n" + 
				"		post_person_name,\r\n" + 
				"		section_code,\r\n" + 
				"		section_name,\r\n" + 
				"		0 last_year_collection_qty,#去年同期揽收量\r\n" + 
				"		0 last_year_postage_total,#去年同期收入\r\n" + 
				"		0 last_year_total_qty,#去年累计到同日期揽收量\r\n" + 
				"		0 last_year_all_postage_total,#去年累计收入\r\n" + 
				"		sum( t.collected_qty ) cur_year_collection_total,#今年累计揽收量\r\n" + 
				"		sum( t.postage_total ) cur_year_postage_total,#今年年累计收入\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) AS period_id,\r\n" + 
				"		0 AS order_weight,\r\n" + 
				"		0 AS real_weight,\r\n" + 
				"		0 AS fee_weight,\r\n" + 
				"		0 AS postage_total,\r\n" + 
				"		0 AS last_month_postage_total,\r\n" + 
				"		0 AS postage_standard,\r\n" + 
				"		0 AS postage_paid,\r\n" + 
				"		0 AS postage_other,\r\n" + 
				"		0 AS total_current_charges,\r\n" + 
				"		0 AS total_charge_owed,\r\n" + 
				"		0 AS total_prepaid_charges,\r\n" + 
				"		0 AS unpaid_amount,\r\n" + 
				"		0 AS payment_amount,\r\n" + 
				"		0 AS collected_qty,\r\n" + 
				"		0 AS last_month_collection_qty,\r\n" + 
				"		0 AS month_delivery_qty,\r\n" + 
				"		0 AS last_month_delivery_qty,\r\n" + 
				"		0 AS standard_express_collection,\r\n" + 
				"		0 AS international_sales_volume,\r\n" + 
				"		0 AS package_collection,\r\n" + 
				"		0 AS standard_express_salary,\r\n" + 
				"		0 AS package_collection_salary,\r\n" + 
				"		0 AS international_sales__salary \r\n" + 
				"	FROM\r\n" + 
				"		dwr_emp_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${curr_year_start}' \r\n" + 
				"		AND t.period_id <= '${last_date}' \r\n" + 
				"	GROUP BY\r\n" + 
				"post_org_id,\r\n" + 
				"post_org_no,\r\n" + 
				"org_drds_code,\r\n" + 
				"post_org_name,\r\n" + 
				"sender_country_no,\r\n" + 
				"sender_country_name,\r\n" + 
				"sender_province_no,\r\n" + 
				"sender_province_name,\r\n" + 
				"sender_city_no,\r\n" + 
				"sender_city_name,\r\n" + 
				"sender_county_no,\r\n" + 
				"sender_county_name,\r\n" + 
				"sender_district_no,\r\n" + 
				"post_person_id,\r\n" + 
				"post_person_no,\r\n" + 
				"post_person_name,\r\n" + 
				"section_code,\r\n" + 
				"section_name,\r\n" + 
				"DATE_FORMAT('${last_date}','%Y%m')" +
				"		UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		post_org_id,\r\n" + 
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
				"		post_person_id,\r\n" + 
				"		post_person_no,\r\n" + 
				"		post_person_name,\r\n" + 
				"		section_code,\r\n" + 
				"		section_name,\r\n" + 
				"		0 last_year_collection_qty,#去年同期揽收量\r\n" + 
				"		0 last_year_postage_total,#去年同期收入\r\n" + 
				"		0 last_year_total_qty,#去年累计到同日期揽收量\r\n" + 
				"		0 last_year_all_postage_total,#去年累计收入\r\n" + 
				"		sum( t.collected_qty ) cur_year_collection_total,#今年累计揽收量\r\n" + 
				"		sum( t.postage_total ) cur_year_postage_total,#今年年累计收入\r\n" + 
				"		DATE_FORMAT( '${last_date}', '%Y%m' ) AS period_id,\r\n" + 
				"		0 AS order_weight,\r\n" + 
				"		0 AS real_weight,\r\n" + 
				"		0 AS fee_weight,\r\n" + 
				"		0 AS postage_total,\r\n" + 
				"		0 AS last_month_postage_total,\r\n" + 
				"		0 AS postage_standard,\r\n" + 
				"		0 AS postage_paid,\r\n" + 
				"		0 AS postage_other,\r\n" + 
				"		0 AS total_current_charges,\r\n" + 
				"		0 AS total_charge_owed,\r\n" + 
				"		0 AS total_prepaid_charges,\r\n" + 
				"		0 AS unpaid_amount,\r\n" + 
				"		0 AS payment_amount,\r\n" + 
				"		0 AS collected_qty,\r\n" + 
				"		0 AS last_month_collection_qty,\r\n" + 
				"		0 AS month_delivery_qty,\r\n" + 
				"		0 AS last_month_delivery_qty,\r\n" + 
				"		0 AS standard_express_collection,\r\n" + 
				"		0 AS international_sales_volume,\r\n" + 
				"		0 AS package_collection,\r\n" + 
				"		0 AS standard_express_salary,\r\n" + 
				"		0 AS package_collection_salary,\r\n" + 
				"		0 AS international_sales__salary \r\n" + 
				"	FROM\r\n" + 
				"		dwr_emp_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${curr_year_start}' \r\n" + 
				"		AND t.period_id <= LAST_DAY( '${first_date}' ) \r\n" + 
				"	GROUP BY\r\n" + 
				"post_org_id,\r\n" + 
				"post_org_no,\r\n" + 
				"org_drds_code,\r\n" + 
				"post_org_name,\r\n" + 
				"sender_country_no,\r\n" + 
				"sender_country_name,\r\n" + 
				"sender_province_no,\r\n" + 
				"sender_province_name,\r\n" + 
				"sender_city_no,\r\n" + 
				"sender_city_name,\r\n" + 
				"sender_county_no,\r\n" + 
				"sender_county_name,\r\n" + 
				"sender_district_no,\r\n" + 
				"post_person_id,\r\n" + 
				"post_person_no,\r\n" + 
				"post_person_name,\r\n" + 
				"section_code,\r\n" + 
				"section_name,\r\n" + 
				"DATE_FORMAT('${last_date}','%Y%m')" +
				"	) f \r\n" + 
				"GROUP BY\r\n" + 
				"post_org_id,\r\n" + 
				"post_org_no,\r\n" + 
				"org_drds_code,\r\n" + 
				"post_org_name,\r\n" + 
				"sender_country_no,\r\n" + 
				"sender_country_name,\r\n" + 
				"sender_province_no,\r\n" + 
				"sender_province_name,\r\n" + 
				"sender_city_no,\r\n" + 
				"sender_city_name,\r\n" + 
				"sender_county_no,\r\n" + 
				"sender_county_name,\r\n" + 
				"sender_district_no,\r\n" + 
				"post_person_id,\r\n" + 
				"post_person_no,\r\n" + 
				"post_person_name,\r\n" + 
				"section_code,\r\n" + 
				"section_name,\r\n" + 
				"period_id";
		// 替换变量
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("first_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_FIRSTDAY));
		params2.put("last_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_LASTDAY));
		params2.put("lm_first_date", (String) Application.GLOBAL_PARAM.get(Application.LAST_MONTH_FIRSTDAY));
		params2.put("lm_last_date", (String) Application.GLOBAL_PARAM.get(Application.LAST_MONTH_LASTDAY));
		params2.put("last_year_same_date", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR_SAME_DATE));
		params2.put("last_year_same_month_start", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR_SAME_MONTH_START));
		params2.put("last_year_start", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR_START));
		params2.put("curr_year_start", (String) Application.GLOBAL_PARAM.get(Application.CURR_YEAR_START));
		querySQL = Tools.parse(querySQL, params2);
		System.out.println(querySQL);
		PreparedStatement queryPs = connection.prepareStatement(querySQL);
		

		String insertSQL = "insert into dm_emp_month_collection_t(last_year_total_qty,sender_district_no,post_org_no,real_weight,package_collection_salary,post_person_id,postage_total,month_delivery_qty,fee_weight,sender_country_name,package_collection,sender_province_no,sender_county_name,post_person_name,international_sales_volume,collected_qty,sender_country_no,last_month_postage_total,postage_other,post_org_name,cur_year_postage_total,sender_city_no,sender_city_name,period_id,order_weight,last_year_postage_total,last_year_all_postage_total,international_sales__salary,standard_express_collection,section_name,post_org_id,sender_county_no,last_year_collection_qty,payment_amount,sender_province_name,standard_express_salary,total_current_charges,post_person_no,cur_year_collection_total,postage_paid,total_charge_owed,org_drds_code,last_month_delivery_qty,last_month_collection_qty,total_prepaid_charges,unpaid_amount,section_code,postage_standard)value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertPs = connection.prepareStatement(insertSQL);

		ResultSet rs = queryPs.executeQuery();
		int count = 0;
		while (rs.next()) {
			insertPs.setInt(1, rs.getInt("last_year_total_qty"));
			insertPs.setString(2, rs.getString("sender_district_no"));
			insertPs.setString(3, rs.getString("post_org_no"));
			insertPs.setBigDecimal(4, rs.getBigDecimal("real_weight"));
			insertPs.setBigDecimal(5, rs.getBigDecimal("package_collection_salary"));
			insertPs.setInt(6, rs.getInt("post_person_id"));
			insertPs.setBigDecimal(7, rs.getBigDecimal("postage_total"));
			insertPs.setInt(8, rs.getInt("month_delivery_qty"));
			insertPs.setBigDecimal(9, rs.getBigDecimal("fee_weight"));
			insertPs.setString(10, rs.getString("sender_country_name"));
			insertPs.setInt(11, rs.getInt("package_collection"));
			insertPs.setString(12, rs.getString("sender_province_no"));
			insertPs.setString(13, rs.getString("sender_county_name"));
			insertPs.setString(14, rs.getString("post_person_name"));
			insertPs.setInt(15, rs.getInt("international_sales_volume"));
			insertPs.setInt(16, rs.getInt("collected_qty"));
			insertPs.setString(17, rs.getString("sender_country_no"));
			insertPs.setBigDecimal(18, rs.getBigDecimal("last_month_postage_total"));
			insertPs.setBigDecimal(19, rs.getBigDecimal("postage_other"));
			insertPs.setString(20, rs.getString("post_org_name"));
			insertPs.setBigDecimal(21, rs.getBigDecimal("cur_year_postage_total"));
			insertPs.setString(22, rs.getString("sender_city_no"));
			insertPs.setString(23, rs.getString("sender_city_name"));
			insertPs.setInt(24, rs.getInt("period_id"));
			insertPs.setBigDecimal(25, rs.getBigDecimal("order_weight"));
			insertPs.setBigDecimal(26, rs.getBigDecimal("last_year_postage_total"));
			insertPs.setBigDecimal(27, rs.getBigDecimal("last_year_all_postage_total"));
			insertPs.setBigDecimal(28, rs.getBigDecimal("international_sales__salary"));
			insertPs.setInt(29, rs.getInt("standard_express_collection"));
			insertPs.setString(30, rs.getString("section_name"));
			insertPs.setInt(31, rs.getInt("post_org_id"));
			insertPs.setString(32, rs.getString("sender_county_no"));
			insertPs.setInt(33, rs.getInt("last_year_collection_qty"));
			insertPs.setBigDecimal(34, rs.getBigDecimal("payment_amount"));
			insertPs.setString(35, rs.getString("sender_province_name"));
			insertPs.setBigDecimal(36, rs.getBigDecimal("standard_express_salary"));
			insertPs.setBigDecimal(37, rs.getBigDecimal("total_current_charges"));
			insertPs.setString(38, rs.getString("post_person_no"));
			insertPs.setInt(39, rs.getInt("cur_year_collection_total"));
			insertPs.setBigDecimal(40, rs.getBigDecimal("postage_paid"));
			insertPs.setBigDecimal(41, rs.getBigDecimal("total_charge_owed"));
			insertPs.setString(42, rs.getString("org_drds_code"));
			insertPs.setInt(43, rs.getInt("last_month_delivery_qty"));
			insertPs.setInt(44, rs.getInt("last_month_collection_qty"));
			insertPs.setBigDecimal(45, rs.getBigDecimal("total_prepaid_charges"));
			insertPs.setBigDecimal(46, rs.getBigDecimal("unpaid_amount"));
			insertPs.setString(47, rs.getString("section_code"));
			insertPs.setBigDecimal(48, rs.getBigDecimal("postage_standard"));
			insertPs.executeUpdate();
			count++;
		}
		rs.close();
		queryPs.close();
		insertPs.close();
		System.out.println("写入dm_emp_month_collection_t" + " 记录数:" + count);
		
		Map<String,String> transferMap = new HashMap<>();
		transferMap.put("tableName", "dm_emp_month_collection_t");
		String selectSql = "select `post_org_id`,`post_org_no`,`org_drds_code`,`post_org_name`,`sender_country_no`,`sender_country_name`,`sender_province_no`,`sender_province_name`,`sender_city_no`,`sender_city_name`,`sender_county_no`,`sender_county_name`,`sender_district_no`,`post_person_id`,`post_person_no`,`post_person_name`,`post_person_mobile`,`section_code`,`section_name`,`period_id`,`order_weight`,`real_weight`,`fee_weight`,`postage_total`,`last_month_postage_total`,`postage_standard`,`postage_paid`,`postage_other`,`total_current_charges`,`total_charge_owed`,`total_prepaid_charges`,`unpaid_amount`,`payment_amount`,`collected_qty`,`last_month_collection_qty`,`month_delivery_qty`,`last_month_delivery_qty`,`cur_year_collection_total`,`last_year_collection_qty`,`last_year_total_qty`,`cur_year_postage_total`,`last_year_postage_total`,`last_year_all_postage_total`,`standard_express_collection`,`international_sales_volume`,`standard_express_salary`,`package_collection_salary`,`international_sales__salary`,`emp_salary`,`package_collection`,`created_date`,`created_by`,`extend_column`,`create_user`,`create_date`,`modify_user`,`modify_date` from dm_emp_month_collection_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		selectSql = Tools.parse(selectSql, params);
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", deleteSQL);
		HttpUtil.upload(transferMap);
	}

}
