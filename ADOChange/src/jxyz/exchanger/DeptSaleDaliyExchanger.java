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
 * 部门收入日统计
 * 输入表：sdi_jxyz_pkp_waybill_base_，dwr_jxyz_emp_d，dwr_jxyz_department_d
 * 输出表：dwr_sales_department_collection_t
 * 
 * @author xiaoxin
 *
 */
public class DeptSaleDaliyExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		// 删除目标数据(从全局变量里取开始时间和结束时间)
		String deleteSQL = "delete from dwr_sales_department_collection_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		// 替换变量
		Map<String, String> params = new HashMap<String, String>();
		params.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.LAST_DAY));
		params.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.CURR_DAY));
		deleteSQL = Tools.parse(deleteSQL, params);

		PreparedStatement deletePs = connection.prepareStatement(deleteSQL);
		int num = deletePs.executeUpdate();
		System.out.println("删除: " + num);
		deletePs.close();

		String querySQL = "SELECT post_org_id\r\n" + 
				",post_org_no\r\n" + 
				",org_drds_code\r\n" + 
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
				",period_id\r\n" + 
				",SUM(order_weight				) order_weight				\r\n" + 
				",SUM(real_weight                ) real_weight               \r\n" + 
				",SUM(fee_weight                 ) fee_weight                \r\n" + 
				",SUM(postage_total              ) postage_total             \r\n" + 
				",SUM(yesterday_postage_total    ) yesterday_postage_total   \r\n" + 
				",SUM(postage_standard           ) postage_standard          \r\n" + 
				",SUM(postage_paid               ) postage_paid              \r\n" + 
				",SUM(postage_other              ) postage_other             \r\n" + 
				",SUM(total_current_charges      ) total_current_charges     \r\n" + 
				",SUM(total_charge_owed          ) total_charge_owed         \r\n" + 
				",SUM(total_prepaid_charges      ) total_prepaid_charges     \r\n" + 
				",SUM(unpaid_amount              ) unpaid_amount             \r\n" + 
				",SUM(payment_amount             ) payment_amount            \r\n" + 
				",SUM(files_qty                  ) files_qty                 \r\n" + 
				",SUM(yesterday_files_qty        ) yesterday_files_qty       \r\n" + 
				",SUM(goods_qty                  ) goods_qty                 \r\n" + 
				",SUM(yesterday_goods_qty        ) yesterday_goods_qty       \r\n" + 
				",SUM(files_revenue              ) files_revenue             \r\n" + 
				",SUM(goods_revenue              ) goods_revenue             \r\n" + 
				",SUM(collected_qty              ) collected_qty             \r\n" + 
				",SUM(yesterday_collection_qty   ) yesterday_collection_qty  \r\n" + 
				",SUM(delivery_qty               ) delivery_qty              \r\n" + 
				",SUM(yesterday_delivery_qty     ) yesterday_delivery_qty \r\n" + 
				",ROUND(AVG(effective_person)) effective_person\r\n" + 
				"FROM \r\n" + 
				"(SELECT post_org_id\r\n" + 
				",post_org_no\r\n" + 
				",org_drds_code\r\n" + 
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
				",period_id\r\n" + 
				",SUM(order_weight				) order_weight				\r\n" + 
				",SUM(real_weight                ) real_weight               \r\n" + 
				",SUM(fee_weight                 ) fee_weight                \r\n" + 
				",SUM(postage_total              ) postage_total             \r\n" + 
				",SUM(yesterday_postage_total    ) yesterday_postage_total   \r\n" + 
				",SUM(postage_standard           ) postage_standard          \r\n" + 
				",SUM(postage_paid               ) postage_paid              \r\n" + 
				",SUM(postage_other              ) postage_other             \r\n" + 
				",SUM(total_current_charges      ) total_current_charges     \r\n" + 
				",SUM(total_charge_owed          ) total_charge_owed         \r\n" + 
				",SUM(total_prepaid_charges      ) total_prepaid_charges     \r\n" + 
				",SUM(unpaid_amount              ) unpaid_amount             \r\n" + 
				",SUM(payment_amount             ) payment_amount            \r\n" + 
				",SUM(files_qty                  ) files_qty                 \r\n" + 
				",SUM(yesterday_files_qty        ) yesterday_files_qty       \r\n" + 
				",SUM(goods_qty                  ) goods_qty                 \r\n" + 
				",SUM(yesterday_goods_qty        ) yesterday_goods_qty       \r\n" + 
				",SUM(files_revenue              ) files_revenue             \r\n" + 
				",SUM(goods_revenue              ) goods_revenue             \r\n" + 
				",SUM(collected_qty              ) collected_qty             \r\n" + 
				",SUM(yesterday_collection_qty   ) yesterday_collection_qty  \r\n" + 
				",SUM(delivery_qty               ) delivery_qty              \r\n" + 
				",SUM(yesterday_delivery_qty     ) yesterday_delivery_qty \r\n" + 
				",ROUND(AVG(effective_person)) effective_person FROM \r\n" + 
				"(SELECT @num := IF(@waybill_no = t.waybill_no ,@num  +1 ,1) num\r\n" + 
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
				",DATE_FORMAT(t.biz_occur_date,'%Y-%m-%d') period_id\r\n" + 
				",ifnull(t.order_weight,0) 	order_weight\r\n" + 
				",ifnull(t.real_weight,0) 		real_weight\r\n" + 
				",ifnull(t.fee_weight,0) 		fee_weight\r\n" + 
				",ifnull(t.postage_total,0) 	postage_total\r\n" + 
				",0 yesterday_postage_total\r\n" + 
				",ifnull(t.postage_standard,0) postage_standard\r\n" + 
				",ifnull(t.postage_paid,0) 	postage_paid\r\n" + 
				",ifnull(t.postage_other,0) 	postage_other\r\n" + 
				",case when t.settlement_mode = '1' then\r\n" + 
				"  ifnull(t.postage_total,0) else 0 end as total_current_charges\r\n" + 
				",case when t.settlement_mode = '2' then \r\n" + 
				"  ifnull(t.postage_total,0) else 0 end as total_charge_owed\r\n" + 
				",case when t.settlement_mode = '3' then\r\n" + 
				"  ifnull(t.postage_total,0) else 0 end as total_prepaid_charges\r\n" + 
				",case when t.payment_state ='0' then \r\n" + 
				" ifnull(t.postage_total,0)  else 0 end as unpaid_amount\r\n" + 
				",case when t.payment_state ='1' then \r\n" + 
				" ifnull(t.postage_total,0)  else 0 end as payment_amount\r\n" + 
				",case when t.contents_attribute = '1' then \r\n" + 
				"	1 else 0 end files_qty\r\n" + 
				",0 yesterday_files_qty\r\n" + 
				",case when t.contents_attribute = '3' then \r\n" + 
				"	1 else 0 end goods_qty\r\n" + 
				",0 yesterday_goods_qty\r\n" + 
				",case when t.contents_attribute = '1' then \r\n" + 
				" ifnull(t.postage_total,0) else 0 end files_revenue\r\n" + 
				",case when t.contents_attribute = '3' then \r\n" + 
				" ifnull(t.postage_total,0) else 0 end goods_revenue\r\n" + 
				", IF(t.waybill_no IS NULL,0,1)  collected_qty\r\n" + 
				",0 yesterday_collection_qty\r\n" + 
				",0 delivery_qty\r\n" + 
				",0 yesterday_delivery_qty\r\n" + 
				",IFNULL(d.effective_person,0) effective_person\r\n" + 
				"from sdi_jxyz_pkp_waybill_base_${C_YEAR} t LEFT OUTER JOIN\r\n" + 
				"     (SELECT \r\n" + 
				"  	 d.dept_code,\r\n" + 
				"    d.dept_name,\r\n" + 
				"    d.province_code,\r\n" + 
				"    d.province_name,\r\n" + 
				"    d.city_code,\r\n" + 
				"    d.city_name,\r\n" + 
				"    d.county_code,\r\n" + 
				"    d.county_name,\r\n" + 
				"  SUM(effective_number) effective_person \r\n" + 
				"FROM\r\n" + 
				"  (SELECT \r\n" + 
				"    d.dept_code,\r\n" + 
				"    d.dept_name,\r\n" + 
				"    d.province_code,\r\n" + 
				"    d.province_name,\r\n" + 
				"    d.city_code,\r\n" + 
				"    d.city_name,\r\n" + 
				"    d.county_code,\r\n" + 
				"    d.county_name,\r\n" + 
				"    CASE\r\n" + 
				"      WHEN e.emp_status = '01' \r\n" + 
				"      THEN 1 \r\n" + 
				"      ELSE 0 \r\n" + 
				"    END AS effective_number \r\n" + 
				"  FROM\r\n" + 
				"    dwr_jxyz_emp_d e,\r\n" + 
				"    dwr_jxyz_department_d d \r\n" + 
				"  WHERE d.dept_code = e.emp_dept_code) d\r\n" + 
				"GROUP BY d.dept_code,\r\n" + 
				"    d.dept_name,\r\n" + 
				"    d.province_code,\r\n" + 
				"    d.province_name,\r\n" + 
				"    d.city_code,\r\n" + 
				"    d.city_name,\r\n" + 
				"    d.county_code,\r\n" + 
				"    d.county_name) d\r\n" + 
				"	ON d.dept_code = t.post_org_no\r\n" + 
				"	INNER JOIN \r\n" + 
				"     (SELECT @waybill_no := '',@num := 0) t1\r\n" + 
				"where t.biz_occur_date >= '${START_DATE}'\r\n" + 
				"and t.biz_occur_date <= '${END_DATE}'\r\n" + 
				"AND t.sender_province_no = '360000'\r\n" + 
				"and ifnull(t.postage_total,0) > 0\r\n" + 
				"and t.waybill_no is not null\r\n" + 
