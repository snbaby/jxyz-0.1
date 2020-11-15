package com.isoftstone.jxyz.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DB;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.jxyz.service.PageTransferService;
import com.isoftstone.jxyz.util.DataBaseUtil;
import com.isoftstone.jxyz.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class JxyzController {

    @Autowired
    private PageTransferService pageTransferService;

    private static JSONObject resultJson() {
        JSONObject resJsb = new JSONObject();
        resJsb.put("code", 0);
        resJsb.put("msg", "success");
        resJsb.put("data", "");
        return resJsb;
    }

    @GetMapping(value = "/receive/getDate")
    public ResponseEntity<JSONObject> getData(String tableName, String periodId, String endTime) {
        System.out.println("tabl:" + tableName + " perid:" + periodId + " endTime:" + endTime);
        //获取表名
//        if (null == jsonObject.get("tableName"))
        if (null == tableName) {
            throw new RuntimeException();
        }
//        String tableName = jsonObject.get("tableName").toString();
//        log.info("接受数据库表名字", jsonObject.get("tableName"));
        log.info("接受数据库表名字", tableName);
        //获取表字段
        String fieId = DataBaseUtil.tableNameUtil(tableName);
        //创建dbcontext
        DbContext dbContext = DbContext.getGlobalDbContext();
        Long count;
        String sql;
//        String periodId = null;
        //总条数
//        if (null != jsonObject.get("periodId")) {
        if (null != periodId) {
//            log.info("接受过滤时间", jsonObject.get("periodId"));
            log.info("接受过滤时间", periodId);
//            periodId = jsonObject.get("periodId").toString();
            sql = "select count(0) from " + tableName + " where period_id >= " + periodId;
            if (null != endTime) {
                sql = sql + " and period_id <= " + endTime;
            }
        } else {
            sql = "select count(1) from " + tableName;
        }
        count = dbContext.qryLongValue(sql);
        if (null != periodId) {
//            log.info("接受过滤时间", jsonObject.get("periodId"));
            log.info("接受过滤时间", periodId);
            sql = "select " + fieId + " from " + tableName + " where period_id >= " + periodId;
            if (null != endTime) {
                sql = sql + " and period_id <= " + endTime;
            }
        } else {
            sql = "select " + fieId + " from " + tableName;
        }
        pageTransferService.pageTransfer(count, dbContext, sql);
        return new ResponseEntity<>(resultJson(), HttpStatus.OK);
    }


    @PostMapping(value = "receive/trajectory")
    @ResponseBody
    public ResponseEntity<JSONObject> trajectory(@RequestBody JSONObject jsb) {
        DbContext dbContext = DbContext.getGlobalDbContext();
        log.debug("轨迹数据：导入数据：{}", jsb.toString());
        log.info("轨迹数据：开始导入数据");
        String GET_YEAR_MONTH = Utils.getYearMonth();
        dbContext.exe("INSERT INTO sdi_jxyz_pkp_trace_message_" + GET_YEAR_MONTH + "(",
                DB.notNull("trace_no,", jsb.getString("traceNo")),
                DB.notNull("op_time,", Utils.translateDateStr(jsb.get("opTime"))),
                DB.notNull("op_code,", jsb.getString("opCode")),
                DB.notNull("op_name,", jsb.getString("opName")),
                DB.notNull("op_desc,", jsb.getString("opDesc")),
                DB.notNull("op_org_prov_name,", jsb.getString("opOrgProvName")),
                DB.notNull("op_org_city,", jsb.getString("oporgcity")),
                DB.notNull("op_org_code,", jsb.getString("oporgcode")),
                DB.notNull("op_org_name,", jsb.getString("oporgname")),
                DB.notNull("op_erator_no,", jsb.getString("operatorno")),
                DB.notNull("op_erator_name,", jsb.getString("operatorname")),
                DB.notNull("created_date,", Utils.df().format(new Date())), DB.notNull("created_by", "王小贱"), ")",
                DB.valuesQuestions());

        log.info("轨迹数据：结束导入数据");
        return new ResponseEntity<>(resultJson(), HttpStatus.OK);
    }


}
