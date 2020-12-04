package com.isoftstone.jxyz.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.jxyz.model.DmCustomerMonthRevenueT;
import com.isoftstone.jxyz.model.DmDeliveryMonthT;
import com.isoftstone.jxyz.model.DmEmpMonthCollectionT;
import com.isoftstone.jxyz.model.DmJxyzEmpInfoT;
import com.isoftstone.jxyz.model.DmJxyzSandSectionT;
import com.isoftstone.jxyz.model.DmJxyzSandTableT;
import com.isoftstone.jxyz.model.DmJxyzSectinInfoT;
import com.isoftstone.jxyz.model.DmRegionalMonthCollectionT;
import com.isoftstone.jxyz.model.DmSalesDepartmentCollectionMonthT;
import com.isoftstone.jxyz.model.DwrCustomerDailyRevenueT;
import com.isoftstone.jxyz.model.DwrDeliveryDetailT;
import com.isoftstone.jxyz.model.DwrEmpDailyCollectionT;
import com.isoftstone.jxyz.model.DwrJxyzCustomerD;
import com.isoftstone.jxyz.model.DwrJxyzCustomerRelationD;
import com.isoftstone.jxyz.model.DwrJxyzDepartmentD;
import com.isoftstone.jxyz.model.DwrJxyzEmpD;
import com.isoftstone.jxyz.model.DwrJxyzRegionD;
import com.isoftstone.jxyz.model.DwrJxyzResourcesD;
import com.isoftstone.jxyz.model.DwrRegionalDailyCollectionT;
import com.isoftstone.jxyz.model.DwrSalesDepartmentCollectionT;
import com.isoftstone.jxyz.model.TEmolumentResult;
import com.isoftstone.jxyz.model.TEmolumentRule;
import com.isoftstone.jxyz.model.TEmolumentTemplate;
import com.isoftstone.jxyz.model.TGridM;
import com.isoftstone.jxyz.model.TGridM0928;

@Service
public class JxyzDownloadService {
	@Autowired
	private AdoService adoService;
	
	public void dm_customer_month_revenue_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dm_delivery_month_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dm_emp_month_collection_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dm_jxyz_emp_info_t(String prefix, String qry, String suffix)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dm_jxyz_sand_section_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dm_jxyz_sand_table_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dm_jxyz_sectin_info_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dm_regional_month_collection_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dm_sales_department_collection_month_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dwr_customer_daily_revenue_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dwr_delivery_detail_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dwr_emp_daily_collection_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dwr_jxyz_customer_d(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dwr_jxyz_customer_relation_d(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dwr_jxyz_department_d(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dwr_jxyz_emp_d(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dwr_jxyz_region_d(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dwr_jxyz_resources_d(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dwr_regional_daily_collection_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void dwr_sales_department_collection_t(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void t_emolument_result(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void t_emolument_rule(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void t_emolument_template(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}

	public void t_grid_m(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}
	
	public void t_grid_m_0928(String prefix, String qry, String suffix) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		JSONArray dataJsa = adoService.qryDatas(qry).getJSONArray("data");
		DbContext ctx = DbContext.getGlobalDbContext();
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
	}
}
