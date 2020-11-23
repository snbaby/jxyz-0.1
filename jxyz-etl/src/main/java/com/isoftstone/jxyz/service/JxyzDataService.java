package com.isoftstone.jxyz.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DB;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.jxyz.util.JSONUtil;
import com.isoftstone.jxyz.util.PostHttpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class JxyzDataService {

    //获取token
    @Value("${jxyz.get_token.user_name}")
    private String userName;
    @Value("${jxyz.get_token.password}")
    private String password;
    @Value("${jxyz.get_token.token_url}")
    private String token_url;
    @Value("${jxyz.get_user_data_url}")
    private String get_user_data_url;

    @Autowired
    private JSONUtil jsonUtil;

    @Scheduled(cron = "1 2 22 * * ?")
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        List<String> list = new LinkedList<>();
        list.add("dwr_jxyz_resources_d");
        list.add("dwr_jxyz_emp_d");
        list.add("dwr_jxyz_customer_d");
        for (String s : list) {
            getDataList(s, 1, 500000);
        }
    }

    public void getDataList(String tableName, Integer pageNumber, Integer totalPage) {
        log.info(">>表:{}开始同步数据，当前第{}页,每页{}条", tableName, pageNumber, totalPage);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", userName);
        jsonObject.put("password", password);
        String token = PostHttpsUtil.post(token_url, jsonObject.toJSONString(), null);
        String resultString = PostHttpsUtil.get(token,get_user_data_url + "?tableName=" + tableName + "&pageNum=" + pageNumber + "&pageSize=" + totalPage);
        JSONObject result = JSONObject.parseObject(resultString);
        int num = result.getInteger("total");
        log.info("获取的数据条数{}==》", num);
        DbContext dbContext = DbContext.getGlobalDbContext();

        JSONArray dataArray = result.getJSONArray("data");
        String tSql = "TRUNCATE " + tableName;
        if (tableName.equals("dwr_jxyz_resources_d")) {
            //清空表
            dbContext.exe(tSql);
            jsonUtil.dwr_jxyz_resources_d(dataArray, dbContext);
        }
        if (tableName.equals("dwr_jxyz_emp_d")) {
            dbContext.exe(tSql);
            jsonUtil.dwr_jxyz_emp_d(dataArray, dbContext);
        }
        if (tableName.equals("dwr_jxyz_customer_d")) {
            dbContext.exe(tSql);
            jsonUtil.dwr_jxyz_customer_d(dataArray, dbContext);
        }

        if (num == totalPage) {
            int pageNum = pageNumber + 1;
            getDataList(tableName, pageNum, totalPage);
        } else {
            log.info(">>表:{}数据同步完成", tableName);
        }
    }

}
