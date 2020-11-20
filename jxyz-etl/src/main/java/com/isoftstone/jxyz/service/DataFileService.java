package com.isoftstone.jxyz.service;


import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.jxyz.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Component
@Slf4j
public class DataFileService {

    //库名
    @Value("${jxyz.table_schema}")
    private String table_schema;
    //每页最大条数
    @Value("${jxyz.data_total}")
    private Integer data_total;
    //生成文件位置
    @Value("${jxyz.data_base_path}")
    private String data_base_path;
    //上传地址
    @Value("${jxyz.upload_url}")
    private String upload_url;
    //获取token
    @Value("${jxyz.get_token.user_name}")
    private String userName;
    @Value("${jxyz.get_token.password}")
    private String password;
    @Value("${jxyz.get_token.token_url}")
    private String token_url;
    //fileCodeUrl转移地址
    @Value("${jxyz.file_code_url}")
    private String file_code_url;

    //每天凌晨3点2分1秒触发
    @Scheduled(cron = "1 2 3 * * ?")
    void getData() {
        configureTasks(null, null, null);
    }

    public void configureTasks(String tName, String startTime, String endTime) {
        List<String> tableNameList = DataBaseUtil.tableNameList();
        if (null != tName && !tName.equals("")) {
            tableNameList.clear();
            tableNameList.add(tName);
        }
        //天表列表
        ArrayList<String> dayList = new ArrayList<>();
        dayList.add("dwr_delivery_detail_t");
        dayList.add("dwr_emp_daily_collection_t");
        dayList.add("dwr_customer_daily_revenue_t");
        dayList.add("dwr_sales_department_collection_t");
        dayList.add("dwr_regional_daily_collection_t");
        //月表列表
        ArrayList<String> monthList = new ArrayList<>();
        monthList.add("dm_delivery_month_t");
        monthList.add("dm_emp_month_collection_t");
        monthList.add("dm_regional_month_collection_t");
        monthList.add("dm_customer_month_revenue_t");
        monthList.add("dm_sales_department_collection_month_t");

        DbContext dbContext = DbContext.getGlobalDbContext();

        for (String tableName : tableNameList) {
            String startDayTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            log.info(tableName + "表开始======》" + startDayTime);
            //查询总数
            String countSql = " select count(0) from " + tableName;
            //获取列名字
            String columnName = getColumnName(tableName, dbContext);
            String selectSql = "select " + columnName + "from " + tableName;
            String deleteSql = "DELETE FROM " + tableName;
            //一张表的总条数
            long count;
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
            String condition = "";
            if (dayList.contains(tableName)) {
                condition = yesterday;
                log.info("查询时间范围 condition =====" + condition);
                //当前日期的前一天的数据
                countSql = countSql + " where period_id >= '" + yesterday + "' and" + " period_id <= '" + endDay + "'";
                count = dbContext.qryLongValue(countSql);
                selectSql = selectSql + " where period_id >= '" + yesterday + "' and" + " period_id <= '" + endDay + "'";
                deleteSql = deleteSql + " where period_id >= '" + yesterday + "' and" + " period_id <= '" + endDay + "'";
            } else if (monthList.contains(tableName)) {
                condition = lastMonth;
                log.info("查询时间范围 condition =====" + condition);
                //当前日期的前一个月数据（判断条件为当前时间是1日 运行）
                countSql = countSql + " where period_id >= '" + lastMonth + "' and" + " period_id <= '" + endMonth + "'";
                count = dbContext.qryLongValue(countSql);
                selectSql = selectSql + " where period_id >= '" + lastMonth + "' and" + " period_id <= '" + endMonth + "'";
                deleteSql = deleteSql + " where period_id >= '" + lastMonth + "' and" + " period_id <= '" + endMonth + "'";
            } else {
                if (tableName.contains("sdi_jxyz_pkp_waybill_base_")) {
                    //获取前一天的日期
                    yesterday = LocalDateTime.now().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                    String year = yesterday.substring(0, 4);
                    if (!tableName.contains(year)) {
                        countSql = countSql + yesterday;
                        count = dbContext.qryLongValue(countSql);
                        selectSql = selectSql + yesterday;
                        tableName = tableName + yesterday.substring(0, 4);
                    } else {
                        count = dbContext.qryLongValue(countSql);
                    }
                    deleteSql = "";
                } else {
                    count = dbContext.qryLongValue(countSql);
                    deleteSql = "TRUNCATE " + tableName;
                }
            }
            String pageStartTime = null;
            String pageEndTime = null;
            Map<String, String> timeMap = new HashMap<>();
            if (tableName.contains("sdi_jxyz_pkp_waybill_base_")) {
                //每页最大条数为50万,此值不可修改，数据过大文件上传限制
                data_total = 500000;
                log.info("查询每页最大条数 total ======》" + data_total);
                //分页
                long totalPage = (count + data_total - 1) / data_total;
                for (int page = 1; page <= totalPage; page++) {
                    pageStartTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    log.info(tableName + "表，" + "第" + page + "页开始时间=====》" + pageStartTime);
                    String file = tableName + "_" + page;
                    try {
                        String studentResourcePath = new File(data_base_path, file + "\\" + file + ".sql").getAbsolutePath();
                        // 保证目录一定存在
                        ensureDirectory(studentResourcePath);
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(studentResourcePath)));
                        String pageSql = dbContext.pagin(page, data_total, selectSql);
                        //查询需要的数据
                        List<Map<String, Object>> dataList = dbContext.qryMapList(pageSql);
                        createInsertSql(writer, page, tableName, columnName, dataList, deleteSql);
                        writer.flush();
                        writer.close();
                        //生成压缩文件
                        File7zUtil.Compress7z(data_base_path + "\\" + file, data_base_path + "\\" + file + ".7z");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    timeMap = uploadAndDelFile(file);
                    pageEndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    log.info(tableName + "表，" + "第" + page + "页结束时间=====》" + pageEndTime);
                }
            } else {
                pageStartTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                log.info(tableName + "表，查询" + "开始时间=====》" + pageStartTime);
                try {
                    String studentResourcePath = new File(data_base_path, tableName + "\\" + tableName + ".sql").getAbsolutePath();
                    // 保证目录一定存在
                    ensureDirectory(studentResourcePath);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(studentResourcePath)));
                    //分页
                    long totalPage = (count + data_total - 1) / data_total;
                    for (int page = 1; page <= totalPage; page++) {
                        String pageSql = dbContext.pagin(page, data_total, selectSql);
                        //查询需要的tableName +"表"+数据
                        List<Map<String, Object>> dataList = dbContext.qryMapList(pageSql);
                        createInsertSql(writer, page, tableName, columnName, dataList, deleteSql);
                    }
                    writer.flush();
                    writer.close();
                    //生成压缩文件
                    File7zUtil.Compress7z(data_base_path + "\\" + tableName, data_base_path + "\\" + tableName + ".7z");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //删除文件夹
                timeMap = uploadAndDelFile(tableName);
                pageEndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                log.info(tableName + "表，查询" + "结束时间=====》" + pageEndTime);
            }
            String endDayTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            log.info(tableName + "表结束========》" + endDayTime);

