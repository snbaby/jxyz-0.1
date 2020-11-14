package com.isoftstone.jxyz.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DB;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.jxyz.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class JxyzController {

    private static int a = 0;

    private String GET_YEAR_MONTH= Utils.getYearMonth();

    @PostMapping(value = "/http_request")
    @ResponseBody
    public int eg01(@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject.toJSONString().length());
        a+=1;
        return a;
    }

    @PostMapping(value = "receive/trajectory")
    @ResponseBody
    public ResponseEntity<JSONObject> trajectory(@RequestBody JSONObject jsb) {
        DbContext dbContext = DbContext.getGlobalDbContext();
        log.debug("轨迹数据：导入数据：{}", jsb.toString());
        log.info("轨迹数据：开始导入数据");

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

        JSONObject resJsb = new JSONObject();
        resJsb.put("code", 0);
        resJsb.put("msg", "success");
        resJsb.put("data", "");
        return new ResponseEntity<>(resJsb, HttpStatus.OK);
    }

}
