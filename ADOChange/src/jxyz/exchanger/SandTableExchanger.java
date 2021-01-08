package jxyz.exchanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import jxyz.Application;
import jxyz.utils.HttpUtil;
import jxyz.utils.Tools;

/**
 * 沙盘看板 输入表：dwr_emp_daily_collection_t 输出表：dm_jxyz_sand_table_t
 * 
 * @author xiaoxin
 *
 */
public class SandTableExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		// 删除目标数据(从全局变量里取开始时间和结束时间)
		String deleteSQL = "DELETE FROM dm_jxyz_sand_table_t  WHERE grid_level = '4'";
		PreparedStatement deletePs = connection.prepareStatement(deleteSQL);
		int num = deletePs.executeUpdate();
		System.out.println("断道级别删除: " + num);
		deletePs.close();
		
		//断道级别数据插入
		String queryAndInsertSQL = "	INSERT INTO dm_jxyz_sand_table_t(grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 province_no,	\r\n" + 
				"			 province_name,\r\n" + 
				"			 city_no,\r\n" + 
				"			 city_name,\r\n" + 
				"			 county_no,\r\n" + 
				"			 county_name,\r\n" + 
				"			 post_org_no,\r\n" + 
				"			 post_org_name,\r\n" + 
				"			 section_no,\r\n" + 
				"			 section_name,\r\n" + 
				"			 yesterday_collection_qty,\r\n" + 
				"		    month_collection_qty	,\r\n" + 
				"		    year_collection_qty		,\r\n" + 
				"		    yesterday_salary		,\r\n" + 
				"		    month_salary			,\r\n" + 
				"		    year_salary				)\r\n" + 
				"	SELECT grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 sender_province_no,	\r\n" + 
				"			 sender_province_name,\r\n" + 
				"			 sender_city_no,\r\n" + 
				"			 sender_city_name,\r\n" + 
				"			 sender_county_no,\r\n" + 
				"			 sender_county_name,\r\n" + 
				"			 post_org_no,\r\n" + 
				"			 post_org_name,\r\n" + 
				"			 section_code,\r\n" + 
				"			 section_name,\r\n" + 
				"			SUM(yesterday_collection_qty)	yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			SUM(month_collection_qty	)	month_collection_qty	,							#本月标快揽收量\r\n" + 
				"			SUM(year_collection_qty		)	year_collection_qty	,						#本年标快揽收量\r\n" + 
				"			SUM(yesterday_salary		)	yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			SUM(month_salary			)	month_salary			,												#本月标快揽收收入\r\n" + 
				"			SUM(year_salary				)	year_salary				#本年标快揽收收入\r\n" + 
				"	FROM (\r\n" + 
				"	#天维度数据\r\n" + 
				"	SELECT \r\n" + 
				"			CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_code \r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_code \r\n" + 
				"			  ELSE CONCAT(d.post_org_no,'01') END grid_code,	 #段道编码\r\n" + 
				"			  '4' AS grid_level,   	  									 #界面等级\r\n" + 
				"			 CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_name\r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_name\r\n" + 
				"			  ELSE 'L01' END region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			 d.sender_city_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no) sender_city_name,\r\n" + 
				"			d.sender_county_no,\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_county_no) sender_county_name,\r\n" + 
				"			d.post_org_no,\r\n" + 
				"			 IFNULL(dep.dept_name,d.post_org_name) post_org_name,\r\n" + 
				"			CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_code \r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_code \r\n" + 
				"			  ELSE CONCAT(d.post_org_no,'01') END section_code,		  \r\n" + 
				"			 CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_name\r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_name\r\n" + 
				"			  ELSE 'L01' END section_name,\r\n" + 
				"			  d.standard_express_collection yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  0 month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  0 year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  d.standard_express_salary yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  0 month_salary	,												#本月标快揽收收入\r\n" + 
				"			  0 year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dwr_emp_daily_collection_t d LEFT OUTER JOIN \r\n" + 
				"	    (SELECT g.parent_code,\r\n" + 
				"			g.grid_name,\r\n" + 
				"			g.grid_code FROM \r\n" + 
				"	(SELECT \r\n" + 
				"		@grid_num := IF(@parent_code = `parent_code` , @grid_num +1 ,1 ) num,\r\n" + 
				"			`code` AS grid_code,\r\n" + 
				"			`name` AS grid_name ,\r\n" + 
				"		@parent_code:=	g.parent_code parent_code\r\n" + 
				"	FROM t_grid_m  g INNER JOIN\r\n" + 
				"	(SELECT @parent_code = '' ,@grid_num := 0) tt\r\n" + 
				"	WHERE `level` = '5'\r\n" + 
				"	ORDER BY g.parent_code,`code`  \r\n" + 
				"	) g\r\n" + 
				"	WHERE num = 1 )  g \r\n" + 
				"	ON d.post_org_no = g.parent_code\r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id = '${LAST_DAY}' \r\n" + 
				"	UNION ALL \r\n" + 
				"	#月度数据\r\n" + 
				"	SELECT \r\n" + 
				"			CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_code \r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_code \r\n" + 
				"			  ELSE CONCAT(d.post_org_no,'01') END grid_code,	 #段道编码\r\n" + 
				"			  '4' AS grid_level,   	  									 #界面等级\r\n" + 
				"			 CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_name\r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_name\r\n" + 
				"			  ELSE 'L01' END region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			 d.sender_city_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no) sender_city_name,\r\n" + 
				"			d.sender_county_no,\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_county_no) sender_county_name,\r\n" + 
				"			d.post_org_no,\r\n" + 
				"			 IFNULL(dep.dept_name,d.post_org_name) post_org_name,\r\n" + 
				"			CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_code \r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_code \r\n" + 
				"			  ELSE CONCAT(d.post_org_no,'01') END section_code,		  \r\n" + 
				"			 CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_name\r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_name\r\n" + 
				"			  ELSE 'L01' END section_name,\r\n" + 
				"			  0 yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  d.standard_express_collection month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  0 year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  0 yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  d.standard_express_salary month_salary	,												#本月标快揽收收入\r\n" + 
				"			  0 year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dm_emp_month_collection_t d LEFT OUTER JOIN \r\n" + 
				"	    (SELECT g.parent_code,\r\n" + 
				"			g.grid_name,\r\n" + 
				"			g.grid_code FROM \r\n" + 
				"	(SELECT \r\n" + 
				"		@grid_num1 := IF(@parent_code1 = `parent_code` , @grid_num1 +1 ,1 ) num,\r\n" + 
				"			`code` AS grid_code,\r\n" + 
				"			`name` AS grid_name ,\r\n" + 
				"		@parent_code1 :=	g.parent_code parent_code\r\n" + 
				"	FROM t_grid_m  g INNER JOIN\r\n" + 
				"	(SELECT @parent_code1 = '' ,@grid_num1 := 0) tt\r\n" + 
				"	WHERE `level` = '5'\r\n" + 
				"	ORDER BY g.parent_code,`code`  \r\n" + 
				"	) g\r\n" + 
				"	WHERE num = 1 )  g \r\n" + 
				"	ON d.post_org_no = g.parent_code\r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id = DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')\r\n" + 
				"	#本年\r\n" + 
				"	UNION ALL \r\n" + 
				"	SELECT \r\n" + 
				"			CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_code \r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_code \r\n" + 
				"			  ELSE CONCAT(d.post_org_no,'01') END grid_code,	 #段道编码\r\n" + 
				"			  '4' AS grid_level,   	  									 #界面等级\r\n" + 
				"			 CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_name\r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_name\r\n" + 
				"			  ELSE 'L01' END region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			 d.sender_city_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no) sender_city_name,\r\n" + 
				"			d.sender_county_no,\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_county_no) sender_county_name,\r\n" + 
				"			 d.post_org_no,\r\n" + 
				"			 IFNULL(dep.dept_name,d.post_org_name) post_org_name,\r\n" + 
				"			CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_code \r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_code \r\n" + 
				"			  ELSE CONCAT(d.post_org_no,'01') END section_code,		  \r\n" + 
				"			 CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_name\r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_name\r\n" + 
				"			  ELSE 'L01' END section_name,\r\n" + 
				"			  0 yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  0 month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  d.standard_express_collection year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  0 yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  0 month_salary	,												#本月标快揽收收入\r\n" + 
				"			  d.standard_express_salary year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dm_emp_month_collection_t d LEFT OUTER JOIN \r\n" + 
				"	    (SELECT g.parent_code,\r\n" + 
				"			g.grid_name,\r\n" + 
				"			g.grid_code FROM \r\n" + 
				"	(SELECT \r\n" + 
				"		@grid_num2 := IF(@parent_code2 = `parent_code` , @grid_num2 +1 ,1 ) num,\r\n" + 
				"			`code` AS grid_code,\r\n" + 
				"			`name` AS grid_name ,\r\n" + 
				"		@parent_code2 :=	g.parent_code parent_code\r\n" + 
				"	FROM t_grid_m  g INNER JOIN\r\n" + 
				"	(SELECT @parent_code2 = '' ,@grid_num2 := 0) tt\r\n" + 
				"	WHERE `level` = '5'\r\n" + 
				"	ORDER BY g.parent_code,`code`  \r\n" + 
				"	) g\r\n" + 
				"	WHERE num = 1 )  g \r\n" + 
				"	ON d.post_org_no = g.parent_code\r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE  d.period_id >= DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%01') and d.period_id <= DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m') ) f\r\n" +
				"	GROUP BY grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 sender_province_no,	\r\n" + 
				"			 sender_province_name,\r\n" + 
				"			 sender_city_no,\r\n" + 
				"			 sender_city_name,\r\n" + 
				"			 sender_county_no,\r\n" + 
				"			 sender_county_name,\r\n" + 
				"			 post_org_no,\r\n" + 
				"			 post_org_name,\r\n" + 
				"			 section_code,\r\n" + 
				"			 section_name";
		Map<String, String> params = new HashMap<String, String>();
		params.put("LAST_DAY", (String) Application.GLOBAL_PARAM.get(Application.LAST_DAY));
		params.put("CURR_MONTH", (String) Application.GLOBAL_PARAM.get(Application.CURR_MONTH));
		params.put("LAST_MONTH", (String) Application.GLOBAL_PARAM.get(Application.LAST_MONTH));
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		
		PreparedStatement queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("段道级别数据插入: " + num);
		queryAndInsertPs.close();
		
		//营业部级别的数据插入
		deleteSQL = "DELETE FROM dm_jxyz_sand_table_t  WHERE grid_level = '3'";
		deletePs = connection.prepareStatement(deleteSQL);
		num = deletePs.executeUpdate();
		System.out.println("营业部级别删除: " + num);
		deletePs.close();

		queryAndInsertSQL = "INSERT INTO dm_jxyz_sand_table_t(grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 province_no,	\r\n" + 
				"			 province_name,\r\n" + 
				"			 city_no,\r\n" + 
				"			 city_name,\r\n" + 
				"			 county_no,\r\n" + 
				"			 county_name,\r\n" + 
				"			 post_org_no,\r\n" + 
				"			 post_org_name,\r\n" + 
				"			 yesterday_collection_qty,\r\n" + 
				"		    month_collection_qty	,\r\n" + 
				"		    year_collection_qty		,\r\n" + 
				"		    yesterday_salary		,\r\n" + 
				"		    month_salary			,\r\n" + 
				"		    year_salary				)\r\n" + 
				"	SELECT grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 sender_province_no,	\r\n" + 
				"			 sender_province_name,\r\n" + 
				"			 sender_city_no,\r\n" + 
				"			 sender_city_name,\r\n" + 
				"			 sender_county_no,\r\n" + 
				"			 sender_county_name,\r\n" + 
				"			 post_org_no,\r\n" + 
				"			 post_org_name,\r\n" + 
				"			SUM(yesterday_collection_qty)	yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			SUM(month_collection_qty	)	month_collection_qty	,							#本月标快揽收量\r\n" + 
				"			SUM(year_collection_qty		)	year_collection_qty	,						#本年标快揽收量\r\n" + 
				"			SUM(yesterday_salary		)	yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			SUM(month_salary			)	month_salary			,												#本月标快揽收收入\r\n" + 
				"			SUM(year_salary				)	year_salary				#本年标快揽收收入\r\n" + 
				"	FROM (\r\n" + 
				"	#天维度数据\r\n" + 
				"	SELECT \r\n" + 
				"			d.post_org_no grid_code,	 #营业部编码\r\n" + 
				"			  '3' AS grid_level,   	  									 #界面等级\r\n" + 
				"			IFNULL(dep.dept_name,d.post_org_name)  region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			 d.sender_city_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no) sender_city_name,\r\n" + 
				"			d.sender_county_no,\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_county_no) sender_county_name,\r\n" + 
				"			d.post_org_no,\r\n" + 
				"			IFNULL(dep.dept_name,d.post_org_name) post_org_name,\r\n" + 
				"			  d.standard_express_collection yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  0 month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  0 year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  d.standard_express_salary yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  0 month_salary	,												#本月标快揽收收入\r\n" + 
				"			  0 year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dwr_emp_daily_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id = '${LAST_DAY}' \r\n" + 
				"	UNION ALL \r\n" + 
				"	#月度数据\r\n" + 
				"	SELECT \r\n" + 
				"			d.post_org_no grid_code,	 #营业部编码\r\n" + 
				"			  '3' AS grid_level,   	  									 #界面等级\r\n" + 
				"			IFNULL(dep.dept_name,d.post_org_name)  region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			 d.sender_city_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no) sender_city_name,\r\n" + 
				"			d.sender_county_no,\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_county_no) sender_county_name,\r\n" + 
				"			d.post_org_no,\r\n" + 
				"			IFNULL(dep.dept_name,d.post_org_name) post_org_name,\r\n" + 
				"			  0 yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  d.standard_express_collection month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  0 year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  0 yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  d.standard_express_salary month_salary	,												#本月标快揽收收入\r\n" + 
				"			  0 year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dm_emp_month_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id = DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')\r\n" + 
				"	UNION ALL \r\n" + 
				"	#本年 \r\n" + 
				"	SELECT \r\n" + 
				"			d.post_org_no grid_code,	 #营业部编码\r\n" + 
				"			  '3' AS grid_level,   	  									 #界面等级\r\n" + 
				"			IFNULL(dep.dept_name,d.post_org_name)  region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			 d.sender_city_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no) sender_city_name,\r\n" + 
				"			d.sender_county_no,\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_county_no) sender_county_name,\r\n" + 
				"			d.post_org_no,\r\n" + 
				"			IFNULL(dep.dept_name,d.post_org_name) post_org_name,\r\n" + 
				"			  0 yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  0 month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  d.standard_express_collection year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  0 yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  0 month_salary	,												#本月标快揽收收入\r\n" + 
				"			  d.standard_express_salary year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dm_emp_month_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id >= DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%01') and d.period_id <= DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')) f\r\n" +
				"	GROUP BY grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 sender_province_no,	\r\n" + 
				"			 sender_province_name,\r\n" + 
				"			 sender_city_no,\r\n" + 
				"			 sender_city_name,\r\n" + 
				"			 sender_county_no,\r\n" + 
				"			 sender_county_name,\r\n" + 
				"			 post_org_no,\r\n" + 
				"			 post_org_name";
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("营业部级别数据插入: " + num);
		queryAndInsertPs.close();
		
		//区县级别的数据插入
		deleteSQL = "DELETE FROM dm_jxyz_sand_table_t  WHERE grid_level = '2'";
		deletePs = connection.prepareStatement(deleteSQL);
		num = deletePs.executeUpdate();
		System.out.println("区县级别删除: " + num);
		deletePs.close();

		queryAndInsertSQL = "INSERT INTO dm_jxyz_sand_table_t(grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 province_no,	\r\n" + 
				"			 province_name,\r\n" + 
				"			 city_no,\r\n" + 
				"			 city_name,\r\n" + 
				"			 county_no,\r\n" + 
				"			 county_name,\r\n" + 
				"			 yesterday_collection_qty,\r\n" + 
				"		    month_collection_qty	,\r\n" + 
				"		    year_collection_qty		,\r\n" + 
				"		    yesterday_salary		,\r\n" + 
				"		    month_salary			,\r\n" + 
				"		    year_salary				)\r\n" + 
				"		SELECT grid_code,\r\n" + 
				"				 grid_level, \r\n" + 
				"				 region_name,\r\n" + 
				"				 sender_province_no,	\r\n" + 
				"				 sender_province_name,\r\n" + 
				"				 sender_city_no,\r\n" + 
				"				 sender_city_name,\r\n" + 
				"				 sender_county_no,\r\n" + 
				"				 sender_county_name,\r\n" + 
				"				SUM(yesterday_collection_qty)	yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"				SUM(month_collection_qty	)	month_collection_qty	,							#本月标快揽收量\r\n" + 
				"				SUM(year_collection_qty		)	year_collection_qty	,						#本年标快揽收量\r\n" + 
				"				SUM(yesterday_salary		)	yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"				SUM(month_salary			)	month_salary			,												#本月标快揽收收入\r\n" + 
				"				SUM(year_salary				)	year_salary				#本年标快揽收收入\r\n" + 
				"		FROM (\r\n" + 
				"		#天维度数据\r\n" + 
				"		SELECT \r\n" + 
				"				d.sender_county_no grid_code,	 #区县编码\r\n" + 
				"				  '2' AS grid_level,   	  									 #界面等级\r\n" + 
				"				(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_county_no)  region_name,								#段道名称\r\n" + 
				"				 d.sender_province_no,\r\n" + 
				"				 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"				 d.sender_city_no,\r\n" + 
				"				 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no) sender_city_name,\r\n" + 
				"				d.sender_county_no,\r\n" + 
				"				(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_county_no) sender_county_name,\r\n" + 
				"				  d.standard_express_collection yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"				  0 month_collection_qty,							#本月标快揽收量\r\n" + 
				"				  0 year_collection_qty,						#本年标快揽收量\r\n" + 
				"				  d.standard_express_salary yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"				  0 month_salary	,												#本月标快揽收收入\r\n" + 
				"				  0 year_salary													#本年标快揽收收入\r\n" + 
				"		FROM dwr_emp_daily_collection_t d \r\n" + 
				"		LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"		ON dep.dept_code = d.post_org_no\r\n" + 
				"		WHERE d.period_id = '${LAST_DAY}' \r\n" + 
				"		UNION ALL \r\n" + 
				"		#月度数据\r\n" + 
				"		SELECT \r\n" + 
				"				d.sender_county_no grid_code,	 #区县编码\r\n" + 
				"				  '2' AS grid_level,   	  									 #界面等级\r\n" + 
				"				(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_county_no)  region_name,								#段道名称\r\n" + 
				"				 d.sender_province_no,\r\n" + 
				"				 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"				 d.sender_city_no,\r\n" + 
				"				 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no) sender_city_name,\r\n" + 
				"				d.sender_county_no,\r\n" + 
				"				(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_county_no) sender_county_name,\r\n" + 
				"				  0 yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"				  d.standard_express_collection month_collection_qty,							#本月标快揽收量\r\n" + 
				"				  0 year_collection_qty,						#本年标快揽收量\r\n" + 
				"				  0 yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"				  d.standard_express_salary month_salary	,												#本月标快揽收收入\r\n" + 
				"				  0 year_salary													#本年标快揽收收入\r\n" + 
				"		FROM dm_emp_month_collection_t d \r\n" + 
				"		LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"		ON dep.dept_code = d.post_org_no\r\n" + 
				"		WHERE d.period_id = DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')\r\n" + 
				"		UNION ALL \r\n" + 
				"		#本年 \r\n" + 
				"		SELECT \r\n" + 
				"				d.sender_county_no grid_code,	 #区县编码\r\n" + 
				"				  '2' AS grid_level,   	  									 #界面等级\r\n" + 
				"				(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_county_no)  region_name,								#段道名称\r\n" + 
				"				 d.sender_province_no,\r\n" + 
				"				 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"				 d.sender_city_no,\r\n" + 
				"				 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no) sender_city_name,\r\n" + 
				"				d.sender_county_no,\r\n" + 
				"				(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_county_no) sender_county_name,\r\n" + 
				"				  0 yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"				  0 month_collection_qty,							#本月标快揽收量\r\n" + 
				"				  d.standard_express_collection year_collection_qty,						#本年标快揽收量\r\n" + 
				"				  0 yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"				  0 month_salary	,												#本月标快揽收收入\r\n" + 
				"				  d.standard_express_salary year_salary													#本年标快揽收收入\r\n" + 
				"		FROM dm_emp_month_collection_t d \r\n" + 
				"		LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"		ON dep.dept_code = d.post_org_no\r\n" + 
				"		WHERE d.period_id >= DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%01') and d.period_id <= DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')) f\r\n" +
				"		GROUP BY grid_code,\r\n" + 
				"				 grid_level, \r\n" + 
				"				 region_name,\r\n" + 
				"				 sender_province_no,	\r\n" + 
				"				 sender_province_name,\r\n" + 
				"				 sender_city_no,\r\n" + 
				"				 sender_city_name,\r\n" + 
				"				 sender_county_no,\r\n" + 
				"				 sender_county_name";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("区县级别数据插入: " + num);
		queryAndInsertPs.close();
		
		//市级别的数据插入
		deleteSQL = "DELETE FROM dm_jxyz_sand_table_t  WHERE grid_level = '1'";
		deletePs = connection.prepareStatement(deleteSQL);
		num = deletePs.executeUpdate();
		System.out.println("市级别删除: " + num);
		deletePs.close();

		queryAndInsertSQL = "INSERT INTO dm_jxyz_sand_table_t(grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 province_no,	\r\n" + 
				"			 province_name,\r\n" + 
				"			 city_no,\r\n" + 
				"			 city_name,\r\n" + 
				"			 yesterday_collection_qty,\r\n" + 
				"		    month_collection_qty	,\r\n" + 
				"		    year_collection_qty		,\r\n" + 
				"		    yesterday_salary		,\r\n" + 
				"		    month_salary			,\r\n" + 
				"		    year_salary				)\r\n" + 
				"	SELECT grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 sender_province_no,	\r\n" + 
				"			 sender_province_name,\r\n" + 
				"			 sender_city_no,\r\n" + 
				"			 sender_city_name,\r\n" + 
				"			SUM(yesterday_collection_qty)	yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			SUM(month_collection_qty	)	month_collection_qty	,							#本月标快揽收量\r\n" + 
				"			SUM(year_collection_qty		)	year_collection_qty	,						#本年标快揽收量\r\n" + 
				"			SUM(yesterday_salary		)	yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			SUM(month_salary			)	month_salary			,												#本月标快揽收收入\r\n" + 
				"			SUM(year_salary				)	year_salary				#本年标快揽收收入\r\n" + 
				"	FROM (\r\n" + 
				"	#天维度数据\r\n" + 
				"	SELECT \r\n" + 
				"			d.sender_city_no grid_code,	 #区县编码\r\n" + 
				"			  '1' AS grid_level,   	  									 #界面等级\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no)   region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			 d.sender_city_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no) sender_city_name,\r\n" + 
				"			d.standard_express_collection yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  0 month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  0 year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  d.standard_express_salary yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  0 month_salary	,												#本月标快揽收收入\r\n" + 
				"			  0 year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dwr_emp_daily_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id = '${LAST_DAY}' \r\n" + 
				"	UNION ALL \r\n" + 
				"	#月度数据\r\n" + 
				"	SELECT \r\n" + 
				"			d.sender_city_no grid_code,	 #区县编码\r\n" + 
				"			  '1' AS grid_level,   	  									 #界面等级\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no)   region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			 d.sender_city_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no) sender_city_name,\r\n" + 
				"				0 yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  d.standard_express_collection month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  0 year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  0 yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  d.standard_express_salary month_salary	,												#本月标快揽收收入\r\n" + 
				"			  0 year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dm_emp_month_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id = DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')\r\n" + 
				"	UNION ALL \r\n" + 
				"	#本年 \r\n" + 
				"	SELECT \r\n" + 
				"			d.sender_city_no grid_code,	 #区县编码\r\n" + 
				"			  '1' AS grid_level,   	  									 #界面等级\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no)   region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			 d.sender_city_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_city_no) sender_city_name,\r\n" + 
				"			0 yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  0 month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  d.standard_express_collection year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  0 yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  0 month_salary	,												#本月标快揽收收入\r\n" + 
				"			  d.standard_express_salary year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dm_emp_month_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id >= DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%01') and d.period_id <= DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')) f\r\n" +
				"	GROUP BY grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 sender_province_no,	\r\n" + 
				"			 sender_province_name,\r\n" + 
				"			 sender_city_no,\r\n" + 
				"			 sender_city_name";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("市级别数据插入: " + num);
		queryAndInsertPs.close();
		
		//省级别的数据插入
		deleteSQL = "DELETE FROM dm_jxyz_sand_table_t  WHERE grid_level = '0'";
		deletePs = connection.prepareStatement(deleteSQL);
		num = deletePs.executeUpdate();
		System.out.println("省级别删除: " + num);
		deletePs.close();

		queryAndInsertSQL = "INSERT INTO dm_jxyz_sand_table_t(grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 province_no,	\r\n" + 
				"			 province_name,\r\n" + 
				"			 yesterday_collection_qty,\r\n" + 
				"		    month_collection_qty	,\r\n" + 
				"		    year_collection_qty		,\r\n" + 
				"		    yesterday_salary		,\r\n" + 
				"		    month_salary			,\r\n" + 
				"		    year_salary				)\r\n" + 
				"	SELECT grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 sender_province_no,	\r\n" + 
				"			 sender_province_name,\r\n" + 
				"			SUM(yesterday_collection_qty)	yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			SUM(month_collection_qty	)	month_collection_qty	,							#本月标快揽收量\r\n" + 
				"			SUM(year_collection_qty		)	year_collection_qty	,						#本年标快揽收量\r\n" + 
				"			SUM(yesterday_salary		)	yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			SUM(month_salary			)	month_salary			,												#本月标快揽收收入\r\n" + 
				"			SUM(year_salary				)	year_salary				#本年标快揽收收入\r\n" + 
				"	FROM (\r\n" + 
				"	#天维度数据\r\n" + 
				"	SELECT \r\n" + 
				"			d.sender_province_no grid_code,	 #省编码\r\n" + 
				"			  '0' AS grid_level,   	  									 #界面等级\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no)   region_name,	#省名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			d.standard_express_collection yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  0 month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  0 year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  d.standard_express_salary yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  0 month_salary	,												#本月标快揽收收入\r\n" + 
				"			  0 year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dwr_emp_daily_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id = '${LAST_DAY}' \r\n" + 
				"	UNION ALL \r\n" + 
				"	#月度数据\r\n" + 
				"	SELECT \r\n" + 
				"			d.sender_province_no grid_code,	 #区县编码\r\n" + 
				"			  '0' AS grid_level,   	  									 #界面等级\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no)  region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			0 yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  d.standard_express_collection month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  0 year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  0 yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  d.standard_express_salary month_salary	,												#本月标快揽收收入\r\n" + 
				"			  0 year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dm_emp_month_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id = DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')\r\n" + 
				"	UNION ALL \r\n" + 
				"	#本年 \r\n" + 
				"	SELECT \r\n" + 
				"			d.sender_province_no grid_code,	 #区县编码\r\n" + 
				"			  '0' AS grid_level,   	  									 #界面等级\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no)  region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			 0 yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  0 month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  d.standard_express_collection year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  0 yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  0 month_salary	,												#本月标快揽收收入\r\n" + 
				"			  d.standard_express_salary year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dm_emp_month_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id >= DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%01') and d.period_id <= DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')) f\r\n" +
				"	GROUP BY grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 sender_province_no,	\r\n" + 
				"			 sender_province_name";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("省级别数据插入: " + num);
		queryAndInsertPs.close();
		
		
		//省级别的数据插入
		deleteSQL = "DELETE FROM dm_jxyz_sand_table_t  WHERE grid_level = '0'";
		deletePs = connection.prepareStatement(deleteSQL);
		num = deletePs.executeUpdate();
		System.out.println("省级别删除: " + num);
		deletePs.close();

		queryAndInsertSQL = "INSERT INTO dm_jxyz_sand_table_t(grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 province_no,	\r\n" + 
				"			 province_name,\r\n" + 
				"			 yesterday_collection_qty,\r\n" + 
				"		    month_collection_qty	,\r\n" + 
				"		    year_collection_qty		,\r\n" + 
				"		    yesterday_salary		,\r\n" + 
				"		    month_salary			,\r\n" + 
				"		    year_salary				)\r\n" + 
				"	SELECT grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 sender_province_no,	\r\n" + 
				"			 sender_province_name,\r\n" + 
				"			SUM(yesterday_collection_qty)	yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			SUM(month_collection_qty	)	month_collection_qty	,							#本月标快揽收量\r\n" + 
				"			SUM(year_collection_qty		)	year_collection_qty	,						#本年标快揽收量\r\n" + 
				"			SUM(yesterday_salary		)	yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			SUM(month_salary			)	month_salary			,												#本月标快揽收收入\r\n" + 
				"			SUM(year_salary				)	year_salary				#本年标快揽收收入\r\n" + 
				"	FROM (\r\n" + 
				"	#天维度数据\r\n" + 
				"	SELECT \r\n" + 
				"			d.sender_province_no grid_code,	 #省编码\r\n" + 
				"			  '0' AS grid_level,   	  									 #界面等级\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no)   region_name,	#省名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			d.standard_express_collection yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  0 month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  0 year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  d.standard_express_salary yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  0 month_salary	,												#本月标快揽收收入\r\n" + 
				"			  0 year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dwr_emp_daily_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id = '${LAST_DAY}' \r\n" + 
				"	UNION ALL \r\n" + 
				"	#月度数据\r\n" + 
				"	SELECT \r\n" + 
				"			d.sender_province_no grid_code,	 #区县编码\r\n" + 
				"			  '0' AS grid_level,   	  									 #界面等级\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no)  region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			0 yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  d.standard_express_collection month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  0 year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  0 yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  d.standard_express_salary month_salary	,												#本月标快揽收收入\r\n" + 
				"			  0 year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dm_emp_month_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id = DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')\r\n" + 
				"	UNION ALL \r\n" + 
				"	#本年 \r\n" + 
				"	SELECT \r\n" + 
				"			d.sender_province_no grid_code,	 #区县编码\r\n" + 
				"			  '0' AS grid_level,   	  									 #界面等级\r\n" + 
				"			(SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no)  region_name,								#段道名称\r\n" + 
				"			 d.sender_province_no,\r\n" + 
				"			 (SELECT region_name FROM dwr_jxyz_region_d WHERE region_code  = d.sender_province_no) sender_province_name,\r\n" + 
				"			 0 yesterday_collection_qty, #昨日标快揽收量\r\n" + 
				"			  0 month_collection_qty,							#本月标快揽收量\r\n" + 
				"			  d.standard_express_collection year_collection_qty,						#本年标快揽收量\r\n" + 
				"			  0 yesterday_salary,			#昨日标快揽收收入\r\n" + 
				"			  0 month_salary	,												#本月标快揽收收入\r\n" + 
				"			  d.standard_express_salary year_salary													#本年标快揽收收入\r\n" + 
				"	FROM dm_emp_month_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id >= DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%01') and d.period_id <= DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')) f\r\n" +
				"	GROUP BY grid_code,\r\n" + 
				"			 grid_level, \r\n" + 
				"			 region_name,\r\n" + 
				"			 sender_province_no,	\r\n" + 
				"			 sender_province_name";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("省级别数据插入: " + num);
		queryAndInsertPs.close();
		
		//更新段道人数
		queryAndInsertSQL = "UPDATE dm_jxyz_sand_table_t  n LEFT OUTER JOIN \r\n" + 
				"	(SELECT grid_code ,COUNT(post_person_no) qty FROM \r\n" + 
				"	(SELECT grid_code ,post_person_no FROM \r\n" + 
				"	(SELECT \r\n" + 
				"			CASE WHEN  d.section_code IS NOT NULL THEN \r\n" + 
				"			 d.section_code \r\n" + 
				"			 WHEN d.section_code IS NULL AND d.post_org_no = g.parent_code THEN\r\n" + 
				"			  g.grid_code \r\n" + 
				"			  ELSE CONCAT(d.post_org_no,'01') END grid_code,	 #段道编码\r\n" + 
				"			  d.post_person_no 	  									 #界面等级			 \r\n" + 
				"	FROM dwr_emp_daily_collection_t d LEFT OUTER JOIN \r\n" + 
				"	    (SELECT g.parent_code,\r\n" + 
				"			g.grid_name,\r\n" + 
				"			g.grid_code FROM \r\n" + 
				"	(SELECT \r\n" + 
				"		@grid_num := IF(@parent_code = `parent_code` , @grid_num +1 ,1 ) num,\r\n" + 
				"			`code` AS grid_code,\r\n" + 
				"			`name` AS grid_name ,\r\n" + 
				"		@parent_code:=	g.parent_code parent_code\r\n" + 
				"	FROM t_grid_m  g INNER JOIN\r\n" + 
				"	(SELECT @parent_code = '' ,@grid_num := 0) tt\r\n" + 
				"	WHERE `level` = '5'\r\n" + 
				"	ORDER BY g.parent_code,`code`  \r\n" + 
				"	) g\r\n" + 
				"	WHERE num = 1 )  g \r\n" + 
				"	ON d.post_org_no = g.parent_code\r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id = '${LAST_DAY}') t\r\n" + 
				"	group BY grid_code ,post_person_no) t\r\n" + 
				"	GROUP BY grid_code )  t \r\n" + 
				"	ON t.grid_code = n.grid_code\r\n" + 
				"	SET n.post_person_qty = t.qty\r\n" + 
				"	WHERE n.grid_level = '4'";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("更新段道人数: " + num);
		queryAndInsertPs.close();
		
		
		//营业部人员数
		queryAndInsertSQL = "UPDATE dm_jxyz_sand_table_t  n LEFT OUTER JOIN \r\n" + 
				"	(SELECT grid_code,count(post_person_no) qty FROM \r\n" + 
				"	(SELECT grid_code,post_person_no FROM \r\n" + 
				"	(SELECT \r\n" + 
				"			d.post_org_no grid_code,	 #营业部编码\r\n" + 
				"			 d.post_person_no\r\n" + 
				"	FROM dwr_emp_daily_collection_t d \r\n" + 
				"	LEFT OUTER JOIN  dwr_jxyz_department_d dep \r\n" + 
				"	ON dep.dept_code = d.post_org_no\r\n" + 
				"	WHERE d.period_id = '${LAST_DAY}' \r\n" + 
				"	) t\r\n" + 
				"	GROUP BY grid_code,post_person_no) t\r\n" + 
				"	GROUP BY grid_code )  t \r\n" + 
				"	ON t.grid_code = n.grid_code\r\n" + 
				"	SET n.post_person_qty = t.qty\r\n" + 
				"	WHERE n.grid_level = '3';";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("更新营业部人数: " + num);
		queryAndInsertPs.close();
		
		//区县人员数
		queryAndInsertSQL = "UPDATE dm_jxyz_sand_table_t  n LEFT OUTER JOIN \r\n" + 
				"(SELECT grid_code,count(post_person_no) qty FROM \r\n" + 
				"(SELECT grid_code,post_person_no FROM \r\n" + 
				"(SELECT \r\n" + 
				"			d.sender_county_no grid_code,	 #区县部编码\r\n" + 
				"			 d.post_person_no\r\n" + 
				"	FROM dwr_emp_daily_collection_t d \r\n" + 
				"	WHERE d.period_id = '${LAST_DAY}' \r\n" + 
				"	) t\r\n" + 
				"	GROUP BY grid_code,post_person_no) t\r\n" + 
				"	GROUP BY grid_code)  t \r\n" + 
				"	ON t.grid_code = n.grid_code\r\n" + 
				"	SET n.post_person_qty = t.qty\r\n" + 
				"	WHERE n.grid_level = '2';";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("更新区县人数: " + num);
		queryAndInsertPs.close();
		
		
		//市人员数
		queryAndInsertSQL = "UPDATE dm_jxyz_sand_table_t  n LEFT OUTER JOIN \r\n" + 
				"(SELECT grid_code,count(post_person_no) qty FROM \r\n" + 
				"(SELECT grid_code,post_person_no FROM \r\n" + 
				"(SELECT \r\n" + 
				"			d.sender_city_no grid_code,	 #营业部编码\r\n" + 
				"			 d.post_person_no\r\n" + 
				"	FROM dwr_emp_daily_collection_t d \r\n" + 
				"	WHERE d.period_id = '${LAST_DAY}' \r\n" + 
				"	) t\r\n" + 
				"	GROUP BY grid_code,post_person_no) t\r\n" + 
				"	GROUP BY grid_code)  t \r\n" + 
				"	ON t.grid_code = n.grid_code\r\n" + 
				"	SET n.post_person_qty = t.qty\r\n" + 
				"	WHERE n.grid_level = '1'";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("更新市人数: " + num);
		queryAndInsertPs.close();

		
		//省人员数
		queryAndInsertSQL = "UPDATE dm_jxyz_sand_table_t  n LEFT OUTER JOIN \r\n" + 
				"(SELECT grid_code,count(post_person_no) qty FROM \r\n" + 
				"(SELECT grid_code,post_person_no FROM \r\n" + 
				"(SELECT \r\n" + 
				"			d.sender_province_no grid_code,	 #营业部编码\r\n" + 
				"			 d.post_person_no\r\n" + 
				"	FROM dwr_emp_daily_collection_t d \r\n" + 
				"	WHERE d.period_id = '${LAST_DAY}' \r\n" + 
				"	) t\r\n" + 
				"	GROUP BY grid_code,post_person_no) t\r\n" + 
				"	GROUP BY grid_code)  t \r\n" + 
				"	ON t.grid_code = n.grid_code\r\n" + 
				"	SET n.post_person_qty = t.qty\r\n" + 
				"	WHERE n.grid_level = '0'";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("更新省人数: " + num);
		queryAndInsertPs.close();
		
		
		//更新资源数据
		queryAndInsertSQL = "UPDATE dm_jxyz_sand_table_t st LEFT OUTER JOIN \r\n" + 
				"	(SELECT t.section_code AS grid_code, #段道维度\r\n" + 
				"	SUM(case when resources_type= '商业楼宇' THEN 1 ELSE 0 END) AS commercial_buildings,\r\n" + 
				"	SUM(case when resources_type= '工业园区' THEN 1 ELSE 0 END) AS industrial_park,\r\n" + 
				"	SUM(case when resources_type= '住宅小区' THEN 1 ELSE 0 END) AS residential_quarters,\r\n" + 
				"	SUM(case when resources_type IN ( '校园','政务市场') THEN 1 ELSE 0 END) AS characteristic_markets  FROM \r\n" + 
				"	(SELECT r.section_code,CASE WHEN resources_type LIKE '%工业园%' THEN '工业园区'  \r\n" + 
				"	WHEN resources_type LIKE '%商业%' THEN '商业楼宇'  \r\n" + 
				"	WHEN resources_type LIKE '%政务%' THEN '政务市场' \r\n" + 
				"	WHEN resources_type IN ('特色市场','高校') THEN '校园' \r\n" + 
				"	ELSE  '住宅小区' END resources_type,\r\n" + 
				"	r.key_market_code \r\n" + 
				"	FROM \r\n" + 
				"	dwr_jxyz_resources_d r\r\n" + 
				"	GROUP BY r.section_code,CASE WHEN resources_type LIKE '%工业园%' THEN '工业园区'  \r\n" + 
				"	WHEN resources_type LIKE '%商业%' THEN '商业楼宇'  \r\n" + 
				"	WHEN resources_type LIKE '%政务%' THEN '政务市场' \r\n" + 
				"	WHEN resources_type IN ('特色市场','高校') THEN '校园' \r\n" + 
				"	ELSE  '住宅小区' END ,\r\n" + 
				"	key_market_code) t\r\n" + 
				"	GROUP BY  t.section_code\r\n" + 
				"	UNION ALL\r\n" + 
				"	SELECT t.dept_code AS grid_code, #营业部维度\r\n" + 
				"	SUM(case when resources_type= '商业楼宇' THEN 1 ELSE 0 END) AS commercial_buildings,\r\n" + 
				"	SUM(case when resources_type= '工业园区' THEN 1 ELSE 0 END) AS industrial_park,\r\n" + 
				"	SUM(case when resources_type= '住宅小区' THEN 1 ELSE 0 END) AS residential_quarters,\r\n" + 
				"	SUM(case when resources_type IN ( '校园','政务市场') THEN 1 ELSE 0 END) AS characteristic_markets  FROM \r\n" + 
				"	(SELECT r.dept_code,CASE WHEN resources_type LIKE '%工业园%' THEN '工业园区'  \r\n" + 
				"	WHEN resources_type LIKE '%商业%' THEN '商业楼宇'  \r\n" + 
				"	WHEN resources_type LIKE '%政务%' THEN '政务市场' \r\n" + 
				"	WHEN resources_type IN ('特色市场','高校') THEN '校园' \r\n" + 
				"	ELSE  '住宅小区' END resources_type,\r\n" + 
				"	r.key_market_code \r\n" + 
				"	FROM \r\n" + 
				"	dwr_jxyz_resources_d r\r\n" + 
				"	GROUP BY r.dept_code,CASE WHEN resources_type LIKE '%工业园%' THEN '工业园区'  \r\n" + 
				"	WHEN resources_type LIKE '%商业%' THEN '商业楼宇'  \r\n" + 
				"	WHEN resources_type LIKE '%政务%' THEN '政务市场' \r\n" + 
				"	WHEN resources_type IN ('特色市场','高校') THEN '校园' \r\n" + 
				"	ELSE  '住宅小区' END ,\r\n" + 
				"	key_market_code) t\r\n" + 
				"	GROUP BY  t.dept_code\r\n" + 
				"	UNION ALL #区县维度\r\n" + 
				"	SELECT t2.county_code AS grid_code,\r\n" + 
				"	SUM(case when resources_type= '商业楼宇' THEN 1 ELSE 0 END) AS commercial_buildings,\r\n" + 
				"	SUM(case when resources_type= '工业园区' THEN 1 ELSE 0 END) AS industrial_park,\r\n" + 
				"	SUM(case when resources_type= '住宅小区' THEN 1 ELSE 0 END) AS residential_quarters,\r\n" + 
				"	SUM(case when resources_type IN ( '校园','政务市场') THEN 1 ELSE 0 END) AS characteristic_markets  FROM \r\n" + 
				"	(SELECT r.county_code,CASE WHEN resources_type LIKE '%工业园%' THEN '工业园区'  \r\n" + 
				"	WHEN resources_type LIKE '%商业%' THEN '商业楼宇'  \r\n" + 
				"	WHEN resources_type LIKE '%政务%' THEN '政务市场' \r\n" + 
				"	WHEN resources_type IN ('特色市场','高校') THEN '校园' \r\n" + 
				"	ELSE  '住宅小区' END resources_type,\r\n" + 
				"	r.key_market_code \r\n" + 
				"	FROM \r\n" + 
				"	dwr_jxyz_resources_d r\r\n" + 
				"	GROUP BY r.county_code,CASE WHEN resources_type LIKE '%工业园%' THEN '工业园区'  \r\n" + 
				"	WHEN resources_type LIKE '%商业%' THEN '商业楼宇'  \r\n" + 
				"	WHEN resources_type LIKE '%政务%' THEN '政务市场' \r\n" + 
				"	WHEN resources_type IN ('特色市场','高校') THEN '校园' \r\n" + 
				"	ELSE  '住宅小区' END ,\r\n" + 
				"	key_market_code) t2\r\n" + 
				"	GROUP BY  t2.county_code\r\n" + 
				"	UNION ALL #市维度\r\n" + 
				"	SELECT t3.city_code AS grid_code,\r\n" + 
				"	SUM(case when resources_type= '商业楼宇' THEN 1 ELSE 0 END) AS commercial_buildings,\r\n" + 
				"	SUM(case when resources_type= '工业园区' THEN 1 ELSE 0 END) AS industrial_park,\r\n" + 
				"	SUM(case when resources_type= '住宅小区' THEN 1 ELSE 0 END) AS residential_quarters,\r\n" + 
				"	SUM(case when resources_type IN ( '校园','政务市场') THEN 1 ELSE 0 END) AS characteristic_markets  FROM \r\n" + 
				"	(SELECT r.city_code,CASE WHEN resources_type LIKE '%工业园%' THEN '工业园区'  \r\n" + 
				"	WHEN resources_type LIKE '%商业%' THEN '商业楼宇'  \r\n" + 
				"	WHEN resources_type LIKE '%政务%' THEN '政务市场' \r\n" + 
				"	WHEN resources_type IN ('特色市场','高校') THEN '校园' \r\n" + 
				"	ELSE  '住宅小区' END resources_type,\r\n" + 
				"	r.key_market_code \r\n" + 
				"	FROM \r\n" + 
				"	dwr_jxyz_resources_d r\r\n" + 
				"	GROUP BY r.city_code,CASE WHEN resources_type LIKE '%工业园%' THEN '工业园区'  \r\n" + 
				"	WHEN resources_type LIKE '%商业%' THEN '商业楼宇'  \r\n" + 
				"	WHEN resources_type LIKE '%政务%' THEN '政务市场' \r\n" + 
				"	WHEN resources_type IN ('特色市场','高校') THEN '校园' \r\n" + 
				"	ELSE  '住宅小区' END ,\r\n" + 
				"	key_market_code) t3\r\n" + 
				"	GROUP BY  t3.city_code\r\n" + 
				"	UNION ALL #省维度\r\n" + 
				"	SELECT t4.province_code AS grid_code,\r\n" + 
				"	SUM(case when resources_type= '商业楼宇' THEN 1 ELSE 0 END) AS commercial_buildings,\r\n" + 
				"	SUM(case when resources_type= '工业园区' THEN 1 ELSE 0 END) AS industrial_park,\r\n" + 
				"	SUM(case when resources_type= '住宅小区' THEN 1 ELSE 0 END) AS residential_quarters,\r\n" + 
				"	SUM(case when resources_type IN ( '校园','政务市场') THEN 1 ELSE 0 END) AS characteristic_markets  FROM \r\n" + 
				"	(SELECT r.province_code,CASE WHEN resources_type LIKE '%工业园%' THEN '工业园区'  \r\n" + 
				"	WHEN resources_type LIKE '%商业%' THEN '商业楼宇'  \r\n" + 
				"	WHEN resources_type LIKE '%政务%' THEN '政务市场' \r\n" + 
				"	WHEN resources_type IN ('特色市场','高校') THEN '校园' \r\n" + 
				"	ELSE  '住宅小区' END resources_type,\r\n" + 
				"	r.key_market_code \r\n" + 
				"	FROM \r\n" + 
				"	dwr_jxyz_resources_d r\r\n" + 
				"	GROUP BY r.province_code,CASE WHEN resources_type LIKE '%工业园%' THEN '工业园区'  \r\n" + 
				"	WHEN resources_type LIKE '%商业%' THEN '商业楼宇'  \r\n" + 
				"	WHEN resources_type LIKE '%政务%' THEN '政务市场' \r\n" + 
				"	WHEN resources_type IN ('特色市场','高校') THEN '校园' \r\n" + 
				"	ELSE  '住宅小区' END ,\r\n" + 
				"	key_market_code) t4\r\n" + 
				"	GROUP BY  t4.province_code)  tt\r\n" + 
				"	ON st.grid_code = tt.grid_code\r\n" + 
				"	SET st.commercial_buildings = tt.commercial_buildings,\r\n" + 
				"	st.industrial_park = tt.industrial_park,\r\n" + 
				"	st.residential_quarters = tt.residential_quarters,\r\n" + 
				"	st.characteristic_markets = tt.characteristic_markets";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("更新资源数据: " + num);
		queryAndInsertPs.close();
		
		
		//段道级比率和
		queryAndInsertSQL = "UPDATE  dm_jxyz_sand_table_t st LEFT OUTER JOIN \r\n" + 
				"		(SELECT section_code,\r\n" + 
				"		SUM(IFNULL(yesterday_collected_qty,0)) yesterday_collected_qty,\r\n" + 
				"		SUM(IFNULL(yesterday_salary,0)) yesterday_salary,\r\n" + 
				"		SUM(IFNULL(befor_day_collected_qty,0)) befor_day_collected_qty,\r\n" + 
				"		SUM(IFNULL(standard_express_salary,0)) standard_express_salary,\r\n" + 
				"		CASE WHEN SUM(IFNULL(standard_express_salary,0)) = 0 THEN 0 ELSE\r\n" + 
				"		(SUM(IFNULL(yesterday_salary,0)) - SUM(IFNULL(standard_express_salary,0))) / SUM(IFNULL(standard_express_salary,0))\r\n" + 
				"		END * 100 AS yesterday_growth_rate,\r\n" + 
				"		SUM(IFNULL(month_collected_qty,0)) month_collected_qty,\r\n" + 
				"		SUM(IFNULL(month_salary,0)) month_salary,\r\n" + 
				"		SUM(IFNULL(last_month_collected_qty,0)) last_month_collected_qty,\r\n" + 
				"		SUM(IFNULL(last_month_salary,0)) last_month_salary, \r\n" + 
				"		CASE WHEN SUM(IFNULL(last_month_salary,0)) = 0 THEN 0 ELSE\r\n" + 
				"		(SUM(IFNULL(month_salary,0)) - SUM(IFNULL(last_month_salary,0))) / SUM(IFNULL(last_month_salary,0))\r\n" + 
				"		END * 100 AS month_growth_rate,\r\n" + 
				"		SUM(IFNULL(yesterday_deliver_qty,0)) yesterday_deliver_qty,\r\n" + 
				"		SUM(IFNULL(befor_day_deliver_qty,0)) befor_day_deliver_qty,\r\n" + 
				"		SUM(IFNULL(month_deliver_qty,0)) month_deliver_qty,\r\n" + 
				"		SUM(IFNULL(last_month_deliver_qty,0)) last_month_deliver_qty,\r\n" + 
				"		SUM(IFNULL(year_delivery_qty,0)) year_delivery_qty,\r\n" + 
				"		SUM(IFNULL(last_year_total_qty,0)) last_year_total_qty,\r\n" + 
				"		CASE WHEN SUM(IFNULL(last_year_total_qty,0)) = 0 THEN\r\n" + 
				"		0 ELSE \r\n" + 
				"		(SUM(IFNULL(year_delivery_qty,0))  - \r\n" + 
				"		SUM(IFNULL(last_year_total_qty,0))) /SUM(IFNULL(last_year_total_qty,0)) \r\n" + 
				"		END * 100 year_delivery_growth_rate\r\n" + 
				"		FROM \r\n" + 
				"		(SELECT  CASE WHEN  t.section_code IS NOT NULL THEN \r\n" + 
				"					 t.section_code \r\n" + 
				"					 WHEN t.section_code IS NULL AND t.post_org_no = g.parent_code THEN\r\n" + 
				"					  t.section_code \r\n" + 
				"					  ELSE CONCAT(t.post_org_no,'01') END section_code,t.post_person_no,\r\n" + 
				"		SUM(t.standard_express_collection) yesterday_collected_qty,  ##昨天的揽收量\r\n" + 
				"		SUM(t.standard_express_salary) yesterday_salary ######昨天的揽收收入\r\n" + 
				"		FROM  dwr_emp_daily_collection_t t LEFT OUTER JOIN \r\n" + 
				"			    (SELECT g.parent_code,\r\n" + 
				"					g.grid_name,\r\n" + 
				"					g.grid_code FROM \r\n" + 
				"			(SELECT \r\n" + 
				"				@grid_num := IF(@parent_code = `parent_code` , @grid_num +1 ,1 ) num,\r\n" + 
				"					`code` AS grid_code,\r\n" + 
				"					`name` AS grid_name ,\r\n" + 
				"				@parent_code:=	g.parent_code parent_code\r\n" + 
				"			FROM t_grid_m  g INNER JOIN\r\n" + 
				"			(SELECT @parent_code = '' ,@grid_num := 0) tt\r\n" + 
				"			WHERE `level` = '5'\r\n" + 
				"			ORDER BY g.parent_code,`code`  \r\n" + 
				"			) g\r\n" + 
				"			WHERE num = 1 )  g \r\n" + 
				"			ON g.grid_code = t.section_code\r\n" + 
				"		WHERE t.period_id = '${LAST_DAY}' \r\n" + 
				"		GROUP BY CASE WHEN  t.section_code IS NOT NULL THEN \r\n" + 
				"					 t.section_code \r\n" + 
				"					 WHEN t.section_code IS NULL AND t.post_org_no = g.parent_code THEN\r\n" + 
				"					  t.section_code \r\n" + 
				"					  ELSE CONCAT(t.post_org_no,'01') END,t.post_person_no			  \r\n" + 
				"					  ) t LEFT OUTER JOIN \r\n" + 
				"		(SELECT  t.post_person_no,\r\n" + 
				"		SUM(t.standard_express_collection) befor_day_collected_qty,  ##前天的揽收量\r\n" + 
				"		SUM(t.standard_express_salary) standard_express_salary ######前天的揽收收入\r\n" + 
				"		FROM  dwr_emp_daily_collection_t t \r\n" + 
				"		WHERE t.period_id = ADDDATE(CURDATE(),INTERVAL -2 DAY)\r\n" + 
				"		GROUP BY t.post_person_no) yt\r\n" + 
				"		ON t.post_person_no = yt.post_person_no\r\n" + 
				"		LEFT OUTER JOIN \r\n" + 
				"		(SELECT mt.post_person_no ,sum(mt.standard_express_collection)  month_collected_qty, #本月揽收量\r\n" + 
				"		SUM(mt.standard_express_salary)  month_salary #本月收入\r\n" + 
				"		FROM dm_emp_month_collection_t mt WHERE mt.period_id =DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')\r\n" + 
				"		GROUP BY mt.post_person_no) mt\r\n" + 
				"		ON mt.post_person_no = t.post_person_no\r\n" + 
				"		LEFT OUTER JOIN \r\n" + 
				"		(SELECT lmt.post_person_no, sum( lmt.standard_express_collection ) last_month_collected_qty, #上月揽收量\n" +
				"		SUM( lmt.standard_express_salary ) last_month_salary #上月收入\n" +
				"		FROM dwr_emp_daily_collection_t lmt  WHERE lmt.period_id >= DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 1 MONTH ), '%Y-%m-%01' )  \n" +
				"		and lmt.period_id <= DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 1 MONTH ), '%Y-%m-%d' )  \n" +
				"		GROUP BY lmt.post_person_no) lmt\r\n" +
				"		ON lmt.post_person_no = t.post_person_no\r\n" + 
				"		LEFT OUTER JOIN \r\n" + 
				"		(SELECT del.post_person_no,SUM(ifnull(yesterday_deliver_qty,0)) yesterday_deliver_qty,\r\n" + 
				"		SUM(IFNULL(befor_day_deliver_qty,0)) befor_day_deliver_qty,\r\n" + 
				"		SUM(IFNULL(month_deliver_qty,0)) month_deliver_qty,\r\n" + 
				"		SUM(IFNULL(last_month_deliver_qty,0)) last_month_deliver_qty,\r\n" + 
				"		SUM(IFNULL(year_delivery_qty,0)) year_delivery_qty,\r\n" + 
				"		SUM(IFNULL(last_year_total_qty,0)) last_year_total_qty\r\n" + 
				"		FROM \r\n" + 
				"		(SELECT  del.post_person_no,sum(del.deliver_qty) yesterday_deliver_qty #昨天投递量\r\n" + 
				"		FROM dwr_delivery_detail_t del WHERE del.period_id = '${LAST_DAY}' \r\n" + 
				"		GROUP BY del.post_person_no) del LEFT OUTER JOIN \r\n" + 
				"		(SELECT  del.post_person_no,sum(del.deliver_qty) befor_day_deliver_qty  #前天投递量\r\n" + 
				"		FROM dwr_delivery_detail_t del WHERE del.period_id = ADDDATE(CURDATE(),INTERVAL -2 DAY)\r\n" + 
				"		GROUP BY del.post_person_no) del2\r\n" + 
				"		ON del.post_person_no = del2.post_person_no\r\n" + 
				"		LEFT OUTER JOIN \r\n" + 
				"		(SELECT  mel.post_person_no,\r\n" + 
				"		sum(mel.deliver_qty) month_deliver_qty, #本月投递量\r\n" + 
				"		SUM(mel.current_year_total_qty) year_delivery_qty,  #本年投递量\r\n" + 
				"		SUM(mel.last_year_total_qty) last_year_total_qty #去年累计投递量\r\n" + 
				"		FROM dm_delivery_month_t mel WHERE mel.period_id = '${CURR_MONTH}' \r\n" + 
				"		GROUP BY mel.post_person_no) mel\r\n" + 
				"		ON del.post_person_no = mel.post_person_no\r\n" + 
				"		LEFT OUTER JOIN \r\n" + 
				"		(SELECT  lmel.post_person_no,sum(lmel.deliver_qty) last_month_deliver_qty #上月投递量\r\n" + 
				"		FROM dm_delivery_month_t lmel WHERE lmel.period_id = '${LAST_MONTH}' \r\n" + 
				"		GROUP BY lmel.post_person_no) lmel\r\n" + 
				"		ON del.post_person_no = lmel.post_person_no\r\n" + 
				"		GROUP BY del.post_person_no) del\r\n" + 
				"		ON t.post_person_no = del.post_person_no\r\n" + 
				"		GROUP BY section_code\r\n" + 
				"		)   tmp\r\n" + 
				"		ON st.grid_code = tmp.section_code\r\n" + 
				"		SET st.yesterday_growth_rate = ifnull(tmp.yesterday_growth_rate,0),\r\n" + 
				"			st.month_growth_rate = ifnull(tmp.month_growth_rate,0),\r\n" + 
				"			st.yesterday_delivery_qty = ifnull(tmp.yesterday_deliver_qty,0),\r\n" + 
				"			st.month_delivery_qty = ifnull(tmp.month_deliver_qty,0),\r\n" + 
				"			st.year_delivery_qty = ifnull(tmp.year_delivery_qty,0),\r\n" + 
				"			st.year_delivery_growth_rate = ifnull(tmp.year_delivery_growth_rate,0)\r\n" + 
				"		WHERE st.grid_level = '4'";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("段道级比率和: " + num);
		queryAndInsertPs.close();
		
		
		
		//营业部级比率和
		queryAndInsertSQL = "UPDATE  dm_jxyz_sand_table_t st LEFT OUTER JOIN \r\n" + 
				"			(SELECT post_org_no,\r\n" + 
				"			SUM(IFNULL(yesterday_collected_qty,0)) yesterday_collected_qty,\r\n" + 
				"			SUM(IFNULL(yesterday_salary,0)) yesterday_salary,\r\n" + 
				"			SUM(IFNULL(befor_day_collected_qty,0)) befor_day_collected_qty,\r\n" + 
				"			SUM(IFNULL(standard_express_salary,0)) standard_express_salary,\r\n" + 
				"			CASE WHEN SUM(IFNULL(standard_express_salary,0)) = 0 THEN 0 ELSE\r\n" + 
				"			(SUM(IFNULL(yesterday_salary,0)) - SUM(IFNULL(standard_express_salary,0))) / SUM(IFNULL(standard_express_salary,0))\r\n" + 
				"			END * 100 AS yesterday_growth_rate,\r\n" + 
				"			SUM(IFNULL(month_collected_qty,0)) month_collected_qty,\r\n" + 
				"			SUM(IFNULL(month_salary,0)) month_salary,\r\n" + 
				"			SUM(IFNULL(last_month_collected_qty,0)) last_month_collected_qty,\r\n" + 
				"			SUM(IFNULL(last_month_salary,0)) last_month_salary, \r\n" + 
				"			CASE WHEN SUM(IFNULL(last_month_salary,0)) = 0 THEN 0 ELSE\r\n" + 
				"			(SUM(IFNULL(month_salary,0)) - SUM(IFNULL(last_month_salary,0))) / SUM(IFNULL(last_month_salary,0))\r\n" + 
				"			END * 100 AS month_growth_rate,\r\n" + 
				"			SUM(IFNULL(yesterday_deliver_qty,0)) yesterday_deliver_qty,\r\n" + 
				"			SUM(IFNULL(befor_day_deliver_qty,0)) befor_day_deliver_qty,\r\n" + 
				"			SUM(IFNULL(month_deliver_qty,0)) month_deliver_qty,\r\n" + 
				"			SUM(IFNULL(last_month_deliver_qty,0)) last_month_deliver_qty,\r\n" + 
				"			SUM(IFNULL(year_delivery_qty,0)) year_delivery_qty,\r\n" + 
				"			SUM(IFNULL(last_year_total_qty,0)) last_year_total_qty,\r\n" + 
				"			CASE WHEN SUM(IFNULL(last_year_total_qty,0)) = 0 THEN\r\n" + 
				"			0 ELSE \r\n" + 
				"			(SUM(IFNULL(year_delivery_qty,0))  - \r\n" + 
				"			SUM(IFNULL(last_year_total_qty,0))) /SUM(IFNULL(last_year_total_qty,0)) \r\n" + 
				"			END * 100 year_delivery_growth_rate\r\n" + 
				"			FROM \r\n" + 
				"			(SELECT  t.post_org_no,t.post_person_no,\r\n" + 
				"			SUM(t.standard_express_collection) yesterday_collected_qty,  ##昨天的揽收量\r\n" + 
				"			SUM(t.standard_express_salary) yesterday_salary ######昨天的揽收收入\r\n" + 
				"			FROM  dwr_emp_daily_collection_t t\r\n" + 
				"			WHERE t.period_id = '${LAST_DAY}' \r\n" + 
				"			GROUP BY t.post_org_no,t.post_person_no			  \r\n" + 
				"						  ) t LEFT OUTER JOIN \r\n" + 
				"			(SELECT  t.post_person_no,\r\n" + 
				"			SUM(t.standard_express_collection) befor_day_collected_qty,  ##前天的揽收量\r\n" + 
				"			SUM(t.standard_express_salary) standard_express_salary ######前天的揽收收入\r\n" + 
				"			FROM  dwr_emp_daily_collection_t t \r\n" + 
				"			WHERE t.period_id = ADDDATE(CURDATE(),INTERVAL -2 DAY)\r\n" + 
				"			GROUP BY t.post_person_no) yt\r\n" + 
				"			ON t.post_person_no = yt.post_person_no\r\n" + 
				"			LEFT OUTER JOIN \r\n" + 
				"			(SELECT mt.post_person_no ,sum(mt.standard_express_collection)  month_collected_qty, #本月揽收量\r\n" + 
				"			SUM(mt.standard_express_salary)  month_salary #本月收入\r\n" + 
				"			FROM dm_emp_month_collection_t mt WHERE mt.period_id =DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')\r\n" + 
				"			GROUP BY mt.post_person_no) mt\r\n" + 
				"			ON mt.post_person_no = t.post_person_no\r\n" + 
				"			LEFT OUTER JOIN \r\n" + 
				"			(SELECT lmt.post_person_no, sum( lmt.standard_express_collection ) last_month_collected_qty, #上月揽收量\n" +
				"			SUM( lmt.standard_express_salary ) last_month_salary #上月收入\n" +
				"			FROM dwr_emp_daily_collection_t lmt  WHERE lmt.period_id >= DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 1 MONTH ), '%Y-%m-%01' )  \n" +
				"			and lmt.period_id <= DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 1 MONTH ), '%Y-%m-%d' )  \n" +
				"			GROUP BY lmt.post_person_no) lmt\r\n" +
				"			ON lmt.post_person_no = t.post_person_no\r\n" + 
				"			LEFT OUTER JOIN \r\n" + 
				"			(SELECT del.post_person_no,SUM(ifnull(yesterday_deliver_qty,0)) yesterday_deliver_qty,\r\n" + 
				"			SUM(IFNULL(befor_day_deliver_qty,0)) befor_day_deliver_qty,\r\n" + 
				"			SUM(IFNULL(month_deliver_qty,0)) month_deliver_qty,\r\n" + 
				"			SUM(IFNULL(last_month_deliver_qty,0)) last_month_deliver_qty,\r\n" + 
				"			SUM(IFNULL(year_delivery_qty,0)) year_delivery_qty,\r\n" + 
				"			SUM(IFNULL(last_year_total_qty,0)) last_year_total_qty\r\n" + 
				"			FROM \r\n" + 
				"			(SELECT  del.post_person_no,sum(del.deliver_qty) yesterday_deliver_qty #昨天投递量\r\n" + 
				"			FROM dwr_delivery_detail_t del WHERE del.period_id = '${LAST_DAY}' \r\n" + 
				"			GROUP BY del.post_person_no) del LEFT OUTER JOIN \r\n" + 
				"			(SELECT  del.post_person_no,sum(del.deliver_qty) befor_day_deliver_qty  #前天投递量\r\n" + 
				"			FROM dwr_delivery_detail_t del WHERE del.period_id = ADDDATE(CURDATE(),INTERVAL -2 DAY)\r\n" + 
				"			GROUP BY del.post_person_no) del2\r\n" + 
				"			ON del.post_person_no = del2.post_person_no\r\n" + 
				"			LEFT OUTER JOIN \r\n" + 
				"			(SELECT  mel.post_person_no,\r\n" + 
				"			sum(mel.deliver_qty) month_deliver_qty, #本月投递量\r\n" + 
				"			SUM(mel.current_year_total_qty) year_delivery_qty,  #本年投递量\r\n" + 
				"			SUM(mel.last_year_total_qty) last_year_total_qty #去年累计投递量\r\n" + 
				"			FROM dm_delivery_month_t mel WHERE mel.period_id = '${CURR_MONTH}' \r\n" + 
				"			GROUP BY mel.post_person_no) mel\r\n" + 
				"			ON del.post_person_no = mel.post_person_no\r\n" + 
				"			LEFT OUTER JOIN \r\n" + 
				"			(SELECT  lmel.post_person_no,sum(lmel.deliver_qty) last_month_deliver_qty #上月投递量\r\n" + 
				"			FROM dm_delivery_month_t lmel WHERE lmel.period_id = '${LAST_MONTH}' \r\n" + 
				"			GROUP BY lmel.post_person_no) lmel\r\n" + 
				"			ON del.post_person_no = lmel.post_person_no\r\n" + 
				"			GROUP BY del.post_person_no) del\r\n" + 
				"			ON t.post_person_no = del.post_person_no\r\n" + 
				"			GROUP BY post_org_no\r\n" + 
				"			)   tmp\r\n" + 
				"			ON st.grid_code = tmp.post_org_no\r\n" + 
				"			SET st.yesterday_growth_rate = ifnull(tmp.yesterday_growth_rate,0),\r\n" + 
				"				st.month_growth_rate = ifnull(tmp.month_growth_rate,0),\r\n" + 
				"				st.yesterday_delivery_qty = ifnull(tmp.yesterday_deliver_qty,0),\r\n" + 
				"				st.month_delivery_qty = ifnull(tmp.month_deliver_qty,0),\r\n" + 
				"				st.year_delivery_qty = ifnull(tmp.year_delivery_qty,0),\r\n" + 
				"				st.year_delivery_growth_rate = ifnull(tmp.year_delivery_growth_rate,0)\r\n" + 
				"			WHERE st.grid_level = '3'";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("营业部级比率和: " + num);
		queryAndInsertPs.close();
		
		//区县级比率和
		queryAndInsertSQL = "UPDATE  dm_jxyz_sand_table_t st LEFT OUTER JOIN \r\n" + 
				"			(SELECT sender_county_no,\r\n" + 
				"			SUM(IFNULL(yesterday_collected_qty,0)) yesterday_collected_qty,\r\n" + 
				"			SUM(IFNULL(yesterday_salary,0)) yesterday_salary,\r\n" + 
				"			SUM(IFNULL(befor_day_collected_qty,0)) befor_day_collected_qty,\r\n" + 
				"			SUM(IFNULL(standard_express_salary,0)) standard_express_salary,\r\n" + 
				"			CASE WHEN SUM(IFNULL(standard_express_salary,0)) = 0 THEN 0 ELSE\r\n" + 
				"			(SUM(IFNULL(yesterday_salary,0)) - SUM(IFNULL(standard_express_salary,0))) / SUM(IFNULL(standard_express_salary,0))\r\n" + 
				"			END * 100 AS yesterday_growth_rate,\r\n" + 
				"			SUM(IFNULL(month_collected_qty,0)) month_collected_qty,\r\n" + 
				"			SUM(IFNULL(month_salary,0)) month_salary,\r\n" + 
				"			SUM(IFNULL(last_month_collected_qty,0)) last_month_collected_qty,\r\n" + 
				"			SUM(IFNULL(last_month_salary,0)) last_month_salary, \r\n" + 
				"			CASE WHEN SUM(IFNULL(last_month_salary,0)) = 0 THEN 0 ELSE\r\n" + 
				"			(SUM(IFNULL(month_salary,0)) - SUM(IFNULL(last_month_salary,0))) / SUM(IFNULL(last_month_salary,0))\r\n" + 
				"			END * 100 AS month_growth_rate,\r\n" + 
				"			SUM(IFNULL(yesterday_deliver_qty,0)) yesterday_deliver_qty,\r\n" + 
				"			SUM(IFNULL(befor_day_deliver_qty,0)) befor_day_deliver_qty,\r\n" + 
				"			SUM(IFNULL(month_deliver_qty,0)) month_deliver_qty,\r\n" + 
				"			SUM(IFNULL(last_month_deliver_qty,0)) last_month_deliver_qty,\r\n" + 
				"			SUM(IFNULL(year_delivery_qty,0)) year_delivery_qty,\r\n" + 
				"			SUM(IFNULL(last_year_total_qty,0)) last_year_total_qty,\r\n" + 
				"			CASE WHEN SUM(IFNULL(last_year_total_qty,0)) = 0 THEN\r\n" + 
				"			0 ELSE \r\n" + 
				"			(SUM(IFNULL(year_delivery_qty,0))  - \r\n" + 
				"			SUM(IFNULL(last_year_total_qty,0))) /SUM(IFNULL(last_year_total_qty,0)) \r\n" + 
				"			END * 100 year_delivery_growth_rate\r\n" + 
				"			FROM \r\n" + 
				"			(SELECT  t.sender_county_no,t.post_person_no,\r\n" + 
				"			SUM(t.standard_express_collection) yesterday_collected_qty,  ##昨天的揽收量\r\n" + 
				"			SUM(t.standard_express_salary) yesterday_salary ######昨天的揽收收入\r\n" + 
				"			FROM  dwr_emp_daily_collection_t t\r\n" + 
				"			WHERE t.period_id = '${LAST_DAY}' \r\n" + 
				"			GROUP BY t.sender_county_no,t.post_person_no			  \r\n" + 
				"						  ) t LEFT OUTER JOIN \r\n" + 
				"			(SELECT  t.post_person_no,\r\n" + 
				"			SUM(t.standard_express_collection) befor_day_collected_qty,  ##前天的揽收量\r\n" + 
				"			SUM(t.standard_express_salary) standard_express_salary ######前天的揽收收入\r\n" + 
				"			FROM  dwr_emp_daily_collection_t t \r\n" + 
				"			WHERE t.period_id = ADDDATE(CURDATE(),INTERVAL -2 DAY)\r\n" + 
				"			GROUP BY t.post_person_no) yt\r\n" + 
				"			ON t.post_person_no = yt.post_person_no\r\n" + 
				"			LEFT OUTER JOIN \r\n" + 
				"			(SELECT mt.post_person_no ,sum(mt.standard_express_collection)  month_collected_qty, #本月揽收量\r\n" + 
				"			SUM(mt.standard_express_salary)  month_salary #本月收入\r\n" + 
				"			FROM dm_emp_month_collection_t mt WHERE mt.period_id =DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')\r\n" + 
				"			GROUP BY mt.post_person_no) mt\r\n" + 
				"			ON mt.post_person_no = t.post_person_no\r\n" + 
				"			LEFT OUTER JOIN \r\n" + 
				"			(SELECT lmt.post_person_no, sum( lmt.standard_express_collection ) last_month_collected_qty, #上月揽收量\n" +
				"			SUM( lmt.standard_express_salary ) last_month_salary #上月收入\n" +
				"			FROM dwr_emp_daily_collection_t lmt  WHERE lmt.period_id >= DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 1 MONTH ), '%Y-%m-%01' )  \n" +
				"			and lmt.period_id <= DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 1 MONTH ), '%Y-%m-%d' )  \n" +
				"			GROUP BY lmt.post_person_no) lmt\r\n" +
				"			ON lmt.post_person_no = t.post_person_no\r\n" + 
				"			LEFT OUTER JOIN \r\n" + 
				"			(SELECT del.post_person_no,SUM(ifnull(yesterday_deliver_qty,0)) yesterday_deliver_qty,\r\n" + 
				"			SUM(IFNULL(befor_day_deliver_qty,0)) befor_day_deliver_qty,\r\n" + 
				"			SUM(IFNULL(month_deliver_qty,0)) month_deliver_qty,\r\n" + 
				"			SUM(IFNULL(last_month_deliver_qty,0)) last_month_deliver_qty,\r\n" + 
				"			SUM(IFNULL(year_delivery_qty,0)) year_delivery_qty,\r\n" + 
				"			SUM(IFNULL(last_year_total_qty,0)) last_year_total_qty\r\n" + 
				"			FROM \r\n" + 
				"			(SELECT  del.post_person_no,sum(del.deliver_qty) yesterday_deliver_qty #昨天投递量\r\n" + 
				"			FROM dwr_delivery_detail_t del WHERE del.period_id = '${LAST_DAY}' \r\n" + 
				"			GROUP BY del.post_person_no) del LEFT OUTER JOIN \r\n" + 
				"			(SELECT  del.post_person_no,sum(del.deliver_qty) befor_day_deliver_qty  #前天投递量\r\n" + 
				"			FROM dwr_delivery_detail_t del WHERE del.period_id = ADDDATE(CURDATE(),INTERVAL -2 DAY)\r\n" + 
				"			GROUP BY del.post_person_no) del2\r\n" + 
				"			ON del.post_person_no = del2.post_person_no\r\n" + 
				"			LEFT OUTER JOIN \r\n" + 
				"			(SELECT  mel.post_person_no,\r\n" + 
				"			sum(mel.deliver_qty) month_deliver_qty, #本月投递量\r\n" + 
				"			SUM(mel.current_year_total_qty) year_delivery_qty,  #本年投递量\r\n" + 
				"			SUM(mel.last_year_total_qty) last_year_total_qty #去年累计投递量\r\n" + 
				"			FROM dm_delivery_month_t mel WHERE mel.period_id = '${CURR_MONTH}' \r\n" + 
				"			GROUP BY mel.post_person_no) mel\r\n" + 
				"			ON del.post_person_no = mel.post_person_no\r\n" + 
				"			LEFT OUTER JOIN \r\n" + 
				"			(SELECT  lmel.post_person_no,sum(lmel.deliver_qty) last_month_deliver_qty #上月投递量\r\n" + 
				"			FROM dm_delivery_month_t lmel WHERE lmel.period_id = '${LAST_MONTH}' \r\n" + 
				"			GROUP BY lmel.post_person_no) lmel\r\n" + 
				"			ON del.post_person_no = lmel.post_person_no\r\n" + 
				"			GROUP BY del.post_person_no) del\r\n" + 
				"			ON t.post_person_no = del.post_person_no\r\n" + 
				"			GROUP BY sender_county_no\r\n" + 
				"			)   tmp\r\n" + 
				"			ON st.grid_code = tmp.sender_county_no\r\n" + 
				"			SET st.yesterday_growth_rate = ifnull(tmp.yesterday_growth_rate,0),\r\n" + 
				"				st.month_growth_rate = ifnull(tmp.month_growth_rate,0),\r\n" + 
				"				st.yesterday_delivery_qty = ifnull(tmp.yesterday_deliver_qty,0),\r\n" + 
				"				st.month_delivery_qty = ifnull(tmp.month_deliver_qty,0),\r\n" + 
				"				st.year_delivery_qty = ifnull(tmp.year_delivery_qty,0),\r\n" + 
				"				st.year_delivery_growth_rate = ifnull(tmp.year_delivery_growth_rate,0)\r\n" + 
				"			WHERE st.grid_level = '2'";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("区县级别比率和: " + num);
		queryAndInsertPs.close();
		
		//市级比率和
		queryAndInsertSQL = "UPDATE  dm_jxyz_sand_table_t st LEFT OUTER JOIN \r\n" + 
				"				(SELECT sender_city_no,\r\n" + 
				"				SUM(IFNULL(yesterday_collected_qty,0)) yesterday_collected_qty,\r\n" + 
				"				SUM(IFNULL(yesterday_salary,0)) yesterday_salary,\r\n" + 
				"				SUM(IFNULL(befor_day_collected_qty,0)) befor_day_collected_qty,\r\n" + 
				"				SUM(IFNULL(standard_express_salary,0)) standard_express_salary,\r\n" + 
				"				CASE WHEN SUM(IFNULL(standard_express_salary,0)) = 0 THEN 0 ELSE\r\n" + 
				"				(SUM(IFNULL(yesterday_salary,0)) - SUM(IFNULL(standard_express_salary,0))) / SUM(IFNULL(standard_express_salary,0))\r\n" + 
				"				END * 100 AS yesterday_growth_rate,\r\n" + 
				"				SUM(IFNULL(month_collected_qty,0)) month_collected_qty,\r\n" + 
				"				SUM(IFNULL(month_salary,0)) month_salary,\r\n" + 
				"				SUM(IFNULL(last_month_collected_qty,0)) last_month_collected_qty,\r\n" + 
				"				SUM(IFNULL(last_month_salary,0)) last_month_salary, \r\n" + 
				"				CASE WHEN SUM(IFNULL(last_month_salary,0)) = 0 THEN 0 ELSE\r\n" + 
				"				(SUM(IFNULL(month_salary,0)) - SUM(IFNULL(last_month_salary,0))) / SUM(IFNULL(last_month_salary,0))\r\n" + 
				"				END * 100 AS month_growth_rate,\r\n" + 
				"				SUM(IFNULL(yesterday_deliver_qty,0)) yesterday_deliver_qty,\r\n" + 
				"				SUM(IFNULL(befor_day_deliver_qty,0)) befor_day_deliver_qty,\r\n" + 
				"				SUM(IFNULL(month_deliver_qty,0)) month_deliver_qty,\r\n" + 
				"				SUM(IFNULL(last_month_deliver_qty,0)) last_month_deliver_qty,\r\n" + 
				"				SUM(IFNULL(year_delivery_qty,0)) year_delivery_qty,\r\n" + 
				"				SUM(IFNULL(last_year_total_qty,0)) last_year_total_qty,\r\n" + 
				"				CASE WHEN SUM(IFNULL(last_year_total_qty,0)) = 0 THEN\r\n" + 
				"				0 ELSE \r\n" + 
				"				(SUM(IFNULL(year_delivery_qty,0))  - \r\n" + 
				"				SUM(IFNULL(last_year_total_qty,0))) /SUM(IFNULL(last_year_total_qty,0)) \r\n" + 
				"				END * 100 year_delivery_growth_rate\r\n" + 
				"				FROM \r\n" + 
				"				(SELECT  t.sender_city_no,t.post_person_no,\r\n" + 
				"				SUM(t.standard_express_collection) yesterday_collected_qty,  ##昨天的揽收量\r\n" + 
				"				SUM(t.standard_express_salary) yesterday_salary ######昨天的揽收收入\r\n" + 
				"				FROM  dwr_emp_daily_collection_t t\r\n" + 
				"				WHERE t.period_id = '${LAST_DAY}' \r\n" + 
				"				GROUP BY t.sender_city_no,t.post_person_no			  \r\n" + 
				"							  ) t LEFT OUTER JOIN \r\n" + 
				"				(SELECT  t.post_person_no,\r\n" + 
				"				SUM(t.standard_express_collection) befor_day_collected_qty,  ##前天的揽收量\r\n" + 
				"				SUM(t.standard_express_salary) standard_express_salary ######前天的揽收收入\r\n" + 
				"				FROM  dwr_emp_daily_collection_t t \r\n" + 
				"				WHERE t.period_id = ADDDATE(CURDATE(),INTERVAL -2 DAY)\r\n" + 
				"				GROUP BY t.post_person_no) yt\r\n" + 
				"				ON t.post_person_no = yt.post_person_no\r\n" + 
				"				LEFT OUTER JOIN \r\n" + 
				"				(SELECT mt.post_person_no ,sum(mt.standard_express_collection)  month_collected_qty, #本月揽收量\r\n" + 
				"				SUM(mt.standard_express_salary)  month_salary #本月收入\r\n" + 
				"				FROM dm_emp_month_collection_t mt WHERE mt.period_id =DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')\r\n" + 
				"				GROUP BY mt.post_person_no) mt\r\n" + 
				"				ON mt.post_person_no = t.post_person_no\r\n" + 
				"				LEFT OUTER JOIN \r\n" + 
				"				(SELECT lmt.post_person_no, sum( lmt.standard_express_collection ) last_month_collected_qty, #上月揽收量\n" +
				"				SUM( lmt.standard_express_salary ) last_month_salary #上月收入\n" +
				"				FROM dwr_emp_daily_collection_t lmt  WHERE lmt.period_id >= DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 1 MONTH ), '%Y-%m-%01' )  \n" +
				"				and lmt.period_id <= DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 1 MONTH ), '%Y-%m-%d' )  \n" +
				"				GROUP BY lmt.post_person_no) lmt\r\n" +
				"				ON lmt.post_person_no = t.post_person_no\r\n" + 
				"				LEFT OUTER JOIN \r\n" + 
				"				(SELECT del.post_person_no,SUM(ifnull(yesterday_deliver_qty,0)) yesterday_deliver_qty,\r\n" + 
				"				SUM(IFNULL(befor_day_deliver_qty,0)) befor_day_deliver_qty,\r\n" + 
				"				SUM(IFNULL(month_deliver_qty,0)) month_deliver_qty,\r\n" + 
				"				SUM(IFNULL(last_month_deliver_qty,0)) last_month_deliver_qty,\r\n" + 
				"				SUM(IFNULL(year_delivery_qty,0)) year_delivery_qty,\r\n" + 
				"				SUM(IFNULL(last_year_total_qty,0)) last_year_total_qty\r\n" + 
				"				FROM \r\n" + 
				"				(SELECT  del.post_person_no,sum(del.deliver_qty) yesterday_deliver_qty #昨天投递量\r\n" + 
				"				FROM dwr_delivery_detail_t del WHERE del.period_id = '${LAST_DAY}' \r\n" + 
				"				GROUP BY del.post_person_no) del LEFT OUTER JOIN \r\n" + 
				"				(SELECT  del.post_person_no,sum(del.deliver_qty) befor_day_deliver_qty  #前天投递量\r\n" + 
				"				FROM dwr_delivery_detail_t del WHERE del.period_id = ADDDATE(CURDATE(),INTERVAL -2 DAY)\r\n" + 
				"				GROUP BY del.post_person_no) del2\r\n" + 
				"				ON del.post_person_no = del2.post_person_no\r\n" + 
				"				LEFT OUTER JOIN \r\n" + 
				"				(SELECT  mel.post_person_no,\r\n" + 
				"				sum(mel.deliver_qty) month_deliver_qty, #本月投递量\r\n" + 
				"				SUM(mel.current_year_total_qty) year_delivery_qty,  #本年投递量\r\n" + 
				"				SUM(mel.last_year_total_qty) last_year_total_qty #去年累计投递量\r\n" + 
				"				FROM dm_delivery_month_t mel WHERE mel.period_id = '${CURR_MONTH}' \r\n" + 
				"				GROUP BY mel.post_person_no) mel\r\n" + 
				"				ON del.post_person_no = mel.post_person_no\r\n" + 
				"				LEFT OUTER JOIN \r\n" + 
				"				(SELECT  lmel.post_person_no,sum(lmel.deliver_qty) last_month_deliver_qty #上月投递量\r\n" + 
				"				FROM dm_delivery_month_t lmel WHERE lmel.period_id = '${LAST_MONTH}' \r\n" + 
				"				GROUP BY lmel.post_person_no) lmel\r\n" + 
				"				ON del.post_person_no = lmel.post_person_no\r\n" + 
				"				GROUP BY del.post_person_no) del\r\n" + 
				"				ON t.post_person_no = del.post_person_no\r\n" + 
				"				GROUP BY sender_city_no\r\n" + 
				"				)   tmp\r\n" + 
				"				ON st.grid_code = tmp.sender_city_no\r\n" + 
				"				SET st.yesterday_growth_rate = ifnull(tmp.yesterday_growth_rate,0),\r\n" + 
				"					st.month_growth_rate = ifnull(tmp.month_growth_rate,0),\r\n" + 
				"					st.yesterday_delivery_qty = ifnull(tmp.yesterday_deliver_qty,0),\r\n" + 
				"					st.month_delivery_qty = ifnull(tmp.month_deliver_qty,0),\r\n" + 
				"					st.year_delivery_qty = ifnull(tmp.year_delivery_qty,0),\r\n" + 
				"					st.year_delivery_growth_rate = ifnull(tmp.year_delivery_growth_rate,0)\r\n" + 
				"				WHERE st.grid_level = '1'";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("市级别比率和: " + num);
		queryAndInsertPs.close();
		
		
		//省级别
		queryAndInsertSQL = "UPDATE  dm_jxyz_sand_table_t st LEFT OUTER JOIN \r\n" + 
				"					(SELECT sender_province_no,\r\n" + 
				"					SUM(IFNULL(yesterday_collected_qty,0)) yesterday_collected_qty,\r\n" + 
				"					SUM(IFNULL(yesterday_salary,0)) yesterday_salary,\r\n" + 
				"					SUM(IFNULL(befor_day_collected_qty,0)) befor_day_collected_qty,\r\n" + 
				"					SUM(IFNULL(standard_express_salary,0)) standard_express_salary,\r\n" + 
				"					CASE WHEN SUM(IFNULL(standard_express_salary,0)) = 0 THEN 0 ELSE\r\n" + 
				"					(SUM(IFNULL(yesterday_salary,0)) - SUM(IFNULL(standard_express_salary,0))) / SUM(IFNULL(standard_express_salary,0))\r\n" + 
				"					END * 100 AS yesterday_growth_rate,\r\n" + 
				"					SUM(IFNULL(month_collected_qty,0)) month_collected_qty,\r\n" + 
				"					SUM(IFNULL(month_salary,0)) month_salary,\r\n" + 
				"					SUM(IFNULL(last_month_collected_qty,0)) last_month_collected_qty,\r\n" + 
				"					SUM(IFNULL(last_month_salary,0)) last_month_salary, \r\n" + 
				"					CASE WHEN SUM(IFNULL(last_month_salary,0)) = 0 THEN 0 ELSE\r\n" + 
				"					(SUM(IFNULL(month_salary,0)) - SUM(IFNULL(last_month_salary,0))) / SUM(IFNULL(last_month_salary,0))\r\n" + 
				"					END * 100 AS month_growth_rate,\r\n" + 
				"					SUM(IFNULL(yesterday_deliver_qty,0)) yesterday_deliver_qty,\r\n" + 
				"					SUM(IFNULL(befor_day_deliver_qty,0)) befor_day_deliver_qty,\r\n" + 
				"					SUM(IFNULL(month_deliver_qty,0)) month_deliver_qty,\r\n" + 
				"					SUM(IFNULL(last_month_deliver_qty,0)) last_month_deliver_qty,\r\n" + 
				"					SUM(IFNULL(year_delivery_qty,0)) year_delivery_qty,\r\n" + 
				"					SUM(IFNULL(last_year_total_qty,0)) last_year_total_qty,\r\n" + 
				"					CASE WHEN SUM(IFNULL(last_year_total_qty,0)) = 0 THEN\r\n" + 
				"					0 ELSE \r\n" + 
				"					(SUM(IFNULL(year_delivery_qty,0))  - \r\n" + 
				"					SUM(IFNULL(last_year_total_qty,0))) /SUM(IFNULL(last_year_total_qty,0)) \r\n" + 
				"					END * 100 year_delivery_growth_rate\r\n" + 
				"					FROM \r\n" + 
				"					(SELECT  t.sender_province_no,t.post_person_no,\r\n" + 
				"					SUM(t.standard_express_collection) yesterday_collected_qty,  ##昨天的揽收量\r\n" + 
				"					SUM(t.standard_express_salary) yesterday_salary ######昨天的揽收收入\r\n" + 
				"					FROM  dwr_emp_daily_collection_t t\r\n" + 
				"					WHERE t.period_id = '${LAST_DAY}' \r\n" + 
				"					GROUP BY t.sender_province_no,t.post_person_no			  \r\n" + 
				"								  ) t LEFT OUTER JOIN \r\n" + 
				"					(SELECT  t.post_person_no,\r\n" + 
				"					SUM(t.standard_express_collection) befor_day_collected_qty,  ##前天的揽收量\r\n" + 
				"					SUM(t.standard_express_salary) standard_express_salary ######前天的揽收收入\r\n" + 
				"					FROM  dwr_emp_daily_collection_t t \r\n" + 
				"					WHERE t.period_id = ADDDATE(CURDATE(),INTERVAL -2 DAY)\r\n" + 
				"					GROUP BY t.post_person_no) yt\r\n" + 
				"					ON t.post_person_no = yt.post_person_no\r\n" + 
				"					LEFT OUTER JOIN \r\n" + 
				"					(SELECT mt.post_person_no ,sum(mt.standard_express_collection)  month_collected_qty, #本月揽收量\r\n" + 
				"					SUM(mt.standard_express_salary)  month_salary #本月收入\r\n" + 
				"					FROM dm_emp_month_collection_t mt WHERE mt.period_id =DATE_FORMAT(ADDDATE(NOW(),INTERVAL -0 MONTH),'%Y%m')\r\n" + 
				"					GROUP BY mt.post_person_no) mt\r\n" + 
				"					ON mt.post_person_no = t.post_person_no\r\n" + 
				"					LEFT OUTER JOIN \r\n" + 
				"					(SELECT lmt.post_person_no, sum( lmt.standard_express_collection ) last_month_collected_qty, #上月揽收量\n" +
				"					SUM( lmt.standard_express_salary ) last_month_salary #上月收入\n" +
				"					FROM dwr_emp_daily_collection_t lmt  WHERE lmt.period_id >= DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 1 MONTH ), '%Y-%m-%01' )  \n" +
				"					and lmt.period_id <= DATE_FORMAT( ADDDATE( NOW( ), INTERVAL - 1 MONTH ), '%Y-%m-%d' )  \n" +
				"					GROUP BY lmt.post_person_no) lmt\r\n" +
				"					ON lmt.post_person_no = t.post_person_no\r\n" + 
				"					LEFT OUTER JOIN \r\n" + 
				"					(SELECT del.post_person_no,SUM(ifnull(yesterday_deliver_qty,0)) yesterday_deliver_qty,\r\n" + 
				"					SUM(IFNULL(befor_day_deliver_qty,0)) befor_day_deliver_qty,\r\n" + 
				"					SUM(IFNULL(month_deliver_qty,0)) month_deliver_qty,\r\n" + 
				"					SUM(IFNULL(last_month_deliver_qty,0)) last_month_deliver_qty,\r\n" + 
				"					SUM(IFNULL(year_delivery_qty,0)) year_delivery_qty,\r\n" + 
				"					SUM(IFNULL(last_year_total_qty,0)) last_year_total_qty\r\n" + 
				"					FROM \r\n" + 
				"					(SELECT  del.post_person_no,sum(del.deliver_qty) yesterday_deliver_qty #昨天投递量\r\n" + 
				"					FROM dwr_delivery_detail_t del WHERE del.period_id = '${LAST_DAY}' \r\n" + 
				"					GROUP BY del.post_person_no) del LEFT OUTER JOIN \r\n" + 
				"					(SELECT  del.post_person_no,sum(del.deliver_qty) befor_day_deliver_qty  #前天投递量\r\n" + 
				"					FROM dwr_delivery_detail_t del WHERE del.period_id = ADDDATE(CURDATE(),INTERVAL -2 DAY)\r\n" + 
				"					GROUP BY del.post_person_no) del2\r\n" + 
				"					ON del.post_person_no = del2.post_person_no\r\n" + 
				"					LEFT OUTER JOIN \r\n" + 
				"					(SELECT  mel.post_person_no,\r\n" + 
				"					sum(mel.deliver_qty) month_deliver_qty, #本月投递量\r\n" + 
				"					SUM(mel.current_year_total_qty) year_delivery_qty,  #本年投递量\r\n" + 
				"					SUM(mel.last_year_total_qty) last_year_total_qty #去年累计投递量\r\n" + 
				"					FROM dm_delivery_month_t mel WHERE mel.period_id = '${CURR_MONTH}' \r\n" + 
				"					GROUP BY mel.post_person_no) mel\r\n" + 
				"					ON del.post_person_no = mel.post_person_no\r\n" + 
				"					LEFT OUTER JOIN \r\n" + 
				"					(SELECT  lmel.post_person_no,sum(lmel.deliver_qty) last_month_deliver_qty #上月投递量\r\n" + 
				"					FROM dm_delivery_month_t lmel WHERE lmel.period_id = '${LAST_MONTH}' \r\n" + 
				"					GROUP BY lmel.post_person_no) lmel\r\n" + 
				"					ON del.post_person_no = lmel.post_person_no\r\n" + 
				"					GROUP BY del.post_person_no) del\r\n" + 
				"					ON t.post_person_no = del.post_person_no\r\n" + 
				"					GROUP BY sender_province_no\r\n" + 
				"					)   tmp\r\n" + 
				"					ON st.grid_code = tmp.sender_province_no\r\n" + 
				"					SET st.yesterday_growth_rate = ifnull(tmp.yesterday_growth_rate,0),\r\n" + 
				"						st.month_growth_rate = ifnull(tmp.month_growth_rate,0),\r\n" + 
				"						st.yesterday_delivery_qty = ifnull(tmp.yesterday_deliver_qty,0),\r\n" + 
				"						st.month_delivery_qty = ifnull(tmp.month_deliver_qty,0),\r\n" + 
				"						st.year_delivery_qty = ifnull(tmp.year_delivery_qty,0),\r\n" + 
				"						st.year_delivery_growth_rate = ifnull(tmp.year_delivery_growth_rate,0)\r\n" + 
				"					WHERE st.grid_level = '0'";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("省级别比率和: " + num);
		queryAndInsertPs.close();
		
		
		//开发率
		queryAndInsertSQL = "update dm_jxyz_sand_table_t t LEFT OUTER JOIN \r\n" + 
				"		(SELECT  section_code,SUM(commercial_buildings_customer_qty) commercial_buildings_customer_qty,\r\n" + 
				"		SUM(commercial_buildings_act_customer_qty) commercial_buildings_act_customer_qty,\r\n" + 
				"		SUM(industrial_park_customer_qty) industrial_park_customer_qty,\r\n" + 
				"		SUM(industrial_park_act_customer_qty) industrial_park_act_customer_qty FROM \r\n" + 
				"		(SELECT section_code,SUM(commercial_buildings_customer_qty) commercial_buildings_customer_qty,\r\n" + 
				"		0 commercial_buildings_act_customer_qty,\r\n" + 
				"		SUM(industrial_park_customer_qty) industrial_park_customer_qty,\r\n" + 
				"		0 industrial_park_act_customer_qty FROM \r\n" + 
				"		(SELECT  d.section_code,case when d.resources_type LIKE '%商业%' THEN IFNULL(sender_qty,0)\r\n" + 
				"		ELSE 0 END commercial_buildings_customer_qty,\r\n" + 
				"		case when d.resources_type LIKE '%工业%' THEN IFNULL(sender_qty,0)\r\n" + 
				"		ELSE 0 END  industrial_park_customer_qty \r\n" + 
				"		FROM dwr_jxyz_resources_d  d \r\n" + 
				"		LEFT OUTER JOIN \r\n" + 
				"		(SELECT c.key_market_code , COUNT(c.sender_no)  sender_qty\r\n" + 
				"		FROM  dwr_jxyz_customer_d  c \r\n" + 
				"		GROUP BY c.key_market_code) c\r\n" + 
				"		ON d.key_market_code  = c.key_market_code\r\n" + 
				"		WHERE d.resources_type LIKE '%商业%' OR   d.resources_type LIKE '%工业%')  t\r\n" + 
				"		GROUP BY section_code\r\n" + 
				"		UNION ALL\r\n" + 
				"		SELECT section_code,0 commercial_buildings_customer_qty,SUM(commercial_buildings_act_customer_qty) commercial_buildings_act_customer_qty,\r\n" + 
				"		0 industrial_park_customer_qty,\r\n" + 
				"		SUM(industrial_park_act_customer_qty) industrial_park_act_customer_qty FROM \r\n" + 
				"		(SELECT  d.section_code,case when d.resources_type LIKE '%商业%' THEN 1 \r\n" + 
				"		ELSE 0 END commercial_buildings_act_customer_qty,\r\n" + 
				"		case when d.resources_type LIKE '%工业%' THEN 1\r\n" + 
				"		ELSE 0 END  industrial_park_act_customer_qty \r\n" + 
				"		FROM dwr_jxyz_resources_d  d,\r\n" + 
				"				dwr_jxyz_customer_d  c \r\n" + 
				"		WHERE (d.resources_type LIKE '%商业%' OR   d.resources_type LIKE '%工业%')\r\n" + 
				"		AND d.key_market_code = c.key_market_code)  t\r\n" + 
				"		GROUP BY section_code\r\n" + 
				"		) f\r\n" + 
				"		GROUP BY section_code)  f\r\n" + 
				"		on t.grid_code = f.section_code\r\n" + 
				"		set t.commercial_buildings_customer_qty = f.commercial_buildings_customer_qty,\r\n" + 
				"			t.commercial_buildings_act_customer_qty = f.commercial_buildings_act_customer_qty,\r\n" + 
				"			t.industrial_park_customer_qty = f.industrial_park_customer_qty,\r\n" + 
				"			t.industrial_park_act_customer_qty = f.industrial_park_act_customer_qty\r\n" + 
				"		WHERE t.grid_level = '4'";
		
		queryAndInsertSQL = Tools.parse(queryAndInsertSQL, params);
		queryAndInsertPs = connection.prepareStatement(queryAndInsertSQL);
		num = queryAndInsertPs.executeUpdate();
		System.out.println("开发率: " + num);
		queryAndInsertPs.close();
		
//		Map<String,String> transferMap = new HashMap<>();
//        transferMap.put("tableName", "dm_jxyz_sand_table_t");
//		String selectSql = "select * from dm_jxyz_sand_table_t ";
//		transferMap.put("selectSql", selectSql);
//		transferMap.put("prefix", "TRUNCATE dm_jxyz_sand_table_t");
//		HttpUtil.upload(transferMap);
	}
}
