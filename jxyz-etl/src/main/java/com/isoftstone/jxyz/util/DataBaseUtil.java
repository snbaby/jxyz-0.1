package com.isoftstone.jxyz.util;

import java.util.*;

public class DataBaseUtil {
    private final static String dm_jxyz_emp_info_t = "dm_jxyz_emp_info_t";
    private final static String dm_jxyz_sectin_info_t = "dm_jxyz_sectin_info_t";
    private final static String dwr_jxyz_customer_d = "dwr_jxyz_customer_d";
    private final static String dwr_jxyz_department_d = "dwr_jxyz_department_d";
    private final static String dwr_jxyz_emp_d = "dwr_jxyz_emp_d";
    private final static String dwr_jxyz_resources_d = "dwr_jxyz_resources_d";
    private final static String dwr_jxyz_customer_relation_d = "dwr_jxyz_customer_relation_d";
    private final static String dm_customer_month_revenue_t = "dm_customer_month_revenue_t";
    private final static String dm_delivery_month_t = "dm_delivery_month_t";
    private final static String dm_emp_month_collection_t = "dm_emp_month_collection_t";
    private final static String dm_regional_month_collection_t = "dm_regional_month_collection_t";
    private final static String dm_sales_department_collection_month_t = "dm_sales_department_collection_month_t";
    private final static String dwr_customer_daily_revenue_t = "dwr_customer_daily_revenue_t";
    private final static String dwr_delivery_detail_t = "dwr_delivery_detail_t";
    private final static String dwr_emp_daily_collection_t = "dwr_emp_daily_collection_t";
    private final static String dwr_regional_daily_collection_t = "dwr_regional_daily_collection_t";
    private final static String dwr_sales_department_collection_t = "dwr_sales_department_collection_t";
    private final static String dwr_jxyz_region_d = "dwr_jxyz_region_d";
    private final static String dm_jxyz_sand_table_t = "dm_jxyz_sand_table_t";
    private final static String dm_jxyz_sand_section_t = "dm_jxyz_sand_section_t";
    private final static String sdi_jxyz_pkp_waybill_base_ = "sdi_jxyz_pkp_waybill_base_";

    private DataBaseUtil() {
        super();
    }

    public static HashMap<String, String> tableNameMap() {
        LinkedHashMap<String, String> tableNameMap = new LinkedHashMap<>(20);
        tableNameMap.put("dm_jxyz_emp_info_t", dm_jxyz_emp_info_t);
        tableNameMap.put("dm_jxyz_sand_section_t", dm_jxyz_sand_section_t);
        tableNameMap.put("dm_jxyz_sand_table_t", dm_jxyz_sand_table_t);
        tableNameMap.put("dm_jxyz_sectin_info_t", dm_jxyz_sectin_info_t);
        tableNameMap.put("dwr_jxyz_customer_d", dwr_jxyz_customer_d);
        tableNameMap.put("dm_customer_month_revenue_t", dm_customer_month_revenue_t);
        tableNameMap.put("dm_delivery_month_t", dm_delivery_month_t);
        tableNameMap.put("dm_emp_month_collection_t", dm_emp_month_collection_t);
        tableNameMap.put("dm_regional_month_collection_t", dm_regional_month_collection_t);
        tableNameMap.put("dm_sales_department_collection_month_t", dm_sales_department_collection_month_t);
        tableNameMap.put("dwr_customer_daily_revenue_t", dwr_customer_daily_revenue_t);
        tableNameMap.put("dwr_delivery_detail_t", dwr_delivery_detail_t);
        tableNameMap.put("dwr_emp_daily_collection_t", dwr_emp_daily_collection_t);
        tableNameMap.put("dwr_jxyz_customer_relation_d", dwr_jxyz_customer_relation_d);
        tableNameMap.put("dwr_jxyz_department_d", dwr_jxyz_department_d);
        tableNameMap.put("dwr_jxyz_emp_d", dwr_jxyz_emp_d);
        tableNameMap.put("dwr_jxyz_region_d", dwr_jxyz_region_d);
        tableNameMap.put("dwr_jxyz_resources_d", dwr_jxyz_resources_d);
        tableNameMap.put("dwr_regional_daily_collection_t", dwr_regional_daily_collection_t);
        tableNameMap.put("dwr_sales_department_collection_t", dwr_sales_department_collection_t);
        tableNameMap.put("sdi_jxyz_pkp_waybill_base_", sdi_jxyz_pkp_waybill_base_);
        return tableNameMap;
    }

    public static ArrayList<String> tableNameList() {
        return new ArrayList<>(tableNameMap().values());
    }



}
