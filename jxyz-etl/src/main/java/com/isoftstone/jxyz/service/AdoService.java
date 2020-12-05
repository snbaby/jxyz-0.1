package com.isoftstone.jxyz.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.jxyz.config.RestTemplateConfig;

@Service
public class AdoService {
	// 获取token
	@Value("${jxyz.ado.user_name}")
	private String userName;
	@Value("${jxyz.ado.password}")
	private String password;
	@Value("${jxyz.ado.token_url}")
	private String token_url;

	@Value("${jxyz.ado.qry_url}")
	private String qry_url;

	@Value("${jxyz.ado.ins_url}")
	private String ins_url;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public String getToken() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		RestTemplate restTemplate = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
		JSONObject paramJsb = new JSONObject();
		paramJsb.put("userName", userName);
		paramJsb.put("password", password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(paramJsb.toJSONString(), headers);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(token_url, requestEntity, String.class);
		return responseEntity.getBody();
	}

	public JSONObject qryDatas(String qry, String id)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject paramJsb = new JSONObject();
		paramJsb.put("qry", qry);
		paramJsb.put("id", id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("gc-authentication", getToken());

		HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(paramJsb, headers);
		ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(qry_url, requestEntity,
				JSONObject.class);
		return responseEntity.getBody();
	}

	@Async
	public void insDatas(String ins, String prefix, String suffix, String table)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("gc-authentication", getToken());

		DbContext ctx = DbContext.getGlobalDbContext();
		String id = UUID.randomUUID().toString();
		ctx.execute(
				"INSERT INTO t_etl_upload_log(id,ins,prefix,suffix,`table`,qry_start_time,create_time)VALUES(?,?,?,?,?,?,?)",
				id, ins, prefix, suffix, table, sdf.format(new Date()), sdf.format(new Date()));

		List<Map<String, Object>> data = ctx.qryMapList(ins);
		ctx.execute("update t_etl_upload_log set qry_end_time = ?, trans_start_time = ?, total = ? where id = ?",
				sdf.format(new Date()), sdf.format(new Date()), data.size(), id);
		JSONObject paramJsb = new JSONObject();
		paramJsb.put("data", data);
		paramJsb.put("prefix", prefix);
		paramJsb.put("suffix", suffix);
		paramJsb.put("table", table);
		paramJsb.put("id", id);

		HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(paramJsb, headers);
		restTemplate.postForEntity(ins_url, requestEntity, JSONObject.class);

		ctx.execute("update t_etl_upload_log set trans_end_time = ? where id = ?", sdf.format(new Date()), id);
	}
}
