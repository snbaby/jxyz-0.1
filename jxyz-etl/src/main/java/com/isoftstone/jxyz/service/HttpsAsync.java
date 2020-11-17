package com.isoftstone.jxyz.service;

import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.jxyz.util.PostHttpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HttpsAsync {

    @Value("${jxyz.data_total}")
    private Integer data_total;
    @Value("${jxyz.get_token.user_name}")
    private String userName;
    @Value("${jxyz.get_token.password}")
    private String password;
    @Value("${jxyz.get_token.token_url}")
    private String token_url;
    @Value("${jxyz.url}")
    private String jxyz_url;


    @Async
    void pageTransfer(Long count, String sql, String tableName, String periodId) {
        DbContext dbContext = DbContext.getGlobalDbContext();
        int j = 0;
        JSONObject jsonObject = new JSONObject();
        JSONObject getToken = new JSONObject();
        getToken.put("userName", userName);
        getToken.put("password", password);
        //获取token 传完一张表获取一次token避免token失效
        String token = PostHttpsUtil.post(token_url, getToken.toJSONString(),null);
        log.info(token);
        //分页传输
        List<Map<String, Object>> resultList;
        long totalPage = (count + data_total - 1) / data_total;
        for (int i = 1; i <= totalPage; i++) {
            String a = dbContext.pagin(i, data_total, sql);
            resultList = dbContext.qryMapList(a);
            //传输
            jsonObject.put("tableName", tableName);
            jsonObject.put("period", periodId);
            jsonObject.put("datas", resultList);
            if (j == 0) {
                jsonObject.put("del", "1");
            } else {
                jsonObject.put("del", "0");
            }
            //转移数据
            String result = PostHttpsUtil.post(jxyz_url, jsonObject.toJSONString(),null);
            log.info(result + "");
            j += 1;
            log.info(j + "");
        }
    }
}
