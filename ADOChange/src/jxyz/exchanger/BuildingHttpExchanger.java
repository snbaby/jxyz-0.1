package jxyz.exchanger;

import jxyz.utils.HttpUtil;
import java.sql.Connection;

import com.alibaba.fastjson.JSONObject;

public class BuildingHttpExchanger implements Exchanger {
    @Override
    public void process(Connection connection) {
    	//这里改变地址===================================================
        String url = "http://127.0.0.1:18411/jxyz-etl/etl/download";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("qry", "select * from t_biz_building_m;");
        jsonObject.put("prefix", "TRUNCATE t_biz_building_m;");
        jsonObject.put("suffix", "");
        jsonObject.put("table", "t_biz_building_m");
        String a = HttpUtil.doPost(url,jsonObject.toJSONString());
        System.out.println("收到结果过返回===========》" + a);
        
        
        jsonObject.put("qry", "select id,code, parent_code, all_parent_code, old_grid_code, name,level,grid_status,create_user, create_date, modify_user, modify_date, extend_column from t_grid_m;");
        jsonObject.put("prefix", "TRUNCATE t_grid_m;");
        jsonObject.put("suffix", "");
        jsonObject.put("table", "t_grid_m");
        HttpUtil.doPost(url,jsonObject.toJSONString());
        System.out.println("收到结果过返回===========》t_grid_m");
    }
    
}
