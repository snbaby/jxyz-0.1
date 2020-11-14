package com.isoftstone.jxyz.service;

import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.jxyz.util.DataBaseUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.nodes.CollectionNode;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Slf4j
@EnableScheduling
public class PageTransferService {
    @Value("${jxyz.data_total}")
    private Integer data_total;
    @Autowired
    TransactionService transactionService;

    public void pageTransfer(Long count, DbContext dbContext, String sql) {
        JSONObject js = new JSONObject();
        //分页传输
        long totalPage = (count + data_total - 1) / data_total;
        for (int i = 1; i <= totalPage; i++) {
            List<Map<String, Object>> result;
            String a = dbContext.pagin(i, data_total, sql);
            result = dbContext.qryMapList(a);
            //传输
            js.put("result", result);
            //转移数据
            int code = transactionService.httpRequest(js);
            log.info(code + "");
        }
    }

    //3.添加定时任务
    //指定时间间隔，例如：5秒
    @Scheduled(cron = "0/5 * * * * ?")
    //@Scheduled(fixedRate=5000)
    public void configureTasks() {
        //获取当前时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowFormatter = now.format(dateTimeFormatter);
        //获取日
        int day = now.getDayOfMonth();
        //获取月
        int month = now.getMonth().getValue();
        //获取年
        int year = now.getYear();

        //当前日期的前一天日期
        String yesterday = LocalDateTime.now().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        //取值
        ArrayList<String> tableNames = DataBaseUtil.tableNameList();

        //天表列表
        ArrayList<String> dayList = new ArrayList<>();
        dayList.add("dwr_customer_daily_revenue_t");
        dayList.add("dwr_delivery_detail_t");
        dayList.add("dwr_emp_daily_collection_t");
        dayList.add("dwr_regional_daily_collection_t");
        dayList.add("dwr_sales_department_collection_t");
        //月表列表
        ArrayList<String> monthList = new ArrayList<>();
        monthList.add("dm_customer_month_revenue_t");
        monthList.add("dm_delivery_month_t");
        monthList.add("dm_emp_month_collection_t");
        monthList.add("dm_regional_month_collection_t");
        monthList.add("dm_sales_department_collection_month_t");

        //创建dbcontext
        DbContext dbContext = DbContext.getGlobalDbContext();

        String sql;
        Long count;
        String fieId;
        for (String tableName : tableNames) {

            if (dayList.contains(tableName)) {

                //当前日期的前一天的数据
                sql = "select count(0) from " + tableName + " where period_id = " + yesterday;
                count = dbContext.qryLongValue(sql);
                //获取表字段
                fieId = DataBaseUtil.tableNameUtil(tableName);
                sql = "select " + fieId + " from " + tableName + " where period_id = " + yesterday;

            } else if (monthList.contains(tableName)) {
                //当前日期的前一个月数据（判断条件为当前时间是1日 运行）

            } else {
                //再传全量
            }


        }
    }

    public static void main(String[] args) {

    }


}
