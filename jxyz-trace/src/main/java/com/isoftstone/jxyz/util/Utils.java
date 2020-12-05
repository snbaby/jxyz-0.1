package com.isoftstone.jxyz.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static DateFormat df() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	//获取当前时间年月yyyyMM 202001
	public static String getYearMonth(){
		DateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(new Date());
	}
	
	public static String translateDateStr(Object obj) {
		if (null == obj) {
			return null;
		}
		try {
			return df().format(df().parse(obj.toString()));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String validateTable() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String tableStr = "SELECT \r\n" + 
				"    COUNT(*)\r\n" + 
				"FROM\r\n" + 
				"    information_schema.TABLES t\r\n" + 
				"WHERE\r\n" + 
				"    t.TABLE_SCHEMA = 'jxyz'\r\n" + 
				"        AND t.TABLE_NAME = 'sdi_jxyz_pkp_waybill_base_"+df.format(new Date())+"'";
		return tableStr;
	}

	public static String createTable() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String tableStr = "CREATE TABLE `sdi_jxyz_pkp_waybill_base_"+df.format(new Date())+"` (\r\n" + 
				"  `id` bigint(20) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `pkp_waybill_id` bigint(20) DEFAULT NULL COMMENT '运单流水号',\r\n" + 
				"  `order_id` bigint(20) DEFAULT NULL COMMENT '订单号（平台唯一）',\r\n" + 
				"  `logistics_order_no` varchar(50) DEFAULT NULL,\r\n" + 
				"  `inner_channel` varchar(20) DEFAULT NULL COMMENT '0:直接对接 1：邮务国内小包订单系统 2：邮务国际小包订单系统 3：速递国内订单系统 4：速递国际订单系统5：在线发货平台 6、11183派揽调度',\r\n" + 
				"  `base_product_id` bigint(20) DEFAULT NULL COMMENT '基础产品编号',\r\n" + 
				"  `base_product_no` varchar(20) DEFAULT NULL COMMENT '基础产品代码',\r\n" + 
				"  `base_product_name` varchar(20) DEFAULT NULL COMMENT '基础产品名称',\r\n" + 
				"  `biz_product_id` bigint(20) DEFAULT NULL COMMENT '业务产品编号',\r\n" + 
				"  `biz_product_no` varchar(20) DEFAULT NULL COMMENT '业务产品代码',\r\n" + 
				"  `biz_product_name` varchar(50) DEFAULT NULL COMMENT '业务产品名称',\r\n" + 
				"  `product_type` varchar(20) DEFAULT NULL,\r\n" + 
				"  `product_reach_area` char(1) DEFAULT NULL COMMENT '寄达范围 1：同城 2：地市 3：省内 4：国内 5：国际(地区)',\r\n" + 
				"  `contents_attribute` char(1) DEFAULT NULL COMMENT '内件性质 1：文件 2：信函 3：物品 4：包裹',\r\n" + 
				"  `cmd_code` varchar(20) DEFAULT NULL,\r\n" + 
				"  `manual_charge_reason` varchar(200) DEFAULT NULL,\r\n" + 
				"  `time_limit` char(1) DEFAULT NULL COMMENT '寄达时限 1：当日递 2：次晨达 3：次日递 4：限时递 5：当日上午递 6：经济时限 9：标准时限',\r\n" + 
				"  `io_type` varchar(20) DEFAULT NULL COMMENT '收寄来源：10：录入收寄  20：导入收寄 30：PDA收寄 40：终端收寄',\r\n" + 
				"  `ecommerce_no` varchar(20) DEFAULT NULL COMMENT '电商标识',\r\n" + 
				"  `waybill_type` varchar(20) DEFAULT NULL COMMENT '单据类型: 0常规运单 1退件运单',\r\n" + 
				"  `waybill_no` varchar(50) DEFAULT NULL COMMENT '运单号',\r\n" + 
				"  `pre_waybill_no` varchar(50) DEFAULT NULL COMMENT '标识 上游客户的单据号，或者返单业务对应的正向邮件号',\r\n" + 
				"  `biz_occur_date` datetime DEFAULT NULL COMMENT '业务发生时间（分表），预告信息接入代表接入时间，PDA揽收代表揽收时间，收寄作业代表收寄时间，运单调整代表申请调整时间',\r\n" + 
				"  `post_org_id` bigint(20) DEFAULT NULL COMMENT '收寄机构编号',\r\n" + 
				"  `post_org_no` varchar(50) DEFAULT NULL COMMENT '收寄机构代码',\r\n" + 
				"  `org_drds_code` varchar(50) DEFAULT NULL COMMENT '分库机构代码（分库）',\r\n" + 
				"  `post_org_name` varchar(200) DEFAULT NULL COMMENT '收寄机构名称',\r\n" + 
				"  `post_person_id` bigint(20) DEFAULT NULL COMMENT '收寄人员编号',\r\n" + 
				"  `post_person_no` varchar(50) DEFAULT NULL COMMENT '收寄人员代码',\r\n" + 
				"  `post_person_name` varchar(50) DEFAULT NULL COMMENT '收寄人员名称',\r\n" + 
				"  `post_person_mobile` varchar(50) DEFAULT NULL COMMENT '收寄人员电话',\r\n" + 
				"  `sender_type` char(1) DEFAULT NULL COMMENT '寄件客户类型：0 散户 1协议客户',\r\n" + 
				"  `sender_id` bigint(20) DEFAULT NULL COMMENT '寄件客户编号',\r\n" + 
				"  `sender_no` varchar(50) DEFAULT NULL COMMENT '寄件客户代码(大宗客户代码)',\r\n" + 
				"  `sender` varchar(200) DEFAULT NULL COMMENT '寄件客户名称',\r\n" + 
				"  `sender_warehouse_id` bigint(20) DEFAULT NULL COMMENT '寄件客户分仓编号',\r\n" + 
				"  `sender_warehouse_name` varchar(200) DEFAULT NULL COMMENT '寄件客户分仓名称',\r\n" + 
				"  `sender_linker` varchar(200) DEFAULT NULL COMMENT '寄件联系人',\r\n" + 
				"  `sender_fixtel` varchar(50) DEFAULT NULL COMMENT '寄件客户电话',\r\n" + 
				"  `sender_mobile` varchar(50) DEFAULT NULL COMMENT '寄件客户手机',\r\n" + 
				"  `sender_im_type` varchar(20) DEFAULT NULL COMMENT '寄件即时通讯类型 1：微信  2：QQ  3:淘宝旺旺',\r\n" + 
				"  `sender_im_id` varchar(50) DEFAULT NULL COMMENT '寄件即时通讯ID',\r\n" + 
				"  `sender_id_type` varchar(20) DEFAULT NULL COMMENT '寄件人证件类型 1:居民身份证、临时居民身份证、临时或者户口簿 2:中国人民解放军身份证件、中国人民武装警察身份证件 3:港澳居民来往内地通行证、台湾居民来往内地通行证或者其他有效旅行证件 4:外国公民护照 5:法律、行政和国家规定的其他有效身份证件',\r\n" + 
				"  `sender_id_no` varchar(50) DEFAULT NULL COMMENT '寄件人证件号码',\r\n" + 
				"  `sender_id_encrypted_code` varchar(50) DEFAULT NULL COMMENT '寄件人身份信息伪码',\r\n" + 
				"  `sender_agent_id_type` varchar(20) DEFAULT NULL COMMENT '交寄人证件类型 1:居民身份证、临时居民身份证、临时或者户口簿 2:中国人民解放军身份证件、中国人民武装警察身份证件 3:港澳居民来往内地通行证、台湾居民来往内地通行证或者其他有效旅行证件 4:外国公民护照 5:法律、行政和国家规定的其他有效身份证件',\r\n" + 
				"  `sender_agent_id_no` varchar(50) DEFAULT NULL COMMENT '交寄人证件号码',\r\n" + 
				"  `sender_id_encrypted_code_agent` varchar(50) DEFAULT NULL COMMENT '交寄人身份信息伪码',\r\n" + 
				"  `sender_addr` varchar(200) DEFAULT NULL COMMENT '寄件客户地址',\r\n" + 
				"  `sender_addr_additional` varchar(200) DEFAULT NULL COMMENT '外文收寄地址',\r\n" + 
				"  `sender_country_no` varchar(20) DEFAULT NULL COMMENT '寄件国家代码',\r\n" + 
				"  `sender_country_name` varchar(50) DEFAULT NULL COMMENT '寄件国家名称',\r\n" + 
				"  `sender_province_no` varchar(20) DEFAULT NULL COMMENT '寄件省份代码',\r\n" + 
				"  `sender_province_name` varchar(50) DEFAULT NULL COMMENT '寄件省份名称',\r\n" + 
				"  `sender_city_no` varchar(20) DEFAULT NULL COMMENT '寄件城市代码',\r\n" + 
				"  `sender_city_name` varchar(50) DEFAULT NULL COMMENT '寄件城市名称',\r\n" + 
				"  `sender_county_no` varchar(20) DEFAULT NULL COMMENT '寄件区县代码',\r\n" + 
				"  `sender_county_name` varchar(50) DEFAULT NULL COMMENT '寄件区县名称',\r\n" + 
				"  `sender_district_no` varchar(20) DEFAULT NULL COMMENT '寄件行政区划',\r\n" + 
				"  `sender_postcode` varchar(20) DEFAULT NULL COMMENT '寄件邮编',\r\n" + 
				"  `sender_gis` varchar(20) DEFAULT NULL COMMENT '寄件GIS坐标',\r\n" + 
				"  `sender_notes` varchar(1000) DEFAULT NULL,\r\n" + 
				"  `registered_customer_no` varchar(50) DEFAULT NULL COMMENT '注册客户号',\r\n" + 
				"  `receiver_type` char(1) DEFAULT NULL COMMENT '收件客户类型：0 散户 1协议客户',\r\n" + 
				"  `receiver_id` bigint(20) DEFAULT NULL COMMENT '收件客户编号',\r\n" + 
				"  `receiver_no` varchar(50) DEFAULT NULL COMMENT '收件客户代码（大宗客户代码）',\r\n" + 
				"  `receiver` varchar(200) DEFAULT NULL COMMENT '收件客户名称',\r\n" + 
				"  `receiver_warehouse_id` bigint(20) DEFAULT NULL COMMENT '收件客户分仓编号',\r\n" + 
				"  `receiver_warehouse_name` varchar(200) DEFAULT NULL COMMENT '收件客户分仓名称',\r\n" + 
				"  `receiver_linker` varchar(50) DEFAULT NULL COMMENT '收件联系人',\r\n" + 
				"  `receiver_im_type` varchar(20) DEFAULT NULL COMMENT '收件即时通讯类型 1：微信  2：QQ  3:淘宝旺旺',\r\n" + 
				"  `receiver_im_id` varchar(50) DEFAULT NULL COMMENT '收件即时通讯ID',\r\n" + 
				"  `receiver_fixtel` varchar(50) DEFAULT NULL COMMENT '收件客户电话',\r\n" + 
				"  `receiver_mobile` varchar(50) DEFAULT NULL COMMENT '收件客户手机',\r\n" + 
				"  `receiver_addr` varchar(200) DEFAULT NULL COMMENT '收件客户地址',\r\n" + 
				"  `receiver_addr_additional` varchar(200) DEFAULT NULL COMMENT '收件客户附加地址',\r\n" + 
				"  `receiver_country_no` varchar(20) DEFAULT NULL COMMENT '收件国家代码',\r\n" + 
				"  `receiver_country_name` varchar(50) DEFAULT NULL COMMENT '收件国家名称',\r\n" + 
				"  `receiver_province_no` varchar(20) DEFAULT NULL COMMENT '收件省份代码',\r\n" + 
				"  `receiver_province_name` varchar(50) DEFAULT NULL COMMENT '收件省份名称',\r\n" + 
				"  `receiver_city_no` varchar(20) DEFAULT NULL COMMENT '收件城市代码',\r\n" + 
				"  `receiver_city_name` varchar(50) DEFAULT NULL COMMENT '收件城市名称',\r\n" + 
				"  `receiver_county_no` varchar(20) DEFAULT NULL COMMENT '收件区县代码',\r\n" + 
				"  `receiver_county_name` varchar(50) DEFAULT NULL COMMENT '收件区县名称',\r\n" + 
				"  `receiver_district_no` varchar(20) DEFAULT NULL COMMENT '收件行政区划',\r\n" + 
				"  `receiver_postcode` varchar(20) DEFAULT NULL COMMENT '收件邮编',\r\n" + 
				"  `receiver_gis` varchar(20) DEFAULT NULL COMMENT '收件GIS坐标',\r\n" + 
				"  `receiver_notes` varchar(1000) DEFAULT NULL,\r\n" + 
				"  `customer_manager_id` bigint(20) DEFAULT NULL COMMENT '客户经理编号',\r\n" + 
				"  `customer_manager_no` varchar(50) DEFAULT NULL COMMENT '客户经理代码',\r\n" + 
				"  `customer_manager_name` varchar(50) DEFAULT NULL COMMENT '客户经理名称',\r\n" + 
				"  `salesman_id` bigint(20) DEFAULT NULL COMMENT '营销员编号',\r\n" + 
				"  `salesman_no` varchar(50) DEFAULT NULL COMMENT '营销员代码',\r\n" + 
				"  `salesman_name` varchar(50) DEFAULT NULL COMMENT '营销员名称',\r\n" + 
				"  `order_weight` decimal(8,0) DEFAULT NULL COMMENT '订单重量',\r\n" + 
				"  `real_weight` decimal(8,0) DEFAULT NULL COMMENT '实际重量',\r\n" + 
				"  `fee_weight` decimal(8,0) DEFAULT NULL COMMENT '计费重量',\r\n" + 
				"  `volume_weight` decimal(8,0) DEFAULT NULL COMMENT '体积重',\r\n" + 
				"  `volume` decimal(8,0) DEFAULT NULL COMMENT '体积',\r\n" + 
				"  `length` decimal(8,0) DEFAULT NULL COMMENT '长',\r\n" + 
				"  `width` decimal(8,0) DEFAULT NULL COMMENT '宽',\r\n" + 
				"  `height` decimal(8,0) DEFAULT NULL COMMENT '高',\r\n" + 
				"  `quantity` int(11) DEFAULT NULL COMMENT '数量',\r\n" + 
				"  `packaging` varchar(20) DEFAULT NULL COMMENT '邮件包装',\r\n" + 
				"  `package_material` varchar(20) DEFAULT NULL COMMENT '包装材质',\r\n" + 
				"  `goods_desc` varchar(200) DEFAULT NULL COMMENT '货物描述',\r\n" + 
				"  `contents_type_no` varchar(20) DEFAULT NULL COMMENT '内件类型代码',\r\n" + 
				"  `contents_type_name` varchar(50) DEFAULT NULL COMMENT '内件类型名称',\r\n" + 
				"  `contents_weight` decimal(8,0) DEFAULT NULL COMMENT '内件商品重量',\r\n" + 
				"  `contents_quantity` int(11) DEFAULT NULL COMMENT '内件商品数量',\r\n" + 
				"  `cod_flag` char(1) DEFAULT NULL COMMENT '代收款标志（附加服务）：1:代收货款 2:代缴费 9:无',\r\n" + 
				"  `cod_amount` decimal(12,2) DEFAULT NULL COMMENT '代收款金额',\r\n" + 
				"  `receipt_flag` char(1) DEFAULT NULL COMMENT '回单标志（反馈方式） 1:基本 2:回执 3:短信 5:电子返单 6:格式返单 7:自备返单 8:反向返单',\r\n" + 
				"  `receipt_waybill_no` varchar(20) DEFAULT NULL COMMENT '回单运单号',\r\n" + 
				"  `receipt_fee_amount` decimal(12,2) DEFAULT NULL COMMENT '回单费金额',\r\n" + 
				"  `insurance_flag` char(1) DEFAULT NULL COMMENT '保价保险标志（所负责任）1:基本 2:保价 3:保险 ',\r\n" + 
				"  `insurance_amount` decimal(12,2) DEFAULT NULL COMMENT '保价保险金额',\r\n" + 
				"  `insurance_premium_amount` decimal(12,2) DEFAULT NULL COMMENT '保费金额',\r\n" + 
				"  `valuable_flag` char(1) DEFAULT NULL COMMENT '贵品标识',\r\n" + 
				"  `transfer_type` varchar(20) DEFAULT NULL COMMENT '运输方式',\r\n" + 
				"  `pickup_type` varchar(20) DEFAULT NULL COMMENT '揽收方式 1：客户送货上门    2：机构上门揽收',\r\n" + 
				"  `allow_fee_flag` char(1) DEFAULT NULL COMMENT '是否可计费标志：0不可计费 1可计费',\r\n" + 
				"  `is_feed_flag` char(1) DEFAULT NULL COMMENT '计费完成标志：0 未计费 1已计费',\r\n" + 
				"  `fee_date` datetime DEFAULT NULL COMMENT '计费时间',\r\n" + 
				"  `postage_total` decimal(12,2) DEFAULT NULL COMMENT '总资费=实收邮资+其他资费',\r\n" + 
				"  `postage_standard` decimal(12,2) DEFAULT NULL COMMENT '标准邮资',\r\n" + 
				"  `postage_paid` decimal(12,2) DEFAULT NULL COMMENT '实收邮资',\r\n" + 
				"  `postage_other` decimal(12,2) DEFAULT NULL COMMENT '其它资费',\r\n" + 
				"  `payment_mode` varchar(20) DEFAULT NULL COMMENT '付款方式(付费方) 1:寄件人 2:收件人 3:第三方 4:收件人集中付费 5:免费 6:寄/收件人 7:预付卡',\r\n" + 
				"  `discount_rate` decimal(6,2) DEFAULT NULL COMMENT '优惠率 单位%',\r\n" + 
				"  `settlement_mode` varchar(20) DEFAULT NULL COMMENT '结算方式 1:现结 2:计欠 3:预付费 ',\r\n" + 
				"  `payment_state` char(1) DEFAULT NULL COMMENT '支付状态 0未支付 1已支付',\r\n" + 
				"  `payment_date` datetime DEFAULT NULL COMMENT '支付时间',\r\n" + 
				"  `payment_id` varchar(50) DEFAULT NULL COMMENT '支付流水号',\r\n" + 
				"  `is_advance_flag` char(1) DEFAULT NULL COMMENT '是否预告标志 0：无预告  1：有预告',\r\n" + 
				"  `deliver_type` char(1) DEFAULT NULL COMMENT '投递方式要求 1:上门投递 2:客户自提',\r\n" + 
				"  `deliver_sign` varchar(50) DEFAULT NULL COMMENT '投递签收要求 10：验示身份证 20：本人签收并验示身份证 30：收款 40：验示内件 50：验证取货码 60：拍照 70：电话预约 80：收回返单',\r\n" + 
				"  `deliver_date` char(1) DEFAULT NULL COMMENT '投递时间要求 0:无限制 1:工作日 2:双休日 ',\r\n" + 
				"  `deliver_notes` varchar(1000) DEFAULT NULL,\r\n" + 
				"  `deliver_pre_date` date DEFAULT NULL COMMENT '预约投递时间',\r\n" + 
				"  `battery_flag` char(1) DEFAULT NULL COMMENT '有无电池标志：0 无 1有',\r\n" + 
				"  `workbench` varchar(20) DEFAULT NULL COMMENT '台席',\r\n" + 
				"  `electronic_preferential_no` varchar(50) DEFAULT NULL COMMENT '电子优惠券号',\r\n" + 
				"  `electronic_preferential_amount` decimal(12,2) DEFAULT NULL COMMENT '电子优惠券金额',\r\n" + 
				"  `pickup_attribute` char(1) DEFAULT NULL COMMENT '收寄属性 1：全部  2：邮政 3：速递',\r\n" + 
				"  `adjust_type` varchar(20) DEFAULT NULL COMMENT '调整信息类型：10 收寄查改 20  撤单 30 改址 40 删除',\r\n" + 
				"  `postage_revoke` decimal(12,2) DEFAULT NULL COMMENT '撤单费用',\r\n" + 
				"  `print_flag` char(1) DEFAULT NULL COMMENT '打印标志 0：未打印 1：已打印',\r\n" + 
				"  `print_date` date DEFAULT NULL COMMENT '打印时间',\r\n" + 
				"  `print_times` int(11) DEFAULT NULL COMMENT '打印次数',\r\n" + 
				"  `is_deleted` char(1) DEFAULT NULL COMMENT '是否删除：0：否1：是',\r\n" + 
				"  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',\r\n" + 
				"  `gmt_created` datetime DEFAULT NULL COMMENT '创建时间',\r\n" + 
				"  `modify_user_id` bigint(20) DEFAULT NULL COMMENT '修改人id',\r\n" + 
				"  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',\r\n" + 
				"  `declare_source` varchar(50) DEFAULT NULL COMMENT '申报信息来源,1:个人申报；2:企业申报；3:个人税款复核',\r\n" + 
				"  `declare_type` varchar(50) DEFAULT NULL COMMENT '申报类别',\r\n" + 
				"  `declare_curr_code` varchar(50) DEFAULT NULL COMMENT '申报币制代码',\r\n" + 
				"  `post_org_product_name` varchar(100) DEFAULT NULL,\r\n" + 
				"  `fee_area_suite_code` varchar(100) DEFAULT NULL,\r\n" + 
				"  `post_batch_id` varchar(100) DEFAULT NULL,\r\n" + 
				"  `post_org_simple_name` varchar(100) DEFAULT NULL,\r\n" + 
				"  `fee_area_name` varchar(100) DEFAULT NULL,\r\n" + 
				"  `cargo_total_purchasing_price` varchar(100) DEFAULT NULL,\r\n" + 
				"  `fee_area_code` varchar(100) DEFAULT NULL,\r\n" + 
				"  `postage_suite_code` varchar(100) DEFAULT NULL,\r\n" + 
				"  `create_user_name` varchar(100) DEFAULT NULL,\r\n" + 
				"  `cargo_total_price` varchar(100) DEFAULT NULL,\r\n" + 
				"  `biz_product_type` varchar(100) DEFAULT NULL,\r\n" + 
				"  `modify_user_name` varchar(100) DEFAULT NULL,\r\n" + 
				"  `manage_org_code` varchar(100) DEFAULT NULL,\r\n" + 
				"  `contents_cargo_no` varchar(100) DEFAULT NULL,\r\n" + 
				"  `is_special_marketing` varchar(100) DEFAULT NULL,\r\n" + 
				"  `manual_fee_type` varchar(100) DEFAULT NULL,\r\n" + 
				"  `reserved1` varchar(200) DEFAULT NULL,\r\n" + 
				"  `reserved2` varchar(200) DEFAULT NULL,\r\n" + 
				"  `reserved3` varchar(200) DEFAULT NULL,\r\n" + 
				"  `reserved4` varchar(200) DEFAULT NULL,\r\n" + 
				"  `reserved5` varchar(200) DEFAULT NULL,\r\n" + 
				"  `reserved6` varchar(200) DEFAULT NULL,\r\n" + 
				"  `reserved7` varchar(200) DEFAULT NULL,\r\n" + 
				"  `reserved8` varchar(200) DEFAULT NULL,\r\n" + 
				"  `reserved9` datetime DEFAULT NULL,\r\n" + 
				"  `reserved10` text,\r\n" + 
				"  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\r\n" + 
				"  `created_by` varchar(200) DEFAULT 'sys',\r\n" + 
				"  UNIQUE KEY `u1` (`id`,`biz_occur_date`,`waybill_no`,`sender_city_no`,`sender_county_no`,`post_org_no`) USING BTREE,\r\n" + 
				"  KEY `ind_base` (`biz_occur_date`,`waybill_no`,`sender_city_no`,`sender_county_no`,`post_org_no`,`post_person_no`) USING BTREE,\r\n" + 
				"  KEY `n2` (`biz_occur_date`,`sender_type`,`sender_no`) USING BTREE\r\n" + 
				") ENGINE=InnoDB AUTO_INCREMENT=0 COMMENT='收寄运单基本信息表'\r\n" + 
				"";
		return tableStr;
	}

}
