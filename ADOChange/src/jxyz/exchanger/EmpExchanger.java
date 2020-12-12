package jxyz.exchanger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import jxyz.Application;
import jxyz.utils.HttpUtil;
import jxyz.utils.Tools;

/**
 * 处理员工数据
 * 输入表：sdi_jxyz_query_emp_inf_by_csb
 * 输出表：dwr_jxyz_emp_d
 * @author xiaoxin
 *
 */
public class EmpExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		//先执行download操作，更新adoEtl里的emp_code表，再执行 sdi_jxyz_query_emp_inf_by_csb 表

		//这里改变地址===================================================
		String url = "http://127.0.0.1:18411/jxyz-etl/etl/download";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("qry", "select * from dwr_jxyz_emp_d;");
		jsonObject.put("prefix", "TRUNCATE dwr_jxyz_emp_d;");
		jsonObject.put("suffix", "");
		jsonObject.put("table", "dwr_jxyz_emp_d");
		String a = HttpUtil.doPost(url,jsonObject.toJSONString());
		System.out.println("收到结果过返回===========》" + a);

		String querySQL = "SELECT e.id,\r\n" + 
				"		 e.employee_code,\r\n" + 
				"		 e.emp_name,\r\n" + 
				"		 e.org_code,\r\n" + 
				"		 d.dept_name,\r\n" + 
				"		 date_format(from_unixtime(e.modify_time / 1000),'%Y-%m-%d %h:%i:%s') AS modify_time ,\r\n" + 
				"		 e.employee_status,\r\n" + 
				"		 e.telephone\r\n" + 
				"		  FROM \r\n" + 
				"(SELECT \r\n" + 
				"		 @num := IF(@employee_code = c.employee_code, @num + 1, 1) num,\r\n" + 
				"		 @employee_code :=c.employee_code employee_code,\r\n" + 
				"		 c.name emp_name,\r\n" + 
				"		 c.org_code,\r\n" + 
				"		 c.modify_time ,\r\n" + 
				"		 c.employee_status ,\r\n" + 
				"		 c.telephone,\r\n" + 
				"		 c.id FROM \r\n" + 
				"sdi_jxyz_query_emp_inf_by_csb c ,(SELECT @employee_code := '' ,@num := 0) t\r\n" + 
				"where 1=1\r\n" + 
				"ORDER BY employee_code,modify_time DESC \r\n" + 
				") e,\r\n" + 
				"dwr_jxyz_department_d d \r\n" + 
				"WHERE e.num = 1 \r\n" + 
				"AND e.org_code = d.dept_code;";

		
		PreparedStatement queryPs = connection.prepareStatement(querySQL);
		
		System.out.println("SQL: " + queryPs.toString());
		
		ResultSet rs = queryPs.executeQuery();
		
		
		
		PreparedStatement findPs = connection.prepareStatement("select count(1) from dwr_jxyz_emp_d where emp_code=?");


		//张煜=============================================》
		PreparedStatement findSectionCodePs = connection.prepareStatement("select emp_section_code from dwr_jxyz_emp_d where emp_code =? and emp_section_code is not null LIMIT 1");

		PreparedStatement sectionPs = connection.prepareStatement("SELECT `code`,`name` FROM `t_grid_m` where parent_code =?");

		PreparedStatement insertGird = connection.prepareStatement("INSERT INTO `t_grid_m`(`code`, `parent_code`, `all_parent_code`, " +
				"`name`, `level`,`grid_status`, `create_user`, `create_date`) VALUES (?,?,?,?,?,?,?,?)");

		PreparedStatement queryDepartPs = connection.prepareStatement("select d.`dept_code`, d.`dept_name`, d.`province_code`, d.`province_name`,"
				+ "d.`city_code`, d.`city_name`, d.`county_code`, d.`county_name`"
				+ "from dwr_jxyz_department_d d   where d.dept_code = ? LIMIT 1");
		//=================================================》



		PreparedStatement insertPs = connection.prepareStatement("insert into dwr_jxyz_emp_d" +
                "(emp_code,emp_status,emp_name,emp_tel," +
                "emp_dept_code,modify_date,emp_dept_name,emp_section_code,emp_section_name)value(?,?,?,?,?,?,?,?,?)");


		PreparedStatement updatePs = connection.prepareStatement("update dwr_jxyz_emp_d "
				+ "set emp_status = ?, emp_name = ?, emp_tel = ?, "
				+ "emp_dept_code = ?, modify_date = ?, emp_dept_name = ? where emp_code = ?");

		PreparedStatement updatePsSe = connection.prepareStatement("update dwr_jxyz_emp_d "
				+ "set emp_status = ?, emp_name = ?, emp_tel = ?, "
				+ "emp_dept_code = ?, modify_date = ?, emp_dept_name = ?,"
				+ "emp_section_code =?,emp_section_name = ? where emp_code = ?");

		int insertCount = 0;
		int updateCount = 0;
		int insetGridCount = 0;
		while(rs.next())
		{
			//查找员工ID是否存在目标表，如果有则更新没有则插入
			findPs.setString(1, rs.getString("employee_code"));
			ResultSet frs = findPs.executeQuery();
			int recordCount = 0;
			if(frs.next())
			{
				recordCount = frs.getInt(1);
			}
			frs.close();
            Map<String,String> map = getSection(rs,findSectionCodePs,sectionPs,insertGird,queryDepartPs,insetGridCount);
			if(recordCount > 0)
			{
				//更新
                if (map.size() > 0) {
                    updatePsSe.setString(1, rs.getString("employee_status"));
                    updatePsSe.setString(2, rs.getString("emp_name"));
                    updatePsSe.setString(3, rs.getString("telephone"));
                    updatePsSe.setString(4, rs.getString("org_code"));
                    updatePsSe.setDate(5, rs.getDate("modify_time"));
                    updatePsSe.setString(6, rs.getString("dept_name"));
                    updatePsSe.setString(7, map.get("sectionCode"));
                    updatePsSe.setString(8, map.get("sectionName"));
                    updatePsSe.setString(9, rs.getString("employee_code"));
                    updatePsSe.executeUpdate();
                }else{
                    updatePs.setString(1, rs.getString("employee_status"));
                    updatePs.setString(2, rs.getString("emp_name"));
                    updatePs.setString(3, rs.getString("telephone"));
                    updatePs.setString(4, rs.getString("org_code"));
                    updatePs.setDate(5, rs.getDate("modify_time"));
                    updatePs.setString(6, rs.getString("dept_name"));
                    updatePs.setString(7, rs.getString("employee_code"));
                    updatePs.executeUpdate();
                }

				updateCount++;
			}
			else
			{
				//插入
				insertPs.setString(1, rs.getString("employee_code"));
				insertPs.setString(2, rs.getString("employee_status"));
				insertPs.setString(3, rs.getString("emp_name"));
				insertPs.setString(4, rs.getString("telephone"));
				insertPs.setString(5, rs.getString("org_code"));
				insertPs.setDate(6, rs.getDate("modify_time"));
				insertPs.setString(7, rs.getString("dept_name"));
                insertPs.setString(8, map.get("sectionCode"));
                insertPs.setString(9, map.get("sectionName"));
				insertPs.executeUpdate();
				insertCount++;
			}
		}
		rs.close();
		findPs.close();
		insertPs.close();
		updatePs.close();
		
		findSectionCodePs.close();
		sectionPs.close();
		insertGird.close();
		queryDepartPs.close();
		
		System.out.println(String.format("输出dwr_jxyz_emp_d，更新记录: %d, 插入进入数：%s", updateCount, insertCount));
		
		Map<String,String> transferMap = new HashMap<>();
	    transferMap.put("tableName", "dwr_jxyz_emp_d");
		String selectSql = "select * from dwr_jxyz_emp_d ";
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", "TRUNCATE dwr_jxyz_emp_d");
		HttpUtil.upload(transferMap);
	}

	private Map<String,String> getSection(ResultSet rs,PreparedStatement findSectionCodePs,PreparedStatement sectionPs,
                                          PreparedStatement insertGird,PreparedStatement queryDepartPs,
                                          int insetGridCount)throws Exception{
        //查询是否有段道code
        findSectionCodePs.setString(1,rs.getString("employee_code"));
        ResultSet fsc = findSectionCodePs.executeQuery();
        String sectionCode = null;
        String sectionName = null;
        //有段道code不做更改，无则查询营业部下是否有段道，有就随机取一个更新，无则填入L01
        //无短道code
        Map<String,String> map = new HashMap<>();
        if(!fsc.next()) {
            //查询营业部是否有段道
            sectionPs.setString(1,rs.getString("org_code"));
            ResultSet sCodeSet = sectionPs.executeQuery();
            if (sCodeSet.next()) {
                //有下标
                sCodeSet.last();
                int a  = sCodeSet.getRow();
                int count = (new Random().nextInt(a))+1;
                sCodeSet.first();
                sCodeSet.previous();
                //随机取一个存入sectionCode中
                while (sCodeSet.next()) {
                    if (count == sCodeSet.getRow()) {
                        sectionCode = sCodeSet.getString("code");
                        sectionName = sCodeSet.getString("name");
                    }
                }
            }else{
                //无下标则t_grid_m中创建一个段道L01

                String code;
                String parentCode;
                String name;
                String allParentCode;
                int gridStatus = 1;
                int level = 5;
                Date createDate = new Date(new java.util.Date().getTime());
                String createUser = "admininset";
                queryDepartPs.setString(1,rs.getString("org_code"));
                ResultSet queryDeparts = queryDepartPs.executeQuery();
                while (queryDeparts.next()) {
                    code = queryDeparts.getString("dept_code");
                    sectionCode = code + "01";
                    String provinceCode = queryDeparts.getString("province_code");
                    String cityCode = queryDeparts.getString("city_code");
                    parentCode = queryDeparts.getString("county_code");
                    name = "L01";
                    sectionName = name;
                    allParentCode = provinceCode+","+cityCode+","+parentCode+","+code+","+sectionCode;
                    //插入t_grid_m表
                    insertGird.setString(1, sectionCode);
                    insertGird.setString(2, code);
                    insertGird.setString(3, allParentCode);
                    insertGird.setString(4, name);
                    insertGird.setInt(5, level);
                    insertGird.setInt(6, gridStatus);
                    insertGird.setString(7, createUser);
                    insertGird.setDate(8, createDate);
                    insertGird.executeUpdate();
                    insetGridCount ++ ;
                }
                queryDeparts.close();
            }
            sCodeSet.close();
//            sectionPs.close();
            map.put("sectionCode",sectionCode);
            map.put("sectionName",sectionName);
        }
        fsc.close();
        return map;
    }

}
