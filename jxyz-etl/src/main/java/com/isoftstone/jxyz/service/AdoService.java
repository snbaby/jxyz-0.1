package com.isoftstone.jxyz.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.isoftstone.jxyz.config.RestTemplateConfig;

@Service
public class AdoService {
	// 获取token
	@Value("${jxyz.get_token.user_name}")
	private String userName;
	@Value("${jxyz.get_token.password}")
	private String password;
	@Value("${jxyz.get_token.token_url}")
	private String token_url;

	public String getToken() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		RestTemplate restTemplate = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
		JSONObject paramJsb = new JSONObject();
		paramJsb.put("userName", userName);
		paramJsb.put("password", password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(paramJsb.toJSONString(), headers);
		ResponseEntity<String> responseEntity  = restTemplate.postForEntity(token_url, requestEntity, String.class);
		return responseEntity.getBody();
	}
}
