package com.isoftstone.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.service.ExchangeService;

@RestController
@RequestMapping(value = "/sunCtrl")
public class JxyzSunController {

	@Autowired
	private ExchangeService exchangeService;

	@GetMapping(value = "/putDataToMQ")
    private void putDataToMQ() {
		DbContext ctx = DbContext.getGlobalDbContext();
		List<Map<String,Object>> mlist = ctx.qryMapList("select * from sdi_jxyz_pkp_waybill_base_20201130 limit 10");
		for(Map<String,Object> item : mlist) {
			JSONObject obj = new JSONObject(item);
			exchangeService.exchangeSend(JSONObject.parseObject(obj.toJSONString()));
		}
    }
}