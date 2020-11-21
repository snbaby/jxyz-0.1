package com.isoftstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
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
	public JSONObject download(@PathVariable(value = "id") String id,String tableName,String condition)
			throws Exception {
		etlComponent.asyncParse(id,tableName,condition);
		JSONObject jsb = new JSONObject();
		jsb.put("success", id);
		return jsb;
	}
}
