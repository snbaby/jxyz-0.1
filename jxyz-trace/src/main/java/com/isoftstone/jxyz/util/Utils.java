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
	
	public static String validateTable(String table) {
		String tableStr = "SELECT \r\n" + 
				"    COUNT(*)\r\n" + 
				"FROM\r\n" + 
				"    information_schema.TABLES t\r\n" + 
				"WHERE\r\n" + 
				"    t.TABLE_SCHEMA = 'jxyz'\r\n" + 
				"        AND t.TABLE_NAME = '"+table+"'";
		return tableStr;
	}

	public static String createMessageTable(String table) {
		String tableStr = "CREATE TABLE `"+table+"` (\r\n" + 
				"  `trace_no` varchar(100) DEFAULT NULL COMMENT '运单号',\r\n" + 
				"  `op_time` datetime DEFAULT NULL COMMENT '操作时间',\r\n" + 
				"  `op_code` varchar(100) DEFAULT NULL COMMENT '操作码',\r\n" + 
				"  `op_name` varchar(100) DEFAULT NULL COMMENT '操作名',\r\n" + 
				"  `op_desc` varchar(1000) DEFAULT NULL COMMENT '操作描述',\r\n" + 
				"  `op_org_prov_name` varchar(100) DEFAULT NULL COMMENT '操作网点省名',\r\n" + 
				"  `op_org_city` varchar(100) DEFAULT NULL COMMENT '操作网点城市',\r\n" + 
				"  `op_org_code` varchar(100) DEFAULT NULL COMMENT '操作网点编码',\r\n" + 
				"  `op_org_name` varchar(100) DEFAULT NULL COMMENT '操作网点名称',\r\n" + 
				"  `op_erator_no` varchar(100) DEFAULT NULL COMMENT '操作员工号',\r\n" + 
				"  `op_erator_name` varchar(100) DEFAULT NULL COMMENT '操作员工名称',\r\n" + 
				"  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\r\n" + 
				"  `created_by` varchar(200) DEFAULT NULL COMMENT '创建人',\r\n" + 
				"  KEY `N12` (`op_time`,`trace_no`,`op_org_code`,`op_code`) USING BTREE\r\n" + 
				") ENGINE=InnoDB ROW_FORMAT=DYNAMIC COMMENT='路径信息表'\r\n" + 
				"/*!50100 PARTITION BY RANGE (month(`op_time`))\r\n" + 
				"SUBPARTITION BY HASH (dayofmonth(`op_time`))\r\n" + 
				"(PARTITION p1 VALUES LESS THAN (2)\r\n" + 
				" (SUBPARTITION p1_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p1_s31 ENGINE = InnoDB),\r\n" + 
				" PARTITION p2 VALUES LESS THAN (3)\r\n" + 
				" (SUBPARTITION p2_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p2_s31 ENGINE = InnoDB),\r\n" + 
				" PARTITION p3 VALUES LESS THAN (4)\r\n" + 
				" (SUBPARTITION p3_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p3_s31 ENGINE = InnoDB),\r\n" + 
				" PARTITION p4 VALUES LESS THAN (5)\r\n" + 
				" (SUBPARTITION p4_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p4_s31 ENGINE = InnoDB),\r\n" + 
				" PARTITION p5 VALUES LESS THAN (6)\r\n" + 
				" (SUBPARTITION p5_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p5_s31 ENGINE = InnoDB),\r\n" + 
				" PARTITION p6 VALUES LESS THAN (7)\r\n" + 
				" (SUBPARTITION p6_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p6_s31 ENGINE = InnoDB),\r\n" + 
				" PARTITION p7 VALUES LESS THAN (8)\r\n" + 
				" (SUBPARTITION p7_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p7_s31 ENGINE = InnoDB),\r\n" + 
				" PARTITION p8 VALUES LESS THAN (9)\r\n" + 
				" (SUBPARTITION p8_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p8_s31 ENGINE = InnoDB),\r\n" + 
				" PARTITION p9 VALUES LESS THAN (10)\r\n" + 
				" (SUBPARTITION p9_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p9_s31 ENGINE = InnoDB),\r\n" + 
				" PARTITION p10 VALUES LESS THAN (11)\r\n" + 
				" (SUBPARTITION p10_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p10_s31 ENGINE = InnoDB),\r\n" + 
				" PARTITION p11 VALUES LESS THAN (12)\r\n" + 
				" (SUBPARTITION p11_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p11_s31 ENGINE = InnoDB),\r\n" + 
				" PARTITION p12 VALUES LESS THAN (13)\r\n" + 
				" (SUBPARTITION p12_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION p12_s31 ENGINE = InnoDB),\r\n" + 
				" PARTITION pmax VALUES LESS THAN MAXVALUE\r\n" + 
				" (SUBPARTITION pmax_s1 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s2 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s3 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s4 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s5 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s6 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s7 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s8 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s9 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s10 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s11 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s12 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s13 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s14 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s15 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s16 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s17 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s18 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s19 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s20 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s21 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s22 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s23 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s24 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s25 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s26 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s27 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s28 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s29 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s30 ENGINE = InnoDB,\r\n" + 
				"  SUBPARTITION pmax_s31 ENGINE = InnoDB)) */;\r\n" + 
				"";
		return tableStr;
	}

}