//				"and t.is_deleted = '0'\r\n" + 
				"ORDER BY t.waybill_no,t.biz_occur_date DESC) f\r\n" + 
				"WHERE num = 1\r\n" + 
				"GROUP BY post_org_id\r\n" + 
				",post_org_no\r\n" + 
				",org_drds_code\r\n" + 
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
				",period_id\r\n" + 
				"UNION ALL\r\n" + 
				"#计算昨日收入和揽收情况\r\n" + 
				"SELECT post_org_id\r\n" + 
				",post_org_no\r\n" + 
				",org_drds_code\r\n" + 
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
				",period_id\r\n" + 
				",SUM(order_weight				) order_weight				\r\n" + 
				",SUM(real_weight                ) real_weight               \r\n" + 
				",SUM(fee_weight                 ) fee_weight                \r\n" + 
				",SUM(postage_total              ) postage_total             \r\n" + 
				",SUM(yesterday_postage_total    ) yesterday_postage_total   \r\n" + 
				",SUM(postage_standard           ) postage_standard          \r\n" + 
				",SUM(postage_paid               ) postage_paid              \r\n" + 
				",SUM(postage_other              ) postage_other             \r\n" + 
				",SUM(total_current_charges      ) total_current_charges     \r\n" + 
				",SUM(total_charge_owed          ) total_charge_owed         \r\n" + 
				",SUM(total_prepaid_charges      ) total_prepaid_charges     \r\n" + 
				",SUM(unpaid_amount              ) unpaid_amount             \r\n" + 
				",SUM(payment_amount             ) payment_amount            \r\n" + 
				",SUM(files_qty                  ) files_qty                 \r\n" + 
				",SUM(yesterday_files_qty        ) yesterday_files_qty       \r\n" + 
				",SUM(goods_qty                  ) goods_qty                 \r\n" + 
				",SUM(yesterday_goods_qty        ) yesterday_goods_qty       \r\n" + 
				",SUM(files_revenue              ) files_revenue             \r\n" + 
				",SUM(goods_revenue              ) goods_revenue             \r\n" + 
				",SUM(collected_qty              ) collected_qty             \r\n" + 
				",SUM(yesterday_collection_qty   ) yesterday_collection_qty  \r\n" + 
				",SUM(delivery_qty               ) delivery_qty              \r\n" + 
				",SUM(yesterday_delivery_qty     ) yesterday_delivery_qty \r\n" + 
				",ROUND(AVG(effective_person)) effective_person FROM \r\n" + 
				"(SELECT @num1 := IF(@waybill_no1 = t.waybill_no ,@num1  +1 ,1) num\r\n" + 
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
				",ADDDATE(DATE_FORMAT(t.biz_occur_date,'%Y-%m-%d'),INTERVAL 1 DAY) period_id\r\n" + 
				",0.00 	order_weight\r\n" + 
				",0.00		real_weight\r\n" + 
				",0.00		fee_weight\r\n" + 
				",0.00 	postage_total\r\n" + 
				",ifnull(t.postage_total,0) yesterday_postage_total\r\n" + 
				",0.00 postage_standard\r\n" + 
				",0.00 	postage_paid\r\n" + 
				",0.00	postage_other\r\n" + 
				",0.00 total_current_charges\r\n" + 
				",0.00 total_charge_owed\r\n" + 
				",0.00 as total_prepaid_charges\r\n" + 
				",0.00 as unpaid_amount\r\n" + 
				",0.00 as payment_amount\r\n" + 
				",0 files_qty\r\n" + 
				",case when t.contents_attribute = '1' then \r\n" + 
				"	1 else 0 end yesterday_files_qty\r\n" + 
				",0 goods_qty\r\n" + 
				",case when t.contents_attribute = '3' then \r\n" + 
				"	1 else 0 end yesterday_goods_qty\r\n" + 
				",0.00 files_revenue\r\n" + 
				",0.00 goods_revenue\r\n" + 
				",0  collected_qty\r\n" + 
				",IF(t.waybill_no IS NULL,0,1) yesterday_collection_qty\r\n" + 
				",0 delivery_qty\r\n" + 
				",0 yesterday_delivery_qty\r\n" + 
				",IFNULL(d.effective_person,0) effective_person\r\n" + 
				"from sdi_jxyz_pkp_waybill_base_${C_YEAR} t LEFT OUTER JOIN\r\n" + 
				"     (SELECT \r\n" + 
				"  	 d.dept_code,\r\n" + 
				"    d.dept_name,\r\n" + 
				"    d.province_code,\r\n" + 
				"    d.province_name,\r\n" + 
				"    d.city_code,\r\n" + 
				"    d.city_name,\r\n" + 
				"    d.county_code,\r\n" + 
				"    d.county_name,\r\n" + 
				"  SUM(effective_number) effective_person \r\n" + 
				"FROM\r\n" + 
				"  (SELECT \r\n" + 
				"    d.dept_code,\r\n" + 
				"    d.dept_name,\r\n" + 
				"    d.province_code,\r\n" + 
				"    d.province_name,\r\n" + 
				"    d.city_code,\r\n" + 
				"    d.city_name,\r\n" + 
				"    d.county_code,\r\n" + 
				"    d.county_name,\r\n" + 
				"    CASE\r\n" + 
				"      WHEN e.emp_status = '01' \r\n" + 
				"      THEN 1 \r\n" + 
				"      ELSE 0 \r\n" + 
				"    END AS effective_number \r\n" + 
				"  FROM\r\n" + 
				"    dwr_jxyz_emp_d e,\r\n" + 
				"    dwr_jxyz_department_d d \r\n" + 
				"  WHERE d.dept_code = e.emp_dept_code) d\r\n" + 
				"GROUP BY d.dept_code,\r\n" + 
				"    d.dept_name,\r\n" + 
				"    d.province_code,\r\n" + 
				"    d.province_name,\r\n" + 
				"    d.city_code,\r\n" + 
				"    d.city_name,\r\n" + 
				"    d.county_code,\r\n" + 
				"    d.county_name) d\r\n" + 
				"	ON d.dept_code = t.post_org_no\r\n" + 
				"	INNER JOIN \r\n" + 
				"     (SELECT @waybill_no1 := '',@num1 := 0) t1\r\n" + 
				"where t.biz_occur_date >= ADDDATE('${START_DATE}',INTERVAL -1 DAY)\r\n" + 
				"and t.biz_occur_date <= ADDDATE('${END_DATE}',INTERVAL -1 DAY)\r\n" + 
				"AND t.sender_province_no = '360000'\r\n" + 
				"and ifnull(t.postage_total,0) > 0\r\n" + 
				"and t.waybill_no is not null\r\n" + 
