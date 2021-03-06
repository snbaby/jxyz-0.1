package com.isoftstone.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.drinkjava2.jsqlbox.DB;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalaryAnalysisService {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat bizOccurDateDf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
	private SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
	private SimpleDateFormat periodDf = new SimpleDateFormat("yyyyMM");

	@Async
	public void salaryAnalysis(Map<String, Object> empMap, List<Map<String, Object>> ruleMapList,
			List<Map<String, Object>> tGridMMapList) throws SQLException {
		DbContext dbContext = DbContext.getGlobalDbContext();
		String period_id = periodDf.format(new Date());
		String emp_code = Utils.getMapStr(empMap, "emp_code");
		String emp_name = Utils.getMapStr(empMap, "emp_name");
		String all_parent_code = Utils.getMapStr(empMap, "all_parent_code");
		String province_code = "";
		String province_name = "";
		String city_code = "";
		String city_name = "";
		String county_code = "";
		String county_name = "";
		String[] all_parent_codeArray = all_parent_code.split(",");
		if (all_parent_codeArray.length > 3) {
			province_code = all_parent_codeArray[0];
			city_code = all_parent_codeArray[1];

			county_code = all_parent_codeArray[2];
			for (Map<String, Object> tGridMMap : tGridMMapList) {
				if (tGridMMap.getOrDefault("code", "").equals(province_code)) {
					province_name = Utils.getMapStr(tGridMMap, "name");
				} else if (tGridMMap.getOrDefault("code", "").equals(city_code)) {
					city_name = Utils.getMapStr(tGridMMap, "name");
				} else if (tGridMMap.getOrDefault("code", "").equals(county_code)) {
					county_name = Utils.getMapStr(tGridMMap, "name");
				}
			}
		} else {
			log.error("行政区域错误：emp_code:{},all_parent_code:{}", emp_code, all_parent_code);
			return;
		}

		String dept_code = Utils.getMapStr(empMap, "emp_dept_code");
		String dept_name = Utils.getMapStr(empMap, "emp_dept_name");
		String grid_code = Utils.getMapStr(empMap, "emp_section_code");
		String grid_name = Utils.getMapStr(empMap, "emp_section_name");
		double basic_salary = 0;
		int deliver_num = 0;
		double deliver_total = 0;
		int deliver_court_num = 0;
		double deliver_court_total = 0;
		int standard_delivery_num = 0;
		double standard_delivery_total = 0;
		int express_package_num = 0;
		double express_package_total = 0;
		int international_delivery_num = 0;
		double international_delivery_total = 0;
		String modify_time = sdf.format(new Date());
		String ado_id = Utils.getMapStr(empMap, "ado_id");

		List<Map<String, Object>> thisRuleMapList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < ruleMapList.size(); i++) {
			Map<String, Object> ruleMap = ruleMapList.get(i);
			if (ruleMap.getOrDefault("city_code", "").equals(city_code)) {
				thisRuleMapList.add(ruleMap);
			}
			;
		}

		if (thisRuleMapList.size() > 0) {
			basic_salary = Utils.getMapDouble(thisRuleMapList.get(0), "basic_salary");
		}

		String msgSql = "select trace_no,op_org_code,op_erator_no from sdi_jxyz_pkp_trace_message_704_"
				+ periodDf.format(new Date()) + " where op_erator_no = ? and op_code = '704' ";
		List<Map<String, Object>> msgMapList = dbContext.qryMapList(msgSql, DB.param(empMap.get("emp_code")));
		Set<String> trace_noSet = new HashSet<String>();
		for (Map<String, Object> msgMap : msgMapList) {
			String trace_no = Utils.getMapStr(msgMap, "trace_no");
			if (!trace_noSet.contains(trace_no)) {
				trace_noSet.add(trace_no);// 去重trace_no
			} else {
				continue;
			}
			String isCourt = ""; // 是否为法院专线
			String type = "1"; // 投递
			if (trace_no.endsWith("93") || trace_no.endsWith("94")) { // 运单号以93、94结尾的为法院专线
				isCourt = "1"; // 法院专线
			} else {
				isCourt = "0"; // 基础投递
			}
			boolean is_matching = false;
			for (int i = 0; i < thisRuleMapList.size(); i++) {
				Map<String, Object> thisRuleMap = thisRuleMapList.get(i);
				if (thisRuleMap.getOrDefault("type", "").equals(type)
						&& thisRuleMap.getOrDefault("is_loose_items", "").equals(isCourt)) {
					double fee = Utils.getMapDouble(thisRuleMap, "fixed_income");
					deliver_num++;
					deliver_total += fee;
					is_matching = true;
				}
			}
			if (!is_matching) {
				log.error("投递未匹配上规则：trace_no:{}", trace_no);
			}
		}
		String baseSql = "select waybill_no,base_product_no,product_reach_area,contents_attribute,sender_type,postage_total,postage_standard,postage_paid,postage_other,discount_rate from sdi_jxyz_pkp_waybill_base_"
				+ yearDf.format(new Date())
				+ " where biz_occur_date >= ? and post_person_no = ? and postage_paid != 0 ";
		List<Map<String, Object>> baseMapList = dbContext.qryMapList(baseSql,
				DB.param(bizOccurDateDf.format(new Date()), empMap.get("emp_code")));

		Set<String> waybill_noSet = new HashSet<String>();
		for (Map<String, Object> baseMap : baseMapList) {
			String waybill_no = Utils.getMapStr(baseMap, "waybill_no");

			if (!waybill_noSet.contains(waybill_no)) {
				waybill_noSet.add(waybill_no);// 去重waybill_no
			} else {
				continue;
			}
			String loose_items = baseMap.getOrDefault("sender_type", "0").equals("0") ? "1" : "0";// 是否为散件,'1'散户，'0'，协议客户
			String type = "";
			String base_product_no = (String) baseMap.getOrDefault("base_product_no", "");
			if (("21210".equals(base_product_no) || "22210".equals(base_product_no) || "21412".equals(base_product_no))) { // 国际标快
				type = "4";// 国际标快
			} else if ("11312".equals(base_product_no)) { // 包裹
				type = "3";// 包裹
			} else if("11210".equals(base_product_no)){
				type = "2";// 标快
			} else {
				continue; // 其他则不计算
			}

			boolean is_matching = false;
			for (int i = 0; i < thisRuleMapList.size(); i++) {
				Map<String, Object> thisRuleMap = thisRuleMapList.get(i);
				if (thisRuleMap.getOrDefault("type", "").equals(type)
						&& thisRuleMap.getOrDefault("is_loose_items", "").equals(loose_items)) {
					double discount_rate = Utils.getMapDouble(baseMap, "discount_rate") > 100 ? 100
							: Utils.getMapDouble(baseMap, "discount_rate");
					double min_discount = Utils.getMapDouble(thisRuleMap, "min_discount") * 10;
					double max_discount = Utils.getMapDouble(thisRuleMap, "max_discount") * 10;
					if ((discount_rate == min_discount && min_discount == max_discount)
							|| (discount_rate > min_discount && discount_rate < max_discount)) {
						basic_salary = Utils.getMapDouble(thisRuleMap, "basic_salary");
						double fee = Utils.getMapDouble(thisRuleMap, "fixed_income")
								+ Utils.getMapDouble(thisRuleMap, "commission_rate")
										* Utils.getMapDouble(baseMap, "postage_paid");
						if (thisRuleMap.getOrDefault("is_discount", "1").equals("1")) {// 打折
							fee = fee * discount_rate / 100;
						}

						if (type.equals("2")) {// 标快
							standard_delivery_num++;
							standard_delivery_total += fee;
						} else if (type.equals("3")) {// 包裹
							express_package_num++;
							express_package_total += fee;
						} else {// 国际标快
							international_delivery_num++;
							international_delivery_total += fee;
						}
						is_matching = true;
						break;
					}
				}
			}
			if (!is_matching) {
				log.error("揽收未匹配上规则：waybill_no:{}", waybill_no);
			}
		}

		dbContext.execute("delete from t_emolument_result where period_id = ? and emp_code = ?",period_id, emp_code);
		String resultInsert = "insert INTO t_emolument_result(period_id,emp_code,emp_name,province_code,province_name,city_code,city_name,county_code,county_name,dept_code,dept_name,grid_code,grid_name,all_parent_code,basic_salary,deliver_num,deliver_total,deliver_court_num,deliver_court_total,standard_delivery_num,standard_delivery_total,express_package_num,express_package_total,international_delivery_num,international_delivery_total,modify_time,ado_id)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		dbContext.execute(resultInsert, period_id, emp_code, emp_name, province_code, province_name, city_code,
				city_name, county_code, county_name, dept_code, dept_name, grid_code, grid_name, all_parent_code,
				basic_salary, deliver_num, deliver_total, deliver_court_num, deliver_court_total, standard_delivery_num,
				standard_delivery_total, express_package_num, express_package_total, international_delivery_num,
				international_delivery_total, modify_time, ado_id);
	}

}
