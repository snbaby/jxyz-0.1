package jxyz.exchanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import jxyz.Application;
import jxyz.utils.HttpUtil;
import jxyz.utils.TimeUtil;
import jxyz.utils.Tools;

/**
 * 处理客户日收入汇总
 * 输入表：sdi_jxyz_pkp_waybill_base_$, dwr_jxyz_department_d
 * 输出表：dwr_customer_daily_revenue_t
 * @author xiaoxin
 *
 */
public class CustomDaliyExchanger implements Exchanger{

	@Override
	public void process(Connection connection) throws Exception {
		// 删除目标数据(从全局变量里取开始时间和结束时间)
		String deleteSQL = "delete from dwr_customer_daily_revenue_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		// 替换变量
		Map<String, String> params = new HashMap<String, String>();
		params.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.LAST_DAY));
		params.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.CURR_DAY));
		deleteSQL = Tools.parse(deleteSQL, params);

		PreparedStatement deletePs = connection.prepareStatement(deleteSQL);
		int num = deletePs.executeUpdate();
		System.out.println("删除: " + num);
		deletePs.close();
		
		String querySQL = "SELECT  post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",sender_id\r\n" + 
				",sender_no\r\n" + 
				",sender\r\n" + 
				",sender_warehouse_id\r\n" + 
				",sender_warehouse_name\r\n" + 
				",period_id\r\n" + 
				",customer_send_time_type\r\n" + 
				",SUM(order_weight				) order_weight\r\n" + 
				",SUM(real_weight                ) real_weight\r\n" + 
				",SUM(fee_weight                 ) fee_weight\r\n" + 
				",SUM(postage_total              ) postage_total\r\n" + 
				",SUM(yesterday_postage_total    ) yesterday_postage_total\r\n" + 
				",SUM(postage_standard           ) postage_standard\r\n" + 
				",SUM(postage_paid               ) postage_paid\r\n" + 
				",SUM(postage_other              ) postage_other\r\n" + 
				",SUM(total_current_charges      ) total_current_charges\r\n" + 
				",SUM(total_charge_owed          ) total_charge_owed\r\n" + 
				",SUM(total_prepaid_charges      ) total_prepaid_charges\r\n" + 
				",SUM(unpaid_amount              ) unpaid_amount\r\n" + 
				",SUM(payment_amount             ) payment_amount\r\n" + 
				",SUM(collected_qty              ) collected_qty\r\n" + 
				",SUM(yesterday_collection_qty   ) yesterday_collection_qty\r\n" + 
				",SUM(files_qty                  ) files_qty\r\n" + 
				",SUM(yesterday_files_qty        ) yesterday_files_qty\r\n" + 
				",SUM(goods_qty                  ) goods_qty\r\n" + 
				",SUM(yesterday_goods_qty        ) yesterday_goods_qty\r\n" + 
				",SUM(delivery_qty               ) delivery_qty\r\n" + 
				",SUM(yesterday_delivery_qty     ) yesterday_delivery_qty\r\n" + 
				",SUM(customer_send_qty1) customer_send_qty1\r\n" + 
				",SUM(customer_send_qty2) customer_send_qty2\r\n" + 
				",SUM(customer_send_qty3) customer_send_qty3\r\n" + 
				"FROM \r\n" + 
				"(SELECT  post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",sender_id\r\n" + 
				",sender_no\r\n" + 
				",sender\r\n" + 
				",sender_warehouse_id\r\n" + 
				",sender_warehouse_name\r\n" + 
				",period_id\r\n" + 
				",customer_send_time_type\r\n" + 
				",SUM(order_weight				) order_weight\r\n" + 
				",SUM(real_weight                ) real_weight\r\n" + 
				",SUM(fee_weight                 ) fee_weight\r\n" + 
				",SUM(postage_total              ) postage_total\r\n" + 
				",SUM(yesterday_postage_total    ) yesterday_postage_total\r\n" + 
				",SUM(postage_standard           ) postage_standard\r\n" + 
				",SUM(postage_paid               ) postage_paid\r\n" + 
				",SUM(postage_other              ) postage_other\r\n" + 
				",SUM(total_current_charges      ) total_current_charges\r\n" + 
				",SUM(total_charge_owed          ) total_charge_owed\r\n" + 
				",SUM(total_prepaid_charges      ) total_prepaid_charges\r\n" + 
				",SUM(unpaid_amount              ) unpaid_amount\r\n" + 
				",SUM(payment_amount             ) payment_amount\r\n" + 
				",SUM(collected_qty              ) collected_qty\r\n" + 
				",SUM(yesterday_collection_qty   ) yesterday_collection_qty\r\n" + 
				",SUM(files_qty                  ) files_qty\r\n" + 
				",SUM(yesterday_files_qty        ) yesterday_files_qty\r\n" + 
				",SUM(goods_qty                  ) goods_qty\r\n" + 
				",SUM(yesterday_goods_qty        ) yesterday_goods_qty\r\n" + 
				",SUM(delivery_qty               ) delivery_qty\r\n" + 
				",SUM(yesterday_delivery_qty     ) yesterday_delivery_qty\r\n" + 
				",SUM(customer_send_qty1) customer_send_qty1\r\n" + 
				",SUM(customer_send_qty2) customer_send_qty2\r\n" + 
				",SUM(customer_send_qty3) customer_send_qty3\r\n" + 
				"FROM \r\n" + 
				"(SELECT @num := IF(@waybill_no = t.waybill_no ,@num  +1 ,1) num\r\n" + 
				",@waybill_no := t.waybill_no waybill_no\r\n" + 
				",t.post_org_no\r\n" + 
				",IFNULL(d.dept_name,t.post_org_name) post_org_name\r\n" + 
				",t.sender_country_no\r\n" + 
				",t.sender_country_name\r\n" + 
				",IFNULL(d.province_code,t.sender_province_no) as sender_province_no\r\n" + 
				",d.province_name as sender_province_name\r\n" + 
				",IFNULL(d.city_code,t.sender_city_no) as sender_city_no\r\n" + 
				",d.city_name as sender_city_name\r\n" + 
				",IFNULL(d.county_code,t.sender_county_no) as sender_county_no\r\n" + 
				",d.county_name as sender_county_name\r\n" + 
				",t.sender_district_no\r\n" + 
				",t.sender_id\r\n" + 
				",t.sender_no\r\n" + 
				",t.sender\r\n" + 
				",t.sender_warehouse_id\r\n" + 
				",t.sender_warehouse_name\r\n" + 
				",DATE_FORMAT(t.biz_occur_date,'%Y-%m-%d') as period_id\r\n" + 
				",ifnull(t.order_weight,0) as order_weight\r\n" + 
				",ifnull(t.real_weight,0) as real_weight\r\n" + 
				",ifnull(t.fee_weight,0) as fee_weight\r\n" + 
				",ifnull(t.postage_total,0) as postage_total\r\n" + 
				",0 as yesterday_postage_total\r\n" + 
				",ifnull(t.postage_standard,0) as postage_standard\r\n" + 
				",ifnull(t.postage_paid,0) as postage_paid\r\n" + 
				",ifnull(t.postage_other,0) as postage_other\r\n" + 
				",ifnull(t.settlement_mode,0) as settlement_mode\r\n" + 
				",ifnull(t.payment_state,0) as payment_state\r\n" + 
				",case when t.settlement_mode = '1' then ifnull(t.postage_total,0) else 0 end as total_current_charges\r\n" + 
				",case when t.settlement_mode = '2' then ifnull(t.postage_total,0) else 0 end as total_charge_owed\r\n" + 
				",case when t.settlement_mode = '3' then ifnull(t.postage_total,0) else 0 end as total_prepaid_charges\r\n" + 
				",case when t.payment_state ='0' then ifnull(t.postage_total,0)  else 0 end as unpaid_amount\r\n" + 
				",case when t.payment_state ='1' then ifnull(t.postage_total,0)  else 0 end as payment_amount\r\n" + 
				",IF( t.waybill_no IS NULL ,0 ,1 ) collected_qty\r\n" + 
				",0 as  yesterday_collection_qty\r\n" + 
				",case when t.contents_attribute = '1' then 1 else 0 end files_qty\r\n" + 
				",0 as yesterday_files_qty\r\n" + 
				",case when t.contents_attribute = '3' then 1 else 0 end goods_qty\r\n" + 
				",0 as yesterday_goods_qty\r\n" + 
				",0 delivery_qty\r\n" + 
				",0 yesterday_delivery_qty\r\n" + 
				",case when hour(t.biz_occur_date) < 11 then 0\r\n" + 
				"     when hour(t.biz_occur_date) >= 11 and hour(t.biz_occur_date) <= 13 then 1\r\n" + 
				"		 else 2 \r\n" + 
				"		 end as customer_send_time_type\r\n" + 
				",case when hour(t.biz_occur_date) < 11 then 1 else 0 end as customer_send_qty1\r\n" + 
				",case when hour(t.biz_occur_date) >= 11 and hour(t.biz_occur_date) <= 13 then 1 else 0 end as customer_send_qty2\r\n" + 
				",case when hour(t.biz_occur_date) > 13 then 1 else 0 end as customer_send_qty3\r\n" + 
				"from sdi_jxyz_pkp_waybill_base_${C_YEAR} t LEFT OUTER JOIN \r\n" + 
				"     dwr_jxyz_department_d d\r\n" + 
				"ON d.dept_code = t.post_org_no \r\n" + 
				"INNER JOIN \r\n" + 
				"     (SELECT @waybill_no := '',@num := 0) t1\r\n" + 
				"where t.biz_occur_date >= '${START_DATE}'\r\n" + 
				"and t.biz_occur_date <= '${END_DATE}'\r\n" + 
				"AND t.sender_province_no = '360000'\r\n" + 
				"and ifnull(t.postage_total,0) > 0\r\n" + 
				"AND t.sender_type = '1'\r\n" + 
				"AND t.sender_no IS NOT  NULL \r\n" + 
