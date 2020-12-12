package jxyz.exchanger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import jxyz.Application;
import jxyz.utils.HttpUtil;
import jxyz.utils.Tools;

/**
 * 员工日统计 输入表：sdi_jxyz_pkp_waybill_base_2020，dwr_jxyz_emp_d，dwr_jxyz_department_d
 * 输出表：dwr_emp_daily_collection_t
 * 
 * @author xiaoxin
 *
 */
public class EmpDaliyExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		// 删除目标数据(从全局变量里取开始时间和结束时间)
		String deleteSQL = "delete from dwr_emp_daily_collection_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		// 替换变量
		Map<String, String> params = new HashMap<String, String>();
		params.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.LAST_DAY));
		params.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.CURR_DAY));
		deleteSQL = Tools.parse(deleteSQL, params);

		PreparedStatement deletePs = connection.prepareStatement(deleteSQL);
		int num = deletePs.executeUpdate();
		System.out.println("删除: " + num);
		deletePs.close();

		String querySQL = "SELECT post_org_id,\r\n" +
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
				"e.emp_section_code,\r\n" +
				"e.emp_section_name,\r\n" +
				"e.emp_tel,\r\n" +
				"period_id ,\r\n" +
				"sum(order_weight               ) order_weight                               ,\r\n" +
				"sum(real_weight                ) real_weight                               ,\r\n" +
				"sum(fee_weight                 ) fee_weight                               ,\r\n" +
				"sum(postage_total              ) postage_total                               ,\r\n" +
				"sum(yesterday_postage_total    ) yesterday_postage_total                               ,\r\n" +
				"sum(postage_standard           ) postage_standard                               ,\r\n" +
				"sum(postage_other              ) postage_other                               ,\r\n" +
				"sum(total_current_charges      ) total_current_charges                               ,\r\n" +
				"sum(total_charge_owed          ) total_charge_owed                               ,\r\n" +
				"sum(total_prepaid_charges      ) total_prepaid_charges                               ,\r\n" +
				"sum(unpaid_amount              ) unpaid_amount                               ,\r\n" +
				"sum(payment_amount             ) payment_amount                               ,\r\n" +
				"sum(collected_qty              ) collected_qty                               ,\r\n" +
				"sum(yesterday_collection_qty   ) yesterday_collection_qty                               ,\r\n" +
				"sum(delivery_qty               ) delivery_qty                               ,\r\n" +
				"sum(yesterday_delivery_qty     ) yesterday_delivery_qty                               ,\r\n" +
				"sum(standard_express_collection) standard_express_collection                                ,\r\n" +
				"sum(package_collection         ) package_collection                               ,\r\n" +
				"sum(international_sales_volume ) international_sales_volume,\r\n" +
				"sum(standard_express_salary) AS standard_express_salary ,\r\n" +
				"sum(package_collection_salary) AS package_collection_salary ,\r\n" +
				"sum(international_sales__salary) AS international_sales__salary\r\n" +
				"FROM (\r\n" +
				"SELECT post_org_id,\r\n" +
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
				"period_id ,\r\n" +
				"sum(order_weight               ) order_weight                               ,\r\n" +
				"sum(real_weight                ) real_weight                               ,\r\n" +
				"sum(fee_weight                 ) fee_weight                               ,\r\n" +
				"sum(postage_total              ) postage_total                               ,\r\n" +
				"sum(yesterday_postage_total    ) yesterday_postage_total                               ,\r\n" +
				"sum(postage_standard           ) postage_standard                               ,\r\n" +
				"sum(postage_other              ) postage_other                               ,\r\n" +
				"sum(total_current_charges      ) total_current_charges                               ,\r\n" +
				"sum(total_charge_owed          ) total_charge_owed                               ,\r\n" +
				"sum(total_prepaid_charges      ) total_prepaid_charges                               ,\r\n" +
				"sum(unpaid_amount              ) unpaid_amount                               ,\r\n" +
				"sum(payment_amount             ) payment_amount                               ,\r\n" +
				"sum(collected_qty              ) collected_qty                               ,\r\n" +
				"sum(yesterday_collection_qty   ) yesterday_collection_qty                               ,\r\n" +
				"sum(delivery_qty               ) delivery_qty                               ,\r\n" +
				"sum(yesterday_delivery_qty     ) yesterday_delivery_qty                               ,\r\n" +
				"sum(standard_express_collection) standard_express_collection                                ,\r\n" +
				"sum(package_collection         ) package_collection                               ,\r\n" +
				"sum(international_sales_volume ) international_sales_volume,\r\n" +
				"sum(standard_express_salary) AS standard_express_salary ,\r\n" +
				"sum(package_collection_salary) AS package_collection_salary ,\r\n" +
				"sum(international_sales__salary) AS international_sales__salary\r\n" +
				" FROM \r\n" +
				"(SELECT \r\n" +
				"@num := IF(@waybill_no = t.waybill_no ,@num  +1 ,1) num\r\n" +
				",@waybill_no := t.waybill_no waybill_no\r\n" +
				",0 post_org_id\r\n" +
				",t.post_org_no\r\n" +
				",t.org_drds_code\r\n" +
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
				",t.post_person_id\r\n" +
				",t.post_person_no\r\n" +
				",t.post_person_name\r\n" +
				",t.post_person_mobile\r\n" +
				",date_format(t.biz_occur_date,'%Y-%m-%d') as period_id\r\n" +
				",ifnull(t.order_weight,0.0) as order_weight\r\n" +
				",ifnull(t.real_weight,0.0) as real_weight\r\n" +
				",ifnull(t.fee_weight,0.0) as fee_weight\r\n" +
				",ifnull(t.postage_total,0.0) as postage_total\r\n" +
				",0.0 as yesterday_postage_total\r\n" +
				",ifnull(t.postage_standard,0.0) as postage_standard\r\n" +
				",ifnull(t.postage_paid,0.0) as postage_paid\r\n" +
				",ifnull(t.postage_other,0.0) as postage_other\r\n" +
				",case when t.settlement_mode = '1' then ifnull(t.postage_total,0.0) else 0 end as total_current_charges\r\n" +
				",case when t.settlement_mode = '2' then ifnull(t.postage_total,0.0) else 0 end as total_charge_owed\r\n" +
				",case when t.settlement_mode = '3' then ifnull(t.postage_total,0.0) else 0 end as total_prepaid_charges\r\n" +
				",case when t.payment_state ='0' then ifnull(t.postage_total,0.0)  else 0 end as unpaid_amount\r\n" +
				",case when t.payment_state ='1' then ifnull(t.postage_total,0.0)  else 0 end as payment_amount\r\n" +
				",IF (t.waybill_no IS NULL,0,1)  collected_qty\r\n" +
				",0 as yesterday_collection_qty\r\n" +
				",0  delivery_qty\r\n" +
				",0 yesterday_delivery_qty\r\n" +
				",CASE WHEN  t.base_product_no = '11210' THEN 1 else 0 END AS standard_express_collection \r\n" +
				",CASE WHEN t.base_product_no ='11312' THEN 1 ELSE 0 END AS package_collection \r\n" +
				",CASE WHEN t.base_product_no IN ('21210','22210','21412') THEN 1 ELSE 0 END AS international_sales_volume \r\n" +
				",CASE WHEN  t.base_product_no = '11210' THEN IFNULL(t.postage_total,0) else 0 END AS standard_express_salary \r\n" +
				",CASE WHEN t.base_product_no ='11312' THEN IFNULL(t.postage_total,0) ELSE 0 END AS package_collection_salary \r\n" +
				",CASE WHEN t.base_product_no IN ('21210','22210','21412') THEN IFNULL(t.postage_total,0) ELSE 0 END AS international_sales__salary\r\n" +
				"from sdi_jxyz_pkp_waybill_base_${C_YEAR} t left outer join\r\n" +
				"     dwr_jxyz_department_d d \r\n" +
				"on d.dept_code = t.post_org_no\r\n" +
				"INNER JOIN \r\n" +
				"     (SELECT @waybill_no := '',@num := 0) t1\r\n" +
				"where t.biz_occur_date >= '${START_DATE}'\r\n" +
				"and t.biz_occur_date <= '${END_DATE}'\r\n" +
				"and t.sender_province_no = '360000'\r\n" +
				"and ifnull(t.postage_total,0) > 0\r\n" +
				"and t.waybill_no is not null\r\n" +
