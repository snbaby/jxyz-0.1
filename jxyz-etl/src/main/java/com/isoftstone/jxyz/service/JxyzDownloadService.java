package com.isoftstone.jxyz.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.isoftstone.jxyz.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;

@Service
public class JxyzDownloadService {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private AdoService adoService;

	public void dm_customer_month_revenue_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);
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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dm_delivery_month_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dm_emp_month_collection_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dm_jxyz_emp_info_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dm_jxyz_sand_section_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dm_jxyz_sand_table_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dm_jxyz_sectin_info_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dm_regional_month_collection_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dm_sales_department_collection_month_t(String prefix, String qry, String suffix, String table,
			String id) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dwr_customer_daily_revenue_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dwr_delivery_detail_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dwr_emp_daily_collection_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dwr_jxyz_customer_d(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dwr_jxyz_customer_relation_d(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dwr_jxyz_department_d(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dwr_jxyz_emp_d(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dwr_jxyz_region_d(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dwr_jxyz_resources_d(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dwr_regional_daily_collection_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void dwr_sales_department_collection_t(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void t_emolument_result(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void t_emolument_rule(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void t_emolument_template(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void t_grid_m(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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
		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void t_grid_m_0928(String prefix, String qry, String suffix, String table, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

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

		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void t_biz_building_m(String prefix, String qry, String suffix, String table, String id) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {

		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TBizBuildingM tBizBuildingM = JSONObject.toJavaObject(dataJsa.getJSONObject(i), TBizBuildingM.class);
				tBizBuildingM.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}

		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void t_biz_campus_m(String prefix, String qry, String suffix, String table, String id) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {

		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TBizCampusM tBizCampusM = JSONObject.toJavaObject(dataJsa.getJSONObject(i), TBizCampusM.class);
				tBizCampusM.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}

		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}


	public void t_biz_enterprise_m(String prefix, String qry, String suffix, String table, String id) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {

		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TBizEnterpriseM TBizEnterpriseM = JSONObject.toJavaObject(dataJsa.getJSONObject(i), TBizEnterpriseM.class);
				TBizEnterpriseM.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}

		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void t_uptown_m(String prefix, String qry, String suffix, String table, String id) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException{

		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TUptownM tUptownM = JSONObject.toJavaObject(dataJsa.getJSONObject(i), TUptownM.class);
				tUptownM.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}

		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void t_biz_resource_customer_relation(String prefix, String qry, String suffix, String table, String id) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {

		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TBizResourceCustomerRelation tBizResourceCustomerRelation = JSONObject.toJavaObject(dataJsa.getJSONObject(i), TBizResourceCustomerRelation.class);
				tBizResourceCustomerRelation.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}

		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void t_biz_enterprise_customer_m(String prefix, String qry, String suffix, String table, String id) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {

		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TBizEnterpriseCustomerM tBizEnterpriseCustomerM = JSONObject.toJavaObject(dataJsa.getJSONObject(i), TBizEnterpriseCustomerM.class);
				tBizEnterpriseCustomerM.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}

		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void t_biz_enterprise_customer_contract_m(String prefix, String qry, String suffix, String table, String id) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {

		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TBizEnterpriseCustomerContractM tBizEnterpriseCustomerContractM = JSONObject.toJavaObject(dataJsa.getJSONObject(i), TBizEnterpriseCustomerContractM.class);
				tBizEnterpriseCustomerContractM.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}

		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}

	public void t_biz_enterprise_customer_instance_m(String prefix, String qry, String suffix, String table, String id) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {

		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.execute(
				"INSERT INTO t_etl_download_log(id,qry,prefix,suffix,`table`,start_time,create_time)VALUES(?,?,?,?,?,?,?);",
				id, qry, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));
		JSONArray dataJsa = adoService.qryDatas(qry, id).getJSONArray("data");
		ctx.execute("update t_etl_download_log set total = ?,end_time= ?,insert_start_time= ? where id = ?",
				dataJsa.size(), sdf.format(new Date()), sdf.format(new Date()), id);

		if (StringUtils.isNotBlank(prefix)) {
			ctx.exe(prefix);
		}
		try {
			ctx.nBatchBegin();
			for (int i = 0; i < dataJsa.size(); i++) {
				TBizEnterpriseCustomerInstanceM tBizEnterpriseCustomerInstanceM = JSONObject.toJavaObject(dataJsa.getJSONObject(i), TBizEnterpriseCustomerInstanceM.class);
				tBizEnterpriseCustomerInstanceM.insert();
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}
		if (StringUtils.isNotBlank(suffix)) {
			ctx.exe(suffix);
		}

		ctx.execute("update t_etl_download_log set insert_end_time= ? where id = ?", sdf.format(new Date()), id);
	}
}
