package jxyz.exchanger;

import jxyz.utils.HttpUtil;
import java.sql.Connection;

import com.alibaba.fastjson.JSONObject;

public class CustomHttpExchanger implements Exchanger {
    @Override
    public void process(Connection connection) {
    	//这里改变地址===================================================
        String url = "http://127.0.0.1:18411/jxyz-etl/etl/download";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("qry", "select * from dwr_jxyz_customer_d;");
        jsonObject.put("prefix", "TRUNCATE dwr_jxyz_customer_d;");
        jsonObject.put("suffix", "");
        jsonObject.put("table", "dwr_jxyz_customer_d");
        HttpUtil.doPost(url,jsonObject.toJSONString());
        System.out.println("收到结果过返回===========》dwr_jxyz_customer_d");

    }
    
}
