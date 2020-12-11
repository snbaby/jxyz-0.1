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
 * 区域日汇总 输入表：sdi_jxyz_pkp_waybill_base_，dwr_jxyz_emp_d，dwr_jxyz_department_d
 * 输出表：dwr_regional_daily_collection_t
 * 
 * @author xiaoxin
 *
 */
public class RegionDaliyExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		// 删除目标数据(从全局变量里取开始时间和结束时间)
		String deleteSQL = "delete from dwr_regional_daily_collection_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		// 替换变量
		Map<String, String> params = new HashMap<String, String>();
		params.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.LAST_DAY));
		params.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.CURR_DAY));
		deleteSQL = Tools.parse(deleteSQL, params);

		PreparedStatement deletePs = connection.prepareStatement(deleteSQL);
		int num = deletePs.executeUpdate();
		System.out.println("删除: " + num);
		deletePs.close();

		String querySQL = "SELECT \r\n" + 
				"sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",receiver_country_no\r\n" + 
				",receiver_country_name\r\n" + 
				",receiver_province_no\r\n" + 
				",receiver_province_name\r\n" + 
				",post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",period_id	\r\n" + 
				",sum(order_weight                   ) order_weight\r\n" + 
				",sum(real_weight                    ) real_weight\r\n" + 
				",sum(fee_weight                     ) fee_weight\r\n" + 
				",sum(postage_total                  ) postage_total\r\n" + 
				",sum(yesterday_postage_total        ) yesterday_postage_total\r\n" + 
				",sum(postage_standard               ) postage_standard\r\n" + 
				",sum(postage_paid                   ) postage_paid\r\n" + 
				",sum(postage_other                  ) postage_other\r\n" + 
				",sum(total_current_charges          ) total_current_charges\r\n" + 
				",sum(total_charge_owed              ) total_charge_owed\r\n" + 
				",sum(total_prepaid_charges          ) total_prepaid_charges\r\n" + 
				",sum(unpaid_amount                  ) unpaid_amount\r\n" + 
				",sum(payment_amount                 ) payment_amount\r\n" + 
				",sum( collected_qty                 )  collected_qty\r\n" + 
				",sum(yesterday_collection_qty       ) yesterday_collection_qty\r\n" + 
				",sum(delivery_qty                   ) delivery_qty\r\n" + 
				",sum(yesterday_delivery_qty         ) yesterday_delivery_qty\r\n" + 
				",sum(files_qty                      ) files_qty\r\n" + 
				",sum(yesterday_files_qty            ) yesterday_files_qty\r\n" + 
				",sum(goods_qty                      ) goods_qty\r\n" + 
				",sum(yesterday_goods_qty            ) yesterday_goods_qty\r\n" + 
				",sum(files_revenue                  ) files_revenue\r\n" + 
				",sum(goods_revenue                  ) goods_revenue\r\n" + 
				"FROM\r\n" + 
				"(SELECT \r\n" + 
				"sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",receiver_country_no\r\n" + 
				",receiver_country_name\r\n" + 
				",receiver_province_no\r\n" + 
				",receiver_province_name\r\n" + 
				",post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",period_id	\r\n" + 
				",sum(order_weight                   ) order_weight\r\n" + 
				",sum(real_weight                    ) real_weight\r\n" + 
				",sum(fee_weight                     ) fee_weight\r\n" + 
				",sum(postage_total                  ) postage_total\r\n" + 
				",sum(yesterday_postage_total        ) yesterday_postage_total\r\n" + 
				",sum(postage_standard               ) postage_standard\r\n" + 
				",sum(postage_paid                   ) postage_paid\r\n" + 
				",sum(postage_other                  ) postage_other\r\n" + 
				",sum(total_current_charges          ) total_current_charges\r\n" + 
				",sum(total_charge_owed              ) total_charge_owed\r\n" + 
				",sum(total_prepaid_charges          ) total_prepaid_charges\r\n" + 
				",sum(unpaid_amount                  ) unpaid_amount\r\n" + 
				",sum(payment_amount                 ) payment_amount\r\n" + 
				",sum( collected_qty                 )  collected_qty\r\n" + 
				",sum(yesterday_collection_qty       ) yesterday_collection_qty\r\n" + 
				",sum(delivery_qty                   ) delivery_qty\r\n" + 
				",sum(yesterday_delivery_qty         ) yesterday_delivery_qty\r\n" + 
				",sum(files_qty                      ) files_qty\r\n" + 
				",sum(yesterday_files_qty            ) yesterday_files_qty\r\n" + 
				",sum(goods_qty                      ) goods_qty\r\n" + 
				",sum(yesterday_goods_qty            ) yesterday_goods_qty\r\n" + 
				",sum(files_revenue                  ) files_revenue\r\n" + 
				",sum(goods_revenue                  ) goods_revenue\r\n" + 
				"FROM \r\n" + 
				"(SELECT @num := IF(@waybill_no = t.waybill_no ,@num  +1 ,1) num\r\n" + 
				",@waybill_no := t.waybill_no waybill_no\r\n" + 
				",t.sender_country_no\r\n" + 
				",t.sender_country_name\r\n" + 
				",IFNULL(d.province_code,t.sender_province_no) as sender_province_no\r\n" + 
				",d.province_name as sender_province_name\r\n" + 
				",IFNULL(d.city_code,t.sender_city_no) as sender_city_no\r\n" + 
				",d.city_name as sender_city_name\r\n" + 
				",IFNULL(d.county_code,t.sender_county_no) as sender_county_no\r\n" + 
				",d.county_name as sender_county_name\r\n" + 
				",t.sender_district_no\r\n" + 
				",t.receiver_country_no\r\n" + 
				",t.receiver_country_name\r\n" + 
				",t.receiver_province_no\r\n" + 
				",t.receiver_province_name\r\n" + 
				",IFNULL(d.dept_code,t.post_org_no) post_org_no\r\n" + 
				",d.dept_name post_org_name\r\n" + 
				",DATE_FORMAT(t.biz_occur_date,'%Y-%m-%d') AS period_id\r\n" + 
				",IFNULL(t.order_weight,0)			order_weight\r\n" + 
				",IFNULL(t.real_weight,0)             real_weight\r\n" + 
				",IFNULL(t.fee_weight,0)              fee_weight\r\n" + 
				",IFNULL(t.postage_total,0)           postage_total\r\n" + 
				",0 AS yesterday_postage_total\r\n" + 
				",IFNULL(t.postage_standard,0)        postage_standard\r\n" + 
				",IFNULL(t.postage_paid,0)            postage_paid\r\n" + 
				",IFNULL(t.postage_other,0)           postage_other\r\n" + 
				",CASE WHEN t.settlement_mode = '1' THEN \r\n" + 
				"	IFNULL(t.postage_total,0)\r\n" + 
				" ELSE 0 END AS total_current_charges\r\n" + 
				",CASE WHEN t.settlement_mode = '2' THEN \r\n" + 
				"	IFNULL(t.postage_total,0)\r\n" + 
				" ELSE 0 END AS total_charge_owed\r\n" + 
				",CASE WHEN t.settlement_mode = '3' THEN \r\n" + 
				"	IFNULL(t.postage_total,0)\r\n" + 
				" ELSE 0 END AS total_prepaid_charges\r\n" + 
				" ,CASE WHEN t.payment_state = '0' THEN \r\n" + 
				"	IFNULL(t.postage_total,0)\r\n" + 
				"ELSE 0 END unpaid_amount\r\n" + 
				",CASE WHEN t.payment_state = '1' THEN \r\n" + 
				"	IFNULL(t.postage_total,0)\r\n" + 
				"ELSE 0 END payment_amount\r\n" + 
				",IF( t.waybill_no IS NULL ,0 ,1) collected_qty\r\n" + 
				",0 yesterday_collection_qty\r\n" + 
				",0 delivery_qty\r\n" + 
				" ,0 yesterday_delivery_qty\r\n" + 
				" ,CASE WHEN t.contents_attribute = '1' THEN \r\n" + 
				"	1 ELSE 0 END files_qty\r\n" + 
				" ,0 AS yesterday_files_qty\r\n" + 
				",CASE WHEN t.contents_attribute = '3' THEN \r\n" + 
				"	1 ELSE 0 END goods_qty\r\n" + 
				" ,0 AS yesterday_goods_qty\r\n" + 
				" ,CASE WHEN t.contents_attribute = '1' THEN \r\n" + 
				"	IFNULL(t.postage_total,0) ELSE 0 END files_revenue\r\n" + 
				",CASE WHEN t.contents_attribute = '3' THEN \r\n" + 
				"	IFNULL(t.postage_total,0) ELSE 0 END goods_revenue\r\n" + 
				"FROM sdi_jxyz_pkp_waybill_base_${C_YEAR} t \r\n" + 
				"		LEFT OUTER JOIN dwr_jxyz_department_d d\r\n" + 
				"		 ON d.dept_code = t.post_org_no\r\n" + 
				"		 INNER JOIN \r\n" + 
				"     (SELECT @waybill_no := '',@num := 0) t1\r\n" + 
				"WHERE biz_occur_date >= '${START_DATE}'\r\n" + 
				"AND biz_occur_date <= '${END_DATE}'\r\n" + 
				"AND sender_province_no = '360000'\r\n" + 
				"AND t.base_product_no = '11210'\r\n" + 
				"and ifnull(t.postage_total,0) > 0\r\n" + 
				"and t.waybill_no is not null\r\n" + 