//				"and t.is_deleted = '0'\r\n" +
				"ORDER BY t.waybill_no,t.biz_occur_date DESC \r\n" +
				") t\r\n" +
				"WHERE t.num = 1\r\n" +
				"GROUP BY post_org_id,\r\n" +
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
				"period_id\r\n" +
				"UNION ALL\r\n" +
				"SELECT post_org_id,\r\n" +
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
				"period_id ,\r\n" +
				"sum(order_weight               ) order_weight                               ,\r\n" +
				"sum(real_weight                ) real_weight                               ,\r\n" +
				"sum(fee_weight                 ) fee_weight                               ,\r\n" +
				"sum(postage_total              ) postage_total                               ,\r\n" +
				"sum(yesterday_postage_total    ) yesterday_postage_total                               ,\r\n" +
				"sum(postage_standard           ) postage_standard                               ,\r\n" +
				"sum(postage_other              ) postage_other                               ,\r\n" +
				"sum(total_current_charges      ) total_current_charges                               ,\r\n" +
				"sum(total_charge_owed          ) total_charge_owed                               ,\r\n" +
				"sum(total_prepaid_charges      ) total_prepaid_charges                               ,\r\n" +
				"sum(unpaid_amount              ) unpaid_amount                               ,\r\n" +
				"sum(payment_amount             ) payment_amount                               ,\r\n" +
				"sum(collected_qty              ) collected_qty                               ,\r\n" +
				"sum(yesterday_collection_qty   ) yesterday_collection_qty                               ,\r\n" +
				"sum(delivery_qty               ) delivery_qty                               ,\r\n" +
				"sum(yesterday_delivery_qty     ) yesterday_delivery_qty                               ,\r\n" +
				"sum(standard_express_collection) standard_express_collection                                ,\r\n" +
				"sum(package_collection         ) package_collection                               ,\r\n" +
				"sum(international_sales_volume ) international_sales_volume,\r\n" +
				"sum(standard_express_salary) AS standard_express_salary ,\r\n" +
				"sum(package_collection_salary) AS package_collection_salary ,\r\n" +
				"sum(international_sales__salary) AS international_sales__salary\r\n" +
				" FROM \r\n" +
				"(SELECT \r\n" +
				"@num1 := IF(@waybill_no1 = t.waybill_no ,@num1  +1 ,1) num\r\n" +
				",@waybill_no1 := t.waybill_no waybill_no\r\n" +
				",0 post_org_id\r\n" +
				",t.post_org_no\r\n" +
				",t.org_drds_code\r\n" +
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
				",t.post_person_id\r\n" +
				",t.post_person_no\r\n" +
				",t.post_person_name\r\n" +
				",t.post_person_mobile\r\n" +
				",ADDDATE(date_format(t.biz_occur_date,'%Y-%m-%d'),INTERVAL 1 DAY) as period_id\r\n" +
				",0.00 as order_weight\r\n" +
				",0.00 as real_weight\r\n" +
				",0.00 as fee_weight\r\n" +
				",0.00 as postage_total\r\n" +
				",ifnull(t.postage_total,0.00) as yesterday_postage_total\r\n" +
				",0.00 as postage_standard\r\n" +
				",0.00 as postage_paid\r\n" +
				",0.00 as postage_other\r\n" +
				",0.00 as payment_state\r\n" +
				",0.00 as total_current_charges\r\n" +
				",0.00 as total_charge_owed\r\n" +
				",0.00 as total_prepaid_charges\r\n" +
				",0.00 as unpaid_amount\r\n" +
				",0.00 as payment_amount\r\n" +
				",0  collected_qty\r\n" +
				",IF (t.waybill_no IS NULL,0,1) yesterday_collection_qty\r\n" +
				",0  delivery_qty\r\n" +
				",0 yesterday_delivery_qty\r\n" +
				",0 AS standard_express_collection \r\n" +
				",0 AS package_collection \r\n" +
				",0 AS international_sales_volume\r\n" +
				",0.00 AS standard_express_salary \r\n" +
				",0.00 AS package_collection_salary \r\n" +
				",0.00 AS international_sales__salary \r\n" +
				"from sdi_jxyz_pkp_waybill_base_${C_YEAR} t left outer join\r\n" +
				"     dwr_jxyz_department_d d \r\n" +
				"on d.dept_code = t.post_org_no\r\n" +
				"INNER JOIN \r\n" +
				"     (SELECT @waybill_no1 := '',@num1 := 0) t1\r\n" +
				"where t.biz_occur_date >= ADDDATE('${START_DATE}',INTERVAL -1 DAY)\r\n" +
				"and t.biz_occur_date <= ADDDATE('${END_DATE}',INTERVAL -1 DAY)\r\n" +
				"and t.sender_province_no = '360000'\r\n" +
				"and ifnull(t.postage_total,0) > 0\r\n" +
				"and t.waybill_no is not null\r\n" +