//				"AND t.is_deleted = '0'\r\n" +
				"AND t.base_product_no = '11210'\r\n" + 
				"ORDER BY t.waybill_no,t.biz_occur_date DESC ) f \r\n" + 
				"WHERE num = 1 \r\n" + 
				"GROUP BY post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",sender_id\r\n" + 
				",sender_no\r\n" + 
				",sender\r\n" + 
				",sender_warehouse_id\r\n" + 
				",sender_warehouse_name\r\n" + 
				",period_id\r\n" + 
				",customer_send_time_type\r\n" + 
				"union all\r\n" + 
				"SELECT  post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",sender_id\r\n" + 
				",sender_no\r\n" + 
				",sender\r\n" + 
				",sender_warehouse_id\r\n" + 
				",sender_warehouse_name\r\n" + 
				",period_id\r\n" + 
				",customer_send_time_type\r\n" + 
				",SUM(order_weight				) order_weight\r\n" + 
				",SUM(real_weight                ) real_weight\r\n" + 
				",SUM(fee_weight                 ) fee_weight\r\n" + 
				",SUM(postage_total              ) postage_total\r\n" + 
				",SUM(yesterday_postage_total    ) yesterday_postage_total\r\n" + 
				",SUM(postage_standard           ) postage_standard\r\n" + 
				",SUM(postage_paid               ) postage_paid\r\n" + 
				",SUM(postage_other              ) postage_other\r\n" + 
				",SUM(total_current_charges      ) total_current_charges\r\n" + 
				",SUM(total_charge_owed          ) total_charge_owed\r\n" + 
				",SUM(total_prepaid_charges      ) total_prepaid_charges\r\n" + 
				",SUM(unpaid_amount              ) unpaid_amount\r\n" + 
				",SUM(payment_amount             ) payment_amount\r\n" + 
				",SUM(collected_qty              ) collected_qty\r\n" + 
				",SUM(yesterday_collection_qty   ) yesterday_collection_qty\r\n" + 
				",SUM(files_qty                  ) files_qty\r\n" + 
				",SUM(yesterday_files_qty        ) yesterday_files_qty\r\n" + 
				",SUM(goods_qty                  ) goods_qty\r\n" + 
				",SUM(yesterday_goods_qty        ) yesterday_goods_qty\r\n" + 
				",SUM(delivery_qty               ) delivery_qty\r\n" + 
				",SUM(yesterday_delivery_qty     ) yesterday_delivery_qty\r\n" + 
				",SUM(customer_send_qty1) customer_send_qty1\r\n" + 
				",SUM(customer_send_qty2) customer_send_qty2\r\n" + 
				",SUM(customer_send_qty3) customer_send_qty3 FROM \r\n" + 
				"(SELECT @num := IF(@waybill_no = t.waybill_no ,@num  +1 ,1) num\r\n" + 
				",@waybill_no := t.waybill_no waybill_no\r\n" + 
				",t.post_org_no\r\n" + 
				",IFNULL(d.dept_name,t.post_org_name) post_org_name\r\n" + 
				",t.sender_country_no\r\n" + 
				",t.sender_country_name\r\n" + 
				",IFNULL(d.province_code,t.sender_province_no) as sender_province_no\r\n" + 
				",d.province_name as sender_province_name\r\n" + 
				",IFNULL(d.city_code,t.sender_city_no) as sender_city_no\r\n" + 
				",d.city_name as sender_city_name\r\n" + 
				",IFNULL(d.county_code,t.sender_county_no) as sender_county_no\r\n" + 
				",d.county_name as sender_county_name\r\n" + 
				",t.sender_district_no\r\n" + 
				",t.sender_id\r\n" + 
				",t.sender_no\r\n" + 
				",t.sender\r\n" + 
				",t.sender_warehouse_id\r\n" + 
				",t.sender_warehouse_name\r\n" + 
				",ADDDATE(DATE_FORMAT(t.biz_occur_date,'%Y-%m-%d'),INTERVAL 1 DAY) as period_id\r\n" + 
				",0.00 as order_weight\r\n" + 
				",0.00 as real_weight\r\n" + 
				",0.00 as fee_weight\r\n" + 
				",0.00 as postage_total\r\n" + 
				",0.00	as yesterday_postage_total\r\n" + 
				",0.00 	as postage_standard\r\n" + 
				",0.00	as postage_paid\r\n" + 
				",0.00	as postage_other\r\n" + 
				",0.00	as total_current_charges\r\n" + 
				",0.00	as total_charge_owed\r\n" + 
				",0.00	as total_prepaid_charges\r\n" + 
				",0.00 unpaid_amount\r\n" + 
				",0.00 payment_amount\r\n" + 
				",0 collected_qty\r\n" + 
				",IF( t.waybill_no IS NULL ,0 ,1 ) as  yesterday_collection_qty\r\n" + 
				",0 files_qty\r\n" + 
				",case when t.contents_attribute = '1' then 1 else 0 end as yesterday_files_qty\r\n" + 
				",0 goods_qty\r\n" + 
				",case when t.contents_attribute = '3' then 1 else 0 end as yesterday_goods_qty\r\n" + 
				",0 delivery_qty\r\n" + 
				",0 yesterday_delivery_qty\r\n" + 
				",case when hour(t.biz_occur_date) < 11 then 0\r\n" + 
				"     when hour(t.biz_occur_date) >= 11 and hour(t.biz_occur_date) <= 13 then 1\r\n" + 
				"		 else 2 \r\n" + 
				"		 end as customer_send_time_type\r\n" + 
				",0 as customer_send_qty1\r\n" + 
				",0 as customer_send_qty2\r\n" + 
				",0 as customer_send_qty3\r\n" + 
				"from sdi_jxyz_pkp_waybill_base_${C_YEAR} t LEFT OUTER JOIN \r\n" + 
				"     dwr_jxyz_department_d d\r\n" + 
				"ON d.dept_code = t.post_org_no \r\n" + 
				"INNER JOIN \r\n" + 
				"     (SELECT @waybill_no := '',@num := 0) t1\r\n" + 
				"where t.biz_occur_date >= ADDDATE('${START_DATE}',INTERVAL -1 DAY)\r\n" + 
				"and t.biz_occur_date <= ADDDATE('${END_DATE}',INTERVAL -1 DAY)\r\n" + 
				"AND t.sender_province_no = '360000'\r\n" + 
				"and ifnull(t.postage_total,0) > 0\r\n" + 
				"AND t.sender_type = '1'\r\n" + 
				"AND t.sender_no IS NOT  NULL \r\n" + 
