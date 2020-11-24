package com.isoftstone.jxyz.controller;


import com.isoftstone.jxyz.service.JxyzDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/etlSync")
@Slf4j
public class JxyzSyncDataController {

	@Autowired
	private JxyzDataService jxyzDataService;

	@GetMapping(value = "/getDataList")
    private void getDataList(String tableName, Integer pageNum,Integer totalPage) {
    	log.info(">>表:{}开始同步数据，当前第{}页,每页{}", tableName,pageNum,totalPage);
		jxyzDataService.getDataList(tableName,pageNum,totalPage);
    }



}