//				"and t.is_deleted = '0'\r\n" +
				"ORDER BY t.waybill_no,t.biz_occur_date DESC \r\n" +
				") t\r\n" +
				"WHERE t.num = 1\r\n" +
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
				"period_id\r\n" +
				"UNION ALL\r\n" +
				"SELECT post_org_id,\r\n" +
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
				"period_id ,\r\n" +
				"sum(order_weight               ) order_weight                               ,\r\n" +
				"sum(real_weight                ) real_weight                               ,\r\n" +
				"sum(fee_weight                 ) fee_weight                               ,\r\n" +
				"sum(postage_total              ) postage_total                               ,\r\n" +
				"sum(yesterday_postage_total    ) yesterday_postage_total                               ,\r\n" +
				"sum(postage_standard           ) postage_standard                               ,\r\n" +
				"sum(postage_other              ) postage_other                               ,\r\n" +
				"sum(total_current_charges      ) total_current_charges                               ,\r\n" +
				"sum(total_charge_owed          ) total_charge_owed                               ,\r\n" +
				"sum(total_prepaid_charges      ) total_prepaid_charges                               ,\r\n" +
				"sum(unpaid_amount              ) unpaid_amount                               ,\r\n" +
				"sum(payment_amount             ) payment_amount                               ,\r\n" +
				"sum(collected_qty              ) collected_qty                               ,\r\n" +
				"sum(yesterday_collection_qty   ) yesterday_collection_qty                               ,\r\n" +
				"sum(delivery_qty               ) delivery_qty                               ,\r\n" +
				"sum(yesterday_delivery_qty     ) yesterday_delivery_qty                               ,\r\n" +
				"sum(standard_express_collection) standard_express_collection                                ,\r\n" +
				"sum(package_collection         ) package_collection                               ,\r\n" +
				"sum(international_sales_volume ) international_sales_volume,\r\n" +
				"sum(standard_express_salary) AS standard_express_salary ,\r\n" +
				"sum(package_collection_salary) AS package_collection_salary ,\r\n" +
				"sum(international_sales__salary) AS international_sales__salary\r\n" +
				" FROM \r\n" +
				"(SELECT \r\n" +
				"@num2 := IF(@waybill_no2 = t.waybill_no ,@num2  +1 ,1) num\r\n" +
				",@waybill_no2 := t.waybill_no waybill_no\r\n" +
				",0 post_org_id\r\n" +
				",t.post_org_no\r\n" +
				",t.org_drds_code\r\n" +
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
				",t.post_person_id\r\n" +
				",t.post_person_no\r\n" +
				",t.post_person_name\r\n" +
				",t.post_person_mobile\r\n" +
				",ADDDATE(date_format(t.biz_occur_date,'%Y-%m-%d'),INTERVAL 1 DAY) as period_id\r\n" +
				",0.00 as order_weight\r\n" +
				",0.00 as real_weight\r\n" +
				",0.00 as fee_weight\r\n" +
				",0.00 as postage_total\r\n" +
				",ifnull(t.postage_total,0.00) as yesterday_postage_total\r\n" +
				",0.00 as postage_standard\r\n" +
				",0.00 as postage_paid\r\n" +
				",0.00 as postage_other\r\n" +
				",0.00 as payment_state\r\n" +
				",0.00 as total_current_charges\r\n" +
				",0.00 as total_charge_owed\r\n" +
				",0.00 as total_prepaid_charges\r\n" +
				",0.00 as unpaid_amount\r\n" +
				",0.00 as payment_amount\r\n" +
				",0  collected_qty\r\n" +
				",IF (t.waybill_no IS NULL,0,1) yesterday_collection_qty\r\n" +
				",0  delivery_qty\r\n" +
				",0 yesterday_delivery_qty\r\n" +
				",0 AS standard_express_collection \r\n" +
				",0 AS package_collection \r\n" +
				",0 AS international_sales_volume\r\n" +
				",0.00 AS standard_express_salary \r\n" +
				",0.00 AS package_collection_salary \r\n" +
				",0.00 AS international_sales__salary \r\n" +
				"from sdi_jxyz_pkp_waybill_base_${L_YEAR} t left outer join\r\n" +
				"     dwr_jxyz_department_d d \r\n" +
				"on d.dept_code = t.post_org_no\r\n" +
				"INNER JOIN \r\n" +
				"     (SELECT @waybill_no2 := '',@num2 := 0) t1\r\n" +
				"where t.biz_occur_date >= ADDDATE('${START_DATE}',INTERVAL -1 DAY)\r\n" +
				"and t.biz_occur_date <= ADDDATE('${END_DATE}',INTERVAL -1 DAY)\r\n" +
				"and t.sender_province_no = '360000'\r\n" +
				"and ifnull(t.postage_total,0) > 0\r\n" +
				"and t.waybill_no is not null\r\n" +