//				"AND t.is_deleted = '0'\r\n" +
				"AND t.base_product_no = '11210'\r\n" + 
				"ORDER BY t.waybill_no,t.biz_occur_date DESC ) f \r\n" + 
				"WHERE num = 1 \r\n" + 
				"GROUP BY post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",sender_id\r\n" + 
				",sender_no\r\n" + 
				",sender\r\n" + 
				",sender_warehouse_id\r\n" + 
				",sender_warehouse_name\r\n" + 
				",period_id\r\n" + 
				",customer_send_time_type\r\n" + 
				"union all\r\n" + 
				"SELECT  post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",sender_id\r\n" + 
				",sender_no\r\n" + 
				",sender\r\n" + 
				",sender_warehouse_id\r\n" + 
				",sender_warehouse_name\r\n" + 
				",period_id\r\n" + 
				",customer_send_time_type\r\n" + 
				",SUM(order_weight				) order_weight\r\n" + 
				",SUM(real_weight                ) real_weight\r\n" + 
				",SUM(fee_weight                 ) fee_weight\r\n" + 
				",SUM(postage_total              ) postage_total\r\n" + 
				",SUM(yesterday_postage_total    ) yesterday_postage_total\r\n" + 
				",SUM(postage_standard           ) postage_standard\r\n" + 
				",SUM(postage_paid               ) postage_paid\r\n" + 
				",SUM(postage_other              ) postage_other\r\n" + 
				",SUM(total_current_charges      ) total_current_charges\r\n" + 
				",SUM(total_charge_owed          ) total_charge_owed\r\n" + 
				",SUM(total_prepaid_charges      ) total_prepaid_charges\r\n" + 
				",SUM(unpaid_amount              ) unpaid_amount\r\n" + 
				",SUM(payment_amount             ) payment_amount\r\n" + 
				",SUM(collected_qty              ) collected_qty\r\n" + 
				",SUM(yesterday_collection_qty   ) yesterday_collection_qty\r\n" + 
				",SUM(files_qty                  ) files_qty\r\n" + 
				",SUM(yesterday_files_qty        ) yesterday_files_qty\r\n" + 
				",SUM(goods_qty                  ) goods_qty\r\n" + 
				",SUM(yesterday_goods_qty        ) yesterday_goods_qty\r\n" + 
				",SUM(delivery_qty               ) delivery_qty\r\n" + 
				",SUM(yesterday_delivery_qty     ) yesterday_delivery_qty\r\n" + 
				",SUM(customer_send_qty1) customer_send_qty1\r\n" + 
				",SUM(customer_send_qty2) customer_send_qty2\r\n" + 
				",SUM(customer_send_qty3) customer_send_qty3 FROM \r\n" + 
				"(SELECT @num := IF(@waybill_no = t.waybill_no ,@num  +1 ,1) num\r\n" + 
				",@waybill_no := t.waybill_no waybill_no\r\n" + 
				",t.post_org_no\r\n" + 
				",IFNULL(d.dept_name,t.post_org_name) post_org_name\r\n" + 
				",t.sender_country_no\r\n" + 
				",t.sender_country_name\r\n" + 
				",IFNULL(d.province_code,t.sender_province_no) as sender_province_no\r\n" + 
				",d.province_name as sender_province_name\r\n" + 
				",IFNULL(d.city_code,t.sender_city_no) as sender_city_no\r\n" + 
				",d.city_name as sender_city_name\r\n" + 
				",IFNULL(d.county_code,t.sender_county_no) as sender_county_no\r\n" + 
				",d.county_name as sender_county_name\r\n" + 
				",t.sender_district_no\r\n" + 
				",t.sender_id\r\n" + 
				",t.sender_no\r\n" + 
				",t.sender\r\n" + 
				",t.sender_warehouse_id\r\n" + 
				",t.sender_warehouse_name\r\n" + 
				",ADDDATE(DATE_FORMAT(t.biz_occur_date,'%Y-%m-%d'),INTERVAL 1 DAY) as period_id\r\n" + 
				",0.00 as order_weight\r\n" + 
				",0.00 as real_weight\r\n" + 
				",0.00 as fee_weight\r\n" + 
				",0.00 as postage_total\r\n" + 
				",0.00	as yesterday_postage_total\r\n" + 
				",0.00 	as postage_standard\r\n" + 
				",0.00	as postage_paid\r\n" + 
				",0.00	as postage_other\r\n" + 
				",0.00	as total_current_charges\r\n" + 
				",0.00	as total_charge_owed\r\n" + 
				",0.00	as total_prepaid_charges\r\n" + 
				",0.00 unpaid_amount\r\n" + 
				",0.00 payment_amount\r\n" + 
				",0 collected_qty\r\n" + 
				",IF( t.waybill_no IS NULL ,0 ,1 ) as  yesterday_collection_qty\r\n" + 
				",0 files_qty\r\n" + 
				",case when t.contents_attribute = '1' then 1 else 0 end as yesterday_files_qty\r\n" + 
				",0 goods_qty\r\n" + 
				",case when t.contents_attribute = '3' then 1 else 0 end as yesterday_goods_qty\r\n" + 
				",0 delivery_qty\r\n" + 
				",0 yesterday_delivery_qty\r\n" + 
				",case when hour(t.biz_occur_date) < 11 then 0\r\n" + 
				"     when hour(t.biz_occur_date) >= 11 and hour(t.biz_occur_date) <= 13 then 1\r\n" + 
				"		 else 2 \r\n" + 
				"		 end as customer_send_time_type\r\n" + 
				",0 as customer_send_qty1\r\n" + 
				",0 as customer_send_qty2\r\n" + 
				",0 as customer_send_qty3\r\n" + 
				"from sdi_jxyz_pkp_waybill_base_${L_YEAR} t LEFT OUTER JOIN \r\n" + 
				"     dwr_jxyz_department_d d\r\n" + 
				"ON d.dept_code = t.post_org_no \r\n" + 
				"INNER JOIN \r\n" + 
				"     (SELECT @waybill_no := '',@num := 0) t1\r\n" + 
				"where t.biz_occur_date >= ADDDATE('${START_DATE}',INTERVAL -1 DAY)\r\n" + 
				"and t.biz_occur_date <= ADDDATE('${END_DATE}',INTERVAL -1 DAY)\r\n" + 
				"AND t.sender_province_no = '360000'\r\n" + 
				"AND t.sender_type = '1'\r\n" + 
				"and ifnull(t.postage_total,0) > 0\r\n" + 
				"AND t.sender_no IS NOT  NULL \r\n" + 
