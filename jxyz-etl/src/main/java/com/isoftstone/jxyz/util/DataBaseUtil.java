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
    private final static String sdi_jxyz_pkp_waybill_base_ = "sdi_jxyz_pkp_waybill_base_";
    private final static String dwr_jxyz_region_d = "dwr_jxyz_region_d";
    private final static String dm_jxyz_sand_table_t = "dm_jxyz_sand_table_t";
    private final static String dm_jxyz_sand_section_t = "dm_jxyz_sand_section_t";

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

    public static String tableNameUtil(String tableName) {
        switch (tableName) {
            case dwr_jxyz_region_d:
                return "`id`, `region_code`, `region_name`, `region_level`, `procince_name`, `province_code`, `city_name`, `city_code`, `county_name`, `county_code`, `created_date`, `created_by`, `modify_time`, `modify_by`";
            case dm_jxyz_sand_section_t:
                return "`id`, `grid_code`, `section_name`, `qty`, `resources_type`, `school_qty`, `industrial_park_qty`, `commercial_buildings`, `residential_quarters_qty`, `school_rate`, `industrial_park_rate`, `commercial_buildings_rate`, `residential_quarters_rate`, `industrial_park_dev_qty`, `commercial_buildings_dev_rate`, `mail_per_person_rate`, `service_per_capita`, `service_radius`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case dm_jxyz_sand_table_t:
                return "`id`, `grid_code`, `gis_code`, `gis_level`, `region_name`, `province_no`, `province_name`, `city_no`, `city_name`, `county_no`, `county_name`, `post_org_no`, `post_org_name`, `section_no`, `section_name`, `post_person_qty`, `commercial_buildings`, `industrial_park`, `residential_quarters`, `characteristic_markets`, `yesterday_collection_qty`, `month_collection_qty`, `year_collection_qty`, `yesterday_salary`, `month_salary`, `year_salary`, `yesterday_growth_rate`, `month_growth_rate`, `year_growth_rate`, `month_cash_income`, `yesterday_delivery_qty`, `month_delivery_qty`, `year_delivery_qty`, `year_delivery_growth_rate`, `extend_column`, `created_date`, `created_user`, `created_by`, `modify_user`, `modify_date`, `commercial_buildings_act_customer_qty`, `commercial_buildings_customer_qty`, `commercial_buildings_dev_rate`, `industrial_park_act_customer_qty`, `industrial_park_customer_qty`, `industrial_park_dev_rate`";
            case dm_jxyz_emp_info_t:
                return "`id`, `grid_code`, `emp_code`, `emp_name`, `emp_dept_code`, `emp_dept_name`, `emp_section_code`, `emp_section_name`, `emp_working_seniority`, `emp_post`, `emp_tel`, `emp_tel2`, `emp_training_times`, `ado_id`, `hire_time`, `leavedate`, `star_rating`, `customer_complaints`, `daily_volume`, `monthly_volume`, `daily_delivery`, `monthly_delivery`, `location_longitude`, `location_latitude`, `bundary_coordinate`, `remark`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`, `daily_salary`, `monthly_salary`";
            case dm_jxyz_sectin_info_t:
                return "`id`, `section_code`, `section_name`, `dept_code`, `dept_name`, `post_person_qty`, `bundary_coordinate`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case dwr_jxyz_customer_d:
                return "`id`, `sender_id`, `sender_no`, `sender`, `contract_name`, `contract_expiration_time`, `sender_warehouse_id`, `sender_warehouse_name`, `sender_linker`, `sender_fixtel`, `sender_mobile`, `sender_im_type`, `sender_im_id`, `post_org_no`, `post_org_name`, `sender_addr`, `key_market_code`, `key_market_name`, `customer_maintainer_name`, `customer_maintainer_no`, `emp_position`, `customer_email`, `location_longitude`, `location_latitude`, `bundary_coordinate`, `last_sender_date`, `extend_column`, `created_date`, `created_by`, `modify_user`, `modify_date`";
            case dwr_jxyz_department_d:
                return "`id`, `dept_code`, `dept_name`, `province_code`, `province_name`, `city_code`, `city_name`, `county_code`, `county_name`, `daily_effective_person`, `created_date`, `created_by`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case dwr_jxyz_emp_d:
                return "`id`, `emp_code`, `emp_name`, `emp_dept_code`, `emp_dept_name`, `emp_section_code`, `emp_section_name`, `emp_working_seniority`, `emp_post`, `emp_status`, `emp_tel`, `emp_tel2`, `emp_training_times`, `ado_id`, `hire_time`, `leavedate`, `location_longitude`, `location_latitude`, `bundary_coordinate`, `remark`, `created_date`, `created_by`, `modify_date`, `create_date`, `create_user`, `extend_column`, `modify_user`";
            case dwr_jxyz_resources_d:
                return "`id`, `key_market_code`, `key_market_name`, `market_address`, `resources_type`, `province_code`, `province_name`, `city_code`, `city_name`, `county_code`, `county_name`, `dept_code`, `dept_name`, `section_code`, `section_name`, `location_longitude`, `location_latitude`, `bundary_coordinate`, `users_qty`, `extend_column`, `created_date`, `created_by`, `modify_user`, `modify_date`";
            case dwr_jxyz_customer_relation_d:
                //开发中无表字段
                return "`id`, `grid_code`, `region_code`, `region_name`, `dept_code`, `dept_name`, `market_type`, `key_market_name`, `market_address`, `section_name`, `resident_enterprise_name`, `location_longitude`, `location_latitude`, `contract_status`, `contract_no`, `contract_name`, `contract_expiration_time`, `customer_maintainer_name`, `customer_key_contact`, `emp_position`, `customer_key_contact_number`, `customer_email`, `coverage`, `development_rate`, `users_qty`, `section_postman`, `mail_consumption_per_capita`, `service_per_capita`, `service_radius`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`, `created_date`, `created_by`, `bundary_coordinate`";
            case dm_customer_month_revenue_t:
                return "`id`, `post_org_no`, `post_org_name`, `sender_country_no`, `sender_country_name`, `sender_province_no`, `sender_province_name`, `sender_city_no`, `sender_city_name`, `sender_county_no`, `sender_county_name`, `sender_district_no`, `sender_id`, `sender_no`, `sender`, `sender_warehouse_id`, `sender_warehouse_name`, `period_id`, `order_weight`, `real_weight`, `fee_weight`, `postage_total`, `last_month_postage_total`, `postage_standard`, `postage_paid`, `postage_other`, `total_current_charges`, `total_charge_owed`, `total_prepaid_charges`, `unpaid_amount`, `payment_amount`, `collected_qty`, `last_month_collection_qty`, `month_delivery_qty`, `last_month_delivery_qty`, `daily_effective_person`, `files_qty`, `last_month_files_qty`, `goods_qty`, `last_month_goods_qty`, `is_new_customer`, `customer_send_time_type`, `customer_send_qty1`, `customer_send_qty2`, `customer_send_qty3`, `cur_year_collection_total`, `last_year_collection_qty`, `last_year_total_qty`, `cur_year_postage_total`, `last_year_postage_total`, `last_year_all_postage_total`, `created_date`, `created_by`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case dm_delivery_month_t:
                //开发中无表字段
                return "`id`, `period_id`, `post_person_id`, `post_person_no`, `post_person_name`, `post_person_mobile`, `receiver_country_no`, `receiver_country_name`, `receiver_province_no`, `receiver_province_name`, `receiver_city_no`, `receiver_city_name`, `receiver_county_no`, `receiver_county_name`, `receiver_district_no`, `deliver_org_no`, `deliver_org_name`, `deliver_section`, `deliver_section_name`, `deliver_section_code`, `deliver_qty`, `last_month_deliver_qty`, `cur_year_collection_total`, `current_year_total_qty`, `last_year_total_qty`, `court_mail_qty`, `created_date`, `created_by`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case dm_emp_month_collection_t:
                return "`id`, `post_org_id`, `post_org_no`, `org_drds_code`, `post_org_name`, `sender_country_no`, `sender_country_name`, `sender_province_no`, `sender_province_name`, `sender_city_no`, `sender_city_name`, `sender_county_no`, `sender_county_name`, `sender_district_no`, `post_person_id`, `post_person_no`, `post_person_name`, `post_person_mobile`, `section_code`, `section_name`, `period_id`, `order_weight`, `real_weight`, `fee_weight`, `postage_total`, `last_month_postage_total`, `postage_standard`, `postage_paid`, `postage_other`, `total_current_charges`, `total_charge_owed`, `total_prepaid_charges`, `unpaid_amount`, `payment_amount`, `collected_qty`, `last_month_collection_qty`, `month_delivery_qty`, `last_month_delivery_qty`, `cur_year_collection_total`, `last_year_collection_qty`, `last_year_total_qty`, `cur_year_postage_total`, `last_year_postage_total`, `last_year_all_postage_total`, `standard_express_collection`, `international_sales_volume`, `standard_express_salary`, `package_collection_salary`, `international_sales__salary`, `package_collection`, `created_date`, `created_by`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case dm_regional_month_collection_t:
                return "`id`, `sender_country_no`, `sender_country_name`, `sender_province_no`, `sender_province_name`, `sender_city_no`, `sender_city_name`, `sender_county_no`, `sender_county_name`, `sender_district_no`, `receiver_country_no`, `receiver_country_name`, `receiver_province_no`, `receiver_province_name`, `receiver_city_no`, `receiver_city_name`, `receiver_county_no`, `receiver_county_name`, `receiver_district_no`, `post_org_no`, `post_org_name`, `period_id`, `order_weight`, `real_weight`, `fee_weight`, `postage_total`, `last_month_postage_total`, `postage_standard`, `postage_paid`, `postage_other`, `total_current_charges`, `total_charge_owed`, `total_prepaid_charges`, `unpaid_amount`, `payment_amount`, `collected_qty`, `last_month_collection_qty`, `month_delivery_qty`, `last_month_delivery_qty`, `daily_effective_person`, `files_qty`, `last_month_files_qty`, `goods_qty`, `last_month_goods_qty`, `cur_year_collection_total`, `last_year_collection_qty`, `last_year_total_qty`, `cur_year_postage_total`, `last_year_postage_total`, `last_year_all_postage_total`, `created_date`, `created_by`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case dm_sales_department_collection_month_t:
                return "`id`, `post_org_id`, `post_org_no`, `org_drds_code`, `post_org_name`, `sender_country_no`, `sender_country_name`, `sender_province_no`, `sender_province_name`, `sender_city_no`, `sender_city_name`, `sender_county_no`, `sender_county_name`, `sender_district_no`, `period_id`, `order_weight`, `real_weight`, `fee_weight`, `postage_total`, `last_month_postage_total`, `postage_standard`, `postage_paid`, `postage_other`, `total_current_charges`, `total_charge_owed`, `total_prepaid_charges`, `unpaid_amount`, `payment_amount`, `collected_qty`, `last_month_collection_qty`, `month_delivery_qty`, `last_month_delivery_qty`, `daily_effective_person`, `files_qty`, `last_month_files_qty`, `goods_qty`, `last_month_goods_qty`, `files_revenue`, `goods_revenue`, `files_rate`, `goods_rate`, `files_unit_price`, `goods_unit_price`, `fee_weight_unit_price`, `cur_year_collection_total`, `last_year_collection_qty`, `last_year_total_qty`, `cur_year_postage_total`, `last_year_postage_total`, `last_year_all_postage_total`, `created_date`, `created_by`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case dwr_customer_daily_revenue_t:
                return "`post_org_no`, `post_org_name`, `sender_country_no`, `sender_country_name`, `sender_province_no`, `sender_province_name`, `sender_city_no`, `sender_city_name`, `sender_county_no`, `sender_county_name`, `sender_district_no`, `id`, `sender_id`, `sender_no`, `sender`, `sender_warehouse_id`, `sender_warehouse_name`, `period_id`, `order_weight`, `real_weight`, `fee_weight`, `postage_total`, `yesterday_postage_total`, `postage_standard`, `postage_paid`, `postage_other`, `total_current_charges`, `total_charge_owed`, `total_prepaid_charges`, `unpaid_amount`, `payment_amount`, `collected_qty`, `yesterday_collection_qty`, `delivery_qty`, `yesterday_delivery_qty`, `daily_effective_person`, `files_qty`, `yesterday_files_qty`, `goods_qty`, `yesterday_goods_qty`, `is_new_customer`, `customer_send_time_type`, `customer_send_qty1`, `customer_send_qty2`, `customer_send_qty3`, `created_date`, `created_by`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case dwr_delivery_detail_t:
                //开发中无表字段
                return "`id`, `period_id`, `post_person_id`, `post_person_no`, `post_person_name`, `post_person_mobile`, `receiver_country_no`, `receiver_country_name`, `receiver_province_no`, `receiver_province_name`, `receiver_city_no`, `receiver_city_name`, `receiver_county_no`, `receiver_county_name`, `receiver_district_no`, `deliver_org_no`, `deliver_org_name`, `deliver_section`, `deliver_section_name`, `deliver_section_code`, `deliver_qty`, `last_month_deliver_qty`, `cur_year_collection_total`, `current_year_total_qty`, `last_year_total_qty`, `court_mail_qty`, `created_date`, `created_by`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case dwr_emp_daily_collection_t:
                return "`id`, `post_org_id`, `post_org_no`, `org_drds_code`, `post_org_name`, `sender_country_no`, `sender_country_name`, `sender_province_no`, `sender_province_name`, `sender_city_no`, `sender_city_name`, `sender_county_no`, `sender_county_name`, `sender_district_no`, `post_person_id`, `post_person_no`, `post_person_name`, `post_person_mobile`, `section_code`, `section_name`, `period_id`, `order_weight`, `real_weight`, `fee_weight`, `postage_total`, `yesterday_postage_total`, `postage_standard`, `postage_paid`, `postage_other`, `total_current_charges`, `total_charge_owed`, `total_prepaid_charges`, `unpaid_amount`, `payment_amount`, `collected_qty`, `yesterday_collection_qty`, `delivery_qty`, `yesterday_delivery_qty`, `standard_express_collection`, `international_sales_volume`, `standard_express_salary`, `package_collection_salary`, `international_sales__salary`, `package_collection`, `created_date`, `created_by`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case dwr_regional_daily_collection_t:
                return "`id`, `sender_country_no`, `sender_country_name`, `sender_province_no`, `sender_province_name`, `sender_city_no`, `sender_city_name`, `sender_county_no`, `sender_county_name`, `sender_district_no`, `receiver_country_no`, `receiver_country_name`, `receiver_province_no`, `receiver_province_name`, `receiver_city_no`, `receiver_city_name`, `receiver_county_no`, `receiver_county_name`, `receiver_district_no`, `post_org_no`, `post_org_name`, `period_id`, `order_weight`, `real_weight`, `fee_weight`, `postage_total`, `yesterday_postage_total`, `postage_standard`, `postage_paid`, `postage_other`, `total_current_charges`, `total_charge_owed`, `total_prepaid_charges`, `unpaid_amount`, `payment_amount`, `collected_qty`, `yesterday_collection_qty`, `delivery_qty`, `yesterday_delivery_qty`, `daily_effective_person`, `files_qty`, `yesterday_files_qty`, `goods_qty`, `yesterday_goods_qty`, `files_revenue`, `goods_revenue`, `created_date`, `created_by`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case dwr_sales_department_collection_t:
                return "`id`, `post_org_id`, `post_org_no`, `org_drds_code`, `post_org_name`, `sender_country_no`, `sender_country_name`, `sender_province_no`, `sender_province_name`, `sender_city_no`, `sender_city_name`, `sender_county_no`, `sender_county_name`, `sender_district_no`, `period_id`, `order_weight`, `real_weight`, `fee_weight`, `postage_total`, `yesterday_postage_total`, `postage_standard`, `postage_paid`, `postage_other`, `total_current_charges`, `total_charge_owed`, `total_prepaid_charges`, `unpaid_amount`, `payment_amount`, `collected_qty`, `yesterday_collection_qty`, `delivery_qty`, `yesterday_delivery_qty`, `daily_effective_person`, `files_qty`, `yesterday_files_qty`, `goods_qty`, `yesterday_goods_qty`, `files_revenue`, `goods_revenue`, `files_rate`, `goods_rate`, `files_unit_price`, `goods_unit_price`, `fee_weight_unit_price`, `created_date`, `created_by`, `extend_column`, `create_user`, `create_date`, `modify_user`, `modify_date`";
            case sdi_jxyz_pkp_waybill_base_:
                return "`id`, `pkp_waybill_id`, `order_id`, `logistics_order_no`, `inner_channel`, `base_product_id`, `base_product_no`, `base_product_name`, `biz_product_id`, `biz_product_no`, `biz_product_name`, `product_type`, `product_reach_area`, `contents_attribute`, `cmd_code`, `manual_charge_reason`, `time_limit`, `io_type`, `ecommerce_no`, `waybill_type`, `waybill_no`, `pre_waybill_no`, `biz_occur_date`, `post_org_id`, `post_org_no`, `org_drds_code`, `post_org_name`, `post_person_id`, `post_person_no`, `post_person_name`, `post_person_mobile`, `sender_type`, `sender_id`, `sender_no`, `sender`, `sender_warehouse_id`, `sender_warehouse_name`, `sender_linker`, `sender_fixtel`, `sender_mobile`, `sender_im_type`, `sender_im_id`, `sender_id_type`, `sender_id_no`, `sender_id_encrypted_code`, `sender_agent_id_type`, `sender_agent_id_no`, `sender_id_encrypted_code_agent`, `sender_addr`, `sender_addr_additional`, `sender_country_no`, `sender_country_name`, `sender_province_no`, `sender_province_name`, `sender_city_no`, `sender_city_name`, `sender_county_no`, `sender_county_name`, `sender_district_no`, `sender_postcode`, `sender_gis`, `sender_notes`, `registered_customer_no`, `receiver_type`, `receiver_id`, `receiver_no`, `receiver`, `receiver_warehouse_id`, `receiver_warehouse_name`, `receiver_linker`, `receiver_im_type`, `receiver_im_id`, `receiver_fixtel`, `receiver_mobile`, `receiver_addr`, `receiver_addr_additional`, `receiver_country_no`, `receiver_country_name`, `receiver_province_no`, `receiver_province_name`, `receiver_city_no`, `receiver_city_name`, `receiver_county_no`, `receiver_county_name`, `receiver_district_no`, `receiver_postcode`, `receiver_gis`, `receiver_notes`, `customer_manager_id`, `customer_manager_no`, `customer_manager_name`, `salesman_id`, `salesman_no`, `salesman_name`, `order_weight`, `real_weight`, `fee_weight`, `volume_weight`, `volume`, `length`, `width`, `height`, `quantity`, `packaging`, `package_material`, `goods_desc`, `contents_type_no`, `contents_type_name`, `contents_weight`, `contents_quantity`, `cod_flag`, `cod_amount`, `receipt_flag`, `receipt_waybill_no`, `receipt_fee_amount`, `insurance_flag`, `insurance_amount`, `insurance_premium_amount`, `valuable_flag`, `transfer_type`, `pickup_type`, `allow_fee_flag`, `is_feed_flag`, `fee_date`, `postage_total`, `postage_standard`, `postage_paid`, `postage_other`, `payment_mode`, `discount_rate`, `settlement_mode`, `payment_state`, `payment_date`, `payment_id`, `is_advance_flag`, `deliver_type`, `deliver_sign`, `deliver_date`, `deliver_notes`, `deliver_pre_date`, `battery_flag`, `workbench`, `electronic_preferential_no`, `electronic_preferential_amount`, `pickup_attribute`, `adjust_type`, `postage_revoke`, `print_flag`, `print_date`, `print_times`, `is_deleted`, `create_user_id`, `gmt_created`, `modify_user_id`, `gmt_modified`, `declare_source`, `declare_type`, `declare_curr_code`, `post_org_product_name`, `fee_area_suite_code`, `post_batch_id`, `post_org_simple_name`, `fee_area_name`, `cargo_total_purchasing_price`, `fee_area_code`, `postage_suite_code`, `create_user_name`, `cargo_total_price`, `biz_product_type`, `modify_user_name`, `manage_org_code`, `contents_cargo_no`, `is_special_marketing`, `manual_fee_type`, `reserved1`, `reserved2`, `reserved3`, `reserved4`, `reserved5`, `reserved6`, `reserved7`, `reserved8`, `reserved9`, `reserved10`, `created_date`, `created_by`";
            default:
                return "*";
        }
    }


}