//				"and t.is_deleted = '0'\r\n" +
				"ORDER BY t.waybill_no,t.biz_occur_date DESC \r\n" +
				") t\r\n" +
				"WHERE t.num = 1\r\n" +
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
				"period_id) f LEFT OUTER JOIN \r\n" +
				"(SELECT e.emp_code,e.emp_section_code,e.emp_section_name,e.emp_tel FROM \r\n" +
				"dwr_jxyz_emp_d e \r\n" +
//				"WHERE e.emp_status = '01'\r\n" +
				"GROUP BY e.emp_code,e.emp_section_code,e.emp_section_name,e.emp_tel) e\r\n" +
				"ON f.post_person_no = e.emp_code\r\n" +
				"GROUP BY\r\n" +
				"e.emp_section_code,e.emp_section_name,e.emp_tel, \r\n" +
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
				"period_id";
		// 替换变量
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("C_YEAR", (String) Application.GLOBAL_PARAM.get(Application.CURR_YEAR));
		params2.put("L_YEAR", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR));
		params2.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.LAST_DAY));
		params2.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.CURR_DAY));
		querySQL = Tools.parse(querySQL, params2);

		PreparedStatement queryPs = connection.prepareStatement(querySQL);

		String insertSQL = "insert into dwr_emp_daily_collection_t(delivery_qty,sender_district_no,post_org_no,real_weight,package_collection_salary,post_person_id,postage_total,fee_weight,post_person_mobile,sender_country_name,package_collection,sender_province_no,sender_county_name,post_person_name,international_sales_volume,collected_qty,sender_country_no,postage_other,post_org_name,yesterday_postage_total,sender_city_no,sender_city_name,period_id,order_weight,yesterday_collection_qty,international_sales__salary,standard_express_collection,section_name,post_org_id,sender_county_no,payment_amount,sender_province_name,standard_express_salary,total_current_charges,post_person_no,yesterday_delivery_qty,total_charge_owed,org_drds_code,total_prepaid_charges,unpaid_amount,section_code,postage_standard)value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertPs = connection.prepareStatement(insertSQL);
		ResultSet rs = queryPs.executeQuery();
		int count = 0;
		while (rs.next()) {
			insertPs.setInt(1, rs.getInt("delivery_qty"));
			insertPs.setString(2, rs.getString("sender_district_no"));
			insertPs.setString(3, rs.getString("post_org_no"));
			insertPs.setBigDecimal(4, rs.getBigDecimal("real_weight"));
			insertPs.setBigDecimal(5, rs.getBigDecimal("package_collection_salary"));
			insertPs.setInt(6, rs.getInt("post_person_id"));
			insertPs.setBigDecimal(7, rs.getBigDecimal("postage_total"));
			insertPs.setBigDecimal(8, rs.getBigDecimal("fee_weight"));
			insertPs.setString(9, rs.getString("emp_tel"));
			insertPs.setString(10, rs.getString("sender_country_name"));
			insertPs.setInt(11, rs.getInt("package_collection"));
			insertPs.setString(12, rs.getString("sender_province_no"));
			insertPs.setString(13, rs.getString("sender_county_name"));
			insertPs.setString(14, rs.getString("post_person_name"));
			insertPs.setInt(15, rs.getInt("international_sales_volume"));
			insertPs.setInt(16, rs.getInt("collected_qty"));
			insertPs.setString(17, rs.getString("sender_country_no"));
			insertPs.setBigDecimal(18, rs.getBigDecimal("postage_other"));
			insertPs.setString(19, rs.getString("post_org_name"));
			insertPs.setBigDecimal(20, rs.getBigDecimal("yesterday_postage_total"));
			insertPs.setString(21, rs.getString("sender_city_no"));
			insertPs.setString(22, rs.getString("sender_city_name"));
			insertPs.setDate(23, rs.getDate("period_id"));
			insertPs.setBigDecimal(24, rs.getBigDecimal("order_weight"));
			insertPs.setInt(25, rs.getInt("yesterday_collection_qty"));
			insertPs.setBigDecimal(26, rs.getBigDecimal("international_sales__salary"));
			insertPs.setInt(27, rs.getInt("standard_express_collection"));
			insertPs.setString(28, rs.getString("emp_section_name"));
			insertPs.setInt(29, rs.getInt("post_org_id"));
			insertPs.setString(30, rs.getString("sender_county_no"));
			insertPs.setBigDecimal(31, rs.getBigDecimal("payment_amount"));
			insertPs.setString(32, rs.getString("sender_province_name"));
			insertPs.setBigDecimal(33, rs.getBigDecimal("standard_express_salary"));
			insertPs.setBigDecimal(34, rs.getBigDecimal("total_current_charges"));
			insertPs.setString(35, rs.getString("post_person_no"));
			insertPs.setInt(36, rs.getInt("yesterday_delivery_qty"));
			insertPs.setBigDecimal(37, rs.getBigDecimal("total_charge_owed"));
			insertPs.setString(38, rs.getString("org_drds_code"));
			insertPs.setBigDecimal(39, rs.getBigDecimal("total_prepaid_charges"));
			insertPs.setBigDecimal(40, rs.getBigDecimal("unpaid_amount"));
			insertPs.setString(41, rs.getString("emp_section_code"));
			insertPs.setBigDecimal(42, rs.getBigDecimal("postage_standard"));
			insertPs.executeUpdate();

			count++;
		}
		rs.close();
		queryPs.close();
		insertPs.close();
		System.out.println("写入dwr_emp_daily_collection_t" + " 记录数:" + count);

		//查询所有无段道人员信息
		String selectSectionPersonSql = "select sender_county_no,post_org_no,post_person_no,section_code,section_name from dwr_emp_daily_collection_t " +
				"where section_code is null and post_person_no is not null GROUP BY post_person_no";
		PreparedStatement sectionPs = connection.prepareStatement(selectSectionPersonSql);
		ResultSet sectionData = sectionPs.executeQuery();
		//查询旧的段道值
		String oldSectionSql = "select section_code,section_name from dwr_emp_daily_collection_t where section_code is not null " +
				" and post_person_no = ? limit 1";
		PreparedStatement oldSectionPs = connection.prepareStatement(oldSectionSql);

		//更新段道值
		String updateSql = "update dwr_emp_daily_collection_t set  section_code = ? ,section_name = ? " +
				"where  section_code is null and post_person_no = ?";

		PreparedStatement updatePs = connection.prepareStatement(updateSql);

		//查询营业部段道
		PreparedStatement tSectionPs = connection.prepareStatement("SELECT `code`,`name` FROM `t_grid_m` where parent_code = ? ");

		while (sectionData.next()){
			//查询以往记录中有无段道信息
			oldSectionPs.setString(1,sectionData.getString("post_person_no"));
			ResultSet  oldSectionData = oldSectionPs.executeQuery();
			if (oldSectionData.next()){
				//有下标，更新section_code为空的数据
				updatePs.setString(1,oldSectionData.getString("section_code"));
				updatePs.setString(2,oldSectionData.getString("section_name"));
				updatePs.setString(3,sectionData.getString("post_person_no"));
				updatePs.executeUpdate();
			}else{
				//无下标，取营业部的段道，随机插入一个并更新所有section_code为空的数据
				//查询营业部是否有段道
				tSectionPs.setString(1,sectionData.getString("post_org_no"));
				ResultSet sCodeSet = tSectionPs.executeQuery();
				String sectionCode = null;
				String sectionName = null;
				if (sCodeSet.next()) {
					//有下标
					sCodeSet.last();
					int a  = sCodeSet.getRow();
					int number = (new Random().nextInt(a))+1;
					sCodeSet.first();
					sCodeSet.previous();
					//随机取一个存入sectionCode中
					while (sCodeSet.next()) {
						if (number == sCodeSet.getRow()) {
							sectionCode = sCodeSet.getString("code");
							sectionName = sCodeSet.getString("name");
						}
					}
				}else{
					//无段道创建 要创建段道并修改
					sectionCode = sectionData.getString("post_org_no") + "01";
					sectionName = "L01";
				}
				updatePs.setString(1,sectionCode);
				updatePs.setString(2,sectionName);
				updatePs.setString(3,sectionData.getString("post_person_no"));
				updatePs.executeUpdate();
				sCodeSet.close();
			}
			oldSectionData.close();
		}
		tSectionPs.close();
		updatePs.close();
		oldSectionPs.close();
		sectionData.close();
		sectionPs.close();
		
		Map<String,String> transferMap = new HashMap<>();
		transferMap.put("tableName", "dwr_emp_daily_collection_t");
		String selectSql = "select `post_org_id`,`post_org_no`,`org_drds_code`,`post_org_name`,`sender_country_no`,`sender_country_name`,`sender_province_no`,`sender_province_name`,`sender_city_no`,`sender_city_name`,`sender_county_no`,`sender_county_name`,`sender_district_no`,`post_person_id`,`post_person_no`,`post_person_name`,`post_person_mobile`,`section_code`,`section_name`,`period_id`,`order_weight`,`real_weight`,`fee_weight`,`postage_total`,`yesterday_postage_total`,`postage_standard`,`postage_paid`,`postage_other`,`total_current_charges`,`total_charge_owed`,`total_prepaid_charges`,`unpaid_amount`,`payment_amount`,`collected_qty`,`yesterday_collection_qty`,`delivery_qty`,`yesterday_delivery_qty`,`standard_express_collection`,`international_sales_volume`,`standard_express_salary`,`package_collection_salary`,`international_sales__salary`,`package_collection`,`created_date`,`created_by`,`extend_column`,`create_user`,`create_date`,`modify_user`,`modify_date` from dwr_emp_daily_collection_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		selectSql = Tools.parse(selectSql, params);
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", deleteSQL);
		HttpUtil.upload(transferMap);

	}

}
