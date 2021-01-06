package jxyz.exchanger;

import jxyz.utils.HttpUtil;
import java.sql.Connection;

import com.alibaba.fastjson.JSONObject;

public class UptownHttpExchanger implements Exchanger {
    @Override
    public void process(Connection connection) {
    	//这里改变地址===================================================
        String url = "http://127.0.0.1:18411/jxyz-etl/etl/download";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("qry", "select code,name,actual_householdcount,grid_code from t_uptown_m;");
        jsonObject.put("prefix", "TRUNCATE t_uptown_m;");
        jsonObject.put("suffix", "");
        jsonObject.put("table", "t_uptown_m");
        String a = HttpUtil.doPost(url,jsonObject.toJSONString());
        System.out.println("收到结果过返回===========》" + a);
    }
    
}
