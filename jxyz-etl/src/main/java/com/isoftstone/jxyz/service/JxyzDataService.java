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

//    @Scheduled(cron = "1 2 22 * * ?")
//    private void configureTasks() {
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
//        List<String> list = new LinkedList<>();
//        list.add("dwr_jxyz_resources_d");
//        list.add("dwr_jxyz_emp_d");
//        list.add("dwr_jxyz_customer_d");
//        for (String s : list) {
//            getDataList(s, 1, 500000);
//        }
//    }

    private List<String> tableNameList() {
        List<String> tableNames = new ArrayList<>();
        tableNames.add("dwr_jxyz_resources_d");
        tableNames.add("dwr_jxyz_emp_d");
        tableNames.add("dwr_jxyz_customer_d");
        return tableNames;
    }

    public void getData(String[] tableNames, Integer pageNember, Integer totalPage) {
        List<String> tableNameList;
        if (null == tableNames || tableNames.length == 0) {
            tableNameList = tableNameList();
        } else {
            tableNameList = Arrays.asList(tableNames);
        }
        //将所有数据获取到
        Map<String, JSONArray> map = new HashMap<>();
        for (String tableName : tableNameList) {
            int pageNum = 1;
            int pageSize = 100;
            if (null != pageNember) {
                pageNum = pageNember;
            }
            if (null != totalPage) {
                pageSize = totalPage;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", userName);
            jsonObject.put("password", password);
            String token = PostHttpsUtil.post(token_url, jsonObject.toJSONString(), null);
            JSONObject result;
            JSONArray jsonArray = new JSONArray();
            while (true) {
                result = getResultData(token, tableName, pageNum++, pageSize);
                if (result.getJSONArray("data").size()>0){
                    jsonArray.addAll(result.getJSONArray("data"));
                }
                Long total = result.getLong("total");
                if (pageSize > total) {
                    break;
                }
            }
            map.put(tableName, jsonArray);
        }
        //处理数据
        DbContext dbContext = DbContext.getGlobalDbContext();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            JSONArray jsonArray = map.get(key);
            System.out.println(jsonArray);
            //三个表的不同处理
            String tSql = "TRUNCATE " + key;
            log.info("清空表{}==》", key);
            if (key.equals("dwr_jxyz_emp_d")) {
                dbContext.exe(tSql);
                jsonUtil.dwr_jxyz_emp_d(jsonArray, dbContext);
            }
            if (key.equals("dwr_jxyz_resources_d")) {
                //清空表
                dbContext.exe(tSql);
                jsonUtil.dwr_jxyz_resources_d(jsonArray, dbContext);
            }
            if (key.equals("dwr_jxyz_customer_d")) {
                dbContext.exe(tSql);
                jsonUtil.dwr_jxyz_customer_d(jsonArray, dbContext);
            }
            log.info(">>表:{}数据同步完成", key);
        }

    }

    private JSONObject getResultData(String token, String tableName, Integer pageNum, Integer pageSize) {
        String resultString = PostHttpsUtil.get(token, get_user_data_url + "?tableName=" + tableName + "&pageNum=" + pageNum + "&pageSize=" + pageSize);
        JSONObject result = JSONObject.parseObject(resultString);
        return result;
    }

    public void getDataList(String tableName, Integer pageNumber, Integer totalPage) {
        log.info(">>表:{}开始同步数据，当前第{}页,每页{}条", tableName, pageNumber, totalPage);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", userName);
        jsonObject.put("password", password);
        String token = PostHttpsUtil.post(token_url, jsonObject.toJSONString(), null);
        String resultString = PostHttpsUtil.get(token, get_user_data_url + "?tableName=" + tableName + "&pageNum=" + pageNumber + "&pageSize=" + totalPage);
        JSONObject result = JSONObject.parseObject(resultString);
        int num = result.getInteger("total");
        log.info("获取的数据条数{}==》", num);
        DbContext dbContext = DbContext.getGlobalDbContext();

        JSONArray dataArray = result.getJSONArray("data");
        String tSql = "TRUNCATE " + tableName;
        log.info("清空表{}==》", tableName);
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
