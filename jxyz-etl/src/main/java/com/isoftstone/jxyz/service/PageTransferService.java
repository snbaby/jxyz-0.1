package com.isoftstone.jxyz.service;

import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.jxyz.util.DataBaseUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Component
@Slf4j
public class PageTransferService {
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

    @Autowired
    private HttpsAsync httpsAsync;

    @Scheduled(cron = "1 2 3 * * ?") //每天凌晨3点2分1秒触发
    public void getData() {
        configureTasks(null, null, null);
    }

    public void configureTasks(String tableName, String startTime, String endTime) {
        //获取表名
        ArrayList<String> tableNames = DataBaseUtil.tableNameList();
        if (null != tableName && !tableName.equals("")) {
            tableNames.clear();
            tableNames.add(tableName);
        }
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
        //sql语句
        String countSql;
        String selectSql;
        //一张表的总条数
        long count;
        //字段列名
        String fieId;
        //获取前一天的日期
        String yesterday = LocalDateTime.now().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDay = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //获取上一个月的月份
        String lastMonth = LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).
                format(DateTimeFormatter.ofPattern("yyyyMM"));
        String endMonth = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).
                format(DateTimeFormatter.ofPattern("yyyyMM"));
        if (null != startTime && !startTime.equals("")) {
            yesterday = startTime;
            lastMonth = startTime;
        }
        if (null != endTime && !endTime.equals("")) {
            endDay = endTime;
            endMonth = endTime;
        }

        for (String tName : tableNames) {
            log.info(tableName + "=====开始======");
            //获取表字段
            fieId = DataBaseUtil.tableNameUtil(tName);
            countSql = " select count(0) from " + tName;
            selectSql = " select " + fieId + " from " + tName;
            String periodId = null;
            if (dayList.contains(tName)) {
                //当前日期的前一天的数据
                periodId = yesterday;
                countSql = countSql + " where period_id >= '" + yesterday + "' and" + " period_id <= '" + endDay + "'";
                count = dbContext.qryLongValue(countSql);
                selectSql = selectSql + " where period_id >= '" + yesterday + "' and" + " period_id <= '" + endDay + "'";
            } else if (monthList.contains(tName)) {
                //当前日期的前一个月数据（判断条件为当前时间是1日 运行）
                countSql = countSql + " where period_id >= '" + lastMonth + "' and" + " period_id <= '" + endMonth + "'";
                count = dbContext.qryLongValue(countSql);
                selectSql = selectSql + " where period_id >= '" + lastMonth + "' and" + " period_id <= '" + endMonth + "'";
                periodId = lastMonth;
            } else {
                if (tName.equals("sdi_jxyz_pkp_waybill_base_")) {
                    //获取前一天的日期
                    yesterday = LocalDateTime.now().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                    countSql = countSql + yesterday;
                    count = dbContext.qryLongValue(countSql);
                    selectSql = selectSql + yesterday;
                    tName = tName + yesterday.substring(0, 4);
                    periodId = yesterday;
                } else {
                    count = dbContext.qryLongValue(countSql);
                }
            }

            httpsAsync.pageTransfer(count, selectSql, tName, periodId);
        }
    }

}
