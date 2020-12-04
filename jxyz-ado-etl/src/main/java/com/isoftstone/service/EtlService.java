package com.isoftstone.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.model.DmCustomerMonthRevenueT;
import com.isoftstone.model.DmDeliveryMonthT;
import com.isoftstone.model.DmEmpMonthCollectionT;
import com.isoftstone.model.DmJxyzEmpInfoT;
import com.isoftstone.model.DmJxyzSandSectionT;
import com.isoftstone.model.DmJxyzSandTableT;
import com.isoftstone.model.DmJxyzSectinInfoT;
import com.isoftstone.model.DmRegionalMonthCollectionT;
import com.isoftstone.model.DmSalesDepartmentCollectionMonthT;
import com.isoftstone.model.DwrCustomerDailyRevenueT;
import com.isoftstone.model.DwrDeliveryDetailT;
import com.isoftstone.model.DwrEmpDailyCollectionT;
import com.isoftstone.model.DwrJxyzCustomerD;
import com.isoftstone.model.DwrJxyzCustomerRelationD;
import com.isoftstone.model.DwrJxyzDepartmentD;
import com.isoftstone.model.DwrJxyzEmpD;
import com.isoftstone.model.DwrJxyzRegionD;
import com.isoftstone.model.DwrJxyzResourcesD;
import com.isoftstone.model.DwrRegionalDailyCollectionT;
import com.isoftstone.model.DwrSalesDepartmentCollectionT;
import com.isoftstone.model.TEmolumentResult;
import com.isoftstone.model.TEmolumentRule;
import com.isoftstone.model.TEmolumentTemplate;
import com.isoftstone.model.TGridM;

@Service
public class EtlService {

	public void dm_customer_month_revenue_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dm_delivery_month_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dm_emp_month_collection_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dm_jxyz_emp_info_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dm_jxyz_sand_section_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dm_jxyz_sand_table_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dm_jxyz_sectin_info_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dm_regional_month_collection_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dm_sales_department_collection_month_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dwr_customer_daily_revenue_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dwr_delivery_detail_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dwr_emp_daily_collection_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dwr_jxyz_customer_d(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dwr_jxyz_customer_relation_d(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dwr_jxyz_department_d(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dwr_jxyz_emp_d(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dwr_jxyz_region_d(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dwr_jxyz_resources_d(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dwr_regional_daily_collection_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void dwr_sales_department_collection_t(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void t_emolument_result(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void t_emolument_rule(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void t_emolument_template(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}

	public void t_grid_m(String pre, JSONArray dataJsa, String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if (StringUtils.isNotBlank(pre)) {
			ctx.exe(pre);
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
		if (StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);
		}
	}
}