//				"and t.is_deleted = '0'\r\n" + 
				"ORDER BY t.waybill_no,t.biz_occur_date DESC) f\r\n" + 
				"WHERE num = 1\r\n" + 
				"GROUP BY post_org_id\r\n" + 
				",post_org_no\r\n" + 
				",org_drds_code\r\n" + 
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
				",period_id\r\n" + 
				"union all\r\n" + 
				"SELECT post_org_id\r\n" + 
				",post_org_no\r\n" + 
				",org_drds_code\r\n" + 
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
				",period_id\r\n" + 
				",SUM(order_weight				) order_weight				\r\n" + 
				",SUM(real_weight                ) real_weight               \r\n" + 
				",SUM(fee_weight                 ) fee_weight                \r\n" + 
				",SUM(postage_total              ) postage_total             \r\n" + 
				",SUM(yesterday_postage_total    ) yesterday_postage_total   \r\n" + 
				",SUM(postage_standard           ) postage_standard          \r\n" + 
				",SUM(postage_paid               ) postage_paid              \r\n" + 
				",SUM(postage_other              ) postage_other             \r\n" + 
				",SUM(total_current_charges      ) total_current_charges     \r\n" + 
				",SUM(total_charge_owed          ) total_charge_owed         \r\n" + 
				",SUM(total_prepaid_charges      ) total_prepaid_charges     \r\n" + 
				",SUM(unpaid_amount              ) unpaid_amount             \r\n" + 
				",SUM(payment_amount             ) payment_amount            \r\n" + 
				",SUM(files_qty                  ) files_qty                 \r\n" + 
				",SUM(yesterday_files_qty        ) yesterday_files_qty       \r\n" + 
				",SUM(goods_qty                  ) goods_qty                 \r\n" + 
				",SUM(yesterday_goods_qty        ) yesterday_goods_qty       \r\n" + 
				",SUM(files_revenue              ) files_revenue             \r\n" + 
				",SUM(goods_revenue              ) goods_revenue             \r\n" + 
				",SUM(collected_qty              ) collected_qty             \r\n" + 
				",SUM(yesterday_collection_qty   ) yesterday_collection_qty  \r\n" + 
				",SUM(delivery_qty               ) delivery_qty              \r\n" + 
				",SUM(yesterday_delivery_qty     ) yesterday_delivery_qty \r\n" + 
				",ROUND(AVG(effective_person)) effective_person FROM \r\n" + 
				"(SELECT @num2 := IF(@waybill_no2 = t.waybill_no ,@num2  +1 ,1) num\r\n" + 
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
				",ADDDATE(DATE_FORMAT(t.biz_occur_date,'%Y-%m-%d'),INTERVAL 1 DAY) period_id\r\n" + 
				",0.00 	order_weight\r\n" + 
				",0.00		real_weight\r\n" + 
				",0.00		fee_weight\r\n" + 
				",0.00 	postage_total\r\n" + 
				",ifnull(t.postage_total,0) yesterday_postage_total\r\n" + 
				",0.00 postage_standard\r\n" + 
				",0.00 	postage_paid\r\n" + 
				",0.00	postage_other\r\n" + 
				",0.00 total_current_charges\r\n" + 
				",0.00 total_charge_owed\r\n" + 
				",0.00 as total_prepaid_charges\r\n" + 
				",0.00 as unpaid_amount\r\n" + 
				",0.00 as payment_amount\r\n" + 
				",0 files_qty\r\n" + 
				",case when t.contents_attribute = '1' then \r\n" + 
				"	1 else 0 end yesterday_files_qty\r\n" + 
				",0 goods_qty\r\n" + 
				",case when t.contents_attribute = '3' then \r\n" + 
				"	1 else 0 end yesterday_goods_qty\r\n" + 
				",0.00 files_revenue\r\n" + 
				",0.00 goods_revenue\r\n" + 
				",0  collected_qty\r\n" + 
				",IF(t.waybill_no IS NULL,0,1) yesterday_collection_qty\r\n" + 
				",0 delivery_qty\r\n" + 
				",0 yesterday_delivery_qty\r\n" + 
				",IFNULL(d.effective_person,0) effective_person\r\n" + 
				"from sdi_jxyz_pkp_waybill_base_${L_YEAR} t LEFT OUTER JOIN\r\n" + 
				"     (SELECT \r\n" + 
				"  	 d.dept_code,\r\n" + 
				"    d.dept_name,\r\n" + 
				"    d.province_code,\r\n" + 
				"    d.province_name,\r\n" + 
				"    d.city_code,\r\n" + 
				"    d.city_name,\r\n" + 
				"    d.county_code,\r\n" + 
				"    d.county_name,\r\n" + 
				"  SUM(effective_number) effective_person \r\n" + 
				"FROM\r\n" + 
				"  (SELECT \r\n" + 
				"    d.dept_code,\r\n" + 
				"    d.dept_name,\r\n" + 
				"    d.province_code,\r\n" + 
				"    d.province_name,\r\n" + 
				"    d.city_code,\r\n" + 
				"    d.city_name,\r\n" + 
				"    d.county_code,\r\n" + 
				"    d.county_name,\r\n" + 
				"    CASE\r\n" + 
				"      WHEN e.emp_status = '01' \r\n" + 
				"      THEN 1 \r\n" + 
				"      ELSE 0 \r\n" + 
				"    END AS effective_number \r\n" + 
				"  FROM\r\n" + 
				"    dwr_jxyz_emp_d e,\r\n" + 
				"    dwr_jxyz_department_d d \r\n" + 
				"  WHERE d.dept_code = e.emp_dept_code) d\r\n" + 
				"GROUP BY d.dept_code,\r\n" + 
				"    d.dept_name,\r\n" + 
				"    d.province_code,\r\n" + 
				"    d.province_name,\r\n" + 
				"    d.city_code,\r\n" + 
				"    d.city_name,\r\n" + 
				"    d.county_code,\r\n" + 
				"    d.county_name) d\r\n" + 
				"	ON d.dept_code = t.post_org_no\r\n" + 
				"	INNER JOIN \r\n" + 
				"     (SELECT @waybill_no2 := '',@num2 := 0) t1\r\n" + 
				"where t.biz_occur_date >= ADDDATE('${START_DATE}',INTERVAL -1 DAY)\r\n" + 
				"and t.biz_occur_date <= ADDDATE('${END_DATE}',INTERVAL -1 DAY)\r\n" + 
				"AND t.sender_province_no = '360000'\r\n" + 
				"and ifnull(t.postage_total,0) > 0\r\n" + 
				"and t.waybill_no is not null\r\n" + 
