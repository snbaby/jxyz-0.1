package com.isoftstone.jxyz.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DB;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.jxyz.service.PageTransferService;
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
    private PageTransferService pageTransferService;

    @Autowired
    public JxyzController(PageTransferService pageTransferService) {
        this.pageTransferService = pageTransferService;
    }

    private static JSONObject resultJson() {
        JSONObject resJsb = new JSONObject();
        resJsb.put("code", 0);
        resJsb.put("msg", "success");
        resJsb.put("data", "");
        return resJsb;
    }

    /**
     * 传输数据接口（注意，传输月份格式为:"202001"; 日期格式为："2020-02-28"）
     * @param tableName
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping(value = "/receive/get_date")
    public ResponseEntity<JSONObject> getData(String tableName, String startTime, String endTime) {
        pageTransferService.configureTasks(tableName,startTime,endTime);
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