//				"and t.is_deleted = '0'\r\n" + 
				"ORDER BY t.waybill_no,t.biz_occur_date DESC) f\r\n" + 
				"WHERE num = 1\r\n" + 
				"GROUP BY sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",receiver_country_no\r\n" + 
				",receiver_country_name\r\n" + 
				",receiver_province_no\r\n" + 
				",receiver_province_name\r\n" + 
				",post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",period_id\r\n" + 
				"UNION ALL\r\n" + 
				"SELECT \r\n" + 
				"sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",receiver_country_no\r\n" + 
				",receiver_country_name\r\n" + 
				",receiver_province_no\r\n" + 
				",receiver_province_name\r\n" + 
				",post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",period_id	\r\n" + 
				",sum(order_weight                   ) order_weight\r\n" + 
				",sum(real_weight                    ) real_weight\r\n" + 
				",sum(fee_weight                     ) fee_weight\r\n" + 
				",sum(postage_total                  ) postage_total\r\n" + 
				",sum(yesterday_postage_total        ) yesterday_postage_total\r\n" + 
				",sum(postage_standard               ) postage_standard\r\n" + 
				",sum(postage_paid                   ) postage_paid\r\n" + 
				",sum(postage_other                  ) postage_other\r\n" + 
				",sum(total_current_charges          ) total_current_charges\r\n" + 
				",sum(total_charge_owed              ) total_charge_owed\r\n" + 
				",sum(total_prepaid_charges          ) total_prepaid_charges\r\n" + 
				",sum(unpaid_amount                  ) unpaid_amount\r\n" + 
				",sum(payment_amount                 ) payment_amount\r\n" + 
				",sum( collected_qty                 )  collected_qty\r\n" + 
				",sum(yesterday_collection_qty       ) yesterday_collection_qty\r\n" + 
				",sum(delivery_qty                   ) delivery_qty\r\n" + 
				",sum(yesterday_delivery_qty         ) yesterday_delivery_qty\r\n" + 
				",sum(files_qty                      ) files_qty\r\n" + 
				",sum(yesterday_files_qty            ) yesterday_files_qty\r\n" + 
				",sum(goods_qty                      ) goods_qty\r\n" + 
				",sum(yesterday_goods_qty            ) yesterday_goods_qty\r\n" + 
				",sum(files_revenue                  ) files_revenue\r\n" + 
				",sum(goods_revenue                  ) goods_revenue\r\n" + 
				"FROM \r\n" + 
				"(SELECT @num1 := IF(@waybill_no1 = t.waybill_no ,@num1  +1 ,1) num\r\n" + 
				",@waybill_no1 := t.waybill_no waybill_no\r\n" + 
				",t.sender_country_no\r\n" + 
				",t.sender_country_name\r\n" + 
				",IFNULL(d.province_code,t.sender_province_no) as sender_province_no\r\n" + 
				",d.province_name as sender_province_name\r\n" + 
				",IFNULL(d.city_code,t.sender_city_no) as sender_city_no\r\n" + 
				",d.city_name as sender_city_name\r\n" + 
				",IFNULL(d.county_code,t.sender_county_no) as sender_county_no\r\n" + 
				",d.county_name as sender_county_name\r\n" + 
				",t.sender_district_no\r\n" + 
				",t.receiver_country_no\r\n" + 
				",t.receiver_country_name\r\n" + 
				",t.receiver_province_no\r\n" + 
				",t.receiver_province_name\r\n" + 
				",IFNULL(d.dept_code,t.post_org_no) post_org_no\r\n" + 
				",d.dept_name post_org_name\r\n" + 
				",ADDDATE(DATE_FORMAT(t.biz_occur_date,'%Y-%m-%d'),INTERVAL 1 DAY) AS period_id\r\n" + 
				",0.00			order_weight\r\n" + 
				",0.00             real_weight\r\n" + 
				",0.00            fee_weight\r\n" + 
				",0.00         postage_total\r\n" + 
				",IFNULL(t.postage_total,0)   AS yesterday_postage_total\r\n" + 
				",0.00       postage_standard\r\n" + 
				",0.00           postage_paid\r\n" + 
				",0.00           postage_other\r\n" + 
				",0.00 AS total_current_charges\r\n" + 
				",0.00 AS total_charge_owed\r\n" + 
				",0.00 AS total_prepaid_charges\r\n" + 
				",0.00 unpaid_amount\r\n" + 
				",0.00 payment_amount\r\n" + 
				",0 collected_qty\r\n" + 
				",IF( t.waybill_no IS NULL ,0 ,1) yesterday_collection_qty\r\n" + 
				",0 delivery_qty\r\n" + 
				",0 yesterday_delivery_qty\r\n" + 
				",0 files_qty\r\n" + 
				",CASE WHEN t.contents_attribute = '1' THEN \r\n" + 
				"	1 ELSE 0 END AS yesterday_files_qty\r\n" + 
				",0 goods_qty\r\n" + 
				",CASE WHEN t.contents_attribute = '3' THEN \r\n" + 
				"	1 ELSE 0 END AS yesterday_goods_qty\r\n" + 
				",0 files_revenue\r\n" + 
				",0 goods_revenue\r\n" + 
				"FROM sdi_jxyz_pkp_waybill_base_${C_YEAR} t \r\n" + 
				"		LEFT OUTER JOIN dwr_jxyz_department_d d\r\n" + 
				"		 ON d.dept_code = t.post_org_no\r\n" + 
				"		 INNER JOIN \r\n" + 
				"     (SELECT @waybill_no1 := '',@num1 := 0) t1\r\n" + 
				"WHERE t.biz_occur_date >= ADDDATE('${START_DATE}',INTERVAL -1 DAY)\r\n" + 
				"and t.biz_occur_date <= ADDDATE('${END_DATE}',INTERVAL -1 DAY)\r\n" + 
				"AND sender_province_no = '360000'\r\n" + 
				"AND t.base_product_no = '11210'\r\n" + 
				"and ifnull(t.postage_total,0) > 0\r\n" + 
				"and t.waybill_no is not null\r\n" + 