//				"and t.is_deleted = '0'\r\n" + 
				"ORDER BY t.waybill_no,t.biz_occur_date DESC) f\r\n" + 
				"WHERE num = 1\r\n" + 
				"GROUP BY post_org_id\r\n" + 
				",post_org_no\r\n" + 
				",org_drds_code\r\n" + 
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
				",period_id) f\r\n" + 
				"GROUP BY  post_org_id\r\n" + 
				",post_org_no\r\n" + 
				",org_drds_code\r\n" + 
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
				",period_id\r\n";
		// 替换变量
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("C_YEAR", (String) Application.GLOBAL_PARAM.get(Application.CURR_YEAR));
		params2.put("L_YEAR", (String) Application.GLOBAL_PARAM.get(Application.LAST_YEAR));
		params2.put("START_DATE", (String) Application.GLOBAL_PARAM.get(Application.LAST_DAY));
		params2.put("END_DATE", (String) Application.GLOBAL_PARAM.get(Application.CURR_DAY));
		querySQL = Tools.parse(querySQL, params2);
        System.out.println("SQL: " + querySQL);
		PreparedStatement queryPs = connection.prepareStatement(querySQL);

		String insertSQL = "insert into dwr_sales_department_collection_t(goods_revenue,delivery_qty,sender_district_no,post_org_no,real_weight,postage_total,fee_weight,sender_country_name,sender_province_no,sender_county_name,collected_qty,sender_country_no,postage_other,post_org_name,yesterday_postage_total,sender_city_no,sender_city_name,period_id,order_weight,yesterday_collection_qty,yesterday_goods_qty,post_org_id,sender_county_no,payment_amount,goods_qty,sender_province_name,total_current_charges,yesterday_delivery_qty,daily_effective_person,postage_paid,total_charge_owed,org_drds_code,yesterday_files_qty,files_qty,files_revenue,total_prepaid_charges,unpaid_amount,postage_standard)value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertPs = connection.prepareStatement(insertSQL);

		ResultSet rs = queryPs.executeQuery();
		int count = 0;
		while (rs.next()) {
			insertPs.setBigDecimal(1, rs.getBigDecimal("goods_revenue"));
			insertPs.setInt(2, rs.getInt("delivery_qty"));
			insertPs.setString(3, rs.getString("sender_district_no"));
			insertPs.setString(4, rs.getString("post_org_no"));
			insertPs.setBigDecimal(5, rs.getBigDecimal("real_weight"));
			insertPs.setBigDecimal(6, rs.getBigDecimal("postage_total"));
			insertPs.setBigDecimal(7, rs.getBigDecimal("fee_weight"));
			insertPs.setString(8, rs.getString("sender_country_name"));
			insertPs.setString(9, rs.getString("sender_province_no"));
			insertPs.setString(10, rs.getString("sender_county_name"));
			insertPs.setInt(11, rs.getInt("collected_qty"));
			insertPs.setString(12, rs.getString("sender_country_no"));
			insertPs.setBigDecimal(13, rs.getBigDecimal("postage_other"));
			insertPs.setString(14, rs.getString("post_org_name"));
			insertPs.setBigDecimal(15, rs.getBigDecimal("yesterday_postage_total"));
			insertPs.setString(16, rs.getString("sender_city_no"));
			insertPs.setString(17, rs.getString("sender_city_name"));
			insertPs.setDate(18, rs.getDate("period_id"));
			insertPs.setBigDecimal(19, rs.getBigDecimal("order_weight"));
			insertPs.setInt(20, rs.getInt("yesterday_collection_qty"));
			insertPs.setInt(21, rs.getInt("yesterday_goods_qty"));
			insertPs.setInt(22, rs.getInt("post_org_id"));
			insertPs.setString(23, rs.getString("sender_county_no"));
			insertPs.setBigDecimal(24, rs.getBigDecimal("payment_amount"));
			insertPs.setInt(25, rs.getInt("goods_qty"));
			insertPs.setString(26, rs.getString("sender_province_name"));
			insertPs.setBigDecimal(27, rs.getBigDecimal("total_current_charges"));
			insertPs.setInt(28, rs.getInt("yesterday_delivery_qty"));
			insertPs.setInt(29, rs.getInt("effective_person"));
			insertPs.setBigDecimal(30, rs.getBigDecimal("postage_paid"));
			insertPs.setBigDecimal(31, rs.getBigDecimal("total_charge_owed"));
			insertPs.setString(32, rs.getString("org_drds_code"));
			insertPs.setInt(33, rs.getInt("yesterday_files_qty"));
			insertPs.setInt(34, rs.getInt("files_qty"));
			insertPs.setBigDecimal(35, rs.getBigDecimal("files_revenue"));
			insertPs.setBigDecimal(36, rs.getBigDecimal("total_prepaid_charges"));
			insertPs.setBigDecimal(37, rs.getBigDecimal("unpaid_amount"));
			insertPs.setBigDecimal(38, rs.getBigDecimal("postage_standard"));
			insertPs.executeUpdate();
			count++;
		}
		rs.close();
		queryPs.close();
		insertPs.close();
		System.out.println("写入dwr_sales_department_collection_t" + " 记录数:" + count);
		
		Map<String,String> transferMap = new HashMap<>();
		transferMap.put("tableName", "dwr_sales_department_collection_t");
		String selectSql = "select * from dwr_sales_department_collection_t where period_id >= '${START_DATE}' and period_id <= '${END_DATE}'";
		selectSql = Tools.parse(selectSql, params);
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", deleteSQL);
		HttpUtil.upload(transferMap);
	}

}
