package com.isoftstone.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DB;
import com.github.drinkjava2.jsqlbox.DbContext;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class CsbController {

	@PostMapping(value = "receive/trajectory")
	@ResponseBody
	public ResponseEntity<JSONObject> trajectory() throws IOException {
		DbContext dbContext = DbContext.getGlobalDbContext();

		File file = new File("F:\\queryOrgInfByCsb.txt");
		String jsonStr = FileUtils.readFileToString(file);
		JSONObject csbJsb = JSON.parseObject(jsonStr);
		JSONObject csbBodyJsb = JSON.parseObject(csbJsb.getString("body"));

		JSONArray retBodyJsa = csbBodyJsb.getJSONArray("retBody");

		retBodyJsa.forEach(obj -> {
			JSONObject jsb = (JSONObject) obj;
			String id = UUID.randomUUID().toString();
			String code = jsb.getString("code");
			String companyName = jsb.getString("companyName");
			String administrativeDivision = jsb.getString("administrativeDivision");
			String isDeleted = jsb.getString("isDeleted");
			String companyCode = jsb.getString("companyCode");
			String simpleNameCn = jsb.getString("simpleNameCn");
			String parentOrgCode = jsb.getString("parentOrgCode");
			String nameCn = jsb.getString("nameCn");
			String sql = "INSERT INTO csborg(id,code,companyName,administrativeDivision,isDeleted,companyCode,simpleNameCn,parentOrgCode,nameCn)VALUES(?,?,?,?,?,?,?,?,?)";

			try {
				dbContext.execute(sql, id, code, companyName, administrativeDivision, isDeleted, companyCode,
						simpleNameCn, parentOrgCode, nameCn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		JSONObject resJsb = new JSONObject();
		resJsb.put("code", 0);
		resJsb.put("msg", "success");
		resJsb.put("data", "");
		return new ResponseEntity<>(resJsb, HttpStatus.OK);
	}

	@PostMapping(value = "receive/depttest")
	@ResponseBody
	public ResponseEntity<JSONObject> depttest() throws IOException {
		DbContext dbContext = DbContext.getGlobalDbContext();

		String sql = "select * from dept_test where code not in (select code from t_grid_m)";

		List<Map<String, Object>> depttestMapList = dbContext.qryMapList(sql);

		depttestMapList.forEach(depttestMap -> {
			String insertSql = "INSERT INTO t_grid_m(code,parent_code,all_parent_code,name,level,grid_status,create_user)VALUES(?,?,?,?,?,?,?)";
			
			String code=depttestMap.get("code").toString();
			String parent_code=depttestMap.get("parent_code").toString();
			Map<String,Object> parentMap = dbContext.qryMap("select all_parent_code from t_grid_m where code = ?",DB.param(parent_code));
			String all_parent_code = parentMap.get("all_parent_code").toString()+","+code;
			String name=depttestMap.get("name").toString();;
			String level="4";
			String grid_status="1";
			String create_user="sn_baby_20201125";
			try {
				dbContext.execute(insertSql, code,parent_code,all_parent_code,name,level,grid_status,create_user);
				
				String codeSon=code+"01";
				String parent_codeSon=code;
				String all_parent_codeSon = all_parent_code+","+codeSon;
				String nameSon="L01";
				String levelSon="5";
				String grid_statusSon="1";
				dbContext.execute(insertSql, codeSon,parent_codeSon,all_parent_codeSon,nameSon,levelSon,grid_statusSon,create_user);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		});

		JSONObject resJsb = new JSONObject();
		resJsb.put("code", 0);
		resJsb.put("msg", "success");
		resJsb.put("data", "");
		return new ResponseEntity<>(resJsb, HttpStatus.OK);
	}
	
	@PostMapping(value = "income/year")
	@ResponseBody
	public ResponseEntity<JSONObject> incomeYear() throws IOException {
		DbContext dbContext = DbContext.getGlobalDbContext();

		String sql = "select * from dept_test where code not in (select code from t_grid_m)";

		List<Map<String, Object>> depttestMapList = dbContext.qryMapList(sql);

		depttestMapList.forEach(depttestMap -> {
			String insertSql = "INSERT INTO t_grid_m(code,parent_code,all_parent_code,name,level,grid_status,create_user)VALUES(?,?,?,?,?,?,?)";
			
			String code=depttestMap.get("code").toString();
			String parent_code=depttestMap.get("parent_code").toString();
			Map<String,Object> parentMap = dbContext.qryMap("select all_parent_code from t_grid_m where code = ?",DB.param(parent_code));
			String all_parent_code = parentMap.get("all_parent_code").toString()+","+code;
			String name=depttestMap.get("name").toString();;
			String level="4";
			String grid_status="1";
			String create_user="sn_baby_20201125";
			try {
				dbContext.execute(insertSql, code,parent_code,all_parent_code,name,level,grid_status,create_user);
				
				String codeSon=code+"01";
				String parent_codeSon=code;
				String all_parent_codeSon = all_parent_code+","+codeSon;
				String nameSon="L01";
				String levelSon="5";
				String grid_statusSon="1";
				dbContext.execute(insertSql, codeSon,parent_codeSon,all_parent_codeSon,nameSon,levelSon,grid_statusSon,create_user);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		});

		JSONObject resJsb = new JSONObject();
		resJsb.put("code", 0);
		resJsb.put("msg", "success");
		resJsb.put("data", "");
		return new ResponseEntity<>(resJsb, HttpStatus.OK);
	}
	
	
	public static void main(String[] args) throws IOException {

		System.out.println(123);

	}
}