//				"and t.is_deleted = '0'\r\n" + 
				"ORDER BY t.waybill_no,t.biz_occur_date DESC) f\r\n" + 
				"WHERE num = 1\r\n" + 
				"GROUP BY sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",receiver_country_no\r\n" + 
				",receiver_country_name\r\n" + 
				",receiver_province_no\r\n" + 
				",receiver_province_name\r\n" + 
				",post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",period_id\r\n" + 
				"UNION ALL\r\n" + 
				"SELECT \r\n" + 
				"sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",receiver_country_no\r\n" + 
				",receiver_country_name\r\n" + 
				",receiver_province_no\r\n" + 
				",receiver_province_name\r\n" + 
				",post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",period_id	\r\n" + 
				",sum(order_weight                   ) order_weight\r\n" + 
				",sum(real_weight                    ) real_weight\r\n" + 
				",sum(fee_weight                     ) fee_weight\r\n" + 
				",sum(postage_total                  ) postage_total\r\n" + 
				",sum(yesterday_postage_total        ) yesterday_postage_total\r\n" + 
				",sum(postage_standard               ) postage_standard\r\n" + 
				",sum(postage_paid                   ) postage_paid\r\n" + 
				",sum(postage_other                  ) postage_other\r\n" + 
				",sum(total_current_charges          ) total_current_charges\r\n" + 
				",sum(total_charge_owed              ) total_charge_owed\r\n" + 
				",sum(total_prepaid_charges          ) total_prepaid_charges\r\n" + 
				",sum(unpaid_amount                  ) unpaid_amount\r\n" + 
				",sum(payment_amount                 ) payment_amount\r\n" + 
				",sum( collected_qty                 )  collected_qty\r\n" + 
				",sum(yesterday_collection_qty       ) yesterday_collection_qty\r\n" + 
				",sum(delivery_qty                   ) delivery_qty\r\n" + 
				",sum(yesterday_delivery_qty         ) yesterday_delivery_qty\r\n" + 
				",sum(files_qty                      ) files_qty\r\n" + 
				",sum(yesterday_files_qty            ) yesterday_files_qty\r\n" + 
				",sum(goods_qty                      ) goods_qty\r\n" + 
				",sum(yesterday_goods_qty            ) yesterday_goods_qty\r\n" + 
				",sum(files_revenue                  ) files_revenue\r\n" + 
				",sum(goods_revenue                  ) goods_revenue\r\n" + 
				"FROM \r\n" + 
				"(SELECT @num2 := IF(@waybill_no2 = t.waybill_no ,@num2  +1 ,1) num\r\n" + 
				",@waybill_no2 := t.waybill_no waybill_no\r\n" + 
				",t.sender_country_no\r\n" + 
				",t.sender_country_name\r\n" + 
				",IFNULL(d.province_code,t.sender_province_no) as sender_province_no\r\n" + 
				",d.province_name as sender_province_name\r\n" + 
				",IFNULL(d.city_code,t.sender_city_no) as sender_city_no\r\n" + 
				",d.city_name as sender_city_name\r\n" + 
				",IFNULL(d.county_code,t.sender_county_no) as sender_county_no\r\n" + 
				",d.county_name as sender_county_name\r\n" + 
				",t.sender_district_no\r\n" + 
				",t.receiver_country_no\r\n" + 
				",t.receiver_country_name\r\n" + 
				",t.receiver_province_no\r\n" + 
				",t.receiver_province_name\r\n" + 
				",IFNULL(d.dept_code,t.post_org_no) post_org_no\r\n" + 
				",d.dept_name post_org_name\r\n" + 
				",ADDDATE(DATE_FORMAT(t.biz_occur_date,'%Y-%m-%d'),INTERVAL 1 DAY) AS period_id\r\n" + 
				",0.00			order_weight\r\n" + 
				",0.00             real_weight\r\n" + 
				",0.00            fee_weight\r\n" + 
				",0.00         postage_total\r\n" + 
				",IFNULL(t.postage_total,0)   AS yesterday_postage_total\r\n" + 
				",0.00       postage_standard\r\n" + 
				",0.00           postage_paid\r\n" + 
				",0.00           postage_other\r\n" + 
				",0.00 AS total_current_charges\r\n" + 
				",0.00 AS total_charge_owed\r\n" + 
				",0.00 AS total_prepaid_charges\r\n" + 
				",0.00 unpaid_amount\r\n" + 
				",0.00 payment_amount\r\n" + 
				",0 collected_qty\r\n" + 
				",IF( t.waybill_no IS NULL ,0 ,1) yesterday_collection_qty\r\n" + 
				",0 delivery_qty\r\n" + 
				",0 yesterday_delivery_qty\r\n" + 
				",0 files_qty\r\n" + 
				",CASE WHEN t.contents_attribute = '1' THEN \r\n" + 
				"	1 ELSE 0 END AS yesterday_files_qty\r\n" + 
				",0 goods_qty\r\n" + 
				",CASE WHEN t.contents_attribute = '3' THEN \r\n" + 
				"	1 ELSE 0 END AS yesterday_goods_qty\r\n" + 
				",0 files_revenue\r\n" + 
				",0 goods_revenue\r\n" + 
				"FROM sdi_jxyz_pkp_waybill_base_${L_YEAR} t \r\n" + 
				"		LEFT OUTER JOIN dwr_jxyz_department_d d\r\n" + 
				"		 ON d.dept_code = t.post_org_no\r\n" + 
				"		 INNER JOIN \r\n" + 
				"     (SELECT @waybill_no2 := '',@num2 := 0) t1\r\n" + 
				"WHERE t.biz_occur_date >= ADDDATE('${START_DATE}',INTERVAL -1 DAY)\r\n" + 
				"and t.biz_occur_date <= ADDDATE('${END_DATE}',INTERVAL -1 DAY)\r\n" + 
				"AND sender_province_no = '360000'\r\n" + 
				"AND t.base_product_no = '11210'\r\n" + 
				"and ifnull(t.postage_total,0) > 0\r\n" + 
				"and t.waybill_no is not null\r\n" + 
