package com.isoftstone.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

@Service
public class EtlService {
	@Value("${jxyz.etl.download_url}")
	private String download_url;

	@Value("${jxyz.etl.upload_url}")
	private String upload_url;

	public void downloadDwrJxyzEmpD() {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject paramJsb = new JSONObject();
		paramJsb.put("qry", "select * from dwr_jxyz_emp_d");
		paramJsb.put("table", "dwr_jxyz_emp_d");
		paramJsb.put("prefix", "truncate table dwr_jxyz_emp_d");
		paramJsb.put("suffix", "");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(paramJsb.toJSONString(), headers);
		restTemplate.postForEntity(download_url, requestEntity, Object.class);
	}
	
	public void downloadTGridM() {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject paramJsb = new JSONObject();
		paramJsb.put("qry", "select * from t_grid_m");
		paramJsb.put("table", "t_grid_m");
		paramJsb.put("prefix", "truncate table t_grid_m");
		paramJsb.put("suffix", "");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(paramJsb.toJSONString(), headers);
		restTemplate.postForEntity(download_url, requestEntity, Object.class);
	}
	
	public void downloadTEmolumentTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject paramJsb = new JSONObject();
		paramJsb.put("qry", "select * from t_emolument_template");
		paramJsb.put("table", "t_emolument_template");
		paramJsb.put("prefix", "truncate table t_emolument_template");
		paramJsb.put("suffix", "");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(paramJsb.toJSONString(), headers);
		restTemplate.postForEntity(download_url, requestEntity, Object.class);
	}
	
	public void downloadTEmolumentRule() {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject paramJsb = new JSONObject();
		paramJsb.put("qry", "select * from t_emolument_rule");
		paramJsb.put("table", "t_emolument_rule");
		paramJsb.put("prefix", "truncate table t_emolument_rule");
		paramJsb.put("suffix", "");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(paramJsb.toJSONString(), headers);
		restTemplate.postForEntity(download_url, requestEntity, Object.class);
	}
	
	public void uploadTEmolumentResult() {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject paramJsb = new JSONObject();
		paramJsb.put("ins", "select * from t_emolument_result");
		paramJsb.put("table", "t_emolument_result");
		paramJsb.put("prefix", "truncate table t_emolument_result");
		paramJsb.put("suffix", "");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(paramJsb.toJSONString(), headers);
		restTemplate.postForEntity(upload_url, requestEntity, Object.class);
	}
}
