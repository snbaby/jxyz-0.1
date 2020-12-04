package com.isoftstone.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.model.DwrJxyzEmpD;
import com.isoftstone.model.TGridM;

@Service
public class EtlService {

	public void t_grid_m(String pre, JSONArray dataJsa,String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		ctx.exe(pre);
		for (int i = 0; i < dataJsa.size(); i++) {
			TGridM tGridM = JSONObject.toJavaObject(dataJsa.getJSONObject(i), TGridM.class);
			tGridM.insert();
		}

	}

	public void dwr_jxyz_emp_d(String pre, JSONArray dataJsa,String sufix) {
		DbContext ctx = DbContext.getGlobalDbContext();
		if(StringUtils.isNotBlank(pre)) {
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
		if(StringUtils.isNotBlank(sufix)) {
			ctx.exe(sufix);	
		}
	}
}
