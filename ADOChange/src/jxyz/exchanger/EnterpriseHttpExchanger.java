package jxyz.exchanger;

import jxyz.utils.HttpUtil;
import java.sql.Connection;

import com.alibaba.fastjson.JSONObject;

public class EnterpriseHttpExchanger implements Exchanger {
    @Override
    public void process(Connection connection) {
    	//这里改变地址===================================================
        String url = "http://127.0.0.1:18411/jxyz-etl/etl/download";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("qry", "select * from t_biz_enterprise_m;");
        jsonObject.put("prefix", "TRUNCATE t_biz_enterprise_m;");
        jsonObject.put("suffix", "");
        jsonObject.put("table", "t_biz_enterprise_m");
        String a = HttpUtil.doPost(url,jsonObject.toJSONString());
        System.out.println("收到结果过返回===========》" + a);
    }
    
}
