/*
 Navicat Premium Data Transfer

 Source Server         : python
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : rm-8vbif49m6k7l651e5fo.mysql.zhangbei.rds.aliyuncs.com:3306
 Source Schema         : jxyz_plus

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 25/11/2020 09:46:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for income
-- ----------------------------
DROP TABLE IF EXISTS `income`;
CREATE TABLE `income`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dept_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '营业部code',
  `period_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '月份',
  `cur_day_qty` bigint(20) DEFAULT NULL COMMENT '今日揽收件数',
  `cur_day_total` decimal(12, 2) DEFAULT NULL COMMENT '今日揽收收入',
  `cur_day_qty_s` bigint(20) DEFAULT NULL COMMENT '今日现费件数',
  `cur_day_total1_s` decimal(12, 2) DEFAULT NULL COMMENT '今日现费收入',
  `last_day_qty` bigint(20) DEFAULT NULL COMMENT '昨日揽收件数',
  `last_day_total` decimal(12, 2) DEFAULT NULL COMMENT '昨日揽收收入',
  `last_day_qty_s` bigint(20) DEFAULT NULL COMMENT '昨日现费件数',
  `last_day_total1_s` decimal(12, 2) DEFAULT NULL COMMENT '昨日现费收入',
  `last_month_clledted_qty` bigint(20) DEFAULT NULL COMMENT '当月揽收件数',
  `last_month_postage_total` decimal(12, 2) DEFAULT NULL COMMENT '当月揽收收入',
  `last_collected_qty` bigint(20) DEFAULT NULL COMMENT '当月现费件数',
  `last_postage_total` decimal(12, 2) DEFAULT NULL COMMENT '当月现费收入',
  `year_collected_qty` bigint(20) DEFAULT NULL COMMENT '年累计件数',
  `year_postage_total` decimal(12, 2) DEFAULT NULL COMMENT '年累计收入',
  `modify_date` datetime(0) DEFAULT NULL COMMENT '修改时间',
  `all_parent_code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 761 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
