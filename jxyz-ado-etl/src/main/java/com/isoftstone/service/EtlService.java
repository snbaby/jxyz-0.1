package com.isoftstone.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.isoftstone.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;

@Service
public class EtlService {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Async
	public void dm_customer_month_revenue_t(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));

		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DmCustomerMonthRevenueT dmCustomerMonthRevenueT = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DmCustomerMonthRevenueT.class);
				dmCustomerMonthRevenueT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dm_delivery_month_t(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DmDeliveryMonthT dmDeliveryMonthT = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DmDeliveryMonthT.class);
				dmDeliveryMonthT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dm_emp_month_collection_t(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DmEmpMonthCollectionT dmEmpMonthCollectionT = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DmEmpMonthCollectionT.class);
				dmEmpMonthCollectionT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dm_jxyz_emp_info_t(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DmJxyzEmpInfoT dmJxyzEmpInfoT = JSONObject.toJavaObject(dataJsa.getJSONObject(i), DmJxyzEmpInfoT.class);
				dmJxyzEmpInfoT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dm_jxyz_sand_section_t(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DmJxyzSandSectionT dmJxyzSandSectionT = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DmJxyzSandSectionT.class);
				dmJxyzSandSectionT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dm_jxyz_sand_table_t(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DmJxyzSandTableT dmJxyzSandTableT = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DmJxyzSandTableT.class);
				dmJxyzSandTableT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dm_jxyz_sectin_info_t(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DmJxyzSectinInfoT dmJxyzSectinInfoT = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DmJxyzSectinInfoT.class);
				dmJxyzSectinInfoT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dm_regional_month_collection_t(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DmRegionalMonthCollectionT dmRegionalMonthCollectionT = JSONObject
						.toJavaObject(dataJsa.getJSONObject(i), DmRegionalMonthCollectionT.class);
				dmRegionalMonthCollectionT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dm_sales_department_collection_month_t(String prefix, JSONArray dataJsa, String suffix, String table,
			String id) throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DmSalesDepartmentCollectionMonthT dmSalesDepartmentCollectionMonthT = JSONObject
						.toJavaObject(dataJsa.getJSONObject(i), DmSalesDepartmentCollectionMonthT.class);
				dmSalesDepartmentCollectionMonthT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dwr_customer_daily_revenue_t(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DwrCustomerDailyRevenueT dwrCustomerDailyRevenueT = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DwrCustomerDailyRevenueT.class);
				dwrCustomerDailyRevenueT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dwr_delivery_detail_t(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DwrDeliveryDetailT dwrDeliveryDetailT = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DwrDeliveryDetailT.class);
				dwrDeliveryDetailT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dwr_emp_daily_collection_t(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DwrEmpDailyCollectionT dwrEmpDailyCollectionT = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DwrEmpDailyCollectionT.class);
				dwrEmpDailyCollectionT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dwr_jxyz_customer_d(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DwrJxyzCustomerD dwrJxyzCustomerD = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DwrJxyzCustomerD.class);
				dwrJxyzCustomerD.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dwr_jxyz_customer_relation_d(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DwrJxyzCustomerRelationD dwrJxyzCustomerRelationD = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DwrJxyzCustomerRelationD.class);
				dwrJxyzCustomerRelationD.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dwr_jxyz_department_d(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DwrJxyzDepartmentD dwrJxyzDepartmentD = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DwrJxyzDepartmentD.class);
				dwrJxyzDepartmentD.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dwr_jxyz_emp_d(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DwrJxyzEmpD dwrJxyzEmpD = JSONObject.toJavaObject(dataJsa.getJSONObject(i), DwrJxyzEmpD.class);
				dwrJxyzEmpD.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dwr_jxyz_region_d(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DwrJxyzRegionD dwrJxyzRegionD = JSONObject.toJavaObject(dataJsa.getJSONObject(i), DwrJxyzRegionD.class);
				dwrJxyzRegionD.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dwr_jxyz_resources_d(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DwrJxyzResourcesD dwrJxyzResourcesD = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						DwrJxyzResourcesD.class);
				dwrJxyzResourcesD.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dwr_regional_daily_collection_t(String prefix, JSONArray dataJsa, String suffix, String table,
			String id) throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DwrRegionalDailyCollectionT dwrRegionalDailyCollectionT = JSONObject
						.toJavaObject(dataJsa.getJSONObject(i), DwrRegionalDailyCollectionT.class);
				dwrRegionalDailyCollectionT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void dwr_sales_department_collection_t(String prefix, JSONArray dataJsa, String suffix, String table,
			String id) throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				DwrSalesDepartmentCollectionT dwrSalesDepartmentCollectionT = JSONObject
						.toJavaObject(dataJsa.getJSONObject(i), DwrSalesDepartmentCollectionT.class);
				dwrSalesDepartmentCollectionT.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void t_emolument_result(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TEmolumentResult tEmolumentResult = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						TEmolumentResult.class);
				tEmolumentResult.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void t_emolument_rule(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TEmolumentRule tEmolumentRule = JSONObject.toJavaObject(dataJsa.getJSONObject(i), TEmolumentRule.class);
				tEmolumentRule.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void t_emolument_template(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TEmolumentTemplate tEmolumentTemplate = JSONObject.toJavaObject(dataJsa.getJSONObject(i),
						TEmolumentTemplate.class);
				tEmolumentTemplate.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void t_grid_m(String prefix, JSONArray dataJsa, String suffix, String table, String id) throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TGridM tGridM = JSONObject.toJavaObject(dataJsa.getJSONObject(i), TGridM.class);
				tGridM.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
	public void t_grid_m_0928(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TGridM0928 tGridM0928 = JSONObject.toJavaObject(dataJsa.getJSONObject(i), TGridM0928.class);
				tGridM0928.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
	}

	@Async
    public void jpx_resource_collect_d(String prefix, JSONArray dataJsa, String suffix, String table, String id)
			throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();

		ctx.execute("INSERT INTO t_ado_etl_ins_log(id,`table`,prefix,suffix,start_time,create_time)VALUES(?,?,?,?,?,?)",
				id, table, prefix, suffix, sdf.format(new Date()), sdf.format(new Date()));
		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				JpxResourceCollectD jpxResourceCollectD = JSONObject.toJavaObject(dataJsa.getJSONObject(i), JpxResourceCollectD.class);
				jpxResourceCollectD.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}
		ctx.execute("update t_ado_etl_ins_log set total = ?, end_time = ? where id = ?", dataJsa.size(),
				sdf.format(new Date()), id);
    }
}
