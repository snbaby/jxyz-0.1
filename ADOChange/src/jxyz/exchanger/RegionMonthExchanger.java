package jxyz.exchanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import jxyz.Application;
import jxyz.model.CustomMonth;
import jxyz.model.RegionMonth;
import jxyz.utils.HttpUtil;
import jxyz.utils.Tools;

/**
 * 区域月汇总 输入表：dwr_regional_daily_collection_t 输出表：dm_regional_month_collection_t
 * 
 * @author xiaoxin
 *
 */
public class RegionMonthExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		// 删除目标数据(从全局变量里取开始时间和结束时间)
		String deleteSQL = "delete from dm_regional_month_collection_t where period_id >= ${START_DATE} and period_id <= ${END_DATE}";
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
				"	tmp.sender_country_no,\r\n" + 
				"	tmp.sender_country_name,\r\n" + 
				"	tmp.sender_province_no,\r\n" + 
				"	tmp.sender_province_name,\r\n" + 
				"	tmp.sender_city_no,\r\n" + 
				"	tmp.sender_city_name,\r\n" + 
				"	tmp.sender_county_no,\r\n" + 
				"	tmp.sender_county_name,\r\n" + 
				"	tmp.sender_district_no,\r\n" + 
				"	tmp.receiver_country_no,\r\n" + 
				"	tmp.receiver_country_name,\r\n" + 
				"	tmp.receiver_province_no,\r\n" + 
				"	tmp.receiver_province_name,\r\n" + 
				"	tmp.receiver_city_no,\r\n" + 
				"	tmp.receiver_city_name,\r\n" + 
				"	tmp.receiver_county_no,\r\n" + 
				"	tmp.receiver_county_name,\r\n" + 
				"	tmp.receiver_district_no,\r\n" + 
				"	tmp.post_org_no,\r\n" + 
				"	tmp.post_org_name,\r\n" + 
				"	tmp.period_id,\r\n" + 
				"	sum( tmp.order_weight ) order_weight,\r\n" + 
				"	SUM( tmp.real_weight ) real_weight,\r\n" + 
				"	SUM( tmp.fee_weight ) fee_weight,\r\n" + 
				"	SUM( tmp.postage_total ) postage_total,\r\n" + 
				"	SUM( tmp.collected_qty ) collected_qty,\r\n" + 
				"	SUM( tmp.last_month_collection_qty ) last_month_collection_qty,\r\n" + 
				"	SUM( tmp.last_month_postage_total ) AS last_month_postage_total,\r\n" + 
				"	SUM( tmp.month_delivery_qty ) AS month_delivery_qty,\r\n" + 
				"	SUM( tmp.last_month_delivery_qty ) AS last_month_delivery_qty,\r\n" + 
				"	SUM( tmp.daily_effective_person ) daily_effective_person,\r\n" + 
				"	sum( tmp.files_qty ) files_qty,\r\n" + 
				"	SUM( tmp.last_month_files_qty ) AS last_month_files_qty,\r\n" + 
				"	SUM( tmp.goods_qty ) goods_qty,\r\n" + 
				"	SUM( tmp.last_month_goods_qty ) AS last_month_goods_qty,\r\n" + 
				"	sum( tmp.files_revenue ) files_revenue,\r\n" + 
				"	SUM( tmp.goods_revenue ) goods_revenue \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_province_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_county_name,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_country_name,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_province_name,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_city_name,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_county_name,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no,\r\n" + 
				"		t.post_org_name,\r\n" + 
				"		date_format('${last_date}', '%Y%m' ) period_id,\r\n" + 
				"		sum( t.order_weight ) order_weight,\r\n" + 
				"		SUM( t.real_weight ) real_weight,\r\n" + 
				"		SUM( t.fee_weight ) fee_weight,\r\n" + 
				"		SUM( t.postage_total ) postage_total,\r\n" + 
				"		0 AS last_month_postage_total,\r\n" + 
				"		SUM( collected_qty ) collected_qty,\r\n" + 
				"		0 last_month_collection_qty,\r\n" + 
				"		0 AS month_delivery_qty,\r\n" + 
				"		0 AS last_month_delivery_qty,\r\n" + 
				"		SUM( t.daily_effective_person ) daily_effective_person,\r\n" + 
				"		sum( t.files_qty ) files_qty,\r\n" + 
				"		0 AS last_month_files_qty,\r\n" + 
				"		SUM( t.goods_qty ) goods_qty,\r\n" + 
				"		0 AS last_month_goods_qty,\r\n" + 
				"		sum( t.files_revenue ) files_revenue,\r\n" + 
				"		SUM( t.goods_revenue ) goods_revenue \r\n" + 
				"	FROM\r\n" + 
				"		dwr_regional_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${first_date}' \r\n" + 
				"		AND t.period_id <= '${last_date}' \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_province_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_county_name,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_country_name,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_province_name,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_city_name,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_county_name,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no,\r\n" + 
				"       t.post_org_name, \r\n" +
                "       date_format('${last_date}','%Y%m')" +
				"		UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_province_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_county_name,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_country_name,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_province_name,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_city_name,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_county_name,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no,\r\n" + 
				"		t.post_org_name,\r\n" + 
				"		date_format( '${last_date}', '%Y%m' ) period_id,\r\n" + 
				"		0 order_weight,\r\n" + 
				"		0 real_weight,\r\n" + 
				"		0 fee_weight,\r\n" + 
				"		0 postage_total,\r\n" + 
				"		SUM( t.postage_total ) AS last_month_postage_total,\r\n" + 
				"		0 collected_qty,\r\n" + 
				"		SUM( collected_qty ) last_month_collection_qty,\r\n" + 
				"		0 AS month_delivery_qty,\r\n" + 
				"		0 AS last_month_delivery_qty,\r\n" + 
				"		0 daily_effective_person,\r\n" + 
				"		0 files_qty,\r\n" + 
				"		sum( t.files_qty ) AS last_month_files_qty,\r\n" + 
				"		0 goods_qty,\r\n" + 
				"		SUM( t.goods_qty ) AS last_month_goods_qty,\r\n" + 
				"		0 files_revenue,\r\n" + 
				"		0 goods_revenue \r\n" + 
				"	FROM\r\n" + 
				"		dwr_regional_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${lm_first_date}' \r\n" + 
				"		AND t.period_id <= '${lm_last_date}' \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_country_name,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_province_name,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_city_name,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_county_name,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_country_name,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_province_name,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_city_name,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_county_name,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no,\r\n" + 
				"       t.post_org_name, \r\n" +
                "       date_format('${last_date}','%Y%m')" +
				"	) tmp \r\n" + 
				"GROUP BY\r\n" + 
				"	tmp.sender_country_no,\r\n" + 
				"	tmp.sender_country_name,\r\n" + 
				"	tmp.sender_province_no,\r\n" + 
				"	tmp.sender_province_name,\r\n" + 
				"	tmp.sender_city_no,\r\n" + 
				"	tmp.sender_city_name,\r\n" + 
				"	tmp.sender_county_no,\r\n" + 
				"	tmp.sender_county_name,\r\n" + 
				"	tmp.sender_district_no,\r\n" + 
				"	tmp.receiver_country_no,\r\n" + 
				"	tmp.receiver_country_name,\r\n" + 
				"	tmp.receiver_province_no,\r\n" + 
				"	tmp.receiver_province_name,\r\n" + 
				"	tmp.receiver_city_no,\r\n" + 
				"	tmp.receiver_city_name,\r\n" + 
				"	tmp.receiver_county_no,\r\n" + 
				"	tmp.receiver_county_name,\r\n" + 
				"	tmp.post_org_no,\r\n" + 
				"	tmp.post_org_name,\r\n" +
				"	tmp.receiver_district_no,\r\n" + 
				"	tmp.period_id";
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
		Map<String, RegionMonth> map = new HashMap<>();
		while (rs.next()) {
			RegionMonth cm = new RegionMonth(rs);
			count++;
			map.put(cm.getGroupName(), cm);
			count++;
		}
		rs.close();
		queryPs.close();
		System.out.println("查询本月和上月同期: " + count);

		// 查找去年月同期和去年年同期
		querySQL = "SELECT\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_province_no,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	receiver_country_no,\r\n" + 
				"	receiver_province_no,\r\n" + 
				"	receiver_city_no,\r\n" + 
				"	receiver_county_no,\r\n" + 
				"	receiver_district_no,\r\n" + 
				"	post_org_no,\r\n" + 
				"	post_org_name,\r\n" + 
				"	period_id,\r\n" + 
				"	SUM( last_year_collection_qty ) last_year_collection_qty,\r\n" + 
				"	SUM( last_year_postage_total ) last_year_postage_total,\r\n" + 
				"	SUM( last_year_total_qty ) last_year_total_qty,\r\n" + 
				"	SUM( last_year_all_postage_total ) last_year_all_postage_total \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no, " +
				"		t.post_org_name, " +
				"		date_format( '${last_date}', '%Y%m' ) period_id,\r\n" + 
				"		SUM( t.collected_qty ) last_year_collection_qty,\r\n" + 
				"		SUM( t.postage_total ) last_year_postage_total,\r\n" + 
				"		0 last_year_total_qty,\r\n" + 
				"		0 last_year_all_postage_total \r\n" + 
				"	FROM\r\n" + 
				"		dwr_regional_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${last_year_same_month_start}' \r\n" + 
				"		AND t.period_id <= '${last_year_same_date}' \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no, " +
				"       t.post_org_name, \r\n" +
                "       date_format('${last_date}','%Y%m')" +
				" UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no, " +
				"		t.post_org_name, " +
				"		date_format( '${last_date}', '%Y%m' ) period_id,\r\n" + 
				"		0 last_year_collection_qty,\r\n" + 
				"		0 last_year_postage_total,\r\n" + 
				"		SUM( t.collected_qty ) last_year_total_qty,\r\n" + 
				"		SUM( t.postage_total ) last_year_all_postage_total \r\n" + 
				"	FROM\r\n" + 
				"		dwr_regional_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${last_year_start}' \r\n" + 
				"		AND t.period_id <= '${last_year_same_date}' \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no, " +
				"       t.post_org_name, \r\n" +
                "       date_format('${last_date}','%Y%m')" +
				" UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no, " +
				"		t.post_org_name, " +
				"		date_format( '${last_date}', '%Y%m' ) period_id,\r\n" + 
				"		SUM( t.collected_qty ) last_year_collection_qty,\r\n" + 
				"		SUM( t.postage_total ) last_year_postage_total,\r\n" + 
				"		0 last_year_total_qty,\r\n" + 
				"		0 last_year_all_postage_total \r\n" + 
				"	FROM\r\n" + 
				"		dwr_regional_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= ADDDATE( '${first_date}', INTERVAL - 1 YEAR ) \r\n" + 
				"		AND t.period_id <= ADDDATE( LAST_DAY( '${first_date}' ), INTERVAL - 1 YEAR ) \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no, " +
				"       t.post_org_name, \r\n" +
                "       date_format('${last_date}','%Y%m')" +
				" UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no, " +
				"		t.post_org_name, " +
				"		date_format( '${last_date}', '%Y%m' ) period_id,\r\n" + 
				"		0 last_year_collection_qty,\r\n" + 
				"		0 last_year_postage_total,\r\n" + 
				"		SUM( t.collected_qty ) last_year_total_qty,\r\n" + 
				"		SUM( t.postage_total ) last_year_all_postage_total \r\n" + 
				"	FROM\r\n" + 
				"		dwr_regional_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${last_year_start}' \r\n" + 
				"		AND t.period_id <= ADDDATE( LAST_DAY( '${first_date}' ), INTERVAL - 1 YEAR ) \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no, " +
				"       t.post_org_name, \r\n" +
                "       date_format('${last_date}','%Y%m')" +
				"	) k \r\n" + 
				"GROUP BY\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_province_no,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	receiver_country_no,\r\n" + 
				"	receiver_province_no,\r\n" + 
				"	receiver_city_no,\r\n" + 
				"	receiver_county_no,\r\n" + 
				"	receiver_district_no,\r\n" + 
				"	post_org_no,\r\n" + 
				"   post_org_name,\r\n" + 
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
            RegionMonth rm = new RegionMonth(rs);
            RegionMonth cm = map.get(rm.getGroupName());
			if (cm != null) {
				cm.setLast_year_collection_qty(rs.getBigDecimal("last_year_collection_qty"));
				cm.setLast_year_postage_total((rs.getBigDecimal("last_year_postage_total")));
				cm.setLast_year_total_qty(rs.getBigDecimal("last_year_total_qty"));
				cm.setLast_year_all_postage_total((rs.getBigDecimal("last_year_all_postage_total")));
				count2++;
			} else {
				System.out.println("未找到记录：ReagionMonthExchanger1＝" + rm.getGroupName());
			}
			
		}
		rs.close();
		queryPs.close();
		System.out.println("查询并更新去年: " + count2);

		// 查找今年同期和去年年同期
		querySQL = "SELECT\r\n" + 
				"	post_org_no,\r\n" + 
				"	post_org_name,\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_province_no,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	receiver_country_no,\r\n" + 
				"	receiver_province_no,\r\n" + 
				"	receiver_city_no,\r\n" + 
				"	receiver_county_no,\r\n" + 
				"	receiver_district_no,\r\n" + 
				"	period_id,\r\n" + 
				"	cur_year_collection_total,\r\n" + 
				"	cur_year_postage_total \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no,\r\n" + 
				"		t.post_org_name,\r\n" + 
				"		date_format( '${last_date}', '%Y%m' ) period_id,\r\n" + 
				"		SUM( t.collected_qty ) cur_year_collection_total,\r\n" + 
				"		SUM( t.postage_total ) cur_year_postage_total \r\n" + 
				"	FROM\r\n" + 
				"		dwr_regional_daily_collection_t t \r\n" + 
				"	WHERE\r\n" + 
				"		t.period_id >= '${curr_year_start}' \r\n" + 
				"		AND t.period_id <= '${last_date}' \r\n" + 
				"	GROUP BY\r\n" + 
				"		t.sender_country_no,\r\n" + 
				"		t.sender_province_no,\r\n" + 
				"		t.sender_city_no,\r\n" + 
				"		t.sender_county_no,\r\n" + 
				"		t.sender_district_no,\r\n" + 
				"		t.receiver_country_no,\r\n" + 
				"		t.receiver_province_no,\r\n" + 
				"		t.receiver_city_no,\r\n" + 
				"		t.receiver_county_no,\r\n" + 
				"		t.receiver_district_no,\r\n" + 
				"		t.post_org_no, \r\n" + 
				"       t.post_org_name, \r\n" +
                "       date_format('${last_date}','%Y%m')" +
				"	) k \r\n" + 
				"GROUP BY\r\n" + 
				"	sender_country_no,\r\n" + 
				"	sender_province_no,\r\n" + 
				"	sender_city_no,\r\n" + 
				"	sender_county_no,\r\n" + 
				"	sender_district_no,\r\n" + 
				"	receiver_country_no,\r\n" + 
				"	receiver_province_no,\r\n" + 
				"	receiver_city_no,\r\n" + 
				"	receiver_county_no,\r\n" + 
				"	receiver_district_no,\r\n" + 
				"	post_org_no,\r\n" + 
				"	post_org_name,\r\n" + 
				"	period_id";

		Map<String, String> params4 = new HashMap<String, String>();
		params4.put("last_date", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH_LASTDAY));
		params4.put("curr_year_start", (String) Application.GLOBAL_PARAM.get(Application.CURR_YEAR_START));
		querySQL = Tools.parse(querySQL, params4);
		queryPs = connection.prepareStatement(querySQL);
		System.out.println("今年SQL: " + querySQL);
		rs = queryPs.executeQuery();
		int count3 = 0;
		while (rs.next()) {
			RegionMonth rm = new RegionMonth(rs);
            RegionMonth cm = map.get(rm.getGroupName());
			if (cm != null) {
				cm.setCur_year_collection_total(rs.getBigDecimal("cur_year_collection_total"));
				cm.setCur_year_postage_total((rs.getBigDecimal("cur_year_postage_total")));
				count3++;
			} else {
				System.out.println("未找到记录：ReagionMonthExchanger2＝" + rm.getGroupName());
			}
		}
		rs.close();
		queryPs.close();
		System.out.println("查询并更新今年: " + count3);

		String insertSQL = "insert into dm_regional_month_collection_t(receiver_city_name,last_year_total_qty,sender_district_no,real_weight,postage_total,month_delivery_qty,fee_weight,last_month_goods_qty,sender_country_name,receiver_district_no,receiver_province_name,receiver_city_no,receiver_county_name,sender_province_no,sender_county_name,receiver_province_no,collected_qty,sender_country_no,last_month_postage_total,last_month_files_qty,cur_year_postage_total,sender_city_no,sender_city_name,period_id,order_weight,last_year_postage_total,last_year_all_postage_total,receiver_county_no,sender_county_no,last_year_collection_qty,goods_qty,sender_province_name,cur_year_collection_total,receiver_country_no,daily_effective_person,last_month_delivery_qty,receiver_country_name,last_month_collection_qty,files_qty,post_org_no,post_org_name)value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertPs = connection.prepareStatement(insertSQL);
		System.out.println("insert: " + insertPs.toString());

		int insertCount = 0;
		for (RegionMonth obj : map.values()) {
			insertPs.setString(1, obj.getReceiver_city_name());
			insertPs.setBigDecimal(2, obj.getLast_year_total_qty());
			insertPs.setString(3, obj.getSender_district_no());
			insertPs.setBigDecimal(4, obj.getReal_weight());
			insertPs.setBigDecimal(5, obj.getPostage_total());
			insertPs.setBigDecimal(6, obj.getMonth_delivery_qty());
			insertPs.setBigDecimal(7, obj.getFee_weight());
			insertPs.setBigDecimal(8, obj.getLast_month_goods_qty());
			insertPs.setString(9, obj.getSender_country_name());
			insertPs.setString(10, obj.getReceiver_district_no());
			insertPs.setString(11, obj.getReceiver_province_name());
			insertPs.setString(12, obj.getReceiver_city_no());
			insertPs.setString(13, obj.getReceiver_county_name());
			insertPs.setString(14, obj.getSender_province_no());
			insertPs.setString(15, obj.getSender_county_name());
			insertPs.setString(16, obj.getReceiver_province_no());
			insertPs.setBigDecimal(17, obj.getCollected_qty());
			insertPs.setString(18, obj.getSender_country_no());
			insertPs.setBigDecimal(19, obj.getLast_month_postage_total());
			insertPs.setBigDecimal(20, obj.getLast_month_files_qty());
			insertPs.setBigDecimal(21, obj.getCur_year_postage_total());
			insertPs.setString(22, obj.getSender_city_no());
			insertPs.setString(23, obj.getSender_city_name());
			insertPs.setString(24, obj.getPeriod_id());
			insertPs.setBigDecimal(25, obj.getOrder_weight());
			insertPs.setBigDecimal(26, obj.getLast_year_postage_total());
			insertPs.setBigDecimal(27, obj.getLast_year_all_postage_total());
			insertPs.setString(28, obj.getReceiver_county_no());
			insertPs.setString(29, obj.getSender_county_no());
			insertPs.setBigDecimal(30, obj.getLast_year_collection_qty());
			insertPs.setBigDecimal(31, obj.getGoods_qty());
			insertPs.setString(32, obj.getSender_province_name());
			insertPs.setBigDecimal(33, obj.getCur_year_collection_total());
			insertPs.setString(34, obj.getReceiver_country_no());
			insertPs.setBigDecimal(35, obj.getDaily_effective_person());
			insertPs.setBigDecimal(36, obj.getLast_month_delivery_qty());
			insertPs.setString(37, obj.getReceiver_country_name());
			insertPs.setBigDecimal(38, obj.getLast_month_collection_qty());
			insertPs.setBigDecimal(39, obj.getFiles_qty());
			insertPs.setString(40, obj.getPost_org_no());
			insertPs.setString(41, obj.getPost_org_name());
			insertPs.executeUpdate();
			insertCount++;
		}
		rs.close();
		queryPs.close();
		insertPs.close();
		System.out.println("写入dm_regional_month_collection_t" + " 记录数:" + insertCount);
		
		Map<String,String> transferMap = new HashMap<>();
		transferMap.put("tableName", "dm_regional_month_collection_t");
		String selectSql = "select `sender_country_no`,`sender_country_name`,`sender_province_no`,`sender_province_name`,`sender_city_no`,`sender_city_name`,`sender_county_no`,`sender_county_name`,`sender_district_no`,`receiver_country_no`,`receiver_country_name`,`receiver_province_no`,`receiver_province_name`,`receiver_city_no`,`receiver_city_name`,`receiver_county_no`,`receiver_county_name`,`receiver_district_no`,`post_org_no`,`post_org_name`,`period_id`,`order_weight`,`real_weight`,`fee_weight`,`postage_total`,`last_month_postage_total`,`postage_standard`,`postage_paid`,`postage_other`,`total_current_charges`,`total_charge_owed`,`total_prepaid_charges`,`unpaid_amount`,`payment_amount`,`collected_qty`,`last_month_collection_qty`,`month_delivery_qty`,`last_month_delivery_qty`,`daily_effective_person`,`files_qty`,`last_month_files_qty`,`goods_qty`,`last_month_goods_qty`,`cur_year_collection_total`,`last_year_collection_qty`,`last_year_total_qty`,`cur_year_postage_total`,`last_year_postage_total`,`last_year_all_postage_total`,`created_date`,`created_by`,`extend_column`,`create_user`,`create_date`,`modify_user`,`modify_date` from dm_regional_month_collection_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		selectSql = Tools.parse(selectSql, params);
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", deleteSQL);
		HttpUtil.upload(transferMap);

	}

}