//				"and t.is_deleted = '0'\r\n" + 
				"ORDER BY t.waybill_no,t.biz_occur_date DESC) f\r\n" + 
				"WHERE num = 1\r\n" + 
				"GROUP BY sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",receiver_country_no\r\n" + 
				",receiver_country_name\r\n" + 
				",receiver_province_no\r\n" + 
				",receiver_province_name\r\n" + 
				",post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",period_id) f\r\n" + 
				"GROUP BY sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",receiver_country_no\r\n" + 
				",receiver_country_name\r\n" + 
				",receiver_province_no\r\n" + 
				",receiver_province_name\r\n" + 
				",post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",period_id";
		// 替换变量
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("C_YEAR", (String) Application.GLOBAL_PARAM.get(Application.CURR_YEAR));
		params2.put("L_YEAR", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR));
		params2.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.LAST_DAY));
		params2.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.CURR_DAY));
		querySQL = Tools.parse(querySQL, params2);
        System.out.println("Region SQL: " + querySQL);
		PreparedStatement queryPs = connection.prepareStatement(querySQL);
         
		String insertSQL = "insert into dwr_regional_daily_collection_t(goods_revenue,delivery_qty,sender_district_no,real_weight,post_org_no,postage_total,fee_weight,sender_country_name,receiver_province_name,sender_province_no,sender_county_name,receiver_province_no,collected_qty,sender_country_no,postage_other,yesterday_postage_total,post_org_name,sender_city_no,sender_city_name,period_id,order_weight,yesterday_collection_qty,yesterday_goods_qty,sender_county_no,payment_amount,goods_qty,sender_province_name,total_current_charges,yesterday_delivery_qty,receiver_country_no,postage_paid,total_charge_owed,yesterday_files_qty,receiver_country_name,files_qty,files_revenue,total_prepaid_charges,unpaid_amount,postage_standard)value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement insertPs = connection.prepareStatement(insertSQL);

		ResultSet rs = queryPs.executeQuery();
		int count = 0;
		while (rs.next()) {
			insertPs.setBigDecimal(1, rs.getBigDecimal("goods_revenue"));
			insertPs.setInt(2, rs.getInt("delivery_qty"));
			insertPs.setString(3, rs.getString("sender_district_no"));
			insertPs.setBigDecimal(4, rs.getBigDecimal("real_weight"));
			insertPs.setString(5, rs.getString("post_org_no"));
			insertPs.setBigDecimal(6, rs.getBigDecimal("postage_total"));
			insertPs.setBigDecimal(7, rs.getBigDecimal("fee_weight"));
			insertPs.setString(8, rs.getString("sender_country_name"));
			insertPs.setString(9, rs.getString("receiver_province_name"));
			insertPs.setString(10, rs.getString("sender_province_no"));
			insertPs.setString(11, rs.getString("sender_county_name"));
			insertPs.setString(12, rs.getString("receiver_province_no"));
			insertPs.setInt(13, rs.getInt("collected_qty"));
			insertPs.setString(14, rs.getString("sender_country_no"));
			insertPs.setBigDecimal(15, rs.getBigDecimal("postage_other"));
			insertPs.setBigDecimal(16, rs.getBigDecimal("yesterday_postage_total"));
			insertPs.setString(17, rs.getString("post_org_name"));
			insertPs.setString(18, rs.getString("sender_city_no"));
			insertPs.setString(19, rs.getString("sender_city_name"));
			insertPs.setDate(20, rs.getDate("period_id"));
			insertPs.setBigDecimal(21, rs.getBigDecimal("order_weight"));
			insertPs.setInt(22, rs.getInt("yesterday_collection_qty"));
			insertPs.setInt(23, rs.getInt("yesterday_goods_qty"));
			insertPs.setString(24, rs.getString("sender_county_no"));
			insertPs.setBigDecimal(25, rs.getBigDecimal("payment_amount"));
			insertPs.setInt(26, rs.getInt("goods_qty"));
			insertPs.setString(27, rs.getString("sender_province_name"));
			insertPs.setBigDecimal(28, rs.getBigDecimal("total_current_charges"));
			insertPs.setInt(29, rs.getInt("yesterday_delivery_qty"));
			insertPs.setString(30, rs.getString("receiver_country_no"));
			insertPs.setBigDecimal(31, rs.getBigDecimal("postage_paid"));
			insertPs.setBigDecimal(32, rs.getBigDecimal("total_charge_owed"));
			insertPs.setInt(33, rs.getInt("yesterday_files_qty"));
			insertPs.setString(34, rs.getString("receiver_country_name"));
			insertPs.setInt(35, rs.getInt("files_qty"));
			insertPs.setBigDecimal(36, rs.getBigDecimal("files_revenue"));
			insertPs.setBigDecimal(37, rs.getBigDecimal("total_prepaid_charges"));
			insertPs.setBigDecimal(38, rs.getBigDecimal("unpaid_amount"));
			insertPs.setBigDecimal(39, rs.getBigDecimal("postage_standard"));
			insertPs.executeUpdate();
			count++;
		}
		rs.close();
		queryPs.close();
		insertPs.close();
		System.out.println("写入dwr_regional_daily_collection_t" + " 记录数:" + count);
		
		Map<String,String> transferMap = new HashMap<>();
		transferMap.put("tableName", "dwr_regional_daily_collection_t");
		String selectSql = "select `sender_country_no`,`sender_country_name`,`sender_province_no`,`sender_province_name`,`sender_city_no`,`sender_city_name`,`sender_county_no`,`sender_county_name`,`sender_district_no`,`receiver_country_no`,`receiver_country_name`,`receiver_province_no`,`receiver_province_name`,`receiver_city_no`,`receiver_city_name`,`receiver_county_no`,`receiver_county_name`,`receiver_district_no`,`post_org_no`,`post_org_name`,`period_id`,`order_weight`,`real_weight`,`fee_weight`,`postage_total`,`yesterday_postage_total`,`postage_standard`,`postage_paid`,`postage_other`,`total_current_charges`,`total_charge_owed`,`total_prepaid_charges`,`unpaid_amount`,`payment_amount`,`collected_qty`,`yesterday_collection_qty`,`delivery_qty`,`yesterday_delivery_qty`,`daily_effective_person`,`files_qty`,`yesterday_files_qty`,`goods_qty`,`yesterday_goods_qty`,`files_revenue`,`goods_revenue`,`created_date`,`created_by`,`extend_column`,`create_user`,`create_date`,`modify_user`,`modify_date` from dwr_regional_daily_collection_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		selectSql = Tools.parse(selectSql, params);
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", deleteSQL);
		HttpUtil.upload(transferMap);

	}

}
