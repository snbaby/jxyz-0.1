package com.isoftstone.jxyz.controller;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/etlSync")
@Slf4j
@EnableScheduling
public class JxyzSyncDataController {

    @Scheduled(cron = "0 */30 * * * ?")
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        getDataList("dwr_jxyz_resources_d",1);
    }
    
    
    private void getDataList(String tableName, int pageNum) {
    	log.info(">>表:{}开始同步数据，当前第{}页", tableName,pageNum);
    	RestTemplate resetTemplate = new RestTemplate();
		String url = "http://localhost:45678/jxyzadoetl/etl/syncDwrResources?tableName="+tableName+"&pageNum="+pageNum+"&pageSize=1000";
		JSONObject result = resetTemplate.getForObject(url, JSONObject.class);
		System.out.println(result);
		int num = result.getInteger("total");
		if(num == 1000) {
			getDataList(tableName,pageNum++);
		} else {
			log.info(">>表:{}数据同步完成", tableName);
		}
    }

}
