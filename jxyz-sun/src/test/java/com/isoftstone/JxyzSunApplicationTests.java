package com.isoftstone;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSONObject;
import com.isoftstone.service.ExchangeService;

@SpringBootTest
class JxyzSunApplicationTests {
	@Autowired
	private ExchangeService exchangeService;
	
	@Test
	void contextLoads() {
		List<String> strList = new ArrayList<>();
		strList.add("aa");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("asd", 123);
		
		JSONObject jsb = new JSONObject();
		jsb.put("date", new Date());
		jsb.put("int", 1);
		jsb.put("null", null);
		jsb.put("String", "2str");
		jsb.put("List", strList);
		jsb.put("map", map);
		
		exchangeService.exchangeSend(jsb);
	}

}
