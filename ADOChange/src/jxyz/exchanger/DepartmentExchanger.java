package jxyz.exchanger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import jxyz.utils.HttpUtil;
import jxyz.utils.Tools;

/**
 * 营业部转换同步，
 * 涉及表：dwr_jxyz_department_d；t_grid_m
 * 两张表同步数据，
 *
 * @author yuzhangfj
 * @Date 2020-12-03
 */
public class DepartmentExchanger implements Exchanger {

    @Override
    public void process(Connection connection) throws Exception {

        //先维护t_grid_m表中有，dwr_jxyz_department_d表中无的数据
        String queryGridSql = " select t.`code`,t.`name`, t.all_parent_code from  t_grid_m t LEFT JOIN dwr_jxyz_department_d d  " +
                "on t.`code` = d.dept_code " +
                "where t.`level` = 4 and d.dept_code is null";
        PreparedStatement queryGridPs = connection.prepareStatement(queryGridSql);
        System.out.println("SQL: " + queryGridPs.toString());
        ResultSet rs = queryGridPs.executeQuery();
        int insertDepartCount = 0;

        PreparedStatement parentPs = connection.prepareStatement("SELECT `code`,`name`,`level` from t_grid_m where `code` = ?");

        String insertDepartmentSql = "INSERT INTO `dwr_jxyz_department_d`(`dept_code`, `dept_name`, `province_code`, `province_name`,"
                + " `city_code`, `city_name`, `county_code`, `county_name`,  `created_date`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insertDepartment = connection.prepareStatement(insertDepartmentSql);

        String deptCode;
        String deptName;
        String parentCodes;
        String[] pCodes;
        String provinceCode = null;
        String provinceName = null;
        String cityCode = null;
        String cityName = null;
        String countyCode = null;
        String countyName = null;
        while (rs.next()) {
            deptCode = rs.getString("code");
            deptName = rs.getString("name");
            parentCodes = rs.getString("all_parent_code");
            pCodes = parentCodes.split(",");
            for (String id : pCodes) {
                parentPs.setString(1, id);
                ResultSet parent = parentPs.executeQuery();
                while (parent.next()) {
                    int level = parent.getInt("level");
                    switch (level) {
                        case 1:
                            provinceCode = parent.getString("code");
                            provinceName = parent.getString("name");
                            break;
                        case 2:
                            cityCode = parent.getString("code");
                            cityName = parent.getString("name");
                            break;
                        case 3:
                            countyCode = parent.getString("code");
                            countyName = parent.getString("name");
                            break;
                    }
                }
                parent.close();
            }
            //在dwr_jxyz_department_d中添加
            insertDepartment.setString(1, deptCode);
            insertDepartment.setString(2, deptName);
            insertDepartment.setString(3, provinceCode);
            insertDepartment.setString(4, provinceName);
            insertDepartment.setString(5, cityCode);
            insertDepartment.setString(6, cityName);
            insertDepartment.setString(7, countyCode);
            insertDepartment.setString(8, countyName);
            insertDepartment.setDate(9, new Date(new java.util.Date().getTime()));
            insertDepartment.executeUpdate();
            insertDepartCount++;
            
        }
        parentPs.close();
        insertDepartment.close();
        rs.close();
        System.out.println("输出dwr_jxyz_department_d，插入进入数：" + insertDepartCount);
        queryGridPs.close();


        //再维护dwr_jxyz_department_d表中有，t_grid_m表中无的数据
        String queryDepartSql = "select d.`dept_code`, d.`dept_name`, d.`province_code`, d.`province_name`,"
                + "d.`city_code`, d.`city_name`, d.`county_code`, d.`county_name`"
                + "from dwr_jxyz_department_d d LEFT JOIN t_grid_m  t on d.dept_code = t.`code` where t.code is null";
        PreparedStatement queryDepartPs = connection.prepareStatement(queryDepartSql);
        ResultSet queryDeparts = queryDepartPs.executeQuery();

        String insertGirdSql = "INSERT INTO `t_grid_m`(`code`, `parent_code`, `all_parent_code`, " +
                "`name`, `level`,`grid_status`, `create_user`, `create_date`) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement insertGird = connection.prepareStatement(insertGirdSql);

        int insertGridCount = 0;

        String code;
        String parentCode;
        String allParentCode;
        String name;
        int level = 4;
        int gridStatus = 1;
        String createUser = "admininset";
        Date createDate = new Date(new java.util.Date().getTime());
        while (queryDeparts.next()) {
            code = queryDeparts.getString("dept_code");
            provinceCode = queryDeparts.getString("province_code");
            cityCode = queryDeparts.getString("city_code");
            parentCode = queryDeparts.getString("county_code");
            name = queryDeparts.getString("dept_name");
            allParentCode = provinceCode +"," +cityCode+"," +parentCode+"," + code;
            //插入t_grid_m表
            insertGird.setString(1, code);
            insertGird.setString(2, parentCode);
            insertGird.setString(3, allParentCode);
            insertGird.setString(4, name);
            insertGird.setInt(5, level);
            insertGird.setInt(6, gridStatus);
            insertGird.setString(7, createUser);
            insertGird.setDate(8, createDate);
            insertGird.executeUpdate();
            insertGridCount++;
        }


        System.out.println("输出t_grid_m，插入进入数" + insertGridCount);
        queryDeparts.close();
        queryDepartPs.close();
        insertGird.close();
        
        Map<String,String> transferMap = new HashMap<>();
        transferMap.put("tableName", "dwr_jxyz_department_d");
		String selectSql = "select * from dwr_jxyz_department_d ";
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", "TRUNCATE dwr_jxyz_department_d");
		HttpUtil.upload(transferMap);
        
    }

}
