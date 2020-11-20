package com.isoftstone.jxyz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.isoftstone.jxyz.service.DataFileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/etl")
@Slf4j
public class JxyzController {
    @Autowired
    private DataFileService dataFileService;

    /**
     * 传输数据接口（注意，传输月份格式为:"202001"; 日期格式为："2020-02-28"）
     * @param tableName
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping(value = "/receive/get_date")
    public ResponseEntity<JSONObject> getData(String tableName, String startTime, String endTime) {
    	log.info("tableName--{},startTime--{},endTime--{}",tableName,startTime,endTime);
        dataFileService.configureTasks(tableName,startTime,endTime);
        JSONObject resJsb = new JSONObject();
        resJsb.put("code", 0);
        resJsb.put("msg", "success");
        resJsb.put("data", "");
        return new ResponseEntity<>(resJsb, HttpStatus.OK);
    }

}
