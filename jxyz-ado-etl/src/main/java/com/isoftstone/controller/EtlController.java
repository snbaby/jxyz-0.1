package com.isoftstone.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.isoftstone.component.EtlComponent;
import com.isoftstone.service.EtlService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName EtlController
 * @Deacription TODO
 * @Author wangjian
 * @Date 2020/11/18 13:58
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "etl")
@Slf4j
public class EtlController {

	@Autowired
	public EtlComponent etlComponent;

	@Value("${jxyzadoetl.etl.download}")
	private String downloadBasePath;

	@Value("${jxyzadoetl.etl.unzip}")
	private String unzipBasePath;

	@Value("${jxyzadoetl.etl.url}")
	private String downloadBaseUrl;

	@Autowired
	private EtlService etlService;

	@RequestMapping(value = "download/{id}", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject download(@PathVariable(value = "id") String id, String tableName, String condition)
			throws Exception {
		etlComponent.asyncParse(id, tableName, condition);
		JSONObject jsb = new JSONObject();
		jsb.put("success", id);
		return jsb;
	}

	@RequestMapping(value = "syncDwrResources", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject syncDwrResources(String tableName, int pageNum, int pageSize) throws Exception {
		DbContext ctx = DbContext.getGlobalDbContext();
		List<Map<String, Object>> maps = ctx.qryMapList("select * from " + tableName, JSQLBOX.pagin(pageNum, pageSize));
		JSONObject jsb = new JSONObject();
		jsb.put("data", maps);
		jsb.put("total", maps.size());
		return jsb;
	}

	@RequestMapping(value = "qry", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject qry(@RequestBody JSONObject jsb) throws Exception {
		DbContext ctx = DbContext.getGlobalDbContext();
		String qry = jsb.getString("qry");// 查询语句
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotBlank(qry)) {
			maps = ctx.qryMapList(qry);
		}
		jsb.put("data", maps);
		return jsb;
	}

	@RequestMapping(value = "ins", method = RequestMethod.POST)
	public void exe(@RequestBody JSONObject jsb) throws Exception {
		String prefix = jsb.getString("prefix");// 预处理语句
		String table = jsb.getString("table");// 表名
		JSONArray dataJsa = jsb.getJSONArray("data");
		String suffix = jsb.getString("suffix");// 预处理语句

		switch (table) {
		case "dm_customer_month_revenue_t":
			etlService.dm_customer_month_revenue_t(prefix, dataJsa, suffix);
			break;
		case "dm_delivery_month_t":
			etlService.dm_delivery_month_t(prefix, dataJsa, suffix);
			break;

		case "dm_emp_month_collection_t":
			etlService.dm_emp_month_collection_t(prefix, dataJsa, suffix);
			break;
		case "dm_jxyz_emp_info_t":
			etlService.dm_jxyz_emp_info_t(prefix, dataJsa, suffix);
			break;
		case "dm_jxyz_sand_section_t":
			etlService.dm_jxyz_sand_section_t(prefix, dataJsa, suffix);
			break;
		case "dm_jxyz_sand_table_t":
			etlService.dm_jxyz_sand_table_t(prefix, dataJsa, suffix);
			break;
		case "dm_jxyz_sectin_info_t":
			etlService.dm_jxyz_sectin_info_t(prefix, dataJsa, suffix);
			break;
		case "dm_regional_month_collection_t":
			etlService.dm_regional_month_collection_t(prefix, dataJsa, suffix);
			break;
		case "dm_sales_department_collection_month_t":
			etlService.dm_sales_department_collection_month_t(prefix, dataJsa, suffix);
			break;
		case "dwr_customer_daily_revenue_t":
			etlService.dwr_customer_daily_revenue_t(prefix, dataJsa, suffix);
			break;
		case "dwr_delivery_detail_t":
			etlService.dwr_delivery_detail_t(prefix, dataJsa, suffix);
			break;
		case "dwr_emp_daily_collection_t":
			etlService.dwr_emp_daily_collection_t(prefix, dataJsa, suffix);
			break;
		case "dwr_jxyz_customer_d":
			etlService.dwr_jxyz_customer_d(prefix, dataJsa, suffix);
			break;
		case "dwr_jxyz_customer_relation_d":
			etlService.dwr_jxyz_customer_relation_d(prefix, dataJsa, suffix);
			break;
		case "dwr_jxyz_department_d":
			etlService.dwr_jxyz_department_d(prefix, dataJsa, suffix);
			break;
		case "dwr_jxyz_emp_d":
			etlService.dwr_jxyz_emp_d(prefix, dataJsa, suffix);
			break;
		case "dwr_jxyz_region_d":
			etlService.dwr_jxyz_region_d(prefix, dataJsa, suffix);
			break;
		case "dwr_jxyz_resources_d":
			etlService.dwr_jxyz_resources_d(prefix, dataJsa, suffix);
			break;
		case "dwr_regional_daily_collection_t":
			etlService.dwr_regional_daily_collection_t(prefix, dataJsa, suffix);
			break;
		case "dwr_sales_department_collection_t":
			etlService.dwr_sales_department_collection_t(prefix, dataJsa, suffix);
			break;
		case "t_emolument_result":
			etlService.t_emolument_result(prefix, dataJsa, suffix);
			break;
		case "t_emolument_rule":
			etlService.t_emolument_rule(prefix, dataJsa, suffix);
			break;
		case "t_emolument_template":
			etlService.t_emolument_template(prefix, dataJsa, suffix);
			break;
		case "t_grid_m":
			etlService.t_grid_m(prefix, dataJsa, suffix);
			break;
		case "t_grid_m_0928":
			etlService.t_grid_m_0928(prefix, dataJsa, suffix);
			break;
		default:
			log.error("table：{},不在计算之列", table);
			break;
		}
	}
}