//				"AND t.is_deleted = '0'\r\n" +
				"AND t.base_product_no = '11210'\r\n" + 
				"ORDER BY t.waybill_no,t.biz_occur_date DESC ) f \r\n" + 
				"WHERE num = 1 \r\n" + 
				"GROUP BY post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",sender_id\r\n" + 
				",sender_no\r\n" + 
				",sender\r\n" + 
				",sender_warehouse_id\r\n" + 
				",sender_warehouse_name\r\n" + 
				",period_id\r\n" + 
				",customer_send_time_type) f\r\n" + 
				"GROUP BY post_org_no\r\n" + 
				",post_org_name\r\n" + 
				",sender_country_no\r\n" + 
				",sender_country_name\r\n" + 
				",sender_province_no\r\n" + 
				",sender_province_name\r\n" + 
				",sender_city_no\r\n" + 
				",sender_city_name\r\n" + 
				",sender_county_no\r\n" + 
				",sender_county_name\r\n" + 
				",sender_district_no\r\n" + 
				",sender_id\r\n" + 
				",sender_no\r\n" + 
				",sender\r\n" + 
				",sender_warehouse_id\r\n" + 
				",sender_warehouse_name\r\n" + 
				",period_id\r\n" + 
				",customer_send_time_type";
		// 替换变量
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("C_YEAR", (String) Application.GLOBAL_PARAM.get(Application.CURR_YEAR));
		params2.put("L_YEAR", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR));
		params2.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.LAST_DAY));
		params2.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.CURR_DAY));
		querySQL = Tools.parse(querySQL, params2);

		PreparedStatement queryPs = connection.prepareStatement(querySQL);

		String insertSQL = "insert into dwr_customer_daily_revenue_t(delivery_qty,sender_district_no,post_org_no,real_weight,postage_total,customer_send_qty1,customer_send_qty3,fee_weight,customer_send_qty2,sender_warehouse_id,sender_warehouse_name,sender_country_name,sender_id,sender_province_no,sender_county_name,collected_qty,sender_country_no,postage_other,post_org_name,yesterday_postage_total,sender_city_no,sender_city_name,period_id,customer_send_time_type,order_weight,yesterday_collection_qty,yesterday_goods_qty,sender_no,sender_county_no,payment_amount,goods_qty,sender_province_name,total_current_charges,yesterday_delivery_qty,postage_paid,total_charge_owed,yesterday_files_qty,sender,files_qty,total_prepaid_charges,unpaid_amount,postage_standard)value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertPs = connection.prepareStatement(insertSQL);

		ResultSet rs = queryPs.executeQuery();
		int count = 0;
		while (rs.next()) {
			insertPs.setInt(1, rs.getInt("delivery_qty"));
			insertPs.setString(2, rs.getString("sender_district_no"));
			insertPs.setString(3, rs.getString("post_org_no"));
			insertPs.setBigDecimal(4, rs.getBigDecimal("real_weight"));
			insertPs.setBigDecimal(5, rs.getBigDecimal("postage_total"));
			insertPs.setInt(6, rs.getInt("customer_send_qty1"));
			insertPs.setInt(7, rs.getInt("customer_send_qty3"));
			insertPs.setBigDecimal(8, rs.getBigDecimal("fee_weight"));
			insertPs.setInt(9, rs.getInt("customer_send_qty2"));
			insertPs.setInt(10, rs.getInt("sender_warehouse_id"));
			insertPs.setString(11, rs.getString("sender_warehouse_name"));
			insertPs.setString(12, rs.getString("sender_country_name"));
			insertPs.setInt(13, rs.getInt("sender_id"));
			insertPs.setString(14, rs.getString("sender_province_no"));
			insertPs.setString(15, rs.getString("sender_county_name"));
			insertPs.setInt(16, rs.getInt("collected_qty"));
			insertPs.setString(17, rs.getString("sender_country_no"));
			insertPs.setBigDecimal(18, rs.getBigDecimal("postage_other"));
			insertPs.setString(19, rs.getString("post_org_name"));
			insertPs.setBigDecimal(20, rs.getBigDecimal("yesterday_postage_total"));
			insertPs.setString(21, rs.getString("sender_city_no"));
			insertPs.setString(22, rs.getString("sender_city_name"));
			insertPs.setDate(23, rs.getDate("period_id"));
			insertPs.setString(24, rs.getString("customer_send_time_type"));
			insertPs.setBigDecimal(25, rs.getBigDecimal("order_weight"));
			insertPs.setInt(26, rs.getInt("yesterday_collection_qty"));
			insertPs.setInt(27, rs.getInt("yesterday_goods_qty"));
			insertPs.setString(28, rs.getString("sender_no"));
			insertPs.setString(29, rs.getString("sender_county_no"));
			insertPs.setBigDecimal(30, rs.getBigDecimal("payment_amount"));
			insertPs.setInt(31, rs.getInt("goods_qty"));
			insertPs.setString(32, rs.getString("sender_province_name"));
			insertPs.setBigDecimal(33, rs.getBigDecimal("total_current_charges"));
			insertPs.setInt(34, rs.getInt("yesterday_delivery_qty"));
			insertPs.setBigDecimal(35, rs.getBigDecimal("postage_paid"));
			insertPs.setBigDecimal(36, rs.getBigDecimal("total_charge_owed"));
			insertPs.setInt(37, rs.getInt("yesterday_files_qty"));
			insertPs.setString(38, rs.getString("sender"));
			insertPs.setInt(39, rs.getInt("files_qty"));
			insertPs.setBigDecimal(40, rs.getBigDecimal("total_prepaid_charges"));
			insertPs.setBigDecimal(41, rs.getBigDecimal("unpaid_amount"));
			insertPs.setBigDecimal(42, rs.getBigDecimal("postage_standard"));
			insertPs.executeUpdate();
			count++;
		}
		rs.close();
		queryPs.close();
		insertPs.close();
		System.out.println("写入dwr_customer_daily_revenue_t" + " 记录数:" + count);
		
		Map<String,String> map = new HashMap<>();
		map.put("tableName", "dwr_customer_daily_revenue_t");
		String selectSql = "select * from dwr_customer_daily_revenue_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		selectSql = Tools.parse(selectSql, params);
		map.put("selectSql", selectSql);
		map.put("prefix", deleteSQL);
		HttpUtil.upload(map);
		
		
	}

}
