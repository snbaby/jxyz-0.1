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

import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.isoftstone.component.EtlComponent;

/**
 * @ClassName EtlController
 * @Deacription TODO
 * @Author wangjian
 * @Date 2020/11/18 13:58
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "etl")
public class EtlController {

	@Autowired
	public EtlComponent etlComponent;

	@Value("${jxyzadoetl.etl.download}")
	private String downloadBasePath;

	@Value("${jxyzadoetl.etl.unzip}")
	private String unzipBasePath;

	@Value("${jxyzadoetl.etl.url}")
	private String downloadBaseUrl;

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
	public JSONObject async(@RequestBody JSONObject jsb) throws Exception {
		DbContext ctx = DbContext.getGlobalDbContext();
		String qry = jsb.getString("qry");
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotBlank(qry)) {
			maps = ctx.qryMapList(qry);
		}
		jsb.put("data", maps);
		return jsb;
	}
}
