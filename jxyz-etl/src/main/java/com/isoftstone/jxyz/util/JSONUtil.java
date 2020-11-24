package com.isoftstone.jxyz.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jdbpro.SqlItem;
import com.github.drinkjava2.jsqlbox.DB;
import com.github.drinkjava2.jsqlbox.DbContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JSONUtil {

    public void dwr_jxyz_resources_d(JSONArray dwr_jxyz_resources_d, DbContext ctx) {
        for (int i = 0; i < dwr_jxyz_resources_d.size(); i++) {
            List<SqlItem> sqlItemList = new ArrayList<>();
            JSONObject jsb = dwr_jxyz_resources_d.getJSONObject(i);
            DataBaseUtil.consStr(sqlItemList, jsb, "grid_code");
            DataBaseUtil.consStr(sqlItemList, jsb, "key_market_name");
            DataBaseUtil.consStr(sqlItemList, jsb, "market_address");
            DataBaseUtil.consStr(sqlItemList, jsb, "resources_type");
            DataBaseUtil.consStr(sqlItemList, jsb, "province_code");
            DataBaseUtil.consStr(sqlItemList, jsb, "province_name");
            DataBaseUtil.consStr(sqlItemList, jsb, "city_code");
            DataBaseUtil.consStr(sqlItemList, jsb, "city_name");
            DataBaseUtil.consStr(sqlItemList, jsb, "county_code");
            DataBaseUtil.consStr(sqlItemList, jsb, "county_name");
            DataBaseUtil.consStr(sqlItemList, jsb, "dept_code");
            DataBaseUtil.consStr(sqlItemList, jsb, "dept_name");
            DataBaseUtil.consStr(sqlItemList, jsb, "section_code");
            DataBaseUtil.consStr(sqlItemList, jsb, "section_name");
            DataBaseUtil.consDecimal(sqlItemList, jsb, "location_longitude");
            DataBaseUtil.consDecimal(sqlItemList, jsb, "location_latitude");
            DataBaseUtil.consStr(sqlItemList, jsb, "bundary_coordinate");
            DataBaseUtil.consInteger(sqlItemList, jsb, "users_qty");
            DataBaseUtil.consDate(sqlItemList, jsb, "created_date");
            DataBaseUtil.consDate(sqlItemList, jsb, "create_date");
            DataBaseUtil.consStr(sqlItemList, jsb, "create_user");
            DataBaseUtil.consStr(sqlItemList, jsb, "created_by");
            DataBaseUtil.consStr(sqlItemList, jsb, "modify_user");
            DataBaseUtil.consStr(sqlItemList, jsb, "modify_date");
            sqlItemList.add(DB.notNull("key_market_code",jsb.getString("key_market_code")));
            if (sqlItemList.size() > 0) {
                ctx.exe("INSERT INTO dwr_jxyz_resources_d (", sqlItemList.toArray(), ")",
                        DB.valuesQuestions());
            }
        }
    }

    public void dwr_jxyz_emp_d(JSONArray dwr_jxyz_emp_d, DbContext ctx) {
        for (int i = 0; i < dwr_jxyz_emp_d.size(); i++) {
            List<SqlItem> sqlItemList = new ArrayList<>();
            JSONObject jsb = dwr_jxyz_emp_d.getJSONObject(i);
            DataBaseUtil.consStr(sqlItemList, jsb,"grid_code" );
            DataBaseUtil.consStr(sqlItemList, jsb,"emp_code" );
            DataBaseUtil.consStr(sqlItemList, jsb,"emp_name" );
            DataBaseUtil.consStr(sqlItemList, jsb,"emp_dept_code" );
            DataBaseUtil.consStr(sqlItemList, jsb,"emp_dept_name" );
            DataBaseUtil.consStr(sqlItemList, jsb,"emp_section_code" );
            DataBaseUtil.consStr(sqlItemList, jsb,"emp_section_name" );
            DataBaseUtil.consStr(sqlItemList, jsb,"emp_working_seniority" );
            DataBaseUtil.consStr(sqlItemList, jsb,"emp_post" );
            DataBaseUtil.consStr(sqlItemList, jsb,"emp_tel" );
            DataBaseUtil.consStr(sqlItemList, jsb,"emp_tel2" );
            DataBaseUtil.consStr(sqlItemList, jsb,"emp_training_times");
            DataBaseUtil.consStr(sqlItemList, jsb,"ado_id" );
            DataBaseUtil.consStr(sqlItemList, jsb,"hire_time");
            DataBaseUtil.consStr(sqlItemList, jsb,"leavedate");
            DataBaseUtil.consDecimal(sqlItemList, jsb, "location_longitude");
            DataBaseUtil.consDecimal(sqlItemList, jsb, "location_latitude");
            DataBaseUtil.consStr(sqlItemList, jsb,"bundary_coordinate");
            DataBaseUtil.consStr(sqlItemList, jsb,"remark");
            DataBaseUtil.consDate(sqlItemList, jsb, "created_date");
            DataBaseUtil.consStr(sqlItemList, jsb,"created_by");
            DataBaseUtil.consStr(sqlItemList, jsb,"extend_column");
            DataBaseUtil.consStr(sqlItemList, jsb,"create_user");
            DataBaseUtil.consDate(sqlItemList, jsb, "create_date");
            DataBaseUtil.consStr(sqlItemList, jsb,"modify_user");
            DataBaseUtil.consDate(sqlItemList, jsb, "modify_date");
            sqlItemList.add(DB.notNull("emp_status",jsb.getInteger("emp_status")));
            if (sqlItemList.size() > 0) {
                ctx.exe("INSERT INTO dwr_jxyz_emp_d (", sqlItemList.toArray(), ")",
                        DB.valuesQuestions());
            }
        }
    }

    public void dwr_jxyz_customer_d(JSONArray dwr_jxyz_customer_d, DbContext ctx) {
        for (int i = 0; i < dwr_jxyz_customer_d.size(); i++) {
            List<SqlItem> sqlItemList = new ArrayList<>();
            JSONObject jsb = dwr_jxyz_customer_d.getJSONObject(i);
            DataBaseUtil.consInteger(sqlItemList, jsb, "sender_id");
            DataBaseUtil.consStr(sqlItemList, jsb, "sender_no");
            DataBaseUtil.consStr(sqlItemList, jsb, "contract_name");
            DataBaseUtil.consDate(sqlItemList, jsb, "contract_expiration_time");
            DataBaseUtil.consInteger(sqlItemList, jsb, "sender_warehouse_id");
            DataBaseUtil.consStr(sqlItemList, jsb, "sender_warehouse_name");
            DataBaseUtil.consStr(sqlItemList, jsb, "sender_linker");
            DataBaseUtil.consStr(sqlItemList, jsb, "sender_fixtel");
            DataBaseUtil.consStr(sqlItemList, jsb, "sender_mobile");
            DataBaseUtil.consStr(sqlItemList, jsb, "sender_im_type");
            DataBaseUtil.consStr(sqlItemList, jsb, "sender_im_id");
            DataBaseUtil.consStr(sqlItemList, jsb, "post_org_no");
            DataBaseUtil.consStr(sqlItemList, jsb, "post_org_name");
            DataBaseUtil.consStr(sqlItemList, jsb, "sender_addr");
            DataBaseUtil.consStr(sqlItemList, jsb, "key_market_code");
            DataBaseUtil.consStr(sqlItemList, jsb, "key_market_name");
            DataBaseUtil.consStr(sqlItemList, jsb, "customer_maintainer_name");
            DataBaseUtil.consStr(sqlItemList, jsb, "customer_maintainer_no");
            DataBaseUtil.consStr(sqlItemList, jsb, "emp_position");
            DataBaseUtil.consStr(sqlItemList, jsb, "customer_email");
            DataBaseUtil.consStr(sqlItemList, jsb, "location_longitude");
            DataBaseUtil.consStr(sqlItemList, jsb, "location_latitude");
            DataBaseUtil.consStr(sqlItemList, jsb, "bundary_coordinate");
            DataBaseUtil.consDate(sqlItemList, jsb, "last_sender_date");
            DataBaseUtil.consStr(sqlItemList, jsb, "extend_column");
            DataBaseUtil.consDate(sqlItemList, jsb, "created_date");
            DataBaseUtil.consStr(sqlItemList, jsb, "created_by");
            DataBaseUtil.consStr(sqlItemList, jsb, "modify_user");
            DataBaseUtil.consStr(sqlItemList, jsb, "modify_date");
            sqlItemList.add(DB.notNull("sender",jsb.getString("sender")));
            if (sqlItemList.size() > 0) {
                ctx.exe("INSERT INTO dwr_jxyz_customer_d (", sqlItemList.toArray(), ")",
                        DB.valuesQuestions());
            }
        }
    }

}
