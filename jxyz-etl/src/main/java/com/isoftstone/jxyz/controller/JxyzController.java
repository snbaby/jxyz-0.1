package com.isoftstone.jxyz.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.isoftstone.jxyz.service.AdoService;
import com.isoftstone.jxyz.service.JxyzDownloadService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/etl")
@Slf4j
public class JxyzController {

	@Autowired
	private AdoService adoService;

	@Autowired
	private JxyzDownloadService jxyzDownloadService;

	@PostMapping(value = "/token")
	@ResponseBody
	public ResponseEntity<JSONObject> token()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONObject resJsb = new JSONObject();
		resJsb.put("code", 0);
		resJsb.put("msg", "success");
		resJsb.put("data", adoService.getToken());
		return new ResponseEntity<>(resJsb, HttpStatus.OK);
	}

	@PostMapping(value = "/download")
	public void download(@RequestBody JSONObject reqJsb)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		String qry = reqJsb.getString("qry");
		String prefix = reqJsb.getString("prefix");
		String suffix = reqJsb.getString("suffix");
		String table = reqJsb.getString("table");
		String id = UUID.randomUUID().toString();// 日志ID

		switch (table) {
		case "dm_customer_month_revenue_t":// 客户销售月统计
			jxyzDownloadService.dm_customer_month_revenue_t(prefix, qry, suffix, table, id);
			break;
		case "dm_delivery_month_t":// 投递月统计
			jxyzDownloadService.dm_delivery_month_t(prefix, qry, suffix, table, id);
			break;

		case "dm_emp_month_collection_t":// 员工销售月统计
			jxyzDownloadService.dm_emp_month_collection_t(prefix, qry, suffix, table, id);
			break;
		case "dm_jxyz_emp_info_t":// 员工沙盘展示
			jxyzDownloadService.dm_jxyz_emp_info_t(prefix, qry, suffix, table, id);
			break;
		case "dm_jxyz_sand_section_t":// 段道信息表
			jxyzDownloadService.dm_jxyz_sand_section_t(prefix, qry, suffix, table, id);
			break;
		case "dm_jxyz_sand_table_t":// 沙盘表
			jxyzDownloadService.dm_jxyz_sand_table_t(prefix, qry, suffix, table, id);
			break;
		case "dm_jxyz_sectin_info_t":
			jxyzDownloadService.dm_jxyz_sectin_info_t(prefix, qry, suffix, table, id);
			break;
		case "dm_regional_month_collection_t":// 区域销售月统计
			jxyzDownloadService.dm_regional_month_collection_t(prefix, qry, suffix, table, id);
			break;
		case "dm_sales_department_collection_month_t":// 部门销售月统计
			jxyzDownloadService.dm_sales_department_collection_month_t(prefix, qry, suffix, table, id);
			break;
		case "dwr_customer_daily_revenue_t":// 客户日销售统计
			jxyzDownloadService.dwr_customer_daily_revenue_t(prefix, qry, suffix, table, id);
			break;
		case "dwr_delivery_detail_t":// 投递明细表
			jxyzDownloadService.dwr_delivery_detail_t(prefix, qry, suffix, table, id);
			break;
		case "dwr_emp_daily_collection_t":// 员工日销售统计
			jxyzDownloadService.dwr_emp_daily_collection_t(prefix, qry, suffix, table, id);
			break;
		case "dwr_jxyz_customer_d":// 客户表
			jxyzDownloadService.dwr_jxyz_customer_d(prefix, qry, suffix, table, id);
			break;
		case "dwr_jxyz_customer_relation_d":
			jxyzDownloadService.dwr_jxyz_customer_relation_d(prefix, qry, suffix, table, id);
			break;
		case "dwr_jxyz_department_d":// 部门表
			jxyzDownloadService.dwr_jxyz_department_d(prefix, qry, suffix, table, id);
			break;
		case "dwr_jxyz_emp_d":// 员工表
			jxyzDownloadService.dwr_jxyz_emp_d(prefix, qry, suffix, table, id);
			break;
		case "dwr_jxyz_region_d":
			jxyzDownloadService.dwr_jxyz_region_d(prefix, qry, suffix, table, id);
			break;
		case "dwr_jxyz_resources_d":
			jxyzDownloadService.dwr_jxyz_resources_d(prefix, qry, suffix, table, id);
			break;
		case "dwr_regional_daily_collection_t":// 区域销售日统计
			jxyzDownloadService.dwr_regional_daily_collection_t(prefix, qry, suffix, table, id);
			break;
		case "dwr_sales_department_collection_t":// 部门销售日统计
			jxyzDownloadService.dwr_sales_department_collection_t(prefix, qry, suffix, table, id);
			break;
		case "t_emolument_result":// 薪酬结果
			jxyzDownloadService.t_emolument_result(prefix, qry, suffix, table, id);
			break;
		case "t_emolument_rule":// 薪酬规则
			jxyzDownloadService.t_emolument_rule(prefix, qry, suffix, table, id);
			break;
		case "t_emolument_template":// 薪酬模板
			jxyzDownloadService.t_emolument_template(prefix, qry, suffix, table, id);
			break;
		case "t_grid_m":// 网格段道表
			jxyzDownloadService.t_grid_m(prefix, qry, suffix, table, id);
			break;
		case "t_grid_m_0928":
			jxyzDownloadService.t_grid_m_0928(prefix, qry, suffix, table, id);
			break;
		case "t_biz_building_m":
			jxyzDownloadService.t_biz_building_m(prefix, qry, suffix, table, id);
			break;
		case "t_biz_campus_m":
			jxyzDownloadService.t_biz_campus_m(prefix, qry, suffix, table, id);
			break;
		case "t_biz_enterprise_m":
			jxyzDownloadService.t_biz_enterprise_m(prefix, qry, suffix, table, id);
			break;
		case "t_uptown_m":
			jxyzDownloadService.t_uptown_m(prefix, qry, suffix, table, id);
			break;
		case "t_biz_resource_customer_relation":
			jxyzDownloadService.t_biz_resource_customer_relation(prefix, qry, suffix, table, id);
			break;
		case "t_biz_enterprise_customer_m":
			jxyzDownloadService.t_biz_enterprise_customer_m(prefix, qry, suffix, table, id);
			break;
		case "t_biz_enterprise_customer_contract_m":
			jxyzDownloadService.t_biz_enterprise_customer_contract_m(prefix, qry, suffix, table, id);
			break;
		case "t_biz_enterprise_customer_instance_m":
			jxyzDownloadService.t_biz_enterprise_customer_instance_m(prefix, qry, suffix, table, id);
			break;
		default:
			log.error("table：{},不在计算之列", table);
			break;
		}
	}

	@PostMapping(value = "/upload")
	public void upload(@RequestBody JSONObject reqJsb)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		String ins = reqJsb.getString("ins");
		String prefix = reqJsb.getString("prefix");
		String suffix = reqJsb.getString("suffix");
		String table = reqJsb.getString("table");

		adoService.insDatas(ins, prefix, suffix, table);
	}

}