            dbContext.exe(" INSERT INTO `t_etl_log`( `table_name`, `table_start_time`, `table_end_time`," +
                    " `data_total`,`condition`, `page_start_time`,`page_end_time`, `sql_file_start_time`, `sql_file_end_time`, `create_time`) " +
                    " VALUES ( '" + tableName + "','" + startDayTime + "','" + endDayTime + "','" +
                    count + "','"+ condition +"' , '"+ pageStartTime + "','" + pageEndTime + "','" + timeMap.get("fileUploadStartTime") + "','" +
                    timeMap.get("fileUploadStartTime") + "','" + (LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    + "')"));
            log.info(tableName + "表完成");
        }
    }

    private Map<String, String> uploadAndDelFile(String file) {
        //删除文件夹
        delAllFile(new File(data_base_path + "\\" + file));
        //获取token
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", userName);
        jsonObject.put("password", password);
        //获取token 传完一张表获取一次token避免token失效
        String fileUploadStartTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String token = PostHttpsUtil.post(token_url, jsonObject.toJSONString(), null);
        log.info("上传文件开始时间======》" + fileUploadStartTime);
        String reslut = PostHttpsUtil.uploadPost(data_base_path + "\\" + file + ".7z", upload_url, token);
        String fileUploadEndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("上传文件结束时间======》" + fileUploadEndTime);
        log.info("上传响应信息 ：" + reslut);
        jsonObject.clear();
        jsonObject = JSONObject.parseObject(reslut);
        String dataCode = jsonObject.get("data").toString();
        //将code传给王师傅
        PostHttpsUtil.get(file_code_url + dataCode);
        //将7z文件删除
        delAllFile(new File(data_base_path + "\\" + file + ".7z"));
        Map<String, String> map = new HashMap<>();
        map.put("fileUploadStartTime", fileUploadStartTime);
        map.put("fileUploadEndTime", fileUploadEndTime);
        return map;
    }

    /**
     * 删除文件或文件夹
     */
    public static void delAllFile(File directory) {
        if (!directory.isDirectory()) {
            directory.delete();
        } else {
            File[] files = directory.listFiles();
            // 空文件夹
            if (files.length == 0) {
                directory.delete();
                return;
            }
            // 删除子文件夹和子文件
            for (File file : files) {
                if (file.isDirectory()) {
                    delAllFile(file);
                } else {
                    file.delete();
                }
            }
            // 删除文件夹本身
            directory.delete();
        }
    }

    private void createInsertSql(BufferedWriter writer, Integer page, String tableName, String
            columnName, List<Map<String, Object>> dataList, String deletesSql) throws IOException {
        //再第一页加删除语句
        if (page == 1) {
            if (!deletesSql.equals("")) {
                writer.write(deletesSql + ";" + "\n");
            }
        }
        for (Map<String, Object> map : dataList) {
            //每一个map生一个insert语句
            List<Object> valList = new LinkedList<>(map.values());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < valList.size(); i++) {
                if (null == valList.get(i)) {
                    if (i == valList.size() - 1) {
                        stringBuilder.append(valList.get(i));
                    } else {
                        stringBuilder.append(valList.get(i) + ", ");
                    }
                } else {
                    if (i == valList.size() - 1) {
                        stringBuilder.append(" \"" + valList.get(i)).append("\" ");
                    } else {
                        stringBuilder.append(" \"" + valList.get(i)).append("\", ");
                    }
                }
            }
            String valString = stringBuilder.toString().replaceAll("\\r|\\n", "");
            String insertSql = " INSERT INTO `" + tableName + "` (" + columnName + ") VALUES (" + valString + ");";
            writer.write(insertSql + "\n");
        }
    }


    /**
     * 保证拷贝的文件的目录一定要存在
     */
    public static void ensureDirectory(String filePath) {
        if (null == filePath || filePath == "") {
            return;
        }
        filePath = replaceSeparator(filePath);
        if (filePath.indexOf("/") != -1) {
            filePath = filePath.substring(0, filePath.lastIndexOf("/"));
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    /**
     * 将符号“\\”和“\”替换成“/”,有时候便于统一的处理路径的分隔符,避免同一个路径出现两个或三种不同的分隔符
     */
    private static String replaceSeparator(String str) {
        return str.replace("\\", "/").replace("\\\\", "/");
    }

    private String getColumnName(String tableName, DbContext dbContext) {
        //获取列名
        StringBuilder cId = new StringBuilder();
        String getColumnNameSql = "select COLUMN_NAME from information_schema.columns where " +
                "table_name='" + tableName + "' and table_schema='" + table_schema + "';";
        List<String> cIds = dbContext.qryList(getColumnNameSql);
        //字段中主键id不要查出来
        for (int i = 0; i < cIds.size(); i++) {
            if (i == cIds.size() - 1) {
                if (!cIds.get(i).equals("id")) {
                    cId.append(" `");
                    cId.append(cIds.get(i));
                    cId.append("`");
                }
            } else {
                if (!cIds.get(i).equals("id")) {
                    cId.append(" `");
                    cId.append(cIds.get(i));
                    cId.append("`, ");
                }
            }
        }
        return cId.toString();
    }

}
